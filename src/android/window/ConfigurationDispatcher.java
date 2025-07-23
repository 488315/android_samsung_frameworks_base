package android.window;

import android.content.res.Configuration;

/* loaded from: classes5.dex */
public interface ConfigurationDispatcher {
    void dispatchConfigurationChanged(Configuration configuration);

    default boolean shouldReportPrivateChanges() {
        return false;
    }
}
