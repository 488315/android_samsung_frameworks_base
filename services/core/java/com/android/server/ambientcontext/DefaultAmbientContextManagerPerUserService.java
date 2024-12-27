package com.android.server.ambientcontext;

import android.R;
import android.content.ComponentName;
import android.content.Intent;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

public final class DefaultAmbientContextManagerPerUserService
        extends AmbientContextManagerPerUserService {
    public ComponentName mComponentName;
    DefaultRemoteAmbientContextDetectionService mRemoteService;
    public final AmbientContextManagerPerUserService.ServiceType mServiceType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultAmbientContextManagerPerUserService(
            AmbientContextManagerService ambientContextManagerService,
            Object obj,
            int i,
            String str) {
        super(ambientContextManagerService, obj, i);
        AmbientContextManagerPerUserService.ServiceType serviceType =
                AmbientContextManagerPerUserService.ServiceType.DEFAULT;
        this.mServiceType = serviceType;
        this.mComponentName = ComponentName.unflattenFromString(str);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "Created DefaultAmbientContextManagerPerUserServiceand service type: DEFAULT and"
                    + " service name: ",
                str,
                "DefaultAmbientContextManagerPerUserService");
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final void clearRemoteService() {
        this.mRemoteService = null;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final void ensureRemoteServiceInitiated() {
        if (this.mRemoteService == null) {
            DefaultRemoteAmbientContextDetectionService
                    defaultRemoteAmbientContextDetectionService =
                            new DefaultRemoteAmbientContextDetectionService(
                                    this.mMaster.getContext(),
                                    new Intent(
                                                    "android.service.ambientcontext.AmbientContextDetectionService")
                                            .setComponent(this.mComponentName),
                                    67112960,
                                    this.mUserId,
                                    new DefaultRemoteAmbientContextDetectionService$$ExternalSyntheticLambda0());
            defaultRemoteAmbientContextDetectionService.connect();
            this.mRemoteService = defaultRemoteAmbientContextDetectionService;
        }
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final int getAmbientContextEventArrayExtraKeyConfig() {
        return R.string.config_wwan_network_service_package;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final int getAmbientContextPackageNameExtraKeyConfig() {
        return R.string.confirm_battery_saver;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final int getConsentComponentConfig() {
        return R.string.data_usage_wifi_limit_snoozed_title;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final String getProtectedBindPermission() {
        return "android.permission.BIND_AMBIENT_CONTEXT_DETECTION_SERVICE";
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final RemoteAmbientDetectionService getRemoteService() {
        return this.mRemoteService;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final AmbientContextManagerPerUserService.ServiceType getServiceType() {
        return this.mServiceType;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final void setComponentName(ComponentName componentName) {
        this.mComponentName = componentName;
    }
}
