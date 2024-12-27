package com.android.server.companion.association;

import android.R;
import android.companion.AssociatedDevice;
import android.companion.AssociationInfo;
import android.companion.AssociationRequest;
import android.companion.IAssociationRequestCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.net.MacAddress;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ResultReceiver;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.companion.utils.PermissionsUtils;
import com.android.server.companion.utils.RolesUtils;

import java.util.Objects;

public final class AssociationRequestsProcessor {
    public final AssociationStore mAssociationStore;
    public final ComponentName mCompanionAssociationActivity;
    public final Context mContext;
    public final AnonymousClass1 mOnRequestConfirmationReceiver =
            new ResultReceiver(
                    Handler
                            .getMain()) { // from class:
                                          // com.android.server.companion.association.AssociationRequestsProcessor.1
                @Override // android.os.ResultReceiver
                public final void onReceiveResult(int i, Bundle bundle) {
                    MacAddress macAddress;
                    if (i != 0) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                i, "Unknown result code:", "CDM_AssociationRequestsProcessor");
                        return;
                    }
                    AssociationRequest associationRequest =
                            (AssociationRequest)
                                    bundle.getParcelable(
                                            "association_request", AssociationRequest.class);
                    IAssociationRequestCallback asInterface =
                            IAssociationRequestCallback.Stub.asInterface(
                                    bundle.getBinder("application_callback"));
                    ResultReceiver resultReceiver =
                            (ResultReceiver)
                                    bundle.getParcelable("result_receiver", ResultReceiver.class);
                    Objects.requireNonNull(associationRequest);
                    Objects.requireNonNull(asInterface);
                    Objects.requireNonNull(resultReceiver);
                    if (associationRequest.isSelfManaged()) {
                        macAddress = null;
                    } else {
                        macAddress =
                                (MacAddress) bundle.getParcelable("mac_address", MacAddress.class);
                        Objects.requireNonNull(macAddress);
                    }
                    MacAddress macAddress2 = macAddress;
                    AssociationRequestsProcessor associationRequestsProcessor =
                            AssociationRequestsProcessor.this;
                    associationRequestsProcessor.getClass();
                    String packageName = associationRequest.getPackageName();
                    int userId = associationRequest.getUserId();
                    try {
                        PermissionsUtils.enforcePermissionForCreatingAssociation(
                                associationRequestsProcessor.mContext,
                                associationRequest,
                                associationRequestsProcessor.mPackageManagerInternal.getPackageUid(
                                        packageName, 0L, userId));
                        Binder.withCleanCallingIdentity(
                                new AssociationRequestsProcessor$$ExternalSyntheticLambda0(
                                        associationRequestsProcessor,
                                        userId,
                                        packageName,
                                        macAddress2,
                                        associationRequest,
                                        asInterface,
                                        resultReceiver));
                    } catch (SecurityException e) {
                        try {
                            asInterface.onFailure(e.getMessage());
                        } catch (RemoteException unused) {
                        }
                    }
                }
            };
    public final PackageManagerInternal mPackageManagerInternal;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.companion.association.AssociationRequestsProcessor$1] */
    public AssociationRequestsProcessor(
            Context context,
            PackageManagerInternal packageManagerInternal,
            AssociationStore associationStore) {
        this.mContext = context;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mAssociationStore = associationStore;
        this.mCompanionAssociationActivity =
                ComponentName.createRelative(
                        context.getString(R.string.csd_momentary_exposure_warning),
                        ".CompanionAssociationActivity");
    }

    public static void sendCallbackAndFinish(
            AssociationInfo associationInfo,
            IAssociationRequestCallback iAssociationRequestCallback,
            ResultReceiver resultReceiver) {
        if (associationInfo == null) {
            if (iAssociationRequestCallback != null) {
                try {
                    iAssociationRequestCallback.onFailure("internal_error");
                } catch (RemoteException unused) {
                }
            }
            if (resultReceiver != null) {
                resultReceiver.send(3, new Bundle());
                return;
            }
            return;
        }
        if (iAssociationRequestCallback != null) {
            try {
                iAssociationRequestCallback.onAssociationCreated(associationInfo);
            } catch (RemoteException unused2) {
            }
        }
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("association", associationInfo);
            resultReceiver.send(0, bundle);
        }
    }

    public final void createAssociation(
            int i,
            String str,
            MacAddress macAddress,
            CharSequence charSequence,
            String str2,
            AssociatedDevice associatedDevice,
            boolean z,
            IAssociationRequestCallback iAssociationRequestCallback,
            ResultReceiver resultReceiver) {
        int i2;
        int i3;
        AssociationStore associationStore = this.mAssociationStore;
        synchronized (associationStore.mLock) {
            synchronized (associationStore.mLock) {
                i2 = associationStore.mMaxId;
            }
            i3 = i2 + 1;
        }
        AssociationInfo associationInfo =
                new AssociationInfo(
                        i3,
                        i,
                        str,
                        null,
                        macAddress,
                        charSequence,
                        str2,
                        associatedDevice,
                        z,
                        false,
                        false,
                        false,
                        System.currentTimeMillis(),
                        Long.MAX_VALUE,
                        0);
        RolesUtils.addRoleHolderForAssociation(
                this.mContext,
                associationInfo,
                new AssociationRequestsProcessor$$ExternalSyntheticLambda1(
                        this, associationInfo, iAssociationRequestCallback, resultReceiver));
    }
}
