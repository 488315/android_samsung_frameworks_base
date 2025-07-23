package android.os;

import android.app.GameManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import dalvik.system.VMRuntime;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class GraphicsEnvironment {
    private static final String ACTION_ANGLE_FOR_ANDROID = "android.app.action.ANGLE_FOR_ANDROID";
    private static final String ACTION_ANGLE_FOR_ANDROID_TOAST_MESSAGE = "android.app.action.ANGLE_FOR_ANDROID_TOAST_MESSAGE";
    private static final String ANGLE_DRIVER_NAME = "angle";
    private static final long ANGLE_DRIVER_VERSION_CODE = 0;
    private static final String ANGLE_DRIVER_VERSION_NAME = "";
    private static final int ANGLE_GL_DRIVER_ALL_ANGLE_OFF = 0;
    private static final int ANGLE_GL_DRIVER_ALL_ANGLE_ON = 1;
    private static final String ANGLE_GL_DRIVER_CHOICE_ANGLE = "angle";
    private static final String ANGLE_GL_DRIVER_CHOICE_DEFAULT = "default";
    private static final String ANGLE_GL_DRIVER_CHOICE_NATIVE = "native";
    private static final boolean DEBUG = false;
    private static final String GPU_CONTROL_LAYER = "VK_LAYER_GPU_Control";
    private static final String GPU_CONTROL_LAYER_APP = "com.samsung.gpuwatchapp";
    private static final String GPU_CONTROL_LAYER_GLES = "libGLESLayer_GPU_Control.so";
    private static final boolean GRAPHICS_SUPPORT_GPU_CONTROL = true;
    private static final String INTENT_KEY_A4A_TOAST_MESSAGE = "A4A Toast Message";
    private static final String METADATA_DEVELOPER_DRIVER_ENABLE = "com.android.graphics.developerdriver.enable";
    private static final String METADATA_DRIVER_BUILD_TIME = "com.android.graphics.driver.build_time";
    private static final String METADATA_INJECT_LAYERS_ENABLE = "com.android.graphics.injectLayers.enable";
    private static final String PROPERTY_GFX_DRIVER_BUILD_TIME = "ro.gfx.driver_build_time";
    private static final String PROPERTY_GFX_DRIVER_PRERELEASE = "ro.gfx.driver.1";
    private static final String PROPERTY_GFX_DRIVER_PRODUCTION = "ro.gfx.driver.0";
    private static final String PROPERTY_RO_HARDWARE_EGL = "ro.hardware.egl";
    private static final String SYSTEM_ANGLE_STRING = "system";
    private static final String SYSTEM_DRIVER_NAME = "system";
    private static final long SYSTEM_DRIVER_VERSION_CODE = 0;
    private static final String SYSTEM_DRIVER_VERSION_NAME = "";
    private static final String TAG = "GraphicsEnvironment";
    private static final String UPDATABLE_DRIVER_ALLOWLIST_ALL = "*";
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_DEFAULT = 0;
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_OFF = 3;
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_PRERELEASE_DRIVER = 2;
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_PRODUCTION_DRIVER = 1;
    private static final String UPDATABLE_DRIVER_SPHAL_LIBRARIES_FILENAME = "sphal_libraries.txt";
    private static final int VULKAN_1_0 = 4194304;
    private static final int VULKAN_1_1 = 4198400;
    private static final int VULKAN_1_2 = 4202496;
    private static final int VULKAN_1_3 = 4206592;
    private static final int VULKAN_1_4 = 4210688;
    private static final GraphicsEnvironment sInstance = new GraphicsEnvironment();
    private ClassLoader mClassLoader;
    private String mLibraryPermittedPaths;
    private String mLibrarySearchPaths;
    private int mAngleOptInIndex = -1;
    private boolean mShouldUseAngle = false;

    public static native void hintActivityLaunch();

    private static native boolean isDebuggable();

    private static native void nativeSetAngleInfo(String str, boolean z, String str2, String[] strArr);

    private static native void nativeToggleAngleAsSystemDriver(boolean z);

    private static native void setDebugLayers(String str);

    private static native void setDebugLayersGLES(String str);

    private static native void setDriverPathAndSphalLibraries(String str, String str2);

    private static native void setGpuStats(String str, String str2, long j, long j2, String str3, int i);

    private static native boolean setInjectLayersPrSetDumpable();

    private static native void setLayerPaths(ClassLoader classLoader, String str);

    public static GraphicsEnvironment getInstance() {
        return sInstance;
    }

    public void setup(Context context, Bundle bundle) {
        GameManager gameManager;
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        ApplicationInfo appInfoWithMetadata = getAppInfoWithMetadata(context, packageManager, packageName);
        Trace.traceBegin(2L, "setupGpuLayers");
        setupGpuLayers(context, bundle, packageManager, packageName, appInfoWithMetadata);
        Trace.traceEnd(2L);
        Trace.traceBegin(2L, "setupAngle");
        if (setupAngle(context, bundle, packageManager, packageName)) {
            this.mShouldUseAngle = true;
            setGpuStats("angle", "", 0L, 0L, packageName, getVulkanVersion(packageManager));
            packageName = packageName;
        }
        Trace.traceEnd(2L);
        Trace.traceBegin(2L, "chooseDriver");
        if (!chooseDriver(context, bundle, packageManager, packageName, appInfoWithMetadata) && !this.mShouldUseAngle) {
            setGpuStats("system", "", 0L, SystemProperties.getLong(PROPERTY_GFX_DRIVER_BUILD_TIME, 0L), packageName, getVulkanVersion(packageManager));
        }
        Trace.traceEnd(2L);
        Trace.traceBegin(2L, "notifyGraphicsEnvironmentSetup");
        if (appInfoWithMetadata.category == 0 && (gameManager = (GameManager) context.getSystemService(GameManager.class)) != null) {
            gameManager.notifyGraphicsEnvironmentSetup();
        }
        Trace.traceEnd(2L);
    }

    public void toggleAngleAsSystemDriver(boolean z) {
        nativeToggleAngleAsSystemDriver(z);
    }

    private int getVulkanVersion(PackageManager packageManager) {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, VULKAN_1_4)) {
            return VULKAN_1_4;
        }
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, VULKAN_1_3)) {
            return VULKAN_1_3;
        }
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, 4202496)) {
            return 4202496;
        }
        return packageManager.hasSystemFeature(PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, VULKAN_1_1) ? VULKAN_1_1 : packageManager.hasSystemFeature(PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, 4194304) ? 4194304 : 0;
    }

    private boolean canInjectLayers(ApplicationInfo applicationInfo) {
        return applicationInfo.metaData != null && applicationInfo.metaData.getBoolean(METADATA_INJECT_LAYERS_ENABLE) && setInjectLayersPrSetDumpable();
    }

    public void setLayerPaths(ClassLoader classLoader, String str, String str2) {
        this.mClassLoader = classLoader;
        this.mLibrarySearchPaths = str;
        this.mLibraryPermittedPaths = str2;
    }

    public String getDebugLayerPathsFromSettings(Bundle bundle, IPackageManager iPackageManager, String str, ApplicationInfo applicationInfo) {
        if (!debugLayerEnabled(bundle, str, applicationInfo)) {
            return null;
        }
        Log.i(TAG, "GPU debug layers enabled for " + str);
        String str2 = "";
        String string = bundle.getString(Settings.Global.GPU_DEBUG_LAYER_APP, "");
        if (!string.isEmpty()) {
            Log.i(TAG, "GPU debug layer apps: " + string);
            for (String str3 : string.split(":")) {
                String debugLayerAppPaths = getDebugLayerAppPaths(iPackageManager, str3);
                if (!debugLayerAppPaths.isEmpty()) {
                    str2 = str2 + debugLayerAppPaths + File.pathSeparator;
                }
            }
        }
        return str2;
    }

    private String getDebugLayerAppPaths(IPackageManager iPackageManager, String str) {
        try {
            ApplicationInfo applicationInfo = iPackageManager.getApplicationInfo(str, 131072L, UserHandle.myUserId());
            if (applicationInfo == null) {
                Log.w(TAG, "Debug layer app '" + str + "' not installed");
                return "";
            }
            return applicationInfo.nativeLibraryDir + File.pathSeparator + applicationInfo.sourceDir + "!/lib/" + chooseAbi(applicationInfo);
        } catch (RemoteException unused) {
            return "";
        }
    }

    private boolean debugLayerEnabled(Bundle bundle, String str, ApplicationInfo applicationInfo) {
        if ((!isDebuggable() && !canInjectLayers(applicationInfo)) || bundle.getInt(Settings.Global.ENABLE_GPU_DEBUG_LAYERS, 0) == 0) {
            return false;
        }
        String string = bundle.getString(Settings.Global.GPU_DEBUG_APP, "");
        return (str == null || string.isEmpty() || str.isEmpty() || !string.equals(str)) ? false : true;
    }

    private String getGpuControlLayerAppPaths(PackageManager packageManager) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(GPU_CONTROL_LAYER_APP, 131072);
            if (applicationInfo == null) {
                Log.w(TAG, "Debug layer app 'com.samsung.gpuwatchapp' not installed");
                return "";
            }
            return applicationInfo.nativeLibraryDir + File.pathSeparator + applicationInfo.sourceDir + "!/lib/" + chooseAbi(applicationInfo) + File.pathSeparator;
        } catch (PackageManager.NameNotFoundException unused) {
            return "";
        }
    }

    private void setupGpuLayers(Context context, Bundle bundle, PackageManager packageManager, String str, ApplicationInfo applicationInfo) {
        String str2;
        if (debugLayerEnabled(bundle, str, applicationInfo)) {
            str2 = this.mLibraryPermittedPaths;
            String string = bundle.getString(Settings.Global.GPU_DEBUG_LAYERS);
            Log.i(TAG, "Vulkan debug layer list: " + string);
            if (string != null && !string.isEmpty()) {
                setDebugLayers(string);
            }
            String string2 = bundle.getString(Settings.Global.GPU_DEBUG_LAYERS_GLES);
            Log.i(TAG, "GLES debug layer list: " + string2);
            if (string2 != null && !string2.isEmpty()) {
                setDebugLayersGLES(string2);
            }
        } else if (applicationInfo.isPrivilegedApp() || ((applicationInfo.isSystemApp() && !applicationInfo.isUpdatedSystemApp()) || !getGlobalSettingsString(context.getContentResolver(), bundle, Settings.Global.GPU_CONTROL_LAYER_APPS).contains(str))) {
            str2 = "";
        } else {
            str2 = getGpuControlLayerAppPaths(packageManager);
            if (!"".equals(str2)) {
                Log.i(TAG, "GPU control app: " + str);
                setDebugLayers(GPU_CONTROL_LAYER);
                setDebugLayersGLES(GPU_CONTROL_LAYER_GLES);
            }
        }
        setLayerPaths(this.mClassLoader, str2 + this.mLibrarySearchPaths);
    }

    private static List<String> getGlobalSettingsString(ContentResolver contentResolver, Bundle bundle, String str) {
        String string;
        if (bundle != null) {
            string = bundle.getString(str);
        } else {
            string = Settings.Global.getString(contentResolver, str);
        }
        if (string != null) {
            return new ArrayList(Arrays.asList(string.split(",")));
        }
        return new ArrayList();
    }

    private static int getPackageIndex(String str, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private static ApplicationInfo getAppInfoWithMetadata(Context context, PackageManager packageManager, String str) {
        try {
            return packageManager.getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException unused) {
            return context.getApplicationInfo();
        }
    }

    private String queryAngleChoice(Context context, Bundle bundle, String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            Log.v(TAG, "No package name specified; use the system driver");
            return "default";
        }
        if (bundle != null) {
            i = bundle.getInt(Settings.Global.ANGLE_GL_DRIVER_ALL_ANGLE);
        } else {
            i = Settings.Global.getInt(context.getContentResolver(), Settings.Global.ANGLE_GL_DRIVER_ALL_ANGLE, 0);
        }
        if (i == 1) {
            Log.v(TAG, "Turn on ANGLE for all applications.");
            return "angle";
        }
        ContentResolver contentResolver = context.getContentResolver();
        List<String> globalSettingsString = getGlobalSettingsString(contentResolver, bundle, Settings.Global.ANGLE_GL_DRIVER_SELECTION_PKGS);
        List<String> globalSettingsString2 = getGlobalSettingsString(contentResolver, bundle, Settings.Global.ANGLE_GL_DRIVER_SELECTION_VALUES);
        Log.v(TAG, "Currently set values for:");
        Log.v(TAG, "  angle_gl_driver_selection_pkgs=" + globalSettingsString);
        Log.v(TAG, "  angle_gl_driver_selection_values=" + globalSettingsString2);
        if (globalSettingsString.size() != globalSettingsString2.size()) {
            Log.v(TAG, "Global.Settings values are invalid: number of packages: " + globalSettingsString.size() + ", number of values: " + globalSettingsString2.size());
            return "default";
        }
        int packageIndex = getPackageIndex(str, globalSettingsString);
        if (packageIndex >= 0) {
            this.mAngleOptInIndex = packageIndex;
            String str2 = globalSettingsString2.get(packageIndex);
            Log.v(TAG, "ANGLE Developer option for '" + str + "' set to: '" + str2 + "'");
            if (str2.equals("angle")) {
                return "angle";
            }
            if (str2.equals(ANGLE_GL_DRIVER_CHOICE_NATIVE)) {
                return ANGLE_GL_DRIVER_CHOICE_NATIVE;
            }
        }
        Log.v(TAG, str + " is not listed in per-application setting");
        if (Flags.enableAngleAllowList()) {
            String[] stringArray = context.getResources().getStringArray(R.array.config_angleAllowList);
            Log.v(TAG, "ANGLE allowlist from config: " + String.join(" ", stringArray));
            for (String str3 : stringArray) {
                if (str3.equals(str)) {
                    Log.v(TAG, "Package name " + str + " is listed in config_angleAllowList, enabling ANGLE");
                    return "angle";
                }
            }
            Log.v(TAG, str + " is not listed in ANGLE allowlist or settings, returning default");
        }
        return "default";
    }

    private String getAnglePackageName(PackageManager packageManager) {
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new Intent(ACTION_ANGLE_FOR_ANDROID), 1048576);
        if (queryIntentActivities.isEmpty()) {
            Log.v(TAG, "No ANGLE packages installed.");
            return "";
        }
        if (queryIntentActivities.size() > 1) {
            Log.v(TAG, "Too many ANGLE packages found: " + queryIntentActivities.size());
            return "";
        }
        return ((ResolveInfo) queryIntentActivities.getFirst()).activityInfo.packageName;
    }

    private String getAngleDebugPackage(Context context, Bundle bundle) {
        String string;
        if (!isDebuggable()) {
            return "";
        }
        if (bundle != null) {
            string = bundle.getString(Settings.Global.ANGLE_DEBUG_PACKAGE);
        } else {
            string = Settings.Global.getString(context.getContentResolver(), Settings.Global.ANGLE_DEBUG_PACKAGE);
        }
        return TextUtils.isEmpty(string) ? "" : string;
    }

    private boolean setupAngle(Context context, Bundle bundle, PackageManager packageManager, String str) {
        if (!SystemProperties.get(PROPERTY_RO_HARDWARE_EGL).equals("angle")) {
            String queryAngleChoice = queryAngleChoice(context, bundle, str);
            if (queryAngleChoice.equals("default")) {
                return false;
            }
            if (queryAngleChoice.equals(ANGLE_GL_DRIVER_CHOICE_NATIVE)) {
                nativeSetAngleInfo("", true, str, null);
                return false;
            }
        }
        return setupAngleFromApk(context, bundle, packageManager, str) || setupAngleFromSystem(context, bundle, str);
    }

    private boolean setupAngleFromApk(Context context, Bundle bundle, PackageManager packageManager, String str) {
        ApplicationInfo applicationInfo;
        String angleDebugPackage = getAngleDebugPackage(context, bundle);
        if (angleDebugPackage.isEmpty()) {
            applicationInfo = null;
        } else {
            Log.v(TAG, "ANGLE debug package enabled: " + angleDebugPackage);
            try {
                applicationInfo = packageManager.getApplicationInfo(angleDebugPackage, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.v(TAG, "ANGLE debug package '" + angleDebugPackage + "' not installed");
                return false;
            }
        }
        if (applicationInfo == null) {
            String anglePackageName = getAnglePackageName(packageManager);
            if (TextUtils.isEmpty(anglePackageName)) {
                return false;
            }
            Log.v(TAG, "ANGLE package enabled: " + anglePackageName);
            try {
                applicationInfo = packageManager.getApplicationInfo(anglePackageName, 1048576);
            } catch (PackageManager.NameNotFoundException unused2) {
                Log.v(TAG, "ANGLE package '" + anglePackageName + "' not installed");
                return false;
            }
        }
        nativeSetAngleInfo(applicationInfo.nativeLibraryDir + File.pathSeparator + applicationInfo.sourceDir + "!/lib/" + chooseAbi(applicationInfo), false, str, getAngleEglFeatures(context, bundle));
        return true;
    }

    private boolean setupAngleFromSystem(Context context, Bundle bundle, String str) {
        nativeSetAngleInfo("system", false, str, getAngleEglFeatures(context, bundle));
        return true;
    }

    private boolean shouldShowAngleInUseDialogBox(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.SHOW_ANGLE_IN_USE_DIALOG_BOX) == 1;
    }

    public void showAngleInUseDialogBox(Context context) {
        if (this.mShouldUseAngle && shouldShowAngleInUseDialogBox(context)) {
            Intent intent = new Intent(ACTION_ANGLE_FOR_ANDROID_TOAST_MESSAGE);
            String anglePackageName = getAnglePackageName(context.getPackageManager());
            if (anglePackageName.isEmpty()) {
                return;
            }
            intent.setPackage(anglePackageName);
            context.sendOrderedBroadcast(intent, null, new BroadcastReceiver(this) { // from class: android.os.GraphicsEnvironment.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent2) {
                    Toast.makeText(context2, getResultExtras(true).getString(GraphicsEnvironment.INTENT_KEY_A4A_TOAST_MESSAGE), 1).show();
                }
            }, null, -1, null, null);
        }
    }

    private String[] getAngleEglFeatures(Context context, Bundle bundle) {
        if (this.mAngleOptInIndex < 0) {
            return null;
        }
        List<String> globalSettingsString = getGlobalSettingsString(context.getContentResolver(), bundle, Settings.Global.ANGLE_EGL_FEATURES);
        int size = globalSettingsString.size();
        int i = this.mAngleOptInIndex;
        if (size <= i) {
            return null;
        }
        return globalSettingsString.get(i).split(":");
    }

    private String chooseDriverInternal(Bundle bundle, ApplicationInfo applicationInfo) {
        String str = SystemProperties.get(PROPERTY_GFX_DRIVER_PRODUCTION);
        boolean z = (str == null || str.isEmpty()) ? false : true;
        String str2 = SystemProperties.get(PROPERTY_GFX_DRIVER_PRERELEASE);
        boolean z2 = (str2 == null || str2.isEmpty()) ? false : true;
        if (!z && !z2) {
            Log.v(TAG, "Neither updatable production driver nor prerelease driver is supported.");
            return null;
        }
        if (!applicationInfo.isPrivilegedApp() && (!applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp())) {
            boolean z3 = (applicationInfo.metaData != null && applicationInfo.metaData.getBoolean(METADATA_DEVELOPER_DRIVER_ENABLE)) || isDebuggable();
            int i = bundle.getInt(Settings.Global.UPDATABLE_DRIVER_ALL_APPS, 0);
            if (i != 1) {
                if (i == 2) {
                    Log.v(TAG, "All apps opt in to use updatable prerelease driver.");
                    if (!z2 || !z3) {
                        return null;
                    }
                } else {
                    if (i == 3) {
                        Log.v(TAG, "The updatable driver is turned off on this device.");
                        return null;
                    }
                    String str3 = applicationInfo.packageName;
                    if (getGlobalSettingsString(null, bundle, Settings.Global.UPDATABLE_DRIVER_PRODUCTION_OPT_OUT_APPS).contains(str3)) {
                        Log.v(TAG, "App opts out for updatable production driver.");
                        return null;
                    }
                    if (getGlobalSettingsString(null, bundle, Settings.Global.UPDATABLE_DRIVER_PRERELEASE_OPT_IN_APPS).contains(str3)) {
                        Log.v(TAG, "App opts in for updatable prerelease driver.");
                        if (!z2 || !z3) {
                            return null;
                        }
                    } else {
                        if (!z) {
                            Log.v(TAG, "Updatable production driver is not supported on the device.");
                            return null;
                        }
                        boolean contains = getGlobalSettingsString(null, bundle, Settings.Global.UPDATABLE_DRIVER_PRODUCTION_OPT_IN_APPS).contains(str3);
                        List<String> globalSettingsString = getGlobalSettingsString(null, bundle, Settings.Global.UPDATABLE_DRIVER_PRODUCTION_ALLOWLIST);
                        if (!contains && globalSettingsString.indexOf("*") != 0 && !globalSettingsString.contains(str3)) {
                            Log.v(TAG, "App is not on the allowlist for updatable production driver.");
                            return null;
                        }
                        if (!contains && getGlobalSettingsString(null, bundle, Settings.Global.UPDATABLE_DRIVER_PRODUCTION_DENYLIST).contains(str3)) {
                            Log.v(TAG, "App is on the denylist for updatable production driver.");
                            return null;
                        }
                    }
                }
                return str2;
            }
            Log.v(TAG, "All apps opt in to use updatable production driver.");
            if (z) {
            }
            return str;
        }
        return null;
    }

    private boolean chooseDriver(Context context, Bundle bundle, PackageManager packageManager, String str, ApplicationInfo applicationInfo) {
        String chooseAbi;
        String chooseDriverInternal = chooseDriverInternal(bundle, applicationInfo);
        if (chooseDriverInternal == null) {
            return false;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(chooseDriverInternal, 1048704);
            ApplicationInfo applicationInfo2 = packageInfo.applicationInfo;
            if (applicationInfo2.targetSdkVersion < 26 || (chooseAbi = chooseAbi(applicationInfo2)) == null) {
                return false;
            }
            String str2 = applicationInfo2.nativeLibraryDir + File.pathSeparator + applicationInfo2.sourceDir + "!/lib/" + chooseAbi;
            String sphalLibraries = getSphalLibraries(context, chooseDriverInternal);
            Log.v(TAG, "Updatable driver package search path: " + str2 + ", required sphal libraries: " + sphalLibraries);
            setDriverPathAndSphalLibraries(str2, sphalLibraries);
            if (applicationInfo2.metaData == null) {
                throw new NullPointerException("apk's meta-data cannot be null");
            }
            String string = applicationInfo2.metaData.getString(METADATA_DRIVER_BUILD_TIME);
            if (string == null || string.length() <= 1) {
                Log.w(TAG, "com.android.graphics.driver.build_time is not set");
                string = "L0";
            }
            setGpuStats(chooseDriverInternal, packageInfo.versionName, applicationInfo2.longVersionCode, Long.parseLong(string.substring(1)), str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "updatable driver package '" + chooseDriverInternal + "' not installed");
            return false;
        }
    }

    private static String chooseAbi(ApplicationInfo applicationInfo) {
        String currentInstructionSet = VMRuntime.getCurrentInstructionSet();
        if (applicationInfo.primaryCpuAbi != null && currentInstructionSet.equals(VMRuntime.getInstructionSet(applicationInfo.primaryCpuAbi))) {
            return applicationInfo.primaryCpuAbi;
        }
        if (applicationInfo.secondaryCpuAbi == null || !currentInstructionSet.equals(VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi))) {
            return null;
        }
        return applicationInfo.secondaryCpuAbi;
    }

    private String getSphalLibraries(Context context, String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.createPackageContext(str, 4).getAssets().open(UPDATABLE_DRIVER_SPHAL_LIBRARIES_FILENAME)));
            ArrayList arrayList = new ArrayList();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    return String.join(":", arrayList);
                }
            }
        } catch (PackageManager.NameNotFoundException | IOException unused) {
            return "";
        }
    }
}
