package com.android.systemui.keyguard.ui.preview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.communal.ui.binder.CommunalTutorialIndicatorViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.clocks.DefaultClockController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.util.kotlin.DisposableHandles;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

public final class KeyguardRemotePreviewManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayMap activePreviews = new ArrayMap();
    public final CoroutineScope applicationScope;
    public final Handler backgroundHandler;
    public final CoroutineDispatcher mainDispatcher;
    public final KeyguardPreviewRendererFactory previewRendererFactory;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getKEY_PREVIEW_CALLBACK$annotations() {
        }

        public static /* synthetic */ void getKEY_PREVIEW_SURFACE_PACKAGE$annotations() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardRemotePreviewManager(KeyguardPreviewRendererFactory keyguardPreviewRendererFactory, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        this.previewRendererFactory = keyguardPreviewRendererFactory;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundHandler = handler;
    }

    public final void destroyObserver(PreviewLifecycleObserver previewLifecycleObserver) {
        Pair pair = null;
        if (!previewLifecycleObserver.isDestroyedOrDestroying) {
            if (Log.isLoggable("KeyguardRemotePreviewManager", 3)) {
                Log.d("KeyguardRemotePreviewManager", "Destroying " + previewLifecycleObserver);
            }
            previewLifecycleObserver.isDestroyedOrDestroying = true;
            KeyguardPreviewRenderer keyguardPreviewRenderer = previewLifecycleObserver.renderer;
            if (keyguardPreviewRenderer != null) {
                previewLifecycleObserver.renderer = null;
                previewLifecycleObserver.onDestroy = null;
                IBinder iBinder = keyguardPreviewRenderer.hostToken;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(previewLifecycleObserver, 0);
                }
                BuildersKt.launch$default(previewLifecycleObserver.scope, previewLifecycleObserver.mainDispatcher, null, new PreviewLifecycleObserver$onDestroy$2$1(keyguardPreviewRenderer, null), 2);
                pair = keyguardPreviewRenderer.id;
            }
        }
        if (pair == null || this.activePreviews.get(pair) != previewLifecycleObserver) {
            return;
        }
        this.activePreviews.remove(pair);
    }

    public final Bundle preview(Bundle bundle) {
        PreviewLifecycleObserver previewLifecycleObserver;
        if (bundle == null) {
            return null;
        }
        try {
            final KeyguardPreviewRenderer create = this.previewRendererFactory.create(bundle);
            previewLifecycleObserver = new PreviewLifecycleObserver(this.applicationScope, this.mainDispatcher, create, new KeyguardRemotePreviewManager$preview$1(this));
            try {
                if (Log.isLoggable("KeyguardRemotePreviewManager", 3)) {
                    Log.d("KeyguardRemotePreviewManager", "Created observer " + previewLifecycleObserver);
                }
                PreviewLifecycleObserver previewLifecycleObserver2 = (PreviewLifecycleObserver) this.activePreviews.get(create.id);
                if (previewLifecycleObserver2 != null) {
                    destroyObserver(previewLifecycleObserver2);
                }
                this.activePreviews.put(create.id, previewLifecycleObserver);
                create.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$render$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayInfo displayInfo;
                        KeyguardPreviewRenderer keyguardPreviewRenderer = KeyguardPreviewRenderer.this;
                        Display display = keyguardPreviewRenderer.display;
                        Context contextThemeWrapper = display != null ? new ContextThemeWrapper(keyguardPreviewRenderer.context.createDisplayContext(display), keyguardPreviewRenderer.context.getTheme()) : keyguardPreviewRenderer.context;
                        final FrameLayout frameLayout = new FrameLayout(contextThemeWrapper);
                        final KeyguardPreviewRenderer keyguardPreviewRenderer2 = KeyguardPreviewRenderer.this;
                        keyguardPreviewRenderer2.getClass();
                        KeyguardRootView keyguardRootView = new KeyguardRootView(contextThemeWrapper, null);
                        Flags.keyguardBottomAreaRefactor();
                        DisposableHandles bind = KeyguardRootViewBinder.bind(keyguardRootView, keyguardPreviewRenderer2.keyguardRootViewModel, keyguardPreviewRenderer2.keyguardBlueprintViewModel, keyguardPreviewRenderer2.configuration, keyguardPreviewRenderer2.occludingAppDeviceEntryMessageViewModel, keyguardPreviewRenderer2.chipbarCoordinator, keyguardPreviewRenderer2.screenOffAnimationController, keyguardPreviewRenderer2.shadeInteractor, keyguardPreviewRenderer2.keyguardClockInteractor, keyguardPreviewRenderer2.keyguardClockViewModel, null, null, null, null);
                        DisposableHandles disposableHandles = keyguardPreviewRenderer2.disposables;
                        disposableHandles.plusAssign(bind);
                        frameLayout.addView(keyguardRootView, new FrameLayout.LayoutParams(-1, -1));
                        Flags.migrateClocksToBlueprint();
                        Rect rect = ((UdfpsOverlayParams) keyguardPreviewRenderer2.udfpsOverlayInteractor.udfpsOverlayParams.$$delegate_0.getValue()).sensorBounds;
                        if (!Intrinsics.areEqual(rect, new Rect())) {
                            View inflate = LayoutInflater.from(contextThemeWrapper).inflate(R.layout.udfps_keyguard_preview, (ViewGroup) frameLayout, false);
                            Flags.migrateClocksToBlueprint();
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(rect.width(), rect.height());
                            layoutParams.setMarginsRelative(rect.left, rect.top, rect.right, rect.bottom);
                            frameLayout.addView(inflate, layoutParams);
                        }
                        Flags.keyguardBottomAreaRefactor();
                        if (!keyguardPreviewRenderer2.shouldHideClock) {
                            Resources resources = frameLayout.getResources();
                            Flags.migrateClocksToBlueprint();
                            FrameLayout frameLayout2 = new FrameLayout(contextThemeWrapper);
                            keyguardPreviewRenderer2.largeClockHostView = frameLayout2;
                            frameLayout2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                            FrameLayout frameLayout3 = keyguardPreviewRenderer2.largeClockHostView;
                            if (frameLayout3 == null) {
                                frameLayout3 = null;
                            }
                            frameLayout3.setVisibility(4);
                            FrameLayout frameLayout4 = keyguardPreviewRenderer2.largeClockHostView;
                            if (frameLayout4 == null) {
                                frameLayout4 = null;
                            }
                            frameLayout.addView(frameLayout4);
                            keyguardPreviewRenderer2.smallClockHostView = new FrameLayout(contextThemeWrapper);
                            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, resources.getDimensionPixelSize(R.dimen.small_clock_height));
                            layoutParams2.topMargin = resources.getDimensionPixelSize(R.dimen.small_clock_padding_top) + SystemBarUtils.getStatusBarHeight(contextThemeWrapper);
                            FrameLayout frameLayout5 = keyguardPreviewRenderer2.smallClockHostView;
                            if (frameLayout5 == null) {
                                frameLayout5 = null;
                            }
                            frameLayout5.setLayoutParams(layoutParams2);
                            FrameLayout frameLayout6 = keyguardPreviewRenderer2.smallClockHostView;
                            if (frameLayout6 == null) {
                                frameLayout6 = null;
                            }
                            frameLayout6.setPaddingRelative(resources.getDimensionPixelSize(R.dimen.clock_padding_start), 0, 0, 0);
                            FrameLayout frameLayout7 = keyguardPreviewRenderer2.smallClockHostView;
                            if (frameLayout7 == null) {
                                frameLayout7 = null;
                            }
                            frameLayout7.setClipChildren(false);
                            FrameLayout frameLayout8 = keyguardPreviewRenderer2.smallClockHostView;
                            if (frameLayout8 == null) {
                                frameLayout8 = null;
                            }
                            frameLayout.addView(frameLayout8);
                            FrameLayout frameLayout9 = keyguardPreviewRenderer2.smallClockHostView;
                            if (frameLayout9 == null) {
                                frameLayout9 = null;
                            }
                            frameLayout9.setVisibility(4);
                            Flags.migrateClocksToBlueprint();
                            final ?? r4 = new ClockRegistry.ClockChangeListener() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$clockChangeListener$1
                                @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
                                public final void onCurrentClockChanged() {
                                    int i = KeyguardPreviewRenderer.$r8$clinit;
                                    KeyguardPreviewRenderer keyguardPreviewRenderer3 = KeyguardPreviewRenderer.this;
                                    keyguardPreviewRenderer3.getClass();
                                    Flags.migrateClocksToBlueprint();
                                    BuildersKt.launch$default(keyguardPreviewRenderer3.coroutineScope, null, null, new KeyguardPreviewRenderer$onClockChanged$1(keyguardPreviewRenderer3, null), 3);
                                }
                            };
                            ClockRegistry clockRegistry = keyguardPreviewRenderer2.clockRegistry;
                            clockRegistry.f74assert.isMainThread();
                            ((ArrayList) clockRegistry.clockChangeListeners).add(r4);
                            disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$1
                                @Override // kotlinx.coroutines.DisposableHandle
                                public final void dispose() {
                                    ClockRegistry clockRegistry2 = KeyguardPreviewRenderer.this.clockRegistry;
                                    clockRegistry2.f74assert.isMainThread();
                                    ((ArrayList) clockRegistry2.clockChangeListeners).remove(r4);
                                }
                            });
                            keyguardPreviewRenderer2.clockController.registerListeners(frameLayout);
                            disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$2
                                @Override // kotlinx.coroutines.DisposableHandle
                                public final void dispose() {
                                    KeyguardPreviewRenderer.this.clockController.unregisterListeners();
                                }
                            });
                            final ?? r42 = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$receiver$1
                                @Override // android.content.BroadcastReceiver
                                public final void onReceive(Context context, Intent intent) {
                                    ClockController clockController = KeyguardPreviewRenderer.this.clockController.clock;
                                    if (clockController != null) {
                                        clockController.getSmallClock().getEvents().onTimeTick();
                                        clockController.getLargeClock().getEvents().onTimeTick();
                                    }
                                }
                            };
                            IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.TIME_TICK", "android.intent.action.TIME_SET");
                            Unit unit = Unit.INSTANCE;
                            BroadcastDispatcher.registerReceiver$default(keyguardPreviewRenderer2.broadcastDispatcher, r42, m, null, null, 0, null, 60);
                            disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$4
                                @Override // kotlinx.coroutines.DisposableHandle
                                public final void dispose() {
                                    KeyguardPreviewRenderer.this.broadcastDispatcher.unregisterReceiver(r42);
                                }
                            });
                            Flags.migrateClocksToBlueprint();
                            final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$layoutChangeListener$1
                                @Override // android.view.View.OnLayoutChangeListener
                                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                                    ClockFaceController smallClock;
                                    ClockFaceEvents events;
                                    ClockFaceController largeClock;
                                    ClockFaceEvents events2;
                                    ClockController clockController = KeyguardPreviewRenderer.this.clockController.clock;
                                    if (clockController instanceof DefaultClockController) {
                                        return;
                                    }
                                    if (clockController != null && (largeClock = clockController.getLargeClock()) != null && (events2 = largeClock.getEvents()) != null) {
                                        events2.onTargetRegionChanged(KeyguardClockSwitch.getLargeClockRegion(frameLayout));
                                    }
                                    ClockController clockController2 = KeyguardPreviewRenderer.this.clockController.clock;
                                    if (clockController2 == null || (smallClock = clockController2.getSmallClock()) == null || (events = smallClock.getEvents()) == null) {
                                        return;
                                    }
                                    events.onTargetRegionChanged(KeyguardClockSwitch.getSmallClockRegion(frameLayout));
                                }
                            };
                            frameLayout.addOnLayoutChangeListener(onLayoutChangeListener);
                            disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$5
                                @Override // kotlinx.coroutines.DisposableHandle
                                public final void dispose() {
                                    frameLayout.removeOnLayoutChangeListener(onLayoutChangeListener);
                                }
                            });
                            Flags.migrateClocksToBlueprint();
                            BuildersKt.launch$default(keyguardPreviewRenderer2.coroutineScope, null, null, new KeyguardPreviewRenderer$onClockChanged$1(keyguardPreviewRenderer2, null), 3);
                            Flags.migrateClocksToBlueprint();
                            FrameLayout frameLayout10 = keyguardPreviewRenderer2.largeClockHostView;
                            if (frameLayout10 == null) {
                                frameLayout10 = null;
                            }
                            FrameLayout frameLayout11 = keyguardPreviewRenderer2.smallClockHostView;
                            if (frameLayout11 == null) {
                                frameLayout11 = null;
                            }
                            KeyguardPreviewClockViewBinder.bind(frameLayout10, frameLayout11, keyguardPreviewRenderer2.clockViewModel);
                        }
                        LockscreenSmartspaceController lockscreenSmartspaceController = keyguardPreviewRenderer2.lockscreenSmartspaceController;
                        boolean isEnabled = lockscreenSmartspaceController.isEnabled();
                        int i = keyguardPreviewRenderer2.height;
                        int i2 = keyguardPreviewRenderer2.width;
                        KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel = keyguardPreviewRenderer2.smartspaceViewModel;
                        if (isEnabled && lockscreenSmartspaceController.isDateWeatherDecoupled()) {
                            View view = keyguardPreviewRenderer2.smartSpaceView;
                            if (view != null) {
                                frameLayout.removeView(view);
                            }
                            keyguardPreviewRenderer2.smartSpaceView = lockscreenSmartspaceController.buildAndConnectDateView(frameLayout);
                            boolean z = i2 > i;
                            keyguardPreviewSmartspaceViewModel.getClass();
                            int smallClockTopPadding = KeyguardPreviewSmartspaceViewModel.getSmallClockTopPadding(contextThemeWrapper, z);
                            KeyguardSmartspaceViewModel.Companion.getClass();
                            int m2 = StrongAuthPopup$$ExternalSyntheticOutline0.m(contextThemeWrapper, R.dimen.status_view_margin_horizontal, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start));
                            int m3 = StrongAuthPopup$$ExternalSyntheticOutline0.m(contextThemeWrapper, R.dimen.status_view_margin_horizontal, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_end));
                            View view2 = keyguardPreviewRenderer2.smartSpaceView;
                            if (view2 != null) {
                                view2.setPaddingRelative(m2, smallClockTopPadding, m3, 0);
                                view2.setClickable(false);
                                view2.setVisibility(4);
                                frameLayout.addView(view2, new FrameLayout.LayoutParams(-1, -2));
                            }
                            View view3 = keyguardPreviewRenderer2.smartSpaceView;
                            if (view3 != null) {
                                view3.setAlpha(keyguardPreviewRenderer2.shouldHighlightSelectedAffordance ? 0.3f : 1.0f);
                            }
                        }
                        View view4 = keyguardPreviewRenderer2.smartSpaceView;
                        if (view4 != null) {
                            KeyguardPreviewSmartspaceViewBinder.bind(keyguardPreviewSmartspaceViewModel, i2 > i, contextThemeWrapper, view4);
                        }
                        TextView textView = (TextView) keyguardRootView.findViewById(R.id.communal_tutorial_indicator);
                        if (textView != null) {
                            CommunalTutorialIndicatorViewBinder.INSTANCE.getClass();
                            CommunalTutorialIndicatorViewBinder.bind(textView, keyguardPreviewRenderer2.communalTutorialViewModel, true);
                        }
                        Flags.keyguardBottomAreaRefactor();
                        KeyguardPreviewRenderer keyguardPreviewRenderer3 = KeyguardPreviewRenderer.this;
                        KeyguardBottomAreaView keyguardBottomAreaView = (KeyguardBottomAreaView) LayoutInflater.from(keyguardPreviewRenderer3.context).inflate(R.layout.keyguard_bottom_area, (ViewGroup) frameLayout, false);
                        KeyguardBottomAreaView.Companion companion = KeyguardBottomAreaView.Companion;
                        keyguardBottomAreaView.init(keyguardPreviewRenderer3.bottomAreaViewModel, null, null, null, null, null, null);
                        frameLayout.addView(keyguardBottomAreaView, new FrameLayout.LayoutParams(-1, -1));
                        Display display2 = KeyguardPreviewRenderer.this.display;
                        if (display2 != null) {
                            displayInfo = new DisplayInfo();
                            display2.getDisplayInfo(displayInfo);
                        } else {
                            displayInfo = null;
                        }
                        frameLayout.measure(View.MeasureSpec.makeMeasureSpec(displayInfo != null ? displayInfo.logicalWidth : KeyguardPreviewRenderer.this.windowManager.getCurrentWindowMetrics().getBounds().width(), 1073741824), View.MeasureSpec.makeMeasureSpec(displayInfo != null ? displayInfo.logicalHeight : KeyguardPreviewRenderer.this.windowManager.getCurrentWindowMetrics().getBounds().height(), 1073741824));
                        frameLayout.layout(0, 0, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
                        float coerceAtMost = RangesKt___RangesKt.coerceAtMost(KeyguardPreviewRenderer.this.width / frameLayout.getMeasuredWidth(), KeyguardPreviewRenderer.this.height / frameLayout.getMeasuredHeight());
                        frameLayout.setScaleX(coerceAtMost);
                        frameLayout.setScaleY(coerceAtMost);
                        frameLayout.setPivotX(0.0f);
                        frameLayout.setPivotY(0.0f);
                        float f = 2;
                        frameLayout.setTranslationX((KeyguardPreviewRenderer.this.width - (frameLayout.getWidth() * coerceAtMost)) / f);
                        frameLayout.setTranslationY((KeyguardPreviewRenderer.this.height - (coerceAtMost * frameLayout.getHeight())) / f);
                        KeyguardPreviewRenderer keyguardPreviewRenderer4 = KeyguardPreviewRenderer.this;
                        if (keyguardPreviewRenderer4.isDestroyed) {
                            return;
                        }
                        keyguardPreviewRenderer4.host.setView(frameLayout, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
                    }
                });
                IBinder iBinder = create.hostToken;
                if (iBinder != null) {
                    iBinder.linkToDeath(previewLifecycleObserver, 0);
                }
                Bundle bundle2 = new Bundle();
                SurfaceControlViewHost.SurfacePackage surfacePackage = create.host.getSurfacePackage();
                if (surfacePackage == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                bundle2.putParcelable("surface_package", surfacePackage);
                Messenger messenger = new Messenger(new Handler(this.backgroundHandler.getLooper(), previewLifecycleObserver));
                Message obtain = Message.obtain();
                obtain.replyTo = messenger;
                bundle2.putParcelable("callback", obtain);
                return bundle2;
            } catch (Exception e) {
                e = e;
                Log.e("KeyguardRemotePreviewManager", "Unable to generate preview", e);
                if (previewLifecycleObserver == null) {
                    return null;
                }
                destroyObserver(previewLifecycleObserver);
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            previewLifecycleObserver = null;
        }
    }
}
