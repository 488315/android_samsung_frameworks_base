package com.android.systemui.wallpaper.view;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.service.wallpaper.IWallpaperConnection;
import android.service.wallpaper.IWallpaperEngine;
import android.service.wallpaper.IWallpaperService;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.FixedOrientationController;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpaper.view.KeyguardLiveWallpaper;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardLiveWallpaper extends SystemUIWallpaper {
    public ComponentName mComponentName;
    public final Context mContext;
    public final FixedOrientationController mFixedOrientationController;
    public final LockPatternUtils mLockPatternUtils;
    public final WallpaperLogger mLogger;
    public final Handler mMainHandler;
    public final KeyguardLiveWallpaper$$ExternalSyntheticLambda0 mRunnableAttachService;
    public KeyguardLiveWallpaper$$ExternalSyntheticLambda0 mRunnableUpdateThumbnail;
    public KeyguardLiveWallpaper$$ExternalSyntheticLambda1 mRunnableUpdateVisibility;
    public final SettingsHelper mSettingsHelper;
    public boolean mShowing;
    public BitmapDrawable mThumbnail;
    public final ImageView mThumbnailView;
    public int mThumbnailVisibility;
    public final int mUserId;
    public WallpaperConnection mWallpaperConnection;
    public final Intent mWallpaperIntent;

    public KeyguardLiveWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, WallpaperLogger wallpaperLogger, Consumer<Boolean> consumer, boolean z, boolean z2, int i, boolean z3, SettingsHelper settingsHelper) {
        super(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, z3, z, z2);
        this.mThumbnailVisibility = -1;
        this.mRunnableAttachService = new KeyguardLiveWallpaper$$ExternalSyntheticLambda0(this, 0);
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mFixedOrientationController = null;
        this.mContext = context;
        this.mIsKeyguardShowing = z2;
        Intent intent = new Intent("android.service.wallpaper.WallpaperService");
        this.mWallpaperIntent = intent;
        this.mUserId = i;
        this.mComponentName = WallpaperManager.getInstance(context).semGetWallpaperComponent(LsRune.WALLPAPER_SUB_WATCHFACE ? 6 : WallpaperUtils.sCurrentWhich, i);
        this.mLogger = wallpaperLogger;
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("KeyguardLiveWallpaper : userId = ", i, ", componentName = ");
        m1m.append(this.mComponentName);
        m1m.append(", mIsKeyguardShowing = ");
        m1m.append(this.mIsKeyguardShowing);
        ((WallpaperLoggerImpl) wallpaperLogger).log("KeyguardLiveWallpaper", m1m.toString());
        this.mSettingsHelper = settingsHelper;
        ComponentName componentName = this.mComponentName;
        if (componentName == null) {
            return;
        }
        intent.setComponent(componentName);
        this.mLockPatternUtils = new LockPatternUtils(context);
        requestAttachService();
        ImageView imageView = new ImageView(context);
        this.mThumbnailView = imageView;
        imageView.setVisibility(4);
        this.mThumbnailVisibility = 4;
        addView(imageView, new FrameLayout.LayoutParams(-1, -1));
        this.mFixedOrientationController = new FixedOrientationController(this, imageView);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void cleanUp() {
        this.mMainHandler.post(new KeyguardLiveWallpaper$$ExternalSyntheticLambda0(this, 1));
        ImageView imageView = this.mThumbnailView;
        if (imageView != null) {
            imageView.setBackground(null);
            this.mThumbnail = null;
        }
        if (this.mRunnableUpdateThumbnail != null) {
            Log.i("KeyguardLiveWallpaper", "updateThumbnail, remove runnable");
            this.mMainHandler.removeCallbacks(this.mRunnableUpdateThumbnail);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void dispatchWallpaperCommand(String str) {
        WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
        if (wallpaperConnection == null || wallpaperConnection.mEngine == null) {
            return;
        }
        try {
            Log.i(wallpaperConnection.TAG, "dispatchWallpaperCommand : ".concat(str));
            wallpaperConnection.mEngine.dispatchWallpaperCommand(str, 0, 0, 0, (Bundle) null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void handleTouchEvent(MotionEvent motionEvent) {
        IWallpaperEngine iWallpaperEngine;
        WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
        if (wallpaperConnection == null || (iWallpaperEngine = wallpaperConnection.mEngine) == null) {
            return;
        }
        try {
            iWallpaperEngine.dispatchPointer(motionEvent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FixedOrientationController fixedOrientationController = this.mFixedOrientationController;
        if (fixedOrientationController != null) {
            fixedOrientationController.clearPortraitRotation();
            this.mFixedOrientationController.applyPortraitRotation();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mMainHandler.post(new KeyguardLiveWallpaper$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onFingerprintAuthSuccess(boolean z) {
        if (this.mWallpaperConnection == null || WallpaperUtils.isAODShowLockWallpaperEnabled()) {
            return;
        }
        if (!z || this.mSettingsHelper.isAODShown()) {
            if (!this.mUpdateMonitor.isUnlockingWithFingerprintAllowed()) {
                Log.i("KeyguardLiveWallpaper", "onFingerprintAuthSuccess: fingerprint unlock not allowed now");
            } else {
                Log.i("KeyguardLiveWallpaper", "onFingerprintAuthSuccess: hide wallpaper surface");
                this.mWallpaperConnection.setSurfaceAlpha(0.0f);
            }
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onKeyguardShowing(boolean z) {
        this.mIsKeyguardShowing = z;
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onKeyguardShowing = ", z, "KeyguardLiveWallpaper");
        WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
        if (wallpaperConnection != null && wallpaperConnection.mAlpha == 0.0f && z) {
            updateVisibilityOnUiThread(true);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onLayout called : ", i, " , ", i2, " , "), i3, " , ", i4, "KeyguardLiveWallpaper");
        WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
        if (wallpaperConnection != null) {
            if (!wallpaperConnection.mIsEngineAttached && getWidth() > 0 && getHeight() > 0) {
                this.mWallpaperConnection.attachWindow();
            }
            WallpaperConnection wallpaperConnection2 = this.mWallpaperConnection;
            int width = getWidth();
            int height = getHeight();
            IWallpaperEngine iWallpaperEngine = wallpaperConnection2.mEngine;
            if (iWallpaperEngine != null) {
                try {
                    iWallpaperEngine.setDesiredSize(width, height);
                } catch (RemoteException e) {
                    Log.w(wallpaperConnection2.TAG, "Failure to setDesiredSize ", e);
                }
            }
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        boolean z = true;
        if (i == 8) {
            this.mShowing = false;
        } else {
            this.mShowing = (i == 0 && this.mIsKeyguardShowing) || (this.mUpdateMonitor.mKeyguardShowing && WallpaperUtils.isAODShowLockWallpaperEnabled() && !this.mUpdateMonitor.mDeviceInteractive);
        }
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("onVisibilityChanged called : visibility=", i, " , showingAndNotOccluded=");
        m1m.append(this.mIsKeyguardShowing);
        m1m.append(" , showing=");
        m1m.append(this.mUpdateMonitor.mKeyguardShowing);
        m1m.append(" , isDeviceInteractive=");
        m1m.append(this.mUpdateMonitor.mDeviceInteractive);
        m1m.append(" , connState=");
        WallpaperConnection wallpaperConnection = this.mWallpaperConnection;
        m1m.append(wallpaperConnection != null ? wallpaperConnection.mConnectionState : null);
        m1m.append(" , view=");
        m1m.append(view);
        Log.d("KeyguardLiveWallpaper", m1m.toString());
        WallpaperConnection wallpaperConnection2 = this.mWallpaperConnection;
        if (wallpaperConnection2 != null) {
            WallpaperConnection.ConnectionState connectionState = wallpaperConnection2.mConnectionState;
            if (connectionState != WallpaperConnection.ConnectionState.CONNECTING && connectionState != WallpaperConnection.ConnectionState.CONNECTED) {
                z = false;
            }
            if (z) {
                setThumbnailVisibility(this.mShowing ? 4 : 0);
                updateVisibilityOnUiThread(this.mShowing);
                return;
            }
        }
        if (this.mShowing) {
            ((WallpaperLoggerImpl) this.mLogger).log("KeyguardLiveWallpaper", "onVisibilityChanged : service is disconnected, so try to reconnect");
            requestAttachService();
        }
    }

    public final void requestAttachService() {
        this.mMainHandler.post(new KeyguardLiveWallpaper$$ExternalSyntheticLambda0(this, 1));
        this.mMainHandler.removeCallbacks(this.mRunnableAttachService);
        this.mMainHandler.post(this.mRunnableAttachService);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void setThumbnailVisibility(int i) {
        if (!this.mIsKeyguardShowing) {
            ImageView imageView = this.mThumbnailView;
            if (imageView != null) {
                imageView.setVisibility(4);
                this.mThumbnailVisibility = 4;
                return;
            }
            return;
        }
        if (this.mThumbnailView == null || this.mThumbnailVisibility == i || this.mThumbnail == null) {
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(new StringBuilder("setThumbnailVisibility prev = "), this.mThumbnailVisibility, " , new = ", i, "KeyguardLiveWallpaper");
        this.mThumbnailView.setBackground(this.mThumbnail);
        this.mThumbnailView.setVisibility(i);
        this.mThumbnailVisibility = i;
        invalidate();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void update() {
        ComponentName semGetWallpaperComponent = WallpaperManager.getInstance(this.mContext).semGetWallpaperComponent(LsRune.WALLPAPER_SUB_WATCHFACE ? 6 : WallpaperUtils.sCurrentWhich, this.mUserId);
        Log.i("KeyguardLiveWallpaper", "update: new comp = " + semGetWallpaperComponent + " , prev comp = " + this.mComponentName);
        ComponentName componentName = this.mComponentName;
        if (componentName != null && componentName.equals(semGetWallpaperComponent)) {
            dispatchWallpaperCommand("android.wallpaper.reapply");
        } else if (semGetWallpaperComponent != null && !semGetWallpaperComponent.equals(this.mComponentName)) {
            this.mWallpaperIntent.setComponent(semGetWallpaperComponent);
            requestAttachService();
        }
        this.mComponentName = semGetWallpaperComponent;
        WallpaperResultCallback wallpaperResultCallback = this.mWallpaperResultCallback;
        if (wallpaperResultCallback != null) {
            wallpaperResultCallback.onPreviewReady();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateDrawState(boolean z) {
        if (this.mShowing && z && this.mWallpaperConnection == null) {
            ((WallpaperLoggerImpl) this.mLogger).log("KeyguardLiveWallpaper", "updateDrawState : service is disconnected, so try to reconnect");
            requestAttachService();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateThumbnail() {
        if (LsRune.WALLPAPER_SUB_WATCHFACE && WhichChecker.isSubDisplay(WallpaperUtils.sCurrentWhich)) {
            ((WallpaperLoggerImpl) this.mLogger).log("KeyguardLiveWallpaper", "updateThumbnail failed if invalid which.");
            return;
        }
        if (this.mRunnableUpdateThumbnail != null) {
            Log.i("KeyguardLiveWallpaper", "updateThumbnail, remove runnable");
            this.mMainHandler.removeCallbacks(this.mRunnableUpdateThumbnail);
        }
        KeyguardLiveWallpaper$$ExternalSyntheticLambda0 keyguardLiveWallpaper$$ExternalSyntheticLambda0 = new KeyguardLiveWallpaper$$ExternalSyntheticLambda0(this, 2);
        this.mRunnableUpdateThumbnail = keyguardLiveWallpaper$$ExternalSyntheticLambda0;
        this.mMainHandler.post(keyguardLiveWallpaper$$ExternalSyntheticLambda0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wallpaper.view.KeyguardLiveWallpaper$$ExternalSyntheticLambda1, java.lang.Runnable] */
    public final void updateVisibilityOnUiThread(final boolean z) {
        if (this.mRunnableUpdateVisibility != null) {
            Log.i("KeyguardLiveWallpaper", "updateVisibilityOnUiThread, remove runnable");
            this.mMainHandler.removeCallbacks(this.mRunnableUpdateVisibility);
        }
        ?? r0 = new Runnable() { // from class: com.android.systemui.wallpaper.view.KeyguardLiveWallpaper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardLiveWallpaper keyguardLiveWallpaper = KeyguardLiveWallpaper.this;
                boolean z2 = z;
                keyguardLiveWallpaper.mRunnableUpdateVisibility = null;
                if (keyguardLiveWallpaper.mWallpaperConnection == null) {
                    return;
                }
                if (!keyguardLiveWallpaper.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
                    keyguardLiveWallpaper.mWallpaperConnection.setSurfaceAlpha(z2 ? 1.0f : 0.0f);
                } else if (z2) {
                    keyguardLiveWallpaper.mWallpaperConnection.setSurfaceAlpha(1.0f);
                }
                KeyguardLiveWallpaper.WallpaperConnection wallpaperConnection = keyguardLiveWallpaper.mWallpaperConnection;
                int i = KeyguardLiveWallpaper.WallpaperConnection.$r8$clinit;
                wallpaperConnection.setEngineVisibility(z2);
            }
        };
        this.mRunnableUpdateVisibility = r0;
        this.mMainHandler.post(r0);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WallpaperConnection extends IWallpaperConnection.Stub implements ServiceConnection {
        public static final /* synthetic */ int $r8$clinit = 0;
        public IWallpaperEngine mEngine;
        public final Intent mIntent;
        public boolean mIsEngineVisible;
        public boolean mIsReleaseRequested;
        public boolean mIsVisible;
        public final Listener mListener;
        public IWallpaperService mService;
        public final Binder mToken = new Binder();
        public ConnectionState mConnectionState = ConnectionState.DISCONNECTED;
        public boolean mIsEngineAttached = false;
        public float mAlpha = -1.0f;
        public final String TAG = "KeyguardLiveWallpaper.conn@" + Integer.toHexString(hashCode() & CustomDeviceManager.QUICK_PANEL_ALL);

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        enum ConnectionState {
            DISCONNECTED,
            CONNECTING,
            CONNECTED
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface Listener {
        }

        public WallpaperConnection(Intent intent, Listener listener) {
            this.mIntent = intent;
            this.mListener = listener;
        }

        public final void attachEngine(IWallpaperEngine iWallpaperEngine, int i) {
            synchronized (this) {
                if (this.mConnectionState != ConnectionState.DISCONNECTED && !this.mIsReleaseRequested) {
                    Log.i(this.TAG, "attachEngine: connectedEngine=" + iWallpaperEngine + ", prevEngine=" + this.mEngine);
                    this.mEngine = iWallpaperEngine;
                    if (this.mIsVisible) {
                        setEngineVisibility(true);
                    }
                    return;
                }
                Log.i(this.TAG, "attachEngine: service disconnected or released. isReleased=" + this.mIsReleaseRequested);
            }
        }

        public final void attachWindow() {
            synchronized (this) {
                if (this.mService == null) {
                    return;
                }
                int width = KeyguardLiveWallpaper.this.getWidth();
                int height = KeyguardLiveWallpaper.this.getHeight();
                Log.i(this.TAG, "attachWindow: w = " + width + ", h = " + height + ", mIsEngineAttached=" + this.mIsEngineAttached);
                if (width <= 0 || height <= 0) {
                    WallpaperLogger wallpaperLogger = KeyguardLiveWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "attachWindow: invalid view size! w=" + width + ", h=" + height);
                } else {
                    try {
                        this.mService.attach(this, this.mToken, 2633, true, width, height, new Rect(0, 0, 0, 0), KeyguardLiveWallpaper.this.mContext.getDisplay().getDisplayId(), 2);
                        this.mService.setCurrentUserId(KeyguardLiveWallpaper.this.mUserId);
                        this.mIsEngineAttached = true;
                    } catch (RemoteException e) {
                        Log.w(this.TAG, "attachWindow: Failed attaching wallpaper. e=" + e, e);
                    }
                }
            }
        }

        public final void detachEngine() {
            synchronized (this) {
                if (this.mService == null) {
                    Log.i(this.TAG, "detachEngine: service is null");
                    return;
                }
                if (!this.mIsEngineAttached) {
                    Log.i(this.TAG, "detachEngine: engine not attached");
                    return;
                }
                Log.i(this.TAG, "detachEngine: detach. engine = " + this.mEngine);
                try {
                    this.mService.detach(this.mToken);
                } catch (RemoteException e) {
                    Log.w(this.TAG, "detachEngine: Failed detaching wallpaper. e=" + e, e);
                }
                this.mIsEngineAttached = false;
                this.mEngine = null;
            }
        }

        public final void disconnect() {
            synchronized (this) {
                WallpaperLogger wallpaperLogger = KeyguardLiveWallpaper.this.mLogger;
                ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "disconnect: unbind the service. engine=" + this.mEngine);
                setConnectionState(ConnectionState.DISCONNECTED);
                try {
                    KeyguardLiveWallpaper.this.mContext.unbindService(this);
                } catch (IllegalArgumentException e) {
                    WallpaperLogger wallpaperLogger2 = KeyguardLiveWallpaper.this.mLogger;
                    ((WallpaperLoggerImpl) wallpaperLogger2).log(this.TAG, "disconnect: unbind error=" + e);
                }
                this.mService = null;
            }
        }

        public final void engineShown(IWallpaperEngine iWallpaperEngine) {
            int width = KeyguardLiveWallpaper.this.getWidth();
            int height = KeyguardLiveWallpaper.this.getHeight();
            IWallpaperEngine iWallpaperEngine2 = this.mEngine;
            if (iWallpaperEngine2 != null) {
                try {
                    iWallpaperEngine2.setDesiredSize(width, height);
                } catch (RemoteException e) {
                    Log.w(this.TAG, "Failure to setDesiredSize ", e);
                }
            }
            KeyguardLiveWallpaper keyguardLiveWallpaper = KeyguardLiveWallpaper.this;
            boolean z = keyguardLiveWallpaper.mIsKeyguardShowing || (keyguardLiveWallpaper.mUpdateMonitor.mKeyguardShowing && WallpaperUtils.isAODShowLockWallpaperEnabled());
            Log.i(this.TAG, "engineShown: " + iWallpaperEngine + ", focus=" + KeyguardLiveWallpaper.this.hasWindowFocus() + ", isShowing=" + z);
            if (!z) {
                setSurfaceAlpha(0.0f);
            }
            this.mIsVisible = z;
            setEngineVisibility(z);
            WallpaperResultCallback wallpaperResultCallback = KeyguardLiveWallpaper.this.mWallpaperResultCallback;
            if (wallpaperResultCallback != null) {
                wallpaperResultCallback.onPreviewReady();
            }
            KeyguardLiveWallpaper.this.setThumbnailVisibility(z ? 4 : 0);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(this.TAG, "onServiceConnected: service connected. name: " + componentName.flattenToShortString() + ", service=@" + Integer.toHexString(iBinder.hashCode()) + ", connState=" + this.mConnectionState + ", mIsKeyguardShowing = " + KeyguardLiveWallpaper.this.mIsKeyguardShowing);
            this.mService = IWallpaperService.Stub.asInterface(iBinder);
            setConnectionState(ConnectionState.CONNECTED);
            if (this.mIsReleaseRequested) {
                Log.i(this.TAG, "onServiceConnected: connection release reserved");
                release();
                return;
            }
            KeyguardLiveWallpaper keyguardLiveWallpaper = KeyguardLiveWallpaper.this;
            if (keyguardLiveWallpaper.mWallpaperConnection != this) {
                Log.i(this.TAG, "onServiceConnected: not active connection");
                release();
                return;
            }
            WallpaperLogger wallpaperLogger = keyguardLiveWallpaper.mLogger;
            ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "onServiceConnected: Attaching engine. service = " + this.mService);
            attachWindow();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (this) {
                WallpaperLogger wallpaperLogger = KeyguardLiveWallpaper.this.mLogger;
                ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "onServiceDisconnected: comp = " + componentName);
                setConnectionState(ConnectionState.DISCONNECTED);
                KeyguardLiveWallpaper keyguardLiveWallpaper = KeyguardLiveWallpaper.this;
                if (keyguardLiveWallpaper.mWallpaperConnection == this) {
                    ((WallpaperLoggerImpl) keyguardLiveWallpaper.mLogger).log(this.TAG, "onServiceDisconnected: active wallpaper service gone.");
                }
                release();
            }
        }

        public final void release() {
            synchronized (this) {
                WallpaperLogger wallpaperLogger = KeyguardLiveWallpaper.this.mLogger;
                ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "release: engine=" + this.mEngine + ", service=" + this.mService + ", connState=" + this.mConnectionState);
                this.mIsReleaseRequested = true;
                detachEngine();
                disconnect();
                Listener listener = this.mListener;
                if (listener != null) {
                    KeyguardLiveWallpaper keyguardLiveWallpaper = ((KeyguardLiveWallpaper$$ExternalSyntheticLambda2) listener).f$0;
                    if (this == keyguardLiveWallpaper.mWallpaperConnection) {
                        Log.i("KeyguardLiveWallpaper", "onReleased : active connection released. conn=" + this.TAG);
                        keyguardLiveWallpaper.mWallpaperConnection = null;
                    }
                }
            }
        }

        public final void setConnectionState(ConnectionState connectionState) {
            synchronized (this) {
                WallpaperLogger wallpaperLogger = KeyguardLiveWallpaper.this.mLogger;
                ((WallpaperLoggerImpl) wallpaperLogger).log(this.TAG, "setConnectionState: " + connectionState);
                this.mConnectionState = connectionState;
            }
        }

        public final void setEngineVisibility(boolean z) {
            if (this.mEngine == null || z == this.mIsEngineVisible) {
                return;
            }
            try {
                Log.i(this.TAG, "setEngineVisibility : " + z);
                this.mEngine.setVisibility(z);
                this.mIsEngineVisible = z;
            } catch (RemoteException e) {
                Log.w(this.TAG, "Failure setting wallpaper visibility ", e);
            }
        }

        public final void setSurfaceAlpha(float f) {
            if (this.mEngine != null) {
                try {
                    if (this.mAlpha != f) {
                        Log.i(this.TAG, "setSurfaceAlpha prev = " + this.mAlpha + " , new = " + f);
                        this.mEngine.setSurfaceAlpha(f);
                        this.mAlpha = f;
                    }
                } catch (RemoteException e) {
                    Log.w(this.TAG, "Failure to setSurfaceAlpha ", e);
                }
            }
        }

        public final ParcelFileDescriptor setWallpaper(String str) {
            return null;
        }

        public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i) {
        }

        public final void onLocalWallpaperColorsChanged(RectF rectF, WallpaperColors wallpaperColors, int i) {
        }
    }
}
