package android.widget;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.view.View;

/* loaded from: classes4.dex */
public class SemRemoteViewsDrawableAnimation extends SemRemoteViewsAnimation {
  public static final Parcelable.Creator<SemRemoteViewsDrawableAnimation> CREATOR =
      new Parcelable.Creator<
          SemRemoteViewsDrawableAnimation>() { // from class:
                                               // android.widget.SemRemoteViewsDrawableAnimation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsDrawableAnimation createFromParcel(Parcel in) {
          return new SemRemoteViewsDrawableAnimation(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsDrawableAnimation[] newArray(int size) {
          return new SemRemoteViewsDrawableAnimation[size];
        }
      };
  private static final String LOG_TAG = "SemRemoteViewsDrawableAnimation";
  private Animatable2 mAnimatableDrawable;
  private final boolean mNeedToStart;
  private final int mResId;

  public SemRemoteViewsDrawableAnimation(int viewId, boolean needToStart) {
    super(viewId);
    this.mNeedToStart = needToStart;
    this.mResId = 0;
  }

  public SemRemoteViewsDrawableAnimation(int viewId, boolean needToStart, int resId) {
    super(viewId);
    this.mNeedToStart = needToStart;
    this.mResId = resId;
  }

  protected SemRemoteViewsDrawableAnimation(Parcel parcel) {
    super(parcel);
    this.mNeedToStart = parcel.readBoolean();
    this.mResId = parcel.readInt();
  }

  @Override // android.widget.SemRemoteViewsAnimation, android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeBoolean(this.mNeedToStart);
    dest.writeInt(this.mResId);
  }

  public static void writeToParcel(SemRemoteViewsDrawableAnimation c, Parcel out) {
    if (c != null) {
      c.writeToParcel(out, 0);
    } else {
      out.writeString(null);
    }
  }

  @Override // android.widget.SemRemoteViewsAnimation, android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.widget.SemRemoteViewsAnimation
  protected void startAnimation(View root) {
    if (this.mIsExpired) {
      return;
    }
    View target = root.findViewById(this.mViewId);
    if (target instanceof ImageView) {
      ImageView imageView = (ImageView) target;
      int i = this.mResId;
      if (i > 0) {
        imageView.setImageResource(i);
      }
      Object drawable = imageView.getDrawable();
      if ((drawable instanceof AnimatedImageDrawable)
          || (drawable instanceof AnimatedVectorDrawable)) {
        Animatable2 animatable2 = (Animatable2) drawable;
        this.mAnimatableDrawable = animatable2;
        if (this.mNeedToStart) {
          animatable2.start();
          this.mIsExpired = true;
        } else {
          animatable2.stop();
        }
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: protected */
  @Override // android.widget.SemRemoteViewsAnimation
  /* renamed from: endAnimation */
  public void lambda$play$0(View root) {}
}
