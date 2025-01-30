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
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NumPadKey extends ViewGroup implements NumPadAnimationListener {
    public static String[] sKlondike;
    public final int mDigit;
    public TextView mDigitText;
    public TextView mKlondikeText;
    public final ViewOnClickListenerC08261 mListener;
    public final PowerManager mPM;
    public PasswordTextView mTextView;
    public int mTextViewResId;

    public NumPadKey(Context context) {
        this(context, null);
    }

    public void doHapticKeyClick() {
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
                View findViewById;
                NumPadKey numPadKey = NumPadKey.this;
                if (numPadKey.mTextView == null && numPadKey.mTextViewResId > 0 && (findViewById = numPadKey.getRootView().findViewById(NumPadKey.this.mTextViewResId)) != null && (findViewById instanceof PasswordTextView)) {
                    NumPadKey.this.mTextView = (PasswordTextView) findViewById;
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.NumPadKey, i, i2);
        try {
            int i3 = obtainStyledAttributes.getInt(0, -1);
            this.mDigit = i3;
            this.mTextViewResId = obtainStyledAttributes.getResourceId(1, 0);
            obtainStyledAttributes.recycle();
            setOnClickListener(r1);
            setOnHoverListener(new LiftToActivateListener((AccessibilityManager) context.getSystemService("accessibility")));
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
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
