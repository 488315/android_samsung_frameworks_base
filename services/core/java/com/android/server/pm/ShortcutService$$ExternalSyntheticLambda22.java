package com.android.server.pm;

import android.content.pm.ApplicationInfo;
import android.content.pm.ShortcutInfo;
import com.android.internal.infra.AndroidFuture;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutService$$ExternalSyntheticLambda22 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda22(int i, List list, AndroidFuture androidFuture) {
        this.f$2 = i;
        this.f$0 = list;
        this.f$1 = androidFuture;
    }

    public /* synthetic */ ShortcutService$$ExternalSyntheticLambda22(ShortcutService shortcutService, ShortcutUser shortcutUser, int i) {
        this.f$0 = shortcutService;
        this.f$1 = shortcutUser;
        this.f$2 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutService shortcutService = (ShortcutService) this.f$0;
                ShortcutUser shortcutUser = (ShortcutUser) this.f$1;
                int i = this.f$2;
                ApplicationInfo applicationInfo = (ApplicationInfo) obj;
                shortcutService.getClass();
                String str = applicationInfo.packageName;
                shortcutUser.getClass();
                shortcutUser.forAllPackageItems(new ShortcutUser$$ExternalSyntheticLambda6(i, str, new ShortcutUser$$ExternalSyntheticLambda0(1)));
                shortcutUser.rescanPackageIfNeeded(applicationInfo.packageName, true);
                break;
            default:
                final int i2 = this.f$2;
                List list = (List) this.f$0;
                AndroidFuture androidFuture = (AndroidFuture) this.f$1;
                List list2 = (List) obj;
                if (list2 != null) {
                    Stream map = list2.stream().map(new Function() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            return ((ShortcutInfo) obj2).clone(i2);
                        }
                    });
                    Objects.requireNonNull(list);
                    map.forEach(new ShortcutPackage$$ExternalSyntheticLambda13(0, list));
                }
                androidFuture.complete(list);
                break;
        }
    }
}
