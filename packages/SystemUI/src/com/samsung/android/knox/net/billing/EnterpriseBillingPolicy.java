package com.samsung.android.knox.net.billing;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class EnterpriseBillingPolicy {
    public static final String ALL_APPS_IN_SCOPE = "*";
    public ContextInfo mContextInfo;
    public IEnterpriseBillingPolicy billingPolicyService = null;
    public EnterpriseBillingPolicy policy = null;

    public EnterpriseBillingPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public boolean activateProfile(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.activateProfile");
        return false;
    }

    public boolean allowRoaming(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.allowRoaming");
        return false;
    }

    public boolean bindAppsToProfile(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.bindAppsToProfile");
        return false;
    }

    public boolean bindVpnToProfile(String str, String str2, String str3) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.bindVpnToProfile");
        return false;
    }

    public boolean createProfile(EnterpriseBillingProfile enterpriseBillingProfile) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.createProfile");
        return false;
    }

    public List<String> getAppsBoundToProfile(String str) {
        return null;
    }

    public List<String> getAvailableProfiles() {
        return null;
    }

    public EnterpriseBillingProfile getBoundedProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.getBoundedProfile", true);
        return null;
    }

    public final synchronized IEnterpriseBillingPolicy getEnterpriseBillingService() {
        return this.billingPolicyService;
    }

    public EnterpriseBillingProfile getProfileDetails(String str) {
        return null;
    }

    public boolean isProfileActive(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.isProfileActive", true);
        return false;
    }

    public boolean isRoamingAllowed(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.isRoamingAllowed", true);
        return false;
    }

    public boolean removeProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.removeProfile");
        return false;
    }

    public boolean unbindAppsFromProfile(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.unbindAppsFromProfile");
        return false;
    }

    public boolean unbindVpnFromProfile(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.unbindVpnFromProfile");
        return false;
    }

    public boolean updateProfile(EnterpriseBillingProfile enterpriseBillingProfile) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.updateProfile");
        return false;
    }
}
