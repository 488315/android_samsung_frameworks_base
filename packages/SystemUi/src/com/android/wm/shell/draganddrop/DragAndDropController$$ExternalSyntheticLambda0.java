package com.android.wm.shell.draganddrop;

import android.content.IntentFilter;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.draganddrop.DragAndDropController;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DragAndDropController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DragAndDropController f$0;

    public /* synthetic */ DragAndDropController$$ExternalSyntheticLambda0(DragAndDropController dragAndDropController, int i) {
        this.$r8$classId = i;
        this.f$0 = dragAndDropController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final DragAndDropController dragAndDropController = this.f$0;
                ((HandlerExecutor) dragAndDropController.mMainExecutor).executeDelayed(0L, new DragAndDropController$$ExternalSyntheticLambda0(dragAndDropController, 1));
                dragAndDropController.mShellController.addExternalInterface("extra_shell_drag_and_drop", new Supplier() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        DragAndDropController dragAndDropController2 = DragAndDropController.this;
                        dragAndDropController2.getClass();
                        return new DragAndDropController.IDragAndDropImpl(dragAndDropController2);
                    }
                }, dragAndDropController);
                dragAndDropController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        DragAndDropController dragAndDropController2 = DragAndDropController.this;
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        dragAndDropController2.getClass();
                        printWriter.println(str + "DragAndDropController");
                        printWriter.println(str + " listeners=" + dragAndDropController2.mListeners.size());
                    }
                }, dragAndDropController);
                break;
            default:
                DragAndDropController dragAndDropController2 = this.f$0;
                dragAndDropController2.mDisplayController.addDisplayWindowListener(dragAndDropController2);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
                dragAndDropController2.mContext.registerReceiver(dragAndDropController2.mDismissReceiver, intentFilter, 2);
                break;
        }
    }
}
