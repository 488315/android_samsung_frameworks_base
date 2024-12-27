package com.samsung.android.content.smartclip;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SmartClipRemoteRequestInfo implements Parcelable {
    public static final Parcelable.Creator<SmartClipRemoteRequestInfo> CREATOR =
            new Parcelable.Creator<
                    SmartClipRemoteRequestInfo>() { // from class:
                                                    // com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SmartClipRemoteRequestInfo createFromParcel(Parcel in) {
                    SmartClipRemoteRequestInfo data = new SmartClipRemoteRequestInfo();
                    data.readFromParcel(in);
                    return data;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SmartClipRemoteRequestInfo[] newArray(int size) {
                    return new SmartClipRemoteRequestInfo[size];
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

    public SmartClipRemoteRequestInfo() {
        this.mCallerPid = 0;
        this.mCallerUid = 0;
        this.mRequestId = 0;
        this.mRequestType = 0;
        this.mWindowTargetingType = 0;
        this.mTargetWindowLayer = -1;
    }

    public SmartClipRemoteRequestInfo(
            int requestId, int requestType, int windowTargetingType, Parcelable requestData) {
        this.mCallerPid = 0;
        this.mCallerUid = 0;
        this.mRequestId = 0;
        this.mRequestType = 0;
        this.mWindowTargetingType = 0;
        this.mTargetWindowLayer = -1;
        this.mRequestId = requestId;
        this.mRequestType = requestType;
        this.mRequestData = requestData;
        this.mWindowTargetingType = windowTargetingType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mCallerPid);
        out.writeInt(this.mCallerUid);
        out.writeInt(this.mRequestId);
        out.writeInt(this.mRequestType);
        out.writeParcelable(this.mRequestData, flags);
        out.writeInt(this.mTargetWindowLayer);
        out.writeInt(this.mWindowTargetingType);
    }

    public void readFromParcel(Parcel in) {
        this.mCallerPid = in.readInt();
        this.mCallerUid = in.readInt();
        this.mRequestId = in.readInt();
        this.mRequestType = in.readInt();
        this.mRequestData = in.readParcelable(null);
        this.mTargetWindowLayer = in.readInt();
        this.mWindowTargetingType = in.readInt();
    }
}
