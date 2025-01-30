package com.android.systemui.globalactions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GlobalActionsImpl implements GlobalActions, CommandQueue.Callbacks {
    public final BlurUtils mBlurUtils;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public boolean mDisabled;
    public final KeyguardStateController mKeyguardStateController;
    public SamsungGlobalActionsDialog mSamsungGlobalActionsDialog;

    public GlobalActionsImpl(Context context, CommandQueue commandQueue, Lazy lazy, BlurUtils blurUtils, KeyguardStateController keyguardStateController, DeviceProvisionedController deviceProvisionedController) {
        this.mContext = context;
        this.mKeyguardStateController = keyguardStateController;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        this.mBlurUtils = blurUtils;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public final void destroy() {
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
        throw null;
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
        this.mSamsungGlobalActionsDialog = null;
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public final void showGlobalActions(GlobalActions.GlobalActionsManager globalActionsManager) {
        showGlobalActions(globalActionsManager, -1);
    }

    @Override // com.android.systemui.plugins.GlobalActions
    public final void showShutdownUi(boolean z, String str) {
        final ScrimDrawable scrimDrawable = new ScrimDrawable();
        Context context = this.mContext;
        final Dialog dialog = new Dialog(context, 2132018531);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.globalactions.GlobalActionsImpl$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                GlobalActionsImpl globalActionsImpl = GlobalActionsImpl.this;
                ScrimDrawable scrimDrawable2 = scrimDrawable;
                Dialog dialog2 = dialog;
                BlurUtils blurUtils = globalActionsImpl.mBlurUtils;
                if (!blurUtils.supportsBlursOnWindows()) {
                    scrimDrawable2.setAlpha((int) (globalActionsImpl.mContext.getResources().getFloat(R.dimen.shutdown_scrim_behind_alpha) * 255.0f));
                } else {
                    scrimDrawable2.setAlpha(255);
                    blurUtils.applyBlur(dialog2.getWindow().getDecorView().getViewRootImpl(), (int) blurUtils.blurRadiusOfRatio(1.0f), true);
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
        window.setWindowAnimations(2132017169);
        dialog.setContentView(17367423);
        dialog.setCancelable(false);
        int colorAttrDefaultColor = this.mBlurUtils.supportsBlursOnWindows() ? Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColor, context, 0) : context.getResources().getColor(R.color.global_actions_shutdown_ui_text);
        ((ProgressBar) dialog.findViewById(android.R.id.progress)).getIndeterminateDrawable().setTint(colorAttrDefaultColor);
        TextView textView = (TextView) dialog.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) dialog.findViewById(android.R.id.text2);
        textView.setTextColor(colorAttrDefaultColor);
        textView2.setTextColor(colorAttrDefaultColor);
        textView2.setText((str == null || !str.startsWith("recovery-update")) ? ((str == null || !str.equals("recovery")) && !z) ? 17042825 : 17042365 : 17042369);
        String string = (str == null || !str.startsWith("recovery-update")) ? (str == null || !str.equals("recovery")) ? null : context.getString(17042366) : context.getString(17042370);
        if (string != null) {
            textView.setVisibility(0);
            textView.setText(string);
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
        this.mSamsungGlobalActionsDialog.show(((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing, ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).isDeviceProvisioned(), false, i);
    }
}
