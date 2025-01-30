package com.android.server.wm;

import com.android.internal.util.function.QuadConsumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class WindowProcessController$$ExternalSyntheticLambda5
    implements QuadConsumer {
  public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
    ((WindowProcessListener) obj)
        .updateProcessInfo(
            ((Boolean) obj2).booleanValue(),
            ((Boolean) obj3).booleanValue(),
            ((Boolean) obj4).booleanValue());
  }
}
