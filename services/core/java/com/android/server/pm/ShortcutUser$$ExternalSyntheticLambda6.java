package com.android.server.pm;

import java.util.function.Consumer;

public final /* synthetic */ class ShortcutUser$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Consumer f$2;

    public /* synthetic */ ShortcutUser$$ExternalSyntheticLambda6(
            int i,
            String str,
            ShortcutUser$$ExternalSyntheticLambda0 shortcutUser$$ExternalSyntheticLambda0) {
        this.f$0 = i;
        this.f$1 = str;
        this.f$2 = shortcutUser$$ExternalSyntheticLambda0;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.f$0;
        String str = this.f$1;
        Consumer consumer = this.f$2;
        ShortcutPackageItem shortcutPackageItem = (ShortcutPackageItem) obj;
        if (shortcutPackageItem.mPackageUserId == i
                && shortcutPackageItem.mPackageName.equals(str)) {
            consumer.accept(shortcutPackageItem);
        }
    }
}
