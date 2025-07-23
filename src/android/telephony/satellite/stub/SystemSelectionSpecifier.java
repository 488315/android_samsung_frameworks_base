package android.telephony.satellite.stub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class SystemSelectionSpecifier implements Parcelable {
    public static final Parcelable.Creator<SystemSelectionSpecifier> CREATOR = new Parcelable.Creator<SystemSelectionSpecifier>() { // from class: android.telephony.satellite.stub.SystemSelectionSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemSelectionSpecifier createFromParcel(Parcel parcel) {
            SystemSelectionSpecifier systemSelectionSpecifier = new SystemSelectionSpecifier();
            systemSelectionSpecifier.readFromParcel(parcel);
            return systemSelectionSpecifier;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SystemSelectionSpecifier[] newArray(int i) {
            return new SystemSelectionSpecifier[i];
        }
    };
    public int[] mBands;
    public int[] mEarfcs;
    public String mMccMnc;
    public SatelliteInfo[] satelliteInfos;
    public int[] tagIds;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mMccMnc);
        parcel.writeIntArray(this.mBands);
        parcel.writeIntArray(this.mEarfcs);
        parcel.writeTypedArray(this.satelliteInfos, i);
        parcel.writeIntArray(this.tagIds);
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
                this.mMccMnc = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.mBands = parcel.createIntArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.mEarfcs = parcel.createIntArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.satelliteInfos = (SatelliteInfo[]) parcel.createTypedArray(SatelliteInfo.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.tagIds = parcel.createIntArray();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.satelliteInfos);
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
