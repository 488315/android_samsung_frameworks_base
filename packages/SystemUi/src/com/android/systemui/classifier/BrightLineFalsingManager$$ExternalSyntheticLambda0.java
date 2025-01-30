package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.plugins.FalsingManager;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
                ((FalsingClassifier) obj).onSessionStarted();
                break;
            case 4:
                ((FalsingManager.FalsingBeliefListener) obj).onFalse();
                break;
            default:
                FalsingClassifier.Result result = (FalsingClassifier.Result) obj;
                if (result.mFalsed && result.getReason() != null) {
                    boolean z = BrightLineFalsingManager.DEBUG;
                    break;
                }
                break;
        }
    }
}
