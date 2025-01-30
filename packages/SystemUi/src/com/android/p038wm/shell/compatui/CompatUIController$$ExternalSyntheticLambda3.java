package com.android.p038wm.shell.compatui;

import com.android.p038wm.shell.common.DisplayLayout;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((CompatUIWindowManagerAbstract) obj).updateDisplayLayout((DisplayLayout) this.f$0);
                break;
            default:
                ((List) this.f$0).add(Integer.valueOf(((CompatUIWindowManagerAbstract) obj).mTaskId));
                break;
        }
    }
}
