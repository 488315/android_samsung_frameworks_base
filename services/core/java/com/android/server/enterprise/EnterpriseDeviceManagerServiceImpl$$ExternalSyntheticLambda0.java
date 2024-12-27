package com.android.server.enterprise;

import android.content.Intent;

import com.android.server.enterprise.dualdar.DualDARPolicy;
import com.android.server.enterprise.impl.KeyCodeMediatorImpl;
import com.android.server.knox.dar.ddar.proxy.DualDARComnService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final /* synthetic */ class EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EnterpriseDeviceManagerServiceImpl f$0;

    public /* synthetic */ EnterpriseDeviceManagerServiceImpl$$ExternalSyntheticLambda0(
            EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = enterpriseDeviceManagerServiceImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        EnterpriseDeviceManagerServiceImpl enterpriseDeviceManagerServiceImpl = this.f$0;
        switch (i) {
            case 0:
                List list = EnterpriseDeviceManagerServiceImpl.EXCLUDED_ADMINS;
                enterpriseDeviceManagerServiceImpl.getClass();
                DualDARComnService dualDARComnService =
                        new DualDARComnService(enterpriseDeviceManagerServiceImpl.mContext);
                enterpriseDeviceManagerServiceImpl.mInjector.getClass();
                EnterpriseDeviceManagerServiceImpl.Injector.addSystemService(
                        dualDARComnService, "knox_adapter_service");
                enterpriseDeviceManagerServiceImpl.mInjector.getClass();
                EnterpriseService.invokeSystemReadyIfNeeded(
                        dualDARComnService, "knox_adapter_service");
                DualDARPolicy dualDARPolicy =
                        new DualDARPolicy(enterpriseDeviceManagerServiceImpl.mContext);
                enterpriseDeviceManagerServiceImpl.mInjector.getClass();
                EnterpriseDeviceManagerServiceImpl.Injector.addSystemService(
                        dualDARPolicy, "DualDARPolicy");
                enterpriseDeviceManagerServiceImpl.mInjector.getClass();
                EnterpriseService.invokeSystemReadyIfNeeded(dualDARPolicy, "DualDARPolicy");
                EnterpriseDeviceManagerServiceImpl.mServiceAdditionCondition.open();
                break;
            case 1:
                List list2 = EnterpriseDeviceManagerServiceImpl.EXCLUDED_ADMINS;
                enterpriseDeviceManagerServiceImpl.createDeferredServices();
                enterpriseDeviceManagerServiceImpl.mContext.sendBroadcast(
                        new Intent(
                                "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL"));
                enterpriseDeviceManagerServiceImpl.mContext.sendBroadcast(
                        new Intent("edm.intent.action.ACTION_EDM_BOOT_COMPLETED"));
                EnterpriseDeviceManagerServiceImpl.mServiceAdditionCondition.open();
                break;
            default:
                KeyCodeMediatorImpl keyCodeMediatorImpl =
                        enterpriseDeviceManagerServiceImpl.mKeyCodeMediator;
                Iterator it = ((HashSet) keyCodeMediatorImpl.getAllRestrictedKeyCodes()).iterator();
                while (it.hasNext()) {
                    keyCodeMediatorImpl.update(((Integer) it.next()).intValue());
                }
                break;
        }
    }
}
