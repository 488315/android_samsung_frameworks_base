package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SignalStrength implements Parcelable {
    public static final Parcelable.Creator<SignalStrength> CREATOR = new Parcelable.Creator<SignalStrength>() { // from class: android.hardware.radio.network.SignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalStrength createFromParcel(Parcel parcel) {
            SignalStrength signalStrength = new SignalStrength();
            signalStrength.readFromParcel(parcel);
            return signalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SignalStrength[] newArray(int i) {
            return new SignalStrength[i];
        }
    };

    @Deprecated
    public CdmaSignalStrength cdma;

    @Deprecated
    public EvdoSignalStrength evdo;
    public GsmSignalStrength gsm;
    public LteSignalStrength lte;
    public NrSignalStrength nr;
    public TdscdmaSignalStrength tdscdma;
    public WcdmaSignalStrength wcdma;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.gsm, i);
        parcel.writeTypedObject(this.cdma, i);
        parcel.writeTypedObject(this.evdo, i);
        parcel.writeTypedObject(this.lte, i);
        parcel.writeTypedObject(this.tdscdma, i);
        parcel.writeTypedObject(this.wcdma, i);
        parcel.writeTypedObject(this.nr, i);
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
                this.gsm = (GsmSignalStrength) parcel.readTypedObject(GsmSignalStrength.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.cdma = (CdmaSignalStrength) parcel.readTypedObject(CdmaSignalStrength.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.evdo = (EvdoSignalStrength) parcel.readTypedObject(EvdoSignalStrength.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.lte = (LteSignalStrength) parcel.readTypedObject(LteSignalStrength.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.tdscdma = (TdscdmaSignalStrength) parcel.readTypedObject(TdscdmaSignalStrength.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.wcdma = (WcdmaSignalStrength) parcel.readTypedObject(WcdmaSignalStrength.CREATOR);
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.nr = (NrSignalStrength) parcel.readTypedObject(NrSignalStrength.CREATOR);
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
        stringJoiner.add("gsm: " + Objects.toString(this.gsm));
        stringJoiner.add("cdma: " + Objects.toString(this.cdma));
        stringJoiner.add("evdo: " + Objects.toString(this.evdo));
        stringJoiner.add("lte: " + Objects.toString(this.lte));
        stringJoiner.add("tdscdma: " + Objects.toString(this.tdscdma));
        stringJoiner.add("wcdma: " + Objects.toString(this.wcdma));
        stringJoiner.add("nr: " + Objects.toString(this.nr));
        return "SignalStrength" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.nr) | describeContents(this.gsm) | describeContents(this.cdma) | describeContents(this.evdo) | describeContents(this.lte) | describeContents(this.tdscdma) | describeContents(this.wcdma);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
