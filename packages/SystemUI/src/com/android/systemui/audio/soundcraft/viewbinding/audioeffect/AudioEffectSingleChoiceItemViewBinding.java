package com.android.systemui.audio.soundcraft.viewbinding.audioeffect;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioEffectSingleChoiceItemViewBinding {
    public final TextView name;
    public final LinearLayout root;
    public final TextView status;

    public AudioEffectSingleChoiceItemViewBinding(View view) {
        this.root = (LinearLayout) view.requireViewById(R.id.item_root);
        this.name = (TextView) view.requireViewById(R.id.item_name);
        this.status = (TextView) view.requireViewById(R.id.item_status);
    }
}
