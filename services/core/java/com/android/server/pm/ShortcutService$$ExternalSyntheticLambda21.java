package com.android.server.pm;

import android.content.IntentFilter;
import android.content.pm.ShortcutInfo;

import com.android.internal.infra.AndroidFuture;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda21 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda21(
            ShortcutService.LocalService localService,
            AndroidFuture androidFuture,
            ShortcutPackage shortcutPackage) {
        this.f$0 = localService;
        this.f$1 = androidFuture;
        this.f$2 = shortcutPackage;
    }

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda21(
            List list, IntentFilter intentFilter, String str) {
        this.f$0 = list;
        this.f$1 = intentFilter;
        this.f$2 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((List) this.f$0)
                        .addAll(
                                ((ShortcutPackage) obj)
                                        .getMatchingShareTargets(
                                                (IntentFilter) this.f$1, (String) this.f$2));
                break;
            default:
                ShortcutService.LocalService localService = (ShortcutService.LocalService) this.f$0;
                AndroidFuture androidFuture = (AndroidFuture) this.f$1;
                localService.getClass();
                androidFuture.complete(
                        ShortcutService.LocalService.getShortcutIconParcelFileDescriptor(
                                (ShortcutPackage) this.f$2, (ShortcutInfo) obj));
                break;
        }
    }
}
