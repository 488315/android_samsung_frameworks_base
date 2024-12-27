package com.android.server.updates;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
