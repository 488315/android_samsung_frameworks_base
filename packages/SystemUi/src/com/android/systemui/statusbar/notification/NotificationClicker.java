package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda6;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationClicker implements View.OnClickListener {
    public final Optional mBubblesOptional;
    public final Optional mCentralSurfacesOptional;
    public final NotificationClickerLogger mLogger;
    public final NotificationActivityStarter mNotificationActivityStarter;
    public final C27171 mOnDragSuccessListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.NotificationClicker$1 */
    public final class C27171 {
        public C27171() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final NotificationClickerLogger mLogger;

        public Builder(NotificationClickerLogger notificationClickerLogger) {
            this.mLogger = notificationClickerLogger;
        }
    }

    public /* synthetic */ NotificationClicker(NotificationClickerLogger notificationClickerLogger, Optional optional, Optional optional2, NotificationActivityStarter notificationActivityStarter, int i) {
        this(notificationClickerLogger, optional, optional2, notificationActivityStarter);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            Log.e("NotificationClicker", "NotificationClicker called on a view that is not a notification row.");
            return;
        }
        final int i = 0;
        this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                switch (i) {
                    case 0:
                        ((CentralSurfacesImpl) ((CentralSurfaces) obj)).wakeUpIfDozing(SystemClock.uptimeMillis(), "NOTIFICATION_CLICK", 4);
                        break;
                    default:
                        ((CentralSurfacesImpl) ((CentralSurfaces) obj)).setShowSwipeBouncer(true);
                        break;
                }
            }
        });
        final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        NotificationClickerLogger notificationClickerLogger = this.mLogger;
        notificationClickerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationClickerLogger$logOnClick$2 notificationClickerLogger$logOnClick$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logOnClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return MotionLayout$$ExternalSyntheticOutline0.m22m("CLICK ", logMessage.getStr1(), " (channel=", logMessage.getStr2(), ")");
            }
        };
        LogBuffer logBuffer = notificationClickerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationClicker", logLevel, notificationClickerLogger$logOnClick$2, null);
        obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        obtain.setStr2(notificationEntry.mRanking.getChannel().getId());
        logBuffer.commit(obtain);
        NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
        final int i2 = 1;
        if (notificationMenuRowPlugin != null && notificationMenuRowPlugin.isMenuVisible()) {
            NotificationClickerLogger notificationClickerLogger2 = this.mLogger;
            notificationClickerLogger2.getClass();
            NotificationClickerLogger$logMenuVisible$2 notificationClickerLogger$logMenuVisible$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logMenuVisible$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PathParser$$ExternalSyntheticOutline0.m29m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; menu is visible");
                }
            };
            LogBuffer logBuffer2 = notificationClickerLogger2.buffer;
            LogMessage obtain2 = logBuffer2.obtain("NotificationClicker", logLevel, notificationClickerLogger$logMenuVisible$2, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain2, logBuffer2, obtain2);
            Animator animator = expandableNotificationRow.mTranslateAnim;
            if (animator != null) {
                animator.cancel();
            }
            Animator translateViewAnimator = expandableNotificationRow.getTranslateViewAnimator(0.0f, null);
            expandableNotificationRow.mTranslateAnim = translateViewAnimator;
            translateViewAnimator.start();
            return;
        }
        if (expandableNotificationRow.isChildInGroup()) {
            NotificationMenuRowPlugin notificationMenuRowPlugin2 = expandableNotificationRow.mNotificationParent.mMenuRow;
            if (notificationMenuRowPlugin2 != null && notificationMenuRowPlugin2.isMenuVisible()) {
                i = 1;
            }
            if (i != 0) {
                NotificationClickerLogger notificationClickerLogger3 = this.mLogger;
                notificationClickerLogger3.getClass();
                NotificationClickerLogger$logParentMenuVisible$2 notificationClickerLogger$logParentMenuVisible$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logParentMenuVisible$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return PathParser$$ExternalSyntheticOutline0.m29m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; parent menu is visible");
                    }
                };
                LogBuffer logBuffer3 = notificationClickerLogger3.buffer;
                LogMessage obtain3 = logBuffer3.obtain("NotificationClicker", logLevel, notificationClickerLogger$logParentMenuVisible$2, null);
                NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain3, logBuffer3, obtain3);
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
                Animator animator2 = expandableNotificationRow2.mTranslateAnim;
                if (animator2 != null) {
                    animator2.cancel();
                }
                Animator translateViewAnimator2 = expandableNotificationRow2.getTranslateViewAnimator(0.0f, null);
                expandableNotificationRow2.mTranslateAnim = translateViewAnimator2;
                translateViewAnimator2.start();
                return;
            }
        }
        if (expandableNotificationRow.mIsSummaryWithChildren && expandableNotificationRow.mChildrenExpanded) {
            NotificationClickerLogger notificationClickerLogger4 = this.mLogger;
            notificationClickerLogger4.getClass();
            NotificationClickerLogger$logChildrenExpanded$2 notificationClickerLogger$logChildrenExpanded$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logChildrenExpanded$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PathParser$$ExternalSyntheticOutline0.m29m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; children are expanded");
                }
            };
            LogBuffer logBuffer4 = notificationClickerLogger4.buffer;
            LogMessage obtain4 = logBuffer4.obtain("NotificationClicker", logLevel, notificationClickerLogger$logChildrenExpanded$2, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain4, logBuffer4, obtain4);
            return;
        }
        if (expandableNotificationRow.areGutsExposed()) {
            NotificationClickerLogger notificationClickerLogger5 = this.mLogger;
            notificationClickerLogger5.getClass();
            NotificationClickerLogger$logGutsExposed$2 notificationClickerLogger$logGutsExposed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logGutsExposed$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PathParser$$ExternalSyntheticOutline0.m29m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; guts are exposed");
                }
            };
            LogBuffer logBuffer5 = notificationClickerLogger5.buffer;
            LogMessage obtain5 = logBuffer5.obtain("NotificationClicker", logLevel, notificationClickerLogger$logGutsExposed$2, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain5, logBuffer5, obtain5);
            return;
        }
        expandableNotificationRow.mJustClicked = true;
        DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableNotificationRow.this.mJustClicked = false;
            }
        });
        if (!expandableNotificationRow.mEntry.isBubble() && this.mBubblesOptional.isPresent()) {
            BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) this.mBubblesOptional.get());
            ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda6(bubblesImpl, 3));
        }
        if (LsRune.SECURITY_SWIPE_BOUNCER) {
            this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    switch (i2) {
                        case 0:
                            ((CentralSurfacesImpl) ((CentralSurfaces) obj)).wakeUpIfDozing(SystemClock.uptimeMillis(), "NOTIFICATION_CLICK", 4);
                            break;
                        default:
                            ((CentralSurfacesImpl) ((CentralSurfaces) obj)).setShowSwipeBouncer(true);
                            break;
                    }
                }
            });
        }
        ((StatusBarNotificationActivityStarter) this.mNotificationActivityStarter).onNotificationClicked(notificationEntry, expandableNotificationRow);
    }

    private NotificationClicker(NotificationClickerLogger notificationClickerLogger, Optional<CentralSurfaces> optional, Optional<Bubbles> optional2, NotificationActivityStarter notificationActivityStarter) {
        this.mOnDragSuccessListener = new C27171();
        this.mLogger = notificationClickerLogger;
        this.mCentralSurfacesOptional = optional;
        this.mBubblesOptional = optional2;
        this.mNotificationActivityStarter = notificationActivityStarter;
    }
}
