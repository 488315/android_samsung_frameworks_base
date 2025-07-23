package android.window;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.RemoteAnimationTarget;

/* loaded from: classes5.dex */
public final class BackMotionEvent implements Parcelable {
    public static final Parcelable.Creator<BackMotionEvent> CREATOR = new Parcelable.Creator<BackMotionEvent>() { // from class: android.window.BackMotionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackMotionEvent createFromParcel(Parcel parcel) {
            return new BackMotionEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackMotionEvent[] newArray(int i) {
            return new BackMotionEvent[i];
        }
    };
    private final RemoteAnimationTarget mDepartingAnimationTarget;
    private final long mFrameTimeMillis;
    private final float mProgress;
    private final int mSwipeEdge;
    private final float mTouchX;
    private final float mTouchY;
    private final boolean mTriggerBack;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BackMotionEvent(float f, float f2, long j, float f3, boolean z, int i, RemoteAnimationTarget remoteAnimationTarget) {
        this.mTouchX = f;
        this.mTouchY = f2;
        this.mFrameTimeMillis = j;
        this.mProgress = f3;
        this.mTriggerBack = z;
        this.mSwipeEdge = i;
        this.mDepartingAnimationTarget = remoteAnimationTarget;
    }

    private BackMotionEvent(Parcel parcel) {
        this.mTouchX = parcel.readFloat();
        this.mTouchY = parcel.readFloat();
        this.mProgress = parcel.readFloat();
        this.mTriggerBack = parcel.readBoolean();
        this.mSwipeEdge = parcel.readInt();
        this.mDepartingAnimationTarget = (RemoteAnimationTarget) parcel.readTypedObject(RemoteAnimationTarget.CREATOR);
        this.mFrameTimeMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mTouchX);
        parcel.writeFloat(this.mTouchY);
        parcel.writeFloat(this.mProgress);
        parcel.writeBoolean(this.mTriggerBack);
        parcel.writeInt(this.mSwipeEdge);
        parcel.writeTypedObject(this.mDepartingAnimationTarget, i);
        parcel.writeLong(this.mFrameTimeMillis);
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

    public boolean getTriggerBack() {
        return this.mTriggerBack;
    }

    public int getSwipeEdge() {
        return this.mSwipeEdge;
    }

    public long getFrameTimeMillis() {
        return this.mFrameTimeMillis;
    }

    public RemoteAnimationTarget getDepartingAnimationTarget() {
        return this.mDepartingAnimationTarget;
    }

    public String toString() {
        return "BackMotionEvent{mTouchX=" + this.mTouchX + ", mTouchY=" + this.mTouchY + ", mFrameTimeMillis=" + this.mFrameTimeMillis + ", mProgress=" + this.mProgress + ", mTriggerBack=" + this.mTriggerBack + ", mSwipeEdge=" + this.mSwipeEdge + ", mDepartingAnimationTarget=" + this.mDepartingAnimationTarget + "}";
    }
}
