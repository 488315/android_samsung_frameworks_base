package com.sec.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.ICentralMsgStoreServiceListener;

/* loaded from: classes4.dex */
public interface ICentralMsgStoreService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ICentralMsgStoreService";

    void cancelMessage(String str, String str2) throws RemoteException;

    void createParticipant(String str, String str2) throws RemoteException;

    void createSession(String str, String str2) throws RemoteException;

    void deleteMessage(String str, String str2) throws RemoteException;

    void deleteOldLegacyMessage(String str, String str2) throws RemoteException;

    void deleteParticipant(String str, String str2) throws RemoteException;

    void deleteSession(String str, String str2) throws RemoteException;

    void disableAutoSync(String str, String str2) throws RemoteException;

    void downloadMessage(String str, String str2) throws RemoteException;

    void enableAutoSync(String str, String str2) throws RemoteException;

    void getAccount(int i) throws RemoteException;

    int getRestartScreenName(String str) throws RemoteException;

    void getSd(int i, boolean z, String str) throws RemoteException;

    void manageSd(int i, int i2, String str) throws RemoteException;

    void manualSync(String str, String str2) throws RemoteException;

    void notifyCloudMessageUpdate(String str, String str2, String str3) throws RemoteException;

    void notifyExtendedFuncUpdated() throws RemoteException;

    void notifyUIScreen(String str, int i, String str2, int i2) throws RemoteException;

    void onBufferDBReadResult(String str, String str2, String str3, String str4, int i, boolean z) throws RemoteException;

    void onBufferDBReadResultBatch(String str, String str2) throws RemoteException;

    void onDefaultSmsPackageChanged() throws RemoteException;

    void onDeregistered(ImsRegistration imsRegistration) throws RemoteException;

    void onFTUriResponse(String str, String str2) throws RemoteException;

    void onRCSDBReady(String str) throws RemoteException;

    void onRegistered(ImsRegistration imsRegistration) throws RemoteException;

    boolean onUIButtonProceed(String str, int i, String str2) throws RemoteException;

    void onUserEnterApp(String str) throws RemoteException;

    void onUserLeaveApp(String str) throws RemoteException;

    void readMessage(String str, String str2) throws RemoteException;

    void receivedMessage(String str, String str2) throws RemoteException;

    void registerCallback(String str, ICentralMsgStoreService iCentralMsgStoreService) throws RemoteException;

    void registerCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) throws RemoteException;

    void requestMessageProcess(String str, String str2, int i) throws RemoteException;

    void requestOperation(int i, int i2, String str, String str2) throws RemoteException;

    void restartService(String str) throws RemoteException;

    void resumeSync(String str) throws RemoteException;

    void sendTryDeregisterCms(int i) throws RemoteException;

    void sendTryRegisterCms(int i, String str) throws RemoteException;

    void sentMessage(String str, String str2) throws RemoteException;

    void startContactSyncActivity(int i, boolean z) throws RemoteException;

    void startDeltaSync(String str, String str2) throws RemoteException;

    void startFullSync(String str, String str2) throws RemoteException;

    void stopSync(String str, String str2) throws RemoteException;

    void unReadMessage(String str, String str2) throws RemoteException;

    void unregisterCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) throws RemoteException;

    void updateAccountInfo(int i, String str) throws RemoteException;

    void uploadMessage(String str, String str2) throws RemoteException;

    void wipeOutMessage(String str, String str2) throws RemoteException;

    public class Default implements ICentralMsgStoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public int getRestartScreenName(String str) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public boolean onUIButtonProceed(String str, int i, String str2) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void notifyExtendedFuncUpdated() throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onDefaultSmsPackageChanged() throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void getAccount(int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onDeregistered(ImsRegistration imsRegistration) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onRCSDBReady(String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onRegistered(ImsRegistration imsRegistration) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onUserEnterApp(String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onUserLeaveApp(String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void restartService(String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void resumeSync(String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void sendTryDeregisterCms(int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void cancelMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void createParticipant(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void createSession(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteOldLegacyMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteParticipant(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void deleteSession(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void disableAutoSync(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void downloadMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void enableAutoSync(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void manualSync(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onBufferDBReadResultBatch(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onFTUriResponse(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void readMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void receivedMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void registerCallback(String str, ICentralMsgStoreService iCentralMsgStoreService) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void registerCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void sendTryRegisterCms(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void sentMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void startContactSyncActivity(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void startDeltaSync(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void startFullSync(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void stopSync(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void unReadMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void unregisterCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void updateAccountInfo(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void uploadMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void wipeOutMessage(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void getSd(int i, boolean z, String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void manageSd(int i, int i2, String str) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void notifyCloudMessageUpdate(String str, String str2, String str3) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void requestMessageProcess(String str, String str2, int i) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void notifyUIScreen(String str, int i, String str2, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void requestOperation(int i, int i2, String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.ICentralMsgStoreService
        public void onBufferDBReadResult(String str, String str2, String str3, String str4, int i, boolean z) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements ICentralMsgStoreService {
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
        static final int TRANSACTION_notifyExtendedFuncUpdated = 43;
        static final int TRANSACTION_notifyUIScreen = 27;
        static final int TRANSACTION_onBufferDBReadResult = 13;
        static final int TRANSACTION_onBufferDBReadResultBatch = 14;
        static final int TRANSACTION_onDefaultSmsPackageChanged = 44;
        static final int TRANSACTION_onDeregistered = 46;
        static final int TRANSACTION_onFTUriResponse = 32;
        static final int TRANSACTION_onRCSDBReady = 23;
        static final int TRANSACTION_onRegistered = 45;
        static final int TRANSACTION_onUIButtonProceed = 12;
        static final int TRANSACTION_onUserEnterApp = 10;
        static final int TRANSACTION_onUserLeaveApp = 11;
        static final int TRANSACTION_readMessage = 3;
        static final int TRANSACTION_receivedMessage = 1;
        static final int TRANSACTION_registerCallback = 15;
        static final int TRANSACTION_registerCmsProvisioningListenerByPhoneId = 47;
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
        static final int TRANSACTION_unregisterCmsProvisioningListenerByPhoneId = 48;
        static final int TRANSACTION_updateAccountInfo = 39;
        static final int TRANSACTION_uploadMessage = 7;
        static final int TRANSACTION_wipeOutMessage = 9;

        class Proxy implements ICentralMsgStoreService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void cancelMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void createParticipant(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void createSession(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteOldLegacyMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteParticipant(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void deleteSession(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void disableAutoSync(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void downloadMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void enableAutoSync(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void getAccount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICentralMsgStoreService.DESCRIPTOR;
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public int getRestartScreenName(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void getSd(int i, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void manageSd(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void manualSync(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void notifyCloudMessageUpdate(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void notifyExtendedFuncUpdated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void notifyUIScreen(String str, int i, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onBufferDBReadResult(String str, String str2, String str3, String str4, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onBufferDBReadResultBatch(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onDefaultSmsPackageChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onDeregistered(ImsRegistration imsRegistration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsRegistration, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onFTUriResponse(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onRCSDBReady(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onRegistered(ImsRegistration imsRegistration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsRegistration, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public boolean onUIButtonProceed(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onUserEnterApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void onUserLeaveApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void readMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void receivedMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void registerCallback(String str, ICentralMsgStoreService iCentralMsgStoreService) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iCentralMsgStoreService);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void registerCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCentralMsgStoreServiceListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void requestMessageProcess(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void requestOperation(int i, int i2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void restartService(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void resumeSync(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void sendTryDeregisterCms(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void sendTryRegisterCms(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void sentMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void startContactSyncActivity(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void startDeltaSync(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void startFullSync(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void stopSync(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void unReadMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void unregisterCmsProvisioningListenerByPhoneId(ICentralMsgStoreServiceListener iCentralMsgStoreServiceListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCentralMsgStoreServiceListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void updateAccountInfo(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void uploadMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.ICentralMsgStoreService
            public void wipeOutMessage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICentralMsgStoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICentralMsgStoreService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICentralMsgStoreService)) ? new Proxy(iBinder) : (ICentralMsgStoreService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICentralMsgStoreService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICentralMsgStoreService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    receivedMessage(string, string2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sentMessage(string3, string4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    readMessage(string5, string6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unReadMessage(string7, string8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelMessage(string9, string10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteMessage(string11, string12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    uploadMessage(string13, string14);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    downloadMessage(string15, string16);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wipeOutMessage(string17, string18);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUserEnterApp(string19);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUserLeaveApp(string20);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string21 = parcel.readString();
                    int i3 = parcel.readInt();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zOnUIButtonProceed = onUIButtonProceed(string21, i3, string22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOnUIButtonProceed);
                    return true;
                case 13:
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBufferDBReadResult(string23, string24, string25, string26, i4, z);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBufferDBReadResultBatch(string27, string28);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string29 = parcel.readString();
                    ICentralMsgStoreService iCentralMsgStoreServiceAsInterface = asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(string29, iCentralMsgStoreServiceAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopSync(string30, string31);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startFullSync(string32, string33);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCloudMessageUpdate(string34, string35, string36);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string37 = parcel.readString();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createSession(string37, string38);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string39 = parcel.readString();
                    String string40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createParticipant(string39, string40);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String string41 = parcel.readString();
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteSession(string41, string42);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String string43 = parcel.readString();
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteParticipant(string43, string44);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRCSDBReady(string45);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string46 = parcel.readString();
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteOldLegacyMessage(string46, string47);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resumeSync(string48);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restartService(string49);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String string50 = parcel.readString();
                    int i5 = parcel.readInt();
                    String string51 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyUIScreen(string50, i5, string51, i6);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string52 = parcel.readString();
                    String string53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    manualSync(string52, string53);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String string54 = parcel.readString();
                    String string55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableAutoSync(string54, string55);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String string56 = parcel.readString();
                    String string57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableAutoSync(string56, string57);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String string58 = parcel.readString();
                    String string59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startDeltaSync(string58, string59);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    String string60 = parcel.readString();
                    String string61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onFTUriResponse(string60, string61);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String string62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int restartScreenName = getRestartScreenName(string62);
                    parcel2.writeNoException();
                    parcel2.writeInt(restartScreenName);
                    return true;
                case 34:
                    int i7 = parcel.readInt();
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendTryRegisterCms(i7, string63);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTryDeregisterCms(i8);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    String string64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    manageSd(i9, i10, string64);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i11 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getSd(i11, z2, string65);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAccount(i12);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i13 = parcel.readInt();
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAccountInfo(i13, string66);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    String string67 = parcel.readString();
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestOperation(i14, i15, string67, string68);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    String string69 = parcel.readString();
                    String string70 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestMessageProcess(string69, string70, i16);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i17 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startContactSyncActivity(i17, z3);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    notifyExtendedFuncUpdated();
                    parcel2.writeNoException();
                    return true;
                case 44:
                    onDefaultSmsPackageChanged();
                    parcel2.writeNoException();
                    return true;
                case 45:
                    ImsRegistration imsRegistration = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRegistered(imsRegistration);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    ImsRegistration imsRegistration2 = (ImsRegistration) parcel.readTypedObject(ImsRegistration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeregistered(imsRegistration2);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    ICentralMsgStoreServiceListener iCentralMsgStoreServiceListenerAsInterface = ICentralMsgStoreServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCmsProvisioningListenerByPhoneId(iCentralMsgStoreServiceListenerAsInterface, i18);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    ICentralMsgStoreServiceListener iCentralMsgStoreServiceListenerAsInterface2 = ICentralMsgStoreServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCmsProvisioningListenerByPhoneId(iCentralMsgStoreServiceListenerAsInterface2, i19);
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
