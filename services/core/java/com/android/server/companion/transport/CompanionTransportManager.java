package com.android.server.companion.transport;

import android.companion.AssociationInfo;
import android.companion.IOnMessageReceivedListener;
import android.companion.IOnTransportsChangedListener;
import android.content.Context;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.companion.association.AssociationStore;
import java.io.FileDescriptor;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CompanionTransportManager {
    public final AssociationStore mAssociationStore;
    public final Context mContext;
    public boolean mSecureTransportEnabled = true;
    public final SparseArray mTransports = new SparseArray();
    public final RemoteCallbackList mTransportsListeners = new RemoteCallbackList();
    public final SparseArray mMessageListeners = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EmulatedTransport extends RawTransport {
        @Override // com.android.server.companion.transport.RawTransport, com.android.server.companion.transport.Transport
        public final void sendMessage(int i, int i2, byte[] bArr) {
            StringBuilder sb = new StringBuilder("Black-holing emulated message type 0x");
            sb.append(Integer.toHexString(i));
            sb.append(" sequence ");
            sb.append(i2);
            sb.append(" length ");
            sb.append(bArr.length);
            sb.append(" to association ");
            VaultKeeperService$$ExternalSyntheticOutline0.m(sb, this.mAssociationId, "CDM_CompanionTransport");
        }
    }

    public CompanionTransportManager(Context context, AssociationStore associationStore) {
        this.mContext = context;
        this.mAssociationStore = associationStore;
    }

    public final void addListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
        this.mMessageListeners.put(i, iOnMessageReceivedListener);
        synchronized (this.mTransports) {
            for (int i2 = 0; i2 < this.mTransports.size(); i2++) {
                try {
                    ((HashMap) ((Transport) this.mTransports.valueAt(i2)).mListeners).put(Integer.valueOf(i), iOnMessageReceivedListener);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void addListener(final IOnTransportsChangedListener iOnTransportsChangedListener) {
        Slog.i("CDM_CompanionTransportManager", "Registering OnTransportsChangedListener");
        synchronized (this.mTransportsListeners) {
            this.mTransportsListeners.register(iOnTransportsChangedListener);
            this.mTransportsListeners.broadcast(new Consumer() { // from class: com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CompanionTransportManager companionTransportManager = CompanionTransportManager.this;
                    IOnTransportsChangedListener iOnTransportsChangedListener2 = iOnTransportsChangedListener;
                    IOnTransportsChangedListener iOnTransportsChangedListener3 = (IOnTransportsChangedListener) obj;
                    companionTransportManager.getClass();
                    if (iOnTransportsChangedListener3 == iOnTransportsChangedListener2) {
                        try {
                            iOnTransportsChangedListener2.onTransportsChanged(companionTransportManager.getAssociationsWithTransport());
                        } catch (RemoteException unused) {
                        }
                    }
                }
            });
        }
    }

    public final void addMessageListenersToTransport(Transport transport) {
        for (int i = 0; i < this.mMessageListeners.size(); i++) {
            int keyAt = this.mMessageListeners.keyAt(i);
            IOnMessageReceivedListener iOnMessageReceivedListener = (IOnMessageReceivedListener) this.mMessageListeners.valueAt(i);
            ((HashMap) transport.mListeners).put(Integer.valueOf(keyAt), iOnMessageReceivedListener);
        }
    }

    public final EmulatedTransport createEmulatedTransport(int i) {
        EmulatedTransport emulatedTransport;
        synchronized (this.mTransports) {
            emulatedTransport = new EmulatedTransport(i, new ParcelFileDescriptor(new FileDescriptor()));
            addMessageListenersToTransport(emulatedTransport);
            this.mTransports.put(i, emulatedTransport);
            notifyOnTransportsChanged();
        }
        return emulatedTransport;
    }

    public final void detachSystemDataTransport(int i) {
        BootReceiver$$ExternalSyntheticOutline0.m(i, "Detaching transport for association id=[", "]...", "CDM_CompanionTransportManager");
        this.mAssociationStore.getAssociationWithCallerChecks(i);
        synchronized (this.mTransports) {
            try {
                Transport transport = (Transport) this.mTransports.removeReturnOld(i);
                if (transport == null) {
                    return;
                }
                transport.stop();
                notifyOnTransportsChanged();
                Slog.i("CDM_CompanionTransportManager", "Transport detached.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getAssociationsWithTransport() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mTransports) {
            for (int i = 0; i < this.mTransports.size(); i++) {
                try {
                    AssociationInfo associationById = this.mAssociationStore.getAssociationById(this.mTransports.keyAt(i));
                    if (associationById != null) {
                        arrayList.add(associationById);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    public final void initializeTransport(int i, ParcelFileDescriptor parcelFileDescriptor) {
        Transport secureTransport;
        Slog.i("CDM_CompanionTransportManager", "Initializing transport");
        if (!this.mSecureTransportEnabled) {
            Slog.i("CDM_CompanionTransportManager", "Secure channel is disabled. Creating raw transport");
            secureTransport = new RawTransport(i, parcelFileDescriptor);
        } else if (Build.isDebuggable()) {
            Slog.d("CDM_CompanionTransportManager", "Creating an unauthenticated secure channel");
            secureTransport = new SecureTransport(i, parcelFileDescriptor, "CDM".getBytes(StandardCharsets.UTF_8));
        } else {
            Slog.d("CDM_CompanionTransportManager", "Creating a secure channel");
            secureTransport = new SecureTransport(i, parcelFileDescriptor, this.mContext);
        }
        addMessageListenersToTransport(secureTransport);
        secureTransport.mOnTransportClosed = new CompanionTransportManager$$ExternalSyntheticLambda2(this);
        secureTransport.start();
        synchronized (this.mTransports) {
            this.mTransports.put(i, secureTransport);
        }
    }

    public final void notifyOnTransportsChanged() {
        synchronized (this.mTransportsListeners) {
            this.mTransportsListeners.broadcast(new Consumer() { // from class: com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CompanionTransportManager companionTransportManager = CompanionTransportManager.this;
                    IOnTransportsChangedListener iOnTransportsChangedListener = (IOnTransportsChangedListener) obj;
                    companionTransportManager.getClass();
                    try {
                        iOnTransportsChangedListener.onTransportsChanged(companionTransportManager.getAssociationsWithTransport());
                    } catch (RemoteException unused) {
                    }
                }
            });
        }
    }

    public final void sendMessage(int i, byte[] bArr, int[] iArr) {
        StringBuilder sb = new StringBuilder("Sending message 0x");
        BatteryService$$ExternalSyntheticOutline0.m(i, sb, " data length ");
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, bArr.length, "CDM_CompanionTransportManager");
        synchronized (this.mTransports) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                try {
                    if (this.mTransports.contains(iArr[i2])) {
                        ((Transport) this.mTransports.get(iArr[i2])).sendMessage(i, bArr);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
