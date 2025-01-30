package com.android.systemui.keyguard;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.p009ui.preview.KeyguardRemotePreviewManager;
import com.android.systemui.keyguard.shared.model.KeyguardPickerFlag;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomizationProvider extends ContentProvider implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback;
    public KeyguardQuickAffordanceInteractor interactor;
    public CoroutineDispatcher mainDispatcher;
    public KeyguardRemotePreviewManager previewManager;
    public final UriMatcher uriMatcher;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CustomizationProvider() {
        UriMatcher uriMatcher = new UriMatcher(-1);
        CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
        uriMatcher.addURI("com.android.systemui.customization", CustomizationProviderContract.LockScreenQuickAffordances.qualifiedTablePath("slots"), 1);
        uriMatcher.addURI("com.android.systemui.customization", CustomizationProviderContract.LockScreenQuickAffordances.qualifiedTablePath("affordances"), 2);
        uriMatcher.addURI("com.android.systemui.customization", CustomizationProviderContract.LockScreenQuickAffordances.qualifiedTablePath("selections"), 3);
        uriMatcher.addURI("com.android.systemui.customization", "flags", 4);
        this.uriMatcher = uriMatcher;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$deleteSelection(CustomizationProvider customizationProvider, Uri uri, String[] strArr, Continuation continuation) {
        CustomizationProvider$deleteSelection$1 customizationProvider$deleteSelection$1;
        Object obj;
        int i;
        Pair pair;
        CustomizationProvider customizationProvider2;
        String str;
        Uri uri2;
        String str2;
        ContentResolver contentResolver;
        customizationProvider.getClass();
        if (continuation instanceof CustomizationProvider$deleteSelection$1) {
            customizationProvider$deleteSelection$1 = (CustomizationProvider$deleteSelection$1) continuation;
            int i2 = customizationProvider$deleteSelection$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                customizationProvider$deleteSelection$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = customizationProvider$deleteSelection$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = customizationProvider$deleteSelection$1.label;
                int i3 = 0;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (strArr == null) {
                        throw new IllegalArgumentException("Cannot delete selection, selection arguments not included!");
                    }
                    int length = strArr.length;
                    if (length == 1) {
                        pair = new Pair(strArr[0], null);
                    } else {
                        if (length != 2) {
                            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Cannot delete selection, selection arguments has wrong size, expected to have 1 or 2 arguments, had ", strArr.length, " instead!"));
                        }
                        pair = new Pair(strArr[0], strArr[1]);
                    }
                    String str3 = (String) pair.component1();
                    String str4 = (String) pair.component2();
                    KeyguardQuickAffordanceInteractor interactor = customizationProvider.getInteractor();
                    customizationProvider$deleteSelection$1.L$0 = customizationProvider;
                    customizationProvider$deleteSelection$1.L$1 = uri;
                    customizationProvider$deleteSelection$1.L$2 = str3;
                    customizationProvider$deleteSelection$1.L$3 = str4;
                    customizationProvider$deleteSelection$1.label = 1;
                    Object unselect = interactor.unselect(customizationProvider$deleteSelection$1);
                    if (unselect == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    customizationProvider2 = customizationProvider;
                    str = str4;
                    obj = unselect;
                    uri2 = uri;
                    str2 = str3;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    str = (String) customizationProvider$deleteSelection$1.L$3;
                    str2 = (String) customizationProvider$deleteSelection$1.L$2;
                    uri2 = (Uri) customizationProvider$deleteSelection$1.L$1;
                    customizationProvider2 = (CustomizationProvider) customizationProvider$deleteSelection$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (((Boolean) obj).booleanValue()) {
                    CustomizationProvider$$ExternalSyntheticOutline0.m135m("Failed to unselect ", str, " for slot ", str2, "KeyguardQuickAffordanceProvider");
                } else {
                    android.util.Log.d("KeyguardQuickAffordanceProvider", "Successfully unselected " + str + " for slot " + str2);
                    Context context = customizationProvider2.getContext();
                    if (context != null && (contentResolver = context.getContentResolver()) != null) {
                        contentResolver.notifyChange(uri2, null);
                    }
                    i3 = 1;
                }
                return new Integer(i3);
            }
        }
        customizationProvider$deleteSelection$1 = new CustomizationProvider$deleteSelection$1(customizationProvider, continuation);
        obj = customizationProvider$deleteSelection$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = customizationProvider$deleteSelection$1.label;
        int i32 = 0;
        if (i != 0) {
        }
        if (((Boolean) obj).booleanValue()) {
        }
        return new Integer(i32);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$insertSelection(CustomizationProvider customizationProvider, ContentValues contentValues, Continuation continuation) {
        CustomizationProvider$insertSelection$1 customizationProvider$insertSelection$1;
        Object obj;
        int i;
        CustomizationProvider customizationProvider2;
        String str;
        String str2;
        ContentResolver contentResolver;
        customizationProvider.getClass();
        if (continuation instanceof CustomizationProvider$insertSelection$1) {
            customizationProvider$insertSelection$1 = (CustomizationProvider$insertSelection$1) continuation;
            int i2 = customizationProvider$insertSelection$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                customizationProvider$insertSelection$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = customizationProvider$insertSelection$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = customizationProvider$insertSelection$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (contentValues == null) {
                        throw new IllegalArgumentException("Cannot insert selection, no values passed in!");
                    }
                    if (!contentValues.containsKey("slot_id")) {
                        throw new IllegalArgumentException("Cannot insert selection, \"slot_id\" not specified!");
                    }
                    if (!contentValues.containsKey("affordance_id")) {
                        throw new IllegalArgumentException("Cannot insert selection, \"affordance_id\" not specified!");
                    }
                    String asString = contentValues.getAsString("slot_id");
                    String asString2 = contentValues.getAsString("affordance_id");
                    if (asString == null || asString.length() == 0) {
                        throw new IllegalArgumentException("Cannot insert selection, slot ID was empty!");
                    }
                    if (asString2 == null || asString2.length() == 0) {
                        throw new IllegalArgumentException("Cannot insert selection, affordance ID was empty!");
                    }
                    KeyguardQuickAffordanceInteractor interactor = customizationProvider.getInteractor();
                    customizationProvider$insertSelection$1.L$0 = customizationProvider;
                    customizationProvider$insertSelection$1.L$1 = asString;
                    customizationProvider$insertSelection$1.L$2 = asString2;
                    customizationProvider$insertSelection$1.label = 1;
                    Object select = interactor.select(customizationProvider$insertSelection$1);
                    if (select == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    customizationProvider2 = customizationProvider;
                    str = asString2;
                    str2 = asString;
                    obj = select;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    str = (String) customizationProvider$insertSelection$1.L$2;
                    str2 = (String) customizationProvider$insertSelection$1.L$1;
                    customizationProvider2 = (CustomizationProvider) customizationProvider$insertSelection$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (((Boolean) obj).booleanValue()) {
                    CustomizationProvider$$ExternalSyntheticOutline0.m135m("Failed to select ", str, " for slot ", str2, "KeyguardQuickAffordanceProvider");
                    return null;
                }
                android.util.Log.d("KeyguardQuickAffordanceProvider", "Successfully selected " + str + " for slot " + str2);
                Context context = customizationProvider2.getContext();
                if (context != null && (contentResolver = context.getContentResolver()) != null) {
                    CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
                    contentResolver.notifyChange(CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, null);
                }
                CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
                return CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI;
            }
        }
        customizationProvider$insertSelection$1 = new CustomizationProvider$insertSelection$1(customizationProvider, continuation);
        obj = customizationProvider$insertSelection$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = customizationProvider$insertSelection$1.label;
        if (i != 0) {
        }
        if (((Boolean) obj).booleanValue()) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$queryAffordances(CustomizationProvider customizationProvider, Continuation continuation) {
        CustomizationProvider$queryAffordances$1 customizationProvider$queryAffordances$1;
        int i;
        MatrixCursor matrixCursor;
        customizationProvider.getClass();
        if (continuation instanceof CustomizationProvider$queryAffordances$1) {
            customizationProvider$queryAffordances$1 = (CustomizationProvider$queryAffordances$1) continuation;
            int i2 = customizationProvider$queryAffordances$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                customizationProvider$queryAffordances$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = customizationProvider$queryAffordances$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = customizationProvider$queryAffordances$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"id", "name", "icon", "is_enabled", "enablement_explanation", "enablement_action_text", "enablement_action_intent", "configure_intent"});
                    KeyguardQuickAffordanceInteractor interactor = customizationProvider.getInteractor();
                    customizationProvider$queryAffordances$1.L$0 = matrixCursor2;
                    customizationProvider$queryAffordances$1.L$1 = matrixCursor2;
                    customizationProvider$queryAffordances$1.label = 1;
                    Object affordancePickerRepresentations = ((KeyguardQuickAffordanceRepository) interactor.repository.get()).getAffordancePickerRepresentations(customizationProvider$queryAffordances$1);
                    if (affordancePickerRepresentations != obj2) {
                        obj2 = matrixCursor2;
                        obj = affordancePickerRepresentations;
                        matrixCursor = obj2;
                    }
                    return obj2;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                MatrixCursor matrixCursor3 = (MatrixCursor) customizationProvider$queryAffordances$1.L$1;
                MatrixCursor matrixCursor4 = (MatrixCursor) customizationProvider$queryAffordances$1.L$0;
                ResultKt.throwOnFailure(obj);
                obj2 = matrixCursor4;
                matrixCursor = matrixCursor3;
                for (KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation : (Iterable) obj) {
                    Object[] objArr = new Object[8];
                    objArr[0] = keyguardQuickAffordancePickerRepresentation.f301id;
                    objArr[1] = keyguardQuickAffordancePickerRepresentation.name;
                    objArr[2] = new Integer(keyguardQuickAffordancePickerRepresentation.iconResourceId);
                    objArr[3] = new Integer(keyguardQuickAffordancePickerRepresentation.isEnabled ? 1 : 0);
                    objArr[4] = keyguardQuickAffordancePickerRepresentation.explanation;
                    objArr[5] = keyguardQuickAffordancePickerRepresentation.actionText;
                    String str = null;
                    Intent intent = keyguardQuickAffordancePickerRepresentation.actionIntent;
                    objArr[6] = intent != null ? intent.toUri(1) : null;
                    Intent intent2 = keyguardQuickAffordancePickerRepresentation.configureIntent;
                    if (intent2 != null) {
                        str = intent2.toUri(1);
                    }
                    objArr[7] = str;
                    matrixCursor.addRow(objArr);
                }
                return obj2;
            }
        }
        customizationProvider$queryAffordances$1 = new CustomizationProvider$queryAffordances$1(customizationProvider, continuation);
        Object obj3 = customizationProvider$queryAffordances$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = customizationProvider$queryAffordances$1.label;
        if (i != 0) {
        }
        while (r13.hasNext()) {
        }
        return obj22;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006d A[LOOP:0: B:11:0x0067->B:13:0x006d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$queryFlags(CustomizationProvider customizationProvider, Continuation continuation) {
        CustomizationProvider$queryFlags$1 customizationProvider$queryFlags$1;
        int i;
        MatrixCursor matrixCursor;
        customizationProvider.getClass();
        if (continuation instanceof CustomizationProvider$queryFlags$1) {
            customizationProvider$queryFlags$1 = (CustomizationProvider$queryFlags$1) continuation;
            int i2 = customizationProvider$queryFlags$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                customizationProvider$queryFlags$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = customizationProvider$queryFlags$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = customizationProvider$queryFlags$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"name", "value"});
                    KeyguardQuickAffordanceInteractor interactor = customizationProvider.getInteractor();
                    customizationProvider$queryFlags$1.L$0 = matrixCursor2;
                    customizationProvider$queryFlags$1.L$1 = matrixCursor2;
                    customizationProvider$queryFlags$1.label = 1;
                    Object pickerFlags = interactor.getPickerFlags(customizationProvider$queryFlags$1);
                    if (pickerFlags != obj2) {
                        obj2 = matrixCursor2;
                        obj = pickerFlags;
                        matrixCursor = obj2;
                    }
                    return obj2;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                MatrixCursor matrixCursor3 = (MatrixCursor) customizationProvider$queryFlags$1.L$1;
                MatrixCursor matrixCursor4 = (MatrixCursor) customizationProvider$queryFlags$1.L$0;
                ResultKt.throwOnFailure(obj);
                obj2 = matrixCursor4;
                matrixCursor = matrixCursor3;
                for (KeyguardPickerFlag keyguardPickerFlag : (Iterable) obj) {
                    matrixCursor.addRow(new Object[]{keyguardPickerFlag.name, new Integer(keyguardPickerFlag.value ? 1 : 0)});
                }
                return obj2;
            }
        }
        customizationProvider$queryFlags$1 = new CustomizationProvider$queryFlags$1(customizationProvider, continuation);
        Object obj3 = customizationProvider$queryFlags$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = customizationProvider$queryFlags$1.label;
        if (i != 0) {
        }
        while (r6.hasNext()) {
        }
        return obj22;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$querySelections(CustomizationProvider customizationProvider, Continuation continuation) {
        CustomizationProvider$querySelections$1 customizationProvider$querySelections$1;
        int i;
        MatrixCursor matrixCursor;
        customizationProvider.getClass();
        if (continuation instanceof CustomizationProvider$querySelections$1) {
            customizationProvider$querySelections$1 = (CustomizationProvider$querySelections$1) continuation;
            int i2 = customizationProvider$querySelections$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                customizationProvider$querySelections$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = customizationProvider$querySelections$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = customizationProvider$querySelections$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"slot_id", "affordance_id", "affordance_name"});
                    KeyguardQuickAffordanceInteractor interactor = customizationProvider.getInteractor();
                    customizationProvider$querySelections$1.L$0 = matrixCursor2;
                    customizationProvider$querySelections$1.L$1 = matrixCursor2;
                    customizationProvider$querySelections$1.label = 1;
                    Object selections = interactor.getSelections(customizationProvider$querySelections$1);
                    if (selections != obj2) {
                        obj2 = matrixCursor2;
                        obj = selections;
                        matrixCursor = obj2;
                    }
                    return obj2;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                MatrixCursor matrixCursor3 = (MatrixCursor) customizationProvider$querySelections$1.L$1;
                MatrixCursor matrixCursor4 = (MatrixCursor) customizationProvider$querySelections$1.L$0;
                ResultKt.throwOnFailure(obj);
                obj2 = matrixCursor4;
                matrixCursor = matrixCursor3;
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    String str = (String) entry.getKey();
                    for (KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation : (List) entry.getValue()) {
                        matrixCursor.addRow(new String[]{str, keyguardQuickAffordancePickerRepresentation.f301id, keyguardQuickAffordancePickerRepresentation.name});
                    }
                }
                return obj2;
            }
        }
        customizationProvider$querySelections$1 = new CustomizationProvider$querySelections$1(customizationProvider, continuation);
        Object obj3 = customizationProvider$querySelections$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = customizationProvider$querySelections$1.label;
        if (i != 0) {
        }
        while (r7.hasNext()) {
        }
        return obj22;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006b A[LOOP:0: B:11:0x0065->B:13:0x006b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$querySlots(CustomizationProvider customizationProvider, Continuation continuation) {
        CustomizationProvider$querySlots$1 customizationProvider$querySlots$1;
        int i;
        MatrixCursor matrixCursor;
        customizationProvider.getClass();
        if (continuation instanceof CustomizationProvider$querySlots$1) {
            customizationProvider$querySlots$1 = (CustomizationProvider$querySlots$1) continuation;
            int i2 = customizationProvider$querySlots$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                customizationProvider$querySlots$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = customizationProvider$querySlots$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = customizationProvider$querySlots$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{"id", "capacity"});
                    KeyguardQuickAffordanceInteractor interactor = customizationProvider.getInteractor();
                    customizationProvider$querySlots$1.L$0 = matrixCursor2;
                    customizationProvider$querySlots$1.L$1 = matrixCursor2;
                    customizationProvider$querySlots$1.label = 1;
                    Object slotPickerRepresentations = interactor.getSlotPickerRepresentations(customizationProvider$querySlots$1);
                    if (slotPickerRepresentations != obj2) {
                        obj2 = matrixCursor2;
                        obj = slotPickerRepresentations;
                        matrixCursor = obj2;
                    }
                    return obj2;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                MatrixCursor matrixCursor3 = (MatrixCursor) customizationProvider$querySlots$1.L$1;
                MatrixCursor matrixCursor4 = (MatrixCursor) customizationProvider$querySlots$1.L$0;
                ResultKt.throwOnFailure(obj);
                obj2 = matrixCursor4;
                matrixCursor = matrixCursor3;
                for (KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation : (Iterable) obj) {
                    matrixCursor.addRow(new Object[]{keyguardSlotPickerRepresentation.f302id, new Integer(keyguardSlotPickerRepresentation.maxSelectedAffordances)});
                }
                return obj2;
            }
        }
        customizationProvider$querySlots$1 = new CustomizationProvider$querySlots$1(customizationProvider, continuation);
        Object obj3 = customizationProvider$querySlots$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = customizationProvider$querySlots$1.label;
        if (i != 0) {
        }
        while (r6.hasNext()) {
        }
        return obj22;
    }

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
        if (contextAvailableCallback == null) {
            contextAvailableCallback = null;
        }
        if (context == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        contextAvailableCallback.onContextAvailable(context);
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        if (requireContext().checkPermission("android.permission.BIND_WALLPAPER", Binder.getCallingPid(), Binder.getCallingUid()) != 0) {
            return null;
        }
        KeyguardRemotePreviewManager keyguardRemotePreviewManager = this.previewManager;
        return (keyguardRemotePreviewManager != null ? keyguardRemotePreviewManager : null).preview(bundle);
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        if (this.uriMatcher.match(uri) != 3) {
            throw new UnsupportedOperationException();
        }
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (coroutineDispatcher == null) {
            coroutineDispatcher = null;
        }
        return ((Number) BuildersKt.runBlocking(coroutineDispatcher, new CustomizationProvider$delete$1(this, uri, strArr, null))).intValue();
    }

    public final KeyguardQuickAffordanceInteractor getInteractor() {
        KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = this.interactor;
        if (keyguardQuickAffordanceInteractor != null) {
            return keyguardQuickAffordanceInteractor;
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        String qualifiedTablePath;
        int match = this.uriMatcher.match(uri);
        String str = (match == 1 || match == 2 || match == 3 || match == 4) ? "vnd.android.cursor.dir/vnd." : null;
        int match2 = this.uriMatcher.match(uri);
        if (match2 == 1) {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            qualifiedTablePath = CustomizationProviderContract.LockScreenQuickAffordances.qualifiedTablePath("slots");
        } else if (match2 == 2) {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            qualifiedTablePath = CustomizationProviderContract.LockScreenQuickAffordances.qualifiedTablePath("affordances");
        } else if (match2 != 3) {
            qualifiedTablePath = match2 != 4 ? null : "flags";
        } else {
            CustomizationProviderContract.LockScreenQuickAffordances.INSTANCE.getClass();
            qualifiedTablePath = CustomizationProviderContract.LockScreenQuickAffordances.qualifiedTablePath("selections");
        }
        if (str == null || qualifiedTablePath == null) {
            return null;
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, "com.android.systemui.customization.", qualifiedTablePath);
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        if (this.uriMatcher.match(uri) != 3) {
            throw new UnsupportedOperationException();
        }
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (coroutineDispatcher == null) {
            coroutineDispatcher = null;
        }
        return (Uri) BuildersKt.runBlocking(coroutineDispatcher, new CustomizationProvider$insert$1(this, contentValues, null));
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        CoroutineDispatcher coroutineDispatcher = this.mainDispatcher;
        if (coroutineDispatcher == null) {
            coroutineDispatcher = null;
        }
        return (Cursor) BuildersKt.runBlocking(coroutineDispatcher, new CustomizationProvider$query$1(this, uri, null));
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public final void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.contextAvailableCallback = contextAvailableCallback;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        android.util.Log.e("KeyguardQuickAffordanceProvider", "Update is not supported!");
        return 0;
    }
}
