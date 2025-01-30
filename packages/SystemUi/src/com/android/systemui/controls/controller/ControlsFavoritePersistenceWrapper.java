package com.android.systemui.controls.controller;

import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.backup.BackupHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsFavoritePersistenceWrapper {
    public BackupManager backupManager;
    public final Executor executor;
    public File file;
    public final SecureSettings secureSettings;

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
                if (next == 2 && Intrinsics.areEqual(name, "structure")) {
                    componentName = ComponentName.unflattenFromString(xmlPullParser.getAttributeValue(null, "component"));
                    str = xmlPullParser.getAttributeValue(null, "structure");
                    if (str == null) {
                        str = "";
                    }
                    if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                        String attributeValue = xmlPullParser.getAttributeValue(null, "sem_active");
                        if (attributeValue != null) {
                            z = Boolean.parseBoolean(attributeValue);
                        }
                    } else {
                        continue;
                    }
                } else if (next == 2 && Intrinsics.areEqual(name, "control")) {
                    String attributeValue2 = xmlPullParser.getAttributeValue(null, "id");
                    String attributeValue3 = xmlPullParser.getAttributeValue(null, UniversalCredentialUtil.AGENT_TITLE);
                    String attributeValue4 = xmlPullParser.getAttributeValue(null, "subtitle");
                    String str2 = attributeValue4 != null ? attributeValue4 : "";
                    String attributeValue5 = xmlPullParser.getAttributeValue(null, "type");
                    Integer valueOf = attributeValue5 != null ? Integer.valueOf(Integer.parseInt(attributeValue5)) : null;
                    if (attributeValue2 != null && attributeValue3 != null && valueOf != null) {
                        ControlInfo controlInfo = new ControlInfo(attributeValue2, attributeValue3, str2, valueOf.intValue());
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
                    if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                        structureInfo.customStructureInfo.active = z;
                    }
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
                    Log.d("ControlsFavoritePersistenceWrapper", "Reading data from file: " + this.file);
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
                throw new IllegalStateException("Failed parsing favorites file: " + this.file, e);
            } catch (XmlPullParserException e2) {
                throw new IllegalStateException("Failed parsing favorites file: " + this.file, e2);
            }
        } catch (FileNotFoundException unused) {
            Log.i("ControlsFavoritePersistenceWrapper", "No file found");
            return EmptyList.INSTANCE;
        }
    }

    public final void storeFavorites(final List list) {
        if (!list.isEmpty() || this.file.exists()) {
            if (BasicRune.CONTROLS_MANAGE_BACKUP_RESOTRE) {
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
                ((SecureSettingsImpl) this.secureSettings).putStringForUser(-2, "device_controls_use_components", join);
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("save DEVICE_CONTROLS_USE_COMPONENTS="), join, "ControlsFavoritePersistenceWrapper");
            }
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
                                            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                                                newSerializer.attribute(null, "sem_active", String.valueOf(structureInfo.customStructureInfo.active));
                                            }
                                            newSerializer.startTag(null, "controls");
                                            for (ControlInfo controlInfo : structureInfo.controls) {
                                                newSerializer.startTag(null, "control");
                                                newSerializer.attribute(null, "id", controlInfo.controlId);
                                                newSerializer.attribute(null, UniversalCredentialUtil.AGENT_TITLE, controlInfo.controlTitle.toString());
                                                newSerializer.attribute(null, "subtitle", controlInfo.controlSubtitle.toString());
                                                newSerializer.attribute(null, "type", String.valueOf(controlInfo.deviceType));
                                                if (BasicRune.CONTROLS_LAYOUT_TYPE) {
                                                    newSerializer.attribute(null, "sem_layoutType", String.valueOf(controlInfo.customControlInfo.layoutType));
                                                }
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
                        } catch (Throwable th) {
                            throw th;
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
