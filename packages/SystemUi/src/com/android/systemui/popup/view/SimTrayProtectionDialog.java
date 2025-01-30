package com.android.systemui.popup.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.popup.data.SimTrayProtectionData;
import com.android.systemui.popup.util.PopupUIUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SimTrayProtectionDialog implements PopupUIAlertDialog {
    public LottieAnimationView mBodyImage;
    public LinearLayout mBodyLayout;
    public final Context mContext;
    public AlertDialog mDialog;
    public DisplayMetrics mDisplayMetrics;
    public int mDisplayWidth;
    public final ViewTreeObserverOnGlobalLayoutListenerC19271 mGlobalLayoutListener;
    public final LogWrapper mLogWrapper;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.view.ViewTreeObserver$OnGlobalLayoutListener, com.android.systemui.popup.view.SimTrayProtectionDialog$1] */
    public SimTrayProtectionDialog(Context context, LogWrapper logWrapper, int i, boolean z, int i2) {
        int i3;
        ?? r4 = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.popup.view.SimTrayProtectionDialog.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                SimTrayProtectionDialog simTrayProtectionDialog = SimTrayProtectionDialog.this;
                if (simTrayProtectionDialog.mDisplayMetrics == null) {
                    simTrayProtectionDialog.mDisplayMetrics = new DisplayMetrics();
                }
                SimTrayProtectionDialog.this.mContext.getDisplay().getRealMetrics(SimTrayProtectionDialog.this.mDisplayMetrics);
                SimTrayProtectionDialog simTrayProtectionDialog2 = SimTrayProtectionDialog.this;
                int i4 = simTrayProtectionDialog2.mDisplayMetrics.widthPixels;
                int dimensionPixelSize = simTrayProtectionDialog2.mContext.getResources().getDimensionPixelSize(R.dimen.sim_card_tray_protection_dialog_left_right_margin);
                int i5 = dimensionPixelSize * 2;
                int dimensionPixelSize2 = SimTrayProtectionDialog.this.mContext.getResources().getDimensionPixelSize(R.dimen.sim_card_tray_protection_dialog_start_end_padding) * 2;
                int width = SimTrayProtectionDialog.this.mBodyImage.getWidth() + i5 + dimensionPixelSize2;
                SimTrayProtectionDialog simTrayProtectionDialog3 = SimTrayProtectionDialog.this;
                if (simTrayProtectionDialog3.mDisplayWidth != i4) {
                    simTrayProtectionDialog3.mDisplayWidth = i4;
                    if (i4 < width) {
                        simTrayProtectionDialog3.mBodyImage.getLayoutParams().width = (i4 - i5) - dimensionPixelSize2;
                    } else {
                        simTrayProtectionDialog3.mBodyImage.getLayoutParams().width = SimTrayProtectionDialog.this.mContext.getResources().getDimensionPixelSize(R.dimen.sim_card_tray_protection_dialog_body_image_width);
                    }
                    SimTrayProtectionDialog.this.mBodyImage.requestLayout();
                }
            }
        };
        this.mGlobalLayoutListener = r4;
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        SimTrayProtectionData simTrayProtectionData = new SimTrayProtectionData(context);
        Resources resources = context.getResources();
        boolean z2 = BasicRune.POPUPUI_SD_CARD_STORAGE;
        String string = resources.getString(z2 ? R.string.sim_sd_card_tray_protection_dialog_title : R.string.sim_card_tray_protection_dialog_title);
        boolean z3 = BasicRune.POPUPUI_FOLDERBLE_TYPE_FLIP && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened;
        View inflate = LayoutInflater.from(new ContextThemeWrapper(context, 2132018540)).inflate(R.layout.sim_card_tray_protection_dialog, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_layout);
        this.mBodyLayout = linearLayout;
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(r4);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_image);
        this.mBodyImage = lottieAnimationView;
        ViewGroup.LayoutParams layoutParams = lottieAnimationView.getLayoutParams();
        Resources resources2 = context.getResources();
        boolean z4 = PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_TYPE;
        layoutParams.height = resources2.getDimensionPixelSize((!(z4 && !PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL) || ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) ? (i2 == 1 && BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD) ? R.dimen.sim_card_tray_protection_dialog_body_image_height_folder : R.dimen.sim_card_tray_protection_dialog_body_image_height : R.dimen.sim_card_tray_protection_dialog_body_image_height_flip_cover);
        int i4 = z4 && !PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL ? R.drawable.sim_card_tray_normal_image_flip : (i2 == 1 || z4 || PopupUIUtil.SIM_CARD_TRAY_STYLE_FOLD_TYPE) ? R.drawable.sim_card_tray_normal_dialog_animation : R.drawable.sim_card_tray_stacked_dialog_animation;
        String resourceTypeName = i4 == 0 ? "" : context.getResources().getResourceTypeName(i4);
        resourceTypeName.getClass();
        if (resourceTypeName.equals("drawable")) {
            this.mBodyImage.setVisibility(0);
            this.mBodyImage.setImageDrawable(context.getResources().getDrawable(i4, null));
        } else if (resourceTypeName.equals("raw")) {
            this.mBodyImage.setVisibility(0);
            this.mBodyImage.setAnimation(i4);
        } else {
            logWrapper.m100e("SimTrayProtectionDialog", "Unknown resource type");
        }
        TextView textView = (TextView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_message_no_sim_card);
        if (i == 1) {
            textView.setText(context.getResources().getString(R.string.sim_card_tray_protection_dialog_body_no_sim_card));
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_message_waterproof_sim_card);
        if (z) {
            textView2.setText(context.getResources().getString(z2 ? R.string.sim_card_tray_protection_dialog_body_sim_sd_card_waterproof : R.string.sim_card_tray_protection_dialog_body_sim_card_waterproof));
        } else {
            textView2.setVisibility(8);
        }
        TextView textView3 = (TextView) inflate.findViewById(R.id.sim_card_tray_protection_dialog_body_message_inserting_sim_card);
        if (i2 == 0) {
            textView3.setVisibility(8);
        } else {
            Resources resources3 = context.getResources();
            if (simTrayProtectionData.mContext.getResources().getBoolean(R.bool.config_enableCustomeSimTrayPopupText)) {
                i3 = R.string.f796x2eff39b8;
            } else if (BasicRune.POPUPUI_MODEL_TYPE_WINNER || PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL) {
                i3 = R.string.f797x9fc3a6dd;
            } else if (PopupUIUtil.SIM_CARD_TRAY_STYLE_FOLD_TYPE) {
                i3 = R.string.f798xde525cc3;
            } else {
                if (!z4) {
                    if (i2 != 1) {
                        i3 = BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD ? R.string.f794x8e65ae36 : R.string.f795x1f24bfcf;
                    } else if (!BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD) {
                        i3 = R.string.f800xe262e0b;
                    }
                }
                i3 = R.string.f799x653047a;
            }
            textView3.setText(resources3.getString(i3));
        }
        if (BasicRune.POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG && z3) {
            TextView[] textViewArr = {textView3, textView2, textView};
            for (int i5 = 0; i5 < 3; i5++) {
                TextView textView4 = textViewArr[i5];
                if (textView4 != null) {
                    FontSizeUtils.updateFontSize(textView4, R.dimen.subscreen_dialog_text_size, 0.9f, 1.3f);
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2132018540);
        builder.setTitle(string);
        builder.setView(inflate);
        builder.setPositiveButton(context.getResources().getString(R.string.yes), (DialogInterface.OnClickListener) null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.popup.view.SimTrayProtectionDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SimTrayProtectionDialog.this.mDialog = null;
            }
        });
        AlertDialog create = builder.create();
        create.getWindow().getAttributes().setTitle("SimTrayProtectionDialog");
        if (BasicRune.POPUPUI_SUPPORT_COVER_SIM_TRAY_DIALOG) {
            create.getWindow().setType(2017);
        } else {
            create.getWindow().setType(2009);
        }
        this.mDialog = create;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final void dismiss() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mDialog.dismiss();
        this.mBodyLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final boolean isShowing() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            return alertDialog.isShowing();
        }
        return false;
    }

    @Override // com.android.systemui.popup.view.PopupUIAlertDialog
    public final void show() {
        try {
            if (this.mDialog != null) {
                dismiss();
                this.mDialog.setCancelable(false);
                this.mDialog.show();
            }
        } catch (WindowManager.BadTokenException unused) {
            this.mLogWrapper.m103v("SimTrayProtectionDialog");
        }
    }
}
