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

/* loaded from: classes5.dex */
public class SemRemoteViewsValueAnimation extends SemRemoteViewsAnimation {
    private static final String ANIMATION_TYPE_LAYOUT_PARAMS_HEIGHT = "height";
    private static final String ANIMATION_TYPE_LAYOUT_PARAMS_WIDTH = "width";
    public static final String ANIMATION_TYPE_PROGRESS = "progress";
    private static final String ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT = "decimal_text";
    private static final String ANIMATION_TYPE_VIEW_ALPHA = "alpha";
    public static final Parcelable.Creator<SemRemoteViewsValueAnimation> CREATOR = new Parcelable.Creator<SemRemoteViewsValueAnimation>() { // from class: android.widget.SemRemoteViewsValueAnimation.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsValueAnimation createFromParcel(Parcel parcel) {
            return new SemRemoteViewsValueAnimation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsValueAnimation[] newArray(int i) {
            return new SemRemoteViewsValueAnimation[i];
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

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View view) {
    }

    public SemRemoteViewsValueAnimation(int i, String str, String str2, float f, float f2, long j) {
        this(i, str, str2, f, f2, j, (Bundle) null);
    }

    SemRemoteViewsValueAnimation(int i, String str, String str2, float f, float f2, long j, Bundle bundle) {
        super(i);
        this.mInterpolatorResId = -1;
        this.mAnimationType = str;
        if (ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT.equals(str)) {
            this.mValueType = VALUE_TYPE_FLOAT;
            this.mDecimalFormatString = str2;
        } else {
            this.mValueType = str2;
        }
        this.mFloatValueFrom = f;
        this.mFloatValueTo = f2;
        this.mIntValueFrom = 0;
        this.mIntValueTo = 0;
        this.mDuration = j;
        this.mIsValidArgument = checkArgumentValidation();
        this.mOptions = bundle;
    }

    public SemRemoteViewsValueAnimation(int i, String str, String str2, int i2, int i3, long j) {
        this(i, str, str2, i2, i3, j, (Bundle) null);
    }

    SemRemoteViewsValueAnimation(int i, String str, String str2, int i2, int i3, long j, Bundle bundle) {
        super(i);
        this.mInterpolatorResId = -1;
        this.mAnimationType = str;
        if (ANIMATION_TYPE_TEXTVIEW_DECIMAL_TEXT.equals(str)) {
            this.mValueType = "int";
            this.mDecimalFormatString = str2;
        } else {
            this.mValueType = str2;
        }
        this.mFloatValueFrom = 0.0f;
        this.mFloatValueTo = 0.0f;
        this.mIntValueFrom = i2;
        this.mIntValueTo = i3;
        this.mDuration = j;
        this.mIsValidArgument = checkArgumentValidation();
        this.mOptions = bundle;
    }

    protected SemRemoteViewsValueAnimation(Parcel parcel) {
        super(parcel);
        this.mInterpolatorResId = -1;
        this.mAnimationType = parcel.readString();
        this.mValueType = parcel.readString();
        this.mFloatValueFrom = parcel.readFloat();
        this.mFloatValueTo = parcel.readFloat();
        this.mIntValueFrom = parcel.readInt();
        this.mIntValueTo = parcel.readInt();
        this.mDuration = parcel.readLong();
        this.mIsValidArgument = parcel.readBoolean();
        this.mDecimalFormatString = parcel.readString();
        this.mInterpolatorResId = parcel.readInt();
        this.mOptions = parcel.readBundle();
    }

    public void semSetInterpolator(int i) {
        this.mInterpolatorResId = i;
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mAnimationType);
        parcel.writeString(this.mValueType);
        parcel.writeFloat(this.mFloatValueFrom);
        parcel.writeFloat(this.mFloatValueTo);
        parcel.writeInt(this.mIntValueFrom);
        parcel.writeInt(this.mIntValueTo);
        parcel.writeLong(this.mDuration);
        parcel.writeBoolean(this.mIsValidArgument);
        parcel.writeString(this.mDecimalFormatString);
        parcel.writeInt(this.mInterpolatorResId);
        parcel.writeBundle(this.mOptions);
    }

