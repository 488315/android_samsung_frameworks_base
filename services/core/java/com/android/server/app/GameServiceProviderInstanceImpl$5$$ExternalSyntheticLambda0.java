package com.android.server.app;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GameServiceProviderInstanceImpl.AnonymousClass5 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda0(
            GameServiceProviderInstanceImpl.AnonymousClass5 anonymousClass5, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass5;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GameServiceProviderInstanceImpl.AnonymousClass5 anonymousClass5 = this.f$0;
                int i = this.f$1;
                GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                        anonymousClass5.this$0;
                synchronized (gameServiceProviderInstanceImpl.mLock) {
                    gameServiceProviderInstanceImpl.onForegroundActivitiesChangedLocked(i);
                }
                return;
            default:
                GameServiceProviderInstanceImpl.AnonymousClass5 anonymousClass52 = this.f$0;
                int i2 = this.f$1;
                GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl2 =
                        anonymousClass52.this$0;
                synchronized (gameServiceProviderInstanceImpl2.mLock) {
                    gameServiceProviderInstanceImpl2.onProcessDiedLocked(i2);
                }
                return;
        }
    }
}
