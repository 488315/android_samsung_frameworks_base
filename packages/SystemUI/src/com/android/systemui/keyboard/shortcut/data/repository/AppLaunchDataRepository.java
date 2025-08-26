package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.AppLaunchData;
import android.hardware.input.InputGestureData;
import android.hardware.input.InputManager;
import android.hardware.input.KeyGlyphMap;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo;
import com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class AppLaunchDataRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InputManager inputManager;
    public final ShortcutCategoriesUtils shortcutCategoriesUtils;
    public final ReadonlyStateFlow shortcutCommandToAppLaunchDataMap;

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

    public AppLaunchDataRepository(InputManager inputManager, CoroutineScope coroutineScope, ShortcutCategoriesUtils shortcutCategoriesUtils, ShortcutHelperInputDeviceRepository shortcutHelperInputDeviceRepository) {
        this.inputManager = inputManager;
        this.shortcutCategoriesUtils = shortcutCategoriesUtils;
        final ReadonlyStateFlow readonlyStateFlow = shortcutHelperInputDeviceRepository.activeInputDevice;
        Flow flow = new Flow() { // from class: com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AppLaunchDataRepository this$0;

                /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AppLaunchDataRepository appLaunchDataRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = appLaunchDataRepository;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Map mapEmptyMap;
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
                        InputDevice inputDevice = (InputDevice) obj;
                        if (inputDevice == null) {
                            mapEmptyMap = MapsKt__MapsKt.emptyMap();
                        } else {
                            int i3 = AppLaunchDataRepository.$r8$clinit;
                            AppLaunchDataRepository appLaunchDataRepository = this.this$0;
                            appLaunchDataRepository.getClass();
                            LinkedHashMap linkedHashMap = new LinkedHashMap();
                            List<InputGestureData> appLaunchBookmarks = appLaunchDataRepository.inputManager.getAppLaunchBookmarks();
                            appLaunchBookmarks.getClass();
                            for (InputGestureData inputGestureData : appLaunchBookmarks) {
                                KeyGlyphMap keyGlyphMap = appLaunchDataRepository.inputManager.getKeyGlyphMap(inputDevice.getId());
                                KeyCharacterMap keyCharacterMap = inputDevice.getKeyCharacterMap();
                                InputGestureData.KeyTrigger trigger = inputGestureData.getTrigger();
                                ShortcutCategoriesUtils shortcutCategoriesUtils = appLaunchDataRepository.shortcutCategoriesUtils;
                                shortcutCategoriesUtils.getClass();
                                ShortcutCommand shortcutCommand = shortcutCategoriesUtils.toShortcutCommand(keyGlyphMap, keyCharacterMap, new InternalKeyboardShortcutInfo(null, trigger.getKeycode(), trigger.getModifierState(), (char) 0, null, false, 57, null));
                                if (shortcutCommand != null) {
                                    AppLaunchDataRepository.ShortcutCommandKey shortcutCommandKey = new AppLaunchDataRepository.ShortcutCommandKey(shortcutCommand);
                                    AppLaunchData appLaunchData = inputGestureData.getAction().appLaunchData();
                                    appLaunchData.getClass();
                                    linkedHashMap.put(shortcutCommandKey, appLaunchData);
                                } else {
                                    Log.w("AppLaunchDataRepository", "could not get Shortcut Command. inputGesture: " + inputGestureData);
                                }
                            }
                            mapEmptyMap = linkedHashMap;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mapEmptyMap, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.shortcutCommandToAppLaunchDataMap = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, MapsKt__MapsKt.emptyMap());
    }

    public final class ShortcutCommandKey {
        public final List keys;

        public ShortcutCommandKey(ShortcutCommand shortcutCommand) {
            this((List<? extends ShortcutKey>) CollectionsKt___CollectionsKt.sortedWith(shortcutCommand.keys, new Comparator() { // from class: com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$ShortcutCommandKey$special$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(((ShortcutKey) obj).toString(), ((ShortcutKey) obj2).toString());
                }
            }));
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ShortcutCommandKey) && Intrinsics.areEqual(this.keys, ((ShortcutCommandKey) obj).keys);
        }

        public final int hashCode() {
            return this.keys.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("ShortcutCommandKey(keys=", this.keys, ")");
        }

        public ShortcutCommandKey(List<? extends ShortcutKey> list) {
            this.keys = list;
        }
    }
}
