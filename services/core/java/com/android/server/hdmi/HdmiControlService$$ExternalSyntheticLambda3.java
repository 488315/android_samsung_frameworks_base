package com.android.server.hdmi;

import android.sysprop.HdmiProperties;
import android.util.Slog;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class HdmiControlService$$ExternalSyntheticLambda3
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        HdmiProperties.cec_device_types_values cec_device_types_valuesVar =
                (HdmiProperties.cec_device_types_values) obj;
        if (cec_device_types_valuesVar == null) {
            return null;
        }
        switch (HdmiControlService.AnonymousClass37
                .$SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                cec_device_types_valuesVar.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            case 8:
                return 7;
            default:
                Slog.w(
                        "HdmiControlService",
                        "Unrecognized device type in ro.hdmi.cec_device_types: "
                                + cec_device_types_valuesVar.getPropValue());
                return null;
        }
    }
}
