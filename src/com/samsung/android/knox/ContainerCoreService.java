package com.samsung.android.knox;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.samsung.android.knox.IContainerService;

/* loaded from: classes6.dex */
public abstract class ContainerCoreService extends Service {
    public abstract Bundle onCommandReceived(String str, Bundle bundle);

    public abstract Bundle onEvent(String str, Bundle bundle);

    public abstract Bundle onPolicyUpdated(String str, Bundle bundle);

    public abstract Bundle onTestMessage(String str, Bundle bundle);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new ServiceImpl();
    }

    private final class ServiceImpl extends IContainerService.Stub {
        private ServiceImpl() {
        }

        @Override // com.samsung.android.knox.IContainerService
        public Bundle onMessage(String str, Bundle bundle) {
            if (str.startsWith("knox.container.proxy.COMMAND_")) {
                return ContainerCoreService.this.onCommandReceived(str, bundle);
            }
            if (str.startsWith("knox.container.proxy.POLICY_")) {
                return ContainerCoreService.this.onPolicyUpdated(str, bundle);
            }
            if (str.startsWith("knox.container.proxy.EVENT_")) {
                return ContainerCoreService.this.onEvent(str, bundle);
            }
            if (str.startsWith(ContainerProxy.TEST_BASE)) {
                return ContainerCoreService.this.onTestMessage(str, bundle);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putInt("android.intent.extra.RETURN_RESULT", 2);
            return bundle2;
        }
    }
}
