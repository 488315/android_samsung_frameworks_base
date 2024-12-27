package com.android.systemui.classifier;

import android.os.Build;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.classifier.BrightLineFalsingManager;
import com.android.systemui.classifier.FalsingClassifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class FalsingDataProvider$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FalsingDataProvider$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                long eventTime = ((MotionEvent) ((InputEvent) ((ArrayList) ((FalsingDataProvider) obj2).mRecentMotionEvents.mInputEvents).get(r8.size() - 1))).getEventTime();
                BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
                Collection collection = brightLineFalsingManager.mPriorResults;
                HistoryTracker historyTracker = brightLineFalsingManager.mHistoryTracker;
                FalsingDataProvider falsingDataProvider = brightLineFalsingManager.mDataProvider;
                if (collection == null) {
                    historyTracker.addResults(Collections.singleton(FalsingClassifier.Result.falsed(brightLineFalsingManager.mSingleTapClassifier.isTap(falsingDataProvider.getRecentMotionEvents(), 0.0d).mFalsed ? 0.7d : 0.8d, BrightLineFalsingManager.AnonymousClass3.class.getSimpleName(), "unclassified")), eventTime);
                    break;
                } else {
                    boolean anyMatch = collection.stream().anyMatch(new BrightLineFalsingManager$3$$ExternalSyntheticLambda0());
                    brightLineFalsingManager.mPriorResults.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(4));
                    if (Build.IS_ENG || Build.IS_USERDEBUG) {
                        ((ArrayDeque) BrightLineFalsingManager.RECENT_SWIPES).add(new BrightLineFalsingManager.DebugSwipeRecord(anyMatch, brightLineFalsingManager.mPriorInteractionType, (List) falsingDataProvider.getRecentMotionEvents().stream().map(new BrightLineFalsingManager$3$$ExternalSyntheticLambda2()).collect(Collectors.toList())));
                        while (true) {
                            ArrayDeque arrayDeque = (ArrayDeque) BrightLineFalsingManager.RECENT_SWIPES;
                            if (arrayDeque.size() > 40) {
                                arrayDeque.remove();
                            }
                        }
                    }
                    historyTracker.addResults(brightLineFalsingManager.mPriorResults, eventTime);
                    brightLineFalsingManager.mPriorResults = null;
                    brightLineFalsingManager.mPriorInteractionType = 7;
                    break;
                }
                break;
            default:
                ((FalsingClassifier$$ExternalSyntheticLambda0) obj).f$0.onTouchEvent((MotionEvent) obj2);
                break;
        }
    }
}
