package com.android.server.health;

import android.hardware.health.HealthInfo;

import vendor.samsung.hardware.health.SehHealthInfo;

public abstract class Utils {
    public static void copySehV1Battery(SehHealthInfo sehHealthInfo, SehHealthInfo sehHealthInfo2) {
        if (sehHealthInfo.aospHealthInfo == null) {
            sehHealthInfo.aospHealthInfo = new HealthInfo();
        }
        HealthInfo healthInfo = sehHealthInfo.aospHealthInfo;
        HealthInfo healthInfo2 = sehHealthInfo2.aospHealthInfo;
        healthInfo.chargerAcOnline = healthInfo2.chargerAcOnline;
        healthInfo.chargerUsbOnline = healthInfo2.chargerUsbOnline;
        healthInfo.chargerWirelessOnline = healthInfo2.chargerWirelessOnline;
        healthInfo.maxChargingCurrentMicroamps = healthInfo2.maxChargingCurrentMicroamps;
        healthInfo.maxChargingVoltageMicrovolts = healthInfo2.maxChargingVoltageMicrovolts;
        healthInfo.batteryStatus = healthInfo2.batteryStatus;
        healthInfo.batteryHealth = healthInfo2.batteryHealth;
        healthInfo.batteryPresent = healthInfo2.batteryPresent;
        healthInfo.batteryLevel = healthInfo2.batteryLevel;
        healthInfo.batteryVoltageMillivolts = healthInfo2.batteryVoltageMillivolts;
        healthInfo.batteryTemperatureTenthsCelsius = healthInfo2.batteryTemperatureTenthsCelsius;
        healthInfo.batteryCurrentMicroamps = healthInfo2.batteryCurrentMicroamps;
        healthInfo.batteryCycleCount = healthInfo2.batteryCycleCount;
        healthInfo.batteryFullChargeUah = healthInfo2.batteryFullChargeUah;
        healthInfo.batteryChargeCounterUah = healthInfo2.batteryChargeCounterUah;
        healthInfo.batteryTechnology = healthInfo2.batteryTechnology;
        healthInfo.batteryCurrentAverageMicroamps = healthInfo2.batteryCurrentAverageMicroamps;
        healthInfo.batteryCapacityLevel = healthInfo2.batteryCapacityLevel;
        healthInfo.batteryChargeTimeToFullNowSeconds =
                healthInfo2.batteryChargeTimeToFullNowSeconds;
        healthInfo.batteryFullChargeDesignCapacityUah =
                healthInfo2.batteryFullChargeDesignCapacityUah;
        sehHealthInfo.batteryCurrentNow = sehHealthInfo2.batteryCurrentNow;
        sehHealthInfo.batteryOnline = sehHealthInfo2.batteryOnline;
        sehHealthInfo.batteryChargeType = sehHealthInfo2.batteryChargeType;
        sehHealthInfo.batteryPowerSharingOnline = sehHealthInfo2.batteryPowerSharingOnline;
        sehHealthInfo.chargerPogoOnline = sehHealthInfo2.chargerPogoOnline;
        sehHealthInfo.batteryHighVoltageCharger = sehHealthInfo2.batteryHighVoltageCharger;
        sehHealthInfo.batteryEvent = sehHealthInfo2.batteryEvent;
        sehHealthInfo.batteryCurrentEvent = sehHealthInfo2.batteryCurrentEvent;
        sehHealthInfo.chargerOtgOnline = sehHealthInfo2.chargerOtgOnline;
        sehHealthInfo.wirelessPowerSharingTxEvent = sehHealthInfo2.wirelessPowerSharingTxEvent;
    }
}
