package com.android.server.knox.dar;

import android.app.admin.PasswordMetrics;
import android.content.Context;
import android.net.util.NetworkConstants;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.gatekeeper.IGateKeeperService;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockscreenCredential;
import com.android.server.LocalServices;
import com.android.server.knox.dar.sdp.SDPLog;
import com.samsung.android.knox.dar.VirtualLockUtils;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes2.dex */
public final class VirtualLockImpl {
  public final Context mContext;
  public final DarDatabaseCache mDarDatabaseCache;
  public IGateKeeperService mGateKeeperService;
  public final DarManagerService.Injector mInjector;
  public final LockPatternUtils mLockPatternUtils;
  public LockSettingsInternal mLockSettingsInternal;

  public VirtualLockImpl(DarManagerService.Injector injector) {
    this.mInjector = injector;
    this.mContext = injector.getContext();
    this.mDarDatabaseCache = injector.getDarDatabaseCache();
    this.mLockPatternUtils = injector.getLockPatternUtils();
  }

  public int reserveUserIdForSystem() {
    this.mInjector.enforceCallerKnoxCoreOrSelf("reserveUserIdForSystem");
    int reservedUserIdForSystem = getReservedUserIdForSystem();
    if (getReservedUserIdForSystem() != -10000) {
      return reservedUserIdForSystem;
    }
    int availableUserId = getAvailableUserId();
    this.mDarDatabaseCache.putInt(0, "vl.reserved.userid", availableUserId);
    return availableUserId;
  }

  public int getReservedUserIdForSystem() {
    this.mInjector.enforceCallerKnoxCoreOrSelf("getReservedUserIdForSystem");
    return this.mDarDatabaseCache.getInt(0, "vl.reserved.userid", -10000);
  }

  public int getAvailableUserId() {
    return getAvailableUserId(1000);
  }

  public int getAvailableUserId(int i) {
    int reservedUserIdForSystem = getReservedUserIdForSystem();
    while (i < i + 10) {
      if (i != reservedUserIdForSystem) {
        try {
          if (getGateKeeperService().getSecureUserId(i) == 0) {
            return i;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      i++;
    }
    return new SecureRandom().nextInt(500) + NetworkConstants.ETHER_MTU;
  }

  public boolean setResetPasswordToken(byte[] bArr, int i) {
    SDPLog.m39d("VirtualLockImpl", "Set reset password token for user " + i);
    if (!VirtualLockUtils.isVirtualUserId(i)) {
      return false;
    }
    if (bArr == null || bArr.length < 32) {
      throw new IllegalArgumentException("token must be at least 32-byte long");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      clearResetPasswordTokenInternal(
          this.mDarDatabaseCache.getLong(i, "vl.rst.token.handle", 0L), i);
      long addEscrowToken =
          this.mLockPatternUtils.addEscrowToken(
              bArr, i, this.mInjector.getEscrowTokenStateChangeCallback());
      this.mDarDatabaseCache.putLong(i, "vl.rst.token.handle", addEscrowToken);
      return addEscrowToken != 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean clearResetPasswordToken(int i) {
    SDPLog.m39d("VirtualLockImpl", "Clear Reset password token for user " + i);
    if (!VirtualLockUtils.isVirtualUserId(i)) {
      return false;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return clearResetPasswordTokenInternal(
          this.mDarDatabaseCache.getLong(i, "vl.rst.token.handle", 0L), i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean clearResetPasswordTokenInternal(long j, int i) {
    if (j == 0 || !this.mLockPatternUtils.removeEscrowToken(j, i)) {
      return false;
    }
    this.mDarDatabaseCache.delete(i, "vl.rst.token.handle");
    return true;
  }

  public boolean isResetPasswordTokenActive(int i) {
    if (!VirtualLockUtils.isVirtualUserId(i)) {
      return false;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mLockPatternUtils.isEscrowTokenActive(
          this.mDarDatabaseCache.getLong(i, "vl.rst.token.handle", 0L), i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean resetPasswordWithToken(String str, final byte[] bArr, final int i) {
    LockscreenCredential createPasswordOrNone;
    SDPLog.m39d("VirtualLockImpl", "Reset password with token for user " + i);
    if (!VirtualLockUtils.isVirtualUserId(i)) {
      return false;
    }
    if (str == null) {
      str = "";
    }
    final long j = this.mDarDatabaseCache.getLong(i, "vl.rst.token.handle", 0L);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (PasswordMetrics.isNumericOnly(str)) {
        createPasswordOrNone = LockscreenCredential.createPin(str);
      } else {
        createPasswordOrNone = LockscreenCredential.createPasswordOrNone(str);
      }
      final LockscreenCredential lockscreenCredential = createPasswordOrNone;
      return ((Boolean)
              getLockSettingsInternal()
                  .map(
                      new Function() { // from class:
                                       // com.android.server.knox.dar.VirtualLockImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          Boolean lambda$resetPasswordWithToken$0;
                          lambda$resetPasswordWithToken$0 =
                              VirtualLockImpl.lambda$resetPasswordWithToken$0(
                                  lockscreenCredential, j, bArr, i, (LockSettingsInternal) obj);
                          return lambda$resetPasswordWithToken$0;
                        }
                      })
                  .orElse(Boolean.FALSE))
          .booleanValue();
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ Boolean lambda$resetPasswordWithToken$0(
      LockscreenCredential lockscreenCredential,
      long j,
      byte[] bArr,
      int i,
      LockSettingsInternal lockSettingsInternal) {
    return Boolean.valueOf(
        lockSettingsInternal.setLockCredentialWithToken(lockscreenCredential, j, bArr, i));
  }

  public final Optional getLockSettingsInternal() {
    if (this.mLockSettingsInternal == null) {
      this.mLockSettingsInternal =
          (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
    }
    return Optional.ofNullable(this.mLockSettingsInternal);
  }

  public class GateKeeperDiedRecipient implements IBinder.DeathRecipient {
    public GateKeeperDiedRecipient() {}

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      VirtualLockImpl.this.mGateKeeperService.asBinder().unlinkToDeath(this, 0);
      VirtualLockImpl.this.mGateKeeperService = null;
    }
  }

  public final synchronized IGateKeeperService getGateKeeperService() {
    IGateKeeperService iGateKeeperService = this.mGateKeeperService;
    if (iGateKeeperService != null) {
      return iGateKeeperService;
    }
    IBinder service = ServiceManager.getService("android.service.gatekeeper.IGateKeeperService");
    if (service != null) {
      try {
        service.linkToDeath(new GateKeeperDiedRecipient(), 0);
      } catch (RemoteException e) {
        Log.w("VirtualLockImpl", " Unable to register death recipient", e);
      }
      IGateKeeperService asInterface = IGateKeeperService.Stub.asInterface(service);
      this.mGateKeeperService = asInterface;
      return asInterface;
    }
    Log.e("VirtualLockImpl", "Unable to acquire GateKeeperService");
    return null;
  }
}
