package com.android.systemui.volume.dialog;

import android.content.Context;
import android.media.AudioManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.volume.CsdWarningDialog;
import com.android.systemui.volume.dialog.dagger.factory.VolumeDialogPluginComponentFactory;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public final class VolumeDialogPlugin implements com.android.systemui.plugins.VolumeDialog {
    public final CoroutineScope applicationCoroutineScope;
    public final AudioManager audioManager;
    public final Context context;
    public final CsdWarningDialog.Factory csdWarningDialogFactory;
    public StandaloneCoroutine job;
    public final VolumeDialogPluginComponentFactory volumeDialogPluginComponentFactory;

    /* renamed from: com.android.systemui.volume.dialog.VolumeDialogPlugin$init$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogPlugin.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                VolumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1 volumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1 = new VolumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1(null, "[Volume]plugin", VolumeDialogPlugin.this);
                this.label = 1;
                if (CoroutineScopeKt.coroutineScope(volumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1, this) == coroutineSingletons) {
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

    public VolumeDialogPlugin(CoroutineScope coroutineScope, Context context, AudioManager audioManager, VolumeDialogPluginComponentFactory volumeDialogPluginComponentFactory, CsdWarningDialog.Factory factory) {
        this.applicationCoroutineScope = coroutineScope;
        this.context = context;
        this.audioManager = audioManager;
        this.volumeDialogPluginComponentFactory = volumeDialogPluginComponentFactory;
        this.csdWarningDialogFactory = factory;
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void destroy() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void init(int i, VolumeDialog.Callback callback) {
        this.job = CoroutineTracingKt.launchTraced$default(this.applicationCoroutineScope, null, null, new AnonymousClass1(null), 7);
    }
}
