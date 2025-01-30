package com.android.systemui.qs.bar.soundcraft.viewbinding;

import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.view.audioeffect.AudioEffectBoxView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioEffectBoxViewBinding {
    public final LinearLayout effectItemList;
    public final AudioEffectHeaderViewBinding header;
    public final AudioEffectBoxView root;

    public AudioEffectBoxViewBinding(View view) {
        AudioEffectBoxView audioEffectBoxView = (AudioEffectBoxView) view.findViewById(R.id.soundcraft_audio_effect_box);
        this.root = audioEffectBoxView;
        this.header = new AudioEffectHeaderViewBinding(audioEffectBoxView);
        this.effectItemList = (LinearLayout) audioEffectBoxView.findViewById(R.id.soundcraft_audio_effect_item_list);
    }
}
