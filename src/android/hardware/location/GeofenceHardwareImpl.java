package android.hardware.location;

import android.Manifest;
import android.content.Context;
import android.location.IFusedGeofenceHardware;
import android.location.IGpsGeofenceHardware;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final class GeofenceHardwareImpl {
    private static final int ADD_GEOFENCE_CALLBACK = 2;
    private static final int CALLBACK_ADD = 2;
    private static final int CALLBACK_REMOVE = 3;
    private static final int CAPABILITY_GNSS = 1;
    private static final int FIRST_VERSION_WITH_CAPABILITIES = 2;
    private static final int GEOFENCE_CALLBACK_BINDER_DIED = 6;
    private static final int GEOFENCE_STATUS = 1;
    private static final int GEOFENCE_TRANSITION_CALLBACK = 1;
    private static final int LOCATION_HAS_ACCURACY = 16;
    private static final int LOCATION_HAS_ALTITUDE = 2;
    private static final int LOCATION_HAS_BEARING = 8;
    private static final int LOCATION_HAS_LAT_LONG = 1;
    private static final int LOCATION_HAS_SPEED = 4;
    private static final int LOCATION_INVALID = 0;
    private static final int MONITOR_CALLBACK_BINDER_DIED = 4;
    private static final int PAUSE_GEOFENCE_CALLBACK = 4;
    private static final int REAPER_GEOFENCE_ADDED = 1;
    private static final int REAPER_MONITOR_CALLBACK_ADDED = 2;
    private static final int REAPER_REMOVED = 3;
    private static final int REMOVE_GEOFENCE_CALLBACK = 3;
    private static final int RESOLUTION_LEVEL_COARSE = 2;
    private static final int RESOLUTION_LEVEL_FINE = 3;
    private static final int RESOLUTION_LEVEL_NONE = 1;
    private static final int RESUME_GEOFENCE_CALLBACK = 5;
    private static GeofenceHardwareImpl sInstance;
    private int mCapabilities;
    private final Context mContext;
    private IFusedGeofenceHardware mFusedService;
    private IGpsGeofenceHardware mGpsService;
    private PowerManager.WakeLock mWakeLock;
    private static final String TAG = "GeofenceHardwareImpl";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private final SparseArray<IGeofenceHardwareCallback> mGeofences = new SparseArray<>();
    private final ArrayList<IGeofenceHardwareMonitorCallback>[] mCallbacks = new ArrayList[2];
    private final ArrayList<Reaper> mReapers = new ArrayList<>();
    private int mVersion = 1;
    private int[] mSupportedMonitorTypes = new int[2];
    private Handler mGeofenceHandler = new Handler() { // from class: android.hardware.location.GeofenceHardwareImpl.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            IGeofenceHardwareCallback iGeofenceHardwareCallback;
            IGeofenceHardwareCallback iGeofenceHardwareCallback2;
            IGeofenceHardwareCallback iGeofenceHardwareCallback3;
            IGeofenceHardwareCallback iGeofenceHardwareCallback4;
            IGeofenceHardwareCallback iGeofenceHardwareCallback5;
            int i = 0;
            switch (message.what) {
                case 1:
                    GeofenceTransition geofenceTransition = (GeofenceTransition) message.obj;
                    synchronized (GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback = (IGeofenceHardwareCallback) GeofenceHardwareImpl.this.mGeofences.get(geofenceTransition.mGeofenceId);
                        if (GeofenceHardwareImpl.DEBUG) {
                            Log.d(GeofenceHardwareImpl.TAG, "GeofenceTransistionCallback: GPS : GeofenceId: " + geofenceTransition.mGeofenceId + " Transition: " + geofenceTransition.mTransition + " Location: " + geofenceTransition.mLocation + ":" + GeofenceHardwareImpl.this.mGeofences);
                        }
                    }
                    if (iGeofenceHardwareCallback != null) {
                        try {
                            iGeofenceHardwareCallback.onGeofenceTransition(geofenceTransition.mGeofenceId, geofenceTransition.mTransition, geofenceTransition.mLocation, geofenceTransition.mTimestamp, geofenceTransition.mMonitoringType);
                        } catch (RemoteException unused) {
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 2:
                    int i2 = message.arg1;
                    synchronized (GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback2 = (IGeofenceHardwareCallback) GeofenceHardwareImpl.this.mGeofences.get(i2);
                    }
                    if (iGeofenceHardwareCallback2 != null) {
                        try {
                            iGeofenceHardwareCallback2.onGeofenceAdd(i2, message.arg2);
                        } catch (RemoteException e) {
                            Log.i(GeofenceHardwareImpl.TAG, "Remote Exception:" + e);
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 3:
                    int i3 = message.arg1;
                    synchronized (GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback3 = (IGeofenceHardwareCallback) GeofenceHardwareImpl.this.mGeofences.get(i3);
                    }
                    if (iGeofenceHardwareCallback3 != null) {
                        try {
                            iGeofenceHardwareCallback3.onGeofenceRemove(i3, message.arg2);
                        } catch (RemoteException unused2) {
                        }
                        IBinder asBinder = iGeofenceHardwareCallback3.asBinder();
                        synchronized (GeofenceHardwareImpl.this.mGeofences) {
                            GeofenceHardwareImpl.this.mGeofences.remove(i3);
                            int i4 = 0;
                            while (true) {
                                if (i4 < GeofenceHardwareImpl.this.mGeofences.size()) {
                                    if (((IGeofenceHardwareCallback) GeofenceHardwareImpl.this.mGeofences.valueAt(i4)).asBinder() == asBinder) {
                                        i = 1;
                                    } else {
                                        i4++;
                                    }
                                }
                            }
                        }
                        if (i == 0) {
                            Iterator it = GeofenceHardwareImpl.this.mReapers.iterator();
                            while (it.hasNext()) {
                                Reaper reaper = (Reaper) it.next();
                                if (reaper.mCallback != null && reaper.mCallback.asBinder() == asBinder) {
                                    it.remove();
                                    reaper.unlinkToDeath();
                                    if (GeofenceHardwareImpl.DEBUG) {
                                        Log.d(GeofenceHardwareImpl.TAG, String.format("Removed reaper %s because binder %s is no longer needed.", reaper, asBinder));
                                    }
                                }
                            }
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 4:
                    int i5 = message.arg1;
                    synchronized (GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback4 = (IGeofenceHardwareCallback) GeofenceHardwareImpl.this.mGeofences.get(i5);
                    }
                    if (iGeofenceHardwareCallback4 != null) {
                        try {
                            iGeofenceHardwareCallback4.onGeofencePause(i5, message.arg2);
                        } catch (RemoteException unused3) {
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 5:
                    int i6 = message.arg1;
                    synchronized (GeofenceHardwareImpl.this.mGeofences) {
                        iGeofenceHardwareCallback5 = (IGeofenceHardwareCallback) GeofenceHardwareImpl.this.mGeofences.get(i6);
                    }
                    if (iGeofenceHardwareCallback5 != null) {
                        try {
                            iGeofenceHardwareCallback5.onGeofenceResume(i6, message.arg2);
                        } catch (RemoteException unused4) {
                        }
                    }
                    GeofenceHardwareImpl.this.releaseWakeLock();
                    return;
                case 6:
                    IGeofenceHardwareCallback iGeofenceHardwareCallback6 = (IGeofenceHardwareCallback) message.obj;
                    if (GeofenceHardwareImpl.DEBUG) {
                        Log.d(GeofenceHardwareImpl.TAG, "Geofence callback reaped:" + iGeofenceHardwareCallback6);
                    }
                    int i7 = message.arg1;
                    synchronized (GeofenceHardwareImpl.this.mGeofences) {
                        while (i < GeofenceHardwareImpl.this.mGeofences.size()) {
                            if (((IGeofenceHardwareCallback) GeofenceHardwareImpl.this.mGeofences.valueAt(i)).equals(iGeofenceHardwareCallback6)) {
                                int keyAt = GeofenceHardwareImpl.this.mGeofences.keyAt(i);
                                GeofenceHardwareImpl geofenceHardwareImpl = GeofenceHardwareImpl.this;
                                geofenceHardwareImpl.removeGeofence(geofenceHardwareImpl.mGeofences.keyAt(i), i7);
                                GeofenceHardwareImpl.this.mGeofences.remove(keyAt);
                            }
                            i++;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private Handler mCallbacksHandler = new Handler() { // from class: android.hardware.location.GeofenceHardwareImpl.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent = (GeofenceHardwareMonitorEvent) message.obj;
                ArrayList arrayList = GeofenceHardwareImpl.this.mCallbacks[geofenceHardwareMonitorEvent.getMonitoringType()];
                if (arrayList != null) {
                    if (GeofenceHardwareImpl.DEBUG) {
                        Log.d(GeofenceHardwareImpl.TAG, "MonitoringSystemChangeCallback: " + geofenceHardwareMonitorEvent);
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        try {
                            ((IGeofenceHardwareMonitorCallback) it.next()).onMonitoringSystemChange(geofenceHardwareMonitorEvent);
                        } catch (RemoteException e) {
                            Log.d(GeofenceHardwareImpl.TAG, "Error reporting onMonitoringSystemChange.", e);
                        }
                    }
                }
                GeofenceHardwareImpl.this.releaseWakeLock();
                return;
            }
            if (i == 2) {
                int i2 = message.arg1;
                IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = (IGeofenceHardwareMonitorCallback) message.obj;
                ArrayList arrayList2 = GeofenceHardwareImpl.this.mCallbacks[i2];
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    GeofenceHardwareImpl.this.mCallbacks[i2] = arrayList2;
                }
                if (arrayList2.contains(iGeofenceHardwareMonitorCallback)) {
                    return;
                }
                arrayList2.add(iGeofenceHardwareMonitorCallback);
                return;
            }
            if (i == 3) {
                int i3 = message.arg1;
                IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback2 = (IGeofenceHardwareMonitorCallback) message.obj;
                ArrayList arrayList3 = GeofenceHardwareImpl.this.mCallbacks[i3];
                if (arrayList3 != null) {
                    arrayList3.remove(iGeofenceHardwareMonitorCallback2);
                    return;
                }
                return;
            }
            if (i != 4) {
                return;
            }
            IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback3 = (IGeofenceHardwareMonitorCallback) message.obj;
            if (GeofenceHardwareImpl.DEBUG) {
                Log.d(GeofenceHardwareImpl.TAG, "Monitor callback reaped:" + iGeofenceHardwareMonitorCallback3);
            }
            ArrayList arrayList4 = GeofenceHardwareImpl.this.mCallbacks[message.arg1];
            if (arrayList4 == null || !arrayList4.contains(iGeofenceHardwareMonitorCallback3)) {
                return;
            }
            arrayList4.remove(iGeofenceHardwareMonitorCallback3);
        }
    };
    private Handler mReaperHandler = new Handler() { // from class: android.hardware.location.GeofenceHardwareImpl.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            try {
                if (i == 1) {
                    IGeofenceHardwareCallback iGeofenceHardwareCallback = (IGeofenceHardwareCallback) message.obj;
                    Reaper reaper = GeofenceHardwareImpl.this.new Reaper(iGeofenceHardwareCallback, message.arg1);
                    if (GeofenceHardwareImpl.this.mReapers.contains(reaper)) {
                        return;
                    }
                    GeofenceHardwareImpl.this.mReapers.add(reaper);
                    iGeofenceHardwareCallback.asBinder().linkToDeath(reaper, 0);
                } else {
                    if (i != 2) {
                        if (i != 3) {
                            return;
                        }
                        GeofenceHardwareImpl.this.mReapers.remove((Reaper) message.obj);
                        return;
                    }
                    IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = (IGeofenceHardwareMonitorCallback) message.obj;
                    Reaper reaper2 = GeofenceHardwareImpl.this.new Reaper(iGeofenceHardwareMonitorCallback, message.arg1);
                    if (GeofenceHardwareImpl.this.mReapers.contains(reaper2)) {
                        return;
                    }
                    GeofenceHardwareImpl.this.mReapers.add(reaper2);
                    iGeofenceHardwareMonitorCallback.asBinder().linkToDeath(reaper2, 0);
                }
            } catch (RemoteException unused) {
            }
        }
    };

    int getMonitoringResolutionLevel(int i) {
        return (i == 0 || i == 1) ? 3 : 1;
    }

    public static synchronized GeofenceHardwareImpl getInstance(Context context) {
        GeofenceHardwareImpl geofenceHardwareImpl;
        synchronized (GeofenceHardwareImpl.class) {
            if (sInstance == null) {
                sInstance = new GeofenceHardwareImpl(context);
            }
            geofenceHardwareImpl = sInstance;
        }
        return geofenceHardwareImpl;
    }

    private GeofenceHardwareImpl(Context context) {
        this.mContext = context;
        setMonitorAvailability(0, 2);
        setMonitorAvailability(1, 2);
    }

    private void acquireWakeLock() {
        if (this.mWakeLock == null) {
            this.mWakeLock = ((PowerManager) this.mContext.getSystemService("power")).newWakeLock(1, TAG);
        }
        this.mWakeLock.acquire();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseWakeLock() {
        if (this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    private void updateGpsHardwareAvailability() {
        boolean z;
        try {
            z = this.mGpsService.isHardwareGeofenceSupported();
        } catch (RemoteException unused) {
            Log.e(TAG, "Remote Exception calling LocationManagerService");
            z = false;
        }
        if (z) {
            setMonitorAvailability(0, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0014 A[Catch: RemoteException -> 0x0020, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x0020, blocks: (B:2:0x0000, B:4:0x0007, B:8:0x0010, B:10:0x0014), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateFusedHardwareAvailability() {
        /*
            r4 = this;
            int r0 = r4.mVersion     // Catch: android.os.RemoteException -> L20
            r1 = 2
            r2 = 0
            r3 = 1
            if (r0 < r1) goto Lf
            int r0 = r4.mCapabilities     // Catch: android.os.RemoteException -> L20
            r0 = r0 & r3
            if (r0 == 0) goto Ld
            goto Lf
        Ld:
            r0 = r2
            goto L10
        Lf:
            r0 = r3
        L10:
            android.location.IFusedGeofenceHardware r1 = r4.mFusedService     // Catch: android.os.RemoteException -> L20
            if (r1 == 0) goto L27
            boolean r1 = r1.isSupported()     // Catch: android.os.RemoteException -> L20
            if (r1 == 0) goto L27
            if (r0 == 0) goto L27
            r4.setMonitorAvailability(r3, r2)
            return
        L20:
            java.lang.String r4 = "GeofenceHardwareImpl"
            java.lang.String r0 = "RemoteException calling LocationManagerService"
            android.util.Log.e(r4, r0)
        L27:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.location.GeofenceHardwareImpl.updateFusedHardwareAvailability():void");
    }

    public void setGpsHardwareGeofence(IGpsGeofenceHardware iGpsGeofenceHardware) {
        if (this.mGpsService == null) {
            this.mGpsService = iGpsGeofenceHardware;
            updateGpsHardwareAvailability();
        } else if (iGpsGeofenceHardware != null) {
            Log.e(TAG, "Error: GpsService being set again.");
        } else {
            this.mGpsService = null;
            Log.w(TAG, "GPS Geofence Hardware service seems to have crashed");
        }
    }

    public void onCapabilities(int i) {
        this.mCapabilities = i;
        updateFusedHardwareAvailability();
    }

    public void setVersion(int i) {
        this.mVersion = i;
        updateFusedHardwareAvailability();
    }

    public void setFusedGeofenceHardware(IFusedGeofenceHardware iFusedGeofenceHardware) {
        if (this.mFusedService == null) {
            this.mFusedService = iFusedGeofenceHardware;
            updateFusedHardwareAvailability();
        } else if (iFusedGeofenceHardware != null) {
            Log.e(TAG, "Error: FusedService being set again");
        } else {
            this.mFusedService = null;
            Log.w(TAG, "Fused Geofence Hardware service seems to have crashed");
        }
    }

    public int[] getMonitoringTypes() {
        boolean z;
        boolean z2;
        synchronized (this.mSupportedMonitorTypes) {
            int[] iArr = this.mSupportedMonitorTypes;
            z = iArr[0] != 2;
            z2 = iArr[1] != 2;
        }
        if (z) {
            if (z2) {
                return new int[]{0, 1};
            }
            return new int[]{0};
        }
        if (z2) {
            return new int[]{1};
        }
        return new int[0];
    }

    public int getStatusOfMonitoringType(int i) {
        int i2;
        synchronized (this.mSupportedMonitorTypes) {
            int[] iArr = this.mSupportedMonitorTypes;
            if (i >= iArr.length || i < 0) {
                throw new IllegalArgumentException("Unknown monitoring type");
            }
            i2 = iArr[i];
        }
        return i2;
    }

    public int getCapabilitiesForMonitoringType(int i) {
        if (this.mSupportedMonitorTypes[i] != 0) {
            return 0;
        }
        if (i != 0) {
            if (i != 1) {
                return 0;
            }
            if (this.mVersion >= 2) {
                return this.mCapabilities;
            }
        }
        return 1;
    }

    public boolean addCircularFence(int i, GeofenceHardwareRequestParcelable geofenceHardwareRequestParcelable, IGeofenceHardwareCallback iGeofenceHardwareCallback) {
        int id = geofenceHardwareRequestParcelable.getId();
        if (DEBUG) {
            Log.d(TAG, String.format("addCircularFence: monitoringType=%d, %s", Integer.valueOf(i), geofenceHardwareRequestParcelable));
        }
        synchronized (this.mGeofences) {
            this.mGeofences.put(id, iGeofenceHardwareCallback);
        }
        boolean z = false;
        if (i == 0) {
            IGpsGeofenceHardware iGpsGeofenceHardware = this.mGpsService;
            if (iGpsGeofenceHardware == null) {
                return false;
            }
            try {
                z = iGpsGeofenceHardware.addCircularHardwareGeofence(geofenceHardwareRequestParcelable.getId(), geofenceHardwareRequestParcelable.getLatitude(), geofenceHardwareRequestParcelable.getLongitude(), geofenceHardwareRequestParcelable.getRadius(), geofenceHardwareRequestParcelable.getLastTransition(), geofenceHardwareRequestParcelable.getMonitorTransitions(), geofenceHardwareRequestParcelable.getNotificationResponsiveness(), geofenceHardwareRequestParcelable.getUnknownTimer());
            } catch (RemoteException unused) {
                Log.e(TAG, "AddGeofence: Remote Exception calling LocationManagerService");
            }
        } else if (i == 1) {
            IFusedGeofenceHardware iFusedGeofenceHardware = this.mFusedService;
            if (iFusedGeofenceHardware == null) {
                return false;
            }
            try {
                iFusedGeofenceHardware.addGeofences(new GeofenceHardwareRequestParcelable[]{geofenceHardwareRequestParcelable});
                z = true;
            } catch (RemoteException unused2) {
                Log.e(TAG, "AddGeofence: RemoteException calling LocationManagerService");
            }
        }
        if (z) {
            Message obtainMessage = this.mReaperHandler.obtainMessage(1, iGeofenceHardwareCallback);
            obtainMessage.arg1 = i;
            this.mReaperHandler.sendMessage(obtainMessage);
        } else {
            synchronized (this.mGeofences) {
                this.mGeofences.remove(id);
            }
        }
        if (DEBUG) {
            Log.d(TAG, "addCircularFence: Result is: " + z);
        }
        return z;
    }

    public boolean removeGeofence(int i, int i2) {
        if (DEBUG) {
            Log.d(TAG, "Remove Geofence: GeofenceId: " + i);
        }
        synchronized (this.mGeofences) {
            if (this.mGeofences.get(i) == null) {
                throw new IllegalArgumentException("Geofence " + i + " not registered.");
            }
        }
        boolean z = false;
        if (i2 == 0) {
            IGpsGeofenceHardware iGpsGeofenceHardware = this.mGpsService;
            if (iGpsGeofenceHardware == null) {
                return false;
            }
            try {
                z = iGpsGeofenceHardware.removeHardwareGeofence(i);
            } catch (RemoteException unused) {
                Log.e(TAG, "RemoveGeofence: Remote Exception calling LocationManagerService");
            }
        } else if (i2 == 1) {
            IFusedGeofenceHardware iFusedGeofenceHardware = this.mFusedService;
            if (iFusedGeofenceHardware == null) {
                return false;
            }
            try {
                iFusedGeofenceHardware.removeGeofences(new int[]{i});
                z = true;
            } catch (RemoteException unused2) {
                Log.e(TAG, "RemoveGeofence: RemoteException calling LocationManagerService");
            }
        }
        if (DEBUG) {
            Log.d(TAG, "removeGeofence: Result is: " + z);
        }
        return z;
    }

    public boolean pauseGeofence(int i, int i2) {
        if (DEBUG) {
            Log.d(TAG, "Pause Geofence: GeofenceId: " + i);
        }
        synchronized (this.mGeofences) {
            if (this.mGeofences.get(i) == null) {
                throw new IllegalArgumentException("Geofence " + i + " not registered.");
            }
        }
        boolean z = false;
        if (i2 == 0) {
            IGpsGeofenceHardware iGpsGeofenceHardware = this.mGpsService;
            if (iGpsGeofenceHardware == null) {
                return false;
            }
            try {
                z = iGpsGeofenceHardware.pauseHardwareGeofence(i);
            } catch (RemoteException unused) {
                Log.e(TAG, "PauseGeofence: Remote Exception calling LocationManagerService");
            }
        } else if (i2 == 1) {
            IFusedGeofenceHardware iFusedGeofenceHardware = this.mFusedService;
            if (iFusedGeofenceHardware == null) {
                return false;
            }
            try {
                iFusedGeofenceHardware.pauseMonitoringGeofence(i);
                z = true;
            } catch (RemoteException unused2) {
                Log.e(TAG, "PauseGeofence: RemoteException calling LocationManagerService");
            }
        }
        if (DEBUG) {
            Log.d(TAG, "pauseGeofence: Result is: " + z);
        }
        return z;
    }

    public boolean resumeGeofence(int i, int i2, int i3) {
        if (DEBUG) {
            Log.d(TAG, "Resume Geofence: GeofenceId: " + i);
        }
        synchronized (this.mGeofences) {
            if (this.mGeofences.get(i) == null) {
                throw new IllegalArgumentException("Geofence " + i + " not registered.");
            }
        }
        boolean z = false;
        if (i2 == 0) {
            IGpsGeofenceHardware iGpsGeofenceHardware = this.mGpsService;
            if (iGpsGeofenceHardware == null) {
                return false;
            }
            try {
                z = iGpsGeofenceHardware.resumeHardwareGeofence(i, i3);
            } catch (RemoteException unused) {
                Log.e(TAG, "ResumeGeofence: Remote Exception calling LocationManagerService");
            }
        } else if (i2 == 1) {
            IFusedGeofenceHardware iFusedGeofenceHardware = this.mFusedService;
            if (iFusedGeofenceHardware == null) {
                return false;
            }
            try {
                iFusedGeofenceHardware.resumeMonitoringGeofence(i, i3);
                z = true;
            } catch (RemoteException unused2) {
                Log.e(TAG, "ResumeGeofence: RemoteException calling LocationManagerService");
            }
        }
        if (DEBUG) {
            Log.d(TAG, "resumeGeofence: Result is: " + z);
        }
        return z;
    }

    public boolean registerForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
        Message obtainMessage = this.mReaperHandler.obtainMessage(2, iGeofenceHardwareMonitorCallback);
        obtainMessage.arg1 = i;
        this.mReaperHandler.sendMessage(obtainMessage);
        Message obtainMessage2 = this.mCallbacksHandler.obtainMessage(2, iGeofenceHardwareMonitorCallback);
        obtainMessage2.arg1 = i;
        this.mCallbacksHandler.sendMessage(obtainMessage2);
        return true;
    }

    public boolean unregisterForMonitorStateChangeCallback(int i, IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback) {
        Message obtainMessage = this.mCallbacksHandler.obtainMessage(3, iGeofenceHardwareMonitorCallback);
        obtainMessage.arg1 = i;
        this.mCallbacksHandler.sendMessage(obtainMessage);
        return true;
    }

    public void reportGeofenceTransition(int i, Location location, int i2, long j, int i3, int i4) {
        int i5;
        if (location == null) {
            Log.e(TAG, String.format("Invalid Geofence Transition: location=null", new Object[0]));
            return;
        }
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("GeofenceTransition| ");
            sb.append(location);
            sb.append(", transition:");
            sb.append(i2);
            sb.append(", transitionTimestamp:");
            sb.append(j);
            sb.append(", monitoringType:");
            sb.append(i3);
            sb.append(", sourcesUsed:");
            i5 = i4;
            sb.append(i5);
            Log.d(TAG, sb.toString());
        } else {
            i5 = i4;
        }
        GeofenceTransition geofenceTransition = new GeofenceTransition(this, i, i2, j, location, i3, i5);
        acquireWakeLock();
        this.mGeofenceHandler.obtainMessage(1, geofenceTransition).sendToTarget();
    }

    public void reportGeofenceMonitorStatus(int i, int i2, Location location, int i3) {
        setMonitorAvailability(i, i2);
        acquireWakeLock();
        this.mCallbacksHandler.obtainMessage(1, new GeofenceHardwareMonitorEvent(i, i2, i3, location)).sendToTarget();
    }

    private void reportGeofenceOperationStatus(int i, int i2, int i3) {
        acquireWakeLock();
        Message obtainMessage = this.mGeofenceHandler.obtainMessage(i);
        obtainMessage.arg1 = i2;
        obtainMessage.arg2 = i3;
        obtainMessage.sendToTarget();
    }

    public void reportGeofenceAddStatus(int i, int i2) {
        if (DEBUG) {
            Log.d(TAG, "AddCallback| id:" + i + ", status:" + i2);
        }
        reportGeofenceOperationStatus(2, i, i2);
    }

    public void reportGeofenceRemoveStatus(int i, int i2) {
        if (DEBUG) {
            Log.d(TAG, "RemoveCallback| id:" + i + ", status:" + i2);
        }
        reportGeofenceOperationStatus(3, i, i2);
    }

    public void reportGeofencePauseStatus(int i, int i2) {
        if (DEBUG) {
            Log.d(TAG, "PauseCallbac| id:" + i + ", status" + i2);
        }
        reportGeofenceOperationStatus(4, i, i2);
    }

    public void reportGeofenceResumeStatus(int i, int i2) {
        if (DEBUG) {
            Log.d(TAG, "ResumeCallback| id:" + i + ", status:" + i2);
        }
        reportGeofenceOperationStatus(5, i, i2);
    }

    private class GeofenceTransition {
        private int mGeofenceId;
        private Location mLocation;
        private int mMonitoringType;
        private int mSourcesUsed;
        private long mTimestamp;
        private int mTransition;

        GeofenceTransition(GeofenceHardwareImpl geofenceHardwareImpl, int i, int i2, long j, Location location, int i3, int i4) {
            this.mGeofenceId = i;
            this.mTransition = i2;
            this.mTimestamp = j;
            this.mLocation = location;
            this.mMonitoringType = i3;
            this.mSourcesUsed = i4;
        }
    }

    private void setMonitorAvailability(int i, int i2) {
        synchronized (this.mSupportedMonitorTypes) {
            this.mSupportedMonitorTypes[i] = i2;
        }
    }

    class Reaper implements IBinder.DeathRecipient {
        private IGeofenceHardwareCallback mCallback;
        private IGeofenceHardwareMonitorCallback mMonitorCallback;
        private int mMonitoringType;

        Reaper(IGeofenceHardwareCallback iGeofenceHardwareCallback, int i) {
            this.mCallback = iGeofenceHardwareCallback;
            this.mMonitoringType = i;
        }

        Reaper(IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback, int i) {
            this.mMonitorCallback = iGeofenceHardwareMonitorCallback;
            this.mMonitoringType = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (this.mCallback != null) {
                Message obtainMessage = GeofenceHardwareImpl.this.mGeofenceHandler.obtainMessage(6, this.mCallback);
                obtainMessage.arg1 = this.mMonitoringType;
                GeofenceHardwareImpl.this.mGeofenceHandler.sendMessage(obtainMessage);
            } else if (this.mMonitorCallback != null) {
                Message obtainMessage2 = GeofenceHardwareImpl.this.mCallbacksHandler.obtainMessage(4, this.mMonitorCallback);
                obtainMessage2.arg1 = this.mMonitoringType;
                GeofenceHardwareImpl.this.mCallbacksHandler.sendMessage(obtainMessage2);
            }
            GeofenceHardwareImpl.this.mReaperHandler.sendMessage(GeofenceHardwareImpl.this.mReaperHandler.obtainMessage(3, this));
        }

        public int hashCode() {
            IGeofenceHardwareCallback iGeofenceHardwareCallback = this.mCallback;
            int hashCode = (527 + (iGeofenceHardwareCallback != null ? iGeofenceHardwareCallback.asBinder().hashCode() : 0)) * 31;
            IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = this.mMonitorCallback;
            return ((hashCode + (iGeofenceHardwareMonitorCallback != null ? iGeofenceHardwareMonitorCallback.asBinder().hashCode() : 0)) * 31) + this.mMonitoringType;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            Reaper reaper = (Reaper) obj;
            return binderEquals(reaper.mCallback, this.mCallback) && binderEquals(reaper.mMonitorCallback, this.mMonitorCallback) && reaper.mMonitoringType == this.mMonitoringType;
        }

        private boolean binderEquals(IInterface iInterface, IInterface iInterface2) {
            return iInterface == null ? iInterface2 == null : iInterface2 != null && iInterface.asBinder() == iInterface2.asBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean unlinkToDeath() {
            IGeofenceHardwareMonitorCallback iGeofenceHardwareMonitorCallback = this.mMonitorCallback;
            if (iGeofenceHardwareMonitorCallback != null) {
                return iGeofenceHardwareMonitorCallback.asBinder().unlinkToDeath(this, 0);
            }
            IGeofenceHardwareCallback iGeofenceHardwareCallback = this.mCallback;
            if (iGeofenceHardwareCallback != null) {
                return iGeofenceHardwareCallback.asBinder().unlinkToDeath(this, 0);
            }
            return true;
        }

        private boolean callbackEquals(IGeofenceHardwareCallback iGeofenceHardwareCallback) {
            IGeofenceHardwareCallback iGeofenceHardwareCallback2 = this.mCallback;
            return iGeofenceHardwareCallback2 != null && iGeofenceHardwareCallback2.asBinder() == iGeofenceHardwareCallback.asBinder();
        }
    }

    int getAllowedResolutionLevel(int i, int i2) {
        if (this.mContext.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, i, i2) == 0) {
            return 3;
        }
        return this.mContext.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, i, i2) == 0 ? 2 : 1;
    }
}
