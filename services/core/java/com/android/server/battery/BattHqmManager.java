package com.android.server.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.server.BatteryService;
import com.android.server.power.Slog;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BattHqmManager {
    public static BatteryService.BattCallbackImpl mBattCallback;
    public static AnonymousClass1 mHqmEventReceiver;
    public static final Map mMapForBSHL = new LinkedHashMap();

    /* renamed from: com.android.server.battery.BattHqmManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            BatteryService.BattCallbackImpl battCallbackImpl = BattHqmManager.mBattCallback;
            Slog.d(
                    "[SS]BattHqmManager",
                    "[hqmEventReceiver_onReceive]intent:" + intent.getAction());
            BatteryService.BattCallbackImpl battCallbackImpl2 = BattHqmManager.mBattCallback;
            if (battCallbackImpl2 == null) {
                Slog.e(
                        "[SS]BattHqmManager",
                        "[hqmEventReceiver_onReceive]fail - mBattCallback null");
                return;
            }
            String str = BatteryService.TAG_SS;
            Slog.v("BatteryService", "[onHqmEventOccured]");
            BatteryService.this.mHandler.post(
                    new BatteryService.AnonymousClass31(3, battCallbackImpl2));
        }
    }

    public static void updateMap(String str, String str2) {
        Slog.d("[SS]BattHqmManager", "[updateMap]key:" + str + " ,value:" + str2);
        mMapForBSHL.put(str, str2);
    }
}
