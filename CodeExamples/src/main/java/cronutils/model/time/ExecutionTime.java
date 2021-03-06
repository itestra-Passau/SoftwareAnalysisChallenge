/*
 * Copyright 2014 jmrozanec
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cronutils.model.time;

import cronutils.model.CompositeCron;
import cronutils.model.Cron;
import cronutils.model.SingleCron;
import cronutils.model.field.CronField;
import cronutils.model.field.CronFieldName;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Calculates execution time given a cron pattern.
 */
public interface ExecutionTime {

    /**
     * Creates execution time for given Cron.
     *
     * @param cron - Cron instance
     * @return ExecutionTime instance
     */
    public static ExecutionTime forCron(final Cron cron) {
        if(cron instanceof SingleCron){
            final Map<CronFieldName, CronField> fields = cron.retrieveFieldsAsMap();
            final ExecutionTimeBuilder executionTimeBuilder = new ExecutionTimeBuilder(cron);
            for (final CronFieldName name : CronFieldName.values()) {
                if (fields.get(name) != null) {
                    switch (name) {
                        case SECOND:
                            executionTimeBuilder.forSecondsMatching(fields.get(name));
                            break;
                        case MINUTE:
                            executionTimeBuilder.forMinutesMatching(fields.get(name));
                            break;
                        case HOUR:
                            executionTimeBuilder.forHoursMatching(fields.get(name));
                            break;
                        case DAY_OF_WEEK:
                            executionTimeBuilder.forDaysOfWeekMatching(fields.get(name));
                            break;
                        case DAY_OF_MONTH:
                            executionTimeBuilder.forDaysOfMonthMatching(fields.get(name));
                            break;
                        case MONTH:
                            executionTimeBuilder.forMonthsMatching(fields.get(name));
                            break;
                        case YEAR:
                            executionTimeBuilder.forYearsMatching(fields.get(name));
                            break;
                        case DAY_OF_YEAR:
                            executionTimeBuilder.forDaysOfYearMatching(fields.get(name));
                            break;
                        default:
                            break;
                    }
                }
            }
            return executionTimeBuilder.build();
        }else{
            return new CompositeExecutionTime(((CompositeCron)cron).getCrons().parallelStream().map(ExecutionTime::forCron).collect(Collectors.toList()));
        }

    }

    /**
     * Provide nearest date for next execution.
     *
     * @param date - ZonedDateTime instance. If null, a NullPointerException will be raised.
     * @return Optional ZonedDateTime instance, never null. Contains next execution time or empty.
     */
    Optional<ZonedDateTime> nextExecution(final ZonedDateTime date);

    /**
     * Provide nearest time for next execution.
     *
     * Due to the question #468 we clarify: crons execute on local instance time.
     * See: https://serverfault.com/questions/791713/what-time-zone-is-a-cron-job-using
     * We ask for a ZonedDateTime for two reasons:
     *  (i) to provide flexibility on which timezone the cron is being executed
     *  (ii) to be able to reproduce issues regardless of our own local time (e.g.: daylight savings, etc.)
     *
     * @param date - ZonedDateTime instance. If null, a NullPointerException will be raised.
     * @return Duration instance, never null. Time to next execution.
     */
    Optional<Duration> timeToNextExecution(final ZonedDateTime date);

    /**
     * Provide nearest date for last execution.
     *
     * Due to the question #468 we clarify: crons execute on local instance time.
     * See: https://serverfault.com/questions/791713/what-time-zone-is-a-cron-job-using
     * We ask for a ZonedDateTime for two reasons:
     * (i) to provide flexibility on which timezone the cron is being executed
     * (ii) to be able to reproduce issues regardless of our own local time (e.g.: daylight savings, etc.)
     * 
     * @param date - ZonedDateTime instance. If null, a NullPointerException will be raised.
     * @return Optional ZonedDateTime instance, never null. Last execution time or empty.
     */
    Optional<ZonedDateTime> lastExecution(final ZonedDateTime date);

    /**
     * Provide nearest time from last execution.
     *
     * @param date - ZonedDateTime instance. If null, a NullPointerException will be raised.
     * @return Duration instance, never null. Time from last execution.
     */
    Optional<Duration> timeFromLastExecution(final ZonedDateTime date);

    /**
     * Provide feedback if a given date matches the cron expression.
     *
     * @param date - ZonedDateTime instance. If null, a NullPointerException will be raised.
     * @return true if date matches cron expression requirements, false otherwise.
     */
    boolean isMatch(ZonedDateTime date);
}
