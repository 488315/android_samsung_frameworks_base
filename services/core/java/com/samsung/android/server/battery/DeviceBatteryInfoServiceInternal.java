package com.samsung.android.server.battery;

import android.content.Context;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public interface DeviceBatteryInfoServiceInternal {
    void addBatteryInfo(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo);

    boolean containsBatteryInfo(String str);

    void dump(PrintWriter printWriter);

    SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str);

    SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos();

    void registerDeviceBatteryInfoChanged(String str);

    void removeBatteryInfo(String str);

    void sendBroadcast(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo);

    void setDeviceBatteryInfo(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo);

    void systemServicesReady(Context context);

    void unRegisterDeviceBatteryInfoChanged(String str);

    void unsetDeviceBatteryInfo(String str);
}
