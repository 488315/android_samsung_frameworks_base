package com.android.server.companion.association;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.UserHandle;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class AssociationRequestsProcessor$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ AssociationRequestsProcessor f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Intent f$2;

    public /* synthetic */ AssociationRequestsProcessor$$ExternalSyntheticLambda2(
            AssociationRequestsProcessor associationRequestsProcessor, int i, Intent intent) {
        this.f$0 = associationRequestsProcessor;
        this.f$1 = i;
        this.f$2 = intent;
    }

    public final Object getOrThrow() {
        AssociationRequestsProcessor associationRequestsProcessor = this.f$0;
        return PendingIntent.getActivityAsUser(
                associationRequestsProcessor.mContext,
                this.f$1,
                this.f$2,
                1409286144,
                ActivityOptions.makeBasic()
                        .setPendingIntentCreatorBackgroundActivityStartMode(1)
                        .toBundle(),
                UserHandle.CURRENT);
    }
}
