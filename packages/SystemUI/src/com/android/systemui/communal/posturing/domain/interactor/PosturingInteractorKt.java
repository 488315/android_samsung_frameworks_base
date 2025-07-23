package com.android.systemui.communal.posturing.domain.interactor;

import com.android.systemui.communal.posturing.shared.model.PosturedState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PosturingInteractorKt {
    public static final Boolean asBoolean(PosturedState posturedState) {
        if (posturedState instanceof PosturedState.Postured) {
            return Boolean.TRUE;
        }
        if (Intrinsics.areEqual(posturedState, PosturedState.NotPostured.INSTANCE)) {
            return Boolean.FALSE;
        }
        if (Intrinsics.areEqual(posturedState, PosturedState.Unknown.INSTANCE)) {
            return null;
        }
        throw new NoWhenBranchMatchedException();
    }
}
