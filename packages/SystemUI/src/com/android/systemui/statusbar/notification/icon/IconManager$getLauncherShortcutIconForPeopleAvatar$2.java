package com.android.systemui.statusbar.notification.icon;

import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.util.Log;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class IconManager$getLauncherShortcutIconForPeopleAvatar$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationEntry $entry;
    int label;
    final /* synthetic */ IconManager this$0;

    /* renamed from: com.android.systemui.statusbar.notification.icon.IconManager$getLauncherShortcutIconForPeopleAvatar$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationEntry $entry;
        final /* synthetic */ Ref$ObjectRef<Icon> $icon;
        int label;
        final /* synthetic */ IconManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(IconManager iconManager, Ref$ObjectRef<Icon> ref$ObjectRef, NotificationEntry notificationEntry, Continuation continuation) {
            super(2, continuation);
            this.this$0 = iconManager;
            this.$icon = ref$ObjectRef;
            this.$entry = notificationEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$icon, this.$entry, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            StatusBarIcon statusBarIcon = this.this$0.toStatusBarIcon(this.$icon.element, this.$entry, StatusBarIcon.Type.PeopleAvatar);
            NotificationEntry notificationEntry = this.$entry;
            notificationEntry.mIcons.mPeopleAvatarDescriptor = statusBarIcon;
            return this.this$0.updateIcons(notificationEntry, true);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IconManager$getLauncherShortcutIconForPeopleAvatar$2(NotificationEntry notificationEntry, IconManager iconManager, Continuation continuation) {
        super(2, continuation);
        this.$entry = notificationEntry;
        this.this$0 = iconManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IconManager$getLauncherShortcutIconForPeopleAvatar$2(this.$entry, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IconManager$getLauncherShortcutIconForPeopleAvatar$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [T, android.graphics.drawable.Icon] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ShortcutInfo conversationShortcutInfo = this.$entry.mRanking.getConversationShortcutInfo();
            if (conversationShortcutInfo != null) {
                try {
                    ref$ObjectRef.element = this.this$0.launcherApps.getShortcutIcon(conversationShortcutInfo);
                } catch (Exception e) {
                    Boxing.boxInt(Log.e("IconManager", "Error calling LauncherApps#getShortcutIcon for notification " + this.$entry + ": " + e));
                }
            }
            if (ref$ObjectRef.element != 0) {
                IconManager iconManager = this.this$0;
                CoroutineContext coroutineContext = iconManager.mainCoroutineContext;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(iconManager, ref$ObjectRef, this.$entry, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineContext, anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
