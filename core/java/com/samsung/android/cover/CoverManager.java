package com.samsung.android.cover;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import android.view.Window;
import android.view.WindowManager;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.sepunion.UnionConstants;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes6.dex */
public class CoverManager {
    public static final int COVER_MODE_HIDE_SVIEW_ONCE = 2;
    public static final int COVER_MODE_NONE = 0;
    public static final int COVER_MODE_SVIEW = 1;
    private static final String FEATURE_COVER = "com.sec.feature.cover";
    private static final String FEATURE_COVER_CLEAR = "com.sec.feature.cover.clearcover";
    private static final String FEATURE_COVER_CLEAR_CAMERA_VIEW = "com.sec.feature.cover.clearcameraviewcover";
    private static final String FEATURE_COVER_CLEAR_SIDE_VIEW = "com.sec.feature.cover.clearsideviewcover";
    private static final String FEATURE_COVER_FLIP = "com.sec.feature.cover.flip";
    private static final String FEATURE_COVER_LED_BACK = "com.sec.feature.cover.ledbackcover";
    private static final String FEATURE_COVER_MINI_SVIEW_WALLET = "com.sec.feature.cover.minisviewwalletcover";
    private static final String FEATURE_COVER_NEON = "com.sec.feature.cover.neoncover";
    private static final String FEATURE_COVER_NFCLED = "com.sec.feature.cover.nfcledcover";
    private static final String FEATURE_COVER_SVIEW = "com.sec.feature.cover.sview";
    private static final String TAG = "CoverManager";
    private Context mContext;
    private ICoverManager mService;
    private static boolean sIsSystemFeatureQueried = false;
    private static boolean sIsCoverSystemFeatureEnabled = false;
    private static boolean sIsFilpCoverSystemFeatureEnabled = false;
    private static boolean sIsSViewCoverSystemFeatureEnabled = false;
    private static boolean sIsClearCoverSystemFeatureEnabled = false;
    private static boolean sIsNfcLedCoverSystemFeatureEnabled = false;
    private static boolean sIsNeonCoverSystemFeatureEnabled = false;
    private static boolean sIsClearSideViewCoverSystemFeatureEnabled = false;
    private static boolean sIsLEDBackCoverSystemFeatureEnabled = false;
    private static boolean sIsMiniSviewWalletCoverFeatureEnabled = false;
    private static boolean sIsClearCameraViewCoverSystemFeatureEnabled = false;
    private final CopyOnWriteArrayList<CoverListenerDelegate> mListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<CoverStateListenerDelegate> mCoverStateListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<NfcLedCoverTouchListenerDelegate> mNfcLedCoverTouchListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<LedSystemEventListenerDelegate> mLedSystemEventListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<CoverPowerKeyListenerDelegate> mCoverPowerKeyListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<CoverListenerDelegate> mLcdOffDisableDelegates = new CopyOnWriteArrayList<>();
    private final IBinder mToken = new Binder();

    public CoverManager(Context context) {
        this.mContext = context;
        initSystemFeature();
    }

    public CoverManager(Context context, ICoverManager service) {
        this.mContext = context;
        this.mService = service;
    }

