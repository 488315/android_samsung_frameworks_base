package android.content;

import android.accounts.Account;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.UriGrantsManager;
import android.content.IContentService;
import android.content.ISyncStatusObserver;
import android.content.SyncRequest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.database.IContentObserver;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ICancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.system.Int64Ref;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import com.android.internal.telephony.HbpcdLookup;
import com.android.internal.util.MimeIconUtils;
import com.google.android.mms.ContentType;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class ContentResolver implements ContentInterface {
    public static final String ANY_CURSOR_ITEM_TYPE = "vnd.android.cursor.item/*";
    public static final int CONTENT_PROVIDER_PUBLISH_TIMEOUT_MILLIS;
    public static final int CONTENT_PROVIDER_READY_TIMEOUT_MILLIS;
    private static final int CONTENT_PROVIDER_TIMEOUT_MILLIS;
    public static final String CONTENT_SERVICE_NAME = "content";
    public static final String CURSOR_DIR_BASE_TYPE = "vnd.android.cursor.dir";
    public static final String CURSOR_ITEM_BASE_TYPE = "vnd.android.cursor.item";
    public static final boolean DEPRECATE_DATA_COLUMNS = true;
    public static final String DEPRECATE_DATA_PREFIX = "/mnt/content/";
    private static final boolean ENABLE_CONTENT_SAMPLE = false;
    public static final String EXTRA_HONORED_ARGS = "android.content.extra.HONORED_ARGS";
    public static final String EXTRA_REFRESH_SUPPORTED = "android.content.extra.REFRESH_SUPPORTED";
    public static final String EXTRA_SIZE = "android.content.extra.SIZE";
    public static final String EXTRA_TOTAL_COUNT = "android.content.extra.TOTAL_COUNT";

    @Deprecated
    public static final String MIME_TYPE_DEFAULT = "application/octet-stream";
    public static final String MULTIMEDIA_AUTHORITY = "mms";
    public static final int NOTIFY_DELETE = 16;
    public static final int NOTIFY_INSERT = 4;
    public static final int NOTIFY_NO_DELAY = 32768;
    public static final int NOTIFY_SKIP_NOTIFY_FOR_DESCENDANTS = 2;
    public static final int NOTIFY_SYNC_TO_NETWORK = 1;
    public static final int NOTIFY_UPDATE = 8;
    public static final String QUERY_ARG_GROUP_COLUMNS = "android:query-arg-group-columns";
    public static final String QUERY_ARG_LIMIT = "android:query-arg-limit";
    public static final String QUERY_ARG_OFFSET = "android:query-arg-offset";
    public static final String QUERY_ARG_SORT_COLLATION = "android:query-arg-sort-collation";
    public static final String QUERY_ARG_SORT_COLUMNS = "android:query-arg-sort-columns";
    public static final String QUERY_ARG_SORT_DIRECTION = "android:query-arg-sort-direction";
    public static final String QUERY_ARG_SORT_LOCALE = "android:query-arg-sort-locale";
    public static final String QUERY_ARG_SQL_GROUP_BY = "android:query-arg-sql-group-by";
    public static final String QUERY_ARG_SQL_HAVING = "android:query-arg-sql-having";
    public static final String QUERY_ARG_SQL_LIMIT = "android:query-arg-sql-limit";
    public static final String QUERY_ARG_SQL_SELECTION = "android:query-arg-sql-selection";
    public static final String QUERY_ARG_SQL_SELECTION_ARGS = "android:query-arg-sql-selection-args";
    public static final String QUERY_ARG_SQL_SORT_ORDER = "android:query-arg-sql-sort-order";
    public static final int QUERY_SORT_DIRECTION_ASCENDING = 0;
    public static final int QUERY_SORT_DIRECTION_DESCENDING = 1;
    public static final String RCS_AUTHORITY = "im";
    public static final String REMOTE_CALLBACK_ERROR = "error";
    public static final String REMOTE_CALLBACK_RESULT = "result";
    private static final int REMOTE_CONTENT_PROVIDER_TIMEOUT_MILLIS;
    public static final String SCHEME_ANDROID_RESOURCE = "android.resource";
    public static final String SCHEME_CONTENT = "content";
    public static final String SCHEME_FILE = "file";
    public static final int SYNC_ERROR_AUTHENTICATION = 2;
    public static final int SYNC_ERROR_CONFLICT = 5;
    public static final int SYNC_ERROR_INTERNAL = 8;
    public static final int SYNC_ERROR_IO = 3;
    public static final int SYNC_ERROR_PARSE = 4;
    public static final int SYNC_ERROR_SYNC_ALREADY_IN_PROGRESS = 1;
    public static final int SYNC_ERROR_TOO_MANY_DELETIONS = 6;
    public static final int SYNC_ERROR_TOO_MANY_RETRIES = 7;
    public static final int SYNC_EXEMPTION_NONE = 0;
    public static final int SYNC_EXEMPTION_PROMOTE_BUCKET = 1;
    public static final int SYNC_EXEMPTION_PROMOTE_BUCKET_WITH_TEMP = 2;

    @Deprecated
    public static final String SYNC_EXTRAS_ACCOUNT = "account";
    public static final String SYNC_EXTRAS_DISALLOW_METERED = "allow_metered";
    public static final String SYNC_EXTRAS_DISCARD_LOCAL_DELETIONS = "discard_deletions";
    public static final String SYNC_EXTRAS_DO_NOT_RETRY = "do_not_retry";
    public static final String SYNC_EXTRAS_EXPECTED_DOWNLOAD = "expected_download";
    public static final String SYNC_EXTRAS_EXPECTED_UPLOAD = "expected_upload";
    public static final String SYNC_EXTRAS_EXPEDITED = "expedited";

    @Deprecated
    public static final String SYNC_EXTRAS_FORCE = "force";
    public static final String SYNC_EXTRAS_IGNORE_BACKOFF = "ignore_backoff";
    public static final String SYNC_EXTRAS_IGNORE_SETTINGS = "ignore_settings";
    public static final String SYNC_EXTRAS_INITIALIZE = "initialize";
    public static final String SYNC_EXTRAS_MANUAL = "force";
    public static final String SYNC_EXTRAS_OVERRIDE_TOO_MANY_DELETIONS = "deletions_override";
    public static final String SYNC_EXTRAS_PRIORITY = "sync_priority";
    public static final String SYNC_EXTRAS_REQUIRE_CHARGING = "require_charging";
    public static final String SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB = "schedule_as_expedited_job";
    public static final String SYNC_EXTRAS_UPLOAD = "upload";
    public static final int SYNC_OBSERVER_TYPE_ACTIVE = 4;
    public static final int SYNC_OBSERVER_TYPE_ALL = Integer.MAX_VALUE;
    public static final int SYNC_OBSERVER_TYPE_PENDING = 2;
    public static final int SYNC_OBSERVER_TYPE_SETTINGS = 1;
    public static final int SYNC_OBSERVER_TYPE_STATUS = 8;
    public static final String SYNC_VIRTUAL_EXTRAS_EXEMPTION_FLAG = "v_exemption";
    private static final String TAG = "ContentResolver";
    private static volatile IContentService sContentService;
    private final Context mContext;

    @Deprecated
    final String mPackageName;
    private final Random mRandom;
    final int mTargetSdkVersion;
    final ContentInterface mWrapped;
    public static final Intent ACTION_SYNC_CONN_STATUS_CHANGED = new Intent("com.android.sync.SYNC_CONN_STATUS_CHANGED");
    private static final String[] SYNC_ERROR_NAMES = {"already-in-progress", "authentication-error", "io-error", "parse-error", HbpcdLookup.PATH_MCC_SID_CONFLICT, "too-many-deletions", "too-many-retries", "internal-error"};
    private static final int SLOW_THRESHOLD_MILLIS = Build.HW_TIMEOUT_MULTIPLIER * 500;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NotifyFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface QueryCollator {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SortDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SyncExemption {
    }

    private void maybeLogQueryToEventLog(long j, Uri uri, String[] strArr, Bundle bundle) {
    }

    private void maybeLogUpdateToEventLog(long j, Uri uri, String str, String str2) {
    }

    protected abstract IContentProvider acquireProvider(Context context, String str);

    protected abstract IContentProvider acquireUnstableProvider(Context context, String str);

    public abstract boolean releaseProvider(IContentProvider iContentProvider);

    public abstract boolean releaseUnstableProvider(IContentProvider iContentProvider);

    public abstract void unstableProviderDied(IContentProvider iContentProvider);

    static {
        int i = Build.HW_TIMEOUT_MULTIPLIER * 10000;
        CONTENT_PROVIDER_PUBLISH_TIMEOUT_MILLIS = i;
        int i2 = i + (Build.HW_TIMEOUT_MULTIPLIER * 10000);
        CONTENT_PROVIDER_READY_TIMEOUT_MILLIS = i2;
        int i3 = Build.HW_TIMEOUT_MULTIPLIER * 3000;
        CONTENT_PROVIDER_TIMEOUT_MILLIS = i3;
        REMOTE_CONTENT_PROVIDER_TIMEOUT_MILLIS = i2 + i3;
    }

    public static String syncErrorToString(int i) {
        if (i >= 1) {
            String[] strArr = SYNC_ERROR_NAMES;
            if (i <= strArr.length) {
                return strArr[i - 1];
            }
        }
        return String.valueOf(i);
    }

    public static int syncErrorStringToInt(String str) {
        int length = SYNC_ERROR_NAMES.length;
        for (int i = 0; i < length; i++) {
            if (SYNC_ERROR_NAMES[i].equals(str)) {
                return i + 1;
            }
        }
        if (str != null) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                Log.d(TAG, "error parsing sync error: " + str);
            }
        }
        return 0;
    }

    public ContentResolver(Context context) {
        this(context, null);
    }

    public ContentResolver(Context context, ContentInterface contentInterface) {
        this.mRandom = new Random();
        context = context == null ? ActivityThread.currentApplication() : context;
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mWrapped = contentInterface;
    }

    public static ContentResolver wrap(ContentInterface contentInterface) {
        Objects.requireNonNull(contentInterface);
        return new ContentResolver(null, contentInterface) { // from class: android.content.ContentResolver.1
            @Override // android.content.ContentResolver
            public void unstableProviderDied(IContentProvider iContentProvider) {
                throw new UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            public boolean releaseUnstableProvider(IContentProvider iContentProvider) {
                throw new UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            public boolean releaseProvider(IContentProvider iContentProvider) {
                throw new UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            protected IContentProvider acquireUnstableProvider(Context context, String str) {
                throw new UnsupportedOperationException();
            }

            @Override // android.content.ContentResolver
            protected IContentProvider acquireProvider(Context context, String str) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static ContentResolver wrap(ContentProvider contentProvider) {
        return wrap((ContentInterface) contentProvider);
    }

    public static ContentResolver wrap(ContentProviderClient contentProviderClient) {
        return wrap((ContentInterface) contentProviderClient);
    }

    protected IContentProvider acquireExistingProvider(Context context, String str) {
        return acquireProvider(context, str);
    }

    public void appNotRespondingViaProvider(IContentProvider iContentProvider) {
        throw new UnsupportedOperationException("appNotRespondingViaProvider");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentInterface
    public final String getType(Uri uri) {
        IContentProvider iContentProvider;
        Objects.requireNonNull(uri, "url");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.getType(uri);
            }
            try {
                iContentProvider = acquireProvider(uri);
            } catch (Exception unused) {
                iContentProvider = null;
            }
            try {
                if (iContentProvider != null) {
                    try {
                        try {
                            StringResultListener stringResultListener = new StringResultListener();
                            iContentProvider.getTypeAsync(this.mContext.getAttributionSource(), uri, new RemoteCallback(stringResultListener));
                            stringResultListener.waitForResult(CONTENT_PROVIDER_TIMEOUT_MILLIS);
                            if (stringResultListener.exception != null) {
                                throw stringResultListener.exception;
                            }
                            return (String) stringResultListener.result;
                        } catch (RemoteException unused2) {
                            releaseProvider(iContentProvider);
                            return null;
                        } catch (Exception e) {
                            Log.w(TAG, "Failed to get type for: " + uri + " (" + e.getMessage() + NavigationBarInflaterView.KEY_CODE_END);
                            try {
                                releaseProvider(iContentProvider);
                            } catch (NullPointerException unused3) {
                            }
                            return null;
                        }
                    } catch (NullPointerException unused4) {
                        return null;
                    }
                }
                if (!"content".equals(uri.getScheme())) {
                    return null;
                }
                try {
                    StringResultListener stringResultListener2 = new StringResultListener();
                    ActivityManager.getService().getMimeTypeFilterAsync(ContentProvider.getUriWithoutUserId(uri), resolveUserId(uri), new RemoteCallback(stringResultListener2));
                    stringResultListener2.waitForResult(REMOTE_CONTENT_PROVIDER_TIMEOUT_MILLIS);
                    if (stringResultListener2.exception != null) {
                        throw stringResultListener2.exception;
                    }
                    return (String) stringResultListener2.result;
                } catch (Exception e2) {
                    Log.w(TAG, "Failed to get type for: " + uri + " (" + e2.getMessage() + NavigationBarInflaterView.KEY_CODE_END);
                    return null;
                }
            } finally {
                try {
                    releaseProvider(iContentProvider);
                } catch (NullPointerException unused5) {
                }
            }
        } catch (RemoteException unused6) {
        }
    }

    private static abstract class ResultListener<T> implements RemoteCallback.OnResultListener {
        public boolean done;
        public RuntimeException exception;
        public T result;

        protected abstract T getResultFromBundle(Bundle bundle);

        private ResultListener() {
        }

        @Override // android.os.RemoteCallback.OnResultListener
        public void onResult(Bundle bundle) {
            synchronized (this) {
                ParcelableException parcelableException = (ParcelableException) bundle.getParcelable("error", ParcelableException.class);
                if (parcelableException != null) {
                    Throwable cause = parcelableException.getCause();
                    if (cause instanceof RuntimeException) {
                        this.exception = (RuntimeException) cause;
                    } else {
                        this.exception = new RuntimeException(cause);
                    }
                } else {
                    this.result = getResultFromBundle(bundle);
                }
                this.done = true;
                notifyAll();
            }
        }

        public void waitForResult(long j) {
            synchronized (this) {
                if (!this.done) {
                    try {
                        wait(j);
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }

    private static class StringResultListener extends ResultListener<String> {
        private StringResultListener() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.content.ContentResolver.ResultListener
        public String getResultFromBundle(Bundle bundle) {
            return bundle.getString("result");
        }
    }

    private static class UriResultListener extends ResultListener<Uri> {
        private UriResultListener() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.ContentResolver.ResultListener
        public Uri getResultFromBundle(Bundle bundle) {
            return (Uri) bundle.getParcelable("result", Uri.class);
        }
    }

    @Override // android.content.ContentInterface
    public String[] getStreamTypes(Uri uri, String str) {
        Objects.requireNonNull(uri, "url");
        Objects.requireNonNull(str, "mimeTypeFilter");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.getStreamTypes(uri, str);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return null;
            }
            try {
                return acquireProvider.getStreamTypes(this.mContext.getAttributionSource(), uri, str);
            } catch (RemoteException unused) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (RemoteException unused2) {
        }
    }

    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return query(uri, strArr, str, strArr2, str2, null);
    }

    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        return query(uri, strArr, createSqlQueryBundle(str, strArr2, str2), cancellationSignal);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[SYNTHETIC] */
    @Override // android.content.ContentInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.database.Cursor query(android.net.Uri r15, java.lang.String[] r16, android.os.Bundle r17, android.os.CancellationSignal r18) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ContentResolver.query(android.net.Uri, java.lang.String[], android.os.Bundle, android.os.CancellationSignal):android.database.Cursor");
    }

    public final Uri canonicalizeOrElse(Uri uri) {
        Uri canonicalize = canonicalize(uri);
        return canonicalize != null ? canonicalize : uri;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentInterface
    public final Uri canonicalize(Uri uri) {
        Objects.requireNonNull(uri, "url");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.canonicalize(uri);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return null;
            }
            try {
                UriResultListener uriResultListener = new UriResultListener();
                acquireProvider.canonicalizeAsync(this.mContext.getAttributionSource(), uri, new RemoteCallback(uriResultListener));
                uriResultListener.waitForResult(CONTENT_PROVIDER_TIMEOUT_MILLIS);
                if (uriResultListener.exception != null) {
                    throw uriResultListener.exception;
                }
                return (Uri) uriResultListener.result;
            } catch (RemoteException unused) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (RemoteException unused2) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.ContentInterface
    public final Uri uncanonicalize(Uri uri) {
        Objects.requireNonNull(uri, "url");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.uncanonicalize(uri);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return null;
            }
            try {
                UriResultListener uriResultListener = new UriResultListener();
                acquireProvider.uncanonicalizeAsync(this.mContext.getAttributionSource(), uri, new RemoteCallback(uriResultListener));
                uriResultListener.waitForResult(CONTENT_PROVIDER_TIMEOUT_MILLIS);
                if (uriResultListener.exception != null) {
                    throw uriResultListener.exception;
                }
                return (Uri) uriResultListener.result;
            } catch (RemoteException unused) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (RemoteException unused2) {
        }
    }

    @Override // android.content.ContentInterface
    public final boolean refresh(Uri uri, Bundle bundle, CancellationSignal cancellationSignal) {
        ICancellationSignal createCancellationSignal;
        Objects.requireNonNull(uri, "url");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.refresh(uri, bundle, cancellationSignal);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                return false;
            }
            if (cancellationSignal != null) {
                try {
                    cancellationSignal.throwIfCanceled();
                    createCancellationSignal = acquireProvider.createCancellationSignal();
                    cancellationSignal.setRemote(createCancellationSignal);
                } catch (RemoteException unused) {
                    return false;
                } finally {
                    releaseProvider(acquireProvider);
                }
            } else {
                createCancellationSignal = null;
            }
            return acquireProvider.refresh(this.mContext.getAttributionSource(), uri, bundle, createCancellationSignal);
        } catch (RemoteException unused2) {
        }
    }

    @Override // android.content.ContentInterface
    @SystemApi
    public int checkUriPermission(Uri uri, int i, int i2) {
        Objects.requireNonNull(uri, "uri");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.checkUriPermission(uri, i, i2);
            }
            ContentProviderClient acquireUnstableContentProviderClient = acquireUnstableContentProviderClient(uri);
            try {
                int checkUriPermission = acquireUnstableContentProviderClient.checkUriPermission(uri, i, i2);
                if (acquireUnstableContentProviderClient != null) {
                    acquireUnstableContentProviderClient.close();
                }
                return checkUriPermission;
            } finally {
            }
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public final InputStream openInputStream(Uri uri) throws FileNotFoundException {
        Objects.requireNonNull(uri, "uri");
        String scheme = uri.getScheme();
        if (SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            OpenResourceIdResult resourceId = getResourceId(uri);
            try {
                return resourceId.r.openRawResource(resourceId.id);
            } catch (Resources.NotFoundException unused) {
                throw new FileNotFoundException("Resource does not exist: " + uri);
            }
        }
        if ("file".equals(scheme)) {
            return new FileInputStream(uri.getPath());
        }
        try {
            return switchInputStream(uri, openAssetFileDescriptor(uri, "r", null));
        } catch (IOException unused2) {
            throw new FileNotFoundException("Unable to create stream");
        }
    }

    private final FileInputStream switchInputStream(Uri uri, AssetFileDescriptor assetFileDescriptor) throws IOException {
        if ("mms".equals(uri.getAuthority()) || "im".equals(uri.getAuthority())) {
            if (assetFileDescriptor != null) {
                return assetFileDescriptor.createKumihoInputStream();
            }
            return null;
        }
        if (assetFileDescriptor != null) {
            return assetFileDescriptor.createInputStream();
        }
        return null;
    }

    public final OutputStream openOutputStream(Uri uri) throws FileNotFoundException {
        return openOutputStream(uri, "w");
    }

    public final OutputStream openOutputStream(Uri uri, String str) throws FileNotFoundException {
        AssetFileDescriptor openAssetFileDescriptor = openAssetFileDescriptor(uri, str, null);
        if (openAssetFileDescriptor == null) {
            return null;
        }
        try {
            return openAssetFileDescriptor.createOutputStream();
        } catch (IOException unused) {
            throw new FileNotFoundException("Unable to create stream");
        }
    }

    @Override // android.content.ContentInterface
    public final ParcelFileDescriptor openFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            ContentInterface contentInterface = this.mWrapped;
            return contentInterface != null ? contentInterface.openFile(uri, str, cancellationSignal) : openFileDescriptor(uri, str, cancellationSignal);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public final ParcelFileDescriptor openFileDescriptor(Uri uri, String str) throws FileNotFoundException {
        return openFileDescriptor(uri, str, null);
    }

    public final ParcelFileDescriptor openFileDescriptor(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.openFile(uri, str, cancellationSignal);
            }
            AssetFileDescriptor openAssetFileDescriptor = openAssetFileDescriptor(uri, str, cancellationSignal);
            if (openAssetFileDescriptor == null) {
                return null;
            }
            if (openAssetFileDescriptor.getDeclaredLength() < 0) {
                return openAssetFileDescriptor.getParcelFileDescriptor();
            }
            try {
                openAssetFileDescriptor.close();
            } catch (IOException unused) {
            }
            throw new FileNotFoundException("Not a whole file");
        } catch (RemoteException unused2) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public final AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            ContentInterface contentInterface = this.mWrapped;
            return contentInterface != null ? contentInterface.openAssetFile(uri, str, cancellationSignal) : openAssetFileDescriptor(uri, str, cancellationSignal);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public final AssetFileDescriptor openAssetFileDescriptor(Uri uri, String str) throws FileNotFoundException {
        return openAssetFileDescriptor(uri, str, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.res.AssetFileDescriptor openAssetFileDescriptor(android.net.Uri r13, java.lang.String r14, android.os.CancellationSignal r15) throws java.io.FileNotFoundException {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ContentResolver.openAssetFileDescriptor(android.net.Uri, java.lang.String, android.os.CancellationSignal):android.content.res.AssetFileDescriptor");
    }

    @Override // android.content.ContentInterface
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.openTypedAssetFile(uri, str, bundle, cancellationSignal);
            }
            return openTypedAssetFileDescriptor(uri, str, bundle, cancellationSignal);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String str, Bundle bundle) throws FileNotFoundException {
        return openTypedAssetFileDescriptor(uri, str, bundle, null);
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0110: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:107:0x010f */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.res.AssetFileDescriptor openTypedAssetFileDescriptor(android.net.Uri r20, java.lang.String r21, android.os.Bundle r22, android.os.CancellationSignal r23) throws java.io.FileNotFoundException {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ContentResolver.openTypedAssetFileDescriptor(android.net.Uri, java.lang.String, android.os.Bundle, android.os.CancellationSignal):android.content.res.AssetFileDescriptor");
    }

    public class OpenResourceIdResult {
        public int id;
        public Resources r;

        public OpenResourceIdResult(ContentResolver contentResolver) {
        }
    }

    public OpenResourceIdResult getResourceId(Uri uri) throws FileNotFoundException {
        int parseInt;
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException("No authority: " + uri);
        }
        try {
            Resources resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(authority);
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new FileNotFoundException("No path: " + uri);
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    parseInt = Integer.parseInt(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                }
            } else if (size == 2) {
                parseInt = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            } else {
                throw new FileNotFoundException("More than two path segments: " + uri);
            }
            if (parseInt == 0) {
                throw new FileNotFoundException("No resource found for: " + uri);
            }
            OpenResourceIdResult openResourceIdResult = new OpenResourceIdResult(this);
            openResourceIdResult.r = resourcesForApplication;
            openResourceIdResult.id = parseInt;
            return openResourceIdResult;
        } catch (PackageManager.NameNotFoundException unused2) {
            throw new FileNotFoundException("No package found for authority: " + uri);
        }
    }

    public final Uri insert(Uri uri, ContentValues contentValues) {
        return insert(uri, contentValues, null);
    }

    @Override // android.content.ContentInterface
    public final Uri insert(Uri uri, ContentValues contentValues, Bundle bundle) {
        ContentResolver contentResolver;
        long uptimeMillis;
        Uri insert;
        Objects.requireNonNull(uri, "url");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.insert(uri, contentValues, bundle);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new IllegalArgumentException("Unknown URL " + uri);
            }
            try {
                uptimeMillis = SystemClock.uptimeMillis();
                insert = acquireProvider.insert(this.mContext.getAttributionSource(), uri, contentValues, bundle);
                contentResolver = this;
            } catch (RemoteException unused) {
                contentResolver = this;
            } catch (Throwable th) {
                th = th;
                contentResolver = this;
            }
            try {
                contentResolver.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - uptimeMillis, uri, "insert", null);
                contentResolver.releaseProvider(acquireProvider);
                return insert;
            } catch (RemoteException unused2) {
                contentResolver.releaseProvider(acquireProvider);
                return null;
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                contentResolver.releaseProvider(acquireProvider);
                throw th3;
            }
        } catch (RemoteException unused3) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public ContentProviderResult[] applyBatch(String str, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Objects.requireNonNull(str, ContactsContract.Directory.DIRECTORY_AUTHORITY);
        Objects.requireNonNull(arrayList, "operations");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.applyBatch(str, arrayList);
            }
            ContentProviderClient acquireContentProviderClient = acquireContentProviderClient(str);
            if (acquireContentProviderClient == null) {
                throw new IllegalArgumentException("Unknown authority " + str);
            }
            try {
                return acquireContentProviderClient.applyBatch(arrayList);
            } finally {
                acquireContentProviderClient.release();
            }
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // android.content.ContentInterface
    public final int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        ContentResolver contentResolver;
        long uptimeMillis;
        int bulkInsert;
        Objects.requireNonNull(uri, "url");
        Objects.requireNonNull(contentValuesArr, "values");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.bulkInsert(uri, contentValuesArr);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new IllegalArgumentException("Unknown URL " + uri);
            }
            try {
                uptimeMillis = SystemClock.uptimeMillis();
                bulkInsert = acquireProvider.bulkInsert(this.mContext.getAttributionSource(), uri, contentValuesArr);
                contentResolver = this;
            } catch (RemoteException unused) {
                contentResolver = this;
            } catch (Throwable th) {
                th = th;
                contentResolver = this;
            }
            try {
                contentResolver.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - uptimeMillis, uri, "bulkinsert", null);
                contentResolver.releaseProvider(acquireProvider);
                return bulkInsert;
            } catch (RemoteException unused2) {
                contentResolver.releaseProvider(acquireProvider);
                return 0;
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                contentResolver.releaseProvider(acquireProvider);
                throw th3;
            }
        } catch (RemoteException unused3) {
            return 0;
        }
    }

    public final int delete(Uri uri, String str, String[] strArr) {
        return delete(uri, createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public final int delete(Uri uri, Bundle bundle) {
        ContentResolver contentResolver;
        long uptimeMillis;
        int delete;
        Objects.requireNonNull(uri, "url");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.delete(uri, bundle);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new IllegalArgumentException("Unknown URL " + uri);
            }
            try {
                uptimeMillis = SystemClock.uptimeMillis();
                delete = acquireProvider.delete(this.mContext.getAttributionSource(), uri, bundle);
                contentResolver = this;
            } catch (RemoteException unused) {
                contentResolver = this;
            } catch (Throwable th) {
                th = th;
                contentResolver = this;
            }
            try {
                contentResolver.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - uptimeMillis, uri, "delete", null);
                contentResolver.releaseProvider(acquireProvider);
                return delete;
            } catch (RemoteException unused2) {
                contentResolver.releaseProvider(acquireProvider);
                return -1;
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                contentResolver.releaseProvider(acquireProvider);
                throw th3;
            }
        } catch (RemoteException unused3) {
            return 0;
        }
    }

    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return update(uri, contentValues, createSqlQueryBundle(str, strArr));
    }

    @Override // android.content.ContentInterface
    public final int update(Uri uri, ContentValues contentValues, Bundle bundle) {
        ContentResolver contentResolver;
        long uptimeMillis;
        int update;
        Objects.requireNonNull(uri, "uri");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.update(uri, contentValues, bundle);
            }
            IContentProvider acquireProvider = acquireProvider(uri);
            if (acquireProvider == null) {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
            try {
                uptimeMillis = SystemClock.uptimeMillis();
                update = acquireProvider.update(this.mContext.getAttributionSource(), uri, contentValues, bundle);
                contentResolver = this;
            } catch (RemoteException unused) {
                contentResolver = this;
            } catch (Throwable th) {
                th = th;
                contentResolver = this;
            }
            try {
                contentResolver.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - uptimeMillis, uri, "update", null);
                contentResolver.releaseProvider(acquireProvider);
                return update;
            } catch (RemoteException unused2) {
                contentResolver.releaseProvider(acquireProvider);
                return -1;
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                contentResolver.releaseProvider(acquireProvider);
                throw th3;
            }
        } catch (RemoteException unused3) {
            return 0;
        }
    }

    public final Bundle call(Uri uri, String str, String str2, Bundle bundle) {
        return call(uri.getAuthority(), str, str2, bundle);
    }

    @Override // android.content.ContentInterface
    public final Bundle call(String str, String str2, String str3, Bundle bundle) {
        Objects.requireNonNull(str, ContactsContract.Directory.DIRECTORY_AUTHORITY);
        Objects.requireNonNull(str2, "method");
        try {
            ContentInterface contentInterface = this.mWrapped;
            if (contentInterface != null) {
                return contentInterface.call(str, str2, str3, bundle);
            }
            IContentProvider acquireProvider = acquireProvider(str);
            if (acquireProvider == null) {
                throw new IllegalArgumentException("Unknown authority " + str);
            }
            try {
                Bundle call = acquireProvider.call(this.mContext.getAttributionSource(), str, str2, str3, bundle);
                Bundle.setDefusable(call, true);
                return call;
            } catch (RemoteException unused) {
                return null;
            } finally {
                releaseProvider(acquireProvider);
            }
        } catch (RemoteException unused2) {
            return null;
        }
    }

    public final IContentProvider acquireProvider(Uri uri) {
        String authority;
        if ("content".equals(uri.getScheme()) && (authority = uri.getAuthority()) != null) {
            return acquireProvider(this.mContext, authority);
        }
        return null;
    }

    public final IContentProvider acquireExistingProvider(Uri uri) {
        String authority;
        if ("content".equals(uri.getScheme()) && (authority = uri.getAuthority()) != null) {
            return acquireExistingProvider(this.mContext, authority);
        }
        return null;
    }

    public final IContentProvider acquireProvider(String str) {
        if (str == null) {
            return null;
        }
        return acquireProvider(this.mContext, str);
    }

    public final IContentProvider acquireUnstableProvider(Uri uri) {
        if ("content".equals(uri.getScheme()) && uri.getAuthority() != null) {
            return acquireUnstableProvider(this.mContext, uri.getAuthority());
        }
        return null;
    }

    public final IContentProvider acquireUnstableProvider(String str) {
        if (str == null) {
            return null;
        }
        return acquireUnstableProvider(this.mContext, str);
    }

    public final ContentProviderClient acquireContentProviderClient(Uri uri) {
        Objects.requireNonNull(uri, "uri");
        IContentProvider acquireProvider = acquireProvider(uri);
        if (acquireProvider != null) {
            return new ContentProviderClient(this, acquireProvider, uri.getAuthority(), true);
        }
        return null;
    }

    public final ContentProviderClient acquireContentProviderClient(String str) {
        Objects.requireNonNull(str, "name");
        IContentProvider acquireProvider = acquireProvider(str);
        if (acquireProvider != null) {
            return new ContentProviderClient(this, acquireProvider, str, true);
        }
        return null;
    }

    public final ContentProviderClient acquireUnstableContentProviderClient(Uri uri) {
        Objects.requireNonNull(uri, "uri");
        IContentProvider acquireUnstableProvider = acquireUnstableProvider(uri);
        if (acquireUnstableProvider != null) {
            return new ContentProviderClient(this, acquireUnstableProvider, uri.getAuthority(), false);
        }
        return null;
    }

    public final ContentProviderClient acquireUnstableContentProviderClient(String str) {
        Objects.requireNonNull(str, "name");
        IContentProvider acquireUnstableProvider = acquireUnstableProvider(str);
        if (acquireUnstableProvider != null) {
            return new ContentProviderClient(this, acquireUnstableProvider, str, false);
        }
        return null;
    }

    public final void registerContentObserver(Uri uri, boolean z, ContentObserver contentObserver) {
        Objects.requireNonNull(uri, "uri");
        Objects.requireNonNull(contentObserver, "observer");
        registerContentObserver(ContentProvider.getUriWithoutUserId(uri), z, contentObserver, ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId()));
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public final void registerContentObserverAsUser(Uri uri, boolean z, ContentObserver contentObserver, UserHandle userHandle) {
        Objects.requireNonNull(uri, "uri");
        Objects.requireNonNull(contentObserver, "observer");
        Objects.requireNonNull(userHandle, "userHandle");
        registerContentObserver(ContentProvider.getUriWithoutUserId(uri), z, contentObserver, userHandle.getIdentifier());
    }

    public final void registerContentObserver(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        try {
            getContentService().registerContentObserver(uri, z, contentObserver.getContentObserver(), i, this.mTargetSdkVersion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void unregisterContentObserver(ContentObserver contentObserver) {
        Objects.requireNonNull(contentObserver, "observer");
        try {
            IContentObserver releaseContentObserver = contentObserver.releaseContentObserver();
            if (releaseContentObserver != null) {
                getContentService().unregisterContentObserver(releaseContentObserver);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver) {
        notifyChange(uri, contentObserver, true);
    }

    @Deprecated
    public void notifyChange(Uri uri, ContentObserver contentObserver, boolean z) {
        notifyChange(uri, contentObserver, z ? 1 : 0);
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver, int i) {
        Objects.requireNonNull(uri, "uri");
        notifyChange(ContentProvider.getUriWithoutUserId(uri), contentObserver, i, ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId()));
    }

    @Deprecated
    public void notifyChange(Iterable<Uri> iterable, ContentObserver contentObserver, int i) {
        final ArrayList arrayList = new ArrayList();
        iterable.forEach(new Consumer() { // from class: android.content.ContentResolver$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((Uri) obj);
            }
        });
        notifyChange((Collection<Uri>) arrayList, contentObserver, i);
    }

    public void notifyChange(Collection<Uri> collection, ContentObserver contentObserver, int i) {
        Objects.requireNonNull(collection, "uris");
        SparseArray sparseArray = new SparseArray();
        for (Uri uri : collection) {
            int userIdFromUri = ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId());
            ArrayList arrayList = (ArrayList) sparseArray.get(userIdFromUri);
            if (arrayList == null) {
                arrayList = new ArrayList();
                sparseArray.put(userIdFromUri, arrayList);
            }
            arrayList.add(ContentProvider.getUriWithoutUserId(uri));
        }
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            int keyAt = sparseArray.keyAt(i2);
            ArrayList arrayList2 = (ArrayList) sparseArray.valueAt(i2);
            notifyChange((Uri[]) arrayList2.toArray(new Uri[arrayList2.size()]), contentObserver, i, keyAt);
        }
    }

    @Deprecated
    public void notifyChange(Uri uri, ContentObserver contentObserver, boolean z, int i) {
        notifyChange(uri, contentObserver, z ? 1 : 0, i);
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver, int i, int i2) {
        notifyChange(new Uri[]{uri}, contentObserver, i, i2);
    }

    public void notifyChange(Uri[] uriArr, ContentObserver contentObserver, int i, int i2) {
        try {
            getContentService().notifyChange(uriArr, contentObserver == null ? null : contentObserver.getContentObserver(), contentObserver != null && contentObserver.deliverSelfNotifications(), i, i2, this.mTargetSdkVersion, this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void takePersistableUriPermission(Uri uri, int i) {
        Objects.requireNonNull(uri, "uri");
        try {
            UriGrantsManager.getService().takePersistableUriPermission(ContentProvider.getUriWithoutUserId(uri), i, null, resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void takePersistableUriPermission(String str, Uri uri, int i) {
        Objects.requireNonNull(str, "toPackage");
        Objects.requireNonNull(uri, "uri");
        try {
            UriGrantsManager.getService().takePersistableUriPermission(ContentProvider.getUriWithoutUserId(uri), i, str, resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releasePersistableUriPermission(Uri uri, int i) {
        Objects.requireNonNull(uri, "uri");
        try {
            UriGrantsManager.getService().releasePersistableUriPermission(ContentProvider.getUriWithoutUserId(uri), i, null, resolveUserId(uri));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UriPermission> getPersistedUriPermissions() {
        try {
            return UriGrantsManager.getService().getUriPermissions(this.mPackageName, true, true).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UriPermission> getOutgoingPersistedUriPermissions() {
        try {
            return UriGrantsManager.getService().getUriPermissions(this.mPackageName, false, true).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UriPermission> getOutgoingUriPermissions() {
        try {
            return UriGrantsManager.getService().getUriPermissions(this.mPackageName, false, false).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void startSync(Uri uri, Bundle bundle) {
        Account account;
        if (bundle != null) {
            String string = bundle.getString("account");
            account = !TextUtils.isEmpty(string) ? new Account(string, "com.google") : null;
            bundle.remove("account");
        } else {
            account = null;
        }
        requestSync(account, uri != null ? uri.getAuthority() : null, bundle);
    }

    public static void requestSync(Account account, String str, Bundle bundle) {
        requestSyncAsUser(account, str, UserHandle.myUserId(), bundle);
    }

    public static void requestSyncAsUser(Account account, String str, int i, Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Must specify extras.");
        }
        try {
            getContentService().syncAsUser(new SyncRequest.Builder().setSyncAdapter(account, str).setExtras(bundle).syncOnce().build(), i, ActivityThread.currentPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void requestSync(SyncRequest syncRequest) {
        try {
            getContentService().sync(syncRequest, ActivityThread.currentPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void validateSyncExtrasBundle(Bundle bundle) {
        try {
            Iterator<String> it = bundle.keySet().iterator();
            while (it.hasNext()) {
                Object obj = bundle.get(it.next());
                if (obj != null && !(obj instanceof Long) && !(obj instanceof Integer) && !(obj instanceof Boolean) && !(obj instanceof Float) && !(obj instanceof Double) && !(obj instanceof String) && !(obj instanceof Account)) {
                    throw new IllegalArgumentException("unexpected value type: " + obj.getClass().getName());
                }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (RuntimeException e2) {
            throw new IllegalArgumentException("error unparceling Bundle", e2);
        }
    }

    @Deprecated
    public void cancelSync(Uri uri) {
        cancelSync(null, uri != null ? uri.getAuthority() : null);
    }

    public static void cancelSync(Account account, String str) {
        try {
            getContentService().cancelSync(account, str, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void cancelSyncAsUser(Account account, String str, int i) {
        try {
            getContentService().cancelSyncAsUser(account, str, null, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static SyncAdapterType[] getSyncAdapterTypes() {
        try {
            return getContentService().getSyncAdapterTypes();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static SyncAdapterType[] getSyncAdapterTypesAsUser(int i) {
        try {
            return getContentService().getSyncAdapterTypesAsUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String[] getSyncAdapterPackagesForAuthorityAsUser(String str, int i) {
        try {
            return getContentService().getSyncAdapterPackagesForAuthorityAsUser(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String getSyncAdapterPackageAsUser(String str, String str2, int i) {
        try {
            return getContentService().getSyncAdapterPackageAsUser(str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getSyncAutomatically(Account account, String str) {
        try {
            return getContentService().getSyncAutomatically(account, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getSyncAutomaticallyAsUser(Account account, String str, int i) {
        try {
            return getContentService().getSyncAutomaticallyAsUser(account, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setSyncAutomatically(Account account, String str, boolean z) {
        setSyncAutomaticallyAsUser(account, str, z, UserHandle.myUserId());
    }

    public static void setSyncAutomaticallyAsUser(Account account, String str, boolean z, int i) {
        try {
            getContentService().setSyncAutomaticallyAsUser(account, str, z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean hasInvalidScheduleAsEjExtras(Bundle bundle) {
        return bundle.getBoolean(SYNC_EXTRAS_REQUIRE_CHARGING) || bundle.getBoolean(SYNC_EXTRAS_EXPEDITED);
    }

    public static void addPeriodicSync(Account account, String str, Bundle bundle, long j) {
        validateSyncExtrasBundle(bundle);
        if (invalidPeriodicExtras(bundle)) {
            throw new IllegalArgumentException("illegal extras were set");
        }
        try {
            getContentService().addPeriodicSync(account, str, bundle, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean invalidPeriodicExtras(Bundle bundle) {
        return bundle.getBoolean("force", false) || bundle.getBoolean(SYNC_EXTRAS_DO_NOT_RETRY, false) || bundle.getBoolean(SYNC_EXTRAS_IGNORE_BACKOFF, false) || bundle.getBoolean(SYNC_EXTRAS_IGNORE_SETTINGS, false) || bundle.getBoolean("initialize", false) || bundle.getBoolean("force", false) || bundle.getBoolean(SYNC_EXTRAS_EXPEDITED, false) || bundle.getBoolean(SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB, false);
    }

    public static void removePeriodicSync(Account account, String str, Bundle bundle) {
        validateSyncExtrasBundle(bundle);
        try {
            getContentService().removePeriodicSync(account, str, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void cancelSync(SyncRequest syncRequest) {
        if (syncRequest == null) {
            throw new IllegalArgumentException("request cannot be null");
        }
        try {
            getContentService().cancelRequest(syncRequest);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static List<PeriodicSync> getPeriodicSyncs(Account account, String str) {
        try {
            return getContentService().getPeriodicSyncs(account, str, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getIsSyncable(Account account, String str) {
        try {
            return getContentService().getIsSyncable(account, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getIsSyncableAsUser(Account account, String str, int i) {
        try {
            return getContentService().getIsSyncableAsUser(account, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setIsSyncable(Account account, String str, int i) {
        try {
            getContentService().setIsSyncable(account, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setIsSyncableAsUser(Account account, String str, int i, int i2) {
        try {
            getContentService().setIsSyncableAsUser(account, str, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getMasterSyncAutomatically() {
        try {
            return getContentService().getMasterSyncAutomatically();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean getMasterSyncAutomaticallyAsUser(int i) {
        try {
            return getContentService().getMasterSyncAutomaticallyAsUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setMasterSyncAutomatically(boolean z) {
        setMasterSyncAutomaticallyAsUser(z, UserHandle.myUserId());
    }

    public static void setMasterSyncAutomaticallyAsUser(boolean z, int i) {
        try {
            getContentService().setMasterSyncAutomaticallyAsUser(z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isSyncActive(Account account, String str) {
        if (account == null) {
            throw new IllegalArgumentException("account must not be null");
        }
        if (str == null) {
            throw new IllegalArgumentException("authority must not be null");
        }
        try {
            return getContentService().isSyncActive(account, str, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public static SyncInfo getCurrentSync() {
        try {
            List<SyncInfo> currentSyncs = getContentService().getCurrentSyncs();
            if (currentSyncs.isEmpty()) {
                return null;
            }
            return currentSyncs.get(0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static List<SyncInfo> getCurrentSyncs() {
        try {
            return getContentService().getCurrentSyncs();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static List<SyncInfo> getCurrentSyncsAsUser(int i) {
        try {
            return getContentService().getCurrentSyncsAsUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static SyncStatusInfo getSyncStatus(Account account, String str) {
        try {
            return getContentService().getSyncStatus(account, str, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static SemSyncStatusInfo semGetSyncStatusInfo(Account account, String str) {
        try {
            SyncStatusInfo syncStatus = getContentService().getSyncStatus(account, str, null);
            if (syncStatus != null) {
                return new SemSyncStatusInfo(syncStatus);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException("the ContentService should always be reachable", e);
        }
    }

    public static SyncStatusInfo getSyncStatusAsUser(Account account, String str, int i) {
        try {
            return getContentService().getSyncStatusAsUser(account, str, null, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isSyncPending(Account account, String str) {
        return isSyncPendingAsUser(account, str, UserHandle.myUserId());
    }

    public static boolean isSyncPendingAsUser(Account account, String str, int i) {
        try {
            return getContentService().isSyncPendingAsUser(account, str, null, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static Object addStatusChangeListener(int i, final SyncStatusObserver syncStatusObserver) {
        if (syncStatusObserver == null) {
            throw new IllegalArgumentException("you passed in a null callback");
        }
        try {
            ISyncStatusObserver.Stub stub = new ISyncStatusObserver.Stub() { // from class: android.content.ContentResolver.2
                @Override // android.content.ISyncStatusObserver
                public void onStatusChanged(int i2) throws RemoteException {
                    SyncStatusObserver.this.onStatusChanged(i2);
                }
            };
            getContentService().addStatusChangeListener(i, stub);
            return stub;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void removeStatusChangeListener(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("you passed in a null handle");
        }
        try {
            getContentService().removeStatusChangeListener((ISyncStatusObserver.Stub) obj);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void putCache(Uri uri, Bundle bundle) {
        try {
            getContentService().putCache(this.mContext.getPackageName(), uri, bundle, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Bundle getCache(Uri uri) {
        try {
            Bundle cache = getContentService().getCache(this.mContext.getPackageName(), uri, this.mContext.getUserId());
            if (cache != null) {
                cache.setClassLoader(this.mContext.getClassLoader());
            }
            return cache;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    private int samplePercentForDuration(long j) {
        int i = SLOW_THRESHOLD_MILLIS;
        if (j >= i) {
            return 100;
        }
        return ((int) ((j * 100) / i)) + 1;
    }

    private final class CursorWrapperInner extends CrossProcessCursorWrapper {
        private final CloseGuard mCloseGuard;
        private final IContentProvider mContentProvider;
        private final AtomicBoolean mProviderReleased;

        CursorWrapperInner(Cursor cursor, IContentProvider iContentProvider) {
            super(cursor);
            this.mProviderReleased = new AtomicBoolean();
            CloseGuard closeGuard = CloseGuard.get();
            this.mCloseGuard = closeGuard;
            this.mContentProvider = iContentProvider;
            closeGuard.open("CursorWrapperInner.close");
        }

        @Override // android.database.CursorWrapper, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mCloseGuard.close();
            super.close();
            if (this.mProviderReleased.compareAndSet(false, true)) {
                ContentResolver.this.releaseProvider(this.mContentProvider);
            }
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

    private final class ParcelFileDescriptorInner extends ParcelFileDescriptor {
        private final IContentProvider mContentProvider;
        private final AtomicBoolean mProviderReleased;

        ParcelFileDescriptorInner(ParcelFileDescriptor parcelFileDescriptor, IContentProvider iContentProvider) {
            super(parcelFileDescriptor);
            this.mProviderReleased = new AtomicBoolean();
            this.mContentProvider = iContentProvider;
        }

        @Override // android.os.ParcelFileDescriptor
        public void releaseResources() {
            if (this.mProviderReleased.compareAndSet(false, true)) {
                ContentResolver.this.releaseProvider(this.mContentProvider);
            }
        }
    }

    public static IContentService getContentService() {
        if (sContentService != null) {
            return sContentService;
        }
        sContentService = IContentService.Stub.asInterface(ServiceManager.getService("content"));
        return sContentService;
    }

    public String getPackageName() {
        return this.mContext.getOpPackageName();
    }

    public String getAttributionTag() {
        return this.mContext.getAttributionTag();
    }

    public AttributionSource getAttributionSource() {
        return this.mContext.getAttributionSource();
    }

    public int resolveUserId(Uri uri) {
        return ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId());
    }

    public int getUserId() {
        return this.mContext.getUserId();
    }

    @Deprecated
    public Drawable getTypeDrawable(String str) {
        return getTypeInfo(str).getIcon().loadDrawable(this.mContext);
    }

    public final MimeTypeInfo getTypeInfo(String str) {
        Objects.requireNonNull(str);
        return MimeIconUtils.getTypeInfo(str);
    }

    public static final class MimeTypeInfo {
        private final CharSequence mContentDescription;
        private final Icon mIcon;
        private final CharSequence mLabel;

        public MimeTypeInfo(Icon icon, CharSequence charSequence, CharSequence charSequence2) {
            this.mIcon = (Icon) Objects.requireNonNull(icon);
            this.mLabel = (CharSequence) Objects.requireNonNull(charSequence);
            this.mContentDescription = (CharSequence) Objects.requireNonNull(charSequence2);
        }

        public Icon getIcon() {
            return this.mIcon;
        }

        public CharSequence getLabel() {
            return this.mLabel;
        }

        public CharSequence getContentDescription() {
            return this.mContentDescription;
        }
    }

    public static Bundle createSqlQueryBundle(String str, String[] strArr) {
        return createSqlQueryBundle(str, strArr, null);
    }

    public static Bundle createSqlQueryBundle(String str, String[] strArr, String str2) {
        if (str == null && strArr == null && str2 == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        if (str != null) {
            bundle.putString(QUERY_ARG_SQL_SELECTION, str);
        }
        if (strArr != null) {
            bundle.putStringArray(QUERY_ARG_SQL_SELECTION_ARGS, strArr);
        }
        if (str2 != null) {
            bundle.putString(QUERY_ARG_SQL_SORT_ORDER, str2);
        }
        return bundle;
    }

    public static Bundle includeSqlSelectionArgs(Bundle bundle, String str, String[] strArr) {
        if (str != null) {
            bundle.putString(QUERY_ARG_SQL_SELECTION, str);
        }
        if (strArr != null) {
            bundle.putStringArray(QUERY_ARG_SQL_SELECTION_ARGS, strArr);
        }
        return bundle;
    }

    public static String createSqlSortClause(Bundle bundle) {
        String[] stringArray = bundle.getStringArray(QUERY_ARG_SORT_COLUMNS);
        if (stringArray == null || stringArray.length == 0) {
            throw new IllegalArgumentException("Can't create sort clause without columns.");
        }
        String join = TextUtils.join(", ", stringArray);
        int i = bundle.getInt(QUERY_ARG_SORT_COLLATION, 3);
        if (i == 0 || i == 1) {
            join = join + " COLLATE NOCASE";
        }
        int i2 = bundle.getInt(QUERY_ARG_SORT_DIRECTION, Integer.MIN_VALUE);
        if (i2 == Integer.MIN_VALUE) {
            return join;
        }
        if (i2 == 0) {
            return join + " ASC";
        }
        if (i2 == 1) {
            return join + " DESC";
        }
        throw new IllegalArgumentException("Unsupported sort direction value. See ContentResolver documentation for details.");
    }

    public Bitmap loadThumbnail(Uri uri, Size size, CancellationSignal cancellationSignal) throws IOException {
        return loadThumbnail(this, uri, size, cancellationSignal, 1);
    }

    public static Bitmap loadThumbnail(final ContentInterface contentInterface, final Uri uri, final Size size, final CancellationSignal cancellationSignal, final int i) throws IOException {
        Objects.requireNonNull(contentInterface);
        Objects.requireNonNull(uri);
        Objects.requireNonNull(size);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SIZE, new Point(size.getWidth(), size.getHeight()));
        final Int64Ref int64Ref = new Int64Ref(0L);
        Bitmap decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource((Callable<AssetFileDescriptor>) new Callable() { // from class: android.content.ContentResolver$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ContentResolver.lambda$loadThumbnail$0(ContentInterface.this, uri, bundle, cancellationSignal, int64Ref);
            }
        }), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.content.ContentResolver$$ExternalSyntheticLambda1
            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                ContentResolver.lambda$loadThumbnail$1(i, cancellationSignal, size, imageDecoder, imageInfo, source);
            }
        });
        if (int64Ref.value == 0) {
            return decodeBitmap;
        }
        int width = decodeBitmap.getWidth();
        int height = decodeBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(int64Ref.value, width / 2, height / 2);
        return Bitmap.createBitmap(decodeBitmap, 0, 0, width, height, matrix, false);
    }

    static /* synthetic */ AssetFileDescriptor lambda$loadThumbnail$0(ContentInterface contentInterface, Uri uri, Bundle bundle, CancellationSignal cancellationSignal, Int64Ref int64Ref) throws Exception {
        AssetFileDescriptor openTypedAssetFile = contentInterface.openTypedAssetFile(uri, ContentType.IMAGE_UNSPECIFIED, bundle, cancellationSignal);
        int64Ref.value = openTypedAssetFile.getExtras() != null ? r2.getInt(DocumentsContract.EXTRA_ORIENTATION, 0) : 0L;
        return openTypedAssetFile;
    }

    static /* synthetic */ void lambda$loadThumbnail$1(int i, CancellationSignal cancellationSignal, Size size, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(i);
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        int max = Math.max(imageInfo.getSize().getWidth() / size.getWidth(), imageInfo.getSize().getHeight() / size.getHeight());
        if (max > 1) {
            imageDecoder.setTargetSampleSize(max);
        }
    }

    public static void onDbCorruption(String str, String str2, Throwable th) {
        try {
            getContentService().onDbCorruption(str, str2, Log.getStackTraceString(th));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static Uri decodeFromFile(File file) {
        return translateDeprecatedDataPath(file.getAbsolutePath());
    }

    @SystemApi
    public static File encodeToFile(Uri uri) {
        return new File(translateDeprecatedDataPath(uri));
    }

    public static Uri translateDeprecatedDataPath(String str) {
        return Uri.parse(new Uri.Builder().scheme("content").encodedOpaquePart("//" + str.substring(13)).build().toString());
    }

    public static String translateDeprecatedDataPath(Uri uri) {
        return DEPRECATE_DATA_PREFIX + uri.getEncodedSchemeSpecificPart().substring(2);
    }
}
