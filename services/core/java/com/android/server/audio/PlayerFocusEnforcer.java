package com.android.server.audio;

import android.media.AudioAttributes;

public interface PlayerFocusEnforcer {
    boolean duckPlayers(FocusRequester focusRequester, FocusRequester focusRequester2, boolean z);

    boolean fadeOutPlayers(FocusRequester focusRequester, FocusRequester focusRequester2);

    void forgetUid(int i);

    long getFadeInDelayForOffendersMillis(AudioAttributes audioAttributes);

    long getFadeOutDurationMillis(AudioAttributes audioAttributes);

    void mutePlayersForCall(int[] iArr);

    void restoreVShapedPlayers(FocusRequester focusRequester);

    boolean shouldEnforceFade();

    void unmutePlayersForCall();
}
