package com.android.wm.shell.naturalswitching;

import android.view.SurfaceControl;
import android.window.TaskAppearedInfo;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class NaturalSwitchingLayout$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NaturalSwitchingLayout$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                boolean z = NaturalSwitchingLayout.DEBUG_DEV;
                ((SurfaceControl.Transaction) obj2).setAlpha(((TaskAppearedInfo) obj).getLeash(), 1.0f);
                break;
            default:
                ((NaturalSwitchingLayout) obj2).hide(((Boolean) obj).booleanValue());
                break;
        }
    }
}
