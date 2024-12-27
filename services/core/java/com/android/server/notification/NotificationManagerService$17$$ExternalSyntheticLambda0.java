package com.android.server.notification;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$17$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationManagerService.AnonymousClass17 f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ NotificationManagerService$17$$ExternalSyntheticLambda0(
            NotificationManagerService.AnonymousClass17 anonymousClass17,
            String str,
            int i,
            int i2,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = anonymousClass17;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                NotificationManagerService.AnonymousClass17 anonymousClass17 = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                synchronized (anonymousClass17.this$0.mNotificationLock) {
                    anonymousClass17.removeFlagFromNotificationLocked(i, i2, 32768, str);
                }
                return;
            default:
                NotificationManagerService.AnonymousClass17 anonymousClass172 = this.f$0;
                String str2 = this.f$1;
                int i3 = this.f$2;
                int i4 = this.f$3;
                synchronized (anonymousClass172.this$0.mNotificationLock) {
                    anonymousClass172.removeFlagFromNotificationLocked(i3, i4, 64, str2);
                }
                return;
        }
    }
}
