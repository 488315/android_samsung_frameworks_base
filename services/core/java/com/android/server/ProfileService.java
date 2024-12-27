package com.android.server;

import android.content.Context;
import android.content.pm.dex.DexMetadataHelper;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.util.jar.StrictJarFile;

import com.android.internal.os.BackgroundThread;
import com.android.server.pm.SpegService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipFile;

public abstract class ProfileService extends SystemService {
    public final String SERVICE_NAME;
    public final String TAG;
    public Set packageBlockList;

    public ProfileService(Context context, String str, String str2) {
        super(context);
        this.TAG = str;
        this.SERVICE_NAME = str2;
    }

    public static boolean checkSafeToCreateProfile(String str, String str2) {
        try {
            String buildDexMetadataPathForApk = DexMetadataHelper.buildDexMetadataPathForApk(str2);
            if (!new File(buildDexMetadataPathForApk).exists()) {
                if (!new File(str2 + ".prof").exists()) {
                    return true;
                }
                Slog.d(str, "Feature is disabled because prebuilt profile already present");
                return false;
            }
            try {
                ZipFile zipFile = new ZipFile(buildDexMetadataPathForApk);
                try {
                    if (zipFile.getEntry("primary.prof") != null) {
                        Slog.d(str, "Feature is disabled because base.dm has profile");
                        zipFile.close();
                        return false;
                    }
                    Slog.d(str, "No primary.prof in base.dm");
                    zipFile.close();
                    return true;
                } catch (Throwable th) {
                    try {
                        zipFile.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                Slog.e(str, "Feature is disabled because of exception: " + e.getMessage());
                return false;
            }
        } catch (IllegalStateException e2) {
            Slog.e(str, "Feature is disabled because of exception: " + e2.getMessage());
            return false;
        }
    }

    public final boolean apkHasNumOfDexFiles(int i, String str) {
        StrictJarFile strictJarFile = null;
        try {
            try {
                StrictJarFile strictJarFile2 = new StrictJarFile(str, false, false);
                try {
                    StringBuilder sb = new StringBuilder("classes");
                    sb.append(i > 1 ? String.valueOf(i) : "");
                    sb.append(".dex");
                    boolean z = strictJarFile2.findEntry(sb.toString()) != null;
                    try {
                        strictJarFile2.close();
                    } catch (IOException unused) {
                    }
                    return z;
                } catch (IOException e) {
                    e = e;
                    strictJarFile = strictJarFile2;
                    Slog.w(this.TAG, "Cannot read " + str + " for counting dex files, error: " + e);
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return true;
                } catch (Throwable th) {
                    th = th;
                    strictJarFile = strictJarFile2;
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public abstract boolean checkAppId(int i);

    public final boolean checkUserAndService(int i, int i2) {
        if (!isServiceRunning()) {
            return false;
        }
        if (i >= 0) {
            return !((this instanceof SpegService) ^ true) || checkAppId(i2);
        }
        Slog.w(this.TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid user id: "));
        return false;
    }

    public abstract IBinder getBinderOfService();

    public final Set initPackageBlockList(String str) {
        HashSet hashSet = new HashSet();
        boolean m45m = BatteryService$$ExternalSyntheticOutline0.m45m(str);
        String str2 = this.TAG;
        if (m45m) {
            try {
                BufferedReader bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(str), StandardCharsets.UTF_8));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (!readLine.startsWith("#")) {
                            hashSet.add(readLine);
                        }
                    } finally {
                    }
                }
                bufferedReader.close();
            } catch (IOException unused) {
                Slog.w(str2, "Exception caught while reading ".concat(str));
            }
        } else {
            PinnerService$$ExternalSyntheticOutline0.m(
                    "Blocklist file ", str, " does not exist", str2);
        }
        return hashSet;
    }

    public abstract void initializeInterfaceOfService();

    public final boolean isPackageBlockListed(String str) {
        Set<String> set = this.packageBlockList;
        if (set == null) {
            return false;
        }
        if (!set.contains(str)) {
            for (String str2 : set) {
                if (str2.contains("*")) {
                    try {
                        if (str.matches(str2.replace("*", "\\S+").replace(".", "\\."))) {}
                    } catch (PatternSyntaxException unused) {
                        Slog.e(
                                this.TAG,
                                "Invalid regular expression's syntax in pattern: ".concat(str2));
                    }
                }
            }
            return false;
        }
        return true;
    }

    public abstract boolean isServiceRunning();

    @Override // com.android.server.SystemService
    public void onStart() {
        selectSuitableProfileService();
    }

    public final void selectSuitableProfileService() {
        IBinder binderOfService = getBinderOfService();
        if (binderOfService != null) {
            try {
                binderOfService.linkToDeath(
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.android.server.ProfileService.1
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                ProfileService.this.initializeInterfaceOfService();
                                ProfileService$1$$ExternalSyntheticOutline0.m(
                                        new StringBuilder(),
                                        ProfileService.this.SERVICE_NAME,
                                        " died; reconnecting",
                                        ProfileService.this.TAG);
                                ProfileService.this.selectSuitableProfileService();
                            }
                        },
                        0);
            } catch (RemoteException unused) {
                binderOfService = null;
            }
        }
        String str = this.SERVICE_NAME;
        String str2 = this.TAG;
        if (binderOfService != null) {
            setInterfaceOfService(binderOfService);
            Slog.i(str2, str + " found successfully");
            return;
        }
        Slog.w(str2, str + " not found; trying again");
        BackgroundThread.getHandler()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.android.server.ProfileService$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ProfileService.this.selectSuitableProfileService();
                            }
                        },
                        1000L);
    }

    public abstract void setInterfaceOfService(IBinder iBinder);
}
