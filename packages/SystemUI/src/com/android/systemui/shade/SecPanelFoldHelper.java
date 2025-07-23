package com.android.systemui.shade;

import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecPanelFoldHelper {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecPanelFoldHelper() {
        new SecPanelFoldHelper$screenRatioListener$1(this);
        SecQsUiDisplayModeInteractor.FoldState foldState = SecQsUiDisplayModeInteractor.FoldState.FOLD;
    }
}
