package com.samsung.android.knox.net.billing;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EnterpriseBillingPolicy {
    public static final String ALL_APPS_IN_SCOPE = "*";
    public ContextInfo mContextInfo;
    public IEnterpriseBillingPolicy billingPolicyService = null;
    public EnterpriseBillingPolicy policy = null;

    public EnterpriseBillingPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean activateProfile(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.activateProfile");
        return false;
    }

    public final boolean allowRoaming(String str, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.allowRoaming");
        return false;
    }

    public final boolean bindAppsToProfile(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.bindAppsToProfile");
        return false;
    }

    public final boolean bindVpnToProfile(String str, String str2, String str3) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.bindVpnToProfile");
        return false;
    }

    public final boolean createProfile(EnterpriseBillingProfile enterpriseBillingProfile) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.createProfile");
        return false;
    }

    public final List<String> getAppsBoundToProfile(String str) {
        return null;
    }

    public final List<String> getAvailableProfiles() {
        return null;
    }

    public final EnterpriseBillingProfile getBoundedProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.getBoundedProfile", true);
        return null;
    }

    public final synchronized IEnterpriseBillingPolicy getEnterpriseBillingService() {
        return this.billingPolicyService;
    }

    public final EnterpriseBillingProfile getProfileDetails(String str) {
        return null;
    }

    public final boolean isProfileActive(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.isProfileActive", true);
        return false;
    }

    public final boolean isRoamingAllowed(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.isRoamingAllowed", true);
        return false;
    }

    public final boolean removeProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.removeProfile");
        return false;
    }

    public final boolean unbindAppsFromProfile(String str, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.unbindAppsFromProfile");
        return false;
    }

    public final boolean unbindVpnFromProfile(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.unbindVpnFromProfile");
        return false;
    }

    public final boolean updateProfile(EnterpriseBillingProfile enterpriseBillingProfile) {
        EnterpriseLicenseManager.log(this.mContextInfo, "EnterpriseBillingPolicy.updateProfile");
        return false;
    }
}
