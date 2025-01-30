package com.android.server.asks;

/* compiled from: UnknownStore.java */
/* loaded from: classes.dex */
public class InstalledAppInfo {
    public String name = "";
    public String signature = "";
    public String execute = "allow";
    public String overlay = "block";
    public String requestInstallerZip = "false";
    public String initType = "warning";
    public String accessibility = "false";
    public String hasReqInstallPEM = "false";

    public void set(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.name = str;
        this.signature = str2;
        this.execute = str3;
        this.overlay = str4;
        this.requestInstallerZip = str5;
        this.initType = str6;
        this.accessibility = str7;
        this.hasReqInstallPEM = str8;
    }
}
