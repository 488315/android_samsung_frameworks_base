package android.se.omapi;

import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class Channel implements java.nio.channels.Channel {
    private static final String TAG = "OMAPI.Channel";
    private final ISecureElementChannel mChannel;
    private final Object mLock = new Object();
    private final SEService mService;
    private Session mSession;

    Channel(SEService sEService, Session session, ISecureElementChannel iSecureElementChannel) {
        if (sEService == null || session == null || iSecureElementChannel == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.mService = sEService;
        this.mSession = session;
        this.mChannel = iSecureElementChannel;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (isOpen()) {
            synchronized (this.mLock) {
                try {
                    this.mChannel.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error closing channel", e);
                }
            }
        }
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        if (!this.mService.isConnected()) {
            Log.e(TAG, "service not connected to system");
            return false;
        }
        try {
            return !this.mChannel.isClosed();
        } catch (RemoteException unused) {
            Log.e(TAG, "Exception in isClosed()");
            return false;
        }
    }

    public boolean isBasicChannel() {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        try {
            return this.mChannel.isBasicChannel();
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public byte[] transmit(byte[] bArr) throws IOException {
        byte[] transmit;
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        synchronized (this.mLock) {
            try {
                transmit = this.mChannel.transmit(bArr);
                if (transmit == null) {
                    throw new IOException("Error in communicating with Secure Element");
                }
            } catch (RemoteException e) {
                throw new IllegalStateException(e.getMessage());
            } catch (ServiceSpecificException e2) {
                throw new IOException(e2.getMessage());
            }
        }
        return transmit;
    }

    public Session getSession() {
        return this.mSession;
    }

    public byte[] getSelectResponse() {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        try {
            byte[] selectResponse = this.mChannel.getSelectResponse();
            if (selectResponse == null || selectResponse.length != 0) {
                return selectResponse;
            }
            return null;
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public boolean selectNext() throws IOException {
        boolean selectNext;
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        try {
            synchronized (this.mLock) {
                selectNext = this.mChannel.selectNext();
            }
            return selectNext;
        } catch (RemoteException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (ServiceSpecificException e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
