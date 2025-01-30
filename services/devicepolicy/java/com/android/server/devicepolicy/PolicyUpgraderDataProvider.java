package com.android.server.devicepolicy;

import com.android.internal.util.JournaledFile;
import java.util.List;
import java.util.function.Function;

/* loaded from: classes2.dex */
public interface PolicyUpgraderDataProvider {
  Function getAdminInfoSupplier(int i);

  List getPlatformSuspendedPackages(int i);

  int[] getUsersForUpgrade();

  JournaledFile makeDevicePoliciesJournaledFile(int i);

  JournaledFile makePoliciesVersionJournaledFile(int i);
}
