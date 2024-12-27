package com.android.systemui.controls.controller;

import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import com.android.systemui.backup.BackupHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class ControlsFavoritePersistenceWrapper {
    public BackupManager backupManager;
    public final Executor executor;
    public File file;
    public final SecureSettings secureSettings;

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

    public ControlsFavoritePersistenceWrapper(File file, Executor executor, BackupManager backupManager, SecureSettings secureSettings) {
        this.file = file;
        this.executor = executor;
        this.backupManager = backupManager;
        this.secureSettings = secureSettings;
    }

    public static List parseXml(XmlPullParser xmlPullParser) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ComponentName componentName = null;
        String str = null;
        while (true) {
            boolean z = true;
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1) {
                    return arrayList;
                }
                String name = xmlPullParser.getName();
                if (name == null) {
                    name = "";
                }
                if (next == 2 && name.equals("structure")) {
                    componentName = ComponentName.unflattenFromString(xmlPullParser.getAttributeValue(null, "component"));
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
                    Integer valueOf = attributeValue6 != null ? Integer.valueOf(Integer.parseInt(attributeValue6)) : null;
                    if (attributeValue3 != null && attributeValue4 != null && valueOf != null) {
                        ControlInfo controlInfo = new ControlInfo(attributeValue3, attributeValue4, str2, valueOf.intValue(), 0, 16, null);
                        String attributeValue7 = xmlPullParser.getAttributeValue(null, "sem_layoutType");
                        if (attributeValue7 != null) {
                            controlInfo.layoutType = Integer.parseInt(attributeValue7);
                        }
                        arrayList2.add(controlInfo);
                    }
                } else if (next == 3 && name.equals("structure")) {
                    Intrinsics.checkNotNull(componentName);
                    Intrinsics.checkNotNull(str);
                    StructureInfo structureInfo = new StructureInfo(componentName, str, CollectionsKt___CollectionsKt.toList(arrayList2), false, 8, null);
                    structureInfo.active = z;
                    arrayList.add(structureInfo);
                    arrayList2.clear();
                }
            }
        }
    }

    public final List readFavorites() {
        List parseXml;
        if (!this.file.exists()) {
            Log.d("ControlsFavoritePersistenceWrapper", "No favorites, returning empty list");
            return EmptyList.INSTANCE;
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.file));
            try {
                try {
                    try {
                        Log.d("ControlsFavoritePersistenceWrapper", "Reading data from file: " + this.file);
                        BackupHelper.Companion.getClass();
                        synchronized (BackupHelper.controlsDataLock) {
                            XmlPullParser newPullParser = Xml.newPullParser();
                            newPullParser.setInput(bufferedInputStream, null);
                            parseXml = parseXml(newPullParser);
                        }
                        return parseXml;
                    } catch (XmlPullParserException e) {
                        throw new IllegalStateException("Failed parsing favorites file: " + this.file, e);
                    }
                } catch (IOException e2) {
                    throw new IllegalStateException("Failed parsing favorites file: " + this.file, e2);
                }
            } finally {
                IoUtils.closeQuietly(bufferedInputStream);
            }
        } catch (FileNotFoundException unused) {
            Log.i("ControlsFavoritePersistenceWrapper", "No file found");
            return EmptyList.INSTANCE;
        }
    }

    public final void storeFavorites(final List list) {
        if (!list.isEmpty() || this.file.exists()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : list) {
                String packageName = ((StructureInfo) obj).componentName.getPackageName();
                Object obj2 = linkedHashMap.get(packageName);
                if (obj2 == null) {
                    obj2 = new ArrayList();
                    linkedHashMap.put(packageName, obj2);
                }
                ((List) obj2).add(obj);
            }
            String join = TextUtils.join(",", linkedHashMap.keySet());
            Intrinsics.checkNotNull(join);
            this.secureSettings.putStringForUser("device_controls_use_components", join, -2);
            Log.d("ControlsFavoritePersistenceWrapper", "save DEVICE_CONTROLS_USE_COMPONENTS=".concat(join));
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsFavoritePersistenceWrapper$storeFavorites$1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z;
                    BackupManager backupManager;
                    Log.d("ControlsFavoritePersistenceWrapper", "Saving data to file: " + ControlsFavoritePersistenceWrapper.this.file);
                    AtomicFile atomicFile = new AtomicFile(ControlsFavoritePersistenceWrapper.this.file);
                    BackupHelper.Companion.getClass();
                    Object obj3 = BackupHelper.controlsDataLock;
                    List<StructureInfo> list2 = list;
                    synchronized (obj3) {
                        try {
                            FileOutputStream startWrite = atomicFile.startWrite();
                            try {
                                try {
                                    XmlSerializer newSerializer = Xml.newSerializer();
                                    newSerializer.setOutput(startWrite, "utf-8");
                                    z = true;
                                    newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                                    newSerializer.startDocument(null, Boolean.TRUE);
                                    newSerializer.startTag(null, "version");
                                    newSerializer.text("1");
                                    newSerializer.endTag(null, "version");
                                    newSerializer.startTag(null, "structures");
                                    for (StructureInfo structureInfo : list2) {
                                        newSerializer.startTag(null, "structure");
                                        newSerializer.attribute(null, "component", structureInfo.componentName.flattenToString());
                                        newSerializer.attribute(null, "structure", structureInfo.structure.toString());
                                        newSerializer.attribute(null, "sem_active", String.valueOf(structureInfo.active));
                                        newSerializer.startTag(null, "controls");
                                        for (ControlInfo controlInfo : structureInfo.controls) {
                                            newSerializer.startTag(null, "control");
                                            newSerializer.attribute(null, "id", controlInfo.controlId);
                                            newSerializer.attribute(null, UniversalCredentialUtil.AGENT_TITLE, controlInfo.controlTitle.toString());
                                            newSerializer.attribute(null, "subtitle", controlInfo.controlSubtitle.toString());
                                            newSerializer.attribute(null, "type", String.valueOf(controlInfo.deviceType));
                                            newSerializer.endTag(null, "control");
                                        }
                                        newSerializer.endTag(null, "controls");
                                        newSerializer.endTag(null, "structure");
                                    }
                                    newSerializer.endTag(null, "structures");
                                    newSerializer.endDocument();
                                    atomicFile.finishWrite(startWrite);
                                } finally {
                                    IoUtils.closeQuietly(startWrite);
                                }
                            } catch (Throwable unused) {
                                Log.e("ControlsFavoritePersistenceWrapper", "Failed to write file, reverting to previous version");
                                atomicFile.failWrite(startWrite);
                                z = false;
                            }
                        } catch (IOException e) {
                            Log.e("ControlsFavoritePersistenceWrapper", "Failed to start write file", e);
                            return;
                        }
                    }
                    if (!z || (backupManager = ControlsFavoritePersistenceWrapper.this.backupManager) == null) {
                        return;
                    }
                    backupManager.dataChanged();
                }
            });
        }
    }

    public /* synthetic */ ControlsFavoritePersistenceWrapper(File file, Executor executor, BackupManager backupManager, SecureSettings secureSettings, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, executor, (i & 4) != 0 ? null : backupManager, secureSettings);
    }
}
