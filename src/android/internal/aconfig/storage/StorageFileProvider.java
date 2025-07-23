package android.internal.aconfig.storage;

import java.io.Closeable;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class StorageFileProvider {
    private static final String FMAP_FILE_EXT = ".flag.map";
    private static final String PMAP_FILE_EXT = ".package.map";
    private static final String VAL_FILE_EXT = ".val";
    private final String mBootPath;
    private final String mMapPath;
    private static final String DEFAULT_MAP_PATH = "/metadata/aconfig/maps/";
    private static final String DEFAULT_BOOT_PATH = "/metadata/aconfig/boot/";
    private static final StorageFileProvider DEFAULT_INSTANCE = new StorageFileProvider(DEFAULT_MAP_PATH, DEFAULT_BOOT_PATH);

    public static StorageFileProvider getDefaultProvider() {
        return DEFAULT_INSTANCE;
    }

    public StorageFileProvider(String str, String str2) {
        this.mMapPath = str;
        this.mBootPath = str2;
    }

    public List<String> listContainers(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet(Arrays.asList(strArr));
        try {
            Iterator<Path> it = Files.newDirectoryStream(Paths.get(this.mMapPath, new String[0]), "*.package.map").iterator();
            while (it.hasNext()) {
                String substring = it.next().getFileName().toString().substring(0, r3.length() - 12);
                if (!hashSet.contains(substring)) {
                    arrayList.add(substring);
                }
            }
        } catch (NoSuchFileException unused) {
        } catch (Exception e) {
            throw new AconfigStorageException(String.format("Fail to list map files in path %s", this.mMapPath), e);
        }
        return arrayList;
    }

    public PackageTable getPackageTable(String str) {
        return PackageTable.fromBytes(mapStorageFile(Paths.get(this.mMapPath, str + PMAP_FILE_EXT), FileType.PACKAGE_MAP));
    }

    public FlagTable getFlagTable(String str) {
        return FlagTable.fromBytes(mapStorageFile(Paths.get(this.mMapPath, str + FMAP_FILE_EXT), FileType.FLAG_MAP));
    }

    public FlagValueList getFlagValueList(String str) {
        return FlagValueList.fromBytes(mapStorageFile(Paths.get(this.mBootPath, str + VAL_FILE_EXT), FileType.FLAG_VAL));
    }

    private static MappedByteBuffer mapStorageFile(Path path, FileType fileType) {
        Throwable th;
        FileChannel fileChannel = null;
        try {
            try {
                FileChannel open = FileChannel.open(path, StandardOpenOption.READ);
                try {
                    MappedByteBuffer map = open.map(FileChannel.MapMode.READ_ONLY, 0L, open.size());
                    quietlyDispose(open);
                    return map;
                } catch (Exception e) {
                    e = e;
                    throw new AconfigStorageException(4, String.format("Fail to mmap storage %s file %s", fileType.toString(), path), e);
                } catch (Throwable th2) {
                    th = th2;
                    fileChannel = open;
                    quietlyDispose(fileChannel);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th3) {
            th = th3;
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
