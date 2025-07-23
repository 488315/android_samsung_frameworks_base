package com.android.wm.shell.bubbles;

import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.CollectionUtils;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$6$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleController$6$$ExternalSyntheticLambda0(BubbleController.AnonymousClass7 anonymousClass7) {
        this.$r8$classId = 1;
        this.f$0 = anonymousClass7;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BubbleController.this.collapseStack();
                break;
            case 1:
                BubbleController bubbleController = BubbleController.this;
                BubbleData bubbleData = bubbleController.mBubbleData;
                if (!bubbleData.mExpanded) {
                    if (bubbleData.mSelectedBubble == null) {
                        BubbleViewProvider bubbleViewProvider = (BubbleViewProvider) CollectionUtils.firstOrNull(Collections.unmodifiableList(bubbleData.mBubbles));
                        if (bubbleViewProvider == null) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, -4029904759595518332L, 0, null);
                            }
                            bubbleController.loadOverflowBubblesFromDisk();
                            bubbleViewProvider = bubbleData.mOverflow;
                        }
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, 5069015937721908995L, 0, String.valueOf(bubbleViewProvider.getKey()));
                        }
                        bubbleData.setSelectedBubbleAndExpandStack(bubbleViewProvider);
                        break;
                    } else {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, 4398841635101555077L, 0, null);
                        }
                        BubbleData bubbleData2 = bubbleController.mBubbleData;
                        if (bubbleData2.mSelectedBubble != null) {
                            bubbleData2.setExpanded(true);
                            break;
                        }
                    }
                } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, 788200245784688848L, 0, null);
                    break;
                }
                break;
            default:
                ((SingleInstanceRemoteListener) obj).unregister();
                break;
        }
    }

    public /* synthetic */ BubbleController$6$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }
}
