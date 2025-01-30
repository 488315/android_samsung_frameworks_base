package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes4.dex */
public class SemRemoteViewsValueAnimation extends SemRemoteViewsAnimation {
  private static final String ANIMATION_TYPE_LAYOUT_PARAMS_WIDTH = "width";
  public static final String ANIMATION_TYPE_PROGRESS = "progress";
  public static final Parcelable.Creator<SemRemoteViewsValueAnimation> CREATOR =
      new Parcelable.Creator<
          SemRemoteViewsValueAnimation>() { // from class:
                                            // android.widget.SemRemoteViewsValueAnimation.3
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
  private final long mDuration;
  private final float mFloatValueFrom;
  private final float mFloatValueTo;
  private final int mIntValueFrom;
  private final int mIntValueTo;
  private final boolean mIsValidArgument;
  private final String mValueType;

  public SemRemoteViewsValueAnimation(
      int viewId,
      String animationType,
      String valueType,
      float valueFrom,
      float ValueTo,
      long duration) {
    super(viewId);
    this.mAnimationType = animationType;
    this.mValueType = valueType;
    this.mFloatValueFrom = valueFrom;
    this.mFloatValueTo = ValueTo;
    this.mIntValueFrom = 0;
    this.mIntValueTo = 0;
    this.mDuration = duration;
    this.mIsValidArgument = checkArgumentValidation();
  }

  public SemRemoteViewsValueAnimation(
      int viewId,
      String animationType,
      String valueType,
      int valueFrom,
      int valueTo,
      long duration) {
    super(viewId);
    this.mAnimationType = animationType;
    this.mValueType = valueType;
    this.mFloatValueFrom = 0.0f;
    this.mFloatValueTo = 0.0f;
    this.mIntValueFrom = valueFrom;
    this.mIntValueTo = valueTo;
    this.mDuration = duration;
    this.mIsValidArgument = checkArgumentValidation();
  }

  protected SemRemoteViewsValueAnimation(Parcel in) {
    super(in);
    this.mAnimationType = in.readString();
    this.mValueType = in.readString();
    this.mFloatValueFrom = in.readFloat();
    this.mFloatValueTo = in.readFloat();
    this.mIntValueFrom = in.readInt();
    this.mIntValueTo = in.readInt();
    this.mDuration = in.readLong();
    this.mIsValidArgument = in.readBoolean();
  }

  @Override // android.widget.SemRemoteViewsAnimation, android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.widget.SemRemoteViewsAnimation, android.p009os.Parcelable
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
  }

  public static void writeToParcel(SemRemoteViewsValueAnimation c, Parcel out) {
    if (c != null) {
      c.writeToParcel(out, 0);
    } else {
      out.writeString(null);
    }
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  @Override // android.widget.SemRemoteViewsAnimation
  protected void startAnimation(View root) {
    View targetView;
    char c;
    Animator.AnimatorListener animatorListener;
    if (!this.mIsValidArgument) {
      Log.m96e(LOG_TAG, "Illegal Argument");
      return;
    }
    if (root == null || (targetView = root.findViewById(this.mViewId)) == null) {
      return;
    }
    ValueAnimator animator = new ValueAnimator();
    animator.setDuration(this.mDuration);
    String str = this.mValueType;
    switch (str.hashCode()) {
      case 104431:
        if (str.equals("int")) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case 94842723:
        if (str.equals("color")) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case 97526364:
        if (str.equals(VALUE_TYPE_FLOAT)) {
          c = 0;
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
        animator.setEvaluator(new FloatEvaluator());
        animator.setFloatValues(this.mFloatValueFrom, this.mFloatValueTo);
        break;
      case 1:
        animator.setEvaluator(new IntEvaluator());
        animator.setIntValues(this.mIntValueFrom, this.mIntValueTo);
        break;
      case 2:
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
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
        isValid = "int".equals(this.mValueType);
        break;
      case 1:
        isValid = true;
        break;
      default:
        isValid = false;
        break;
    }
    if (!isValid) {
      Log.m96e(LOG_TAG, "Illegal Argument");
    }
    return isValid;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  private ValueAnimator.AnimatorUpdateListener provideAnimatorUpdateListener(
      final View targetView) {
    char c;
    String str = this.mAnimationType;
    switch (str.hashCode()) {
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
                  SemRemoteViewsValueAnimation.lambda$provideAnimatorUpdateListener$0(
                      ProgressBar.this, valueAnimator);
                }
              };
          return animatorUpdateListener;
        }
        Log.m96e(LOG_TAG, "targetView is not ProgressBar");
        return null;
      case 1:
        final ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener2 =
            new ValueAnimator
                .AnimatorUpdateListener() { // from class:
                                            // android.widget.SemRemoteViewsValueAnimation$$ExternalSyntheticLambda1
              @Override // android.animation.ValueAnimator.AnimatorUpdateListener
              public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SemRemoteViewsValueAnimation.lambda$provideAnimatorUpdateListener$1(
                    ViewGroup.LayoutParams.this, targetView, valueAnimator);
              }
            };
        return animatorUpdateListener2;
      default:
        return null;
    }
  }

  static /* synthetic */ void lambda$provideAnimatorUpdateListener$0(
      ProgressBar progressBar, ValueAnimator animation) {
    int value = ((Integer) animation.getAnimatedValue()).intValue();
    progressBar.setProgress(value);
  }

  static /* synthetic */ void lambda$provideAnimatorUpdateListener$1(
      ViewGroup.LayoutParams layoutParams, View targetView, ValueAnimator animation) {
    if (layoutParams == null) {
      return;
    }
    int value = ((Integer) animation.getAnimatedValue()).intValue();
    layoutParams.width = value;
    targetView.setLayoutParams(layoutParams);
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  private Animator.AnimatorListener provideAnimatorListener(View targetView) {
    char c;
    String str = this.mAnimationType;
    switch (str.hashCode()) {
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
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
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
      case 1:
        Animator.AnimatorListener animatorListener2 =
            new AnimatorListenerAdapter() { // from class:
                                            // android.widget.SemRemoteViewsValueAnimation.2
              @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
              public void onAnimationEnd(Animator animation) {
                SemRemoteViewsValueAnimation.this.mIsExpired = true;
              }
            };
        return animatorListener2;
      default:
        return null;
    }
  }
}
