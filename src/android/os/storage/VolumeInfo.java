package android.os.storage;

import android.app.backup.FullBackup;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.input.KeyboardLayout;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.R;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.samsung.android.security.SemSdCardEncryption;
import java.io.CharArrayWriter;
import java.io.File;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes3.dex */
public class VolumeInfo implements Parcelable {
    public static final String ACTION_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    public static final Parcelable.Creator<VolumeInfo> CREATOR;
    private static final String DOCUMENT_AUTHORITY = "com.android.externalstorage.documents";
    private static final String DOCUMENT_ROOT_PRIMARY_EMULATED = "primary";
    public static final String EXTRA_VOLUME_ID = "android.os.storage.extra.VOLUME_ID";
    public static final String EXTRA_VOLUME_STATE = "android.os.storage.extra.VOLUME_STATE";
    public static final String ID_EMULATED_INTERNAL = "emulated";
    public static final String ID_PRIVATE_INTERNAL = "private";
    public static final int MOUNT_FLAG_PRIMARY = 1;
    public static final int MOUNT_FLAG_VISIBLE_FOR_READ = 2;
    public static final int MOUNT_FLAG_VISIBLE_FOR_WRITE = 4;
    public static final int STATE_BAD_REMOVAL = 8;
    public static final int STATE_CHECKING = 1;
    public static final int STATE_EJECTING = 5;
    public static final int STATE_FORMATTING = 4;
    public static final int STATE_HIDDEN_MOUNT = 9;
    public static final int STATE_MOUNTED = 2;
    public static final int STATE_MOUNTED_READ_ONLY = 3;
    public static final int STATE_MOVE_MOUNT = 10;
    public static final int STATE_REMOVED = 7;
    public static final int STATE_UNMOUNTABLE = 6;
    public static final int STATE_UNMOUNTED = 0;
    public static final int TYPE_ASEC = 3;
    public static final int TYPE_EMULATED = 2;
    public static final int TYPE_OBB = 4;
    public static final int TYPE_PRIVATE = 1;
    public static final int TYPE_PUBLIC = 0;
    public static final int TYPE_STUB = 5;
    public final DiskInfo disk;
    public String fsLabel;
    public String fsType;
    public String fsUuid;
    public final String id;
    public String internalPath;
    public int mountFlags;
    public int mountUserId;
    public final String partGuid;
    public String path;
    public int state;
    public final int type;
    private static SparseArray<String> sStateToEnvironment = new SparseArray<>();
    private static ArrayMap<String, String> sEnvironmentToBroadcast = new ArrayMap<>();
    private static SparseIntArray sStateToDescrip = new SparseIntArray();
    private static final Comparator<VolumeInfo> sDescriptionComparator = new Comparator<VolumeInfo>() { // from class: android.os.storage.VolumeInfo.1
        @Override // java.util.Comparator
        public int compare(VolumeInfo volumeInfo, VolumeInfo volumeInfo2) {
            if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.getId())) {
                return -1;
            }
            if (volumeInfo.getDescription() == null) {
                return 1;
            }
            if (volumeInfo2.getDescription() == null) {
                return -1;
            }
            return volumeInfo.getDescription().compareTo(volumeInfo2.getDescription());
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        sStateToEnvironment.put(0, Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(1, Environment.MEDIA_CHECKING);
        sStateToEnvironment.put(2, Environment.MEDIA_MOUNTED);
        sStateToEnvironment.put(3, Environment.MEDIA_MOUNTED_READ_ONLY);
        sStateToEnvironment.put(4, Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(5, Environment.MEDIA_EJECTING);
        sStateToEnvironment.put(6, Environment.MEDIA_UNMOUNTABLE);
        sStateToEnvironment.put(7, Environment.MEDIA_REMOVED);
        sStateToEnvironment.put(8, Environment.MEDIA_BAD_REMOVAL);
        sStateToEnvironment.put(9, SemSdCardEncryption.VOLUME_STATE_HIDDEN);
        sStateToEnvironment.put(10, SemSdCardEncryption.MOVE_MOUNT);
        sEnvironmentToBroadcast.put(Environment.MEDIA_UNMOUNTED, Intent.ACTION_MEDIA_UNMOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_CHECKING, Intent.ACTION_MEDIA_CHECKING);
        sEnvironmentToBroadcast.put(Environment.MEDIA_MOUNTED, Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_MOUNTED_READ_ONLY, Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_EJECTING, Intent.ACTION_MEDIA_EJECT);
        sEnvironmentToBroadcast.put(Environment.MEDIA_UNMOUNTABLE, Intent.ACTION_MEDIA_UNMOUNTABLE);
        sEnvironmentToBroadcast.put(Environment.MEDIA_REMOVED, Intent.ACTION_MEDIA_REMOVED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_BAD_REMOVAL, Intent.ACTION_MEDIA_BAD_REMOVAL);
        sStateToDescrip.put(9, R.string.ext_media_status_mounted);
        sStateToDescrip.put(10, R.string.ext_media_status_mounted);
        sStateToDescrip.put(0, R.string.ext_media_status_unmounted);
        sStateToDescrip.put(1, R.string.ext_media_status_checking);
        sStateToDescrip.put(2, R.string.ext_media_status_mounted);
        sStateToDescrip.put(3, R.string.ext_media_status_mounted_ro);
        sStateToDescrip.put(4, R.string.ext_media_status_formatting);
        sStateToDescrip.put(5, R.string.ext_media_status_ejecting);
        sStateToDescrip.put(6, R.string.ext_media_status_unmountable);
        sStateToDescrip.put(7, R.string.ext_media_status_removed);
        sStateToDescrip.put(8, R.string.ext_media_status_bad_removal);
        CREATOR = new Parcelable.Creator<VolumeInfo>() { // from class: android.os.storage.VolumeInfo.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VolumeInfo createFromParcel(Parcel parcel) {
                return new VolumeInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VolumeInfo[] newArray(int i) {
                return new VolumeInfo[i];
            }
        };
    }

    public VolumeInfo(String str, int i, DiskInfo diskInfo, String str2) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.id = (String) Preconditions.checkNotNull(str);
        this.type = i;
        this.disk = diskInfo;
        this.partGuid = str2;
    }

