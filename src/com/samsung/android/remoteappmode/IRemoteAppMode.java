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
import com.samsung.android.remoteappmode.IRemoteAppModeListener;
import com.samsung.android.remoteappmode.IRotationChangeListener;
import com.samsung.android.remoteappmode.ISecureAppChangedListener;
import com.samsung.android.remoteappmode.IStartActivityInterceptListener;
import com.samsung.android.remoteappmode.ITaskChangeListener;
import com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker;

/* loaded from: classes6.dex */
public interface IRemoteAppMode extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.IRemoteAppMode";

    public static class Default implements IRemoteAppMode {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void clearAll() throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public int createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, IVirtualDisplayAliveChecker iVirtualDisplayAliveChecker) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void disableSendingUserPresentIntent() throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void enableSendingUserPresentIntent(String str) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void forceStopPackage(String str) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public int getProtocolVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public long getSendingUserPresentExpiredTime() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean isAllowed() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean isSendingUserPresentEnabled() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void launchApplication(int i, String str, Intent intent, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void moveDisplayToTop(int i) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerRotationChangeListener(IRotationChangeListener iRotationChangeListener, String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean registerTaskChangeListener(ITaskChangeListener iTaskChangeListener, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void releaseVirtualDisplay(int i) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean sendNotificationAction(StatusBarNotification statusBarNotification, int i, Intent intent) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean sendNotificationContent(StatusBarNotification statusBarNotification) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void sendPendingIntent(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void setLTWProtocolVersion(int i) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void setSendingUserPresentExpiredTime(long j) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void startRFCommService() throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void stopRFCommService() throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppMode
        public boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) throws RemoteException {
            return false;
        }
    }

    void clearAll() throws RemoteException;

    int createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, IVirtualDisplayAliveChecker iVirtualDisplayAliveChecker) throws RemoteException;

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

    boolean registerRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener, String str) throws RemoteException;

    boolean registerRotationChangeListener(IRotationChangeListener iRotationChangeListener, String str, int i) throws RemoteException;

    boolean registerSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener, String str) throws RemoteException;

    boolean registerStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener, String str) throws RemoteException;

    boolean registerTaskChangeListener(ITaskChangeListener iTaskChangeListener, String str) throws RemoteException;

    void releaseVirtualDisplay(int i) throws RemoteException;

    void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface) throws RemoteException;

    boolean sendNotificationAction(StatusBarNotification statusBarNotification, int i, Intent intent) throws RemoteException;

    boolean sendNotificationContent(StatusBarNotification statusBarNotification) throws RemoteException;

    void sendPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void setLTWProtocolVersion(int i) throws RemoteException;

    void setSendingUserPresentExpiredTime(long j) throws RemoteException;

    void startRFCommService() throws RemoteException;

    void stopRFCommService() throws RemoteException;

    void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle) throws RemoteException;

    void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) throws RemoteException;

    boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener) throws RemoteException;

    boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener) throws RemoteException;

    boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener) throws RemoteException;

    boolean unregisterStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener) throws RemoteException;

    boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteAppMode {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 32;
        }

        public Stub() {
            attachInterface(this, IRemoteAppMode.DESCRIPTOR);
        }

        public static IRemoteAppMode asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteAppMode.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteAppMode)) {
                return (IRemoteAppMode) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteAppMode.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteAppMode.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    IVirtualDisplayAliveChecker asInterface = IVirtualDisplayAliveChecker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int createVirtualDisplay = createVirtualDisplay(readString, readInt, readInt2, readInt3, surface, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(createVirtualDisplay);
                    return true;
                case 2:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseVirtualDisplay(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    resizeVirtualDisplay(readInt5, readInt6, readInt7, readInt8, surface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveDisplayToTop(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt10 = parcel.readInt();
                    String readString2 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    launchApplication(readInt10, readString2, intent, bundle);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean isAllowed = isAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAllowed);
                    return true;
                case 7:
                    ISecureAppChangedListener asInterface2 = ISecureAppChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerSecureAppChangedListener = registerSecureAppChangedListener(asInterface2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSecureAppChangedListener);
                    return true;
                case 8:
                    ISecureAppChangedListener asInterface3 = ISecureAppChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterSecureAppChangedListener = unregisterSecureAppChangedListener(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterSecureAppChangedListener);
                    return true;
                case 9:
                    ITaskChangeListener asInterface4 = ITaskChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerTaskChangeListener = registerTaskChangeListener(asInterface4, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerTaskChangeListener);
                    return true;
                case 10:
                    ITaskChangeListener asInterface5 = ITaskChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterTaskChangeListener = unregisterTaskChangeListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterTaskChangeListener);
                    return true;
                case 11:
                    IRotationChangeListener asInterface6 = IRotationChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean registerRotationChangeListener = registerRotationChangeListener(asInterface6, readString5, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerRotationChangeListener);
                    return true;
                case 12:
                    IRotationChangeListener asInterface7 = IRotationChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterRotationChangeListener = unregisterRotationChangeListener(asInterface7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterRotationChangeListener);
                    return true;
                case 13:
                    IStartActivityInterceptListener asInterface8 = IStartActivityInterceptListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerStartActivityInterceptListener = registerStartActivityInterceptListener(asInterface8, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerStartActivityInterceptListener);
                    return true;
                case 14:
                    IStartActivityInterceptListener asInterface9 = IStartActivityInterceptListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterStartActivityInterceptListener = unregisterStartActivityInterceptListener(asInterface9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterStartActivityInterceptListener);
                    return true;
                case 15:
                    IRemoteAppModeListener asInterface10 = IRemoteAppModeListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean registerRemoteAppModeListener = registerRemoteAppModeListener(asInterface10, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerRemoteAppModeListener);
                    return true;
                case 16:
                    IRemoteAppModeListener asInterface11 = IRemoteAppModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterRemoteAppModeListener = unregisterRemoteAppModeListener(asInterface11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterRemoteAppModeListener);
                    return true;
                case 17:
                    clearAll();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendPendingIntent(pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int protocolVersion = getProtocolVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(protocolVersion);
                    return true;
                case 20:
                    StatusBarNotification statusBarNotification = (StatusBarNotification) parcel.readTypedObject(StatusBarNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendNotificationContent = sendNotificationContent(statusBarNotification);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendNotificationContent);
                    return true;
                case 21:
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) parcel.readTypedObject(StatusBarNotification.CREATOR);
                    int readInt12 = parcel.readInt();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendNotificationAction = sendNotificationAction(statusBarNotification2, readInt12, intent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendNotificationAction);
                    return true;
                case 22:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forceStopPackage(readString8);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    transferTaskWithoutIntercept(readInt13, readInt14, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLTWProtocolVersion(readInt15);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    startRFCommService();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    stopRFCommService();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString9 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    getLastAnr(readString9, parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableSendingUserPresentIntent(readString10);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    disableSendingUserPresentIntent();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    boolean isSendingUserPresentEnabled = isSendingUserPresentEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSendingUserPresentEnabled);
                    return true;
                case 31:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setSendingUserPresentExpiredTime(readLong);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    long sendingUserPresentExpiredTime = getSendingUserPresentExpiredTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(sendingUserPresentExpiredTime);
                    return true;
                case 33:
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    transferTaskUsingIntent(intent3, readInt16, readInt17, bundle3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRemoteAppMode {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteAppMode.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public int createVirtualDisplay(String str, int i, int i2, int i3, Surface surface, IVirtualDisplayAliveChecker iVirtualDisplayAliveChecker) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeStrongInterface(iVirtualDisplayAliveChecker);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void releaseVirtualDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void moveDisplayToTop(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void launchApplication(int i, String str, Intent intent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean isAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iSecureAppChangedListener);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iSecureAppChangedListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerTaskChangeListener(ITaskChangeListener iTaskChangeListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskChangeListener);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskChangeListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerRotationChangeListener(IRotationChangeListener iRotationChangeListener, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iRotationChangeListener);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iRotationChangeListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iStartActivityInterceptListener);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iStartActivityInterceptListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAppModeListener);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAppModeListener);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void clearAll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void sendPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public int getProtocolVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean sendNotificationContent(StatusBarNotification statusBarNotification) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeTypedObject(statusBarNotification, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean sendNotificationAction(StatusBarNotification statusBarNotification, int i, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeTypedObject(statusBarNotification, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void forceStopPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void setLTWProtocolVersion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void startRFCommService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void stopRFCommService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void enableSendingUserPresentIntent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void disableSendingUserPresentIntent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean isSendingUserPresentEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void setSendingUserPresentExpiredTime(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public long getSendingUserPresentExpiredTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
