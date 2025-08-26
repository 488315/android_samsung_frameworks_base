package com.android.systemui.audio.soundcraft.viewbinding.audioeffect;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectBoxView;

/* loaded from: classes.dex */
public final class AudioEffectBoxLayoutBinding {
    public final AudioEffectBoxView box;
    public final TextView detailJumpButton;
    public final LinearLayout effectItemList;
    public final TextView fallbackText;
    public final AudioEffectHeaderViewBinding header;
    public final View root;

    public AudioEffectBoxLayoutBinding(View view) {
        View viewRequireViewById = view.requireViewById(R.id.soundcraft_audio_effect_box_root);
        this.root = viewRequireViewById;
        this.box = (AudioEffectBoxView) view.requireViewById(R.id.soundcraft_audio_effect_box);
        this.header = new AudioEffectHeaderViewBinding(viewRequireViewById);
        this.effectItemList = (LinearLayout) viewRequireViewById.requireViewById(R.id.soundcraft_audio_effect_item_list);
        this.fallbackText = (TextView) viewRequireViewById.requireViewById(R.id.soundcraft_soundeffect_fallback_text);
        this.detailJumpButton = (TextView) viewRequireViewById.requireViewById(R.id.soundcraft_detail_jump_button);
    }
}
