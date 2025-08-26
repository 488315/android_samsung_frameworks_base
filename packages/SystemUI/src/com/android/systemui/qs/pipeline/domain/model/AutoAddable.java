package com.android.systemui.qs.pipeline.domain.model;

import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public interface AutoAddable {
    Flow autoAddSignal(int i);

    AutoAddTracking getAutoAddTracking();

    String getDescription();
}
