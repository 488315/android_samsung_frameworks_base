package com.android.systemui.keyguard.ui.preview;

import android.app.WallpaperColors;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Display;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.window.InputTransferToken;
import com.android.keyguard.ClockEventController;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardPreviewRenderer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ClockEventController clockController;
    public final ClockRegistry clockRegistry;
    public final KeyguardPreviewClockViewModel clockViewModel;
    public final Context context;
    public final DefaultShortcutsSection defaultShortcutsSection;
    public final Display display;
    public final DisposableHandles disposables;
    public final int height;
    public SurfaceControlViewHost host;
    public final IBinder hostToken;
    public final Pair id;
    public final KeyguardIndicationController indicationController;
    public boolean isDestroyed;
    public final KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder;
    public final LockscreenSmartspaceController lockscreenSmartspaceController;
    public final Handler mainHandler;
    public final KeyguardQuickAffordancesCombinedViewModel quickAffordancesCombinedViewModel;
    public final SecureSettings secureSettings;
    public final ShadeModeInteractor shadeModeInteractor;
    public final Set shortcutsBindings;
    public final boolean shouldHideClock;
    public final boolean shouldHighlightSelectedAffordance;
    public View smartSpaceView;
    public final KeyguardPreviewSmartspaceViewModel smartspaceViewModel;
    public Integer themeStyle;
    public final UdfpsOverlayInteractor udfpsOverlayInteractor;
    public final WallpaperColors wallpaperColors;
    public final int width;
    public final WindowManager windowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ DisplayManager $displayManager;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DisplayManager displayManager, Continuation continuation) {
            super(2, continuation);
            this.$displayManager = displayManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardPreviewRenderer.this.new AnonymousClass1(this.$displayManager, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            KeyguardPreviewRenderer.this.host = new SurfaceControlViewHost(KeyguardPreviewRenderer.this.context, this.$displayManager.getDisplay(0), KeyguardPreviewRenderer.this.hostToken == null ? null : new InputTransferToken(KeyguardPreviewRenderer.this.hostToken), "KeyguardPreviewRenderer");
            final KeyguardPreviewRenderer keyguardPreviewRenderer = KeyguardPreviewRenderer.this;
            keyguardPreviewRenderer.disposables.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.1.1
                @Override // kotlinx.coroutines.DisposableHandle
                public final void dispose() {
                    KeyguardPreviewRenderer.this.host.release();
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardPreviewRenderer(Context context, CoroutineDispatcher coroutineDispatcher, Handler handler, CoroutineDispatcher coroutineDispatcher2, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, DisplayManager displayManager, WindowManager windowManager, ClockEventController clockEventController, ClockRegistry clockRegistry, BroadcastDispatcher broadcastDispatcher, LockscreenSmartspaceController lockscreenSmartspaceController, UdfpsOverlayInteractor udfpsOverlayInteractor, KeyguardIndicationController keyguardIndicationController, Bundle bundle, ShadeModeInteractor shadeModeInteractor, SecureSettings secureSettings, DefaultShortcutsSection defaultShortcutsSection, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, WallpaperFocalAreaInteractor wallpaperFocalAreaInteractor) {
        this.context = context;
        this.mainHandler = handler;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.clockViewModel = keyguardPreviewClockViewModel;
        this.smartspaceViewModel = keyguardPreviewSmartspaceViewModel;
        this.quickAffordancesCombinedViewModel = keyguardQuickAffordancesCombinedViewModel;
        this.windowManager = windowManager;
        this.clockController = clockEventController;
        this.clockRegistry = clockRegistry;
        this.broadcastDispatcher = broadcastDispatcher;
        this.lockscreenSmartspaceController = lockscreenSmartspaceController;
        this.udfpsOverlayInteractor = udfpsOverlayInteractor;
        this.indicationController = keyguardIndicationController;
        this.shadeModeInteractor = shadeModeInteractor;
        this.secureSettings = secureSettings;
        this.defaultShortcutsSection = defaultShortcutsSection;
        this.keyguardQuickAffordanceViewBinder = keyguardQuickAffordanceViewBinder;
        IBinder binder = bundle.getBinder("host_token");
        this.hostToken = binder;
        this.width = bundle.getInt("width");
        this.height = bundle.getInt("height");
        boolean z = bundle.getBoolean("highlight_quick_affordances", false);
        this.shouldHighlightSelectedAffordance = z;
        int i = bundle.getInt("display_id", 0);
        this.display = displayManager.getDisplay(i);
        this.id = new Pair(binder, Integer.valueOf(i));
        this.shouldHideClock = bundle.getBoolean("hide_clock", false);
        this.wallpaperColors = (WallpaperColors) bundle.getParcelable("wallpaper_colors");
        this.disposables = new DisposableHandles();
        this.shortcutsBindings = new LinkedHashSet();
        WeatherData placeholderWeatherData = WeatherData.Companion.getPlaceholderWeatherData();
        if (clockEventController.weatherData == null) {
            clockEventController.weatherData = placeholderWeatherData;
            ClockController clockController = clockEventController.clock;
            if (clockController != null) {
                clockController.getEvents().onWeatherDataChanged(placeholderWeatherData);
            }
        }
        String string = bundle.getString("initially_selected_slot_id");
        keyguardQuickAffordancesCombinedViewModel.enablePreviewMode(string == null ? "bottom_start" : string, z);
        keyguardPreviewClockViewModel.shouldHighlightSelectedAffordance = z;
        BuildersKt.runBlocking(coroutineDispatcher, new AnonymousClass1(displayManager, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateClockAppearance(com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer r6, com.android.systemui.plugins.clocks.ClockController r7, android.content.res.Resources r8, kotlin.coroutines.Continuation r9) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.access$updateClockAppearance(com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer, com.android.systemui.plugins.clocks.ClockController, android.content.res.Resources, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object fetchThemeStyleFromSetting(kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r6 = this;
            java.lang.String r0 = "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES."
            java.lang.String r1 = "KeyguardPreviewRenderer"
            boolean r2 = r7 instanceof com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1
            if (r2 == 0) goto L17
            r2 = r7
            com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1 r2 = (com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L17
            int r3 = r3 - r4
            r2.label = r3
            goto L1c
        L17:
            com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1 r2 = new com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1
            r2.<init>(r6, r7)
        L1c:
            java.lang.Object r7 = r2.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r4 = r2.label
            r5 = 1
            if (r4 == 0) goto L33
            if (r4 != r5) goto L2b
            kotlin.ResultKt.throwOnFailure(r7)
            goto L47
        L2b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$overlayPackageJson$1 r7 = new com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$overlayPackageJson$1
            r4 = 0
            r7.<init>(r6, r4)
            r2.label = r5
            kotlinx.coroutines.CoroutineDispatcher r6 = r6.backgroundDispatcher
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r2)
            if (r7 != r3) goto L47
            return r3
        L47:
            java.lang.String r7 = (java.lang.String) r7
            if (r7 == 0) goto L6d
            int r6 = r7.length()
            if (r6 != 0) goto L52
            goto L6d
        L52:
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            r6.<init>(r7)     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            java.lang.String r7 = "android.theme.customization.theme_style"
            java.lang.String r6 = r6.getString(r7)     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            int r5 = com.android.systemui.monet.Style.valueOf(r6)     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            goto L6d
        L62:
            r6 = move-exception
            goto L66
        L64:
            r6 = move-exception
            goto L6a
        L66:
            android.util.Log.i(r1, r0, r6)
            goto L6d
        L6a:
            android.util.Log.i(r1, r0, r6)
        L6d:
            java.lang.Integer r6 = new java.lang.Integer
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.fetchThemeStyleFromSetting(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final boolean getPreviewShadeLayoutWide(Display display) {
        return display.getDisplayId() == 0 ? ((Boolean) ((ShadeModeInteractorImpl) this.shadeModeInteractor).isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() : Intrinsics.areEqual(display.getName(), "Inner Display");
    }
}
