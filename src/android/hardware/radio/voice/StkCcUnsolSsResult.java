package android.hardware.radio.voice;

import android.hardware.radio.RadioError$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class StkCcUnsolSsResult implements Parcelable {
    public static final Parcelable.Creator<StkCcUnsolSsResult> CREATOR = new Parcelable.Creator<StkCcUnsolSsResult>() { // from class: android.hardware.radio.voice.StkCcUnsolSsResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StkCcUnsolSsResult createFromParcel(Parcel parcel) {
            StkCcUnsolSsResult stkCcUnsolSsResult = new StkCcUnsolSsResult();
            stkCcUnsolSsResult.readFromParcel(parcel);
            return stkCcUnsolSsResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StkCcUnsolSsResult[] newArray(int i) {
            return new StkCcUnsolSsResult[i];
        }
    };
    public static final int REQUEST_TYPE_ACTIVATION = 0;
    public static final int REQUEST_TYPE_DEACTIVATION = 1;
    public static final int REQUEST_TYPE_ERASURE = 4;
    public static final int REQUEST_TYPE_INTERROGATION = 2;
    public static final int REQUEST_TYPE_REGISTRATION = 3;
    public static final int SERVICE_TYPE_ALL_BARRING = 16;
    public static final int SERVICE_TYPE_BAIC = 14;
    public static final int SERVICE_TYPE_BAIC_ROAMING = 15;
    public static final int SERVICE_TYPE_BAOC = 11;
    public static final int SERVICE_TYPE_BAOIC = 12;
    public static final int SERVICE_TYPE_BAOIC_EXC_HOME = 13;
    public static final int SERVICE_TYPE_CFU = 0;
    public static final int SERVICE_TYPE_CF_ALL = 4;
    public static final int SERVICE_TYPE_CF_ALL_CONDITIONAL = 5;
    public static final int SERVICE_TYPE_CF_BUSY = 1;
    public static final int SERVICE_TYPE_CF_NOT_REACHABLE = 3;
    public static final int SERVICE_TYPE_CF_NO_REPLY = 2;
    public static final int SERVICE_TYPE_CLIP = 6;
    public static final int SERVICE_TYPE_CLIR = 7;
    public static final int SERVICE_TYPE_COLP = 8;
    public static final int SERVICE_TYPE_COLR = 9;
    public static final int SERVICE_TYPE_INCOMING_BARRING = 18;
    public static final int SERVICE_TYPE_OUTGOING_BARRING = 17;
    public static final int SERVICE_TYPE_WAIT = 10;
    public static final int SUPP_SERVICE_CLASS_DATA = 2;
    public static final int SUPP_SERVICE_CLASS_DATA_ASYNC = 32;
    public static final int SUPP_SERVICE_CLASS_DATA_SYNC = 16;
    public static final int SUPP_SERVICE_CLASS_FAX = 4;
    public static final int SUPP_SERVICE_CLASS_MAX = 128;
    public static final int SUPP_SERVICE_CLASS_NONE = 0;
    public static final int SUPP_SERVICE_CLASS_PACKET = 64;
    public static final int SUPP_SERVICE_CLASS_PAD = 128;
    public static final int SUPP_SERVICE_CLASS_SMS = 8;
    public static final int SUPP_SERVICE_CLASS_VOICE = 1;
    public static final int TELESERVICE_TYPE_ALL_DATA_TELESERVICES = 3;
    public static final int TELESERVICE_TYPE_ALL_TELESERVICES_EXCEPT_SMS = 5;
    public static final int TELESERVICE_TYPE_ALL_TELESEVICES = 1;
    public static final int TELESERVICE_TYPE_ALL_TELE_AND_BEARER_SERVICES = 0;
    public static final int TELESERVICE_TYPE_SMS_SERVICES = 4;
    public static final int TELESERVICE_TYPE_TELEPHONY = 2;
    public CfData[] cfData;
    public SsInfoData[] ssInfo;
    public int serviceType = 0;
    public int requestType = 0;
    public int teleserviceType = 0;
    public int serviceClass = 0;
    public int result = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.serviceType);
        parcel.writeInt(this.requestType);
        parcel.writeInt(this.teleserviceType);
        parcel.writeInt(this.serviceClass);
        parcel.writeInt(this.result);
        parcel.writeTypedArray(this.ssInfo, i);
        parcel.writeTypedArray(this.cfData, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.serviceType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.requestType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.teleserviceType = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.serviceClass = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.result = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ssInfo = (SsInfoData[]) parcel.createTypedArray(SsInfoData.CREATOR);
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.cfData = (CfData[]) parcel.createTypedArray(CfData.CREATOR);
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("serviceType: " + this.serviceType);
        stringJoiner.add("requestType: " + this.requestType);
        stringJoiner.add("teleserviceType: " + this.teleserviceType);
        stringJoiner.add("serviceClass: " + this.serviceClass);
        stringJoiner.add("result: " + RadioError$$.toString(this.result));
        stringJoiner.add("ssInfo: " + Arrays.toString(this.ssInfo));
        stringJoiner.add("cfData: " + Arrays.toString(this.cfData));
        return "StkCcUnsolSsResult" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cfData) | describeContents(this.ssInfo);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
