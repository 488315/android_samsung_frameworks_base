package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.uicc.IccUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

@SystemApi
/* loaded from: classes4.dex */
public final class SmsCbEtwsInfo implements Parcelable {
    public static final Parcelable.Creator<SmsCbEtwsInfo> CREATOR = new Parcelable.Creator<SmsCbEtwsInfo>() { // from class: android.telephony.SmsCbEtwsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbEtwsInfo createFromParcel(Parcel parcel) {
            return new SmsCbEtwsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbEtwsInfo[] newArray(int i) {
            return new SmsCbEtwsInfo[i];
        }
    };
    public static final int ETWS_WARNING_TYPE_EARTHQUAKE = 0;
    public static final int ETWS_WARNING_TYPE_EARTHQUAKE_AND_TSUNAMI = 2;
    public static final int ETWS_WARNING_TYPE_OTHER_EMERGENCY = 4;
    public static final int ETWS_WARNING_TYPE_TEST_MESSAGE = 3;
    public static final int ETWS_WARNING_TYPE_TSUNAMI = 1;
    public static final int ETWS_WARNING_TYPE_UNKNOWN = -1;
    private final boolean mIsEmergencyUserAlert;
    private final boolean mIsPopupAlert;
    private final boolean mIsPrimary;
    private final byte[] mWarningSecurityInformation;
    private final int mWarningType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface WarningType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmsCbEtwsInfo(int i, boolean z, boolean z2, boolean z3, byte[] bArr) {
        this.mWarningType = i;
        this.mIsEmergencyUserAlert = z;
        this.mIsPopupAlert = z2;
        this.mIsPrimary = z3;
        this.mWarningSecurityInformation = bArr;
    }

    SmsCbEtwsInfo(Parcel parcel) {
        this.mWarningType = parcel.readInt();
        this.mIsEmergencyUserAlert = parcel.readInt() != 0;
        this.mIsPopupAlert = parcel.readInt() != 0;
        this.mIsPrimary = parcel.readInt() != 0;
        this.mWarningSecurityInformation = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mWarningType);
        parcel.writeInt(this.mIsEmergencyUserAlert ? 1 : 0);
        parcel.writeInt(this.mIsPopupAlert ? 1 : 0);
        parcel.writeInt(this.mIsPrimary ? 1 : 0);
        parcel.writeByteArray(this.mWarningSecurityInformation);
    }

    public int getWarningType() {
        return this.mWarningType;
    }

    public boolean isEmergencyUserAlert() {
        return this.mIsEmergencyUserAlert;
    }

    public boolean isPopupAlert() {
        return this.mIsPopupAlert;
    }

    public boolean isPrimary() {
        return this.mIsPrimary;
    }

    public long getPrimaryNotificationTimestamp() {
        byte[] bArr = this.mWarningSecurityInformation;
        if (bArr != null && bArr.length >= 7) {
            int gsmBcdByteToInt = IccUtils.gsmBcdByteToInt(bArr[0]);
            int gsmBcdByteToInt2 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[1]);
            int gsmBcdByteToInt3 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[2]);
            int gsmBcdByteToInt4 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[3]);
            int gsmBcdByteToInt5 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[4]);
            int gsmBcdByteToInt6 = IccUtils.gsmBcdByteToInt(this.mWarningSecurityInformation[5]);
            byte b = this.mWarningSecurityInformation[6];
            int gsmBcdByteToInt7 = IccUtils.gsmBcdByteToInt((byte) (b & (-9)));
            if ((b & 8) != 0) {
                gsmBcdByteToInt7 = -gsmBcdByteToInt7;
            }
            try {
                return (LocalDateTime.of(gsmBcdByteToInt + 2000, gsmBcdByteToInt2, gsmBcdByteToInt3, gsmBcdByteToInt4, gsmBcdByteToInt5, gsmBcdByteToInt6).toEpochSecond(ZoneOffset.UTC) - (gsmBcdByteToInt7 * 900)) * 1000;
            } catch (DateTimeException unused) {
            }
        }
        return 0L;
    }

    public byte[] getPrimaryNotificationSignature() {
        byte[] bArr = this.mWarningSecurityInformation;
        if (bArr == null || bArr.length < 50) {
            return null;
        }
        return Arrays.copyOfRange(bArr, 7, 50);
    }

    public String toString() {
        return "SmsCbEtwsInfo{warningType=" + this.mWarningType + ", emergencyUserAlert=" + this.mIsEmergencyUserAlert + ", activatePopup=" + this.mIsPopupAlert + '}';
    }
}
