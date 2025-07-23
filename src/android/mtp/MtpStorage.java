package android.mtp;

import android.content.res.Resources;
import android.os.storage.StorageVolume;
import com.android.internal.R;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class MtpStorage {
    private final String mDescription;
    private final Supplier<Boolean> mIsHostWindows;
    private final long mMaxFileSize;
    private final String mPath;
    private final boolean mRemovable;
    private final int mStorageId;
    private final String mVolumeName;

    public MtpStorage(StorageVolume storageVolume, int i, Supplier<Boolean> supplier) {
        this.mStorageId = i;
        this.mPath = storageVolume.getPath();
        this.mRemovable = storageVolume.isRemovable();
        this.mMaxFileSize = storageVolume.getMaxFileSize();
        this.mVolumeName = storageVolume.getMediaStoreVolumeName();
        this.mIsHostWindows = supplier;
        if (i == 65537) {
            this.mDescription = Resources.getSystem().getString(R.string.app_internal_storage);
        } else if (i == 65538) {
            this.mDescription = Resources.getSystem().getString(R.string.app_clone_storage);
        } else {
            this.mDescription = storageVolume.getDescription(null);
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
