package android.window;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.view.RemoteAnimationTarget;

/* loaded from: classes4.dex */
public final class BackMotionEvent implements Parcelable {
  public static final Parcelable.Creator<BackMotionEvent> CREATOR =
      new Parcelable.Creator<BackMotionEvent>() { // from class: android.window.BackMotionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackMotionEvent createFromParcel(Parcel in) {
          return new BackMotionEvent(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackMotionEvent[] newArray(int size) {
          return new BackMotionEvent[size];
        }
      };
  private final RemoteAnimationTarget mDepartingAnimationTarget;
  private final float mProgress;
  private final int mSwipeEdge;
  private final float mTouchX;
  private final float mTouchY;
  private final float mVelocityX;
  private final float mVelocityY;

  public BackMotionEvent(
      float touchX,
      float touchY,
      float progress,
      float velocityX,
      float velocityY,
      int swipeEdge,
      RemoteAnimationTarget departingAnimationTarget) {
    this.mTouchX = touchX;
    this.mTouchY = touchY;
    this.mProgress = progress;
    this.mVelocityX = velocityX;
    this.mVelocityY = velocityY;
    this.mSwipeEdge = swipeEdge;
    this.mDepartingAnimationTarget = departingAnimationTarget;
  }

  private BackMotionEvent(Parcel in) {
    this.mTouchX = in.readFloat();
    this.mTouchY = in.readFloat();
    this.mProgress = in.readFloat();
    this.mVelocityX = in.readFloat();
    this.mVelocityY = in.readFloat();
    this.mSwipeEdge = in.readInt();
    this.mDepartingAnimationTarget =
        (RemoteAnimationTarget) in.readTypedObject(RemoteAnimationTarget.CREATOR);
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeFloat(this.mTouchX);
    dest.writeFloat(this.mTouchY);
    dest.writeFloat(this.mProgress);
    dest.writeFloat(this.mVelocityX);
    dest.writeFloat(this.mVelocityY);
    dest.writeInt(this.mSwipeEdge);
    dest.writeTypedObject(this.mDepartingAnimationTarget, flags);
  }

  public float getTouchX() {
    return this.mTouchX;
  }

  public float getTouchY() {
    return this.mTouchY;
  }

  public float getProgress() {
    return this.mProgress;
  }

  public float getVelocityX() {
    return this.mVelocityX;
  }

  public float getVelocityY() {
    return this.mVelocityY;
  }

  public int getSwipeEdge() {
    return this.mSwipeEdge;
  }

  public RemoteAnimationTarget getDepartingAnimationTarget() {
    return this.mDepartingAnimationTarget;
  }

  public String toString() {
    return "BackMotionEvent{mTouchX="
        + this.mTouchX
        + ", mTouchY="
        + this.mTouchY
        + ", mProgress="
        + this.mProgress
        + ", mVelocityX="
        + this.mVelocityX
        + ", mVelocityY="
        + this.mVelocityY
        + ", mSwipeEdge"
        + this.mSwipeEdge
        + ", mDepartingAnimationTarget"
        + this.mDepartingAnimationTarget
        + "}";
  }
}
