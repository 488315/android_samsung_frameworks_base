package com.android.server.updates;

public class ConversationActionsInstallReceiver extends ConfigUpdateInstallReceiver {
    public ConversationActionsInstallReceiver() {
        super(
                "/data/misc/textclassifier/",
                "actions_suggestions.model",
                "metadata/actions_suggestions",
                "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final boolean verifyVersion(int i, int i2) {
        return true;
    }
}
