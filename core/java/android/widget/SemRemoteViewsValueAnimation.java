package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class SemRemoteViewsValueAnimation extends SemRemoteViewsAnimation {
    private static final String ANIMATION_TYPE_LAYOUT_PARAMS_HEIGHT = "height";
    private static final String ANIMATION_TYPE_LAYOUT_PARAMS_WIDTH = "width";
    public static final String ANIMATION_TYPE_PROGRESS = "progress";
    private static final String ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT = "decimal_text";
    public static final Parcelable.Creator<SemRemoteViewsValueAnimation> CREATOR =
            new Parcelable.Creator<
                    SemRemoteViewsValueAnimation>() { // from class:
                                                      // android.widget.SemRemoteViewsValueAnimation.2
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemRemoteViewsValueAnimation createFromParcel(Parcel in) {
                    return new SemRemoteViewsValueAnimation(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemRemoteViewsValueAnimation[] newArray(int size) {
                    return new SemRemoteViewsValueAnimation[size];
                }
            };
    private static final String LOG_TAG = "SemRemoteViewsValueAnimation";
    public static final String VALUE_TYPE_COLOR = "color";
    public static final String VALUE_TYPE_FLOAT = "float";
    public static final String VALUE_TYPE_INT = "int";
    private final String mAnimationType;
    private DecimalFormat mDecimalFormat;
    private String mDecimalFormatString;
    private final long mDuration;
    private final float mFloatValueFrom;
    private final float mFloatValueTo;
    private final int mIntValueFrom;
    private final int mIntValueTo;
    private Interpolator mInterpolator;
    private int mInterpolatorResId;
    private final boolean mIsValidArgument;
    private Bundle mOptions;
    private final String mValueType;

    public SemRemoteViewsValueAnimation(
            int viewId,
            String animationType,
            String valueType,
            float valueFrom,
            float ValueTo,
            long duration) {
        this(viewId, animationType, valueType, valueFrom, ValueTo, duration, (Bundle) null);
    }

    SemRemoteViewsValueAnimation(
            int viewId,
            String animationType,
            String valueType,
            float valueFrom,
            float ValueTo,
            long duration,
            Bundle options) {
        super(viewId);
        this.mInterpolatorResId = -1;
        this.mAnimationType = animationType;
        if (ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT.equals(animationType)) {
            this.mValueType = VALUE_TYPE_FLOAT;
            this.mDecimalFormatString = valueType;
        } else {
            this.mValueType = valueType;
        }
        this.mFloatValueFrom = valueFrom;
        this.mFloatValueTo = ValueTo;
        this.mIntValueFrom = 0;
        this.mIntValueTo = 0;
        this.mDuration = duration;
        this.mIsValidArgument = checkArgumentValidation();
        this.mOptions = options;
    }

    public SemRemoteViewsValueAnimation(
            int viewId,
            String animationType,
            String valueType,
            int valueFrom,
            int valueTo,
            long duration) {
        this(viewId, animationType, valueType, valueFrom, valueTo, duration, (Bundle) null);
    }

    SemRemoteViewsValueAnimation(
            int viewId,
            String animationType,
            String valueType,
            int valueFrom,
            int valueTo,
            long duration,
            Bundle options) {
        super(viewId);
        this.mInterpolatorResId = -1;
        this.mAnimationType = animationType;
        if (ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT.equals(animationType)) {
            this.mValueType = "int";
            this.mDecimalFormatString = valueType;
        } else {
            this.mValueType = valueType;
        }
        this.mFloatValueFrom = 0.0f;
        this.mFloatValueTo = 0.0f;
        this.mIntValueFrom = valueFrom;
        this.mIntValueTo = valueTo;
        this.mDuration = duration;
        this.mIsValidArgument = checkArgumentValidation();
        this.mOptions = options;
    }

    protected SemRemoteViewsValueAnimation(Parcel in) {
        super(in);
        this.mInterpolatorResId = -1;
        this.mAnimationType = in.readString();
        this.mValueType = in.readString();
        this.mFloatValueFrom = in.readFloat();
        this.mFloatValueTo = in.readFloat();
        this.mIntValueFrom = in.readInt();
        this.mIntValueTo = in.readInt();
        this.mDuration = in.readLong();
        this.mIsValidArgument = in.readBoolean();
        this.mDecimalFormatString = in.readString();
        this.mInterpolatorResId = in.readInt();
        this.mOptions = in.readBundle();
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void semSetInterpolator(int resId) {
        this.mInterpolatorResId = resId;
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mAnimationType);
        dest.writeString(this.mValueType);
        dest.writeFloat(this.mFloatValueFrom);
        dest.writeFloat(this.mFloatValueTo);
        dest.writeInt(this.mIntValueFrom);
        dest.writeInt(this.mIntValueTo);
        dest.writeLong(this.mDuration);
        dest.writeBoolean(this.mIsValidArgument);
        dest.writeString(this.mDecimalFormatString);
        dest.writeInt(this.mInterpolatorResId);
        dest.writeBundle(this.mOptions);
    }

    public static void writeToParcel(SemRemoteViewsValueAnimation c, Parcel out) {
        if (c != null) {
            c.writeToParcel(out, 0);
        } else {
            out.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View root) {
        View targetView;
        ValueAnimator animator;
        Animator.AnimatorListener animatorListener;
        Context context;
        if (!this.mIsValidArgument) {
            Log.e(LOG_TAG, "Illegal Argument");
            return;
        }
        if (root == null || (targetView = root.findViewById(this.mViewId)) == null) {
            return;
        }
        animator = new ValueAnimator();
        if (this.mInterpolatorResId != -1) {
            if (this.mInterpolator == null && (context = targetView.getContext()) != null) {
                this.mInterpolator =
                        AnimationUtils.loadInterpolator(context, this.mInterpolatorResId);
            }
            animator.setInterpolator(this.mInterpolator);
        }
        animator.setDuration(this.mDuration);
        switch (this.mValueType) {
            case "float":
                animator.setEvaluator(new FloatEvaluator());
                animator.setFloatValues(this.mFloatValueFrom, this.mFloatValueTo);
                break;
            case "int":
                animator.setEvaluator(new IntEvaluator());
                animator.setIntValues(this.mIntValueFrom, this.mIntValueTo);
                break;
            case "color":
                animator.setEvaluator(new ArgbEvaluator());
                animator.setIntValues(this.mIntValueFrom, this.mIntValueTo);
                break;
            default:
                return;
        }
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener =
                provideAnimatorUpdateListener(targetView);
        if (animatorUpdateListener == null
                || (animatorListener = provideAnimatorListener(targetView)) == null) {
            return;
        }
        animator.addUpdateListener(animatorUpdateListener);
        animator.addListener(animatorListener);
        if (this.mIsExpired) {
            animator.setDuration(0L);
        }
        animator.start();
        this.mIsExpired = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View root) {}

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean checkArgumentValidation() {
        char c;
        boolean isValid;
        String str = this.mAnimationType;
        switch (str.hashCode()) {
            case -1221029593:
                if (str.equals("height")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 113126854:
                if (str.equals("width")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 200299867:
                if (str.equals(ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                isValid = "int".equals(this.mValueType);
                break;
            case 1:
            case 2:
            case 3:
                isValid = true;
                break;
            default:
                isValid = false;
                break;
        }
        if (!isValid) {
            Log.e(LOG_TAG, "Illegal Argument");
        }
        return isValid;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private ValueAnimator.AnimatorUpdateListener provideAnimatorUpdateListener(
            final View targetView) {
        char c;
        String str = this.mAnimationType;
        switch (str.hashCode()) {
            case -1221029593:
                if (str.equals("height")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 113126854:
                if (str.equals("width")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 200299867:
                if (str.equals(ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (targetView instanceof ProgressBar) {
                    final ProgressBar progressBar = (ProgressBar) targetView;
                    ValueAnimator.AnimatorUpdateListener animatorUpdateListener =
                            new ValueAnimator
                                    .AnimatorUpdateListener() { // from class:
                                                                // android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda0
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    SemRemoteViewsValueAnimation
                                            .lambda$provideAnimatorUpdateListener$0(
                                                    ProgressBar.this, valueAnimator);
                                }
                            };
                    return animatorUpdateListener;
                }
                Log.e(LOG_TAG, "targetView is not ProgressBar");
                return null;
            case 1:
            case 2:
                final ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
                ValueAnimator.AnimatorUpdateListener animatorUpdateListener2 =
                        new ValueAnimator
                                .AnimatorUpdateListener() { // from class:
                                                            // android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                SemRemoteViewsValueAnimation.this
                                        .lambda$provideAnimatorUpdateListener$1(
                                                layoutParams, targetView, valueAnimator);
                            }
                        };
                return animatorUpdateListener2;
            case 3:
                if (targetView instanceof TextView) {
                    final TextView textView = (TextView) targetView;
                    ValueAnimator.AnimatorUpdateListener animatorUpdateListener3 =
                            new ValueAnimator
                                    .AnimatorUpdateListener() { // from class:
                                                                // android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda2
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    SemRemoteViewsValueAnimation.this
                                            .lambda$provideAnimatorUpdateListener$2(
                                                    textView, valueAnimator);
                                }
                            };
                    return animatorUpdateListener3;
                }
                Log.e(LOG_TAG, "targetView is not TextView");
                return null;
            default:
                return null;
        }
    }

    static /* synthetic */ void lambda$provideAnimatorUpdateListener$0(
            ProgressBar progressBar, ValueAnimator animation) {
        int value = ((Integer) animation.getAnimatedValue()).intValue();
        progressBar.setProgress(value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$provideAnimatorUpdateListener$1(
            ViewGroup.LayoutParams layoutParams, View targetView, ValueAnimator animation) {
        if (layoutParams == null) {
            return;
        }
        int value = ((Integer) animation.getAnimatedValue()).intValue();
        if (this.mAnimationType.equals("width")) {
            layoutParams.width = value;
        } else {
            layoutParams.height = value;
        }
        targetView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$provideAnimatorUpdateListener$2(
            TextView textView, ValueAnimator animation) {
        if (this.mDecimalFormat == null) {
            this.mDecimalFormat = new DecimalFormat(this.mDecimalFormatString);
        }
        if (VALUE_TYPE_FLOAT.equals(this.mValueType)) {
            float value = ((Float) animation.getAnimatedValue()).floatValue();
            if (this.mOptions != null) {
                RemoteViews.setTextWithSpannableString(
                        textView, this.mDecimalFormat.format(value), this.mOptions);
                return;
            } else {
                textView.lambda$setTextAsync$0(this.mDecimalFormat.format(value));
                return;
            }
        }
        if ("int".equals(this.mValueType)) {
            int value2 = ((Integer) animation.getAnimatedValue()).intValue();
            if (this.mOptions != null) {
                RemoteViews.setTextWithSpannableString(
                        textView, this.mDecimalFormat.format(value2), this.mOptions);
                return;
            } else {
                textView.lambda$setTextAsync$0(this.mDecimalFormat.format(value2));
                return;
            }
        }
        Log.w(LOG_TAG, "missed value type:" + this.mValueType);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Animator.AnimatorListener provideAnimatorListener(View targetView) {
        char c;
        String str = this.mAnimationType;
        switch (str.hashCode()) {
            case -1221029593:
                if (str.equals("height")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1001078227:
                if (str.equals("progress")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 113126854:
                if (str.equals("width")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 200299867:
                if (str.equals(ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                Animator.AnimatorListener animatorListener =
                        new AnimatorListenerAdapter() { // from class:
                                                        // android.widget.SemRemoteViewsValueAnimation.1
                            @Override // android.animation.AnimatorListenerAdapter,
                                      // android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                                SemRemoteViewsValueAnimation.this.mIsExpired = true;
                            }
                        };
                return animatorListener;
            default:
                return null;
        }
    }

    public void hidden_setInterpolator(int resID) {
        this.mInterpolatorResId = resID;
    }
}
