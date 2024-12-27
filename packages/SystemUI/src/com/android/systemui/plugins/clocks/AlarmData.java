package com.android.systemui.plugins.clocks;

import kotlin.jvm.internal.Intrinsics;

public final class AlarmData {
    private final String descriptionId;
    private final Long nextAlarmMillis;

    public AlarmData(Long l, String str) {
        this.nextAlarmMillis = l;
        this.descriptionId = str;
    }

    public static /* synthetic */ AlarmData copy$default(AlarmData alarmData, Long l, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            l = alarmData.nextAlarmMillis;
        }
        if ((i & 2) != 0) {
            str = alarmData.descriptionId;
        }
        return alarmData.copy(l, str);
    }

    public final Long component1() {
        return this.nextAlarmMillis;
    }

    public final String component2() {
        return this.descriptionId;
    }

    public final AlarmData copy(Long l, String str) {
        return new AlarmData(l, str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlarmData)) {
            return false;
        }
        AlarmData alarmData = (AlarmData) obj;
        return Intrinsics.areEqual(this.nextAlarmMillis, alarmData.nextAlarmMillis) && Intrinsics.areEqual(this.descriptionId, alarmData.descriptionId);
    }

    public final String getDescriptionId() {
        return this.descriptionId;
    }

    public final Long getNextAlarmMillis() {
        return this.nextAlarmMillis;
    }

    public int hashCode() {
        Long l = this.nextAlarmMillis;
        int hashCode = (l == null ? 0 : l.hashCode()) * 31;
        String str = this.descriptionId;
        return hashCode + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        return "AlarmData(nextAlarmMillis=" + this.nextAlarmMillis + ", descriptionId=" + this.descriptionId + ")";
    }
}
