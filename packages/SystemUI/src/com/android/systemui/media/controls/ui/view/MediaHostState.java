package com.android.systemui.media.controls.ui.view;

import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;

public interface MediaHostState {
    MediaHost.MediaHostStateHolder copy();

    DisappearParameters getDisappearParameters();

    boolean getExpandedMatchesParentHeight();

    float getExpansion();

    boolean getFalsingProtectionNeeded();

    MeasurementInput getMeasurementInput();

    boolean getShowsOnlyActiveMedia();

    float getSquishFraction();

    boolean getVisible();
}
