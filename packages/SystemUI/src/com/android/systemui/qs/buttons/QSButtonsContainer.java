package com.android.systemui.qs.buttons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
    private final SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                QSButtonsContainer qSButtonsContainer = QSButtonsContainer.this;
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
        return (((LinearLayout) this).mContext.getResources().getConfiguration().orientation != 2 || QpRune.QUICK_PANEL_BLUR_DEFAULT || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) ? false : true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), this.mDismissReceiver);
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
        int color = ((LinearLayout) this).mContext.getColor(R.color.sec_qs_header_tint_color);
        ((ImageView) this.mEditButton.findViewById(R.id.edit_button)).setColorFilter(color);
        ((ImageView) this.mPowerButton.findViewById(R.id.power_button)).setColorFilter(color);
        ((ImageView) this.mSettingsButton.findViewById(R.id.settings_button)).setColorFilter(color);
        ((ImageView) this.mMumButton.findViewById(R.id.multi_user_avatar)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
        ((ImageView) this.mEditButton.findViewById(R.id.edit_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
        ((ImageView) this.mPowerButton.findViewById(R.id.power_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
        ((ImageView) this.mSettingsButton.findViewById(R.id.settings_button)).setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(((LinearLayout) this).mContext));
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x002e, code lost:
    
        if (((android.widget.LinearLayout) r7).mContext.getResources().getConfiguration().orientation == 1) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateButtonsContainerWidth() {
        /*
            r7 = this;
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.qs.SecQSPanelResourcePicker> r1 = com.android.systemui.qs.SecQSPanelResourcePicker.class
            java.lang.Object r0 = r0.getDependencyInner(r1)
            com.android.systemui.qs.SecQSPanelResourcePicker r0 = (com.android.systemui.qs.SecQSPanelResourcePicker) r0
            android.content.Context r2 = r7.mContext
            int r0 = r0.getQQSPanelSidePadding(r2)
            com.android.systemui.Dependency r2 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.util.SecQsUiDisplayModeInteractor> r3 = com.android.systemui.util.SecQsUiDisplayModeInteractor.class
            java.lang.Object r2 = r2.getDependencyInner(r3)
            com.android.systemui.util.SecQsUiDisplayModeInteractor r2 = (com.android.systemui.util.SecQsUiDisplayModeInteractor) r2
            boolean r2 = r2.isFoldWide()
            r4 = 0
            if (r2 == 0) goto L31
            android.content.Context r2 = r7.mContext
            android.content.res.Resources r2 = r2.getResources()
            android.content.res.Configuration r2 = r2.getConfiguration()
            int r2 = r2.orientation
            r5 = 1
            if (r2 != r5) goto L31
            goto L32
        L31:
            r5 = r4
        L32:
            if (r5 == 0) goto L36
            int r0 = r0 * (-1)
        L36:
            com.android.systemui.Dependency r2 = com.android.systemui.Dependency.sDependency
            java.lang.Object r2 = r2.getDependencyInner(r3)
            com.android.systemui.util.SecQsUiDisplayModeInteractor r2 = (com.android.systemui.util.SecQsUiDisplayModeInteractor) r2
            boolean r2 = r2.isTablet()
            if (r2 == 0) goto L52
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Object r0 = r0.getDependencyInner(r1)
            com.android.systemui.qs.SecQSPanelResourcePicker r0 = (com.android.systemui.qs.SecQSPanelResourcePicker) r0
            android.content.Context r2 = r7.mContext
            int r0 = r0.getPanelSidePadding(r2)
        L52:
            boolean r2 = com.android.systemui.shade.SecPanelSplitHelper.isEnabled()
            if (r2 == 0) goto L6a
            if (r5 == 0) goto L6a
            com.android.systemui.Dependency r2 = com.android.systemui.Dependency.sDependency
            java.lang.Object r2 = r2.getDependencyInner(r1)
            com.android.systemui.qs.SecQSPanelResourcePicker r2 = (com.android.systemui.qs.SecQSPanelResourcePicker) r2
            android.content.Context r3 = r7.mContext
            int r2 = r2.getQQSPanelSidePadding(r3)
            int r4 = r2 * (-1)
        L6a:
            java.lang.String r2 = "updateButtonsContainerWidth isQQSBiggerThanQS = "
            java.lang.String r3 = ", sidePadding = "
            java.lang.String r6 = ", endMargin = "
            java.lang.StringBuilder r2 = com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0.m(r2, r0, r3, r5, r6)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "QSButtonsContainer"
            android.util.Log.d(r3, r2)
            android.view.ViewGroup$LayoutParams r2 = r7.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r2 = (android.widget.LinearLayout.LayoutParams) r2
            com.android.systemui.Dependency r3 = com.android.systemui.Dependency.sDependency
            java.lang.Object r1 = r3.getDependencyInner(r1)
            com.android.systemui.qs.SecQSPanelResourcePicker r1 = (com.android.systemui.qs.SecQSPanelResourcePicker) r1
            android.content.Context r3 = r7.mContext
            com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper r1 = r1.resourcePickHelper
            com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker r1 = r1.getTargetPicker()
            int r1 = r1.getButtonsWidth(r3)
            int r1 = r1 * 4
            int r1 = r1 + r0
            r2.width = r1
            r2.setMarginEnd(r4)
            r7.setLayoutParams(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.buttons.QSButtonsContainer.updateButtonsContainerWidth():void");
    }
}
