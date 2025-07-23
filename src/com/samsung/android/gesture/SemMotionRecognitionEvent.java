package com.samsung.android.gesture;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;

/* loaded from: classes6.dex */
public class SemMotionRecognitionEvent implements Parcelable {
    public static final int BLOW = 66;
    public static final int BOUNCE = 46;
    public static final int BT_SHARING_RECEIVE_NOT_READY = 41;
    public static final int BT_SHARING_RECEIVE_READY = 40;
    public static final int BT_SHARING_SEND_PAUSE = 43;
    public static final int BT_SHARING_SEND_START = 42;
    public static final int BT_SHARING_SEND_STOP = 44;
    public static final int CALLPOSE_L = 76;
    public static final int CALLPOSE_NONE = 100;
    public static final int CALLPOSE_R = 77;
    public static final Parcelable.Creator<SemMotionRecognitionEvent> CREATOR = new Parcelable.Creator<SemMotionRecognitionEvent>() { // from class: com.samsung.android.gesture.SemMotionRecognitionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMotionRecognitionEvent createFromParcel(Parcel parcel) {
            return new SemMotionRecognitionEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMotionRecognitionEvent[] newArray(int i) {
            return new SemMotionRecognitionEvent[i];
        }
    };
    public static final int DIRECT_CALL = 68;
    public static final int DIRECT_CALL_LEFT = 101;
    public static final int DIRECT_CALL_RIGHT = 102;
    public static final int FLAT = 71;
    public static final int FLIP_SCREEN_DOWN = 10;
    public static final int FLIP_SCREEN_UP = 86;
    public static final int LOCK_EXECUTE_APP = 69;
    public static final int LOCK_EXECUTE_CAMERA_L = 73;
    public static final int LOCK_EXECUTE_CAMERA_R = 74;
    public static final int MAX = 116;
    public static final int NONE = 0;
    public static final int NOT_ELEVATOR = 114;
    public static final int ONE_TAPPING_GYRO = 62;
    public static final int ONE_TIPPING_GYRO = 64;
    public static final int OUT_OF_ELEVATOR = 115;
    public static final int PANNING_GYRO = 61;
    public static final int ROTATE_0 = 28;
    public static final int ROTATE_180 = 30;
    public static final int ROTATE_270 = 31;
    public static final int ROTATE_90 = 29;
    public static final int ROTATE_HORIZONTAL = 45;
    public static final int ROTATE_START = 32;
    public static final int ROTATE_STOP = 33;
    public static final int ROTATION_AXIS_X = 94;
    public static final int ROTATION_AXIS_Y = 95;
    public static final int ROTATION_AXIS_Z = 96;

    @Deprecated
    public static final int SCREEN_UP_STEADY = 99;
    public static final int SHAKE = 34;
    public static final int SHAKE_START = 35;
    public static final int SHAKE_STOP = 36;
    public static final int SHORT_SHAKE = 37;
    public static final int SHORT_SHAKE_START = 38;
    public static final int SHORT_SHAKE_STOP = 39;
    public static final int SMART_ALERT = 67;
    public static final int SMART_ALERT_SETTING = 98;

