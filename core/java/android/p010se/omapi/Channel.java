package android.p010se.omapi;

import android.p009os.RemoteException;
import android.p009os.ServiceSpecificException;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class Channel implements java.nio.channels.Channel {
    private static final String TAG = "OMAPI.Channel";
    private final ISecureElementChannel mChannel;
    private final Object mLock = new Object();
    private final SEService mService;
    private Session mSession;

    Channel(SEService service, Session session, ISecureElementChannel channel) {
        if (service == null || session == null || channel == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.mService = service;
        this.mSession = session;
        this.mChannel = channel;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (isOpen()) {
            synchronized (this.mLock) {
                try {
                    this.mChannel.close();
                } catch (Exception e) {
                    Log.m97e(TAG, "Error closing channel", e);
                }
            }
        }
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        if (!this.mService.isConnected()) {
            Log.m96e(TAG, "service not connected to system");
            return false;
        }
        try {
            return !this.mChannel.isClosed();
        } catch (RemoteException e) {
            Log.m96e(TAG, "Exception in isClosed()");
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

    public byte[] transmit(byte[] command) throws IOException {
        byte[] response;
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        synchronized (this.mLock) {
            try {
                response = this.mChannel.transmit(command);
                if (response == null) {
                    throw new IOException("Error in communicating with Secure Element");
                }
            } catch (RemoteException e) {
                throw new IllegalStateException(e.getMessage());
            } catch (ServiceSpecificException e2) {
                throw new IOException(e2.getMessage());
            }
        }
        return response;
    }

    public Session getSession() {
        return this.mSession;
    }

    public byte[] getSelectResponse() {
        if (!this.mService.isConnected()) {
            throw new IllegalStateException("service not connected to system");
        }
        try {
            byte[] response = this.mChannel.getSelectResponse();
            if (response != null && response.length == 0) {
                return null;
            }
            return response;
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
