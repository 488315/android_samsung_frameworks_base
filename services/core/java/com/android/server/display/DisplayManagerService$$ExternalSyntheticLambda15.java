package com.android.server.display;

import android.R;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.os.UserHandle;
import android.widget.Toast;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda15
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayManagerService f$0;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda15(
            DisplayManagerService displayManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = displayManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DisplayManagerService displayManagerService = this.f$0;
        switch (i) {
            case 0:
                Toast makeText =
                        Toast.makeText(
                                displayManagerService.mContext.createDisplayContext(
                                        ((DisplayManager)
                                                        displayManagerService.mContext
                                                                .getSystemService(
                                                                        DisplayManager.class))
                                                .getDisplay(0)),
                                displayManagerService
                                        .mContext
                                        .getResources()
                                        .getString(R.string.miniresolver_open_work),
                                0);
                makeText.semSetPreferredDisplayType(0);
                makeText.show();
                break;
            case 1:
                displayManagerService.mContext.sendBroadcastAsUser(
                        new Intent("com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW"),
                        UserHandle.ALL);
                break;
            case 2:
                displayManagerService.getClass();
                displayManagerService.mContext.registerReceiver(
                        displayManagerService.mRotationChangeReceiver,
                        new IntentFilter("com.samsung.intent.action.ROTATION_CHANGED"),
                        2);
                break;
            case 3:
                displayManagerService.mContext.sendBroadcastAsUser(
                        new Intent("com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW"),
                        UserHandle.ALL);
                break;
            case 4:
                Toast.makeText(
                                displayManagerService.mContext.createDisplayContext(
                                        ((DisplayManager)
                                                        displayManagerService.mContext
                                                                .getSystemService(
                                                                        DisplayManager.class))
                                                .getDisplay(0)),
                                displayManagerService
                                        .mContext
                                        .getResources()
                                        .getString(R.string.miniresolver_open_work),
                                0)
                        .semSetPreferredDisplayType(0);
                break;
            default:
                Toast makeText2 =
                        Toast.makeText(
                                displayManagerService.mContext.createDisplayContext(
                                        ((DisplayManager)
                                                        displayManagerService.mContext
                                                                .getSystemService(
                                                                        DisplayManager.class))
                                                .getDisplay(0)),
                                displayManagerService
                                        .mContext
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .miniresolver_private_space_messages_information),
                                0);
                makeText2.semSetPreferredDisplayType(0);
                makeText2.show();
                break;
        }
    }
}
