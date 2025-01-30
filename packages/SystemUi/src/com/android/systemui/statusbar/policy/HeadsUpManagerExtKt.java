package com.android.systemui.statusbar.policy;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class HeadsUpManagerExtKt {
    public static final Flow getHeadsUpEvents(HeadsUpManager headsUpManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        HeadsUpManagerExtKt$headsUpEvents$1 headsUpManagerExtKt$headsUpEvents$1 = new HeadsUpManagerExtKt$headsUpEvents$1(headsUpManager, null);
        conflatedCallbackFlow.getClass();
        return ConflatedCallbackFlow.conflatedCallbackFlow(headsUpManagerExtKt$headsUpEvents$1);
    }
}
