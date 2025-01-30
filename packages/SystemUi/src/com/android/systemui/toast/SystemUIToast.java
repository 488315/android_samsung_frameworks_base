package com.android.systemui.toast;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.plugins.ToastPlugin;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        Context context = this.mContext;
        this.mDefaultY = context.getResources().getDimensionPixelSize(17106244);
        this.mDefaultGravity = context.getResources().getInteger(R.integer.config_userTypePackageWhitelistMode);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SystemUIToast(LayoutInflater layoutInflater, Context context, CharSequence charSequence, ToastPlugin.Toast toast, String str, int i, int i2) {
        ApplicationInfo applicationInfo;
        Drawable drawable;
        View view;
        ApplicationInfo applicationInfoAsUser;
        Animator animator;
        View findViewById;
        AnimatorSet animatorSet;
        Animator animator2;
        this.mContext = context;
        this.mText = charSequence;
        this.mPluginToast = toast;
        if (!isPluginToast() || toast.getView() == null) {
            View inflate = LayoutInflater.from(context).inflate(com.android.systemui.R.layout.sem_text_toast, (ViewGroup) null);
            TextView textView = (TextView) inflate.findViewById(R.id.message);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.icon);
            textView.setText(charSequence);
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.toast_text_size);
            float f = context.getResources().getConfiguration().fontScale;
            if (f > 1.3f) {
                textView.setTextSize(0, (dimensionPixelSize / f) * 1.3f);
            }
            try {
                applicationInfo = context.getPackageManager().getApplicationInfoAsUser(str, 0, i);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("SystemUIToast", "Package name not found package=" + str + " user=" + i);
                applicationInfo = null;
            }
            try {
                applicationInfoAsUser = context.getPackageManager().getApplicationInfoAsUser(str, PackageManager.ApplicationInfoFlags.of(128L), i);
            } catch (PackageManager.NameNotFoundException unused2) {
                Log.e("SystemUIToast", "Couldn't find application info for packageName=" + str + " userId=" + i);
            }
            if (applicationInfoAsUser != null) {
                if (!((applicationInfoAsUser.flags & 1) != 0)) {
                    drawable = IconDrawableFactory.newInstance(context).getBadgedIcon(applicationInfoAsUser, UserHandle.getUserId(applicationInfoAsUser.uid));
                    if (drawable != null) {
                        imageView.setVisibility(8);
                        textView.setMaxLines(Integer.MAX_VALUE);
                    } else {
                        imageView.setImageDrawable(drawable);
                        textView.setMaxLines(2);
                        if (applicationInfo == null) {
                            Log.d("SystemUIToast", "No appInfo for pkg=" + str + " usr=" + i);
                        } else if (applicationInfo.labelRes != 0) {
                            try {
                                imageView.setContentDescription(context.getPackageManager().getResourcesForApplication(applicationInfo, new Configuration(context.getResources().getConfiguration())).getString(applicationInfo.labelRes));
                            } catch (PackageManager.NameNotFoundException unused3) {
                                Log.d("SystemUIToast", "Cannot find application resources for icon label.");
                            }
                        }
                    }
                    view = inflate;
                }
            }
            drawable = null;
            if (drawable != null) {
            }
            view = inflate;
        } else {
            view = toast.getView();
        }
        this.mToastView = view;
        if (isPluginToast()) {
            ToastPlugin.Toast toast2 = this.mPluginToast;
            if (toast2.getInAnimation() != null) {
                animator = toast2.getInAnimation();
                this.mInAnimator = animator;
                if (isPluginToast()) {
                    ToastPlugin.Toast toast3 = this.mPluginToast;
                    if (toast3.getOutAnimation() != null) {
                        animator2 = toast3.getOutAnimation();
                        this.mOutAnimator = animator2;
                        onOrientationChange(i2);
                    }
                }
                ToastDefaultAnimation.Companion.getClass();
                findViewById = view.findViewById(com.android.systemui.R.id.icon);
                View findViewById2 = view.findViewById(com.android.systemui.R.id.text);
                if (findViewById != null || findViewById2 == null) {
                    animatorSet = null;
                } else {
                    LinearInterpolator linearInterpolator = new LinearInterpolator();
                    PathInterpolator pathInterpolator = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.9f);
                    ofFloat.setInterpolator(pathInterpolator);
                    ofFloat.setDuration(250L);
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.9f);
                    ofFloat2.setInterpolator(pathInterpolator);
                    ofFloat2.setDuration(250L);
                    ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "elevation", view.getElevation(), 0.0f);
                    ofFloat3.setInterpolator(linearInterpolator);
                    ofFloat3.setDuration(40L);
                    ofFloat3.setStartDelay(150L);
                    ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
                    ofFloat4.setInterpolator(linearInterpolator);
                    ofFloat4.setDuration(100L);
                    ofFloat4.setStartDelay(150L);
                    ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(findViewById2, "alpha", 1.0f, 0.0f);
                    ofFloat5.setInterpolator(linearInterpolator);
                    ofFloat5.setDuration(166L);
                    ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(findViewById, "alpha", 1.0f, 0.0f);
                    ofFloat6.setInterpolator(linearInterpolator);
                    ofFloat6.setDuration(166L);
                    animatorSet = new AnimatorSet();
                    animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4, ofFloat5, ofFloat6);
                }
                animator2 = animatorSet;
                this.mOutAnimator = animator2;
                onOrientationChange(i2);
            }
        }
        ToastDefaultAnimation.Companion.getClass();
        View findViewById3 = view.findViewById(com.android.systemui.R.id.icon);
        View findViewById4 = view.findViewById(com.android.systemui.R.id.text);
        if (findViewById3 == null || findViewById4 == null) {
            animator = null;
        } else {
            LinearInterpolator linearInterpolator2 = new LinearInterpolator();
            PathInterpolator pathInterpolator2 = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
            ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.0f);
            ofFloat7.setInterpolator(pathInterpolator2);
            ofFloat7.setDuration(333L);
            ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.0f);
            ofFloat8.setInterpolator(pathInterpolator2);
            ofFloat8.setDuration(333L);
            ObjectAnimator ofFloat9 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
            ofFloat9.setInterpolator(linearInterpolator2);
            ofFloat9.setDuration(66L);
            findViewById4.setAlpha(0.0f);
            ObjectAnimator ofFloat10 = ObjectAnimator.ofFloat(findViewById4, "alpha", 0.0f, 1.0f);
            ofFloat10.setInterpolator(linearInterpolator2);
            ofFloat10.setDuration(283L);
            ofFloat10.setStartDelay(50L);
            findViewById3.setAlpha(0.0f);
            ObjectAnimator ofFloat11 = ObjectAnimator.ofFloat(findViewById3, "alpha", 0.0f, 1.0f);
            ofFloat11.setInterpolator(linearInterpolator2);
            ofFloat11.setDuration(283L);
            ofFloat11.setStartDelay(50L);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(ofFloat7, ofFloat8, ofFloat9, ofFloat10, ofFloat11);
            animator = animatorSet2;
        }
        this.mInAnimator = animator;
        if (isPluginToast()) {
        }
        ToastDefaultAnimation.Companion.getClass();
        findViewById = view.findViewById(com.android.systemui.R.id.icon);
        View findViewById22 = view.findViewById(com.android.systemui.R.id.text);
        if (findViewById != null) {
        }
        animatorSet = null;
        animator2 = animatorSet;
        this.mOutAnimator = animator2;
        onOrientationChange(i2);
    }
}
