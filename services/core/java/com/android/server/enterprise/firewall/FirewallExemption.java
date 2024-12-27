package com.android.server.enterprise.firewall;

import com.samsung.android.knox.net.firewall.FirewallRule;

public final class FirewallExemption {
    public final FirewallRule mExemptionRule;
    public boolean mIsApplied = false;

    public FirewallExemption(FirewallRule firewallRule) {
        this.mExemptionRule = firewallRule;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "FirewallExemption for "
                        + this.mExemptionRule.getApplication().getPackageName()
                        + ", ");
        sb.append("for address " + this.mExemptionRule.getIpAddress() + ". ");
        StringBuilder sb2 = new StringBuilder("isApplied: ");
        sb2.append(this.mIsApplied);
        sb.append(sb2.toString());
        return sb.toString();
    }
}
