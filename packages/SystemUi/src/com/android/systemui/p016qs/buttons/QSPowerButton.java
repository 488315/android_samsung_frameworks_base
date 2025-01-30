package com.android.systemui.p016qs.buttons;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.FactoryTest;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.globalactions.presentation.view.SamsungGlobalActionsDialog;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.p016qs.buttons.QSButtonsContainer;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.ShadowDelegateUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.TouchDelegateUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSPowerButton extends FrameLayout implements QSButtonsContainer.CloseTooltipWindow {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public ImageButton mPowerButton;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final QSTooltipWindow mTipWindow;
    public final int mToolTipString;

    public QSPowerButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = R.string.tooltip_quick_settings_power_off;
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
        ImageButton imageButton = (ImageButton) findViewById(R.id.power_button);
        this.mPowerButton = imageButton;
        imageButton.semSetHoverPopupType(0);
        ImageButton imageButton2 = this.mPowerButton;
        ShadowDelegateUtil shadowDelegateUtil = ShadowDelegateUtil.INSTANCE;
        Drawable drawable = this.mContext.getDrawable(R.drawable.tw_ic_ab_power_mtrl);
        float dimension = this.mContext.getResources().getDimension(R.dimen.shadow_radius);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_button_size);
        shadowDelegateUtil.getClass();
        imageButton2.setImageDrawable(ShadowDelegateUtil.createShadowDrawable(drawable, dimension, 0.2f, dimensionPixelSize));
        updateTouchTargetArea();
        this.mPowerButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSPowerButton.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Log.d("QSPowerButton", "!@[Shutdown] Click power off button.");
                QSPowerButton qSPowerButton = QSPowerButton.this;
                int i = QSPowerButton.$r8$clinit;
                qSPowerButton.getClass();
                if (FactoryTest.isLongPressOnPowerOffEnabled() || FactoryTest.isAutomaticTestMode(qSPowerButton.mContext)) {
                    Slog.d("QSPowerButton", "!@long press power shutdown by power icon of quickpanel");
                    ((GlobalActionsComponent) Dependency.get(GlobalActionsComponent.class)).shutdown();
                } else {
                    int i2 = SamsungGlobalActionsDialog.$r8$clinit;
                    ((GlobalActionsComponent) Dependency.get(GlobalActionsComponent.class)).handleShowGlobalActionsMenu(2);
                    if (BasicRune.GLOBALACTIONS_BLUR) {
                        if (((StatusBarStateController) Dependency.get(StatusBarStateController.class)).getState() != 0) {
                            ((NotificationPanelViewController) ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).getShadeViewController()).closeQsIfPossible();
                        } else {
                            ((ShadeControllerImpl) ((ShadeController) Dependency.get(ShadeController.class))).animateCollapsePanels(1.0f, 2, true, true);
                        }
                    }
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1002");
            }
        });
        TouchDelegateUtil touchDelegateUtil = TouchDelegateUtil.INSTANCE;
        View findViewById = findViewById(R.id.power_button_container);
        ImageButton imageButton3 = this.mPowerButton;
        touchDelegateUtil.getClass();
        TouchDelegateUtil.expandTouchAreaAsParent(findViewById, imageButton3);
        this.mPowerButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSPowerButton.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                if (QSPowerButton.this.mTipWindow.isTooltipShown()) {
                    return true;
                }
                QSPowerButton qSPowerButton = QSPowerButton.this;
                qSPowerButton.mTipWindow.showToolTip(view, qSPowerButton.mToolTipString);
                ((QSButtonsContainer) QSPowerButton.this.getParent()).mCloseTooltipWindow = QSPowerButton.this;
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
