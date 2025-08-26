package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaInformationRecord implements Parcelable {

    @Deprecated
    public static final int CDMA_MAX_NUMBER_OF_INFO_RECS = 10;
    public static final Parcelable.Creator<CdmaInformationRecord> CREATOR = new Parcelable.Creator<CdmaInformationRecord>() { // from class: android.hardware.radio.voice.CdmaInformationRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaInformationRecord createFromParcel(Parcel parcel) {
            CdmaInformationRecord cdmaInformationRecord = new CdmaInformationRecord();
            cdmaInformationRecord.readFromParcel(parcel);
            return cdmaInformationRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaInformationRecord[] newArray(int i) {
            return new CdmaInformationRecord[i];
        }
    };

    @Deprecated
    public static final int NAME_CALLED_PARTY_NUMBER = 1;

    @Deprecated
    public static final int NAME_CALLING_PARTY_NUMBER = 2;

    @Deprecated
    public static final int NAME_CONNECTED_NUMBER = 3;

    @Deprecated
    public static final int NAME_DISPLAY = 0;

    @Deprecated
    public static final int NAME_EXTENDED_DISPLAY = 7;

    @Deprecated
    public static final int NAME_LINE_CONTROL = 6;

    @Deprecated
    public static final int NAME_REDIRECTING_NUMBER = 5;

    @Deprecated
    public static final int NAME_SIGNAL = 4;

    @Deprecated
    public static final int NAME_T53_AUDIO_CONTROL = 10;

    @Deprecated
    public static final int NAME_T53_CLIR = 8;

    @Deprecated
    public static final int NAME_T53_RELEASE = 9;

    @Deprecated
    public CdmaT53AudioControlInfoRecord[] audioCtrl;

    @Deprecated
    public CdmaT53ClirInfoRecord[] clir;

    @Deprecated
    public CdmaDisplayInfoRecord[] display;

    @Deprecated
    public CdmaLineControlInfoRecord[] lineCtrl;

    @Deprecated
    public int name = 0;

    @Deprecated
    public CdmaNumberInfoRecord[] number;

    @Deprecated
    public CdmaRedirectingNumberInfoRecord[] redir;

    @Deprecated
    public CdmaSignalInfoRecord[] signal;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.name);
        parcel.writeTypedArray(this.display, i);
        parcel.writeTypedArray(this.number, i);
        parcel.writeTypedArray(this.signal, i);
        parcel.writeTypedArray(this.redir, i);
        parcel.writeTypedArray(this.lineCtrl, i);
        parcel.writeTypedArray(this.clir, i);
        parcel.writeTypedArray(this.audioCtrl, i);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.name = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.display = (CdmaDisplayInfoRecord[]) parcel.createTypedArray(CdmaDisplayInfoRecord.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.number = (CdmaNumberInfoRecord[]) parcel.createTypedArray(CdmaNumberInfoRecord.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.signal = (CdmaSignalInfoRecord[]) parcel.createTypedArray(CdmaSignalInfoRecord.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.redir = (CdmaRedirectingNumberInfoRecord[]) parcel.createTypedArray(CdmaRedirectingNumberInfoRecord.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.lineCtrl = (CdmaLineControlInfoRecord[]) parcel.createTypedArray(CdmaLineControlInfoRecord.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.clir = (CdmaT53ClirInfoRecord[]) parcel.createTypedArray(CdmaT53ClirInfoRecord.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.audioCtrl = (CdmaT53AudioControlInfoRecord[]) parcel.createTypedArray(CdmaT53AudioControlInfoRecord.CREATOR);
                                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("name: " + this.name);
        stringJoiner.add("display: " + Arrays.toString(this.display));
        stringJoiner.add("number: " + Arrays.toString(this.number));
        stringJoiner.add("signal: " + Arrays.toString(this.signal));
        stringJoiner.add("redir: " + Arrays.toString(this.redir));
        stringJoiner.add("lineCtrl: " + Arrays.toString(this.lineCtrl));
        stringJoiner.add("clir: " + Arrays.toString(this.clir));
        stringJoiner.add("audioCtrl: " + Arrays.toString(this.audioCtrl));
        return "CdmaInformationRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.audioCtrl) | describeContents(this.display) | describeContents(this.number) | describeContents(this.signal) | describeContents(this.redir) | describeContents(this.lineCtrl) | describeContents(this.clir);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
