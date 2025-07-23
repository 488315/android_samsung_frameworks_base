package com.android.systemui.audio.soundcraft.viewbinding.audioeffect;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.audioeffect.AudioEffectHeaderView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioEffectHeaderViewBinding {
    public final ImageView icon;
    public final AudioEffectHeaderView root;
    public final TextView title;

    public AudioEffectHeaderViewBinding(View view) {
        AudioEffectHeaderView audioEffectHeaderView = (AudioEffectHeaderView) view.requireViewById(R.id.soundcraft_audio_effect_header);
        this.root = audioEffectHeaderView;
        this.icon = (ImageView) audioEffectHeaderView.requireViewById(R.id.effect_box_header_icon);
        this.title = (TextView) audioEffectHeaderView.requireViewById(R.id.effect_box_header_title);
    }
}
