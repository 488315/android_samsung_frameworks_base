package android.net;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.telephony.SubscriptionManager;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes3.dex */
public class LowLatencyModeManager {
    public static final int LOW = 2;
    private static final int MSG_GET_LATENCY_DONE = 2;
    private static final int MSG_SET_LATENCY_DONE = 1;
    public static final int NORMAL = 1;
    private static final int SIMSLOT1 = 0;
    private static final int SIMSLOT2 = 1;
    public static final int SUPER_LOW = 4;
    private static final String TAG = "LowLatencyModeManager";
    public static final int VERY_LOW = 3;
    private LatencyCallback mCallback;
    private final Context mContext;
    private int mDlLevel;
    private boolean mExtension;
    private boolean mPrioDefault;
    private int mUlLevel;
    private Messenger mServiceMessenger = null;
    private Messenger mServiceMessenger2 = null;
    private Handler mReceiverHandler = new Handler() { // from class: android.net.LowLatencyModeManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message == null) {
                return;
            }
            int i = message.getData().getInt("error");
            int i2 = message.what;
            if (i2 == 1) {
                LowLatencyModeManager.this.log("set latency done, error:" + i);
                LowLatencyModeManager.this.unbindRilService(message.getData().getInt("slotId"));
                return;
            }
            if (i2 != 2) {
                return;
            }
            LowLatencyModeManager.this.log("get latency done, error:" + i);
            if (i == 0) {
                byte[] byteArray = message.getData().getByteArray("response");
                if (byteArray == null || byteArray.length != 4) {
                    LowLatencyModeManager.this.log("get latency wrong result format");
                    return;
                }
                LowLatencyModeManager.this.log("get latency settings from modem, ul:" + ((int) byteArray[0]) + ",dl:" + ((int) byteArray[1]) + ",prio:" + ((int) byteArray[2]) + ",ets:" + ((int) byteArray[3]));
                LatencyResult latencyResult = new LatencyResult(byteArray[0], byteArray[1], byteArray[2] == 1, byteArray[3] == 1);
                if (LowLatencyModeManager.this.mCallback != null) {
                    LowLatencyModeManager.this.mCallback.onGetLatencyDone(latencyResult);
                    LowLatencyModeManager.this.mCallback = null;
                }
            }
            LowLatencyModeManager.this.unbindRilService(message.getData().getInt("slotId"));
        }
    };
    private Messenger mSvcModeMessenger = new Messenger(this.mReceiverHandler);
    private ServiceConnection mSecPhoneServiceConnection = new ServiceConnection() { // from class: android.net.LowLatencyModeManager.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LowLatencyModeManager.this.log("onServiceConnected(), classname:" + componentName.getClassName());
            LowLatencyModeManager.this.mServiceMessenger = new Messenger(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LowLatencyModeManager.this.log("onServiceDisconnected(), classname:" + componentName.getClassName());
            LowLatencyModeManager.this.mServiceMessenger = null;
        }
    };
    private ServiceConnection mSecPhoneServiceConnection2 = new ServiceConnection() { // from class: android.net.LowLatencyModeManager.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LowLatencyModeManager.this.log("onServiceConnected(), classname:" + componentName.getClassName());
            LowLatencyModeManager.this.mServiceMessenger2 = new Messenger(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LowLatencyModeManager.this.log("onServiceDisconnected(), classname:" + componentName.getClassName());
            LowLatencyModeManager.this.mServiceMessenger2 = null;
        }
    };

    public static class LatencyCallback {
        public void onGetLatencyDone(LatencyResult latencyResult) {
        }
    }

    public static class LatencyResult {
        private int mDlLevel;
        private boolean mExtension;
        private boolean mPrioDefault;
        private int mUlLevel;

        public LatencyResult(int i, int i2, boolean z, boolean z2) {
            this.mUlLevel = i;
            this.mDlLevel = i2;
            this.mPrioDefault = z;
            this.mExtension = z2;
        }

        public int getUlLevel() {
            return this.mUlLevel;
        }

        public int getDlLevel() {
            return this.mDlLevel;
        }

        public boolean getPrioDefault() {
            return this.mPrioDefault;
        }

        public boolean getExtension() {
            return this.mExtension;
        }
    }

    public LowLatencyModeManager(Context context) {
        this.mContext = context;
    }

    public void getLowLatencyMode(LatencyCallback latencyCallback) {
        int phoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
        if (!SubscriptionManager.isValidPhoneId(phoneId)) {
            loge("invalid default datat slotId id, " + phoneId);
            return;
        }
        getLowLatencyMode(phoneId, latencyCallback);
    }

    public void getLowLatencyMode(int i, LatencyCallback latencyCallback) {
        if (!SubscriptionManager.isValidPhoneId(i)) {
            loge("invalid slotId id, " + i);
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(13);
            dataOutputStream.writeByte(18);
            dataOutputStream.writeShort(4);
            if (i == 0) {
                connectToRilService();
            } else if (i == 1) {
                connectToRilService2();
            }
            log("start to get latency settings from cp, slotId:" + i);
            Bundle bundle = new Bundle();
            bundle.putByteArray("request", byteArrayOutputStream.toByteArray());
            bundle.putInt("slotId", i);
            Message obtainMessage = this.mReceiverHandler.obtainMessage(2);
            obtainMessage.setData(bundle);
            obtainMessage.replyTo = this.mSvcModeMessenger;
            for (int i2 = 0; i2 < 10; i2++) {
                try {
                    Messenger messenger = this.mServiceMessenger;
                    if (messenger != null && i == 0) {
                        messenger.send(obtainMessage);
                    } else {
                        Messenger messenger2 = this.mServiceMessenger2;
                        if (messenger2 != null && i == 1) {
                            messenger2.send(obtainMessage);
                        } else {
                            loge("mServiceMessenger is null, wait more time for it is ready");
                            try {
                                Thread.sleep(200L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    this.mCallback = latencyCallback;
                    return;
                } catch (RemoteException e3) {
                    loge("get latency settings failed, e:" + e3);
                    return;
                }
            }
        } catch (IOException e4) {
            loge("make get latency settings data: exception occured: " + e4);
        }
    }

    public void setLowLatencyMode(int i, int i2, boolean z, boolean z2) {
        int phoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId());
        if (!SubscriptionManager.isValidPhoneId(phoneId)) {
            loge("invalid default datat slotId id, " + phoneId);
            return;
        }
        setLowLatencyMode(phoneId, i, i2, z, z2);
    }

    public void setLowLatencyMode(int i, int i2, int i3, boolean z, boolean z2) {
        if (!SubscriptionManager.isValidPhoneId(i)) {
            loge("invalid slotId id, " + i);
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(13);
            dataOutputStream.writeByte(19);
            dataOutputStream.writeShort(8);
            dataOutputStream.writeByte(i2);
            dataOutputStream.writeByte(i3);
            dataOutputStream.writeByte(z ? 1 : 0);
            dataOutputStream.writeByte(z2 ? 1 : 0);
            if (i == 0) {
                connectToRilService();
            } else if (i == 1) {
                connectToRilService2();
            }
            log("set latency mode, ulevel:" + i2 + ",dlevel:" + i3 + ", prio:" + z + ",extension:" + z2 + ",slotId:" + i);
            this.mUlLevel = i2;
            this.mDlLevel = i3;
            this.mPrioDefault = z;
            this.mExtension = z2;
            Bundle bundle = new Bundle();
            bundle.putByteArray("request", byteArrayOutputStream.toByteArray());
            bundle.putInt("slotId", i);
            Message obtainMessage = this.mReceiverHandler.obtainMessage(1);
            obtainMessage.setData(bundle);
            obtainMessage.replyTo = this.mSvcModeMessenger;
            for (int i4 = 0; i4 < 10; i4++) {
                try {
                    Messenger messenger = this.mServiceMessenger;
                    if (messenger != null && i == 0) {
                        messenger.send(obtainMessage);
                        return;
                    }
                    Messenger messenger2 = this.mServiceMessenger2;
                    if (messenger2 != null && i == 1) {
                        messenger2.send(obtainMessage);
                        return;
                    }
                    loge("mServiceMessenger is null, wait more time for it is ready");
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    }
                } catch (RemoteException unused) {
                    loge("set latency settings failed");
                    return;
                }
            }
        } catch (IOException e3) {
            loge("make set latency settings data: exception occured: " + e3);
        }
    }

    private void connectToRilService() {
        log("connect To Ril service");
        Intent intent = new Intent();
        intent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService");
        this.mContext.bindService(intent, this.mSecPhoneServiceConnection, 1);
    }

    private void connectToRilService2() {
        log("connect To Ril service2");
        Intent intent = new Intent();
        intent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService2");
        this.mContext.bindService(intent, this.mSecPhoneServiceConnection2, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindRilService(int i) {
        if (i == 0) {
            if (this.mServiceMessenger == null || this.mSecPhoneServiceConnection == null) {
                return;
            }
            try {
                log("unbindRilService");
                this.mContext.unbindService(this.mSecPhoneServiceConnection);
            } catch (IllegalArgumentException e) {
                loge("from unbindRilService : " + e.toString());
            }
            this.mServiceMessenger = null;
            return;
        }
        if (i != 1 || this.mServiceMessenger2 == null || this.mSecPhoneServiceConnection2 == null) {
            return;
        }
        try {
            log("unbindRilService2");
            this.mContext.unbindService(this.mSecPhoneServiceConnection2);
        } catch (IllegalArgumentException e2) {
            loge("from unbindRilService : " + e2.toString());
        }
        this.mServiceMessenger2 = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(String str) {
        Log.d(TAG, str);
    }

    private void loge(String str) {
        Log.e(TAG, str);
    }
}
