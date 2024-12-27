package com.android.server.companion.datatransfer;

import android.companion.datatransfer.PermissionSyncRequest;
import android.companion.datatransfer.SystemDataTransferRequest;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.companion.utils.DataStoreUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemDataTransferRequestStore {
    public final ConcurrentMap mUserIdToStorageFile = new ConcurrentHashMap();
    public final Object mLock = new Object();
    public final SparseArray mCachedPerUser = new SparseArray();
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public static ArrayList readRequestsFromXml(int i, TypedXmlPullParser typedXmlPullParser) {
        PermissionSyncRequest permissionSyncRequest;
        if (!DataStoreUtils.isStartOfTag(typedXmlPullParser, "requests")) {
            throw new XmlPullParserException("The XML doesn't have start tag: requests");
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            typedXmlPullParser.nextTag();
            if (DataStoreUtils.isEndOfTag(typedXmlPullParser, "requests")) {
                return arrayList;
            }
            if (DataStoreUtils.isStartOfTag(typedXmlPullParser, "request")) {
                if (!DataStoreUtils.isStartOfTag(typedXmlPullParser, "request")) {
                    throw new XmlPullParserException("XML doesn't have start tag: request");
                }
                int readIntAttribute = XmlUtils.readIntAttribute(typedXmlPullParser, "association_id");
                int readIntAttribute2 = XmlUtils.readIntAttribute(typedXmlPullParser, "data_type");
                boolean readBooleanAttribute = XmlUtils.readBooleanAttribute(typedXmlPullParser, "is_user_consented");
                if (readIntAttribute2 != 1) {
                    permissionSyncRequest = null;
                } else {
                    PermissionSyncRequest permissionSyncRequest2 = new PermissionSyncRequest(readIntAttribute);
                    permissionSyncRequest2.setUserId(i);
                    permissionSyncRequest2.setUserConsented(readBooleanAttribute);
                    permissionSyncRequest = permissionSyncRequest2;
                }
                arrayList.add(permissionSyncRequest);
            }
        }
    }

    public final AtomicFile getStorageFileForUser(final int i) {
        return (AtomicFile) ((ConcurrentHashMap) this.mUserIdToStorageFile).computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new AtomicFile(new File(Environment.getDataSystemDeDirectory(i), "companion_device_system_data_transfer_requests.xml"));
            }
        });
    }

    public final List readRequestsByAssociationId(int i, int i2) {
        ArrayList<SystemDataTransferRequest> readRequestsFromCache;
        synchronized (this.mLock) {
            readRequestsFromCache = readRequestsFromCache(i);
        }
        ArrayList arrayList = new ArrayList();
        for (SystemDataTransferRequest systemDataTransferRequest : readRequestsFromCache) {
            if (systemDataTransferRequest.getAssociationId() == i2) {
                arrayList.add(systemDataTransferRequest);
            }
        }
        return arrayList;
    }

    public final ArrayList readRequestsFromCache(final int i) {
        ArrayList arrayList = (ArrayList) this.mCachedPerUser.get(i);
        if (arrayList == null) {
            try {
                arrayList = (ArrayList) this.mExecutor.submit(new Callable() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda4
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        ArrayList arrayList2;
                        SystemDataTransferRequestStore systemDataTransferRequestStore = SystemDataTransferRequestStore.this;
                        int i2 = i;
                        AtomicFile storageFileForUser = systemDataTransferRequestStore.getStorageFileForUser(i2);
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "Reading SystemDataTransferRequests for user ", " from file=");
                        m.append(storageFileForUser.getBaseFile().getPath());
                        Slog.i("CDM_SystemDataTransferRequestStore", m.toString());
                        synchronized (storageFileForUser) {
                            if (storageFileForUser.getBaseFile().exists()) {
                                try {
                                    FileInputStream openRead = storageFileForUser.openRead();
                                    try {
                                        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                                        XmlUtils.beginDocument(resolvePullParser, "requests");
                                        arrayList2 = SystemDataTransferRequestStore.readRequestsFromXml(i2, resolvePullParser);
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
                                    Slog.e("CDM_SystemDataTransferRequestStore", "Error while reading requests file", e);
                                    arrayList2 = new ArrayList();
                                }
                            } else {
                                Slog.d("CDM_SystemDataTransferRequestStore", "File does not exist -> Abort");
                                arrayList2 = new ArrayList();
                            }
                        }
                        return arrayList2;
                    }
                }).get(5L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                Slog.e("CDM_SystemDataTransferRequestStore", "Thread reading SystemDataTransferRequest from disk is interrupted.");
            } catch (ExecutionException unused2) {
                Slog.e("CDM_SystemDataTransferRequestStore", "Error occurred while reading SystemDataTransferRequest from disk.");
            } catch (TimeoutException unused3) {
                Slog.e("CDM_SystemDataTransferRequestStore", "Reading SystemDataTransferRequest from disk timed out.");
            }
            this.mCachedPerUser.set(i, arrayList);
        }
        return arrayList;
    }

    public final void removeRequestsByAssociationId(int i, final int i2) {
        ArrayList readRequestsFromCache;
        Slog.i("CDM_SystemDataTransferRequestStore", "Removing system data transfer requests for userId=" + i + ", associationId=" + i2);
        synchronized (this.mLock) {
            readRequestsFromCache = readRequestsFromCache(i);
            readRequestsFromCache.removeIf(new Predicate() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((SystemDataTransferRequest) obj).getAssociationId() == i2;
                }
            });
            this.mCachedPerUser.set(i, readRequestsFromCache);
        }
        this.mExecutor.execute(new SystemDataTransferRequestStore$$ExternalSyntheticLambda1(this, i, readRequestsFromCache, 1));
    }

    public final void writeRequest(int i, final SystemDataTransferRequest systemDataTransferRequest) {
        ArrayList readRequestsFromCache;
        Slog.i("CDM_SystemDataTransferRequestStore", "Writing request=" + systemDataTransferRequest + " to store.");
        synchronized (this.mLock) {
            readRequestsFromCache = readRequestsFromCache(i);
            readRequestsFromCache.removeIf(new Predicate() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((SystemDataTransferRequest) obj).getAssociationId() == systemDataTransferRequest.getAssociationId();
                }
            });
            readRequestsFromCache.add(systemDataTransferRequest);
            this.mCachedPerUser.set(i, readRequestsFromCache);
        }
        this.mExecutor.execute(new SystemDataTransferRequestStore$$ExternalSyntheticLambda1(this, i, readRequestsFromCache, 0));
    }

    public final void writeRequestsToStore(int i, final List list) {
        AtomicFile storageFileForUser = getStorageFileForUser(i);
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Writing SystemDataTransferRequests for user ", " to file=");
        m.append(storageFileForUser.getBaseFile().getPath());
        Slog.i("CDM_SystemDataTransferRequestStore", m.toString());
        synchronized (storageFileForUser) {
            DataStoreUtils.writeToFileSafely(storageFileForUser, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda5
                public final void acceptOrThrow(Object obj) {
                    SystemDataTransferRequestStore systemDataTransferRequestStore = SystemDataTransferRequestStore.this;
                    List<SystemDataTransferRequest> list2 = list;
                    systemDataTransferRequestStore.getClass();
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer((FileOutputStream) obj);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.startTag((String) null, "requests");
                    for (SystemDataTransferRequest systemDataTransferRequest : list2) {
                        resolveSerializer.startTag((String) null, "request");
                        XmlUtils.writeIntAttribute(resolveSerializer, "association_id", systemDataTransferRequest.getAssociationId());
                        XmlUtils.writeIntAttribute(resolveSerializer, "data_type", systemDataTransferRequest.getDataType());
                        XmlUtils.writeBooleanAttribute(resolveSerializer, "is_user_consented", systemDataTransferRequest.isUserConsented());
                        resolveSerializer.endTag((String) null, "request");
                    }
                    resolveSerializer.endTag((String) null, "requests");
                    resolveSerializer.endDocument();
                }
            });
        }
    }
}
