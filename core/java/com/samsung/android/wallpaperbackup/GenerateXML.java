package com.samsung.android.wallpaperbackup;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes5.dex */
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
        Log.m98i(TAG, "generateXML: file = " + file + ", which = " + which);
        if (file == null) {
            Log.m96e(TAG, "generateXML: File shouldn't not be null");
            return;
        }
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            boolean created = parent.mkdir();
            if (!created) {
                Log.m94d(TAG, "generateXML: parent directory(" + file.getParentFile() + ") isn't created.");
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
        Log.m94d(TAG, "generateXML: filePath = " + file.getPath());
        generate(file, wallpaperUser);
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x0260 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void generate(File file, WallpaperUser wallpaperUser) {
        Throwable th;
        FileWriter writer;
        Log.m98i(TAG, "generate()");
        if (file == null) {
            Log.m96e(TAG, "File shouldn't not be null");
            return;
        }
        XmlSerializer serializer = Xml.newSerializer();
        try {
            writer = new FileWriter(file);
            try {
                serializer.setOutput(writer);
                try {
                    serializer.startDocument("UTF-8", true);
                    serializer.startTag("", OBJECT_LIST_TAG);
                    serializer.attribute("", "ID", String.valueOf(0));
                    serializer.startTag("", "width");
                    serializer.text(Integer.toString(wallpaperUser.getWidth()));
                    serializer.endTag("", "width");
                    serializer.startTag("", "height");
                    serializer.text(Integer.toString(wallpaperUser.getHeight()));
                    serializer.endTag("", "height");
                    serializer.startTag("", "transparency");
                    serializer.text(Integer.toString(wallpaperUser.getTransparency()));
                    serializer.endTag("", "transparency");
                    if (!TextUtils.isEmpty(wallpaperUser.getDeviceType())) {
                        serializer.startTag("", DEVICETYPE);
                        serializer.text(wallpaperUser.getDeviceType());
                        serializer.endTag("", DEVICETYPE);
                    }
                    if (!TextUtils.isEmpty(wallpaperUser.getCoverType())) {
                        serializer.startTag("", COVERTYPE);
                        serializer.text(wallpaperUser.getCoverType());
                        serializer.endTag("", COVERTYPE);
                    }
                    if (!TextUtils.isEmpty(wallpaperUser.getPath())) {
                        serializer.startTag("", "path");
                        serializer.text(wallpaperUser.getPath());
                        serializer.endTag("", "path");
                    }
                    if (!TextUtils.isEmpty(wallpaperUser.getComponent())) {
                        serializer.startTag("", "component");
                        serializer.text(wallpaperUser.getComponent());
                        serializer.endTag("", "component");
                    }
                    serializer.startTag("", TILTSETTING);
                    serializer.text(Integer.toString(wallpaperUser.getTiltSettingValue()));
                    serializer.endTag("", TILTSETTING);
                    serializer.startTag("", WPTYPE);
                    serializer.text(Integer.toString(wallpaperUser.getWpType()));
                    serializer.endTag("", WPTYPE);
                    serializer.startTag("", PAIRED);
                    serializer.text(Boolean.toString(wallpaperUser.getIsHomeAndLockPaired()));
                    serializer.endTag("", PAIRED);
                    if (wallpaperUser.getUri() != null) {
                        serializer.startTag("", "uri");
                        serializer.text(wallpaperUser.getUri().toString());
                        serializer.endTag("", "uri");
                    }
                    if (!TextUtils.isEmpty(wallpaperUser.getExternalParams())) {
                        serializer.startTag("", EXTERNAL_PARAMS);
                        serializer.text(wallpaperUser.getExternalParams());
                        serializer.endTag("", EXTERNAL_PARAMS);
                    }
                    if (!TextUtils.isEmpty(wallpaperUser.getComponentName())) {
                        serializer.startTag("", COMPONENT_NAME);
                        serializer.text(wallpaperUser.getComponentName());
                        serializer.endTag("", COMPONENT_NAME);
                    }
                    if (!TextUtils.isEmpty(wallpaperUser.getDeviceType()) && (wallpaperUser.getDeviceType() == "folder" || wallpaperUser.getDeviceType() == BnRConstants.DEVICETYPE_TABLET)) {
                        serializer.startTag("", "orientation");
                        serializer.text(Integer.toString(wallpaperUser.getOrientation()));
                        serializer.endTag("", "orientation");
                    }
                    serializer.startTag("", "left");
                    serializer.text(Integer.toString(wallpaperUser.getLeftValue()));
                    serializer.endTag("", "left");
                    serializer.startTag("", TOP);
                    serializer.text(Integer.toString(wallpaperUser.getTopValue()));
                    serializer.endTag("", TOP);
                    serializer.startTag("", "right");
                    serializer.text(Integer.toString(wallpaperUser.getRightValue()));
                    serializer.endTag("", "right");
                    serializer.startTag("", BOTTOM);
                    serializer.text(Integer.toString(wallpaperUser.getBottomValue()));
                    serializer.endTag("", BOTTOM);
                    serializer.startTag("", ROTATION);
                    serializer.text(Integer.toString(wallpaperUser.getRotationValue()));
                    serializer.endTag("", ROTATION);
                    serializer.endTag("", OBJECT_LIST_TAG);
                    serializer.endDocument();
                    try {
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e2) {
                    e = e2;
                    writer = writer;
                    try {
                        e.printStackTrace();
                        if (writer != null) {
                            try {
                                writer.flush();
                                writer.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (writer != null) {
                            throw th;
                        }
                        try {
                            writer.flush();
                            writer.close();
                            throw th;
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            throw th;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    writer = writer;
                    if (writer != null) {
                    }
                }
            } catch (IOException e5) {
                e = e5;
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (IOException e6) {
            e = e6;
            writer = null;
        } catch (Throwable th5) {
            th = th5;
            writer = null;
        }
    }
}
