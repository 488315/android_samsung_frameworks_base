package com.android.systemui;

import android.content.Context;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.statusbar.events.PrivacyDotViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ScreenDecorationsComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        ScreenDecorationsComponent create(Context context, PrivacyDotViewController privacyDotViewController, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory);
    }

    ScreenDecorations getScreenDecorations();
}
