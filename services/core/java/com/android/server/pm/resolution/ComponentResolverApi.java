package com.android.server.pm.resolution;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import com.android.server.pm.Computer;
import com.android.server.pm.DumpState;
import com.android.server.pm.pkg.component.ParsedActivity;
import com.android.server.pm.pkg.component.ParsedProvider;
import com.android.server.pm.pkg.component.ParsedService;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes3.dex */
public interface ComponentResolverApi {
  boolean componentExists(ComponentName componentName);

  void dumpActivityResolvers(PrintWriter printWriter, DumpState dumpState, String str);

  void dumpContentProviders(
      Computer computer, PrintWriter printWriter, DumpState dumpState, String str);

  void dumpProviderResolvers(PrintWriter printWriter, DumpState dumpState, String str);

  void dumpReceiverResolvers(PrintWriter printWriter, DumpState dumpState, String str);

  void dumpServicePermissions(PrintWriter printWriter, DumpState dumpState);

  void dumpServiceResolvers(PrintWriter printWriter, DumpState dumpState, String str);

  ParsedActivity getActivity(ComponentName componentName);

  ParsedProvider getProvider(ComponentName componentName);

  ParsedActivity getReceiver(ComponentName componentName);

  ParsedService getService(ComponentName componentName);

  List queryActivities(Computer computer, Intent intent, String str, long j, int i);

  List queryActivities(Computer computer, Intent intent, String str, long j, List list, int i);

  ProviderInfo queryProvider(Computer computer, String str, long j, int i);

  List queryProviders(Computer computer, Intent intent, String str, long j, int i);

  List queryProviders(Computer computer, Intent intent, String str, long j, List list, int i);

  List queryProviders(Computer computer, String str, String str2, int i, long j, int i2);

  List queryReceivers(Computer computer, Intent intent, String str, long j, int i);

  List queryReceivers(Computer computer, Intent intent, String str, long j, List list, int i);

  List queryServices(Computer computer, Intent intent, String str, long j, int i);

  List queryServices(Computer computer, Intent intent, String str, long j, List list, int i);

  void querySyncProviders(Computer computer, List list, List list2, boolean z, int i);
}
