package com.android.systemui.highlight;

import android.content.Context;
import com.android.systemui.highlight.interaction.InteractionRecorder;

/* loaded from: classes2.dex */
public class HighlightManager {
    public HighlightManager(Context context) {
        new InteractionRecorder(context);
    }
}
