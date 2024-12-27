package com.android.server.ibs.sleepmode;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ibs.sleepmode.SleepModePolicyController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SleepModePolicyController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SleepModePolicyController.ActionEntry actionEntry = (SleepModePolicyController.ActionEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder(" do sleep mode restriction "), actionEntry.tag, "SleepModePolicyController");
                actionEntry.callBack.triggerAction();
                break;
            case 1:
                BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder(" cancel sleep mode restriction "), actionEntry.tag, "SleepModePolicyController");
                actionEntry.callBack.cancelAction();
                break;
            default:
                BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder(" cancel sleep mode restriction "), actionEntry.tag, "SleepModePolicyController");
                actionEntry.callBack.cancelAction();
                break;
        }
    }
}
