package com.android.systemui.controls.controller;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import java.io.File;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
