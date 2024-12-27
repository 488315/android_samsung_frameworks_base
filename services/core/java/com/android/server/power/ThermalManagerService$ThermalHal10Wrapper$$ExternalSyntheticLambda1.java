package com.android.server.power;

import android.hardware.thermal.V1_0.IThermal;
import android.hardware.thermal.V1_0.Temperature;
import android.hardware.thermal.V1_0.ThermalStatus;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda1
        implements IThermal.getTemperaturesCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ ThermalManagerService$ThermalHal10Wrapper$$ExternalSyntheticLambda1(
            List list, int i, boolean z, int i2) {
        this.$r8$classId = i2;
        this.f$0 = z;
        this.f$1 = i;
        this.f$2 = list;
    }

    public final void onValues(ThermalStatus thermalStatus, ArrayList arrayList) {
        switch (this.$r8$classId) {
            case 0:
                boolean z = this.f$0;
                int i = this.f$1;
                List list = this.f$2;
                if (thermalStatus.code != 0) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            new StringBuilder("Couldn't get temperatures because of HAL error: "),
                            thermalStatus.debugMessage,
                            "ThermalManagerService$ThermalHalWrapper");
                    break;
                } else {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Temperature temperature = (Temperature) it.next();
                        if (!z || i == temperature.type) {
                            list.add(
                                    new android.os.Temperature(
                                            temperature.currentValue,
                                            temperature.type,
                                            temperature.name,
                                            0));
                        }
                    }
                    break;
                }
            default:
                boolean z2 = this.f$0;
                int i2 = this.f$1;
                List list2 = this.f$2;
                if (thermalStatus.code != 0) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            new StringBuilder("Couldn't get temperatures because of HAL error: "),
                            thermalStatus.debugMessage,
                            "ThermalManagerService$ThermalHalWrapper");
                    break;
                } else {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        Temperature temperature2 = (Temperature) it2.next();
                        if (!z2 || i2 == temperature2.type) {
                            list2.add(
                                    new android.os.Temperature(
                                            temperature2.currentValue,
                                            temperature2.type,
                                            temperature2.name,
                                            0));
                        }
                    }
                    break;
                }
        }
    }
}
