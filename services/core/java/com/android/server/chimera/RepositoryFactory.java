package com.android.server.chimera;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RepositoryFactory {
    static RepositoryFactory sInstance;
    SettingRepository mSettingRepository;
    SystemRepository mSystemRepository;

    public static synchronized RepositoryFactory getInstance() {
        RepositoryFactory repositoryFactory;
        synchronized (RepositoryFactory.class) {
            try {
                if (sInstance == null) {
                    RepositoryFactory repositoryFactory2 = new RepositoryFactory();
                    repositoryFactory2.mSystemRepository = null;
                    repositoryFactory2.mSettingRepository = null;
                    sInstance = repositoryFactory2;
                }
                repositoryFactory = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return repositoryFactory;
    }
}
