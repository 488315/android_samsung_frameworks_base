package android.content.pm;

import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SystemProperties;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class PackagePartitions {
    public static final String FINGERPRINT = getFingerprint();
    public static final int PARTITION_ODM = 2;
    public static final int PARTITION_OEM = 3;
    public static final int PARTITION_PRODUCT = 4;
    public static final int PARTITION_SYSTEM = 0;
    public static final int PARTITION_SYSTEM_EXT = 5;
    public static final int PARTITION_VENDOR = 1;
    private static final ArrayList<SystemPartition> SYSTEM_PARTITIONS;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PartitionType {}

    static {
        boolean z = true;
        boolean z2 = true;
        boolean z3 = false;
        int i = 0;
        SYSTEM_PARTITIONS =
                new ArrayList<>(
                        Arrays.asList(
                                new SystemPartition(
                                        Environment.getRootDirectory(), 0, "system", true, false),
                                new SystemPartition(
                                        Environment.getVendorDirectory(), 1, "vendor", true, true),
                                new SystemPartition(
                                        Environment.getOdmDirectory(),
                                        2,
                                        Build.Partition.PARTITION_NAME_ODM,
                                        true,
                                        z),
                                new SystemPartition(
                                        Environment.getOemDirectory(),
                                        3,
                                        Build.Partition.PARTITION_NAME_OEM,
                                        false,
                                        z),
                                new SystemPartition(
                                        Environment.getProductDirectory(), 4, "product", z2, z),
                                new SystemPartition(
                                        Environment.getSystemExtDirectory(),
                                        5,
                                        Build.Partition.PARTITION_NAME_SYSTEM_EXT,
                                        z2,
                                        z),
                                new SystemPartition(new File("/prism"), i, "prism", z2, z3),
                                new SystemPartition(
                                        new File(Environment.getRootDirectory(), "carrier"),
                                        i,
                                        "carrier",
                                        z2,
                                        z3)));
    }

    public static <T> ArrayList<T> getOrderedPartitions(Function<SystemPartition, T> producer) {
        ArrayList<T> out = new ArrayList<>();
        int n = SYSTEM_PARTITIONS.size();
        for (int i = 0; i < n; i++) {
            T v = producer.apply(SYSTEM_PARTITIONS.get(i));
            if (v != null) {
                out.add(v);
            }
        }
        return out;
    }

    public static File canonicalize(File path) {
        try {
            return path.getCanonicalFile();
        } catch (IOException e) {
            return path;
        }
    }

    private static String getFingerprint() {
        String[] digestProperties = new String[SYSTEM_PARTITIONS.size() + 1];
        for (int i = 0; i < SYSTEM_PARTITIONS.size(); i++) {
            String partitionName = SYSTEM_PARTITIONS.get(i).getName();
            digestProperties[i] = "ro." + partitionName + ".build.fingerprint";
        }
        digestProperties[SYSTEM_PARTITIONS.size()] = "ro.build.fingerprint";
        return SystemProperties.digestOf(digestProperties);
    }

    public static class SystemPartition {
        private final DeferredCanonicalFile mAppFolder;
        private final DeferredCanonicalFile mFolder;
        private final String mName;
        private final File mNonConicalFolder;
        private final DeferredCanonicalFile mOverlayFolder;
        private final DeferredCanonicalFile mPrivAppFolder;
        public final int type;

        private SystemPartition(File file, int i, String str, boolean z, boolean z2) {
            DeferredCanonicalFile deferredCanonicalFile;
            this.type = i;
            this.mName = str;
            byte b = 0;
            byte b2 = 0;
            this.mFolder = new DeferredCanonicalFile(file);
            this.mAppFolder = new DeferredCanonicalFile(file, "app");
            if (z) {
                deferredCanonicalFile = new DeferredCanonicalFile(file, "priv-app");
            } else {
                deferredCanonicalFile = null;
            }
            this.mPrivAppFolder = deferredCanonicalFile;
            this.mOverlayFolder = z2 ? new DeferredCanonicalFile(file, "overlay") : null;
            this.mNonConicalFolder = file;
        }

        public SystemPartition(SystemPartition original) {
            this.type = original.type;
            this.mName = original.mName;
            this.mFolder = new DeferredCanonicalFile(original.mFolder.getFile());
            this.mAppFolder = original.mAppFolder;
            this.mPrivAppFolder = original.mPrivAppFolder;
            this.mOverlayFolder = original.mOverlayFolder;
            this.mNonConicalFolder = original.mNonConicalFolder;
        }

        public SystemPartition(File rootFolder, SystemPartition partition) {
            this(
                    rootFolder,
                    partition.type,
                    partition.mName,
                    partition.mPrivAppFolder != null,
                    partition.mOverlayFolder != null);
        }

        public String getName() {
            return this.mName;
        }

        public File getFolder() {
            return this.mFolder.getFile();
        }

        public File getNonConicalFolder() {
            return this.mNonConicalFolder;
        }

        public File getAppFolder() {
            if (this.mAppFolder == null) {
                return null;
            }
            return this.mAppFolder.getFile();
        }

        public File getPrivAppFolder() {
            if (this.mPrivAppFolder == null) {
                return null;
            }
            return this.mPrivAppFolder.getFile();
        }

        public File getOverlayFolder() {
            if (this.mOverlayFolder == null) {
                return null;
            }
            return this.mOverlayFolder.getFile();
        }

        public boolean containsPath(String path) {
            return containsFile(new File(path));
        }

        public boolean containsFile(File file) {
            return FileUtils.contains(this.mFolder.getFile(), PackagePartitions.canonicalize(file));
        }

        public boolean containsPrivApp(File scanFile) {
            return this.mPrivAppFolder != null
                    && FileUtils.contains(
                            this.mPrivAppFolder.getFile(),
                            PackagePartitions.canonicalize(scanFile));
        }

        public boolean containsApp(File scanFile) {
            return this.mAppFolder != null
                    && FileUtils.contains(
                            this.mAppFolder.getFile(), PackagePartitions.canonicalize(scanFile));
        }

        public boolean containsOverlay(File scanFile) {
            return this.mOverlayFolder != null
                    && FileUtils.contains(
                            this.mOverlayFolder.getFile(),
                            PackagePartitions.canonicalize(scanFile));
        }
    }

    private static class DeferredCanonicalFile {
        private File mFile;
        private boolean mIsCanonical;

        private DeferredCanonicalFile(File dir) {
            this.mIsCanonical = false;
            this.mFile = dir;
        }

        private DeferredCanonicalFile(File dir, String fileName) {
            this.mIsCanonical = false;
            this.mFile = new File(dir, fileName);
        }

        public File getFile() {
            if (!this.mIsCanonical) {
                this.mFile = PackagePartitions.canonicalize(this.mFile);
                this.mIsCanonical = true;
            }
            return this.mFile;
        }
    }
}
