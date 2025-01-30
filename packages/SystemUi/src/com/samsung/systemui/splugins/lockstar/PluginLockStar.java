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
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = PluginLockStar.ACTION, version = 1007)
/* loaded from: classes3.dex */
public interface PluginLockStar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_LOCK_STAR";
    public static final String CLOCK_TYPE = "Clock";
    public static final String COMPLICATION_WIDGET_TYPE = "ComplicationWidget";
    public static final int MAIN_DISPLAY = 0;
    public static final int MAJOR_VERSION = 1;
    public static final int MINOR_VERSION = 7;
    public static final String MUSIC_TYPE = "Music";
    public static final String NOTIFICATION_TYPE = "Notification";
    public static final String STICKER_TYPE = "Sticker";
    public static final int SUB_DISPLAY = 1;
    public static final int VERSION = 1007;
    public static final String WIDGET_TYPE = "Widget";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Modifier<T> extends Consumer<T> {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PluginLockStarCallback {
        <T> T get(String str);

        int getResourceId(String str, String str2, String str3);

        default int getVersion() {
            return 1007;
        }

        void onChangedLockStarEnabled(boolean z);

        void onUpdateModifiers(Map<String, Modifier<?>> map);
    }

    void dump(PrintWriter printWriter, String[] strArr);

    Bundle getAODData(boolean z);

    <T> Modifier<T> getModifier(String str);

    @Requires(target = PluginLockStar.class, version = 1002)
    <T> Supplier<T> getSupplier(String str);

    @Override // com.samsung.systemui.splugins.SPlugin
    default int getVersion() {
        return 1007;
    }

    void init(PluginLockStarCallback pluginLockStarCallback);

    boolean isLockStarEnabled();

    @Requires(target = PluginLockStar.class, version = 1007)
    boolean isPositionSynchronized(String str);

    @Requires(target = PluginLockStar.class, version = VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI)
    boolean isTouchable(MotionEvent motionEvent);

    @Requires(target = PluginLockStar.class, version = 1007)
    void onFinishedGoingToSleep();

    @Requires(target = PluginLockStar.class, version = 1007)
    void onFinishedWakingUp();

    @Requires(target = PluginLockStar.class, version = 1006)
    boolean onInterceptTouchEvent(MotionEvent motionEvent);

    @Requires(target = PluginLockStar.class, version = 1007)
    void onStartedGoingToSleep();

    @Requires(target = PluginLockStar.class, version = 1007)
    void onStartedWakingUp();

    @Requires(target = PluginLockStar.class, version = 1007)
    Bundle requestExtraData(Bundle bundle);

    @Requires(target = PluginLockStar.class, version = 1007)
    void setDarkAmount(Float f);

    @Requires(target = PluginLockStar.class, version = 1005)
    default void onChangedDisplay(int i) {
    }

    @Requires(target = PluginLockStar.class, version = 1005)
    default void onChangedOrientation(boolean z) {
    }

    @Requires(target = PluginLockStar.class, version = 1003)
    default void onPackageAdded(String str) {
    }

    @Requires(target = PluginLockStar.class, version = 1003)
    default void onPackageChanged(String str) {
    }

    @Requires(target = PluginLockStar.class, version = 1005)
    default void onChangedDisplaySize(int i, int i2) {
    }

    @Requires(target = PluginLockStar.class, version = 1003)
    default void onPackageRemoved(String str, boolean z) {
    }

    @Requires(target = PluginLockStar.class, version = 1005)
    default void onChangedItemSize(String str, int i, int i2) {
    }
}
