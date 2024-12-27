package com.android.systemui.controls.controller;

import android.util.Log;
import com.android.systemui.controls.util.ControlsUtil;

public final class ControlsProviderLifecycleManager$bindService$1 implements Runnable {
    public final /* synthetic */ boolean $bind;
    public final /* synthetic */ boolean $forPanel;
    public final /* synthetic */ ControlsProviderLifecycleManager this$0;

    public ControlsProviderLifecycleManager$bindService$1(ControlsProviderLifecycleManager controlsProviderLifecycleManager, boolean z, boolean z2) {
        this.this$0 = controlsProviderLifecycleManager;
        this.$bind = z;
        this.$forPanel = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.this$0;
        boolean z2 = this.$bind;
        boolean z3 = this.$forPanel;
        ControlsProviderLifecycleManager$serviceConnection$1 controlsProviderLifecycleManager$serviceConnection$1 = controlsProviderLifecycleManager.serviceConnection;
        controlsProviderLifecycleManager.requiresBound = z2;
        if (!z2) {
            controlsProviderLifecycleManager.unbindAndCleanup("unbind requested");
            PackageUpdateMonitor packageUpdateMonitor = controlsProviderLifecycleManager.packageUpdateMonitor;
            if (packageUpdateMonitor.monitoring.compareAndSet(true, false)) {
                packageUpdateMonitor.unregister();
                return;
            }
            return;
        }
        if (controlsProviderLifecycleManager.wrapper == null) {
            String str = "Binding service " + controlsProviderLifecycleManager.intent;
            String str2 = controlsProviderLifecycleManager.TAG;
            Log.d(str2, str);
            try {
                controlsProviderLifecycleManager.lastForPanel = z3;
                int i = z3 ? ControlsProviderLifecycleManager.BIND_FLAGS_PANEL : ControlsProviderLifecycleManager.BIND_FLAGS;
                if (controlsProviderLifecycleManager$serviceConnection$1.connected.compareAndSet(false, true)) {
                    z = controlsProviderLifecycleManager.context.bindServiceAsUser(controlsProviderLifecycleManager.intent, controlsProviderLifecycleManager$serviceConnection$1, i, controlsProviderLifecycleManager.user);
                } else {
                    if (controlsProviderLifecycleManager$serviceConnection$1.connected.get()) {
                        ControlsUtil controlsUtil = controlsProviderLifecycleManager.controlsUtil;
                        String packageName = controlsProviderLifecycleManager.componentName.getPackageName();
                        controlsUtil.getClass();
                        if ("com.samsung.android.oneconnect".equals(packageName)) {
                            z = controlsProviderLifecycleManager.context.bindServiceAsUser(controlsProviderLifecycleManager.intent, controlsProviderLifecycleManager$serviceConnection$1, i, controlsProviderLifecycleManager.user);
                        }
                    }
                    z = false;
                }
                if (z) {
                    return;
                }
                Log.d(str2, "Couldn't bind to " + controlsProviderLifecycleManager.intent);
                ControlsProviderLifecycleManager$serviceConnection$1 controlsProviderLifecycleManager$serviceConnection$12 = controlsProviderLifecycleManager.serviceConnection;
                if (controlsProviderLifecycleManager$serviceConnection$12.connected.compareAndSet(true, false)) {
                    controlsProviderLifecycleManager.context.unbindService(controlsProviderLifecycleManager$serviceConnection$12);
                }
            } catch (SecurityException e) {
                Log.e(str2, "Failed to bind to service", e);
                controlsProviderLifecycleManager$serviceConnection$1.connected.set(false);
            }
        }
    }
}
