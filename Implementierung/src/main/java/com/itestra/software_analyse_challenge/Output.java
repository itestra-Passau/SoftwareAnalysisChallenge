package com.itestra.software_analyse_challenge;

import java.util.List;

public class Output {

    private final int lineNumber;
    private boolean hasLineNumberWithoutGetterSetter = false;
    private int lineNumberWithoutGetterSetter;

    private final List<String> dependencies;

    public Output(final int lineNumber, final List<String> dependencies) {
        this.lineNumber = lineNumber;
        this.dependencies = dependencies;
    }

    @SuppressWarnings("unused")
    public Output lineNumberWithoutGetterSetter(final int lineNumberWithoutGetterSetter) {
        this.lineNumberWithoutGetterSetter = lineNumberWithoutGetterSetter;
        this.hasLineNumberWithoutGetterSetter = true;
        return this;
    }

    public String getLineNumber() {
        return String.valueOf(lineNumber);
    }

    public String getLineNumberWithoutGetterSetter() {
        return this.hasLineNumberWithoutGetterSetter ? String.valueOf(lineNumberWithoutGetterSetter) : "N/A";
    }

    public String getDependencies() {
        return String.valueOf(dependencies);
    }
}
