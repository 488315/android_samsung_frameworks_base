package com.android.server.am.mars.filter.filter;

import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CarConnectedFilter implements IFilter {
    public static AnonymousClass1 listener;
    public CarConnectionQueryHandler carConnectionQueryHandler;
    public Context mContext = null;
    public final AtomicBoolean isCarConnected = new AtomicBoolean();
    public CarConnectionReceiver carConnectionReceiver = null;
    public final ArrayMap userIdPackageListSelfLocked = new ArrayMap();
    public final ArrayList logExcludeList =
            new ArrayList(List.of("com.google.android.projection.gearhead"));

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.mars.filter.filter.CarConnectedFilter$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class AndroidAutoFilterHolder {
        public static final CarConnectedFilter INSTANCE = new CarConnectedFilter();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CarConnectionQueryHandler extends AsyncQueryHandler {
        @Override // android.content.AsyncQueryHandler
        public final void onQueryComplete(int i, Object obj, Cursor cursor) {
            try {
                if (cursor == null) {
                    Slog.d("CarConnectedFilter", "Null response from content provider");
                    CarConnectedFilter.m209$$Nest$smnotifyCarDisconnected();
                    return;
                }
                int columnIndex = cursor.getColumnIndex("CarConnectionState");
                if (columnIndex < 0) {
                    Slog.d("CarConnectedFilter", "Connection to car response is missing");
                    CarConnectedFilter.m209$$Nest$smnotifyCarDisconnected();
                    return;
                }
                if (!cursor.moveToNext()) {
                    Slog.d("CarConnectedFilter", "Connection to car response is empty");
                    CarConnectedFilter.m209$$Nest$smnotifyCarDisconnected();
                    return;
                }
                int i2 = cursor.getInt(columnIndex);
                if (i2 == 0) {
                    Slog.d("CarConnectedFilter", "Android Auto disconnected");
                    CarConnectedFilter.m209$$Nest$smnotifyCarDisconnected();
                    return;
                }
                Slog.d("CarConnectedFilter", "Android Auto connected");
                Slog.d("CarConnectedFilter", "onQueryComplete: " + i2);
                CarConnectedFilter.this.isCarConnected.set(true);
                MARsUtils.addFilterDebugInfoToHistory("FILTER 32", "android auto on");
            } catch (Exception unused) {
                Slog.e("CarConnectedFilter", "Error at onQueryComplete");
                CarConnectedFilter.m209$$Nest$smnotifyCarDisconnected();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CarConnectionReceiver extends BroadcastReceiver {
        public CarConnectionReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            CarConnectedFilter.this.queryForState();
        }
    }

    /* renamed from: -$$Nest$smnotifyCarDisconnected, reason: not valid java name */
    public static void m209$$Nest$smnotifyCarDisconnected() {
        CarConnectedFilter.this.isCarConnected.set(false);
        MARsUtils.addFilterDebugInfoToHistory("FILTER 32", "android auto off");
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        this.mContext.unregisterReceiver(this.carConnectionReceiver);
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        if (!this.isCarConnected.get()) {
            return 0;
        }
        synchronized (this.userIdPackageListSelfLocked) {
            try {
                ArrayList arrayList =
                        (ArrayList) this.userIdPackageListSelfLocked.get(Integer.valueOf(i));
                if (arrayList == null) {
                    return 0;
                }
                return arrayList.contains(str) ? 32 : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        this.carConnectionQueryHandler =
                new CarConnectionQueryHandler(context.getContentResolver());
        listener = new AnonymousClass1();
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "androidx.car.app.connection.action.CAR_CONNECTION_UPDATED");
        CarConnectionReceiver carConnectionReceiver = new CarConnectionReceiver();
        this.carConnectionReceiver = carConnectionReceiver;
        this.mContext.registerReceiver(carConnectionReceiver, m, 2);
        queryForState();
    }

    public final void queryForState() {
        this.carConnectionQueryHandler.startQuery(
                42,
                null,
                new Uri.Builder()
                        .scheme("content")
                        .authority("androidx.car.app.connection")
                        .build(),
                new String[] {"CarConnectionState"},
                null,
                null,
                null);
    }
}