    @Deprecated
    public static final int SMART_RELAY = 113;
    public static final int SMART_SCROLL_CAMERA_OFF = 111;
    public static final int SMART_SCROLL_CAMERA_ON = 112;
    public static final int SMART_SCROLL_TILT_DOWN_START = 104;
    public static final int SMART_SCROLL_TILT_DOWN_START_LAND = 108;
    public static final int SMART_SCROLL_TILT_FACE_IN_STOP = 105;
    public static final int SMART_SCROLL_TILT_FACE_IN_STOP_LAND = 109;
    public static final int SMART_SCROLL_TILT_FACE_OUT_STOP = 106;
    public static final int SMART_SCROLL_TILT_FACE_OUT_STOP_LAND = 110;
    public static final int SMART_SCROLL_TILT_UP_START = 103;
    public static final int SMART_SCROLL_TILT_UP_START_LAND = 107;
    public static final int SNAP1_X_NEGATIVE = 48;
    public static final int SNAP1_X_POSITIVE = 47;
    public static final int SNAP1_Y_NEGATIVE = 50;
    public static final int SNAP1_Y_POSITIVE = 49;
    public static final int SNAP1_Z_NEGATIVE = 52;
    public static final int SNAP1_Z_POSITIVE = 51;
    public static final int SNAP2_X_NEGATIVE = 54;
    public static final int SNAP2_X_POSITIVE = 53;
    public static final int SNAP2_Y_NEGATIVE = 56;
    public static final int SNAP2_Y_POSITIVE = 55;
    public static final int SNAP2_Z_NEGATIVE = 58;
    public static final int SNAP2_Z_POSITIVE = 57;
    public static final int SNAP_LEFT = 59;
    public static final int SNAP_RIGHT = 60;
    public static final int SNAP_X_NEGATIVE = 3;
    public static final int SNAP_X_POSITIVE = 2;
    public static final int SNAP_Y_NEGATIVE = 5;
    public static final int SNAP_Y_POSITIVE = 4;
    public static final int SNAP_Z_NEGATIVE = 7;
    public static final int SNAP_Z_POSITIVE = 6;
    public static final int SPEAKER_PHONE_OFF = 9;
    public static final int SPEAKER_PHONE_ON = 8;
    public static final int TILT = 72;
    public static final int TILT_BACK = 23;
    public static final int TILT_DOWN_LEVEL_1 = 81;
    public static final int TILT_DOWN_LEVEL_1_LAND = 90;
    public static final int TILT_DOWN_LEVEL_2 = 82;
    public static final int TILT_DOWN_LEVEL_2_LAND = 91;
    public static final int TILT_DOWN_LEVEL_3 = 83;
    public static final int TILT_DOWN_LEVEL_3_LAND = 92;
    public static final int TILT_FRONT = 22;
    public static final int TILT_FRONT_BACK_END = 24;
    public static final int TILT_LANDSCAPE_LEFT_LEVEL_1 = 17;
    public static final int TILT_LANDSCAPE_LEFT_LEVEL_2 = 18;
    public static final int TILT_LANDSCAPE_LEFT_RIGHT_STOP = 21;
    public static final int TILT_LANDSCAPE_RIGHT_LEVEL_1 = 19;
    public static final int TILT_LANDSCAPE_RIGHT_LEVEL_2 = 20;
    public static final int TILT_LEFT = 25;
    public static final int TILT_LEFT_RIGHT_END = 27;
    public static final int TILT_LEVEL_FLAT = 85;
    public static final int TILT_LEVEL_ZERO = 84;
    public static final int TILT_LEVEL_ZERO_LAND = 93;
    public static final int TILT_PORTRAIT_BACK = 12;
    public static final int TILT_PORTRAIT_FRONT = 11;
    public static final int TILT_PORTRAIT_FRONT_BACK_STOP = 13;
    public static final int TILT_PORTRAIT_LEFT = 14;
    public static final int TILT_PORTRAIT_LEFT_RIGHT_STOP = 16;
    public static final int TILT_PORTRAIT_RIGHT = 15;
    public static final int TILT_RIGHT = 26;
    public static final int TILT_TO_UNLOCK = 75;
    public static final int TILT_TO_UNLOCK_LAND = 97;
    public static final int TILT_UP_LEVEL_1 = 78;
    public static final int TILT_UP_LEVEL_1_LAND = 87;
    public static final int TILT_UP_LEVEL_2 = 79;
    public static final int TILT_UP_LEVEL_2_LAND = 88;
    public static final int TILT_UP_LEVEL_3 = 80;
    public static final int TILT_UP_LEVEL_3_LAND = 89;
    public static final int TWO_TAPPING = 1;
    public static final int TWO_TAPPING_GYRO = 63;
    public static final int TWO_TIPPING_GYRO = 65;
    public static final int VOLUMEDOWN = 70;
    private int motion;
    private int panningDx;
    private int panningDxImage;
    private int panningDy;
    private int panningDyImage;
    private int panningDz;
    private int panningDzImage;
    private int tilt;
    private int walkingStatus;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemMotionRecognitionEvent() {
        this.motion = 0;
        this.panningDx = 0;
        this.panningDy = 0;
        this.panningDz = 0;
        this.panningDxImage = 0;
        this.panningDyImage = 0;
        this.panningDzImage = 0;
        this.walkingStatus = 0;
    }

