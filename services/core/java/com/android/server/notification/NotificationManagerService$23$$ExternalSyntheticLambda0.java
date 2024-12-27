package com.android.server.notification;

import com.android.server.notification.NotificationManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$23$$ExternalSyntheticLambda0 implements NotificationManagerService.FlagChecker {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ NotificationManagerService$23$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = 0;
        this.f$0 = 0;
        this.f$1 = i;
    }

    public /* synthetic */ NotificationManagerService$23$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = 1;
        this.f$0 = i;
        this.f$1 = i2;
    }

    @Override // com.android.server.notification.NotificationManagerService.FlagChecker
    public final boolean apply(int i) {
        switch (this.$r8$classId) {
            case 0:
                int i2 = this.f$0;
                if ((i & i2) == i2 && (this.f$1 & i) == 0) {
                }
                break;
            default:
                int i3 = this.f$0;
                int i4 = this.f$1;
                if (11 == i4 || 3 == i4) {
                    i3 |= 4096;
                }
                if ((i & i3) != 0) {
                }
                break;
        }
        return false;
    }
}
