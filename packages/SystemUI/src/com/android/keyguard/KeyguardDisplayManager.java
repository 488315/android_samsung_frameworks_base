package com.android.keyguard;

import android.app.Presentation;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardDisplayManager {
    public final ConnectedDisplayKeyguardPresentation.Factory mConnectedDisplayKeyguardPresentationFactory;
    public final Context mContext;
    public final DeviceStateHelper mDeviceStateHelper;
    public final KeyguardPresentationDisabler mDisableHandler;
    public final DisplayManager mDisplayService;
    public final DisplayTracker mDisplayTracker;
    public final Lazy mKeyguardDeskTopStateMonitorLazy;
    public final KeyguardFoldController mKeyguardFoldController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardVisibilityMonitor mKeyguardVisibilityMonitor;
    public final Lazy mNavigationBarControllerLazy;
    public final Provider mShadePositionRepositoryProvider;
    public boolean mShowing;
    public final KeyguardDisplayManager$$ExternalSyntheticLambda0 mVisibilityListener = new IntConsumer() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda0
        @Override // java.util.function.IntConsumer
        public final void accept(int i) {
            KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
            if (i != 0) {
                ((ArrayList) keyguardDisplayManager.mKeyguardVisibilityMonitor.visibilityChangedListeners).remove(keyguardDisplayManager.mVisibilityListener);
                keyguardDisplayManager.hide();
            }
        }
    };
    public MediaRouter mMediaRouter = null;
    public final DisplayInfo mTmpDisplayInfo = new DisplayInfo();
    public final SparseArray mPresentations = new SparseArray();
    public final DisplayTracker.Callback mDisplayCallback = new DisplayTracker.Callback() { // from class: com.android.keyguard.KeyguardDisplayManager.1
        @Override // com.android.systemui.settings.DisplayTracker.Callback
        public final void onDisplayAdded(int i) {
            Trace.beginSection("KeyguardDisplayManager#onDisplayAdded(displayId=" + i + ")");
            KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
            Display display = keyguardDisplayManager.mDisplayService.getDisplay(i);
            if (keyguardDisplayManager.mShowing) {
                keyguardDisplayManager.updateNavigationBarVisibility(i, false);
                keyguardDisplayManager.showPresentation(display);
            }
            Trace.endSection();
        }

        @Override // com.android.systemui.settings.DisplayTracker.Callback
        public final void onDisplayRemoved(int i) {
            Trace.beginSection("KeyguardDisplayManager#onDisplayRemoved(displayId=" + i + ")");
            KeyguardDisplayManager.this.hidePresentation(i);
            Trace.endSection();
        }
    };
    public final AnonymousClass4 mMediaRouterCallback = new MediaRouter.SimpleCallback() { // from class: com.android.keyguard.KeyguardDisplayManager.4
        @Override // android.media.MediaRouter.Callback
        public final void onRoutePresentationDisplayChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            Log.d("KeyguardDisplayManager", "onRoutePresentationDisplayChanged: info=" + routeInfo);
            KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
            keyguardDisplayManager.updateDisplays(keyguardDisplayManager.mShowing);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            Log.d("KeyguardDisplayManager", "onRouteSelected: type=" + i + ", info=" + routeInfo);
            KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
            keyguardDisplayManager.updateDisplays(keyguardDisplayManager.mShowing);
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public final void onRouteUnselected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            Log.d("KeyguardDisplayManager", "onRouteUnselected: type=" + i + ", info=" + routeInfo);
            KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
            keyguardDisplayManager.updateDisplays(keyguardDisplayManager.mShowing);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DeviceStateHelper implements DeviceStateManager.DeviceStateCallback {
        public DeviceState mDeviceState;

        public DeviceStateHelper(DeviceStateManager deviceStateManager, Executor executor) {
            deviceStateManager.registerCallback(executor, this);
        }

        public final void onDeviceStateChanged(DeviceState deviceState) {
            this.mDeviceState = deviceState;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.keyguard.KeyguardDisplayManager$4] */
    public KeyguardDisplayManager(Context context, KeyguardFoldController keyguardFoldController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, KeyguardPresentationDisabler keyguardPresentationDisabler, CommandQueue commandQueue, Lazy lazy, Lazy lazy2, DisplayTracker displayTracker, Executor executor, Executor executor2, DeviceStateHelper deviceStateHelper, final KeyguardStateController keyguardStateController, ConnectedDisplayKeyguardPresentation.Factory factory, Provider provider, CoroutineScope coroutineScope) {
        this.mDisableHandler = keyguardPresentationDisabler;
        this.mKeyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.mContext = context;
        this.mNavigationBarControllerLazy = lazy2;
        this.mShadePositionRepositoryProvider = provider;
        executor2.execute(new Runnable() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                keyguardDisplayManager.mMediaRouter = (MediaRouter) keyguardDisplayManager.mContext.getSystemService(MediaRouter.class);
            }
        });
        this.mDisplayService = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayTracker = displayTracker;
        this.mDeviceStateHelper = deviceStateHelper;
        this.mKeyguardStateController = keyguardStateController;
        this.mConnectedDisplayKeyguardPresentationFactory = factory;
        if (ShadeWindowGoesAround.isEnabled()) {
            JavaAdapterKt.collectFlow(coroutineScope, ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) provider.get())).displayId, new Consumer() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                    int intValue = ((Integer) obj).intValue();
                    if (keyguardDisplayManager.mShowing) {
                        keyguardDisplayManager.hidePresentation(intValue);
                        keyguardDisplayManager.updateDisplays(true);
                    }
                }
            });
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            this.mKeyguardFoldController = keyguardFoldController;
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda3
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    final KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                    if (LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
                        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardDisplayManager.mKeyguardStateController;
                        if (keyguardStateControllerImpl.mSecure || keyguardDisplayManager.isExternalDesktopWindowing()) {
                            return;
                        }
                        if (!z) {
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardDisplayManager.3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    if (((KeyguardFoldControllerImpl) KeyguardDisplayManager.this.mKeyguardFoldController).isFoldOpened()) {
                                        return;
                                    }
                                    KeyguardDisplayManager.this.hide();
                                }
                            }, 0L);
                        } else if (keyguardStateControllerImpl.mShowing) {
                            keyguardDisplayManager.mShowing = false;
                            keyguardDisplayManager.show();
                        }
                    }
                }
            }, 6, false);
        }
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.keyguard.KeyguardDisplayManager.2
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onDisplayAddSystemDecorations(int i) {
                StringBuilder sb = new StringBuilder("onDisplayAddSystemDecorations : mShowing = ");
                KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                sb.append(keyguardDisplayManager.mShowing);
                sb.append(", keyguardStateController.isShowing() = ");
                KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
                sb.append(keyguardStateControllerImpl.mShowing);
                Log.i("KeyguardDisplayManager", sb.toString());
                Trace.beginSection("KeyguardDisplayManager#onDisplayAddSystemDecorations(displayId=" + i + ")");
                Display display = keyguardDisplayManager.mDisplayService.getDisplay(i);
                if (keyguardDisplayManager.mShowing) {
                    keyguardDisplayManager.updateNavigationBarVisibility(i, false);
                    keyguardDisplayManager.showPresentation(display);
                } else if (keyguardStateControllerImpl.mShowing) {
                    keyguardDisplayManager.updateNavigationBarVisibility(i, false);
                    keyguardDisplayManager.showPresentation(display);
                    keyguardDisplayManager.mShowing = true;
                }
                Trace.endSection();
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onDisplayRemoveSystemDecorations(int i) {
                Log.i("KeyguardDisplayManager", "onDisplayRemoveSystemDecorations : " + i);
                Trace.beginSection("KeyguardDisplayManager#onDisplayRemoveSystemDecorations(displayId=" + i + ")");
                KeyguardDisplayManager.this.hidePresentation(i);
                Trace.endSection();
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onDisplayRemoved(int i) {
                Log.i("KeyguardDisplayManager", "onDisplayRemoved : " + i);
                onDisplayRemoveSystemDecorations(i);
            }
        });
        this.mKeyguardDeskTopStateMonitorLazy = lazy;
    }

    public final void hide() {
        if (this.mShowing) {
            MediaRouter mediaRouter = this.mMediaRouter;
            if (mediaRouter != null) {
                mediaRouter.removeCallback(this.mMediaRouterCallback);
            }
            updateDisplays(false);
        }
        this.mShowing = false;
    }

    public final void hidePresentation(int i) {
        Presentation presentation = (Presentation) this.mPresentations.get(i);
        if (presentation != null) {
            presentation.dismiss();
            this.mPresentations.remove(i);
        }
    }

    public final boolean isDesktopMode() {
        if (this.mKeyguardDeskTopStateMonitorLazy != null) {
            if (isExternalDesktopWindowing()) {
                return true;
            }
            Lazy lazy = this.mKeyguardDeskTopStateMonitorLazy;
            if (lazy == null ? false : ((KeyguardDeskTopStateMonitor) lazy.get()).mIsDesktopStandAlone) {
                return true;
            }
        }
        return false;
    }

    public final boolean isExternalDesktopWindowing() {
        Lazy lazy = this.mKeyguardDeskTopStateMonitorLazy;
        if (lazy == null) {
            return false;
        }
        return ((KeyguardDeskTopStateMonitor) lazy.get()).mIsExternalDesktopWindowing;
    }

    public final void show() {
        if (!this.mShowing) {
            MediaRouter mediaRouter = this.mMediaRouter;
            if (mediaRouter != null) {
                mediaRouter.addCallback(4, this.mMediaRouterCallback, 8);
            } else {
                Log.w("KeyguardDisplayManager", "MediaRouter not yet initialized");
            }
            updateDisplays(true);
        }
        this.mShowing = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean showPresentation(android.view.Display r7) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardDisplayManager.showPresentation(android.view.Display):boolean");
    }

    public final void updateDisplays(boolean z) {
        if (!z) {
            this.mPresentations.size();
            for (int size = this.mPresentations.size() - 1; size >= 0; size--) {
                updateNavigationBarVisibility(this.mPresentations.keyAt(size), true);
                ((Presentation) this.mPresentations.valueAt(size)).dismiss();
            }
            this.mPresentations.clear();
            return;
        }
        for (Display display : ((DisplayTrackerImpl) this.mDisplayTracker).displayManager.getDisplays()) {
            updateNavigationBarVisibility(display.getDisplayId(), false);
            showPresentation(display);
        }
    }

    public final void updateNavigationBarVisibility(int i, boolean z) {
        NavigationBarView navigationBarView;
        this.mDisplayTracker.getClass();
        if (i == 0 || (navigationBarView = ((NavigationBarControllerImpl) ((NavigationBarController) this.mNavigationBarControllerLazy.get())).getNavigationBarView(i)) == null) {
            return;
        }
        if (z) {
            navigationBarView.getRootView().setVisibility(0);
        } else {
            navigationBarView.getRootView().setVisibility(8);
        }
    }
}
