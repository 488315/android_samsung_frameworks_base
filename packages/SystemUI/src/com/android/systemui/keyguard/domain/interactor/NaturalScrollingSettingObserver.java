package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

public final class NaturalScrollingSettingObserver {
    public final Context context;
    public boolean isInitialized;
    public boolean isNaturalScrollingEnabled = true;

    public NaturalScrollingSettingObserver(final Handler handler, Context context) {
        this.context = context;
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("touchpad_natural_scrolling"), false, new ContentObserver(handler) { // from class: com.android.systemui.keyguard.domain.interactor.NaturalScrollingSettingObserver$contentObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                NaturalScrollingSettingObserver.this.update();
            }
        });
    }

    public final void update() {
        boolean z = true;
        try {
            if (Settings.System.getIntForUser(this.context.getContentResolver(), "touchpad_natural_scrolling", -2) != 1) {
                z = false;
            }
        } catch (Settings.SettingNotFoundException unused) {
        }
        this.isNaturalScrollingEnabled = z;
    }
}
