package com.android.systemui.keyguard.p009ui.preview;

import android.app.WallpaperColors;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.keyguard.ClockEventController;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.monet.TonalPalette;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockEvents;
import com.android.systemui.plugins.ClockFaceEvents;
import com.android.systemui.plugins.ClockSettings;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPreviewRenderer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final KeyguardBottomAreaViewModel bottomAreaViewModel;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ClockEventController clockController;
    public final ClockRegistry clockRegistry;
    public final KeyguardPreviewClockViewModel clockViewModel;
    public final Context context;
    public final Set disposables;
    public final int height;
    public SurfaceControlViewHost host;
    public final IBinder hostToken;
    public boolean isDestroyed;
    public FrameLayout largeClockHostView;
    public final LockscreenSmartspaceController lockscreenSmartspaceController;
    public final Handler mainHandler;
    public final boolean shouldHideClock;
    public final boolean shouldHighlightSelectedAffordance;
    public FrameLayout smallClockHostView;
    public View smartSpaceView;
    public final KeyguardPreviewSmartspaceViewModel smartspaceViewModel;
    public final UdfpsOverlayInteractor udfpsOverlayInteractor;
    public final WallpaperColors wallpaperColors;
    public final int width;
    public final WindowManager windowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$1", m277f = "KeyguardPreviewRenderer.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$1 */
    public static final class C17471 extends SuspendLambda implements Function2 {
        final /* synthetic */ Bundle $bundle;
        final /* synthetic */ DisplayManager $displayManager;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C17471(DisplayManager displayManager, Bundle bundle, Continuation<? super C17471> continuation) {
            super(2, continuation);
            this.$displayManager = displayManager;
            this.$bundle = bundle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardPreviewRenderer.this.new C17471(this.$displayManager, this.$bundle, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C17471) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            KeyguardPreviewRenderer.this.host = new SurfaceControlViewHost(KeyguardPreviewRenderer.this.context, this.$displayManager.getDisplay(this.$bundle.getInt("display_id")), KeyguardPreviewRenderer.this.hostToken, "KeyguardPreviewRenderer");
            final KeyguardPreviewRenderer keyguardPreviewRenderer = KeyguardPreviewRenderer.this;
            return Boolean.valueOf(keyguardPreviewRenderer.disposables.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.1.1
                @Override // kotlinx.coroutines.DisposableHandle
                public final void dispose() {
                    KeyguardPreviewRenderer.this.host.release();
                }
            }));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public KeyguardPreviewRenderer(Context context, CoroutineDispatcher coroutineDispatcher, Handler handler, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, DisplayManager displayManager, WindowManager windowManager, ClockEventController clockEventController, ClockRegistry clockRegistry, BroadcastDispatcher broadcastDispatcher, LockscreenSmartspaceController lockscreenSmartspaceController, UdfpsOverlayInteractor udfpsOverlayInteractor, Bundle bundle) {
        this.context = context;
        this.mainHandler = handler;
        this.clockViewModel = keyguardPreviewClockViewModel;
        this.smartspaceViewModel = keyguardPreviewSmartspaceViewModel;
        this.bottomAreaViewModel = keyguardBottomAreaViewModel;
        this.windowManager = windowManager;
        this.clockController = clockEventController;
        this.clockRegistry = clockRegistry;
        this.broadcastDispatcher = broadcastDispatcher;
        this.lockscreenSmartspaceController = lockscreenSmartspaceController;
        this.udfpsOverlayInteractor = udfpsOverlayInteractor;
        this.hostToken = bundle.getBinder("host_token");
        this.width = bundle.getInt("width");
        this.height = bundle.getInt("height");
        boolean z = bundle.getBoolean("highlight_quick_affordances", false);
        this.shouldHighlightSelectedAffordance = z;
        this.shouldHideClock = bundle.getBoolean("hide_clock", false);
        this.wallpaperColors = (WallpaperColors) bundle.getParcelable("wallpaper_colors");
        this.disposables = new LinkedHashSet();
        String string = bundle.getString("initially_selected_slot_id");
        keyguardBottomAreaViewModel.previewMode.setValue(new KeyguardBottomAreaViewModel.PreviewMode(true, z));
        keyguardBottomAreaViewModel.selectedPreviewSlotId.setValue(string == null ? "bottom_start" : string);
        BuildersKt.runBlocking(coroutineDispatcher, new C17471(displayManager, bundle, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x005c, code lost:
    
        if ((r0.getColorHints() & 1) == 0) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onClockChanged() {
        TonalPalette tonalPalette;
        TonalPalette tonalPalette2;
        ClockRegistry clockRegistry = this.clockRegistry;
        ClockController createCurrentClock = clockRegistry.createCurrentClock();
        this.clockController.setClock(createCurrentClock);
        ClockSettings clockSettings = clockRegistry.settings;
        if ((clockSettings != null ? clockSettings.getSeedColor() : null) == null) {
            WallpaperColors wallpaperColors = this.wallpaperColors;
            ColorScheme colorScheme = wallpaperColors != null ? new ColorScheme(wallpaperColors, false, (Style) null, 4, (DefaultConstructorMarker) null) : null;
            Integer valueOf = (colorScheme == null || (tonalPalette2 = colorScheme.accent1) == null) ? null : Integer.valueOf(tonalPalette2.getS100());
            Integer valueOf2 = (colorScheme == null || (tonalPalette = colorScheme.accent2) == null) ? null : Integer.valueOf(((Number) ((ArrayList) tonalPalette.allShades).get(7)).intValue());
            boolean z = wallpaperColors != null;
            ClockEvents events = createCurrentClock.getEvents();
            if (!z) {
                valueOf = valueOf2;
            }
            events.onSeedColorChanged(valueOf);
        }
        ClockFaceEvents events2 = createCurrentClock.getLargeClock().getEvents();
        FrameLayout frameLayout = this.largeClockHostView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        events2.onTargetRegionChanged(KeyguardClockSwitch.getLargeClockRegion(frameLayout));
        boolean z2 = this.shouldHighlightSelectedAffordance;
        if (z2) {
            createCurrentClock.getLargeClock().getView().setAlpha(0.3f);
        }
        FrameLayout frameLayout2 = this.largeClockHostView;
        if (frameLayout2 == null) {
            frameLayout2 = null;
        }
        frameLayout2.removeAllViews();
        FrameLayout frameLayout3 = this.largeClockHostView;
        if (frameLayout3 == null) {
            frameLayout3 = null;
        }
        frameLayout3.addView(createCurrentClock.getLargeClock().getView());
        ClockFaceEvents events3 = createCurrentClock.getSmallClock().getEvents();
        FrameLayout frameLayout4 = this.smallClockHostView;
        if (frameLayout4 == null) {
            frameLayout4 = null;
        }
        events3.onTargetRegionChanged(KeyguardClockSwitch.getSmallClockRegion(frameLayout4));
        if (z2) {
            createCurrentClock.getSmallClock().getView().setAlpha(0.3f);
        }
        FrameLayout frameLayout5 = this.smallClockHostView;
        if (frameLayout5 == null) {
            frameLayout5 = null;
        }
        frameLayout5.removeAllViews();
        FrameLayout frameLayout6 = this.smallClockHostView;
        (frameLayout6 != null ? frameLayout6 : null).addView(createCurrentClock.getSmallClock().getView());
    }
}
