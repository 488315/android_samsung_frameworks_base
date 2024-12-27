package com.android.server.asks;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class InstalledAppInfo {
    public String name = "";
    public String signature = "";
    public String execute = "allow";
    public String overlay = "block";
    public String requestInstallerZip = "false";
    public String initType = "warning";
    public String accessibility = "false";
    public String hasReqInstallPEM = "false";
    public String installAuthority = "";
    public String installAuthorityDate = "";
    public String initPkg = "NA";

    public final void set(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this.name = str;
        this.signature = str2;
        this.execute = str3;
        this.overlay = str4;
        this.requestInstallerZip = str5;
        this.initType = str6;
        this.accessibility = str7;
        this.hasReqInstallPEM = str8;
        this.installAuthority = str9;
        this.installAuthorityDate = str10;
    }
}
