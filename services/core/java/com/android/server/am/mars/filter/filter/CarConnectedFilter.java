package com.android.server.am.mars.filter.filter;

import android.content.AsyncQueryHandler;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/* loaded from: classes.dex */
public class CarConnectedFilter implements IFilter {
  public static OnCarConnectionStateListener listener;
  public CarConnectionQueryHandler carConnectionQueryHandler;
  public Context mContext = null;
  public final AtomicBoolean isCarConnected = new AtomicBoolean();
  public CarConnectionReceiver carConnectionReceiver = null;
  public final int CONNECTION_TYPE_NATIVE = 1;
  public final int CONNECTION_TYPE_PROJECTION = 2;
  public ArrayMap userIdPackageListSelfLocked = new ArrayMap();
  public final ArrayList logExcludeList =
      new ArrayList(List.of("com.google.android.projection.gearhead"));

  public abstract class AndroidAutoFilterHolder {
    public static final CarConnectedFilter INSTANCE = new CarConnectedFilter();
  }

  public interface OnCarConnectionStateListener {
    void onCarConnected();

    void onCarDisconnected();
  }

  public static CarConnectedFilter getInstance() {
    return AndroidAutoFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    this.mContext = context;
    this.carConnectionQueryHandler = new CarConnectionQueryHandler(context.getContentResolver());
    listener =
        new OnCarConnectionStateListener() { // from class:
                                             // com.android.server.am.mars.filter.filter.CarConnectedFilter.1
          @Override // com.android.server.am.mars.filter.filter.CarConnectedFilter.OnCarConnectionStateListener
          public void onCarConnected() {
            CarConnectedFilter.this.isCarConnected.set(true);
            MARsPolicyManager.getInstance()
                .addFilterDebugInfoToHistory("FILTER 31", "android auto on");
          }

          @Override // com.android.server.am.mars.filter.filter.CarConnectedFilter.OnCarConnectionStateListener
          public void onCarDisconnected() {
            CarConnectedFilter.this.isCarConnected.set(false);
            MARsPolicyManager.getInstance()
                .addFilterDebugInfoToHistory("FILTER 31", "android auto off");
          }
        };
    registerReceiver();
    queryForState();
  }

  public final void registerReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("androidx.car.app.connection.action.CAR_CONNECTION_UPDATED");
    CarConnectionReceiver carConnectionReceiver = new CarConnectionReceiver();
    this.carConnectionReceiver = carConnectionReceiver;
    this.mContext.registerReceiver(carConnectionReceiver, intentFilter);
  }

  public void onAutoAppBind(String str, int i) {
    synchronized (this.userIdPackageListSelfLocked) {
      ArrayList arrayList =
          (ArrayList)
              this.userIdPackageListSelfLocked.computeIfAbsent(
                  Integer.valueOf(i),
                  new Function() { // from class:
                                   // com.android.server.am.mars.filter.filter.CarConnectedFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      ArrayList lambda$onAutoAppBind$0;
                      lambda$onAutoAppBind$0 =
                          CarConnectedFilter.lambda$onAutoAppBind$0((Integer) obj);
                      return lambda$onAutoAppBind$0;
                    }
                  });
      if (arrayList.contains(str)) {
        return;
      }
      arrayList.add(str);
      if (this.logExcludeList.contains(str)) {
        return;
      }
      MARsPolicyManager.getInstance().addFilterDebugInfoToHistory("FILTER 31", str);
    }
  }

  public static /* synthetic */ ArrayList lambda$onAutoAppBind$0(Integer num) {
    return new ArrayList();
  }

  public void onAutoAppUnbind(String str, int i) {
    synchronized (this.userIdPackageListSelfLocked) {
      ArrayList arrayList = (ArrayList) this.userIdPackageListSelfLocked.get(Integer.valueOf(i));
      if (arrayList == null) {
        return;
      }
      if (arrayList.contains(str)) {
        arrayList.remove(str);
        if (this.logExcludeList.contains(str)) {
          return;
        }
        MARsPolicyManager.getInstance().addFilterDebugInfoToHistory("FILTER 31 remove", str);
      }
    }
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    this.mContext.unregisterReceiver(this.carConnectionReceiver);
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    if (!this.isCarConnected.get()) {
      return 0;
    }
    synchronized (this.userIdPackageListSelfLocked) {
      ArrayList arrayList = (ArrayList) this.userIdPackageListSelfLocked.get(Integer.valueOf(i));
      if (arrayList == null) {
        return 0;
      }
      return arrayList.contains(str) ? 31 : 0;
    }
  }

  public final class CarConnectionReceiver extends BroadcastReceiver {
    public CarConnectionReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      CarConnectedFilter.this.queryForState();
    }
  }

  public final void queryForState() {
    this.carConnectionQueryHandler.startQuery(
        42,
        null,
        new Uri.Builder().scheme("content").authority("androidx.car.app.connection").build(),
        new String[] {"CarConnectionState"},
        null,
        null,
        null);
  }

  public static void notifyCarConnected() {
    listener.onCarConnected();
  }

  public static void notifyCarDisconnected() {
    listener.onCarDisconnected();
  }

  public class CarConnectionQueryHandler extends AsyncQueryHandler {
    public CarConnectionQueryHandler(ContentResolver contentResolver) {
      super(contentResolver);
    }

    @Override // android.content.AsyncQueryHandler
    public void onQueryComplete(int i, Object obj, Cursor cursor) {
      try {
        if (cursor == null) {
          Slog.d("CarConnectedFilter", "Null response from content provider");
          CarConnectedFilter.notifyCarDisconnected();
          return;
        }
        int columnIndex = cursor.getColumnIndex("CarConnectionState");
        if (columnIndex < 0) {
          Slog.d("CarConnectedFilter", "Connection to car response is missing");
          CarConnectedFilter.notifyCarDisconnected();
          return;
        }
        if (!cursor.moveToNext()) {
          Slog.d("CarConnectedFilter", "Connection to car response is empty");
          CarConnectedFilter.notifyCarDisconnected();
          return;
        }
        int i2 = cursor.getInt(columnIndex);
        if (i2 == 0) {
          Slog.d("CarConnectedFilter", "Android Auto disconnected");
          CarConnectedFilter.notifyCarDisconnected();
          return;
        }
        Slog.d("CarConnectedFilter", "Android Auto connected");
        Slog.d("CarConnectedFilter", "onQueryComplete: " + i2);
        CarConnectedFilter.notifyCarConnected();
      } catch (Exception unused) {
        Slog.e("CarConnectedFilter", "Error at onQueryComplete");
        CarConnectedFilter.notifyCarDisconnected();
      }
    }
  }
}
