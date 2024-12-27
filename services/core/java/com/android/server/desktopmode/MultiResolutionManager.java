package com.android.server.desktopmode;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Point;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.provider.Settings;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerInternal;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.feature.SemFloatingFeature;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MultiResolutionManager {
    public static final Resolution DUAL_MODE_DEFAULT;
    public static final Resolution HIGHEST_RESOLUTION;
    public static final Resolution LOWEST_RESOLUTION;
    public static final Map RESOLUTION_TABLE;
    public final ActivityTaskManagerInternal mAtmInternal;
    public final Context mContext;
    public DisplayMetrics mCustomDisplayMetrics;
    public Resolution mLastDualModeMaxSupportedResolution;
    public Resolution mMaxSupportedResolution;
    public final ContentResolver mResolver;
    public DisplayMetrics mSelectedDisplayMetrics;
    public final SettingsHelper mSettingsHelper;
    public final StandaloneModeDisplayMetrics mStandaloneModeDisplayMetrics;
    public final AnonymousClass4 mStateListener;
    public final IStateManager mStateManager;
    public Resolution mUserSettingResolution;
    public final WindowManagerInternal mWindowManagerInternal;
    public boolean mIsForcedSupportAllResolution = false;
    public boolean mDisplayRemovedOnEnablingDesktopMode = false;
    public final AnonymousClass1 mUserSettingResolutionChangedListener =
            new SettingsHelper.OnSettingChangedListener(
                    this,
                    0) { // from class: com.android.server.desktopmode.MultiResolutionManager.1
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ MultiResolutionManager this$0;

                {
                    super("resolution_user_setting");
                    this.$r8$classId = r2;
                    switch (r2) {
                        case 1:
                            this.this$0 = this;
                            super("dual_mode_screen_zoom");
                            break;
                        case 2:
                            this.this$0 = this;
                            super("standalone_mode_screen_zoom");
                            break;
                        default:
                            this.this$0 = this;
                            break;
                    }
                }

                @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                public final void onSettingChanged(String str) {
                    MultiResolutionManager multiResolutionManager = this.this$0;
                    switch (this.$r8$classId) {
                        case 0:
                            if (!((StateManager) multiResolutionManager.mStateManager)
                                    .getState()
                                    .isDexOnPcOrWirelessDexConnected()) {
                                if (str != null) {
                                    Resolution resolution =
                                            (Resolution)
                                                    ((LinkedHashMap)
                                                                    MultiResolutionManager
                                                                            .RESOLUTION_TABLE)
                                                            .get(str);
                                    if (!resolution.isSupportedOn(
                                            multiResolutionManager.mMaxSupportedResolution)) {
                                        resolution = multiResolutionManager.mMaxSupportedResolution;
                                    }
                                    MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                            multiResolutionManager,
                                            resolution,
                                            multiResolutionManager.mSelectedDisplayMetrics.density);
                                    break;
                                } else {
                                    if (DesktopModeFeature.DEBUG) {
                                        Map map = MultiResolutionManager.RESOLUTION_TABLE;
                                        Log.e(
                                                "[DMS]MultiResolutionManager",
                                                "Resolution user setting is null.");
                                    }
                                    MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                            multiResolutionManager,
                                            multiResolutionManager.mMaxSupportedResolution,
                                            multiResolutionManager.mSelectedDisplayMetrics.density);
                                    break;
                                }
                            }
                            break;
                        case 1:
                            MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                    multiResolutionManager,
                                    multiResolutionManager.mSelectedDisplayMetrics.resolution,
                                    str == null ? 160 : Integer.parseInt(str));
                            break;
                        default:
                            StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                                    multiResolutionManager.mStandaloneModeDisplayMetrics;
                            int parseInt = str == null ? -1 : Integer.parseInt(str);
                            if (parseInt == -1) {
                                parseInt =
                                        standaloneModeDisplayMetrics.getDesktopModeDefaultDensity();
                            }
                            DisplayMetrics displayMetrics =
                                    standaloneModeDisplayMetrics.mSelectedDisplayMetrics;
                            if (displayMetrics.density != parseInt) {
                                if (DesktopModeFeature.DEBUG) {
                                    Map map2 = MultiResolutionManager.RESOLUTION_TABLE;
                                    DesktopModeService$$ExternalSyntheticOutline0.m(
                                            parseInt,
                                            "changed to: ",
                                            "[DMS]MultiResolutionManager");
                                }
                                displayMetrics.density = parseInt;
                                standaloneModeDisplayMetrics.setForcedDisplayMertics(true);
                                break;
                            }
                            break;
                    }
                }
            };
    public final AnonymousClass1 mDualModeDensityChangedListener =
            new SettingsHelper.OnSettingChangedListener(
                    this,
                    1) { // from class: com.android.server.desktopmode.MultiResolutionManager.1
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ MultiResolutionManager this$0;

                {
                    super("resolution_user_setting");
                    this.$r8$classId = r2;
                    switch (r2) {
                        case 1:
                            this.this$0 = this;
                            super("dual_mode_screen_zoom");
                            break;
                        case 2:
                            this.this$0 = this;
                            super("standalone_mode_screen_zoom");
                            break;
                        default:
                            this.this$0 = this;
                            break;
                    }
                }

                @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                public final void onSettingChanged(String str) {
                    MultiResolutionManager multiResolutionManager = this.this$0;
                    switch (this.$r8$classId) {
                        case 0:
                            if (!((StateManager) multiResolutionManager.mStateManager)
                                    .getState()
                                    .isDexOnPcOrWirelessDexConnected()) {
                                if (str != null) {
                                    Resolution resolution =
                                            (Resolution)
                                                    ((LinkedHashMap)
                                                                    MultiResolutionManager
                                                                            .RESOLUTION_TABLE)
                                                            .get(str);
                                    if (!resolution.isSupportedOn(
                                            multiResolutionManager.mMaxSupportedResolution)) {
                                        resolution = multiResolutionManager.mMaxSupportedResolution;
                                    }
                                    MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                            multiResolutionManager,
                                            resolution,
                                            multiResolutionManager.mSelectedDisplayMetrics.density);
                                    break;
                                } else {
                                    if (DesktopModeFeature.DEBUG) {
                                        Map map = MultiResolutionManager.RESOLUTION_TABLE;
                                        Log.e(
                                                "[DMS]MultiResolutionManager",
                                                "Resolution user setting is null.");
                                    }
                                    MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                            multiResolutionManager,
                                            multiResolutionManager.mMaxSupportedResolution,
                                            multiResolutionManager.mSelectedDisplayMetrics.density);
                                    break;
                                }
                            }
                            break;
                        case 1:
                            MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                    multiResolutionManager,
                                    multiResolutionManager.mSelectedDisplayMetrics.resolution,
                                    str == null ? 160 : Integer.parseInt(str));
                            break;
                        default:
                            StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                                    multiResolutionManager.mStandaloneModeDisplayMetrics;
                            int parseInt = str == null ? -1 : Integer.parseInt(str);
                            if (parseInt == -1) {
                                parseInt =
                                        standaloneModeDisplayMetrics.getDesktopModeDefaultDensity();
                            }
                            DisplayMetrics displayMetrics =
                                    standaloneModeDisplayMetrics.mSelectedDisplayMetrics;
                            if (displayMetrics.density != parseInt) {
                                if (DesktopModeFeature.DEBUG) {
                                    Map map2 = MultiResolutionManager.RESOLUTION_TABLE;
                                    DesktopModeService$$ExternalSyntheticOutline0.m(
                                            parseInt,
                                            "changed to: ",
                                            "[DMS]MultiResolutionManager");
                                }
                                displayMetrics.density = parseInt;
                                standaloneModeDisplayMetrics.setForcedDisplayMertics(true);
                                break;
                            }
                            break;
                    }
                }
            };
    public final AnonymousClass1 mStandaloneModeDensityChangedListener =
            new SettingsHelper.OnSettingChangedListener(
                    this,
                    2) { // from class: com.android.server.desktopmode.MultiResolutionManager.1
                public final /* synthetic */ int $r8$classId;
                public final /* synthetic */ MultiResolutionManager this$0;

                {
                    super("resolution_user_setting");
                    this.$r8$classId = r2;
                    switch (r2) {
                        case 1:
                            this.this$0 = this;
                            super("dual_mode_screen_zoom");
                            break;
                        case 2:
                            this.this$0 = this;
                            super("standalone_mode_screen_zoom");
                            break;
                        default:
                            this.this$0 = this;
                            break;
                    }
                }

                @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                public final void onSettingChanged(String str) {
                    MultiResolutionManager multiResolutionManager = this.this$0;
                    switch (this.$r8$classId) {
                        case 0:
                            if (!((StateManager) multiResolutionManager.mStateManager)
                                    .getState()
                                    .isDexOnPcOrWirelessDexConnected()) {
                                if (str != null) {
                                    Resolution resolution =
                                            (Resolution)
                                                    ((LinkedHashMap)
                                                                    MultiResolutionManager
                                                                            .RESOLUTION_TABLE)
                                                            .get(str);
                                    if (!resolution.isSupportedOn(
                                            multiResolutionManager.mMaxSupportedResolution)) {
                                        resolution = multiResolutionManager.mMaxSupportedResolution;
                                    }
                                    MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                            multiResolutionManager,
                                            resolution,
                                            multiResolutionManager.mSelectedDisplayMetrics.density);
                                    break;
                                } else {
                                    if (DesktopModeFeature.DEBUG) {
                                        Map map = MultiResolutionManager.RESOLUTION_TABLE;
                                        Log.e(
                                                "[DMS]MultiResolutionManager",
                                                "Resolution user setting is null.");
                                    }
                                    MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                            multiResolutionManager,
                                            multiResolutionManager.mMaxSupportedResolution,
                                            multiResolutionManager.mSelectedDisplayMetrics.density);
                                    break;
                                }
                            }
                            break;
                        case 1:
                            MultiResolutionManager.m415$$Nest$msetSelectedDisplayMetrics(
                                    multiResolutionManager,
                                    multiResolutionManager.mSelectedDisplayMetrics.resolution,
                                    str == null ? 160 : Integer.parseInt(str));
                            break;
                        default:
                            StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                                    multiResolutionManager.mStandaloneModeDisplayMetrics;
                            int parseInt = str == null ? -1 : Integer.parseInt(str);
                            if (parseInt == -1) {
                                parseInt =
                                        standaloneModeDisplayMetrics.getDesktopModeDefaultDensity();
                            }
                            DisplayMetrics displayMetrics =
                                    standaloneModeDisplayMetrics.mSelectedDisplayMetrics;
                            if (displayMetrics.density != parseInt) {
                                if (DesktopModeFeature.DEBUG) {
                                    Map map2 = MultiResolutionManager.RESOLUTION_TABLE;
                                    DesktopModeService$$ExternalSyntheticOutline0.m(
                                            parseInt,
                                            "changed to: ",
                                            "[DMS]MultiResolutionManager");
                                }
                                displayMetrics.density = parseInt;
                                standaloneModeDisplayMetrics.setForcedDisplayMertics(true);
                                break;
                            }
                            break;
                    }
                }
            };

    public final class DisplayMetrics {
        public int density;
        public Resolution resolution;

        public DisplayMetrics(Resolution resolution, int i) {
            if (resolution == null || i <= 0) {
                throw new IllegalArgumentException();
            }
            this.resolution = resolution;
            this.density = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || DisplayMetrics.class != obj.getClass()) {
                return false;
            }
            DisplayMetrics displayMetrics = (DisplayMetrics) obj;
            return this.resolution.equals(displayMetrics.resolution)
                    && this.density == displayMetrics.density;
        }

        public final int hashCode() {
            return Objects.hash(this.resolution, Integer.valueOf(this.density));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DisplayMetrics(name=");
            sb.append(this.resolution.name);
            sb.append(", width=");
            sb.append(this.resolution.width);
            sb.append(", height=");
            sb.append(this.resolution.height);
            sb.append(", density=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.density, sb, ")");
        }
    }

    public final class Resolution {
        public final int height;
        public final String name;
        public final int width;

        public Resolution(int i, int i2, String str) {
            if (i <= 0 || i2 <= 0) {
                throw new IllegalArgumentException();
            }
            this.name = str;
            this.width = i;
            this.height = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || Resolution.class != obj.getClass()) {
                return false;
            }
            Resolution resolution = (Resolution) obj;
            return this.width == resolution.width && this.height == resolution.height;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.width), Integer.valueOf(this.height));
        }

        public final boolean isSupportedOn(Resolution resolution) {
            return this.width <= resolution.width && this.height <= resolution.height;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Resolution(name=");
            sb.append(this.name);
            sb.append(", width=");
            sb.append(this.width);
            sb.append(", height=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.height, sb, ")");
        }
    }

    public final class StandaloneModeDisplayMetrics {
        public final int[] mDisplayProperties;
        public final DisplayMetrics mSelectedDisplayMetrics;
        public final DisplayMetrics mTabletDefaultDisplayMetrics;

        public StandaloneModeDisplayMetrics() {
            int[] initialDisplayProperties =
                    MultiResolutionManager.this.mWindowManagerInternal.getInitialDisplayProperties(
                            0);
            this.mDisplayProperties = initialDisplayProperties;
            DisplayMetrics displayMetrics =
                    new DisplayMetrics(
                            new Resolution(
                                    initialDisplayProperties[0],
                                    initialDisplayProperties[1],
                                    "Tablet"),
                            getDesktopModeDefaultDensity());
            this.mTabletDefaultDisplayMetrics = displayMetrics;
            this.mSelectedDisplayMetrics = displayMetrics;
        }

        public final int getDesktopModeDefaultDensity() {
            int i;
            List of =
                    List.of(
                            (Object[])
                                    SemFloatingFeature.getInstance()
                                            .getString(
                                                    "SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE")
                                            .split(","));
            int i2 = 0;
            while (true) {
                if (i2 >= of.size()) {
                    i = 0;
                    break;
                }
                String str = (String) of.get(i2);
                if (str.contains("fixedzoom")) {
                    Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(str);
                    if (matcher.find()) {
                        i = Integer.parseInt(matcher.group(1));
                        break;
                    }
                }
                i2++;
            }
            if (i == 0) {
                int[] iArr = this.mDisplayProperties;
                return Math.max(iArr[0], iArr[1]) < 2560 ? 213 : 280;
            }
            Map map = MultiResolutionManager.RESOLUTION_TABLE;
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    i, "getDesktopModeDefaultDensity(), dpi= ", "[DMS]MultiResolutionManager");
            return i;
        }

        public final DisplayMetrics getOriginalDisplaySizeDensity() {
            MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
            int[] initialDisplayProperties =
                    multiResolutionManager.mWindowManagerInternal.getInitialDisplayProperties(0);
            String string =
                    Settings.Global.getString(
                            multiResolutionManager.mResolver, "display_size_forced");
            if (string != null && string.length() > 0) {
                int indexOf = string.indexOf(44);
                if (indexOf > 0 && string.lastIndexOf(44) == indexOf) {
                    try {
                        int parseInt = Integer.parseInt(string.substring(0, indexOf));
                        int parseInt2 = Integer.parseInt(string.substring(indexOf + 1));
                        if (parseInt > 0 && parseInt2 > 0) {
                            initialDisplayProperties[0] = parseInt;
                            initialDisplayProperties[1] = parseInt2;
                        }
                    } catch (NumberFormatException e) {
                        Map map = MultiResolutionManager.RESOLUTION_TABLE;
                        Log.e(
                                "[DMS]MultiResolutionManager",
                                "Failed to parse previous forced display size",
                                e);
                    }
                }
            } else if (DesktopModeFeature.DEBUG) {
                Map map2 = MultiResolutionManager.RESOLUTION_TABLE;
                Log.i(
                        "[DMS]MultiResolutionManager",
                        "No previous forced display size. Use default size instead.");
            }
            try {
                int intForUser =
                        Settings.Secure.getIntForUser(
                                multiResolutionManager.mResolver, "display_density_forced", 0);
                if (intForUser > 0) {
                    initialDisplayProperties[2] = intForUser;
                }
            } catch (Settings.SettingNotFoundException unused) {
                if (DesktopModeFeature.DEBUG) {
                    Map map3 = MultiResolutionManager.RESOLUTION_TABLE;
                    Log.i(
                            "[DMS]MultiResolutionManager",
                            "No previous forced display density. Use default density instead.");
                }
            }
            return new DisplayMetrics(
                    new Resolution(
                            initialDisplayProperties[0], initialDisplayProperties[1], "Original"),
                    initialDisplayProperties[2]);
        }

        public final void setForcedDisplayMertics(boolean z) {
            MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
            if (z) {
                DisplayMetrics displayMetrics = multiResolutionManager.mCustomDisplayMetrics;
                if (displayMetrics == null) {
                    displayMetrics = this.mSelectedDisplayMetrics;
                }
                Resolution resolution = displayMetrics.resolution;
                multiResolutionManager.mWindowManagerInternal.setDisplaySizeAndDensityInDex(
                        0, resolution.width, resolution.height, displayMetrics.density);
                return;
            }
            DisplayMetrics originalDisplaySizeDensity = getOriginalDisplaySizeDensity();
            if (DesktopModeFeature.DEBUG) {
                Map map = MultiResolutionManager.RESOLUTION_TABLE;
                Log.i(
                        "[DMS]MultiResolutionManager",
                        "Restoring display: " + originalDisplaySizeDensity);
            }
            Resolution resolution2 = originalDisplaySizeDensity.resolution;
            multiResolutionManager.mWindowManagerInternal.setDisplaySizeAndDensityInDex(
                    0, resolution2.width, resolution2.height, originalDisplaySizeDensity.density);
        }

        public final String toString() {
            return "(mTabletDefaultDisplayMetrics="
                    + this.mTabletDefaultDisplayMetrics
                    + ", mSelectedDisplayMetrics="
                    + this.mSelectedDisplayMetrics
                    + ')';
        }
    }

    /* renamed from: -$$Nest$mhandleReconnection, reason: not valid java name */
    public static void m414$$Nest$mhandleReconnection(
            MultiResolutionManager multiResolutionManager,
            StateManager.InternalState internalState) {
        boolean z;
        multiResolutionManager.getClass();
        int i = internalState.mDesktopModeState.enabled;
        if ((i == 4 || i == 3) && internalState.mDesktopDisplayId != -1) {
            Resolution resolution = multiResolutionManager.mUserSettingResolution;
            Resolution resolution2 = LOWEST_RESOLUTION;
            if (resolution == null) {
                z =
                        !calculateMaxSupportedResolution(
                                        internalState.mConnectedDisplay, resolution2)
                                .equals(multiResolutionManager.mLastDualModeMaxSupportedResolution);
            } else if (calculateMaxSupportedResolution(internalState.mConnectedDisplay, resolution2)
                    .isSupportedOn(multiResolutionManager.getSelectedDisplayMetrics().resolution)) {
                if (DesktopModeFeature.DEBUG) {
                    Log.d(
                            "[DMS]MultiResolutionManager",
                            "This display does not support current resolution.");
                }
                z = true;
            } else {
                z = false;
            }
            boolean z2 = DesktopModeFeature.DEBUG;
            if (z2) {
                Log.d("[DMS]MultiResolutionManager", "isResolutionChangeNeeded: " + z);
            }
            if (z) {
                multiResolutionManager.setDualDisplayResolutionDensity(true);
                return;
            }
            if (z2) {
                Log.d("[DMS]MultiResolutionManager", "No need to change resolution");
            }
            if (multiResolutionManager.mUserSettingResolution != null) {
                multiResolutionManager.updateMaxSupportedResolution(
                        calculateMaxSupportedResolution(
                                internalState.mConnectedDisplay, resolution2));
            }
        }
    }

    /* renamed from: -$$Nest$msetSelectedDisplayMetrics, reason: not valid java name */
    public static void m415$$Nest$msetSelectedDisplayMetrics(
            MultiResolutionManager multiResolutionManager, Resolution resolution, int i) {
        multiResolutionManager.getClass();
        boolean z = DesktopModeFeature.DEBUG;
        if (z) {
            Log.d(
                    "[DMS]MultiResolutionManager",
                    "setSelectedDisplayMetrics: resolution=" + resolution + ", density=" + i);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics(resolution, i);
        if (multiResolutionManager.mSelectedDisplayMetrics.equals(displayMetrics)) {
            return;
        }
        if (z) {
            Log.d("[DMS]MultiResolutionManager", "changed to: " + displayMetrics);
        }
        multiResolutionManager.mSelectedDisplayMetrics = displayMetrics;
        int i2 = ((StateManager) multiResolutionManager.mStateManager).getState().mDesktopDisplayId;
        if (i2 != -1) {
            multiResolutionManager.mWindowManagerInternal.setDisplaySizeAndDensityInDex(
                    i2,
                    multiResolutionManager.getSelectedDisplayMetrics().resolution.width,
                    multiResolutionManager.getSelectedDisplayMetrics().resolution.height,
                    multiResolutionManager.getSelectedDisplayMetrics().density);
        }
    }

    /* renamed from: -$$Nest$mupdateDisplayResolutionUnsupported, reason: not valid java name */
    public static void m416$$Nest$mupdateDisplayResolutionUnsupported(
            MultiResolutionManager multiResolutionManager, DisplayInfo displayInfo) {
        multiResolutionManager.getClass();
        boolean z = calculateMaxSupportedResolution(displayInfo, null) == null;
        StateManager stateManager = (StateManager) multiResolutionManager.mStateManager;
        stateManager.getClass();
        if (DesktopModeFeature.DEBUG) {
            DesktopModeService$$ExternalSyntheticOutline0.m(
                    "setDisplayResolutionUnsupported(unsupported=", ")", "[DMS]StateManager", z);
        }
        synchronized (stateManager.mLock) {
            try {
                if (stateManager.mInternalState.mDisplayResolutionUnsupported != z) {
                    stateManager.mInternalState.mDisplayResolutionUnsupported = z;
                    stateManager.copyInternalStateLocked(stateManager.mInternalState);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        RESOLUTION_TABLE = linkedHashMap;
        Resolution resolution = new Resolution(3840, 2160, "UHD");
        Resolution resolution2 = new Resolution(3440, 1440, "UWQHD");
        Resolution resolution3 = new Resolution(2560, 1600, "WQXGA");
        Resolution resolution4 = new Resolution(2560, 1440, "WQHD");
        Resolution resolution5 = new Resolution(2560, 1080, "UWFHD");
        Resolution resolution6 = new Resolution(1920, 1200, "WUXGA");
        Resolution resolution7 = new Resolution(1920, 1080, "FHD");
        Resolution resolution8 =
                new Resolution(
                        1600, FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT, "HD");
        DUAL_MODE_DEFAULT = resolution7;
        HIGHEST_RESOLUTION = resolution;
        LOWEST_RESOLUTION = resolution8;
        linkedHashMap.put("UHD", resolution);
        linkedHashMap.put("UWQHD", resolution2);
        linkedHashMap.put("WQXGA", resolution3);
        linkedHashMap.put("WQHD", resolution4);
        linkedHashMap.put("UWFHD", resolution5);
        linkedHashMap.put("WUXGA", resolution6);
        linkedHashMap.put("FHD", resolution7);
        linkedHashMap.put("HD", resolution8);
    }

    public MultiResolutionManager(
            Context context,
            IStateManager iStateManager,
            SettingsHelper settingsHelper,
            ActivityTaskManagerInternal activityTaskManagerInternal,
            WindowManagerInternal windowManagerInternal) {
        StateManager.StateListener stateListener =
                new StateManager
                        .StateListener() { // from class:
                                           // com.android.server.desktopmode.MultiResolutionManager.4
                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDualModeStartLoadingScreen(boolean z) {
                        MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                        if (z) {
                            multiResolutionManager.mSettingsHelper.registerListener(
                                    multiResolutionManager.mUserSettingResolutionChangedListener);
                            multiResolutionManager.mSettingsHelper.registerListener(
                                    multiResolutionManager.mDualModeDensityChangedListener);
                        } else {
                            multiResolutionManager.mSettingsHelper.unregisterListener(
                                    multiResolutionManager.mUserSettingResolutionChangedListener);
                            multiResolutionManager.mSettingsHelper.unregisterListener(
                                    multiResolutionManager.mDualModeDensityChangedListener);
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDualModeStopLoadingScreen(boolean z) {
                        MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                        if (z && multiResolutionManager.mUserSettingResolution != null) {
                            StateManager stateManager =
                                    (StateManager) multiResolutionManager.mStateManager;
                            if (!stateManager.getState().isDexOnPcOrWirelessDexConnected()
                                    && !multiResolutionManager.mUserSettingResolution.isSupportedOn(
                                            multiResolutionManager.getSelectedDisplayMetrics()
                                                    .resolution)) {
                                if (DesktopModeFeature.DEBUG) {
                                    Log.d(
                                            "[DMS]MultiResolutionManager",
                                            "showToastResolutionChanged()");
                                }
                                int i = stateManager.getState().mDesktopDisplayId;
                                if (i != -1) {
                                    Context displayContext =
                                            Utils.getDisplayContext(
                                                    multiResolutionManager.mContext, i);
                                    List list = ToastManager.sToasts;
                                    ToastManager.showToast(
                                            displayContext,
                                            displayContext.getString(
                                                    R.string.httpErrorRedirectLoop),
                                            1);
                                }
                            }
                        }
                        multiResolutionManager.mDisplayRemovedOnEnablingDesktopMode = false;
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onExternalDisplayConnectionChanged(
                            StateManager.InternalState internalState) {
                        SemDesktopModeState semDesktopModeState = internalState.mDesktopModeState;
                        DisplayInfo displayInfo = internalState.mConnectedDisplay;
                        MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                        MultiResolutionManager.m416$$Nest$mupdateDisplayResolutionUnsupported(
                                multiResolutionManager, displayInfo);
                        if (internalState.mIsExternalDisplayConnected) {
                            if (multiResolutionManager.mDisplayRemovedOnEnablingDesktopMode) {
                                MultiResolutionManager.m414$$Nest$mhandleReconnection(
                                        multiResolutionManager, internalState);
                                multiResolutionManager.mDisplayRemovedOnEnablingDesktopMode = false;
                                return;
                            }
                            return;
                        }
                        int i = semDesktopModeState.enabled;
                        if (((i != 4 || semDesktopModeState.state == 0) && i != 3)
                                || internalState.mDesktopDisplayId == -1) {
                            return;
                        }
                        multiResolutionManager.mDisplayRemovedOnEnablingDesktopMode = true;
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onExternalDisplayUpdated(
                            StateManager.InternalState internalState) {
                        DisplayInfo displayInfo = internalState.mConnectedDisplay;
                        MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                        MultiResolutionManager.m416$$Nest$mupdateDisplayResolutionUnsupported(
                                multiResolutionManager, displayInfo);
                        MultiResolutionManager.m414$$Nest$mhandleReconnection(
                                multiResolutionManager,
                                ((StateManager) multiResolutionManager.mStateManager).getState());
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onSetDesktopModeInternal(boolean z) {
                        MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                        if (z) {
                            StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                                    multiResolutionManager.mStandaloneModeDisplayMetrics;
                            standaloneModeDisplayMetrics.mSelectedDisplayMetrics.density =
                                    DesktopModeSettings.getSettingsAsUser(
                                            MultiResolutionManager.this.mResolver,
                                            "standalone_mode_screen_zoom",
                                            standaloneModeDisplayMetrics
                                                    .getDesktopModeDefaultDensity(),
                                            DesktopModeSettings.sCurrentUserId);
                        }
                        multiResolutionManager.mStandaloneModeDisplayMetrics
                                .setForcedDisplayMertics(z);
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onStartLoadingScreen(boolean z) {
                        MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                        if (z) {
                            multiResolutionManager.mSettingsHelper.registerListener(
                                    multiResolutionManager.mStandaloneModeDensityChangedListener);
                        } else {
                            multiResolutionManager.mSettingsHelper.unregisterListener(
                                    multiResolutionManager.mStandaloneModeDensityChangedListener);
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onUserChanged(StateManager.InternalState internalState) {
                        boolean compareTo = internalState.mDesktopModeState.compareTo(4, 0, 102);
                        MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                        multiResolutionManager.getClass();
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("[DMS]MultiResolutionManager", "onUserChanged(), " + compareTo);
                        }
                        multiResolutionManager.updateUserSettingDisplayMetrics();
                        if (DesktopModeFeature.SUPPORT_STANDALONE) {
                            StandaloneModeDisplayMetrics standaloneModeDisplayMetrics =
                                    multiResolutionManager.mStandaloneModeDisplayMetrics;
                            standaloneModeDisplayMetrics.mSelectedDisplayMetrics.density =
                                    DesktopModeSettings.getSettingsAsUser(
                                            MultiResolutionManager.this.mResolver,
                                            "standalone_mode_screen_zoom",
                                            standaloneModeDisplayMetrics
                                                    .getDesktopModeDefaultDensity(),
                                            DesktopModeSettings.sCurrentUserId);
                        }
                        StateManager.InternalState state =
                                ((StateManager) multiResolutionManager.mStateManager).getState();
                        if (compareTo && state.mIsExternalDisplayConnected) {
                            multiResolutionManager.updateMaxSupportedResolution(
                                    MultiResolutionManager.calculateMaxSupportedResolution(
                                            state.mConnectedDisplay,
                                            MultiResolutionManager.LOWEST_RESOLUTION));
                        }
                    }
                };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(stateListener);
        this.mAtmInternal = activityTaskManagerInternal;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mSettingsHelper = settingsHelper;
        Resolution resolution = DUAL_MODE_DEFAULT;
        this.mSelectedDisplayMetrics = new DisplayMetrics(resolution, 160);
        this.mMaxSupportedResolution = resolution;
        if (DesktopModeFeature.SUPPORT_STANDALONE) {
            this.mStandaloneModeDisplayMetrics = new StandaloneModeDisplayMetrics();
        }
    }

    public static Resolution calculateMaxSupportedResolution(
            DisplayInfo displayInfo, Resolution resolution) {
        int i;
        int i2;
        if (displayInfo == null) {
            return DUAL_MODE_DEFAULT;
        }
        Point point = new Point(displayInfo.mRealSize);
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]MultiResolutionManager",
                    "calculateMaxSupportedResolution(), displayInfo=" + displayInfo);
        }
        int i3 = displayInfo.mRotation;
        if ((i3 == 1 || i3 == 3) && (i = point.y) > (i2 = point.x)) {
            point.set(i, i2);
        }
        Iterator it = ((LinkedHashMap) RESOLUTION_TABLE).entrySet().iterator();
        while (it.hasNext()) {
            Resolution resolution2 = (Resolution) ((Map.Entry) it.next()).getValue();
            if (point.x >= resolution2.width && point.y >= resolution2.height) {
                return resolution2;
            }
        }
        return resolution;
    }

    public final DisplayMetrics getSelectedDisplayMetrics() {
        DisplayMetrics displayMetrics = this.mCustomDisplayMetrics;
        return displayMetrics != null ? displayMetrics : this.mSelectedDisplayMetrics;
    }

    public final int setDualDisplayResolutionDensity(boolean z) {
        long clearCallingIdentity;
        ActivityTaskManagerInternal activityTaskManagerInternal = this.mAtmInternal;
        if (!z) {
            ActivityTaskManagerService activityTaskManagerService = ActivityTaskManagerService.this;
            activityTaskManagerService.mAmInternal.enforceCallingPermission(
                    "android.permission.MANAGE_ACTIVITY_TASKS", "disableDexDisplay");
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return activityTaskManagerService.mDexController.disableDexDisplay();
            } finally {
            }
        }
        updateUserSettingDisplayMetrics();
        StateManager stateManager = (StateManager) this.mStateManager;
        DisplayInfo displayInfo = stateManager.getState().mConnectedDisplay;
        if (displayInfo == null || this.mIsForcedSupportAllResolution) {
            boolean z2 = this.mIsForcedSupportAllResolution;
            Resolution resolution = HIGHEST_RESOLUTION;
            if (z2) {
                this.mSelectedDisplayMetrics.resolution = resolution;
                DesktopModeSettings.setSettings(this.mResolver, "resolution_max", "ADB");
            } else {
                updateMaxSupportedResolution(resolution);
                this.mSelectedDisplayMetrics.resolution = DUAL_MODE_DEFAULT;
            }
        } else {
            updateMaxSupportedResolution(
                    calculateMaxSupportedResolution(displayInfo, LOWEST_RESOLUTION));
            this.mSelectedDisplayMetrics.resolution =
                    (this.mUserSettingResolution == null
                                    || stateManager.getState().isDexOnPcOrWirelessDexConnected())
                            ? this.mMaxSupportedResolution
                            : this.mUserSettingResolution.isSupportedOn(
                                            this.mMaxSupportedResolution)
                                    ? this.mUserSettingResolution
                                    : this.mMaxSupportedResolution;
            this.mLastDualModeMaxSupportedResolution = this.mMaxSupportedResolution;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]MultiResolutionManager",
                    "mSelectedDisplayMetrics= " + this.mSelectedDisplayMetrics);
        }
        int i = getSelectedDisplayMetrics().resolution.width;
        int i2 = getSelectedDisplayMetrics().resolution.height;
        int i3 = getSelectedDisplayMetrics().density;
        ActivityTaskManagerService activityTaskManagerService2 = ActivityTaskManagerService.this;
        activityTaskManagerService2.mAmInternal.enforceCallingPermission(
                "android.permission.MANAGE_ACTIVITY_TASKS", "enableDexDisplay");
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return activityTaskManagerService2.mDexController.enableDexDisplay(i, i2, i3);
        } finally {
        }
    }

    public final void updateMaxSupportedResolution(Resolution resolution) {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]MultiResolutionManager", "updateMaxSupportedResolution: " + resolution);
        }
        this.mMaxSupportedResolution = resolution;
        DesktopModeSettings.setSettings(this.mResolver, "resolution_max", resolution.name);
    }

    public final void updateUserSettingDisplayMetrics() {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]MultiResolutionManager", "updateUserSettingDisplayMetrics()");
        }
        this.mUserSettingResolution =
                (Resolution)
                        ((LinkedHashMap) RESOLUTION_TABLE)
                                .get(
                                        DesktopModeSettings.getSettingsAsUser(
                                                this.mResolver,
                                                "resolution_user_setting",
                                                (String) null,
                                                DesktopModeSettings.sCurrentUserId));
        this.mSelectedDisplayMetrics.density =
                DesktopModeSettings.getSettingsAsUser(
                        this.mResolver,
                        "dual_mode_screen_zoom",
                        160,
                        DesktopModeSettings.sCurrentUserId);
    }
}
