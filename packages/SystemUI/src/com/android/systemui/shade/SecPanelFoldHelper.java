package com.android.systemui.shade;

import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SecPanelFoldHelper {
    public static final /* synthetic */ int $r8$clinit = 0;

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
        LazyKt__LazyJVMKt.lazy(new SecPanelFoldHelper$$ExternalSyntheticLambda0(0));
        new SecPanelFoldHelper$screenRatioListener$1(this);
        SecQsUiDisplayModeInteractor.FoldState foldState = SecQsUiDisplayModeInteractor.FoldState.FOLD;
    }
}
