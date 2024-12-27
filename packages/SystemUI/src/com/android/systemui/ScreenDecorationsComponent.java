package com.android.systemui;

import android.content.Context;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.statusbar.events.PrivacyDotViewController;

public interface ScreenDecorationsComponent {

    public interface Factory {
        ScreenDecorationsComponent create(Context context, PrivacyDotViewController privacyDotViewController, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory);
    }

    ScreenDecorations getScreenDecorations();
}
