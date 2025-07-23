package com.android.systemui.statusbar.featurepods.media.ui.viewmodel;

import android.content.Context;
import androidx.compose.runtime.State;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.statusbar.featurepods.media.domain.interactor.MediaControlChipInteractor;
import com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipId;
import com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaControlChipViewModel extends ExclusiveActivatable {
    public final Context applicationContext;
    public final State chip$delegate;
    public final Hydrator hydrator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        MediaControlChipViewModel create();
    }

    public MediaControlChipViewModel(Context context, MediaControlChipInteractor mediaControlChipInteractor) {
        this.applicationContext = context;
        Hydrator hydrator = new Hydrator("MediaControlChipViewModel.hydrator", null, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        PopupChipModel.Hidden hidden = new PopupChipModel.Hidden(PopupChipId.MediaControl.INSTANCE, false, 2, null);
        final ReadonlyStateFlow readonlyStateFlow = mediaControlChipInteractor.mediaControlChipModel;
        this.chip$delegate = hydrator.hydratedStateOf("chip", hidden, new Flow() { // from class: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaControlChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, MediaControlChipViewModel mediaControlChipViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaControlChipViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r22, kotlin.coroutines.Continuation r23) {
                    /*
                        Method dump skipped, instructions count: 226
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$onActivated$1 r0 = (com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$onActivated$1 r0 = new com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3d
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.label = r3
            com.android.systemui.lifecycle.Hydrator r4 = r4.hydrator
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L3d
            return r1
        L3d:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
