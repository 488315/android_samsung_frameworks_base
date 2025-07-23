package android.content;

import android.annotation.SystemApi;
import android.os.Environment;
import android.os.UserHandle;
import java.io.File;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public class ApexEnvironment {
    private static final String APEX_DATA = "apexdata";
    private final String mApexModuleName;

    public static ApexEnvironment getApexEnvironment(String str) {
        Objects.requireNonNull(str, "apexModuleName cannot be null");
        return new ApexEnvironment(str);
    }

    private ApexEnvironment(String str) {
        this.mApexModuleName = str;
    }

    public File getDeviceProtectedDataDir() {
        return Environment.buildPath(Environment.getDataMiscDirectory(), APEX_DATA, this.mApexModuleName);
    }

    public File getDeviceProtectedDataDirForUser(UserHandle userHandle) {
        return Environment.buildPath(Environment.getDataMiscDeDirectory(userHandle.getIdentifier()), APEX_DATA, this.mApexModuleName);
    }

    public File getCredentialProtectedDataDirForUser(UserHandle userHandle) {
        return Environment.buildPath(Environment.getDataMiscCeDirectory(userHandle.getIdentifier()), APEX_DATA, this.mApexModuleName);
    }
}
