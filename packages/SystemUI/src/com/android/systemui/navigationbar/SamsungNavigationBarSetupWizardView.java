package com.android.systemui.navigationbar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.systemui.R;
import com.android.systemui.shared.navigationbar.SamsungKeyButtonRipple;

public final class SamsungNavigationBarSetupWizardView extends FrameLayout {
    public ImageView a11yBtn;
    public NavigationBarSetupWizardButton a11yLayout;
    public SamsungKeyButtonRipple a11yRipple;
    public SamsungKeyButtonRipple backAltRipple;
    public SamsungKeyButtonRipple backRipple;
    public int hints;
    public ImageView imeBtn;
    public NavigationBarSetupWizardButton imeBtnLayout;
    public final LayoutInflater layoutInflater;
    public ImageView prevBtn;
    public NavigationBarSetupWizardButton prevBtnLayout;
    public FrameLayout setupWizardView;

    public final class NavigationBarSetupWizardButton extends LinearLayout {
        public NavigationBarSetupWizardButton(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        public final CharSequence getAccessibilityClassName() {
            return "android.widget.Button";
        }
    }

    public SamsungNavigationBarSetupWizardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public static void sendEvent$default(SamsungNavigationBarSetupWizardView samsungNavigationBarSetupWizardView, int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        samsungNavigationBarSetupWizardView.getClass();
        InputManager.getInstance().injectInputEvent(new KeyEvent(uptimeMillis, uptimeMillis, i, 4, 0, 0, -1, 0, 72, 257), 0);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        updateResources();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        removeAllViews();
        FrameLayout frameLayout = (FrameLayout) this.layoutInflater.inflate(R.layout.samsung_navigation_setup_wizard_bar, (ViewGroup) this, false);
        this.setupWizardView = frameLayout;
        addView(frameLayout);
        FrameLayout frameLayout2 = this.setupWizardView;
        if (frameLayout2 == null) {
            frameLayout2 = null;
        }
        this.prevBtnLayout = (NavigationBarSetupWizardButton) frameLayout2.requireViewById(R.id.prev_btn_area);
        Context context = getContext();
        NavigationBarSetupWizardButton navigationBarSetupWizardButton = this.prevBtnLayout;
        if (navigationBarSetupWizardButton == null) {
            navigationBarSetupWizardButton = null;
        }
        SamsungKeyButtonRipple samsungKeyButtonRipple = new SamsungKeyButtonRipple(context, navigationBarSetupWizardButton, R.dimen.key_button_ripple_max_width, 1.0f);
        this.backRipple = samsungKeyButtonRipple;
        NavigationBarSetupWizardButton navigationBarSetupWizardButton2 = this.prevBtnLayout;
        if (navigationBarSetupWizardButton2 == null) {
            navigationBarSetupWizardButton2 = null;
        }
        navigationBarSetupWizardButton2.setBackground(samsungKeyButtonRipple);
        FrameLayout frameLayout3 = this.setupWizardView;
        if (frameLayout3 == null) {
            frameLayout3 = null;
        }
        this.imeBtnLayout = (NavigationBarSetupWizardButton) frameLayout3.requireViewById(R.id.prev_ime_area);
        Context context2 = getContext();
        NavigationBarSetupWizardButton navigationBarSetupWizardButton3 = this.imeBtnLayout;
        if (navigationBarSetupWizardButton3 == null) {
            navigationBarSetupWizardButton3 = null;
        }
        SamsungKeyButtonRipple samsungKeyButtonRipple2 = new SamsungKeyButtonRipple(context2, navigationBarSetupWizardButton3, R.dimen.key_button_ripple_max_width, 1.0f);
        this.backAltRipple = samsungKeyButtonRipple2;
        NavigationBarSetupWizardButton navigationBarSetupWizardButton4 = this.imeBtnLayout;
        if (navigationBarSetupWizardButton4 == null) {
            navigationBarSetupWizardButton4 = null;
        }
        navigationBarSetupWizardButton4.setBackground(samsungKeyButtonRipple2);
        FrameLayout frameLayout4 = this.setupWizardView;
        if (frameLayout4 == null) {
            frameLayout4 = null;
        }
        this.a11yLayout = (NavigationBarSetupWizardButton) frameLayout4.requireViewById(R.id.a11y_area);
        Context context3 = getContext();
        NavigationBarSetupWizardButton navigationBarSetupWizardButton5 = this.a11yLayout;
        if (navigationBarSetupWizardButton5 == null) {
            navigationBarSetupWizardButton5 = null;
        }
        SamsungKeyButtonRipple samsungKeyButtonRipple3 = new SamsungKeyButtonRipple(context3, navigationBarSetupWizardButton5, R.dimen.key_button_ripple_max_width, 1.0f);
        this.a11yRipple = samsungKeyButtonRipple3;
        NavigationBarSetupWizardButton navigationBarSetupWizardButton6 = this.a11yLayout;
        if (navigationBarSetupWizardButton6 == null) {
            navigationBarSetupWizardButton6 = null;
        }
        navigationBarSetupWizardButton6.setBackground(samsungKeyButtonRipple3);
        FrameLayout frameLayout5 = this.setupWizardView;
        if (frameLayout5 == null) {
            frameLayout5 = null;
        }
        this.prevBtn = (ImageView) frameLayout5.requireViewById(R.id.prev_btn_arrow);
        FrameLayout frameLayout6 = this.setupWizardView;
        if (frameLayout6 == null) {
            frameLayout6 = null;
        }
        this.imeBtn = (ImageView) frameLayout6.requireViewById(R.id.ime_icon);
        FrameLayout frameLayout7 = this.setupWizardView;
        if (frameLayout7 == null) {
            frameLayout7 = null;
        }
        this.a11yBtn = (ImageView) frameLayout7.requireViewById(R.id.a11y_button);
        updateResources();
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.navigationbar.SamsungNavigationBarSetupWizardView$onFinishInflate$clickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SamsungNavigationBarSetupWizardView.sendEvent$default(SamsungNavigationBarSetupWizardView.this, 0);
                SamsungNavigationBarSetupWizardView.sendEvent$default(SamsungNavigationBarSetupWizardView.this, 1);
            }
        };
        NavigationBarSetupWizardButton navigationBarSetupWizardButton7 = this.prevBtnLayout;
        if (navigationBarSetupWizardButton7 == null) {
            navigationBarSetupWizardButton7 = null;
        }
        navigationBarSetupWizardButton7.setOnClickListener(onClickListener);
        NavigationBarSetupWizardButton navigationBarSetupWizardButton8 = this.imeBtnLayout;
        if (navigationBarSetupWizardButton8 == null) {
            navigationBarSetupWizardButton8 = null;
        }
        navigationBarSetupWizardButton8.setOnClickListener(onClickListener);
        NavigationBarSetupWizardButton navigationBarSetupWizardButton9 = this.a11yLayout;
        if (navigationBarSetupWizardButton9 == null) {
            navigationBarSetupWizardButton9 = null;
        }
        navigationBarSetupWizardButton9.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.navigationbar.SamsungNavigationBarSetupWizardView$onFinishInflate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Display display = view.getDisplay();
                ((AccessibilityManager) SamsungNavigationBarSetupWizardView.this.getContext().getSystemService(AccessibilityManager.class)).notifyAccessibilityButtonClicked(display != null ? display.getDisplayId() : 0);
            }
        });
        NavigationBarSetupWizardButton navigationBarSetupWizardButton10 = this.a11yLayout;
        (navigationBarSetupWizardButton10 != null ? navigationBarSetupWizardButton10 : null).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.navigationbar.SamsungNavigationBarSetupWizardView$onFinishInflate$2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                intent.addFlags(268468224);
                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                view.getContext().startActivityAsUser(intent, UserHandle.CURRENT);
                return true;
            }
        });
    }

    public final void updateResources() {
        ImageView imageView = this.prevBtn;
        if (imageView == null) {
            imageView = null;
        }
        imageView.setImageDrawable(getContext().getDrawable(R.drawable.ic_samsung_sysbar_back));
        ImageView imageView2 = this.imeBtn;
        if (imageView2 == null) {
            imageView2 = null;
        }
        imageView2.setImageDrawable(getContext().getDrawable(R.drawable.ic_samsung_sysbar_back_ime));
        ImageView imageView3 = this.a11yBtn;
        if (imageView3 == null) {
            imageView3 = null;
        }
        imageView3.setImageDrawable(getContext().getDrawable(R.drawable.ic_samsung_sysbar_accessibility_button));
        NavigationBarSetupWizardButton navigationBarSetupWizardButton = this.prevBtnLayout;
        if (navigationBarSetupWizardButton == null) {
            navigationBarSetupWizardButton = null;
        }
        navigationBarSetupWizardButton.setContentDescription(getContext().getResources().getText(R.string.samsung_accessibility_back));
        NavigationBarSetupWizardButton navigationBarSetupWizardButton2 = this.imeBtnLayout;
        if (navigationBarSetupWizardButton2 == null) {
            navigationBarSetupWizardButton2 = null;
        }
        navigationBarSetupWizardButton2.setContentDescription(getContext().getResources().getText(R.string.samsung_accessibility_back));
        NavigationBarSetupWizardButton navigationBarSetupWizardButton3 = this.a11yLayout;
        (navigationBarSetupWizardButton3 != null ? navigationBarSetupWizardButton3 : null).setContentDescription(getContext().getResources().getText(R.string.accessibility_accessibility_button));
    }
}
