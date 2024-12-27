package com.android.systemui.classifier;

import android.view.KeyEvent;
import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingClassifier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
