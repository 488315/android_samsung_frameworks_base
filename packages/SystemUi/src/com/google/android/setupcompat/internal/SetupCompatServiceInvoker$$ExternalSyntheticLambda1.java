package com.google.android.setupcompat.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.util.Logger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SetupCompatServiceInvoker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SetupCompatServiceInvoker f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ SetupCompatServiceInvoker$$ExternalSyntheticLambda1(SetupCompatServiceInvoker setupCompatServiceInvoker, String str, Bundle bundle, int i) {
        this.$r8$classId = i;
        this.f$0 = setupCompatServiceInvoker;
        this.f$1 = str;
        this.f$2 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SetupCompatServiceInvoker setupCompatServiceInvoker = this.f$0;
                String str = this.f$1;
                Bundle bundle = this.f$2;
                setupCompatServiceInvoker.getClass();
                Logger logger = SetupCompatServiceInvoker.LOG;
                try {
                    Context context = setupCompatServiceInvoker.context;
                    ISetupCompatService service = SetupCompatServiceProvider.getInstance(context).getService(setupCompatServiceInvoker.waitTimeInMillisForServiceConnection, TimeUnit.MILLISECONDS);
                    if (service != null) {
                        ((ISetupCompatService.Stub.Proxy) service).validateActivity(bundle, str);
                    } else {
                        logger.m239w("BindBack failed since service reference is null. Are the permissions valid?");
                    }
                    break;
                } catch (RemoteException | InterruptedException | TimeoutException e) {
                    logger.m238e(String.format("Exception occurred while %s trying bind back to SetupWizard.", str), e);
                    return;
                }
            default:
                SetupCompatServiceInvoker setupCompatServiceInvoker2 = this.f$0;
                String str2 = this.f$1;
                Bundle bundle2 = this.f$2;
                setupCompatServiceInvoker2.getClass();
                Logger logger2 = SetupCompatServiceInvoker.LOG;
                try {
                    Context context2 = setupCompatServiceInvoker2.context;
                    ISetupCompatService service2 = SetupCompatServiceProvider.getInstance(context2).getService(setupCompatServiceInvoker2.waitTimeInMillisForServiceConnection, TimeUnit.MILLISECONDS);
                    if (service2 != null) {
                        ((ISetupCompatService.Stub.Proxy) service2).onFocusStatusChanged(bundle2);
                    } else {
                        logger2.m239w("Report focusChange failed since service reference is null. Are the permission valid?");
                    }
                    break;
                } catch (RemoteException | InterruptedException | UnsupportedOperationException | TimeoutException e2) {
                    logger2.m238e(String.format("Exception occurred while %s trying report windowFocusChange to SetupWizard.", str2), e2);
                    return;
                }
        }
    }
}
