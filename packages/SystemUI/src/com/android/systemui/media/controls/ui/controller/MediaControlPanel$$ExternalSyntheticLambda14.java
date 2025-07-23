package com.android.systemui.media.controls.ui.controller;

import android.content.Intent;
import com.android.systemui.R;
import com.android.systemui.media.controls.shared.model.MediaButton;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda14 implements Predicate {
    public final /* synthetic */ MediaButton f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        MediaButton mediaButton = this.f$0;
        Integer num = (Integer) obj;
        Intent intent = MediaControlPanel.SETTINGS_INTENT;
        if (mediaButton.getActionById(num.intValue()) != null) {
            return true;
        }
        if (num.intValue() == R.id.actionPrev && mediaButton.reservePrev) {
            return true;
        }
        return num.intValue() == R.id.actionNext && mediaButton.reserveNext;
    }
}
