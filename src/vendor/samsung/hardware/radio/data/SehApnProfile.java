package vendor.samsung.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehApnProfile implements Parcelable {
    public static final Parcelable.Creator<SehApnProfile> CREATOR = new Parcelable.Creator<SehApnProfile>() { // from class: vendor.samsung.hardware.radio.data.SehApnProfile.1
        @Override // android.os.Parcelable.Creator
        public SehApnProfile createFromParcel(Parcel parcel) {
            SehApnProfile sehApnProfile = new SehApnProfile();
            sehApnProfile.readFromParcel(parcel);
            return sehApnProfile;
        }

        @Override // android.os.Parcelable.Creator
        public SehApnProfile[] newArray(int i) {
            return new SehApnProfile[i];
        }
    };
    public String apn;
    public String auth;
    public String proto;
    public String pw;
    public String roamingProto;
    public String user;

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
        parcel.writeString(this.apn);
        parcel.writeString(this.proto);
        parcel.writeString(this.roamingProto);
        parcel.writeString(this.user);
        parcel.writeString(this.pw);
        parcel.writeString(this.auth);
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
                this.apn = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.proto = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.roamingProto = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.user = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.pw = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.auth = parcel.readString();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
