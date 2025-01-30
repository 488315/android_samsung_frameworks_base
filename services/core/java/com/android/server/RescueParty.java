package com.android.server;

import android.app.IWallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.VersionedPackage;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.PowerManager;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.am.SettingsToPropertiesMapper;
import com.android.server.pm.PackageManagerServiceUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public abstract class RescueParty {
  static final long DEFAULT_FACTORY_RESET_THROTTLE_DURATION_MIN = 10;
  static final long DEFAULT_OBSERVING_DURATION_MS = TimeUnit.DAYS.toMillis(2);
  static final int DEVICE_CONFIG_RESET_MODE = 4;
  static final int LEVEL_FACTORY_RESET = 7;
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
  static final String PROP_RESCUE_BOOT_COUNT = "sys.rescue_boot_count";
  static final String TAG = "RescueParty";

  public static int mapRescueLevelToUserImpact(int i) {
    switch (i) {
      case 1:
      case 2:
        return 10;
      case 3:
      case 4:
        return 50;
      case 5:
      case 6:
      case 7:
        return 100;
      default:
        return 0;
    }
  }

  public static void registerHealthObserver(Context context) {
    PackageWatchdog.getInstance(context)
        .registerHealthObserver(RescuePartyObserver.getInstance(context));
  }

  public static boolean isDisabled() {
    if (SystemProperties.getBoolean(PROP_ENABLE_RESCUE, false)
        || !"0x4f4c".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"))) {
      return false;
    }
    if (SystemProperties.getBoolean(
        "persist.device_config.configuration.disable_rescue_party", false)) {
      Slog.v(TAG, "Disabled because of DeviceConfig flag");
      return true;
    }
    if ("emergency".equals(SystemProperties.get("persist.sys.emergency_reset", ""))) {
      Slog.w(TAG, "!@ RescueParty last level is already done");
      return true;
    }
    if (Build.IS_ENG) {
      Slog.v(TAG, "Disabled because of eng build");
      return true;
    }
    if (Build.IS_USERDEBUG && isUsbActive()) {
      Slog.v(TAG, "Disabled because of active USB connection");
      return true;
    }
    if (!SystemProperties.getBoolean("persist.sys.disable_rescue", false)) {
      return false;
    }
    Slog.v(TAG, "Disabled because of manual property");
    return true;
  }

  public static boolean isAttemptingFactoryReset() {
    return isFactoryResetPropertySet() || isRebootPropertySet();
  }

  public static boolean isFactoryResetPropertySet() {
    return SystemProperties.getBoolean("sys.attempting_factory_reset", false);
  }

  public static boolean isRebootPropertySet() {
    return SystemProperties.getBoolean("sys.attempting_reboot", false);
  }

  public static void onSettingsProviderPublished(Context context) {
    handleNativeRescuePartyResets();
    DeviceConfig.setMonitorCallback(
        context.getContentResolver(),
        Executors.newSingleThreadExecutor(),
        new RescuePartyMonitorCallback(context));
  }

  public static void resetDeviceConfigForPackages(List list) {
    if (list == null) {
      return;
    }
    ArraySet<String> arraySet = new ArraySet();
    Iterator it = list.iterator();
    RescuePartyObserver instanceIfCreated = RescuePartyObserver.getInstanceIfCreated();
    if (instanceIfCreated != null) {
      while (it.hasNext()) {
        Set affectedNamespaceSet = instanceIfCreated.getAffectedNamespaceSet((String) it.next());
        if (affectedNamespaceSet != null) {
          arraySet.addAll(affectedNamespaceSet);
        }
      }
    }
    Set presetNamespacesForPackages = getPresetNamespacesForPackages(list);
    if (presetNamespacesForPackages != null) {
      arraySet.addAll(presetNamespacesForPackages);
    }
    for (String str : arraySet) {
      try {
        if (!DeviceConfig.setProperties(new DeviceConfig.Properties.Builder(str).build())) {
          PackageManagerServiceUtils.logCriticalInfo(
              6,
              "Failed to clear properties under "
                  + str
                  + ". Running `device_config get_sync_disabled_for_tests` will confirm if"
                  + " config-bulk-update is enabled.");
        }
      } catch (DeviceConfig.BadConfigException unused) {
        PackageManagerServiceUtils.logCriticalInfo(
            5, "namespace " + str + " is already banned, skip reset.");
      }
    }
  }

  public static Set getPresetNamespacesForPackages(List list) {
    ArraySet arraySet = new ArraySet();
    try {
      try {
        String[] split =
            DeviceConfig.getString(NAMESPACE_CONFIGURATION, NAMESPACE_TO_PACKAGE_MAPPING_FLAG, "")
                .split(",");
        for (int i = 0; i < split.length; i++) {
          if (!TextUtils.isEmpty(split[i])) {
            String[] split2 = split[i].split(XmlUtils.STRING_ARRAY_SEPARATOR);
            if (split2.length != 2) {
              throw new RuntimeException("Invalid mapping entry: " + split[i]);
            }
            String str = split2[0];
            if (list.contains(split2[1])) {
              arraySet.add(str);
            }
          }
        }
        return arraySet;
      } catch (Exception e) {
        arraySet.clear();
        Slog.e(TAG, "Failed to read preset package to namespaces mapping.", e);
        return arraySet;
      }
    } catch (Throwable unused) {
      return arraySet;
    }
  }

  public static long getElapsedRealtime() {
    return SystemClock.elapsedRealtime();
  }

  public class RescuePartyMonitorCallback implements DeviceConfig.MonitorCallback {
    public Context mContext;

    public RescuePartyMonitorCallback(Context context) {
      this.mContext = context;
    }

    public void onNamespaceUpdate(String str) {
      RescueParty.startObservingPackages(this.mContext, str);
    }

    public void onDeviceConfigAccess(String str, String str2) {
      RescuePartyObserver.getInstance(this.mContext).recordDeviceConfigAccess(str, str2);
    }
  }

  public static void startObservingPackages(Context context, String str) {
    RescuePartyObserver rescuePartyObserver = RescuePartyObserver.getInstance(context);
    Set callingPackagesSet = rescuePartyObserver.getCallingPackagesSet(str);
    if (callingPackagesSet == null) {
      return;
    }
    ArrayList arrayList = new ArrayList();
    arrayList.addAll(callingPackagesSet);
    Slog.i(TAG, "Starting to observe: " + arrayList + ", updated namespace: " + str);
    PackageWatchdog.getInstance(context)
        .startObservingHealth(rescuePartyObserver, arrayList, DEFAULT_OBSERVING_DURATION_MS);
  }

  public static void handleNativeRescuePartyResets() {
    if (SettingsToPropertiesMapper.isNativeFlagsResetPerformed()) {
      String[] resetNativeCategories = SettingsToPropertiesMapper.getResetNativeCategories();
      for (int i = 0; i < resetNativeCategories.length; i++) {
        if (!NAMESPACE_CONFIGURATION.equals(resetNativeCategories[i])) {
          DeviceConfig.resetToDefaults(4, resetNativeCategories[i]);
        }
      }
    }
  }

  public static int getMaxRescueLevel(boolean z) {
    return (!z
            || SystemProperties.getBoolean(
                "persist.device_config.configuration.disable_rescue_party_factory_reset", false))
        ? 3
        : 7;
  }

  public static int getRescueLevel(int i, boolean z, String str) {
    if ("com.android.systemui".equals(str)) {
      i = SystemProperties.getInt("persist.sys.rescue_level", 0) + 1;
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
      return Math.min(getMaxRescueLevel(z), 4);
    }
    if (i == 5) {
      return Math.min(getMaxRescueLevel(z), 5);
    }
    if (i == 6) {
      return Math.min(getMaxRescueLevel(z), 6);
    }
    if (i >= 7) {
      return Math.min(getMaxRescueLevel(z), 7);
    }
    Slog.w(TAG, "Expected positive mitigation count, was " + i);
    return 0;
  }

  public static void executeRescueLevel(Context context, String str, int i) {
    String str2;
    if (isFactoryResetPropertySet() || isRebootPropertySet()) {
      Slog.w(TAG, "!@ Reboot was already triggered, don't level-up");
      return;
    }
    if ("com.android.systemui".equals(str) || str == null) {
      StringBuilder sb = new StringBuilder();
      sb.append("!@ Attempting rescue level ");
      sb.append(levelToString(i));
      sb.append("(");
      sb.append(i);
      sb.append(")");
      if (str == null) {
        str2 = "";
      } else {
        str2 = " by " + str;
      }
      sb.append(str2);
      Slog.w(TAG, sb.toString());
      SystemProperties.set("persist.sys.rescue_level", Integer.toString(i));
    }
    try {
      executeRescueLevelInternal(context, i, str);
      EventLogTags.writeRescueSuccess(i);
      String str3 = "Finished rescue level " + levelToString(i);
      if (!TextUtils.isEmpty(str)) {
        str3 = str3 + " for package " + str;
      }
      PackageManagerServiceUtils.logCriticalInfo(3, str3);
    } catch (Throwable th) {
      logRescueException(i, str, th);
    }
  }

  /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
  jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:28:0x00db
  	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
  	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
  	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
  	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
  */
  public static void executeRescueLevelInternal(
      final android.content.Context r10, final int r11, final java.lang.String r12) {
    /*
        Method dump skipped, instructions count: 262
        To view this dump add '--comments-level debug' option
    */
    throw new UnsupportedOperationException(
        "Method not decompiled:"
            + " com.android.server.RescueParty.executeRescueLevelInternal(android.content.Context,"
            + " int, java.lang.String):void");
  }

  public static /* synthetic */ void lambda$executeRescueLevelInternal$0(
      Context context, String str, int i) {
    try {
      PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
      String str2 = "";
      if (powerManager != null) {
        StringBuilder sb = new StringBuilder();
        sb.append(TAG);
        if (str != null) {
          str2 = " by " + str;
        }
        sb.append(str2);
        powerManager.reboot(sb.toString());
        return;
      }
      StringBuilder sb2 = new StringBuilder();
      sb2.append("reboot,RescueParty");
      if (str != null) {
        str2 = " by " + str;
      }
      sb2.append(str2);
      SystemProperties.set("sys.powerctl", sb2.toString());
    } catch (Throwable th) {
      logRescueException(i, str, th);
    }
  }

  public static /* synthetic */ void lambda$executeRescueLevelInternal$1(
      Context context, String str, int i) {
    try {
      PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
      String str2 = "";
      if (powerManager != null) {
        StringBuilder sb = new StringBuilder();
        sb.append(TAG);
        if (str != null) {
          str2 = " by " + str;
        }
        sb.append(str2);
        powerManager.reboot(sb.toString());
        return;
      }
      StringBuilder sb2 = new StringBuilder();
      sb2.append("reboot,RescueParty");
      if (str != null) {
        str2 = " by " + str;
      }
      sb2.append(str2);
      SystemProperties.set("sys.powerctl", sb2.toString());
    } catch (Throwable th) {
      logRescueException(i, str, th);
    }
  }

  public static void runDumpstateAndWait(Context context, int i) {
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
    int i2 = 0;
    while (true) {
      int i3 = i2 + 1;
      if (i2 >= i) {
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
        i2 = i3;
      }
    }
  }

  public static void saveRescuePartyLog() {
    try {
      File file = new File("/cache/recovery/rescueparty_log");
      if (file.exists()) {
        file.delete();
      }
      String format =
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
              .format(new Date(System.currentTimeMillis() - 600000));
      Slog.w(
          TAG,
          "saveRescuePartyLog : logcat -v raw -b crash -t '"
              + format
              + "' -f /cache/recovery/rescueparty_log");
      Runtime.getRuntime()
          .exec(
              new String[] {
                "logcat",
                "-v",
                "raw",
                "-b",
                "crash",
                "-t",
                format,
                "-f",
                "/cache/recovery/rescueparty_log"
              });
    } catch (Exception unused) {
    }
  }

  public static void logRescueException(int i, String str, Throwable th) {
    String completeMessage = ExceptionUtils.getCompleteMessage(th);
    EventLogTags.writeRescueFailure(i, completeMessage);
    String str2 = "Failed rescue level " + levelToString(i);
    if (!TextUtils.isEmpty(str)) {
      str2 = str2 + " for package " + str;
    }
    PackageManagerServiceUtils.logCriticalInfo(6, str2 + ": " + completeMessage);
  }

  public static void resetAllSettingsIfNecessary(Context context, int i, int i2) {
    RuntimeException runtimeException;
    if (SystemProperties.getInt("sys.max_rescue_level_attempted", 0) >= i2) {
      return;
    }
    SystemProperties.set("sys.max_rescue_level_attempted", Integer.toString(i2));
    ContentResolver contentResolver = context.getContentResolver();
    try {
      Settings.Global.resetToDefaultsAsUser(contentResolver, null, i, 0);
      runtimeException = null;
    } catch (Exception e) {
      runtimeException = new RuntimeException("Failed to reset global settings", e);
    }
    for (int i3 : getAllUserIds()) {
      try {
        Settings.Secure.resetToDefaultsAsUser(contentResolver, null, i, i3);
      } catch (Exception e2) {
        runtimeException = new RuntimeException("Failed to reset secure settings for " + i3, e2);
      }
    }
    if (runtimeException != null) {
      throw runtimeException;
    }
  }

  public static void resetDeviceConfig(Context context, boolean z, String str) {
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

  public static void resetAllAffectedNamespaces(Context context) {
    Set<String> allAffectedNamespaceSet =
        RescuePartyObserver.getInstance(context).getAllAffectedNamespaceSet();
    Slog.w(
        TAG,
        "Performing reset for all affected namespaces: "
            + Arrays.toString(allAffectedNamespaceSet.toArray()));
    for (String str : allAffectedNamespaceSet) {
      if (!NAMESPACE_CONFIGURATION.equals(str)) {
        DeviceConfig.resetToDefaults(4, str);
      }
    }
  }

  public static void resetWallpaperData(Context context) {
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
  }

  public static void resetDlsData(Context context) {
    Slog.w(TAG, "reset dls settings");
    Settings.System.putInt(context.getContentResolver(), "dls_state", 0);
    Settings.System.putInt(context.getContentResolver(), "lockstar_enabled", 0);
    Settings.System.putInt(context.getContentResolver(), "plugin_lock_sub_enabled", 0);
  }

  public static void performScopedReset(Context context, String str) {
    Set<String> affectedNamespaceSet =
        RescuePartyObserver.getInstance(context).getAffectedNamespaceSet(str);
    if (affectedNamespaceSet != null) {
      Slog.w(
          TAG,
          "Performing scoped reset for package: "
              + str
              + ", affected namespaces: "
              + Arrays.toString(affectedNamespaceSet.toArray()));
      for (String str2 : affectedNamespaceSet) {
        if (!NAMESPACE_CONFIGURATION.equals(str2)) {
          DeviceConfig.resetToDefaults(4, str2);
        }
      }
    }
  }

  public class RescuePartyObserver implements PackageWatchdog.PackageHealthObserver {
    public static RescuePartyObserver sRescuePartyObserver;
    public final Context mContext;
    public final Map mCallingPackageNamespaceSetMap = new HashMap();
    public final Map mNamespaceCallingPackageSetMap = new HashMap();

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public String getName() {
      return "rescue-party-observer";
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean isPersistent() {
      return true;
    }

    public final boolean shouldThrottleReboot() {
      return false;
    }

    public RescuePartyObserver(Context context) {
      this.mContext = context;
    }

    public static RescuePartyObserver getInstance(Context context) {
      RescuePartyObserver rescuePartyObserver;
      synchronized (RescuePartyObserver.class) {
        if (sRescuePartyObserver == null) {
          sRescuePartyObserver = new RescuePartyObserver(context);
        }
        rescuePartyObserver = sRescuePartyObserver;
      }
      return rescuePartyObserver;
    }

    public static RescuePartyObserver getInstanceIfCreated() {
      RescuePartyObserver rescuePartyObserver;
      synchronized (RescuePartyObserver.class) {
        rescuePartyObserver = sRescuePartyObserver;
      }
      return rescuePartyObserver;
    }

    public static void reset() {
      synchronized (RescuePartyObserver.class) {
        sRescuePartyObserver = null;
      }
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public int onHealthCheckFailed(VersionedPackage versionedPackage, int i, int i2) {
      if (RescueParty.isDisabled()) {
        return 0;
      }
      if (i == 3 || i == 4) {
        return RescueParty.mapRescueLevelToUserImpact(
            RescueParty.getRescueLevel(
                i2, mayPerformReboot(versionedPackage), versionedPackage.getPackageName()));
      }
      return 0;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean execute(VersionedPackage versionedPackage, int i, int i2) {
      if (RescueParty.isDisabled()) {
        return false;
      }
      if (i != 3 && i != 4) {
        return false;
      }
      RescueParty.executeRescueLevel(
          this.mContext,
          versionedPackage.getPackageName(),
          RescueParty.getRescueLevel(
              i2, mayPerformReboot(versionedPackage), versionedPackage.getPackageName()));
      return true;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean mayObservePackage(String str) {
      try {
        if (this.mContext.getPackageManager().getModuleInfo(str, 0) != null) {
          return true;
        }
      } catch (PackageManager.NameNotFoundException unused) {
      }
      return isPersistentSystemApp(str);
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public int onBootLoop(int i) {
      if (RescueParty.isDisabled()) {
        return 0;
      }
      return RescueParty.mapRescueLevelToUserImpact(RescueParty.getRescueLevel(i, true, null));
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean executeBootLoopMitigation(int i) {
      if (RescueParty.isDisabled()) {
        return false;
      }
      RescueParty.executeRescueLevel(
          this.mContext, null, RescueParty.getRescueLevel(i, !shouldThrottleReboot(), null));
      return true;
    }

    public final boolean mayPerformReboot(VersionedPackage versionedPackage) {
      return (versionedPackage == null
              || shouldThrottleReboot()
              || !"com.android.systemui".equals(versionedPackage.getPackageName()))
          ? false
          : true;
    }

    public final boolean isPersistentSystemApp(String str) {
      try {
        return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 9) == 9;
      } catch (PackageManager.NameNotFoundException unused) {
        return false;
      }
    }

    public final synchronized void recordDeviceConfigAccess(String str, String str2) {
      Set set = (Set) this.mCallingPackageNamespaceSetMap.get(str);
      if (set == null) {
        set = new ArraySet();
        this.mCallingPackageNamespaceSetMap.put(str, set);
      }
      set.add(str2);
      Set set2 = (Set) this.mNamespaceCallingPackageSetMap.get(str2);
      if (set2 == null) {
        set2 = new ArraySet();
      }
      set2.add(str);
      this.mNamespaceCallingPackageSetMap.put(str2, set2);
    }

    public final synchronized Set getAffectedNamespaceSet(String str) {
      return (Set) this.mCallingPackageNamespaceSetMap.get(str);
    }

    public final synchronized Set getAllAffectedNamespaceSet() {
      return new HashSet(this.mNamespaceCallingPackageSetMap.keySet());
    }

    public final synchronized Set getCallingPackagesSet(String str) {
      return (Set) this.mNamespaceCallingPackageSetMap.get(str);
    }
  }

  public static int[] getAllUserIds() {
    int[] iArr = {0};
    try {
      for (File file : FileUtils.listFilesOrEmpty(Environment.getDataSystemDeDirectory())) {
        try {
          int parseInt = Integer.parseInt(file.getName());
          if (parseInt != 0) {
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

  public static boolean isUsbActive() {
    if (SystemProperties.getBoolean("ro.hardware.virtual_device", false)) {
      Slog.v(TAG, "Assuming virtual device is connected over USB");
      return true;
    }
    try {
      return "CONFIGURED"
          .equals(
              FileUtils.readTextFile(new File("/sys/class/android_usb/android0/state"), 128, "")
                  .trim());
    } catch (Throwable th) {
      Slog.w(TAG, "Failed to determine if device was on USB", th);
      return false;
    }
  }

  public static String levelToString(int i) {
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
        return "FACTORY_RESET";
      default:
        return Integer.toString(i);
    }
  }
}
