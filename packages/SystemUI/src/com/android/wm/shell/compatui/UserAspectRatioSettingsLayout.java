package com.android.wm.shell.compatui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.common.HandlerExecutor;

/* loaded from: classes3.dex */
public class UserAspectRatioSettingsLayout extends LinearLayout {
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final Interpolator PATH_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
    public UserAspectRatioSettingsWindowManager mWindowManager;

    public UserAspectRatioSettingsLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ImageButton imageButton = (ImageButton) findViewById(R.id.user_aspect_ratio_settings_button);
        final int i = 0;
        imageButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.compatui.UserAspectRatioSettingsLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ UserAspectRatioSettingsLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                UserAspectRatioSettingsLayout userAspectRatioSettingsLayout = this.f$0;
                switch (i2) {
                    case 0:
                        UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager = userAspectRatioSettingsLayout.mWindowManager;
                        userAspectRatioSettingsWindowManager.mOnButtonClicked.accept(userAspectRatioSettingsWindowManager.mTaskInfo, userAspectRatioSettingsWindowManager.mTaskListener);
                        break;
                    default:
                        Interpolator interpolator = UserAspectRatioSettingsLayout.LINEAR_INTERPOLATOR;
                        userAspectRatioSettingsLayout.setViewVisibility(R.id.user_aspect_ratio_settings_hint, false);
                        break;
                }
            }
        });
        imageButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.wm.shell.compatui.UserAspectRatioSettingsLayout$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager = this.f$0.mWindowManager;
                UserAspectRatioSettingsLayout userAspectRatioSettingsLayout = userAspectRatioSettingsWindowManager.mLayout;
                if (userAspectRatioSettingsLayout == null) {
                    return true;
                }
                userAspectRatioSettingsLayout.setViewVisibility(R.id.user_aspect_ratio_settings_hint, true);
                long jIntValue = ((Integer) userAspectRatioSettingsWindowManager.mDisappearTimeSupplier.apply(4)).intValue();
                userAspectRatioSettingsWindowManager.mNextButtonHideTimeMs = SystemClock.uptimeMillis() + jIntValue;
                ((HandlerExecutor) userAspectRatioSettingsWindowManager.mShellExecutor).executeDelayed(new UserAspectRatioSettingsWindowManager$$ExternalSyntheticLambda0(userAspectRatioSettingsWindowManager, 1), jIntValue);
                return true;
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.user_aspect_ratio_settings_hint);
        ((TextView) linearLayout.findViewById(R.id.compat_mode_hint_text)).setText(R.string.user_aspect_ratio_settings_button_hint);
        final int i2 = 1;
        linearLayout.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.compatui.UserAspectRatioSettingsLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ UserAspectRatioSettingsLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                UserAspectRatioSettingsLayout userAspectRatioSettingsLayout = this.f$0;
                switch (i22) {
                    case 0:
                        UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager = userAspectRatioSettingsLayout.mWindowManager;
                        userAspectRatioSettingsWindowManager.mOnButtonClicked.accept(userAspectRatioSettingsWindowManager.mTaskInfo, userAspectRatioSettingsWindowManager.mTaskListener);
                        break;
                    default:
                        Interpolator interpolator = UserAspectRatioSettingsLayout.LINEAR_INTERPOLATOR;
                        userAspectRatioSettingsLayout.setViewVisibility(R.id.user_aspect_ratio_settings_hint, false);
                        break;
                }
            }
        });
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager = this.mWindowManager;
        WindowManager.LayoutParams windowLayoutParams = userAspectRatioSettingsWindowManager.getWindowLayoutParams();
        SurfaceControlViewHost surfaceControlViewHost = userAspectRatioSettingsWindowManager.mViewHost;
        if (surfaceControlViewHost == null) {
            return;
        }
        surfaceControlViewHost.relayout(windowLayoutParams);
        userAspectRatioSettingsWindowManager.updateSurfacePosition();
    }

    public final void setViewVisibility(int i, boolean z) {
        final View viewFindViewById = findViewById(i);
        if (viewFindViewById.getVisibility() == (z ? 0 : 8)) {
            return;
        }
        if (!z) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewFindViewById, "alpha", 1.0f, 0.0f);
            objectAnimatorOfFloat.setDuration(167L);
            objectAnimatorOfFloat.setInterpolator(LINEAR_INTERPOLATOR);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.compatui.UserAspectRatioSettingsLayout.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    viewFindViewById.setVisibility(8);
                    UserAspectRatioSettingsLayout.this.mWindowManager.release();
                }
            });
            objectAnimatorOfFloat.start();
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewFindViewById, "alpha", 0.0f, 1.0f);
        objectAnimatorOfFloat2.setDuration(167L);
        objectAnimatorOfFloat2.setInterpolator(LINEAR_INTERPOLATOR);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(viewFindViewById, "scaleY", 0.8f, 1.0f);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(viewFindViewById, "scaleX", 0.8f, 1.0f);
        objectAnimatorOfFloat4.setDuration(300L);
        Interpolator interpolator = PATH_INTERPOLATOR;
        objectAnimatorOfFloat4.setInterpolator(interpolator);
        objectAnimatorOfFloat3.setDuration(300L);
        objectAnimatorOfFloat3.setInterpolator(interpolator);
        animatorSet.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.wm.shell.compatui.UserAspectRatioSettingsLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                viewFindViewById.setVisibility(0);
            }
        });
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat3, objectAnimatorOfFloat4);
        animatorSet.start();
    }

    public UserAspectRatioSettingsLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UserAspectRatioSettingsLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public UserAspectRatioSettingsLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
