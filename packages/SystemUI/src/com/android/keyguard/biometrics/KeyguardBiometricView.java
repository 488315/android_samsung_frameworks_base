package com.android.keyguard.biometrics;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.android.keyguard.SecLockIconView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class KeyguardBiometricView extends FrameLayout {
    public final Display defaultDisplay;
    public SecLockIconView lockIconView;
    public int orientation;

    public /* synthetic */ KeyguardBiometricView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration != null) {
            int i = this.orientation;
            int i2 = configuration.orientation;
            if (i != i2) {
                this.orientation = i2;
                SecLockIconView secLockIconView = this.lockIconView;
                if (secLockIconView == null) {
                    return;
                }
                secLockIconView.setClickable(i2 == 2);
            }
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.orientation = getContext().getResources().getConfiguration().orientation;
        SecLockIconView secLockIconView = (SecLockIconView) findViewById(R.id.bouncer_lock_icon_view);
        this.lockIconView = secLockIconView;
        if (secLockIconView != null) {
            secLockIconView.setClickable(this.orientation == 2);
        }
        SecLockIconView secLockIconView2 = this.lockIconView;
        if (secLockIconView2 != null) {
            secLockIconView2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.keyguard.biometrics.KeyguardBiometricView$onFinishInflate$1
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    if (KeyguardBiometricView.this.getContext().getResources().getConfiguration().orientation == 1) {
                        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                        SecLockIconView secLockIconView3 = KeyguardBiometricView.this.lockIconView;
                        if (secLockIconView3 == null) {
                            return;
                        }
                        secLockIconView3.setClickable(false);
                    }
                }
            });
        }
    }

    public KeyguardBiometricView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.defaultDisplay = ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getDisplay(0);
    }
}
