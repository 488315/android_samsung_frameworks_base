package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.media.IResourceManagerObserver;
import com.samsung.android.media.IResourceManagerObserverClient;
import com.samsung.android.media.MediaInfoParcel;

/* loaded from: classes2.dex */
public interface IResourceManagerService extends IInterface {
  public static final String DESCRIPTOR = "android.media.IResourceManagerService";
  public static final String kPolicySupportsMultipleSecureCodecs =
      "supports-multiple-secure-codecs";
  public static final String kPolicySupportsSecureWithNonSecureCodec =
      "supports-secure-with-non-secure-codec";

  void addMediaInfo(
      int i,
      int i2,
      long j,
      IResourceManagerClient iResourceManagerClient,
      MediaInfoParcel[] mediaInfoParcelArr)
      throws RemoteException;

  void addResource(
      ClientInfoParcel clientInfoParcel,
      IResourceManagerClient iResourceManagerClient,
      MediaResourceParcel[] mediaResourceParcelArr)
      throws RemoteException;

  void config(MediaResourcePolicyParcel[] mediaResourcePolicyParcelArr) throws RemoteException;

  IResourceManagerObserver createResourceObserver(
      IResourceManagerObserverClient iResourceManagerObserverClient) throws RemoteException;

  float getRemainedFrameRateFor(String str, int i, int i2) throws RemoteException;

  float getSupportedFrameRateFor(String str, int i, int i2) throws RemoteException;

  void markClientForPendingRemoval(ClientInfoParcel clientInfoParcel) throws RemoteException;

  void notifyClientConfigChanged(ClientConfigParcel clientConfigParcel) throws RemoteException;

  void notifyClientCreated(ClientInfoParcel clientInfoParcel) throws RemoteException;

  void notifyClientStarted(ClientConfigParcel clientConfigParcel) throws RemoteException;

  void notifyClientStopped(ClientConfigParcel clientConfigParcel) throws RemoteException;

  void overridePid(int i, int i2) throws RemoteException;

  void overrideProcessInfo(IResourceManagerClient iResourceManagerClient, int i, int i2, int i3)
      throws RemoteException;

  boolean reclaimResource(
      ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr)
      throws RemoteException;

  void reclaimResourcesFromClientsPendingRemoval(int i) throws RemoteException;

  void removeClient(ClientInfoParcel clientInfoParcel) throws RemoteException;

  void removeResource(
      ClientInfoParcel clientInfoParcel, MediaResourceParcel[] mediaResourceParcelArr)
      throws RemoteException;

  void sendCapacityError(
      int i,
      int i2,
      long j,
      IResourceManagerClient iResourceManagerClient,
      MediaInfoParcel[] mediaInfoParcelArr)
      throws RemoteException;

  void setCodecState(int i, int i2, long j, IResourceManagerClient iResourceManagerClient, int i3)
      throws RemoteException;

  public static class Default implements IResourceManagerService {
    @Override // android.media.IResourceManagerService
    public void config(MediaResourcePolicyParcel[] policies) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void addResource(
        ClientInfoParcel clientInfo, IResourceManagerClient client, MediaResourceParcel[] resources)
        throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void removeResource(ClientInfoParcel clientInfo, MediaResourceParcel[] resources)
        throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void removeClient(ClientInfoParcel clientInfo) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public boolean reclaimResource(ClientInfoParcel clientInfo, MediaResourceParcel[] resources)
        throws RemoteException {
      return false;
    }

    @Override // android.media.IResourceManagerService
    public void overridePid(int originalPid, int newPid) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void overrideProcessInfo(
        IResourceManagerClient client, int pid, int procState, int oomScore)
        throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void markClientForPendingRemoval(ClientInfoParcel clientInfo) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void reclaimResourcesFromClientsPendingRemoval(int pid) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void notifyClientCreated(ClientInfoParcel clientInfo) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void notifyClientStarted(ClientConfigParcel clientConfig) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void notifyClientStopped(ClientConfigParcel clientConfig) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void notifyClientConfigChanged(ClientConfigParcel clientConfig) throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public IResourceManagerObserver createResourceObserver(IResourceManagerObserverClient client)
        throws RemoteException {
      return null;
    }

