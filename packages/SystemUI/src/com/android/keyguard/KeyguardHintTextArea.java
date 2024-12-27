package com.android.keyguard;

import android.app.ActivityManager;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.RelativeLayout;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.widget.SystemUITextView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardHintTextArea extends RelativeLayout {
    public SystemUITextView mHintText;
    public final LockPatternUtils mLockPatternUtils;
    public String mPasswordHintText;
    public SystemUITextView mShowHintText;
    public final PathInterpolator mSineOut33;

    public KeyguardHintTextArea(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mHintText = (SystemUITextView) findViewById(R.id.hint_text);
        this.mShowHintText = (SystemUITextView) findViewById(R.id.show_hint_text);
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.kg_password_hint));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        this.mHintText.setMaxFontScale(1.1f);
        this.mHintText.setText(spannableString);
        this.mPasswordHintText = this.mLockPatternUtils.getPasswordHint(ActivityManager.getCurrentUser());
        this.mShowHintText.setMaxFontScale(1.1f);
        this.mShowHintText.setText(getResources().getString(R.string.kg_password_hint_show, this.mPasswordHintText));
        final int i = 0;
        this.mHintText.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardHintTextArea f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                final KeyguardHintTextArea keyguardHintTextArea = this.f$0;
                switch (i2) {
                    case 0:
                        keyguardHintTextArea.mShowHintText.setSelected(true);
                        final int i3 = 0;
                        keyguardHintTextArea.mHintText.animate().alpha(0.0f).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i4 = i3;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i4) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        keyguardHintTextArea.mShowHintText.setAlpha(0.0f);
                        final int i4 = 1;
                        keyguardHintTextArea.mShowHintText.animate().alpha(1.0f).setStartDelay(100L).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i42 = i4;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i42) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_SHOW_HINT);
                        break;
                    default:
                        keyguardHintTextArea.mShowHintText.setSelected(false);
                        final int i5 = 2;
                        keyguardHintTextArea.mShowHintText.animate().alpha(0.0f).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i42 = i5;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i42) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        keyguardHintTextArea.mHintText.setAlpha(0.0f);
                        final int i6 = 3;
                        keyguardHintTextArea.mHintText.animate().alpha(1.0f).setStartDelay(100L).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i42 = i6;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i42) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mShowHintText.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardHintTextArea f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                final KeyguardHintTextArea keyguardHintTextArea = this.f$0;
                switch (i22) {
                    case 0:
                        keyguardHintTextArea.mShowHintText.setSelected(true);
                        final int i3 = 0;
                        keyguardHintTextArea.mHintText.animate().alpha(0.0f).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i42 = i3;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i42) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        keyguardHintTextArea.mShowHintText.setAlpha(0.0f);
                        final int i4 = 1;
                        keyguardHintTextArea.mShowHintText.animate().alpha(1.0f).setStartDelay(100L).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i42 = i4;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i42) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_SHOW_HINT);
                        break;
                    default:
                        keyguardHintTextArea.mShowHintText.setSelected(false);
                        final int i5 = 2;
                        keyguardHintTextArea.mShowHintText.animate().alpha(0.0f).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i42 = i5;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i42) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        keyguardHintTextArea.mHintText.setAlpha(0.0f);
                        final int i6 = 3;
                        keyguardHintTextArea.mHintText.animate().alpha(1.0f).setStartDelay(100L).setDuration(233L).setInterpolator(keyguardHintTextArea.mSineOut33).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardHintTextArea$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i42 = i6;
                                KeyguardHintTextArea keyguardHintTextArea2 = keyguardHintTextArea;
                                switch (i42) {
                                    case 0:
                                        keyguardHintTextArea2.mHintText.setVisibility(8);
                                        break;
                                    case 1:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(0);
                                        break;
                                    case 2:
                                        keyguardHintTextArea2.mShowHintText.setVisibility(8);
                                        break;
                                    default:
                                        keyguardHintTextArea2.mHintText.setVisibility(0);
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        if (!DeviceType.isTablet()) {
            int rotation = ((RelativeLayout) this).mContext.getResources().getConfiguration().windowConfiguration.getRotation();
            if (i == 0 && (rotation == 1 || rotation == 3)) {
                i = 8;
            }
        }
        super.setVisibility(i);
    }

    public final void updateHintButton() {
        this.mHintText.setAlpha(1.0f);
        this.mHintText.setVisibility(0);
        this.mShowHintText.setVisibility(8);
    }

    public KeyguardHintTextArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPasswordHintText = null;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mSineOut33 = new PathInterpolator(0.17f, 0.17f, 0.67f, 1.0f);
    }
}
