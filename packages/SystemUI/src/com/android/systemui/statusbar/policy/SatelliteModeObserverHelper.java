package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SatelliteModeObserverHelper {
    public final List callbacks;
    public boolean isEnabled;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Context context;
        public final Uri uri;

        public SettingsObserver(Context context) {
            super(new Handler());
            this.context = context;
            this.uri = Settings.Global.getUriFor(SettingsHelper.INDEX_STATUS_SATELLITE_MODE_ENABLED);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            SatelliteModeObserverHelper.this.isEnabled = Settings.Global.getInt(this.context.getContentResolver(), SettingsHelper.INDEX_STATUS_SATELLITE_MODE_ENABLED, 0) == 1;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onChange: ", "SatelliteModeObserver", SatelliteModeObserverHelper.this.isEnabled);
            SatelliteModeObserverHelper satelliteModeObserverHelper = SatelliteModeObserverHelper.this;
            synchronized (satelliteModeObserverHelper.callbacks) {
                try {
                    Iterator it = satelliteModeObserverHelper.callbacks.iterator();
                    while (it.hasNext()) {
                        ((SatelliteModeObserver$SatelliteModeCallback) it.next()).onSatelliteModeChanged(satelliteModeObserverHelper.isEnabled);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    public SatelliteModeObserverHelper(Context context) {
        SettingsObserver settingsObserver = new SettingsObserver(context);
        this.callbacks = new ArrayList();
        if (settingsObserver.uri == null) {
            return;
        }
        settingsObserver.context.getContentResolver().registerContentObserver(settingsObserver.uri, false, settingsObserver);
    }

    public final void addCallback(SatelliteModeObserver$SatelliteModeCallback satelliteModeObserver$SatelliteModeCallback) {
        synchronized (this.callbacks) {
            ((ArrayList) this.callbacks).add(satelliteModeObserver$SatelliteModeCallback);
            satelliteModeObserver$SatelliteModeCallback.onSatelliteModeChanged(this.isEnabled);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void removeCallback(SatelliteModeObserver$SatelliteModeCallback satelliteModeObserver$SatelliteModeCallback) {
        synchronized (this.callbacks) {
            ((ArrayList) this.callbacks).remove(satelliteModeObserver$SatelliteModeCallback);
        }
    }
}
