package com.android.wm.shell.freeform;

import android.content.Context;
import android.util.Log;
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int indexOf;
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
            indexOf = 0;
            int i2 = 0;
            loop0: while (true) {
                if (i2 >= size) {
                    indexOf = 0;
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
                indexOf++;
            }
        } else {
            indexOf = this.mItemList.indexOf(freeformContainerItem);
        }
        if (indexOf != 0 && !((FreeformContainerItem) this.mItemList.get(indexOf - 1)).mPublishCompleted) {
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
        if (indexOf < this.mItemList.size() - 1) {
            publishItemIfNeeded((FreeformContainerItem) this.mItemList.get(indexOf + 1));
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

    /* JADX WARN: Removed duplicated region for block: B:36:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removeItem(com.android.wm.shell.freeform.FreeformContainerItem r9) {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "[ItemController] Run removeItem, item="
            r0.<init>(r1)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "FreeformContainer"
            android.util.Log.i(r1, r0)
            boolean r0 = com.samsung.android.rune.CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW
            if (r0 == 0) goto Lc3
            r9.getClass()
            boolean r0 = r9 instanceof com.android.wm.shell.freeform.MultiInstanceItem
            if (r0 == 0) goto Lc3
            int r9 = r9.getTaskId()
            java.util.List r0 = r8.mItemList
            java.util.Iterator r0 = r0.iterator()
        L28:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto Ld1
            java.lang.Object r2 = r0.next()
            com.android.wm.shell.freeform.FreeformContainerItem r2 = (com.android.wm.shell.freeform.FreeformContainerItem) r2
            com.android.wm.shell.freeform.MultiInstanceItem r3 = r2.asMultiInstanceItem()
            if (r3 != 0) goto L3b
            goto L28
        L3b:
            java.util.List r4 = r3.mChildItemList
            int r4 = r4.size()
            int r4 = r4 + (-1)
        L43:
            r5 = 0
            if (r4 < 0) goto L56
            java.util.List r6 = r3.mChildItemList
            java.lang.Object r6 = r6.get(r4)
            com.android.wm.shell.freeform.MultiInstanceItem r6 = (com.android.wm.shell.freeform.MultiInstanceItem) r6
            int r7 = r6.mTaskId
            if (r7 != r9) goto L53
            goto L57
        L53:
            int r4 = r4 + (-1)
            goto L43
        L56:
            r6 = r5
        L57:
            if (r6 == 0) goto Lae
            java.util.List r4 = r3.mChildItemList
            boolean r4 = r4.contains(r6)
            if (r4 != 0) goto L73
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "[MultiInstanceItem]  removeChildItem: failed, not exist, "
            r3.<init>(r4)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            android.util.Log.w(r1, r3)
            goto La9
        L73:
            android.graphics.Bitmap r4 = r6.mSnapshotBitmap
            if (r4 == 0) goto L8a
            r6.mSnapshotBitmap = r5
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "[MultiInstanceItem] setSnapshotBitmap: "
            r4.<init>(r5)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r1, r4)
        L8a:
            java.util.List r4 = r3.mChildItemList
            r4.remove(r6)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "removeChildItem: "
            r4.<init>(r5)
            r4.append(r6)
            java.lang.String r5 = ", this="
            r4.append(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.util.Log.d(r1, r3)
        La9:
            com.android.wm.shell.freeform.FreeformContainerViewController r3 = r8.mViewController
            r3.notifyItemRemoved(r6)
        Lae:
            boolean r3 = r2.mPublishCompleted
            if (r3 == 0) goto L28
            int r3 = r2.getItemCount()
            if (r3 != 0) goto L28
            java.util.List r9 = r8.mItemList
            r9.remove(r2)
            com.android.wm.shell.freeform.FreeformContainerViewController r9 = r8.mViewController
            r9.notifyItemRemoved(r2)
            goto Ld1
        Lc3:
            java.util.List r0 = r8.mItemList
            r0.remove(r9)
            boolean r0 = r9.mPublishCompleted
            if (r0 == 0) goto Ld1
            com.android.wm.shell.freeform.FreeformContainerViewController r0 = r8.mViewController
            r0.notifyItemRemoved(r9)
        Ld1:
            java.util.List r8 = r8.mItemList
            boolean r8 = r8.isEmpty()
            if (r8 == 0) goto Le4
            java.util.concurrent.ExecutorService r8 = com.android.wm.shell.freeform.FreeformContainerSystemProxy.mExecutor
            com.android.wm.shell.freeform.FreeformContainerSystemProxy$$ExternalSyntheticLambda0 r9 = new com.android.wm.shell.freeform.FreeformContainerSystemProxy$$ExternalSyntheticLambda0
            r0 = 0
            r9.<init>(r0)
            r8.execute(r9)
        Le4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.freeform.FreeformContainerItemController.removeItem(com.android.wm.shell.freeform.FreeformContainerItem):void");
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
