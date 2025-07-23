package com.android.systemui.smartspace.preconditions;

import android.app.smartspace.SmartspaceSession;
import android.util.Log;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController$preconditionListener$1;
import com.android.systemui.smartspace.SmartspacePrecondition;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.concurrency.Execution;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LockscreenPrecondition implements SmartspacePrecondition {
    public final DeviceProvisionedController deviceProvisionedController;
    public final LockscreenPrecondition$deviceProvisionedListener$1 deviceProvisionedListener;
    public boolean deviceReady;
    public final Execution execution;
    public final Set listeners = new LinkedHashSet();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.smartspace.preconditions.LockscreenPrecondition$deviceProvisionedListener$1, java.lang.Object] */
    public LockscreenPrecondition(DeviceProvisionedController deviceProvisionedController, Execution execution) {
        this.deviceProvisionedController = deviceProvisionedController;
        this.execution = execution;
        ?? r2 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.smartspace.preconditions.LockscreenPrecondition$deviceProvisionedListener$1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onDeviceProvisionedChanged() {
                LockscreenPrecondition.this.updateDeviceReadiness();
            }

            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                LockscreenPrecondition.this.updateDeviceReadiness();
            }
        };
        this.deviceProvisionedListener = r2;
        ((DeviceProvisionedControllerImpl) deviceProvisionedController).addCallback(r2);
        updateDeviceReadiness();
    }

    public final void updateDeviceReadiness() {
        if (this.deviceReady) {
            return;
        }
        boolean z = ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).deviceProvisioned.get() && ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).isCurrentUserSetup();
        this.deviceReady = z;
        if (z) {
            ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).removeCallback(this.deviceProvisionedListener);
            synchronized (this.listeners) {
                try {
                    Iterator it = this.listeners.iterator();
                    while (it.hasNext()) {
                        CommunalSmartspaceController communalSmartspaceController = ((CommunalSmartspaceController$preconditionListener$1) it.next()).this$0;
                        if (communalSmartspaceController.session != null || communalSmartspaceController.listeners.isEmpty()) {
                            SmartspaceSession smartspaceSession = communalSmartspaceController.session;
                            if (smartspaceSession != null) {
                                smartspaceSession.requestSmartspaceUpdate();
                            }
                        } else {
                            Log.d("CommunalSmartspaceCtrlr", "Precondition criteria changed. Attempting to connect session.");
                            communalSmartspaceController.connectSession();
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
