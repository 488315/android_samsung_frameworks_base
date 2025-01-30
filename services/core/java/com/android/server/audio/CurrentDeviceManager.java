package com.android.server.audio;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class CurrentDeviceManager {
  public static final Object lock = new Object();
  public final Set callbacks = new HashSet();

  public abstract class CallbackRecord {}

  public void changedCurrentDevice(final Set set) {
    synchronized (lock) {
      if (this.callbacks.isEmpty()) {
        return;
      }
      this.callbacks.forEach(
          new Consumer() { // from class:
            // com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              Set set2 = set;
              CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(obj);
              CurrentDeviceManager.lambda$changedCurrentDevice$0(set2, null);
            }
          });
    }
  }

  public static /* synthetic */ void lambda$changedCurrentDevice$0(
      Set set, CallbackRecord callbackRecord) {
    throw null;
  }
}
