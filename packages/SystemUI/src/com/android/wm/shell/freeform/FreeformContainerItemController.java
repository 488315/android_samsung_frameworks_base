package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.RemoteException;
import android.util.Log;
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes3.dex */
public class FreeformContainerItemController {
    public final FreeformContainerIconLoader mFreeformContainerIconLoader;
    public FreeformContainerManager.H mH;
    public final List mItemList = Collections.synchronizedList(new ArrayList());
    public ThreadPoolExecutor mThreadPoolExecutor;
    public FreeformContainerViewController mViewController;

    public FreeformContainerItemController(Context context) {
        this.mFreeformContainerIconLoader = new FreeformContainerIconLoader(context);
    }

    public final void addItem(FreeformContainerItem freeformContainerItem) {
        if (!freeformContainerItem.needLoading(this)) {
            freeformContainerItem.toString();
            return;
        }
        synchronized (this.mItemList) {
            try {
                removeAllItemsWithType(freeformContainerItem);
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
                            break;
                        }
                        size--;
                    }
                }
                this.mItemList.add(freeformContainerItem);
                if (this.mItemList.size() == 1) {
                    FreeformContainerSystemProxy.mExecutor.execute(new FreeformContainerSystemProxy$$ExternalSyntheticLambda0(true));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mThreadPoolExecutor.execute(new FreeformContainerItemController$$ExternalSyntheticLambda0(this, freeformContainerItem));
    }

    public final void animationCompleted(FreeformContainerItem freeformContainerItem) {
        Log.i("FreeformContainer", "[ItemController] animationCompleted: item: " + freeformContainerItem);
        freeformContainerItem.getClass();
        if ((freeformContainerItem instanceof MultiInstanceItem) || this.mItemList.contains(freeformContainerItem)) {
            if (!freeformContainerItem.mAnimationCompleted) {
                freeformContainerItem.mAnimationCompleted = true;
            }
            publishItemIfNeeded(freeformContainerItem);
        } else {
            Log.w("FreeformContainer", "[ItemController] animationCompleted failed item(=" + freeformContainerItem + ") is not in list");
        }
    }

