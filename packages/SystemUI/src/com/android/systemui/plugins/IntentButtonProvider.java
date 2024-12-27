package com.android.systemui.plugins;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(version = 1)
public interface IntentButtonProvider extends Plugin {
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface IntentButton {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
