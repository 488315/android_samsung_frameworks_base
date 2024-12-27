package com.android.server.wm;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityTaskManagerService f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda2(
            ActivityTaskManagerService activityTaskManagerService, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = activityTaskManagerService;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ActivityTaskManagerService activityTaskManagerService = this.f$0;
                boolean z = this.f$1;
                for (int size = activityTaskManagerService.mScreenObservers.size() - 1;
                        size >= 0;
                        size--) {
                    ((ActivityTaskManagerInternal.ScreenObserver)
                                    activityTaskManagerService.mScreenObservers.get(size))
                            .onKeyguardStateChanged(z);
                }
                break;
            default:
                ActivityTaskManagerService activityTaskManagerService2 = this.f$0;
                boolean z2 = this.f$1;
                for (int size2 = activityTaskManagerService2.mScreenObservers.size() - 1;
                        size2 >= 0;
                        size2--) {
                    ((ActivityTaskManagerInternal.ScreenObserver)
                                    activityTaskManagerService2.mScreenObservers.get(size2))
                            .onAwakeStateChanged(z2);
                }
                break;
        }
    }
}
