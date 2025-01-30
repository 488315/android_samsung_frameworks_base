package com.android.server.alarm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.accounts.OnAccountsUpdateListener;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.alarm.AppSyncWrapper;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class AppSyncInfo extends AppSyncWrapper {
    public static long EXP_MIN_INEXACT_WINDOW = 300000;
    public static long SUSPICIOUS_TIME_THRESHOLD = 10000;
    public IntentFilter boot_filter;
    public AccountListener mAccountListener;
    public final ArrayList mAccountsPackages;
    public final PackageList mAllowlistPackages;
    public final ArrayList mAllowlistPackagesFromSCPM;
    public final PackageList mBlocklistAppSync3P;
    public final PackageList mBlocklistPackages;
    public final ArrayList mBlocklistPackagesFromConfig;
    public final ArrayList mBlocklistPackagesFromConfig3P;
    public final ArrayList mBlocklistPackagesFromSCPM;
    public BroadcastReceiver mBootIntentReceiver;
    public boolean mCharging;
    public Context mContext;
    public final ArrayList mCscPackages;
    public IntentFilter mFilter = null;
    public final Object mLockAllowlistFromSCPM;
    public final Object mLockBlocklistFromSCPM;
    public final Object mLockExt;
    public final Object mLockSuspiciousTagSet;
    public final Object mLockSuspiciousTagSetFromApi;
    public final Object mLockSuspiciousTagSetFromSCPM;
    public final ArrayList mPermanentAllowlistPackages;
    public PackageManager mPm;
    public IntentReceiver mReceiver;
    public SCPMBroadcastReceiver mSCPMReceiver;
    public boolean mScreenOn;
    public Set mSuspiciousTagSet;
    public Set mSuspiciousTagSetFromApi;
    public Set mSuspiciousTagSetFromSCPM;
    public final ArrayList mTargetPackages;
    public Set preloadedPackages;

    public final boolean isSystemApplication(int i) {
        return (i & 1) > 0;
    }

    public static AppSyncWrapper createAppSync(Context context) {
        if (ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY))) {
            return new AppSyncInfo(context);
        }
        return new DummyAppSync();
    }

    public AppSyncInfo(Context context) {
        this.mReceiver = null;
        Object obj = new Object();
        this.mLockExt = obj;
        this.mLockSuspiciousTagSet = new Object();
        this.mSuspiciousTagSet = new HashSet();
        this.mLockSuspiciousTagSetFromApi = new Object();
        this.mSuspiciousTagSetFromApi = new HashSet();
        this.mLockSuspiciousTagSetFromSCPM = new Object();
        this.mSuspiciousTagSetFromSCPM = new HashSet();
        this.preloadedPackages = new HashSet();
        this.mTargetPackages = new ArrayList();
        this.mAccountsPackages = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.mCscPackages = arrayList;
        PackageList packageList = new PackageList();
        this.mBlocklistPackages = packageList;
        PackageList packageList2 = new PackageList();
        this.mBlocklistAppSync3P = packageList2;
        ArrayList arrayList2 = new ArrayList();
        this.mBlocklistPackagesFromConfig = arrayList2;
        this.mBlocklistPackagesFromConfig3P = new ArrayList();
        PackageList packageList3 = new PackageList();
        this.mAllowlistPackages = packageList3;
        this.mLockAllowlistFromSCPM = new Object();
        this.mAllowlistPackagesFromSCPM = new ArrayList();
        this.mLockBlocklistFromSCPM = new Object();
        this.mBlocklistPackagesFromSCPM = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        this.mPermanentAllowlistPackages = arrayList3;
        this.boot_filter = new IntentFilter();
        this.mBootIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.alarm.AppSyncInfo.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                AppSyncInfo.this.updateSuspiciousPolicy(context2);
                AccountManager accountManager = (AccountManager) context2.getSystemService("account");
                if (accountManager != null) {
                    try {
                        accountManager.addOnAccountsUpdatedListener(AppSyncInfo.this.mAccountListener, null, true);
                    } catch (IllegalStateException e) {
                        Slog.v("AppSyncInfo", "Ignore Exception : " + e.getMessage());
                    }
                }
            }
        };
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mScreenOn = true;
        this.mCharging = false;
        IntentReceiver intentReceiver = new IntentReceiver();
        this.mReceiver = intentReceiver;
        this.mContext.registerReceiver(intentReceiver, this.mFilter);
        this.mAccountListener = new AccountListener();
        this.mSCPMReceiver = new SCPMBroadcastReceiver(context);
        synchronized (obj) {
            arrayList.clear();
            packageList.clear();
            packageList2.clear();
            arrayList3.clear();
            arrayList.add("com.sec.spp.push");
            arrayList.add("com.sec.chaton");
            arrayList.add("com.facebook.katana");
            arrayList.add("com.twitter.android");
            arrayList.add("com.facebook.orca");
            arrayList.add("com.kakao.talk");
            arrayList.add("com.google.android.apps.plus");
            arrayList.add("com.mobilesrepublic.appygeek");
            arrayList.add("mnn.Android");
            arrayList.add("com.google.android.apps.maps");
            packageList.loadAppSyncBlockList();
            packageList2.loadAppSync3PlusBlockList();
            packageList3.loadChnAllowlist();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!this.mBlocklistPackages.contains(str)) {
                    this.mBlocklistPackages.add(str);
                }
            }
            Iterator it2 = this.mBlocklistPackagesFromConfig3P.iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                if (!this.mBlocklistAppSync3P.contains(str2)) {
                    this.mBlocklistAppSync3P.add(str2);
                }
            }
            this.mPermanentAllowlistPackages.add("com.samsung.location");
            this.mTargetPackages.clear();
            Iterator it3 = this.mCscPackages.iterator();
            while (it3.hasNext()) {
                this.mTargetPackages.add((String) it3.next());
            }
            Iterator it4 = this.mPermanentAllowlistPackages.iterator();
            while (it4.hasNext()) {
                this.mTargetPackages.add((String) it4.next());
            }
            for (int i = 0; i < this.mTargetPackages.size(); i++) {
                Slog.v("AppSyncInfo", "<TargetPackages> " + i + ": " + ((String) this.mTargetPackages.get(i)));
            }
            this.boot_filter.addAction("android.intent.action.BOOT_COMPLETED");
            this.mContext.registerReceiver(this.mBootIntentReceiver, this.boot_filter);
            this.preloadedPackages.add("com.facebook.services");
            this.preloadedPackages.add("com.facebook.katana");
            this.preloadedPackages.add("com.facebook.orca");
            this.preloadedPackages.add("com.facebook.pages.app");
            this.preloadedPackages.add("com.facebook.appmanager");
            this.preloadedPackages.add("com.facebook.system");
            this.preloadedPackages.add("com.whatsapp");
            this.preloadedPackages.add("com.instagram.android");
            this.preloadedPackages.add("com.skype.raider");
            this.preloadedPackages.add("com.microsoft.skydrive");
            this.preloadedPackages.add("com.samsung.android.opencalendar");
        }
    }

    public final boolean isIgnorePackage(String str) {
        if (str == null) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        return this.mBlocklistPackages.contains(lowerCase) || this.mBlocklistAppSync3P.contains(lowerCase);
    }

    public boolean isSuspiciousAlarm(int i, long j, int i2, String str) {
        long elapsedRealtime;
        if (i <= 1) {
            elapsedRealtime = System.currentTimeMillis();
        } else {
            elapsedRealtime = SystemClock.elapsedRealtime();
        }
        if (j - elapsedRealtime >= SUSPICIOUS_TIME_THRESHOLD) {
            return true;
        }
        return containsPackageAsUser(AppSyncWrapper.SET_TYPE.SUSPICIOUS_PACKAGES, str, UserHandle.getUserId(i2));
    }

    public boolean isTargetApplication(int i, String str) {
        ApplicationInfo applicationInfo;
        if (!UserHandle.isApp(i)) {
            return false;
        }
        try {
            applicationInfo = this.mPm.getApplicationInfoAsUser(str, 0, UserHandle.getUserId(i));
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            return false;
        }
        if ((!isSystemApplication(applicationInfo.flags) || this.preloadedPackages.contains(str.toLowerCase()) || this.mTargetPackages.contains(str.toLowerCase())) && !isIgnorePackage(str) && isActivePackage(i, str)) {
            return ((this.mAllowlistPackages.contains(str.toLowerCase()) || this.mAllowlistPackagesFromSCPM.contains(str.toLowerCase())) && !this.mBlocklistPackagesFromSCPM.contains(str.toLowerCase())) || this.preloadedPackages.contains(str.toLowerCase()) || this.mTargetPackages.contains(str.toLowerCase());
        }
        return false;
    }

    public static boolean isActivePackage(int i, String str) {
        if (str == null) {
            return false;
        }
        try {
            return ((UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class)).getAppStandbyBucket(str, UserHandle.getUserId(i), SystemClock.elapsedRealtime()) <= 10;
        } catch (Exception unused) {
            return false;
        }
    }

    public final void initFilter() {
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mFilter.addAction("android.intent.action.SCREEN_ON");
        this.mFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        this.mFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
    }

    @Override // com.android.server.alarm.AppSyncWrapper
    public long getWindowLength() {
        return EXP_MIN_INEXACT_WINDOW;
    }

    @Override // com.android.server.alarm.AppSyncWrapper
    public boolean isAdjustableAlarm(int i, long j, long j2, long j3, int i2, String str) {
        if (EXP_MIN_INEXACT_WINDOW <= 0 || UserHandle.isCore(i2) || !isTargetApplication(i2, str) || !isSuspiciousAlarm(i, j, i2, str)) {
            return false;
        }
        if (j2 < 0 || j2 >= EXP_MIN_INEXACT_WINDOW) {
            return j2 < 0 && j3 > 0 && ((double) j3) * 0.75d < ((double) EXP_MIN_INEXACT_WINDOW);
        }
        return true;
    }

    @Override // com.android.server.alarm.AppSyncWrapper
    public void dump(PrintWriter printWriter, String str) {
        printWriter.println("");
        printWriter.println("<AppSyncInfo>");
        printWriter.println("mCharging: " + this.mCharging);
        printWriter.println("mScreenOn: " + this.mScreenOn);
        printWriter.println("SUSP_THRE: " + SUSPICIOUS_TIME_THRESHOLD);
        printWriter.println("INEXACT_WIN: " + EXP_MIN_INEXACT_WINDOW);
        printWriter.println("  <AppSync3 Allowlist>");
        Iterator it = this.mCscPackages.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            printWriter.print(str);
            printWriter.println("   (AppSync) " + str2);
        }
        printWriter.print(str);
        printWriter.println("   (AppSync) ---------");
        printWriter.println("  <AppSync3 TargetList>");
        Iterator it2 = this.mTargetPackages.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            printWriter.print(str);
            printWriter.println("   (AppSync) " + str3);
        }
        printWriter.print(str);
        printWriter.println("   (AppSync) ---------");
        printWriter.println("  <AppSync3 Blocklist>");
        printWriter.print(str);
        printWriter.println("   (AppSync) " + this.mBlocklistPackages.toString());
        printWriter.print(str);
        printWriter.println("   (AppSync) ---------");
        printWriter.println("  <AppSync3p Blocklist>");
        printWriter.print(str);
        printWriter.println("   (AppSync) " + this.mBlocklistAppSync3P.toString());
        printWriter.print(str);
        printWriter.println("   (AppSync) ---------");
        printWriter.println("");
    }

    public class IntentReceiver extends BroadcastReceiver {
        public IntentReceiver() {
            AppSyncInfo.this.initFilter();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                AppSyncInfo.this.mScreenOn = false;
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                AppSyncInfo.this.mScreenOn = true;
            } else if ("android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
                AppSyncInfo.this.mCharging = true;
            } else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(intent.getAction())) {
                AppSyncInfo.this.mCharging = false;
            }
        }
    }

    public class AccountListener implements OnAccountsUpdateListener {
        public boolean ALLOWLIST_PLUS_AUTO_ADD;

        public AccountListener() {
            this.ALLOWLIST_PLUS_AUTO_ADD = false;
        }

        public final void queryAccounts() {
            AccountManager accountManager = (AccountManager) AppSyncInfo.this.mContext.getSystemService("account");
            if (accountManager == null) {
                return;
            }
            AuthenticatorDescription[] authenticatorTypes = accountManager.getAuthenticatorTypes();
            AppSyncInfo.this.mAccountsPackages.clear();
            for (AuthenticatorDescription authenticatorDescription : authenticatorTypes) {
                if (!AppSyncInfo.this.mAccountsPackages.contains(authenticatorDescription.packageName)) {
                    AppSyncInfo.this.mAccountsPackages.add(authenticatorDescription.packageName);
                }
            }
            if (this.ALLOWLIST_PLUS_AUTO_ADD) {
                AppSyncInfo.this.mTargetPackages.clear();
                Iterator it = AppSyncInfo.this.mCscPackages.iterator();
                while (it.hasNext()) {
                    AppSyncInfo.this.mTargetPackages.add((String) it.next());
                }
                Iterator it2 = AppSyncInfo.this.mAccountsPackages.iterator();
                while (it2.hasNext()) {
                    if (AppSyncInfo.this.mTargetPackages.contains((String) it2.next())) {
                        it2.remove();
                    }
                }
                Iterator it3 = AppSyncInfo.this.mAccountsPackages.iterator();
                while (it3.hasNext()) {
                    AppSyncInfo.this.mTargetPackages.add((String) it3.next());
                }
                Iterator it4 = AppSyncInfo.this.mTargetPackages.iterator();
                while (it4.hasNext()) {
                    Slog.v("AppSyncInfo", "<TargetPackages> " + ((String) it4.next()));
                }
                return;
            }
            Iterator it5 = AppSyncInfo.this.mAccountsPackages.iterator();
            while (it5.hasNext()) {
                Slog.v("AppSyncInfo", "<AccPackages> " + ((String) it5.next()));
            }
        }

        @Override // android.accounts.OnAccountsUpdateListener
        public void onAccountsUpdated(Account[] accountArr) {
            queryAccounts();
        }
    }

    public interface Sales {
        public static final boolean ATT;
        public static final boolean CANADA;
        public static final boolean CHINA;
        public static final boolean JAPAN;
        public static final boolean MPCS;
        public static final boolean NORTH_AMERICA;
        public static final String SALES_CODE;
        public static final boolean SPR;
        public static final boolean TMO;
        public static final boolean USCC;
        public static final boolean VZW;

        static {
            String upperCase = SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase();
            SALES_CODE = upperCase;
            boolean equals = "VZW".equals(upperCase);
            VZW = equals;
            boolean z = "ATT".equals(upperCase) || "AIO".equals(upperCase) || "CRI".equals(upperCase);
            ATT = z;
            boolean equals2 = "TMB".equals(upperCase);
            TMO = equals2;
            MPCS = "TMK".equals(upperCase);
            boolean z2 = "SPR".equals(upperCase) || "BST".equals(upperCase) || "VMU".equals(upperCase) || "XAS".equals(upperCase);
            SPR = z2;
            boolean z3 = "USC".equals(upperCase) || "LRA".equals(upperCase) || "ACG".equals(upperCase);
            USCC = z3;
            CANADA = "RWC".equals(upperCase) || "FMC".equals(upperCase) || "MTA".equals(upperCase) || "CHR".equals(upperCase) || "MTS".equals(upperCase) || "TLS".equals(upperCase) || "KDO".equals(upperCase) || "SPC".equals(upperCase) || "CLN".equals(upperCase) || "BMC".equals(upperCase) || "VMC".equals(upperCase) || "PCM".equals(upperCase) || "SOL".equals(upperCase) || "BWA".equals(upperCase) || "GLW".equals(upperCase) || "VTR".equals(upperCase) || "ESK".equals(upperCase) || "PMB".equals(upperCase) || "XAC".equals(upperCase);
            CHINA = "CHN".equals(upperCase) || "CHU".equals(upperCase) || "CTC".equals(upperCase) || "CHM".equals(upperCase) || "CHC".equals(upperCase);
            JAPAN = "DCM".equals(upperCase);
            NORTH_AMERICA = equals || z || equals2 || z2 || z3 || "XAR".equals(upperCase) || "MTR".equals(upperCase) || "SPT".equals(upperCase) || "CSP".equals(upperCase) || "TFN".equals(upperCase) || "BNN".equals(upperCase);
        }
    }

    public class PackageList {
        public HashSet mPackageSet = new HashSet();
        public ArrayList mRegExpList = new ArrayList();

        public void add(String str) {
            if (str == null) {
                return;
            }
            if (str.contains("*")) {
                if (this.mRegExpList.contains(str)) {
                    return;
                }
                this.mRegExpList.add(str.toLowerCase());
            } else {
                if (this.mPackageSet.contains(str)) {
                    return;
                }
                this.mPackageSet.add(str.toLowerCase());
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.mPackageSet.iterator();
            while (it.hasNext()) {
                sb.append(((String) it.next()) + ", ");
            }
            Iterator it2 = this.mRegExpList.iterator();
            while (it2.hasNext()) {
                sb.append(((String) it2.next()) + ", ");
            }
            return sb.toString();
        }

        public boolean contains(String str) {
            if (str == null) {
                return false;
            }
            boolean contains = this.mPackageSet.contains(str);
            if (contains) {
                return contains;
            }
            Iterator it = this.mRegExpList.iterator();
            while (it.hasNext()) {
                if (str.matches((String) it.next())) {
                    return true;
                }
            }
            return contains;
        }

        public void clear() {
            this.mPackageSet.clear();
            this.mRegExpList.clear();
        }

        public void loadAppSyncBlockList() {
            add(".*alarm.*");
            add(".*clock.*");
            add("com.android.email");
            add("com.samsung.android.email.sync");
            add("com.sec\\..*ims.*");
            add("com.sec.epdg");
            add("com.samsung\\..*ims.*");
            add("com.samsung.android.themecenter");
            add("com.iloen.melon");
            add("com.iloen.melon.tablet");
            add(".*knox.*");
            add("android");
            add(".*email.ui");
            add(".*shealth.*");
            if (Sales.JAPAN) {
                add("com.android.incallui");
                add("com.android.services.telephony.common");
            }
        }

        public void loadChnAllowlist() {
            add("com.tencent.mobileqq");
            add("com.sohu.inputmethod.sogou");
            add("com.eg.android.AlipayGphone");
            add("com.alibaba.android.rimet");
            add("com.baidu.map.location");
        }

        public void loadAppSync3PlusBlockList() {
            add(".*reminder.*");
            add(".*alert.*");
            add("com.sec.screencheck");
            add("com.sec.dsm.system");
            add("com.samsung.android.fmm");
            add("com.samsung.ssd.wolfserver");
            add("ch.bitspin.timely");
            add("com.nhn.android.nmap");
            add("com.qihoo.security");
            add(".*vodafone.*");
            add("com.blackberry.enterprise.bscp.*");
            add("com.google.android.ims");
            add("com.google.android.apps.messaging");
            add(".*vzw.*");
            add(".*verizon.*");
            if (Sales.VZW) {
                add(".*amazon.*");
                add("com.audible.application");
                add("com.imdb.mobile");
                add("com.amazon.fv");
                add("com.gotv.nflgamecenter.us.lite");
                add("com.slacker.radio");
                add("com.telecomsys.directedsms.android.SCG");
                add("com.LogiaGroup.LogiaDeck");
                add("com.vznavigator.Generic");
                add("com.cequint.ecid");
                add("com.vcast.mediamanager");
                add("com.sec.android.app.cmas");
                add("com.samsung.spg");
                add("com.sec.android.app.setupwizard");
                add("com.samsung.vvm");
                add("com.samsung.carrier.logcollector");
                add("com.samsung.sdm");
                add("com.samsung.syncmlservice");
                add("com.samsung.syncmlphonedataservice");
                add("com.samsung.sdm.sdmviewer");
                add("com.samsung.PAYGPrePayDetection");
                add("com.fusionone.android.sync.vzbuaclient");
            }
            add(".*att.*");
            add(".*mizmowireless.*");
            if (Sales.ATT) {
                add("com.welldoc.diabetesmanager");
                add("deezer.android.app");
            }
            add(".*sprint.*");
            if (Sales.SPR) {
                add("com.airg");
                add("com.amazon.mShop");
                add("com.amazon.mShop.android");
                add("com.amazon.avod.thirdpartyclient");
                add("com.amazon.mp3");
                add("com.amazon.clouddrive.photos");
                add("com.boostmobile.boosttv");
                add("com.coremobility.app.vnotes");
                add("com.ebay.mobile");
                add("com.familyandco.framilywall");
                add("com.handmark.expressweather");
                add("com.itsoninc.android.itsonclient");
                add("com.itsoninc.android.itsonservice");
                add("com.itsoninc.android.uid");
                add("com.kineto.smartwifi");
                add("com.livewiremobile.musicstore.boost");
                add("com.locationlabs.sparkle.yellow.pre");
                add("com.lookout");
                add("com.nascar.nascarmobile");
                add("com.nbadigital.gametimelite");
                add("com.nextradioapp.nextradio");
                add("com.oem.smartwifisupport");
                add("com.pinsight.v1");
                add("com.smithmicro.EDM");
                add("com.smithmicro.mnd");
                add("com.soleo.numbersearch");
                add("com.spotify.music");
                add("com.telenav.app.android.scout_us");
                add("com.ubercab");
                add("com.wipit.android.boostwallet");
                add("msgplus.jibe.sca");
                add("com.privacystar.android.spg");
                add("com.playphone.gamestore");
                add("com.livewiremobile.musicstore.vmu");
            }
            add(".*tmobile.*");
            if (Sales.TMO) {
                add("com.amazon.mShop.android");
                add("com.lookout");
                add("com.customermobile.preload");
            }
            add(".*metro.*");
            if (Sales.MPCS) {
                add("com.tmobile.pr.adapt");
                add("com.lookout");
                add("com.mobileposse.client");
                add("com.sec.tetheringprovision");
            }
            add(".*tracfone.*");
            add(".*uscc.*");
            if (Sales.USCC) {
                add("com.cequint.ecid");
                add("com.synchronoss.sm");
                add("com.amazon.mShop.android");
                add("com.LogiaGroup.LogiaDeck");
                add("com.zed.TrdWapLauncher");
                add("com.amazon.windowshop");
            }
            if (Sales.JAPAN) {
                add("com.nttdocomo.*");
                add("jp.co.nttdocomo.*");
                add("com.ipg.gguide.*");
                add("com.rsupport.rs.activity.ntt");
                add("com.mcafee.vsm_android_dcm");
                add("com.mcafee.safecall.docomo");
                add("com.mcafee.android.scanservice");
                add("com.showcasegig.devlawson");
                add("jp.id_credit_sp.android");
                add("jp.dmapnavi.navi02");
                add("jp.co.mcdonalds.android");
                add("jp.co.lawson.activity");
                add("jp.co.omronsoft.android.decoemojimanager_docomo");
                add("org.simalliance.openmobileapi.service");
            }
        }
    }

    public final String getPackageTag(String str, int i) {
        return str + "/" + i;
    }

    /* renamed from: com.android.server.alarm.AppSyncInfo$2 */
    public abstract /* synthetic */ class AbstractC04742 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$alarm$AppSyncWrapper$SET_TYPE;

        static {
            int[] iArr = new int[AppSyncWrapper.SET_TYPE.values().length];
            $SwitchMap$com$android$server$alarm$AppSyncWrapper$SET_TYPE = iArr;
            try {
                iArr[AppSyncWrapper.SET_TYPE.SUSPICIOUS_PACKAGES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public final boolean containsPackageAsUser(AppSyncWrapper.SET_TYPE set_type, String str, int i) {
        if (str != null && AbstractC04742.$SwitchMap$com$android$server$alarm$AppSyncWrapper$SET_TYPE[set_type.ordinal()] == 1) {
            return containsTagWithLock(this.mLockSuspiciousTagSet, this.mSuspiciousTagSet, str, i);
        }
        return false;
    }

    public final boolean containsTagWithLock(Object obj, Collection collection, String str, int i) {
        boolean z;
        if (obj == null || collection == null || str == null) {
            return false;
        }
        String packageTag = getPackageTag(str, i);
        String packageTag2 = getPackageTag(str, -1);
        synchronized (obj) {
            z = collection.contains(packageTag) || collection.contains(packageTag2);
        }
        return z;
    }

    public final void addCollection(Collection collection, Collection collection2) {
        if (collection == null || collection2 == null) {
            return;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            collection2.add((String) it.next());
        }
    }

    public final void updateSuspiciousTags() {
        HashSet hashSet = new HashSet();
        synchronized (this.mLockSuspiciousTagSetFromSCPM) {
            hashSet.addAll(this.mSuspiciousTagSetFromSCPM);
        }
        synchronized (this.mLockSuspiciousTagSetFromApi) {
            hashSet.addAll(this.mSuspiciousTagSetFromApi);
        }
        synchronized (this.mLockSuspiciousTagSet) {
            this.mSuspiciousTagSet = hashSet;
        }
    }

    public final void updateSuspiciousPolicy(Context context) {
        SCPMHelper sCPMHelper = new SCPMHelper(context);
        boolean isSCPMAvailable = sCPMHelper.isSCPMAvailable();
        long j = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
        if (!isSCPMAvailable) {
            SUSPICIOUS_TIME_THRESHOLD = 10000L;
            EXP_MIN_INEXACT_WINDOW = BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            synchronized (this.mLockSuspiciousTagSetFromSCPM) {
                this.mSuspiciousTagSetFromSCPM.clear();
            }
            updateSuspiciousTags();
            return;
        }
        long suspiciousTimeLimitFromSCPM = sCPMHelper.getSuspiciousTimeLimitFromSCPM();
        long inexactWindowFromSCPM = sCPMHelper.getInexactWindowFromSCPM();
        ArrayList suspiciousTagFromSCPM = sCPMHelper.getSuspiciousTagFromSCPM();
        ArrayList allowlistPkgFromSCPM = sCPMHelper.getAllowlistPkgFromSCPM();
        ArrayList blocklistPkgFromSCPM = sCPMHelper.getBlocklistPkgFromSCPM();
        SUSPICIOUS_TIME_THRESHOLD = suspiciousTimeLimitFromSCPM >= 0 ? suspiciousTimeLimitFromSCPM : 10000L;
        if (inexactWindowFromSCPM >= 0) {
            j = suspiciousTimeLimitFromSCPM;
        }
        EXP_MIN_INEXACT_WINDOW = j;
        synchronized (this.mLockSuspiciousTagSetFromSCPM) {
            this.mSuspiciousTagSetFromSCPM.clear();
            addCollection(suspiciousTagFromSCPM, this.mSuspiciousTagSetFromSCPM);
        }
        synchronized (this.mLockAllowlistFromSCPM) {
            this.mAllowlistPackagesFromSCPM.clear();
            addCollection(allowlistPkgFromSCPM, this.mAllowlistPackagesFromSCPM);
        }
        synchronized (this.mLockBlocklistFromSCPM) {
            this.mBlocklistPackagesFromSCPM.clear();
            addCollection(blocklistPkgFromSCPM, this.mBlocklistPackagesFromSCPM);
        }
        updateSuspiciousTags();
    }

    public class SCPMBroadcastReceiver extends BroadcastReceiver {
        public SCPMBroadcastReceiver(Context context) {
            context.registerReceiver(this, new IntentFilter("sec.app.policy.UPDATE.AppsyncPolicy"));
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals("sec.app.policy.UPDATE.AppsyncPolicy")) {
                AppSyncInfo.this.updateSuspiciousPolicy(context);
            }
        }
    }

    public class SCPMHelper {
        public final Uri AUTHORITY_SCPM_URI;
        public final Uri CONTENT_SCPM_URI;
        public final String[] POLICY_ITEMS;
        public final String[] POLICY_SCPM_PROJECTION;
        public final Uri POLICY_SCPM_URI;
        public ContentResolver mContentResolver;
        public Context mCtx;

        public SCPMHelper(Context context) {
            String[] strArr = {"item", "data1", "data2", "data3", "data4", "data5"};
            this.POLICY_ITEMS = strArr;
            Uri parse = Uri.parse("content://com.samsung.android.sm.policy");
            this.AUTHORITY_SCPM_URI = parse;
            Uri withAppendedPath = Uri.withAppendedPath(parse, "policy_item");
            this.CONTENT_SCPM_URI = withAppendedPath;
            this.POLICY_SCPM_URI = Uri.withAppendedPath(withAppendedPath, "AppsyncPolicy");
            this.POLICY_SCPM_PROJECTION = new String[]{strArr[0], strArr[1], strArr[2], strArr[3], strArr[4], strArr[5], "category"};
            this.mCtx = context;
            this.mContentResolver = context.getContentResolver();
        }

        public boolean isSCPMAvailable() {
            return this.mCtx.getPackageManager().resolveContentProvider("com.samsung.android.sm.policy", 0) != null;
        }

        public long getSuspiciousTimeLimitFromSCPM() {
            Cursor query = this.mContentResolver.query(this.POLICY_SCPM_URI, this.POLICY_SCPM_PROJECTION, "category = ?", new String[]{"CAT_SUSP_MILLIS"}, null);
            long j = -1;
            if (query == null) {
                return -1L;
            }
            if (!query.moveToNext()) {
                query.close();
                return -1L;
            }
            int columnIndex = query.getColumnIndex("item");
            if (columnIndex < 0) {
                query.close();
                return -1L;
            }
            try {
                j = query.getLong(columnIndex);
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
            return j;
        }

        public long getInexactWindowFromSCPM() {
            Cursor query = this.mContentResolver.query(this.POLICY_SCPM_URI, this.POLICY_SCPM_PROJECTION, "category = ?", new String[]{"CAT_WIN_MILLIS"}, null);
            long j = -1;
            if (query == null) {
                return -1L;
            }
            if (!query.moveToNext()) {
                query.close();
                return -1L;
            }
            int columnIndex = query.getColumnIndex("item");
            if (columnIndex < 0) {
                query.close();
                return -1L;
            }
            try {
                j = query.getLong(columnIndex);
            } catch (Exception unused) {
            } catch (Throwable th) {
                query.close();
                throw th;
            }
            query.close();
            return j;
        }

        public ArrayList getSuspiciousTagFromSCPM() {
            String str;
            Cursor query = this.mContentResolver.query(this.POLICY_SCPM_URI, this.POLICY_SCPM_PROJECTION, "category = ?", new String[]{"CAT_SUSP_PKGS"}, null);
            if (query == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            while (query.moveToNext()) {
                for (String str2 : this.POLICY_ITEMS) {
                    int columnIndex = query.getColumnIndex(str2);
                    if (columnIndex >= 0) {
                        try {
                            str = query.getString(columnIndex);
                        } catch (Exception unused) {
                            str = null;
                        }
                        if (str != null && !str.equals("")) {
                            arrayList.add(AppSyncInfo.this.getPackageTag(str, -1));
                        }
                    }
                }
            }
            query.close();
            return arrayList;
        }

        public ArrayList getAllowlistPkgFromSCPM() {
            String str;
            Cursor query = this.mContentResolver.query(this.POLICY_SCPM_URI, this.POLICY_SCPM_PROJECTION, "category = ?", new String[]{"CAT_ALLOW_PKGS"}, null);
            if (query == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            while (query.moveToNext()) {
                for (String str2 : this.POLICY_ITEMS) {
                    int columnIndex = query.getColumnIndex(str2);
                    if (columnIndex >= 0) {
                        try {
                            str = query.getString(columnIndex);
                        } catch (Exception unused) {
                            str = null;
                        }
                        if (str != null && !str.equals("")) {
                            arrayList.add(str.toLowerCase());
                        }
                    }
                }
            }
            query.close();
            return arrayList;
        }

        public ArrayList getBlocklistPkgFromSCPM() {
            String str;
            Cursor query = this.mContentResolver.query(this.POLICY_SCPM_URI, this.POLICY_SCPM_PROJECTION, "category = ?", new String[]{"CAT_BLOCK_PKGS"}, null);
            if (query == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            while (query.moveToNext()) {
                for (String str2 : this.POLICY_ITEMS) {
                    int columnIndex = query.getColumnIndex(str2);
                    if (columnIndex >= 0) {
                        try {
                            str = query.getString(columnIndex);
                        } catch (Exception unused) {
                            str = null;
                        }
                        if (str != null && !str.equals("")) {
                            arrayList.add(str.toLowerCase());
                        }
                    }
                }
            }
            query.close();
            return arrayList;
        }
    }
}
