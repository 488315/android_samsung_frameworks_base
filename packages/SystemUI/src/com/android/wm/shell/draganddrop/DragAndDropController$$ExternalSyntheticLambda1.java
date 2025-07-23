package com.android.wm.shell.draganddrop;

import android.content.IntentFilter;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DragAndDropController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DragAndDropController f$0;

    public /* synthetic */ DragAndDropController$$ExternalSyntheticLambda1(DragAndDropController dragAndDropController, int i) {
        this.$r8$classId = i;
        this.f$0 = dragAndDropController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final DragAndDropController dragAndDropController = this.f$0;
        switch (i) {
            case 0:
                ((HandlerExecutor) dragAndDropController.mMainExecutor).executeDelayed(new DragAndDropController$$ExternalSyntheticLambda1(dragAndDropController, 1), 0L);
                dragAndDropController.mShellController.addExternalInterface("com.android.wm.shell.draganddrop.IDragAndDrop", new Supplier() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda8
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        DragAndDropController dragAndDropController2 = DragAndDropController.this;
                        int i2 = DragAndDropController.$r8$clinit;
                        return new DragAndDropController.IDragAndDropImpl(dragAndDropController2);
                    }
                }, dragAndDropController);
                dragAndDropController.mShellTaskOrganizer.addTaskVanishedListener(dragAndDropController);
                dragAndDropController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda9
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        DragAndDropController dragAndDropController2 = DragAndDropController.this;
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
                        printWriter.println(str + "DragAndDropController");
                        printWriter.println(m + "listeners=" + dragAndDropController2.mListeners.size());
                        StringBuilder sb = new StringBuilder();
                        sb.append(m);
                        sb.append("Per display:");
                        printWriter.println(sb.toString());
                        for (int i2 = 0; i2 < dragAndDropController2.mDisplayDropTargets.size(); i2++) {
                            DragAndDropController.PerDisplay perDisplay = (DragAndDropController.PerDisplay) dragAndDropController2.mDisplayDropTargets.valueAt(i2);
                            perDisplay.getClass();
                            String str2 = m + "  ";
                            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str2, "displayId=");
                            m2.append(perDisplay.displayId);
                            printWriter.println(m2.toString());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str2);
                            sb2.append("hasDrawn=");
                            MagnificationImpl$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb2, perDisplay.hasDrawn, printWriter, str2, "isHandlingDrag="), perDisplay.isHandlingDrag, printWriter, str2, "activeDragCount="), perDisplay.activeDragCount, printWriter);
                            DropTargetLayout dropTargetLayout = (DropTargetLayout) perDisplay.dragLayout;
                            dropTargetLayout.getClass();
                            String str3 = str2 + "  ";
                            printWriter.println(str2 + "DragLayout:");
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str3);
                            sb3.append("mIsShowing=");
                            StringBuilder m3 = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb3, dropTargetLayout.mIsShowing, printWriter, str3, "mHasDropped="), dropTargetLayout.mHasDropped, printWriter, str3, "mCurrentTarget=");
                            m3.append(dropTargetLayout.mCurrentTarget);
                            printWriter.println(m3.toString());
                        }
                    }
                }, dragAndDropController);
                GlobalDragListener globalDragListener = dragAndDropController.mGlobalDragListener;
                boolean z = globalDragListener.callback == null;
                globalDragListener.callback = dragAndDropController;
                if (z) {
                    try {
                        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "%s unhandled drag listener", new Object[]{"Registering"});
                        globalDragListener.wmService.setGlobalDragListener(globalDragListener.callback != null ? globalDragListener.globalDragListener : null);
                    } catch (RemoteException unused) {
                        Log.e(GlobalDragListener.TAG, "Failed to set unhandled drag listener");
                    }
                }
                dragAndDropController.mListeners.add(new UnhandledDragController(dragAndDropController.mContext, dragAndDropController.mTransitions, dragAndDropController.mMultiInstanceHelper, dragAndDropController.mDisplayController));
                break;
            default:
                dragAndDropController.mDisplayController.addDisplayWindowListener(dragAndDropController, -1);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
                dragAndDropController.mContext.registerReceiver(dragAndDropController.mDismissReceiver, intentFilter, 2);
                break;
        }
    }
}
