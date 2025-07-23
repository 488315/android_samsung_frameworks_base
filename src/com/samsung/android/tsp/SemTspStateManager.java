package com.samsung.android.tsp;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewRootImpl;

/* loaded from: classes6.dex */
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

    public static void setDeadZone(View view, Bundle bundle) {
        if (view == null) {
            throw new IllegalArgumentException("The argument is null. decorView=" + view + ", deadZone=" + bundle);
        }
        if (bundle != null) {
            Log.i(TAG, "setDeadZone() v2=" + bundle.getString("deadzone_v2") + ", v3=" + bundle.getString("deadzone_v3"));
        }
        ViewParent parent = view.getRootView().getParent();
        if (parent == null || !(parent instanceof ViewRootImpl)) {
            throw new IllegalArgumentException("The decorView is not attached.");
        }
        if (bundle == null) {
            ((ViewRootImpl) parent).clearTspDeadzone();
        } else {
            ((ViewRootImpl) parent).setTspDeadzone(bundle);
        }
    }

    public static void setDeadZoneHole(Context context, Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            throw new IllegalArgumentException("deadZoneHole is null or empty");
        }
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE)).setDeadzoneHole(bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void setSensitivePalmRecognitionEnabled(View view, boolean z) {
        if (view == null || view.getViewRootImpl() == null) {
            throw new IllegalArgumentException("The decorView or the viewRootImpl is null.");
        }
        view.getViewRootImpl().setTspNoteMode(z);
    }

    public static void clearDeadZone(View view) {
        Log.i(TAG, "clearDeadZone()");
        if (view == null) {
            throw new IllegalArgumentException("The argument is null. decorView=" + view);
        }
        ViewParent parent = view.getRootView().getParent();
        if (!(parent instanceof ViewRootImpl)) {
            throw new IllegalArgumentException("The decorview is not attached.");
        }
        ((ViewRootImpl) parent).clearTspDeadzone();
    }
}
