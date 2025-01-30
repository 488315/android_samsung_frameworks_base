package com.android.server.enterprise.geofencing;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.KpuHelper;
import com.android.server.enterprise.utils.Utils;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.location.BoundingBox;
import com.samsung.android.knox.location.CircularGeofence;
import com.samsung.android.knox.location.Geofence;
import com.samsung.android.knox.location.IGeofencing;
import com.samsung.android.knox.location.LatLongPoint;
import com.samsung.android.knox.location.LinearGeofence;
import com.samsung.android.knox.location.PolygonalGeofence;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class GeofenceService extends IGeofencing.Stub implements EnterpriseServiceCallback {
    public static GeoLocationListener mLocationListener;
    public final Context mContext;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public Location mLocation;
    public LocationManager mLocationManager;
    public final BroadcastReceiver mReceiver;
    public BroadcastReceiver mUserRemovedReceiver;
    public HandlerThread mHandlerThread = null;
    public GeofencingHandler mHandler = null;
    public HashMap mActiveGeofenceList = new HashMap();

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    public GeofenceService(Context context) {
        this.mEDM = null;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.geofencing.GeofenceService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action == null || !action.equals("android.location.PROVIDERS_CHANGED")) {
                    return;
                }
                Message obtainMessage = GeofenceService.this.getHandler().obtainMessage(3);
                obtainMessage.arg1 = UserHandle.myUserId();
                GeofenceService.this.getHandler().sendMessage(obtainMessage);
            }
        };
        this.mReceiver = broadcastReceiver;
        this.mUserRemovedReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.geofencing.GeofenceService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int intExtra;
                String action = intent.getAction();
                if (action != null && action.equals("android.intent.action.USER_REMOVED")) {
                    Message obtainMessage = GeofenceService.this.getHandler().obtainMessage(2);
                    obtainMessage.arg1 = UserHandle.myUserId();
                    GeofenceService.this.getHandler().sendMessage(obtainMessage);
                } else {
                    if (action == null || !action.equals("android.intent.action.USER_STOPPED") || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1)) < 1) {
                        return;
                    }
                    Iterator it = GeofenceService.this.mEdmStorageProvider.getIntListAsUser("ADMIN", "adminUid", intExtra).iterator();
                    while (it.hasNext()) {
                        GeofenceService.this.deleteGeofenceActiveListByAdmin(((Integer) it.next()).intValue());
                    }
                }
            }
        };
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        initializeHandlerThread();
        this.mEDM = EnterpriseDeviceManager.getInstance(context);
        context.registerReceiver(broadcastReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
    }

    public final void initializeHandlerThread() {
        HandlerThread handlerThread = new HandlerThread("GeofenceService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new GeofencingHandler(this.mHandlerThread.getLooper());
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public final void setLocationManager() {
        this.mLocationManager = (LocationManager) this.mContext.getSystemService("location");
    }

    public final class GeofencingHandler extends Handler {
        public GeofencingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message != null) {
                int i = message.what;
                if (i != 1) {
                    if (i == 2) {
                        GeofenceService.this.loadGeofenceActiveList(message.arg1);
                        GeofenceService.this.checkMonitoring();
                        return;
                    } else {
                        if (i != 3) {
                            return;
                        }
                        GeofenceService.this.checkMonitoring();
                        return;
                    }
                }
                int intValue = ((Integer) message.obj).intValue();
                List isDeviceInsideGeofence = GeofenceService.this.isDeviceInsideGeofence(intValue);
                if (isDeviceInsideGeofence == null || isDeviceInsideGeofence.isEmpty()) {
                    return;
                }
                Intent intent = new Intent("com.samsung.android.knox.intent.action.DEVICE_INSIDE_GEOFENCE");
                int[] iArr = new int[isDeviceInsideGeofence.size()];
                for (int i2 = 0; i2 < isDeviceInsideGeofence.size(); i2++) {
                    iArr[i2] = ((Integer) isDeviceInsideGeofence.get(i2)).intValue();
                }
                intent.putExtra("com.samsung.android.knox.intent.extra.ID", iArr);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", UserHandle.getUserId(intValue));
                intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", intValue);
                intent.setPackage(GeofenceService.this.getPackageNameForUid(intValue));
                GeofenceService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                try {
                    String kpuPackageName = KpuHelper.getInstance(GeofenceService.this.mContext).getKpuPackageName();
                    Intent intent2 = new Intent(intent);
                    intent2.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", GeofenceService.this.mContext.getPackageManager().getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
                    intent2.setPackage(kpuPackageName);
                    GeofenceService.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        this.mContext.registerReceiver(this.mUserRemovedReceiver, intentFilter);
        Message obtainMessage = getHandler().obtainMessage(2);
        obtainMessage.arg1 = UserHandle.myUserId();
        getHandler().sendMessageDelayed(obtainMessage, 5000L);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        deleteGeofenceActiveListByAdmin(i);
        getHandler().sendMessage(getHandler().obtainMessage(3));
    }

    public final ContextInfo enforceGeofencingPermission(ContextInfo contextInfo) {
        return this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_GEOFENCING")));
    }

    public final class GeoLocationListener implements LocationListener {
        @Override // android.location.LocationListener
        public void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public GeoLocationListener() {
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(Location location) {
            GeofenceService.this.mLocation = location;
            GeofenceService.this.checkDeviceInsideOrOutsideGeo(location, true);
        }

        @Override // android.location.LocationListener
        public void onProviderDisabled(String str) {
            GeofenceService.this.mLocation = null;
        }
    }

    public int createGeofence(ContextInfo contextInfo, Geofence geofence) {
        int i = geofence.type;
        if (i == 1) {
            return createGeofence(contextInfo, (CircularGeofence) geofence);
        }
        if (i == 2) {
            return createGeofence(contextInfo, (PolygonalGeofence) geofence);
        }
        if (i == 3) {
            return createGeofence(contextInfo, (LinearGeofence) geofence);
        }
        return -1;
    }

    public int createGeofence(ContextInfo contextInfo, LinearGeofence linearGeofence) {
        List findCollinear;
        Log.d("GeofenceService", "createGeofence");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i = enforceGeofencingPermission.mCallerUid;
        double d = linearGeofence.width;
        new ArrayList();
        double d2 = linearGeofence.width;
        if (d2 <= 0.0d) {
            linearGeofence.width = 1.0d;
        } else if (d2 > 1000000.0d) {
            linearGeofence.width = 999999.0d;
        }
        List createPolygonalPoints2 = createPolygonalPoints2(linearGeofence.points, linearGeofence.width);
        int i2 = -1;
        if (createPolygonalPoints2.size() > 2 && validatePolygonalGeofence(createPolygonalPoints2) && (findCollinear = findCollinear(createPolygonalPoints2)) != null && findCollinear.size() > 2) {
            i2 = writeGeofenceToDB(enforceGeofencingPermission, 3, serializeGeoFence(new LinearGeofence(createPolygonalPoints2, findCollinear, calcBoundingBox(findCollinear), d)));
            if (isGeofencingEnabled(enforceGeofencingPermission)) {
                getHandler().sendMessage(getHandler().obtainMessage(1, Integer.valueOf(i)));
            }
        }
        return i2;
    }

    public int createGeofence(ContextInfo contextInfo, CircularGeofence circularGeofence) {
        Log.d("GeofenceService", "createGeofence");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i = enforceGeofencingPermission.mCallerUid;
        int i2 = -1;
        if (validateCircularGeofence(circularGeofence.center, circularGeofence.radius)) {
            byte[] serializeGeoFence = serializeGeoFence(circularGeofence);
            if (serializeGeoFence == null) {
                return -1;
            }
            i2 = writeGeofenceToDB(enforceGeofencingPermission, 1, serializeGeoFence);
            if (isGeofencingEnabled(enforceGeofencingPermission)) {
                getHandler().sendMessage(getHandler().obtainMessage(1, Integer.valueOf(i)));
            }
        }
        return i2;
    }

    public int createGeofence(ContextInfo contextInfo, PolygonalGeofence polygonalGeofence) {
        List findCollinear;
        Log.d("GeofenceService", "createGeofence");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i = enforceGeofencingPermission.mCallerUid;
        int i2 = -1;
        if (polygonalGeofence.points.size() > 2 && validatePolygonalGeofence(polygonalGeofence.points) && (findCollinear = findCollinear(polygonalGeofence.points)) != null && findCollinear.size() > 2) {
            BoundingBox calcBoundingBox = calcBoundingBox(findCollinear);
            double d = polygonalGeofence.graceDistance;
            i2 = writeGeofenceToDB(enforceGeofencingPermission, 2, serializeGeoFence(new PolygonalGeofence(polygonalGeofence.points, polygonalGeofence.graceDistance, findCollinear, d == 0.0d ? findCollinear : createGracePoints(findCollinear, d), calcBoundingBox)));
            if (isGeofencingEnabled(enforceGeofencingPermission)) {
                getHandler().sendMessage(getHandler().obtainMessage(1, Integer.valueOf(i)));
            }
        }
        return i2;
    }

    public synchronized boolean destroyGeofence(ContextInfo contextInfo, int i) {
        boolean deleteFromDB;
        Log.d("GeofenceService", "destroyGeofence");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i2 = enforceGeofencingPermission.mCallerUid;
        deleteFromDB = deleteFromDB(enforceGeofencingPermission, i);
        if (deleteFromDB && !isAdminHasGeofence(i2)) {
            this.mEdmStorageProvider.putInt(i2, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0);
            getHandler().sendMessage(getHandler().obtainMessage(3));
        } else if (deleteFromDB) {
            getHandler().sendMessage(getHandler().obtainMessage(1, Integer.valueOf(i2)));
        }
        return deleteFromDB;
    }

    public boolean startGeofencing(ContextInfo contextInfo) {
        Log.d("GeofenceService", "startGeofencing");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i = enforceGeofencingPermission.mCallerUid;
        if (!isAdminHasGeofence(i)) {
            return false;
        }
        boolean isGeofencingEnabled = isGeofencingEnabled(enforceGeofencingPermission);
        synchronized (this) {
            if (isGeofencingEnabled) {
                if (mLocationListener != null) {
                    return true;
                }
            }
            boolean putInt = this.mEdmStorageProvider.putInt(i, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 1);
            if (putInt) {
                updateGeofenceActiveListbyAdmin(i);
                getHandler().sendMessage(getHandler().obtainMessage(3));
            }
            return putInt;
        }
    }

    public boolean stopGeofencing(ContextInfo contextInfo) {
        Log.d("GeofenceService", "stopGeofencing");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i = enforceGeofencingPermission.mCallerUid;
        if (!isAdminHasGeofence(i)) {
            return false;
        }
        if (!isGeofencingEnabled(enforceGeofencingPermission)) {
            return true;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0);
        if (putInt) {
            deleteGeofenceActiveListByAdmin(i);
            getHandler().sendMessage(getHandler().obtainMessage(3));
        }
        return putInt;
    }

    public boolean isGeofencingEnabled(ContextInfo contextInfo) {
        try {
            return this.mEdmStorageProvider.getInt(enforceGeofencingPermission(contextInfo).mCallerUid, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN) == 1;
        } catch (SettingNotFoundException unused) {
            return false;
        }
    }

    public List isDeviceInsideGeofence(ContextInfo contextInfo) {
        return isDeviceInsideGeofence(enforceGeofencingPermission(contextInfo).mCallerUid);
    }

    public final List isDeviceInsideGeofence(int i) {
        Location location;
        String bestProvider;
        Log.d("GeofenceService", "isDeviceInsideGeofence");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(1);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(1);
        List list = null;
        try {
            location = this.mLocation;
            if (location == null && (bestProvider = this.mLocationManager.getBestProvider(criteria, true)) != null && !bestProvider.equalsIgnoreCase("passive")) {
                location = this.mLocationManager.getLastKnownLocation(bestProvider);
            }
        } catch (Exception e) {
            Log.w("GeofenceService", "isDeviceInsideGeofence - EX", e);
            e.printStackTrace();
            location = null;
        }
        if (location == null) {
            deviceLocationUnavailableMessage(i);
        } else {
            list = getActiveGeofenceIdsbyAdmin(checkDeviceInsideOrOutsideGeo(location, false), i);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return list;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b2, code lost:
    
        if (r12 == null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0086, code lost:
    
        if (r12 != null) goto L44;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r12v2, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List getGeofences(ContextInfo contextInfo) {
        Cursor cursor;
        Log.d("GeofenceService", "getGeofences");
        ?? r12 = enforceGeofencingPermission(contextInfo).mCallerUid;
        ArrayList arrayList = new ArrayList();
        Cursor cursor2 = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursorByAdmin("GEOFENCING", r12, new String[]{KnoxCustomManagerService.f1773ID, "type", "blobdata"});
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        try {
                            int i = cursor.getInt(cursor.getColumnIndex(KnoxCustomManagerService.f1773ID));
                            int i2 = cursor.getInt(cursor.getColumnIndex("type"));
                            byte[] blob = cursor.getBlob(cursor.getColumnIndex("blobdata"));
                            if (i2 == 2) {
                                PolygonalGeofence polygonalGeofence = (PolygonalGeofence) deserializeGeoFence(blob);
                                polygonalGeofence.id = i;
                                polygonalGeofence.type = i2;
                                arrayList.add(polygonalGeofence);
                            } else if (i2 == 3) {
                                LinearGeofence linearGeofence = (LinearGeofence) deserializeGeoFence(blob);
                                linearGeofence.id = i;
                                linearGeofence.type = i2;
                                linearGeofence.points = convertToLinear(linearGeofence);
                                arrayList.add(linearGeofence);
                            } else {
                                CircularGeofence circularGeofence = (CircularGeofence) deserializeGeoFence(blob);
                                circularGeofence.id = i;
                                circularGeofence.type = i2;
                                arrayList.add(circularGeofence);
                            }
                        } catch (SQLException e) {
                            e = e;
                            Log.e("GeofenceService", "Exception occurred accessing Enterprise db " + e.getMessage());
                        } catch (Exception e2) {
                            e = e2;
                            Log.w("GeofenceService", "getGeofences - EX", e);
                            if (cursor != null) {
                                try {
                                    cursor.close();
                                } catch (Exception unused) {
                                }
                            }
                            if (arrayList.isEmpty()) {
                                return null;
                            }
                            return arrayList;
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                cursor2 = r12;
                if (cursor2 != null) {
                    try {
                        cursor2.close();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } catch (SQLException e3) {
            e = e3;
            cursor = null;
        } catch (Exception e4) {
            e = e4;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    public boolean setMinTimeParameter(ContextInfo contextInfo, long j) {
        int i = enforceGeofencingPermission(contextInfo).mCallerUid;
        if (j < 0) {
            return false;
        }
        boolean putString = this.mEdmStorageProvider.putString(i, "GEOFENCINGSETTINGS", "time", Long.toString(j));
        if (putString) {
            getHandler().sendMessage(getHandler().obtainMessage(3));
        }
        return putString;
    }

    public long getMinTimeParameter(ContextInfo contextInfo) {
        return parseStringToLong(this.mEdmStorageProvider.getString(Utils.getCallingOrUserUid(enforceGeofencingPermission(contextInfo)), "GEOFENCINGSETTINGS", "time"));
    }

    public final long getEffectiveMinTimeParameter() {
        long j = 0;
        for (ContentValues contentValues : this.mEdmStorageProvider.getValuesList("GEOFENCINGSETTINGS", new String[]{"time", LauncherConfigurationInternal.KEY_STATE_BOOLEAN})) {
            Integer asInteger = contentValues.getAsInteger(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            if (asInteger == null || asInteger.intValue() != 0) {
                long parseStringToLong = parseStringToLong(contentValues.getAsString("time"));
                if (j == 0 || (parseStringToLong != 0 && j > parseStringToLong)) {
                    j = parseStringToLong;
                }
            }
        }
        if (j == 0) {
            return 60000L;
        }
        return j;
    }

    public boolean setMinDistanceParameter(ContextInfo contextInfo, float f) {
        int i = enforceGeofencingPermission(contextInfo).mCallerUid;
        if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            return false;
        }
        boolean putString = this.mEdmStorageProvider.putString(i, "GEOFENCINGSETTINGS", "distance", Float.toString(f));
        if (putString) {
            getHandler().sendMessage(getHandler().obtainMessage(3));
        }
        return putString;
    }

    public float getMinDistanceParameter(ContextInfo contextInfo) {
        return parseStringToFloat(this.mEdmStorageProvider.getString(Utils.getCallingOrUserUid(enforceGeofencingPermission(contextInfo)), "GEOFENCINGSETTINGS", "distance"));
    }

    public final float getEffectiveMinDistanceParameter() {
        float f = 0.0f;
        for (ContentValues contentValues : this.mEdmStorageProvider.getValuesList("GEOFENCINGSETTINGS", new String[]{"distance", LauncherConfigurationInternal.KEY_STATE_BOOLEAN})) {
            Integer asInteger = contentValues.getAsInteger(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            if (asInteger == null || asInteger.intValue() != 0) {
                float parseStringToFloat = parseStringToFloat(contentValues.getAsString("distance"));
                if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON || (parseStringToFloat != DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f > parseStringToFloat)) {
                    f = parseStringToFloat;
                }
            }
        }
        return f;
    }

    public final float parseStringToFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception unused) {
            return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
    }

    public final long parseStringToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception unused) {
            return 0L;
        }
    }

    public final List createGracePoints(List list, double d) {
        LatLongPoint latLongPoint;
        LatLongPoint latLongPoint2;
        double d2;
        double d3;
        List list2 = list;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        double d4 = 8.964E-6d * d;
        int i = 0;
        int i2 = 0;
        while (i2 < list.size()) {
            if (i2 == list.size() - 1) {
                latLongPoint = (LatLongPoint) list2.get(i2);
                latLongPoint2 = (LatLongPoint) list2.get(i);
            } else {
                latLongPoint = (LatLongPoint) list2.get(i2);
                latLongPoint2 = (LatLongPoint) list2.get(i2 + 1);
            }
            double d5 = latLongPoint2.latitude;
            if (d5 - latLongPoint.latitude == 0.0d) {
                if (d5 >= 0.0d) {
                    latLongPoint2.latitude = d5 - 0.005d;
                } else {
                    latLongPoint2.latitude = d5 + 0.005d;
                }
            }
            double d6 = latLongPoint2.longitude;
            if (d6 - latLongPoint.longitude == 0.0d) {
                if (d6 >= 0.0d) {
                    latLongPoint2.longitude = d6 - 0.005d;
                } else {
                    latLongPoint2.longitude = d6 + 0.005d;
                }
            }
            int i3 = i2;
            double d7 = (latLongPoint2.longitude - latLongPoint.longitude) / (latLongPoint2.latitude - latLongPoint.latitude);
            arrayList2.add(Double.valueOf(d7));
            ArrayList arrayList4 = arrayList;
            LatLongPoint latLongPoint3 = new LatLongPoint((latLongPoint.latitude + latLongPoint2.latitude) / 2.0d, (latLongPoint.longitude + latLongPoint2.longitude) / 2.0d);
            double d8 = -(1.0d / d7);
            double sqrt = d4 / Math.sqrt((d8 * d8) + 1.0d);
            if (d7 > 0.0d) {
                d2 = d4;
                if (latLongPoint.longitude >= latLongPoint2.longitude) {
                    d3 = 0.0d;
                }
                double d9 = latLongPoint3.longitude;
                double d10 = (sqrt * d8) + d9;
                arrayList3.add(new LatLongPoint(((d10 - d9) + (latLongPoint3.latitude * d8)) / d8, d10));
                arrayList = arrayList4;
                d4 = d2;
                i = 0;
                i2 = i3 + 1;
                list2 = list;
            } else {
                d2 = d4;
                d3 = 0.0d;
            }
            if (d7 >= d3 || latLongPoint.longitude <= latLongPoint2.longitude) {
                if ((d7 > 0.0d && latLongPoint.longitude > latLongPoint2.longitude) || (d7 < d3 && latLongPoint.longitude < latLongPoint2.longitude)) {
                    double d11 = latLongPoint3.longitude;
                    double d12 = d11 - (sqrt * d8);
                    arrayList3.add(new LatLongPoint(((d12 - d11) + (latLongPoint3.latitude * d8)) / d8, d12));
                }
                arrayList = arrayList4;
                d4 = d2;
                i = 0;
                i2 = i3 + 1;
                list2 = list;
            }
            double d92 = latLongPoint3.longitude;
            double d102 = (sqrt * d8) + d92;
            arrayList3.add(new LatLongPoint(((d102 - d92) + (latLongPoint3.latitude * d8)) / d8, d102));
            arrayList = arrayList4;
            d4 = d2;
            i = 0;
            i2 = i3 + 1;
            list2 = list;
        }
        ArrayList arrayList5 = arrayList;
        int size = arrayList3.size() - 1;
        int size2 = arrayList2.size() - 1;
        int i4 = 0;
        int i5 = 0;
        while (i5 < arrayList2.size() - 1) {
            double doubleValue = ((Double) arrayList2.get(i5)).doubleValue();
            double doubleValue2 = ((Double) arrayList2.get(size2)).doubleValue();
            LatLongPoint latLongPoint4 = (LatLongPoint) arrayList3.get(i4);
            LatLongPoint latLongPoint5 = (LatLongPoint) arrayList3.get(size);
            double d13 = latLongPoint5.longitude - (latLongPoint5.latitude * doubleValue2);
            double d14 = latLongPoint4.longitude;
            double d15 = latLongPoint4.latitude;
            double d16 = (d13 - (d14 - (doubleValue * d15))) / (doubleValue - doubleValue2);
            arrayList5.add(new LatLongPoint(d16, (doubleValue * d16) + (d14 - (doubleValue * d15))));
            int i6 = i4;
            i4++;
            size = i6;
            int i7 = i5;
            i5++;
            size2 = i7;
        }
        return arrayList5;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x00d7, code lost:
    
        if (r13.longitude >= r14.longitude) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01a9, code lost:
    
        if (r13.longitude <= r14.longitude) goto L61;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List createPolygonalPoints2(List list, double d) {
        LatLongPoint latLongPoint;
        LatLongPoint latLongPoint2;
        ArrayList arrayList;
        LatLongPoint latLongPoint3;
        ArrayList arrayList2;
        ArrayList arrayList3;
        int i;
        ArrayList arrayList4;
        int i2;
        ArrayList arrayList5;
        LatLongPoint latLongPoint4;
        List list2 = list;
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList();
        double d2 = 8.964E-6d * d;
        LatLongPoint latLongPoint5 = null;
        LatLongPoint latLongPoint6 = null;
        LatLongPoint latLongPoint7 = null;
        int i3 = 0;
        while (i3 < list.size()) {
            if (i3 == list.size() - 1) {
                latLongPoint = (LatLongPoint) list2.get(i3 - 1);
                latLongPoint2 = (LatLongPoint) list2.get(i3);
            } else {
                latLongPoint = (LatLongPoint) list2.get(i3);
                latLongPoint2 = (LatLongPoint) list2.get(i3 + 1);
            }
            LatLongPoint latLongPoint8 = latLongPoint6;
            double d3 = latLongPoint2.latitude;
            ArrayList arrayList11 = arrayList6;
            if (d3 - latLongPoint.latitude == 0.0d) {
                if (d3 >= 0.0d) {
                    latLongPoint2.latitude = d3 - 0.005d;
                } else {
                    latLongPoint2.latitude = d3 + 0.005d;
                }
            }
            double d4 = latLongPoint2.longitude;
            if (d4 - latLongPoint.longitude == 0.0d) {
                if (d4 >= 0.0d) {
                    latLongPoint2.longitude = d4 - 0.005d;
                } else {
                    latLongPoint2.longitude = d4 + 0.005d;
                }
            }
            LatLongPoint latLongPoint9 = latLongPoint7;
            int i4 = i3;
            double d5 = (latLongPoint2.longitude - latLongPoint.longitude) / (latLongPoint2.latitude - latLongPoint.latitude);
            arrayList8.add(Double.valueOf(d5));
            ArrayList arrayList12 = arrayList7;
            ArrayList arrayList13 = arrayList8;
            ArrayList arrayList14 = arrayList9;
            ArrayList arrayList15 = arrayList10;
            LatLongPoint latLongPoint10 = new LatLongPoint((latLongPoint.latitude + latLongPoint2.latitude) / 2.0d, (latLongPoint.longitude + latLongPoint2.longitude) / 2.0d);
            double d6 = -(1.0d / d5);
            double sqrt = d2 / Math.sqrt((d6 * d6) + 1.0d);
            double d7 = d2;
            if (d5 > 0.0d) {
                arrayList = arrayList12;
            } else {
                arrayList = arrayList12;
            }
            if (d5 >= 0.0d || latLongPoint.longitude <= latLongPoint2.longitude) {
                latLongPoint3 = latLongPoint5;
                arrayList2 = arrayList11;
                arrayList3 = arrayList15;
                ArrayList arrayList16 = arrayList;
                if (d5 > 0.0d) {
                    arrayList = arrayList16;
                    i = i4;
                } else {
                    i = i4;
                    arrayList = arrayList16;
                }
                if (d5 >= 0.0d || latLongPoint.longitude >= latLongPoint2.longitude) {
                    arrayList4 = arrayList14;
                    i2 = i;
                    arrayList5 = arrayList;
                    latLongPoint6 = latLongPoint8;
                    latLongPoint7 = latLongPoint9;
                    latLongPoint5 = latLongPoint3;
                    arrayList7 = arrayList5;
                    arrayList10 = arrayList3;
                    arrayList6 = arrayList2;
                    arrayList8 = arrayList13;
                    d2 = d7;
                    arrayList9 = arrayList4;
                    i3 = i2 + 1;
                    list2 = list;
                }
                double d8 = latLongPoint10.longitude;
                double d9 = sqrt * d6;
                double d10 = d8 - d9;
                LatLongPoint latLongPoint11 = latLongPoint;
                LatLongPoint latLongPoint12 = latLongPoint2;
                arrayList14.add(new LatLongPoint(((d10 - d8) + (latLongPoint10.latitude * d6)) / d6, d10));
                double d11 = latLongPoint10.longitude;
                double d12 = d11 + d9;
                arrayList3.add(new LatLongPoint(((d12 - d11) + (latLongPoint10.latitude * d6)) / d6, d12));
                i2 = i;
                latLongPoint2 = i2 == list.size() + (-1) ? latLongPoint12 : i == 0 ? latLongPoint11 : latLongPoint9;
                if (i2 == 0 || i2 == list.size() - 1) {
                    double d13 = latLongPoint2.longitude;
                    double d14 = d13 - d9;
                    arrayList4 = arrayList14;
                    double d15 = ((d14 - d13) + (latLongPoint2.latitude * d6)) / d6;
                    if (i2 == 0) {
                        arrayList2.add(new LatLongPoint(d15, d14));
                        latLongPoint6 = latLongPoint8;
                    } else {
                        latLongPoint6 = new LatLongPoint(d15, d14);
                    }
                    double d16 = latLongPoint2.longitude;
                    double d17 = d9 + d16;
                    double d18 = ((d17 - d16) + (latLongPoint2.latitude * d6)) / d6;
                    if (i2 == 0) {
                        arrayList5 = arrayList;
                        arrayList5.add(new LatLongPoint(d18, d17));
                        latLongPoint7 = latLongPoint2;
                        latLongPoint5 = latLongPoint3;
                        arrayList7 = arrayList5;
                        arrayList10 = arrayList3;
                        arrayList6 = arrayList2;
                        arrayList8 = arrayList13;
                        d2 = d7;
                        arrayList9 = arrayList4;
                        i3 = i2 + 1;
                        list2 = list;
                    } else {
                        arrayList5 = arrayList;
                        latLongPoint5 = new LatLongPoint(d18, d17);
                        latLongPoint7 = latLongPoint2;
                        arrayList7 = arrayList5;
                        arrayList10 = arrayList3;
                        arrayList6 = arrayList2;
                        arrayList8 = arrayList13;
                        d2 = d7;
                        arrayList9 = arrayList4;
                        i3 = i2 + 1;
                        list2 = list;
                    }
                } else {
                    latLongPoint6 = latLongPoint8;
                    arrayList4 = arrayList14;
                    latLongPoint7 = latLongPoint2;
                    latLongPoint5 = latLongPoint3;
                    arrayList5 = arrayList;
                    arrayList7 = arrayList5;
                    arrayList10 = arrayList3;
                    arrayList6 = arrayList2;
                    arrayList8 = arrayList13;
                    d2 = d7;
                    arrayList9 = arrayList4;
                    i3 = i2 + 1;
                    list2 = list;
                }
            }
            double d19 = latLongPoint10.longitude;
            double d20 = sqrt * d6;
            double d21 = d19 + d20;
            arrayList14.add(new LatLongPoint(((d21 - d19) + (latLongPoint10.latitude * d6)) / d6, d21));
            double d22 = latLongPoint10.longitude;
            double d23 = d22 - d20;
            LatLongPoint latLongPoint13 = latLongPoint5;
            LatLongPoint latLongPoint14 = new LatLongPoint(((d23 - d22) + (latLongPoint10.latitude * d6)) / d6, d23);
            arrayList3 = arrayList15;
            arrayList3.add(latLongPoint14);
            LatLongPoint latLongPoint15 = i4 == 0 ? latLongPoint : latLongPoint9;
            if (i4 != list.size() - 1) {
                latLongPoint2 = latLongPoint15;
            }
            if (i4 == 0 || i4 == list.size() - 1) {
                double d24 = latLongPoint2.longitude;
                double d25 = d24 + d20;
                latLongPoint3 = latLongPoint13;
                double d26 = ((d25 - d24) + (latLongPoint2.latitude * d6)) / d6;
                if (i4 == 0) {
                    arrayList2 = arrayList11;
                    arrayList2.add(new LatLongPoint(d26, d25));
                    latLongPoint4 = latLongPoint8;
                } else {
                    arrayList2 = arrayList11;
                    latLongPoint4 = new LatLongPoint(d26, d25);
                }
                double d27 = latLongPoint2.longitude;
                double d28 = d27 - d20;
                LatLongPoint latLongPoint16 = latLongPoint4;
                double d29 = ((d28 - d27) + (latLongPoint2.latitude * d6)) / d6;
                if (i4 == 0) {
                    ArrayList arrayList17 = arrayList;
                    arrayList17.add(new LatLongPoint(d29, d28));
                    latLongPoint6 = latLongPoint16;
                    arrayList4 = arrayList14;
                    i2 = i4;
                    arrayList5 = arrayList17;
                    latLongPoint7 = latLongPoint2;
                    latLongPoint5 = latLongPoint3;
                    arrayList7 = arrayList5;
                    arrayList10 = arrayList3;
                    arrayList6 = arrayList2;
                    arrayList8 = arrayList13;
                    d2 = d7;
                    arrayList9 = arrayList4;
                    i3 = i2 + 1;
                    list2 = list;
                } else {
                    latLongPoint6 = latLongPoint16;
                    latLongPoint5 = new LatLongPoint(d29, d28);
                    arrayList4 = arrayList14;
                    i2 = i4;
                    arrayList5 = arrayList;
                    latLongPoint7 = latLongPoint2;
                    arrayList7 = arrayList5;
                    arrayList10 = arrayList3;
                    arrayList6 = arrayList2;
                    arrayList8 = arrayList13;
                    d2 = d7;
                    arrayList9 = arrayList4;
                    i3 = i2 + 1;
                    list2 = list;
                }
            } else {
                latLongPoint6 = latLongPoint8;
                arrayList4 = arrayList14;
                i2 = i4;
                latLongPoint5 = latLongPoint13;
                latLongPoint7 = latLongPoint2;
                arrayList2 = arrayList11;
                arrayList5 = arrayList;
                arrayList7 = arrayList5;
                arrayList10 = arrayList3;
                arrayList6 = arrayList2;
                arrayList8 = arrayList13;
                d2 = d7;
                arrayList9 = arrayList4;
                i3 = i2 + 1;
                list2 = list;
            }
        }
        ArrayList arrayList18 = arrayList6;
        ArrayList arrayList19 = arrayList8;
        ArrayList arrayList20 = arrayList9;
        ArrayList arrayList21 = arrayList10;
        LatLongPoint latLongPoint17 = latLongPoint5;
        LatLongPoint latLongPoint18 = latLongPoint6;
        ArrayList arrayList22 = arrayList7;
        int i5 = 0;
        int i6 = 0;
        while (i6 < arrayList19.size() - 2) {
            ArrayList arrayList23 = arrayList19;
            double doubleValue = ((Double) arrayList23.get(i6)).doubleValue();
            int i7 = i6 + 1;
            double doubleValue2 = ((Double) arrayList23.get(i7)).doubleValue();
            LatLongPoint latLongPoint19 = (LatLongPoint) arrayList20.get(i5);
            int i8 = i5 + 1;
            LatLongPoint latLongPoint20 = (LatLongPoint) arrayList20.get(i8);
            ArrayList arrayList24 = arrayList22;
            double d30 = latLongPoint20.longitude - (latLongPoint20.latitude * doubleValue2);
            double d31 = latLongPoint19.longitude;
            double d32 = latLongPoint19.latitude;
            double d33 = doubleValue - doubleValue2;
            double d34 = (d30 - (d31 - (doubleValue * d32))) / d33;
            arrayList18.add(new LatLongPoint(d34, (doubleValue * d34) + (d31 - (d32 * doubleValue))));
            LatLongPoint latLongPoint21 = (LatLongPoint) arrayList21.get(i5);
            LatLongPoint latLongPoint22 = (LatLongPoint) arrayList21.get(i8);
            double d35 = latLongPoint21.longitude;
            double d36 = d35 - (doubleValue2 * latLongPoint22.latitude);
            double d37 = latLongPoint21.latitude;
            double d38 = (d36 - (d35 - (doubleValue * d37))) / d33;
            arrayList24.add(new LatLongPoint(d38, (doubleValue * d38) + (d35 - (doubleValue * d37))));
            i6 = i7;
            arrayList19 = arrayList23;
            arrayList22 = arrayList24;
            i5 = i8;
        }
        ArrayList arrayList25 = arrayList22;
        arrayList18.add(latLongPoint18);
        arrayList25.add(latLongPoint17);
        for (int size = arrayList25.size() - 1; size >= 0; size--) {
            arrayList18.add((LatLongPoint) arrayList25.get(size));
        }
        return arrayList18;
    }

    public final synchronized void loadGeofenceActiveList(int i) {
        Iterator it = ((UserManager) this.mContext.getSystemService("user")).getUsers().iterator();
        while (it.hasNext()) {
            try {
                ArrayList intListAsUser = this.mEdmStorageProvider.getIntListAsUser("GEOFENCINGSETTINGS", "adminUid", ((UserInfo) it.next()).id);
                this.mActiveGeofenceList.clear();
                Iterator it2 = intListAsUser.iterator();
                while (it2.hasNext()) {
                    int intValue = ((Integer) it2.next()).intValue();
                    if (this.mEdmStorageProvider.getInt(intValue, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN) == 1) {
                        updateGeofenceActiveListbyAdmin(intValue);
                    }
                }
            } catch (Exception e) {
                Log.d("GeofenceService", "loadGeofenceActiveList - EX" + e.toString());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0090, code lost:
    
        if (r1 != null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bf, code lost:
    
        if (r1 == null) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void updateGeofenceActiveListbyAdmin(int i) {
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursorByAdmin("GEOFENCING", i, new String[]{KnoxCustomManagerService.f1773ID, "type", "blobdata"});
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        int i2 = cursor.getInt(cursor.getColumnIndex(KnoxCustomManagerService.f1773ID));
                        if (!this.mActiveGeofenceList.containsKey(Integer.valueOf(i2))) {
                            int i3 = cursor.getInt(cursor.getColumnIndex("type"));
                            byte[] blob = cursor.getBlob(cursor.getColumnIndex("blobdata"));
                            if (i3 == 2) {
                                PolygonalGeofence polygonalGeofence = (PolygonalGeofence) deserializeGeoFence(blob);
                                polygonalGeofence.id = i2;
                                polygonalGeofence.type = i3;
                                this.mActiveGeofenceList.put(Integer.valueOf(i2), polygonalGeofence);
                            } else if (i3 == 3) {
                                LinearGeofence linearGeofence = (LinearGeofence) deserializeGeoFence(blob);
                                linearGeofence.id = i2;
                                linearGeofence.type = i3;
                                this.mActiveGeofenceList.put(Integer.valueOf(i2), linearGeofence);
                            } else {
                                CircularGeofence circularGeofence = (CircularGeofence) deserializeGeoFence(blob);
                                circularGeofence.id = i2;
                                circularGeofence.type = i3;
                                this.mActiveGeofenceList.put(Integer.valueOf(i2), circularGeofence);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
        } catch (SQLException e) {
            Log.e("GeofenceService", "Exception occurred accessing Enterprise db " + e.getMessage());
        } catch (Exception e2) {
            Log.w("GeofenceService", "updateGeofenceActiveList - EX", e2);
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception unused2) {
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
    
        if (r1 != null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005c, code lost:
    
        if (r1 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void deleteGeofenceActiveListByAdmin(int i) {
        Cursor cursor = null;
        try {
            try {
                try {
                    cursor = this.mEdmStorageProvider.getCursorByAdmin("GEOFENCING", i, new String[]{KnoxCustomManagerService.f1773ID});
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            this.mActiveGeofenceList.remove(Integer.valueOf(cursor.getInt(cursor.getColumnIndex(KnoxCustomManagerService.f1773ID))));
                        }
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e) {
                Log.w("GeofenceService", "deleteGeofenceActiveListByAdmin - EX", e);
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception unused2) {
                    }
                }
            }
        } catch (SQLException e2) {
            Log.e("GeofenceService", "Exception occurred accessing Enterprise db " + e2.getMessage());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
    
        if (r3 == null) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0025, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0023, code lost:
    
        if (r3 == null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isAdminHasGeofence(int i) {
        Cursor cursor = null;
        try {
            try {
                cursor = this.mEdmStorageProvider.getCursorByAdmin("GEOFENCING", i, new String[]{KnoxCustomManagerService.f1773ID});
                boolean moveToNext = cursor.moveToNext();
                try {
                    cursor.close();
                } catch (Exception unused) {
                }
                return moveToNext;
            } catch (SQLException e) {
                Log.e("GeofenceService", "Exception occurred accessing Enterprise db " + e.getMessage());
            } catch (Exception e2) {
                Log.w("GeofenceService", "isAdminHasGeofence - EX", e2);
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    public final List convertToLinear(LinearGeofence linearGeofence) {
        int size = linearGeofence.points.size();
        int i = size - 1;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < size / 2; i2++) {
            arrayList.add(new LatLongPoint((((LatLongPoint) linearGeofence.points.get(i2)).latitude + ((LatLongPoint) linearGeofence.points.get(i)).latitude) * 0.5d, (((LatLongPoint) linearGeofence.points.get(i2)).longitude + ((LatLongPoint) linearGeofence.points.get(i)).longitude) * 0.5d));
            i--;
        }
        return arrayList;
    }

    public static Object deserializeGeoFence(byte[] bArr) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bArr));
            Object readObject = objectInputStream.readObject();
            objectInputStream.close();
            return readObject;
        } catch (IOException | ClassNotFoundException unused) {
            return null;
        }
    }

    public final synchronized List checkDeviceInsideOrOutsideGeo(Location location, boolean z) {
        if (this.mActiveGeofenceList.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Integer num : this.mActiveGeofenceList.keySet()) {
            if (checkGeofenceInsideOrOutside(location, (Geofence) this.mActiveGeofenceList.get(num))) {
                arrayList.add(num);
            }
        }
        if (z) {
            sendIntenttoAdmins(arrayList);
        }
        return arrayList.isEmpty() ? null : arrayList;
    }

    public final boolean checkGeofenceInsideOrOutside(Location location, Geofence geofence) {
        int i = geofence.type;
        if (i == 1) {
            return checkDeviceInsideCircleSpherical(location, (CircularGeofence) geofence);
        }
        if (i == 2) {
            PolygonalGeofence polygonalGeofence = (PolygonalGeofence) geofence;
            return checkDeviceInsideBoundingbox(location, polygonalGeofence.boundingBox) && checkDeviceInsidePolygon(location, polygonalGeofence);
        }
        if (i != 3) {
            return false;
        }
        LinearGeofence linearGeofence = (LinearGeofence) geofence;
        return checkDeviceInsideBoundingbox(location, linearGeofence.boundingBox) && checkDeviceInsideLinear(location, linearGeofence);
    }

    public final boolean checkDeviceInsideCircleSpherical(Location location, CircularGeofence circularGeofence) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLongPoint latLongPoint = circularGeofence.center;
        double d = latLongPoint.latitude;
        double d2 = latLongPoint.longitude;
        double radians = Math.toRadians(latitude);
        double radians2 = Math.toRadians(longitude);
        double radians3 = Math.toRadians(d);
        return (Math.acos((Math.sin(radians3) * Math.sin(radians)) + ((Math.cos(radians3) * Math.cos(radians)) * Math.cos(radians2 - Math.toRadians(d2)))) * 6371.0d) * 1000.0d <= circularGeofence.radius;
    }

    public final boolean checkDeviceInsideBoundingbox(Location location, BoundingBox boundingBox) {
        if (location == null || boundingBox.left == null) {
            return false;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return latitude <= boundingBox.top.latitude && latitude >= boundingBox.bottom.latitude && longitude <= boundingBox.right.longitude && longitude >= boundingBox.left.longitude;
    }

    public final int checkDeviceInsidePolygonRayCasting(List list, double d, double d2) {
        int size = list.size();
        double[] dArr = new double[list.size()];
        double[] dArr2 = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            dArr[i] = ((LatLongPoint) list.get(i)).latitude;
            dArr2[i] = ((LatLongPoint) list.get(i)).longitude;
        }
        int i2 = size - 1;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            double d3 = dArr2[i4];
            boolean z = d3 > d2;
            double d4 = dArr2[i2];
            if (z != (d4 > d2)) {
                double d5 = dArr[i2];
                double d6 = dArr[i4];
                if (d <= (((d5 - d6) * (d2 - d3)) / (d4 - d3)) + d6) {
                    i3 = i3 == 0 ? 1 : 0;
                }
            }
            i2 = i4;
        }
        return i3;
    }

    public final boolean checkDeviceInsidePolygon(Location location, PolygonalGeofence polygonalGeofence) {
        if (checkDeviceInsidePolygonRayCasting(polygonalGeofence.optimizedPoints, location.getLatitude(), location.getLongitude()) == 1) {
            return true;
        }
        return checkDeviceInsidePolygonRayCasting(polygonalGeofence.pointsWithinGraceLimit, location.getLatitude(), location.getLongitude()) == 1;
    }

    public final boolean checkDeviceInsideLinear(Location location, LinearGeofence linearGeofence) {
        return checkDeviceInsidePolygonRayCasting(linearGeofence.optimizedPoints, location.getLatitude(), location.getLongitude()) == 1;
    }

    public final void sendIntenttoAdmins(List list) {
        Log.d("GeofenceService", "sendIntenttoAdmins");
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        for (UserInfo userInfo : userManager.getUsers()) {
            if (userManager.isUserRunning(userInfo.getUserHandle())) {
                Iterator it = this.mEdmStorageProvider.getIntListAsUser("ADMIN", "adminUid", userInfo.id).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    List activeGeofenceIdsbyAdmin = getActiveGeofenceIdsbyAdmin(list, intValue);
                    if (activeGeofenceIdsbyAdmin != null) {
                        Intent intent = new Intent("com.samsung.android.knox.intent.action.DEVICE_INSIDE_GEOFENCE");
                        int[] iArr = new int[activeGeofenceIdsbyAdmin.size()];
                        for (int i = 0; i < activeGeofenceIdsbyAdmin.size(); i++) {
                            iArr[i] = ((Integer) activeGeofenceIdsbyAdmin.get(i)).intValue();
                        }
                        intent.putExtra("com.samsung.android.knox.intent.extra.ID", iArr);
                        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", UserHandle.getUserId(intValue));
                        intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", intValue);
                        int proxyAdminOwnerUid = Utils.getProxyAdminOwnerUid(this.mEdmStorageProvider, intValue);
                        Log.d("GeofenceService", "@sendIntenttoAdmins - ownerUid = " + proxyAdminOwnerUid);
                        String packageNameForUid = getPackageNameForUid(proxyAdminOwnerUid);
                        Log.d("GeofenceService", "@sendIntenttoAdmins - thePackageNameFortheUID = " + packageNameForUid);
                        intent.setPackage(packageNameForUid);
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(proxyAdminOwnerUid)), "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                        try {
                            String kpuPackageName = KpuHelper.getInstance(this.mContext).getKpuPackageName();
                            Intent intent2 = new Intent(intent);
                            intent2.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mContext.getPackageManager().getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
                            intent2.setPackage(kpuPackageName);
                            this.mContext.sendBroadcastAsUser(intent2, new UserHandle(UserHandle.getUserId(proxyAdminOwnerUid)), "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            if (this.mEdmStorageProvider.getInt(intValue, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN) == 1) {
                                Intent intent3 = new Intent("com.samsung.android.knox.intent.action.DEVICE_OUTSIDE_GEOFENCE");
                                intent3.putExtra("com.samsung.android.knox.intent.extra.USER_ID", UserHandle.getUserId(intValue));
                                intent3.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", intValue);
                                int proxyAdminOwnerUid2 = Utils.getProxyAdminOwnerUid(this.mEdmStorageProvider, intValue);
                                intent3.setPackage(getPackageNameForUid(proxyAdminOwnerUid2));
                                this.mContext.sendBroadcastAsUser(intent3, new UserHandle(UserHandle.getUserId(proxyAdminOwnerUid2)), "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                                try {
                                    String kpuPackageName2 = KpuHelper.getInstance(this.mContext).getKpuPackageName();
                                    Intent intent4 = new Intent(intent3);
                                    intent4.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mContext.getPackageManager().getPackageUidAsUser(kpuPackageName2, UserHandle.getCallingUserId()));
                                    intent4.setPackage(kpuPackageName2);
                                    this.mContext.sendBroadcastAsUser(intent4, new UserHandle(UserHandle.getUserId(proxyAdminOwnerUid2)), "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
    }

    public final List getActiveGeofenceIdsbyAdmin(List list, int i) {
        if (list != null && !list.isEmpty()) {
            int userId = UserHandle.getUserId(i);
            try {
                ArrayList arrayList = new ArrayList();
                for (ContentValues contentValues : this.mEdmStorageProvider.getValuesListAsUser("GEOFENCING", new String[]{"adminUid", KnoxCustomManagerService.f1773ID}, userId)) {
                    int intValue = contentValues.getAsInteger(KnoxCustomManagerService.f1773ID).intValue();
                    Integer asInteger = contentValues.getAsInteger("adminUid");
                    if (asInteger != null && asInteger.intValue() == i && list.contains(Integer.valueOf(intValue))) {
                        arrayList.add(Integer.valueOf(intValue));
                    }
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return arrayList;
            } catch (Exception e) {
                Log.w("GeofenceService", "getActiveGeofenceIdsbyAdmin - EX", e);
            }
        }
        return null;
    }

    public final void deviceLocationUnavailableMessage(int i) {
        Log.d("GeofenceService", "DEVICE_LOCATION_UNAVAILABLE");
        Intent intent = new Intent("com.samsung.android.knox.intent.action.DEVICE_LOCATION_UNAVAILABLE");
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", UserHandle.getUserId(i));
        intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", i);
        if (i != -1) {
            intent.setPackage(getPackageNameForUid(i));
        }
        int proxyAdminOwnerUid = Utils.getProxyAdminOwnerUid(this.mEdmStorageProvider, i);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(UserHandle.getUserId(proxyAdminOwnerUid)), "com.samsung.android.knox.permission.KNOX_GEOFENCING");
        try {
            String kpuPackageName = KpuHelper.getInstance(this.mContext).getKpuPackageName();
            Intent intent2 = new Intent(intent);
            intent2.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", this.mContext.getPackageManager().getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
            if (i != -1) {
                intent2.setPackage(kpuPackageName);
            }
            this.mContext.sendBroadcastAsUser(intent2, new UserHandle(UserHandle.getUserId(proxyAdminOwnerUid)), "com.samsung.android.knox.permission.KNOX_GEOFENCING");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String getPackageNameForUid(int i) {
        Log.d("GeofenceService", "@getPackageNameForUid - uid = " + i);
        if (i != 1000 && (i < 10000 || i > 19999)) {
            Log.d("GeofenceService", "@getPackageNameForUid - returning UMC PACKAGENAME");
            return "com.sec.enterprise.knox.cloudmdm.smdms";
        }
        String string = this.mEdmStorageProvider.getString(i, "ADMIN_INFO", "adminName");
        if (string == null) {
            Log.d("GeofenceService", "@getPackageNameForUid - returning null");
            return null;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
        if (unflattenFromString == null) {
            Log.d("GeofenceService", "@getPackageNameForUid - returning null");
            return null;
        }
        Log.d("GeofenceService", "@getPackageNameForUid - returning compName.getPackageName() --> " + unflattenFromString.getPackageName());
        return unflattenFromString.getPackageName();
    }

    public final synchronized boolean deleteFromDB(ContextInfo contextInfo, int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        HashMap hashMap = new HashMap();
        hashMap.put(KnoxCustomManagerService.f1773ID, Integer.toString(i));
        if (this.mEdmStorageProvider.removeByFieldsAsUser("GEOFENCING", hashMap, callingOrCurrentUserId) == 0) {
            return false;
        }
        this.mActiveGeofenceList.remove(Integer.valueOf(i));
        return true;
    }

    public final BoundingBox calcBoundingBox(List list) {
        if (list == null) {
            return null;
        }
        BoundingBox boundingBox = new BoundingBox((LatLongPoint) list.get(0), (LatLongPoint) list.get(0), (LatLongPoint) list.get(0), (LatLongPoint) list.get(0));
        for (int i = 1; i < list.size(); i++) {
            LatLongPoint latLongPoint = (LatLongPoint) list.get(i);
            double d = latLongPoint.longitude;
            if (d < boundingBox.left.longitude) {
                boundingBox.left = latLongPoint;
            }
            if (d > boundingBox.right.longitude) {
                boundingBox.right = latLongPoint;
            }
            double d2 = latLongPoint.latitude;
            if (d2 > boundingBox.top.latitude) {
                boundingBox.top = latLongPoint;
            }
            if (d2 < boundingBox.bottom.latitude) {
                boundingBox.bottom = latLongPoint;
            }
        }
        return boundingBox;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v0, types: [com.android.server.enterprise.geofencing.GeofenceService] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.util.List] */
    public final List findCollinear(List list) {
        ?? r1;
        ArrayList arrayList;
        List list2 = list;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int i = 0;
        arrayList2.add((LatLongPoint) list2.get(0));
        int i2 = 2;
        int i3 = 1;
        while (i2 < list.size()) {
            LatLongPoint latLongPoint = (LatLongPoint) list2.get(i);
            LatLongPoint latLongPoint2 = (LatLongPoint) list2.get(i3);
            LatLongPoint latLongPoint3 = (LatLongPoint) list2.get(i2);
            double d = latLongPoint.latitude;
            double d2 = latLongPoint2.longitude;
            int i4 = i2;
            double d3 = latLongPoint3.longitude;
            double d4 = d * (d2 - d3);
            ArrayList arrayList4 = arrayList2;
            double d5 = latLongPoint2.latitude;
            ArrayList arrayList5 = arrayList3;
            int i5 = i;
            double d6 = latLongPoint.longitude;
            if (d4 + (d5 * (d3 - d6)) + (latLongPoint3.latitude * (d6 - d2)) == 0.0d) {
                r1 = arrayList5;
                if (!r1.contains(latLongPoint)) {
                    r1.add(latLongPoint);
                }
                if (!r1.contains(latLongPoint2)) {
                    r1.add(latLongPoint2);
                }
                if (!r1.contains(latLongPoint3)) {
                    r1.add(latLongPoint3);
                }
                arrayList = arrayList4;
            } else {
                r1 = arrayList5;
                if (!r1.isEmpty()) {
                    arrayList = arrayList4;
                    arrayList.add(findFirst(r1));
                    arrayList.add(findLast(r1));
                    r1.clear();
                } else {
                    arrayList = arrayList4;
                    arrayList.add(latLongPoint2);
                }
            }
            i = i5 + 1;
            i3++;
            i2 = i4 + 1;
            arrayList2 = arrayList;
            arrayList3 = r1;
            list2 = list;
        }
        ArrayList arrayList6 = arrayList2;
        arrayList6.add((LatLongPoint) list.get(list.size() - 1));
        return arrayList6;
    }

    public final LatLongPoint findLast(List list) {
        LatLongPoint latLongPoint = (LatLongPoint) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (((LatLongPoint) list.get(i)).latitude > latLongPoint.latitude && ((LatLongPoint) list.get(i)).longitude > latLongPoint.longitude) {
                latLongPoint = (LatLongPoint) list.get(i);
            }
        }
        return latLongPoint;
    }

    public final LatLongPoint findFirst(List list) {
        LatLongPoint latLongPoint = (LatLongPoint) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (((LatLongPoint) list.get(i)).latitude < latLongPoint.latitude && ((LatLongPoint) list.get(i)).longitude < latLongPoint.longitude) {
                latLongPoint = (LatLongPoint) list.get(i);
            }
        }
        return latLongPoint;
    }

    public final boolean validatePolygonalGeofence(List list) {
        int i;
        for (0; i < list.size(); i + 1) {
            LatLongPoint latLongPoint = (LatLongPoint) list.get(i);
            double d = latLongPoint.latitude;
            if (d <= 90.0d && d >= -90.0d) {
                double d2 = latLongPoint.longitude;
                i = (d2 <= 180.0d && d2 >= -180.0d) ? i + 1 : 0;
            }
            return false;
        }
        return true;
    }

    public final int writeGeofenceToDB(ContextInfo contextInfo, int i, byte[] bArr) {
        int i2 = contextInfo.mCallerUid;
        if (bArr == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i2));
        contentValues.put("type", Integer.valueOf(i));
        contentValues.put("blobdata", bArr);
        int insertValuesNoUpdate = this.mEdmStorageProvider.insertValuesNoUpdate("GEOFENCING", contentValues);
        updateGeofenceActiveListbyAdmin(i2);
        return insertValuesNoUpdate;
    }

    public final byte[] serializeGeoFence(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final boolean validateCircularGeofence(LatLongPoint latLongPoint, double d) {
        if (d <= 0.0d) {
            return false;
        }
        double d2 = latLongPoint.latitude;
        if (d2 > 90.0d || d2 < -90.0d) {
            return false;
        }
        double d3 = latLongPoint.longitude;
        return d3 <= 180.0d && d3 >= -180.0d;
    }

    public final synchronized void checkMonitoring() {
        if (this.mLocationManager == null) {
            setLocationManager();
        }
        GeoLocationListener geoLocationListener = mLocationListener;
        if (geoLocationListener != null) {
            this.mLocationManager.removeUpdates(geoLocationListener);
            mLocationListener = null;
        }
        if (this.mActiveGeofenceList.isEmpty()) {
            return;
        }
        try {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(1);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);
            criteria.setPowerRequirement(1);
            mLocationListener = new GeoLocationListener();
            this.mLocationManager.requestLocationUpdates(getEffectiveMinTimeParameter(), getEffectiveMinDistanceParameter(), criteria, mLocationListener, this.mHandlerThread.getLooper());
        } catch (Exception e) {
            Log.d("GeofenceService", "checkMonitoring - EX" + e);
            mLocationListener = null;
            deviceLocationUnavailableMessage(-1);
        }
    }
}
