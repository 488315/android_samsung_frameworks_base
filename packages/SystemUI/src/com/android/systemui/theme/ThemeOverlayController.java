package com.android.systemui.theme;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.FabricatedOverlay;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.CustomDynamicColors;
import com.android.systemui.monet.DynamicColors;
import com.android.systemui.monet.Style;
import com.android.systemui.monet.TonalPalette;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.SecureSettings;
import com.google.ux.material.libmonet.dynamiccolor.ContrastCurve;
import com.google.ux.material.libmonet.dynamiccolor.DynamicColor;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda0;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda1;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda2;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda3;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda5;
import com.google.ux.material.libmonet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda7;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.FlowKt;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ThemeOverlayController implements CoreStartable, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityManager mActivityManager;
    public final Executor mBgExecutor;
    public final Handler mBgHandler;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass4 mBroadcastReceiver;
    protected ColorScheme mColorScheme;
    public final Context mContext;
    public ColorScheme mDarkColorScheme;
    public boolean mDeferredThemeEvaluation;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public FabricatedOverlay mDynamicOverlay;
    public final boolean mIsMonetEnabled;
    public ColorScheme mLightColorScheme;
    public final Executor mMainExecutor;
    public boolean mNeedsOverlayCreation;
    public FabricatedOverlay mNeutralOverlay;
    public final Resources mResources;
    public FabricatedOverlay mSecondaryOverlay;
    public final SecureSettings mSecureSettings;
    public boolean mSkipSettingChange;
    public final SystemPropertiesHelper mSystemPropertiesHelper;
    public final ThemeOverlayApplier mThemeManager;
    public final UiModeManager mUiModeManager;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final UserTracker.Callback mUserTrackerCallback;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WallpaperManager mWallpaperManager;
    protected final SparseArray<WallpaperColors> mCurrentColors = new SparseArray<>();
    public int mMainWallpaperColor = 0;
    public double mContrast = -1.0d;
    protected int mThemeStyle = 1;
    public boolean mAcceptColorEvents = true;
    public final SparseArray mDeferredWallpaperColors = new SparseArray();
    public final SparseIntArray mDeferredWallpaperColorsFlags = new SparseIntArray();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HardwareDefaultSetting extends Record {
        public final String colorSource;
        public final Color seedColor;
        public final int style;

        public HardwareDefaultSetting(Color color, int i, String str) {
            this.seedColor = color;
            this.style = i;
            this.colorSource = str;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            if (!(obj instanceof HardwareDefaultSetting)) {
                return false;
            }
            HardwareDefaultSetting hardwareDefaultSetting = (HardwareDefaultSetting) obj;
            return this.style == hardwareDefaultSetting.style && Objects.equals(this.seedColor, hardwareDefaultSetting.seedColor) && Objects.equals(this.colorSource, hardwareDefaultSetting.colorSource);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            int i = this.style;
            Color color = this.seedColor;
            String str = this.colorSource;
            int hashCode = Objects.hashCode(color);
            return Objects.hashCode(str) + ((hashCode + (i * 31)) * 31);
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {this.seedColor, Integer.valueOf(this.style), this.colorSource};
            String[] split = "seedColor;style;colorSource".length() == 0 ? new String[0] : "seedColor;style;colorSource".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(HardwareDefaultSetting.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < split.length; i++) {
                sb.append(split[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != split.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.theme.ThemeOverlayController$4] */
    public ThemeOverlayController(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, Executor executor, Executor executor2, ThemeOverlayApplier themeOverlayApplier, SecureSettings secureSettings, WallpaperManager wallpaperManager, UserManager userManager, DeviceProvisionedController deviceProvisionedController, UserTracker userTracker, DumpManager dumpManager, FeatureFlags featureFlags, Resources resources, WakefulnessLifecycle wakefulnessLifecycle, JavaAdapter javaAdapter, KeyguardTransitionInteractor keyguardTransitionInteractor, UiModeManager uiModeManager, ActivityManager activityManager, SystemPropertiesHelper systemPropertiesHelper) {
        new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.theme.ThemeOverlayController.1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                if (((DeviceProvisionedControllerImpl) themeOverlayController.mDeviceProvisionedController).isCurrentUserSetup() && themeOverlayController.mDeferredThemeEvaluation) {
                    Log.i("ThemeOverlayController", "Applying deferred theme");
                    themeOverlayController.mDeferredThemeEvaluation = false;
                    themeOverlayController.reevaluateSystemTheme(true);
                }
            }
        };
        new WallpaperManager.OnColorsChangedListener() { // from class: com.android.systemui.theme.ThemeOverlayController.2
            @Override // android.app.WallpaperManager.OnColorsChangedListener
            public final void onColorsChanged(WallpaperColors wallpaperColors, int i) {
                throw new IllegalStateException("This should never be invoked, all messages should arrive on the overload that has a user id");
            }

            public final void onColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
                WallpaperColors wallpaperColors2 = ThemeOverlayController.this.mCurrentColors.get(i2);
                if (wallpaperColors == null || !wallpaperColors.equals(wallpaperColors2)) {
                    boolean z = i2 == ((UserTrackerImpl) ThemeOverlayController.this.mUserTracker).getUserId();
                    ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                    boolean z2 = themeOverlayController.mWakefulnessLifecycle.mWakefulness != 0;
                    if (z && !themeOverlayController.mAcceptColorEvents && z2) {
                        themeOverlayController.mDeferredWallpaperColors.put(i2, wallpaperColors);
                        ThemeOverlayController.this.mDeferredWallpaperColorsFlags.put(i2, i);
                        Log.i("ThemeOverlayController", "colors received; processing deferred until screen off: " + wallpaperColors + " user: " + i2);
                        return;
                    }
                    if (z && wallpaperColors != null) {
                        themeOverlayController.mAcceptColorEvents = false;
                        themeOverlayController.mDeferredWallpaperColors.put(i2, null);
                        ThemeOverlayController.this.mDeferredWallpaperColorsFlags.put(i2, 0);
                    }
                    ThemeOverlayController themeOverlayController2 = ThemeOverlayController.this;
                    String str = "lock_wallpaper";
                    int userId = ((UserTrackerImpl) themeOverlayController2.mUserTracker).getUserId();
                    boolean z3 = themeOverlayController2.mCurrentColors.get(i2) != null;
                    boolean z4 = ((themeOverlayController2.mWallpaperManager.getWallpaperIdForUser(2, i2) > themeOverlayController2.mWallpaperManager.getWallpaperIdForUser(1, i2) ? 2 : 1) & i) != 0;
                    if (z4) {
                        themeOverlayController2.mCurrentColors.put(i2, wallpaperColors);
                        Log.d("ThemeOverlayController", "got new colors: " + wallpaperColors + " where: " + i);
                    }
                    if (i2 != userId) {
                        StringBuilder sb = new StringBuilder("Colors ");
                        sb.append(wallpaperColors);
                        sb.append(" for user ");
                        sb.append(i2);
                        sb.append(". Not for current user: ");
                        RecyclerView$$ExternalSyntheticOutline0.m(userId, "ThemeOverlayController", sb);
                        return;
                    }
                    DeviceProvisionedController deviceProvisionedController2 = themeOverlayController2.mDeviceProvisionedController;
                    if (deviceProvisionedController2 != null && !((DeviceProvisionedControllerImpl) deviceProvisionedController2).isCurrentUserSetup()) {
                        if (z3) {
                            Log.i("ThemeOverlayController", "Wallpaper color event deferred until setup is finished: " + wallpaperColors);
                            themeOverlayController2.mDeferredThemeEvaluation = true;
                            return;
                        }
                        if (themeOverlayController2.mDeferredThemeEvaluation) {
                            Log.i("ThemeOverlayController", "Wallpaper color event received, but we already were deferring eval: " + wallpaperColors);
                            return;
                        }
                        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("During user setup, but allowing first color event: had? ", " has? ", z3), themeOverlayController2.mCurrentColors.get(i2) != null, "ThemeOverlayController");
                    }
                    SecureSettings secureSettings2 = themeOverlayController2.mSecureSettings;
                    String stringForUser = secureSettings2.getStringForUser("theme_customization_overlay_packages", userId);
                    boolean z5 = i == 3;
                    boolean z6 = i == 1;
                    try {
                        JSONObject jSONObject = stringForUser == null ? new JSONObject() : new JSONObject(stringForUser);
                        String optString = jSONObject.optString("android.theme.customization.color_source");
                        boolean z7 = z5;
                        boolean equals = "preset".equals(optString);
                        boolean z8 = z6 && "lock_wallpaper".equals(optString);
                        if (!equals && !z8 && z4 && !ThemeOverlayController.isSeedColorSet(jSONObject, wallpaperColors)) {
                            themeOverlayController2.mSkipSettingChange = true;
                            if (jSONObject.has("android.theme.customization.accent_color") || jSONObject.has("android.theme.customization.system_palette")) {
                                jSONObject.remove("android.theme.customization.dynamic_color");
                                jSONObject.remove("android.theme.customization.accent_color");
                                jSONObject.remove("android.theme.customization.system_palette");
                                jSONObject.remove("android.theme.customization.color_index");
                            }
                            jSONObject.put("android.theme.customization.color_both", z7 ? "1" : "0");
                            if (i != 2) {
                                str = "home_wallpaper";
                            }
                            jSONObject.put("android.theme.customization.color_source", str);
                            jSONObject.put("_applied_timestamp", System.currentTimeMillis());
                            Log.d("ThemeOverlayController", "Updating theme setting from " + stringForUser + " to " + jSONObject.toString());
                            secureSettings2.putStringForUser("theme_customization_overlay_packages", jSONObject.toString(), -2);
                        }
                    } catch (JSONException e) {
                        Log.i("ThemeOverlayController", "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES.", e);
                    }
                    themeOverlayController2.reevaluateSystemTheme(false);
                }
            }
        };
        this.mUserTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.theme.ThemeOverlayController.3
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                boolean isManagedProfile = themeOverlayController.mUserManager.isManagedProfile(i);
                if (!((DeviceProvisionedControllerImpl) themeOverlayController.mDeviceProvisionedController).isCurrentUserSetup() && isManagedProfile) {
                    AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("User setup not finished when new user event was received. Deferring... Managed profile? ", "ThemeOverlayController", isManagedProfile);
                } else {
                    Log.d("ThemeOverlayController", "Updating overlays for user switch / profile added.");
                    themeOverlayController.reevaluateSystemTheme(true);
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.theme.ThemeOverlayController.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                if (themeOverlayController.mContrast == -1.0d || Settings.System.getInt(themeOverlayController.mContext.getContentResolver(), "wallpapertheme_state", -1) == 1) {
                    return;
                }
                if (!"android.intent.action.PROFILE_ADDED".equals(intent.getAction())) {
                    if ("android.intent.action.WALLPAPER_CHANGED".equals(intent.getAction())) {
                        if (!intent.getBooleanExtra("android.service.wallpaper.extra.FROM_FOREGROUND_APP", false)) {
                            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("Wallpaper changed from background app, keep deferring color events. Accepting: "), ThemeOverlayController.this.mAcceptColorEvents, "ThemeOverlayController");
                            return;
                        } else {
                            ThemeOverlayController.this.mAcceptColorEvents = true;
                            Log.i("ThemeOverlayController", "Wallpaper changed, allowing color events again");
                            return;
                        }
                    }
                    return;
                }
                UserHandle userHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER", UserHandle.class);
                boolean isManagedProfile = ThemeOverlayController.this.mUserManager.isManagedProfile(userHandle.getIdentifier());
                if (!((DeviceProvisionedControllerImpl) ThemeOverlayController.this.mDeviceProvisionedController).isUserSetup(userHandle.getIdentifier()) && isManagedProfile) {
                    Log.i("ThemeOverlayController", "User setup not finished when " + intent.getAction() + " was received. Deferring... Managed profile? " + isManagedProfile);
                    return;
                }
                if (ThemeOverlayController.this.isPrivateProfile(userHandle)) {
                    ThemeOverlayController.this.mDeferredThemeEvaluation = true;
                    Log.i("ThemeOverlayController", "Deferring theme for private profile till user setup is complete");
                } else {
                    Log.d("ThemeOverlayController", "Updating overlays for user switch / profile added.");
                    ThemeOverlayController.this.reevaluateSystemTheme(true);
                }
            }
        };
        this.mContext = context;
        this.mIsMonetEnabled = ((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.MONET);
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserManager = userManager;
        this.mBgExecutor = executor2;
        this.mMainExecutor = executor;
        this.mBgHandler = handler;
        this.mThemeManager = themeOverlayApplier;
        this.mSecureSettings = secureSettings;
        this.mWallpaperManager = wallpaperManager;
        this.mUserTracker = userTracker;
        this.mResources = resources;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mUiModeManager = uiModeManager;
        this.mActivityManager = activityManager;
        this.mSystemPropertiesHelper = systemPropertiesHelper;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "ThemeOverlayController", this);
        final KeyguardState.Companion companion = KeyguardState.Companion;
        Objects.requireNonNull(companion);
        javaAdapter.stateInApp(FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(keyguardTransitionInteractor.finishedKeyguardState, new Function1() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                KeyguardState.Companion.this.getClass();
                return Boolean.valueOf(KeyguardState.Companion.deviceIsAsleepInState((KeyguardState) obj));
            }
        })), Boolean.FALSE);
    }

    public static void assignTonalPaletteToOverlay(String str, final FabricatedOverlay fabricatedOverlay, TonalPalette tonalPalette) {
        final String concat = "android:color/system_".concat(str);
        tonalPalette.allShadesMapped.forEach(new BiConsumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                String str2 = concat;
                FabricatedOverlay fabricatedOverlay2 = fabricatedOverlay;
                int i = ThemeOverlayController.$r8$clinit;
                fabricatedOverlay2.setResourceValue(str2 + "_" + ((Integer) obj), 28, ColorUtils.setAlphaComponent(((Integer) obj2).intValue(), 255), (String) null);
            }
        });
    }

    public static boolean isSeedColorSet(JSONObject jSONObject, WallpaperColors wallpaperColors) {
        String str;
        if (wallpaperColors == null || (str = (String) jSONObject.opt("android.theme.customization.system_palette")) == null) {
            return false;
        }
        if (!str.startsWith("#")) {
            str = "#".concat(str);
        }
        int parseColor = Color.parseColor(str);
        Iterator it = ColorScheme.getSeedColors(wallpaperColors, true).iterator();
        while (it.hasNext()) {
            if (((Integer) it.next()).intValue() == parseColor) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Same as previous set system palette: ", str, "ThemeOverlayController");
                return true;
            }
        }
        return false;
    }

    public final void createOverlays(int i) {
        int i2 = this.mThemeStyle;
        double d = this.mContrast;
        this.mDarkColorScheme = new ColorScheme(i, true, i2, d == -1.0d ? 0.0d : d);
        int i3 = this.mThemeStyle;
        double d2 = this.mContrast;
        this.mLightColorScheme = new ColorScheme(i, false, i3, d2 == -1.0d ? 0.0d : d2);
        this.mColorScheme = isNightMode() ? this.mDarkColorScheme : this.mLightColorScheme;
        FabricatedOverlay newFabricatedOverlay = newFabricatedOverlay("neutral");
        assignTonalPaletteToOverlay("neutral1", newFabricatedOverlay, this.mColorScheme.mNeutral1);
        assignTonalPaletteToOverlay("neutral2", newFabricatedOverlay, this.mColorScheme.mNeutral2);
        this.mNeutralOverlay = newFabricatedOverlay;
        FabricatedOverlay newFabricatedOverlay2 = newFabricatedOverlay("accent");
        assignTonalPaletteToOverlay("accent1", newFabricatedOverlay2, this.mColorScheme.mAccent1);
        assignTonalPaletteToOverlay("accent2", newFabricatedOverlay2, this.mColorScheme.mAccent2);
        assignTonalPaletteToOverlay("accent3", newFabricatedOverlay2, this.mColorScheme.mAccent3);
        this.mSecondaryOverlay = newFabricatedOverlay2;
        final FabricatedOverlay newFabricatedOverlay3 = newFabricatedOverlay("dynamic");
        final MaterialDynamicColors materialDynamicColors = new MaterialDynamicColors(false);
        final int i4 = 0;
        final int i5 = 3;
        final int i6 = 14;
        final int i7 = 26;
        final int i8 = 8;
        final int i9 = 9;
        final int i10 = 10;
        final int i11 = 11;
        final int i12 = 12;
        final int i13 = 14;
        final int i14 = 21;
        final int i15 = 0;
        final int i16 = 13;
        final int i17 = 24;
        final int i18 = 28;
        final int i19 = 29;
        final int i20 = 0;
        final int i21 = 1;
        final int i22 = 1;
        final int i23 = 2;
        final int i24 = 4;
        final int i25 = 5;
        final int i26 = 6;
        final int i27 = 7;
        final int i28 = 8;
        final int i29 = 9;
        final int i30 = 10;
        final int i31 = 11;
        final int i32 = 12;
        final int i33 = 13;
        final int i34 = 15;
        final int i35 = 16;
        final int i36 = 17;
        final int i37 = 18;
        final int i38 = 19;
        final int i39 = 20;
        final int i40 = 22;
        final int i41 = 23;
        final int i42 = 24;
        final int i43 = 25;
        final int i44 = 27;
        final int i45 = 28;
        final int i46 = 29;
        final int i47 = 1;
        final int i48 = 2;
        final int i49 = 3;
        final int i50 = 4;
        final int i51 = 5;
        final int i52 = 6;
        final int i53 = 7;
        List generateSysUINames = DynamicColors.generateSysUINames(new Supplier[]{new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i4) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i5) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i6) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i7) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i8) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i9) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i10) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i11) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i12) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i13) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i14) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i15) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i16) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i17) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i18) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i19) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                int i54 = i20;
                MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                materialDynamicColors2.getClass();
                switch (i54) {
                    case 0:
                        return new DynamicColor("surface_variant", new MaterialDynamicColors$$ExternalSyntheticLambda7(10), new MaterialDynamicColors$$ExternalSyntheticLambda7(11), true, null, null, null, null);
                    default:
                        return new DynamicColor("on_surface_variant", new MaterialDynamicColors$$ExternalSyntheticLambda1(18), new MaterialDynamicColors$$ExternalSyntheticLambda1(19), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                int i54 = i21;
                MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                materialDynamicColors2.getClass();
                switch (i54) {
                    case 0:
                        return new DynamicColor("surface_variant", new MaterialDynamicColors$$ExternalSyntheticLambda7(10), new MaterialDynamicColors$$ExternalSyntheticLambda7(11), true, null, null, null, null);
                    default:
                        return new DynamicColor("on_surface_variant", new MaterialDynamicColors$$ExternalSyntheticLambda1(18), new MaterialDynamicColors$$ExternalSyntheticLambda1(19), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i22) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i23) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i24) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i25) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i26) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i27) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i28) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i29) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i30) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i31) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i32) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i33) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i34) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i35) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i36) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i37) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i38) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i39) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i40) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i41) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i42) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i43) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i44) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i45) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i46) {
                    case 0:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("primary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(5), new MaterialDynamicColors$$ExternalSyntheticLambda0(6));
                    case 1:
                        return MaterialDynamicColors.inverseSurface();
                    case 2:
                        return new DynamicColor("inverse_on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(17), new MaterialDynamicColors$$ExternalSyntheticLambda7(18), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 13), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("secondary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda3(18), new MaterialDynamicColors$$ExternalSyntheticLambda3(19));
                    case 4:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        materialDynamicColors2.getClass();
                        return new DynamicColor("outline", new MaterialDynamicColors$$ExternalSyntheticLambda1(26), new MaterialDynamicColors$$ExternalSyntheticLambda1(27), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 0), null, new ContrastCurve(1.5d, 3.0d, 4.5d, 7.0d), null);
                    case 5:
                        return materialDynamicColors.outlineVariant();
                    case 6:
                        materialDynamicColors.getClass();
                        return new DynamicColor("shadow", new MaterialDynamicColors$$ExternalSyntheticLambda0(28), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 7:
                        materialDynamicColors.getClass();
                        return new DynamicColor("scrim", new MaterialDynamicColors$$ExternalSyntheticLambda0(19), new CustomDynamicColors$$ExternalSyntheticLambda75(4), false, null, null, null, null);
                    case 8:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_tint", new MaterialDynamicColors$$ExternalSyntheticLambda7(21), new MaterialDynamicColors$$ExternalSyntheticLambda7(22), true, null, null, null, null);
                    case 9:
                        return materialDynamicColors.primary();
                    case 10:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        materialDynamicColors3.getClass();
                        return new DynamicColor("on_primary", new MaterialDynamicColors$$ExternalSyntheticLambda3(0), new MaterialDynamicColors$$ExternalSyntheticLambda3(11), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 11:
                        return materialDynamicColors.primaryContainer();
                    case 12:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(16), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 11), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors4, 12), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 13:
                        return new DynamicColor("inverse_primary", new MaterialDynamicColors$$ExternalSyntheticLambda0(1), new MaterialDynamicColors$$ExternalSyntheticLambda0(2), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 1), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), null);
                    case 14:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("tertiary_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(7), new MaterialDynamicColors$$ExternalSyntheticLambda0(8));
                    case 15:
                        return materialDynamicColors.secondary();
                    case 16:
                        return new DynamicColor("on_secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(24), new MaterialDynamicColors$$ExternalSyntheticLambda3(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 8), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        return materialDynamicColors.secondaryContainer();
                    case 18:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 21), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors5, 22), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 19:
                        return materialDynamicColors.tertiary();
                    case 20:
                        return new DynamicColor("on_tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda7(14), new MaterialDynamicColors$$ExternalSyntheticLambda7(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 12), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 21:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_lowest", new MaterialDynamicColors$$ExternalSyntheticLambda3(12), new MaterialDynamicColors$$ExternalSyntheticLambda3(13), true, null, null, null, null);
                    case 22:
                        return materialDynamicColors.tertiaryContainer();
                    case 23:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(9), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 5), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 6), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 24:
                        return materialDynamicColors.error();
                    case 25:
                        return new DynamicColor("on_error", new MaterialDynamicColors$$ExternalSyntheticLambda1(5), new MaterialDynamicColors$$ExternalSyntheticLambda1(6), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 19), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 26:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda7(23), new MaterialDynamicColors$$ExternalSyntheticLambda7(24));
                    case 27:
                        return materialDynamicColors.errorContainer();
                    case 28:
                        return new DynamicColor("on_error_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(20), new MaterialDynamicColors$$ExternalSyntheticLambda3(21), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors, 7), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    default:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_activated", new MaterialDynamicColors$$ExternalSyntheticLambda7(19), new MaterialDynamicColors$$ExternalSyntheticLambda7(20));
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i47) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i48) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i49) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i50) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i51) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i52) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i53) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors2 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors2, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors.primaryFixedDim();
                    case 19:
                        return materialDynamicColors.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors.secondaryFixed();
                    case 22:
                        return materialDynamicColors.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors.tertiaryFixed();
                    case 27:
                        return materialDynamicColors.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors.onSurface();
                }
            }
        }});
        final Boolean bool = Boolean.FALSE;
        ((ArrayList) generateSysUINames).forEach(new Consumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                Boolean bool2 = bool;
                FabricatedOverlay fabricatedOverlay = newFabricatedOverlay3;
                Pair pair = (Pair) obj;
                int i54 = ThemeOverlayController.$r8$clinit;
                themeOverlayController.getClass();
                String str = "android:color/system_" + ((String) pair.first);
                if (bool2.booleanValue()) {
                    fabricatedOverlay.setResourceValue(str, 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mLightColorScheme.mMaterialScheme), (String) null);
                } else {
                    fabricatedOverlay.setResourceValue(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "_light"), 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mLightColorScheme.mMaterialScheme), (String) null);
                    fabricatedOverlay.setResourceValue(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), str, "_dark"), 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mDarkColorScheme.mMaterialScheme), (String) null);
                }
            }
        });
        final MaterialDynamicColors materialDynamicColors2 = new MaterialDynamicColors(false);
        final int i54 = 15;
        final int i55 = 18;
        final int i56 = 19;
        final int i57 = 20;
        final int i58 = 21;
        final int i59 = 22;
        final int i60 = 23;
        final int i61 = 25;
        final int i62 = 26;
        final int i63 = 27;
        final int i64 = 16;
        final int i65 = 17;
        List generateSysUINames2 = DynamicColors.generateSysUINames(new Supplier[]{new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i54) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i55) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i56) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i57) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i58) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i59) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i60) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i61) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i62) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i63) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i64) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }, new Supplier() { // from class: com.android.systemui.monet.DynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i65) {
                    case 0:
                        return MaterialDynamicColors.surfaceContainerLow();
                    case 1:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("control_normal", new MaterialDynamicColors$$ExternalSyntheticLambda0(21), new MaterialDynamicColors$$ExternalSyntheticLambda0(22));
                    case 2:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("control_highlight", new MaterialDynamicColors$$ExternalSyntheticLambda0(25), new MaterialDynamicColors$$ExternalSyntheticLambda0(26), false, null, null, null, null, new MaterialDynamicColors$$ExternalSyntheticLambda0(27));
                    case 3:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda7(4), new MaterialDynamicColors$$ExternalSyntheticLambda7(5));
                    case 4:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda1(7), new MaterialDynamicColors$$ExternalSyntheticLambda1(8));
                    case 5:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_primary_inverse_disable_only", new MaterialDynamicColors$$ExternalSyntheticLambda1(12), new MaterialDynamicColors$$ExternalSyntheticLambda1(13));
                    case 6:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_secondary_and_tertiary_inverse_disabled", new MaterialDynamicColors$$ExternalSyntheticLambda3(6), new MaterialDynamicColors$$ExternalSyntheticLambda3(7));
                    case 7:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("text_hint_inverse", new MaterialDynamicColors$$ExternalSyntheticLambda3(26), new MaterialDynamicColors$$ExternalSyntheticLambda3(28));
                    case 8:
                        materialDynamicColors2.getClass();
                        return DynamicColor.fromPalette("neutral_variant_palette_key_color", new MaterialDynamicColors$$ExternalSyntheticLambda0(23), new MaterialDynamicColors$$ExternalSyntheticLambda0(24));
                    case 9:
                        return new DynamicColor(BriefViewController.SUGGESTION_BACKGROUND_KEY, new MaterialDynamicColors$$ExternalSyntheticLambda3(29), new MaterialDynamicColors$$ExternalSyntheticLambda7(1), true, null, null, null, null);
                    case 10:
                        return new DynamicColor("on_background", new MaterialDynamicColors$$ExternalSyntheticLambda0(14), new MaterialDynamicColors$$ExternalSyntheticLambda0(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors2, 10), null, new ContrastCurve(3.0d, 3.0d, 4.5d, 7.0d), null);
                    case 11:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface", new MaterialDynamicColors$$ExternalSyntheticLambda3(14), new MaterialDynamicColors$$ExternalSyntheticLambda3(15), true, null, null, null, null);
                    case 12:
                        return new DynamicColor("surface_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(24), new MaterialDynamicColors$$ExternalSyntheticLambda1(25), true, null, null, null, null);
                    case 13:
                        return new DynamicColor("surface_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(22), new MaterialDynamicColors$$ExternalSyntheticLambda1(23), true, null, null, null, null);
                    case 14:
                        return new DynamicColor("surface_bright", new MaterialDynamicColors$$ExternalSyntheticLambda3(27), new MaterialDynamicColors$$ExternalSyntheticLambda7(0), true, null, null, null, null);
                    case 15:
                        return materialDynamicColors2.primaryFixed();
                    case 16:
                        MaterialDynamicColors materialDynamicColors22 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(2), new MaterialDynamicColors$$ExternalSyntheticLambda7(3), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 9), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors22, 10), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 17:
                        MaterialDynamicColors materialDynamicColors3 = materialDynamicColors2;
                        return new DynamicColor("on_tertiary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(16), new MaterialDynamicColors$$ExternalSyntheticLambda3(17), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 5), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors3, 6), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 18:
                        return materialDynamicColors2.primaryFixedDim();
                    case 19:
                        return materialDynamicColors2.onPrimaryFixed();
                    case 20:
                        MaterialDynamicColors materialDynamicColors4 = materialDynamicColors2;
                        return new DynamicColor("on_primary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda3(8), new MaterialDynamicColors$$ExternalSyntheticLambda3(9), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 1), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors4, 2), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 21:
                        return materialDynamicColors2.secondaryFixed();
                    case 22:
                        return materialDynamicColors2.secondaryFixedDim();
                    case 23:
                        MaterialDynamicColors materialDynamicColors5 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda3(10), new CustomDynamicColors$$ExternalSyntheticLambda75(12), false, new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 3), new MaterialDynamicColors$$ExternalSyntheticLambda5(materialDynamicColors5, 4), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
                    case 24:
                        return new DynamicColor("surface_container_high", new MaterialDynamicColors$$ExternalSyntheticLambda1(28), new MaterialDynamicColors$$ExternalSyntheticLambda1(29), true, null, null, null, null);
                    case 25:
                        MaterialDynamicColors materialDynamicColors6 = materialDynamicColors2;
                        return new DynamicColor("on_secondary_fixed_variant", new MaterialDynamicColors$$ExternalSyntheticLambda0(3), new MaterialDynamicColors$$ExternalSyntheticLambda0(4), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 2), new MaterialDynamicColors$$ExternalSyntheticLambda2(materialDynamicColors6, 3), new ContrastCurve(3.0d, 4.5d, 7.0d, 11.0d), null);
                    case 26:
                        return materialDynamicColors2.tertiaryFixed();
                    case 27:
                        return materialDynamicColors2.tertiaryFixedDim();
                    case 28:
                        materialDynamicColors2.getClass();
                        return new DynamicColor("surface_container_highest", new MaterialDynamicColors$$ExternalSyntheticLambda7(12), new MaterialDynamicColors$$ExternalSyntheticLambda7(13), true, null, null, null, null);
                    default:
                        return materialDynamicColors2.onSurface();
                }
            }
        }});
        final Boolean bool2 = Boolean.TRUE;
        ((ArrayList) generateSysUINames2).forEach(new Consumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                Boolean bool22 = bool2;
                FabricatedOverlay fabricatedOverlay = newFabricatedOverlay3;
                Pair pair = (Pair) obj;
                int i542 = ThemeOverlayController.$r8$clinit;
                themeOverlayController.getClass();
                String str = "android:color/system_" + ((String) pair.first);
                if (bool22.booleanValue()) {
                    fabricatedOverlay.setResourceValue(str, 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mLightColorScheme.mMaterialScheme), (String) null);
                } else {
                    fabricatedOverlay.setResourceValue(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "_light"), 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mLightColorScheme.mMaterialScheme), (String) null);
                    fabricatedOverlay.setResourceValue(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), str, "_dark"), 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mDarkColorScheme.mMaterialScheme), (String) null);
                }
            }
        });
        ((ArrayList) DynamicColors.generateSysUINames(new CustomDynamicColors(false).allColors)).forEach(new Consumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                Boolean bool22 = bool;
                FabricatedOverlay fabricatedOverlay = newFabricatedOverlay3;
                Pair pair = (Pair) obj;
                int i542 = ThemeOverlayController.$r8$clinit;
                themeOverlayController.getClass();
                String str = "android:color/system_" + ((String) pair.first);
                if (bool22.booleanValue()) {
                    fabricatedOverlay.setResourceValue(str, 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mLightColorScheme.mMaterialScheme), (String) null);
                } else {
                    fabricatedOverlay.setResourceValue(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "_light"), 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mLightColorScheme.mMaterialScheme), (String) null);
                    fabricatedOverlay.setResourceValue(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), str, "_dark"), 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mDarkColorScheme.mMaterialScheme), (String) null);
                }
            }
        });
        this.mDynamicOverlay = newFabricatedOverlay3;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mSystemColors=" + this.mCurrentColors);
        printWriter.println("mMainWallpaperColor=" + Integer.toHexString(this.mMainWallpaperColor));
        printWriter.println("mContrast=" + this.mContrast);
        printWriter.println("mSecondaryOverlay=" + this.mSecondaryOverlay);
        printWriter.println("mNeutralOverlay=" + this.mNeutralOverlay);
        printWriter.println("mDynamicOverlay=" + this.mDynamicOverlay);
        printWriter.println("mIsMonetEnabled=" + this.mIsMonetEnabled);
        printWriter.println("mIsFidelityEnabled=false");
        printWriter.println("mColorScheme=" + this.mColorScheme);
        MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("mNeedsOverlayCreation="), this.mNeedsOverlayCreation, printWriter, "mAcceptColorEvents="), this.mAcceptColorEvents, printWriter, "mDeferredThemeEvaluation="), this.mDeferredThemeEvaluation, printWriter, "mThemeStyle="), this.mThemeStyle, printWriter);
    }

    public HardwareDefaultSetting getThemeSettingsDefaults() {
        String[] stringArray = this.mResources.getStringArray(17236495);
        HashMap hashMap = new HashMap();
        int length = stringArray.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String[] split = stringArray[i].split("\\|");
            if (split.length == 3) {
                hashMap.put(split[0], new Pair(Integer.valueOf(Style.valueOf(split[1])), split[2]));
            }
            i++;
        }
        Pair pair = (Pair) hashMap.get("*");
        if (pair == null) {
            Log.d("ThemeOverlayController", "Theming wildcard not found. Fallback to TONAL_SPOT|home_wallpaper");
            pair = new Pair(1, "home_wallpaper");
        }
        this.mSystemPropertiesHelper.getClass();
        String str = SystemProperties.get("ro.boot.hardware.color");
        Pair pair2 = (Pair) hashMap.get(str);
        if (pair2 == null) {
            ExifInterface$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Sysprop `ro.boot.hardware.color` of value '", str, "' not found in theming_defaults: "), Arrays.toString(stringArray), "ThemeOverlayController");
        } else {
            pair = pair2;
        }
        Color valueOf = Color.valueOf(-14979341);
        boolean equals = ((String) pair.second).equals("home_wallpaper");
        if (equals) {
            WallpaperManager wallpaperManager = this.mWallpaperManager;
            int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
            WallpaperColors wallpaperColors = wallpaperManager.getWallpaperColors(this.mWallpaperManager.getWallpaperIdForUser(2, userId) <= this.mWallpaperManager.getWallpaperIdForUser(1, userId) ? 1 : 2);
            if (wallpaperColors != null) {
                valueOf = wallpaperColors.getPrimaryColor();
            }
            Log.d("ThemeOverlayController", "Default seed color read from home wallpaper: " + Integer.toHexString(valueOf.toArgb()));
        } else {
            try {
                valueOf = Color.valueOf(Color.parseColor((String) pair.second));
                Log.d("ThemeOverlayController", "Default seed color read from resource: " + Integer.toHexString(valueOf.toArgb()));
            } catch (IllegalArgumentException e) {
                Log.e("ThemeOverlayController", "Error parsing color: " + ((String) pair.second), e);
            }
        }
        return new HardwareDefaultSetting(valueOf, ((Integer) pair.first).intValue(), equals ? "home_wallpaper" : "preset");
    }

    public boolean isNightMode() {
        return (this.mResources.getConfiguration().uiMode & 48) == 32;
    }

    public boolean isPrivateProfile(UserHandle userHandle) {
        return ((UserManager) this.mContext.createContextAsUser(userHandle, 0).getSystemService(UserManager.class)).isPrivateProfile();
    }

    public FabricatedOverlay newFabricatedOverlay(String str) {
        return new FabricatedOverlay.Builder("com.android.systemui", str, "android").build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0092, code lost:
    
        if (r0.contains(java.lang.Integer.valueOf(r12)) == false) goto L16;
     */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda3] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reevaluateSystemTheme(boolean r21) {
        /*
            Method dump skipped, instructions count: 882
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.theme.ThemeOverlayController.reevaluateSystemTheme(boolean):void");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda1, java.lang.Runnable] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.d("ThemeOverlayController", "Start");
        final ?? r0 = new Runnable() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                final ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                WallpaperManager wallpaperManager = themeOverlayController.mWallpaperManager;
                int userId = ((UserTrackerImpl) themeOverlayController.mUserTracker).getUserId();
                final WallpaperColors wallpaperColors = wallpaperManager.getWallpaperColors(themeOverlayController.mWallpaperManager.getWallpaperIdForUser(2, userId) <= themeOverlayController.mWallpaperManager.getWallpaperIdForUser(1, userId) ? 1 : 2);
                themeOverlayController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        ThemeOverlayController themeOverlayController2 = ThemeOverlayController.this;
                        themeOverlayController2.mCurrentColors.put(((UserTrackerImpl) themeOverlayController2.mUserTracker).getUserId(), wallpaperColors);
                        themeOverlayController2.reevaluateSystemTheme(true);
                    }
                });
            }
        };
        IntentFilter m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.PROFILE_ADDED", "android.intent.action.WALLPAPER_CHANGED");
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, m, this.mMainExecutor, UserHandle.ALL);
        this.mSecureSettings.registerContentObserverForUserSync("theme_customization_overlay_packages", false, new ContentObserver(this.mBgHandler) { // from class: com.android.systemui.theme.ThemeOverlayController.5
            public final void onChange(boolean z, Collection collection, int i, int i2) {
                if (TextUtils.isEmpty(ThemeOverlayController.this.mSecureSettings.getStringForUser("theme_customization_overlay_packages", i2))) {
                    return;
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "Overlay changed for user: ", "ThemeOverlayController");
                if (((UserTrackerImpl) ThemeOverlayController.this.mUserTracker).getUserId() != i2) {
                    return;
                }
                if (!((DeviceProvisionedControllerImpl) ThemeOverlayController.this.mDeviceProvisionedController).isUserSetup(i2)) {
                    Log.i("ThemeOverlayController", "Theme application deferred when setting changed.");
                    ThemeOverlayController.this.mDeferredThemeEvaluation = true;
                    return;
                }
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                if (!themeOverlayController.mSkipSettingChange) {
                    themeOverlayController.mBgExecutor.execute(r0);
                } else {
                    Log.d("ThemeOverlayController", "Skipping setting change");
                    ThemeOverlayController.this.mSkipSettingChange = false;
                }
            }
        }, -1);
        this.mUiModeManager.addContrastChangeListener(this.mMainExecutor, new UiModeManager.ContrastChangeListener() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda2
            @Override // android.app.UiModeManager.ContrastChangeListener
            public final void onContrastChanged(float f) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                ThemeOverlayController$$ExternalSyntheticLambda1 themeOverlayController$$ExternalSyntheticLambda1 = r0;
                double d = f;
                themeOverlayController.mContrast = d;
                if (d == -1.0d) {
                    return;
                }
                themeOverlayController.mBgExecutor.execute(themeOverlayController$$ExternalSyntheticLambda1);
            }
        });
    }
}
