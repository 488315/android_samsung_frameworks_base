package com.android.server.am;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class FreecessPolicy {
    public ArrayList googleFreezeExemptionList = new ArrayList();
    public int googleFreezeTime;
    public boolean lessUnfreezeEnabled;
    public boolean masterSwitch;
    public int quickFreezeCheckTime;
    public boolean quickFreezeEnabled;
    public int quickFreezeIntervalTime;

    public boolean getMasterSwitch() {
        return this.masterSwitch;
    }

    public void setMasterSwitch(boolean z) {
        this.masterSwitch = z;
    }

    public boolean getQuickFreezeEnabled() {
        return this.quickFreezeEnabled;
    }

    public void setQuickFreezeEnabled(boolean z) {
        this.quickFreezeEnabled = z;
    }

    public boolean getLessUnfreezeEnabled() {
        return this.lessUnfreezeEnabled;
    }

    public void setLessUnfreezeEnabled(boolean z) {
        this.lessUnfreezeEnabled = z;
    }

    public int getQuickFreezeCheckTime() {
        return this.quickFreezeCheckTime;
    }

    public void setQuickFreezeCheckTime(int i) {
        this.quickFreezeCheckTime = i;
    }

    public int getQuickFreezeIntervalTime() {
        return this.quickFreezeCheckTime;
    }

    public void setQuickFreezeIntervalTime(int i) {
        this.quickFreezeIntervalTime = i;
    }

    public int getGoogleFreezeTime() {
        return this.googleFreezeTime;
    }

    public void setGoogleFreezeTime(int i) {
        this.googleFreezeTime = i;
    }

    public ArrayList getGoogleFreezeExemptionList() {
        return this.googleFreezeExemptionList;
    }

    public void addGoogleFreezeExemptionPackage(String str) {
        this.googleFreezeExemptionList.add(str);
    }
}
