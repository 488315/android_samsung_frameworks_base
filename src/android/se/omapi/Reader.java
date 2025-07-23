package android.se.omapi;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class Reader {
    private static final String TAG = "OMAPI.Reader";
    private final Object mLock = new Object();
    private final String mName;
    private ISecureElementReader mReader;
    private final SEService mService;

    Reader(SEService sEService, String str, ISecureElementReader iSecureElementReader) {
        if (iSecureElementReader == null || sEService == null || str == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.mName = str;
        this.mService = sEService;
        this.mReader = iSecureElementReader;
    }

    public String getName() {
        return this.mName;
    }

    public Session openSession() throws IOException {
        Session session;
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service is not connected");
        }
        synchronized (this.mLock) {
            try {
                try {
                    ISecureElementSession openSession = this.mReader.openSession();
                    if (openSession == null) {
                        throw new IOException("service session is null.");
                    }
                    session = new Session(this.mService, openSession, this);
                } catch (RemoteException e) {
                    throw new IllegalStateException(e.getMessage());
                } catch (ServiceSpecificException e2) {
                    throw new IOException(e2.getMessage());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return session;
    }

    public boolean isSecureElementPresent() {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service is not connected");
        }
        try {
            return this.mReader.isSecureElementPresent();
        } catch (RemoteException unused) {
            throw new IllegalStateException("Error in isSecureElementPresent()");
        }
    }

    public SEService getSEService() {
        return this.mService;
    }

    public void closeSessions() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service is not connected");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mReader.closeSessions();
            } catch (RemoteException unused) {
            }
        }
    }

    @SystemApi
    public boolean reset() {
        boolean reset;
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service is not connected");
            return false;
        }
        synchronized (this.mLock) {
            try {
                try {
                    closeSessions();
                    reset = this.mReader.reset();
                } catch (RemoteException unused) {
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return reset;
    }
}
