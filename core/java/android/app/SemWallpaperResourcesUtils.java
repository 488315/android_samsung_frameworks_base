package android.app;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
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
        public boolean accept(File dir, String name) {
            String fileName = name.toLowerCase();
            return fileName.endsWith(".png") || fileName.endsWith(".jpg");
        }
    };

    public static File getOMCWallpaperFile(Context context, int flag) {
        return getOMCWallpaperFile(context, flag, null);
    }

    public static File getOMCWallpaperFile(Context context, int flag, String color) {
        String omcWallpaperDirPath;
        File omcFile = null;
        String omcResourcePath = SystemProperties.get("persist.sys.omc_respath");
        if (omcResourcePath != null) {
            if ((flag & 3) == 1 || isUsedWithLockscreen()) {
                omcWallpaperDirPath = omcResourcePath + HOME_OMC_WALLPAPER_DIR_PATH;
            } else {
                omcWallpaperDirPath = omcResourcePath + LOCK_OMC_WALLPAPER_DIR_PATH;
            }
            omcFile = getOperatorFile(omcWallpaperDirPath, getOperatorFileName(context, flag, color));
            if (omcFile != null) {
                Log.d(TAG, "omc wallpaper return: " + omcFile.getAbsolutePath());
            }
        }
        return omcFile;
    }

    private static File getOperatorFile(String dirPath, String filename) {
        File file = null;
        File wallpaperDir = new File(dirPath);
        String[] wallpapers = wallpaperDir.list(mImageFileNameFilter);
        if (wallpapers != null && wallpapers.length > 0) {
            for (int i = 0; i < wallpapers.length; i++) {
                String name = wallpapers[i].substring(0, wallpapers[i].length() - 4);
                if (!TextUtils.isEmpty(name) && name.equals(filename) && (file = getFile(dirPath + wallpapers[i])) != null) {
                    break;
                }
            }
        }
        return file;
    }

    private static File getFile(String path) {
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            return file;
        }
        return null;
    }

    private static String getOperatorFileName(Context context, int flag, String color) {
        if ((flag & 3) == 1 || isUsedWithLockscreen()) {
            return DEFAULT_WALLPAPER_NAME;
        }
        return KEYGUARD_CSC_DEFAULT_WALLPAPER_NAME;
    }

    public static String getOMCVideoWallpaperFilePath(String videoName) {
        String omcVideoWallpaperFileName;
        String omcResourcePath = SystemProperties.get("persist.sys.omc_respath") + LOCK_OMC_WALLPAPER_DIR_PATH;
        if (!TextUtils.isEmpty(videoName)) {
            omcVideoWallpaperFileName = videoName;
        } else {
            omcVideoWallpaperFileName = "lockscreen_default_wallpaper.mp4";
        }
        File file = getFile(omcResourcePath + omcVideoWallpaperFileName);
        boolean fileExist = file != null;
        if (fileExist) {
            return omcResourcePath + omcVideoWallpaperFileName;
        }
        return null;
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int which) {
        return isDefaultOperatorWallpaper(context, which, null);
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int which, String color) {
        return (getCSCWallpaperFile(context, which, color) == null && getOMCWallpaperFile(context, which, color) == null) ? false : true;
    }

    public static File getCSCWallpaperFile(Context context, int flag, String color) {
        File cscFile;
        if ((flag & 3) == 1) {
            cscFile = getCSCWallpaperFile(context, color);
        } else {
            cscFile = getOperatorFile(MULTI_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, flag, color));
            if (cscFile == null) {
                cscFile = getOperatorFile(LOCK_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, flag, color));
            }
        }
        if (cscFile != null) {
            Log.d(TAG, "csc wallpaper return: " + cscFile.getAbsolutePath());
        }
        return cscFile;
    }

    private static File getCSCWallpaperFile(Context context, String color) {
        File cscFile = null;
        String path = SystemProperties.get(PROP_WALLPAPER);
        if (!TextUtils.isEmpty(path)) {
            cscFile = getFile(path);
        }
        if (cscFile == null) {
            cscFile = getFile(CHAMELEON_WALLPAPER_PATH);
        }
        if (cscFile == null && (cscFile = getOperatorFile(MULTI_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, 1, color))) == null) {
            cscFile = getOperatorFile(HOME_CSC_WALLPAPER_DIR_PATH, getOperatorFileName(context, 1, color));
        }
        if (cscFile != null) {
            Log.d(TAG, "csc wallpaper return: " + cscFile.getAbsolutePath());
        }
        return cscFile;
    }

    public static boolean isUsedWithLockscreen() {
        boolean ret = false;
        String omcResourcePath = SystemProperties.get("persist.sys.omc_respath");
        File customerFile = new File(omcResourcePath, CUSTOMER_FILE);
        if (customerFile.exists()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(customerFile);
                NodeList nodeList = doc.getElementsByTagName("Wallpaper");
                if (nodeList != null && nodeList.getLength() > 0) {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        NamedNodeMap attrs = node.getAttributes();
                        if (attrs != null && attrs.getLength() > 0) {
                            int j = 0;
                            while (true) {
                                if (j >= attrs.getLength()) {
                                    break;
                                }
                                String attrName = attrs.item(j).getNodeName().trim();
                                String attrValue = attrs.item(j).getNodeValue().trim();
                                if (!"usedWithLockScreen".equalsIgnoreCase(attrName) || !"true".equals(attrValue)) {
                                    j++;
                                } else {
                                    ret = true;
                                    break;
                                }
                            }
                        }
                        if (ret) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