    public SemMotionRecognitionEvent(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getMotion() {
        return this.motion;
    }

    public void setMotion(int i) {
        this.motion = 0;
        if (i < 0 || i > 116) {
            return;
        }
        this.motion = i;
    }

    public void setPanningDx(int i) {
        this.panningDx = i;
    }

    public void setPanningDy(int i) {
        this.panningDy = i;
    }

    public void setPanningDz(int i) {
        this.panningDz = i;
    }

    public void setTilt(int i) {
        this.tilt = i;
    }

    public void setWalkingStatus(int i) {
        this.walkingStatus = i;
    }

    public int getPanningDx() {
        return this.panningDx;
    }

    public int getPanningDy() {
        return this.panningDy;
    }

    public int getPanningDz() {
        return this.panningDz;
    }

    public int getSmartMotion() {
        return this.panningDz;
    }

    public int getTilt() {
        return this.tilt;
    }

    public int getWalkingStatus() {
        return this.walkingStatus;
    }

    public void setPanningDxImage(int i) {
        this.panningDxImage = i;
    }

    public void setPanningDyImage(int i) {
        this.panningDyImage = i;
    }

    public void setPanningDzImage(int i) {
        this.panningDzImage = i;
    }

    public int getPanningDxImage() {
        return this.panningDxImage;
    }

    public int getPanningDyImage() {
        return this.panningDyImage;
    }

    public int getPanningDzImage() {
        return this.panningDzImage;
    }

    public String toString() {
        String str = Integer.toString(this.motion) + "=";
        int i = this.motion;
        if (i == 0) {
            str = str + KeyProperties.DIGEST_NONE;
        } else if (i == 10) {
            str = str + "FLIP_SCREEN_DOWN";
        } else if (i == 67) {
            str = str + "SMART_ALERT";
        } else if (i == 71) {
            str = str + "FLAT";
        } else if (i == 86) {
            str = str + "FLIP_SCREEN_UP";
        } else if (i == 99) {
            str = str + "SCREEN_UP_STEADY";
        } else if (i == 101) {
            str = str + "DIRECT_CALL_LEFT";
        } else if (i == 102) {
            str = str + "DIRECT_CALL_RIGHT";
        } else {
            switch (i) {
                case 113:
                    str = str + "SMART_RELAY";
                    break;
                case 114:
                    str = str + "NOT_ELEVATOR";
                    break;
                case 115:
                    str = str + "OUT_OF_ELEVATOR";
                    break;
                case 116:
                    str = str + "MAX";
                    break;
            }
        }
        return str.trim();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.motion);
        parcel.writeInt(this.panningDx);
        parcel.writeInt(this.panningDy);
        parcel.writeInt(this.panningDz);
        parcel.writeInt(this.panningDxImage);
        parcel.writeInt(this.panningDyImage);
        parcel.writeInt(this.panningDzImage);
        parcel.writeInt(this.tilt);
        parcel.writeInt(this.walkingStatus);
    }

    public void readFromParcel(Parcel parcel) {
        this.motion = parcel.readInt();
        this.panningDx = parcel.readInt();
        this.panningDy = parcel.readInt();
        this.panningDz = parcel.readInt();
        this.panningDxImage = parcel.readInt();
        this.panningDyImage = parcel.readInt();
        this.panningDzImage = parcel.readInt();
        this.tilt = parcel.readInt();
        this.walkingStatus = parcel.readInt();
    }
}
