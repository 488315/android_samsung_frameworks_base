package android.content;

import android.annotation.SystemApi;
import android.content.res.AssetFileDescriptor;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class ContentProviderClient implements ContentInterface, AutoCloseable {
    private static final String TAG = "ContentProviderClient";
    private static Handler sAnrHandler;
    private NotRespondingRunnable mAnrRunnable;
    private long mAnrTimeout;
    private final AttributionSource mAttributionSource;
    private final String mAuthority;
    private final CloseGuard mCloseGuard;
    private final AtomicBoolean mClosed;
    private final IContentProvider mContentProvider;
    private final ContentResolver mContentResolver;
    private final String mPackageName;
    private final boolean mStable;

    public ContentProviderClient(ContentResolver contentResolver, IContentProvider iContentProvider, boolean z) {
        this(contentResolver, iContentProvider, "unknown", z);
    }

    public ContentProviderClient(ContentResolver contentResolver, IContentProvider iContentProvider, String str, boolean z) {
        this.mClosed = new AtomicBoolean();
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mContentResolver = contentResolver;
        this.mContentProvider = iContentProvider;
        this.mPackageName = contentResolver.mPackageName;
        this.mAttributionSource = contentResolver.getAttributionSource();
        this.mAuthority = str;
        this.mStable = z;
        closeGuard.open("ContentProviderClient.close");
    }

    @SystemApi
    public void setDetectNotResponding(long j) {
        synchronized (ContentProviderClient.class) {
            this.mAnrTimeout = j;
            if (j > 0) {
                if (this.mAnrRunnable == null) {
                    this.mAnrRunnable = new NotRespondingRunnable();
                }
                if (sAnrHandler == null) {
                    sAnrHandler = new Handler(Looper.getMainLooper(), null, true);
                }
                Binder.allowBlocking(this.mContentProvider.asBinder());
            } else {
                this.mAnrRunnable = null;
                Binder.defaultBlocking(this.mContentProvider.asBinder());
            }
        }
    }

    private void beforeRemote() {
        NotRespondingRunnable notRespondingRunnable = this.mAnrRunnable;
        if (notRespondingRunnable != null) {
            sAnrHandler.postDelayed(notRespondingRunnable, this.mAnrTimeout);
        }
    }

    private void afterRemote() {
        NotRespondingRunnable notRespondingRunnable = this.mAnrRunnable;
        if (notRespondingRunnable != null) {
            sAnrHandler.removeCallbacks(notRespondingRunnable);
        }
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) throws RemoteException {
        return query(uri, strArr, str, strArr2, str2, null);
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) throws RemoteException {
        return query(uri, strArr, ContentResolver.createSqlQueryBundle(str, strArr2, str2), cancellationSignal);
    }

    @Override // android.content.ContentInterface
    public Cursor query(Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException {
        ICancellationSignal iCancellationSignal;
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            if (cancellationSignal != null) {
                try {
                    cancellationSignal.throwIfCanceled();
                    ICancellationSignal iCancellationSignalCreateCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(iCancellationSignalCreateCancellationSignal);
                    iCancellationSignal = iCancellationSignalCreateCancellationSignal;
                } catch (DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                        throw e;
                    }
                    throw e;
                }
            } else {
                iCancellationSignal = null;
            }
            Cursor cursorQuery = this.mContentProvider.query(this.mAttributionSource, uri, strArr, bundle, iCancellationSignal);
            if (cursorQuery != null) {
                CursorWrapperInner cursorWrapperInner = new CursorWrapperInner(this, cursorQuery);
                afterRemote();
                return cursorWrapperInner;
            }
            afterRemote();
            return null;
        } catch (Throwable th) {
            afterRemote();
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public String getType(Uri uri) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.getType(this.mAttributionSource, uri);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public String[] getStreamTypes(Uri uri, String str) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        Objects.requireNonNull(str, "mimeTypeFilter");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.getStreamTypes(this.mAttributionSource, uri, str);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public final Uri canonicalize(Uri uri) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.canonicalize(this.mAttributionSource, uri);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public final Uri uncanonicalize(Uri uri) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.uncanonicalize(this.mAttributionSource, uri);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public boolean refresh(Uri uri, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException {
        ICancellationSignal iCancellationSignalCreateCancellationSignal;
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            if (cancellationSignal != null) {
                try {
                    cancellationSignal.throwIfCanceled();
                    iCancellationSignalCreateCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(iCancellationSignalCreateCancellationSignal);
                } catch (DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            } else {
                iCancellationSignalCreateCancellationSignal = null;
            }
            return this.mContentProvider.refresh(this.mAttributionSource, uri, bundle, iCancellationSignalCreateCancellationSignal);
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public int checkUriPermission(Uri uri, int i, int i2) throws RemoteException {
        Objects.requireNonNull(uri, "uri");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.checkUriPermission(this.mAttributionSource, uri, i, i2);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public Uri insert(Uri uri, ContentValues contentValues) throws RemoteException {
        return insert(uri, contentValues, null);
    }

    @Override // android.content.ContentInterface
    public Uri insert(Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.insert(this.mAttributionSource, uri, contentValues, bundle);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    @Override // android.content.ContentInterface
    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        Objects.requireNonNull(contentValuesArr, "initialValues");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.bulkInsert(this.mAttributionSource, uri, contentValuesArr);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public int delete(Uri uri, String str, String[] strArr) throws RemoteException {
        return delete(uri, ContentResolver.createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public int delete(Uri uri, Bundle bundle) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.delete(this.mAttributionSource, uri, bundle);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) throws RemoteException {
        return update(uri, contentValues, ContentResolver.createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public int update(Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Objects.requireNonNull(uri, "url");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.update(this.mAttributionSource, uri, contentValues, bundle);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public ParcelFileDescriptor openFile(Uri uri, String str) throws RemoteException, FileNotFoundException {
        return openFile(uri, str, null);
    }

    @Override // android.content.ContentInterface
    public ParcelFileDescriptor openFile(Uri uri, String str, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        ICancellationSignal iCancellationSignalCreateCancellationSignal;
        Objects.requireNonNull(uri, "url");
        Objects.requireNonNull(str, "mode");
        beforeRemote();
        if (cancellationSignal != null) {
            try {
                try {
                    cancellationSignal.throwIfCanceled();
                    iCancellationSignalCreateCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(iCancellationSignalCreateCancellationSignal);
                } catch (DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            } finally {
                afterRemote();
            }
        } else {
            iCancellationSignalCreateCancellationSignal = null;
        }
        return this.mContentProvider.openFile(this.mAttributionSource, uri, str, iCancellationSignalCreateCancellationSignal);
    }

    public AssetFileDescriptor openAssetFile(Uri uri, String str) throws RemoteException, FileNotFoundException {
        return openAssetFile(uri, str, null);
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        ICancellationSignal iCancellationSignalCreateCancellationSignal;
        Objects.requireNonNull(uri, "url");
        Objects.requireNonNull(str, "mode");
        beforeRemote();
        if (cancellationSignal != null) {
            try {
                try {
                    cancellationSignal.throwIfCanceled();
                    iCancellationSignalCreateCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(iCancellationSignalCreateCancellationSignal);
                } catch (DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    }
                    throw e;
                }
            } finally {
                afterRemote();
            }
        } else {
            iCancellationSignalCreateCancellationSignal = null;
        }
        return this.mContentProvider.openAssetFile(this.mAttributionSource, uri, str, iCancellationSignalCreateCancellationSignal);
    }

    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String str, Bundle bundle) throws RemoteException, FileNotFoundException {
        return openTypedAssetFileDescriptor(uri, str, bundle, null);
    }

    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        return openTypedAssetFile(uri, str, bundle, cancellationSignal);
    }

    @Override // android.content.ContentInterface
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        ICancellationSignal iCancellationSignalCreateCancellationSignal;
        Objects.requireNonNull(uri, "uri");
        Objects.requireNonNull(str, "mimeTypeFilter");
        beforeRemote();
        if (cancellationSignal != null) {
            try {
                try {
                    cancellationSignal.throwIfCanceled();
                    iCancellationSignalCreateCancellationSignal = this.mContentProvider.createCancellationSignal();
                    cancellationSignal.setRemote(iCancellationSignalCreateCancellationSignal);
                } catch (DeadObjectException e) {
                    if (!this.mStable) {
                        this.mContentResolver.unstableProviderDied(this.mContentProvider);
                        throw e;
                    }
                    throw e;
                }
            } catch (Throwable th) {
                afterRemote();
                throw th;
            }
        } else {
            iCancellationSignalCreateCancellationSignal = null;
        }
        AssetFileDescriptor assetFileDescriptorOpenTypedAssetFile = this.mContentProvider.openTypedAssetFile(this.mAttributionSource, uri, str, bundle, iCancellationSignalCreateCancellationSignal);
        afterRemote();
        return assetFileDescriptorOpenTypedAssetFile;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        return applyBatch(this.mAuthority, arrayList);
    }

    @Override // android.content.ContentInterface
    public ContentProviderResult[] applyBatch(String str, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Objects.requireNonNull(arrayList, "operations");
        beforeRemote();
        try {
            try {
                return this.mContentProvider.applyBatch(this.mAttributionSource, str, arrayList);
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                }
                throw e;
            }
        } finally {
            afterRemote();
        }
    }

    public Bundle call(String str, String str2, Bundle bundle) throws RemoteException {
        return call(this.mAuthority, str, str2, bundle);
    }

    @Override // android.content.ContentInterface
    public Bundle call(String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Objects.requireNonNull(str, ContactsContract.Directory.DIRECTORY_AUTHORITY);
        Objects.requireNonNull(str2, "method");
        beforeRemote();
        try {
            try {
                Bundle bundleCall = this.mContentProvider.call(this.mAttributionSource, str, str2, str3, bundle);
                afterRemote();
                return bundleCall;
            } catch (DeadObjectException e) {
                if (!this.mStable) {
                    this.mContentResolver.unstableProviderDied(this.mContentProvider);
                    throw e;
                }
                throw e;
            }
        } catch (Throwable th) {
            afterRemote();
            throw th;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        closeInternal();
    }

    @Deprecated
    public boolean release() {
        return closeInternal();
    }

    private boolean closeInternal() {
        this.mCloseGuard.close();
        if (!this.mClosed.compareAndSet(false, true)) {
            return false;
        }
        setDetectNotResponding(0L);
        if (this.mStable) {
            return this.mContentResolver.releaseProvider(this.mContentProvider);
        }
        return this.mContentResolver.releaseUnstableProvider(this.mContentProvider);
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    public ContentProvider getLocalContentProvider() {
        return ContentProvider.coerceToLocalContentProvider(this.mContentProvider);
    }

    @Deprecated
    public static void closeQuietly(ContentProviderClient contentProviderClient) {
        IoUtils.closeQuietly(contentProviderClient);
    }

    @Deprecated
    public static void releaseQuietly(ContentProviderClient contentProviderClient) {
        IoUtils.closeQuietly(contentProviderClient);
    }

    private class NotRespondingRunnable implements Runnable {
        private NotRespondingRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.w(ContentProviderClient.TAG, "Detected provider not responding: " + ContentProviderClient.this.mContentProvider);
            ContentProviderClient.this.mContentResolver.appNotRespondingViaProvider(ContentProviderClient.this.mContentProvider);
        }
    }

    private final class CursorWrapperInner extends CrossProcessCursorWrapper {
        private final CloseGuard mCloseGuard;

        CursorWrapperInner(ContentProviderClient contentProviderClient, Cursor cursor) {
            super(cursor);
            CloseGuard closeGuard = CloseGuard.get();
            this.mCloseGuard = closeGuard;
            closeGuard.open("CursorWrapperInner.close");
        }

        @Override // android.database.CursorWrapper, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mCloseGuard.close();
            super.close();
        }

        protected void finalize() throws Throwable {
            try {
                CloseGuard closeGuard = this.mCloseGuard;
                if (closeGuard != null) {
                    closeGuard.warnIfOpen();
                }
                close();
            } finally {
                super.finalize();
            }
        }
    }
}
