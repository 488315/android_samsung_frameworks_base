package com.android.systemui.globalactions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewRootImpl;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.keyguard.SecurityUtils$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.globalactions.presentation.view.SamsungGlobalActionsDialog;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;

public final class GlobalActionsImpl implements GlobalActions, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public boolean mDisabled;
    public final KeyguardStateController mKeyguardStateController;
    public SamsungGlobalActionsDialog mSamsungGlobalActionsDialog;
    public final ShutdownUi mShutdownUi;

    public GlobalActionsImpl(Context context, CommandQueue commandQueue, Lazy lazy, BlurUtils blurUtils, KeyguardStateController keyguardStateController, DeviceProvisionedController deviceProvisionedController, ShutdownUi shutdownUi) {
        this.mContext = context;
        this.mKeyguardStateController = keyguardStateController;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mShutdownUi = shutdownUi;
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public final void destroy() {
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        SamsungGlobalActionsDialog samsungGlobalActionsDialog;
        boolean z2 = (i3 & 8) != 0;
        if (i != this.mContext.getDisplayId() || z2 == this.mDisabled) {
            return;
        }
        this.mDisabled = z2;
        if (!z2 || (samsungGlobalActionsDialog = this.mSamsungGlobalActionsDialog) == null) {
            return;
        }
        samsungGlobalActionsDialog.dismiss();
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public final void showGlobalActions(GlobalActions.GlobalActionsManager globalActionsManager) {
        showGlobalActions(globalActionsManager, -1);
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public final void showShutdownUi(boolean z, String str) {
        final ShutdownUi shutdownUi = this.mShutdownUi;
        shutdownUi.getClass();
        final ScrimDrawable scrimDrawable = new ScrimDrawable();
        final Dialog dialog = new Dialog(shutdownUi.mContext, R.style.Theme_SystemUI_Dialog_GlobalActions);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.globalactions.ShutdownUi$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                ShutdownUi shutdownUi2 = ShutdownUi.this;
                ScrimDrawable scrimDrawable2 = scrimDrawable;
                Dialog dialog2 = dialog;
                BlurUtils blurUtils = shutdownUi2.mBlurUtils;
                if (!blurUtils.supportsBlursOnWindows()) {
                    scrimDrawable2.setAlpha((int) SecurityUtils$$ExternalSyntheticOutline0.m(shutdownUi2.mContext, R.dimen.shutdown_scrim_behind_alpha, 255.0f));
                    return;
                }
                scrimDrawable2.setAlpha(255);
                ViewRootImpl viewRootImpl = dialog2.getWindow().getDecorView().getViewRootImpl();
                blurUtils.blurRadiusOfRatio(1.0f);
                if (viewRootImpl != null) {
                    viewRootImpl.getSurfaceControl().isValid();
                }
            }
        });
        Window window = dialog.getWindow();
        window.requestFeature(1);
        window.getAttributes().systemUiVisibility |= 1792;
        window.getDecorView();
        window.getAttributes().width = -1;
        window.getAttributes().height = -1;
        window.getAttributes().layoutInDisplayCutoutMode = 3;
        window.setType(2020);
        window.getAttributes().setFitInsetsTypes(0);
        window.clearFlags(2);
        window.addFlags(17629472);
        window.setBackgroundDrawable(scrimDrawable);
        window.setWindowAnimations(R.style.Animation_ShutdownUi);
        dialog.setContentView(shutdownUi.getShutdownDialogContent(z));
        dialog.setCancelable(false);
        int colorAttrDefaultColor = shutdownUi.mBlurUtils.supportsBlursOnWindows() ? Utils.getColorAttrDefaultColor(shutdownUi.mContext, R.attr.wallpaperTextColor, 0) : shutdownUi.mContext.getResources().getColor(R.color.global_actions_shutdown_ui_text);
        ((ProgressBar) dialog.findViewById(android.R.id.progress)).getIndeterminateDrawable().setTint(colorAttrDefaultColor);
        TextView textView = (TextView) dialog.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) dialog.findViewById(android.R.id.text2);
        textView.setTextColor(colorAttrDefaultColor);
        textView2.setTextColor(colorAttrDefaultColor);
        textView2.setText(shutdownUi.getRebootMessage(z, str));
        String reasonMessage = shutdownUi.getReasonMessage(str);
        if (reasonMessage != null) {
            textView.setVisibility(0);
            textView.setText(reasonMessage);
        }
        dialog.show();
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public final void showGlobalActions(GlobalActions.GlobalActionsManager globalActionsManager, int i) {
        if (this.mDisabled) {
            return;
        }
        if (this.mSamsungGlobalActionsDialog == null) {
            this.mSamsungGlobalActionsDialog = new SamsungGlobalActionsDialog(this.mContext, globalActionsManager);
        }
        this.mSamsungGlobalActionsDialog.show(((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing, ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get(), false, i);
    }
}
