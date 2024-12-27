package com.samsung.android.nfc.adapter;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SamsungNfcAdapter {
    public static final HashMap mAdapter = new HashMap();
    public static ISamsungNfcAdapter sService;

    public static void attemptDeadServiceRecovery(Exception exc) {
        Log.e("SamsungNfcAdapter", "Nfc Samsung service dead - attempting to recover", exc);
        ISamsungNfcAdapter serviceInterface = getServiceInterface();
        if (serviceInterface == null) {
            Log.e(
                    "SamsungNfcAdapter",
                    "Could not retrieve Nfc Samsung service during service recovery");
        } else {
            sService = serviceInterface;
        }
    }

    public static synchronized SamsungNfcAdapter getDefaultAdapter(Context context) {
        synchronized (SamsungNfcAdapter.class) {
            try {
                if (context == null) {
                    throw new IllegalArgumentException("context cannot be null");
                }
                Context applicationContext = context.getApplicationContext();
                if (applicationContext == null) {
                    throw new IllegalArgumentException(
                            "context not associated with any application(using a mock context?)");
                }
                SamsungNfcAdapter samsungNfcAdapter =
                        (SamsungNfcAdapter) mAdapter.get(applicationContext);
                if (samsungNfcAdapter == null) {
                    samsungNfcAdapter = new SamsungNfcAdapter();
                    try {
                        NfcAdapter.getDefaultAdapter(applicationContext);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    mAdapter.put(applicationContext, samsungNfcAdapter);
                }
                ISamsungNfcAdapter serviceInterface = getServiceInterface();
                sService = serviceInterface;
                if (serviceInterface != null) {
                    return samsungNfcAdapter;
                }
                Log.e("SamsungNfcAdapter", "Could not retrieve Samsung service");
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static ISamsungNfcAdapter getServiceInterface() {
        IBinder service = ServiceManager.getService("samsungnfc");
        if (service == null) {
            return null;
        }
        int i = ISamsungNfcAdapter.Stub.$r8$clinit;
        IInterface queryLocalInterface =
                service.queryLocalInterface("com.samsung.android.nfc.adapter.ISamsungNfcAdapter");
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISamsungNfcAdapter)) {
            return (ISamsungNfcAdapter) queryLocalInterface;
        }
        ISamsungNfcAdapter.Stub.Proxy proxy = new ISamsungNfcAdapter.Stub.Proxy();
        proxy.mRemote = service;
        return proxy;
    }

    public static void setWirelessChargeEnabled(boolean z) {
        try {
            ((ISamsungNfcAdapter.Stub.Proxy) sService).setWirelessChargeEnabled(z);
        } catch (RemoteException unused) {
        }
    }

    public static byte[] startCoverAuth() {
        try {
            return ((ISamsungNfcAdapter.Stub.Proxy) sService).startCoverAuth(new Binder());
        } catch (RemoteException e) {
            Log.e("SamsungNfcAdapter", "Failed to transmit authentication data");
            attemptDeadServiceRecovery(e);
            throw new IOException("Failed to transmit authentication data");
        }
    }

    public static byte[] startLedCover() {
        try {
            return ((ISamsungNfcAdapter.Stub.Proxy) sService).startLedCover(new Binder());
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return new byte[] {3};
        }
    }

    public static boolean stopCoverAuth() {
        try {
            ISamsungNfcAdapter.Stub.Proxy proxy = (ISamsungNfcAdapter.Stub.Proxy) sService;
            Parcel obtain = Parcel.obtain(proxy.mRemote);
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.samsung.android.nfc.adapter.ISamsungNfcAdapter");
                proxy.mRemote.transact(3, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readBoolean();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        } catch (RemoteException e) {
            Log.e("SamsungNfcAdapter", "Failed to stop authentication");
            attemptDeadServiceRecovery(e);
            throw new IOException("Failed to stop authentication");
        }
    }

    public static void stopLedCover() {
        try {
            ISamsungNfcAdapter.Stub.Proxy proxy = (ISamsungNfcAdapter.Stub.Proxy) sService;
            Parcel obtain = Parcel.obtain(proxy.mRemote);
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.samsung.android.nfc.adapter.ISamsungNfcAdapter");
                proxy.mRemote.transact(6, obtain, obtain2, 0);
                obtain2.readException();
                obtain2.readBoolean();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }

    public static byte[] transceiveDataWithLedCover(byte[] bArr) {
        try {
            return ((ISamsungNfcAdapter.Stub.Proxy) sService).transceiveLedCover(bArr);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return new byte[] {3};
        }
    }
}
