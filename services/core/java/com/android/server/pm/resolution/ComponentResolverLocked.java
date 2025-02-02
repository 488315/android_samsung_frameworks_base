package com.android.server.pm.resolution;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import com.android.server.pm.Computer;
import com.android.server.pm.DumpState;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.pkg.component.ParsedActivity;
import com.android.server.pm.pkg.component.ParsedProvider;
import com.android.server.pm.pkg.component.ParsedService;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class ComponentResolverLocked extends ComponentResolverBase {
  public final PackageManagerTracedLock mLock;

  public ComponentResolverLocked(UserManagerService userManagerService) {
    super(userManagerService);
    this.mLock = new PackageManagerTracedLock();
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public boolean componentExists(ComponentName componentName) {
    boolean componentExists;
    synchronized (this.mLock) {
      componentExists = super.componentExists(componentName);
    }
    return componentExists;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public ParsedActivity getActivity(ComponentName componentName) {
    ParsedActivity activity;
    synchronized (this.mLock) {
      activity = super.getActivity(componentName);
    }
    return activity;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public ParsedProvider getProvider(ComponentName componentName) {
    ParsedProvider provider;
    synchronized (this.mLock) {
      provider = super.getProvider(componentName);
    }
    return provider;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public ParsedActivity getReceiver(ComponentName componentName) {
    ParsedActivity receiver;
    synchronized (this.mLock) {
      receiver = super.getReceiver(componentName);
    }
    return receiver;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public ParsedService getService(ComponentName componentName) {
    ParsedService service;
    synchronized (this.mLock) {
      service = super.getService(componentName);
    }
    return service;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase
  public boolean isActivityDefined(ComponentName componentName) {
    boolean isActivityDefined;
    synchronized (this.mLock) {
      isActivityDefined = super.isActivityDefined(componentName);
    }
    return isActivityDefined;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryActivities(Computer computer, Intent intent, String str, long j, int i) {
    List queryActivities;
    synchronized (this.mLock) {
      queryActivities = super.queryActivities(computer, intent, str, j, i);
    }
    return queryActivities;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryActivities(
      Computer computer, Intent intent, String str, long j, List list, int i) {
    List queryActivities;
    synchronized (this.mLock) {
      queryActivities = super.queryActivities(computer, intent, str, j, list, i);
    }
    return queryActivities;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public ProviderInfo queryProvider(Computer computer, String str, long j, int i) {
    ProviderInfo queryProvider;
    synchronized (this.mLock) {
      queryProvider = super.queryProvider(computer, str, j, i);
    }
    return queryProvider;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryProviders(Computer computer, Intent intent, String str, long j, int i) {
    List queryProviders;
    synchronized (this.mLock) {
      queryProviders = super.queryProviders(computer, intent, str, j, i);
    }
    return queryProviders;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryProviders(
      Computer computer, Intent intent, String str, long j, List list, int i) {
    List queryProviders;
    synchronized (this.mLock) {
      queryProviders = super.queryProviders(computer, intent, str, j, list, i);
    }
    return queryProviders;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryProviders(Computer computer, String str, String str2, int i, long j, int i2) {
    List queryProviders;
    synchronized (this.mLock) {
      queryProviders = super.queryProviders(computer, str, str2, i, j, i2);
    }
    return queryProviders;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryReceivers(Computer computer, Intent intent, String str, long j, int i) {
    List queryReceivers;
    synchronized (this.mLock) {
      queryReceivers = super.queryReceivers(computer, intent, str, j, i);
    }
    return queryReceivers;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryReceivers(
      Computer computer, Intent intent, String str, long j, List list, int i) {
    List queryReceivers;
    synchronized (this.mLock) {
      queryReceivers = super.queryReceivers(computer, intent, str, j, list, i);
    }
    return queryReceivers;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryServices(Computer computer, Intent intent, String str, long j, int i) {
    List queryServices;
    synchronized (this.mLock) {
      queryServices = super.queryServices(computer, intent, str, j, i);
    }
    return queryServices;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public List queryServices(
      Computer computer, Intent intent, String str, long j, List list, int i) {
    List queryServices;
    synchronized (this.mLock) {
      queryServices = super.queryServices(computer, intent, str, j, list, i);
    }
    return queryServices;
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public void querySyncProviders(Computer computer, List list, List list2, boolean z, int i) {
    synchronized (this.mLock) {
      super.querySyncProviders(computer, list, list2, z, i);
    }
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public void dumpActivityResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
    synchronized (this.mLock) {
      super.dumpActivityResolvers(printWriter, dumpState, str);
    }
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public void dumpProviderResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
    synchronized (this.mLock) {
      super.dumpProviderResolvers(printWriter, dumpState, str);
    }
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public void dumpReceiverResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
    synchronized (this.mLock) {
      super.dumpReceiverResolvers(printWriter, dumpState, str);
    }
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public void dumpServiceResolvers(PrintWriter printWriter, DumpState dumpState, String str) {
    synchronized (this.mLock) {
      super.dumpServiceResolvers(printWriter, dumpState, str);
    }
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public void dumpContentProviders(
      Computer computer, PrintWriter printWriter, DumpState dumpState, String str) {
    synchronized (this.mLock) {
      super.dumpContentProviders(computer, printWriter, dumpState, str);
    }
  }

  @Override // com.android.server.pm.resolution.ComponentResolverBase,
            // com.android.server.pm.resolution.ComponentResolverApi
  public void dumpServicePermissions(PrintWriter printWriter, DumpState dumpState) {
    synchronized (this.mLock) {
      super.dumpServicePermissions(printWriter, dumpState);
    }
  }
}
