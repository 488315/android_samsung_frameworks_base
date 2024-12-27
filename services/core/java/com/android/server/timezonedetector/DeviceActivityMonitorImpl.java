package com.android.server.timezonedetector;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.server.timezonedetector.TimeZoneDetectorService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DeviceActivityMonitorImpl implements Dumpable {
    public final List mListeners = new ArrayList();

    /* renamed from: -$$Nest$mnotifyFlightComplete, reason: not valid java name */
    public static void m973$$Nest$mnotifyFlightComplete(DeviceActivityMonitorImpl deviceActivityMonitorImpl) {
        ArrayList arrayList;
        synchronized (deviceActivityMonitorImpl) {
            arrayList = new ArrayList(deviceActivityMonitorImpl.mListeners);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((TimeZoneDetectorStrategyImpl) ((TimeZoneDetectorService.Lifecycle.AnonymousClass1) it.next()).val$timeZoneDetectorStrategy).enableTelephonyTimeZoneFallback("onFlightComplete()");
        }
    }

    public DeviceActivityMonitorImpl(Context context, Handler handler) {
        final ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("airplane_mode_on"), true, new ContentObserver(handler) { // from class: com.android.server.timezonedetector.DeviceActivityMonitorImpl.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                try {
                    if (Settings.Global.getInt(contentResolver, "airplane_mode_on") == 0) {
                        DeviceActivityMonitorImpl.m973$$Nest$mnotifyFlightComplete(DeviceActivityMonitorImpl.this);
                    }
                } catch (Settings.SettingNotFoundException e) {
                    Slog.e("time_zone_detector", "Unable to read airplane mode state", e);
                }
            }
        });
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public final void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
    }
}
