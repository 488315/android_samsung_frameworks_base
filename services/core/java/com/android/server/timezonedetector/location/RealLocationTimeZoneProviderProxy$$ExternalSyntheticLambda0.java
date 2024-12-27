package com.android.server.timezonedetector.location;

import android.os.IBinder;
import android.service.timezone.ITimeZoneProvider;

import com.android.server.servicewatcher.ServiceWatcher$BinderOperation;

public final /* synthetic */ class RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0
        implements ServiceWatcher$BinderOperation {
    public final /* synthetic */ TimeZoneProviderRequest f$0;
    public final /* synthetic */ RealLocationTimeZoneProviderProxy.ManagerProxy f$1;

    public /* synthetic */ RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0(
            TimeZoneProviderRequest timeZoneProviderRequest,
            RealLocationTimeZoneProviderProxy.ManagerProxy managerProxy) {
        this.f$0 = timeZoneProviderRequest;
        this.f$1 = managerProxy;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
    public final void run(IBinder iBinder) {
        ITimeZoneProvider asInterface = ITimeZoneProvider.Stub.asInterface(iBinder);
        TimeZoneProviderRequest timeZoneProviderRequest = this.f$0;
        if (!timeZoneProviderRequest.mSendUpdates) {
            asInterface.stopUpdates();
            return;
        }
        asInterface.startUpdates(
                this.f$1,
                timeZoneProviderRequest.mInitializationTimeout.toMillis(),
                timeZoneProviderRequest.mEventFilteringAgeThreshold.toMillis());
    }
}
