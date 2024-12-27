package com.android.server.asks;

import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UnknownStore {
    public String KEY_VALUE = null;
    public String PKGNAME = null;
    public String SIGHASH = null;
    public String PKGSIGHASH = null;
    public String BASE_CODE_PATH = null;
    public HashMap blockPermissions = null;
    public HashMap warningPermissions = null;
    public ArrayList blockPermGroup = null;
    public ArrayList warningPermGroup = null;
    public HashMap certPolicies = null;
    public ArrayList executeBlockPkgName = null;
    public ArrayList exceptPkgName = null;
    public ArrayList regexDomainRule = null;
    public ArrayList regexPackageRule = null;
    public ArrayList tagNameList = null;
    public HashMap unknownAppsList = null;
    public PKGINFO defaultCertPolicy = null;

    public static boolean isDevDevice() {
        return "0x1".equals(SystemProperties.get("ro.boot.em.status"));
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addCertPolicy(java.lang.String r17, int r18, int r19, int r20, java.lang.String r21, int r22, int r23, java.lang.String r24, java.lang.String r25, int r26, java.lang.String r27, java.lang.String r28) {
        /*
            Method dump skipped, instructions count: 409
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.asks.UnknownStore.addCertPolicy(java.lang.String, int, int, int, java.lang.String, int, int, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String):void");
    }

    public final void addPermission(String str, boolean z, int i, int i2, int i3, int i4, int i5, int i6, boolean z2, String str2, int i7, String str3, String str4, String str5, boolean z3) {
        HashMap hashMap;
        if (this.blockPermGroup == null) {
            this.blockPermGroup = new ArrayList();
        }
        if (this.warningPermGroup == null) {
            this.warningPermGroup = new ArrayList();
        }
        if (this.blockPermissions == null) {
            this.blockPermissions = new HashMap();
        }
        if (this.warningPermissions == null) {
            this.warningPermissions = new HashMap();
        }
        if (this.tagNameList == null) {
            this.tagNameList = new ArrayList();
        }
        if (z) {
            if (z2) {
                int size = this.blockPermGroup.size();
                hashMap = size > 0 ? (HashMap) this.blockPermGroup.get(size - 1) : new HashMap();
            } else {
                hashMap = this.blockPermissions;
            }
        } else if (z2) {
            int size2 = this.warningPermGroup.size();
            hashMap = size2 > 0 ? (HashMap) this.warningPermGroup.get(size2 - 1) : new HashMap();
        } else {
            hashMap = this.warningPermissions;
        }
        if (hashMap == null || hashMap.containsKey(str)) {
            return;
        }
        if (this.tagNameList.contains(str5 + str)) {
            return;
        }
        PEMINFO peminfo = new PEMINFO();
        peminfo.MIN = i2;
        peminfo.MAX = i3;
        peminfo.SA = i;
        peminfo.policy = i4;
        peminfo.policyTarget = i5;
        peminfo.moreRules = null;
        peminfo.tagName = str3;
        peminfo.eventNameForSA = str4;
        if (str2 != null && i7 != -1) {
            MORERULES morerules = new MORERULES();
            morerules.result_moreRule_RANK = false;
            morerules.result_moreRule_RandomPkg = false;
            morerules.result_moreRule_Malformed = false;
            peminfo.moreRules = morerules;
            morerules.moreRulePolicy = -1;
            morerules.check_moreRule_RANK = false;
            morerules.check_moreRule_RandomPkg = false;
            morerules.check_moreRule_Malformed = false;
            String[] split = str2.split("=");
            if (split != null && split.length == 2) {
                String[] split2 = split[0].split("\\+");
                for (int i8 = 0; i8 < split2.length; i8++) {
                    if ("famous".equalsIgnoreCase(split2[i8])) {
                        morerules.check_moreRule_RANK = true;
                    } else if ("randompkg".equalsIgnoreCase(split2[i8])) {
                        morerules.check_moreRule_RandomPkg = true;
                    } else if ("malformed".equalsIgnoreCase(split2[i8])) {
                        morerules.check_moreRule_Malformed = true;
                    }
                }
                if (morerules.check_moreRule_RANK || morerules.check_moreRule_RandomPkg || morerules.check_moreRule_Malformed) {
                    morerules.moreRulePolicy = i7;
                }
            }
        }
        hashMap.put(str, peminfo);
        if (z3) {
            this.tagNameList.add(str5 + str);
        }
    }

    public final void addPermissionGroup(boolean z) {
        if (this.blockPermGroup == null) {
            this.blockPermGroup = new ArrayList();
        }
        if (this.warningPermGroup == null) {
            this.warningPermGroup = new ArrayList();
        }
        HashMap hashMap = new HashMap();
        if (z) {
            this.blockPermGroup.add(hashMap);
        } else {
            this.warningPermGroup.add(hashMap);
        }
    }

    public final void checkDomain(String str, RETVALUE retvalue) {
        HashMap hashMap;
        if (isDevDevice()) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(" checkDomain() : ", str, "PackageInformationStore");
        }
        if (str == null || (hashMap = this.certPolicies) == null || !hashMap.containsKey(str)) {
            return;
        }
        HashMap hashMap2 = (HashMap) this.certPolicies.get(str);
        if (hashMap2 == null || !hashMap2.containsKey("ALL")) {
            Slog.d("PackageInformationStore", " no");
            return;
        }
        PKGINFO pkginfo = (PKGINFO) hashMap2.get("ALL");
        if (pkginfo != null) {
            Slog.i("PackageInformationStore", "checkPolicyWithDetail() : Target");
            retvalue.set(0, pkginfo.policy, pkginfo.SA, pkginfo.isExecuteBlock, pkginfo.tagName, pkginfo.eventNameForSA, null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x01b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkMoreRule(com.android.server.asks.PEMINFO r23, com.android.server.asks.RETVALUE r24) {
        /*
            Method dump skipped, instructions count: 777
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.asks.UnknownStore.checkMoreRule(com.android.server.asks.PEMINFO, com.android.server.asks.RETVALUE):void");
    }

    public final void checkPolicy(String str, RETVALUE retvalue) {
        HashMap hashMap;
        PKGINFO pkginfo;
        HashMap hashMap2 = this.certPolicies;
        if (hashMap2 == null && this.blockPermissions == null && this.warningPermissions == null && this.blockPermGroup == null && this.warningPermGroup == null && (pkginfo = this.defaultCertPolicy) != null) {
            retvalue.set(0, pkginfo.policy, pkginfo.SA, pkginfo.isExecuteBlock, pkginfo.tagName, pkginfo.eventNameForSA, null);
            return;
        }
        if (str != null) {
            if (hashMap2 != null && hashMap2.containsKey(str) && (hashMap = (HashMap) this.certPolicies.get(str)) != null) {
                if (hashMap.size() != 1 || !hashMap.containsKey("ALL")) {
                    retvalue.set(1, 0, 0, 0, "", "", null);
                    return;
                }
                PKGINFO pkginfo2 = (PKGINFO) hashMap.get("ALL");
                if (pkginfo2 != null) {
                    Slog.i("PackageInformationStore", "checkPolicy() : Target");
                    retvalue.set(0, pkginfo2.policy, pkginfo2.SA, pkginfo2.isExecuteBlock, pkginfo2.tagName, pkginfo2.eventNameForSA, null);
                    return;
                }
            }
            if (this.blockPermissions != null && this.warningPermissions != null && this.blockPermGroup != null && this.warningPermGroup != null) {
                retvalue.set(2, 0, 0, 0, "", "", null);
                return;
            } else if (this.defaultCertPolicy != null) {
                Slog.i("PackageInformationStore", "checkPolicy() : Default");
                PKGINFO pkginfo3 = this.defaultCertPolicy;
                retvalue.set(0, pkginfo3.policy, pkginfo3.SA, pkginfo3.isExecuteBlock, pkginfo3.tagName, pkginfo3.eventNameForSA, null);
                return;
            }
        }
        retvalue.set(4, 0, 0, 0, "", "", null);
    }

    public final void checkPolicyWithAppHash(String str, String str2, RETVALUE retvalue) {
        HashMap hashMap;
        PKGINFO pkginfo;
        HashMap hashMap2 = this.certPolicies;
        if (hashMap2 == null || str == null || !hashMap2.containsKey(str) || (hashMap = (HashMap) this.certPolicies.get(str)) == null) {
            return;
        }
        if (hashMap.containsKey(str2)) {
            PKGINFO pkginfo2 = (PKGINFO) hashMap.get(str2);
            if (pkginfo2 != null) {
                Slog.i("PackageInformationStore", "checkPolicyWithDetail() : Target");
                retvalue.set(0, pkginfo2.policy, pkginfo2.SA, pkginfo2.isExecuteBlock, pkginfo2.tagName, pkginfo2.eventNameForSA, null);
                return;
            }
            return;
        }
        if (this.blockPermissions != null && this.warningPermissions != null && this.blockPermGroup != null && this.warningPermGroup != null) {
            retvalue.set(2, 0, 0, 0, "", "", null);
        } else {
            if (!hashMap.containsKey("ALL") || (pkginfo = (PKGINFO) hashMap.get("ALL")) == null) {
                return;
            }
            Slog.i("PackageInformationStore", "checkPolicyWithDetail() : Target");
            retvalue.set(0, pkginfo.policy, pkginfo.SA, pkginfo.isExecuteBlock, pkginfo.tagName, pkginfo.eventNameForSA, null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:144:0x025f, code lost:
    
        if (r8 != 3) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00bb, code lost:
    
        if (r11 != 3) goto L64;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkPolicyWithPEM(java.lang.String[] r19, int r20, boolean r21, com.android.server.asks.RETVALUE r22) {
        /*
            Method dump skipped, instructions count: 926
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.asks.UnknownStore.checkPolicyWithPEM(java.lang.String[], int, boolean, com.android.server.asks.RETVALUE):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkRegexTarget(java.lang.String r12, com.android.server.asks.RETVALUE r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.asks.UnknownStore.checkRegexTarget(java.lang.String, com.android.server.asks.RETVALUE, boolean):boolean");
    }
}
