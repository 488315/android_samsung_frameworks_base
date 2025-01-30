package com.android.systemui.keyguard.p009ui.preview;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.p009ui.binder.KeyguardPreviewClockViewBinder;
import com.android.systemui.keyguard.p009ui.binder.KeyguardPreviewSmartspaceViewBinder;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockFaceController;
import com.android.systemui.plugins.ClockFaceEvents;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.clocks.DefaultClockController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.util.Assert;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardRemotePreviewManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayMap activePreviews = new ArrayMap();
    public final CoroutineScope applicationScope;
    public final Handler backgroundHandler;
    public final CoroutineDispatcher mainDispatcher;
    public final KeyguardPreviewRendererFactory previewRendererFactory;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PreviewLifecycleObserver implements Handler.Callback, IBinder.DeathRecipient {
        public boolean isDestroyedOrDestroying;
        public final CoroutineDispatcher mainDispatcher;
        public final KeyguardPreviewRenderer renderer;
        public final Function1 requestDestruction;
        public final CoroutineScope scope;

        public PreviewLifecycleObserver(KeyguardPreviewRenderer keyguardPreviewRenderer, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Function1 function1) {
            this.renderer = keyguardPreviewRenderer;
            this.scope = coroutineScope;
            this.mainDispatcher = coroutineDispatcher;
            this.requestDestruction = function1;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.requestDestruction.invoke(this);
        }

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            if (this.isDestroyedOrDestroying) {
                return true;
            }
            int i = message.what;
            if (i == 1111) {
                final KeyguardPreviewRenderer keyguardPreviewRenderer = this.renderer;
                final boolean z = message.getData().getBoolean("hide_smart_space");
                keyguardPreviewRenderer.getClass();
                keyguardPreviewRenderer.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$hideSmartspace$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        View view = KeyguardPreviewRenderer.this.smartSpaceView;
                        if (view == null) {
                            return;
                        }
                        view.setVisibility(z ? 4 : 0);
                    }
                });
            } else if (i != 1337) {
                this.requestDestruction.invoke(this);
            } else {
                String string = message.getData().getString("slot_id");
                if (string != null) {
                    this.renderer.bottomAreaViewModel.selectedPreviewSlotId.setValue(string);
                }
            }
            return true;
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
        IBinder iBinder = null;
        if (!previewLifecycleObserver.isDestroyedOrDestroying) {
            previewLifecycleObserver.isDestroyedOrDestroying = true;
            IBinder iBinder2 = previewLifecycleObserver.renderer.hostToken;
            if (iBinder2 != null) {
                iBinder2.unlinkToDeath(previewLifecycleObserver, 0);
            }
            BuildersKt.launch$default(previewLifecycleObserver.scope, previewLifecycleObserver.mainDispatcher, null, new C1748xb7a5b90f(previewLifecycleObserver, null), 2);
            iBinder = iBinder2;
        }
        if (iBinder != null) {
            ArrayMap arrayMap = this.activePreviews;
            if (arrayMap.get(iBinder) == previewLifecycleObserver) {
                arrayMap.remove(iBinder);
            }
        }
    }

    public final Bundle preview(Bundle bundle) {
        PreviewLifecycleObserver previewLifecycleObserver;
        final KeyguardPreviewRenderer create;
        IBinder iBinder;
        ArrayMap arrayMap = this.activePreviews;
        if (bundle == null) {
            return null;
        }
        try {
            create = this.previewRendererFactory.create(bundle);
            iBinder = create.hostToken;
            PreviewLifecycleObserver previewLifecycleObserver2 = (PreviewLifecycleObserver) arrayMap.get(iBinder);
            if (previewLifecycleObserver2 != null) {
                destroyObserver(previewLifecycleObserver2);
            }
            previewLifecycleObserver = new PreviewLifecycleObserver(create, this.applicationScope, this.mainDispatcher, new KeyguardRemotePreviewManager$preview$2(this));
        } catch (Exception e) {
            e = e;
            previewLifecycleObserver = null;
        }
        try {
            arrayMap.put(iBinder, previewLifecycleObserver);
            create.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$render$1
                /* JADX WARN: Type inference failed for: r2v40, types: [com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$clockChangeListener$1, java.lang.Object] */
                /* JADX WARN: Type inference failed for: r2v43, types: [android.content.BroadcastReceiver, com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$receiver$1] */
                @Override // java.lang.Runnable
                public final void run() {
                    final FrameLayout frameLayout = new FrameLayout(KeyguardPreviewRenderer.this.context);
                    KeyguardPreviewRenderer keyguardPreviewRenderer = KeyguardPreviewRenderer.this;
                    KeyguardBottomAreaView keyguardBottomAreaView = (KeyguardBottomAreaView) LayoutInflater.from(keyguardPreviewRenderer.context).inflate(R.layout.keyguard_bottom_area, (ViewGroup) frameLayout, false);
                    KeyguardBottomAreaViewModel keyguardBottomAreaViewModel = keyguardPreviewRenderer.bottomAreaViewModel;
                    KeyguardBottomAreaView.Companion companion = KeyguardBottomAreaView.Companion;
                    keyguardBottomAreaView.init(keyguardBottomAreaViewModel, null, null, null, null, null);
                    frameLayout.addView(keyguardBottomAreaView, new FrameLayout.LayoutParams(-1, -1));
                    KeyguardPreviewRenderer keyguardPreviewRenderer2 = KeyguardPreviewRenderer.this;
                    LockscreenSmartspaceController lockscreenSmartspaceController = keyguardPreviewRenderer2.lockscreenSmartspaceController;
                    if (lockscreenSmartspaceController.isEnabled() && lockscreenSmartspaceController.isDateWeatherDecoupled()) {
                        keyguardPreviewRenderer2.smartSpaceView = lockscreenSmartspaceController.buildAndConnectDateView(frameLayout);
                        KeyguardPreviewSmartspaceViewModel.Companion companion2 = KeyguardPreviewSmartspaceViewModel.Companion;
                        Context context = keyguardPreviewRenderer2.context;
                        Resources resources = context.getResources();
                        companion2.getClass();
                        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_clock_top_margin) + resources.getDimensionPixelSize(R.dimen.keyguard_smartspace_top_offset) + resources.getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard);
                        Resources resources2 = context.getResources();
                        int dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.below_clock_padding_start) + resources2.getDimensionPixelSize(R.dimen.clock_padding_start);
                        int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.below_clock_padding_end);
                        View view = keyguardPreviewRenderer2.smartSpaceView;
                        if (view != null) {
                            view.setPaddingRelative(dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize3, 0);
                            view.setClickable(false);
                            view.setVisibility(4);
                            frameLayout.addView(view, new FrameLayout.LayoutParams(-1, -2));
                        }
                        View view2 = keyguardPreviewRenderer2.smartSpaceView;
                        if (view2 != null) {
                            view2.setAlpha(keyguardPreviewRenderer2.shouldHighlightSelectedAffordance ? 0.3f : 1.0f);
                        }
                    }
                    KeyguardPreviewRenderer keyguardPreviewRenderer3 = KeyguardPreviewRenderer.this;
                    View view3 = keyguardPreviewRenderer3.smartSpaceView;
                    if (view3 != null) {
                        KeyguardPreviewSmartspaceViewBinder.bind(view3, keyguardPreviewRenderer3.smartspaceViewModel);
                    }
                    KeyguardPreviewRenderer keyguardPreviewRenderer4 = KeyguardPreviewRenderer.this;
                    Rect rect = ((UdfpsOverlayParams) keyguardPreviewRenderer4.udfpsOverlayInteractor.udfpsOverlayParams.getValue()).sensorBounds;
                    if (!Intrinsics.areEqual(rect, new Rect())) {
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(rect.width(), rect.height());
                        layoutParams.setMarginsRelative(rect.left, rect.top, rect.right, rect.bottom);
                        frameLayout.addView(LayoutInflater.from(keyguardPreviewRenderer4.context).inflate(R.layout.udfps_keyguard_preview, (ViewGroup) frameLayout, false), layoutParams);
                    }
                    final KeyguardPreviewRenderer keyguardPreviewRenderer5 = KeyguardPreviewRenderer.this;
                    if (!keyguardPreviewRenderer5.shouldHideClock) {
                        Context context2 = keyguardPreviewRenderer5.context;
                        FrameLayout frameLayout2 = new FrameLayout(context2);
                        frameLayout2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                        keyguardPreviewRenderer5.largeClockHostView = frameLayout2;
                        frameLayout2.setVisibility(4);
                        FrameLayout frameLayout3 = keyguardPreviewRenderer5.largeClockHostView;
                        if (frameLayout3 == null) {
                            frameLayout3 = null;
                        }
                        frameLayout.addView(frameLayout3);
                        Resources resources3 = frameLayout.getResources();
                        FrameLayout frameLayout4 = new FrameLayout(context2);
                        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, resources3.getDimensionPixelSize(R.dimen.small_clock_height));
                        KeyguardPreviewSmartspaceViewModel.Companion.getClass();
                        int identifier = resources3.getIdentifier("status_bar_height", "dimen", "android");
                        layoutParams2.topMargin = resources3.getDimensionPixelSize(R.dimen.small_clock_padding_top) + (identifier > 0 ? resources3.getDimensionPixelSize(identifier) : 0);
                        frameLayout4.setLayoutParams(layoutParams2);
                        frameLayout4.setPaddingRelative(resources3.getDimensionPixelSize(R.dimen.clock_padding_start), 0, 0, 0);
                        frameLayout4.setClipChildren(false);
                        keyguardPreviewRenderer5.smallClockHostView = frameLayout4;
                        frameLayout4.setVisibility(4);
                        FrameLayout frameLayout5 = keyguardPreviewRenderer5.smallClockHostView;
                        if (frameLayout5 == null) {
                            frameLayout5 = null;
                        }
                        frameLayout.addView(frameLayout5);
                        final ?? r2 = new ClockRegistry.ClockChangeListener() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$clockChangeListener$1
                            @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
                            public final void onCurrentClockChanged() {
                                int i = KeyguardPreviewRenderer.$r8$clinit;
                                KeyguardPreviewRenderer.this.onClockChanged();
                            }

                            @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
                            public final void onAvailableClocksChanged() {
                            }
                        };
                        ClockRegistry clockRegistry = keyguardPreviewRenderer5.clockRegistry;
                        clockRegistry.getClass();
                        Assert.isMainThread();
                        ((ArrayList) clockRegistry.clockChangeListeners).add(r2);
                        Set set = keyguardPreviewRenderer5.disposables;
                        set.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$1
                            @Override // kotlinx.coroutines.DisposableHandle
                            public final void dispose() {
                                ClockRegistry clockRegistry2 = KeyguardPreviewRenderer.this.clockRegistry;
                                clockRegistry2.getClass();
                                Assert.isMainThread();
                                ((ArrayList) clockRegistry2.clockChangeListeners).remove(r2);
                            }
                        });
                        keyguardPreviewRenderer5.clockController.registerListeners(frameLayout);
                        set.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$2
                            @Override // kotlinx.coroutines.DisposableHandle
                            public final void dispose() {
                                KeyguardPreviewRenderer.this.clockController.unregisterListeners();
                            }
                        });
                        final ?? r22 = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$receiver$1
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context3, Intent intent) {
                                ClockController clockController = KeyguardPreviewRenderer.this.clockController.clock;
                                if (clockController != null) {
                                    clockController.getSmallClock().getEvents().onTimeTick();
                                    clockController.getLargeClock().getEvents().onTimeTick();
                                }
                            }
                        };
                        BroadcastDispatcher broadcastDispatcher = keyguardPreviewRenderer5.broadcastDispatcher;
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.intent.action.TIME_TICK");
                        intentFilter.addAction("android.intent.action.TIME_SET");
                        Unit unit = Unit.INSTANCE;
                        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r22, intentFilter, null, null, 0, null, 60);
                        set.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$4
                            @Override // kotlinx.coroutines.DisposableHandle
                            public final void dispose() {
                                KeyguardPreviewRenderer.this.broadcastDispatcher.unregisterReceiver(r22);
                            }
                        });
                        final View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$layoutChangeListener$1
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(View view4, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
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
                        set.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$5
                            @Override // kotlinx.coroutines.DisposableHandle
                            public final void dispose() {
                                frameLayout.removeOnLayoutChangeListener(onLayoutChangeListener);
                            }
                        });
                        keyguardPreviewRenderer5.onClockChanged();
                        KeyguardPreviewRenderer keyguardPreviewRenderer6 = KeyguardPreviewRenderer.this;
                        FrameLayout frameLayout6 = keyguardPreviewRenderer6.largeClockHostView;
                        if (frameLayout6 == null) {
                            frameLayout6 = null;
                        }
                        FrameLayout frameLayout7 = keyguardPreviewRenderer6.smallClockHostView;
                        KeyguardPreviewClockViewBinder.bind(frameLayout6, frameLayout7 != null ? frameLayout7 : null, keyguardPreviewRenderer6.clockViewModel);
                    }
                    frameLayout.measure(View.MeasureSpec.makeMeasureSpec(KeyguardPreviewRenderer.this.windowManager.getCurrentWindowMetrics().getBounds().width(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(KeyguardPreviewRenderer.this.windowManager.getCurrentWindowMetrics().getBounds().height(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                    frameLayout.layout(0, 0, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
                    float measuredWidth = KeyguardPreviewRenderer.this.width / frameLayout.getMeasuredWidth();
                    float measuredHeight = KeyguardPreviewRenderer.this.height / frameLayout.getMeasuredHeight();
                    if (measuredWidth > measuredHeight) {
                        measuredWidth = measuredHeight;
                    }
                    frameLayout.setScaleX(measuredWidth);
                    frameLayout.setScaleY(measuredWidth);
                    frameLayout.setPivotX(0.0f);
                    frameLayout.setPivotY(0.0f);
                    float f = 2;
                    frameLayout.setTranslationX((KeyguardPreviewRenderer.this.width - (frameLayout.getWidth() * measuredWidth)) / f);
                    frameLayout.setTranslationY((KeyguardPreviewRenderer.this.height - (measuredWidth * frameLayout.getHeight())) / f);
                    KeyguardPreviewRenderer keyguardPreviewRenderer7 = KeyguardPreviewRenderer.this;
                    if (keyguardPreviewRenderer7.isDestroyed) {
                        return;
                    }
                    keyguardPreviewRenderer7.host.setView(frameLayout, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
                }
            });
            if (iBinder != null) {
                iBinder.linkToDeath(previewLifecycleObserver, 0);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("surface_package", create.host.getSurfacePackage());
            Messenger messenger = new Messenger(new Handler(this.backgroundHandler.getLooper(), previewLifecycleObserver));
            Message obtain = Message.obtain();
            obtain.replyTo = messenger;
            bundle2.putParcelable("callback", obtain);
            return bundle2;
        } catch (Exception e2) {
            e = e2;
            Log.e("KeyguardRemotePreviewManager", "Unable to generate preview", e);
            if (previewLifecycleObserver == null) {
                return null;
            }
            destroyObserver(previewLifecycleObserver);
            return null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
}
