package com.android.systemui.controls.util;

import android.content.ComponentName;
import android.util.Xml;
import com.android.systemui.BasicRune;
import com.android.systemui.backup.BackupHelper;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.util.ControlsLogger;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p055io.CloseableKt;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsFileLoader {
    public final ControlsLogger controlsLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public ControlsFileLoader(ControlsLogger controlsLogger) {
        this.controlsLogger = controlsLogger;
    }

    public static void generateBodyForControl(XmlSerializer xmlSerializer, ControlsBackupControl controlsBackupControl) {
        xmlSerializer.startTag(null, "structures");
        for (StructureInfo structureInfo : controlsBackupControl.structures) {
            xmlSerializer.startTag(null, "structure");
            xmlSerializer.attribute(null, "component", structureInfo.componentName.flattenToString());
            xmlSerializer.attribute(null, "structure", structureInfo.structure.toString());
            xmlSerializer.attribute(null, "sem_active", String.valueOf(structureInfo.customStructureInfo.active));
            xmlSerializer.startTag(null, "controls");
            for (ControlInfo controlInfo : structureInfo.controls) {
                xmlSerializer.startTag(null, "control");
                xmlSerializer.attribute(null, "id", controlInfo.controlId);
                xmlSerializer.attribute(null, UniversalCredentialUtil.AGENT_TITLE, controlInfo.controlTitle.toString());
                xmlSerializer.attribute(null, "subtitle", controlInfo.controlSubtitle.toString());
                xmlSerializer.attribute(null, "type", String.valueOf(controlInfo.deviceType));
                if (BasicRune.CONTROLS_LAYOUT_TYPE) {
                    xmlSerializer.attribute(null, "sem_layoutType", String.valueOf(controlInfo.customControlInfo.layoutType));
                }
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

    public static ControlsBackupFormat parseXml(XmlPullParser xmlPullParser) {
        ArrayList arrayList = new ArrayList();
        ControlsBackupSetting controlsBackupSetting = new ControlsBackupSetting(false, false, false, "");
        ArrayList arrayList2 = new ArrayList();
        ComponentName componentName = null;
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
                if (next == 2 && Intrinsics.areEqual(name, "setting")) {
                    controlsBackupSetting.showDevice = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "setting_show_device"));
                    controlsBackupSetting.controlDevice = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "setting_control_device"));
                    controlsBackupSetting.isOOBECompleted = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "setting_oobe_completed"));
                    controlsBackupSetting.selectedComponent = xmlPullParser.getAttributeValue(null, "settings_selected_component");
                } else if (next == 2 && Intrinsics.areEqual(name, "structure")) {
                    componentName = ComponentName.unflattenFromString(xmlPullParser.getAttributeValue(null, "component"));
                    str = xmlPullParser.getAttributeValue(null, "structure");
                    if (str == null) {
                        str = "";
                    }
                    String attributeValue = xmlPullParser.getAttributeValue(null, "sem_active");
                    if (attributeValue != null) {
                        z = Boolean.parseBoolean(attributeValue);
                    }
                } else if (next == 2 && Intrinsics.areEqual(name, "control")) {
                    String attributeValue2 = xmlPullParser.getAttributeValue(null, "id");
                    String attributeValue3 = xmlPullParser.getAttributeValue(null, UniversalCredentialUtil.AGENT_TITLE);
                    String attributeValue4 = xmlPullParser.getAttributeValue(null, "subtitle");
                    if (attributeValue4 == null) {
                        attributeValue4 = "";
                    }
                    String attributeValue5 = xmlPullParser.getAttributeValue(null, "type");
                    Integer valueOf = attributeValue5 != null ? Integer.valueOf(Integer.parseInt(attributeValue5)) : null;
                    if (attributeValue2 != null && attributeValue3 != null && valueOf != null) {
                        ControlInfo controlInfo = new ControlInfo(attributeValue2, attributeValue3, attributeValue4, valueOf.intValue());
                        String attributeValue6 = xmlPullParser.getAttributeValue(null, "sem_layoutType");
                        if (attributeValue6 != null) {
                            Integer valueOf2 = Integer.valueOf(Integer.parseInt(attributeValue6));
                            valueOf2.intValue();
                            if (!BasicRune.CONTROLS_LAYOUT_TYPE) {
                                valueOf2 = null;
                            }
                            if (valueOf2 != null) {
                                controlInfo.customControlInfo.layoutType = valueOf2.intValue();
                            }
                        }
                        arrayList2.add(controlInfo);
                    }
                } else if (next == 3 && Intrinsics.areEqual(name, "structure")) {
                    Intrinsics.checkNotNull(componentName);
                    Intrinsics.checkNotNull(str);
                    StructureInfo structureInfo = new StructureInfo(componentName, str, CollectionsKt___CollectionsKt.toList(arrayList2));
                    structureInfo.customStructureInfo.active = z;
                    arrayList.add(structureInfo);
                    arrayList2.clear();
                }
            }
        }
    }

    public final File generateResultXML(File file, ControlsBackupFormat controlsBackupFormat) {
        boolean z;
        String str = "generateResultXml path=" + file.getPath();
        ControlsLogger controlsLogger = this.controlsLogger;
        ControlsLogger.printLog$default(controlsLogger, "ControlsFileLoader", str);
        try {
            File parentFile = file.getParentFile();
            z = true;
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
        } catch (Exception e) {
            ControlsLogger.printLog$default(controlsLogger, "ControlsFileLoader", "make file Exception: " + e);
            z = false;
        }
        if (!z) {
            return null;
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
                ControlsLogger.printLog$default(controlsLogger, "ControlsFileLoader", "backup success");
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileWriter, null);
                return file;
            } finally {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final ControlsBackupFormat loadResultXml(File file) {
        ControlsBackupFormat parseXml;
        if (!file.exists()) {
            ControlsLogger.printLog$default(this.controlsLogger, "ControlsFileLoader", "No backup file, returning null");
            return null;
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                try {
                    ControlsLogger.printLog$default(this.controlsLogger, "ControlsFileLoader", "Reading data from file: " + file);
                    BackupHelper.Companion.getClass();
                    synchronized (BackupHelper.controlsDataLock) {
                        XmlPullParser newPullParser = Xml.newPullParser();
                        newPullParser.setInput(bufferedInputStream, null);
                        parseXml = parseXml(newPullParser);
                    }
                    return parseXml;
                } finally {
                    IoUtils.closeQuietly(bufferedInputStream);
                }
            } catch (IOException e) {
                throw new IllegalStateException("Failed parsing backup file: " + file, e);
            } catch (XmlPullParserException e2) {
                throw new IllegalStateException("Failed parsing backup file: " + file, e2);
            }
        } catch (FileNotFoundException e3) {
            ControlsLogger controlsLogger = this.controlsLogger;
            ControlsLogger.LogLevel logLevel = ControlsLogger.LogLevel.INFO;
            controlsLogger.getClass();
            ControlsLogger.printLog("ControlsFileLoader", "No file found e=" + e3, logLevel);
            return null;
        }
    }
}
