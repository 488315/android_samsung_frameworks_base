package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.animation.view.LaunchableTextView;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.AnonymousClass3;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationShelfViewBinder$bindSettingsButton$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationActivityStarter $notificationActivityStarter;
    final /* synthetic */ NotificationShelfManager $shelfManager;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$bindSettingsButton$2(NotificationShelfManager notificationShelfManager, NotificationActivityStarter notificationActivityStarter, Continuation continuation) {
        super(2, continuation);
        this.$shelfManager = notificationShelfManager;
        this.$notificationActivityStarter = notificationActivityStarter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationShelfViewBinder$bindSettingsButton$2(this.$shelfManager, this.$notificationActivityStarter, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationShelfViewBinder$bindSettingsButton$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final NotificationActivityStarter notificationActivityStarter = this.$notificationActivityStarter;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bindSettingsButton$2$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) NotificationActivityStarter.this;
                statusBarNotificationActivityStarter.getClass();
                int i = ModesEmptyShadeFix.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                statusBarNotificationActivityStarter.mActivityStarter.dismissKeyguardThenExecute(statusBarNotificationActivityStarter.new AnonymousClass3(false, (View) obj2, ((ShadeDialogContextInteractorImpl) statusBarNotificationActivityStarter.mContextInteractor).getContext().getDisplayId(), false), null, false);
                ((ShelfToolTipManager) Dependency.sDependency.getDependencyInner(ShelfToolTipManager.class)).isTappedNotiSettings = true;
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_NOTIFICATION_SETTINGS_ON_FOOTER);
                return Unit.INSTANCE;
            }
        };
        NotificationShelfManager notificationShelfManager = this.$shelfManager;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$sam$android_view_View_OnClickListener$0
            @Override // android.view.View.OnClickListener
            public final /* synthetic */ void onClick(View view) {
                Function1.this.mo779invoke(view);
            }
        };
        LaunchableTextView launchableTextView = notificationShelfManager.mSettingButton;
        if (launchableTextView != null) {
            launchableTextView.setOnClickListener(onClickListener);
        }
        return Unit.INSTANCE;
    }
}
