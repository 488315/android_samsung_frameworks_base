package com.android.systemui.tuner;

import com.android.systemui.tuner.LockscreenFragment;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class LockscreenFragment$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LockscreenFragment$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((LockscreenFragment) this.f$0).mTunerService.removeTunable((TunerService.Tunable) obj);
                break;
            default:
                LockscreenFragment.Adapter adapter = (LockscreenFragment.Adapter) this.f$0;
                LockscreenFragment.Item item = (LockscreenFragment.Item) obj;
                ArrayList arrayList = adapter.mItems;
                int indexOf = arrayList.indexOf(item);
                arrayList.remove(item);
                adapter.notifyItemRemoved(indexOf);
                break;
        }
    }
}
