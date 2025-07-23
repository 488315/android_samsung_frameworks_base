package com.android.wm.shell.windowdecor;

import android.graphics.Bitmap;
import com.android.wm.shell.windowdecor.HandleMenu;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HandleMenu$createHandleMenu$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ HandleMenu.HandleMenuView $handleMenuView;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ HandleMenu this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.windowdecor.HandleMenu$createHandleMenu$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ HandleMenu.HandleMenuView $handleMenuView;
        final /* synthetic */ Bitmap $icon;
        final /* synthetic */ CharSequence $name;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(HandleMenu.HandleMenuView handleMenuView, CharSequence charSequence, Bitmap bitmap, Continuation continuation) {
            super(2, continuation);
            this.$handleMenuView = handleMenuView;
            this.$name = charSequence;
            this.$icon = bitmap;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$handleMenuView, this.$name, this.$icon, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
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
            if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                return Unit.INSTANCE;
            }
            HandleMenu.HandleMenuView handleMenuView = this.$handleMenuView;
            handleMenuView.appNameView.setText(this.$name);
            HandleMenu.HandleMenuView handleMenuView2 = this.$handleMenuView;
            handleMenuView2.appIconView.setImageBitmap(this.$icon);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HandleMenu$createHandleMenu$1(HandleMenu handleMenu, HandleMenu.HandleMenuView handleMenuView, Continuation continuation) {
        super(2, continuation);
        this.this$0 = handleMenu;
        this.$handleMenuView = handleMenuView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HandleMenu$createHandleMenu$1 handleMenu$createHandleMenu$1 = new HandleMenu$createHandleMenu$1(this.this$0, this.$handleMenuView, continuation);
        handleMenu$createHandleMenu$1.L$0 = obj;
        return handleMenu$createHandleMenu$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HandleMenu$createHandleMenu$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                return Unit.INSTANCE;
            }
            HandleMenu handleMenu = this.this$0;
            CharSequence name = handleMenu.taskResourceLoader.getName(handleMenu.taskInfo);
            HandleMenu handleMenu2 = this.this$0;
            Bitmap headerIcon = handleMenu2.taskResourceLoader.getHeaderIcon(handleMenu2.taskInfo);
            CoroutineDispatcher coroutineDispatcher = this.this$0.mainDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$handleMenuView, name, headerIcon, null);
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
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
