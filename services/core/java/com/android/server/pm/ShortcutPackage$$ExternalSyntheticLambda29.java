package com.android.server.pm;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;

import com.android.internal.infra.AndroidFuture;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda29 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShortcutPackage f$0;
    public final /* synthetic */ AndroidFuture f$1;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda29(
            ShortcutPackage shortcutPackage, AndroidFuture androidFuture, int i) {
        this.$r8$classId = i;
        this.f$0 = shortcutPackage;
        this.f$1 = androidFuture;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPackage shortcutPackage = this.f$0;
                ((AppSearchSession) obj)
                        .search("", shortcutPackage.getSearchSpec())
                        .getNextPage(
                                shortcutPackage.mShortcutUser.mExecutor,
                                new ShortcutPackage$$ExternalSyntheticLambda29(
                                        shortcutPackage, this.f$1, 1));
                break;
            default:
                ShortcutPackage shortcutPackage2 = this.f$0;
                AndroidFuture androidFuture = this.f$1;
                AppSearchResult appSearchResult = (AppSearchResult) obj;
                shortcutPackage2.getClass();
                if (!appSearchResult.isSuccess()) {
                    androidFuture.completeExceptionally(
                            new IllegalStateException(appSearchResult.getErrorMessage()));
                    break;
                } else {
                    androidFuture.complete(
                            (List)
                                    ((List) appSearchResult.getResultValue())
                                            .stream()
                                                    .map(
                                                            new ShortcutPackage$$ExternalSyntheticLambda51(
                                                                    1))
                                                    .map(
                                                            new ShortcutPackage$$ExternalSyntheticLambda43(
                                                                    shortcutPackage2, 1))
                                                    .collect(Collectors.toList()));
                    break;
                }
        }
    }
}
