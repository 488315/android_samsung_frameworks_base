package com.android.systemui.media.dialog;

import android.content.Context;
import com.android.settingslib.Utils;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public final class MediaOutputColorSchemeLegacySystem extends MediaOutputColorSchemeLegacy {
    public final Context mContext;

    public MediaOutputColorSchemeLegacySystem(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorButtonBackground() {
        return Utils.getColorStateListDefaultColor(R.color.media_dialog_button_background, this.mContext);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorConnectedItemBackground() {
        return Utils.getColorStateListDefaultColor(R.color.media_dialog_connected_item_background, this.mContext);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorDialogBackground() {
        return Utils.getColorStateListDefaultColor(R.color.media_dialog_background, this.mContext);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorItemBackground() {
        return Utils.getColorStateListDefaultColor(R.color.media_dialog_item_background, this.mContext);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorItemContent() {
        return Utils.getColorStateListDefaultColor(R.color.media_dialog_item_main_content, this.mContext);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorPositiveButtonText() {
        return Utils.getColorStateListDefaultColor(R.color.media_dialog_solid_button_text, this.mContext);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputColorSchemeLegacy
    public final int getColorSeekbarProgress() {
        return Utils.getColorStateListDefaultColor(R.color.media_dialog_seekbar_progress, this.mContext);
    }
}
