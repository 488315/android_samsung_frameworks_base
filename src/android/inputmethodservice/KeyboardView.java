package android.inputmethodservice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.R;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: classes2.dex */
public class KeyboardView extends View implements View.OnClickListener {
    private static final int DEBOUNCE_TIME = 70;
    private static final boolean DEBUG = false;
    private static final int DELAY_AFTER_PREVIEW = 70;
    private static final int DELAY_BEFORE_PREVIEW = 0;
    private static final int MSG_LONGPRESS = 4;
    private static final int MSG_REMOVE_PREVIEW = 2;
    private static final int MSG_REPEAT = 3;
    private static final int MSG_SHOW_PREVIEW = 1;
    private static final int MULTITAP_INTERVAL = 800;
    private static final int NOT_A_KEY = -1;
    private static final int REPEAT_INTERVAL = 50;
    private static final int REPEAT_START_DELAY = 400;
    private boolean mAbortKey;
    private AccessibilityManager mAccessibilityManager;
    private AudioManager mAudioManager;
    private float mBackgroundDimAmount;
    private Bitmap mBuffer;
    private Canvas mCanvas;
    private Rect mClipRegion;
    private final int[] mCoordinates;
    private int mCurrentKey;
    private int mCurrentKeyIndex;
    private long mCurrentKeyTime;
    private Rect mDirtyRect;
    private boolean mDisambiguateSwipe;
    private int[] mDistances;
    private int mDownKey;
    private long mDownTime;
    private boolean mDrawPending;
    private GestureDetector mGestureDetector;
    Handler mHandler;
    private boolean mHeadsetRequiredToHearPasswordsAnnounced;
    private boolean mInMultiTap;
    private Keyboard.Key mInvalidatedKey;
    private Drawable mKeyBackground;
    private int[] mKeyIndices;
    private int mKeyTextColor;
    private int mKeyTextSize;
    private Keyboard mKeyboard;
    private OnKeyboardActionListener mKeyboardActionListener;
    private boolean mKeyboardChanged;
    private Keyboard.Key[] mKeys;
    private int mLabelTextSize;
    private int mLastCodeX;
    private int mLastCodeY;
    private int mLastKey;
    private long mLastKeyTime;
    private long mLastMoveTime;
    private int mLastSentIndex;
    private long mLastTapTime;
    private int mLastX;
    private int mLastY;
    private KeyboardView mMiniKeyboard;
    private Map<Keyboard.Key, View> mMiniKeyboardCache;
    private View mMiniKeyboardContainer;
    private int mMiniKeyboardOffsetX;
    private int mMiniKeyboardOffsetY;
    private boolean mMiniKeyboardOnScreen;
    private int mOldPointerCount;
    private float mOldPointerX;
    private float mOldPointerY;
    private Rect mPadding;
    private Paint mPaint;
    private PopupWindow mPopupKeyboard;
    private int mPopupLayout;
    private View mPopupParent;
    private int mPopupPreviewX;
    private int mPopupPreviewY;
    private int mPopupX;
    private int mPopupY;
    private boolean mPossiblePoly;
    private boolean mPreviewCentered;
    private int mPreviewHeight;
    private StringBuilder mPreviewLabel;
    private int mPreviewOffset;
    private PopupWindow mPreviewPopup;
    private TextView mPreviewText;
    private int mPreviewTextSizeLarge;
    private boolean mProximityCorrectOn;
    private int mProximityThreshold;
    private int mRepeatKeyIndex;
    private int mShadowColor;
    private float mShadowRadius;
    private boolean mShowPreview;
    private boolean mShowTouchPoints;
    private int mStartX;
    private int mStartY;
    private int mSwipeThreshold;
    private SwipeTracker mSwipeTracker;
    private int mTapCount;
    private int mVerticalCorrection;
    private static final int[] KEY_DELETE = {-5};
    private static final int[] LONG_PRESSABLE_STATE_SET = {16843324};
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    private static int MAX_NEARBY_KEYS = 12;

    public interface OnKeyboardActionListener {
        void onKey(int i, int[] iArr);

        void onPress(int i);

        void onRelease(int i);

        void onText(CharSequence charSequence);

        void swipeDown();

        void swipeLeft();

        void swipeRight();

        void swipeUp();
    }

    public void setVerticalCorrection(int i) {
    }

