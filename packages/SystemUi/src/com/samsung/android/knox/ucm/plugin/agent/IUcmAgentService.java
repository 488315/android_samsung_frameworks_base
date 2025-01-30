package com.samsung.android.knox.ucm.plugin.agent;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IUcmAgentService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IUcmAgentService {
        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle APDUCommand(byte[] bArr, Bundle bundle) {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle changePin(String str, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle changePinWithPassword(String str, String str2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle containsAlias(String str, int i, int i2) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle delete(String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateDek() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKey(String str, String str2, int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateKeyguardPassword(int i, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle generateWrappedDek() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCertificateChain(String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCredentialStoragePluginConfiguration(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getDek() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public String getDetailErrorMessage(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getInfo() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyType(String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinCurrentRetryCount() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMaximumLength() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMaximumRetryCount() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getKeyguardPinMinimumLength() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle getStatus() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle importKey(String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle initKeyguardPin(String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public int notifyChange(int i, Bundle bundle) {
            return 0;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle processCommand(byte[] bArr, Bundle bundle, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle resetUid(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle resetUser(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle saw(Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMaximumLength(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMaximumRetryCount(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setKeyguardPinMinimumLength(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle setState(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle unwrapDek(byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPassword(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPin(int i, String str, Bundle bundle) {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
        public Bundle verifyPuk(String str, String str2) {
            return null;
        }
    }

    Bundle APDUCommand(byte[] bArr, Bundle bundle);

    Bundle changePin(String str, String str2);

    Bundle changePinWithPassword(String str, String str2);

    Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2);

    Bundle containsAlias(String str, int i, int i2);

    Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle);

    Bundle delete(String str, Bundle bundle);

    Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle);

    Bundle generateDek();

    Bundle generateKey(String str, String str2, int i, Bundle bundle);

    Bundle generateKeyPair(String str, String str2, int i, Bundle bundle);

    Bundle generateKeyguardPassword(int i, Bundle bundle);

    Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle);

    Bundle generateWrappedDek();

    Bundle getCertificateChain(String str, Bundle bundle);

    Bundle getCredentialStoragePluginConfiguration(int i);

    Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle);

    Bundle getDek();

    String getDetailErrorMessage(int i);

    Bundle getInfo();

    Bundle getKeyType(String str, Bundle bundle);

    Bundle getKeyguardPinCurrentRetryCount();

    Bundle getKeyguardPinMaximumLength();

    Bundle getKeyguardPinMaximumRetryCount();

    Bundle getKeyguardPinMinimumLength();

    Bundle getStatus();

    Bundle importKey(String str, Bundle bundle);

    Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle);

    Bundle initKeyguardPin(String str, Bundle bundle);

    Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle);

    Bundle mac(String str, byte[] bArr, String str2, Bundle bundle);

    int notifyChange(int i, Bundle bundle);

    Bundle processCommand(byte[] bArr, Bundle bundle, int i);

    Bundle resetUid(int i);

    Bundle resetUser(int i);

    Bundle saw(Bundle bundle);

    Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle);

    Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle);

    Bundle setKeyguardPinMaximumLength(int i);

    Bundle setKeyguardPinMaximumRetryCount(int i);

    Bundle setKeyguardPinMinimumLength(int i);

    Bundle setState(int i);

    Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle);

    Bundle unwrapDek(byte[] bArr);

    Bundle verifyPassword(String str);

    Bundle verifyPin(int i, String str, Bundle bundle);

    Bundle verifyPuk(String str, String str2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IUcmAgentService {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IUcmAgentService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle APDUCommand(byte[] bArr, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle changePin(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle changePinWithPassword(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle configureCredentialStoragePlugin(int i, Bundle bundle, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle containsAlias(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle decrypt(String str, byte[] bArr, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle delete(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle encrypt(String str, byte[] bArr, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateDek() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateKey(String str, String str2, int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateKeyPair(String str, String str2, int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateKeyguardPassword(int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateSecureRandom(int i, byte[] bArr, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle generateWrappedDek() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getCertificateChain(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getCredentialStoragePluginConfiguration(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getCredentialStorageProperty(int i, int i2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getDek() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public String getDetailErrorMessage(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getInfo() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IUcmAgentService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyType(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinCurrentRetryCount() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinMaximumLength() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinMaximumRetryCount() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getKeyguardPinMinimumLength() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle getStatus() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle importKey(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle importKeyPair(String str, byte[] bArr, byte[] bArr2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle initKeyguardPin(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle installCertificateIfSupported(String str, byte[] bArr, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle mac(String str, byte[] bArr, String str2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public int notifyChange(int i, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle processCommand(byte[] bArr, Bundle bundle, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle resetUid(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle resetUser(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle saw(Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setCertificateChain(String str, byte[] bArr, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setCredentialStorageProperty(int i, int i2, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setKeyguardPinMaximumLength(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setKeyguardPinMaximumRetryCount(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setKeyguardPinMinimumLength(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle setState(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle sign(String str, byte[] bArr, String str2, boolean z, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle unwrapDek(byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle verifyPassword(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle verifyPin(int i, String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService
            public Bundle verifyPuk(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUcmAgentService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUcmAgentService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IUcmAgentService)) ? new Proxy(iBinder) : (IUcmAgentService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUcmAgentService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUcmAgentService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle certificateChain = getCertificateChain(readString, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain, 1);
                    return true;
                case 2:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle saw = saw(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(saw, 1);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle sign = sign(readString2, createByteArray, readString3, readBoolean, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sign, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString5 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle decrypt = decrypt(readString4, createByteArray2, readString5, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(decrypt, 1);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte[] createByteArray4 = parcel.createByteArray();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle importKeyPair = importKeyPair(readString6, createByteArray3, createByteArray4, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importKeyPair, 1);
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    Bundle bundle6 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle certificateChain2 = setCertificateChain(readString7, createByteArray5, bundle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(certificateChain2, 1);
                    return true;
                case 7:
                    String readString8 = parcel.readString();
                    Bundle bundle7 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle delete = delete(readString8, bundle7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(delete, 1);
                    return true;
                case 8:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt = parcel.readInt();
                    Bundle bundle8 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKeyPair = generateKeyPair(readString9, readString10, readInt, bundle8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKeyPair, 1);
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray6 = parcel.createByteArray();
                    Bundle bundle9 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateSecureRandom = generateSecureRandom(readInt2, createByteArray6, bundle9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateSecureRandom, 1);
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    Bundle bundle10 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int notifyChange = notifyChange(readInt3, bundle10);
                    parcel2.writeNoException();
                    parcel2.writeInt(notifyChange);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    Bundle bundle11 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle configureCredentialStoragePlugin = configureCredentialStoragePlugin(readInt4, bundle11, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configureCredentialStoragePlugin, 1);
                    return true;
                case 12:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle credentialStoragePluginConfiguration = getCredentialStoragePluginConfiguration(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStoragePluginConfiguration, 1);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    Bundle bundle12 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty = getCredentialStorageProperty(readInt7, readInt8, bundle12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty, 1);
                    return true;
                case 14:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    Bundle bundle13 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle credentialStorageProperty2 = setCredentialStorageProperty(readInt9, readInt10, bundle13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialStorageProperty2, 1);
                    return true;
                case 15:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle resetUser = resetUser(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resetUser, 1);
                    return true;
                case 16:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle resetUid = resetUid(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resetUid, 1);
                    return true;
                case 17:
                    byte[] createByteArray7 = parcel.createByteArray();
                    Bundle bundle14 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle processCommand = processCommand(createByteArray7, bundle14, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(processCommand, 1);
                    return true;
                case 18:
                    String readString11 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle containsAlias = containsAlias(readString11, readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(containsAlias, 1);
                    return true;
                case 19:
                    Bundle generateDek = generateDek();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateDek, 1);
                    return true;
                case 20:
                    Bundle generateWrappedDek = generateWrappedDek();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateWrappedDek, 1);
                    return true;
                case 21:
                    Bundle dek = getDek();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dek, 1);
                    return true;
                case 22:
                    byte[] createByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    Bundle unwrapDek = unwrapDek(createByteArray8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(unwrapDek, 1);
                    return true;
                case 23:
                    int readInt16 = parcel.readInt();
                    String readString12 = parcel.readString();
                    Bundle bundle15 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle verifyPin = verifyPin(readInt16, readString12, bundle15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyPin, 1);
                    return true;
                case 24:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle verifyPuk = verifyPuk(readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyPuk, 1);
                    return true;
                case 25:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle changePin = changePin(readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(changePin, 1);
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle state = setState(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 27:
                    Bundle info = getInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(info, 1);
                    return true;
                case 28:
                    byte[] createByteArray9 = parcel.createByteArray();
                    Bundle bundle16 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle APDUCommand = APDUCommand(createByteArray9, bundle16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(APDUCommand, 1);
                    return true;
                case 29:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle verifyPassword = verifyPassword(readString17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyPassword, 1);
                    return true;
                case 30:
                    Bundle status = getStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(status, 1);
                    return true;
                case 31:
                    int readInt18 = parcel.readInt();
                    Bundle bundle17 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKeyguardPassword = generateKeyguardPassword(readInt18, bundle17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKeyguardPassword, 1);
                    return true;
                case 32:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String detailErrorMessage = getDetailErrorMessage(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeString(detailErrorMessage);
                    return true;
                case 33:
                    String readString18 = parcel.readString();
                    byte[] createByteArray10 = parcel.createByteArray();
                    String readString19 = parcel.readString();
                    Bundle bundle18 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle encrypt = encrypt(readString18, createByteArray10, readString19, bundle18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(encrypt, 1);
                    return true;
                case 34:
                    String readString20 = parcel.readString();
                    Bundle bundle19 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle initKeyguardPin = initKeyguardPin(readString20, bundle19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(initKeyguardPin, 1);
                    return true;
                case 35:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumRetryCount = setKeyguardPinMaximumRetryCount(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMaximumRetryCount, 1);
                    return true;
                case 36:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMinimumLength = setKeyguardPinMinimumLength(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyguardPinMinimumLength, 1);
                    return true;
                case 37:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle keyguardPinMaximumLength = setKeyguardPinMaximumLength(readInt22);
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
                    String readString21 = parcel.readString();
                    Bundle bundle20 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle importKey = importKey(readString21, bundle20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(importKey, 1);
                    return true;
                case 43:
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    Bundle bundle21 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle generateKey = generateKey(readString22, readString23, readInt23, bundle21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(generateKey, 1);
                    return true;
                case 44:
                    String readString24 = parcel.readString();
                    Bundle bundle22 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle keyType = getKeyType(readString24, bundle22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyType, 1);
                    return true;
                case 45:
                    String readString25 = parcel.readString();
                    byte[] createByteArray11 = parcel.createByteArray();
                    String readString26 = parcel.readString();
                    Bundle bundle23 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle installCertificateIfSupported = installCertificateIfSupported(readString25, createByteArray11, readString26, bundle23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installCertificateIfSupported, 1);
                    return true;
                case 46:
                    String readString27 = parcel.readString();
                    byte[] createByteArray12 = parcel.createByteArray();
                    String readString28 = parcel.readString();
                    Bundle bundle24 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle mac = mac(readString27, createByteArray12, readString28, bundle24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mac, 1);
                    return true;
                case 47:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle changePinWithPassword = changePinWithPassword(readString29, readString30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(changePinWithPassword, 1);
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
