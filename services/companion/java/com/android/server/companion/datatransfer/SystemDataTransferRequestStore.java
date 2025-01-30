package com.android.server.companion.datatransfer;

import android.companion.datatransfer.PermissionSyncRequest;
import android.companion.datatransfer.SystemDataTransferRequest;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.companion.DataStoreUtils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SystemDataTransferRequestStore {
  public final ConcurrentMap mUserIdToStorageFile = new ConcurrentHashMap();
  public final Object mLock = new Object();
  public final SparseArray mCachedPerUser = new SparseArray();
  public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

  public List readRequestsByAssociationId(int i, int i2) {
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

  public void writeRequest(final int i, final SystemDataTransferRequest systemDataTransferRequest) {
    final ArrayList readRequestsFromCache;
    Slog.i(
        "CDM_SystemDataTransferRequestStore",
        "Writing request=" + systemDataTransferRequest + " to store.");
    synchronized (this.mLock) {
      readRequestsFromCache = readRequestsFromCache(i);
      readRequestsFromCache.removeIf(
          new Predicate() { // from class:
            // com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$writeRequest$0;
              lambda$writeRequest$0 =
                  SystemDataTransferRequestStore.lambda$writeRequest$0(
                      systemDataTransferRequest, (SystemDataTransferRequest) obj);
              return lambda$writeRequest$0;
            }
          });
      readRequestsFromCache.add(systemDataTransferRequest);
      this.mCachedPerUser.set(i, readRequestsFromCache);
    }
    this.mExecutor.execute(
        new Runnable() { // from class:
          // com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda3
          @Override // java.lang.Runnable
          public final void run() {
            SystemDataTransferRequestStore.this.lambda$writeRequest$1(i, readRequestsFromCache);
          }
        });
  }

  public static /* synthetic */ boolean lambda$writeRequest$0(
      SystemDataTransferRequest systemDataTransferRequest,
      SystemDataTransferRequest systemDataTransferRequest2) {
    return systemDataTransferRequest2.getAssociationId()
        == systemDataTransferRequest.getAssociationId();
  }

  public void removeRequestsByAssociationId(final int i, final int i2) {
    final ArrayList readRequestsFromCache;
    Slog.i(
        "CDM_SystemDataTransferRequestStore",
        "Removing system data transfer requests for userId=" + i + ", associationId=" + i2);
    synchronized (this.mLock) {
      readRequestsFromCache = readRequestsFromCache(i);
      readRequestsFromCache.removeIf(
          new Predicate() { // from class:
            // com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$removeRequestsByAssociationId$2;
              lambda$removeRequestsByAssociationId$2 =
                  SystemDataTransferRequestStore.lambda$removeRequestsByAssociationId$2(
                      i2, (SystemDataTransferRequest) obj);
              return lambda$removeRequestsByAssociationId$2;
            }
          });
      this.mCachedPerUser.set(i, readRequestsFromCache);
    }
    this.mExecutor.execute(
        new Runnable() { // from class:
          // com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SystemDataTransferRequestStore.this.lambda$removeRequestsByAssociationId$3(
                i, readRequestsFromCache);
          }
        });
  }

  public static /* synthetic */ boolean lambda$removeRequestsByAssociationId$2(
      int i, SystemDataTransferRequest systemDataTransferRequest) {
    return systemDataTransferRequest.getAssociationId() == i;
  }

  public final ArrayList readRequestsFromCache(final int i) {
    ArrayList arrayList = (ArrayList) this.mCachedPerUser.get(i);
    if (arrayList == null) {
      try {
        arrayList =
            (ArrayList)
                this.mExecutor
                    .submit(
                        new Callable() { // from class:
                          // com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda5
                          @Override // java.util.concurrent.Callable
                          public final Object call() {
                            ArrayList lambda$readRequestsFromCache$4;
                            lambda$readRequestsFromCache$4 =
                                SystemDataTransferRequestStore.this.lambda$readRequestsFromCache$4(
                                    i);
                            return lambda$readRequestsFromCache$4;
                          }
                        })
                    .get(5L, TimeUnit.SECONDS);
      } catch (InterruptedException unused) {
        Slog.e(
            "CDM_SystemDataTransferRequestStore",
            "Thread reading SystemDataTransferRequest from disk is interrupted.");
      } catch (ExecutionException unused2) {
        Slog.e(
            "CDM_SystemDataTransferRequestStore",
            "Error occurred while reading SystemDataTransferRequest from disk.");
      } catch (TimeoutException unused3) {
        Slog.e(
            "CDM_SystemDataTransferRequestStore",
            "Reading SystemDataTransferRequest from disk timed out.");
      }
      this.mCachedPerUser.set(i, arrayList);
    }
    return arrayList;
  }

  /* renamed from: readRequestsFromStore, reason: merged with bridge method [inline-methods] */
  public final ArrayList lambda$readRequestsFromCache$4(int i) {
    AtomicFile storageFileForUser = getStorageFileForUser(i);
    Slog.i(
        "CDM_SystemDataTransferRequestStore",
        "Reading SystemDataTransferRequests for user "
            + i
            + " from file="
            + storageFileForUser.getBaseFile().getPath());
    synchronized (storageFileForUser) {
      if (!storageFileForUser.getBaseFile().exists()) {
        Slog.d("CDM_SystemDataTransferRequestStore", "File does not exist -> Abort");
        return new ArrayList();
      }
      try {
        FileInputStream openRead = storageFileForUser.openRead();
        try {
          TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
          XmlUtils.beginDocument(resolvePullParser, "requests");
          ArrayList readRequestsFromXml = readRequestsFromXml(resolvePullParser);
          if (openRead != null) {
            openRead.close();
          }
          return readRequestsFromXml;
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
        return new ArrayList();
      }
    }
  }

  public final ArrayList readRequestsFromXml(TypedXmlPullParser typedXmlPullParser) {
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
        arrayList.add(readRequestFromXml(typedXmlPullParser));
      }
    }
  }

  public final SystemDataTransferRequest readRequestFromXml(TypedXmlPullParser typedXmlPullParser) {
    if (!DataStoreUtils.isStartOfTag(typedXmlPullParser, "request")) {
      throw new XmlPullParserException("XML doesn't have start tag: request");
    }
    int readIntAttribute = XmlUtils.readIntAttribute(typedXmlPullParser, "association_id");
    int readIntAttribute2 = XmlUtils.readIntAttribute(typedXmlPullParser, "data_type");
    int readIntAttribute3 = XmlUtils.readIntAttribute(typedXmlPullParser, "user_id");
    boolean readBooleanAttribute =
        XmlUtils.readBooleanAttribute(typedXmlPullParser, "is_user_consented");
    if (readIntAttribute2 != 1) {
      return null;
    }
    PermissionSyncRequest permissionSyncRequest = new PermissionSyncRequest(readIntAttribute);
    permissionSyncRequest.setUserId(readIntAttribute3);
    permissionSyncRequest.setUserConsented(readBooleanAttribute);
    return permissionSyncRequest;
  }

  /* renamed from: writeRequestsToStore, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
  public void lambda$writeRequest$1(int i, final List list) {
    AtomicFile storageFileForUser = getStorageFileForUser(i);
    Slog.i(
        "CDM_SystemDataTransferRequestStore",
        "Writing SystemDataTransferRequests for user "
            + i
            + " to file="
            + storageFileForUser.getBaseFile().getPath());
    synchronized (storageFileForUser) {
      DataStoreUtils.writeToFileSafely(
          storageFileForUser,
          new FunctionalUtils.ThrowingConsumer() { // from class:
            // com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda4
            public final void acceptOrThrow(Object obj) {
              SystemDataTransferRequestStore.this.lambda$writeRequestsToStore$5(
                  list, (FileOutputStream) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$writeRequestsToStore$5(
      List list, FileOutputStream fileOutputStream) {
    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
    resolveSerializer.startDocument((String) null, Boolean.TRUE);
    writeRequestsToXml(resolveSerializer, list);
    resolveSerializer.endDocument();
  }

  public final void writeRequestsToXml(
      TypedXmlSerializer typedXmlSerializer, Collection collection) {
    typedXmlSerializer.startTag((String) null, "requests");
    Iterator it = collection.iterator();
    while (it.hasNext()) {
      writeRequestToXml(typedXmlSerializer, (SystemDataTransferRequest) it.next());
    }
    typedXmlSerializer.endTag((String) null, "requests");
  }

  public final void writeRequestToXml(
      TypedXmlSerializer typedXmlSerializer, SystemDataTransferRequest systemDataTransferRequest) {
    typedXmlSerializer.startTag((String) null, "request");
    XmlUtils.writeIntAttribute(
        typedXmlSerializer, "association_id", systemDataTransferRequest.getAssociationId());
    XmlUtils.writeIntAttribute(
        typedXmlSerializer, "data_type", systemDataTransferRequest.getDataType());
    XmlUtils.writeIntAttribute(
        typedXmlSerializer, "user_id", systemDataTransferRequest.getUserId());
    XmlUtils.writeBooleanAttribute(
        typedXmlSerializer, "is_user_consented", systemDataTransferRequest.isUserConsented());
    typedXmlSerializer.endTag((String) null, "request");
  }

  public final AtomicFile getStorageFileForUser(final int i) {
    return (AtomicFile)
        this.mUserIdToStorageFile.computeIfAbsent(
            Integer.valueOf(i),
            new Function() { // from class:
              // com.android.server.companion.datatransfer.SystemDataTransferRequestStore$$ExternalSyntheticLambda6
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                AtomicFile lambda$getStorageFileForUser$6;
                lambda$getStorageFileForUser$6 =
                    SystemDataTransferRequestStore.lambda$getStorageFileForUser$6(i, (Integer) obj);
                return lambda$getStorageFileForUser$6;
              }
            });
  }

  public static /* synthetic */ AtomicFile lambda$getStorageFileForUser$6(int i, Integer num) {
    return DataStoreUtils.createStorageFileForUser(
        i, "companion_device_system_data_transfer_requests.xml");
  }
}
