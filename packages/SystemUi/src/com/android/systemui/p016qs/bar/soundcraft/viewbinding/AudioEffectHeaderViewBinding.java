package com.android.systemui.p016qs.bar.soundcraft.viewbinding;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.soundcraft.view.audioeffect.AudioEffectHeaderView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioEffectHeaderViewBinding {
    public final ImageView icon;
    public final AudioEffectHeaderView root;
    public final TextView title;

    public AudioEffectHeaderViewBinding(View view) {
        AudioEffectHeaderView audioEffectHeaderView = (AudioEffectHeaderView) view.findViewById(R.id.soundcraft_audio_effect_header);
        this.root = audioEffectHeaderView;
        this.icon = (ImageView) audioEffectHeaderView.findViewById(R.id.effect_box_header_icon);
        this.title = (TextView) audioEffectHeaderView.findViewById(R.id.effect_box_header_title);
    }
}
