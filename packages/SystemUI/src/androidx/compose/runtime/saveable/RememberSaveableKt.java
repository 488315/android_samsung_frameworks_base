package androidx.compose.runtime.saveable;

import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.saveable.SaveableStateRegistryImpl;
import androidx.compose.runtime.snapshots.SnapshotMutableState;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;

/* loaded from: classes.dex */
public abstract class RememberSaveableKt {
    public static final String generateCannotBeSavedErrorMessage(Object obj) {
        return obj + " cannot be saved using the current SaveableStateRegistry. The default implementation only supports types which can be stored inside the Bundle. Please consider implementing a custom Saver for this class and pass it to rememberSaveable().";
    }

    public static final Object rememberSaveable(Object[] objArr, SaverKt$Saver$1 saverKt$Saver$1, String str, Function0 function0, Composer composer, int i, int i2) {
        Object[] objArr2;
        final Object obj;
        Object objConsumeRestored;
        if ((i2 & 2) != 0) {
            saverKt$Saver$1 = SaverKt.AutoSaver;
        }
        final SaverKt$Saver$1 saverKt$Saver$12 = saverKt$Saver$1;
        if ((i2 & 4) != 0) {
            str = null;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.saveable.rememberSaveable (RememberSaveable.kt:69)");
        }
        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
        if (str == null || str.length() == 0) {
            CharsKt__CharJVMKt.checkRadix(36);
            str = Integer.toString(currentCompositeKeyHash, 36);
        }
        final String str2 = str;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        final SaveableStateRegistry saveableStateRegistry = (SaveableStateRegistry) composerImpl.consume(SaveableStateRegistryKt.LocalSaveableStateRegistry);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            Object objMo781invoke = (saveableStateRegistry == null || (objConsumeRestored = saveableStateRegistry.consumeRestored(str2)) == null) ? null : saverKt$Saver$12.$restore.mo781invoke(objConsumeRestored);
            if (objMo781invoke == null) {
                objMo781invoke = function0.invoke();
            }
            objArr2 = objArr;
            SaveableHolder saveableHolder = new SaveableHolder(saverKt$Saver$12, saveableStateRegistry, str2, objMo781invoke, objArr2);
            composerImpl.updateRememberedValue(saveableHolder);
            objRememberedValue = saveableHolder;
        } else {
            objArr2 = objArr;
        }
        final SaveableHolder saveableHolder2 = (SaveableHolder) objRememberedValue;
        Object objInvoke = Arrays.equals(objArr2, saveableHolder2.inputs) ? saveableHolder2.value : null;
        if (objInvoke == null) {
            objInvoke = function0.invoke();
        }
        boolean zChangedInstance = composerImpl.changedInstance(saveableHolder2) | composerImpl.changedInstance(saverKt$Saver$12) | composerImpl.changedInstance(saveableStateRegistry) | composerImpl.changed(str2) | composerImpl.changedInstance(objInvoke) | composerImpl.changedInstance(objArr2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
            final Object[] objArr3 = objArr2;
            obj = objInvoke;
            Function0 function02 = new Function0() { // from class: androidx.compose.runtime.saveable.RememberSaveableKt$rememberSaveable$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean z;
                    SaveableHolder<Object> saveableHolder3 = saveableHolder2;
                    Saver<Object, ? extends Object> saver = saverKt$Saver$12;
                    SaveableStateRegistry saveableStateRegistry2 = saveableStateRegistry;
                    String str3 = str2;
                    Object obj2 = obj;
                    Object[] objArr4 = objArr3;
                    boolean z2 = true;
                    if (saveableHolder3.registry != saveableStateRegistry2) {
                        saveableHolder3.registry = saveableStateRegistry2;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (Intrinsics.areEqual(saveableHolder3.key, str3)) {
                        z2 = z;
                    } else {
                        saveableHolder3.key = str3;
                    }
                    saveableHolder3.saver = saver;
                    saveableHolder3.value = obj2;
                    saveableHolder3.inputs = objArr4;
                    SaveableStateRegistry.Entry entry = saveableHolder3.entry;
                    if (entry != null && z2) {
                        ((SaveableStateRegistryImpl.AnonymousClass3) entry).unregister();
                        saveableHolder3.entry = null;
                        saveableHolder3.register$1();
                    }
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(function02);
            objRememberedValue2 = function02;
        } else {
            obj = objInvoke;
        }
        EffectsKt.SideEffect((Function0) objRememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return obj;
    }

    public static final MutableState rememberSaveable(Object[] objArr, final SaverKt$Saver$1 saverKt$Saver$1, Function0 function0, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.saveable.rememberSaveable (RememberSaveable.kt:122)");
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        Function2 function2 = new Function2() { // from class: androidx.compose.runtime.saveable.RememberSaveableKt$mutableStateSaver$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SaverScope saverScope = (SaverScope) obj;
                MutableState mutableState = (MutableState) obj2;
                if (!(mutableState instanceof SnapshotMutableState)) {
                    throw new IllegalArgumentException("If you use a custom MutableState implementation you have to write a custom Saver and pass it as a saver param to rememberSaveable()");
                }
                Object objSave = saverKt$Saver$1.save(saverScope, mutableState.getValue());
                if (objSave != null) {
                    return SnapshotStateKt.mutableStateOf(objSave, ((SnapshotMutableState) mutableState).getPolicy());
                }
                return null;
            }
        };
        Function1 function1 = new Function1() { // from class: androidx.compose.runtime.saveable.RememberSaveableKt$mutableStateSaver$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object objRestore;
                MutableState mutableState = (MutableState) obj;
                if (!(mutableState instanceof SnapshotMutableState)) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                if (mutableState.getValue() != null) {
                    Saver<Object, Object> saver = saverKt$Saver$1;
                    Object value = mutableState.getValue();
                    value.getClass();
                    objRestore = saver.restore(value);
                } else {
                    objRestore = null;
                }
                return SnapshotStateKt.mutableStateOf(objRestore, ((SnapshotMutableState) mutableState).getPolicy());
            }
        };
        SaverKt$Saver$1 saverKt$Saver$12 = SaverKt.AutoSaver;
        MutableState mutableState = (MutableState) rememberSaveable(objArrCopyOf, new SaverKt$Saver$1(function2, function1), null, function0, composer, 3072, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }
}
