package com.android.server.pm;

import android.content.res.Resources;

import com.android.internal.util.CollectionUtils;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class ShortcutUser$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShortcutUser$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPackage shortcutPackage = (ShortcutPackage) obj;
                if (shortcutPackage.mApiCallCount > 0) {
                    shortcutPackage.mApiCallCount = 0;
                    shortcutPackage.scheduleSave();
                }
                ShortcutService shortcutService = shortcutPackage.mShortcutUser.mService;
                Resources injectGetResourcesForApplicationAsUser =
                        shortcutService.injectGetResourcesForApplicationAsUser(
                                shortcutPackage.mPackageUserId, shortcutPackage.mPackageName);
                ArrayList arrayList = new ArrayList(1);
                if (injectGetResourcesForApplicationAsUser != null) {
                    shortcutPackage.forEachShortcutMutate(
                            new ShortcutPackage$$ExternalSyntheticLambda2(
                                    injectGetResourcesForApplicationAsUser,
                                    shortcutService,
                                    arrayList));
                }
                if (!CollectionUtils.isEmpty(arrayList)) {
                    shortcutService.packageShortcutsChanged(shortcutPackage, arrayList, null);
                    break;
                }
                break;
            default:
                ((ShortcutPackageItem) obj).attemptToRestoreIfNeededAndSave();
                break;
        }
    }
}
