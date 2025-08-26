package com.android.wm.shell.bubbles;

import android.content.pm.UserInfo;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda22 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda22(BubbleController.BubblesImpl bubblesImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = bubblesImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean zRemoveBubblesForUserWithParent;
        switch (this.$r8$classId) {
            case 0:
                BubbleController.BubblesImpl bubblesImpl = this.f$0;
                int i = this.f$1;
                BubbleController bubbleController = BubbleController.this;
                UserInfo profileParent = bubbleController.mUserManager.getProfileParent(i);
                int identifier = profileParent != null ? profileParent.getUserHandle().getIdentifier() : -1;
                BubbleData bubbleData = bubbleController.mBubbleData;
                bubbleData.getClass();
                ArrayList arrayList = new ArrayList();
                for (Bubble bubble : bubbleData.mPendingBubbles.values()) {
                    if (i == bubble.mUser.getIdentifier()) {
                        arrayList.add(bubble);
                    }
                }
                for (Bubble bubble2 : bubbleData.mSuppressedBubbles.values()) {
                    if (i == bubble2.mUser.getIdentifier()) {
                        arrayList.add(bubble2);
                    }
                }
                ArrayList arrayList2 = (ArrayList) bubbleData.mBubbles;
                int size = arrayList2.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList2.get(i2);
                    i2++;
                    Bubble bubble3 = (Bubble) obj;
                    if (i == bubble3.mUser.getIdentifier()) {
                        arrayList.add(bubble3);
                    }
                }
                ArrayList arrayList3 = (ArrayList) bubbleData.mOverflowBubbles;
                int size2 = arrayList3.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList3.get(i3);
                    i3++;
                    Bubble bubble4 = (Bubble) obj2;
                    if (i == bubble4.mUser.getIdentifier()) {
                        arrayList.add(bubble4);
                    }
                }
                int size3 = arrayList.size();
                int i4 = 0;
                while (i4 < size3) {
                    Object obj3 = arrayList.get(i4);
                    i4++;
                    bubbleData.doRemove(16, ((Bubble) obj3).mKey);
                }
                if (!arrayList.isEmpty()) {
                    bubbleData.dispatchPendingChanges();
                }
                BubbleDataRepository bubbleDataRepository = bubbleController.mDataRepository;
                BubbleVolatileRepository bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
                synchronized (bubbleVolatileRepository) {
                    if (identifier != -1) {
                        zRemoveBubblesForUserWithParent = bubbleVolatileRepository.removeBubblesForUserWithParent(i, identifier);
                    } else {
                        List list = (List) bubbleVolatileRepository.entitiesByUser.get(i);
                        bubbleVolatileRepository.entitiesByUser.remove(i);
                        zRemoveBubblesForUserWithParent = list != null;
                    }
                }
                if (zRemoveBubblesForUserWithParent) {
                    BubbleDataRepository.persistToDisk$default(bubbleDataRepository);
                    return;
                }
                return;
            default:
                BubbleController.this.onUserChanged(this.f$1);
                return;
        }
    }
}
