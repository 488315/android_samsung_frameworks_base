package com.android.server.autofill;

import android.service.autofill.augmented.IAugmentedAutofillService;
import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RemoteAugmentedAutofillService$$ExternalSyntheticLambda1 implements ServiceConnector.VoidJob {
    public final void runNoResult(Object obj) {
        int i = RemoteAugmentedAutofillService.$r8$clinit;
        ((IAugmentedAutofillService) obj).onDestroyAllFillWindowsRequest();
    }
}
