package com.android.server.am;

import android.os.IBinder;
import com.android.internal.util.function.TriConsumer;
import java.lang.ref.WeakReference;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class PendingIntentController$$ExternalSyntheticLambda0
    implements TriConsumer {
  public final void accept(Object obj, Object obj2, Object obj3) {
    ((PendingIntentController) obj)
        .clearPendingResultForActivity((IBinder) obj2, (WeakReference) obj3);
  }
}
