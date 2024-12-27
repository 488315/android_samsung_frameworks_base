package com.android.server.wm;

import android.content.res.Configuration;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ConfigurationContainerListener {
    default void onMergedOverrideConfigurationChanged(Configuration configuration) {}

    default void onRequestedOverrideConfigurationChanged(Configuration configuration) {}
}
