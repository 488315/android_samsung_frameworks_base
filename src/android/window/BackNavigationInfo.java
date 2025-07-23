package android.window;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.window.IOnBackInvokedCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public final class BackNavigationInfo implements Parcelable {
    public static final Parcelable.Creator<BackNavigationInfo> CREATOR = new Parcelable.Creator<BackNavigationInfo>() { // from class: android.window.BackNavigationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackNavigationInfo createFromParcel(Parcel parcel) {
            return new BackNavigationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackNavigationInfo[] newArray(int i) {
            return new BackNavigationInfo[i];
        }
    };
    public static final String KEY_DISPLAY_ID = "DisplayId";
    public static final String KEY_GESTURE_FINISHED = "GestureFinished";
    public static final String KEY_NAVIGATION_FINISHED = "NavigationFinished";
    public static final String KEY_TOUCH_GESTURE_TRANSFERRED = "TouchGestureTransferred";
    public static final int TYPE_CALLBACK = 4;
    public static final int TYPE_CROSS_ACTIVITY = 2;
    public static final int TYPE_CROSS_TASK = 3;
    public static final int TYPE_DIALOG_CLOSE = 0;
    public static final int TYPE_RETURN_TO_HOME = 1;
    public static final int TYPE_UNDEFINED = -1;
    private final boolean mAnimationCallback;
    private boolean mAppProgressGenerationAllowed;
    private final CustomAnimationInfo mCustomAnimationInfo;
    private final int mFocusedTaskId;
    private final int mLetterboxColor;
    private final IOnBackInvokedCallback mOnBackInvokedCallback;
    private final RemoteCallback mOnBackNavigationDone;
    private final boolean mPrepareRemoteAnimation;
    private final Rect mTouchableRegion;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackTargetType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BackNavigationInfo(int i, RemoteCallback remoteCallback, IOnBackInvokedCallback iOnBackInvokedCallback, boolean z, boolean z2, CustomAnimationInfo customAnimationInfo, int i2, Rect rect, boolean z3, int i3) {
        this.mType = i;
        this.mOnBackNavigationDone = remoteCallback;
        this.mOnBackInvokedCallback = iOnBackInvokedCallback;
        this.mPrepareRemoteAnimation = z;
        this.mAnimationCallback = z2;
        this.mCustomAnimationInfo = customAnimationInfo;
        this.mLetterboxColor = i2;
        this.mTouchableRegion = new Rect(rect);
        this.mAppProgressGenerationAllowed = z3;
        this.mFocusedTaskId = i3;
    }

    private BackNavigationInfo(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mOnBackNavigationDone = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
        this.mOnBackInvokedCallback = IOnBackInvokedCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mPrepareRemoteAnimation = parcel.readBoolean();
        this.mAnimationCallback = parcel.readBoolean();
        this.mCustomAnimationInfo = (CustomAnimationInfo) parcel.readTypedObject(CustomAnimationInfo.CREATOR);
        this.mLetterboxColor = parcel.readInt();
        this.mTouchableRegion = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mAppProgressGenerationAllowed = parcel.readBoolean();
        this.mFocusedTaskId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeTypedObject(this.mOnBackNavigationDone, i);
        parcel.writeStrongInterface(this.mOnBackInvokedCallback);
        parcel.writeBoolean(this.mPrepareRemoteAnimation);
        parcel.writeBoolean(this.mAnimationCallback);
        parcel.writeTypedObject(this.mCustomAnimationInfo, i);
        parcel.writeInt(this.mLetterboxColor);
        parcel.writeTypedObject(this.mTouchableRegion, i);
        parcel.writeBoolean(this.mAppProgressGenerationAllowed);
        parcel.writeInt(this.mFocusedTaskId);
    }

    public int getType() {
        return this.mType;
    }

    public IOnBackInvokedCallback getOnBackInvokedCallback() {
        return this.mOnBackInvokedCallback;
    }

    public boolean isPrepareRemoteAnimation() {
        return this.mPrepareRemoteAnimation;
    }

    public boolean isAnimationCallback() {
        return this.mAnimationCallback;
    }

    public int getLetterboxColor() {
        return this.mLetterboxColor;
    }

    public Rect getTouchableRegion() {
        return this.mTouchableRegion;
    }

    public boolean isAppProgressGenerationAllowed() {
        return this.mAppProgressGenerationAllowed;
    }

    public int getFocusedTaskId() {
        return this.mFocusedTaskId;
    }

    public void disableAppProgressGenerationAllowed() {
        this.mAppProgressGenerationAllowed = false;
    }

    public void onBackNavigationFinished(boolean z) {
        if (this.mOnBackNavigationDone != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY_NAVIGATION_FINISHED, z);
            this.mOnBackNavigationDone.sendResult(bundle);
        }
    }

    public CustomAnimationInfo getCustomAnimationInfo() {
        return this.mCustomAnimationInfo;
    }

    public String toString() {
        return "BackNavigationInfo{mType=" + typeToString(this.mType) + " (" + this.mType + "), mOnBackNavigationDone=" + this.mOnBackNavigationDone + ", mOnBackInvokedCallback=" + this.mOnBackInvokedCallback + ", mPrepareRemoteAnimation=" + this.mPrepareRemoteAnimation + ", mAnimationCallback=" + this.mAnimationCallback + ", mCustomizeAnimationInfo=" + this.mCustomAnimationInfo + '}';
    }

    public static String typeToString(int i) {
        if (i == -1) {
            return "TYPE_UNDEFINED";
        }
        if (i == 0) {
            return "TYPE_DIALOG_CLOSE";
        }
        if (i == 1) {
            return "TYPE_RETURN_TO_HOME";
        }
        if (i == 2) {
            return "TYPE_CROSS_ACTIVITY";
        }
        if (i == 3) {
            return "TYPE_CROSS_TASK";
        }
        if (i == 4) {
            return "TYPE_CALLBACK";
        }
        return String.valueOf(i);
    }

    public static final class CustomAnimationInfo implements Parcelable {
        public static final Parcelable.Creator<CustomAnimationInfo> CREATOR = new Parcelable.Creator<CustomAnimationInfo>() { // from class: android.window.BackNavigationInfo.CustomAnimationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAnimationInfo createFromParcel(Parcel parcel) {
                return new CustomAnimationInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAnimationInfo[] newArray(int i) {
                return new CustomAnimationInfo[i];
            }
        };
        private int mCustomBackground;
        private int mCustomEnterAnim;
        private int mCustomExitAnim;
        private final String mPackageName;
        private int mWindowAnimations;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getWindowAnimations() {
            return this.mWindowAnimations;
        }

        public int getCustomExitAnim() {
            return this.mCustomExitAnim;
        }

        public int getCustomEnterAnim() {
            return this.mCustomEnterAnim;
        }

        public int getCustomBackground() {
            return this.mCustomBackground;
        }

        public CustomAnimationInfo(String str) {
            this.mPackageName = str;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mPackageName);
            parcel.writeInt(this.mWindowAnimations);
            parcel.writeInt(this.mCustomEnterAnim);
            parcel.writeInt(this.mCustomExitAnim);
            parcel.writeInt(this.mCustomBackground);
        }

        private CustomAnimationInfo(Parcel parcel) {
            this.mPackageName = parcel.readString8();
            this.mWindowAnimations = parcel.readInt();
            this.mCustomEnterAnim = parcel.readInt();
            this.mCustomExitAnim = parcel.readInt();
            this.mCustomBackground = parcel.readInt();
        }

        public String toString() {
            return "CustomAnimationInfo, package name= " + this.mPackageName;
        }
    }

    public static class Builder {
        private boolean mAppProgressGenerationAllowed;
        private CustomAnimationInfo mCustomAnimationInfo;
        private boolean mPrepareRemoteAnimation;
        private Rect mTouchableRegion;
        private int mType = -1;
        private RemoteCallback mOnBackNavigationDone = null;
        private IOnBackInvokedCallback mOnBackInvokedCallback = null;
        private boolean mAnimationCallback = false;
        private int mLetterboxColor = 0;
        private int mFocusedTaskId = -1;

        public Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public Builder setOnBackNavigationDone(RemoteCallback remoteCallback) {
            this.mOnBackNavigationDone = remoteCallback;
            return this;
        }

        public Builder setOnBackInvokedCallback(IOnBackInvokedCallback iOnBackInvokedCallback) {
            this.mOnBackInvokedCallback = iOnBackInvokedCallback;
            return this;
        }

        public Builder setPrepareRemoteAnimation(boolean z) {
            this.mPrepareRemoteAnimation = z;
            return this;
        }

        public Builder setWindowAnimations(String str, int i) {
            if (this.mCustomAnimationInfo == null) {
                this.mCustomAnimationInfo = new CustomAnimationInfo(str);
            }
            this.mCustomAnimationInfo.mWindowAnimations = i;
            return this;
        }

        public Builder setCustomAnimation(String str, int i, int i2, int i3) {
            if (this.mCustomAnimationInfo == null) {
                this.mCustomAnimationInfo = new CustomAnimationInfo(str);
            }
            this.mCustomAnimationInfo.mCustomExitAnim = i2;
            this.mCustomAnimationInfo.mCustomEnterAnim = i;
            this.mCustomAnimationInfo.mCustomBackground = i3;
            return this;
        }

        public Builder setAnimationCallback(boolean z) {
            this.mAnimationCallback = z;
            return this;
        }

        public Builder setLetterboxColor(int i) {
            this.mLetterboxColor = i;
            return this;
        }

        public Builder setTouchableRegion(Rect rect) {
            this.mTouchableRegion = new Rect(rect);
            return this;
        }

        public Builder setAppProgressAllowed(boolean z) {
            this.mAppProgressGenerationAllowed = z;
            return this;
        }

        public Builder setFocusedTaskId(int i) {
            this.mFocusedTaskId = i;
            return this;
        }

        public BackNavigationInfo build() {
            return new BackNavigationInfo(this.mType, this.mOnBackNavigationDone, this.mOnBackInvokedCallback, this.mPrepareRemoteAnimation, this.mAnimationCallback, this.mCustomAnimationInfo, this.mLetterboxColor, this.mTouchableRegion, this.mAppProgressGenerationAllowed, this.mFocusedTaskId);
        }
    }
}
