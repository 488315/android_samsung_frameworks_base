package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.plugins.FalsingManager;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class BrightLineFalsingManager$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BrightLineFalsingManager$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((FalsingManager.FalsingTapListener) obj).onAdditionalTapRequired();
                break;
            case 1:
                FalsingClassifier falsingClassifier = (FalsingClassifier) obj;
                ((ArrayList) falsingClassifier.mDataProvider.mMotionEventListeners).remove(falsingClassifier.mMotionEventListener);
                break;
            case 2:
                ((FalsingClassifier) obj).onSessionEnded();
                break;
            case 3:
                ((FalsingManager.FalsingBeliefListener) obj).onFalse();
                break;
            default:
                FalsingClassifier.Result result = (FalsingClassifier.Result) obj;
                if (result.mFalsed) {
                    result.getReason();
                    boolean z = BrightLineFalsingManager.DEBUG;
                    break;
                }
                break;
        }
    }
}
