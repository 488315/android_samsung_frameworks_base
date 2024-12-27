package com.android.systemui.media.controls.ui.view;

import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
