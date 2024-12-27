package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public interface ParsedInstrumentation extends ParsedComponent {
    String getTargetPackage();

    String getTargetProcesses();

    boolean isFunctionalTest();

    boolean isHandleProfiling();
}
