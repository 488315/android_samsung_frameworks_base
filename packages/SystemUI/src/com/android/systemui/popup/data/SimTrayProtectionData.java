package com.android.systemui.popup.data;

import android.content.Context;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SimTrayProtectionData {
    private Context mContext;

    public SimTrayProtectionData() {
    }

    public int getBodyImage() {
        return R.drawable.sim_card_tray_normal_dialog_animation;
    }

    public int getBodyImageHeight() {
        return BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD ? R.dimen.sim_card_tray_protection_dialog_body_image_height_folder : R.dimen.sim_card_tray_protection_dialog_body_image_height;
    }

    public int getBodyMessageInsertingSimCard() {
        return this.mContext.getResources().getBoolean(R.bool.config_enableCustomeSimTrayPopupText) ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_model_Q5_type : BasicRune.POPUPUI_MODEL_TYPE_WINNER ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_model_winner_type : this.mContext.getResources().getBoolean(R.bool.config_enableSimTrayPopupText) ? BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_layer_folder_type : R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_layer_type : BasicRune.POPUPUI_FOLDERBLE_TYPE_FOLD ? R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_normal_folder_type : R.string.sim_card_tray_protection_dialog_body_inserting_sim_card_normal_type;
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
