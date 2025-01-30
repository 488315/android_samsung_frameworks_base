package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import android.util.IndentingPrintWriter;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.utils.Slogf;
import java.util.LinkedHashMap;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class PolicyState {
  public PolicyValue mCurrentResolvedPolicy;
  public final LinkedHashMap mPoliciesSetByAdmins;
  public final PolicyDefinition mPolicyDefinition;

  public PolicyState(PolicyDefinition policyDefinition) {
    this.mPoliciesSetByAdmins = new LinkedHashMap();
    Objects.requireNonNull(policyDefinition);
    this.mPolicyDefinition = policyDefinition;
  }

  public PolicyState(
      PolicyDefinition policyDefinition, LinkedHashMap linkedHashMap, PolicyValue policyValue) {
    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
    this.mPoliciesSetByAdmins = linkedHashMap2;
    Objects.requireNonNull(policyDefinition);
    Objects.requireNonNull(linkedHashMap);
    this.mPolicyDefinition = policyDefinition;
    linkedHashMap2.putAll(linkedHashMap);
    this.mCurrentResolvedPolicy = policyValue;
  }

  public boolean addPolicy(EnforcingAdmin enforcingAdmin, PolicyValue policyValue) {
    Objects.requireNonNull(enforcingAdmin);
    this.mPoliciesSetByAdmins.remove(enforcingAdmin);
    this.mPoliciesSetByAdmins.put(enforcingAdmin, policyValue);
    return resolvePolicy();
  }

  public boolean addPolicy(
      EnforcingAdmin enforcingAdmin, PolicyValue policyValue, LinkedHashMap linkedHashMap) {
    LinkedHashMap linkedHashMap2 = this.mPoliciesSetByAdmins;
    Objects.requireNonNull(enforcingAdmin);
    linkedHashMap2.put(enforcingAdmin, policyValue);
    return resolvePolicy(linkedHashMap);
  }

  public boolean removePolicy(EnforcingAdmin enforcingAdmin) {
    Objects.requireNonNull(enforcingAdmin);
    if (this.mPoliciesSetByAdmins.remove(enforcingAdmin) == null) {
      return false;
    }
    return resolvePolicy();
  }

  public boolean removePolicy(EnforcingAdmin enforcingAdmin, LinkedHashMap linkedHashMap) {
    Objects.requireNonNull(enforcingAdmin);
    if (this.mPoliciesSetByAdmins.remove(enforcingAdmin) == null) {
      return false;
    }
    return resolvePolicy(linkedHashMap);
  }

  public boolean resolvePolicy(LinkedHashMap linkedHashMap) {
    if (this.mPolicyDefinition.isNonCoexistablePolicy()) {
      return false;
    }
    LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
    linkedHashMap2.putAll(this.mPoliciesSetByAdmins);
    PolicyValue resolvePolicy = this.mPolicyDefinition.resolvePolicy(linkedHashMap2);
    boolean z = !Objects.equals(resolvePolicy, this.mCurrentResolvedPolicy);
    this.mCurrentResolvedPolicy = resolvePolicy;
    return z;
  }

  public LinkedHashMap getPoliciesSetByAdmins() {
    return new LinkedHashMap(this.mPoliciesSetByAdmins);
  }

  public final boolean resolvePolicy() {
    if (this.mPolicyDefinition.isNonCoexistablePolicy()) {
      return false;
    }
    PolicyValue resolvePolicy = this.mPolicyDefinition.resolvePolicy(this.mPoliciesSetByAdmins);
    boolean z = !Objects.equals(resolvePolicy, this.mCurrentResolvedPolicy);
    this.mCurrentResolvedPolicy = resolvePolicy;
    return z;
  }

  public PolicyValue getCurrentResolvedPolicy() {
    return this.mCurrentResolvedPolicy;
  }

  public android.app.admin.PolicyState getParcelablePolicyState() {
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    for (EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
      linkedHashMap.put(
          enforcingAdmin.getParcelableAdmin(),
          (PolicyValue) this.mPoliciesSetByAdmins.get(enforcingAdmin));
    }
    return new android.app.admin.PolicyState(
        linkedHashMap,
        this.mCurrentResolvedPolicy,
        this.mPolicyDefinition.getResolutionMechanism().mo5258getParcelableResolutionMechanism());
  }

  public String toString() {
    return "\nPolicyKey - "
        + this.mPolicyDefinition.getPolicyKey()
        + "\nmPolicyDefinition= \n\t"
        + this.mPolicyDefinition
        + "\nmPoliciesSetByAdmins= \n\t"
        + this.mPoliciesSetByAdmins
        + ",\nmCurrentResolvedPolicy= \n\t"
        + this.mCurrentResolvedPolicy
        + " }";
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println(this.mPolicyDefinition.getPolicyKey());
    indentingPrintWriter.increaseIndent();
    indentingPrintWriter.println("Per-admin Policy");
    indentingPrintWriter.increaseIndent();
    if (this.mPoliciesSetByAdmins.size() == 0) {
      indentingPrintWriter.println("null");
    } else {
      for (EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
        indentingPrintWriter.println(enforcingAdmin);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(this.mPoliciesSetByAdmins.get(enforcingAdmin));
        indentingPrintWriter.decreaseIndent();
      }
    }
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.printf(
        "Resolved Policy (%s):\n",
        new Object[] {this.mPolicyDefinition.getResolutionMechanism().getClass().getSimpleName()});
    indentingPrintWriter.increaseIndent();
    indentingPrintWriter.println(this.mCurrentResolvedPolicy);
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.decreaseIndent();
  }

  public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.startTag((String) null, "policy-definition-entry");
    this.mPolicyDefinition.saveToXml(typedXmlSerializer);
    typedXmlSerializer.endTag((String) null, "policy-definition-entry");
    if (this.mCurrentResolvedPolicy != null) {
      typedXmlSerializer.startTag((String) null, "resolved-value-entry");
      this.mPolicyDefinition.savePolicyValueToXml(
          typedXmlSerializer, this.mCurrentResolvedPolicy.getValue());
      typedXmlSerializer.endTag((String) null, "resolved-value-entry");
    }
    for (EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
      typedXmlSerializer.startTag((String) null, "admin-policy-entry");
      if (this.mPoliciesSetByAdmins.get(enforcingAdmin) != null) {
        typedXmlSerializer.startTag((String) null, "policy-value-entry");
        this.mPolicyDefinition.savePolicyValueToXml(
            typedXmlSerializer,
            ((PolicyValue) this.mPoliciesSetByAdmins.get(enforcingAdmin)).getValue());
        typedXmlSerializer.endTag((String) null, "policy-value-entry");
      }
      typedXmlSerializer.startTag((String) null, "enforcing-admin-entry");
      enforcingAdmin.saveToXml(typedXmlSerializer);
      typedXmlSerializer.endTag((String) null, "enforcing-admin-entry");
      typedXmlSerializer.endTag((String) null, "admin-policy-entry");
    }
  }

  public static PolicyState readFromXml(TypedXmlPullParser typedXmlPullParser) {
    String name;
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    int depth = typedXmlPullParser.getDepth();
    PolicyDefinition policyDefinition = null;
    PolicyValue policyValue = null;
    while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
      name = typedXmlPullParser.getName();
      name.hashCode();
      switch (name) {
        case "policy-definition-entry":
          policyDefinition = PolicyDefinition.readFromXml(typedXmlPullParser);
          if (policyDefinition != null) {
            break;
          } else {
            Slogf.wtf(
                "PolicyState",
                "Error Parsing TAG_POLICY_DEFINITION_ENTRY, PolicyDefinition is null");
            break;
          }
        case "admin-policy-entry":
          int depth2 = typedXmlPullParser.getDepth();
          EnforcingAdmin enforcingAdmin = null;
          PolicyValue policyValue2 = null;
          while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
            String name2 = typedXmlPullParser.getName();
            name2.hashCode();
            if (name2.equals("enforcing-admin-entry")) {
              enforcingAdmin = EnforcingAdmin.readFromXml(typedXmlPullParser);
              if (enforcingAdmin == null) {
                Slogf.wtf(
                    "PolicyState",
                    "Error Parsing TAG_ENFORCING_ADMIN_ENTRY, EnforcingAdmin is null");
              }
            } else if (name2.equals("policy-value-entry")
                && (policyValue2 = policyDefinition.readPolicyValueFromXml(typedXmlPullParser))
                    == null) {
              Slogf.wtf("PolicyState", "Error Parsing TAG_POLICY_VALUE_ENTRY, PolicyValue is null");
            }
          }
          if (enforcingAdmin != null) {
            linkedHashMap.put(enforcingAdmin, policyValue2);
            break;
          } else {
            Slogf.wtf(
                "PolicyState", "Error Parsing TAG_ADMIN_POLICY_ENTRY, EnforcingAdmin is null");
            break;
          }
        case "resolved-value-entry":
          if (policyDefinition == null) {
            Slogf.wtf(
                "PolicyState", "Error Parsing TAG_RESOLVED_VALUE_ENTRY, policyDefinition is null");
            break;
          } else {
            policyValue = policyDefinition.readPolicyValueFromXml(typedXmlPullParser);
            if (policyValue != null) {
              break;
            } else {
              Slogf.wtf(
                  "PolicyState",
                  "Error Parsing TAG_RESOLVED_VALUE_ENTRY, currentResolvedPolicy is null");
              break;
            }
          }
        default:
          Slogf.wtf("PolicyState", "Unknown tag: " + name);
          break;
      }
    }
    if (policyDefinition != null) {
      return new PolicyState(policyDefinition, linkedHashMap, policyValue);
    }
    Slogf.wtf("PolicyState", "Error parsing policyState, policyDefinition is null");
    return null;
  }

  public PolicyDefinition getPolicyDefinition() {
    return this.mPolicyDefinition;
  }
}
