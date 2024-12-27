package com.android.systemui.statusbar.phone;

import android.metrics.LogMaker;
import android.util.ArrayMap;
import android.util.EventLog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.EventLogConstants;

public final class LockscreenGestureLogger {
    public final ArrayMap mLegacyMap = new ArrayMap(11);
    public final MetricsLogger mMetricsLogger;

    public enum LockscreenUiEvent implements UiEventLogger.UiEventEnum {
        LOCKSCREEN_PULL_SHADE_OPEN(539),
        LOCKSCREEN_LOCK_TAP(540),
        LOCKSCREEN_QUICK_SETTINGS_OPEN(541),
        LOCKSCREEN_UNLOCKED_QUICK_SETTINGS_OPEN(542),
        LOCKSCREEN_LOCK_SHOW_HINT(543),
        LOCKSCREEN_NOTIFICATION_SHADE_QUICK_SETTINGS_OPEN(544),
        LOCKSCREEN_DIALER(545),
        LOCKSCREEN_CAMERA(546),
        LOCKSCREEN_UNLOCK(547),
        LOCKSCREEN_NOTIFICATION_FALSE_TOUCH(548),
        LOCKSCREEN_UNLOCKED_NOTIFICATION_PANEL_EXPAND(549),
        LOCKSCREEN_SWITCH_USER_TAP(934);

        private final int mId;

        LockscreenUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public LockscreenGestureLogger(MetricsLogger metricsLogger) {
        this.mMetricsLogger = metricsLogger;
        for (int i = 0; i < 11; i++) {
            this.mLegacyMap.put(Integer.valueOf(EventLogConstants.METRICS_GESTURE_TYPE_MAP[i]), Integer.valueOf(i));
        }
    }

    public final void write(int i, int i2, int i3) {
        this.mMetricsLogger.write(new LogMaker(i).setType(4).addTaggedData(826, Integer.valueOf(i2)).addTaggedData(827, Integer.valueOf(i3)));
        Integer num = (Integer) this.mLegacyMap.get(Integer.valueOf(i));
        EventLog.writeEvent(36021, Integer.valueOf(num == null ? 0 : num.intValue()), Integer.valueOf(i2), Integer.valueOf(i3));
    }
}
