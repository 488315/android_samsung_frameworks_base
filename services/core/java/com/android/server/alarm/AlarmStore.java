package com.android.server.alarm;

public interface AlarmStore {

    public interface AlarmDeliveryCalculator {
        boolean updateAlarmDelivery(Alarm alarm);
    }
}
