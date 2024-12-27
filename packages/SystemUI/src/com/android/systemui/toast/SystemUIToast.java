package com.android.systemui.toast;

import android.R;
import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.android.systemui.plugins.ToastPlugin;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemUIToast implements ToastPlugin.Toast {
    public final Context mContext;
    public int mDefaultGravity;
    public int mDefaultY;
    public final Animator mInAnimator;
    public final Animator mOutAnimator;
    public final ToastPlugin.Toast mPluginToast;
    public final CharSequence mText;
    public final View mToastView;

    public SystemUIToast(LayoutInflater layoutInflater, Context context, CharSequence charSequence, String str, int i, int i2) {
        this(layoutInflater, context, charSequence, null, str, i, i2);
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final Integer getGravity() {
        if (isPluginToast()) {
            ToastPlugin.Toast toast = this.mPluginToast;
            if (toast.getGravity() != null) {
                return toast.getGravity();
            }
        }
        return Integer.valueOf(this.mDefaultGravity);
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final Integer getHorizontalMargin() {
        if (isPluginToast()) {
            ToastPlugin.Toast toast = this.mPluginToast;
            if (toast.getHorizontalMargin() != null) {
                return toast.getHorizontalMargin();
            }
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final Animator getInAnimation() {
        return this.mInAnimator;
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final Animator getOutAnimation() {
        return this.mOutAnimator;
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final Integer getVerticalMargin() {
        if (isPluginToast()) {
            ToastPlugin.Toast toast = this.mPluginToast;
            if (toast.getVerticalMargin() != null) {
                return toast.getVerticalMargin();
            }
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final View getView() {
        return this.mToastView;
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final Integer getXOffset() {
        if (isPluginToast()) {
            ToastPlugin.Toast toast = this.mPluginToast;
            if (toast.getXOffset() != null) {
                return toast.getXOffset();
            }
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final Integer getYOffset() {
        if (isPluginToast()) {
            ToastPlugin.Toast toast = this.mPluginToast;
            if (toast.getYOffset() != null) {
                return toast.getYOffset();
            }
        }
        return Integer.valueOf(this.mDefaultY);
    }

    public final boolean isPluginToast() {
        return this.mPluginToast != null;
    }

    @Override // com.android.systemui.plugins.ToastPlugin.Toast
    public final void onOrientationChange(int i) {
        ToastPlugin.Toast toast = this.mPluginToast;
        if (toast != null) {
            toast.onOrientationChange(i);
        }
        this.mDefaultY = this.mContext.getResources().getDimensionPixelSize(17106355);
        this.mDefaultGravity = this.mContext.getResources().getInteger(R.integer.device_idle_max_temp_app_allowlist_duration_ms);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SystemUIToast(android.view.LayoutInflater r18, android.content.Context r19, java.lang.CharSequence r20, com.android.systemui.plugins.ToastPlugin.Toast r21, java.lang.String r22, int r23, int r24) {
        /*
            Method dump skipped, instructions count: 788
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.toast.SystemUIToast.<init>(android.view.LayoutInflater, android.content.Context, java.lang.CharSequence, com.android.systemui.plugins.ToastPlugin$Toast, java.lang.String, int, int):void");
    }
}
