package com.android.server.ambientcontext;

import android.R;
import android.content.ComponentName;
import android.content.Intent;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

public final class WearableAmbientContextManagerPerUserService
        extends AmbientContextManagerPerUserService {
    public ComponentName mComponentName;
    RemoteWearableSensingService mRemoteService;
    public final AmbientContextManagerPerUserService.ServiceType mServiceType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WearableAmbientContextManagerPerUserService(
            AmbientContextManagerService ambientContextManagerService,
            Object obj,
            int i,
            String str) {
        super(ambientContextManagerService, obj, i);
        AmbientContextManagerPerUserService.ServiceType serviceType =
                AmbientContextManagerPerUserService.ServiceType.WEARABLE;
        this.mServiceType = serviceType;
        this.mComponentName = ComponentName.unflattenFromString(str);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "Created WearableAmbientContextManagerPerUserServiceand service type: WEARABLE and"
                    + " service name: ",
                str,
                "WearableAmbientContextManagerPerUserService");
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final void clearRemoteService() {
        this.mRemoteService = null;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final void ensureRemoteServiceInitiated() {
        if (this.mRemoteService == null) {
            RemoteWearableSensingService remoteWearableSensingService =
                    new RemoteWearableSensingService(
                            this.mMaster.getContext(),
                            new Intent("android.service.wearable.WearableSensingService")
                                    .setComponent(this.mComponentName),
                            67112960,
                            this.mUserId,
                            new RemoteWearableSensingService$$ExternalSyntheticLambda0());
            remoteWearableSensingService.connect();
            this.mRemoteService = remoteWearableSensingService;
        }
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final int getAmbientContextEventArrayExtraKeyConfig() {
        return R.string.face_error_vendor_unknown;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final int getAmbientContextPackageNameExtraKeyConfig() {
        return R.string.face_icon_content_description;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final int getConsentComponentConfig() {
        return R.string.device_state_notification_turn_off_button;
    }

    @Override // com.android.server.ambientcontext.AmbientContextManagerPerUserService
    public final String getProtectedBindPermission() {
        return "android.permission.BIND_WEARABLE_SENSING_SERVICE";
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
