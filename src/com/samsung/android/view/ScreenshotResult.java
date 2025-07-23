package com.samsung.android.view;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class ScreenshotResult implements Parcelable {
    public static final Parcelable.Creator<ScreenshotResult> CREATOR = new Parcelable.Creator<ScreenshotResult>() { // from class: com.samsung.android.view.ScreenshotResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScreenshotResult createFromParcel(Parcel parcel) {
            return new ScreenshotResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ScreenshotResult[] newArray(int i) {
            return new ScreenshotResult[i];
        }
    };
    public static final int FAIL_REASON_EMPTY_BITMAP = 8;
    public static final int FAIL_REASON_INVALID_DEFAULT_TASK_DISPLAY_AREA = 4;
    public static final int FAIL_REASON_INVALID_DISPLAY = 1;
    public static final int FAIL_REASON_INVALID_SYSTEM_WINDOW = 2;
    public static final int FAIL_REASON_SECURE_POLICY_BY_MDM = 32;
    public static final int FAIL_REASON_SECURE_POLICY_BY_SECURE_FLAGS = 16;
    private Bitmap mCapturedBitmap;
    private int mFailedReason;
    private String mSecuredWindowName;
    private String mTargetWindowName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ScreenshotResult(Bitmap bitmap, int i, String str, String str2) {
        this.mCapturedBitmap = bitmap;
        this.mFailedReason = i;
        this.mTargetWindowName = str;
        this.mSecuredWindowName = str2;
    }

    private ScreenshotResult(Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.mCapturedBitmap = Bitmap.CREATOR.createFromParcel(parcel);
        } else {
            this.mCapturedBitmap = null;
        }
        this.mFailedReason = parcel.readInt();
        this.mTargetWindowName = parcel.readString();
        this.mSecuredWindowName = parcel.readString();
    }

    public Bitmap getCapturedBitmap() {
        return this.mCapturedBitmap;
    }

    public int getFailedReason() {
        return this.mFailedReason;
    }

    public String getTargetWindowName() {
        return this.mTargetWindowName;
    }

    public String getSecuredWindowName() {
        return this.mSecuredWindowName;
    }

    public static class Builder {
        private Bitmap mCapturedBitmap;
        private int mFailedReason;
        private String mSecuredWindowName;
        private String mTargetWindowName;

        public ScreenshotResult build() {
            return new ScreenshotResult(this.mCapturedBitmap, this.mFailedReason, this.mTargetWindowName, this.mSecuredWindowName);
        }

        public Builder setCapturedBitmap(Bitmap bitmap) {
            this.mCapturedBitmap = bitmap;
            return this;
        }

        public Builder setFailedReason(int i) {
            this.mFailedReason = i;
            return this;
        }

        public Builder setTargetWindowName(String str) {
            this.mTargetWindowName = str;
            return this;
        }

        public Builder setSecuredWindowName(String str) {
            this.mSecuredWindowName = str;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mCapturedBitmap != null) {
            parcel.writeInt(1);
            this.mCapturedBitmap.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mFailedReason);
        parcel.writeString(this.mTargetWindowName);
        parcel.writeString(this.mSecuredWindowName);
    }
}
