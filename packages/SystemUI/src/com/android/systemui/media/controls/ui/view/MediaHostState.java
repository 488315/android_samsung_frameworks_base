package com.android.systemui.media.controls.ui.view;

import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface MediaHostState {
    MediaHost.MediaHostStateHolder copy();

    boolean getDisableScrolling();

    DisappearParameters getDisappearParameters();

    boolean getExpandedMatchesParentHeight();

    float getExpansion();

    boolean getFalsingProtectionNeeded();

    MeasurementInput getMeasurementInput();

    boolean getShowsOnlyActiveMedia();

    float getSquishFraction();

    boolean getVisible();
}
