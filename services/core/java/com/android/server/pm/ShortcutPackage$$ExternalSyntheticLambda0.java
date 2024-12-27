package com.android.server.pm;

import android.content.pm.ShortcutInfo;
import com.android.internal.infra.AndroidFuture;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ShortcutPackage f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda0(ShortcutPackage shortcutPackage, ShortcutInfo shortcutInfo) {
        this.f$0 = shortcutPackage;
        this.f$1 = shortcutInfo;
    }

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda0(ShortcutPackage shortcutPackage, AndroidFuture androidFuture) {
        this.f$0 = shortcutPackage;
        this.f$1 = androidFuture;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPackage shortcutPackage = this.f$0;
                shortcutPackage.fromAppSearch().thenAccept(new ShortcutPackage$$ExternalSyntheticLambda29(shortcutPackage, (AndroidFuture) this.f$1, 0));
                break;
            default:
                ShortcutPackage shortcutPackage2 = this.f$0;
                shortcutPackage2.fromAppSearch().thenAccept(new ShortcutPackage$$ExternalSyntheticLambda23(1, shortcutPackage2, (ShortcutInfo) this.f$1));
                break;
        }
    }
}
