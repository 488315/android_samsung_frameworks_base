package com.android.systemui.qs.buttons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.RecoilEffectUtil;

public class QSButtonsContainer extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public CloseTooltipWindow mCloseTooltipWindow;
    public final AnonymousClass1 mDismissReceiver;
    public QSEditButton mEditButton;
    public boolean mExpanded;
    public boolean mListening;
    public QSMumButton mMumButton;
    public QSPowerButton mPowerButton;
    public QSSettingsButton mSettingsButton;

    public interface CloseTooltipWindow {
        void closeTooltip();
    }

    public QSButtonsContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDismissReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.buttons.QSButtonsContainer.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                QSButtonsContainer qSButtonsContainer;
                CloseTooltipWindow closeTooltipWindow;
                if (!PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction()) || (closeTooltipWindow = (qSButtonsContainer = QSButtonsContainer.this).mCloseTooltipWindow) == null) {
                    return;
                }
                closeTooltipWindow.closeTooltip();
                qSButtonsContainer.mCloseTooltipWindow = null;
            }
        };
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
    }

    public final boolean isMassiveLandscape() {
        return ((LinearLayout) this).mContext.getResources().getConfiguration().orientation == 2 && !QpRune.QUICK_PANEL_BLUR_DEFAULT;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), this.mDismissReceiver);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        post(new QSButtonsContainer$$ExternalSyntheticLambda0(this));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mCloseTooltipWindow != null) {
            this.mCloseTooltipWindow = null;
        }
        this.mBroadcastDispatcher.unregisterReceiver(this.mDismissReceiver);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSettingsButton = (QSSettingsButton) findViewById(R.id.settings_button_container);
        this.mEditButton = (QSEditButton) findViewById(R.id.edit_button_container);
        this.mPowerButton = (QSPowerButton) findViewById(R.id.power_button_container);
        this.mMumButton = (QSMumButton) findViewById(R.id.mum_button_container);
        post(new QSButtonsContainer$$ExternalSyntheticLambda0(this));
        int color = ((LinearLayout) this).mContext.getColor(R.color.sec_qs_header_tint_color);
        ((ImageView) this.mEditButton.findViewById(R.id.edit_button)).setColorFilter(color);
        ((ImageView) this.mPowerButton.findViewById(R.id.power_button)).setColorFilter(color);
        ((ImageView) this.mSettingsButton.findViewById(R.id.settings_button)).setColorFilter(color);
        ((ImageView) this.mMumButton.findViewById(R.id.multi_user_avatar)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
        ((ImageView) this.mEditButton.findViewById(R.id.edit_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
        ((ImageView) this.mPowerButton.findViewById(R.id.power_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
        ((ImageView) this.mSettingsButton.findViewById(R.id.settings_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
    }
}
