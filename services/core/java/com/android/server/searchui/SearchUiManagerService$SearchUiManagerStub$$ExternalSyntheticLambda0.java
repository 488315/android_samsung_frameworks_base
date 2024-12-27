package com.android.server.searchui;

import android.app.search.ISearchCallback;
import android.app.search.Query;
import android.app.search.SearchContext;
import android.app.search.SearchSessionId;
import android.app.search.SearchTargetEvent;
import android.os.IBinder;
import android.os.IInterface;
import android.service.search.ISearchUiService;

import com.android.internal.infra.AbstractRemoteService;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SearchSessionId f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda0(
            SearchContext searchContext, SearchSessionId searchSessionId, IBinder iBinder) {
        this.$r8$classId = 2;
        this.f$1 = searchContext;
        this.f$0 = searchSessionId;
        this.f$2 = iBinder;
    }

    public /* synthetic */ SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda0(
            SearchSessionId searchSessionId, Query query, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = searchSessionId;
        this.f$1 = query;
        this.f$2 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final SearchSessionId searchSessionId = this.f$0;
                final Query query = (Query) this.f$1;
                final SearchTargetEvent searchTargetEvent = (SearchTargetEvent) this.f$2;
                SearchUiPerUserService searchUiPerUserService = (SearchUiPerUserService) obj;
                if (((SearchUiPerUserService.SearchSessionInfo)
                                searchUiPerUserService.mSessionInfos.get(searchSessionId))
                        != null) {
                    final int i = 0;
                    searchUiPerUserService.resolveService(
                            new AbstractRemoteService
                                    .AsyncRequest() { // from class:
                                                      // com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda0
                                public final void run(IInterface iInterface) {
                                    switch (i) {
                                        case 0:
                                            ((ISearchUiService) iInterface)
                                                    .onNotifyEvent(
                                                            searchSessionId,
                                                            query,
                                                            (SearchTargetEvent) searchTargetEvent);
                                            break;
                                        default:
                                            ((ISearchUiService) iInterface)
                                                    .onQuery(
                                                            searchSessionId,
                                                            query,
                                                            (ISearchCallback) searchTargetEvent);
                                            break;
                                    }
                                }
                            });
                    break;
                }
                break;
            case 1:
                final SearchSessionId searchSessionId2 = this.f$0;
                final Query query2 = (Query) this.f$1;
                final ISearchCallback iSearchCallback = (ISearchCallback) this.f$2;
                SearchUiPerUserService searchUiPerUserService2 = (SearchUiPerUserService) obj;
                if (((SearchUiPerUserService.SearchSessionInfo)
                                searchUiPerUserService2.mSessionInfos.get(searchSessionId2))
                        != null) {
                    final int i2 = 1;
                    searchUiPerUserService2.resolveService(
                            new AbstractRemoteService
                                    .AsyncRequest() { // from class:
                                                      // com.android.server.searchui.SearchUiPerUserService$$ExternalSyntheticLambda0
                                public final void run(IInterface iInterface) {
                                    switch (i2) {
                                        case 0:
                                            ((ISearchUiService) iInterface)
                                                    .onNotifyEvent(
                                                            searchSessionId2,
                                                            query2,
                                                            (SearchTargetEvent) iSearchCallback);
                                            break;
                                        default:
                                            ((ISearchUiService) iInterface)
                                                    .onQuery(
                                                            searchSessionId2,
                                                            query2,
                                                            (ISearchCallback) iSearchCallback);
                                            break;
                                    }
                                }
                            });
                    break;
                }
                break;
            default:
                ((SearchUiPerUserService) obj)
                        .onCreateSearchSessionLocked(
                                (SearchContext) this.f$1, this.f$0, (IBinder) this.f$2);
                break;
        }
    }
}
