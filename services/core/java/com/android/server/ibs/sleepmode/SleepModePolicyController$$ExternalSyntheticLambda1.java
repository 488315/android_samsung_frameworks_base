package com.android.server.ibs.sleepmode;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.function.Consumer;

public final /* synthetic */ class SleepModePolicyController$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SleepModePolicyController.ActionEntry actionEntry =
                (SleepModePolicyController.ActionEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder(" do sleep mode restriction "),
                        actionEntry.tag,
                        "SleepModePolicyController");
                actionEntry.callBack.triggerAction();
                break;
            case 1:
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder(" cancel sleep mode restriction "),
                        actionEntry.tag,
                        "SleepModePolicyController");
                actionEntry.callBack.cancelAction();
                break;
            default:
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder(" cancel sleep mode restriction "),
                        actionEntry.tag,
                        "SleepModePolicyController");
                actionEntry.callBack.cancelAction();
                break;
        }
    }
}
