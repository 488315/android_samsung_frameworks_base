package android.media;

import android.app.jank.AppJankStats;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ServiceConnection;
import android.media.IMediaScannerListener;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import com.android.internal.os.BackgroundThread;
import java.io.File;

/* loaded from: classes2.dex */
public class MediaScannerConnection implements ServiceConnection {
    private static final String TAG = "MediaScannerConnection";
    private final MediaScannerConnectionClient mClient;

    @Deprecated
    private boolean mConnected;
    private final Context mContext;

    @Deprecated
    private final IMediaScannerListener.Stub mListener = new IMediaScannerListener.Stub(this) { // from class: android.media.MediaScannerConnection.1
        @Override // android.media.IMediaScannerListener
        public void scanCompleted(String str, Uri uri) {
        }
    };
    private ContentProviderClient mProvider;

    @Deprecated
    private IMediaScannerService mService;

    public interface MediaScannerConnectionClient extends OnScanCompletedListener {
        void onMediaScannerConnected();
    }

    public interface OnScanCompletedListener {
        void onScanCompleted(String str, Uri uri);
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }

    public MediaScannerConnection(Context context, MediaScannerConnectionClient mediaScannerConnectionClient) {
        this.mContext = context;
        this.mClient = mediaScannerConnectionClient;
    }

    public void connect() {
        synchronized (this) {
            if (this.mProvider == null) {
                this.mProvider = this.mContext.getContentResolver().acquireContentProviderClient(AppJankStats.WIDGET_CATEGORY_MEDIA);
                MediaScannerConnectionClient mediaScannerConnectionClient = this.mClient;
                if (mediaScannerConnectionClient != null) {
                    mediaScannerConnectionClient.onMediaScannerConnected();
                }
            }
        }
    }

    public void disconnect() {
        synchronized (this) {
            ContentProviderClient contentProviderClient = this.mProvider;
            if (contentProviderClient != null) {
                contentProviderClient.close();
                this.mProvider = null;
            }
        }
    }

    public synchronized boolean isConnected() {
        return this.mProvider != null;
    }

    public void scanFile(final String str, String str2) {
        synchronized (this) {
            if (this.mProvider == null) {
                throw new IllegalStateException("not connected to MediaScannerService");
            }
            BackgroundThread.getExecutor().execute(new Runnable() { // from class: android.media.MediaScannerConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaScannerConnection.this.lambda$scanFile$0(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanFile$0(String str) {
        runCallBack(this.mContext, this.mClient, str, scanFileQuietly(this.mProvider, new File(str)));
    }

    public static void scanFile(final Context context, final String[] strArr, String[] strArr2, final OnScanCompletedListener onScanCompletedListener) {
        BackgroundThread.getExecutor().execute(new Runnable() { // from class: android.media.MediaScannerConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MediaScannerConnection.lambda$scanFile$1(Context.this, strArr, onScanCompletedListener);
            }
        });
    }

    static /* synthetic */ void lambda$scanFile$1(Context context, String[] strArr, OnScanCompletedListener onScanCompletedListener) {
        ContentProviderClient acquireContentProviderClient = context.getContentResolver().acquireContentProviderClient(AppJankStats.WIDGET_CATEGORY_MEDIA);
        try {
            for (String str : strArr) {
                runCallBack(context, onScanCompletedListener, str, scanFileQuietly(acquireContentProviderClient, new File(str)));
            }
            if (acquireContentProviderClient != null) {
                acquireContentProviderClient.close();
            }
        } catch (Throwable th) {
            if (acquireContentProviderClient != null) {
                try {
                    acquireContentProviderClient.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static Uri scanFileQuietly(ContentProviderClient contentProviderClient, File file) {
        Uri uri = null;
        try {
            uri = MediaStore.scanFile(ContentResolver.wrap(contentProviderClient), file.getCanonicalFile());
            Log.d(TAG, "Scanned " + file + " to " + uri);
            return uri;
        } catch (Exception e) {
            Log.w(TAG, "Failed to scan " + file + ": " + e);
            return uri;
        }
    }

    private static void runCallBack(Context context, OnScanCompletedListener onScanCompletedListener, String str, Uri uri) {
        if (onScanCompletedListener != null) {
            try {
                onScanCompletedListener.onScanCompleted(str, uri);
            } catch (Throwable th) {
                if (context.getApplicationInfo().targetSdkVersion >= 30) {
                    throw th;
                }
                Log.w(TAG, "Ignoring exception from callback for backward compatibility", th);
            }
        }
    }

    @Deprecated
    static class ClientProxy implements MediaScannerConnectionClient {
        final OnScanCompletedListener mClient;
        MediaScannerConnection mConnection;
        final String[] mMimeTypes;
        int mNextPath;
        final String[] mPaths;

        @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
        public void onMediaScannerConnected() {
        }

        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
        public void onScanCompleted(String str, Uri uri) {
        }

        void scanNextPath() {
        }

        ClientProxy(String[] strArr, String[] strArr2, OnScanCompletedListener onScanCompletedListener) {
            this.mPaths = strArr;
            this.mMimeTypes = strArr2;
            this.mClient = onScanCompletedListener;
        }
    }
}
