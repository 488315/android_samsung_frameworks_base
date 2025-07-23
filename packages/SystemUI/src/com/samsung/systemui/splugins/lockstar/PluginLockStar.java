package com.samsung.systemui.splugins.lockstar;

import android.os.Bundle;
import android.view.MotionEvent;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import com.samsung.systemui.splugins.annotations.Requires;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = PluginLockStar.ACTION, version = 4002)
/* loaded from: classes4.dex */
public interface PluginLockStar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_LOCK_STAR";
    public static final String CLOCK_TYPE = "Clock";
    public static final String COMPLICATION_WIDGET_TYPE = "ComplicationWidget";
    public static final String INDICATOR_TYPE = "Indicator";
    public static final String LOCK_ICON_TYPE = "LockIcon";
    public static final int MAIN_DISPLAY = 0;
    public static final int MAJOR_VERSION = 4;
    public static final int MINOR_VERSION = 2;
    public static final String MUSIC_TYPE = "Music";
    public static final String NOTIFICATION_TYPE = "Notification";
    public static final String PUNCH_HOLE_TYPE = "PunchHole";
    public static final String STATUS_BAR_TYPE = "StatusBar";
    public static final String STICKER_TYPE = "Sticker";
    public static final int SUB_DISPLAY = 1;
    public static final String TIMEOUT_TYPE = "Timeout";
    public static final String TYPE_KEY = "type";
    public static final int VERSION = 4002;
    public static final String WIDGET_TYPE = "Widget";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Deprecated
    public interface Modifier<T> extends Consumer<T> {
    }

    void dump(PrintWriter printWriter, String[] strArr);

    Bundle getAODData(boolean z);

    default <T> Function<T, Boolean> getFunction(String str) {
        return null;
    }

    LockStarValues getLockStarValues();

    @Deprecated
    <T> Modifier<T> getModifier(String str);

    @Requires(target = PluginLockStar.class, version = 1002)
    <T> Supplier<T> getSupplier(String str);

    @Override // com.samsung.systemui.splugins.SPlugin
    default int getVersion() {
        return 4002;
    }

    void init(PluginLockStarCallback pluginLockStarCallback);

    boolean isLockStarEnabled();

    @Requires(target = PluginLockStar.class, version = 1007)
    boolean isPositionSynchronized(String str);

    @Requires(target = PluginLockStar.class, version = VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI)
    boolean isTouchable(MotionEvent motionEvent);

    @Requires(target = PluginLockStar.class, version = 1005)
    void onChangedDisplay(int i);

    @Requires(target = PluginLockStar.class, version = 1005)
    void onChangedDisplaySize(int i, int i2);

    @Requires(target = PluginLockStar.class, version = 1005)
    void onChangedItemSize(String str, int i, int i2);

    @Requires(target = PluginLockStar.class, version = 1005)
    void onChangedOrientation(boolean z);

    @Requires(target = PluginLockStar.class, version = 1007)
    void onFinishedGoingToSleep();

    @Requires(target = PluginLockStar.class, version = 1007)
    void onFinishedWakingUp();

    @Requires(target = PluginLockStar.class, version = 1006)
    boolean onInterceptTouchEvent(MotionEvent motionEvent);

    void onMediaNowBarExpandStateChanged(boolean z);

    @Requires(target = PluginLockStar.class, version = 1003)
    void onPackageChanged(String str);

    @Requires(target = PluginLockStar.class, version = 1003)
    void onPackageRemoved(String str, boolean z);

    @Requires(target = PluginLockStar.class, version = 1007)
    void onStartedGoingToSleep();

    @Requires(target = PluginLockStar.class, version = 1007)
    void onStartedWakingUp();

    @Requires(target = PluginLockStar.class, version = 1007)
    Bundle requestExtraData(Bundle bundle);

    @Requires(target = PluginLockStar.class, version = 1007)
    void setDarkAmount(Float f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PluginLockStarCallback {
        <T> T get(String str);

        int getResourceId(String str, String str2, String str3);

        default int getVersion() {
            return 4002;
        }

        default boolean isRunningNoNeedUnlockService(String str) {
            return false;
        }

        void onChangedLockStarData(boolean z, Bundle bundle);

        @Deprecated
        void onChangedLockStarEnabled(boolean z);

        void onUpdateModifiers(Map<String, Modifier<?>> map);

        default Bundle request(Bundle bundle) {
            return null;
        }

        default void launchShortcutApp(String str) {
        }
    }

    @Requires(target = PluginLockStar.class, version = 1003)
    default void onPackageAdded(String str) {
    }

    default void onRequestResult(Bundle bundle) {
    }

    default void onChangedNoNeedUnlockServiceState(String str, boolean z) {
    }
}
