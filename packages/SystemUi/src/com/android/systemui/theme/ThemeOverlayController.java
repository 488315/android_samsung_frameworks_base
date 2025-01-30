package com.android.systemui.theme;

import android.R;
import android.app.UiModeManager;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.FabricatedOverlay;
import android.content.om.OverlayIdentifier;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Color;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.monet.TonalPalette;
import com.android.systemui.monet.dynamiccolor.DynamicColor;
import com.android.systemui.monet.dynamiccolor.MaterialDynamicColors;
import com.android.systemui.monet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda4;
import com.android.systemui.monet.dynamiccolor.MaterialDynamicColors$$ExternalSyntheticLambda5;
import com.android.systemui.monet.hct.Hct;
import com.android.systemui.monet.scheme.DynamicScheme;
import com.android.systemui.monet.scheme.SchemeExpressive;
import com.android.systemui.monet.scheme.SchemeFruitSalad;
import com.android.systemui.monet.scheme.SchemeMonochrome;
import com.android.systemui.monet.scheme.SchemeNeutral;
import com.android.systemui.monet.scheme.SchemeRainbow;
import com.android.systemui.monet.scheme.SchemeTonalSpot;
import com.android.systemui.monet.scheme.SchemeVibrant;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.sec.ims.configuration.DATA;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.collections.CollectionsKt___CollectionsKt;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ThemeOverlayController implements CoreStartable, Dumpable {
    public final Executor mBgExecutor;
    public final Handler mBgHandler;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C34824 mBroadcastReceiver;
    protected ColorScheme mColorScheme;
    public final Context mContext;
    public boolean mDeferredThemeEvaluation;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public FabricatedOverlay mDynamicOverlay;
    public DynamicScheme mDynamicSchemeDark;
    public DynamicScheme mDynamicSchemeLight;
    public final boolean mIsMonetEnabled;
    public final Executor mMainExecutor;
    public boolean mNeedsOverlayCreation;
    public FabricatedOverlay mNeutralOverlay;
    public final C34802 mOnColorsChangedListener;
    public final Resources mResources;
    public FabricatedOverlay mSecondaryOverlay;
    public final SecureSettings mSecureSettings;
    public boolean mSkipSettingChange;
    public final ThemeOverlayApplier mThemeManager;
    public final UiModeManager mUiModeManager;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final UserTracker.Callback mUserTrackerCallback;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WallpaperManager mWallpaperManager;
    public final SparseArray mCurrentColors = new SparseArray();
    public int mMainWallpaperColor = 0;
    public float mContrast = -1.0f;
    protected Style mThemeStyle = Style.TONAL_SPOT;
    public boolean mAcceptColorEvents = true;
    public final SparseArray mDeferredWallpaperColors = new SparseArray();
    public final SparseIntArray mDeferredWallpaperColorsFlags = new SparseIntArray();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.theme.ThemeOverlayController$7 */
    public abstract /* synthetic */ class AbstractC34847 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$monet$Style;

        static {
            int[] iArr = new int[Style.values().length];
            $SwitchMap$com$android$systemui$monet$Style = iArr;
            try {
                iArr[Style.EXPRESSIVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$Style[Style.SPRITZ.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$Style[Style.TONAL_SPOT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$Style[Style.FRUIT_SALAD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$Style[Style.RAINBOW.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$Style[Style.VIBRANT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$monet$Style[Style.MONOCHROMATIC.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.theme.ThemeOverlayController$4] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.theme.ThemeOverlayController$2] */
    public ThemeOverlayController(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, Executor executor, Executor executor2, ThemeOverlayApplier themeOverlayApplier, SecureSettings secureSettings, WallpaperManager wallpaperManager, UserManager userManager, DeviceProvisionedController deviceProvisionedController, UserTracker userTracker, DumpManager dumpManager, FeatureFlags featureFlags, Resources resources, WakefulnessLifecycle wakefulnessLifecycle, UiModeManager uiModeManager) {
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
        this.mOnColorsChangedListener = new WallpaperManager.OnColorsChangedListener() { // from class: com.android.systemui.theme.ThemeOverlayController.2
            @Override // android.app.WallpaperManager.OnColorsChangedListener
            public final void onColorsChanged(WallpaperColors wallpaperColors, int i) {
                throw new IllegalStateException("This should never be invoked, all messages should arrive on the overload that has a user id");
            }

            public final void onColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                if (themeOverlayController.mContrast == -1.0f || themeOverlayController.isColorThemeEnabled$1()) {
                    return;
                }
                WallpaperColors wallpaperColors2 = (WallpaperColors) ThemeOverlayController.this.mCurrentColors.get(i2);
                if (wallpaperColors == null || !wallpaperColors.equals(wallpaperColors2)) {
                    boolean z = i2 == ((UserTrackerImpl) ThemeOverlayController.this.mUserTracker).getUserId();
                    if (z) {
                        ThemeOverlayController themeOverlayController2 = ThemeOverlayController.this;
                        if (!themeOverlayController2.mAcceptColorEvents && themeOverlayController2.mWakefulnessLifecycle.mWakefulness != 0) {
                            themeOverlayController2.mDeferredWallpaperColors.put(i2, wallpaperColors);
                            ThemeOverlayController.this.mDeferredWallpaperColorsFlags.put(i2, i);
                            Log.i("ThemeOverlayController", "colors received; processing deferred until screen off: " + wallpaperColors + " user: " + i2);
                            return;
                        }
                    }
                    if (z && wallpaperColors != null) {
                        ThemeOverlayController themeOverlayController3 = ThemeOverlayController.this;
                        themeOverlayController3.mAcceptColorEvents = false;
                        themeOverlayController3.mDeferredWallpaperColors.put(i2, null);
                        ThemeOverlayController.this.mDeferredWallpaperColorsFlags.put(i2, 0);
                    }
                    ThemeOverlayController themeOverlayController4 = ThemeOverlayController.this;
                    String str = "lock_wallpaper";
                    int userId = ((UserTrackerImpl) themeOverlayController4.mUserTracker).getUserId();
                    SparseArray sparseArray = themeOverlayController4.mCurrentColors;
                    boolean z2 = sparseArray.get(i2) != null;
                    WallpaperManager wallpaperManager2 = themeOverlayController4.mWallpaperManager;
                    boolean z3 = ((wallpaperManager2.getWallpaperIdForUser(2, i2) <= wallpaperManager2.getWallpaperIdForUser(1, i2) ? 1 : 2) & i) != 0;
                    if (z3) {
                        sparseArray.put(i2, wallpaperColors);
                        Log.d("ThemeOverlayController", "got new colors: " + wallpaperColors + " where: " + i);
                    }
                    if (i2 != userId) {
                        StringBuilder sb = new StringBuilder("Colors ");
                        sb.append(wallpaperColors);
                        sb.append(" for user ");
                        sb.append(i2);
                        sb.append(". Not for current user: ");
                        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, userId, "ThemeOverlayController");
                        return;
                    }
                    DeviceProvisionedController deviceProvisionedController2 = themeOverlayController4.mDeviceProvisionedController;
                    if (deviceProvisionedController2 != null && !((DeviceProvisionedControllerImpl) deviceProvisionedController2).isCurrentUserSetup()) {
                        if (z2) {
                            Log.i("ThemeOverlayController", "Wallpaper color event deferred until setup is finished: " + wallpaperColors);
                            themeOverlayController4.mDeferredThemeEvaluation = true;
                            return;
                        }
                        if (themeOverlayController4.mDeferredThemeEvaluation) {
                            Log.i("ThemeOverlayController", "Wallpaper color event received, but we already were deferring eval: " + wallpaperColors);
                            return;
                        }
                        NotificationListener$$ExternalSyntheticOutline0.m123m(RowView$$ExternalSyntheticOutline0.m49m("During user setup, but allowing first color event: had? ", z2, " has? "), sparseArray.get(i2) != null, "ThemeOverlayController");
                    }
                    SecureSettings secureSettings2 = themeOverlayController4.mSecureSettings;
                    String stringForUser = ((SecureSettingsImpl) secureSettings2).getStringForUser(userId, "theme_customization_overlay_packages");
                    boolean z4 = i == 3;
                    boolean z5 = i == 1;
                    try {
                        JSONObject jSONObject = stringForUser == null ? new JSONObject() : new JSONObject(stringForUser);
                        String optString = jSONObject.optString("android.theme.customization.color_source");
                        boolean equals = "preset".equals(optString);
                        boolean z6 = z5 && "lock_wallpaper".equals(optString);
                        if (!equals && !z6 && z3 && !ThemeOverlayController.isSeedColorSet(jSONObject, wallpaperColors)) {
                            themeOverlayController4.mSkipSettingChange = true;
                            if (jSONObject.has("android.theme.customization.accent_color") || jSONObject.has("android.theme.customization.system_palette")) {
                                jSONObject.remove("android.theme.customization.dynamic_color");
                                jSONObject.remove("android.theme.customization.accent_color");
                                jSONObject.remove("android.theme.customization.system_palette");
                                jSONObject.remove("android.theme.customization.color_index");
                            }
                            jSONObject.put("android.theme.customization.color_both", z4 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                            if (i != 2) {
                                str = "home_wallpaper";
                            }
                            jSONObject.put("android.theme.customization.color_source", str);
                            jSONObject.put("_applied_timestamp", System.currentTimeMillis());
                            Log.d("ThemeOverlayController", "Updating theme setting from " + stringForUser + " to " + jSONObject.toString());
                            ((SecureSettingsImpl) secureSettings2).putStringForUser(-2, "theme_customization_overlay_packages", jSONObject.toString());
                        }
                    } catch (JSONException e) {
                        Log.i("ThemeOverlayController", "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES.", e);
                    }
                    themeOverlayController4.reevaluateSystemTheme(false);
                }
            }
        };
        this.mUserTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.theme.ThemeOverlayController.3
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                boolean isManagedProfile = themeOverlayController.mUserManager.isManagedProfile(i);
                if (!((DeviceProvisionedControllerImpl) themeOverlayController.mDeviceProvisionedController).isCurrentUserSetup() && isManagedProfile) {
                    ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("User setup not finished when new user event was received. Deferring... Managed profile? ", isManagedProfile, "ThemeOverlayController");
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
                if (themeOverlayController.mContrast == -1.0f || themeOverlayController.isColorThemeEnabled$1()) {
                    return;
                }
                boolean equals = "android.intent.action.MANAGED_PROFILE_ADDED".equals(intent.getAction());
                boolean isManagedProfile = ThemeOverlayController.this.mUserManager.isManagedProfile(intent.getIntExtra("android.intent.extra.user_handle", 0));
                if (!equals) {
                    if ("android.intent.action.WALLPAPER_CHANGED".equals(intent.getAction())) {
                        if (!intent.getBooleanExtra("android.service.wallpaper.extra.FROM_FOREGROUND_APP", false)) {
                            NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("Wallpaper changed from background app, keep deferring color events. Accepting: "), ThemeOverlayController.this.mAcceptColorEvents, "ThemeOverlayController");
                            return;
                        } else {
                            ThemeOverlayController.this.mAcceptColorEvents = true;
                            Log.i("ThemeOverlayController", "Wallpaper changed, allowing color events again");
                            return;
                        }
                    }
                    return;
                }
                if (((DeviceProvisionedControllerImpl) ThemeOverlayController.this.mDeviceProvisionedController).isCurrentUserSetup() || !isManagedProfile) {
                    Log.d("ThemeOverlayController", "Updating overlays for user switch / profile added.");
                    ThemeOverlayController.this.reevaluateSystemTheme(true);
                    return;
                }
                Log.i("ThemeOverlayController", "User setup not finished when " + intent.getAction() + " was received. Deferring... Managed profile? " + isManagedProfile);
            }
        };
        this.mContext = context;
        FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) featureFlags;
        featureFlagsRelease.isEnabled(Flags.MONOCHROMATIC_THEME);
        this.mIsMonetEnabled = featureFlagsRelease.isEnabled(Flags.MONET);
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
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "ThemeOverlayController", this);
    }

    public static void assignTonalPaletteToOverlay(String str, final FabricatedOverlay fabricatedOverlay, TonalPalette tonalPalette) {
        final String concat = "android:color/system_".concat(str);
        tonalPalette.allShadesMapped.forEach(new BiConsumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                String str2 = concat;
                fabricatedOverlay.setResourceValue(str2 + "_" + ((Integer) obj), 28, ColorUtils.setAlphaComponent(((Integer) obj2).intValue(), 255), (String) null);
            }
        });
    }

    public static DynamicScheme dynamicSchemeFromStyle(Style style, int i, boolean z, double d) {
        Hct fromInt = Hct.fromInt(i);
        switch (AbstractC34847.$SwitchMap$com$android$systemui$monet$Style[style.ordinal()]) {
            case 1:
                return new SchemeExpressive(fromInt, z, d);
            case 2:
                return new SchemeNeutral(fromInt, z, d);
            case 3:
                return new SchemeTonalSpot(fromInt, z, d);
            case 4:
                return new SchemeFruitSalad(fromInt, z, d);
            case 5:
                return new SchemeRainbow(fromInt, z, d);
            case 6:
                return new SchemeVibrant(fromInt, z, d);
            case 7:
                return new SchemeMonochrome(fromInt, z, d);
            default:
                return null;
        }
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
        ColorScheme.Companion.getClass();
        Iterator it = ColorScheme.Companion.getSeedColors(wallpaperColors, true).iterator();
        while (it.hasNext()) {
            if (((Integer) it.next()).intValue() == parseColor) {
                AbstractC0000x2c234b15.m3m("Same as previous set system palette: ", str, "ThemeOverlayController");
                return true;
            }
        }
        return false;
    }

    public final void createOverlays(int i) {
        this.mColorScheme = new ColorScheme(i, isNightMode(), this.mThemeStyle);
        FabricatedOverlay newFabricatedOverlay = newFabricatedOverlay("neutral");
        assignTonalPaletteToOverlay("neutral1", newFabricatedOverlay, this.mColorScheme.neutral1);
        assignTonalPaletteToOverlay("neutral2", newFabricatedOverlay, this.mColorScheme.neutral2);
        this.mNeutralOverlay = newFabricatedOverlay;
        FabricatedOverlay newFabricatedOverlay2 = newFabricatedOverlay("accent");
        assignTonalPaletteToOverlay("accent1", newFabricatedOverlay2, this.mColorScheme.accent1);
        assignTonalPaletteToOverlay("accent2", newFabricatedOverlay2, this.mColorScheme.accent2);
        assignTonalPaletteToOverlay("accent3", newFabricatedOverlay2, this.mColorScheme.accent3);
        this.mSecondaryOverlay = newFabricatedOverlay2;
        Style style = this.mThemeStyle;
        float f = this.mContrast;
        if (f == -1.0f) {
            f = 0.0f;
        }
        this.mDynamicSchemeDark = dynamicSchemeFromStyle(style, i, true, f);
        this.mDynamicSchemeLight = dynamicSchemeFromStyle(this.mThemeStyle, i, false, this.mContrast != -1.0f ? r1 : 0.0f);
        final FabricatedOverlay newFabricatedOverlay3 = newFabricatedOverlay("dynamic");
        final DynamicScheme dynamicScheme = this.mDynamicSchemeDark;
        List list = DynamicColors.ALL_DYNAMIC_COLORS_MAPPED;
        final String str = "dark";
        list.forEach(new Consumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Pair pair = (Pair) obj;
                newFabricatedOverlay3.setResourceValue(FragmentTransaction$$ExternalSyntheticOutline0.m38m(new StringBuilder("android:color/system_"), (String) pair.first, "_", str), 28, ((DynamicColor) pair.second).getArgb(dynamicScheme), (String) null);
            }
        });
        final DynamicScheme dynamicScheme2 = this.mDynamicSchemeLight;
        final String str2 = "light";
        list.forEach(new Consumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Pair pair = (Pair) obj;
                newFabricatedOverlay3.setResourceValue(FragmentTransaction$$ExternalSyntheticOutline0.m38m(new StringBuilder("android:color/system_"), (String) pair.first, "_", str2), 28, ((DynamicColor) pair.second).getArgb(dynamicScheme2), (String) null);
            }
        });
        DynamicColors.FIXED_COLORS_MAPPED.forEach(new Consumer() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                FabricatedOverlay fabricatedOverlay = newFabricatedOverlay3;
                Pair pair = (Pair) obj;
                themeOverlayController.getClass();
                fabricatedOverlay.setResourceValue("android:color/system_" + ((String) pair.first), 28, ((DynamicColor) pair.second).getArgb(themeOverlayController.mDynamicSchemeLight), (String) null);
            }
        });
        this.mDynamicOverlay = newFabricatedOverlay3;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mSystemColors=" + this.mCurrentColors);
        printWriter.println("mMainWallpaperColor=" + Integer.toHexString(this.mMainWallpaperColor));
        printWriter.println("mSecondaryOverlay=" + this.mSecondaryOverlay);
        printWriter.println("mNeutralOverlay=" + this.mNeutralOverlay);
        printWriter.println("mDynamicOverlay=" + this.mDynamicOverlay);
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("mIsMonetEnabled="), this.mIsMonetEnabled, printWriter, "mColorScheme=");
        m64m.append(this.mColorScheme);
        printWriter.println(m64m.toString());
        StringBuilder m64m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("mNeedsOverlayCreation="), this.mNeedsOverlayCreation, printWriter, "mAcceptColorEvents="), this.mAcceptColorEvents, printWriter, "mDeferredThemeEvaluation="), this.mDeferredThemeEvaluation, printWriter, "mThemeStyle=");
        m64m2.append(this.mThemeStyle);
        printWriter.println(m64m2.toString());
    }

    public final boolean isColorThemeEnabled$1() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "wallpapertheme_state", -1) == 1;
    }

    public boolean isNightMode() {
        return (this.mResources.getConfiguration().uiMode & 48) == 32;
    }

    public FabricatedOverlay newFabricatedOverlay(String str) {
        return new FabricatedOverlay.Builder("com.android.systemui", str, "android").build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0082, code lost:
    
        if (r0.contains(r10) == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void reevaluateSystemTheme(boolean z) {
        int intValue;
        FabricatedOverlay fabricatedOverlay;
        FabricatedOverlay fabricatedOverlay2;
        FabricatedOverlay fabricatedOverlay3;
        Style valueOf;
        SparseArray sparseArray = this.mCurrentColors;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.mUserTracker;
        WallpaperColors wallpaperColors = (WallpaperColors) sparseArray.get(userTrackerImpl.getUserId());
        boolean z2 = true;
        if (wallpaperColors == null) {
            intValue = 0;
        } else {
            ColorScheme.Companion.getClass();
            intValue = ((Number) CollectionsKt___CollectionsKt.first(ColorScheme.Companion.getSeedColors(wallpaperColors, true))).intValue();
        }
        if (this.mMainWallpaperColor != intValue || z) {
            this.mMainWallpaperColor = intValue;
            SecureSettings secureSettings = this.mSecureSettings;
            boolean z3 = this.mIsMonetEnabled;
            if (z3) {
                Style style = Style.EXPRESSIVE;
                Style style2 = Style.SPRITZ;
                Style style3 = Style.TONAL_SPOT;
                ArrayList arrayList = new ArrayList(Arrays.asList(style, style2, style3, Style.FRUIT_SALAD, Style.RAINBOW, Style.VIBRANT, Style.MONOCHROMATIC));
                String stringForUser = ((SecureSettingsImpl) secureSettings).getStringForUser(userTrackerImpl.getUserId(), "theme_customization_overlay_packages");
                if (!TextUtils.isEmpty(stringForUser)) {
                    try {
                        valueOf = Style.valueOf(new JSONObject(stringForUser).getString("android.theme.customization.theme_style"));
                    } catch (IllegalArgumentException | JSONException e) {
                        Log.i("ThemeOverlayController", "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES.", e);
                        style3 = Style.TONAL_SPOT;
                    }
                }
                valueOf = style3;
                this.mThemeStyle = valueOf;
                createOverlays(this.mMainWallpaperColor);
                this.mNeedsOverlayCreation = true;
                Log.d("ThemeOverlayController", "fetched overlays. accent: " + this.mSecondaryOverlay + " neutral: " + this.mNeutralOverlay + " dynamic: " + this.mDynamicOverlay);
            }
            int userId = userTrackerImpl.getUserId();
            String stringForUser2 = ((SecureSettingsImpl) secureSettings).getStringForUser(userId, "theme_customization_overlay_packages");
            Log.d("ThemeOverlayController", "updateThemeOverlays. Setting: " + stringForUser2);
            final ArrayMap arrayMap = new ArrayMap();
            if (!TextUtils.isEmpty(stringForUser2)) {
                try {
                    JSONObject jSONObject = new JSONObject(stringForUser2);
                    for (String str : ThemeOverlayApplier.THEME_CATEGORIES) {
                        if (jSONObject.has(str)) {
                            arrayMap.put(str, new OverlayIdentifier(jSONObject.getString(str)));
                        }
                    }
                } catch (JSONException e2) {
                    Log.i("ThemeOverlayController", "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES.", e2);
                }
            }
            OverlayIdentifier overlayIdentifier = (OverlayIdentifier) arrayMap.get("android.theme.customization.system_palette");
            if (z3 && overlayIdentifier != null && overlayIdentifier.getPackageName() != null) {
                try {
                    String lowerCase = overlayIdentifier.getPackageName().toLowerCase();
                    if (!lowerCase.startsWith("#")) {
                        lowerCase = "#" + lowerCase;
                    }
                    createOverlays(Color.parseColor(lowerCase));
                    this.mNeedsOverlayCreation = true;
                    arrayMap.remove("android.theme.customization.system_palette");
                    arrayMap.remove("android.theme.customization.accent_color");
                    arrayMap.remove("android.theme.customization.dynamic_color");
                } catch (Exception e3) {
                    Log.w("ThemeOverlayController", "Invalid color definition: " + overlayIdentifier.getPackageName(), e3);
                }
            } else if (!z3 && overlayIdentifier != null) {
                try {
                    arrayMap.remove("android.theme.customization.system_palette");
                    arrayMap.remove("android.theme.customization.accent_color");
                    arrayMap.remove("android.theme.customization.dynamic_color");
                } catch (NumberFormatException unused) {
                }
            }
            if (!arrayMap.containsKey("android.theme.customization.system_palette") && (fabricatedOverlay3 = this.mNeutralOverlay) != null) {
                arrayMap.put("android.theme.customization.system_palette", fabricatedOverlay3.getIdentifier());
            }
            if (!arrayMap.containsKey("android.theme.customization.accent_color") && (fabricatedOverlay2 = this.mSecondaryOverlay) != null) {
                arrayMap.put("android.theme.customization.accent_color", fabricatedOverlay2.getIdentifier());
            }
            if (!arrayMap.containsKey("android.theme.customization.dynamic_color") && (fabricatedOverlay = this.mDynamicOverlay) != null) {
                arrayMap.put("android.theme.customization.dynamic_color", fabricatedOverlay.getIdentifier());
            }
            HashSet hashSet = new HashSet();
            for (UserInfo userInfo : this.mUserManager.getEnabledProfiles(userId)) {
                if (userInfo.isManagedProfile()) {
                    hashSet.add(userInfo.getUserHandle());
                }
            }
            ArraySet arraySet = new ArraySet(hashSet);
            arraySet.add(UserHandle.SYSTEM);
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                UserHandle userHandle = (UserHandle) it.next();
                boolean isSystem = userHandle.isSystem();
                Context context = this.mContext;
                Resources resources = isSystem ? this.mResources : context.createContextAsUser(userHandle, 0).getResources();
                Resources.Theme theme = context.getTheme();
                MaterialDynamicColors materialDynamicColors = new MaterialDynamicColors();
                if (resources.getColor(R.color.system_accent1_500, theme) != this.mColorScheme.accent1.getS500() || resources.getColor(R.color.system_accent2_500, theme) != this.mColorScheme.accent2.getS500() || resources.getColor(R.color.system_accent3_500, theme) != this.mColorScheme.accent3.getS500() || resources.getColor(R.color.system_neutral1_500, theme) != this.mColorScheme.neutral1.getS500() || resources.getColor(R.color.system_neutral2_500, theme) != this.mColorScheme.neutral2.getS500() || resources.getColor(R.color.system_outline_variant_dark, theme) != materialDynamicColors.outlineVariant().getArgb(this.mDynamicSchemeDark) || resources.getColor(R.color.system_outline_variant_light, theme) != materialDynamicColors.outlineVariant().getArgb(this.mDynamicSchemeLight) || resources.getColor(R.color.system_primary_container_dark, theme) != materialDynamicColors.primaryContainer().getArgb(this.mDynamicSchemeDark) || resources.getColor(R.color.system_primary_container_light, theme) != materialDynamicColors.primaryContainer().getArgb(this.mDynamicSchemeLight) || resources.getColor(R.color.system_primary_fixed, theme) != DynamicColor.fromPalette(new MaterialDynamicColors$$ExternalSyntheticLambda5(15), new MaterialDynamicColors$$ExternalSyntheticLambda5(16), new MaterialDynamicColors$$ExternalSyntheticLambda4(materialDynamicColors, 6), null).getArgb(this.mDynamicSchemeLight)) {
                    z2 = false;
                    break;
                }
            }
            if (z2) {
                Log.d("ThemeOverlayController", "Skipping overlay creation. Theme was already: " + this.mColorScheme);
                return;
            }
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Applying overlays: "), (String) arrayMap.keySet().stream().map(new Function() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    Map map = arrayMap;
                    String str2 = (String) obj;
                    StringBuilder m2m = AbstractC0000x2c234b15.m2m(str2, " -> ");
                    m2m.append(map.get(str2));
                    return m2m.toString();
                }
            }).collect(Collectors.joining(", ")), "ThemeOverlayController");
            boolean z4 = this.mNeedsOverlayCreation;
            ThemeOverlayApplier themeOverlayApplier = this.mThemeManager;
            if (!z4) {
                themeOverlayApplier.applyCurrentUserOverlays(arrayMap, null, userId, hashSet);
                return;
            }
            this.mNeedsOverlayCreation = false;
            if (isColorThemeEnabled$1()) {
                themeOverlayApplier.applyCurrentUserOverlays(arrayMap, new FabricatedOverlay[]{this.mDynamicOverlay}, userId, hashSet);
            } else {
                themeOverlayApplier.applyCurrentUserOverlays(arrayMap, new FabricatedOverlay[]{this.mSecondaryOverlay, this.mNeutralOverlay, this.mDynamicOverlay}, userId, hashSet);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda4, java.lang.Runnable] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.d("ThemeOverlayController", "Start");
        final ?? r0 = new Runnable() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                final ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                int userId = ((UserTrackerImpl) themeOverlayController.mUserTracker).getUserId();
                WallpaperManager wallpaperManager = themeOverlayController.mWallpaperManager;
                final WallpaperColors wallpaperColors = wallpaperManager.getWallpaperColors(wallpaperManager.getWallpaperIdForUser(2, userId) <= wallpaperManager.getWallpaperIdForUser(1, userId) ? 1 : 2);
                themeOverlayController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ThemeOverlayController themeOverlayController2 = ThemeOverlayController.this;
                        themeOverlayController2.mCurrentColors.put(((UserTrackerImpl) themeOverlayController2.mUserTracker).getUserId(), wallpaperColors);
                        themeOverlayController2.reevaluateSystemTheme(true);
                    }
                });
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
        intentFilter.addAction("android.intent.action.WALLPAPER_CHANGED");
        UserHandle userHandle = UserHandle.ALL;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        C34824 c34824 = this.mBroadcastReceiver;
        Executor executor = this.mMainExecutor;
        broadcastDispatcher.registerReceiver(c34824, intentFilter, executor, userHandle);
        this.mSecureSettings.registerContentObserverForUser("theme_customization_overlay_packages", false, new ContentObserver(this.mBgHandler) { // from class: com.android.systemui.theme.ThemeOverlayController.5
            public final void onChange(boolean z, Collection collection, int i, int i2) {
                if (TextUtils.isEmpty(((SecureSettingsImpl) ThemeOverlayController.this.mSecureSettings).getStringForUser(i2, "theme_customization_overlay_packages"))) {
                    return;
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("Overlay changed for user: ", i2, "ThemeOverlayController");
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
        float floatForUser = Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "contrast_level", -1.0f, -2);
        UiModeManager uiModeManager = this.mUiModeManager;
        if (floatForUser != -1.0f) {
            this.mContrast = uiModeManager.getContrast();
        }
        uiModeManager.addContrastChangeListener(executor, new UiModeManager.ContrastChangeListener() { // from class: com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda5
            @Override // android.app.UiModeManager.ContrastChangeListener
            public final void onContrastChanged(float f) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                Runnable runnable = r0;
                themeOverlayController.mContrast = f;
                if (f == -1.0f) {
                    return;
                }
                themeOverlayController.mBgExecutor.execute(runnable);
            }
        });
        this.mWallpaperManager.addOnColorsChangedListener(this.mOnColorsChangedListener, null, -1);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserTrackerCallback, executor);
    }
}