    @Override // android.media.IResourceManagerService
    public void addMediaInfo(
        int uid, int pid, long clientId, IResourceManagerClient client, MediaInfoParcel[] mediaInfo)
        throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void setCodecState(
        int uid, int pid, long clientId, IResourceManagerClient client, int state)
        throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public void sendCapacityError(
        int uid, int pid, long clientId, IResourceManagerClient client, MediaInfoParcel[] mediaInfo)
        throws RemoteException {}

    @Override // android.media.IResourceManagerService
    public float getSupportedFrameRateFor(String componentName, int width, int height)
        throws RemoteException {
      return 0.0f;
    }

    @Override // android.media.IResourceManagerService
    public float getRemainedFrameRateFor(String componentName, int width, int height)
        throws RemoteException {
      return 0.0f;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IResourceManagerService {
    static final int TRANSACTION_addMediaInfo = 15;
    static final int TRANSACTION_addResource = 2;
    static final int TRANSACTION_config = 1;
    static final int TRANSACTION_createResourceObserver = 14;
    static final int TRANSACTION_getRemainedFrameRateFor = 19;
    static final int TRANSACTION_getSupportedFrameRateFor = 18;
    static final int TRANSACTION_markClientForPendingRemoval = 8;
    static final int TRANSACTION_notifyClientConfigChanged = 13;
    static final int TRANSACTION_notifyClientCreated = 10;
    static final int TRANSACTION_notifyClientStarted = 11;
    static final int TRANSACTION_notifyClientStopped = 12;
    static final int TRANSACTION_overridePid = 6;
    static final int TRANSACTION_overrideProcessInfo = 7;
    static final int TRANSACTION_reclaimResource = 5;
    static final int TRANSACTION_reclaimResourcesFromClientsPendingRemoval = 9;
    static final int TRANSACTION_removeClient = 4;
    static final int TRANSACTION_removeResource = 3;
    static final int TRANSACTION_sendCapacityError = 17;
    static final int TRANSACTION_setCodecState = 16;

    public Stub() {
      attachInterface(this, IResourceManagerService.DESCRIPTOR);
    }

    public static IResourceManagerService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IResourceManagerService.DESCRIPTOR);
      if (iin != null && (iin instanceof IResourceManagerService)) {
        return (IResourceManagerService) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "config";
        case 2:
          return "addResource";
        case 3:
          return "removeResource";
        case 4:
          return "removeClient";
        case 5:
          return "reclaimResource";
        case 6:
          return "overridePid";
        case 7:
          return "overrideProcessInfo";
        case 8:
          return "markClientForPendingRemoval";
        case 9:
          return "reclaimResourcesFromClientsPendingRemoval";
        case 10:
          return "notifyClientCreated";
        case 11:
          return "notifyClientStarted";
        case 12:
          return "notifyClientStopped";
        case 13:
          return "notifyClientConfigChanged";
        case 14:
          return "createResourceObserver";
        case 15:
          return "addMediaInfo";
        case 16:
          return "setCodecState";
        case 17:
          return "sendCapacityError";
        case 18:
          return "getSupportedFrameRateFor";
        case 19:
          return "getRemainedFrameRateFor";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IResourceManagerService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IResourceManagerService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              MediaResourcePolicyParcel[] _arg0 =
                  (MediaResourcePolicyParcel[])
                      data.createTypedArray(MediaResourcePolicyParcel.CREATOR);
              data.enforceNoDataAvail();
              config(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              ClientInfoParcel _arg02 =
                  (ClientInfoParcel) data.readTypedObject(ClientInfoParcel.CREATOR);
              IResourceManagerClient _arg1 =
                  IResourceManagerClient.Stub.asInterface(data.readStrongBinder());
              MediaResourceParcel[] _arg2 =
                  (MediaResourceParcel[]) data.createTypedArray(MediaResourceParcel.CREATOR);
              data.enforceNoDataAvail();
              addResource(_arg02, _arg1, _arg2);
              reply.writeNoException();
              return true;
            case 3:
              ClientInfoParcel _arg03 =
                  (ClientInfoParcel) data.readTypedObject(ClientInfoParcel.CREATOR);
              MediaResourceParcel[] _arg12 =
                  (MediaResourceParcel[]) data.createTypedArray(MediaResourceParcel.CREATOR);
              data.enforceNoDataAvail();
              removeResource(_arg03, _arg12);
              reply.writeNoException();
              return true;
            case 4:
              ClientInfoParcel _arg04 =
                  (ClientInfoParcel) data.readTypedObject(ClientInfoParcel.CREATOR);
              data.enforceNoDataAvail();
              removeClient(_arg04);
              reply.writeNoException();
              return true;
            case 5:
              ClientInfoParcel _arg05 =
                  (ClientInfoParcel) data.readTypedObject(ClientInfoParcel.CREATOR);
              MediaResourceParcel[] _arg13 =
                  (MediaResourceParcel[]) data.createTypedArray(MediaResourceParcel.CREATOR);
              data.enforceNoDataAvail();
              boolean _result = reclaimResource(_arg05, _arg13);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 6:
              int _arg06 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              overridePid(_arg06, _arg14);
              reply.writeNoException();
              return true;
            case 7:
              IResourceManagerClient _arg07 =
                  IResourceManagerClient.Stub.asInterface(data.readStrongBinder());
              int _arg15 = data.readInt();
              int _arg22 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              overrideProcessInfo(_arg07, _arg15, _arg22, _arg3);
              reply.writeNoException();
              return true;
            case 8:
              ClientInfoParcel _arg08 =
                  (ClientInfoParcel) data.readTypedObject(ClientInfoParcel.CREATOR);
              data.enforceNoDataAvail();
              markClientForPendingRemoval(_arg08);
              reply.writeNoException();
              return true;
            case 9:
              int _arg09 = data.readInt();
              data.enforceNoDataAvail();
              reclaimResourcesFromClientsPendingRemoval(_arg09);
              reply.writeNoException();
              return true;
            case 10:
              ClientInfoParcel _arg010 =
                  (ClientInfoParcel) data.readTypedObject(ClientInfoParcel.CREATOR);
              data.enforceNoDataAvail();
              notifyClientCreated(_arg010);
              reply.writeNoException();
              return true;
            case 11:
              ClientConfigParcel _arg011 =
                  (ClientConfigParcel) data.readTypedObject(ClientConfigParcel.CREATOR);
              data.enforceNoDataAvail();
              notifyClientStarted(_arg011);
              reply.writeNoException();
              return true;
            case 12:
              ClientConfigParcel _arg012 =
                  (ClientConfigParcel) data.readTypedObject(ClientConfigParcel.CREATOR);
              data.enforceNoDataAvail();
              notifyClientStopped(_arg012);
              reply.writeNoException();
              return true;
            case 13:
              ClientConfigParcel _arg013 =
                  (ClientConfigParcel) data.readTypedObject(ClientConfigParcel.CREATOR);
              data.enforceNoDataAvail();
              notifyClientConfigChanged(_arg013);
              reply.writeNoException();
              return true;
            case 14:
              IResourceManagerObserverClient _arg014 =
                  IResourceManagerObserverClient.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              IResourceManagerObserver _result2 = createResourceObserver(_arg014);
              reply.writeNoException();
              reply.writeStrongInterface(_result2);
              return true;
            case 15:
              int _arg015 = data.readInt();
              int _arg16 = data.readInt();
              long _arg23 = data.readLong();
              IResourceManagerClient _arg32 =
                  IResourceManagerClient.Stub.asInterface(data.readStrongBinder());
              MediaInfoParcel[] _arg4 =
                  (MediaInfoParcel[]) data.createTypedArray(MediaInfoParcel.CREATOR);
              data.enforceNoDataAvail();
              addMediaInfo(_arg015, _arg16, _arg23, _arg32, _arg4);
              reply.writeNoException();
              return true;
            case 16:
              int _arg016 = data.readInt();
              int _arg17 = data.readInt();
              long _arg24 = data.readLong();
              IResourceManagerClient _arg33 =
                  IResourceManagerClient.Stub.asInterface(data.readStrongBinder());
              int _arg42 = data.readInt();
              data.enforceNoDataAvail();
              setCodecState(_arg016, _arg17, _arg24, _arg33, _arg42);
              reply.writeNoException();
              return true;
            case 17:
              int _arg017 = data.readInt();
              int _arg18 = data.readInt();
              long _arg25 = data.readLong();
              IResourceManagerClient _arg34 =
                  IResourceManagerClient.Stub.asInterface(data.readStrongBinder());
              MediaInfoParcel[] _arg43 =
                  (MediaInfoParcel[]) data.createTypedArray(MediaInfoParcel.CREATOR);
              data.enforceNoDataAvail();
              sendCapacityError(_arg017, _arg18, _arg25, _arg34, _arg43);
              reply.writeNoException();
              return true;
            case 18:
              String _arg018 = data.readString();
              int _arg19 = data.readInt();
              int _arg26 = data.readInt();
              data.enforceNoDataAvail();
              float _result3 = getSupportedFrameRateFor(_arg018, _arg19, _arg26);
              reply.writeNoException();
              reply.writeFloat(_result3);
              return true;
            case 19:
              String _arg019 = data.readString();
              int _arg110 = data.readInt();
              int _arg27 = data.readInt();
              data.enforceNoDataAvail();
              float _result4 = getRemainedFrameRateFor(_arg019, _arg110, _arg27);
              reply.writeNoException();
              reply.writeFloat(_result4);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IResourceManagerService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IResourceManagerService.DESCRIPTOR;
      }

      @Override // android.media.IResourceManagerService
      public void config(MediaResourcePolicyParcel[] policies) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedArray(policies, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void addResource(
          ClientInfoParcel clientInfo,
          IResourceManagerClient client,
          MediaResourceParcel[] resources)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientInfo, 0);
          _data.writeStrongInterface(client);
          _data.writeTypedArray(resources, 0);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void removeResource(ClientInfoParcel clientInfo, MediaResourceParcel[] resources)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientInfo, 0);
          _data.writeTypedArray(resources, 0);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void removeClient(ClientInfoParcel clientInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientInfo, 0);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public boolean reclaimResource(ClientInfoParcel clientInfo, MediaResourceParcel[] resources)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientInfo, 0);
          _data.writeTypedArray(resources, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void overridePid(int originalPid, int newPid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeInt(originalPid);
          _data.writeInt(newPid);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void overrideProcessInfo(
          IResourceManagerClient client, int pid, int procState, int oomScore)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeStrongInterface(client);
          _data.writeInt(pid);
          _data.writeInt(procState);
          _data.writeInt(oomScore);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void markClientForPendingRemoval(ClientInfoParcel clientInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientInfo, 0);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void reclaimResourcesFromClientsPendingRemoval(int pid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeInt(pid);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void notifyClientCreated(ClientInfoParcel clientInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientInfo, 0);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void notifyClientStarted(ClientConfigParcel clientConfig) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientConfig, 0);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void notifyClientStopped(ClientConfigParcel clientConfig) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientConfig, 0);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void notifyClientConfigChanged(ClientConfigParcel clientConfig)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeTypedObject(clientConfig, 0);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public IResourceManagerObserver createResourceObserver(IResourceManagerObserverClient client)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeStrongInterface(client);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          IResourceManagerObserver _result =
              IResourceManagerObserver.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void addMediaInfo(
          int uid,
          int pid,
          long clientId,
          IResourceManagerClient client,
          MediaInfoParcel[] mediaInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(pid);
          _data.writeLong(clientId);
          _data.writeStrongInterface(client);
          _data.writeTypedArray(mediaInfo, 0);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void setCodecState(
          int uid, int pid, long clientId, IResourceManagerClient client, int state)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(pid);
          _data.writeLong(clientId);
          _data.writeStrongInterface(client);
          _data.writeInt(state);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public void sendCapacityError(
          int uid,
          int pid,
          long clientId,
          IResourceManagerClient client,
          MediaInfoParcel[] mediaInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeInt(uid);
          _data.writeInt(pid);
          _data.writeLong(clientId);
          _data.writeStrongInterface(client);
          _data.writeTypedArray(mediaInfo, 0);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public float getSupportedFrameRateFor(String componentName, int width, int height)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeString(componentName);
          _data.writeInt(width);
          _data.writeInt(height);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.IResourceManagerService
      public float getRemainedFrameRateFor(String componentName, int width, int height)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IResourceManagerService.DESCRIPTOR);
          _data.writeString(componentName);
          _data.writeInt(width);
          _data.writeInt(height);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 18;
    }
  }
}
