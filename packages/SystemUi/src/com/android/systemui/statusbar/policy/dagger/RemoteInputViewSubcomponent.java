package com.android.systemui.statusbar.policy.dagger;

import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface RemoteInputViewSubcomponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        RemoteInputViewSubcomponent create(RemoteInputView remoteInputView, RemoteInputController remoteInputController);
    }

    RemoteInputViewController getController();
}
