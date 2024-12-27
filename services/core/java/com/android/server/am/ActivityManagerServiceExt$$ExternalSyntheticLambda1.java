package com.android.server.am;

import android.os.RemoteCallback;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerServiceExt$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityManagerServiceExt f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ ProcessRecord f$2;

    public /* synthetic */ ActivityManagerServiceExt$$ExternalSyntheticLambda1(ActivityManagerServiceExt activityManagerServiceExt, String str, ProcessRecord processRecord, int i) {
        this.$r8$classId = i;
        this.f$0 = activityManagerServiceExt;
        this.f$1 = str;
        this.f$2 = processRecord;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityManagerServiceExt activityManagerServiceExt = this.f$0;
                String str = this.f$1;
                ProcessRecord processRecord = this.f$2;
                activityManagerServiceExt.getClass();
                ActivityManagerServiceExt.updateNeverKill((RemoteCallback) obj, true, str, processRecord.mPid);
                break;
            default:
                ActivityManagerServiceExt activityManagerServiceExt2 = this.f$0;
                String str2 = this.f$1;
                ProcessRecord processRecord2 = this.f$2;
                activityManagerServiceExt2.getClass();
                ActivityManagerServiceExt.updateNeverKill((RemoteCallback) obj, true, str2, processRecord2.mPid);
                break;
        }
    }
}
