package com.android.wm.shell.bubbles;

import android.os.IBinder;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.bubbles.BubbleTransitions;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ WindowContainerTransaction f$1;

    public /* synthetic */ BubbleTransitions$ConvertToBubble$$ExternalSyntheticLambda0(Object obj, WindowContainerTransaction windowContainerTransaction, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = windowContainerTransaction;
    }

    public final IBinder start() {
        switch (this.$r8$classId) {
            case 0:
                WindowContainerTransaction windowContainerTransaction = this.f$1;
                BubbleTransitions.ConvertToBubble convertToBubble = (BubbleTransitions.ConvertToBubble) this.f$0;
                IBinder iBinderStartTransition = BubbleTransitions.this.mTransitions.startTransition(1024, windowContainerTransaction, convertToBubble);
                convertToBubble.mTransition = iBinderStartTransition;
                return iBinderStartTransition;
            case 1:
                WindowContainerTransaction windowContainerTransaction2 = this.f$1;
                BubbleTransitions.ConvertFromBubble convertFromBubble = (BubbleTransitions.ConvertFromBubble) this.f$0;
                IBinder iBinderStartTransition2 = BubbleTransitions.this.mTransitions.startTransition(6, windowContainerTransaction2, convertFromBubble);
                convertFromBubble.mTransition = iBinderStartTransition2;
                return iBinderStartTransition2;
            default:
                WindowContainerTransaction windowContainerTransaction3 = this.f$1;
                BubbleTransitions.DraggedBubbleIconToFullscreen draggedBubbleIconToFullscreen = (BubbleTransitions.DraggedBubbleIconToFullscreen) this.f$0;
                IBinder iBinderStartTransition3 = BubbleTransitions.this.mTransitions.startTransition(3, windowContainerTransaction3, draggedBubbleIconToFullscreen);
                draggedBubbleIconToFullscreen.mTransition = iBinderStartTransition3;
                return iBinderStartTransition3;
        }
    }
}
