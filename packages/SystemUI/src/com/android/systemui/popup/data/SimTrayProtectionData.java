package com.android.systemui.popup.data;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.popup.util.PopupUIUtil;

/* loaded from: classes2.dex */
public class SimTrayProtectionData {
    private Context mContext;

    public SimTrayProtectionData() {
    }

    private boolean isFlipCommonModel() {
        return PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_TYPE && !PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL;
    }

    public int getBodyImage() {
        return isFlipCommonModel() ? R.drawable.sim_card_tray_normal_image_flip : R.drawable.sim_card_tray_normal_dialog_animation;
    }

    public int getBodyImageHeight() {
        return (!isFlipCommonModel() || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD ? R.dimen.sim_card_tray_protection_dialog_body_image_height_folder : R.dimen.sim_card_tray_protection_dialog_body_image_height : R.dimen.sim_card_tray_protection_dialog_body_image_height_flip_cover;
    }

    public int getBodyMessageInsertingSimCard() {
        return this.mContext.getResources().getBoolean(R.bool.config_enableCustomeSimTrayPopupText) ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_model_Q5_type : (BasicRune.POPUPUI_MODEL_TYPE_WINNER || PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_CHC_MODEL) ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_model_winner_type : (PopupUIUtil.SIM_CARD_TRAY_STYLE_FOLD_TYPE || PopupUIUtil.SIM_CARD_TRAY_STYLE_Q7M_MODEL) ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_normal_Q6_type : PopupUIUtil.SIM_CARD_TRAY_STYLE_FLIP_TYPE ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_normal_folder_type : this.mContext.getResources().getBoolean(R.bool.config_enableSimTrayPopupText) ? BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_layer_folder_type : R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_layer_type : BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_normal_folder_type : R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_normal_type;
    }

    public int getBodyMessageNoSimCard() {
        return R.string.sim_card_tray_protection_dialog_body_no_sim_card;
    }

    public int getBodyMessageWaterProofSimCard() {
        return BasicRune.POPUPUI_SD_CARD_STORAGE ? R.string.sim_card_tray_protection_dialog_body_sim_sd_card_waterproof : R.string.sim_card_tray_protection_dialog_body_sim_card_waterproof;
    }

    public int getTitleMessage() {
        return BasicRune.POPUPUI_SD_CARD_STORAGE ? R.string.sim_sd_card_tray_protection_dialog_title : R.string.sim_card_tray_protection_dialog_title;
    }

    public SimTrayProtectionData(Context context) {
        this.mContext = context;
    }
}
