package com.android.p038wm.shell.bubbles;

import android.content.Context;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecHideInformationMirroringController {
    public final HideInformationMirroringCallback mCallback;
    public final Context mContext;
    public final SecHideInformationMirroringModel mModel = new SecHideInformationMirroringModel();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface HideInformationMirroringCallback {
    }

    public SecHideInformationMirroringController(Context context, HideInformationMirroringCallback hideInformationMirroringCallback) {
        this.mContext = context;
        this.mCallback = hideInformationMirroringCallback;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001b, code lost:
    
        if ((android.provider.Settings.Global.getInt(r1.getContentResolver(), "smart_view_show_notification_on", 0) == 0) != false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMirroringWindowFlag() {
        boolean z;
        this.mModel.getClass();
        int i = 0;
        Context context = this.mContext;
        if (context != null) {
            z = true;
        }
        z = false;
        BubbleController bubbleController = ((BubbleController$$ExternalSyntheticLambda5) this.mCallback).f$0;
        bubbleController.getClass();
        bubbleController.mMainHandler.post(new BubbleController$$ExternalSyntheticLambda19(i, bubbleController, z));
    }
}
