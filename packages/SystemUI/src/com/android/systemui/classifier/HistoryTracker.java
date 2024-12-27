package com.android.systemui.classifier;

import com.android.systemui.classifier.BrightLineFalsingManager;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class HistoryTracker {
    public static final double HISTORY_DECAY = Math.pow(10.0d, (Math.log10(0.1d) / 10000.0d) * 100.0d);
    public final SystemClock mSystemClock;
    public final DelayQueue mResults = new DelayQueue();
    public final List mBeliefListeners = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CombinedResult implements Delayed {
        public final long mExpiryMs;
        public final double mScore;

        public CombinedResult(long j, double d) {
            this.mExpiryMs = j + 10000;
            this.mScore = d;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Delayed delayed) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            return Long.compare(getDelay(timeUnit), delayed.getDelay(timeUnit));
        }

        @Override // java.util.concurrent.Delayed
        public final long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.mExpiryMs - HistoryTracker.this.mSystemClock.uptimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public HistoryTracker(SystemClock systemClock) {
        this.mSystemClock = systemClock;
    }

    public final void addResults(Collection collection, long j) {
        Iterator it = collection.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            FalsingClassifier.Result result = (FalsingClassifier.Result) it.next();
            d += ((result.mFalsed ? 0.5d : -0.5d) * result.mConfidence) + 0.5d;
        }
        double size = d / collection.size();
        if (size == 1.0d) {
            size = 0.99999d;
        } else if (size == 0.0d) {
            size = 1.0E-5d;
        }
        double d2 = size;
        while (this.mResults.poll() != null) {
        }
        this.mResults.add((DelayQueue) new CombinedResult(j, d2));
        ((ArrayList) this.mBeliefListeners).forEach(new Consumer() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                double falseBelief = HistoryTracker.this.falseBelief();
                BrightLineFalsingManager brightLineFalsingManager = ((BrightLineFalsingManager.AnonymousClass2) obj).this$0;
                brightLineFalsingManager.mHistoryTracker.falseBelief();
                brightLineFalsingManager.mHistoryTracker.falseConfidence();
                boolean z = BrightLineFalsingManager.DEBUG;
                if (falseBelief > 0.9d) {
                    ((ArrayList) brightLineFalsingManager.mFalsingBeliefListeners).forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(3));
                }
            }
        });
    }

    public final double falseBelief() {
        while (this.mResults.poll() != null) {
        }
        if (this.mResults.isEmpty()) {
            return 0.5d;
        }
        final long uptimeMillis = this.mSystemClock.uptimeMillis();
        return ((Double) this.mResults.stream().map(new Function() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf((Math.pow(HistoryTracker.HISTORY_DECAY, (10000 - (r7.mExpiryMs - uptimeMillis)) / 100.0d) * (((HistoryTracker.CombinedResult) obj).mScore - 0.5d)) + 0.5d);
            }
        }).reduce(Double.valueOf(0.5d), new HistoryTracker$$ExternalSyntheticLambda1(1))).doubleValue();
    }

    public final double falseConfidence() {
        while (this.mResults.poll() != null) {
        }
        if (this.mResults.isEmpty()) {
            return 0.0d;
        }
        final double doubleValue = ((Double) this.mResults.stream().map(new HistoryTracker$$ExternalSyntheticLambda0()).reduce(Double.valueOf(0.0d), new HistoryTracker$$ExternalSyntheticLambda1(0))).doubleValue() / this.mResults.size();
        return 1.0d - Math.sqrt(((Double) this.mResults.stream().map(new Function() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(Math.pow(((HistoryTracker.CombinedResult) obj).mScore - doubleValue, 2.0d));
            }
        }).reduce(Double.valueOf(0.0d), new HistoryTracker$$ExternalSyntheticLambda1(0))).doubleValue() / this.mResults.size());
    }
}