    private void initSystemFeature() {
        if (!sIsSystemFeatureQueried) {
            PackageManager pm = this.mContext.getPackageManager();
            sIsCoverSystemFeatureEnabled = pm.hasSystemFeature("com.sec.feature.cover");
            sIsFilpCoverSystemFeatureEnabled = pm.hasSystemFeature("com.sec.feature.cover.flip");
            sIsSViewCoverSystemFeatureEnabled = pm.hasSystemFeature("com.sec.feature.cover.sview");
            sIsNfcLedCoverSystemFeatureEnabled = pm.hasSystemFeature("com.sec.feature.cover.nfcledcover");
            sIsClearCoverSystemFeatureEnabled = pm.hasSystemFeature("com.sec.feature.cover.clearcover");
            sIsNeonCoverSystemFeatureEnabled = pm.hasSystemFeature(FEATURE_COVER_NEON);
            sIsClearSideViewCoverSystemFeatureEnabled = pm.hasSystemFeature(FEATURE_COVER_CLEAR_SIDE_VIEW);
            sIsLEDBackCoverSystemFeatureEnabled = pm.hasSystemFeature(FEATURE_COVER_LED_BACK);
            sIsMiniSviewWalletCoverFeatureEnabled = pm.hasSystemFeature(FEATURE_COVER_MINI_SVIEW_WALLET);
            sIsClearCameraViewCoverSystemFeatureEnabled = pm.hasSystemFeature(FEATURE_COVER_CLEAR_CAMERA_VIEW);
            sIsSystemFeatureQueried = true;
        }
    }

    boolean isSupportCover() {
        return sIsCoverSystemFeatureEnabled;
    }

    boolean isSupportFlipCover() {
        return sIsFilpCoverSystemFeatureEnabled;
    }

    boolean isSupportSViewCover() {
        return sIsSViewCoverSystemFeatureEnabled;
    }

    boolean isSupportClearCover() {
        return sIsClearCoverSystemFeatureEnabled;
    }

    boolean isSupportNfcLedCover() {
        return sIsNfcLedCoverSystemFeatureEnabled;
    }

    boolean isSupportNeonCover() {
        return sIsNeonCoverSystemFeatureEnabled;
    }

    boolean isSupportClearSideViewCover() {
        return sIsClearSideViewCoverSystemFeatureEnabled;
    }

    boolean isSupportLEDBackCover() {
        return sIsLEDBackCoverSystemFeatureEnabled;
    }

    boolean isSupportMiniSviewWalletCover() {
        return sIsMiniSviewWalletCoverFeatureEnabled;
    }

    boolean isSupportClearCameraViewCover() {
        return sIsClearCameraViewCoverSystemFeatureEnabled;
    }

    boolean isSupportTypeOfCover(int type) {
        switch (type) {
            case 0:
                return sIsFilpCoverSystemFeatureEnabled;
            case 1:
            case 3:
                return sIsSViewCoverSystemFeatureEnabled;
            case 2:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 12:
            case 13:
            default:
                return false;
            case 7:
                return sIsNfcLedCoverSystemFeatureEnabled;
            case 8:
                return sIsClearCoverSystemFeatureEnabled;
            case 11:
                return sIsNeonCoverSystemFeatureEnabled;
            case 14:
                return sIsLEDBackCoverSystemFeatureEnabled;
            case 15:
                return sIsClearSideViewCoverSystemFeatureEnabled;
            case 16:
                return sIsMiniSviewWalletCoverFeatureEnabled;
            case 17:
                return sIsClearCameraViewCoverSystemFeatureEnabled;
        }
    }

    private synchronized ICoverManager getService() {
        if (this.mService == null) {
            this.mService = ICoverManager.Stub.asInterface(ServiceManager.getService(UnionConstants.SERVICE_COVER));
            if (this.mService == null) {
                Slog.w(TAG, "warning: no COVER_MANAGER_SERVICE");
            }
        }
        return this.mService;
    }

