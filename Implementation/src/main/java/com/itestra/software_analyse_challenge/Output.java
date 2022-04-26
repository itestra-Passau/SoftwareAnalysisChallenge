package com.itestra.software_analyse_challenge;

import java.util.List;

public class Output {

    private final int lineNumber;
    private boolean hasLineNumberWithoutGetter = false;
    private int lineNumberWithoutGetter;

    private final List<String> dependencies;

    public Output(final int lineNumber, final List<String> dependencies) {
        this.lineNumber = lineNumber;
        this.dependencies = dependencies;
    }

    @SuppressWarnings("unused")
    public Output lineNumberWithoutGetter(final int lineNumberWithoutGetter) {
        this.lineNumberWithoutGetter = lineNumberWithoutGetter;
        this.hasLineNumberWithoutGetter = true;
        return this;
    }

    public String getLineNumber() {
        return String.valueOf(lineNumber);
    }

    public String getLineNumberWithoutGetter() {
        return this.hasLineNumberWithoutGetter ? String.valueOf(lineNumberWithoutGetter) : "N/A";
    }

    public String getDependencies() {
        return String.valueOf(dependencies);
    }
}
