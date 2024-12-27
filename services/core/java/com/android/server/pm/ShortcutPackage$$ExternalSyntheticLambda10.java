package com.android.server.pm;

import android.app.appsearch.AppSearchSession;
import android.content.pm.ShortcutInfo;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda10(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                ((ShortcutPackage) obj2).getClass();
                ShortcutPackage.ensureNotImmutable(shortcutInfo);
                shortcutInfo.clearFlags(64);
                shortcutInfo.setDisabledReason(0);
                break;
            case 1:
                ShortcutPackage shortcutPackage = (ShortcutPackage) obj2;
                ((AppSearchSession) obj).remove("", shortcutPackage.getSearchSpec(), shortcutPackage.mShortcutUser.mExecutor, new ShortcutPackage$$ExternalSyntheticLambda14(5));
                break;
            default:
                ShortcutInfo shortcutInfo2 = (ShortcutInfo) obj;
                if (!((Set) obj2).contains(shortcutInfo2.getId()) && shortcutInfo2.isPinned()) {
                    shortcutInfo2.clearFlags(2);
                    break;
                }
                break;
        }
    }
}
