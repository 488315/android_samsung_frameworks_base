package com.android.server.pm;

import com.samsung.android.knox.container.ContainerCreationParams;

/* loaded from: classes3.dex */
public abstract class KnoxMUMContainerPolicyInternal {
  public abstract void onNewUserCreated(int i);

  public abstract void setAppSeparationOwnership(ContainerCreationParams containerCreationParams);
}
