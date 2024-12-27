package com.android.systemui.settings.brightness;

import android.view.View;
import com.android.systemui.statusbar.policy.CallbackController;

public interface MirrorController extends CallbackController {
    void hideMirror();

    void setLocationAndSize(View view);

    void showMirror();
}
