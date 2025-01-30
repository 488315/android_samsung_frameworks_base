package com.android.p038wm.shell.freeform;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.p038wm.shell.freeform.FreeformContainerManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformContainerItemController {
    public final FreeformContainerIconLoader mFreeformContainerIconLoader;

    /* renamed from: mH */
    public FreeformContainerManager.HandlerC3983H f446mH;
    public final List mItemList = Collections.synchronizedList(new ArrayList());
    public ThreadPoolExecutor mThreadPoolExecutor;
    public FreeformContainerViewController mViewController;

    public FreeformContainerItemController(Context context) {
        this.mFreeformContainerIconLoader = new FreeformContainerIconLoader(context);
    }

    public final void addItem(final FreeformContainerItem freeformContainerItem) {
        if (!freeformContainerItem.needLoading(this)) {
            freeformContainerItem.toString();
            return;
        }
        synchronized (this.mItemList) {
            try {
                freeformContainerItem.removeDuplicatedItemsIfExist(this);
                if (this.mItemList.size() >= 20) {
                    Log.w("FreeformContainer", "[ItemController] remove last published item because it's over the max Freeform container count");
                    int size = this.mItemList.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        FreeformContainerItem freeformContainerItem2 = (FreeformContainerItem) this.mItemList.get(size);
                        if (((FreeformContainerItem) this.mItemList.get(size)).mPublishCompleted) {
                            removeItem(freeformContainerItem2);
                            freeformContainerItem2.handleMaxItem();
                            break;
                        }
                        size--;
                    }
                }
                this.mItemList.add(freeformContainerItem);
                if (this.mItemList.size() == 1) {
                    FreeformContainerSystemProxy.mExecutor.execute(new FreeformContainerSystemProxy$$ExternalSyntheticLambda1(true));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mThreadPoolExecutor.execute(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerItemController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FreeformContainerItemController freeformContainerItemController = FreeformContainerItemController.this;
                FreeformContainerItem freeformContainerItem3 = freeformContainerItem;
                freeformContainerItemController.getClass();
                String str = freeformContainerItem3.mPackageName;
                PackageManager packageManager = freeformContainerItem3.mContext.getPackageManager();
                try {
                    freeformContainerItem3.mDescription = packageManager.getApplicationLabel(packageManager.getApplicationInfoAsUser(str, 1024, freeformContainerItem3.mUserId)).toString();
                } catch (PackageManager.NameNotFoundException e) {
                    Log.w("FreeformContainer", "load info failed! use system icon, " + str);
                    e.printStackTrace();
                }
                freeformContainerItem3.loadShowingIcon(freeformContainerItemController.mFreeformContainerIconLoader);
                freeformContainerItemController.f446mH.sendMessage(31, freeformContainerItem3);
                Log.i("FreeformContainer", "[ItemController] IconInfo is Loaded: " + freeformContainerItem3);
            }
        });
    }

    public final void animationCompleted(FreeformContainerItem freeformContainerItem) {
        Log.i("FreeformContainer", "[ItemController] animationCompleted: item: " + freeformContainerItem);
        if (this.mItemList.contains(freeformContainerItem)) {
            if (!freeformContainerItem.mAnimationCompleted) {
                freeformContainerItem.mAnimationCompleted = true;
            }
            publishItemIfNeeded(freeformContainerItem);
        } else {
            Log.w("FreeformContainer", "[ItemController] animationCompleted failed item(=" + freeformContainerItem + ") is not in list");
        }
    }

    public final FreeformContainerItem getItemById(int i) {
        Iterator it = new ArrayList(this.mItemList).iterator();
        while (it.hasNext()) {
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) it.next();
            if ((freeformContainerItem instanceof MinimizeContainerItem) && freeformContainerItem.getTaskId() == i) {
                return freeformContainerItem;
            }
        }
        return null;
    }

    public final FreeformContainerItem getItemByName(String str) {
        Iterator it = new ArrayList(this.mItemList).iterator();
        while (it.hasNext()) {
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) it.next();
            if (freeformContainerItem.mPackageName.equals(str)) {
                return freeformContainerItem;
            }
        }
        return null;
    }

    public final void publishItemIfNeeded(FreeformContainerItem freeformContainerItem) {
        List list = this.mItemList;
        if (!list.contains(freeformContainerItem)) {
            Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded: item is not in list, item=" + freeformContainerItem);
            return;
        }
        if (!(freeformContainerItem.mIconLoadCompleted && !freeformContainerItem.mPublishCompleted && freeformContainerItem.mAnimationCompleted)) {
            Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded: item is not ready, item=" + freeformContainerItem);
            return;
        }
        int indexOf = list.indexOf(freeformContainerItem);
        if (!(indexOf == 0 || ((FreeformContainerItem) list.get(indexOf + (-1))).mPublishCompleted)) {
            Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded: previous item is not published, item=" + freeformContainerItem);
            return;
        }
        list.remove(freeformContainerItem);
        list.add(0, freeformContainerItem);
        if (!freeformContainerItem.mPublishCompleted) {
            freeformContainerItem.mPublishCompleted = true;
        }
        this.f446mH.removeMessages(16, freeformContainerItem);
        Iterator it = ((ArrayList) this.mViewController.mCallBacks).iterator();
        while (it.hasNext()) {
            FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) it.next();
            Log.i("FreeformContainer", "[ViewController] onItemAdded: " + freeformContainerCallback);
            freeformContainerCallback.onItemAdded(freeformContainerItem);
        }
        Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded item=" + freeformContainerItem);
        if (indexOf < list.size() - 1) {
            publishItemIfNeeded((FreeformContainerItem) list.get(indexOf + 1));
        }
    }

    public final void removeAllMinimizeContainerItem() {
        Log.i("FreeformContainer", "[ItemController] Run removeAllMinimizeContainerItem");
        Iterator it = new ArrayList(this.mItemList).iterator();
        while (it.hasNext()) {
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) it.next();
            if (freeformContainerItem instanceof MinimizeContainerItem) {
                removeItem(freeformContainerItem);
            }
        }
    }

    public final void removeAllSmartPopupViewItem() {
        Log.i("FreeformContainer", "[ItemController] Run removeAllSmartPopupViewItem");
        Iterator it = new ArrayList(this.mItemList).iterator();
        while (it.hasNext()) {
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) it.next();
            if (freeformContainerItem instanceof SmartPopupViewItem) {
                removeItem(freeformContainerItem);
            }
        }
    }

    public final void removeItem(FreeformContainerItem freeformContainerItem) {
        Log.i("FreeformContainer", "[ItemController] Run removeItem, item=" + freeformContainerItem);
        List list = this.mItemList;
        list.remove(freeformContainerItem);
        if (freeformContainerItem.mPublishCompleted) {
            Iterator it = ((ArrayList) this.mViewController.mCallBacks).iterator();
            while (it.hasNext()) {
                FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) it.next();
                Log.i("FreeformContainer", "[ViewController] onItemRemoved: " + freeformContainerCallback);
                freeformContainerCallback.onItemRemoved(freeformContainerItem);
            }
        }
        if (list.isEmpty()) {
            FreeformContainerSystemProxy.mExecutor.execute(new FreeformContainerSystemProxy$$ExternalSyntheticLambda1(false));
        }
    }
}
