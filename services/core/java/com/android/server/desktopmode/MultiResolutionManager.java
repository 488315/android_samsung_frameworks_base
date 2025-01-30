package com.android.server.desktopmode;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Point;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import com.android.server.desktopmode.SettingsHelper;
import com.android.server.desktopmode.StateManager;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public class MultiResolutionManager {
    public static final Resolution DUAL_MODE_DEFAULT;
    public static final Resolution FHD;

    /* renamed from: HD */
    public static final Resolution f1673HD;
    public static final Resolution HIGHEST_RESOLUTION;
    public static final Resolution LOWEST_RESOLUTION;
    public static final Map RESOLUTION_TABLE;
    public static final String TAG = "[DMS]" + MultiResolutionManager.class.getSimpleName();
    public static final Resolution UHD;
    public static final Resolution UWFHD;
    public static final Resolution UWQHD;
    public static final Resolution WQHD;
    public static final Resolution WQXGA;
    public static final Resolution WUXGA;
    public final ActivityTaskManagerInternal mAtmInternal;
    public final Context mContext;
    public DisplayMetrics mCustomDisplayMetrics;
    public Resolution mLastDualModeMaxSupportedResolution;
    public Resolution mMaxSupportedResolution;
    public final ContentResolver mResolver;
    public DisplayMetrics mSelectedDisplayMetrics;
    public final SettingsHelper mSettingsHelper;
    public StandaloneModeDisplayMetrics mStandaloneModeDisplayMetrics;
    public final StateManager.StateListener mStateListener;
    public final IStateManager mStateManager;
    public Resolution mUserSettingResolution;
    public final WindowManagerService mWindowManager;
    public final WindowManagerInternal mWindowManagerInternal;
    public boolean mIsForcedSupportAllResolution = false;
    public boolean mDisplayRemovedOnEnablingDesktopMode = false;
    public final SettingsHelper.OnSettingChangedListener mUserSettingResolutionChangedListener = new SettingsHelper.OnSettingChangedListener("resolution_user_setting") { // from class: com.android.server.desktopmode.MultiResolutionManager.1
        @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
        public void onSettingChanged(String str, int i) {
            if (MultiResolutionManager.this.mStateManager.getState().isDexOnPcOrWirelessDexConnected()) {
                return;
            }
            if (str == null) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m8e(MultiResolutionManager.TAG, "Resolution user setting is null.");
                }
                MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                multiResolutionManager.setSelectedDisplayMetrics(multiResolutionManager.mMaxSupportedResolution, MultiResolutionManager.this.mSelectedDisplayMetrics.density);
                return;
            }
            Resolution resolution = (Resolution) MultiResolutionManager.RESOLUTION_TABLE.get(str);
            MultiResolutionManager multiResolutionManager2 = MultiResolutionManager.this;
            if (!resolution.isSupportedOn(multiResolutionManager2.mMaxSupportedResolution)) {
                resolution = MultiResolutionManager.this.mMaxSupportedResolution;
            }
            multiResolutionManager2.setSelectedDisplayMetrics(resolution, MultiResolutionManager.this.mSelectedDisplayMetrics.density);
        }
    };
    public final SettingsHelper.OnSettingChangedListener mDualModeDensityChangedListener = new SettingsHelper.OnSettingChangedListener("dual_mode_screen_zoom") { // from class: com.android.server.desktopmode.MultiResolutionManager.2
        @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
        public void onSettingChanged(String str, int i) {
            MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
            multiResolutionManager.setSelectedDisplayMetrics(multiResolutionManager.mSelectedDisplayMetrics.resolution, str == null ? 160 : Integer.parseInt(str));
        }
    };
    public final SettingsHelper.OnSettingChangedListener mStandaloneModeDensityChangedListener = new SettingsHelper.OnSettingChangedListener("standalone_mode_screen_zoom") { // from class: com.android.server.desktopmode.MultiResolutionManager.3
        @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
        public void onSettingChanged(String str, int i) {
            MultiResolutionManager.this.mStandaloneModeDisplayMetrics.setSelectedDensity(str == null ? -1 : Integer.parseInt(str));
        }
    };

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        RESOLUTION_TABLE = linkedHashMap;
        Resolution resolution = new Resolution("UHD", 3840, 2160);
        UHD = resolution;
        Resolution resolution2 = new Resolution("UWQHD", 3440, 1440);
        UWQHD = resolution2;
        Resolution resolution3 = new Resolution("WQXGA", 2560, 1600);
        WQXGA = resolution3;
        Resolution resolution4 = new Resolution("WQHD", 2560, 1440);
        WQHD = resolution4;
        Resolution resolution5 = new Resolution("UWFHD", 2560, 1080);
        UWFHD = resolution5;
        Resolution resolution6 = new Resolution("WUXGA", 1920, 1200);
        WUXGA = resolution6;
        Resolution resolution7 = new Resolution("FHD", 1920, 1080);
        FHD = resolution7;
        Resolution resolution8 = new Resolution("HD", 1600, 900);
        f1673HD = resolution8;
        DUAL_MODE_DEFAULT = resolution7;
        HIGHEST_RESOLUTION = resolution;
        LOWEST_RESOLUTION = resolution8;
        linkedHashMap.put(resolution.name, resolution);
        linkedHashMap.put(resolution2.name, resolution2);
        linkedHashMap.put(resolution3.name, resolution3);
        linkedHashMap.put(resolution4.name, resolution4);
        linkedHashMap.put(resolution5.name, resolution5);
        linkedHashMap.put(resolution6.name, resolution6);
        linkedHashMap.put(resolution7.name, resolution7);
        linkedHashMap.put(resolution8.name, resolution8);
    }

    public MultiResolutionManager(Context context, IStateManager iStateManager, SettingsHelper settingsHelper, ActivityTaskManagerInternal activityTaskManagerInternal, WindowManagerService windowManagerService, WindowManagerInternal windowManagerInternal) {
        StateManager.StateListener stateListener = new StateManager.StateListener() { // from class: com.android.server.desktopmode.MultiResolutionManager.4
            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onUserChanged(State state) {
                MultiResolutionManager.this.onUserChanged(state.getDesktopModeState().compareTo(4, 0, 102));
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onExternalDisplayUpdated(State state) {
                MultiResolutionManager.this.updateDisplayResolutionUnsupported(state.getConnectedDisplay());
                MultiResolutionManager multiResolutionManager = MultiResolutionManager.this;
                multiResolutionManager.handleReconnection(multiResolutionManager.mStateManager.getState());
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onSetDesktopModeInternal(boolean z) {
                if (z) {
                    MultiResolutionManager.this.mStandaloneModeDisplayMetrics.updateDesktopModeDensity();
                }
                MultiResolutionManager.this.mStandaloneModeDisplayMetrics.setForcedDisplayMertics(z);
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onExternalDisplayConnectionChanged(State state) {
                SemDesktopModeState desktopModeState = state.getDesktopModeState();
                MultiResolutionManager.this.updateDisplayResolutionUnsupported(state.getConnectedDisplay());
                if (state.isExternalDisplayConnected()) {
                    if (MultiResolutionManager.this.mDisplayRemovedOnEnablingDesktopMode) {
                        MultiResolutionManager.this.handleReconnection(state);
                        MultiResolutionManager.this.mDisplayRemovedOnEnablingDesktopMode = false;
                        return;
                    }
                    return;
                }
                int i = desktopModeState.enabled;
                if (((i != 4 || desktopModeState.state == 0) && i != 3) || state.getDesktopDisplayId() == -1) {
                    return;
                }
                MultiResolutionManager.this.mDisplayRemovedOnEnablingDesktopMode = true;
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onStartLoadingScreen(boolean z) {
                if (z) {
                    MultiResolutionManager.this.mSettingsHelper.registerListener(MultiResolutionManager.this.mStandaloneModeDensityChangedListener);
                } else {
                    MultiResolutionManager.this.mSettingsHelper.unregisterListener(MultiResolutionManager.this.mStandaloneModeDensityChangedListener);
                }
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onDualModeStartLoadingScreen(boolean z) {
                if (z) {
                    MultiResolutionManager.this.mSettingsHelper.registerListener(MultiResolutionManager.this.mUserSettingResolutionChangedListener);
                    MultiResolutionManager.this.mSettingsHelper.registerListener(MultiResolutionManager.this.mDualModeDensityChangedListener);
                } else {
                    MultiResolutionManager.this.mSettingsHelper.unregisterListener(MultiResolutionManager.this.mUserSettingResolutionChangedListener);
                    MultiResolutionManager.this.mSettingsHelper.unregisterListener(MultiResolutionManager.this.mDualModeDensityChangedListener);
                }
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onDualModeStopLoadingScreen(boolean z) {
                if (z) {
                    MultiResolutionManager.this.showUserSettingIsNotSupportedToast();
                }
                MultiResolutionManager.this.mDisplayRemovedOnEnablingDesktopMode = false;
            }
        };
        this.mStateListener = stateListener;
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mStateManager = iStateManager;
        iStateManager.registerListener(stateListener);
        this.mAtmInternal = activityTaskManagerInternal;
        this.mWindowManager = windowManagerService;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mSettingsHelper = settingsHelper;
        Resolution resolution = DUAL_MODE_DEFAULT;
        this.mSelectedDisplayMetrics = new DisplayMetrics(resolution, 160);
        this.mMaxSupportedResolution = resolution;
        if (DesktopModeFeature.SUPPORT_STANDALONE) {
            this.mStandaloneModeDisplayMetrics = new StandaloneModeDisplayMetrics();
        }
    }

    public int getStandaloneModeDensity() {
        return this.mStandaloneModeDisplayMetrics.getSelectedDisplayMetrics().density;
    }

    public int getStandaloneModeOriginalDensity(int i) {
        return this.mStandaloneModeDisplayMetrics.getOriginalDisplaySizeDensity(i).density;
    }

    public void onUserChanged(boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "onUserChanged(), " + z);
        }
        updateUserSettingDisplayMetrics();
        if (DesktopModeFeature.SUPPORT_STANDALONE) {
            this.mStandaloneModeDisplayMetrics.updateDesktopModeDensity();
        }
        State state = this.mStateManager.getState();
        if (z && state.isExternalDisplayConnected()) {
            updateMaxSupportedResolution(calculateMaxSupportedResolution(state.getConnectedDisplay()));
        }
    }

    public final DisplayMetrics getSelectedDisplayMetrics() {
        DisplayMetrics displayMetrics = this.mCustomDisplayMetrics;
        return displayMetrics != null ? displayMetrics : this.mSelectedDisplayMetrics;
    }

    public final void setSelectedDisplayMetrics(Resolution resolution, int i) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setSelectedDisplayMetrics: resolution=" + resolution + ", density=" + i);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics(resolution, i);
        if (this.mSelectedDisplayMetrics.equals(displayMetrics)) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "changed to: " + displayMetrics);
        }
        this.mSelectedDisplayMetrics = displayMetrics;
        State state = this.mStateManager.getState();
        if (state.getDesktopDisplayId() != -1) {
            this.mWindowManagerInternal.setDisplaySizeAndDensityInDex(state.getDesktopDisplayId(), getSelectedDisplayMetrics().resolution.width, getSelectedDisplayMetrics().resolution.height, getSelectedDisplayMetrics().density);
        }
    }

    public int setDualDisplayResolutionDensity(boolean z) {
        if (z) {
            updateUserSettingDisplayMetrics();
            updateDualDisplayMetrics();
            return this.mAtmInternal.enableDexDisplay(getSelectedDisplayMetrics().resolution.width, getSelectedDisplayMetrics().resolution.height, getSelectedDisplayMetrics().density);
        }
        return this.mAtmInternal.disableDexDisplay();
    }

    public final void updateDisplayResolutionUnsupported(DisplayInfo displayInfo) {
        this.mStateManager.setDisplayResolutionUnsupported(calculateMaxSupportedResolution(displayInfo, null) == null);
    }

    public final void showUserSettingIsNotSupportedToast() {
        if (this.mUserSettingResolution == null || this.mStateManager.getState().isDexOnPcOrWirelessDexConnected() || this.mUserSettingResolution.isSupportedOn(getSelectedDisplayMetrics().resolution)) {
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "showToastResolutionChanged()");
        }
        int desktopDisplayId = this.mStateManager.getState().getDesktopDisplayId();
        if (desktopDisplayId != -1) {
            ToastManager.showToast(Utils.getDisplayContext(this.mContext, desktopDisplayId), R.string.global_action_toggle_silent_mode);
        }
    }

    public final void updateDualDisplayMetrics() {
        Resolution resolution;
        DisplayInfo connectedDisplay = this.mStateManager.getState().getConnectedDisplay();
        if (connectedDisplay == null || this.mIsForcedSupportAllResolution) {
            if (this.mIsForcedSupportAllResolution) {
                updateSupportAllResolution();
            } else {
                updateMaxSupportedResolution(HIGHEST_RESOLUTION);
                this.mSelectedDisplayMetrics.setResolution(DUAL_MODE_DEFAULT);
            }
        } else {
            updateMaxSupportedResolution(calculateMaxSupportedResolution(connectedDisplay));
            if (this.mUserSettingResolution == null || this.mStateManager.getState().isDexOnPcOrWirelessDexConnected()) {
                resolution = this.mMaxSupportedResolution;
            } else {
                resolution = this.mUserSettingResolution.isSupportedOn(this.mMaxSupportedResolution) ? this.mUserSettingResolution : this.mMaxSupportedResolution;
            }
            this.mSelectedDisplayMetrics.setResolution(resolution);
            this.mLastDualModeMaxSupportedResolution = this.mMaxSupportedResolution;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "mSelectedDisplayMetrics= " + this.mSelectedDisplayMetrics);
        }
    }

    public final boolean isResolutionChangeNeeded(State state) {
        boolean z = true;
        if (this.mUserSettingResolution == null) {
            z = true ^ calculateMaxSupportedResolution(state.getConnectedDisplay()).equals(this.mLastDualModeMaxSupportedResolution);
        } else if (!calculateMaxSupportedResolution(state.getConnectedDisplay()).isSupportedOn(getSelectedDisplayMetrics().resolution)) {
            z = false;
        } else if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "This display does not support current resolution.");
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "isResolutionChangeNeeded: " + z);
        }
        return z;
    }

    public final void updateMaxSupportedResolution(DisplayInfo displayInfo) {
        updateMaxSupportedResolution(calculateMaxSupportedResolution(displayInfo));
    }

    public final void updateMaxSupportedResolution(Resolution resolution) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "updateMaxSupportedResolution: " + resolution);
        }
        this.mMaxSupportedResolution = resolution;
        DesktopModeSettings.setSettings(this.mResolver, "resolution_max", resolution.name);
    }

    public final void updateSupportAllResolution() {
        this.mSelectedDisplayMetrics.setResolution(HIGHEST_RESOLUTION);
        DesktopModeSettings.setSettings(this.mResolver, "resolution_max", "ADB");
    }

    public final void updateUserSettingDisplayMetrics() {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "updateUserSettingDisplayMetrics()");
        }
        this.mUserSettingResolution = (Resolution) RESOLUTION_TABLE.get(DesktopModeSettings.getSettings(this.mResolver, "resolution_user_setting", (String) null));
        this.mSelectedDisplayMetrics.setDensity(DesktopModeSettings.getSettings(this.mResolver, "dual_mode_screen_zoom", 160));
    }

    public final Resolution calculateMaxSupportedResolution(DisplayInfo displayInfo) {
        return calculateMaxSupportedResolution(displayInfo, LOWEST_RESOLUTION);
    }

    public final Resolution calculateMaxSupportedResolution(DisplayInfo displayInfo, Resolution resolution) {
        int i;
        int i2;
        if (displayInfo != null) {
            Point realSize = displayInfo.getRealSize();
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "calculateMaxSupportedResolution(), displayInfo=" + displayInfo);
            }
            if ((displayInfo.getRotation() == 1 || displayInfo.getRotation() == 3) && (i = realSize.y) > (i2 = realSize.x)) {
                realSize.set(i, i2);
            }
            Iterator it = RESOLUTION_TABLE.entrySet().iterator();
            while (it.hasNext()) {
                Resolution resolution2 = (Resolution) ((Map.Entry) it.next()).getValue();
                if (realSize.x >= resolution2.width && realSize.y >= resolution2.height) {
                    return resolution2;
                }
            }
            return resolution;
        }
        return DUAL_MODE_DEFAULT;
    }

    public final void handleReconnection(State state) {
        int i = state.getDesktopModeState().enabled;
        if ((i == 4 || i == 3) && state.getDesktopDisplayId() != -1) {
            if (isResolutionChangeNeeded(state)) {
                setDualDisplayResolutionDensity(true);
                return;
            }
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "No need to change resolution");
            }
            if (this.mUserSettingResolution != null) {
                updateMaxSupportedResolution(state.getConnectedDisplay());
            }
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + MultiResolutionManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mIsForcedSupportAllResolution=" + this.mIsForcedSupportAllResolution);
        indentingPrintWriter.println("mDisplayRemovedOnEnablingDesktopMode=" + this.mDisplayRemovedOnEnablingDesktopMode);
        indentingPrintWriter.println("mStandaloneModeDisplayMetrics=" + this.mStandaloneModeDisplayMetrics);
        indentingPrintWriter.println("mCustomDisplayMetrics=" + this.mCustomDisplayMetrics);
        indentingPrintWriter.println("mUserSettingResolution=" + this.mUserSettingResolution);
        indentingPrintWriter.println("mMaxSupportedResolution=" + this.mMaxSupportedResolution);
        indentingPrintWriter.println("mSelectedDisplayMetrics=" + this.mSelectedDisplayMetrics);
        indentingPrintWriter.println("mLastDualModeMaxSupportedResolution=" + this.mLastDualModeMaxSupportedResolution);
        indentingPrintWriter.decreaseIndent();
    }

    public DisplayMetrics getCustomDisplayMetrics() {
        return this.mCustomDisplayMetrics;
    }

    public void setCustomResolutionFromAdbCommand(PrintWriter printWriter, int i, int i2, int i3) {
        try {
            this.mCustomDisplayMetrics = new DisplayMetrics(new Resolution("ADB", i, i2), i3);
            printWriter.println("Successfully set custom resolution");
        } catch (IllegalArgumentException unused) {
            this.mCustomDisplayMetrics = null;
            printWriter.println("Failed to set custom resolution. Width, height, and density must be greater than 0. Custom resolution is removed.");
        }
    }

    public void setSupportAllResolution(boolean z) {
        this.mIsForcedSupportAllResolution = z;
    }

    public class Resolution {
        public int height;
        public String name;
        public int width;

        public Resolution(String str, int i, int i2) {
            if (i <= 0 || i2 <= 0) {
                throw new IllegalArgumentException();
            }
            this.name = str;
            this.width = i;
            this.height = i2;
        }

        public boolean isSupportedOn(Resolution resolution) {
            return this.width <= resolution.width && this.height <= resolution.height;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Resolution resolution = (Resolution) obj;
            return this.width == resolution.width && this.height == resolution.height;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.width), Integer.valueOf(this.height));
        }

        public String toString() {
            return "Resolution(name=" + this.name + ", width=" + this.width + ", height=" + this.height + ")";
        }
    }

    public class DisplayMetrics {
        public int density;
        public Resolution resolution;

        public DisplayMetrics(Resolution resolution, int i) {
            if (resolution == null || i <= 0) {
                throw new IllegalArgumentException();
            }
            this.resolution = resolution;
            this.density = i;
        }

        public void setResolution(Resolution resolution) {
            this.resolution = resolution;
        }

        public void setDensity(int i) {
            this.density = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            DisplayMetrics displayMetrics = (DisplayMetrics) obj;
            return this.resolution.equals(displayMetrics.resolution) && this.density == displayMetrics.density;
        }

        public int hashCode() {
            return Objects.hash(this.resolution, Integer.valueOf(this.density));
        }

        public String toString() {
            return "DisplayMetrics(name=" + this.resolution.name + ", width=" + this.resolution.width + ", height=" + this.resolution.height + ", density=" + this.density + ")";
        }
    }

    public class StandaloneModeDisplayMetrics {
        public final int[] mDisplayProperties;
        public DisplayMetrics mSelectedDisplayMetrics;
        public final DisplayMetrics mTabletDefaultDisplayMetrics;

        public StandaloneModeDisplayMetrics() {
            int[] initialDisplayProperties = MultiResolutionManager.this.mWindowManagerInternal.getInitialDisplayProperties(0);
            this.mDisplayProperties = initialDisplayProperties;
            DisplayMetrics displayMetrics = new DisplayMetrics(new Resolution("Tablet", initialDisplayProperties[0], initialDisplayProperties[1]), getDesktopModeDefaultDensity());
            this.mTabletDefaultDisplayMetrics = displayMetrics;
            this.mSelectedDisplayMetrics = displayMetrics;
        }

        public final int getDesktopModeDefaultDensity() {
            int fixedZoomProperty = Utils.getFixedZoomProperty();
            if (fixedZoomProperty != 0) {
                Log.m7d(MultiResolutionManager.TAG, "getDesktopModeDefaultDensity(), dpi= " + fixedZoomProperty);
                return fixedZoomProperty;
            }
            int[] iArr = this.mDisplayProperties;
            return Math.max(iArr[0], iArr[1]) < 2560 ? 213 : 280;
        }

        public final int getDesktopModeDensity() {
            return DesktopModeSettings.getSettings(MultiResolutionManager.this.mResolver, "standalone_mode_screen_zoom", getDesktopModeDefaultDensity());
        }

        public final void updateDesktopModeDensity() {
            this.mSelectedDisplayMetrics.setDensity(getDesktopModeDensity());
        }

        public DisplayMetrics getSelectedDisplayMetrics() {
            return MultiResolutionManager.this.mCustomDisplayMetrics != null ? MultiResolutionManager.this.mCustomDisplayMetrics : this.mSelectedDisplayMetrics;
        }

        public final DisplayMetrics getOriginalDisplaySizeDensity(int i) {
            int[] initialDisplayProperties = MultiResolutionManager.this.mWindowManagerInternal.getInitialDisplayProperties(i);
            String string = Settings.Global.getString(MultiResolutionManager.this.mResolver, "display_size_forced");
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
                        Log.m9e(MultiResolutionManager.TAG, "Failed to parse previous forced display size", e);
                    }
                }
            } else if (DesktopModeFeature.DEBUG) {
                Log.m10i(MultiResolutionManager.TAG, "No previous forced display size. Use default size instead.");
            }
            try {
                int intForUser = Settings.Secure.getIntForUser(MultiResolutionManager.this.mResolver, "display_density_forced", 0);
                if (intForUser > 0) {
                    initialDisplayProperties[2] = intForUser;
                }
            } catch (Settings.SettingNotFoundException unused) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m10i(MultiResolutionManager.TAG, "No previous forced display density. Use default density instead.");
                }
            }
            return new DisplayMetrics(new Resolution("Original", initialDisplayProperties[0], initialDisplayProperties[1]), initialDisplayProperties[2]);
        }

        public final void setSamsungWindowManagerForcedDisplaySizeDensity(int i, int i2, int i3, int i4) {
            MultiResolutionManager.this.mWindowManagerInternal.setDisplaySizeAndDensityInDex(i, i2, i3, i4);
        }

        public final void restoreOriginalSizeDensity(int i) {
            DisplayMetrics originalDisplaySizeDensity = getOriginalDisplaySizeDensity(i);
            if (DesktopModeFeature.DEBUG) {
                Log.m10i(MultiResolutionManager.TAG, "Restoring display: " + originalDisplaySizeDensity);
            }
            Resolution resolution = originalDisplaySizeDensity.resolution;
            setSamsungWindowManagerForcedDisplaySizeDensity(i, resolution.width, resolution.height, originalDisplaySizeDensity.density);
        }

        public final void setForcedDisplayMertics(boolean z) {
            if (z) {
                DisplayMetrics selectedDisplayMetrics = getSelectedDisplayMetrics();
                Resolution resolution = selectedDisplayMetrics.resolution;
                setSamsungWindowManagerForcedDisplaySizeDensity(0, resolution.width, resolution.height, selectedDisplayMetrics.density);
                return;
            }
            restoreOriginalSizeDensity(0);
        }

        public final void setSelectedDensity(int i) {
            if (i == -1) {
                i = getDesktopModeDefaultDensity();
            }
            if (this.mSelectedDisplayMetrics.density != i) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(MultiResolutionManager.TAG, "changed to: " + i);
                }
                this.mSelectedDisplayMetrics.density = i;
                setForcedDisplayMertics(true);
            }
        }

        public String toString() {
            return "(mTabletDefaultDisplayMetrics=" + this.mTabletDefaultDisplayMetrics + ", mSelectedDisplayMetrics=" + this.mSelectedDisplayMetrics + ')';
        }
    }
}
