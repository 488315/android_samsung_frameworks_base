package com.samsung.android.content.smartclip;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SmartClipRemoteRequestInfo implements Parcelable {
    public static final Parcelable.Creator<SmartClipRemoteRequestInfo> CREATOR = new Parcelable.Creator<SmartClipRemoteRequestInfo>() { // from class: com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipRemoteRequestInfo createFromParcel(Parcel parcel) {
            SmartClipRemoteRequestInfo smartClipRemoteRequestInfo = new SmartClipRemoteRequestInfo();
            smartClipRemoteRequestInfo.readFromParcel(parcel);
            return smartClipRemoteRequestInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipRemoteRequestInfo[] newArray(int i) {
            return new SmartClipRemoteRequestInfo[i];
        }
    };
    public static final int REQUEST_TYPE_AIR_BUTTON_HIT_TEST = 2;
    public static final int REQUEST_TYPE_INJECT_INPUT_EVENT = 3;
    public static final int REQUEST_TYPE_INVALID = 0;
    public static final int REQUEST_TYPE_SCROLLABLE_AREA_INFO = 4;
    public static final int REQUEST_TYPE_SCROLLABLE_VIEW_INFO = 5;
    public static final int REQUEST_TYPE_SMART_CLIP_META_EXTRACTION = 1;
    public static final int WINDOW_TARGETING_TYPE_FOCUSED = 2;
    public static final int WINDOW_TARGETING_TYPE_TOPMOST_LAYER = 0;
    public static final int WINDOW_TARGETING_TYPE_TOPMOST_TOUCHABLE = 1;
    public int mCallerPid;
    public int mCallerUid;
    public Parcelable mRequestData;
    public int mRequestId;
    public int mRequestType;
    public int mTargetWindowLayer;
    public int mWindowTargetingType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmartClipRemoteRequestInfo() {
        this.mCallerPid = 0;
        this.mCallerUid = 0;
        this.mRequestId = 0;
        this.mRequestType = 0;
        this.mWindowTargetingType = 0;
        this.mTargetWindowLayer = -1;
    }

    public SmartClipRemoteRequestInfo(int i, int i2, int i3, Parcelable parcelable) {
        this.mCallerPid = 0;
        this.mCallerUid = 0;
        this.mTargetWindowLayer = -1;
        this.mRequestId = i;
        this.mRequestType = i2;
        this.mRequestData = parcelable;
        this.mWindowTargetingType = i3;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCallerPid);
        parcel.writeInt(this.mCallerUid);
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mRequestType);
        parcel.writeParcelable(this.mRequestData, i);
        parcel.writeInt(this.mTargetWindowLayer);
        parcel.writeInt(this.mWindowTargetingType);
    }

    public void readFromParcel(Parcel parcel) {
        this.mCallerPid = parcel.readInt();
        this.mCallerUid = parcel.readInt();
        this.mRequestId = parcel.readInt();
        this.mRequestType = parcel.readInt();
        this.mRequestData = parcel.readParcelable(null);
        this.mTargetWindowLayer = parcel.readInt();
        this.mWindowTargetingType = parcel.readInt();
    }
}
