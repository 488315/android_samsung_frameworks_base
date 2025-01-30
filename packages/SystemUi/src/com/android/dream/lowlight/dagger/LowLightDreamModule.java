package com.android.dream.lowlight.dagger;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LowLightDreamModule {
    public static final LowLightDreamModule INSTANCE = new LowLightDreamModule();

    private LowLightDreamModule() {
    }

    public static ComponentName providesLowLightDreamComponent(Context context) {
        String string = context.getResources().getString(R.string.config_lowLightDreamComponent);
        if (string.length() == 0) {
            return null;
        }
        return ComponentName.unflattenFromString(string);
    }
}
