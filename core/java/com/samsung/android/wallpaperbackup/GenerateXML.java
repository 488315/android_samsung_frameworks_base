package com.samsung.android.wallpaperbackup;

import android.util.Log;

import java.io.File;
import java.io.IOException;

public class GenerateXML {
    public static final String BOTTOM = "bottom";
    public static final String COMPONENT = "component";
    public static final String COMPONENT_NAME = "componentname";
    public static final String COVERTYPE = "covertype";
    public static final String DEVICETYPE = "devicetype";
    public static final String EXTERNAL_PARAMS = "externalParams";
    public static final String HEIGHT = "height";
    public static final String LEFT = "left";
    public static final String OBJECT_LIST_TAG = "User";
    public static final String ORIENTATION = "orientation";
    public static final String PAIRED = "isHomeAndLockPaired";
    public static final String PATH = "path";
    public static final String RIGHT = "right";
    public static final String ROTATION = "rotation";
    private static final String TAG = "GenerateXML";
    public static final String TILTSETTING = "tiltSetting";
    public static final String TOP = "top";
    private static final String TOP_TAG = "Wallpapers";
    private static final String TOP_TAG_LOCK = "lockscreen";
    public static final String TRANSPARENCY = "transparency";
    public static final String URI = "uri";
    public static final String WIDTH = "width";
    public static final String WPTYPE = "wpType";

    public static void generateXML(File file, int which, WallpaperUser wallpaperUser) {
        Log.i(TAG, "generateXML: file = " + file + ", which = " + which);
        if (file == null) {
            Log.e(TAG, "generateXML: File shouldn't not be null");
            return;
        }
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            boolean created = parent.mkdir();
            if (!created) {
                Log.d(
                        TAG,
                        "generateXML: parent directory("
                                + file.getParentFile()
                                + ") isn't created.");
                return;
            }
        }
        boolean created2 = file.exists();
        if (created2) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "generateXML: filePath = " + file.getPath());
        generate(file, wallpaperUser);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void generate(
            java.io.File r26, com.samsung.android.wallpaperbackup.WallpaperUser r27) {
        /*
            Method dump skipped, instructions count: 608
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.wallpaperbackup.GenerateXML.generate(java.io.File,"
                    + " com.samsung.android.wallpaperbackup.WallpaperUser):void");
    }
}
