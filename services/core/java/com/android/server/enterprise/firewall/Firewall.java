package com.android.server.enterprise.firewall;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.firewall.FirewallDefinitions;
import com.android.server.enterprise.storage.EdmStorageDefs;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knox.net.firewall.FirewallRuleValidator;
import com.samsung.android.knox.net.firewall.IFirewall;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class Firewall extends IFirewall.Stub implements EnterpriseServiceCallback {
    public static String TAG = "Firewall";
    public static boolean mHasIpv6FilterSupport = false;
    public static boolean mHasIpv6NatSupport = false;
    public boolean mAreRulesReloaded;
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public DomainFilter mDomainFilter;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDumpHelper mEnterpriseDumpHelper;
    public FirewallRulesApplier mFirewallRulesApplier;
    public Thread mInitIpTablesThread;
    public final Object mInitializingIpTablesRulesLock;
    public final Injector mInjector;
    public boolean mIsChainsCreated;
    public final BroadcastReceiver mPackageFilterReceiver;
    public final BroadcastReceiver mUserReceiver;

    enum DownloadBlockStatus {
        PASSED_NORULE,
        PASSED_BY_WHITELIST_RULE,
        BLOCKED_BY_BLACKLIST_RULE
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    class Injector {
        public final Context mCtx;
        public DomainFilter mDomainFilter;
        public EdmStorageProvider mEdmStorageProvider;
        public EnterpriseDumpHelper mEnterpriseDumpHelper;
        public FirewallRulesApplier mFirewallRulesApplier;

        public Injector(Context context) {
            this.mCtx = context;
        }

        public void initInjector(Firewall firewall) {
            this.mEdmStorageProvider = new EdmStorageProvider(this.mCtx);
            this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mCtx);
            this.mFirewallRulesApplier = new FirewallRulesApplier(this.mCtx, firewall);
            this.mDomainFilter = new DomainFilter(this.mCtx, this.mFirewallRulesApplier);
        }

        public Context getContext() {
            return this.mCtx;
        }

        public EdmStorageProvider getEDMStorageProvider() {
            return this.mEdmStorageProvider;
        }

        public EnterpriseDumpHelper getEnterpriseDumpHelper() {
            return this.mEnterpriseDumpHelper;
        }

        public FirewallRulesApplier getFirewallRulesApplier() {
            return this.mFirewallRulesApplier;
        }

        public DomainFilter getDomainFilter() {
            return this.mDomainFilter;
        }

        public EnterpriseDeviceManager getEDM() {
            return EnterpriseDeviceManager.getInstance(this.mCtx);
        }
    }

    public Firewall(Context context) {
        this(new Injector(context));
    }

    public Firewall(Injector injector) {
        this.mIsChainsCreated = false;
        this.mAreRulesReloaded = false;
        this.mInitializingIpTablesRulesLock = new Object();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.firewall.Firewall.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.intent.action.USER_REMOVED".equals(action) || "android.intent.action.USER_ADDED".equals(action)) {
                    Firewall.this.onUserHandle(intent);
                }
            }
        };
        this.mUserReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.server.enterprise.firewall.Firewall.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Log.i(Firewall.TAG, "onReceive() - " + intent);
                String action = intent.getAction();
                if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                    Firewall.this.packageAdded(context, intent);
                } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                    Firewall.this.packageRemoved(context, intent);
                }
            }
        };
        this.mPackageFilterReceiver = broadcastReceiver2;
        injector.initInjector(this);
        this.mInjector = injector;
        Context context = injector.getContext();
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mEdmStorageProvider = injector.getEDMStorageProvider();
        this.mFirewallRulesApplier = injector.getFirewallRulesApplier();
        this.mDomainFilter = injector.getDomainFilter();
        this.mEnterpriseDumpHelper = injector.getEnterpriseDumpHelper();
        mHasIpv6FilterSupport = this.mFirewallRulesApplier.hasIpv6Support(FirewallDefinitions.Table.FILTER);
        getConnectivityManager().registerDefaultNetworkCallback(new NetworkCallback());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiverAsUser(broadcastReceiver2, UserHandle.ALL, intentFilter2, null, null);
        initializeIpTables();
    }

    public final ConnectivityManager getConnectivityManager() {
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
        }
        return this.mConnectivityManager;
    }

    public class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public NetworkCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            Firewall.this.initializeIpTables();
        }
    }

    public final void onUserHandle(final Intent intent) {
        new Thread(new Runnable() { // from class: com.android.server.enterprise.firewall.Firewall.3
            @Override // java.lang.Runnable
            public void run() {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra == -1) {
                    return;
                }
                String action = intent.getAction();
                Log.i(Firewall.TAG, "onUserHandle: Lock acquired. UserID received: " + intExtra + ". Got Action: " + action);
                Firewall.this.handleUserChains(Integer.valueOf(intExtra), action);
            }
        }).start();
    }

    public final void initializeIpTables() {
        Thread thread = new Thread(new Runnable() { // from class: com.android.server.enterprise.firewall.Firewall.4
            @Override // java.lang.Runnable
            public void run() {
                Log.i(Firewall.TAG, "initializeIpTables() ");
                synchronized (Firewall.this.mInitializingIpTablesRulesLock) {
                    if (!Firewall.this.mAreRulesReloaded) {
                        if (!Firewall.this.mIsChainsCreated) {
                            Firewall.this.createChains();
                        }
                        Firewall.this.mFirewallRulesApplier.flushAllChains(null);
                        Firewall.this.mFirewallRulesApplier.setRulesStatusAfterReboot();
                        Firewall.this.blockOrUnblockAll(true, -1);
                        Firewall.this.mFirewallRulesApplier.reloadIptablesRules();
                        Firewall.this.mFirewallRulesApplier.reloadDomainFilterOnIptablesRules();
                        Firewall.this.mAreRulesReloaded = true;
                        Firewall.this.blockOrUnblockAll(false, -1);
                        Log.d(Firewall.TAG, "initializeIpTables() - IP Tables initialization was done");
                    }
                }
            }
        });
        this.mInitIpTablesThread = thread;
        thread.start();
    }

    public final void blockOrUnblockAll(boolean z, int i) {
        Log.i(TAG, "blockOrUnblockAll(block = " + String.valueOf(z));
        Log.d(TAG, ", uid = " + String.valueOf(i) + ") ");
        ArrayList arrayList = new ArrayList();
        if (i == -1) {
            arrayList = this.mFirewallRulesApplier.getAllAdmins();
        } else {
            arrayList.add(Integer.valueOf(i));
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (intValue != 1000) {
                ContextInfo contextInfo = new ContextInfo(intValue);
                if (isFirewallEnabled(contextInfo)) {
                    enableOrDisableRuleAsAdmin(createDenyAllRule(Firewall.AddressType.IPV4, false), contextInfo, z);
                    enableOrDisableRuleAsAdmin(createDenyAllRule(Firewall.AddressType.IPV6, false), contextInfo, z);
                    if (UserHandle.getUserId(intValue) == 0 && adminHaveSystemRule(contextInfo)) {
                        enableOrDisableRuleAsAdmin(createDenyAllRule(Firewall.AddressType.IPV4, true), contextInfo, z);
                        enableOrDisableRuleAsAdmin(createDenyAllRule(Firewall.AddressType.IPV6, true), contextInfo, z);
                    }
                }
            }
        }
    }

    public final FirewallRule createDenyAllRule(Firewall.AddressType addressType, boolean z) {
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.DENY, addressType);
        firewallRule.setIpAddress("*");
        firewallRule.setPortNumber("*");
        if (z) {
            firewallRule.setApplication(new AppIdentity("android", (String) null));
        }
        return firewallRule;
    }

    public FirewallRule createPort53DenyRule(Firewall.AddressType addressType, AppIdentity appIdentity) {
        FirewallRule createDenyAllRule = createDenyAllRule(addressType, true);
        createDenyAllRule.setPortNumber("53");
        if ("*".equals(appIdentity.getPackageName())) {
            appIdentity = new AppIdentity("*", (String) null);
        }
        createDenyAllRule.setApplication(appIdentity);
        Log.d(TAG, "createPort53DenyRule(): package = " + String.valueOf(createDenyAllRule.getPackageName()));
        return createDenyAllRule;
    }

    public final void enableOrDisableRuleAsAdmin(FirewallRule firewallRule, ContextInfo contextInfo, boolean z) {
        FirewallResponse disableIpTablesRule;
        Log.i(TAG, "enableOrDisableRuleAsAdmin()");
        for (int i = 0; i < 5; i++) {
            if (z) {
                Log.i(TAG, "enableOrDisableRuleAsAdmin() - Block the traffic until applying rules process is finished");
                disableIpTablesRule = this.mFirewallRulesApplier.enableIptablesRule(firewallRule, true, contextInfo, false);
            } else {
                Log.i(TAG, "enableOrDisableRuleAsAdmin() - Applying rules process is finished. Unblock the traffic");
                disableIpTablesRule = this.mFirewallRulesApplier.disableIpTablesRule(firewallRule, contextInfo, false);
            }
            if (disableIpTablesRule != null && disableIpTablesRule.getResult() == FirewallResponse.Result.SUCCESS) {
                Log.d(TAG, "enableOrDisableRuleAsAdmin() - success");
                return;
            }
        }
    }

    public final boolean adminHaveSystemRule(ContextInfo contextInfo) {
        FirewallRule[] rules = getRules(contextInfo, 15, null);
        IPackageManager asInterface = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                for (FirewallRule firewallRule : rules) {
                    ApplicationInfo applicationInfo = asInterface.getApplicationInfo(firewallRule.getApplication().getPackageName(), 0L, UserHandle.getUserId(contextInfo.mCallerUid));
                    if (applicationInfo != null && 1000 == UserHandle.getAppId(applicationInfo.uid)) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                }
            } catch (RemoteException e) {
                Log.e(TAG, "adminHaveSystemRule() - Remote Exception on get getApplicationInfo", e);
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createChains() {
        synchronized (this.mInitializingIpTablesRulesLock) {
            if (!this.mIsChainsCreated) {
                try {
                    try {
                        try {
                            this.mIsChainsCreated = this.mFirewallRulesApplier.createIptablesChains(null);
                        } catch (SecurityException e) {
                            Log.e(TAG, "createIptablesChains(): SecurityException was thrown.", e);
                        }
                    } catch (NullPointerException e2) {
                        Log.e(TAG, "createIptablesChains(): NullPointerException was thrown.", e2);
                    }
                } catch (IOException e3) {
                    Log.e(TAG, "createIptablesChains(): IOException was thrown.", e3);
                } catch (IllegalArgumentException e4) {
                    Log.e(TAG, "createIptablesChains(): IllegalArgumentException was thrown.", e4);
                }
            }
        }
    }

    public final void handleUserChains(Integer num, String str) {
        synchronized (this.mInitializingIpTablesRulesLock) {
            try {
                try {
                    try {
                        try {
                            if (str.equals("android.intent.action.USER_REMOVED")) {
                                this.mFirewallRulesApplier.removeIptablesChains(num);
                                this.mFirewallRulesApplier.flushDomainChains(num, true);
                            } else if (str.equals("android.intent.action.USER_ADDED")) {
                                this.mFirewallRulesApplier.createIptablesChains(num);
                            }
                        } catch (NullPointerException e) {
                            Log.e(TAG, "handleUserChains(): NullPointerException was thrown." + e.getMessage());
                        }
                    } catch (SecurityException e2) {
                        Log.e(TAG, "handleUserChains(): SecurityException was thrown." + e2.getMessage());
                    }
                } catch (IOException e3) {
                    Log.e(TAG, "handleUserChains(): IOException was thrown." + e3.getMessage());
                }
            } catch (IllegalArgumentException e4) {
                Log.e(TAG, "handleUserChains(): IllegalArgumentException was thrown." + e4.getMessage());
            }
        }
    }

    public final synchronized FirewallResponse addRule(ContextInfo contextInfo, FirewallRule firewallRule) {
        int i = contextInfo.mCallerUid;
        FirewallResponse validateFirewallRule = FirewallRuleValidator.validateFirewallRule(firewallRule);
        if (validateFirewallRule.getResult() == FirewallResponse.Result.FAILED) {
            Log.i(TAG, "addRule() - Invalid Firewall Rule");
            return validateFirewallRule;
        }
        if (!TextUtils.isEmpty(firewallRule.getApplication().getSignature())) {
            Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, UserHandle.getUserId(i));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!Utils.comparePackageSignature(createContextAsUser, firewallRule.getApplication().getPackageName(), firewallRule.getApplication().getSignature())) {
                    Log.i(TAG, "addRule() - Signature mismatch");
                    return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Given signature does not match with the application.");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (Firewall.AddressType.IPV6.equals(firewallRule.getAddressType())) {
            FirewallRule.RuleType ruleType = firewallRule.getRuleType();
            if ((FirewallRule.RuleType.ALLOW.equals(ruleType) || FirewallRule.RuleType.DENY.equals(ruleType)) && !mHasIpv6FilterSupport) {
                Log.i(TAG, "addRule() - IPv6 not supported");
                return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.IPV6_NOT_SUPPORTED_ERROR, "This device does not have IPv6 support for this type of rule.");
            }
            if ((FirewallRule.RuleType.REDIRECT.equals(ruleType) || FirewallRule.RuleType.REDIRECT_EXCEPTION.equals(ruleType)) && !mHasIpv6NatSupport) {
                Log.i(TAG, "addRule() - IPv6 not supported");
                return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.IPV6_NOT_SUPPORTED_ERROR, "This device does not have IPv6 support for this type of rule.");
            }
        }
        if (FirewallUtils.isRuleInDb(firewallRule, i, this.mEdmStorageProvider)) {
            Log.i(TAG, "addRule() - This rule is already in the database");
            return new FirewallResponse(FirewallResponse.Result.NO_CHANGES, FirewallResponse.ErrorCode.NO_ERROR, "The specified rule is already in the database.");
        }
        if (isFirewallEnabled(contextInfo)) {
            firewallRule.setStatus(FirewallRule.Status.PENDING);
        }
        long insert = this.mEdmStorageProvider.insert("FirewallRule", FirewallUtils.getContentValuesFromRule(firewallRule, i));
        if (insert == -1) {
            Log.e(TAG, "addRule() - Failed to add rule in the database");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to add/update rule in the database.");
        }
        firewallRule.setId((int) insert);
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The rule(s) was successfully added/updated.");
    }

    public synchronized FirewallResponse[] addRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) {
        ContextInfo enforceFirewallPermission = enforceFirewallPermission(contextInfo);
        Log.i(TAG, "addRules()");
        if (firewallRuleArr != null && firewallRuleArr.length != 0) {
            JSONArray jSONArray = new JSONArray();
            FirewallResponse[] firewallResponseArr = new FirewallResponse[firewallRuleArr.length];
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < firewallRuleArr.length; i++) {
                setRulesProperties(firewallRuleArr[i], jSONArray);
                FirewallResponse addRule = addRule(enforceFirewallPermission, firewallRuleArr[i]);
                firewallResponseArr[i] = addRule;
                if (FirewallResponse.Result.SUCCESS.equals(addRule.getResult())) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            boolean z = true;
            KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_SECURE_NETWORK", 1, "addRules");
            knoxAnalyticsData.setUserTypeProperty(enforceFirewallPermission.mContainerId);
            knoxAnalyticsData.setProperty("rules", "\"" + jSONArray.toString() + "\"");
            KnoxAnalytics.log(knoxAnalyticsData);
            int size = arrayList.size();
            FirewallRule[] firewallRuleArr2 = new FirewallRule[size];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                firewallRuleArr2[i2] = firewallRuleArr[((Integer) arrayList.get(i2)).intValue()];
            }
            FirewallResponse[] firewallResponseArr2 = new FirewallResponse[arrayList.size()];
            if (isFirewallEnabled(enforceFirewallPermission)) {
                firewallResponseArr2 = enableFirewallRules(enforceFirewallPermission, firewallRuleArr2);
                if (UserHandle.getUserId(enforceFirewallPermission.mCallerUid) == 0 && shouldApplyExemptRules(firewallRuleArr2)) {
                    this.mFirewallRulesApplier.addOrRemoveExemptRules(true, enforceFirewallPermission);
                }
                if (UserHandle.getUserId(enforceFirewallPermission.mCallerUid) == 0 && shouldApplyKGExemptRule(firewallRuleArr2)) {
                    this.mFirewallRulesApplier.addOrRemoveKGExemptRule(true, enforceFirewallPermission);
                }
                for (int i3 = 0; i3 < size; i3++) {
                    if (shouldApplyIcmpAllowRule(firewallRuleArr2[i3])) {
                        this.mFirewallRulesApplier.addOrRemoveIcmpAllowRule(true);
                    }
                }
            } else {
                z = false;
            }
            if (!z) {
                return firewallResponseArr;
            }
            FirewallResponse[] firewallResponseArr3 = new FirewallResponse[firewallRuleArr.length];
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                if (FirewallResponse.Result.SUCCESS.equals(firewallResponseArr2[i4].getResult())) {
                    if (FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR.equals(firewallResponseArr2[i4].getErrorCode())) {
                        firewallResponseArr3[((Integer) arrayList.get(i4)).intValue()] = new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR, "The rule(s) was successfully added/updated. / " + firewallResponseArr2[i4].getMessage());
                    } else {
                        firewallResponseArr3[((Integer) arrayList.get(i4)).intValue()] = new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The rule(s) was successfully added/updated. / " + firewallResponseArr2[i4].getMessage());
                    }
                } else {
                    firewallResponseArr3[((Integer) arrayList.get(i4)).intValue()] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, "The rule(s) was successfully added/updated. / " + firewallResponseArr2[i4].getMessage());
                }
            }
            for (int i5 = 0; i5 < firewallRuleArr.length; i5++) {
                if (!FirewallResponse.Result.SUCCESS.equals(firewallResponseArr[i5].getResult())) {
                    if (FirewallResponse.Result.NO_CHANGES.equals(firewallResponseArr[i5].getResult())) {
                        firewallResponseArr3[i5] = firewallResponseArr[i5];
                    } else {
                        firewallResponseArr3[i5] = new FirewallResponse(FirewallResponse.Result.FAILED, firewallResponseArr[i5].getErrorCode(), "Failed to add/update rule in the database. / " + firewallResponseArr[i5].getMessage());
                    }
                }
            }
            return firewallResponseArr3;
        }
        return null;
    }

    public synchronized FirewallResponse[] clearRules(ContextInfo contextInfo, int i) {
        FirewallResponse[] firewallResponseArr;
        ContextInfo enforceFirewallPermission = enforceFirewallPermission(contextInfo);
        Log.i(TAG, "clearRules()");
        firewallResponseArr = new FirewallResponse[FirewallRule.RuleType.values().length];
        firewallResponseArr[0] = clearRuleByType(FirewallRule.RuleType.ALLOW, i, enforceFirewallPermission);
        firewallResponseArr[1] = clearRuleByType(FirewallRule.RuleType.DENY, i, enforceFirewallPermission);
        firewallResponseArr[2] = clearRuleByType(FirewallRule.RuleType.REDIRECT, i, enforceFirewallPermission);
        firewallResponseArr[3] = clearRuleByType(FirewallRule.RuleType.REDIRECT_EXCEPTION, i, enforceFirewallPermission);
        return firewallResponseArr;
    }

    public final synchronized FirewallResponse clearRuleByType(FirewallRule.RuleType ruleType, int i, ContextInfo contextInfo) {
        int i2;
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if (ruleType == FirewallRule.RuleType.DENY) {
            i2 = 2;
        } else if (ruleType == FirewallRule.RuleType.ALLOW) {
            i2 = 1;
        } else if (ruleType == FirewallRule.RuleType.REDIRECT) {
            i2 = 4;
        } else {
            i2 = ruleType == FirewallRule.RuleType.REDIRECT_EXCEPTION ? 8 : 0;
        }
        if ((i & i2) != 0) {
            if (isFirewallEnabled(contextInfo)) {
                if (i2 == 2) {
                    this.mFirewallRulesApplier.clearIcmpAllowRule();
                }
                if (i2 == 2 && UserHandle.getUserId(contextInfo.mCallerUid) == 0) {
                    this.mFirewallRulesApplier.addOrRemoveExemptRules(false, contextInfo);
                    this.mFirewallRulesApplier.addOrRemoveKGExemptRule(false, contextInfo);
                }
                if (!this.mFirewallRulesApplier.flushChain(ruleType, Integer.valueOf(userId))) {
                    Log.e(TAG, "clearRuleByType() - Failed to disable firewall rules");
                    return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, " failed to disable. Error: ");
                }
                if (i2 == 1 && this.mFirewallRulesApplier.isIcmpAllowRuleApplied()) {
                    this.mFirewallRulesApplier.reinsertIcmpAllowRule();
                }
                if (i2 == 1 && (i & 2) == 0 && UserHandle.getUserId(contextInfo.mCallerUid) == 0) {
                    if (this.mFirewallRulesApplier.isExemptRulesApplied()) {
                        this.mFirewallRulesApplier.reinsertExemptRules(contextInfo);
                    }
                    if (this.mFirewallRulesApplier.isKGExemptRuleApplied()) {
                        this.mFirewallRulesApplier.reinsertKGExemptRule(contextInfo);
                    }
                }
            }
            return clearFirewallRulesFromDb(ruleType, contextInfo);
        }
        return new FirewallResponse(FirewallResponse.Result.NO_CHANGES, FirewallResponse.ErrorCode.NO_ERROR, "Clear was not requested for this RuleType.");
    }

    public final synchronized FirewallResponse clearFirewallRulesFromDb(FirewallRule.RuleType ruleType, ContextInfo contextInfo) {
        Log.i(TAG, "clearFirewallRulesFromDb()");
        if (ruleType == null) {
            Log.i(TAG, "clearFirewallRulesFromDb() - Rule type is null");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "The specified package name is not installed.");
        }
        int i = contextInfo.mCallerUid;
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("ruleType", ruleType.toString());
        int delete = this.mEdmStorageProvider.delete("FirewallRule", contentValues);
        if (delete == -1) {
            Log.e(TAG, "clearFirewallRulesFromDb() - Failed to clear rules from database");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to clear rules from database.");
        }
        if (delete == 0) {
            Log.i(TAG, "clearFirewallRulesFromDb() - Rules already cleared");
            return new FirewallResponse(FirewallResponse.Result.NO_CHANGES, FirewallResponse.ErrorCode.NO_ERROR, "The rules are already cleared.");
        }
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "Rules successfully cleared.");
    }

    public final synchronized FirewallResponse deleteFirewallRuleFromDb(ContentValues contentValues) {
        Log.i(TAG, "deleteFirewallRuleFromDb()");
        int delete = this.mEdmStorageProvider.delete("FirewallRule", contentValues);
        if (delete == -1) {
            Log.e(TAG, "deleteFirewallRuleFromDb() - Failed to remove rule from database");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to remove/update rule from the database.");
        }
        if (delete == 0) {
            Log.e(TAG, "deleteFirewallRuleFromDb() - Firewall rule not found");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, "The specified FirewallRule was not found.");
        }
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The rule was successfuly removed/updated.");
    }

    public final synchronized FirewallResponse[] enableFirewallRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) {
        ContextInfo enforceFirewallPermission = enforceFirewallPermission(contextInfo);
        Log.i(TAG, "enableFirewallRules()");
        if (firewallRuleArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < firewallRuleArr.length; i++) {
            if (!FirewallUtils.verifyPackageName(firewallRuleArr[i].getApplication().getPackageName(), UserHandle.getUserId(enforceFirewallPermission.mCallerUid))) {
                this.mFirewallRulesApplier.updateStatusOnDB(firewallRuleArr[i], FirewallRule.Status.DISABLED, enforceFirewallPermission);
            } else {
                arrayList.add(Integer.valueOf(i));
            }
        }
        FirewallRule[] firewallRuleArr2 = new FirewallRule[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            firewallRuleArr2[i2] = firewallRuleArr[((Integer) arrayList.get(i2)).intValue()];
        }
        Log.i(TAG, "enableFirewallRules()");
        return this.mFirewallRulesApplier.changeRuleStatus(firewallRuleArr2, enforceFirewallPermission, true);
    }

    public synchronized FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceFirewallPermission = enforceFirewallPermission(contextInfo);
        if (z) {
            return enableFirewall(enforceFirewallPermission);
        }
        return disableFirewall(enforceFirewallPermission);
    }

    public boolean isFirewallEnabled(ContextInfo contextInfo) {
        ContentValues contentValues = new ContentValues();
        int callingOrUserUid = Utils.getCallingOrUserUid(contextInfo);
        int userId = UserHandle.getUserId(callingOrUserUid);
        contentValues.put("adminUid", Integer.valueOf(callingOrUserUid));
        contentValues.put("userID", Integer.valueOf(userId));
        List values = this.mEdmStorageProvider.getValues("FirewallStatus", EdmStorageDefs.FIREWALL_POLICY_STATUS_COLUMNS, contentValues);
        return (values == null || values.isEmpty()) ? false : true;
    }

    public final synchronized FirewallResponse enableFirewall(ContextInfo contextInfo) {
        Log.i(TAG, "enableFirewall()");
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        int firewallOwner = getFirewallOwner(userId);
        if (firewallOwner != -1 && firewallOwner != i) {
            Log.i(TAG, "enableFirewall() - Failed! NOT_AUTHORIZED_ERROR ADMINUID_IS_NOT_THE_OWNER");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR, "This administrator can't execute this operation because he is not the owner.");
        }
        if (firewallOwner == -1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userID", Integer.valueOf(userId));
            if (this.mEdmStorageProvider.insert("FirewallStatus", contentValues) == -1) {
                Log.e(TAG, "enableFirewall() - Failed! DATABASE_ERROR FAILED_TO_ENABLE_FIREWALL_IN_THE_DATABASE");
                return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Failed to enable Firewall in the database.");
            }
        }
        FirewallRule[] rules = getRules(contextInfo, 15, null);
        if (rules != null && rules.length > 0) {
            FirewallResponse[] enableFirewallRules = enableFirewallRules(contextInfo, rules);
            StringBuilder sb = new StringBuilder();
            boolean z = false;
            boolean z2 = false;
            for (int i2 = 0; i2 < enableFirewallRules.length; i2++) {
                if (enableFirewallRules[i2].getResult().equals(FirewallResponse.Result.SUCCESS)) {
                    if (FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR.equals(enableFirewallRules[i2].getErrorCode())) {
                        sb.append("Rule with Id = " + rules[i2].getId() + " Rule not applied to INPUT chain for Direction.All." + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                        z2 = true;
                    } else {
                        sb.append("Rule with Id = " + rules[i2].getId() + "  successfully enabled.\n" + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    }
                } else if (enableFirewallRules[i2].getResult().equals(FirewallResponse.Result.FAILED)) {
                    sb.append("Rule with Id =  " + rules[i2].getId() + " failed to enable. Error: " + enableFirewallRules[i2].getMessage() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    z = true;
                }
            }
            if (z) {
                Log.e(TAG, "enableFirewall() - Failed to enable Firewall");
                return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, sb.toString());
            }
            if (UserHandle.getUserId(contextInfo.mCallerUid) == 0 && shouldApplyExemptRules(rules)) {
                this.mFirewallRulesApplier.addOrRemoveExemptRules(true, contextInfo);
            }
            if (UserHandle.getUserId(contextInfo.mCallerUid) == 0 && shouldApplyKGExemptRule(rules)) {
                this.mFirewallRulesApplier.addOrRemoveKGExemptRule(true, contextInfo);
            }
            for (FirewallRule firewallRule : rules) {
                if (shouldApplyIcmpAllowRule(firewallRule)) {
                    this.mFirewallRulesApplier.addOrRemoveIcmpAllowRule(true);
                }
            }
            Log.d(TAG, "enableFirewall() - Firewall successfully enabled");
            if (z2) {
                return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR, sb.toString());
            }
            return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, sb.toString());
        }
        Log.d(TAG, "enableFirewall() - Firewall successfully enabled (no rules)");
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The firewall was successfully enabled.");
    }

    public final synchronized FirewallResponse disableFirewall(ContextInfo contextInfo) {
        Log.i(TAG, "disableFirewall()");
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (getFirewallOwner(userId) != i) {
            Log.e(TAG, "disableFirewall() - FAILED! NOT_AUTHORIZED_ERROR ADMINUID_IS_NOT_THE_OWNER");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR, "This administrator can't execute this operation because he is not the owner.");
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("userID", Integer.valueOf(userId));
        if (this.mEdmStorageProvider.delete("FirewallStatus", contentValues) == 0) {
            Log.e(TAG, "disableFirewall() - FAILED! DATABASE_ERROR FAIL_TO_DISABLE_FIREWALL_IN_THE_DATABASE");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.DATABASE_ERROR, "Fail to disable Firewall in the database.");
        }
        FirewallRule[] rules = getRules(contextInfo, 15, null);
        if (rules != null && rules.length > 0) {
            StringBuilder sb = new StringBuilder();
            FirewallResponse[] firewallResponseArr = new FirewallResponse[rules.length];
            if (!this.mFirewallRulesApplier.flushAllChains(Integer.valueOf(userId))) {
                for (FirewallRule firewallRule : rules) {
                    sb.append("Rule with Id = ");
                    sb.append(firewallRule.getId());
                    sb.append(" failed to disable. Error: ");
                    sb.append("Unexpected error");
                }
                Log.e(TAG, "disableFirewall() - Failed to disable Firewall");
                return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.UNEXPECTED_ERROR, sb.toString());
            }
            for (FirewallRule firewallRule2 : rules) {
                this.mFirewallRulesApplier.updateStatusOnDB(firewallRule2, FirewallRule.Status.DISABLED, contextInfo);
            }
            for (FirewallRule firewallRule3 : rules) {
                sb.append("Rule with Id = ");
                sb.append(firewallRule3.getId());
                sb.append(" successfully disabled.\n");
            }
            if (UserHandle.getUserId(contextInfo.mCallerUid) == 0) {
                this.mFirewallRulesApplier.addOrRemoveExemptRules(false, contextInfo);
                this.mFirewallRulesApplier.addOrRemoveKGExemptRule(false, contextInfo);
            }
            return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, sb.toString());
        }
        Log.d(TAG, "disableFirewall() - Firewall successfully disabled");
        return new FirewallResponse(FirewallResponse.Result.SUCCESS, FirewallResponse.ErrorCode.NO_ERROR, "The firewall was successfully disabled.");
    }

    public int getFirewallOwner(int i) {
        Integer asInteger;
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", Integer.valueOf(i));
        List values = this.mEdmStorageProvider.getValues("FirewallStatus", EdmStorageDefs.FIREWALL_POLICY_STATUS_COLUMNS, contentValues);
        if (values == null || values.isEmpty() || (asInteger = ((ContentValues) values.get(0)).getAsInteger("adminUid")) == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public final ContextInfo enforceFirewallPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
    }

    public FirewallRule[] getRules(ContextInfo contextInfo, int i, String str) {
        int i2;
        ContextInfo enforceFirewallPermission = enforceFirewallPermission(contextInfo);
        FirewallRule[] ruleByType = getRuleByType(FirewallRule.RuleType.ALLOW, i, enforceFirewallPermission, str);
        FirewallRule[] ruleByType2 = getRuleByType(FirewallRule.RuleType.DENY, i, enforceFirewallPermission, str);
        FirewallRule[] ruleByType3 = getRuleByType(FirewallRule.RuleType.REDIRECT, i, enforceFirewallPermission, str);
        FirewallRule[] ruleByType4 = getRuleByType(FirewallRule.RuleType.REDIRECT_EXCEPTION, i, enforceFirewallPermission, str);
        int i3 = 0;
        int length = ruleByType != null ? ruleByType.length + 0 : 0;
        if (ruleByType2 != null) {
            length += ruleByType2.length;
        }
        if (ruleByType3 != null) {
            length += ruleByType3.length;
        }
        if (ruleByType4 != null) {
            length += ruleByType4.length;
        }
        FirewallRule[] firewallRuleArr = new FirewallRule[length];
        if (ruleByType != null) {
            int i4 = 0;
            i2 = 0;
            while (i4 < ruleByType.length) {
                firewallRuleArr[i2] = ruleByType[i4];
                i4++;
                i2++;
            }
        } else {
            i2 = 0;
        }
        if (ruleByType2 != null) {
            int i5 = 0;
            while (i5 < ruleByType2.length) {
                firewallRuleArr[i2] = ruleByType2[i5];
                i5++;
                i2++;
            }
        }
        if (ruleByType3 != null) {
            int i6 = 0;
            while (i6 < ruleByType3.length) {
                firewallRuleArr[i2] = ruleByType3[i6];
                i6++;
                i2++;
            }
        }
        if (ruleByType4 != null) {
            while (i3 < ruleByType4.length) {
                firewallRuleArr[i2] = ruleByType4[i3];
                i3++;
                i2++;
            }
        }
        return firewallRuleArr;
    }

    public final FirewallRule[] getRuleByType(FirewallRule.RuleType ruleType, int i, ContextInfo contextInfo, String str) {
        int i2;
        int i3 = contextInfo.mCallerUid;
        if (ruleType == FirewallRule.RuleType.ALLOW) {
            i2 = 1;
        } else if (ruleType == FirewallRule.RuleType.DENY) {
            i2 = 2;
        } else if (ruleType == FirewallRule.RuleType.REDIRECT) {
            i2 = 4;
        } else {
            i2 = ruleType == FirewallRule.RuleType.REDIRECT_EXCEPTION ? 8 : 0;
        }
        ContentValues contentValues = new ContentValues();
        if (i3 != 1000) {
            contentValues.put("adminUid", Integer.valueOf(i3));
        }
        if (str != null) {
            contentValues.put("status", str);
        }
        if ((i & i2) == 0) {
            return null;
        }
        contentValues.put("ruleType", ruleType.toString());
        return FirewallUtils.getRuleFromContentValues(this.mEdmStorageProvider.getValues("FirewallRule", EdmStorageDefs.FIREWALL_RULE_COLUMNS, contentValues), ruleType);
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = this.mInjector.getEDM();
        }
        return this.mEDM;
    }

    public String[] listIptablesRules(ContextInfo contextInfo) {
        return this.mFirewallRulesApplier.listIptablesRules(enforceFirewallPermission(contextInfo));
    }

    public synchronized FirewallResponse[] removeRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) {
        ContextInfo enforceFirewallPermission = enforceFirewallPermission(contextInfo);
        Log.i(TAG, "removeRules()");
        if (firewallRuleArr == null) {
            Log.i(TAG, "removeRules() - No rule specified");
            return null;
        }
        FirewallResponse[] changeRuleStatus = this.mFirewallRulesApplier.changeRuleStatus(firewallRuleArr, enforceFirewallPermission, false);
        FirewallResponse[] firewallResponseArr = new FirewallResponse[firewallRuleArr.length];
        for (int i = 0; i < firewallRuleArr.length; i++) {
            FirewallResponse firewallResponse = changeRuleStatus[i];
            if (firewallResponse != null && firewallResponse.getResult().equals(FirewallResponse.Result.SUCCESS)) {
                firewallRuleArr[i].setStatus(FirewallRule.Status.DISABLED);
            }
            FirewallResponse removeRule = removeRule(enforceFirewallPermission, firewallRuleArr[i]);
            if (removeRule != null && !removeRule.getResult().equals(FirewallResponse.Result.SUCCESS)) {
                FirewallResponse.Result result = FirewallResponse.Result.FAILED;
                FirewallResponse.ErrorCode errorCode = removeRule.getErrorCode();
                StringBuilder sb = new StringBuilder();
                sb.append(changeRuleStatus[i] == null ? "" : changeRuleStatus[i].getMessage() + "/");
                sb.append(removeRule.getMessage());
                firewallResponseArr[i] = new FirewallResponse(result, errorCode, sb.toString());
            } else {
                firewallResponseArr[i] = removeRule;
            }
        }
        for (FirewallRule firewallRule : firewallRuleArr) {
            if (shouldApplyIcmpAllowRule(firewallRule)) {
                this.mFirewallRulesApplier.addOrRemoveIcmpAllowRule(false);
            }
        }
        FirewallRule[] rules = getRules(enforceFirewallPermission, 2, null);
        if (UserHandle.getUserId(enforceFirewallPermission.mCallerUid) == 0 && !shouldApplyExemptRules(rules)) {
            this.mFirewallRulesApplier.addOrRemoveExemptRules(false, enforceFirewallPermission);
        }
        if (UserHandle.getUserId(enforceFirewallPermission.mCallerUid) == 0 && !shouldApplyKGExemptRule(rules)) {
            this.mFirewallRulesApplier.addOrRemoveKGExemptRule(false, enforceFirewallPermission);
        }
        return firewallResponseArr;
    }

    public final synchronized FirewallResponse removeRule(ContextInfo contextInfo, FirewallRule firewallRule) {
        Log.i(TAG, "removeRule()");
        int i = contextInfo.mCallerUid;
        FirewallResponse validateFirewallRule = FirewallRuleValidator.validateFirewallRule(firewallRule);
        if (validateFirewallRule.getResult() == FirewallResponse.Result.FAILED) {
            Log.i(TAG, "removeRule() - Invalid Firewall Rule");
            return validateFirewallRule;
        }
        if (!TextUtils.isEmpty(firewallRule.getApplication().getSignature())) {
            Context createContextAsUser = Utils.createContextAsUser(this.mContext, "android", 0, UserHandle.getUserId(i));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!Utils.comparePackageSignature(createContextAsUser, firewallRule.getApplication().getPackageName(), firewallRule.getApplication().getSignature())) {
                    Log.i(TAG, "removeRule() - Signature mismatch");
                    return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR, "Given signature does not match with the application.");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (FirewallUtils.isRuleEnabled(firewallRule, i, this.mEdmStorageProvider)) {
            Log.i(TAG, "removeRule() - Failed to remove rule. The rule must be disabled.");
            return new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, "Disable the rule before remove it.");
        }
        return deleteFirewallRuleFromDb(FirewallUtils.getContentValuesFromRule(firewallRule, i));
    }

    public void populateDomainFilterBrokenRules(ContextInfo contextInfo, List list, int i) {
        this.mDomainFilter.populateDomainFilterBrokenRules(enforceFirewallPermission(contextInfo), list, i);
    }

    public FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo) {
        return this.mDomainFilter.removeDomainFilterRules(enforceFirewallPermission(contextInfo), DomainFilterRule.CLEAR_ALL);
    }

    public FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i) {
        return this.mDomainFilter.addDomainFilterRules(enforceFirewallPermission(contextInfo), i);
    }

    public FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i) {
        return this.mDomainFilter.removeDomainFilterRules(enforceFirewallPermission(contextInfo), i);
    }

    public List getDomainFilterRules(ContextInfo contextInfo, List list, int i) {
        ContextInfo enforceFirewallPermission = enforceFirewallPermission(contextInfo);
        if (i == -1) {
            return this.mDomainFilter.getDomainFilterRules(enforceFirewallPermission, list);
        }
        return this.mDomainFilter.getDomainFilterRules(enforceFirewallPermission, list, i);
    }

    public synchronized FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z) {
        return this.mDomainFilter.enableDomainFilterReport(enforceFirewallPermission(contextInfo), z);
    }

    public boolean isDomainFilterReportEnabled(ContextInfo contextInfo) {
        return this.mDomainFilter.isDomainFilterReportEnabled(enforceFirewallPermission(contextInfo));
    }

    public List getDomainFilterReport(ContextInfo contextInfo, List list) {
        return this.mDomainFilter.getDomainFilterReport(enforceFirewallPermission(contextInfo), list);
    }

    public synchronized FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z) {
        return this.mDomainFilter.enableDomainFilterOnIptables(enforceFirewallPermission(contextInfo), z);
    }

    public boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) {
        return this.mDomainFilter.isDomainFilterOnIptablesEnabled(enforceFirewallPermission(contextInfo));
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
        Log.i(TAG, "onAdminAdded(uid = " + i + ")");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        Log.i(TAG, "onAdminRemoved(uid = " + i + ")");
        DomainFilter domainFilter = this.mDomainFilter;
        if (domainFilter != null) {
            domainFilter.adminRemoved(i);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
        Log.i(TAG, "onPreAdminRemoval(uid = " + i + ")");
        ContextInfo contextInfo = new ContextInfo(i);
        if (getFirewallOwner(UserHandle.getUserId(i)) == i) {
            blockOrUnblockAll(false, i);
            this.mFirewallRulesApplier.deleteRulesFromAdmin(contextInfo);
        }
        DomainFilter domainFilter = this.mDomainFilter;
        if (domainFilter != null) {
            domainFilter.onPreAdminRemoval(i);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        Log.i(TAG, "systemReady()");
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Firewall");
            return;
        }
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "FirewallRule", new String[]{"adminUid", "ruleType", "status", "addressType", "ipAddress", "portNumber", "portLocation", "packageName", "signature", "networkInterface", "targetIpAddress", "targetPortNumber", "direction", "protocol"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "DomainFilterTable", new String[]{"adminUid", "packageName", "signature", "dns1", "dns2", "networkId"});
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "DomainFilterListTable", new String[]{"adminUid", "packageName", "typeList", "domain"});
    }

    public String getDefaulCaptivePortalURL() {
        return this.mDomainFilter.getDefaulCaptivePortalURL();
    }

    public boolean shouldApplyExemptRules(FirewallRule[] firewallRuleArr) {
        if (firewallRuleArr == null) {
            return false;
        }
        for (FirewallRule firewallRule : firewallRuleArr) {
            if (FirewallRule.RuleType.DENY.equals(firewallRule.getRuleType()) && (("*".equals(firewallRule.getPackageName()) || FirewallDefinitions.EXEMPT_PACKAGE_LIST.keySet().contains(firewallRule.getPackageName())) && !FirewallRule.Status.DISABLED.equals(firewallRule.getStatus()) && !Firewall.Direction.FORWARD.equals(firewallRule.getDirection()))) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldApplyKGExemptRule(FirewallRule[] firewallRuleArr) {
        if (firewallRuleArr == null) {
            return false;
        }
        for (FirewallRule firewallRule : firewallRuleArr) {
            if (FirewallRule.RuleType.DENY.equals(firewallRule.getRuleType()) && (("*".equals(firewallRule.getPackageName()) || KnoxCustomManagerService.KG_PKG_NAME.equals(firewallRule.getPackageName())) && FirewallUtils.isKGExemptRuleRequired() && !FirewallRule.Status.DISABLED.equals(firewallRule.getStatus()) && !Firewall.Direction.FORWARD.equals(firewallRule.getDirection()))) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldApplyIcmpAllowRule(FirewallRule firewallRule) {
        if (firewallRule != null && FirewallRule.RuleType.DENY.equals(firewallRule.getRuleType()) && "*".equals(firewallRule.getIpAddress())) {
            return Firewall.Direction.ALL.equals(firewallRule.getDirection()) || Firewall.Direction.INPUT.equals(firewallRule.getDirection());
        }
        return false;
    }

    public final void setRulesProperties(FirewallRule firewallRule, JSONArray jSONArray) {
        if (firewallRule == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("pN", firewallRule.getApplication().getPackageName());
            jSONObject.put("adrTp", getAddressTypePropertyToLog(firewallRule.getAddressType()));
            jSONObject.put("netIt", getNetworkInterfacePropertyToLog(firewallRule.getNetworkInterface()));
            try {
                jSONObject.put("ptLc", getPortLocationPropertyToLog(firewallRule.getPortLocation()));
            } catch (UnsupportedOperationException e) {
                Log.e(TAG, "Failed to put parameter in json: " + e.getMessage());
            }
            jSONObject.put("prt", getProtocolPropertyToLog(firewallRule.getProtocol()));
            try {
                jSONObject.put("rlTp", getRuleTypePropertyToLog(firewallRule.getRuleType()));
            } catch (UnsupportedOperationException e2) {
                Log.e(TAG, "Failed to put parameter in json: " + e2.getMessage());
            }
            jSONArray.put(jSONObject);
        } catch (JSONException e3) {
            Log.e(TAG, "Failed to put rule in json: " + e3.getMessage());
        }
    }

    public final int getAddressTypePropertyToLog(Firewall.AddressType addressType) {
        if (addressType == null) {
            return -1;
        }
        int i = AbstractC14325.f1684xf6a73711[addressType.ordinal()];
        if (i != 1) {
            return i != 2 ? -1 : 1;
        }
        return 0;
    }

    public final int getNetworkInterfacePropertyToLog(Firewall.NetworkInterface networkInterface) {
        if (networkInterface == null) {
            return -1;
        }
        int i = AbstractC14325.f1685xc026b6c8[networkInterface.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return i != 3 ? -1 : 2;
        }
        return 1;
    }

    public final int getPortLocationPropertyToLog(Firewall.PortLocation portLocation) {
        if (portLocation == null) {
            return -1;
        }
        int i = AbstractC14325.f1686x28da11f3[portLocation.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return i != 3 ? -1 : 2;
        }
        return 1;
    }

    public final int getProtocolPropertyToLog(Firewall.Protocol protocol) {
        if (protocol == null) {
            return -1;
        }
        int i = AbstractC14325.f1687x367febb5[protocol.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return i != 3 ? -1 : 1;
        }
        return 2;
    }

    /* renamed from: com.android.server.enterprise.firewall.Firewall$5 */
    public abstract /* synthetic */ class AbstractC14325 {

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$AddressType */
        public static final /* synthetic */ int[] f1684xf6a73711;

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$NetworkInterface */
        public static final /* synthetic */ int[] f1685xc026b6c8;

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$PortLocation */
        public static final /* synthetic */ int[] f1686x28da11f3;

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$Protocol */
        public static final /* synthetic */ int[] f1687x367febb5;

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType */
        public static final /* synthetic */ int[] f1688x6178b957;

        static {
            int[] iArr = new int[FirewallRule.RuleType.values().length];
            f1688x6178b957 = iArr;
            try {
                iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1688x6178b957[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1688x6178b957[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1688x6178b957[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Firewall.Protocol.values().length];
            f1687x367febb5 = iArr2;
            try {
                iArr2[Firewall.Protocol.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1687x367febb5[Firewall.Protocol.TCP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f1687x367febb5[Firewall.Protocol.UDP.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr3 = new int[Firewall.PortLocation.values().length];
            f1686x28da11f3 = iArr3;
            try {
                iArr3[Firewall.PortLocation.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f1686x28da11f3[Firewall.PortLocation.LOCAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f1686x28da11f3[Firewall.PortLocation.REMOTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            int[] iArr4 = new int[Firewall.NetworkInterface.values().length];
            f1685xc026b6c8 = iArr4;
            try {
                iArr4[Firewall.NetworkInterface.ALL_NETWORKS.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f1685xc026b6c8[Firewall.NetworkInterface.MOBILE_DATA_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f1685xc026b6c8[Firewall.NetworkInterface.WIFI_DATA_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr5 = new int[Firewall.AddressType.values().length];
            f1684xf6a73711 = iArr5;
            try {
                iArr5[Firewall.AddressType.IPV4.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f1684xf6a73711[Firewall.AddressType.IPV6.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    public final int getRuleTypePropertyToLog(FirewallRule.RuleType ruleType) {
        if (ruleType == null) {
            return -1;
        }
        int i = AbstractC14325.f1688x6178b957[ruleType.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i != 3) {
            return i != 4 ? -1 : 3;
        }
        return 2;
    }

    public final FirewallRule[] getRulesByAdminUidAndPackage(int i, String str) {
        int i2;
        ContentValues contentValues = new ContentValues();
        contentValues.put("packageName", str);
        contentValues.put("adminUid", Integer.valueOf(i));
        List<ContentValues> values = this.mEdmStorageProvider.getValues("FirewallRule", EdmStorageDefs.FIREWALL_RULE_COLUMNS, contentValues);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (ContentValues contentValues2 : values) {
            if (contentValues2.containsKey("ruleType")) {
                int i3 = AbstractC14325.f1688x6178b957[FirewallRule.RuleType.valueOf(contentValues2.getAsString("ruleType")).ordinal()];
                if (i3 == 1) {
                    arrayList.add(contentValues2);
                } else if (i3 == 2) {
                    arrayList2.add(contentValues2);
                } else if (i3 == 3) {
                    arrayList3.add(contentValues2);
                } else if (i3 == 4) {
                    arrayList4.add(contentValues2);
                }
            }
        }
        FirewallRule[] firewallRuleArr = new FirewallRule[values.size()];
        FirewallRule[] ruleFromContentValues = FirewallUtils.getRuleFromContentValues(arrayList, FirewallRule.RuleType.ALLOW);
        int i4 = 0;
        if (ruleFromContentValues != null) {
            int i5 = 0;
            i2 = 0;
            while (i5 < ruleFromContentValues.length) {
                firewallRuleArr[i2] = ruleFromContentValues[i5];
                i5++;
                i2++;
            }
        } else {
            i2 = 0;
        }
        FirewallRule[] ruleFromContentValues2 = FirewallUtils.getRuleFromContentValues(arrayList2, FirewallRule.RuleType.DENY);
        if (ruleFromContentValues2 != null) {
            int i6 = 0;
            while (i6 < ruleFromContentValues2.length) {
                firewallRuleArr[i2] = ruleFromContentValues2[i6];
                i6++;
                i2++;
            }
        }
        FirewallRule[] ruleFromContentValues3 = FirewallUtils.getRuleFromContentValues(arrayList3, FirewallRule.RuleType.REDIRECT);
        if (ruleFromContentValues3 != null) {
            int i7 = 0;
            while (i7 < ruleFromContentValues3.length) {
                firewallRuleArr[i2] = ruleFromContentValues3[i7];
                i7++;
                i2++;
            }
        }
        FirewallRule[] ruleFromContentValues4 = FirewallUtils.getRuleFromContentValues(arrayList4, FirewallRule.RuleType.REDIRECT_EXCEPTION);
        if (ruleFromContentValues4 != null) {
            while (i4 < ruleFromContentValues4.length) {
                firewallRuleArr[i2] = ruleFromContentValues4[i4];
                i4++;
                i2++;
            }
        }
        return firewallRuleArr;
    }

    public final void packageAdded(Context context, Intent intent) {
        FirewallResponse enableIptablesRule;
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        if (intExtra == -1 || schemeSpecificPart == null) {
            Log.i(TAG, "packageAdded() - Received invalid user id orpackage name, can't retrieve application info");
            return;
        }
        String adminLUIDWhereIn = EdmStorageProviderBase.getAdminLUIDWhereIn(0, intExtra);
        ContentValues contentValues = new ContentValues();
        contentValues.put("packageName", schemeSpecificPart);
        contentValues.put(adminLUIDWhereIn, "#SelectClause#");
        HashSet hashSet = new HashSet(this.mEdmStorageProvider.getValues("FirewallRule", new String[]{"adminUid"}, contentValues));
        if (hashSet.isEmpty()) {
            return;
        }
        Log.d(TAG, "packageAdded() - " + schemeSpecificPart + " has firewall rules to enable");
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer asInteger = ((ContentValues) it.next()).getAsInteger("adminUid");
            if (asInteger != null) {
                ContextInfo contextInfo = new ContextInfo(asInteger.intValue());
                FirewallRule[] rulesByAdminUidAndPackage = getRulesByAdminUidAndPackage(asInteger.intValue(), schemeSpecificPart);
                Context createContextAsUser = Utils.createContextAsUser(context, "android", 0, intExtra);
                for (FirewallRule firewallRule : rulesByAdminUidAndPackage) {
                    String signature = firewallRule.getApplication().getSignature();
                    if ((TextUtils.isEmpty(signature) || Utils.comparePackageSignature(createContextAsUser, firewallRule.getApplication().getPackageName(), signature, intExtra)) && (enableIptablesRule = this.mFirewallRulesApplier.enableIptablesRule(firewallRule, true, contextInfo, false)) != null && enableIptablesRule.getResult() == FirewallResponse.Result.SUCCESS) {
                        this.mFirewallRulesApplier.updateStatusOnDB(firewallRule, FirewallRule.Status.ENABLED, contextInfo);
                        Log.d(TAG, "enableRuleAsAdmin() - success");
                    }
                }
            }
        }
    }

    public final void packageRemoved(Context context, Intent intent) {
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
        int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
        if (intExtra == -1 || schemeSpecificPart == null) {
            Log.i(TAG, "packageRemoved() - Received invalid user id orpackage name, can't retrieve application info");
            return;
        }
        String adminLUIDWhereIn = EdmStorageProviderBase.getAdminLUIDWhereIn(0, intExtra);
        ContentValues contentValues = new ContentValues();
        contentValues.put("packageName", schemeSpecificPart);
        contentValues.put(adminLUIDWhereIn, "#SelectClause#");
        HashSet hashSet = new HashSet(this.mEdmStorageProvider.getValues("FirewallRule", new String[]{"adminUid"}, contentValues));
        if (hashSet.isEmpty()) {
            return;
        }
        Log.d(TAG, "packageRemoved() - " + schemeSpecificPart + " has firewall rules to disable");
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer asInteger = ((ContentValues) it.next()).getAsInteger("adminUid");
            if (asInteger != null) {
                ContextInfo contextInfo = new ContextInfo(asInteger.intValue());
                FirewallRule[] rulesByAdminUidAndPackage = getRulesByAdminUidAndPackage(asInteger.intValue(), schemeSpecificPart);
                Context createContextAsUser = Utils.createContextAsUser(context, "android", 0, intExtra);
                for (FirewallRule firewallRule : rulesByAdminUidAndPackage) {
                    String signature = firewallRule.getApplication().getSignature();
                    if (TextUtils.isEmpty(signature) || Utils.comparePackageSignature(createContextAsUser, firewallRule.getApplication().getPackageName(), signature, intExtra)) {
                        firewallRule.setPackageUid(intExtra2);
                        FirewallResponse disableIpTablesRule = this.mFirewallRulesApplier.disableIpTablesRule(firewallRule, contextInfo, false);
                        if (disableIpTablesRule != null && disableIpTablesRule.getResult() == FirewallResponse.Result.SUCCESS) {
                            this.mFirewallRulesApplier.updateStatusOnDB(firewallRule, FirewallRule.Status.DISABLED, contextInfo);
                            Log.d(TAG, "disableIpTablesRule() - success");
                        }
                    }
                }
            }
        }
    }

    public boolean shouldBlockDownload(String str, String str2, int i) {
        enforceDownloadProvider();
        if (str == null) {
            Log.d(TAG, "shouldBlockDownload() false - package is null");
            return false;
        }
        try {
            URL url = new URL(str2);
            int port = url.getPort() != -1 ? url.getPort() : url.getDefaultPort();
            String[] strArr = {"*"};
            Iterator it = this.mEdmStorageProvider.getAdminUidListAsUser(UserHandle.getUserId(i)).iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (intValue != 1000) {
                    DownloadBlockStatus shouldBlockDownloadDomainFilter = shouldBlockDownloadDomainFilter(intValue, str, url.getHost());
                    if (shouldBlockDownloadDomainFilter.equals(DownloadBlockStatus.BLOCKED_BY_BLACKLIST_RULE)) {
                        return true;
                    }
                    if (this.mDomainFilter.isDomainFilterOnIptablesEnabled(new ContextInfo(intValue)) && shouldBlockDownloadDomainFilter.equals(DownloadBlockStatus.PASSED_BY_WHITELIST_RULE)) {
                        return false;
                    }
                    if (isFirewallEnabled(new ContextInfo(intValue))) {
                        List enabledFirewallRulesForPackage = getEnabledFirewallRulesForPackage(intValue, str);
                        if (enabledFirewallRulesForPackage.isEmpty()) {
                            continue;
                        } else {
                            if (strArr.length > 0 && "*".equals(strArr[0])) {
                                ArrayList arrayList = new ArrayList();
                                try {
                                    for (InetAddress inetAddress : InetAddress.getAllByName(url.getHost())) {
                                        arrayList.add(inetAddress.getHostAddress());
                                    }
                                } catch (UnknownHostException e) {
                                    Log.e(TAG, "shouldBlockDownload() - UnknownHostException was thrown.", e);
                                }
                                strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                            }
                            if (shouldBlockDownloadFirewall(strArr, String.valueOf(port), enabledFirewallRulesForPackage).equals(DownloadBlockStatus.BLOCKED_BY_BLACKLIST_RULE)) {
                                return true;
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
            return false;
        } catch (MalformedURLException e2) {
            Log.e(TAG, "shouldBlockDownload() false - MalformedURLException was thrown.", e2);
            return false;
        }
    }

    public final List getEnabledFirewallRulesForPackage(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("packageName", str);
        contentValues.put("status", FirewallRule.Status.ENABLED.toString());
        return this.mEdmStorageProvider.getValues("FirewallRule", EdmStorageDefs.FIREWALL_RULE_COLUMNS, contentValues);
    }

    public final DownloadBlockStatus shouldBlockDownloadFirewall(String[] strArr, String str, List list) {
        for (String str2 : strArr) {
            Firewall.AddressType addressType = str2.contains(XmlUtils.STRING_ARRAY_SEPARATOR) ? Firewall.AddressType.IPV6 : Firewall.AddressType.IPV4;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues.getAsString("ruleType").equals(FirewallRule.RuleType.ALLOW.toString())) {
                    FirewallRule firewallRule = FirewallUtils.getRuleFromContentValues(Arrays.asList(contentValues), FirewallRule.RuleType.ALLOW)[0];
                    if (firewallRule.getAddressType() == addressType && ("*".equals(firewallRule.getIpAddress()) || str2.equals(firewallRule.getIpAddress()) || matchIpIntoIpRangeRule(str2, firewallRule))) {
                        if ("*".equals(firewallRule.getPortNumber()) || str.equals(firewallRule.getPortNumber()) || matchPortIntoPortRangeRule(str, firewallRule)) {
                            if (Firewall.Protocol.ALL.equals(firewallRule.getProtocol()) || Firewall.Protocol.TCP.equals(firewallRule.getProtocol())) {
                                return DownloadBlockStatus.PASSED_BY_WHITELIST_RULE;
                            }
                        }
                    }
                }
            }
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it2.next();
                if (contentValues2.getAsString("ruleType").equals(FirewallRule.RuleType.DENY.toString())) {
                    FirewallRule firewallRule2 = FirewallUtils.getRuleFromContentValues(Arrays.asList(contentValues2), FirewallRule.RuleType.DENY)[0];
                    if (firewallRule2.getAddressType() == addressType && ("*".equals(firewallRule2.getIpAddress()) || str2.equals(firewallRule2.getIpAddress()) || matchIpIntoIpRangeRule(str2, firewallRule2))) {
                        if ("*".equals(firewallRule2.getPortNumber()) || str.equals(firewallRule2.getPortNumber()) || matchPortIntoPortRangeRule(str, firewallRule2)) {
                            if (Firewall.Protocol.ALL.equals(firewallRule2.getProtocol()) || Firewall.Protocol.TCP.equals(firewallRule2.getProtocol())) {
                                return DownloadBlockStatus.BLOCKED_BY_BLACKLIST_RULE;
                            }
                        }
                    }
                }
            }
        }
        return DownloadBlockStatus.PASSED_NORULE;
    }

    public final DownloadBlockStatus shouldBlockDownloadDomainFilter(int i, String str, String str2) {
        ContextInfo contextInfo = new ContextInfo(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List<DomainFilterRule> domainFilterRules = getDomainFilterRules(contextInfo, new ArrayList(Arrays.asList(str)), -1);
            if (domainFilterRules == null) {
                return DownloadBlockStatus.PASSED_NORULE;
            }
            for (DomainFilterRule domainFilterRule : domainFilterRules) {
                if ("*".equals(domainFilterRule.getApplication().getPackageName()) || str.equals(domainFilterRule.getApplication().getPackageName())) {
                    Iterator it = domainFilterRule.getAllowDomains().iterator();
                    while (it.hasNext()) {
                        if (matchDomains(str2, (String) it.next())) {
                            return DownloadBlockStatus.PASSED_BY_WHITELIST_RULE;
                        }
                    }
                    Iterator it2 = domainFilterRule.getDenyDomains().iterator();
                    while (it2.hasNext()) {
                        if (matchDomains(str2, (String) it2.next())) {
                            return DownloadBlockStatus.BLOCKED_BY_BLACKLIST_RULE;
                        }
                    }
                }
            }
            return DownloadBlockStatus.PASSED_NORULE;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean matchDomains(String str, String str2) {
        return Pattern.compile(str2.replace("*", ".*")).matcher(str).matches();
    }

    public final void enforceDownloadProvider() {
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if (packagesForUid != null && packagesForUid.length > 0) {
            for (int i = 0; i < packagesForUid.length; i++) {
                if (packagesForUid[i].equals("com.android.providers.downloads.ui") || packagesForUid[i].equals("com.android.providers.downloads")) {
                    return;
                }
            }
        }
        throw new SecurityException("Caller is not allowed to call this method");
    }

    public final boolean matchPortIntoPortRangeRule(String str, FirewallRule firewallRule) {
        if (firewallRule.getPortNumber().contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
            String[] split = firewallRule.getPortNumber().split(PackageManagerShellCommandDataLoader.STDIN_PATH);
            if (split.length == 2 && Integer.valueOf(str).intValue() > Integer.valueOf(split[0]).intValue() && Integer.valueOf(str).intValue() < Integer.valueOf(split[1]).intValue()) {
                return true;
            }
        }
        return false;
    }

    public final boolean matchIpIntoIpRangeRule(String str, FirewallRule firewallRule) {
        Firewall.AddressType addressType = str.contains(XmlUtils.STRING_ARRAY_SEPARATOR) ? Firewall.AddressType.IPV6 : Firewall.AddressType.IPV4;
        if (!firewallRule.getIpAddress().contains(PackageManagerShellCommandDataLoader.STDIN_PATH) || firewallRule.getAddressType() != addressType) {
            return false;
        }
        String[] split = firewallRule.getIpAddress().split(PackageManagerShellCommandDataLoader.STDIN_PATH);
        if (addressType == Firewall.AddressType.IPV4) {
            return compareIpSubdivisions(split[0].split("\\."), split[1].split("\\."), str.split("\\."), addressType);
        }
        split[0] = convertToIpv6CompleteForm(split[0]);
        split[1] = convertToIpv6CompleteForm(split[1]);
        return compareIpSubdivisions(split[0].split(XmlUtils.STRING_ARRAY_SEPARATOR), split[1].split(XmlUtils.STRING_ARRAY_SEPARATOR), convertToIpv6CompleteForm(str).split(XmlUtils.STRING_ARRAY_SEPARATOR), addressType);
    }

    public final String convertToIpv6CompleteForm(String str) {
        if (!str.contains("::")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if ("::".equals(str)) {
            for (int i = 1; i < 8; i++) {
                sb.append("0");
                if (i != 7) {
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                }
            }
            return sb.toString();
        }
        String[] split = str.split("::");
        int i2 = 0;
        if (str.charAt(0) == ':') {
            String[] split2 = split[1].split(XmlUtils.STRING_ARRAY_SEPARATOR);
            int length = 8 - split2.length;
            for (int i3 = 0; i3 < length; i3++) {
                sb.append("0:");
            }
            while (i2 < split2.length) {
                sb.append(split2[i2]);
                if (i2 != split2.length - 1) {
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                }
                i2++;
            }
            return sb.toString();
        }
        if (split.length == 2) {
            String[] split3 = split[0].split(XmlUtils.STRING_ARRAY_SEPARATOR);
            String[] split4 = split[1].split(XmlUtils.STRING_ARRAY_SEPARATOR);
            int length2 = (8 - split3.length) - split4.length;
            for (String str2 : split3) {
                sb.append(str2);
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
            }
            for (int i4 = 0; i4 < length2; i4++) {
                sb.append("0:");
            }
            while (i2 < split4.length) {
                sb.append(split4[i2]);
                if (i2 != split4.length - 1) {
                    sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
                }
                i2++;
            }
            return sb.toString();
        }
        String[] split5 = split[0].split(XmlUtils.STRING_ARRAY_SEPARATOR);
        int length3 = 8 - split5.length;
        for (String str3 : split5) {
            sb.append(str3 + XmlUtils.STRING_ARRAY_SEPARATOR);
        }
        while (i2 < length3) {
            sb.append("0");
            if (i2 != length3 - 1) {
                sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
            }
            i2++;
        }
        return sb.toString();
    }

    public final boolean compareIpSubdivisions(String[] strArr, String[] strArr2, String[] strArr3, Firewall.AddressType addressType) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < strArr3.length; i++) {
            int convertIpSectionToInt = convertIpSectionToInt(strArr[i], addressType);
            int convertIpSectionToInt2 = convertIpSectionToInt(strArr2[i], addressType);
            int convertIpSectionToInt3 = convertIpSectionToInt(strArr3[i], addressType);
            if (convertIpSectionToInt3 > convertIpSectionToInt && !z) {
                z = true;
            }
            if (convertIpSectionToInt3 < convertIpSectionToInt && !z) {
                return false;
            }
            if (convertIpSectionToInt3 < convertIpSectionToInt2 && !z2) {
                z2 = true;
            }
            if (convertIpSectionToInt3 > convertIpSectionToInt2 && !z2) {
                return false;
            }
            if (z && z2) {
                return true;
            }
        }
        return true;
    }

    public final int convertIpSectionToInt(String str, Firewall.AddressType addressType) {
        return addressType == Firewall.AddressType.IPV4 ? Integer.valueOf(str).intValue() : Integer.parseInt(str, 16);
    }
}
