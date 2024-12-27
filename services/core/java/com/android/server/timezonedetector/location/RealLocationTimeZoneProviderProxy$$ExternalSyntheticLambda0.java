package com.android.server.timezonedetector.location;

import android.os.IBinder;
import android.service.timezone.ITimeZoneProvider;
import com.android.server.servicewatcher.ServiceWatcher$BinderOperation;
import com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0 implements ServiceWatcher$BinderOperation {
    public final /* synthetic */ TimeZoneProviderRequest f$0;
    public final /* synthetic */ RealLocationTimeZoneProviderProxy.ManagerProxy f$1;

    public /* synthetic */ RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0(TimeZoneProviderRequest timeZoneProviderRequest, RealLocationTimeZoneProviderProxy.ManagerProxy managerProxy) {
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
        asInterface.startUpdates(this.f$1, timeZoneProviderRequest.mInitializationTimeout.toMillis(), timeZoneProviderRequest.mEventFilteringAgeThreshold.toMillis());
    }
}
