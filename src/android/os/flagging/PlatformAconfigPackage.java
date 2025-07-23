package android.os.flagging;

import android.internal.aconfig.storage.AconfigStorageException;
import android.internal.aconfig.storage.FlagTable;
import android.internal.aconfig.storage.FlagValueList;
import android.internal.aconfig.storage.PackageTable;
import android.internal.aconfig.storage.TableUtils;
import android.util.Log;
import java.io.Closeable;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class PlatformAconfigPackage {
    private static final String BOOT_PATH = "/metadata/aconfig/boot/";
    private static final String MAP_PATH = "/metadata/aconfig/maps/";
    public static final Set<String> PLATFORM_PACKAGE_MAP_FILES;
    private static final String TAG = "PlatformAconfigPackage";
    static final Map<String, TableUtils.StorageFilesBundle> sStorageFilesCache = new HashMap();
    private FlagTable mFlagTable;
    private FlagValueList mFlagValueList;
    private int mPackageBooleanStartOffset = -1;
    private int mPackageId = -1;

    private PlatformAconfigPackage() {
    }

    static {
        Set<String> of = Set.of("system.package.map", "system_ext.package.map", "vendor.package.map", "product.package.map");
        PLATFORM_PACKAGE_MAP_FILES = of;
        Iterator<String> it = of.iterator();
        while (it.hasNext()) {
            try {
                PackageTable fromBytes = PackageTable.fromBytes(mapStorageFile(MAP_PATH + it.next()));
                String container = fromBytes.getHeader().getContainer();
                TableUtils.StorageFilesBundle storageFilesBundle = new TableUtils.StorageFilesBundle(fromBytes, FlagTable.fromBytes(mapStorageFile(MAP_PATH + container + ".flag.map")), FlagValueList.fromBytes(mapStorageFile(BOOT_PATH + container + ".val")));
                Iterator<String> it2 = fromBytes.getPackageList().iterator();
                while (it2.hasNext()) {
                    sStorageFilesCache.put(it2.next(), storageFilesBundle);
                }
            } catch (Exception e) {
                Log.w(TAG, e.toString());
            }
        }
    }

    public static PlatformAconfigPackage load(String str) {
        try {
            PlatformAconfigPackage platformAconfigPackage = new PlatformAconfigPackage();
            TableUtils.StorageFilesBundle storageFilesBundle = sStorageFilesCache.get(str);
            if (storageFilesBundle == null) {
                return null;
            }
            PackageTable.Node node = storageFilesBundle.packageTable.get(str);
            platformAconfigPackage.mFlagTable = storageFilesBundle.flagTable;
            platformAconfigPackage.mFlagValueList = storageFilesBundle.flagValueList;
            platformAconfigPackage.mPackageBooleanStartOffset = node.getBooleanStartIndex();
            platformAconfigPackage.mPackageId = node.getPackageId();
            return platformAconfigPackage;
        } catch (AconfigStorageException e) {
            throw new AconfigStorageReadException(e.getErrorCode(), "Fail to create PlatformAconfigPackage: " + str, e);
        } catch (Exception e2) {
            throw new AconfigStorageReadException(0, "Fail to create PlatformAconfigPackage: " + str, e2);
        }
    }

    public boolean getBooleanFlagValue(String str, boolean z) {
        FlagTable.Node node = this.mFlagTable.get(this.mPackageId, str);
        return node == null ? z : this.mFlagValueList.getBoolean(node.getFlagIndex() + this.mPackageBooleanStartOffset);
    }

    private static MappedByteBuffer mapStorageFile(String str) {
        Throwable th;
        Exception exc;
        FileChannel fileChannel = null;
        try {
            try {
                FileChannel open = FileChannel.open(Paths.get(str, new String[0]), StandardOpenOption.READ);
                try {
                    MappedByteBuffer map = open.map(FileChannel.MapMode.READ_ONLY, 0L, open.size());
                    quietlyDispose(open);
                    return map;
                } catch (Exception e) {
                    exc = e;
                    fileChannel = open;
                    throw new AconfigStorageReadException(4, "Fail to mmap storage", exc);
                } catch (Throwable th2) {
                    th = th2;
                    fileChannel = open;
                    quietlyDispose(fileChannel);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e2) {
            exc = e2;
        }
    }

    private static void quietlyDispose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }
}
