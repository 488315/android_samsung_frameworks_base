package com.android.server.companion.devicepresence;

import android.os.ParcelUuid;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ObservableUuidStore {
    public final ConcurrentMap mUserIdToStorageFile = new ConcurrentHashMap();
    public final Object mLock = new Object();
    public final SparseArray mCachedPerUser = new SparseArray();
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public static List readObservableUuidFromXml(TypedXmlPullParser typedXmlPullParser) {
        if (!DataStoreUtils.isStartOfTag(typedXmlPullParser, "uuids")) {
            throw new XmlPullParserException("The XML doesn't have start tag: uuids");
        }
        ArrayList arrayList = new ArrayList();
        while (true) {
            typedXmlPullParser.nextTag();
            if (DataStoreUtils.isEndOfTag(typedXmlPullParser, "uuids")) {
                return arrayList;
            }
            if (DataStoreUtils.isStartOfTag(typedXmlPullParser, "uuid")) {
                if (!DataStoreUtils.isStartOfTag(typedXmlPullParser, "uuid")) {
                    throw new XmlPullParserException("XML doesn't have start tag: uuid");
                }
                arrayList.add(new ObservableUuid(XmlUtils.readIntAttribute(typedXmlPullParser, "user_id"), ParcelUuid.fromString(XmlUtils.readStringAttribute(typedXmlPullParser, "uuid")), XmlUtils.readStringAttribute(typedXmlPullParser, "package_name"), Long.valueOf(XmlUtils.readLongAttribute(typedXmlPullParser, "time_approved"))));
            }
        }
    }

    public final List getObservableUuidsForPackage(int i, String str) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                for (ObservableUuid observableUuid : readObservableUuidsFromCache(i)) {
                    if (observableUuid.mPackageName.equals(str)) {
                        arrayList.add(observableUuid);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final boolean isUuidBeingObserved(int i, ParcelUuid parcelUuid, String str) {
        Iterator it = ((ArrayList) getObservableUuidsForPackage(i, str)).iterator();
        while (it.hasNext()) {
            if (((ObservableUuid) it.next()).mUuid.equals(parcelUuid)) {
                return true;
            }
        }
        return false;
    }

    public final List readObservableUuidsFromCache(final int i) {
        List list = (List) this.mCachedPerUser.get(i);
        if (list == null) {
            try {
                list = (List) this.mExecutor.submit(new Callable() { // from class: com.android.server.companion.devicepresence.ObservableUuidStore$$ExternalSyntheticLambda0
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        ObservableUuidStore observableUuidStore = ObservableUuidStore.this;
                        int i2 = i;
                        AtomicFile atomicFile = (AtomicFile) ((ConcurrentHashMap) observableUuidStore.mUserIdToStorageFile).computeIfAbsent(Integer.valueOf(i2), new ObservableUuidStore$$ExternalSyntheticLambda3(i2));
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "Reading ObservableUuid for user ", " from file=");
                        m.append(atomicFile.getBaseFile().getPath());
                        Slog.i("CDM_ObservableUuidStore", m.toString());
                        synchronized (atomicFile) {
                            if (!atomicFile.getBaseFile().exists()) {
                                Slog.d("CDM_ObservableUuidStore", "File does not exist -> Abort");
                                return new ArrayList();
                            }
                            try {
                                FileInputStream openRead = atomicFile.openRead();
                                try {
                                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                                    XmlUtils.beginDocument(resolvePullParser, "uuids");
                                    List readObservableUuidFromXml = ObservableUuidStore.readObservableUuidFromXml(resolvePullParser);
                                    if (openRead != null) {
                                        openRead.close();
                                    }
                                    return readObservableUuidFromXml;
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
                                Slog.e("CDM_ObservableUuidStore", "Error while reading requests file", e);
                                return new ArrayList();
                            }
                        }
                    }
                }).get(5L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                Slog.e("CDM_ObservableUuidStore", "Thread reading ObservableUuid from disk is interrupted.");
            } catch (ExecutionException unused2) {
                Slog.e("CDM_ObservableUuidStore", "Error occurred while reading ObservableUuid from disk.");
            } catch (TimeoutException unused3) {
                Slog.e("CDM_ObservableUuidStore", "Reading ObservableUuid from disk timed out.");
            }
            this.mCachedPerUser.set(i, list);
        }
        return list;
    }

    public final void removeObservableUuid(int i, final ParcelUuid parcelUuid, final String str) {
        List readObservableUuidsFromCache;
        Slog.i("CDM_ObservableUuidStore", "Removing uuid=[" + parcelUuid.getUuid() + "] from store...");
        synchronized (this.mLock) {
            readObservableUuidsFromCache = readObservableUuidsFromCache(i);
            readObservableUuidsFromCache.removeIf(new Predicate() { // from class: com.android.server.companion.devicepresence.ObservableUuidStore$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    ObservableUuid observableUuid = (ObservableUuid) obj;
                    return observableUuid.mPackageName.equals(str) && observableUuid.mUuid.equals(parcelUuid);
                }
            });
            this.mCachedPerUser.set(i, readObservableUuidsFromCache);
        }
        this.mExecutor.execute(new ObservableUuidStore$$ExternalSyntheticLambda2(this, i, readObservableUuidsFromCache, 0));
    }

    public final void writeObservableUuidToStore(int i, final List list) {
        AtomicFile atomicFile = (AtomicFile) ((ConcurrentHashMap) this.mUserIdToStorageFile).computeIfAbsent(Integer.valueOf(i), new ObservableUuidStore$$ExternalSyntheticLambda3(i));
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Writing ObservableUuid for user ", " to file=");
        m.append(atomicFile.getBaseFile().getPath());
        Slog.i("CDM_ObservableUuidStore", m.toString());
        synchronized (atomicFile) {
            DataStoreUtils.writeToFileSafely(atomicFile, new FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.companion.devicepresence.ObservableUuidStore$$ExternalSyntheticLambda6
                public final void acceptOrThrow(Object obj) {
                    ObservableUuidStore observableUuidStore = ObservableUuidStore.this;
                    List<ObservableUuid> list2 = list;
                    observableUuidStore.getClass();
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer((FileOutputStream) obj);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.startTag((String) null, "uuids");
                    for (ObservableUuid observableUuid : list2) {
                        resolveSerializer.startTag((String) null, "uuid");
                        XmlUtils.writeIntAttribute(resolveSerializer, "user_id", observableUuid.mUserId);
                        XmlUtils.writeStringAttribute(resolveSerializer, "uuid", observableUuid.mUuid.toString());
                        XmlUtils.writeStringAttribute(resolveSerializer, "package_name", observableUuid.mPackageName);
                        XmlUtils.writeLongAttribute(resolveSerializer, "time_approved", observableUuid.mTimeApprovedMs);
                        resolveSerializer.endTag((String) null, "uuid");
                    }
                    resolveSerializer.endTag((String) null, "uuids");
                    resolveSerializer.endDocument();
                }
            });
        }
    }
}
