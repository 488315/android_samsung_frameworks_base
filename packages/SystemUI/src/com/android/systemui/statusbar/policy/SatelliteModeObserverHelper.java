package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.AirplaneModeTile;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SatelliteModeObserverHelper {
    public final CarrierConfigManager carrierConfigManager;
    public final Context context;
    public boolean observing;
    public final SatelliteModeObserverHelper$settingsObserver$1 settingsObserver;
    public final SatelliteModeObserverHelper$telephonyCallback$1 telephonyCallback;
    public final TelephonyManager telephonyManager;
    public final Set enabledListeners = new LinkedHashSet();
    public final Set trtListeners = new LinkedHashSet();
    public final Set esosListeners = new LinkedHashSet();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.statusbar.policy.SatelliteModeObserverHelper$settingsObserver$1] */
    public SatelliteModeObserverHelper(Context context) {
        this.context = context;
        Object systemService = context.getSystemService("phone");
        this.telephonyManager = systemService instanceof TelephonyManager ? (TelephonyManager) systemService : null;
        Object systemService2 = context.getSystemService("carrier_config");
        this.carrierConfigManager = systemService2 instanceof CarrierConfigManager ? (CarrierConfigManager) systemService2 : null;
        isSupportedESOS();
        final Handler handler = new Handler(Looper.getMainLooper());
        this.settingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.SatelliteModeObserverHelper$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                boolean z2 = Settings.Global.getInt(this.this$0.context.getContentResolver(), "satellite_mode_enabled", 0) == 1;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onChange: ", "SatelliteModeObserver", z2);
                SatelliteModeObserverHelper satelliteModeObserverHelper = this.this$0;
                synchronized (satelliteModeObserverHelper.enabledListeners) {
                    try {
                        Iterator it = satelliteModeObserverHelper.enabledListeners.iterator();
                        while (it.hasNext()) {
                            ((SatelliteEnabledListener) it.next()).onSatelliteEnabledChanged(z2);
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.telephonyCallback = new SatelliteModeObserverHelper$telephonyCallback$1(this);
    }

    public final void addCallback(Object obj) {
        if (obj instanceof SatelliteEnabledListener) {
            synchronized (this.enabledListeners) {
                this.enabledListeners.add(obj);
            }
        } else if (obj instanceof SatelliteTrtListener) {
            synchronized (this.trtListeners) {
                this.trtListeners.add(obj);
            }
        } else if (obj instanceof AirplaneModeTile.AnonymousClass2) {
            synchronized (this.esosListeners) {
                this.esosListeners.add(obj);
            }
        }
        if (this.observing || totalListenerCount() <= 0) {
            return;
        }
        Log.d("SatelliteModeObserver", "startObserving:");
        this.observing = true;
        this.context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("satellite_mode_enabled"), false, this.settingsObserver);
        TelephonyManager telephonyManager = this.telephonyManager;
        if (telephonyManager != null) {
            telephonyManager.registerTelephonyCallback(new HandlerExecutor(new Handler(Looper.getMainLooper())), this.telephonyCallback);
        }
    }

    public final boolean isSupportedESOS() {
        CarrierConfigManager carrierConfigManager = this.carrierConfigManager;
        PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(SubscriptionManager.getActiveDataSubscriptionId()) : null;
        boolean z = configForSubId != null ? configForSubId.getBoolean("satellite_esos_supported_bool") : false;
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isSupportedESOS: ", "SatelliteModeObserver", z);
        return z;
    }

    public final void removeCallback(Object obj) {
        if (obj instanceof SatelliteEnabledListener) {
            synchronized (this.enabledListeners) {
                this.enabledListeners.add(obj);
            }
        } else if (obj instanceof SatelliteTrtListener) {
            synchronized (this.trtListeners) {
                this.trtListeners.add(obj);
            }
        } else if (obj instanceof AirplaneModeTile.AnonymousClass2) {
            synchronized (this.esosListeners) {
                this.esosListeners.add(obj);
            }
        }
        if (this.observing && totalListenerCount() == 0) {
            Log.d("SatelliteModeObserver", "stopObserving:");
            this.observing = false;
            this.context.getContentResolver().unregisterContentObserver(this.settingsObserver);
            TelephonyManager telephonyManager = this.telephonyManager;
            if (telephonyManager != null) {
                telephonyManager.unregisterTelephonyCallback(this.telephonyCallback);
            }
        }
    }

    public final int totalListenerCount() {
        int size;
        int size2;
        int size3;
        synchronized (this.enabledListeners) {
            size = this.enabledListeners.size();
        }
        synchronized (this.trtListeners) {
            size2 = this.trtListeners.size();
        }
        synchronized (this.esosListeners) {
            size3 = this.esosListeners.size();
        }
        return size + size2 + size3;
    }
}
