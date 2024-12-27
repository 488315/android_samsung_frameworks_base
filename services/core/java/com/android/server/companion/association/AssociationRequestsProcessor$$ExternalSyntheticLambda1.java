package com.android.server.companion.association;

import android.companion.AssociationInfo;
import android.companion.IAssociationRequestCallback;
import android.os.ResultReceiver;
import android.util.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class AssociationRequestsProcessor$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ AssociationRequestsProcessor f$0;
    public final /* synthetic */ AssociationInfo f$1;
    public final /* synthetic */ IAssociationRequestCallback f$2;
    public final /* synthetic */ ResultReceiver f$3;

    public /* synthetic */ AssociationRequestsProcessor$$ExternalSyntheticLambda1(
            AssociationRequestsProcessor associationRequestsProcessor,
            AssociationInfo associationInfo,
            IAssociationRequestCallback iAssociationRequestCallback,
            ResultReceiver resultReceiver) {
        this.f$0 = associationRequestsProcessor;
        this.f$1 = associationInfo;
        this.f$2 = iAssociationRequestCallback;
        this.f$3 = resultReceiver;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AssociationRequestsProcessor associationRequestsProcessor = this.f$0;
        AssociationInfo associationInfo = this.f$1;
        IAssociationRequestCallback iAssociationRequestCallback = this.f$2;
        ResultReceiver resultReceiver = this.f$3;
        associationRequestsProcessor.getClass();
        if (((Boolean) obj).booleanValue()) {
            Slog.i(
                    "CDM_AssociationRequestsProcessor",
                    "Added "
                            + associationInfo.getDeviceProfile()
                            + " role to userId="
                            + associationInfo.getUserId()
                            + ", packageName="
                            + associationInfo.getPackageName());
            associationRequestsProcessor.mAssociationStore.addAssociation(associationInfo);
            AssociationRequestsProcessor.sendCallbackAndFinish(
                    associationInfo, iAssociationRequestCallback, resultReceiver);
            return;
        }
        Slog.e(
                "CDM_AssociationRequestsProcessor",
                "Failed to add u"
                        + associationInfo.getUserId()
                        + "\\"
                        + associationInfo.getPackageName()
                        + " to the list of "
                        + associationInfo.getDeviceProfile()
                        + " holders.");
        AssociationRequestsProcessor.sendCallbackAndFinish(
                null, iAssociationRequestCallback, resultReceiver);
    }
}
