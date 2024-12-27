package com.android.server.autofill;

import android.service.autofill.augmented.IAugmentedAutofillService;

import com.android.internal.infra.ServiceConnector;

public final /* synthetic */ class RemoteAugmentedAutofillService$$ExternalSyntheticLambda1
        implements ServiceConnector.VoidJob {
    public final void runNoResult(Object obj) {
        int i = RemoteAugmentedAutofillService.$r8$clinit;
        ((IAugmentedAutofillService) obj).onDestroyAllFillWindowsRequest();
    }
}
