package com.android.server.timezonedetector.location;

import android.app.ActivityManagerInternal;
import com.android.server.LocalServices;
import com.android.server.timezonedetector.ConfigurationInternal;
import com.android.server.timezonedetector.ServiceConfigAccessor;
import com.android.server.timezonedetector.ServiceConfigAccessorImpl;
import com.android.server.timezonedetector.StateChangeListener;
import java.util.ArrayList;
import java.util.Objects;

public final class LocationTimeZoneProviderControllerEnvironmentImpl {
    public final LocationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda0 mConfigurationInternalChangeListener;
    public final ServiceConfigAccessor mServiceConfigAccessor;
    public final Object mSharedLock;
    public final HandlerThreadingDomain mThreadingDomain;

    public LocationTimeZoneProviderControllerEnvironmentImpl(HandlerThreadingDomain handlerThreadingDomain, ServiceConfigAccessor serviceConfigAccessor, final LocationTimeZoneProviderController locationTimeZoneProviderController) {
        Objects.requireNonNull(handlerThreadingDomain);
        this.mThreadingDomain = handlerThreadingDomain;
        this.mSharedLock = handlerThreadingDomain.mLockObject;
        Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
        ?? r1 = new StateChangeListener() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda0
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                LocationTimeZoneProviderControllerEnvironmentImpl locationTimeZoneProviderControllerEnvironmentImpl = LocationTimeZoneProviderControllerEnvironmentImpl.this;
                locationTimeZoneProviderControllerEnvironmentImpl.getClass();
                final LocationTimeZoneProviderController locationTimeZoneProviderController2 = locationTimeZoneProviderController;
                Objects.requireNonNull(locationTimeZoneProviderController2);
                locationTimeZoneProviderControllerEnvironmentImpl.mThreadingDomain.mHandler.post(new Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ConfigurationInternal configurationInternal;
                        LocationTimeZoneProviderController locationTimeZoneProviderController3 = LocationTimeZoneProviderController.this;
                        locationTimeZoneProviderController3.mThreadingDomain.assertCurrentThread();
                        synchronized (locationTimeZoneProviderController3.mSharedLock) {
                            try {
                                LocationTimeZoneManagerService.debugLog("onConfigChanged()");
                                ConfigurationInternal configurationInternal2 = locationTimeZoneProviderController3.mCurrentUserConfiguration;
                                ServiceConfigAccessorImpl serviceConfigAccessorImpl = (ServiceConfigAccessorImpl) locationTimeZoneProviderController3.mEnvironment.mServiceConfigAccessor;
                                synchronized (serviceConfigAccessorImpl) {
                                    configurationInternal = serviceConfigAccessorImpl.getConfigurationInternal(((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getCurrentUserId());
                                }
                                locationTimeZoneProviderController3.mCurrentUserConfiguration = configurationInternal;
                                if (!configurationInternal.equals(configurationInternal2)) {
                                    if (configurationInternal.mUserId != configurationInternal2.mUserId) {
                                        String str = "User changed. old=" + configurationInternal2.mUserId + ", new=" + configurationInternal.mUserId;
                                        LocationTimeZoneManagerService.debugLog("Stopping providers: " + str);
                                        locationTimeZoneProviderController3.stopProviders(str);
                                        locationTimeZoneProviderController3.alterProvidersStartedStateIfRequired(null, configurationInternal);
                                    } else {
                                        locationTimeZoneProviderController3.alterProvidersStartedStateIfRequired(configurationInternal2, configurationInternal);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
            }
        };
        this.mConfigurationInternalChangeListener = r1;
        ServiceConfigAccessorImpl serviceConfigAccessorImpl = (ServiceConfigAccessorImpl) serviceConfigAccessor;
        synchronized (serviceConfigAccessorImpl) {
            ((ArrayList) serviceConfigAccessorImpl.mConfigurationInternalListeners).add(r1);
        }
    }
}
