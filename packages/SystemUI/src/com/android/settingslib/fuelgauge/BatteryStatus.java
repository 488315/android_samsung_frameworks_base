package com.android.settingslib.fuelgauge;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemProperties;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.sec.ims.IMSParameter;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BatteryStatus {
    public final int chargingStatus;
    public final Optional incompatibleCharger;
    public final int level;
    public final int maxChargingWattage;
    public final int plugged;
    public final boolean present;
    public final int status;

    public BatteryStatus(int i, int i2, int i3, int i4, int i5, boolean z) {
        this.status = i;
        this.level = i2;
        this.plugged = i3;
        this.chargingStatus = i4;
        this.maxChargingWattage = i5;
        this.present = z;
        this.incompatibleCharger = Optional.empty();
    }

    public final int getChargingSpeed(Context context) {
        int integer = context.getResources().getInteger(R.integer.config_chargingSlowlyThreshold);
        Resources resources = context.getResources();
        if (BatteryUtils.sChargingStringV2Enabled == null) {
            BatteryUtils.sChargingStringV2Enabled = Boolean.valueOf(SystemProperties.getBoolean("charging_string.apply_v2", false));
        }
        int integer2 = resources.getInteger(BatteryUtils.sChargingStringV2Enabled.booleanValue() ? R.integer.config_chargingFastThreshold_v2 : R.integer.config_chargingFastThreshold);
        int i = this.maxChargingWattage;
        if (i <= 0) {
            return -1;
        }
        if (i < integer) {
            return 0;
        }
        return i > integer2 ? 2 : 1;
    }

    public boolean isCharged() {
        return this.status == 5 || this.level >= 100;
    }

    public boolean isPluggedIn() {
        int i = this.plugged;
        return i == 1 || i == 2 || i == 4 || i == 8;
    }

    public boolean isPluggedInWired() {
        int i = this.plugged;
        return i == 1 || i == 2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("BatteryStatus{status=");
        sb.append(this.status);
        sb.append(",level=");
        sb.append(this.level);
        sb.append(",plugged=");
        sb.append(this.plugged);
        sb.append(",chargingStatus=");
        sb.append(this.chargingStatus);
        sb.append(",maxChargingWattage=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.maxChargingWattage, "}", sb);
    }

    public BatteryStatus(Intent intent) {
        this(intent, (Optional<Boolean>) Optional.empty());
    }

    public BatteryStatus(Intent intent, boolean z) {
        this(intent, (Optional<Boolean>) Optional.of(Boolean.valueOf(z)));
    }

    private BatteryStatus(Intent intent, Optional<Boolean> optional) {
        this.status = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
        this.plugged = intent.getIntExtra("plugged", 0);
        int intExtra = intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, -1);
        int intExtra2 = intent.getIntExtra("scale", 0);
        this.level = intExtra2 == 0 ? -1 : Math.round((intExtra / intExtra2) * 100.0f);
        this.chargingStatus = intent.getIntExtra("android.os.extra.CHARGING_STATUS", 1);
        this.present = intent.getBooleanExtra("present", true);
        this.incompatibleCharger = optional;
        int intExtra3 = intent.getIntExtra("max_charging_current", -1);
        this.maxChargingWattage = intExtra3 > 0 ? (int) Math.round(intExtra3 * 0.001d * (intent.getIntExtra("max_charging_voltage", -1) <= 0 ? 5000000 : r6) * 0.001d) : -1;
    }
}
