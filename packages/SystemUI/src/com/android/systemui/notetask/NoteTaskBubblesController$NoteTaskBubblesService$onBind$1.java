package com.android.systemui.notetask;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.INoteTaskBubblesService;
import com.android.systemui.notetask.NoteTaskBubblesController;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class NoteTaskBubblesController$NoteTaskBubblesService$onBind$1 extends INoteTaskBubblesService.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ NoteTaskBubblesController.NoteTaskBubblesService this$0;

    public NoteTaskBubblesController$NoteTaskBubblesService$onBind$1(NoteTaskBubblesController.NoteTaskBubblesService noteTaskBubblesService) {
        this.this$0 = noteTaskBubblesService;
    }

    @Override // com.android.systemui.notetask.INoteTaskBubblesService
    public final boolean areBubblesAvailable() {
        return this.this$0.mOptionalBubbles.isPresent();
    }

    @Override // com.android.systemui.notetask.INoteTaskBubblesService
    public final void showOrHideNoteBubble(final Intent intent, final UserHandle userHandle, final Icon icon, final NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehavior) {
        Optional optional = this.this$0.mOptionalBubbles;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$NoteTaskBubblesService$onBind$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                final Intent intent2 = intent;
                final UserHandle userHandle2 = userHandle;
                final Icon icon2 = icon;
                Bubbles bubbles = (Bubbles) obj;
                int i = NoteTaskBubblesController$NoteTaskBubblesService$onBind$1.$r8$clinit;
                if (noteTaskBubbleExpandBehavior == NoteTaskBubbleExpandBehavior.KEEP_IF_EXPANDED) {
                    if (((BubbleController.BubblesImpl) bubbles).isBubbleExpanded(Bubble.getNoteBubbleKeyForApp(intent2.getPackage(), userHandle2))) {
                        return Unit.INSTANCE;
                    }
                }
                final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                BubbleController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str;
                        BubbleController.BubblesImpl bubblesImpl2 = bubblesImpl;
                        Intent intent3 = intent2;
                        UserHandle userHandle3 = userHandle2;
                        Icon icon3 = icon2;
                        BubbleController bubbleController = BubbleController.this;
                        bubbleController.getClass();
                        if (intent3 == null || intent3.getPackage() == null) {
                            StringBuilder sb = new StringBuilder("Notes bubble failed to show, invalid intent: ");
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
                        String noteBubbleKeyForApp = Bubble.getNoteBubbleKeyForApp(intent3.getPackage(), userHandle3);
                        if (((BubbleResizabilityChecker) bubbleController.mResizabilityChecker).isResizableActivity(intent3, BubbleController.getPackageManagerForUser(userHandle3.getIdentifier(), bubbleController.mContext), noteBubbleKeyForApp)) {
                            BubbleData bubbleData = bubbleController.mBubbleData;
                            Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(noteBubbleKeyForApp);
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                String strValueOf = String.valueOf(noteBubbleKeyForApp);
                                String strValueOf2 = String.valueOf(bubbleInStackWithKey);
                                BubbleStackView bubbleStackView = bubbleController.mStackView;
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -7114439641328373468L, 0, strValueOf, strValueOf2, String.valueOf(bubbleStackView != null ? Integer.valueOf(bubbleStackView.getVisibility()) : "null"), String.valueOf(bubbleController.mIsStatusBarShade));
                            }
                            if (bubbleInStackWithKey == null) {
                                Bubble overflowBubbleWithKey = bubbleData.getOverflowBubbleWithKey(noteBubbleKeyForApp);
                                if (overflowBubbleWithKey != null) {
                                    bubbleData.dismissBubbleWithKey(5, noteBubbleKeyForApp);
                                    overflowBubbleWithKey.mIntent = intent3;
                                } else {
                                    overflowBubbleWithKey = Bubble.createNotesBubble(intent3, userHandle3, icon3, bubbleController.mMainExecutor, bubbleController.mBackgroundExecutor);
                                }
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -1428177178668878758L, 0, String.valueOf(noteBubbleKeyForApp));
                                }
                                overflowBubbleWithKey.setShouldAutoExpand(true);
                                bubbleController.inflateAndAdd(overflowBubbleWithKey, true, false);
                                return;
                            }
                            BubbleViewProvider bubbleViewProvider = bubbleData.mSelectedBubble;
                            if (!bubbleData.mExpanded) {
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -9169024674370276453L, 0, String.valueOf(noteBubbleKeyForApp));
                                }
                                bubbleData.setSelectedBubbleAndExpandStack(bubbleInStackWithKey);
                            } else if (bubbleViewProvider != null && noteBubbleKeyForApp.equals(bubbleViewProvider.getKey())) {
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 8825060701225958311L, 0, noteBubbleKeyForApp);
                                }
                                bubbleController.collapseStack();
                            } else {
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -6686345507646064545L, 0, String.valueOf(noteBubbleKeyForApp));
                                }
                                bubbleData.setSelectedBubbleInternal(bubbleInStackWithKey);
                                bubbleData.dispatchPendingChanges();
                            }
                        }
                    }
                });
                return Unit.INSTANCE;
            }
        };
        Consumer consumer = new Consumer() { // from class: com.android.systemui.notetask.NoteTaskBubblesController$sam$java_util_function_Consumer$0
            @Override // java.util.function.Consumer
            public final /* synthetic */ void accept(Object obj) {
                function1.mo781invoke(obj);
            }
        };
        final NoteTaskBubblesController.NoteTaskBubblesService noteTaskBubblesService = this.this$0;
        optional.ifPresentOrElse(consumer, new Runnable(intent, noteTaskBubblesService, icon) { // from class: com.android.systemui.notetask.NoteTaskBubblesController$NoteTaskBubblesService$onBind$1$showOrHideNoteBubble$2
            @Override // java.lang.Runnable
            public final void run() {
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                NoteTaskBubblesController$NoteTaskBubblesService$onBind$1 noteTaskBubblesController$NoteTaskBubblesService$onBind$1 = this.this$0;
                boolean z = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(noteTaskBubblesController$NoteTaskBubblesService$onBind$1.getClass()).getSimpleName();
            }
        });
    }
}
