package android.content;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.app.jank.AppJankStats;
import android.content.pm.PackageManager;
import android.content.pm.PathPermission;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.multiuser.Flags;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ICancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseBooleanArray;
import com.android.internal.util.FrameworkStatsLog;
import com.samsung.android.app.SemDualAppManager;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class ContentProvider implements ContentInterface, ComponentCallbacks2 {
    private static final String TAG = "ContentProvider";
    private String[] mAuthorities;
    private String mAuthority;
    private ThreadLocal<AttributionSource> mCallingAttributionSource;
    private Context mContext;
    private boolean mExported;
    private int mMyUid;
    private boolean mNoPerms;
    private PathPermission[] mPathPermissions;
    private String mReadPermission;
    private boolean mSingleUser;
    private boolean mSystemUserOnly;
    private Transport mTransport;
    private SparseBooleanArray mUsersRedirectedToOwnerForMedia;
    private String mWritePermission;

    public interface PipeDataWriter<T> {
        void writeDataToPipe(ParcelFileDescriptor parcelFileDescriptor, Uri uri, String str, Bundle bundle, T t);
    }

    public Bundle call(String str, String str2, Bundle bundle) {
        return null;
    }

    @Override // android.content.ContentInterface
    public Uri canonicalize(Uri uri) {
        return null;
    }

    @Override // android.content.ContentInterface
    @SystemApi
    public int checkUriPermission(Uri uri, int i, int i2) {
        return -1;
    }

    public abstract int delete(Uri uri, String str, String[] strArr);

    @Override // android.content.ContentInterface
    public String[] getStreamTypes(Uri uri, String str) {
        return null;
    }

    @Override // android.content.ContentInterface
    public abstract String getType(Uri uri);

    public abstract Uri insert(Uri uri, ContentValues contentValues);

    protected boolean isTemporary() {
        return false;
    }

    public void onCallingPackageChanged() {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    public abstract boolean onCreate();

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
    }

    public abstract Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2);

    @Override // android.content.ContentInterface
    public boolean refresh(Uri uri, Bundle bundle, CancellationSignal cancellationSignal) {
        return false;
    }

    @Override // android.content.ContentInterface
    public Uri uncanonicalize(Uri uri) {
        return uri;
    }

    public abstract int update(Uri uri, ContentValues contentValues, String str, String[] strArr);

    public static boolean isAuthorityRedirectedForCloneProfile(String str) {
        return AppJankStats.WIDGET_CATEGORY_MEDIA.equals(str);
    }

    public ContentProvider() {
        this.mContext = null;
        this.mUsersRedirectedToOwnerForMedia = new SparseBooleanArray();
        this.mTransport = new Transport();
    }

    public ContentProvider(Context context, String str, String str2, PathPermission[] pathPermissionArr) {
        this.mContext = null;
        this.mUsersRedirectedToOwnerForMedia = new SparseBooleanArray();
        this.mTransport = new Transport();
        this.mContext = context;
        this.mReadPermission = str;
        this.mWritePermission = str2;
        this.mPathPermissions = pathPermissionArr;
    }

    public static ContentProvider coerceToLocalContentProvider(IContentProvider iContentProvider) {
        if (iContentProvider instanceof Transport) {
            return ((Transport) iContentProvider).getContentProvider();
        }
        return null;
    }

    class Transport extends ContentProviderNative {
        volatile ContentInterface mInterface;
        volatile AppOpsManager mAppOpsManager = null;
        volatile int mReadOp = -1;
        volatile int mWriteOp = -1;

        Transport() {
            this.mInterface = ContentProvider.this;
        }

        ContentProvider getContentProvider() {
            return ContentProvider.this;
        }

        @Override // android.content.ContentProviderNative
        public String getProviderName() {
            return getContentProvider().getClass().getName();
        }

        @Override // android.content.IContentProvider
        public Cursor query(AttributionSource attributionSource, Uri uri, String[] strArr, Bundle bundle, ICancellationSignal iCancellationSignal) {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceReadPermission(attributionSource, maybeGetUriWithoutUserId) == 0) {
                ContentProvider.traceBegin(64L, "query: ", maybeGetUriWithoutUserId.getAuthority());
                AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
                try {
                    try {
                        return this.mInterface.query(maybeGetUriWithoutUserId, strArr, bundle, CancellationSignal.fromTransport(iCancellationSignal));
                    } catch (RemoteException e) {
                        throw e.rethrowAsRuntimeException();
                    }
                } finally {
                    ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                    Trace.traceEnd(64L);
                }
            }
            if (strArr != null) {
                return new MatrixCursor(strArr, 0);
            }
            AttributionSource callingAttributionSource2 = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    Cursor query = this.mInterface.query(maybeGetUriWithoutUserId, strArr, bundle, CancellationSignal.fromTransport(iCancellationSignal));
                    if (query == null) {
                        return null;
                    }
                    return new MatrixCursor(query.getColumnNames(), 0);
                } finally {
                    ContentProvider.this.setCallingAttributionSource(callingAttributionSource2);
                }
            } catch (RemoteException e2) {
                throw e2.rethrowAsRuntimeException();
            }
        }

        @Override // android.content.IContentProvider
        public String getType(AttributionSource attributionSource, Uri uri) {
            CallingIdentity clearCallingIdentity;
            String type;
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "getType: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    if (checkGetTypePermission(attributionSource, maybeGetUriWithoutUserId) == 0) {
                        if (ContentProvider.this.checkPermission(Manifest.permission.GET_ANY_PROVIDER_TYPE, attributionSource) == 0) {
                            clearCallingIdentity = getContentProvider().clearCallingIdentity();
                            try {
                                type = this.mInterface.getType(maybeGetUriWithoutUserId);
                                getContentProvider().restoreCallingIdentity(clearCallingIdentity);
                            } finally {
                            }
                        } else {
                            type = this.mInterface.getType(maybeGetUriWithoutUserId);
                        }
                        if (type != null) {
                            logGetTypeData(Binder.getCallingUid(), maybeGetUriWithoutUserId, type, true);
                        }
                        return type;
                    }
                    int callingUid = Binder.getCallingUid();
                    clearCallingIdentity = getContentProvider().clearCallingIdentity();
                    try {
                        String typeAnonymous = ContentProvider.this.getTypeAnonymous(maybeGetUriWithoutUserId);
                        if (typeAnonymous != null) {
                            logGetTypeData(callingUid, maybeGetUriWithoutUserId, typeAnonymous, false);
                        }
                        return typeAnonymous;
                    } finally {
                    }
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        private void logGetTypeData(int i, Uri uri, String str, boolean z) {
            if (z) {
                try {
                    ProviderInfo resolveContentProvider = ContentProvider.this.mContext.getPackageManager().resolveContentProvider(uri.getAuthority(), PackageManager.ComponentInfoFlags.of(128L));
                    int userId = UserHandle.getUserId(i);
                    Uri maybeAddUserId = (!ContentProvider.this.mSingleUser || UserHandle.isSameUser(ContentProvider.this.mMyUid, i)) ? uri : ContentProvider.maybeAddUserId(uri, userId);
                    if (!resolveContentProvider.forceUriPermissions || this.mInterface.checkUriPermission(uri, i, 1) == 0 || ContentProvider.this.getContext().checkUriPermission(maybeAddUserId, Binder.getCallingPid(), i, 1) == 0 || ContentProvider.deniedAccessSystemUserOnlyProvider(userId, ContentProvider.this.mSystemUserOnly)) {
                        return;
                    }
                    FrameworkStatsLog.write(564, 5, i, uri.getAuthority(), str);
                    return;
                } catch (Exception unused) {
                    return;
                }
            }
            FrameworkStatsLog.write(564, 4, i, uri.getAuthority(), str);
        }

        @Override // android.content.IContentProvider
        public void getTypeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) {
            Bundle bundle = new Bundle();
            try {
                bundle.putString("result", getType(attributionSource, uri));
            } catch (Exception e) {
                bundle.putParcelable("error", new ParcelableException(e));
            }
            remoteCallback.sendResult(bundle);
        }

        @Override // android.content.IContentProvider
        public void getTypeAnonymousAsync(Uri uri, RemoteCallback remoteCallback) {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "getTypeAnonymous: ", maybeGetUriWithoutUserId.getAuthority());
            Bundle bundle = new Bundle();
            try {
                bundle.putString("result", ContentProvider.this.getTypeAnonymous(maybeGetUriWithoutUserId));
            } catch (Exception e) {
                bundle.putParcelable("error", new ParcelableException(e));
            } finally {
                remoteCallback.sendResult(bundle);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public Uri insert(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) {
            Uri validateIncomingUri = ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = ContentProvider.getUserIdFromUri(validateIncomingUri);
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(validateIncomingUri);
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
                try {
                    return ContentProvider.this.rejectInsert(maybeGetUriWithoutUserId, contentValues);
                } finally {
                    ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                }
            }
            ContentProvider.traceBegin(64L, "insert: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource2 = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return ContentProvider.maybeAddUserId(this.mInterface.insert(maybeGetUriWithoutUserId, contentValues, bundle), userIdFromUri);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource2);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int bulkInsert(AttributionSource attributionSource, Uri uri, ContentValues[] contentValuesArr) {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            ContentProvider.traceBegin(64L, "bulkInsert: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.bulkInsert(maybeGetUriWithoutUserId, contentValuesArr);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public ContentProviderResult[] applyBatch(AttributionSource attributionSource, String str, ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
            ContentProvider.this.validateIncomingAuthority(str);
            int size = arrayList.size();
            int[] iArr = new int[size];
            ArraySet arraySet = new ArraySet();
            ArraySet arraySet2 = new ArraySet();
            for (int i = 0; i < size; i++) {
                ContentProviderOperation contentProviderOperation = arrayList.get(i);
                Uri uri = contentProviderOperation.getUri();
                iArr[i] = ContentProvider.getUserIdFromUri(uri);
                Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
                if (!Objects.equals(contentProviderOperation.getUri(), maybeGetUriWithoutUserId)) {
                    ContentProviderOperation contentProviderOperation2 = new ContentProviderOperation(contentProviderOperation, maybeGetUriWithoutUserId);
                    arrayList.set(i, contentProviderOperation2);
                    contentProviderOperation = contentProviderOperation2;
                }
                if (contentProviderOperation.isReadOperation() && !arraySet.contains(maybeGetUriWithoutUserId.toString())) {
                    if (enforceReadPermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                        throw new OperationApplicationException("App op not allowed", 0);
                    }
                    arraySet.add(maybeGetUriWithoutUserId.toString());
                }
                if (contentProviderOperation.isWriteOperation() && !arraySet2.contains(maybeGetUriWithoutUserId.toString())) {
                    if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                        throw new OperationApplicationException("App op not allowed", 0);
                    }
                    arraySet2.add(maybeGetUriWithoutUserId.toString());
                }
            }
            ContentProvider.traceBegin(64L, "applyBatch: ", str);
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    ContentProviderResult[] applyBatch = this.mInterface.applyBatch(str, arrayList);
                    if (applyBatch != null) {
                        for (int i2 = 0; i2 < applyBatch.length; i2++) {
                            if (iArr[i2] != -2) {
                                applyBatch[i2] = new ContentProviderResult(applyBatch[i2], iArr[i2]);
                            }
                        }
                    }
                    return applyBatch;
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int delete(AttributionSource attributionSource, Uri uri, Bundle bundle) {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            ContentProvider.traceBegin(64L, "delete: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.delete(maybeGetUriWithoutUserId, bundle);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int update(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            ContentProvider.traceBegin(64L, "update: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.update(maybeGetUriWithoutUserId, contentValues, bundle);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public ParcelFileDescriptor openFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws FileNotFoundException {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, maybeGetUriWithoutUserId, str);
            ContentProvider.traceBegin(64L, "openFile: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openFile(maybeGetUriWithoutUserId, str, CancellationSignal.fromTransport(iCancellationSignal));
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public AssetFileDescriptor openAssetFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws FileNotFoundException {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, maybeGetUriWithoutUserId, str);
            ContentProvider.traceBegin(64L, "openAssetFile: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openAssetFile(maybeGetUriWithoutUserId, str, CancellationSignal.fromTransport(iCancellationSignal));
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public Bundle call(AttributionSource attributionSource, String str, String str2, String str3, Bundle bundle) {
            ContentProvider.this.validateIncomingAuthority(str);
            Bundle.setDefusable(bundle, true);
            ContentProvider.traceBegin(64L, "call: ", str);
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.call(str, str2, str3, bundle);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public String[] getStreamTypes(AttributionSource attributionSource, Uri uri, String str) {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "getStreamTypes: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.getStreamTypes(maybeGetUriWithoutUserId, str);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public AssetFileDescriptor openTypedAssetFile(AttributionSource attributionSource, Uri uri, String str, Bundle bundle, ICancellationSignal iCancellationSignal) throws FileNotFoundException {
            Bundle.setDefusable(bundle, true);
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, maybeGetUriWithoutUserId, "r");
            ContentProvider.traceBegin(64L, "openTypedAssetFile: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openTypedAssetFile(maybeGetUriWithoutUserId, str, bundle, CancellationSignal.fromTransport(iCancellationSignal));
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public ICancellationSignal createCancellationSignal() {
            return CancellationSignal.createTransport();
        }

        @Override // android.content.IContentProvider
        public Uri canonicalize(AttributionSource attributionSource, Uri uri) {
            Uri validateIncomingUri = ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = ContentProvider.getUserIdFromUri(validateIncomingUri);
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(validateIncomingUri);
            if (enforceReadPermission(attributionSource, uriWithoutUserId) != 0) {
                return null;
            }
            ContentProvider.traceBegin(64L, "canonicalize: ", uriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return ContentProvider.maybeAddUserId(this.mInterface.canonicalize(uriWithoutUserId), userIdFromUri);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public void canonicalizeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) {
            Bundle bundle = new Bundle();
            try {
                bundle.putParcelable("result", canonicalize(attributionSource, uri));
            } catch (Exception e) {
                bundle.putParcelable("error", new ParcelableException(e));
            }
            remoteCallback.sendResult(bundle);
        }

        @Override // android.content.IContentProvider
        public Uri uncanonicalize(AttributionSource attributionSource, Uri uri) {
            Uri validateIncomingUri = ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = ContentProvider.getUserIdFromUri(validateIncomingUri);
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(validateIncomingUri);
            if (enforceReadPermission(attributionSource, uriWithoutUserId) != 0) {
                return null;
            }
            ContentProvider.traceBegin(64L, "uncanonicalize: ", uriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return ContentProvider.maybeAddUserId(this.mInterface.uncanonicalize(uriWithoutUserId), userIdFromUri);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public void uncanonicalizeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) {
            Bundle bundle = new Bundle();
            try {
                bundle.putParcelable("result", uncanonicalize(attributionSource, uri));
            } catch (Exception e) {
                bundle.putParcelable("error", new ParcelableException(e));
            }
            remoteCallback.sendResult(bundle);
        }

        @Override // android.content.IContentProvider
        public boolean refresh(AttributionSource attributionSource, Uri uri, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException {
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceReadPermission(attributionSource, uriWithoutUserId) != 0) {
                return false;
            }
            ContentProvider.traceBegin(64L, "refresh: ", uriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                return this.mInterface.refresh(uriWithoutUserId, bundle, CancellationSignal.fromTransport(iCancellationSignal));
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int checkUriPermission(AttributionSource attributionSource, Uri uri, int i, int i2) {
            Uri maybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "checkUriPermission: ", maybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.checkUriPermission(maybeGetUriWithoutUserId, i, i2);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        private void enforceFilePermission(AttributionSource attributionSource, Uri uri, String str) throws FileNotFoundException, SecurityException {
            if (str != null && str.indexOf(119) != -1) {
                if (enforceWritePermission(attributionSource, uri) != 0) {
                    throw new FileNotFoundException("App op not allowed");
                }
            } else if (enforceReadPermission(attributionSource, uri) != 0) {
                throw new FileNotFoundException("App op not allowed");
            }
        }

        private int enforceReadPermission(AttributionSource attributionSource, Uri uri) throws SecurityException {
            int semEnforceReadPermission = ContentProvider.this.semEnforceReadPermission(uri, attributionSource);
            if (semEnforceReadPermission != 0) {
                return semEnforceReadPermission;
            }
            if (ContentProvider.this.mTransport.mReadOp == -1 || ContentProvider.this.mTransport.mReadOp == AppOpsManager.permissionToOpCode(ContentProvider.this.mReadPermission)) {
                return 0;
            }
            return PermissionChecker.checkOpForDataDelivery(ContentProvider.this.getContext(), AppOpsManager.opToPublicName(ContentProvider.this.mTransport.mReadOp), attributionSource, null);
        }

        private int enforceWritePermission(AttributionSource attributionSource, Uri uri) throws SecurityException {
            int semEnforceWritePermission = ContentProvider.this.semEnforceWritePermission(uri, attributionSource);
            if (semEnforceWritePermission != 0) {
                return semEnforceWritePermission;
            }
            if (ContentProvider.this.mTransport.mWriteOp == -1 || ContentProvider.this.mTransport.mWriteOp == AppOpsManager.permissionToOpCode(ContentProvider.this.mWritePermission)) {
                return 0;
            }
            return PermissionChecker.checkOpForDataDelivery(ContentProvider.this.getContext(), AppOpsManager.opToPublicName(ContentProvider.this.mTransport.mWriteOp), attributionSource, null);
        }

        private int checkGetTypePermission(AttributionSource attributionSource, Uri uri) {
            if (UserHandle.getAppId(Binder.getCallingUid()) == 1000 || ContentProvider.this.checkPermission(Manifest.permission.GET_ANY_PROVIDER_TYPE, attributionSource) == 0) {
                return 0;
            }
            try {
                return enforceReadPermission(attributionSource, uri);
            } catch (SecurityException unused) {
                return 2;
            }
        }
    }

    boolean checkUser(int i, int i2, Context context) {
        int userId = UserHandle.getUserId(i2);
        if (deniedAccessSystemUserOnlyProvider(userId, this.mSystemUserOnly)) {
            return false;
        }
        if (userId == context.getUserId() || this.mSingleUser || ((SemDualAppManager.isDualAppId(UserHandle.getUserId(i2)) && context.getUserId() == 0) || ((SemDualAppManager.isDualAppId(context.getUserId()) && UserHandle.getUserId(i2) == 0) || context.checkPermission(Manifest.permission.INTERACT_ACROSS_USERS, i, i2) == 0 || context.checkPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL, i, i2) == 0))) {
            return true;
        }
        return isContentRedirectionAllowedForUser(userId);
    }

    private boolean isContentRedirectionAllowedForUser(int i) {
        UserHandle profileParent;
        if (AppJankStats.WIDGET_CATEGORY_MEDIA.equals(this.mAuthority)) {
            int indexOfKey = this.mUsersRedirectedToOwnerForMedia.indexOfKey(i);
            if (indexOfKey >= 0) {
                return this.mUsersRedirectedToOwnerForMedia.valueAt(indexOfKey);
            }
            UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
            if (userManager != null && userManager.getUserProperties(UserHandle.of(i)).isMediaSharedWithParent() && (profileParent = userManager.getProfileParent(UserHandle.of(i))) != null && profileParent.equals(Process.myUserHandle())) {
                this.mUsersRedirectedToOwnerForMedia.put(i, true);
                return true;
            }
            this.mUsersRedirectedToOwnerForMedia.put(i, false);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int checkPermission(String str, AttributionSource attributionSource) {
        if (Binder.getCallingPid() == Process.myPid()) {
            return 0;
        }
        return PermissionChecker.checkPermissionForDataDeliveryFromDataSource(getContext(), str, -1, new AttributionSource(getContext().getAttributionSource(), attributionSource), null);
    }

    protected int semEnforceReadPermission(Uri uri, AttributionSource attributionSource) throws SecurityException {
        return enforceReadPermissionInner(uri, attributionSource);
    }

    protected int enforceReadPermissionInner(Uri uri, AttributionSource attributionSource) throws SecurityException {
        int i;
        int i2;
        String str;
        Context context = getContext();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        int i3 = 0;
        if (UserHandle.isSameApp(callingUid, this.mMyUid)) {
            return 0;
        }
        String str2 = null;
        if (this.mExported && checkUser(callingPid, callingUid, context)) {
            String readPermission = getReadPermission();
            if (readPermission != null) {
                int checkPermission = checkPermission(readPermission, attributionSource);
                if (checkPermission == 0) {
                    return 0;
                }
                i2 = Math.max(0, checkPermission);
                str2 = readPermission;
            } else {
                i2 = 0;
            }
            int i4 = readPermission == null ? 1 : 0;
            PathPermission[] pathPermissions = getPathPermissions();
            if (pathPermissions != null) {
                String path = uri.getPath();
                int length = pathPermissions.length;
                int i5 = 0;
                while (i5 < length) {
                    PathPermission pathPermission = pathPermissions[i5];
                    int i6 = i3;
                    String readPermission2 = pathPermission.getReadPermission();
                    if (readPermission2 != null && pathPermission.match(path)) {
                        int checkPermission2 = checkPermission(readPermission2, attributionSource);
                        if (checkPermission2 == 0) {
                            return i6;
                        }
                        i2 = Math.max(i2, checkPermission2);
                        str2 = readPermission2;
                        i4 = i6;
                    }
                    i5++;
                    i3 = i6;
                }
            }
            i = i3;
            if (i4 != 0) {
                return i;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        int userId = UserHandle.getUserId(callingUid);
        if (deniedAccessSystemUserOnlyProvider(userId, this.mSystemUserOnly)) {
            return 2;
        }
        if (context.checkUriPermission((!this.mSingleUser || UserHandle.isSameUser(this.mMyUid, callingUid)) ? uri : maybeAddUserId(uri, userId), callingPid, callingUid, 1) == 0) {
            return i;
        }
        if (i2 == 1) {
            return 1;
        }
        if (Manifest.permission.MANAGE_DOCUMENTS.equals(this.mReadPermission)) {
            str = " requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs";
        } else if (this.mExported) {
            str = " requires " + str2 + ", or grantUriPermission()";
        } else {
            str = " requires the provider be exported, or grantUriPermission()";
        }
        throw new SecurityException("Permission Denial: reading " + getClass().getName() + " uri " + uri + " from pid=" + callingPid + ", uid=" + callingUid + str);
    }

    protected int semEnforceWritePermission(Uri uri, AttributionSource attributionSource) throws SecurityException {
        return enforceWritePermissionInner(uri, attributionSource);
    }

    protected int enforceWritePermissionInner(Uri uri, AttributionSource attributionSource) throws SecurityException {
        int i;
        int i2;
        String str;
        Context context = getContext();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        int i3 = 0;
        if (UserHandle.isSameApp(callingUid, this.mMyUid)) {
            return 0;
        }
        String str2 = null;
        if (this.mExported && checkUser(callingPid, callingUid, context)) {
            String writePermission = getWritePermission();
            if (writePermission != null) {
                int checkPermission = checkPermission(writePermission, attributionSource);
                if (checkPermission == 0) {
                    return 0;
                }
                i2 = Math.max(0, checkPermission);
                str2 = writePermission;
            } else {
                i2 = 0;
            }
            int i4 = writePermission == null ? 1 : 0;
            PathPermission[] pathPermissions = getPathPermissions();
            if (pathPermissions != null) {
                String path = uri.getPath();
                int length = pathPermissions.length;
                int i5 = 0;
                while (i5 < length) {
                    PathPermission pathPermission = pathPermissions[i5];
                    int i6 = i3;
                    String writePermission2 = pathPermission.getWritePermission();
                    if (writePermission2 != null && pathPermission.match(path)) {
                        int checkPermission2 = checkPermission(writePermission2, attributionSource);
                        if (checkPermission2 == 0) {
                            return i6;
                        }
                        i2 = Math.max(i2, checkPermission2);
                        str2 = writePermission2;
                        i4 = i6;
                    }
                    i5++;
                    i3 = i6;
                }
            }
            i = i3;
            if (i4 != 0) {
                return i;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        if (context.checkUriPermission(uri, callingPid, callingUid, 2) == 0) {
            return i;
        }
        if (i2 == 1) {
            return 1;
        }
        if (this.mExported) {
            str = " requires " + str2 + ", or grantUriPermission()";
        } else {
            str = " requires the provider be exported, or grantUriPermission()";
        }
        throw new SecurityException("Permission Denial: writing " + getClass().getName() + " uri " + uri + " from pid=" + callingPid + ", uid=" + callingUid + str);
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Context requireContext() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("Cannot find context from the provider.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AttributionSource setCallingAttributionSource(AttributionSource attributionSource) {
        AttributionSource attributionSource2 = this.mCallingAttributionSource.get();
        this.mCallingAttributionSource.set(attributionSource);
        onCallingPackageChanged();
        return attributionSource2;
    }

    public final String getCallingPackage() {
        AttributionSource callingAttributionSource = getCallingAttributionSource();
        if (callingAttributionSource != null) {
            return callingAttributionSource.getPackageName();
        }
        return null;
    }

    public final AttributionSource getCallingAttributionSource() {
        AttributionSource attributionSource = this.mCallingAttributionSource.get();
        if (attributionSource != null) {
            this.mTransport.mAppOpsManager.checkPackage(Binder.getCallingUid(), attributionSource.getPackageName());
        }
        return attributionSource;
    }

    public final String getCallingAttributionTag() {
        AttributionSource attributionSource = this.mCallingAttributionSource.get();
        if (attributionSource != null) {
            return attributionSource.getAttributionTag();
        }
        return null;
    }

    @Deprecated
    public final String getCallingFeatureId() {
        return getCallingAttributionTag();
    }

    public final String getCallingPackageUnchecked() {
        AttributionSource attributionSource = this.mCallingAttributionSource.get();
        if (attributionSource != null) {
            return attributionSource.getPackageName();
        }
        return null;
    }

    public final class CallingIdentity {
        public final long binderToken;
        public final AttributionSource callingAttributionSource;

        public CallingIdentity(ContentProvider contentProvider, long j, AttributionSource attributionSource) {
            this.binderToken = j;
            this.callingAttributionSource = attributionSource;
        }
    }

    public final CallingIdentity clearCallingIdentity() {
        return new CallingIdentity(this, Binder.clearCallingIdentity(), setCallingAttributionSource(null));
    }

    public final void restoreCallingIdentity(CallingIdentity callingIdentity) {
        Binder.restoreCallingIdentity(callingIdentity.binderToken);
        this.mCallingAttributionSource.set(callingIdentity.callingAttributionSource);
    }

    protected final void setAuthorities(String str) {
        if (str != null) {
            if (str.indexOf(59) == -1) {
                this.mAuthority = str;
                this.mAuthorities = null;
            } else {
                this.mAuthority = null;
                this.mAuthorities = str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
        }
    }

    protected final boolean matchesOurAuthorities(String str) {
        String str2 = this.mAuthority;
        if (str2 != null) {
            return str2.equals(str);
        }
        String[] strArr = this.mAuthorities;
        if (strArr != null) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                if (this.mAuthorities[i].equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected final void setReadPermission(String str) {
        this.mReadPermission = str;
    }

    public final String getReadPermission() {
        return this.mReadPermission;
    }

    protected final void setWritePermission(String str) {
        this.mWritePermission = str;
    }

    public final String getWritePermission() {
        return this.mWritePermission;
    }

    protected final void setPathPermissions(PathPermission[] pathPermissionArr) {
        this.mPathPermissions = pathPermissionArr;
    }

    public final PathPermission[] getPathPermissions() {
        return this.mPathPermissions;
    }

    public final void setAppOps(int i, int i2) {
        if (this.mNoPerms) {
            return;
        }
        this.mTransport.mReadOp = i;
        this.mTransport.mWriteOp = i2;
    }

    public AppOpsManager getAppOpsManager() {
        return this.mTransport.mAppOpsManager;
    }

    public final void setTransportLoggingEnabled(boolean z) {
        Transport transport = this.mTransport;
        if (transport == null) {
            return;
        }
        if (z) {
            transport.mInterface = new LoggingContentInterface(getClass().getSimpleName(), this);
        } else {
            transport.mInterface = this;
        }
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        return query(uri, strArr, str, strArr2, str2);
    }

    @Override // android.content.ContentInterface
    public Cursor query(Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) {
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        String string = bundle.getString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER);
        if (string == null && bundle.containsKey(ContentResolver.QUERY_ARG_SORT_COLUMNS)) {
            string = ContentResolver.createSqlSortClause(bundle);
        }
        return query(uri, strArr, bundle.getString(ContentResolver.QUERY_ARG_SQL_SELECTION), bundle.getStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS), string, cancellationSignal);
    }

    public String getTypeAnonymous(Uri uri) {
        return getType(uri);
    }

    public Uri rejectInsert(Uri uri, ContentValues contentValues) {
        return uri.buildUpon().appendPath("0").build();
    }

    @Override // android.content.ContentInterface
    public Uri insert(Uri uri, ContentValues contentValues, Bundle bundle) {
        return insert(uri, contentValues);
    }

    @Override // android.content.ContentInterface
    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        int length = contentValuesArr.length;
        for (ContentValues contentValues : contentValuesArr) {
            insert(uri, contentValues);
        }
        return length;
    }

    @Override // android.content.ContentInterface
    public int delete(Uri uri, Bundle bundle) {
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        return delete(uri, bundle.getString(ContentResolver.QUERY_ARG_SQL_SELECTION), bundle.getStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS));
    }

    @Override // android.content.ContentInterface
    public int update(Uri uri, ContentValues contentValues, Bundle bundle) {
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        return update(uri, contentValues, bundle.getString(ContentResolver.QUERY_ARG_SQL_SELECTION), bundle.getStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS));
    }

    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        throw new FileNotFoundException("No files supported by provider at " + uri);
    }

    @Override // android.content.ContentInterface
    public ParcelFileDescriptor openFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return openFile(uri, str);
    }

    public AssetFileDescriptor openAssetFile(Uri uri, String str) throws FileNotFoundException {
        ParcelFileDescriptor openFile = openFile(uri, str);
        if (openFile != null) {
            return new AssetFileDescriptor(openFile, 0L, -1L);
        }
        return null;
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return openAssetFile(uri, str);
    }

    protected final ParcelFileDescriptor openFileHelper(Uri uri, String str) throws FileNotFoundException {
        Cursor query = query(uri, new String[]{"_data"}, null, null, null);
        int count = query != null ? query.getCount() : 0;
        if (count == 1) {
            query.moveToFirst();
            int columnIndex = query.getColumnIndex("_data");
            String string = columnIndex >= 0 ? query.getString(columnIndex) : null;
            query.close();
            if (string == null) {
                throw new FileNotFoundException("Column _data not found.");
            }
            return ParcelFileDescriptor.open(new File(string), ParcelFileDescriptor.parseMode(str));
        }
        if (query != null) {
            query.close();
        }
        if (count == 0) {
            throw new FileNotFoundException("No entry for " + uri);
        }
        throw new FileNotFoundException("Multiple items at " + uri);
    }

    public AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle) throws FileNotFoundException {
        if ("*/*".equals(str)) {
            return openAssetFile(uri, "r");
        }
        String type = getType(uri);
        if (type != null && ClipDescription.compareMimeTypes(type, str)) {
            return openAssetFile(uri, "r");
        }
        throw new FileNotFoundException("Can't open " + uri + " as type " + str);
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return openTypedAssetFile(uri, str, bundle);
    }

    public <T> ParcelFileDescriptor openPipeHelper(final Uri uri, final String str, final Bundle bundle, final T t, final PipeDataWriter<T> pipeDataWriter) throws FileNotFoundException {
        try {
            final ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            new AsyncTask<Object, Object, Object>(this) { // from class: android.content.ContentProvider.1
                @Override // android.os.AsyncTask
                protected Object doInBackground(Object... objArr) {
                    pipeDataWriter.writeDataToPipe(createPipe[1], uri, str, bundle, t);
                    try {
                        createPipe[1].close();
                        return null;
                    } catch (IOException e) {
                        Log.w(ContentProvider.TAG, "Failure closing pipe", e);
                        return null;
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            return createPipe[0];
        } catch (IOException unused) {
            throw new FileNotFoundException("failure making pipe");
        }
    }

    public IContentProvider getIContentProvider() {
        return this.mTransport;
    }

    public void attachInfoForTesting(Context context, ProviderInfo providerInfo) {
        attachInfo(context, providerInfo, true);
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        attachInfo(context, providerInfo, false);
    }

    private void attachInfo(Context context, ProviderInfo providerInfo, boolean z) {
        Transport transport;
        this.mNoPerms = z;
        this.mCallingAttributionSource = new ThreadLocal<>();
        if (this.mContext == null) {
            this.mContext = context;
            if (context != null && (transport = this.mTransport) != null) {
                transport.mAppOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            }
            this.mMyUid = Process.myUid();
            if (providerInfo != null) {
                setReadPermission(providerInfo.readPermission);
                setWritePermission(providerInfo.writePermission);
                setPathPermissions(providerInfo.pathPermissions);
                this.mExported = providerInfo.exported;
                this.mSingleUser = (providerInfo.flags & 1073741824) != 0;
                this.mSystemUserOnly = (providerInfo.flags & 536870912) != 0;
                setAuthorities(providerInfo.authority);
            }
            if (Build.IS_DEBUGGABLE) {
                setTransportLoggingEnabled(Log.isLoggable(getClass().getSimpleName(), 2));
            }
            onCreate();
        }
    }

    @Override // android.content.ContentInterface
    public ContentProviderResult[] applyBatch(String str, ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        return applyBatch(arrayList);
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        int size = arrayList.size();
        ContentProviderResult[] contentProviderResultArr = new ContentProviderResult[size];
        for (int i = 0; i < size; i++) {
            contentProviderResultArr[i] = arrayList.get(i).apply(this, contentProviderResultArr, i);
        }
        return contentProviderResultArr;
    }

    @Override // android.content.ContentInterface
    public Bundle call(String str, String str2, String str3, Bundle bundle) {
        return call(str2, str3, bundle);
    }

    public void shutdown() {
        Log.w(TAG, "implement ContentProvider shutdown() to make sure all database connections are gracefully shutdown");
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("nothing to dump");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateIncomingAuthority(String str) throws SecurityException {
        String str2;
        if (matchesOurAuthorities(getAuthorityWithoutUserId(str))) {
            return;
        }
        String str3 = "The authority " + str + " does not match the one of the contentProvider: ";
        if (this.mAuthority != null) {
            str2 = str3 + this.mAuthority;
        } else {
            str2 = str3 + Arrays.toString(this.mAuthorities);
        }
        throw new SecurityException(str2);
    }

    public Uri validateIncomingUri(Uri uri) throws SecurityException {
        String authority = uri.getAuthority();
        if (!this.mSingleUser) {
            int userIdFromAuthority = getUserIdFromAuthority(authority, -2);
            if (deniedAccessSystemUserOnlyProvider(this.mContext.getUserId(), this.mSystemUserOnly)) {
                throw new SecurityException("Trying to query a SYSTEM user only content provider from user:" + this.mContext.getUserId());
            }
            if (userIdFromAuthority != -2 && ((!Flags.fixGetUserPropertyCache() || userIdFromAuthority != -10000) && userIdFromAuthority != this.mContext.getUserId() && !isContentRedirectionAllowedForUser(userIdFromAuthority))) {
                throw new SecurityException("trying to query a ContentProvider in user " + this.mContext.getUserId() + " with a uri belonging to user " + userIdFromAuthority);
            }
        }
        validateIncomingAuthority(authority);
        String encodedPath = uri.getEncodedPath();
        if (encodedPath == null || encodedPath.indexOf("//") == -1) {
            return uri;
        }
        Uri build = uri.buildUpon().encodedPath(encodedPath.replaceAll("//+", "/")).build();
        Log.w(TAG, "Normalized " + uri + " to " + build + " to avoid possible security issues");
        return build;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Uri maybeGetUriWithoutUserId(Uri uri) {
        return this.mSingleUser ? uri : getUriWithoutUserId(uri);
    }

    public static int getUserIdFromAuthority(String str, int i) {
        int lastIndexOf;
        if (str == null || (lastIndexOf = str.lastIndexOf(64)) == -1) {
            return i;
        }
        try {
            return Integer.parseInt(str.substring(0, lastIndexOf));
        } catch (NumberFormatException e) {
            Log.w(TAG, "Error parsing userId.", e);
            return -10000;
        }
    }

    public static int getUserIdFromAuthority(String str) {
        return getUserIdFromAuthority(str, -2);
    }

    public static int getUserIdFromUri(Uri uri, int i) {
        return uri == null ? i : getUserIdFromAuthority(uri.getAuthority(), i);
    }

    public static int getUserIdFromUri(Uri uri) {
        return getUserIdFromUri(uri, -2);
    }

    public static UserHandle getUserHandleFromUri(Uri uri) {
        return UserHandle.of(getUserIdFromUri(uri, Process.myUserHandle().getIdentifier()));
    }

    public static String getAuthorityWithoutUserId(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(str.lastIndexOf(64) + 1);
    }

    public static Uri getUriWithoutUserId(Uri uri) {
        if (uri == null) {
            return null;
        }
        Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.authority(getAuthorityWithoutUserId(uri.getAuthority()));
        return buildUpon.build();
    }

    public static boolean uriHasUserId(Uri uri) {
        if (uri == null) {
            return false;
        }
        return !TextUtils.isEmpty(uri.getUserInfo());
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static Uri createContentUriForUser(Uri uri, UserHandle userHandle) {
        if (!"content".equals(uri.getScheme())) {
            throw new IllegalArgumentException(String.format("Given URI [%s] is not a content URI: ", uri));
        }
        int identifier = userHandle.getIdentifier();
        if (uriHasUserId(uri)) {
            if (String.valueOf(identifier).equals(uri.getUserInfo())) {
                return uri;
            }
            throw new IllegalArgumentException(String.format("Given URI [%s] already has a user ID, different from given user handle [%s]", uri, Integer.valueOf(identifier)));
        }
        Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.encodedAuthority("" + userHandle.getIdentifier() + "@" + uri.getEncodedAuthority());
        return buildUpon.build();
    }

    public static Uri maybeAddUserId(Uri uri, int i) {
        if (uri == null) {
            return null;
        }
        if (i == -2 || !"content".equals(uri.getScheme()) || uriHasUserId(uri)) {
            return uri;
        }
        Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.encodedAuthority("" + i + "@" + uri.getEncodedAuthority());
        return buildUpon.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void traceBegin(long j, String str, String str2) {
        if (Trace.isTagEnabled(j)) {
            Trace.traceBegin(j, str + str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean deniedAccessSystemUserOnlyProvider(int i, boolean z) {
        return Flags.enableSystemUserOnlyForServicesAndProviders() && i != 0 && z;
    }
}
