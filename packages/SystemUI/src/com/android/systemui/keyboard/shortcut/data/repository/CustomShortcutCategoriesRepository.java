package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.AppLaunchData;
import android.hardware.input.InputGestureData;
import android.hardware.input.InputManager;
import android.hardware.input.InputSettings;
import android.hardware.input.KeyGlyphMap;
import android.util.Log;
import android.view.InputDevice;
import com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult;
import com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository;
import com.android.systemui.keyboard.shortcut.shared.model.KeyCombination;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.CustomShortcutCategoriesRepository$isSelectedKeyCombinationAvailable$1, reason: invalid class name */
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
            return CustomShortcutCategoriesRepository.this.isSelectedKeyCombinationAvailable(this);
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._selectedKeyCombination = stateFlowImplMutableStateFlow;
        this._shortcutBeingCustomized = StateFlowKt.MutableStateFlow(null);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow, shortcutHelperInputDeviceRepository.activeInputDevice, new CustomShortcutCategoriesRepository$pressedKeys$1(this, null));
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
        int iIntValue = keyCombination.keyCode.intValue();
        this.shortcutCategoriesUtils.getClass();
        Iterator it = ShortcutCategoriesUtils.SUPPORTED_MODIFIERS.iterator();
        if (!it.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            next = Integer.valueOf(((Number) next).intValue() | ((Number) it.next()).intValue());
        }
        return InputGestureData.createKeyTrigger(iIntValue, ((Number) next).intValue() & keyCombination.modifiers);
    }

    public final Object confirmAndSetShortcutCurrentlyBeingCustomized(Continuation continuation) {
        InputGestureData inputGestureDataBuildInputGestureDataForShortcutBeingCustomized = buildInputGestureDataForShortcutBeingCustomized();
        if (inputGestureDataBuildInputGestureDataForShortcutBeingCustomized == null) {
            return ShortcutCustomizationRequestResult.ERROR_OTHER;
        }
        CustomInputGesturesRepository customInputGesturesRepository = this.customInputGesturesRepository;
        customInputGesturesRepository.getClass();
        return BuildersKt.withContext(customInputGesturesRepository.bgCoroutineContext, new CustomInputGesturesRepository$addCustomInputGesture$2(customInputGesturesRepository, inputGestureDataBuildInputGestureDataForShortcutBeingCustomized, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ec A[EDGE_INSN: B:60:0x00ec->B:46:0x00ec BREAK  A[LOOP:1: B:24:0x0075->B:64:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[LOOP:1: B:24:0x0075->B:64:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object deleteShortcutCurrentlyBeingCustomized(Continuation continuation) {
        List customInputGestures;
        InputGestureData inputGestureData;
        Object obj;
        List listPlus;
        Boolean boolValueOf;
        InputDevice inputDevice;
        Integer keyGestureTypeForShortcutBeingCustomized = getKeyGestureTypeForShortcutBeingCustomized();
        CustomInputGesturesRepository customInputGesturesRepository = this.customInputGesturesRepository;
        customInputGesturesRepository.getClass();
        if (InputSettings.isCustomizableInputGesturesFeatureFlagEnabled()) {
            customInputGestures = customInputGesturesRepository.getInputManager().getCustomInputGestures(InputGestureData.Filter.KEY);
            customInputGestures.getClass();
        } else {
            customInputGestures = EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : customInputGestures) {
            int iKeyGestureType = ((InputGestureData) obj2).getAction().keyGestureType();
            if (keyGestureTypeForShortcutBeingCustomized != null && iKeyGestureType == keyGestureTypeForShortcutBeingCustomized.intValue()) {
                arrayList.add(obj2);
            }
        }
        if (keyGestureTypeForShortcutBeingCustomized != null && keyGestureTypeForShortcutBeingCustomized.intValue() == 51) {
            ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete delete = (ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete) getShortcutBeingCustomized();
            if (delete.customShortcutCommand == null) {
                Log.w("CustomShortcutCategoriesRepository", "Requested to delete custom shortcut but customShortcutCommand was null");
                inputGestureData = null;
            } else {
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        obj = null;
                        break;
                    }
                    obj = arrayList.get(i);
                    i++;
                    InputGestureData.KeyTrigger trigger = ((InputGestureData) obj).getTrigger();
                    List list = delete.customShortcutCommand.keys;
                    if (!(trigger instanceof InputGestureData.KeyTrigger) || (inputDevice = (InputDevice) this.inputDeviceRepository.activeInputDevice.$$delegate_0.getValue()) == null) {
                        listPlus = null;
                        boolValueOf = listPlus == null ? Boolean.valueOf(listPlus.containsAll(list)) : null;
                        if (!(boolValueOf == null ? boolValueOf.booleanValue() : false)) {
                            break;
                        }
                    } else {
                        KeyGlyphMap keyGlyphMap = this.inputManager.getKeyGlyphMap(inputDevice.getId());
                        InputGestureData.KeyTrigger keyTrigger = trigger;
                        int modifierState = keyTrigger.getModifierState();
                        ShortcutCategoriesUtils shortcutCategoriesUtils = this.shortcutCategoriesUtils;
                        List shortcutModifierKeys = shortcutCategoriesUtils.toShortcutModifierKeys(modifierState, keyGlyphMap);
                        if (shortcutModifierKeys != null) {
                            ShortcutKey shortcutKey = shortcutCategoriesUtils.toShortcutKey(this.inputManager.getKeyGlyphMap(inputDevice.getId()), inputDevice.getKeyCharacterMap(), keyTrigger.getKeycode(), (char) 0);
                            if (shortcutKey != null) {
                                listPlus = CollectionsKt___CollectionsKt.plus(shortcutModifierKeys, shortcutKey);
                            }
                            if (listPlus == null) {
                            }
                            if (!(boolValueOf == null ? boolValueOf.booleanValue() : false)) {
                            }
                        }
                    }
                }
                inputGestureData = (InputGestureData) obj;
            }
        } else {
            inputGestureData = (InputGestureData) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
        }
        return inputGestureData == null ? ShortcutCustomizationRequestResult.ERROR_OTHER : BuildersKt.withContext(customInputGesturesRepository.bgCoroutineContext, new CustomInputGesturesRepository$deleteCustomInputGesture$2(customInputGesturesRepository, inputGestureData, null), continuation);
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
        Set<Map.Entry> setEntrySet = inputGestureMaps.gestureToInternalKeyboardShortcutInfoLabelResIdMap.entrySet();
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10));
        if (iMapCapacity < 16) {
            iMapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
        for (Map.Entry entry : setEntrySet) {
            linkedHashMap.put(inputGestureMaps.context.getString(((Number) entry.getValue()).intValue()), Integer.valueOf(((Number) entry.getKey()).intValue()));
        }
        return (Integer) linkedHashMap.get(label);
    }

    public final ShortcutCustomizationRequestInfo getShortcutBeingCustomized() {
        return (ShortcutCustomizationRequestInfo) this._shortcutBeingCustomized.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isSelectedKeyCombinationAvailable(ContinuationImpl continuationImpl) throws Throwable {
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
            InputGestureData.Trigger triggerBuildTriggerFromSelectedKeyCombination = buildTriggerFromSelectedKeyCombination();
            if (triggerBuildTriggerFromSelectedKeyCombination == null) {
                return Boolean.FALSE;
            }
            anonymousClass1.label = 1;
            CustomInputGesturesRepository customInputGesturesRepository = this.customInputGesturesRepository;
            customInputGesturesRepository.getClass();
            objWithContext = BuildersKt.withContext(customInputGesturesRepository.bgCoroutineContext, new CustomInputGesturesRepository$getInputGestureByTrigger$2(customInputGesturesRepository, triggerBuildTriggerFromSelectedKeyCombination, null), anonymousClass1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext);
        }
        return Boolean.valueOf(objWithContext == null);
    }

    public final Object resetAllCustomShortcuts(Continuation continuation) {
        CustomInputGesturesRepository customInputGesturesRepository = this.customInputGesturesRepository;
        customInputGesturesRepository.getClass();
        return BuildersKt.withContext(customInputGesturesRepository.bgCoroutineContext, new CustomInputGesturesRepository$resetAllCustomInputGestures$2(customInputGesturesRepository, null), continuation);
    }
}
