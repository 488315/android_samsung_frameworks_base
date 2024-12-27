package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.util.Log;
import android.view.View;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda5;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import java.util.Optional;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationClicker implements View.OnClickListener {
    public final Optional mBubblesOptional;
    public final NotificationClickerLogger mLogger;
    public final NotificationActivityStarter mNotificationActivityStarter;
    public final AnonymousClass1 mOnDragSuccessListener;
    public final PowerInteractor mPowerInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.NotificationClicker$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Builder {
        public final NotificationClickerLogger mLogger;
        public final PowerInteractor mPowerInteractor;

        public Builder(NotificationClickerLogger notificationClickerLogger, PowerInteractor powerInteractor) {
            this.mLogger = notificationClickerLogger;
            this.mPowerInteractor = powerInteractor;
        }
    }

    public /* synthetic */ NotificationClicker(NotificationClickerLogger notificationClickerLogger, PowerInteractor powerInteractor, Optional optional, NotificationActivityStarter notificationActivityStarter, int i) {
        this(notificationClickerLogger, powerInteractor, optional, notificationActivityStarter);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        NotificationMenuRowPlugin notificationMenuRowPlugin;
        if (!(view instanceof ExpandableNotificationRow)) {
            Log.e("NotificationClicker", "NotificationClicker called on a view that is not a notification row.");
            return;
        }
        this.mPowerInteractor.wakeUpIfDozing(4, "NOTIFICATION_CLICK");
        final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        NotificationClickerLogger notificationClickerLogger = this.mLogger;
        notificationClickerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationClickerLogger$logOnClick$2 notificationClickerLogger$logOnClick$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logOnClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return MotionLayout$$ExternalSyntheticOutline0.m("CLICK ", logMessage.getStr1(), " (channel=", logMessage.getStr2(), ")");
            }
        };
        LogBuffer logBuffer = notificationClickerLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationClicker", logLevel, notificationClickerLogger$logOnClick$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = notificationEntry.mRanking.getChannel().getId();
        logBuffer.commit(obtain);
        NotificationMenuRowPlugin notificationMenuRowPlugin2 = expandableNotificationRow.mMenuRow;
        if (notificationMenuRowPlugin2 != null && notificationMenuRowPlugin2.isMenuVisible()) {
            NotificationClickerLogger notificationClickerLogger2 = this.mLogger;
            notificationClickerLogger2.getClass();
            NotificationClickerLogger$logMenuVisible$2 notificationClickerLogger$logMenuVisible$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logMenuVisible$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; menu is visible");
                }
            };
            LogBuffer logBuffer2 = notificationClickerLogger2.buffer;
            LogMessage obtain2 = logBuffer2.obtain("NotificationClicker", logLevel, notificationClickerLogger$logMenuVisible$2, null);
            ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer2.commit(obtain2);
            Animator animator = expandableNotificationRow.mTranslateAnim;
            if (animator != null) {
                animator.cancel();
            }
            Animator translateViewAnimator = expandableNotificationRow.getTranslateViewAnimator(0.0f, null);
            expandableNotificationRow.mTranslateAnim = translateViewAnimator;
            translateViewAnimator.start();
            return;
        }
        if (expandableNotificationRow.isChildInGroup() && (notificationMenuRowPlugin = expandableNotificationRow.mNotificationParent.mMenuRow) != null && notificationMenuRowPlugin.isMenuVisible()) {
            NotificationClickerLogger notificationClickerLogger3 = this.mLogger;
            notificationClickerLogger3.getClass();
            NotificationClickerLogger$logParentMenuVisible$2 notificationClickerLogger$logParentMenuVisible$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logParentMenuVisible$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; parent menu is visible");
                }
            };
            LogBuffer logBuffer3 = notificationClickerLogger3.buffer;
            LogMessage obtain3 = logBuffer3.obtain("NotificationClicker", logLevel, notificationClickerLogger$logParentMenuVisible$2, null);
            ((LogMessageImpl) obtain3).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer3.commit(obtain3);
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
        if (expandableNotificationRow.mIsSummaryWithChildren && expandableNotificationRow.mChildrenExpanded) {
            NotificationClickerLogger notificationClickerLogger4 = this.mLogger;
            notificationClickerLogger4.getClass();
            NotificationClickerLogger$logChildrenExpanded$2 notificationClickerLogger$logChildrenExpanded$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logChildrenExpanded$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; children are expanded");
                }
            };
            LogBuffer logBuffer4 = notificationClickerLogger4.buffer;
            LogMessage obtain4 = logBuffer4.obtain("NotificationClicker", logLevel, notificationClickerLogger$logChildrenExpanded$2, null);
            ((LogMessageImpl) obtain4).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer4.commit(obtain4);
            return;
        }
        if (expandableNotificationRow.areGutsExposed()) {
            NotificationClickerLogger notificationClickerLogger5 = this.mLogger;
            notificationClickerLogger5.getClass();
            NotificationClickerLogger$logGutsExposed$2 notificationClickerLogger$logGutsExposed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$logGutsExposed$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", ((LogMessage) obj).getStr1(), "; guts are exposed");
                }
            };
            LogBuffer logBuffer5 = notificationClickerLogger5.buffer;
            LogMessage obtain5 = logBuffer5.obtain("NotificationClicker", logLevel, notificationClickerLogger$logGutsExposed$2, null);
            ((LogMessageImpl) obtain5).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer5.commit(obtain5);
            return;
        }
        expandableNotificationRow.mJustClicked = true;
        DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableNotificationRow.this.mJustClicked = false;
            }
        });
        if (!expandableNotificationRow.mEntry.isBubble() && this.mBubblesOptional.isPresent()) {
            BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) this.mBubblesOptional.get());
            ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda5(bubblesImpl, 2));
        }
        if (LsRune.SECURITY_SWIPE_BOUNCER) {
            ((StatusBarNotificationActivityStarter) this.mNotificationActivityStarter).setShowSwipeBouncer(true);
        }
        ((StatusBarNotificationActivityStarter) this.mNotificationActivityStarter).onNotificationClicked(notificationEntry, expandableNotificationRow);
    }

    private NotificationClicker(NotificationClickerLogger notificationClickerLogger, PowerInteractor powerInteractor, Optional<Bubbles> optional, NotificationActivityStarter notificationActivityStarter) {
        this.mOnDragSuccessListener = new AnonymousClass1();
        this.mLogger = notificationClickerLogger;
        this.mPowerInteractor = powerInteractor;
        this.mBubblesOptional = optional;
        this.mNotificationActivityStarter = notificationActivityStarter;
    }
}
