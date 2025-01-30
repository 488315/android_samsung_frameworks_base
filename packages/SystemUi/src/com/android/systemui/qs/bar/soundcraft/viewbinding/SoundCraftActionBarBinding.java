package com.android.systemui.qs.bar.soundcraft.viewbinding;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.qs.bar.soundcraft.view.actionbar.SoundCraftActionBarView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SoundCraftActionBarBinding {
    public final View backButton;
    public final View backIcon;
    public final SoundCraftActionBarView root;
    public final TextView title;

    public SoundCraftActionBarBinding(View view) {
        this.root = (SoundCraftActionBarView) view.findViewById(R.id.action_bar);
        this.backButton = view.findViewById(R.id.action_arrow);
        this.backIcon = view.findViewById(R.id.iv_action_arrow);
        this.title = (TextView) view.findViewById(R.id.action_bar_text);
    }
}
