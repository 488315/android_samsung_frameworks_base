package android.security;

import android.hardware.security.keymint.KeyParameter;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.system.keystore2.AuthenticatorSpec;
import android.system.keystore2.IKeystoreSecurityLevel;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import android.util.Log;
import java.util.Calendar;
import java.util.Collection;

/* loaded from: classes3.dex */
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
      Log.m97e(TAG, "Could not connect to Keystore.", e);
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
    L1:
        android.system.keystore2.IKeystoreSecurityLevel r0 = r5.mSecurityLevel     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        int r1 = r7.size()     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        android.hardware.security.keymint.KeyParameter[] r1 = new android.hardware.security.keymint.KeyParameter[r1]     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        java.lang.Object[] r1 = r7.toArray(r1)     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        android.hardware.security.keymint.KeyParameter[] r1 = (android.hardware.security.keymint.KeyParameter[]) r1     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        r2 = 0
        android.system.keystore2.CreateOperationResponse r0 = r0.createOperation(r6, r1, r2)     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        r1 = 0
        android.system.keystore2.OperationChallenge r2 = r0.operationChallenge     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        if (r2 == 0) goto L22
        android.system.keystore2.OperationChallenge r2 = r0.operationChallenge     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        long r2 = r2.challenge     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        r1 = r2
    L22:
        r2 = 0
        android.system.keystore2.KeyParameters r3 = r0.parameters     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        if (r3 == 0) goto L2c
        android.system.keystore2.KeyParameters r3 = r0.parameters     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        android.hardware.security.keymint.KeyParameter[] r3 = r3.keyParameter     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        r2 = r3
    L2c:
        android.security.KeyStoreOperation r3 = new android.security.KeyStoreOperation     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        android.system.keystore2.IKeystoreOperation r4 = r0.iOperation     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        r3.<init>(r4, r1, r2)     // Catch: android.os.RemoteException -> L34 android.os.ServiceSpecificException -> L42
        return r3
    L34:
        r0 = move-exception
        java.lang.String r1 = "KeyStoreSecurityLevel"
        java.lang.String r2 = "Cannot connect to keystore"
        android.util.Log.m103w(r1, r2, r0)
        android.security.keystore.KeyStoreConnectException r1 = new android.security.keystore.KeyStoreConnectException
        r1.<init>()
        throw r1
    L42:
        r0 = move-exception
        int r1 = r0.errorCode
        switch(r1) {
            case 18: goto L53;
            default: goto L48;
        }
    L48:
        int r1 = r0.errorCode
        java.lang.String r2 = r0.getMessage()
        android.security.KeyStoreException r1 = android.security.KeyStore2.getKeyStoreException(r1, r2)
        throw r1
    L53:
        double r1 = java.lang.Math.random()
        r3 = 4635329916471083008(0x4054000000000000, double:80.0)
        double r1 = r1 * r3
        r3 = 4626322717216342016(0x4034000000000000, double:20.0)
        double r1 = r1 + r3
        long r1 = (long) r1
        r3 = 169897160(0xa206cc8, double:8.394035E-316)
        boolean r3 = android.app.compat.CompatChanges.isChangeEnabled(r3)
        if (r3 != 0) goto L6c
        interruptedPreservingSleep(r1)
        goto L1
    L6c:
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
    if (KeyStoreAuditLog.isAuditLogEnabledAsUser()) {
      try {
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
      } catch (KeyStoreException e) {
        KeyStoreAuditLog.auditLogPrivilegedAsUser(
            KeyStoreAuditLog.AuditLogParams.init(descriptor, 4, TAG, e.getErrorCode()));
        throw e;
      }
    }
    return (KeyMetadata)
        handleExceptions(
            new CheckedRemoteRequest() { // from class:
                                         // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda3
              @Override // android.security.CheckedRemoteRequest
              public final Object execute() {
                KeyMetadata lambda$generateKey$1;
                lambda$generateKey$1 =
                    KeyStoreSecurityLevel.this.lambda$generateKey$1(
                        descriptor, attestationKey, args, flags, entropy);
                return lambda$generateKey$1;
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
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

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ KeyMetadata lambda$generateKey$1(
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
    if (!KeyStoreAuditLog.isAuditLogEnabledAsUser()) {
      return (KeyMetadata)
          handleExceptions(
              new CheckedRemoteRequest() { // from class:
                                           // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda1
                @Override // android.security.CheckedRemoteRequest
                public final Object execute() {
                  KeyMetadata lambda$importKey$3;
                  lambda$importKey$3 =
                      KeyStoreSecurityLevel.this.lambda$importKey$3(
                          descriptor, attestationKey, args, flags, keyData);
                  return lambda$importKey$3;
                }
              });
    }
    KeyMetadata keyMetadata = null;
    int errorCode = 1;
    try {
      try {
        keyMetadata =
            (KeyMetadata)
                handleExceptions(
                    new CheckedRemoteRequest() { // from class:
                                                 // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda0
                      @Override // android.security.CheckedRemoteRequest
                      public final Object execute() {
                        KeyMetadata lambda$importKey$2;
                        lambda$importKey$2 =
                            KeyStoreSecurityLevel.this.lambda$importKey$2(
                                descriptor, attestationKey, args, flags, keyData);
                        return lambda$importKey$2;
                      }
                    });
        return keyMetadata;
      } catch (KeyStoreException e) {
        errorCode = e.getErrorCode();
        throw e;
      }
    } finally {
      if (errorCode == 1 && keyMetadata != null && keyMetadata.key != null) {
        KeyStoreAuditLog.setKeyDescriptorBeforeImportKey(keyMetadata.key.nspace, descriptor);
      }
      KeyStoreAuditLog.auditLogPrivilegedAsUser(
          KeyStoreAuditLog.AuditLogParams.init(descriptor, 5, TAG, errorCode));
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ KeyMetadata lambda$importKey$2(
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

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ KeyMetadata lambda$importKey$3(
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
    final KeyDescriptor keyDescriptor = new KeyDescriptor();
    keyDescriptor.alias = wrappedKeyDescriptor.alias;
    keyDescriptor.nspace = wrappedKeyDescriptor.nspace;
    keyDescriptor.blob = wrappedKey;
    keyDescriptor.domain = wrappedKeyDescriptor.domain;
    if (!KeyStoreAuditLog.isAuditLogEnabledAsUser()) {
      return (KeyMetadata)
          handleExceptions(
              new CheckedRemoteRequest() { // from class:
                                           // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda5
                @Override // android.security.CheckedRemoteRequest
                public final Object execute() {
                  KeyMetadata lambda$importWrappedKey$5;
                  lambda$importWrappedKey$5 =
                      KeyStoreSecurityLevel.this.lambda$importWrappedKey$5(
                          keyDescriptor,
                          wrappingKeyDescriptor,
                          maskingKey,
                          args,
                          authenticatorSpecs);
                  return lambda$importWrappedKey$5;
                }
              });
    }
    try {
      try {
        return (KeyMetadata)
            handleExceptions(
                new CheckedRemoteRequest() { // from class:
                                             // android.security.KeyStoreSecurityLevel$$ExternalSyntheticLambda4
                  @Override // android.security.CheckedRemoteRequest
                  public final Object execute() {
                    KeyMetadata lambda$importWrappedKey$4;
                    lambda$importWrappedKey$4 =
                        KeyStoreSecurityLevel.this.lambda$importWrappedKey$4(
                            keyDescriptor,
                            wrappingKeyDescriptor,
                            maskingKey,
                            args,
                            authenticatorSpecs);
                    return lambda$importWrappedKey$4;
                  }
                });
      } catch (KeyStoreException e) {
        e.getErrorCode();
        throw e;
      }
    } finally {
      KeyStoreAuditLog.auditLogPrivilegedAsUser(
          KeyStoreAuditLog.AuditLogParams.init(keyDescriptor, 5, TAG, 1));
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ KeyMetadata lambda$importWrappedKey$4(
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

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ KeyMetadata lambda$importWrappedKey$5(
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
