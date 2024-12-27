package com.android.server.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.hardware.HardwareBuffer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.service.contentsuggestions.IContentSuggestionsService;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteContentSuggestionsService extends AbstractMultiplePendingRequestsRemoteService {
    public final void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) {
        scheduleAsyncRequest(new RemoteContentSuggestionsService$$ExternalSyntheticLambda0(0, classificationsRequest, iClassificationsCallback));
    }

    public final long getRemoteRequestMillis() {
        return 2000L;
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return IContentSuggestionsService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return 0L;
    }

    public final void notifyInteraction(Bundle bundle, String str) {
        scheduleAsyncRequest(new RemoteContentSuggestionsService$$ExternalSyntheticLambda0(1, str, bundle));
    }

    public final void provideContextImage(final int i, final HardwareBuffer hardwareBuffer, final int i2, final Bundle bundle) {
        scheduleAsyncRequest(new AbstractRemoteService.AsyncRequest() { // from class: com.android.server.contentsuggestions.RemoteContentSuggestionsService$$ExternalSyntheticLambda1
            public final void run(IInterface iInterface) {
                ((IContentSuggestionsService) iInterface).provideContextImage(i, hardwareBuffer, i2, bundle);
            }
        });
    }

    public final void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) {
        scheduleAsyncRequest(new RemoteContentSuggestionsService$$ExternalSyntheticLambda0(2, selectionsRequest, iSelectionsCallback));
    }
}
