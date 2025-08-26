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
        public Cursor query(AttributionSource attributionSource, Uri uri, String[] strArr, Bundle bundle, ICancellationSignal iCancellationSignal) throws SecurityException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceReadPermission(attributionSource, uriMaybeGetUriWithoutUserId) == 0) {
                ContentProvider.traceBegin(64L, "query: ", uriMaybeGetUriWithoutUserId.getAuthority());
                AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
                try {
                    try {
                        return this.mInterface.query(uriMaybeGetUriWithoutUserId, strArr, bundle, CancellationSignal.fromTransport(iCancellationSignal));
                    } finally {
                        ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                        Trace.traceEnd(64L);
                    }
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }
            if (strArr != null) {
                return new MatrixCursor(strArr, 0);
            }
            AttributionSource callingAttributionSource2 = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    Cursor cursorQuery = this.mInterface.query(uriMaybeGetUriWithoutUserId, strArr, bundle, CancellationSignal.fromTransport(iCancellationSignal));
                    if (cursorQuery == null) {
                        return null;
                    }
                    return new MatrixCursor(cursorQuery.getColumnNames(), 0);
                } catch (RemoteException e2) {
                    throw e2.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource2);
            }
        }

        @Override // android.content.IContentProvider
        public String getType(AttributionSource attributionSource, Uri uri) throws SecurityException {
            CallingIdentity callingIdentityClearCallingIdentity;
            String type;
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "getType: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    if (checkGetTypePermission(attributionSource, uriMaybeGetUriWithoutUserId) == 0) {
                        if (ContentProvider.this.checkPermission(Manifest.permission.GET_ANY_PROVIDER_TYPE, attributionSource) == 0) {
                            callingIdentityClearCallingIdentity = getContentProvider().clearCallingIdentity();
                            try {
                                type = this.mInterface.getType(uriMaybeGetUriWithoutUserId);
                                getContentProvider().restoreCallingIdentity(callingIdentityClearCallingIdentity);
                            } finally {
                            }
                        } else {
                            type = this.mInterface.getType(uriMaybeGetUriWithoutUserId);
                        }
                        if (type != null) {
                            logGetTypeData(Binder.getCallingUid(), uriMaybeGetUriWithoutUserId, type, true);
                        }
                        return type;
                    }
                    int callingUid = Binder.getCallingUid();
                    callingIdentityClearCallingIdentity = getContentProvider().clearCallingIdentity();
                    try {
                        String typeAnonymous = ContentProvider.this.getTypeAnonymous(uriMaybeGetUriWithoutUserId);
                        if (typeAnonymous != null) {
                            logGetTypeData(callingUid, uriMaybeGetUriWithoutUserId, typeAnonymous, false);
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
                    ProviderInfo providerInfoResolveContentProvider = ContentProvider.this.mContext.getPackageManager().resolveContentProvider(uri.getAuthority(), PackageManager.ComponentInfoFlags.of(128L));
                    int userId = UserHandle.getUserId(i);
                    Uri uriMaybeAddUserId = (!ContentProvider.this.mSingleUser || UserHandle.isSameUser(ContentProvider.this.mMyUid, i)) ? uri : ContentProvider.maybeAddUserId(uri, userId);
                    if (!providerInfoResolveContentProvider.forceUriPermissions || this.mInterface.checkUriPermission(uri, i, 1) == 0 || ContentProvider.this.getContext().checkUriPermission(uriMaybeAddUserId, Binder.getCallingPid(), i, 1) == 0 || ContentProvider.deniedAccessSystemUserOnlyProvider(userId, ContentProvider.this.mSystemUserOnly)) {
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
        public void getTypeAnonymousAsync(Uri uri, RemoteCallback remoteCallback) throws SecurityException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "getTypeAnonymous: ", uriMaybeGetUriWithoutUserId.getAuthority());
            Bundle bundle = new Bundle();
            try {
                bundle.putString("result", ContentProvider.this.getTypeAnonymous(uriMaybeGetUriWithoutUserId));
            } catch (Exception e) {
                bundle.putParcelable("error", new ParcelableException(e));
            } finally {
                remoteCallback.sendResult(bundle);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public Uri insert(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) throws SecurityException {
            Uri uriValidateIncomingUri = ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = ContentProvider.getUserIdFromUri(uriValidateIncomingUri);
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(uriValidateIncomingUri);
            if (enforceWritePermission(attributionSource, uriMaybeGetUriWithoutUserId) != 0) {
                AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
                try {
                    return ContentProvider.this.rejectInsert(uriMaybeGetUriWithoutUserId, contentValues);
                } finally {
                    ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                }
            }
            ContentProvider.traceBegin(64L, "insert: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource2 = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return ContentProvider.maybeAddUserId(this.mInterface.insert(uriMaybeGetUriWithoutUserId, contentValues, bundle), userIdFromUri);
                } finally {
                    ContentProvider.this.setCallingAttributionSource(callingAttributionSource2);
                    Trace.traceEnd(64L);
                }
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        @Override // android.content.IContentProvider
        public int bulkInsert(AttributionSource attributionSource, Uri uri, ContentValues[] contentValuesArr) throws SecurityException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, uriMaybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            ContentProvider.traceBegin(64L, "bulkInsert: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.bulkInsert(uriMaybeGetUriWithoutUserId, contentValuesArr);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public ContentProviderResult[] applyBatch(AttributionSource attributionSource, String str, ArrayList<ContentProviderOperation> arrayList) throws SecurityException, OperationApplicationException {
            ContentProvider.this.validateIncomingAuthority(str);
            int size = arrayList.size();
            int[] iArr = new int[size];
            ArraySet arraySet = new ArraySet();
            ArraySet arraySet2 = new ArraySet();
            for (int i = 0; i < size; i++) {
                ContentProviderOperation contentProviderOperation = arrayList.get(i);
                Uri uri = contentProviderOperation.getUri();
                iArr[i] = ContentProvider.getUserIdFromUri(uri);
                Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
                if (!Objects.equals(contentProviderOperation.getUri(), uriMaybeGetUriWithoutUserId)) {
                    ContentProviderOperation contentProviderOperation2 = new ContentProviderOperation(contentProviderOperation, uriMaybeGetUriWithoutUserId);
                    arrayList.set(i, contentProviderOperation2);
                    contentProviderOperation = contentProviderOperation2;
                }
                if (contentProviderOperation.isReadOperation() && !arraySet.contains(uriMaybeGetUriWithoutUserId.toString())) {
                    if (enforceReadPermission(attributionSource, uriMaybeGetUriWithoutUserId) != 0) {
                        throw new OperationApplicationException("App op not allowed", 0);
                    }
                    arraySet.add(uriMaybeGetUriWithoutUserId.toString());
                }
                if (contentProviderOperation.isWriteOperation() && !arraySet2.contains(uriMaybeGetUriWithoutUserId.toString())) {
                    if (enforceWritePermission(attributionSource, uriMaybeGetUriWithoutUserId) != 0) {
                        throw new OperationApplicationException("App op not allowed", 0);
                    }
                    arraySet2.add(uriMaybeGetUriWithoutUserId.toString());
                }
            }
            ContentProvider.traceBegin(64L, "applyBatch: ", str);
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    ContentProviderResult[] contentProviderResultArrApplyBatch = this.mInterface.applyBatch(str, arrayList);
                    if (contentProviderResultArrApplyBatch != null) {
                        for (int i2 = 0; i2 < contentProviderResultArrApplyBatch.length; i2++) {
                            if (iArr[i2] != -2) {
                                contentProviderResultArrApplyBatch[i2] = new ContentProviderResult(contentProviderResultArrApplyBatch[i2], iArr[i2]);
                            }
                        }
                    }
                    return contentProviderResultArrApplyBatch;
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int delete(AttributionSource attributionSource, Uri uri, Bundle bundle) throws SecurityException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, uriMaybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            ContentProvider.traceBegin(64L, "delete: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.delete(uriMaybeGetUriWithoutUserId, bundle);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int update(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) throws SecurityException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, uriMaybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            ContentProvider.traceBegin(64L, "update: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.update(uriMaybeGetUriWithoutUserId, contentValues, bundle);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public ParcelFileDescriptor openFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws SecurityException, FileNotFoundException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, uriMaybeGetUriWithoutUserId, str);
            ContentProvider.traceBegin(64L, "openFile: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openFile(uriMaybeGetUriWithoutUserId, str, CancellationSignal.fromTransport(iCancellationSignal));
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public AssetFileDescriptor openAssetFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws SecurityException, FileNotFoundException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, uriMaybeGetUriWithoutUserId, str);
            ContentProvider.traceBegin(64L, "openAssetFile: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openAssetFile(uriMaybeGetUriWithoutUserId, str, CancellationSignal.fromTransport(iCancellationSignal));
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public Bundle call(AttributionSource attributionSource, String str, String str2, String str3, Bundle bundle) throws SecurityException {
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
        public String[] getStreamTypes(AttributionSource attributionSource, Uri uri, String str) throws SecurityException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "getStreamTypes: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.getStreamTypes(uriMaybeGetUriWithoutUserId, str);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public AssetFileDescriptor openTypedAssetFile(AttributionSource attributionSource, Uri uri, String str, Bundle bundle, ICancellationSignal iCancellationSignal) throws SecurityException, FileNotFoundException {
            Bundle.setDefusable(bundle, true);
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, uriMaybeGetUriWithoutUserId, "r");
            ContentProvider.traceBegin(64L, "openTypedAssetFile: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openTypedAssetFile(uriMaybeGetUriWithoutUserId, str, bundle, CancellationSignal.fromTransport(iCancellationSignal));
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
        public Uri canonicalize(AttributionSource attributionSource, Uri uri) throws SecurityException {
            Uri uriValidateIncomingUri = ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = ContentProvider.getUserIdFromUri(uriValidateIncomingUri);
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uriValidateIncomingUri);
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
        public Uri uncanonicalize(AttributionSource attributionSource, Uri uri) throws SecurityException {
            Uri uriValidateIncomingUri = ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = ContentProvider.getUserIdFromUri(uriValidateIncomingUri);
            Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uriValidateIncomingUri);
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
        public int checkUriPermission(AttributionSource attributionSource, Uri uri, int i, int i2) throws SecurityException {
            Uri uriMaybeGetUriWithoutUserId = ContentProvider.this.maybeGetUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri));
            ContentProvider.traceBegin(64L, "checkUriPermission: ", uriMaybeGetUriWithoutUserId.getAuthority());
            AttributionSource callingAttributionSource = ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.checkUriPermission(uriMaybeGetUriWithoutUserId, i, i2);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                Trace.traceEnd(64L);
            }
        }

        private void enforceFilePermission(AttributionSource attributionSource, Uri uri, String str) throws SecurityException, FileNotFoundException {
            if (str != null && str.indexOf(119) != -1) {
                if (enforceWritePermission(attributionSource, uri) != 0) {
                    throw new FileNotFoundException("App op not allowed");
                }
            } else if (enforceReadPermission(attributionSource, uri) != 0) {
                throw new FileNotFoundException("App op not allowed");
            }
        }

        private int enforceReadPermission(AttributionSource attributionSource, Uri uri) throws SecurityException {
            int iSemEnforceReadPermission = ContentProvider.this.semEnforceReadPermission(uri, attributionSource);
            if (iSemEnforceReadPermission != 0) {
                return iSemEnforceReadPermission;
            }
            if (ContentProvider.this.mTransport.mReadOp == -1 || ContentProvider.this.mTransport.mReadOp == AppOpsManager.permissionToOpCode(ContentProvider.this.mReadPermission)) {
                return 0;
            }
            return PermissionChecker.checkOpForDataDelivery(ContentProvider.this.getContext(), AppOpsManager.opToPublicName(ContentProvider.this.mTransport.mReadOp), attributionSource, null);
        }

        private int enforceWritePermission(AttributionSource attributionSource, Uri uri) throws SecurityException {
            int iSemEnforceWritePermission = ContentProvider.this.semEnforceWritePermission(uri, attributionSource);
            if (iSemEnforceWritePermission != 0) {
                return iSemEnforceWritePermission;
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
            int iIndexOfKey = this.mUsersRedirectedToOwnerForMedia.indexOfKey(i);
            if (iIndexOfKey >= 0) {
                return this.mUsersRedirectedToOwnerForMedia.valueAt(iIndexOfKey);
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
        int iMax;
        String str;
        Context context = getContext();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        int i2 = 0;
        if (UserHandle.isSameApp(callingUid, this.mMyUid)) {
            return 0;
        }
        String str2 = null;
        if (this.mExported && checkUser(callingPid, callingUid, context)) {
            String readPermission = getReadPermission();
            if (readPermission != null) {
                int iCheckPermission = checkPermission(readPermission, attributionSource);
                if (iCheckPermission == 0) {
                    return 0;
                }
                iMax = Math.max(0, iCheckPermission);
                str2 = readPermission;
            } else {
                iMax = 0;
            }
            int i3 = readPermission == null ? 1 : 0;
            PathPermission[] pathPermissions = getPathPermissions();
            if (pathPermissions != null) {
                String path = uri.getPath();
                int length = pathPermissions.length;
                int i4 = 0;
                while (i4 < length) {
                    PathPermission pathPermission = pathPermissions[i4];
                    int i5 = i2;
                    String readPermission2 = pathPermission.getReadPermission();
                    if (readPermission2 != null && pathPermission.match(path)) {
                        int iCheckPermission2 = checkPermission(readPermission2, attributionSource);
                        if (iCheckPermission2 == 0) {
                            return i5;
                        }
                        iMax = Math.max(iMax, iCheckPermission2);
                        str2 = readPermission2;
                        i3 = i5;
                    }
                    i4++;
                    i2 = i5;
                }
            }
            i = i2;
            if (i3 != 0) {
                return i;
            }
        } else {
            i = 0;
            iMax = 0;
        }
        int userId = UserHandle.getUserId(callingUid);
        if (deniedAccessSystemUserOnlyProvider(userId, this.mSystemUserOnly)) {
            return 2;
        }
        if (context.checkUriPermission((!this.mSingleUser || UserHandle.isSameUser(this.mMyUid, callingUid)) ? uri : maybeAddUserId(uri, userId), callingPid, callingUid, 1) == 0) {
            return i;
        }
        if (iMax == 1) {
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
        int iMax;
        String str;
        Context context = getContext();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        int i2 = 0;
        if (UserHandle.isSameApp(callingUid, this.mMyUid)) {
            return 0;
        }
        String str2 = null;
        if (this.mExported && checkUser(callingPid, callingUid, context)) {
            String writePermission = getWritePermission();
            if (writePermission != null) {
                int iCheckPermission = checkPermission(writePermission, attributionSource);
                if (iCheckPermission == 0) {
                    return 0;
                }
                iMax = Math.max(0, iCheckPermission);
                str2 = writePermission;
            } else {
                iMax = 0;
            }
            int i3 = writePermission == null ? 1 : 0;
            PathPermission[] pathPermissions = getPathPermissions();
            if (pathPermissions != null) {
                String path = uri.getPath();
                int length = pathPermissions.length;
                int i4 = 0;
                while (i4 < length) {
                    PathPermission pathPermission = pathPermissions[i4];
                    int i5 = i2;
                    String writePermission2 = pathPermission.getWritePermission();
                    if (writePermission2 != null && pathPermission.match(path)) {
                        int iCheckPermission2 = checkPermission(writePermission2, attributionSource);
                        if (iCheckPermission2 == 0) {
                            return i5;
                        }
                        iMax = Math.max(iMax, iCheckPermission2);
                        str2 = writePermission2;
                        i3 = i5;
                    }
                    i4++;
                    i2 = i5;
                }
            }
            i = i2;
            if (i3 != 0) {
                return i;
            }
        } else {
            i = 0;
            iMax = 0;
        }
        if (context.checkUriPermission(uri, callingPid, callingUid, 2) == 0) {
            return i;
        }
        if (iMax == 1) {
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
        ParcelFileDescriptor parcelFileDescriptorOpenFile = openFile(uri, str);
        if (parcelFileDescriptorOpenFile != null) {
            return new AssetFileDescriptor(parcelFileDescriptorOpenFile, 0L, -1L);
        }
        return null;
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return openAssetFile(uri, str);
    }

    protected final ParcelFileDescriptor openFileHelper(Uri uri, String str) throws FileNotFoundException {
        Cursor cursorQuery = query(uri, new String[]{"_data"}, null, null, null);
        int count = cursorQuery != null ? cursorQuery.getCount() : 0;
        if (count == 1) {
            cursorQuery.moveToFirst();
            int columnIndex = cursorQuery.getColumnIndex("_data");
            String string = columnIndex >= 0 ? cursorQuery.getString(columnIndex) : null;
            cursorQuery.close();
            if (string == null) {
                throw new FileNotFoundException("Column _data not found.");
            }
            return ParcelFileDescriptor.open(new File(string), ParcelFileDescriptor.parseMode(str));
        }
        if (cursorQuery != null) {
            cursorQuery.close();
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
            final ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe = ParcelFileDescriptor.createPipe();
            new AsyncTask<Object, Object, Object>(this) { // from class: android.content.ContentProvider.1
                @Override // android.os.AsyncTask
                protected Object doInBackground(Object... objArr) {
                    pipeDataWriter.writeDataToPipe(parcelFileDescriptorArrCreatePipe[1], uri, str, bundle, t);
                    try {
                        parcelFileDescriptorArrCreatePipe[1].close();
                        return null;
                    } catch (IOException e) {
                        Log.w(ContentProvider.TAG, "Failure closing pipe", e);
                        return null;
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            return parcelFileDescriptorArrCreatePipe[0];
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
        Uri uriBuild = uri.buildUpon().encodedPath(encodedPath.replaceAll("//+", "/")).build();
        Log.w(TAG, "Normalized " + uri + " to " + uriBuild + " to avoid possible security issues");
        return uriBuild;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Uri maybeGetUriWithoutUserId(Uri uri) {
        return this.mSingleUser ? uri : getUriWithoutUserId(uri);
    }

    public static int getUserIdFromAuthority(String str, int i) {
        int iLastIndexOf;
        if (str == null || (iLastIndexOf = str.lastIndexOf(64)) == -1) {
            return i;
        }
        try {
            return Integer.parseInt(str.substring(0, iLastIndexOf));
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
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.authority(getAuthorityWithoutUserId(uri.getAuthority()));
        return builderBuildUpon.build();
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
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.encodedAuthority("" + userHandle.getIdentifier() + "@" + uri.getEncodedAuthority());
        return builderBuildUpon.build();
    }

    public static Uri maybeAddUserId(Uri uri, int i) {
        if (uri == null) {
            return null;
        }
        if (i == -2 || !"content".equals(uri.getScheme()) || uriHasUserId(uri)) {
            return uri;
        }
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.encodedAuthority("" + i + "@" + uri.getEncodedAuthority());
        return builderBuildUpon.build();
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
