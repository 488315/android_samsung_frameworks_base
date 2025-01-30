package com.android.keyguard;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUITextView;
import com.android.systemui.widget.SystemUIWidgetUtil;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SecNumPadKey extends NumPadKey {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C08421 mAccessibilityDelegate;
    public ImageView mDigitImage;
    public boolean mIsImagePinLock;
    public final SecNumPadKey$$ExternalSyntheticLambda0 mOnSettingsChangedCallback;
    public RippleDrawable mRipple;
    public final SettingsHelper mSettingsHelper;

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
        this.mSettingsHelper.registerCallback(this.mOnSettingsChangedCallback, Settings.System.getUriFor("accessibility_reduce_transparency"));
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

    public final void updateViewStyle() {
        Typeface create;
        boolean z;
        boolean z2 = false;
        boolean z3 = this.mSettingsHelper.isOpenThemeLook() && getResources().getBoolean(R.bool.theme_use_image_pinlock);
        this.mIsImagePinLock = z3;
        int i = z3 ? R.layout.keyguard_image_pad_key : DeviceType.isTablet() ? R.layout.keyguard_sec_num_pad_key_tablet : R.layout.keyguard_sec_num_pad_key;
        removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        Objects.requireNonNull(layoutInflater);
        layoutInflater.inflate(i, (ViewGroup) this, true);
        boolean z4 = this.mIsImagePinLock;
        int i2 = R.drawable.keyguard_pin_background_whitebg;
        if (z4) {
            this.mDigitImage = (ImageView) findViewById(R.id.digit_image);
            boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
            int i3 = R.drawable.pin_number_image_1_black;
            if (isWhiteKeyguardWallpaper) {
                if (BitmapFactory.decodeResource(getResources(), R.drawable.pin_number_image_1_black) == null) {
                    Log.d("SecNumPadKey", "return null - bitmap is null");
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    z2 = true;
                }
            }
            switch (this.mDigit) {
                case 0:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_0_black : R.drawable.pin_number_image_0);
                    break;
                case 1:
                    ImageView imageView = this.mDigitImage;
                    if (!z2) {
                        i3 = R.drawable.pin_number_image_1;
                    }
                    imageView.setImageResource(i3);
                    break;
                case 2:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_2_black : R.drawable.pin_number_image_2);
                    break;
                case 3:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_3_black : R.drawable.pin_number_image_3);
                    break;
                case 4:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_4_black : R.drawable.pin_number_image_4);
                    break;
                case 5:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_5_black : R.drawable.pin_number_image_5);
                    break;
                case 6:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_6_black : R.drawable.pin_number_image_6);
                    break;
                case 7:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_7_black : R.drawable.pin_number_image_7);
                    break;
                case 8:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_8_black : R.drawable.pin_number_image_8);
                    break;
                case 9:
                    this.mDigitImage.setImageResource(z2 ? R.drawable.pin_number_image_9_black : R.drawable.pin_number_image_9);
                    break;
            }
            setContentDescription(Integer.toString(this.mDigit));
            try {
                setBackground(getContext().getDrawable(isWhiteKeyguardWallpaper ? R.drawable.pin_number_bg_image_black : R.drawable.pin_number_bg_image));
                return;
            } catch (Exception unused) {
                Context context = getContext();
                if (!isWhiteKeyguardWallpaper) {
                    i2 = R.drawable.keyguard_pin_background;
                }
                setBackground(context.getDrawable(i2));
                return;
            }
        }
        TextView textView = (TextView) findViewById(R.id.digit_text);
        this.mDigitText = textView;
        textView.setText(Integer.toString(this.mDigit));
        TextView textView2 = (TextView) findViewById(R.id.klondike_text);
        this.mKlondikeText = textView2;
        int i4 = this.mDigit;
        if (i4 > 0) {
            if (NumPadKey.sKlondike == null) {
                NumPadKey.sKlondike = getResources().getStringArray(R.array.lockscreen_num_pad_klondike);
            }
            String[] strArr = NumPadKey.sKlondike;
            int length = strArr.length;
            int i5 = this.mDigit;
            if (length > i5) {
                String str = strArr[i5];
                if (str.length() > 0) {
                    this.mKlondikeText.setText(str);
                } else {
                    this.mKlondikeText.setVisibility(4);
                }
            }
        } else if (i4 == 0) {
            textView2.setVisibility(8);
        }
        setContentDescription(this.mDigitText.getText().toString());
        TextView textView3 = this.mDigitText;
        if (textView3 instanceof SystemUITextView) {
            SystemUITextView systemUITextView = (SystemUITextView) textView3;
            String stringValue = this.mSettingsHelper.isOpenThemeLook() ? this.mSettingsHelper.mItemLists.get("theme_font_numeric").getStringValue() : null;
            if (TextUtils.isEmpty(stringValue) || !new File(stringValue).exists()) {
                create = Typeface.create(Typeface.create(getContext().getString(R.string.pinlock_numeric_font_family), 0), 400, false);
                if (!TextUtils.isEmpty(stringValue)) {
                    Log.e("NumPadKey", stringValue + " does not exist. Use default font.");
                }
            } else {
                create = Typeface.createFromFile(stringValue);
            }
            if (create != null) {
                systemUITextView.setTypeface(create);
            }
        }
        boolean needsBlackComponent = SystemUIWidgetUtil.needsBlackComponent(getContext(), SystemUIWidgetUtil.convertFlag("background"), true);
        if (this.mSettingsHelper.isReduceTransparencyEnabled()) {
            this.mRipple = (RippleDrawable) getContext().getDrawable(needsBlackComponent ? R.drawable.keyguard_pin_background_whitebg_reduce_transparency : R.drawable.keyguard_pin_background_reduce_transparency);
        } else {
            Context context2 = getContext();
            if (!needsBlackComponent) {
                i2 = R.drawable.keyguard_pin_background;
            }
            this.mRipple = (RippleDrawable) context2.getDrawable(i2);
        }
        RippleDrawable rippleDrawable = this.mRipple;
        if (rippleDrawable != null) {
            setBackground(rippleDrawable);
        }
    }

    public SecNumPadKey(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecNumPadKey(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.layout.keyguard_sec_num_pad_key);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.SecNumPadKey$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.keyguard.SecNumPadKey$$ExternalSyntheticLambda0] */
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
                secNumPadKey.getClass();
                Log.d("SecNumPadKey", "onChanged " + uri);
                ((Executor) Dependency.get(Dependency.MAIN_EXECUTOR)).execute(new Runnable() { // from class: com.android.keyguard.SecNumPadKey$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecNumPadKey.this.updateViewStyle();
                    }
                });
            }
        };
        setOnHoverListener(null);
        if (this.mDigit == 0) {
            this.mKlondikeText.setVisibility(8);
        }
        if (LsRune.SECURITY_SPR_USIM_TEXT) {
            setContentDescription(context.getString(R.string.keyguard_accessibility_dot));
        }
        RippleDrawable rippleDrawable = (RippleDrawable) context.getDrawable(R.drawable.keyguard_pin_background);
        this.mRipple = rippleDrawable;
        setBackground(rippleDrawable);
        setClipChildren(false);
        setClipToPadding(false);
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
    }
}
