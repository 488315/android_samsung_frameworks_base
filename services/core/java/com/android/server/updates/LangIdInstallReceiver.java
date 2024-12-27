package com.android.server.updates;

public class LangIdInstallReceiver extends ConfigUpdateInstallReceiver {
    public LangIdInstallReceiver() {
        super("/data/misc/textclassifier/", "lang_id.model", "metadata/lang_id", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final boolean verifyVersion(int i, int i2) {
        return true;
    }
}
