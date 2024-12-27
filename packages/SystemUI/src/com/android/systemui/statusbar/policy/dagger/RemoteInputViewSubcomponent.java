package com.android.systemui.statusbar.policy.dagger;

import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;

public interface RemoteInputViewSubcomponent {

    public interface Factory {
        RemoteInputViewSubcomponent create(RemoteInputView remoteInputView, RemoteInputController remoteInputController);
    }

    RemoteInputViewController getController();
}
