package com.samsung.android.wallpaperbackup;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

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

    public static void generateXML(File file, int i, WallpaperUser wallpaperUser) throws Throwable {
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

    /* JADX WARN: Removed duplicated region for block: B:68:0x024c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void generate(File file, WallpaperUser wallpaperUser) throws Throwable {
        Throwable th;
        FileWriter fileWriter;
        Throwable th2;
        Log.i(TAG, "generate()");
        if (file == null) {
            Log.e(TAG, "File shouldn't not be null");
            return;
        }
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        try {
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } catch (IOException e2) {
            e = e2;
            fileWriter = null;
        } catch (Throwable th3) {
            th = th3;
            fileWriter = null;
        }
        try {
            xmlSerializerNewSerializer.setOutput(fileWriter);
            try {
                xmlSerializerNewSerializer.startDocument("UTF-8", true);
                xmlSerializerNewSerializer.startTag("", OBJECT_LIST_TAG);
                xmlSerializerNewSerializer.attribute("", "ID", String.valueOf(0));
                xmlSerializerNewSerializer.startTag("", "width");
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getWidth()));
                xmlSerializerNewSerializer.endTag("", "width");
                xmlSerializerNewSerializer.startTag("", "height");
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getHeight()));
                xmlSerializerNewSerializer.endTag("", "height");
                xmlSerializerNewSerializer.startTag("", "transparency");
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getTransparency()));
                xmlSerializerNewSerializer.endTag("", "transparency");
                if (!TextUtils.isEmpty(wallpaperUser.getDeviceType())) {
                    xmlSerializerNewSerializer.startTag("", DEVICETYPE);
                    xmlSerializerNewSerializer.text(wallpaperUser.getDeviceType());
                    xmlSerializerNewSerializer.endTag("", DEVICETYPE);
                }
                if (!TextUtils.isEmpty(wallpaperUser.getCoverType())) {
                    xmlSerializerNewSerializer.startTag("", COVERTYPE);
                    xmlSerializerNewSerializer.text(wallpaperUser.getCoverType());
                    xmlSerializerNewSerializer.endTag("", COVERTYPE);
                }
                if (!TextUtils.isEmpty(wallpaperUser.getPath())) {
                    xmlSerializerNewSerializer.startTag("", "path");
                    xmlSerializerNewSerializer.text(wallpaperUser.getPath());
                    xmlSerializerNewSerializer.endTag("", "path");
                }
                if (!TextUtils.isEmpty(wallpaperUser.getComponent())) {
                    xmlSerializerNewSerializer.startTag("", "component");
                    xmlSerializerNewSerializer.text(wallpaperUser.getComponent());
                    xmlSerializerNewSerializer.endTag("", "component");
                }
                xmlSerializerNewSerializer.startTag("", TILTSETTING);
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getTiltSettingValue()));
                xmlSerializerNewSerializer.endTag("", TILTSETTING);
                xmlSerializerNewSerializer.startTag("", WPTYPE);
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getWpType()));
                xmlSerializerNewSerializer.endTag("", WPTYPE);
                xmlSerializerNewSerializer.startTag("", PAIRED);
                xmlSerializerNewSerializer.text(Boolean.toString(wallpaperUser.getIsHomeAndLockPaired()));
                xmlSerializerNewSerializer.endTag("", PAIRED);
                if (wallpaperUser.getUri() != null) {
                    xmlSerializerNewSerializer.startTag("", "uri");
                    xmlSerializerNewSerializer.text(wallpaperUser.getUri().toString());
                    xmlSerializerNewSerializer.endTag("", "uri");
                }
                if (!TextUtils.isEmpty(wallpaperUser.getExternalParams())) {
                    xmlSerializerNewSerializer.startTag("", EXTERNAL_PARAMS);
                    xmlSerializerNewSerializer.text(wallpaperUser.getExternalParams());
                    xmlSerializerNewSerializer.endTag("", EXTERNAL_PARAMS);
                }
                if (!TextUtils.isEmpty(wallpaperUser.getComponentName())) {
                    xmlSerializerNewSerializer.startTag("", COMPONENT_NAME);
                    xmlSerializerNewSerializer.text(wallpaperUser.getComponentName());
                    xmlSerializerNewSerializer.endTag("", COMPONENT_NAME);
                }
                if (!TextUtils.isEmpty(wallpaperUser.getDeviceType()) && (wallpaperUser.getDeviceType() == "folder" || wallpaperUser.getDeviceType() == BnRConstants.DEVICETYPE_TABLET)) {
                    xmlSerializerNewSerializer.startTag("", "orientation");
                    xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getOrientation()));
                    xmlSerializerNewSerializer.endTag("", "orientation");
                }
                xmlSerializerNewSerializer.startTag("", "left");
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getLeftValue()));
                xmlSerializerNewSerializer.endTag("", "left");
                xmlSerializerNewSerializer.startTag("", TOP);
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getTopValue()));
                xmlSerializerNewSerializer.endTag("", TOP);
                xmlSerializerNewSerializer.startTag("", "right");
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getRightValue()));
                xmlSerializerNewSerializer.endTag("", "right");
                xmlSerializerNewSerializer.startTag("", BOTTOM);
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getBottomValue()));
                xmlSerializerNewSerializer.endTag("", BOTTOM);
                xmlSerializerNewSerializer.startTag("", ROTATION);
                xmlSerializerNewSerializer.text(Integer.toString(wallpaperUser.getRotationValue()));
                xmlSerializerNewSerializer.endTag("", ROTATION);
                xmlSerializerNewSerializer.endTag("", OBJECT_LIST_TAG);
                xmlSerializerNewSerializer.endDocument();
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e3) {
                e = e3;
                fileWriter = fileWriter;
                try {
                    e.printStackTrace();
                    if (fileWriter != null) {
                        fileWriter.flush();
                        fileWriter.close();
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                    th = th2;
                    if (fileWriter != null) {
                        try {
                            fileWriter.flush();
                            fileWriter.close();
                            throw th;
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                fileWriter = fileWriter;
                if (fileWriter != null) {
                }
            }
        } catch (IOException e5) {
            e = e5;
        } catch (Throwable th6) {
            th2 = th6;
            th = th2;
            if (fileWriter != null) {
            }
        }
    }
}
