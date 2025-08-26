package com.android.systemui.controls.util;

import android.content.ComponentName;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Xml;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes2.dex */
public final class ControlsFileLoader {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public static void generateBodyForControl(XmlSerializer xmlSerializer, ControlsBackupControl controlsBackupControl) throws IllegalStateException, IOException, IllegalArgumentException {
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

    public static void generateBodyForSetting(XmlSerializer xmlSerializer, ControlsBackupSetting controlsBackupSetting) throws IllegalStateException, IOException, IllegalArgumentException {
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

    public static File generateResultXML(File file, ControlsBackupFormat controlsBackupFormat) throws IOException {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("generateResultXml path = ", file.getPath(), "ControlsFileLoader");
        try {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                if (parentFile.exists()) {
                    parentFile = null;
                }
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("make file Exception : ", e, "ControlsFileLoader");
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            try {
                XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
                xmlSerializerNewSerializer.setOutput(fileWriter);
                xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
                xmlSerializerNewSerializer.startTag(null, "version");
                xmlSerializerNewSerializer.text("1");
                xmlSerializerNewSerializer.endTag(null, "version");
                generateBodyForSetting(xmlSerializerNewSerializer, controlsBackupFormat.setting);
                generateBodyForControl(xmlSerializerNewSerializer, controlsBackupFormat.controls);
                Log.d("ControlsFileLoader", "backup success");
                fileWriter.close();
                return file;
            } finally {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static ControlsBackupFormat loadResultXml(File file) {
        ControlsBackupFormat xml;
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
                        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                        xmlPullParserNewPullParser.setInput(bufferedInputStream, null);
                        xml = parseXml(xmlPullParserNewPullParser);
                    }
                    return xml;
                } finally {
                    IoUtils.closeQuietly(bufferedInputStream);
                }
            } catch (IOException e) {
                throw new IllegalStateException("Failed parsing backup file: " + file, e);
            } catch (XmlPullParserException e2) {
                throw new IllegalStateException("Failed parsing backup file: " + file, e2);
            }
        } catch (FileNotFoundException e3) {
            Log.i("ControlsFileLoader", "No file found e = " + e3);
            return null;
        }
    }

    public static ControlsBackupFormat parseXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        ControlsBackupSetting controlsBackupSetting = new ControlsBackupSetting(false, false, false, "");
        ArrayList arrayList2 = new ArrayList();
        ComponentName componentNameUnflattenFromString = null;
        String str = null;
        while (true) {
            boolean z = true;
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1) {
                    return new ControlsBackupFormat(controlsBackupSetting, new ControlsBackupControl(arrayList));
                }
                String name = xmlPullParser.getName();
                if (name == null) {
                    name = "";
                }
                if (next == 2 && name.equals("setting")) {
                    controlsBackupSetting.showDevice = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "setting_show_device"));
                    controlsBackupSetting.controlDevice = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "setting_control_device"));
                    controlsBackupSetting.isOOBECompleted = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "setting_oobe_completed"));
                    controlsBackupSetting.selectedComponent = xmlPullParser.getAttributeValue(null, "settings_selected_component");
                } else if (next == 2 && name.equals("structure")) {
                    componentNameUnflattenFromString = ComponentName.unflattenFromString(xmlPullParser.getAttributeValue(null, "component"));
                    String attributeValue = xmlPullParser.getAttributeValue(null, "structure");
                    str = attributeValue != null ? attributeValue : "";
                    String attributeValue2 = xmlPullParser.getAttributeValue(null, "sem_active");
                    if (attributeValue2 != null) {
                        z = Boolean.parseBoolean(attributeValue2);
                    }
                } else if (next == 2 && name.equals("control")) {
                    String attributeValue3 = xmlPullParser.getAttributeValue(null, "id");
                    String attributeValue4 = xmlPullParser.getAttributeValue(null, UniversalCredentialUtil.AGENT_TITLE);
                    String attributeValue5 = xmlPullParser.getAttributeValue(null, "subtitle");
                    String str2 = attributeValue5 == null ? "" : attributeValue5;
                    String attributeValue6 = xmlPullParser.getAttributeValue(null, "type");
                    Integer numValueOf = attributeValue6 != null ? Integer.valueOf(Integer.parseInt(attributeValue6)) : null;
                    if (attributeValue3 != null && attributeValue4 != null && numValueOf != null) {
                        ControlInfo controlInfo = new ControlInfo(attributeValue3, attributeValue4, str2, numValueOf.intValue(), 0, 16, null);
                        String attributeValue7 = xmlPullParser.getAttributeValue(null, "sem_layoutType");
                        if (attributeValue7 != null) {
                            controlInfo.layoutType = Integer.parseInt(attributeValue7);
                        }
                        arrayList2.add(controlInfo);
                    }
                } else if (next == 3 && name.equals("structure")) {
                    componentNameUnflattenFromString.getClass();
                    str.getClass();
                    StructureInfo structureInfo = new StructureInfo(componentNameUnflattenFromString, str, CollectionsKt___CollectionsKt.toList(arrayList2), false, 8, null);
                    structureInfo.active = z;
                    arrayList.add(structureInfo);
                    arrayList2.clear();
                }
            }
        }
    }
}
