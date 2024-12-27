package com.android.server.pm.dex;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Build;
import android.util.AtomicFile;
import android.util.Slog;
import com.android.internal.util.FastPrintWriter;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.pm.AbstractStatsBase;
import com.android.server.utils.WatchedArrayMap;
import dalvik.system.VMRuntime;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageDexUsage extends AbstractStatsBase {
    static final int MAX_SECONDARY_FILES_PER_OWNER = 100;
    public final Map mPackageUseInfoMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DexUseInfo {
        public String mClassLoaderContext;
        public boolean mIsUsedByOtherApps;
        public final Set mLoaderIsas;
        public final Set mLoadingPackages;
        public final int mOwnerUserId;

        /* renamed from: -$$Nest$mmerge, reason: not valid java name */
        public static boolean m781$$Nest$mmerge(DexUseInfo dexUseInfo, DexUseInfo dexUseInfo2, boolean z) {
            boolean z2 = dexUseInfo.mIsUsedByOtherApps;
            dexUseInfo.mIsUsedByOtherApps = z2 || dexUseInfo2.mIsUsedByOtherApps;
            boolean addAll = dexUseInfo.mLoaderIsas.addAll(dexUseInfo2.mLoaderIsas);
            boolean addAll2 = dexUseInfo.mLoadingPackages.addAll(dexUseInfo2.mLoadingPackages);
            String str = dexUseInfo.mClassLoaderContext;
            if (z) {
                dexUseInfo.mClassLoaderContext = dexUseInfo2.mClassLoaderContext;
            } else if ("=UnsupportedClassLoaderContext=".equals(str)) {
                dexUseInfo.mClassLoaderContext = dexUseInfo2.mClassLoaderContext;
            } else if (!Objects.equals(dexUseInfo.mClassLoaderContext, dexUseInfo2.mClassLoaderContext)) {
                dexUseInfo.mClassLoaderContext = "=VariableClassLoaderContext=";
            }
            return addAll || z2 != dexUseInfo.mIsUsedByOtherApps || addAll2 || !Objects.equals(str, dexUseInfo.mClassLoaderContext);
        }

        public DexUseInfo(DexUseInfo dexUseInfo) {
            this.mIsUsedByOtherApps = dexUseInfo.mIsUsedByOtherApps;
            this.mOwnerUserId = dexUseInfo.mOwnerUserId;
            this.mClassLoaderContext = dexUseInfo.mClassLoaderContext;
            this.mLoaderIsas = new HashSet(dexUseInfo.mLoaderIsas);
            this.mLoadingPackages = new HashSet(dexUseInfo.mLoadingPackages);
        }

        public DexUseInfo(boolean z, int i, String str, String str2) {
            this.mIsUsedByOtherApps = z;
            this.mOwnerUserId = i;
            this.mClassLoaderContext = str;
            HashSet hashSet = new HashSet();
            this.mLoaderIsas = hashSet;
            if (str2 != null) {
                hashSet.add(str2);
            }
            this.mLoadingPackages = new HashSet();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageUseInfo {
        public final Map mDexUseInfoMap;
        public final String mPackageName;
        public final Map mPrimaryCodePaths;

        public PackageUseInfo(PackageUseInfo packageUseInfo) {
            this.mPackageName = packageUseInfo.mPackageName;
            this.mPrimaryCodePaths = new HashMap();
            for (Map.Entry entry : ((HashMap) packageUseInfo.mPrimaryCodePaths).entrySet()) {
                ((HashMap) this.mPrimaryCodePaths).put((String) entry.getKey(), new HashSet((Collection) entry.getValue()));
            }
            this.mDexUseInfoMap = new HashMap();
            for (Map.Entry entry2 : ((HashMap) packageUseInfo.mDexUseInfoMap).entrySet()) {
                ((HashMap) this.mDexUseInfoMap).put((String) entry2.getKey(), new DexUseInfo((DexUseInfo) entry2.getValue()));
            }
        }

        public PackageUseInfo(String str) {
            this.mPrimaryCodePaths = new HashMap();
            this.mDexUseInfoMap = new HashMap();
            this.mPackageName = str;
        }
    }

    public PackageDexUsage() {
        super("package-dex-usage.list", "PackageDexUsage_DiskWriter", false);
        this.mPackageUseInfoMap = new HashMap();
    }

    public static Set readLoadingPackages(BufferedReader bufferedReader) {
        String readLine = bufferedReader.readLine();
        if (readLine == null) {
            throw new IllegalStateException("Could not find the loadingPackages line.");
        }
        HashSet hashSet = new HashSet();
        if (readLine.length() != 1) {
            Collections.addAll(hashSet, readLine.substring(1).split(","));
        }
        return hashSet;
    }

    public final PackageUseInfo getPackageUseInfo(String str) {
        PackageUseInfo packageUseInfo;
        synchronized (this.mPackageUseInfoMap) {
            PackageUseInfo packageUseInfo2 = (PackageUseInfo) ((HashMap) this.mPackageUseInfoMap).get(str);
            packageUseInfo = packageUseInfo2 == null ? null : new PackageUseInfo(packageUseInfo2);
        }
        return packageUseInfo;
    }

    public final void read$1(Reader reader) {
        HashMap hashMap = new HashMap();
        BufferedReader bufferedReader = new BufferedReader(reader);
        String readLine = bufferedReader.readLine();
        if (readLine == null) {
            throw new IllegalStateException("No version line found.");
        }
        if (!readLine.startsWith("PACKAGE_MANAGER__PACKAGE_DEX_USAGE__")) {
            throw new IllegalStateException("Invalid version line: ".concat(readLine));
        }
        int parseInt = Integer.parseInt(readLine.substring(36));
        if (parseInt != 2) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(parseInt, "Unexpected package-dex-use version: ", ". Not reading from it", "PackageDexUsage");
            return;
        }
        HashSet hashSet = new HashSet();
        for (String str : Build.SUPPORTED_ABIS) {
            hashSet.add(VMRuntime.getInstructionSet(str));
        }
        String str2 = null;
        PackageUseInfo packageUseInfo = null;
        while (true) {
            String readLine2 = bufferedReader.readLine();
            if (readLine2 == null) {
                synchronized (this.mPackageUseInfoMap) {
                    ((HashMap) this.mPackageUseInfoMap).clear();
                    ((HashMap) this.mPackageUseInfoMap).putAll(hashMap);
                }
                return;
            }
            boolean z = true;
            if (readLine2.startsWith("#")) {
                if (str2 == null) {
                    throw new IllegalStateException("Malformed PackageDexUsage file. Expected package line before dex line.");
                }
                String substring = readLine2.substring(1);
                String readLine3 = bufferedReader.readLine();
                if (readLine3 == null) {
                    throw new IllegalStateException("Could not find dexUseInfo line");
                }
                String[] split = readLine3.split(",");
                if (split.length < 3) {
                    throw new IllegalStateException("Invalid PackageDexUsage line: ".concat(readLine3));
                }
                Set readLoadingPackages = readLoadingPackages(bufferedReader);
                String readLine4 = bufferedReader.readLine();
                if (readLine4 == null) {
                    throw new IllegalStateException("Could not find the classLoaderContext line.");
                }
                if ("=UnsupportedClassLoaderContext=".equals(readLine4)) {
                    continue;
                } else {
                    int parseInt2 = Integer.parseInt(split[0]);
                    String str3 = split[1];
                    if ("0".equals(str3)) {
                        z = false;
                    } else if (!"1".equals(str3)) {
                        throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Unknown bool encoding: ", str3));
                    }
                    DexUseInfo dexUseInfo = new DexUseInfo(z, parseInt2, readLine4, null);
                    dexUseInfo.mLoadingPackages.addAll(readLoadingPackages);
                    for (int i = 2; i < split.length; i++) {
                        String str4 = split[i];
                        if (hashSet.contains(str4)) {
                            ((HashSet) dexUseInfo.mLoaderIsas).add(split[i]);
                        } else {
                            Slog.wtf("PackageDexUsage", "Unsupported ISA when parsing PackageDexUsage: " + str4);
                        }
                    }
                    if (hashSet.isEmpty()) {
                        Slog.wtf("PackageDexUsage", "Ignore dexPath when parsing PackageDexUsage because of unsupported isas. dexPath=" + substring);
                    } else {
                        ((HashMap) packageUseInfo.mDexUseInfoMap).put(substring, dexUseInfo);
                    }
                }
            } else if (readLine2.startsWith("+")) {
                ((HashMap) packageUseInfo.mPrimaryCodePaths).put(readLine2.substring(1), readLoadingPackages(bufferedReader));
            } else {
                packageUseInfo = new PackageUseInfo(readLine2);
                hashMap.put(readLine2, packageUseInfo);
                str2 = readLine2;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.pm.AbstractStatsBase
    public final void readInternal(WatchedArrayMap watchedArrayMap) {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(getFile().openRead()));
                try {
                    read$1(bufferedReader2);
                    IoUtils.closeQuietly(bufferedReader2);
                } catch (FileNotFoundException unused) {
                    bufferedReader = bufferedReader2;
                    IoUtils.closeQuietly(bufferedReader);
                } catch (IOException e) {
                    e = e;
                    bufferedReader = bufferedReader2;
                    Slog.w("PackageDexUsage", "Failed to parse package dex usage.", e);
                    IoUtils.closeQuietly(bufferedReader);
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    IoUtils.closeQuietly(bufferedReader);
                    throw th;
                }
            } catch (FileNotFoundException unused2) {
                IoUtils.closeQuietly(bufferedReader);
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void syncData(Map map, Map map2, List list) {
        synchronized (this.mPackageUseInfoMap) {
            try {
                Iterator it = ((HashMap) this.mPackageUseInfoMap).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String str = (String) entry.getKey();
                    ArrayList arrayList = (ArrayList) list;
                    if (!arrayList.contains(str)) {
                        PackageUseInfo packageUseInfo = (PackageUseInfo) entry.getValue();
                        HashMap hashMap = (HashMap) map;
                        Set set = (Set) hashMap.get(str);
                        if (set == null) {
                            it.remove();
                        } else {
                            Iterator it2 = ((HashMap) packageUseInfo.mDexUseInfoMap).entrySet().iterator();
                            while (it2.hasNext()) {
                                if (!set.contains(Integer.valueOf(((DexUseInfo) ((Map.Entry) it2.next()).getValue()).mOwnerUserId))) {
                                    it2.remove();
                                }
                            }
                            Set set2 = (Set) ((HashMap) map2).get(str);
                            Iterator it3 = ((HashMap) packageUseInfo.mPrimaryCodePaths).entrySet().iterator();
                            while (it3.hasNext()) {
                                Map.Entry entry2 = (Map.Entry) it3.next();
                                if (set2.contains((String) entry2.getKey())) {
                                    Iterator it4 = ((Set) entry2.getValue()).iterator();
                                    while (it4.hasNext()) {
                                        String str2 = (String) it4.next();
                                        if (!arrayList.contains(str2) && !hashMap.containsKey(str2)) {
                                            it4.remove();
                                        }
                                    }
                                } else {
                                    it3.remove();
                                }
                            }
                            if (!(!((HashMap) packageUseInfo.mPrimaryCodePaths).isEmpty()) && ((HashMap) packageUseInfo.mDexUseInfoMap).isEmpty()) {
                                it.remove();
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void write(Writer writer) {
        HashMap hashMap = new HashMap();
        synchronized (this.mPackageUseInfoMap) {
            try {
                for (Map.Entry entry : ((HashMap) this.mPackageUseInfoMap).entrySet()) {
                    hashMap.put((String) entry.getKey(), new PackageUseInfo((PackageUseInfo) entry.getValue()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        FastPrintWriter fastPrintWriter = new FastPrintWriter(writer);
        fastPrintWriter.print("PACKAGE_MANAGER__PACKAGE_DEX_USAGE__");
        fastPrintWriter.println(2);
        for (Map.Entry entry2 : hashMap.entrySet()) {
            String str = (String) entry2.getKey();
            PackageUseInfo packageUseInfo = (PackageUseInfo) entry2.getValue();
            fastPrintWriter.println(str);
            for (Map.Entry entry3 : ((HashMap) packageUseInfo.mPrimaryCodePaths).entrySet()) {
                String str2 = (String) entry3.getKey();
                Set set = (Set) entry3.getValue();
                fastPrintWriter.println("+" + str2);
                fastPrintWriter.println("@" + String.join(",", set));
            }
            for (Map.Entry entry4 : ((HashMap) packageUseInfo.mDexUseInfoMap).entrySet()) {
                String str3 = (String) entry4.getKey();
                DexUseInfo dexUseInfo = (DexUseInfo) entry4.getValue();
                fastPrintWriter.println("#" + str3);
                CharSequence[] charSequenceArr = new CharSequence[2];
                charSequenceArr[0] = Integer.toString(dexUseInfo.mOwnerUserId);
                charSequenceArr[1] = dexUseInfo.mIsUsedByOtherApps ? "1" : "0";
                fastPrintWriter.print(String.join(",", charSequenceArr));
                Iterator it = ((HashSet) dexUseInfo.mLoaderIsas).iterator();
                while (it.hasNext()) {
                    fastPrintWriter.print("," + ((String) it.next()));
                }
                fastPrintWriter.println();
                fastPrintWriter.println("@" + String.join(",", dexUseInfo.mLoadingPackages));
                fastPrintWriter.println(dexUseInfo.mClassLoaderContext);
            }
        }
        fastPrintWriter.flush();
    }

    public final void writeInternal() {
        FileOutputStream fileOutputStream;
        AtomicFile file = getFile();
        try {
            fileOutputStream = file.startWrite();
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                write(outputStreamWriter);
                outputStreamWriter.flush();
                file.finishWrite(fileOutputStream);
            } catch (IOException e) {
                e = e;
                if (fileOutputStream != null) {
                    file.failWrite(fileOutputStream);
                }
                Slog.e("PackageDexUsage", "Failed to write usage for dex files", e);
            }
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }

    @Override // com.android.server.pm.AbstractStatsBase
    public final /* bridge */ /* synthetic */ void writeInternal(Object obj) {
        writeInternal();
    }
}
