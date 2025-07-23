package android.os.flagging;

import android.internal.aconfig.storage.AconfigStorageException;
import android.internal.aconfig.storage.FlagValueList;
import android.internal.aconfig.storage.PackageTable;
import android.internal.aconfig.storage.TableUtils;

/* loaded from: classes3.dex */
public class PlatformAconfigPackageInternal {
    private final FlagValueList mFlagValueList;
    private final int mPackageBooleanStartOffset;

    private PlatformAconfigPackageInternal(FlagValueList flagValueList, int i) {
        this.mFlagValueList = flagValueList;
        this.mPackageBooleanStartOffset = i;
    }

    public static PlatformAconfigPackageInternal load(String str, long j) {
        TableUtils.StorageFilesBundle storageFilesBundle = PlatformAconfigPackage.sStorageFilesCache.get(str);
        if (storageFilesBundle == null) {
            throw new AconfigStorageException(2, "package " + str + " cannot be found on the device");
        }
        PackageTable.Node node = storageFilesBundle.packageTable.get(str);
        FlagValueList flagValueList = storageFilesBundle.flagValueList;
        if (node.hasPackageFingerprint() && j != node.getPackageFingerprint()) {
            throw new AconfigStorageException(5, "package " + str + "fingerprint doesn't match the one on device");
        }
        return new PlatformAconfigPackageInternal(flagValueList, node.getBooleanStartIndex());
    }

    public boolean getBooleanFlagValue(int i) {
        return this.mFlagValueList.getBoolean(i + this.mPackageBooleanStartOffset);
    }
}
