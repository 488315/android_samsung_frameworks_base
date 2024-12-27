package com.android.server.searchui;

import android.app.search.ISearchCallback;
import android.app.search.SearchSessionId;
import android.util.Slog;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SearchSessionId f$0;
    public final /* synthetic */ ISearchCallback f$1;

    public /* synthetic */ SearchUiManagerService$SearchUiManagerStub$$ExternalSyntheticLambda3(
            SearchSessionId searchSessionId, ISearchCallback iSearchCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = searchSessionId;
        this.f$1 = iSearchCallback;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SearchUiPerUserService) obj)
                        .registerEmptyQueryResultUpdateCallbackLocked(this.f$0, this.f$1);
                break;
            default:
                SearchSessionId searchSessionId = this.f$0;
                ISearchCallback iSearchCallback = this.f$1;
                SearchUiPerUserService searchUiPerUserService = (SearchUiPerUserService) obj;
                SearchUiPerUserService.SearchSessionInfo searchSessionInfo =
                        (SearchUiPerUserService.SearchSessionInfo)
                                searchUiPerUserService.mSessionInfos.get(searchSessionId);
                if (searchSessionInfo != null
                        && searchUiPerUserService.resolveService(
                                new SearchUiPerUserService$$ExternalSyntheticLambda3(
                                        searchSessionId, iSearchCallback, 1))) {
                    Slog.d(
                            "SearchUiPerUserService",
                            "Removing callback for session Id="
                                    + searchSessionInfo.mSessionId
                                    + " and callback="
                                    + iSearchCallback.asBinder());
                    searchSessionInfo.mCallbacks.unregister(iSearchCallback);
                    break;
                }
                break;
        }
    }
}
