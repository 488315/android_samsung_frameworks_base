package com.android.server.appfunctions;

import android.content.Intent;
import android.os.UserHandle;

public interface ServiceHelper {
    Intent resolveAppFunctionService(String str, UserHandle userHandle);
}
