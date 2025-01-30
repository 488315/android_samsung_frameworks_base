package com.android.server.wm;

import android.os.Environment;
import java.io.File;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ActivitySnapshotController$$ExternalSyntheticLambda0
    implements BaseAppSnapshotPersister.DirectoryResolver {
  @Override // com.android.server.wm.BaseAppSnapshotPersister.DirectoryResolver
  public final File getSystemDirectoryForUser(int i) {
    return Environment.getDataSystemCeDirectory(i);
  }
}
