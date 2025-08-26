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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mMccMnc);
        parcel.writeIntArray(this.mBands);
        parcel.writeIntArray(this.mEarfcs);
        parcel.writeTypedArray(this.satelliteInfos, i);
        parcel.writeIntArray(this.tagIds);
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
                this.mMccMnc = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mBands = parcel.createIntArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mEarfcs = parcel.createIntArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.satelliteInfos = (SatelliteInfo[]) parcel.createTypedArray(SatelliteInfo.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.tagIds = parcel.createIntArray();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
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
