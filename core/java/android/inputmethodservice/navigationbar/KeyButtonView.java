package android.inputmethodservice.navigationbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.navigationbar.KeyButtonRipple;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputConnection;
import android.widget.ImageView;
import com.android.internal.R;

/* loaded from: classes2.dex */
public class KeyButtonView extends ImageView implements ButtonInterface {
    public static final float QUICKSTEP_TOUCH_SLOP_RATIO = 3.0f;
    private static final String TAG = KeyButtonView.class.getSimpleName();
    private AudioManager mAudioManager;
    private int mCode;
    private float mDarkIntensity;
    private long mDownTime;
    private boolean mGestureAborted;
    private boolean mHasOvalBg;
    private View.OnClickListener mOnClickListener;
    private final Paint mOvalBgPaint;
    private final boolean mPlaySounds;
    private final KeyButtonRipple mRipple;
    private int mTouchDownX;
    private int mTouchDownY;
    private boolean mTracking;

    public KeyButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOvalBgPaint = new Paint(3);
        this.mHasOvalBg = false;
        switch (getId()) {
            case R.id.input_method_nav_back /* 16909180 */:
                this.mCode = 4;
                break;
            default:
                this.mCode = 0;
                break;
        }
        this.mPlaySounds = true;
        setClickable(true);
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mRipple = new KeyButtonRipple(context, this, R.dimen.input_method_nav_key_button_ripple_max_width);
        setBackground(this.mRipple);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }

    @Override // android.view.View
    public boolean isClickable() {
        return this.mCode != 0 || super.isClickable();
    }

    public void setCode(int code) {
        this.mCode = code;
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (this.mCode != 0) {
            info.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, (CharSequence) null));
            if (isLongClickable()) {
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, (CharSequence) null));
            }
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        if (action == 16 && this.mCode != 0) {
            sendEvent(0, 0, SystemClock.uptimeMillis());
            sendEvent(1, this.mTracking ? 512 : 0);
            this.mTracking = false;
            sendAccessibilityEvent(1);
            playSoundEffect(0);
            return true;
        }
        if (action == 32 && this.mCode != 0) {
            sendEvent(0, 128);
            sendEvent(1, this.mTracking ? 512 : 0);
            this.mTracking = false;
            sendAccessibilityEvent(2);
            return true;
        }
        return super.performAccessibilityActionInternal(action, arguments);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00c7, code lost:
    
        return true;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r11) {
        /*
            r10 = this;
            r0 = 0
            int r1 = r11.getAction()
            r2 = 0
            if (r1 != 0) goto La
            r10.mGestureAborted = r2
        La:
            boolean r3 = r10.mGestureAborted
            if (r3 == 0) goto L12
            r10.setPressed(r2)
            return r2
        L12:
            r3 = 32
            r4 = 1
            switch(r1) {
                case 0: goto L9f;
                case 1: goto L57;
                case 2: goto L26;
                case 3: goto L1a;
                default: goto L18;
            }
        L18:
            goto Lc7
        L1a:
            r10.setPressed(r2)
            int r2 = r10.mCode
            if (r2 == 0) goto Lc7
            r10.sendEvent(r4, r3)
            goto Lc7
        L26:
            float r3 = r11.getRawX()
            int r3 = (int) r3
            float r5 = r11.getRawY()
            int r5 = (int) r5
            android.content.Context r6 = r10.getContext()
            float r6 = getQuickStepTouchSlopPx(r6)
            int r7 = r10.mTouchDownX
            int r7 = r3 - r7
            int r7 = java.lang.Math.abs(r7)
            float r7 = (float) r7
            int r7 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r7 > 0) goto L52
            int r7 = r10.mTouchDownY
            int r7 = r5 - r7
            int r7 = java.lang.Math.abs(r7)
            float r7 = (float) r7
            int r7 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r7 <= 0) goto Lc7
        L52:
            r10.setPressed(r2)
            goto Lc7
        L57:
            boolean r5 = r10.isPressed()
            r10.setPressed(r2)
            long r6 = android.os.SystemClock.uptimeMillis()
            long r8 = r10.mDownTime
            long r6 = r6 - r8
            r8 = 150(0x96, double:7.4E-322)
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 <= 0) goto L6d
            r6 = r4
            goto L6e
        L6d:
            r6 = r2
        L6e:
            if (r6 == 0) goto L75
            r7 = 8
            r10.performHapticFeedback(r7)
        L75:
            int r7 = r10.mCode
            if (r7 == 0) goto L90
            if (r5 == 0) goto L8c
            boolean r3 = r10.mTracking
            if (r3 == 0) goto L82
            r3 = 512(0x200, float:7.175E-43)
            goto L83
        L82:
            r3 = r2
        L83:
            r10.sendEvent(r4, r3)
            r10.mTracking = r2
            r10.sendAccessibilityEvent(r4)
            goto Lc7
        L8c:
            r10.sendEvent(r4, r3)
            goto Lc7
        L90:
            if (r5 == 0) goto Lc7
            android.view.View$OnClickListener r2 = r10.mOnClickListener
            if (r2 == 0) goto Lc7
            android.view.View$OnClickListener r2 = r10.mOnClickListener
            r2.onClick(r10)
            r10.sendAccessibilityEvent(r4)
            goto Lc7
        L9f:
            long r5 = android.os.SystemClock.uptimeMillis()
            r10.mDownTime = r5
            r10.setPressed(r4)
            float r3 = r11.getRawX()
            int r3 = (int) r3
            r10.mTouchDownX = r3
            float r3 = r11.getRawY()
            int r3 = (int) r3
            r10.mTouchDownY = r3
            int r3 = r10.mCode
            if (r3 == 0) goto Lc0
            long r5 = r10.mDownTime
            r10.sendEvent(r2, r2, r5)
            goto Lc3
        Lc0:
            r10.performHapticFeedback(r4)
        Lc3:
            r10.playSoundEffect(r2)
        Lc7:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.navigationbar.KeyButtonView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.ImageView, android.inputmethodservice.navigationbar.ButtonInterface
    /* renamed from: setImageDrawable */
    public void lambda$setImageURIAsync$0(Drawable drawable) {
        super.lambda$setImageURIAsync$0(drawable);
        if (drawable == null) {
            return;
        }
        KeyButtonDrawable keyButtonDrawable = (KeyButtonDrawable) drawable;
        keyButtonDrawable.setDarkIntensity(this.mDarkIntensity);
        this.mHasOvalBg = keyButtonDrawable.hasOvalBg();
        if (this.mHasOvalBg) {
            this.mOvalBgPaint.setColor(keyButtonDrawable.getDrawableBackgroundColor());
        }
        this.mRipple.setType(keyButtonDrawable.hasOvalBg() ? KeyButtonRipple.Type.OVAL : KeyButtonRipple.Type.ROUNDED_RECT);
    }

    @Override // android.view.View
    public void playSoundEffect(int soundConstant) {
        if (this.mPlaySounds) {
            this.mAudioManager.playSoundEffect(soundConstant);
        }
    }

    private void sendEvent(int action, int flags) {
        sendEvent(action, flags, SystemClock.uptimeMillis());
    }

    private void sendEvent(int action, int flags, long when) {
        boolean handled;
        InputConnection ic;
        if (this.mContext instanceof InputMethodService) {
            int repeatCount = (flags & 128) != 0 ? 1 : 0;
            KeyEvent ev = new KeyEvent(this.mDownTime, when, action, this.mCode, repeatCount, 0, -1, 0, flags | 2 | 64, 257);
            int displayId = -1;
            if (getDisplay() != null) {
                displayId = getDisplay().getDisplayId();
            }
            if (displayId != -1) {
                ev.setDisplayId(displayId);
            }
            InputMethodService ims = (InputMethodService) this.mContext;
            switch (action) {
                case 0:
                    boolean handled2 = ims.onKeyDown(ev.getKeyCode(), ev);
                    this.mTracking = handled2 && ev.getRepeatCount() == 0 && (ev.getFlags() & 1073741824) != 0;
                    handled = handled2;
                    break;
                case 1:
                    handled = ims.onKeyUp(ev.getKeyCode(), ev);
                    break;
                default:
                    handled = false;
                    break;
            }
            if (!handled && (ic = ims.getCurrentInputConnection()) != null) {
                ic.sendKeyEvent(ev);
            }
        }
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDarkIntensity(float darkIntensity) {
        this.mDarkIntensity = darkIntensity;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ((KeyButtonDrawable) drawable).setDarkIntensity(darkIntensity);
            invalidate();
        }
        this.mRipple.setDarkIntensity(darkIntensity);
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDelayTouchFeedback(boolean shouldDelay) {
        this.mRipple.setDelayTouchFeedback(shouldDelay);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mHasOvalBg) {
            int d = Math.min(getWidth(), getHeight());
            canvas.drawOval(0.0f, 0.0f, d, d, this.mOvalBgPaint);
        }
        super.draw(canvas);
    }

    private static float getQuickStepTouchSlopPx(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop() * 3.0f;
    }
}
