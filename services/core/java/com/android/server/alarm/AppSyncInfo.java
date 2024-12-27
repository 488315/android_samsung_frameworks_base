package com.android.server.alarm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.accounts.OnAccountsUpdateListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class AppSyncInfo extends AppSyncWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static long EXP_MIN_INEXACT_WINDOW = 300000;
    public static long SUSPICIOUS_TIME_THRESHOLD = 10000;
    public final IntentFilter boot_filter;
    public final AccountListener mAccountListener;
    public final ArrayList mAccountsPackages;
    public final PackageList mAllowlistPackages;
    public final ArrayList mAllowlistPackagesFromSCPM;
    public final PackageList mBlocklistAppSync3P;
    public final PackageList mBlocklistPackages;
    public final ArrayList mBlocklistPackagesFromConfig3P;
    public final ArrayList mBlocklistPackagesFromSCPM;
    public final AnonymousClass1 mBootIntentReceiver;
    public boolean mCharging;
    public final Context mContext;
    public final ArrayList mCscPackages;
    public IntentFilter mFilter = null;
    public final Object mLockAllowlistFromSCPM;
    public final Object mLockBlocklistFromSCPM;
    public final Object mLockExt;
    public final Object mLockSuspiciousTagSet;
    public final Object mLockSuspiciousTagSetFromApi;
    public final Object mLockSuspiciousTagSetFromSCPM;
    public final ArrayList mPermanentAllowlistPackages;
    public final PackageManager mPm;
    public boolean mScreenOn;
    public Set mSuspiciousTagSet;
    public final Set mSuspiciousTagSetFromApi;
    public final Set mSuspiciousTagSetFromSCPM;
    public final ArrayList mTargetPackages;
    public final Set preloadedPackages;

    /* renamed from: com.android.server.alarm.AppSyncInfo$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppSyncInfo this$0;

        public AnonymousClass1(AppSyncInfo appSyncInfo, int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = appSyncInfo;
                    appSyncInfo.getClass();
                    IntentFilter intentFilter = new IntentFilter();
                    appSyncInfo.mFilter = intentFilter;
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    appSyncInfo.mFilter.addAction("android.intent.action.SCREEN_ON");
                    appSyncInfo.mFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
                    appSyncInfo.mFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
                    break;
                default:
                    this.this$0 = appSyncInfo;
                    break;
            }
        }

        public AnonymousClass1(AppSyncInfo appSyncInfo, Context context) {
            this.$r8$classId = 2;
            this.this$0 = appSyncInfo;
            context.registerReceiver(this, new IntentFilter("sec.app.policy.UPDATE.AppsyncPolicy"));
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    AppSyncInfo.m156$$Nest$mupdateSuspiciousPolicy(this.this$0, context);
                    AccountManager accountManager = (AccountManager) context.getSystemService("account");
                    if (accountManager != null) {
                        try {
                            accountManager.addOnAccountsUpdatedListener(this.this$0.mAccountListener, null, true);
                            break;
                        } catch (IllegalStateException e) {
                            Slog.v("AppSyncInfo", "Ignore Exception : " + e.getMessage());
                            return;
                        }
                    }
                    break;
                case 1:
                    if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                        if (!"android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                            if (!"android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
                                if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(intent.getAction())) {
                                    this.this$0.mCharging = false;
                                    break;
                                }
                            } else {
                                this.this$0.mCharging = true;
                                break;
                            }
                        } else {
                            this.this$0.mScreenOn = true;
                            break;
                        }
                    } else {
                        this.this$0.mScreenOn = false;
                        break;
                    }
                    break;
                default:
                    String action = intent.getAction();
                    if (action != null && action.equals("sec.app.policy.UPDATE.AppsyncPolicy")) {
                        AppSyncInfo.m156$$Nest$mupdateSuspiciousPolicy(this.this$0, context);
                        break;
                    }
                    break;
            }
        }
    }

    public final class AccountListener implements OnAccountsUpdateListener {
        public AccountListener() {
        }

        @Override // android.accounts.OnAccountsUpdateListener
        public final void onAccountsUpdated(Account[] accountArr) {
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
            Iterator it = AppSyncInfo.this.mAccountsPackages.iterator();
            while (it.hasNext()) {
                Slog.v("AppSyncInfo", "<AccPackages> " + ((String) it.next()));
            }
        }
    }

    public final class PackageList {
        public final HashSet mPackageSet = new HashSet();
        public final ArrayList mRegExpList = new ArrayList();

        public final void add(String str) {
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

        public final boolean contains(String str) {
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

        public final void loadAppSync3PlusBlockList() {
            AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, ".*reminder.*", ".*alert.*", "com.sec.screencheck", "com.sec.dsm.system");
            AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.samsung.android.fmm", "com.samsung.ssd.wolfserver", "ch.bitspin.timely", "com.nhn.android.nmap");
            AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.qihoo.security", ".*vodafone.*", "com.blackberry.enterprise.bscp.*", "com.google.android.ims");
            add("com.google.android.apps.messaging");
            add(".*vzw.*");
            add(".*verizon.*");
            if (Sales.VZW) {
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, ".*amazon.*", "com.audible.application", "com.imdb.mobile", "com.amazon.fv");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.gotv.nflgamecenter.us.lite", "com.slacker.radio", "com.telecomsys.directedsms.android.SCG", "com.LogiaGroup.LogiaDeck");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.vznavigator.Generic", "com.cequint.ecid", "com.vcast.mediamanager", "com.sec.android.app.cmas");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.samsung.spg", "com.sec.android.app.setupwizard", "com.samsung.vvm", "com.samsung.carrier.logcollector");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.samsung.sdm", "com.samsung.syncmlservice", "com.samsung.syncmlphonedataservice", "com.samsung.sdm.sdmviewer");
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
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.airg", "com.amazon.mShop", "com.amazon.mShop.android", "com.amazon.avod.thirdpartyclient");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.amazon.mp3", "com.amazon.clouddrive.photos", "com.boostmobile.boosttv", "com.coremobility.app.vnotes");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.ebay.mobile", "com.familyandco.framilywall", "com.handmark.expressweather", "com.itsoninc.android.itsonclient");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.itsoninc.android.itsonservice", "com.itsoninc.android.uid", "com.kineto.smartwifi", "com.livewiremobile.musicstore.boost");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.locationlabs.sparkle.yellow.pre", "com.lookout", "com.nascar.nascarmobile", "com.nbadigital.gametimelite");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.nextradioapp.nextradio", "com.oem.smartwifisupport", "com.pinsight.v1", "com.smithmicro.EDM");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.smithmicro.mnd", "com.soleo.numbersearch", "com.spotify.music", "com.telenav.app.android.scout_us");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.ubercab", "com.wipit.android.boostwallet", "msgplus.jibe.sca", "com.privacystar.android.spg");
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
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.tmobile.pr.adapt", "com.lookout", "com.mobileposse.client", "com.sec.tetheringprovision");
            }
            add(".*tracfone.*");
            add(".*uscc.*");
            if (Sales.USCC) {
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.cequint.ecid", "com.synchronoss.sm", "com.amazon.mShop.android", "com.LogiaGroup.LogiaDeck");
                add("com.zed.TrdWapLauncher");
                add("com.amazon.windowshop");
            }
            if (Sales.JAPAN) {
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.nttdocomo.*", "jp.co.nttdocomo.*", "com.ipg.gguide.*", "com.rsupport.rs.activity.ntt");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.mcafee.vsm_android_dcm", "com.mcafee.safecall.docomo", "com.mcafee.android.scanservice", "com.showcasegig.devlawson");
                AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "jp.id_credit_sp.android", "jp.dmapnavi.navi02", "jp.co.mcdonalds.android", "jp.co.lawson.activity");
                add("jp.co.omronsoft.android.decoemojimanager_docomo");
                add("org.simalliance.openmobileapi.service");
            }
        }

        public final void loadAppSyncBlockList() {
            AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, ".*alarm.*", ".*clock.*", "com.android.email", "com.samsung.android.email.sync");
            AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.sec\\..*ims.*", "com.sec.epdg", "com.samsung\\..*ims.*", "com.samsung.android.themecenter");
            AppSyncInfo$PackageList$$ExternalSyntheticOutline0.m(this, "com.iloen.melon", "com.iloen.melon.tablet", ".*knox.*", "android");
            add(".*email.ui");
            add(".*shealth.*");
            if (Sales.JAPAN) {
                add("com.android.incallui");
                add("com.android.services.telephony.common");
            }
        }

        public final String toString() {
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
    }

    public interface Sales {
        public static final boolean ATT;
        public static final boolean JAPAN;
        public static final boolean MPCS;
        public static final boolean SPR;
        public static final boolean TMO;
        public static final boolean USCC;
        public static final boolean VZW;

        static {
            String upperCase = SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase();
            boolean equals = "VZW".equals(upperCase);
            VZW = equals;
            boolean z = true;
            boolean z2 = "ATT".equals(upperCase) || "AIO".equals(upperCase) || "CRI".equals(upperCase);
            ATT = z2;
            boolean equals2 = "TMB".equals(upperCase);
            TMO = equals2;
            MPCS = "TMK".equals(upperCase);
            boolean z3 = "SPR".equals(upperCase) || "BST".equals(upperCase) || "VMU".equals(upperCase) || "XAS".equals(upperCase);
            SPR = z3;
            if (!"USC".equals(upperCase) && !"LRA".equals(upperCase) && !"ACG".equals(upperCase)) {
                z = false;
            }
            USCC = z;
            if (!"RWC".equals(upperCase) && !"FMC".equals(upperCase) && !"MTA".equals(upperCase) && !"CHR".equals(upperCase) && !"MTS".equals(upperCase) && !"TLS".equals(upperCase) && !"KDO".equals(upperCase) && !"SPC".equals(upperCase) && !"CLN".equals(upperCase) && !"BMC".equals(upperCase) && !"VMC".equals(upperCase) && !"PCM".equals(upperCase) && !"SOL".equals(upperCase) && !"BWA".equals(upperCase) && !"GLW".equals(upperCase) && !"VTR".equals(upperCase) && !"ESK".equals(upperCase) && !"PMB".equals(upperCase)) {
                "XAC".equals(upperCase);
            }
            if (!"CHN".equals(upperCase) && !"CHU".equals(upperCase) && !"CTC".equals(upperCase) && !"CHM".equals(upperCase)) {
                "CHC".equals(upperCase);
            }
            JAPAN = "DCM".equals(upperCase);
            if (equals || z2 || equals2 || z3 || z || "XAR".equals(upperCase) || "MTR".equals(upperCase) || "SPT".equals(upperCase) || "CSP".equals(upperCase) || "TFN".equals(upperCase)) {
                return;
            }
            "BNN".equals(upperCase);
        }
    }

    /* renamed from: -$$Nest$mupdateSuspiciousPolicy, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m156$$Nest$mupdateSuspiciousPolicy(com.android.server.alarm.AppSyncInfo r23, android.content.Context r24) {
        /*
            Method dump skipped, instructions count: 559
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AppSyncInfo.m156$$Nest$mupdateSuspiciousPolicy(com.android.server.alarm.AppSyncInfo, android.content.Context):void");
    }

    public AppSyncInfo(Context context) {
        Object obj = new Object();
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
        this.mBootIntentReceiver = new AnonymousClass1(this, 0);
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mScreenOn = true;
        this.mCharging = false;
        context.registerReceiver(new AnonymousClass1(this, 1), this.mFilter);
        this.mAccountListener = new AccountListener();
        new AnonymousClass1(this, context);
        synchronized (obj) {
            try {
                arrayList.clear();
                packageList.mPackageSet.clear();
                packageList.mRegExpList.clear();
                packageList2.mPackageSet.clear();
                packageList2.mRegExpList.clear();
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
                packageList3.add("com.tencent.mobileqq");
                packageList3.add("com.tencent.mm");
                packageList3.add("com.sohu.inputmethod.sogou");
                packageList3.add("com.eg.android.AlipayGphone");
                packageList3.add("com.alibaba.android.rimet");
                packageList3.add("com.baidu.map.location");
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
                ((HashSet) this.preloadedPackages).add("com.facebook.services");
                ((HashSet) this.preloadedPackages).add("com.facebook.katana");
                ((HashSet) this.preloadedPackages).add("com.facebook.orca");
                ((HashSet) this.preloadedPackages).add("com.facebook.pages.app");
                ((HashSet) this.preloadedPackages).add("com.facebook.appmanager");
                ((HashSet) this.preloadedPackages).add("com.facebook.system");
                ((HashSet) this.preloadedPackages).add("com.whatsapp");
                ((HashSet) this.preloadedPackages).add("com.instagram.android");
                ((HashSet) this.preloadedPackages).add("com.skype.raider");
                ((HashSet) this.preloadedPackages).add("com.microsoft.skydrive");
                ((HashSet) this.preloadedPackages).add("com.samsung.android.opencalendar");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void addCollection(Collection collection, Collection collection2) {
        if (collection == null || collection2 == null) {
            return;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            collection2.add((String) it.next());
        }
    }

    public static String getPackageTag(int i, String str) {
        return VpnManagerService$$ExternalSyntheticOutline0.m(i, str, "/");
    }

    @Override // com.android.server.alarm.AppSyncWrapper
    public final void dump(PrintWriter printWriter) {
        printWriter.println("");
        printWriter.println("<AppSyncInfo>");
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mCharging: "), this.mCharging, printWriter, "mScreenOn: "), this.mScreenOn, printWriter, "SUSP_THRE: "), SUSPICIOUS_TIME_THRESHOLD, printWriter, "INEXACT_WIN: ");
        m.append(EXP_MIN_INEXACT_WINDOW);
        printWriter.println(m.toString());
        printWriter.println("  <AppSync3 Allowlist>");
        Iterator it = this.mCscPackages.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            printWriter.print("");
            printWriter.println("   (AppSync) " + str);
        }
        printWriter.print("");
        printWriter.println("   (AppSync) ---------");
        printWriter.println("  <AppSync3 TargetList>");
        Iterator it2 = this.mTargetPackages.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            printWriter.print("");
            printWriter.println("   (AppSync) " + str2);
        }
        printWriter.print("");
        printWriter.println("   (AppSync) ---------");
        printWriter.println("  <AppSync3 Blocklist>");
        printWriter.print("");
        printWriter.println("   (AppSync) " + this.mBlocklistPackages.toString());
        printWriter.print("");
        printWriter.println("   (AppSync) ---------");
        printWriter.println("  <AppSync3p Blocklist>");
        printWriter.print("");
        printWriter.println("   (AppSync) " + this.mBlocklistAppSync3P.toString());
        printWriter.print("");
        printWriter.println("   (AppSync) ---------");
        printWriter.println("");
    }

    @Override // com.android.server.alarm.AppSyncWrapper
    public final long getWindowLength() {
        return EXP_MIN_INEXACT_WINDOW;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004a, code lost:
    
        if (r13.mTargetPackages.contains(r22.toLowerCase()) == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0063, code lost:
    
        if (r13.mBlocklistAppSync3P.contains(r4) == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00c1, code lost:
    
        if (r13.mTargetPackages.contains(r22.toLowerCase()) == false) goto L82;
     */
    @Override // com.android.server.alarm.AppSyncWrapper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAdjustableAlarm(int r14, int r15, long r16, long r18, long r20, java.lang.String r22) {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AppSyncInfo.isAdjustableAlarm(int, int, long, long, long, java.lang.String):boolean");
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
}
