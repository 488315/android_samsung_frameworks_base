package com.android.systemui.navigationbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.IconDrawableUtil;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__StringsKt;

public final class SecTaskBarManagerImpl extends TaskbarDelegate {
    public final Context context;
    public final NavBarIconResourceMapper iconResourceMapper;
    public NavBarRemoteViewManager navBarRemoteViewManager;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public final SamsungPluginTaskBar pluginTaskBar;
    public boolean shouldInitializeAgain;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecTaskBarManagerImpl(Context context, LightBarTransitionsController.Factory factory, StatusBarKeyguardViewManager statusBarKeyguardViewManager, NavBarStore navBarStore) {
        super(context, factory, statusBarKeyguardViewManager);
        this.context = context;
        this.navBarStore = navBarStore;
        this.navBarStateManager = navBarStore.getNavStateManager();
        NavBarButtonDrawableProvider.Companion.getClass();
        NavBarButtonDrawableProvider navBarButtonDrawableProvider = NavBarButtonDrawableProvider.INSTANCE;
        if (navBarButtonDrawableProvider == null) {
            navBarButtonDrawableProvider = new NavBarButtonDrawableProvider();
            NavBarButtonDrawableProvider.INSTANCE = navBarButtonDrawableProvider;
        }
        this.iconResourceMapper = new NavBarIconResourceMapper(navBarButtonDrawableProvider, navBarStore, context, new LogWrapper(ModuleType.NAVBAR, null));
        SamsungPluginTaskBar samsungPluginTaskBar = new SamsungPluginTaskBar(navBarStore, context);
        this.pluginTaskBar = samsungPluginTaskBar;
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        samsungPluginTaskBar.taskbarDelegate = this;
        samsungPluginTaskBar.iconResourceMapper = navBarIconResourceMapper;
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void attributesChanged(int i, String str, boolean z) {
        NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
        navBarEvents.eventType = NavBarEvents.EventType.ON_APPEARANCE_CHANGED;
        navBarEvents.appearance = (-2097153) & i;
        handleNavigationBarEvent(navBarEvents);
        if (BasicRune.NAVBAR_TASKBAR) {
            StringBuilder sb = new StringBuilder("onSystemBarAttributesChanged() -");
            sb.append("  displayId:" + this.mDisplayId);
            sb.append(", appearance:" + i);
            if (i != 0) {
                sb.append(" (");
                sb.append((i & 16) != 0 ? "APPEARANCE_LIGHT_NAVIGATION_BARS " : "");
                sb.append((i & 2) != 0 ? "APPEARANCE_OPAQUE_NAVIGATION_BARS " : "");
                sb.append((i & 64) != 0 ? "APPEARANCE_SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
                sb.append((1048576 & i) != 0 ? "APPEARANCE_LIGHT_SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
                sb.append(")");
            }
            sb.append(", navbarColorManagedByIme: " + z);
            if (!StringsKt__StringsKt.contains(str, "com.att", false)) {
                sb.append(", packageName: ".concat(str));
            }
            Log.d("TaskBarDelegate", sb.toString());
        }
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final int getDisabledFlags() {
        return this.mDisabledFlags;
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final EdgeBackGestureHandler getEdgeBackGestureHandler() {
        return this.mEdgeBackGestureHandler;
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final LightBarTransitionsController getLightBarTransitionsController() {
        return this.mLightBarTransitionsController;
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void handleNavigationBarEvent(NavBarEvents navBarEvents) {
        if (!this.mInitialized) {
            Log.d("TaskBarDelegate", "handleNavigationBarEvent() TaskbarDelegate is not initialized.");
            return;
        }
        IOverviewProxy iOverviewProxy = this.mOverviewProxyService.mOverviewProxy;
        if (iOverviewProxy == null) {
            this.shouldInitializeAgain = true;
            return;
        }
        try {
            ((IOverviewProxy.Stub.Proxy) iOverviewProxy).handleNavigationBarEvent(navBarEvents);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "Failed to call handleNavigationBarEvent()", e);
        }
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void initialize() {
        this.mNavigationIconHints = this.mNavBarHelper.mLastIMEhints;
        boolean z = false;
        this.shouldInitializeAgain = false;
        LightBarTransitionsController create = this.mLightBarTransitionsControllerFactory.create(new TaskbarDelegate.AnonymousClass4());
        this.mLightBarTransitionsController = create;
        this.mOverviewProxyService.onNavButtonsDarkIntensityChanged(create.mDarkIntensity);
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver = this.mAutoHideController.mObserver;
            autoHideUiElementObserver.getClass();
            ArrayList arrayList = (ArrayList) autoHideUiElementObserver.mList;
            TaskbarDelegate.AnonymousClass3 anonymousClass3 = this.mAutoHideUiElement;
            arrayList.remove(anonymousClass3);
            if (anonymousClass3 != null) {
                ((ArrayList) autoHideUiElementObserver.mList).add(anonymousClass3);
            }
            LightBarController lightBarController = this.mLightBarController;
            LightBarTransitionsController lightBarTransitionsController = this.mLightBarTransitionsController;
            LightBarController.LightBarTransientObserver lightBarTransientObserver = lightBarController.mObserver;
            lightBarTransientObserver.mList.remove(lightBarTransitionsController);
            if (lightBarTransitionsController != null) {
                lightBarTransientObserver.mList.add(lightBarTransitionsController);
            }
        }
        this.mOverviewProxyService.onNavButtonsDarkIntensityChanged(this.mLightBarTransitionsController.mDarkIntensity);
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnTaskbarAttachedToWindow(z, 1, null));
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final boolean isBackGestureAllowed(MotionEvent motionEvent) {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        if (edgeBackGestureHandler == null) {
            return false;
        }
        return edgeBackGestureHandler.isBackGestureAllowed(motionEvent);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedGameToolsWin(boolean z) {
        if (!BasicRune.NAVBAR_TASKBAR || this.mAutoHideController == null) {
            return;
        }
        int i = StringCompanionObject.$r8$clinit;
        Log.d("TaskBarDelegate", String.format("notifyRequestedGameToolsWin visible : %s", Arrays.copyOf(new Object[]{Boolean.valueOf(z)}, 1)));
        this.mAutoHideController.notifyRequestedGameToolsWin(z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedSystemKey(boolean z, boolean z2) {
        if (BasicRune.NAVBAR_TASKBAR) {
            SysUiState sysUiState = this.mSysUiState;
            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_NIO, z);
            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_NIO_TEXT, z2);
            sysUiState.commitUpdate(this.mDisplayId);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        if (BasicRune.NAVBAR_TASKBAR && this.mDisplayId == i) {
            int i2 = StringCompanionObject.$r8$clinit;
            Log.d("TaskBarDelegate", String.format("notifySamsungPayInfo displayId : %d, visible: %s", Arrays.copyOf(new Object[]{Integer.valueOf(i), Boolean.valueOf(z)}, 2)));
            if (i == 0) {
                OverviewProxyService overviewProxyService = this.mOverviewProxyService;
                int width = rect.width();
                overviewProxyService.getClass();
                try {
                    IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).notifyPayInfo(width, z);
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to notify pay info.", e);
                }
            }
            ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSpayVisibility(z, rect.width()));
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onConnectionChanged(boolean z) {
        if (BasicRune.NAVBAR_TASKBAR && z && this.shouldInitializeAgain) {
            this.shouldInitializeAgain = false;
            onInitializedTaskbarNavigationBar();
            try {
                IOverviewProxy iOverviewProxy = this.mOverviewProxyService.mOverviewProxy;
                NavBarStateManager navBarStateManager = this.navBarStateManager;
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).isTaskbarEnabled(navBarStateManager != null ? ((NavBarStateManagerImpl) navBarStateManager).isTaskBarEnabled(false) : false);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void onDestroy() {
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnTaskbarDetachedFromWindow(false, 1, null));
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver = this.mAutoHideController.mObserver;
            autoHideUiElementObserver.getClass();
            ((ArrayList) autoHideUiElementObserver.mList).remove(this.mAutoHideUiElement);
            LightBarController lightBarController = this.mLightBarController;
            lightBarController.mObserver.mList.remove(this.mLightBarTransitionsController);
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onInitializedTaskbarNavigationBar() {
        NavBarRemoteView remoteView;
        if (this.mInitialized) {
            Log.d("TaskBarDelegate", "onInitializedTaskbarNavigationBar()");
            updateTaskbarButtonIconsAndHints();
            for (int i = 0; i < 2; i++) {
                NavBarRemoteViewManager navBarRemoteViewManager = this.navBarRemoteViewManager;
                Bundle bundle = null;
                if (navBarRemoteViewManager != null && (remoteView = navBarRemoteViewManager.getRemoteView(i)) != null) {
                    bundle = new Bundle();
                    bundle.putString("requestClass", remoteView.requestClass);
                    bundle.putParcelable("remoteViews", remoteView.remoteViews);
                    bundle.putInt(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, i);
                    bundle.putInt(SystemUIAnalytics.QPNE_VID_PRIORITY, remoteView.priority);
                }
                if (bundle != null) {
                    NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
                    navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_NAVBAR_REMOTEVIEWS;
                    navBarEvents.remoteViewBundle = bundle;
                    handleNavigationBarEvent(navBarEvents);
                }
            }
            NavBarEvents navBarEvents2 = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
            navBarEvents2.eventType = NavBarEvents.EventType.ON_ROTATION_LOCKED_CHANGED;
            navBarEvents2.rotationLocked = ((RotationLockController) Dependency.sDependency.getDependencyInner(RotationLockController.class)).isRotationLocked();
            handleNavigationBarEvent(navBarEvents2);
            NavBarStateManager navBarStateManager = this.navBarStateManager;
            boolean isNavBarHiddenByKnox = navBarStateManager != null ? ((NavBarStateManagerImpl) navBarStateManager).isNavBarHiddenByKnox() : false;
            NavBarEvents navBarEvents3 = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
            navBarEvents3.eventType = NavBarEvents.EventType.ON_UPDATE_TASKBAR_VIS_BY_KNOX;
            navBarEvents3.hiddenByKnox = isNavBarHiddenByKnox;
            handleNavigationBarEvent(navBarEvents3);
            SysUiState sysUiState = this.mSysUiState;
            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_MUSIC, isNavBarHiddenByKnox);
            sysUiState.commitUpdate(this.mDisplayId);
            this.mOverviewProxyService.onNavButtonsDarkIntensityChanged(this.mLightBarTransitionsController.mDarkIntensity);
            this.pluginTaskBar.updatePluginBundle();
            EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
            this.navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnUpdateSideBackGestureInsets(edgeBackGestureHandler.mEdgeWidthLeft, edgeBackGestureHandler.mEdgeWidthRight), this.mDisplayId);
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onTaskbarSPluginButtonClicked() {
        this.pluginTaskBar.buttonDispatcherProxy.pinButton.view.performClick();
    }

    public final void putButtonBitmapsToBundle(IconType iconType, Bundle bundle) {
        KeyButtonDrawable buttonDrawable = this.iconResourceMapper.getButtonDrawable(iconType);
        Drawable mutate = buttonDrawable.mLayerDrawable.getDrawable(0).mutate();
        Drawable mutate2 = buttonDrawable.mLayerDrawable.getDrawable(1).mutate();
        mutate.setAlpha(255);
        mutate2.setAlpha(255);
        Bitmap[] bitmapArr = {IconDrawableUtil.getBitmap(mutate), IconDrawableUtil.getBitmap(mutate2)};
        bundle.putParcelable(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(iconType.name(), "_LIGHT"), bitmapArr[0]);
        bundle.putParcelable(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(iconType.name(), "_DARK"), bitmapArr[1]);
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate, com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        super.setImeWindowStatus(i, iBinder, i2, i3, z);
        if (BasicRune.NAVBAR_TASKBAR) {
            this.mNavBarHelper.mLastIMEhints = this.mNavigationIconHints;
            NavBarStateManager navBarStateManager = this.navBarStateManager;
            if (navBarStateManager != null) {
                ((NavBarStateManagerImpl) navBarStateManager).canShowHideKeyboardButtonForRotation(((NavBarStateManagerImpl) navBarStateManager).states.rotation);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void transitionModeChanged(int i) {
        ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarTransitionModeChanged(i));
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void transitionStateChanged(boolean z) {
        if (z) {
            this.mAutoHideController.touchAutoHide();
        }
        NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
        navBarEvents.eventType = NavBarEvents.EventType.ON_TRANSIENT_SHOWING_CHANGED;
        navBarEvents.transientShowing = z;
        handleNavigationBarEvent(navBarEvents);
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void updateActiveIndicatorSpringParams(float f, float f2) {
        this.mEdgeBackGestureHandler.mEdgeBackPlugin.updateActiveIndicatorSpringParams(f, f2);
    }

    @Override // com.android.systemui.navigationbar.TaskbarDelegate
    public final void updateTaskbarButtonIconsAndHints() {
        this.iconResourceMapper.isRTL = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context) == 1;
        Bundle bundle = new Bundle();
        bundle.putBoolean("defaultIcon", !this.iconResourceMapper.themeIcon);
        putButtonBitmapsToBundle(IconType.TYPE_RECENT, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_HOME, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_BACK, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_BACK_ALT, bundle);
        Context context = this.context;
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        IconType iconType = IconType.TYPE_GESTURE_HANDLE_HINT;
        Bitmap[] bitmapFromDrawable = IconDrawableUtil.getBitmapFromDrawable(context, ((NavBarButtonDrawableProvider) navBarIconResourceMapper.buttonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper.context, navBarIconResourceMapper.getIconResource(iconType), 0));
        Context context2 = this.context;
        NavBarIconResourceMapper navBarIconResourceMapper2 = this.iconResourceMapper;
        Bitmap[] bitmapFromDrawable2 = IconDrawableUtil.getBitmapFromDrawable(context2, ((NavBarButtonDrawableProvider) navBarIconResourceMapper2.buttonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper2.context, navBarIconResourceMapper2.getIconResource(iconType), 0));
        Context context3 = this.context;
        NavBarIconResourceMapper navBarIconResourceMapper3 = this.iconResourceMapper;
        Bitmap[] bitmapFromDrawable3 = IconDrawableUtil.getBitmapFromDrawable(context3, ((NavBarButtonDrawableProvider) navBarIconResourceMapper3.buttonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper3.context, navBarIconResourceMapper3.getIconResource(iconType), 0));
        bundle.putParcelable("TYPE_GESTURE_HANDLE_HINT_LIGHT", bitmapFromDrawable[0]);
        bundle.putParcelable("TYPE_GESTURE_HANDLE_HINT_DARK", bitmapFromDrawable[1]);
        bundle.putParcelable("TYPE_GESTURE_HINT_LIGHT", bitmapFromDrawable2[0]);
        bundle.putParcelable("TYPE_GESTURE_HINT_DARK", bitmapFromDrawable2[1]);
        bundle.putParcelable("TYPE_GESTURE_HINT_VI_LIGHT", bitmapFromDrawable3[0]);
        bundle.putParcelable("TYPE_GESTURE_HINT_VI_DARK", bitmapFromDrawable3[1]);
        SamsungPluginTaskBar samsungPluginTaskBar = this.pluginTaskBar;
        for (int i = 1; i < 6; i++) {
            Bitmap bitmap = (Bitmap) samsungPluginTaskBar.pluginBundle.getParcelable("extra" + i + "_LIGHT");
            if (bitmap != null) {
                bundle.putParcelable("extra" + i + "_LIGHT", bitmap);
            }
            Bitmap bitmap2 = (Bitmap) samsungPluginTaskBar.pluginBundle.getParcelable("extra" + i + "_DARK");
            if (bitmap2 != null) {
                bundle.putParcelable("extra" + i + "_DARK", bitmap2);
            }
        }
        samsungPluginTaskBar.getClass();
        this.pluginTaskBar.parseAndUpdateBundle();
        NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
        navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_ICON_BITMAP;
        navBarEvents.iconBitmapBundle = bundle;
        handleNavigationBarEvent(navBarEvents);
    }
}
