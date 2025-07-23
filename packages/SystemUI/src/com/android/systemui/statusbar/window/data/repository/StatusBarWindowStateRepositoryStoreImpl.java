package com.android.systemui.statusbar.window.data.repository;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarWindowStateRepositoryStoreImpl implements StatusBarWindowStateRepositoryStore {
    public final StatusBarWindowStatePerDisplayRepositoryFactory factory;
    public final Map repositoryCache = new LinkedHashMap();

    public StatusBarWindowStateRepositoryStoreImpl(int i, StatusBarWindowStatePerDisplayRepositoryFactory statusBarWindowStatePerDisplayRepositoryFactory) {
        this.factory = statusBarWindowStatePerDisplayRepositoryFactory;
        statusBarWindowStatePerDisplayRepositoryFactory.create(i);
    }
}
