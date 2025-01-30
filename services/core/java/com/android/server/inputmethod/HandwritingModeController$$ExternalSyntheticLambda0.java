package com.android.server.inputmethod;

import android.view.MotionEvent;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class HandwritingModeController$$ExternalSyntheticLambda0
    implements Consumer {
  @Override // java.util.function.Consumer
  public final void accept(Object obj) {
    ((MotionEvent) obj).recycle();
  }
}
