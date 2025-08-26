package com.samsung.android.knox.net.firewall;

import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knox.net.firewall.IFirewall;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/* loaded from: classes4.dex */
public class Firewall {
    public static final String ACTION_BLOCKED_DOMAIN = "com.samsung.android.knox.intent.action.BLOCKED_DOMAIN";
    public static final int ADD_OPERATION = 1;
    public static final String EXTRA_BLOCKED_DOMAIN_ISFOREGROUND = "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_ISFOREGROUND";
    public static final String EXTRA_BLOCKED_DOMAIN_PACKAGENAME = "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_PACKAGENAME";
    public static final String EXTRA_BLOCKED_DOMAIN_TIMESTAMP = "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_TIMESTAMP";
    public static final String EXTRA_BLOCKED_DOMAIN_UID = "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_UID";
    public static final String EXTRA_BLOCKED_DOMAIN_URL = "com.samsung.android.knox.intent.extra.BLOCKED_DOMAIN_URL";
    public static final int FIREWALL_ALLOW_RULE = 1;
    public static final String FIREWALL_ALL_PACKAGES = "*";
    public static final int FIREWALL_ALL_RULES = 15;
    public static final int FIREWALL_DENY_RULE = 2;
    public static final int FIREWALL_REDIRECT_EXCEPTION_RULE = 8;
    public static final int FIREWALL_REDIRECT_RULE = 4;
    public static final String FIREWALL_SYSTEM_UIDS = "SYSTEM_UIDS";
    public static final int REMOVE_OPERATION = -1;
    public ContextInfo mContextInfo;
    public IFirewall mService;
    public static final int MAX_LIST_SIZE_IN_BYTES = IBinder.getSuggestedMaxIpcSizeBytes();
    public static final List<DomainFilterRule> FIREWALL_ALL_DOMAIN_RULES = null;
    public static String TAG = "FirewallSDK";
    public static final Random mRand = new Random();

    public enum AddressType {
        IPV4,
        IPV6
    }

    public enum Direction {
        INPUT,
        OUTPUT,
        ALL,
        FORWARD
    }

    public enum NetworkInterface {
        ALL_NETWORKS,
        WIFI_DATA_ONLY,
        MOBILE_DATA_ONLY
    }

    public enum PortLocation {
        REMOTE,
        LOCAL,
        ALL
    }

    public enum Protocol {
        TCP,
        UDP,
        ALL
    }

    public Firewall() {
        this(new ContextInfo(Process.myUid()));
    }

    public static int generateToken() {
        return mRand.nextInt(2147483646);
    }

    public FirewallResponse[] addDomainFilterRules(List<DomainFilterRule> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.addDomainFilterRules");
        String str = TAG;
        StringBuilder sb = new StringBuilder("addDomainFilterRules() - rules.size = ");
        sb.append(list != null ? Integer.valueOf(list.size()) : "0");
        Log.i(str, sb.toString());
        return evaluateAndProcessRules(list, 1);
    }

    public FirewallResponse[] addRules(FirewallRule[] firewallRuleArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.addRules");
        String str = TAG;
        StringBuilder sb = new StringBuilder("addRules() - FirewallRule[].length = ");
        sb.append(firewallRuleArr != null ? Integer.valueOf(firewallRuleArr.length) : "0");
        Log.i(str, sb.toString());
        if (getService() == null) {
            return null;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.e(TAG, "Firewall.addRules() : This device doesn't support this API.");
            return null;
        }
        try {
            return this.mService.addRules(this.mContextInfo, firewallRuleArr);
        } catch (RemoteException unused) {
            Log.e(TAG, "addRules() - RemoteException at addRules method.");
            return null;
        }
    }

    public FirewallResponse[] clearRules(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.clearRules");
        Log.i(TAG, "clearRules(bitmask = " + Integer.toBinaryString(i) + ")");
        if (getService() == null) {
            return null;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.e(TAG, "Firewall.clearRules() : This device doesn't support this API.");
            return null;
        }
        try {
            return this.mService.clearRules(this.mContextInfo, i);
        } catch (RemoteException unused) {
            Log.e(TAG, "clearRules() - RemoteException at clearRules method.");
            return null;
        }
    }

