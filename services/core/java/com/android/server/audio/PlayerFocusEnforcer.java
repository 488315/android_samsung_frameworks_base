package com.android.server.audio;

/* loaded from: classes.dex */
public interface PlayerFocusEnforcer {
  boolean duckPlayers(FocusRequester focusRequester, FocusRequester focusRequester2, boolean z);

  void forgetUid(int i);

  void mutePlayersForCall(int[] iArr);

  void restoreVShapedPlayers(FocusRequester focusRequester);

  void unmutePlayersForCall();
}
