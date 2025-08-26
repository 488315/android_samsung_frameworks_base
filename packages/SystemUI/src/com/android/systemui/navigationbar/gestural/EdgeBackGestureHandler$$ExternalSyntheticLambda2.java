package com.android.systemui.navigationbar.gestural;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.Log;
import android.view.WindowManagerGlobal;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.util.concurrency.UiThreadContext;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import kotlinx.coroutines.Job;

/* loaded from: classes2.dex */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EdgeBackGestureHandler f$0;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda2(EdgeBackGestureHandler edgeBackGestureHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = edgeBackGestureHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final HashMap map = null;
        final int i = 1;
        final int i2 = 0;
        int i3 = this.$r8$classId;
        final EdgeBackGestureHandler edgeBackGestureHandler = this.f$0;
        switch (i3) {
            case 0:
                int i4 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                boolean zIsHandlingGestures = edgeBackGestureHandler.isHandlingGestures();
                edgeBackGestureHandler.updateCurrentUserResources();
                if (edgeBackGestureHandler.mStateChangeCallback == null || zIsHandlingGestures == edgeBackGestureHandler.isHandlingGestures()) {
                    return;
                }
                edgeBackGestureHandler.mStateChangeCallback.run();
                return;
            case 1:
                final BackGestureTfClassifierProvider backGestureTfClassifierProvider = (BackGestureTfClassifierProvider) edgeBackGestureHandler.mBackGestureTfClassifierProviderProvider.get();
                final float f = DeviceConfig.getFloat("systemui", "back_gesture_ml_model_threshold", 0.9f);
                if (backGestureTfClassifierProvider != null) {
                    Log.w("EdgeBackGestureHandler", "Cannot load model because it isn't active");
                    backGestureTfClassifierProvider = null;
                }
                if (backGestureTfClassifierProvider != null) {
                    Trace.beginSection("EdgeBackGestureHandler#loadVocab");
                    edgeBackGestureHandler.mContext.getAssets();
                    map = new HashMap();
                }
                edgeBackGestureHandler.mUiThreadContext.getExecutor().execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgeBackGestureHandler edgeBackGestureHandler2 = edgeBackGestureHandler;
                        BackGestureTfClassifierProvider backGestureTfClassifierProvider2 = backGestureTfClassifierProvider;
                        Map map2 = map;
                        float f2 = f;
                        edgeBackGestureHandler2.mUiThreadContext.isCurrentThread();
                        edgeBackGestureHandler2.mMLModelIsLoading = false;
                        if (!edgeBackGestureHandler2.mUseMLModel) {
                            Log.d("EdgeBackGestureHandler", "Model finished loading but isn't needed.");
                            return;
                        }
                        edgeBackGestureHandler2.mBackGestureTfClassifierProvider = backGestureTfClassifierProvider2;
                        edgeBackGestureHandler2.mVocab = map2;
                        edgeBackGestureHandler2.mMLModelThreshold = f2;
                    }
                });
                return;
            case 2:
                int i5 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.pilferPointers();
                return;
            case 3:
                int i6 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.getClass();
                ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
                ComponentName componentName = runningTask != null ? runningTask.topActivity : null;
                if (componentName != null) {
                    edgeBackGestureHandler.mPackageName = componentName.getPackageName();
                    return;
                } else {
                    edgeBackGestureHandler.mPackageName = "_UNKNOWN";
                    return;
                }
            case 4:
                int i7 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.getClass();
                try {
                    WindowManagerGlobal.getWindowManagerService().setNavBarVirtualKeyHapticFeedbackEnabled(false);
                    return;
                } catch (RemoteException e) {
                    Log.w("EdgeBackGestureHandler", "Failed to disable navigation bar button haptics: ", e);
                    return;
                }
            default:
                int i8 = EdgeBackGestureHandler.MAX_LONG_PRESS_TIMEOUT;
                edgeBackGestureHandler.getClass();
                UiThreadContext uiThreadContext = edgeBackGestureHandler.mUiThreadContext;
                try {
                    Trace.beginSection("EdgeBackGestureHandler#updateIsEnabled");
                    boolean z = edgeBackGestureHandler.mInGestureNavMode || (edgeBackGestureHandler.mUsingThreeButtonNav && !((ArraySet) edgeBackGestureHandler.mTrackpadsConnected).isEmpty());
                    edgeBackGestureHandler.mIsGestureHandlingEnabled = z;
                    boolean z2 = edgeBackGestureHandler.mIsAttached;
                    boolean z3 = z2 && z;
                    if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && !z) {
                        z3 = z2 && edgeBackGestureHandler.mIsLargeCoverBackGestureEnabled;
                    }
                    if (z3 != edgeBackGestureHandler.mIsEnabled) {
                        edgeBackGestureHandler.mIsEnabled = z3;
                        Iterator it = ((HashMap) edgeBackGestureHandler.mInputMonitorResources).entrySet().iterator();
                        while (it.hasNext()) {
                            EdgeBackGestureHandler.InputMonitorResource inputMonitorResource = (EdgeBackGestureHandler.InputMonitorResource) ((Map.Entry) it.next()).getValue();
                            inputMonitorResource.mInputEventReceiver.dispose();
                            inputMonitorResource.mInputMonitorCompat.dispose();
                            it.remove();
                        }
                        NavigationEdgeBackPlugin navigationEdgeBackPlugin = edgeBackGestureHandler.mEdgeBackPlugin;
                        if (navigationEdgeBackPlugin != null) {
                            navigationEdgeBackPlugin.onDestroy();
                            edgeBackGestureHandler.mEdgeBackPlugin = null;
                        }
                        boolean z4 = edgeBackGestureHandler.mIsEnabled;
                        EdgeBackGestureHandler.AnonymousClass3 anonymousClass3 = edgeBackGestureHandler.mTaskStackListener;
                        EdgeBackSplitGestureHandler edgeBackSplitGestureHandler = edgeBackGestureHandler.mEdgeBackSplitGestureHandler;
                        PluginManager pluginManager = edgeBackGestureHandler.mPluginManager;
                        int i9 = edgeBackGestureHandler.mDisplayId;
                        if (z4) {
                            Executor executor = edgeBackGestureHandler.mBackgroundExecutor;
                            final GestureNavigationSettingsObserver gestureNavigationSettingsObserver = edgeBackGestureHandler.mGestureNavigationSettingsObserver;
                            Objects.requireNonNull(gestureNavigationSettingsObserver);
                            executor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda14
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i10 = i;
                                    GestureNavigationSettingsObserver gestureNavigationSettingsObserver2 = gestureNavigationSettingsObserver;
                                    switch (i10) {
                                        case 0:
                                            gestureNavigationSettingsObserver2.unregister();
                                            break;
                                        default:
                                            gestureNavigationSettingsObserver2.register();
                                            break;
                                    }
                                }
                            });
                            if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                                edgeBackGestureHandler.updateCurrentUserResources();
                            }
                            edgeBackGestureHandler.updateDisplaySize();
                            TaskStackChangeListeners.INSTANCE.registerTaskStackListener(anonymousClass3);
                            final Executor executor2 = uiThreadContext.getExecutor();
                            Objects.requireNonNull(executor2);
                            DeviceConfig.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda17
                                @Override // java.util.concurrent.Executor
                                public final void execute(Runnable runnable) {
                                    executor2.execute(runnable);
                                }
                            }, edgeBackGestureHandler.mOnPropertiesChangedListener);
                            edgeBackGestureHandler.mPipOptional.ifPresent(new EdgeBackGestureHandler$$ExternalSyntheticLambda0(edgeBackGestureHandler, 2));
                            edgeBackGestureHandler.mDesktopModeOptional.ifPresent(new EdgeBackGestureHandler$$ExternalSyntheticLambda0(edgeBackGestureHandler, 3));
                            try {
                                edgeBackGestureHandler.mWindowManagerService.registerSystemGestureExclusionListener(edgeBackGestureHandler.mGestureExclusionListener, i9);
                            } catch (RemoteException | IllegalArgumentException e2) {
                                Log.e("EdgeBackGestureHandler", "Failed to register window manager callbacks", e2);
                            }
                            edgeBackGestureHandler.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(edgeBackGestureHandler.mContext, "EdgeBack");
                            ((HashMap) edgeBackGestureHandler.mInputMonitorResources).put(Integer.valueOf(i9), new EdgeBackGestureHandler.InputMonitorResource(edgeBackGestureHandler, i9, i2));
                            BackPanelController backPanelControllerCreate = edgeBackGestureHandler.mBackPanelControllerFactory.create(edgeBackGestureHandler.mContext, edgeBackGestureHandler.mWindowManager, uiThreadContext.getHandler());
                            backPanelControllerCreate.init();
                            edgeBackGestureHandler.setEdgeBackPlugin(backPanelControllerCreate);
                            pluginManager.addPluginListener((PluginListener) edgeBackGestureHandler, NavigationEdgeBackPlugin.class, false);
                            edgeBackGestureHandler.mBlockedActivitiesJob = edgeBackGestureHandler.mJavaAdapter.alwaysCollectFlow(edgeBackGestureHandler.mGestureInteractor.topActivityBlocked, new EdgeBackGestureHandler$$ExternalSyntheticLambda0(edgeBackGestureHandler, 5));
                            if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
                                edgeBackSplitGestureHandler.setEnabled(true);
                                edgeBackSplitGestureHandler.inputMonitor = ((EdgeBackGestureHandler.InputMonitorResource) ((HashMap) edgeBackGestureHandler.mInputMonitorResources).get(Integer.valueOf(i9))).mInputMonitorCompat;
                            }
                        } else {
                            if (BasicRune.NAVBAR_MW_ENTER_SPLIT_USING_GESTURE) {
                                edgeBackSplitGestureHandler.setEnabled(false);
                            }
                            Executor executor3 = edgeBackGestureHandler.mBackgroundExecutor;
                            final GestureNavigationSettingsObserver gestureNavigationSettingsObserver2 = edgeBackGestureHandler.mGestureNavigationSettingsObserver;
                            Objects.requireNonNull(gestureNavigationSettingsObserver2);
                            executor3.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda14
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i10 = i2;
                                    GestureNavigationSettingsObserver gestureNavigationSettingsObserver22 = gestureNavigationSettingsObserver2;
                                    switch (i10) {
                                        case 0:
                                            gestureNavigationSettingsObserver22.unregister();
                                            break;
                                        default:
                                            gestureNavigationSettingsObserver22.register();
                                            break;
                                    }
                                }
                            });
                            pluginManager.removePluginListener(edgeBackGestureHandler);
                            TaskStackChangeListeners.INSTANCE.unregisterTaskStackListener(anonymousClass3);
                            DeviceConfig.removeOnPropertiesChangedListener(edgeBackGestureHandler.mOnPropertiesChangedListener);
                            edgeBackGestureHandler.mPipOptional.ifPresent(new EdgeBackGestureHandler$$ExternalSyntheticLambda0(edgeBackGestureHandler, i));
                            try {
                                edgeBackGestureHandler.mWindowManagerService.unregisterSystemGestureExclusionListener(edgeBackGestureHandler.mGestureExclusionListener, i9);
                            } catch (RemoteException | IllegalArgumentException e3) {
                                Log.e("EdgeBackGestureHandler", "Failed to unregister window manager callbacks", e3);
                            }
                            Job job = edgeBackGestureHandler.mBlockedActivitiesJob;
                            if (job != null) {
                                job.cancel(new CancellationException());
                                edgeBackGestureHandler.mBlockedActivitiesJob = null;
                            }
                            edgeBackGestureHandler.mBlockedActivities.clear();
                        }
                        edgeBackGestureHandler.updateMLModelState();
                    }
                    return;
                } finally {
                    Trace.endSection();
                }
        }
    }
}
