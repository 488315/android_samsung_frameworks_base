package com.android.server.pm;

import android.app.appsearch.AppSearchResult;
import android.content.pm.ShortcutInfo;
import android.util.Slog;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda14 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda14(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((ShortcutInfo) obj).clearFlags(1610629120);
                break;
            case 1:
                ((ShortcutInfo) obj).clearImplicitRankAndRankChangedFlag();
                break;
            case 2:
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                if (!shortcutInfo.isPinned()) {
                    shortcutInfo.addFlags(2);
                    break;
                }
                break;
            case 3:
                ((ShortcutLauncher) obj).scheduleSave();
                break;
            case 4:
                AppSearchResult appSearchResult = (AppSearchResult) obj;
                if (!appSearchResult.isSuccess()) {
                    Slog.e(
                            "ShortcutService",
                            "Failed to report usage via AppSearch. "
                                    + appSearchResult.getErrorMessage());
                    break;
                }
                break;
            default:
                AppSearchResult appSearchResult2 = (AppSearchResult) obj;
                if (!appSearchResult2.isSuccess()) {
                    Slog.e(
                            "ShortcutService",
                            "Failed to remove shortcuts from AppSearch. "
                                    + appSearchResult2.getErrorMessage());
                    break;
                }
                break;
        }
    }
}
