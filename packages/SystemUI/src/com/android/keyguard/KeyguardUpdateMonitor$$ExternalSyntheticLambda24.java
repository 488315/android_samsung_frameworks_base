package com.android.keyguard;

import android.telephony.SubscriptionInfo;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.SimLogger;
import com.android.keyguard.logging.SimLogger$$ExternalSyntheticLambda0;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.util.Assert;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda24 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardUpdateMonitor f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda24(KeyguardUpdateMonitor keyguardUpdateMonitor, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardUpdateMonitor;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.f$0;
                BatteryStatus batteryStatus = (BatteryStatus) this.f$1;
                if (keyguardUpdateMonitor.mBatteryStatus == null) {
                    keyguardUpdateMonitor.handleBatteryUpdate(batteryStatus);
                    return;
                }
                return;
            case 1:
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = this.f$0;
                WeatherData weatherData = (WeatherData) this.f$1;
                int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor2.getClass();
                Assert.isMainThread();
                while (i < keyguardUpdateMonitor2.mCallbacks.size()) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor2.mCallbacks.get(i)).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onWeatherDataChanged(weatherData);
                    }
                    i++;
                }
                return;
            default:
                KeyguardUpdateMonitor keyguardUpdateMonitor3 = this.f$0;
                List list = (List) this.f$1;
                int i3 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor3.getClass();
                ArrayList arrayList = (ArrayList) list;
                if (arrayList.isEmpty()) {
                    SimLogger simLogger = keyguardUpdateMonitor3.mSimLogger;
                    simLogger.getClass();
                    LogBuffer.log$default(simLogger.logBuffer, "SimLog", LogLevel.VERBOSE, "onSubscriptionInfoChanged: list is null");
                } else {
                    int size = arrayList.size();
                    int i4 = 0;
                    while (i4 < size) {
                        Object obj = arrayList.get(i4);
                        i4++;
                        SimLogger simLogger2 = keyguardUpdateMonitor3.mSimLogger;
                        simLogger2.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        SimLogger$$ExternalSyntheticLambda0 simLogger$$ExternalSyntheticLambda0 = new SimLogger$$ExternalSyntheticLambda0(6);
                        LogBuffer logBuffer = simLogger2.logBuffer;
                        LogMessage obtain = logBuffer.obtain("SimLog", logLevel, simLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) obtain).str1 = String.valueOf((SubscriptionInfo) obj);
                        logBuffer.commit(obtain);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                HashSet hashSet = new HashSet();
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo) arrayList.get(i5);
                    hashSet.add(Integer.valueOf(subscriptionInfo.getSubscriptionId()));
                    if (keyguardUpdateMonitor3.refreshSimState(subscriptionInfo.getSubscriptionId(), subscriptionInfo.getSimSlotIndex())) {
                        arrayList2.add(subscriptionInfo);
                    }
                }
                ArrayList arrayList3 = new ArrayList();
                synchronized (keyguardUpdateMonitor3.mSimDataLockObject) {
                    try {
                        Iterator it = keyguardUpdateMonitor3.mSimDatasBySlotId.entrySet().iterator();
                        while (it.hasNext()) {
                            KeyguardUpdateMonitor.SimData simData = (KeyguardUpdateMonitor.SimData) ((Map.Entry) it.next()).getValue();
                            if (!hashSet.contains(Integer.valueOf(simData.subId))) {
                                SimLogger simLogger3 = keyguardUpdateMonitor3.mSimLogger;
                                int i6 = simData.subId;
                                int i7 = simData.slotId;
                                simLogger3.getClass();
                                LogLevel logLevel2 = LogLevel.INFO;
                                SimLogger$$ExternalSyntheticLambda0 simLogger$$ExternalSyntheticLambda02 = new SimLogger$$ExternalSyntheticLambda0(2);
                                LogBuffer logBuffer2 = simLogger3.logBuffer;
                                LogMessage obtain2 = logBuffer2.obtain("SimLog", logLevel2, simLogger$$ExternalSyntheticLambda02, null);
                                ((LogMessageImpl) obtain2).int1 = i6;
                                ((LogMessageImpl) obtain2).int2 = i7;
                                logBuffer2.commit(obtain2);
                                Log.d("KeyguardUpdateMonitor", "    onSubscriptionInfoChanged(): Previously active sub id " + simData.subId + " is now invalid, will remove");
                                it.remove();
                            }
                        }
                        while (i < arrayList2.size()) {
                            KeyguardUpdateMonitor.SimData simData2 = (KeyguardUpdateMonitor.SimData) keyguardUpdateMonitor3.mSimDatasBySlotId.get(Integer.valueOf(((SubscriptionInfo) arrayList2.get(i)).getSimSlotIndex()));
                            if (simData2 == null) {
                                Log.w("KeyguardUpdateMonitor", "Null SimData for subscription: " + arrayList2.get(i));
                            } else {
                                arrayList3.add(new KeyguardUpdateMonitor.SimData(simData2.simState, simData2.slotId, simData2.subId));
                            }
                            i++;
                        }
                        keyguardUpdateMonitor3.callbacksRefreshCarrierInfo(null);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                keyguardUpdateMonitor3.sendOnSimStateChangedCallback(arrayList3);
                return;
        }
    }
}
