package android.os.storage;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.KeyboardLayout;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.DocumentsContract;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.CharArrayWriter;
import java.io.File;
import java.util.Locale;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class StorageVolume implements Parcelable {
    private static final String ACTION_OPEN_EXTERNAL_DIRECTORY = "android.os.storage.action.OPEN_EXTERNAL_DIRECTORY";
    public static final Parcelable.Creator<StorageVolume> CREATOR = new Parcelable.Creator<StorageVolume>() { // from class: android.os.storage.StorageVolume.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageVolume createFromParcel(Parcel parcel) {
            return new StorageVolume(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StorageVolume[] newArray(int i) {
            return new StorageVolume[i];
        }
    };
    public static final String EXTRA_DIRECTORY_NAME = "android.os.storage.extra.DIRECTORY_NAME";
    public static final String EXTRA_STORAGE_VOLUME = "android.os.storage.extra.STORAGE_VOLUME";
    public static final int STORAGE_ID_INVALID = 0;
    public static final int STORAGE_ID_PRIMARY = 65537;
    public static final int STORAGE_ID_PRIVATE = 65538;
    private final boolean mActivitySecureContainer;
    private final boolean mAllowMassStorage;
    private final String mDescription;
    private final boolean mEmulated;
    private final boolean mExternallyManaged;
    private final String mFsUuid;
    private final String mId;
    private final File mInternalPath;
    private final long mMaxFileSize;
    private final UserHandle mOwner;
    private final File mPath;
    private final boolean mPrimary;
    private final boolean mRemovable;
    private final String mState;
    private final int mStorageId;
    private final String mSubSystem;
    private final UUID mUuid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StorageVolume(String str, File file, File file2, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, long j, UserHandle userHandle, UUID uuid, String str3, String str4) {
        this.mId = (String) Preconditions.checkNotNull(str);
        this.mPath = (File) Preconditions.checkNotNull(file);
        this.mInternalPath = (File) Preconditions.checkNotNull(file2);
        this.mDescription = (String) Preconditions.checkNotNull(str2);
        this.mPrimary = z;
        this.mRemovable = z2;
        this.mEmulated = z3;
        this.mExternallyManaged = z4;
        this.mAllowMassStorage = z5;
        this.mMaxFileSize = j;
        this.mOwner = (UserHandle) Preconditions.checkNotNull(userHandle);
        this.mUuid = uuid;
        this.mFsUuid = str3;
        this.mState = (String) Preconditions.checkNotNull(str4);
        this.mStorageId = 0;
        this.mSubSystem = KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        this.mActivitySecureContainer = false;
    }

    private StorageVolume(Parcel parcel) {
        this.mId = parcel.readString8();
        this.mPath = new File(parcel.readString8());
        this.mInternalPath = new File(parcel.readString8());
        this.mDescription = parcel.readString8();
        this.mPrimary = parcel.readInt() != 0;
        this.mRemovable = parcel.readInt() != 0;
        this.mEmulated = parcel.readInt() != 0;
        this.mExternallyManaged = parcel.readInt() != 0;
        this.mAllowMassStorage = parcel.readInt() != 0;
        this.mMaxFileSize = parcel.readLong();
        this.mOwner = (UserHandle) parcel.readParcelable(null, UserHandle.class);
        if (parcel.readInt() != 0) {
            this.mUuid = StorageManager.convert(parcel.readString8());
        } else {
            this.mUuid = null;
        }
        this.mFsUuid = parcel.readString8();
        this.mState = parcel.readString8();
        this.mStorageId = parcel.readInt();
        this.mSubSystem = parcel.readString8();
        this.mActivitySecureContainer = parcel.readInt() != 0;
    }

    @SystemApi
    public String getId() {
        return this.mId;
    }

    public String getPath() {
        return this.mPath.toString();
    }

    public String getInternalPath() {
        return this.mInternalPath.toString();
    }

    public File getPathFile() {
        return this.mPath;
    }

    public File getDirectory() {
        String str = this.mState;
        str.hashCode();
        if (str.equals(Environment.MEDIA_MOUNTED) || str.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return this.mPath;
        }
        return null;
    }

    public String getDescription(Context context) {
        return this.mDescription;
    }

    public boolean isPrimary() {
        return this.mPrimary;
    }

    public boolean isRemovable() {
        return this.mRemovable;
    }

    public boolean isEmulated() {
        return this.mEmulated;
    }

    @SystemApi
    public boolean isExternallyManaged() {
        return this.mExternallyManaged;
    }

    public boolean allowMassStorage() {
        return this.mAllowMassStorage;
    }

    public long getMaxFileSize() {
        return this.mMaxFileSize;
    }

    public UserHandle getOwner() {
        return this.mOwner;
    }

    public UUID getStorageUuid() {
        return this.mUuid;
    }

    public String getUuid() {
        return this.mFsUuid;
    }

    public String getMediaStoreVolumeName() {
        if (isPrimary()) {
            return "external_primary";
        }
        return getNormalizedUuid();
    }

    public static String normalizeUuid(String str) {
        if (str != null) {
            return str.toLowerCase(Locale.US);
        }
        return null;
    }

    public String getNormalizedUuid() {
        return normalizeUuid(this.mFsUuid);
    }

    public int getFatVolumeId() {
        String str = this.mFsUuid;
        if (str != null && str.length() == 9) {
            try {
                return (int) Long.parseLong(this.mFsUuid.replace(NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ""), 16);
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    public String getUserLabel() {
        return this.mDescription;
    }

    public String getState() {
        return this.mState;
    }

    @Deprecated
    public Intent createAccessIntent(String str) {
        if (isPrimary() && str == null) {
            return null;
        }
        if (str != null && !Environment.isStandardDirectory(str)) {
            return null;
        }
        Intent intent = new Intent(ACTION_OPEN_EXTERNAL_DIRECTORY);
        intent.putExtra(EXTRA_STORAGE_VOLUME, this);
        intent.putExtra(EXTRA_DIRECTORY_NAME, str);
        return intent;
    }

    public Intent createOpenDocumentTreeIntent() {
        String str;
        if (isEmulated()) {
            str = "primary";
        } else {
            str = this.mFsUuid;
        }
        return new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).putExtra(DocumentsContract.EXTRA_INITIAL_URI, DocumentsContract.buildRootUri(DocumentsContract.EXTERNAL_STORAGE_PROVIDER_AUTHORITY, str)).putExtra(DocumentsContract.EXTRA_SHOW_ADVANCED, true);
    }

    public boolean equals(Object obj) {
        File file;
        if (!(obj instanceof StorageVolume) || (file = this.mPath) == null) {
            return false;
        }
        return file.equals(((StorageVolume) obj).mPath);
    }

    public int hashCode() {
        return this.mPath.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StorageVolume: ");
        sb.append(this.mDescription);
        if (this.mFsUuid != null) {
            sb.append(" (");
            sb.append(this.mFsUuid);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        return sb.toString();
    }

    public String dump() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        dump(new IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("StorageVolume:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("mId", this.mId);
        indentingPrintWriter.printPair("mPath", this.mPath);
        indentingPrintWriter.printPair("mInternalPath", this.mInternalPath);
        indentingPrintWriter.printPair("mDescription", this.mDescription);
        indentingPrintWriter.printPair("mPrimary", Boolean.valueOf(this.mPrimary));
        indentingPrintWriter.printPair("mRemovable", Boolean.valueOf(this.mRemovable));
        indentingPrintWriter.printPair("mEmulated", Boolean.valueOf(this.mEmulated));
        indentingPrintWriter.printPair("mExternallyManaged", Boolean.valueOf(this.mExternallyManaged));
        indentingPrintWriter.printPair("mAllowMassStorage", Boolean.valueOf(this.mAllowMassStorage));
        indentingPrintWriter.printPair("mMaxFileSize", Long.valueOf(this.mMaxFileSize));
        indentingPrintWriter.printPair("mOwner", this.mOwner);
        indentingPrintWriter.printPair("mFsUuid", this.mFsUuid);
        indentingPrintWriter.printPair("mState", this.mState);
        indentingPrintWriter.printPair("mStorageId", Integer.valueOf(this.mStorageId));
        indentingPrintWriter.printPair("mSubSystem", this.mSubSystem);
        indentingPrintWriter.printPair("mActivitySecureContainer", Boolean.valueOf(this.mActivitySecureContainer));
        indentingPrintWriter.decreaseIndent();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mId);
        parcel.writeString8(this.mPath.toString());
        parcel.writeString8(this.mInternalPath.toString());
        parcel.writeString8(this.mDescription);
        parcel.writeInt(this.mPrimary ? 1 : 0);
        parcel.writeInt(this.mRemovable ? 1 : 0);
        parcel.writeInt(this.mEmulated ? 1 : 0);
        parcel.writeInt(this.mExternallyManaged ? 1 : 0);
        parcel.writeInt(this.mAllowMassStorage ? 1 : 0);
        parcel.writeLong(this.mMaxFileSize);
        parcel.writeParcelable(this.mOwner, i);
        if (this.mUuid != null) {
            parcel.writeInt(1);
            parcel.writeString8(StorageManager.convert(this.mUuid));
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.mFsUuid);
        parcel.writeString8(this.mState);
        parcel.writeInt(this.mStorageId);
        parcel.writeString8(this.mSubSystem);
        parcel.writeInt(this.mActivitySecureContainer ? 1 : 0);
    }

    public static final class Builder {
        private String mDescription;
        private boolean mEmulated;
        private String mId;
        private UserHandle mOwner;
        private File mPath;
        private boolean mPrimary;
        private boolean mRemovable;
        private String mState;
        private UUID mStorageUuid;
        private String mUuid;

        public Builder(String str, File file, String str2, UserHandle userHandle, String str3) {
            this.mId = str;
            this.mPath = file;
            this.mDescription = str2;
            this.mOwner = userHandle;
            this.mState = str3;
        }

        public Builder setStorageUuid(UUID uuid) {
            this.mStorageUuid = uuid;
            return this;
        }

        public Builder setUuid(String str) {
            this.mUuid = str;
            return this;
        }

        public Builder setPrimary(boolean z) {
            this.mPrimary = z;
            return this;
        }

        public Builder setRemovable(boolean z) {
            this.mRemovable = z;
            return this;
        }

        public Builder setEmulated(boolean z) {
            this.mEmulated = z;
            return this;
        }

        public StorageVolume build() {
            String str = this.mId;
            File file = this.mPath;
            return new StorageVolume(str, file, file, this.mDescription, this.mPrimary, this.mRemovable, this.mEmulated, false, false, 0L, this.mOwner, this.mStorageUuid, this.mUuid, this.mState);
        }
    }

    public StorageVolume(String str, File file, File file2, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, long j, UserHandle userHandle, UUID uuid, String str3, String str4, int i, String str5, boolean z6) {
        this.mId = (String) Preconditions.checkNotNull(str);
        this.mPath = (File) Preconditions.checkNotNull(file);
        this.mInternalPath = (File) Preconditions.checkNotNull(file2);
        this.mDescription = (String) Preconditions.checkNotNull(str2);
        this.mPrimary = z;
        this.mRemovable = z2;
        this.mEmulated = z3;
        this.mExternallyManaged = z4;
        this.mAllowMassStorage = z5;
        this.mMaxFileSize = j;
        this.mOwner = (UserHandle) Preconditions.checkNotNull(userHandle);
        this.mUuid = uuid;
        this.mFsUuid = str3;
        this.mState = (String) Preconditions.checkNotNull(str4);
        this.mStorageId = i;
        this.mSubSystem = str5;
        this.mActivitySecureContainer = z6;
    }

    public boolean getActivitySecureContainer() {
        return this.mActivitySecureContainer;
    }

    public int getStorageId() {
        return this.mStorageId;
    }

    public String getSubSystem() {
        return this.mSubSystem;
    }

    public int semGetStorageId() {
        return this.mStorageId;
    }

    public String semGetPath() {
        return this.mPath.toString();
    }

    public String semGetSubSystem() {
        return this.mSubSystem;
    }
}
