package com.android.server.location.provider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AbstractLocationProvider$Controller$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AbstractLocationProvider f$0;

    public /* synthetic */ AbstractLocationProvider$Controller$$ExternalSyntheticLambda0(AbstractLocationProvider abstractLocationProvider, int i) {
        this.$r8$classId = i;
        this.f$0 = abstractLocationProvider;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AbstractLocationProvider abstractLocationProvider = this.f$0;
        switch (i) {
            case 0:
                abstractLocationProvider.onStart();
                break;
            default:
                abstractLocationProvider.onStop();
                break;
        }
    }
}
