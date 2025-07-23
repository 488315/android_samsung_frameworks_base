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

/* loaded from: classes4.dex */
public final class DisconnectCause implements Parcelable {
    public static final int ANSWERED_ELSEWHERE = 11;
    public static final int BUSY = 7;
    public static final int CALL_PULLED = 12;
    public static final int CANCELED = 4;
    public static final int CONNECTION_MANAGER_NOT_SUPPORTED = 10;
    public static final Parcelable.Creator<DisconnectCause> CREATOR = new Parcelable.Creator<DisconnectCause>() { // from class: android.telecom.DisconnectCause.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisconnectCause createFromParcel(Parcel parcel) {
            return new DisconnectCause(parcel.readInt(), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (ImsReasonInfo) parcel.readParcelable(null, ImsReasonInfo.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisconnectCause[] newArray(int i) {
            return new DisconnectCause[i];
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
    public @interface DisconnectCauseCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisconnectCause(int i) {
        this(i, null, null, null, -1);
    }

    public DisconnectCause(int i, String str) {
        this(i, null, null, str, -1);
    }

    public DisconnectCause(int i, CharSequence charSequence, CharSequence charSequence2, String str) {
        this(i, charSequence, charSequence2, str, -1);
    }

    public DisconnectCause(int i, CharSequence charSequence, CharSequence charSequence2, String str, int i2) {
        this(i, charSequence, charSequence2, str, i2, 36, 65535, null);
    }

    public DisconnectCause(int i, CharSequence charSequence, CharSequence charSequence2, String str, int i2, int i3, int i4, ImsReasonInfo imsReasonInfo) {
        this.mDisconnectCode = i;
        this.mDisconnectLabel = charSequence;
        this.mDisconnectDescription = charSequence2;
        this.mDisconnectReason = str;
        this.mToneToPlay = i2;
        this.mTelephonyDisconnectCause = i3;
        this.mTelephonyPreciseDisconnectCause = i4;
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

        public Builder(int i) {
            this.mDisconnectCode = i;
        }

        public Builder setLabel(CharSequence charSequence) {
            this.mDisconnectLabel = charSequence;
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            this.mDisconnectDescription = charSequence;
            return this;
        }

        public Builder setReason(String str) {
            this.mDisconnectReason = str;
            return this;
        }

        public Builder setTone(int i) {
            this.mToneToPlay = i;
            return this;
        }

        public Builder setTelephonyDisconnectCause(int i) {
            this.mTelephonyDisconnectCause = i;
            return this;
        }

        public Builder setTelephonyPreciseDisconnectCause(int i) {
            this.mTelephonyPreciseDisconnectCause = i;
            return this;
        }

        public Builder setImsReasonInfo(ImsReasonInfo imsReasonInfo) {
            this.mImsReasonInfo = imsReasonInfo;
            return this;
        }

        public DisconnectCause build() {
            return new DisconnectCause(this.mDisconnectCode, this.mDisconnectLabel, this.mDisconnectDescription, this.mDisconnectReason, this.mToneToPlay, this.mTelephonyDisconnectCause, this.mTelephonyPreciseDisconnectCause, this.mImsReasonInfo);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDisconnectCode);
        TextUtils.writeToParcel(this.mDisconnectLabel, parcel, i);
        TextUtils.writeToParcel(this.mDisconnectDescription, parcel, i);
        parcel.writeString(this.mDisconnectReason);
        parcel.writeInt(this.mToneToPlay);
        parcel.writeInt(this.mTelephonyDisconnectCause);
        parcel.writeInt(this.mTelephonyPreciseDisconnectCause);
        parcel.writeParcelable(this.mImsReasonInfo, 0);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.mDisconnectCode)) + Objects.hashCode(this.mDisconnectLabel) + Objects.hashCode(this.mDisconnectDescription) + Objects.hashCode(this.mDisconnectReason) + Objects.hashCode(Integer.valueOf(this.mToneToPlay)) + Objects.hashCode(Integer.valueOf(this.mTelephonyDisconnectCause)) + Objects.hashCode(Integer.valueOf(this.mTelephonyPreciseDisconnectCause)) + Objects.hashCode(this.mImsReasonInfo);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DisconnectCause) {
            DisconnectCause disconnectCause = (DisconnectCause) obj;
            if (Objects.equals(Integer.valueOf(this.mDisconnectCode), Integer.valueOf(disconnectCause.getCode())) && Objects.equals(this.mDisconnectLabel, disconnectCause.getLabel()) && Objects.equals(this.mDisconnectDescription, disconnectCause.getDescription()) && Objects.equals(this.mDisconnectReason, disconnectCause.getReason()) && Objects.equals(Integer.valueOf(this.mToneToPlay), Integer.valueOf(disconnectCause.getTone())) && Objects.equals(Integer.valueOf(this.mTelephonyDisconnectCause), Integer.valueOf(disconnectCause.getTelephonyDisconnectCause())) && Objects.equals(Integer.valueOf(this.mTelephonyPreciseDisconnectCause), Integer.valueOf(disconnectCause.getTelephonyPreciseDisconnectCause())) && Objects.equals(this.mImsReasonInfo, disconnectCause.getImsReasonInfo())) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String str;
        switch (this.mDisconnectCode) {
            case 0:
                str = "UNKNOWN";
                break;
            case 1:
                str = TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
                break;
            case 2:
                str = CalendarContract.ACCOUNT_TYPE_LOCAL;
                break;
            case 3:
                str = "REMOTE";
                break;
            case 4:
                str = "CANCELED";
                break;
            case 5:
                str = "MISSED";
                break;
            case 6:
                str = "REJECTED";
                break;
            case 7:
                str = "BUSY";
                break;
            case 8:
                str = "RESTRICTED";
                break;
            case 9:
                str = "OTHER";
                break;
            case 10:
                str = "CONNECTION_MANAGER_NOT_SUPPORTED";
                break;
            case 11:
                str = "ANSWERED_ELSEWHERE";
                break;
            case 12:
                str = "CALL_PULLED";
                break;
            default:
                str = "invalid code: " + this.mDisconnectCode;
                break;
        }
        CharSequence charSequence = this.mDisconnectLabel;
        String charSequence2 = charSequence == null ? "" : charSequence.toString();
        CharSequence charSequence3 = this.mDisconnectDescription;
        String charSequence4 = charSequence3 == null ? "" : charSequence3.toString();
        String str2 = this.mDisconnectReason;
        return "DisconnectCause [ Code: (" + str + ") Label: (" + charSequence2 + ") Description: (" + charSequence4 + ") Reason: (" + (str2 != null ? str2 : "") + ") Tone: (" + this.mToneToPlay + ")  TelephonyCause: " + this.mTelephonyDisconnectCause + "/" + this.mTelephonyPreciseDisconnectCause + " ImsReasonInfo: " + this.mImsReasonInfo + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
