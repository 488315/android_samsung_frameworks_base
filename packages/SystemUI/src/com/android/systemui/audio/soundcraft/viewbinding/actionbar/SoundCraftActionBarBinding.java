package com.android.systemui.audio.soundcraft.viewbinding.actionbar;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView;

public final class SoundCraftActionBarBinding {
    public final View backButton;
    public final View backIcon;
    public final SoundCraftActionBarView root;
    public final TextView title;

    public SoundCraftActionBarBinding(View view) {
        this.root = (SoundCraftActionBarView) view.requireViewById(R.id.action_bar);
        this.backButton = view.requireViewById(R.id.action_arrow);
        this.backIcon = view.requireViewById(R.id.iv_action_arrow);
        this.title = (TextView) view.requireViewById(R.id.action_bar_text);
    }
}
