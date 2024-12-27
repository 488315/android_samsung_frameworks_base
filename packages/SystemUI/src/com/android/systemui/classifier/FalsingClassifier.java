package com.android.systemui.classifier;

import android.view.MotionEvent;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.FalsingManager;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class FalsingClassifier {
    public final FalsingDataProvider mDataProvider;
    public final FalsingClassifier$$ExternalSyntheticLambda0 mMotionEventListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Result {
        public final double mConfidence;
        public final String mContext;
        public final boolean mFalsed;
        public final String mReason;

        private Result(boolean z, double d, String str, String str2) {
            this.mFalsed = z;
            this.mConfidence = d;
            this.mContext = str;
            this.mReason = str2;
        }

        public static Result falsed(double d, String str, String str2) {
            return new Result(true, d, str, str2);
        }

        public static Result passed(double d) {
            return new Result(false, d, null, null);
        }

        public final String getReason() {
            StringBuilder sb = new StringBuilder("{context=");
            sb.append(this.mContext);
            sb.append(" reason=");
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.mReason, "}");
        }
    }

    public FalsingClassifier(FalsingDataProvider falsingDataProvider) {
        FalsingClassifier$$ExternalSyntheticLambda0 falsingClassifier$$ExternalSyntheticLambda0 = new FalsingClassifier$$ExternalSyntheticLambda0(this);
        this.mMotionEventListener = falsingClassifier$$ExternalSyntheticLambda0;
        this.mDataProvider = falsingDataProvider;
        ((ArrayList) falsingDataProvider.mMotionEventListeners).add(falsingClassifier$$ExternalSyntheticLambda0);
    }

    public abstract Result calculateFalsingResult(int i);

    public final Result falsed(double d, String str) {
        return Result.falsed(d, getClass().getSimpleName(), str);
    }

    public final boolean isRight() {
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        falsingDataProvider.recalculateData();
        return !falsingDataProvider.mRecentMotionEvents.isEmpty() && falsingDataProvider.mLastMotionEvent.getX() > falsingDataProvider.mFirstRecentMotionEvent.getX();
    }

    public final boolean isUp() {
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        falsingDataProvider.recalculateData();
        return !falsingDataProvider.mRecentMotionEvents.isEmpty() && falsingDataProvider.mLastMotionEvent.getY() < falsingDataProvider.mFirstRecentMotionEvent.getY();
    }

    public void onSessionEnded() {
    }

    public void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
    }

    public void onTouchEvent(MotionEvent motionEvent) {
    }
}
