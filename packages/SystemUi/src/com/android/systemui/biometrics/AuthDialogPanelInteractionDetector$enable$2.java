package com.android.systemui.biometrics;

import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthDialogPanelInteractionDetector$enable$2 implements ShadeExpansionListener, FunctionAdapter {
    public final /* synthetic */ AuthDialogPanelInteractionDetector $tmp0;

    public AuthDialogPanelInteractionDetector$enable$2(AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector) {
        this.$tmp0 = authDialogPanelInteractionDetector;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ShadeExpansionListener) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new FunctionReferenceImpl(1, this.$tmp0, AuthDialogPanelInteractionDetector.class, "onPanelExpansionChanged", "onPanelExpansionChanged(Lcom/android/systemui/shade/ShadeExpansionChangeEvent;)V", 0);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        AuthDialogPanelInteractionDetector authDialogPanelInteractionDetector = this.$tmp0;
        authDialogPanelInteractionDetector.getClass();
        authDialogPanelInteractionDetector.mainExecutor.execute(new AuthDialogPanelInteractionDetector$onPanelExpansionChanged$1(authDialogPanelInteractionDetector, shadeExpansionChangeEvent));
    }
}
