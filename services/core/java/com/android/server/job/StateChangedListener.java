package com.android.server.job;

import android.net.Network;
import android.util.ArraySet;
import com.android.server.job.controllers.JobStatus;
import java.util.List;

/* loaded from: classes2.dex */
public interface StateChangedListener {
  void onControllerStateChanged(ArraySet arraySet);

  void onDeviceIdleStateChanged(boolean z);

  void onNetworkChanged(JobStatus jobStatus, Network network);

  void onRestrictedBucketChanged(List list);

  void onRunJobNow(JobStatus jobStatus);
}
