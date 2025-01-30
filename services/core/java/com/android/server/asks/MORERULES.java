package com.android.server.asks;

/* compiled from: UnknownStore.java */
/* loaded from: classes.dex */
public class MORERULES {
    public boolean check_moreRule_RANK = false;
    public boolean check_moreRule_RandomPkg = false;
    public boolean check_moreRule_Malformed = false;
    public int moreRulePolicy = -1;
    public String RANK = "famous";
    public String RANDOM_PKG = "randompkg";
    public String MALFORMED_APK = "malformed";

    public void set(String str, int i) {
        String[] split;
        this.moreRulePolicy = -1;
        this.check_moreRule_RANK = false;
        this.check_moreRule_RandomPkg = false;
        this.check_moreRule_Malformed = false;
        if (str == null || (split = str.split("=")) == null || split.length != 2) {
            return;
        }
        String[] split2 = split[0].split("\\+");
        for (int i2 = 0; i2 < split2.length; i2++) {
            if (this.RANK.equalsIgnoreCase(split2[i2])) {
                this.check_moreRule_RANK = true;
            } else if (this.RANDOM_PKG.equalsIgnoreCase(split2[i2])) {
                this.check_moreRule_RandomPkg = true;
            } else if (this.MALFORMED_APK.equalsIgnoreCase(split2[i2])) {
                this.check_moreRule_Malformed = true;
            }
        }
        if (this.check_moreRule_RANK || this.check_moreRule_RandomPkg || this.check_moreRule_Malformed) {
            this.moreRulePolicy = i;
        }
    }
}
