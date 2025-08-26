package com.android.keyguard;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUITextView;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class SecNumPadKey extends NumPadKey {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mAccessibilityDelegate;
    public ImageView mDigitImage;
    public boolean mIsImagePinLock;
    public final AnonymousClass2 mListener;
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

    public final void updateViewStyle() {
        Typeface typefaceCreate;
        boolean z = true;
        boolean z2 = this.mSettingsHelper.isOpenThemeLook() && getResources().getBoolean(R.bool.theme_use_image_pinlock);
        this.mIsImagePinLock = z2;
        int i = z2 ? R.layout.keyguard_image_pad_key : DeviceType.isTablet() ? R.layout.keyguard_sec_num_pad_key_tablet : R.layout.keyguard_sec_num_pad_key;
        removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        Objects.requireNonNull(layoutInflater);
        layoutInflater.inflate(i, (ViewGroup) this, true);
        boolean z3 = this.mIsImagePinLock;
        int i2 = R.drawable.keyguard_pin_background;
        if (z3) {
            this.mDigitImage = (ImageView) findViewById(R.id.digit_image);
            boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY);
            int i3 = R.drawable.pin_number_image_1_black;
            if (!zIsWhiteKeyguardWallpaper) {
                z = false;
            } else if (BitmapFactory.decodeResource(getResources(), R.drawable.pin_number_image_1_black) == null) {
                Log.d("SecNumPadKey", "return null - bitmap is null");
                z = false;
            }
            switch (this.mDigit) {
                case 0:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_0_black : R.drawable.pin_number_image_0);
                    break;
                case 1:
                    ImageView imageView = this.mDigitImage;
                    if (!z) {
                        i3 = R.drawable.pin_number_image_1;
                    }
                    imageView.setImageResource(i3);
                    break;
                case 2:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_2_black : R.drawable.pin_number_image_2);
                    break;
                case 3:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_3_black : R.drawable.pin_number_image_3);
                    break;
                case 4:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_4_black : R.drawable.pin_number_image_4);
                    break;
                case 5:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_5_black : R.drawable.pin_number_image_5);
                    break;
                case 6:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_6_black : R.drawable.pin_number_image_6);
                    break;
                case 7:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_7_black : R.drawable.pin_number_image_7);
                    break;
                case 8:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_8_black : R.drawable.pin_number_image_8);
                    break;
                case 9:
                    this.mDigitImage.setImageResource(z ? R.drawable.pin_number_image_9_black : R.drawable.pin_number_image_9);
                    break;
            }
            setContentDescription(Integer.toString(this.mDigit));
            try {
                setBackground(getContext().getDrawable(zIsWhiteKeyguardWallpaper ? R.drawable.pin_number_bg_image_black : R.drawable.pin_number_bg_image));
                return;
            } catch (Exception unused) {
                Context context = getContext();
                if (zIsWhiteKeyguardWallpaper) {
                    i2 = R.drawable.keyguard_pin_background_whitebg;
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
            String openThemeNumericFont = this.mSettingsHelper.isOpenThemeLook() ? this.mSettingsHelper.getOpenThemeNumericFont() : null;
            if (TextUtils.isEmpty(openThemeNumericFont) || !new File(openThemeNumericFont).exists()) {
                typefaceCreate = Typeface.create(Typeface.create(getContext().getString(R.string.pinlock_numeric_font_family), 0), 400, false);
                if (!TextUtils.isEmpty(openThemeNumericFont)) {
                    Log.e("SecNumPadKey", openThemeNumericFont + " does not exist. Use default font.");
                }
            } else {
                int i6 = SecurityUtils.sPINContainerBottomMargin;
                synchronized (SecurityUtils.class) {
                    HashMap map = SecurityUtils.sTypefaceMap;
                    typefaceCreate = (Typeface) map.get(openThemeNumericFont);
                    if (typefaceCreate == null) {
                        typefaceCreate = Typeface.createFromFile(openThemeNumericFont);
                        map.put(openThemeNumericFont, typefaceCreate);
                    }
                }
            }
            if (typefaceCreate != null) {
                systemUITextView.setTypeface(typefaceCreate);
            }
        }
        getContext();
        boolean zIsWhiteKeyguardWallpaper2 = WallpaperUtils.isWhiteKeyguardWallpaper(512L, false);
        if (this.mSettingsHelper.isReduceTransparencyEnabled()) {
            this.mRecoilDrawable = (SeslRecoilDrawable) getContext().getDrawable(zIsWhiteKeyguardWallpaper2 ? R.drawable.keyguard_pin_background_whitebg_reduce_transparency : R.drawable.keyguard_pin_background_reduce_transparency);
        } else {
            Context context2 = getContext();
            if (zIsWhiteKeyguardWallpaper2) {
                i2 = R.drawable.keyguard_pin_background_whitebg;
            }
            this.mRecoilDrawable = (SeslRecoilDrawable) context2.getDrawable(i2);
        }
        SeslRecoilDrawable seslRecoilDrawable = this.mRecoilDrawable;
        if (seslRecoilDrawable != null) {
            setBackground(seslRecoilDrawable);
        }
    }

    public SecNumPadKey(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecNumPadKey(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.layout.keyguard_sec_num_pad_key);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.SecNumPadKey$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.view.View$OnClickListener, com.android.keyguard.SecNumPadKey$2] */
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
                final SecNumPadKey secNumPadKey = this.f$0;
                Log.d("SecNumPadKey", "onChanged " + uri);
                secNumPadKey.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.SecNumPadKey$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecNumPadKey secNumPadKey2 = secNumPadKey;
                        int i4 = SecNumPadKey.$r8$clinit;
                        secNumPadKey2.updateViewStyle();
                    }
                });
            }
        };
        ?? r2 = new View.OnClickListener() { // from class: com.android.keyguard.SecNumPadKey.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                View viewFindViewById;
                SecNumPadKey secNumPadKey = SecNumPadKey.this;
                if (secNumPadKey.mTextView == null && secNumPadKey.mTextViewResId > 0) {
                    View viewFindViewWithTag = secNumPadKey.getRootView().findViewWithTag("security_container");
                    if (viewFindViewWithTag != null) {
                        viewFindViewById = viewFindViewWithTag.findViewById(SecNumPadKey.this.mTextViewResId);
                    } else {
                        Log.w("SecNumPadKey", "!@<security_container> tag not found! Search from rootView!");
                        viewFindViewById = SecNumPadKey.this.getRootView().findViewById(SecNumPadKey.this.mTextViewResId);
                    }
                    if (viewFindViewById != null && (viewFindViewById instanceof PasswordTextView)) {
                        SecNumPadKey.this.mTextView = (PasswordTextView) viewFindViewById;
                    } else if (viewFindViewById != null) {
                        Log.e("SecNumPadKey", "!@mTextView is NOT PasswordTextView.  v = " + viewFindViewById);
                    } else {
                        Log.e("SecNumPadKey", "!@mTextView not found.");
                    }
                }
                PasswordTextView passwordTextView = SecNumPadKey.this.mTextView;
                if (passwordTextView != null && passwordTextView.isEnabled()) {
                    SecNumPadKey secNumPadKey2 = SecNumPadKey.this;
                    secNumPadKey2.mTextView.append(Character.forDigit(secNumPadKey2.mDigit, 10));
                } else if (SecNumPadKey.this.mTextView != null) {
                    Log.w("SecNumPadKey", "!@mTextView.isEnabled() = false");
                }
                SecNumPadKey.this.mPM.userActivity(SystemClock.uptimeMillis(), false);
            }
        };
        this.mListener = r2;
        setOnClickListener(r2);
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
        setOnKeyListener(new View.OnKeyListener(this) { // from class: com.android.keyguard.SecNumPadKey.3
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
