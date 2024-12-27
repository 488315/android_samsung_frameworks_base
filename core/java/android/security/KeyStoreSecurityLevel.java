package android.security;

import android.hardware.security.keymint.KeyParameter;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.system.keystore2.AuthenticatorSpec;
import android.system.keystore2.IKeystoreSecurityLevel;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import android.util.Log;

import java.util.Calendar;
import java.util.Collection;

public class KeyStoreSecurityLevel {
    private static final String TAG = "KeyStoreSecurityLevel";
    private final IKeystoreSecurityLevel mSecurityLevel;

    public KeyStoreSecurityLevel(IKeystoreSecurityLevel securityLevel) {
        Binder.allowBlocking(securityLevel.asBinder());
        this.mSecurityLevel = securityLevel;
    }

    private <R> R handleExceptions(CheckedRemoteRequest<R> request) throws KeyStoreException {
        try {
            return request.execute();
        } catch (RemoteException e) {
            Log.e(TAG, "Could not connect to Keystore.", e);
            throw new KeyStoreException(4, "", e.getMessage());
        } catch (ServiceSpecificException e2) {
            throw KeyStore2.getKeyStoreException(e2.errorCode, e2.getMessage());
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
    	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
    	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.processExcHandler(ExcHandlersRegionMaker.java:144)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:77)
    	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
    */
    public android.security.KeyStoreOperation createOperation(
            android.system.keystore2.KeyDescriptor r6,
            java.util.Collection<android.hardware.security.keymint.KeyParameter> r7)
            throws android.security.KeyStoreException {
        /*
            r5 = this;
            android.os.StrictMode.noteDiskWrite()
        L3:
            android.system.keystore2.IKeystoreSecurityLevel r0 = r5.mSecurityLevel     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            int r1 = r7.size()     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            android.hardware.security.keymint.KeyParameter[] r1 = new android.hardware.security.keymint.KeyParameter[r1]     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            java.lang.Object[] r1 = r7.toArray(r1)     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            android.hardware.security.keymint.KeyParameter[] r1 = (android.hardware.security.keymint.KeyParameter[]) r1     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            r2 = 0
            android.system.keystore2.CreateOperationResponse r0 = r0.createOperation(r6, r1, r2)     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            r1 = 0
            android.system.keystore2.OperationChallenge r2 = r0.operationChallenge     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            if (r2 == 0) goto L24
            android.system.keystore2.OperationChallenge r2 = r0.operationChallenge     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            long r2 = r2.challenge     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            r1 = r2
        L24:
            r2 = 0
            android.system.keystore2.KeyParameters r3 = r0.parameters     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            if (r3 == 0) goto L2e
            android.system.keystore2.KeyParameters r3 = r0.parameters     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            android.hardware.security.keymint.KeyParameter[] r3 = r3.keyParameter     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            r2 = r3
        L2e:
            android.security.KeyStoreOperation r3 = new android.security.KeyStoreOperation     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            android.system.keystore2.IKeystoreOperation r4 = r0.iOperation     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            r3.<init>(r4, r1, r2)     // Catch: android.os.RemoteException -> L36 android.os.ServiceSpecificException -> L44
            return r3
        L36:
            r0 = move-exception
            java.lang.String r1 = "KeyStoreSecurityLevel"
            java.lang.String r2 = "Cannot connect to keystore"
            android.util.Log.w(r1, r2, r0)
            android.security.keystore.KeyStoreConnectException r1 = new android.security.keystore.KeyStoreConnectException
            r1.<init>()
            throw r1
        L44:
            r0 = move-exception
            int r1 = r0.errorCode
            switch(r1) {
                case 18: goto L55;
                default: goto L4a;
            }
        L4a:
            int r1 = r0.errorCode
            java.lang.String r2 = r0.getMessage()
            android.security.KeyStoreException r1 = android.security.KeyStore2.getKeyStoreException(r1, r2)
            throw r1
        L55:
            double r1 = java.lang.Math.random()
            r3 = 4635329916471083008(0x4054000000000000, double:80.0)
            double r1 = r1 * r3
            r3 = 4626322717216342016(0x4034000000000000, double:20.0)
            double r1 = r1 + r3
            long r1 = (long) r1
            r3 = 169897160(0xa206cc8, double:8.394035E-316)
            boolean r3 = android.app.compat.CompatChanges.isChangeEnabled(r3)
            if (r3 != 0) goto L6e
            interruptedPreservingSleep(r1)
            goto L3
        L6e:
            android.security.keystore.BackendBusyException r3 = new android.security.keystore.BackendBusyException
            r3.<init>(r1)
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.security.KeyStoreSecurityLevel.createOperation(android.system.keystore2.KeyDescriptor,"
                    + " java.util.Collection):android.security.KeyStoreOperation");
    }

    public KeyMetadata generateKey(
            final KeyDescriptor descriptor,
            final KeyDescriptor attestationKey,
            final Collection<KeyParameter> args,
            final int flags,
            final byte[] entropy)
            throws KeyStoreException {
        StrictMode.noteDiskWrite();
        return (KeyMetadata)
                handleExceptions(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda2
                            @Override // android.security.CheckedRemoteRequest
                            public final Object execute() {
                                KeyMetadata lambda$generateKey$0;
                                lambda$generateKey$0 =
                                        KeyStoreSecurityLevel.this.lambda$generateKey$0(
                                                descriptor, attestationKey, args, flags, entropy);
                                return lambda$generateKey$0;
                            }
                        });
    }

    public /* synthetic */ KeyMetadata lambda$generateKey$0(
            KeyDescriptor descriptor,
            KeyDescriptor attestationKey,
            Collection args,
            int flags,
            byte[] entropy)
            throws RemoteException {
        return this.mSecurityLevel.generateKey(
                descriptor,
                attestationKey,
                (KeyParameter[]) args.toArray(new KeyParameter[args.size()]),
                flags,
                entropy);
    }

    public KeyMetadata importKey(
            final KeyDescriptor descriptor,
            final KeyDescriptor attestationKey,
            final Collection<KeyParameter> args,
            final int flags,
            final byte[] keyData)
            throws KeyStoreException {
        StrictMode.noteDiskWrite();
        return (KeyMetadata)
                handleExceptions(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda0
                            @Override // android.security.CheckedRemoteRequest
                            public final Object execute() {
                                KeyMetadata lambda$importKey$1;
                                lambda$importKey$1 =
                                        KeyStoreSecurityLevel.this.lambda$importKey$1(
                                                descriptor, attestationKey, args, flags, keyData);
                                return lambda$importKey$1;
                            }
                        });
    }

    public /* synthetic */ KeyMetadata lambda$importKey$1(
            KeyDescriptor descriptor,
            KeyDescriptor attestationKey,
            Collection args,
            int flags,
            byte[] keyData)
            throws RemoteException {
        return this.mSecurityLevel.importKey(
                descriptor,
                attestationKey,
                (KeyParameter[]) args.toArray(new KeyParameter[args.size()]),
                flags,
                keyData);
    }

    public KeyMetadata importWrappedKey(
            KeyDescriptor wrappedKeyDescriptor,
            final KeyDescriptor wrappingKeyDescriptor,
            byte[] wrappedKey,
            final byte[] maskingKey,
            final Collection<KeyParameter> args,
            final AuthenticatorSpec[] authenticatorSpecs)
            throws KeyStoreException {
        StrictMode.noteDiskWrite();
        final KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.alias = wrappedKeyDescriptor.alias;
        keyDescriptor.nspace = wrappedKeyDescriptor.nspace;
        keyDescriptor.blob = wrappedKey;
        keyDescriptor.domain = wrappedKeyDescriptor.domain;
        return (KeyMetadata)
                handleExceptions(
                        new CheckedRemoteRequest() { // from class:
                                                     // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda1
                            @Override // android.security.CheckedRemoteRequest
                            public final Object execute() {
                                KeyMetadata lambda$importWrappedKey$2;
                                lambda$importWrappedKey$2 =
                                        KeyStoreSecurityLevel.this.lambda$importWrappedKey$2(
                                                keyDescriptor,
                                                wrappingKeyDescriptor,
                                                maskingKey,
                                                args,
                                                authenticatorSpecs);
                                return lambda$importWrappedKey$2;
                            }
                        });
    }

    public /* synthetic */ KeyMetadata lambda$importWrappedKey$2(
            KeyDescriptor keyDescriptor,
            KeyDescriptor wrappingKeyDescriptor,
            byte[] maskingKey,
            Collection args,
            AuthenticatorSpec[] authenticatorSpecs)
            throws RemoteException {
        return this.mSecurityLevel.importWrappedKey(
                keyDescriptor,
                wrappingKeyDescriptor,
                maskingKey,
                (KeyParameter[]) args.toArray(new KeyParameter[args.size()]),
                authenticatorSpecs);
    }

    protected static void interruptedPreservingSleep(long millis) {
        boolean wasInterrupted = false;
        Calendar calendar = Calendar.getInstance();
        long target = calendar.getTimeInMillis() + millis;
        while (true) {
            try {
                Thread.sleep(target - calendar.getTimeInMillis());
                break;
            } catch (IllegalArgumentException e) {
            } catch (InterruptedException e2) {
                wasInterrupted = true;
            }
        }
        if (wasInterrupted) {
            Thread.currentThread().interrupt();
        }
    }
}
