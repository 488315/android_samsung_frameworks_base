package com.android.systemui.shade;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface QuickSettingsController {
    void closeQs();

    void closeQsCustomizer();

    boolean getExpanded();

    SecQuickSettingsControllerImpl getSecQuickSettingsControllerImpl$1();

    boolean isCustomizing();

    boolean shouldQuickSettingsIntercept(float f, float f2, float f3);
}
