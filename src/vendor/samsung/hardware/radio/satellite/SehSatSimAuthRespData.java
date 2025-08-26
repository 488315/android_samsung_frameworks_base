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
        int iDataPosition = parcel.dataPosition();
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
                this.result = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.resLen = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.res = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.ckLen = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.ck = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.ikLen = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.ik = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.kcLen = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.kc = parcel.readString();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.autsLen = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.auts = parcel.readString();
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
