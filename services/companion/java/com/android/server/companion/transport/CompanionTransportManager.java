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
import com.android.server.companion.AssociationStore;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class CompanionTransportManager {
  public final AssociationStore mAssociationStore;
  public final Context mContext;
  public boolean mSecureTransportEnabled = true;
  public final SparseArray mTransports = new SparseArray();
  public final RemoteCallbackList mTransportsListeners = new RemoteCallbackList();
  public final SparseArray mMessageListeners = new SparseArray();

  public CompanionTransportManager(Context context, AssociationStore associationStore) {
    this.mContext = context;
    this.mAssociationStore = associationStore;
  }

  public void addListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
    this.mMessageListeners.put(i, iOnMessageReceivedListener);
    synchronized (this.mTransports) {
      for (int i2 = 0; i2 < this.mTransports.size(); i2++) {
        ((Transport) this.mTransports.valueAt(i2)).addListener(i, iOnMessageReceivedListener);
      }
    }
  }

  public void addListener(final IOnTransportsChangedListener iOnTransportsChangedListener) {
    Slog.i("CDM_CompanionTransportManager", "Registering OnTransportsChangedListener");
    this.mTransportsListeners.register(iOnTransportsChangedListener);
    final ArrayList arrayList = new ArrayList();
    synchronized (this.mTransports) {
      for (int i = 0; i < this.mTransports.size(); i++) {
        AssociationInfo associationById =
            this.mAssociationStore.getAssociationById(this.mTransports.keyAt(i));
        if (associationById != null) {
          arrayList.add(associationById);
        }
      }
    }
    this.mTransportsListeners.broadcast(
        new Consumer() { // from class:
          // com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            CompanionTransportManager.lambda$addListener$0(
                iOnTransportsChangedListener, arrayList, (IOnTransportsChangedListener) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$addListener$0(
      IOnTransportsChangedListener iOnTransportsChangedListener,
      List list,
      IOnTransportsChangedListener iOnTransportsChangedListener2) {
    if (iOnTransportsChangedListener2 == iOnTransportsChangedListener) {
      try {
        iOnTransportsChangedListener.onTransportsChanged(list);
      } catch (RemoteException unused) {
      }
    }
  }

  public void removeListener(IOnTransportsChangedListener iOnTransportsChangedListener) {
    this.mTransportsListeners.unregister(iOnTransportsChangedListener);
  }

  public void removeListener(int i, IOnMessageReceivedListener iOnMessageReceivedListener) {
    this.mMessageListeners.remove(i);
  }

  public void sendMessage(int i, byte[] bArr, int[] iArr) {
    Slog.i(
        "CDM_CompanionTransportManager",
        "Sending message 0x" + Integer.toHexString(i) + " data length " + bArr.length);
    synchronized (this.mTransports) {
      for (int i2 = 0; i2 < iArr.length; i2++) {
        if (this.mTransports.contains(iArr[i2])) {
          ((Transport) this.mTransports.get(iArr[i2])).requestForResponse(i, bArr);
        }
      }
    }
  }

  public void attachSystemDataTransport(
      String str, int i, int i2, ParcelFileDescriptor parcelFileDescriptor) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.DELIVER_COMPANION_MESSAGES", "CDM_CompanionTransportManager");
    synchronized (this.mTransports) {
      if (this.mTransports.contains(i2)) {
        detachSystemDataTransport(str, i, i2);
      }
      initializeTransport(i2, parcelFileDescriptor, null);
      notifyOnTransportsChanged();
    }
  }

  public void detachSystemDataTransport(String str, int i, int i2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.DELIVER_COMPANION_MESSAGES", "CDM_CompanionTransportManager");
    synchronized (this.mTransports) {
      Transport transport = (Transport) this.mTransports.get(i2);
      if (transport != null) {
        this.mTransports.delete(i2);
        transport.stop();
      }
      notifyOnTransportsChanged();
    }
  }

  public final void notifyOnTransportsChanged() {
    final ArrayList arrayList = new ArrayList();
    synchronized (this.mTransports) {
      for (int i = 0; i < this.mTransports.size(); i++) {
        AssociationInfo associationById =
            this.mAssociationStore.getAssociationById(this.mTransports.keyAt(i));
        if (associationById != null) {
          arrayList.add(associationById);
        }
      }
    }
    this.mTransportsListeners.broadcast(
        new Consumer() { // from class:
          // com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            CompanionTransportManager.lambda$notifyOnTransportsChanged$1(
                arrayList, (IOnTransportsChangedListener) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$notifyOnTransportsChanged$1(
      List list, IOnTransportsChangedListener iOnTransportsChangedListener) {
    try {
      iOnTransportsChangedListener.onTransportsChanged(list);
    } catch (RemoteException unused) {
    }
  }

  public final void initializeTransport(
      int i, ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
    Transport secureTransport;
    Slog.i("CDM_CompanionTransportManager", "Initializing transport");
    if (!isSecureTransportEnabled()) {
      Slog.i("CDM_CompanionTransportManager", "Secure channel is disabled. Creating raw transport");
      secureTransport = new RawTransport(i, parcelFileDescriptor, this.mContext);
    } else if (Build.isDebuggable()) {
      Slog.d("CDM_CompanionTransportManager", "Creating an unauthenticated secure channel");
      secureTransport =
          new SecureTransport(
              i, parcelFileDescriptor, this.mContext, "CDM".getBytes(StandardCharsets.UTF_8), null);
    } else if (bArr != null) {
      Slog.d("CDM_CompanionTransportManager", "Creating a PSK-authenticated secure channel");
      secureTransport = new SecureTransport(i, parcelFileDescriptor, this.mContext, bArr, null);
    } else {
      Slog.d("CDM_CompanionTransportManager", "Creating a secure channel");
      secureTransport = new SecureTransport(i, parcelFileDescriptor, this.mContext);
    }
    addMessageListenersToTransport(secureTransport);
    secureTransport.setOnTransportClosedListener(
        new Transport.OnTransportClosedListener() { // from class:
          // com.android.server.companion.transport.CompanionTransportManager$$ExternalSyntheticLambda2
          @Override // com.android.server.companion.transport.Transport.OnTransportClosedListener
          public final void onClosed(Transport transport) {
            CompanionTransportManager.this.detachSystemDataTransport(transport);
          }
        });
    secureTransport.start();
    synchronized (this.mTransports) {
      this.mTransports.put(i, secureTransport);
    }
  }

  public Future requestPermissionRestore(int i, byte[] bArr) {
    synchronized (this.mTransports) {
      Transport transport = (Transport) this.mTransports.get(i);
      if (transport == null) {
        return CompletableFuture.failedFuture(new IOException("Missing transport"));
      }
      return transport.requestForResponse(1669491075, bArr);
    }
  }

  public void enableSecureTransport(boolean z) {
    this.mSecureTransportEnabled = z;
  }

  public EmulatedTransport createEmulatedTransport(int i) {
    EmulatedTransport emulatedTransport;
    synchronized (this.mTransports) {
      emulatedTransport =
          new EmulatedTransport(i, new ParcelFileDescriptor(new FileDescriptor()), this.mContext);
      addMessageListenersToTransport(emulatedTransport);
      this.mTransports.put(i, emulatedTransport);
      notifyOnTransportsChanged();
    }
    return emulatedTransport;
  }

  public class EmulatedTransport extends RawTransport {
    public EmulatedTransport(int i, ParcelFileDescriptor parcelFileDescriptor, Context context) {
      super(i, parcelFileDescriptor, context);
    }

    public void processMessage(int i, int i2, byte[] bArr) {
      handleMessage(i, i2, bArr);
    }

    @Override // com.android.server.companion.transport.RawTransport,
    // com.android.server.companion.transport.Transport
    public void sendMessage(int i, int i2, byte[] bArr) {
      Slog.e(
          "CDM_CompanionTransport",
          "Black-holing emulated message type 0x"
              + Integer.toHexString(i)
              + " sequence "
              + i2
              + " length "
              + bArr.length
              + " to association "
              + this.mAssociationId);
    }
  }

  public final boolean isSecureTransportEnabled() {
    return !Build.IS_DEBUGGABLE || this.mSecureTransportEnabled;
  }

  public final void addMessageListenersToTransport(Transport transport) {
    for (int i = 0; i < this.mMessageListeners.size(); i++) {
      transport.addListener(
          this.mMessageListeners.keyAt(i),
          (IOnMessageReceivedListener) this.mMessageListeners.valueAt(i));
    }
  }

  public void detachSystemDataTransport(Transport transport) {
    AssociationInfo associationById =
        this.mAssociationStore.getAssociationById(transport.mAssociationId);
    if (associationById != null) {
      detachSystemDataTransport(
          associationById.getPackageName(), associationById.getUserId(), associationById.getId());
    }
  }
}
