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

package cronutils.parser;

import cronutils.model.field.CronField;
import cronutils.model.field.CronFieldName;
import cronutils.model.field.constraint.FieldConstraints;
import cronutils.utils.Preconditions;

import java.util.Comparator;

/**
 * Represents a cron field.
 */
public class CronParserField {

    private final CronFieldName field;
    private final FieldConstraints constraints;
    private final FieldParser parser;
    private final boolean optional;

    /**
     * Mandatory CronParserField Constructor.
     *
     * @param fieldName   - CronFieldName instance
     * @param constraints - FieldConstraints, constraints
     */
    public CronParserField(final CronFieldName fieldName, final FieldConstraints constraints) {
        this(fieldName, constraints, false);
    }

    /**
     * Constructor.
     *
     * @param fieldName   - CronFieldName instance
     * @param constraints - FieldConstraints, constraints
     * @param optional    - optional tag
     */
    public CronParserField(final CronFieldName fieldName, final FieldConstraints constraints, final boolean optional) {
        field = Preconditions.checkNotNull(fieldName, "CronFieldName must not be null");
        this.constraints = Preconditions.checkNotNull(constraints, "FieldConstraints must not be null");
        parser = new FieldParser(constraints);
        this.optional = optional;
    }

    /**
     * Returns field name.
     *
     * @return CronFieldName, never null
     */
    public CronFieldName getField() {
        return field;
    }

    /**
     * Returns optional tag.
     *
     * @return optional tag
     */
    public final boolean isOptional() {
        return optional;
    }

    /**
     * Parses a String cron expression.
     *
     * @param expression - cron expression
     * @return parse result as CronFieldParseResult instance - never null. May throw a RuntimeException if cron expression is bad.
     */
    public CronField parse(final String expression) {
        String newExpression = expression;
        if (getField().equals(CronFieldName.DAY_OF_WEEK) && newExpression.endsWith("L")) {
            Integer value = constraints.getStringMappingValue(newExpression.substring(0, newExpression.length() - 1));
            if (value != null) {
                newExpression = value + "L";
            }
        }
        return new CronField(field, parser.parse(newExpression), constraints);
    }

    /**
     * Create a Comparator that compares CronField instances using CronFieldName value.
     *
     * @return Comparator for CronField instance, never null.
     */
    public static Comparator<CronParserField> createFieldTypeComparator() {
        return Comparator.comparingInt(o -> o.getField().getOrder());
    }

    @Override
    public String toString() {
        return "CronParserField{" + "field=" + field + '}';
    }
}
