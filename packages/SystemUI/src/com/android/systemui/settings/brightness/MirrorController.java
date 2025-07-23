package com.android.systemui.settings.brightness;

import android.view.View;
import com.android.systemui.statusbar.policy.CallbackController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface MirrorController extends CallbackController {
    void hideMirror();

    void setLocationAndSize(View view);

    void showMirror();
}
