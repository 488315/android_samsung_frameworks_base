package android.hardware.location;

import android.annotation.SystemApi;
import android.chre.flags.Flags;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.content.NativeLibraryHelper;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public class MemoryRegion implements Parcelable {
    public static final Parcelable.Creator<MemoryRegion> CREATOR = new Parcelable.Creator<MemoryRegion>() { // from class: android.hardware.location.MemoryRegion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemoryRegion createFromParcel(Parcel parcel) {
            return new MemoryRegion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MemoryRegion[] newArray(int i) {
            return new MemoryRegion[i];
        }
    };
    private boolean mIsExecutable;
    private boolean mIsReadable;
    private boolean mIsWritable;
    private int mSizeBytes;
    private int mSizeBytesFree;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCapacityBytes() {
        return this.mSizeBytes;
    }

    public int getFreeCapacityBytes() {
        return this.mSizeBytesFree;
    }

    public boolean isReadable() {
        return this.mIsReadable;
    }

    public boolean isWritable() {
        return this.mIsWritable;
    }

    public boolean isExecutable() {
        return this.mIsExecutable;
    }

    public String toString() {
        String str;
        String str2;
        String str3 = isReadable() ? "r" : NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        if (isWritable()) {
            str = str3 + "w";
        } else {
            str = str3 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        if (isExecutable()) {
            str2 = str + "x";
        } else {
            str2 = str + NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        return "[ " + this.mSizeBytesFree + "/ " + this.mSizeBytes + " ] : " + str2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MemoryRegion) {
            MemoryRegion memoryRegion = (MemoryRegion) obj;
            if (memoryRegion.getCapacityBytes() == this.mSizeBytes && memoryRegion.getFreeCapacityBytes() == this.mSizeBytesFree && memoryRegion.isReadable() == this.mIsReadable && memoryRegion.isWritable() == this.mIsWritable && memoryRegion.isExecutable() == this.mIsExecutable) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (!Flags.fixApiCheck()) {
            return super.hashCode();
        }
        return Objects.hash(Integer.valueOf(this.mSizeBytes), Integer.valueOf(this.mSizeBytesFree), Boolean.valueOf(this.mIsReadable), Boolean.valueOf(this.mIsWritable), Boolean.valueOf(this.mIsExecutable));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSizeBytes);
        parcel.writeInt(this.mSizeBytesFree);
        parcel.writeInt(this.mIsReadable ? 1 : 0);
        parcel.writeInt(this.mIsWritable ? 1 : 0);
        parcel.writeInt(this.mIsExecutable ? 1 : 0);
    }

    public MemoryRegion(Parcel parcel) {
        this.mSizeBytes = parcel.readInt();
        this.mSizeBytesFree = parcel.readInt();
        this.mIsReadable = parcel.readInt() != 0;
        this.mIsWritable = parcel.readInt() != 0;
        this.mIsExecutable = parcel.readInt() != 0;
    }
}
