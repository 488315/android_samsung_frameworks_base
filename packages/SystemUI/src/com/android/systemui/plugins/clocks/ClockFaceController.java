package com.android.systemui.plugins.clocks;

import android.view.View;

/* loaded from: classes2.dex */
public interface ClockFaceController {
    ClockAnimations getAnimations();

    ClockFaceConfig getConfig();

    ClockFaceEvents getEvents();

    ClockFaceLayout getLayout();

    ThemeConfig getTheme();

    View getView();

    public final class DefaultImpls {
        public static /* synthetic */ void getView$annotations() {
        }
    }
}
