package com.android.systemui.classifier;

import android.view.KeyEvent;
import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingClassifier;

public interface FalsingCollector {
    void avoidGesture();

    void init();

    void onA11yAction();

    void onBouncerHidden();

    void onKeyEvent(KeyEvent keyEvent);

    void onMotionEventComplete();

    void onScreenOff();

    void onScreenOnFromTouch();

    void onScreenTurningOn();

    void onSuccessfulUnlock();

    void onTouchEvent(MotionEvent motionEvent);

    void setShowingAod(boolean z);

    void updateFalseConfidence(FalsingClassifier.Result result);
}
