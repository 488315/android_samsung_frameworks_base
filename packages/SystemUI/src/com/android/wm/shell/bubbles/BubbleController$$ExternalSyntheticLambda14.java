package com.android.wm.shell.bubbles;

import android.os.RemoteException;
import android.util.SparseArray;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda24;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.BubbleController;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda14 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda14(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() throws RemoteException {
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = (BubbleController) this.f$0;
                Bubble bubble = (Bubble) this.f$1;
                bubbleController.getClass();
                bubbleController.removeBubble(10, bubble.mKey);
                break;
            case 1:
                ((BubbleStackView$$ExternalSyntheticLambda29) this.f$0).accept((Boolean) this.f$1);
                break;
            case 2:
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) this.f$0;
                Bubble bubble2 = (Bubble) this.f$1;
                BubbleController bubbleController2 = BubbleController.this;
                if (bubble2 != null) {
                    BubbleData bubbleData = bubbleController2.mBubbleData;
                    String str = bubble2.mKey;
                    if (!bubbleData.hasBubbleInStackWithKey(str)) {
                        if (bubbleData.hasOverflowBubbleWithKey(str)) {
                            bubbleController2.promoteBubbleFromOverflow(bubble2);
                            break;
                        }
                    } else {
                        bubbleData.setSelectedBubbleAndExpandStack(bubble2);
                        break;
                    }
                } else {
                    bubbleController2.getClass();
                    break;
                }
                break;
            case 3:
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) this.f$0;
                BubbleController.this.setExpandListener((CentralSurfacesImpl$$ExternalSyntheticLambda24) this.f$1);
                break;
            case 4:
                BubbleController.this.mCurrentProfiles = (SparseArray) this.f$1;
                break;
            case 5:
                BubbleController.this.mBubbleSALogger = (BubblesManager$$ExternalSyntheticLambda1) this.f$1;
                break;
            case 6:
                BubbleController.this.mSysuiProxy = (BubblesManager.AnonymousClass5) this.f$1;
                break;
            case 7:
                BubbleViewProvider bubbleViewProvider = ((BubbleController.IBubblesImpl) this.f$0).mController.mBubbleData.mSelectedBubble;
                break;
            default:
                BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this.f$0;
                iBubblesImpl.mListener.register((IBubblesListener$Stub$Proxy) this.f$1);
                break;
        }
    }
}