    public FirewallResponse enableDomainFilterOnIptables(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.enableDomainFilterOnIptables");
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 25) {
            return null;
        }
        try {
            return this.mService.enableDomainFilterOnIptables(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.d(TAG, "RemoteException at enableDomainFilterOnIptables method.");
            return null;
        }
    }

    public FirewallResponse enableDomainFilterReport(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "enableDomainFilterReport");
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.enableDomainFilterReport");
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 16) {
            return null;
        }
        try {
            return this.mService.enableDomainFilterReport(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.e(TAG, "enableDomainFilterReport() - RemoteException at enableDomainFilterReport method.");
            return null;
        }
    }

    public FirewallResponse enableFirewall(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.enableFirewall");
        Log.i(TAG, "enableFirewall(enabled = " + String.valueOf(z) + ")");
        if (getService() == null) {
            return null;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.e(TAG, "Firewall.enableFirewall() : This device doesn't support this API.");
            return null;
        }
        try {
            return this.mService.enableFirewall(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.e(TAG, "enableFirewall() - RemoteException at enableFirewall method.");
            return null;
        }
    }

    public final FirewallResponse[] evaluateAndProcessRules(List<DomainFilterRule> list, int i) {
        FirewallResponse[] firewallResponseArrRemoveDomainFilterRules;
        if (getService() == null) {
            Log.e(TAG, "evaluateAndProcessRules() - Error in getService()");
            return null;
        }
        int i2 = KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION;
        if (i2 >= 16) {
            ArrayList arrayList = new ArrayList();
            if (i2 < 17 && list != null) {
                DomainFilterRule domainFilterRule = new DomainFilterRule();
                for (int i3 = 0; i3 < list.size(); i3++) {
                    DomainFilterRule domainFilterRule2 = list.get(i3);
                    if (domainFilterRule2 != null && (!TextUtils.isEmpty(domainFilterRule2.getDns1()) || !TextUtils.isEmpty(domainFilterRule2.getDns2()))) {
                        arrayList.add(Integer.valueOf(i3));
                        list.set(i3, domainFilterRule);
                    }
                }
            }
            try {
                if (i != -1 || list != null) {
                    if (list != null && !list.isEmpty()) {
                        int iGenerateToken = generateToken();
                        HashMap map = new HashMap();
                        ArrayList arrayList2 = new ArrayList();
                        Iterator<DomainFilterRule> it = list.iterator();
                        while (it.hasNext()) {
                            map.putAll(pageableRule(it.next()));
                        }
                        int i4 = 0;
                        for (Map.Entry entry : map.entrySet()) {
                            int iIntValue = ((Integer) entry.getValue()).intValue();
                            i4 += iIntValue;
                            if (i4 <= MAX_LIST_SIZE_IN_BYTES) {
                                arrayList2.add((DomainFilterRule) entry.getKey());
                            } else {
                                Log.i(TAG, "evaluateAndProcessRules() SDK tokenValue: " + String.valueOf(((DomainFilterRule) arrayList2.get(0)).getIpcToken()));
                                this.mService.populateDomainFilterBrokenRules(this.mContextInfo, arrayList2, iGenerateToken);
                                Log.i(TAG, "populateDomainFilterBrokenRules - rulePageable = " + arrayList2);
                                arrayList2.clear();
                                arrayList2.add((DomainFilterRule) entry.getKey());
                                i4 = iIntValue;
                            }
                        }
                        if (!arrayList2.isEmpty()) {
                            this.mService.populateDomainFilterBrokenRules(this.mContextInfo, arrayList2, iGenerateToken);
                            Log.i(TAG, "populateDomainFilterBrokenRules() - rulePageable = " + arrayList2);
                        }
                        if (i == 1) {
                            Log.i(TAG, "populateDomainFilterBrokenRules() - Add Operation = " + i);
                            firewallResponseArrRemoveDomainFilterRules = this.mService.addDomainFilterRules(this.mContextInfo, iGenerateToken);
                        } else {
                            if (i != -1) {
                                Log.i(TAG, "populateDomainFilterBrokenRules() - Invalid Operation = " + i);
                                return null;
                            }
                            Log.i(TAG, "populateDomainFilterBrokenRules() - Remove Operation = " + i);
                            firewallResponseArrRemoveDomainFilterRules = this.mService.removeDomainFilterRules(this.mContextInfo, iGenerateToken);
                        }
                    }
                    Log.i(TAG, "evaluateAndProcessRules() - No rule specified");
                    return new FirewallResponse[]{new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, FirewallResponseMessages.NO_RULE_SPECIFIED)};
                }
                firewallResponseArrRemoveDomainFilterRules = this.mService.clearAllDomainFilterRules(this.mContextInfo);
                if (firewallResponseArrRemoveDomainFilterRules != null && !arrayList.isEmpty()) {
                    for (int i5 = 0; i5 < firewallResponseArrRemoveDomainFilterRules.length; i5++) {
                        if (arrayList.contains(Integer.valueOf(i5))) {
                            firewallResponseArrRemoveDomainFilterRules[i5] = new FirewallResponse(FirewallResponse.Result.FAILED, FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR, FirewallResponseMessages.FAILED_DNS_MDM_VERSION);
                        }
                    }
                }
                return firewallResponseArrRemoveDomainFilterRules;
            } catch (RemoteException unused) {
                Log.e(TAG, "evaluateAndProcessRules() - RemoteException at evaluateAndProcessRules method");
            }
        } else {
            Log.e(TAG, "evaluateAndProcessRules() - Not supported");
        }
        return null;
    }

    public List<DomainFilterReport> getDomainFilterReport(List<String> list) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getDomainFilterReport");
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 16) {
            return null;
        }
        try {
            return this.mService.getDomainFilterReport(this.mContextInfo, list);
        } catch (RemoteException unused) {
            Log.e(TAG, "getDomainFilterReport() - RemoteException at getDomainFilterReport method.");
            return null;
        }
    }

    public List<DomainFilterRule> getDomainFilterRules(List<String> list) {
        List<DomainFilterRule> domainFilterRules;
        ArrayList arrayList = null;
        if (getService() != null && KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 16) {
            try {
                int iGenerateToken = generateToken();
                ArrayList arrayList2 = new ArrayList();
                do {
                    try {
                        domainFilterRules = this.mService.getDomainFilterRules(this.mContextInfo, list, iGenerateToken);
                        if (domainFilterRules != null && !domainFilterRules.isEmpty()) {
                            if (arrayList2.isEmpty()) {
                                arrayList2.addAll(domainFilterRules);
                            } else {
                                DomainFilterRule domainFilterRule = domainFilterRules.get(0);
                                DomainFilterRule domainFilterRuleUpdateLastDomainRule = updateLastDomainRule(domainFilterRule, arrayList2);
                                if (domainFilterRuleUpdateLastDomainRule != null) {
                                    arrayList2.set(arrayList2.size() - 1, domainFilterRuleUpdateLastDomainRule);
                                } else {
                                    arrayList2.add(domainFilterRule);
                                }
                                domainFilterRules.remove(0);
                                arrayList2.addAll(domainFilterRules);
                            }
                        }
                    } catch (RemoteException unused) {
                        arrayList = arrayList2;
                        Log.e(TAG, "getDomainFilterRules() - RemoteException at getDomainFilterRules method.");
                        return arrayList;
                    }
                } while (domainFilterRules != null);
                return arrayList2;
            } catch (RemoteException unused2) {
            }
        }
        return arrayList;
    }

    public FirewallRule[] getRules(int i, FirewallRule.Status status) {
        if (getService() != null) {
            if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 14) {
                try {
                    return status != null ? this.mService.getRules(this.mContextInfo, i, status.toString()) : this.mService.getRules(this.mContextInfo, i, null);
                } catch (RemoteException unused) {
                    Log.e(TAG, "getRules() - RemoteException at getRules method.");
                }
            } else {
                Log.e(TAG, "Firewall.getRules() : This device doesn't support this API.");
            }
        }
        return null;
    }

    public final IFirewall getService() {
        if (this.mService == null) {
            this.mService = IFirewall.Stub.asInterface(ServiceManager.getService("firewall"));
        }
        return this.mService;
    }

    public boolean isDomainFilterOnIptablesEnabled() {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.isDomainFilterOnIptablesEnabled");
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 25) {
            return false;
        }
        try {
            return this.mService.isDomainFilterOnIptablesEnabled(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.e(TAG, "isDomainFilterOnIptablesEnabled() - RemoteException at isDomainFilterOnIptablesEnabled method.");
            return false;
        }
    }

    public boolean isDomainFilterReportEnabled() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isDomainFilterReportEnabled");
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.isDomainFilterReportEnabled", true);
        if (getService() == null || KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 16) {
            return false;
        }
        try {
            return this.mService.isDomainFilterReportEnabled(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.e(TAG, "isDomainFilterReportEnabled() - RemoteException at isDomainFilterReportEnabled method.");
            return false;
        }
    }

    public boolean isFirewallEnabled() {
        if (getService() == null) {
            return false;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.e(TAG, "Firewall.isFirewallEnabled() : This device doesn't support this API.");
            return false;
        }
        try {
            return this.mService.isFirewallEnabled(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.e(TAG, "isFirewallEnabled() - RemoteException at isFirewallEnabled method.");
            return false;
        }
    }

    public String[] listIptablesRules() {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.listIptablesRules");
        if (getService() == null) {
            return null;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.i(TAG, "Firewall.listIptablesRules() : This device doesn't support this API.");
            return null;
        }
        try {
            return this.mService.listIptablesRules(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.e(TAG, "listIptablesRules() - RemoteException at listIptablesRules method.");
            return null;
        }
    }

    public final Map<DomainFilterRule, Integer> pageableRule(DomainFilterRule domainFilterRule) {
        int i;
        ArrayList arrayList;
        HashMap map = new HashMap();
        boolean z = false;
        if (domainFilterRule.getAllowDomains() != null && domainFilterRule.getDenyDomains() != null && domainFilterRule.getAllowDomains().isEmpty() && domainFilterRule.getDenyDomains().isEmpty()) {
            map.put(new DomainFilterRule(domainFilterRule.getApplication(), domainFilterRule.getDenyDomains(), domainFilterRule.getAllowDomains(), domainFilterRule.getDns1(), domainFilterRule.getDns2()), 0);
            return map;
        }
        int iGenerateToken = generateToken();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        AppIdentity application = domainFilterRule.getApplication();
        String dns1 = domainFilterRule.getDns1();
        int length = dns1 != null ? dns1.getBytes(StandardCharsets.UTF_8).length : 0;
        String dns2 = domainFilterRule.getDns2();
        int length2 = length + (dns2 != null ? dns2.getBytes(StandardCharsets.UTF_8).length : 0) + 8;
        if (application != null) {
            String signature = application.getSignature();
            String packageName = application.getPackageName();
            length2 = length2 + (signature != null ? signature.getBytes(StandardCharsets.UTF_8).length : 0) + (packageName != null ? packageName.getBytes(StandardCharsets.UTF_8).length : 0);
        }
        int i2 = length2;
        List<String> denyDomains = domainFilterRule.getDenyDomains();
        List<String> allowDomains = domainFilterRule.getAllowDomains();
        if ((allowDomains == null && denyDomains == null) || ((allowDomains == null && denyDomains.size() == 0) || (denyDomains == null && allowDomains.size() == 0))) {
            DomainFilterRule domainFilterRule2 = new DomainFilterRule(domainFilterRule.getApplication(), denyDomains, allowDomains, domainFilterRule.getDns1(), domainFilterRule.getDns2());
            domainFilterRule2.setIpcToken(iGenerateToken);
            map.put(domainFilterRule2, Integer.valueOf(i2));
            return map;
        }
        if (denyDomains != null) {
            i = i2;
            for (String str : denyDomains) {
                int length3 = str.getBytes(StandardCharsets.UTF_8).length;
                int i3 = i + length3;
                if (i3 <= MAX_LIST_SIZE_IN_BYTES) {
                    arrayList2.add(str);
                    i = i3;
                } else {
                    DomainFilterRule domainFilterRule3 = new DomainFilterRule(domainFilterRule.getApplication(), arrayList2, allowDomains != null ? arrayList3 : null, domainFilterRule.getDns1(), domainFilterRule.getDns2());
                    domainFilterRule3.setIpcToken(iGenerateToken);
                    map.put(domainFilterRule3, Integer.valueOf(i));
                    arrayList2.clear();
                    arrayList2.add(str);
                    i = i2 + length3;
                    z = true;
                }
            }
        } else {
            i = i2;
        }
        if (allowDomains != null) {
            int i4 = i;
            for (String str2 : allowDomains) {
                int length4 = str2.getBytes(StandardCharsets.UTF_8).length;
                int i5 = i4 + length4;
                if (i5 <= MAX_LIST_SIZE_IN_BYTES) {
                    arrayList3.add(str2);
                    i4 = i5;
                    arrayList = arrayList3;
                } else {
                    DomainFilterRule domainFilterRule4 = new DomainFilterRule(domainFilterRule.getApplication(), denyDomains != null ? arrayList2 : null, arrayList3, domainFilterRule.getDns1(), domainFilterRule.getDns2());
                    arrayList = arrayList3;
                    domainFilterRule4.setIpcToken(iGenerateToken);
                    map.put(domainFilterRule4, Integer.valueOf(i4));
                    arrayList2.clear();
                    arrayList.clear();
                    arrayList.add(str2);
                    i4 = i2 + length4;
                    z = true;
                }
                arrayList3 = arrayList;
            }
            i = i4;
        }
        ArrayList arrayList4 = arrayList3;
        if (arrayList4.isEmpty() && arrayList2.isEmpty()) {
            return map;
        }
        DomainFilterRule domainFilterRule5 = new DomainFilterRule(domainFilterRule.getApplication(), arrayList2, arrayList4, domainFilterRule.getDns1(), domainFilterRule.getDns2());
        if (z) {
            domainFilterRule5.setIpcToken(iGenerateToken);
        }
        map.put(domainFilterRule5, Integer.valueOf(i));
        return map;
    }

    public FirewallResponse[] removeDomainFilterRules(List<DomainFilterRule> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.removeDomainFilterRules");
        String str = TAG;
        StringBuilder sb = new StringBuilder("removeDomainFilterRules() - rules.size = ");
        sb.append(list != null ? Integer.valueOf(list.size()) : "0");
        Log.i(str, sb.toString());
        return evaluateAndProcessRules(list, -1);
    }

    public FirewallResponse[] removeRules(FirewallRule[] firewallRuleArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Firewall.removeRules");
        String str = TAG;
        StringBuilder sb = new StringBuilder("removeRules() - FirewallRule[].length = ");
        sb.append(firewallRuleArr != null ? Integer.valueOf(firewallRuleArr.length) : "0");
        Log.i(str, sb.toString());
        if (getService() == null) {
            return null;
        }
        if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 14) {
            Log.i(TAG, "Firewall.removeRules() : This device doesn't support this API.");
            return null;
        }
        try {
            return this.mService.removeRules(this.mContextInfo, firewallRuleArr);
        } catch (RemoteException unused) {
            Log.e(TAG, "removeRules() - RemoteException at removeRules method.");
            return null;
        }
    }

    public final DomainFilterRule updateLastDomainRule(DomainFilterRule domainFilterRule, List<DomainFilterRule> list) {
        DomainFilterRule domainFilterRule2 = (DomainFilterRule) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, list);
        if (!domainFilterRule2.getApplication().getPackageName().equals(domainFilterRule.getApplication().getPackageName())) {
            return null;
        }
        List<String> denyDomains = domainFilterRule.getDenyDomains();
        if (denyDomains != null && !denyDomains.isEmpty()) {
            List<String> denyDomains2 = domainFilterRule2.getDenyDomains();
            denyDomains2.addAll(denyDomains);
            domainFilterRule2.setDenyDomains(denyDomains2);
        }
        List<String> allowDomains = domainFilterRule.getAllowDomains();
        if (allowDomains != null && !allowDomains.isEmpty()) {
            List<String> allowDomains2 = domainFilterRule2.getAllowDomains();
            allowDomains2.addAll(allowDomains);
            domainFilterRule2.setAllowDomains(allowDomains2);
        }
        return domainFilterRule2;
    }

    public Firewall(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }
}
