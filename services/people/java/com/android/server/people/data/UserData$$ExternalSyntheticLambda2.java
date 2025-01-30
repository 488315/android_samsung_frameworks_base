package com.android.server.people.data;

import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserData$$ExternalSyntheticLambda2 implements Predicate {
  public final /* synthetic */ UserData f$0;

  public /* synthetic */ UserData$$ExternalSyntheticLambda2(UserData userData) {
    this.f$0 = userData;
  }

  @Override // java.util.function.Predicate
  public final boolean test(Object obj) {
    boolean isDefaultSmsApp;
    isDefaultSmsApp = this.f$0.isDefaultSmsApp((String) obj);
    return isDefaultSmsApp;
  }
}
