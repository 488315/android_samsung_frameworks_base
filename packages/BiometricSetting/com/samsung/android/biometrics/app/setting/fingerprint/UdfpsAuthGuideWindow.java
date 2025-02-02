package com.samsung.android.biometrics.app.setting.fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SettingHelper;
import com.samsung.android.biometrics.app.setting.Utils;

/* loaded from: classes.dex */
public final class UdfpsAuthGuideWindow extends FocusableWindow implements View.OnKeyListener {
    private View mBottomView;
    private final UdfpsWindowCallback mCallback;
    private final DisplayStateManager mDisplayStateManager;
    private ImageView mGuideImage;
    private String mGuideString;
    private TextView mGuideText;
    private boolean mKeyDownPressed;
    private LinearLayout mLayout;
    private final UdfpsInfo mSensorInfo;

    @SuppressLint({"InflateParams"})
    public UdfpsAuthGuideWindow(Context context, DisplayStateManager displayStateManager, UdfpsWindowCallback udfpsWindowCallback, Pair<CharSequence, Drawable> pair, UdfpsInfo udfpsInfo) {
        super(context);
        this.mCallback = udfpsWindowCallback;
        this.mSensorInfo = udfpsInfo;
        this.mDisplayStateManager = displayStateManager;
        try {
            View inflate = getLayoutInflater().inflate(R.layout.sem_fingerprint_bg_view, (ViewGroup) null);
            this.mBaseView = inflate;
            this.mLayout = (LinearLayout) inflate.findViewById(R.id.sem_fingerprint_bg_layout);
            this.mBottomView = this.mBaseView.findViewById(R.id.sem_fingerprint_bg_bottom_view);
            this.mGuideString = getContext().getString(R.string.sem_fingerprint_bg_ready_description);
            ((TextView) this.mBaseView.findViewById(R.id.sem_fingerprint_bg_title)).setText(R.string.sem_fingerprint_bg_title);
            TextView textView = (TextView) this.mBaseView.findViewById(R.id.sem_fingerprint_bg_guide_text);
            this.mGuideText = textView;
            textView.setText(R.string.sem_fingerprint_bg_ready_description);
            ImageView imageView = (ImageView) this.mBaseView.findViewById(R.id.sem_fingerprint_bg_error_image);
            this.mGuideImage = imageView;
            imageView.setVisibility(4);
            if (!TextUtils.isEmpty((CharSequence) pair.first)) {
                ((TextView) this.mBaseView.findViewById(R.id.sem_fingerprint_app_title)).setText((CharSequence) pair.first);
            }
            if (pair.second != null) {
                ImageView imageView2 = (ImageView) this.mBaseView.findViewById(R.id.sem_fingerprint_app_icon);
                imageView2.setImageDrawable((Drawable) pair.second);
                imageView2.setVisibility(0);
            }
            Button button = (Button) this.mBaseView.findViewById(R.id.sem_fingerprint_bg_cancel_button);
            button.setBackgroundResource(R.drawable.sem_cancel_button_shape);
            button.setText(android.R.string.cancel);
            Utils.setMaxTextScaleSize(button, R.dimen.biometric_prompt_verification_negative_text_size);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthGuideWindow$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UdfpsAuthGuideWindow.this.mCallback.onUserCancel(3);
                }
            });
            this.mBaseView.setOnKeyListener(this);
            if (udfpsInfo.isNaviBarHide() && !SettingHelper.isNavigationBarHidden(getContext())) {
                this.mBaseView.setSystemUiVisibility(18874368);
            }
            this.mBaseView.setFocusableInTouchMode(true);
            this.mBaseView.requestFocus();
        } catch (Exception e) {
            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("UdfpsBackgroundGuideWindow: "), "BSS_SysUiWindow.B");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0032, code lost:
    
        if (r9 != 3) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateLayout(int i) {
        if (this.mLayout == null || this.mBottomView == null) {
            return;
        }
        Point maximumWindowSize = Utils.getMaximumWindowSize(getContext());
        DisplayMetrics displayMetrics = Utils.getDisplayMetrics(getContext());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBottomView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mLayout.getLayoutParams();
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                }
            }
            layoutParams.height = Math.round((float) (16.0d * displayMetrics.density));
            layoutParams2.width = (int) (maximumWindowSize.x * 0.5056d);
            this.mBottomView.setLayoutParams(layoutParams);
            this.mLayout.setLayoutParams(layoutParams2);
        }
        layoutParams.height = (this.mSensorInfo.getImageSize() / 2) + (this.mSensorInfo.getAreaWidth() / 2) + this.mSensorInfo.getMarginBottom();
        layoutParams2.width = -1;
        this.mBottomView.setLayoutParams(layoutParams);
        this.mLayout.setLayoutParams(layoutParams2);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2618, android.R.drawable.pointer_wait_vector_68, -3);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.dimAmount = 0.7f;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsIgnoringVisibility(true);
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        layoutParams.setTitle("FP BG" + hashCode());
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.B";
    }

    public final void init() {
        updateLayout(this.mDisplayStateManager.getCurrentRotation());
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {
        updateLayout(this.mDisplayStateManager.getCurrentRotation());
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onFocusLost() {
        this.mCallback.onUserCancel(1);
    }

    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 0 && !this.mKeyDownPressed) {
            this.mKeyDownPressed = true;
        } else if (keyEvent.getAction() == 0) {
            this.mKeyDownPressed = false;
        } else if (keyEvent.getAction() == 1 && this.mKeyDownPressed) {
            this.mKeyDownPressed = false;
            this.mCallback.onUserCancel(2);
        }
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onRotationInfoChanged(int i) {
        updateLayout(i);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onSystemDialogClosed() {
        this.mCallback.onUserCancel(4);
    }

    public final void resetMessage() {
        TextView textView = this.mGuideText;
        if (textView != null) {
            textView.setText(this.mGuideString);
        }
        ImageView imageView = this.mGuideImage;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    public final void showHelpMessage(int i, String str) {
        ImageView imageView = this.mGuideImage;
        if (imageView != null) {
            if (i == 0) {
                imageView.setVisibility(4);
            } else {
                imageView.setVisibility(0);
                ImageView imageView2 = this.mGuideImage;
                if (imageView2 != null) {
                    if (i == -1) {
                        imageView2.setImageResource(R.drawable.sem_fingerprint_no_match);
                    } else if (i == 5) {
                        imageView2.setImageResource(R.drawable.sem_fingerprint_error_timeout);
                    } else if (i == 1003) {
                        imageView2.setImageResource(R.drawable.sem_fingerprint_error_press_long);
                    } else if (i == 1 || i == 2) {
                        imageView2.setImageResource(R.drawable.sem_fingerprint_error_move);
                    } else if (i == 3) {
                        imageView2.setImageResource(R.drawable.sem_fingerprint_error_wipe);
                    }
                }
            }
        }
        if (this.mGuideText == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.mGuideText.setText(str);
    }
}
