package com.android.systemui.qs.buttons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes2.dex */
public class QSButtonsContainer extends FrameLayout {
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
    private final SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;

    public interface CloseTooltipWindow {
        void closeTooltip();
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.qs.buttons.QSButtonsContainer$1] */
    public QSButtonsContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.buttons.QSButtonsContainer$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                int i = QSButtonsContainer.$r8$clinit;
                QSButtonsContainer qSButtonsContainer = this.f$0;
                if (uri.equals(Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL))) {
                    qSButtonsContainer.post(new QSButtonsContainer$$ExternalSyntheticLambda1(qSButtonsContainer));
                    qSButtonsContainer.updateButtonsContainerWidth();
                }
            }
        };
        this.mDismissReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.buttons.QSButtonsContainer.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                QSButtonsContainer qSButtonsContainer;
                CloseTooltipWindow closeTooltipWindow;
                if (("android.intent.action.SCREEN_OFF".equals(intent.getAction()) || PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) && (closeTooltipWindow = (qSButtonsContainer = QSButtonsContainer.this).mCloseTooltipWindow) != null) {
                    closeTooltipWindow.closeTooltip();
                    qSButtonsContainer.mCloseTooltipWindow = null;
                }
            }
        };
        this.mBroadcastDispatcher = (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
    }

    public final boolean isMassiveLandscape() {
        return (((FrameLayout) this).mContext.getResources().getConfiguration().orientation != 2 || QpRune.QUICK_PANEL_BLUR_DEFAULT || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBroadcastDispatcher.registerReceiver(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS, "android.intent.action.SCREEN_OFF"), this.mDismissReceiver);
        updateButtonsContainerWidth();
        this.mSettingsHelper.registerCallback(this.mSettingsListener, Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL));
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        post(new QSButtonsContainer$$ExternalSyntheticLambda1(this));
        updateButtonsContainerWidth();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mCloseTooltipWindow != null) {
            this.mCloseTooltipWindow = null;
        }
        this.mBroadcastDispatcher.unregisterReceiver(this.mDismissReceiver);
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSettingsButton = (QSSettingsButton) findViewById(R.id.settings_button_container);
        this.mEditButton = (QSEditButton) findViewById(R.id.edit_button_container);
        this.mPowerButton = (QSPowerButton) findViewById(R.id.power_button_container);
        this.mMumButton = (QSMumButton) findViewById(R.id.mum_button_container);
        post(new QSButtonsContainer$$ExternalSyntheticLambda1(this));
        int color = ((FrameLayout) this).mContext.getColor(R.color.sec_qs_header_tint_color);
        ((ImageView) this.mEditButton.findViewById(R.id.edit_button)).setColorFilter(color);
        ((ImageView) this.mPowerButton.findViewById(R.id.power_button)).setColorFilter(color);
        ((ImageView) this.mSettingsButton.findViewById(R.id.settings_button)).setColorFilter(color);
        ((ImageView) this.mMumButton.findViewById(R.id.multi_user_avatar)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((FrameLayout) this).mContext));
        ((ImageView) this.mEditButton.findViewById(R.id.edit_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((FrameLayout) this).mContext));
        ((ImageView) this.mPowerButton.findViewById(R.id.power_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((FrameLayout) this).mContext));
        ((ImageView) this.mSettingsButton.findViewById(R.id.settings_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((FrameLayout) this).mContext));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateButtonsContainerWidth() {
        boolean z;
        int qQSPanelSidePadding = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQQSPanelSidePadding(((FrameLayout) this).mContext);
        int qQSPanelSidePadding2 = 0;
        if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide()) {
            z = ((FrameLayout) this).mContext.getResources().getConfiguration().orientation == 1;
        }
        if (z) {
            qQSPanelSidePadding *= -1;
        }
        if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            qQSPanelSidePadding = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelSidePadding(((FrameLayout) this).mContext);
        }
        if (SecPanelSplitHelper.isEnabled() && z) {
            qQSPanelSidePadding2 = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQQSPanelSidePadding(((FrameLayout) this).mContext) * (-1);
        }
        StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("updateButtonsContainerWidth isQQSBiggerThanQS = ", qQSPanelSidePadding, ", sidePadding = ", z, ", endMargin = ");
        sbM.append(qQSPanelSidePadding2);
        Log.d("QSButtonsContainer", sbM.toString());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        layoutParams.width = (((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getButtonsWidth(((FrameLayout) this).mContext) * 4) + qQSPanelSidePadding;
        layoutParams.setMarginEnd(qQSPanelSidePadding2);
        setLayoutParams(layoutParams);
    }
}
