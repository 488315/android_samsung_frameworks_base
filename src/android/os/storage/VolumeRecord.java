package android.os.storage;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.DebugUtils;
import android.util.TimeUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes3.dex */
public class VolumeRecord implements Parcelable {
    public static final Parcelable.Creator<VolumeRecord> CREATOR = new Parcelable.Creator<VolumeRecord>() { // from class: android.os.storage.VolumeRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeRecord createFromParcel(Parcel parcel) {
            return new VolumeRecord(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumeRecord[] newArray(int i) {
            return new VolumeRecord[i];
        }
    };
    public static final String EXTRA_FS_UUID = "android.os.storage.extra.FS_UUID";
    public static final int USER_FLAG_INITED = 1;
    public static final int USER_FLAG_SNOOZED = 2;
    public long createdMillis;
    public final String fsUuid;
    public long lastBenchMillis;
    public long lastSeenMillis;
    public long lastTrimMillis;
    public String nickname;
    public String partGuid;
    public final int type;
    public int userFlags;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VolumeRecord(int i, String str) {
        this.type = i;
        this.fsUuid = (String) Preconditions.checkNotNull(str);
    }

    public VolumeRecord(Parcel parcel) {
        this.type = parcel.readInt();
        this.fsUuid = parcel.readString();
        this.partGuid = parcel.readString();
        this.nickname = parcel.readString();
        this.userFlags = parcel.readInt();
        this.createdMillis = parcel.readLong();
        this.lastSeenMillis = parcel.readLong();
        this.lastTrimMillis = parcel.readLong();
        this.lastBenchMillis = parcel.readLong();
    }

    public int getType() {
        return this.type;
    }

    public String getFsUuid() {
        return this.fsUuid;
    }

    public String getNormalizedFsUuid() {
        String str = this.fsUuid;
        if (str != null) {
            return str.toLowerCase(Locale.US);
        }
        return null;
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean isInited() {
        return (this.userFlags & 1) != 0;
    }

    public boolean isSnoozed() {
        return (this.userFlags & 2) != 0;
    }

    public StorageVolume buildStorageVolume(Context context) {
        String str = "unknown:" + this.fsUuid;
        File file = new File("/dev/null");
        File file2 = new File("/dev/null");
        UserHandle userHandle = new UserHandle(-10000);
        String string = this.nickname;
        if (string == null) {
            string = context.getString(17039374);
        }
        return new StorageVolume(str, file, file2, string, false, true, false, false, false, 0L, userHandle, null, this.fsUuid, "unknown");
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VolumeRecord:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("type", DebugUtils.valueToString(VolumeInfo.class, "TYPE_", this.type));
        indentingPrintWriter.printPair("fsUuid", this.fsUuid);
        indentingPrintWriter.printPair("partGuid", this.partGuid);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("nickname", this.nickname);
        indentingPrintWriter.printPair("userFlags", DebugUtils.flagsToString(VolumeRecord.class, "USER_FLAG_", this.userFlags));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("createdMillis", TimeUtils.formatForLogging(this.createdMillis));
        indentingPrintWriter.printPair("lastSeenMillis", TimeUtils.formatForLogging(this.lastSeenMillis));
        indentingPrintWriter.printPair("lastTrimMillis", TimeUtils.formatForLogging(this.lastTrimMillis));
        indentingPrintWriter.printPair("lastBenchMillis", TimeUtils.formatForLogging(this.lastBenchMillis));
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public VolumeRecord m3717clone() {
        Parcel parcelObtain = Parcel.obtain();
        try {
            writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            return CREATOR.createFromParcel(parcelObtain);
        } finally {
            parcelObtain.recycle();
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof VolumeRecord) {
            return Objects.equals(this.fsUuid, ((VolumeRecord) obj).fsUuid);
        }
        return false;
    }

    public int hashCode() {
        return this.fsUuid.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeString(this.fsUuid);
        parcel.writeString(this.partGuid);
        parcel.writeString(this.nickname);
        parcel.writeInt(this.userFlags);
        parcel.writeLong(this.createdMillis);
        parcel.writeLong(this.lastSeenMillis);
        parcel.writeLong(this.lastTrimMillis);
        parcel.writeLong(this.lastBenchMillis);
    }
}
