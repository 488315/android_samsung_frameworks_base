package com.samsung.android.knox.ucm.plugin.agent;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IUcmAgentService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService";

    public class Default implements IUcmAgentService {
        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle APDUCommand(byte[] bArr, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle changePin(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle changePinWithPassword(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle containsAlias(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle delete(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateDek() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKey(String str, String str2, int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKeyguardPassword(int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateWrappedDek() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCertificateChain(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCredentialStoragePluginConfiguration(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getDek() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public String getDetailErrorMessage(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyType(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinCurrentRetryCount() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMaximumLength() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMaximumRetryCount() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMinimumLength() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getStatus() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle importKey(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle initKeyguardPin(String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle keyAgreement(String str, String str2, byte[] bArr, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle keyguardPasswordUpdated(boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public int notifyChange(int i, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle processCommand(byte[] bArr, Bundle bundle, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle resetUid(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle resetUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle saw(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMaximumLength(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMaximumRetryCount(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMinimumLength(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setState(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle unwrapDek(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPassword(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPin(int i, String str, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPuk(String str, String str2) throws RemoteException {
            return null;
        }
    }

    Bundle APDUCommand(byte[] bArr, Bundle bundle) throws RemoteException;

    Bundle changePin(String str, String str2) throws RemoteException;

    Bundle changePinWithPassword(String str, String str2) throws RemoteException;

    Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2) throws RemoteException;

    Bundle containsAlias(String str, int i, int i2) throws RemoteException;

    Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    Bundle delete(String str, Bundle bundle) throws RemoteException;

    Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    Bundle generateDek() throws RemoteException;

    Bundle generateKey(String str, String str2, int i, Bundle bundle) throws RemoteException;

    Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) throws RemoteException;

    Bundle generateKeyguardPassword(int i, Bundle bundle) throws RemoteException;

    Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle) throws RemoteException;

    Bundle generateWrappedDek() throws RemoteException;

    Bundle getCertificateChain(String str, Bundle bundle) throws RemoteException;

    Bundle getCredentialStoragePluginConfiguration(int i) throws RemoteException;

    Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle) throws RemoteException;

    Bundle getDek() throws RemoteException;

    String getDetailErrorMessage(int i) throws RemoteException;

    Bundle getInfo() throws RemoteException;

    Bundle getKeyType(String str, Bundle bundle) throws RemoteException;

    Bundle getKeyguardPinCurrentRetryCount() throws RemoteException;

    Bundle getKeyguardPinMaximumLength() throws RemoteException;

    Bundle getKeyguardPinMaximumRetryCount() throws RemoteException;

    Bundle getKeyguardPinMinimumLength() throws RemoteException;

    Bundle getStatus() throws RemoteException;

    Bundle importKey(String str, Bundle bundle) throws RemoteException;

    Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException;

    Bundle initKeyguardPin(String str, Bundle bundle) throws RemoteException;

    Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    Bundle keyAgreement(String str, String str2, byte[] bArr, Bundle bundle) throws RemoteException;

    Bundle keyguardPasswordUpdated(boolean z) throws RemoteException;

    Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException;

    int notifyChange(int i, Bundle bundle) throws RemoteException;

    Bundle processCommand(byte[] bArr, Bundle bundle, int i) throws RemoteException;

    Bundle resetUid(int i) throws RemoteException;

    Bundle resetUser(int i) throws RemoteException;

    Bundle saw(Bundle bundle) throws RemoteException;

    Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) throws RemoteException;

    Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle) throws RemoteException;

    Bundle setKeyguardPinMaximumLength(int i) throws RemoteException;

    Bundle setKeyguardPinMaximumRetryCount(int i) throws RemoteException;

    Bundle setKeyguardPinMinimumLength(int i) throws RemoteException;

    Bundle setState(int i) throws RemoteException;

    Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle) throws RemoteException;

    Bundle unwrapDek(byte[] bArr) throws RemoteException;

    Bundle verifyPassword(String str) throws RemoteException;

    Bundle verifyPin(int i, String str, Bundle bundle) throws RemoteException;

    Bundle verifyPuk(String str, String str2) throws RemoteException;

    public abstract class Stub extends Binder implements IUcmAgentService {
        static final int TRANSACTION_APDUCommand = 28;
        static final int TRANSACTION_changePin = 25;
        static final int TRANSACTION_changePinWithPassword = 47;
        static final int TRANSACTION_configureCredentialStoragePlugin = 11;
        static final int TRANSACTION_containsAlias = 18;
        static final int TRANSACTION_decrypt = 4;
        static final int TRANSACTION_delete = 7;
        static final int TRANSACTION_encrypt = 33;
        static final int TRANSACTION_generateDek = 19;
        static final int TRANSACTION_generateKey = 43;
        static final int TRANSACTION_generateKeyPair = 8;
        static final int TRANSACTION_generateKeyguardPassword = 31;
        static final int TRANSACTION_generateSecureRandom = 9;
        static final int TRANSACTION_generateWrappedDek = 20;
        static final int TRANSACTION_getCertificateChain = 1;
        static final int TRANSACTION_getCredentialStoragePluginConfiguration = 12;
        static final int TRANSACTION_getCredentialStorageProperty = 13;
        static final int TRANSACTION_getDek = 21;
        static final int TRANSACTION_getDetailErrorMessage = 32;
        static final int TRANSACTION_getInfo = 27;
        static final int TRANSACTION_getKeyType = 44;
        static final int TRANSACTION_getKeyguardPinCurrentRetryCount = 39;
        static final int TRANSACTION_getKeyguardPinMaximumLength = 41;
        static final int TRANSACTION_getKeyguardPinMaximumRetryCount = 38;
        static final int TRANSACTION_getKeyguardPinMinimumLength = 40;
        static final int TRANSACTION_getStatus = 30;
        static final int TRANSACTION_importKey = 42;
        static final int TRANSACTION_importKeyPair = 5;
        static final int TRANSACTION_initKeyguardPin = 34;
        static final int TRANSACTION_installCertificateIfSupported = 45;
        static final int TRANSACTION_keyAgreement = 49;
        static final int TRANSACTION_keyguardPasswordUpdated = 48;
        static final int TRANSACTION_mac = 46;
        static final int TRANSACTION_notifyChange = 10;
        static final int TRANSACTION_processCommand = 17;
        static final int TRANSACTION_resetUid = 16;
        static final int TRANSACTION_resetUser = 15;
        static final int TRANSACTION_saw = 2;
        static final int TRANSACTION_setCertificateChain = 6;
        static final int TRANSACTION_setCredentialStorageProperty = 14;
        static final int TRANSACTION_setKeyguardPinMaximumLength = 37;
        static final int TRANSACTION_setKeyguardPinMaximumRetryCount = 35;
        static final int TRANSACTION_setKeyguardPinMinimumLength = 36;
        static final int TRANSACTION_setState = 26;
        static final int TRANSACTION_sign = 3;
        static final int TRANSACTION_unwrapDek = 22;
        static final int TRANSACTION_verifyPassword = 29;
        static final int TRANSACTION_verifyPin = 23;
        static final int TRANSACTION_verifyPuk = 24;

        class Proxy implements IUcmAgentService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle APDUCommand(byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle changePin(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle changePinWithPassword(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle containsAlias(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle delete(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateDek() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateKey(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateKeyguardPassword(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateWrappedDek() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getCertificateChain(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getCredentialStoragePluginConfiguration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getDek() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public String getDetailErrorMessage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IUcmAgentService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyType(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinCurrentRetryCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinMaximumLength() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinMaximumRetryCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinMinimumLength() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle importKey(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle initKeyguardPin(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle keyAgreement(String str, String str2, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle keyguardPasswordUpdated(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public int notifyChange(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle processCommand(byte[] bArr, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle resetUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle resetUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle saw(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setKeyguardPinMaximumLength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setKeyguardPinMaximumRetryCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setKeyguardPinMinimumLength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle unwrapDek(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle verifyPassword(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle verifyPin(int i, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle verifyPuk(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUcmAgentService.DESCRIPTOR);
        }

        public static IUcmAgentService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IUcmAgentService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IUcmAgentService)) ? new Proxy(iBinder) : (IUcmAgentService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUcmAgentService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUcmAgentService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle certificateChain = getCertificateChain(string, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain, 1);
                    return true;
                case 2:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleSaw = saw(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleSaw, 1);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string3 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleSign = sign(string2, bArrCreateByteArray, string3, z, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleSign, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    String string5 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleDecrypt = decrypt(string4, bArrCreateByteArray2, string5, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleDecrypt, 1);
                    return true;
                case 5:
                    String string6 = parcel.readString();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleImportKeyPair = importKeyPair(string6, bArrCreateByteArray3, bArrCreateByteArray4, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleImportKeyPair, 1);
                    return true;
                case 6:
                    String string7 = parcel.readString();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle certificateChain2 = setCertificateChain(string7, bArrCreateByteArray5, bundle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain2, 1);
                    return true;
                case 7:
                    String string8 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleDelete = delete(string8, bundle7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleDelete, 1);
                    return true;
                case 8:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i3 = parcel.readInt();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateKeyPair = generateKeyPair(string9, string10, i3, bundle8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateKeyPair, 1);
                    return true;
                case 9:
                    int i4 = parcel.readInt();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateSecureRandom = generateSecureRandom(i4, bArrCreateByteArray6, bundle9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateSecureRandom, 1);
                    return true;
                case 10:
                    int i5 = parcel.readInt();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iNotifyChange = notifyChange(i5, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iNotifyChange);
                    return true;
                case 11:
                    int i6 = parcel.readInt();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleConfigureCredentialStoragePlugin = configureCredentialStoragePlugin(i6, bundle11, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleConfigureCredentialStoragePlugin, 1);
                    return true;
                case 12:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle credentialStoragePluginConfiguration = getCredentialStoragePluginConfiguration(i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStoragePluginConfiguration, 1);
                    return true;
                case 13:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty = getCredentialStorageProperty(i9, i10, bundle12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty, 1);
                    return true;
                case 14:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty2 = setCredentialStorageProperty(i11, i12, bundle13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty2, 1);
                    return true;
                case 15:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleResetUser = resetUser(i13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleResetUser, 1);
                    return true;
                case 16:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleResetUid = resetUid(i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleResetUid, 1);
                    return true;
                case 17:
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleProcessCommand = processCommand(bArrCreateByteArray7, bundle14, i15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleProcessCommand, 1);
                    return true;
                case 18:
                    String string11 = parcel.readString();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle bundleContainsAlias = containsAlias(string11, i16, i17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleContainsAlias, 1);
                    return true;
                case 19:
                    Bundle bundleGenerateDek = generateDek();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateDek, 1);
                    return true;
                case 20:
                    Bundle bundleGenerateWrappedDek = generateWrappedDek();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateWrappedDek, 1);
                    return true;
                case 21:
                    Bundle dek = getDek();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dek, 1);
                    return true;
                case 22:
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    Bundle bundleUnwrapDek = unwrapDek(bArrCreateByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleUnwrapDek, 1);
                    return true;
                case 23:
                    int i18 = parcel.readInt();
                    String string12 = parcel.readString();
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleVerifyPin = verifyPin(i18, string12, bundle15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleVerifyPin, 1);
                    return true;
                case 24:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle bundleVerifyPuk = verifyPuk(string13, string14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleVerifyPuk, 1);
                    return true;
                case 25:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle bundleChangePin = changePin(string15, string16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleChangePin, 1);
                    return true;
                case 26:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle state = setState(i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 27:
                    Bundle info = getInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(info, 1);
                    return true;
                case 28:
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleAPDUCommand = APDUCommand(bArrCreateByteArray9, bundle16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleAPDUCommand, 1);
                    return true;
                case 29:
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle bundleVerifyPassword = verifyPassword(string17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleVerifyPassword, 1);
                    return true;
                case 30:
                    Bundle status = getStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(status, 1);
                    return true;
                case 31:
                    int i20 = parcel.readInt();
                    Bundle bundle17 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateKeyguardPassword = generateKeyguardPassword(i20, bundle17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateKeyguardPassword, 1);
                    return true;
                case 32:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String detailErrorMessage = getDetailErrorMessage(i21);
                    parcel2.writeNoException();
                    parcel2.writeString(detailErrorMessage);
                    return true;
                case 33:
                    String string18 = parcel.readString();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    String string19 = parcel.readString();
                    Bundle bundle18 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleEncrypt = encrypt(string18, bArrCreateByteArray10, string19, bundle18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleEncrypt, 1);
                    return true;
                case 34:
                    String string20 = parcel.readString();
                    Bundle bundle19 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleInitKeyguardPin = initKeyguardPin(string20, bundle19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleInitKeyguardPin, 1);
                    return true;
                case 35:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount = setKeyguardPinMaximumRetryCount(i22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount, 1);
                    return true;
                case 36:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength = setKeyguardPinMinimumLength(i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength, 1);
                    return true;
                case 37:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength = setKeyguardPinMaximumLength(i24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumLength, 1);
                    return true;
                case 38:
                    Bundle keyguardPinMaximumRetryCount2 = getKeyguardPinMaximumRetryCount();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount2, 1);
                    return true;
                case 39:
                    Bundle keyguardPinCurrentRetryCount = getKeyguardPinCurrentRetryCount();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinCurrentRetryCount, 1);
                    return true;
                case 40:
                    Bundle keyguardPinMinimumLength2 = getKeyguardPinMinimumLength();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength2, 1);
                    return true;
                case 41:
                    Bundle keyguardPinMaximumLength2 = getKeyguardPinMaximumLength();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumLength2, 1);
                    return true;
                case 42:
                    String string21 = parcel.readString();
                    Bundle bundle20 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleImportKey = importKey(string21, bundle20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleImportKey, 1);
                    return true;
                case 43:
                    String string22 = parcel.readString();
                    String string23 = parcel.readString();
                    int i25 = parcel.readInt();
                    Bundle bundle21 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleGenerateKey = generateKey(string22, string23, i25, bundle21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleGenerateKey, 1);
                    return true;
                case 44:
                    String string24 = parcel.readString();
                    Bundle bundle22 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle keyType = getKeyType(string24, bundle22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyType, 1);
                    return true;
                case 45:
                    String string25 = parcel.readString();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    String string26 = parcel.readString();
                    Bundle bundle23 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleInstallCertificateIfSupported = installCertificateIfSupported(string25, bArrCreateByteArray11, string26, bundle23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleInstallCertificateIfSupported, 1);
                    return true;
                case 46:
                    String string27 = parcel.readString();
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    String string28 = parcel.readString();
                    Bundle bundle24 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleMac = mac(string27, bArrCreateByteArray12, string28, bundle24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleMac, 1);
                    return true;
                case 47:
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle bundleChangePinWithPassword = changePinWithPassword(string29, string30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleChangePinWithPassword, 1);
                    return true;
                case 48:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    Bundle bundleKeyguardPasswordUpdated = keyguardPasswordUpdated(z2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleKeyguardPasswordUpdated, 1);
                    return true;
                case 49:
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    byte[] bArrCreateByteArray13 = parcel.createByteArray();
                    Bundle bundle25 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleKeyAgreement = keyAgreement(string31, string32, bArrCreateByteArray13, bundle25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleKeyAgreement, 1);
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
