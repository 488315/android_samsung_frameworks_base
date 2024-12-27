package com.android.systemui.classifier;

import android.view.KeyEvent;
import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingClassifier;

public final class FalsingCollectorNoOp implements FalsingCollector {
    @Override // com.android.systemui.classifier.FalsingCollector
    public final void avoidGesture() {
        FalsingCollectorImpl.logDebug("NOOP: avoidGesture");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void init() {
        FalsingCollectorImpl.logDebug("NOOP: init");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onA11yAction() {
        FalsingCollectorImpl.logDebug("NOOP: onA11yAction");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onBouncerHidden() {
        FalsingCollectorImpl.logDebug("NOOP: onBouncerHidden");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onKeyEvent(KeyEvent keyEvent) {
        FalsingCollectorImpl.logDebug("NOOP: onKeyEvent(" + KeyEvent.actionToString(keyEvent.getAction()));
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onMotionEventComplete() {
        FalsingCollectorImpl.logDebug("NOOP: onMotionEventComplete");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onScreenOff() {
        FalsingCollectorImpl.logDebug("NOOP: onScreenOff");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onScreenOnFromTouch() {
        FalsingCollectorImpl.logDebug("NOOP: onScreenOnFromTouch");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onScreenTurningOn() {
        FalsingCollectorImpl.logDebug("NOOP: onScreenTurningOn");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onSuccessfulUnlock() {
        FalsingCollectorImpl.logDebug("NOOP: onSuccessfulUnlock");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void onTouchEvent(MotionEvent motionEvent) {
        FalsingCollectorImpl.logDebug("NOOP: onTouchEvent(" + MotionEvent.actionToString(motionEvent.getActionMasked()) + ")");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void setShowingAod(boolean z) {
        FalsingCollectorImpl.logDebug("NOOP: setShowingAod(" + z + ")");
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public final void updateFalseConfidence(FalsingClassifier.Result result) {
        FalsingCollectorImpl.logDebug("NOOP: updateFalseConfidence(" + result.mFalsed + ")");
    }
}
