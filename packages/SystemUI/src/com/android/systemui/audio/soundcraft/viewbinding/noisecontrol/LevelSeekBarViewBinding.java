package com.android.systemui.audio.soundcraft.viewbinding.noisecontrol;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class LevelSeekBarViewBinding {
    public final ImageView minusButton;
    public final ImageView plusButton;
    public final LinearLayout root;
    public final SeekBar seekbar;
    public final TextView title;

    public LevelSeekBarViewBinding(View view) {
        this.root = (LinearLayout) view.requireViewById(R.id.item_root);
        this.title = (TextView) view.requireViewById(R.id.title);
        this.seekbar = (SeekBar) view.requireViewById(R.id.seekbar);
        this.minusButton = (ImageView) view.requireViewById(R.id.minus_button);
        this.plusButton = (ImageView) view.requireViewById(R.id.plus_button);
    }
}
