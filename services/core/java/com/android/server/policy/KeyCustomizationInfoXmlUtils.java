package com.android.server.policy;

import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Debug;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.FastXmlSerializer;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.view.SemWindowManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public class KeyCustomizationInfoXmlUtils {
    public final KeyCustomizationInfoManager mKeyCustomizationInfoManager;
    public ErrorCode xmlFileErrorCode = ErrorCode.FAIL;
    public float mXmlVersion = -1.0f;

    enum ErrorCode {
        SUCCESS(0),
        FAIL(1),
        UNKNOWN_ERROR(2),
        FILE_NOT_FOUND(3);

        private final int code;

        ErrorCode(int i) {
            this.code = i;
        }
    }

    public KeyCustomizationInfoXmlUtils(KeyCustomizationInfoManager keyCustomizationInfoManager) {
        this.mKeyCustomizationInfoManager = keyCustomizationInfoManager;
    }

    public void initXmlVersion() {
        this.mXmlVersion = 3.1f;
    }

    public boolean updateXmlVersionIfNeeded() {
        if (Float.compare(this.mXmlVersion, 3.1f) == 0) {
            if (!KeyCustomizationConstants.isSafeDebugInput()) {
                return false;
            }
            Log.d("KeyCustomizationInfoXmlUtils", "the version has same with latest.");
            return false;
        }
        Log.d("KeyCustomizationInfoXmlUtils", "updateXmlVersion old=" + this.mXmlVersion + " new=3.1");
        this.mXmlVersion = 3.1f;
        return true;
    }

    public float getXmlVersion() {
        return this.mXmlVersion;
    }

    public void saveSettingsLocked(int i) {
        Slog.d("KeyCustomizationInfoXmlUtils", "saveSettingsLocked, Callers=" + Debug.getCallers(5));
        StringWriter stringWriter = new StringWriter();
        try {
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(stringWriter);
            fastXmlSerializer.startDocument(null, Boolean.TRUE);
            fastXmlSerializer.startTag(null, "keycustomize_info_version");
            fastXmlSerializer.attribute(null, "version", Float.toString(3.1f));
            fastXmlSerializer.endTag(null, "keycustomize_info_version");
            for (int i2 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
                writeKeyCustomizationAttributes(fastXmlSerializer, (i2 & 3) != 0 ? "press" : (i2 & 4) != 0 ? "long" : (i2 & 8) != 0 ? "double" : (i2 & 16) != 0 ? "triple" : (i2 & 32) != 0 ? "quadruple" : (i2 & 64) != 0 ? "quintuple" : null, this.mKeyCustomizationInfoManager.getInfoMapLocked(i2));
            }
            fastXmlSerializer.startTag(null, "hot_key");
            writeHotKeysAttributes(fastXmlSerializer, this.mKeyCustomizationInfoManager.getHotKeyMapLocked());
            fastXmlSerializer.endTag(null, "hot_key");
            fastXmlSerializer.endDocument();
            fastXmlSerializer.flush();
        } catch (IOException | IllegalArgumentException | IllegalStateException e) {
            Slog.w("KeyCustomizationInfoXmlUtils", "failed saveSettings " + e);
        }
        writeOutXmlFileLocked(stringWriter, i);
    }

    public final void writeOutXmlFileLocked(StringWriter stringWriter, int i) {
        StringBuilder sb;
        AtomicFile atomicFile = new AtomicFile(new File(getKeyCustomizationDir(i), "key_customize_info.xml"));
        FileOutputStream fileOutputStream = null;
        try {
            try {
                fileOutputStream = atomicFile.startWrite();
                fileOutputStream.write(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
                fileOutputStream.write(10);
                atomicFile.finishWrite(fileOutputStream);
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e = e;
                    sb = new StringBuilder();
                    sb.append("Unable to close.");
                    sb.append(e);
                    Slog.w("KeyCustomizationInfoXmlUtils", sb.toString());
                }
            } catch (IOException e2) {
                atomicFile.failWrite(fileOutputStream);
                Slog.e("KeyCustomizationInfoXmlUtils", "Unable to open " + atomicFile + " for persisting. " + e2);
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        e = e3;
                        sb = new StringBuilder();
                        sb.append("Unable to close.");
                        sb.append(e);
                        Slog.w("KeyCustomizationInfoXmlUtils", sb.toString());
                    }
                }
            }
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e4) {
                    Slog.w("KeyCustomizationInfoXmlUtils", "Unable to close." + e4);
                }
            }
            throw th;
        }
    }

    public final void writeKeyCustomizationAttributes(XmlSerializer xmlSerializer, String str, SparseArray sparseArray) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.v("KeyCustomizationInfoXmlUtils", "writeKeyCustomizationAttributes. " + str);
        }
        xmlSerializer.startTag(null, str);
        for (int i = 0; i < sparseArray.size(); i++) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.valueAt(i);
            for (int i2 = 0; i2 < sparseArray2.size(); i2++) {
                xmlSerializer.startTag(null, "key");
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = (SemWindowManager.KeyCustomizationInfo) sparseArray2.valueAt(i2);
                if (keyCustomizationInfo != null) {
                    xmlSerializer.attribute(null, "keyCode", Integer.toString(keyCustomizationInfo.keyCode));
                    xmlSerializer.attribute(null, "launchAction", Integer.toString(keyCustomizationInfo.action));
                    int i3 = keyCustomizationInfo.dispatching;
                    if (i3 == -1) {
                        xmlSerializer.attribute(null, "dispatching", Integer.toString(i3));
                    }
                    xmlSerializer.attribute(null, "id", Integer.toString(keyCustomizationInfo.id));
                    int i4 = keyCustomizationInfo.userId;
                    if (i4 != -2) {
                        xmlSerializer.attribute(null, "userId", Integer.toString(i4));
                    }
                    long j = keyCustomizationInfo.longPressTimeout;
                    if (j != 0) {
                        xmlSerializer.attribute(null, "longPressTimeoutMs", Long.toString(j));
                    }
                    long j2 = keyCustomizationInfo.multiPressTimeout;
                    if (j2 != 0) {
                        xmlSerializer.attribute(null, "multiPressTimeoutMs", Long.toString(j2));
                    }
                    if (keyCustomizationInfo.id == 2003 && !TextUtils.isEmpty(keyCustomizationInfo.ownerPackage)) {
                        xmlSerializer.attribute(null, "ownerPackage", keyCustomizationInfo.ownerPackage);
                    }
                    xmlSerializer.startTag(null, KnoxCustomManagerService.INTENT);
                    Intent intent = keyCustomizationInfo.intent;
                    if (intent != null) {
                        xmlSerializer.attribute(null, "action", intent.toUri(1));
                    } else {
                        xmlSerializer.attribute(null, "action", "null");
                    }
                    xmlSerializer.endTag(null, KnoxCustomManagerService.INTENT);
                    xmlSerializer.endTag(null, "key");
                }
            }
        }
        xmlSerializer.endTag(null, str);
    }

    public static File getKeyCustomizationDir(int i) {
        return Environment.getUserSystemDirectory(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void loadSettingsLocked(int i) {
        BufferedReader bufferedReader;
        Exception e;
        int next;
        Slog.v("KeyCustomizationInfoXmlUtils", "loadSettingsLocked, userId=" + i);
        File file = new File(getKeyCustomizationDir(i), "key_customize_info.xml");
        BufferedReader bufferedReader2 = null;
        BluetoothSocket bluetoothSocket = 0;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                } catch (Throwable th) {
                    th = th;
                    IoUtils.closeQuietly(bluetoothSocket);
                    throw th;
                }
            } catch (FileNotFoundException unused) {
            } catch (Exception e2) {
                bufferedReader = null;
                e = e2;
            }
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(bufferedReader);
                do {
                    next = newPullParser.next();
                    if (next == 2) {
                        String name = newPullParser.getName();
                        if ("keycustomize_info_version".equals(name)) {
                            String attributeValue = newPullParser.getAttributeValue(null, "version");
                            if (!TextUtils.isEmpty(attributeValue)) {
                                this.mXmlVersion = Float.parseFloat(attributeValue);
                            }
                        } else if ("press".equals(name)) {
                            parseKeyCustomizationInfoByPress(newPullParser, 3);
                        } else if ("long".equals(name)) {
                            parseKeyCustomizationInfoByPress(newPullParser, 4);
                        } else if ("double".equals(name)) {
                            parseKeyCustomizationInfoByPress(newPullParser, 8);
                        } else if ("triple".equals(name)) {
                            parseKeyCustomizationInfoByPress(newPullParser, 16);
                        } else if ("quadruple".equals(name)) {
                            parseKeyCustomizationInfoByPress(newPullParser, 32);
                        } else if ("quintuple".equals(name)) {
                            parseKeyCustomizationInfoByPress(newPullParser, 64);
                        } else if ("hot_key".equals(name)) {
                            SparseArray hotKeyMapLocked = this.mKeyCustomizationInfoManager.getHotKeyMapLocked();
                            hotKeyMapLocked.clear();
                            parseHotKeysAttributes(newPullParser, hotKeyMapLocked);
                        }
                    }
                } while (next != 1);
                this.xmlFileErrorCode = ErrorCode.SUCCESS;
            } catch (FileNotFoundException unused2) {
                bufferedReader2 = bufferedReader;
                Slog.d("KeyCustomizationInfoXmlUtils", "File not found " + file);
                this.xmlFileErrorCode = ErrorCode.FILE_NOT_FOUND;
                IoUtils.closeQuietly(bufferedReader2);
                return;
            } catch (Exception e3) {
                e = e3;
                Slog.wtf("KeyCustomizationInfoXmlUtils", "Unable to parse " + file + ". Error ", e);
                this.xmlFileErrorCode = ErrorCode.UNKNOWN_ERROR;
                IoUtils.closeQuietly(bufferedReader);
            }
            IoUtils.closeQuietly(bufferedReader);
        } catch (Throwable th2) {
            th = th2;
            bluetoothSocket = "key_customize_info.xml";
        }
    }

    public final void parseKeyCustomizationInfoByPress(XmlPullParser xmlPullParser, int i) {
        SparseArray infoMapLocked = this.mKeyCustomizationInfoManager.getInfoMapLocked(i);
        infoMapLocked.clear();
        parseKeyCustomizationAttributes(xmlPullParser, infoMapLocked, i);
    }

    public final void parseKeyCustomizationAttributes(XmlPullParser xmlPullParser, SparseArray sparseArray, int i) {
        String name = xmlPullParser.getName();
        int eventType = xmlPullParser.getEventType();
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = null;
        boolean z = false;
        do {
            String name2 = xmlPullParser.getName();
            if (eventType != 2) {
                if (eventType == 3) {
                    if (keyCustomizationInfo != null && "key".equals(name2)) {
                        if (sparseArray.get(keyCustomizationInfo.keyCode) == null) {
                            sparseArray.put(keyCustomizationInfo.keyCode, new SparseArray());
                        }
                        ((SparseArray) sparseArray.get(keyCustomizationInfo.keyCode)).put(keyCustomizationInfo.id, keyCustomizationInfo);
                    }
                    if (name != null && name.equals(name2)) {
                        z = true;
                    }
                }
            } else if ("key".equals(name2)) {
                keyCustomizationInfo = new SemWindowManager.KeyCustomizationInfo();
                keyCustomizationInfo.keyCode = getAttributeInt(xmlPullParser, "keyCode", 0);
                keyCustomizationInfo.action = getAttributeInt(xmlPullParser, "launchAction", -1);
                keyCustomizationInfo.dispatching = getAttributeInt(xmlPullParser, "dispatching", 0);
                keyCustomizationInfo.id = getAttributeInt(xmlPullParser, "id", -1);
                keyCustomizationInfo.userId = getAttributeInt(xmlPullParser, "userId", -2);
                keyCustomizationInfo.longPressTimeout = getAttributeInt(xmlPullParser, "longPressTimeoutMs", 0);
                keyCustomizationInfo.multiPressTimeout = getAttributeInt(xmlPullParser, "multiPressTimeoutMs", 0);
                if (keyCustomizationInfo.id == 1102) {
                    keyCustomizationInfo.id = 951;
                }
                if (keyCustomizationInfo.id == 2003) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "ownerPackage");
                    if (!TextUtils.isEmpty(attributeValue)) {
                        keyCustomizationInfo.ownerPackage = attributeValue;
                        this.mKeyCustomizationInfoManager.addOwnerPackageList(attributeValue);
                    }
                }
                keyCustomizationInfo.press = i;
            } else if (KnoxCustomManagerService.INTENT.equals(name2) && keyCustomizationInfo != null) {
                Intent restoreFromXml = restoreFromXml(xmlPullParser);
                keyCustomizationInfo.intent = restoreFromXml;
                if (restoreFromXml != null && keyCustomizationInfo.id == 2003 && TextUtils.isEmpty(keyCustomizationInfo.ownerPackage)) {
                    String packageName = restoreFromXml.getComponent() != null ? restoreFromXml.getComponent().getPackageName() : null;
                    if (TextUtils.isEmpty(packageName)) {
                        packageName = restoreFromXml.getPackage();
                    }
                    if (!TextUtils.isEmpty(packageName)) {
                        keyCustomizationInfo.ownerPackage = packageName;
                        this.mKeyCustomizationInfoManager.addOwnerPackageList(packageName);
                    }
                }
            }
            if (!z) {
                eventType = xmlPullParser.next();
            }
            if (eventType == 1) {
                return;
            }
        } while (!z);
    }

    public final Intent restoreFromXml(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "action");
        if (TextUtils.isEmpty(attributeValue) || attributeValue.equals("null")) {
            Slog.d("KeyCustomizationInfoXmlUtils", "restoreFromXml intent info is empty or null");
            return null;
        }
        try {
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Slog.d("KeyCustomizationInfoXmlUtils", "restoreFromXml intent info is uri type. uri=" + attributeValue);
            }
            return Intent.parseUri(attributeValue, 1);
        } catch (Exception e) {
            Log.e("KeyCustomizationInfoXmlUtils", "restoreFromXml failed ", e);
            return new Intent();
        }
    }

    public final int getAttributeInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return TextUtils.isEmpty(attributeValue) ? i : Integer.parseInt(attributeValue);
    }

    public ErrorCode getXmlFileErrorCode() {
        return this.xmlFileErrorCode;
    }

    public final void writeHotKeysAttributes(XmlSerializer xmlSerializer, SparseArray sparseArray) {
        if (KeyCustomizationConstants.isSafeDebugInput()) {
            Slog.v("KeyCustomizationInfoXmlUtils", "writeHotKeysAttributes.");
        }
        if (sparseArray == null) {
            return;
        }
        for (int i = 0; i < sparseArray.size(); i++) {
            xmlSerializer.startTag(null, "key");
            int keyAt = sparseArray.keyAt(i);
            ComponentName componentName = (ComponentName) sparseArray.get(keyAt);
            xmlSerializer.attribute(null, "keyCode", Integer.toString(keyAt));
            if (componentName != null) {
                xmlSerializer.attribute(null, "package_name", componentName.getPackageName());
                xmlSerializer.attribute(null, "class_name", componentName.getClassName());
            }
            xmlSerializer.endTag(null, "key");
        }
    }

    public final void parseHotKeysAttributes(XmlPullParser xmlPullParser, SparseArray sparseArray) {
        String name = xmlPullParser.getName();
        int eventType = xmlPullParser.getEventType();
        int i = 0;
        boolean z = false;
        String str = null;
        String str2 = null;
        do {
            String name2 = xmlPullParser.getName();
            if (eventType != 2) {
                if (eventType == 3) {
                    if (i != 0 && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        sparseArray.put(i, new ComponentName(str, str2));
                    }
                    if (name != null && name.equals(name2)) {
                        z = true;
                    }
                }
            } else if ("key".equals(name2)) {
                i = getAttributeInt(xmlPullParser, "keyCode", 0);
                str = xmlPullParser.getAttributeValue(null, "package_name");
                str2 = xmlPullParser.getAttributeValue(null, "class_name");
            }
            if (!z) {
                eventType = xmlPullParser.next();
            }
            if (eventType == 1) {
                return;
            }
        } while (!z);
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("XmlFileErrorCode=");
        printWriter.println(getXmlFileErrorCode());
    }
}
