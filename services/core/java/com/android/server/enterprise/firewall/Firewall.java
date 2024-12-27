package com.android.server.enterprise.firewall;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.firewall.FirewallRulesApplier.DomainFilterApplicationChainsManager;
import com.android.server.enterprise.storage.EdmStorageDefs;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.analytics.KnoxAnalytics;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.net.firewall.DomainFilterReport;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knox.net.firewall.FirewallRuleValidator;
import com.samsung.android.knox.net.firewall.IFirewall;
import com.samsung.android.knoxguard.service.utils.Constants;

import org.json.JSONArray;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Firewall extends IFirewall.Stub implements EnterpriseServiceCallback {
    public static boolean mHasIpv6FilterSupport;
    public boolean mAreRulesReloaded;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final DomainFilter mDomainFilter;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public final Map mFirewallExemptions;
    public final FirewallRulesApplier mFirewallRulesApplier;
    public final Object mInitializingExemptionListLock;
    public final Object mInitializingIpTablesRulesLock;
    public final Injector mInjector;
    public boolean mIsChainsCreated;
    public final AnonymousClass1 mPackageFilterReceiver;
    public final AnonymousClass1 mUserReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.firewall.Firewall$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public AnonymousClass4() {}

        @Override // java.lang.Runnable
        public final void run() {
            boolean z = Firewall.mHasIpv6FilterSupport;
            Log.i("Firewall", "initializeIpTables() ");
            synchronized (Firewall.this.mInitializingIpTablesRulesLock) {
                try {
                    Firewall firewall = Firewall.this;
                    if (!firewall.mAreRulesReloaded) {
                        if (!firewall.mIsChainsCreated) {
                            Firewall.m516$$Nest$mcreateChains(firewall);
                        }
                        Firewall.this.mFirewallRulesApplier.flushAllChains(null);
                        Firewall.this.mFirewallRulesApplier.setRulesStatusAfterReboot();
                        Firewall.this.blockOrUnblockAll(-1, true);
                        Firewall.this.mFirewallRulesApplier.reloadIptablesRules();
                        Firewall.this.mFirewallRulesApplier.reloadDomainFilterOnIptablesRules();
                        Firewall firewall2 = Firewall.this;
                        firewall2.mAreRulesReloaded = true;
                        firewall2.blockOrUnblockAll(-1, false);
                        Log.d(
                                "Firewall",
                                "initializeIpTables() - IP Tables initialization was done");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.firewall.Firewall$5, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass5 {
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$AddressType;
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$NetworkInterface;
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$PortLocation;
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$Protocol;
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

        static {
            int[] iArr = new int[FirewallRule.RuleType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType = iArr;
            try {
                iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.DENY.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.REDIRECT.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Firewall.Protocol.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$Protocol = iArr2;
            try {
                iArr2[Firewall.Protocol.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$Protocol[
                                Firewall.Protocol.TCP.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$Protocol[
                                Firewall.Protocol.UDP.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr3 = new int[Firewall.PortLocation.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$PortLocation = iArr3;
            try {
                iArr3[Firewall.PortLocation.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$PortLocation[
                                Firewall.PortLocation.LOCAL.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$PortLocation[
                                Firewall.PortLocation.REMOTE.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused10) {
            }
            int[] iArr4 = new int[Firewall.NetworkInterface.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$NetworkInterface = iArr4;
            try {
                iArr4[Firewall.NetworkInterface.ALL_NETWORKS.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$NetworkInterface[
                                Firewall.NetworkInterface.MOBILE_DATA_ONLY.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$NetworkInterface[
                                Firewall.NetworkInterface.WIFI_DATA_ONLY.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr5 = new int[Firewall.AddressType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$AddressType = iArr5;
            try {
                iArr5[Firewall.AddressType.IPV4.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$Firewall$AddressType[
                                Firewall.AddressType.IPV6.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class DownloadBlockStatus {
        public static final /* synthetic */ DownloadBlockStatus[] $VALUES;
        public static final DownloadBlockStatus BLOCKED_BY_BLACKLIST_RULE;
        public static final DownloadBlockStatus PASSED_BY_WHITELIST_RULE;
        public static final DownloadBlockStatus PASSED_NORULE;

        static {
            DownloadBlockStatus downloadBlockStatus = new DownloadBlockStatus("PASSED_NORULE", 0);
            PASSED_NORULE = downloadBlockStatus;
            DownloadBlockStatus downloadBlockStatus2 =
                    new DownloadBlockStatus("PASSED_BY_WHITELIST_RULE", 1);
            PASSED_BY_WHITELIST_RULE = downloadBlockStatus2;
            DownloadBlockStatus downloadBlockStatus3 =
                    new DownloadBlockStatus("BLOCKED_BY_BLACKLIST_RULE", 2);
            BLOCKED_BY_BLACKLIST_RULE = downloadBlockStatus3;
            $VALUES =
                    new DownloadBlockStatus[] {
                        downloadBlockStatus, downloadBlockStatus2, downloadBlockStatus3
                    };
        }

        public static DownloadBlockStatus valueOf(String str) {
            return (DownloadBlockStatus) Enum.valueOf(DownloadBlockStatus.class, str);
        }

        public static DownloadBlockStatus[] values() {
            return (DownloadBlockStatus[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public final Context mCtx;
        public DomainFilter mDomainFilter;
        public EdmStorageProvider mEdmStorageProvider;
        public EnterpriseDumpHelper mEnterpriseDumpHelper;
        public FirewallRulesApplier mFirewallRulesApplier;

        public Injector(Context context) {
            this.mCtx = context;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkCallback extends ConnectivityManager.NetworkCallback {
        public NetworkCallback() {}

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            Firewall firewall = Firewall.this;
            firewall.getClass();
            new Thread(firewall.new AnonymousClass4()).start();
        }
    }

    /* renamed from: -$$Nest$mcreateChains, reason: not valid java name */
    public static void m516$$Nest$mcreateChains(Firewall firewall) {
        synchronized (firewall.mInitializingIpTablesRulesLock) {
            if (!firewall.mIsChainsCreated) {
                try {
                    try {
                        try {
                            firewall.mIsChainsCreated =
                                    firewall.mFirewallRulesApplier.createIptablesChains(null);
                        } catch (IOException e) {
                            Log.e("Firewall", "createIptablesChains(): IOException was thrown.", e);
                        } catch (IllegalArgumentException e2) {
                            Log.e(
                                    "Firewall",
                                    "createIptablesChains(): IllegalArgumentException was thrown.",
                                    e2);
                        }
                    } catch (NullPointerException e3) {
                        Log.e(
                                "Firewall",
                                "createIptablesChains(): NullPointerException was thrown.",
                                e3);
                    }
                } catch (SecurityException e4) {
                    Log.e("Firewall", "createIptablesChains(): SecurityException was thrown.", e4);
                }
            }
        }
    }

    public Firewall(Context context) {
        Injector injector = new Injector(context);
        this.mIsChainsCreated = false;
        this.mAreRulesReloaded = false;
        this.mInitializingIpTablesRulesLock = new Object();
        Object obj = new Object();
        this.mInitializingExemptionListLock = obj;
        this.mFirewallExemptions = new HashMap();
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.enterprise.firewall.Firewall.1
                    public final /* synthetic */ Firewall this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, final Intent intent) {
                        int i2;
                        int i3;
                        int i4;
                        Context context3 = context2;
                        switch (i) {
                            case 0:
                                String action = intent.getAction();
                                if ("android.intent.action.USER_REMOVED".equals(action)
                                        || "android.intent.action.USER_ADDED".equals(action)) {
                                    final Firewall firewall = this.this$0;
                                    firewall.getClass();
                                    new Thread(
                                                    new Runnable() { // from class:
                                                        // com.android.server.enterprise.firewall.Firewall.3
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            int intExtra =
                                                                    intent.getIntExtra(
                                                                            "android.intent.extra.user_handle",
                                                                            -1);
                                                            if (intExtra == -1) {
                                                                return;
                                                            }
                                                            String action2 = intent.getAction();
                                                            boolean z =
                                                                    Firewall.mHasIpv6FilterSupport;
                                                            Log.i(
                                                                    "Firewall",
                                                                    "onUserHandle: Lock acquired."
                                                                        + " UserID received: "
                                                                            + intExtra
                                                                            + ". Got Action: "
                                                                            + action2);
                                                            Firewall firewall2 = Firewall.this;
                                                            Integer valueOf =
                                                                    Integer.valueOf(intExtra);
                                                            synchronized (
                                                                    firewall2
                                                                            .mInitializingIpTablesRulesLock) {
                                                                try {
                                                                    if (action2.equals(
                                                                            "android.intent.action.USER_REMOVED")) {
                                                                        firewall2
                                                                                .mFirewallRulesApplier
                                                                                .removeIptablesChains(
                                                                                        valueOf);
                                                                        firewall2
                                                                                .mFirewallRulesApplier
                                                                                .flushDomainChains(
                                                                                        valueOf,
                                                                                        true);
                                                                    } else if (action2.equals(
                                                                            "android.intent.action.USER_ADDED")) {
                                                                        firewall2
                                                                                .mFirewallRulesApplier
                                                                                .createIptablesChains(
                                                                                        valueOf);
                                                                    }
                                                                } catch (NullPointerException e) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " NullPointerException"
                                                                                + " was thrown."
                                                                                    + e
                                                                                            .getMessage());
                                                                } catch (SecurityException e2) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " SecurityException"
                                                                                + " was thrown."
                                                                                    + e2
                                                                                            .getMessage());
                                                                } catch (IOException e3) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " IOException was"
                                                                                + " thrown."
                                                                                    + e3
                                                                                            .getMessage());
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e4) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " IllegalArgumentException"
                                                                                + " was thrown."
                                                                                    + e4
                                                                                            .getMessage());
                                                                } finally {
                                                                }
                                                            }
                                                            Firewall firewall3 = Firewall.this;
                                                            Integer valueOf2 =
                                                                    Integer.valueOf(intExtra);
                                                            synchronized (
                                                                    firewall3
                                                                            .mInitializingExemptionListLock) {
                                                                try {
                                                                    if (action2.equals(
                                                                            "android.intent.action.USER_REMOVED")) {
                                                                        ((HashMap)
                                                                                        firewall3
                                                                                                .mFirewallExemptions)
                                                                                .remove(valueOf2);
                                                                    } else if (action2.equals(
                                                                            "android.intent.action.USER_ADDED")) {
                                                                        ((HashMap)
                                                                                        firewall3
                                                                                                .mFirewallExemptions)
                                                                                .put(
                                                                                        valueOf2,
                                                                                        new HashMap());
                                                                        firewall3
                                                                                .populateFirewallExemptionList(
                                                                                        valueOf2);
                                                                    }
                                                                } finally {
                                                                }
                                                            }
                                                        }
                                                    })
                                            .start();
                                    break;
                                }
                                break;
                            default:
                                boolean z = Firewall.mHasIpv6FilterSupport;
                                Log.i("Firewall", "onReceive() - " + intent);
                                String action2 = intent.getAction();
                                int i5 = 0;
                                if (!"android.intent.action.PACKAGE_ADDED".equals(action2)) {
                                    int i6 = 0;
                                    if ("android.intent.action.PACKAGE_REMOVED".equals(action2)) {
                                        Firewall firewall2 = this.this$0;
                                        firewall2.getClass();
                                        String schemeSpecificPart =
                                                intent.getData().getSchemeSpecificPart();
                                        int intExtra =
                                                intent.getIntExtra(
                                                        "android.intent.extra.user_handle", -1);
                                        int intExtra2 =
                                                intent.getIntExtra("android.intent.extra.UID", -1);
                                        if (intExtra != -1 && schemeSpecificPart != null) {
                                            firewall2.handleExemptionListOnPkgEvent(
                                                    intExtra2,
                                                    Integer.valueOf(intExtra),
                                                    schemeSpecificPart,
                                                    "android.intent.action.PACKAGE_REMOVED");
                                            String adminLUIDWhereIn =
                                                    EdmStorageProviderBase.getAdminLUIDWhereIn(
                                                            0, intExtra);
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("packageName", schemeSpecificPart);
                                            contentValues.put(adminLUIDWhereIn, "#SelectClause#");
                                            HashSet hashSet =
                                                    new HashSet(
                                                            firewall2.mEdmStorageProvider.getValues(
                                                                    "FirewallRule",
                                                                    new String[] {"adminUid"},
                                                                    contentValues));
                                            if (!hashSet.isEmpty()) {
                                                Log.d(
                                                        "Firewall",
                                                        "packageRemoved() - "
                                                                + schemeSpecificPart
                                                                + " has firewall rules to disable");
                                                Iterator it = hashSet.iterator();
                                                while (it.hasNext()) {
                                                    Integer asInteger =
                                                            ((ContentValues) it.next())
                                                                    .getAsInteger("adminUid");
                                                    if (asInteger != null) {
                                                        ContextInfo contextInfo =
                                                                new ContextInfo(
                                                                        asInteger.intValue());
                                                        FirewallRule[] rulesByAdminUidAndPackage =
                                                                firewall2
                                                                        .getRulesByAdminUidAndPackage(
                                                                                asInteger
                                                                                        .intValue(),
                                                                                schemeSpecificPart);
                                                        Context createContextAsUser =
                                                                Utils.createContextAsUser(
                                                                        context3, "android", i6,
                                                                        intExtra);
                                                        int length =
                                                                rulesByAdminUidAndPackage.length;
                                                        int i7 = i6;
                                                        while (i7 < length) {
                                                            FirewallRule firewallRule =
                                                                    rulesByAdminUidAndPackage[i7];
                                                            String signature =
                                                                    firewallRule
                                                                            .getApplication()
                                                                            .getSignature();
                                                            if (TextUtils.isEmpty(signature)
                                                                    || Utils
                                                                            .comparePackageSignature(
                                                                                    intExtra,
                                                                                    createContextAsUser,
                                                                                    firewallRule
                                                                                            .getApplication()
                                                                                            .getPackageName(),
                                                                                    signature)) {
                                                                firewallRule.setPackageUid(
                                                                        intExtra2);
                                                                if (firewall2
                                                                                .mFirewallRulesApplier
                                                                                .disableIpTablesRule(
                                                                                        firewallRule,
                                                                                        contextInfo,
                                                                                        false)
                                                                                .getResult()
                                                                        == FirewallResponse.Result
                                                                                .SUCCESS) {
                                                                    firewall2.mFirewallRulesApplier
                                                                            .updateStatusOnDB(
                                                                                    firewallRule,
                                                                                    FirewallRule
                                                                                            .Status
                                                                                            .DISABLED,
                                                                                    contextInfo);
                                                                    Log.d(
                                                                            "Firewall",
                                                                            "disableIpTablesRule()"
                                                                                + " - success");
                                                                }
                                                            }
                                                            i7++;
                                                            i6 = 0;
                                                        }
                                                        context3 = context2;
                                                    }
                                                }
                                                break;
                                            }
                                        } else {
                                            Log.i(
                                                    "Firewall",
                                                    "packageRemoved() - Received invalid user id"
                                                        + " orpackage name, can't retrieve"
                                                        + " application info");
                                            break;
                                        }
                                    }
                                } else {
                                    Firewall firewall3 = this.this$0;
                                    firewall3.getClass();
                                    String schemeSpecificPart2 =
                                            intent.getData().getSchemeSpecificPart();
                                    int intExtra3 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    if (intExtra3 != -1 && schemeSpecificPart2 != null) {
                                        firewall3.handleExemptionListOnPkgEvent(
                                                -1,
                                                Integer.valueOf(intExtra3),
                                                schemeSpecificPart2,
                                                "android.intent.action.PACKAGE_ADDED");
                                        String adminLUIDWhereIn2 =
                                                EdmStorageProviderBase.getAdminLUIDWhereIn(
                                                        0, intExtra3);
                                        ContentValues contentValues2 = new ContentValues();
                                        contentValues2.put("packageName", schemeSpecificPart2);
                                        contentValues2.put(adminLUIDWhereIn2, "#SelectClause#");
                                        HashSet hashSet2 =
                                                new HashSet(
                                                        firewall3.mEdmStorageProvider.getValues(
                                                                "FirewallRule",
                                                                new String[] {"adminUid"},
                                                                contentValues2));
                                        if (!hashSet2.isEmpty()) {
                                            Log.d(
                                                    "Firewall",
                                                    "packageAdded() - "
                                                            + schemeSpecificPart2
                                                            + " has firewall rules to enable");
                                            Iterator it2 = hashSet2.iterator();
                                            while (it2.hasNext()) {
                                                Integer asInteger2 =
                                                        ((ContentValues) it2.next())
                                                                .getAsInteger("adminUid");
                                                if (asInteger2 != null) {
                                                    ContextInfo contextInfo2 =
                                                            new ContextInfo(asInteger2.intValue());
                                                    FirewallRule[] rulesByAdminUidAndPackage2 =
                                                            firewall3.getRulesByAdminUidAndPackage(
                                                                    asInteger2.intValue(),
                                                                    schemeSpecificPart2);
                                                    Context createContextAsUser2 =
                                                            Utils.createContextAsUser(
                                                                    context3, "android", i5,
                                                                    intExtra3);
                                                    int length2 = rulesByAdminUidAndPackage2.length;
                                                    int i8 = i5;
                                                    while (i8 < length2) {
                                                        FirewallRule firewallRule2 =
                                                                rulesByAdminUidAndPackage2[i8];
                                                        String signature2 =
                                                                firewallRule2
                                                                        .getApplication()
                                                                        .getSignature();
                                                        if (TextUtils.isEmpty(signature2)
                                                                || Utils.comparePackageSignature(
                                                                        intExtra3,
                                                                        createContextAsUser2,
                                                                        firewallRule2
                                                                                .getApplication()
                                                                                .getPackageName(),
                                                                        signature2)) {
                                                            FirewallRulesApplier
                                                                    firewallRulesApplier =
                                                                            firewall3
                                                                                    .mFirewallRulesApplier;
                                                            firewallRulesApplier.getClass();
                                                            i2 = intExtra3;
                                                            i3 = i8;
                                                            i4 = 0;
                                                            FirewallResponse firewallResponse =
                                                                    firewallRulesApplier
                                                                            .enableOrDisableIptablesRule(
                                                                                    new FirewallRule
                                                                                            [] {
                                                                                        firewallRule2
                                                                                    },
                                                                                    true,
                                                                                    contextInfo2,
                                                                                    false,
                                                                                    true)[0];
                                                            if (firewallResponse != null
                                                                    && firewallResponse.getResult()
                                                                            == FirewallResponse
                                                                                    .Result
                                                                                    .SUCCESS) {
                                                                firewall3.mFirewallRulesApplier
                                                                        .updateStatusOnDB(
                                                                                firewallRule2,
                                                                                FirewallRule.Status
                                                                                        .ENABLED,
                                                                                contextInfo2);
                                                                Log.d(
                                                                        "Firewall",
                                                                        "enableRuleAsAdmin() -"
                                                                            + " success");
                                                            }
                                                        } else {
                                                            i2 = intExtra3;
                                                            i3 = i8;
                                                            i4 = 0;
                                                        }
                                                        i8 = i3 + 1;
                                                        i5 = i4;
                                                        intExtra3 = i2;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    } else {
                                        Log.i(
                                                "Firewall",
                                                "packageAdded() - Received invalid user id"
                                                    + " orpackage name, can't retrieve application"
                                                    + " info");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.enterprise.firewall.Firewall.1
                    public final /* synthetic */ Firewall this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, final Intent intent) {
                        int i22;
                        int i3;
                        int i4;
                        Context context3 = context2;
                        switch (i2) {
                            case 0:
                                String action = intent.getAction();
                                if ("android.intent.action.USER_REMOVED".equals(action)
                                        || "android.intent.action.USER_ADDED".equals(action)) {
                                    final Firewall firewall = this.this$0;
                                    firewall.getClass();
                                    new Thread(
                                                    new Runnable() { // from class:
                                                        // com.android.server.enterprise.firewall.Firewall.3
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            int intExtra =
                                                                    intent.getIntExtra(
                                                                            "android.intent.extra.user_handle",
                                                                            -1);
                                                            if (intExtra == -1) {
                                                                return;
                                                            }
                                                            String action2 = intent.getAction();
                                                            boolean z =
                                                                    Firewall.mHasIpv6FilterSupport;
                                                            Log.i(
                                                                    "Firewall",
                                                                    "onUserHandle: Lock acquired."
                                                                        + " UserID received: "
                                                                            + intExtra
                                                                            + ". Got Action: "
                                                                            + action2);
                                                            Firewall firewall2 = Firewall.this;
                                                            Integer valueOf =
                                                                    Integer.valueOf(intExtra);
                                                            synchronized (
                                                                    firewall2
                                                                            .mInitializingIpTablesRulesLock) {
                                                                try {
                                                                    if (action2.equals(
                                                                            "android.intent.action.USER_REMOVED")) {
                                                                        firewall2
                                                                                .mFirewallRulesApplier
                                                                                .removeIptablesChains(
                                                                                        valueOf);
                                                                        firewall2
                                                                                .mFirewallRulesApplier
                                                                                .flushDomainChains(
                                                                                        valueOf,
                                                                                        true);
                                                                    } else if (action2.equals(
                                                                            "android.intent.action.USER_ADDED")) {
                                                                        firewall2
                                                                                .mFirewallRulesApplier
                                                                                .createIptablesChains(
                                                                                        valueOf);
                                                                    }
                                                                } catch (NullPointerException e) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " NullPointerException"
                                                                                + " was thrown."
                                                                                    + e
                                                                                            .getMessage());
                                                                } catch (SecurityException e2) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " SecurityException"
                                                                                + " was thrown."
                                                                                    + e2
                                                                                            .getMessage());
                                                                } catch (IOException e3) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " IOException was"
                                                                                + " thrown."
                                                                                    + e3
                                                                                            .getMessage());
                                                                } catch (
                                                                        IllegalArgumentException
                                                                                e4) {
                                                                    Log.e(
                                                                            "Firewall",
                                                                            "handleUserChains():"
                                                                                + " IllegalArgumentException"
                                                                                + " was thrown."
                                                                                    + e4
                                                                                            .getMessage());
                                                                } finally {
                                                                }
                                                            }
                                                            Firewall firewall3 = Firewall.this;
                                                            Integer valueOf2 =
                                                                    Integer.valueOf(intExtra);
                                                            synchronized (
                                                                    firewall3
                                                                            .mInitializingExemptionListLock) {
                                                                try {
                                                                    if (action2.equals(
                                                                            "android.intent.action.USER_REMOVED")) {
                                                                        ((HashMap)
                                                                                        firewall3
                                                                                                .mFirewallExemptions)
                                                                                .remove(valueOf2);
                                                                    } else if (action2.equals(
                                                                            "android.intent.action.USER_ADDED")) {
                                                                        ((HashMap)
                                                                                        firewall3
                                                                                                .mFirewallExemptions)
                                                                                .put(
                                                                                        valueOf2,
                                                                                        new HashMap());
                                                                        firewall3
                                                                                .populateFirewallExemptionList(
                                                                                        valueOf2);
                                                                    }
                                                                } finally {
                                                                }
                                                            }
                                                        }
                                                    })
                                            .start();
                                    break;
                                }
                                break;
                            default:
                                boolean z = Firewall.mHasIpv6FilterSupport;
                                Log.i("Firewall", "onReceive() - " + intent);
                                String action2 = intent.getAction();
                                int i5 = 0;
                                if (!"android.intent.action.PACKAGE_ADDED".equals(action2)) {
                                    int i6 = 0;
                                    if ("android.intent.action.PACKAGE_REMOVED".equals(action2)) {
                                        Firewall firewall2 = this.this$0;
                                        firewall2.getClass();
                                        String schemeSpecificPart =
                                                intent.getData().getSchemeSpecificPart();
                                        int intExtra =
                                                intent.getIntExtra(
                                                        "android.intent.extra.user_handle", -1);
                                        int intExtra2 =
                                                intent.getIntExtra("android.intent.extra.UID", -1);
                                        if (intExtra != -1 && schemeSpecificPart != null) {
                                            firewall2.handleExemptionListOnPkgEvent(
                                                    intExtra2,
                                                    Integer.valueOf(intExtra),
                                                    schemeSpecificPart,
                                                    "android.intent.action.PACKAGE_REMOVED");
                                            String adminLUIDWhereIn =
                                                    EdmStorageProviderBase.getAdminLUIDWhereIn(
                                                            0, intExtra);
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("packageName", schemeSpecificPart);
                                            contentValues.put(adminLUIDWhereIn, "#SelectClause#");
                                            HashSet hashSet =
                                                    new HashSet(
                                                            firewall2.mEdmStorageProvider.getValues(
                                                                    "FirewallRule",
                                                                    new String[] {"adminUid"},
                                                                    contentValues));
                                            if (!hashSet.isEmpty()) {
                                                Log.d(
                                                        "Firewall",
                                                        "packageRemoved() - "
                                                                + schemeSpecificPart
                                                                + " has firewall rules to disable");
                                                Iterator it = hashSet.iterator();
                                                while (it.hasNext()) {
                                                    Integer asInteger =
                                                            ((ContentValues) it.next())
                                                                    .getAsInteger("adminUid");
                                                    if (asInteger != null) {
                                                        ContextInfo contextInfo =
                                                                new ContextInfo(
                                                                        asInteger.intValue());
                                                        FirewallRule[] rulesByAdminUidAndPackage =
                                                                firewall2
                                                                        .getRulesByAdminUidAndPackage(
                                                                                asInteger
                                                                                        .intValue(),
                                                                                schemeSpecificPart);
                                                        Context createContextAsUser =
                                                                Utils.createContextAsUser(
                                                                        context3, "android", i6,
                                                                        intExtra);
                                                        int length =
                                                                rulesByAdminUidAndPackage.length;
                                                        int i7 = i6;
                                                        while (i7 < length) {
                                                            FirewallRule firewallRule =
                                                                    rulesByAdminUidAndPackage[i7];
                                                            String signature =
                                                                    firewallRule
                                                                            .getApplication()
                                                                            .getSignature();
                                                            if (TextUtils.isEmpty(signature)
                                                                    || Utils
                                                                            .comparePackageSignature(
                                                                                    intExtra,
                                                                                    createContextAsUser,
                                                                                    firewallRule
                                                                                            .getApplication()
                                                                                            .getPackageName(),
                                                                                    signature)) {
                                                                firewallRule.setPackageUid(
                                                                        intExtra2);
                                                                if (firewall2
                                                                                .mFirewallRulesApplier
                                                                                .disableIpTablesRule(
                                                                                        firewallRule,
                                                                                        contextInfo,
                                                                                        false)
                                                                                .getResult()
                                                                        == FirewallResponse.Result
                                                                                .SUCCESS) {
                                                                    firewall2.mFirewallRulesApplier
                                                                            .updateStatusOnDB(
                                                                                    firewallRule,
                                                                                    FirewallRule
                                                                                            .Status
                                                                                            .DISABLED,
                                                                                    contextInfo);
                                                                    Log.d(
                                                                            "Firewall",
                                                                            "disableIpTablesRule()"
                                                                                + " - success");
                                                                }
                                                            }
                                                            i7++;
                                                            i6 = 0;
                                                        }
                                                        context3 = context2;
                                                    }
                                                }
                                                break;
                                            }
                                        } else {
                                            Log.i(
                                                    "Firewall",
                                                    "packageRemoved() - Received invalid user id"
                                                        + " orpackage name, can't retrieve"
                                                        + " application info");
                                            break;
                                        }
                                    }
                                } else {
                                    Firewall firewall3 = this.this$0;
                                    firewall3.getClass();
                                    String schemeSpecificPart2 =
                                            intent.getData().getSchemeSpecificPart();
                                    int intExtra3 =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    if (intExtra3 != -1 && schemeSpecificPart2 != null) {
                                        firewall3.handleExemptionListOnPkgEvent(
                                                -1,
                                                Integer.valueOf(intExtra3),
                                                schemeSpecificPart2,
                                                "android.intent.action.PACKAGE_ADDED");
                                        String adminLUIDWhereIn2 =
                                                EdmStorageProviderBase.getAdminLUIDWhereIn(
                                                        0, intExtra3);
                                        ContentValues contentValues2 = new ContentValues();
                                        contentValues2.put("packageName", schemeSpecificPart2);
                                        contentValues2.put(adminLUIDWhereIn2, "#SelectClause#");
                                        HashSet hashSet2 =
                                                new HashSet(
                                                        firewall3.mEdmStorageProvider.getValues(
                                                                "FirewallRule",
                                                                new String[] {"adminUid"},
                                                                contentValues2));
                                        if (!hashSet2.isEmpty()) {
                                            Log.d(
                                                    "Firewall",
                                                    "packageAdded() - "
                                                            + schemeSpecificPart2
                                                            + " has firewall rules to enable");
                                            Iterator it2 = hashSet2.iterator();
                                            while (it2.hasNext()) {
                                                Integer asInteger2 =
                                                        ((ContentValues) it2.next())
                                                                .getAsInteger("adminUid");
                                                if (asInteger2 != null) {
                                                    ContextInfo contextInfo2 =
                                                            new ContextInfo(asInteger2.intValue());
                                                    FirewallRule[] rulesByAdminUidAndPackage2 =
                                                            firewall3.getRulesByAdminUidAndPackage(
                                                                    asInteger2.intValue(),
                                                                    schemeSpecificPart2);
                                                    Context createContextAsUser2 =
                                                            Utils.createContextAsUser(
                                                                    context3, "android", i5,
                                                                    intExtra3);
                                                    int length2 = rulesByAdminUidAndPackage2.length;
                                                    int i8 = i5;
                                                    while (i8 < length2) {
                                                        FirewallRule firewallRule2 =
                                                                rulesByAdminUidAndPackage2[i8];
                                                        String signature2 =
                                                                firewallRule2
                                                                        .getApplication()
                                                                        .getSignature();
                                                        if (TextUtils.isEmpty(signature2)
                                                                || Utils.comparePackageSignature(
                                                                        intExtra3,
                                                                        createContextAsUser2,
                                                                        firewallRule2
                                                                                .getApplication()
                                                                                .getPackageName(),
                                                                        signature2)) {
                                                            FirewallRulesApplier
                                                                    firewallRulesApplier =
                                                                            firewall3
                                                                                    .mFirewallRulesApplier;
                                                            firewallRulesApplier.getClass();
                                                            i22 = intExtra3;
                                                            i3 = i8;
                                                            i4 = 0;
                                                            FirewallResponse firewallResponse =
                                                                    firewallRulesApplier
                                                                            .enableOrDisableIptablesRule(
                                                                                    new FirewallRule
                                                                                            [] {
                                                                                        firewallRule2
                                                                                    },
                                                                                    true,
                                                                                    contextInfo2,
                                                                                    false,
                                                                                    true)[0];
                                                            if (firewallResponse != null
                                                                    && firewallResponse.getResult()
                                                                            == FirewallResponse
                                                                                    .Result
                                                                                    .SUCCESS) {
                                                                firewall3.mFirewallRulesApplier
                                                                        .updateStatusOnDB(
                                                                                firewallRule2,
                                                                                FirewallRule.Status
                                                                                        .ENABLED,
                                                                                contextInfo2);
                                                                Log.d(
                                                                        "Firewall",
                                                                        "enableRuleAsAdmin() -"
                                                                            + " success");
                                                            }
                                                        } else {
                                                            i22 = intExtra3;
                                                            i3 = i8;
                                                            i4 = 0;
                                                        }
                                                        i8 = i3 + 1;
                                                        i5 = i4;
                                                        intExtra3 = i22;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    } else {
                                        Log.i(
                                                "Firewall",
                                                "packageAdded() - Received invalid user id"
                                                    + " orpackage name, can't retrieve application"
                                                    + " info");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        injector.mEdmStorageProvider = new EdmStorageProvider(context);
        injector.mEnterpriseDumpHelper = new EnterpriseDumpHelper(context);
        FirewallRulesApplier firewallRulesApplier = new FirewallRulesApplier();
        firewallRulesApplier.mShouldAddAcceptRuleToInput = true;
        firewallRulesApplier.mContext = context;
        firewallRulesApplier.mFirewallService = this;
        firewallRulesApplier.mEdmStorageProvider = new EdmStorageProvider(context);
        firewallRulesApplier.mDomainFilterAppChainsMngr =
                firewallRulesApplier.new DomainFilterApplicationChainsManager();
        firewallRulesApplier.mIcmpAllowRuleApplies = 0;
        injector.mFirewallRulesApplier = firewallRulesApplier;
        DomainFilter domainFilter = new DomainFilter(context, injector.mFirewallRulesApplier);
        this.mInjector = injector;
        this.mContext = context;
        this.mEdmStorageProvider = injector.mEdmStorageProvider;
        FirewallRulesApplier firewallRulesApplier2 = injector.mFirewallRulesApplier;
        this.mFirewallRulesApplier = firewallRulesApplier2;
        this.mDomainFilter = domainFilter;
        this.mEnterpriseDumpHelper = injector.mEnterpriseDumpHelper;
        firewallRulesApplier2.getClass();
        mHasIpv6FilterSupport =
                KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 16
                        ? firewallRulesApplier2.runShellCommand(
                                6, "*filter\n:test_ipv6 -\n-X test_ipv6\nCOMMIT\n")
                        : false;
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        }
        this.mConnectivityManager.registerDefaultNetworkCallback(new NetworkCallback());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        context.registerReceiverAsUser(
                broadcastReceiver2, UserHandle.ALL, intentFilter2, null, null);
        ArrayList allUsers = FirewallUtils.getAllUsers(context);
        synchronized (obj) {
            try {
                Iterator it = allUsers.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    ((HashMap) this.mFirewallExemptions).put(num, new HashMap());
                    populateFirewallExemptionList(num);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        new Thread(new AnonymousClass4()).start();
    }

    public static boolean compareIpSubdivisions(
            String[] strArr, String[] strArr2, String[] strArr3, Firewall.AddressType addressType) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < strArr3.length; i++) {
            int convertIpSectionToInt = convertIpSectionToInt(addressType, strArr[i]);
            int convertIpSectionToInt2 = convertIpSectionToInt(addressType, strArr2[i]);
            int convertIpSectionToInt3 = convertIpSectionToInt(addressType, strArr3[i]);
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

    public static int convertIpSectionToInt(Firewall.AddressType addressType, String str) {
        return addressType == Firewall.AddressType.IPV4
                ? Integer.valueOf(str).intValue()
                : Integer.parseInt(str, 16);
    }

    public static String convertToIpv6CompleteForm(String str) {
        if (!str.contains("::")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if ("::".equals(str)) {
            for (int i = 1; i < 8; i++) {
                sb.append("0");
                if (i != 7) {
                    sb.append(":");
                }
            }
            return sb.toString();
        }
        String[] split = str.split("::");
        int i2 = 0;
        if (str.charAt(0) == ':') {
            String[] split2 = split[1].split(":");
            int length = 8 - split2.length;
            for (int i3 = 0; i3 < length; i3++) {
                sb.append("0:");
            }
            while (i2 < split2.length) {
                sb.append(split2[i2]);
                if (i2 != split2.length - 1) {
                    sb.append(":");
                }
                i2++;
            }
            return sb.toString();
        }
        if (split.length != 2) {
            String[] split3 = split[0].split(":");
            int length2 = split3.length;
            int i4 = 8 - length2;
            for (String str2 : split3) {
                sb.append(str2 + ":");
            }
            while (i2 < i4) {
                sb.append("0");
                if (i2 != 7 - length2) {
                    sb.append(":");
                }
                i2++;
            }
            return sb.toString();
        }
        String[] split4 = split[0].split(":");
        String[] split5 = split[1].split(":");
        int length3 = (8 - split4.length) - split5.length;
        for (String str3 : split4) {
            sb.append(str3);
            sb.append(":");
        }
        for (int i5 = 0; i5 < length3; i5++) {
            sb.append("0:");
        }
        while (i2 < split5.length) {
            sb.append(split5[i2]);
            if (i2 != split5.length - 1) {
                sb.append(":");
            }
            i2++;
        }
        return sb.toString();
    }

    public static FirewallRule createDenyAllRule(Firewall.AddressType addressType, boolean z) {
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.DENY, addressType);
        firewallRule.setIpAddress("*");
        firewallRule.setPortNumber("*");
        if (z) {
            firewallRule.setApplication(new AppIdentity("android", (String) null));
        }
        return firewallRule;
    }

    public static boolean matchIpIntoIpRangeRule(String str, FirewallRule firewallRule) {
        Firewall.AddressType addressType =
                str.contains(":") ? Firewall.AddressType.IPV6 : Firewall.AddressType.IPV4;
        if (!firewallRule.getIpAddress().contains(PackageManagerShellCommandDataLoader.STDIN_PATH)
                || firewallRule.getAddressType() != addressType) {
            return false;
        }
        String[] split =
                firewallRule.getIpAddress().split(PackageManagerShellCommandDataLoader.STDIN_PATH);
        if (addressType == Firewall.AddressType.IPV4) {
            return compareIpSubdivisions(
                    split[0].split("\\."), split[1].split("\\."), str.split("\\."), addressType);
        }
        split[0] = convertToIpv6CompleteForm(split[0]);
        split[1] = convertToIpv6CompleteForm(split[1]);
        return compareIpSubdivisions(
                split[0].split(":"),
                split[1].split(":"),
                convertToIpv6CompleteForm(str).split(":"),
                addressType);
    }

    public static boolean matchPortIntoPortRangeRule(String str, FirewallRule firewallRule) {
        if (firewallRule
                .getPortNumber()
                .contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
            String[] split =
                    firewallRule
                            .getPortNumber()
                            .split(PackageManagerShellCommandDataLoader.STDIN_PATH);
            if (split.length == 2
                    && Integer.valueOf(str).intValue() > Integer.valueOf(split[0]).intValue()
                    && Integer.valueOf(str).intValue() < Integer.valueOf(split[1]).intValue()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(24:5|6|(22:70|(1:(1:73)(1:74))(1:75)|9|(19:62|(1:(1:(1:66)(1:67))(1:68))(1:69)|12|13|14|(14:52|(1:(1:(1:56)(1:57))(1:58))(1:59)|17|18|(10:43|(1:(1:(1:47)(1:48))(1:49))(1:50)|21|22|23|(5:31|(1:(1:(2:35|(1:37)(1:38))(1:39))(1:40))|26|27|29)|25|26|27|29)|20|21|22|23|(0)|25|26|27|29)|16|17|18|(0)|20|21|22|23|(0)|25|26|27|29)|11|12|13|14|(0)|16|17|18|(0)|20|21|22|23|(0)|25|26|27|29)|8|9|(0)|11|12|13|14|(0)|16|17|18|(0)|20|21|22|23|(0)|25|26|27|29) */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e0, code lost:

       r11 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e1, code lost:

       android.util.Log.e("Firewall", "Failed to put parameter in json: " + r11.getMessage());
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0084, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0085, code lost:

       android.util.Log.e("Firewall", "Failed to put parameter in json: " + r3.getMessage());
    */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c5 A[Catch: JSONException -> 0x0081, UnsupportedOperationException -> 0x00e0, TryCatch #2 {UnsupportedOperationException -> 0x00e0, blocks: (B:23:0x00ba, B:26:0x00dc, B:31:0x00c5), top: B:22:0x00ba, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a3 A[Catch: JSONException -> 0x0081, TryCatch #0 {JSONException -> 0x0081, blocks: (B:6:0x0007, B:9:0x0038, B:12:0x005b, B:14:0x005e, B:17:0x007d, B:18:0x0098, B:21:0x00b7, B:23:0x00ba, B:26:0x00dc, B:27:0x00f4, B:31:0x00c5, B:42:0x00e1, B:43:0x00a3, B:52:0x0069, B:61:0x0085, B:62:0x0047, B:70:0x0028), top: B:5:0x0007, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0069 A[Catch: JSONException -> 0x0081, UnsupportedOperationException -> 0x0084, TryCatch #1 {UnsupportedOperationException -> 0x0084, blocks: (B:14:0x005e, B:17:0x007d, B:52:0x0069), top: B:13:0x005e, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0047 A[Catch: JSONException -> 0x0081, TryCatch #0 {JSONException -> 0x0081, blocks: (B:6:0x0007, B:9:0x0038, B:12:0x005b, B:14:0x005e, B:17:0x007d, B:18:0x0098, B:21:0x00b7, B:23:0x00ba, B:26:0x00dc, B:27:0x00f4, B:31:0x00c5, B:42:0x00e1, B:43:0x00a3, B:52:0x0069, B:61:0x0085, B:62:0x0047, B:70:0x0028), top: B:5:0x0007, inners: #1, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setRulesProperties(
            com.samsung.android.knox.net.firewall.FirewallRule r11, org.json.JSONArray r12) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.Firewall.setRulesProperties(com.samsung.android.knox.net.firewall.FirewallRule,"
                    + " org.json.JSONArray):void");
    }

    public static boolean shouldApplyExemptRules(FirewallRule[] firewallRuleArr) {
        for (FirewallRule firewallRule : firewallRuleArr) {
            if (FirewallRule.RuleType.DENY.equals(firewallRule.getRuleType())
                    && !FirewallRule.Status.DISABLED.equals(firewallRule.getStatus())
                    && !Firewall.Direction.FORWARD.equals(firewallRule.getDirection())) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldApplyIcmpAllowRule(FirewallRule firewallRule) {
        if (firewallRule != null
                && FirewallRule.RuleType.DENY.equals(firewallRule.getRuleType())
                && "*".equals(firewallRule.getIpAddress())) {
            return Firewall.Direction.ALL.equals(firewallRule.getDirection())
                    || Firewall.Direction.INPUT.equals(firewallRule.getDirection());
        }
        return false;
    }

    public final boolean addAppToExemptList(Integer num, String str) {
        Map map = (Map) this.mFirewallExemptions.get(num);
        if (map.containsKey(str)
                || !FirewallUtils.verifyPackageName(num.intValue(), str)
                || !FirewallUtils.isPlatformSigned(num.intValue(), str)) {
            return false;
        }
        FirewallRule firewallRule =
                new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
        firewallRule.setDirection(Firewall.Direction.OUTPUT);
        firewallRule.setApplication(new AppIdentity(str, (String) null));
        map.put(str, new FirewallExemption(firewallRule));
        return true;
    }

    public final FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i) {
        FirewallResponse[] addDomainFilterRules;
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        DomainFilter domainFilter = this.mDomainFilter;
        synchronized (domainFilter) {
            addDomainFilterRules =
                    domainFilter.addDomainFilterRules(
                            enforceFirewallPermission$1,
                            domainFilter.reassembleDomainFilterBrokenRules(i));
        }
        return addDomainFilterRules;
    }

    public final synchronized FirewallResponse addRule(
            ContextInfo contextInfo, FirewallRule firewallRule) {
        long clearCallingIdentity;
        int i = contextInfo.mCallerUid;
        FirewallResponse validateFirewallRule =
                FirewallRuleValidator.validateFirewallRule(firewallRule);
        FirewallResponse.Result result = validateFirewallRule.getResult();
        FirewallResponse.Result result2 = FirewallResponse.Result.FAILED;
        if (result == result2) {
            Log.i("Firewall", "addRule() - Invalid Firewall Rule");
            return validateFirewallRule;
        }
        boolean z = false;
        if (!TextUtils.isEmpty(firewallRule.getApplication().getSignature())) {
            Context createContextAsUser =
                    Utils.createContextAsUser(this.mContext, "android", 0, UserHandle.getUserId(i));
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!Utils.comparePackageSignature(
                        0,
                        createContextAsUser,
                        firewallRule.getApplication().getPackageName(),
                        firewallRule.getApplication().getSignature())) {
                    Log.i("Firewall", "addRule() - Signature mismatch");
                    return new FirewallResponse(
                            result2,
                            FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                            "Given signature does not match with the application.");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        if ("SYSTEM_UIDS".equals(firewallRule.getApplication().getPackageName())) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ComponentName profileOwnerAsUser =
                        devicePolicyManager.getProfileOwnerAsUser(UserHandle.getUserId(i));
                if (devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()
                        && profileOwnerAsUser != null
                        && profileOwnerAsUser.getPackageName().equals(nameForUid)) {
                    z = true;
                }
                if (!devicePolicyManager.isDeviceOwnerAppOnCallingUser(nameForUid) && !z) {
                    Log.i(
                            "Firewall",
                            "addRule() - Firewall system uid rule not supported outside DO or"
                                + " WPCOD");
                    return new FirewallResponse(
                            result2,
                            FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                            "Firewall rules for system UIDs are only supported on DO or WPCOD.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if ("SYSTEM_UIDS".equals(firewallRule.getApplication().getPackageName())
                && FirewallUtils.isKGExemptRuleRequired()) {
            Log.i(
                    "Firewall",
                    "addRule() - Firewall system uid rule not supported on Knox Guard enrolled"
                        + " device");
            return new FirewallResponse(
                    result2,
                    FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                    "Firewall rules for system UIDs are not supported on devices enrolled in Knox"
                        + " Guard.");
        }
        if (Firewall.AddressType.IPV6.equals(firewallRule.getAddressType())) {
            FirewallRule.RuleType ruleType = firewallRule.getRuleType();
            if ((FirewallRule.RuleType.ALLOW.equals(ruleType)
                            || FirewallRule.RuleType.DENY.equals(ruleType))
                    && !mHasIpv6FilterSupport) {
                Log.i("Firewall", "addRule() - IPv6 not supported");
                return new FirewallResponse(
                        result2,
                        FirewallResponse.ErrorCode.IPV6_NOT_SUPPORTED_ERROR,
                        "This device does not have IPv6 support for this type of rule.");
            }
            if (FirewallRule.RuleType.REDIRECT.equals(ruleType)
                    || FirewallRule.RuleType.REDIRECT_EXCEPTION.equals(ruleType)) {
                Log.i("Firewall", "addRule() - IPv6 not supported");
                return new FirewallResponse(
                        result2,
                        FirewallResponse.ErrorCode.IPV6_NOT_SUPPORTED_ERROR,
                        "This device does not have IPv6 support for this type of rule.");
            }
        }
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        if (edmStorageProvider != null) {
            ContentValues contentValuesFromRule =
                    FirewallUtils.getContentValuesFromRule(firewallRule, i);
            contentValuesFromRule.remove(Constants.JSON_CLIENT_DATA_STATUS);
            if (!((ArrayList)
                            edmStorageProvider.getValues(
                                    "FirewallRule",
                                    EdmStorageDefs.FIREWALL_RULE_COLUMNS,
                                    contentValuesFromRule))
                    .isEmpty()) {
                Log.i("Firewall", "addRule() - This rule is already in the database");
                return new FirewallResponse(
                        FirewallResponse.Result.NO_CHANGES,
                        FirewallResponse.ErrorCode.NO_ERROR,
                        "The specified rule is already in the database.");
            }
        }
        if (isFirewallEnabled(contextInfo)) {
            firewallRule.setStatus(FirewallRule.Status.PENDING);
        }
        long insert =
                this.mEdmStorageProvider.insert(
                        "FirewallRule", FirewallUtils.getContentValuesFromRule(firewallRule, i));
        if (insert == -1) {
            Log.e("Firewall", "addRule() - Failed to add rule in the database");
            return new FirewallResponse(
                    result2,
                    FirewallResponse.ErrorCode.DATABASE_ERROR,
                    "Failed to add/update rule in the database.");
        }
        firewallRule.setId((int) insert);
        return new FirewallResponse(
                FirewallResponse.Result.SUCCESS,
                FirewallResponse.ErrorCode.NO_ERROR,
                "The rule(s) was successfully added/updated.");
    }

    public final synchronized FirewallResponse[] addRules(
            ContextInfo contextInfo, FirewallRule[] firewallRuleArr) {
        try {
            ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
            Log.i("Firewall", "addRules()");
            if (firewallRuleArr != null && firewallRuleArr.length != 0) {
                JSONArray jSONArray = new JSONArray();
                FirewallResponse[] firewallResponseArr =
                        new FirewallResponse[firewallRuleArr.length];
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < firewallRuleArr.length; i++) {
                    setRulesProperties(firewallRuleArr[i], jSONArray);
                    FirewallResponse addRule =
                            addRule(enforceFirewallPermission$1, firewallRuleArr[i]);
                    firewallResponseArr[i] = addRule;
                    if (FirewallResponse.Result.SUCCESS.equals(addRule.getResult())) {
                        arrayList.add(Integer.valueOf(i));
                    }
                }
                boolean z = true;
                KnoxAnalyticsData knoxAnalyticsData =
                        new KnoxAnalyticsData("KNOX_SECURE_NETWORK", 1, "addRules");
                knoxAnalyticsData.setUserTypeProperty(enforceFirewallPermission$1.mContainerId);
                knoxAnalyticsData.setProperty("rules", "\"" + jSONArray.toString() + "\"");
                KnoxAnalytics.log(knoxAnalyticsData);
                int size = arrayList.size();
                FirewallRule[] firewallRuleArr2 = new FirewallRule[size];
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    firewallRuleArr2[i2] =
                            firewallRuleArr[((Integer) arrayList.get(i2)).intValue()];
                }
                FirewallResponse[] firewallResponseArr2 = new FirewallResponse[arrayList.size()];
                if (isFirewallEnabled(enforceFirewallPermission$1)) {
                    firewallResponseArr2 =
                            enableFirewallRules(enforceFirewallPermission$1, firewallRuleArr2);
                    if (shouldApplyExemptRules(firewallRuleArr2)) {
                        applyExemptRules(
                                enforceFirewallPermission$1,
                                UserHandle.getUserId(enforceFirewallPermission$1.mCallerUid));
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
                FirewallResponse[] firewallResponseArr3 =
                        new FirewallResponse[firewallRuleArr.length];
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    FirewallResponse.Result result = FirewallResponse.Result.SUCCESS;
                    if (result.equals(firewallResponseArr2[i4].getResult())) {
                        FirewallResponse.ErrorCode errorCode =
                                FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR;
                        if (errorCode.equals(firewallResponseArr2[i4].getErrorCode())) {
                            firewallResponseArr3[((Integer) arrayList.get(i4)).intValue()] =
                                    new FirewallResponse(
                                            result,
                                            errorCode,
                                            "The rule(s) was successfully added/updated. / "
                                                    + firewallResponseArr2[i4].getMessage());
                        } else {
                            firewallResponseArr3[((Integer) arrayList.get(i4)).intValue()] =
                                    new FirewallResponse(
                                            result,
                                            FirewallResponse.ErrorCode.NO_ERROR,
                                            "The rule(s) was successfully added/updated. / "
                                                    + firewallResponseArr2[i4].getMessage());
                        }
                    } else {
                        firewallResponseArr3[((Integer) arrayList.get(i4)).intValue()] =
                                new FirewallResponse(
                                        FirewallResponse.Result.FAILED,
                                        FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                                        "The rule(s) was successfully added/updated. / "
                                                + firewallResponseArr2[i4].getMessage());
                    }
                }
                for (int i5 = 0; i5 < firewallRuleArr.length; i5++) {
                    if (!FirewallResponse.Result.SUCCESS.equals(
                            firewallResponseArr[i5].getResult())) {
                        if (FirewallResponse.Result.NO_CHANGES.equals(
                                firewallResponseArr[i5].getResult())) {
                            firewallResponseArr3[i5] = firewallResponseArr[i5];
                        } else {
                            firewallResponseArr3[i5] =
                                    new FirewallResponse(
                                            FirewallResponse.Result.FAILED,
                                            firewallResponseArr[i5].getErrorCode(),
                                            "Failed to add/update rule in the database. / "
                                                    + firewallResponseArr[i5].getMessage());
                        }
                    }
                }
                return firewallResponseArr3;
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void applyExemptRules(ContextInfo contextInfo, int i) {
        for (FirewallExemption firewallExemption :
                ((Map) ((HashMap) this.mFirewallExemptions).get(Integer.valueOf(i))).values()) {
            if (!firewallExemption.mIsApplied) {
                String packageName =
                        firewallExemption.mExemptionRule.getApplication().getPackageName();
                packageName.getClass();
                if (!packageName.equals("com.samsung.android.kgclient")
                        ? true
                        : FirewallUtils.isKGExemptRuleRequired()) {
                    this.mFirewallRulesApplier.addOrRemoveExemptRules(
                            firewallExemption, true, contextInfo);
                    firewallExemption.mIsApplied = true;
                }
            }
        }
    }

    public final void blockOrUnblockAll(int i, boolean z) {
        Log.i("Firewall", "blockOrUnblockAll(block = " + String.valueOf(z));
        Log.d("Firewall", ", uid = " + String.valueOf(i) + ") ");
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
                    enableOrDisableRuleAsAdmin(
                            createDenyAllRule(Firewall.AddressType.IPV4, false), contextInfo, z);
                    enableOrDisableRuleAsAdmin(
                            createDenyAllRule(Firewall.AddressType.IPV6, false), contextInfo, z);
                    if (UserHandle.getUserId(intValue) == 0) {
                        FirewallRule[] rules = getRules(contextInfo, 15, null);
                        IPackageManager asInterface =
                                IPackageManager.Stub.asInterface(
                                        ServiceManager.getService("package"));
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                for (FirewallRule firewallRule : rules) {
                                    ApplicationInfo applicationInfo =
                                            asInterface.getApplicationInfo(
                                                    firewallRule.getApplication().getPackageName(),
                                                    0L,
                                                    UserHandle.getUserId(contextInfo.mCallerUid));
                                    if (applicationInfo != null
                                            && 1000 == UserHandle.getAppId(applicationInfo.uid)) {
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        enableOrDisableRuleAsAdmin(
                                                createDenyAllRule(Firewall.AddressType.IPV4, true),
                                                contextInfo,
                                                z);
                                        enableOrDisableRuleAsAdmin(
                                                createDenyAllRule(Firewall.AddressType.IPV6, true),
                                                contextInfo,
                                                z);
                                        break;
                                    }
                                }
                            } catch (RemoteException e) {
                                Log.e(
                                        "Firewall",
                                        "adminHaveSystemRule() - Remote Exception on get"
                                            + " getApplicationInfo",
                                        e);
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            }
        }
    }

    public final FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo) {
        return this.mDomainFilter.removeDomainFilterRules(
                enforceFirewallPermission$1(contextInfo), DomainFilterRule.CLEAR_ALL);
    }

    public final synchronized FirewallResponse clearFirewallRulesFromDb(
            FirewallRule.RuleType ruleType, ContextInfo contextInfo) {
        Log.i("Firewall", "clearFirewallRulesFromDb()");
        if (ruleType == null) {
            Log.i("Firewall", "clearFirewallRulesFromDb() - Rule type is null");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                    "The specified package name is not installed.");
        }
        int i = contextInfo.mCallerUid;
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("ruleType", ruleType.toString());
        int delete = this.mEdmStorageProvider.delete("FirewallRule", contentValues);
        if (delete == -1) {
            Log.e("Firewall", "clearFirewallRulesFromDb() - Failed to clear rules from database");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.DATABASE_ERROR,
                    "Failed to clear rules from database.");
        }
        if (delete != 0) {
            return new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.NO_ERROR,
                    "Rules successfully cleared.");
        }
        Log.i("Firewall", "clearFirewallRulesFromDb() - Rules already cleared");
        return new FirewallResponse(
                FirewallResponse.Result.NO_CHANGES,
                FirewallResponse.ErrorCode.NO_ERROR,
                "The rules are already cleared.");
    }

    public final synchronized FirewallResponse clearRuleByType(
            FirewallRule.RuleType ruleType, int i, ContextInfo contextInfo) {
        boolean z;
        try {
            int userId = UserHandle.getUserId(contextInfo.mCallerUid);
            int i2 =
                    ruleType == FirewallRule.RuleType.DENY
                            ? 2
                            : ruleType == FirewallRule.RuleType.ALLOW
                                    ? 1
                                    : ruleType == FirewallRule.RuleType.REDIRECT
                                            ? 4
                                            : ruleType == FirewallRule.RuleType.REDIRECT_EXCEPTION
                                                    ? 8
                                                    : 0;
            if ((i & i2) == 0) {
                return new FirewallResponse(
                        FirewallResponse.Result.NO_CHANGES,
                        FirewallResponse.ErrorCode.NO_ERROR,
                        "Clear was not requested for this RuleType.");
            }
            if (isFirewallEnabled(contextInfo)) {
                if (i2 == 2) {
                    FirewallRulesApplier firewallRulesApplier = this.mFirewallRulesApplier;
                    synchronized (firewallRulesApplier) {
                        firewallRulesApplier.mIcmpAllowRuleApplies = 1;
                        firewallRulesApplier.addOrRemoveIcmpAllowRule(false);
                    }
                }
                if (i2 == 2) {
                    removeExemptRules(contextInfo, userId);
                }
                if (!this.mFirewallRulesApplier.flushChain(ruleType, Integer.valueOf(userId))) {
                    Log.e("Firewall", "clearRuleByType() - Failed to disable firewall rules");
                    return new FirewallResponse(
                            FirewallResponse.Result.FAILED,
                            FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                            " failed to disable. Error: ");
                }
                if (i2 == 1) {
                    FirewallRulesApplier firewallRulesApplier2 = this.mFirewallRulesApplier;
                    synchronized (firewallRulesApplier2) {
                        z = firewallRulesApplier2.mIcmpAllowRuleApplies > 0;
                    }
                    if (z) {
                        FirewallRulesApplier firewallRulesApplier3 = this.mFirewallRulesApplier;
                        synchronized (firewallRulesApplier3) {
                            int i3 = firewallRulesApplier3.mIcmpAllowRuleApplies;
                            firewallRulesApplier3.mIcmpAllowRuleApplies = 0;
                            firewallRulesApplier3.addOrRemoveIcmpAllowRule(true);
                            firewallRulesApplier3.mIcmpAllowRuleApplies = i3;
                        }
                    }
                }
                if (i2 == 1 && (i & 2) == 0) {
                    for (FirewallExemption firewallExemption :
                            ((Map)
                                            ((HashMap) this.mFirewallExemptions)
                                                    .get(Integer.valueOf(userId)))
                                    .values()) {
                        if (firewallExemption.mIsApplied) {
                            this.mFirewallRulesApplier.addOrRemoveExemptRules(
                                    firewallExemption, true, contextInfo);
                        }
                    }
                }
            }
            return clearFirewallRulesFromDb(ruleType, contextInfo);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized FirewallResponse[] clearRules(ContextInfo contextInfo, int i) {
        FirewallResponse[] firewallResponseArr;
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        Log.i("Firewall", "clearRules()");
        firewallResponseArr = new FirewallResponse[FirewallRule.RuleType.values().length];
        firewallResponseArr[0] =
                clearRuleByType(FirewallRule.RuleType.ALLOW, i, enforceFirewallPermission$1);
        firewallResponseArr[1] =
                clearRuleByType(FirewallRule.RuleType.DENY, i, enforceFirewallPermission$1);
        firewallResponseArr[2] =
                clearRuleByType(FirewallRule.RuleType.REDIRECT, i, enforceFirewallPermission$1);
        firewallResponseArr[3] =
                clearRuleByType(
                        FirewallRule.RuleType.REDIRECT_EXCEPTION, i, enforceFirewallPermission$1);
        return firewallResponseArr;
    }

    public final synchronized FirewallResponse deleteFirewallRuleFromDb(
            ContentValues contentValues) {
        Log.i("Firewall", "deleteFirewallRuleFromDb()");
        int delete = this.mEdmStorageProvider.delete("FirewallRule", contentValues);
        if (delete == -1) {
            Log.e("Firewall", "deleteFirewallRuleFromDb() - Failed to remove rule from database");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.DATABASE_ERROR,
                    "Failed to remove/update rule from the database.");
        }
        if (delete != 0) {
            return new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.NO_ERROR,
                    "The rule was successfuly removed/updated.");
        }
        Log.e("Firewall", "deleteFirewallRuleFromDb() - Firewall rule not found");
        return new FirewallResponse(
                FirewallResponse.Result.FAILED,
                FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                "The specified FirewallRule was not found.");
    }

    public final synchronized FirewallResponse disableFirewall(ContextInfo contextInfo) {
        Log.i("Firewall", "disableFirewall()");
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        if (getFirewallOwner(userId) != i) {
            Log.e(
                    "Firewall",
                    "disableFirewall() - FAILED! NOT_AUTHORIZED_ERROR ADMINUID_IS_NOT_THE_OWNER");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR,
                    "This administrator can't execute this operation because he is not the owner.");
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        contentValues.put("userID", Integer.valueOf(userId));
        if (this.mEdmStorageProvider.delete("FirewallStatus", contentValues) == 0) {
            Log.e(
                    "Firewall",
                    "disableFirewall() - FAILED! DATABASE_ERROR"
                        + " FAIL_TO_DISABLE_FIREWALL_IN_THE_DATABASE");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.DATABASE_ERROR,
                    "Fail to disable Firewall in the database.");
        }
        FirewallRule[] rules = getRules(contextInfo, 15, null);
        if (rules.length <= 0) {
            Log.d("Firewall", "disableFirewall() - Firewall successfully disabled");
            return new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.NO_ERROR,
                    "The firewall was successfully disabled.");
        }
        StringBuilder sb = new StringBuilder();
        int length = rules.length;
        FirewallResponse[] firewallResponseArr = new FirewallResponse[length];
        int i2 = 0;
        if (!this.mFirewallRulesApplier.flushAllChains(Integer.valueOf(userId))) {
            while (i2 < length) {
                sb.append("Rule with Id = ");
                sb.append(rules[i2].getId());
                sb.append(" failed to disable. Error: ");
                sb.append("Unexpected error");
                i2++;
            }
            Log.e("Firewall", "disableFirewall() - Failed to disable Firewall");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                    sb.toString());
        }
        for (FirewallRule firewallRule : rules) {
            this.mFirewallRulesApplier.updateStatusOnDB(
                    firewallRule, FirewallRule.Status.DISABLED, contextInfo);
        }
        while (i2 < length) {
            sb.append("Rule with Id = ");
            sb.append(rules[i2].getId());
            sb.append(" successfully disabled.\n");
            i2++;
        }
        removeExemptRules(contextInfo, userId);
        return new FirewallResponse(
                FirewallResponse.Result.SUCCESS,
                FirewallResponse.ErrorCode.NO_ERROR,
                sb.toString());
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Firewall");
            return;
        }
        this.mEnterpriseDumpHelper.dumpTable(
                printWriter,
                "FirewallRule",
                new String[] {
                    "adminUid",
                    "ruleType",
                    Constants.JSON_CLIENT_DATA_STATUS,
                    "addressType",
                    "ipAddress",
                    "portNumber",
                    "portLocation",
                    "packageName",
                    "signature",
                    "networkInterface",
                    "targetIpAddress",
                    "targetPortNumber",
                    "direction",
                    "protocol"
                },
                null);
        this.mEnterpriseDumpHelper.dumpTable(
                printWriter,
                "DomainFilterTable",
                new String[] {"adminUid", "packageName", "signature", "dns1", "dns2", "networkId"},
                null);
        this.mEnterpriseDumpHelper.dumpTable(
                printWriter,
                "DomainFilterListTable",
                new String[] {"adminUid", "packageName", "typeList", "domain"},
                null);
    }

    public final synchronized FirewallResponse enableDomainFilterOnIptables(
            ContextInfo contextInfo, boolean z) {
        return this.mDomainFilter.enableDomainFilterOnIptables(
                enforceFirewallPermission$1(contextInfo), z);
    }

    public final synchronized FirewallResponse enableDomainFilterReport(
            ContextInfo contextInfo, boolean z) {
        return this.mDomainFilter.enableDomainFilterReport(
                enforceFirewallPermission$1(contextInfo), z);
    }

    public final synchronized FirewallResponse enableFirewall(ContextInfo contextInfo) {
        Log.i("Firewall", "enableFirewall()");
        int i = contextInfo.mCallerUid;
        int userId = UserHandle.getUserId(i);
        int firewallOwner = getFirewallOwner(userId);
        if (firewallOwner != -1 && firewallOwner != i) {
            Log.i(
                    "Firewall",
                    "enableFirewall() - Failed! NOT_AUTHORIZED_ERROR ADMINUID_IS_NOT_THE_OWNER");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.NOT_AUTHORIZED_ERROR,
                    "This administrator can't execute this operation because he is not the owner.");
        }
        if (firewallOwner == -1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(i));
            contentValues.put("userID", Integer.valueOf(userId));
            if (this.mEdmStorageProvider.insert("FirewallStatus", contentValues) == -1) {
                Log.e(
                        "Firewall",
                        "enableFirewall() - Failed! DATABASE_ERROR"
                            + " FAILED_TO_ENABLE_FIREWALL_IN_THE_DATABASE");
                return new FirewallResponse(
                        FirewallResponse.Result.FAILED,
                        FirewallResponse.ErrorCode.DATABASE_ERROR,
                        "Failed to enable Firewall in the database.");
            }
        }
        FirewallRule[] rules = getRules(contextInfo, 15, null);
        if (rules.length <= 0) {
            Log.d("Firewall", "enableFirewall() - Firewall successfully enabled (no rules)");
            return new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.NO_ERROR,
                    "The firewall was successfully enabled.");
        }
        FirewallResponse[] enableFirewallRules = enableFirewallRules(contextInfo, rules);
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < enableFirewallRules.length; i2++) {
            if (enableFirewallRules[i2].getResult().equals(FirewallResponse.Result.SUCCESS)) {
                if (FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR.equals(
                        enableFirewallRules[i2].getErrorCode())) {
                    sb.append(
                            "Rule with Id = "
                                    + rules[i2].getId()
                                    + " Rule not applied to INPUT chain for Direction.All.\n");
                    z2 = true;
                } else {
                    sb.append(
                            "Rule with Id = " + rules[i2].getId() + "  successfully enabled.\n\n");
                }
            } else if (enableFirewallRules[i2].getResult().equals(FirewallResponse.Result.FAILED)) {
                sb.append(
                        "Rule with Id =  "
                                + rules[i2].getId()
                                + " failed to enable. Error: "
                                + enableFirewallRules[i2].getMessage()
                                + "\n");
                z = true;
            }
        }
        if (z) {
            Log.e("Firewall", "enableFirewall() - Failed to enable Firewall");
            return new FirewallResponse(
                    FirewallResponse.Result.FAILED,
                    FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                    sb.toString());
        }
        if (shouldApplyExemptRules(rules)) {
            applyExemptRules(contextInfo, userId);
        }
        for (FirewallRule firewallRule : rules) {
            if (shouldApplyIcmpAllowRule(firewallRule)) {
                this.mFirewallRulesApplier.addOrRemoveIcmpAllowRule(true);
            }
        }
        Log.d("Firewall", "enableFirewall() - Firewall successfully enabled");
        if (z2) {
            return new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR,
                    sb.toString());
        }
        return new FirewallResponse(
                FirewallResponse.Result.SUCCESS,
                FirewallResponse.ErrorCode.NO_ERROR,
                sb.toString());
    }

    public final synchronized FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        if (z) {
            return enableFirewall(enforceFirewallPermission$1);
        }
        return disableFirewall(enforceFirewallPermission$1);
    }

    public final synchronized FirewallResponse[] enableFirewallRules(
            ContextInfo contextInfo, FirewallRule[] firewallRuleArr) {
        ContextInfo enforceFirewallPermission$1;
        FirewallRule[] firewallRuleArr2;
        try {
            enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
            Log.i("Firewall", "enableFirewallRules()");
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < firewallRuleArr.length; i++) {
                if (FirewallUtils.verifyPackageName(
                        UserHandle.getUserId(enforceFirewallPermission$1.mCallerUid),
                        firewallRuleArr[i].getApplication().getPackageName())) {
                    arrayList.add(Integer.valueOf(i));
                } else {
                    this.mFirewallRulesApplier.updateStatusOnDB(
                            firewallRuleArr[i],
                            FirewallRule.Status.DISABLED,
                            enforceFirewallPermission$1);
                }
            }
            firewallRuleArr2 = new FirewallRule[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                firewallRuleArr2[i2] = firewallRuleArr[((Integer) arrayList.get(i2)).intValue()];
            }
            Log.i("Firewall", "enableFirewallRules()");
        } catch (Throwable th) {
            throw th;
        }
        return this.mFirewallRulesApplier.enableOrDisableIptablesRule(
                firewallRuleArr2, false, enforceFirewallPermission$1, true, true);
    }

    public final void enableOrDisableRuleAsAdmin(
            FirewallRule firewallRule, ContextInfo contextInfo, boolean z) {
        FirewallResponse disableIpTablesRule;
        Log.i("Firewall", "enableOrDisableRuleAsAdmin()");
        for (int i = 0; i < 5; i++) {
            if (z) {
                Log.i(
                        "Firewall",
                        "enableOrDisableRuleAsAdmin() - Block the traffic until applying rules"
                            + " process is finished");
                FirewallRulesApplier firewallRulesApplier = this.mFirewallRulesApplier;
                firewallRulesApplier.getClass();
                disableIpTablesRule =
                        firewallRulesApplier
                                .enableOrDisableIptablesRule(
                                        new FirewallRule[] {firewallRule},
                                        true,
                                        contextInfo,
                                        false,
                                        true)[0];
            } else {
                Log.i(
                        "Firewall",
                        "enableOrDisableRuleAsAdmin() - Applying rules process is finished. Unblock"
                            + " the traffic");
                disableIpTablesRule =
                        this.mFirewallRulesApplier.disableIpTablesRule(
                                firewallRule, contextInfo, false);
            }
            if (disableIpTablesRule != null
                    && disableIpTablesRule.getResult() == FirewallResponse.Result.SUCCESS) {
                Log.d("Firewall", "enableOrDisableRuleAsAdmin() - success");
                return;
            }
        }
    }

    public final ContextInfo enforceFirewallPermission$1(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mInjector.mCtx);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(
                contextInfo,
                new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_FIREWALL")));
    }

    public final String getDefaulCaptivePortalURL() {
        DomainFilter domainFilter = this.mDomainFilter;
        if (!domainFilter.hasAnyRuleInDatabase()) {
            return null;
        }
        String string =
                Settings.Global.getString(
                        domainFilter.mContext.getContentResolver(), "captive_portal_server");
        if (string == null) {
            string = "connectivitycheck.gstatic.com";
        }
        return XmlUtils$$ExternalSyntheticOutline0.m("http://", string, "/generate_204");
    }

    public final List getDomainFilterReport(ContextInfo contextInfo, List list) {
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        DomainFilter domainFilter = this.mDomainFilter;
        domainFilter.getClass();
        Log.i("DomainFilter", "getDomainFilterReport()");
        int userId = UserHandle.getUserId(enforceFirewallPermission$1.mCallerUid);
        ArrayList arrayList = new ArrayList();
        if (!((HashSet) domainFilter.mDomainReportCache).isEmpty()) {
            if (((HashSet) domainFilter.mDomainReportCache).contains(Integer.valueOf(userId))) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("userId", Integer.valueOf(userId));
                Iterator it =
                        ((ArrayList)
                                        domainFilter.mEdmStorageProvider.getValues(
                                                "DomainFilterReportTable", null, contentValues))
                                .iterator();
                while (it.hasNext()) {
                    ContentValues contentValues2 = (ContentValues) it.next();
                    String asString = contentValues2.getAsString("packageName");
                    if (list == null || list.contains(asString)) {
                        Long asLong = contentValues2.getAsLong("time");
                        arrayList.add(
                                new DomainFilterReport(
                                        asString,
                                        asLong != null ? asLong.longValue() : 0L,
                                        contentValues2.getAsString("url")));
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0130, code lost:

       if (r4 == null) goto L55;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0136, code lost:

       if (r4.isEmpty() != false) goto L76;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0138, code lost:

       r8 = (java.lang.String) r4.get(0);
       r9 = r17 + r8.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0148, code lost:

       if (r9 > r7) goto L70;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x014a, code lost:

       r3.add(r8);
       r4.remove(0);
       r17 = r9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0153, code lost:

       r5.add(new com.samsung.android.knox.net.firewall.DomainFilterRule(r16, r2, r3, r14, r15));
       r3.clear();
       r13.setAllowDomains(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x016d, code lost:

       if (r3.isEmpty() == false) goto L59;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0173, code lost:

       if (r2.isEmpty() != false) goto L60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0192, code lost:

       if (r13.getDenyDomains().isEmpty() == false) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x019c, code lost:

       if (r13.getAllowDomains().isEmpty() == false) goto L65;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x019e, code lost:

       r4 = 0;
       r1.remove(0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01a3, code lost:

       r4 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0175, code lost:

       r5.add(new com.samsung.android.knox.net.firewall.DomainFilterRule(r16, r2, r3, r14, r15));
       r2.clear();
       r3.clear();
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getDomainFilterRules(
            com.samsung.android.knox.ContextInfo r19, java.util.List r20, int r21) {
        /*
            Method dump skipped, instructions count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.Firewall.getDomainFilterRules(com.samsung.android.knox.ContextInfo,"
                    + " java.util.List, int):java.util.List");
    }

    public final int getFirewallOwner(int i) {
        Integer asInteger;
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", Integer.valueOf(i));
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "FirewallStatus",
                                EdmStorageDefs.FIREWALL_POLICY_STATUS_COLUMNS,
                                contentValues);
        if (arrayList.isEmpty()
                || (asInteger = ((ContentValues) arrayList.get(0)).getAsInteger("adminUid"))
                        == null) {
            return -1;
        }
        return asInteger.intValue();
    }

    public final FirewallRule[] getRuleByType(
            FirewallRule.RuleType ruleType, int i, ContextInfo contextInfo, String str) {
        int i2 = contextInfo.mCallerUid;
        int i3 =
                ruleType == FirewallRule.RuleType.ALLOW
                        ? 1
                        : ruleType == FirewallRule.RuleType.DENY
                                ? 2
                                : ruleType == FirewallRule.RuleType.REDIRECT
                                        ? 4
                                        : ruleType == FirewallRule.RuleType.REDIRECT_EXCEPTION
                                                ? 8
                                                : 0;
        ContentValues contentValues = new ContentValues();
        if (i2 != 1000) {
            contentValues.put("adminUid", Integer.valueOf(i2));
        }
        if (str != null) {
            contentValues.put(Constants.JSON_CLIENT_DATA_STATUS, str);
        }
        if ((i & i3) == 0) {
            return null;
        }
        contentValues.put("ruleType", ruleType.toString());
        return FirewallUtils.getRuleFromContentValues(
                this.mEdmStorageProvider.getValues(
                        "FirewallRule", EdmStorageDefs.FIREWALL_RULE_COLUMNS, contentValues),
                ruleType);
    }

    public final FirewallRule[] getRules(ContextInfo contextInfo, int i, String str) {
        int i2;
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        FirewallRule[] ruleByType =
                getRuleByType(FirewallRule.RuleType.ALLOW, i, enforceFirewallPermission$1, str);
        FirewallRule[] ruleByType2 =
                getRuleByType(FirewallRule.RuleType.DENY, i, enforceFirewallPermission$1, str);
        FirewallRule[] ruleByType3 =
                getRuleByType(FirewallRule.RuleType.REDIRECT, i, enforceFirewallPermission$1, str);
        FirewallRule[] ruleByType4 =
                getRuleByType(
                        FirewallRule.RuleType.REDIRECT_EXCEPTION,
                        i,
                        enforceFirewallPermission$1,
                        str);
        int i3 = 0;
        int length = ruleByType != null ? ruleByType.length : 0;
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

    public final FirewallRule[] getRulesByAdminUidAndPackage(int i, String str) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("packageName", str);
        m.put("adminUid", Integer.valueOf(i));
        List values =
                this.mEdmStorageProvider.getValues(
                        "FirewallRule", EdmStorageDefs.FIREWALL_RULE_COLUMNS, m);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = (ArrayList) values;
        Iterator it = arrayList5.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (contentValues.containsKey("ruleType")) {
                int i2 =
                        AnonymousClass5
                                .$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.valueOf(contentValues.getAsString("ruleType"))
                                        .ordinal()];
                if (i2 == 1) {
                    arrayList.add(contentValues);
                } else if (i2 == 2) {
                    arrayList2.add(contentValues);
                } else if (i2 == 3) {
                    arrayList3.add(contentValues);
                } else if (i2 == 4) {
                    arrayList4.add(contentValues);
                }
            }
        }
        FirewallRule[] firewallRuleArr = new FirewallRule[arrayList5.size()];
        FirewallRule[] ruleFromContentValues =
                FirewallUtils.getRuleFromContentValues(arrayList, FirewallRule.RuleType.ALLOW);
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i4 < ruleFromContentValues.length) {
            firewallRuleArr[i5] = ruleFromContentValues[i4];
            i4++;
            i5++;
        }
        FirewallRule[] ruleFromContentValues2 =
                FirewallUtils.getRuleFromContentValues(arrayList2, FirewallRule.RuleType.DENY);
        int i6 = 0;
        while (i6 < ruleFromContentValues2.length) {
            firewallRuleArr[i5] = ruleFromContentValues2[i6];
            i6++;
            i5++;
        }
        FirewallRule[] ruleFromContentValues3 =
                FirewallUtils.getRuleFromContentValues(arrayList3, FirewallRule.RuleType.REDIRECT);
        int i7 = 0;
        while (i7 < ruleFromContentValues3.length) {
            firewallRuleArr[i5] = ruleFromContentValues3[i7];
            i7++;
            i5++;
        }
        FirewallRule[] ruleFromContentValues4 =
                FirewallUtils.getRuleFromContentValues(
                        arrayList4, FirewallRule.RuleType.REDIRECT_EXCEPTION);
        while (i3 < ruleFromContentValues4.length) {
            firewallRuleArr[i5] = ruleFromContentValues4[i3];
            i3++;
            i5++;
        }
        return firewallRuleArr;
    }

    public final void handleExemptionListOnPkgEvent(int i, Integer num, String str, String str2) {
        synchronized (this.mInitializingExemptionListLock) {
            try {
                if (str2.equals("android.intent.action.PACKAGE_REMOVED")) {
                    Map map = (Map) ((HashMap) this.mFirewallExemptions).get(num);
                    if (map == null) {
                        return;
                    }
                    FirewallExemption firewallExemption = (FirewallExemption) map.get(str);
                    if (firewallExemption == null) {
                        return;
                    }
                    if (firewallExemption.mIsApplied) {
                        int firewallOwner = getFirewallOwner(num.intValue());
                        if (firewallOwner < 0) {
                            return;
                        }
                        ContextInfo contextInfo = new ContextInfo(firewallOwner);
                        firewallExemption.mExemptionRule.setPackageUid(i);
                        this.mFirewallRulesApplier.addOrRemoveExemptRules(
                                firewallExemption, false, contextInfo);
                    }
                    ((Map) ((HashMap) this.mFirewallExemptions).get(num)).remove(str);
                } else if (str2.equals("android.intent.action.PACKAGE_ADDED")) {
                    if (!FirewallDefinitions.EXEMPT_PACKAGE_LIST.contains(str)) {
                        return;
                    }
                    if (!addAppToExemptList(num, str)) {
                        return;
                    }
                    int firewallOwner2 = getFirewallOwner(num.intValue());
                    if (firewallOwner2 < 0) {
                        return;
                    }
                    ContextInfo contextInfo2 = new ContextInfo(firewallOwner2);
                    if (shouldApplyExemptRules(getRules(contextInfo2, 2, null))) {
                        applyExemptRules(contextInfo2, num.intValue());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) {
        return this.mDomainFilter.isDomainFilterOnIptablesEnabled(
                enforceFirewallPermission$1(contextInfo));
    }

    public final boolean isDomainFilterReportEnabled(ContextInfo contextInfo) {
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        DomainFilter domainFilter = this.mDomainFilter;
        domainFilter.getClass();
        return domainFilter.isDomainFilterReportEnabledAsUser(
                UserHandle.getUserId(enforceFirewallPermission$1.mCallerUid));
    }

    public final boolean isFirewallEnabled(ContextInfo contextInfo) {
        ContentValues contentValues = new ContentValues();
        int callingOrUserUid = Utils.getCallingOrUserUid(contextInfo);
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                callingOrUserUid,
                contentValues,
                "adminUid",
                UserHandle.getUserId(callingOrUserUid),
                "userID");
        return !((ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "FirewallStatus",
                                EdmStorageDefs.FIREWALL_POLICY_STATUS_COLUMNS,
                                contentValues))
                .isEmpty();
    }

    public final String[] listIptablesRules(ContextInfo contextInfo) {
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        FirewallRulesApplier firewallRulesApplier = this.mFirewallRulesApplier;
        firewallRulesApplier.getClass();
        int userId = UserHandle.getUserId(enforceFirewallPermission$1.mCallerUid);
        ArrayList m =
                PortStatus_1_1$$ExternalSyntheticOutline0.m(
                        "************ FILTER TABLE ************");
        m.add(
                FirewallUtils.filterRulesByUser(
                        userId,
                        firewallRulesApplier.runShellCommandWithOutput(
                                4,
                                String.join(
                                        "\n",
                                        IptablesCommandBuilder.getListIptablesRestoreCommand(
                                                "filter")))));
        m.add("************ NAT TABLE ************");
        m.add(
                FirewallUtils.filterRulesByUser(
                        userId,
                        firewallRulesApplier.runShellCommandWithOutput(
                                4,
                                String.join(
                                        "\n",
                                        IptablesCommandBuilder.getListIptablesRestoreCommand(
                                                "nat")))));
        m.add("************ MANGLE TABLE ************");
        m.add(
                FirewallUtils.filterRulesByUser(
                        userId,
                        firewallRulesApplier.runShellCommandWithOutput(
                                4,
                                String.join(
                                        "\n",
                                        IptablesCommandBuilder.getListIptablesRestoreCommand(
                                                "mangle")))));
        m.add("************ RAW TABLE ************");
        m.add(
                FirewallUtils.filterRulesByUser(
                        userId,
                        firewallRulesApplier.runShellCommandWithOutput(
                                4,
                                String.join(
                                        "\n",
                                        IptablesCommandBuilder.getListIptablesRestoreCommand(
                                                "raw")))));
        if (mHasIpv6FilterSupport) {
            m.add("=============== IPV6 RULES ===============");
            m.add("************ FILTER TABLE ************");
            m.add(
                    FirewallUtils.filterRulesByUser(
                            userId,
                            firewallRulesApplier.runShellCommandWithOutput(
                                    6,
                                    String.join(
                                            "\n",
                                            IptablesCommandBuilder.getListIptablesRestoreCommand(
                                                    "filter")))));
        }
        return (String[]) m.toArray(new String[m.size()]);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
        Log.i("Firewall", "onAdminAdded(uid = " + i + ")");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        Log.i("Firewall", "onAdminRemoved(uid = " + i + ")");
        DomainFilter domainFilter = this.mDomainFilter;
        if (domainFilter != null) {
            Log.i("DomainFilter", "adminRemoved() - adminUid: " + i);
            int userId = UserHandle.getUserId(i);
            Integer valueOf = Integer.valueOf(userId);
            if (!domainFilter.isDomainFilterReportEnabledAsUser(userId)
                    && ((HashSet) domainFilter.mDomainReportCache).contains(valueOf)) {
                ((HashSet) domainFilter.mDomainReportCache).remove(valueOf);
                ContentValues contentValues = new ContentValues();
                contentValues.put("userId", Integer.valueOf(userId));
                domainFilter.mEdmStorageProvider.delete("DomainFilterReportTable", contentValues);
            }
            if (domainFilter.hasAnyRuleInDatabase()) {
                return;
            }
            domainFilter.sendToCache(5, "", null, null, null, null);
            domainFilter.sendToCache(5, domainFilter.mServer, null, null, null, null);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        Log.i("Firewall", "onPreAdminRemoval(uid = " + i + ")");
        ContextInfo contextInfo = new ContextInfo(i);
        if (getFirewallOwner(UserHandle.getUserId(i)) == i) {
            blockOrUnblockAll(i, false);
            Firewall firewall = this.mFirewallRulesApplier.mFirewallService;
            firewall.enableFirewall(contextInfo, false);
            firewall.clearRules(contextInfo, 15);
            firewall.getRules(contextInfo, 15, null);
        }
        DomainFilter domainFilter = this.mDomainFilter;
        if (domainFilter != null) {
            Log.i("DomainFilter", "onPreAdminRemoval(adminUid = " + String.valueOf(i) + ")");
            int userId = UserHandle.getUserId(i);
            if (domainFilter.checkAdminInDatabase(i) == 1) {
                domainFilter.sendToCache(2, Integer.toString(userId), null, null, null, null);
                domainFilter.releaseNetworks(i);
                if (domainFilter.isDomainFilterOnIptablesEnabled(new ContextInfo(i))) {
                    domainFilter.mFirewallRulesApplier.flushDomainChains(
                            Integer.valueOf(userId), false);
                }
            }
        }
    }

    public final void populateDomainFilterBrokenRules(ContextInfo contextInfo, List list, int i) {
        enforceFirewallPermission$1(contextInfo);
        DomainFilter domainFilter = this.mDomainFilter;
        synchronized (domainFilter) {
            try {
                List list2 =
                        (List)
                                ((HashMap) domainFilter.mDomainFilterBrokenRules)
                                        .get(Integer.valueOf(i));
                if (list2 == null) {
                    list2 = new ArrayList();
                }
                list2.addAll(list);
                ((HashMap) domainFilter.mDomainFilterBrokenRules).put(Integer.valueOf(i), list2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void populateFirewallExemptionList(Integer num) {
        Map map = (Map) this.mFirewallExemptions.get(num);
        if (num.intValue() == 0 && !map.containsKey("netd")) {
            FirewallRule firewallRule =
                    new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
            firewallRule.setDirection(Firewall.Direction.OUTPUT);
            firewallRule.setApplication(new AppIdentity("netd", (String) null));
            firewallRule.setPackageUid(1051);
            map.put("netd", new FirewallExemption(firewallRule));
        }
        Iterator it = FirewallDefinitions.EXEMPT_PACKAGE_LIST.iterator();
        while (it.hasNext()) {
            addAppToExemptList(num, (String) it.next());
        }
    }

    public final FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i) {
        FirewallResponse[] removeDomainFilterRules;
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        DomainFilter domainFilter = this.mDomainFilter;
        synchronized (domainFilter) {
            removeDomainFilterRules =
                    domainFilter.removeDomainFilterRules(
                            enforceFirewallPermission$1,
                            domainFilter.reassembleDomainFilterBrokenRules(i));
        }
        return removeDomainFilterRules;
    }

    public final void removeExemptRules(ContextInfo contextInfo, int i) {
        for (FirewallExemption firewallExemption :
                ((Map) ((HashMap) this.mFirewallExemptions).get(Integer.valueOf(i))).values()) {
            if (firewallExemption.mIsApplied) {
                this.mFirewallRulesApplier.addOrRemoveExemptRules(
                        firewallExemption, false, contextInfo);
                firewallExemption.mIsApplied = false;
            }
        }
    }

    public final synchronized FirewallResponse removeRule(
            ContextInfo contextInfo, FirewallRule firewallRule) {
        Log.i("Firewall", "removeRule()");
        int i = contextInfo.mCallerUid;
        FirewallResponse validateFirewallRule =
                FirewallRuleValidator.validateFirewallRule(firewallRule);
        FirewallResponse.Result result = validateFirewallRule.getResult();
        FirewallResponse.Result result2 = FirewallResponse.Result.FAILED;
        if (result == result2) {
            Log.i("Firewall", "removeRule() - Invalid Firewall Rule");
            return validateFirewallRule;
        }
        if (!TextUtils.isEmpty(firewallRule.getApplication().getSignature())) {
            Context createContextAsUser =
                    Utils.createContextAsUser(this.mContext, "android", 0, UserHandle.getUserId(i));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!Utils.comparePackageSignature(
                        0,
                        createContextAsUser,
                        firewallRule.getApplication().getPackageName(),
                        firewallRule.getApplication().getSignature())) {
                    Log.i("Firewall", "removeRule() - Signature mismatch");
                    return new FirewallResponse(
                            result2,
                            FirewallResponse.ErrorCode.INVALID_PARAMETER_ERROR,
                            "Given signature does not match with the application.");
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (!FirewallUtils.isRuleEnabled(firewallRule, i, this.mEdmStorageProvider)) {
            return deleteFirewallRuleFromDb(
                    FirewallUtils.getContentValuesFromRule(firewallRule, i));
        }
        Log.i("Firewall", "removeRule() - Failed to remove rule. The rule must be disabled.");
        return new FirewallResponse(
                result2,
                FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                "Disable the rule before remove it.");
    }

    public final synchronized FirewallResponse[] removeRules(
            ContextInfo contextInfo, FirewallRule[] firewallRuleArr) {
        ContextInfo enforceFirewallPermission$1 = enforceFirewallPermission$1(contextInfo);
        Log.i("Firewall", "removeRules()");
        if (firewallRuleArr == null) {
            Log.i("Firewall", "removeRules() - No rule specified");
            return null;
        }
        FirewallResponse[] enableOrDisableIptablesRule =
                this.mFirewallRulesApplier.enableOrDisableIptablesRule(
                        firewallRuleArr, false, enforceFirewallPermission$1, true, false);
        FirewallResponse[] firewallResponseArr = new FirewallResponse[firewallRuleArr.length];
        for (int i = 0; i < firewallRuleArr.length; i++) {
            FirewallResponse firewallResponse = enableOrDisableIptablesRule[i];
            if (firewallResponse != null
                    && firewallResponse.getResult().equals(FirewallResponse.Result.SUCCESS)) {
                firewallRuleArr[i].setStatus(FirewallRule.Status.DISABLED);
            }
            FirewallResponse removeRule =
                    removeRule(enforceFirewallPermission$1, firewallRuleArr[i]);
            if (removeRule.getResult().equals(FirewallResponse.Result.SUCCESS)) {
                firewallResponseArr[i] = removeRule;
            } else {
                FirewallResponse.Result result = FirewallResponse.Result.FAILED;
                FirewallResponse.ErrorCode errorCode = removeRule.getErrorCode();
                StringBuilder sb = new StringBuilder();
                sb.append(
                        enableOrDisableIptablesRule[i] == null
                                ? ""
                                : enableOrDisableIptablesRule[i].getMessage() + "/");
                sb.append(removeRule.getMessage());
                firewallResponseArr[i] = new FirewallResponse(result, errorCode, sb.toString());
            }
        }
        for (FirewallRule firewallRule : firewallRuleArr) {
            if (shouldApplyIcmpAllowRule(firewallRule)) {
                this.mFirewallRulesApplier.addOrRemoveIcmpAllowRule(false);
            }
        }
        if (!shouldApplyExemptRules(getRules(enforceFirewallPermission$1, 2, null))) {
            removeExemptRules(
                    enforceFirewallPermission$1,
                    UserHandle.getUserId(enforceFirewallPermission$1.mCallerUid));
        }
        return firewallResponseArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x0294, code lost:

       r18 = r5;
       r17 = r6;
       r19 = r7;
       r5 = r3.iterator();
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x02a2, code lost:

       if (r5.hasNext() == false) goto L190;
    */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x02a4, code lost:

       r6 = (android.content.ContentValues) r5.next();
       r7 = r6.getAsString(r2);
       r23 = r2;
       r2 = com.samsung.android.knox.net.firewall.FirewallRule.RuleType.DENY;
       r16 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x02bc, code lost:

       if (r7.equals(r2.toString()) == false) goto L191;
    */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x02be, code lost:

       r2 = com.android.server.enterprise.firewall.FirewallUtils.getRuleFromContentValues(java.util.Arrays.asList(r6), r2)[0];
    */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x02d2, code lost:

       if (r2.getAddressType() == r1) goto L127;
    */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x02e1, code lost:

       if ("*".equals(r2.getIpAddress()) != false) goto L133;
    */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x02eb, code lost:

       if (r13.equals(r2.getIpAddress()) != false) goto L133;
    */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x02f1, code lost:

       if (matchIpIntoIpRangeRule(r13, r2) == false) goto L193;
    */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x02d4, code lost:

       r2 = r23;
       r3 = r16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x02fb, code lost:

       if ("*".equals(r2.getPortNumber()) != false) goto L139;
    */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0305, code lost:

       if (r0.equals(r2.getPortNumber()) != false) goto L139;
    */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x030b, code lost:

       if (matchPortIntoPortRangeRule(r0, r2) == false) goto L194;
    */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0317, code lost:

       if (com.samsung.android.knox.net.firewall.Firewall.Protocol.ALL.equals(r2.getProtocol()) != false) goto L181;
    */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0323, code lost:

       if (com.samsung.android.knox.net.firewall.Firewall.Protocol.TCP.equals(r2.getProtocol()) == false) goto L195;
    */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0325, code lost:

       r8 = r15;
    */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0327, code lost:

       r12 = r12 + 1;
       r6 = r17;
       r5 = r18;
       r7 = r19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldBlockDownload(java.lang.String r21, java.lang.String r22, int r23) {
        /*
            Method dump skipped, instructions count: 869
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.Firewall.shouldBlockDownload(java.lang.String,"
                    + " java.lang.String, int):boolean");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        Log.i("Firewall", "systemReady()");
    }
}
