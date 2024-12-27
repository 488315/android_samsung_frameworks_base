package com.android.server.accounts;

import android.R;
import android.accounts.Account;
import android.accounts.AccountAndUser;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.AccountManagerInternal;
import android.accounts.AccountManagerResponse;
import android.accounts.AuthenticatorDescription;
import android.accounts.CantAddAccountActivity;
import android.accounts.ChooseAccountActivity;
import android.accounts.GrantCredentialsPermissionActivity;
import android.accounts.IAccountAuthenticator;
import android.accounts.IAccountAuthenticatorResponse;
import android.accounts.IAccountManager;
import android.accounts.IAccountManagerResponse;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.RegisteredServicesCache;
import android.content.pm.RegisteredServicesCacheListener;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteStatement;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.content.PackageMonitor;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.modules.expresslog.Histogram;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService;
import com.android.server.accounts.TokenCache;
import com.android.server.pm.PersonaManagerService;
import com.google.android.collect.Lists;
import com.google.android.collect.Sets;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.accounts.IDeviceAccountPolicy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.server.pm.PmLog;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public final class AccountManagerService extends IAccountManager.Stub implements RegisteredServicesCacheListener {
    public static final Intent ACCOUNTS_CHANGED_INTENT;
    public static final Bundle ACCOUNTS_CHANGED_OPTIONS = new BroadcastOptions().setDeliveryGroupPolicy(1).toBundle();
    public static final Account[] EMPTY_ACCOUNT_ARRAY;
    public static final Histogram sResponseLatency;
    public static final AtomicReference sThis;
    public final AppOpsManager mAppOpsManager;
    public final AccountAuthenticatorCache mAuthenticatorCache;
    public final Context mContext;
    public final MessageHandler mHandler;
    public final Injector mInjector;
    public final PackageManager mPackageManager;
    public UserManager mUserManager;
    public final RingBuffer mAccountHistory = new RingBuffer(AccountEventRecord.class, 200);
    public boolean mUseAccountDb = true;
    public boolean mSyncDeCeAccountsExecuted = false;
    public final LinkedHashMap mSessions = new LinkedHashMap();
    public final SparseArray mUsers = new SparseArray();
    public final SparseBooleanArray mLocalUnlockedUsers = new SparseBooleanArray();
    public final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final CopyOnWriteArrayList mAppPermissionChangeListeners = new CopyOnWriteArrayList();

    /* renamed from: com.android.server.accounts.AccountManagerService$12, reason: invalid class name */
    public final class AnonymousClass12 extends Session {
        public final /* synthetic */ int $r8$classId = 1;
        public final Account val$account;
        public final Object val$options;

        public AnonymousClass12(AccountManagerService accountManagerService, UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr) {
            super(userAccounts, iAccountManagerResponse, account.type, false, true, account.name, false, false);
            this.val$options = strArr;
            this.val$account = account;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass12(AccountManagerService accountManagerService, UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, boolean z, String str2, Account account, Bundle bundle) {
            super(userAccounts, iAccountManagerResponse, str, z, true, str2, true, true);
            this.val$account = account;
            this.val$options = bundle;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void onResult(Bundle bundle) {
            switch (this.$r8$classId) {
                case 1:
                    Bundle.setDefusable(bundle, true);
                    IAccountManagerResponse responseAndClose = getResponseAndClose();
                    if (responseAndClose != null) {
                        try {
                            if (bundle != null) {
                                if (Log.isLoggable("AccountManagerService", 2)) {
                                    Log.v("AccountManagerService", AnonymousClass12.class.getSimpleName() + " calling onResult() on response " + responseAndClose);
                                }
                                Bundle bundle2 = new Bundle();
                                bundle2.putBoolean("booleanResult", bundle.getBoolean("booleanResult", false));
                                responseAndClose.onResult(bundle2);
                                break;
                            } else {
                                responseAndClose.onError(5, "null bundle");
                                break;
                            }
                        } catch (RemoteException e) {
                            if (Log.isLoggable("AccountManagerService", 2)) {
                                Log.v("AccountManagerService", "failure while notifying response", e);
                                return;
                            }
                            return;
                        }
                    }
                    break;
                default:
                    super.onResult(bundle);
                    break;
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.mAuthenticator.confirmCredentials(this, this.val$account, (Bundle) this.val$options);
                    break;
                default:
                    try {
                        this.mAuthenticator.hasFeatures(this, this.val$account, (String[]) this.val$options);
                        break;
                    } catch (RemoteException unused) {
                        onError(1, "remote exception");
                        return;
                    }
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final String toDebugString(long j) {
            switch (this.$r8$classId) {
                case 0:
                    return super.toDebugString(j) + ", confirmCredentials, " + this.val$account.toSafeString();
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append(super.toDebugString(j));
                    sb.append(", hasFeatures, ");
                    sb.append(this.val$account);
                    sb.append(", ");
                    String[] strArr = (String[]) this.val$options;
                    sb.append(strArr != null ? TextUtils.join(",", strArr) : null);
                    return sb.toString();
            }
        }
    }

    /* renamed from: com.android.server.accounts.AccountManagerService$15, reason: invalid class name */
    public final class AnonymousClass15 extends Session {
        public final /* synthetic */ int $r8$classId = 0;
        public final /* synthetic */ Object this$0;
        public final /* synthetic */ Account val$account;
        public final /* synthetic */ String val$statusToken;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass15(AccountManagerService accountManagerService, UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, String str2, Account account, String str3) {
            super(userAccounts, iAccountManagerResponse, str, false, false, str2, false, false);
            this.this$0 = accountManagerService;
            this.val$account = account;
            this.val$statusToken = str3;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass15(AccountManagerService accountManagerService, UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, boolean z, String str2, Account account, String str3, Bundle bundle) {
            super(userAccounts, iAccountManagerResponse, str, z, true, str2, false, true);
            this.val$account = account;
            this.val$statusToken = str3;
            this.this$0 = bundle;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void onResult(Bundle bundle) {
            switch (this.$r8$classId) {
                case 0:
                    Bundle.setDefusable(bundle, true);
                    IAccountManagerResponse responseAndClose = getResponseAndClose();
                    if (responseAndClose != null) {
                        if (bundle == null) {
                            ((AccountManagerService) this.this$0).getClass();
                            AccountManagerService.sendErrorResponse(responseAndClose, 5, "null bundle");
                            break;
                        } else {
                            if (Log.isLoggable("AccountManagerService", 2)) {
                                Log.v("AccountManagerService", AnonymousClass15.class.getSimpleName() + " calling onResult() on response " + responseAndClose);
                            }
                            if (bundle.getInt("errorCode", -1) > 0) {
                                AccountManagerService accountManagerService = (AccountManagerService) this.this$0;
                                int i = bundle.getInt("errorCode");
                                String string = bundle.getString("errorMessage");
                                accountManagerService.getClass();
                                AccountManagerService.sendErrorResponse(responseAndClose, i, string);
                                break;
                            } else if (!bundle.containsKey("booleanResult")) {
                                ((AccountManagerService) this.this$0).getClass();
                                AccountManagerService.sendErrorResponse(responseAndClose, 5, "no result in response");
                                break;
                            } else {
                                Bundle bundle2 = new Bundle();
                                bundle2.putBoolean("booleanResult", bundle.getBoolean("booleanResult", false));
                                ((AccountManagerService) this.this$0).getClass();
                                try {
                                    responseAndClose.onResult(bundle2);
                                    break;
                                } catch (RemoteException e) {
                                    if (Log.isLoggable("AccountManagerService", 2)) {
                                        Log.v("AccountManagerService", "failure while notifying response", e);
                                        return;
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    break;
                default:
                    super.onResult(bundle);
                    break;
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.mAuthenticator.isCredentialsUpdateSuggested(this, this.val$account, this.val$statusToken);
                    break;
                default:
                    this.mAuthenticator.updateCredentials(this, this.val$account, this.val$statusToken, (Bundle) this.this$0);
                    break;
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final String toDebugString(long j) {
            switch (this.$r8$classId) {
                case 0:
                    return super.toDebugString(j) + ", isCredentialsUpdateSuggested, " + this.val$account.toSafeString();
                default:
                    Bundle bundle = (Bundle) this.this$0;
                    if (bundle != null) {
                        bundle.keySet();
                    }
                    return super.toDebugString(j) + ", updateCredentials, " + this.val$account.toSafeString() + ", authTokenType " + this.val$statusToken + ", loginOptions " + ((Bundle) this.this$0);
            }
        }
    }

    /* renamed from: com.android.server.accounts.AccountManagerService$17, reason: invalid class name */
    public final class AnonymousClass17 extends IAccountAuthenticatorResponse.Stub {
        public final /* synthetic */ Account val$account;
        public final /* synthetic */ RemoteCallback val$callback;
        public final /* synthetic */ int val$uid;

        public AnonymousClass17(int i, Account account, RemoteCallback remoteCallback) {
            this.val$uid = i;
            this.val$account = account;
            this.val$callback = remoteCallback;
        }

        public final void handleAuthenticatorResponse(boolean z) {
            UserAccounts userAccounts = AccountManagerService.this.getUserAccounts(UserHandle.getUserId(this.val$uid));
            AccountManagerService accountManagerService = AccountManagerService.this;
            Account account = this.val$account;
            int i = this.val$uid;
            accountManagerService.getClass();
            accountManagerService.cancelNotification(AccountManagerService.getCredentialPermissionNotificationId(i, account, userAccounts, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE"), userAccounts);
            if (this.val$callback != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("booleanResult", z);
                this.val$callback.sendResult(bundle);
            }
        }

        public final void onError(int i, String str) {
            handleAuthenticatorResponse(false);
        }

        public final void onRequestContinued() {
        }

        public final void onResult(Bundle bundle) {
            handleAuthenticatorResponse(true);
        }
    }

    /* renamed from: com.android.server.accounts.AccountManagerService$7, reason: invalid class name */
    public final class AnonymousClass7 extends Session {
        public final /* synthetic */ int $r8$classId = 1;
        public final Object val$accountType;
        public final /* synthetic */ Object val$authTokenType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(AccountManagerService accountManagerService, UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, Account account, boolean z) {
            super(userAccounts, iAccountManagerResponse, account.type, z, true, account.name, false, false);
            this.val$authTokenType = accountManagerService;
            this.val$accountType = account;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(AccountManagerService accountManagerService, UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, String str2, String str3) {
            super(userAccounts, iAccountManagerResponse, str, false, false, null, false, false);
            this.val$accountType = str2;
            this.val$authTokenType = str3;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(AccountManagerService accountManagerService, UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, boolean z, Bundle bundle, String str2) {
            super(userAccounts, iAccountManagerResponse, str, z, true, null, false, true);
            this.val$authTokenType = bundle;
            this.val$accountType = str2;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public void onResult(Bundle bundle) {
            switch (this.$r8$classId) {
                case 0:
                    Bundle.setDefusable(bundle, true);
                    if (bundle == null) {
                        super.onResult(bundle);
                        break;
                    } else {
                        super.onResult(AccountManagerService$$ExternalSyntheticOutline0.m142m("authTokenLabelKey", bundle.getString("authTokenLabelKey")));
                        break;
                    }
                case 1:
                    Bundle.setDefusable(bundle, true);
                    if (bundle != null && bundle.containsKey("booleanResult") && !bundle.containsKey(KnoxCustomManagerService.INTENT)) {
                        if (bundle.getBoolean("booleanResult")) {
                            ((AccountManagerService) this.val$authTokenType).removeAccountInternal(this.mAccounts, (Account) this.val$accountType, IAccountAuthenticatorResponse.Stub.getCallingUid());
                        }
                        IAccountManagerResponse responseAndClose = getResponseAndClose();
                        if (responseAndClose != null) {
                            if (Log.isLoggable("AccountManagerService", 2)) {
                                Log.v("AccountManagerService", AnonymousClass7.class.getSimpleName() + " calling onResult() on response " + responseAndClose);
                            }
                            try {
                                responseAndClose.onResult(bundle);
                            } catch (RemoteException e) {
                                Slog.e("AccountManagerService", "Error calling onResult()", e);
                            }
                        }
                    }
                    super.onResult(bundle);
                    break;
                default:
                    super.onResult(bundle);
                    break;
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.mAuthenticator.getAuthTokenLabel(this, (String) this.val$authTokenType);
                    break;
                case 1:
                    this.mAuthenticator.getAccountRemovalAllowed(this, (Account) this.val$accountType);
                    break;
                default:
                    this.mAuthenticator.finishSession(this, this.mAccountType, (Bundle) this.val$authTokenType);
                    break;
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final String toDebugString(long j) {
            switch (this.$r8$classId) {
                case 0:
                    return super.toDebugString(j) + ", getAuthTokenLabel, " + ((String) this.val$accountType) + ", authTokenType " + ((String) this.val$authTokenType);
                case 1:
                    return super.toDebugString(j) + ", removeAccount, account " + ((Account) this.val$accountType);
                default:
                    return super.toDebugString(j) + ", finishSession, accountType " + ((String) this.val$accountType);
            }
        }
    }

    public final class AccountEventRecord {
        public static final DateTimeFormatter sDateTimeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        public final String mDate = sDateTimeFormatter.format(LocalDateTime.now());
        public final String mDescription;

        public AccountEventRecord(String str) {
            this.mDescription = str;
        }
    }

    public final class AccountManagerInternalImpl extends AccountManagerInternal {
        public AccountManagerBackupHelper mBackupHelper;
        public final Object mLock = new Object();

        public AccountManagerInternalImpl() {
        }

        public final void addOnAppPermissionChangeListener(AccountManagerInternal.OnAppPermissionChangeListener onAppPermissionChangeListener) {
            AccountManagerService.this.mAppPermissionChangeListeners.add(onAppPermissionChangeListener);
        }

        public final byte[] backupAccountAccessPermissions(int i) {
            byte[] backupAccountAccessPermissions;
            synchronized (this.mLock) {
                try {
                    if (this.mBackupHelper == null) {
                        this.mBackupHelper = new AccountManagerBackupHelper(AccountManagerService.this, this);
                    }
                    backupAccountAccessPermissions = this.mBackupHelper.backupAccountAccessPermissions(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return backupAccountAccessPermissions;
        }

        public final boolean hasAccountAccess(Account account, int i) {
            return AccountManagerService.this.hasAccountAccess(account, (String) null, i);
        }

        public final void requestAccountAccess(Account account, String str, int i, RemoteCallback remoteCallback) {
            UserAccounts userAccounts;
            if (account == null) {
                Slog.w("AccountManagerService", "account cannot be null");
                return;
            }
            if (str == null) {
                Slog.w("AccountManagerService", "packageName cannot be null");
                return;
            }
            if (i < 0) {
                Slog.w("AccountManagerService", "user id must be concrete");
                return;
            }
            if (remoteCallback == null) {
                Slog.w("AccountManagerService", "callback cannot be null");
                return;
            }
            AccountManagerService accountManagerService = AccountManagerService.this;
            if (accountManagerService.resolveAccountVisibility(account, accountManagerService.getUserAccounts(i), str).intValue() == 3) {
                Slog.w("AccountManagerService", "requestAccountAccess: account is hidden");
                return;
            }
            if (AccountManagerService.this.hasAccountAccess(account, str, new UserHandle(i))) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("booleanResult", true);
                remoteCallback.sendResult(bundle);
                return;
            }
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int packageUidAsUser = AccountManagerService.this.mPackageManager.getPackageUidAsUser(str, i);
                    AccountManagerService accountManagerService2 = AccountManagerService.this;
                    accountManagerService2.getClass();
                    Intent newGrantCredentialsPermissionIntent = accountManagerService2.newGrantCredentialsPermissionIntent(account, str, packageUidAsUser, new AccountAuthenticatorResponse((IAccountAuthenticatorResponse) accountManagerService2.new AnonymousClass17(packageUidAsUser, account, remoteCallback)), "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", false);
                    synchronized (AccountManagerService.this.mUsers) {
                        userAccounts = (UserAccounts) AccountManagerService.this.mUsers.get(i);
                    }
                    SystemNotificationChannels.createAccountChannelForPackage(str, packageUidAsUser, AccountManagerService.this.mContext);
                    AccountManagerService.m139$$Nest$mdoNotification(AccountManagerService.this, userAccounts, account, null, newGrantCredentialsPermissionIntent, str, i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("AccountManagerService", "Unknown package ".concat(str));
            }
        }

        public final void restoreAccountAccessPermissions(byte[] bArr, int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mBackupHelper == null) {
                        this.mBackupHelper = new AccountManagerBackupHelper(AccountManagerService.this, this);
                    }
                    this.mBackupHelper.restoreAccountAccessPermissions(bArr, i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class GetAccountsByTypeAndFeatureSession extends Session {
        public volatile Account[] mAccountsOfType;
        public volatile ArrayList mAccountsWithFeatures;
        public final int mCallingUid;
        public volatile int mCurrentAccount;
        public final String[] mFeatures;
        public final boolean mIncludeManagedNotVisible;
        public final String mPackageName;

        public GetAccountsByTypeAndFeatureSession(UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, int i, String str2, boolean z) {
            super(userAccounts, iAccountManagerResponse, str, false, true, null, false, false);
            this.mAccountsOfType = null;
            this.mAccountsWithFeatures = null;
            this.mCurrentAccount = 0;
            this.mCallingUid = i;
            this.mFeatures = strArr;
            this.mPackageName = str2;
            this.mIncludeManagedNotVisible = z;
        }

        public final void checkAccount() {
            if (this.mCurrentAccount < this.mAccountsOfType.length) {
                IAccountAuthenticator iAccountAuthenticator = this.mAuthenticator;
                if (iAccountAuthenticator != null) {
                    try {
                        iAccountAuthenticator.hasFeatures(this, this.mAccountsOfType[this.mCurrentAccount], this.mFeatures);
                        return;
                    } catch (RemoteException unused) {
                        onError(1, "remote exception");
                        return;
                    }
                } else {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        Log.v("AccountManagerService", "checkAccount: aborting session since we are no longer connected to the authenticator, " + toDebugString(SystemClock.elapsedRealtime()));
                        return;
                    }
                    return;
                }
            }
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    int size = this.mAccountsWithFeatures.size();
                    Account[] accountArr = new Account[size];
                    for (int i = 0; i < size; i++) {
                        accountArr[i] = (Account) this.mAccountsWithFeatures.get(i);
                    }
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        Log.v("AccountManagerService", GetAccountsByTypeAndFeatureSession.class.getSimpleName() + " calling onResult() on response " + responseAndClose);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArray("accounts", accountArr);
                    responseAndClose.onResult(bundle);
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        Log.v("AccountManagerService", "failure while notifying response", e);
                    }
                }
            }
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final void onResult(Bundle bundle) {
            Bundle.setDefusable(bundle, true);
            this.mNumResults++;
            if (bundle == null) {
                onError(5, "null bundle");
                return;
            }
            if (bundle.getBoolean("booleanResult", false)) {
                this.mAccountsWithFeatures.add(this.mAccountsOfType[this.mCurrentAccount]);
            }
            this.mCurrentAccount++;
            checkAccount();
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final void run() {
            this.mAccountsOfType = AccountManagerService.this.getAccountsFromCache(this.mAccounts, this.mAccountType, this.mCallingUid, this.mPackageName, this.mIncludeManagedNotVisible);
            this.mAccountsWithFeatures = new ArrayList(this.mAccountsOfType.length);
            this.mCurrentAccount = 0;
            checkAccount();
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final String toDebugString(long j) {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toDebugString(j));
            sb.append(", getAccountsByTypeAndFeatures, ");
            String[] strArr = this.mFeatures;
            sb.append(strArr != null ? TextUtils.join(",", strArr) : null);
            return sb.toString();
        }
    }

    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public static String getPreNDatabaseName(int i) {
            File dataSystemDirectory = Environment.getDataSystemDirectory();
            File file = new File(Environment.getUserSystemDirectory(i), "accounts.db");
            if (i == 0) {
                File file2 = new File(dataSystemDirectory, "accounts.db");
                if (file2.exists() && !file.exists()) {
                    File userSystemDirectory = Environment.getUserSystemDirectory(i);
                    if (!userSystemDirectory.exists() && !userSystemDirectory.mkdirs()) {
                        throw new IllegalStateException(AccountManagerService$$ExternalSyntheticOutline0.m(userSystemDirectory, "User dir cannot be created: "));
                    }
                    if (!file2.renameTo(file)) {
                        throw new IllegalStateException(AccountManagerService$$ExternalSyntheticOutline0.m(file, "User dir cannot be migrated: "));
                    }
                }
            }
            return file.getPath();
        }
    }

    public final class Lifecycle extends SystemService {
        public AccountManagerService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.accounts.AccountManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? accountManagerService = new AccountManagerService(new Injector(getContext()));
            this.mService = accountManagerService;
            publishBinderService("account", accountManagerService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            Slog.i("AccountManagerService", "onUserStopped " + targetUser);
            AccountManagerService.m141$$Nest$mpurgeUserData(this.mService, targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            this.mService.onUnlockUser(targetUser.getUserIdentifier());
        }
    }

    public final class MessageHandler extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("unhandled message: " + message.what);
                }
                AccountManagerService.this.copyAccountToUser(null, (Account) message.obj, message.arg1, message.arg2);
                return;
            }
            IAccountManagerResponse responseAndClose = ((Session) message.obj).getResponseAndClose();
            if (Log.isLoggable("AccountManagerService", 2)) {
                Log.v("AccountManagerService", "Session.onTimedOut");
            }
            if (responseAndClose != null) {
                try {
                    responseAndClose.onError(1, "timeout");
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        Log.v("AccountManagerService", "Session.onTimedOut: caught RemoteException while responding", e);
                    }
                }
            }
        }
    }

    public final class NotificationId {
        public final int mId;
        public final String mTag;

        public NotificationId(String str, int i) {
            this.mTag = str;
            this.mId = i;
        }
    }

    public abstract class Session extends IAccountAuthenticatorResponse.Stub implements IBinder.DeathRecipient, ServiceConnection {
        public final String mAccountName;
        public final String mAccountType;
        public final UserAccounts mAccounts;
        public final boolean mAuthDetailsRequired;
        public int mAuthenticatorUid;
        public long mBindingStartTime;
        public final long mCreationTime;
        public final boolean mExpectActivityLaunch;
        public IAccountManagerResponse mResponse;
        public final boolean mStripAuthTokenFromResult;
        public final boolean mUpdateLastAuthenticatedTime;
        public final Object mSessionLock = new Object();
        public int mNumResults = 0;
        public int mNumRequestContinued = 0;
        public int mNumErrors = 0;
        public IAccountAuthenticator mAuthenticator = null;
        public boolean mCanStartAccountManagerActivity = false;

        public Session(UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, boolean z, boolean z2, String str2, boolean z3, boolean z4) {
            if (str == null) {
                throw new IllegalArgumentException("accountType is null");
            }
            this.mAccounts = userAccounts;
            this.mStripAuthTokenFromResult = z2;
            this.mAccountType = str;
            this.mExpectActivityLaunch = z;
            this.mCreationTime = SystemClock.elapsedRealtime();
            this.mAccountName = str2;
            this.mAuthDetailsRequired = z3;
            this.mUpdateLastAuthenticatedTime = z4;
            synchronized (AccountManagerService.this.mSessions) {
                AccountManagerService.this.mSessions.put(toString(), this);
            }
            MessageHandler messageHandler = AccountManagerService.this.mHandler;
            messageHandler.sendMessageDelayed(messageHandler.obtainMessage(3, this), 900000L);
            if (iAccountManagerResponse != null) {
                try {
                    iAccountManagerResponse.asBinder().linkToDeath(this, 0);
                    this.mResponse = iAccountManagerResponse;
                } catch (RemoteException unused) {
                    binderDied();
                }
            }
        }

        public final void bind() {
            if (Log.isLoggable("AccountManagerService", 2)) {
                Log.v("AccountManagerService", "initiating bind to authenticator type " + this.mAccountType);
            }
            String str = this.mAccountType;
            RegisteredServicesCache.ServiceInfo serviceInfo = AccountManagerService.this.mAuthenticatorCache.getServiceInfo(AuthenticatorDescription.newKey(str), this.mAccounts.userId);
            if (serviceInfo == null) {
                Log.w("AccountManagerService", "there is no authenticator for " + str + ", bailing out");
            } else if (AccountManagerService.this.isLocalUnlockedUser(this.mAccounts.userId) || serviceInfo.componentInfo.directBootAware) {
                Intent intent = new Intent();
                intent.setAction("android.accounts.AccountAuthenticator");
                intent.setComponent(serviceInfo.componentName);
                if (Log.isLoggable("AccountManagerService", 2)) {
                    Log.v("AccountManagerService", "performing bindService to " + serviceInfo.componentName);
                }
                if (AccountManagerService.this.mContext.bindServiceAsUser(intent, this, Context.BindServiceFlags.of(AccountManagerService.this.mAuthenticatorCache.getBindInstantServiceAllowed(this.mAccounts.userId) ? 4194305L : 1L), UserHandle.of(this.mAccounts.userId))) {
                    this.mAuthenticatorUid = serviceInfo.uid;
                    this.mBindingStartTime = SystemClock.uptimeMillis();
                    return;
                } else {
                    Log.w("AccountManagerService", "bindService to " + serviceInfo.componentName + " failed");
                    AccountManagerService.this.mContext.unbindService(this);
                }
            } else {
                Slog.w("AccountManagerService", "Blocking binding to authenticator " + serviceInfo.componentName + " which isn't encryption aware");
            }
            Log.w("AccountManagerService", "bind attempt failed for " + toDebugString(SystemClock.elapsedRealtime()));
            onError(1, "bind failure");
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.mResponse = null;
            close();
        }

        /* JADX WARN: Code restructure failed: missing block: B:41:0x00c2, code lost:
        
            if (android.accounts.GrantCredentialsPermissionActivity.class.getName().equals(r7) == false) goto L41;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean checkKeyIntent(int r11, android.os.Bundle r12) {
            /*
                Method dump skipped, instructions count: 292
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.accounts.AccountManagerService.Session.checkKeyIntent(int, android.os.Bundle):boolean");
        }

        public final void close() {
            synchronized (AccountManagerService.this.mSessions) {
                try {
                    if (AccountManagerService.this.mSessions.remove(toString()) == null) {
                        return;
                    }
                    IAccountManagerResponse iAccountManagerResponse = this.mResponse;
                    if (iAccountManagerResponse != null) {
                        iAccountManagerResponse.asBinder().unlinkToDeath(this, 0);
                        this.mResponse = null;
                    }
                    AccountManagerService.this.mHandler.removeMessages(3, this);
                    synchronized (this.mSessionLock) {
                        try {
                            if (this.mAuthenticator != null) {
                                this.mAuthenticator = null;
                                AccountManagerService.this.mContext.unbindService(this);
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            }
        }

        public final IAccountManagerResponse getResponseAndClose() {
            int i = this.mAuthenticatorUid;
            if (i != 0 && this.mBindingStartTime > 0) {
                AccountManagerService.sResponseLatency.logSampleWithUid(i, SystemClock.uptimeMillis() - this.mBindingStartTime);
            }
            IAccountManagerResponse iAccountManagerResponse = this.mResponse;
            if (iAccountManagerResponse == null) {
                close();
                return null;
            }
            close();
            return iAccountManagerResponse;
        }

        public void onError(int i, String str) {
            this.mNumErrors++;
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose == null) {
                if (Log.isLoggable("AccountManagerService", 2)) {
                    Log.v("AccountManagerService", "Session.onError: already closed");
                    return;
                }
                return;
            }
            if (Log.isLoggable("AccountManagerService", 2)) {
                Log.v("AccountManagerService", getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
            }
            try {
                responseAndClose.onError(i, str);
            } catch (RemoteException e) {
                if (Log.isLoggable("AccountManagerService", 2)) {
                    Log.v("AccountManagerService", "Session.onError: caught RemoteException while responding", e);
                }
            }
        }

        public final void onRequestContinued() {
            this.mNumRequestContinued++;
        }

        public void onResult(Bundle bundle) {
            long j;
            boolean z = true;
            Bundle.setDefusable(bundle, true);
            this.mNumResults++;
            if (bundle != null) {
                boolean z2 = this.mUpdateLastAuthenticatedTime && (bundle.getBoolean("booleanResult", false) || (bundle.containsKey("authAccount") && bundle.containsKey("accountType")));
                if (z2 || this.mAuthDetailsRequired) {
                    AccountManagerService accountManagerService = AccountManagerService.this;
                    String str = this.mAccountName;
                    String str2 = this.mAccountType;
                    Intent intent = AccountManagerService.ACCOUNTS_CHANGED_INTENT;
                    accountManagerService.getClass();
                    if (accountManagerService.getUserAccounts(UserHandle.getCallingUserId()).accountCache.containsKey(str2)) {
                        for (Account account : (Account[]) accountManagerService.getUserAccounts(UserHandle.getCallingUserId()).accountCache.get(str2)) {
                            if (account.name.equals(str)) {
                                break;
                            }
                        }
                    }
                    z = false;
                    if (z2 && z) {
                        AccountManagerService.this.updateLastAuthenticatedTime(new Account(this.mAccountName, this.mAccountType));
                    }
                    if (this.mAuthDetailsRequired) {
                        if (z) {
                            AccountsDb accountsDb = this.mAccounts.accountsDb;
                            Account account2 = new Account(this.mAccountName, this.mAccountType);
                            j = DatabaseUtils.longForQuery(accountsDb.mDeDatabase.getReadableDatabase(), "SELECT last_password_entry_time_millis_epoch FROM accounts WHERE name=? AND type=?", new String[]{account2.name, account2.type});
                        } else {
                            j = -1;
                        }
                        bundle.putLong("lastAuthenticatedTime", j);
                    }
                }
            }
            if (bundle != null && !checkKeyIntent(Binder.getCallingUid(), bundle)) {
                onError(5, "invalid intent in bundle returned");
                return;
            }
            if (bundle != null && !TextUtils.isEmpty(bundle.getString("authtoken"))) {
                String string = bundle.getString("authAccount");
                String string2 = bundle.getString("accountType");
                if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                    Account account3 = new Account(string, string2);
                    AccountManagerService accountManagerService2 = AccountManagerService.this;
                    UserAccounts userAccounts = this.mAccounts;
                    Intent intent2 = AccountManagerService.ACCOUNTS_CHANGED_INTENT;
                    accountManagerService2.getClass();
                    accountManagerService2.cancelNotification(AccountManagerService.getSigninRequiredNotificationId(account3, userAccounts), this.mAccounts);
                }
            }
            IAccountManagerResponse responseAndClose = (this.mExpectActivityLaunch && bundle != null && bundle.containsKey(KnoxCustomManagerService.INTENT)) ? this.mResponse : getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    if (bundle == null) {
                        if (Log.isLoggable("AccountManagerService", 2)) {
                            Log.v("AccountManagerService", getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
                        }
                        responseAndClose.onError(5, "null bundle returned");
                        return;
                    }
                    if (this.mStripAuthTokenFromResult) {
                        bundle.remove("authtoken");
                        if (!checkKeyIntent(Binder.getCallingUid(), bundle)) {
                            onError(5, "invalid intent in bundle returned");
                            return;
                        }
                    }
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        Log.v("AccountManagerService", getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
                    }
                    if (bundle.getInt("errorCode", -1) > 0) {
                        responseAndClose.onError(bundle.getInt("errorCode"), bundle.getString("errorMessage"));
                    } else {
                        responseAndClose.onResult(bundle);
                    }
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        Log.v("AccountManagerService", "failure while notifying response", e);
                    }
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (this.mSessionLock) {
                IAccountAuthenticator asInterface = IAccountAuthenticator.Stub.asInterface(iBinder);
                this.mAuthenticator = asInterface;
                if (asInterface != null) {
                    try {
                        run();
                    } catch (RemoteException unused) {
                        onError(1, "remote exception");
                    }
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            IAccountManagerResponse responseAndClose = getResponseAndClose();
            if (responseAndClose != null) {
                try {
                    responseAndClose.onError(1, "disconnected");
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        Log.v("AccountManagerService", "Session.onServiceDisconnected: caught RemoteException while responding", e);
                    }
                }
            }
        }

        public abstract void run();

        public String toDebugString(long j) {
            StringBuilder sb = new StringBuilder("Session: expectLaunch ");
            sb.append(this.mExpectActivityLaunch);
            sb.append(", connected ");
            sb.append(this.mAuthenticator != null);
            sb.append(", stats (");
            sb.append(this.mNumResults);
            sb.append("/");
            sb.append(this.mNumRequestContinued);
            sb.append("/");
            sb.append(this.mNumErrors);
            sb.append("), lifetime ");
            sb.append((j - this.mCreationTime) / 1000.0d);
            return sb.toString();
        }
    }

    public abstract class StartAccountSession extends Session {
        public final boolean mIsPasswordForwardingAllowed;

        public StartAccountSession(UserAccounts userAccounts, IAccountManagerResponse iAccountManagerResponse, String str, boolean z, String str2, boolean z2) {
            super(userAccounts, iAccountManagerResponse, str, z, true, str2, false, true);
            this.mIsPasswordForwardingAllowed = z2;
        }

        @Override // com.android.server.accounts.AccountManagerService.Session
        public final void onResult(Bundle bundle) {
            Bundle.setDefusable(bundle, true);
            this.mNumResults++;
            if (bundle != null && !checkKeyIntent(Binder.getCallingUid(), bundle)) {
                onError(5, "invalid intent in bundle returned");
                return;
            }
            IAccountManagerResponse responseAndClose = (this.mExpectActivityLaunch && bundle != null && bundle.containsKey(KnoxCustomManagerService.INTENT)) ? this.mResponse : getResponseAndClose();
            if (responseAndClose == null) {
                return;
            }
            if (bundle == null) {
                if (Log.isLoggable("AccountManagerService", 2)) {
                    Log.v("AccountManagerService", getClass().getSimpleName() + " calling onError() on response " + responseAndClose);
                }
                AccountManagerService accountManagerService = AccountManagerService.this;
                Intent intent = AccountManagerService.ACCOUNTS_CHANGED_INTENT;
                accountManagerService.getClass();
                AccountManagerService.sendErrorResponse(responseAndClose, 5, "null bundle returned");
                return;
            }
            if (bundle.getInt("errorCode", -1) > 0) {
                AccountManagerService accountManagerService2 = AccountManagerService.this;
                int i = bundle.getInt("errorCode");
                String string = bundle.getString("errorMessage");
                Intent intent2 = AccountManagerService.ACCOUNTS_CHANGED_INTENT;
                accountManagerService2.getClass();
                AccountManagerService.sendErrorResponse(responseAndClose, i, string);
                return;
            }
            if (!this.mIsPasswordForwardingAllowed) {
                bundle.remove("password");
            }
            bundle.remove("authtoken");
            if (!checkKeyIntent(Binder.getCallingUid(), bundle)) {
                onError(5, "invalid intent in bundle returned");
                return;
            }
            if (Log.isLoggable("AccountManagerService", 2)) {
                Log.v("AccountManagerService", getClass().getSimpleName() + " calling onResult() on response " + responseAndClose);
            }
            Bundle bundle2 = bundle.getBundle("accountSessionBundle");
            if (bundle2 != null) {
                String string2 = bundle2.getString("accountType");
                if (TextUtils.isEmpty(string2) || !this.mAccountType.equalsIgnoreCase(string2)) {
                    Log.w("AccountManagerService", "Account type in session bundle doesn't match request.");
                }
                bundle2.putString("accountType", this.mAccountType);
                try {
                    bundle.putBundle("accountSessionBundle", CryptoHelper.getInstance().encryptBundle(bundle2));
                } catch (GeneralSecurityException e) {
                    if (Log.isLoggable("AccountManagerService", 3)) {
                        Log.v("AccountManagerService", "Failed to encrypt session bundle!", e);
                    }
                    AccountManagerService accountManagerService3 = AccountManagerService.this;
                    Intent intent3 = AccountManagerService.ACCOUNTS_CHANGED_INTENT;
                    accountManagerService3.getClass();
                    AccountManagerService.sendErrorResponse(responseAndClose, 5, "failed to encrypt session bundle");
                    return;
                }
            }
            AccountManagerService accountManagerService4 = AccountManagerService.this;
            Intent intent4 = AccountManagerService.ACCOUNTS_CHANGED_INTENT;
            accountManagerService4.getClass();
            try {
                responseAndClose.onResult(bundle);
            } catch (RemoteException e2) {
                if (Log.isLoggable("AccountManagerService", 2)) {
                    Log.v("AccountManagerService", "failure while notifying response", e2);
                }
            }
        }
    }

    public final class UserAccounts {
        public final HashMap accountCache;
        public final TokenCache accountTokenCaches;
        public final AccountsDb accountsDb;
        public final Map authTokenCache;
        public final Object cacheLock;
        public final Object dbLock;
        public final Map mReceiversForType;
        public final HashMap previousNameCache;
        public final Map userDataCache;
        public final int userId;
        public final Map visibilityCache;
        public final HashMap credentialsPermissionNotificationIds = new HashMap();
        public final HashMap signinRequiredNotificationIds = new HashMap();

        public UserAccounts(Context context, int i, File file, File file2) {
            Object obj = new Object();
            this.cacheLock = obj;
            Object obj2 = new Object();
            this.dbLock = obj2;
            this.accountCache = new LinkedHashMap();
            this.userDataCache = new HashMap();
            this.authTokenCache = new HashMap();
            TokenCache tokenCache = new TokenCache();
            TokenCache.TokenLruCache tokenLruCache = new TokenCache.TokenLruCache(64000);
            tokenLruCache.mTokenEvictors = new HashMap();
            tokenLruCache.mAccountEvictors = new HashMap();
            tokenCache.mCachedTokens = tokenLruCache;
            this.accountTokenCaches = tokenCache;
            this.visibilityCache = new HashMap();
            this.mReceiversForType = new HashMap();
            this.previousNameCache = new HashMap();
            this.userId = i;
            synchronized (obj2) {
                synchronized (obj) {
                    this.accountsDb = AccountsDb.create(context, i, file, file2);
                }
            }
        }
    }

    /* renamed from: -$$Nest$mdoNotification, reason: not valid java name */
    public static void m139$$Nest$mdoNotification(AccountManagerService accountManagerService, UserAccounts userAccounts, Account account, CharSequence charSequence, Intent intent, String str, int i) {
        Context context;
        accountManagerService.getClass();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            if (Log.isLoggable("AccountManagerService", 2)) {
                Log.v("AccountManagerService", "doNotification: " + ((Object) charSequence) + " intent:" + intent);
            }
            if (intent.getComponent() == null || !GrantCredentialsPermissionActivity.class.getName().equals(intent.getComponent().getClassName())) {
                UserHandle userHandle = new UserHandle(i);
                try {
                    Context context2 = accountManagerService.mContext;
                    context = context2.createPackageContextAsUser(context2.getPackageName(), 0, userHandle);
                } catch (PackageManager.NameNotFoundException unused) {
                    context = accountManagerService.mContext;
                }
                NotificationId signinRequiredNotificationId = getSigninRequiredNotificationId(account, userAccounts);
                intent.addCategory(signinRequiredNotificationId.mTag);
                accountManagerService.installNotification(signinRequiredNotificationId, new Notification.Builder(context, SystemNotificationChannels.ACCOUNT).setWhen(0L).setSmallIcon(R.drawable.stat_sys_warning).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(String.format(context.getText(R.string.shutdown_progress).toString(), account.name)).setContentText(charSequence).setContentIntent(PendingIntent.getActivityAsUser(accountManagerService.mContext, 0, intent, 335544320, null, new UserHandle(i))).build(), str, i);
            } else {
                accountManagerService.createNoCredentialsPermissionNotification(account, intent, str, userAccounts);
            }
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: -$$Nest$mlogAddAccountMetrics, reason: not valid java name */
    public static void m140$$Nest$mlogAddAccountMetrics(AccountManagerService accountManagerService, String str, String str2, String[] strArr, String str3) {
        accountManagerService.getClass();
        DevicePolicyEventLogger.createEvent(202).setStrings(new String[]{TextUtils.emptyIfNull(str2), TextUtils.emptyIfNull(str), TextUtils.emptyIfNull(str3), strArr == null ? "" : TextUtils.join(";", strArr)}).write();
    }

    /* renamed from: -$$Nest$mpurgeUserData, reason: not valid java name */
    public static void m141$$Nest$mpurgeUserData(AccountManagerService accountManagerService, int i) {
        UserAccounts userAccounts;
        synchronized (accountManagerService.mUsers) {
            userAccounts = (UserAccounts) accountManagerService.mUsers.get(i);
            accountManagerService.mUsers.remove(i);
            accountManagerService.mLocalUnlockedUsers.delete(i);
            AccountManager.invalidateLocalAccountsDataCaches();
        }
        if (userAccounts != null) {
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    AccountsDb accountsDb = userAccounts.accountsDb;
                    synchronized (accountsDb.mDebugStatementLock) {
                        try {
                            if (accountsDb.mDebugStatementForLogging != null) {
                                accountsDb.mDebugStatementForLogging.close();
                                accountsDb.mDebugStatementForLogging = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    userAccounts.accountsDb.close();
                }
            }
        }
    }

    static {
        Intent intent = new Intent("android.accounts.LOGIN_ACCOUNTS_CHANGED");
        ACCOUNTS_CHANGED_INTENT = intent;
        intent.setFlags(83886080);
        sThis = new AtomicReference();
        EMPTY_ACCOUNT_ARRAY = new Account[0];
        sResponseLatency = new Histogram("app.value_high_authenticator_response_latency", new Histogram.ScaledRangeOptions(20, 10000, 10000.0f, 1.5f));
    }

    public AccountManagerService(Injector injector) {
        this.mInjector = injector;
        Context context = injector.mContext;
        this.mContext = context;
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAppOpsManager = appOpsManager;
        ServiceThread serviceThread = new ServiceThread(-2, "AccountManagerService", true);
        serviceThread.start();
        MessageHandler messageHandler = new MessageHandler(serviceThread.getLooper());
        this.mHandler = messageHandler;
        AccountAuthenticatorCache accountAuthenticatorCache = new AccountAuthenticatorCache(injector.mContext);
        this.mAuthenticatorCache = accountAuthenticatorCache;
        accountAuthenticatorCache.setListener(this, messageHandler);
        sThis.set(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        final int i = 0;
        context.registerReceiver(new BroadcastReceiver(this) { // from class: com.android.server.accounts.AccountManagerService.1
            public final /* synthetic */ AccountManagerService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra;
                switch (i) {
                    case 0:
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            final String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                            this.this$0.mHandler.post(new Runnable() { // from class: com.android.server.accounts.AccountManagerService.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i2;
                                    AccountManagerService accountManagerService = AnonymousClass1.this.this$0;
                                    synchronized (accountManagerService.mUsers) {
                                        for (int i3 = 0; i3 < accountManagerService.mUsers.size(); i3++) {
                                            try {
                                                accountManagerService.purgeOldGrants((UserAccounts) accountManagerService.mUsers.valueAt(i3));
                                            } finally {
                                            }
                                        }
                                    }
                                    AccountManagerService accountManagerService2 = AnonymousClass1.this.this$0;
                                    String str = schemeSpecificPart;
                                    accountManagerService2.getClass();
                                    if (AccountManagerService.isSpecialPackageKey(str)) {
                                        return;
                                    }
                                    synchronized (accountManagerService2.mUsers) {
                                        int size = accountManagerService2.mUsers.size();
                                        for (i2 = 0; i2 < size; i2++) {
                                            UserAccounts userAccounts = (UserAccounts) accountManagerService2.mUsers.valueAt(i2);
                                            try {
                                                try {
                                                    accountManagerService2.mPackageManager.getPackageUidAsUser(str, userAccounts.userId);
                                                } catch (PackageManager.NameNotFoundException unused) {
                                                    userAccounts.accountsDb.mDeDatabase.getWritableDatabase().delete(LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN, "_package=? ", new String[]{str});
                                                    synchronized (userAccounts.dbLock) {
                                                        synchronized (userAccounts.cacheLock) {
                                                            try {
                                                                Iterator it = ((HashMap) userAccounts.visibilityCache).keySet().iterator();
                                                                while (it.hasNext()) {
                                                                    AccountManagerService.getPackagesAndVisibilityForAccountLocked((Account) it.next(), userAccounts).remove(str);
                                                                }
                                                                AccountManager.invalidateLocalAccountsDataCaches();
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (SQLiteCantOpenDatabaseException e) {
                                                Log.w("AccountManagerService", "Could not delete account visibility for user = " + userAccounts.userId, e);
                                            }
                                        }
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        if ("android.intent.action.USER_REMOVED".equals(intent.getAction()) && (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1)) >= 1) {
                            BootReceiver$$ExternalSyntheticOutline0.m(intExtra, "User ", " removed", "AccountManagerService");
                            AccountManagerService.m141$$Nest$mpurgeUserData(this.this$0, intExtra);
                            break;
                        }
                        break;
                }
            }
        }, intentFilter);
        LocalServices.addService(AccountManagerInternal.class, new AccountManagerInternalImpl());
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.accounts.AccountManagerService.1
            public final /* synthetic */ AccountManagerService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra;
                switch (i2) {
                    case 0:
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            final String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                            this.this$0.mHandler.post(new Runnable() { // from class: com.android.server.accounts.AccountManagerService.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i22;
                                    AccountManagerService accountManagerService = AnonymousClass1.this.this$0;
                                    synchronized (accountManagerService.mUsers) {
                                        for (int i3 = 0; i3 < accountManagerService.mUsers.size(); i3++) {
                                            try {
                                                accountManagerService.purgeOldGrants((UserAccounts) accountManagerService.mUsers.valueAt(i3));
                                            } finally {
                                            }
                                        }
                                    }
                                    AccountManagerService accountManagerService2 = AnonymousClass1.this.this$0;
                                    String str = schemeSpecificPart;
                                    accountManagerService2.getClass();
                                    if (AccountManagerService.isSpecialPackageKey(str)) {
                                        return;
                                    }
                                    synchronized (accountManagerService2.mUsers) {
                                        int size = accountManagerService2.mUsers.size();
                                        for (i22 = 0; i22 < size; i22++) {
                                            UserAccounts userAccounts = (UserAccounts) accountManagerService2.mUsers.valueAt(i22);
                                            try {
                                                try {
                                                    accountManagerService2.mPackageManager.getPackageUidAsUser(str, userAccounts.userId);
                                                } catch (PackageManager.NameNotFoundException unused) {
                                                    userAccounts.accountsDb.mDeDatabase.getWritableDatabase().delete(LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN, "_package=? ", new String[]{str});
                                                    synchronized (userAccounts.dbLock) {
                                                        synchronized (userAccounts.cacheLock) {
                                                            try {
                                                                Iterator it = ((HashMap) userAccounts.visibilityCache).keySet().iterator();
                                                                while (it.hasNext()) {
                                                                    AccountManagerService.getPackagesAndVisibilityForAccountLocked((Account) it.next(), userAccounts).remove(str);
                                                                }
                                                                AccountManager.invalidateLocalAccountsDataCaches();
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (SQLiteCantOpenDatabaseException e) {
                                                Log.w("AccountManagerService", "Could not delete account visibility for user = " + userAccounts.userId, e);
                                            }
                                        }
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        if ("android.intent.action.USER_REMOVED".equals(intent.getAction()) && (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1)) >= 1) {
                            BootReceiver$$ExternalSyntheticOutline0.m(intExtra, "User ", " removed", "AccountManagerService");
                            AccountManagerService.m141$$Nest$mpurgeUserData(this.this$0, intExtra);
                            break;
                        }
                        break;
                }
            }
        };
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter2, null, null);
        new PackageMonitor() { // from class: com.android.server.accounts.AccountManagerService.3
            public final void onPackageAdded(String str, int i3) {
                try {
                    UserAccounts userAccounts = AccountManagerService.this.getUserAccounts(UserHandle.getUserId(i3));
                    AccountManagerService accountManagerService = AccountManagerService.this;
                    accountManagerService.getClass();
                    for (Account account : accountManagerService.getAccountsOrEmptyArray(UserHandle.getUserId(i3), "android")) {
                        accountManagerService.cancelAccountAccessRequestNotificationIfNeeded(account, i3, true, userAccounts);
                    }
                } catch (SQLiteCantOpenDatabaseException e) {
                    Log.w("AccountManagerService", "Can't read accounts database", e);
                }
            }

            public final void onPackageUpdateFinished(String str, int i3) {
                try {
                    UserAccounts userAccounts = AccountManagerService.this.getUserAccounts(UserHandle.getUserId(i3));
                    AccountManagerService accountManagerService = AccountManagerService.this;
                    accountManagerService.getClass();
                    for (Account account : accountManagerService.getAccountsOrEmptyArray(UserHandle.getUserId(i3), "android")) {
                        accountManagerService.cancelAccountAccessRequestNotificationIfNeeded(account, i3, true, userAccounts);
                    }
                } catch (SQLiteCantOpenDatabaseException e) {
                    Log.w("AccountManagerService", "Can't read accounts database", e);
                }
            }
        }.register(context, messageHandler.getLooper(), userHandle, true);
        appOpsManager.startWatchingMode(62, (String) null, (AppOpsManager.OnOpChangedListener) new AppOpsManager.OnOpChangedInternalListener() { // from class: com.android.server.accounts.AccountManagerService.4
            public final void onOpChanged(int i3, String str) {
                try {
                    int currentUser = ActivityManager.getCurrentUser();
                    int packageUidAsUser = AccountManagerService.this.mPackageManager.getPackageUidAsUser(str, currentUser);
                    if (AccountManagerService.this.mAppOpsManager.checkOpNoThrow(62, packageUidAsUser, str) == 0) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            UserAccounts userAccounts = AccountManagerService.this.getUserAccounts(currentUser);
                            AccountManagerService accountManagerService = AccountManagerService.this;
                            accountManagerService.getClass();
                            for (Account account : accountManagerService.getAccountsOrEmptyArray(UserHandle.getUserId(packageUidAsUser), "android")) {
                                accountManagerService.cancelAccountAccessRequestNotificationIfNeeded(account, packageUidAsUser, str, true, userAccounts);
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                } catch (SQLiteCantOpenDatabaseException e) {
                    Log.w("AccountManagerService", "Can't read accounts database", e);
                }
            }
        });
        packageManager.addOnPermissionsChangeListener(new PackageManager.OnPermissionsChangedListener() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda10
            public final void onPermissionsChanged(int i3) {
                AccountManagerService accountManagerService = AccountManagerService.this;
                accountManagerService.getClass();
                AccountManager.invalidateLocalAccountsDataCaches();
                String[] packagesForUid = accountManagerService.mPackageManager.getPackagesForUid(i3);
                if (packagesForUid != null) {
                    int userId = UserHandle.getUserId(i3);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        Account[] accountArr = null;
                        for (String str : packagesForUid) {
                            if (accountManagerService.mPackageManager.checkPermission("android.permission.GET_ACCOUNTS", str) == 0) {
                                if (accountArr == null) {
                                    accountArr = accountManagerService.getAccountsOrEmptyArray(userId, "android");
                                    if (ArrayUtils.isEmpty(accountArr)) {
                                        break;
                                    }
                                }
                                Account[] accountArr2 = accountArr;
                                AccountManagerService.UserAccounts userAccounts = accountManagerService.getUserAccounts(UserHandle.getUserId(i3));
                                int length = accountArr2.length;
                                int i4 = 0;
                                while (i4 < length) {
                                    int i5 = i4;
                                    int i6 = length;
                                    Account[] accountArr3 = accountArr2;
                                    accountManagerService.cancelAccountAccessRequestNotificationIfNeeded(accountArr2[i4], i3, str, true, userAccounts);
                                    i4 = i5 + 1;
                                    accountArr2 = accountArr3;
                                    length = i6;
                                }
                                accountArr = accountArr2;
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        });
    }

    public static boolean accountExistsCache(Account account, UserAccounts userAccounts) {
        synchronized (userAccounts.cacheLock) {
            try {
                if (userAccounts.accountCache.containsKey(account.type)) {
                    for (Account account2 : (Account[]) userAccounts.accountCache.get(account.type)) {
                        if (account2.name.equals(account.name)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean canCallerAccessPackage(int i, int i2, String str) {
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        if (!CompatChanges.isChangeEnabled(154726397L, i)) {
            return packageManagerInternal.getPackageUid(str, 0L, i2) != -1;
        }
        boolean z = !packageManagerInternal.filterAppAccess(i, i2, str, true);
        if (!z && Log.isLoggable("AccountManagerService", 3)) {
            GestureWakeup$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i, "Package ", str, " is not visible to caller ", " for user "), i2, "AccountManagerService");
        }
        return z;
    }

    public static int getAccountVisibilityFromCache(Account account, UserAccounts userAccounts, String str) {
        int intValue;
        synchronized (userAccounts.cacheLock) {
            try {
                Integer num = (Integer) getPackagesAndVisibilityForAccountLocked(account, userAccounts).get(str);
                intValue = num != null ? num.intValue() : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
        return intValue;
    }

    public static HashMap getAuthenticatorTypeAndUIDForUser(IAccountAuthenticatorCache iAccountAuthenticatorCache, int i) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (RegisteredServicesCache.ServiceInfo serviceInfo : iAccountAuthenticatorCache.getAllServices(i)) {
            linkedHashMap.put(((AuthenticatorDescription) serviceInfo.type).type, Integer.valueOf(serviceInfo.uid));
        }
        return linkedHashMap;
    }

    public static NotificationId getCredentialPermissionNotificationId(int i, Account account, UserAccounts userAccounts, String str) {
        NotificationId notificationId;
        synchronized (userAccounts.credentialsPermissionNotificationIds) {
            try {
                Pair pair = new Pair(new Pair(account, str), Integer.valueOf(i));
                notificationId = (NotificationId) userAccounts.credentialsPermissionNotificationIds.get(pair);
                if (notificationId == null) {
                    notificationId = new NotificationId("AccountManagerService:38:" + account.hashCode() + ":" + str.hashCode() + ":" + i, 38);
                    userAccounts.credentialsPermissionNotificationIds.put(pair, notificationId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return notificationId;
    }

    public static Map getPackagesAndVisibilityForAccountLocked(Account account, UserAccounts userAccounts) {
        Map map = (Map) ((HashMap) userAccounts.visibilityCache).get(account);
        if (map != null) {
            return map;
        }
        Log.d("AccountManagerService", "Visibility was not initialized");
        HashMap hashMap = new HashMap();
        ((HashMap) userAccounts.visibilityCache).put(account, hashMap);
        AccountManager.invalidateLocalAccountsDataCaches();
        return hashMap;
    }

    public static String getPackagesForVisibilityStr(int i, Map map) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(":");
        HashMap hashMap = (HashMap) map;
        sb.append(hashMap.containsKey(Integer.valueOf(i)) ? TextUtils.join(",", (Iterable) hashMap.get(Integer.valueOf(i))) : "");
        return sb.toString();
    }

    public static NotificationId getSigninRequiredNotificationId(Account account, UserAccounts userAccounts) {
        NotificationId notificationId;
        synchronized (userAccounts.signinRequiredNotificationIds) {
            try {
                notificationId = (NotificationId) userAccounts.signinRequiredNotificationIds.get(account);
                if (notificationId == null) {
                    notificationId = new NotificationId("AccountManagerService:37:" + account.hashCode(), 37);
                    userAccounts.signinRequiredNotificationIds.put(account, notificationId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return notificationId;
    }

    public static AccountManagerService getSingleton() {
        return (AccountManagerService) sThis.get();
    }

    public static int handleIncomingUser(int i) {
        try {
            return ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, true, "", (String) null);
        } catch (RemoteException unused) {
            return i;
        }
    }

    public static Account insertAccountIntoCacheLocked(Account account, UserAccounts userAccounts) {
        Account[] accountArr = (Account[]) userAccounts.accountCache.get(account.type);
        int length = accountArr != null ? accountArr.length : 0;
        Account[] accountArr2 = new Account[length + 1];
        if (accountArr != null) {
            System.arraycopy(accountArr, 0, accountArr2, 0, length);
        }
        accountArr2[length] = new Account(account, account.getAccessId() != null ? account.getAccessId() : UUID.randomUUID().toString());
        userAccounts.accountCache.put(account.type, accountArr2);
        AccountManager.invalidateLocalAccountsDataCaches();
        return accountArr2[length];
    }

    public static List invalidateAuthTokenLocked(UserAccounts userAccounts, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        AccountsDb accountsDb = userAccounts.accountsDb;
        Cursor rawQuery = accountsDb.mDeDatabase.getReadableDatabaseUserIsUnlocked().rawQuery("SELECT ceDb.authtokens._id, ceDb.accounts.name, ceDb.authtokens.type FROM ceDb.accounts JOIN ceDb.authtokens ON ceDb.accounts._id = ceDb.authtokens.accounts_id WHERE ceDb.authtokens.authtoken = ? AND ceDb.accounts.type = ?", new String[]{str2, str});
        while (rawQuery.moveToNext()) {
            try {
                String string = rawQuery.getString(0);
                String string2 = rawQuery.getString(1);
                String string3 = rawQuery.getString(2);
                accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked().delete("ceDb.authtokens", "_id= ?", new String[]{string});
                arrayList.add(Pair.create(new Account(string2, str), string3));
            } finally {
                rawQuery.close();
            }
        }
        return arrayList;
    }

    public static boolean isProfileOwner(int i) {
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null && (devicePolicyManagerInternal.isActiveProfileOwner(i) || devicePolicyManagerInternal.isActiveDeviceOwner(i));
    }

    public static boolean isSpecialPackageKey(String str) {
        return "android:accounts:key_legacy_visible".equals(str) || "android:accounts:key_legacy_not_visible".equals(str);
    }

    public static void logGetAuthTokenMetrics(String str, String str2) {
        DevicePolicyEventLogger.createEvent(204).setStrings(new String[]{TextUtils.emptyIfNull(str), TextUtils.emptyIfNull(str2)}).write();
    }

    public static void onResult(IAccountManagerResponse iAccountManagerResponse, Bundle bundle) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "AccountManagerService calling onResult() on response " + iAccountManagerResponse);
        }
        try {
            iAccountManagerResponse.onResult(bundle);
        } catch (RemoteException e) {
            if (Log.isLoggable("AccountManagerService", 2)) {
                Log.v("AccountManagerService", "failure while notifying response", e);
            }
        }
    }

    public static String readAuthTokenInternal(Account account, UserAccounts userAccounts, String str) {
        String str2;
        synchronized (userAccounts.cacheLock) {
            try {
                Map map = (Map) ((HashMap) userAccounts.authTokenCache).get(account);
                if (map != null) {
                    return (String) map.get(str);
                }
                synchronized (userAccounts.dbLock) {
                    synchronized (userAccounts.cacheLock) {
                        try {
                            Map map2 = (Map) ((HashMap) userAccounts.authTokenCache).get(account);
                            if (map2 == null) {
                                map2 = userAccounts.accountsDb.findAuthTokensByAccount(account);
                                ((HashMap) userAccounts.authTokenCache).put(account, map2);
                            }
                            str2 = (String) map2.get(str);
                        } finally {
                        }
                    }
                }
                return str2;
            } finally {
            }
        }
    }

    public static TokenCache.Value readCachedTokenInternal(UserAccounts userAccounts, Account account, String str, String str2, byte[] bArr) {
        TokenCache.Value value;
        synchronized (userAccounts.cacheLock) {
            TokenCache tokenCache = userAccounts.accountTokenCaches;
            tokenCache.getClass();
            value = (TokenCache.Value) tokenCache.mCachedTokens.get(new TokenCache.Key(account, str, str2, bArr));
            long currentTimeMillis = System.currentTimeMillis();
            if (value == null || currentTimeMillis >= value.expiryEpochMillis) {
                if (value != null) {
                    TokenCache.TokenLruCache.Evictor evictor = (TokenCache.TokenLruCache.Evictor) tokenCache.mCachedTokens.mTokenEvictors.get(new Pair(account.type, value.token));
                    if (evictor != null) {
                        evictor.evict();
                    }
                }
                value = null;
            }
        }
        return value;
    }

    public static String readPreviousNameInternal(Account account, UserAccounts userAccounts) {
        if (account == null) {
            return null;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                AtomicReference atomicReference = (AtomicReference) userAccounts.previousNameCache.get(account);
                if (atomicReference != null) {
                    return (String) atomicReference.get();
                }
                String findDeAccountPreviousName = userAccounts.accountsDb.findDeAccountPreviousName(account);
                userAccounts.previousNameCache.put(account, new AtomicReference(findDeAccountPreviousName));
                return findDeAccountPreviousName;
            }
        }
    }

    public static String readUserDataInternal(Account account, UserAccounts userAccounts, String str) {
        Map map;
        Map map2;
        synchronized (userAccounts.cacheLock) {
            map = (Map) ((HashMap) userAccounts.userDataCache).get(account);
        }
        if (map == null) {
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    try {
                        map2 = (Map) ((HashMap) userAccounts.userDataCache).get(account);
                        if (map2 == null) {
                            map2 = userAccounts.accountsDb.findUserExtrasForAccount(account);
                            ((HashMap) userAccounts.userDataCache).put(account, map2);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            map = map2;
        }
        return (String) map.get(str);
    }

    public static void registerAccountListener(String[] strArr, String str, UserAccounts userAccounts) {
        synchronized (userAccounts.mReceiversForType) {
            if (strArr == null) {
                try {
                    strArr = new String[]{null};
                } catch (Throwable th) {
                    throw th;
                }
            }
            for (String str2 : strArr) {
                Map map = (Map) ((HashMap) userAccounts.mReceiversForType).get(str2);
                if (map == null) {
                    map = new HashMap();
                    ((HashMap) userAccounts.mReceiversForType).put(str2, map);
                }
                Integer num = (Integer) map.get(str);
                int i = 1;
                if (num != null) {
                    i = 1 + num.intValue();
                }
                map.put(str, Integer.valueOf(i));
            }
        }
    }

    public static void removeAccountFromCacheLocked(Account account, UserAccounts userAccounts) {
        Account[] accountArr = (Account[]) userAccounts.accountCache.get(account.type);
        if (accountArr != null) {
            ArrayList arrayList = new ArrayList();
            for (Account account2 : accountArr) {
                if (!account2.equals(account)) {
                    arrayList.add(account2);
                }
            }
            if (arrayList.isEmpty()) {
                userAccounts.accountCache.remove(account.type);
            } else {
                userAccounts.accountCache.put(account.type, (Account[]) arrayList.toArray(new Account[arrayList.size()]));
            }
        }
        ((HashMap) userAccounts.userDataCache).remove(account);
        ((HashMap) userAccounts.authTokenCache).remove(account);
        userAccounts.previousNameCache.remove(account);
        ((HashMap) userAccounts.visibilityCache).remove(account);
        AccountManager.invalidateLocalAccountsDataCaches();
    }

    public static void sendErrorResponse(IAccountManagerResponse iAccountManagerResponse, int i, String str) {
        try {
            iAccountManagerResponse.onError(i, str);
        } catch (RemoteException e) {
            if (Log.isLoggable("AccountManagerService", 2)) {
                Log.v("AccountManagerService", "failure while notifying response", e);
            }
        }
    }

    public static void setUserdataInternal(UserAccounts userAccounts, Account account, String str, String str2) {
        long clearCallingIdentity;
        synchronized (userAccounts.dbLock) {
            try {
                userAccounts.accountsDb.beginTransaction();
                try {
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId < 0) {
                        userAccounts.accountsDb.endTransaction();
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logAsUser(5, 4, false, Process.myPid(), "AccountManagerService", "Updating account " + account.toString() + " failed", "", userAccounts.userId);
                            return;
                        } finally {
                        }
                    }
                    long findExtrasIdByAccountId = userAccounts.accountsDb.findExtrasIdByAccountId(findDeAccountId, str);
                    if (findExtrasIdByAccountId >= 0) {
                        SQLiteDatabase writableDatabaseUserIsUnlocked = userAccounts.accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("value", str2);
                        if (writableDatabaseUserIsUnlocked.update("extras", contentValues, "_id=?", new String[]{String.valueOf(findExtrasIdByAccountId)}) != 1) {
                            userAccounts.accountsDb.endTransaction();
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                AuditLog.logAsUser(5, 4, false, Process.myPid(), "AccountManagerService", "Updating account " + account.toString() + " failed", "", userAccounts.userId);
                                return;
                            } finally {
                            }
                        }
                    } else if (userAccounts.accountsDb.insertExtra(str, findDeAccountId, str2) < 0) {
                        userAccounts.accountsDb.endTransaction();
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logAsUser(5, 4, false, Process.myPid(), "AccountManagerService", "Updating account " + account.toString() + " failed", "", userAccounts.userId);
                            return;
                        } finally {
                        }
                    }
                    userAccounts.accountsDb.setTransactionSuccessful();
                    userAccounts.accountsDb.endTransaction();
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        AuditLog.logAsUser(5, 4, true, Process.myPid(), "AccountManagerService", "Updating account " + account.toString() + " succeeded", "", userAccounts.userId);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        synchronized (userAccounts.cacheLock) {
                            Map map = (Map) ((HashMap) userAccounts.userDataCache).get(account);
                            if (map == null) {
                                map = userAccounts.accountsDb.findUserExtrasForAccount(account);
                                ((HashMap) userAccounts.userDataCache).put(account, map);
                            }
                            if (str2 == null) {
                                map.remove(str);
                            } else {
                                map.put(str, str2);
                            }
                            AccountManager.invalidateLocalAccountUserDataCaches();
                        }
                        return;
                    } finally {
                    }
                } catch (Throwable th) {
                    userAccounts.accountsDb.endTransaction();
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        AuditLog.logAsUser(5, 4, false, Process.myPid(), "AccountManagerService", "Updating account " + account.toString() + " failed", "", userAccounts.userId);
                        throw th;
                    } finally {
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
            throw th2;
        }
    }

    public static void unregisterAccountListener(String[] strArr, String str, UserAccounts userAccounts) {
        synchronized (userAccounts.mReceiversForType) {
            if (strArr == null) {
                try {
                    strArr = new String[]{null};
                } catch (Throwable th) {
                    throw th;
                }
            }
            for (String str2 : strArr) {
                Map map = (Map) ((HashMap) userAccounts.mReceiversForType).get(str2);
                if (map == null || map.get(str) == null) {
                    throw new IllegalArgumentException("attempt to unregister wrong receiver");
                }
                Integer num = (Integer) map.get(str);
                if (num.intValue() == 1) {
                    map.remove(str);
                } else {
                    map.put(str, Integer.valueOf(num.intValue() - 1));
                }
            }
        }
    }

    public static boolean updateAccountVisibilityLocked(int i, Account account, UserAccounts userAccounts, String str) {
        AccountsDb accountsDb = userAccounts.accountsDb;
        long findDeAccountId = accountsDb.findDeAccountId(account);
        if (findDeAccountId < 0) {
            return false;
        }
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            SQLiteDatabase writableDatabase = accountsDb.mDeDatabase.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("accounts_id", String.valueOf(findDeAccountId));
            contentValues.put("_package", str);
            contentValues.put("value", String.valueOf(i));
            if (writableDatabase.replace(LauncherConfigurationInternal.KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN, "value", contentValues) == -1) {
                return false;
            }
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
            getPackagesAndVisibilityForAccountLocked(account, userAccounts).put(str, Integer.valueOf(i));
            AccountManager.invalidateLocalAccountsDataCaches();
            return true;
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    public static void writeAuthTokenIntoCacheLocked(UserAccounts userAccounts, Account account, String str, String str2) {
        Map map = (Map) ((HashMap) userAccounts.authTokenCache).get(account);
        if (map == null) {
            map = userAccounts.accountsDb.findAuthTokensByAccount(account);
            ((HashMap) userAccounts.authTokenCache).put(account, map);
        }
        if (str2 == null) {
            map.remove(str);
        } else {
            map.put(str, str2);
        }
    }

    public final boolean accountAuthenticated(Account account) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", String.format("accountAuthenticated( account: %s, callerUid: %s)", account, Integer.valueOf(callingUid)));
        }
        Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot notify authentication for accounts of type: ", account.type));
        }
        if (!canUserModifyAccounts(callingUserId, callingUid) || !canUserModifyAccountsForType(callingUserId, callingUid, account.type)) {
            return false;
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            getUserAccounts(callingUserId);
            return updateLastAuthenticatedTime(account);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void addAccount(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) {
        Bundle.setDefusable(bundle, true);
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "addAccount: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + Arrays.toString(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (!canUserModifyAccounts(userId, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User is not allowed to add an account!");
            } catch (RemoteException unused) {
            }
            showCantAddAccount(100, userId);
        } else if (canUserModifyAccountsForType(userId, callingUid, str)) {
            addAccountAndLogMetrics(iAccountManagerResponse, str, str2, strArr, z, bundle, userId);
        } else {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
            } catch (RemoteException unused2) {
            }
            showCantAddAccount(101, userId);
        }
    }

    public final void addAccountAndLogMetrics(IAccountManagerResponse iAccountManagerResponse, final String str, final String str2, final String[] strArr, boolean z, Bundle bundle, int i) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        bundle2.putInt("callerUid", callingUid);
        bundle2.putInt("callerPid", callingPid);
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(i);
            logRecord("action_called_account_add", "accounts", -1L, userAccounts, callingUid);
            final Bundle bundle3 = bundle2;
            new Session(userAccounts, iAccountManagerResponse, str, z) { // from class: com.android.server.accounts.AccountManagerService.9
                @Override // com.android.server.accounts.AccountManagerService.Session
                public final void run() {
                    this.mAuthenticator.addAccount(this, this.mAccountType, str2, strArr, bundle3);
                    AccountManagerService.m140$$Nest$mlogAddAccountMetrics(AccountManagerService.this, bundle3.getString("androidPackageName"), str, strArr, str2);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public final String toDebugString(long j) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(super.toDebugString(j));
                    sb.append(", addAccount, accountType ");
                    sb.append(str);
                    sb.append(", requiredFeatures ");
                    String[] strArr2 = strArr;
                    sb.append(strArr2 != null ? TextUtils.join(",", strArr2) : null);
                    return sb.toString();
                }
            }.bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle, int i) {
        Bundle.setDefusable(bundle, true);
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "addAccount: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + Arrays.toString(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid() + ", for user id " + i);
        }
        Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        Preconditions.checkArgument(str != null, "accountType cannot be null");
        if (isCrossUser(callingUid, i)) {
            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(UserHandle.getCallingUserId(), i, "User ", " trying to add account for "));
        }
        if (!canUserModifyAccounts(i, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User is not allowed to add an account!");
            } catch (RemoteException unused) {
            }
            showCantAddAccount(100, i);
        } else if (canUserModifyAccountsForType(i, callingUid, str)) {
            addAccountAndLogMetrics(iAccountManagerResponse, str, str2, strArr, z, bundle, i);
        } else {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
            } catch (RemoteException unused2) {
            }
            showCantAddAccount(101, i);
        }
    }

    public final boolean addAccountExplicitly(Account account, String str, Bundle bundle, String str2) {
        return addAccountExplicitlyWithVisibility(account, str, bundle, null, str2);
    }

    public final boolean addAccountExplicitlyWithVisibility(Account account, String str, Bundle bundle, Map map, String str2) {
        Bundle.setDefusable(bundle, true);
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        Objects.requireNonNull(account, "account cannot be null");
        Log.v("AccountManagerService", "addAccountExplicitly: caller's uid=" + callingUid + ", pid=" + Binder.getCallingPid() + ", packageName=" + str2 + ", accountType=" + account.type);
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            String str3 = account.type;
            StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(callingUid, "uid=", ", package=", str2, " cannot explicitly add accounts of type: ");
            m.append(str3);
            throw new SecurityException(m.toString());
        }
        try {
            IDeviceAccountPolicy asInterface = IDeviceAccountPolicy.Stub.asInterface(ServiceManager.getService("device_account_policy"));
            if (asInterface != null && !asInterface.isAccountAdditionAllowed(account.type, account.name, true)) {
                throw new SecurityException("Security policy blocks this account addition");
            }
        } catch (RemoteException unused) {
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return addAccountInternal(getUserAccounts(callingUserId), account, str, bundle, callingUid, map, str2);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean addAccountInternal(UserAccounts userAccounts, Account account, String str, Bundle bundle, int i, Map map, String str2) {
        PersonaManagerService personaManagerService;
        Bundle.setDefusable(bundle, true);
        if (account == null) {
            return false;
        }
        String str3 = account.name;
        if (str3 != null && str3.length() > 200) {
            Log.w("AccountManagerService", "Account cannot be added - Name longer than 200 chars");
            return false;
        }
        String str4 = account.type;
        if (str4 != null && str4.length() > 200) {
            Log.w("AccountManagerService", "Account cannot be added - Name longer than 200 chars");
            return false;
        }
        if (!isLocalUnlockedUser(userAccounts.userId)) {
            Log.w("AccountManagerService", "Account " + account.toSafeString() + " cannot be added - user " + userAccounts.userId + " is locked. callingUid=" + i);
            return false;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                userAccounts.accountsDb.beginTransaction();
                try {
                    if (userAccounts.accountsDb.findCeAccountId(account) >= 0) {
                        Log.w("AccountManagerService", "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping since the account already exists");
                        return false;
                    }
                    if (userAccounts.accountsDb.findAllDeAccounts().size() > 100) {
                        Log.w("AccountManagerService", "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping since more than 100 accounts on device exist");
                        return false;
                    }
                    SQLiteDatabase writableDatabaseUserIsUnlocked = userAccounts.accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", account.name);
                    contentValues.put("type", account.type);
                    contentValues.put("password", str);
                    long insert = writableDatabaseUserIsUnlocked.insert("ceDb.accounts", "name", contentValues);
                    if (insert < 0) {
                        Log.w("AccountManagerService", "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping the DB insert failed");
                        return false;
                    }
                    if (userAccounts.accountsDb.insertDeAccount(account, insert) < 0) {
                        Log.w("AccountManagerService", "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping the DB insert failed");
                        return false;
                    }
                    if (bundle != null) {
                        for (String str5 : bundle.keySet()) {
                            if (userAccounts.accountsDb.insertExtra(str5, insert, bundle.getString(str5)) < 0) {
                                Log.w("AccountManagerService", "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping since insertExtra failed for key " + str5);
                                return false;
                            }
                            AccountManager.invalidateLocalAccountUserDataCaches();
                        }
                    }
                    if (map != null) {
                        for (Map.Entry entry : map.entrySet()) {
                            setAccountVisibility(account, (String) entry.getKey(), ((Integer) entry.getValue()).intValue(), false, userAccounts, i);
                            insert = insert;
                        }
                    }
                    userAccounts.accountsDb.setTransactionSuccessful();
                    logRecord("action_account_add", "accounts", insert, userAccounts, i);
                    insertAccountIntoCacheLocked(account, userAccounts);
                    if (getUserManager().getUserInfo(userAccounts.userId).canHaveProfile()) {
                        int i2 = userAccounts.userId;
                        for (UserInfo userInfo : getUserManager().getUsers()) {
                            if (userInfo.isRestricted() && i2 == userInfo.restrictedProfileParentId) {
                                addSharedAccountAsUser(account, userInfo.id);
                                if (isLocalUnlockedUser(userInfo.id)) {
                                    MessageHandler messageHandler = this.mHandler;
                                    messageHandler.sendMessage(messageHandler.obtainMessage(4, i2, userInfo.id, account));
                                }
                            }
                        }
                    }
                    sendNotificationAccountUpdated(account, userAccounts);
                    Log.i("AccountManagerService", "callingUid=" + i + ", userId=" + userAccounts.userId + " added account");
                    sendAccountsChangedBroadcast(userAccounts.userId, account.type, "addAccount");
                    boolean z = PersonaManagerService.DEBUG;
                    synchronized (PersonaManagerService.class) {
                        personaManagerService = PersonaManagerService.sInstance;
                    }
                    if (personaManagerService != null) {
                        int i3 = userAccounts.userId;
                        String str6 = account.type;
                        PersonaManagerService.PersonaHandler personaHandler = personaManagerService.mPersonaHandler;
                        if (personaHandler != null) {
                            Message obtainMessage = personaHandler.obtainMessage(70);
                            obtainMessage.arg1 = i3;
                            obtainMessage.arg2 = 1;
                            obtainMessage.obj = str6;
                            personaManagerService.mPersonaHandler.sendMessage(obtainMessage);
                        }
                    }
                    String str7 = account.type;
                    DevicePolicyEventLogger createEvent = DevicePolicyEventLogger.createEvent(203);
                    String emptyIfNull = TextUtils.emptyIfNull(str7);
                    String emptyIfNull2 = TextUtils.emptyIfNull(str2);
                    HashMap hashMap = new HashMap();
                    if (map != null) {
                        for (Map.Entry entry2 : map.entrySet()) {
                            if (!hashMap.containsKey(entry2.getValue())) {
                                hashMap.put((Integer) entry2.getValue(), new HashSet());
                            }
                            ((Set) hashMap.get(entry2.getValue())).add((String) entry2.getKey());
                        }
                    }
                    createEvent.setStrings(emptyIfNull, emptyIfNull2, new String[]{getPackagesForVisibilityStr(0, hashMap), getPackagesForVisibilityStr(1, hashMap), getPackagesForVisibilityStr(2, hashMap), getPackagesForVisibilityStr(3, hashMap), getPackagesForVisibilityStr(4, hashMap)}).write();
                    return true;
                } finally {
                    userAccounts.accountsDb.endTransaction();
                }
            }
        }
    }

    public final void addSharedAccountAsUser(Account account, int i) {
        UserAccounts userAccounts = getUserAccounts(handleIncomingUser(i));
        AccountsDb accountsDb = userAccounts.accountsDb;
        accountsDb.mDeDatabase.getWritableDatabase().delete("shared_accounts", "name=? AND type=?", new String[]{account.name, account.type});
        SQLiteDatabase writableDatabase = accountsDb.mDeDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", account.name);
        contentValues.put("type", account.type);
        long insert = writableDatabase.insert("shared_accounts", "name", contentValues);
        if (insert >= 0) {
            logRecord("action_account_add", "shared_accounts", insert, userAccounts, IAccountManager.Stub.getCallingUid());
            return;
        }
        Log.w("AccountManagerService", "insertAccountIntoDatabase: " + account.toSafeString() + ", skipping the DB insert failed");
    }

    public final void addSharedAccountsFromParentUser(int i, int i2, String str) {
        if (ActivityManager.checkComponentPermission("android.permission.MANAGE_USERS", Binder.getCallingUid(), -1, true) != 0 && ActivityManager.checkComponentPermission("android.permission.CREATE_USERS", Binder.getCallingUid(), -1, true) != 0) {
            throw new SecurityException("You need MANAGE_USERS or CREATE_USERS permission to: addSharedAccountsFromParentUser");
        }
        for (Account account : getAccountsOrEmptyArray(i, str)) {
            addSharedAccountAsUser(account, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] calculatePackageSignatureDigest(int r6, java.lang.String r7) {
        /*
            r5 = this;
            java.lang.String r0 = "AccountManagerService"
            r1 = 0
            java.lang.String r2 = "SHA-256"
            java.security.MessageDigest r2 = java.security.MessageDigest.getInstance(r2)     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            android.content.pm.PackageManager r5 = r5.mPackageManager     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            r3 = 64
            android.content.pm.PackageInfo r5 = r5.getPackageInfoAsUser(r7, r3, r6)     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            android.content.pm.Signature[] r5 = r5.signatures     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            int r6 = r5.length     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            r3 = 0
        L15:
            if (r3 >= r6) goto L36
            r4 = r5[r3]     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            byte[] r4 = r4.toByteArray()     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            r2.update(r4)     // Catch: java.security.NoSuchAlgorithmException -> L23 android.content.pm.PackageManager.NameNotFoundException -> L25
            int r3 = r3 + 1
            goto L15
        L23:
            r5 = move-exception
            goto L30
        L25:
            java.lang.String r5 = "Could not find packageinfo for: "
            java.lang.String r5 = r5.concat(r7)
            android.util.Log.w(r0, r5)
        L2e:
            r2 = r1
            goto L36
        L30:
            java.lang.String r6 = "SHA-256 should be available"
            android.util.Log.wtf(r0, r6, r5)
            goto L2e
        L36:
            if (r2 != 0) goto L39
            goto L3d
        L39:
            byte[] r1 = r2.digest()
        L3d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accounts.AccountManagerService.calculatePackageSignatureDigest(int, java.lang.String):byte[]");
    }

    public final boolean canUserModifyAccounts(int i, int i2) {
        return isProfileOwner(i2) || !getUserManager().getUserRestrictions(new UserHandle(i)).getBoolean("no_modify_accounts");
    }

    public final boolean canUserModifyAccountsForType(final int i, final int i2, final String str) {
        return ((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda8
            public final Object getOrThrow() {
                String[] accountTypesWithManagementDisabledAsUser;
                AccountManagerService accountManagerService = AccountManagerService.this;
                int i3 = i2;
                int i4 = i;
                String str2 = str;
                accountManagerService.getClass();
                if (!AccountManagerService.isProfileOwner(i3) && (accountTypesWithManagementDisabledAsUser = ((DevicePolicyManager) accountManagerService.mContext.getSystemService("device_policy")).getAccountTypesWithManagementDisabledAsUser(i4)) != null) {
                    for (String str3 : accountTypesWithManagementDisabledAsUser) {
                        if (str3.equals(str2)) {
                            return Boolean.FALSE;
                        }
                    }
                    return Boolean.TRUE;
                }
                return Boolean.TRUE;
            }
        })).booleanValue();
    }

    public final void cancelAccountAccessRequestNotificationIfNeeded(Account account, int i, String str, boolean z, UserAccounts userAccounts) {
        if (!z || hasAccountAccess(account, str, UserHandle.getUserHandleForUid(i))) {
            cancelNotification(getCredentialPermissionNotificationId(i, account, userAccounts, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE"), userAccounts);
        }
    }

    public final void cancelAccountAccessRequestNotificationIfNeeded(Account account, int i, boolean z, UserAccounts userAccounts) {
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                cancelAccountAccessRequestNotificationIfNeeded(account, i, str, z, userAccounts);
            }
        }
    }

    public final void cancelNotification(NotificationId notificationId, UserAccounts userAccounts) {
        String packageName = this.mContext.getPackageName();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            this.mInjector.getClass();
            NotificationManager.getService().cancelNotificationWithTag(packageName, "android", notificationId.mTag, notificationId.mId, UserHandle.of(userAccounts.userId).getIdentifier());
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean checkPermissionAndNote(String str, int i, String... strArr) {
        for (String str2 : strArr) {
            if (this.mContext.checkCallingOrSelfPermission(str2) == 0) {
                if (Log.isLoggable("AccountManagerService", 2)) {
                    Log.v("AccountManagerService", "  caller uid " + i + " has " + str2);
                }
                int permissionToOpCode = AppOpsManager.permissionToOpCode(str2);
                if (permissionToOpCode == -1 || this.mAppOpsManager.noteOpNoThrow(permissionToOpCode, i, str) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void clearPassword(Account account) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "clearPassword: " + account + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot clear passwords for accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            setPasswordInternal(callingUid, account, getUserAccounts(callingUserId), null);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean z, int i) {
        Bundle.setDefusable(bundle, true);
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "confirmCredentials: " + account + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        if (isCrossUser(callingUid, i)) {
            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(UserHandle.getCallingUserId(), i, "User ", " trying to confirm account credentials for "));
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new AnonymousClass12(this, getUserAccounts(i), iAccountManagerResponse, account.type, z, account.name, account, bundle).bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void copyAccountToUser(final IAccountManagerResponse iAccountManagerResponse, final Account account, final int i, int i2) {
        if (isCrossUser(Binder.getCallingUid(), -1)) {
            throw new SecurityException("Calling copyAccountToUser requires android.permission.INTERACT_ACROSS_USERS_FULL");
        }
        UserAccounts userAccounts = getUserAccounts(i);
        final UserAccounts userAccounts2 = getUserAccounts(i2);
        Slog.d("AccountManagerService", "Copying account " + account.toSafeString() + " from user " + i + " to user " + i2);
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new Session(userAccounts, iAccountManagerResponse, account.type, account.name) { // from class: com.android.server.accounts.AccountManagerService.5
                @Override // com.android.server.accounts.AccountManagerService.Session
                public final void onResult(Bundle bundle) {
                    Bundle.setDefusable(bundle, true);
                    if (bundle == null || !bundle.getBoolean("booleanResult", false)) {
                        super.onResult(bundle);
                        return;
                    }
                    AccountManagerService accountManagerService = AccountManagerService.this;
                    IAccountManagerResponse iAccountManagerResponse2 = iAccountManagerResponse;
                    Account account2 = account;
                    UserAccounts userAccounts3 = userAccounts2;
                    int i3 = i;
                    accountManagerService.getClass();
                    Bundle.setDefusable(bundle, true);
                    long clearCallingIdentity2 = IAccountManager.Stub.clearCallingIdentity();
                    try {
                        new Session(userAccounts3, iAccountManagerResponse2, account2.type, account2.name, account2, i3, bundle) { // from class: com.android.server.accounts.AccountManagerService.6
                            public final /* synthetic */ Account val$account;
                            public final /* synthetic */ Bundle val$accountCredentials;
                            public final /* synthetic */ int val$parentUserId;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(userAccounts3, iAccountManagerResponse2, r14, false, false, r15, false, false);
                                this.val$account = account2;
                                this.val$parentUserId = i3;
                                this.val$accountCredentials = bundle;
                            }

                            @Override // com.android.server.accounts.AccountManagerService.Session
                            public final void onResult(Bundle bundle2) {
                                Bundle.setDefusable(bundle2, true);
                                super.onResult(bundle2);
                            }

                            @Override // com.android.server.accounts.AccountManagerService.Session
                            public final void run() {
                                AccountManagerService accountManagerService2 = AccountManagerService.this;
                                for (Account account3 : accountManagerService2.getAccounts(this.val$parentUserId, accountManagerService2.mContext.getOpPackageName())) {
                                    if (account3.equals(this.val$account)) {
                                        this.mAuthenticator.addAccountFromCredentials(this, this.val$account, this.val$accountCredentials);
                                        return;
                                    }
                                }
                            }

                            @Override // com.android.server.accounts.AccountManagerService.Session
                            public final String toDebugString(long j) {
                                return super.toDebugString(j) + ", getAccountCredentialsForClone, " + this.val$account.type;
                            }
                        }.bind();
                    } finally {
                        IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity2);
                    }
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public final void run() {
                    this.mAuthenticator.getAccountCredentialsForCloning(this, account);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public final String toDebugString(long j) {
                    return super.toDebugString(j) + ", getAccountCredentialsForClone, " + account.type;
                }
            }.bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createNoCredentialsPermissionNotification(Account account, Intent intent, String str, UserAccounts userAccounts) {
        String str2;
        Context context;
        int i = userAccounts.userId;
        int intExtra = intent.getIntExtra("uid", -1);
        String stringExtra = intent.getStringExtra("authTokenType");
        Context context2 = this.mContext;
        try {
            PackageManager packageManager = this.mPackageManager;
            str = packageManager.getApplicationLabel(packageManager.getApplicationInfoAsUser(str, 0, i)).toString();
        } catch (PackageManager.NameNotFoundException unused) {
        }
        String string = context2.getString(17042087, str, account.name);
        int indexOf = string.indexOf(10);
        if (indexOf > 0) {
            String substring = string.substring(0, indexOf);
            str2 = string.substring(indexOf + 1);
            string = substring;
        } else {
            str2 = "";
        }
        UserHandle of = UserHandle.of(i);
        try {
            Context context3 = this.mContext;
            context = context3.createPackageContextAsUser(context3.getPackageName(), 0, of);
        } catch (PackageManager.NameNotFoundException unused2) {
            context = this.mContext;
        }
        installNotification(getCredentialPermissionNotificationId(intExtra, account, userAccounts, stringExtra), new Notification.Builder(context, SystemNotificationChannels.ACCOUNT).setSmallIcon(R.drawable.stat_sys_warning).setWhen(0L).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(str2).setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, intent, 335544320, null, of)).build(), "android", of.getIdentifier());
    }

    public final IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String str, UserHandle userHandle) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            throw new SecurityException("Can be called only by system UID");
        }
        Objects.requireNonNull(account, "account cannot be null");
        Objects.requireNonNull(str, "packageName cannot be null");
        Objects.requireNonNull(userHandle, "userHandle cannot be null");
        int identifier = userHandle.getIdentifier();
        Preconditions.checkArgumentInRange(identifier, 0, Integer.MAX_VALUE, "user must be concrete");
        try {
            int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, identifier);
            Intent newGrantCredentialsPermissionIntent = newGrantCredentialsPermissionIntent(account, str, packageUidAsUser, new AccountAuthenticatorResponse((IAccountAuthenticatorResponse) new AnonymousClass17(packageUidAsUser, account, null)), "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", false);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PendingIntent.getActivityAsUser(this.mContext, 0, newGrantCredentialsPermissionIntent, 1409286144, null, new UserHandle(identifier)).getIntentSender();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e("AccountManagerService", "Unknown package ".concat(str));
            return null;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        boolean z;
        boolean isLocalUnlockedUser;
        if (DumpUtils.checkDumpPermission(this.mContext, "AccountManagerService", printWriter)) {
            boolean z2 = true;
            if (strArr != null) {
                for (String str : strArr) {
                    if ("--checkin".equals(str)) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            if (!z) {
                if (strArr != null) {
                    for (String str2 : strArr) {
                        if ("-c".equals(str2)) {
                            break;
                        }
                    }
                }
                z2 = false;
            }
            PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            for (UserInfo userInfo : getUserManager().getUsers()) {
                indentingPrintWriter.println("User " + userInfo + ":");
                indentingPrintWriter.increaseIndent();
                UserAccounts userAccounts = getUserAccounts(userInfo.id);
                if (z2) {
                    synchronized (userAccounts.dbLock) {
                        userAccounts.accountsDb.dumpDeAccountsTable(indentingPrintWriter);
                    }
                } else {
                    Account[] accountsFromCache = getAccountsFromCache(userAccounts, null, 1000, "android", false);
                    indentingPrintWriter.println("Accounts: " + accountsFromCache.length);
                    int length = accountsFromCache.length;
                    for (int i = 0; i < length; i++) {
                        indentingPrintWriter.println("  " + accountsFromCache[i].toString());
                    }
                    indentingPrintWriter.println();
                    synchronized (userAccounts.dbLock) {
                        userAccounts.accountsDb.dumpDebugTable(indentingPrintWriter);
                    }
                    indentingPrintWriter.println();
                    synchronized (this.mSessions) {
                        try {
                            long elapsedRealtime = SystemClock.elapsedRealtime();
                            indentingPrintWriter.println("Active Sessions: " + this.mSessions.size());
                            Iterator it = this.mSessions.values().iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter.println("  " + ((Session) it.next()).toDebugString(elapsedRealtime));
                            }
                        } finally {
                        }
                    }
                    indentingPrintWriter.println();
                    this.mAuthenticatorCache.dump(fileDescriptor, indentingPrintWriter, strArr, userAccounts.userId);
                    synchronized (this.mUsers) {
                        isLocalUnlockedUser = isLocalUnlockedUser(userAccounts.userId);
                    }
                    if (isLocalUnlockedUser) {
                        indentingPrintWriter.println();
                        synchronized (userAccounts.dbLock) {
                            try {
                                Map findAllVisibilityValues = userAccounts.accountsDb.findAllVisibilityValues();
                                indentingPrintWriter.println("Account visibility:");
                                HashMap hashMap = (HashMap) findAllVisibilityValues;
                                for (Account account : hashMap.keySet()) {
                                    indentingPrintWriter.println("  " + account.name);
                                    for (Map.Entry entry : ((Map) hashMap.get(account)).entrySet()) {
                                        indentingPrintWriter.println("    " + ((String) entry.getKey()) + ", " + entry.getValue());
                                    }
                                }
                            } finally {
                            }
                        }
                    } else {
                        continue;
                    }
                }
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
            }
            if (this.mAccountHistory.isEmpty()) {
                return;
            }
            indentingPrintWriter.println("Account event dump: ");
            indentingPrintWriter.increaseIndent();
            for (AccountEventRecord accountEventRecord : (AccountEventRecord[]) this.mAccountHistory.toArray()) {
                indentingPrintWriter.print("description", accountEventRecord.mDescription);
                indentingPrintWriter.print("date", accountEventRecord.mDate);
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
    }

    public final void editProperties(IAccountManagerResponse iAccountManagerResponse, final String str, boolean z) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "editProperties: accountType " + str + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, str) && !isSystemUid(callingUid)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot edit authenticator properites for account type: ", str));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new Session(this, getUserAccounts(callingUserId), iAccountManagerResponse, str, z) { // from class: com.android.server.accounts.AccountManagerService.16
                @Override // com.android.server.accounts.AccountManagerService.Session
                public final void run() {
                    this.mAuthenticator.editProperties(this, this.mAccountType);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public final String toDebugString(long j) {
                    return super.toDebugString(j) + ", editProperties, accountType " + str;
                }
            }.bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Account[] filterAccounts(UserAccounts userAccounts, Account[] accountArr, int i, String str, boolean z) {
        String packageNameForUid = str == null ? getPackageNameForUid(i) : str;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Account account : accountArr) {
            Integer resolveAccountVisibility = resolveAccountVisibility(account, userAccounts, packageNameForUid);
            int intValue = resolveAccountVisibility.intValue();
            if (intValue == 1 || intValue == 2 || (z && intValue == 4)) {
                linkedHashMap.put(account, resolveAccountVisibility);
            }
        }
        Map filterSharedAccounts = filterSharedAccounts(userAccounts, linkedHashMap, i, str);
        return (Account[]) filterSharedAccounts.keySet().toArray(new Account[filterSharedAccounts.size()]);
    }

    public final Map filterSharedAccounts(UserAccounts userAccounts, Map map, int i, String str) {
        int i2;
        UserInfo userInfo;
        String str2;
        String str3;
        if (getUserManager() == null || userAccounts == null || (i2 = userAccounts.userId) < 0 || i == 1000 || (userInfo = getUserManager().getUserInfo(i2)) == null || !userInfo.isRestricted()) {
            return map;
        }
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        if (packagesForUid == null) {
            packagesForUid = new String[0];
        }
        String string = this.mContext.getResources().getString(R.string.connected_display_thermally_unavailable_notification_content);
        for (String str4 : packagesForUid) {
            if (string.contains(";" + str4 + ";")) {
                return map;
            }
        }
        Account[] sharedAccountsAsUser = getSharedAccountsAsUser(i2);
        if (ArrayUtils.isEmpty(sharedAccountsAsUser)) {
            return map;
        }
        String str5 = "";
        try {
            if (str == null) {
                for (String str6 : packagesForUid) {
                    PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str6, 0);
                    if (packageInfo != null && (str2 = packageInfo.restrictedAccountType) != null) {
                        str5 = str2;
                        break;
                    }
                }
            } else {
                PackageInfo packageInfo2 = this.mPackageManager.getPackageInfo(str, 0);
                if (packageInfo2 != null && (str3 = packageInfo2.restrictedAccountType) != null) {
                    str5 = str3;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("AccountManagerService", "filterSharedAccounts#Package not found " + e.getMessage());
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((LinkedHashMap) map).entrySet()) {
            Account account = (Account) entry.getKey();
            if (!account.type.equals(str5)) {
                int length = sharedAccountsAsUser.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        linkedHashMap.put(account, (Integer) entry.getValue());
                        break;
                    }
                    if (sharedAccountsAsUser[i3].equals(account)) {
                        break;
                    }
                    i3++;
                }
            } else {
                linkedHashMap.put(account, (Integer) entry.getValue());
            }
        }
        return linkedHashMap;
    }

    public final void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean z, Bundle bundle2, int i) {
        Bundle.setDefusable(bundle, true);
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "finishSession: response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + callingUid + ", caller's user id " + UserHandle.getCallingUserId() + ", pid " + Binder.getCallingPid() + ", for user id " + i);
        }
        Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        if (bundle == null || bundle.size() == 0) {
            throw new IllegalArgumentException("sessionBundle is empty");
        }
        if (isCrossUser(callingUid, i)) {
            throw new SecurityException(DualAppManagerService$$ExternalSyntheticOutline0.m(UserHandle.getCallingUserId(), i, "User ", " trying to finish session for ", " without cross user permission"));
        }
        if (!canUserModifyAccounts(i, callingUid)) {
            sendErrorResponse(iAccountManagerResponse, 100, "User is not allowed to add an account!");
            showCantAddAccount(100, i);
            return;
        }
        int callingPid = Binder.getCallingPid();
        try {
            Bundle decryptBundle = CryptoHelper.getInstance().decryptBundle(bundle);
            if (decryptBundle == null) {
                sendErrorResponse(iAccountManagerResponse, 8, "failed to decrypt session bundle");
                return;
            }
            String string = decryptBundle.getString("accountType");
            if (TextUtils.isEmpty(string)) {
                sendErrorResponse(iAccountManagerResponse, 7, "accountType is empty");
                return;
            }
            if (bundle2 != null) {
                decryptBundle.putAll(bundle2);
            }
            decryptBundle.putInt("callerUid", callingUid);
            decryptBundle.putInt("callerPid", callingPid);
            if (!canUserModifyAccountsForType(i, callingUid, string)) {
                sendErrorResponse(iAccountManagerResponse, 101, "User cannot modify accounts of this type (policy).");
                showCantAddAccount(101, i);
                return;
            }
            long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
            try {
                UserAccounts userAccounts = getUserAccounts(i);
                logRecord("action_called_account_session_finish", "accounts", -1L, userAccounts, callingUid);
                new AnonymousClass7(this, userAccounts, iAccountManagerResponse, string, z, decryptBundle, string).bind();
            } finally {
                IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (GeneralSecurityException e) {
            if (Log.isLoggable("AccountManagerService", 3)) {
                Log.v("AccountManagerService", "Failed to decrypt session bundle!", e);
            }
            sendErrorResponse(iAccountManagerResponse, 8, "failed to decrypt session bundle");
        }
    }

    public final void getAccountByTypeAndFeatures(final IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, final String str2) {
        int callingUid = Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str2);
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "getAccount: accountType " + str + ", response " + iAccountManagerResponse + ", features " + Arrays.toString(strArr) + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (ArrayUtils.isEmpty(strArr)) {
                handleGetAccountsResult(iAccountManagerResponse, getAccountsFromCache(userAccounts, str, callingUid, str2, true), str2);
            } else {
                new GetAccountsByTypeAndFeatureSession(userAccounts, new IAccountManagerResponse.Stub() { // from class: com.android.server.accounts.AccountManagerService.18
                    public final void onError(int i, String str3) {
                    }

                    public final void onResult(Bundle bundle) {
                        Parcelable[] parcelableArray = bundle.getParcelableArray("accounts");
                        Account[] accountArr = new Account[parcelableArray.length];
                        for (int i = 0; i < parcelableArray.length; i++) {
                            accountArr[i] = (Account) parcelableArray[i];
                        }
                        AccountManagerService.this.handleGetAccountsResult(iAccountManagerResponse, accountArr, str2);
                    }
                }, str, strArr, callingUid, str2, true).bind();
            }
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getAccountRemovedReceivers(Account account, UserAccounts userAccounts) {
        Intent intent = new Intent("android.accounts.action.ACCOUNT_REMOVED");
        intent.setFlags(16777216);
        List queryBroadcastReceiversAsUser = this.mPackageManager.queryBroadcastReceiversAsUser(intent, 0, userAccounts.userId);
        ArrayList arrayList = new ArrayList();
        if (queryBroadcastReceiversAsUser == null) {
            return arrayList;
        }
        Iterator it = queryBroadcastReceiversAsUser.iterator();
        while (it.hasNext()) {
            String str = ((ResolveInfo) it.next()).activityInfo.applicationInfo.packageName;
            int intValue = resolveAccountVisibility(account, userAccounts, str).intValue();
            if (intValue == 1 || intValue == 2) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public final int getAccountVisibility(Account account, String str) {
        Objects.requireNonNull(account, "account cannot be null");
        Objects.requireNonNull(str, "packageName cannot be null");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type) && !isSystemUid(callingUid)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot get secrets for accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            if ("android:accounts:key_legacy_visible".equals(str)) {
                int accountVisibilityFromCache = getAccountVisibilityFromCache(account, userAccounts, str);
                if (accountVisibilityFromCache != 0) {
                    return accountVisibilityFromCache;
                }
                IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
                return 2;
            }
            if (!"android:accounts:key_legacy_not_visible".equals(str)) {
                if (canCallerAccessPackage(callingUid, userAccounts.userId, str)) {
                    return resolveAccountVisibility(account, userAccounts, str).intValue();
                }
                IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
                return 3;
            }
            int accountVisibilityFromCache2 = getAccountVisibilityFromCache(account, userAccounts, str);
            if (accountVisibilityFromCache2 != 0) {
                return accountVisibilityFromCache2;
            }
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            return 4;
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Account[] getAccounts(int i, String str) {
        int callingUid = Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str);
        List typesForCaller = getTypesForCaller(callingUid, i, true);
        if (((ArrayList) typesForCaller).isEmpty()) {
            return EMPTY_ACCOUNT_ARRAY;
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAccountsInternal(getUserAccounts(i), callingUid, str, typesForCaller, false);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Map getAccountsAndVisibilityForPackage(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        boolean isSameApp = UserHandle.isSameApp(callingUid, 1000);
        List typesForCaller = getTypesForCaller(callingUid, callingUserId, isSameApp);
        if ((str2 != null && !((ArrayList) typesForCaller).contains(str2)) || (str2 == null && !isSameApp)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "getAccountsAndVisibilityForPackage() called from unauthorized uid ", " with packageName=", str));
        }
        if (str2 != null) {
            typesForCaller = PortStatus_1_1$$ExternalSyntheticOutline0.m(str2);
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAccountsAndVisibilityForPackage(str, typesForCaller, Integer.valueOf(callingUid), getUserAccounts(callingUserId));
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Map getAccountsAndVisibilityForPackage(String str, List list, Integer num, UserAccounts userAccounts) {
        if (!canCallerAccessPackage(num.intValue(), userAccounts.userId, str)) {
            Log.w("AccountManagerService", "getAccountsAndVisibilityForPackage#Package not found " + str);
            return new LinkedHashMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    try {
                        Account[] accountArr = (Account[]) userAccounts.accountCache.get(str2);
                        if (accountArr != null) {
                            for (Account account : accountArr) {
                                linkedHashMap.put(account, resolveAccountVisibility(account, userAccounts, str));
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        return filterSharedAccounts(userAccounts, linkedHashMap, num.intValue(), str);
    }

    public final Account[] getAccountsAsUser(String str, int i, String str2) {
        this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str2);
        try {
            return getAccountsAsUserForPackage(str, i, str2, str2, -1, false);
        } catch (SQLiteCantOpenDatabaseException e) {
            Log.e("AccountManagerService", "Could not get accounts for user " + i, e);
            return new Account[0];
        }
    }

    public final Account[] getAccountsAsUserForPackage(String str, int i, String str2, String str3, int i2, boolean z) {
        int callingUid = Binder.getCallingUid();
        if (i != UserHandle.getCallingUserId() && callingUid != 1000 && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            throw new SecurityException("User " + UserHandle.getCallingUserId() + " trying to get account for " + i);
        }
        if (Log.isLoggable("AccountManagerService", 2)) {
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("getAccounts: accountType ", str, ", caller's uid ");
            m.append(Binder.getCallingUid());
            m.append(", pid ");
            m.append(Binder.getCallingPid());
            Log.v("AccountManagerService", m.toString());
        }
        List typesForCaller = getTypesForCaller(callingUid, UserHandle.getUserId(callingUid), false);
        if (i2 == -1 || (!UserHandle.isSameApp(callingUid, 1000) && (str == null || !((ArrayList) typesForCaller).contains(str)))) {
            str2 = str3;
            i2 = callingUid;
        }
        List typesForCaller2 = getTypesForCaller(i2, i, true);
        ArrayList arrayList = (ArrayList) typesForCaller2;
        if (arrayList.isEmpty() || !(str == null || arrayList.contains(str))) {
            return EMPTY_ACCOUNT_ARRAY;
        }
        if (arrayList.contains(str)) {
            typesForCaller2 = PortStatus_1_1$$ExternalSyntheticOutline0.m(str);
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAccountsInternal(getUserAccounts(i), i2, str2, typesForCaller2, z);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) {
        int callingUid = Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str2);
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "getAccounts: accountType " + str + ", response " + iAccountManagerResponse + ", features " + Arrays.toString(strArr) + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        }
        int callingUserId = UserHandle.getCallingUserId();
        if (!((ArrayList) getTypesForCaller(callingUid, callingUserId, true)).contains(str)) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("accounts", EMPTY_ACCOUNT_ARRAY);
            try {
                iAccountManagerResponse.onResult(bundle);
                return;
            } catch (RemoteException e) {
                Log.e("AccountManagerService", "Cannot respond to caller do to exception.", e);
                return;
            }
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (strArr != null && strArr.length != 0) {
                new GetAccountsByTypeAndFeatureSession(userAccounts, iAccountManagerResponse, str, strArr, callingUid, str2, false).bind();
                return;
            }
            Account[] accountsFromCache = getAccountsFromCache(userAccounts, str, callingUid, str2, false);
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArray("accounts", accountsFromCache);
            onResult(iAccountManagerResponse, bundle2);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Account[] getAccountsByTypeForPackage(String str, String str2, String str3) {
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        this.mAppOpsManager.checkPackage(callingUid, str3);
        try {
            int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str2, callingUserId);
            return (UserHandle.isSameApp(callingUid, 1000) || str == null || isAccountManagedByCaller(callingUid, callingUserId, str)) ? (UserHandle.isSameApp(callingUid, 1000) || str != null) ? getAccountsAsUserForPackage(str, callingUserId, str2, str3, packageUidAsUser, true) : getAccountsAsUserForPackage(str, callingUserId, str2, str3, packageUidAsUser, false) : EMPTY_ACCOUNT_ARRAY;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("AccountManagerService", "Couldn't determine the packageUid for " + str2 + e);
            return EMPTY_ACCOUNT_ARRAY;
        }
    }

    public final Account[] getAccountsForPackage(String str, int i, String str2) {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.isSameApp(callingUid, 1000)) {
            return getAccountsAsUserForPackage(null, UserHandle.getCallingUserId(), str, str2, i, true);
        }
        throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, i, "getAccountsForPackage() called from unauthorized uid ", " with uid="));
    }

    public final AccountAndUser[] getAccountsForSystem(int[] iArr) {
        ArrayList newArrayList = Lists.newArrayList();
        for (int i : iArr) {
            for (Account account : getAccountsFromCache(getUserAccounts(i), null, Binder.getCallingUid(), "android", false)) {
                newArrayList.add(new AccountAndUser(account, i));
            }
        }
        return (AccountAndUser[]) newArrayList.toArray(new AccountAndUser[newArrayList.size()]);
    }

    public final Account[] getAccountsFromCache(UserAccounts userAccounts, String str, int i, String str2, boolean z) {
        Account[] accountArr;
        Account[] accountArr2;
        Preconditions.checkState(!Thread.holdsLock(userAccounts.cacheLock), "Method should not be called with cacheLock");
        if (str != null) {
            if (this.mUseAccountDb && isAccountsCacheEmpty(userAccounts)) {
                accountArr2 = queryAccountsFromDb(str, userAccounts);
                if (accountArr2.length == 0) {
                    return EMPTY_ACCOUNT_ARRAY;
                }
            } else {
                synchronized (userAccounts.cacheLock) {
                    accountArr2 = (Account[]) userAccounts.accountCache.get(str);
                }
            }
            return accountArr2 == null ? EMPTY_ACCOUNT_ARRAY : filterAccounts(userAccounts, (Account[]) Arrays.copyOf(accountArr2, accountArr2.length), i, str2, z);
        }
        if (this.mUseAccountDb && isAccountsCacheEmpty(userAccounts)) {
            Account[] queryAccountsFromDb = queryAccountsFromDb(str, userAccounts);
            if (queryAccountsFromDb.length == 0) {
                return EMPTY_ACCOUNT_ARRAY;
            }
            accountArr = queryAccountsFromDb;
        } else {
            synchronized (userAccounts.cacheLock) {
                try {
                    Iterator it = userAccounts.accountCache.values().iterator();
                    int i2 = 0;
                    while (it.hasNext()) {
                        i2 += ((Account[]) it.next()).length;
                    }
                    if (i2 == 0) {
                        return EMPTY_ACCOUNT_ARRAY;
                    }
                    Account[] accountArr3 = new Account[i2];
                    int i3 = 0;
                    for (Account[] accountArr4 : userAccounts.accountCache.values()) {
                        System.arraycopy(accountArr4, 0, accountArr3, i3, accountArr4.length);
                        i3 += accountArr4.length;
                    }
                    accountArr = accountArr3;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return filterAccounts(userAccounts, accountArr, i, str2, z);
    }

    public final Account[] getAccountsInternal(UserAccounts userAccounts, int i, String str, List list, boolean z) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Account[] accountsFromCache = getAccountsFromCache(userAccounts, (String) it.next(), i, str, z);
            if (accountsFromCache != null) {
                arrayList.addAll(Arrays.asList(accountsFromCache));
            }
        }
        int size = arrayList.size();
        Account[] accountArr = new Account[size];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            accountArr[i2] = (Account) arrayList.get(i2);
        }
        if (this.mSyncDeCeAccountsExecuted && size == 0 && i != 1000) {
            this.mSyncDeCeAccountsExecuted = false;
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Return empty accounts list after sync db. , caller : ", str, ", userId : ");
            int i3 = userAccounts.userId;
            m.append(i3);
            m.append(", userUnlocked : ");
            m.append(isLocalUnlockedUser(i3));
            m.append(", isCacheEmpty : ");
            m.append(isAccountsCacheEmpty(userAccounts));
            saveAccountEventRecord(m.toString(), true);
            AccountAuthenticatorCache accountAuthenticatorCache = this.mAuthenticatorCache;
            if (accountAuthenticatorCache != null) {
                Iterator it2 = accountAuthenticatorCache.getAllServices(i3).iterator();
                while (it2.hasNext()) {
                    saveAccountEventRecord("dumpAuthenticatorCache : " + ((RegisteredServicesCache.ServiceInfo) it2.next()).componentName.toString(), false);
                }
            }
        }
        return accountArr;
    }

    public final Account[] getAccountsOrEmptyArray(int i, String str) {
        try {
            return getAccountsAsUser(null, i, str);
        } catch (SQLiteCantOpenDatabaseException e) {
            Log.w("AccountManagerService", "Could not get accounts for user " + i, e);
            return new Account[0];
        }
    }

    public final AccountAndUser[] getAllAccountsForSystemProcess() {
        List aliveUsers = getUserManager().getAliveUsers();
        int size = aliveUsers.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((UserInfo) aliveUsers.get(i)).id;
        }
        return getAccountsForSystem(iArr);
    }

    public final void getAuthToken(IAccountManagerResponse iAccountManagerResponse, final Account account, final String str, final boolean z, boolean z2, final Bundle bundle) {
        TokenCache.Value readCachedTokenInternal;
        String readAuthTokenInternal;
        Bundle.setDefusable(bundle, true);
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "getAuthToken: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", notifyOnAuthFailure " + z + ", expectActivityLaunch " + z2 + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        try {
            if (account == null) {
                Slog.w("AccountManagerService", "getAuthToken called with null account");
                iAccountManagerResponse.onError(7, "account is null");
                return;
            }
            if (str == null) {
                Slog.w("AccountManagerService", "getAuthToken called with null authTokenType");
                iAccountManagerResponse.onError(7, "authTokenType is null");
                return;
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                final UserAccounts userAccounts = getUserAccounts(callingUserId);
                RegisteredServicesCache.ServiceInfo serviceInfo = this.mAuthenticatorCache.getServiceInfo(AuthenticatorDescription.newKey(account.type), userAccounts.userId);
                boolean z3 = serviceInfo != null && ((AuthenticatorDescription) serviceInfo.type).customTokens;
                final int callingUid = Binder.getCallingUid();
                final boolean z4 = z3 || permissionIsGranted(account, str, callingUid, callingUserId);
                final String string = bundle.getString("androidPackageName");
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String[] packagesForUid = this.mPackageManager.getPackagesForUid(callingUid);
                    if (string == null || packagesForUid == null || !ArrayUtils.contains(packagesForUid, string)) {
                        throw new SecurityException(AccountManagerService$$ExternalSyntheticOutline0.m(callingUid, "Uid ", " is attempting to illegally masquerade as package ", string, "!"));
                    }
                    bundle.putInt("callerUid", callingUid);
                    bundle.putInt("callerPid", Binder.getCallingPid());
                    if (z) {
                        bundle.putBoolean("notifyOnAuthFailure", true);
                    }
                    long clearCallingIdentity2 = IAccountManager.Stub.clearCallingIdentity();
                    try {
                        final byte[] calculatePackageSignatureDigest = calculatePackageSignatureDigest(callingUserId, string);
                        if (!z3 && z4 && (readAuthTokenInternal = readAuthTokenInternal(account, userAccounts, str)) != null) {
                            logGetAuthTokenMetrics(string, account.type);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("authtoken", readAuthTokenInternal);
                            bundle2.putString("authAccount", account.name);
                            bundle2.putString("accountType", account.type);
                            onResult(iAccountManagerResponse, bundle2);
                            return;
                        }
                        if (!z3 || (readCachedTokenInternal = readCachedTokenInternal(userAccounts, account, str, string, calculatePackageSignatureDigest)) == null) {
                            final boolean z5 = z3;
                            new Session(userAccounts, iAccountManagerResponse, account.type, z2, account.name) { // from class: com.android.server.accounts.AccountManagerService.8
                                @Override // com.android.server.accounts.AccountManagerService.Session
                                public final void onResult(Bundle bundle3) {
                                    Bundle.setDefusable(bundle3, true);
                                    if (bundle3 != null) {
                                        if (bundle3.containsKey("authTokenLabelKey")) {
                                            Intent newGrantCredentialsPermissionIntent = AccountManagerService.this.newGrantCredentialsPermissionIntent(account, null, callingUid, new AccountAuthenticatorResponse((IAccountAuthenticatorResponse) this), str, true);
                                            this.mCanStartAccountManagerActivity = true;
                                            Bundle bundle4 = new Bundle();
                                            bundle4.putParcelable(KnoxCustomManagerService.INTENT, newGrantCredentialsPermissionIntent);
                                            onResult(bundle4);
                                            return;
                                        }
                                        String string2 = bundle3.getString("authtoken");
                                        if (string2 != null) {
                                            String string3 = bundle3.getString("authAccount");
                                            String string4 = bundle3.getString("accountType");
                                            if (TextUtils.isEmpty(string4) || TextUtils.isEmpty(string3)) {
                                                onError(5, "the type and name should not be empty");
                                                return;
                                            }
                                            Account account2 = new Account(string3, string4);
                                            if (!z5) {
                                                AccountManagerService.this.saveAuthTokenToDatabase(this.mAccounts, account2, str, string2);
                                            }
                                            long j = bundle3.getLong("android.accounts.expiry", 0L);
                                            if (z5 && j > System.currentTimeMillis()) {
                                                AccountManagerService accountManagerService = AccountManagerService.this;
                                                UserAccounts userAccounts2 = this.mAccounts;
                                                Account account3 = account;
                                                String str2 = string;
                                                byte[] bArr = calculatePackageSignatureDigest;
                                                String str3 = str;
                                                accountManagerService.getClass();
                                                if (account3 != null && str3 != null && str2 != null && bArr != null) {
                                                    accountManagerService.cancelNotification(AccountManagerService.getSigninRequiredNotificationId(account3, userAccounts2), userAccounts2);
                                                    synchronized (userAccounts2.cacheLock) {
                                                        userAccounts2.accountTokenCaches.put(account3, string2, str3, str2, bArr, j);
                                                    }
                                                }
                                            }
                                        }
                                        Intent intent = (Intent) bundle3.getParcelable(KnoxCustomManagerService.INTENT, Intent.class);
                                        if (intent != null && z && !z5) {
                                            if (!checkKeyIntent(Binder.getCallingUid(), bundle3)) {
                                                onError(5, "invalid intent in bundle returned");
                                                return;
                                            }
                                            AccountManagerService.m139$$Nest$mdoNotification(AccountManagerService.this, this.mAccounts, account, bundle3.getString("authFailedMessage"), intent, "android", userAccounts.userId);
                                        }
                                    }
                                    super.onResult(bundle3);
                                }

                                @Override // com.android.server.accounts.AccountManagerService.Session
                                public final void run() {
                                    if (!z4) {
                                        this.mAuthenticator.getAuthTokenLabel(this, str);
                                        return;
                                    }
                                    this.mAuthenticator.getAuthToken(this, account, str, bundle);
                                    AccountManagerService accountManagerService = AccountManagerService.this;
                                    String str2 = string;
                                    String str3 = account.type;
                                    accountManagerService.getClass();
                                    AccountManagerService.logGetAuthTokenMetrics(str2, str3);
                                }

                                @Override // com.android.server.accounts.AccountManagerService.Session
                                public final String toDebugString(long j) {
                                    Bundle bundle3 = bundle;
                                    if (bundle3 != null) {
                                        bundle3.keySet();
                                    }
                                    return super.toDebugString(j) + ", getAuthToken, " + account.toSafeString() + ", authTokenType " + str + ", loginOptions " + bundle + ", notifyOnAuthFailure " + z;
                                }
                            }.bind();
                            return;
                        }
                        logGetAuthTokenMetrics(string, account.type);
                        if (Log.isLoggable("AccountManagerService", 2)) {
                            Log.v("AccountManagerService", "getAuthToken: cache hit ofr custom token authenticator.");
                        }
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("authtoken", readCachedTokenInternal.token);
                        bundle3.putLong("android.accounts.expiry", readCachedTokenInternal.expiryEpochMillis);
                        bundle3.putString("authAccount", account.name);
                        bundle3.putString("accountType", account.type);
                        onResult(iAccountManagerResponse, bundle3);
                    } finally {
                        IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity2);
                    }
                } finally {
                }
            } finally {
            }
        } catch (RemoteException e) {
            AccountManagerService$$ExternalSyntheticOutline0.m("Failed to report error back to the client.", e, "AccountManagerService");
        }
    }

    public final void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String str, String str2) {
        Preconditions.checkArgument(str != null, "accountType cannot be null");
        Preconditions.checkArgument(str2 != null, "authTokenType cannot be null");
        int callingUid = IAccountManager.Stub.getCallingUid();
        IAccountManager.Stub.clearCallingIdentity();
        if (UserHandle.getAppId(callingUid) != 1000) {
            throw new SecurityException("can only call from system");
        }
        int userId = UserHandle.getUserId(callingUid);
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new AnonymousClass7(this, getUserAccounts(userId), iAccountManagerResponse, str, str, str2).bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final AuthenticatorDescription[] getAuthenticatorTypes(int i) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, callingUid, "getAuthenticatorTypes: for user id ", " caller's uid ", ", pid ");
            m.append(Binder.getCallingPid());
            Log.v("AccountManagerService", m.toString());
        }
        if (isCrossUser(callingUid, i) && ((UserHandle.getCallingUserId() != 0 || !SemPersonaManager.isKnoxId(i)) && (i != 0 || !SemPersonaManager.isKnoxId(UserHandle.getCallingUserId())))) {
            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(UserHandle.getCallingUserId(), i, "User ", " tying to get authenticator types for "));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return getAuthenticatorTypesInternal(i, callingUid);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final AuthenticatorDescription[] getAuthenticatorTypesInternal(int i, int i2) {
        this.mAuthenticatorCache.updateServices(i);
        Collection<RegisteredServicesCache.ServiceInfo> allServices = this.mAuthenticatorCache.getAllServices(i);
        ArrayList arrayList = new ArrayList(allServices.size());
        for (RegisteredServicesCache.ServiceInfo serviceInfo : allServices) {
            if (canCallerAccessPackage(i2, i, ((AuthenticatorDescription) serviceInfo.type).packageName)) {
                arrayList.add((AuthenticatorDescription) serviceInfo.type);
            }
        }
        return (AuthenticatorDescription[]) arrayList.toArray(new AuthenticatorDescription[arrayList.size()]);
    }

    public final String getPackageNameForUid(int i) {
        int i2;
        String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
        if (ArrayUtils.isEmpty(packagesForUid)) {
            return null;
        }
        String str = packagesForUid[0];
        if (packagesForUid.length == 1) {
            return str;
        }
        int i3 = Integer.MAX_VALUE;
        for (String str2 : packagesForUid) {
            try {
                ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str2, 0);
                if (applicationInfo != null && (i2 = applicationInfo.targetSdkVersion) < i3) {
                    str = str2;
                    i3 = i2;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return str;
    }

    public final Map getPackagesAndVisibilityForAccount(Account account) {
        Map packagesAndVisibilityForAccountLocked;
        Objects.requireNonNull(account, "account cannot be null");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type) && !isSystemUid(callingUid)) {
            throw new SecurityException(String.format("uid %s cannot get secrets for account %s", Integer.valueOf(callingUid), account));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            synchronized (userAccounts.dbLock) {
                synchronized (userAccounts.cacheLock) {
                    packagesAndVisibilityForAccountLocked = getPackagesAndVisibilityForAccountLocked(account, userAccounts);
                }
            }
            return packagesAndVisibilityForAccountLocked;
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getPassword(Account account) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "getPassword: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot get secrets for accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return readPasswordInternal(account, getUserAccounts(callingUserId));
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getPreviousName(Account account) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "getPreviousName: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return readPreviousNameInternal(account, getUserAccounts(callingUserId));
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Map getRequestingPackages(Account account, UserAccounts userAccounts) {
        HashSet hashSet = new HashSet();
        synchronized (userAccounts.mReceiversForType) {
            try {
                String[] strArr = {account.type, null};
                for (int i = 0; i < 2; i++) {
                    Map map = (Map) ((HashMap) userAccounts.mReceiversForType).get(strArr[i]);
                    if (map != null) {
                        hashSet.addAll(map.keySet());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        HashMap hashMap = new HashMap();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            hashMap.put(str, resolveAccountVisibility(account, userAccounts, str));
        }
        return hashMap;
    }

    public final Account[] getSharedAccountsAsUser(int i) {
        Account[] accountArr;
        UserAccounts userAccounts = getUserAccounts(handleIncomingUser(i));
        synchronized (userAccounts.dbLock) {
            ArrayList arrayList = (ArrayList) userAccounts.accountsDb.getSharedAccounts();
            accountArr = new Account[arrayList.size()];
            arrayList.toArray(accountArr);
        }
        return accountArr;
    }

    public final List getTypesForCaller(int i, int i2, boolean z) {
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Collection<RegisteredServicesCache.ServiceInfo> allServices = this.mAuthenticatorCache.getAllServices(i2);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            for (RegisteredServicesCache.ServiceInfo serviceInfo : allServices) {
                if (z || packageManagerInternal.hasSignatureCapability(serviceInfo.uid, i)) {
                    arrayList.add(((AuthenticatorDescription) serviceInfo.type).type);
                }
            }
            return arrayList;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final UserAccounts getUserAccounts(int i) {
        try {
            return getUserAccountsNotChecked(i);
        } catch (RuntimeException e) {
            if (!this.mPackageManager.hasSystemFeature("android.hardware.type.automotive")) {
                throw e;
            }
            Slog.wtf("AccountManagerService", "Removing user " + i + " due to exception (" + e + ") reading its account database");
            if (i == ActivityManager.getCurrentUser() && i != 0) {
                Slog.i("AccountManagerService", "Switching to system user first");
                try {
                    ActivityManager.getService().switchUser(0);
                } catch (RemoteException e2) {
                    Slog.e("AccountManagerService", "Could not switch to 0: " + e2);
                }
            }
            if (this.getUserManager().removeUserEvenWhenDisallowed(i)) {
                throw e;
            }
            NandswapManager$$ExternalSyntheticOutline0.m(i, "could not remove user ", "AccountManagerService");
            throw e;
        }
    }

    public final UserAccounts getUserAccountsNotChecked(int i) {
        UserAccounts userAccounts;
        boolean z;
        synchronized (this.mUsers) {
            try {
                userAccounts = (UserAccounts) this.mUsers.get(i);
                if (userAccounts == null) {
                    this.mInjector.getClass();
                    File file = new File(Injector.getPreNDatabaseName(i));
                    this.mInjector.getClass();
                    UserAccounts userAccounts2 = new UserAccounts(this.mContext, i, file, new File(new File(Environment.getDataSystemDeDirectory(i), "accounts_de.db").getPath()));
                    this.mUsers.append(i, userAccounts2);
                    purgeOldGrants(userAccounts2);
                    AccountManager.invalidateLocalAccountsDataCaches();
                    z = true;
                    userAccounts = userAccounts2;
                } else {
                    z = false;
                }
                if (!userAccounts.accountsDb.mDeDatabase.mCeAttached && this.mLocalUnlockedUsers.get(i)) {
                    Log.i("AccountManagerService", "User " + i + " is unlocked - opening CE database");
                    synchronized (userAccounts.dbLock) {
                        synchronized (userAccounts.cacheLock) {
                            this.mInjector.getClass();
                            userAccounts.accountsDb.attachCeDatabase(new File(new File(Environment.getDataSystemCeDirectory(i), "accounts_ce.db").getPath()));
                        }
                    }
                    syncDeCeAccountsLocked(userAccounts);
                }
                if (z) {
                    validateAccountsInternal(userAccounts, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return userAccounts;
    }

    public final String getUserData(Account account, String str) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", String.format("getUserData( account: %s, key: %s, callerUid: %s, pid: %s", account, str, Integer.valueOf(callingUid), Integer.valueOf(Binder.getCallingPid())));
        }
        Objects.requireNonNull(account, "account cannot be null");
        Objects.requireNonNull(str, "key cannot be null");
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot get user data for accounts of type: ", account.type));
        }
        if (!isLocalUnlockedUser(callingUserId)) {
            Log.w("AccountManagerService", "User " + callingUserId + " data is locked. callingUid " + callingUid);
            return null;
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (accountExistsCache(account, userAccounts)) {
                return readUserDataInternal(account, userAccounts, str);
            }
            return null;
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    public final void grantAppPermission(Account account, String str, int i) {
        if (account == null || str == null) {
            Log.e("AccountManagerService", "grantAppPermission: called with invalid arguments", new Exception());
            return;
        }
        UserAccounts userAccounts = getUserAccounts(UserHandle.getUserId(i));
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId >= 0) {
                        SQLiteDatabase writableDatabase = userAccounts.accountsDb.mDeDatabase.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("accounts_id", Long.valueOf(findDeAccountId));
                        contentValues.put("auth_token_type", str);
                        contentValues.put("uid", Integer.valueOf(i));
                        writableDatabase.insert("grants", "accounts_id", contentValues);
                    }
                    cancelNotification(getCredentialPermissionNotificationId(i, account, userAccounts, str), userAccounts);
                    cancelAccountAccessRequestNotificationIfNeeded(account, i, true, userAccounts);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Iterator it = this.mAppPermissionChangeListeners.iterator();
        while (it.hasNext()) {
            this.mHandler.post(new AccountManagerService$$ExternalSyntheticLambda5((AccountManagerInternal.OnAppPermissionChangeListener) it.next(), account, i, 0));
        }
    }

    public final void handleGetAccountsResult(IAccountManagerResponse iAccountManagerResponse, Account[] accountArr, String str) {
        if (accountArr.length >= 1 && (accountArr.length > 1 || resolveAccountVisibility(accountArr[0], getUserAccounts(UserHandle.getCallingUserId()), str).intValue() == 4)) {
            Intent intent = new Intent(this.mContext, (Class<?>) ChooseAccountActivity.class);
            intent.putExtra("accounts", accountArr);
            intent.putExtra("accountManagerResponse", (Parcelable) new AccountManagerResponse(iAccountManagerResponse));
            intent.putExtra("androidPackageName", str);
            this.mContext.startActivityAsUser(intent, UserHandle.of(UserHandle.getCallingUserId()));
            return;
        }
        if (accountArr.length != 1) {
            onResult(iAccountManagerResponse, new Bundle());
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("authAccount", accountArr[0].name);
        bundle.putString("accountType", accountArr[0].type);
        onResult(iAccountManagerResponse, bundle);
    }

    public final boolean hasAccountAccess(Account account, String str, int i) {
        if (str == null && (str = getPackageNameForUid(i)) == null) {
            return false;
        }
        if (permissionIsGranted(account, null, i, UserHandle.getUserId(i))) {
            return true;
        }
        int intValue = resolveAccountVisibility(account, getUserAccounts(UserHandle.getUserId(i)), str).intValue();
        return intValue == 1 || intValue == 2;
    }

    public final boolean hasAccountAccess(Account account, String str, UserHandle userHandle) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            throw new SecurityException("Can be called only by system UID");
        }
        Objects.requireNonNull(account, "account cannot be null");
        Objects.requireNonNull(str, "packageName cannot be null");
        Objects.requireNonNull(userHandle, "userHandle cannot be null");
        int identifier = userHandle.getIdentifier();
        Preconditions.checkArgumentInRange(identifier, 0, Integer.MAX_VALUE, "user must be concrete");
        try {
            return hasAccountAccess(account, str, this.mPackageManager.getPackageUidAsUser(str, identifier));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("AccountManagerService", "hasAccountAccess#Package not found " + e.getMessage());
            return false;
        }
    }

    public final void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr, int i, String str) {
        int callingUid = Binder.getCallingUid();
        this.mAppOpsManager.checkPackage(callingUid, str);
        if (Log.isLoggable("AccountManagerService", 2)) {
            StringBuilder sb = new StringBuilder("hasFeatures: ");
            sb.append(account);
            sb.append(", response ");
            sb.append(iAccountManagerResponse);
            sb.append(", features ");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, Arrays.toString(strArr), ", caller's uid ", ", userId ", sb);
            sb.append(i);
            sb.append(", pid ");
            sb.append(Binder.getCallingPid());
            Log.v("AccountManagerService", sb.toString());
        }
        Preconditions.checkArgument(account != null, "account cannot be null");
        Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        Preconditions.checkArgument(strArr != null, "features cannot be null");
        if (i != UserHandle.getCallingUserId() && callingUid != 1000 && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            throw new SecurityException("User " + UserHandle.getCallingUserId() + " trying to check account features for " + i);
        }
        String str2 = account.type;
        if (!(str2 != null ? ((ArrayList) getTypesForCaller(callingUid, i, true)).contains(str2) : false)) {
            String m = AccountManagerService$$ExternalSyntheticOutline0.m(callingUid, "caller uid ", " cannot access ", str2, " accounts");
            Log.w("AccountManagerService", "  ".concat(m));
            throw new SecurityException(m);
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new AnonymousClass12(this, getUserAccounts(i), iAccountManagerResponse, account, strArr).bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void installNotification(NotificationId notificationId, Notification notification, String str, int i) {
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            this.mInjector.getClass();
            try {
                NotificationManager.getService().enqueueNotificationWithTag(str, "android", notificationId.mTag, notificationId.mId, notification, i);
            } catch (RemoteException unused) {
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void invalidateAuthToken(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        Objects.requireNonNull(str, "accountType cannot be null");
        Objects.requireNonNull(str2, "authToken cannot be null");
        if (Log.isLoggable("AccountManagerService", 2)) {
            StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(callingUid, "invalidateAuthToken: accountType ", str, ", caller's uid ", ", pid ");
            m.append(Binder.getCallingPid());
            Log.v("AccountManagerService", m.toString());
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            synchronized (userAccounts.dbLock) {
                userAccounts.accountsDb.beginTransaction();
                try {
                    List invalidateAuthTokenLocked = invalidateAuthTokenLocked(userAccounts, str, str2);
                    userAccounts.accountsDb.setTransactionSuccessful();
                    userAccounts.accountsDb.endTransaction();
                    synchronized (userAccounts.cacheLock) {
                        try {
                            Iterator it = ((ArrayList) invalidateAuthTokenLocked).iterator();
                            while (it.hasNext()) {
                                Pair pair = (Pair) it.next();
                                writeAuthTokenIntoCacheLocked(userAccounts, (Account) pair.first, (String) pair.second, null);
                            }
                            TokenCache.TokenLruCache.Evictor evictor = (TokenCache.TokenLruCache.Evictor) userAccounts.accountTokenCaches.mCachedTokens.mTokenEvictors.get(new Pair(str, str2));
                            if (evictor != null) {
                                evictor.evict();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    userAccounts.accountsDb.endTransaction();
                    throw th2;
                }
            }
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAccountManagedByCaller(int i, int i2, String str) {
        if (str == null) {
            return false;
        }
        return ((ArrayList) getTypesForCaller(i, i2, false)).contains(str);
    }

    public final boolean isAccountsCacheEmpty(UserAccounts userAccounts) {
        HashMap hashMap;
        synchronized (userAccounts.cacheLock) {
            hashMap = userAccounts.accountCache;
        }
        boolean z = true;
        if (hashMap != null && hashMap.size() != 0) {
            Iterator it = hashMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((Account[]) ((Map.Entry) it.next()).getValue()).length != 0) {
                    z = false;
                    if (this.mUseAccountDb) {
                        saveAccountEventRecord("Accounts cache exists. There is no need to use db", false);
                        this.mUseAccountDb = false;
                    }
                }
            }
        }
        return z;
    }

    public final void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String str) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "isCredentialsUpdateSuggested: " + account + ", response " + iAccountManagerResponse + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("status token is empty");
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new AnonymousClass15(this, getUserAccounts(callingUserId), iAccountManagerResponse, account.type, account.name, account, str).bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isCrossUser(int i, int i2) {
        return ((SemDualAppManager.isDualAppId(UserHandle.getUserId(i)) && i2 == 0) || (SemDualAppManager.isDualAppId(i2) && UserHandle.getUserId(i) == 0) || i2 == UserHandle.getCallingUserId() || i == 1000 || this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0) ? false : true;
    }

    public final boolean isFirstAccountRemovalDisabled(Account account) {
        if (UserHandle.getCallingUserId() != 0 || this.mContext.getResources().getBoolean(R.bool.config_carrier_volte_tty_supported) || Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "allow_primary_gaia_account_removal_for_tests", 0, 0) != 0) {
            return false;
        }
        String string = this.mContext.getResources().getString(R.string.config_wwan_data_service_class);
        if (string.isEmpty() || !string.equals(account.type)) {
            return false;
        }
        Account[] accountsFromCache = getAccountsFromCache(getUserAccounts(0), string, 1000, "android", false);
        return accountsFromCache.length > 0 && accountsFromCache[0].equals(account);
    }

    public final boolean isLocalUnlockedUser(int i) {
        boolean z;
        synchronized (this.mUsers) {
            z = this.mLocalUnlockedUsers.get(i);
        }
        return z;
    }

    public final boolean isPermittedForPackage(String str, int i, String... strArr) {
        int permissionToOpCode;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int packageUidAsUser = this.mPackageManager.getPackageUidAsUser(str, i);
            IPackageManager packageManager = ActivityThread.getPackageManager();
            for (String str2 : strArr) {
                if (packageManager.checkPermission(str2, str, i) == 0 && ((permissionToOpCode = AppOpsManager.permissionToOpCode(str2)) == -1 || this.mAppOpsManager.checkOpNoThrow(permissionToOpCode, packageUidAsUser, str) == 0)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException | RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    public final boolean isSystemUid(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid != null) {
                for (String str : packagesForUid) {
                    try {
                        PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 0);
                        if (packageInfo != null && (packageInfo.applicationInfo.flags & 1) != 0) {
                            return true;
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.w("AccountManagerService", "Could not find package [" + str + "]", e);
                    }
                }
            } else {
                Log.w("AccountManagerService", "No known packages with uid " + i);
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void logRecord(String str, String str2, long j, UserAccounts userAccounts, int i) {
        long j2;
        long j3;
        AccountsDb accountsDb = userAccounts.accountsDb;
        if (accountsDb.mDebugDbInsertionPoint == -1) {
            try {
                SQLiteDatabase readableDatabase = accountsDb.mDeDatabase.getReadableDatabase();
                int longForQuery = (int) DatabaseUtils.longForQuery(readableDatabase, "SELECT COUNT(*) FROM debug_table", null);
                j2 = longForQuery < 64 ? longForQuery : DatabaseUtils.longForQuery(readableDatabase, "SELECT primary_key FROM debug_table ORDER BY time,primary_key LIMIT 1", null);
            } catch (SQLiteException e) {
                Log.e("AccountsDb", "Failed to open debug table" + e);
                j2 = -1L;
            }
            accountsDb.mDebugDbInsertionPoint = j2;
            j3 = accountsDb.mDebugDbInsertionPoint;
        } else {
            accountsDb.mDebugDbInsertionPoint = (accountsDb.mDebugDbInsertionPoint + 1) % 64;
            j3 = accountsDb.mDebugDbInsertionPoint;
        }
        long j4 = j3;
        if (j4 != -1) {
            this.mHandler.post(new Runnable(str, str2, j, userAccounts, i, j4) { // from class: com.android.server.accounts.AccountManagerService.1LogRecordTask
                public final long accountId;
                public final String action;
                public final int callingUid;
                public final String tableName;
                public final UserAccounts userAccount;
                public final long userDebugDbInsertionPoint;

                {
                    this.action = str;
                    this.tableName = str2;
                    this.accountId = j;
                    this.userAccount = userAccounts;
                    this.callingUid = i;
                    this.userDebugDbInsertionPoint = j4;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (this.userAccount.accountsDb.mDebugStatementLock) {
                        try {
                            SQLiteStatement statementForLogging = this.userAccount.accountsDb.getStatementForLogging();
                            if (statementForLogging == null) {
                                return;
                            }
                            statementForLogging.bindLong(1, this.accountId);
                            statementForLogging.bindString(2, this.action);
                            statementForLogging.bindString(3, AccountManagerService.this.mDateFormat.format(new Date()));
                            statementForLogging.bindLong(4, this.callingUid);
                            statementForLogging.bindString(5, this.tableName);
                            statementForLogging.bindLong(6, this.userDebugDbInsertionPoint);
                            try {
                                try {
                                    statementForLogging.execute();
                                } finally {
                                    statementForLogging.clearBindings();
                                }
                            } catch (SQLiteFullException | IllegalStateException e2) {
                                Slog.w("AccountManagerService", "Failed to insert a log record. accountId=" + this.accountId + " action=" + this.action + " tableName=" + this.tableName + " Error: " + e2);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }
    }

    public final Intent newGrantCredentialsPermissionIntent(Account account, String str, int i, AccountAuthenticatorResponse accountAuthenticatorResponse, String str2, boolean z) {
        Intent intent = new Intent(this.mContext, (Class<?>) GrantCredentialsPermissionActivity.class);
        if (z) {
            intent.setFlags(268435456);
        }
        UserAccounts userAccounts = getUserAccounts(UserHandle.getUserId(i));
        StringBuilder sb = new StringBuilder();
        sb.append(getCredentialPermissionNotificationId(i, account, userAccounts, str2).mTag);
        if (str == null) {
            str = "";
        }
        sb.append(str);
        intent.addCategory(sb.toString());
        intent.putExtra("account", account);
        intent.putExtra("authTokenType", str2);
        intent.putExtra("response", accountAuthenticatorResponse);
        intent.putExtra("uid", i);
        return intent;
    }

    public final void notifyPackage(String str, UserAccounts userAccounts) {
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("notifying package=", str, " for userId=");
        m.append(userAccounts.userId);
        m.append(", sending broadcast of android.accounts.action.VISIBLE_ACCOUNTS_CHANGED");
        Log.i("AccountManagerService", m.toString());
        Intent intent = new Intent("android.accounts.action.VISIBLE_ACCOUNTS_CHANGED");
        intent.setPackage(str);
        intent.setFlags(1073741824);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(userAccounts.userId));
    }

    public final void onAccountAccessed(String str) {
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) == 1000) {
            return;
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            for (Account account : getAccounts(callingUserId, this.mContext.getOpPackageName())) {
                if (Objects.equals(account.getAccessId(), str) && !hasAccountAccess(account, (String) null, callingUid)) {
                    updateAppPermission(account, "com.android.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE", callingUid, true);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onServiceChanged(Object obj, int i, boolean z) {
        if (getUserManager().getUserInfo(i) == null) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(i, "onServiceChanged: ignore removed user ", "AccountManagerService");
        } else {
            validateAccountsInternal(getUserAccounts(i), false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new AccountManagerServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException) && !(e instanceof IllegalArgumentException)) {
                Slog.wtf("AccountManagerService", "Account Manager Crash", e);
            }
            throw e;
        }
    }

    public final void onUnlockUser(final int i) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "onUserUnlocked " + i);
        }
        synchronized (this.mUsers) {
            this.mLocalUnlockedUsers.put(i, true);
        }
        if (i < 1) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AccountManagerService accountManagerService = AccountManagerService.this;
                int i2 = i;
                Account[] sharedAccountsAsUser = accountManagerService.getSharedAccountsAsUser(i2);
                if (sharedAccountsAsUser.length == 0) {
                    return;
                }
                Account[] accountsAsUser = accountManagerService.getAccountsAsUser(null, i2, accountManagerService.mContext.getOpPackageName());
                for (Account account : sharedAccountsAsUser) {
                    if (!ArrayUtils.contains(accountsAsUser, account)) {
                        accountManagerService.copyAccountToUser(null, account, 0, i2);
                    }
                }
            }
        });
    }

    public void onUserUnlocked(Intent intent) {
        onUnlockUser(intent.getIntExtra("android.intent.extra.user_handle", -1));
    }

    public final String peekAuthToken(Account account, String str) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "peekAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        Objects.requireNonNull(account, "account cannot be null");
        Objects.requireNonNull(str, "authTokenType cannot be null");
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot peek the authtokens associated with accounts of type: ", account.type));
        }
        if (isLocalUnlockedUser(callingUserId)) {
            long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
            try {
                return readAuthTokenInternal(account, getUserAccounts(callingUserId), str);
            } finally {
                IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Log.w("AccountManagerService", "Authtoken not available - user " + callingUserId + " data is locked. callingUid " + callingUid);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x018a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean permissionIsGranted(android.accounts.Account r17, java.lang.String r18, int r19, int r20) {
        /*
            Method dump skipped, instructions count: 480
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accounts.AccountManagerService.permissionIsGranted(android.accounts.Account, java.lang.String, int, int):boolean");
    }

    public final void purgeOldGrants(UserAccounts userAccounts) {
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    try {
                        Iterator it = ((ArrayList) userAccounts.accountsDb.findAllUidGrants()).iterator();
                        while (it.hasNext()) {
                            int intValue = ((Integer) it.next()).intValue();
                            if (this.mPackageManager.getPackagesForUid(intValue) == null) {
                                Log.d("AccountManagerService", "deleting grants for UID " + intValue + " because its package is no longer installed");
                                userAccounts.accountsDb.mDeDatabase.getWritableDatabase().delete("grants", "uid=?", new String[]{Integer.toString(intValue)});
                            }
                        }
                    } catch (SQLiteCantOpenDatabaseException unused) {
                        Log.w("AccountManagerService", "Could not delete grants for user = " + userAccounts.userId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final Account[] queryAccountsFromDb(final String str, UserAccounts userAccounts) {
        Map findAllDeAccounts;
        this.mUseAccountDb = false;
        synchronized (userAccounts.dbLock) {
            findAllDeAccounts = userAccounts.accountsDb.findAllDeAccounts();
        }
        final ArrayList arrayList = new ArrayList();
        ((LinkedHashMap) findAllDeAccounts).forEach(new BiConsumer() { // from class: com.android.server.accounts.AccountManagerService$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                ArrayList arrayList2 = arrayList;
                Account account = (Account) obj2;
                if (str2 == null || account.type.equals(str2)) {
                    arrayList2.add(account);
                }
            }
        });
        boolean isEmpty = findAllDeAccounts.isEmpty();
        if (isAccountsCacheEmpty(userAccounts) && !isEmpty) {
            saveAccountEventRecord("Accounts cache and db are mismatched. Recompute accounts cache", true);
            validateAccountsInternal(userAccounts, false);
            AccountManager.invalidateLocalAccountsDataCaches();
        }
        return arrayList.size() == 0 ? EMPTY_ACCOUNT_ARRAY : (Account[]) arrayList.toArray(new Account[arrayList.size()]);
    }

    public final String readPasswordInternal(Account account, UserAccounts userAccounts) {
        String findAccountPasswordByNameAndType;
        if (account == null) {
            return null;
        }
        if (!isLocalUnlockedUser(userAccounts.userId)) {
            Log.w("AccountManagerService", "Password is not available - user " + userAccounts.userId + " data is locked");
            return null;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                findAccountPasswordByNameAndType = userAccounts.accountsDb.findAccountPasswordByNameAndType(account.name, account.type);
            }
        }
        return findAccountPasswordByNameAndType;
    }

    public final void registerAccountListener(String[] strArr, String str) {
        this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str);
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            registerAccountListener(strArr, str, getUserAccounts(callingUserId));
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean z, int i) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "removeAccount: " + account + ", response " + iAccountManagerResponse + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid() + ", for user id " + i);
        }
        Preconditions.checkArgument(account != null, "account cannot be null");
        Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        if (isCrossUser(callingUid, i)) {
            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(UserHandle.getCallingUserId(), i, "User ", " tying remove account for "));
        }
        if (!isAccountManagedByCaller(callingUid, UserHandle.of(i).getIdentifier(), account.type) && !isSystemUid(callingUid) && !isProfileOwner(callingUid)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot remove accounts of type: ", account.type));
        }
        if (!canUserModifyAccounts(i, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User cannot modify accounts");
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        try {
            IDeviceAccountPolicy asInterface = IDeviceAccountPolicy.Stub.asInterface(ServiceManager.getService("device_account_policy"));
            if (asInterface != null && !asInterface.isAccountRemovalAllowedAsUser(account.type, account.name, true, i)) {
                throw new SecurityException("Security policy blocks this account removal");
            }
        } catch (RemoteException unused2) {
        }
        if (!canUserModifyAccountsForType(i, callingUid, account.type)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
                return;
            } catch (RemoteException e) {
                Log.w("AccountManagerService", "RemoteException while removing account", e);
                return;
            }
        }
        if (isFirstAccountRemovalDisabled(account)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot remove the first " + account.type + " account on the device.");
                return;
            } catch (RemoteException e2) {
                Log.w("AccountManagerService", "RemoteException while removing account", e2);
                return;
            }
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        UserAccounts userAccounts = getUserAccounts(i);
        cancelNotification(getSigninRequiredNotificationId(account, userAccounts), userAccounts);
        synchronized (userAccounts.credentialsPermissionNotificationIds) {
            try {
                for (Pair pair : userAccounts.credentialsPermissionNotificationIds.keySet()) {
                    if (account.equals(((Pair) pair.first).first)) {
                        cancelNotification((NotificationId) userAccounts.credentialsPermissionNotificationIds.get(pair), userAccounts);
                    }
                }
            } finally {
            }
        }
        logRecord("action_called_account_remove", "accounts", userAccounts.accountsDb.findDeAccountId(account), userAccounts, callingUid);
        try {
            new AnonymousClass7(this, userAccounts, iAccountManagerResponse, account, z).bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean removeAccountExplicitly(Account account) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "removeAccountExplicitly: " + account + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        int identifier = Binder.getCallingUserHandle().getIdentifier();
        if (account == null) {
            Log.e("AccountManagerService", "account is null");
            return false;
        }
        if (!isAccountManagedByCaller(callingUid, identifier, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot explicitly remove accounts of type: ", account.type));
        }
        if (isFirstAccountRemovalDisabled(account)) {
            Log.e("AccountManagerService", "Cannot remove the first " + account.type + " account on the device.");
            return false;
        }
        UserAccounts userAccounts = getUserAccounts(UserHandle.getCallingUserId());
        logRecord("action_called_account_remove", "accounts", userAccounts.accountsDb.findDeAccountId(account), userAccounts, callingUid);
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return removeAccountInternal(userAccounts, account, callingUid);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeAccountInternal(Account account) {
        removeAccountInternal(getUserAccounts(UserHandle.getCallingUserId()), account, IAccountManager.Stub.getCallingUid());
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x00a7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x028b, code lost:
    
        throw r0;
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean removeAccountInternal(final com.android.server.accounts.AccountManagerService.UserAccounts r24, final android.accounts.Account r25, int r26) {
        /*
            Method dump skipped, instructions count: 654
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accounts.AccountManagerService.removeAccountInternal(com.android.server.accounts.AccountManagerService$UserAccounts, android.accounts.Account, int):boolean");
    }

    public final void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String str) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "renameAccount: " + account + " -> " + str + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        if (str != null && str.length() > 200) {
            Log.e("AccountManagerService", "renameAccount failed - account name longer than 200");
            throw new IllegalArgumentException("account name longer than 200");
        }
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot rename accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            Log.i("AccountManagerService", "callingUid=" + callingUid + ", userId=" + userAccounts.userId + " performing rename account");
            Account renameAccountInternal = renameAccountInternal(account, userAccounts, str);
            Bundle bundle = new Bundle();
            bundle.putString("authAccount", renameAccountInternal.name);
            bundle.putString("accountType", renameAccountInternal.type);
            bundle.putString("accountAccessId", renameAccountInternal.getAccessId());
            try {
                iAccountManagerResponse.onResult(bundle);
            } catch (RemoteException e) {
                Log.w("AccountManagerService", e.getMessage());
            }
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Account renameAccountInternal(Account account, UserAccounts userAccounts, String str) {
        cancelNotification(getSigninRequiredNotificationId(account, userAccounts), userAccounts);
        synchronized (userAccounts.credentialsPermissionNotificationIds) {
            try {
                for (Pair pair : userAccounts.credentialsPermissionNotificationIds.keySet()) {
                    if (account.equals(((Pair) pair.first).first)) {
                        cancelNotification((NotificationId) userAccounts.credentialsPermissionNotificationIds.get(pair), userAccounts);
                    }
                }
            } finally {
            }
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                List accountRemovedReceivers = getAccountRemovedReceivers(account, userAccounts);
                userAccounts.accountsDb.beginTransaction();
                Account account2 = new Account(str, account.type);
                try {
                    if (userAccounts.accountsDb.findCeAccountId(account2) >= 0) {
                        Log.e("AccountManagerService", "renameAccount failed - account with new name already exists");
                        return null;
                    }
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId < 0) {
                        Log.e("AccountManagerService", "renameAccount failed - old account does not exist");
                        return null;
                    }
                    SQLiteDatabase writableDatabaseUserIsUnlocked = userAccounts.accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", str);
                    writableDatabaseUserIsUnlocked.update("ceDb.accounts", contentValues, "_id=?", new String[]{String.valueOf(findDeAccountId)});
                    AccountsDb accountsDb = userAccounts.accountsDb;
                    String str2 = account.name;
                    SQLiteDatabase writableDatabase = accountsDb.mDeDatabase.getWritableDatabase();
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("name", str);
                    contentValues2.put("previous_name", str2);
                    if (writableDatabase.update("accounts", contentValues2, "_id=?", new String[]{String.valueOf(findDeAccountId)}) <= 0) {
                        Log.e("AccountManagerService", "renameAccount failed");
                        return null;
                    }
                    userAccounts.accountsDb.setTransactionSuccessful();
                    userAccounts.accountsDb.endTransaction();
                    Account insertAccountIntoCacheLocked = insertAccountIntoCacheLocked(account2, userAccounts);
                    Map map = (Map) ((HashMap) userAccounts.userDataCache).get(account);
                    Map map2 = (Map) ((HashMap) userAccounts.authTokenCache).get(account);
                    Map map3 = (Map) ((HashMap) userAccounts.visibilityCache).get(account);
                    removeAccountFromCacheLocked(account, userAccounts);
                    ((HashMap) userAccounts.userDataCache).put(insertAccountIntoCacheLocked, map);
                    ((HashMap) userAccounts.authTokenCache).put(insertAccountIntoCacheLocked, map2);
                    ((HashMap) userAccounts.visibilityCache).put(insertAccountIntoCacheLocked, map3);
                    userAccounts.previousNameCache.put(insertAccountIntoCacheLocked, new AtomicReference(account.name));
                    int i = userAccounts.userId;
                    UserInfo userInfo = getUserManager().getUserInfo(i);
                    if (userInfo != null && userInfo.canHaveProfile()) {
                        for (UserInfo userInfo2 : getUserManager().getAliveUsers()) {
                            if (userInfo2.isRestricted() && userInfo2.restrictedProfileParentId == i) {
                                renameSharedAccountAsUser(account, str, userInfo2.id);
                            }
                        }
                    }
                    sendNotificationAccountUpdated(insertAccountIntoCacheLocked, userAccounts);
                    sendAccountsChangedBroadcast(userAccounts.userId, account.type, "renameAccount");
                    Iterator it = ((ArrayList) accountRemovedReceivers).iterator();
                    while (it.hasNext()) {
                        sendAccountRemovedBroadcast(account, (String) it.next(), userAccounts.userId, "renameAccount");
                    }
                    AccountManager.invalidateLocalAccountsDataCaches();
                    AccountManager.invalidateLocalAccountUserDataCaches();
                    return insertAccountIntoCacheLocked;
                } finally {
                    userAccounts.accountsDb.endTransaction();
                }
            }
        }
    }

    public final void renameSharedAccountAsUser(Account account, String str, int i) {
        UserAccounts userAccounts = getUserAccounts(handleIncomingUser(i));
        AccountsDb accountsDb = userAccounts.accountsDb;
        long findSharedAccountId = accountsDb.findSharedAccountId(account);
        if (accountsDb.mDeDatabase.getWritableDatabase().update("shared_accounts", AccountManagerService$$ExternalSyntheticOutline0.m("name", str), "name=? AND type=?", new String[]{account.name, account.type}) > 0) {
            logRecord("action_account_rename", "shared_accounts", findSharedAccountId, userAccounts, IAccountManager.Stub.getCallingUid());
            renameAccountInternal(account, userAccounts, str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00cb, code lost:
    
        if (r2 == false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00d7, code lost:
    
        if (isPermittedForPackage(r14, r6, "android.permission.GET_ACCOUNTS", "android.permission.GET_ACCOUNTS_PRIVILEGED") != false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e3, code lost:
    
        if (isPermittedForPackage(r14, r6, "android.permission.READ_CONTACTS") == false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e5, code lost:
    
        r14 = r12.type;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e7, code lost:
    
        if (r14 != null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ea, code lost:
    
        r0 = android.os.Binder.clearCallingIdentity();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ee, code lost:
    
        r2 = r11.mAuthenticatorCache.getAllServices(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f4, code lost:
    
        android.os.Binder.restoreCallingIdentity(r0);
        r0 = r2.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ff, code lost:
    
        if (r0.hasNext() == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0101, code lost:
    
        r1 = (android.content.pm.RegisteredServicesCache.ServiceInfo) r0.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0111, code lost:
    
        if (r14.equals(((android.accounts.AuthenticatorDescription) r1.type).type) == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0113, code lost:
    
        r5 = isPermittedForPackage(((android.accounts.AuthenticatorDescription) r1.type).packageName, r6, "android.permission.WRITE_CONTACTS");
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0126, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x012a, code lost:
    
        throw r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0123, code lost:
    
        if (r5 != false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x012b, code lost:
    
        if (r8 == false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x012e, code lost:
    
        r4 = getAccountVisibilityFromCache(r12, r13, "android:accounts:key_legacy_not_visible");
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0134, code lost:
    
        if (r4 != 0) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0136, code lost:
    
        r4 = 4;
     */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0141  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Integer resolveAccountVisibility(android.accounts.Account r12, com.android.server.accounts.AccountManagerService.UserAccounts r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 367
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accounts.AccountManagerService.resolveAccountVisibility(android.accounts.Account, com.android.server.accounts.AccountManagerService$UserAccounts, java.lang.String):java.lang.Integer");
    }

    public final void saveAccountEventRecord(String str, boolean z) {
        this.mAccountHistory.append(new AccountEventRecord(str));
        if (z) {
            PmLog.logDebugInfoAndLogcat(str);
        }
    }

    public final boolean saveAuthTokenToDatabase(UserAccounts userAccounts, Account account, String str, String str2) {
        if (account == null || str == null) {
            return false;
        }
        cancelNotification(getSigninRequiredNotificationId(account, userAccounts), userAccounts);
        synchronized (userAccounts.dbLock) {
            userAccounts.accountsDb.beginTransaction();
            try {
                long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                if (findDeAccountId < 0) {
                    userAccounts.accountsDb.endTransaction();
                    return false;
                }
                userAccounts.accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked().delete("ceDb.authtokens", "accounts_id=? AND type=?", new String[]{String.valueOf(findDeAccountId), str});
                SQLiteDatabase writableDatabaseUserIsUnlocked = userAccounts.accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked();
                ContentValues contentValues = new ContentValues();
                contentValues.put("accounts_id", Long.valueOf(findDeAccountId));
                contentValues.put("type", str);
                contentValues.put("authtoken", str2);
                if (writableDatabaseUserIsUnlocked.insert("ceDb.authtokens", "authtoken", contentValues) < 0) {
                    userAccounts.accountsDb.endTransaction();
                    return false;
                }
                userAccounts.accountsDb.setTransactionSuccessful();
                userAccounts.accountsDb.endTransaction();
                synchronized (userAccounts.cacheLock) {
                    writeAuthTokenIntoCacheLocked(userAccounts, account, str, str2);
                }
                return true;
            } catch (Throwable th) {
                userAccounts.accountsDb.endTransaction();
                throw th;
            }
        }
    }

    public final void sendAccountRemovedBroadcast(Account account, String str, int i, String str2) {
        StringBuilder sb = new StringBuilder("the account with type=");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, account.type, " removed while useCase=", str2, " for userId=");
        sb.append(i);
        sb.append(", sending broadcast of android.accounts.action.ACCOUNT_REMOVED");
        Log.i("AccountManagerService", sb.toString());
        Intent intent = new Intent("android.accounts.action.ACCOUNT_REMOVED");
        intent.setFlags(16777216);
        intent.setPackage(str);
        intent.putExtra("authAccount", account.name);
        intent.putExtra("accountType", account.type);
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(i));
    }

    public final void sendAccountsChangedBroadcast(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder("the accountType= ");
        if (str == null) {
            str = "";
        }
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, str, " changed with useCase=", str2, " for userId=");
        sb.append(i);
        sb.append(", sending broadcast of ");
        Intent intent = ACCOUNTS_CHANGED_INTENT;
        sb.append(intent.getAction());
        Log.i("AccountManagerService", sb.toString());
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(i), null, ACCOUNTS_CHANGED_OPTIONS);
    }

    public final void sendNotificationAccountUpdated(Account account, UserAccounts userAccounts) {
        for (Map.Entry entry : ((HashMap) getRequestingPackages(account, userAccounts)).entrySet()) {
            if (((Integer) entry.getValue()).intValue() != 3 && ((Integer) entry.getValue()).intValue() != 4) {
                notifyPackage((String) entry.getKey(), userAccounts);
            }
        }
    }

    public final boolean setAccountVisibility(Account account, String str, int i) {
        Objects.requireNonNull(account, "account cannot be null");
        Objects.requireNonNull(str, "packageName cannot be null");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type) && !isSystemUid(callingUid)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot get secrets for accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            return setAccountVisibility(account, str, i, true, getUserAccounts(callingUserId), callingUid);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x010c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00db A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setAccountVisibility(android.accounts.Account r17, java.lang.String r18, int r19, boolean r20, com.android.server.accounts.AccountManagerService.UserAccounts r21, int r22) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accounts.AccountManagerService.setAccountVisibility(android.accounts.Account, java.lang.String, int, boolean, com.android.server.accounts.AccountManagerService$UserAccounts, int):boolean");
    }

    public final void setAuthToken(Account account, String str, String str2) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "setAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        Objects.requireNonNull(account, "account cannot be null");
        Objects.requireNonNull(str, "authTokenType cannot be null");
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot set auth tokens associated with accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            saveAuthTokenToDatabase(getUserAccounts(callingUserId), account, str, str2);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setPassword(Account account, String str) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "setAuthToken: " + account + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        Objects.requireNonNull(account, "account cannot be null");
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot set secrets for accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            setPasswordInternal(callingUid, account, getUserAccounts(callingUserId), str);
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setPasswordInternal(int i, Account account, UserAccounts userAccounts, String str) {
        boolean z;
        long clearCallingIdentity;
        boolean z2;
        String str2;
        if (account == null) {
            return;
        }
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    userAccounts.accountsDb.beginTransaction();
                    try {
                        long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                        if (findDeAccountId >= 0) {
                            SQLiteDatabase writableDatabaseUserIsUnlocked = userAccounts.accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("password", str);
                            writableDatabaseUserIsUnlocked.update("ceDb.accounts", contentValues, "_id=?", new String[]{String.valueOf(findDeAccountId)});
                            userAccounts.accountsDb.mDeDatabase.getWritableDatabaseUserIsUnlocked().delete("ceDb.authtokens", "accounts_id=?", new String[]{String.valueOf(findDeAccountId)});
                            ((HashMap) userAccounts.authTokenCache).remove(account);
                            TokenCache.TokenLruCache.Evictor evictor = (TokenCache.TokenLruCache.Evictor) userAccounts.accountTokenCaches.mCachedTokens.mAccountEvictors.get(account);
                            if (evictor != null) {
                                evictor.evict();
                            }
                            userAccounts.accountsDb.setTransactionSuccessful();
                            if (str != null) {
                                try {
                                    if (str.length() != 0) {
                                        str2 = "action_set_password";
                                        logRecord(str2, "accounts", findDeAccountId, userAccounts, i);
                                        z2 = true;
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    z = true;
                                    userAccounts.accountsDb.endTransaction();
                                    if (z) {
                                        sendNotificationAccountUpdated(account, userAccounts);
                                        Log.i("AccountManagerService", "callingUid=" + i + " changed password");
                                        sendAccountsChangedBroadcast(userAccounts.userId, account.type, "setPassword");
                                    }
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        AuditLog.logAsUser(5, 4, z, Process.myPid(), "AccountManagerService", String.format(z ? "Updating account %s succeeded" : "Updating account %s failed", account.toString()), "", userAccounts.userId);
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        throw th;
                                    } finally {
                                    }
                                }
                            }
                            str2 = "action_clear_password";
                            logRecord(str2, "accounts", findDeAccountId, userAccounts, i);
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        userAccounts.accountsDb.endTransaction();
                        if (z2) {
                            sendNotificationAccountUpdated(account, userAccounts);
                            Log.i("AccountManagerService", "callingUid=" + i + " changed password");
                            sendAccountsChangedBroadcast(userAccounts.userId, account.type, "setPassword");
                        }
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            AuditLog.logAsUser(5, 4, z2, Process.myPid(), "AccountManagerService", String.format(z2 ? "Updating account %s succeeded" : "Updating account %s failed", account.toString()), "", userAccounts.userId);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } finally {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        z = false;
                    }
                } catch (Throwable th3) {
                    throw th3;
                }
            }
        }
    }

    public final void setUserData(Account account, String str, String str2) {
        int callingUid = Binder.getCallingUid();
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "setUserData: " + account + ", key " + str + ", caller's uid " + callingUid + ", pid " + Binder.getCallingPid());
        }
        if (str == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        int callingUserId = UserHandle.getCallingUserId();
        if (!isAccountManagedByCaller(callingUid, callingUserId, account.type)) {
            throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "uid ", " cannot set user data for accounts of type: ", account.type));
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(callingUserId);
            if (accountExistsCache(account, userAccounts)) {
                setUserdataInternal(userAccounts, account, str, str2);
            }
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void showCantAddAccount(int i, int i2) {
        Intent createUserRestrictionSupportIntent;
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            createUserRestrictionSupportIntent = new Intent(this.mContext, (Class<?>) CantAddAccountActivity.class);
            createUserRestrictionSupportIntent.putExtra("android.accounts.extra.ERROR_CODE", i);
            createUserRestrictionSupportIntent.addFlags(268435456);
        } else {
            createUserRestrictionSupportIntent = i == 100 ? devicePolicyManagerInternal.createUserRestrictionSupportIntent(i2, "no_modify_accounts") : i == 101 ? devicePolicyManagerInternal.createShowAdminSupportIntent(i2, false) : null;
        }
        if (createUserRestrictionSupportIntent == null) {
            createUserRestrictionSupportIntent = new Intent(this.mContext, (Class<?>) CantAddAccountActivity.class);
            createUserRestrictionSupportIntent.putExtra("android.accounts.extra.ERROR_CODE", i);
            createUserRestrictionSupportIntent.addFlags(268435456);
        }
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            this.mContext.startActivityAsUser(createUserRestrictionSupportIntent, new UserHandle(i2));
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean someUserHasAccount(Account account) {
        if (!UserHandle.isSameApp(1000, Binder.getCallingUid())) {
            throw new SecurityException("Only system can check for accounts across users");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AccountAndUser[] allAccountsForSystemProcess = getAllAccountsForSystemProcess();
            for (int length = allAccountsForSystemProcess.length - 1; length >= 0; length--) {
                if (allAccountsForSystemProcess[length].account.equals(account)) {
                    return true;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, final String str, final String str2, final String[] strArr, boolean z, Bundle bundle) {
        Bundle bundle2 = bundle;
        Bundle.setDefusable(bundle2, true);
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "startAddAccountSession: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + Arrays.toString(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        Preconditions.checkArgument(iAccountManagerResponse != null, "response cannot be null");
        Preconditions.checkArgument(str != null, "accountType cannot be null");
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        if (!canUserModifyAccounts(userId, callingUid)) {
            try {
                iAccountManagerResponse.onError(100, "User is not allowed to add an account!");
            } catch (RemoteException unused) {
            }
            showCantAddAccount(100, userId);
            return;
        }
        if (!canUserModifyAccountsForType(userId, callingUid, str)) {
            try {
                iAccountManagerResponse.onError(101, "User cannot modify accounts of this type (policy).");
            } catch (RemoteException unused2) {
            }
            showCantAddAccount(101, userId);
            return;
        }
        int callingPid = Binder.getCallingPid();
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        final Bundle bundle3 = bundle2;
        bundle3.putInt("callerUid", callingUid);
        bundle3.putInt("callerPid", callingPid);
        final String string = bundle3.getString("androidPackageName");
        boolean checkPermissionAndNote = checkPermissionAndNote(string, callingUid, "android.permission.GET_PASSWORD");
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            UserAccounts userAccounts = getUserAccounts(userId);
            logRecord("action_called_start_account_add", "accounts", -1L, userAccounts, callingUid);
            new StartAccountSession(userAccounts, iAccountManagerResponse, str, z, checkPermissionAndNote) { // from class: com.android.server.accounts.AccountManagerService.10
                @Override // com.android.server.accounts.AccountManagerService.Session
                public final void run() {
                    this.mAuthenticator.startAddAccountSession(this, this.mAccountType, str2, strArr, bundle3);
                    AccountManagerService.m140$$Nest$mlogAddAccountMetrics(AccountManagerService.this, string, str, strArr, str2);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public final String toDebugString(long j) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(super.toDebugString(j));
                    sb.append(", startAddAccountSession, accountType ");
                    sb.append(str);
                    sb.append(", requiredFeatures ");
                    String[] strArr2 = strArr;
                    sb.append(strArr2 != null ? TextUtils.join(",", strArr2) : "null");
                    return sb.toString();
                }
            }.bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, final Account account, final String str, boolean z, final Bundle bundle) {
        Bundle.setDefusable(bundle, true);
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "startUpdateCredentialsSession: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        boolean checkPermissionAndNote = checkPermissionAndNote(bundle.getString("androidPackageName"), callingUid, "android.permission.GET_PASSWORD");
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new StartAccountSession(this, getUserAccounts(callingUserId), iAccountManagerResponse, account.type, z, account.name, checkPermissionAndNote) { // from class: com.android.server.accounts.AccountManagerService.14
                @Override // com.android.server.accounts.AccountManagerService.Session
                public final void run() {
                    this.mAuthenticator.startUpdateCredentialsSession(this, account, str, bundle);
                }

                @Override // com.android.server.accounts.AccountManagerService.Session
                public final String toDebugString(long j) {
                    Bundle bundle2 = bundle;
                    if (bundle2 != null) {
                        bundle2.keySet();
                    }
                    return super.toDebugString(j) + ", startUpdateCredentialsSession, " + account.toSafeString() + ", authTokenType " + str + ", loginOptions " + bundle;
                }
            }.bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void syncDeCeAccountsLocked(UserAccounts userAccounts) {
        Preconditions.checkState(Thread.holdsLock(this.mUsers), "mUsers lock must be held");
        Cursor rawQuery = userAccounts.accountsDb.mDeDatabase.getReadableDatabaseUserIsUnlocked().rawQuery("SELECT name,type FROM ceDb.accounts WHERE NOT EXISTS  (SELECT _id FROM accounts WHERE _id=ceDb.accounts._id )", null);
        try {
            ArrayList arrayList = new ArrayList(rawQuery.getCount());
            while (rawQuery.moveToNext()) {
                arrayList.add(new Account(rawQuery.getString(0), rawQuery.getString(1)));
            }
            rawQuery.close();
            if (arrayList.isEmpty()) {
                return;
            }
            Slog.i("AccountManagerService", arrayList.size() + " accounts were previously deleted while user " + userAccounts.userId + " was locked. Removing accounts from CE tables");
            logRecord("action_sync_de_ce_accounts", "accounts", -1L, userAccounts, IAccountManager.Stub.getCallingUid());
            this.mSyncDeCeAccountsExecuted = true;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Account account = (Account) it.next();
                saveAccountEventRecord("Sync accounts db. Remove : " + account.name + "/" + account.type + ", isCacheEmpty : " + isAccountsCacheEmpty(userAccounts), true);
                removeAccountInternal(userAccounts, account, 1000);
            }
        } catch (Throwable th) {
            rawQuery.close();
            throw th;
        }
    }

    public final void unregisterAccountListener(String[] strArr, String str) {
        this.mAppOpsManager.checkPackage(Binder.getCallingUid(), str);
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            unregisterAccountListener(strArr, str, getUserAccounts(callingUserId));
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateAppPermission(Account account, String str, int i, boolean z) {
        if (UserHandle.getAppId(IAccountManager.Stub.getCallingUid()) != 1000) {
            throw new SecurityException();
        }
        if (z) {
            grantAppPermission(account, str, i);
            return;
        }
        if (account == null || str == null) {
            Log.e("AccountManagerService", "revokeAppPermission: called with invalid arguments", new Exception());
            return;
        }
        UserAccounts userAccounts = getUserAccounts(UserHandle.getUserId(i));
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                userAccounts.accountsDb.beginTransaction();
                try {
                    long findDeAccountId = userAccounts.accountsDb.findDeAccountId(account);
                    if (findDeAccountId >= 0) {
                        userAccounts.accountsDb.mDeDatabase.getWritableDatabase().delete("grants", "accounts_id=? AND auth_token_type=? AND uid=?", new String[]{String.valueOf(findDeAccountId), str, String.valueOf(i)});
                        userAccounts.accountsDb.setTransactionSuccessful();
                    }
                    userAccounts.accountsDb.endTransaction();
                    cancelNotification(getCredentialPermissionNotificationId(i, account, userAccounts, str), userAccounts);
                } catch (Throwable th) {
                    userAccounts.accountsDb.endTransaction();
                    throw th;
                }
            }
        }
        Iterator it = this.mAppPermissionChangeListeners.iterator();
        while (it.hasNext()) {
            this.mHandler.post(new AccountManagerService$$ExternalSyntheticLambda5((AccountManagerInternal.OnAppPermissionChangeListener) it.next(), account, i, 1));
        }
    }

    public final void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) {
        Bundle.setDefusable(bundle, true);
        if (Log.isLoggable("AccountManagerService", 2)) {
            Log.v("AccountManagerService", "updateCredentials: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        }
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = IAccountManager.Stub.clearCallingIdentity();
        try {
            new AnonymousClass15(this, getUserAccounts(callingUserId), iAccountManagerResponse, account.type, z, account.name, account, str, bundle).bind();
        } finally {
            IAccountManager.Stub.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updateLastAuthenticatedTime(Account account) {
        boolean z;
        UserAccounts userAccounts = getUserAccounts(UserHandle.getCallingUserId());
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                SQLiteDatabase writableDatabase = userAccounts.accountsDb.mDeDatabase.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("last_password_entry_time_millis_epoch", Long.valueOf(System.currentTimeMillis()));
                z = writableDatabase.update("accounts", contentValues, "name=? AND type=?", new String[]{account.name, account.type}) > 0;
            }
        }
        return z;
    }

    public final void validateAccountsInternal(UserAccounts userAccounts, boolean z) {
        Throwable th;
        boolean z2;
        LinkedHashMap linkedHashMap;
        ApplicationInfo applicationInfo;
        if (Log.isLoggable("AccountManagerService", 3)) {
            Log.d("AccountManagerService", "validateAccountsInternal " + userAccounts.userId + " isCeDatabaseAttached=" + userAccounts.accountsDb.mDeDatabase.mCeAttached + " userLocked=" + this.mLocalUnlockedUsers.get(userAccounts.userId));
        }
        saveAccountEventRecord("validateAccountsInternal " + userAccounts.userId + " isCeDatabaseAttached=" + userAccounts.accountsDb.mDeDatabase.mCeAttached + " userUnLocked=" + this.mLocalUnlockedUsers.get(userAccounts.userId), true);
        this.mUseAccountDb = true;
        if (z) {
            this.mAuthenticatorCache.invalidateCache(userAccounts.userId);
        }
        HashMap authenticatorTypeAndUIDForUser = getAuthenticatorTypeAndUIDForUser(this.mAuthenticatorCache, userAccounts.userId);
        boolean isLocalUnlockedUser = isLocalUnlockedUser(userAccounts.userId);
        synchronized (userAccounts.dbLock) {
            synchronized (userAccounts.cacheLock) {
                try {
                    AccountsDb accountsDb = userAccounts.accountsDb;
                    Map findMetaAuthUid = accountsDb.findMetaAuthUid();
                    HashSet newHashSet = Sets.newHashSet();
                    SparseBooleanArray sparseBooleanArray = null;
                    for (Map.Entry entry : ((LinkedHashMap) findMetaAuthUid).entrySet()) {
                        String str = (String) entry.getKey();
                        int intValue = ((Integer) entry.getValue()).intValue();
                        Integer num = (Integer) authenticatorTypeAndUIDForUser.get(str);
                        if (num == null || intValue != num.intValue()) {
                            if (sparseBooleanArray == null) {
                                List installedPackagesAsUser = this.mPackageManager.getInstalledPackagesAsUser(8192, userAccounts.userId);
                                sparseBooleanArray = new SparseBooleanArray(installedPackagesAsUser.size());
                                Iterator it = installedPackagesAsUser.iterator();
                                while (it.hasNext()) {
                                    ApplicationInfo applicationInfo2 = ((PackageInfo) it.next()).applicationInfo;
                                    if (applicationInfo2 != null && (applicationInfo2.flags & 8388608) != 0) {
                                        sparseBooleanArray.put(applicationInfo2.uid, true);
                                    }
                                }
                            }
                            if (!sparseBooleanArray.get(intValue)) {
                                newHashSet.add(str);
                                accountsDb.deleteMetaByAuthTypeAndUid(intValue, str);
                                Iterator it2 = this.mPackageManager.getInstalledPackagesAsUser(8192, userAccounts.userId).iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        applicationInfo = null;
                                        break;
                                    }
                                    applicationInfo = ((PackageInfo) it2.next()).applicationInfo;
                                    if (applicationInfo != null && applicationInfo.uid == intValue) {
                                        break;
                                    }
                                }
                                saveAccountEventRecord(applicationInfo == null ? "Authenticator packageInfo is null : " + str + "/" + intValue : "ObsoleteAuthType packageInfo : " + applicationInfo.className + "/" + applicationInfo.flags, true);
                            } else if (num != null && num.intValue() != intValue) {
                                Slog.w("AccountManagerService", "authenticator no longer exist for type " + str);
                                newHashSet.add(str);
                                accountsDb.deleteMetaByAuthTypeAndUid(intValue, str);
                            }
                        } else {
                            authenticatorTypeAndUIDForUser.remove(str);
                        }
                    }
                    for (Map.Entry entry2 : authenticatorTypeAndUIDForUser.entrySet()) {
                        accountsDb.insertOrReplaceMetaAuthTypeAndUid(((Integer) entry2.getValue()).intValue(), (String) entry2.getKey());
                    }
                    Map findAllDeAccounts = accountsDb.findAllDeAccounts();
                    try {
                        userAccounts.accountCache.clear();
                        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                        boolean z3 = false;
                        for (Map.Entry entry3 : ((LinkedHashMap) findAllDeAccounts).entrySet()) {
                            try {
                                long longValue = ((Long) entry3.getKey()).longValue();
                                Account account = (Account) entry3.getValue();
                                if (newHashSet.contains(account.type)) {
                                    Slog.w("AccountManagerService", "deleting account " + account.toSafeString() + " because type " + account.type + "'s registered authenticator no longer exist.");
                                    Map requestingPackages = getRequestingPackages(account, userAccounts);
                                    List accountRemovedReceivers = getAccountRemovedReceivers(account, userAccounts);
                                    accountsDb.beginTransaction();
                                    try {
                                        accountsDb.deleteDeAccount(longValue);
                                        if (isLocalUnlockedUser) {
                                            accountsDb.deleteCeAccount(longValue);
                                        }
                                        accountsDb.setTransactionSuccessful();
                                        accountsDb.endTransaction();
                                        try {
                                            Log.i("AccountManagerService", "validateAccountsInternal#Deleted UserId=" + userAccounts.userId + ", AccountId=" + longValue);
                                            linkedHashMap = linkedHashMap2;
                                            logRecord("action_authenticator_remove", "accounts", longValue, userAccounts, IAccountManager.Stub.getCallingUid());
                                            ((HashMap) userAccounts.userDataCache).remove(account);
                                            ((HashMap) userAccounts.authTokenCache).remove(account);
                                            TokenCache.TokenLruCache.Evictor evictor = (TokenCache.TokenLruCache.Evictor) userAccounts.accountTokenCaches.mCachedTokens.mAccountEvictors.get(account);
                                            if (evictor != null) {
                                                evictor.evict();
                                            }
                                            ((HashMap) userAccounts.visibilityCache).remove(account);
                                            for (Map.Entry entry4 : ((HashMap) requestingPackages).entrySet()) {
                                                int intValue2 = ((Integer) entry4.getValue()).intValue();
                                                if (intValue2 == 1 || intValue2 == 2) {
                                                    notifyPackage((String) entry4.getKey(), userAccounts);
                                                }
                                            }
                                            try {
                                                Iterator it3 = ((ArrayList) accountRemovedReceivers).iterator();
                                                while (it3.hasNext()) {
                                                    sendAccountRemovedBroadcast(account, (String) it3.next(), userAccounts.userId, "validateAccounts");
                                                }
                                            } catch (IllegalStateException e) {
                                                saveAccountEventRecord("Fail to send account remove broadcast " + e, true);
                                            }
                                            z3 = true;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            z2 = true;
                                            if (!z2) {
                                                throw th;
                                            }
                                            try {
                                                sendAccountsChangedBroadcast(userAccounts.userId, "ambiguous", "validateAccounts");
                                                throw th;
                                            } catch (IllegalStateException e2) {
                                                saveAccountEventRecord("Fail to send account remove broadcast " + e2, true);
                                                throw th;
                                            }
                                        }
                                    } finally {
                                        accountsDb.endTransaction();
                                    }
                                } else {
                                    linkedHashMap = linkedHashMap2;
                                    ArrayList arrayList = (ArrayList) linkedHashMap.get(account.type);
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                        linkedHashMap.put(account.type, arrayList);
                                    }
                                    arrayList.add(account.name);
                                }
                                linkedHashMap2 = linkedHashMap;
                            } catch (Throwable th3) {
                                z2 = z3;
                                th = th3;
                            }
                        }
                        for (Map.Entry entry5 : linkedHashMap2.entrySet()) {
                            String str2 = (String) entry5.getKey();
                            ArrayList arrayList2 = (ArrayList) entry5.getValue();
                            int size = arrayList2.size();
                            Account[] accountArr = new Account[size];
                            for (int i = 0; i < size; i++) {
                                accountArr[i] = new Account((String) arrayList2.get(i), str2, UUID.randomUUID().toString());
                            }
                            userAccounts.accountCache.put(str2, accountArr);
                        }
                        saveAccountEventRecord("Accounts cache is created: user " + userAccounts.userId, true);
                        ((HashMap) userAccounts.visibilityCache).putAll(accountsDb.findAllVisibilityValues());
                        AccountManager.invalidateLocalAccountsDataCaches();
                        if (z3) {
                            try {
                                sendAccountsChangedBroadcast(userAccounts.userId, "ambiguous", "validateAccounts");
                            } catch (IllegalStateException e3) {
                                saveAccountEventRecord("Fail to send account remove broadcast " + e3, true);
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        z2 = false;
                    }
                } finally {
                }
            }
        }
    }
}
