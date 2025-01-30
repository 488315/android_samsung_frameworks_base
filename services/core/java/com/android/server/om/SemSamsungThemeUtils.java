package com.android.server.om;

import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.os.FabricatedOverlayInfo;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.util.Log;
import com.android.server.pm.pkg.AndroidPackage;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class SemSamsungThemeUtils {
    public static final boolean IS_ENG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final boolean IS_USERDEBUG = "userdebug".equals(SystemProperties.get("ro.build.type"));
    public static final List disableOverlayList = Arrays.asList("SemWT_com.android.systemui", "SemWT_android", "SemWT_MonetPalette");
    public static final List dynamicColorOverlayList = Arrays.asList("accent", "neutral", "dynamic");

    public static void deleteResourceMapFile(AndroidPackage androidPackage) {
        String baseApkPath;
        if (androidPackage == null || androidPackage.getMetaData() == null || !androidPackage.getMetaData().containsKey("resource-map") || (baseApkPath = androidPackage.getBaseApkPath()) == null) {
            return;
        }
        File file = new File("/data/overlays/remaps/" + baseApkPath.replace("/", ".") + ".map");
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean isOperationPermitted(int i, OverlayInfo overlayInfo) {
        if (i == 2000) {
            if (!(IS_ENG || IS_USERDEBUG) && OverlayInfoExt.isOverlayInfoExt(overlayInfo)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidThemeParkOverlay(AndroidPackage androidPackage, long j) {
        File file;
        if (androidPackage == null) {
            return false;
        }
        try {
            StringBuilder sb = new StringBuilder(128);
            sb.append("/data/overlays/themepark/");
            sb.append(androidPackage.getPackageName());
            sb.append("/");
            file = new File(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.exists() && file.list() != null && file.list().length == 1) {
            String name = new File(file.list()[0]).getName();
            Log.d("OverlayManager", "Park Key found for - " + androidPackage.getPackageName());
            return String.valueOf(j).equals(name);
        }
        Log.d("OverlayManager", "Park key not found for - " + androidPackage.getPackageName());
        return false;
    }

    public static boolean hasSamsungOverlayPermission(List list) {
        if (list == null) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && (str.startsWith("com.samsung.android.permission.SAMSUNG_OVERLAY_") || str.startsWith("com.samsung.android.permission.SAMSUNG_OVERLAY_COMPONENT"))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isZippedLocaleOverlay(AndroidPackage androidPackage) {
        return (androidPackage == null || androidPackage.getBaseApkPath() == null || androidPackage.getMetaData() == null || !androidPackage.getBaseApkPath().startsWith("/data/overlays/current_locale_apks/files") || !androidPackage.getMetaData().getBoolean("com.samsung.android.zippedOverlay")) ? false : true;
    }

    public static void deleteFile(String str) {
        if (str == null) {
            Log.d("OverlayManager", "deleteFile, path is null");
            return;
        }
        try {
            if (new File(str).delete()) {
                return;
            }
            Log.d("OverlayManager", "deleteFile, file deletion failed for path =" + str);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public static void createLocaleOverlayPreferenceDir(File file) {
        if (file.exists()) {
            return;
        }
        if (!file.mkdirs()) {
            Log.e("OverlayManager", "createLocaleOverlayPreferenceDir: Unable to create dir for new user - " + file);
            return;
        }
        FileUtils.setPermissions(file.toString(), 508, -1, -1);
    }

    public static boolean hasThemeParkPrefix(String str) {
        return str != null && str.startsWith("com.samsung.themedesigner");
    }

    public static boolean isThemeCenter(String str) {
        return "com.samsung.android.themecenter".equals(str);
    }

    public static boolean shouldBeDisabledInMaintenanceMode(FabricatedOverlayInfo fabricatedOverlayInfo) {
        String str = fabricatedOverlayInfo.overlayName;
        return str != null && disableOverlayList.contains(str);
    }

    public static boolean isColorThemeOverlay(FabricatedOverlayInfo fabricatedOverlayInfo) {
        String str = fabricatedOverlayInfo.overlayName;
        return str != null && str.startsWith("SemWT_");
    }

    public static boolean isDynamicColorOverlay(FabricatedOverlayInfo fabricatedOverlayInfo) {
        String str = fabricatedOverlayInfo.overlayName;
        return str != null && dynamicColorOverlayList.contains(str);
    }
}
