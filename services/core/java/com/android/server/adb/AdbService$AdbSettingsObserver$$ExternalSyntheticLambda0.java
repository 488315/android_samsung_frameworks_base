package com.android.server.adb;

import android.os.SystemProperties;
import com.android.internal.util.function.TriConsumer;
import com.android.server.adb.AdbService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0 implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        AdbService adbService = (AdbService) obj;
        Boolean bool = (Boolean) obj2;
        switch (this.$r8$classId) {
            case 0:
                AdbService.AdbSettingsObserver.m148$r8$lambda$QsA9Am4S856tzWrx_VQgRFMzI(adbService, bool.booleanValue(), ((Byte) obj3).byteValue());
                break;
            default:
                boolean booleanValue = bool.booleanValue();
                byte byteValue = ((Byte) obj3).byteValue();
                if (byteValue == 0) {
                    adbService.mIsAdbUsbEnabled = booleanValue;
                } else if (byteValue == 1) {
                    adbService.mIsAdbWifiEnabled = booleanValue;
                } else {
                    int i = AdbService.$r8$clinit;
                }
                if (!booleanValue) {
                    if (!adbService.mIsAdbUsbEnabled && !adbService.mIsAdbWifiEnabled) {
                        SystemProperties.set("ctl.stop", "adbd");
                        break;
                    }
                } else {
                    adbService.getClass();
                    SystemProperties.set("ctl.start", "adbd");
                    break;
                }
                break;
        }
    }
}
