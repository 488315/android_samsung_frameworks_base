package com.android.systemui.qs.tiles.base.interactor;

import kotlin.coroutines.Continuation;

public interface QSTileUserActionInteractor {
    Object handleInput(QSTileInput qSTileInput, Continuation continuation);
}
