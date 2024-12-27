package com.android.systemui.media.dialog;

import android.content.Context;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaOutputSwitcherDialogUI implements CoreStartable, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final MediaOutputDialogManager mMediaOutputDialogManager;

    public MediaOutputSwitcherDialogUI(Context context, CommandQueue commandQueue, MediaOutputDialogManager mediaOutputDialogManager) {
        this.mCommandQueue = commandQueue;
        this.mMediaOutputDialogManager = mediaOutputDialogManager;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showMediaOutputSwitcher(String str, UserHandle userHandle) {
        if (TextUtils.isEmpty(str)) {
            Log.e("MediaOutputSwitcherDialogUI", "Unable to launch media output dialog. Package name is empty.");
        } else {
            this.mMediaOutputDialogManager.createAndShow(str, false, null, true, userHandle, null);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }
}
