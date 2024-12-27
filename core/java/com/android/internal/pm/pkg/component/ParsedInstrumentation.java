package com.android.internal.pm.pkg.component;

public interface ParsedInstrumentation extends ParsedComponent {
    String getTargetPackage();

    String getTargetProcesses();

    boolean isFunctionalTest();

    boolean isHandleProfiling();
}
