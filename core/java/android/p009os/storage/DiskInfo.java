package android.p009os.storage;

import android.content.res.Resources;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.text.TextUtils;
import android.util.DebugUtils;
import com.android.internal.C4337R;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.samsung.android.knox.analytics.database.Contract;
import java.io.CharArrayWriter;
import java.util.Objects;

/* loaded from: classes3.dex */
public class DiskInfo implements Parcelable {
    public static final String ACTION_DISK_SCANNED = "android.os.storage.action.DISK_SCANNED";
    public static final Parcelable.Creator<DiskInfo> CREATOR = new Parcelable.Creator<DiskInfo>() { // from class: android.os.storage.DiskInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DiskInfo createFromParcel(Parcel in) {
            return new DiskInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DiskInfo[] newArray(int size) {
            return new DiskInfo[size];
        }
    };
    public static final String EXTRA_DISK_ID = "android.os.storage.extra.DISK_ID";
    public static final String EXTRA_VOLUME_COUNT = "android.os.storage.extra.VOLUME_COUNT";
    public static final int FLAG_ADOPTABLE = 1;
    public static final int FLAG_DEFAULT_PRIMARY = 2;
    public static final int FLAG_SD = 4;
    public static final int FLAG_STUB_VISIBLE = 64;
    public static final int FLAG_USB = 8;
    public final int flags;

    /* renamed from: id */
    public final String f344id;
    public String label;
    public long size;
    public String sysPath;
    public int volumeCount;

    public DiskInfo(String id, int flags) {
        this.f344id = (String) Preconditions.checkNotNull(id);
        this.flags = flags;
    }

    public DiskInfo(Parcel parcel) {
        this.f344id = parcel.readString();
        this.flags = parcel.readInt();
        this.size = parcel.readLong();
        this.label = parcel.readString();
        this.volumeCount = parcel.readInt();
        this.sysPath = parcel.readString();
    }

    public String getId() {
        return this.f344id;
    }

    private boolean isInteresting(String label) {
        return (TextUtils.isEmpty(label) || label.equalsIgnoreCase("ata") || label.toLowerCase().contains("generic") || label.toLowerCase().startsWith("usb") || label.toLowerCase().startsWith("multiple")) ? false : true;
    }

    public String getDescription() {
        Resources res = Resources.getSystem();
        int i = this.flags;
        if ((i & 4) != 0) {
            if (isInteresting(this.label)) {
                return res.getString(C4337R.string.storage_sd_card_label, this.label);
            }
            return res.getString(C4337R.string.storage_sd_card);
        }
        if ((i & 8) != 0) {
            if (isInteresting(this.label)) {
                return res.getString(C4337R.string.storage_usb_drive_label, this.label);
            }
            return res.getString(C4337R.string.storage_usb_drive);
        }
        return null;
    }

    public String getShortDescription() {
        Resources res = Resources.getSystem();
        if (isSd()) {
            return res.getString(C4337R.string.storage_sd_card);
        }
        if (isUsb()) {
            return res.getString(C4337R.string.storage_usb_drive);
        }
        return null;
    }

    public boolean isAdoptable() {
        return (this.flags & 1) != 0;
    }

    public boolean isDefaultPrimary() {
        return (this.flags & 2) != 0;
    }

    public boolean isSd() {
        return (this.flags & 4) != 0;
    }

    public boolean isUsb() {
        return (this.flags & 8) != 0;
    }

    public boolean isStubVisible() {
        return (this.flags & 64) != 0;
    }

    public String toString() {
        CharArrayWriter writer = new CharArrayWriter();
        dump(new IndentingPrintWriter(writer, "    ", 80));
        return writer.toString();
    }

    public void dump(IndentingPrintWriter pw) {
        pw.println("DiskInfo{" + this.f344id + "}:");
        pw.increaseIndent();
        pw.printPair("flags", DebugUtils.flagsToString(getClass(), "FLAG_", this.flags));
        pw.printPair(Contract.DatabaseSize.PATH, Long.valueOf(this.size));
        pw.printPair("label", this.label);
        pw.printPair("volumeCount", Integer.valueOf(this.volumeCount));
        pw.println();
        pw.printPair("sysPath", this.sysPath);
        pw.decreaseIndent();
        pw.println();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public DiskInfo m3765clone() {
        Parcel temp = Parcel.obtain();
        try {
            writeToParcel(temp, 0);
            temp.setDataPosition(0);
            return CREATOR.createFromParcel(temp);
        } finally {
            temp.recycle();
        }
    }

    public boolean equals(Object o) {
        if (o instanceof DiskInfo) {
            return Objects.equals(this.f344id, ((DiskInfo) o).f344id);
        }
        return false;
    }

    public int hashCode() {
        return this.f344id.hashCode();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.f344id);
        parcel.writeInt(this.flags);
        parcel.writeLong(this.size);
        parcel.writeString(this.label);
        parcel.writeInt(this.volumeCount);
        parcel.writeString(this.sysPath);
    }
}
