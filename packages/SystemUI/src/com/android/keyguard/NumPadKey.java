package com.android.keyguard;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.res.R$styleable;

/* loaded from: classes.dex */
public class NumPadKey extends ViewGroup implements NumPadAnimationListener {
    public static String[] sKlondike;
    public BouncerHapticPlayer mBouncerHapticPlayer;
    public final int mDigit;
    public TextView mDigitText;
    public TextView mKlondikeText;
    public final AnonymousClass1 mListener;
    public final PowerManager mPM;
    public PasswordTextView mTextView;
    public int mTextViewResId;

    public NumPadKey(Context context) {
        this(context, null);
    }

    public void doHapticKeyClick() {
        BouncerHapticPlayer bouncerHapticPlayer = this.mBouncerHapticPlayer;
        if (bouncerHapticPlayer != null) {
            bouncerHapticPlayer.getClass();
        }
        performHapticFeedback(1, 1);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        int i = configuration.orientation;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setTextEntryKey(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredHeight = this.mDigitText.getMeasuredHeight();
        int measuredHeight2 = this.mKlondikeText.getMeasuredHeight();
        int height = (getHeight() / 2) - ((measuredHeight + measuredHeight2) / 2);
        int width = getWidth() / 2;
        int measuredWidth = width - (this.mDigitText.getMeasuredWidth() / 2);
        int i5 = measuredHeight + height;
        TextView textView = this.mDigitText;
        textView.layout(measuredWidth, height, textView.getMeasuredWidth() + measuredWidth, i5);
        int i6 = (int) (i5 - (measuredHeight2 * 0.35f));
        int measuredWidth2 = width - (this.mKlondikeText.getMeasuredWidth() / 2);
        TextView textView2 = this.mKlondikeText;
        textView2.layout(measuredWidth2, i6, textView2.getMeasuredWidth() + measuredWidth2, measuredHeight2 + i6);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        measureChildren(i, i2);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            doHapticKeyClick();
        }
        return super.onTouchEvent(motionEvent);
    }

    public NumPadKey(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.numPadKeyStyle);
    }

    public NumPadKey(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.layout.keyguard_num_pad_key);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.View$OnClickListener, com.android.keyguard.NumPadKey$1] */
    public NumPadKey(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mDigit = -1;
        ?? r1 = new View.OnClickListener() { // from class: com.android.keyguard.NumPadKey.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                View viewFindViewById;
                NumPadKey numPadKey = NumPadKey.this;
                if (numPadKey.mTextView == null && numPadKey.mTextViewResId > 0 && (viewFindViewById = numPadKey.getRootView().findViewById(NumPadKey.this.mTextViewResId)) != null && (viewFindViewById instanceof PasswordTextView)) {
                    NumPadKey.this.mTextView = (PasswordTextView) viewFindViewById;
                }
                PasswordTextView passwordTextView = NumPadKey.this.mTextView;
                if (passwordTextView != null && passwordTextView.isEnabled()) {
                    NumPadKey numPadKey2 = NumPadKey.this;
                    numPadKey2.mTextView.append(Character.forDigit(numPadKey2.mDigit, 10));
                }
                NumPadKey.this.mPM.userActivity(SystemClock.uptimeMillis(), false);
            }
        };
        this.mListener = r1;
        setFocusable(true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.NumPadKey, i, i2);
        try {
            int i3 = typedArrayObtainStyledAttributes.getInt(0, -1);
            this.mDigit = i3;
            this.mTextViewResId = typedArrayObtainStyledAttributes.getResourceId(1, 0);
            typedArrayObtainStyledAttributes.recycle();
            setOnClickListener(r1);
            this.mPM = (PowerManager) ((ViewGroup) this).mContext.getSystemService("power");
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(i2, (ViewGroup) this, true);
            TextView textView = (TextView) findViewById(R.id.digit_text);
            this.mDigitText = textView;
            textView.setText(Integer.toString(i3));
            this.mKlondikeText = (TextView) findViewById(R.id.klondike_text);
            if (i3 >= 0) {
                if (sKlondike == null) {
                    sKlondike = getResources().getStringArray(R.array.lockscreen_num_pad_klondike);
                }
                String[] strArr = sKlondike;
                if (strArr != null && strArr.length > i3) {
                    String str = strArr[i3];
                    if (str.length() > 0) {
                        this.mKlondikeText.setText(str);
                    } else if (this.mKlondikeText.getVisibility() != 8) {
                        this.mKlondikeText.setVisibility(4);
                    }
                }
            }
            setContentDescription(this.mDigitText.getText().toString());
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }
}
