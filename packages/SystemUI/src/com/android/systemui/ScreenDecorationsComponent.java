package com.android.systemui;

import android.content.Context;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.statusbar.events.PrivacyDotViewController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ScreenDecorationsComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ScreenDecorationsComponent create(Context context, PrivacyDotViewController privacyDotViewController, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory);
    }
}