    public KeyboardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.keyboardViewStyle);
    }

    public KeyboardView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public KeyboardView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCurrentKeyIndex = -1;
        this.mCoordinates = new int[2];
        this.mPreviewCentered = false;
        this.mShowPreview = true;
        this.mShowTouchPoints = true;
        this.mCurrentKey = -1;
        this.mDownKey = -1;
        this.mKeyIndices = new int[12];
        this.mRepeatKeyIndex = -1;
        this.mClipRegion = new Rect(0, 0, 0, 0);
        this.mSwipeTracker = new SwipeTracker();
        this.mOldPointerCount = 1;
        this.mDistances = new int[MAX_NEARBY_KEYS];
        this.mPreviewLabel = new StringBuilder(1);
        this.mDirtyRect = new Rect();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.KeyboardView, i, i2);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int indexCount = obtainStyledAttributes.getIndexCount();
        int i3 = 0;
        for (int i4 = 0; i4 < indexCount; i4++) {
            int index = obtainStyledAttributes.getIndex(i4);
            switch (index) {
                case 0:
                    this.mShadowColor = obtainStyledAttributes.getColor(index, 0);
                    break;
                case 1:
                    this.mShadowRadius = obtainStyledAttributes.getFloat(index, 0.0f);
                    break;
                case 2:
                    this.mKeyBackground = obtainStyledAttributes.getDrawable(index);
                    break;
                case 3:
                    this.mKeyTextSize = obtainStyledAttributes.getDimensionPixelSize(index, 18);
                    break;
                case 4:
                    this.mLabelTextSize = obtainStyledAttributes.getDimensionPixelSize(index, 14);
                    break;
                case 5:
                    this.mKeyTextColor = obtainStyledAttributes.getColor(index, -16777216);
                    break;
                case 6:
                    i3 = obtainStyledAttributes.getResourceId(index, 0);
                    break;
                case 7:
                    this.mPreviewOffset = obtainStyledAttributes.getDimensionPixelOffset(index, 0);
                    break;
                case 8:
                    this.mPreviewHeight = obtainStyledAttributes.getDimensionPixelSize(index, 80);
                    break;
                case 9:
                    this.mVerticalCorrection = obtainStyledAttributes.getDimensionPixelOffset(index, 0);
                    break;
                case 10:
                    this.mPopupLayout = obtainStyledAttributes.getResourceId(index, 0);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        this.mBackgroundDimAmount = obtainStyledAttributes2.getFloat(2, 0.5f);
        obtainStyledAttributes2.recycle();
        this.mPreviewPopup = new PopupWindow(context);
        if (i3 != 0) {
            TextView textView = (TextView) layoutInflater.inflate(i3, (ViewGroup) null);
            this.mPreviewText = textView;
            this.mPreviewTextSizeLarge = (int) textView.getTextSize();
            this.mPreviewPopup.setContentView(this.mPreviewText);
            this.mPreviewPopup.setBackgroundDrawable(null);
        } else {
            this.mShowPreview = false;
        }
        this.mPreviewPopup.setTouchable(false);
        PopupWindow popupWindow = new PopupWindow(context);
        this.mPopupKeyboard = popupWindow;
        popupWindow.setBackgroundDrawable(null);
        this.mPopupParent = this;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setTextSize(0);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setAlpha(255);
        this.mPadding = new Rect(0, 0, 0, 0);
        this.mMiniKeyboardCache = new HashMap();
        this.mKeyBackground.getPadding(this.mPadding);
        this.mSwipeThreshold = (int) (getResources().getDisplayMetrics().density * 500.0f);
        this.mDisambiguateSwipe = getResources().getBoolean(R.bool.config_swipeDisambiguation);
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        resetMultiTap();
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initGestureDetector();
        if (this.mHandler == null) {
            this.mHandler = new Handler() { // from class: android.inputmethodservice.KeyboardView.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    int i = message.what;
                    if (i == 1) {
                        KeyboardView.this.showKey(message.arg1);
                        return;
                    }
                    if (i == 2) {
                        KeyboardView.this.mPreviewText.setVisibility(4);
                        return;
                    }
                    if (i != 3) {
                        if (i != 4) {
                            return;
                        }
                        KeyboardView.this.openPopupIfRequired((MotionEvent) message.obj);
                    } else if (KeyboardView.this.repeatKey()) {
                        sendMessageDelayed(Message.obtain(this, 3), 50L);
                    }
                }
            };
        }
    }

    private void initGestureDetector() {
        if (this.mGestureDetector == null) {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: android.inputmethodservice.KeyboardView.2
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (KeyboardView.this.mPossiblePoly) {
                        return false;
                    }
                    float abs = Math.abs(f);
                    float abs2 = Math.abs(f2);
                    float x = motionEvent2.getX() - motionEvent.getX();
                    float y = motionEvent2.getY() - motionEvent.getY();
                    int width = KeyboardView.this.getWidth() / 2;
                    int height = KeyboardView.this.getHeight() / 2;
                    KeyboardView.this.mSwipeTracker.computeCurrentVelocity(1000);
                    float xVelocity = KeyboardView.this.mSwipeTracker.getXVelocity();
                    float yVelocity = KeyboardView.this.mSwipeTracker.getYVelocity();
                    if (f <= KeyboardView.this.mSwipeThreshold || abs2 >= abs || x <= width) {
                        if (f >= (-KeyboardView.this.mSwipeThreshold) || abs2 >= abs || x >= (-width)) {
                            if (f2 >= (-KeyboardView.this.mSwipeThreshold) || abs >= abs2 || y >= (-height)) {
                                if (f2 > KeyboardView.this.mSwipeThreshold && abs < abs2 / 2.0f && y > height) {
                                    if (!KeyboardView.this.mDisambiguateSwipe || yVelocity >= f2 / 4.0f) {
                                        KeyboardView.this.swipeDown();
                                        return true;
                                    }
                                }
                                return false;
                            }
                            if (!KeyboardView.this.mDisambiguateSwipe || yVelocity <= f2 / 4.0f) {
                                KeyboardView.this.swipeUp();
                                return true;
                            }
                        } else if (!KeyboardView.this.mDisambiguateSwipe || xVelocity <= f / 4.0f) {
                            KeyboardView.this.swipeLeft();
                            return true;
                        }
                    } else if (!KeyboardView.this.mDisambiguateSwipe || xVelocity >= f / 4.0f) {
                        KeyboardView.this.swipeRight();
                        return true;
                    }
                    KeyboardView keyboardView = KeyboardView.this;
                    keyboardView.detectAndSendKey(keyboardView.mDownKey, KeyboardView.this.mStartX, KeyboardView.this.mStartY, motionEvent.getEventTime());
                    return false;
                }
            });
            this.mGestureDetector = gestureDetector;
            gestureDetector.setIsLongpressEnabled(false);
        }
    }

    public void setOnKeyboardActionListener(OnKeyboardActionListener onKeyboardActionListener) {
        this.mKeyboardActionListener = onKeyboardActionListener;
    }

    protected OnKeyboardActionListener getOnKeyboardActionListener() {
        return this.mKeyboardActionListener;
    }

    public void setKeyboard(Keyboard keyboard) {
        if (this.mKeyboard != null) {
            showPreview(-1);
        }
        removeMessages();
        this.mKeyboard = keyboard;
        List<Keyboard.Key> keys = keyboard.getKeys();
        this.mKeys = (Keyboard.Key[]) keys.toArray(new Keyboard.Key[keys.size()]);
        requestLayout();
        this.mKeyboardChanged = true;
        invalidateAllKeys();
        computeProximityThreshold(keyboard);
        this.mMiniKeyboardCache.clear();
        this.mAbortKey = true;
    }

    public Keyboard getKeyboard() {
        return this.mKeyboard;
    }

    public boolean setShifted(boolean z) {
        Keyboard keyboard = this.mKeyboard;
        if (keyboard == null || !keyboard.setShifted(z)) {
            return false;
        }
        invalidateAllKeys();
        return true;
    }

    public boolean isShifted() {
        Keyboard keyboard = this.mKeyboard;
        if (keyboard != null) {
            return keyboard.isShifted();
        }
        return false;
    }

    public void setPreviewEnabled(boolean z) {
        this.mShowPreview = z;
    }

    public boolean isPreviewEnabled() {
        return this.mShowPreview;
    }

    public void setPopupParent(View view) {
        this.mPopupParent = view;
    }

    public void setPopupOffset(int i, int i2) {
        this.mMiniKeyboardOffsetX = i;
        this.mMiniKeyboardOffsetY = i2;
        if (this.mPreviewPopup.isShowing()) {
            this.mPreviewPopup.dismiss();
        }
    }

    public void setProximityCorrectionEnabled(boolean z) {
        this.mProximityCorrectOn = z;
    }

    public boolean isProximityCorrectionEnabled() {
        return this.mProximityCorrectOn;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        dismissPopupKeyboard();
    }

    private CharSequence adjustCase(CharSequence charSequence) {
        return (!this.mKeyboard.isShifted() || charSequence == null || charSequence.length() >= 3 || !Character.isLowerCase(charSequence.charAt(0))) ? charSequence : charSequence.toString().toUpperCase();
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        Keyboard keyboard = this.mKeyboard;
        if (keyboard == null) {
            setMeasuredDimension(this.mPaddingLeft + this.mPaddingRight, this.mPaddingTop + this.mPaddingBottom);
            return;
        }
        int minWidth = keyboard.getMinWidth() + this.mPaddingLeft + this.mPaddingRight;
        if (View.MeasureSpec.getSize(i) < minWidth + 10) {
            minWidth = View.MeasureSpec.getSize(i);
        }
        setMeasuredDimension(minWidth, this.mKeyboard.getHeight() + this.mPaddingTop + this.mPaddingBottom);
    }

    private void computeProximityThreshold(Keyboard keyboard) {
        Keyboard.Key[] keyArr;
        if (keyboard == null || (keyArr = this.mKeys) == null) {
            return;
        }
        int length = keyArr.length;
        int i = 0;
        for (Keyboard.Key key : keyArr) {
            i += Math.min(key.width, key.height) + key.gap;
        }
        if (i < 0 || length == 0) {
            return;
        }
        int i2 = (int) ((i * 1.4f) / length);
        this.mProximityThreshold = i2 * i2;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Keyboard keyboard = this.mKeyboard;
        if (keyboard != null) {
            keyboard.resize(i, i2);
        }
        this.mBuffer = null;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawPending || this.mBuffer == null || this.mKeyboardChanged) {
            onBufferDraw();
        }
        canvas.drawBitmap(this.mBuffer, 0.0f, 0.0f, (Paint) null);
    }

    private void onBufferDraw() {
        boolean z;
        Drawable drawable;
        Rect rect;
        Bitmap bitmap = this.mBuffer;
        if (bitmap == null || this.mKeyboardChanged) {
            if (bitmap == null || (this.mKeyboardChanged && (bitmap.getWidth() != getWidth() || this.mBuffer.getHeight() != getHeight()))) {
                this.mBuffer = Bitmap.createBitmap(Math.max(1, getWidth()), Math.max(1, getHeight()), Bitmap.Config.ARGB_8888);
                this.mCanvas = new Canvas(this.mBuffer);
            }
            invalidateAllKeys();
            this.mKeyboardChanged = false;
        }
        if (this.mKeyboard == null) {
            return;
        }
        this.mCanvas.save();
        Canvas canvas = this.mCanvas;
        canvas.clipRect(this.mDirtyRect);
        Paint paint = this.mPaint;
        Drawable drawable2 = this.mKeyBackground;
        Rect rect2 = this.mClipRegion;
        Rect rect3 = this.mPadding;
        int i = this.mPaddingLeft;
        int i2 = this.mPaddingTop;
        Keyboard.Key[] keyArr = this.mKeys;
        Keyboard.Key key = this.mInvalidatedKey;
        paint.setColor(this.mKeyTextColor);
        boolean z2 = key != null && canvas.getClipBounds(rect2) && (key.x + i) - 1 <= rect2.left && (key.y + i2) - 1 <= rect2.top && ((key.x + key.width) + i) + 1 >= rect2.right && ((key.y + key.height) + i2) + 1 >= rect2.bottom;
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        int length = keyArr.length;
        int i3 = 0;
        while (i3 < length) {
            Keyboard.Key key2 = keyArr[i3];
            if (!z2 || key == key2) {
                drawable2.setState(key2.getCurrentDrawableState());
                String charSequence = key2.label == null ? null : adjustCase(key2.label).toString();
                Rect bounds = drawable2.getBounds();
                z = z2;
                if (key2.width != bounds.right || key2.height != bounds.bottom) {
                    drawable2.setBounds(0, 0, key2.width, key2.height);
                }
                canvas.translate(key2.x + i, key2.y + i2);
                drawable2.draw(canvas);
                if (charSequence != null) {
                    if (charSequence.length() > 1 && key2.codes.length < 2) {
                        paint.setTextSize(this.mLabelTextSize);
                        paint.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        paint.setTextSize(this.mKeyTextSize);
                        paint.setTypeface(Typeface.DEFAULT);
                    }
                    paint.setShadowLayer(this.mShadowRadius, 0.0f, 0.0f, this.mShadowColor);
                    canvas.drawText(charSequence, (((key2.width - rect3.left) - rect3.right) / 2) + rect3.left, (((key2.height - rect3.top) - rect3.bottom) / 2) + ((paint.getTextSize() - paint.descent()) / 2.0f) + rect3.top, paint);
                    paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
                } else if (key2.icon != null) {
                    canvas.translate(((((key2.width - rect3.left) - rect3.right) - key2.icon.getIntrinsicWidth()) / 2) + rect3.left, ((((key2.height - rect3.top) - rect3.bottom) - key2.icon.getIntrinsicHeight()) / 2) + rect3.top);
                    drawable = drawable2;
                    rect = rect3;
                    key2.icon.setBounds(0, 0, key2.icon.getIntrinsicWidth(), key2.icon.getIntrinsicHeight());
                    key2.icon.draw(canvas);
                    canvas.translate(-r2, -r3);
                    canvas.translate((-key2.x) - i, (-key2.y) - i2);
                }
                drawable = drawable2;
                rect = rect3;
                canvas.translate((-key2.x) - i, (-key2.y) - i2);
            } else {
                drawable = drawable2;
                z = z2;
                rect = rect3;
            }
            i3++;
            drawable2 = drawable;
            z2 = z;
            rect3 = rect;
        }
        this.mInvalidatedKey = null;
        if (this.mMiniKeyboardOnScreen) {
            paint.setColor(((int) (this.mBackgroundDimAmount * 255.0f)) << 24);
            canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), paint);
        }
        this.mCanvas.restore();
        this.mDrawPending = false;
        this.mDirtyRect.setEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x003a, code lost:
    
        if (r15 >= r17.mProximityThreshold) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getKeyIndices(int r18, int r19, int[] r20) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            android.inputmethodservice.Keyboard$Key[] r4 = r0.mKeys
            int r5 = r0.mProximityThreshold
            int r5 = r5 + 1
            int[] r6 = r0.mDistances
            r7 = 2147483647(0x7fffffff, float:NaN)
            java.util.Arrays.fill(r6, r7)
            android.inputmethodservice.Keyboard r6 = r0.mKeyboard
            int[] r6 = r6.getNearestKeys(r1, r2)
            int r7 = r6.length
            r10 = 0
            r11 = -1
            r12 = -1
        L20:
            if (r10 >= r7) goto L90
            r13 = r6[r10]
            r13 = r4[r13]
            boolean r14 = r13.isInside(r1, r2)
            if (r14 == 0) goto L2e
            r11 = r6[r10]
        L2e:
            boolean r15 = r0.mProximityCorrectOn
            if (r15 == 0) goto L3d
            int r15 = r13.squaredDistanceFrom(r1, r2)
            r16 = 0
            int r9 = r0.mProximityThreshold
            if (r15 < r9) goto L43
            goto L41
        L3d:
            r16 = 0
            r15 = r16
        L41:
            if (r14 == 0) goto L89
        L43:
            int[] r9 = r13.codes
            r9 = r9[r16]
            r14 = 32
            if (r9 <= r14) goto L89
            int[] r9 = r13.codes
            int r9 = r9.length
            if (r15 >= r5) goto L53
            r12 = r6[r10]
            r5 = r15
        L53:
            if (r3 != 0) goto L56
            goto L89
        L56:
            r14 = r16
        L58:
            int[] r8 = r0.mDistances
            int r1 = r8.length
            if (r14 >= r1) goto L89
            r1 = r8[r14]
            if (r1 <= r15) goto L82
            int r1 = r14 + r9
            int r2 = r8.length
            int r2 = r2 - r14
            int r2 = r2 - r9
            java.lang.System.arraycopy(r8, r14, r8, r1, r2)
            int r2 = r3.length
            int r2 = r2 - r14
            int r2 = r2 - r9
            java.lang.System.arraycopy(r3, r14, r3, r1, r2)
            r1 = r16
        L71:
            if (r1 >= r9) goto L89
            int r2 = r14 + r1
            int[] r8 = r13.codes
            r8 = r8[r1]
            r3[r2] = r8
            int[] r8 = r0.mDistances
            r8[r2] = r15
            int r1 = r1 + 1
            goto L71
        L82:
            int r14 = r14 + 1
            r1 = r18
            r2 = r19
            goto L58
        L89:
            int r10 = r10 + 1
            r1 = r18
            r2 = r19
            goto L20
        L90:
            r1 = -1
            if (r11 != r1) goto L94
            return r12
        L94:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.KeyboardView.getKeyIndices(int, int, int[]):int");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detectAndSendKey(int i, int i2, int i3, long j) {
        if (i != -1) {
            Keyboard.Key[] keyArr = this.mKeys;
            if (i < keyArr.length) {
                Keyboard.Key key = keyArr[i];
                if (key.text != null) {
                    this.mKeyboardActionListener.onText(key.text);
                    this.mKeyboardActionListener.onRelease(-1);
                } else {
                    int i4 = key.codes[0];
                    int[] iArr = new int[MAX_NEARBY_KEYS];
                    Arrays.fill(iArr, -1);
                    getKeyIndices(i2, i3, iArr);
                    if (this.mInMultiTap) {
                        if (this.mTapCount != -1) {
                            this.mKeyboardActionListener.onKey(-5, KEY_DELETE);
                        } else {
                            this.mTapCount = 0;
                        }
                        i4 = key.codes[this.mTapCount];
                    }
                    this.mKeyboardActionListener.onKey(i4, iArr);
                    this.mKeyboardActionListener.onRelease(i4);
                }
                this.mLastSentIndex = i;
                this.mLastTapTime = j;
            }
        }
    }

    private CharSequence getPreviewText(Keyboard.Key key) {
        if (this.mInMultiTap) {
            this.mPreviewLabel.setLength(0);
            StringBuilder sb = this.mPreviewLabel;
            int[] iArr = key.codes;
            int i = this.mTapCount;
            sb.append((char) iArr[i >= 0 ? i : 0]);
            return adjustCase(this.mPreviewLabel);
        }
        return adjustCase(key.label);
    }

    private void showPreview(int i) {
        int i2 = this.mCurrentKeyIndex;
        PopupWindow popupWindow = this.mPreviewPopup;
        this.mCurrentKeyIndex = i;
        Keyboard.Key[] keyArr = this.mKeys;
        if (i2 != i) {
            if (i2 != -1 && keyArr.length > i2) {
                Keyboard.Key key = keyArr[i2];
                key.onReleased(i == -1);
                invalidateKey(i2);
                int i3 = key.codes[0];
                sendAccessibilityEventForUnicodeCharacter(256, i3);
                sendAccessibilityEventForUnicodeCharacter(65536, i3);
            }
            int i4 = this.mCurrentKeyIndex;
            if (i4 != -1 && keyArr.length > i4) {
                Keyboard.Key key2 = keyArr[i4];
                key2.onPressed();
                invalidateKey(this.mCurrentKeyIndex);
                int i5 = key2.codes[0];
                sendAccessibilityEventForUnicodeCharacter(128, i5);
                sendAccessibilityEventForUnicodeCharacter(32768, i5);
            }
        }
        if (i2 == this.mCurrentKeyIndex || !this.mShowPreview) {
            return;
        }
        this.mHandler.removeMessages(1);
        if (popupWindow.isShowing() && i == -1) {
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(2), 70L);
        }
        if (i != -1) {
            if (popupWindow.isShowing() && this.mPreviewText.getVisibility() == 0) {
                showKey(i);
            } else {
                Handler handler2 = this.mHandler;
                handler2.sendMessageDelayed(handler2.obtainMessage(1, i, 0), 0L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKey(int i) {
        PopupWindow popupWindow = this.mPreviewPopup;
        Keyboard.Key[] keyArr = this.mKeys;
        if (i < 0 || i >= keyArr.length) {
            return;
        }
        Keyboard.Key key = keyArr[i];
        if (key.icon != null) {
            this.mPreviewText.setCompoundDrawables(null, null, null, key.iconPreview != null ? key.iconPreview : key.icon);
            this.mPreviewText.lambda$setTextAsync$0((CharSequence) null);
        } else {
            this.mPreviewText.setCompoundDrawables(null, null, null, null);
            this.mPreviewText.lambda$setTextAsync$0(getPreviewText(key));
            if (key.label.length() > 1 && key.codes.length < 2) {
                this.mPreviewText.setTextSize(0, this.mKeyTextSize);
                this.mPreviewText.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                this.mPreviewText.setTextSize(0, this.mPreviewTextSizeLarge);
                this.mPreviewText.setTypeface(Typeface.DEFAULT);
            }
        }
        this.mPreviewText.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        int max = Math.max(this.mPreviewText.getMeasuredWidth(), key.width + this.mPreviewText.getPaddingLeft() + this.mPreviewText.getPaddingRight());
        int i2 = this.mPreviewHeight;
        ViewGroup.LayoutParams layoutParams = this.mPreviewText.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = max;
            layoutParams.height = i2;
        }
        if (!this.mPreviewCentered) {
            this.mPopupPreviewX = (key.x - this.mPreviewText.getPaddingLeft()) + this.mPaddingLeft;
            this.mPopupPreviewY = (key.y - i2) + this.mPreviewOffset;
        } else {
            this.mPopupPreviewX = 160 - (this.mPreviewText.getMeasuredWidth() / 2);
            this.mPopupPreviewY = -this.mPreviewText.getMeasuredHeight();
        }
        this.mHandler.removeMessages(2);
        getLocationInWindow(this.mCoordinates);
        int[] iArr = this.mCoordinates;
        iArr[0] = iArr[0] + this.mMiniKeyboardOffsetX;
        iArr[1] = iArr[1] + this.mMiniKeyboardOffsetY;
        this.mPreviewText.getBackground().setState(key.popupResId != 0 ? LONG_PRESSABLE_STATE_SET : EMPTY_STATE_SET);
        int i3 = this.mPopupPreviewX;
        int[] iArr2 = this.mCoordinates;
        this.mPopupPreviewX = i3 + iArr2[0];
        this.mPopupPreviewY += iArr2[1];
        getLocationOnScreen(iArr2);
        if (this.mPopupPreviewY + this.mCoordinates[1] < 0) {
            if (key.x + key.width <= getWidth() / 2) {
                this.mPopupPreviewX += (int) (key.width * 2.5d);
            } else {
                this.mPopupPreviewX -= (int) (key.width * 2.5d);
            }
            this.mPopupPreviewY += i2;
        }
        if (popupWindow.isShowing()) {
            popupWindow.update(this.mPopupPreviewX, this.mPopupPreviewY, max, i2);
        } else {
            popupWindow.setWidth(max);
            popupWindow.setHeight(i2);
            popupWindow.showAtLocation(this.mPopupParent, 0, this.mPopupPreviewX, this.mPopupPreviewY);
        }
        this.mPreviewText.setVisibility(0);
    }

    private void sendAccessibilityEventForUnicodeCharacter(int i, int i2) {
        String string;
        if (this.mAccessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
            onInitializeAccessibilityEvent(obtain);
            if (i2 != 10) {
                switch (i2) {
                    case -6:
                        string = this.mContext.getString(R.string.keyboardview_keycode_alt);
                        break;
                    case -5:
                        string = this.mContext.getString(R.string.keyboardview_keycode_delete);
                        break;
                    case -4:
                        string = this.mContext.getString(R.string.keyboardview_keycode_done);
                        break;
                    case -3:
                        string = this.mContext.getString(R.string.keyboardview_keycode_cancel);
                        break;
                    case -2:
                        string = this.mContext.getString(R.string.keyboardview_keycode_mode_change);
                        break;
                    case -1:
                        string = this.mContext.getString(R.string.keyboardview_keycode_shift);
                        break;
                    default:
                        string = String.valueOf((char) i2);
                        break;
                }
            } else {
                string = this.mContext.getString(R.string.keyboardview_keycode_enter);
            }
            obtain.getText().add(string);
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public void invalidateAllKeys() {
        this.mDirtyRect.union(0, 0, getWidth(), getHeight());
        this.mDrawPending = true;
        invalidate();
    }

    public void invalidateKey(int i) {
        Keyboard.Key[] keyArr = this.mKeys;
        if (keyArr != null && i >= 0 && i < keyArr.length) {
            Keyboard.Key key = keyArr[i];
            this.mInvalidatedKey = key;
            this.mDirtyRect.union(key.x + this.mPaddingLeft, key.y + this.mPaddingTop, key.x + key.width + this.mPaddingLeft, key.y + key.height + this.mPaddingTop);
            onBufferDraw();
            invalidate(key.x + this.mPaddingLeft, key.y + this.mPaddingTop, key.x + key.width + this.mPaddingLeft, key.y + key.height + this.mPaddingTop);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean openPopupIfRequired(MotionEvent motionEvent) {
        int i;
        if (this.mPopupLayout != 0 && (i = this.mCurrentKey) >= 0) {
            Keyboard.Key[] keyArr = this.mKeys;
            if (i < keyArr.length) {
                boolean onLongPress = onLongPress(keyArr[i]);
                if (onLongPress) {
                    this.mAbortKey = true;
                    showPreview(-1);
                }
                return onLongPress;
            }
        }
        return false;
    }

    protected boolean onLongPress(Keyboard.Key key) {
        Keyboard keyboard;
        int i = key.popupResId;
        if (i == 0) {
            return false;
        }
        View view = this.mMiniKeyboardCache.get(key);
        this.mMiniKeyboardContainer = view;
        if (view == null) {
            View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.mPopupLayout, (ViewGroup) null);
            this.mMiniKeyboardContainer = inflate;
            this.mMiniKeyboard = (KeyboardView) inflate.findViewById(16908326);
            View findViewById = this.mMiniKeyboardContainer.findViewById(16908327);
            if (findViewById != null) {
                findViewById.setOnClickListener(this);
            }
            this.mMiniKeyboard.setOnKeyboardActionListener(new OnKeyboardActionListener() { // from class: android.inputmethodservice.KeyboardView.3
                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeDown() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeLeft() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeRight() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void swipeUp() {
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onKey(int i2, int[] iArr) {
                    KeyboardView.this.mKeyboardActionListener.onKey(i2, iArr);
                    KeyboardView.this.dismissPopupKeyboard();
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onText(CharSequence charSequence) {
                    KeyboardView.this.mKeyboardActionListener.onText(charSequence);
                    KeyboardView.this.dismissPopupKeyboard();
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onPress(int i2) {
                    KeyboardView.this.mKeyboardActionListener.onPress(i2);
                }

                @Override // android.inputmethodservice.KeyboardView.OnKeyboardActionListener
                public void onRelease(int i2) {
                    KeyboardView.this.mKeyboardActionListener.onRelease(i2);
                }
            });
            if (key.popupCharacters != null) {
                keyboard = new Keyboard(getContext(), i, key.popupCharacters, -1, getPaddingRight() + getPaddingLeft());
            } else {
                keyboard = new Keyboard(getContext(), i);
            }
            this.mMiniKeyboard.setKeyboard(keyboard);
            this.mMiniKeyboard.setPopupParent(this);
            this.mMiniKeyboardContainer.measure(View.MeasureSpec.makeMeasureSpec(getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(getHeight(), Integer.MIN_VALUE));
            this.mMiniKeyboardCache.put(key, this.mMiniKeyboardContainer);
        } else {
            this.mMiniKeyboard = (KeyboardView) view.findViewById(16908326);
        }
        getLocationInWindow(this.mCoordinates);
        this.mPopupX = key.x + this.mPaddingLeft;
        this.mPopupY = key.y + this.mPaddingTop;
        this.mPopupX = (this.mPopupX + key.width) - this.mMiniKeyboardContainer.getMeasuredWidth();
        this.mPopupY -= this.mMiniKeyboardContainer.getMeasuredHeight();
        int paddingRight = this.mPopupX + this.mMiniKeyboardContainer.getPaddingRight() + this.mCoordinates[0];
        int paddingBottom = this.mPopupY + this.mMiniKeyboardContainer.getPaddingBottom() + this.mCoordinates[1];
        this.mMiniKeyboard.setPopupOffset(paddingRight < 0 ? 0 : paddingRight, paddingBottom);
        this.mMiniKeyboard.setShifted(isShifted());
        this.mPopupKeyboard.setContentView(this.mMiniKeyboardContainer);
        this.mPopupKeyboard.setWidth(this.mMiniKeyboardContainer.getMeasuredWidth());
        this.mPopupKeyboard.setHeight(this.mMiniKeyboardContainer.getMeasuredHeight());
        this.mPopupKeyboard.showAtLocation(this, 0, paddingRight, paddingBottom);
        this.mMiniKeyboardOnScreen = true;
        invalidateAllKeys();
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (!this.mAccessibilityManager.isTouchExplorationEnabled() || motionEvent.getPointerCount() != 1) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 7) {
            motionEvent.setAction(2);
        } else if (action == 9) {
            motionEvent.setAction(0);
        } else if (action == 10) {
            motionEvent.setAction(1);
        }
        return onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        int action = motionEvent.getAction();
        long eventTime = motionEvent.getEventTime();
        boolean z = true;
        if (pointerCount != this.mOldPointerCount) {
            if (pointerCount == 1) {
                MotionEvent obtain = MotionEvent.obtain(eventTime, eventTime, 0, motionEvent.getX(), motionEvent.getY(), motionEvent.getMetaState());
                boolean onModifiedTouchEvent = onModifiedTouchEvent(obtain, false);
                obtain.recycle();
                z = action == 1 ? onModifiedTouchEvent(motionEvent, true) : onModifiedTouchEvent;
            } else {
                MotionEvent obtain2 = MotionEvent.obtain(eventTime, eventTime, 1, this.mOldPointerX, this.mOldPointerY, motionEvent.getMetaState());
                z = onModifiedTouchEvent(obtain2, true);
                obtain2.recycle();
            }
        } else if (pointerCount == 1) {
            z = onModifiedTouchEvent(motionEvent, false);
            this.mOldPointerX = motionEvent.getX();
            this.mOldPointerY = motionEvent.getY();
        }
        this.mOldPointerCount = pointerCount;
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean onModifiedTouchEvent(android.view.MotionEvent r16, boolean r17) {
        /*
            Method dump skipped, instructions count: 433
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.KeyboardView.onModifiedTouchEvent(android.view.MotionEvent, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean repeatKey() {
        Keyboard.Key key = this.mKeys[this.mRepeatKeyIndex];
        detectAndSendKey(this.mCurrentKey, key.x, key.y, this.mLastTapTime);
        return true;
    }

    protected void swipeRight() {
        this.mKeyboardActionListener.swipeRight();
    }

    protected void swipeLeft() {
        this.mKeyboardActionListener.swipeLeft();
    }

    protected void swipeUp() {
        this.mKeyboardActionListener.swipeUp();
    }

    protected void swipeDown() {
        this.mKeyboardActionListener.swipeDown();
    }

    public void closing() {
        if (this.mPreviewPopup.isShowing()) {
            this.mPreviewPopup.dismiss();
        }
        removeMessages();
        dismissPopupKeyboard();
        this.mBuffer = null;
        this.mCanvas = null;
        this.mMiniKeyboardCache.clear();
    }

    private void removeMessages() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler.removeMessages(1);
        }
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        closing();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissPopupKeyboard() {
        if (this.mPopupKeyboard.isShowing()) {
            this.mPopupKeyboard.dismiss();
            this.mMiniKeyboardOnScreen = false;
            invalidateAllKeys();
        }
    }

    public boolean handleBack() {
        if (!this.mPopupKeyboard.isShowing()) {
            return false;
        }
        dismissPopupKeyboard();
        return true;
    }

    private void resetMultiTap() {
        this.mLastSentIndex = -1;
        this.mTapCount = 0;
        this.mLastTapTime = -1L;
        this.mInMultiTap = false;
    }

    private void checkMultiTap(long j, int i) {
        if (i == -1) {
            return;
        }
        Keyboard.Key key = this.mKeys[i];
        if (key.codes.length <= 1) {
            if (j > this.mLastTapTime + 800 || i != this.mLastSentIndex) {
                resetMultiTap();
                return;
            }
            return;
        }
        this.mInMultiTap = true;
        if (j < this.mLastTapTime + 800 && i == this.mLastSentIndex) {
            this.mTapCount = (this.mTapCount + 1) % key.codes.length;
        } else {
            this.mTapCount = -1;
        }
    }

    private static class SwipeTracker {
        static final int LONGEST_PAST_TIME = 200;
        static final int NUM_PAST = 4;
        final long[] mPastTime;
        final float[] mPastX;
        final float[] mPastY;
        float mXVelocity;
        float mYVelocity;

        private SwipeTracker() {
            this.mPastX = new float[4];
            this.mPastY = new float[4];
            this.mPastTime = new long[4];
        }

        public void clear() {
            this.mPastTime[0] = 0;
        }

        public void addMovement(MotionEvent motionEvent) {
            long eventTime = motionEvent.getEventTime();
            int historySize = motionEvent.getHistorySize();
            for (int i = 0; i < historySize; i++) {
                addPoint(motionEvent.getHistoricalX(i), motionEvent.getHistoricalY(i), motionEvent.getHistoricalEventTime(i));
            }
            addPoint(motionEvent.getX(), motionEvent.getY(), eventTime);
        }

        private void addPoint(float f, float f2, long j) {
            long[] jArr = this.mPastTime;
            int i = -1;
            int i2 = 0;
            while (i2 < 4) {
                long j2 = jArr[i2];
                if (j2 == 0) {
                    break;
                }
                if (j2 < j - 200) {
                    i = i2;
                }
                i2++;
            }
            if (i2 == 4 && i < 0) {
                i = 0;
            }
            if (i == i2) {
                i--;
            }
            float[] fArr = this.mPastX;
            float[] fArr2 = this.mPastY;
            if (i >= 0) {
                int i3 = i + 1;
                int i4 = 3 - i;
                System.arraycopy(fArr, i3, fArr, 0, i4);
                System.arraycopy(fArr2, i3, fArr2, 0, i4);
                System.arraycopy(jArr, i3, jArr, 0, i4);
                i2 -= i3;
            }
            fArr[i2] = f;
            fArr2[i2] = f2;
            jArr[i2] = j;
            int i5 = i2 + 1;
            if (i5 < 4) {
                jArr[i5] = 0;
            }
        }

        public void computeCurrentVelocity(int i) {
            computeCurrentVelocity(i, Float.MAX_VALUE);
        }

        public void computeCurrentVelocity(int i, float f) {
            float min;
            float min2;
            float[] fArr;
            float[] fArr2 = this.mPastX;
            float[] fArr3 = this.mPastY;
            long[] jArr = this.mPastTime;
            int i2 = 0;
            float f2 = fArr2[0];
            float f3 = fArr3[0];
            long j = jArr[0];
            while (i2 < 4 && jArr[i2] != 0) {
                i2++;
            }
            int i3 = 1;
            float f4 = 0.0f;
            float f5 = 0.0f;
            while (i3 < i2) {
                int i4 = (int) (jArr[i3] - j);
                if (i4 == 0) {
                    fArr = fArr2;
                } else {
                    float f6 = i4;
                    float f7 = (fArr2[i3] - f2) / f6;
                    fArr = fArr2;
                    float f8 = i;
                    float f9 = f7 * f8;
                    f4 = f4 == 0.0f ? f9 : (f4 + f9) * 0.5f;
                    float f10 = ((fArr3[i3] - f3) / f6) * f8;
                    f5 = f5 == 0.0f ? f10 : (f5 + f10) * 0.5f;
                }
                i3++;
                fArr2 = fArr;
            }
            if (f4 < 0.0f) {
                min = Math.max(f4, -f);
            } else {
                min = Math.min(f4, f);
            }
            this.mXVelocity = min;
            if (f5 < 0.0f) {
                min2 = Math.max(f5, -f);
            } else {
                min2 = Math.min(f5, f);
            }
            this.mYVelocity = min2;
        }

        public float getXVelocity() {
            return this.mXVelocity;
        }

        public float getYVelocity() {
            return this.mYVelocity;
        }
    }
}
