package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.shared.model.MediaButton;
import java.util.function.Predicate;

public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda11 implements Predicate {
    public final /* synthetic */ MediaButton f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.getActionById(((Integer) obj).intValue()) != null;
    }
}
