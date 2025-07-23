package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

@Deprecated
/* loaded from: classes5.dex */
public class ZoomButton extends ImageButton implements View.OnLongClickListener {
    private boolean mIsInLongpress;
    private final Runnable mRunnable;
    private long mZoomSpeed;

    public ZoomButton(Context context) {
        this(context, null);
    }

    public ZoomButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ZoomButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRunnable = new Runnable() { // from class: android.widget.ZoomButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (ZoomButton.this.hasOnClickListeners() && ZoomButton.this.mIsInLongpress && ZoomButton.this.isEnabled()) {
                    ZoomButton.this.callOnClick();
                    ZoomButton zoomButton = ZoomButton.this;
                    zoomButton.postDelayed(this, zoomButton.mZoomSpeed);
                }
            }
        };
        this.mZoomSpeed = 1000L;
        setOnLongClickListener(this);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            this.mIsInLongpress = false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setZoomSpeed(long j) {
        this.mZoomSpeed = j;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        this.mIsInLongpress = true;
        post(this.mRunnable);
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.mIsInLongpress = false;
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (!z) {
            setPressed(false);
        }
        super.setEnabled(z);
    }

    @Override // android.view.View
    public boolean dispatchUnhandledMove(View view, int i) {
        clearFocus();
        return super.dispatchUnhandledMove(view, i);
    }

    @Override // android.widget.ImageButton, android.widget.ImageView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ZoomButton.class.getName();
    }
}
