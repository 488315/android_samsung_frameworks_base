package android.hardware.display;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.Surface;

import com.android.internal.R;
import com.android.internal.display.BrightnessSynchronizer;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.rune.CoreRune;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class DisplayManager {
    public static final String ACTION_ROTATION_CHANGED =
            "com.samsung.intent.action.ROTATION_CHANGED";
    public static final String ACTION_WIFI_DISPLAY_STATUS_CHANGED =
            "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED";
    static final boolean DEBUG;
    public static final String DISPLAY_CATEGORY_ALL_INCLUDING_BUILT_IN =
            "android.hardware.display.category.ALL_INCLUDING_BUILT_IN";
    public static final String DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED =
            "android.hardware.display.category.ALL_INCLUDING_DISABLED";
    public static final String DISPLAY_CATEGORY_BACKGROUND_DISPLAY =
            "com.samsung.android.hardware.display.category.BACKGROUND_DISPLAY";
    public static final String DISPLAY_CATEGORY_BUILTIN =
            "com.samsung.android.hardware.display.category.BUILTIN";
    public static final String DISPLAY_CATEGORY_CARLIFE_DISPLAY =
            "com.samsung.android.hardware.display.category.CARLIFE_DISPLAY";
    public static final String DISPLAY_CATEGORY_DESKTOP =
            "com.samsung.android.hardware.display.category.DESKTOP";
    public static final String DISPLAY_CATEGORY_HIDDEN_SPACE_DISPLAY =
            "com.samsung.android.hardware.display.category.HIDDEN_SPACE_DISPLAY";
    public static final String DISPLAY_CATEGORY_PRESENTATION =
            "android.hardware.display.category.PRESENTATION";
    public static final String DISPLAY_CATEGORY_REAR = "android.hardware.display.category.REAR";
    public static final String DISPLAY_CATEGORY_REMOTE_APP_DISPLAY =
            "com.samsung.android.hardware.display.category.REMOTE_APP_DISPLAY";
    public static final String DISPLAY_CATEGORY_VIEW_COVER_DISPLAY =
            "com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY";
    private static final boolean ENABLE_VIRTUAL_DISPLAY_REFRESH_RATE = true;
    public static final long EVENT_FLAG_DISPLAY_ADDED = 1;
    public static final long EVENT_FLAG_DISPLAY_BRIGHTNESS = 8;
    public static final long EVENT_FLAG_DISPLAY_CHANGED = 4;
    public static final long EVENT_FLAG_DISPLAY_CONNECTION_CHANGED = 32;
    public static final long EVENT_FLAG_DISPLAY_REMOVED = 2;
    public static final long EVENT_FLAG_HDR_SDR_RATIO_CHANGED = 16;
    public static final String EXTRA_WIFI_DISPLAY_STATUS =
            "android.hardware.display.extra.WIFI_DISPLAY_STATUS";
    public static final String HDR_OUTPUT_CONTROL_FLAG = "enable_hdr_output_control";
    public static final int MATCH_CONTENT_FRAMERATE_ALWAYS = 2;
    public static final int MATCH_CONTENT_FRAMERATE_NEVER = 0;
    public static final int MATCH_CONTENT_FRAMERATE_SEAMLESSS_ONLY = 1;
    public static final int MATCH_CONTENT_FRAMERATE_UNKNOWN = -1;
    public static final String SEM_ACTION_DISCONNECT_LELINK_CAST =
            "com.samsung.intent.action.DISCONNECT_LELINK_CAST";
    public static final String SEM_ACTION_DLNA_STATUS_CHANGED =
            "com.samsung.intent.action.DLNA_STATUS_CHANGED";
    public static final String SEM_ACTION_SET_SCREEN_RATIO_VALUE =
            "com.samsung.intent.action.SET_SCREEN_RATIO_VALUE";
    public static final String SEM_ACTION_WIFI_DISPLAY_STATUS_CHANGED =
            "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED";
    public static final int SEM_CONNECT_STATE_CHANGEPLAYER_MUSIC = 8;
    public static final int SEM_CONNECT_STATE_NORMAL = -1;
    public static final String SEM_DISPLAY_CATEGORY_RUNTIME_MIRRORING_SWAP =
            "com.samsung.android.hardware.display.category.RUNTIME_MIRRORING_SWAP";
    public static final String SEM_EXTRA_DLNA_PLAYER_TYPE = "player_type";
    public static final String SEM_EXTRA_DLNA_STATUS = "status";
    public static final String SEM_EXTRA_WIFI_DISPLAY_STATUS =
            "android.hardware.display.extra.WIFI_DISPLAY_STATUS";
    public static final String SEM_PRESENTATION_START =
            "com.samsung.intent.action.SEC_PRESENTATION_START";
    public static final String SEM_PRESENTATION_START_SMARTVIEW =
            "com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW";
    public static final String SEM_PRESENTATION_STOP =
            "com.samsung.intent.action.SEC_PRESENTATION_STOP";
    public static final String SEM_PRESENTATION_STOP_SMARTVIEW =
            "com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW";
    public static final String SEM_WIFIDISPLAY_NOTI_CONNECTION_MODE =
            "com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE";
    public static final String SEM_WIFI_DISPLAY_SINK_STATE =
            "com.samsung.intent.action.WIFI_DISPLAY_SINK_STATE";
    public static final String SEM_WIFI_DISPLAY_SOURCE_STATE =
            "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE";
    public static final String SEM_WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED =
            "com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED";
    public static final String SPEG_DISPLAY_NAME = "SpegVirtualDisplay";
    public static final int SPEG_VIRTUAL_DISPLAY_FLAGS = 16777672;
    public static final boolean SUPPORT_SCREEN_SHARING_READY = false;
    public static final boolean SUPPORT_WFD_SERVICE = true;
    public static final int SWITCHING_TYPE_ACROSS_AND_WITHIN_GROUPS = 2;
    public static final int SWITCHING_TYPE_NONE = 0;
    public static final int SWITCHING_TYPE_RENDER_FRAME_RATE_ONLY = 3;
    public static final int SWITCHING_TYPE_WITHIN_GROUPS = 1;
    private static final String TAG = "DisplayManager";
    public static final String TAG_SPEG = "SPEG";
    public static final int VIRTUAL_DISPLAY_FLAG_ALWAYS_UNLOCKED = 4096;
    public static final int VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR = 16;
    public static final int VIRTUAL_DISPLAY_FLAG_BACKGROUND_DISPLAY = Integer.MIN_VALUE;
    public static final int VIRTUAL_DISPLAY_FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 32;
    public static final int VIRTUAL_DISPLAY_FLAG_CARLIFE = 1048576;
    public static final int VIRTUAL_DISPLAY_FLAG_DESKTOP = 262144;
    public static final int VIRTUAL_DISPLAY_FLAG_DESTROY_CONTENT_ON_REMOVAL = 256;
    public static final int VIRTUAL_DISPLAY_FLAG_DEVICE_DISPLAY_GROUP = 32768;
    public static final int VIRTUAL_DISPLAY_FLAG_HIDDEN_SPACE = 131072;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY = 8;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_DISPLAY_GROUP = 2048;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_FOCUS = 16384;
    public static final int VIRTUAL_DISPLAY_FLAG_PRESENTATION = 2;
    public static final int VIRTUAL_DISPLAY_FLAG_PUBLIC = 1;
    public static final int VIRTUAL_DISPLAY_FLAG_REMOTE_APP = 524288;

    @SystemApi public static final int VIRTUAL_DISPLAY_FLAG_ROTATES_WITH_CONTENT = 128;
    public static final int VIRTUAL_DISPLAY_FLAG_SECURE = 4;
    public static final int VIRTUAL_DISPLAY_FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 512;
    private static final int VIRTUAL_DISPLAY_FLAG_SPEG_DISPLAY = 16777216;

    @SystemApi public static final int VIRTUAL_DISPLAY_FLAG_STEAL_TOP_FOCUS_DISABLED = 65536;
    public static final int VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH = 64;
    public static final int VIRTUAL_DISPLAY_FLAG_TOUCH_FEEDBACK_DISABLED = 8192;

    @SystemApi public static final int VIRTUAL_DISPLAY_FLAG_TRUSTED = 1024;
    public static final int VIRTUAL_DISPLAY_FLAG_VIEW_COVER = 1073741824;
    public static final int VIRTUAL_DISPLAY_NO_LOCK_PRESENTATION = 2097152;
    private final Context mContext;
    private final Object mLock = new Object();
    private final WeakDisplayCache mDisplayCache = new WeakDisplayCache();
    private final DisplayManagerGlobal mGlobal = DisplayManagerGlobal.getInstance();

    public interface DeviceConfig {
        public static final String KEY_BRIGHTNESS_THROTTLING_DATA = "brightness_throttling_data";
        public static final String KEY_DISABLE_SCREEN_WAKE_LOCKS_WHILE_CACHED =
                "disable_screen_wake_locks_while_cached";
        public static final String KEY_FIXED_REFRESH_RATE_HIGH_AMBIENT_BRIGHTNESS_THRESHOLDS =
                "fixed_refresh_rate_high_ambient_brightness_thresholds";
        public static final String KEY_FIXED_REFRESH_RATE_HIGH_DISPLAY_BRIGHTNESS_THRESHOLDS =
                "fixed_refresh_rate_high_display_brightness_thresholds";
        public static final String KEY_FIXED_REFRESH_RATE_LOW_AMBIENT_BRIGHTNESS_THRESHOLDS =
                "peak_refresh_rate_ambient_thresholds";
        public static final String KEY_FIXED_REFRESH_RATE_LOW_DISPLAY_BRIGHTNESS_THRESHOLDS =
                "peak_refresh_rate_brightness_thresholds";
        public static final String KEY_HIGH_REFRESH_RATE_BLACKLIST = "high_refresh_rate_blacklist";
        public static final String KEY_PEAK_REFRESH_RATE_DEFAULT = "peak_refresh_rate_default";
        public static final String KEY_POWER_THROTTLING_DATA = "power_throttling_data";
        public static final String KEY_REFRESH_RATE_IN_HBM_HDR = "refresh_rate_in_hbm_hdr";
        public static final String KEY_REFRESH_RATE_IN_HBM_SUNLIGHT =
                "refresh_rate_in_hbm_sunlight";
        public static final String KEY_REFRESH_RATE_IN_HIGH_ZONE = "refresh_rate_in_high_zone";
        public static final String KEY_REFRESH_RATE_IN_LOW_ZONE = "refresh_rate_in_zone";
        public static final String KEY_USE_NORMAL_BRIGHTNESS_MODE_CONTROLLER =
                "use_normal_brightness_mode_controller";
    }

    public interface DisplayHbmBrightnessListener {
        void onChanged(int i, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventsMask {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface MatchContentFrameRateType {}

    public enum SemWifiDisplayAppState {
        SETUP,
        PAUSE,
        RESUME,
        TEARDOWN
    }

    public interface SemWifiDisplayConnectionCallback {
        public static final int REASON_NOT_DEFINED = 1;
        public static final int REASON_NO_HDCP_KEY = 3;
        public static final int REASON_RTSP_TIME_OUT = 2;

        void onFailure(int i);

        void onSuccess(List<SemWifiDisplayParameter> list);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SwitchingType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface VirtualDisplayFlag {}

    static {
        DEBUG = Log.isLoggable(TAG, 3) || Log.isLoggable("DisplayManager_All", 3);
    }

    public DisplayManager(Context context) {
        this.mContext = context;
    }

    public Display getDisplay(int displayId) {
        return getOrCreateDisplay(displayId, false);
    }

    public Display[] getDisplays() {
        return getDisplays(null);
    }

    public Display[] getDisplays(String category) {
        boolean includeDisabled =
                category != null && category.equals(DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED);
        if (category != null
                && (category.equals(DISPLAY_CATEGORY_ALL_INCLUDING_BUILT_IN)
                        || category.equals(
                                "com.samsung.android.hardware.display.category.BUILTIN"))) {
            includeDisabled = true;
        }
        boolean isDexDualModeEnabled = false;
        SemDesktopModeManager dexManager =
                (SemDesktopModeManager)
                        this.mContext.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
        if (dexManager == null) {
            Log.w(TAG, "getDisplays: dexManager is null, category=" + category);
        } else {
            SemDesktopModeState state = dexManager.getDesktopModeState();
            isDexDualModeEnabled =
                    (state == null || state.getEnabled() == 2 || state.getDisplayType() != 102)
                            ? false
                            : true;
        }
        int[] displayIds = this.mGlobal.getDisplayIds(includeDisabled);
        if (DISPLAY_CATEGORY_PRESENTATION.equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isPresentationDisplay;
                            isPresentationDisplay =
                                    DisplayManager.isPresentationDisplay((Display) obj);
                            return isPresentationDisplay;
                        }
                    });
        }
        if (DISPLAY_CATEGORY_REAR.equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isRearDisplay;
                            isRearDisplay = DisplayManager.isRearDisplay((Display) obj);
                            return isRearDisplay;
                        }
                    });
        }
        if ("com.samsung.android.hardware.display.category.BUILTIN".equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isBuiltInDisplay;
                            isBuiltInDisplay = DisplayManager.isBuiltInDisplay((Display) obj);
                            return isBuiltInDisplay;
                        }
                    });
        }
        if (DISPLAY_CATEGORY_ALL_INCLUDING_BUILT_IN.equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean checkNonNullIncludingBuiltIn;
                            checkNonNullIncludingBuiltIn =
                                    DisplayManager.checkNonNullIncludingBuiltIn((Display) obj);
                            return checkNonNullIncludingBuiltIn;
                        }
                    });
        }
        if (DISPLAY_CATEGORY_VIEW_COVER_DISPLAY.equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isViewCoverDisplay;
                            isViewCoverDisplay = DisplayManager.isViewCoverDisplay((Display) obj);
                            return isViewCoverDisplay;
                        }
                    });
        }
        if (DISPLAY_CATEGORY_DESKTOP.equals(category) && isDexDualModeEnabled) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda5
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isDexDisplay;
                            isDexDisplay = DisplayManager.isDexDisplay((Display) obj);
                            return isDexDisplay;
                        }
                    });
        }
        if (DISPLAY_CATEGORY_REMOTE_APP_DISPLAY.equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda6
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isRemoteAppDisplay;
                            isRemoteAppDisplay = DisplayManager.isRemoteAppDisplay((Display) obj);
                            return isRemoteAppDisplay;
                        }
                    });
        }
        if (CoreRune.BAIDU_CARLIFE && DISPLAY_CATEGORY_CARLIFE_DISPLAY.equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda7
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean isCarLifeDisplay;
                            isCarLifeDisplay = DisplayManager.isCarLifeDisplay((Display) obj);
                            return isCarLifeDisplay;
                        }
                    });
        }
        if (category == null || DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED.equals(category)) {
            return getDisplays(
                    displayIds,
                    new Predicate() { // from class:
                        // android.hardware.display.DisplayManager$$ExternalSyntheticLambda8
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean checkNonNullAndOtherPolicy;
                            checkNonNullAndOtherPolicy =
                                    DisplayManager.checkNonNullAndOtherPolicy((Display) obj);
                            return checkNonNullAndOtherPolicy;
                        }
                    });
        }
        if (category.equals(DISPLAY_CATEGORY_HIDDEN_SPACE_DISPLAY)) {
            return addHiddenSpaceDisplaysLocked(displayIds, 5);
        }
        return new Display[0];
    }

    private Display[] addHiddenSpaceDisplaysLocked(int[] displayIds, int matchType) {
        ArrayList<Display> tmpDisplays = new ArrayList<>();
        for (int i : displayIds) {
            Display display = getOrCreateDisplay(i, true);
            if (display != null
                    && display.getType() == matchType
                    && (display.getFlags() & 33554432) != 0) {
                tmpDisplays.add(display);
            }
        }
        int i2 = tmpDisplays.size();
        return (Display[]) tmpDisplays.toArray(new Display[i2]);
    }

    private Display[] getDisplays(int[] displayIds, Predicate<Display> predicate) {
        ArrayList<Display> tmpDisplays = new ArrayList<>();
        for (int displayId : displayIds) {
            Display display = getOrCreateDisplay(displayId, true);
            if ((display == null || (display.getFlags() & 33554432) == 0)
                    && predicate.test(display)) {
                tmpDisplays.add(display);
            }
        }
        return (Display[]) tmpDisplays.toArray(new Display[tmpDisplays.size()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPresentationDisplay(Display display) {
        if (display == null
                || display.getDisplayId() == 0
                || (display.getFlags() & 8) == 0
                || isExtraDisplay(display)
                || isViewCoverDisplay(display)) {
            return false;
        }
        switch (display.getType()) {
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRearDisplay(Display display) {
        return (display == null
                        || display.getDisplayId() == 0
                        || display.getType() != 1
                        || (display.getFlags() & 8192) == 0)
                ? false
                : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isBuiltInDisplay(Display display) {
        return display != null && display.getType() == 1;
    }

    private static boolean isExtraDisplay(Display display) {
        return display != null && display.getDisplayId() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkNonNullIncludingBuiltIn(Display display) {
        if (display == null) {
            return false;
        }
        return isExtraDisplay(display) || checkNonNullAndOtherPolicy(display);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isViewCoverDisplay(Display display) {
        return (display == null || display.getType() != 5 || (display.getFlags() & 524288) == 0)
                ? false
                : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDexDisplay(Display display) {
        return display != null && display.getType() == 5 && display.getDisplayId() == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRemoteAppDisplay(Display display) {
        return (display == null || display.getType() != 5 || (display.getFlags() & 2097152) == 0)
                ? false
                : true;
    }

    public static boolean isBackgroundDisplay(Display display) {
        return (display == null
                        || display.getType() != 5
                        || (display.getFlags() & Display.FLAG_BACKGROUND_DISPLAY) == 0)
                ? false
                : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCarLifeDisplay(Display display) {
        return (display == null || display.getType() != 5 || (display.getFlags() & 1048576) == 0)
                ? false
                : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkNonNullAndOtherPolicy(Display display) {
        if (display == null
                || isExtraDisplay(display)
                || isViewCoverDisplay(display)
                || display.getDisplayId() == 2
                || (display.getFlags() & 2097152) != 0) {
            return false;
        }
        return true;
    }

    private Display getOrCreateDisplay(int displayId, boolean assumeValid) {
        Display display;
        synchronized (this.mLock) {
            display = this.mDisplayCache.get(displayId);
            if (display == null) {
                Resources resources =
                        this.mContext.getDisplayId() == displayId
                                ? this.mContext.getResources()
                                : null;
                display = this.mGlobal.getCompatibleDisplay(displayId, resources);
                if (display != null) {
                    this.mDisplayCache.put(display);
                }
            } else if (!assumeValid && !display.isValid()) {
                display = null;
            }
        }
        return display;
    }

    public void registerDisplayListener(DisplayListener listener, Handler handler) {
        registerDisplayListener(listener, handler, 7L);
    }

    public void registerDisplayListener(
            DisplayListener listener, Handler handler, long eventsMask) {
        this.mGlobal.registerDisplayListener(
                listener, handler, eventsMask, ActivityThread.currentPackageName());
    }

    public void unregisterDisplayListener(DisplayListener listener) {
        this.mGlobal.unregisterDisplayListener(listener);
    }

    public void startWifiDisplayScan() {
        this.mGlobal.startWifiDisplayScan();
    }

    public void stopWifiDisplayScan() {
        this.mGlobal.stopWifiDisplayScan();
    }

    public void connectWifiDisplay(String deviceAddress) {
        this.mGlobal.connectWifiDisplay(deviceAddress);
    }

    public void pauseWifiDisplay() {
        this.mGlobal.pauseWifiDisplay();
    }

    public void resumeWifiDisplay() {
        this.mGlobal.resumeWifiDisplay();
    }

    public void disconnectWifiDisplay() {
        this.mGlobal.disconnectWifiDisplay();
    }

    public void renameWifiDisplay(String deviceAddress, String alias) {
        this.mGlobal.renameWifiDisplay(deviceAddress, alias);
    }

    public void forgetWifiDisplay(String deviceAddress) {
        this.mGlobal.forgetWifiDisplay(deviceAddress);
    }

    public WifiDisplayStatus getWifiDisplayStatus() {
        return this.mGlobal.getWifiDisplayStatus();
    }

    public void enableConnectedDisplay(int displayId) {
        this.mGlobal.enableConnectedDisplay(displayId);
    }

    public void disableConnectedDisplay(int displayId) {
        this.mGlobal.disableConnectedDisplay(displayId);
    }

    @SystemApi
    public void setSaturationLevel(float level) {
        if (level < 0.0f || level > 1.0f) {
            throw new IllegalArgumentException("Saturation level must be between 0 and 1");
        }
        ColorDisplayManager cdm =
                (ColorDisplayManager) this.mContext.getSystemService(ColorDisplayManager.class);
        cdm.setSaturationLevel(Math.round(100.0f * level));
    }

    public void setUserDisabledHdrTypes(int[] userDisabledTypes) {
        this.mGlobal.setUserDisabledHdrTypes(userDisabledTypes);
    }

    public void setAreUserDisabledHdrTypesAllowed(boolean areUserDisabledHdrTypesAllowed) {
        this.mGlobal.setAreUserDisabledHdrTypesAllowed(areUserDisabledHdrTypesAllowed);
    }

    public boolean areUserDisabledHdrTypesAllowed() {
        return this.mGlobal.areUserDisabledHdrTypesAllowed();
    }

    public int[] getUserDisabledHdrTypes() {
        return this.mGlobal.getUserDisabledHdrTypes();
    }

    public void overrideHdrTypes(int displayId, int[] modes) {
        this.mGlobal.overrideHdrTypes(displayId, modes);
    }

    public VirtualDisplay createVirtualDisplay(
            String name, int width, int height, int densityDpi, Surface surface, int flags) {
        return createVirtualDisplay(name, width, height, densityDpi, surface, flags, null, null);
    }

    public VirtualDisplay createVirtualDisplay(
            String name,
            int width,
            int height,
            int densityDpi,
            Surface surface,
            int flags,
            VirtualDisplay.Callback callback,
            Handler handler) {
        VirtualDisplayConfig.Builder builder =
                new VirtualDisplayConfig.Builder(name, width, height, densityDpi);
        builder.setFlags(flags);
        if (surface != null) {
            builder.setSurface(surface);
        }
        return createVirtualDisplay(builder.build(), handler, callback);
    }

    public VirtualDisplay createVirtualDisplay(VirtualDisplayConfig config) {
        return createVirtualDisplay(config, null, null);
    }

    public VirtualDisplay createVirtualDisplay(
            VirtualDisplayConfig config, Handler handler, VirtualDisplay.Callback callback) {
        return createVirtualDisplay(null, config, callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(
            MediaProjection projection,
            String name,
            int width,
            int height,
            int densityDpi,
            Surface surface,
            int flags,
            VirtualDisplay.Callback callback,
            Handler handler,
            String uniqueId) {
        VirtualDisplayConfig.Builder builder =
                new VirtualDisplayConfig.Builder(name, width, height, densityDpi);
        builder.setFlags(flags);
        if (uniqueId != null) {
            builder.setUniqueId(uniqueId);
        }
        if (surface != null) {
            builder.setSurface(surface);
        }
        if (projection != null) {
            Display display = this.mContext.getDisplayNoVerify();
            boolean isDualMode = this.mContext.getResources().getConfiguration().dexMode == 2;
            if ((display != null && display.getDisplayId() == 2) || isDualMode) {
                builder.setDisplayIdToMirror(2);
            }
        }
        return createVirtualDisplay(projection, builder.build(), callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(
            MediaProjection projection,
            VirtualDisplayConfig virtualDisplayConfig,
            VirtualDisplay.Callback callback,
            Handler handler) {
        Executor executor = null;
        if (callback != null) {
            executor =
                    new HandlerExecutor(
                            Handler.createAsync(
                                    handler != null ? handler.getLooper() : Looper.myLooper()));
        }
        return this.mGlobal.createVirtualDisplay(
                this.mContext, projection, virtualDisplayConfig, callback, executor);
    }

    @SystemApi
    public Point getStableDisplaySize() {
        return this.mGlobal.getStableDisplaySize();
    }

    @SystemApi
    public List<BrightnessChangeEvent> getBrightnessEvents() {
        return this.mGlobal.getBrightnessEvents(this.mContext.getOpPackageName());
    }

    @SystemApi
    public List<AmbientBrightnessDayStats> getAmbientBrightnessStats() {
        return this.mGlobal.getAmbientBrightnessStats();
    }

    @SystemApi
    public void setBrightnessConfiguration(BrightnessConfiguration c) {
        setBrightnessConfigurationForUser(
                c, this.mContext.getUserId(), this.mContext.getPackageName());
    }

    @SystemApi
    public void setBrightnessConfigurationForDisplay(BrightnessConfiguration c, String uniqueId) {
        this.mGlobal.setBrightnessConfigurationForDisplay(
                c, uniqueId, this.mContext.getUserId(), this.mContext.getPackageName());
    }

    @SystemApi
    public BrightnessConfiguration getBrightnessConfigurationForDisplay(String uniqueId) {
        return this.mGlobal.getBrightnessConfigurationForDisplay(
                uniqueId, this.mContext.getUserId());
    }

    public void setBrightnessConfigurationForUser(
            BrightnessConfiguration c, int userId, String packageName) {
        this.mGlobal.setBrightnessConfigurationForUser(c, userId, packageName);
    }

    @SystemApi
    public BrightnessConfiguration getBrightnessConfiguration() {
        return getBrightnessConfigurationForUser(this.mContext.getUserId());
    }

    public BrightnessConfiguration getBrightnessConfigurationForUser(int userId) {
        return this.mGlobal.getBrightnessConfigurationForUser(userId);
    }

    @SystemApi
    public BrightnessConfiguration getDefaultBrightnessConfiguration() {
        return this.mGlobal.getDefaultBrightnessConfiguration();
    }

    public boolean isMinimalPostProcessingRequested(int displayId) {
        return this.mGlobal.isMinimalPostProcessingRequested(displayId);
    }

    public void setTemporaryBrightness(int displayId, float brightness) {
        this.mGlobal.setTemporaryBrightness(displayId, brightness);
    }

    public void setBrightness(int displayId, float brightness) {
        this.mGlobal.setBrightness(displayId, brightness);
    }

    public float getBrightness(int displayId) {
        return this.mGlobal.getBrightness(displayId);
    }

    public void setTemporaryAutoBrightnessAdjustment(float adjustment) {
        this.mGlobal.setTemporaryAutoBrightnessAdjustment(adjustment);
    }

    @SystemApi
    public Pair<float[], float[]> getMinimumBrightnessCurve() {
        return this.mGlobal.getMinimumBrightnessCurve();
    }

    public void setGlobalUserPreferredDisplayMode(Display.Mode mode) {
        Display.Mode preferredMode =
                new Display.Mode(
                        mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate());
        this.mGlobal.setUserPreferredDisplayMode(-1, preferredMode);
    }

    public void clearGlobalUserPreferredDisplayMode() {
        this.mGlobal.setUserPreferredDisplayMode(-1, null);
    }

    public Display.Mode getGlobalUserPreferredDisplayMode() {
        return this.mGlobal.getUserPreferredDisplayMode(-1);
    }

    public void setHdrConversionMode(HdrConversionMode hdrConversionMode) {
        this.mGlobal.setHdrConversionMode(hdrConversionMode);
    }

    public HdrConversionMode getHdrConversionMode() {
        return this.mGlobal.getHdrConversionMode();
    }

    public HdrConversionMode getHdrConversionModeSetting() {
        return this.mGlobal.getHdrConversionModeSetting();
    }

    public int[] getSupportedHdrOutputTypes() {
        return this.mGlobal.getSupportedHdrOutputTypes();
    }

    public void setShouldAlwaysRespectAppRequestedMode(boolean enabled) {
        this.mGlobal.setShouldAlwaysRespectAppRequestedMode(enabled);
    }

    public boolean shouldAlwaysRespectAppRequestedMode() {
        return this.mGlobal.shouldAlwaysRespectAppRequestedMode();
    }

    public boolean supportsSeamlessRefreshRateSwitching() {
        return this.mContext
                .getResources()
                .getBoolean(R.bool.config_supportsSeamlessRefreshRateSwitching);
    }

    public void setRefreshRateSwitchingType(int newValue) {
        this.mGlobal.setRefreshRateSwitchingType(newValue);
    }

    public int getMatchContentFrameRateUserPreference() {
        return toMatchContentFrameRateSetting(this.mGlobal.getRefreshRateSwitchingType());
    }

    private int toMatchContentFrameRateSetting(int switchingType) {
        switch (switchingType) {
            case 0:
                return 0;
            case 1:
            case 3:
                return 1;
            case 2:
                return 2;
            default:
                Slog.e(TAG, switchingType + " is not a valid value of switching type.");
                return -1;
        }
    }

    @SystemApi
    public static VirtualDisplay createVirtualDisplay(
            String name, int width, int height, int displayIdToMirror, Surface surface) {
        IDisplayManager sDm =
                IDisplayManager.Stub.asInterface(
                        ServiceManager.getService(Context.DISPLAY_SERVICE));
        IPackageManager sPackageManager =
                IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        VirtualDisplayConfig.Builder builder =
                new VirtualDisplayConfig.Builder(name, width, height, 1)
                        .setFlags(16)
                        .setDisplayIdToMirror(displayIdToMirror);
        if (surface != null) {
            builder.setSurface(surface);
        }
        VirtualDisplayConfig virtualDisplayConfig = builder.build();
        try {
            String[] packages = sPackageManager.getPackagesForUid(Process.myUid());
            String packageName = packages == null ? null : packages[0];
            DisplayManagerGlobal.VirtualDisplayCallback callbackWrapper =
                    new DisplayManagerGlobal.VirtualDisplayCallback(null, null);
            try {
                int displayId =
                        sDm.createVirtualDisplay(
                                virtualDisplayConfig, callbackWrapper, null, packageName);
                return DisplayManagerGlobal.getInstance()
                        .createVirtualDisplayWrapper(
                                virtualDisplayConfig, callbackWrapper, displayId);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        } catch (RemoteException ex2) {
            throw ex2.rethrowFromSystemServer();
        }
    }

    public void requestDisplayModes(int displayId, int[] modeIds) {
        if (modeIds != null && modeIds.length == 0) {
            throw new IllegalArgumentException("requestDisplayModes: modesIds can't be empty");
        }
        this.mGlobal.requestDisplayModes(displayId, modeIds);
    }

    public interface DisplayListener {
        void onDisplayAdded(int i);

        void onDisplayChanged(int i);

        void onDisplayRemoved(int i);

        default void onDisplayConnected(int displayId) {}

        default void onDisplayDisconnected(int displayId) {}
    }

    private static final class WeakDisplayCache {
        private final SparseArray<WeakReference<Display>> mDisplayCache;

        private WeakDisplayCache() {
            this.mDisplayCache = new SparseArray<>();
        }

        Display get(int displayId) {
            WeakReference<Display> wrDisplay = this.mDisplayCache.get(displayId);
            if (wrDisplay == null) {
                return null;
            }
            return wrDisplay.get();
        }

        void put(Display display) {
            removeStaleEntries();
            this.mDisplayCache.put(display.getDisplayId(), new WeakReference<>(display));
        }

        private void removeStaleEntries() {
            ArrayList<Integer> staleEntriesIndices = new ArrayList<>();
            for (int i = 0; i < this.mDisplayCache.size(); i++) {
                if (this.mDisplayCache.valueAt(i).get() == null) {
                    staleEntriesIndices.add(Integer.valueOf(i));
                }
            }
            for (int i2 = 0; i2 < staleEntriesIndices.size(); i2++) {
                this.mDisplayCache.removeAt(staleEntriesIndices.get(i2).intValue());
            }
        }
    }

    public int getHiddenDisplayId(String callerPackageName) {
        return getHiddenDisplayId(callerPackageName, -1);
    }

    public int getHiddenDisplayId(int uid) {
        return getHiddenDisplayId(null, uid);
    }

    private int getHiddenDisplayId(String owner, int uid) {
        for (Display display : getDisplays()) {
            if (display.getType() == 5
                    && (display.getFlags() & 32768) != 0
                    && ((owner != null && owner.equals(display.getOwnerPackageName()))
                            || (UserHandle.isApp(uid) && display.getOwnerUid() == uid))) {
                int id = display.getDisplayId();
                Slog.d(
                        TAG_SPEG,
                        "Display #"
                                + id
                                + " ("
                                + SPEG_DISPLAY_NAME
                                + "), owner: "
                                + display.getOwnerPackageName()
                                + ":"
                                + display.getOwnerUid());
                return id;
            }
        }
        return -1;
    }

    public void setTemporaryBrightness(int brightness, boolean slowChange) {
        this.mGlobal.setTemporaryBrightnessForSlowChange(
                0, BrightnessSynchronizer.brightnessIntToFloat(brightness), slowChange);
    }

    public void setTemporaryBrightness(int displayId, int brightness, boolean slowChange) {
        this.mGlobal.setTemporaryBrightnessForSlowChange(
                displayId, BrightnessSynchronizer.brightnessIntToFloat(brightness), slowChange);
    }

    public void semSetTemporaryBrightness(int brightness) {
        Slog.i(TAG, "semSetTemporaryBrightness: brightness=" + brightness);
        this.mGlobal.setTemporaryBrightness(
                0, BrightnessSynchronizer.brightnessIntToFloat(brightness));
    }

    public void semSetTemporaryBrightness(float brightness) {
        this.mGlobal.setTemporaryBrightness(0, brightness);
    }

    public void semSetTemporaryBrightness(int displayId, float brightness) {
        this.mGlobal.setTemporaryBrightness(displayId, brightness);
    }

    public int semCheckScreenSharingSupported() {
        return 0;
    }

    public void semStartScanWifiDisplays() {
        Log.d(TAG, "semStartScanWifiDisplays" + Log.getStackTraceString(new Throwable()));
        this.mGlobal.startWifiDisplayScan();
    }

    public void semStartScanWifiDisplays(int scanChannel) {
        Log.d(
                TAG,
                "semStartScanWifiDisplays, scanChannel = "
                        + scanChannel
                        + Log.getStackTraceString(new Throwable()));
        this.mGlobal.startWifiDisplayScan(scanChannel);
    }

    public void semStartScanWifiDisplays(int scanChannel, int interval) {
        Log.d(
                TAG,
                "semStartScanWifiDisplays, scanChannel = "
                        + scanChannel
                        + ", interval = "
                        + interval
                        + Log.getStackTraceString(new Throwable()));
        this.mGlobal.startWifiDisplayScan(scanChannel, interval);
    }

    public void semStopScanWifiDisplays() {
        Log.d(TAG, "semStopScanWifiDisplays" + Log.getStackTraceString(new Throwable()));
        this.mGlobal.stopWifiDisplayScan();
    }

    public void semRegisterDeviceStatusListener(SemDeviceStatusListener listener, Handler handler) {
        this.mGlobal.registerDeviceListener(listener, handler);
    }

    public void semUnregisterDeviceStatusListener(SemDeviceStatusListener listener) {
        this.mGlobal.unregisterDeviceListener(listener);
    }

    public void semRegisterDisplayVolumeListener(
            SemDisplayVolumeListener listener, Handler handler) {
        this.mGlobal.registerDisplayVolumeListener(listener, handler);
    }

    public void semUnregisterDisplayVolumeListener(SemDisplayVolumeListener listener) {
        this.mGlobal.unregisterDisplayVolumeListener(listener);
    }

    public void semRegisterDisplayVolumeKeyListener(
            SemDisplayVolumeKeyListener listener, Handler handler) {
        this.mGlobal.registerDisplayVolumeKeyListener(listener, handler);
    }

    public void semUnregisterDisplayVolumeKeyListener(SemDisplayVolumeKeyListener listener) {
        this.mGlobal.unregisterDisplayVolumeKeyListener(listener);
    }

    public void semRegisterWifiDisplayParameterListener(
            SemWifiDisplayParameterListener listener, Handler handler) {
        this.mGlobal.registerWifiDisplayParameterListener(listener, handler);
    }

    public void semUnregisterWifiDisplayParameterListener(
            SemWifiDisplayParameterListener listener) {
        this.mGlobal.unregisterWifiDisplayParameterListener(listener);
    }

    public void semSetDeviceVolume(int volume) {
        this.mGlobal.setDeviceVolume(volume);
    }

    public boolean semIsWifiDisplayWithPinSupported(String address) {
        return this.mGlobal.isWifiDisplayWithPinSupported(address);
    }

    public String semGetPresentationOwner(int displayId) {
        return this.mGlobal.getPresentationOwner(displayId);
    }

    public boolean semIsFitToActiveDisplay() {
        return this.mGlobal.isFitToActiveDisplay();
    }

    public void semFitToActiveDisplay(boolean status) {
        this.mGlobal.fitToActiveDisplay(status);
    }

    public boolean semRequestSetWifiDisplayParameters(List<SemWifiDisplayParameter> parameters) {
        Log.d(TAG, "semRequestSetWifiDisplayParameters : " + parameters);
        return this.mGlobal.requestSetWifiDisplayParameters(parameters);
    }

    public boolean semRequestWifiDisplayParameter(
            String parameterGroup, SemWifiDisplayParameter parameter) {
        Log.d(
                TAG,
                "semRequestWifiDisplayParameter, parameterGroup : "
                        + parameterGroup
                        + ", parameter : "
                        + parameter);
        return this.mGlobal.requestWifiDisplayParameter(parameterGroup, parameter);
    }

    public int semGetScreenSharingStatus() {
        return this.mGlobal.getScreenSharingStatus();
    }

    public void semSetScreenSharingStatus(int status) {
        this.mGlobal.setScreenSharingStatus(status);
    }

    public void semSetActiveDlnaState(SemDlnaDevice device, int state) {
        Log.d(TAG, "semSetActiveDlnaState" + Log.getStackTraceString(new Throwable()));
        if (device != null) {
            device.setConnectionState(state);
            this.mGlobal.setDlnaDevice(device);
        }
    }

    public SemDlnaDevice semGetActiveDlnaDevice() {
        if (this.mGlobal.getDlnaDevice().isConnected()) {
            return this.mGlobal.getDlnaDevice();
        }
        return null;
    }

    public int semGetActiveDlnaState() {
        return this.mGlobal.getDlnaDevice().getConnectionState();
    }

    public void semConnectWifiDisplay(
            SemWifiDisplayConfig wifidisplayConfig,
            SemWifiDisplayConnectionCallback callback,
            Handler handler) {
        Log.d(TAG, "semConnectWifiDisplay : config = " + wifidisplayConfig.toString());
        this.mGlobal.connectWifiDisplay(wifidisplayConfig, callback, handler);
    }

    public void semDisconnectWifiDisplay() {
        this.mGlobal.disconnectWifiDisplay();
    }

    public int semSetWifiDisplayConfiguration(String key, boolean isSet) {
        Log.w(TAG, "semSetWifiDisplayConfiguration key " + key + " value " + isSet);
        if (key.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SET_VOLUME_MUTE)) {
            this.mGlobal.setDeviceVolumeMuted(isSet);
            return 0;
        }
        if (key.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SEND_VOLUME_MUTE_KEY_EVENT)) {
            if (isSet) {
                this.mGlobal.setVolumeKeyEvent(2);
            } else {
                this.mGlobal.setVolumeKeyEvent(3);
            }
            return 0;
        }
        return -1;
    }

    public int semSetWifiDisplayConfiguration(String key, int value) {
        Log.w(TAG, "semSetWifiDisplayConfiguration key " + key + " value " + value);
        if (key.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SEND_VOLUME_KEY_EVENT)) {
            this.mGlobal.setVolumeKeyEvent(value);
            return 0;
        }
        if (key.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SET_DISPLAY_VOLUME)) {
            semSetDeviceVolume(value);
            return 0;
        }
        return -1;
    }

    public int semSetWifiDisplayConfiguration(String key, String value) {
        Log.d(TAG, "semSetWifiDisplayConfiguration key " + key + " String value " + value);
        this.mGlobal.setWifiDisplayParam(key, value);
        return 0;
    }

    public Object semGetWifiDisplayConfiguration(String key) {
        if (key.equals(SemScreenSharingConstants.KEY_CONFIGURATION_IS_VOLUME_MUTE)) {
            return Boolean.valueOf(this.mGlobal.isDeviceVolumeMuted());
        }
        if (key.equals(SemScreenSharingConstants.KEY_CONFIGURATION_MAX_VOLUME_LEVEL)) {
            return Integer.valueOf(this.mGlobal.getDeviceMaxVolume());
        }
        if (key.equals(SemScreenSharingConstants.KEY_CONFIGURATION_MIN_VOLUME_LEVEL)) {
            return Integer.valueOf(this.mGlobal.getDeviceMinVolume());
        }
        return null;
    }

    public void semSetWifiDisplayVolume(int volume) {
        semSetDeviceVolume(volume);
    }

    public void semSetWifiDisplayVolumeMuted(boolean muted) {
        this.mGlobal.setDeviceVolumeMuted(muted);
    }

    public SemWifiDisplayStatus semGetWifiDisplayStatus() {
        return new SemWifiDisplayStatus(this.mGlobal.getWifiDisplayStatus());
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0181  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void semEnableWifiDisplay(
            java.lang.String r24,
            java.lang.String r25,
            int r26,
            java.lang.String r27,
            java.lang.String r28,
            java.lang.String r29,
            boolean r30) {
        /*
            Method dump skipped, instructions count: 485
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.hardware.display.DisplayManager.semEnableWifiDisplay(java.lang.String,"
                    + " java.lang.String, int, java.lang.String, java.lang.String,"
                    + " java.lang.String, boolean):void");
    }

    public void semDisconnectDevice() {
        this.mGlobal.disconnectWifiDisplay();
    }

    public void semRemoveSavedDevice() {}

    public void semPauseWifiDisplay() {
        this.mGlobal.pauseWifiDisplay();
    }

    public void semResumeWifiDisplay() {
        this.mGlobal.resumeWifiDisplay();
    }

    public boolean semIsSmartMirroringSupported() {
        return true;
    }

    public int semCheckExceptionalCase() {
        return -1;
    }

    public void semSetActivityState(SemWifiDisplayAppState state) {
        if (state == SemWifiDisplayAppState.SETUP) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "wifi_display_on", 1);
            return;
        }
        if (state == SemWifiDisplayAppState.PAUSE) {
            this.mGlobal.stopWifiDisplayScan();
        } else if (state == SemWifiDisplayAppState.RESUME) {
            this.mGlobal.startWifiDisplayScan();
        } else if (state == SemWifiDisplayAppState.TEARDOWN) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "wifi_display_on", 0);
        }
    }

    public boolean semIsWifiDisplayVolumeControlSupported() {
        return false;
    }

    public void semSetDeviceVolumeMuted(boolean muted) {
        this.mGlobal.setDeviceVolumeMuted(muted);
    }

    public void setBrightnessConfigurationForUser(
            BrightnessConfiguration c,
            int userId,
            String packageName,
            List<String> weights,
            List<String> timeWeights,
            List<String> continuityWeights) {
        this.mGlobal.setBrightnessConfigurationForUser(
                c, userId, packageName, weights, timeWeights, continuityWeights);
    }

    public void resetBrightnessConfiguration() {
        this.mGlobal.resetBrightnessConfigurationForUser(
                this.mContext.getUserId(), this.mContext.getPackageName());
    }

    public void setBackupBrightnessConfiguration(
            BrightnessConfiguration config, int userId, String packageName) {
        this.mGlobal.setBackupBrightnessConfiguration(config, userId, packageName);
    }

    public BrightnessConfiguration getBackupBrightnessConfiguration(int userId) {
        return this.mGlobal.getBackupBrightnessConfiguration(userId);
    }

    public int convertToBrightness(float nits) {
        return this.mGlobal.convertToBrightness(nits);
    }

    public float getAdaptiveBrightness(int displayId, float lux) {
        return this.mGlobal.getAdaptiveBrightness(displayId, lux);
    }

    public void registerHbmBrightnessListener(DisplayHbmBrightnessListener listener) {
        this.mGlobal.registerHbmBrightnessListener(listener);
    }

    public void unregisterHbmBrightnessListener(DisplayHbmBrightnessListener listener) {
        this.mGlobal.unregisterHbmBrightnessListener(listener);
    }
}
