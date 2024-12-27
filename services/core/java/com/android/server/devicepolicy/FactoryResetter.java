package com.android.server.devicepolicy;

import android.app.admin.DevicePolicySafetyChecker;
import android.content.Context;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.service.persistentdata.PersistentDataBlockManager;

import com.android.internal.os.IResultReceiver;
import com.android.internal.util.Preconditions;
import com.android.server.utils.Slogf;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FactoryResetter {
    public final Context mContext;
    public final boolean mForce;
    public final String mReason;
    public final DevicePolicySafetyChecker mSafetyChecker;
    public final boolean mShutdown;
    public final boolean mWipeAdoptableStorage;
    public final boolean mWipeEuicc;
    public final boolean mWipeFactoryResetProtection;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public final Context mContext;
        public boolean mForce;
        public String mReason;
        public DevicePolicySafetyChecker mSafetyChecker;
        public boolean mShutdown;
        public boolean mWipeAdoptableStorage;
        public boolean mWipeEuicc;
        public boolean mWipeFactoryResetProtection;

        public Builder(Context context) {
            Objects.requireNonNull(context);
            this.mContext = context;
        }
    }

    public FactoryResetter(Builder builder) {
        this.mContext = builder.mContext;
        this.mSafetyChecker = builder.mSafetyChecker;
        this.mReason = builder.mReason;
        this.mShutdown = builder.mShutdown;
        this.mForce = builder.mForce;
        this.mWipeEuicc = builder.mWipeEuicc;
        this.mWipeAdoptableStorage = builder.mWipeAdoptableStorage;
        this.mWipeFactoryResetProtection = builder.mWipeFactoryResetProtection;
    }

    public final boolean factoryReset() {
        Preconditions.checkCallAuthorization(
                this.mContext.checkCallingOrSelfPermission("android.permission.MASTER_CLEAR") == 0);
        Context context = this.mContext;
        AtomicBoolean atomicBoolean = com.android.server.FactoryResetter.sFactoryResetting;
        Preconditions.checkCallAuthorization(
                context.checkCallingOrSelfPermission("android.permission.MASTER_CLEAR") == 0);
        com.android.server.FactoryResetter.sFactoryResetting.set(true);
        if (this.mSafetyChecker == null) {
            factoryResetInternalUnchecked();
            return true;
        }
        IResultReceiver.Stub stub =
                new IResultReceiver
                        .Stub() { // from class: com.android.server.devicepolicy.FactoryResetter.1
                    public final void send(int i, Bundle bundle) {
                        Slogf.i(
                                "FactoryResetter",
                                "Factory reset confirmed by %s, proceeding",
                                FactoryResetter.this.mSafetyChecker);
                        try {
                            FactoryResetter.this.factoryResetInternalUnchecked();
                        } catch (IOException e) {
                            Slogf.wtf(
                                    "FactoryResetter",
                                    e,
                                    "IOException calling underlying systems",
                                    new Object[0]);
                        }
                    }
                };
        Slogf.i("FactoryResetter", "Delaying factory reset until %s confirms", this.mSafetyChecker);
        this.mSafetyChecker.onFactoryReset(stub);
        return false;
    }

    public final void factoryResetInternalUnchecked() {
        boolean z = this.mShutdown;
        Boolean valueOf = Boolean.valueOf(z);
        boolean z2 = this.mForce;
        Boolean valueOf2 = Boolean.valueOf(z2);
        boolean z3 = this.mWipeEuicc;
        Boolean valueOf3 = Boolean.valueOf(z3);
        boolean z4 = this.mWipeAdoptableStorage;
        Boolean valueOf4 = Boolean.valueOf(z4);
        boolean z5 = this.mWipeFactoryResetProtection;
        Slogf.i(
                "FactoryResetter",
                "factoryReset(): reason=%s, shutdown=%b, force=%b, wipeEuicc=%b,"
                    + " wipeAdoptableStorage=%b, wipeFRP=%b",
                this.mReason,
                valueOf,
                valueOf2,
                valueOf3,
                valueOf4,
                Boolean.valueOf(z5));
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (!z2 && userManager.hasUserRestriction("no_factory_reset")) {
            throw new SecurityException("Factory reset is not allowed for this user.");
        }
        if (z5) {
            PersistentDataBlockManager persistentDataBlockManager =
                    (PersistentDataBlockManager)
                            this.mContext.getSystemService(PersistentDataBlockManager.class);
            if (persistentDataBlockManager != null) {
                Slogf.w("FactoryResetter", "Wiping factory reset protection");
                persistentDataBlockManager.wipe();
            } else {
                Slogf.w("FactoryResetter", "No need to wipe factory reset protection");
            }
        }
        if (z4) {
            Slogf.w("FactoryResetter", "Wiping adoptable storage");
            ((StorageManager) this.mContext.getSystemService(StorageManager.class))
                    .wipeAdoptableDisks();
        }
        RecoverySystem.rebootWipeUserData(this.mContext, z, this.mReason, z2, z3);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FactoryResetter[");
        String str = this.mReason;
        if (str == null) {
            sb.append("no_reason");
        } else {
            sb.append("reason='");
            sb.append(str);
            sb.append("'");
        }
        if (this.mSafetyChecker != null) {
            sb.append(",hasSafetyChecker");
        }
        if (this.mShutdown) {
            sb.append(",shutdown");
        }
        if (this.mForce) {
            sb.append(",force");
        }
        if (this.mWipeEuicc) {
            sb.append(",wipeEuicc");
        }
        if (this.mWipeAdoptableStorage) {
            sb.append(",wipeAdoptableStorage");
        }
        if (this.mWipeFactoryResetProtection) {
            sb.append(",ipeFactoryResetProtection");
        }
        sb.append(']');
        return sb.toString();
    }
}
