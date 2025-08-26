package com.android.systemui.classifier;

import android.view.MotionEvent;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.classifier.FalsingClassifier;
import java.util.List;

/* loaded from: classes.dex */
public class DoubleTapClassifier extends FalsingClassifier {
    public final float mDoubleTapSlop;
    public final long mDoubleTapTimeMs;
    public final SingleTapClassifier mSingleTapClassifier;

    public DoubleTapClassifier(FalsingDataProvider falsingDataProvider, SingleTapClassifier singleTapClassifier, float f, long j) {
        super(falsingDataProvider);
        this.mSingleTapClassifier = singleTapClassifier;
        this.mDoubleTapSlop = f;
        this.mDoubleTapTimeMs = j;
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        List recentMotionEvents = falsingDataProvider.getRecentMotionEvents();
        List list = falsingDataProvider.mPriorMotionEvents;
        StringBuilder sb = new StringBuilder();
        if (list == null) {
            return falsed(0.0d, "Only one gesture recorded");
        }
        SingleTapClassifier singleTapClassifier = this.mSingleTapClassifier;
        FalsingClassifier.Result resultIsTap = singleTapClassifier.isTap(list, 0.5d);
        if (resultIsTap.mFalsed) {
            sb.append("First gesture is not a tap. ");
            sb.append(resultIsTap.getReason());
        } else {
            FalsingClassifier.Result resultIsTap2 = singleTapClassifier.isTap(recentMotionEvents, 0.5d);
            if (resultIsTap2.mFalsed) {
                sb.append("Second gesture is not a tap. ");
                sb.append(resultIsTap2.getReason());
            } else {
                MotionEvent motionEvent = (MotionEvent) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, list);
                MotionEvent motionEvent2 = (MotionEvent) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, recentMotionEvents);
                long eventTime = motionEvent2.getEventTime() - motionEvent.getEventTime();
                if (eventTime > this.mDoubleTapTimeMs) {
                    sb.append("Time between taps too large: ");
                    sb.append(eventTime);
                    sb.append("ms");
                } else {
                    float fAbs = Math.abs(motionEvent.getX() - motionEvent2.getX());
                    float f = this.mDoubleTapSlop;
                    if (fAbs >= f) {
                        sb.append("Delta X between taps too large:");
                        sb.append(Math.abs(motionEvent.getX() - motionEvent2.getX()));
                        sb.append(" vs ");
                        sb.append(f);
                    } else {
                        if (Math.abs(motionEvent.getY() - motionEvent2.getY()) < f) {
                            return FalsingClassifier.Result.passed(0.5d);
                        }
                        sb.append("Delta Y between taps too large:");
                        sb.append(Math.abs(motionEvent.getY() - motionEvent2.getY()));
                        sb.append(" vs ");
                        sb.append(f);
                    }
                }
            }
        }
        return falsed(0.5d, sb.toString());
    }
}
