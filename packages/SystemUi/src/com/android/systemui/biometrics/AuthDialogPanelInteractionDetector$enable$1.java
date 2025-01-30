package com.android.systemui.biometrics;

import com.android.systemui.shade.ShadeStateListener;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthDialogPanelInteractionDetector$enable$1 implements ShadeStateListener, FunctionAdapter {
    public final /* synthetic */ AuthDialogPanelInteractionDetector $tmp0;

    public AuthDialogPanelInteractionDetector$enable$1(AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector) {
        this.$tmp0 = authDialogPanelInteractionDetector;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ShadeStateListener) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(1, this.$tmp0, AuthDialogPanelInteractionDetector.class, "onPanelStateChanged", "onPanelStateChanged(I)V", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.shade.ShadeStateListener
    public final void onPanelStateChanged(int i) {
        AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector = this.$tmp0;
        authDialogPanelInteractionDetector.getClass();
        authDialogPanelInteractionDetector.mainExecutor.execute(new AuthDialogPanelInteractionDetector$onPanelStateChanged$1(authDialogPanelInteractionDetector, i));
    }
}
