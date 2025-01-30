package com.samsung.android.sdk.command.action;

import android.os.Bundle;
import android.util.Log;
import java.util.UUID;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CommandAction {
    public static final C47551 ERROR_ACTION = new CommandAction() { // from class: com.samsung.android.sdk.command.action.CommandAction.1
        @Override // com.samsung.android.sdk.command.action.CommandAction
        public final int getActionType() {
            return 0;
        }
    };
    public String mActionId;
    public final CommandParam mCommandParam;
    public final String mTemplateId;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.sdk.command.action.CommandAction$1] */
    static {
        new CommandAction() { // from class: com.samsung.android.sdk.command.action.CommandAction.2
            @Override // com.samsung.android.sdk.command.action.CommandAction
            public final int getActionType() {
                return 98;
            }
        };
    }

    public CommandAction() {
        this.mTemplateId = "";
        this.mCommandParam = new CommandParam();
    }

    public static CommandAction createActionFromBundle(Bundle bundle) {
        C47551 c47551 = ERROR_ACTION;
        if (bundle == null) {
            return c47551;
        }
        int i = bundle.getInt("key_action_type", 0);
        try {
            if (i == 98) {
                return new DefaultAction(bundle);
            }
            if (i == 99) {
                return new TriggerAction(bundle);
            }
            switch (i) {
            }
            return c47551;
        }
    }

    public String getActionTemplateId() {
        return this.mTemplateId;
    }

    public abstract int getActionType();

    public Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        if (this.mActionId == null) {
            this.mActionId = UUID.randomUUID().toString();
        }
        bundle.putString("key_action_id", this.mActionId);
        bundle.putInt("key_action_type", getActionType());
        bundle.putString("key_template_id", getActionTemplateId());
        CommandParam commandParam = this.mCommandParam;
        commandParam.getClass();
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("dex_mode", commandParam.mDexMode);
        bundle.putBundle("command_param", bundle2);
        return bundle;
    }

    public CommandAction(Bundle bundle) {
        this.mActionId = bundle.getString("key_action_id");
        this.mTemplateId = bundle.getString("key_template_id");
        CommandParam commandParam = new CommandParam();
        Bundle bundle2 = bundle.getBundle("command_param");
        if (bundle2 == null) {
            Log.w("CommandLib/CommandParam", "commandParamBundle is empty");
        } else {
            commandParam.mDexMode = bundle2.getBoolean("dex_mode", false);
        }
        this.mCommandParam = commandParam;
    }

    public CommandAction(String str) {
        this.mTemplateId = str;
        this.mCommandParam = new CommandParam();
    }
}
