package android.app;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/* loaded from: classes.dex */
public class SemWallpaperResourcesUtils {
    private static final String CHAMELEON_WALLPAPER_PATH = "/carrier/data/app/WallpaperChooser/Customization_DefaultBackground.jpg";
    private static final String CUSTOMER_FILE = "customer.xml";
    private static final String DEFAULT_DEVICE_COLOR_BLACK = "black";
    private static final String DEFAULT_THEME_VIDEO_RES_ID_SUFFIX = ".mp4";
    private static final String DEFAULT_WALLPAPER_NAME = "default_wallpaper";
    private static final String HOME_CSC_WALLPAPER_DIR_PATH = "/system/wallpaper/default_wallpaper/";
    private static final String HOME_OMC_WALLPAPER_DIR_PATH = "/wallpaper/drawable/";
    private static final String KEYGUARD_CSC_DEFAULT_WALLPAPER_NAME = "lockscreen_default_wallpaper";
    private static final String LOCK_CSC_WALLPAPER_DIR_PATH = "/system/wallpaper/";
    private static final String LOCK_OMC_WALLPAPER_DIR_PATH = "/wallpaper/lockscreen/drawable/";
    private static final String MULTI_CSC_WALLPAPER_DIR_PATH = "/system/csc_contents/";
    private static final String PROPERTY_OMC_RESOURCE_PATH = "persist.sys.omc_respath";
    private static final String PROP_WALLPAPER = "ro.config.wallpaper";
    private static final String TAG = "WallpaperResourcesUtils";
    private static FilenameFilter mImageFileNameFilter = new FilenameFilter() { // from class: android.app.SemWallpaperResourcesUtils.1
        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            String lowerCase = str.toLowerCase();
            return lowerCase.endsWith(".png") || lowerCase.endsWith(".jpg");
        }
    };

    public static File getOMCWallpaperFile(Context context, int i) {
        return getOMCWallpaperFile(context, i, null);
    }

    public static File getOMCWallpaperFile(Context context, int i, String str) {
        String str2;
        String str3 = SystemProperties.get("persist.sys.omc_respath");
        if (str3 == null) {
            return null;
        }
        if ((i & 3) == 1 || isUsedWithLockscreen()) {
            str2 = str3 + HOME_OMC_WALLPAPER_DIR_PATH;
        } else {
            str2 = str3 + LOCK_OMC_WALLPAPER_DIR_PATH;
        }
        File operatorFile = getOperatorFile(str2, getOperatorFileName(context, i, str));
        if (operatorFile != null) {
            Log.d(TAG, "omc wallpaper return: " + operatorFile.getAbsolutePath());
        }
        return operatorFile;
    }

    private static File getOperatorFile(String str, String str2) {
        String[] list = new File(str).list(mImageFileNameFilter);
        File file = null;
        if (list != null && list.length > 0) {
            for (int i = 0; i < list.length; i++) {
                String strSubstring = list[i].substring(0, r4.length() - 4);
                if (!TextUtils.isEmpty(strSubstring) && strSubstring.equals(str2)) {
                    file = getFile(str + list[i]);
                    if (file != null) {
                        return file;
                    }
                }
            }
        }
        return file;
    }

    private static File getFile(String str) {
        File file = new File(str);
        if (!file.exists() || file.length() <= 0) {
            return null;
        }
        return file;
    }

    private static String getOperatorFileName(Context context, int i, String str) {
        if ((i & 3) == 1 || isUsedWithLockscreen()) {
            return DEFAULT_WALLPAPER_NAME;
        }
        return KEYGUARD_CSC_DEFAULT_WALLPAPER_NAME;
    }

    public static String getOMCVideoWallpaperFilePath(String str) {
        String str2 = SystemProperties.get("persist.sys.omc_respath") + LOCK_OMC_WALLPAPER_DIR_PATH;
        if (TextUtils.isEmpty(str)) {
            str = "lockscreen_default_wallpaper.mp4";
        }
        if (getFile(str2 + str) == null) {
            return null;
        }
        return str2 + str;
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int i) {
        return isDefaultOperatorWallpaper(context, i, null);
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int i, String str) {
        return (getCSCWallpaperFile(context, i, str) == null && getOMCWallpaperFile(context, i, str) == null) ? false : true;
    }

    public static File getCSCWallpaperFile(Context context, int i, String str) {
        File operatorFile;
        if ((i & 3) == 1) {
            operatorFile = getCSCWallpaperFile(context, str);
        } else {
            File operatorFile2 = getOperatorFile(MULTI_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, i, str));
            operatorFile = operatorFile2 == null ? getOperatorFile(LOCK_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, i, str)) : operatorFile2;
        }
        if (operatorFile != null) {
            Log.d(TAG, "csc wallpaper return: " + operatorFile.getAbsolutePath());
        }
        return operatorFile;
    }

    private static File getCSCWallpaperFile(Context context, String str) {
        String str2 = SystemProperties.get(PROP_WALLPAPER);
        File file = !TextUtils.isEmpty(str2) ? getFile(str2) : null;
        if (file == null) {
            file = getFile(CHAMELEON_WALLPAPER_PATH);
        }
        if (file == null && (file = getOperatorFile(MULTI_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, 1, str))) == null) {
            file = getOperatorFile(HOME_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, 1, str));
        }
        if (file != null) {
            Log.d(TAG, "csc wallpaper return: " + file.getAbsolutePath());
        }
        return file;
    }

    public static boolean isUsedWithLockscreen() {
        File file = new File(SystemProperties.get("persist.sys.omc_respath"), CUSTOMER_FILE);
        boolean z = false;
        if (file.exists()) {
            try {
                NodeList elementsByTagName = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file).getElementsByTagName("Wallpaper");
                if (elementsByTagName != null) {
                    if (elementsByTagName.getLength() > 0) {
                        boolean z2 = false;
                        for (int i = 0; i < elementsByTagName.getLength(); i++) {
                            try {
                                NamedNodeMap attributes = elementsByTagName.item(i).getAttributes();
                                if (attributes != null && attributes.getLength() > 0) {
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= attributes.getLength()) {
                                            break;
                                        }
                                        String strTrim = attributes.item(i2).getNodeName().trim();
                                        String strTrim2 = attributes.item(i2).getNodeValue().trim();
                                        if ("usedWithLockScreen".equalsIgnoreCase(strTrim) && "true".equals(strTrim2)) {
                                            z2 = true;
                                            break;
                                        }
                                        i2++;
                                    }
                                }
                                if (z2) {
                                    return z2;
                                }
                            } catch (Exception e) {
                                e = e;
                                z = z2;
                                e.printStackTrace();
                                return z;
                            }
                        }
                        return z2;
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
        }
        return z;
    }
}
