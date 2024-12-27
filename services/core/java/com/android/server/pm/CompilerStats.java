package com.android.server.pm;

import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Log;

import com.android.internal.util.FastPrintWriter;
import com.android.server.utils.WatchedArrayMap;

import libcore.io.IoUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CompilerStats extends AbstractStatsBase {
    public final Map packageStats;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageStats {
        public final Map compileTimePerCodePath = new ArrayMap(2);
        public final String packageName;

        public PackageStats(String str) {
            this.packageName = str;
        }

        public final void setCompileTime(long j, String str) {
            String substring = str.substring(str.lastIndexOf(File.separatorChar) + 1);
            synchronized (this.compileTimePerCodePath) {
                try {
                    if (j <= 0) {
                        ((ArrayMap) this.compileTimePerCodePath).remove(substring);
                    } else {
                        ((ArrayMap) this.compileTimePerCodePath).put(substring, Long.valueOf(j));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public CompilerStats() {
        super("package-cstats.list", "CompilerStats_DiskWriter", false);
        this.packageStats = new HashMap();
    }

    public final PackageStats getOrCreatePackageStats(String str) {
        PackageStats packageStats;
        synchronized (this.packageStats) {
            try {
                PackageStats packageStats2 = (PackageStats) ((HashMap) this.packageStats).get(str);
                if (packageStats2 != null) {
                    return packageStats2;
                }
                synchronized (this.packageStats) {
                    packageStats = new PackageStats(str);
                    ((HashMap) this.packageStats).put(str, packageStats);
                }
                return packageStats;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void read() {
        read((WatchedArrayMap) null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x007a, code lost:

       throw new java.lang.IllegalArgumentException("Could not parse data " + r0);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void read(java.io.Reader r9) {
        /*
            r8 = this;
            java.lang.String r0 = "Unexpected version: "
            java.lang.String r1 = "Invalid version line: "
            java.util.Map r2 = r8.packageStats
            monitor-enter(r2)
            java.util.Map r3 = r8.packageStats     // Catch: java.lang.Throwable -> L60
            java.util.HashMap r3 = (java.util.HashMap) r3     // Catch: java.lang.Throwable -> L60
            r3.clear()     // Catch: java.lang.Throwable -> L60
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r9 = r3.readLine()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            if (r9 == 0) goto L9e
            java.lang.String r4 = "PACKAGE_MANAGER__COMPILER_STATS__"
            boolean r4 = r9.startsWith(r4)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            if (r4 == 0) goto L94
            r1 = 33
            java.lang.String r9 = r9.substring(r1)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            int r9 = java.lang.Integer.parseInt(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r1 = 1
            if (r9 != r1) goto L82
            com.android.server.pm.CompilerStats$PackageStats r9 = new com.android.server.pm.CompilerStats$PackageStats     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r0 = "fake package"
            r9.<init>(r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
        L35:
            java.lang.String r0 = r3.readLine()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            if (r0 == 0) goto L80
            java.lang.String r4 = "-"
            boolean r4 = r0.startsWith(r4)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            if (r4 == 0) goto L7b
            r4 = 58
            int r4 = r0.indexOf(r4)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r5 = -1
            if (r4 == r5) goto L64
            if (r4 == r1) goto L64
            java.lang.String r5 = r0.substring(r1, r4)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            int r4 = r4 + 1
            java.lang.String r0 = r0.substring(r4)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            long r6 = java.lang.Long.parseLong(r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r9.setCompileTime(r6, r5)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            goto L35
        L60:
            r8 = move-exception
            goto Laf
        L62:
            r8 = move-exception
            goto La6
        L64:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r9.<init>()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r1 = "Could not parse data "
            r9.append(r1)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r9.append(r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            throw r8     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
        L7b:
            com.android.server.pm.CompilerStats$PackageStats r9 = r8.getOrCreatePackageStats(r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            goto L35
        L80:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L60
            return
        L82:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r1.append(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r9 = r1.toString()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            throw r8     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
        L94:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r9 = r1.concat(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            throw r8     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
        L9e:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r9 = "No version line found."
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            throw r8     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
        La6:
            java.lang.String r9 = "PackageManager"
            java.lang.String r0 = "Error parsing compiler stats"
            android.util.Log.e(r9, r0, r8)     // Catch: java.lang.Throwable -> L60
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L60
            return
        Laf:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L60
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.CompilerStats.read(java.io.Reader):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.pm.AbstractStatsBase
    public final void readInternal(WatchedArrayMap watchedArrayMap) {
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 =
                    new BufferedReader(new InputStreamReader(getFile().openRead()));
            try {
                read(bufferedReader2);
                IoUtils.closeQuietly(bufferedReader2);
            } catch (FileNotFoundException unused) {
                bufferedReader = bufferedReader2;
                IoUtils.closeQuietly(bufferedReader);
            } catch (Throwable th) {
                th = th;
                bufferedReader = bufferedReader2;
                IoUtils.closeQuietly(bufferedReader);
                throw th;
            }
        } catch (FileNotFoundException unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void write(Writer writer) {
        FastPrintWriter fastPrintWriter = new FastPrintWriter(writer);
        fastPrintWriter.print("PACKAGE_MANAGER__COMPILER_STATS__");
        fastPrintWriter.println(1);
        synchronized (this.packageStats) {
            try {
                for (PackageStats packageStats : ((HashMap) this.packageStats).values()) {
                    synchronized (packageStats.compileTimePerCodePath) {
                        try {
                            if (!((ArrayMap) packageStats.compileTimePerCodePath).isEmpty()) {
                                fastPrintWriter.println(packageStats.packageName);
                                for (Map.Entry entry :
                                        ((ArrayMap) packageStats.compileTimePerCodePath)
                                                .entrySet()) {
                                    fastPrintWriter.println(
                                            PackageManagerShellCommandDataLoader.STDIN_PATH
                                                    + ((String) entry.getKey())
                                                    + ":"
                                                    + entry.getValue());
                                }
                            }
                        } finally {
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        fastPrintWriter.flush();
    }

    @Override // com.android.server.pm.AbstractStatsBase
    public final void writeInternal(Object obj) {
        FileOutputStream fileOutputStream;
        AtomicFile file = getFile();
        try {
            fileOutputStream = file.startWrite();
        } catch (IOException e) {
            e = e;
            fileOutputStream = null;
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            write(outputStreamWriter);
            outputStreamWriter.flush();
            file.finishWrite(fileOutputStream);
        } catch (IOException e2) {
            e = e2;
            if (fileOutputStream != null) {
                file.failWrite(fileOutputStream);
            }
            Log.e("PackageManager", "Failed to write compiler stats", e);
        }
    }
}
