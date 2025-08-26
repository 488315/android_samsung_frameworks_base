package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.util.Log;
import android.view.View;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda5;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class NotificationClicker implements View.OnClickListener {
    public final Optional mBubblesOptional;
    public final NotificationClickerLogger mLogger;
    public final NotificationActivityStarter mNotificationActivityStarter;
    public final AnonymousClass1 mOnDragSuccessListener;
    public final PowerInteractor mPowerInteractor;

    /* renamed from: com.android.systemui.statusbar.notification.NotificationClicker$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public class Builder {
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
        final int i = 2;
        final int i2 = 1;
        final int i3 = 4;
        if (!(view instanceof ExpandableNotificationRow)) {
            Log.e("NotificationClicker", "NotificationClicker called on a view that is not a notification row.");
            return;
        }
        this.mPowerInteractor.wakeUpIfDozing(4, "NOTIFICATION_CLICK");
        final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        NotificationClickerLogger notificationClickerLogger = this.mLogger;
        String str = expandableNotificationRow.mLoggingKey;
        notificationClickerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i3) {
                    case 0:
                        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; children are expanded");
                    case 1:
                        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; menu is visible");
                    case 2:
                        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; guts are exposed");
                    case 3:
                        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; parent menu is visible");
                    default:
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CLICK ", logMessage.getStr1());
                }
            }
        };
        LogBuffer logBuffer = notificationClickerLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotificationClicker", logLevel, function1, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
        NotificationMenuRowPlugin notificationMenuRowPlugin2 = expandableNotificationRow.mMenuRow;
        if (notificationMenuRowPlugin2 != null && notificationMenuRowPlugin2.isMenuVisible()) {
            NotificationClickerLogger notificationClickerLogger2 = this.mLogger;
            String str2 = expandableNotificationRow.mLoggingKey;
            notificationClickerLogger2.getClass();
            Function1 function12 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    switch (i2) {
                        case 0:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; children are expanded");
                        case 1:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; menu is visible");
                        case 2:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; guts are exposed");
                        case 3:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; parent menu is visible");
                        default:
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CLICK ", logMessage.getStr1());
                    }
                }
            };
            LogBuffer logBuffer2 = notificationClickerLogger2.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("NotificationClicker", logLevel, function12, null);
            ((LogMessageImpl) logMessageObtain2).str1 = str2;
            logBuffer2.commit(logMessageObtain2);
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
            String str3 = expandableNotificationRow.mLoggingKey;
            notificationClickerLogger3.getClass();
            final int i4 = 3;
            Function1 function13 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    switch (i4) {
                        case 0:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; children are expanded");
                        case 1:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; menu is visible");
                        case 2:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; guts are exposed");
                        case 3:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; parent menu is visible");
                        default:
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CLICK ", logMessage.getStr1());
                    }
                }
            };
            LogBuffer logBuffer3 = notificationClickerLogger3.buffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("NotificationClicker", logLevel, function13, null);
            ((LogMessageImpl) logMessageObtain3).str1 = str3;
            logBuffer3.commit(logMessageObtain3);
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
            String str4 = expandableNotificationRow.mLoggingKey;
            notificationClickerLogger4.getClass();
            final int i5 = 0;
            Function1 function14 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    switch (i5) {
                        case 0:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; children are expanded");
                        case 1:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; menu is visible");
                        case 2:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; guts are exposed");
                        case 3:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; parent menu is visible");
                        default:
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CLICK ", logMessage.getStr1());
                    }
                }
            };
            LogBuffer logBuffer4 = notificationClickerLogger4.buffer;
            LogMessage logMessageObtain4 = logBuffer4.obtain("NotificationClicker", logLevel, function14, null);
            ((LogMessageImpl) logMessageObtain4).str1 = str4;
            logBuffer4.commit(logMessageObtain4);
            return;
        }
        if (expandableNotificationRow.areGutsExposed()) {
            NotificationClickerLogger notificationClickerLogger5 = this.mLogger;
            String str5 = expandableNotificationRow.mLoggingKey;
            notificationClickerLogger5.getClass();
            Function1 function15 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationClickerLogger$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    switch (i) {
                        case 0:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; children are expanded");
                        case 1:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; menu is visible");
                        case 2:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; guts are exposed");
                        case 3:
                            return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Ignoring click on ", logMessage.getStr1(), "; parent menu is visible");
                        default:
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CLICK ", logMessage.getStr1());
                    }
                }
            };
            LogBuffer logBuffer5 = notificationClickerLogger5.buffer;
            LogMessage logMessageObtain5 = logBuffer5.obtain("NotificationClicker", logLevel, function15, null);
            ((LogMessageImpl) logMessageObtain5).str1 = str5;
            logBuffer5.commit(logMessageObtain5);
            return;
        }
        expandableNotificationRow.mJustClicked = true;
        DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                expandableNotificationRow.mJustClicked = false;
            }
        });
        if (LsRune.SECURITY_SWIPE_BOUNCER) {
            ((StatusBarNotificationActivityStarter) this.mNotificationActivityStarter).setShowSwipeBouncer(true);
        }
        int i6 = NotificationBundleUi.$r8$clinit;
        if (!expandableNotificationRow.getEntryLegacy().isBubble() && this.mBubblesOptional.isPresent()) {
            BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) this.mBubblesOptional.get());
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda5(bubblesImpl, i));
        }
        ((StatusBarNotificationActivityStarter) this.mNotificationActivityStarter).onNotificationClicked(expandableNotificationRow.getEntryLegacy(), expandableNotificationRow);
    }

    private NotificationClicker(NotificationClickerLogger notificationClickerLogger, PowerInteractor powerInteractor, Optional<Bubbles> optional, NotificationActivityStarter notificationActivityStarter) {
        this.mOnDragSuccessListener = new AnonymousClass1();
        this.mLogger = notificationClickerLogger;
        this.mPowerInteractor = powerInteractor;
        this.mBubblesOptional = optional;
        this.mNotificationActivityStarter = notificationActivityStarter;
    }
}
