package com.android.systemui.statusbar.phone;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConfigurationControllerStartable implements CoreStartable {
    public final ConfigurationController configurationController;
    public final Set listeners;

    public ConfigurationControllerStartable(ConfigurationController configurationController, Set<ConfigurationController.ConfigurationListener> set) {
        this.configurationController = configurationController;
        this.listeners = set;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((ConfigurationControllerImpl) this.configurationController).addCallback((ConfigurationController.ConfigurationListener) it.next());
        }
    }
}
