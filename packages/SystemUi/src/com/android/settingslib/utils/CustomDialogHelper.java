package com.android.settingslib.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.DialogTitle;
import com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda2;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomDialogHelper {
    public final Button mBackButton;
    public final FrameLayout mContentPanel;
    public final Context mContext;
    public final FrameLayout mCustomLayout;
    public final FrameLayout mCustomPanel;
    public Dialog mDialog;
    public final View mDialogContent;
    public final ImageView mDialogIcon;
    public final TextView mDialogMessage;
    public final DialogTitle mDialogTitle;
    public final View mDivider1;
    public final View mDivider2;
    public final Button mNegativeButton;
    public final Button mPositiveButton;
    public final LinearLayout mTitleTemplete;

    public CustomDialogHelper(Context context) {
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.sec_alert_dialog, (ViewGroup) null);
        this.mDialogContent = inflate;
        this.mDialogIcon = (ImageView) inflate.findViewById(R.id.dialog_with_icon_icon);
        this.mDialogTitle = (DialogTitle) inflate.findViewById(R.id.dialog_with_icon_title);
        this.mDialogMessage = (TextView) inflate.findViewById(R.id.dialog_with_icon_message);
        this.mCustomLayout = (FrameLayout) inflate.findViewById(R.id.custom_layout);
        this.mPositiveButton = (Button) inflate.findViewById(R.id.button_ok);
        this.mNegativeButton = (Button) inflate.findViewById(R.id.button_cancel);
        this.mBackButton = (Button) inflate.findViewById(R.id.button_back);
        this.mCustomPanel = (FrameLayout) inflate.findViewById(R.id.customPanel);
        this.mContentPanel = (FrameLayout) inflate.findViewById(R.id.contentPanel);
        this.mTitleTemplete = (LinearLayout) inflate.findViewById(R.id.title_template);
        this.mDivider1 = inflate.findViewById(R.id.sem_divider1);
        this.mDivider2 = inflate.findViewById(R.id.sem_divider2);
        AlertDialog create = new AlertDialog.Builder(context).setView(inflate).setCancelable(true).create();
        this.mDialog = create;
        create.getWindow().setSoftInputMode(4);
    }

    public final void checkMaxFontScale(int i, TextView textView) {
        float f = this.mContext.getResources().getConfiguration().fontScale;
        if (f > 1.3f) {
            textView.setTextSize(0, (i / f) * 1.3f);
        }
    }

    public final void setButton(int i, int i2, CreateUserDialogController$$ExternalSyntheticLambda2 createUserDialogController$$ExternalSyntheticLambda2) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_dialog_button_text_size);
        View view = this.mDivider2;
        View view2 = this.mDivider1;
        if (i == 4) {
            Button button = this.mBackButton;
            button.setText(i2);
            button.setVisibility(0);
            button.setOnClickListener(createUserDialogController$$ExternalSyntheticLambda2);
            button.setTextSize(0, dimensionPixelSize);
            checkMaxFontScale(dimensionPixelSize, button);
            view2.setVisibility(8);
            view.setVisibility(0);
            return;
        }
        if (i != 5) {
            if (i != 6) {
                return;
            }
            Button button2 = this.mPositiveButton;
            button2.setText(i2);
            button2.setVisibility(0);
            button2.setOnClickListener(createUserDialogController$$ExternalSyntheticLambda2);
            button2.setTextSize(0, dimensionPixelSize);
            checkMaxFontScale(dimensionPixelSize, button2);
            return;
        }
        Button button3 = this.mNegativeButton;
        button3.setText(i2);
        button3.setVisibility(0);
        button3.setOnClickListener(createUserDialogController$$ExternalSyntheticLambda2);
        button3.setTextSize(0, dimensionPixelSize);
        checkMaxFontScale(dimensionPixelSize, button3);
        view2.setVisibility(0);
        view.setVisibility(8);
    }

    public final void setTitle(int i) {
        DialogTitle dialogTitle = this.mDialogTitle;
        dialogTitle.setText(i);
        checkMaxFontScale(this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_dialog_title_text_size), dialogTitle);
    }

    public final void setVisibility(int i, boolean z) {
        int i2 = z ? 0 : 8;
        switch (i) {
            case 0:
                this.mDialogIcon.setVisibility(i2);
                break;
            case 1:
                this.mDialogTitle.setVisibility(i2);
                break;
            case 2:
                this.mDialogMessage.setVisibility(i2);
                break;
            case 4:
                this.mBackButton.setVisibility(i2);
                break;
            case 5:
                this.mNegativeButton.setVisibility(i2);
                break;
            case 6:
                this.mPositiveButton.setVisibility(i2);
                break;
            case 7:
                this.mCustomPanel.setVisibility(i2);
                break;
            case 8:
                this.mContentPanel.setVisibility(i2);
                break;
            case 9:
                this.mTitleTemplete.setVisibility(i2);
                break;
        }
    }

    public final void setupDialogPaddings() {
        View view = this.mDialogContent;
        View findViewById = view.findViewById(R.id.parentPanel);
        View findViewById2 = view.findViewById(R.id.title_template);
        View findViewById3 = view.findViewById(R.id.scrollView);
        View findViewById4 = view.findViewById(R.id.topPanel);
        View findViewById5 = view.findViewById(R.id.buttonBarLayout);
        View findViewById6 = view.findViewById(R.id.customPanel);
        View findViewById7 = view.findViewById(R.id.contentPanel);
        boolean z = (findViewById6 == null || findViewById6.getVisibility() == 8) ? false : true;
        boolean z2 = (findViewById4 == null || findViewById4.getVisibility() == 8) ? false : true;
        boolean z3 = (findViewById7 == null || findViewById7.getVisibility() == 8) ? false : true;
        Resources resources = this.mContext.getResources();
        if (!z || z2 || z3) {
            findViewById.setPadding(0, resources.getDimensionPixelSize(R.dimen.sec_dialog_title_padding_top), 0, 0);
        } else {
            findViewById.setPadding(0, 0, 0, 0);
        }
        if (findViewById2 != null) {
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sec_dialog_padding_horizontal);
            if (z && z2 && !z3) {
                findViewById2.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
            } else {
                findViewById2.setPadding(dimensionPixelSize, 0, dimensionPixelSize, resources.getDimensionPixelSize(R.dimen.sec_dialog_title_padding_bottom));
            }
        }
        if (findViewById3 != null) {
            findViewById3.setPadding(resources.getDimensionPixelSize(R.dimen.sec_dialog_body_text_scroll_padding_start), 0, resources.getDimensionPixelSize(R.dimen.sec_dialog_body_text_scroll_padding_end), resources.getDimensionPixelSize(R.dimen.sec_dialog_body_text_padding_bottom));
        }
        if (findViewById5 != null) {
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.sec_dialog_button_bar_padding_horizontal);
            findViewById5.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, resources.getDimensionPixelSize(R.dimen.sec_dialog_button_bar_padding_bottom));
        }
    }
}
