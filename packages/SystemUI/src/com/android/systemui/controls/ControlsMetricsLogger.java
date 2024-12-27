package com.android.systemui.controls;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.controls.ui.ControlViewHolder;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface ControlsMetricsLogger {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class ControlsEvents implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ ControlsEvents[] $VALUES;
        public static final ControlsEvents CONTROL_DRAG;
        public static final ControlsEvents CONTROL_REFRESH_BEGIN;
        public static final ControlsEvents CONTROL_REFRESH_END;
        public static final ControlsEvents CONTROL_TOUCH;
        private final int metricId;

        static {
            ControlsEvents controlsEvents = new ControlsEvents("CONTROL_TOUCH", 0, 714);
            CONTROL_TOUCH = controlsEvents;
            ControlsEvents controlsEvents2 = new ControlsEvents("CONTROL_DRAG", 1, 713);
            CONTROL_DRAG = controlsEvents2;
            ControlsEvents controlsEvents3 = new ControlsEvents("CONTROL_LONG_PRESS", 2, 715);
            ControlsEvents controlsEvents4 = new ControlsEvents("CONTROL_REFRESH_BEGIN", 3, 716);
            CONTROL_REFRESH_BEGIN = controlsEvents4;
            ControlsEvents controlsEvents5 = new ControlsEvents("CONTROL_REFRESH_END", 4, 717);
            CONTROL_REFRESH_END = controlsEvents5;
            ControlsEvents[] controlsEventsArr = {controlsEvents, controlsEvents2, controlsEvents3, controlsEvents4, controlsEvents5};
            $VALUES = controlsEventsArr;
            EnumEntriesKt.enumEntries(controlsEventsArr);
        }

        private ControlsEvents(String str, int i, int i2) {
            this.metricId = i2;
        }

        public static ControlsEvents valueOf(String str) {
            return (ControlsEvents) Enum.valueOf(ControlsEvents.class, str);
        }

        public static ControlsEvents[] values() {
            return (ControlsEvents[]) $VALUES.clone();
        }

        public final int getId() {
            return this.metricId;
        }
    }

    default void drag(ControlViewHolder controlViewHolder, boolean z) {
        ((ControlsMetricsLoggerImpl) this).log(ControlsEvents.CONTROL_DRAG.getId(), controlViewHolder.getDeviceType(), controlViewHolder.uid, z);
    }

    default void refreshBegin(int i, boolean z) {
        ControlsMetricsLoggerImpl controlsMetricsLoggerImpl = (ControlsMetricsLoggerImpl) this;
        controlsMetricsLoggerImpl.instanceId = controlsMetricsLoggerImpl.instanceIdSequence.newInstanceId().getId();
        controlsMetricsLoggerImpl.log(ControlsEvents.CONTROL_REFRESH_BEGIN.getId(), 0, i, z);
    }

    default void refreshEnd(ControlViewHolder controlViewHolder) {
        ((ControlsMetricsLoggerImpl) this).log(ControlsEvents.CONTROL_REFRESH_END.getId(), controlViewHolder.getDeviceType(), controlViewHolder.uid, false);
    }

    default void touch(ControlViewHolder controlViewHolder, boolean z) {
        ((ControlsMetricsLoggerImpl) this).log(ControlsEvents.CONTROL_TOUCH.getId(), controlViewHolder.getDeviceType(), controlViewHolder.uid, z);
    }
}
