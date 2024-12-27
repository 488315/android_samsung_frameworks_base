package com.android.server.vr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.ArraySet;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public final class SettingsObserver {
    public final AnonymousClass2 mContentObserver;
    public final Set mSettingsListeners = new ArraySet();

    public SettingsObserver(Context context, Handler handler, final Uri uri) {
        new BroadcastReceiver() { // from class: com.android.server.vr.SettingsObserver.1
            public final /* synthetic */ String val$secureSettingName = "enabled_vr_listeners";

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.os.action.SETTING_RESTORED".equals(intent.getAction())
                        && Objects.equals(
                                intent.getStringExtra("setting_name"),
                                this.val$secureSettingName)) {
                    intent.getStringExtra("previous_value");
                    intent.getStringExtra("new_value");
                    SettingsObserver settingsObserver = SettingsObserver.this;
                    getSendingUserId();
                    Iterator it = ((ArraySet) settingsObserver.mSettingsListeners).iterator();
                    while (it.hasNext()) {
                        ((EnabledComponentsObserver) it.next()).rebuildAll();
                    }
                }
            }
        };
        context.getContentResolver()
                .registerContentObserver(
                        uri,
                        false,
                        new ContentObserver(
                                handler) { // from class: com.android.server.vr.SettingsObserver.2
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z, Uri uri2) {
                                if (uri2 == null || uri.equals(uri2)) {
                                    Iterator it =
                                            ((ArraySet) SettingsObserver.this.mSettingsListeners)
                                                    .iterator();
                                    while (it.hasNext()) {
                                        ((EnabledComponentsObserver) it.next()).rebuildAll();
                                    }
                                }
                            }
                        },
                        -1);
    }
}
