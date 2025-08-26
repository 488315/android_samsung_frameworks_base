package android.media.projection;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.media.projection.IMediaProjectionCallback;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.Surface;
import com.android.media.projection.flags.Flags;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class MediaProjection {
    static final long MEDIA_PROJECTION_REQUIRES_CALLBACK = 269849258;
    private static final String TAG = "MediaProjection";
    private final Map<Callback, CallbackRecord> mCallbacks;
    private final Context mContext;
    private final int mDisplayId;
    private final DisplayManager mDisplayManager;
    private final IMediaProjection mImpl;

    public static abstract class Callback {
        public void onCapturedContentResize(int i, int i2) {
        }

        public void onCapturedContentVisibilityChanged(boolean z) {
        }

        public void onStop() {
        }
    }

    public MediaProjection(Context context, IMediaProjection iMediaProjection) {
        this(context, iMediaProjection, (DisplayManager) context.getSystemService(DisplayManager.class));
    }

    public MediaProjection(Context context, IMediaProjection iMediaProjection, DisplayManager displayManager) {
        int displayId;
        this.mCallbacks = new ArrayMap();
        this.mContext = context;
        this.mImpl = iMediaProjection;
        this.mDisplayManager = displayManager;
        try {
            iMediaProjection.start(new MediaProjectionCallback());
            if (!Flags.mediaProjectionConnectedDisplay() || (displayId = iMediaProjection.getDisplayId()) == 0) {
                UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
                this.mDisplayId = userManager.isVisibleBackgroundUsersSupported() ? userManager.getMainDisplayIdAssignedToUser() : 0;
            } else {
                this.mDisplayId = displayId;
                Log.v(TAG, "Created MediaProjection for display " + displayId);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Content Recording: Failed to start media projection", e);
            throw new RuntimeException("Failed to start media projection", e);
        }
    }

    public void registerCallback(Callback callback, Handler handler) {
        try {
            Callback callback2 = (Callback) Objects.requireNonNull(callback);
            if (handler == null) {
                handler = new Handler(this.mContext.getMainLooper());
            }
            this.mCallbacks.put(callback2, new CallbackRecord(callback2, handler));
        } catch (NullPointerException e) {
            Log.e(TAG, "Content Recording: cannot register null Callback", e);
            throw e;
        } catch (RuntimeException e2) {
            Log.e(TAG, "Content Recording: failed to create new Handler to register Callback", e2);
        }
    }

    public void unregisterCallback(Callback callback) {
        try {
            this.mCallbacks.remove((Callback) Objects.requireNonNull(callback));
        } catch (NullPointerException e) {
            Log.d(TAG, "Content Recording: cannot unregister null Callback", e);
            throw e;
        }
    }

    public VirtualDisplay createVirtualDisplay(String str, int i, int i2, int i3, boolean z, Surface surface, VirtualDisplay.Callback callback, Handler handler) {
        VirtualDisplayConfig.Builder flags = new VirtualDisplayConfig.Builder(str, i, i2, i3).setFlags(z ? 22 : 18);
        if (surface != null) {
            flags.setSurface(surface);
        }
        flags.setDisplayIdToMirror(this.mDisplayId);
        return createVirtualDisplay(flags, callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(String str, int i, int i2, int i3, int i4, Surface surface, VirtualDisplay.Callback callback, Handler handler) {
        if (shouldMediaProjectionRequireCallback() && this.mCallbacks.isEmpty()) {
            IllegalStateException illegalStateException = new IllegalStateException("Must register a callback before starting capture, to manage resources in response to MediaProjection states.");
            Log.e(TAG, "Content Recording: no callback registered for virtual display", illegalStateException);
            throw illegalStateException;
        }
        VirtualDisplayConfig.Builder flags = new VirtualDisplayConfig.Builder(str, i, i2, i3).setFlags(i4);
        if (surface != null) {
            flags.setSurface(surface);
        }
        flags.setDisplayIdToMirror(this.mDisplayId);
        return createVirtualDisplay(flags, callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(VirtualDisplayConfig.Builder builder, VirtualDisplay.Callback callback, Handler handler) {
        builder.setWindowManagerMirroringEnabled(true);
        VirtualDisplay virtualDisplayCreateVirtualDisplay = this.mDisplayManager.createVirtualDisplay(this, builder.build(), callback, handler);
        if (virtualDisplayCreateVirtualDisplay != null) {
            return virtualDisplayCreateVirtualDisplay;
        }
        Slog.w(TAG, "Failed to create virtual display.");
        return null;
    }

    private boolean shouldMediaProjectionRequireCallback() {
        return CompatChanges.isChangeEnabled(MEDIA_PROJECTION_REQUIRES_CALLBACK);
    }

    public void stop() {
        try {
            Log.d(TAG, "Content Recording: stopping projection");
            this.mImpl.stop(1);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to stop projection", e);
        }
    }

    public void stop(int i) {
        try {
            Log.d(TAG, "Content Recording: stopping projection");
            this.mImpl.stop(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to stop projection", e);
        }
    }

    public IMediaProjection getProjection() {
        return this.mImpl;
    }

    private final class MediaProjectionCallback extends IMediaProjectionCallback.Stub {
        private MediaProjectionCallback() {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onStop() {
            Slog.v(MediaProjection.TAG, "Dispatch stop to " + MediaProjection.this.mCallbacks.size() + " callbacks.");
            Iterator it = MediaProjection.this.mCallbacks.values().iterator();
            while (it.hasNext()) {
                ((CallbackRecord) it.next()).onStop();
            }
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentResize(int i, int i2) {
            Iterator it = MediaProjection.this.mCallbacks.values().iterator();
            while (it.hasNext()) {
                ((CallbackRecord) it.next()).onCapturedContentResize(i, i2);
            }
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentVisibilityChanged(boolean z) {
            Iterator it = MediaProjection.this.mCallbacks.values().iterator();
            while (it.hasNext()) {
                ((CallbackRecord) it.next()).onCapturedContentVisibilityChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackRecord extends Callback {
        private final Callback mCallback;
        private final Handler mHandler;

        public CallbackRecord(Callback callback, Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onStop() {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjection.CallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    CallbackRecord.this.mCallback.onStop();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturedContentResize$0(int i, int i2) {
            this.mCallback.onCapturedContentResize(i, i2);
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onCapturedContentResize(final int i, final int i2) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjection$CallbackRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCapturedContentResize$0(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturedContentVisibilityChanged$1(boolean z) {
            this.mCallback.onCapturedContentVisibilityChanged(z);
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onCapturedContentVisibilityChanged(final boolean z) {
            this.mHandler.post(new Runnable() { // from class: android.media.projection.MediaProjection$CallbackRecord$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCapturedContentVisibilityChanged$1(z);
                }
            });
        }
    }
}
