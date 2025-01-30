package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.ICentralMsgStoreServiceListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ICentralMsgStoreService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ICentralMsgStoreService";

    void cancelMessage(String str, String str2);

    void createParticipant(String str, String str2);

    void createSession(String str, String str2);

    void deleteMessage(String str, String str2);

    void deleteOldLegacyMessage(String str, String str2);

    void deleteParticipant(String str, String str2);

    void deleteSession(String str, String str2);

    void disableAutoSync(String str, String str2);

    void downloadMessage(String str, String str2);

    void enableAutoSync(String str, String str2);

    void getAccount(int i);

    int getRestartScreenName(String str);

    void getSd(int i, boolean z, String str);

    void manageSd(int i, int i2, String str);

    void manualSync(String str, String str2);

    void notifyCloudMessageUpdate(String str, String str2, String str3);

    void notifyUIScreen(String str, int i, String str2, int i2);

    void onBufferDBReadResult(String str, String str2, String str3, String str4, int i, boolean z);

    void onBufferDBReadResultBatch(String str, String str2);

    void onDefaultSmsPackageChanged();

    void onDeregistered(ImsRegistration imsRegistration);

    void onFTUriResponse(String str, String str2);

    void onRCSDBReady(String str);

    void onRegistered(ImsRegistration imsRegistration);

    boolean onUIButtonProceed(String str, int i, String str2);

    void onUserEnterApp(String str);

    void onUserLeaveApp(String str);

    void readMessage(String str, String str2);

    void receivedMessage(String str, String str2);

    void registerCallback(String str, ICentralMsgStoreService iCentralMsgStoreService);

    void registerCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i);

    void requestMessageProcess(String str, String str2, int i);

    void requestOperation(int i, int i2, String str, String str2);

    void restartService(String str);

    void resumeSync(String str);

    void sendTryDeregisterCms(int i);

    void sendTryRegisterCms(int i, String str);

    void sentMessage(String str, String str2);

    void startContactSyncActivity(int i, boolean z);

    void startDeltaSync(String str, String str2);

    void startFullSync(String str, String str2);

    void stopSync(String str, String str2);

    void unReadMessage(String str, String str2);

    void unregisterCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i);

    void updateAccountInfo(int i, String str);

    void uploadMessage(String str, String str2);

    void wipeOutMessage(String str, String str2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ICentralMsgStoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public int getRestartScreenName(String str) {
            return 0;
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public boolean onUIButtonProceed(String str, int i, String str2) {
            return false;
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onDefaultSmsPackageChanged() {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void getAccount(int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onDeregistered(ImsRegistration imsRegistration) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onRCSDBReady(String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onRegistered(ImsRegistration imsRegistration) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onUserEnterApp(String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onUserLeaveApp(String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void restartService(String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void resumeSync(String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void sendTryDeregisterCms(int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void cancelMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void createParticipant(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void createSession(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteOldLegacyMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteParticipant(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteSession(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void disableAutoSync(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void downloadMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void enableAutoSync(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void manualSync(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onBufferDBReadResultBatch(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onFTUriResponse(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void readMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void receivedMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void registerCallback(String str, ICentralMsgStoreService iCentralMsgStoreService) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void registerCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void sendTryRegisterCms(int i, String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void sentMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void startContactSyncActivity(int i, boolean z) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void startDeltaSync(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void startFullSync(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void stopSync(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void unReadMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void unregisterCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void updateAccountInfo(int i, String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void uploadMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void wipeOutMessage(String str, String str2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void getSd(int i, boolean z, String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void manageSd(int i, int i2, String str) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void notifyCloudMessageUpdate(String str, String str2, String str3) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void requestMessageProcess(String str, String str2, int i) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onBufferDBReadResult(String str, String str2, String str3, String str4, int i, boolean z) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void notifyUIScreen(String str, int i, String str2, int i2) {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void requestOperation(int i, int i2, String str, String str2) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ICentralMsgStoreService {
        static final int TRANSACTION_cancelMessage = 5;
        static final int TRANSACTION_createParticipant = 20;
        static final int TRANSACTION_createSession = 19;
        static final int TRANSACTION_deleteMessage = 6;
        static final int TRANSACTION_deleteOldLegacyMessage = 24;
        static final int TRANSACTION_deleteParticipant = 22;
        static final int TRANSACTION_deleteSession = 21;
        static final int TRANSACTION_disableAutoSync = 30;
        static final int TRANSACTION_downloadMessage = 8;
        static final int TRANSACTION_enableAutoSync = 29;
        static final int TRANSACTION_getAccount = 38;
        static final int TRANSACTION_getRestartScreenName = 33;
        static final int TRANSACTION_getSd = 37;
        static final int TRANSACTION_manageSd = 36;
        static final int TRANSACTION_manualSync = 28;
        static final int TRANSACTION_notifyCloudMessageUpdate = 18;
        static final int TRANSACTION_notifyUIScreen = 27;
        static final int TRANSACTION_onBufferDBReadResult = 13;
        static final int TRANSACTION_onBufferDBReadResultBatch = 14;
        static final int TRANSACTION_onDefaultSmsPackageChanged = 43;
        static final int TRANSACTION_onDeregistered = 45;
        static final int TRANSACTION_onFTUriResponse = 32;
        static final int TRANSACTION_onRCSDBReady = 23;
        static final int TRANSACTION_onRegistered = 44;
        static final int TRANSACTION_onUIButtonProceed = 12;
        static final int TRANSACTION_onUserEnterApp = 10;
        static final int TRANSACTION_onUserLeaveApp = 11;
        static final int TRANSACTION_readMessage = 3;
        static final int TRANSACTION_receivedMessage = 1;
        static final int TRANSACTION_registerCallback = 15;
        static final int TRANSACTION_registerCmsProvisioningListenerByPhoneId = 46;
        static final int TRANSACTION_requestMessageProcess = 41;
        static final int TRANSACTION_requestOperation = 40;
        static final int TRANSACTION_restartService = 26;
        static final int TRANSACTION_resumeSync = 25;
        static final int TRANSACTION_sendTryDeregisterCms = 35;
        static final int TRANSACTION_sendTryRegisterCms = 34;
        static final int TRANSACTION_sentMessage = 2;
        static final int TRANSACTION_startContactSyncActivity = 42;
        static final int TRANSACTION_startDeltaSync = 31;
        static final int TRANSACTION_startFullSync = 17;
        static final int TRANSACTION_stopSync = 16;
        static final int TRANSACTION_unReadMessage = 4;
        static final int TRANSACTION_unregisterCmsProvisioningListenerByPhoneId = 47;
        static final int TRANSACTION_updateAccountInfo = 39;
        static final int TRANSACTION_uploadMessage = 7;
        static final int TRANSACTION_wipeOutMessage = 9;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ICentralMsgStoreService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void cancelMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void createParticipant(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void createSession(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteOldLegacyMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteParticipant(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteSession(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void disableAutoSync(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void downloadMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void enableAutoSync(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void getAccount(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICentralMsgStoreService.DESCRIPTOR;
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public int getRestartScreenName(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void getSd(int i, boolean z, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void manageSd(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void manualSync(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void notifyCloudMessageUpdate(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void notifyUIScreen(String str, int i, String str2, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onBufferDBReadResult(String str, String str2, String str3, String str4, int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onBufferDBReadResultBatch(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onDefaultSmsPackageChanged() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onDeregistered(ImsRegistration imsRegistration) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistration, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onFTUriResponse(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onRCSDBReady(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onRegistered(ImsRegistration imsRegistration) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeTypedObject(imsRegistration, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public boolean onUIButtonProceed(String str, int i, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onUserEnterApp(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onUserLeaveApp(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void readMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void receivedMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void registerCallback(String str, ICentralMsgStoreService iCentralMsgStoreService) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iCentralMsgStoreService);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void registerCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCentralMsgStoreServiceListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void requestMessageProcess(String str, String str2, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void requestOperation(int i, int i2, String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void restartService(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void resumeSync(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void sendTryDeregisterCms(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void sendTryRegisterCms(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void sentMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void startContactSyncActivity(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void startDeltaSync(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void startFullSync(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void stopSync(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void unReadMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void unregisterCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCentralMsgStoreServiceListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void updateAccountInfo(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void uploadMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void wipeOutMessage(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICentralMsgStoreService.DESCRIPTOR);
        }

        public static ICentralMsgStoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICentralMsgStoreService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICentralMsgStoreService)) ? new Proxy(iBinder) : (ICentralMsgStoreService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICentralMsgStoreService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICentralMsgStoreService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    receivedMessage(readString, readString2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sentMessage(readString3, readString4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    readMessage(readString5, readString6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unReadMessage(readString7, readString8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelMessage(readString9, readString10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteMessage(readString11, readString12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    uploadMessage(readString13, readString14);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    downloadMessage(readString15, readString16);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wipeOutMessage(readString17, readString18);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUserEnterApp(readString19);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUserLeaveApp(readString20);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString21 = parcel.readString();
                    int readInt = parcel.readInt();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean onUIButtonProceed = onUIButtonProceed(readString21, readInt, readString22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(onUIButtonProceed);
                    return true;
                case 13:
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBufferDBReadResult(readString23, readString24, readString25, readString26, readInt2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBufferDBReadResultBatch(readString27, readString28);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString29 = parcel.readString();
                    ICentralMsgStoreService asInterface = asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(readString29, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopSync(readString30, readString31);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startFullSync(readString32, readString33);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCloudMessageUpdate(readString34, readString35, readString36);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createSession(readString37, readString38);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createParticipant(readString39, readString40);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteSession(readString41, readString42);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteParticipant(readString43, readString44);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRCSDBReady(readString45);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String readString46 = parcel.readString();
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteOldLegacyMessage(readString46, readString47);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resumeSync(readString48);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restartService(readString49);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString50 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    String readString51 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyUIScreen(readString50, readInt3, readString51, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString52 = parcel.readString();
                    String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    manualSync(readString52, readString53);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString54 = parcel.readString();
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableAutoSync(readString54, readString55);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString56 = parcel.readString();
                    String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableAutoSync(readString56, readString57);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString58 = parcel.readString();
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startDeltaSync(readString58, readString59);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String readString60 = parcel.readString();
                    String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onFTUriResponse(readString60, readString61);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int restartScreenName = getRestartScreenName(readString62);
                    parcel2.writeNoException();
                    parcel2.writeInt(restartScreenName);
                    return true;
                case 34:
                    int readInt5 = parcel.readInt();
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterCms(readInt5, readString63);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryDeregisterCms(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    manageSd(readInt7, readInt8, readString64);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getSd(readInt9, readBoolean2, readString65);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAccount(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt11 = parcel.readInt();
                    String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAccountInfo(readInt11, readString66);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    String readString67 = parcel.readString();
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestOperation(readInt12, readInt13, readString67, readString68);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String readString69 = parcel.readString();
                    String readString70 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestMessageProcess(readString69, readString70, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt15 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startContactSyncActivity(readInt15, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    onDefaultSmsPackageChanged();
                    parcel2.writeNoException();
                    return true;
                case 44:
                    ImsRegistration imsRegistration = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRegistered(imsRegistration);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    ImsRegistration imsRegistration2 = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeregistered(imsRegistration2);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    ICentralMsgStoreServiceListener asInterface2 = ICentralMsgStoreServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCmsProvisioningListenerByPhoneId(asInterface2, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    ICentralMsgStoreServiceListener asInterface3 = ICentralMsgStoreServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCmsProvisioningListenerByPhoneId(asInterface3, readInt17);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
