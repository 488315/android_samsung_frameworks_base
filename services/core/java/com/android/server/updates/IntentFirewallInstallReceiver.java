package com.android.server.updates;

import com.android.server.firewall.IntentFirewall;

public class IntentFirewallInstallReceiver extends ConfigUpdateInstallReceiver {
    public IntentFirewallInstallReceiver() {
        super(
                IntentFirewall.RULES_DIR.getAbsolutePath(),
                "ifw.xml",
                "metadata/",
                "gservices.version");
    }
}
