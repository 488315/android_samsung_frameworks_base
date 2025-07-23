package com.android.systemui.classifier;

import android.view.KeyEvent;
import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingClassifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
