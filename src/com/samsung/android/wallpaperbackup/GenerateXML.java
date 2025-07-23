package com.samsung.android.wallpaperbackup;

import android.util.Log;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
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

    public static void generateXML(File file, int i, WallpaperUser wallpaperUser) {
        Log.i(TAG, "generateXML: file = " + file + ", which = " + i);
        if (file == null) {
            Log.e(TAG, "generateXML: File shouldn't not be null");
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdir()) {
            Log.d(TAG, "generateXML: parent directory(" + file.getParentFile() + ") isn't created.");
            return;
        }
        if (file.exists()) {
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

    /* JADX WARN: Removed duplicated region for block: B:58:0x024c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void generate(java.io.File r26, com.samsung.android.wallpaperbackup.WallpaperUser r27) {
        /*
            Method dump skipped, instructions count: 600
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wallpaperbackup.GenerateXML.generate(java.io.File, com.samsung.android.wallpaperbackup.WallpaperUser):void");
    }
}
