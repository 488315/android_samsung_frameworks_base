package com.android.internal.backup;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.RestoreDescription;
import android.app.backup.RestoreSet;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.backup.ITransportStatusCallback;
import com.android.internal.infra.AndroidFuture;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IBackupTransport extends IInterface {

    public static class Default implements IBackupTransport {
        @Override // com.android.internal.backup.IBackupTransport
        public void abortFullRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void cancelFullBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void checkFullBackupSize(long j, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void clearBackupData(PackageInfo packageInfo, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void configurationIntent(AndroidFuture<Intent> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void currentDestinationString(AndroidFuture<String> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntent(AndroidFuture<Intent> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntentLabel(AndroidFuture<CharSequence> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getAvailableRestoreSets(AndroidFuture<List<RestoreSet>> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupManagerMonitor(AndroidFuture<IBackupManagerMonitor> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupQuota(String str, boolean z, AndroidFuture<Long> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getCurrentRestoreSet(AndroidFuture<Long> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getPackagesThatShouldNotUseRestrictedMode(List<String> list, int i, AndroidFuture<List<String>> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getRestoreData(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getTransportFlags(AndroidFuture<Integer> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void initializeDevice(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void isAppEligibleForBackup(PackageInfo packageInfo, boolean z, AndroidFuture<Boolean> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void name(AndroidFuture<String> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void nextRestorePackage(AndroidFuture<RestoreDescription> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestFullBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void sendBackupData(int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void startRestore(long j, PackageInfo[] packageInfoArr, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void transportDirName(AndroidFuture<String> androidFuture) throws RemoteException {
        }
    }

    void abortFullRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void cancelFullBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void checkFullBackupSize(long j, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void clearBackupData(PackageInfo packageInfo, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void configurationIntent(AndroidFuture<Intent> androidFuture) throws RemoteException;

    void currentDestinationString(AndroidFuture<String> androidFuture) throws RemoteException;

    void dataManagementIntent(AndroidFuture<Intent> androidFuture) throws RemoteException;

    void dataManagementIntentLabel(AndroidFuture<CharSequence> androidFuture) throws RemoteException;

    void finishBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void finishRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void getAvailableRestoreSets(AndroidFuture<List<RestoreSet>> androidFuture) throws RemoteException;

    void getBackupManagerMonitor(AndroidFuture<IBackupManagerMonitor> androidFuture) throws RemoteException;

    void getBackupQuota(String str, boolean z, AndroidFuture<Long> androidFuture) throws RemoteException;

    void getCurrentRestoreSet(AndroidFuture<Long> androidFuture) throws RemoteException;

    void getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void getPackagesThatShouldNotUseRestrictedMode(List<String> list, int i, AndroidFuture<List<String>> androidFuture) throws RemoteException;

    void getRestoreData(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void getTransportFlags(AndroidFuture<Integer> androidFuture) throws RemoteException;

    void initializeDevice(ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void isAppEligibleForBackup(PackageInfo packageInfo, boolean z, AndroidFuture<Boolean> androidFuture) throws RemoteException;

    void name(AndroidFuture<String> androidFuture) throws RemoteException;

    void nextRestorePackage(AndroidFuture<RestoreDescription> androidFuture) throws RemoteException;

    void performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void requestBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException;

    void requestFullBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException;

    void sendBackupData(int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void startRestore(long j, PackageInfo[] packageInfoArr, ITransportStatusCallback iTransportStatusCallback) throws RemoteException;

    void transportDirName(AndroidFuture<String> androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackupTransport {
        public static final String DESCRIPTOR = "com.android.internal.backup.IBackupTransport";
        static final int TRANSACTION_abortFullRestore = 26;
        static final int TRANSACTION_cancelFullBackup = 22;
        static final int TRANSACTION_checkFullBackupSize = 20;
        static final int TRANSACTION_clearBackupData = 10;
        static final int TRANSACTION_configurationIntent = 2;
        static final int TRANSACTION_currentDestinationString = 3;
        static final int TRANSACTION_dataManagementIntent = 4;
        static final int TRANSACTION_dataManagementIntentLabel = 5;
        static final int TRANSACTION_finishBackup = 11;
        static final int TRANSACTION_finishRestore = 17;
        static final int TRANSACTION_getAvailableRestoreSets = 12;
        static final int TRANSACTION_getBackupManagerMonitor = 28;
        static final int TRANSACTION_getBackupQuota = 24;
        static final int TRANSACTION_getCurrentRestoreSet = 13;
        static final int TRANSACTION_getNextFullRestoreDataChunk = 25;
        static final int TRANSACTION_getPackagesThatShouldNotUseRestrictedMode = 29;
        static final int TRANSACTION_getRestoreData = 16;
        static final int TRANSACTION_getTransportFlags = 27;
        static final int TRANSACTION_initializeDevice = 8;
        static final int TRANSACTION_isAppEligibleForBackup = 23;
        static final int TRANSACTION_name = 1;
        static final int TRANSACTION_nextRestorePackage = 15;
        static final int TRANSACTION_performBackup = 9;
        static final int TRANSACTION_performFullBackup = 19;
        static final int TRANSACTION_requestBackupTime = 7;
        static final int TRANSACTION_requestFullBackupTime = 18;
        static final int TRANSACTION_sendBackupData = 21;
        static final int TRANSACTION_startRestore = 14;
        static final int TRANSACTION_transportDirName = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupTransport asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBackupTransport)) {
                return (IBackupTransport) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "name";
                case 2:
                    return "configurationIntent";
                case 3:
                    return "currentDestinationString";
                case 4:
                    return "dataManagementIntent";
                case 5:
                    return "dataManagementIntentLabel";
                case 6:
                    return "transportDirName";
                case 7:
                    return "requestBackupTime";
                case 8:
                    return "initializeDevice";
                case 9:
                    return "performBackup";
                case 10:
                    return "clearBackupData";
                case 11:
                    return "finishBackup";
                case 12:
                    return "getAvailableRestoreSets";
                case 13:
                    return "getCurrentRestoreSet";
                case 14:
                    return "startRestore";
                case 15:
                    return "nextRestorePackage";
                case 16:
                    return "getRestoreData";
                case 17:
                    return "finishRestore";
                case 18:
                    return "requestFullBackupTime";
                case 19:
                    return "performFullBackup";
                case 20:
                    return "checkFullBackupSize";
                case 21:
                    return "sendBackupData";
                case 22:
                    return "cancelFullBackup";
                case 23:
                    return "isAppEligibleForBackup";
                case 24:
                    return "getBackupQuota";
                case 25:
                    return "getNextFullRestoreDataChunk";
                case 26:
                    return "abortFullRestore";
                case 27:
                    return "getTransportFlags";
                case 28:
                    return "getBackupManagerMonitor";
                case 29:
                    return "getPackagesThatShouldNotUseRestrictedMode";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    AndroidFuture<String> androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    name(androidFuture);
                    return true;
                case 2:
                    AndroidFuture<Intent> androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    configurationIntent(androidFuture2);
                    return true;
                case 3:
                    AndroidFuture<String> androidFuture3 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentDestinationString(androidFuture3);
                    return true;
                case 4:
                    AndroidFuture<Intent> androidFuture4 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    dataManagementIntent(androidFuture4);
                    return true;
                case 5:
                    AndroidFuture<CharSequence> androidFuture5 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    dataManagementIntentLabel(androidFuture5);
                    return true;
                case 6:
                    AndroidFuture<String> androidFuture6 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    transportDirName(androidFuture6);
                    return true;
                case 7:
                    AndroidFuture<Long> androidFuture7 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBackupTime(androidFuture7);
                    return true;
                case 8:
                    ITransportStatusCallback asInterface = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    initializeDevice(asInterface);
                    return true;
                case 9:
                    PackageInfo packageInfo = (PackageInfo) parcel.readTypedObject(PackageInfo.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt = parcel.readInt();
                    ITransportStatusCallback asInterface2 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    performBackup(packageInfo, parcelFileDescriptor, readInt, asInterface2);
                    return true;
                case 10:
                    PackageInfo packageInfo2 = (PackageInfo) parcel.readTypedObject(PackageInfo.CREATOR);
                    ITransportStatusCallback asInterface3 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearBackupData(packageInfo2, asInterface3);
                    return true;
                case 11:
                    ITransportStatusCallback asInterface4 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishBackup(asInterface4);
                    return true;
                case 12:
                    AndroidFuture<List<RestoreSet>> androidFuture8 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAvailableRestoreSets(androidFuture8);
                    return true;
                case 13:
                    AndroidFuture<Long> androidFuture9 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCurrentRestoreSet(androidFuture9);
                    return true;
                case 14:
                    long readLong = parcel.readLong();
                    PackageInfo[] packageInfoArr = (PackageInfo[]) parcel.createTypedArray(PackageInfo.CREATOR);
                    ITransportStatusCallback asInterface5 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startRestore(readLong, packageInfoArr, asInterface5);
                    return true;
                case 15:
                    AndroidFuture<RestoreDescription> androidFuture10 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    nextRestorePackage(androidFuture10);
                    return true;
                case 16:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ITransportStatusCallback asInterface6 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getRestoreData(parcelFileDescriptor2, asInterface6);
                    return true;
                case 17:
                    ITransportStatusCallback asInterface7 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishRestore(asInterface7);
                    return true;
                case 18:
                    AndroidFuture<Long> androidFuture11 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestFullBackupTime(androidFuture11);
                    return true;
                case 19:
                    PackageInfo packageInfo3 = (PackageInfo) parcel.readTypedObject(PackageInfo.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt2 = parcel.readInt();
                    ITransportStatusCallback asInterface8 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    performFullBackup(packageInfo3, parcelFileDescriptor3, readInt2, asInterface8);
                    return true;
                case 20:
                    long readLong2 = parcel.readLong();
                    ITransportStatusCallback asInterface9 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    checkFullBackupSize(readLong2, asInterface9);
                    return true;
                case 21:
                    int readInt3 = parcel.readInt();
                    ITransportStatusCallback asInterface10 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendBackupData(readInt3, asInterface10);
                    return true;
                case 22:
                    ITransportStatusCallback asInterface11 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelFullBackup(asInterface11);
                    return true;
                case 23:
                    PackageInfo packageInfo4 = (PackageInfo) parcel.readTypedObject(PackageInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    AndroidFuture<Boolean> androidFuture12 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    isAppEligibleForBackup(packageInfo4, readBoolean, androidFuture12);
                    return true;
                case 24:
                    String readString = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    AndroidFuture<Long> androidFuture13 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getBackupQuota(readString, readBoolean2, androidFuture13);
                    return true;
                case 25:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ITransportStatusCallback asInterface12 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getNextFullRestoreDataChunk(parcelFileDescriptor4, asInterface12);
                    return true;
                case 26:
                    ITransportStatusCallback asInterface13 = ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortFullRestore(asInterface13);
                    return true;
                case 27:
                    AndroidFuture<Integer> androidFuture14 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTransportFlags(androidFuture14);
                    return true;
                case 28:
                    AndroidFuture<IBackupManagerMonitor> androidFuture15 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getBackupManagerMonitor(androidFuture15);
                    return true;
                case 29:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt4 = parcel.readInt();
                    AndroidFuture<List<String>> androidFuture16 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPackagesThatShouldNotUseRestrictedMode(createStringArrayList, readInt4, androidFuture16);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBackupTransport {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void name(AndroidFuture<String> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void configurationIntent(AndroidFuture<Intent> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void currentDestinationString(AndroidFuture<String> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void dataManagementIntent(AndroidFuture<Intent> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void dataManagementIntentLabel(AndroidFuture<CharSequence> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void transportDirName(AndroidFuture<String> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void requestBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void initializeDevice(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void performBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void clearBackupData(PackageInfo packageInfo, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void finishBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getAvailableRestoreSets(AndroidFuture<List<RestoreSet>> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getCurrentRestoreSet(AndroidFuture<Long> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void startRestore(long j, PackageInfo[] packageInfoArr, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedArray(packageInfoArr, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void nextRestorePackage(AndroidFuture<RestoreDescription> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getRestoreData(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void finishRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void requestFullBackupTime(AndroidFuture<Long> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void performFullBackup(PackageInfo packageInfo, ParcelFileDescriptor parcelFileDescriptor, int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void checkFullBackupSize(long j, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void sendBackupData(int i, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void cancelFullBackup(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void isAppEligibleForBackup(PackageInfo packageInfo, boolean z, AndroidFuture<Boolean> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getBackupQuota(String str, boolean z, AndroidFuture<Long> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getNextFullRestoreDataChunk(ParcelFileDescriptor parcelFileDescriptor, ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void abortFullRestore(ITransportStatusCallback iTransportStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getTransportFlags(AndroidFuture<Integer> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getBackupManagerMonitor(AndroidFuture<IBackupManagerMonitor> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getPackagesThatShouldNotUseRestrictedMode(List<String> list, int i, AndroidFuture<List<String>> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