    public VolumeInfo(Parcel parcel) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.id = parcel.readString8();
        this.type = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.disk = DiskInfo.CREATOR.createFromParcel(parcel);
        } else {
            this.disk = null;
        }
        this.partGuid = parcel.readString8();
        this.mountFlags = parcel.readInt();
        this.mountUserId = parcel.readInt();
        this.state = parcel.readInt();
        this.fsType = parcel.readString8();
        this.fsUuid = parcel.readString8();
        this.fsLabel = parcel.readString8();
        this.path = parcel.readString8();
        this.internalPath = parcel.readString8();
    }

    public VolumeInfo(VolumeInfo volumeInfo) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.id = volumeInfo.id;
        this.type = volumeInfo.type;
        this.disk = volumeInfo.disk;
        this.partGuid = volumeInfo.partGuid;
        this.mountFlags = volumeInfo.mountFlags;
        this.mountUserId = volumeInfo.mountUserId;
        this.state = volumeInfo.state;
        this.fsType = volumeInfo.fsType;
        this.fsUuid = volumeInfo.fsUuid;
        this.fsLabel = volumeInfo.fsLabel;
        this.path = volumeInfo.path;
        this.internalPath = volumeInfo.internalPath;
    }

    public static String getEnvironmentForState(int i) {
        String str = sStateToEnvironment.get(i);
        return str != null ? str : "unknown";
    }

    public static String getBroadcastForEnvironment(String str) {
        return sEnvironmentToBroadcast.get(str);
    }

    public static String getBroadcastForState(int i) {
        return getBroadcastForEnvironment(getEnvironmentForState(i));
    }

    public static Comparator<VolumeInfo> getDescriptionComparator() {
        return sDescriptionComparator;
    }

    public String getId() {
        return this.id;
    }

    public DiskInfo getDisk() {
        return this.disk;
    }

    public String getDiskId() {
        DiskInfo diskInfo = this.disk;
        if (diskInfo != null) {
            return diskInfo.id;
        }
        return null;
    }

    public int getType() {
        return this.type;
    }

    public int getState() {
        return this.state;
    }

    public int getStateDescription() {
        return sStateToDescrip.get(this.state, 0);
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

    public int getMountUserId() {
        return this.mountUserId;
    }

    public String getDescription() {
        if (ID_PRIVATE_INTERNAL.equals(this.id) || this.id.startsWith("emulated;")) {
            return Resources.getSystem().getString(R.string.storage_internal);
        }
        if (TextUtils.isEmpty(this.fsLabel)) {
            return null;
        }
        return this.fsLabel;
    }

    public boolean isMountedReadable() {
        int i = this.state;
        return i == 2 || i == 3;
    }

    public boolean isMountedWritable() {
        return this.state == 2;
    }

    public boolean isPrimary() {
        return (this.mountFlags & 1) != 0;
    }

    public boolean isPrimaryPhysical() {
        return isPrimary() && getType() == 0;
    }

    private boolean isVisibleForRead() {
        return (this.mountFlags & 2) != 0;
    }

    private boolean isVisibleForWrite() {
        return (this.mountFlags & 4) != 0;
    }

    public boolean isVisible() {
        return isVisibleForRead() || isVisibleForWrite();
    }

    private boolean isVolumeSupportedForUser(int i) {
        if (this.mountUserId != i) {
            return false;
        }
        int i2 = this.type;
        return i2 == 0 || i2 == 5 || i2 == 2;
    }

    public boolean isVisibleForUser(int i) {
        return isVolumeSupportedForUser(i) && isVisible();
    }

    public boolean isPrimaryEmulatedForUser(int i) {
        return this.id.equals("emulated;" + i);
    }

    public boolean isVisibleForRead(int i) {
        return isVolumeSupportedForUser(i) && isVisibleForRead();
    }

    public boolean isVisibleForWrite(int i) {
        return isVolumeSupportedForUser(i) && isVisibleForWrite();
    }

    public File getPath() {
        if (this.path != null) {
            return new File(this.path);
        }
        return null;
    }

    public File getInternalPath() {
        if (this.internalPath != null) {
            return new File(this.internalPath);
        }
        return null;
    }

    public File getPathForUser(int i) {
        if (this.path == null) {
            return null;
        }
        int i2 = this.type;
        if (i2 == 0 || i2 == 5) {
            return new File(this.path);
        }
        if (i2 == 2) {
            return new File(this.path, Integer.toString(i));
        }
        return null;
    }

    public File getInternalPathForUser(int i) {
        if (this.path == null) {
            return null;
        }
        int i2 = this.type;
        if (i2 == 0 || i2 == 5) {
            return new File(this.path.replace(FullBackup.MANAGED_EXTERNAL_SPECIFIC_TREE_TOKEN, "/mnt/media_rw/"));
        }
        return getPathForUser(i);
    }

    public StorageVolume buildStorageVolume(Context context, int i, boolean z) {
        String str;
        int buildStableMtpStorageId;
        UUID uuid;
        long j;
        boolean z2;
        boolean z3;
        String str2;
        String str3;
        boolean z4;
        UUID uuid2;
        StorageManager storageManager = (StorageManager) context.getSystemService(StorageManager.class);
        boolean z5 = this.type == 5;
        String environmentForState = z ? Environment.MEDIA_UNMOUNTED : getEnvironmentForState(this.state);
        File pathForUser = getPathForUser(i);
        if (pathForUser == null) {
            pathForUser = new File("/dev/null");
        }
        File file = pathForUser;
        File internalPathForUser = getInternalPathForUser(i);
        if (internalPathForUser == null) {
            internalPathForUser = new File("/dev/null");
        }
        File file2 = internalPathForUser;
        String str4 = this.fsUuid;
        int i2 = this.type;
        String str5 = null;
        if (i2 == 2) {
            VolumeInfo findPrivateForEmulated = storageManager.findPrivateForEmulated(this);
            if (findPrivateForEmulated != null) {
                str5 = storageManager.getBestVolumeDescription(findPrivateForEmulated);
                uuid2 = StorageManager.convert(findPrivateForEmulated.fsUuid);
                str4 = findPrivateForEmulated.fsUuid;
            } else {
                uuid2 = StorageManager.UUID_DEFAULT;
            }
            uuid = uuid2;
            str = str4;
            buildStableMtpStorageId = isPrimary() ? 65537 : 0;
            j = 0;
            z2 = true;
            z3 = !isPrimaryEmulatedForUser(i);
        } else if (i2 == 0 || i2 == 5) {
            String bestVolumeDescription = storageManager.getBestVolumeDescription(this);
            str = str4;
            buildStableMtpStorageId = isPrimary() ? 65537 : buildStableMtpStorageId(this.fsUuid);
            uuid = null;
            j = "vfat".equals(this.fsType) ? 4294967295L : 0L;
            str5 = bestVolumeDescription;
            z2 = false;
            z3 = true;
        } else {
            throw new IllegalStateException("Unexpected volume type " + this.type);
        }
        if (str5 == null) {
            str5 = context.getString(17039374);
        }
        String str6 = str5;
        DiskInfo diskInfo = this.disk;
        if (diskInfo != null) {
            if (diskInfo.isSd()) {
                str3 = "sd";
                z4 = true;
                return new StorageVolume(this.id, file, file2, str6, isPrimary(), z3, z2, z5, false, j, new UserHandle(i), uuid, str, environmentForState, buildStableMtpStorageId, str3, z4);
            }
            if (!this.disk.isUsb()) {
                str2 = KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
            } else {
                str2 = "usb";
            }
        } else {
            str2 = "fuse";
        }
        str3 = str2;
        z4 = false;
        return new StorageVolume(this.id, file, file2, str6, isPrimary(), z3, z2, z5, false, j, new UserHandle(i), uuid, str, environmentForState, buildStableMtpStorageId, str3, z4);
    }

    public static int buildStableMtpStorageId(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            i = (i * 31) + str.charAt(i2);
        }
        int i3 = ((i << 16) ^ i) & (-65536);
        if (i3 == 0) {
            i3 = 131072;
        }
        int i4 = i3 != 65536 ? i3 : 131072;
        if (i4 == -65536) {
            i4 = -131072;
        }
        return i4 | 1;
    }

    public Intent buildBrowseIntent() {
        return buildBrowseIntentForUser(UserHandle.myUserId());
    }

    public Intent buildBrowseIntentForUser(int i) {
        Uri buildRootUri;
        int i2 = this.type;
        if ((i2 == 0 || i2 == 5) && this.mountUserId == i) {
            buildRootUri = DocumentsContract.buildRootUri("com.android.externalstorage.documents", this.fsUuid);
        } else {
            if (i2 != 2 || !isPrimary()) {
                return null;
            }
            buildRootUri = DocumentsContract.buildRootUri("com.android.externalstorage.documents", "primary");
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(buildRootUri, DocumentsContract.Root.MIME_TYPE_ITEM);
        intent.putExtra(DocumentsContract.EXTRA_SHOW_ADVANCED, isPrimary());
        return intent;
    }

    public String toString() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        dump(new IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VolumeInfo{" + this.id + "}:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("type", DebugUtils.valueToString(getClass(), "TYPE_", this.type));
        indentingPrintWriter.printPair("diskId", getDiskId());
        indentingPrintWriter.printPair("partGuid", this.partGuid);
        indentingPrintWriter.printPair("mountFlags", DebugUtils.flagsToString(getClass(), "MOUNT_FLAG_", this.mountFlags));
        indentingPrintWriter.printPair("mountUserId", Integer.valueOf(this.mountUserId));
        indentingPrintWriter.printPair("state", DebugUtils.valueToString(getClass(), "STATE_", this.state));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("fsType", this.fsType);
        indentingPrintWriter.printPair("fsUuid", this.fsUuid);
        indentingPrintWriter.printPair("fsLabel", this.fsLabel);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("path", this.path);
        indentingPrintWriter.printPair("internalPath", this.internalPath);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public VolumeInfo m3709clone() {
        Parcel obtain = Parcel.obtain();
        try {
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return CREATOR.createFromParcel(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof VolumeInfo) {
            return Objects.equals(this.id, ((VolumeInfo) obj).id);
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.id);
        parcel.writeInt(this.type);
        if (this.disk != null) {
            parcel.writeInt(1);
            this.disk.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.partGuid);
        parcel.writeInt(this.mountFlags);
        parcel.writeInt(this.mountUserId);
        parcel.writeInt(this.state);
        parcel.writeString8(this.fsType);
        parcel.writeString8(this.fsUuid);
        parcel.writeString8(this.fsLabel);
        parcel.writeString8(this.path);
        parcel.writeString8(this.internalPath);
    }
}
