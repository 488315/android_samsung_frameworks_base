package com.android.server.companion.association;

import android.companion.AssociationRequest;
import android.companion.IAssociationRequestCallback;
import android.net.MacAddress;
import android.os.ResultReceiver;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AssociationRequestsProcessor$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ AssociationRequestsProcessor f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ MacAddress f$3;
    public final /* synthetic */ AssociationRequest f$4;
    public final /* synthetic */ IAssociationRequestCallback f$5;
    public final /* synthetic */ ResultReceiver f$6;

    public /* synthetic */ AssociationRequestsProcessor$$ExternalSyntheticLambda0(
            AssociationRequestsProcessor associationRequestsProcessor,
            int i,
            String str,
            MacAddress macAddress,
            AssociationRequest associationRequest,
            IAssociationRequestCallback iAssociationRequestCallback,
            ResultReceiver resultReceiver) {
        this.f$0 = associationRequestsProcessor;
        this.f$1 = i;
        this.f$2 = str;
        this.f$3 = macAddress;
        this.f$4 = associationRequest;
        this.f$5 = iAssociationRequestCallback;
        this.f$6 = resultReceiver;
    }

    public final void runOrThrow() {
        AssociationRequestsProcessor associationRequestsProcessor = this.f$0;
        int i = this.f$1;
        String str = this.f$2;
        MacAddress macAddress = this.f$3;
        AssociationRequest associationRequest = this.f$4;
        IAssociationRequestCallback iAssociationRequestCallback = this.f$5;
        ResultReceiver resultReceiver = this.f$6;
        associationRequestsProcessor.getClass();
        associationRequestsProcessor.createAssociation(
                i,
                str,
                macAddress,
                associationRequest.getDisplayName(),
                associationRequest.getDeviceProfile(),
                associationRequest.getAssociatedDevice(),
                associationRequest.isSelfManaged(),
                iAssociationRequestCallback,
                resultReceiver);
    }
}
