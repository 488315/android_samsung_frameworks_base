package com.android.systemui.qs.tiles.impl.alarm.domain.model;

import android.app.AlarmManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface AlarmTileModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NextAlarmSet implements AlarmTileModel {
        public final AlarmManager.AlarmClockInfo alarmClockInfo;
        public final boolean is24HourFormat;

        public NextAlarmSet(boolean z, AlarmManager.AlarmClockInfo alarmClockInfo) {
            this.is24HourFormat = z;
            this.alarmClockInfo = alarmClockInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NextAlarmSet)) {
                return false;
            }
            NextAlarmSet nextAlarmSet = (NextAlarmSet) obj;
            return this.is24HourFormat == nextAlarmSet.is24HourFormat && Intrinsics.areEqual(this.alarmClockInfo, nextAlarmSet.alarmClockInfo);
        }

        public final int hashCode() {
            return this.alarmClockInfo.hashCode() + (Boolean.hashCode(this.is24HourFormat) * 31);
        }

        public final String toString() {
            return "NextAlarmSet(is24HourFormat=" + this.is24HourFormat + ", alarmClockInfo=" + this.alarmClockInfo + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NoAlarmSet implements AlarmTileModel {
        public static final NoAlarmSet INSTANCE = new NoAlarmSet();

        private NoAlarmSet() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoAlarmSet);
        }

        public final int hashCode() {
            return 167450716;
        }

        public final String toString() {
            return "NoAlarmSet";
        }
    }
}
