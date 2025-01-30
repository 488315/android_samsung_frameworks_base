package androidx.picker.adapter.viewholder;

import java.util.Iterator;
import java.util.List;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class GridViewHolder$$ExternalSyntheticLambda0 implements DisposableHandle {
    public final /* synthetic */ List f$0;

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        Iterator it = this.f$0.iterator();
        while (it.hasNext()) {
            ((DisposableHandle) it.next()).dispose();
        }
    }
}
