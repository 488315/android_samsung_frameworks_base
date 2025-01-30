package com.android.server;

import android.os.UEventObserver;

/* loaded from: classes.dex */
public abstract class ExtconStateObserver extends ExtconUEventObserver {
  public abstract Object parseState(ExtconUEventObserver.ExtconInfo extconInfo, String str);

  public abstract void updateState(
      ExtconUEventObserver.ExtconInfo extconInfo, String str, Object obj);

  @Override // com.android.server.ExtconUEventObserver
  public void onUEvent(ExtconUEventObserver.ExtconInfo extconInfo, UEventObserver.UEvent uEvent) {
    String str = uEvent.get("NAME");
    Object parseState = parseState(extconInfo, uEvent.get("STATE"));
    if (parseState != null) {
      updateState(extconInfo, str, parseState);
    }
  }
}
