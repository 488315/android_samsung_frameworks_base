package com.android.systemui.statusbar.phone;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
