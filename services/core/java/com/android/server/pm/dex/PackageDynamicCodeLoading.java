package com.android.server.pm.dex;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.AtomicFile;
import android.util.Slog;

import com.android.internal.util.FastPrintWriter;
import com.android.server.pm.AbstractStatsBase;
import com.android.server.utils.WatchedArrayMap;

import libcore.io.IoUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageDynamicCodeLoading extends AbstractStatsBase {
    static final int MAX_FILES_PER_OWNER = 100;
    public static final Pattern PACKAGE_LINE_PATTERN =
            Pattern.compile("([A-Z]):([0-9]+):([^:]*):(.*)");
    public final Object mLock;
    public Map mPackageMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DynamicCodeFile {
        public final char mFileType;
        public final Set mLoadingPackages;
        public final int mUserId;

        public DynamicCodeFile(char c, int i, String[] strArr) {
            this.mFileType = c;
            this.mUserId = i;
            this.mLoadingPackages = new HashSet(Arrays.asList(strArr));
        }

        public DynamicCodeFile(DynamicCodeFile dynamicCodeFile) {
            this.mFileType = dynamicCodeFile.mFileType;
            this.mUserId = dynamicCodeFile.mUserId;
            this.mLoadingPackages = new HashSet(dynamicCodeFile.mLoadingPackages);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageDynamicCode {
        public final Map mFileUsageMap;

        /* renamed from: -$$Nest$madd, reason: not valid java name */
        public static boolean m782$$Nest$madd(
                PackageDynamicCode packageDynamicCode, String str, char c, int i, String str2) {
            DynamicCodeFile dynamicCodeFile =
                    (DynamicCodeFile) ((HashMap) packageDynamicCode.mFileUsageMap).get(str);
            if (dynamicCodeFile != null) {
                if (dynamicCodeFile.mUserId != i) {
                    return false;
                }
                return ((HashSet) dynamicCodeFile.mLoadingPackages).add(str2);
            }
            if (((HashMap) packageDynamicCode.mFileUsageMap).size() >= 100) {
                return false;
            }
            ((HashMap) packageDynamicCode.mFileUsageMap)
                    .put(str, new DynamicCodeFile(c, i, new String[] {str2}));
            return true;
        }

        /* renamed from: -$$Nest$msyncData, reason: not valid java name */
        public static void m783$$Nest$msyncData(
                PackageDynamicCode packageDynamicCode, Map map, Set set) {
            Iterator it = ((HashMap) packageDynamicCode.mFileUsageMap).values().iterator();
            while (it.hasNext()) {
                DynamicCodeFile dynamicCodeFile = (DynamicCodeFile) it.next();
                int i = dynamicCodeFile.mUserId;
                if (set.contains(Integer.valueOf(i))) {
                    Iterator it2 = ((HashSet) dynamicCodeFile.mLoadingPackages).iterator();
                    while (it2.hasNext()) {
                        Set set2 = (Set) ((HashMap) map).get((String) it2.next());
                        if (set2 == null || !set2.contains(Integer.valueOf(i))) {
                            it2.remove();
                        }
                    }
                    if (((HashSet) dynamicCodeFile.mLoadingPackages).isEmpty()) {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            }
        }

        public PackageDynamicCode() {
            this.mFileUsageMap = new HashMap();
        }

        public PackageDynamicCode(PackageDynamicCode packageDynamicCode) {
            this.mFileUsageMap = new HashMap(((HashMap) packageDynamicCode.mFileUsageMap).size());
            for (Map.Entry entry : ((HashMap) packageDynamicCode.mFileUsageMap).entrySet()) {
                DynamicCodeFile dynamicCodeFile =
                        new DynamicCodeFile((DynamicCodeFile) entry.getValue());
                ((HashMap) this.mFileUsageMap).put((String) entry.getKey(), dynamicCodeFile);
            }
        }
    }

    public PackageDynamicCodeLoading() {
        super("package-dcl.list", "PackageDynamicCodeLoading_DiskWriter", false);
        this.mLock = new Object();
        this.mPackageMap = new HashMap();
    }

    public static String escape(String str) {
        if (str.indexOf(92) == -1 && str.indexOf(10) == -1 && str.indexOf(13) == -1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() + 10);
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\n') {
                sb.append("\\n");
            } else if (charAt == '\r') {
                sb.append("\\r");
            } else if (charAt != '\\') {
                sb.append(charAt);
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }

    public static String unescape(String str) throws IOException {
        int indexOf = str.indexOf(92);
        if (indexOf == -1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        int i = 0;
        while (indexOf < str.length() - 1) {
            sb.append((CharSequence) str, i, indexOf);
            char charAt = str.charAt(indexOf + 1);
            if (charAt == '\\') {
                sb.append('\\');
            } else if (charAt == 'n') {
                sb.append('\n');
            } else {
                if (charAt != 'r') {
                    throw new IOException("Bad escape in: ".concat(str));
                }
                sb.append('\r');
            }
            i = indexOf + 2;
            indexOf = str.indexOf(92, i);
            if (indexOf == -1) {
                sb.append((CharSequence) str, i, str.length());
                return sb.toString();
            }
        }
        throw new IOException("Unexpected \\ in: ".concat(str));
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e9, code lost:

       if (((java.util.HashMap) r5.mFileUsageMap).isEmpty() != false) goto L61;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00eb, code lost:

       r0.put(r14, r5);
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ee, code lost:

       r14 = r6;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void read(java.io.InputStream r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.dex.PackageDynamicCodeLoading.read(java.io.InputStream):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.pm.AbstractStatsBase
    public final void readInternal(WatchedArrayMap watchedArrayMap) {
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = getFile().openRead();
                read(fileInputStream);
            } catch (FileNotFoundException unused) {
            } catch (IOException e) {
                Slog.w(
                        "PackageDynamicCodeLoading",
                        "Failed to parse dynamic usage for secondary code files.",
                        e);
            }
        } finally {
            IoUtils.closeQuietly(fileInputStream);
        }
    }

    public final boolean record(int i, int i2, String str, String str2, String str3) {
        boolean m782$$Nest$madd;
        if (i != 68 && i != 78) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad file type: "));
        }
        synchronized (this.mLock) {
            try {
                PackageDynamicCode packageDynamicCode =
                        (PackageDynamicCode) ((HashMap) this.mPackageMap).get(str);
                if (packageDynamicCode == null) {
                    packageDynamicCode = new PackageDynamicCode();
                    ((HashMap) this.mPackageMap).put(str, packageDynamicCode);
                }
                m782$$Nest$madd =
                        PackageDynamicCode.m782$$Nest$madd(
                                packageDynamicCode, str2, (char) i, i2, str3);
            } catch (Throwable th) {
                throw th;
            }
        }
        return m782$$Nest$madd;
    }

    public final boolean removeFile(int i, String str, String str2) {
        synchronized (this.mLock) {
            try {
                PackageDynamicCode packageDynamicCode =
                        (PackageDynamicCode) this.mPackageMap.get(str);
                if (packageDynamicCode == null) {
                    return false;
                }
                DynamicCodeFile dynamicCodeFile =
                        (DynamicCodeFile) packageDynamicCode.mFileUsageMap.get(str2);
                if (dynamicCodeFile != null && dynamicCodeFile.mUserId == i) {
                    packageDynamicCode.mFileUsageMap.remove(str2);
                    if (packageDynamicCode.mFileUsageMap.isEmpty()) {
                        this.mPackageMap.remove(str);
                    }
                    return true;
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean removeUserPackage(int i, String str) {
        synchronized (this.mLock) {
            try {
                PackageDynamicCode packageDynamicCode =
                        (PackageDynamicCode) this.mPackageMap.get(str);
                if (packageDynamicCode == null) {
                    return false;
                }
                Iterator it = packageDynamicCode.mFileUsageMap.values().iterator();
                boolean z = false;
                while (it.hasNext()) {
                    if (((DynamicCodeFile) it.next()).mUserId == i) {
                        it.remove();
                        z = true;
                    }
                }
                if (!z) {
                    return false;
                }
                if (packageDynamicCode.mFileUsageMap.isEmpty()) {
                    this.mPackageMap.remove(str);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void write(OutputStream outputStream) throws IOException {
        HashMap hashMap;
        synchronized (this.mLock) {
            try {
                hashMap = new HashMap(((HashMap) this.mPackageMap).size());
                for (Map.Entry entry : ((HashMap) this.mPackageMap).entrySet()) {
                    hashMap.put(
                            (String) entry.getKey(),
                            new PackageDynamicCode((PackageDynamicCode) entry.getValue()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        FastPrintWriter fastPrintWriter = new FastPrintWriter(outputStream);
        fastPrintWriter.println("DCL1");
        for (Map.Entry entry2 : hashMap.entrySet()) {
            fastPrintWriter.print("P:");
            fastPrintWriter.println((String) entry2.getKey());
            for (Map.Entry entry3 :
                    ((HashMap) ((PackageDynamicCode) entry2.getValue()).mFileUsageMap).entrySet()) {
                String str = (String) entry3.getKey();
                DynamicCodeFile dynamicCodeFile = (DynamicCodeFile) entry3.getValue();
                fastPrintWriter.print(dynamicCodeFile.mFileType);
                fastPrintWriter.print(':');
                fastPrintWriter.print(dynamicCodeFile.mUserId);
                fastPrintWriter.print(':');
                Iterator it = ((HashSet) dynamicCodeFile.mLoadingPackages).iterator();
                String str2 = "";
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    fastPrintWriter.print(str2);
                    fastPrintWriter.print(str3);
                    str2 = ",";
                }
                fastPrintWriter.print(':');
                fastPrintWriter.println(escape(str));
            }
        }
        fastPrintWriter.flush();
        if (fastPrintWriter.checkError()) {
            throw new IOException("Writer failed");
        }
    }

    @Override // com.android.server.pm.AbstractStatsBase
    public final void writeInternal(Object obj) {
        FileOutputStream fileOutputStream;
        AtomicFile file = getFile();
        try {
            fileOutputStream = file.startWrite();
            try {
                write(fileOutputStream);
                file.finishWrite(fileOutputStream);
            } catch (IOException e) {
                e = e;
                file.failWrite(fileOutputStream);
                Slog.e(
                        "PackageDynamicCodeLoading",
                        "Failed to write dynamic usage for secondary code files.",
                        e);
            }
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }
}
