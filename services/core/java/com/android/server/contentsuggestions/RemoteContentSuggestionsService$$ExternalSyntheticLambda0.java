package com.android.server.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.os.Bundle;
import android.os.IInterface;
import android.service.contentsuggestions.IContentSuggestionsService;
import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RemoteContentSuggestionsService$$ExternalSyntheticLambda0 implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RemoteContentSuggestionsService$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run(IInterface iInterface) {
        switch (this.$r8$classId) {
            case 0:
                ((IContentSuggestionsService) iInterface).classifyContentSelections((ClassificationsRequest) this.f$0, (IClassificationsCallback) this.f$1);
                break;
            case 1:
                ((IContentSuggestionsService) iInterface).notifyInteraction((String) this.f$0, (Bundle) this.f$1);
                break;
            default:
                ((IContentSuggestionsService) iInterface).suggestContentSelections((SelectionsRequest) this.f$0, (ISelectionsCallback) this.f$1);
                break;
        }
    }
}
