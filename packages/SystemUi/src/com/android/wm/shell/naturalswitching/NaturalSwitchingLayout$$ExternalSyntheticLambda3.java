package com.android.wm.shell.naturalswitching;

import android.view.SurfaceControl;
import android.window.TaskAppearedInfo;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NaturalSwitchingLayout$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NaturalSwitchingLayout$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SurfaceControl.Transaction) this.f$0).setAlpha(((TaskAppearedInfo) obj).getLeash(), 1.0f);
                break;
            default:
                ((NaturalSwitchingLayout) this.f$0).hide(((Boolean) obj).booleanValue());
                break;
        }
    }
}
