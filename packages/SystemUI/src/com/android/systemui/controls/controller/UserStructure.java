package com.android.systemui.controls.controller;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import java.io.File;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
