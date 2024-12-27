package com.samsung.android.camera.iris;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public final class EyeInfo implements Parcelable {
    public static final Parcelable.Creator<EyeInfo> CREATOR =
            new Parcelable.Creator<
                    EyeInfo>() { // from class: com.samsung.android.camera.iris.EyeInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EyeInfo createFromParcel(Parcel in) {
                    return new EyeInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EyeInfo[] newArray(int size) {
                    return new EyeInfo[size];
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

    public EyeInfo(
            PupilInfo[] pupilInfo,
            ReflectionInfo[] reflectionInfo,
            int acquireInfo,
            int reflectionNum) {
        this.mPupilInfo = null;
        this.mReflectionInfo = null;
        this.mAcquireInfo = -1;
        this.mReflectionNum = -1;
        this.mPupilInfo = pupilInfo;
        this.mReflectionInfo = reflectionInfo;
        this.mAcquireInfo = acquireInfo;
        this.mReflectionNum = reflectionNum;
    }

    private EyeInfo(Parcel in) {
        this.mPupilInfo = null;
        this.mReflectionInfo = null;
        this.mAcquireInfo = -1;
        this.mReflectionNum = -1;
        this.mPupilInfo = new PupilInfo[2];
        this.mReflectionNum = in.readInt();
        this.mReflectionInfo = new ReflectionInfo[this.mReflectionNum];
        for (int i = 0; i < 2; i++) {
            this.mPupilInfo[i] = new PupilInfo();
            this.mPupilInfo[i].mRect = new Rect();
            this.mPupilInfo[i].mRect.left = in.readInt();
            this.mPupilInfo[i].mRect.top = in.readInt();
            this.mPupilInfo[i].mRect.right = in.readInt();
            this.mPupilInfo[i].mRect.bottom = in.readInt();
            this.mPupilInfo[i].mDistance = in.readInt();
            this.mPupilInfo[i].mOpening = in.readInt();
            this.mPupilInfo[i].mMsgId = in.readInt();
        }
        for (int i2 = 0; i2 < this.mReflectionNum; i2++) {
            this.mReflectionInfo[i2] = new ReflectionInfo();
            this.mReflectionInfo[i2].mRect = new Rect();
            this.mReflectionInfo[i2].mRect.left = in.readInt();
            this.mReflectionInfo[i2].mRect.top = in.readInt();
            this.mReflectionInfo[i2].mRect.right = in.readInt();
            this.mReflectionInfo[i2].mRect.bottom = in.readInt();
            this.mReflectionInfo[i2].mMsgId = in.readInt();
        }
        int i3 = in.readInt();
        this.mAcquireInfo = i3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mReflectionNum);
        for (int i = 0; i < 2; i++) {
            out.writeInt(this.mPupilInfo[i].mRect.left);
            out.writeInt(this.mPupilInfo[i].mRect.top);
            out.writeInt(this.mPupilInfo[i].mRect.right);
            out.writeInt(this.mPupilInfo[i].mRect.bottom);
            out.writeInt(this.mPupilInfo[i].mDistance);
            out.writeInt(this.mPupilInfo[i].mOpening);
            out.writeInt(this.mPupilInfo[i].mMsgId);
        }
        for (int i2 = 0; i2 < this.mReflectionNum; i2++) {
            out.writeInt(this.mReflectionInfo[i2].mRect.left);
            out.writeInt(this.mReflectionInfo[i2].mRect.top);
            out.writeInt(this.mReflectionInfo[i2].mRect.right);
            out.writeInt(this.mReflectionInfo[i2].mRect.bottom);
            out.writeInt(this.mReflectionInfo[i2].mMsgId);
        }
        int i3 = this.mAcquireInfo;
        out.writeInt(i3);
    }
}
