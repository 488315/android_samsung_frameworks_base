package com.android.systemui.toast;

import android.R;
import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.android.systemui.plugins.ToastPlugin;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SystemUIToast implements ToastPlugin.Toast {
    public final Context mContext;
    public int mDefaultGravity;
    public int mDefaultY;
    public final Context mDisplayContext;
    public final Animator mInAnimator;
    public final Animator mOutAnimator;
    public final ToastPlugin.Toast mPluginToast;
    public final CharSequence mText;
    public final View mToastView;

    public SystemUIToast(LayoutInflater layoutInflater, Context context, Context context2, CharSequence charSequence, String str, int i, int i2) {
        this(layoutInflater, context, context2, charSequence, null, str, i, i2);
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
        this.mDefaultY = this.mDisplayContext.getResources().getDimensionPixelSize(17106447);
        this.mDefaultGravity = this.mDisplayContext.getResources().getInteger(R.integer.leanback_setup_translation_content_resting_point_v4);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SystemUIToast(android.view.LayoutInflater r20, android.content.Context r21, android.content.Context r22, java.lang.CharSequence r23, com.android.systemui.plugins.ToastPlugin.Toast r24, java.lang.String r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 810
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.toast.SystemUIToast.<init>(android.view.LayoutInflater, android.content.Context, android.content.Context, java.lang.CharSequence, com.android.systemui.plugins.ToastPlugin$Toast, java.lang.String, int, int):void");
    }
}
