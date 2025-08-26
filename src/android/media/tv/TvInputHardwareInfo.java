package android.media.tv;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class TvInputHardwareInfo implements Parcelable {
    public static final int CABLE_CONNECTION_STATUS_CONNECTED = 1;
    public static final int CABLE_CONNECTION_STATUS_DISCONNECTED = 2;
    public static final int CABLE_CONNECTION_STATUS_UNKNOWN = 0;
    public static final Parcelable.Creator<TvInputHardwareInfo> CREATOR = new Parcelable.Creator<TvInputHardwareInfo>() { // from class: android.media.tv.TvInputHardwareInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInputHardwareInfo createFromParcel(Parcel parcel) {
            try {
                TvInputHardwareInfo tvInputHardwareInfo = new TvInputHardwareInfo();
                tvInputHardwareInfo.readFromParcel(parcel);
                return tvInputHardwareInfo;
            } catch (Exception e) {
                Log.e(TvInputHardwareInfo.TAG, "Exception creating TvInputHardwareInfo from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInputHardwareInfo[] newArray(int i) {
            return new TvInputHardwareInfo[i];
        }
    };
    static final String TAG = "TvInputHardwareInfo";
    public static final int TV_INPUT_TYPE_COMPONENT = 6;
    public static final int TV_INPUT_TYPE_COMPOSITE = 3;
    public static final int TV_INPUT_TYPE_DISPLAY_PORT = 10;
    public static final int TV_INPUT_TYPE_DVI = 8;
    public static final int TV_INPUT_TYPE_HDMI = 9;
    public static final int TV_INPUT_TYPE_OTHER_HARDWARE = 1;
    public static final int TV_INPUT_TYPE_SCART = 5;
    public static final int TV_INPUT_TYPE_SVIDEO = 4;
    public static final int TV_INPUT_TYPE_TUNER = 2;
    public static final int TV_INPUT_TYPE_VGA = 7;
    private String mAudioAddress;
    private int mAudioType;
    private int mCableConnectionStatus;
    private int mDeviceId;
    private int mHdmiPortId;
    private int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CableConnectionStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TvInputHardwareInfo() {
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public int getType() {
        return this.mType;
    }

    public int getAudioType() {
        return this.mAudioType;
    }

    public String getAudioAddress() {
        return this.mAudioAddress;
    }

    public int getHdmiPortId() {
        if (this.mType != 9) {
            throw new IllegalStateException();
        }
        return this.mHdmiPortId;
    }

    public int getCableConnectionStatus() {
        return this.mCableConnectionStatus;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("TvInputHardwareInfo {id=");
        sb.append(this.mDeviceId);
        sb.append(", type=");
        sb.append(this.mType);
        sb.append(", audio_type=");
        sb.append(this.mAudioType);
        sb.append(", audio_addr=");
        sb.append(this.mAudioAddress);
        if (this.mType == 9) {
            sb.append(", hdmi_port=");
            sb.append(this.mHdmiPortId);
        }
        sb.append(", cable_connection_status=");
        sb.append(this.mCableConnectionStatus);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDeviceId);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAudioType);
        parcel.writeString(this.mAudioAddress);
        if (this.mType == 9) {
            parcel.writeInt(this.mHdmiPortId);
        }
        parcel.writeInt(this.mCableConnectionStatus);
    }

    public void readFromParcel(Parcel parcel) {
        this.mDeviceId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mAudioType = parcel.readInt();
        this.mAudioAddress = parcel.readString();
        if (this.mType == 9) {
            this.mHdmiPortId = parcel.readInt();
        }
        this.mCableConnectionStatus = parcel.readInt();
    }

    public Builder toBuilder() {
        Builder builderCableConnectionStatus = new Builder().deviceId(this.mDeviceId).type(this.mType).audioType(this.mAudioType).audioAddress(this.mAudioAddress).cableConnectionStatus(this.mCableConnectionStatus);
        if (this.mType == 9) {
            builderCableConnectionStatus.hdmiPortId(this.mHdmiPortId);
        }
        return builderCableConnectionStatus;
    }

    public static final class Builder {
        private Integer mDeviceId = null;
        private Integer mType = null;
        private int mAudioType = 0;
        private String mAudioAddress = "";
        private Integer mHdmiPortId = null;
        private Integer mCableConnectionStatus = 0;

        public Builder deviceId(int i) {
            this.mDeviceId = Integer.valueOf(i);
            return this;
        }

        public Builder type(int i) {
            this.mType = Integer.valueOf(i);
            return this;
        }

        public Builder audioType(int i) {
            this.mAudioType = i;
            return this;
        }

        public Builder audioAddress(String str) {
            this.mAudioAddress = str;
            return this;
        }

        public Builder hdmiPortId(int i) {
            this.mHdmiPortId = Integer.valueOf(i);
            return this;
        }

        public Builder cableConnectionStatus(int i) {
            this.mCableConnectionStatus = Integer.valueOf(i);
            return this;
        }

        public TvInputHardwareInfo build() {
            Integer num;
            if (this.mDeviceId == null || (num = this.mType) == null) {
                throw new UnsupportedOperationException();
            }
            if ((num.intValue() == 9 && this.mHdmiPortId == null) || (this.mType.intValue() != 9 && this.mHdmiPortId != null)) {
                throw new UnsupportedOperationException();
            }
            TvInputHardwareInfo tvInputHardwareInfo = new TvInputHardwareInfo();
            tvInputHardwareInfo.mDeviceId = this.mDeviceId.intValue();
            tvInputHardwareInfo.mType = this.mType.intValue();
            tvInputHardwareInfo.mAudioType = this.mAudioType;
            if (tvInputHardwareInfo.mAudioType != 0) {
                tvInputHardwareInfo.mAudioAddress = this.mAudioAddress;
            }
            Integer num2 = this.mHdmiPortId;
            if (num2 != null) {
                tvInputHardwareInfo.mHdmiPortId = num2.intValue();
            }
            tvInputHardwareInfo.mCableConnectionStatus = this.mCableConnectionStatus.intValue();
            return tvInputHardwareInfo;
        }
    }
}
