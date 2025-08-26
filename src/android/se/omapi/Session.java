package android.se.omapi;

import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.io.IOException;
import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
public final class Session {
    private static final String TAG = "OMAPI.Session";
    private final Object mLock = new Object();
    private final Reader mReader;
    private final SEService mService;
    private final ISecureElementSession mSession;

    Session(SEService sEService, ISecureElementSession iSecureElementSession, Reader reader) {
        if (sEService == null || reader == null || iSecureElementSession == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.mService = sEService;
        this.mReader = reader;
        this.mSession = iSecureElementSession;
    }

    public Reader getReader() {
        return this.mReader;
    }

    public byte[] getATR() {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        try {
            return this.mSession.getAtr();
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void close() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service not connected to system");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mSession.close();
            } catch (RemoteException e) {
                Log.e(TAG, "Error closing session", e);
            }
        }
    }

    public boolean isClosed() {
        try {
            return this.mSession.isClosed();
        } catch (RemoteException unused) {
            return true;
        }
    }

    public void closeChannels() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service not connected to system");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mSession.closeChannels();
            } catch (RemoteException e) {
                Log.e(TAG, "Error closing channels", e);
            }
        }
    }

    public Channel openBasicChannel(byte[] bArr, byte b) throws IOException {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        synchronized (this.mLock) {
            try {
                try {
                    ISecureElementChannel iSecureElementChannelOpenBasicChannel = this.mSession.openBasicChannel(bArr, b, this.mReader.getSEService().getListener());
                    if (iSecureElementChannelOpenBasicChannel == null) {
                        return null;
                    }
                    return new Channel(this.mService, this, iSecureElementChannelOpenBasicChannel);
                } catch (RemoteException e) {
                    throw new IllegalStateException(e.getMessage());
                } catch (ServiceSpecificException e2) {
                    if (e2.errorCode == 1) {
                        throw new IOException(e2.getMessage());
                    }
                    if (e2.errorCode == 2) {
                        throw new NoSuchElementException(e2.getMessage());
                    }
                    throw new IllegalStateException(e2.getMessage());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Channel openBasicChannel(byte[] bArr) throws IOException {
        return openBasicChannel(bArr, (byte) 0);
    }

    public Channel openLogicalChannel(byte[] bArr, byte b) throws IOException {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        synchronized (this.mLock) {
            try {
                try {
                    ISecureElementChannel iSecureElementChannelOpenLogicalChannel = this.mSession.openLogicalChannel(bArr, b, this.mReader.getSEService().getListener());
                    if (iSecureElementChannelOpenLogicalChannel == null) {
                        return null;
                    }
                    return new Channel(this.mService, this, iSecureElementChannelOpenLogicalChannel);
                } catch (RemoteException e) {
                    throw new IllegalStateException(e.getMessage());
                } catch (ServiceSpecificException e2) {
                    if (e2.errorCode == 1) {
                        throw new IOException(e2.getMessage());
                    }
                    if (e2.errorCode == 2) {
                        throw new NoSuchElementException(e2.getMessage());
                    }
                    throw new IllegalStateException(e2.getMessage());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Channel openLogicalChannel(byte[] bArr) throws IOException {
        return openLogicalChannel(bArr, (byte) 0);
    }
}
