package com.android.server.updates;

import com.android.server.firewall.IntentFirewall;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class IntentFirewallInstallReceiver extends ConfigUpdateInstallReceiver {
    public IntentFirewallInstallReceiver() {
        super(
                IntentFirewall.RULES_DIR.getAbsolutePath(),
                "ifw.xml",
                "metadata/",
                "gservices.version");
    }
}
