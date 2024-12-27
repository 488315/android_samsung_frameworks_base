package com.android.systemui.audio.soundcraft.viewbinding.actionbar;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.actionbar.SoundCraftActionBarView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
