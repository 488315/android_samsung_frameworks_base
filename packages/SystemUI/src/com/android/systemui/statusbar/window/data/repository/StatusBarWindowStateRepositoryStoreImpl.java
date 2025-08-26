package com.android.systemui.statusbar.window.data.repository;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class StatusBarWindowStateRepositoryStoreImpl implements StatusBarWindowStateRepositoryStore {
    public final StatusBarWindowStatePerDisplayRepositoryFactory factory;
    public final Map repositoryCache = new LinkedHashMap();

    public StatusBarWindowStateRepositoryStoreImpl(int i, StatusBarWindowStatePerDisplayRepositoryFactory statusBarWindowStatePerDisplayRepositoryFactory) {
        this.factory = statusBarWindowStatePerDisplayRepositoryFactory;
        statusBarWindowStatePerDisplayRepositoryFactory.create(i);
    }
}
