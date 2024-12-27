package com.samsung.android.remoteappmode;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.view.Surface;

public interface IRemoteAppMode extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.IRemoteAppMode";

    void clearAll() throws RemoteException;

    int createVirtualDisplay(
            String str,
            int i,
            int i2,
            int i3,
            Surface surface,
            IVirtualDisplayAliveChecker iVirtualDisplayAliveChecker)
            throws RemoteException;

    void disableSendingUserPresentIntent() throws RemoteException;

    void enableSendingUserPresentIntent(String str) throws RemoteException;

    void forceStopPackage(String str) throws RemoteException;

    void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    int getProtocolVersion() throws RemoteException;

    long getSendingUserPresentExpiredTime() throws RemoteException;

    boolean isAllowed() throws RemoteException;

    boolean isSendingUserPresentEnabled() throws RemoteException;

    void launchApplication(int i, String str, Intent intent, Bundle bundle) throws RemoteException;

    void moveDisplayToTop(int i) throws RemoteException;

    boolean registerRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener, String str)
            throws RemoteException;

    boolean registerRotationChangeListener(
            IRotationChangeListener iRotationChangeListener, String str, int i)
            throws RemoteException;

    boolean registerSecureAppChangedListener(
            ISecureAppChangedListener iSecureAppChangedListener, String str) throws RemoteException;

    boolean registerStartActivityInterceptListener(
            IStartActivityInterceptListener iStartActivityInterceptListener, String str)
            throws RemoteException;

    boolean registerTaskChangeListener(ITaskChangeListener iTaskChangeListener, String str)
            throws RemoteException;

    void releaseVirtualDisplay(int i) throws RemoteException;

    void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface)
            throws RemoteException;

    boolean sendNotificationAction(
            StatusBarNotification statusBarNotification, int i, Intent intent)
            throws RemoteException;

    boolean sendNotificationContent(StatusBarNotification statusBarNotification)
            throws RemoteException;

    void sendPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void setLTWProtocolVersion(int i) throws RemoteException;

    void setSendingUserPresentExpiredTime(long j) throws RemoteException;

    void startRFCommService() throws RemoteException;

    void stopRFCommService() throws RemoteException;

    void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle)
            throws RemoteException;

    void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) throws RemoteException;

    boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener)
            throws RemoteException;

    boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener)
            throws RemoteException;

    boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener)
            throws RemoteException;

    boolean unregisterStartActivityInterceptListener(
            IStartActivityInterceptListener iStartActivityInterceptListener) throws RemoteException;

    boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener)
            throws RemoteException;

    public static class Default implements IRemoteAppMode {
        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public int createVirtualDisplay(
                String name,
                int width,
                int height,
                int densityDpi,
                Surface surface,
                IVirtualDisplayAliveChecker checker)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void releaseVirtualDisplay(int displayId) throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void resizeVirtualDisplay(
                int displayId, int width, int height, int densityDpi, Surface surface)
                throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void moveDisplayToTop(int displayId) throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void launchApplication(
                int displayId, String packageName, Intent intent, Bundle activityOptionsBundle)
                throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean isAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerSecureAppChangedListener(
                ISecureAppChangedListener listener, String name) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterSecureAppChangedListener(ISecureAppChangedListener listener)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerTaskChangeListener(ITaskChangeListener listner, String name)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterTaskChangeListener(ITaskChangeListener listner)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerRotationChangeListener(
                IRotationChangeListener listener, String name, int displayId)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterRotationChangeListener(IRotationChangeListener listener)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerStartActivityInterceptListener(
                IStartActivityInterceptListener listner, String name) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterStartActivityInterceptListener(
                IStartActivityInterceptListener listner) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerRemoteAppModeListener(IRemoteAppModeListener listner, String name)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterRemoteAppModeListener(IRemoteAppModeListener listner)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void clearAll() throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void sendPendingIntent(PendingIntent pendingIntent) throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public int getProtocolVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean sendNotificationContent(StatusBarNotification sbn) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean sendNotificationAction(
                StatusBarNotification sbn, int actionIndex, Intent intent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void forceStopPackage(String packageName) throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void transferTaskWithoutIntercept(
                int taskId, int targetDisplayId, Bundle activityOptionsBundle)
                throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void setLTWProtocolVersion(int version) throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void startRFCommService() throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void stopRFCommService() throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void getLastAnr(String packageName, ParcelFileDescriptor outputPfd)
                throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void enableSendingUserPresentIntent(String packageName) throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void disableSendingUserPresentIntent() throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean isSendingUserPresentEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void setSendingUserPresentExpiredTime(long expiredTimeMillis)
                throws RemoteException {}

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public long getSendingUserPresentExpiredTime() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void transferTaskUsingIntent(
                Intent intent, int taskId, int targetDisplayId, Bundle activityOptionsBundle)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteAppMode {
        static final int TRANSACTION_clearAll = 17;
        static final int TRANSACTION_createVirtualDisplay = 1;
        static final int TRANSACTION_disableSendingUserPresentIntent = 29;
        static final int TRANSACTION_enableSendingUserPresentIntent = 28;
        static final int TRANSACTION_forceStopPackage = 22;
        static final int TRANSACTION_getLastAnr = 27;
        static final int TRANSACTION_getProtocolVersion = 19;
        static final int TRANSACTION_getSendingUserPresentExpiredTime = 32;
        static final int TRANSACTION_isAllowed = 6;
        static final int TRANSACTION_isSendingUserPresentEnabled = 30;
        static final int TRANSACTION_launchApplication = 5;
        static final int TRANSACTION_moveDisplayToTop = 4;
        static final int TRANSACTION_registerRemoteAppModeListener = 15;
        static final int TRANSACTION_registerRotationChangeListener = 11;
        static final int TRANSACTION_registerSecureAppChangedListener = 7;
        static final int TRANSACTION_registerStartActivityInterceptListener = 13;
        static final int TRANSACTION_registerTaskChangeListener = 9;
        static final int TRANSACTION_releaseVirtualDisplay = 2;
        static final int TRANSACTION_resizeVirtualDisplay = 3;
        static final int TRANSACTION_sendNotificationAction = 21;
        static final int TRANSACTION_sendNotificationContent = 20;
        static final int TRANSACTION_sendPendingIntent = 18;
        static final int TRANSACTION_setLTWProtocolVersion = 24;
        static final int TRANSACTION_setSendingUserPresentExpiredTime = 31;
        static final int TRANSACTION_startRFCommService = 25;
        static final int TRANSACTION_stopRFCommService = 26;
        static final int TRANSACTION_transferTaskUsingIntent = 33;
        static final int TRANSACTION_transferTaskWithoutIntercept = 23;
        static final int TRANSACTION_unregisterRemoteAppModeListener = 16;
        static final int TRANSACTION_unregisterRotationChangeListener = 12;
        static final int TRANSACTION_unregisterSecureAppChangedListener = 8;
        static final int TRANSACTION_unregisterStartActivityInterceptListener = 14;
        static final int TRANSACTION_unregisterTaskChangeListener = 10;

        public Stub() {
            attachInterface(this, IRemoteAppMode.DESCRIPTOR);
        }

        public static IRemoteAppMode asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteAppMode.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteAppMode)) {
                return (IRemoteAppMode) iin;
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
                    return "createVirtualDisplay";
                case 2:
                    return "releaseVirtualDisplay";
                case 3:
                    return "resizeVirtualDisplay";
                case 4:
                    return "moveDisplayToTop";
                case 5:
                    return "launchApplication";
                case 6:
                    return "isAllowed";
                case 7:
                    return "registerSecureAppChangedListener";
                case 8:
                    return "unregisterSecureAppChangedListener";
                case 9:
                    return "registerTaskChangeListener";
                case 10:
                    return "unregisterTaskChangeListener";
                case 11:
                    return "registerRotationChangeListener";
                case 12:
                    return "unregisterRotationChangeListener";
                case 13:
                    return "registerStartActivityInterceptListener";
                case 14:
                    return "unregisterStartActivityInterceptListener";
                case 15:
                    return "registerRemoteAppModeListener";
                case 16:
                    return "unregisterRemoteAppModeListener";
                case 17:
                    return "clearAll";
                case 18:
                    return "sendPendingIntent";
                case 19:
                    return "getProtocolVersion";
                case 20:
                    return "sendNotificationContent";
                case 21:
                    return "sendNotificationAction";
                case 22:
                    return "forceStopPackage";
                case 23:
                    return "transferTaskWithoutIntercept";
                case 24:
                    return "setLTWProtocolVersion";
                case 25:
                    return "startRFCommService";
                case 26:
                    return "stopRFCommService";
                case 27:
                    return "getLastAnr";
                case 28:
                    return "enableSendingUserPresentIntent";
                case 29:
                    return "disableSendingUserPresentIntent";
                case 30:
                    return "isSendingUserPresentEnabled";
                case 31:
                    return "setSendingUserPresentExpiredTime";
                case 32:
                    return "getSendingUserPresentExpiredTime";
                case 33:
                    return "transferTaskUsingIntent";
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
                data.enforceInterface(IRemoteAppMode.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteAppMode.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    Surface _arg4 = (Surface) data.readTypedObject(Surface.CREATOR);
                    IVirtualDisplayAliveChecker _arg5 =
                            IVirtualDisplayAliveChecker.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result = createVirtualDisplay(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    releaseVirtualDisplay(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    int _arg32 = data.readInt();
                    Surface _arg42 = (Surface) data.readTypedObject(Surface.CREATOR);
                    data.enforceNoDataAvail();
                    resizeVirtualDisplay(_arg03, _arg12, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    moveDisplayToTop(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    String _arg13 = data.readString();
                    Intent _arg23 = (Intent) data.readTypedObject(Intent.CREATOR);
                    Bundle _arg33 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    launchApplication(_arg05, _arg13, _arg23, _arg33);
                    reply.writeNoException();
                    return true;
                case 6:
                    boolean _result2 = isAllowed();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 7:
                    ISecureAppChangedListener _arg06 =
                            ISecureAppChangedListener.Stub.asInterface(data.readStrongBinder());
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result3 = registerSecureAppChangedListener(_arg06, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 8:
                    ISecureAppChangedListener _arg07 =
                            ISecureAppChangedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result4 = unregisterSecureAppChangedListener(_arg07);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 9:
                    ITaskChangeListener _arg08 =
                            ITaskChangeListener.Stub.asInterface(data.readStrongBinder());
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result5 = registerTaskChangeListener(_arg08, _arg15);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 10:
                    ITaskChangeListener _arg09 =
                            ITaskChangeListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result6 = unregisterTaskChangeListener(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 11:
                    IRotationChangeListener _arg010 =
                            IRotationChangeListener.Stub.asInterface(data.readStrongBinder());
                    String _arg16 = data.readString();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result7 = registerRotationChangeListener(_arg010, _arg16, _arg24);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 12:
                    IRotationChangeListener _arg011 =
                            IRotationChangeListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result8 = unregisterRotationChangeListener(_arg011);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 13:
                    IStartActivityInterceptListener _arg012 =
                            IStartActivityInterceptListener.Stub.asInterface(
                                    data.readStrongBinder());
                    String _arg17 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result9 = registerStartActivityInterceptListener(_arg012, _arg17);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 14:
                    IStartActivityInterceptListener _arg013 =
                            IStartActivityInterceptListener.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result10 = unregisterStartActivityInterceptListener(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 15:
                    IRemoteAppModeListener _arg014 =
                            IRemoteAppModeListener.Stub.asInterface(data.readStrongBinder());
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result11 = registerRemoteAppModeListener(_arg014, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 16:
                    IRemoteAppModeListener _arg015 =
                            IRemoteAppModeListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result12 = unregisterRemoteAppModeListener(_arg015);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 17:
                    clearAll();
                    reply.writeNoException();
                    return true;
                case 18:
                    PendingIntent _arg016 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    sendPendingIntent(_arg016);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _result13 = getProtocolVersion();
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 20:
                    StatusBarNotification _arg017 =
                            (StatusBarNotification)
                                    data.readTypedObject(StatusBarNotification.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result14 = sendNotificationContent(_arg017);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 21:
                    StatusBarNotification _arg018 =
                            (StatusBarNotification)
                                    data.readTypedObject(StatusBarNotification.CREATOR);
                    int _arg19 = data.readInt();
                    Intent _arg25 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result15 = sendNotificationAction(_arg018, _arg19, _arg25);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 22:
                    String _arg019 = data.readString();
                    data.enforceNoDataAvail();
                    forceStopPackage(_arg019);
                    reply.writeNoException();
                    return true;
                case 23:
                    int _arg020 = data.readInt();
                    int _arg110 = data.readInt();
                    Bundle _arg26 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    transferTaskWithoutIntercept(_arg020, _arg110, _arg26);
                    reply.writeNoException();
                    return true;
                case 24:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    setLTWProtocolVersion(_arg021);
                    reply.writeNoException();
                    return true;
                case 25:
                    startRFCommService();
                    reply.writeNoException();
                    return true;
                case 26:
                    stopRFCommService();
                    reply.writeNoException();
                    return true;
                case 27:
                    String _arg022 = data.readString();
                    ParcelFileDescriptor _arg111 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    getLastAnr(_arg022, _arg111);
                    reply.writeNoException();
                    return true;
                case 28:
                    String _arg023 = data.readString();
                    data.enforceNoDataAvail();
                    enableSendingUserPresentIntent(_arg023);
                    reply.writeNoException();
                    return true;
                case 29:
                    disableSendingUserPresentIntent();
                    reply.writeNoException();
                    return true;
                case 30:
                    boolean _result16 = isSendingUserPresentEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 31:
                    long _arg024 = data.readLong();
                    data.enforceNoDataAvail();
                    setSendingUserPresentExpiredTime(_arg024);
                    reply.writeNoException();
                    return true;
                case 32:
                    long _result17 = getSendingUserPresentExpiredTime();
                    reply.writeNoException();
                    reply.writeLong(_result17);
                    return true;
                case 33:
                    Intent _arg025 = (Intent) data.readTypedObject(Intent.CREATOR);
                    int _arg112 = data.readInt();
                    int _arg27 = data.readInt();
                    Bundle _arg34 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    transferTaskUsingIntent(_arg025, _arg112, _arg27, _arg34);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteAppMode {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteAppMode.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public int createVirtualDisplay(
                    String name,
                    int width,
                    int height,
                    int densityDpi,
                    Surface surface,
                    IVirtualDisplayAliveChecker checker)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(densityDpi);
                    _data.writeTypedObject(surface, 0);
                    _data.writeStrongInterface(checker);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void releaseVirtualDisplay(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void resizeVirtualDisplay(
                    int displayId, int width, int height, int densityDpi, Surface surface)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(densityDpi);
                    _data.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void moveDisplayToTop(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void launchApplication(
                    int displayId, String packageName, Intent intent, Bundle activityOptionsBundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeString(packageName);
                    _data.writeTypedObject(intent, 0);
                    _data.writeTypedObject(activityOptionsBundle, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean isAllowed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerSecureAppChangedListener(
                    ISecureAppChangedListener listener, String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeString(name);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterSecureAppChangedListener(ISecureAppChangedListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerTaskChangeListener(ITaskChangeListener listner, String name)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    _data.writeString(name);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterTaskChangeListener(ITaskChangeListener listner)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerRotationChangeListener(
                    IRotationChangeListener listener, String name, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    _data.writeString(name);
                    _data.writeInt(displayId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterRotationChangeListener(IRotationChangeListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerStartActivityInterceptListener(
                    IStartActivityInterceptListener listner, String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    _data.writeString(name);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterStartActivityInterceptListener(
                    IStartActivityInterceptListener listner) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerRemoteAppModeListener(
                    IRemoteAppModeListener listner, String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    _data.writeString(name);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterRemoteAppModeListener(IRemoteAppModeListener listner)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeStrongInterface(listner);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void clearAll() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void sendPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public int getProtocolVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean sendNotificationContent(StatusBarNotification sbn)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeTypedObject(sbn, 0);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean sendNotificationAction(
                    StatusBarNotification sbn, int actionIndex, Intent intent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeTypedObject(sbn, 0);
                    _data.writeInt(actionIndex);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void forceStopPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void transferTaskWithoutIntercept(
                    int taskId, int targetDisplayId, Bundle activityOptionsBundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeInt(targetDisplayId);
                    _data.writeTypedObject(activityOptionsBundle, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void setLTWProtocolVersion(int version) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeInt(version);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void startRFCommService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void stopRFCommService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void getLastAnr(String packageName, ParcelFileDescriptor outputPfd)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(outputPfd, 0);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void enableSendingUserPresentIntent(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void disableSendingUserPresentIntent() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean isSendingUserPresentEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void setSendingUserPresentExpiredTime(long expiredTimeMillis)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeLong(expiredTimeMillis);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public long getSendingUserPresentExpiredTime() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void transferTaskUsingIntent(
                    Intent intent, int taskId, int targetDisplayId, Bundle activityOptionsBundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeInt(taskId);
                    _data.writeInt(targetDisplayId);
                    _data.writeTypedObject(activityOptionsBundle, 0);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 32;
        }
    }
}
