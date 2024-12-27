package com.android.server.app;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GameManagerService$$ExternalSyntheticLambda3
        implements Predicate {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GameManagerService$$ExternalSyntheticLambda3(
            GameManagerService.MyUidObserver myUidObserver, int i) {
        this.f$0 = myUidObserver;
        this.f$1 = i;
    }

    public /* synthetic */ GameManagerService$$ExternalSyntheticLambda3(
            GameManagerService gameManagerService, int i) {
        this.f$0 = gameManagerService;
        this.f$1 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = GameManagerService.$r8$clinit;
                return ((GameManagerService) this.f$0).isPackageGame(this.f$1, (String) obj);
            default:
                GameManagerService gameManagerService =
                        ((GameManagerService.MyUidObserver) this.f$0).this$0;
                int i2 = GameManagerService.$r8$clinit;
                return gameManagerService.isPackageGame(this.f$1, (String) obj);
        }
    }
}
