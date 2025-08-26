package com.android.systemui.qs.tiles.base.domain.interactor;

import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public interface QSTileUserActionInteractor {
    Object handleInput(QSTileInput qSTileInput, Continuation continuation);
}
