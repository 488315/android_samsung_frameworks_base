package com.android.server;

import android.app.IWallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.VersionedPackage;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.FileUtils;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.sysprop.CrashRecoveryProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.Flags;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.statusbar.StatusBarManagerService;

import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.wifi.util.SemWifiRescueParty;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class RescueParty {
    static final long DEFAULT_FACTORY_RESET_THROTTLE_DURATION_MIN = 1440;
    static final long DEFAULT_OBSERVING_DURATION_MS = TimeUnit.DAYS.toMillis(2);
    static final int DEFAULT_RESCUE_NON_REBOOT_LEVEL_LIMIT = 2;
    static final int DEVICE_CONFIG_RESET_MODE = 4;
    static final int LEVEL_ERASE_NON_APPDATA = 7;
    static final int LEVEL_FACTORY_RESET = 8;
    public static final int LEVEL_ISRB_BOOT = 6;
    static final int LEVEL_NONE = 0;
    static final int LEVEL_RESET_OTHERS = 5;
    static final int LEVEL_RESET_SETTINGS_TRUSTED_DEFAULTS = 3;
    static final int LEVEL_RESET_SETTINGS_UNTRUSTED_CHANGES = 2;
    static final int LEVEL_RESET_SETTINGS_UNTRUSTED_DEFAULTS = 1;
    static final int LEVEL_WARM_REBOOT = 4;
    static final String NAMESPACE_CONFIGURATION = "configuration";
    static final String NAMESPACE_TO_PACKAGE_MAPPING_FLAG = "namespace_to_package_mapping";
    static final String PROP_ENABLE_RESCUE = "persist.sys.enable_rescue";
    static final int RESCUE_LEVEL_ALL_DEVICE_CONFIG_RESET = 2;
    static final int RESCUE_LEVEL_ERASE_NON_APPDATA = 9;
    static final int RESCUE_LEVEL_FACTORY_RESET = 10;
    static final int RESCUE_LEVEL_ISRB_BOOT = 8;
    static final int RESCUE_LEVEL_NONE = 0;
    static final int RESCUE_LEVEL_RESET_OTHERS = 7;
    static final int RESCUE_LEVEL_RESET_SETTINGS_TRUSTED_DEFAULTS = 6;
    static final int RESCUE_LEVEL_RESET_SETTINGS_UNTRUSTED_CHANGES = 5;
    static final int RESCUE_LEVEL_RESET_SETTINGS_UNTRUSTED_DEFAULTS = 4;
    static final int RESCUE_LEVEL_SCOPED_DEVICE_CONFIG_RESET = 1;
    static final int RESCUE_LEVEL_WARM_REBOOT = 3;
    static final String RESCUE_NON_REBOOT_LEVEL_LIMIT = "persist.sys.rescue_non_reboot_level_limit";
    static final String TAG = "RescueParty";
    public static boolean isUnrecoverable;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RescuePartyMonitorCallback implements DeviceConfig.MonitorCallback {
        public Context mContext;

        public final void onDeviceConfigAccess(String str, String str2) {
            RescuePartyObserver rescuePartyObserver =
                    RescuePartyObserver.getInstance(this.mContext);
            synchronized (rescuePartyObserver) {
                try {
                    Set set =
                            (Set)
                                    ((HashMap) rescuePartyObserver.mCallingPackageNamespaceSetMap)
                                            .get(str);
                    if (set == null) {
                        set = new ArraySet();
                        ((HashMap) rescuePartyObserver.mCallingPackageNamespaceSetMap)
                                .put(str, set);
                    }
                    set.add(str2);
                    Set set2 =
                            (Set)
                                    ((HashMap) rescuePartyObserver.mNamespaceCallingPackageSetMap)
                                            .get(str2);
                    if (set2 == null) {
                        set2 = new ArraySet();
                    }
                    set2.add(str);
                    ((HashMap) rescuePartyObserver.mNamespaceCallingPackageSetMap).put(str2, set2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onNamespaceUpdate(String str) {
            Set set;
            Context context = this.mContext;
            RescuePartyObserver rescuePartyObserver = RescuePartyObserver.getInstance(context);
            synchronized (rescuePartyObserver) {
                set = (Set) ((HashMap) rescuePartyObserver.mNamespaceCallingPackageSetMap).get(str);
            }
            if (set == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(set);
            Slog.i(
                    RescueParty.TAG,
                    "Starting to observe: " + arrayList + ", updated namespace: " + str);
            PackageWatchdog.getInstance(context)
                    .startObservingHealth(
                            rescuePartyObserver,
                            arrayList,
                            RescueParty.DEFAULT_OBSERVING_DURATION_MS);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RescuePartyObserver implements PackageWatchdog.PackageHealthObserver {
        public static RescuePartyObserver sRescuePartyObserver;
        public final Context mContext;
        public final Map mCallingPackageNamespaceSetMap = new HashMap();
        public final Map mNamespaceCallingPackageSetMap = new HashMap();

        public RescuePartyObserver(Context context) {
            this.mContext = context;
        }

        public static RescuePartyObserver getInstance(Context context) {
            RescuePartyObserver rescuePartyObserver;
            synchronized (RescuePartyObserver.class) {
                try {
                    if (sRescuePartyObserver == null) {
                        sRescuePartyObserver = new RescuePartyObserver(context);
                    }
                    rescuePartyObserver = sRescuePartyObserver;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return rescuePartyObserver;
        }

        public static void reset() {
            synchronized (RescuePartyObserver.class) {
                sRescuePartyObserver = null;
            }
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public final boolean execute(VersionedPackage versionedPackage, int i, int i2) {
            int m82$$Nest$smgetSecRescueLevel;
            if (RescueParty.m83$$Nest$smisDisabled()) {
                return false;
            }
            if (i != 3 && i != 4) {
                return false;
            }
            if (Flags.recoverabilityDetection()) {
                m82$$Nest$smgetSecRescueLevel =
                        RescueParty.m81$$Nest$smgetRescueLevel(
                                i2,
                                versionedPackage != null
                                        ? Constants.SYSTEMUI_PACKAGE_NAME.equals(
                                                versionedPackage.getPackageName())
                                        : false,
                                versionedPackage);
            } else {
                m82$$Nest$smgetSecRescueLevel =
                        RescueParty.m82$$Nest$smgetSecRescueLevel(
                                i2,
                                versionedPackage != null
                                        ? Constants.SYSTEMUI_PACKAGE_NAME.equals(
                                                versionedPackage.getPackageName())
                                        : false,
                                versionedPackage);
            }
            RescueParty.m80$$Nest$smexecuteRescueLevel(
                    this.mContext,
                    versionedPackage == null ? null : versionedPackage.getPackageName(),
                    m82$$Nest$smgetSecRescueLevel);
            return true;
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public final boolean executeBootLoopMitigation(int i) {
            if (RescueParty.m83$$Nest$smisDisabled()) {
                return false;
            }
            RescueParty.m80$$Nest$smexecuteRescueLevel(
                    this.mContext,
                    null,
                    Flags.recoverabilityDetection()
                            ? RescueParty.m81$$Nest$smgetRescueLevel(i, true, null)
                            : RescueParty.m82$$Nest$smgetSecRescueLevel(i, true, null));
            return true;
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public final String getName() {
            return "rescue-party-observer";
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public final boolean mayObservePackage(String str) {
            try {
                if (this.mContext.getPackageManager().getModuleInfo(str, 0) != null) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException | IllegalStateException unused) {
            }
            try {
                return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 9)
                        == 9;
            } catch (PackageManager.NameNotFoundException unused2) {
                return false;
            }
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public final int onBootLoop(int i) {
            if (RescueParty.m83$$Nest$smisDisabled()) {
                return 0;
            }
            return Flags.recoverabilityDetection()
                    ? RescueParty.m84$$Nest$smmapRescueLevelToUserImpact(
                            RescueParty.m81$$Nest$smgetRescueLevel(i, true, null))
                    : RescueParty.m84$$Nest$smmapRescueLevelToUserImpact(
                            RescueParty.m82$$Nest$smgetSecRescueLevel(i, true, null));
        }

        @Override // com.android.server.PackageWatchdog.PackageHealthObserver
        public final int onHealthCheckFailed(VersionedPackage versionedPackage, int i, int i2) {
            if (RescueParty.m83$$Nest$smisDisabled() || !(i == 3 || i == 4)) {
                return 0;
            }
            if (Flags.recoverabilityDetection()) {
                return RescueParty.m84$$Nest$smmapRescueLevelToUserImpact(
                        RescueParty.m81$$Nest$smgetRescueLevel(
                                i2,
                                versionedPackage != null
                                        ? Constants.SYSTEMUI_PACKAGE_NAME.equals(
                                                versionedPackage.getPackageName())
                                        : false,
                                versionedPackage));
            }
            return RescueParty.m84$$Nest$smmapRescueLevelToUserImpact(
                    RescueParty.m82$$Nest$smgetSecRescueLevel(
                            i2,
                            versionedPackage != null
                                    ? Constants.SYSTEMUI_PACKAGE_NAME.equals(
                                            versionedPackage.getPackageName())
                                    : false,
                            versionedPackage));
        }
    }

    /* renamed from: -$$Nest$smexecuteEraseAppData, reason: not valid java name */
    public static void m79$$Nest$smexecuteEraseAppData(Context context, String str, int i) {
        try {
            FileWriter fileWriter =
                    new FileWriter(new File("/efs/recovery/rescueparty"), StandardCharsets.UTF_8);
            try {
                fileWriter.write("emergency_reset");
                fileWriter.flush();
                PowerManager powerManager =
                        (PowerManager) context.getSystemService(PowerManager.class);
                String str2 = "";
                if (powerManager != null) {
                    StringBuilder sb = new StringBuilder(TAG);
                    if (str != null) {
                        str2 = " by ".concat(str);
                    }
                    sb.append(str2);
                    powerManager.reboot(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder("reboot,RescueParty");
                    if (str != null) {
                        str2 = " by ".concat(str);
                    }
                    sb2.append(str2);
                    SystemProperties.set("sys.powerctl", sb2.toString());
                }
                fileWriter.close();
            } finally {
            }
        } catch (IOException e) {
            logRescueException(i, str, e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00e3, code lost:

       if (r5 != null) goto L42;
    */
    /* renamed from: -$$Nest$smexecuteRescueLevel, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m80$$Nest$smexecuteRescueLevel(
            android.content.Context r21, java.lang.String r22, int r23) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.RescueParty.m80$$Nest$smexecuteRescueLevel(android.content.Context,"
                    + " java.lang.String, int):void");
    }

    /* renamed from: -$$Nest$smgetRescueLevel, reason: not valid java name */
    public static int m81$$Nest$smgetRescueLevel(
            int i, boolean z, VersionedPackage versionedPackage) {
        if (versionedPackage == null && i > 0) {
            i++;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return Math.min(getMaxRescueLevel(z), 3);
        }
        if (i == 4) {
            return Math.min(getMaxRescueLevel(z), 4);
        }
        if (i == 5) {
            return Math.min(getMaxRescueLevel(z), 5);
        }
        if (i == 6) {
            return Math.min(getMaxRescueLevel(z), 6);
        }
        if (i == 7) {
            return Math.min(getMaxRescueLevel(z), 7);
        }
        if (i == 8) {
            return Math.min(getMaxRescueLevel(z), 8);
        }
        if (i >= 9) {
            return Math.min(getMaxRescueLevel(z), 10);
        }
        return 0;
    }

    /* renamed from: -$$Nest$smgetSecRescueLevel, reason: not valid java name */
    public static int m82$$Nest$smgetSecRescueLevel(
            int i, boolean z, VersionedPackage versionedPackage) {
        if (versionedPackage == null
                || Constants.SYSTEMUI_PACKAGE_NAME.equals(versionedPackage.getPackageName())) {
            int i2 = SystemProperties.getInt("persist.sys.rescue_level", 0);
            if (versionedPackage == null) {
                if (i2 == 0) {
                    i = 4;
                } else if (i2 == 3) {
                    i = 5;
                } else if (i2 == 4) {
                    i = 1;
                }
            }
            i = i2 + 1;
        }
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 3;
        }
        if (i == 4) {
            return 4;
        }
        if (i == 5) {
            return Math.min(getMaxRescueLevel(z), 5);
        }
        if (i == 6) {
            return Math.min(getMaxRescueLevel(z), 6);
        }
        if (i == 7) {
            return Math.min(getMaxRescueLevel(z), 7);
        }
        if (i >= 8) {
            isUnrecoverable = i > 8;
            return Math.min(getMaxRescueLevel(z), 8);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                i, "Expected positive mitigation count, was ", TAG);
        return 0;
    }

    /* renamed from: -$$Nest$smisDisabled, reason: not valid java name */
    public static boolean m83$$Nest$smisDisabled() {
        if (SystemProperties.getBoolean(PROP_ENABLE_RESCUE, false)
                || !CoreRune.IS_DEBUG_LEVEL_LOW) {
            return false;
        }
        if (SystemProperties.getBoolean(
                "persist.device_config.configuration.disable_rescue_party", false)) {
            Slog.v(TAG, "Disabled because of DeviceConfig flag");
        } else {
            if (!SystemProperties.getBoolean("persist.sys.disable_rescue", false)) {
                return false;
            }
            Slog.v(TAG, "Disabled because of manual property");
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0010  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    /* renamed from: -$$Nest$smmapRescueLevelToUserImpact, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int m84$$Nest$smmapRescueLevelToUserImpact(int r5) {
        /*
            boolean r0 = com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.Flags.recoverabilityDetection()
            r1 = 10
            r2 = 50
            r3 = 100
            r4 = 0
            if (r0 == 0) goto L22
            switch(r5) {
                case 1: goto L26;
                case 2: goto L1f;
                case 3: goto L1d;
                case 4: goto L1a;
                case 5: goto L17;
                case 6: goto L14;
                case 7: goto L12;
                case 8: goto L12;
                case 9: goto L12;
                case 10: goto L12;
                default: goto L10;
            }
        L10:
            r1 = r4
            goto L26
        L12:
            r1 = r3
            goto L26
        L14:
            r1 = 80
            goto L26
        L17:
            r1 = 75
            goto L26
        L1a:
            r1 = 71
            goto L26
        L1d:
            r1 = r2
            goto L26
        L1f:
            r1 = 40
            goto L26
        L22:
            switch(r5) {
                case 1: goto L26;
                case 2: goto L26;
                case 3: goto L1d;
                case 4: goto L1d;
                case 5: goto L12;
                case 6: goto L12;
                case 7: goto L12;
                case 8: goto L12;
                default: goto L25;
            }
        L25:
            goto L10
        L26:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.RescueParty.m84$$Nest$smmapRescueLevelToUserImpact(int):int");
    }

    public static void executeFactoryReset(final Context context, final String str, final int i) {
        CrashRecoveryProperties.attemptingFactoryReset(Boolean.TRUE);
        runDumpstateAndWait();
        SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(false));
        SystemProperties.set("persist.sys.rescue_mode", "");
        SystemProperties.set("persist.sys.isrb_alertnum", Integer.toString(0));
        CrashRecoveryProperties.lastFactoryResetTimeMs(Long.valueOf(System.currentTimeMillis()));
        new Thread(
                        new Runnable() { // from class: com.android.server.RescueParty.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                try {
                                    if (!"emergency"
                                                    .equals(
                                                            SystemProperties.get(
                                                                    "persist.sys.emergency_reset",
                                                                    "unknown"))
                                            || RescueParty.isUnrecoverable) {
                                        StringBuilder sb = new StringBuilder();
                                        String str2 = str;
                                        if (str2 == null) {
                                            str2 = "PlatformReset";
                                        }
                                        sb.append(str2);
                                        sb.append(" (");
                                        sb.append(i);
                                        sb.append(")");
                                        String sb2 = sb.toString();
                                        RecoverySystem.rebootPromptAndWipeAppData(
                                                context, "RescueParty by " + sb2);
                                    } else {
                                        RescueParty.m79$$Nest$smexecuteEraseAppData(
                                                context, str, i);
                                    }
                                } catch (Throwable th) {
                                    RescueParty.logRescueException(i, str, th);
                                }
                            }
                        })
                .start();
    }

    public static void executeISRBReboot(Context context, String str, int i) {
        SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(true));
        SystemProperties.set("persist.sys.isrb_alertnum", Integer.toString(0));
        Slog.e(TAG, "LEVEL_ISRB_BOOT rescueParty set ISRB_ENABLE.");
        CrashRecoveryProperties.attemptingReboot(Boolean.TRUE);
        runDumpstateAndWait();
        new Thread(new RescueParty$$ExternalSyntheticLambda0(context, str, i, 0)).start();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:55:0x00e3
    	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void executeRescueLevelInternal(
            android.content.Context r8, java.lang.String r9, int r10) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.RescueParty.executeRescueLevelInternal(android.content.Context,"
                    + " java.lang.String, int):void");
    }

    public static void executeResetOthers(Context context, String str) {
        if (Constants.SYSTEMUI_PACKAGE_NAME.equals(str)) {
            Slog.w(TAG, "setSysUiSafeMode = true");
            StatusBarManagerService.this.mSysUiSafeMode = true;
        }
        Slog.w(TAG, "reset wallpaper");
        try {
            IWallpaperManager asInterface =
                    IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
            if (asInterface != null) {
                asInterface.clearWallpaper("android", 1, 0);
                asInterface.clearWallpaper("android", 17, 0);
                asInterface.clearWallpaper("android", 33, 0);
                asInterface.clearWallpaper("android", 2, 0);
                asInterface.clearWallpaper("android", 18, 0);
            }
        } catch (Throwable unused) {
        }
        Slog.w(TAG, "reset dls settings");
        Settings.System.putInt(context.getContentResolver(), "dls_state", 0);
        Settings.System.putInt(context.getContentResolver(), "lockstar_enabled", 0);
        Settings.System.putInt(context.getContentResolver(), "plugin_lock_sub_enabled", 0);
        SemWifiRescueParty.resetAllWifiStoredData(getAllUserIds());
    }

    public static int[] getAllUserIds() {
        int identifier = UserHandle.SYSTEM.getIdentifier();
        int[] iArr = {identifier};
        try {
            for (File file : FileUtils.listFilesOrEmpty(Environment.getDataSystemDeDirectory())) {
                try {
                    int parseInt = Integer.parseInt(file.getName());
                    if (parseInt != identifier) {
                        iArr = ArrayUtils.appendInt(iArr, parseInt);
                    }
                } catch (NumberFormatException unused) {
                }
            }
        } catch (Throwable th) {
            Slog.w(TAG, "Trouble discovering users", th);
        }
        return iArr;
    }

    public static long getElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public static int getMaxRescueLevel(boolean z) {
        if (!Flags.recoverabilityDetection()) {
            return (!z
                            || SystemProperties.getBoolean(
                                    "persist.device_config.configuration.disable_rescue_party_factory_reset",
                                    false))
                    ? 3
                    : 8;
        }
        if (!z
                || SystemProperties.getBoolean(
                        "persist.device_config.configuration.disable_rescue_party_factory_reset",
                        false)) {
            return SystemProperties.getInt(RESCUE_NON_REBOOT_LEVEL_LIMIT, 2);
        }
        return 10;
    }

    public static String getRescuePartyReason() {
        return levelToString(SystemProperties.getInt("persist.sys.rescue_level", 0));
    }

    public static boolean isRebootPropertySet() {
        return ((Boolean) CrashRecoveryProperties.attemptingReboot().orElse(Boolean.FALSE))
                .booleanValue();
    }

    public static String levelToString(int i) {
        if (!Flags.recoverabilityDetection()) {
            switch (i) {
                case 0:
                    return "NONE";
                case 1:
                    return "RESET_SETTINGS_UNTRUSTED_DEFAULTS";
                case 2:
                    return "RESET_SETTINGS_UNTRUSTED_CHANGES";
                case 3:
                    return "RESET_SETTINGS_TRUSTED_DEFAULTS";
                case 4:
                    return "WARM_REBOOT";
                case 5:
                    return "RESET_OTHERS";
                case 6:
                    return "ISRB_BOOT";
                case 7:
                    return "ERASE_NON_APPDATA";
                case 8:
                    return "FACTORY_RESET";
                default:
                    return Integer.toString(i);
            }
        }
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "SCOPED_DEVICE_CONFIG_RESET";
            case 2:
                return "ALL_DEVICE_CONFIG_RESET";
            case 3:
                return "WARM_REBOOT";
            case 4:
                return "RESET_SETTINGS_UNTRUSTED_DEFAULTS";
            case 5:
                return "RESET_SETTINGS_UNTRUSTED_CHANGES";
            case 6:
                return "RESET_SETTINGS_TRUSTED_DEFAULTS";
            case 7:
                return "RESET_OTHERS";
            case 8:
                return "ISRB_BOOT";
            case 9:
                return "ERASE_NON_APPDATA";
            case 10:
                return "FACTORY_RESET";
            default:
                return Integer.toString(i);
        }
    }

    public static void logRescueException(int i, String str, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append(th.getMessage());
        while (true) {
            th = th.getCause();
            if (th == null) {
                break;
            }
            sb.append(": ");
            sb.append(th.getMessage());
        }
        String sb2 = sb.toString();
        EventLog.writeEvent(2903, Integer.valueOf(i), sb2);
        String str2 = "Failed rescue level " + levelToString(i);
        if (!TextUtils.isEmpty(str)) {
            str2 = AnyMotionDetector$$ExternalSyntheticOutline0.m(str2, " for package ", str);
        }
        PackageManagerServiceUtils.logCriticalInfo(6, str2 + ": " + sb2);
    }

    public static void performScopedReset(Context context, String str) {
        Set<String> set;
        RescuePartyObserver rescuePartyObserver = RescuePartyObserver.getInstance(context);
        synchronized (rescuePartyObserver) {
            set = (Set) ((HashMap) rescuePartyObserver.mCallingPackageNamespaceSetMap).get(str);
        }
        if (set != null) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Performing scoped reset for package: ",
                            str,
                            ", affected namespaces: ");
            m.append(Arrays.toString(set.toArray()));
            Slog.w(TAG, m.toString());
            for (String str2 : set) {
                if (!NAMESPACE_CONFIGURATION.equals(str2)) {
                    DeviceConfig.resetToDefaults(4, str2);
                }
            }
        }
    }

    public static void resetAllAffectedNamespaces(Context context) {
        HashSet hashSet;
        RescuePartyObserver rescuePartyObserver = RescuePartyObserver.getInstance(context);
        synchronized (rescuePartyObserver) {
            hashSet =
                    new HashSet(
                            ((HashMap) rescuePartyObserver.mNamespaceCallingPackageSetMap)
                                    .keySet());
        }
        Slog.w(
                TAG,
                "Performing reset for all affected namespaces: "
                        + Arrays.toString(hashSet.toArray()));
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!NAMESPACE_CONFIGURATION.equals(str)) {
                DeviceConfig.resetToDefaults(4, str);
            }
        }
    }

    public static void resetAllSettingsIfNecessary(Context context, int i, int i2) {
        RuntimeException runtimeException;
        if (((Integer) CrashRecoveryProperties.maxRescueLevelAttempted().orElse(0)).intValue()
                >= i2) {
            return;
        }
        CrashRecoveryProperties.maxRescueLevelAttempted(Integer.valueOf(i2));
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Settings.Global.resetToDefaultsAsUser(
                    contentResolver, null, i, UserHandle.SYSTEM.getIdentifier());
            runtimeException = null;
        } catch (Exception e) {
            runtimeException = new RuntimeException("Failed to reset global settings", e);
        }
        for (int i3 : getAllUserIds()) {
            try {
                Settings.Secure.resetToDefaultsAsUser(contentResolver, null, i, i3);
            } catch (Exception e2) {
                runtimeException =
                        new RuntimeException(
                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        i3, "Failed to reset secure settings for "),
                                e2);
            }
        }
        if (runtimeException != null) {
            throw runtimeException;
        }
    }

    public static void resetDeviceConfig(Context context, String str, boolean z) {
        context.getContentResolver();
        try {
            if (!z || str == null) {
                resetAllAffectedNamespaces(context);
            } else {
                performScopedReset(context, str);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset config settings", e);
        }
    }

    public static void resetDeviceConfigForPackages(List list) {
        RescuePartyObserver rescuePartyObserver;
        Set set;
        ArraySet arraySet = new ArraySet();
        ArrayList arrayList = (ArrayList) list;
        Iterator it = arrayList.iterator();
        synchronized (RescuePartyObserver.class) {
            rescuePartyObserver = RescuePartyObserver.sRescuePartyObserver;
        }
        if (rescuePartyObserver != null) {
            while (it.hasNext()) {
                String str = (String) it.next();
                synchronized (rescuePartyObserver) {
                    set =
                            (Set)
                                    ((HashMap) rescuePartyObserver.mCallingPackageNamespaceSetMap)
                                            .get(str);
                }
                if (set != null) {
                    arraySet.addAll(set);
                }
            }
        }
        ArraySet arraySet2 = new ArraySet();
        try {
            try {
                String[] split =
                        DeviceConfig.getString(
                                        NAMESPACE_CONFIGURATION,
                                        NAMESPACE_TO_PACKAGE_MAPPING_FLAG,
                                        "")
                                .split(",");
                for (int i = 0; i < split.length; i++) {
                    if (!TextUtils.isEmpty(split[i])) {
                        String[] split2 = split[i].split(":");
                        if (split2.length != 2) {
                            throw new RuntimeException("Invalid mapping entry: " + split[i]);
                        }
                        String str2 = split2[0];
                        if (arrayList.contains(split2[1])) {
                            arraySet2.add(str2);
                        }
                    }
                }
            } catch (Exception e) {
                arraySet2.clear();
                Slog.e(TAG, "Failed to read preset package to namespaces mapping.", e);
            }
        } catch (Throwable unused) {
        }
        arraySet.addAll((Collection) arraySet2);
        Iterator it2 = arraySet.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            try {
                if (!DeviceConfig.setProperties(
                        new DeviceConfig.Properties.Builder(str3).build())) {
                    PackageManagerServiceUtils.logCriticalInfo(
                            6,
                            "Failed to clear properties under "
                                    + str3
                                    + ". Running `device_config get_sync_disabled_for_tests` will"
                                    + " confirm if config-bulk-update is enabled.");
                }
            } catch (DeviceConfig.BadConfigException unused2) {
                PackageManagerServiceUtils.logCriticalInfo(
                        5, "namespace " + str3 + " is already banned, skip reset.");
            }
        }
    }

    public static void runDumpstateAndWait() {
        Slog.v(TAG, "!@ make dumpstate_sys_error for rescueparty");
        if ("1".equals(SystemProperties.get("dumpstate.is_running", "0"))) {
            Slog.w(TAG, "cancel previous dumpstate, and start new one");
            SystemProperties.set("ctl.stop", "bugreportd");
            SystemProperties.set("ctl.stop", "bugreportm");
            SystemProperties.set("ctl.stop", "dumpstate");
            SystemProperties.set("ctl.stop", "dumpstatez");
        }
        SystemProperties.set("bugreport.mode", "sys_rescue");
        SystemProperties.set("dumpstate.process", TAG);
        SystemProperties.set("ctl.start", "bugreportm");
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i >= 50) {
                return;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Slog.w(TAG, "Failed to sleep", e);
            }
            if ("stopped".equals(SystemProperties.get("init.svc.bugreportm", "stopped"))) {
                return;
            } else {
                i = i2;
            }
        }
    }

    public static void setISRBmode() {
        SystemProperties.set("persist.sys.isrb_havesentlog", Boolean.toString(true));
        if (SystemProperties.getBoolean("sys.isrblevel.callreboot", false)) {
            return;
        }
        SystemProperties.set(
                "persist.sys.rescue_level",
                Integer.toString(Flags.recoverabilityDetection() ? 8 : 6));
        SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(false));
        SystemProperties.set("persist.sys.rescue_mode", "isrb_boot");
    }

    public static void truncateAndCopyFile(File file, File file2) {
        try {
            BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(new FileInputStream(file));
            try {
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(file2));
                try {
                    byte[] bArr = new byte[(int) Math.min(65536L, file.length())];
                    int read = bufferedInputStream.read(bArr);
                    if (read != -1) {
                        bufferedOutputStream.write(bArr, 0, read);
                        bufferedOutputStream.flush();
                    }
                    bufferedOutputStream.close();
                    bufferedInputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
        }
    }
}
