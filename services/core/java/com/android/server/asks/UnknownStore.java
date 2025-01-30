package com.android.server.asks;

import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.knox.p024zt.devicetrust.EndpointMonitorConst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class UnknownStore {
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

  public void setKey(String str) {
    this.KEY_VALUE = str;
  }

  public void setPkgSigHash(String str) {
    this.PKGSIGHASH = str;
  }

  public void setSigHash(String str) {
    this.SIGHASH = str;
  }

  public void setPkgName(String str) {
    this.PKGNAME = str;
  }

  public void setBaseCodePath(String str) {
    this.BASE_CODE_PATH = str;
  }

  public String getKey() {
    return this.KEY_VALUE;
  }

  public ArrayList getRegexDomainList() {
    return this.regexDomainRule;
  }

  public ArrayList getExcuteBlockList() {
    return this.executeBlockPkgName;
  }

  public ArrayList getExceptList() {
    return this.exceptPkgName;
  }

  public HashMap getUnknownAppsList() {
    return this.unknownAppsList;
  }

  public boolean checkDomain(String str, RETVALUE retvalue) {
    HashMap hashMap;
    if (isDevDevice()) {
      Slog.d("PackageInformationStore", " checkDomain() : " + str);
    }
    if (str == null || (hashMap = this.certPolicies) == null || !hashMap.containsKey(str)) {
      return false;
    }
    HashMap hashMap2 = (HashMap) this.certPolicies.get(str);
    if (hashMap2 != null && hashMap2.containsKey("ALL")) {
      PKGINFO pkginfo = (PKGINFO) hashMap2.get("ALL");
      if (pkginfo == null) {
        return false;
      }
      Slog.i("PackageInformationStore", "checkPolicyWithDetail() : Target");
      retvalue.set(
          0,
          pkginfo.policy,
          pkginfo.f1643SA,
          pkginfo.isExecuteBlock,
          pkginfo.policyTarget,
          pkginfo.reportedTarget);
      return true;
    }
    Slog.d("PackageInformationStore", " no");
    return false;
  }

  /* JADX WARN: Removed duplicated region for block: B:12:0x0043  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean checkRegexTarget(String str, RETVALUE retvalue, boolean z) {
    ArrayList arrayList;
    HashMap hashMap;
    PKGINFO pkginfo;
    if (isDevDevice()) {
      Slog.d("PackageInformationStore", " checkRegexTarget() :" + str + ", isDomain : " + z);
    }
    boolean z2 = false;
    if (str != null) {
      PKGINFO pkginfo2 = null;
      if (z) {
        ArrayList arrayList2 = this.regexDomainRule;
        if (arrayList2 != null) {
          arrayList = (ArrayList) arrayList2.clone();
          if (arrayList != null) {
            int i = 0;
            while (true) {
              if (i >= arrayList.size()) {
                break;
              }
              String str2 = (String) arrayList.get(i);
              if (Pattern.compile(str2, 32).matcher(str).matches()) {
                if (isDevDevice()) {
                  Slog.d("PackageInformationStore", " hit :" + str + " by " + str2);
                }
                HashMap hashMap2 = this.certPolicies;
                if (hashMap2 != null && hashMap2.containsKey(str2)) {
                  HashMap hashMap3 = (HashMap) this.certPolicies.get(str2);
                  if (hashMap3 != null) {
                    if (hashMap3.containsKey("ALL")) {
                      pkginfo2 = (PKGINFO) hashMap3.get("ALL");
                    } else if (hashMap3.containsKey(str2)) {
                      pkginfo2 = (PKGINFO) hashMap3.get(str2);
                    }
                  }
                  if (pkginfo2 != null) {
                    Slog.i("PackageInformationStore", "checkPolicyWithDetail() : policy Target");
                    retvalue.set(
                        0,
                        pkginfo2.policy,
                        pkginfo2.f1643SA,
                        pkginfo2.isExecuteBlock,
                        pkginfo2.policyTarget,
                        pkginfo2.reportedTarget);
                    z2 = true;
                  }
                } else {
                  HashMap hashMap4 = this.certPolicies;
                  if (hashMap4 != null
                      && hashMap4.containsKey("ALL")
                      && (hashMap = (HashMap) this.certPolicies.get("ALL")) != null
                      && hashMap.containsKey(str2)
                      && (pkginfo = (PKGINFO) hashMap.get(str2)) != null) {
                    Slog.i("PackageInformationStore", "checkPolicyWithDetail() : ALL Target");
                    retvalue.set(
                        0,
                        pkginfo.policy,
                        pkginfo.f1643SA,
                        pkginfo.isExecuteBlock,
                        pkginfo.policyTarget,
                        pkginfo.reportedTarget);
                    z2 = true;
                  }
                }
              } else {
                i++;
              }
            }
            if (!z2) {
              Slog.i("PackageInformationStore", "Regex Policy does not match.");
            }
          }
        }
        arrayList = null;
        if (arrayList != null) {}
      } else {
        ArrayList arrayList3 = this.regexPackageRule;
        if (arrayList3 != null) {
          arrayList = (ArrayList) arrayList3.clone();
          if (arrayList != null) {}
        }
        arrayList = null;
        if (arrayList != null) {}
      }
    }
    return z2;
  }

  public final void addDefaultPolicy(
      int i, int i2, int i3, int i4, int i5, String str, String str2, int i6) {
    int i7;
    int i8;
    int i9;
    int i10;
    int i11;
    int i12 = i;
    if (this.defaultCertPolicy == null) {
      this.defaultCertPolicy = new PKGINFO();
      if (i12 == 500) {
        i12 = 505;
      } else if (i12 == 504) {
        if (this.executeBlockPkgName == null) {
          this.executeBlockPkgName = new ArrayList();
        }
        if (!this.executeBlockPkgName.contains("ALL")) {
          this.executeBlockPkgName.add("ALL");
          this.executeBlockPkgName.add(str);
        }
      }
      int i13 = i12;
      if (i6 == 500) {
        i7 = 500;
      } else {
        int i14 = 503;
        if (i6 != 503) {
          i14 = EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_EGRESS;
          if (i6 != 502) {
            i7 = i6;
          }
        }
        i7 = i14;
      }
      if (i4 == 500) {
        i9 = i5;
        i8 = 501;
      } else {
        i8 = i4;
        i9 = i5;
      }
      if (i9 == 500) {
        i11 = 501;
        i10 = i2;
      } else {
        i10 = i2;
        i11 = i9;
      }
      if (i10 == 500) {
        i10 = 0;
      }
      int i15 = i10;
      if (i3 == 0) {
        if (this.exceptPkgName == null) {
          this.exceptPkgName = new ArrayList();
        }
        if (!this.exceptPkgName.contains("ALL")) {
          this.exceptPkgName.add("ALL");
          this.exceptPkgName.add(str);
        }
      }
      if (this.unknownAppsList == null) {
        this.unknownAppsList = new HashMap();
      }
      if (!this.unknownAppsList.containsKey(str)) {
        this.unknownAppsList.put(str, str2);
      }
      this.defaultCertPolicy.set(i3, i13, i15, i8, i11, i7);
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:33:0x0097  */
  /* JADX WARN: Removed duplicated region for block: B:40:0x00ce  */
  /* JADX WARN: Removed duplicated region for block: B:48:0x00f5  */
  /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:55:0x00b0  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void addCertPolicy(
      String str,
      int i,
      int i2,
      int i3,
      String str2,
      int i4,
      int i5,
      String str3,
      String str4,
      int i6) {
    int i7;
    int i8;
    int i9;
    int i10;
    int i11 = i;
    if ("ALL".equals(str2) && "ALL".equals(str)) {
      addDefaultPolicy(i, i2, i3, i4, i5, str3, str4, i6);
      return;
    }
    if (this.certPolicies == null) {
      this.certPolicies = new HashMap();
    }
    if (this.certPolicies == null || str == null || str2 == null) {
      return;
    }
    PKGINFO pkginfo = new PKGINFO();
    PKGINFO pkginfo2 = this.defaultCertPolicy;
    if (pkginfo2 != null) {
      if (i11 == 500) {
        i11 = pkginfo2.isExecuteBlock;
      } else if (i11 == 504) {
        if (this.executeBlockPkgName == null) {
          this.executeBlockPkgName = new ArrayList();
        }
        if (!this.executeBlockPkgName.contains(str)) {
          this.executeBlockPkgName.add(str);
        }
      }
      i8 = i4;
      if (i8 == 500) {
        i8 = this.defaultCertPolicy.policyTarget;
      }
      i9 = i5;
      if (i9 == 500) {
        i9 = this.defaultCertPolicy.reportedTarget;
      }
      i7 = i2;
      if (i7 == 500) {
        i7 = this.defaultCertPolicy.f1643SA;
      }
      if (i6 == 500) {
        i10 = this.defaultCertPolicy.regexDomain;
        int i12 = i9;
        int i13 = i7;
        int i14 = i8;
        if (i6 != 503) {
          if (this.regexDomainRule == null) {
            this.regexDomainRule = new ArrayList();
          }
          if (!this.regexDomainRule.contains(str)) {
            this.regexDomainRule.add(str);
          }
        } else if (i10 == 502) {
          if (this.regexPackageRule == null) {
            this.regexPackageRule = new ArrayList();
          }
          if (!this.regexPackageRule.contains(str)) {
            this.regexPackageRule.add(str);
          }
        }
        if (i3 == 0) {
          if (this.exceptPkgName == null) {
            this.exceptPkgName = new ArrayList();
          }
          if (!this.exceptPkgName.contains(str)) {
            this.exceptPkgName.add(str);
          }
        }
        pkginfo.set(i3, i11, i13, i14, i12, i10);
        if (this.certPolicies.containsKey(str)) {
          HashMap hashMap = new HashMap();
          if ("ALL".equals(str2)) {
            hashMap.put("ALL", pkginfo);
          } else {
            hashMap.put(str2, pkginfo);
          }
          this.certPolicies.put(str, hashMap);
          return;
        }
        return;
      }
    } else {
      i7 = i2;
      i8 = i4;
      i9 = i5;
    }
    i10 = i6;
    int i122 = i9;
    int i132 = i7;
    int i142 = i8;
    if (i6 != 503) {}
    if (i3 == 0) {}
    pkginfo.set(i3, i11, i132, i142, i122, i10);
    if (this.certPolicies.containsKey(str)) {}
  }

  public void addPermissionGroup(boolean z) {
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

  public void addPermission(
      String str,
      boolean z,
      int i,
      int i2,
      int i3,
      int i4,
      int i5,
      int i6,
      boolean z2,
      String str2,
      int i7,
      String str3,
      boolean z3) {
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
        if (size > 0) {
          hashMap = (HashMap) this.blockPermGroup.get(size - 1);
        } else {
          hashMap = new HashMap();
        }
      } else {
        hashMap = this.blockPermissions;
      }
    } else if (z2) {
      int size2 = this.warningPermGroup.size();
      if (size2 > 0) {
        hashMap = (HashMap) this.warningPermGroup.get(size2 - 1);
      } else {
        hashMap = new HashMap();
      }
    } else {
      hashMap = this.warningPermissions;
    }
    if (hashMap == null || hashMap.containsKey(str)) {
      return;
    }
    if (this.tagNameList.contains(str3 + str)) {
      return;
    }
    PEMINFO peminfo = new PEMINFO();
    peminfo.set(i2, i3, i, i4, i5, i6);
    if (str2 != null && i7 != -1) {
      peminfo.setMoreRules(str2, i7);
    }
    hashMap.put(str, peminfo);
    if (z3) {
      this.tagNameList.add(str3 + str);
    }
  }

  public void checkPolicy(String str, RETVALUE retvalue) {
    HashMap hashMap;
    PKGINFO pkginfo;
    HashMap hashMap2 = this.certPolicies;
    if (hashMap2 == null
        && this.blockPermissions == null
        && this.warningPermissions == null
        && this.blockPermGroup == null
        && this.warningPermGroup == null
        && (pkginfo = this.defaultCertPolicy) != null) {
      retvalue.set(
          0,
          pkginfo.policy,
          pkginfo.f1643SA,
          pkginfo.isExecuteBlock,
          pkginfo.policyTarget,
          pkginfo.reportedTarget);
      return;
    }
    if (str != null && retvalue != null) {
      if (hashMap2 != null
          && hashMap2.containsKey(str)
          && (hashMap = (HashMap) this.certPolicies.get(str)) != null) {
        if (hashMap.size() == 1 && hashMap.containsKey("ALL")) {
          PKGINFO pkginfo2 = (PKGINFO) hashMap.get("ALL");
          if (pkginfo2 != null) {
            Slog.i("PackageInformationStore", "checkPolicy() : Target");
            retvalue.set(
                0,
                pkginfo2.policy,
                pkginfo2.f1643SA,
                pkginfo2.isExecuteBlock,
                pkginfo2.policyTarget,
                pkginfo2.reportedTarget);
            return;
          }
        } else {
          retvalue.set(1, 0, 0, 0, 0, 0);
          return;
        }
      }
      if (this.blockPermissions != null
          && this.warningPermissions != null
          && this.blockPermGroup != null
          && this.warningPermGroup != null) {
        retvalue.set(2, 0, 0, 0, 0, 0);
        return;
      } else if (this.defaultCertPolicy != null) {
        Slog.i("PackageInformationStore", "checkPolicy() : Default");
        PKGINFO pkginfo3 = this.defaultCertPolicy;
        retvalue.set(
            0,
            pkginfo3.policy,
            pkginfo3.f1643SA,
            pkginfo3.isExecuteBlock,
            pkginfo3.policyTarget,
            pkginfo3.reportedTarget);
        return;
      }
    }
    if (retvalue != null) {
      retvalue.set(4, 0, 0, 0, 0, 0);
    }
  }

  public void checkPolicyWithAppHash(String str, String str2, RETVALUE retvalue) {
    HashMap hashMap;
    PKGINFO pkginfo;
    HashMap hashMap2 = this.certPolicies;
    if (hashMap2 == null
        || str == null
        || str2 == null
        || retvalue == null
        || !hashMap2.containsKey(str)
        || (hashMap = (HashMap) this.certPolicies.get(str)) == null) {
      return;
    }
    if (hashMap.containsKey(str2)) {
      PKGINFO pkginfo2 = (PKGINFO) hashMap.get(str2);
      if (pkginfo2 != null) {
        Slog.i("PackageInformationStore", "checkPolicyWithDetail() : Target");
        retvalue.set(
            0,
            pkginfo2.policy,
            pkginfo2.f1643SA,
            pkginfo2.isExecuteBlock,
            pkginfo2.policyTarget,
            pkginfo2.reportedTarget);
        return;
      }
      return;
    }
    if (this.blockPermissions != null
        && this.warningPermissions != null
        && this.blockPermGroup != null
        && this.warningPermGroup != null) {
      retvalue.set(2, 0, 0, 0, 0, 0);
    } else {
      if (!hashMap.containsKey("ALL") || (pkginfo = (PKGINFO) hashMap.get("ALL")) == null) {
        return;
      }
      Slog.i("PackageInformationStore", "checkPolicyWithDetail() : Target");
      retvalue.set(
          0,
          pkginfo.policy,
          pkginfo.f1643SA,
          pkginfo.isExecuteBlock,
          pkginfo.policyTarget,
          pkginfo.reportedTarget);
    }
  }

  public final void checkMoreRule(PEMINFO peminfo, RETVALUE retvalue) {
    if (retvalue != null) {
      retvalue.setStatus(3);
    }
    if (peminfo != null && peminfo.moreRules != null) {
      Slog.i(
          "PackageInformationStore",
          "checkMoreRule() : ["
              + peminfo.moreRules.check_moreRule_RANK
              + "]["
              + peminfo.moreRules.check_moreRule_RandomPkg
              + "]["
              + peminfo.moreRules.check_moreRule_Malformed
              + "]");
      if (peminfo.moreRules.check_moreRule_RANK) {
        if (isDevDevice()) {
          Slog.i(
              "PackageInformationStore",
              "check_moreRule_Famous =" + peminfo.moreRules.moreRulePolicy);
        }
        if (new MoreRuleRANK().getResult(this.SIGHASH, this.PKGSIGHASH) == 0) {
          StringBuilder sb = new StringBuilder();
          sb.append("It is target of rank. ");
          sb.append(isDevDevice() ? Integer.valueOf(peminfo.moreRules.moreRulePolicy) : "");
          Slog.e("PackageInformationStore", sb.toString());
          retvalue.setStatus(0);
        } else {
          retvalue.setStatus(3);
          return;
        }
      }
      if (peminfo.moreRules.check_moreRule_RandomPkg) {
        if (isDevDevice()) {
          Slog.i(
              "PackageInformationStore",
              "check_moreRule_RandomPkg" + peminfo.moreRules.moreRulePolicy);
        }
        if (new MoreRuleRandomPkg().getResult(this.PKGNAME) == 0) {
          if (isDevDevice()) {
            Slog.i(
                "PackageInformationStore",
                "It is target of randomPkg. " + peminfo.moreRules.moreRulePolicy);
          }
          retvalue.setStatus(0);
        } else {
          retvalue.setStatus(3);
          return;
        }
      }
      if (peminfo.moreRules.check_moreRule_Malformed) {
        if (isDevDevice()) {
          Slog.i(
              "PackageInformationStore",
              "check_moreRule_MALFORMED:" + peminfo.moreRules.moreRulePolicy);
        }
        int[] analyzeZipFile = ZipAnalyzerUtil.analyzeZipFile(this.BASE_CODE_PATH);
        if (analyzeZipFile != null) {
          if (Arrays.stream(analyzeZipFile).sum() > 0) {
            Slog.i("PackageInformationStore", "Zip broken");
            retvalue.set(0, 0, 0, 0, 0, 0);
            return;
          } else {
            Slog.i("PackageInformationStore", "Zip Success");
            retvalue.setStatus(3);
            return;
          }
        }
        return;
      }
      return;
    }
    retvalue.setStatus(3);
    Slog.i("PackageInformationStore", "The moreRule targetPolicy may be NULL.");
  }

  /* JADX WARN: Code restructure failed: missing block: B:146:0x02cf, code lost:

     if (r8 != 3) goto L149;
  */
  /* JADX WARN: Code restructure failed: missing block: B:174:0x0372, code lost:

     if (r9 != 3) goto L178;
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x00e9, code lost:

     if (r7 != 3) goto L66;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void checkPolicyWithPEM(String[] strArr, int i, boolean z, RETVALUE retvalue) {
    String str;
    boolean z2;
    HashMap hashMap;
    HashMap hashMap2;
    boolean z3;
    PEMINFO peminfo;
    if (retvalue != null) {
      Slog.i("PackageInformationStore", "start to check P policy.");
      if (this.blockPermGroup != null
          && this.warningPermGroup != null
          && this.blockPermissions != null
          && this.warningPermissions != null
          && strArr != null) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : strArr) {
          arrayList.add(str2);
        }
        int i2 = 0;
        boolean z4 = true;
        String str3 = null;
        int i3 = 0;
        while (true) {
          if (i2 >= this.blockPermGroup.size()) {
            str = str3;
            z2 = z4;
            hashMap = null;
            break;
          }
          hashMap = (HashMap) this.blockPermGroup.get(i2);
          Iterator it = hashMap.entrySet().iterator();
          int i4 = i3;
          str = null;
          while (true) {
            if (!it.hasNext()) {
              z2 = true;
              break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            if (!arrayList.contains(entry.getKey())) {
              z2 = false;
              break;
            }
            if (str == null) {
              str = (String) entry.getKey();
              if (isDevDevice()) {
                Slog.i("PackageInformationStore", "additional P(B) :targetKey  - " + str);
              }
            }
            i4++;
          }
          if (z2) {
            Slog.i("PackageInformationStore", "all target! " + i4);
            break;
          }
          i2++;
          z4 = z2;
          str3 = str;
          i3 = i4;
        }
        if (hashMap != null && z2) {
          PEMINFO peminfo2 = (PEMINFO) hashMap.get(str);
          Slog.i("PackageInformationStore", "checkPolicywithPem(B) :targetKey  - " + str);
          if (peminfo2 != null && peminfo2.MIN <= i && i <= peminfo2.MAX) {
            checkMoreRule(peminfo2, retvalue);
            int i5 = retvalue.status;
            if (i5 == 0) {
              MORERULES morerules = peminfo2.moreRules;
              if (morerules != null) {
                peminfo2.policy = morerules.moreRulePolicy;
                if (isDevDevice()) {
                  Slog.d(
                      "PackageInformationStore",
                      "additional P(B): apply MoreRule" + peminfo2.policy);
                }
              }
            }
            int i6 = peminfo2.policyTarget;
            if (i6 == 502 && !z) {
              Slog.i("PackageInformationStore", "additional P(B) : Target Group(local)");
              retvalue.set(
                  0,
                  peminfo2.policy,
                  peminfo2.f1642SA,
                  505,
                  peminfo2.policyTarget,
                  peminfo2.reportedTarget);
              return;
            } else if (i6 == 503 && z) {
              Slog.i("PackageInformationStore", "additional P(B) : Target Group(url)");
              retvalue.set(
                  0,
                  peminfo2.policy,
                  peminfo2.f1642SA,
                  505,
                  peminfo2.policyTarget,
                  peminfo2.reportedTarget);
              return;
            } else if (i6 == 501) {
              Slog.i("PackageInformationStore", "additional P(B) :  Target Group");
              retvalue.set(
                  0,
                  peminfo2.policy,
                  peminfo2.f1642SA,
                  505,
                  peminfo2.policyTarget,
                  peminfo2.reportedTarget);
              return;
            }
          }
        }
        for (int i7 = 0; i7 < strArr.length; i7++) {
          if (this.blockPermissions.containsKey(strArr[i7])
              && (peminfo = (PEMINFO) this.blockPermissions.get(strArr[i7])) != null
              && peminfo.MIN <= i
              && i <= peminfo.MAX) {
            checkMoreRule(peminfo, retvalue);
            int i8 = retvalue.status;
            if (i8 == 0) {
              MORERULES morerules2 = peminfo.moreRules;
              if (morerules2 != null) {
                peminfo.policy = morerules2.moreRulePolicy;
                if (isDevDevice()) {
                  Slog.d(
                      "PackageInformationStore",
                      "additional P(B): apply MoreRule" + peminfo.policy);
                }
              }
            } else if (i8 != 3) {
              continue;
            }
            int i9 = peminfo.policyTarget;
            if (i9 == 502 && !z) {
              Slog.i("PackageInformationStore", "additional P(B) : Target(local)");
              retvalue.set(
                  0,
                  peminfo.policy,
                  peminfo.f1642SA,
                  505,
                  peminfo.policyTarget,
                  peminfo.reportedTarget);
              return;
            } else if (i9 == 503 && z) {
              Slog.i("PackageInformationStore", "additional P(B) : Target(url)");
              retvalue.set(
                  0,
                  peminfo.policy,
                  peminfo.f1642SA,
                  505,
                  peminfo.policyTarget,
                  peminfo.reportedTarget);
              return;
            } else if (i9 == 501) {
              Slog.i("PackageInformationStore", "additional P(B) : Target");
              retvalue.set(
                  0,
                  peminfo.policy,
                  peminfo.f1642SA,
                  505,
                  peminfo.policyTarget,
                  peminfo.reportedTarget);
              return;
            }
          }
        }
        int i10 = 0;
        boolean z5 = true;
        String str4 = null;
        int i11 = 0;
        while (true) {
          if (i10 >= this.warningPermGroup.size()) {
            hashMap2 = null;
            break;
          }
          HashMap hashMap3 = (HashMap) this.warningPermGroup.get(i10);
          Iterator it2 = hashMap3.entrySet().iterator();
          int i12 = i11;
          String str5 = null;
          while (true) {
            if (!it2.hasNext()) {
              z3 = true;
              break;
            }
            Map.Entry entry2 = (Map.Entry) it2.next();
            if (!arrayList.contains(entry2.getKey())) {
              z3 = false;
              break;
            }
            if (str5 == null) {
              str5 = (String) entry2.getKey();
              if (isDevDevice()) {
                Slog.i("PackageInformationStore", "additional P(Wa) : targetKey=" + str5);
              }
            }
            i12++;
          }
          if (z3) {
            Slog.i("PackageInformationStore", "additional P(Wa) : target all " + i12);
            hashMap2 = hashMap3;
            str4 = str5;
            z5 = z3;
            break;
          }
          i10++;
          z5 = z3;
          str4 = str5;
          i11 = i12;
        }
        if (hashMap2 != null && z5) {
          PEMINFO peminfo3 = (PEMINFO) hashMap2.get(str4);
          if (isDevDevice()) {
            Slog.i("PackageInformationStore", "all target key: " + str4);
          }
          if (peminfo3 != null && peminfo3.MIN <= i && i <= peminfo3.MAX) {
            checkMoreRule(peminfo3, retvalue);
            int i13 = retvalue.status;
            if (i13 == 0) {
              MORERULES morerules3 = peminfo3.moreRules;
              if (morerules3 != null) {
                peminfo3.policy = morerules3.moreRulePolicy;
                Slog.i(
                    "PackageInformationStore",
                    "additional P(Wa): apply MoreRule" + peminfo3.policy);
              }
            }
            int i14 = peminfo3.policyTarget;
            if (i14 == 502 && !z) {
              Slog.i("PackageInformationStore", "additional P(Wa) : Target Group(local)");
              retvalue.set(
                  0,
                  peminfo3.policy,
                  peminfo3.f1642SA,
                  505,
                  peminfo3.policyTarget,
                  peminfo3.reportedTarget);
              return;
            } else if (i14 == 503 && z) {
              Slog.i("PackageInformationStore", "additional P(Wa) : Target Group(url)");
              retvalue.set(
                  0,
                  peminfo3.policy,
                  peminfo3.f1642SA,
                  505,
                  peminfo3.policyTarget,
                  peminfo3.reportedTarget);
              return;
            } else if (i14 == 501) {
              Slog.i("PackageInformationStore", "additional P(Wa) :  Target Group");
              retvalue.set(
                  0,
                  peminfo3.policy,
                  peminfo3.f1642SA,
                  505,
                  peminfo3.policyTarget,
                  peminfo3.reportedTarget);
              return;
            }
          }
        }
        for (int i15 = 0; i15 < strArr.length; i15++) {
          if (this.warningPermissions.containsKey(strArr[i15])) {
            PEMINFO peminfo4 = (PEMINFO) this.warningPermissions.get(strArr[i15]);
            if (peminfo4.MIN <= i && i <= peminfo4.MAX) {
              checkMoreRule(peminfo4, retvalue);
              int i16 = retvalue.status;
              if (i16 == 0) {
                MORERULES morerules4 = peminfo4.moreRules;
                if (morerules4 != null) {
                  peminfo4.policy = morerules4.moreRulePolicy;
                  Slog.i(
                      "PackageInformationStore",
                      "additional P(Wa): apply MoreRule" + peminfo4.policy);
                }
              }
              int i17 = peminfo4.policyTarget;
              if (i17 == 502 && !z) {
                Slog.i("PackageInformationStore", "additional P(Wa) : Target(local)");
                retvalue.set(
                    0,
                    peminfo4.policy,
                    peminfo4.f1642SA,
                    505,
                    peminfo4.policyTarget,
                    peminfo4.reportedTarget);
                return;
              } else if (i17 == 503 && z) {
                Slog.i("PackageInformationStore", "additional P(Wa) : Target(url)");
                retvalue.set(
                    0,
                    peminfo4.policy,
                    peminfo4.f1642SA,
                    505,
                    peminfo4.policyTarget,
                    peminfo4.reportedTarget);
                return;
              } else {
                if (i17 == 501) {
                  Slog.i("PackageInformationStore", "additional P(Wa) : Target");
                  retvalue.set(
                      0,
                      peminfo4.policy,
                      peminfo4.f1642SA,
                      505,
                      peminfo4.policyTarget,
                      peminfo4.reportedTarget);
                  return;
                }
              }
            }
          }
        }
      }
      if (this.defaultCertPolicy != null) {
        Slog.i("PackageInformationStore", "additional P : Default");
        PKGINFO pkginfo = this.defaultCertPolicy;
        retvalue.set(
            0,
            pkginfo.policy,
            pkginfo.f1643SA,
            pkginfo.isExecuteBlock,
            pkginfo.policyTarget,
            pkginfo.reportedTarget);
        return;
      }
      retvalue.set(4, 0, 0, 0, 0, 0);
    }
  }

  public final boolean isDevDevice() {
    return "0x1".equals(SystemProperties.get("ro.boot.em.status"));
  }
}
