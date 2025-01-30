package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingClassifier;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DoubleTapClassifier extends FalsingClassifier {
    public final float mDoubleTapSlop;
    public final long mDoubleTapTimeMs;
    public final SingleTapClassifier mSingleTapClassifier;

    public DoubleTapClassifier(FalsingDataProvider falsingDataProvider, SingleTapClassifier singleTapClassifier, float f, long j) {
        super(falsingDataProvider);
        this.mSingleTapClassifier = singleTapClassifier;
        this.mDoubleTapSlop = f;
        this.mDoubleTapTimeMs = j;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00e6  */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        boolean z;
        List recentMotionEvents = getRecentMotionEvents();
        List list = this.mDataProvider.mPriorMotionEvents;
        StringBuilder sb = new StringBuilder();
        if (list == null) {
            return falsed(0.0d, "Only one gesture recorded");
        }
        SingleTapClassifier singleTapClassifier = this.mSingleTapClassifier;
        FalsingClassifier.Result isTap = singleTapClassifier.isTap(list, 0.5d);
        if (isTap.mFalsed) {
            sb.append("First gesture is not a tap. ");
            sb.append(isTap.getReason());
        } else {
            FalsingClassifier.Result isTap2 = singleTapClassifier.isTap(recentMotionEvents, 0.5d);
            if (isTap2.mFalsed) {
                sb.append("Second gesture is not a tap. ");
                sb.append(isTap2.getReason());
            } else {
                z = true;
                MotionEvent motionEvent = (MotionEvent) list.get(list.size() - 1);
                MotionEvent motionEvent2 = (MotionEvent) recentMotionEvents.get(recentMotionEvents.size() - 1);
                long eventTime = motionEvent2.getEventTime() - motionEvent.getEventTime();
                if (eventTime > this.mDoubleTapTimeMs) {
                    sb.append("Time between taps too large: ");
                    sb.append(eventTime);
                    sb.append("ms");
                } else {
                    float abs = Math.abs(motionEvent.getX() - motionEvent2.getX());
                    float f = this.mDoubleTapSlop;
                    if (abs < f) {
                        if (Math.abs(motionEvent.getY() - motionEvent2.getY()) >= f) {
                            sb.append("Delta Y between taps too large:");
                            sb.append(Math.abs(motionEvent.getY() - motionEvent2.getY()));
                            sb.append(" vs ");
                            sb.append(f);
                        }
                        return z ? falsed(0.5d, sb.toString()) : FalsingClassifier.Result.passed(0.5d);
                    }
                    sb.append("Delta X between taps too large:");
                    sb.append(Math.abs(motionEvent.getX() - motionEvent2.getX()));
                    sb.append(" vs ");
                    sb.append(f);
                }
            }
        }
        z = false;
        if (z) {
        }
    }
}
