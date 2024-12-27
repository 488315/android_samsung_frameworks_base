package com.android.systemui.controls.util;

import android.content.ComponentName;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Xml;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.systemui.backup.BackupHelper;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.StructureInfo;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class ControlsFileLoader {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public static void generateBodyForControl(XmlSerializer xmlSerializer, ControlsBackupControl controlsBackupControl) {
        xmlSerializer.startTag(null, "structures");
        for (StructureInfo structureInfo : controlsBackupControl.structures) {
            xmlSerializer.startTag(null, "structure");
            xmlSerializer.attribute(null, "component", structureInfo.componentName.flattenToString());
            xmlSerializer.attribute(null, "structure", structureInfo.structure.toString());
            xmlSerializer.attribute(null, "sem_active", String.valueOf(structureInfo.active));
            xmlSerializer.startTag(null, "controls");
            for (ControlInfo controlInfo : structureInfo.controls) {
                xmlSerializer.startTag(null, "control");
                xmlSerializer.attribute(null, "id", controlInfo.controlId);
                xmlSerializer.attribute(null, UniversalCredentialUtil.AGENT_TITLE, controlInfo.controlTitle.toString());
                xmlSerializer.attribute(null, "subtitle", controlInfo.controlSubtitle.toString());
                xmlSerializer.attribute(null, "type", String.valueOf(controlInfo.deviceType));
                xmlSerializer.attribute(null, "sem_layoutType", String.valueOf(controlInfo.layoutType));
                xmlSerializer.endTag(null, "control");
            }
            xmlSerializer.endTag(null, "controls");
            xmlSerializer.endTag(null, "structure");
        }
        xmlSerializer.endTag(null, "structures");
        xmlSerializer.endDocument();
    }

    public static void generateBodyForSetting(XmlSerializer xmlSerializer, ControlsBackupSetting controlsBackupSetting) {
        xmlSerializer.startTag(null, "setting");
        xmlSerializer.attribute(null, "setting_show_device", String.valueOf(controlsBackupSetting.showDevice));
        xmlSerializer.attribute(null, "setting_control_device", String.valueOf(controlsBackupSetting.controlDevice));
        xmlSerializer.attribute(null, "setting_oobe_completed", String.valueOf(controlsBackupSetting.isOOBECompleted));
        String str = controlsBackupSetting.selectedComponent;
        if (str == null) {
            str = "";
        }
        xmlSerializer.attribute(null, "settings_selected_component", str);
        xmlSerializer.endTag(null, "setting");
    }

    public static File generateResultXML(File file, ControlsBackupFormat controlsBackupFormat) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("generateResultXml path = ", file.getPath(), "ControlsFileLoader");
        try {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (!(!parentFile.exists())) {
                    parentFile = null;
                }
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            try {
                FileWriter fileWriter = new FileWriter(file);
                try {
                    XmlSerializer newSerializer = Xml.newSerializer();
                    newSerializer.setOutput(fileWriter);
                    newSerializer.startDocument("UTF-8", Boolean.TRUE);
                    newSerializer.startTag(null, "version");
                    newSerializer.text("1");
                    newSerializer.endTag(null, "version");
                    generateBodyForSetting(newSerializer, controlsBackupFormat.setting);
                    generateBodyForControl(newSerializer, controlsBackupFormat.controls);
                    Log.d("ControlsFileLoader", "backup success");
                    CloseableKt.closeFinally(fileWriter, null);
                    return file;
                } finally {
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m("make file Exception : ", e2, "ControlsFileLoader");
            return null;
        }
    }

    public static ControlsBackupFormat loadResultXml(File file) {
        ControlsBackupFormat parseXml;
        if (!file.exists()) {
            Log.d("ControlsFileLoader", "No backup file, returning null");
            return null;
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                try {
                    Log.d("ControlsFileLoader", "Reading data from file = " + file);
                    BackupHelper.Companion.getClass();
                    synchronized (BackupHelper.controlsDataLock) {
                        XmlPullParser newPullParser = Xml.newPullParser();
                        newPullParser.setInput(bufferedInputStream, null);
                        parseXml = parseXml(newPullParser);
                    }
                    return parseXml;
                } catch (IOException e) {
                    throw new IllegalStateException("Failed parsing backup file: " + file, e);
                } catch (XmlPullParserException e2) {
                    throw new IllegalStateException("Failed parsing backup file: " + file, e2);
                }
            } finally {
                IoUtils.closeQuietly(bufferedInputStream);
            }
        } catch (FileNotFoundException e3) {
            Log.i("ControlsFileLoader", "No file found e = " + e3);
            return null;
        }
    }

    public static ControlsBackupFormat parseXml(XmlPullParser xmlPullParser) {
        int i;
        ArrayList arrayList = new ArrayList();
        ControlsBackupSetting controlsBackupSetting = new ControlsBackupSetting(false, false, false, "");
        ArrayList arrayList2 = new ArrayList();
        String str = null;
        int i2 = 1;
        ComponentName componentName = null;
        String str2 = null;
        while (true) {
            int i3 = i2;
            while (true) {
                int next = xmlPullParser.next();
                if (next == i2) {
                    return new ControlsBackupFormat(controlsBackupSetting, new ControlsBackupControl(arrayList));
                }
                String name = xmlPullParser.getName();
                if (name == null) {
                    name = "";
                }
                if (next == 2 && name.equals("setting")) {
                    controlsBackupSetting.showDevice = Boolean.parseBoolean(xmlPullParser.getAttributeValue(str, "setting_show_device"));
                    controlsBackupSetting.controlDevice = Boolean.parseBoolean(xmlPullParser.getAttributeValue(str, "setting_control_device"));
                    controlsBackupSetting.isOOBECompleted = Boolean.parseBoolean(xmlPullParser.getAttributeValue(str, "setting_oobe_completed"));
                    controlsBackupSetting.selectedComponent = xmlPullParser.getAttributeValue(str, "settings_selected_component");
                } else if (next == 2 && name.equals("structure")) {
                    componentName = ComponentName.unflattenFromString(xmlPullParser.getAttributeValue(str, "component"));
                    String attributeValue = xmlPullParser.getAttributeValue(str, "structure");
                    str2 = attributeValue != null ? attributeValue : "";
                    String attributeValue2 = xmlPullParser.getAttributeValue(str, "sem_active");
                    if (attributeValue2 != null) {
                        i3 = Boolean.parseBoolean(attributeValue2) ? 1 : 0;
                    }
                } else if (next == 2 && name.equals("control")) {
                    String attributeValue3 = xmlPullParser.getAttributeValue(str, "id");
                    String attributeValue4 = xmlPullParser.getAttributeValue(str, UniversalCredentialUtil.AGENT_TITLE);
                    String attributeValue5 = xmlPullParser.getAttributeValue(str, "subtitle");
                    String str3 = attributeValue5 == null ? "" : attributeValue5;
                    String attributeValue6 = xmlPullParser.getAttributeValue(str, "type");
                    Integer valueOf = attributeValue6 != null ? Integer.valueOf(Integer.parseInt(attributeValue6)) : str;
                    if (attributeValue3 != null && attributeValue4 != null && valueOf != 0) {
                        ControlInfo controlInfo = new ControlInfo(attributeValue3, attributeValue4, str3, valueOf.intValue(), 0, 16, null);
                        String attributeValue7 = xmlPullParser.getAttributeValue(str, "sem_layoutType");
                        if (attributeValue7 != null) {
                            controlInfo.layoutType = Integer.parseInt(attributeValue7);
                        }
                        arrayList2.add(controlInfo);
                    }
                } else if (next == 3 && name.equals("structure")) {
                    Intrinsics.checkNotNull(componentName);
                    Intrinsics.checkNotNull(str2);
                    boolean z = i3;
                    StructureInfo structureInfo = new StructureInfo(componentName, str2, CollectionsKt___CollectionsKt.toList(arrayList2), false, 8, null);
                    structureInfo.active = z;
                    arrayList.add(structureInfo);
                    arrayList2.clear();
                    i = z;
                    i3 = i;
                    str = null;
                    i2 = 1;
                }
                i = i3;
                i3 = i;
                str = null;
                i2 = 1;
            }
        }
    }
}
