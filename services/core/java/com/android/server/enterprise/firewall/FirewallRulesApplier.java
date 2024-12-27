package com.android.server.enterprise.firewall;

import android.content.ContentValues;
import android.content.Context;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.net.IOemNetd;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;

import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knox.net.firewall.FirewallRuleValidator;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class FirewallRulesApplier {
    public Context mContext;
    public DomainFilterApplicationChainsManager mDomainFilterAppChainsMngr;
    public EdmStorageProvider mEdmStorageProvider;
    public Firewall mFirewallService;
    public int mIcmpAllowRuleApplies;
    public INetd mNetdService;
    public IOemNetd mOemNetdService;
    public boolean mShouldAddAcceptRuleToInput;

    /* renamed from: com.android.server.enterprise.firewall.FirewallRulesApplier$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[]
                $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table;
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

        static {
            int[] iArr = new int[FirewallRule.RuleType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType = iArr;
            try {
                iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.DENY.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.REDIRECT.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                                FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[FirewallDefinitions.Table.values().length];
            $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table = iArr2;
            try {
                iArr2[0] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table[1] = 2;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public final class DomainFilterApplicationChainsManager {
        public final Map mDomainFilterChainsCache = new HashMap();
        public final Object mDomainFilterChainsCacheLock = new Object();
        public int mAppChainsCounter = 0;

        public DomainFilterApplicationChainsManager() {}

        public final void clearChainsForUserId(Integer num) {
            if (((HashMap) this.mDomainFilterChainsCache).containsKey(num)) {
                ArrayList arrayList =
                        new ArrayList(
                                (Collection) ((HashMap) this.mDomainFilterChainsCache).get(num));
                if (arrayList.isEmpty()) {
                    return;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    removeChainForApplication(null, (Integer) it.next(), num);
                }
            }
        }

        public final boolean removeChainForApplication(String str, Integer num, Integer num2) {
            if (!(((HashMap) this.mDomainFilterChainsCache).containsKey(num2)
                    && ((List) ((HashMap) this.mDomainFilterChainsCache).get(num2))
                            .contains(num))) {
                return true;
            }
            boolean z = this.mAppChainsCounter == 1;
            int intValue = num.intValue();
            int intValue2 = num2.intValue();
            ArrayList arrayList = new ArrayList();
            String domainFilterChainNameForApp =
                    IptablesCommandBuilder.getDomainFilterChainNameForApp(str, num, num2, true);
            String domainFilterChainNameForApp2 =
                    IptablesCommandBuilder.getDomainFilterChainNameForApp(str, num, num2, false);
            String domainFilterBaseChainNameForUser =
                    IptablesCommandBuilder.getDomainFilterBaseChainNameForUser(intValue2, true);
            String domainFilterBaseChainNameForUser2 =
                    IptablesCommandBuilder.getDomainFilterBaseChainNameForUser(intValue2, false);
            String appOrUserUid =
                    intValue == -1
                            ? FirewallUtils.getAppOrUserUid("*", intValue2, -1, true)
                            : ProcessList$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(" -m owner --uid-owner "), intValue);
            arrayList.add("*filter");
            if (z) {
                arrayList.add(":" + domainFilterChainNameForApp + " -");
            }
            arrayList.add(":" + domainFilterChainNameForApp2 + " -");
            if (z) {
                arrayList.add(
                        "-D "
                                + domainFilterBaseChainNameForUser
                                + " -j "
                                + domainFilterChainNameForApp);
            }
            arrayList.add(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "-D ",
                            domainFilterBaseChainNameForUser2,
                            appOrUserUid,
                            " -j ",
                            domainFilterChainNameForApp2));
            if (intValue != -1 && !"*".equals(str)) {
                arrayList.add(
                        "-D " + domainFilterBaseChainNameForUser2 + appOrUserUid + " -j RETURN ");
            }
            if (z) {
                arrayList.add("-X " + domainFilterChainNameForApp);
            }
            arrayList.add("-X " + domainFilterChainNameForApp2);
            arrayList.add("COMMIT\n");
            if (z) {
                FirewallRulesApplier.this.mShouldAddAcceptRuleToInput = true;
            }
            if (!FirewallRulesApplier.this.executeCmdIptablesV4AndV6(
                    arrayList, FirewallDefinitions.Table.FILTER)) {
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        "Failed to remove chain for ", str, "FirewallRulesApplier");
                return false;
            }
            synchronized (this.mDomainFilterChainsCacheLock) {
                try {
                    if (((HashMap) this.mDomainFilterChainsCache).containsKey(num2)) {
                        ((List) ((HashMap) this.mDomainFilterChainsCache).get(num2)).remove(num);
                        this.mAppChainsCounter--;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }

    public static List splitAndConvertCommandsList(List list, FirewallDefinitions.Table table) {
        ArrayList arrayList;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int i = 0;
        do {
            int i2 = 0;
            while (true) {
                arrayList = (ArrayList) list;
                if (i >= arrayList.size()) {
                    break;
                }
                String str = (String) arrayList.get(i);
                i2 += str.getBytes(StandardCharsets.UTF_16).length;
                if (i2 > 400000) {
                    break;
                }
                arrayList3.add(str);
                i++;
            }
            arrayList3.add(0, table == FirewallDefinitions.Table.FILTER ? "*filter" : "*nat");
            arrayList3.add("COMMIT\n");
            arrayList2.add(String.join("\n", arrayList3));
            arrayList3.clear();
        } while (i < arrayList.size());
        Log.d("FirewallRulesApplier", "total number of commands " + arrayList2.size());
        return arrayList2;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.firewall.FirewallResponse addDomainRules(
            com.samsung.android.knox.ContextInfo r20, java.util.List r21) {
        /*
            Method dump skipped, instructions count: 813
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.FirewallRulesApplier.addDomainRules(com.samsung.android.knox.ContextInfo,"
                    + " java.util.List):com.samsung.android.knox.net.firewall.FirewallResponse");
    }

    public final synchronized void addOrRemoveExemptRules(
            FirewallExemption firewallExemption, boolean z, ContextInfo contextInfo) {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add("*filter");
            arrayList.addAll(
                    IptablesCommandBuilder.createAllowOrDenyCommands(
                            firewallExemption.mExemptionRule,
                            contextInfo,
                            z ? "-A" : "-D",
                            FirewallRule.RuleType.ALLOW));
            arrayList.add("COMMIT\n");
            if ("*".equals(firewallExemption.mExemptionRule.getIpAddress())) {
                executeCmdIptablesV4AndV6(arrayList, FirewallDefinitions.Table.FILTER);
            } else {
                runShellCommand(
                        firewallExemption.mExemptionRule.getAddressType(),
                        String.join("\n", arrayList));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void addOrRemoveIcmpAllowRule(boolean z) {
        if (z) {
            int i = this.mIcmpAllowRuleApplies;
            this.mIcmpAllowRuleApplies = i + 1;
            if (i >= 1) {
                return;
            }
        }
        if (!z) {
            int i2 = this.mIcmpAllowRuleApplies;
            this.mIcmpAllowRuleApplies = i2 - 1;
            if (i2 > 1) {
                return;
            }
        }
        runShellCommand(
                4, String.join("\n", IptablesCommandBuilder.createIcmpAllowRuleCommands(4, z)));
        if (FirewallUtils.isIpv6SupportedForTable(FirewallDefinitions.Table.FILTER)) {
            runShellCommand(
                    6, String.join("\n", IptablesCommandBuilder.createIcmpAllowRuleCommands(6, z)));
        }
    }

    public final boolean blockPort53(AppIdentity appIdentity, ContextInfo contextInfo, boolean z) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Firewall.AddressType addressType = Firewall.AddressType.IPV4;
        this.mFirewallService.getClass();
        FirewallRule createDenyAllRule = Firewall.createDenyAllRule(addressType, true);
        createDenyAllRule.setPortNumber("53");
        createDenyAllRule.setApplication(
                "*".equals(appIdentity.getPackageName())
                        ? new AppIdentity("*", (String) null)
                        : appIdentity);
        Log.d(
                "Firewall",
                "createPort53DenyRule(): package = "
                        .concat(String.valueOf(createDenyAllRule.getPackageName())));
        if ("*".equals(appIdentity.getPackageName())) {
            ArrayList arrayList3 = new ArrayList();
            String str = z ? "-A" : "-D";
            FirewallRule.RuleType ruleType = FirewallRule.RuleType.ALLOW;
            FirewallRule firewallRule = new FirewallRule(ruleType, addressType);
            firewallRule.setDirection(Firewall.Direction.OUTPUT);
            firewallRule.setApplication(new AppIdentity("dns_tether", (String) null));
            Iterator it =
                    ((ArrayList)
                                    IptablesCommandBuilder.createAllowOrDenyCommands(
                                            firewallRule, contextInfo, str, ruleType))
                            .iterator();
            while (it.hasNext()) {
                arrayList3.add(
                        ((String) it.next())
                                .replaceAll("firewall_allow-output", "block_port53-output"));
            }
            arrayList.addAll(arrayList3);
        }
        List createAllowOrDenyCommands =
                IptablesCommandBuilder.createAllowOrDenyCommands(
                        createDenyAllRule, contextInfo, "-A", createDenyAllRule.getRuleType());
        ArrayList arrayList4 = new ArrayList();
        Iterator it2 = ((ArrayList) createAllowOrDenyCommands).iterator();
        while (it2.hasNext()) {
            arrayList4.add(
                    ((String) it2.next())
                            .replaceAll("firewall_deny-output", "block_port53-output"));
        }
        arrayList2.addAll(arrayList4);
        if (!z) {
            ArrayList arrayList5 = new ArrayList();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                arrayList5.add(((String) it3.next()).replaceAll("-A", "-D"));
            }
            arrayList2 = arrayList5;
        }
        arrayList.addAll(arrayList2);
        return runShellCommand(Firewall.AddressType.IPV6, String.join("\n", arrayList))
                & completeCommandAndExecute(
                        arrayList, FirewallDefinitions.Table.FILTER, Firewall.AddressType.IPV4);
    }

    public final boolean completeCommandAndExecute(
            List list, FirewallDefinitions.Table table, Firewall.AddressType addressType) {
        if (list == null) {
            Log.e("FirewallRulesApplier", "Command list is null");
            return false;
        }
        int ordinal = table.ordinal();
        if (ordinal == 0) {
            list.add(0, "*filter");
        } else if (ordinal == 1) {
            list.add(0, "*nat");
        }
        list.add("COMMIT\n");
        return runShellCommand(addressType, String.join("\n", list));
    }

    public final boolean createIptablesChains(Integer num) {
        Context context;
        ArrayList<Integer> arrayList = new ArrayList();
        if (num == null && (context = this.mContext) != null) {
            arrayList = FirewallUtils.getAllUsers(context);
        } else if (num != null) {
            arrayList.add(num);
        }
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m("*filter");
        for (Integer num2 : arrayList) {
            String num3 = num2.intValue() == 0 ? "" : num2.toString();
            if ("".equals(num3)) {
                m.add(":domain_filter-input -");
                m.add(":firewall_allow-input -");
                m.add(":firewall_deny-input -");
                m.add(":firewall_allow-forward -");
                m.add(":firewall_deny-forward -");
            }
            m.add(":domain_filter-output" + num3 + " -");
            m.add(":firewall_allow-output" + num3 + " -");
            m.add(":firewall_deny-output" + num3 + " -");
            m.add(":block_port53-output" + num3 + " -");
            StringBuilder sb = new StringBuilder("-A OUTPUT -j domain_filter-output");
            sb.append(num3);
            m.add(sb.toString());
            m.add("-A OUTPUT -j firewall_allow-output" + num3);
            m.add("-A OUTPUT -j firewall_deny-output" + num3);
            m.add("-A OUTPUT -j block_port53-output" + num3);
            if ("".equals(num3)) {
                m.add("-A INPUT -j domain_filter-input");
                m.add("-A INPUT -j firewall_allow-input");
                m.add("-A INPUT -j firewall_deny-input");
                m.add("-A FORWARD -j firewall_allow-forward");
                m.add("-A FORWARD -j firewall_deny-forward");
            }
        }
        m.add("COMMIT\n");
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("*nat");
        for (Integer num4 : arrayList) {
            String num5 = num4.intValue() == 0 ? "" : num4.toString();
            arrayList2.add(":firewall_exceptions-output" + num5 + " -");
            arrayList2.add(":firewall_redirect-output" + num5 + " -");
            StringBuilder sb2 = new StringBuilder("-A OUTPUT -j firewall_exceptions-output");
            sb2.append(num5);
            arrayList2.add(sb2.toString());
            arrayList2.add("-A OUTPUT -j firewall_redirect-output" + num5);
        }
        arrayList2.add("COMMIT\n");
        return mergeAndExecuteCmdIptablesV4AndV6(m, arrayList2);
    }

    public final FirewallResponse disableIpTablesRule(
            FirewallRule firewallRule, ContextInfo contextInfo, boolean z) {
        FirewallResponse validateFirewallRule =
                FirewallRuleValidator.validateFirewallRule(firewallRule);
        if (validateFirewallRule.getResult().equals(FirewallResponse.Result.FAILED)) {
            return validateFirewallRule;
        }
        boolean completeCommandAndExecute =
                completeCommandAndExecute(
                        IptablesCommandBuilder.getIptablesCommand(firewallRule, contextInfo, "-D"),
                        FirewallUtils.getTableByRuletype(firewallRule.getRuleType()),
                        firewallRule.getAddressType());
        if (z && completeCommandAndExecute) {
            updateStatusOnDB(firewallRule, FirewallRule.Status.DISABLED, contextInfo);
        }
        return new FirewallResponse(
                FirewallResponse.Result.SUCCESS,
                FirewallResponse.ErrorCode.NO_ERROR,
                "The rule was successfully disabled.");
    }

    public final FirewallResponse[] enableOrDisableIptablesRule(
            FirewallRule[] firewallRuleArr,
            boolean z,
            ContextInfo contextInfo,
            boolean z2,
            boolean z3) {
        boolean z4;
        int i = contextInfo.mCallerUid;
        int length = firewallRuleArr.length;
        FirewallResponse[] firewallResponseArr = new FirewallResponse[length];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        BitSet bitSet = new BitSet();
        for (int i2 = 0; i2 < length; i2++) {
            FirewallRule firewallRule = firewallRuleArr[i2];
            if (firewallRule == null) {
                firewallResponseArr[i2] =
                        new FirewallResponse(
                                FirewallResponse.Result.FAILED,
                                FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                                "Rule is null.");
            } else if (z3) {
                FirewallResponse validateFirewallRule =
                        FirewallRuleValidator.validateFirewallRule(firewallRule);
                FirewallResponse.Result result = validateFirewallRule.getResult();
                FirewallResponse.Result result2 = FirewallResponse.Result.FAILED;
                if (result.equals(result2)) {
                    firewallResponseArr[i2] = validateFirewallRule;
                } else if (z
                        || !z2
                        || !FirewallUtils.isRuleEnabled(
                                firewallRuleArr[i2], i, this.mEdmStorageProvider)
                        || i == 1000) {
                    Firewall.AddressType addressType = firewallRuleArr[i2].getAddressType();
                    FirewallDefinitions.Table tableByRuletype =
                            FirewallUtils.getTableByRuletype(firewallRuleArr[i2].getRuleType());
                    if (!Firewall.AddressType.IPV6.equals(addressType)) {
                        int ordinal = tableByRuletype.ordinal();
                        if (ordinal == 0) {
                            arrayList.addAll(
                                    IptablesCommandBuilder.getIptablesCommand(
                                            firewallRuleArr[i2], contextInfo, "-A"));
                        } else if (ordinal == 1) {
                            arrayList3.addAll(
                                    IptablesCommandBuilder.getIptablesCommand(
                                            firewallRuleArr[i2], contextInfo, "-A"));
                        }
                    } else if (FirewallUtils.isIpv6SupportedForTable(tableByRuletype)) {
                        int ordinal2 = tableByRuletype.ordinal();
                        if (ordinal2 == 0) {
                            arrayList2.addAll(
                                    IptablesCommandBuilder.getIptablesCommand(
                                            firewallRuleArr[i2], contextInfo, "-A"));
                        } else if (ordinal2 == 1) {
                            arrayList4.addAll(
                                    IptablesCommandBuilder.getIptablesCommand(
                                            firewallRuleArr[i2], contextInfo, "-A"));
                        }
                    } else {
                        firewallResponseArr[i2] =
                                new FirewallResponse(
                                        result2,
                                        FirewallResponse.ErrorCode.IPV6_NOT_SUPPORTED_ERROR,
                                        "This device does not have IPv6 support for this type of"
                                            + " rule.");
                    }
                    bitSet.set(i2);
                } else {
                    firewallResponseArr[i2] =
                            new FirewallResponse(
                                    result2,
                                    FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                                    "The specified rule is already enabled.");
                }
            } else {
                firewallResponseArr[i2] = disableIpTablesRule(firewallRule, contextInfo, true);
            }
        }
        boolean isEmpty = arrayList.isEmpty();
        FirewallDefinitions.Table table = FirewallDefinitions.Table.FILTER;
        if (isEmpty) {
            z4 = true;
        } else {
            Iterator it = ((ArrayList) splitAndConvertCommandsList(arrayList, table)).iterator();
            z4 = true;
            while (it.hasNext()) {
                z4 &= runShellCommand(4, (String) it.next());
            }
        }
        if (!arrayList2.isEmpty()) {
            Iterator it2 = ((ArrayList) splitAndConvertCommandsList(arrayList2, table)).iterator();
            while (it2.hasNext()) {
                z4 &= runShellCommand(6, (String) it2.next());
            }
        }
        boolean isEmpty2 = arrayList3.isEmpty();
        FirewallDefinitions.Table table2 = FirewallDefinitions.Table.NAT;
        if (!isEmpty2) {
            Iterator it3 = ((ArrayList) splitAndConvertCommandsList(arrayList3, table2)).iterator();
            while (it3.hasNext()) {
                z4 &= runShellCommand(4, (String) it3.next());
            }
        }
        if (!arrayList4.isEmpty()) {
            Iterator it4 = ((ArrayList) splitAndConvertCommandsList(arrayList4, table2)).iterator();
            while (it4.hasNext()) {
                z4 &= runShellCommand(6, (String) it4.next());
            }
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (bitSet.get(i3)) {
                if (!z2) {
                    firewallResponseArr[i3] =
                            new FirewallResponse(
                                    FirewallResponse.Result.SUCCESS,
                                    FirewallResponse.ErrorCode.NO_ERROR,
                                    "The rule was successfully enabled.");
                } else if (z4) {
                    FirewallRule firewallRule2 = firewallRuleArr[i3];
                    FirewallRule.Status status = FirewallRule.Status.ENABLED;
                    updateStatusOnDB(firewallRule2, status, contextInfo);
                    firewallRuleArr[i3].setStatus(status);
                    if ((FirewallRule.RuleType.ALLOW == firewallRuleArr[i3].getRuleType()
                                    || FirewallRule.RuleType.DENY
                                            == firewallRuleArr[i3].getRuleType())
                            && Firewall.Direction.ALL.equals(firewallRuleArr[i3].getDirection())) {
                        firewallResponseArr[i3] =
                                new FirewallResponse(
                                        FirewallResponse.Result.SUCCESS,
                                        FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR,
                                        "Rule not applied to INPUT chain for Direction.All.");
                    } else {
                        firewallResponseArr[i3] =
                                new FirewallResponse(
                                        FirewallResponse.Result.SUCCESS,
                                        FirewallResponse.ErrorCode.NO_ERROR,
                                        "The rule was successfully enabled.");
                    }
                } else {
                    disableIpTablesRule(firewallRuleArr[i3], contextInfo, true);
                    firewallResponseArr[i3] =
                            new FirewallResponse(
                                    FirewallResponse.Result.FAILED,
                                    FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                                    "Failed to enable rule.");
                }
            }
        }
        return firewallResponseArr;
    }

    public final boolean executeCmdIptablesV4AndV6(List list, FirewallDefinitions.Table table) {
        return runShellCommand(
                FirewallUtils.isIpv6SupportedForTable(table) ? 46 : 4, String.join("\n", list));
    }

    public final boolean flushAllChains(Integer num) {
        ArrayList arrayList = new ArrayList();
        String str = "";
        String num2 = (num == null || num.intValue() == 0) ? "" : num.toString();
        arrayList.add("*filter");
        arrayList.add(":firewall_allow-input -");
        arrayList.add(":firewall_allow-output" + num2 + " -");
        arrayList.add(":firewall_allow-forward -");
        arrayList.add(":firewall_deny-input -");
        arrayList.add(":firewall_deny-output" + num2 + " -");
        arrayList.add(":firewall_deny-forward -");
        arrayList.add("COMMIT\n");
        ArrayList arrayList2 = new ArrayList();
        if (num != null && num.intValue() != 0) {
            str = num.toString();
        }
        arrayList2.add("*nat");
        arrayList2.add(":firewall_exceptions-output" + str + " -");
        arrayList2.add(":firewall_redirect-output" + str + " -");
        arrayList2.add("COMMIT\n");
        return mergeAndExecuteCmdIptablesV4AndV6(arrayList, arrayList2);
    }

    public final boolean flushChain(FirewallRule.RuleType ruleType, Integer num) {
        ArrayList arrayList = new ArrayList();
        String num2 = num.intValue() == 0 ? "" : num.toString();
        int i =
                IptablesCommandBuilder.AnonymousClass1
                        .$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                        ruleType.ordinal()];
        if (i == 1) {
            arrayList.add("*filter");
            arrayList.add(":firewall_allow-input -");
            arrayList.add(":firewall_allow-output" + num2 + " -");
            arrayList.add(":firewall_allow-forward -");
        } else if (i == 2) {
            arrayList.add("*filter");
            arrayList.add(":firewall_deny-input -");
            arrayList.add(":firewall_deny-output" + num2 + " -");
            arrayList.add(":firewall_deny-forward -");
        } else if (i == 3) {
            arrayList.add("*nat");
            arrayList.add(":firewall_redirect-output" + num2 + " -");
        } else if (i == 4) {
            arrayList.add("*nat");
            arrayList.add(":firewall_exceptions-output" + num2 + " -");
        }
        arrayList.add("COMMIT\n");
        return executeCmdIptablesV4AndV6(arrayList, FirewallUtils.getTableByRuletype(ruleType));
    }

    public final boolean flushDnsPortChains(Integer num) {
        this.mDomainFilterAppChainsMngr.clearChainsForUserId(num);
        ArrayList arrayList = new ArrayList();
        int intValue = num.intValue();
        arrayList.add(
                ":"
                        + ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "block_port53-output",
                                intValue == 0 ? "" : Integer.toString(intValue))
                        + " -");
        return runShellCommand(Firewall.AddressType.IPV6, String.join("\n", arrayList))
                & completeCommandAndExecute(
                        arrayList, FirewallDefinitions.Table.FILTER, Firewall.AddressType.IPV4);
    }

    public final boolean flushDomainChains(Integer num, boolean z) {
        DomainFilterApplicationChainsManager domainFilterApplicationChainsManager =
                this.mDomainFilterAppChainsMngr;
        domainFilterApplicationChainsManager.clearChainsForUserId(num);
        boolean z2 = domainFilterApplicationChainsManager.mAppChainsCounter == 0;
        ArrayList arrayList = new ArrayList();
        String domainFilterBaseChainNameForUser =
                IptablesCommandBuilder.getDomainFilterBaseChainNameForUser(num.intValue(), true);
        String domainFilterBaseChainNameForUser2 =
                IptablesCommandBuilder.getDomainFilterBaseChainNameForUser(num.intValue(), false);
        if (z2) {
            arrayList.add(":" + domainFilterBaseChainNameForUser + " -");
        }
        arrayList.add(":" + domainFilterBaseChainNameForUser2 + " -");
        if (num.intValue() != 0 && z) {
            if (z2) {
                arrayList.add("-D INPUT -j " + domainFilterBaseChainNameForUser);
            }
            arrayList.add("-D OUTPUT -j " + domainFilterBaseChainNameForUser2);
            if (z2) {
                arrayList.add("-X " + domainFilterBaseChainNameForUser);
            }
            arrayList.add("-X " + domainFilterBaseChainNameForUser2);
        }
        if (z2) {
            this.mShouldAddAcceptRuleToInput = true;
        }
        return runShellCommand(Firewall.AddressType.IPV6, String.join("\n", arrayList))
                & completeCommandAndExecute(
                        arrayList, FirewallDefinitions.Table.FILTER, Firewall.AddressType.IPV4);
    }

    public final ArrayList getAllAdmins() {
        ArrayList arrayList = new ArrayList();
        Iterator it =
                ((ArrayList)
                                this.mEdmStorageProvider.getValues(
                                        "ADMIN", new String[] {"adminUid"}, new ContentValues()))
                        .iterator();
        while (it.hasNext()) {
            arrayList.add(((ContentValues) it.next()).getAsInteger("adminUid"));
        }
        return arrayList;
    }

    public final IOemNetd getOemNetdService() {
        boolean z;
        INetd asInterface;
        IOemNetd iOemNetd = this.mOemNetdService;
        if (iOemNetd != null) {
            return iOemNetd;
        }
        if (this.mNetdService == null) {
            try {
                asInterface = INetd.Stub.asInterface(ServiceManager.getService("netd"));
                this.mNetdService = asInterface;
            } catch (RemoteException unused) {
                this.mNetdService = null;
                z = false;
            }
            if (asInterface != null) {
                z = asInterface.isAlive();
                if (!z) {
                    Log.e("FirewallRulesApplier", "Can't connect to NativeNetdService netd");
                }
            }
        }
        INetd iNetd = this.mNetdService;
        if (iNetd != null) {
            try {
                this.mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(
                        e,
                        new StringBuilder("Failed to get OemNetd listener "),
                        "FirewallRulesApplier");
            }
        }
        return this.mOemNetdService;
    }

    public final boolean hasDenyRuleInDatabase(int i, String str) {
        ContentValues contentValues = new ContentValues();
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(
                i, contentValues, "adminUid", "packageName", str);
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        ArrayList arrayList =
                (ArrayList) edmStorageProvider.getValues("DomainFilterTable", null, contentValues);
        if (arrayList.isEmpty() || arrayList.get(0) == null) {
            Log.d("FirewallRulesApplier", "hasDenyRuleInDatabase(): false");
            return false;
        }
        if (!((ArrayList)
                        FirewallUtils.getListFromDb(
                                (ContentValues) arrayList.get(0), "blacklist", edmStorageProvider))
                .isEmpty()) {
            return true;
        }
        Log.d("FirewallRulesApplier", "hasDenyRuleInDatabase(): false");
        return false;
    }

    public final boolean mergeAndExecuteCmdIptablesV4AndV6(List list, List list2) {
        String join = String.join("\n", list);
        String join2 = String.join("\n", list2);
        boolean runShellCommand = runShellCommand(4, join + join2);
        StringBuilder sb = new StringBuilder();
        if (FirewallUtils.isIpv6SupportedForTable(FirewallDefinitions.Table.FILTER)) {
            sb.append(join);
        }
        if (FirewallUtils.isIpv6SupportedForTable(FirewallDefinitions.Table.NAT)) {
            sb.append(join2);
        }
        return !sb.toString().isEmpty()
                ? runShellCommand & runShellCommand(6, sb.toString())
                : runShellCommand;
    }

    public final void reloadDomainFilterOnIptablesRules() {
        Iterator it = getAllAdmins().iterator();
        boolean z = false;
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            ContextInfo contextInfo = new ContextInfo(intValue);
            Firewall firewall = this.mFirewallService;
            List<DomainFilterRule> domainFilterRules =
                    firewall.getDomainFilterRules(contextInfo, null, -1);
            if (firewall.isDomainFilterOnIptablesEnabled(contextInfo)) {
                addDomainRules(contextInfo, domainFilterRules);
                z = true;
            }
            ArrayList arrayList = new ArrayList();
            for (DomainFilterRule domainFilterRule : domainFilterRules) {
                String packageName = domainFilterRule.getApplication().getPackageName();
                String signature = domainFilterRule.getApplication().getSignature();
                if (hasDenyRuleInDatabase(intValue, packageName)
                        && !arrayList.contains(packageName)) {
                    arrayList.add(packageName);
                    if (blockPort53(new AppIdentity(packageName, signature), contextInfo, true)) {
                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                "blockPort53() - port53 rule added successfully. Package: ",
                                packageName,
                                "FirewallRulesApplier");
                    }
                }
            }
            arrayList.clear();
        }
        if (z) {
            DomainFilterNapCommon.getInstance(this.mContext).setDomainFilterOnIptablesEnabled(true);
        }
    }

    public final void reloadIptablesRules() {
        Iterator it = getAllAdmins().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (intValue != 1000) {
                ContextInfo contextInfo = new ContextInfo(intValue);
                Firewall firewall = this.mFirewallService;
                FirewallRule[] rules = firewall.getRules(contextInfo, 15, null);
                for (FirewallRule firewallRule : rules) {
                    if (FirewallUtils.verifyPackageName(
                            UserHandle.getUserId(contextInfo.mCallerUid),
                            firewallRule.getApplication().getPackageName())) {
                        if (!FirewallRule.Status.DISABLED.equals(firewallRule.getStatus())) {
                            FirewallResponse firewallResponse =
                                    enableOrDisableIptablesRule(
                                            new FirewallRule[] {firewallRule},
                                            true,
                                            contextInfo,
                                            true,
                                            true)[0];
                        }
                        if (Firewall.shouldApplyIcmpAllowRule(firewallRule)) {
                            addOrRemoveIcmpAllowRule(true);
                        }
                    } else {
                        Log.i(
                                "FirewallRulesApplier",
                                "reloadIptablesRules() - Package not installed");
                    }
                }
                if (Firewall.shouldApplyExemptRules(rules)) {
                    firewall.applyExemptRules(
                            contextInfo, UserHandle.getUserId(contextInfo.mCallerUid));
                }
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.knox.net.firewall.FirewallResponse removeDomainRules(
            com.samsung.android.knox.ContextInfo r10, java.util.List r11, java.util.List r12) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.firewall.FirewallRulesApplier.removeDomainRules(com.samsung.android.knox.ContextInfo,"
                    + " java.util.List,"
                    + " java.util.List):com.samsung.android.knox.net.firewall.FirewallResponse");
    }

    public final void removeIptablesChains(Integer num) {
        ArrayList m = PortStatus_1_1$$ExternalSyntheticOutline0.m("*filter");
        if (num.intValue() == 0) {
            m.add("-D INPUT -j firewall_allow-input");
            m.add("-D INPUT -j firewall_deny-input");
            m.add("-D FORWARD -j firewall_allow-forward");
            m.add("-D FORWARD -j firewall_deny-forward");
        }
        m.add("-D OUTPUT -j firewall_allow-output" + num);
        m.add("-D OUTPUT -j firewall_deny-output" + num);
        if (num.intValue() == 0) {
            m.add(":firewall_allow-input -");
            m.add(":firewall_deny-input -");
            m.add(":firewall_allow-forward -");
            m.add(":firewall_deny-forward -");
        }
        m.add(":firewall_allow-output" + num + " -");
        m.add(":firewall_deny-output" + num + " -");
        if (num.intValue() == 0) {
            m.add("-X firewall_allow-input");
            m.add("-X firewall_deny-input");
            m.add("-X firewall_allow-forward");
            m.add("-X firewall_deny-forward");
        }
        m.add("-X firewall_allow-output" + num);
        m.add("-X firewall_deny-output" + num);
        m.add("COMMIT\n");
        ArrayList arrayList = new ArrayList();
        arrayList.add("*nat");
        arrayList.add("-D OUTPUT -j firewall_exceptions-output" + num);
        arrayList.add("-D OUTPUT -j firewall_redirect-output" + num);
        arrayList.add(":firewall_exceptions-output" + num + " -");
        arrayList.add(":firewall_redirect-output" + num + " -");
        StringBuilder sb = new StringBuilder("-X firewall_exceptions-output");
        sb.append(num);
        arrayList.add(sb.toString());
        arrayList.add("-X firewall_redirect-output" + num);
        arrayList.add("COMMIT\n");
        mergeAndExecuteCmdIptablesV4AndV6(m, arrayList);
    }

    public final synchronized boolean runShellCommand(int i, String str) {
        String runKnoxFirewallRulesCommand;
        String str2;
        Log.d("FirewallRulesApplier", "runShellCommand - target " + i);
        IOemNetd oemNetdService = getOemNetdService();
        this.mOemNetdService = oemNetdService;
        if (oemNetdService == null) {
            Log.e("FirewallRulesApplier", "Failed to obtain instance of mOemNetdService");
            return false;
        }
        try {
            runKnoxFirewallRulesCommand = oemNetdService.runKnoxFirewallRulesCommand(i, str);
        } catch (Exception e) {
            Log.e("FirewallRulesApplier", "Failed to run cmd: " + e.getMessage());
        }
        if (runKnoxFirewallRulesCommand != null && !runKnoxFirewallRulesCommand.isEmpty()) {
            str2 = runKnoxFirewallRulesCommand;
            Log.d("FirewallRulesApplier", "Run cmd: ".concat(str2));
            if (runKnoxFirewallRulesCommand != null && !runKnoxFirewallRulesCommand.isEmpty()) {
                Log.e(
                        "FirewallRulesApplier",
                        "Failed to run command. Result="
                                + runKnoxFirewallRulesCommand
                                + "\ncommand="
                                + str);
                return false;
            }
            return true;
        }
        str2 = "OK";
        Log.d("FirewallRulesApplier", "Run cmd: ".concat(str2));
        if (runKnoxFirewallRulesCommand != null) {
            Log.e(
                    "FirewallRulesApplier",
                    "Failed to run command. Result="
                            + runKnoxFirewallRulesCommand
                            + "\ncommand="
                            + str);
            return false;
        }
        return true;
    }

    public final synchronized boolean runShellCommand(
            Firewall.AddressType addressType, String str) {
        return runShellCommand(Firewall.AddressType.IPV4.equals(addressType) ? 4 : 6, str);
    }

    public final synchronized String runShellCommandWithOutput(int i, String str) {
        String str2;
        str2 = "";
        Log.d("FirewallRulesApplier", "runShellCommandWithOutput - target " + i);
        IOemNetd oemNetdService = getOemNetdService();
        this.mOemNetdService = oemNetdService;
        if (oemNetdService != null) {
            try {
                str2 = oemNetdService.runKnoxFirewallRulesCommand(i, str);
            } catch (Exception e) {
                Log.e("FirewallRulesApplier", "Failed to run cmd: " + e.getMessage());
            }
        }
        return str2;
    }

    public final void setRulesStatusAfterReboot() {
        Iterator it = getAllAdmins().iterator();
        while (it.hasNext()) {
            ContextInfo contextInfo = new ContextInfo(((Integer) it.next()).intValue());
            Firewall firewall = this.mFirewallService;
            boolean isFirewallEnabled = firewall.isFirewallEnabled(contextInfo);
            for (FirewallRule firewallRule : firewall.getRules(contextInfo, 15, null)) {
                if (isFirewallEnabled) {
                    updateStatusOnDB(firewallRule, FirewallRule.Status.PENDING, contextInfo);
                } else {
                    updateStatusOnDB(firewallRule, FirewallRule.Status.DISABLED, contextInfo);
                }
            }
        }
    }

    public final void updateStatusOnDB(
            FirewallRule firewallRule, FirewallRule.Status status, ContextInfo contextInfo) {
        ContentValues contentValues = new ContentValues();
        int i = contextInfo.mCallerUid;
        if (i != 1000) {
            contentValues.put("adminUid", Integer.valueOf(i));
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(Constants.JSON_CLIENT_DATA_STATUS, status.toString());
        contentValues.put("ruleType", firewallRule.getRuleType().name());
        int i2 =
                AnonymousClass1
                        .$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[
                        firewallRule.getRuleType().ordinal()];
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        if (i2 == 1) {
            contentValues.put("ipAddress", firewallRule.getIpAddress());
            contentValues.put("packageName", firewallRule.getApplication().getPackageName());
            if (firewallRule.getApplication().getSignature() != null) {
                contentValues.put("signature", firewallRule.getApplication().getSignature());
            }
            contentValues.put("portNumber", firewallRule.getPortNumber());
            contentValues.put("portLocation", firewallRule.getPortLocation().name());
            contentValues.put("direction", firewallRule.getDirection().name());
            contentValues.put("networkInterface", firewallRule.getNetworkInterface().name());
            contentValues.put("protocol", firewallRule.getProtocol().name());
            contentValues.put("addressType", firewallRule.getAddressType().name());
            if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
                contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
            }
            edmStorageProvider.update("FirewallRule", contentValues2, contentValues);
            return;
        }
        if (i2 == 2) {
            contentValues.put("ipAddress", firewallRule.getIpAddress());
            contentValues.put("packageName", firewallRule.getApplication().getPackageName());
            if (firewallRule.getApplication().getSignature() != null) {
                contentValues.put("signature", firewallRule.getApplication().getSignature());
            }
            contentValues.put("portNumber", firewallRule.getPortNumber());
            contentValues.put("portLocation", firewallRule.getPortLocation().name());
            contentValues.put("direction", firewallRule.getDirection().name());
            contentValues.put("networkInterface", firewallRule.getNetworkInterface().name());
            contentValues.put("protocol", firewallRule.getProtocol().name());
            contentValues.put("addressType", firewallRule.getAddressType().name());
            if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
                contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
            }
            edmStorageProvider.update("FirewallRule", contentValues2, contentValues);
            return;
        }
        if (i2 != 3) {
            if (i2 != 4) {
                return;
            }
            contentValues.put("ipAddress", firewallRule.getIpAddress());
            contentValues.put("packageName", firewallRule.getApplication().getPackageName());
            if (firewallRule.getApplication().getSignature() != null) {
                contentValues.put("signature", firewallRule.getApplication().getSignature());
            }
            contentValues.put("portNumber", firewallRule.getPortNumber());
            contentValues.put("protocol", firewallRule.getProtocol().name());
            contentValues.put("addressType", firewallRule.getAddressType().name());
            if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
                contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
            }
            edmStorageProvider.update("FirewallRule", contentValues2, contentValues);
            return;
        }
        contentValues.put("packageName", firewallRule.getApplication().getPackageName());
        if (firewallRule.getApplication().getSignature() != null) {
            contentValues.put("signature", firewallRule.getApplication().getSignature());
        }
        contentValues.put("ipAddress", firewallRule.getIpAddress());
        contentValues.put("portNumber", firewallRule.getPortNumber());
        contentValues.put("targetIpAddress", firewallRule.getTargetIpAddress());
        contentValues.put("targetPortNumber", firewallRule.getTargetPortNumber());
        contentValues.put("networkInterface", firewallRule.getNetworkInterface().name());
        contentValues.put("protocol", firewallRule.getProtocol().name());
        contentValues.put("addressType", firewallRule.getAddressType().name());
        if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
            contentValues.put("networkInterfaceStr", firewallRule.getStrNetworkInterface());
        }
        edmStorageProvider.update("FirewallRule", contentValues2, contentValues);
    }
}
