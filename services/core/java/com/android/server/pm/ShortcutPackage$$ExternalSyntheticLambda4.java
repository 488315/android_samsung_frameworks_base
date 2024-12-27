package com.android.server.pm;

import android.content.pm.ShortcutInfo;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                return shortcutInfo.isNonManifestVisible();
            case 1:
                return !shortcutInfo.isPinned();
            case 2:
                return shortcutInfo.usesQuota();
            case 3:
                return Objects.nonNull(shortcutInfo);
            case 4:
                return !shortcutInfo.isDynamic();
            default:
                return !shortcutInfo.isManifestShortcut();
        }
    }
}
