package android.apex;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class CompressedApexInfo implements Parcelable {
    public static final Parcelable.Creator<CompressedApexInfo> CREATOR = new Parcelable.Creator<CompressedApexInfo>() { // from class: android.apex.CompressedApexInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompressedApexInfo createFromParcel(Parcel parcel) {
            CompressedApexInfo compressedApexInfo = new CompressedApexInfo();
            compressedApexInfo.readFromParcel(parcel);
            return compressedApexInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompressedApexInfo[] newArray(int i) {
            return new CompressedApexInfo[i];
        }
    };
    public String moduleName;
    public long versionCode = 0;
    public long decompressedSize = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.moduleName);
        parcel.writeLong(this.versionCode);
        parcel.writeLong(this.decompressedSize);
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
                this.moduleName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.versionCode = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.decompressedSize = parcel.readLong();
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
