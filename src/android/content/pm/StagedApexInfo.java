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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.moduleName);
        parcel.writeString(this.diskImagePath);
        parcel.writeLong(this.versionCode);
        parcel.writeString(this.versionName);
        parcel.writeBoolean(this.hasClassPathJars);
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
                this.moduleName = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.diskImagePath = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.versionCode = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.versionName = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.hasClassPathJars = parcel.readBoolean();
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
