package com.samsung.android.sec_platform_library;

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
import android.os.UserHandle;
import android.util.Log;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public class FactoryPhone {
    public String HOST_NAME;
    public Context mContext;
    public String LOG_TAG = "F_PHONE";
    public String BIND_CLASS_NAME = "com.sec.phone.SecPhoneService";
    public Messenger mServiceMessenger = null;
    public LinkedList mPendingMessage = new LinkedList();
    public ServiceConnection mSecPhoneServiceConnection = new ServiceConnection() { // from class: com.samsung.android.sec_platform_library.FactoryPhone.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(FactoryPhone.this.LOG_TAG, FactoryPhone.this.HOST_NAME + " onServiceConnected()");
            FactoryPhone.this.mServiceMessenger = new Messenger(iBinder);
            FactoryPhone.this.registerAction();
            FactoryPhone.this.sendPendingMessage();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(FactoryPhone.this.LOG_TAG, FactoryPhone.this.HOST_NAME + " onServiceDisconnected()");
            FactoryPhone.this.mServiceMessenger = null;
        }
    };
    public Handler mDummyHandler = new Handler() { // from class: com.samsung.android.sec_platform_library.FactoryPhone.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.i(FactoryPhone.this.LOG_TAG, FactoryPhone.this.HOST_NAME + " response handler does not exist");
            if (message.what == 1000) {
                Log.i(FactoryPhone.this.LOG_TAG, FactoryPhone.this.HOST_NAME + " dummy handler : BASE_ID");
                return;
            }
            Log.i(FactoryPhone.this.LOG_TAG, FactoryPhone.this.HOST_NAME + " dummy handler : " + message.what);
        }
    };

    public final void setMultiRilSupport(int i) {
        String num = Integer.toString(i);
        if (i > 0) {
            this.BIND_CLASS_NAME += num;
            String str = this.LOG_TAG + num;
            this.LOG_TAG = str;
            Log.i(str, "setMultiRilSupport() : bind to " + this.BIND_CLASS_NAME);
            return;
        }
        Log.e(this.LOG_TAG, "setMultiRilSupport() : Wrong index : " + num + " bind to " + this.BIND_CLASS_NAME);
    }

    public FactoryPhone(Context context, int i) {
        this.HOST_NAME = null;
        this.mContext = null;
        this.mContext = context;
        this.HOST_NAME = "[[" + this.mContext.getPackageName() + "]]";
        setMultiRilSupport(i);
        connectToRilService();
    }

    public void connectToRilService() {
        Log.i(this.LOG_TAG, this.HOST_NAME + " bind SecPhone Service with FactoryPhone");
        UserHandle userHandle = null;
        try {
            userHandle = (UserHandle) Class.forName("android.os.UserHandle").getMethod("semOf", Integer.TYPE).invoke(null, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClassName("com.sec.phone", this.BIND_CLASS_NAME);
        this.mContext.semBindServiceAsUser(intent, this.mSecPhoneServiceConnection, 1, userHandle);
    }

    public void disconnectFromRilService() {
        ServiceConnection serviceConnection;
        Log.i(this.LOG_TAG, this.HOST_NAME + "disconnect from Ril service");
        Context context = this.mContext;
        if (context == null || (serviceConnection = this.mSecPhoneServiceConnection) == null || this.mServiceMessenger == null) {
            return;
        }
        context.unbindService(serviceConnection);
        this.mServiceMessenger = null;
    }

    public void invokeOemRilRequestRaw(byte[] bArr, Message message) {
        Log.i(this.LOG_TAG, this.HOST_NAME + " invokeOemRilRequestRaw()");
        _invokeOemRilRequestRaw(bArr, message, false);
    }

    public final void _invokeOemRilRequestRaw(byte[] bArr, Message message, boolean z) {
        if (message == null) {
            message = this.mDummyHandler.obtainMessage(1000);
        }
        Bundle data = message.getData();
        if (z) {
            data.putString("Action", convertByteToString(bArr));
        } else {
            data.putByteArray("request", bArr);
        }
        message.setData(data);
        message.replyTo = new Messenger(message.getTarget());
        Messenger messenger = this.mServiceMessenger;
        if (messenger == null) {
            Log.i(this.LOG_TAG, this.HOST_NAME + " mServiceMessenger is null, add message to pending queue...");
            addMessageToPendingQueue(message);
            return;
        }
        try {
            messenger.send(message);
        } catch (RemoteException unused) {
        }
    }

    public final String convertByteToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    public synchronized void addMessageToPendingQueue(Message message) {
        Log.i(this.LOG_TAG, this.HOST_NAME + " addMessageToPendingQueue()");
        this.mPendingMessage.offer(message);
        if (this.mServiceMessenger != null) {
            sendPendingMessage();
        }
    }

    public synchronized void sendPendingMessage() {
        Log.i(this.LOG_TAG, this.HOST_NAME + " sendPendingMessage()");
        while (this.mPendingMessage.peek() != null) {
            try {
                this.mServiceMessenger.send((Message) this.mPendingMessage.poll());
            } catch (RemoteException unused) {
            }
        }
    }

    public void registerAction() {
        Log.i(this.LOG_TAG, "default registerAction()");
    }
}
