package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ChangeTransitionController$$ExternalSyntheticLambda1
    implements Predicate {
  @Override // java.util.function.Predicate
  public final boolean test(Object obj) {
    return ((ActivityRecord) obj).canCaptureSnapshot();
  }
}
