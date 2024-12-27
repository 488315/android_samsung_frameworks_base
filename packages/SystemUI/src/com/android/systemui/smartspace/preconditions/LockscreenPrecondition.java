package com.android.systemui.smartspace.preconditions;

import android.app.smartspace.SmartspaceSession;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController;
import com.android.systemui.communal.smartspace.CommunalSmartspaceController$preconditionListener$1;
import com.android.systemui.smartspace.SmartspacePrecondition;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.concurrency.Execution;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
                    for (CommunalSmartspaceController$preconditionListener$1 communalSmartspaceController$preconditionListener$1 : this.listeners) {
                        communalSmartspaceController$preconditionListener$1.getClass();
                        int i = CommunalSmartspaceController.$r8$clinit;
                        SmartspaceSession smartspaceSession = communalSmartspaceController$preconditionListener$1.this$0.session;
                        if (smartspaceSession != null) {
                            smartspaceSession.requestSmartspaceUpdate();
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
