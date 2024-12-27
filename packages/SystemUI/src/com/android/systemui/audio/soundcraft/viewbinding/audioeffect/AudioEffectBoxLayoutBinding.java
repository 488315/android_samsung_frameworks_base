package com.android.systemui.audio.soundcraft.viewbinding.audioeffect;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AudioEffectBoxLayoutBinding {
    public final AudioEffectBoxView box;
    public final TextView detailJumpButton;
    public final LinearLayout effectItemList;
    public final TextView fallbackText;
    public final AudioEffectHeaderViewBinding header;
    public final View root;

    public AudioEffectBoxLayoutBinding(View view) {
        View requireViewById = view.requireViewById(R.id.soundcraft_audio_effect_box_root);
        this.root = requireViewById;
        this.box = (AudioEffectBoxView) view.requireViewById(R.id.soundcraft_audio_effect_box);
        this.header = new AudioEffectHeaderViewBinding(requireViewById);
        this.effectItemList = (LinearLayout) requireViewById.requireViewById(R.id.soundcraft_audio_effect_item_list);
        this.fallbackText = (TextView) requireViewById.requireViewById(R.id.soundcraft_soundeffect_fallback_text);
        this.detailJumpButton = (TextView) requireViewById.requireViewById(R.id.soundcraft_detail_jump_button);
    }
}
