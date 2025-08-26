package com.android.systemui.media.mediaoutput.viewmodel;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import androidx.lifecycle.ViewModel;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$5;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$6;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$7;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$8;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$9;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.PreferenceDebugLabsKeys;
import com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes2.dex */
public final class LabsViewModel extends ViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final DataStore dataStore;
    public final DataStoreDebugLabsExt$special$$inlined$map$10 isActionOpenOutputSwitcher;
    public final DataStoreLabsExt$special$$inlined$map$1 isCloseOnTouchOutside;
    public final DataStoreDebugLabsExt$special$$inlined$map$2 isGrayscaleThumbnail;
    public final DataStoreLabsExt$special$$inlined$map$3 isGroupSpeakerDefaultExpanded;
    public final SafeFlow isQuickboardInstalled;
    public final DataStoreDebugLabsExt$special$$inlined$map$1 isShowLabsMenu;
    public final DataStoreDebugLabsExt$special$$inlined$map$5 isSupportDisplayDeviceVolumeControl;
    public final DataStoreDebugLabsExt$special$$inlined$map$8 isSupportDisplayOnlyRemoteDevice;
    public final DataStoreDebugLabsExt$special$$inlined$map$7 isSupportForTransferDuringRouting;
    public final DataStoreDebugLabsExt$special$$inlined$map$9 isSupportForUnsupportedTV;
    public final DataStoreDebugLabsExt$special$$inlined$map$3 isSupportMultipleMediaSession;
    public final DataStoreLabsExt$special$$inlined$map$4 isSupportRecentGroupSpeaker;
    public final DataStoreDebugLabsExt$special$$inlined$map$4 isSupportSelectableBudsTogether;
    public final DataStoreDebugLabsExt$special$$inlined$map$6 isSupportTransferableRoutesWhileConnecting;
    public final DataStoreLabsExt$special$$inlined$map$2 isSupportVolumeInteraction;
    public final PanelInteractor panelInteractor;

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new AnonymousClass1(continuation);
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
                Flow data = LabsViewModel.this.dataStore.getData();
                C03531 c03531 = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        for (Map.Entry entry : ((Preferences) obj2).asMap().entrySet()) {
                            Log.e("LabsViewModel", ((Preferences.Key) entry.getKey()) + " : " + entry.getValue());
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (data.collect(c03531, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$openActivity$1, reason: invalid class name and case insensitive filesystem */
    final class C09421 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isDex;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09421(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isDex = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09421(this.$isDex, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09421) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((PanelInteractorImpl) LabsViewModel.this.panelInteractor).collapsePanels();
            Context context = LabsViewModel.this.context;
            Intent intent = new Intent("com.android.systemui.action.OPEN_MEDIA_OUTPUT");
            LabsViewModel labsViewModel = LabsViewModel.this;
            boolean z = this.$isDex;
            intent.setPackage(labsViewModel.context.getPackageName());
            intent.addFlags(268435456);
            intent.putExtra(z ? "extra_is_dex" : "extra_is_cover", true);
            intent.putExtra("android.intent.extra.FREEFORM_WINDOW", true);
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            LabsViewModel labsViewModel2 = LabsViewModel.this;
            boolean z2 = this.$isDex;
            int i = labsViewModel2.context.getResources().getDisplayMetrics().widthPixels;
            int i2 = labsViewModel2.context.getResources().getDisplayMetrics().heightPixels;
            float f = labsViewModel2.context.getResources().getDisplayMetrics().density;
            Rect rect = new Rect();
            float f2 = i;
            rect.union(MathKt__MathJVMKt.roundToInt(z2 ? f2 * 0.8f : Math.min(f2, 352.0f * f)), MathKt__MathJVMKt.roundToInt(z2 ? i2 * 0.8f : Math.min(i2, f * 337.51f)));
            rect.offset((i - rect.width()) / 2, (i2 - rect.height()) / 2);
            activityOptionsMakeBasic.setLaunchBounds(rect);
            Unit unit = Unit.INSTANCE;
            context.startActivity(intent, activityOptionsMakeBasic.toBundle());
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setActionOpenOutputSwitcher$1, reason: invalid class name and case insensitive filesystem */
    final class C09431 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setActionOpenOutputSwitcher$1$1, reason: invalid class name and collision with other inner class name */
        final class C03541 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03541(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03541 c03541 = new C03541(this.$enabled, continuation);
                c03541.L$0 = obj;
                return c03541;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03541) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.ACTION_OPEN_OUTPUT_SWITCHER, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09431(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09431(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09431) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03541 c03541 = new C03541(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03541, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setCloseOnTouchOutside$1, reason: invalid class name and case insensitive filesystem */
    final class C09441 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setCloseOnTouchOutside$1$1, reason: invalid class name and collision with other inner class name */
        final class C03551 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03551(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03551 c03551 = new C03551(this.$enabled, continuation);
                c03551.L$0 = obj;
                return c03551;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03551) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceLabsKeys.CLOSE_ON_TOUCH_OUTSIDE, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09441(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09441(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09441) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03551 c03551 = new C03551(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03551, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setGrayscaleThumbnail$1, reason: invalid class name and case insensitive filesystem */
    final class C09451 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setGrayscaleThumbnail$1$1, reason: invalid class name and collision with other inner class name */
        final class C03561 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03561(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03561 c03561 = new C03561(this.$enabled, continuation);
                c03561.L$0 = obj;
                return c03561;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03561) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.GRAYSCALE_THUMBNAIL, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09451(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09451(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09451) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03561 c03561 = new C03561(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03561, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setGroupSpeakerDefaultExpanded$1, reason: invalid class name and case insensitive filesystem */
    final class C09461 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setGroupSpeakerDefaultExpanded$1$1, reason: invalid class name and collision with other inner class name */
        final class C03571 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03571(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03571 c03571 = new C03571(this.$enabled, continuation);
                c03571.L$0 = obj;
                return c03571;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03571) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceLabsKeys.GROUP_SPEAKER_DEFAULT_EXPANDED, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09461(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09461(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09461) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03571 c03571 = new C03571(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03571, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setShowLabsMenu$1, reason: invalid class name and case insensitive filesystem */
    final class C09471 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setShowLabsMenu$1$1, reason: invalid class name and collision with other inner class name */
        final class C03581 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03581(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03581 c03581 = new C03581(this.$enabled, continuation);
                c03581.L$0 = obj;
                return c03581;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03581) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SHOW_LABS_MENU, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09471(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09471(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09471) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03581 c03581 = new C03581(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03581, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportDisplayDeviceVolumeControl$1, reason: invalid class name and case insensitive filesystem */
    final class C09481 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportDisplayDeviceVolumeControl$1$1, reason: invalid class name and collision with other inner class name */
        final class C03591 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03591(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03591 c03591 = new C03591(this.$enabled, continuation);
                c03591.L$0 = obj;
                return c03591;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03591) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SUPPORT_DISPLAY_DEVICE_VOLUME_CONTROL, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09481(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09481(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09481) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03591 c03591 = new C03591(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03591, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportDisplayOnlyRemoteDevice$1, reason: invalid class name and case insensitive filesystem */
    final class C09491 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportDisplayOnlyRemoteDevice$1$1, reason: invalid class name and collision with other inner class name */
        final class C03601 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03601(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03601 c03601 = new C03601(this.$enabled, continuation);
                c03601.L$0 = obj;
                return c03601;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03601) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SUPPORT_DISPLAY_ONLY_REMOTE_DEVICE, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09491(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09491(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09491) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03601 c03601 = new C03601(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03601, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportForTransferDuringRouting$1, reason: invalid class name and case insensitive filesystem */
    final class C09501 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportForTransferDuringRouting$1$1, reason: invalid class name and collision with other inner class name */
        final class C03611 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03611(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03611 c03611 = new C03611(this.$enabled, continuation);
                c03611.L$0 = obj;
                return c03611;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03611) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SUPPORT_FOR_TRANSFER_DURING_ROUTING, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09501(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09501(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09501) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03611 c03611 = new C03611(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03611, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportForUnsupportedTV$1, reason: invalid class name and case insensitive filesystem */
    final class C09511 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportForUnsupportedTV$1$1, reason: invalid class name and collision with other inner class name */
        final class C03621 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03621(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03621 c03621 = new C03621(this.$enabled, continuation);
                c03621.L$0 = obj;
                return c03621;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03621) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SUPPORT_FOR_UNSUPPORTED_TV, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09511(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09511(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09511) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03621 c03621 = new C03621(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03621, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportMultipleMediaSession$1, reason: invalid class name and case insensitive filesystem */
    final class C09521 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportMultipleMediaSession$1$1, reason: invalid class name and collision with other inner class name */
        final class C03631 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03631(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03631 c03631 = new C03631(this.$enabled, continuation);
                c03631.L$0 = obj;
                return c03631;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03631) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SUPPORT_MULTIPLE_MEDIA_SESSION, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09521(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09521(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09521) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03631 c03631 = new C03631(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03631, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportRecentGroupSpeaker$1, reason: invalid class name and case insensitive filesystem */
    final class C09531 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportRecentGroupSpeaker$1$1, reason: invalid class name and collision with other inner class name */
        final class C03641 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03641(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03641 c03641 = new C03641(this.$enabled, continuation);
                c03641.L$0 = obj;
                return c03641;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03641) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceLabsKeys.SUPPORT_RECENT_GROUP_SPEAKER, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09531(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09531(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09531) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03641 c03641 = new C03641(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03641, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportSelectableBudsTogether$1, reason: invalid class name and case insensitive filesystem */
    final class C09541 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportSelectableBudsTogether$1$1, reason: invalid class name and collision with other inner class name */
        final class C03651 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03651(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03651 c03651 = new C03651(this.$enabled, continuation);
                c03651.L$0 = obj;
                return c03651;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03651) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SUPPORT_SELECTABLE_BUDS_TOGETHER, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09541(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09541(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09541) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03651 c03651 = new C03651(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03651, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportTransferableRoutesWhileConnecting$1, reason: invalid class name and case insensitive filesystem */
    final class C09551 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportTransferableRoutesWhileConnecting$1$1, reason: invalid class name and collision with other inner class name */
        final class C03661 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03661(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03661 c03661 = new C03661(this.$enabled, continuation);
                c03661.L$0 = obj;
                return c03661;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03661) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDebugLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDebugLabsKeys.SUPPORT_TRANSFERABLE_ROUTES_WHILE_CONNECTING, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09551(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09551(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09551) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03661 c03661 = new C03661(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03661, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportVolumeInteraction$1, reason: invalid class name and case insensitive filesystem */
    final class C09561 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setSupportVolumeInteraction$1$1, reason: invalid class name and collision with other inner class name */
        final class C03671 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $enabled;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03671(boolean z, Continuation continuation) {
                super(2, continuation);
                this.$enabled = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03671 c03671 = new C03671(this.$enabled, continuation);
                c03671.L$0 = obj;
                return c03671;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03671) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceLabsKeys.INSTANCE.getClass();
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceLabsKeys.SUPPORT_VOLUME_INTERACTION, Boolean.valueOf(this.$enabled));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09561(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return LabsViewModel.this.new C09561(this.$enabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09561) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = LabsViewModel.this.dataStore;
                C03671 c03671 = new C03671(this.$enabled, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03671, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10] */
    public LabsViewModel(Context context, PanelInteractor panelInteractor, DataStore dataStore) {
        this.context = context;
        this.panelInteractor = panelInteractor;
        this.dataStore = dataStore;
        DataStoreDebugLabsExt.INSTANCE.getClass();
        final Flow data = dataStore.getData();
        this.isShowLabsMenu = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        PreferenceDebugLabsKeys.INSTANCE.getClass();
                        Boolean bool = (Boolean) ((Preferences) obj).get(PreferenceDebugLabsKeys.SHOW_LABS_MENU);
                        Boolean boolValueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : false);
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
                Object objCollect = data.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        DataStoreLabsExt.INSTANCE.getClass();
        final Flow data2 = dataStore.getData();
        this.isCloseOnTouchOutside = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        PreferenceLabsKeys.INSTANCE.getClass();
                        Boolean bool = (Boolean) ((Preferences) obj).get(PreferenceLabsKeys.CLOSE_ON_TOUCH_OUTSIDE);
                        Boolean boolValueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : true);
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
                Object objCollect = data2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow data3 = dataStore.getData();
        this.isSupportVolumeInteraction = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        PreferenceLabsKeys.INSTANCE.getClass();
                        Boolean bool = (Boolean) ((Preferences) obj).get(PreferenceLabsKeys.SUPPORT_VOLUME_INTERACTION);
                        Boolean boolValueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : true);
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
                Object objCollect = data3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow data4 = dataStore.getData();
        this.isGroupSpeakerDefaultExpanded = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        PreferenceLabsKeys.INSTANCE.getClass();
                        Boolean bool = (Boolean) ((Preferences) obj).get(PreferenceLabsKeys.GROUP_SPEAKER_DEFAULT_EXPANDED);
                        Boolean boolValueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : true);
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
                Object objCollect = data4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow data5 = dataStore.getData();
        this.isSupportRecentGroupSpeaker = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreLabsExt$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        PreferenceLabsKeys.INSTANCE.getClass();
                        Boolean bool = (Boolean) ((Preferences) obj).get(PreferenceLabsKeys.SUPPORT_RECENT_GROUP_SPEAKER);
                        Boolean boolValueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : false);
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
                Object objCollect = data5.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isGrayscaleThumbnail = new DataStoreDebugLabsExt$special$$inlined$map$2(dataStore.getData());
        final Flow data6 = dataStore.getData();
        this.isSupportMultipleMediaSession = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        PreferenceDebugLabsKeys.INSTANCE.getClass();
                        Boolean bool = (Boolean) ((Preferences) obj).get(PreferenceDebugLabsKeys.SUPPORT_MULTIPLE_MEDIA_SESSION);
                        Boolean boolValueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : false);
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
                Object objCollect = data6.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isSupportSelectableBudsTogether = new DataStoreDebugLabsExt$special$$inlined$map$4(dataStore.getData());
        this.isSupportDisplayDeviceVolumeControl = new DataStoreDebugLabsExt$special$$inlined$map$5(dataStore.getData());
        this.isSupportTransferableRoutesWhileConnecting = new DataStoreDebugLabsExt$special$$inlined$map$6(dataStore.getData());
        this.isSupportForTransferDuringRouting = new DataStoreDebugLabsExt$special$$inlined$map$7(dataStore.getData());
        this.isSupportDisplayOnlyRemoteDevice = new DataStoreDebugLabsExt$special$$inlined$map$8(dataStore.getData());
        this.isSupportForUnsupportedTV = new DataStoreDebugLabsExt$special$$inlined$map$9(dataStore.getData());
        this.isQuickboardInstalled = new SafeFlow(new LabsViewModel$isQuickboardInstalled$1(this, null));
        final Flow data7 = dataStore.getData();
        this.isActionOpenOutputSwitcher = new Flow() { // from class: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10

            /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10$2$1, reason: invalid class name */
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
                        PreferenceDebugLabsKeys.INSTANCE.getClass();
                        Boolean bool = (Boolean) ((Preferences) obj).get(PreferenceDebugLabsKeys.ACTION_OPEN_OUTPUT_SWITCHER);
                        Boolean boolValueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : false);
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
                Object objCollect = data7.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Log.d("LabsViewModel", "init()");
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("LabsViewModel", "onCleared()");
    }

    public final void openActivity(boolean z) {
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09421(z, null), 3);
    }

    public final void setActionOpenOutputSwitcher(boolean z) {
        Log.d("LabsViewModel", "setActionOpenOutputSwitcher() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09431(z, null), 3);
    }

    public final void setCloseOnTouchOutside(boolean z) {
        Log.d("LabsViewModel", "setCloseOnTouchOutside() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09441(z, null), 3);
    }

    public final void setGrayscaleThumbnail(boolean z) {
        Log.d("LabsViewModel", "setGrayscaleThumbnail() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09451(z, null), 3);
    }

    public final void setGroupSpeakerDefaultExpanded(boolean z) {
        Log.d("LabsViewModel", "setGroupSpeakerDefaultExpanded() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09461(z, null), 3);
    }

    public final void setShowLabsMenu(boolean z) {
        Log.d("LabsViewModel", "setShowLabsMenu()");
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09471(z, null), 3);
    }

    public final void setSupportDisplayDeviceVolumeControl(boolean z) {
        Log.d("LabsViewModel", "setSupportDisplayDeviceVolumeControl() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09481(z, null), 3);
    }

    public final void setSupportDisplayOnlyRemoteDevice(boolean z) {
        Log.d("LabsViewModel", "setSupportDisplayOnlyRemoteDevice() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09491(z, null), 3);
    }

    public final void setSupportForTransferDuringRouting(boolean z) {
        Log.d("LabsViewModel", "setSupportForTransferDuringRouting() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09501(z, null), 3);
    }

    public final void setSupportForUnsupportedTV(boolean z) {
        Log.d("LabsViewModel", "setCastingPriority() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09511(z, null), 3);
    }

    public final void setSupportMultipleMediaSession(boolean z) {
        Log.d("LabsViewModel", "setSupportMultipleMediaSession() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09521(z, null), 3);
    }

    public final void setSupportRecentGroupSpeaker(boolean z) {
        Log.d("LabsViewModel", "setGroupSpeakerDefaultExpandedsetSupportRecentGroupSpeaker() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09531(z, null), 3);
    }

    public final void setSupportSelectableBudsTogether(boolean z) {
        Log.d("LabsViewModel", "setSupportSelectableBudsTogether() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09541(z, null), 3);
    }

    public final void setSupportTransferableRoutesWhileConnecting(boolean z) {
        Log.d("LabsViewModel", "setSupportTransferableRoutesWhileConnecting() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09551(z, null), 3);
    }

    public final void setSupportVolumeInteraction(boolean z) {
        Log.d("LabsViewModel", "setSupportVolumeInteraction() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09561(z, null), 3);
    }
}
