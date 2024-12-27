package android.hardware.health;

public abstract class Translate {
    public static void h2aTranslateInternal(
            HealthInfo healthInfo, android.hardware.health.V1_0.HealthInfo healthInfo2) {
        healthInfo.chargerAcOnline = healthInfo2.chargerAcOnline;
        healthInfo.chargerUsbOnline = healthInfo2.chargerUsbOnline;
        healthInfo.chargerWirelessOnline = healthInfo2.chargerWirelessOnline;
        healthInfo.maxChargingCurrentMicroamps = healthInfo2.maxChargingCurrent;
        healthInfo.maxChargingVoltageMicrovolts = healthInfo2.maxChargingVoltage;
        healthInfo.batteryStatus = healthInfo2.batteryStatus;
        healthInfo.batteryHealth = healthInfo2.batteryHealth;
        healthInfo.batteryPresent = healthInfo2.batteryPresent;
        healthInfo.batteryLevel = healthInfo2.batteryLevel;
        healthInfo.batteryVoltageMillivolts = healthInfo2.batteryVoltage;
        healthInfo.batteryTemperatureTenthsCelsius = healthInfo2.batteryTemperature;
        healthInfo.batteryCurrentMicroamps = healthInfo2.batteryCurrent;
        healthInfo.batteryCycleCount = healthInfo2.batteryCycleCount;
        healthInfo.batteryFullChargeUah = healthInfo2.batteryFullCharge;
        healthInfo.batteryChargeCounterUah = healthInfo2.batteryChargeCounter;
        healthInfo.batteryTechnology = healthInfo2.batteryTechnology;
    }
}
