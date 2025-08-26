package com.google.android.setupcompat.template;

import android.widget.Button;
import android.widget.LinearLayout;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.view.ButtonBarLayout;

/* loaded from: classes4.dex */
public final /* synthetic */ class FooterBarMixin$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ FooterBarMixin f$0;

    public /* synthetic */ FooterBarMixin$$ExternalSyntheticLambda2(FooterBarMixin footerBarMixin) {
        this.f$0 = footerBarMixin;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FooterBarMixin footerBarMixin = this.f$0;
        int measuredWidth = footerBarMixin.buttonContainer.getMeasuredWidth();
        Button primaryButtonView = footerBarMixin.getPrimaryButtonView();
        Button secondaryButtonView = footerBarMixin.getSecondaryButtonView();
        boolean z = false;
        Button button = null;
        if (PartnerConfigHelper.isGlifExpressiveEnabled(footerBarMixin.context)) {
            LinearLayout linearLayout = footerBarMixin.buttonContainer;
            if (linearLayout != null) {
                button = (Button) linearLayout.findViewById(0);
            }
        } else {
            FooterBarMixin.LOG.atDebug("Cannot get tertiary button when glif expressive is not enabled.");
        }
        if (footerBarMixin.context.getResources().getBoolean(R.bool.sucTwoPaneLayoutStyle)) {
            measuredWidth /= 2;
            footerBarMixin.buttonContainer.setGravity(8388613);
        }
        int i = ((measuredWidth - footerBarMixin.footerBarPaddingStart) - footerBarMixin.footerBarPaddingEnd) - footerBarMixin.footerBarButtonMiddleSpacing;
        int i2 = i / 2;
        boolean z2 = button != null && button.getVisibility() == 0;
        String strM = KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isTertiaryButtonVisible=", z2);
        Logger logger = FooterBarMixin.LOG;
        logger.atDebug(strM);
        if (z2 && FooterBarMixin.isBothButtons(primaryButtonView, secondaryButtonView)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) primaryButtonView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) secondaryButtonView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) button.getLayoutParams();
            LinearLayout linearLayout2 = footerBarMixin.buttonContainer;
            if (linearLayout2 instanceof ButtonBarLayout) {
                ButtonBarLayout buttonBarLayout = (ButtonBarLayout) linearLayout2;
                if (PartnerConfigHelper.isGlifExpressiveEnabled(buttonBarLayout.getContext())) {
                    buttonBarLayout.stackedButtonForExpressiveStyle = true;
                } else {
                    buttonBarLayout.stackedButtonForExpressiveStyle = false;
                }
                int i3 = footerBarMixin.footerBarButtonMiddleSpacing / 2;
                layoutParams2.width = i;
                layoutParams2.topMargin = i3;
                secondaryButtonView.setLayoutParams(layoutParams2);
                layoutParams3.width = i;
                layoutParams3.topMargin = i3;
                layoutParams3.bottomMargin = i3;
                button.setLayoutParams(layoutParams3);
                layoutParams.width = i;
                layoutParams.bottomMargin = i3;
                primaryButtonView.setLayoutParams(layoutParams);
                return;
            }
            return;
        }
        if (FooterBarMixin.isBothButtons(primaryButtonView, secondaryButtonView)) {
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) primaryButtonView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) secondaryButtonView.getLayoutParams();
            if (footerBarMixin.stackButtonIfTextOverFlow(primaryButtonView, secondaryButtonView, i2, i)) {
                return;
            }
            if (layoutParams4 != null) {
                layoutParams4.width = i2;
                layoutParams4.setMarginStart(footerBarMixin.footerBarButtonMiddleSpacing / 2);
                primaryButtonView.setLayoutParams(layoutParams4);
            }
            if (layoutParams5 != null) {
                layoutParams5.width = i2;
                layoutParams5.setMarginEnd(footerBarMixin.footerBarButtonMiddleSpacing / 2);
                secondaryButtonView.setLayoutParams(layoutParams5);
                return;
            }
            return;
        }
        boolean z3 = primaryButtonView != null && secondaryButtonView == null;
        boolean z4 = (primaryButtonView == null || secondaryButtonView == null || secondaryButtonView.getVisibility() == 0) ? false : true;
        logger.atDebug("isPrimaryOnly=" + z3 + ", isPrimaryOnlyButSecondaryInvisible=" + z4);
        if (z3 || z4) {
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) primaryButtonView.getLayoutParams();
            if (layoutParams6 != null) {
                layoutParams6.width = i;
                primaryButtonView.setLayoutParams(layoutParams6);
                return;
            }
            return;
        }
        boolean z5 = secondaryButtonView != null && primaryButtonView == null;
        if (secondaryButtonView != null && primaryButtonView != null && primaryButtonView.getVisibility() != 0) {
            z = true;
        }
        logger.atDebug("isSecondaryOnly=" + z5 + ", isSecondaryOnlyButPrimaryInvisible=" + z);
        if (!z5 && !z) {
            logger.atInfo("There are no button visible in the footer bar.");
            return;
        }
        LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) secondaryButtonView.getLayoutParams();
        if (layoutParams7 != null) {
            layoutParams7.width = i;
            secondaryButtonView.setLayoutParams(layoutParams7);
        }
    }
}
