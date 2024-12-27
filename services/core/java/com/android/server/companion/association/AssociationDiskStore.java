package com.android.server.companion.association;

import android.companion.AssociationInfo;
import android.net.MacAddress;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.companion.utils.DataStoreUtils;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public final class AssociationDiskStore {
    public final ConcurrentMap mUserIdToStorageFile = new ConcurrentHashMap();

    public static Associations readAssociationsFromInputStream(
            int i, InputStream inputStream, String str) {
        int i2;
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
        XmlUtils.beginDocument(resolvePullParser, str);
        int readIntAttribute =
                XmlUtils.readIntAttribute(resolvePullParser, "persistence-version", 0);
        Associations associations = new Associations();
        long j = 0;
        if (readIntAttribute == 0) {
            requireStartOfTag(resolvePullParser, "associations");
            int i3 = (100000 * i) + 1;
            associations = new Associations();
            associations.mVersion = 0;
            loop2:
            while (true) {
                i2 = i3;
                do {
                    resolvePullParser.nextTag();
                    if (DataStoreUtils.isEndOfTag(resolvePullParser, "associations")) {
                        break loop2;
                    }
                } while (!DataStoreUtils.isStartOfTag(resolvePullParser, "association"));
                i3 = i2 + 1;
                requireStartOfTag(resolvePullParser, "association");
                associations.mAssociations.add(
                        new AssociationInfo(
                                i2,
                                i,
                                XmlUtils.readStringAttribute(resolvePullParser, "package"),
                                XmlUtils.readStringAttribute(resolvePullParser, "tag"),
                                MacAddress.fromString(
                                        XmlUtils.readStringAttribute(resolvePullParser, "device")),
                                null,
                                XmlUtils.readStringAttribute(resolvePullParser, "profile"),
                                null,
                                false,
                                XmlUtils.readBooleanAttribute(
                                        resolvePullParser, "notify_device_nearby"),
                                false,
                                false,
                                XmlUtils.readLongAttribute(resolvePullParser, "time_approved", 0L),
                                Long.MAX_VALUE,
                                0));
            }
            associations.mMaxId = i2 - 1;
        } else if (readIntAttribute == 1) {
            while (true) {
                resolvePullParser.nextTag();
                if (DataStoreUtils.isStartOfTag(resolvePullParser, "associations")) {
                    requireStartOfTag(resolvePullParser, "associations");
                    int readIntAttribute2 =
                            XmlUtils.readIntAttribute(resolvePullParser, "max-id", 0);
                    Associations associations2 = new Associations();
                    associations2.mVersion = 1;
                    while (true) {
                        resolvePullParser.nextTag();
                        if (DataStoreUtils.isEndOfTag(resolvePullParser, "associations")) {
                            break;
                        }
                        if (DataStoreUtils.isStartOfTag(resolvePullParser, "association")) {
                            requireStartOfTag(resolvePullParser, "association");
                            int readIntAttribute3 =
                                    XmlUtils.readIntAttribute(resolvePullParser, "id");
                            String readStringAttribute =
                                    XmlUtils.readStringAttribute(resolvePullParser, "profile");
                            String readStringAttribute2 =
                                    XmlUtils.readStringAttribute(resolvePullParser, "package");
                            String readStringAttribute3 =
                                    XmlUtils.readStringAttribute(resolvePullParser, "tag");
                            String readStringAttribute4 =
                                    XmlUtils.readStringAttribute(resolvePullParser, "mac_address");
                            AssociationInfo associationInfo =
                                    new AssociationInfo(
                                            readIntAttribute3,
                                            i,
                                            readStringAttribute2,
                                            readStringAttribute3,
                                            readStringAttribute4 != null
                                                    ? MacAddress.fromString(readStringAttribute4)
                                                    : null,
                                            XmlUtils.readStringAttribute(
                                                    resolvePullParser, "display_name"),
                                            readStringAttribute,
                                            null,
                                            XmlUtils.readBooleanAttribute(
                                                    resolvePullParser, "self_managed"),
                                            XmlUtils.readBooleanAttribute(
                                                    resolvePullParser, "notify_device_nearby"),
                                            XmlUtils.readBooleanAttribute(
                                                    resolvePullParser, "revoked", false),
                                            XmlUtils.readBooleanAttribute(
                                                    resolvePullParser, "pending", false),
                                            XmlUtils.readLongAttribute(
                                                    resolvePullParser, "time_approved", j),
                                            XmlUtils.readLongAttribute(
                                                    resolvePullParser,
                                                    "last_time_connected",
                                                    Long.MAX_VALUE),
                                            XmlUtils.readIntAttribute(
                                                    resolvePullParser,
                                                    "system_data_sync_flags",
                                                    0));
                            associations2.mAssociations.add(associationInfo);
                            readIntAttribute2 =
                                    Math.max(readIntAttribute2, associationInfo.getId());
                            j = 0;
                        }
                    }
                    associations2.mMaxId = readIntAttribute2;
                    associations = associations2;
                } else {
                    if (DataStoreUtils.isEndOfTag(resolvePullParser, str)) {
                        break;
                    }
                    j = 0;
                }
            }
        }
        return associations;
    }

    public static void requireStartOfTag(XmlPullParser xmlPullParser, String str) {
        if (!DataStoreUtils.isStartOfTag(xmlPullParser, str)) {
            throw new XmlPullParserException("Should be at the start of \"associations\" tag");
        }
    }

    public final AtomicFile getStorageFileForUser(final int i) {
        return (AtomicFile)
                ((ConcurrentHashMap) this.mUserIdToStorageFile)
                        .computeIfAbsent(
                                Integer.valueOf(i),
                                new Function() { // from class:
                                                 // com.android.server.companion.association.AssociationDiskStore$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return new AtomicFile(
                                                new File(
                                                        Environment.getDataSystemDeDirectory(i),
                                                        "companion_device_manager.xml"));
                                    }
                                });
    }

    public final Map readAssociationsByUsers(List list) {
        String str;
        File file;
        AtomicFile atomicFile;
        Associations associations;
        HashMap hashMap = new HashMap();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int intValue = num.intValue();
            BootReceiver$$ExternalSyntheticOutline0.m(
                    intValue,
                    "Reading associations for user ",
                    " from disk.",
                    "CDM_AssociationDiskStore");
            AtomicFile storageFileForUser = getStorageFileForUser(intValue);
            synchronized (storageFileForUser) {
                try {
                    if (storageFileForUser.getBaseFile().exists()) {
                        str = LauncherConfigurationInternal.KEY_STATE_BOOLEAN;
                        file = null;
                        atomicFile = storageFileForUser;
                    } else {
                        file =
                                new File(
                                        Environment.getUserSystemDirectory(intValue),
                                        "companion_device_manager_associations.xml");
                        if (file.exists()) {
                            atomicFile = new AtomicFile(file);
                            str = "associations";
                        } else {
                            associations = new Associations();
                        }
                    }
                    try {
                        FileInputStream openRead = atomicFile.openRead();
                        try {
                            associations = readAssociationsFromInputStream(intValue, openRead, str);
                            if (openRead != null) {
                                openRead.close();
                            }
                        } catch (Throwable th) {
                            if (openRead != null) {
                                try {
                                    openRead.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (IOException | XmlPullParserException e) {
                        Slog.e(
                                "CDM_AssociationDiskStore",
                                "Error while reading associations file",
                                e);
                        associations = new Associations();
                    }
                    if (file != null || associations.mVersion < 1) {
                        DataStoreUtils.writeToFileSafely(
                                storageFileForUser,
                                new AssociationDiskStore$$ExternalSyntheticLambda0(associations));
                        if (file != null) {
                            file.delete();
                        }
                    }
                } finally {
                }
            }
            hashMap.put(num, associations);
        }
        return hashMap;
    }
}
