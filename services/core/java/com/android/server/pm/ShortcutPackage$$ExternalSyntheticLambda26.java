package com.android.server.pm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
