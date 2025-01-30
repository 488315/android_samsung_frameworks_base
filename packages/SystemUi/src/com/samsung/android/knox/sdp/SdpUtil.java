package com.samsung.android.knox.sdp;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.sdp.ISdpListener;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.sdp.core.SdpException;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class EngineRemovedEvent extends SdpEvent {
        public EngineRemovedEvent() {
            super(2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class SdpEvent {
        static final int ON_ENGINE_REMOVED = 2;
        static final int ON_STATE_CHANGED = 1;
        private Message mMessage;

        public SdpEvent(int i) {
            Message obtain = Message.obtain();
            this.mMessage = obtain;
            obtain.what = i;
            obtain.obj = this;
        }

        public Message getMessage() {
            return this.mMessage;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class SdpStateBinderListener extends ISdpListener.Stub {
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
            this.mHandler.sendMessage(SdpUtil.this.new StateChangeEvent(i).getMessage());
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class StateChangeEvent extends SdpEvent {
        public int state;

        public StateChangeEvent(int i) {
            super(1);
            this.state = i;
        }
    }

    private SdpUtil() {
    }

    private void enforcePermission() {
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface != null) {
            try {
                if (asInterface.isLicensed() == 0) {
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
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isDigit(charArray[i2])) {
                return -1;
            }
            int numericValue = Character.getNumericValue(charArray[i2]);
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("num:", numericValue, " index-", i2, TAG);
            i = i2 == 8 ? i + numericValue : (int) ((Math.pow(10.0d, (length - i2) - 1) * numericValue) + i);
        }
        return i;
    }

    public static String getAndroidDefaultAlias(int i) {
        if (isAndroidDefaultUser(i)) {
            return AbstractC0000x2c234b15.m0m(ANDROID_DEFAULT_ALIAS, i);
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
        int extractAndroidDefaultUserId = extractAndroidDefaultUserId(str);
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("detecected userId : ", extractAndroidDefaultUserId, TAG);
        return extractAndroidDefaultUserId >= 0 && extractAndroidDefaultUserId <= ANDROID_DEFAULT_USER_ID_MAX;
    }

    public static boolean isAndroidDefaultUser(int i) {
        return i >= 0 && i <= ANDROID_DEFAULT_USER_ID_MAX;
    }

    public static boolean isKnoxWorkspace(int i) {
        return SemPersonaManager.isKnoxId(i);
    }

    public SdpEngineInfo getEngineInfo(String str) {
        enforcePermission();
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface != null) {
            try {
                SdpEngineInfo engineInfo = asInterface.getEngineInfo(str);
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

    public String getSupportedSDKVersion() {
        throw new SdpException(-10);
    }

    public boolean isSdpSupported() {
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface != null) {
            try {
                return asInterface.isSdpSupported();
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to talk with sdp service...", e);
            }
        }
        return false;
    }

    public boolean registerListener(String str, SdpStateListener sdpStateListener) {
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        int i = 0;
        if (asInterface == null) {
            return false;
        }
        try {
            SdpStateBinderListener sdpStateBinderListener = new SdpStateBinderListener(this, sdpStateListener, i);
            if (asInterface.registerListener(str, sdpStateBinderListener) != 0) {
                return false;
            }
            this.mBinderListeners.add(sdpStateBinderListener);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException from call registerListener", e);
            return false;
        }
    }

    public boolean unregisterListener(String str, SdpStateListener sdpStateListener) {
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface == null) {
            return false;
        }
        int size = this.mBinderListeners.size();
        for (int i = 0; i < size; i++) {
            SdpStateBinderListener sdpStateBinderListener = this.mBinderListeners.get(i);
            if (sdpStateBinderListener.getListener() == sdpStateListener) {
                this.mBinderListeners.remove(i);
                try {
                    if (asInterface.unregisterListener(str, sdpStateBinderListener) != 0) {
                        return false;
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, "RemoteException from call unregisterListener", e);
                    return false;
                }
            }
        }
        return true;
    }
}
