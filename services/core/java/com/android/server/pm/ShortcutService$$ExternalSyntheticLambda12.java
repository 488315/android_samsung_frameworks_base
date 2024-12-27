package com.android.server.pm;

import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;

import java.util.function.Predicate;

public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda12 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                return shortcutInfo.isVisibleToPublisher()
                        && shortcutInfo.isDynamic()
                        && (shortcutInfo.isCached() || shortcutInfo.isPinned());
            case 1:
                ShortcutInfo shortcutInfo2 = (ShortcutInfo) obj;
                return shortcutInfo2.isVisibleToPublisher()
                        && shortcutInfo2.isDynamic()
                        && (shortcutInfo2.isCached() || shortcutInfo2.isPinned());
            case 2:
                ActivityInfo activityInfo = ((ResolveInfo) obj).activityInfo;
                return !(activityInfo != null
                        && ShortcutService.isInstalled(activityInfo.applicationInfo));
            default:
                ShortcutInfo shortcutInfo3 = (ShortcutInfo) obj;
                return (!shortcutInfo3.isVisibleToPublisher()
                                || !shortcutInfo3.isPinned()
                                || shortcutInfo3.isCached()
                                || shortcutInfo3.isDynamic()
                                || shortcutInfo3.isDeclaredInManifest())
                        ? false
                        : true;
        }
    }
}
