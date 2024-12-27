package com.android.server.audio;

import android.media.AudioAttributes;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
