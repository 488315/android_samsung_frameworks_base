package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

/* loaded from: classes2.dex */
public final class NaturalScrollingSettingObserver {
    public final Context context;

    public NaturalScrollingSettingObserver(final Handler handler, Context context) {
        this.context = context;
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("touchpad_natural_scrolling"), false, new ContentObserver(handler) { // from class: com.android.systemui.keyguard.domain.interactor.NaturalScrollingSettingObserver$contentObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                NaturalScrollingSettingObserver naturalScrollingSettingObserver = this.this$0;
                naturalScrollingSettingObserver.getClass();
                try {
                    Settings.System.getIntForUser(naturalScrollingSettingObserver.context.getContentResolver(), "touchpad_natural_scrolling", -2);
                } catch (Settings.SettingNotFoundException unused) {
                }
            }
        });
    }
}
