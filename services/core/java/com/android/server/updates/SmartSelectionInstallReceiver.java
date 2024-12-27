package com.android.server.updates;

public class SmartSelectionInstallReceiver extends ConfigUpdateInstallReceiver {
    public SmartSelectionInstallReceiver() {
        super(
                "/data/misc/textclassifier/",
                "textclassifier.model",
                "metadata/classification",
                "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final boolean verifyVersion(int i, int i2) {
        return true;
    }
}
