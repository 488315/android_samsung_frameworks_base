package com.android.server.pm;

import android.os.incremental.PerUidReadTimeouts;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DumpHelper {
    public final ArrayMap mAvailableFeatures;
    public final ChangedPackagesTracker mChangedPackagesTracker;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final PackageInstallerService mInstallerService;
    public final KnownPackages mKnownPackages;
    public final PerUidReadTimeouts[] mPerUidReadTimeouts;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManager;
    public final ArraySet mProtectedBroadcasts;
    public final String[] mRequiredVerifierPackages;
    public final SnapshotStatistics mSnapshotStatistics;
    public final StorageEventHelper mStorageEventHelper;

    public DumpHelper(PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl, StorageEventHelper storageEventHelper, DomainVerificationManagerInternal domainVerificationManagerInternal, PackageInstallerService packageInstallerService, String[] strArr, KnownPackages knownPackages, ChangedPackagesTracker changedPackagesTracker, ArrayMap arrayMap, ArraySet arraySet, PerUidReadTimeouts[] perUidReadTimeoutsArr, SnapshotStatistics snapshotStatistics) {
        this.mPermissionManager = permissionManagerServiceInternalImpl;
        this.mStorageEventHelper = storageEventHelper;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mInstallerService = packageInstallerService;
        this.mRequiredVerifierPackages = strArr;
        this.mKnownPackages = knownPackages;
        this.mChangedPackagesTracker = changedPackagesTracker;
        this.mAvailableFeatures = arrayMap;
        this.mProtectedBroadcasts = arraySet;
        this.mPerUidReadTimeouts = perUidReadTimeoutsArr;
        this.mSnapshotStatistics = snapshotStatistics;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01db, code lost:
    
        r4 = r22[r3];
        r14 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x01e5, code lost:
    
        if ("android".equals(r4) != false) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01ed, code lost:
    
        if (r4.contains(".") == false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01fb, code lost:
    
        if ("check-permission".equals(r4) == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01fe, code lost:
    
        if (r14 < r22.length) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0200, code lost:
    
        r21.println("Error: check-permission missing permission argument");
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0205, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0206, code lost:
    
        r4 = r22[r14];
        r2 = 2 + r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x020a, code lost:
    
        if (r2 < r22.length) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x020c, code lost:
    
        r21.println("Error: check-permission missing package argument");
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0211, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0212, code lost:
    
        r2 = r22[r2];
        r3 = r3 + 3;
        r5 = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x021f, code lost:
    
        if (r3 >= r22.length) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0235, code lost:
    
        r21.println(com.android.server.pm.permission.PermissionManagerService.this.checkPermission(r19.resolveInternalPackageName(-1, r2), r4, "default:0", r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0248, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0221, code lost:
    
        r5 = java.lang.Integer.parseInt(r22[r3]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0228, code lost:
    
        com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(new java.lang.StringBuilder("Error: check-permission user argument is not a number: "), r22[r3], r21);
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0234, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x024f, code lost:
    
        if ("l".equals(r4) != false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0257, code lost:
    
        if ("libraries".equals(r4) == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0263, code lost:
    
        if (com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter.FEATURE.equals(r4) != false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x026b, code lost:
    
        if (com.samsung.android.scs.ai.sdkcommon.feature.FeatureConfig.JSON_KEY_FEATURES.equals(r4) == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0278, code lost:
    
        if ("r".equals(r4) != false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0281, code lost:
    
        if ("resolvers".equals(r4) == false) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0290, code lost:
    
        if ("perm".equals(r4) != false) goto L257;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0299, code lost:
    
        if ("permissions".equals(r4) == false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x029b, code lost:
    
        r1 = 64;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0571, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x05fc, code lost:
    
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x05fd, code lost:
    
        r3 = r12.mTargetPackageName;
        r1 = r12.mCheckIn;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0601, code lost:
    
        if (r3 == null) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0607, code lost:
    
        if (r19.getPackageStateInternal(r3) != null) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x060d, code lost:
    
        if (r19.isApexPackage(r3) != false) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x060f, code lost:
    
        r21.println("Unable to find package: ".concat(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0618, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0619, code lost:
    
        if (r1 == false) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x061b, code lost:
    
        r21.println("vers,1");
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0621, code lost:
    
        if (r1 != false) goto L310;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0627, code lost:
    
        if (r12.isDumping(32768) == false) goto L310;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0629, code lost:
    
        if (r3 != null) goto L310;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x062b, code lost:
    
        r19.dump(32768, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0630, code lost:
    
        if (r1 != false) goto L351;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0638, code lost:
    
        if (r12.isDumping(134217728) == false) goto L351;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x063a, code lost:
    
        if (r3 != null) goto L351;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x063c, code lost:
    
        r5 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0640, code lost:
    
        if (r5 == false) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0642, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0645, code lost:
    
        r5 = new com.android.internal.util.IndentingPrintWriter(r21, "  ", 120);
        r5.println("Known Packages:");
        r5.increaseIndent();
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0657, code lost:
    
        if (r14 > 19) goto L640;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0659, code lost:
    
        switch(r14) {
            case 0: goto L342;
            case 1: goto L341;
            case 2: goto L340;
            case 3: goto L339;
            case 4: goto L338;
            case 5: goto L337;
            case 6: goto L336;
            case 7: goto L335;
            case 8: goto L334;
            case 9: goto L333;
            case 10: goto L332;
            case 11: goto L331;
            case 12: goto L330;
            case 13: goto L329;
            case 14: goto L328;
            case 15: goto L327;
            case 16: goto L326;
            case 17: goto L325;
            case 18: goto L324;
            case 19: goto L323;
            default: goto L322;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x065c, code lost:
    
        r6 = "Unknown";
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x069a, code lost:
    
        r5.print(r6);
        r5.println(":");
        r6 = r18.mKnownPackages.getKnownPackageNames(r19, r14, 0);
        r5.increaseIndent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x06b0, code lost:
    
        if (com.android.internal.util.ArrayUtils.isEmpty(r6) == false) goto L346;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x06b2, code lost:
    
        r5.println("none");
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x06c6, code lost:
    
        r5.decreaseIndent();
        r14 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x06b9, code lost:
    
        r10 = r6.length;
        r15 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x06bb, code lost:
    
        if (r15 >= r10) goto L642;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x06bd, code lost:
    
        r5.println(r6[r15]);
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x065f, code lost:
    
        r6 = "Wearable sensing";
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0662, code lost:
    
        r6 = "Ambient Context Detection";
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0665, code lost:
    
        r6 = "Recents";
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0668, code lost:
    
        r6 = "Retail Demo";
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x066b, code lost:
    
        r6 = "Companion";
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x066e, code lost:
    
        r6 = "Wi-Fi";
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0671, code lost:
    
        r6 = "Overlay Config Signature";
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0674, code lost:
    
        r6 = "App Predictor";
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0677, code lost:
    
        r6 = "Incident Report Approver";
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x067a, code lost:
    
        r6 = "Configurator";
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x067d, code lost:
    
        r6 = "Documenter";
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0680, code lost:
    
        r6 = "Wellbeing";
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0683, code lost:
    
        r6 = "Permission Controller";
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0686, code lost:
    
        r6 = "System Text Classifier";
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0689, code lost:
    
        r6 = "Browser";
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x068c, code lost:
    
        r6 = "Verifier";
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x068f, code lost:
    
        r6 = "Uninstaller";
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x0692, code lost:
    
        r6 = "Installer";
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x0695, code lost:
    
        r6 = "Setup Wizard";
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0698, code lost:
    
        r6 = "System";
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x06d1, code lost:
    
        r5.decreaseIndent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x06da, code lost:
    
        if (r12.isDumping(2048) == false) goto L368;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x06dc, code lost:
    
        if (r3 != null) goto L368;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x06de, code lost:
    
        if (r1 != false) goto L361;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x06e3, code lost:
    
        if (r18.mRequiredVerifierPackages.length <= 0) goto L361;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x06e5, code lost:
    
        r5 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x06e9, code lost:
    
        if (r5 == false) goto L360;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x06eb, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x06ee, code lost:
    
        r21.println("Verifiers:");
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x06f3, code lost:
    
        r5 = r18.mRequiredVerifierPackages;
        r6 = r5.length;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x06f7, code lost:
    
        if (r10 >= r6) goto L643;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x06f9, code lost:
    
        r13 = r5[r10];
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x06fb, code lost:
    
        if (r1 != false) goto L366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x06fd, code lost:
    
        r21.print("  Required: ");
        r21.print(r13);
        r21.print(" (uid=");
        r21.print(r19.getPackageUid(r13, 268435456, 0));
        r21.println(")");
        r2 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0736, code lost:
    
        r10 = r10 + r2;
        r11 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x071c, code lost:
    
        r21.print("vrfy,");
        r21.print(r13);
        r21.print(",");
        r21.println(r19.getPackageUid(r13, 268435456, 0));
        r2 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0740, code lost:
    
        if (r12.isDumping(131072) == false) goto L382;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x0742, code lost:
    
        if (r3 != null) goto L382;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x0744, code lost:
    
        r2 = ((com.android.server.pm.verify.domain.DomainVerificationService) r18.mDomainVerificationManager).mProxy.getComponentName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x074e, code lost:
    
        if (r2 == null) goto L383;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x0750, code lost:
    
        r2 = r2.getPackageName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0754, code lost:
    
        if (r1 != false) goto L379;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0756, code lost:
    
        r5 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x075b, code lost:
    
        if (r5 == false) goto L378;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x075d, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x0760, code lost:
    
        r21.println("Domain Verifier:");
        r21.print("  Using: ");
        r21.print(r2);
        r21.print(" (uid=");
        r21.print(r19.getPackageUid(r2, 268435456, 0));
        r21.println(")");
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x0787, code lost:
    
        if (r2 == null) goto L382;
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x0789, code lost:
    
        r21.print("dv,");
        r21.print(r2);
        r21.print(",");
        r21.println(r19.getPackageUid(r2, 268435456, 0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x079f, code lost:
    
        r21.println();
        r21.println("No Domain Verifier available!");
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x07ac, code lost:
    
        if (r12.isDumping(1) == false) goto L388;
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x07ae, code lost:
    
        if (r3 != null) goto L388;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x07b0, code lost:
    
        r19.dump(1, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x07b8, code lost:
    
        if (r12.isDumping(2) == false) goto L406;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x07ba, code lost:
    
        if (r3 != null) goto L406;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x07bc, code lost:
    
        r5 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x07c0, code lost:
    
        if (r5 == false) goto L394;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x07c2, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x07c5, code lost:
    
        if (r1 != false) goto L396;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x07c7, code lost:
    
        r21.println("Features:");
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x07cc, code lost:
    
        r2 = r18.mAvailableFeatures.values().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x07da, code lost:
    
        if (r2.hasNext() == false) goto L647;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x07dc, code lost:
    
        r5 = (android.content.pm.FeatureInfo) r2.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x07e2, code lost:
    
        if (r1 != false) goto L646;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x0800, code lost:
    
        r21.print("feat,");
        r21.print(r5.name);
        r21.print(",");
        r21.println(r5.version);
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x07e4, code lost:
    
        r21.print("  ");
        r21.print(r5.name);
     */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x07f0, code lost:
    
        if (r5.version <= 0) goto L404;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x07f2, code lost:
    
        r21.print(" version=");
        r21.print(r5.version);
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x07fc, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x0815, code lost:
    
        r10 = r19.getComponentResolver();
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x0819, code lost:
    
        if (r1 != false) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x0820, code lost:
    
        if (r12.isDumping(4) == false) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x0822, code lost:
    
        r10.dumpActivityResolvers(r12, r21, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:271:0x0825, code lost:
    
        if (r1 != false) goto L415;
     */
    /* JADX WARN: Code restructure failed: missing block: B:273:0x082d, code lost:
    
        if (r12.isDumping(16) == false) goto L415;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x082f, code lost:
    
        r10.dumpReceiverResolvers(r12, r21, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x0832, code lost:
    
        if (r1 != false) goto L419;
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x083a, code lost:
    
        if (r12.isDumping(8) == false) goto L419;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x083c, code lost:
    
        r10.dumpServiceResolvers(r12, r21, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x083f, code lost:
    
        if (r1 != false) goto L423;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x0847, code lost:
    
        if (r12.isDumping(32) == false) goto L423;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x0849, code lost:
    
        r10.dumpProviderResolvers(r12, r21, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x084c, code lost:
    
        if (r1 != false) goto L427;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x0854, code lost:
    
        if (r12.isDumping(4096) == false) goto L427;
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x0856, code lost:
    
        r19.dump(4096, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x0859, code lost:
    
        if (r1 != false) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x0861, code lost:
    
        if (r12.isDumping(8192) == false) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x0863, code lost:
    
        if (r3 != null) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x0865, code lost:
    
        r19.dump(8192, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x0868, code lost:
    
        if (r1 != false) goto L436;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x0870, code lost:
    
        if (r12.isDumping(262144) == false) goto L436;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x0872, code lost:
    
        r19.dump(262144, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x0875, code lost:
    
        if (r1 != false) goto L440;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x087d, code lost:
    
        if (r12.isDumping(64) == false) goto L440;
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x087f, code lost:
    
        r19.dumpPermissions(r21, r3, r4, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x0882, code lost:
    
        if (r1 != false) goto L444;
     */
    /* JADX WARN: Code restructure failed: missing block: B:302:0x088a, code lost:
    
        if (r12.isDumping(1024) == false) goto L444;
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x088c, code lost:
    
        r10.dumpContentProviders(r19, r21, r12, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:304:0x088f, code lost:
    
        if (r1 != false) goto L448;
     */
    /* JADX WARN: Code restructure failed: missing block: B:306:0x0897, code lost:
    
        if (r12.isDumping(com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) == false) goto L448;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x0899, code lost:
    
        r19.dumpKeySet(r12, r21, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x08a3, code lost:
    
        if (r12.isDumping(128) == false) goto L451;
     */
    /* JADX WARN: Code restructure failed: missing block: B:310:0x08a5, code lost:
    
        r13 = r3;
        r14 = r4;
        r15 = 256;
        r19.dumpPackages(r21, r3, r4, r12, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x08bb, code lost:
    
        if (r1 != false) goto L456;
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x08c3, code lost:
    
        if (r12.isDumping(67108864) == false) goto L456;
     */
    /* JADX WARN: Code restructure failed: missing block: B:314:0x08c5, code lost:
    
        r19.dump(67108864, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x08cc, code lost:
    
        if (r12.isDumping(r15) == false) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x08ce, code lost:
    
        r19.dumpSharedUsers(r21, r13, r14, r12, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x08d9, code lost:
    
        if (r1 != false) goto L474;
     */
    /* JADX WARN: Code restructure failed: missing block: B:320:0x08e1, code lost:
    
        if (r12.isDumping(4194304) == false) goto L474;
     */
    /* JADX WARN: Code restructure failed: missing block: B:321:0x08e3, code lost:
    
        if (r13 != null) goto L474;
     */
    /* JADX WARN: Code restructure failed: missing block: B:322:0x08e5, code lost:
    
        r1 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x08ea, code lost:
    
        if (r1 == false) goto L466;
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x08ec, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x08ef, code lost:
    
        r21.println("Package Changes:");
        r1 = r18.mChangedPackagesTracker;
        r2 = new com.android.server.pm.DumpHelper$$ExternalSyntheticLambda0(r21);
        r3 = r1.mLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x08fd, code lost:
    
        monitor-enter(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x08fe, code lost:
    
        r2.accept(java.lang.Integer.valueOf(r1.mChangedPackagesSequenceNumber), r1.mUserIdToSequenceToPackage);
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x0909, code lost:
    
        monitor-exit(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x090e, code lost:
    
        if (r1 != false) goto L479;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x0916, code lost:
    
        if (r12.isDumping(524288) == false) goto L479;
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x0918, code lost:
    
        if (r13 != null) goto L479;
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x091a, code lost:
    
        r19.dump(524288, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:340:0x091d, code lost:
    
        if (r1 != false) goto L501;
     */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x0925, code lost:
    
        if (r12.isDumping(8388608) == false) goto L501;
     */
    /* JADX WARN: Code restructure failed: missing block: B:343:0x0927, code lost:
    
        if (r13 != null) goto L501;
     */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x0929, code lost:
    
        r1 = r18.mStorageEventHelper;
        r1.getClass();
        r2 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:345:0x0933, code lost:
    
        if (r2 == false) goto L486;
     */
    /* JADX WARN: Code restructure failed: missing block: B:346:0x0935, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x0938, code lost:
    
        r4 = 120;
        r2 = new com.android.internal.util.IndentingPrintWriter(r21, "  ", 120);
        r2.println();
        r2.println("Loaded volumes:");
        r2.increaseIndent();
        r3 = r1.mLoadedVolumes;
     */
    /* JADX WARN: Code restructure failed: missing block: B:348:0x094e, code lost:
    
        monitor-enter(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:351:0x0955, code lost:
    
        if (r1.mLoadedVolumes.size() != 0) goto L493;
     */
    /* JADX WARN: Code restructure failed: missing block: B:352:0x0957, code lost:
    
        r2.println("(none)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:353:0x0976, code lost:
    
        monitor-exit(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x0977, code lost:
    
        r2.decreaseIndent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x097f, code lost:
    
        if (r1 != false) goto L507;
     */
    /* JADX WARN: Code restructure failed: missing block: B:357:0x0987, code lost:
    
        if (r12.isDumping(16777216) == false) goto L507;
     */
    /* JADX WARN: Code restructure failed: missing block: B:358:0x0989, code lost:
    
        if (r13 != null) goto L507;
     */
    /* JADX WARN: Code restructure failed: missing block: B:359:0x098b, code lost:
    
        r10.dumpServicePermissions(r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:360:0x098e, code lost:
    
        if (r1 != false) goto L511;
     */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x0996, code lost:
    
        if (r12.isDumping(1048576) == false) goto L511;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x0998, code lost:
    
        r19.dump(1048576, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:364:0x099b, code lost:
    
        if (r1 != false) goto L515;
     */
    /* JADX WARN: Code restructure failed: missing block: B:366:0x09a3, code lost:
    
        if (r12.isDumping(Integer.MIN_VALUE) == false) goto L515;
     */
    /* JADX WARN: Code restructure failed: missing block: B:367:0x09a5, code lost:
    
        r19.dump(Integer.MIN_VALUE, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x09a8, code lost:
    
        if (r1 != false) goto L519;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x09b0, code lost:
    
        if (r12.isDumping(2097152) == false) goto L519;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x09b2, code lost:
    
        r19.dump(2097152, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x09bb, code lost:
    
        if (r12.isDumping(512) == false) goto L528;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x09bd, code lost:
    
        if (r13 != null) goto L528;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x09bf, code lost:
    
        if (r1 != false) goto L527;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x09c1, code lost:
    
        r2 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x09c6, code lost:
    
        if (r2 == false) goto L526;
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:0x09c8, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:379:0x09cb, code lost:
    
        r19.dump(512, r20, r21, r12);
        r21.println();
        r21.println("Package warning messages:");
        com.android.server.pm.PackageManagerServiceUtils.dumpCriticalInfo(r21, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:380:0x09db, code lost:
    
        com.android.server.pm.PackageManagerServiceUtils.dumpCriticalInfo(r21, "msg,");
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x09e1, code lost:
    
        if (r1 != false) goto L536;
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x09e9, code lost:
    
        if (r12.isDumping(com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) == false) goto L536;
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x09eb, code lost:
    
        if (r13 != null) goto L536;
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x09ed, code lost:
    
        r1 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:386:0x09f2, code lost:
    
        if (r1 == false) goto L535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x09f4, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x09f7, code lost:
    
        r18.mInstallerService.dump(new com.android.internal.util.IndentingPrintWriter(r21, "  ", r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0a03, code lost:
    
        if (r1 != false) goto L540;
     */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x0a0b, code lost:
    
        if (r12.isDumping(33554432) == false) goto L540;
     */
    /* JADX WARN: Code restructure failed: missing block: B:392:0x0a0d, code lost:
    
        r19.dump(33554432, r20, r21, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x0a10, code lost:
    
        if (r1 != false) goto L560;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x0a18, code lost:
    
        if (r12.isDumping(268435456) == false) goto L560;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x0a1a, code lost:
    
        if (r13 != null) goto L560;
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x0a1c, code lost:
    
        r1 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:398:0x0a21, code lost:
    
        if (r1 == false) goto L547;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x0a23, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x0a26, code lost:
    
        r1 = com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(r21, "Per UID read timeouts:", "    Default timeouts flag: ");
        r2 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION;
        r2 = android.os.Binder.clearCallingIdentity();
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x0a34, code lost:
    
        r4 = android.provider.DeviceConfig.getString("package_manager_service", "incfs_default_timeouts", "");
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x0a3f, code lost:
    
        android.os.Binder.restoreCallingIdentity(r2);
        r1.append(r4);
        r21.println(r1.toString());
        r1 = new java.lang.StringBuilder("    Known digesters list flag: ");
        r2 = android.os.Binder.clearCallingIdentity();
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x0a57, code lost:
    
        r4 = android.provider.DeviceConfig.getString("package_manager_service", "known_digesters_list", "");
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x0a62, code lost:
    
        android.os.Binder.restoreCallingIdentity(r2);
        r1.append(r4);
        r21.println(r1.toString());
        r21.println("    Timeouts (" + r18.mPerUidReadTimeouts.length + "):");
        r1 = r18.mPerUidReadTimeouts;
        r2 = r1.length;
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x0a8c, code lost:
    
        if (r3 >= r2) goto L652;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x0a8e, code lost:
    
        r4 = r1[r3];
        r5 = com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r21, "        (", "uid=");
        r5.append(r4.uid);
        r5.append(", ");
        r21.print(r5.toString());
        r21.print("minTimeUs=" + r4.minTimeUs + ", ");
        r21.print("minPendingTimeUs=" + r4.minPendingTimeUs + ", ");
        r5 = new java.lang.StringBuilder("maxPendingTimeUs=");
        r5.append(r4.maxPendingTimeUs);
        r21.print(r5.toString());
        r21.println(")");
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0af8, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x0afc, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x0afd, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:0x0b01, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:418:0x0b02, code lost:
    
        if (r1 != false) goto L578;
     */
    /* JADX WARN: Code restructure failed: missing block: B:420:0x0b0a, code lost:
    
        if (r12.isDumping(536870912) == false) goto L578;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0b0c, code lost:
    
        if (r13 != null) goto L578;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0b0e, code lost:
    
        r1 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x0b13, code lost:
    
        if (r1 == false) goto L567;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x0b15, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x0b18, code lost:
    
        r21.println("Snapshot statistics:");
        r1 = r18.mSnapshotStatistics;
        r14 = android.os.SystemClock.currentTimeMicro();
        r2 = r19.getUsed();
        r7 = r12.mBrief;
        r4 = r1.mLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x0b2d, code lost:
    
        monitor-enter(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x0b2e, code lost:
    
        r5 = r1.mLong;
        r8 = (com.android.server.pm.SnapshotStatistics.Stats[]) java.util.Arrays.copyOf(r5, r5.length);
        r8[0] = new com.android.server.pm.SnapshotStatistics.Stats(r1, r8[0]);
        r5 = r1.mShort;
        r10 = (com.android.server.pm.SnapshotStatistics.Stats[]) java.util.Arrays.copyOf(r5, r5.length);
        r16 = 0;
        r10[0] = new com.android.server.pm.SnapshotStatistics.Stats(r1, r10[0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:429:0x0b57, code lost:
    
        monitor-exit(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x0b58, code lost:
    
        r21.format(java.util.Locale.US, "%s Unrecorded-hits: %d", "   ", java.lang.Integer.valueOf(r2));
        r21.println();
        com.android.server.pm.SnapshotStatistics.dump(r21, r14, r8, r10, "stats");
     */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x0b75, code lost:
    
        if (r7 == false) goto L574;
     */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x0b78, code lost:
    
        r21.println();
        com.android.server.pm.SnapshotStatistics.dump(r21, r14, r8, r10, "times");
        r21.println();
        com.android.server.pm.SnapshotStatistics.dump(r21, r14, r8, r10, "usage");
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x0b95, code lost:
    
        if (r1 != false) goto L590;
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x0b9d, code lost:
    
        if (r12.isDumping(1073741824) == false) goto L663;
     */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x0b9f, code lost:
    
        if (r13 != null) goto L664;
     */
    /* JADX WARN: Code restructure failed: missing block: B:437:0x0ba1, code lost:
    
        r1 = r12.mTitlePrinted;
        r12.mTitlePrinted = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:438:0x0ba6, code lost:
    
        if (r1 == false) goto L586;
     */
    /* JADX WARN: Code restructure failed: missing block: B:439:0x0ba8, code lost:
    
        r21.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:440:0x0bab, code lost:
    
        r21.println("Protected broadcast actions:");
        r10 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:442:0x0bb8, code lost:
    
        if (r10 >= r18.mProtectedBroadcasts.size()) goto L653;
     */
    /* JADX WARN: Code restructure failed: missing block: B:443:0x0bba, code lost:
    
        r21.print("  ");
        r21.println((java.lang.String) r18.mProtectedBroadcasts.valueAt(r10));
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:445:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:446:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:447:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:448:0x0bcd, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:453:0x0b93, code lost:
    
        r16 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:455:0x095f, code lost:
    
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:457:0x0966, code lost:
    
        if (r5 >= r1.mLoadedVolumes.size()) goto L654;
     */
    /* JADX WARN: Code restructure failed: missing block: B:458:0x0968, code lost:
    
        r2.println((java.lang.String) r1.mLoadedVolumes.valueAt(r5));
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:460:0x095d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x097c, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:464:0x097d, code lost:
    
        r4 = 120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:465:0x08b7, code lost:
    
        r13 = r3;
        r14 = r4;
        r15 = 256;
     */
    /* JADX WARN: Code restructure failed: missing block: B:467:0x02aa, code lost:
    
        if ("permission".equals(r4) == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x02ad, code lost:
    
        if (r14 < r22.length) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:470:0x02af, code lost:
    
        r21.println("Error: permission requires permission name");
     */
    /* JADX WARN: Code restructure failed: missing block: B:471:0x02b4, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:472:0x02b5, code lost:
    
        r3 = new android.util.ArraySet();
     */
    /* JADX WARN: Code restructure failed: missing block: B:474:0x02bb, code lost:
    
        if (r14 >= r22.length) goto L655;
     */
    /* JADX WARN: Code restructure failed: missing block: B:475:0x02bd, code lost:
    
        r3.add(r22[r14]);
        r14 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:477:0x02c4, code lost:
    
        r12.setDump(448);
        r4 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:479:0x02d7, code lost:
    
        if ("pref".equals(r4) != false) goto L255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:481:0x02e0, code lost:
    
        if ("preferred".equals(r4) == false) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:482:0x02e2, code lost:
    
        r1 = 4096;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:483:0x0567, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:485:0x02f1, code lost:
    
        if ("preferred-xml".equals(r4) == false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:486:0x02f3, code lost:
    
        r12.setDump(8192);
     */
    /* JADX WARN: Code restructure failed: missing block: B:487:0x02f7, code lost:
    
        if (r14 >= r22.length) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:489:0x0301, code lost:
    
        if ("--full".equals(r22[r14]) == false) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:490:0x0303, code lost:
    
        r12.mFullPreferred = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:492:0x0311, code lost:
    
        if ("d".equals(r4) != false) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:494:0x0319, code lost:
    
        if ("domain-preferred-apps".equals(r4) == false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:495:0x031b, code lost:
    
        r1 = 262144;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:496:0x055d, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:498:0x032a, code lost:
    
        if (com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter.PAYLOAD.equals(r4) != false) goto L251;
     */
    /* JADX WARN: Code restructure failed: missing block: B:500:0x0333, code lost:
    
        if ("packages".equals(r4) == false) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:501:0x0335, code lost:
    
        r1 = 128;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:502:0x0553, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:504:0x0344, code lost:
    
        if ("q".equals(r4) != false) goto L249;
     */
    /* JADX WARN: Code restructure failed: missing block: B:506:0x034d, code lost:
    
        if ("queries".equals(r4) == false) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:507:0x034f, code lost:
    
        r1 = 67108864;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:508:0x0549, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:510:0x035e, code lost:
    
        if ("s".equals(r4) != false) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:512:0x0367, code lost:
    
        if ("shared-users".equals(r4) == false) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:514:0x0378, code lost:
    
        if ("prov".equals(r4) != false) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:516:0x0381, code lost:
    
        if ("providers".equals(r4) == false) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:517:0x0383, code lost:
    
        r1 = 1024;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:518:0x0525, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:521:0x0393, code lost:
    
        if ("m".equals(r4) != false) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:523:0x039c, code lost:
    
        if ("messages".equals(r4) == false) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:524:0x039e, code lost:
    
        r1 = 512;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:525:0x0519, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:527:0x03af, code lost:
    
        if ("v".equals(r4) != false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:529:0x03b8, code lost:
    
        if ("verifiers".equals(r4) == false) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:530:0x03ba, code lost:
    
        r1 = 2048;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:531:0x050d, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:533:0x03ca, code lost:
    
        if ("dv".equals(r4) != false) goto L235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:535:0x03d2, code lost:
    
        if ("domain-verifier".equals(r4) == false) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:536:0x03d4, code lost:
    
        r1 = 131072;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:537:0x0501, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:539:0x03e5, code lost:
    
        if ("version".equals(r4) == false) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:540:0x03e7, code lost:
    
        r12.setDump(32768);
     */
    /* JADX WARN: Code restructure failed: missing block: B:542:0x03f2, code lost:
    
        if ("k".equals(r4) != false) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:544:0x03fa, code lost:
    
        if ("keysets".equals(r4) == false) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:545:0x03fc, code lost:
    
        r1 = com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:546:0x04f5, code lost:
    
        r12.setDump(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:548:0x040c, code lost:
    
        if ("installs".equals(r4) == false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:549:0x040e, code lost:
    
        r12.setDump(com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
     */
    /* JADX WARN: Code restructure failed: missing block: B:551:0x0419, code lost:
    
        if ("frozen".equals(r4) == false) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:552:0x041b, code lost:
    
        r12.setDump(524288);
     */
    /* JADX WARN: Code restructure failed: missing block: B:554:0x0427, code lost:
    
        if ("volumes".equals(r4) == false) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:555:0x0429, code lost:
    
        r12.setDump(8388608);
     */
    /* JADX WARN: Code restructure failed: missing block: B:557:0x0438, code lost:
    
        if ("dexopt".equals(r4) == false) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:558:0x043a, code lost:
    
        r12.setDump(1048576);
     */
    /* JADX WARN: Code restructure failed: missing block: B:560:0x0447, code lost:
    
        if ("compiler-stats".equals(r4) == false) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:561:0x0449, code lost:
    
        r12.setDump(2097152);
     */
    /* JADX WARN: Code restructure failed: missing block: B:563:0x0456, code lost:
    
        if ("changes".equals(r4) == false) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:564:0x0458, code lost:
    
        r12.setDump(4194304);
     */
    /* JADX WARN: Code restructure failed: missing block: B:566:0x0466, code lost:
    
        if ("service-permissions".equals(r4) == false) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:567:0x0468, code lost:
    
        r12.setDump(16777216);
     */
    /* JADX WARN: Code restructure failed: missing block: B:569:0x0475, code lost:
    
        if ("known-packages".equals(r4) == false) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:570:0x0477, code lost:
    
        r12.setDump(134217728);
     */
    /* JADX WARN: Code restructure failed: missing block: B:572:0x0485, code lost:
    
        if (com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter.TIMESTAMP.equals(r4) != false) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:574:0x048e, code lost:
    
        if ("timeouts".equals(r4) == false) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:576:0x049c, code lost:
    
        if ("snapshot".equals(r4) == false) goto L225;
     */
    /* JADX WARN: Code restructure failed: missing block: B:577:0x049e, code lost:
    
        r12.setDump(536870912);
     */
    /* JADX WARN: Code restructure failed: missing block: B:578:0x04a4, code lost:
    
        if (r14 >= r22.length) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:580:0x04ae, code lost:
    
        if ("--full".equals(r22[r14]) == false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:581:0x04b0, code lost:
    
        r12.mBrief = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:583:0x04bd, code lost:
    
        if ("--brief".equals(r22[r14]) == false) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:584:0x04bf, code lost:
    
        r12.mBrief = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:586:0x04ca, code lost:
    
        if ("protected-broadcasts".equals(r4) == false) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:587:0x04cc, code lost:
    
        r12.setDump(1073741824);
     */
    /* JADX WARN: Code restructure failed: missing block: B:589:0x04dc, code lost:
    
        if ("pu".equals(r4) == false) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:590:0x04de, code lost:
    
        r12.setDump(Integer.MIN_VALUE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:591:0x0490, code lost:
    
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:592:0x04e5, code lost:
    
        r12.setDump(268435456);
     */
    /* JADX WARN: Code restructure failed: missing block: B:593:0x04ed, code lost:
    
        r4 = 0;
        r1 = com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
     */
    /* JADX WARN: Code restructure failed: missing block: B:594:0x04f9, code lost:
    
        r4 = 0;
        r1 = 131072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:595:0x0505, code lost:
    
        r4 = 0;
        r1 = 2048;
     */
    /* JADX WARN: Code restructure failed: missing block: B:596:0x0511, code lost:
    
        r4 = 0;
        r1 = 512;
     */
    /* JADX WARN: Code restructure failed: missing block: B:597:0x051d, code lost:
    
        r4 = 0;
        r1 = 1024;
     */
    /* JADX WARN: Code restructure failed: missing block: B:598:0x0369, code lost:
    
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:599:0x0529, code lost:
    
        r12.setDump(256);
     */
    /* JADX WARN: Code restructure failed: missing block: B:600:0x052d, code lost:
    
        if (r14 >= r22.length) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:602:0x0538, code lost:
    
        if ("noperm".equals(r22[r14]) == false) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:603:0x053a, code lost:
    
        r12.mOptions |= 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:604:0x0543, code lost:
    
        r4 = 0;
        r1 = 67108864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:605:0x054d, code lost:
    
        r4 = 0;
        r1 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:606:0x0557, code lost:
    
        r4 = 0;
        r1 = 262144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:607:0x0561, code lost:
    
        r4 = 0;
        r1 = 4096;
     */
    /* JADX WARN: Code restructure failed: missing block: B:608:0x056b, code lost:
    
        r4 = 0;
        r1 = 64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:609:0x0283, code lost:
    
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:611:0x0576, code lost:
    
        if (r14 < r22.length) goto L262;
     */
    /* JADX WARN: Code restructure failed: missing block: B:612:0x0578, code lost:
    
        r12.setDump(60);
     */
    /* JADX WARN: Code restructure failed: missing block: B:614:0x057f, code lost:
    
        if (r14 >= r22.length) goto L657;
     */
    /* JADX WARN: Code restructure failed: missing block: B:615:0x0581, code lost:
    
        r3 = r22[r14];
     */
    /* JADX WARN: Code restructure failed: missing block: B:616:0x0589, code lost:
    
        if ("a".equals(r3) != false) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:618:0x0591, code lost:
    
        if ("activity".equals(r3) == false) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:620:0x059c, code lost:
    
        if ("s".equals(r3) != false) goto L273;
     */
    /* JADX WARN: Code restructure failed: missing block: B:622:0x05a5, code lost:
    
        if ("service".equals(r3) == false) goto L274;
     */
    /* JADX WARN: Code restructure failed: missing block: B:624:0x05b1, code lost:
    
        if ("r".equals(r3) != false) goto L278;
     */
    /* JADX WARN: Code restructure failed: missing block: B:626:0x05ba, code lost:
    
        if ("receiver".equals(r3) == false) goto L279;
     */
    /* JADX WARN: Code restructure failed: missing block: B:628:0x05c5, code lost:
    
        if ("c".equals(r3) != false) goto L283;
     */
    /* JADX WARN: Code restructure failed: missing block: B:630:0x05cd, code lost:
    
        if ("content".equals(r3) == false) goto L656;
     */
    /* JADX WARN: Code restructure failed: missing block: B:632:0x05d2, code lost:
    
        com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(r21, "Error: unknown resolver table type: ", r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:633:0x05d7, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:635:0x05d8, code lost:
    
        r12.setDump(32);
     */
    /* JADX WARN: Code restructure failed: missing block: B:637:0x05e7, code lost:
    
        r14 = r14 + 1;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:639:0x05dc, code lost:
    
        r12.setDump(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:642:0x05e0, code lost:
    
        r12.setDump(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:645:0x05e4, code lost:
    
        r12.setDump(4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:649:0x05eb, code lost:
    
        r12.setDump(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:652:0x05f0, code lost:
    
        r12.setDump(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:654:0x05f5, code lost:
    
        r12.mTargetPackageName = r4;
        r12.mOptions |= 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01d9, code lost:
    
        if (r3 >= r22.length) goto L136;
     */
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doDump(com.android.server.pm.Computer r19, java.io.FileDescriptor r20, final java.io.PrintWriter r21, java.lang.String[] r22) {
        /*
            Method dump skipped, instructions count: 3066
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.DumpHelper.doDump(com.android.server.pm.Computer, java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }
}
