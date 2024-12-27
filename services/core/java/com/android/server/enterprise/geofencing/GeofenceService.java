package com.android.server.enterprise.geofencing;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
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
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
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

public final class GeofenceService extends IGeofencing.Stub implements EnterpriseServiceCallback {
    public static GeoLocationListener mLocationListener;
    public final HashMap mActiveGeofenceList = new HashMap();
    public final Context mContext;
    public final EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final GeofencingHandler mHandler;
    public final HandlerThread mHandlerThread;
    public Location mLocation;
    public LocationManager mLocationManager;
    public final AnonymousClass1 mReceiver;
    public final AnonymousClass1 mUserRemovedReceiver;

    public final class GeoLocationListener implements LocationListener {
        public GeoLocationListener() {
        }

        @Override // android.location.LocationListener
        public final void onLocationChanged(Location location) {
            GeofenceService geofenceService = GeofenceService.this;
            geofenceService.mLocation = location;
            geofenceService.checkDeviceInsideOrOutsideGeo(true, location);
        }

        @Override // android.location.LocationListener
        public final void onProviderDisabled(String str) {
            GeofenceService.this.mLocation = null;
        }

        @Override // android.location.LocationListener
        public final void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    public final class GeofencingHandler extends Handler {
        public GeofencingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message != null) {
                int i = message.what;
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            return;
                        }
                        GeofenceService.m518$$Nest$mcheckMonitoring(GeofenceService.this);
                        return;
                    }
                    GeofenceService geofenceService = GeofenceService.this;
                    synchronized (geofenceService) {
                        Iterator it = ((UserManager) geofenceService.mContext.getSystemService("user")).getUsers().iterator();
                        while (it.hasNext()) {
                            try {
                                ArrayList intListAsUser = geofenceService.mEdmStorageProvider.getIntListAsUser(0, ((UserInfo) it.next()).id, "GEOFENCINGSETTINGS", "adminUid");
                                geofenceService.mActiveGeofenceList.clear();
                                Iterator it2 = intListAsUser.iterator();
                                while (it2.hasNext()) {
                                    int intValue = ((Integer) it2.next()).intValue();
                                    if (geofenceService.mEdmStorageProvider.getInt(intValue, 0, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN) == 1) {
                                        geofenceService.updateGeofenceActiveListbyAdmin(intValue);
                                    }
                                }
                            } catch (Exception e) {
                                Log.d("GeofenceService", "loadGeofenceActiveList - EX" + e.toString());
                            }
                        }
                    }
                    GeofenceService.m518$$Nest$mcheckMonitoring(GeofenceService.this);
                    return;
                }
                int intValue2 = ((Integer) message.obj).intValue();
                List isDeviceInsideGeofence = GeofenceService.this.isDeviceInsideGeofence(intValue2);
                if (isDeviceInsideGeofence == null || isDeviceInsideGeofence.isEmpty()) {
                    return;
                }
                Intent intent = new Intent("com.samsung.android.knox.intent.action.DEVICE_INSIDE_GEOFENCE");
                int[] iArr = new int[isDeviceInsideGeofence.size()];
                for (int i2 = 0; i2 < isDeviceInsideGeofence.size(); i2++) {
                    iArr[i2] = ((Integer) isDeviceInsideGeofence.get(i2)).intValue();
                }
                intent.putExtra("com.samsung.android.knox.intent.extra.ID", iArr);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", UserHandle.getUserId(intValue2));
                intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", intValue2);
                intent.setPackage(GeofenceService.this.getPackageNameForUid$2(intValue2));
                Context context = GeofenceService.this.mContext;
                UserHandle userHandle = UserHandle.ALL;
                context.sendBroadcastAsUser(intent, userHandle, "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                try {
                    String kpuPackageName = KpuHelper.getInstance(GeofenceService.this.mContext).getKpuPackageName();
                    Intent intent2 = new Intent(intent);
                    intent2.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", GeofenceService.this.mContext.getPackageManager().getPackageUidAsUser(kpuPackageName, UserHandle.getCallingUserId()));
                    intent2.setPackage(kpuPackageName);
                    GeofenceService.this.mContext.sendBroadcastAsUser(intent2, userHandle, "com.samsung.android.knox.permission.KNOX_GEOFENCING");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /* renamed from: -$$Nest$mcheckMonitoring, reason: not valid java name */
    public static void m518$$Nest$mcheckMonitoring(GeofenceService geofenceService) {
        synchronized (geofenceService) {
            try {
                if (geofenceService.mLocationManager == null) {
                    geofenceService.mLocationManager = (LocationManager) geofenceService.mContext.getSystemService("location");
                }
                GeoLocationListener geoLocationListener = mLocationListener;
                if (geoLocationListener != null) {
                    geofenceService.mLocationManager.removeUpdates(geoLocationListener);
                    mLocationListener = null;
                }
                if (geofenceService.mActiveGeofenceList.isEmpty()) {
                    return;
                }
                try {
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(1);
                    criteria.setAltitudeRequired(false);
                    criteria.setBearingRequired(false);
                    criteria.setCostAllowed(false);
                    criteria.setPowerRequirement(1);
                    mLocationListener = geofenceService.new GeoLocationListener();
                    geofenceService.mLocationManager.requestLocationUpdates(geofenceService.getEffectiveMinTimeParameter(), geofenceService.getEffectiveMinDistanceParameter(), criteria, mLocationListener, geofenceService.mHandlerThread.getLooper());
                } catch (Exception e) {
                    Log.d("GeofenceService", "checkMonitoring - EX" + e);
                    mLocationListener = null;
                    geofenceService.deviceLocationUnavailableMessage(-1);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.enterprise.geofencing.GeofenceService$1] */
    public GeofenceService(Context context) {
        this.mEDM = null;
        this.mHandlerThread = null;
        this.mHandler = null;
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.geofencing.GeofenceService.1
            public final /* synthetic */ GeofenceService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra;
                switch (i) {
                    case 0:
                        String action = intent.getAction();
                        if (action != null && action.equals("android.location.PROVIDERS_CHANGED")) {
                            Message obtainMessage = this.this$0.mHandler.obtainMessage(3);
                            obtainMessage.arg1 = UserHandle.myUserId();
                            this.this$0.mHandler.sendMessage(obtainMessage);
                            break;
                        }
                        break;
                    default:
                        String action2 = intent.getAction();
                        if (action2 != null && action2.equals("android.intent.action.USER_REMOVED")) {
                            Message obtainMessage2 = this.this$0.mHandler.obtainMessage(2);
                            obtainMessage2.arg1 = UserHandle.myUserId();
                            this.this$0.mHandler.sendMessage(obtainMessage2);
                            break;
                        } else if (action2 != null && action2.equals("android.intent.action.USER_STOPPED") && (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1)) >= 1) {
                            Iterator it = this.this$0.mEdmStorageProvider.getIntListAsUser(0, intExtra, "ADMIN", "adminUid").iterator();
                            while (it.hasNext()) {
                                this.this$0.deleteGeofenceActiveListByAdmin(((Integer) it.next()).intValue());
                            }
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mUserRemovedReceiver = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.geofencing.GeofenceService.1
            public final /* synthetic */ GeofenceService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra;
                switch (i2) {
                    case 0:
                        String action = intent.getAction();
                        if (action != null && action.equals("android.location.PROVIDERS_CHANGED")) {
                            Message obtainMessage = this.this$0.mHandler.obtainMessage(3);
                            obtainMessage.arg1 = UserHandle.myUserId();
                            this.this$0.mHandler.sendMessage(obtainMessage);
                            break;
                        }
                        break;
                    default:
                        String action2 = intent.getAction();
                        if (action2 != null && action2.equals("android.intent.action.USER_REMOVED")) {
                            Message obtainMessage2 = this.this$0.mHandler.obtainMessage(2);
                            obtainMessage2.arg1 = UserHandle.myUserId();
                            this.this$0.mHandler.sendMessage(obtainMessage2);
                            break;
                        } else if (action2 != null && action2.equals("android.intent.action.USER_STOPPED") && (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1)) >= 1) {
                            Iterator it = this.this$0.mEdmStorageProvider.getIntListAsUser(0, intExtra, "ADMIN", "adminUid").iterator();
                            while (it.hasNext()) {
                                this.this$0.deleteGeofenceActiveListByAdmin(((Integer) it.next()).intValue());
                            }
                            break;
                        }
                        break;
                }
            }
        };
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        HandlerThread handlerThread = new HandlerThread("GeofenceService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new GeofencingHandler(this.mHandlerThread.getLooper());
        this.mEDM = EnterpriseDeviceManager.getInstance(context);
        context.registerReceiver(broadcastReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
    }

    public static BoundingBox calcBoundingBox(List list) {
        ArrayList arrayList = (ArrayList) list;
        BoundingBox boundingBox = new BoundingBox((LatLongPoint) arrayList.get(0), (LatLongPoint) arrayList.get(0), (LatLongPoint) arrayList.get(0), (LatLongPoint) arrayList.get(0));
        for (int i = 1; i < arrayList.size(); i++) {
            LatLongPoint latLongPoint = (LatLongPoint) arrayList.get(i);
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

    public static boolean checkDeviceInsideBoundingbox(Location location, BoundingBox boundingBox) {
        if (location != null && boundingBox.left != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            if (latitude <= boundingBox.top.latitude && latitude >= boundingBox.bottom.latitude && longitude <= boundingBox.right.longitude && longitude >= boundingBox.left.longitude) {
                return true;
            }
        }
        return false;
    }

    public static int checkDeviceInsidePolygonRayCasting(List list, double d, double d2) {
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
                if (d <= (((d2 - d3) * (d5 - d6)) / (d4 - d3)) + d6) {
                    i3 = i3 == 0 ? 1 : 0;
                }
            }
            i2 = i4;
        }
        return i3;
    }

    public static boolean checkGeofenceInsideOrOutside(Location location, Geofence geofence) {
        int i = geofence.type;
        if (i != 1) {
            if (i == 2) {
                PolygonalGeofence polygonalGeofence = (PolygonalGeofence) geofence;
                if (checkDeviceInsideBoundingbox(location, polygonalGeofence.boundingBox)) {
                    return checkDeviceInsidePolygonRayCasting(polygonalGeofence.optimizedPoints, location.getLatitude(), location.getLongitude()) == 1 || checkDeviceInsidePolygonRayCasting(polygonalGeofence.pointsWithinGraceLimit, location.getLatitude(), location.getLongitude()) == 1;
                }
                return false;
            }
            if (i != 3) {
                return false;
            }
            LinearGeofence linearGeofence = (LinearGeofence) geofence;
            return checkDeviceInsideBoundingbox(location, linearGeofence.boundingBox) && checkDeviceInsidePolygonRayCasting(linearGeofence.optimizedPoints, location.getLatitude(), location.getLongitude()) == 1;
        }
        CircularGeofence circularGeofence = (CircularGeofence) geofence;
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLongPoint latLongPoint = circularGeofence.center;
        double d = latLongPoint.latitude;
        double d2 = latLongPoint.longitude;
        double radians = Math.toRadians(latitude);
        double radians2 = Math.toRadians(longitude);
        double radians3 = Math.toRadians(d);
        return (Math.acos((Math.cos(radians2 - Math.toRadians(d2)) * (Math.cos(radians) * Math.cos(radians3))) + (Math.sin(radians) * Math.sin(radians3))) * 6371.0d) * 1000.0d <= circularGeofence.radius;
    }

    public static List convertToLinear(LinearGeofence linearGeofence) {
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

    public static List findCollinear(List list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add((LatLongPoint) list.get(0));
        int i = 2;
        int i2 = 0;
        int i3 = 1;
        while (i < list.size()) {
            LatLongPoint latLongPoint = (LatLongPoint) list.get(i2);
            LatLongPoint latLongPoint2 = (LatLongPoint) list.get(i3);
            LatLongPoint latLongPoint3 = (LatLongPoint) list.get(i);
            double d = latLongPoint.latitude;
            double d2 = latLongPoint2.longitude;
            int i4 = i;
            double d3 = latLongPoint3.longitude;
            double d4 = (d2 - d3) * d;
            double d5 = latLongPoint2.latitude;
            int i5 = i2;
            int i6 = i3;
            double d6 = latLongPoint.longitude;
            if (((d6 - d2) * latLongPoint3.latitude) + ((d3 - d6) * d5) + d4 == 0.0d) {
                if (!arrayList2.contains(latLongPoint)) {
                    arrayList2.add(latLongPoint);
                }
                if (!arrayList2.contains(latLongPoint2)) {
                    arrayList2.add(latLongPoint2);
                }
                if (!arrayList2.contains(latLongPoint3)) {
                    arrayList2.add(latLongPoint3);
                }
            } else if (arrayList2.isEmpty()) {
                arrayList.add(latLongPoint2);
            } else {
                LatLongPoint latLongPoint4 = (LatLongPoint) arrayList2.get(0);
                for (int i7 = 1; i7 < arrayList2.size(); i7++) {
                    if (((LatLongPoint) arrayList2.get(i7)).latitude < latLongPoint4.latitude && ((LatLongPoint) arrayList2.get(i7)).longitude < latLongPoint4.longitude) {
                        latLongPoint4 = (LatLongPoint) arrayList2.get(i7);
                    }
                }
                arrayList.add(latLongPoint4);
                LatLongPoint latLongPoint5 = (LatLongPoint) arrayList2.get(0);
                for (int i8 = 1; i8 < arrayList2.size(); i8++) {
                    if (((LatLongPoint) arrayList2.get(i8)).latitude > latLongPoint5.latitude && ((LatLongPoint) arrayList2.get(i8)).longitude > latLongPoint5.longitude) {
                        latLongPoint5 = (LatLongPoint) arrayList2.get(i8);
                    }
                }
                arrayList.add(latLongPoint5);
                arrayList2.clear();
            }
            i2 = i5 + 1;
            i3 = i6 + 1;
            i = i4 + 1;
        }
        arrayList.add((LatLongPoint) list.get(list.size() - 1));
        return arrayList;
    }

    public static byte[] serializeGeoFence(Object obj) {
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

    public static boolean validatePolygonalGeofence(List list) {
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

    public final synchronized List checkDeviceInsideOrOutsideGeo(boolean z, Location location) {
        try {
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
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x03c9, code lost:
    
        if (r3.longitude >= r2.longitude) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x047f, code lost:
    
        if (r3.longitude <= r2.longitude) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x048e, code lost:
    
        r7 = r0.longitude;
        r19 = r19 * r14;
        r10 = r7 - r19;
        r4 = r2;
        r33 = r3;
        r6.add(new com.samsung.android.knox.location.LatLongPoint(((r0.latitude * r14) + (r10 - r7)) / r14, r10));
        r2 = r0.longitude;
        r7 = r2 + r19;
        r13.add(new com.samsung.android.knox.location.LatLongPoint(((r0.latitude * r14) + (r7 - r2)) / r14, r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x04b9, code lost:
    
        if (r9 != 0) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x04bb, code lost:
    
        r16 = r33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x04c3, code lost:
    
        if (r9 != (r5.size() - 1)) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x04c6, code lost:
    
        r4 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x04c8, code lost:
    
        if (r9 == 0) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x04cf, code lost:
    
        if (r9 != (r5.size() - 1)) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x04d2, code lost:
    
        r16 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x04d6, code lost:
    
        r2 = r4.longitude;
        r7 = r2 - r19;
        r10 = ((r4.latitude * r14) + (r7 - r2)) / r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x04e1, code lost:
    
        if (r9 != 0) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x04e3, code lost:
    
        r2 = r39;
        r2.add(new com.samsung.android.knox.location.LatLongPoint(r10, r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x04f7, code lost:
    
        r7 = r4.longitude;
        r10 = r7 + r19;
        r39 = r2;
        r2 = ((r4.latitude * r14) + (r10 - r7)) / r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0504, code lost:
    
        if (r9 != 0) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0506, code lost:
    
        r1.add(new com.samsung.android.knox.location.LatLongPoint(r2, r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x050f, code lost:
    
        r14 = new com.samsung.android.knox.location.LatLongPoint(r2, r10);
        r16 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x04ee, code lost:
    
        r2 = r39;
        r26 = new com.samsung.android.knox.location.LatLongPoint(r10, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x048c, code lost:
    
        if (r3.longitude < r2.longitude) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x018f, code lost:
    
        if (r14.longitude >= r15.longitude) goto L61;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int createGeofence(com.samsung.android.knox.ContextInfo r38, com.samsung.android.knox.location.Geofence r39) {
        /*
            Method dump skipped, instructions count: 1571
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.geofencing.GeofenceService.createGeofence(com.samsung.android.knox.ContextInfo, com.samsung.android.knox.location.Geofence):int");
    }

    public final synchronized boolean deleteFromDB(ContextInfo contextInfo, int i) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        HashMap hashMap = new HashMap();
        hashMap.put(KnoxCustomManagerService.ID, Integer.toString(i));
        if (this.mEdmStorageProvider.removeByFieldsAsUser(callingOrCurrentUserId, "GEOFENCING", hashMap) == 0) {
            return false;
        }
        this.mActiveGeofenceList.remove(Integer.valueOf(i));
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0033, code lost:
    
        if (r0 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0060, code lost:
    
        if (r0 == null) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void deleteGeofenceActiveListByAdmin(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            java.lang.String r1 = "_id"
            java.lang.String[] r1 = new java.lang.String[]{r1}     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            com.android.server.enterprise.storage.EdmStorageProvider r2 = r5.mEdmStorageProvider     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            java.lang.String r3 = "GEOFENCING"
            r4 = 0
            android.database.Cursor r0 = r2.getCursorByAdmin(r6, r4, r3, r1)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            if (r0 == 0) goto L33
        L13:
            boolean r6 = r0.moveToNext()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            if (r6 == 0) goto L33
            java.util.HashMap r6 = r5.mActiveGeofenceList     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            java.lang.String r1 = "_id"
            int r1 = r0.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            int r1 = r0.getInt(r1)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            r6.remove(r1)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f android.database.SQLException -> L31
            goto L13
        L2d:
            r6 = move-exception
            goto L65
        L2f:
            r6 = move-exception
            goto L3b
        L31:
            r6 = move-exception
            goto L46
        L33:
            if (r0 == 0) goto L63
        L35:
            r0.close()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L63
            goto L63
        L39:
            r6 = move-exception
            goto L6b
        L3b:
            java.lang.String r1 = "GeofenceService"
            java.lang.String r2 = "deleteGeofenceActiveListByAdmin - EX"
            android.util.Log.w(r1, r2, r6)     // Catch: java.lang.Throwable -> L2d
            if (r0 == 0) goto L63
            goto L35
        L46:
            java.lang.String r1 = "GeofenceService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2d
            r2.<init>()     // Catch: java.lang.Throwable -> L2d
            java.lang.String r3 = "Exception occurred accessing Enterprise db "
            r2.append(r3)     // Catch: java.lang.Throwable -> L2d
            java.lang.String r6 = r6.getMessage()     // Catch: java.lang.Throwable -> L2d
            r2.append(r6)     // Catch: java.lang.Throwable -> L2d
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L2d
            android.util.Log.e(r1, r6)     // Catch: java.lang.Throwable -> L2d
            if (r0 == 0) goto L63
            goto L35
        L63:
            monitor-exit(r5)
            return
        L65:
            if (r0 == 0) goto L6a
            r0.close()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L6a
        L6a:
            throw r6     // Catch: java.lang.Throwable -> L39
        L6b:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.geofencing.GeofenceService.deleteGeofenceActiveListByAdmin(int):void");
    }

    public final synchronized boolean destroyGeofence(ContextInfo contextInfo, int i) {
        boolean deleteFromDB;
        try {
            Log.d("GeofenceService", "destroyGeofence");
            ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
            int i2 = enforceGeofencingPermission.mCallerUid;
            deleteFromDB = deleteFromDB(enforceGeofencingPermission, i);
            if (deleteFromDB && !isAdminHasGeofence(i2)) {
                this.mEdmStorageProvider.putInt(i2, 0, 0, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
                this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
            } else if (deleteFromDB) {
                this.mHandler.sendMessage(this.mHandler.obtainMessage(1, Integer.valueOf(i2)));
            }
        } catch (Throwable th) {
            throw th;
        }
        return deleteFromDB;
    }

    public final void deviceLocationUnavailableMessage(int i) {
        Log.d("GeofenceService", "DEVICE_LOCATION_UNAVAILABLE");
        Intent intent = new Intent("com.samsung.android.knox.intent.action.DEVICE_LOCATION_UNAVAILABLE");
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", UserHandle.getUserId(i));
        intent.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", i);
        if (i != -1) {
            intent.setPackage(getPackageNameForUid$2(i));
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

    public final ContextInfo enforceGeofencingPermission(ContextInfo contextInfo) {
        return this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_GEOFENCING")));
    }

    public final List getActiveGeofenceIdsbyAdmin(int i, List list) {
        if (list != null && !list.isEmpty()) {
            int userId = UserHandle.getUserId(i);
            try {
                ArrayList arrayList = new ArrayList();
                Iterator it = ((ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, userId, "GEOFENCING", new String[]{"adminUid", KnoxCustomManagerService.ID})).iterator();
                while (it.hasNext()) {
                    ContentValues contentValues = (ContentValues) it.next();
                    Integer asInteger = contentValues.getAsInteger(KnoxCustomManagerService.ID);
                    asInteger.getClass();
                    Integer asInteger2 = contentValues.getAsInteger("adminUid");
                    if (asInteger2 != null && asInteger2.intValue() == i && list.contains(asInteger)) {
                        arrayList.add(asInteger);
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

    public final float getEffectiveMinDistanceParameter() {
        float f;
        Iterator it = ((ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, 0, "GEOFENCINGSETTINGS", new String[]{"distance", LauncherConfigurationInternal.KEY_STATE_BOOLEAN})).iterator();
        float f2 = 0.0f;
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            Integer asInteger = contentValues.getAsInteger(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            if (asInteger == null || asInteger.intValue() != 0) {
                try {
                    f = Float.parseFloat(contentValues.getAsString("distance"));
                } catch (Exception unused) {
                    f = 0.0f;
                }
                if (f2 == FullScreenMagnificationGestureHandler.MAX_SCALE || (f != FullScreenMagnificationGestureHandler.MAX_SCALE && f2 > f)) {
                    f2 = f;
                }
            }
        }
        return f2;
    }

    public final long getEffectiveMinTimeParameter() {
        long j;
        Iterator it = ((ArrayList) this.mEdmStorageProvider.getValuesListAsUser(0, 0, "GEOFENCINGSETTINGS", new String[]{"time", LauncherConfigurationInternal.KEY_STATE_BOOLEAN})).iterator();
        long j2 = 0;
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            Integer asInteger = contentValues.getAsInteger(LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            if (asInteger == null || asInteger.intValue() != 0) {
                try {
                    j = Long.parseLong(contentValues.getAsString("time"));
                } catch (Exception unused) {
                    j = 0;
                }
                if (j2 == 0 || (j != 0 && j2 > j)) {
                    j2 = j;
                }
            }
        }
        if (j2 == 0) {
            return 60000L;
        }
        return j2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b8, code lost:
    
        if (r9 == null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0087, code lost:
    
        if (r9 != null) goto L47;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getGeofences(com.samsung.android.knox.ContextInfo r10) {
        /*
            r9 = this;
            java.lang.String r0 = "blobdata"
            java.lang.String r1 = "type"
            java.lang.String r2 = "_id"
            java.lang.String r3 = "GeofenceService"
            java.lang.String r4 = "getGeofences"
            android.util.Log.d(r3, r4)
            com.samsung.android.knox.ContextInfo r10 = r9.enforceGeofencingPermission(r10)
            int r10 = r10.mCallerUid
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = 0
            java.lang.String[] r6 = new java.lang.String[]{r2, r1, r0}     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93 android.database.SQLException -> L95
            com.android.server.enterprise.storage.EdmStorageProvider r9 = r9.mEdmStorageProvider     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93 android.database.SQLException -> L95
            java.lang.String r7 = "GEOFENCING"
            r8 = 0
            android.database.Cursor r9 = r9.getCursorByAdmin(r10, r8, r7, r6)     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L93 android.database.SQLException -> L95
            if (r9 == 0) goto L87
        L2b:
            boolean r10 = r9.moveToNext()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            if (r10 == 0) goto L87
            int r10 = r9.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            int r10 = r9.getInt(r10)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            int r6 = r9.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            int r6 = r9.getInt(r6)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            int r7 = r9.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            byte[] r7 = r9.getBlob(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r8 = 2
            if (r6 != r8) goto L62
            java.lang.Object r7 = deserializeGeoFence(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            com.samsung.android.knox.location.PolygonalGeofence r7 = (com.samsung.android.knox.location.PolygonalGeofence) r7     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r7.id = r10     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r7.type = r6     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r4.add(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            goto L2b
        L5a:
            r10 = move-exception
            r5 = r9
            goto Lc3
        L5e:
            r10 = move-exception
            goto L97
        L60:
            r10 = move-exception
            goto La0
        L62:
            r8 = 3
            if (r6 != r8) goto L79
            java.lang.Object r7 = deserializeGeoFence(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            com.samsung.android.knox.location.LinearGeofence r7 = (com.samsung.android.knox.location.LinearGeofence) r7     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r7.id = r10     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r7.type = r6     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            java.util.List r10 = convertToLinear(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r7.points = r10     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r4.add(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            goto L2b
        L79:
            java.lang.Object r7 = deserializeGeoFence(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            com.samsung.android.knox.location.CircularGeofence r7 = (com.samsung.android.knox.location.CircularGeofence) r7     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r7.id = r10     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r7.type = r6     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            r4.add(r7)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5e android.database.SQLException -> L60
            goto L2b
        L87:
            if (r9 == 0) goto Lbb
        L89:
            r9.close()     // Catch: java.lang.Exception -> Lbb
            goto Lbb
        L8d:
            r9 = r5
            goto L97
        L8f:
            r9 = r5
            goto La0
        L91:
            r10 = move-exception
            goto Lc3
        L93:
            r10 = move-exception
            goto L8d
        L95:
            r10 = move-exception
            goto L8f
        L97:
            java.lang.String r0 = "getGeofences - EX"
            android.util.Log.w(r3, r0, r10)     // Catch: java.lang.Throwable -> L5a
            if (r9 == 0) goto Lbb
            goto L89
        La0:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a
            r0.<init>()     // Catch: java.lang.Throwable -> L5a
            java.lang.String r1 = "Exception occurred accessing Enterprise db "
            r0.append(r1)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r10 = r10.getMessage()     // Catch: java.lang.Throwable -> L5a
            r0.append(r10)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r10 = r0.toString()     // Catch: java.lang.Throwable -> L5a
            android.util.Log.e(r3, r10)     // Catch: java.lang.Throwable -> L5a
            if (r9 == 0) goto Lbb
            goto L89
        Lbb:
            boolean r9 = r4.isEmpty()
            if (r9 == 0) goto Lc2
            r4 = r5
        Lc2:
            return r4
        Lc3:
            if (r5 == 0) goto Lc8
            r5.close()     // Catch: java.lang.Exception -> Lc8
        Lc8:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.geofencing.GeofenceService.getGeofences(com.samsung.android.knox.ContextInfo):java.util.List");
    }

    public final float getMinDistanceParameter(ContextInfo contextInfo) {
        try {
            return Float.parseFloat(this.mEdmStorageProvider.getString(Utils.getCallingOrUserUid(enforceGeofencingPermission(contextInfo)), 0, "GEOFENCINGSETTINGS", "distance"));
        } catch (Exception unused) {
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
    }

    public final long getMinTimeParameter(ContextInfo contextInfo) {
        try {
            return Long.parseLong(this.mEdmStorageProvider.getString(Utils.getCallingOrUserUid(enforceGeofencingPermission(contextInfo)), 0, "GEOFENCINGSETTINGS", "time"));
        } catch (Exception unused) {
            return 0L;
        }
    }

    public final String getPackageNameForUid$2(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "@getPackageNameForUid - uid = ", "GeofenceService");
        if (i != 1000 && (i < 10000 || i > 19999)) {
            Log.d("GeofenceService", "@getPackageNameForUid - returning UMC PACKAGENAME");
            return "com.sec.enterprise.knox.cloudmdm.smdms";
        }
        String string = this.mEdmStorageProvider.getString(i, 0, "ADMIN_INFO", "adminName");
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

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0041, code lost:
    
        if (r3 == null) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x002a, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0028, code lost:
    
        if (r3 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAdminHasGeofence(int r7) {
        /*
            r6 = this;
            java.lang.String r0 = "GeofenceService"
            java.lang.String r1 = "Exception occurred accessing Enterprise db "
            r2 = 0
            r3 = 0
            java.lang.String r4 = "_id"
            java.lang.String[] r4 = new java.lang.String[]{r4}     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L1e android.database.SQLException -> L20
            com.android.server.enterprise.storage.EdmStorageProvider r6 = r6.mEdmStorageProvider     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L1e android.database.SQLException -> L20
            java.lang.String r5 = "GEOFENCING"
            android.database.Cursor r3 = r6.getCursorByAdmin(r7, r2, r5, r4)     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L1e android.database.SQLException -> L20
            boolean r6 = r3.moveToNext()     // Catch: java.lang.Throwable -> L1c java.lang.Exception -> L1e android.database.SQLException -> L20
            r3.close()     // Catch: java.lang.Exception -> L1b
        L1b:
            return r6
        L1c:
            r6 = move-exception
            goto L45
        L1e:
            r6 = move-exception
            goto L22
        L20:
            r6 = move-exception
            goto L2e
        L22:
            java.lang.String r7 = "isAdminHasGeofence - EX"
            android.util.Log.w(r0, r7, r6)     // Catch: java.lang.Throwable -> L1c
            if (r3 == 0) goto L44
        L2a:
            r3.close()     // Catch: java.lang.Exception -> L44
            goto L44
        L2e:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1c
            r7.<init>(r1)     // Catch: java.lang.Throwable -> L1c
            java.lang.String r6 = r6.getMessage()     // Catch: java.lang.Throwable -> L1c
            r7.append(r6)     // Catch: java.lang.Throwable -> L1c
            java.lang.String r6 = r7.toString()     // Catch: java.lang.Throwable -> L1c
            android.util.Log.e(r0, r6)     // Catch: java.lang.Throwable -> L1c
            if (r3 == 0) goto L44
            goto L2a
        L44:
            return r2
        L45:
            if (r3 == 0) goto L4a
            r3.close()     // Catch: java.lang.Exception -> L4a
        L4a:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.geofencing.GeofenceService.isAdminHasGeofence(int):boolean");
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
            list = getActiveGeofenceIdsbyAdmin(i, checkDeviceInsideOrOutsideGeo(false, location));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return list;
    }

    public final List isDeviceInsideGeofence(ContextInfo contextInfo) {
        return isDeviceInsideGeofence(enforceGeofencingPermission(contextInfo).mCallerUid);
    }

    public final boolean isGeofencingEnabled(ContextInfo contextInfo) {
        try {
            return this.mEdmStorageProvider.getInt(enforceGeofencingPermission(contextInfo).mCallerUid, 0, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN) == 1;
        } catch (SettingNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        deleteGeofenceActiveListByAdmin(i);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    public final void sendIntenttoAdmins(List list) {
        Log.d("GeofenceService", "sendIntenttoAdmins");
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        for (UserInfo userInfo : userManager.getUsers()) {
            if (userManager.isUserRunning(userInfo.getUserHandle())) {
                Iterator it = this.mEdmStorageProvider.getIntListAsUser(0, userInfo.id, "ADMIN", "adminUid").iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    List activeGeofenceIdsbyAdmin = getActiveGeofenceIdsbyAdmin(intValue, list);
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
                        String packageNameForUid$2 = getPackageNameForUid$2(proxyAdminOwnerUid);
                        Log.d("GeofenceService", "@sendIntenttoAdmins - thePackageNameFortheUID = " + packageNameForUid$2);
                        intent.setPackage(packageNameForUid$2);
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
                            if (this.mEdmStorageProvider.getInt(intValue, 0, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN) == 1) {
                                Intent intent3 = new Intent("com.samsung.android.knox.intent.action.DEVICE_OUTSIDE_GEOFENCE");
                                intent3.putExtra("com.samsung.android.knox.intent.extra.USER_ID", UserHandle.getUserId(intValue));
                                intent3.putExtra("com.samsung.android.knox.intent.extra.ADMIN_UID", intValue);
                                int proxyAdminOwnerUid2 = Utils.getProxyAdminOwnerUid(this.mEdmStorageProvider, intValue);
                                intent3.setPackage(getPackageNameForUid$2(proxyAdminOwnerUid2));
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

    public final boolean setMinDistanceParameter(ContextInfo contextInfo, float f) {
        int i = enforceGeofencingPermission(contextInfo).mCallerUid;
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return false;
        }
        boolean putString = this.mEdmStorageProvider.putString(i, 0, "GEOFENCINGSETTINGS", "distance", Float.toString(f));
        if (putString) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
        }
        return putString;
    }

    public final boolean setMinTimeParameter(ContextInfo contextInfo, long j) {
        int i = enforceGeofencingPermission(contextInfo).mCallerUid;
        if (j < 0) {
            return false;
        }
        boolean putString = this.mEdmStorageProvider.putString(i, 0, "GEOFENCINGSETTINGS", "time", Long.toString(j));
        if (putString) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
        }
        return putString;
    }

    public final boolean startGeofencing(ContextInfo contextInfo) {
        Log.d("GeofenceService", "startGeofencing");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i = enforceGeofencingPermission.mCallerUid;
        if (!isAdminHasGeofence(i)) {
            return false;
        }
        boolean isGeofencingEnabled = isGeofencingEnabled(enforceGeofencingPermission);
        synchronized (this) {
            if (isGeofencingEnabled) {
                try {
                    if (mLocationListener != null) {
                        return true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            boolean putInt = this.mEdmStorageProvider.putInt(i, 0, 1, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
            if (putInt) {
                updateGeofenceActiveListbyAdmin(i);
                this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
            }
            return putInt;
        }
    }

    public final boolean stopGeofencing(ContextInfo contextInfo) {
        Log.d("GeofenceService", "stopGeofencing");
        ContextInfo enforceGeofencingPermission = enforceGeofencingPermission(contextInfo);
        int i = enforceGeofencingPermission.mCallerUid;
        if (!isAdminHasGeofence(i)) {
            return false;
        }
        if (!isGeofencingEnabled(enforceGeofencingPermission)) {
            return true;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i, 0, 0, "GEOFENCINGSETTINGS", LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        if (putInt) {
            deleteGeofenceActiveListByAdmin(i);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
        }
        return putInt;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        this.mContext.registerReceiver(this.mUserRemovedReceiver, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED", "android.intent.action.USER_STOPPED"), 2);
        Message obtainMessage = this.mHandler.obtainMessage(2);
        obtainMessage.arg1 = UserHandle.myUserId();
        this.mHandler.sendMessageDelayed(obtainMessage, 5000L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0093, code lost:
    
        if (r0 != null) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c0, code lost:
    
        if (r0 == null) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void updateGeofenceActiveListbyAdmin(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            java.lang.String r1 = "_id"
            java.lang.String r2 = "type"
            java.lang.String r3 = "blobdata"
            java.lang.String[] r1 = new java.lang.String[]{r1, r2, r3}     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            com.android.server.enterprise.storage.EdmStorageProvider r2 = r5.mEdmStorageProvider     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.lang.String r3 = "GEOFENCING"
            r4 = 0
            android.database.Cursor r0 = r2.getCursorByAdmin(r6, r4, r3, r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            if (r0 == 0) goto L93
        L19:
            boolean r6 = r0.moveToNext()     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            if (r6 == 0) goto L93
            java.lang.String r6 = "_id"
            int r6 = r0.getColumnIndex(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            int r6 = r0.getInt(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.util.HashMap r1 = r5.mActiveGeofenceList     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            boolean r1 = r1.containsKey(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            if (r1 != 0) goto L19
            java.lang.String r1 = "type"
            int r1 = r0.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            int r1 = r0.getInt(r1)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.lang.String r2 = "blobdata"
            int r2 = r0.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            byte[] r2 = r0.getBlob(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r3 = 2
            if (r1 != r3) goto L68
            java.lang.Object r2 = deserializeGeoFence(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            com.samsung.android.knox.location.PolygonalGeofence r2 = (com.samsung.android.knox.location.PolygonalGeofence) r2     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r2.id = r6     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r2.type = r1     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.util.HashMap r1 = r5.mActiveGeofenceList     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r1.put(r6, r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            goto L19
        L62:
            r6 = move-exception
            goto Lc5
        L64:
            r6 = move-exception
            goto L9b
        L66:
            r6 = move-exception
            goto La6
        L68:
            r3 = 3
            if (r1 != r3) goto L7f
            java.lang.Object r2 = deserializeGeoFence(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            com.samsung.android.knox.location.LinearGeofence r2 = (com.samsung.android.knox.location.LinearGeofence) r2     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r2.id = r6     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r2.type = r1     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.util.HashMap r1 = r5.mActiveGeofenceList     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r1.put(r6, r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            goto L19
        L7f:
            java.lang.Object r2 = deserializeGeoFence(r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            com.samsung.android.knox.location.CircularGeofence r2 = (com.samsung.android.knox.location.CircularGeofence) r2     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r2.id = r6     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r2.type = r1     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.util.HashMap r1 = r5.mActiveGeofenceList     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            r1.put(r6, r2)     // Catch: java.lang.Throwable -> L62 java.lang.Exception -> L64 android.database.SQLException -> L66
            goto L19
        L93:
            if (r0 == 0) goto Lc3
        L95:
            r0.close()     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> Lc3
            goto Lc3
        L99:
            r6 = move-exception
            goto Lcb
        L9b:
            java.lang.String r1 = "GeofenceService"
            java.lang.String r2 = "updateGeofenceActiveList - EX"
            android.util.Log.w(r1, r2, r6)     // Catch: java.lang.Throwable -> L62
            if (r0 == 0) goto Lc3
            goto L95
        La6:
            java.lang.String r1 = "GeofenceService"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r2.<init>()     // Catch: java.lang.Throwable -> L62
            java.lang.String r3 = "Exception occurred accessing Enterprise db "
            r2.append(r3)     // Catch: java.lang.Throwable -> L62
            java.lang.String r6 = r6.getMessage()     // Catch: java.lang.Throwable -> L62
            r2.append(r6)     // Catch: java.lang.Throwable -> L62
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L62
            android.util.Log.e(r1, r6)     // Catch: java.lang.Throwable -> L62
            if (r0 == 0) goto Lc3
            goto L95
        Lc3:
            monitor-exit(r5)
            return
        Lc5:
            if (r0 == 0) goto Lca
            r0.close()     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> Lca
        Lca:
            throw r6     // Catch: java.lang.Throwable -> L99
        Lcb:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.geofencing.GeofenceService.updateGeofenceActiveListbyAdmin(int):void");
    }

    public final int writeGeofenceToDB(ContextInfo contextInfo, int i, byte[] bArr) {
        int i2 = contextInfo.mCallerUid;
        if (bArr == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(i2, contentValues, "adminUid", i, "type");
        contentValues.put("blobdata", bArr);
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        edmStorageProvider.getClass();
        EdmStorageProviderBase.convertAdminIdToLUID(contentValues);
        int insert = (int) edmStorageProvider.insert("GEOFENCING", contentValues);
        updateGeofenceActiveListbyAdmin(i2);
        return insert;
    }
}
