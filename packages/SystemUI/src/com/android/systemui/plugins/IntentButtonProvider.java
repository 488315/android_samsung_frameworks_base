package com.android.systemui.plugins;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 1)
public interface IntentButtonProvider extends Plugin {
    public static final int VERSION = 1;

    public interface IntentButton {

        public class IconState {
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
