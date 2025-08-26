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
import android.os.RemoteException;
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
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class KeyguardRemotePreviewManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayMap activePreviews = new ArrayMap();
    public final CoroutineScope applicationScope;
    public final Handler backgroundHandler;
    public final CoroutineDispatcher mainDispatcher;
    public final KeyguardPreviewRendererFactory previewRendererFactory;

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

    /* renamed from: com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager$preview$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass1(Object obj) {
            super(1, obj, KeyguardRemotePreviewManager.class, "destroyObserver", "destroyObserver(Lcom/android/systemui/keyguard/ui/preview/PreviewLifecycleObserver;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            KeyguardRemotePreviewManager keyguardRemotePreviewManager = (KeyguardRemotePreviewManager) this.receiver;
            int i = KeyguardRemotePreviewManager.$r8$clinit;
            keyguardRemotePreviewManager.destroyObserver((PreviewLifecycleObserver) obj);
            return Unit.INSTANCE;
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

    public final Bundle preview(Bundle bundle) throws RemoteException {
        PreviewLifecycleObserver previewLifecycleObserver;
        final KeyguardPreviewRenderer keyguardPreviewRenderer;
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (bundle != null) {
            try {
                keyguardPreviewRenderer = (KeyguardPreviewRenderer) CoroutineTracingKt.runBlockingTraced("KeyguardRemotePreviewManager#previewRendererFactory.create", coroutineDispatcher, new KeyguardRemotePreviewManager$preview$renderer$1(this, bundle, null));
                previewLifecycleObserver = new PreviewLifecycleObserver(this.applicationScope, coroutineDispatcher, keyguardPreviewRenderer, new AnonymousClass1(this));
            } catch (Exception e) {
                e = e;
                previewLifecycleObserver = null;
            }
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
                    public final void run() throws Resources.NotFoundException {
                        DisplayInfo displayInfo;
                        final int i = 0;
                        KeyguardPreviewRenderer keyguardPreviewRenderer2 = keyguardPreviewRenderer;
                        Display display = keyguardPreviewRenderer2.display;
                        Context contextThemeWrapper = display != null ? new ContextThemeWrapper(keyguardPreviewRenderer2.context.createDisplayContext(display), keyguardPreviewRenderer2.context.getTheme()) : keyguardPreviewRenderer2.context;
                        ConstraintLayout constraintLayout = new ConstraintLayout(contextThemeWrapper);
                        final KeyguardPreviewRenderer keyguardPreviewRenderer3 = keyguardPreviewRenderer;
                        keyguardPreviewRenderer3.getClass();
                        KeyguardRootView keyguardRootView = new KeyguardRootView(contextThemeWrapper, null);
                        constraintLayout.addView(keyguardRootView, new FrameLayout.LayoutParams(-1, -1));
                        Rect rect = ((UdfpsOverlayParams) keyguardPreviewRenderer3.udfpsOverlayInteractor.udfpsOverlayParams.$$delegate_0.getValue()).sensorBounds;
                        if (!Intrinsics.areEqual(rect, new Rect())) {
                            View viewInflate = LayoutInflater.from(contextThemeWrapper).inflate(R.layout.udfps_keyguard_preview, (ViewGroup) keyguardRootView, false);
                            KeyguardPreviewClockViewBinder.INSTANCE.getClass();
                            int i2 = KeyguardPreviewClockViewBinder.lockId;
                            viewInflate.setId(i2);
                            keyguardRootView.addView(viewInflate);
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
                        Float fValueOf = Float.valueOf(1.0f);
                        KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel = keyguardPreviewRenderer3.quickAffordancesCombinedViewModel;
                        KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder = keyguardPreviewRenderer3.keyguardQuickAffordanceViewBinder;
                        if (launchableImageView != null) {
                            keyguardPreviewRenderer3.shortcutsBindings.add(keyguardQuickAffordanceViewBinder.bind(launchableImageView, keyguardQuickAffordancesCombinedViewModel.startButton, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(fValueOf), new Function1() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    int i3 = i;
                                    int iIntValue = ((Integer) obj).intValue();
                                    switch (i3) {
                                        case 0:
                                            keyguardPreviewRenderer3.indicationController.showTransientIndication(iIntValue);
                                            break;
                                        default:
                                            keyguardPreviewRenderer3.indicationController.showTransientIndication(iIntValue);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            }));
                        }
                        LaunchableImageView launchableImageView2 = (LaunchableImageView) keyguardRootView.findViewById(R.id.end_button);
                        if (launchableImageView2 != null) {
                            final int i3 = 1;
                            keyguardPreviewRenderer3.shortcutsBindings.add(keyguardQuickAffordanceViewBinder.bind(launchableImageView2, keyguardQuickAffordancesCombinedViewModel.endButton, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(fValueOf), new Function1() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    int i32 = i3;
                                    int iIntValue = ((Integer) obj).intValue();
                                    switch (i32) {
                                        case 0:
                                            keyguardPreviewRenderer3.indicationController.showTransientIndication(iIntValue);
                                            break;
                                        default:
                                            keyguardPreviewRenderer3.indicationController.showTransientIndication(iIntValue);
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
                                    ClockController clockController = keyguardPreviewRenderer3.clockController.clock;
                                    if (clockController != null) {
                                        clockController.getSmallClock().getEvents().onTimeTick();
                                        clockController.getLargeClock().getEvents().onTimeTick();
                                    }
                                }
                            };
                            IntentFilter intentFilterM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.TIME_TICK", "android.intent.action.TIME_SET");
                            Unit unit = Unit.INSTANCE;
                            BroadcastDispatcher.registerReceiver$default(keyguardPreviewRenderer3.broadcastDispatcher, r5, intentFilterM, null, null, 0, null, 60);
                            keyguardPreviewRenderer3.disposables.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$2
                                @Override // kotlinx.coroutines.DisposableHandle
                                public final void dispose() {
                                    keyguardPreviewRenderer3.broadcastDispatcher.unregisterReceiver(r5);
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
                            int iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(contextThemeWrapper, i4, contextThemeWrapper.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_end));
                            View view2 = keyguardPreviewRenderer3.smartSpaceView;
                            if (view2 != null) {
                                view2.setPaddingRelative(dimensionPixelSize2, smallClockTopPadding, iM, 0);
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
                        Display display5 = keyguardPreviewRenderer.display;
                        if (display5 != null) {
                            displayInfo = new DisplayInfo();
                            display5.getDisplayInfo(displayInfo);
                        } else {
                            displayInfo = null;
                        }
                        constraintLayout.measure(View.MeasureSpec.makeMeasureSpec(displayInfo != null ? displayInfo.logicalWidth : keyguardPreviewRenderer.windowManager.getCurrentWindowMetrics().getBounds().width(), 1073741824), View.MeasureSpec.makeMeasureSpec(displayInfo != null ? displayInfo.logicalHeight : keyguardPreviewRenderer.windowManager.getCurrentWindowMetrics().getBounds().height(), 1073741824));
                        constraintLayout.layout(0, 0, constraintLayout.getMeasuredWidth(), constraintLayout.getMeasuredHeight());
                        float measuredWidth = keyguardPreviewRenderer.width / constraintLayout.getMeasuredWidth();
                        float measuredHeight = keyguardPreviewRenderer.height / constraintLayout.getMeasuredHeight();
                        if (measuredWidth > measuredHeight) {
                            measuredWidth = measuredHeight;
                        }
                        constraintLayout.setScaleX(measuredWidth);
                        constraintLayout.setScaleY(measuredWidth);
                        constraintLayout.setPivotX(0.0f);
                        constraintLayout.setPivotY(0.0f);
                        float f = 2;
                        constraintLayout.setTranslationX((keyguardPreviewRenderer.width - (constraintLayout.getWidth() * measuredWidth)) / f);
                        constraintLayout.setTranslationY((keyguardPreviewRenderer.height - (measuredWidth * constraintLayout.getHeight())) / f);
                        KeyguardPreviewRenderer keyguardPreviewRenderer4 = keyguardPreviewRenderer;
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
                Message messageObtain = Message.obtain();
                messageObtain.replyTo = messenger;
                bundle2.putParcelable("callback", messageObtain);
                return bundle2;
            } catch (Exception e2) {
                e = e2;
                Log.e("KeyguardRemotePreviewManager", "Unable to generate preview", e);
                if (previewLifecycleObserver != null) {
                    destroyObserver(previewLifecycleObserver);
                }
                return null;
            }
        }
        return null;
    }
}
