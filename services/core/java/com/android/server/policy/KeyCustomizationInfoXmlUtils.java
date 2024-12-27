package com.android.server.policy;

import android.app.appsearch.AppSearchSession;
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
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0;

import libcore.io.IoUtils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.view.SemWindowManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KeyCustomizationInfoXmlUtils {
    public final KeyCustomizationInfoManager mKeyCustomizationInfoManager;
    public ErrorCode xmlFileErrorCode = ErrorCode.FAIL;
    public float mXmlVersion = -1.0f;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum ErrorCode {
        SUCCESS("SUCCESS"),
        FAIL("FAIL"),
        UNKNOWN_ERROR("UNKNOWN_ERROR"),
        FILE_NOT_FOUND("FILE_NOT_FOUND");

        private final int code;

        ErrorCode(String str) {
            this.code = r2;
        }
    }

    public KeyCustomizationInfoXmlUtils(KeyCustomizationInfoManager keyCustomizationInfoManager) {
        this.mKeyCustomizationInfoManager = keyCustomizationInfoManager;
    }

    public static int getAttributeInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return TextUtils.isEmpty(attributeValue) ? i : Integer.parseInt(attributeValue);
    }

    public static void parseHotKeysAttributes(
            XmlPullParser xmlPullParser, SparseArray sparseArray) {
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

    public final void addHotKeyInfo(XmlSerializer xmlSerializer) {
        FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
        fastXmlSerializer.startTag((String) null, "hot_key");
        SparseArray sparseArray = this.mKeyCustomizationInfoManager.mHotKeyMap;
        String str = KeyCustomizationConstants.VOLD_DECRYPT;
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                fastXmlSerializer.startTag((String) null, "key");
                int keyAt = sparseArray.keyAt(i);
                ComponentName componentName = (ComponentName) sparseArray.get(keyAt);
                fastXmlSerializer.attribute((String) null, "keyCode", Integer.toString(keyAt));
                if (componentName != null) {
                    fastXmlSerializer.attribute(
                            (String) null, "package_name", componentName.getPackageName());
                    fastXmlSerializer.attribute(
                            (String) null, "class_name", componentName.getClassName());
                }
                fastXmlSerializer.endTag((String) null, "key");
            }
        }
        fastXmlSerializer.endTag((String) null, "hot_key");
    }

    public final void addKeyInfo(XmlSerializer xmlSerializer) {
        int[] iArr;
        int i;
        int[] iArr2 = KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL;
        int length = iArr2.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr2[i2];
            SparseArray infoMapLocked = this.mKeyCustomizationInfoManager.getInfoMapLocked(i3);
            String str =
                    (i3 & 3) != 0
                            ? "press"
                            : (i3 & 4) != 0
                                    ? "long"
                                    : (i3 & 8) != 0
                                            ? "double"
                                            : (i3 & 16) != 0
                                                    ? "triple"
                                                    : (i3 & 32) != 0
                                                            ? "quadruple"
                                                            : (i3 & 64) != 0 ? "quintuple" : null;
            String str2 = KeyCustomizationConstants.VOLD_DECRYPT;
            FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
            fastXmlSerializer.startTag((String) null, str);
            for (int i4 = 0; i4 < infoMapLocked.size(); i4++) {
                SparseArray sparseArray = (SparseArray) infoMapLocked.valueAt(i4);
                int i5 = 0;
                while (i5 < sparseArray.size()) {
                    fastXmlSerializer.startTag((String) null, "key");
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                            (SemWindowManager.KeyCustomizationInfo) sparseArray.valueAt(i5);
                    if (keyCustomizationInfo == null) {
                        iArr = iArr2;
                        i = 1;
                    } else {
                        fastXmlSerializer.attribute(
                                (String) null,
                                "keyCode",
                                Integer.toString(keyCustomizationInfo.keyCode));
                        fastXmlSerializer.attribute(
                                (String) null,
                                "launchAction",
                                Integer.toString(keyCustomizationInfo.action));
                        int i6 = keyCustomizationInfo.dispatching;
                        if (i6 == -1) {
                            fastXmlSerializer.attribute(
                                    (String) null, "dispatching", Integer.toString(i6));
                        }
                        fastXmlSerializer.attribute(
                                (String) null, "id", Integer.toString(keyCustomizationInfo.id));
                        int i7 = keyCustomizationInfo.userId;
                        if (i7 != -2) {
                            fastXmlSerializer.attribute(
                                    (String) null, "userId", Integer.toString(i7));
                        }
                        iArr = iArr2;
                        long j = keyCustomizationInfo.longPressTimeout;
                        if (j != 0) {
                            fastXmlSerializer.attribute(
                                    (String) null, "longPressTimeoutMs", Long.toString(j));
                        }
                        long j2 = keyCustomizationInfo.multiPressTimeout;
                        if (j2 != 0) {
                            fastXmlSerializer.attribute(
                                    (String) null, "multiPressTimeoutMs", Long.toString(j2));
                        }
                        if (keyCustomizationInfo.id == 2003
                                && !TextUtils.isEmpty(keyCustomizationInfo.ownerPackage)) {
                            fastXmlSerializer.attribute(
                                    (String) null,
                                    "ownerPackage",
                                    keyCustomizationInfo.ownerPackage);
                        }
                        fastXmlSerializer.startTag((String) null, KnoxCustomManagerService.INTENT);
                        Intent intent = keyCustomizationInfo.intent;
                        if (intent != null) {
                            i = 1;
                            fastXmlSerializer.attribute((String) null, "action", intent.toUri(1));
                        } else {
                            i = 1;
                            fastXmlSerializer.attribute((String) null, "action", "null");
                        }
                        fastXmlSerializer.endTag((String) null, KnoxCustomManagerService.INTENT);
                        fastXmlSerializer.endTag((String) null, "key");
                    }
                    i5 += i;
                    iArr2 = iArr;
                }
            }
            fastXmlSerializer.endTag((String) null, str);
            i2++;
            iArr2 = iArr2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void loadSettingsLocked(int i) {
        BufferedReader bufferedReader;
        Exception e;
        int next;
        Slog.v("KeyCustomizationInfoXmlUtils", "loadSettingsLocked, userId=" + i);
        File file = new File(Environment.getUserSystemDirectory(i), "key_customize_info.xml");
        BufferedReader bufferedReader2 = null;
        AppSearchSession appSearchSession = 0;
        try {
        } catch (Throwable th) {
            th = th;
            appSearchSession = "key_customize_info.xml";
        }
        try {
            try {
                bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(file), StandardCharsets.UTF_8));
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(bufferedReader);
                    do {
                        next = newPullParser.next();
                        if (next == 2) {
                            String name = newPullParser.getName();
                            if ("keycustomize_info_version".equals(name)) {
                                String attributeValue =
                                        newPullParser.getAttributeValue(null, "version");
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
                                SparseArray sparseArray =
                                        this.mKeyCustomizationInfoManager.mHotKeyMap;
                                sparseArray.clear();
                                parseHotKeysAttributes(newPullParser, sparseArray);
                            }
                        }
                    } while (next != 1);
                    this.xmlFileErrorCode = ErrorCode.SUCCESS;
                } catch (FileNotFoundException unused) {
                    bufferedReader2 = bufferedReader;
                    Slog.d("KeyCustomizationInfoXmlUtils", "File not found " + file);
                    this.xmlFileErrorCode = ErrorCode.FILE_NOT_FOUND;
                    IoUtils.closeQuietly(bufferedReader2);
                    return;
                } catch (Exception e2) {
                    e = e2;
                    Slog.wtf(
                            "KeyCustomizationInfoXmlUtils",
                            "Unable to parse " + file + ". Error ",
                            e);
                    this.xmlFileErrorCode = ErrorCode.UNKNOWN_ERROR;
                    IoUtils.closeQuietly(bufferedReader);
                }
            } catch (FileNotFoundException unused2) {
            } catch (Exception e3) {
                bufferedReader = null;
                e = e3;
            }
            IoUtils.closeQuietly(bufferedReader);
        } catch (Throwable th2) {
            th = th2;
            IoUtils.closeQuietly(appSearchSession);
            throw th;
        }
    }

    public final void parseKeyCustomizationInfoByPress(XmlPullParser xmlPullParser, int i) {
        Intent intent;
        KeyCustomizationInfoManager keyCustomizationInfoManager = this.mKeyCustomizationInfoManager;
        SparseArray infoMapLocked = keyCustomizationInfoManager.getInfoMapLocked(i);
        infoMapLocked.clear();
        String name = xmlPullParser.getName();
        int eventType = xmlPullParser.getEventType();
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = null;
        boolean z = false;
        do {
            String name2 = xmlPullParser.getName();
            if (eventType != 2) {
                if (eventType == 3) {
                    if (keyCustomizationInfo != null && "key".equals(name2)) {
                        if (infoMapLocked.get(keyCustomizationInfo.keyCode) == null) {
                            infoMapLocked.put(keyCustomizationInfo.keyCode, new SparseArray());
                        }
                        ((SparseArray) infoMapLocked.get(keyCustomizationInfo.keyCode))
                                .put(keyCustomizationInfo.id, keyCustomizationInfo);
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
                keyCustomizationInfo.longPressTimeout =
                        getAttributeInt(xmlPullParser, "longPressTimeoutMs", 0);
                keyCustomizationInfo.multiPressTimeout =
                        getAttributeInt(xmlPullParser, "multiPressTimeoutMs", 0);
                if (keyCustomizationInfo.id == 1102) {
                    keyCustomizationInfo.id = 951;
                }
                if (keyCustomizationInfo.id == 2003) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "ownerPackage");
                    if (!TextUtils.isEmpty(attributeValue)) {
                        keyCustomizationInfo.ownerPackage = attributeValue;
                        keyCustomizationInfoManager.addOwnerPackageList(attributeValue);
                    }
                }
                keyCustomizationInfo.press = i;
            } else if (KnoxCustomManagerService.INTENT.equals(name2)
                    && keyCustomizationInfo != null) {
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "action");
                if (TextUtils.isEmpty(attributeValue2) || attributeValue2.equals("null")) {
                    Slog.d(
                            "KeyCustomizationInfoXmlUtils",
                            "restoreFromXml intent info is empty or null");
                    intent = null;
                } else {
                    try {
                        String str = KeyCustomizationConstants.VOLD_DECRYPT;
                        intent = Intent.parseUri(attributeValue2, 1);
                    } catch (Exception e) {
                        Log.e("KeyCustomizationInfoXmlUtils", "restoreFromXml failed ", e);
                        intent = new Intent();
                    }
                }
                keyCustomizationInfo.intent = intent;
                if (intent != null
                        && keyCustomizationInfo.id == 2003
                        && TextUtils.isEmpty(keyCustomizationInfo.ownerPackage)) {
                    String packageName =
                            intent.getComponent() != null
                                    ? intent.getComponent().getPackageName()
                                    : null;
                    if (TextUtils.isEmpty(packageName)) {
                        packageName = intent.getPackage();
                    }
                    if (!TextUtils.isEmpty(packageName)) {
                        keyCustomizationInfo.ownerPackage = packageName;
                        keyCustomizationInfoManager.addOwnerPackageList(packageName);
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

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0086 -> B:9:0x00ac). Please report as a decompilation issue!!! */
    public final void saveSettingsLocked(int i) {
        Slog.d(
                "KeyCustomizationInfoXmlUtils",
                "saveSettingsLocked, Callers=" + Debug.getCallers(5));
        StringWriter stringWriter = new StringWriter();
        FileOutputStream fileOutputStream = null;
        try {
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(stringWriter);
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            fastXmlSerializer.startTag((String) null, "keycustomize_info_version");
            fastXmlSerializer.attribute((String) null, "version", Float.toString(4.1f));
            fastXmlSerializer.endTag((String) null, "keycustomize_info_version");
            addKeyInfo(fastXmlSerializer);
            addHotKeyInfo(fastXmlSerializer);
            fastXmlSerializer.endDocument();
            fastXmlSerializer.flush();
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e, "failed saveSettings ", "KeyCustomizationInfoXmlUtils");
        }
        AtomicFile atomicFile =
                new AtomicFile(
                        new File(Environment.getUserSystemDirectory(i), "key_customize_info.xml"));
        try {
            try {
                try {
                    fileOutputStream = atomicFile.startWrite();
                    fileOutputStream.write(
                            stringWriter.toString().getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write(10);
                    atomicFile.finishWrite(fileOutputStream);
                    fileOutputStream.close();
                } catch (IOException e2) {
                    atomicFile.failWrite(fileOutputStream);
                    Slog.e(
                            "KeyCustomizationInfoXmlUtils",
                            "Unable to open " + atomicFile + " for persisting. " + e2);
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
            } catch (IOException e3) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "Unable to close.", e3, "KeyCustomizationInfoXmlUtils");
            }
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e4) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            "Unable to close.", e4, "KeyCustomizationInfoXmlUtils");
                }
            }
            throw th;
        }
    }
}
