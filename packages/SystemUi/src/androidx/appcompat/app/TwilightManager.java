package androidx.appcompat.app;

import android.content.Context;
import android.location.LocationManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TwilightManager {
    public static TwilightManager sInstance;
    public final Context mContext;
    public final LocationManager mLocationManager;
    public final TwilightState mTwilightState = new TwilightState();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TwilightState {
        public boolean isNight;
        public long nextUpdate;
    }

    public TwilightManager(Context context, LocationManager locationManager) {
        this.mContext = context;
        this.mLocationManager = locationManager;
    }
}
