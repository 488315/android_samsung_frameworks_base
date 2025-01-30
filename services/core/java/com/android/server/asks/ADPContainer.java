package com.android.server.asks;

import java.util.ArrayList;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class ADPContainer {
  public ArrayList mADPPolicy;
  public String packageName;

  public ADPContainer(String str, ArrayList arrayList) {
    this.packageName = str;
    this.mADPPolicy = arrayList;
  }

  public String getPackageName() {
    return this.packageName;
  }

  public ArrayList getADPPolicy() {
    return this.mADPPolicy;
  }

  public class ADPPolicy implements Comparable {
    public String format;
    public String hashCode;
    public String pattern;
    public int versionType;

    public ADPPolicy(String str, String str2, int i, String str3) {
      this.pattern = str;
      this.hashCode = str2;
      this.versionType = i;
      this.format = str3;
    }

    public String getPattern() {
      return this.pattern;
    }

    public String getHashCode() {
      return this.hashCode;
    }

    public int getVersionType() {
      return this.versionType;
    }

    public String getFormat() {
      return this.format;
    }

    public boolean findMatcherByHashCode(String str) {
      return Pattern.compile(this.pattern).matcher(str).find();
    }

    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      ADPPolicy aDPPolicy = (ADPPolicy) obj;
      if (Pattern.compile(getPattern()).matcher(aDPPolicy.getHashCode()).find()) {
        return Pattern.compile(aDPPolicy.getPattern()).matcher(getHashCode()).find();
      }
      return false;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
      ADPPolicy aDPPolicy = (ADPPolicy) obj;
      int i = this.versionType;
      if (i != 1703114115) {
        return ADPOperation.isGreaterOrEqual(i, this.hashCode, aDPPolicy.getHashCode()) ? -1 : 1;
      }
      String str = this.format;
      return (str != null
              && ADPOperation.isGreaterOrEqual(
                  Pattern.compile(str), this.hashCode, aDPPolicy.getHashCode()))
          ? -1
          : 1;
    }
  }

  public class ADPPolicyBuilder {
    public String pattern = null;
    public String hashCode = null;
    public int versionType = -1;
    public String format = null;

    public ADPPolicyBuilder set_pattern(String str) {
      this.pattern = str;
      return this;
    }

    public ADPPolicyBuilder set_format(String str) {
      this.format = str;
      return this;
    }

    public ADPPolicyBuilder set_hashCode(String str) {
      this.hashCode = str;
      return this;
    }

    public ADPPolicyBuilder set_versionType(int i) {
      this.versionType = i;
      return this;
    }

    public ADPPolicy createADPPolicy() {
      String str;
      int i;
      String str2 = this.pattern;
      if (str2 == null || (str = this.hashCode) == null || (i = this.versionType) == -1) {
        return null;
      }
      return new ADPPolicy(str2, str, i, this.format);
    }

    public void flush() {
      this.pattern = null;
      this.hashCode = null;
      this.versionType = -1;
      this.format = null;
    }
  }

  public class ADPContainerBuilder {
    public String pkgName = null;
    public ArrayList mADPPolicy = null;

    public ADPContainerBuilder set_pkgName(String str) {
      this.pkgName = str;
      return this;
    }

    public ArrayList get_ADPPolicy() {
      return this.mADPPolicy;
    }

    public ADPContainerBuilder set_ADPPolicy() {
      this.mADPPolicy = new ArrayList();
      return this;
    }

    public ADPContainerBuilder add_ADPPolicy(ADPPolicy aDPPolicy) {
      this.mADPPolicy.add(aDPPolicy);
      return this;
    }

    public ADPContainer createADPContainer() {
      ArrayList arrayList;
      if (this.pkgName == null || (arrayList = this.mADPPolicy) == null || arrayList.size() == 0) {
        return null;
      }
      return new ADPContainer(this.pkgName, this.mADPPolicy);
    }

    public void flush() {
      this.pkgName = null;
      this.mADPPolicy = null;
    }
  }
}
