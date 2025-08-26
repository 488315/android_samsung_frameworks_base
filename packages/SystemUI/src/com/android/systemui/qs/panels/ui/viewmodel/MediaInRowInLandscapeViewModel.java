package com.android.systemui.qs.panels.ui.viewmodel;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class MediaInRowInLandscapeViewModel extends ExclusiveActivatable {
    public final Hydrator hydrator;
    public final int inLocation;
    public final State inSingleShade$delegate;
    public final State isLandscapeAndLong$delegate;
    public final State isMediaVisible$delegate;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final boolean usingMedia;

    public interface Factory {
        MediaInRowInLandscapeViewModel create(int i);
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MediaInRowInLandscapeViewModel.this.onActivated(this);
        }
    }

    public MediaInRowInLandscapeViewModel(Resources resources, ConfigurationInteractor configurationInteractor, ShadeModeInteractor shadeModeInteractor, MediaHostStatesManager mediaHostStatesManager, boolean z, int i) {
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.usingMedia = z;
        this.inLocation = i;
        Hydrator hydrator = new Hydrator(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "MediaInRowInLanscapeViewModel - "), null, 2, null);
        this.hydrator = hydrator;
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(shadeModeInteractorImpl.shadeMode.$$delegate_0.getValue(), ShadeMode.Single.INSTANCE));
        final ReadonlyStateFlow readonlyStateFlow = shadeModeInteractorImpl.shadeMode;
        this.inSingleShade$delegate = hydrator.hydratedStateOf("inSingleShade", boolValueOf, new Flow() { // from class: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual((ShadeMode) obj, ShadeMode.Single.INSTANCE));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        Configuration configuration = resources.getConfiguration();
        Boolean boolValueOf2 = Boolean.valueOf(configuration.orientation == 2 && (configuration.screenLayout & 48) == 32);
        final Flow flow = ((ConfigurationInteractorImpl) configurationInteractor).configurationValues;
        this.isLandscapeAndLong$delegate = hydrator.hydratedStateOf("isLandscapeAndLong", boolValueOf2, new Flow() { // from class: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Configuration configuration = (Configuration) obj;
                        Boolean boolValueOf = Boolean.valueOf(configuration.orientation == 2 && (configuration.screenLayout & 48) == 32);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isMediaVisible$delegate = hydrator.hydratedStateOf("isMediaVisible", Boolean.FALSE, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MediaInRowInLandscapeViewModel$isMediaVisible$3(this, null), FlowConflatedKt.conflatedCallbackFlow(new MediaInRowInLandscapeViewModel$isMediaVisible$2(this, null))));
    }

    public final boolean getShouldMediaShowInRow() {
        return this.usingMedia && ((Boolean) ((SnapshotMutableStateImpl) this.inSingleShade$delegate).getValue()).booleanValue() && ((Boolean) ((SnapshotMutableStateImpl) this.isLandscapeAndLong$delegate).getValue()).booleanValue() && ((Boolean) ((SnapshotMutableStateImpl) this.isMediaVisible$delegate).getValue()).booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.label = 1;
            if (this.hydrator.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
