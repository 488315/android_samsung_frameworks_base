package com.samsung.android.knox.sdp;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.sdp.ISdpListener;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.sdp.core.SdpException;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SdpUtil {
    private static final String ANDROID_DEFAULT_ALIAS = "android_";
    private static final String ANDROID_DEFAULT_ALIAS_MAX = "android_999";
    private static final int ANDROID_DEFAULT_USER_ID_MAX = 999;
    private static final int ANDROID_DEFAULT_USER_ID_MIN = 0;
    private static final double SDK_CURRENT_VERSION = 1.1d;
    private static final double SDK_NOT_SUPPORTED = 0.0d;
    private static final double SDK_VERSION_1_0 = 1.0d;
    private static final double SDK_VERSION_1_1 = 1.1d;
    public static final boolean SERVICE_BUILD = false;
    private static final String TAG = "SdpUtil";
    private static SdpUtil mSdpUtil = null;
    private static final boolean runAllConvert = false;
    private ArrayList<SdpStateBinderListener> mBinderListeners = new ArrayList<>();

    class EngineRemovedEvent extends SdpEvent {
        public EngineRemovedEvent(SdpUtil sdpUtil) {
            super(sdpUtil, 2);
        }
    }

    class SdpEvent {
        static final int ON_ENGINE_REMOVED = 2;
        static final int ON_STATE_CHANGED = 1;
        private Message mMessage;

        public SdpEvent(SdpUtil sdpUtil, int i) {
            Message messageObtain = Message.obtain();
            this.mMessage = messageObtain;
            messageObtain.what = i;
            messageObtain.obj = this;
        }

        public Message getMessage() {
            return this.mMessage;
        }
    }

    class SdpStateBinderListener extends ISdpListener.Stub {
        private final Handler mHandler;
        SdpStateListener mListener;

        public /* synthetic */ SdpStateBinderListener(SdpUtil sdpUtil, SdpStateListener sdpStateListener, int i) {
            this(sdpStateListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public SdpStateListener getListener() {
            return this.mListener;
        }

        public void onEngineRemoved() {
            this.mListener.onEngineRemoved();
        }

        public void onStateChange(int i) {
            this.mHandler.sendMessage(new StateChangeEvent(SdpUtil.this, i).getMessage());
            this.mListener.onStateChange(i);
        }

        private SdpStateBinderListener(SdpStateListener sdpStateListener) {
            this.mListener = sdpStateListener;
            this.mHandler = new Handler() { // from class: com.samsung.android.knox.sdp.SdpUtil.SdpStateBinderListener.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    SdpEvent sdpEvent = (SdpEvent) message.obj;
                    int i = message.what;
                    if (i == 1) {
                        SdpStateBinderListener.this.mListener.onStateChange(((StateChangeEvent) sdpEvent).state);
                    } else {
                        if (i == 2) {
                            SdpStateBinderListener.this.mListener.onEngineRemoved();
                            return;
                        }
                        Log.e(SdpUtil.TAG, "Unsupported event " + message.what);
                    }
                }
            };
        }
    }

    class StateChangeEvent extends SdpEvent {
        public int state;

        public StateChangeEvent(SdpUtil sdpUtil, int i) {
            super(sdpUtil, 1);
            this.state = i;
        }
    }

    private SdpUtil() {
    }

    private void enforcePermission() throws SdpException {
        IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (iDarManagerServiceAsInterface != null) {
            try {
                if (iDarManagerServiceAsInterface.isLicensed() == 0) {
                } else {
                    throw new SdpException(-9);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to talk with sdp service...", e);
            }
        }
    }

    public static int extractAndroidDefaultUserId(String str) {
        if (11 < str.length() || 8 >= str.length() || !str.contains(ANDROID_DEFAULT_ALIAS)) {
            return -1;
        }
        int length = str.length() - 8;
        char[] charArray = str.substring(8).toCharArray();
        int iPow = 0;
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(charArray[i])) {
                return -1;
            }
            int numericValue = Character.getNumericValue(charArray[i]);
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(numericValue, i, "num:", " index-", TAG);
            iPow = i == 8 ? iPow + numericValue : (int) ((Math.pow(10.0d, (length - i) - 1) * numericValue) + iPow);
        }
        return iPow;
    }

    public static String getAndroidDefaultAlias(int i) {
        if (isAndroidDefaultUser(i)) {
            return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ANDROID_DEFAULT_ALIAS);
        }
        return null;
    }

    public static SdpUtil getInstance() {
        if (mSdpUtil == null) {
            mSdpUtil = new SdpUtil();
        }
        return mSdpUtil;
    }

    public static boolean isAndroidDefaultAlias(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        int iExtractAndroidDefaultUserId = extractAndroidDefaultUserId(str);
        ListPopupWindow$$ExternalSyntheticOutline0.m(iExtractAndroidDefaultUserId, "detecected userId : ", TAG);
        return iExtractAndroidDefaultUserId >= 0 && iExtractAndroidDefaultUserId <= ANDROID_DEFAULT_USER_ID_MAX;
    }

    public static boolean isAndroidDefaultUser(int i) {
        return i >= 0 && i <= ANDROID_DEFAULT_USER_ID_MAX;
    }

    public static boolean isKnoxWorkspace(int i) {
        return SemPersonaManager.isKnoxId(i);
    }

    public SdpEngineInfo getEngineInfo(String str) throws SdpException {
        enforcePermission();
        IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (iDarManagerServiceAsInterface != null) {
            try {
                SdpEngineInfo engineInfo = iDarManagerServiceAsInterface.getEngineInfo(str);
                if (engineInfo != null) {
                    return engineInfo;
                }
                throw new SdpException(-5);
            } catch (RemoteException e) {
                Log.e(TAG, "getEngineInfo :: Failed to call getEngineInfo", e);
            }
        }
        Log.e(TAG, "getEngineInfo :: Failed to talk with sdp service...");
        throw new SdpException(-13);
    }

    public String getSDKVersion() {
        return String.valueOf(1.1d);
    }

    public String getSupportedSDKVersion() throws SdpException {
        throw new SdpException(-10);
    }

    public boolean isSdpSupported() {
        IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (iDarManagerServiceAsInterface == null) {
            return false;
        }
        try {
            return iDarManagerServiceAsInterface.isSdpSupported();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to talk with sdp service...", e);
            return false;
        }
    }

    public boolean registerListener(String str, SdpStateListener sdpStateListener) {
        IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        int i = 0;
        if (iDarManagerServiceAsInterface != null) {
            try {
                SdpStateBinderListener sdpStateBinderListener = new SdpStateBinderListener(this, sdpStateListener, i);
                if (iDarManagerServiceAsInterface.registerListener(str, sdpStateBinderListener) == 0) {
                    this.mBinderListeners.add(sdpStateBinderListener);
                    return true;
                }
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException from call registerListener", e);
                return false;
            }
        }
        return false;
    }

    public boolean unregisterListener(String str, SdpStateListener sdpStateListener) {
        IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (iDarManagerServiceAsInterface != null) {
            int size = this.mBinderListeners.size();
            for (int i = 0; i < size; i++) {
                SdpStateBinderListener sdpStateBinderListener = this.mBinderListeners.get(i);
                if (sdpStateBinderListener.getListener() == sdpStateListener) {
                    this.mBinderListeners.remove(i);
                    try {
                        if (iDarManagerServiceAsInterface.unregisterListener(str, sdpStateBinderListener) != 0) {
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "RemoteException from call unregisterListener", e);
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
