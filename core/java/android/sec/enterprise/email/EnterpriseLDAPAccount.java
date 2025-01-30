package android.sec.enterprise.email;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class EnterpriseLDAPAccount implements Parcelable {
  public static final String ACTION_CREATE_LDAP_ACCOUNT =
      "com.samsung.android.knox.intent.action.CREATE_LDAPACCOUNT_INTERNAL";
  public static final String ACTION_LDAP_CREATE_ACCOUNT_RESULT =
      "com.samsung.android.knox.intent.action.LDAP_CREATE_ACCT_RESULT_INTERNAL";
  public static final Parcelable.Creator<EnterpriseLDAPAccount> CREATOR =
      new Parcelable.Creator<
          EnterpriseLDAPAccount>() { // from class:
                                     // android.sec.enterprise.email.EnterpriseLDAPAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseLDAPAccount createFromParcel(Parcel in) {
          return new EnterpriseLDAPAccount(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseLDAPAccount[] newArray(int size) {
          return new EnterpriseLDAPAccount[size];
        }
      };
  public static final int ERROR_LDAP_ALREADY_EXISTS = -1;
  public static final int ERROR_LDAP_CONNECTION = -7;
  public static final int ERROR_LDAP_DOES_NOT_EXIST = -2;
  public static final int ERROR_LDAP_INVALID_CREDENTIALS = -3;
  public static final int ERROR_LDAP_NONE = 0;
  public static final int ERROR_LDAP_SERVER_BUSY = -4;
  public static final int ERROR_LDAP_SERVER_DOWN = -5;
  public static final int ERROR_LDAP_TIMEOUT = -6;
  public static final int ERROR_LDAP_UNKNOWN = -8;
  public static final String EXTRA_BASE_DN_INTERNAL =
      "com.samsung.android.knox.intent.extra.BASE_DN_INTERNAL";
  public static final String EXTRA_HOST_INTERNAL =
      "com.samsung.android.knox.intent.extra.HOST_INTERNAL";
  public static final String EXTRA_IS_ANONYMOUS_INTERNAL =
      "com.samsung.android.knox.intent.extra.IS_ANONYMOUS_INTERNAL";
  public static final String EXTRA_IS_SSL_INTERNAL =
      "com.samsung.android.knox.intent.extra.IS_SSL_INTERNAL";
  public static final String EXTRA_LDAP_ACCOUNT_ID =
      "com.samsung.android.knox.intent.extra.LDAP_ACCT_ID";
  public static final String EXTRA_LDAP_RESULT =
      "com.samsung.android.knox.intent.extra.LDAP_RESULT";
  public static final String EXTRA_LDAP_USER_ID =
      "com.samsung.android.knox.intent.extra.LDAP_USER_ID";
  public static final String EXTRA_PORT_INTERNAL =
      "com.samsung.android.knox.intent.extra.PORT_INTERNAL";
  public static final String EXTRA_SERVICE_INTERNAL =
      "com.samsung.android.knox.intent.extra.SERVICE_INTERNAL";
  public static final String EXTRA_TRUST_ALL_INTERNAL =
      "com.samsung.android.knox.intent.extra.TRUST_ALL_INTERNAL";
  public static final String EXTRA_USER_ID_INTERNAL =
      "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL";
  public static final String EXTRA_USER_NAME_INTERNAL =
      "com.samsung.android.knox.intent.extra.USER_NAME_INTERNAL";
  public static final String EXTRA_USER_PASSWORD_ID_INTERNAL =
      "com.samsung.android.knox.intent.extra.USER_PASSWORD_ID_INTERNAL";
  public String mBaseDN;
  public String mHost;
  public long mId;
  public boolean mIsAnonymous;
  public String mPassword;
  public int mPort;
  public int mTrustAll;
  public boolean mUseSSL;
  public String mUserName;

  public EnterpriseLDAPAccount() {}

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public EnterpriseLDAPAccount(Parcel in) {
    readFromParcel(in);
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeLong(this.mId);
    parcel.writeString(this.mUserName);
    parcel.writeString(this.mPassword);
    parcel.writeInt(this.mPort);
    parcel.writeString(this.mHost);
    parcel.writeInt(!this.mUseSSL ? 1 : 0);
    parcel.writeInt(!this.mIsAnonymous ? 1 : 0);
    parcel.writeString(this.mBaseDN);
    parcel.writeInt(this.mTrustAll);
  }

  private void readFromParcel(Parcel in) {
    this.mId = in.readLong();
    this.mUserName = in.readString();
    this.mPassword = in.readString();
    this.mPort = in.readInt();
    this.mHost = in.readString();
    this.mUseSSL = in.readInt() == 0;
    this.mIsAnonymous = in.readInt() == 0;
    this.mBaseDN = in.readString();
    this.mTrustAll = in.readInt();
  }
}
