package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.shared.model.MediaButton;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda11 implements Predicate {
    public final /* synthetic */ MediaButton f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.getActionById(((Integer) obj).intValue()) != null;
    }
}
