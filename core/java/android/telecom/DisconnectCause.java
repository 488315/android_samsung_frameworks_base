package android.telecom;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.service.timezone.TimeZoneProviderService;
import android.telephony.ims.ImsReasonInfo;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class DisconnectCause implements Parcelable {
    public static final int ANSWERED_ELSEWHERE = 11;
    public static final int BUSY = 7;
    public static final int CALL_PULLED = 12;
    public static final int CANCELED = 4;
    public static final int CONNECTION_MANAGER_NOT_SUPPORTED = 10;
    public static final Parcelable.Creator<DisconnectCause> CREATOR =
            new Parcelable.Creator<
                    DisconnectCause>() { // from class: android.telecom.DisconnectCause.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DisconnectCause createFromParcel(Parcel source) {
                    int code = source.readInt();
                    CharSequence label = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
                    CharSequence description =
                            TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
                    String reason = source.readString();
                    int tone = source.readInt();
                    int telephonyDisconnectCause = source.readInt();
                    int telephonyPreciseDisconnectCause = source.readInt();
                    ImsReasonInfo imsReasonInfo =
                            (ImsReasonInfo) source.readParcelable(null, ImsReasonInfo.class);
                    return new DisconnectCause(
                            code,
                            label,
                            description,
                            reason,
                            tone,
                            telephonyDisconnectCause,
                            telephonyPreciseDisconnectCause,
                            imsReasonInfo);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DisconnectCause[] newArray(int size) {
                    return new DisconnectCause[size];
                }
            };
    public static final int ERROR = 1;
    public static final int LOCAL = 2;
    public static final int MISSED = 5;
    public static final int OTHER = 9;
    public static final String REASON_EMERGENCY_CALL_PLACED = "REASON_EMERGENCY_CALL_PLACED";
    public static final String REASON_EMULATING_SINGLE_CALL = "EMULATING_SINGLE_CALL";
    public static final String REASON_IMS_ACCESS_BLOCKED = "REASON_IMS_ACCESS_BLOCKED";
    public static final String REASON_WIFI_ON_BUT_WFC_OFF = "REASON_WIFI_ON_BUT_WFC_OFF";
    public static final int REJECTED = 6;
    public static final int REMOTE = 3;
    public static final int RESTRICTED = 8;
    public static final int UNKNOWN = 0;
    private int mDisconnectCode;
    private CharSequence mDisconnectDescription;
    private CharSequence mDisconnectLabel;
    private String mDisconnectReason;
    private ImsReasonInfo mImsReasonInfo;
    private int mTelephonyDisconnectCause;
    private int mTelephonyPreciseDisconnectCause;
    private int mToneToPlay;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisconnectCauseCode {}

    public DisconnectCause(int code) {
        this(code, null, null, null, -1);
    }

    public DisconnectCause(int code, String reason) {
        this(code, null, null, reason, -1);
    }

    public DisconnectCause(int code, CharSequence label, CharSequence description, String reason) {
        this(code, label, description, reason, -1);
    }

    public DisconnectCause(
            int code, CharSequence label, CharSequence description, String reason, int toneToPlay) {
        this(code, label, description, reason, toneToPlay, 36, 65535, null);
    }

    public DisconnectCause(
            int code,
            CharSequence label,
            CharSequence description,
            String reason,
            int toneToPlay,
            int telephonyDisconnectCause,
            int telephonyPreciseDisconnectCause,
            ImsReasonInfo imsReasonInfo) {
        this.mDisconnectCode = code;
        this.mDisconnectLabel = label;
        this.mDisconnectDescription = description;
        this.mDisconnectReason = reason;
        this.mToneToPlay = toneToPlay;
        this.mTelephonyDisconnectCause = telephonyDisconnectCause;
        this.mTelephonyPreciseDisconnectCause = telephonyPreciseDisconnectCause;
        this.mImsReasonInfo = imsReasonInfo;
    }

    public int getCode() {
        return this.mDisconnectCode;
    }

    public CharSequence getLabel() {
        return this.mDisconnectLabel;
    }

    public CharSequence getDescription() {
        return this.mDisconnectDescription;
    }

    public String getReason() {
        return this.mDisconnectReason;
    }

    @SystemApi
    public int getTelephonyDisconnectCause() {
        return this.mTelephonyDisconnectCause;
    }

    @SystemApi
    public int getTelephonyPreciseDisconnectCause() {
        return this.mTelephonyPreciseDisconnectCause;
    }

    @SystemApi
    public ImsReasonInfo getImsReasonInfo() {
        return this.mImsReasonInfo;
    }

    public int getTone() {
        return this.mToneToPlay;
    }

    @SystemApi
    public static final class Builder {
        private int mDisconnectCode;
        private CharSequence mDisconnectDescription;
        private CharSequence mDisconnectLabel;
        private String mDisconnectReason;
        private ImsReasonInfo mImsReasonInfo;
        private int mTelephonyDisconnectCause;
        private int mTelephonyPreciseDisconnectCause;
        private int mToneToPlay = -1;

        public Builder(int code) {
            this.mDisconnectCode = code;
        }

        public Builder setLabel(CharSequence label) {
            this.mDisconnectLabel = label;
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.mDisconnectDescription = description;
            return this;
        }

        public Builder setReason(String reason) {
            this.mDisconnectReason = reason;
            return this;
        }

        public Builder setTone(int toneToPlay) {
            this.mToneToPlay = toneToPlay;
            return this;
        }

        public Builder setTelephonyDisconnectCause(int telephonyDisconnectCause) {
            this.mTelephonyDisconnectCause = telephonyDisconnectCause;
            return this;
        }

        public Builder setTelephonyPreciseDisconnectCause(int telephonyPreciseDisconnectCause) {
            this.mTelephonyPreciseDisconnectCause = telephonyPreciseDisconnectCause;
            return this;
        }

        public Builder setImsReasonInfo(ImsReasonInfo imsReasonInfo) {
            this.mImsReasonInfo = imsReasonInfo;
            return this;
        }

        public DisconnectCause build() {
            return new DisconnectCause(
                    this.mDisconnectCode,
                    this.mDisconnectLabel,
                    this.mDisconnectDescription,
                    this.mDisconnectReason,
                    this.mToneToPlay,
                    this.mTelephonyDisconnectCause,
                    this.mTelephonyPreciseDisconnectCause,
                    this.mImsReasonInfo);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeInt(this.mDisconnectCode);
        TextUtils.writeToParcel(this.mDisconnectLabel, destination, flags);
        TextUtils.writeToParcel(this.mDisconnectDescription, destination, flags);
        destination.writeString(this.mDisconnectReason);
        destination.writeInt(this.mToneToPlay);
        destination.writeInt(this.mTelephonyDisconnectCause);
        destination.writeInt(this.mTelephonyPreciseDisconnectCause);
        destination.writeParcelable(this.mImsReasonInfo, 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.mDisconnectCode))
                + Objects.hashCode(this.mDisconnectLabel)
                + Objects.hashCode(this.mDisconnectDescription)
                + Objects.hashCode(this.mDisconnectReason)
                + Objects.hashCode(Integer.valueOf(this.mToneToPlay))
                + Objects.hashCode(Integer.valueOf(this.mTelephonyDisconnectCause))
                + Objects.hashCode(Integer.valueOf(this.mTelephonyPreciseDisconnectCause))
                + Objects.hashCode(this.mImsReasonInfo);
    }

    public boolean equals(Object o) {
        if (!(o instanceof DisconnectCause)) {
            return false;
        }
        DisconnectCause d = (DisconnectCause) o;
        return Objects.equals(Integer.valueOf(this.mDisconnectCode), Integer.valueOf(d.getCode()))
                && Objects.equals(this.mDisconnectLabel, d.getLabel())
                && Objects.equals(this.mDisconnectDescription, d.getDescription())
                && Objects.equals(this.mDisconnectReason, d.getReason())
                && Objects.equals(Integer.valueOf(this.mToneToPlay), Integer.valueOf(d.getTone()))
                && Objects.equals(
                        Integer.valueOf(this.mTelephonyDisconnectCause),
                        Integer.valueOf(d.getTelephonyDisconnectCause()))
                && Objects.equals(
                        Integer.valueOf(this.mTelephonyPreciseDisconnectCause),
                        Integer.valueOf(d.getTelephonyPreciseDisconnectCause()))
                && Objects.equals(this.mImsReasonInfo, d.getImsReasonInfo());
    }

    public String toString() {
        String code;
        switch (this.mDisconnectCode) {
            case 0:
                code = "UNKNOWN";
                break;
            case 1:
                code = TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
                break;
            case 2:
                code = CalendarContract.ACCOUNT_TYPE_LOCAL;
                break;
            case 3:
                code = "REMOTE";
                break;
            case 4:
                code = "CANCELED";
                break;
            case 5:
                code = "MISSED";
                break;
            case 6:
                code = "REJECTED";
                break;
            case 7:
                code = "BUSY";
                break;
            case 8:
                code = "RESTRICTED";
                break;
            case 9:
                code = "OTHER";
                break;
            case 10:
                code = "CONNECTION_MANAGER_NOT_SUPPORTED";
                break;
            case 11:
                code = "ANSWERED_ELSEWHERE";
                break;
            case 12:
                code = "CALL_PULLED";
                break;
            default:
                code = "invalid code: " + this.mDisconnectCode;
                break;
        }
        String label = this.mDisconnectLabel == null ? "" : this.mDisconnectLabel.toString();
        String description =
                this.mDisconnectDescription == null ? "" : this.mDisconnectDescription.toString();
        String reason = this.mDisconnectReason != null ? this.mDisconnectReason : "";
        return "DisconnectCause [ Code: ("
                + code
                + ") Label: ("
                + label
                + ") Description: ("
                + description
                + ") Reason: ("
                + reason
                + ") Tone: ("
                + this.mToneToPlay
                + ")  TelephonyCause: "
                + this.mTelephonyDisconnectCause
                + "/"
                + this.mTelephonyPreciseDisconnectCause
                + " ImsReasonInfo: "
                + this.mImsReasonInfo
                + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
