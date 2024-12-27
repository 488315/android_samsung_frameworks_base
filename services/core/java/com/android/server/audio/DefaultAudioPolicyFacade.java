package com.android.server.audio;

import android.media.IAudioPolicyService;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;

import java.util.concurrent.Executor;

public final class DefaultAudioPolicyFacade {
    public final ServiceHolder mServiceHolder;

    public DefaultAudioPolicyFacade(Executor executor) {
        ServiceHolder serviceHolder =
                new ServiceHolder(
                        new DefaultAudioPolicyFacade$$ExternalSyntheticLambda0(), executor);
        this.mServiceHolder = serviceHolder;
        DefaultAudioPolicyFacade$$ExternalSyntheticLambda1
                defaultAudioPolicyFacade$$ExternalSyntheticLambda1 =
                        new DefaultAudioPolicyFacade$$ExternalSyntheticLambda1();
        serviceHolder.mOnStartTasks.add(defaultAudioPolicyFacade$$ExternalSyntheticLambda1);
        IInterface iInterface = (IInterface) serviceHolder.mService.get();
        if (iInterface != null) {
            serviceHolder.mExecutor.execute(
                    new ServiceHolder$$ExternalSyntheticLambda0(
                            defaultAudioPolicyFacade$$ExternalSyntheticLambda1, iInterface, 1));
        }
    }

    public final boolean isHotwordStreamSupported(boolean z) {
        ServiceHolder serviceHolder = this.mServiceHolder;
        IInterface iInterface = (IInterface) serviceHolder.mService.get();
        if (iInterface == null) {
            ServiceHolder.AnonymousClass2 anonymousClass2 = serviceHolder.mServiceProvider;
            String str = serviceHolder.mServiceName;
            anonymousClass2.getClass();
            iInterface = serviceHolder.onServiceInited(ServiceManager.waitForService(str));
        }
        IAudioPolicyService iAudioPolicyService = (IAudioPolicyService) iInterface;
        try {
            return iAudioPolicyService.isHotwordStreamSupported(z);
        } catch (RemoteException unused) {
            serviceHolder.attemptClear(iAudioPolicyService.asBinder());
            throw new IllegalStateException();
        }
    }
}
