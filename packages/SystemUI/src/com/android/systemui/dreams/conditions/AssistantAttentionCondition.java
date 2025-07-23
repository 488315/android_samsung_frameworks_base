package com.android.systemui.dreams.conditions;

import com.android.systemui.assist.AssistManager;
import com.android.systemui.shared.condition.Condition;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AssistantAttentionCondition extends Condition {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AssistManager assistManager;
    public final AssistantAttentionCondition$visualQueryAttentionListener$1 visualQueryAttentionListener;

    public AssistantAttentionCondition(CoroutineScope coroutineScope, AssistManager assistManager) {
        super(coroutineScope, null, false, 6, null);
        this.assistManager = assistManager;
        this.visualQueryAttentionListener = new AssistantAttentionCondition$visualQueryAttentionListener$1(this);
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return 0;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        AssistManager assistManager = this.assistManager;
        ArrayList arrayList = (ArrayList) assistManager.mVisualQueryAttentionListeners;
        AssistantAttentionCondition$visualQueryAttentionListener$1 assistantAttentionCondition$visualQueryAttentionListener$1 = this.visualQueryAttentionListener;
        if (!arrayList.contains(assistantAttentionCondition$visualQueryAttentionListener$1)) {
            ((ArrayList) assistManager.mVisualQueryAttentionListeners).add(assistantAttentionCondition$visualQueryAttentionListener$1);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        ((ArrayList) this.assistManager.mVisualQueryAttentionListeners).remove(this.visualQueryAttentionListener);
    }
}
