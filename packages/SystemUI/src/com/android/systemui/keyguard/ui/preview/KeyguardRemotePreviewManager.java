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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableImageView;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardRemotePreviewManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayMap activePreviews = new ArrayMap();
    public final CoroutineScope applicationScope;
    public final Handler backgroundHandler;
    public final CoroutineDispatcher mainDispatcher;
    public final KeyguardPreviewRendererFactory previewRendererFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
                CoroutineTracingKt.launchTraced$default(previewLifecycleObserver.scope, previewLifecycleObserver.mainDispatcher, null, new PreviewLifecycleObserver$onDestroy$2$1(keyguardPreviewRenderer, null), 5);
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
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (bundle != null) {
            try {
                final KeyguardPreviewRenderer keyguardPreviewRenderer = (KeyguardPreviewRenderer) CoroutineTracingKt.runBlockingTraced("KeyguardRemotePreviewManager#previewRendererFactory.create", coroutineDispatcher, new KeyguardRemotePreviewManager$preview$renderer$1(this, bundle, null));
                previewLifecycleObserver = new PreviewLifecycleObserver(this.applicationScope, coroutineDispatcher, keyguardPreviewRenderer, new KeyguardRemotePreviewManager$preview$1(this));
                try {
                    if (Log.isLoggable("KeyguardRemotePreviewManager", 3)) {
                        Log.d("KeyguardRemotePreviewManager", "Created observer " + previewLifecycleObserver);
                    }
                    PreviewLifecycleObserver previewLifecycleObserver2 = (PreviewLifecycleObserver) this.activePreviews.get(keyguardPreviewRenderer.id);
                    if (previewLifecycleObserver2 != null) {
                        destroyObserver(previewLifecycleObserver2);
                    }
                    this.activePreviews.put(keyguardPreviewRenderer.id, previewLifecycleObserver);
                    keyguardPreviewRenderer.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$render$1
                        /* JADX WARN: Type inference failed for: r5v32, types: [android.content.BroadcastReceiver, com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$receiver$1] */
                        @Override // java.lang.Runnable
                        public final void run() {
                            DisplayInfo displayInfo;
                            final int i = 0;
                            KeyguardPreviewRenderer keyguardPreviewRenderer2 = KeyguardPreviewRenderer.this;
                            Display display = keyguardPreviewRenderer2.display;
                            Context contextThemeWrapper = display != null ? new ContextThemeWrapper(keyguardPreviewRenderer2.context.createDisplayContext(display), keyguardPreviewRenderer2.context.getTheme()) : keyguardPreviewRenderer2.context;
                            ConstraintLayout constraintLayout = new ConstraintLayout(contextThemeWrapper);
                            final KeyguardPreviewRenderer keyguardPreviewRenderer3 = KeyguardPreviewRenderer.this;
                            keyguardPreviewRenderer3.getClass();
                            KeyguardRootView keyguardRootView = new KeyguardRootView(contextThemeWrapper, null);
                            constraintLayout.addView(keyguardRootView, new FrameLayout.LayoutParams(-1, -1));
                            Rect rect = ((UdfpsOverlayParams) keyguardPreviewRenderer3.udfpsOverlayInteractor.udfpsOverlayParams.$$delegate_0.getValue()).sensorBounds;
                            if (!Intrinsics.areEqual(rect, new Rect())) {
                                View inflate = LayoutInflater.from(contextThemeWrapper).inflate(R.layout.udfps_keyguard_preview, (ViewGroup) keyguardRootView, false);
                                KeyguardPreviewClockViewBinder.INSTANCE.getClass();
                                int i2 = KeyguardPreviewClockViewBinder.lockId;
                                inflate.setId(i2);
                                keyguardRootView.addView(inflate);
                                ConstraintSet constraintSet = new ConstraintSet();
                                constraintSet.clone(keyguardRootView);
                                constraintSet.constrainWidth(i2, rect.width());
                                constraintSet.constrainHeight(i2, rect.height());
                                constraintSet.connect(i2, 3, 0, 3, rect.top);
                                constraintSet.connect(i2, 6, 0, 6, rect.left);
                                constraintSet.applyTo(keyguardRootView);
                            }
                            ConstraintSet constraintSet2 = new ConstraintSet();
                            constraintSet2.clone(keyguardRootView);
                            DefaultShortcutsSection defaultShortcutsSection = keyguardPreviewRenderer3.defaultShortcutsSection;
                            defaultShortcutsSection.addViews(keyguardRootView);
                            defaultShortcutsSection.applyConstraints(constraintSet2);
                            constraintSet2.applyTo(keyguardRootView);
                            LaunchableImageView launchableImageView = (LaunchableImageView) keyguardRootView.findViewById(R.id.start_button);
                            Float valueOf = Float.valueOf(1.0f);
                            KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel = keyguardPreviewRenderer3.quickAffordancesCombinedViewModel;
                            KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder = keyguardPreviewRenderer3.keyguardQuickAffordanceViewBinder;
                            if (launchableImageView != null) {
                                keyguardPreviewRenderer3.shortcutsBindings.add(keyguardQuickAffordanceViewBinder.bind(launchableImageView, keyguardQuickAffordancesCombinedViewModel.startButton, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(valueOf), new Function1() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo779invoke(Object obj) {
                                        int i3 = i;
                                        int intValue = ((Integer) obj).intValue();
                                        switch (i3) {
                                            case 0:
                                                keyguardPreviewRenderer3.indicationController.showTransientIndication(intValue);
                                                break;
                                            default:
                                                keyguardPreviewRenderer3.indicationController.showTransientIndication(intValue);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }));
                            }
                            LaunchableImageView launchableImageView2 = (LaunchableImageView) keyguardRootView.findViewById(R.id.end_button);
                            if (launchableImageView2 != null) {
                                final int i3 = 1;
                                keyguardPreviewRenderer3.shortcutsBindings.add(keyguardQuickAffordanceViewBinder.bind(launchableImageView2, keyguardQuickAffordancesCombinedViewModel.endButton, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(valueOf), new Function1() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo779invoke(Object obj) {
                                        int i32 = i3;
                                        int intValue = ((Integer) obj).intValue();
                                        switch (i32) {
                                            case 0:
                                                keyguardPreviewRenderer3.indicationController.showTransientIndication(intValue);
                                                break;
                                            default:
                                                keyguardPreviewRenderer3.indicationController.showTransientIndication(intValue);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }));
                            }
                            if (!keyguardPreviewRenderer3.shouldHideClock) {
                                constraintLayout.getResources();
                                final ?? r5 = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$receiver$1
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
                                BroadcastDispatcher.registerReceiver$default(keyguardPreviewRenderer3.broadcastDispatcher, r5, m, null, null, 0, null, 60);
                                keyguardPreviewRenderer3.disposables.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$2
                                    @Override // kotlinx.coroutines.DisposableHandle
                                    public final void dispose() {
                                        KeyguardPreviewRenderer.this.broadcastDispatcher.unregisterReceiver(r5);
                                    }
                                });
                                KeyguardPreviewRenderer$setupKeyguardRootView$1 keyguardPreviewRenderer$setupKeyguardRootView$1 = new KeyguardPreviewRenderer$setupKeyguardRootView$1(keyguardPreviewRenderer3);
                                Display display2 = keyguardPreviewRenderer3.display;
                                display2.getClass();
                                KeyguardPreviewClockViewBinder.bind(keyguardRootView, keyguardPreviewRenderer3.clockViewModel, keyguardPreviewRenderer3.clockRegistry, keyguardPreviewRenderer$setupKeyguardRootView$1, new ClockPreviewConfig(contextThemeWrapper, keyguardPreviewRenderer3.getPreviewShadeLayoutWide(display2), false, null, null, 24, null));
                            }
                            LockscreenSmartspaceController lockscreenSmartspaceController = keyguardPreviewRenderer3.lockscreenSmartspaceController;
                            boolean z = lockscreenSmartspaceController.isEnabled;
                            KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel = keyguardPreviewRenderer3.smartspaceViewModel;
                            if (z && lockscreenSmartspaceController.isDateWeatherDecoupled) {
                                View view = keyguardPreviewRenderer3.smartSpaceView;
                                if (view != null) {
                                    keyguardRootView.removeView(view);
                                }
                                keyguardPreviewRenderer3.smartSpaceView = lockscreenSmartspaceController.buildAndConnectDateView(keyguardRootView);
                                Display display3 = keyguardPreviewRenderer3.display;
                                display3.getClass();
                                int smallClockTopPadding = new ClockPreviewConfig(contextThemeWrapper, keyguardPreviewRenderer3.getPreviewShadeLayoutWide(display3), false, null, null, 24, null).getSmallClockTopPadding(((SystemBarUtilsProxyImpl) keyguardPreviewSmartspaceViewModel.systemBarUtils).getStatusBarHeaderHeightKeyguard());
                                KeyguardSmartspaceViewModel.Companion.getClass();
                                int dimensionPixelSize = contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start);
                                Resources resources = contextThemeWrapper.getResources();
                                int i4 = R$dimen.status_view_margin_horizontal;
                                int dimensionPixelSize2 = resources.getDimensionPixelSize(i4) + dimensionPixelSize;
                                int m2 = StrongAuthPopup$$ExternalSyntheticOutline0.m(contextThemeWrapper, i4, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_end));
                                View view2 = keyguardPreviewRenderer3.smartSpaceView;
                                if (view2 != null) {
                                    view2.setPaddingRelative(dimensionPixelSize2, smallClockTopPadding, m2, 0);
                                    view2.setClickable(false);
                                    view2.setVisibility(4);
                                    keyguardRootView.addView(view2, new FrameLayout.LayoutParams(-1, -2));
                                }
                                View view3 = keyguardPreviewRenderer3.smartSpaceView;
                                if (view3 != null) {
                                    view3.setAlpha(keyguardPreviewRenderer3.shouldHighlightSelectedAffordance ? 0.3f : 1.0f);
                                }
                            }
                            View view4 = keyguardPreviewRenderer3.smartSpaceView;
                            if (view4 != null) {
                                Display display4 = keyguardPreviewRenderer3.display;
                                display4.getClass();
                                KeyguardPreviewSmartspaceViewBinder.bind(keyguardPreviewSmartspaceViewModel, new ClockPreviewConfig(contextThemeWrapper, keyguardPreviewRenderer3.getPreviewShadeLayoutWide(display4), false, null, null), view4);
                            }
                            Display display5 = KeyguardPreviewRenderer.this.display;
                            if (display5 != null) {
                                displayInfo = new DisplayInfo();
                                display5.getDisplayInfo(displayInfo);
                            } else {
                                displayInfo = null;
                            }
                            constraintLayout.measure(View.MeasureSpec.makeMeasureSpec(displayInfo != null ? displayInfo.logicalWidth : KeyguardPreviewRenderer.this.windowManager.getCurrentWindowMetrics().getBounds().width(), 1073741824), View.MeasureSpec.makeMeasureSpec(displayInfo != null ? displayInfo.logicalHeight : KeyguardPreviewRenderer.this.windowManager.getCurrentWindowMetrics().getBounds().height(), 1073741824));
                            constraintLayout.layout(0, 0, constraintLayout.getMeasuredWidth(), constraintLayout.getMeasuredHeight());
                            float measuredWidth = KeyguardPreviewRenderer.this.width / constraintLayout.getMeasuredWidth();
                            float measuredHeight = KeyguardPreviewRenderer.this.height / constraintLayout.getMeasuredHeight();
                            if (measuredWidth > measuredHeight) {
                                measuredWidth = measuredHeight;
                            }
                            constraintLayout.setScaleX(measuredWidth);
                            constraintLayout.setScaleY(measuredWidth);
                            constraintLayout.setPivotX(0.0f);
                            constraintLayout.setPivotY(0.0f);
                            float f = 2;
                            constraintLayout.setTranslationX((KeyguardPreviewRenderer.this.width - (constraintLayout.getWidth() * measuredWidth)) / f);
                            constraintLayout.setTranslationY((KeyguardPreviewRenderer.this.height - (measuredWidth * constraintLayout.getHeight())) / f);
                            KeyguardPreviewRenderer keyguardPreviewRenderer4 = KeyguardPreviewRenderer.this;
                            if (keyguardPreviewRenderer4.isDestroyed) {
                                return;
                            }
                            keyguardPreviewRenderer4.host.setView(constraintLayout, constraintLayout.getMeasuredWidth(), constraintLayout.getMeasuredHeight());
                        }
                    });
                    IBinder iBinder = keyguardPreviewRenderer.hostToken;
                    if (iBinder != null) {
                        iBinder.linkToDeath(previewLifecycleObserver, 0);
                    }
                    Bundle bundle2 = new Bundle();
                    SurfaceControlViewHost.SurfacePackage surfacePackage = keyguardPreviewRenderer.host.getSurfacePackage();
                    if (surfacePackage == null) {
                        throw new IllegalStateException("Required value was null.");
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
                    if (previewLifecycleObserver != null) {
                        destroyObserver(previewLifecycleObserver);
                    }
                    return null;
                }
            } catch (Exception e2) {
                e = e2;
                previewLifecycleObserver = null;
            }
        }
        return null;
    }
}
