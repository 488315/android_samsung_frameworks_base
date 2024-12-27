package com.android.keyguard;

import android.R;
import android.app.Presentation;
import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

public final class KeyguardDisplayManager {
    public final ConnectedDisplayKeyguardPresentation.Factory mConnectedDisplayKeyguardPresentationFactory;
    public final Context mContext;
    public final DeviceStateHelper mDeviceStateHelper;
    public final KeyguardPresentationDisabler mDisableHandler;
    public final AnonymousClass1 mDisplayCallback;
    public final DisplayManager mDisplayService;
    public final DisplayTracker mDisplayTracker;
    public final KeyguardFoldController mKeyguardFoldController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardVisibilityMonitor mKeyguardVisibilityMonitor;
    public final AnonymousClass3 mMediaRouterCallback;
    public final Lazy mNavigationBarControllerLazy;
    public boolean mShowing;
    public final KeyguardDisplayManager$$ExternalSyntheticLambda0 mVisibilityListener = new IntConsumer() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda0
        @Override // java.util.function.IntConsumer
        public final void accept(int i) {
            KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
            if (i == 0) {
                keyguardDisplayManager.getClass();
                return;
            }
            ((ArrayList) keyguardDisplayManager.mKeyguardVisibilityMonitor.visibilityChangedListeners).remove(keyguardDisplayManager.mVisibilityListener);
            keyguardDisplayManager.hide();
        }
    };
    public MediaRouter mMediaRouter = null;
    public final DisplayInfo mTmpDisplayInfo = new DisplayInfo();
    public final SparseArray mPresentations = new SparseArray();

    public final class DeviceStateHelper implements DeviceStateManager.DeviceStateCallback {
        public final int mConcurrentState;
        public boolean mIsInConcurrentDisplayState;
        public final DisplayAddress.Physical mRearDisplayPhysicalAddress;

        public DeviceStateHelper(Context context, DeviceStateManager deviceStateManager, Executor executor) {
            String string = context.getResources().getString(R.string.ext_media_status_missing);
            if (TextUtils.isEmpty(string)) {
                this.mRearDisplayPhysicalAddress = null;
            } else {
                this.mRearDisplayPhysicalAddress = DisplayAddress.fromPhysicalDisplayId(Long.parseLong(string));
            }
            this.mConcurrentState = context.getResources().getInteger(R.integer.config_dynamicPowerSavingsDefaultDisableThreshold);
            deviceStateManager.registerCallback(executor, this);
        }

        public final void onDeviceStateChanged(DeviceState deviceState) {
            this.mIsInConcurrentDisplayState = deviceState.getIdentifier() == this.mConcurrentState;
        }
    }

    public KeyguardDisplayManager(KeyguardFoldController keyguardFoldController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, KeyguardPresentationDisabler keyguardPresentationDisabler, Context context, Lazy lazy, DisplayTracker displayTracker, Executor executor, Executor executor2, DeviceStateHelper deviceStateHelper, KeyguardStateController keyguardStateController, ConnectedDisplayKeyguardPresentation.Factory factory) {
        DisplayTracker.Callback callback = new DisplayTracker.Callback() { // from class: com.android.keyguard.KeyguardDisplayManager.1
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
                KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                Presentation presentation = (Presentation) keyguardDisplayManager.mPresentations.get(i);
                if (presentation != null) {
                    presentation.dismiss();
                    keyguardDisplayManager.mPresentations.remove(i);
                }
                Trace.endSection();
            }
        };
        this.mMediaRouterCallback = new MediaRouter.SimpleCallback() { // from class: com.android.keyguard.KeyguardDisplayManager.3
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
        this.mDisableHandler = keyguardPresentationDisabler;
        this.mKeyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.mContext = context;
        this.mNavigationBarControllerLazy = lazy;
        executor2.execute(new Runnable() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                keyguardDisplayManager.mMediaRouter = (MediaRouter) keyguardDisplayManager.mContext.getSystemService(MediaRouter.class);
            }
        });
        this.mDisplayService = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mDisplayTracker = displayTracker;
        ((DisplayTrackerImpl) displayTracker).addDisplayChangeCallback(callback, executor);
        this.mDeviceStateHelper = deviceStateHelper;
        this.mKeyguardStateController = keyguardStateController;
        this.mConnectedDisplayKeyguardPresentationFactory = factory;
        if (LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
            this.mKeyguardFoldController = keyguardFoldController;
            ((KeyguardFoldControllerImpl) keyguardFoldController).addCallback(new KeyguardFoldController.StateListener() { // from class: com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda2
                @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
                public final void onFoldStateChanged(boolean z) {
                    final KeyguardDisplayManager keyguardDisplayManager = KeyguardDisplayManager.this;
                    keyguardDisplayManager.getClass();
                    if (LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT) {
                        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardDisplayManager.mKeyguardStateController;
                        if (keyguardStateControllerImpl.mSecure) {
                            return;
                        }
                        if (!z) {
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardDisplayManager.2
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

    public final void hideAfterKeyguardInvisible() {
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = this.mKeyguardVisibilityMonitor;
        if (keyguardVisibilityMonitor.isVisible()) {
            keyguardVisibilityMonitor.addVisibilityChangedListener(this.mVisibilityListener);
        } else {
            hide();
        }
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean showPresentation(android.view.Display r6) {
        /*
            r5 = this;
            java.lang.String r0 = "KeyguardDisplayManager"
            r1 = 0
            if (r6 != 0) goto Ld
            java.lang.String r2 = "Cannot show Keyguard on null display"
            android.util.Log.i(r0, r2)
        La:
            r2 = r1
            goto L9f
        Ld:
            int r2 = r6.getDisplayId()
            com.android.systemui.settings.DisplayTracker r3 = r5.mDisplayTracker
            r3.getClass()
            if (r2 != 0) goto L1e
            java.lang.String r2 = "Do not show KeyguardPresentation on the default display"
            android.util.Log.i(r0, r2)
            goto La
        L1e:
            android.view.DisplayInfo r2 = r5.mTmpDisplayInfo
            r6.getDisplayInfo(r2)
            android.view.DisplayInfo r2 = r5.mTmpDisplayInfo
            int r2 = r2.type
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            android.view.DisplayInfo r3 = r5.mTmpDisplayInfo
            int r3 = r3.flags
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            android.view.DisplayInfo r4 = r5.mTmpDisplayInfo
            int r4 = r4.displayGroupId
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.Object[] r2 = new java.lang.Object[]{r2, r3, r4}
            java.lang.String r3 = "display type=0x%x, flags=0x%x, displayGroupId=0x%x"
            com.android.systemui.util.LogUtil.d(r0, r3, r2)
            android.view.DisplayInfo r2 = r5.mTmpDisplayInfo
            int r2 = r2.flags
            r3 = r2 & 4
            if (r3 == 0) goto L52
            java.lang.String r2 = "Do not show KeyguardPresentation on a private display"
            android.util.Log.i(r0, r2)
            goto La
        L52:
            r2 = r2 & 512(0x200, float:7.175E-43)
            if (r2 == 0) goto L5c
            java.lang.String r2 = "Do not show KeyguardPresentation on an unlocked display"
            android.util.Log.i(r0, r2)
            goto La
        L5c:
            com.android.systemui.statusbar.policy.KeyguardStateController r2 = r5.mKeyguardStateController
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r2 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r2
            boolean r3 = r2.mOccluded
            if (r3 == 0) goto L7e
            com.android.keyguard.KeyguardDisplayManager$DeviceStateHelper r3 = r5.mDeviceStateHelper
            boolean r4 = r3.mIsInConcurrentDisplayState
            if (r4 == 0) goto L7e
            android.view.DisplayAddress$Physical r3 = r3.mRearDisplayPhysicalAddress
            if (r3 == 0) goto L7e
            android.view.DisplayAddress r4 = r6.getAddress()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L7e
            java.lang.String r2 = "Do not show KeyguardPresentation when occluded and concurrent display is active"
            android.util.Log.i(r0, r2)
            goto La
        L7e:
            boolean r3 = com.android.systemui.LsRune.KEYGUARD_SUB_DISPLAY_LARGE_FRONT
            if (r3 == 0) goto L97
            boolean r2 = r2.mSecure
            if (r2 != 0) goto L97
            com.android.systemui.keyguard.KeyguardFoldController r2 = r5.mKeyguardFoldController
            com.android.systemui.keyguard.KeyguardFoldControllerImpl r2 = (com.android.systemui.keyguard.KeyguardFoldControllerImpl) r2
            boolean r2 = r2.isFoldOpened()
            if (r2 != 0) goto L97
            java.lang.String r2 = "Do not show KeyguardPresentation to the large front sub display when non-secure"
            android.util.Log.d(r0, r2)
            goto La
        L97:
            com.android.keyguard.KeyguardPresentationDisabler r2 = r5.mDisableHandler
            android.view.DisplayInfo r3 = r5.mTmpDisplayInfo
            boolean r2 = r2.isEnabled(r3)
        L9f:
            if (r2 != 0) goto La2
            return r1
        La2:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Keyguard enabled on display: "
            r2.<init>(r3)
            r2.append(r6)
            java.lang.String r2 = r2.toString()
            android.util.Log.i(r0, r2)
            int r2 = r6.getDisplayId()
            android.util.SparseArray r3 = r5.mPresentations
            java.lang.Object r3 = r3.get(r2)
            android.app.Presentation r3 = (android.app.Presentation) r3
            if (r3 != 0) goto Le3
            com.android.keyguard.ConnectedDisplayKeyguardPresentation$Factory r3 = r5.mConnectedDisplayKeyguardPresentationFactory
            com.android.keyguard.ConnectedDisplayKeyguardPresentation r6 = r3.create(r6)
            com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda3 r3 = new com.android.keyguard.KeyguardDisplayManager$$ExternalSyntheticLambda3
            r3.<init>()
            r6.setOnDismissListener(r3)
            r6.show()     // Catch: android.view.WindowManager.InvalidDisplayException -> Ld3
            goto Lda
        Ld3:
            r6 = move-exception
            java.lang.String r3 = "Invalid display:"
            android.util.Log.w(r0, r3, r6)
            r6 = 0
        Lda:
            if (r6 == 0) goto Le3
            android.util.SparseArray r5 = r5.mPresentations
            r5.append(r2, r6)
            r5 = 1
            return r5
        Le3:
            return r1
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
