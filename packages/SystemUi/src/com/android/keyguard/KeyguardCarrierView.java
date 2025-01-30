package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardCarrierView extends KeyguardInputView {
    public KeyguardCarrierView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final CharSequence getTitle() {
        return null;
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (isShown()) {
            getRootView().setSystemUiVisibility(getRootView().getSystemUiVisibility() | QuickStepContract.SYSUI_STATE_BACK_DISABLED);
        }
    }

    public KeyguardCarrierView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
    }
}
