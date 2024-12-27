package com.android.systemui.notetask;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.protolog.ProtoLogImpl_1636998151;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.INoteTaskBubblesService;
import com.android.systemui.notetask.NoteTaskBubblesController;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NoteTaskBubblesController$NoteTaskBubblesService$onBind$1 extends INoteTaskBubblesService.Stub {
    public final /* synthetic */ NoteTaskBubblesController.NoteTaskBubblesService this$0;

    public NoteTaskBubblesController$NoteTaskBubblesService$onBind$1(NoteTaskBubblesController.NoteTaskBubblesService noteTaskBubblesService) {
        this.this$0 = noteTaskBubblesService;
    }

    @Override // com.android.systemui.notetask.INoteTaskBubblesService
    public final boolean areBubblesAvailable() {
        return this.this$0.mOptionalBubbles.isPresent();
    }

    @Override // com.android.systemui.notetask.INoteTaskBubblesService
    public final void showOrHideAppBubble(final Intent intent, final UserHandle userHandle, final Icon icon) {
        Optional optional = this.this$0.mOptionalBubbles;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$NoteTaskBubblesService$onBind$1$showOrHideAppBubble$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final Intent intent2 = intent;
                final UserHandle userHandle2 = userHandle;
                final Icon icon2 = icon;
                final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) obj);
                ((HandlerExecutor) bubblesImpl.this$0.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str;
                        BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                        Intent intent3 = intent2;
                        UserHandle userHandle3 = userHandle2;
                        Icon icon3 = icon2;
                        BubbleController bubbleController = bubblesImpl2.this$0;
                        bubbleController.getClass();
                        if (intent3 == null || intent3.getPackage() == null) {
                            StringBuilder sb = new StringBuilder("App bubble failed to show, invalid intent: ");
                            sb.append(intent3);
                            if (intent3 != null) {
                                str = " with package: " + intent3.getPackage();
                            } else {
                                str = " ";
                            }
                            sb.append(str);
                            Log.w("Bubbles", sb.toString());
                            return;
                        }
                        String appBubbleKeyForApp = Bubble.getAppBubbleKeyForApp(intent3.getPackage(), userHandle3);
                        if (BubbleController.isResizableActivity(intent3, BubbleController.getPackageManagerForUser(userHandle3.getIdentifier(), bubbleController.mContext), appBubbleKeyForApp)) {
                            BubbleData bubbleData = bubbleController.mBubbleData;
                            Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(appBubbleKeyForApp);
                            if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                String valueOf = String.valueOf(appBubbleKeyForApp);
                                String valueOf2 = String.valueOf(bubbleInStackWithKey);
                                BubbleStackView bubbleStackView = bubbleController.mStackView;
                                ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -7069550301890004257L, 0, null, valueOf, valueOf2, String.valueOf(bubbleStackView != null ? Integer.valueOf(bubbleStackView.getVisibility()) : "null"), String.valueOf(bubbleController.mIsStatusBarShade));
                            }
                            if (bubbleInStackWithKey == null) {
                                Bubble overflowBubbleWithKey = bubbleData.getOverflowBubbleWithKey(appBubbleKeyForApp);
                                if (overflowBubbleWithKey != null) {
                                    bubbleData.dismissBubbleWithKey(5, appBubbleKeyForApp);
                                } else {
                                    overflowBubbleWithKey = Bubble.createAppBubble(intent3, userHandle3, icon3, bubbleController.mMainExecutor);
                                }
                                if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -1428177178668878758L, 0, null, String.valueOf(appBubbleKeyForApp));
                                }
                                overflowBubbleWithKey.setShouldAutoExpand(true);
                                bubbleController.inflateAndAdd(overflowBubbleWithKey, true, false);
                                return;
                            }
                            BubbleViewProvider bubbleViewProvider = bubbleData.mSelectedBubble;
                            if (!bubbleData.mExpanded) {
                                if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -9169024674370276453L, 0, null, String.valueOf(appBubbleKeyForApp));
                                }
                                bubbleData.setSelectedBubbleAndExpandStack(bubbleInStackWithKey);
                            } else if (bubbleViewProvider != null && appBubbleKeyForApp.equals(bubbleViewProvider.getKey())) {
                                if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 8825060701225958311L, 0, null, appBubbleKeyForApp);
                                }
                                bubbleController.collapseStack();
                            } else {
                                if (ProtoLogImpl_1636998151.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1636998151.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -6686345507646064545L, 0, null, String.valueOf(appBubbleKeyForApp));
                                }
                                bubbleData.setSelectedBubbleInternal(bubbleInStackWithKey);
                                bubbleData.dispatchPendingChanges();
                            }
                        }
                    }
                });
            }
        };
        final NoteTaskBubblesController.NoteTaskBubblesService noteTaskBubblesService = this.this$0;
        optional.ifPresentOrElse(consumer, new Runnable(intent, noteTaskBubblesService, icon) { // from class: com.android.systemui.notetask.NoteTaskBubblesController$NoteTaskBubblesService$onBind$1$showOrHideAppBubble$2
            @Override // java.lang.Runnable
            public final void run() {
                int i = DebugLogger.$r8$clinit;
                NoteTaskBubblesController$NoteTaskBubblesService$onBind$1 noteTaskBubblesController$NoteTaskBubblesService$onBind$1 = NoteTaskBubblesController$NoteTaskBubblesService$onBind$1.this;
                boolean z = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(noteTaskBubblesController$NoteTaskBubblesService$onBind$1.getClass()).getSimpleName();
            }
        });
    }
}
