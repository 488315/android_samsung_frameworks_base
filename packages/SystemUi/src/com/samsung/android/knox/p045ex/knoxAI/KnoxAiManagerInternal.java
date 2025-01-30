package com.samsung.android.knox.p045ex.knoxAI;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.samsung.android.knox.p045ex.knoxAI.IDeathNotifier;
import com.samsung.android.knox.p045ex.knoxAI.IDecryptFramework;
import com.samsung.android.knox.p045ex.knoxAI.IKeyProvisioningCallback;
import com.samsung.android.knox.p045ex.knoxAI.KnoxAiManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KnoxAiManagerInternal {
    public static final int CONN_MAX_WAIT_TIME = 2500;
    public static final String TAG = "KnoxAiManagerInternal";
    public static KnoxAiManagerInternal sKnoxAiManagerInternal;
    public Context mContext;
    public IDecryptFramework mDecryptFwService = null;
    public final Object mConnLock = new Object();
    public ServiceConnection mKFAConn = new ServiceConnection() { // from class: com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (KnoxAiManagerInternal.this) {
                KnoxAiManagerInternal.this.mDecryptFwService = IDecryptFramework.Stub.asInterface(iBinder);
                Log.d(KnoxAiManagerInternal.TAG, "KFAService connected");
                synchronized (KnoxAiManagerInternal.this.mConnLock) {
                    KnoxAiManagerInternal.this.mConnLock.notifyAll();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (KnoxAiManagerInternal.this) {
                KnoxAiManagerInternal.this.mDecryptFwService = null;
                Log.d(KnoxAiManagerInternal.TAG, "KFAService disconnected");
                synchronized (KnoxAiManagerInternal.this.mConnLock) {
                    KnoxAiManagerInternal.this.mConnLock.notifyAll();
                }
                KnoxAiManagerInternal.this.bindKFAServiceInstance();
            }
        }
    };

    private KnoxAiManagerInternal(Context context) {
        Log.d(TAG, "KnoxAiManagerInternal Constructor called: " + context.toString());
        this.mContext = context;
    }

    public static synchronized KnoxAiManagerInternal getInstance(Context context) {
        KnoxAiManagerInternal knoxAiManagerInternal;
        synchronized (KnoxAiManagerInternal.class) {
            if (sKnoxAiManagerInternal == null) {
                sKnoxAiManagerInternal = new KnoxAiManagerInternal(context);
            }
            sKnoxAiManagerInternal.bindKFAServiceInstance();
            knoxAiManagerInternal = sKnoxAiManagerInternal;
        }
        return knoxAiManagerInternal;
    }

    public final boolean bindKFAServiceInstance() {
        Intent intent = new Intent(IDecryptFramework.class.getName());
        intent.setAction("action.decrypt");
        intent.setPackage("com.samsung.android.app.kfa");
        intent.putExtra("binder", "decrypt");
        boolean bindService = this.mContext.bindService(intent, this.mKFAConn, 1);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("getKFAServiceInstance trying to bind, status: ", bindService, TAG);
        return bindService;
    }

    public final KnoxAiManager.ErrorCodes close(long j) {
        if (getKFAServiceInstance() == null) {
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
        try {
            KnoxAiManager.ErrorCodes codeFromValue = KnoxAiManager.ErrorCodes.getCodeFromValue(this.mDecryptFwService.close(j));
            return codeFromValue == null ? KnoxAiManager.ErrorCodes.KNOX_AI_UNKNOWN_ERROR : codeFromValue;
        } catch (DeadObjectException e) {
            Log.e(TAG, "DeadObjectException in KFA, retrying bind", e);
            bindKFAServiceInstance();
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException in KFA", e2);
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
    }

    public final long createKnoxAiSession() {
        int value;
        String str = TAG;
        Log.d(str, "createKnoxAiSession entry");
        if (getKFAServiceInstance() != null) {
            Log.d(str, "createKnoxAiSession connection exists");
            try {
                return this.mDecryptFwService.createKnoxAiSession(new IDeathNotifier.Stub() { // from class: com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal.2
                });
            } catch (DeadObjectException e) {
                Log.e(TAG, "DeadObjectException in KFA, retrying bind", e);
                bindKFAServiceInstance();
                value = KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE.getValue();
                return value;
            } catch (RemoteException e2) {
                Log.e(TAG, "RemoteException in KFA", e2);
                value = KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE.getValue();
                return value;
            }
        }
        value = KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE.getValue();
        return value;
    }

    public final KnoxAiManager.ErrorCodes destroyKnoxAiSession(long j) {
        String str = TAG;
        Log.d(str, "destroyKnoxAiSession entry");
        if (getKFAServiceInstance() == null) {
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
        Log.d(str, "destroyKnoxAiSession connection exists");
        try {
            KnoxAiManager.ErrorCodes codeFromValue = KnoxAiManager.ErrorCodes.getCodeFromValue(this.mDecryptFwService.destroyKnoxAiSession(j));
            return codeFromValue == null ? KnoxAiManager.ErrorCodes.KNOX_AI_UNKNOWN_ERROR : codeFromValue;
        } catch (DeadObjectException e) {
            Log.e(TAG, "DeadObjectException in KFA, retrying bind", e);
            bindKFAServiceInstance();
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException in KFA", e2);
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
    }

    public final KnoxAiManager.ErrorCodes execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) {
        if (getKFAServiceInstance() == null) {
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
        try {
            KnoxAiManager.ErrorCodes codeFromValue = KnoxAiManager.ErrorCodes.getCodeFromValue(this.mDecryptFwService.execute(j, dataBufferArr, dataBufferArr2));
            return codeFromValue == null ? KnoxAiManager.ErrorCodes.KNOX_AI_UNKNOWN_ERROR : codeFromValue;
        } catch (DeadObjectException e) {
            Log.e(TAG, "DeadObjectException in KFA, retrying bind", e);
            bindKFAServiceInstance();
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException in KFA", e2);
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
    }

    public final IDecryptFramework getKFAServiceInstance() {
        synchronized (this) {
            IDecryptFramework iDecryptFramework = this.mDecryptFwService;
            boolean z = true;
            if (iDecryptFramework == null) {
                Log.d(TAG, "getKFAServiceInstance service is null");
            } else {
                IBinder asBinder = iDecryptFramework.asBinder();
                z = asBinder != null ? true ^ asBinder.pingBinder() : false;
            }
            if (z) {
                Log.d(TAG, "getKFAServiceInstance trying to rebind from client");
                this.mDecryptFwService = null;
                IBinder service = ServiceManager.getService("KFAService");
                if (service != null) {
                    this.mDecryptFwService = IDecryptFramework.Stub.asInterface(service);
                } else if (bindKFAServiceInstance()) {
                    try {
                        synchronized (this.mConnLock) {
                            this.mConnLock.wait(2500L);
                        }
                    } catch (InterruptedException unused) {
                        Log.i(TAG, "getKFAServiceInstance() interrupted");
                    }
                    Log.i(TAG, "getKFAServiceInstance binding timed out or success");
                }
            }
        }
        return this.mDecryptFwService;
    }

    public final void getKeyProvisioning(final KeyProvisioningResultCallback keyProvisioningResultCallback) {
        if (getKFAServiceInstance() == null) {
            return;
        }
        try {
            this.mDecryptFwService.getKeyProvisioning(new IKeyProvisioningCallback.Stub() { // from class: com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal.3
                @Override // com.samsung.android.knox.p045ex.knoxAI.IKeyProvisioningCallback
                public final void onFinished(int i) {
                    KnoxAiManager.ErrorCodes codeFromValue = KnoxAiManager.ErrorCodes.getCodeFromValue(i);
                    if (codeFromValue == null) {
                        codeFromValue = KnoxAiManager.ErrorCodes.KNOX_AI_UNKNOWN_ERROR;
                    }
                    keyProvisioningResultCallback.onFinished(codeFromValue);
                }
            });
        } catch (DeadObjectException e) {
            Log.e(TAG, "DeadObjectException in KFA, retrying bind", e);
            bindKFAServiceInstance();
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException in KFA", e2);
        }
    }

    public final KnoxAiManager.ErrorCodes getModelInputShape(long j, int i, int[] iArr) {
        if (getKFAServiceInstance() == null) {
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
        try {
            KnoxAiManager.ErrorCodes codeFromValue = KnoxAiManager.ErrorCodes.getCodeFromValue(this.mDecryptFwService.getModelInputShape(j, i, iArr));
            return codeFromValue == null ? KnoxAiManager.ErrorCodes.KNOX_AI_UNKNOWN_ERROR : codeFromValue;
        } catch (DeadObjectException e) {
            Log.e(TAG, "DeadObjectException in KFA, retrying bind", e);
            bindKFAServiceInstance();
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException in KFA", e2);
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
    }

    public final KnoxAiManager.ErrorCodes open(long j, KfaOptions kfaOptions) {
        if (getKFAServiceInstance() == null) {
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
        try {
            KnoxAiManager.ErrorCodes codeFromValue = KnoxAiManager.ErrorCodes.getCodeFromValue(this.mDecryptFwService.open(j, kfaOptions));
            return codeFromValue == null ? KnoxAiManager.ErrorCodes.KNOX_AI_UNKNOWN_ERROR : codeFromValue;
        } catch (DeadObjectException e) {
            Log.e(TAG, "DeadObjectException in KFA, retrying bind", e);
            bindKFAServiceInstance();
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException in KFA", e2);
            return KnoxAiManager.ErrorCodes.KNOX_AI_SERVICE_FAILURE;
        }
    }
}
