package com.android.systemui.qs.buttons;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import com.android.systemui.qs.customize.setting.SecQSSettingEditMainActivity;
import com.android.systemui.util.ShadowDelegateUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.TouchDelegateUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSEditButton extends FrameLayout implements QSButtonsContainer.CloseTooltipWindow {
    public final Context mContext;
    public ImageButton mEditButton;
    public View mEditButtonBadge;
    public View mEditContainer;
    public SecQSPanelController mQsPanelController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final QSTooltipWindow mTipWindow;
    public final int mToolTipString;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.buttons.QSEditButton$1 */
    public final class ViewOnClickListenerC21171 implements View.OnClickListener {
        public ViewOnClickListenerC21171() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            QSEditButton qSEditButton = QSEditButton.this;
            if (qSEditButton.mQsPanelController != null) {
                qSEditButton.postDelayed(new Runnable() { // from class: com.android.systemui.qs.buttons.QSEditButton$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final SecQSPanelController secQSPanelController = QSEditButton.this.mQsPanelController;
                        secQSPanelController.getClass();
                        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                            secQSPanelController.mResourcePicker.mCapturedBlurredBackground = secQSPanelController.mStatusBarWindow.findViewById(R.id.captured_blur_container).getBackground();
                        }
                        secQSPanelController.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.SecQSPanelController$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecQSPanelController secQSPanelController2 = SecQSPanelController.this;
                                secQSPanelController2.getClass();
                                Intent component = new Intent().setComponent(new ComponentName(secQSPanelController2.getContext(), (Class<?>) SecQSSettingEditMainActivity.class));
                                component.putExtra("user_starts", true);
                                secQSPanelController2.mActivityStarter.startActivity(component, true);
                            }
                        });
                    }
                }, 100L);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1017");
            }
        }
    }

    public QSEditButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = R.string.tooltip_quick_settings_edit;
    }

    @Override // com.android.systemui.qs.buttons.QSButtonsContainer.CloseTooltipWindow
    public final void closeTooltip() {
        this.mTipWindow.hideToolTip();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateTouchTargetArea();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mEditButton = (ImageButton) findViewById(R.id.edit_button);
        this.mEditContainer = findViewById(R.id.edit_button_container);
        this.mEditButton.semSetHoverPopupType(0);
        ImageButton imageButton = this.mEditButton;
        ShadowDelegateUtil shadowDelegateUtil = ShadowDelegateUtil.INSTANCE;
        Drawable drawable = this.mContext.getDrawable(R.drawable.tw_ic_ab_edit_mtrl);
        float dimension = this.mContext.getResources().getDimension(R.dimen.shadow_radius);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_button_size);
        shadowDelegateUtil.getClass();
        imageButton.setImageDrawable(ShadowDelegateUtil.createShadowDrawable(drawable, dimension, 0.2f, dimensionPixelSize));
        updateTouchTargetArea();
        this.mEditButtonBadge = this.mEditContainer.findViewById(R.id.edit_button_badge);
        this.mEditButton.setOnClickListener(new ViewOnClickListenerC21171());
        TouchDelegateUtil touchDelegateUtil = TouchDelegateUtil.INSTANCE;
        View view = this.mEditContainer;
        ImageButton imageButton2 = this.mEditButton;
        touchDelegateUtil.getClass();
        TouchDelegateUtil.expandTouchAreaAsParent(view, imageButton2);
        this.mEditButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSEditButton.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                if (QSEditButton.this.mTipWindow.isTooltipShown()) {
                    return true;
                }
                QSEditButton qSEditButton = QSEditButton.this;
                qSEditButton.mTipWindow.showToolTip(view2, qSEditButton.mToolTipString);
                ((QSButtonsContainer) QSEditButton.this.getParent()).mCloseTooltipWindow = QSEditButton.this;
                return true;
            }
        });
    }

    public final void updateTouchTargetArea() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = this.mContext;
        secQSPanelResourcePicker.getClass();
        layoutParams.width = SecQSPanelResourcePicker.getButtonsWidth(context);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
