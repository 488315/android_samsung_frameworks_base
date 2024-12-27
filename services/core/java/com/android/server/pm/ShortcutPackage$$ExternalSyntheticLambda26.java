package com.android.server.pm;

public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda26 implements Runnable {
    public final /* synthetic */ ShortcutPackage f$0;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda26(
            ShortcutPackage shortcutPackage) {
        this.f$0 = shortcutPackage;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ShortcutPackage shortcutPackage = this.f$0;
        shortcutPackage
                .fromAppSearch()
                .thenAccept(new ShortcutPackage$$ExternalSyntheticLambda10(1, shortcutPackage));
    }
}
