package com.android.systemui.media.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaOutputSwitcherDialogUI implements CoreStartable, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final MediaOutputDialogFactory mMediaOutputDialogFactory;

    public MediaOutputSwitcherDialogUI(Context context, CommandQueue commandQueue, MediaOutputDialogFactory mediaOutputDialogFactory) {
        this.mCommandQueue = commandQueue;
        this.mMediaOutputDialogFactory = mediaOutputDialogFactory;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showMediaOutputSwitcher(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e("MediaOutputSwitcherDialogUI", "Unable to launch media output dialog. Package name is empty.");
        } else {
            this.mMediaOutputDialogFactory.create(str, null);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }
}
