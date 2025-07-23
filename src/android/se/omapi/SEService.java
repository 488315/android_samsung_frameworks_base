package android.se.omapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.se.omapi.ISecureElementListener;
import android.se.omapi.ISecureElementService;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class SEService {
    public static final String ACTION_SECURE_ELEMENT_STATE_CHANGED = "android.se.omapi.action.SECURE_ELEMENT_STATE_CHANGED";
    public static final String EXTRA_READER_NAME = "android.se.omapi.extra.READER_NAME";
    public static final String EXTRA_READER_STATE = "android.se.omapi.extra.READER_STATE";
    public static final int IO_ERROR = 1;
    public static final int NO_SUCH_ELEMENT_ERROR = 2;
    private static final String TAG = "OMAPI.SEService";
    private static final String UICC_TERMINAL = "SIM";
    private ServiceConnection mConnection;
    private final Context mContext;
    private volatile ISecureElementService mSecureElementService;
    private SEListener mSEListener = new SEListener();
    private final Object mLock = new Object();
    private final HashMap<String, Reader> mReaders = new HashMap<>();

    public interface OnConnectedListener {
        void onConnected();
    }

    private class SEListener extends ISecureElementListener.Stub {
        public Executor mExecutor;
        public OnConnectedListener mListener;

        @Override // android.se.omapi.ISecureElementListener.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.se.omapi.ISecureElementListener
        public int getInterfaceVersion() {
            return 1;
        }

        private SEListener(SEService sEService) {
            this.mListener = null;
            this.mExecutor = null;
        }

        public void onConnected() {
            Executor executor;
            if (this.mListener == null || (executor = this.mExecutor) == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: android.se.omapi.SEService.SEListener.1
                @Override // java.lang.Runnable
                public void run() {
                    SEListener.this.mListener.onConnected();
                }
            });
        }

        @Override // android.se.omapi.ISecureElementListener
        public String getInterfaceHash() {
            return "894069bcfe4f35ceb2088278ddf87c83adee8014";
        }
    }

    public SEService(Context context, Executor executor, OnConnectedListener onConnectedListener) {
        if (context == null || onConnectedListener == null || executor == null) {
            throw new NullPointerException("Arguments must not be null");
        }
        this.mContext = context;
        this.mSEListener.mListener = onConnectedListener;
        this.mSEListener.mExecutor = executor;
        this.mConnection = new ServiceConnection() { // from class: android.se.omapi.SEService.1
            @Override // android.content.ServiceConnection
            public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                SEService.this.mSecureElementService = ISecureElementService.Stub.asInterface(iBinder);
                if (SEService.this.mSEListener != null) {
                    SEService.this.mSEListener.onConnected();
                }
                Log.i(SEService.TAG, "Service onServiceConnected");
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                SEService.this.mSecureElementService = null;
                Log.i(SEService.TAG, "Service onServiceDisconnected");
            }
        };
        Intent intent = new Intent(ISecureElementService.class.getName());
        intent.setClassName("com.android.se", "com.android.se.SecureElementService");
        if (context.bindService(intent, this.mConnection, 1)) {
            Log.i(TAG, "bindService successful");
        }
    }

    public boolean isConnected() {
        return this.mSecureElementService != null;
    }

    public Reader[] getReaders() {
        loadReaders();
        return (Reader[]) this.mReaders.values().toArray(new Reader[0]);
    }

    public Reader getUiccReader(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("slotNumber should be larger than 0");
        }
        loadReaders();
        String str = UICC_TERMINAL + i;
        Reader reader = this.mReaders.get(str);
        if (reader != null) {
            return reader;
        }
        throw new IllegalArgumentException("Reader:" + str + " doesn't exist");
    }

    public void shutdown() {
        synchronized (this.mLock) {
            if (this.mSecureElementService != null) {
                Iterator<Reader> it = this.mReaders.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().closeSessions();
                    } catch (Exception unused) {
                    }
                }
            }
            try {
                this.mContext.unbindService(this.mConnection);
            } catch (IllegalArgumentException unused2) {
            }
            this.mSecureElementService = null;
        }
    }

    public String getVersion() {
        return "3.3";
    }

    ISecureElementListener getListener() {
        return this.mSEListener;
    }

    private ISecureElementReader getReader(String str) {
        try {
            return this.mSecureElementService.getReader(str);
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void loadReaders() {
        if (this.mSecureElementService == null) {
            throw new IllegalStateException("service not connected to system");
        }
        try {
            for (String str : this.mSecureElementService.getReaders()) {
                if (this.mReaders.get(str) == null) {
                    try {
                        this.mReaders.put(str, new Reader(this, str, getReader(str)));
                    } catch (Exception e) {
                        Log.e(TAG, "Error adding Reader: " + str, e);
                    }
                }
            }
        } catch (RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }
}
