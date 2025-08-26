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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputConnection;
import android.widget.ImageView;
import com.android.internal.R;

/* loaded from: classes2.dex */
public class KeyButtonView extends ImageView implements ButtonInterface {
    public static final float QUICKSTEP_TOUCH_SLOP_RATIO = 3.0f;
    private static final String TAG = "KeyButtonView";
    private AudioManager mAudioManager;
    private final Runnable mCheckLongPress;
    private int mCode;
    private float mDarkIntensity;
    private long mDownTime;
    private boolean mGestureAborted;
    private boolean mHasOvalBg;
    private boolean mLongClicked;
    private View.OnClickListener mOnClickListener;
    private final Paint mOvalBgPaint;
    private final boolean mPlaySounds;
    private final KeyButtonRipple mRipple;
    private int mTouchDownX;
    private int mTouchDownY;
    private boolean mTracking;

    public KeyButtonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOvalBgPaint = new Paint(3);
        this.mHasOvalBg = false;
        this.mCheckLongPress = new Runnable() { // from class: android.inputmethodservice.navigationbar.KeyButtonView.1
            @Override // java.lang.Runnable
            public void run() {
                if (KeyButtonView.this.isPressed() && KeyButtonView.this.performLongClick()) {
                    KeyButtonView.this.mLongClicked = true;
                }
            }
        };
        if (getId() == 16909220) {
            this.mCode = 4;
        } else {
            this.mCode = 0;
        }
        this.mPlaySounds = true;
        setClickable(true);
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(context, this, R.dimen.input_method_nav_key_button_ripple_max_width);
        this.mRipple = keyButtonRipple;
        setBackground(keyButtonRipple);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }

    @Override // android.view.View
    public boolean isClickable() {
        return this.mCode != 0 || super.isClickable();
    }

    public void setCode(int i) {
        this.mCode = i;
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (isClickable()) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, (CharSequence) null));
            if (isLongClickable()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, getAccessibilityLongClickActionLabel()));
            }
        }
    }

    private CharSequence getAccessibilityLongClickActionLabel() {
        if (Flags.imeSwitcherRevamp() && getId() == 16909226) {
            return getContext().getText(R.string.input_method_ime_switch_long_click_action_desc);
        }
        return null;
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (i == 16 && this.mCode != 0) {
            sendEvent(0, 0, SystemClock.uptimeMillis());
            sendEvent(1, this.mTracking ? 512 : 0);
            this.mTracking = false;
            sendAccessibilityEvent(1);
            playSoundEffect(0);
            return true;
        }
        if (i == 32 && this.mCode != 0) {
            sendEvent(0, 128);
            sendEvent(1, this.mTracking ? 512 : 0);
            this.mTracking = false;
            sendAccessibilityEvent(2);
            return true;
        }
        return super.performAccessibilityActionInternal(i, bundle);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        View.OnClickListener onClickListener;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mGestureAborted = false;
        }
        if (this.mGestureAborted) {
            setPressed(false);
            return false;
        }
        if (action == 0) {
            this.mDownTime = SystemClock.uptimeMillis();
            this.mLongClicked = false;
            setPressed(true);
            this.mTouchDownX = (int) motionEvent.getRawX();
            this.mTouchDownY = (int) motionEvent.getRawY();
            if (this.mCode != 0) {
                sendEvent(0, 0, this.mDownTime);
            } else {
                performHapticFeedback(1);
            }
            playSoundEffect(0);
            if (Flags.imeSwitcherRevamp() && isLongClickable()) {
                removeCallbacks(this.mCheckLongPress);
                postDelayed(this.mCheckLongPress, ViewConfiguration.getLongPressTimeout());
            }
        } else if (action == 1) {
            boolean z = isPressed() && !this.mLongClicked;
            setPressed(false);
            if (SystemClock.uptimeMillis() - this.mDownTime > 150 && !this.mLongClicked) {
                performHapticFeedback(8);
            }
            if (this.mCode != 0) {
                if (z) {
                    sendEvent(1, this.mTracking ? 512 : 0);
                    this.mTracking = false;
                    sendAccessibilityEvent(1);
                } else {
                    sendEvent(1, 32);
                }
            } else if (z && (onClickListener = this.mOnClickListener) != null) {
                onClickListener.onClick(this);
                sendAccessibilityEvent(1);
            }
            if (isLongClickable()) {
                removeCallbacks(this.mCheckLongPress);
            }
        } else if (action == 2) {
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            float quickStepTouchSlopPx = getQuickStepTouchSlopPx(getContext());
            if (Math.abs(rawX - this.mTouchDownX) > quickStepTouchSlopPx || Math.abs(rawY - this.mTouchDownY) > quickStepTouchSlopPx) {
                setPressed(false);
                if (isLongClickable()) {
                    removeCallbacks(this.mCheckLongPress);
                }
            }
        } else if (action == 3) {
            setPressed(false);
            if (this.mCode != 0) {
                sendEvent(1, 32);
            }
            if (isLongClickable()) {
                removeCallbacks(this.mCheckLongPress);
            }
        }
        return true;
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
        boolean zHasOvalBg = keyButtonDrawable.hasOvalBg();
        this.mHasOvalBg = zHasOvalBg;
        if (zHasOvalBg) {
            this.mOvalBgPaint.setColor(keyButtonDrawable.getDrawableBackgroundColor());
        }
        this.mRipple.setType(keyButtonDrawable.hasOvalBg() ? KeyButtonRipple.Type.OVAL : KeyButtonRipple.Type.ROUNDED_RECT);
    }

    @Override // android.view.View
    public void playSoundEffect(int i) {
        if (this.mPlaySounds) {
            this.mAudioManager.playSoundEffect(i);
        }
    }

    private void sendEvent(int i, int i2) {
        sendEvent(i, i2, SystemClock.uptimeMillis());
    }

    private void sendEvent(int i, int i2, long j) {
        InputConnection currentInputConnection;
        if (this.mContext instanceof InputMethodService) {
            boolean zOnKeyUp = false;
            KeyEvent keyEvent = new KeyEvent(this.mDownTime, j, i, this.mCode, (i2 & 128) != 0 ? 1 : 0, 0, -1, 0, i2 | 66, 257);
            int displayId = getDisplay() != null ? getDisplay().getDisplayId() : -1;
            if (displayId != -1) {
                keyEvent.setDisplayId(displayId);
            }
            InputMethodService inputMethodService = (InputMethodService) this.mContext;
            if (i == 0) {
                boolean zOnKeyDown = inputMethodService.onKeyDown(keyEvent.getKeyCode(), keyEvent);
                this.mTracking = zOnKeyDown && keyEvent.getRepeatCount() == 0 && (keyEvent.getFlags() & 1073741824) != 0;
                zOnKeyUp = zOnKeyDown;
            } else if (i == 1) {
                zOnKeyUp = inputMethodService.onKeyUp(keyEvent.getKeyCode(), keyEvent);
            }
            if (zOnKeyUp || (currentInputConnection = inputMethodService.getCurrentInputConnection()) == null) {
                return;
            }
            currentInputConnection.sendKeyEvent(keyEvent);
        }
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ((KeyButtonDrawable) drawable).setDarkIntensity(f);
            invalidate();
        }
        this.mRipple.setDarkIntensity(f);
    }

    @Override // android.inputmethodservice.navigationbar.ButtonInterface
    public void setDelayTouchFeedback(boolean z) {
        this.mRipple.setDelayTouchFeedback(z);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        Canvas canvas2;
        if (this.mHasOvalBg) {
            float fMin = Math.min(getWidth(), getHeight());
            canvas2 = canvas;
            canvas2.drawOval(0.0f, 0.0f, fMin, fMin, this.mOvalBgPaint);
        } else {
            canvas2 = canvas;
        }
        super.draw(canvas2);
    }

    private static float getQuickStepTouchSlopPx(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop() * 3.0f;
    }
}
