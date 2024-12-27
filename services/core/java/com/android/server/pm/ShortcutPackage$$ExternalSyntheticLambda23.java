package com.android.server.pm;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;
import android.app.appsearch.ReportUsageRequest;
import android.content.ComponentName;
import android.content.pm.ShortcutInfo;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.infra.AndroidFuture;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda23 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda23(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPackage shortcutPackage = (ShortcutPackage) this.f$0;
                ArrayMap arrayMap = (ArrayMap) this.f$1;
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                shortcutPackage.getClass();
                if (!shortcutInfo.isFloating()) {
                    ComponentName activity = shortcutInfo.getActivity();
                    if (activity != null) {
                        ((ArrayList)
                                        arrayMap.computeIfAbsent(
                                                activity,
                                                new ShortcutPackage$$ExternalSyntheticLambda51(0)))
                                .add(shortcutInfo);
                        break;
                    } else {
                        shortcutPackage.mShortcutUser.mService.wtf("null activity detected.", null);
                        break;
                    }
                }
                break;
            case 1:
                ShortcutPackage shortcutPackage2 = (ShortcutPackage) this.f$0;
                ShortcutInfo shortcutInfo2 = (ShortcutInfo) this.f$1;
                shortcutPackage2.getClass();
                ((AppSearchSession) obj)
                        .reportUsage(
                                new ReportUsageRequest.Builder(
                                                shortcutPackage2.mPackageName,
                                                shortcutInfo2.getId())
                                        .build(),
                                shortcutPackage2.mExecutor,
                                new ShortcutPackage$$ExternalSyntheticLambda14(4));
                break;
            case 2:
                ShortcutPackage shortcutPackage3 = (ShortcutPackage) this.f$0;
                Set set = (Set) this.f$1;
                ArraySet pinnedShortcutIds =
                        ((ShortcutLauncher) obj)
                                .getPinnedShortcutIds(
                                        shortcutPackage3.mPackageUserId,
                                        shortcutPackage3.mPackageName);
                if (pinnedShortcutIds != null && pinnedShortcutIds.size() != 0) {
                    set.addAll(pinnedShortcutIds);
                    break;
                }
                break;
            default:
                AndroidFuture androidFuture = (AndroidFuture) this.f$0;
                AppSearchSession appSearchSession = (AppSearchSession) this.f$1;
                AppSearchResult appSearchResult = (AppSearchResult) obj;
                if (!appSearchResult.isSuccess()) {
                    androidFuture.completeExceptionally(
                            new IllegalArgumentException(appSearchResult.getErrorMessage()));
                    break;
                } else {
                    androidFuture.complete(appSearchSession);
                    break;
                }
        }
    }
}
