package android.service.controls.actions;

import android.os.Bundle;

/* loaded from: classes3.dex */
public final class CommandAction extends ControlAction {
    private static final int TYPE = 5;

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 5;
    }

    public CommandAction(String str, String str2) {
        super(str, str2);
    }

    public CommandAction(String str) {
        this(str, null);
    }

    CommandAction(Bundle bundle) {
        super(bundle);
    }
}
