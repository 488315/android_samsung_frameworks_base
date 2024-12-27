package com.android.systemui.audio.soundcraft.viewbinding.audioeffect;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;

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
