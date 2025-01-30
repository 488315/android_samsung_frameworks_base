package com.android.keyguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.widget.SystemUITextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardMessageArea extends SystemUITextView implements SecurityMessageDisplay {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final KeyguardMessageArea$$ExternalSyntheticLambda0 mClearMessageRunnable;
    public ViewGroup mContainer;
    public Handler mHandler;
    public boolean mIsDisabled;
    public boolean mIsVisible;
    public CharSequence mMessage;
    public final int mStyleResId;
    public long mTimeout;

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.keyguard.KeyguardMessageArea$$ExternalSyntheticLambda0] */
    public KeyguardMessageArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsDisabled = false;
        this.mTimeout = 3000L;
        this.mClearMessageRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardMessageArea$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardMessageArea keyguardMessageArea = KeyguardMessageArea.this;
                int i = KeyguardMessageArea.$r8$clinit;
                keyguardMessageArea.mMessage = null;
                keyguardMessageArea.update();
            }
        };
        setLayerType(2, null);
        if (attributeSet != null) {
            this.mStyleResId = attributeSet.getStyleAttribute();
        } else {
            this.mStyleResId = 2132017614;
        }
        onThemeChanged();
    }

    @Override // com.android.systemui.widget.SystemUITextView, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ViewGroup) getRootView().findViewById(R.id.keyguard_message_area_container);
    }

    public void onThemeChanged() {
        update();
    }

    public void setMessage(CharSequence charSequence, boolean z) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mMessage = null;
            update();
            return;
        }
        this.mMessage = charSequence;
        update();
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        if (!keyguardUpdateMonitor.is2StepVerification() || keyguardUpdateMonitor.getFingerprintAuthenticated(KeyguardUpdateMonitor.getCurrentUser())) {
            this.mHandler.removeCallbacks(this.mClearMessageRunnable);
            long j = this.mTimeout;
            if (j > 0) {
                this.mHandler.postDelayed(this.mClearMessageRunnable, j);
            }
        }
    }

    public final void update() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        if (this.mIsDisabled) {
            setVisibility(8);
        } else {
            final CharSequence charSequence = this.mMessage;
            this.mHandler.post(new Runnable() { // from class: com.android.keyguard.KeyguardMessageArea$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    KeyguardMessageArea keyguardMessageArea = KeyguardMessageArea.this;
                    CharSequence charSequence2 = charSequence;
                    int i2 = KeyguardMessageArea.$r8$clinit;
                    keyguardMessageArea.getClass();
                    if (TextUtils.isEmpty(charSequence2)) {
                        i = 8;
                        if (keyguardMessageArea.getVisibility() != 8) {
                            i = 4;
                        }
                    } else {
                        i = 0;
                    }
                    keyguardMessageArea.setVisibility(i);
                    keyguardMessageArea.setText(charSequence2);
                }
            });
        }
    }
}
