package com.android.server.searchui;

import android.app.search.ISearchCallback;
import android.app.search.SearchContext;
import android.app.search.SearchSessionId;
import android.os.IInterface;
import android.service.search.ISearchUiService;

import com.android.internal.infra.AbstractRemoteService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SearchUiPerUserService$$ExternalSyntheticLambda3
        implements AbstractRemoteService.AsyncRequest {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SearchSessionId f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SearchUiPerUserService$$ExternalSyntheticLambda3(
            SearchContext searchContext, SearchSessionId searchSessionId) {
        this.$r8$classId = 2;
        this.f$1 = searchContext;
        this.f$0 = searchSessionId;
    }

    public /* synthetic */ SearchUiPerUserService$$ExternalSyntheticLambda3(
            SearchSessionId searchSessionId, ISearchCallback iSearchCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = searchSessionId;
        this.f$1 = iSearchCallback;
    }

    public final void run(IInterface iInterface) {
        switch (this.$r8$classId) {
            case 0:
                ((ISearchUiService) iInterface)
                        .onRegisterEmptyQueryResultUpdateCallback(
                                this.f$0, (ISearchCallback) this.f$1);
                break;
            case 1:
                ((ISearchUiService) iInterface)
                        .onUnregisterEmptyQueryResultUpdateCallback(
                                this.f$0, (ISearchCallback) this.f$1);
                break;
            default:
                ((ISearchUiService) iInterface)
                        .onCreateSearchSession((SearchContext) this.f$1, this.f$0);
                break;
        }
    }
}