    public final FreeformContainerItem getItemById(int i) {
        ArrayList arrayList = new ArrayList(this.mItemList);
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) obj;
            if ((freeformContainerItem instanceof MinimizeContainerItem) && ((MinimizeContainerItem) freeformContainerItem).mTaskId == i) {
                return freeformContainerItem;
            }
            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                freeformContainerItem.getClass();
                if (freeformContainerItem instanceof MultiInstanceItem) {
                    for (FreeformContainerItem freeformContainerItem2 : freeformContainerItem.getItemList()) {
                        if (freeformContainerItem2.getTaskId() == i) {
                            return freeformContainerItem2;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    public final FreeformContainerItem getItemByName(String str) {
        ArrayList arrayList = new ArrayList(this.mItemList);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) obj;
            if (freeformContainerItem.mPackageName.equals(str)) {
                return freeformContainerItem;
            }
        }
        return null;
    }

    public final void iconLoadCompleted(FreeformContainerItem freeformContainerItem) {
        Log.i("FreeformContainer", "[ItemController] iconLoadCompleted: item=" + freeformContainerItem);
        if ((freeformContainerItem instanceof MultiInstanceItem) || this.mItemList.contains(freeformContainerItem)) {
            if (!freeformContainerItem.mIconLoadCompleted) {
                freeformContainerItem.mIconLoadCompleted = true;
            }
            publishItemIfNeeded(freeformContainerItem);
        } else {
            Log.w("FreeformContainer", "[ItemController] iconLoadCompleted failed item(=" + freeformContainerItem + ") is not in list");
        }
    }

    public final void publishItemIfNeeded(FreeformContainerItem freeformContainerItem) {
        int iIndexOf;
        freeformContainerItem.getClass();
        boolean z = freeformContainerItem instanceof MultiInstanceItem;
        if (!z && !this.mItemList.contains(freeformContainerItem)) {
            Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded: item is not in list, item=" + freeformContainerItem);
            return;
        }
        if (!freeformContainerItem.mIconLoadCompleted || freeformContainerItem.mPublishCompleted || !freeformContainerItem.mAnimationCompleted) {
            Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded: item is not ready, item=" + freeformContainerItem);
            return;
        }
        int i = 0;
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && z) {
            int taskId = freeformContainerItem.getTaskId();
            ArrayList arrayList = new ArrayList(this.mItemList);
            int size = arrayList.size();
            iIndexOf = 0;
            int i2 = 0;
            loop0: while (true) {
                if (i2 >= size) {
                    iIndexOf = 0;
                    break;
                }
                Object obj = arrayList.get(i2);
                i2++;
                FreeformContainerItem freeformContainerItem2 = (FreeformContainerItem) obj;
                freeformContainerItem2.getClass();
                if (freeformContainerItem2 instanceof MultiInstanceItem) {
                    Iterator it = freeformContainerItem2.getItemList().iterator();
                    while (it.hasNext()) {
                        if (((FreeformContainerItem) it.next()).getTaskId() == taskId) {
                            break loop0;
                        }
                    }
                }
                iIndexOf++;
            }
        } else {
            iIndexOf = this.mItemList.indexOf(freeformContainerItem);
        }
        if (iIndexOf != 0 && !((FreeformContainerItem) this.mItemList.get(iIndexOf - 1)).mPublishCompleted) {
            Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded: previous item is not published, item=" + freeformContainerItem);
            return;
        }
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && z) {
            ArrayList arrayList2 = new ArrayList(this.mItemList);
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                FreeformContainerItem freeformContainerItem3 = (FreeformContainerItem) obj2;
                freeformContainerItem3.getClass();
                if (freeformContainerItem3 instanceof MultiInstanceItem) {
                    Iterator it2 = freeformContainerItem3.getItemList().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (((FreeformContainerItem) it2.next()).getTaskId() == freeformContainerItem.getTaskId()) {
                            this.mItemList.remove(freeformContainerItem3);
                            this.mItemList.add(0, freeformContainerItem3);
                            if (!freeformContainerItem3.mPublishCompleted) {
                                freeformContainerItem3.mPublishCompleted = true;
                            }
                        }
                    }
                }
            }
        } else {
            this.mItemList.remove(freeformContainerItem);
            this.mItemList.add(0, freeformContainerItem);
        }
        if (!freeformContainerItem.mPublishCompleted) {
            freeformContainerItem.mPublishCompleted = true;
        }
        this.mH.removeMessages(16, freeformContainerItem);
        ArrayList arrayList3 = (ArrayList) this.mViewController.mCallBacks;
        int size3 = arrayList3.size();
        while (i < size3) {
            Object obj3 = arrayList3.get(i);
            i++;
            FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) obj3;
            Log.i("FreeformContainer", "[ViewController] onItemAdded: " + freeformContainerCallback);
            freeformContainerCallback.onItemAdded(freeformContainerItem);
        }
        Log.i("FreeformContainer", "[ItemController] publishItemIfNeeded item=" + freeformContainerItem);
        if (iIndexOf < this.mItemList.size() - 1) {
            publishItemIfNeeded((FreeformContainerItem) this.mItemList.get(iIndexOf + 1));
        }
    }

    public final void removeAllItemsWithType(FreeformContainerItem freeformContainerItem) {
        ArrayList arrayList = new ArrayList(this.mItemList);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FreeformContainerItem freeformContainerItem2 = (FreeformContainerItem) obj;
            int i2 = freeformContainerItem2.mItemType;
            int i3 = freeformContainerItem.mItemType;
            if (i3 == i2) {
                if (i3 == 1) {
                    if (freeformContainerItem.getTaskId() == freeformContainerItem2.getTaskId()) {
                        removeItem(freeformContainerItem2);
                    }
                } else if (i3 == 2 && freeformContainerItem.mPackageName.equals(freeformContainerItem2.mPackageName)) {
                    removeItem(freeformContainerItem2);
                }
            }
        }
    }

    public final void removeAllMinimizeContainerItem() {
        Log.i("FreeformContainer", "[ItemController] Run removeAllMinimizeContainerItem");
        ArrayList arrayList = new ArrayList(this.mItemList);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) obj;
            if (freeformContainerItem instanceof MinimizeContainerItem) {
                removeItem(freeformContainerItem);
            }
        }
    }

    public final void removeAllSmartPopupViewItem() {
        Log.i("FreeformContainer", "[ItemController] Run removeAllSmartPopupViewItem");
        ArrayList arrayList = new ArrayList(this.mItemList);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) obj;
            if (freeformContainerItem instanceof SmartPopupViewItem) {
                removeItem(freeformContainerItem);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void removeItem(FreeformContainerItem freeformContainerItem) {
        MultiInstanceItem multiInstanceItem;
        Log.i("FreeformContainer", "[ItemController] Run removeItem, item=" + freeformContainerItem);
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
            freeformContainerItem.getClass();
            if (freeformContainerItem instanceof MultiInstanceItem) {
                int taskId = freeformContainerItem.getTaskId();
                Iterator it = this.mItemList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    FreeformContainerItem freeformContainerItem2 = (FreeformContainerItem) it.next();
                    MultiInstanceItem multiInstanceItemAsMultiInstanceItem = freeformContainerItem2.asMultiInstanceItem();
                    if (multiInstanceItemAsMultiInstanceItem != null) {
                        int size = multiInstanceItemAsMultiInstanceItem.mChildItemList.size();
                        while (true) {
                            size--;
                            if (size < 0) {
                                multiInstanceItem = null;
                                break;
                            } else {
                                multiInstanceItem = (MultiInstanceItem) multiInstanceItemAsMultiInstanceItem.mChildItemList.get(size);
                                if (multiInstanceItem.mTaskId == taskId) {
                                    break;
                                }
                            }
                        }
                        if (multiInstanceItem != null) {
                            if (multiInstanceItemAsMultiInstanceItem.mChildItemList.contains(multiInstanceItem)) {
                                if (multiInstanceItem.mSnapshotBitmap != null) {
                                    multiInstanceItem.mSnapshotBitmap = null;
                                    Log.d("FreeformContainer", "[MultiInstanceItem] setSnapshotBitmap: " + multiInstanceItem);
                                }
                                multiInstanceItemAsMultiInstanceItem.mChildItemList.remove(multiInstanceItem);
                                Log.d("FreeformContainer", "removeChildItem: " + multiInstanceItem + ", this=" + multiInstanceItemAsMultiInstanceItem);
                            } else {
                                Log.w("FreeformContainer", "[MultiInstanceItem]  removeChildItem: failed, not exist, " + multiInstanceItem);
                            }
                            this.mViewController.notifyItemRemoved(multiInstanceItem);
                        }
                        if (freeformContainerItem2.mPublishCompleted && freeformContainerItem2.getItemCount() == 0) {
                            this.mItemList.remove(freeformContainerItem2);
                            this.mViewController.notifyItemRemoved(freeformContainerItem2);
                            break;
                        }
                    }
                }
            } else {
                this.mItemList.remove(freeformContainerItem);
                if (freeformContainerItem.mPublishCompleted) {
                    this.mViewController.notifyItemRemoved(freeformContainerItem);
                }
            }
        }
        if (this.mItemList.isEmpty()) {
            FreeformContainerSystemProxy.mExecutor.execute(new FreeformContainerSystemProxy$$ExternalSyntheticLambda0(false));
        }
    }

    public final void restoreMinimizeContainerItems(Context context) {
        Context context2;
        ActivityInfo activityInfo;
        this.mItemList.clear();
        List<ActivityManager.RunningTaskInfo> minimizedFreeformTasksForCurrentUser = MultiWindowManager.getInstance().getMinimizedFreeformTasksForCurrentUser();
        if (minimizedFreeformTasksForCurrentUser == null || minimizedFreeformTasksForCurrentUser.isEmpty()) {
            return;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : minimizedFreeformTasksForCurrentUser) {
            try {
                activityInfo = AppGlobals.getPackageManager().getActivityInfo(runningTaskInfo.realActivity, 128L, runningTaskInfo.userId);
            } catch (RemoteException e) {
                e = e;
                context2 = context;
            }
            if (activityInfo != null) {
                context2 = context;
                try {
                    this.mH.sendMessage(13, new MinimizeContainerItem(context2, activityInfo.packageName, runningTaskInfo.realActivity, runningTaskInfo.taskId, runningTaskInfo.userId, true));
                } catch (RemoteException e2) {
                    e = e2;
                    e.printStackTrace();
                    context = context2;
                }
                context = context2;
            }
        }
    }

    public final void throwAwayAllItems() {
        ArrayList arrayList = new ArrayList(this.mItemList);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) obj;
            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                freeformContainerItem.getClass();
                if (freeformContainerItem instanceof MultiInstanceItem) {
                    ArrayList arrayList2 = new ArrayList(freeformContainerItem.getItemList());
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        ((FreeformContainerItem) obj2).throwAway(this);
                    }
                }
            }
            freeformContainerItem.throwAway(this);
        }
    }
}
