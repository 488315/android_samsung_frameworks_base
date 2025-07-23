package android.content.pm;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class StagedApexInfo implements Parcelable {
    public static final Parcelable.Creator<StagedApexInfo> CREATOR = new Parcelable.Creator<StagedApexInfo>() { // from class: android.content.pm.StagedApexInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StagedApexInfo createFromParcel(Parcel parcel) {
            StagedApexInfo stagedApexInfo = new StagedApexInfo();
            stagedApexInfo.readFromParcel(parcel);
            return stagedApexInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StagedApexInfo[] newArray(int i) {
            return new StagedApexInfo[i];
        }
    };
    public String diskImagePath;
    public String moduleName;
    public String versionName;
    public long versionCode = 0;
    public boolean hasClassPathJars = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.moduleName);
        parcel.writeString(this.diskImagePath);
        parcel.writeLong(this.versionCode);
        parcel.writeString(this.versionName);
        parcel.writeBoolean(this.hasClassPathJars);
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
                    this.diskImagePath = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.versionCode = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.versionName = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.hasClassPathJars = parcel.readBoolean();
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof StagedApexInfo)) {
            return false;
        }
        StagedApexInfo stagedApexInfo = (StagedApexInfo) obj;
        return Objects.deepEquals(this.moduleName, stagedApexInfo.moduleName) && Objects.deepEquals(this.diskImagePath, stagedApexInfo.diskImagePath) && Objects.deepEquals(Long.valueOf(this.versionCode), Long.valueOf(stagedApexInfo.versionCode)) && Objects.deepEquals(this.versionName, stagedApexInfo.versionName) && Objects.deepEquals(Boolean.valueOf(this.hasClassPathJars), Boolean.valueOf(stagedApexInfo.hasClassPathJars));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.moduleName, this.diskImagePath, Long.valueOf(this.versionCode), this.versionName, Boolean.valueOf(this.hasClassPathJars)).toArray());
    }
}
