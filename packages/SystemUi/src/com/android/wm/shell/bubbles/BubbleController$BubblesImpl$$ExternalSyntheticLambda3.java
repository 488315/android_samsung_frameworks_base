package com.android.wm.shell.bubbles;

import android.content.pm.UserInfo;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController.BubblesImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda3(BubbleController.BubblesImpl bubblesImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = bubblesImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleController.BubblesImpl bubblesImpl = this.f$0;
                final int i = this.f$1;
                BubbleController bubbleController = BubbleController.this;
                UserInfo profileParent = bubbleController.mUserManager.getProfileParent(i);
                int identifier = profileParent != null ? profileParent.getUserHandle().getIdentifier() : -1;
                BubbleData bubbleData = bubbleController.mBubbleData;
                bubbleData.getClass();
                ArrayList arrayList = new ArrayList();
                Iterator it = bubbleData.mPendingBubbles.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        for (Bubble bubble : bubbleData.mSuppressedBubbles.values()) {
                            if (i == bubble.mUser.getIdentifier()) {
                                arrayList.add(bubble);
                            }
                        }
                        Iterator it2 = ((ArrayList) bubbleData.mBubbles).iterator();
                        while (it2.hasNext()) {
                            Bubble bubble2 = (Bubble) it2.next();
                            if (i == bubble2.mUser.getIdentifier()) {
                                arrayList.add(bubble2);
                            }
                        }
                        Iterator it3 = ((ArrayList) bubbleData.mOverflowBubbles).iterator();
                        while (it3.hasNext()) {
                            Bubble bubble3 = (Bubble) it3.next();
                            if (i == bubble3.mUser.getIdentifier()) {
                                arrayList.add(bubble3);
                            }
                        }
                        Iterator it4 = arrayList.iterator();
                        while (it4.hasNext()) {
                            bubbleData.doRemove(16, ((Bubble) it4.next()).mKey);
                        }
                        if (!arrayList.isEmpty()) {
                            bubbleData.dispatchPendingChanges();
                        }
                        BubbleDataRepository bubbleDataRepository = bubbleController.mDataRepository;
                        BubbleVolatileRepository bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
                        synchronized (bubbleVolatileRepository) {
                            if (identifier != -1) {
                                synchronized (bubbleVolatileRepository) {
                                    if (bubbleVolatileRepository.entitiesByUser.get(identifier) != null) {
                                        r7 = ((List) bubbleVolatileRepository.entitiesByUser.get(identifier)).removeIf(new Predicate() { // from class: com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$removeBubblesForUserWithParent$1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((BubbleEntity) obj).userId == i;
                                            }
                                        });
                                    }
                                }
                            } else {
                                List list = (List) bubbleVolatileRepository.entitiesByUser.get(i);
                                bubbleVolatileRepository.entitiesByUser.remove(i);
                                r7 = list != null;
                            }
                        }
                        if (r7) {
                            bubbleDataRepository.persistToDisk();
                            return;
                        }
                        return;
                    }
                    Bubble bubble4 = (Bubble) it.next();
                    if (i == bubble4.mUser.getIdentifier()) {
                        arrayList.add(bubble4);
                    }
                }
            default:
                BubbleController.this.onUserChanged(this.f$1);
                return;
        }
    }
}
