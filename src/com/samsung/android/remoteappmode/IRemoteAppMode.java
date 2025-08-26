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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteAppMode.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteAppMode)) {
                return (IRemoteAppMode) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    IVirtualDisplayAliveChecker iVirtualDisplayAliveCheckerAsInterface = IVirtualDisplayAliveChecker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iCreateVirtualDisplay = createVirtualDisplay(string, i3, i4, i5, surface, iVirtualDisplayAliveCheckerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateVirtualDisplay);
                    return true;
                case 2:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseVirtualDisplay(i6);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    Surface surface2 = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    resizeVirtualDisplay(i7, i8, i9, i10, surface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveDisplayToTop(i11);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i12 = parcel.readInt();
                    String string2 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    launchApplication(i12, string2, intent, bundle);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean zIsAllowed = isAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAllowed);
                    return true;
                case 7:
                    ISecureAppChangedListener iSecureAppChangedListenerAsInterface = ISecureAppChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterSecureAppChangedListener = registerSecureAppChangedListener(iSecureAppChangedListenerAsInterface, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterSecureAppChangedListener);
                    return true;
                case 8:
                    ISecureAppChangedListener iSecureAppChangedListenerAsInterface2 = ISecureAppChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterSecureAppChangedListener = unregisterSecureAppChangedListener(iSecureAppChangedListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterSecureAppChangedListener);
                    return true;
                case 9:
                    ITaskChangeListener iTaskChangeListenerAsInterface = ITaskChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterTaskChangeListener = registerTaskChangeListener(iTaskChangeListenerAsInterface, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterTaskChangeListener);
                    return true;
                case 10:
                    ITaskChangeListener iTaskChangeListenerAsInterface2 = ITaskChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterTaskChangeListener = unregisterTaskChangeListener(iTaskChangeListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterTaskChangeListener);
                    return true;
                case 11:
                    IRotationChangeListener iRotationChangeListenerAsInterface = IRotationChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterRotationChangeListener = registerRotationChangeListener(iRotationChangeListenerAsInterface, string5, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterRotationChangeListener);
                    return true;
                case 12:
                    IRotationChangeListener iRotationChangeListenerAsInterface2 = IRotationChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterRotationChangeListener = unregisterRotationChangeListener(iRotationChangeListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterRotationChangeListener);
                    return true;
                case 13:
                    IStartActivityInterceptListener iStartActivityInterceptListenerAsInterface = IStartActivityInterceptListener.Stub.asInterface(parcel.readStrongBinder());
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterStartActivityInterceptListener = registerStartActivityInterceptListener(iStartActivityInterceptListenerAsInterface, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterStartActivityInterceptListener);
                    return true;
                case 14:
                    IStartActivityInterceptListener iStartActivityInterceptListenerAsInterface2 = IStartActivityInterceptListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterStartActivityInterceptListener = unregisterStartActivityInterceptListener(iStartActivityInterceptListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterStartActivityInterceptListener);
                    return true;
                case 15:
                    IRemoteAppModeListener iRemoteAppModeListenerAsInterface = IRemoteAppModeListener.Stub.asInterface(parcel.readStrongBinder());
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterRemoteAppModeListener = registerRemoteAppModeListener(iRemoteAppModeListenerAsInterface, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterRemoteAppModeListener);
                    return true;
                case 16:
                    IRemoteAppModeListener iRemoteAppModeListenerAsInterface2 = IRemoteAppModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterRemoteAppModeListener = unregisterRemoteAppModeListener(iRemoteAppModeListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterRemoteAppModeListener);
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
                    boolean zSendNotificationContent = sendNotificationContent(statusBarNotification);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendNotificationContent);
                    return true;
                case 21:
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) parcel.readTypedObject(StatusBarNotification.CREATOR);
                    int i14 = parcel.readInt();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendNotificationAction = sendNotificationAction(statusBarNotification2, i14, intent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendNotificationAction);
                    return true;
                case 22:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forceStopPackage(string8);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    transferTaskWithoutIntercept(i15, i16, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLTWProtocolVersion(i17);
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
                    String string9 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    getLastAnr(string9, parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableSendingUserPresentIntent(string10);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    disableSendingUserPresentIntent();
                    parcel2.writeNoException();
                    return true;
                case 30:
                    boolean zIsSendingUserPresentEnabled = isSendingUserPresentEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSendingUserPresentEnabled);
                    return true;
                case 31:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setSendingUserPresentExpiredTime(j);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    long sendingUserPresentExpiredTime = getSendingUserPresentExpiredTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(sendingUserPresentExpiredTime);
                    return true;
                case 33:
                    Intent intent3 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    transferTaskUsingIntent(intent3, i18, i19, bundle3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeStrongInterface(iVirtualDisplayAliveChecker);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void releaseVirtualDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void resizeVirtualDisplay(int i, int i2, int i3, int i4, Surface surface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void moveDisplayToTop(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void launchApplication(int i, String str, Intent intent, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean isAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSecureAppChangedListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSecureAppChangedListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerTaskChangeListener(ITaskChangeListener iTaskChangeListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskChangeListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterTaskChangeListener(ITaskChangeListener iTaskChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTaskChangeListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerRotationChangeListener(IRotationChangeListener iRotationChangeListener, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRotationChangeListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterRotationChangeListener(IRotationChangeListener iRotationChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRotationChangeListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStartActivityInterceptListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterStartActivityInterceptListener(IStartActivityInterceptListener iStartActivityInterceptListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStartActivityInterceptListener);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean registerRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteAppModeListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean unregisterRemoteAppModeListener(IRemoteAppModeListener iRemoteAppModeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteAppModeListener);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void clearAll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void sendPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public int getProtocolVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean sendNotificationContent(StatusBarNotification statusBarNotification) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusBarNotification, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean sendNotificationAction(StatusBarNotification statusBarNotification, int i, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(statusBarNotification, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void forceStopPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void transferTaskWithoutIntercept(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void setLTWProtocolVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void startRFCommService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void stopRFCommService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void enableSendingUserPresentIntent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void disableSendingUserPresentIntent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public boolean isSendingUserPresentEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void setSendingUserPresentExpiredTime(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public long getSendingUserPresentExpiredTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppMode
            public void transferTaskUsingIntent(Intent intent, int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteAppMode.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
