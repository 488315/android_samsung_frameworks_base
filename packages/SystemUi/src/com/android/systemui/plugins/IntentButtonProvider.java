package com.android.systemui.plugins;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(version = 1)
/* loaded from: classes2.dex */
public interface IntentButtonProvider extends Plugin {
    public static final int VERSION = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface IntentButton {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class IconState {
            public Drawable drawable;
            public boolean isVisible = true;
            public CharSequence contentDescription = null;
            public boolean tint = true;
        }

        IconState getIcon();

        Intent getIntent();
    }

    IntentButton getIntentButton();
}
