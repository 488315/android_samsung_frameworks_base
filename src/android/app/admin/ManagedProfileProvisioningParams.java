package android.app.admin;

import android.accounts.Account;
import android.annotation.SystemApi;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class ManagedProfileProvisioningParams implements Parcelable {
    private static final String ACCOUNT_TO_MIGRATE_PROVIDED_PARAM = "ACCOUNT_TO_MIGRATE_PROVIDED";
    public static final Parcelable.Creator<ManagedProfileProvisioningParams> CREATOR = new Parcelable.Creator<ManagedProfileProvisioningParams>() { // from class: android.app.admin.ManagedProfileProvisioningParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedProfileProvisioningParams createFromParcel(Parcel parcel) {
            return new ManagedProfileProvisioningParams((ComponentName) parcel.readTypedObject(ComponentName.CREATOR), parcel.readString(), parcel.readString(), (Account) parcel.readTypedObject(Account.CREATOR), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readPersistableBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedProfileProvisioningParams[] newArray(int i) {
            return new ManagedProfileProvisioningParams[i];
        }
    };
    private static final String KEEP_MIGRATED_ACCOUNT_PARAM = "KEEP_MIGRATED_ACCOUNT";
    private static final String LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM = "LEAVE_ALL_SYSTEM_APPS_ENABLED";
    private static final String ORGANIZATION_OWNED_PROVISIONING_PARAM = "ORGANIZATION_OWNED_PROVISIONING";
    private final Account mAccountToMigrate;
    private final PersistableBundle mAdminExtras;
    private final boolean mKeepAccountOnMigration;
    private final boolean mLeaveAllSystemAppsEnabled;
    private final boolean mOrganizationOwnedProvisioning;
    private final String mOwnerName;
    private final ComponentName mProfileAdminComponentName;
    private final String mProfileName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ManagedProfileProvisioningParams(ComponentName componentName, String str, String str2, Account account, boolean z, boolean z2, boolean z3, PersistableBundle persistableBundle) {
        this.mProfileAdminComponentName = (ComponentName) Objects.requireNonNull(componentName);
        this.mOwnerName = (String) Objects.requireNonNull(str);
        this.mProfileName = str2;
        this.mAccountToMigrate = account;
        this.mLeaveAllSystemAppsEnabled = z;
        this.mOrganizationOwnedProvisioning = z2;
        this.mKeepAccountOnMigration = z3;
        this.mAdminExtras = persistableBundle;
    }

    public ComponentName getProfileAdminComponentName() {
        return this.mProfileAdminComponentName;
    }

    public String getOwnerName() {
        return this.mOwnerName;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    public Account getAccountToMigrate() {
        return this.mAccountToMigrate;
    }

    public boolean isLeaveAllSystemAppsEnabled() {
        return this.mLeaveAllSystemAppsEnabled;
    }

    public boolean isOrganizationOwnedProvisioning() {
        return this.mOrganizationOwnedProvisioning;
    }

    public boolean isKeepingAccountOnMigration() {
        return this.mKeepAccountOnMigration;
    }

    public PersistableBundle getAdminExtras() {
        return new PersistableBundle(this.mAdminExtras);
    }

    public void logParams(String str) {
        Objects.requireNonNull(str);
        logParam(str, LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM, this.mLeaveAllSystemAppsEnabled);
        logParam(str, ORGANIZATION_OWNED_PROVISIONING_PARAM, this.mOrganizationOwnedProvisioning);
        logParam(str, KEEP_MIGRATED_ACCOUNT_PARAM, this.mKeepAccountOnMigration);
        logParam(str, ACCOUNT_TO_MIGRATE_PROVIDED_PARAM, this.mAccountToMigrate != null);
    }

    private void logParam(String str, String str2, boolean z) {
        DevicePolicyEventLogger.createEvent(197).setStrings(str).setAdmin(this.mProfileAdminComponentName).setStrings(str2).setBoolean(z).write();
    }

    public static final class Builder {
        private Account mAccountToMigrate;
        private PersistableBundle mAdminExtras;
        private boolean mKeepingAccountOnMigration;
        private boolean mLeaveAllSystemAppsEnabled;
        private boolean mOrganizationOwnedProvisioning;
        private final String mOwnerName;
        private final ComponentName mProfileAdminComponentName;
        private String mProfileName;

        public Builder(ComponentName componentName, String str) {
            Objects.requireNonNull(componentName);
            Objects.requireNonNull(str);
            this.mProfileAdminComponentName = componentName;
            this.mOwnerName = str;
        }

        public Builder setProfileName(String str) {
            this.mProfileName = str;
            return this;
        }

        public Builder setAccountToMigrate(Account account) {
            this.mAccountToMigrate = account;
            return this;
        }

        public Builder setLeaveAllSystemAppsEnabled(boolean z) {
            this.mLeaveAllSystemAppsEnabled = z;
            return this;
        }

        public Builder setOrganizationOwnedProvisioning(boolean z) {
            this.mOrganizationOwnedProvisioning = z;
            return this;
        }

        public Builder setKeepingAccountOnMigration(boolean z) {
            this.mKeepingAccountOnMigration = z;
            return this;
        }

        public Builder setAdminExtras(PersistableBundle persistableBundle) {
            PersistableBundle persistableBundle2;
            if (persistableBundle != null) {
                persistableBundle2 = new PersistableBundle(persistableBundle);
            } else {
                persistableBundle2 = new PersistableBundle();
            }
            this.mAdminExtras = persistableBundle2;
            return this;
        }

        public ManagedProfileProvisioningParams build() {
            ComponentName componentName = this.mProfileAdminComponentName;
            String str = this.mOwnerName;
            String str2 = this.mProfileName;
            Account account = this.mAccountToMigrate;
            boolean z = this.mLeaveAllSystemAppsEnabled;
            boolean z2 = this.mOrganizationOwnedProvisioning;
            boolean z3 = this.mKeepingAccountOnMigration;
            PersistableBundle persistableBundle = this.mAdminExtras;
            if (persistableBundle == null) {
                persistableBundle = new PersistableBundle();
            }
            return new ManagedProfileProvisioningParams(componentName, str, str2, account, z, z2, z3, persistableBundle);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ManagedProfileProvisioningParams{mProfileAdminComponentName=");
        sb.append(this.mProfileAdminComponentName);
        sb.append(", mOwnerName=");
        sb.append(this.mOwnerName);
        sb.append(", mProfileName=");
        String str = this.mProfileName;
        Object obj = PerfettoProtoLogImpl.NULL_STRING;
        if (str == null) {
            str = PerfettoProtoLogImpl.NULL_STRING;
        }
        sb.append(str);
        sb.append(", mAccountToMigrate=");
        Account account = this.mAccountToMigrate;
        if (account != null) {
            obj = account;
        }
        sb.append(obj);
        sb.append(", mLeaveAllSystemAppsEnabled=");
        sb.append(this.mLeaveAllSystemAppsEnabled);
        sb.append(", mOrganizationOwnedProvisioning=");
        sb.append(this.mOrganizationOwnedProvisioning);
        sb.append(", mKeepAccountOnMigration=");
        sb.append(this.mKeepAccountOnMigration);
        sb.append(", mAdminExtras=");
        sb.append(this.mAdminExtras);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mProfileAdminComponentName, i);
        parcel.writeString(this.mOwnerName);
        parcel.writeString(this.mProfileName);
        parcel.writeTypedObject(this.mAccountToMigrate, i);
        parcel.writeBoolean(this.mLeaveAllSystemAppsEnabled);
        parcel.writeBoolean(this.mOrganizationOwnedProvisioning);
        parcel.writeBoolean(this.mKeepAccountOnMigration);
        parcel.writePersistableBundle(this.mAdminExtras);
    }
}
