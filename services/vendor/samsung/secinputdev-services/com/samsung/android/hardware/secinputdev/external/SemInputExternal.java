package com.samsung.android.hardware.secinputdev.external;

/* loaded from: classes.dex */
public interface SemInputExternal {

    public interface IExternalEventRegister {
        boolean registerBroadcastReceiver(Event event, IBroadcastReceiver iBroadcastReceiver);

        boolean registerServiceListener(Event event, IServiceListener iServiceListener);
    }

    public enum Event {
        OBSERVER_UEVENT(1),
        LISTENER_DISPLAY_STATE(11),
        LISTENER_DISPLAY(12),
        LISTENER_FOLD_STATE(13),
        LISTENER_DEX(14),
        LISTENER_PROX_LP_SCAN(15),
        LISTENER_SENSOR_WATCH(16),
        SETTING_AOT(21),
        SETTING_SPEN(22),
        SETTING_TSP_EXTRA(23),
        BROADCAST_BATTERY(31),
        BROADCAST_SHUTDOWN(32),
        BROADCAST_LAZY_BOOT(33),
        BROADCAST_GAME(34),
        BROADCAST_COVER(35),
        BROADCAST_USER_SWITCHED(36),
        BROADCAST_RF_DETECTED(37),
        BROADCAST_RF_OFF_DETECTED(38),
        BROADCAST_BATTERY_EXTRA(39);

        private int event;

        Event(int event) {
            this.event = event;
        }
    }

    public interface IServiceListener {
        default void onSemUEvent(String result) {}

        default void onDisplayStateChanged(
                boolean isEarly, int stateLogical, int statePhysical, int displayType) {}

        default void onDisplayChanged(int rotation) {}

        default void onFoldStateChanged(boolean folded) {}

        default void onDesktopModeStateChanged(int mode) {}

        default void onLpScanSensorChanged(int mode) {}

        default void onBodyDetected(int mode) {}
    }

    public interface IBroadcastReceiver {
        default void onBatteryChanged(int status, int type) {}

        default void onBatteryTxIdChanged(int tx_id) {}

        default void onShutdown() {}

        default void onLazybootCompleted() {}

        default void onGameMode(String gameMode, String scanRate, String fastResponse) {}

        default void onCoverAttached(boolean attached, int cover_type) {}

        default void onUserSwitched() {}

        default void onRFDetected(boolean on) {}
    }
}
