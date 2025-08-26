package com.android.systemui.toast;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.ToastPlugin;

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
        this.mDefaultY = this.mDisplayContext.getResources().getDimensionPixelSize(17106448);
        this.mDefaultGravity = this.mDisplayContext.getResources().getInteger(R.integer.leanback_setup_translation_content_resting_point_v4);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0218  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SystemUIToast(LayoutInflater layoutInflater, Context context, Context context2, CharSequence charSequence, ToastPlugin.Toast toast, String str, int i, int i2) throws Resources.NotFoundException {
        ApplicationInfo applicationInfoAsUser;
        View view;
        ApplicationInfo applicationInfoAsUser2;
        char c;
        char c2;
        String str2;
        String str3;
        char c3;
        Animator inAnimation;
        Animator outAnimation;
        this.mContext = context;
        this.mDisplayContext = context2;
        this.mText = charSequence;
        this.mPluginToast = toast;
        if (!isPluginToast() || toast.getView() == null) {
            View viewInflate = LayoutInflater.from(context2).inflate(com.android.systemui.R.layout.sem_text_toast, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.message);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.icon);
            textView.setText(charSequence);
            int dimensionPixelSize = context2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.toast_text_size);
            float f = context2.getResources().getConfiguration().fontScale;
            if (f > 1.3f) {
                textView.setTextSize(0, (dimensionPixelSize / f) * 1.3f);
            }
            try {
                applicationInfoAsUser = context.getPackageManager().getApplicationInfoAsUser(str, 0, i);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("SystemUIToast", "Package name not found package=" + str + " user=" + i);
                applicationInfoAsUser = null;
            }
            Context context3 = this.mContext;
            try {
                applicationInfoAsUser2 = context3.getPackageManager().getApplicationInfoAsUser(str, PackageManager.ApplicationInfoFlags.of(128L), i);
            } catch (PackageManager.NameNotFoundException unused2) {
                Log.e("SystemUIToast", "Couldn't find application info for packageName=" + str + " userId=" + i);
            }
            Drawable badgedIcon = (applicationInfoAsUser2 == null || (applicationInfoAsUser2.flags & 1) != 0) ? null : IconDrawableFactory.newInstance(context3).getBadgedIcon(applicationInfoAsUser2, UserHandle.getUserId(applicationInfoAsUser2.uid));
            if (badgedIcon == null) {
                imageView.setVisibility(8);
                textView.setMaxLines(Integer.MAX_VALUE);
            } else {
                imageView.setImageDrawable(badgedIcon);
                textView.setMaxLines(2);
                if (applicationInfoAsUser == null) {
                    SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "No appInfo for pkg=", str, " usr=", "SystemUIToast");
                } else if (applicationInfoAsUser.labelRes != 0) {
                    try {
                        imageView.setContentDescription(this.mContext.getPackageManager().getResourcesForApplication(applicationInfoAsUser, new Configuration(this.mContext.getResources().getConfiguration())).getString(applicationInfoAsUser.labelRes));
                    } catch (PackageManager.NameNotFoundException unused3) {
                        Log.d("SystemUIToast", "Cannot find application resources for icon label.");
                    }
                }
            }
            view = viewInflate;
        } else {
            view = toast.getView();
        }
        this.mToastView = view;
        if (isPluginToast()) {
            ToastPlugin.Toast toast2 = this.mPluginToast;
            if (toast2.getInAnimation() != null) {
                inAnimation = toast2.getInAnimation();
                c = 0;
                c2 = 1;
                str2 = "alpha";
                str3 = "scaleY";
                c3 = 3;
            } else {
                ToastDefaultAnimation.Companion.getClass();
                View viewFindViewById = view.findViewById(com.android.systemui.R.id.icon);
                c = 0;
                View viewFindViewById2 = view.findViewById(com.android.systemui.R.id.text);
                if (viewFindViewById == null || viewFindViewById2 == null) {
                    c2 = 1;
                    str2 = "alpha";
                    str3 = "scaleY";
                    c3 = 3;
                    inAnimation = null;
                } else {
                    c2 = 1;
                    LinearInterpolator linearInterpolator = new LinearInterpolator();
                    c3 = 3;
                    PathInterpolator pathInterpolator = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.0f);
                    objectAnimatorOfFloat.setInterpolator(pathInterpolator);
                    objectAnimatorOfFloat.setDuration(333L);
                    ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.0f);
                    objectAnimatorOfFloat2.setInterpolator(pathInterpolator);
                    objectAnimatorOfFloat2.setDuration(333L);
                    ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
                    objectAnimatorOfFloat3.setInterpolator(linearInterpolator);
                    objectAnimatorOfFloat3.setDuration(66L);
                    viewFindViewById2.setAlpha(0.0f);
                    str2 = "alpha";
                    ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(viewFindViewById2, str2, 0.0f, 1.0f);
                    objectAnimatorOfFloat4.setInterpolator(linearInterpolator);
                    objectAnimatorOfFloat4.setDuration(283L);
                    str3 = "scaleY";
                    objectAnimatorOfFloat4.setStartDelay(50L);
                    viewFindViewById.setAlpha(0.0f);
                    ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(viewFindViewById, str2, 0.0f, 1.0f);
                    objectAnimatorOfFloat5.setInterpolator(linearInterpolator);
                    objectAnimatorOfFloat5.setDuration(283L);
                    objectAnimatorOfFloat5.setStartDelay(50L);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat5);
                    inAnimation = animatorSet;
                }
            }
        }
        this.mInAnimator = inAnimation;
        if (isPluginToast()) {
            ToastPlugin.Toast toast3 = this.mPluginToast;
            if (toast3.getOutAnimation() != null) {
                outAnimation = toast3.getOutAnimation();
            } else {
                ToastDefaultAnimation.Companion.getClass();
                View viewFindViewById3 = view.findViewById(com.android.systemui.R.id.icon);
                View viewFindViewById4 = view.findViewById(com.android.systemui.R.id.text);
                if (viewFindViewById3 == null || viewFindViewById4 == null) {
                    outAnimation = null;
                } else {
                    LinearInterpolator linearInterpolator2 = new LinearInterpolator();
                    PathInterpolator pathInterpolator2 = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
                    ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.9f);
                    objectAnimatorOfFloat6.setInterpolator(pathInterpolator2);
                    objectAnimatorOfFloat6.setDuration(250L);
                    ObjectAnimator objectAnimatorOfFloat7 = ObjectAnimator.ofFloat(view, str3, 1.0f, 0.9f);
                    objectAnimatorOfFloat7.setInterpolator(pathInterpolator2);
                    objectAnimatorOfFloat7.setDuration(250L);
                    float[] fArr = new float[2];
                    fArr[c] = view.getElevation();
                    fArr[c2] = 0.0f;
                    ObjectAnimator objectAnimatorOfFloat8 = ObjectAnimator.ofFloat(view, "elevation", fArr);
                    objectAnimatorOfFloat8.setInterpolator(linearInterpolator2);
                    objectAnimatorOfFloat8.setDuration(40L);
                    objectAnimatorOfFloat8.setStartDelay(150L);
                    ObjectAnimator objectAnimatorOfFloat9 = ObjectAnimator.ofFloat(view, str2, 1.0f, 0.0f);
                    objectAnimatorOfFloat9.setInterpolator(linearInterpolator2);
                    objectAnimatorOfFloat9.setDuration(100L);
                    objectAnimatorOfFloat9.setStartDelay(150L);
                    ObjectAnimator objectAnimatorOfFloat10 = ObjectAnimator.ofFloat(viewFindViewById4, str2, 1.0f, 0.0f);
                    objectAnimatorOfFloat10.setInterpolator(linearInterpolator2);
                    objectAnimatorOfFloat10.setDuration(166L);
                    ObjectAnimator objectAnimatorOfFloat11 = ObjectAnimator.ofFloat(viewFindViewById3, str2, 1.0f, 0.0f);
                    objectAnimatorOfFloat11.setInterpolator(linearInterpolator2);
                    objectAnimatorOfFloat11.setDuration(166L);
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    Animator[] animatorArr = new Animator[6];
                    animatorArr[c] = objectAnimatorOfFloat6;
                    animatorArr[c2] = objectAnimatorOfFloat7;
                    animatorArr[2] = objectAnimatorOfFloat8;
                    animatorArr[c3] = objectAnimatorOfFloat9;
                    animatorArr[4] = objectAnimatorOfFloat10;
                    animatorArr[5] = objectAnimatorOfFloat11;
                    animatorSet2.playTogether(animatorArr);
                    outAnimation = animatorSet2;
                }
            }
        }
        this.mOutAnimator = outAnimation;
        onOrientationChange(i2);
    }
}
