package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatSimAuthRespData implements Parcelable {
    public static final Parcelable.Creator<SehSatSimAuthRespData> CREATOR = new Parcelable.Creator<SehSatSimAuthRespData>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatSimAuthRespData.1
        @Override // android.os.Parcelable.Creator
        public SehSatSimAuthRespData createFromParcel(Parcel parcel) {
            SehSatSimAuthRespData sehSatSimAuthRespData = new SehSatSimAuthRespData();
            sehSatSimAuthRespData.readFromParcel(parcel);
            return sehSatSimAuthRespData;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatSimAuthRespData[] newArray(int i) {
            return new SehSatSimAuthRespData[i];
        }
    };
    public String auts;
    public String ck;
    public String ik;
    public String kc;
    public String res;
    public int result = 0;
    public int resLen = 0;
    public int ckLen = 0;
    public int ikLen = 0;
    public int kcLen = 0;
    public int autsLen = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.result);
        parcel.writeInt(this.resLen);
        parcel.writeString(this.res);
        parcel.writeInt(this.ckLen);
        parcel.writeString(this.ck);
        parcel.writeInt(this.ikLen);
        parcel.writeString(this.ik);
        parcel.writeInt(this.kcLen);
        parcel.writeString(this.kc);
        parcel.writeInt(this.autsLen);
        parcel.writeString(this.auts);
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
                this.result = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.resLen = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.res = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.ckLen = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.ck = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ikLen = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.ik = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.kcLen = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.kc = parcel.readString();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.autsLen = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.auts = parcel.readString();
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
        stringJoiner.add("result: " + this.result);
        stringJoiner.add("resLen: " + this.resLen);
        stringJoiner.add("res: " + Objects.toString(this.res));
        stringJoiner.add("ckLen: " + this.ckLen);
        stringJoiner.add("ck: " + Objects.toString(this.ck));
        stringJoiner.add("ikLen: " + this.ikLen);
        stringJoiner.add("ik: " + Objects.toString(this.ik));
        stringJoiner.add("kcLen: " + this.kcLen);
        stringJoiner.add("kc: " + Objects.toString(this.kc));
        stringJoiner.add("autsLen: " + this.autsLen);
        stringJoiner.add("auts: " + Objects.toString(this.auts));
        return "SehSatSimAuthRespData" + stringJoiner.toString();
    }
}
