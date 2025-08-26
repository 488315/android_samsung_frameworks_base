package com.android.systemui.keyguard.ui.preview;

import android.app.WallpaperColors;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.window.InputTransferToken;
import com.android.keyguard.ClockEventController;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceViewBinder;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.plugins.clocks.ThemeConfig;
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
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import org.json.JSONException;
import org.json.JSONObject;

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
                    keyguardPreviewRenderer.host.release();
                }
            });
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1, reason: invalid class name and case insensitive filesystem */
    final class C09171 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09171(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            KeyguardPreviewRenderer keyguardPreviewRenderer = KeyguardPreviewRenderer.this;
            int i = KeyguardPreviewRenderer.$r8$clinit;
            return keyguardPreviewRenderer.fetchThemeStyleFromSetting(this);
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardPreviewRenderer(Context context, CoroutineDispatcher coroutineDispatcher, Handler handler, CoroutineDispatcher coroutineDispatcher2, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, DisplayManager displayManager, WindowManager windowManager, ClockEventController clockEventController, ClockRegistry clockRegistry, BroadcastDispatcher broadcastDispatcher, LockscreenSmartspaceController lockscreenSmartspaceController, UdfpsOverlayInteractor udfpsOverlayInteractor, KeyguardIndicationController keyguardIndicationController, Bundle bundle, ShadeModeInteractor shadeModeInteractor, SecureSettings secureSettings, DefaultShortcutsSection defaultShortcutsSection, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, WallpaperFocalAreaInteractor wallpaperFocalAreaInteractor) throws Throwable {
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

    /* JADX WARN: Removed duplicated region for block: B:30:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$updateClockAppearance(KeyguardPreviewRenderer keyguardPreviewRenderer, ClockController clockController, Resources resources, Continuation continuation) throws Throwable {
        KeyguardPreviewRenderer$updateClockAppearance$1 keyguardPreviewRenderer$updateClockAppearance$1;
        WallpaperColors wallpaperColors;
        Object objFetchThemeStyleFromSetting;
        int iIntValue;
        boolean z;
        keyguardPreviewRenderer.getClass();
        if (continuation instanceof KeyguardPreviewRenderer$updateClockAppearance$1) {
            keyguardPreviewRenderer$updateClockAppearance$1 = (KeyguardPreviewRenderer$updateClockAppearance$1) continuation;
            int i = keyguardPreviewRenderer$updateClockAppearance$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                keyguardPreviewRenderer$updateClockAppearance$1.label = i - Integer.MIN_VALUE;
            } else {
                keyguardPreviewRenderer$updateClockAppearance$1 = new KeyguardPreviewRenderer$updateClockAppearance$1(keyguardPreviewRenderer, continuation);
            }
        }
        Object obj = keyguardPreviewRenderer$updateClockAppearance$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = keyguardPreviewRenderer$updateClockAppearance$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            wallpaperColors = keyguardPreviewRenderer.wallpaperColors;
            ClockSettings clockSettings = keyguardPreviewRenderer.clockRegistry.settings;
            if ((clockSettings != null ? clockSettings.getSeedColor() : null) == null && wallpaperColors != null) {
                Integer num = keyguardPreviewRenderer.themeStyle;
                if (num != null) {
                    iIntValue = num.intValue();
                    ColorScheme colorScheme = new ColorScheme(wallpaperColors, false, iIntValue);
                    int s100 = colorScheme.mAccent1.getS100();
                    int iIntValue2 = ((Integer) colorScheme.mAccent2.allShades.get(8)).intValue();
                    z = (wallpaperColors.getColorHints() & 1) == 0;
                    if (!z) {
                        s100 = iIntValue2;
                    }
                    ThemeConfig themeConfig = new ThemeConfig(z, new Integer(s100));
                    clockController.getSmallClock().getEvents().onThemeChanged(themeConfig);
                    clockController.getLargeClock().getEvents().onThemeChanged(themeConfig);
                } else {
                    keyguardPreviewRenderer$updateClockAppearance$1.L$0 = keyguardPreviewRenderer;
                    keyguardPreviewRenderer$updateClockAppearance$1.L$1 = clockController;
                    keyguardPreviewRenderer$updateClockAppearance$1.L$2 = resources;
                    keyguardPreviewRenderer$updateClockAppearance$1.L$3 = wallpaperColors;
                    keyguardPreviewRenderer$updateClockAppearance$1.label = 1;
                    objFetchThemeStyleFromSetting = keyguardPreviewRenderer.fetchThemeStyleFromSetting(keyguardPreviewRenderer$updateClockAppearance$1);
                    if (objFetchThemeStyleFromSetting == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
            keyguardPreviewRenderer.clockController.setClock(clockController);
            clockController.getLargeClock().getEvents().onFontSettingChanged(resources.getDimensionPixelSize(R$dimen.large_clock_text_size));
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        WallpaperColors wallpaperColors2 = (WallpaperColors) keyguardPreviewRenderer$updateClockAppearance$1.L$3;
        resources = (Resources) keyguardPreviewRenderer$updateClockAppearance$1.L$2;
        clockController = (ClockController) keyguardPreviewRenderer$updateClockAppearance$1.L$1;
        KeyguardPreviewRenderer keyguardPreviewRenderer2 = (KeyguardPreviewRenderer) keyguardPreviewRenderer$updateClockAppearance$1.L$0;
        ResultKt.throwOnFailure(obj);
        wallpaperColors = wallpaperColors2;
        keyguardPreviewRenderer = keyguardPreviewRenderer2;
        objFetchThemeStyleFromSetting = obj;
        Number number = (Number) objFetchThemeStyleFromSetting;
        keyguardPreviewRenderer.themeStyle = new Integer(number.intValue());
        iIntValue = number.intValue();
        ColorScheme colorScheme2 = new ColorScheme(wallpaperColors, false, iIntValue);
        int s1002 = colorScheme2.mAccent1.getS100();
        int iIntValue22 = ((Integer) colorScheme2.mAccent2.allShades.get(8)).intValue();
        if ((wallpaperColors.getColorHints() & 1) == 0) {
        }
        if (!z) {
        }
        ThemeConfig themeConfig2 = new ThemeConfig(z, new Integer(s1002));
        clockController.getSmallClock().getEvents().onThemeChanged(themeConfig2);
        clockController.getLargeClock().getEvents().onThemeChanged(themeConfig2);
        keyguardPreviewRenderer.clockController.setClock(clockController);
        clockController.getLargeClock().getEvents().onFontSettingChanged(resources.getDimensionPixelSize(R$dimen.large_clock_text_size));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object fetchThemeStyleFromSetting(ContinuationImpl continuationImpl) throws Throwable {
        C09171 c09171;
        if (continuationImpl instanceof C09171) {
            c09171 = (C09171) continuationImpl;
            int i = c09171.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09171.label = i - Integer.MIN_VALUE;
            } else {
                c09171 = new C09171(continuationImpl);
            }
        }
        Object objWithContext = c09171.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09171.label;
        int iValueOf = 1;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            KeyguardPreviewRenderer$fetchThemeStyleFromSetting$overlayPackageJson$1 keyguardPreviewRenderer$fetchThemeStyleFromSetting$overlayPackageJson$1 = new KeyguardPreviewRenderer$fetchThemeStyleFromSetting$overlayPackageJson$1(this, null);
            c09171.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, keyguardPreviewRenderer$fetchThemeStyleFromSetting$overlayPackageJson$1, c09171);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext);
        }
        String str = (String) objWithContext;
        if (str != null && str.length() != 0) {
            try {
                iValueOf = Style.valueOf(new JSONObject(str).getString("android.theme.customization.theme_style"));
            } catch (IllegalArgumentException e) {
                Log.i("KeyguardPreviewRenderer", "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES.", e);
            } catch (JSONException e2) {
                Log.i("KeyguardPreviewRenderer", "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES.", e2);
            }
        }
        return new Integer(iValueOf);
    }

    public final boolean getPreviewShadeLayoutWide(Display display) {
        return display.getDisplayId() == 0 ? ((Boolean) ((ShadeModeInteractorImpl) this.shadeModeInteractor).isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() : Intrinsics.areEqual(display.getName(), "Inner Display");
    }
}
