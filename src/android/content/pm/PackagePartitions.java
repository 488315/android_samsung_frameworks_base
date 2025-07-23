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

/* loaded from: classes.dex */
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
    public @interface PartitionType {
    }

    static {
        boolean z = true;
        boolean z2 = true;
        boolean z3 = true;
        boolean z4 = true;
        SYSTEM_PARTITIONS = new ArrayList<>(Arrays.asList(new SystemPartition(Environment.getRootDirectory(), 0, "system", true, false), new SystemPartition(Environment.getVendorDirectory(), true ? 1 : 0, "vendor", true, z), new SystemPartition(Environment.getOdmDirectory(), 2, Build.Partition.PARTITION_NAME_ODM, z, true), new SystemPartition(Environment.getOemDirectory(), 3, Build.Partition.PARTITION_NAME_OEM, false, z2), new SystemPartition(Environment.getProductDirectory(), 4, "product", z2, z3), new SystemPartition(Environment.getSystemExtDirectory(), 5, Build.Partition.PARTITION_NAME_SYSTEM_EXT, z3, z4), new SystemPartition(new File("/prism"), 0, "prism", z4, false), new SystemPartition(new File(Environment.getRootDirectory(), "carrier"), 0, "carrier", true, false)));
    }

    public static <T> ArrayList<T> getOrderedPartitions(Function<SystemPartition, T> function) {
        ArrayList<T> arrayList = new ArrayList<>();
        int size = SYSTEM_PARTITIONS.size();
        for (int i = 0; i < size; i++) {
            T apply = function.apply(SYSTEM_PARTITIONS.get(i));
            if (apply != null) {
                arrayList.add(apply);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File canonicalize(File file) {
        try {
            return file.getCanonicalFile();
        } catch (IOException unused) {
            return file;
        }
    }

    private static String getFingerprint() {
        String[] strArr = new String[SYSTEM_PARTITIONS.size() + 1];
        int i = 0;
        while (true) {
            ArrayList<SystemPartition> arrayList = SYSTEM_PARTITIONS;
            if (i < arrayList.size()) {
                strArr[i] = "ro." + arrayList.get(i).getName() + ".build.fingerprint";
                i++;
            } else {
                strArr[arrayList.size()] = "ro.build.fingerprint";
                return SystemProperties.digestOf(strArr);
            }
        }
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

        public SystemPartition(SystemPartition systemPartition) {
            this.type = systemPartition.type;
            this.mName = systemPartition.mName;
            this.mFolder = new DeferredCanonicalFile(systemPartition.mFolder.getFile());
            this.mAppFolder = systemPartition.mAppFolder;
            this.mPrivAppFolder = systemPartition.mPrivAppFolder;
            this.mOverlayFolder = systemPartition.mOverlayFolder;
            this.mNonConicalFolder = systemPartition.mNonConicalFolder;
        }

        /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
            java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
            	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
            	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
            	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
            */
        public SystemPartition(java.io.File r7, android.content.pm.PackagePartitions.SystemPartition r8) {
            /*
                r6 = this;
                int r2 = r8.type
                java.lang.String r3 = r8.mName
                android.content.pm.PackagePartitions$DeferredCanonicalFile r0 = r8.mPrivAppFolder
                r1 = 1
                r4 = 0
                if (r0 == 0) goto Ld
                r0 = r4
                r4 = r1
                goto Le
            Ld:
                r0 = r4
            Le:
                android.content.pm.PackagePartitions$DeferredCanonicalFile r8 = r8.mOverlayFolder
                if (r8 == 0) goto L16
                r5 = r1
                r0 = r6
                r1 = r7
                goto L19
            L16:
                r5 = r0
                r1 = r7
                r0 = r6
            L19:
                r0.<init>(r1, r2, r3, r4, r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.PackagePartitions.SystemPartition.<init>(java.io.File, android.content.pm.PackagePartitions$SystemPartition):void");
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
            DeferredCanonicalFile deferredCanonicalFile = this.mAppFolder;
            if (deferredCanonicalFile == null) {
                return null;
            }
            return deferredCanonicalFile.getFile();
        }

        public File getPrivAppFolder() {
            DeferredCanonicalFile deferredCanonicalFile = this.mPrivAppFolder;
            if (deferredCanonicalFile == null) {
                return null;
            }
            return deferredCanonicalFile.getFile();
        }

        public File getOverlayFolder() {
            DeferredCanonicalFile deferredCanonicalFile = this.mOverlayFolder;
            if (deferredCanonicalFile == null) {
                return null;
            }
            return deferredCanonicalFile.getFile();
        }

        public boolean containsPath(String str) {
            return containsFile(new File(str));
        }

        public boolean containsFile(File file) {
            return FileUtils.contains(this.mFolder.getFile(), PackagePartitions.canonicalize(file));
        }

        public boolean containsPrivApp(File file) {
            DeferredCanonicalFile deferredCanonicalFile = this.mPrivAppFolder;
            return deferredCanonicalFile != null && FileUtils.contains(deferredCanonicalFile.getFile(), PackagePartitions.canonicalize(file));
        }

        public boolean containsApp(File file) {
            DeferredCanonicalFile deferredCanonicalFile = this.mAppFolder;
            return deferredCanonicalFile != null && FileUtils.contains(deferredCanonicalFile.getFile(), PackagePartitions.canonicalize(file));
        }

        public boolean containsOverlay(File file) {
            DeferredCanonicalFile deferredCanonicalFile = this.mOverlayFolder;
            return deferredCanonicalFile != null && FileUtils.contains(deferredCanonicalFile.getFile(), PackagePartitions.canonicalize(file));
        }
    }

    private static class DeferredCanonicalFile {
        private File mFile;
        private boolean mIsCanonical;

        private DeferredCanonicalFile(File file) {
            this.mIsCanonical = false;
            this.mFile = file;
        }

        private DeferredCanonicalFile(File file, String str) {
            this.mIsCanonical = false;
            this.mFile = new File(file, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File getFile() {
            if (!this.mIsCanonical) {
                this.mFile = PackagePartitions.canonicalize(this.mFile);
                this.mIsCanonical = true;
            }
            return this.mFile;
        }
    }
}
