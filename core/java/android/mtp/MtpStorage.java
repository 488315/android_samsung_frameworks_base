package android.mtp;

import android.content.res.Resources;
import android.p009os.storage.StorageVolume;
import com.android.internal.C4337R;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class MtpStorage {
    private final String mDescription;
    private final Supplier<Boolean> mIsHostWindows;
    private final long mMaxFileSize;
    private final String mPath;
    private final boolean mRemovable;
    private final int mStorageId;
    private final String mVolumeName;

    public MtpStorage(StorageVolume volume, int storageId, Supplier<Boolean> isHostWindows) {
        this.mStorageId = storageId;
        this.mPath = volume.getPath();
        this.mRemovable = volume.isRemovable();
        this.mMaxFileSize = volume.getMaxFileSize();
        this.mVolumeName = volume.getMediaStoreVolumeName();
        this.mIsHostWindows = isHostWindows;
        if (storageId == 65537) {
            this.mDescription = Resources.getSystem().getString(C4337R.string.app_internal_storage);
        } else if (storageId == 65538) {
            this.mDescription = Resources.getSystem().getString(C4337R.string.app_clone_storage);
        } else {
            this.mDescription = volume.getDescription(null);
        }
    }

    public final int getStorageId() {
        return this.mStorageId;
    }

    public final String getPath() {
        return this.mPath;
    }

    public final String getDescription() {
        return this.mDescription;
    }

    public final boolean isRemovable() {
        return this.mRemovable;
    }

    public long getMaxFileSize() {
        return this.mMaxFileSize;
    }

    public String getVolumeName() {
        return this.mVolumeName;
    }

    public boolean isHostWindows() {
        return this.mIsHostWindows.get().booleanValue();
    }
}
