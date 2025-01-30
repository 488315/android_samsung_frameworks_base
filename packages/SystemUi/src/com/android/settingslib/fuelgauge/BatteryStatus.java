package com.android.settingslib.fuelgauge;

import android.content.Intent;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.sec.ims.IMSParameter;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public boolean isPluggedIn() {
        int i = this.plugged;
        return i == 1 || i == 2 || i == 4 || i == 8;
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
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.maxChargingWattage, "}");
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
        int intExtra4 = intent.getIntExtra("max_charging_voltage", -1);
        intExtra4 = intExtra4 <= 0 ? 5000000 : intExtra4;
        if (intExtra3 > 0) {
            this.maxChargingWattage = (intExtra4 / 1000) * (intExtra3 / 1000);
        } else {
            this.maxChargingWattage = -1;
        }
    }
}
