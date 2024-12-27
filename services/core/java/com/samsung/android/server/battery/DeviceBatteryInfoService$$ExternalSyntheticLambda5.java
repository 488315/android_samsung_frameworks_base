package com.samsung.android.server.battery;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceBatteryInfoService$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceBatteryInfoService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DeviceBatteryInfoService$$ExternalSyntheticLambda5(DeviceBatteryInfoService deviceBatteryInfoService, String str, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceBatteryInfoService;
        this.f$1 = str;
        this.f$2 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0066 A[Catch: Exception -> 0x004f, TRY_LEAVE, TryCatch #0 {Exception -> 0x004f, blocks: (B:5:0x0014, B:7:0x003a, B:10:0x0042, B:12:0x0049, B:13:0x005b, B:15:0x0066, B:20:0x0051), top: B:4:0x0014 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r6 = this;
            int r0 = r6.$r8$classId
            switch(r0) {
                case 0: goto L7f;
                default: goto L5;
            }
        L5:
            com.samsung.android.server.battery.DeviceBatteryInfoService r0 = r6.f$0
            java.lang.String r1 = r6.f$1
            java.lang.Object r6 = r6.f$2
            java.lang.String r6 = (java.lang.String) r6
            r0.getClass()
            java.lang.String r2 = "DeviceBatteryInfoService"
            java.lang.String r3 = "address : "
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L4f
            r4.<init>(r3)     // Catch: java.lang.Exception -> L4f
            java.lang.String r3 = com.samsung.android.server.battery.DeviceBatteryInfoUtil.getAddressForLog(r1)     // Catch: java.lang.Exception -> L4f
            r4.append(r3)     // Catch: java.lang.Exception -> L4f
            java.lang.String r3 = " packageName : "
            r4.append(r3)     // Catch: java.lang.Exception -> L4f
            r4.append(r6)     // Catch: java.lang.Exception -> L4f
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Exception -> L4f
            android.util.Slog.i(r2, r3)     // Catch: java.lang.Exception -> L4f
            com.samsung.android.os.SemCompanionDeviceBatteryInfo r3 = r0.getDeviceBatteryInfo(r1)     // Catch: java.lang.Exception -> L4f
            int r4 = r3.getDeviceType()     // Catch: java.lang.Exception -> L4f
            r5 = 4
            if (r4 == r5) goto L51
            int r4 = r3.getDeviceType()     // Catch: java.lang.Exception -> L4f
            r5 = 6
            if (r4 != r5) goto L42
            goto L51
        L42:
            int r3 = r3.getDeviceType()     // Catch: java.lang.Exception -> L4f
            r4 = 7
            if (r3 != r4) goto L5b
            java.util.HashMap r3 = r0.packageAddressMap     // Catch: java.lang.Exception -> L4f
            r3.remove(r6)     // Catch: java.lang.Exception -> L4f
            goto L5b
        L4f:
            r6 = move-exception
            goto L79
        L51:
            com.samsung.android.server.battery.WatchBatteryManager r3 = r0.mWatchBatteryManager     // Catch: java.lang.Exception -> L4f
            r3.removeWatchPackageInfo(r6)     // Catch: java.lang.Exception -> L4f
            java.util.HashMap r3 = r0.packageAddressMap     // Catch: java.lang.Exception -> L4f
            r3.remove(r6)     // Catch: java.lang.Exception -> L4f
        L5b:
            r0.removeBatteryInfo(r1)     // Catch: java.lang.Exception -> L4f
            java.util.HashMap r1 = r0.packageReceiverMap     // Catch: java.lang.Exception -> L4f
            boolean r1 = r1.containsKey(r6)     // Catch: java.lang.Exception -> L4f
            if (r1 == 0) goto L7e
            java.util.HashMap r1 = r0.packageReceiverMap     // Catch: java.lang.Exception -> L4f
            java.lang.Object r1 = r1.get(r6)     // Catch: java.lang.Exception -> L4f
            android.content.BroadcastReceiver r1 = (android.content.BroadcastReceiver) r1     // Catch: java.lang.Exception -> L4f
            android.content.Context r3 = r0.mContext     // Catch: java.lang.Exception -> L4f
            r3.unregisterReceiver(r1)     // Catch: java.lang.Exception -> L4f
            java.util.HashMap r0 = r0.packageReceiverMap     // Catch: java.lang.Exception -> L4f
            r0.remove(r6)     // Catch: java.lang.Exception -> L4f
            goto L7e
        L79:
            java.lang.String r0 = "Exception occurred : "
            com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r6, r0, r2)
        L7e:
            return
        L7f:
            com.samsung.android.server.battery.DeviceBatteryInfoService r0 = r6.f$0
            java.lang.String r1 = r6.f$1
            java.lang.Object r6 = r6.f$2
            com.samsung.android.os.SemCompanionDeviceBatteryInfo r6 = (com.samsung.android.os.SemCompanionDeviceBatteryInfo) r6
            r0.getClass()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "address : "
            r2.<init>(r3)
            java.lang.String r3 = com.samsung.android.server.battery.DeviceBatteryInfoUtil.getAddressForLog(r1)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "DeviceBatteryInfoService"
            android.util.Slog.i(r3, r2)
            com.samsung.android.os.SemCompanionDeviceBatteryInfo r1 = r0.getDeviceBatteryInfo(r1)
            if (r1 == 0) goto Lec
            java.lang.String r2 = r6.getDeviceName()
            r1.setDeviceName(r2)
            int r2 = r6.getBatteryLevel()
            r1.setBatteryLevel(r2)
            int r2 = r6.getBatteryStatus()
            r1.setBatteryStatus(r2)
            int r2 = r6.getExtraBatteryLevelLeft()
            r1.setExtraBatteryLevelLeft(r2)
            int r2 = r6.getExtraBatteryLevelRight()
            r1.setExtraBatteryLevelRight(r2)
            int r2 = r6.getExtraBatteryLevelCradle()
            r1.setExtraBatteryLevelCradle(r2)
            int r2 = r6.getExtraBatteryStatusLeft()
            r1.setExtraBatteryStatusLeft(r2)
            int r2 = r6.getExtraBatteryStatusRight()
            r1.setExtraBatteryStatusRight(r2)
            int r6 = r6.getExtraBatteryStatusCradle()
            r1.setExtraBatteryStatusCradle(r6)
            java.lang.String r6 = "com.samsung.battery.ACTION_BATTERY_INFO_CHANGED"
            r0.sendBroadcast(r6, r1)
            goto Lf1
        Lec:
            java.lang.String r6 = "batteryInfo is null"
            android.util.Slog.i(r3, r6)
        Lf1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda5.run():void");
    }
}
