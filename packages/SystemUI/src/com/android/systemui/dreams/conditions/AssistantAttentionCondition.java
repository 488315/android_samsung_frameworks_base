package com.android.systemui.dreams.conditions;

import com.android.systemui.assist.AssistManager;
import com.android.systemui.shared.condition.Condition;
import java.util.ArrayList;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AssistantAttentionCondition extends Condition {
    public final AssistManager mAssistManager;
    public final AnonymousClass1 mVisualQueryAttentionListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.dreams.conditions.AssistantAttentionCondition$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public AssistantAttentionCondition(CoroutineScope coroutineScope, AssistManager assistManager) {
        super(coroutineScope);
        this.mVisualQueryAttentionListener = new AnonymousClass1();
        this.mAssistManager = assistManager;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void start() {
        AssistManager assistManager = this.mAssistManager;
        ArrayList arrayList = (ArrayList) assistManager.mVisualQueryAttentionListeners;
        AnonymousClass1 anonymousClass1 = this.mVisualQueryAttentionListener;
        if (arrayList.contains(anonymousClass1)) {
            return;
        }
        ((ArrayList) assistManager.mVisualQueryAttentionListeners).add(anonymousClass1);
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        ((ArrayList) this.mAssistManager.mVisualQueryAttentionListeners).remove(this.mVisualQueryAttentionListener);
    }
}
