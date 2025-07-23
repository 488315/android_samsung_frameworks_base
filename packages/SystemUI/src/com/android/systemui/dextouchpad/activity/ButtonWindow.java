package com.android.systemui.dextouchpad.activity;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.Toast;
import com.android.systemui.dextouchpad.data.TouchpadButtonItems;
import com.android.systemui.dextouchpad.util.Features;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ButtonWindow extends FloatingWindow {
    public final AlphaAnimation mAlphaAnimation;
    public final int mButtonId;
    public ImageButton mImageButton;
    public OnGestureListener mOnGestureListener;
    public final AtomicBoolean mSpenNotSupportedToastBlocked;
    public final int mToastResId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnGestureListener {
        void onDoubleTap();
    }

    public ButtonWindow(TouchpadButtonItems touchpadButtonItems, AtomicBoolean atomicBoolean) {
        super(touchpadButtonItems.getType(), touchpadButtonItems.getTitle());
        this.mButtonId = touchpadButtonItems.getButtonId();
        this.mToastResId = touchpadButtonItems.getToastResId();
        this.mSpenNotSupportedToastBlocked = atomicBoolean;
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        this.mAlphaAnimation = alphaAnimation;
        alphaAnimation.setDuration(5000L);
        this.mAlphaAnimation.setRepeatMode(2);
        this.mAlphaAnimation.setRepeatCount(-1);
    }

    @Override // com.android.systemui.dextouchpad.activity.FloatingWindow, com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public void onStartSetup() {
        super.onStartSetup();
        ImageButton imageButton = (ImageButton) this.mActivity.findViewById(this.mButtonId);
        this.mImageButton = imageButton;
        imageButton.setFocusable(false);
        startAnimation(true);
        this.mWindowView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.dextouchpad.activity.ButtonWindow.1
            public final GestureDetector mGestureDetector;

            {
                this.mGestureDetector = new GestureDetector(ButtonWindow.this.mActivity, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.dextouchpad.activity.ButtonWindow.1.1
                    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
                    public final boolean onDoubleTap(MotionEvent motionEvent) {
                        ButtonWindow.this.mSpenNotSupportedToastBlocked.set(false);
                        ButtonWindow.this.mOnGestureListener.onDoubleTap();
                        return true;
                    }

                    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
                    public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                        final ButtonWindow buttonWindow = ButtonWindow.this;
                        final Toast makeText = Toast.makeText(buttonWindow.mActivity, buttonWindow.mToastResId, 1);
                        makeText.addCallback(new Toast.Callback() { // from class: com.android.systemui.dextouchpad.activity.ButtonWindow.2
                            @Override // android.widget.Toast.Callback
                            public final void onToastHidden() {
                                ButtonWindow.this.mSpenNotSupportedToastBlocked.set(false);
                                makeText.removeCallback(this);
                            }
                        });
                        makeText.show();
                        if (Features.DEBUG) {
                            Log.d("DexTouchpadButtonWindow", "Button clicked by Single Tap.");
                        }
                        return true;
                    }

                    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                    public final boolean onSingleTapUp(MotionEvent motionEvent) {
                        ButtonWindow.this.mSpenNotSupportedToastBlocked.set(true);
                        return false;
                    }
                });
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                ImageButton imageButton2 = ButtonWindow.this.mImageButton;
                if (imageButton2 != null) {
                    imageButton2.dispatchTouchEvent(motionEvent);
                }
                return this.mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    @Override // com.android.systemui.dextouchpad.activity.FloatingWindow, com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public void onStartTearDown() {
        this.mWindowView.setOnTouchListener(null);
        this.mOnGestureListener = null;
        super.onStartTearDown();
        this.mImageButton = null;
    }

    public final void startAnimation(boolean z) {
        ImageButton imageButton = this.mImageButton;
        if (imageButton == null) {
            return;
        }
        if (z) {
            imageButton.startAnimation(this.mAlphaAnimation);
        } else {
            imageButton.clearAnimation();
        }
    }
}
