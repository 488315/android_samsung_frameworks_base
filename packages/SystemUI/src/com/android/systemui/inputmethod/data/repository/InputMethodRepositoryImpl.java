package com.android.systemui.inputmethod.data.repository;

import android.os.UserHandle;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import com.android.systemui.inputmethod.data.model.InputMethodModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3;

/* loaded from: classes2.dex */
public final class InputMethodRepositoryImpl implements InputMethodRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final InputMethodManager inputMethodManager;

    /* renamed from: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$1, reason: invalid class name */
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
            return InputMethodRepositoryImpl.this.enabledInputMethodSubtypes(null, null, false, this);
        }
    }

    /* renamed from: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethodSubtypes$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $allowsImplicitlyEnabledSubtypes;
        final /* synthetic */ String $imeId;
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, boolean z, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$imeId = str;
            this.$allowsImplicitlyEnabledSubtypes = z;
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return InputMethodRepositoryImpl.this.new AnonymousClass2(this.$imeId, this.$allowsImplicitlyEnabledSubtypes, this.$user, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return InputMethodRepositoryImpl.this.inputMethodManager.getEnabledInputMethodSubtypeListAsUser(this.$imeId, this.$allowsImplicitlyEnabledSubtypes, this.$user);
        }
    }

    /* renamed from: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$1, reason: invalid class name and case insensitive filesystem */
    final class C08761 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C08761(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return InputMethodRepositoryImpl.this.enabledInputMethods(null, false, this);
        }
    }

    /* renamed from: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$2, reason: invalid class name and case insensitive filesystem */
    final class C08772 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08772(UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return InputMethodRepositoryImpl.this.new C08772(this.$user, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08772) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return InputMethodRepositoryImpl.this.inputMethodManager.getEnabledInputMethodListAsUser(this.$user);
        }
    }

    /* renamed from: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$showInputMethodPicker$2, reason: invalid class name and case insensitive filesystem */
    final class C08782 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $displayId;
        final /* synthetic */ boolean $showAuxiliarySubtypes;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08782(boolean z, int i, Continuation continuation) {
            super(2, continuation);
            this.$showAuxiliarySubtypes = z;
            this.$displayId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return InputMethodRepositoryImpl.this.new C08782(this.$showAuxiliarySubtypes, this.$displayId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08782) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            InputMethodRepositoryImpl.this.inputMethodManager.showInputMethodPickerFromSystem(this.$showAuxiliarySubtypes, this.$displayId);
            return Unit.INSTANCE;
        }
    }

    public InputMethodRepositoryImpl(CoroutineDispatcher coroutineDispatcher, InputMethodManager inputMethodManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.inputMethodManager = inputMethodManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object enabledInputMethodSubtypes(UserHandle userHandle, String str, boolean z, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objWithContext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(str, z, userHandle, null);
            anonymousClass1.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, anonymousClass2, anonymousClass1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext);
        }
        Iterable<InputMethodSubtype> iterable = (Iterable) objWithContext;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        for (InputMethodSubtype inputMethodSubtype : iterable) {
            arrayList.add(new InputMethodModel.Subtype(inputMethodSubtype.getSubtypeId(), inputMethodSubtype.isAuxiliary()));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object enabledInputMethods(final UserHandle userHandle, final boolean z, ContinuationImpl continuationImpl) throws Throwable {
        C08761 c08761;
        if (continuationImpl instanceof C08761) {
            c08761 = (C08761) continuationImpl;
            int i = c08761.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08761.label = i - Integer.MIN_VALUE;
            } else {
                c08761 = new C08761(continuationImpl);
            }
        }
        Object objWithContext = c08761.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08761.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            C08772 c08772 = new C08772(userHandle, null);
            c08761.L$0 = this;
            c08761.L$1 = userHandle;
            c08761.Z$0 = z;
            c08761.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, c08772, c08761);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = c08761.Z$0;
            userHandle = (UserHandle) c08761.L$1;
            this = (InputMethodRepositoryImpl) c08761.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        final FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3 flowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3 = new FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3((Iterable) objWithContext);
        return new Flow() { // from class: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$$inlined$map$1

            /* renamed from: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ boolean $fetchSubtypes$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserHandle $user$inlined;
                public final /* synthetic */ InputMethodRepositoryImpl this$0;

                /* renamed from: com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl$enabledInputMethods$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    int I$0;
                    Object L$0;
                    Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, UserHandle userHandle, boolean z, InputMethodRepositoryImpl inputMethodRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$user$inlined = userHandle;
                    this.$fetchSubtypes$inlined = z;
                    this.this$0 = inputMethodRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:25:0x008c, code lost:
                
                    if (r6.emit(r9, r0) != r1) goto L27;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    String id;
                    FlowCollector flowCollector;
                    List list;
                    int i;
                    String str;
                    FlowCollector flowCollector2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i2 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = anonymousClass1.label;
                    if (i3 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        InputMethodInfo inputMethodInfo = (InputMethodInfo) obj;
                        int identifier = this.$user$inlined.getIdentifier();
                        id = inputMethodInfo.getId();
                        boolean z = this.$fetchSubtypes$inlined;
                        flowCollector = this.$this_unsafeFlow;
                        if (z) {
                            UserHandle userHandle = this.$user$inlined;
                            String id2 = inputMethodInfo.getId();
                            anonymousClass1.L$0 = flowCollector;
                            anonymousClass1.L$1 = id;
                            anonymousClass1.I$0 = identifier;
                            anonymousClass1.label = 1;
                            Object objEnabledInputMethodSubtypes = this.this$0.enabledInputMethodSubtypes(userHandle, id2, true, anonymousClass1);
                            if (objEnabledInputMethodSubtypes != coroutineSingletons) {
                                obj2 = objEnabledInputMethodSubtypes;
                                i = identifier;
                                str = id;
                                flowCollector2 = flowCollector;
                            }
                            return coroutineSingletons;
                        }
                        list = EmptyList.INSTANCE;
                        i = identifier;
                        InputMethodModel inputMethodModel = new InputMethodModel(i, id, list);
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.label = 2;
                    } else {
                        if (i3 != 1) {
                            if (i3 != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                            return Unit.INSTANCE;
                        }
                        i = anonymousClass1.I$0;
                        str = (String) anonymousClass1.L$1;
                        flowCollector2 = (FlowCollector) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                    }
                    list = (List) obj2;
                    flowCollector = flowCollector2;
                    id = str;
                    InputMethodModel inputMethodModel2 = new InputMethodModel(i, id, list);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3.collect(new AnonymousClass2(flowCollector, userHandle, z, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public final Object showInputMethodPicker(int i, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new C08782(false, i, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
