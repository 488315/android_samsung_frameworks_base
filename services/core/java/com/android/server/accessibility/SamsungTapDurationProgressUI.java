package com.android.server.accessibility;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.util.Property;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SamsungTapDurationProgressUI {
    public ImageView mArrow;
    public ImageView mBackground;
    public ImageView mCircle;
    public Context mContext;
    public int mDensityDpi;
    public Display mDisplay;
    public long mDuration;
    public ObjectAnimator mFadeInAnimator;
    public ObjectAnimator mFadeOutAnimator;
    public ImageView mHold;
    public boolean mIsRemoveAnimationEnabled;
    public boolean mIsShortThreshold;
    public final int mNavigationBarHeight;
    public WindowManager.LayoutParams mParams;
    public ProgressBar mProgress;
    public AnimatorSet mRotationSet;
    public AnimatorSet mScaleSet;
    public int mSize;
    public ImageView mStandBy;
    public int mUiMode;
    public View mView;
    public WindowManager mWindowManager;
    public final Handler mHandler = new Handler();
    public final Thread mUiThread = Thread.currentThread();

    public SamsungTapDurationProgressUI(Context context) {
        this.mContext = context;
        this.mUiMode = context.getResources().getConfiguration().uiMode;
        this.mDensityDpi = this.mContext.getResources().getConfiguration().densityDpi;
        this.mSize = this.mContext.getResources().getDimensionPixelSize(17106301);
        this.mNavigationBarHeight = this.mContext.getResources().getBoolean(R.bool.config_sms_decode_gsm_8bit_data) ? this.mContext.getResources().getDimensionPixelSize(R.dimen.resolver_max_collapsed_height) : 0;
        this.mIsRemoveAnimationEnabled = Settings.Global.getInt(this.mContext.getContentResolver(), "remove_animations", 0) == 1;
        this.mIsShortThreshold = false;
        makeView();
        makeAnimation();
    }

    public final void makeAnimation() {
        this.mScaleSet = new AnimatorSet();
        this.mRotationSet = new AnimatorSet();
        final int i = 0;
        final int i2 = 1;
        this.mScaleSet.playTogether(ObjectAnimator.ofFloat(this.mView, (Property<View, Float>) View.SCALE_X, 0.8f, 1.0f), ObjectAnimator.ofFloat(this.mView, (Property<View, Float>) View.SCALE_Y, 0.8f, 1.0f));
        this.mScaleSet.setDuration(350L);
        this.mScaleSet.addListener(new Animator.AnimatorListener(this) { // from class: com.android.server.accessibility.SamsungTapDurationProgressUI.1
            public final /* synthetic */ SamsungTapDurationProgressUI this$0;

            {
                this.this$0 = this;
            }

            private final void onAnimationCancel$com$android$server$accessibility$SamsungTapDurationProgressUI$1(Animator animator) {
            }

            private final void onAnimationCancel$com$android$server$accessibility$SamsungTapDurationProgressUI$3(Animator animator) {
            }

            private final void onAnimationRepeat$com$android$server$accessibility$SamsungTapDurationProgressUI$1(Animator animator) {
            }

            private final void onAnimationRepeat$com$android$server$accessibility$SamsungTapDurationProgressUI$3(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                int i3 = i;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                switch (i) {
                    case 0:
                        this.this$0.mView.setScaleX(1.0f);
                        this.this$0.mView.setScaleY(1.0f);
                        break;
                    default:
                        SamsungTapDurationProgressUI samsungTapDurationProgressUI = this.this$0;
                        samsungTapDurationProgressUI.mStandBy.setVisibility((samsungTapDurationProgressUI.mIsRemoveAnimationEnabled || samsungTapDurationProgressUI.mIsShortThreshold) ? 0 : 8);
                        this.this$0.mHold.setVisibility(8);
                        this.this$0.mView.setVisibility(8);
                        break;
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
                int i3 = i;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                switch (i) {
                    case 0:
                        this.this$0.mCircle.setRotation(FullScreenMagnificationGestureHandler.MAX_SCALE);
                        this.this$0.mArrow.setRotation(FullScreenMagnificationGestureHandler.MAX_SCALE);
                        this.this$0.mArrow.setAlpha(FullScreenMagnificationGestureHandler.MAX_SCALE);
                        this.this$0.mView.setScaleX(0.8f);
                        this.this$0.mView.setScaleY(0.8f);
                        this.this$0.mView.setAlpha(1.0f);
                        this.this$0.mProgress.setProgress(0);
                        this.this$0.mRotationSet.start();
                        this.this$0.mFadeInAnimator.start();
                        break;
                    default:
                        this.this$0.mView.setScaleX(1.0f);
                        this.this$0.mView.setScaleY(1.0f);
                        break;
                }
            }
        });
        this.mScaleSet.setInterpolator(new PathInterpolator(0.22f, 0.17f, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f));
        ImageView imageView = this.mCircle;
        Property property = View.ROTATION;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) property, FullScreenMagnificationGestureHandler.MAX_SCALE, 360.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mArrow, (Property<ImageView, Float>) property, FullScreenMagnificationGestureHandler.MAX_SCALE, 360.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.accessibility.SamsungTapDurationProgressUI.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SamsungTapDurationProgressUI.this.mProgress.setProgress((int) ((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.mRotationSet.playTogether(ofFloat, ofFloat2);
        ImageView imageView2 = this.mArrow;
        Property property2 = View.ALPHA;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(imageView2, (Property<ImageView, Float>) property2, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
        this.mFadeInAnimator = ofFloat3;
        ofFloat3.setDuration(100L);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mView, (Property<View, Float>) property2, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE);
        this.mFadeOutAnimator = ofFloat4;
        ofFloat4.setDuration(150L);
        this.mFadeOutAnimator.addListener(new Animator.AnimatorListener(this) { // from class: com.android.server.accessibility.SamsungTapDurationProgressUI.1
            public final /* synthetic */ SamsungTapDurationProgressUI this$0;

            {
                this.this$0 = this;
            }

            private final void onAnimationCancel$com$android$server$accessibility$SamsungTapDurationProgressUI$1(Animator animator) {
            }

            private final void onAnimationCancel$com$android$server$accessibility$SamsungTapDurationProgressUI$3(Animator animator) {
            }

            private final void onAnimationRepeat$com$android$server$accessibility$SamsungTapDurationProgressUI$1(Animator animator) {
            }

            private final void onAnimationRepeat$com$android$server$accessibility$SamsungTapDurationProgressUI$3(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                int i3 = i2;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                switch (i2) {
                    case 0:
                        this.this$0.mView.setScaleX(1.0f);
                        this.this$0.mView.setScaleY(1.0f);
                        break;
                    default:
                        SamsungTapDurationProgressUI samsungTapDurationProgressUI = this.this$0;
                        samsungTapDurationProgressUI.mStandBy.setVisibility((samsungTapDurationProgressUI.mIsRemoveAnimationEnabled || samsungTapDurationProgressUI.mIsShortThreshold) ? 0 : 8);
                        this.this$0.mHold.setVisibility(8);
                        this.this$0.mView.setVisibility(8);
                        break;
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
                int i3 = i2;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                switch (i2) {
                    case 0:
                        this.this$0.mCircle.setRotation(FullScreenMagnificationGestureHandler.MAX_SCALE);
                        this.this$0.mArrow.setRotation(FullScreenMagnificationGestureHandler.MAX_SCALE);
                        this.this$0.mArrow.setAlpha(FullScreenMagnificationGestureHandler.MAX_SCALE);
                        this.this$0.mView.setScaleX(0.8f);
                        this.this$0.mView.setScaleY(0.8f);
                        this.this$0.mView.setAlpha(1.0f);
                        this.this$0.mProgress.setProgress(0);
                        this.this$0.mRotationSet.start();
                        this.this$0.mFadeInAnimator.start();
                        break;
                    default:
                        this.this$0.mView.setScaleX(1.0f);
                        this.this$0.mView.setScaleY(1.0f);
                        break;
                }
            }
        });
    }

    public final void makeView() {
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        this.mWindowManager = windowManager;
        this.mDisplay = windowManager.getDefaultDisplay();
        if (this.mParams == null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.type = 2006;
            layoutParams.flags = 1800;
            layoutParams.format = -3;
            layoutParams.gravity = 51;
            layoutParams.samsungFlags |= 131072;
            layoutParams.layoutInDisplayCutoutMode = 1;
        }
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.notification_material_action_list, (ViewGroup) null);
        this.mView = inflate;
        inflate.bringToFront();
        this.mView.invalidate();
        this.mView.setVisibility(8);
        this.mCircle = (ImageView) this.mView.findViewById(16909859);
        this.mArrow = (ImageView) this.mView.findViewById(16909857);
        this.mBackground = (ImageView) this.mView.findViewById(16909858);
        this.mProgress = (ProgressBar) this.mView.findViewById(16909865);
        this.mStandBy = (ImageView) this.mView.findViewById(16909866);
        this.mHold = (ImageView) this.mView.findViewById(16909860);
        setRemoveAnimationEnabled(this.mIsRemoveAnimationEnabled);
        this.mWindowManager.addView(this.mView, this.mParams);
    }

    public final void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public final void setRemoveAnimationEnabled(boolean z) {
        this.mIsRemoveAnimationEnabled = z;
        int i = 0;
        this.mCircle.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
        this.mArrow.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
        this.mBackground.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
        this.mProgress.setVisibility((z || this.mIsShortThreshold) ? 8 : 0);
        ImageView imageView = this.mStandBy;
        if (!z && !this.mIsShortThreshold) {
            i = 8;
        }
        imageView.setVisibility(i);
        this.mHold.setVisibility(8);
    }
}
