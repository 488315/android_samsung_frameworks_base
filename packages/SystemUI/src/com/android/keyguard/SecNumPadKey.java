package com.android.keyguard;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SecNumPadKey extends NumPadKey {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mAccessibilityDelegate;
    public ImageView mDigitImage;
    public boolean mIsImagePinLock;
    public final Executor mMainExecutor;
    private final SettingsHelper.OnChangedCallback mOnSettingsChangedCallback;
    public SeslRecoilDrawable mRecoilDrawable;
    private final SettingsHelper mSettingsHelper;

    public SecNumPadKey(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.NumPadKey
    public final void doHapticKeyClick() {
        performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(1), (LsRune.SECURITY_HAPTIC_FEEDBACK_ON_DC_MOTOR && this.mSettingsHelper.isHapticFeedbackEnabled()) ? 3 : 1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSettingsHelper.registerCallback(this.mOnSettingsChangedCallback, Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mSettingsHelper.unregisterCallback(this.mOnSettingsChangedCallback);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    @Override // com.android.keyguard.NumPadKey, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!LsRune.SECURITY_OPEN_THEME || !this.mIsImagePinLock) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int measuredHeight = this.mDigitImage.getMeasuredHeight();
        int height = (getHeight() / 2) - (measuredHeight / 2);
        int width = (getWidth() / 2) - (this.mDigitImage.getMeasuredWidth() / 2);
        ImageView imageView = this.mDigitImage;
        imageView.layout(width, height, imageView.getMeasuredWidth() + width, measuredHeight + height);
    }

    public void updateDigitTextSize() {
        this.mDigitText.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_num_pad_font_size));
    }

    public void updateKlondikeTextSize() {
        this.mKlondikeText.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_klondike_font_size));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0128  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateViewStyle() {
        /*
            Method dump skipped, instructions count: 634
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.SecNumPadKey.updateViewStyle():void");
    }

    public SecNumPadKey(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecNumPadKey(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.layout.keyguard_sec_num_pad_key);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.SecNumPadKey$1] */
    private SecNumPadKey(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mAccessibilityDelegate = new View.AccessibilityDelegate(this) { // from class: com.android.keyguard.SecNumPadKey.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setTextEntryKey(true);
            }
        };
        this.mOnSettingsChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.SecNumPadKey$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                int i3 = SecNumPadKey.$r8$clinit;
                final SecNumPadKey secNumPadKey = SecNumPadKey.this;
                Log.d("SecNumPadKey", "onChanged " + uri);
                secNumPadKey.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.SecNumPadKey$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecNumPadKey secNumPadKey2 = SecNumPadKey.this;
                        int i4 = SecNumPadKey.$r8$clinit;
                        secNumPadKey2.updateViewStyle();
                    }
                });
            }
        };
        setOnHoverListener(null);
        if (this.mDigit == 0) {
            this.mKlondikeText.setVisibility(8);
        }
        SeslRecoilDrawable seslRecoilDrawable = (SeslRecoilDrawable) context.getDrawable(R.drawable.keyguard_pin_background);
        this.mRecoilDrawable = seslRecoilDrawable;
        setBackground(seslRecoilDrawable);
        setClipChildren(false);
        setClipToPadding(false);
        this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.mMainExecutor = context.getMainExecutor();
        setOnKeyListener(new View.OnKeyListener(this) { // from class: com.android.keyguard.SecNumPadKey.2
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i3, KeyEvent keyEvent) {
                if (!LsRune.SECURITY_BOUNCER_WINDOW || !KeyEvent.isConfirmKey(keyEvent.getKeyCode())) {
                    return false;
                }
                view.performClick();
                return false;
            }
        });
    }
}
