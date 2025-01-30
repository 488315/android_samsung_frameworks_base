package com.android.server.soundtrigger;

import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
/* renamed from: com.android.server.soundtrigger.SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2 */
/* loaded from: classes3.dex */
public final /* synthetic */ class C2602x6269e71c implements Consumer {
  public final /* synthetic */ SoundTriggerHelper f$0;

  @Override // java.util.function.Consumer
  public final void accept(Object obj) {
    this.f$0.onAppOpStateChanged(((Boolean) obj).booleanValue());
  }
}
