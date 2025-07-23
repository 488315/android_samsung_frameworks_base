package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.AppLaunchData;
import android.hardware.input.InputGestureData;
import android.hardware.input.InputManager;
import android.util.Log;
import com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult;
import com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository;
import com.android.systemui.keyboard.shortcut.shared.model.KeyCombination;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedLazily;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomShortcutCategoriesRepository implements ShortcutCategoriesRepository {
    public final StateFlowImpl _selectedKeyCombination;
    public final StateFlowImpl _shortcutBeingCustomized;
    public final AppLaunchDataRepository appLaunchDataRepository;
    public final ReadonlyStateFlow categories;
    public final CustomInputGesturesRepository customInputGesturesRepository;
    public final ShortcutHelperInputDeviceRepository inputDeviceRepository;
    public final InputGestureDataAdapter inputGestureDataAdapter;
    public final InputManager inputManager;
    public final ReadonlyStateFlow pressedKeys;
    public final ShortcutCategoriesUtils shortcutCategoriesUtils;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CustomShortcutCategoriesRepository(ShortcutHelperInputDeviceRepository shortcutHelperInputDeviceRepository, CoroutineScope coroutineScope, ShortcutCategoriesUtils shortcutCategoriesUtils, InputGestureDataAdapter inputGestureDataAdapter, CustomInputGesturesRepository customInputGesturesRepository, InputManager inputManager, AppLaunchDataRepository appLaunchDataRepository) {
        this.inputDeviceRepository = shortcutHelperInputDeviceRepository;
        this.shortcutCategoriesUtils = shortcutCategoriesUtils;
        this.inputGestureDataAdapter = inputGestureDataAdapter;
        this.customInputGesturesRepository = customInputGesturesRepository;
        this.inputManager = inputManager;
        this.appLaunchDataRepository = appLaunchDataRepository;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._selectedKeyCombination = MutableStateFlow;
        this._shortcutBeingCustomized = StateFlowKt.MutableStateFlow(null);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow, shortcutHelperInputDeviceRepository.activeInputDevice, new CustomShortcutCategoriesRepository$pressedKeys$1(this, null));
        SharingStarted.Companion.getClass();
        StartedLazily startedLazily = SharingStarted.Companion.Lazily;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.pressedKeys = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedLazily, emptyList);
        this.categories = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shortcutHelperInputDeviceRepository.activeInputDevice, customInputGesturesRepository.customInputGestures, new CustomShortcutCategoriesRepository$categories$1(this, null)), coroutineScope, startedLazily, emptyList);
    }

    public final InputGestureData.Builder addAppLaunchDataFromShortcutBeingCustomized(InputGestureData.Builder builder) {
        ShortcutCommand defaultShortcutCommand;
        Object value = this._shortcutBeingCustomized.getValue();
        ShortcutCustomizationRequestInfo.SingleShortcutCustomization singleShortcutCustomization = value instanceof ShortcutCustomizationRequestInfo.SingleShortcutCustomization ? (ShortcutCustomizationRequestInfo.SingleShortcutCustomization) value : null;
        if (singleShortcutCustomization != null && Intrinsics.areEqual(singleShortcutCustomization.getCategoryType(), ShortcutCategoryType.AppCategories.INSTANCE) && (defaultShortcutCommand = singleShortcutCustomization.getDefaultShortcutCommand()) != null) {
            AppLaunchDataRepository appLaunchDataRepository = this.appLaunchDataRepository;
            appLaunchDataRepository.getClass();
            AppLaunchData appLaunchData = (AppLaunchData) ((Map) appLaunchDataRepository.shortcutCommandToAppLaunchDataMap.$$delegate_0.getValue()).get(new AppLaunchDataRepository.ShortcutCommandKey(defaultShortcutCommand));
            if (appLaunchData != null) {
                return builder.setAppLaunchData(appLaunchData);
            }
        }
        return builder;
    }

    public final InputGestureData buildInputGestureDataForShortcutBeingCustomized() {
        try {
            InputGestureData.Builder builder = new InputGestureData.Builder();
            Integer keyGestureTypeForShortcutBeingCustomized = getKeyGestureTypeForShortcutBeingCustomized();
            if (keyGestureTypeForShortcutBeingCustomized == null) {
                Log.w("CustomShortcutCategoriesRepository", "Could not find KeyGestureType for shortcut " + this._shortcutBeingCustomized.getValue());
            } else {
                builder = builder.setKeyGestureType(keyGestureTypeForShortcutBeingCustomized.intValue());
            }
            return addAppLaunchDataFromShortcutBeingCustomized(builder.setTrigger(buildTriggerFromSelectedKeyCombination())).build();
        } catch (IllegalArgumentException e) {
            Log.w("CustomShortcutCategoriesRepository", "could not add custom shortcut: " + e);
            return null;
        }
    }

    public final InputGestureData.Trigger buildTriggerFromSelectedKeyCombination() {
        KeyCombination keyCombination = (KeyCombination) this._selectedKeyCombination.getValue();
        if ((keyCombination != null ? keyCombination.keyCode : null) == null) {
            Log.w("CustomShortcutCategoriesRepository", "User requested to set shortcut but selected key combination is " + keyCombination);
            return null;
        }
        int intValue = keyCombination.keyCode.intValue();
        this.shortcutCategoriesUtils.getClass();
        Iterator it = ShortcutCategoriesUtils.SUPPORTED_MODIFIERS.iterator();
        if (!it.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            next = Integer.valueOf(((Number) next).intValue() | ((Number) it.next()).intValue());
        }
        return InputGestureData.createKeyTrigger(intValue, ((Number) next).intValue() & keyCombination.modifiers);
    }

    public final Object confirmAndSetShortcutCurrentlyBeingCustomized(Continuation continuation) {
        InputGestureData buildInputGestureDataForShortcutBeingCustomized = buildInputGestureDataForShortcutBeingCustomized();
        if (buildInputGestureDataForShortcutBeingCustomized == null) {
            return ShortcutCustomizationRequestResult.ERROR_OTHER;
        }
        CustomInputGesturesRepository customInputGesturesRepository = this.customInputGesturesRepository;
        customInputGesturesRepository.getClass();
        return BuildersKt.withContext(customInputGesturesRepository.bgCoroutineContext, new CustomInputGesturesRepository$addCustomInputGesture$2(customInputGesturesRepository, buildInputGestureDataForShortcutBeingCustomized, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ec A[EDGE_INSN: B:49:0x00ec->B:50:0x00ec BREAK  A[LOOP:1: B:33:0x0075->B:51:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[LOOP:1: B:33:0x0075->B:51:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object deleteShortcutCurrentlyBeingCustomized(kotlin.coroutines.Continuation r17) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository.deleteShortcutCurrentlyBeingCustomized(kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.keyboard.shortcut.data.repository.ShortcutCategoriesRepository
    public final ReadonlyStateFlow getCategories() {
        return this.categories;
    }

    public final Integer getKeyGestureTypeForShortcutBeingCustomized() {
        ShortcutCustomizationRequestInfo shortcutBeingCustomized = getShortcutBeingCustomized();
        ShortcutCustomizationRequestInfo.SingleShortcutCustomization singleShortcutCustomization = shortcutBeingCustomized instanceof ShortcutCustomizationRequestInfo.SingleShortcutCustomization ? (ShortcutCustomizationRequestInfo.SingleShortcutCustomization) shortcutBeingCustomized : null;
        if (singleShortcutCustomization == null) {
            Log.w("CustomShortcutCategoriesRepository", "Requested key gesture type from label but shortcut being customized is null");
            return null;
        }
        String label = singleShortcutCustomization.getLabel();
        ShortcutCategoryType categoryType = singleShortcutCustomization.getCategoryType();
        InputGestureDataAdapter inputGestureDataAdapter = this.inputGestureDataAdapter;
        inputGestureDataAdapter.getClass();
        if (Intrinsics.areEqual(categoryType, ShortcutCategoryType.AppCategories.INSTANCE)) {
            return 51;
        }
        InputGestureMaps inputGestureMaps = inputGestureDataAdapter.inputGestureMaps;
        Set<Map.Entry> entrySet = inputGestureMaps.gestureToInternalKeyboardShortcutInfoLabelResIdMap.entrySet();
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Map.Entry entry : entrySet) {
            linkedHashMap.put(inputGestureMaps.context.getString(((Number) entry.getValue()).intValue()), Integer.valueOf(((Number) entry.getKey()).intValue()));
        }
        return (Integer) linkedHashMap.get(label);
    }

    public final ShortcutCustomizationRequestInfo getShortcutBeingCustomized() {
        return (ShortcutCustomizationRequestInfo) this._shortcutBeingCustomized.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isSelectedKeyCombinationAvailable(kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository$isSelectedKeyCombinationAvailable$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository$isSelectedKeyCombinationAvailable$1 r0 = (com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository$isSelectedKeyCombinationAvailable$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository$isSelectedKeyCombinationAvailable$1 r0 = new com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository$isSelectedKeyCombinationAvailable$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L51
        L27:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            android.hardware.input.InputGestureData$Trigger r6 = r5.buildTriggerFromSelectedKeyCombination()
            if (r6 != 0) goto L3b
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            return r5
        L3b:
            r0.label = r3
            com.android.systemui.keyboard.shortcut.data.repository.CustomInputGesturesRepository r5 = r5.customInputGesturesRepository
            r5.getClass()
            com.android.systemui.keyboard.shortcut.data.repository.CustomInputGesturesRepository$getInputGestureByTrigger$2 r2 = new com.android.systemui.keyboard.shortcut.data.repository.CustomInputGesturesRepository$getInputGestureByTrigger$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            kotlin.coroutines.CoroutineContext r5 = r5.bgCoroutineContext
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r5, r2, r0)
            if (r6 != r1) goto L51
            return r1
        L51:
            if (r6 != 0) goto L54
            goto L55
        L54:
            r3 = 0
        L55:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository.isSelectedKeyCombinationAvailable(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object resetAllCustomShortcuts(Continuation continuation) {
        CustomInputGesturesRepository customInputGesturesRepository = this.customInputGesturesRepository;
        customInputGesturesRepository.getClass();
        return BuildersKt.withContext(customInputGesturesRepository.bgCoroutineContext, new CustomInputGesturesRepository$resetAllCustomInputGestures$2(customInputGesturesRepository, null), continuation);
    }
}