    public void setCoverModeToWindow(Window window, int coverMode) {
        if (!isSupportSViewCover()) {
            Log.w(TAG, "setSViewCoverModeToWindow : This device is not supported s view cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        WindowManager.LayoutParams wlp = window.getAttributes();
        if (wlp != null) {
            wlp.coverMode = coverMode;
            window.setAttributes(wlp);
        }
    }

    public void registerListener(StateListener listener) {
        Log.d(TAG, "registerListener");
        if (!isSupportCover()) {
            Log.w(TAG, "registerListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (listener == null) {
            Log.w(TAG, "registerListener : listener is null");
            return;
        }
        CoverListenerDelegate coverListener = null;
        boolean hasDelegate = false;
        Iterator<CoverListenerDelegate> i = this.mListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                coverListener = delegate;
                hasDelegate = true;
                break;
            }
        }
        if (coverListener == null) {
            coverListener = new CoverListenerDelegate(listener, null, this.mContext);
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (coverListener != null) {
                    svc.registerCallback(coverListener, cm);
                    if (!hasDelegate) {
                        this.mListenerDelegates.add(coverListener);
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
        }
    }

    public void registerListener(CoverStateListener listener) {
        Log.d(TAG, "registerListener");
        if (!isSupportCover()) {
            Log.w(TAG, "registerListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (listener == null) {
            Log.w(TAG, "registerListener : listener is null");
            return;
        }
        CoverStateListenerDelegate coverListener = null;
        boolean hasDelegate = false;
        Iterator<CoverStateListenerDelegate> i = this.mCoverStateListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverStateListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                coverListener = delegate;
                hasDelegate = true;
                break;
            }
        }
        if (coverListener == null) {
            coverListener = new CoverStateListenerDelegate(listener, null, this.mContext);
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (coverListener != null) {
                    svc.registerListenerCallback(coverListener, cm, 2);
                    if (!hasDelegate) {
                        this.mCoverStateListenerDelegates.add(coverListener);
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
        }
    }

    public void unregisterListener(StateListener listener) {
        Log.d(TAG, "unregisterListener");
        if (!isSupportCover()) {
            Log.w(TAG, "unregisterListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (listener == null) {
            Log.w(TAG, "unregisterListener : listener is null");
            return;
        }
        CoverListenerDelegate coverListener = null;
        Iterator<CoverListenerDelegate> i = this.mListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                coverListener = delegate;
                break;
            }
        }
        if (coverListener == null) {
            return;
        }
        try {
            ICoverManager svc = getService();
            if (svc != null && svc.unregisterCallback(coverListener)) {
                this.mListenerDelegates.remove(coverListener);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterListener: ", e);
        }
    }

    public void unregisterListener(CoverStateListener listener) {
        Log.d(TAG, "unregisterListener");
        if (!isSupportCover()) {
            Log.w(TAG, "unregisterListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (listener == null) {
            Log.w(TAG, "unregisterListener : listener is null");
            return;
        }
        CoverStateListenerDelegate coverListener = null;
        Iterator<CoverStateListenerDelegate> i = this.mCoverStateListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverStateListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                coverListener = delegate;
                break;
            }
        }
        if (coverListener == null) {
            return;
        }
        try {
            ICoverManager svc = getService();
            if (svc != null && svc.unregisterCallback(coverListener)) {
                this.mCoverStateListenerDelegates.remove(coverListener);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterListener: ", e);
        }
    }

    public CoverState getCoverState() {
        if (!isSupportCover()) {
            Log.w(TAG, "getCoverState : This device is not supported cover");
            return null;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                CoverState coverState = svc.getCoverState();
                if (coverState != null) {
                    return coverState;
                }
                Log.e(TAG, "getCoverState : coverState is null");
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getCoverState: ", e);
        }
        return null;
    }

    public void sendDataToCover(int command, byte[] data) {
        ICoverManager svc = getService();
        if (svc != null) {
            try {
                svc.sendDataToCover(command, data);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in sendData : ", e);
            }
        }
    }

    public void sendDataToNfcLedCover(int command, byte[] data) {
        ICoverManager svc = getService();
        if (svc != null) {
            try {
                svc.sendDataToNfcLedCover(command, data);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in sendData to NFC : ", e);
            }
        }
    }

    public void sendPowerKeyToCover() {
        ICoverManager svc = getService();
        if (svc != null) {
            try {
                svc.sendPowerKeyToCover();
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in sendPowerKeyToCover() : ", e);
            }
        }
    }

    public boolean isCoverManagerDisabled() {
        try {
            ICoverManager svc = getService();
            if (svc == null) {
                return false;
            }
            boolean disabled = svc.isCoverManagerDisabled();
            return disabled;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in isCoverManagerDisabled : ", e);
            return false;
        }
    }

    public void disableCoverManager(boolean disable) {
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                svc.disableCoverManager(disable, this.mToken, this.mContext.getPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in disalbeCoverManager : ", e);
        }
    }

    public static class StateListener {
        public void onCoverStateChanged(CoverState state) {
        }
    }

    public static class CoverStateListener {
        public void onCoverSwitchStateChanged(boolean switchState) {
        }

        public void onCoverAttachStateChanged(boolean attached) {
        }
    }

    public void registerNfcTouchListener(int type, NfcLedCoverTouchListener listener) {
        Log.d(TAG, "registerNfcTouchListener");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "registerNfcTouchListener : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (listener == null) {
            Log.w(TAG, "registerNfcTouchListener : listener is null");
            return;
        }
        NfcLedCoverTouchListenerDelegate nfcTouchListener = null;
        boolean hasDelegate = false;
        Iterator<NfcLedCoverTouchListenerDelegate> i = this.mNfcLedCoverTouchListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            NfcLedCoverTouchListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                nfcTouchListener = delegate;
                hasDelegate = true;
                break;
            }
        }
        if (nfcTouchListener == null) {
            nfcTouchListener = new NfcLedCoverTouchListenerDelegate(listener, null, this.mContext);
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (nfcTouchListener != null) {
                    svc.registerNfcTouchListenerCallback(type, nfcTouchListener, cm);
                    if (!hasDelegate) {
                        this.mNfcLedCoverTouchListenerDelegates.add(nfcTouchListener);
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerNfcTouchListener: ", e);
        }
    }

    public void unregisterNfcTouchListener(NfcLedCoverTouchListener listener) {
        Log.d(TAG, "unregisterNfcTouchListener");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "unregisterNfcTouchListener : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (listener == null) {
            Log.w(TAG, "unregisterNfcTouchListener : listener is null");
            return;
        }
        NfcLedCoverTouchListenerDelegate nfcTouchListener = null;
        Iterator<NfcLedCoverTouchListenerDelegate> i = this.mNfcLedCoverTouchListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            NfcLedCoverTouchListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                nfcTouchListener = delegate;
                break;
            }
        }
        if (nfcTouchListener == null) {
            return;
        }
        try {
            ICoverManager svc = getService();
            if (svc != null && svc.unregisterNfcTouchListenerCallback(nfcTouchListener)) {
                this.mNfcLedCoverTouchListenerDelegates.remove(nfcTouchListener);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterNfcTouchListener: ", e);
        }
    }

    public void addLedNotification(Bundle data) {
        Log.d(TAG, "addLedNotification");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "addLedNotification : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (data == null) {
            Log.e(TAG, "addLedNotification : Null notification data!");
            return;
        }
        ICoverManager svc = getService();
        if (svc != null) {
            try {
                svc.addLedNotification(data);
            } catch (RemoteException e) {
                Log.e(TAG, "addLedNotification in sendData to NFC : ", e);
            }
        }
    }

