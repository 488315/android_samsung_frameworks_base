package android.inputmethodservice.navigationbar;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.FloatProperty;
import android.util.Log;
import android.view.MotionEvent;

/* loaded from: classes2.dex */
final class DeadZone {
    private static final boolean CHATTY = true;
    public static final boolean DEBUG = false;
    private static final FloatProperty<DeadZone> FLASH_PROPERTY = new FloatProperty<DeadZone>("DeadZoneFlash") { // from class: android.inputmethodservice.navigationbar.DeadZone.1
        @Override // android.util.FloatProperty
        public void setValue(DeadZone deadZone, float f) {
            deadZone.setFlash(f);
        }

        @Override // android.util.Property
        public Float get(DeadZone deadZone) {
            return Float.valueOf(deadZone.getFlash());
        }
    };
    public static final int HORIZONTAL = 0;
    public static final String TAG = "DeadZone";
    public static final int VERTICAL = 1;
    private int mDecay;
    private int mDisplayRotation;
    private int mHold;
    private long mLastPokeTime;
    private final NavigationBarView mNavigationBarView;
    private boolean mShouldFlash;
    private int mSizeMax;
    private int mSizeMin;
    private boolean mVertical;
    private float mFlashFrac = 0.0f;
    private final Runnable mDebugFlash = new Runnable() { // from class: android.inputmethodservice.navigationbar.DeadZone.2
        @Override // java.lang.Runnable
        public void run() {
            ObjectAnimator.ofFloat(DeadZone.this, DeadZone.FLASH_PROPERTY, 1.0f, 0.0f).setDuration(150L).start();
        }
    };

    static float lerp(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    DeadZone(NavigationBarView navigationBarView) {
        this.mNavigationBarView = navigationBarView;
        onConfigurationChanged(0);
    }

    private float getSize(long j) {
        int iLerp;
        int i = this.mSizeMax;
        if (i == 0) {
            return 0.0f;
        }
        long j2 = j - this.mLastPokeTime;
        int i2 = this.mHold;
        int i3 = this.mDecay;
        if (j2 > i2 + i3) {
            iLerp = this.mSizeMin;
        } else {
            if (j2 < i2) {
                return i;
            }
            iLerp = (int) lerp(i, this.mSizeMin, (j2 - i2) / i3);
        }
        return iLerp;
    }

    public void setFlashOnTouchCapture(boolean z) {
        this.mShouldFlash = z;
        this.mFlashFrac = 0.0f;
        this.mNavigationBarView.postInvalidate();
    }

    public void onConfigurationChanged(int i) {
        this.mDisplayRotation = i;
        Resources resources = this.mNavigationBarView.getResources();
        this.mHold = 333;
        this.mDecay = 333;
        this.mSizeMin = NavigationBarUtils.dpToPx(12.0f, resources);
        this.mSizeMax = NavigationBarUtils.dpToPx(32.0f, resources);
        this.mVertical = resources.getConfiguration().orientation == 2;
        setFlashOnTouchCapture(false);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getToolType(0) == 3) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 4) {
            poke(motionEvent);
            return true;
        }
        if (action == 0) {
            int size = (int) getSize(motionEvent.getEventTime());
            if (!this.mVertical ? motionEvent.getY() < size : !(this.mDisplayRotation != 3 ? motionEvent.getX() >= size : motionEvent.getX() <= this.mNavigationBarView.getWidth() - size)) {
                Log.v(TAG, "consuming errant click: (" + motionEvent.getX() + "," + motionEvent.getY() + NavigationBarInflaterView.KEY_CODE_END);
                if (this.mShouldFlash) {
                    this.mNavigationBarView.post(this.mDebugFlash);
                    this.mNavigationBarView.postInvalidate();
                }
                return true;
            }
        }
        return false;
    }

    private void poke(MotionEvent motionEvent) {
        this.mLastPokeTime = motionEvent.getEventTime();
        if (this.mShouldFlash) {
            this.mNavigationBarView.postInvalidate();
        }
    }

    public void setFlash(float f) {
        this.mFlashFrac = f;
        this.mNavigationBarView.postInvalidate();
    }

    public float getFlash() {
        return this.mFlashFrac;
    }

    public void onDraw(Canvas canvas) {
        if (!this.mShouldFlash || this.mFlashFrac <= 0.0f) {
            return;
        }
        int size = (int) getSize(SystemClock.uptimeMillis());
        if (this.mVertical) {
            if (this.mDisplayRotation == 3) {
                canvas.clipRect(canvas.getWidth() - size, 0, canvas.getWidth(), canvas.getHeight());
            } else {
                canvas.clipRect(0, 0, size, canvas.getHeight());
            }
        } else {
            canvas.clipRect(0, 0, canvas.getWidth(), size);
        }
        canvas.drawARGB((int) (this.mFlashFrac * 255.0f), 221, 238, 170);
    }
}
