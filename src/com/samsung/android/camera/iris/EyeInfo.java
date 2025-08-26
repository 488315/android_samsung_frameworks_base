package com.samsung.android.camera.iris;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public final class EyeInfo implements Parcelable {
    public static final Parcelable.Creator<EyeInfo> CREATOR = new Parcelable.Creator<EyeInfo>() { // from class: com.samsung.android.camera.iris.EyeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EyeInfo createFromParcel(Parcel parcel) {
            return new EyeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EyeInfo[] newArray(int i) {
            return new EyeInfo[i];
        }
    };
    public static final int DISTANCE_CLOSE = 1;
    public static final int DISTANCE_FAR = 4;
    public static final int DISTANCE_GOOD = 0;
    public static final int DISTANCE_TOO_CLOSE = 3;
    public static final int DISTANCE_TOO_FAR = 6;
    public static final int DISTANCE_VERY_CLOSE = 2;
    public static final int DISTANCE_VERY_FAR = 5;
    public static final int INFO_NOT_SUPPORTED = -1;
    public static final int IRIS_ACQUIRED_CHANGE_YOUR_POSITION = 12;
    public static final int IRIS_ACQUIRED_EYE_NOT_PRESENT = 10;
    public static final int IRIS_ACQUIRED_FAIL_IN_DOOR = 15;
    public static final int IRIS_ACQUIRED_FAIL_OUT_DOOR = 16;
    public static final int IRIS_ACQUIRED_GOOD = 0;
    public static final int IRIS_ACQUIRED_INSUFFICIENT = 2;
    public static final int IRIS_ACQUIRED_MOVE_CLOSER = 3;
    public static final int IRIS_ACQUIRED_MOVE_DOWN = 8;
    public static final int IRIS_ACQUIRED_MOVE_FARTHER = 4;
    public static final int IRIS_ACQUIRED_MOVE_LEFT = 5;
    public static final int IRIS_ACQUIRED_MOVE_RIGHT = 6;
    public static final int IRIS_ACQUIRED_MOVE_SOMEWHERE_DARKER = 11;
    public static final int IRIS_ACQUIRED_MOVE_UP = 7;
    public static final int IRIS_ACQUIRED_OPEN_EYES_WIDER = 9;
    public static final int IRIS_ACQUIRED_PARTIAL = 1;
    public static final int IRIS_ACQUIRED_PASS_IN_DOOR = 13;
    public static final int IRIS_ACQUIRED_PASS_OUT_DOOR = 14;
    public static final int IRIS_LEFT_EYE = 0;
    public static final int IRIS_RIGHT_EYE = 1;
    public static final int OPENING_GOOD = 0;
    public static final int OPENING_SMALL = 1;
    public static final int OPENING_TOO_SMALL = 3;
    public static final int OPENING_VERY_SMALL = 2;
    public static final int PUPIL_INFO_EYE_IS_FAKE = 3;
    public static final int PUPIL_INFO_EYE_LOW_IRIS_SCLERA_CONTRAST = 5;
    public static final int PUPIL_INFO_EYE_LOW_PUPIL_IRIS_CONTRAST = 4;
    public static final int PUPIL_INFO_EYE_NOT_PRESENT = 1;
    public static final int PUPIL_INFO_EYE_REGION_LOW_CONSTRAST = 2;
    public static final int PUPIL_INFO_LESS_QUALITY_SCORE = 7;
    public static final int PUPIL_INFO_NONE = 0;
    public static final int PUPIL_INFO_SMALL_MATCH_AREA = 6;
    public static final int REFLECTION_INFO_EYE_HIGHLIGHT_OCCLUSION = 0;
    public static final int REFLECTION_INFO_EYE_REGION_OVERILLUMINATED = 1;
    public int mAcquireInfo;
    public PupilInfo[] mPupilInfo;
    public ReflectionInfo[] mReflectionInfo;
    public int mReflectionNum;

    public static class PupilInfo {
        public Rect mRect = null;
        public int mDistance = -1;
        public int mOpening = -1;
        public int mMsgId = -1;
    }

    public static class ReflectionInfo {
        public Rect mRect = null;
        public int mMsgId = -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EyeInfo(PupilInfo[] pupilInfoArr, ReflectionInfo[] reflectionInfoArr, int i, int i2) {
        this.mPupilInfo = pupilInfoArr;
        this.mReflectionInfo = reflectionInfoArr;
        this.mAcquireInfo = i;
        this.mReflectionNum = i2;
    }

    private EyeInfo(Parcel parcel) {
        this.mPupilInfo = null;
        this.mReflectionInfo = null;
        this.mAcquireInfo = -1;
        this.mReflectionNum = -1;
        this.mPupilInfo = new PupilInfo[2];
        int i = parcel.readInt();
        this.mReflectionNum = i;
        this.mReflectionInfo = new ReflectionInfo[i];
        for (int i2 = 0; i2 < 2; i2++) {
            this.mPupilInfo[i2] = new PupilInfo();
            this.mPupilInfo[i2].mRect = new Rect();
            this.mPupilInfo[i2].mRect.left = parcel.readInt();
            this.mPupilInfo[i2].mRect.top = parcel.readInt();
            this.mPupilInfo[i2].mRect.right = parcel.readInt();
            this.mPupilInfo[i2].mRect.bottom = parcel.readInt();
            this.mPupilInfo[i2].mDistance = parcel.readInt();
            this.mPupilInfo[i2].mOpening = parcel.readInt();
            this.mPupilInfo[i2].mMsgId = parcel.readInt();
        }
        for (int i3 = 0; i3 < this.mReflectionNum; i3++) {
            this.mReflectionInfo[i3] = new ReflectionInfo();
            this.mReflectionInfo[i3].mRect = new Rect();
            this.mReflectionInfo[i3].mRect.left = parcel.readInt();
            this.mReflectionInfo[i3].mRect.top = parcel.readInt();
            this.mReflectionInfo[i3].mRect.right = parcel.readInt();
            this.mReflectionInfo[i3].mRect.bottom = parcel.readInt();
            this.mReflectionInfo[i3].mMsgId = parcel.readInt();
        }
        this.mAcquireInfo = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mReflectionNum);
        for (int i2 = 0; i2 < 2; i2++) {
            parcel.writeInt(this.mPupilInfo[i2].mRect.left);
            parcel.writeInt(this.mPupilInfo[i2].mRect.top);
            parcel.writeInt(this.mPupilInfo[i2].mRect.right);
            parcel.writeInt(this.mPupilInfo[i2].mRect.bottom);
            parcel.writeInt(this.mPupilInfo[i2].mDistance);
            parcel.writeInt(this.mPupilInfo[i2].mOpening);
            parcel.writeInt(this.mPupilInfo[i2].mMsgId);
        }
        for (int i3 = 0; i3 < this.mReflectionNum; i3++) {
            parcel.writeInt(this.mReflectionInfo[i3].mRect.left);
            parcel.writeInt(this.mReflectionInfo[i3].mRect.top);
            parcel.writeInt(this.mReflectionInfo[i3].mRect.right);
            parcel.writeInt(this.mReflectionInfo[i3].mRect.bottom);
            parcel.writeInt(this.mReflectionInfo[i3].mMsgId);
        }
        parcel.writeInt(this.mAcquireInfo);
    }
}
