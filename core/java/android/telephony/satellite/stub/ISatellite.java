package android.telephony.satellite.stub;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.android.internal.telephony.IBooleanConsumer;
import com.android.internal.telephony.IIntegerConsumer;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISatellite extends IInterface {
  public static final String DESCRIPTOR = "android.telephony.satellite.stub.ISatellite";

  void deprovisionSatelliteService(String str, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void enableCellularModemWhileSatelliteModeIsOn(boolean z, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void pollPendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException;

  void provisionSatelliteService(String str, byte[] bArr, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void requestIsSatelliteCommunicationAllowedForCurrentLocation(
      IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException;

  void requestIsSatelliteEnabled(
      IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException;

  void requestIsSatelliteEnabledForCarrier(
      int i, IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer)
      throws RemoteException;

  void requestIsSatelliteProvisioned(
      IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException;

  void requestIsSatelliteSupported(
      IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException;

  void requestSatelliteCapabilities(
      IIntegerConsumer iIntegerConsumer,
      ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer)
      throws RemoteException;

  void requestSatelliteEnabled(boolean z, boolean z2, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void requestSatelliteListeningEnabled(boolean z, int i, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void requestSatelliteModemState(
      IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException;

  void requestTimeForNextSatelliteVisibility(
      IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException;

  void sendSatelliteDatagram(
      SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void setSatelliteEnabledForCarrier(int i, boolean z, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void setSatelliteListener(ISatelliteListener iSatelliteListener) throws RemoteException;

  void setSatellitePlmn(
      int i, List<String> list, List<String> list2, IIntegerConsumer iIntegerConsumer)
      throws RemoteException;

  void startSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException;

  void stopSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException;

  public static class Default implements ISatellite {
    @Override // android.telephony.satellite.stub.ISatellite
    public void setSatelliteListener(ISatelliteListener listener) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestSatelliteListeningEnabled(
        boolean enable, int timeout, IIntegerConsumer resultCallback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void enableCellularModemWhileSatelliteModeIsOn(
        boolean enabled, IIntegerConsumer errorCallback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestSatelliteEnabled(
        boolean enableSatellite, boolean enableDemoMode, IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteEnabled(
        IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteSupported(
        IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestSatelliteCapabilities(
        IIntegerConsumer resultCallback, ISatelliteCapabilitiesConsumer callback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void startSendingSatellitePointingInfo(IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void stopSendingSatellitePointingInfo(IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void provisionSatelliteService(
        String token, byte[] provisionData, IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void deprovisionSatelliteService(String token, IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteProvisioned(
        IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void pollPendingSatelliteDatagrams(IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void sendSatelliteDatagram(
        SatelliteDatagram datagram, boolean isEmergency, IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestSatelliteModemState(
        IIntegerConsumer resultCallback, IIntegerConsumer callback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteCommunicationAllowedForCurrentLocation(
        IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestTimeForNextSatelliteVisibility(
        IIntegerConsumer resultCallback, IIntegerConsumer callback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void setSatellitePlmn(
        int simSlot,
        List<String> carrierPlmnList,
        List<String> allSatellitePlmnList,
        IIntegerConsumer resultCallback)
        throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void setSatelliteEnabledForCarrier(
        int simSlot, boolean satelliteEnabled, IIntegerConsumer callback) throws RemoteException {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteEnabledForCarrier(
        int simSlot, IIntegerConsumer resultCallback, IBooleanConsumer callback)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISatellite {
    static final int TRANSACTION_deprovisionSatelliteService = 11;
    static final int TRANSACTION_enableCellularModemWhileSatelliteModeIsOn = 3;
    static final int TRANSACTION_pollPendingSatelliteDatagrams = 13;
    static final int TRANSACTION_provisionSatelliteService = 10;

    /* renamed from: TRANSACTION_requestIsSatelliteCommunicationAllowedForCurrentLocation */
    static final int f522xd8afe6fa = 16;
    static final int TRANSACTION_requestIsSatelliteEnabled = 5;
    static final int TRANSACTION_requestIsSatelliteEnabledForCarrier = 20;
    static final int TRANSACTION_requestIsSatelliteProvisioned = 12;
    static final int TRANSACTION_requestIsSatelliteSupported = 6;
    static final int TRANSACTION_requestSatelliteCapabilities = 7;
    static final int TRANSACTION_requestSatelliteEnabled = 4;
    static final int TRANSACTION_requestSatelliteListeningEnabled = 2;
    static final int TRANSACTION_requestSatelliteModemState = 15;
    static final int TRANSACTION_requestTimeForNextSatelliteVisibility = 17;
    static final int TRANSACTION_sendSatelliteDatagram = 14;
    static final int TRANSACTION_setSatelliteEnabledForCarrier = 19;
    static final int TRANSACTION_setSatelliteListener = 1;
    static final int TRANSACTION_setSatellitePlmn = 18;
    static final int TRANSACTION_startSendingSatellitePointingInfo = 8;
    static final int TRANSACTION_stopSendingSatellitePointingInfo = 9;

    public Stub() {
      attachInterface(this, ISatellite.DESCRIPTOR);
    }

    public static ISatellite asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISatellite.DESCRIPTOR);
      if (iin != null && (iin instanceof ISatellite)) {
        return (ISatellite) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "setSatelliteListener";
        case 2:
          return "requestSatelliteListeningEnabled";
        case 3:
          return "enableCellularModemWhileSatelliteModeIsOn";
        case 4:
          return "requestSatelliteEnabled";
        case 5:
          return "requestIsSatelliteEnabled";
        case 6:
          return "requestIsSatelliteSupported";
        case 7:
          return "requestSatelliteCapabilities";
        case 8:
          return "startSendingSatellitePointingInfo";
        case 9:
          return "stopSendingSatellitePointingInfo";
        case 10:
          return "provisionSatelliteService";
        case 11:
          return "deprovisionSatelliteService";
        case 12:
          return "requestIsSatelliteProvisioned";
        case 13:
          return "pollPendingSatelliteDatagrams";
        case 14:
          return "sendSatelliteDatagram";
        case 15:
          return "requestSatelliteModemState";
        case 16:
          return "requestIsSatelliteCommunicationAllowedForCurrentLocation";
        case 17:
          return "requestTimeForNextSatelliteVisibility";
        case 18:
          return "setSatellitePlmn";
        case 19:
          return "setSatelliteEnabledForCarrier";
        case 20:
          return "requestIsSatelliteEnabledForCarrier";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(ISatellite.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISatellite.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ISatelliteListener _arg0 =
                  ISatelliteListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setSatelliteListener(_arg0);
              return true;
            case 2:
              boolean _arg02 = data.readBoolean();
              int _arg1 = data.readInt();
              IIntegerConsumer _arg2 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestSatelliteListeningEnabled(_arg02, _arg1, _arg2);
              return true;
            case 3:
              boolean _arg03 = data.readBoolean();
              IIntegerConsumer _arg12 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              enableCellularModemWhileSatelliteModeIsOn(_arg03, _arg12);
              return true;
            case 4:
              boolean _arg04 = data.readBoolean();
              boolean _arg13 = data.readBoolean();
              IIntegerConsumer _arg22 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestSatelliteEnabled(_arg04, _arg13, _arg22);
              return true;
            case 5:
              IIntegerConsumer _arg05 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              IBooleanConsumer _arg14 = IBooleanConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestIsSatelliteEnabled(_arg05, _arg14);
              return true;
            case 6:
              IIntegerConsumer _arg06 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              IBooleanConsumer _arg15 = IBooleanConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestIsSatelliteSupported(_arg06, _arg15);
              return true;
            case 7:
              IIntegerConsumer _arg07 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              ISatelliteCapabilitiesConsumer _arg16 =
                  ISatelliteCapabilitiesConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestSatelliteCapabilities(_arg07, _arg16);
              return true;
            case 8:
              IIntegerConsumer _arg08 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              startSendingSatellitePointingInfo(_arg08);
              return true;
            case 9:
              IIntegerConsumer _arg09 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              stopSendingSatellitePointingInfo(_arg09);
              return true;
            case 10:
              String _arg010 = data.readString();
              byte[] _arg17 = data.createByteArray();
              IIntegerConsumer _arg23 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              provisionSatelliteService(_arg010, _arg17, _arg23);
              return true;
            case 11:
              String _arg011 = data.readString();
              IIntegerConsumer _arg18 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              deprovisionSatelliteService(_arg011, _arg18);
              return true;
            case 12:
              IIntegerConsumer _arg012 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              IBooleanConsumer _arg19 = IBooleanConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestIsSatelliteProvisioned(_arg012, _arg19);
              return true;
            case 13:
              IIntegerConsumer _arg013 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              pollPendingSatelliteDatagrams(_arg013);
              return true;
            case 14:
              SatelliteDatagram _arg014 =
                  (SatelliteDatagram) data.readTypedObject(SatelliteDatagram.CREATOR);
              boolean _arg110 = data.readBoolean();
              IIntegerConsumer _arg24 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              sendSatelliteDatagram(_arg014, _arg110, _arg24);
              return true;
            case 15:
              IIntegerConsumer _arg015 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              IIntegerConsumer _arg111 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestSatelliteModemState(_arg015, _arg111);
              return true;
            case 16:
              IIntegerConsumer _arg016 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              IBooleanConsumer _arg112 = IBooleanConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestIsSatelliteCommunicationAllowedForCurrentLocation(_arg016, _arg112);
              return true;
            case 17:
              IIntegerConsumer _arg017 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              IIntegerConsumer _arg113 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestTimeForNextSatelliteVisibility(_arg017, _arg113);
              return true;
            case 18:
              int _arg018 = data.readInt();
              List<String> _arg114 = data.createStringArrayList();
              List<String> _arg25 = data.createStringArrayList();
              IIntegerConsumer _arg3 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setSatellitePlmn(_arg018, _arg114, _arg25, _arg3);
              return true;
            case 19:
              int _arg019 = data.readInt();
              boolean _arg115 = data.readBoolean();
              IIntegerConsumer _arg26 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              setSatelliteEnabledForCarrier(_arg019, _arg115, _arg26);
              return true;
            case 20:
              int _arg020 = data.readInt();
              IIntegerConsumer _arg116 = IIntegerConsumer.Stub.asInterface(data.readStrongBinder());
              IBooleanConsumer _arg27 = IBooleanConsumer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestIsSatelliteEnabledForCarrier(_arg020, _arg116, _arg27);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISatellite {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISatellite.DESCRIPTOR;
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void setSatelliteListener(ISatelliteListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestSatelliteListeningEnabled(
          boolean enable, int timeout, IIntegerConsumer resultCallback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeBoolean(enable);
          _data.writeInt(timeout);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void enableCellularModemWhileSatelliteModeIsOn(
          boolean enabled, IIntegerConsumer errorCallback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeBoolean(enabled);
          _data.writeStrongInterface(errorCallback);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestSatelliteEnabled(
          boolean enableSatellite, boolean enableDemoMode, IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeBoolean(enableSatellite);
          _data.writeBoolean(enableDemoMode);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestIsSatelliteEnabled(
          IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestIsSatelliteSupported(
          IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(6, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestSatelliteCapabilities(
          IIntegerConsumer resultCallback, ISatelliteCapabilitiesConsumer callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(7, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void startSendingSatellitePointingInfo(IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(8, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void stopSendingSatellitePointingInfo(IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(9, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void provisionSatelliteService(
          String token, byte[] provisionData, IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeString(token);
          _data.writeByteArray(provisionData);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(10, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void deprovisionSatelliteService(String token, IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeString(token);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(11, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestIsSatelliteProvisioned(
          IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(12, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void pollPendingSatelliteDatagrams(IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(13, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void sendSatelliteDatagram(
          SatelliteDatagram datagram, boolean isEmergency, IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeTypedObject(datagram, 0);
          _data.writeBoolean(isEmergency);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(14, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestSatelliteModemState(
          IIntegerConsumer resultCallback, IIntegerConsumer callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(15, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestIsSatelliteCommunicationAllowedForCurrentLocation(
          IIntegerConsumer resultCallback, IBooleanConsumer callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(16, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestTimeForNextSatelliteVisibility(
          IIntegerConsumer resultCallback, IIntegerConsumer callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(17, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void setSatellitePlmn(
          int simSlot,
          List<String> carrierPlmnList,
          List<String> allSatellitePlmnList,
          IIntegerConsumer resultCallback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeInt(simSlot);
          _data.writeStringList(carrierPlmnList);
          _data.writeStringList(allSatellitePlmnList);
          _data.writeStrongInterface(resultCallback);
          this.mRemote.transact(18, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void setSatelliteEnabledForCarrier(
          int simSlot, boolean satelliteEnabled, IIntegerConsumer callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeInt(simSlot);
          _data.writeBoolean(satelliteEnabled);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(19, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.telephony.satellite.stub.ISatellite
      public void requestIsSatelliteEnabledForCarrier(
          int simSlot, IIntegerConsumer resultCallback, IBooleanConsumer callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISatellite.DESCRIPTOR);
          _data.writeInt(simSlot);
          _data.writeStrongInterface(resultCallback);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(20, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 19;
    }
  }
}
