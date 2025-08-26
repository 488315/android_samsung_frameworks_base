package com.android.systemui.keyboard.shortcut.ui.viewmodel;

import android.content.Context;
import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key;
import com.android.systemui.R;
import com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult;
import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutCustomizationInteractor;
import com.android.systemui.keyboard.shortcut.extensions.ShortcutKeyExtensionsKt;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class ShortcutCustomizationViewModel extends ExclusiveActivatable {
    public static final List SUPPORTED_MODIFIERS;
    public final StateFlowImpl _shortcutCustomizationUiState;
    public final Context context;
    public KeyEvent keyDownEventCache;
    public final ShortcutCustomizationInteractor shortcutCustomizationInteractor;
    public final ReadonlyStateFlow shortcutCustomizationUiState;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        ShortcutCustomizationViewModel create();
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ShortcutCustomizationRequestResult.values().length];
            try {
                iArr[ShortcutCustomizationRequestResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ShortcutCustomizationRequestResult.ERROR_RESERVED_COMBINATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ShortcutCustomizationRequestResult.ERROR_OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$deleteShortcutCurrentlyBeingCustomized$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return ShortcutCustomizationViewModel.this.deleteShortcutCurrentlyBeingCustomized(this);
        }
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$1, reason: invalid class name and case insensitive filesystem */
    final class C08891 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C08891(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutCustomizationViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onActivated$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public AnonymousClass2() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /* JADX WARN: Type inference failed for: r9v6, types: [java.util.List] */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(List list, Continuation continuation) throws Throwable {
            ShortcutCustomizationViewModel$onActivated$2$emit$1 shortcutCustomizationViewModel$onActivated$2$emit$1;
            ArrayList arrayList;
            Object value;
            Object objCopy$default;
            if (continuation instanceof ShortcutCustomizationViewModel$onActivated$2$emit$1) {
                shortcutCustomizationViewModel$onActivated$2$emit$1 = (ShortcutCustomizationViewModel$onActivated$2$emit$1) continuation;
                int i = shortcutCustomizationViewModel$onActivated$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    shortcutCustomizationViewModel$onActivated$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    shortcutCustomizationViewModel$onActivated$2$emit$1 = new ShortcutCustomizationViewModel$onActivated$2$emit$1(this, continuation);
                }
            }
            Object objAccess$getErrorMessageForPressedKeys = shortcutCustomizationViewModel$onActivated$2$emit$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = shortcutCustomizationViewModel$onActivated$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(objAccess$getErrorMessageForPressedKeys);
                List list2 = ShortcutCustomizationViewModel.SUPPORTED_MODIFIERS;
                ShortcutCustomizationViewModel shortcutCustomizationViewModel = ShortcutCustomizationViewModel.this;
                shortcutCustomizationViewModel.getClass();
                arrayList = new ArrayList();
                for (Object obj : list) {
                    shortcutCustomizationViewModel.shortcutCustomizationInteractor.getClass();
                    ShortcutHelperKeys.INSTANCE.getClass();
                    if (!Intrinsics.areEqual((ShortcutKey) obj, new ShortcutKey.Icon.ResIdIcon(ShortcutHelperKeys.metaModifierIconResId))) {
                        arrayList.add(obj);
                    }
                }
                shortcutCustomizationViewModel$onActivated$2$emit$1.L$0 = this;
                shortcutCustomizationViewModel$onActivated$2$emit$1.L$1 = arrayList;
                shortcutCustomizationViewModel$onActivated$2$emit$1.label = 1;
                objAccess$getErrorMessageForPressedKeys = ShortcutCustomizationViewModel.access$getErrorMessageForPressedKeys(shortcutCustomizationViewModel, arrayList, shortcutCustomizationViewModel$onActivated$2$emit$1);
                if (objAccess$getErrorMessageForPressedKeys == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ?? r9 = (List) shortcutCustomizationViewModel$onActivated$2$emit$1.L$1;
                AnonymousClass2 anonymousClass2 = (AnonymousClass2) shortcutCustomizationViewModel$onActivated$2$emit$1.L$0;
                ResultKt.throwOnFailure(objAccess$getErrorMessageForPressedKeys);
                arrayList = r9;
                this = anonymousClass2;
            }
            String str = (String) objAccess$getErrorMessageForPressedKeys;
            ShortcutCustomizationViewModel shortcutCustomizationViewModel2 = ShortcutCustomizationViewModel.this;
            StateFlowImpl stateFlowImpl = shortcutCustomizationViewModel2._shortcutCustomizationUiState;
            do {
                value = stateFlowImpl.getValue();
                objCopy$default = (ShortcutCustomizationUiState) value;
                if (objCopy$default instanceof ShortcutCustomizationUiState.AddShortcutDialog) {
                    ShortcutCustomizationUiState.AddShortcutDialog addShortcutDialog = (ShortcutCustomizationUiState.AddShortcutDialog) objCopy$default;
                    String string = shortcutCustomizationViewModel2.context.getString(R.string.shortcut_helper_key_combinations_and_conjunction);
                    StringBuilder sb = new StringBuilder();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        String contentDescription = ShortcutKeyExtensionsKt.toContentDescription((ShortcutKey) it.next(), shortcutCustomizationViewModel2.context);
                        if (contentDescription != null) {
                            if (sb.length() > 0) {
                                sb.append(", " + string + " ");
                            }
                            sb.append(contentDescription);
                        }
                    }
                    objCopy$default = ShortcutCustomizationUiState.AddShortcutDialog.copy$default(addShortcutDialog, str, arrayList, sb.toString(), 5);
                }
            } while (!stateFlowImpl.compareAndSet(value, objCopy$default));
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$onSetShortcut$1, reason: invalid class name and case insensitive filesystem */
    final class C08901 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08901(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutCustomizationViewModel.this.onSetShortcut(this);
        }
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutCustomizationViewModel$resetAllCustomShortcuts$1, reason: invalid class name and case insensitive filesystem */
    final class C08911 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08911(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutCustomizationViewModel.this.resetAllCustomShortcuts(this);
        }
    }

    static {
        new Companion(null);
        Key.Companion companion = Key.Companion;
        companion.getClass();
        Key keyM577boximpl = Key.m577boximpl(Key.MetaLeft);
        companion.getClass();
        Key keyM577boximpl2 = Key.m577boximpl(Key.MetaRight);
        companion.getClass();
        Key keyM577boximpl3 = Key.m577boximpl(Key.CtrlRight);
        companion.getClass();
        Key keyM577boximpl4 = Key.m577boximpl(Key.CtrlLeft);
        companion.getClass();
        Key keyM577boximpl5 = Key.m577boximpl(Key.AltLeft);
        companion.getClass();
        Key keyM577boximpl6 = Key.m577boximpl(Key.AltRight);
        companion.getClass();
        Key keyM577boximpl7 = Key.m577boximpl(Key.ShiftLeft);
        companion.getClass();
        Key keyM577boximpl8 = Key.m577boximpl(Key.ShiftRight);
        companion.getClass();
        Key keyM577boximpl9 = Key.m577boximpl(Key.Function);
        companion.getClass();
        SUPPORTED_MODIFIERS = Arrays.asList(keyM577boximpl, keyM577boximpl2, keyM577boximpl3, keyM577boximpl4, keyM577boximpl5, keyM577boximpl6, keyM577boximpl7, keyM577boximpl8, keyM577boximpl9, Key.m577boximpl(Key.Symbol));
    }

    public ShortcutCustomizationViewModel(Context context, ShortcutCustomizationInteractor shortcutCustomizationInteractor) {
        this.context = context;
        this.shortcutCustomizationInteractor = shortcutCustomizationInteractor;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(ShortcutCustomizationUiState.Inactive.INSTANCE);
        this._shortcutCustomizationUiState = stateFlowImplMutableStateFlow;
        this.shortcutCustomizationUiState = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$getErrorMessageForPressedKeys(ShortcutCustomizationViewModel shortcutCustomizationViewModel, List list, ContinuationImpl continuationImpl) throws Throwable {
        ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1 shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1;
        ShortcutCustomizationViewModel shortcutCustomizationViewModel2;
        boolean z;
        shortcutCustomizationViewModel.getClass();
        if (continuationImpl instanceof ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1) {
            shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1 = (ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1) continuationImpl;
            int i = shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.label = i - Integer.MIN_VALUE;
            } else {
                shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1 = new ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1(shortcutCustomizationViewModel, continuationImpl);
            }
        }
        Object objIsSelectedKeyCombinationAvailable = shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsSelectedKeyCombinationAvailable);
            boolean zIsEmpty = ((ArrayList) list).isEmpty();
            shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.L$0 = shortcutCustomizationViewModel;
            shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.Z$0 = zIsEmpty;
            shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.label = 1;
            objIsSelectedKeyCombinationAvailable = shortcutCustomizationViewModel.shortcutCustomizationInteractor.customShortcutRepository.isSelectedKeyCombinationAvailable(shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1);
            if (objIsSelectedKeyCombinationAvailable == coroutineSingletons) {
                return coroutineSingletons;
            }
            shortcutCustomizationViewModel2 = shortcutCustomizationViewModel;
            z = zIsEmpty;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.Z$0;
            shortcutCustomizationViewModel2 = (ShortcutCustomizationViewModel) shortcutCustomizationViewModel$getErrorMessageForPressedKeys$1.L$0;
            ResultKt.throwOnFailure(objIsSelectedKeyCombinationAvailable);
        }
        if (z || ((Boolean) objIsSelectedKeyCombinationAvailable).booleanValue()) {
            return "";
        }
        String string = shortcutCustomizationViewModel2.context.getString(R.string.shortcut_customizer_key_combination_in_use_error_message);
        string.getClass();
        return string;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object deleteShortcutCurrentlyBeingCustomized(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Object value;
        Object obj;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objDeleteShortcutCurrentlyBeingCustomized = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objDeleteShortcutCurrentlyBeingCustomized);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objDeleteShortcutCurrentlyBeingCustomized = this.shortcutCustomizationInteractor.customShortcutRepository.deleteShortcutCurrentlyBeingCustomized(anonymousClass1);
            if (objDeleteShortcutCurrentlyBeingCustomized == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (ShortcutCustomizationViewModel) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objDeleteShortcutCurrentlyBeingCustomized);
        }
        ShortcutCustomizationRequestResult shortcutCustomizationRequestResult = (ShortcutCustomizationRequestResult) objDeleteShortcutCurrentlyBeingCustomized;
        StateFlowImpl stateFlowImpl = this._shortcutCustomizationUiState;
        do {
            value = stateFlowImpl.getValue();
            obj = (ShortcutCustomizationUiState) value;
            if (WhenMappings.$EnumSwitchMapping$0[shortcutCustomizationRequestResult.ordinal()] == 1) {
                obj = ShortcutCustomizationUiState.Inactive.INSTANCE;
            }
        } while (!stateFlowImpl.compareAndSet(value, obj));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        C08891 c08891;
        if (continuation instanceof C08891) {
            c08891 = (C08891) continuation;
            int i = c08891.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08891.label = i - Integer.MIN_VALUE;
            } else {
                c08891 = new C08891(continuation);
            }
        }
        Object obj = c08891.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08891.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow readonlyStateFlow = this.shortcutCustomizationInteractor.pressedKeys;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2();
            c08891.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(anonymousClass2, c08891) == coroutineSingletons) {
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onSetShortcut(ContinuationImpl continuationImpl) {
        C08901 c08901;
        Object value;
        Object objCopy$default;
        if (continuationImpl instanceof C08901) {
            c08901 = (C08901) continuationImpl;
            int i = c08901.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08901.label = i - Integer.MIN_VALUE;
            } else {
                c08901 = new C08901(continuationImpl);
            }
        }
        Object objConfirmAndSetShortcutCurrentlyBeingCustomized = c08901.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08901.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objConfirmAndSetShortcutCurrentlyBeingCustomized);
            c08901.L$0 = this;
            c08901.label = 1;
            objConfirmAndSetShortcutCurrentlyBeingCustomized = this.shortcutCustomizationInteractor.customShortcutRepository.confirmAndSetShortcutCurrentlyBeingCustomized(c08901);
            if (objConfirmAndSetShortcutCurrentlyBeingCustomized == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (ShortcutCustomizationViewModel) c08901.L$0;
            ResultKt.throwOnFailure(objConfirmAndSetShortcutCurrentlyBeingCustomized);
        }
        ShortcutCustomizationRequestResult shortcutCustomizationRequestResult = (ShortcutCustomizationRequestResult) objConfirmAndSetShortcutCurrentlyBeingCustomized;
        StateFlowImpl stateFlowImpl = this._shortcutCustomizationUiState;
        do {
            value = stateFlowImpl.getValue();
            objCopy$default = (ShortcutCustomizationUiState) value;
            int i3 = WhenMappings.$EnumSwitchMapping$0[shortcutCustomizationRequestResult.ordinal()];
            if (i3 == 1) {
                objCopy$default = ShortcutCustomizationUiState.Inactive.INSTANCE;
            } else if (i3 == 2) {
                String string = this.context.getString(R.string.shortcut_customizer_key_combination_in_use_error_message);
                ShortcutCustomizationUiState.AddShortcutDialog addShortcutDialog = objCopy$default instanceof ShortcutCustomizationUiState.AddShortcutDialog ? (ShortcutCustomizationUiState.AddShortcutDialog) objCopy$default : null;
                if (addShortcutDialog != null) {
                    objCopy$default = ShortcutCustomizationUiState.AddShortcutDialog.copy$default(addShortcutDialog, string, null, null, 29);
                }
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                String string2 = this.context.getString(R.string.shortcut_customizer_generic_error_message);
                ShortcutCustomizationUiState.AddShortcutDialog addShortcutDialog2 = objCopy$default instanceof ShortcutCustomizationUiState.AddShortcutDialog ? (ShortcutCustomizationUiState.AddShortcutDialog) objCopy$default : null;
                if (addShortcutDialog2 != null) {
                    objCopy$default = ShortcutCustomizationUiState.AddShortcutDialog.copy$default(addShortcutDialog2, string2, null, null, 29);
                }
            }
        } while (!stateFlowImpl.compareAndSet(value, objCopy$default));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object resetAllCustomShortcuts(ContinuationImpl continuationImpl) {
        C08911 c08911;
        Object value;
        Object obj;
        if (continuationImpl instanceof C08911) {
            c08911 = (C08911) continuationImpl;
            int i = c08911.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08911.label = i - Integer.MIN_VALUE;
            } else {
                c08911 = new C08911(continuationImpl);
            }
        }
        Object objResetAllCustomShortcuts = c08911.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08911.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objResetAllCustomShortcuts);
            c08911.L$0 = this;
            c08911.label = 1;
            objResetAllCustomShortcuts = this.shortcutCustomizationInteractor.customShortcutRepository.resetAllCustomShortcuts(c08911);
            if (objResetAllCustomShortcuts == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (ShortcutCustomizationViewModel) c08911.L$0;
            ResultKt.throwOnFailure(objResetAllCustomShortcuts);
        }
        ShortcutCustomizationRequestResult shortcutCustomizationRequestResult = (ShortcutCustomizationRequestResult) objResetAllCustomShortcuts;
        StateFlowImpl stateFlowImpl = this._shortcutCustomizationUiState;
        do {
            value = stateFlowImpl.getValue();
            obj = (ShortcutCustomizationUiState) value;
            if (WhenMappings.$EnumSwitchMapping$0[shortcutCustomizationRequestResult.ordinal()] == 1) {
                obj = ShortcutCustomizationUiState.Inactive.INSTANCE;
            }
        } while (!stateFlowImpl.compareAndSet(value, obj));
        return Unit.INSTANCE;
    }
}
