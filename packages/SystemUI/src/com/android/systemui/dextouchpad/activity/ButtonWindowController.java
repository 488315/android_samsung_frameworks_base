package com.android.systemui.dextouchpad.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import androidx.fragment.app.FragmentActivity;
import com.android.systemui.dextouchpad.data.TouchpadGesturesGuideItems;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ButtonWindowController {
    public TouchpadGesturesGuideItems itemList;
    public FragmentActivity mActivity;
    public ButtonWindow mCloseButtonWindow;
    public Context mContext;
    public ButtonWindow mGuideButtonWindow;
    public boolean mNightMode;
    public RotationButtonWindow mRotationButtonWindow;
    public final AtomicBoolean mSpenNotSupportedToastBlocked;

    public ButtonWindowController(AtomicBoolean atomicBoolean) {
        this.mSpenNotSupportedToastBlocked = atomicBoolean;
    }

    public final void setRotationButtonVisibility(boolean z) {
        RotationButtonWindow rotationButtonWindow = this.mRotationButtonWindow;
        if (rotationButtonWindow != null) {
            rotationButtonWindow.startAnimation(z);
            RotationButtonWindow rotationButtonWindow2 = this.mRotationButtonWindow;
            ImageButton imageButton = rotationButtonWindow2.mImageButton;
            if (imageButton != null) {
                imageButton.setVisibility(z ? 0 : 8);
            }
            View view = rotationButtonWindow2.mWindowView;
            if (view != null) {
                view.setVisibility(z ? 0 : 8);
            }
        }
        ButtonWindow buttonWindow = this.mGuideButtonWindow;
        if (buttonWindow != null) {
            buttonWindow.startAnimation(true);
        }
        ButtonWindow buttonWindow2 = this.mCloseButtonWindow;
        if (buttonWindow2 != null) {
            buttonWindow2.startAnimation(true);
        }
    }
}
