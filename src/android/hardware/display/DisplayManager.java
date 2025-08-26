package android.hardware.display;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.graphics.Point;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.IDisplayManager;
import android.hardware.display.SemWifiDisplayConfig;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.Surface;
import com.android.internal.R;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.display.feature.flags.Flags;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class DisplayManager {
    public static final String ACTION_ROTATION_CHANGED = "com.samsung.intent.action.ROTATION_CHANGED";
    public static final String ACTION_WIFI_DISPLAY_STATUS_CHANGED = "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED";
    static final boolean DEBUG;
    public static final String DISPLAY_CATEGORY_ALL_INCLUDING_BUILT_IN = "android.hardware.display.category.ALL_INCLUDING_BUILT_IN";
    public static final String DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED = "android.hardware.display.category.ALL_INCLUDING_DISABLED";
    public static final String DISPLAY_CATEGORY_BACKGROUND_DISPLAY = "com.samsung.android.hardware.display.category.BACKGROUND_DISPLAY";
    public static final String DISPLAY_CATEGORY_BUILTIN = "com.samsung.android.hardware.display.category.BUILTIN";
    public static final String DISPLAY_CATEGORY_BUILT_IN_DISPLAYS = "android.hardware.display.category.BUILT_IN_DISPLAYS";
    public static final String DISPLAY_CATEGORY_CARLIFE_DISPLAY = "com.samsung.android.hardware.display.category.CARLIFE_DISPLAY";
    public static final String DISPLAY_CATEGORY_DESKTOP = "com.samsung.android.hardware.display.category.DESKTOP";
    public static final String DISPLAY_CATEGORY_HIDDEN_SPACE_DISPLAY = "com.samsung.android.hardware.display.category.HIDDEN_SPACE_DISPLAY";
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    public static final String DISPLAY_CATEGORY_REAR = "android.hardware.display.category.REAR";
    public static final String DISPLAY_CATEGORY_REMOTE_APP_DISPLAY = "com.samsung.android.hardware.display.category.REMOTE_APP_DISPLAY";
    public static final String DISPLAY_CATEGORY_VIEW_COVER_DISPLAY = "com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY";
    private static final boolean ENABLE_VIRTUAL_DISPLAY_REFRESH_RATE = true;
    public static final long EVENT_TYPE_DISPLAY_ADDED = 1;
    public static final long EVENT_TYPE_DISPLAY_CHANGED = 4;
    public static final long EVENT_TYPE_DISPLAY_REFRESH_RATE = 8;
    public static final long EVENT_TYPE_DISPLAY_REMOVED = 2;
    public static final long EVENT_TYPE_DISPLAY_STATE = 16;
    public static final int EXTERNAL_DESKTOP_DISPLAY_MIN_HEIGHT = 900;
    public static final int EXTERNAL_DESKTOP_DISPLAY_MIN_WIDTH = 1600;
    public static final String EXTRA_WIFI_DISPLAY_STATUS = "android.hardware.display.extra.WIFI_DISPLAY_STATUS";
    public static final String HDR_OUTPUT_CONTROL_FLAG = "enable_hdr_output_control";
    public static final int MATCH_CONTENT_FRAMERATE_ALWAYS = 2;
    public static final int MATCH_CONTENT_FRAMERATE_NEVER = 0;
    public static final int MATCH_CONTENT_FRAMERATE_SEAMLESSS_ONLY = 1;
    public static final int MATCH_CONTENT_FRAMERATE_UNKNOWN = -1;
    public static final long PRIVATE_EVENT_TYPE_DISPLAY_BRIGHTNESS = 1;
    public static final long PRIVATE_EVENT_TYPE_DISPLAY_COMMITTED_STATE_CHANGED = 8;
    public static final long PRIVATE_EVENT_TYPE_DISPLAY_CONNECTION_CHANGED = 4;
    public static final long PRIVATE_EVENT_TYPE_HDR_SDR_RATIO_CHANGED = 2;
    public static final String SEM_ACTION_DISCONNECT_LELINK_CAST = "com.samsung.intent.action.DISCONNECT_LELINK_CAST";
    public static final String SEM_ACTION_DLNA_STATUS_CHANGED = "com.samsung.intent.action.DLNA_STATUS_CHANGED";
    public static final String SEM_ACTION_SET_SCREEN_RATIO_VALUE = "com.samsung.intent.action.SET_SCREEN_RATIO_VALUE";
    public static final String SEM_ACTION_WIFI_DISPLAY_STATUS_CHANGED = "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED";
    public static final int SEM_CONNECT_STATE_CHANGEPLAYER_MUSIC = 8;
    public static final int SEM_CONNECT_STATE_NORMAL = -1;
    public static final String SEM_DISPLAY_CATEGORY_RUNTIME_MIRRORING_SWAP = "com.samsung.android.hardware.display.category.RUNTIME_MIRRORING_SWAP";
    public static final String SEM_EXTRA_DLNA_PLAYER_TYPE = "player_type";
    public static final String SEM_EXTRA_DLNA_STATUS = "status";
    public static final String SEM_EXTRA_WIFI_DISPLAY_STATUS = "android.hardware.display.extra.WIFI_DISPLAY_STATUS";
    public static final String SEM_PRESENTATION_START = "com.samsung.intent.action.SEC_PRESENTATION_START";
    public static final String SEM_PRESENTATION_START_SMARTVIEW = "com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW";
    public static final String SEM_PRESENTATION_STOP = "com.samsung.intent.action.SEC_PRESENTATION_STOP";
    public static final String SEM_PRESENTATION_STOP_SMARTVIEW = "com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW";
    public static final String SEM_WIFIDISPLAY_NOTI_CONNECTION_MODE = "com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE";
    public static final String SEM_WIFI_DISPLAY_SINK_STATE = "com.samsung.intent.action.WIFI_DISPLAY_SINK_STATE";
    public static final String SEM_WIFI_DISPLAY_SOURCE_STATE = "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE";
    public static final String SEM_WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED = "com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED";
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
    public static final int VIRTUAL_DISPLAY_FLAG_DESTROY_CONTENT_ON_REMOVAL = 256;
    public static final int VIRTUAL_DISPLAY_FLAG_DEVICE_DISPLAY_GROUP = 32768;
    public static final int VIRTUAL_DISPLAY_FLAG_HIDDEN_SPACE = 131072;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY = 8;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_DISPLAY_GROUP = 2048;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_FOCUS = 16384;
    public static final int VIRTUAL_DISPLAY_FLAG_PRESENTATION = 2;
    public static final int VIRTUAL_DISPLAY_FLAG_PUBLIC = 1;
    public static final int VIRTUAL_DISPLAY_FLAG_REMOTE_APP = 524288;

    @SystemApi
    public static final int VIRTUAL_DISPLAY_FLAG_ROTATES_WITH_CONTENT = 128;
    public static final int VIRTUAL_DISPLAY_FLAG_SECURE = 4;
    public static final int VIRTUAL_DISPLAY_FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 512;
    private static final int VIRTUAL_DISPLAY_FLAG_SPEG_DISPLAY = 16777216;

    @SystemApi
    public static final int VIRTUAL_DISPLAY_FLAG_STEAL_TOP_FOCUS_DISABLED = 65536;
    public static final int VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH = 64;
    public static final int VIRTUAL_DISPLAY_FLAG_TOUCH_FEEDBACK_DISABLED = 8192;

    @SystemApi
    public static final int VIRTUAL_DISPLAY_FLAG_TRUSTED = 1024;
    public static final int VIRTUAL_DISPLAY_FLAG_VIEW_COVER = 1073741824;
    public static final int VIRTUAL_DISPLAY_NO_LOCK_PRESENTATION = 2097152;
    private AmbientDisplayConfiguration mAmbientDisplayConfiguration;
    private final Context mContext;
    private final Object mLock = new Object();
    private final WeakDisplayCache mDisplayCache = new WeakDisplayCache();
    private int mDisplayIdToMirror = -1;
    private final DisplayManagerGlobal mGlobal = DisplayManagerGlobal.getInstance();

    public interface DeviceConfig {
        public static final String KEY_BRIGHTNESS_THROTTLING_DATA = "brightness_throttling_data";
        public static final String KEY_DISABLE_SCREEN_WAKE_LOCKS_WHILE_CACHED = "disable_screen_wake_locks_while_cached";
        public static final String KEY_FIXED_REFRESH_RATE_HIGH_AMBIENT_BRIGHTNESS_THRESHOLDS = "fixed_refresh_rate_high_ambient_brightness_thresholds";
        public static final String KEY_FIXED_REFRESH_RATE_HIGH_DISPLAY_BRIGHTNESS_THRESHOLDS = "fixed_refresh_rate_high_display_brightness_thresholds";
        public static final String KEY_FIXED_REFRESH_RATE_LOW_AMBIENT_BRIGHTNESS_THRESHOLDS = "peak_refresh_rate_ambient_thresholds";
        public static final String KEY_FIXED_REFRESH_RATE_LOW_DISPLAY_BRIGHTNESS_THRESHOLDS = "peak_refresh_rate_brightness_thresholds";
        public static final String KEY_HIGH_REFRESH_RATE_BLACKLIST = "high_refresh_rate_blacklist";
        public static final String KEY_PEAK_REFRESH_RATE_DEFAULT = "peak_refresh_rate_default";
        public static final String KEY_POWER_THROTTLING_DATA = "power_throttling_data";
        public static final String KEY_REFRESH_RATE_IN_HBM_HDR = "refresh_rate_in_hbm_hdr";
        public static final String KEY_REFRESH_RATE_IN_HBM_SUNLIGHT = "refresh_rate_in_hbm_sunlight";
        public static final String KEY_REFRESH_RATE_IN_HIGH_ZONE = "refresh_rate_in_high_zone";
        public static final String KEY_REFRESH_RATE_IN_LOW_ZONE = "refresh_rate_in_zone";
        public static final String KEY_USE_NORMAL_BRIGHTNESS_MODE_CONTROLLER = "use_normal_brightness_mode_controller";
    }

    public interface DisplayHbmBrightnessListener {
        void onChanged(int i, boolean z);
    }

    public interface DisplayListener {
        void onDisplayAdded(int i);

        void onDisplayChanged(int i);

        default void onDisplayConnected(int i) {
        }

        default void onDisplayDisconnected(int i) {
        }

        void onDisplayRemoved(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MatchContentFrameRateType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrivateEventType {
    }

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
    public @interface SwitchingType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VirtualDisplayFlag {
    }

    public int semCheckExceptionalCase() {
        return -1;
    }

    public int semCheckScreenSharingSupported() {
        return 0;
    }

    static {
        DEBUG = Log.isLoggable(TAG, 3) || Log.isLoggable("DisplayManager_All", 3);
    }

    public DisplayManager(Context context) {
        this.mContext = context;
    }

    public Display getDisplay(int i) {
        return getOrCreateDisplay(i, false);
    }

    public Display[] getDisplays() {
        return getDisplays(null);
    }

    public Display[] getDisplays(String str) {
        int[] displayIds = this.mGlobal.getDisplayIds(shouldIncludeDisabledDisplays(str));
        if (Flags.displayCategoryBuiltIn() && DISPLAY_CATEGORY_BUILT_IN_DISPLAYS.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.isBuiltInDisplay((Display) obj);
                }
            });
        }
        if (DISPLAY_CATEGORY_PRESENTATION.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.isPresentationDisplay((Display) obj);
                }
            });
        }
        if (DISPLAY_CATEGORY_REAR.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.isRearDisplay((Display) obj);
                }
            });
        }
        if ("com.samsung.android.hardware.display.category.BUILTIN".equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.isBuiltInDisplay((Display) obj);
                }
            });
        }
        if (DISPLAY_CATEGORY_ALL_INCLUDING_BUILT_IN.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.checkNonNullIncludingBuiltIn((Display) obj);
                }
            });
        }
        if (DISPLAY_CATEGORY_VIEW_COVER_DISPLAY.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.isViewCoverDisplay((Display) obj);
                }
            });
        }
        if (DISPLAY_CATEGORY_REMOTE_APP_DISPLAY.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.isRemoteAppDisplay((Display) obj);
                }
            });
        }
        if (CoreRune.BAIDU_CARLIFE && DISPLAY_CATEGORY_CARLIFE_DISPLAY.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.isCarLifeDisplay((Display) obj);
                }
            });
        }
        if (str == null || DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED.equals(str)) {
            return getDisplays(displayIds, new Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DisplayManager.checkNonNullAndOtherPolicy((Display) obj);
                }
            });
        }
        if (str.equals(DISPLAY_CATEGORY_HIDDEN_SPACE_DISPLAY)) {
            return addHiddenSpaceDisplaysLocked(displayIds, 5);
        }
        return new Display[0];
    }

    private boolean shouldIncludeDisabledDisplays(String str) {
        return DISPLAY_CATEGORY_BUILT_IN_DISPLAYS.equals(str) || DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED.equals(str) || "com.samsung.android.hardware.display.category.BUILTIN".equals(str) || DISPLAY_CATEGORY_ALL_INCLUDING_BUILT_IN.equals(str);
    }

    private Display[] addHiddenSpaceDisplaysLocked(int[] iArr, int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 : iArr) {
            Display orCreateDisplay = getOrCreateDisplay(i2, true);
            if (orCreateDisplay != null && orCreateDisplay.getType() == i && (orCreateDisplay.getFlags() & 33554432) != 0) {
                arrayList.add(orCreateDisplay);
            }
        }
        return (Display[]) arrayList.toArray(new Display[arrayList.size()]);
    }

    private Display[] getDisplays(int[] iArr, Predicate<Display> predicate) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            Display orCreateDisplay = getOrCreateDisplay(i, true);
            if ((orCreateDisplay == null || (orCreateDisplay.getFlags() & 33554432) == 0) && predicate.test(orCreateDisplay)) {
                arrayList.add(orCreateDisplay);
            }
        }
        return (Display[]) arrayList.toArray(new Display[arrayList.size()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isBuiltInDisplay(Display display) {
        return display != null && display.getType() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPresentationDisplay(Display display) {
        if (display == null || display.getDisplayId() == 0 || (display.getFlags() & 8) == 0 || isExtraDisplay(display) || isViewCoverDisplay(display)) {
            return false;
        }
        int type = display.getType();
        return type == 1 || type == 2 || type == 3 || type == 4 || type == 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRearDisplay(Display display) {
        return (display == null || display.getDisplayId() == 0 || display.getType() != 1 || (display.getFlags() & 8192) == 0) ? false : true;
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
        return (display == null || display.getType() != 5 || (display.getFlags() & 524288) == 0) ? false : true;
    }

    public static boolean isExternalDesktopDisplay(Display display) {
        return (display.getFlags() & 131072) != 0;
    }

    public static boolean isExternalDesktopDisplay(DisplayInfo displayInfo) {
        return (displayInfo.flags & 131072) != 0;
    }

    public static boolean shouldAdjustResolutionForDesktopDisplay(DisplayInfo displayInfo) {
        return Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight) < 1600 || Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight) < 900;
    }

    public static boolean isHDPlusResolutionDisplay(DisplayInfo displayInfo) {
        return Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight) == 1600 && Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight) == 900;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRemoteAppDisplay(Display display) {
        return (display == null || display.getType() != 5 || (display.getFlags() & 2097152) == 0) ? false : true;
    }

    public static boolean isBackgroundDisplay(Display display) {
        return (display == null || display.getType() != 5 || (display.getFlags() & Display.FLAG_BACKGROUND_DISPLAY) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCarLifeDisplay(Display display) {
        return (display == null || display.getType() != 5 || (display.getFlags() & 1048576) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkNonNullAndOtherPolicy(Display display) {
        return (display == null || isExtraDisplay(display) || isViewCoverDisplay(display) || (display.getFlags() & 2097152) != 0) ? false : true;
    }

    private Display getOrCreateDisplay(int i, boolean z) {
        Display compatibleDisplay;
        synchronized (this.mLock) {
            compatibleDisplay = this.mDisplayCache.get(i);
            if (compatibleDisplay == null) {
                compatibleDisplay = this.mGlobal.getCompatibleDisplay(i, this.mContext.getDisplayId() == i ? this.mContext.getResources() : null);
                if (compatibleDisplay != null) {
                    this.mDisplayCache.put(compatibleDisplay);
                }
            } else if (!z && !compatibleDisplay.isValid()) {
                compatibleDisplay = null;
            }
        }
        return compatibleDisplay;
    }

    public void registerDisplayListener(DisplayListener displayListener, Handler handler) {
        registerDisplayListener(displayListener, handler, 7L, 0L, ActivityThread.currentPackageName(), false);
    }

    public void registerDisplayListener(DisplayListener displayListener, Handler handler, long j) {
        registerDisplayListener(displayListener, handler, j, 0L, ActivityThread.currentPackageName(), true);
    }

    public void registerDisplayListener(Executor executor, long j, DisplayListener displayListener) {
        registerDisplayListener(displayListener, executor, j, 0L, ActivityThread.currentPackageName(), true);
    }

    public void registerDisplayListener(DisplayListener displayListener, Handler handler, long j, long j2) {
        registerDisplayListener(displayListener, handler, j, j2, ActivityThread.currentPackageName(), true);
    }

    private void registerDisplayListener(DisplayListener displayListener, Handler handler, long j, long j2, String str, boolean z) {
        DisplayManagerGlobal displayManagerGlobal = this.mGlobal;
        displayManagerGlobal.registerDisplayListener(displayListener, handler, displayManagerGlobal.mapFiltersToInternalEventFlag(j, j2), str, z);
    }

    private void registerDisplayListener(DisplayListener displayListener, Executor executor, long j, long j2, String str, boolean z) {
        DisplayManagerGlobal displayManagerGlobal = this.mGlobal;
        displayManagerGlobal.registerDisplayListener(displayListener, executor, displayManagerGlobal.mapFiltersToInternalEventFlag(j, j2), str, z);
    }

    public void unregisterDisplayListener(DisplayListener displayListener) {
        this.mGlobal.unregisterDisplayListener(displayListener);
    }

    public void startWifiDisplayScan() {
        this.mGlobal.startWifiDisplayScan();
    }

    public void stopWifiDisplayScan() {
        this.mGlobal.stopWifiDisplayScan();
    }

    public void connectWifiDisplay(String str) {
        this.mGlobal.connectWifiDisplay(str);
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

    public void renameWifiDisplay(String str, String str2) {
        this.mGlobal.renameWifiDisplay(str, str2);
    }

    public void forgetWifiDisplay(String str) {
        this.mGlobal.forgetWifiDisplay(str);
    }

    public WifiDisplayStatus getWifiDisplayStatus() {
        return this.mGlobal.getWifiDisplayStatus();
    }

    public void setEnableConnectedDisplay(int i, boolean z) {
        this.mGlobal.setEnableConnectedDisplay(i, z);
    }

    public void enableConnectedDisplay(int i) {
        this.mGlobal.enableConnectedDisplay(i);
    }

    public void disableConnectedDisplay(int i) {
        this.mGlobal.disableConnectedDisplay(i);
    }

    @SystemApi
    public void setSaturationLevel(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Saturation level must be between 0 and 1");
        }
        ((ColorDisplayManager) this.mContext.getSystemService(ColorDisplayManager.class)).setSaturationLevel(Math.round(f * 100.0f));
    }

    public void setUserDisabledHdrTypes(int[] iArr) {
        this.mGlobal.setUserDisabledHdrTypes(iArr);
    }

    public void setAreUserDisabledHdrTypesAllowed(boolean z) {
        this.mGlobal.setAreUserDisabledHdrTypesAllowed(z);
    }

    public boolean areUserDisabledHdrTypesAllowed() {
        return this.mGlobal.areUserDisabledHdrTypesAllowed();
    }

    public int[] getUserDisabledHdrTypes() {
        return this.mGlobal.getUserDisabledHdrTypes();
    }

    public void resetImplicitRefreshRateCallbackStatus() {
        this.mGlobal.resetImplicitRefreshRateCallbackStatus();
    }

    public void overrideHdrTypes(int i, int[] iArr) {
        this.mGlobal.overrideHdrTypes(i, iArr);
    }

    public VirtualDisplay createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, int i4) {
        return createVirtualDisplay(str, i, i2, i3, surface, i4, null, null);
    }

    public VirtualDisplay createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, int i4, VirtualDisplay.Callback callback, Handler handler) {
        VirtualDisplayConfig.Builder builder = new VirtualDisplayConfig.Builder(str, i, i2, i3);
        builder.setFlags(i4);
        if (surface != null) {
            builder.setSurface(surface);
        }
        builder.setDisplayIdToMirror(getDisplayIdToMirror());
        return createVirtualDisplay(builder.build(), handler, callback);
    }

    public VirtualDisplay createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig) {
        return createVirtualDisplay(virtualDisplayConfig, null, null);
    }

    public VirtualDisplay createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, Handler handler, VirtualDisplay.Callback callback) {
        return createVirtualDisplay(null, virtualDisplayConfig, callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(MediaProjection mediaProjection, String str, int i, int i2, int i3, Surface surface, int i4, VirtualDisplay.Callback callback, Handler handler, String str2) {
        VirtualDisplayConfig.Builder builder = new VirtualDisplayConfig.Builder(str, i, i2, i3);
        builder.setFlags(i4);
        if (str2 != null) {
            builder.setUniqueId(str2);
        }
        if (surface != null) {
            builder.setSurface(surface);
        }
        builder.setDisplayIdToMirror(getDisplayIdToMirror());
        return createVirtualDisplay(mediaProjection, builder.build(), callback, handler);
    }

    public VirtualDisplay createVirtualDisplay(MediaProjection mediaProjection, VirtualDisplayConfig virtualDisplayConfig, VirtualDisplay.Callback callback, Handler handler) {
        HandlerExecutor handlerExecutor;
        if (callback != null) {
            handlerExecutor = new HandlerExecutor(Handler.createAsync(handler != null ? handler.getLooper() : Looper.myLooper()));
        } else {
            handlerExecutor = null;
        }
        return this.mGlobal.createVirtualDisplay(this.mContext, mediaProjection, virtualDisplayConfig, callback, handlerExecutor);
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
    public void setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration) {
        setBrightnessConfigurationForUser(brightnessConfiguration, this.mContext.getUserId(), this.mContext.getPackageName());
    }

    @SystemApi
    public void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str) {
        this.mGlobal.setBrightnessConfigurationForDisplay(brightnessConfiguration, str, this.mContext.getUserId(), this.mContext.getPackageName());
    }

    @SystemApi
    public BrightnessConfiguration getBrightnessConfigurationForDisplay(String str) {
        return this.mGlobal.getBrightnessConfigurationForDisplay(str, this.mContext.getUserId());
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) {
        this.mGlobal.setBrightnessConfigurationForUser(brightnessConfiguration, i, str);
    }

    @SystemApi
    public BrightnessConfiguration getBrightnessConfiguration() {
        return getBrightnessConfigurationForUser(this.mContext.getUserId());
    }

    public BrightnessConfiguration getBrightnessConfigurationForUser(int i) {
        return this.mGlobal.getBrightnessConfigurationForUser(i);
    }

    @SystemApi
    public BrightnessConfiguration getDefaultBrightnessConfiguration() {
        return this.mGlobal.getDefaultBrightnessConfiguration();
    }

    public boolean isMinimalPostProcessingRequested(int i) {
        return this.mGlobal.isMinimalPostProcessingRequested(i);
    }

    public void setTemporaryBrightness(int i, float f) {
        this.mGlobal.setTemporaryBrightness(i, f);
    }

    public void setBrightness(int i, float f) {
        this.mGlobal.setBrightness(i, f);
    }

    public float getBrightness(int i) {
        return this.mGlobal.getBrightness(i);
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mGlobal.setTemporaryAutoBrightnessAdjustment(f);
    }

    @SystemApi
    public Pair<float[], float[]> getMinimumBrightnessCurve() {
        return this.mGlobal.getMinimumBrightnessCurve();
    }

    public void setGlobalUserPreferredDisplayMode(Display.Mode mode) {
        this.mGlobal.setUserPreferredDisplayMode(-1, new Display.Mode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate()));
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

    public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
        this.mGlobal.setShouldAlwaysRespectAppRequestedMode(z);
    }

    public boolean shouldAlwaysRespectAppRequestedMode() {
        return this.mGlobal.shouldAlwaysRespectAppRequestedMode();
    }

    @SystemApi
    public boolean isAlwaysOnDisplayCurrentlyAvailable() {
        return getAmbientDisplayConfiguration().alwaysOnAvailableForUser(this.mContext.getUserId());
    }

    public boolean supportsSeamlessRefreshRateSwitching() {
        return this.mContext.getResources().getBoolean(R.bool.config_supportsSeamlessRefreshRateSwitching);
    }

    public void setRefreshRateSwitchingType(int i) {
        this.mGlobal.setRefreshRateSwitchingType(i);
    }

    public int getMatchContentFrameRateUserPreference() {
        return toMatchContentFrameRateSetting(this.mGlobal.getRefreshRateSwitchingType());
    }

    private int toMatchContentFrameRateSetting(int i) {
        if (i == 0) {
            return 0;
        }
        if (i != 1) {
            if (i == 2) {
                return 2;
            }
            if (i != 3) {
                Slog.e(TAG, i + " is not a valid value of switching type.");
                return -1;
            }
        }
        return 1;
    }

    private AmbientDisplayConfiguration getAmbientDisplayConfiguration() {
        synchronized (this) {
            if (this.mAmbientDisplayConfiguration == null) {
                this.mAmbientDisplayConfiguration = new AmbientDisplayConfiguration(this.mContext);
            }
        }
        return this.mAmbientDisplayConfiguration;
    }

    @SystemApi
    public static VirtualDisplay createVirtualDisplay(String str, int i, int i2, int i3, Surface surface) {
        IDisplayManager iDisplayManagerAsInterface = IDisplayManager.Stub.asInterface(ServiceManager.getService(Context.DISPLAY_SERVICE));
        IPackageManager iPackageManagerAsInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        VirtualDisplayConfig.Builder displayIdToMirror = new VirtualDisplayConfig.Builder(str, i, i2, 1).setFlags(16).setDisplayIdToMirror(i3);
        if (surface != null) {
            displayIdToMirror.setSurface(surface);
        }
        VirtualDisplayConfig virtualDisplayConfigBuild = displayIdToMirror.build();
        try {
            String[] packagesForUid = iPackageManagerAsInterface.getPackagesForUid(Process.myUid());
            String str2 = packagesForUid == null ? null : packagesForUid[0];
            DisplayManagerGlobal.VirtualDisplayCallback virtualDisplayCallback = new DisplayManagerGlobal.VirtualDisplayCallback(null, null);
            try {
                return DisplayManagerGlobal.getInstance().createVirtualDisplayWrapper(virtualDisplayConfigBuild, virtualDisplayCallback, iDisplayManagerAsInterface.createVirtualDisplay(virtualDisplayConfigBuild, virtualDisplayCallback, null, str2));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void requestDisplayModes(int i, int[] iArr) {
        if (iArr != null && iArr.length == 0) {
            throw new IllegalArgumentException("requestDisplayModes: modesIds can't be empty");
        }
        this.mGlobal.requestDisplayModes(i, iArr);
    }

    public float[] getDozeBrightnessSensorValueToBrightness(int i) {
        return this.mGlobal.getDozeBrightnessSensorValueToBrightness(i);
    }

    public float getDefaultDozeBrightness(int i) {
        return this.mGlobal.getDefaultDozeBrightness(i);
    }

    private int getDisplayIdToMirror() {
        if (this.mDisplayIdToMirror == -1) {
            UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
            this.mDisplayIdToMirror = userManager.isVisibleBackgroundUsersSupported() ? userManager.getMainDisplayIdAssignedToUser() : 0;
        }
        return this.mDisplayIdToMirror;
    }

    public DisplayTopology getDisplayTopology() {
        return this.mGlobal.getDisplayTopology();
    }

    public void setDisplayTopology(DisplayTopology displayTopology) {
        this.mGlobal.setDisplayTopology(displayTopology);
    }

    public void registerTopologyListener(Executor executor, Consumer<DisplayTopology> consumer) {
        this.mGlobal.registerTopologyListener(executor, consumer, ActivityThread.currentPackageName());
    }

    public void unregisterTopologyListener(Consumer<DisplayTopology> consumer) {
        this.mGlobal.unregisterTopologyListener(consumer);
    }

    private static final class WeakDisplayCache {
        private final SparseArray<WeakReference<Display>> mDisplayCache;

        private WeakDisplayCache() {
            this.mDisplayCache = new SparseArray<>();
        }

        Display get(int i) {
            WeakReference<Display> weakReference = this.mDisplayCache.get(i);
            if (weakReference == null) {
                return null;
            }
            return weakReference.get();
        }

        void put(Display display) {
            removeStaleEntries();
            this.mDisplayCache.put(display.getDisplayId(), new WeakReference<>(display));
        }

        private void removeStaleEntries() {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mDisplayCache.size(); i++) {
                if (this.mDisplayCache.valueAt(i).get() == null) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                this.mDisplayCache.removeAt(((Integer) arrayList.get(i2)).intValue());
            }
        }
    }

    public int getHiddenDisplayId(String str) {
        return getHiddenDisplayId(str, -1);
    }

    public int getHiddenDisplayId(int i) {
        return getHiddenDisplayId(null, i);
    }

    private int getHiddenDisplayId(String str, int i) {
        for (Display display : getDisplays()) {
            if (display.getType() == 5 && (display.getFlags() & 65536) != 0 && ((str != null && str.equals(display.getOwnerPackageName())) || (UserHandle.isApp(i) && display.getOwnerUid() == i))) {
                int displayId = display.getDisplayId();
                Slog.d(TAG_SPEG, "Display #" + displayId + " (SpegVirtualDisplay), owner: " + display.getOwnerPackageName() + ":" + display.getOwnerUid());
                return displayId;
            }
        }
        return -1;
    }

    public void setTemporaryBrightness(int i, boolean z) {
        this.mGlobal.setTemporaryBrightnessForSlowChange(0, BrightnessSynchronizer.brightnessIntToFloat(i), z);
    }

    public void setTemporaryBrightness(int i, int i2, boolean z) {
        this.mGlobal.setTemporaryBrightnessForSlowChange(i, BrightnessSynchronizer.brightnessIntToFloat(i2), z);
    }

    public void semSetTemporaryBrightness(int i) {
        Slog.i(TAG, "semSetTemporaryBrightness: brightness=" + i);
        this.mGlobal.setTemporaryBrightness(0, BrightnessSynchronizer.brightnessIntToFloat(i));
    }

    public void semSetTemporaryBrightness(float f) {
        this.mGlobal.setTemporaryBrightness(0, f);
    }

    public void semSetTemporaryBrightness(int i, float f) {
        this.mGlobal.setTemporaryBrightness(i, f);
    }

    public void semStartScanWifiDisplays() {
        Log.d(TAG, "semStartScanWifiDisplays" + Log.getStackTraceString(new Throwable()));
        this.mGlobal.startWifiDisplayScan();
    }

    public void semStartScanWifiDisplays(int i) {
        Log.d(TAG, "semStartScanWifiDisplays, scanChannel = " + i + Log.getStackTraceString(new Throwable()));
        this.mGlobal.startWifiDisplayScan(i);
    }

    public void semStartScanWifiDisplays(int i, int i2) {
        Log.d(TAG, "semStartScanWifiDisplays, scanChannel = " + i + ", interval = " + i2 + Log.getStackTraceString(new Throwable()));
        this.mGlobal.startWifiDisplayScan(i, i2);
    }

    public void semStopScanWifiDisplays() {
        Log.d(TAG, "semStopScanWifiDisplays" + Log.getStackTraceString(new Throwable()));
        this.mGlobal.stopWifiDisplayScan();
    }

    public void semRegisterDeviceStatusListener(SemDeviceStatusListener semDeviceStatusListener, Handler handler) {
        this.mGlobal.registerDeviceListener(semDeviceStatusListener, handler);
    }

    public void semUnregisterDeviceStatusListener(SemDeviceStatusListener semDeviceStatusListener) {
        this.mGlobal.unregisterDeviceListener(semDeviceStatusListener);
    }

    public void semRegisterDisplayVolumeListener(SemDisplayVolumeListener semDisplayVolumeListener, Handler handler) {
        this.mGlobal.registerDisplayVolumeListener(semDisplayVolumeListener, handler);
    }

    public void semUnregisterDisplayVolumeListener(SemDisplayVolumeListener semDisplayVolumeListener) {
        this.mGlobal.unregisterDisplayVolumeListener(semDisplayVolumeListener);
    }

    public void semRegisterDisplayVolumeKeyListener(SemDisplayVolumeKeyListener semDisplayVolumeKeyListener, Handler handler) {
        this.mGlobal.registerDisplayVolumeKeyListener(semDisplayVolumeKeyListener, handler);
    }

    public void semUnregisterDisplayVolumeKeyListener(SemDisplayVolumeKeyListener semDisplayVolumeKeyListener) {
        this.mGlobal.unregisterDisplayVolumeKeyListener(semDisplayVolumeKeyListener);
    }

    public void semRegisterWifiDisplayParameterListener(SemWifiDisplayParameterListener semWifiDisplayParameterListener, Handler handler) {
        this.mGlobal.registerWifiDisplayParameterListener(semWifiDisplayParameterListener, handler);
    }

    public void semUnregisterWifiDisplayParameterListener(SemWifiDisplayParameterListener semWifiDisplayParameterListener) {
        this.mGlobal.unregisterWifiDisplayParameterListener(semWifiDisplayParameterListener);
    }

    public void semSetDeviceVolume(int i) {
        this.mGlobal.setDeviceVolume(i);
    }

    public boolean semIsWifiDisplayWithPinSupported(String str) {
        return this.mGlobal.isWifiDisplayWithPinSupported(str);
    }

    public String semGetPresentationOwner(int i) {
        return this.mGlobal.getPresentationOwner(i);
    }

    public boolean semIsFitToActiveDisplay() {
        return this.mGlobal.isFitToActiveDisplay();
    }

    public void semFitToActiveDisplay(boolean z) {
        this.mGlobal.fitToActiveDisplay(z);
    }

    public boolean semRequestSetWifiDisplayParameters(List<SemWifiDisplayParameter> list) {
        Log.d(TAG, "semRequestSetWifiDisplayParameters : " + list);
        return this.mGlobal.requestSetWifiDisplayParameters(list);
    }

    public boolean semRequestWifiDisplayParameter(String str, SemWifiDisplayParameter semWifiDisplayParameter) {
        Log.d(TAG, "semRequestWifiDisplayParameter, parameterGroup : " + str + ", parameter : " + semWifiDisplayParameter);
        return this.mGlobal.requestWifiDisplayParameter(str, semWifiDisplayParameter);
    }

    public int semGetScreenSharingStatus() {
        return this.mGlobal.getScreenSharingStatus();
    }

    public void semSetScreenSharingStatus(int i) {
        this.mGlobal.setScreenSharingStatus(i);
    }

    public void semSetActiveDlnaState(SemDlnaDevice semDlnaDevice, int i) {
        Log.d(TAG, "semSetActiveDlnaState" + Log.getStackTraceString(new Throwable()));
        if (semDlnaDevice != null) {
            semDlnaDevice.setConnectionState(i);
            this.mGlobal.setDlnaDevice(semDlnaDevice);
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

    public void semConnectWifiDisplay(SemWifiDisplayConfig semWifiDisplayConfig, SemWifiDisplayConnectionCallback semWifiDisplayConnectionCallback, Handler handler) {
        Log.d(TAG, "semConnectWifiDisplay : config = " + semWifiDisplayConfig.toString());
        this.mGlobal.connectWifiDisplay(semWifiDisplayConfig, semWifiDisplayConnectionCallback, handler);
    }

    public void semDisconnectWifiDisplay() {
        this.mGlobal.disconnectWifiDisplay();
    }

    public int semSetWifiDisplayConfiguration(String str, boolean z) {
        Log.w(TAG, "semSetWifiDisplayConfiguration key " + str + " value " + z);
        if (str.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SET_VOLUME_MUTE)) {
            this.mGlobal.setDeviceVolumeMuted(z);
            return 0;
        }
        if (!str.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SEND_VOLUME_MUTE_KEY_EVENT)) {
            return -1;
        }
        if (z) {
            this.mGlobal.setVolumeKeyEvent(2);
        } else {
            this.mGlobal.setVolumeKeyEvent(3);
        }
        return 0;
    }

    public int semSetWifiDisplayConfiguration(String str, int i) {
        Log.w(TAG, "semSetWifiDisplayConfiguration key " + str + " value " + i);
        if (str.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SEND_VOLUME_KEY_EVENT)) {
            this.mGlobal.setVolumeKeyEvent(i);
            return 0;
        }
        if (!str.equals(SemScreenSharingConstants.KEY_CONFIGURATION_SET_DISPLAY_VOLUME)) {
            return -1;
        }
        semSetDeviceVolume(i);
        return 0;
    }

    public int semSetWifiDisplayConfiguration(String str, String str2) {
        Log.d(TAG, "semSetWifiDisplayConfiguration key " + str + " String value " + str2);
        this.mGlobal.setWifiDisplayParam(str, str2);
        return 0;
    }

    public Object semGetWifiDisplayConfiguration(String str) {
        if (str.equals(SemScreenSharingConstants.KEY_CONFIGURATION_IS_VOLUME_MUTE)) {
            return Boolean.valueOf(this.mGlobal.isDeviceVolumeMuted());
        }
        if (str.equals(SemScreenSharingConstants.KEY_CONFIGURATION_MAX_VOLUME_LEVEL)) {
            return Integer.valueOf(this.mGlobal.getDeviceMaxVolume());
        }
        if (str.equals(SemScreenSharingConstants.KEY_CONFIGURATION_MIN_VOLUME_LEVEL)) {
            return Integer.valueOf(this.mGlobal.getDeviceMinVolume());
        }
        return null;
    }

    public void semSetWifiDisplayVolume(int i) {
        semSetDeviceVolume(i);
    }

    public void semSetWifiDisplayVolumeMuted(boolean z) {
        this.mGlobal.setDeviceVolumeMuted(z);
    }

    public SemWifiDisplayStatus semGetWifiDisplayStatus() {
        return new SemWifiDisplayStatus(this.mGlobal.getWifiDisplayStatus());
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void semEnableWifiDisplay(String str, String str2, int i, String str3, String str4, String str5, boolean z) throws JSONException {
        int i2;
        int i3;
        String str6;
        int i4;
        char c = i == 16 ? (char) 3 : (char) 2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (i == 14) {
            i2 = 32;
            i3 = 0;
        } else if (i == 19 || i == 16) {
            i2 = 0;
            i3 = 3;
        } else if (i == 17) {
            i2 = 0;
            i3 = 2;
        } else {
            i2 = 0;
            i3 = 0;
        }
        if (str3 != null) {
            if (str3.isEmpty()) {
                str6 = SemWifiDisplayParameter.GET_PARAMETER;
            } else {
                JSONObject jSONObject = new JSONObject(str3);
                if (jSONObject.has(SemWifiDisplayParameter.GET_PARAMETER)) {
                    JSONArray jSONArray = jSONObject.getJSONArray(SemWifiDisplayParameter.GET_PARAMETER);
                    str6 = SemWifiDisplayParameter.GET_PARAMETER;
                    int i5 = 0;
                    while (i5 < jSONArray.length()) {
                        try {
                            i4 = i2;
                            try {
                                arrayList2.add(new SemWifiDisplayParameter(jSONArray.getString(i5)));
                                i5++;
                                i2 = i4;
                            } catch (JSONException unused) {
                            }
                        } catch (JSONException unused2) {
                        }
                    }
                } else {
                    str6 = SemWifiDisplayParameter.GET_PARAMETER;
                }
                i4 = i2;
                if (jSONObject.has(SemWifiDisplayParameter.SET_PARAMETER)) {
                    JSONArray jSONArray2 = jSONObject.getJSONArray(SemWifiDisplayParameter.SET_PARAMETER);
                    int i6 = 0;
                    while (i6 < jSONArray2.length()) {
                        String[] strArrSplit = jSONArray2.getString(i6).split(": ");
                        JSONArray jSONArray3 = jSONArray2;
                        if (strArrSplit.length == 2) {
                            arrayList3.add(new SemWifiDisplayParameter(strArrSplit[0], strArrSplit[1]));
                        }
                        i6++;
                        jSONArray2 = jSONArray3;
                    }
                }
                if (jSONObject.has(SemWifiDisplayParameter.KEY_SCAMBLE_SUPPORT)) {
                    arrayList.add(new SemWifiDisplayParameter(SemWifiDisplayParameter.KEY_SCAMBLE_SUPPORT, jSONObject.getString(SemWifiDisplayParameter.KEY_SCAMBLE_SUPPORT)));
                }
            }
            i4 = i2;
        }
        if (c == 3) {
            SemWifiDisplayConfig semWifiDisplayConfigBuild = new SemWifiDisplayConfig.Builder().setUsbConnection(str, str2, str4, str5).setMode(i3).addFlags(i4).addParameters(SemWifiDisplayParameter.INIT_PARAMETER, arrayList).addParameters(str6, arrayList2).addParameters(SemWifiDisplayParameter.SET_PARAMETER, arrayList3).build();
            Log.d(TAG, "semEnableWifiDisplay : deviceType = " + i + ", config = " + semWifiDisplayConfigBuild.toString() + ", option = " + str3);
            this.mGlobal.connectWifiDisplay(semWifiDisplayConfigBuild, null, null);
            return;
        }
        SemWifiDisplayConfig semWifiDisplayConfigBuild2 = new SemWifiDisplayConfig.Builder().setApConnection(str, str2, str4, str5).setMode(i3).addFlags(i4).addParameters(SemWifiDisplayParameter.INIT_PARAMETER, arrayList).addParameters(str6, arrayList2).addParameters(SemWifiDisplayParameter.SET_PARAMETER, arrayList3).build();
        Log.d(TAG, "semEnableWifiDisplay : deviceType = " + i + ", config = " + semWifiDisplayConfigBuild2.toString() + ", option = " + str3);
        this.mGlobal.connectWifiDisplay(semWifiDisplayConfigBuild2, null, null);
    }

    public void semDisconnectDevice() {
        this.mGlobal.disconnectWifiDisplay();
    }

    public void semPauseWifiDisplay() {
        this.mGlobal.pauseWifiDisplay();
    }

    public void semResumeWifiDisplay() {
        this.mGlobal.resumeWifiDisplay();
    }

    public void semSetActivityState(SemWifiDisplayAppState semWifiDisplayAppState) {
        if (semWifiDisplayAppState == SemWifiDisplayAppState.SETUP) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "wifi_display_on", 1);
            return;
        }
        if (semWifiDisplayAppState == SemWifiDisplayAppState.PAUSE) {
            this.mGlobal.stopWifiDisplayScan();
        } else if (semWifiDisplayAppState == SemWifiDisplayAppState.RESUME) {
            this.mGlobal.startWifiDisplayScan();
        } else if (semWifiDisplayAppState == SemWifiDisplayAppState.TEARDOWN) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "wifi_display_on", 0);
        }
    }

    public void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str, List<String> list, List<String> list2, List<String> list3) {
        this.mGlobal.setBrightnessConfigurationForUser(brightnessConfiguration, i, str, list, list2, list3);
    }

    public void setBrightnessConfigurationForDisplay(BrightnessConfiguration brightnessConfiguration, String str, int i, String str2, List<String> list, List<String> list2, List<String> list3) {
        this.mGlobal.setBrightnessConfigurationForDisplay(brightnessConfiguration, str, i, str2, list, list2, list3);
    }

    public void resetBrightnessConfiguration() {
        this.mGlobal.resetBrightnessConfigurationForUser(this.mContext.getUserId(), this.mContext.getPackageName());
    }

    public void setBackupBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, int i, String str, int i2) {
        this.mGlobal.setBackupBrightnessConfiguration(brightnessConfiguration, i, str, i2);
    }

    public BrightnessConfiguration getBackupBrightnessConfiguration(int i, int i2) {
        return this.mGlobal.getBackupBrightnessConfiguration(i, i2);
    }

    public int convertToBrightness(float f) {
        return this.mGlobal.convertToBrightness(f);
    }

    public float getAdaptiveBrightness(int i, float f) {
        return this.mGlobal.getAdaptiveBrightness(i, f);
    }

    public void registerHbmBrightnessListener(DisplayHbmBrightnessListener displayHbmBrightnessListener) {
        this.mGlobal.registerHbmBrightnessListener(displayHbmBrightnessListener);
    }

    public void unregisterHbmBrightnessListener(DisplayHbmBrightnessListener displayHbmBrightnessListener) {
        this.mGlobal.unregisterHbmBrightnessListener(displayHbmBrightnessListener);
    }
}
