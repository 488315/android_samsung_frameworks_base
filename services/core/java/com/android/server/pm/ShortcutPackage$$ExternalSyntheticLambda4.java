package com.android.server.pm;

import android.content.pm.ShortcutInfo;

import java.util.Objects;
import java.util.function.Predicate;

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
