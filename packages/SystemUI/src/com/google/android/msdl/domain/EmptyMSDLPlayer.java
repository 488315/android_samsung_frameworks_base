package com.google.android.msdl.domain;

import com.google.android.msdl.data.model.MSDLToken;
import java.util.List;
import kotlin.collections.EmptyList;

/* loaded from: classes4.dex */
public final class EmptyMSDLPlayer implements MSDLPlayer {
    @Override // com.google.android.msdl.domain.MSDLPlayer
    public final List getHistory() {
        return EmptyList.INSTANCE;
    }

    public final String toString() {
        return "Empty MSDL player without a vibrator.";
    }

    @Override // com.google.android.msdl.domain.MSDLPlayer
    public final void playToken(MSDLToken mSDLToken, InteractionProperties interactionProperties) {
    }
}
