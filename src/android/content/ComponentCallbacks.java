package android.content;

import android.content.res.Configuration;

/* loaded from: classes.dex */
public interface ComponentCallbacks {
    void onConfigurationChanged(Configuration configuration);

    @Deprecated
    void onLowMemory();
}
