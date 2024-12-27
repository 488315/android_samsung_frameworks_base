package com.android.server.pm;

import java.util.function.Consumer;

public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((ShortcutPackage) obj).refreshPinnedFlags();
                break;
            case 1:
                ((ShortcutUser) obj)
                        .forAllPackageItems(new ShortcutService$$ExternalSyntheticLambda4(8));
                break;
            case 2:
                ((ShortcutPackageItem) obj).refreshPackageSignatureAndSave();
                break;
            case 3:
                ((ShortcutLauncher) obj).ensurePackageInfo();
                break;
            case 4:
                ((ShortcutUser) obj)
                        .forAllPackageItems(new ShortcutService$$ExternalSyntheticLambda4(9));
                break;
            case 5:
                ((ShortcutPackageItem) obj).refreshPackageSignatureAndSave();
                break;
            case 6:
                ((ShortcutPackage) obj).rescanPackageIfNeeded(false, true);
                break;
            case 7:
                ((ShortcutLauncher) obj).ensurePackageInfo();
                break;
            case 8:
                ((ShortcutPackageItem) obj).verifyStates();
                break;
            case 9:
                ((ShortcutPackageItem) obj).waitForBitmapSaves();
                break;
            default:
                ((ShortcutUser) obj).cancelAllInFlightTasks();
                break;
        }
    }
}
