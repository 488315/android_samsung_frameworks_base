package com.android.systemui.inputmethod.domain.interactor;

import android.os.UserHandle;
import android.view.inputmethod.InputMethodInfo;
import com.android.systemui.inputmethod.data.model.InputMethodModel;
import com.android.systemui.inputmethod.data.repository.InputMethodRepository;
import com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class InputMethodInteractor {
    public final InputMethodRepository repository;

    /* renamed from: com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return InputMethodInteractor.this.hasMultipleEnabledImesOrSubtypes(0, this);
        }
    }

    public InputMethodInteractor(InputMethodRepository inputMethodRepository) {
        this.repository = inputMethodRepository;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b3, code lost:
    
        if (r10 == r1) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object hasMultipleEnabledImesOrSubtypes(int i, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        UserHandle userHandleOf;
        InputMethodInteractor inputMethodInteractor;
        UserHandle userHandle;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objEnabledInputMethods = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objEnabledInputMethods);
            userHandleOf = UserHandle.of(i);
            userHandleOf.getClass();
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = userHandleOf;
            anonymousClass1.label = 1;
            objEnabledInputMethods = ((InputMethodRepositoryImpl) this.repository).enabledInputMethods(userHandleOf, true, anonymousClass1);
            if (objEnabledInputMethods != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 != 1) {
            if (i3 != 2) {
                if (i3 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objEnabledInputMethods);
                boolean z = ((List) objEnabledInputMethods).size() > 1;
                return Boolean.valueOf(z);
            }
            userHandle = (UserHandle) anonymousClass1.L$1;
            inputMethodInteractor = (InputMethodInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objEnabledInputMethods);
            if (((Number) objEnabledInputMethods).intValue() <= 1) {
                InputMethodRepository inputMethodRepository = inputMethodInteractor.repository;
                userHandle.getClass();
                anonymousClass1.L$0 = null;
                anonymousClass1.L$1 = null;
                anonymousClass1.label = 3;
                InputMethodRepositoryImpl inputMethodRepositoryImpl = (InputMethodRepositoryImpl) inputMethodRepository;
                InputMethodInfo currentInputMethodInfoAsUser = inputMethodRepositoryImpl.inputMethodManager.getCurrentInputMethodInfoAsUser(userHandle);
                objEnabledInputMethods = currentInputMethodInfoAsUser == null ? EmptyList.INSTANCE : inputMethodRepositoryImpl.enabledInputMethodSubtypes(userHandle, currentInputMethodInfoAsUser.getId(), false, anonymousClass1);
            }
            return Boolean.valueOf(z);
        }
        UserHandle userHandle2 = (UserHandle) anonymousClass1.L$1;
        InputMethodInteractor inputMethodInteractor2 = (InputMethodInteractor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objEnabledInputMethods);
        userHandleOf = userHandle2;
        this = inputMethodInteractor2;
        final Flow flow = (Flow) objEnabledInputMethods;
        FlowKt__LimitKt$take$$inlined$unsafeFlow$1 flowKt__LimitKt$take$$inlined$unsafeFlow$1Take = FlowKt.take(new Flow() { // from class: com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$$inlined$filter$1

            /* renamed from: com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor$hasMultipleEnabledImesOrSubtypes$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:28:0x006d A[RETURN] */
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
                        InputMethodModel inputMethodModel = (InputMethodModel) obj;
                        if (inputMethodModel.subtypes.isEmpty()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                            }
                        } else {
                            List list = inputMethodModel.subtypes;
                            if (!(list instanceof Collection) || !list.isEmpty()) {
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    if (!((InputMethodModel.Subtype) it.next()).isAuxiliary) {
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                }
                            }
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
        }, 2);
        anonymousClass1.L$0 = this;
        anonymousClass1.L$1 = userHandleOf;
        anonymousClass1.label = 2;
        objEnabledInputMethods = FlowKt.count(flowKt__LimitKt$take$$inlined$unsafeFlow$1Take, anonymousClass1);
        if (objEnabledInputMethods != coroutineSingletons) {
            UserHandle userHandle3 = userHandleOf;
            inputMethodInteractor = this;
            userHandle = userHandle3;
            if (((Number) objEnabledInputMethods).intValue() <= 1) {
            }
            return Boolean.valueOf(z);
        }
        return coroutineSingletons;
    }
}
