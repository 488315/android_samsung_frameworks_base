package com.android.systemui.dextouchpad.activity;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import com.android.systemui.dextouchpad.manager.TouchpadOrientationManager;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadViewModel extends AndroidViewModel {
    public final ButtonWindowController mButtonWindowController;
    public boolean mIsTouchpadAutoRunShown;
    public boolean mIsTouchpadEnabled;
    public FloatingWindow mSpenRecognitionWindow;
    public final Lazy mSpenRecognitionWindowLazy;
    public long mSpenStartTime;
    public long mTouchpadStartTime;
    public final TouchpadViewMover mTouchpadViewMover;
    public final TouchpadWindow mTouchpadWindow;

    public TouchpadViewModel(Application application, TouchpadWindow touchpadWindow, ButtonWindowController buttonWindowController, Lazy lazy, TouchpadViewMover touchpadViewMover) {
        super(application);
        this.mIsTouchpadEnabled = false;
        this.mIsTouchpadAutoRunShown = false;
        this.mTouchpadStartTime = 0L;
        this.mSpenStartTime = 0L;
        this.mTouchpadWindow = touchpadWindow;
        this.mButtonWindowController = buttonWindowController;
        this.mSpenRecognitionWindowLazy = lazy;
        this.mTouchpadViewMover = touchpadViewMover;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        TouchpadOrientationManager touchpadOrientationManager;
        RotationButtonWindow rotationButtonWindow = this.mButtonWindowController.mRotationButtonWindow;
        if (rotationButtonWindow != null && (touchpadOrientationManager = rotationButtonWindow.mOrientationEventManager) != null) {
            Log.d("DexTouchpadOrientationManager", "clear()");
            touchpadOrientationManager.mSContextManager = null;
            touchpadOrientationManager.mOrientationListener = null;
            touchpadOrientationManager.mTouchpadOrientationEventListener = null;
        }
        TouchpadViewMover touchpadViewMover = this.mTouchpadViewMover;
        Handler handler = touchpadViewMover.mHandler;
        if (handler != null) {
            touchpadViewMover.mIsMoveStarted = false;
            handler.removeCallbacks(touchpadViewMover.mUpdatePositionRunnable);
            touchpadViewMover.mView = null;
        }
    }
}
