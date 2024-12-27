package android.window;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public final class OnBackInvokedCallbackInfo implements Parcelable {
    public static final Parcelable.Creator<OnBackInvokedCallbackInfo> CREATOR =
            new Parcelable.Creator<
                    OnBackInvokedCallbackInfo>() { // from class:
                                                   // android.window.OnBackInvokedCallbackInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OnBackInvokedCallbackInfo createFromParcel(Parcel in) {
                    return new OnBackInvokedCallbackInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public OnBackInvokedCallbackInfo[] newArray(int size) {
                    return new OnBackInvokedCallbackInfo[size];
                }
            };
    private final IOnBackInvokedCallback mCallback;
    private final boolean mIsAnimationCallback;
    private int mPriority;

    public OnBackInvokedCallbackInfo(
            IOnBackInvokedCallback callback, int priority, boolean isAnimationCallback) {
        this.mCallback = callback;
        this.mPriority = priority;
        this.mIsAnimationCallback = isAnimationCallback;
    }

    private OnBackInvokedCallbackInfo(Parcel in) {
        this.mCallback = IOnBackInvokedCallback.Stub.asInterface(in.readStrongBinder());
        this.mPriority = in.readInt();
        this.mIsAnimationCallback = in.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongInterface(this.mCallback);
        dest.writeInt(this.mPriority);
        dest.writeBoolean(this.mIsAnimationCallback);
    }

    public boolean isSystemCallback() {
        return this.mPriority == -1;
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

    public String toString() {
        return "OnBackInvokedCallbackInfo{mCallback="
                + this.mCallback
                + ", mPriority="
                + this.mPriority
                + ", mIsAnimationCallback="
                + this.mIsAnimationCallback
                + '}';
    }
}
