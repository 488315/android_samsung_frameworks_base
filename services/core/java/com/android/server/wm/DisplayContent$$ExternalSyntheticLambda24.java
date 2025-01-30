package com.android.server.wm;

import java.util.function.BiPredicate;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda24
    implements BiPredicate {
  @Override // java.util.function.BiPredicate
  public final boolean test(Object obj, Object obj2) {
    return ((ActivityRecord) obj).isUid(((Integer) obj2).intValue());
  }
}
