package android.app.admin;

import android.accounts.Account;
import android.annotation.SystemApi;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class ManagedProfileProvisioningParams implements Parcelable {
    private static final String ACCOUNT_TO_MIGRATE_PROVIDED_PARAM = "ACCOUNT_TO_MIGRATE_PROVIDED";
    public static final Parcelable.Creator<ManagedProfileProvisioningParams> CREATOR =
            new Parcelable.Creator<ManagedProfileProvisioningParams>() { // from class:
                // android.app.admin.ManagedProfileProvisioningParams.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ManagedProfileProvisioningParams createFromParcel(Parcel in) {
                    ComponentName componentName =
                            (ComponentName) in.readTypedObject(ComponentName.CREATOR);
                    String ownerName = in.readString();
                    String profileName = in.readString();
                    Account account = (Account) in.readTypedObject(Account.CREATOR);
                    boolean leaveAllSystemAppsEnabled = in.readBoolean();
                    boolean organizationOwnedProvisioning = in.readBoolean();
                    boolean keepAccountMigrated = in.readBoolean();
                    PersistableBundle adminExtras = in.readPersistableBundle();
                    return new ManagedProfileProvisioningParams(
                            componentName,
                            ownerName,
                            profileName,
                            account,
                            leaveAllSystemAppsEnabled,
                            organizationOwnedProvisioning,
                            keepAccountMigrated,
                            adminExtras);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ManagedProfileProvisioningParams[] newArray(int size) {
                    return new ManagedProfileProvisioningParams[size];
                }
            };
    private static final String KEEP_MIGRATED_ACCOUNT_PARAM = "KEEP_MIGRATED_ACCOUNT";
    private static final String LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM =
            "LEAVE_ALL_SYSTEM_APPS_ENABLED";
    private static final String ORGANIZATION_OWNED_PROVISIONING_PARAM =
            "ORGANIZATION_OWNED_PROVISIONING";
    private final Account mAccountToMigrate;
    private final PersistableBundle mAdminExtras;
    private final boolean mKeepAccountOnMigration;
    private final boolean mLeaveAllSystemAppsEnabled;
    private final boolean mOrganizationOwnedProvisioning;
    private final String mOwnerName;
    private final ComponentName mProfileAdminComponentName;
    private final String mProfileName;

    private ManagedProfileProvisioningParams(
            ComponentName profileAdminComponentName,
            String ownerName,
            String profileName,
            Account accountToMigrate,
            boolean leaveAllSystemAppsEnabled,
            boolean organizationOwnedProvisioning,
            boolean keepAccountOnMigration,
            PersistableBundle adminExtras) {
        this.mProfileAdminComponentName =
                (ComponentName) Objects.requireNonNull(profileAdminComponentName);
        this.mOwnerName = (String) Objects.requireNonNull(ownerName);
        this.mProfileName = profileName;
        this.mAccountToMigrate = accountToMigrate;
        this.mLeaveAllSystemAppsEnabled = leaveAllSystemAppsEnabled;
        this.mOrganizationOwnedProvisioning = organizationOwnedProvisioning;
        this.mKeepAccountOnMigration = keepAccountOnMigration;
        this.mAdminExtras = adminExtras;
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

    public void logParams(String callerPackage) {
        Objects.requireNonNull(callerPackage);
        logParam(
                callerPackage,
                LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM,
                this.mLeaveAllSystemAppsEnabled);
        logParam(
                callerPackage,
                ORGANIZATION_OWNED_PROVISIONING_PARAM,
                this.mOrganizationOwnedProvisioning);
        logParam(callerPackage, KEEP_MIGRATED_ACCOUNT_PARAM, this.mKeepAccountOnMigration);
        logParam(callerPackage, ACCOUNT_TO_MIGRATE_PROVIDED_PARAM, this.mAccountToMigrate != null);
    }

    private void logParam(String callerPackage, String param, boolean value) {
        DevicePolicyEventLogger.createEvent(197)
                .setStrings(callerPackage)
                .setAdmin(this.mProfileAdminComponentName)
                .setStrings(param)
                .setBoolean(value)
                .write();
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

        public Builder(ComponentName profileAdminComponentName, String ownerName) {
            Objects.requireNonNull(profileAdminComponentName);
            Objects.requireNonNull(ownerName);
            this.mProfileAdminComponentName = profileAdminComponentName;
            this.mOwnerName = ownerName;
        }

        public Builder setProfileName(String profileName) {
            this.mProfileName = profileName;
            return this;
        }

        public Builder setAccountToMigrate(Account accountToMigrate) {
            this.mAccountToMigrate = accountToMigrate;
            return this;
        }

        public Builder setLeaveAllSystemAppsEnabled(boolean leaveAllSystemAppsEnabled) {
            this.mLeaveAllSystemAppsEnabled = leaveAllSystemAppsEnabled;
            return this;
        }

        public Builder setOrganizationOwnedProvisioning(boolean organizationOwnedProvisioning) {
            this.mOrganizationOwnedProvisioning = organizationOwnedProvisioning;
            return this;
        }

        public Builder setKeepingAccountOnMigration(boolean keepingAccountOnMigration) {
            this.mKeepingAccountOnMigration = keepingAccountOnMigration;
            return this;
        }

        public Builder setAdminExtras(PersistableBundle adminExtras) {
            PersistableBundle persistableBundle;
            if (adminExtras != null) {
                persistableBundle = new PersistableBundle(adminExtras);
            } else {
                persistableBundle = new PersistableBundle();
            }
            this.mAdminExtras = persistableBundle;
            return this;
        }

        public ManagedProfileProvisioningParams build() {
            return new ManagedProfileProvisioningParams(
                    this.mProfileAdminComponentName,
                    this.mOwnerName,
                    this.mProfileName,
                    this.mAccountToMigrate,
                    this.mLeaveAllSystemAppsEnabled,
                    this.mOrganizationOwnedProvisioning,
                    this.mKeepingAccountOnMigration,
                    this.mAdminExtras != null ? this.mAdminExtras : new PersistableBundle());
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ManagedProfileProvisioningParams{mProfileAdminComponentName="
                + this.mProfileAdminComponentName
                + ", mOwnerName="
                + this.mOwnerName
                + ", mProfileName="
                + (this.mProfileName == null ? "null" : this.mProfileName)
                + ", mAccountToMigrate="
                + (this.mAccountToMigrate != null ? this.mAccountToMigrate : "null")
                + ", mLeaveAllSystemAppsEnabled="
                + this.mLeaveAllSystemAppsEnabled
                + ", mOrganizationOwnedProvisioning="
                + this.mOrganizationOwnedProvisioning
                + ", mKeepAccountOnMigration="
                + this.mKeepAccountOnMigration
                + ", mAdminExtras="
                + this.mAdminExtras
                + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mProfileAdminComponentName, flags);
        dest.writeString(this.mOwnerName);
        dest.writeString(this.mProfileName);
        dest.writeTypedObject(this.mAccountToMigrate, flags);
        dest.writeBoolean(this.mLeaveAllSystemAppsEnabled);
        dest.writeBoolean(this.mOrganizationOwnedProvisioning);
        dest.writeBoolean(this.mKeepAccountOnMigration);
        dest.writePersistableBundle(this.mAdminExtras);
    }
}
