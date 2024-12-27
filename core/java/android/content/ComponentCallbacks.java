package android.content;

import android.content.res.Configuration;

public interface ComponentCallbacks {
    void onConfigurationChanged(Configuration configuration);

    @Deprecated
    void onLowMemory();
}
