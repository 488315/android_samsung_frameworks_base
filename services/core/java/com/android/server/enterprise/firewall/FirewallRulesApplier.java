package com.android.server.enterprise.firewall;

import android.content.ContentValues;
import android.content.Context;
import android.net.INetd;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.net.IOemNetd;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallResponse;
import com.samsung.android.knox.net.firewall.FirewallRule;
import com.samsung.android.knox.net.firewall.FirewallRuleValidator;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class FirewallRulesApplier {
  public Context mContext;
  public EdmStorageProvider mEdmStorageProvider;
  public Firewall mFirewallService;
  public boolean mIsExemptRulesApplied;
  public boolean mIsKGExemptRuleApplied;
  public INetd mNetdService;
  public IOemNetd mOemNetdService;
  public boolean mShouldAddAcceptRuleToInput = true;
  public DomainFilterApplicationChainsManager mDomainFilterAppChainsMngr =
      new DomainFilterApplicationChainsManager();
  public int mIcmpAllowRuleApplies = 0;

  public FirewallRulesApplier(Context context, Firewall firewall) {
    this.mContext = context;
    this.mFirewallService = firewall;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
  }

  public final void connectNativeNetdService() {
    boolean z;
    INetd asInterface;
    try {
      asInterface =
          INetd.Stub.asInterface(
              ServiceManager.getService(KnoxVpnFirewallHelper.NETD_SERVICE_NAME));
      this.mNetdService = asInterface;
    } catch (RemoteException unused) {
      this.mNetdService = null;
      z = false;
    }
    if (asInterface == null) {
      return;
    }
    z = asInterface.isAlive();
    if (z) {
      return;
    }
    Log.e("FirewallRulesApplier", "Can't connect to NativeNetdService netd");
  }

  public FirewallResponse[] changeRuleStatus(
      FirewallRule[] firewallRuleArr, ContextInfo contextInfo, boolean z) {
    if (firewallRuleArr == null) {
      return null;
    }
    return enableOrDisableIptablesRule(firewallRuleArr, false, contextInfo, z);
  }

  public final FirewallResponse enableIptablesRule(
      FirewallRule firewallRule, boolean z, ContextInfo contextInfo) {
    return enableIptablesRule(firewallRule, z, contextInfo, true);
  }

  public FirewallResponse enableIptablesRule(
      FirewallRule firewallRule, boolean z, ContextInfo contextInfo, boolean z2) {
    return enableOrDisableIptablesRule(new FirewallRule[] {firewallRule}, z, contextInfo, z2, true)[
        0];
  }

  public final FirewallResponse[] enableOrDisableIptablesRule(
      FirewallRule[] firewallRuleArr, boolean z, ContextInfo contextInfo, boolean z2) {
    return enableOrDisableIptablesRule(firewallRuleArr, z, contextInfo, true, z2);
  }

  public FirewallResponse[] enableOrDisableIptablesRule(
      FirewallRule[] firewallRuleArr, boolean z, ContextInfo contextInfo, boolean z2, boolean z3) {
    boolean z4;
    int i = contextInfo.mCallerUid;
    int length = firewallRuleArr.length;
    FirewallResponse[] firewallResponseArr = new FirewallResponse[length];
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    BitSet bitSet = new BitSet();
    int i2 = 0;
    while (true) {
      z4 = true;
      if (i2 >= length) {
        break;
      }
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
        if (validateFirewallRule.getResult().equals(FirewallResponse.Result.FAILED)) {
          firewallResponseArr[i2] = validateFirewallRule;
        } else if (!z
            && z2
            && FirewallUtils.isRuleEnabled(firewallRuleArr[i2], i, this.mEdmStorageProvider)
            && i != 1000) {
          firewallResponseArr[i2] =
              new FirewallResponse(
                  FirewallResponse.Result.FAILED,
                  FirewallResponse.ErrorCode.OPERATION_NOT_PERMITTED_ERROR,
                  "The specified rule is already enabled.");
        } else {
          Firewall.AddressType addressType = firewallRuleArr[i2].getAddressType();
          FirewallDefinitions.Table tableByRuletype =
              FirewallUtils.getTableByRuletype(firewallRuleArr[i2].getRuleType());
          if (Firewall.AddressType.IPV6.equals(addressType)) {
            if (!FirewallUtils.isIpv6SupportedForTable(tableByRuletype)) {
              firewallResponseArr[i2] =
                  new FirewallResponse(
                      FirewallResponse.Result.FAILED,
                      FirewallResponse.ErrorCode.IPV6_NOT_SUPPORTED_ERROR,
                      "This device does not have IPv6 support for this type of rule.");
            } else {
              int i3 = AbstractC14331.f1689xb736b260[tableByRuletype.ordinal()];
              if (i3 == 1) {
                arrayList2.addAll(
                    IptablesCommandBuilder.getIptablesCommand(
                        firewallRuleArr[i2], contextInfo, "-A"));
              } else if (i3 == 2) {
                arrayList4.addAll(
                    IptablesCommandBuilder.getIptablesCommand(
                        firewallRuleArr[i2], contextInfo, "-A"));
              }
            }
          } else {
            int i4 = AbstractC14331.f1689xb736b260[tableByRuletype.ordinal()];
            if (i4 == 1) {
              arrayList.addAll(
                  IptablesCommandBuilder.getIptablesCommand(
                      firewallRuleArr[i2], contextInfo, "-A"));
            } else if (i4 == 2) {
              arrayList3.addAll(
                  IptablesCommandBuilder.getIptablesCommand(
                      firewallRuleArr[i2], contextInfo, "-A"));
            }
          }
          bitSet.set(i2);
        }
      } else {
        firewallResponseArr[i2] = disableIpTablesRule(firewallRule, contextInfo);
      }
      i2++;
    }
    if (!arrayList.isEmpty()) {
      Iterator it =
          splitAndConvertCommandsList(arrayList, FirewallDefinitions.Table.FILTER).iterator();
      while (it.hasNext()) {
        z4 &= runShellCommand(4, (String) it.next());
      }
    }
    if (!arrayList2.isEmpty()) {
      Iterator it2 =
          splitAndConvertCommandsList(arrayList2, FirewallDefinitions.Table.FILTER).iterator();
      while (it2.hasNext()) {
        z4 &= runShellCommand(6, (String) it2.next());
      }
    }
    if (!arrayList3.isEmpty()) {
      Iterator it3 =
          splitAndConvertCommandsList(arrayList3, FirewallDefinitions.Table.NAT).iterator();
      while (it3.hasNext()) {
        z4 &= runShellCommand(4, (String) it3.next());
      }
    }
    if (!arrayList4.isEmpty()) {
      Iterator it4 =
          splitAndConvertCommandsList(arrayList4, FirewallDefinitions.Table.NAT).iterator();
      while (it4.hasNext()) {
        z4 &= runShellCommand(6, (String) it4.next());
      }
    }
    for (int i5 = 0; i5 < length; i5++) {
      if (bitSet.get(i5)) {
        if (!z2) {
          firewallResponseArr[i5] =
              new FirewallResponse(
                  FirewallResponse.Result.SUCCESS,
                  FirewallResponse.ErrorCode.NO_ERROR,
                  "The rule was successfully enabled.");
        } else if (z4) {
          updateStatusOnDB(firewallRuleArr[i5], FirewallRule.Status.ENABLED, contextInfo);
          firewallRuleArr[i5].setStatus(FirewallRule.Status.ENABLED);
          if ((FirewallRule.RuleType.ALLOW == firewallRuleArr[i5].getRuleType()
                  || FirewallRule.RuleType.DENY == firewallRuleArr[i5].getRuleType())
              && Firewall.Direction.ALL.equals(firewallRuleArr[i5].getDirection())) {
            firewallResponseArr[i5] =
                new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.INPUT_CHAIN_NOT_SUPPORTED_ERROR,
                    "Rule not applied to INPUT chain for Direction.All.");
          } else {
            firewallResponseArr[i5] =
                new FirewallResponse(
                    FirewallResponse.Result.SUCCESS,
                    FirewallResponse.ErrorCode.NO_ERROR,
                    "The rule was successfully enabled.");
          }
        } else {
          disableIpTablesRule(firewallRuleArr[i5], contextInfo);
          firewallResponseArr[i5] =
              new FirewallResponse(
                  FirewallResponse.Result.FAILED,
                  FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
                  "Failed to enable rule.");
        }
      }
    }
    return firewallResponseArr;
  }

  public final FirewallResponse disableIpTablesRule(
      FirewallRule firewallRule, ContextInfo contextInfo) {
    return disableIpTablesRule(firewallRule, contextInfo, true);
  }

  public FirewallResponse disableIpTablesRule(
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

  public FirewallResponse addDomainRules(List list, ContextInfo contextInfo) {
    boolean z;
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (true) {
      if (!it.hasNext()) {
        break;
      }
      DomainFilterRule domainFilterRule = (DomainFilterRule) it.next();
      String packageName = domainFilterRule.getApplication().getPackageName();
      Integer valueOf = Integer.valueOf(UserHandle.getUserId(contextInfo.mCallerUid));
      int i = -1;
      if (!"*".equals(packageName)
          && (i = FirewallUtils.getUidForApplication(packageName, valueOf.intValue())) == null) {
        Log.e("FirewallRulesApplier", "failed to get uid for " + packageName);
      } else if (this.mDomainFilterAppChainsMngr.addChainForApplication(packageName, i, valueOf)) {
        z =
            this.mDomainFilterAppChainsMngr.mAppChainsCounter == 1
                && this.mShouldAddAcceptRuleToInput;
        arrayList.addAll(
            IptablesCommandBuilder.getDomainIptablesCommand(domainFilterRule, contextInfo, z));
        if (z) {
          this.mShouldAddAcceptRuleToInput = false;
        }
      }
    }
    Iterator it2 =
        splitAndConvertCommandsList(arrayList, FirewallDefinitions.Table.FILTER).iterator();
    while (it2.hasNext()) {
      z &= runShellCommand(46, (String) it2.next());
    }
    if (z) {
      return new FirewallResponse(
          FirewallResponse.Result.SUCCESS,
          FirewallResponse.ErrorCode.NO_ERROR,
          "The domain filter on iptables api was successfully enabled.");
    }
    return new FirewallResponse(
        FirewallResponse.Result.FAILED,
        FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
        "Failed to enable domain filter on iptables api.");
  }

  public FirewallResponse removeDomainRules(List list, ContextInfo contextInfo, List list2) {
    boolean z;
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (true) {
      z = true;
      if (!it.hasNext()) {
        break;
      }
      DomainFilterRule domainFilterRule = (DomainFilterRule) it.next();
      String packageName = domainFilterRule.getApplication().getPackageName();
      boolean checkApplicationRulesInDatabase =
          checkApplicationRulesInDatabase(contextInfo.mCallerUid, packageName);
      if (domainFilterRule.getAllowDomains() != null || domainFilterRule.getDenyDomains() != null) {
        if (domainFilterRule.getAllowDomains() == null) {
          domainFilterRule.setAllowDomains(list2);
        } else if (domainFilterRule.getDenyDomains() == null) {
          domainFilterRule.setDenyDomains(list2);
        }
        boolean z2 =
            !checkApplicationRulesInDatabase
                && this.mDomainFilterAppChainsMngr.mAppChainsCounter == 1;
        arrayList.addAll(
            IptablesCommandBuilder.getDomainRemoveIptablesCommand(
                domainFilterRule, contextInfo, z2));
        if (z2) {
          this.mShouldAddAcceptRuleToInput = true;
        }
      }
      if (!checkApplicationRulesInDatabase) {
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        int i = -1;
        if (!"*".equals(packageName)
            && (i = FirewallUtils.getUidForApplication(packageName, userId)) == null) {
          Log.e("FirewallRulesApplier", "failed to get uid for " + packageName);
        } else {
          this.mDomainFilterAppChainsMngr.removeChainForApplication(
              packageName, i, Integer.valueOf(userId));
        }
      }
    }
    Iterator it2 =
        splitAndConvertCommandsList(arrayList, FirewallDefinitions.Table.FILTER).iterator();
    while (it2.hasNext()) {
      z &= runShellCommand(46, (String) it2.next());
    }
    if (z) {
      return new FirewallResponse(
          FirewallResponse.Result.SUCCESS,
          FirewallResponse.ErrorCode.NO_ERROR,
          "The domain rule was successfuly removed/updated.");
    }
    return new FirewallResponse(
        FirewallResponse.Result.FAILED,
        FirewallResponse.ErrorCode.UNEXPECTED_ERROR,
        "Failed to remove domain rule from the iptables.");
  }

  public boolean blockPort53(AppIdentity appIdentity, ContextInfo contextInfo, boolean z) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    FirewallRule createPort53DenyRule =
        this.mFirewallService.createPort53DenyRule(Firewall.AddressType.IPV4, appIdentity);
    if ("*".equals(appIdentity.getPackageName())) {
      arrayList.addAll(
          IptablesCommandBuilder.createExemptRulesCommandsForDnsTether(z, contextInfo));
    }
    arrayList2.addAll(
        IptablesCommandBuilder.getCreateDenyPort53Commands(
            createPort53DenyRule, contextInfo, "-A", createPort53DenyRule.getRuleType()));
    if (!z) {
      ArrayList arrayList3 = new ArrayList();
      Iterator it = arrayList2.iterator();
      while (it.hasNext()) {
        arrayList3.add(((String) it.next()).replaceAll("-A", "-D"));
      }
      arrayList2 = arrayList3;
    }
    arrayList.addAll(arrayList2);
    return runShellCommand(
            Firewall.AddressType.IPV6,
            String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, arrayList))
        & completeCommandAndExecute(
            arrayList, FirewallDefinitions.Table.FILTER, Firewall.AddressType.IPV4);
  }

  public boolean flushDomainChains(Integer num, boolean z) {
    this.mDomainFilterAppChainsMngr.clearChainsForUserId(num);
    boolean z2 = this.mDomainFilterAppChainsMngr.mAppChainsCounter == 0;
    List domainFlushBaseChainsCommand =
        IptablesCommandBuilder.getDomainFlushBaseChainsCommand(num, z, z2);
    if (z2) {
      this.mShouldAddAcceptRuleToInput = true;
    }
    return runShellCommand(
            Firewall.AddressType.IPV6,
            String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, domainFlushBaseChainsCommand))
        & completeCommandAndExecute(
            domainFlushBaseChainsCommand,
            FirewallDefinitions.Table.FILTER,
            Firewall.AddressType.IPV4);
  }

  public boolean flushDnsPortChains(Integer num, boolean z, ContextInfo contextInfo) {
    this.mDomainFilterAppChainsMngr.clearChainsForUserId(num);
    List dnsPortFlushBaseChainsCommand =
        IptablesCommandBuilder.getDnsPortFlushBaseChainsCommand(num, z);
    return runShellCommand(
            Firewall.AddressType.IPV6,
            String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, dnsPortFlushBaseChainsCommand))
        & completeCommandAndExecute(
            dnsPortFlushBaseChainsCommand,
            FirewallDefinitions.Table.FILTER,
            Firewall.AddressType.IPV4);
  }

  public final IOemNetd getOemNetdService() {
    IOemNetd iOemNetd = this.mOemNetdService;
    if (iOemNetd != null) {
      return iOemNetd;
    }
    if (this.mNetdService == null) {
      connectNativeNetdService();
    }
    INetd iNetd = this.mNetdService;
    if (iNetd != null) {
      try {
        this.mOemNetdService = IOemNetd.Stub.asInterface(iNetd.getOemNetd());
      } catch (RemoteException e) {
        Log.e("FirewallRulesApplier", "Failed to get OemNetd listener " + e.getMessage());
      }
    }
    return this.mOemNetdService;
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

  public void updateStatusOnDB(
      FirewallRule firewallRule, FirewallRule.Status status, ContextInfo contextInfo) {
    ContentValues contentValues = new ContentValues();
    int i = contextInfo.mCallerUid;
    if (i != 1000) {
      contentValues.put("adminUid", Integer.valueOf(i));
    }
    ContentValues contentValues2 = new ContentValues();
    contentValues2.put("status", status.toString());
    contentValues.put("ruleType", firewallRule.getRuleType().name());
    int i2 = AbstractC14331.f1690x6178b957[firewallRule.getRuleType().ordinal()];
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
      this.mEdmStorageProvider.update("FirewallRule", contentValues2, contentValues);
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
      this.mEdmStorageProvider.update("FirewallRule", contentValues2, contentValues);
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
      this.mEdmStorageProvider.update("FirewallRule", contentValues2, contentValues);
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
    this.mEdmStorageProvider.update("FirewallRule", contentValues2, contentValues);
  }

  /* renamed from: com.android.server.enterprise.firewall.FirewallRulesApplier$1 */
  public abstract /* synthetic */ class AbstractC14331 {

    /* renamed from: $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table */
    public static final /* synthetic */ int[] f1689xb736b260;

    /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType */
    public static final /* synthetic */ int[] f1690x6178b957;

    static {
      int[] iArr = new int[FirewallRule.RuleType.values().length];
      f1690x6178b957 = iArr;
      try {
        iArr[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        f1690x6178b957[FirewallRule.RuleType.DENY.ordinal()] = 2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        f1690x6178b957[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
      } catch (NoSuchFieldError unused3) {
      }
      try {
        f1690x6178b957[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
      } catch (NoSuchFieldError unused4) {
      }
      int[] iArr2 = new int[FirewallDefinitions.Table.values().length];
      f1689xb736b260 = iArr2;
      try {
        iArr2[FirewallDefinitions.Table.FILTER.ordinal()] = 1;
      } catch (NoSuchFieldError unused5) {
      }
      try {
        f1689xb736b260[FirewallDefinitions.Table.NAT.ordinal()] = 2;
      } catch (NoSuchFieldError unused6) {
      }
    }
  }

  public void reloadIptablesRules() {
    Iterator it = getAllAdmins().iterator();
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      if (intValue != 1000) {
        ContextInfo contextInfo = new ContextInfo(intValue);
        FirewallRule[] rules = this.mFirewallService.getRules(contextInfo, 15, null);
        for (FirewallRule firewallRule : rules) {
          if (!FirewallUtils.verifyPackageName(
              firewallRule.getApplication().getPackageName(),
              UserHandle.getUserId(contextInfo.mCallerUid))) {
            Log.i("FirewallRulesApplier", "reloadIptablesRules() - Package not installed");
          } else {
            if (!FirewallRule.Status.DISABLED.equals(firewallRule.getStatus())) {
              enableIptablesRule(firewallRule, true, contextInfo);
            }
            if (this.mFirewallService.shouldApplyIcmpAllowRule(firewallRule)) {
              addOrRemoveIcmpAllowRule(true);
            }
          }
        }
        if (UserHandle.getUserId(intValue) == 0
            && this.mFirewallService.shouldApplyExemptRules(rules)) {
          addOrRemoveExemptRules(true, contextInfo);
        }
        if (UserHandle.getUserId(intValue) == 0
            && this.mFirewallService.shouldApplyKGExemptRule(rules)) {
          addOrRemoveKGExemptRule(true, contextInfo);
        }
      }
    }
  }

  public void reloadDomainFilterOnIptablesRules() {
    Iterator it = getAllAdmins().iterator();
    boolean z = false;
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      ContextInfo contextInfo = new ContextInfo(intValue);
      List<DomainFilterRule> domainFilterRules =
          this.mFirewallService.getDomainFilterRules(contextInfo, null, -1);
      if (this.mFirewallService.isDomainFilterOnIptablesEnabled(contextInfo)) {
        addDomainRules(domainFilterRules, contextInfo);
        z = true;
      }
      ArrayList arrayList = new ArrayList();
      for (DomainFilterRule domainFilterRule : domainFilterRules) {
        String packageName = domainFilterRule.getApplication().getPackageName();
        String signature = domainFilterRule.getApplication().getSignature();
        if (hasDenyRuleInDatabase(intValue, packageName) && !arrayList.contains(packageName)) {
          arrayList.add(packageName);
          if (blockPort53(new AppIdentity(packageName, signature), contextInfo, true)) {
            Log.d(
                "FirewallRulesApplier",
                "blockPort53() - port53 rule added successfully. Package: " + packageName);
          }
        }
      }
      arrayList.clear();
    }
    if (z) {
      DomainFilterNapCommon.getInstance(this.mContext).setDomainFilterOnIptablesEnabled(true);
    }
  }

  public ArrayList getAllAdmins() {
    ArrayList arrayList = new ArrayList();
    Iterator it =
        this.mEdmStorageProvider
            .getValues("ADMIN", new String[] {"adminUid"}, new ContentValues())
            .iterator();
    while (it.hasNext()) {
      arrayList.add(((ContentValues) it.next()).getAsInteger("adminUid"));
    }
    return arrayList;
  }

  public void deleteRulesFromAdmin(ContextInfo contextInfo) {
    this.mFirewallService.enableFirewall(contextInfo, false);
    this.mFirewallService.clearRules(contextInfo, 15);
    this.mFirewallService.getRules(contextInfo, 15, null);
    changeRuleStatus(null, contextInfo, false);
  }

  public void setRulesStatusAfterReboot() {
    Iterator it = getAllAdmins().iterator();
    while (it.hasNext()) {
      ContextInfo contextInfo = new ContextInfo(((Integer) it.next()).intValue());
      boolean isFirewallEnabled = this.mFirewallService.isFirewallEnabled(contextInfo);
      FirewallRule[] rules = this.mFirewallService.getRules(contextInfo, 15, null);
      if (rules != null) {
        for (FirewallRule firewallRule : rules) {
          if (isFirewallEnabled) {
            updateStatusOnDB(firewallRule, FirewallRule.Status.PENDING, contextInfo);
          } else {
            updateStatusOnDB(firewallRule, FirewallRule.Status.DISABLED, contextInfo);
          }
        }
      }
    }
  }

  public String[] listIptablesRules(ContextInfo contextInfo) {
    int userId = UserHandle.getUserId(contextInfo.mCallerUid);
    ArrayList arrayList = new ArrayList();
    arrayList.add("************ FILTER TABLE ************");
    arrayList.add(
        FirewallUtils.filterRulesByUser(
            userId,
            runShellCommandWithOutput(
                4,
                String.join(
                    KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                    IptablesCommandBuilder.getListIptablesRestoreCommand("filter")))));
    arrayList.add("************ NAT TABLE ************");
    arrayList.add(
        FirewallUtils.filterRulesByUser(
            userId,
            runShellCommandWithOutput(
                4,
                String.join(
                    KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                    IptablesCommandBuilder.getListIptablesRestoreCommand("nat")))));
    arrayList.add("************ MANGLE TABLE ************");
    arrayList.add(
        FirewallUtils.filterRulesByUser(
            userId,
            runShellCommandWithOutput(
                4,
                String.join(
                    KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                    IptablesCommandBuilder.getListIptablesRestoreCommand("mangle")))));
    arrayList.add("************ RAW TABLE ************");
    arrayList.add(
        FirewallUtils.filterRulesByUser(
            userId,
            runShellCommandWithOutput(
                4,
                String.join(
                    KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                    IptablesCommandBuilder.getListIptablesRestoreCommand("raw")))));
    if (Firewall.mHasIpv6FilterSupport) {
      arrayList.add("=============== IPV6 RULES ===============");
      arrayList.add("************ FILTER TABLE ************");
      arrayList.add(
          FirewallUtils.filterRulesByUser(
              userId,
              runShellCommandWithOutput(
                  6,
                  String.join(
                      KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                      IptablesCommandBuilder.getListIptablesRestoreCommand("filter")))));
    }
    if (Firewall.mHasIpv6NatSupport) {
      arrayList.add("************ NAT TABLE ************");
      arrayList.add(
          FirewallUtils.filterRulesByUser(
              userId,
              runShellCommandWithOutput(
                  6,
                  String.join(
                      KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                      IptablesCommandBuilder.getListIptablesRestoreCommand("nat")))));
      arrayList.add("************ MANGLE TABLE ************");
      arrayList.add(
          FirewallUtils.filterRulesByUser(
              userId,
              runShellCommandWithOutput(
                  6,
                  String.join(
                      KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                      IptablesCommandBuilder.getListIptablesRestoreCommand("mangle")))));
      arrayList.add("************ RAW TABLE ************");
      arrayList.add(
          FirewallUtils.filterRulesByUser(
              userId,
              runShellCommandWithOutput(
                  6,
                  String.join(
                      KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
                      IptablesCommandBuilder.getListIptablesRestoreCommand("raw")))));
    }
    return (String[]) arrayList.toArray(new String[arrayList.size()]);
  }

  public boolean createIptablesChains(Integer num) {
    Context context;
    ArrayList arrayList = new ArrayList();
    if (num == null && (context = this.mContext) != null) {
      arrayList = FirewallUtils.getAllUsers(context);
    } else if (num != null) {
      arrayList.add(num);
    }
    return mergeAndExecuteCmdIptablesV4AndV6(
        IptablesCommandBuilder.getCreateFilterChains(arrayList),
        IptablesCommandBuilder.getCreateNatChains(arrayList));
  }

  public boolean removeIptablesChains(Integer num) {
    return mergeAndExecuteCmdIptablesV4AndV6(
        IptablesCommandBuilder.getRemoveFilterChainsCommand(num),
        IptablesCommandBuilder.getRemoveNatChainsCommand(num));
  }

  public boolean hasIpv6Support(FirewallDefinitions.Table table) {
    if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION < 16) {
      return false;
    }
    return runShellCommand(6, IptablesCommandBuilder.getTestIpv6Commands(table));
  }

  public boolean flushAllChains(Integer num) {
    return mergeAndExecuteCmdIptablesV4AndV6(
        IptablesCommandBuilder.getFlushFilterChainsCommand(num),
        IptablesCommandBuilder.getFlushNatChainsCommand(num));
  }

  public boolean flushChain(FirewallRule.RuleType ruleType, Integer num) {
    return executeCmdIptablesV4AndV6(
        IptablesCommandBuilder.getFlushChainsCommand(ruleType, num),
        FirewallUtils.getTableByRuletype(ruleType));
  }

  public final boolean executeCmdIptablesV4AndV6(List list, FirewallDefinitions.Table table) {
    return runShellCommand(
        FirewallUtils.isIpv6SupportedForTable(table) ? 46 : 4,
        String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, list));
  }

  public final boolean mergeAndExecuteCmdIptablesV4AndV6(List list, List list2) {
    String join = String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, list);
    String join2 = String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, list2);
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

  public final boolean completeCommandAndExecute(
      List list, FirewallDefinitions.Table table, Firewall.AddressType addressType) {
    if (list == null) {
      Log.e("FirewallRulesApplier", "Command list is null");
      return false;
    }
    int i = AbstractC14331.f1689xb736b260[table.ordinal()];
    if (i == 1) {
      list.add(0, KnoxVpnFirewallHelper.TABLE_FILTER);
    } else if (i == 2) {
      list.add(0, KnoxVpnFirewallHelper.TABLE_NAT);
    }
    list.add(KnoxVpnFirewallHelper.COMMIT_CMD);
    return runShellCommand(
        addressType, String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, list));
  }

  public final synchronized boolean runShellCommand(Firewall.AddressType addressType, String str) {
    return runShellCommand(Firewall.AddressType.IPV4.equals(addressType) ? 4 : 6, str);
  }

  public final synchronized boolean runShellCommand(int i, String str) {
    String runKnoxFirewallRulesCommand;
    StringBuilder sb;
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
      sb = new StringBuilder();
      sb.append("Run cmd: ");
    } catch (Exception e) {
      Log.e("FirewallRulesApplier", "Failed to run cmd: " + e.getMessage());
    }
    if (runKnoxFirewallRulesCommand != null && !runKnoxFirewallRulesCommand.isEmpty()) {
      str2 = runKnoxFirewallRulesCommand;
      sb.append(str2);
      Log.d("FirewallRulesApplier", sb.toString());
      if (runKnoxFirewallRulesCommand != null && !runKnoxFirewallRulesCommand.isEmpty()) {
        Log.e(
            "FirewallRulesApplier",
            "Failed to run command. Result=" + runKnoxFirewallRulesCommand + "\ncommand=" + str);
        return false;
      }
      return true;
    }
    str2 = "OK";
    sb.append(str2);
    Log.d("FirewallRulesApplier", sb.toString());
    if (runKnoxFirewallRulesCommand != null) {
      Log.e(
          "FirewallRulesApplier",
          "Failed to run command. Result=" + runKnoxFirewallRulesCommand + "\ncommand=" + str);
      return false;
    }
    return true;
  }

  public boolean isDomainFilterOnIptablesSupported() {
    boolean runShellCommand =
        runShellCommand(4, IptablesCommandBuilder.getDomainFilterOnIptablesCheckCommand(true));
    runShellCommand(4, IptablesCommandBuilder.getDomainFilterOnIptablesCheckCommand(false));
    return runShellCommand;
  }

  public static List splitAndConvertCommandsList(List list, FirewallDefinitions.Table table) {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    int i = 0;
    do {
      int i2 = 0;
      while (i < list.size()) {
        String str = (String) list.get(i);
        i2 += str.getBytes(StandardCharsets.UTF_16).length;
        if (i2 > 400000) {
          break;
        }
        arrayList2.add(str);
        i++;
      }
      arrayList2.add(
          0,
          table == FirewallDefinitions.Table.FILTER
              ? KnoxVpnFirewallHelper.TABLE_FILTER
              : KnoxVpnFirewallHelper.TABLE_NAT);
      arrayList2.add(KnoxVpnFirewallHelper.COMMIT_CMD);
      arrayList.add(String.join(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, arrayList2));
      arrayList2.clear();
    } while (i < list.size());
    Log.d("FirewallRulesApplier", "total number of commands " + arrayList.size());
    return arrayList;
  }

  public synchronized void addOrRemoveExemptRules(boolean z, ContextInfo contextInfo) {
    if (z) {
      if (this.mIsExemptRulesApplied) {
        return;
      }
    }
    if (z || this.mIsExemptRulesApplied) {
      executeCmdIptablesV4AndV6(
          IptablesCommandBuilder.createExemptRulesCommands(z, contextInfo),
          FirewallDefinitions.Table.FILTER);
      this.mIsExemptRulesApplied = z;
    }
  }

  public synchronized void reinsertExemptRules(ContextInfo contextInfo) {
    this.mIsExemptRulesApplied = false;
    addOrRemoveExemptRules(true, contextInfo);
  }

  public boolean isExemptRulesApplied() {
    return this.mIsExemptRulesApplied;
  }

  public synchronized void addOrRemoveKGExemptRule(boolean z, ContextInfo contextInfo) {
    if (z) {
      if (this.mIsKGExemptRuleApplied) {
        return;
      }
    }
    if (z || this.mIsKGExemptRuleApplied) {
      executeCmdIptablesV4AndV6(
          IptablesCommandBuilder.createKGExemptRuleCommand(z, contextInfo),
          FirewallDefinitions.Table.FILTER);
      this.mIsKGExemptRuleApplied = z;
    }
  }

  public synchronized void reinsertKGExemptRule(ContextInfo contextInfo) {
    this.mIsKGExemptRuleApplied = false;
    addOrRemoveKGExemptRule(true, contextInfo);
  }

  public boolean isKGExemptRuleApplied() {
    return this.mIsKGExemptRuleApplied;
  }

  public synchronized void addOrRemoveIcmpAllowRule(boolean z) {
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
        4,
        String.join(
            KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
            IptablesCommandBuilder.createIcmpAllowRuleCommands(z, 4)));
    if (FirewallUtils.isIpv6SupportedForTable(FirewallDefinitions.Table.FILTER)) {
      runShellCommand(
          6,
          String.join(
              KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE,
              IptablesCommandBuilder.createIcmpAllowRuleCommands(z, 6)));
    }
  }

  public synchronized void clearIcmpAllowRule() {
    this.mIcmpAllowRuleApplies = 1;
    addOrRemoveIcmpAllowRule(false);
  }

  public synchronized void reinsertIcmpAllowRule() {
    int i = this.mIcmpAllowRuleApplies;
    this.mIcmpAllowRuleApplies = 0;
    addOrRemoveIcmpAllowRule(true);
    this.mIcmpAllowRuleApplies = i;
  }

  public boolean isIcmpAllowRuleApplied() {
    return this.mIcmpAllowRuleApplies > 0;
  }

  public class DomainFilterApplicationChainsManager {
    public int mAppChainsCounter;
    public Map mDomainFilterChainsCache;
    public Object mDomainFilterChainsCacheLock;

    public DomainFilterApplicationChainsManager() {
      this.mDomainFilterChainsCache = new HashMap();
      this.mDomainFilterChainsCacheLock = new Object();
      this.mAppChainsCounter = 0;
    }

    public boolean addChainForApplication(String str, Integer num, Integer num2) {
      if (checkChainExistsForApplication(num, num2)) {
        Log.d("FirewallRulesApplier", "chain exists for application");
        return true;
      }
      List createDomainFilterChainsForUidCommands =
          IptablesCommandBuilder.getCreateDomainFilterChainsForUidCommands(
              str, num.intValue(), num2.intValue(), this.mAppChainsCounter == 0);
      if ((str != null && "*".equals(str)) || num.intValue() == -1) {
        List uidsToExemptForUser =
            FirewallUtils.getUidsToExemptForUser(
                num2.intValue(), FirewallRulesApplier.this.mContext);
        createDomainFilterChainsForUidCommands.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        Iterator it = uidsToExemptForUser.iterator();
        while (it.hasNext()) {
          createDomainFilterChainsForUidCommands.addAll(
              IptablesCommandBuilder.getCreateDomainFilterExceptionUidRules(
                  ((Integer) it.next()).intValue()));
        }
        createDomainFilterChainsForUidCommands.add(KnoxVpnFirewallHelper.COMMIT_CMD);
      } else if (FirewallUtils.isPackageExempt(str)) {
        createDomainFilterChainsForUidCommands.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        createDomainFilterChainsForUidCommands.addAll(
            IptablesCommandBuilder.getCreateDomainFilterExceptionForSpecificUidRule(
                str, num.intValue()));
        createDomainFilterChainsForUidCommands.add(KnoxVpnFirewallHelper.COMMIT_CMD);
      }
      if (!FirewallRulesApplier.this.executeCmdIptablesV4AndV6(
          createDomainFilterChainsForUidCommands, FirewallDefinitions.Table.FILTER)) {
        Log.e("FirewallRulesApplier", "Failed to create chain for " + str);
        return false;
      }
      synchronized (this.mDomainFilterChainsCacheLock) {
        if (!this.mDomainFilterChainsCache.containsKey(num2)) {
          this.mDomainFilterChainsCache.put(num2, new ArrayList());
        }
        ((List) this.mDomainFilterChainsCache.get(num2)).add(num);
        this.mAppChainsCounter++;
      }
      return true;
    }

    public boolean removeChainForApplication(String str, Integer num, Integer num2) {
      if (!checkChainExistsForApplication(num, num2)) {
        return true;
      }
      boolean z = this.mAppChainsCounter == 1;
      List removeDomainFilterChainsForUidCommands =
          IptablesCommandBuilder.getRemoveDomainFilterChainsForUidCommands(
              str, num.intValue(), num2.intValue(), z);
      if (z) {
        FirewallRulesApplier.this.mShouldAddAcceptRuleToInput = true;
      }
      if (!FirewallRulesApplier.this.executeCmdIptablesV4AndV6(
          removeDomainFilterChainsForUidCommands, FirewallDefinitions.Table.FILTER)) {
        Log.e("FirewallRulesApplier", "Failed to remove chain for " + str);
        return false;
      }
      synchronized (this.mDomainFilterChainsCacheLock) {
        if (this.mDomainFilterChainsCache.containsKey(num2)) {
          ((List) this.mDomainFilterChainsCache.get(num2)).remove(num);
          this.mAppChainsCounter--;
        }
      }
      return true;
    }

    public boolean clearChainsForUserId(Integer num) {
      boolean z = true;
      if (this.mDomainFilterChainsCache.containsKey(num)) {
        ArrayList arrayList = new ArrayList((Collection) this.mDomainFilterChainsCache.get(num));
        if (!arrayList.isEmpty()) {
          Iterator it = arrayList.iterator();
          while (it.hasNext()) {
            z &= removeChainForApplication(null, (Integer) it.next(), num);
          }
        }
      }
      return z;
    }

    public final boolean checkChainExistsForApplication(Integer num, Integer num2) {
      return this.mDomainFilterChainsCache.containsKey(num2)
          && ((List) this.mDomainFilterChainsCache.get(num2)).contains(num);
    }
  }

  public final boolean checkApplicationRulesInDatabase(int i, String str) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("adminUid", Integer.valueOf(i));
    contentValues.put("packageName", str);
    List values =
        this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, contentValues);
    if (values == null || values.isEmpty() || values.get(0) == null) {
      return false;
    }
    ContentValues contentValues2 = (ContentValues) values.get(0);
    return (FirewallUtils.getListFromDb(contentValues2, "whitelist", this.mEdmStorageProvider)
                .isEmpty()
            && FirewallUtils.getListFromDb(contentValues2, "blacklist", this.mEdmStorageProvider)
                .isEmpty())
        ? false
        : true;
  }

  public boolean hasDenyRuleInDatabase(int i, String str) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("adminUid", Integer.valueOf(i));
    contentValues.put("packageName", str);
    List values =
        this.mEdmStorageProvider.getValues("DomainFilterTable", (String[]) null, contentValues);
    if (values == null || values.isEmpty() || values.get(0) == null) {
      Log.d("FirewallRulesApplier", "hasDenyRuleInDatabase(): false");
      return false;
    }
    if (!FirewallUtils.getListFromDb(
            (ContentValues) values.get(0), "blacklist", this.mEdmStorageProvider)
        .isEmpty()) {
      return true;
    }
    Log.d("FirewallRulesApplier", "hasDenyRuleInDatabase(): false");
    return false;
  }
}
