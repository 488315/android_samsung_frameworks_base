package com.android.systemui.audio.soundcraft.viewbinding.noisecontrol;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
