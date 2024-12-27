package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import android.util.IndentingPrintWriter;

import com.android.modules.utils.TypedXmlSerializer;

import java.util.LinkedHashMap;
import java.util.Objects;

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
            PolicyDefinition policyDefinition,
            LinkedHashMap linkedHashMap,
            PolicyValue policyValue) {
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        this.mPoliciesSetByAdmins = linkedHashMap2;
        this.mPolicyDefinition = policyDefinition;
        linkedHashMap2.putAll(linkedHashMap);
        this.mCurrentResolvedPolicy = policyValue;
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x0147 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x000c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.devicepolicy.PolicyState readFromXml(
            com.android.modules.utils.TypedXmlPullParser r12) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.devicepolicy.PolicyState.readFromXml(com.android.modules.utils.TypedXmlPullParser):com.android.server.devicepolicy.PolicyState");
    }

    public final boolean addPolicy(EnforcingAdmin enforcingAdmin, PolicyValue policyValue) {
        Objects.requireNonNull(enforcingAdmin);
        this.mPoliciesSetByAdmins.remove(enforcingAdmin);
        this.mPoliciesSetByAdmins.put(enforcingAdmin, policyValue);
        PolicyDefinition policyDefinition = this.mPolicyDefinition;
        if (policyDefinition.isNonCoexistablePolicy()) {
            return false;
        }
        PolicyValue resolve =
                policyDefinition.mResolutionMechanism.resolve(this.mPoliciesSetByAdmins);
        boolean z = !Objects.equals(resolve, this.mCurrentResolvedPolicy);
        this.mCurrentResolvedPolicy = resolve;
        return z;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        PolicyDefinition policyDefinition = this.mPolicyDefinition;
        indentingPrintWriter.println(policyDefinition.mPolicyKey);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Per-admin Policy:");
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
                new Object[] {policyDefinition.mResolutionMechanism.getClass().getSimpleName()});
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(this.mCurrentResolvedPolicy);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final android.app.admin.PolicyState getParcelablePolicyState() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            linkedHashMap.put(
                    enforcingAdmin.getParcelableAdmin(),
                    (PolicyValue) this.mPoliciesSetByAdmins.get(enforcingAdmin));
        }
        return new android.app.admin.PolicyState(
                linkedHashMap,
                this.mCurrentResolvedPolicy,
                this.mPolicyDefinition.mResolutionMechanism.getParcelableResolutionMechanism());
    }

    public final LinkedHashMap getPoliciesSetByAdmins() {
        return new LinkedHashMap(this.mPoliciesSetByAdmins);
    }

    public final boolean resolvePolicy(LinkedHashMap linkedHashMap) {
        PolicyDefinition policyDefinition = this.mPolicyDefinition;
        if (policyDefinition.isNonCoexistablePolicy()) {
            return false;
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        linkedHashMap2.putAll(this.mPoliciesSetByAdmins);
        PolicyValue resolve = policyDefinition.mResolutionMechanism.resolve(linkedHashMap2);
        boolean z = !Objects.equals(resolve, this.mCurrentResolvedPolicy);
        this.mCurrentResolvedPolicy = resolve;
        return z;
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startTag((String) null, "policy-definition-entry");
        PolicyDefinition policyDefinition = this.mPolicyDefinition;
        policyDefinition.mPolicyKey.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, "policy-definition-entry");
        PolicyValue policyValue = this.mCurrentResolvedPolicy;
        PolicySerializer policySerializer = policyDefinition.mPolicySerializer;
        if (policyValue != null) {
            typedXmlSerializer.startTag((String) null, "resolved-value-entry");
            policySerializer.saveToXml(this.mCurrentResolvedPolicy.getValue(), typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "resolved-value-entry");
        }
        for (EnforcingAdmin enforcingAdmin : this.mPoliciesSetByAdmins.keySet()) {
            typedXmlSerializer.startTag((String) null, "admin-policy-entry");
            if (this.mPoliciesSetByAdmins.get(enforcingAdmin) != null) {
                typedXmlSerializer.startTag((String) null, "policy-value-entry");
                policySerializer.saveToXml(
                        ((PolicyValue) this.mPoliciesSetByAdmins.get(enforcingAdmin)).getValue(),
                        typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "policy-value-entry");
            }
            typedXmlSerializer.startTag((String) null, "enforcing-admin-entry");
            enforcingAdmin.saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag((String) null, "enforcing-admin-entry");
            typedXmlSerializer.endTag((String) null, "admin-policy-entry");
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("\nPolicyKey - ");
        PolicyDefinition policyDefinition = this.mPolicyDefinition;
        sb.append(policyDefinition.mPolicyKey);
        sb.append("\nmPolicyDefinition= \n\t");
        sb.append(policyDefinition);
        sb.append("\nmPoliciesSetByAdmins= \n\t");
        sb.append(this.mPoliciesSetByAdmins);
        sb.append(",\nmCurrentResolvedPolicy= \n\t");
        sb.append(this.mCurrentResolvedPolicy);
        sb.append(" }");
        return sb.toString();
    }
}
