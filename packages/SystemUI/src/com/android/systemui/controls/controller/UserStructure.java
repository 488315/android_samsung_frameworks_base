package com.android.systemui.controls.controller;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import java.io.File;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserStructure {
    public final File auxiliaryFile;
    public final File file;
    public final Context userContext;

    public UserStructure(Context context, UserHandle userHandle, UserFileManager userFileManager) {
        this.userContext = context.createContextAsUser(userHandle, 0);
        UserFileManagerImpl userFileManagerImpl = (UserFileManagerImpl) userFileManager;
        this.file = userFileManagerImpl.getFile(userHandle.getIdentifier(), "controls_favorites.xml");
        this.auxiliaryFile = userFileManagerImpl.getFile(userHandle.getIdentifier(), "aux_controls_favorites.xml");
    }
}
