package com.android.systemui.media.dialog;

import android.app.KeyguardManager;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.media.dialog.MediaOutputAdapterLegacy;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        KeyguardManager keyguardManager;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                MediaSwitchingController mediaSwitchingController = (MediaSwitchingController) obj;
                DialogTransitionAnimator dialogTransitionAnimator = mediaSwitchingController.mDialogTransitionAnimator;
                dialogTransitionAnimator.getClass();
                DialogTransitionAnimator.AnonymousClass1 anonymousClass1CreateActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
                if (anonymousClass1CreateActivityTransitionController$default == null || ((keyguardManager = mediaSwitchingController.mKeyGuardManager) != null && keyguardManager.isKeyguardLocked())) {
                    mediaSwitchingController.mCallback.dismissDialog();
                }
                Intent intentAddFlags = new Intent("android.settings.BLUETOOTH_SETTINGS").addFlags(335544320);
                Intent intent = new Intent("android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
                if (intent.resolveActivity(mediaSwitchingController.mContext.getPackageManager()) == null) {
                    mediaSwitchingController.startActivity(intentAddFlags, anonymousClass1CreateActivityTransitionController$default);
                    break;
                } else {
                    Log.d("MediaSwitchingController", "Device support split mode, launch page with deep link");
                    intent.setFlags(268435456);
                    intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI", intentAddFlags.toUri(1));
                    intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY", "top_level_connected_devices");
                    mediaSwitchingController.startActivity(intent, anonymousClass1CreateActivityTransitionController$default);
                    break;
                }
                break;
            default:
                int i2 = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.$r8$clinit;
                MediaOutputAdapterBase mediaOutputAdapterBase = MediaOutputAdapterBase.this;
                mediaOutputAdapterBase.mShouldGroupSelectedMediaItems = false;
                mediaOutputAdapterBase.notifyDataSetChanged();
                break;
        }
    }
}
