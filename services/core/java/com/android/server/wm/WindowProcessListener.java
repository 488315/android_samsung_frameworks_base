package com.android.server.wm;

import android.util.proto.ProtoOutputStream;

/* loaded from: classes3.dex */
public interface WindowProcessListener {
  void appDied(String str);

  void clearProfilerIfNeeded();

  void dumpDebug(ProtoOutputStream protoOutputStream, long j);

  long getCpuTime();

  boolean isRemoved();

  void onStartActivity(int i, boolean z, String str, long j);

  void setClearWaitingToKill();

  void setPendingUiClean(boolean z);

  void setPendingUiCleanAndForceProcessStateUpTo(int i);

  void setRunningRemoteAnimation(boolean z);

  boolean skipToFinishActivities();

  void updateProcessInfo(boolean z, boolean z2, boolean z3);

  void updateServiceConnectionActivities();
}
