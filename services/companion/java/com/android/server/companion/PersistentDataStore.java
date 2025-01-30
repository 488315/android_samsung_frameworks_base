package com.android.server.companion;

import android.companion.AssociationInfo;
import android.content.pm.UserInfo;
import android.net.MacAddress;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class PersistentDataStore {
    public final ConcurrentMap mUserIdToStorageFile = new ConcurrentHashMap();

    public void readStateForUsers(List list, Set set, SparseArray sparseArray) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            ArrayMap arrayMap = new ArrayMap();
            HashSet hashSet = new HashSet();
            readStateForUser(i, hashSet, arrayMap);
            int firstAssociationIdForUser = CompanionDeviceManagerService.getFirstAssociationIdForUser(i);
            int lastAssociationIdForUser = CompanionDeviceManagerService.getLastAssociationIdForUser(i);
            Iterator it2 = hashSet.iterator();
            while (it2.hasNext()) {
                int id = ((AssociationInfo) it2.next()).getId();
                if (id < firstAssociationIdForUser || id > lastAssociationIdForUser) {
                    Slog.e("CompanionDevice_PersistentDataStore", "Wrong association ID assignment: " + id + ". Association belongs to u" + i + " and thus its ID should be within [" + firstAssociationIdForUser + ", " + lastAssociationIdForUser + "] range.");
                }
            }
            set.addAll(hashSet);
            sparseArray.append(i, arrayMap);
        }
    }

    public void readStateForUser(int i, Collection collection, Map map) {
        String str;
        File file;
        AtomicFile atomicFile;
        Slog.i("CompanionDevice_PersistentDataStore", "Reading associations for user " + i + " from disk");
        AtomicFile storageFileForUser = getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            if (storageFileForUser.getBaseFile().exists()) {
                str = LauncherConfigurationInternal.KEY_STATE_BOOLEAN;
                file = null;
                atomicFile = storageFileForUser;
            } else {
                file = getBaseLegacyStorageFileForUser(i);
                if (!file.exists()) {
                    return;
                }
                str = "associations";
                atomicFile = new AtomicFile(file);
            }
            int readStateFromFileLocked = readStateFromFileLocked(i, atomicFile, str, collection, map);
            if (file != null || readStateFromFileLocked < 1) {
                persistStateToFileLocked(storageFileForUser, collection, map);
                if (file != null) {
                    file.delete();
                }
            }
        }
    }

    public void persistStateForUser(int i, Collection collection, Map map) {
        Slog.i("CompanionDevice_PersistentDataStore", "Writing associations for user " + i + " to disk");
        AtomicFile storageFileForUser = getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            persistStateToFileLocked(storageFileForUser, collection, map);
        }
    }

    public final int readStateFromFileLocked(int i, AtomicFile atomicFile, String str, Collection collection, Map map) {
        try {
            FileInputStream openRead = atomicFile.openRead();
            try {
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                XmlUtils.beginDocument(resolvePullParser, str);
                int readIntAttribute = XmlUtils.readIntAttribute(resolvePullParser, "persistence-version", 0);
                if (readIntAttribute == 0) {
                    readAssociationsV0(resolvePullParser, i, collection);
                } else if (readIntAttribute == 1) {
                    while (true) {
                        resolvePullParser.nextTag();
                        if (DataStoreUtils.isStartOfTag(resolvePullParser, "associations")) {
                            readAssociationsV1(resolvePullParser, i, collection);
                        } else if (DataStoreUtils.isStartOfTag(resolvePullParser, "previously-used-ids")) {
                            readPreviouslyUsedIdsV1(resolvePullParser, map);
                        } else if (DataStoreUtils.isEndOfTag(resolvePullParser, str)) {
                            break;
                        }
                    }
                }
                if (openRead != null) {
                    openRead.close();
                }
                return readIntAttribute;
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
            Slog.e("CompanionDevice_PersistentDataStore", "Error while reading associations file", e);
            return -1;
        }
    }

    public final void persistStateToFileLocked(AtomicFile atomicFile, final Collection collection, final Map map) {
        DataStoreUtils.writeToFileSafely(atomicFile, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.PersistentDataStore$$ExternalSyntheticLambda1
            public final void acceptOrThrow(Object obj) {
                PersistentDataStore.lambda$persistStateToFileLocked$0(collection, map, (FileOutputStream) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$persistStateToFileLocked$0(Collection collection, Map map, FileOutputStream fileOutputStream) {
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.startTag((String) null, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        XmlUtils.writeIntAttribute(resolveSerializer, "persistence-version", 1);
        writeAssociations(resolveSerializer, collection);
        writePreviouslyUsedIds(resolveSerializer, map);
        resolveSerializer.endTag((String) null, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        resolveSerializer.endDocument();
    }

    public final AtomicFile getStorageFileForUser(final int i) {
        return (AtomicFile) this.mUserIdToStorageFile.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.server.companion.PersistentDataStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                AtomicFile lambda$getStorageFileForUser$1;
                lambda$getStorageFileForUser$1 = PersistentDataStore.lambda$getStorageFileForUser$1(i, (Integer) obj);
                return lambda$getStorageFileForUser$1;
            }
        });
    }

    public static /* synthetic */ AtomicFile lambda$getStorageFileForUser$1(int i, Integer num) {
        return DataStoreUtils.createStorageFileForUser(i, "companion_device_manager.xml");
    }

    public static File getBaseLegacyStorageFileForUser(int i) {
        return new File(Environment.getUserSystemDirectory(i), "companion_device_manager_associations.xml");
    }

    public static void readAssociationsV0(TypedXmlPullParser typedXmlPullParser, int i, Collection collection) {
        requireStartOfTag(typedXmlPullParser, "associations");
        int firstAssociationIdForUser = CompanionDeviceManagerService.getFirstAssociationIdForUser(i);
        while (true) {
            typedXmlPullParser.nextTag();
            if (DataStoreUtils.isEndOfTag(typedXmlPullParser, "associations")) {
                return;
            }
            if (DataStoreUtils.isStartOfTag(typedXmlPullParser, "association")) {
                readAssociationV0(typedXmlPullParser, i, firstAssociationIdForUser, collection);
                firstAssociationIdForUser++;
            }
        }
    }

    public static void readAssociationV0(TypedXmlPullParser typedXmlPullParser, int i, int i2, Collection collection) {
        requireStartOfTag(typedXmlPullParser, "association");
        String readStringAttribute = XmlUtils.readStringAttribute(typedXmlPullParser, "package");
        String readStringAttribute2 = XmlUtils.readStringAttribute(typedXmlPullParser, "device");
        if (readStringAttribute == null || readStringAttribute2 == null) {
            return;
        }
        collection.add(new AssociationInfo(i2, i, readStringAttribute, MacAddress.fromString(readStringAttribute2), null, XmlUtils.readStringAttribute(typedXmlPullParser, "profile"), null, false, XmlUtils.readBooleanAttribute(typedXmlPullParser, "notify_device_nearby"), false, XmlUtils.readLongAttribute(typedXmlPullParser, "time_approved", 0L), Long.MAX_VALUE, 0));
    }

    public static void readAssociationsV1(TypedXmlPullParser typedXmlPullParser, int i, Collection collection) {
        requireStartOfTag(typedXmlPullParser, "associations");
        while (true) {
            typedXmlPullParser.nextTag();
            if (DataStoreUtils.isEndOfTag(typedXmlPullParser, "associations")) {
                return;
            }
            if (DataStoreUtils.isStartOfTag(typedXmlPullParser, "association")) {
                readAssociationV1(typedXmlPullParser, i, collection);
            }
        }
    }

    public static void readAssociationV1(TypedXmlPullParser typedXmlPullParser, int i, Collection collection) {
        requireStartOfTag(typedXmlPullParser, "association");
        AssociationInfo createAssociationInfoNoThrow = createAssociationInfoNoThrow(XmlUtils.readIntAttribute(typedXmlPullParser, "id"), i, XmlUtils.readStringAttribute(typedXmlPullParser, "package"), stringToMacAddress(XmlUtils.readStringAttribute(typedXmlPullParser, "mac_address")), XmlUtils.readStringAttribute(typedXmlPullParser, "display_name"), XmlUtils.readStringAttribute(typedXmlPullParser, "profile"), XmlUtils.readBooleanAttribute(typedXmlPullParser, "self_managed"), XmlUtils.readBooleanAttribute(typedXmlPullParser, "notify_device_nearby"), XmlUtils.readBooleanAttribute(typedXmlPullParser, "revoked", false), XmlUtils.readLongAttribute(typedXmlPullParser, "time_approved", 0L), XmlUtils.readLongAttribute(typedXmlPullParser, "last_time_connected", Long.MAX_VALUE), XmlUtils.readIntAttribute(typedXmlPullParser, "system_data_sync_flags", 0));
        if (createAssociationInfoNoThrow != null) {
            collection.add(createAssociationInfoNoThrow);
        }
    }

    public static void readPreviouslyUsedIdsV1(TypedXmlPullParser typedXmlPullParser, Map map) {
        requireStartOfTag(typedXmlPullParser, "previously-used-ids");
        while (true) {
            typedXmlPullParser.nextTag();
            if (DataStoreUtils.isEndOfTag(typedXmlPullParser, "previously-used-ids")) {
                return;
            }
            if (DataStoreUtils.isStartOfTag(typedXmlPullParser, "package")) {
                String readStringAttribute = XmlUtils.readStringAttribute(typedXmlPullParser, "package_name");
                HashSet hashSet = new HashSet();
                while (true) {
                    typedXmlPullParser.nextTag();
                    if (DataStoreUtils.isEndOfTag(typedXmlPullParser, "package")) {
                        break;
                    } else if (DataStoreUtils.isStartOfTag(typedXmlPullParser, "id")) {
                        typedXmlPullParser.nextToken();
                        hashSet.add(Integer.valueOf(Integer.parseInt(typedXmlPullParser.getText())));
                    }
                }
                map.put(readStringAttribute, hashSet);
            }
        }
    }

    public static void writeAssociations(XmlSerializer xmlSerializer, Collection collection) {
        XmlSerializer startTag = xmlSerializer.startTag(null, "associations");
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            writeAssociation(startTag, (AssociationInfo) it.next());
        }
        startTag.endTag(null, "associations");
    }

    public static void writeAssociation(XmlSerializer xmlSerializer, AssociationInfo associationInfo) {
        XmlSerializer startTag = xmlSerializer.startTag(null, "association");
        XmlUtils.writeIntAttribute(startTag, "id", associationInfo.getId());
        XmlUtils.writeStringAttribute(startTag, "profile", associationInfo.getDeviceProfile());
        XmlUtils.writeStringAttribute(startTag, "package", associationInfo.getPackageName());
        XmlUtils.writeStringAttribute(startTag, "mac_address", associationInfo.getDeviceMacAddressAsString());
        XmlUtils.writeStringAttribute(startTag, "display_name", associationInfo.getDisplayName());
        XmlUtils.writeBooleanAttribute(startTag, "self_managed", associationInfo.isSelfManaged());
        XmlUtils.writeBooleanAttribute(startTag, "notify_device_nearby", associationInfo.isNotifyOnDeviceNearby());
        XmlUtils.writeBooleanAttribute(startTag, "revoked", associationInfo.isRevoked());
        XmlUtils.writeLongAttribute(startTag, "time_approved", associationInfo.getTimeApprovedMs());
        XmlUtils.writeLongAttribute(startTag, "last_time_connected", associationInfo.getLastTimeConnectedMs().longValue());
        XmlUtils.writeIntAttribute(startTag, "system_data_sync_flags", associationInfo.getSystemDataSyncFlags());
        startTag.endTag(null, "association");
    }

    public static void writePreviouslyUsedIds(XmlSerializer xmlSerializer, Map map) {
        XmlSerializer startTag = xmlSerializer.startTag(null, "previously-used-ids");
        for (Map.Entry entry : map.entrySet()) {
            writePreviouslyUsedIdsForPackage(startTag, (String) entry.getKey(), (Set) entry.getValue());
        }
        startTag.endTag(null, "previously-used-ids");
    }

    public static void writePreviouslyUsedIdsForPackage(XmlSerializer xmlSerializer, String str, Set set) {
        final XmlSerializer startTag = xmlSerializer.startTag(null, "package");
        XmlUtils.writeStringAttribute(startTag, "package_name", str);
        CollectionUtils.forEach(set, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.PersistentDataStore$$ExternalSyntheticLambda2
            public final void acceptOrThrow(Object obj) {
                PersistentDataStore.lambda$writePreviouslyUsedIdsForPackage$2(startTag, (Integer) obj);
            }
        });
        startTag.endTag(null, "package");
    }

    public static /* synthetic */ void lambda$writePreviouslyUsedIdsForPackage$2(XmlSerializer xmlSerializer, Integer num) {
        xmlSerializer.startTag(null, "id").text(Integer.toString(num.intValue())).endTag(null, "id");
    }

    public static void requireStartOfTag(XmlPullParser xmlPullParser, String str) {
        if (!DataStoreUtils.isStartOfTag(xmlPullParser, str)) {
            throw new XmlPullParserException("Should be at the start of \"associations\" tag");
        }
    }

    public static MacAddress stringToMacAddress(String str) {
        if (str != null) {
            return MacAddress.fromString(str);
        }
        return null;
    }

    public static AssociationInfo createAssociationInfoNoThrow(int i, int i2, String str, MacAddress macAddress, CharSequence charSequence, String str2, boolean z, boolean z2, boolean z3, long j, long j2, int i3) {
        try {
            return new AssociationInfo(i, i2, str, macAddress, charSequence, str2, null, z, z2, z3, j, j2, i3);
        } catch (Exception unused) {
            return null;
        }
    }
}