    public void removeLedNotification(Bundle data) {
        Log.d(TAG, "removeLedNotification");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "removeLedNotification : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (data == null) {
            Log.e(TAG, "removeLedNotification : Null notification data!");
            return;
        }
        ICoverManager svc = getService();
        if (svc != null) {
            try {
                svc.removeLedNotification(data);
            } catch (RemoteException e) {
                Log.e(TAG, "removeLedNotification in sendData to NFC : ", e);
            }
        }
    }

    public void sendSystemEvent(Bundle data) {
        if (!isSupportCover()) {
            Log.w(TAG, "sendSystemEvent : This device does not support cover");
            return;
        }
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "sendSystemEvent : This device does not support NFC Led cover");
            return;
        }
        if (data == null) {
            Log.e(TAG, "sendSystemEvent : Null system event data!");
            return;
        }
        ICoverManager svc = getService();
        if (svc != null) {
            try {
                svc.sendSystemEvent(data);
            } catch (RemoteException e) {
                Log.e(TAG, "sendSystemEvent in sendData to NFC : ", e);
            }
        }
    }

    public static class NfcLedCoverTouchListener {
        public void onCoverTouchAccept() {
        }

        public void onCoverTouchReject() {
        }
    }

    public void registerLedSystemListener(LedSystemEventListener listener) {
        if (!isSupportCover()) {
            Log.w(TAG, "registerLedSystemListener : This device does not support cover");
            return;
        }
        Log.d(TAG, "registerLedSystemListener");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "registerLedSystemListener : This device does not support NFC Led cover");
            return;
        }
        if (listener == null) {
            Log.w(TAG, "registerLedSystemListener : listener is null");
            return;
        }
        LedSystemEventListenerDelegate ledSystemEventListener = null;
        boolean hasDelegate = false;
        Iterator<LedSystemEventListenerDelegate> i = this.mLedSystemEventListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            LedSystemEventListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                ledSystemEventListener = delegate;
                hasDelegate = true;
                break;
            }
        }
        if (ledSystemEventListener == null) {
            ledSystemEventListener = new LedSystemEventListenerDelegate(listener, null, this.mContext);
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (ledSystemEventListener != null) {
                    svc.registerNfcTouchListenerCallback(4, ledSystemEventListener, cm);
                    if (!hasDelegate) {
                        this.mLedSystemEventListenerDelegates.add(ledSystemEventListener);
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerLedSystemListener: ", e);
        }
    }

    public void unregisterLedSystemEventListener(LedSystemEventListener listener) {
        Log.d(TAG, "unregisterLedSystemEventListener");
        if (!isSupportCover()) {
            Log.w(TAG, "unregisterLedSystemEventListener : This device does not support cover");
            return;
        }
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "unregisterLedSystemEventListener : This device does not support NFC Led cover");
            return;
        }
        if (listener == null) {
            Log.w(TAG, "unregisterLedSystemEventListener : listener is null");
            return;
        }
        LedSystemEventListenerDelegate ledSystemEventListener = null;
        Iterator<LedSystemEventListenerDelegate> i = this.mLedSystemEventListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            LedSystemEventListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                ledSystemEventListener = delegate;
                break;
            }
        }
        if (ledSystemEventListener == null) {
            return;
        }
        try {
            ICoverManager svc = getService();
            if (svc != null && svc.unregisterNfcTouchListenerCallback(ledSystemEventListener)) {
                this.mLedSystemEventListenerDelegates.remove(ledSystemEventListener);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterLedSystemEventListener: ", e);
        }
    }

    public static class LedSystemEventListener {
        private static final int EVENT_TYPE_SYSTEM = 4;

        public void onSystemCoverEvent(int event, Bundle args) {
        }
    }

    public void registerCoverPowerKeyListener(CoverPowerKeyListener listener) {
        if (!isSupportCover()) {
            Log.w(TAG, "registerCoverPowerKeyListener : This device does not support cover");
            return;
        }
        Log.d(TAG, "registerCoverPowerKeyListener");
        if (!isSupportFlipCover()) {
            Log.w(TAG, "registerLedSystemListener : This device does not support Flip cover");
            return;
        }
        if (listener == null) {
            Log.w(TAG, "registerCoverPowerKeyListener : listener is null");
            return;
        }
        CoverPowerKeyListenerDelegate powerKeyEventListener = null;
        boolean hasDelegate = false;
        Iterator<CoverPowerKeyListenerDelegate> i = this.mCoverPowerKeyListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverPowerKeyListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                powerKeyEventListener = delegate;
                hasDelegate = true;
                break;
            }
        }
        if (powerKeyEventListener == null) {
            powerKeyEventListener = new CoverPowerKeyListenerDelegate(listener, null, this.mContext);
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (powerKeyEventListener != null) {
                    svc.registerNfcTouchListenerCallback(10, powerKeyEventListener, cm);
                    if (!hasDelegate) {
                        this.mCoverPowerKeyListenerDelegates.add(powerKeyEventListener);
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerCoverPowerKeyListener: ", e);
        }
    }

    public void unregisterCoverPowerKeyListener(CoverPowerKeyListener listener) {
        Log.d(TAG, "unregisterCoverPowerKeyListener");
        if (!isSupportCover()) {
            Log.w(TAG, "unregisterCoverPowerKeyListener : This device does not support cover");
            return;
        }
        Log.d(TAG, "unregisterCoverPowerKeyListener");
        if (!isSupportFlipCover()) {
            Log.w(TAG, "unregisterCoverPowerKeyListener : This device does not support Flip Cover");
            return;
        }
        if (listener == null) {
            Log.w(TAG, "unregisterCoverPowerKeyListener : listener is null");
            return;
        }
        CoverPowerKeyListenerDelegate powerKeyEventListener = null;
        Iterator<CoverPowerKeyListenerDelegate> i = this.mCoverPowerKeyListenerDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverPowerKeyListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                powerKeyEventListener = delegate;
                break;
            }
        }
        if (powerKeyEventListener == null) {
            return;
        }
        try {
            ICoverManager svc = getService();
            if (svc != null && svc.unregisterNfcTouchListenerCallback(powerKeyEventListener)) {
                this.mCoverPowerKeyListenerDelegates.remove(powerKeyEventListener);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterCoverPowerKeyListener: ", e);
        }
    }

    public static class CoverPowerKeyListener {
        private static final int EVENT_TYPE_POWER_KEY = 10;

        public void onPowerKeyPress() {
        }
    }

    public boolean disableLcdOffByCover(StateListener listener) {
        if (!isSupportCover()) {
            Log.w(TAG, "disableLcdOffByCover : This device does not support cover");
            return false;
        }
        if (listener == null) {
            Log.w(TAG, "disableLcdOffByCover : listener cannot be null");
            return false;
        }
        Log.d(TAG, "disableLcdOffByCover");
        CoverListenerDelegate coverListener = null;
        Iterator<CoverListenerDelegate> i = this.mLcdOffDisableDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                coverListener = delegate;
                break;
            }
        }
        if (coverListener == null) {
            coverListener = new CoverListenerDelegate(listener, null, this.mContext);
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (svc.disableLcdOffByCover(coverListener, cm)) {
                    this.mLcdOffDisableDelegates.add(coverListener);
                    return true;
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterNfcTouchListener: ", e);
        }
        return false;
    }

    public boolean enableLcdOffByCover(StateListener listener) {
        if (!isSupportCover()) {
            Log.w(TAG, "enableLcdOffByCover : This device does not support cover");
            return false;
        }
        if (listener == null) {
            Log.w(TAG, "enableLcdOffByCover : listener cannot be null");
            return false;
        }
        Log.d(TAG, "enableLcdOffByCover");
        CoverListenerDelegate coverListener = null;
        Iterator<CoverListenerDelegate> i = this.mLcdOffDisableDelegates.iterator();
        while (true) {
            if (!i.hasNext()) {
                break;
            }
            CoverListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                coverListener = delegate;
                break;
            }
        }
        if (coverListener == null) {
            Log.e(TAG, "enableLcdOffByCover: Matching listener not found, cannot enable");
            return false;
        }
        try {
            ICoverManager svc = getService();
            if (svc != null) {
                ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (svc.enableLcdOffByCover(coverListener, cm)) {
                    this.mLcdOffDisableDelegates.remove(coverListener);
                    return true;
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterNfcTouchListener: ", e);
        }
        return false;
    }
}
