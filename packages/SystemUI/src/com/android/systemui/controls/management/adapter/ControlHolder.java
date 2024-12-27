package com.android.systemui.controls.management.adapter;

import android.view.View;
import com.android.systemui.controls.ui.ControlViewHolder;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlHolder extends Holder {
    public final ControlViewHolder controlViewHolder;
    public final Map holders;

    public ControlHolder(View view, ControlViewHolder controlViewHolder, Map<String, ControlViewHolder> map) {
        super(view, null);
        this.controlViewHolder = controlViewHolder;
        this.holders = map;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x008c, code lost:
    
        if ((r3 instanceof android.service.controls.templates.StatelessTemplate) == false) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.systemui.controls.ui.SecBehavior] */
    /* JADX WARN: Type inference failed for: r3v33 */
    @Override // com.android.systemui.controls.management.adapter.Holder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bindData(com.android.systemui.controls.management.model.MainModel r9) {
        /*
            Method dump skipped, instructions count: 433
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.management.adapter.ControlHolder.bindData(com.android.systemui.controls.management.model.MainModel):void");
    }
}
