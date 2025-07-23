package com.android.wm.shell.compatui;

import com.android.wm.shell.common.DisplayLayout;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda19 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda19(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((List) obj2).add(Integer.valueOf(((CompatUIWindowManagerAbstract) obj).mTaskId));
                break;
            default:
                ((CompatUIWindowManagerAbstract) obj).updateDisplayLayout((DisplayLayout) obj2);
                break;
        }
    }
}
