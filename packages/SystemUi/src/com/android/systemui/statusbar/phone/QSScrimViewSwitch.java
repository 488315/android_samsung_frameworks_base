package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSScrimViewSwitch implements ShadeExpansionListener, StatusBarStateController.StateListener {
    public final KeyguardStateController mKeyguardStateController;
    public boolean mPanelVisible;
    public final ScrimView mScrimBehind;
    public final ScrimView mScrimInFront;
    public ScrimState mScrimState;
    public int mStatusBarState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.QSScrimViewSwitch$1 */
    public final class C30981 {
        public C30981() {
        }
    }

    public QSScrimViewSwitch(Context context, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController, ScrimController scrimController, KeyguardStateController keyguardStateController, ScrimView scrimView, ScrimView scrimView2) {
        C30981 c30981 = new C30981();
        shadeExpansionStateManager.addExpansionListener(this);
        statusBarStateController.addCallback(this);
        this.mKeyguardStateController = keyguardStateController;
        this.mScrimBehind = scrimView;
        this.mScrimInFront = scrimView2;
        scrimController.mScrimStateCallback = c30981;
    }

    @Override // com.android.systemui.shade.ShadeExpansionListener
    public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
        if (this.mStatusBarState == 1) {
            return;
        }
        onPanelExpansionChanged(shadeExpansionChangeEvent.fraction);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (this.mStatusBarState != i) {
            this.mStatusBarState = i;
            if (i == 0) {
                updateScrimVisibility();
            }
        }
    }

    public final void updateScrimVisibility() {
        int i = this.mPanelVisible && !((KeyguardStateControllerImpl) this.mKeyguardStateController).mPrimaryBouncerShowing ? 8 : 0;
        this.mScrimBehind.setVisibility(i);
        this.mScrimInFront.setVisibility(i);
    }

    public final void onPanelExpansionChanged(float f) {
        ScrimView scrimView = this.mScrimBehind;
        if (f > 0.0f && scrimView.getVisibility() != 8) {
            this.mPanelVisible = true;
            updateScrimVisibility();
        } else if (f == 0.0f && scrimView.getVisibility() == 8) {
            this.mPanelVisible = false;
            updateScrimVisibility();
        }
    }
}
