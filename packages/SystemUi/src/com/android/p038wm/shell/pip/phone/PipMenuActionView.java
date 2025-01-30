package com.android.p038wm.shell.pip.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PipMenuActionView extends FrameLayout {
    public View mCustomCloseBackground;
    public ImageView mImageView;

    public PipMenuActionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
            Log.d("PipMenuActionView", "dispatchTouchEvent action=" + motionEvent.getAction());
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (ImageView) findViewById(R.id.image);
        this.mCustomCloseBackground = findViewById(R.id.custom_close_bg);
    }
}
