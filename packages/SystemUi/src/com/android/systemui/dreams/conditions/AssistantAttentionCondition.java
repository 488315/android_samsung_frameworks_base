package com.android.systemui.dreams.conditions;

import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVisualQueryDetectionAttentionListener;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.shared.condition.Condition;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AssistantAttentionCondition extends Condition {
    public final AssistUtils mAssistUtils;
    public final C12832 mCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public boolean mEnabled;
    public final C12821 mVisualQueryDetectionAttentionListener;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.dreams.conditions.AssistantAttentionCondition$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.dreams.conditions.AssistantAttentionCondition$2] */
    public AssistantAttentionCondition(CoroutineScope coroutineScope, DreamOverlayStateController dreamOverlayStateController, AssistUtils assistUtils) {
        super(coroutineScope);
        this.mVisualQueryDetectionAttentionListener = new IVisualQueryDetectionAttentionListener.Stub() { // from class: com.android.systemui.dreams.conditions.AssistantAttentionCondition.1
            public final void onAttentionGained() {
                AssistantAttentionCondition.this.updateCondition(true);
            }

            public final void onAttentionLost() {
                AssistantAttentionCondition.this.updateCondition(false);
            }
        };
        this.mCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.conditions.AssistantAttentionCondition.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                AssistantAttentionCondition assistantAttentionCondition = AssistantAttentionCondition.this;
                boolean containsState = assistantAttentionCondition.mDreamOverlayStateController.containsState(32);
                AssistUtils assistUtils2 = assistantAttentionCondition.mAssistUtils;
                if (containsState) {
                    if (assistantAttentionCondition.mEnabled) {
                        return;
                    }
                    assistantAttentionCondition.mEnabled = true;
                    assistUtils2.enableVisualQueryDetection(assistantAttentionCondition.mVisualQueryDetectionAttentionListener);
                    return;
                }
                if (assistantAttentionCondition.mEnabled) {
                    assistantAttentionCondition.mEnabled = false;
                    assistUtils2.disableVisualQueryDetection();
                    assistantAttentionCondition.updateCondition(false);
                }
            }
        };
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mAssistUtils = assistUtils;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void start() {
        this.mDreamOverlayStateController.addCallback((DreamOverlayStateController.Callback) this.mCallback);
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        if (this.mEnabled) {
            this.mEnabled = false;
            this.mAssistUtils.disableVisualQueryDetection();
            updateCondition(false);
        }
        this.mDreamOverlayStateController.removeCallback((DreamOverlayStateController.Callback) this.mCallback);
    }
}
