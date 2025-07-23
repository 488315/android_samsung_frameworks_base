package android.window;

import android.os.Parcel;
import android.os.Parcelable;
import android.window.IOnBackInvokedCallback;

/* loaded from: classes5.dex */
public final class OnBackInvokedCallbackInfo implements Parcelable {
    public static final Parcelable.Creator<OnBackInvokedCallbackInfo> CREATOR = new Parcelable.Creator<OnBackInvokedCallbackInfo>() { // from class: android.window.OnBackInvokedCallbackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OnBackInvokedCallbackInfo createFromParcel(Parcel parcel) {
            return new OnBackInvokedCallbackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OnBackInvokedCallbackInfo[] newArray(int i) {
            return new OnBackInvokedCallbackInfo[i];
        }
    };
    private final IOnBackInvokedCallback mCallback;
    private final boolean mIsAnimationCallback;
    private final int mOverrideBehavior;
    private int mPriority;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OnBackInvokedCallbackInfo(IOnBackInvokedCallback iOnBackInvokedCallback, int i, boolean z, int i2) {
        this.mCallback = iOnBackInvokedCallback;
        this.mPriority = i;
        this.mIsAnimationCallback = z;
        this.mOverrideBehavior = i2;
    }

    private OnBackInvokedCallbackInfo(Parcel parcel) {
        this.mCallback = IOnBackInvokedCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mPriority = parcel.readInt();
        this.mIsAnimationCallback = parcel.readBoolean();
        this.mOverrideBehavior = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mCallback);
        parcel.writeInt(this.mPriority);
        parcel.writeBoolean(this.mIsAnimationCallback);
        parcel.writeInt(this.mOverrideBehavior);
    }

    public boolean isSystemCallback() {
        return this.mPriority == -1 || this.mOverrideBehavior != 0;
    }

    public IOnBackInvokedCallback getCallback() {
        return this.mCallback;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public boolean isAnimationCallback() {
        return this.mIsAnimationCallback;
    }

    public int getOverrideBehavior() {
        return this.mOverrideBehavior;
    }

    public String toString() {
        return "OnBackInvokedCallbackInfo{mCallback=" + this.mCallback + ", mPriority=" + this.mPriority + ", mIsAnimationCallback=" + this.mIsAnimationCallback + ", mOverrideBehavior=" + this.mOverrideBehavior + '}';
    }
}
