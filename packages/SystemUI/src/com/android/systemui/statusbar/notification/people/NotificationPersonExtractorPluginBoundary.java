package com.android.systemui.statusbar.notification.people;

import com.android.systemui.plugins.NotificationPersonExtractorPlugin;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationPersonExtractorPluginBoundary implements NotificationPersonExtractor {
    public NotificationPersonExtractorPlugin plugin;

    public NotificationPersonExtractorPluginBoundary(ExtensionController extensionController) {
        ExtensionControllerImpl extensionControllerImpl = (ExtensionControllerImpl) extensionController;
        extensionControllerImpl.getClass();
        ExtensionControllerImpl.ExtensionBuilder extensionBuilder = new ExtensionControllerImpl.ExtensionBuilder(extensionControllerImpl, 0);
        extensionBuilder.withPlugin(NotificationPersonExtractorPlugin.class);
        extensionBuilder.mExtension.mCallbacks.add(new Consumer() { // from class: com.android.systemui.statusbar.notification.people.NotificationPersonExtractorPluginBoundary.1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationPersonExtractorPluginBoundary.this.plugin = (NotificationPersonExtractorPlugin) obj;
            }
        });
        this.plugin = (NotificationPersonExtractorPlugin) extensionBuilder.build().mItem;
    }
}
