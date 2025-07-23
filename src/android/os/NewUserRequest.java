package android.os;

import android.annotation.SystemApi;
import android.graphics.Bitmap;
import android.text.TextUtils;

@SystemApi
/* loaded from: classes3.dex */
public final class NewUserRequest {
    private final String mAccountName;
    private final PersistableBundle mAccountOptions;
    private final String mAccountType;
    private final boolean mAdmin;
    private final boolean mEphemeral;
    private final String mName;
    private final Bitmap mUserIcon;
    private final String mUserType;

    private NewUserRequest(Builder builder) {
        this.mName = builder.mName;
        this.mAdmin = builder.mAdmin;
        this.mEphemeral = builder.mEphemeral;
        this.mUserType = builder.mUserType;
        this.mUserIcon = builder.mUserIcon;
        this.mAccountName = builder.mAccountName;
        this.mAccountType = builder.mAccountType;
        this.mAccountOptions = builder.mAccountOptions;
    }

    public String getName() {
        return this.mName;
    }

    public boolean isEphemeral() {
        return this.mEphemeral;
    }

    public boolean isAdmin() {
        return this.mAdmin;
    }

    int getFlags() {
        int i = isAdmin() ? 2 : 0;
        return isEphemeral() ? i | 256 : i;
    }

    public String getUserType() {
        return this.mUserType;
    }

    public Bitmap getUserIcon() {
        return this.mUserIcon;
    }

    public String getAccountName() {
        return this.mAccountName;
    }

    public String getAccountType() {
        return this.mAccountType;
    }

    public PersistableBundle getAccountOptions() {
        return this.mAccountOptions;
    }

    public String toString() {
        return "NewUserRequest{mName='" + this.mName + "', mAdmin=" + this.mAdmin + ", mEphemeral=" + this.mEphemeral + ", mUserType='" + this.mUserType + "', mAccountName='" + this.mAccountName + "', mAccountType='" + this.mAccountType + "', mAccountOptions=" + this.mAccountOptions + '}';
    }

    public static final class Builder {
        private String mAccountName;
        private PersistableBundle mAccountOptions;
        private String mAccountType;
        private boolean mAdmin;
        private boolean mEphemeral;
        private String mName;
        private Bitmap mUserIcon;
        private String mUserType = UserManager.USER_TYPE_FULL_SECONDARY;

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setAdmin() {
            this.mAdmin = true;
            return this;
        }

        public Builder setEphemeral() {
            this.mEphemeral = true;
            return this;
        }

        public Builder setUserType(String str) {
            this.mUserType = str;
            return this;
        }

        public Builder setUserIcon(Bitmap bitmap) {
            this.mUserIcon = bitmap;
            return this;
        }

        public Builder setAccountName(String str) {
            this.mAccountName = str;
            return this;
        }

        public Builder setAccountType(String str) {
            this.mAccountType = str;
            return this;
        }

        public Builder setAccountOptions(PersistableBundle persistableBundle) {
            this.mAccountOptions = persistableBundle;
            return this;
        }

        public NewUserRequest build() {
            checkIfPropertiesAreCompatible();
            return new NewUserRequest(this);
        }

        private void checkIfPropertiesAreCompatible() {
            String str = this.mUserType;
            if (str == null) {
                throw new IllegalStateException("Usertype cannot be null");
            }
            if (this.mAdmin && !str.equals(UserManager.USER_TYPE_FULL_SECONDARY)) {
                throw new IllegalStateException("Admin user can't be of type: " + this.mUserType);
            }
            if (TextUtils.isEmpty(this.mAccountName) != TextUtils.isEmpty(this.mAccountType)) {
                throw new IllegalStateException("Account name and account type should be provided together.");
            }
        }
    }
}
