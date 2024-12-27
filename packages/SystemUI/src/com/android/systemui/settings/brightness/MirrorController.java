package com.android.systemui.settings.brightness;

import android.view.View;
import com.android.systemui.statusbar.policy.CallbackController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface MirrorController extends CallbackController {
    void hideMirror();

    void setLocationAndSize(View view);

    void showMirror();
}
