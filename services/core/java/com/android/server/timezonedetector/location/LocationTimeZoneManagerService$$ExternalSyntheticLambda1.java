package com.android.server.timezonedetector.location;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class LocationTimeZoneManagerService$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocationTimeZoneManagerService f$0;

    public /* synthetic */ LocationTimeZoneManagerService$$ExternalSyntheticLambda1(LocationTimeZoneManagerService locationTimeZoneManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = locationTimeZoneManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        LocationTimeZoneManagerService locationTimeZoneManagerService = this.f$0;
        switch (i) {
            case 0:
                locationTimeZoneManagerService.stopOnDomainThread();
                return;
            case 1:
                locationTimeZoneManagerService.startOnDomainThread();
                return;
            case 2:
                synchronized (locationTimeZoneManagerService.mSharedLock) {
                    LocationTimeZoneProviderController locationTimeZoneProviderController = locationTimeZoneManagerService.mLocationTimeZoneProviderController;
                    if (locationTimeZoneProviderController != null) {
                        locationTimeZoneProviderController.mThreadingDomain.assertCurrentThread();
                        synchronized (locationTimeZoneProviderController.mSharedLock) {
                            locationTimeZoneProviderController.mRecordedStates.clear();
                            locationTimeZoneProviderController.mPrimaryProvider.clearRecordedStates();
                            locationTimeZoneProviderController.mSecondaryProvider.clearRecordedStates();
                        }
                    }
                }
                return;
            default:
                locationTimeZoneManagerService.mThreadingDomain.assertCurrentThread();
                synchronized (locationTimeZoneManagerService.mSharedLock) {
                    try {
                        if (locationTimeZoneManagerService.mLocationTimeZoneProviderController != null) {
                            locationTimeZoneManagerService.stopOnDomainThread();
                            locationTimeZoneManagerService.startOnDomainThread();
                        }
                    } finally {
                    }
                }
                return;
        }
    }
}
