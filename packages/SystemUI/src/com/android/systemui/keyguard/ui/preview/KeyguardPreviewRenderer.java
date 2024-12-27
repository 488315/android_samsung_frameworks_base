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
import android.widget.FrameLayout;
import android.window.InputTransferToken;
import com.android.keyguard.ClockEventController;
import com.android.systemui.Flags;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.communal.ui.viewmodel.CommunalTutorialIndicatorViewModel;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.monet.Style;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.android.systemui.util.settings.SecureSettings;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardPreviewRenderer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher backgroundDispatcher;
    public final KeyguardBottomAreaViewModel bottomAreaViewModel;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ChipbarCoordinator chipbarCoordinator;
    public final ClockEventController clockController;
    public final ClockRegistry clockRegistry;
    public final KeyguardPreviewClockViewModel clockViewModel;
    public final CommunalTutorialIndicatorViewModel communalTutorialViewModel;
    public final ConfigurationState configuration;
    public final Context context;
    public final ContextScope coroutineScope;
    public final Display display;
    public final DisposableHandles disposables;
    public final FalsingManager falsingManager;
    public final int height;
    public SurfaceControlViewHost host;
    public final IBinder hostToken;
    public final Pair id;
    public final KeyguardIndicationController indicationController;
    public boolean isDestroyed;
    public final KeyguardBlueprintViewModel keyguardBlueprintViewModel;
    public final KeyguardClockInteractor keyguardClockInteractor;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final KeyguardRootViewModel keyguardRootViewModel;
    public FrameLayout largeClockHostView;
    public final LockscreenSmartspaceController lockscreenSmartspaceController;
    public final Handler mainHandler;
    public final OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel;
    public final ScreenOffAnimationController screenOffAnimationController;
    public final SecureSettings secureSettings;
    public final ShadeInteractor shadeInteractor;
    public final Set shortcutsBindings;
    public final boolean shouldHideClock;
    public final boolean shouldHighlightSelectedAffordance;
    public FrameLayout smallClockHostView;
    public View smartSpaceView;
    public final KeyguardPreviewSmartspaceViewModel smartspaceViewModel;
    public Style themeStyle;
    public final UdfpsOverlayInteractor udfpsOverlayInteractor;
    public final WallpaperColors wallpaperColors;
    public final int width;
    public final WindowManager windowManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ DisplayManager $displayManager;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(DisplayManager displayManager, Continuation continuation) {
            super(2, continuation);
            this.$displayManager = displayManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardPreviewRenderer.this.new AnonymousClass2(this.$displayManager, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            keyguardPreviewRenderer.disposables.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.2.1
                @Override // kotlinx.coroutines.DisposableHandle
                public final void dispose() {
                    KeyguardPreviewRenderer.this.host.release();
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public KeyguardPreviewRenderer(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Handler handler, CoroutineDispatcher coroutineDispatcher2, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, DisplayManager displayManager, WindowManager windowManager, ConfigurationState configurationState, ClockEventController clockEventController, ClockRegistry clockRegistry, BroadcastDispatcher broadcastDispatcher, LockscreenSmartspaceController lockscreenSmartspaceController, UdfpsOverlayInteractor udfpsOverlayInteractor, FalsingManager falsingManager, VibratorHelper vibratorHelper, KeyguardIndicationController keyguardIndicationController, KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, Bundle bundle, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, ScreenOffAnimationController screenOffAnimationController, ShadeInteractor shadeInteractor, SecureSettings secureSettings, CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel, DefaultShortcutsSection defaultShortcutsSection, KeyguardClockInteractor keyguardClockInteractor, KeyguardClockViewModel keyguardClockViewModel) {
        this.context = context;
        this.mainHandler = handler;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.clockViewModel = keyguardPreviewClockViewModel;
        this.smartspaceViewModel = keyguardPreviewSmartspaceViewModel;
        this.bottomAreaViewModel = keyguardBottomAreaViewModel;
        this.windowManager = windowManager;
        this.configuration = configurationState;
        this.clockController = clockEventController;
        this.clockRegistry = clockRegistry;
        this.broadcastDispatcher = broadcastDispatcher;
        this.lockscreenSmartspaceController = lockscreenSmartspaceController;
        this.udfpsOverlayInteractor = udfpsOverlayInteractor;
        this.falsingManager = falsingManager;
        this.keyguardRootViewModel = keyguardRootViewModel;
        this.keyguardBlueprintViewModel = keyguardBlueprintViewModel;
        this.occludingAppDeviceEntryMessageViewModel = occludingAppDeviceEntryMessageViewModel;
        this.chipbarCoordinator = chipbarCoordinator;
        this.screenOffAnimationController = screenOffAnimationController;
        this.shadeInteractor = shadeInteractor;
        this.secureSettings = secureSettings;
        this.communalTutorialViewModel = communalTutorialIndicatorViewModel;
        this.keyguardClockInteractor = keyguardClockInteractor;
        this.keyguardClockViewModel = keyguardClockViewModel;
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
        DisposableHandles disposableHandles = new DisposableHandles();
        this.disposables = disposableHandles;
        new LinkedHashSet();
        this.coroutineScope = CoroutineScopeKt.CoroutineScope(coroutineScope.getCoroutineContext().plus(JobKt.Job$default()));
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.1
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                CoroutineScopeKt.cancel(KeyguardPreviewRenderer.this.coroutineScope, null);
            }
        });
        Flags.keyguardBottomAreaRefactor();
        String string = bundle.getString("initially_selected_slot_id");
        keyguardBottomAreaViewModel.previewMode.updateState(null, new KeyguardBottomAreaViewModel.PreviewMode(true, z));
        keyguardBottomAreaViewModel.selectedPreviewSlotId.updateState(null, string == null ? "bottom_start" : string);
        Flags.migrateClocksToBlueprint();
        BuildersKt.runBlocking(coroutineDispatcher, new AnonymousClass2(displayManager, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateClockAppearance(com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer r5, com.android.systemui.plugins.clocks.ClockController r6, kotlin.coroutines.Continuation r7) {
        /*
            r5.getClass()
            boolean r0 = r7 instanceof com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$updateClockAppearance$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$updateClockAppearance$1 r0 = (com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$updateClockAppearance$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$updateClockAppearance$1 r0 = new com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$updateClockAppearance$1
            r0.<init>(r5, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L42
            if (r2 != r3) goto L3a
            java.lang.Object r5 = r0.L$2
            android.app.WallpaperColors r5 = (android.app.WallpaperColors) r5
            java.lang.Object r6 = r0.L$1
            com.android.systemui.plugins.clocks.ClockController r6 = (com.android.systemui.plugins.clocks.ClockController) r6
            java.lang.Object r0 = r0.L$0
            com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer r0 = (com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer) r0
            kotlin.ResultKt.throwOnFailure(r7)
            r4 = r7
            r7 = r5
            r5 = r0
            r0 = r4
            goto L72
        L3a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L42:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.Flags.migrateClocksToBlueprint()
            com.android.keyguard.ClockEventController r7 = r5.clockController
            r7.setClock(r6)
            android.app.WallpaperColors r7 = r5.wallpaperColors
            com.android.systemui.shared.clocks.ClockRegistry r2 = r5.clockRegistry
            com.android.systemui.plugins.clocks.ClockSettings r2 = r2.settings
            if (r2 == 0) goto L5a
            java.lang.Integer r2 = r2.getSeedColor()
            goto L5b
        L5a:
            r2 = 0
        L5b:
            if (r2 != 0) goto Lac
            if (r7 == 0) goto Lac
            com.android.systemui.monet.Style r2 = r5.themeStyle
            if (r2 != 0) goto L77
            r0.L$0 = r5
            r0.L$1 = r6
            r0.L$2 = r7
            r0.label = r3
            java.lang.Object r0 = r5.fetchThemeStyleFromSetting(r0)
            if (r0 != r1) goto L72
            goto Lb1
        L72:
            r2 = r0
            com.android.systemui.monet.Style r2 = (com.android.systemui.monet.Style) r2
            r5.themeStyle = r2
        L77:
            com.android.systemui.monet.ColorScheme r5 = new com.android.systemui.monet.ColorScheme
            r0 = 0
            r5.<init>(r7, r0, r2)
            com.android.systemui.monet.TonalPalette r1 = r5.mAccent1
            int r1 = r1.getS100()
            com.android.systemui.monet.TonalPalette r5 = r5.mAccent2
            java.util.List r5 = r5.allShades
            r2 = 8
            java.lang.Object r5 = r5.get(r2)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            int r7 = r7.getColorHints()
            r7 = r7 & r3
            if (r7 != 0) goto L9b
            goto L9c
        L9b:
            r3 = r0
        L9c:
            com.android.systemui.plugins.clocks.ClockEvents r6 = r6.getEvents()
            if (r3 == 0) goto La3
            goto La4
        La3:
            r1 = r5
        La4:
            java.lang.Integer r5 = new java.lang.Integer
            r5.<init>(r1)
            r6.onSeedColorChanged(r5)
        Lac:
            com.android.systemui.Flags.migrateClocksToBlueprint()
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        Lb1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.access$updateClockAppearance(com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer, com.android.systemui.plugins.clocks.ClockController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object fetchThemeStyleFromSetting(kotlin.coroutines.Continuation r7) {
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
            if (r7 == 0) goto L72
            int r6 = r7.length()
            if (r6 != 0) goto L52
            goto L72
        L52:
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            r6.<init>(r7)     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            java.lang.String r7 = "android.theme.customization.theme_style"
            java.lang.String r6 = r6.getString(r7)     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            com.android.systemui.monet.Style r6 = com.android.systemui.monet.Style.valueOf(r6)     // Catch: java.lang.IllegalArgumentException -> L62 org.json.JSONException -> L64
            goto L74
        L62:
            r6 = move-exception
            goto L66
        L64:
            r6 = move-exception
            goto L6c
        L66:
            android.util.Log.i(r1, r0, r6)
            com.android.systemui.monet.Style r6 = com.android.systemui.monet.Style.TONAL_SPOT
            goto L74
        L6c:
            android.util.Log.i(r1, r0, r6)
            com.android.systemui.monet.Style r6 = com.android.systemui.monet.Style.TONAL_SPOT
            goto L74
        L72:
            com.android.systemui.monet.Style r6 = com.android.systemui.monet.Style.TONAL_SPOT
        L74:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.fetchThemeStyleFromSetting(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
