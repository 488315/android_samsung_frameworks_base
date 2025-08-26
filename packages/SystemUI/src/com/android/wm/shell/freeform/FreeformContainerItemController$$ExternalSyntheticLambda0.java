package com.android.wm.shell.freeform;

import android.content.pm.PackageManager;
import android.util.Log;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerItemController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FreeformContainerItemController f$0;
    public final /* synthetic */ FreeformContainerItem f$1;

    public /* synthetic */ FreeformContainerItemController$$ExternalSyntheticLambda0(FreeformContainerItemController freeformContainerItemController, FreeformContainerItem freeformContainerItem) {
        this.f$0 = freeformContainerItemController;
        this.f$1 = freeformContainerItem;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FreeformContainerItemController freeformContainerItemController = this.f$0;
        FreeformContainerItem freeformContainerItem = this.f$1;
        freeformContainerItemController.getClass();
        String str = freeformContainerItem.mPackageName;
        PackageManager packageManager = freeformContainerItem.mContext.getPackageManager();
        try {
            freeformContainerItem.mDescription = packageManager.getApplicationLabel(packageManager.getApplicationInfoAsUser(str, 1024, freeformContainerItem.mUserId)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("FreeformContainer", "load info failed! use system icon, " + str);
            e.printStackTrace();
        }
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && (freeformContainerItem instanceof MultiInstanceItem) && freeformContainerItem.getItemList() != null) {
            synchronized (freeformContainerItem.getItemList()) {
                try {
                    Iterator it = freeformContainerItem.getItemList().iterator();
                    while (it.hasNext()) {
                        ((FreeformContainerItem) it.next()).loadShowingIcon(freeformContainerItemController.mFreeformContainerIconLoader);
                    }
                } finally {
                }
            }
        }
        freeformContainerItem.loadShowingIcon(freeformContainerItemController.mFreeformContainerIconLoader);
        freeformContainerItemController.mH.sendMessage(31, freeformContainerItem);
        Log.i("FreeformContainer", "[ItemController] IconInfo is Loaded: " + freeformContainerItem);
    }
}
