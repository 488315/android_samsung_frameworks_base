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
    private static boolean sIsClearCameraViewCoverSystemFeatureEnabled = false;
    private static boolean sIsClearCoverSystemFeatureEnabled = false;
    private static boolean sIsClearSideViewCoverSystemFeatureEnabled = false;
    private static boolean sIsCoverSystemFeatureEnabled = false;
    private static boolean sIsFilpCoverSystemFeatureEnabled = false;
    private static boolean sIsLEDBackCoverSystemFeatureEnabled = false;
    private static boolean sIsMiniSviewWalletCoverFeatureEnabled = false;
    private static boolean sIsNeonCoverSystemFeatureEnabled = false;
    private static boolean sIsNfcLedCoverSystemFeatureEnabled = false;
    private static boolean sIsSViewCoverSystemFeatureEnabled = false;
    private static boolean sIsSystemFeatureQueried = false;
    private Context mContext;
    private ICoverManager mService;
    private final CopyOnWriteArrayList<CoverListenerDelegate> mListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<CoverStateListenerDelegate> mCoverStateListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<NfcLedCoverTouchListenerDelegate> mNfcLedCoverTouchListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<LedSystemEventListenerDelegate> mLedSystemEventListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<CoverPowerKeyListenerDelegate> mCoverPowerKeyListenerDelegates = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<CoverListenerDelegate> mLcdOffDisableDelegates = new CopyOnWriteArrayList<>();
    private final IBinder mToken = new Binder();

    public static class CoverPowerKeyListener {
        private static final int EVENT_TYPE_POWER_KEY = 10;

        public void onPowerKeyPress() {
        }
    }

    public static class CoverStateListener {
        public void onCoverAttachStateChanged(boolean z) {
        }

        public void onCoverSwitchStateChanged(boolean z) {
        }
    }

    public static class LedSystemEventListener {
        private static final int EVENT_TYPE_SYSTEM = 4;

        public void onSystemCoverEvent(int i, Bundle bundle) {
        }
    }

    public static class NfcLedCoverTouchListener {
        public void onCoverTouchAccept() {
        }

        public void onCoverTouchReject() {
        }
    }

    public static class StateListener {
        public void onCoverStateChanged(CoverState coverState) {
        }
    }

    public CoverManager(Context context) {
        this.mContext = context;
        initSystemFeature();
    }

    public CoverManager(Context context, ICoverManager iCoverManager) {
        this.mContext = context;
        this.mService = iCoverManager;
    }

    private void initSystemFeature() {
        if (sIsSystemFeatureQueried) {
            return;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        sIsCoverSystemFeatureEnabled = packageManager.hasSystemFeature("com.sec.feature.cover");
        sIsFilpCoverSystemFeatureEnabled = packageManager.hasSystemFeature("com.sec.feature.cover.flip");
        sIsSViewCoverSystemFeatureEnabled = packageManager.hasSystemFeature("com.sec.feature.cover.sview");
        sIsNfcLedCoverSystemFeatureEnabled = packageManager.hasSystemFeature("com.sec.feature.cover.nfcledcover");
        sIsClearCoverSystemFeatureEnabled = packageManager.hasSystemFeature("com.sec.feature.cover.clearcover");
        sIsNeonCoverSystemFeatureEnabled = packageManager.hasSystemFeature(FEATURE_COVER_NEON);
        sIsClearSideViewCoverSystemFeatureEnabled = packageManager.hasSystemFeature(FEATURE_COVER_CLEAR_SIDE_VIEW);
        sIsLEDBackCoverSystemFeatureEnabled = packageManager.hasSystemFeature(FEATURE_COVER_LED_BACK);
        sIsMiniSviewWalletCoverFeatureEnabled = packageManager.hasSystemFeature(FEATURE_COVER_MINI_SVIEW_WALLET);
        sIsClearCameraViewCoverSystemFeatureEnabled = packageManager.hasSystemFeature(FEATURE_COVER_CLEAR_CAMERA_VIEW);
        sIsSystemFeatureQueried = true;
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

    boolean isSupportTypeOfCover(int i) {
        if (i == 0) {
            return sIsFilpCoverSystemFeatureEnabled;
        }
        if (i == 1 || i == 3) {
            return sIsSViewCoverSystemFeatureEnabled;
        }
        if (i == 11) {
            return sIsNeonCoverSystemFeatureEnabled;
        }
        if (i == 7) {
            return sIsNfcLedCoverSystemFeatureEnabled;
        }
        if (i == 8) {
            return sIsClearCoverSystemFeatureEnabled;
        }
        switch (i) {
            case 14:
                return sIsLEDBackCoverSystemFeatureEnabled;
            case 15:
                return sIsClearSideViewCoverSystemFeatureEnabled;
            case 16:
                return sIsMiniSviewWalletCoverFeatureEnabled;
            case 17:
                return sIsClearCameraViewCoverSystemFeatureEnabled;
            default:
                return false;
        }
    }

    private synchronized ICoverManager getService() {
        if (this.mService == null) {
            ICoverManager asInterface = ICoverManager.Stub.asInterface(ServiceManager.getService(UnionConstants.SERVICE_COVER));
            this.mService = asInterface;
            if (asInterface == null) {
                Slog.w(TAG, "warning: no COVER_MANAGER_SERVICE");
            }
        }
        return this.mService;
    }

    public void setCoverModeToWindow(Window window, int i) {
        if (!isSupportSViewCover()) {
            Log.w(TAG, "setSViewCoverModeToWindow : This device is not supported s view cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            attributes.coverMode = i;
            window.setAttributes(attributes);
        }
    }

    public void registerListener(StateListener stateListener) {
        boolean z;
        CoverListenerDelegate coverListenerDelegate;
        Log.d(TAG, "registerListener");
        if (!isSupportCover()) {
            Log.w(TAG, "registerListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (stateListener == null) {
            Log.w(TAG, "registerListener : listener is null");
            return;
        }
        Iterator<CoverListenerDelegate> it = this.mListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                coverListenerDelegate = null;
                break;
            } else {
                coverListenerDelegate = it.next();
                if (coverListenerDelegate.getListener().equals(stateListener)) {
                    z = true;
                    break;
                }
            }
        }
        if (coverListenerDelegate == null) {
            coverListenerDelegate = new CoverListenerDelegate(stateListener, null, this.mContext);
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (coverListenerDelegate != null) {
                    service.registerCallback(coverListenerDelegate, componentName);
                    if (z) {
                        return;
                    }
                    this.mListenerDelegates.add(coverListenerDelegate);
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
        }
    }

    public void registerListener(CoverStateListener coverStateListener) {
        boolean z;
        CoverStateListenerDelegate coverStateListenerDelegate;
        Log.d(TAG, "registerListener");
        if (!isSupportCover()) {
            Log.w(TAG, "registerListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (coverStateListener == null) {
            Log.w(TAG, "registerListener : listener is null");
            return;
        }
        Iterator<CoverStateListenerDelegate> it = this.mCoverStateListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                coverStateListenerDelegate = null;
                break;
            } else {
                coverStateListenerDelegate = it.next();
                if (coverStateListenerDelegate.getListener().equals(coverStateListener)) {
                    z = true;
                    break;
                }
            }
        }
        if (coverStateListenerDelegate == null) {
            coverStateListenerDelegate = new CoverStateListenerDelegate(coverStateListener, null, this.mContext);
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (coverStateListenerDelegate != null) {
                    service.registerListenerCallback(coverStateListenerDelegate, componentName, 2);
                    if (z) {
                        return;
                    }
                    this.mCoverStateListenerDelegates.add(coverStateListenerDelegate);
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
        }
    }

    public void unregisterListener(StateListener stateListener) {
        CoverListenerDelegate coverListenerDelegate;
        Log.d(TAG, "unregisterListener");
        if (!isSupportCover()) {
            Log.w(TAG, "unregisterListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (stateListener == null) {
            Log.w(TAG, "unregisterListener : listener is null");
            return;
        }
        Iterator<CoverListenerDelegate> it = this.mListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                coverListenerDelegate = null;
                break;
            } else {
                coverListenerDelegate = it.next();
                if (coverListenerDelegate.getListener().equals(stateListener)) {
                    break;
                }
            }
        }
        if (coverListenerDelegate == null) {
            return;
        }
        try {
            ICoverManager service = getService();
            if (service == null || !service.unregisterCallback(coverListenerDelegate)) {
                return;
            }
            this.mListenerDelegates.remove(coverListenerDelegate);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterListener: ", e);
        }
    }

    public void unregisterListener(CoverStateListener coverStateListener) {
        CoverStateListenerDelegate coverStateListenerDelegate;
        Log.d(TAG, "unregisterListener");
        if (!isSupportCover()) {
            Log.w(TAG, "unregisterListener : This device is not supported cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (coverStateListener == null) {
            Log.w(TAG, "unregisterListener : listener is null");
            return;
        }
        Iterator<CoverStateListenerDelegate> it = this.mCoverStateListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                coverStateListenerDelegate = null;
                break;
            } else {
                coverStateListenerDelegate = it.next();
                if (coverStateListenerDelegate.getListener().equals(coverStateListener)) {
                    break;
                }
            }
        }
        if (coverStateListenerDelegate == null) {
            return;
        }
        try {
            ICoverManager service = getService();
            if (service == null || !service.unregisterCallback(coverStateListenerDelegate)) {
                return;
            }
            this.mCoverStateListenerDelegates.remove(coverStateListenerDelegate);
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
            ICoverManager service = getService();
            if (service != null) {
                CoverState coverState = service.getCoverState();
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

    public void sendDataToCover(int i, byte[] bArr) {
        ICoverManager service = getService();
        if (service != null) {
            try {
                service.sendDataToCover(i, bArr);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in sendData : ", e);
            }
        }
    }

    public void sendDataToNfcLedCover(int i, byte[] bArr) {
        ICoverManager service = getService();
        if (service != null) {
            try {
                service.sendDataToNfcLedCover(i, bArr);
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in sendData to NFC : ", e);
            }
        }
    }

    public void sendPowerKeyToCover() {
        ICoverManager service = getService();
        if (service != null) {
            try {
                service.sendPowerKeyToCover();
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in sendPowerKeyToCover() : ", e);
            }
        }
    }

    public boolean isCoverManagerDisabled() {
        try {
            ICoverManager service = getService();
            if (service != null) {
                return service.isCoverManagerDisabled();
            }
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in isCoverManagerDisabled : ", e);
            return false;
        }
    }

    public void disableCoverManager(boolean z) {
        try {
            ICoverManager service = getService();
            if (service != null) {
                service.disableCoverManager(z, this.mToken, this.mContext.getPackageName());
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in disalbeCoverManager : ", e);
        }
    }

    public void registerNfcTouchListener(int i, NfcLedCoverTouchListener nfcLedCoverTouchListener) {
        boolean z;
        NfcLedCoverTouchListenerDelegate nfcLedCoverTouchListenerDelegate;
        Log.d(TAG, "registerNfcTouchListener");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "registerNfcTouchListener : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (nfcLedCoverTouchListener == null) {
            Log.w(TAG, "registerNfcTouchListener : listener is null");
            return;
        }
        Iterator<NfcLedCoverTouchListenerDelegate> it = this.mNfcLedCoverTouchListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                nfcLedCoverTouchListenerDelegate = null;
                break;
            } else {
                nfcLedCoverTouchListenerDelegate = it.next();
                if (nfcLedCoverTouchListenerDelegate.getListener().equals(nfcLedCoverTouchListener)) {
                    z = true;
                    break;
                }
            }
        }
        if (nfcLedCoverTouchListenerDelegate == null) {
            nfcLedCoverTouchListenerDelegate = new NfcLedCoverTouchListenerDelegate(nfcLedCoverTouchListener, null, this.mContext);
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (nfcLedCoverTouchListenerDelegate != null) {
                    service.registerNfcTouchListenerCallback(i, nfcLedCoverTouchListenerDelegate, componentName);
                    if (z) {
                        return;
                    }
                    this.mNfcLedCoverTouchListenerDelegates.add(nfcLedCoverTouchListenerDelegate);
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerNfcTouchListener: ", e);
        }
    }

    public void unregisterNfcTouchListener(NfcLedCoverTouchListener nfcLedCoverTouchListener) {
        NfcLedCoverTouchListenerDelegate nfcLedCoverTouchListenerDelegate;
        Log.d(TAG, "unregisterNfcTouchListener");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "unregisterNfcTouchListener : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (nfcLedCoverTouchListener == null) {
            Log.w(TAG, "unregisterNfcTouchListener : listener is null");
            return;
        }
        Iterator<NfcLedCoverTouchListenerDelegate> it = this.mNfcLedCoverTouchListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                nfcLedCoverTouchListenerDelegate = null;
                break;
            } else {
                nfcLedCoverTouchListenerDelegate = it.next();
                if (nfcLedCoverTouchListenerDelegate.getListener().equals(nfcLedCoverTouchListener)) {
                    break;
                }
            }
        }
        if (nfcLedCoverTouchListenerDelegate == null) {
            return;
        }
        try {
            ICoverManager service = getService();
            if (service == null || !service.unregisterNfcTouchListenerCallback(nfcLedCoverTouchListenerDelegate)) {
                return;
            }
            this.mNfcLedCoverTouchListenerDelegates.remove(nfcLedCoverTouchListenerDelegate);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterNfcTouchListener: ", e);
        }
    }

    public void addLedNotification(Bundle bundle) {
        Log.d(TAG, "addLedNotification");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "addLedNotification : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (bundle == null) {
            Log.e(TAG, "addLedNotification : Null notification data!");
            return;
        }
        ICoverManager service = getService();
        if (service != null) {
            try {
                service.addLedNotification(bundle);
            } catch (RemoteException e) {
                Log.e(TAG, "addLedNotification in sendData to NFC : ", e);
            }
        }
    }

    public void removeLedNotification(Bundle bundle) {
        Log.d(TAG, "removeLedNotification");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "removeLedNotification : This device does not support NFC Led cover");
            return;
        }
        if (Process.myUid() != 1000) {
            throw new SecurityException("CoverManager only available from system UID.");
        }
        if (bundle == null) {
            Log.e(TAG, "removeLedNotification : Null notification data!");
            return;
        }
        ICoverManager service = getService();
        if (service != null) {
            try {
                service.removeLedNotification(bundle);
            } catch (RemoteException e) {
                Log.e(TAG, "removeLedNotification in sendData to NFC : ", e);
            }
        }
    }

    public void sendSystemEvent(Bundle bundle) {
        if (!isSupportCover()) {
            Log.w(TAG, "sendSystemEvent : This device does not support cover");
            return;
        }
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "sendSystemEvent : This device does not support NFC Led cover");
            return;
        }
        if (bundle == null) {
            Log.e(TAG, "sendSystemEvent : Null system event data!");
            return;
        }
        ICoverManager service = getService();
        if (service != null) {
            try {
                service.sendSystemEvent(bundle);
            } catch (RemoteException e) {
                Log.e(TAG, "sendSystemEvent in sendData to NFC : ", e);
            }
        }
    }

    public void registerLedSystemListener(LedSystemEventListener ledSystemEventListener) {
        boolean z;
        LedSystemEventListenerDelegate ledSystemEventListenerDelegate;
        if (!isSupportCover()) {
            Log.w(TAG, "registerLedSystemListener : This device does not support cover");
            return;
        }
        Log.d(TAG, "registerLedSystemListener");
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "registerLedSystemListener : This device does not support NFC Led cover");
            return;
        }
        if (ledSystemEventListener == null) {
            Log.w(TAG, "registerLedSystemListener : listener is null");
            return;
        }
        Iterator<LedSystemEventListenerDelegate> it = this.mLedSystemEventListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                ledSystemEventListenerDelegate = null;
                break;
            } else {
                ledSystemEventListenerDelegate = it.next();
                if (ledSystemEventListenerDelegate.getListener().equals(ledSystemEventListener)) {
                    z = true;
                    break;
                }
            }
        }
        if (ledSystemEventListenerDelegate == null) {
            ledSystemEventListenerDelegate = new LedSystemEventListenerDelegate(ledSystemEventListener, null, this.mContext);
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (ledSystemEventListenerDelegate != null) {
                    service.registerNfcTouchListenerCallback(4, ledSystemEventListenerDelegate, componentName);
                    if (z) {
                        return;
                    }
                    this.mLedSystemEventListenerDelegates.add(ledSystemEventListenerDelegate);
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerLedSystemListener: ", e);
        }
    }

    public void unregisterLedSystemEventListener(LedSystemEventListener ledSystemEventListener) {
        LedSystemEventListenerDelegate ledSystemEventListenerDelegate;
        Log.d(TAG, "unregisterLedSystemEventListener");
        if (!isSupportCover()) {
            Log.w(TAG, "unregisterLedSystemEventListener : This device does not support cover");
            return;
        }
        if (!isSupportNfcLedCover()) {
            Log.w(TAG, "unregisterLedSystemEventListener : This device does not support NFC Led cover");
            return;
        }
        if (ledSystemEventListener == null) {
            Log.w(TAG, "unregisterLedSystemEventListener : listener is null");
            return;
        }
        Iterator<LedSystemEventListenerDelegate> it = this.mLedSystemEventListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                ledSystemEventListenerDelegate = null;
                break;
            } else {
                ledSystemEventListenerDelegate = it.next();
                if (ledSystemEventListenerDelegate.getListener().equals(ledSystemEventListener)) {
                    break;
                }
            }
        }
        if (ledSystemEventListenerDelegate == null) {
            return;
        }
        try {
            ICoverManager service = getService();
            if (service == null || !service.unregisterNfcTouchListenerCallback(ledSystemEventListenerDelegate)) {
                return;
            }
            this.mLedSystemEventListenerDelegates.remove(ledSystemEventListenerDelegate);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterLedSystemEventListener: ", e);
        }
    }

    public void registerCoverPowerKeyListener(CoverPowerKeyListener coverPowerKeyListener) {
        boolean z;
        CoverPowerKeyListenerDelegate coverPowerKeyListenerDelegate;
        if (!isSupportCover()) {
            Log.w(TAG, "registerCoverPowerKeyListener : This device does not support cover");
            return;
        }
        Log.d(TAG, "registerCoverPowerKeyListener");
        if (!isSupportFlipCover()) {
            Log.w(TAG, "registerLedSystemListener : This device does not support Flip cover");
            return;
        }
        if (coverPowerKeyListener == null) {
            Log.w(TAG, "registerCoverPowerKeyListener : listener is null");
            return;
        }
        Iterator<CoverPowerKeyListenerDelegate> it = this.mCoverPowerKeyListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                coverPowerKeyListenerDelegate = null;
                break;
            } else {
                coverPowerKeyListenerDelegate = it.next();
                if (coverPowerKeyListenerDelegate.getListener().equals(coverPowerKeyListener)) {
                    z = true;
                    break;
                }
            }
        }
        if (coverPowerKeyListenerDelegate == null) {
            coverPowerKeyListenerDelegate = new CoverPowerKeyListenerDelegate(coverPowerKeyListener, null, this.mContext);
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
                if (coverPowerKeyListenerDelegate != null) {
                    service.registerNfcTouchListenerCallback(10, coverPowerKeyListenerDelegate, componentName);
                    if (z) {
                        return;
                    }
                    this.mCoverPowerKeyListenerDelegates.add(coverPowerKeyListenerDelegate);
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerCoverPowerKeyListener: ", e);
        }
    }

    public void unregisterCoverPowerKeyListener(CoverPowerKeyListener coverPowerKeyListener) {
        CoverPowerKeyListenerDelegate coverPowerKeyListenerDelegate;
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
        if (coverPowerKeyListener == null) {
            Log.w(TAG, "unregisterCoverPowerKeyListener : listener is null");
            return;
        }
        Iterator<CoverPowerKeyListenerDelegate> it = this.mCoverPowerKeyListenerDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                coverPowerKeyListenerDelegate = null;
                break;
            } else {
                coverPowerKeyListenerDelegate = it.next();
                if (coverPowerKeyListenerDelegate.getListener().equals(coverPowerKeyListener)) {
                    break;
                }
            }
        }
        if (coverPowerKeyListenerDelegate == null) {
            return;
        }
        try {
            ICoverManager service = getService();
            if (service == null || !service.unregisterNfcTouchListenerCallback(coverPowerKeyListenerDelegate)) {
                return;
            }
            this.mCoverPowerKeyListenerDelegates.remove(coverPowerKeyListenerDelegate);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterCoverPowerKeyListener: ", e);
        }
    }

    public boolean disableLcdOffByCover(StateListener stateListener) {
        CoverListenerDelegate coverListenerDelegate;
        if (!isSupportCover()) {
            Log.w(TAG, "disableLcdOffByCover : This device does not support cover");
            return false;
        }
        if (stateListener == null) {
            Log.w(TAG, "disableLcdOffByCover : listener cannot be null");
            return false;
        }
        Log.d(TAG, "disableLcdOffByCover");
        Iterator<CoverListenerDelegate> it = this.mLcdOffDisableDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                coverListenerDelegate = null;
                break;
            }
            coverListenerDelegate = it.next();
            if (coverListenerDelegate.getListener().equals(stateListener)) {
                break;
            }
        }
        if (coverListenerDelegate == null) {
            coverListenerDelegate = new CoverListenerDelegate(stateListener, null, this.mContext);
        }
        try {
            ICoverManager service = getService();
            if (service != null && service.disableLcdOffByCover(coverListenerDelegate, new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName()))) {
                this.mLcdOffDisableDelegates.add(coverListenerDelegate);
                return true;
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterNfcTouchListener: ", e);
        }
        return false;
    }

    public boolean enableLcdOffByCover(StateListener stateListener) {
        CoverListenerDelegate coverListenerDelegate;
        if (!isSupportCover()) {
            Log.w(TAG, "enableLcdOffByCover : This device does not support cover");
            return false;
        }
        if (stateListener == null) {
            Log.w(TAG, "enableLcdOffByCover : listener cannot be null");
            return false;
        }
        Log.d(TAG, "enableLcdOffByCover");
        Iterator<CoverListenerDelegate> it = this.mLcdOffDisableDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                coverListenerDelegate = null;
                break;
            }
            coverListenerDelegate = it.next();
            if (coverListenerDelegate.getListener().equals(stateListener)) {
                break;
            }
        }
        if (coverListenerDelegate == null) {
            Log.e(TAG, "enableLcdOffByCover: Matching listener not found, cannot enable");
            return false;
        }
        try {
            ICoverManager service = getService();
            if (service != null && service.enableLcdOffByCover(coverListenerDelegate, new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName()))) {
                this.mLcdOffDisableDelegates.remove(coverListenerDelegate);
                return true;
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterNfcTouchListener: ", e);
        }
        return false;
    }
}