    public static void writeToParcel(SemRemoteViewsValueAnimation semRemoteViewsValueAnimation, Parcel parcel) {
        if (semRemoteViewsValueAnimation != null) {
            semRemoteViewsValueAnimation.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View view) {
        View findViewById;
        ValueAnimator valueAnimator;
        Animator.AnimatorListener provideAnimatorListener;
        Context context;
        if (!this.mIsValidArgument) {
            Log.e(LOG_TAG, "Illegal Argument");
            return;
        }
        if (view == null || (findViewById = view.findViewById(this.mViewId)) == null) {
            return;
        }
        valueAnimator = new ValueAnimator();
        if (this.mInterpolatorResId != -1) {
            if (this.mInterpolator == null && (context = findViewById.getContext()) != null) {
                this.mInterpolator = AnimationUtils.loadInterpolator(context, this.mInterpolatorResId);
            }
            valueAnimator.setInterpolator(this.mInterpolator);
        }
        valueAnimator.setDuration(this.mDuration);
        String str = this.mValueType;
        str.hashCode();
        switch (str) {
            case "int":
                valueAnimator.setEvaluator(new IntEvaluator());
                valueAnimator.setIntValues(this.mIntValueFrom, this.mIntValueTo);
                break;
            case "color":
                valueAnimator.setEvaluator(new ArgbEvaluator());
                valueAnimator.setIntValues(this.mIntValueFrom, this.mIntValueTo);
                break;
            case "float":
                valueAnimator.setEvaluator(new FloatEvaluator());
                valueAnimator.setFloatValues(this.mFloatValueFrom, this.mFloatValueTo);
                break;
            default:
                return;
        }
        ValueAnimator.AnimatorUpdateListener provideAnimatorUpdateListener = provideAnimatorUpdateListener(findViewById);
        if (provideAnimatorUpdateListener == null || (provideAnimatorListener = provideAnimatorListener(findViewById)) == null) {
            return;
        }
        valueAnimator.addUpdateListener(provideAnimatorUpdateListener);
        valueAnimator.addListener(provideAnimatorListener);
        if (this.mIsExpired) {
            valueAnimator.setDuration(0L);
        }
        valueAnimator.start();
        this.mIsExpired = true;
    }

    private boolean checkArgumentValidation() {
        boolean z;
        String str = this.mAnimationType;
        str.hashCode();
        z = true;
        switch (str) {
            case "height":
            case "alpha":
            case "width":
            case "decimal_text":
                break;
            case "progress":
                z = "int".equals(this.mValueType);
                break;
            default:
                z = false;
                break;
        }
        if (!z) {
            Log.e(LOG_TAG, "Illegal Argument");
        }
        return z;
    }

    private ValueAnimator.AnimatorUpdateListener provideAnimatorUpdateListener(final View view) {
        String str = this.mAnimationType;
        str.hashCode();
        switch (str) {
            case "height":
            case "width":
                final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                return new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SemRemoteViewsValueAnimation.this.lambda$provideAnimatorUpdateListener$1(layoutParams, view, valueAnimator);
                    }
                };
            case "progress":
                if (view instanceof ProgressBar) {
                    final ProgressBar progressBar = (ProgressBar) view;
                    return new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            ProgressBar.this.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        }
                    };
                }
                Log.e(LOG_TAG, "targetView is not ProgressBar");
                return null;
            case "alpha":
                return new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        View.this.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                };
            case "decimal_text":
                if (view instanceof TextView) {
                    final TextView textView = (TextView) view;
                    return new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            SemRemoteViewsValueAnimation.this.lambda$provideAnimatorUpdateListener$3(textView, valueAnimator);
                        }
                    };
                }
                Log.e(LOG_TAG, "targetView is not TextView");
                return null;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$provideAnimatorUpdateListener$1(ViewGroup.LayoutParams layoutParams, View view, ValueAnimator valueAnimator) {
        if (layoutParams == null) {
            return;
        }
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (this.mAnimationType.equals("width")) {
            layoutParams.width = intValue;
        } else {
            layoutParams.height = intValue;
        }
        view.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$provideAnimatorUpdateListener$3(TextView textView, ValueAnimator valueAnimator) {
        if (this.mDecimalFormat == null) {
            this.mDecimalFormat = new DecimalFormat(this.mDecimalFormatString);
        }
        if (VALUE_TYPE_FLOAT.equals(this.mValueType)) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            if (this.mOptions != null) {
                RemoteViews.setTextWithSpannableString(textView, this.mDecimalFormat.format(floatValue), this.mOptions);
                return;
            } else {
                textView.lambda$setTextAsync$0(this.mDecimalFormat.format(floatValue));
                return;
            }
        }
        if ("int".equals(this.mValueType)) {
            int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            if (this.mOptions != null) {
                RemoteViews.setTextWithSpannableString(textView, this.mDecimalFormat.format(intValue), this.mOptions);
                return;
            } else {
                textView.lambda$setTextAsync$0(this.mDecimalFormat.format(intValue));
                return;
            }
        }
        Log.w(LOG_TAG, "missed value type:" + this.mValueType);
    }

    private Animator.AnimatorListener provideAnimatorListener(View view) {
        String str = this.mAnimationType;
        str.hashCode();
        switch (str) {
            case "height":
            case "progress":
            case "alpha":
            case "width":
            case "decimal_text":
                return new AnimatorListenerAdapter() { // from class: android.widget.SemRemoteViewsValueAnimation.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SemRemoteViewsValueAnimation.this.mIsExpired = true;
                    }
                };
            default:
                return null;
        }
    }

    public void hidden_setInterpolator(int i) {
        this.mInterpolatorResId = i;
    }
}
