package com.sec.ims.scab;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.sec.ims.scab.ICABService;
import com.sec.ims.util.IMSLog;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CABManager {
    private static final String TAG = "CABManager";
    private static ICABService mImsCABService;
    private boolean mBound = false;
    private final ServiceConnection mConnection = new ServiceConnection() { // from class: com.sec.ims.scab.CABManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CABManager.mImsCABService = ICABService.Stub.asInterface(iBinder);
            Log.d(CABManager.TAG, "Connected to CABService.");
            if (CABManager.this.mListener != null) {
                CABManager.this.mListener.onConnected();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            CABManager.mImsCABService = null;
            Log.d(CABManager.TAG, "Disconnected to CABService.");
            if (CABManager.this.mListener != null) {
                CABManager.this.mListener.onDisconnected();
            }
        }
    };
    private Context mContext;
    private CABServiceListener mListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface CABServiceListener {
        void onConnected();

        void onDisconnected();
    }

    public CABManager(Context context) {
        this.mContext = context;
    }

    private boolean isServiceBinded() {
        if (mImsCABService != null) {
            return true;
        }
        Log.d(TAG, "Failed to bind service.");
        return false;
    }

    public void addBatchOfContacts(List<String> list) {
        Log.d(TAG, "addBatchOfContacts:" + list);
        if (isServiceBinded()) {
            try {
                mImsCABService.addBatchOfContacts(list);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void businessLineReadyForSync(String str, boolean z) {
        Log.d(TAG, "businessLineReadyForSync:" + IMSLog.checker(str) + ": " + z);
        if (isServiceBinded()) {
            try {
                mImsCABService.businessLineReadyForSync(str, z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void connectService() {
        Log.d(TAG, "!!!!!!!!!!!!!!! DEPRECATED !!!!!!!!!!!!!!!");
    }

    public void deleteBatchOfContacts(List<String> list) {
        Log.d(TAG, "deleteBatchOfContacts:" + list);
        if (isServiceBinded()) {
            try {
                mImsCABService.deleteBatchOfContacts(list);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void disableService() {
        Log.d(TAG, "disableService()");
        if (isServiceBinded()) {
            try {
                mImsCABService.disableService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnectService() {
        if (this.mContext != null && this.mBound) {
            Log.e(TAG, "disconnect");
            this.mContext.unbindService(this.mConnection);
            this.mBound = false;
        } else {
            CABServiceListener cABServiceListener = this.mListener;
            if (cABServiceListener != null) {
                cABServiceListener.onDisconnected();
            }
        }
    }

    public void enableService() {
        Log.d(TAG, "enableService()");
        if (isServiceBinded()) {
            try {
                mImsCABService.enableService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPendingUploadContactsExists() {
        Log.d(TAG, "isPendingUploadContactsExists");
        if (!isServiceBinded()) {
            return false;
        }
        try {
            return mImsCABService.isPendingUploadContactsExists();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onBufferDBReadResult(long j, boolean z) {
        Log.d(TAG, "onBufferDBReadResult:" + j + ", isSuccess:" + z);
        if (isServiceBinded()) {
            try {
                mImsCABService.onBufferDBReadResult(j, z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void processUndownloadedBusinessContacts(String str) {
        Log.d(TAG, "processUndownloadedBusinessContacts: " + IMSLog.checker(str));
        if (isServiceBinded()) {
            try {
                mImsCABService.processUndownloadedBusinessContacts(str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCABServiceListener(CABServiceListener cABServiceListener) {
        this.mListener = cABServiceListener;
    }

    public void updateBatchOfContacts(List<String> list) {
        Log.d(TAG, "updateBatchOfContacts:" + list);
        if (isServiceBinded()) {
            try {
                mImsCABService.updateBatchOfContacts(list);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadAddressBook(List<String> list) {
        Log.d(TAG, "uploadAddressBook:" + list);
        if (isServiceBinded()) {
            try {
                mImsCABService.uploadAddressBook(list);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
