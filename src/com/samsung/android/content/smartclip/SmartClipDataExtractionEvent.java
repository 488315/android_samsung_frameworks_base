package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SmartClipDataExtractionEvent implements Parcelable {
    public static final Parcelable.Creator<SmartClipDataExtractionEvent> CREATOR = new Parcelable.Creator<SmartClipDataExtractionEvent>() { // from class: com.samsung.android.content.smartclip.SmartClipDataExtractionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipDataExtractionEvent createFromParcel(Parcel parcel) {
            SmartClipDataExtractionEvent smartClipDataExtractionEvent = new SmartClipDataExtractionEvent();
            smartClipDataExtractionEvent.readFromParcel(parcel);
            return smartClipDataExtractionEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmartClipDataExtractionEvent[] newArray(int i) {
            return new SmartClipDataExtractionEvent[i];
        }
    };
    public static final int EXTRACTION_MODE_DRAG_AND_DROP = 2;
    public static final int EXTRACTION_MODE_FULL_SCREEN = 1;
    public static final int EXTRACTION_MODE_NORMAL = 0;
    public static final int EXTRACTION_MODE_SINGLE_WORD = 3;
    public static final int EXTRACTION_MODE_WALLPAPER = 4;
    public Rect mCropRect;
    public int mExtractionMode;
    public int mRequestId;
    public int mTargetWindowLayer;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmartClipDataExtractionEvent() {
        this.mRequestId = 0;
        this.mExtractionMode = 0;
        this.mCropRect = new Rect();
        this.mTargetWindowLayer = -1;
    }

    public SmartClipDataExtractionEvent(int i, Rect rect) {
        this.mRequestId = 0;
        this.mExtractionMode = 0;
        new Rect();
        this.mTargetWindowLayer = -1;
        this.mRequestId = i;
        this.mCropRect = rect;
    }

    public SmartClipDataExtractionEvent(int i, Rect rect, int i2) {
        this(i, rect);
        this.mExtractionMode = i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mExtractionMode);
        parcel.writeInt(this.mTargetWindowLayer);
        parcel.writeParcelable(this.mCropRect, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mExtractionMode = parcel.readInt();
        this.mTargetWindowLayer = parcel.readInt();
        this.mCropRect = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
    }
}
