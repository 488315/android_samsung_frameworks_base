package com.android.server.pm;

import android.os.FileUtils;
import android.util.AtomicFile;
import android.util.Log;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.utils.WatchedArrayMap;

import libcore.io.IoUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class PackageUsage extends AbstractStatsBase {
    public boolean mIsHistoricalPackageUsageAvailable;

    public PackageUsage() {
        super("package-usage.list", "PackageUsage_DiskWriter", true);
        this.mIsHistoricalPackageUsageAvailable = true;
    }

    public static long parseAsLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new IOException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Failed to parse ", str, " as a long."),
                    e);
        }
    }

    public static String readLine(InputStream inputStream, StringBuilder sb) {
        sb.setLength(0);
        while (true) {
            int read = inputStream.read();
            if (read == -1) {
                if (sb.length() == 0) {
                    return null;
                }
                throw new IOException("Unexpected EOF");
            }
            if (read == 10) {
                return sb.toString();
            }
            sb.append((char) read);
        }
    }

    public static void readVersion0LP(
            WatchedArrayMap watchedArrayMap,
            InputStream inputStream,
            StringBuilder sb,
            String str) {
        while (str != null) {
            String[] split = str.split(" ");
            if (split.length != 2) {
                throw new IOException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Failed to parse ", str, " as package-timestamp pair."));
            }
            PackageSetting packageSetting = (PackageSetting) watchedArrayMap.mStorage.get(split[0]);
            if (packageSetting != null) {
                long parseAsLong = parseAsLong(split[1]);
                for (int i = 0; i < 8; i++) {
                    PackageStateUnserialized packageStateUnserialized = packageSetting.pkgState;
                    packageStateUnserialized.getClass();
                    if (i >= 0 && i < 8) {
                        packageStateUnserialized.getLastPackageUsageTimeInMills()[i] = parseAsLong;
                    }
                }
            }
            str = readLine(inputStream, sb);
        }
    }

    public static void readVersion1LP(
            WatchedArrayMap watchedArrayMap, InputStream inputStream, StringBuilder sb) {
        while (true) {
            String readLine = readLine(inputStream, sb);
            if (readLine == null) {
                return;
            }
            String[] split = readLine.split(" ");
            if (split.length != 9) {
                throw new IOException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Failed to parse ", readLine, " as a timestamp array."));
            }
            int i = 0;
            PackageSetting packageSetting = (PackageSetting) watchedArrayMap.mStorage.get(split[0]);
            if (packageSetting != null) {
                while (i < 8) {
                    int i2 = i + 1;
                    long parseAsLong = parseAsLong(split[i2]);
                    PackageStateUnserialized packageStateUnserialized = packageSetting.pkgState;
                    packageStateUnserialized.getClass();
                    if (i >= 0 && i < 8) {
                        packageStateUnserialized.getLastPackageUsageTimeInMills()[i] = parseAsLong;
                    }
                    i = i2;
                }
            }
        }
    }

    @Override // com.android.server.pm.AbstractStatsBase
    public final void readInternal(WatchedArrayMap watchedArrayMap) {
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                BufferedInputStream bufferedInputStream2 =
                        new BufferedInputStream(getFile().openRead());
                try {
                    StringBuilder sb = new StringBuilder();
                    String readLine = readLine(bufferedInputStream2, sb);
                    if (readLine != null) {
                        if ("PACKAGE_USAGE__VERSION_1".equals(readLine)) {
                            readVersion1LP(watchedArrayMap, bufferedInputStream2, sb);
                        } else {
                            readVersion0LP(watchedArrayMap, bufferedInputStream2, sb, readLine);
                        }
                    }
                    IoUtils.closeQuietly(bufferedInputStream2);
                } catch (FileNotFoundException unused) {
                    bufferedInputStream = bufferedInputStream2;
                    this.mIsHistoricalPackageUsageAvailable = false;
                    IoUtils.closeQuietly(bufferedInputStream);
                } catch (IOException e) {
                    e = e;
                    bufferedInputStream = bufferedInputStream2;
                    Log.w("PackageManager", "Failed to read package usage times", e);
                    IoUtils.closeQuietly(bufferedInputStream);
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    IoUtils.closeQuietly(bufferedInputStream);
                    throw th;
                }
            } catch (FileNotFoundException unused2) {
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // com.android.server.pm.AbstractStatsBase
    public final void writeInternal(Object obj) {
        FileOutputStream fileOutputStream;
        PackageStateUnserialized packageStateUnserialized;
        Map map = (Map) obj;
        AtomicFile file = getFile();
        try {
            fileOutputStream = file.startWrite();
            try {
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(fileOutputStream);
                FileUtils.setPermissions(
                        file.getBaseFile().getPath(),
                        FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED,
                        1000,
                        1032);
                StringBuilder sb = new StringBuilder();
                sb.append("PACKAGE_USAGE__VERSION_1");
                sb.append('\n');
                bufferedOutputStream.write(sb.toString().getBytes(StandardCharsets.US_ASCII));
                for (PackageSetting packageSetting : map.values()) {
                    if (packageSetting != null
                            && (packageStateUnserialized = packageSetting.pkgState) != null
                            && packageStateUnserialized.getLatestPackageUseTimeInMills() != 0) {
                        sb.setLength(0);
                        sb.append(packageSetting.mName);
                        for (long j : packageStateUnserialized.getLastPackageUsageTimeInMills()) {
                            sb.append(' ');
                            sb.append(j);
                        }
                        sb.append('\n');
                        bufferedOutputStream.write(
                                sb.toString().getBytes(StandardCharsets.US_ASCII));
                    }
                }
                bufferedOutputStream.flush();
                file.finishWrite(fileOutputStream);
            } catch (IOException e) {
                e = e;
                if (fileOutputStream != null) {
                    file.failWrite(fileOutputStream);
                }
                Log.e("PackageManager", "Failed to write package usage times", e);
            }
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }
}
