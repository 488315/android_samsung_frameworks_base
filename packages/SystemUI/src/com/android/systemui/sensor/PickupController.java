package com.android.systemui.sensor;

import android.hardware.SensorManager;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.sensor.SensorController;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class PickupController extends SensorController implements Dumpable {
    public final PickupController$baseSensorListener$1 baseSensorListener;
    public final Handler handler;
    public final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback;
    public int phoneState;
    public final ArrayList pickupListener;
    public final PowerManager powerManager;
    public final PickupController$registerRunnable$1 registerRunnable;
    private final SettingsHelper settingsHelper;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public PickupController(final KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, SettingsHelper settingsHelper, PowerManager powerManager, Handler handler, SensorManager sensorManager) {
        super(sensorManager);
        this.settingsHelper = settingsHelper;
        this.powerManager = powerManager;
        this.handler = handler;
        this.pickupListener = new ArrayList();
        this.registerRunnable = new Runnable() { // from class: com.android.systemui.sensor.PickupController$registerRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                PickupController.access$registerSensor(PickupController.this);
            }
        };
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.sensor.PickupController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                PickupController.this.phoneState = i;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedGoingToSleep(int i) {
                Log.d("PickupController", "onStartedGoingToSleep() ");
                PickupController pickupController = PickupController.this;
                if (PickupController.access$isLiftToWakeEnabled(pickupController)) {
                    if (i == 4) {
                        pickupController.handler.removeCallbacks(pickupController.registerRunnable);
                        pickupController.handler.postDelayed(pickupController.registerRunnable, 5000L);
                    } else {
                        PickupController.access$registerSensor(pickupController);
                    }
                    PickupController.access$registerSensor(pickupController);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
                Log.d("PickupController", "onStartedWakingUp() ");
                PickupController pickupController = PickupController.this;
                if (PickupController.access$isLiftToWakeEnabled(pickupController)) {
                    if (pickupController.handler.hasCallbacks(pickupController.registerRunnable)) {
                        pickupController.handler.removeCallbacks(pickupController.registerRunnable);
                    } else {
                        pickupController.unregister$1();
                    }
                }
            }
        };
        this.keyguardUpdateMonitorCallback = keyguardUpdateMonitorCallback;
        statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.sensor.PickupController$statusBarStateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                PickupController pickupController = PickupController.this;
                if (PickupController.access$isLiftToWakeEnabled(pickupController) && keyguardUpdateMonitor.mDeviceInteractive) {
                    if (z) {
                        PickupController.access$registerSensor(pickupController);
                    } else {
                        pickupController.unregister$1();
                    }
                }
            }
        });
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        this.baseSensorListener = new SensorController.SensorListener() { // from class: com.android.systemui.sensor.PickupController$baseSensorListener$1
            @Override // com.android.systemui.sensor.SensorController.SensorListener
            public final void onExecute() {
                Log.d("PickupController", "onExecute : Lift to wake");
                PickupController pickupController = PickupController.this;
                pickupController.powerManager.setEarlyWakeUp(true);
                pickupController.powerManager.wakeUp(SystemClock.uptimeMillis(), 7, "LiftToWake");
            }
        };
    }

    public static final boolean access$isLiftToWakeEnabled(PickupController pickupController) {
        pickupController.getClass();
        if (FactoryTest.isFactoryBinary()) {
            return false;
        }
        return pickupController.settingsHelper.isLiftToWakeEnabled();
    }

    public static final void access$registerSensor(PickupController pickupController) {
        SensorController.SensorInfo sensorInfo = (SensorController.SensorInfo) pickupController.sensorInfos.get(1);
        if (sensorInfo != null ? sensorInfo.bRegistered : false) {
            pickupController.unregister$1();
        }
        SensorController.SensorInfo sensorInfo2 = (SensorController.SensorInfo) pickupController.sensorInfos.get(1);
        if ((sensorInfo2 != null ? sensorInfo2.sensor : null) == null) {
            Log.w("SensorController", "register - not supported sensor type=1");
            return;
        }
        if (sensorInfo2.bRegistered) {
            Log.w("SensorController", "register - already registered sensor type=1");
        } else if (!pickupController.sensorManager.registerListener(pickupController, sensorInfo2.sensor, 3)) {
            Log.i("SensorController", "register - requestTriggerSensor return false");
        } else {
            Log.i("SensorController", "register");
            sensorInfo2.bRegistered = true;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("   PickupController Dump");
        printWriter.print("      addedMonitorCallback=");
        printWriter.println(false);
        if (this.pickupListener.size() > 0) {
            this.pickupListener.forEach(new Consumer() { // from class: com.android.systemui.sensor.PickupController$dump$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    printWriter.println("      lisenter=" + ((SensorController.SensorListener) obj));
                }
            });
        }
    }

    @Override // com.android.systemui.sensor.SensorController
    public final void onTrigger() {
        if (this.phoneState == 2) {
            Log.d("PickupController", "onTrigger return cause by CALL_STATE_OFFHOOK");
            return;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(this.pickupListener.size(), "onTrigger Listener.size()=", "PickupController");
        int size = this.pickupListener.size() - 1;
        if (size >= 0) {
            ((SensorController.SensorListener) this.pickupListener.get(size)).getClass();
            ((SensorController.SensorListener) this.pickupListener.get(size)).onExecute();
        }
    }
}
