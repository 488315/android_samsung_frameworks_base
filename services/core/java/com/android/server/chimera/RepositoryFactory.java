package com.android.server.chimera;

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
