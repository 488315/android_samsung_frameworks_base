package com.samsung.android.tsp;

import android.content.Context;
import android.p009os.Bundle;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewRootImpl;

/* loaded from: classes5.dex */
public final class SemTspStateManager {
  public static final String DEAD_ZONE_DIRECTION = "dead_zone_direction";
  public static final String DEAD_ZONE_LAND_X1 = "dead_zone_land_x1";
  public static final String DEAD_ZONE_PORT_REAL_Y1 = "dead_zone_port_real_y1";
  public static final String DEAD_ZONE_PORT_X1 = "dead_zone_port_x1";
  public static final String DEAD_ZONE_PORT_X2 = "dead_zone_port_x2";
  public static final String DEAD_ZONE_PORT_Y1 = "dead_zone_port_y1";
  public static final String DEAD_ZONE_PORT_Y2 = "dead_zone_port_y2";
  public static final String DEAD_ZONE_SET_PROCESS_NAME = "dead_zone_process_name";
  public static final String EDGE_ZONE_LAND = "edge_zone_land";
  public static final String EDGE_ZONE_PORT = "edge_zone_port";
  public static final String EDGE_ZONE_WIDTH = "edge_zone_width";
  private static final String TAG = "SemTspStateManager";

  public static void setDeadZone(View decorView, Bundle deadZone) {
    if (decorView == null) {
      throw new IllegalArgumentException(
          "The argument is null. decorView=" + decorView + ", deadZone=" + deadZone);
    }
    String cmd = deadZone == null ? null : deadZone.getString("deadzone_v2");
    Log.m98i(TAG, "setDeadZone() cmd=" + cmd);
    View rootView = decorView.getRootView();
    ViewParent parentView = rootView.getParent();
    if (parentView == null || !(parentView instanceof ViewRootImpl)) {
      throw new IllegalArgumentException("The decorView is not attached.");
    }
    if (deadZone == null) {
      ((ViewRootImpl) parentView).clearTspDeadzone();
    } else {
      ((ViewRootImpl) parentView).setTspDeadzone(deadZone);
    }
  }

  public static void setDeadZoneHole(Context context, Bundle deadZoneHole) {
    if (deadZoneHole == null || deadZoneHole.isEmpty()) {
      throw new IllegalArgumentException("deadZoneHole is null or empty");
    }
    try {
      IWindowManager windowManager =
          IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
      windowManager.setDeadzoneHole(deadZoneHole);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public static void setSensitivePalmRecognitionEnabled(View decorView, boolean enabled) {
    if (decorView == null || decorView.getViewRootImpl() == null) {
      throw new IllegalArgumentException("The decorView or the viewRootImpl is null.");
    }
    ViewRootImpl viewRootImpl = decorView.getViewRootImpl();
    viewRootImpl.setTspNoteMode(enabled);
  }

  public static void clearDeadZone(View decorView) {
    Log.m98i(TAG, "clearDeadZone()");
    if (decorView == null) {
      throw new IllegalArgumentException("The argument is null. decorView=" + decorView);
    }
    View rootView = decorView.getRootView();
    ViewParent parentView = rootView.getParent();
    if (!(parentView instanceof ViewRootImpl)) {
      throw new IllegalArgumentException("The decorview is not attached.");
    }
    ((ViewRootImpl) parentView).clearTspDeadzone();
  }
}
