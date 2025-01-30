package com.android.server.chimera;

import android.content.Context;
import com.android.server.am.ActivityManagerService;

/* loaded from: classes.dex */
public class RepositoryFactory {
  static RepositoryFactory sInstance;
  SystemRepository mSystemRepository = null;
  SettingRepository mSettingRepository = null;

  public static synchronized RepositoryFactory getInstance() {
    RepositoryFactory repositoryFactory;
    synchronized (RepositoryFactory.class) {
      if (sInstance == null) {
        sInstance = new RepositoryFactory();
      }
      repositoryFactory = sInstance;
    }
    return repositoryFactory;
  }

  public synchronized SystemRepository getSystemRepository(
      Context context, ActivityManagerService activityManagerService) {
    if (this.mSystemRepository == null) {
      this.mSystemRepository = new SystemRepositoryDefault(context, activityManagerService);
    }
    return this.mSystemRepository;
  }

  public synchronized SettingRepository getSettingRepository(SystemRepository systemRepository) {
    if (this.mSettingRepository == null) {
      this.mSettingRepository = new SettingRepositoryDefault(systemRepository);
    }
    return this.mSettingRepository;
  }
}
