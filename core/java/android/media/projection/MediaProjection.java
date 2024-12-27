package android.media.projection;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.os.Handler;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.view.Surface;

import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MediaProjection {
    static final long MEDIA_PROJECTION_REQUIRES_CALLBACK = 269849258;
    private static final String TAG = "MediaProjection";
    private final Map<Callback, CallbackRecord> mCallbacks;
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final IMediaProjection mImpl;

    public MediaProjection(Context context, IMediaProjection impl) {
        this(context, impl, (DisplayManager) context.getSystemService(DisplayManager.class));
    }

    public MediaProjection(Context context, IMediaProjection impl, DisplayManager displayManager) {
        this.mCallbacks = new ArrayMap();
        this.mContext = context;
        this.mImpl = impl;
        try {
            this.mImpl.start(new MediaProjectionCallback());
            this.mDisplayManager = displayManager;
        } catch (RemoteException e) {
            Log.e(TAG, "Content Recording: Failed to start media projection", e);
            throw new RuntimeException("Failed to start media projection", e);
        }
    }

    public void registerCallback(Callback callback, Handler handler) {
        try {
            Callback c = (Callback) Objects.requireNonNull(callback);
            if (handler == null) {
                handler = new Handler(this.mContext.getMainLooper());
            }
            this.mCallbacks.put(c, new CallbackRecord(c, handler));
        } catch (NullPointerException e) {
            Log.e(TAG, "Content Recording: cannot register null Callback", e);
            throw e;
        } catch (RuntimeException e2) {
            Log.e(TAG, "Content Recording: failed to create new Handler to register Callback", e2);
        }
    }

    public void unregisterCallback(Callback callback) {
        try {
            Callback c = (Callback) Objects.requireNonNull(callback);
            this.mCallbacks.remove(c);
        } catch (NullPointerException e) {
            Log.d(TAG, "Content Recording: cannot unregister null Callback", e);
            throw e;
        }
    }

    public VirtualDisplay createVirtualDisplay(
            String name,
            int width,
            int height,
            int dpi,
            boolean isSecure,
            Surface surface,
            VirtualDisplay.Callback callback,
            Handler handler) {
        int flags = 18;
        if (isSecure) {
            flags = 18 | 4;
        }
        VirtualDisplayConfig.Builder builder =
                new VirtualDisplayConfig.Builder(name, width, height, dpi).setFlags(flags);
        if (surface != null) {
            builder.setSurface(surface);
        }
        return createVirtualDisplay(builder, callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(
            String name,
            int width,
            int height,
            int dpi,
            int flags,
            Surface surface,
            VirtualDisplay.Callback callback,
            Handler handler) {
        if (shouldMediaProjectionRequireCallback() && this.mCallbacks.isEmpty()) {
            IllegalStateException e =
                    new IllegalStateException(
                            "Must register a callback before starting capture, to manage resources"
                                    + " in response to MediaProjection states.");
            Log.e(TAG, "Content Recording: no callback registered for virtual display", e);
            throw e;
        }
        VirtualDisplayConfig.Builder builder =
                new VirtualDisplayConfig.Builder(name, width, height, dpi).setFlags(flags);
        if (surface != null) {
            builder.setSurface(surface);
        }
        Display display = this.mContext.getDisplayNoVerify();
        boolean isDualMode = this.mContext.getResources().getConfiguration().dexMode == 2;
        if ((display != null && display.getDisplayId() == 2) || isDualMode) {
            builder.setDisplayIdToMirror(2);
        }
        return createVirtualDisplay(builder, callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(
            VirtualDisplayConfig.Builder virtualDisplayConfig,
            VirtualDisplay.Callback callback,
            Handler handler) {
        virtualDisplayConfig.setWindowManagerMirroringEnabled(true);
        VirtualDisplay virtualDisplay =
                this.mDisplayManager.createVirtualDisplay(
                        this, virtualDisplayConfig.build(), callback, handler);
        if (virtualDisplay == null) {
            Slog.w(TAG, "Failed to create virtual display.");
            return null;
        }
        return virtualDisplay;
    }

    private boolean shouldMediaProjectionRequireCallback() {
        return CompatChanges.isChangeEnabled(MEDIA_PROJECTION_REQUIRES_CALLBACK);
    }

    public void stop() {
        try {
            Log.d(TAG, "Content Recording: stopping projection");
            this.mImpl.stop();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to stop projection", e);
        }
    }

    public IMediaProjection getProjection() {
        return this.mImpl;
    }

    public abstract static class Callback {
        public void onStop() {}

        public void onCapturedContentResize(int width, int height) {}

        public void onCapturedContentVisibilityChanged(boolean isVisible) {}
    }

    private final class MediaProjectionCallback extends IMediaProjectionCallback.Stub {
        private MediaProjectionCallback() {}

        @Override // android.media.projection.IMediaProjectionCallback
        public void onStop() {
            Slog.v(
                    MediaProjection.TAG,
                    "Dispatch stop to " + MediaProjection.this.mCallbacks.size() + " callbacks.");
            for (CallbackRecord cbr : MediaProjection.this.mCallbacks.values()) {
                cbr.onStop();
            }
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentResize(int width, int height) {
            for (CallbackRecord cbr : MediaProjection.this.mCallbacks.values()) {
                cbr.onCapturedContentResize(width, height);
            }
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentVisibilityChanged(boolean isVisible) {
            for (CallbackRecord cbr : MediaProjection.this.mCallbacks.values()) {
                cbr.onCapturedContentVisibilityChanged(isVisible);
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
            this.mHandler.post(
                    new Runnable() { // from class:
                        // android.media.projection.MediaProjection.CallbackRecord.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CallbackRecord.this.mCallback.onStop();
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturedContentResize$0(int width, int height) {
            this.mCallback.onCapturedContentResize(width, height);
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onCapturedContentResize(final int width, final int height) {
            this.mHandler.post(
                    new Runnable() { // from class:
                        // android.media.projection.MediaProjection$CallbackRecord$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaProjection.CallbackRecord.this.lambda$onCapturedContentResize$0(
                                    width, height);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapturedContentVisibilityChanged$1(boolean isVisible) {
            this.mCallback.onCapturedContentVisibilityChanged(isVisible);
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onCapturedContentVisibilityChanged(final boolean isVisible) {
            this.mHandler.post(
                    new Runnable() { // from class:
                        // android.media.projection.MediaProjection$CallbackRecord$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaProjection.CallbackRecord.this
                                    .lambda$onCapturedContentVisibilityChanged$1(isVisible);
                        }
                    });
        }
    }
}
