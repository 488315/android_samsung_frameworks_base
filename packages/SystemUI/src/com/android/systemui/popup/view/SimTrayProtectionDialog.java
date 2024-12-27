package com.android.systemui.popup.view;

import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.popup.data.SimTrayProtectionData;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SimTrayProtectionDialog implements PopupUIAlertDialog {
    private static final String TAG = "SimTrayProtectionDialog";
    public static final float TINY_TEXT_SCALE = 0.9f;
    private LottieAnimationView mBodyImage;
    private LinearLayout mBodyLayout;
    private Context mContext;
    private AlertDialog mDialog;
    private DisplayMetrics mDisplayMetrics;
    private int mDisplayWidth;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.popup.view.SimTrayProtectionDialog.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (SimTrayProtectionDialog.this.mDisplayMetrics == null) {
                SimTrayProtectionDialog.this.mDisplayMetrics = new DisplayMetrics();
            }
            SimTrayProtectionDialog.this.mContext.getDisplay().getRealMetrics(SimTrayProtectionDialog.this.mDisplayMetrics);
            int i = SimTrayProtectionDialog.this.mDisplayMetrics.widthPixels;
            int dimensionPixelSize = SimTrayProtectionDialog.this.mContext.getResources().getDimensionPixelSize(R.dimen.sim_card_tray_protection_dialog_left_right_margin);
            int i2 = dimensionPixelSize * 2;
            int dimensionPixelSize2 = SimTrayProtectionDialog.this.mContext.getResources().getDimensionPixelSize(R.dimen.sim_card_tray_protection_dialog_start_end_padding) * 2;
            int width = SimTrayProtectionDialog.this.mBodyImage.getWidth() + i2 + dimensionPixelSize2;
            if (SimTrayProtectionDialog.this.mDisplayWidth != i) {
                SimTrayProtectionDialog.this.mDisplayWidth = i;
                if (i < width) {
                    SimTrayProtectionDialog.this.mBodyImage.getLayoutParams().width = (i - i2) - dimensionPixelSize2;
                } else {
                    SimTrayProtectionDialog.this.mBodyImage.getLayoutParams().width = SimTrayProtectionDialog.this.mContext.getResources().getDimensionPixelSize(R.dimen.sim_card_tray_protection_dialog_body_image_width);
                }
                SimTrayProtectionDialog.this.mBodyImage.requestLayout();
            }
        }
    };
    private LogWrapper mLogWrapper;
    private int mStyle;
    private int mType;
    private boolean mWaterproof;

    public SimTrayProtectionDialog(Context context, LogWrapper logWrapper, int i, boolean z, int i2) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mType = i;
        this.mWaterproof = z;
        this.mStyle = i2;
        this.mDialog = createDialog(new SimTrayProtectionData(context));
    }

    private AlertDialog createDialog(SimTrayProtectionData simTrayProtectionData) {
        String string = this.mContext.getResources().getString(simTrayProtectionData.getTitleMessage());
        boolean z = BasicRune.POPUPUI_FOLDERBLE_TYPE_FLIP && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(this.mContext, R.style.Theme_SystemUI_POPUPUI)).inflate(R.layout.sim_card_tray_protection_dialog, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_layout);
        this.mBodyLayout = linearLayout;
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_image);
        this.mBodyImage = lottieAnimationView;
        lottieAnimationView.getLayoutParams().height = this.mContext.getResources().getDimensionPixelSize(simTrayProtectionData.getBodyImageHeight());
        int bodyImage = simTrayProtectionData.getBodyImage();
        String resourceTypeName = bodyImage == 0 ? "" : this.mContext.getResources().getResourceTypeName(bodyImage);
        resourceTypeName.getClass();
        if (resourceTypeName.equals(BriefViewController.URI_PATH_DRAWABLE)) {
            this.mBodyImage.setVisibility(0);
            this.mBodyImage.setImageDrawable(this.mContext.getResources().getDrawable(bodyImage, null));
        } else if (resourceTypeName.equals("raw")) {
            this.mBodyImage.setVisibility(0);
            this.mBodyImage.setAnimation(bodyImage);
        } else {
            this.mLogWrapper.e(TAG, "Unknown resource type");
        }
        TextView textView = (TextView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_message_no_sim_card);
        if (this.mType == 1) {
            textView.setText(this.mContext.getResources().getString(simTrayProtectionData.getBodyMessageNoSimCard()));
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_message_waterproof_sim_card);
        if (this.mWaterproof) {
            textView2.setText(this.mContext.getResources().getString(simTrayProtectionData.getBodyMessageWaterProofSimCard()));
        } else {
            textView2.setVisibility(8);
        }
        TextView textView3 = (TextView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_message_inserting_sim_card);
        if (this.mStyle == 0) {
            textView3.setVisibility(8);
        } else {
            textView3.setText(this.mContext.getResources().getString(simTrayProtectionData.getBodyMessageInsertingSimCard()));
        }
        boolean z2 = BasicRune.POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG;
        if (z2 && z) {
            updateFontSize(textView3, textView2, textView);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext, R.style.Theme_SystemUI_POPUPUI);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = string;
        builder.setView(inflate);
        builder.setPositiveButton(this.mContext.getResources().getString(R.string.yes), null);
        alertParams.mOnDismissListener = new DialogInterface.OnDismissListener() { // from class: com.android.systemui.popup.view.SimTrayProtectionDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SimTrayProtectionDialog.this.lambda$createDialog$0(dialogInterface);
            }
        };
        AlertDialog create = builder.create();
        create.getWindow().getAttributes().setTitle(TAG);
        if (z2) {
            create.getWindow().setType(2017);
        } else {
            create.getWindow().setType(2009);
        }
        return create;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDialog$0(DialogInterface dialogInterface) {
        this.mDialog = null;
    }

    private void updateFontSize(TextView... textViewArr) {
        for (TextView textView : textViewArr) {
            if (textView != null) {
                FontSizeUtils.updateFontSize(textView, R.dimen.subscreen_dialog_text_size, 0.9f, 1.3f);
            }
        }
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public void dismiss() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mDialog.dismiss();
        this.mBodyLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public boolean isShowing() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            return alertDialog.isShowing();
        }
        return false;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public void show() {
        try {
            if (this.mDialog != null) {
                dismiss();
                this.mDialog.setCancelable(false);
                this.mDialog.show();
            }
        } catch (WindowManager.BadTokenException unused) {
            this.mLogWrapper.v(TAG);
        }
    }
}
