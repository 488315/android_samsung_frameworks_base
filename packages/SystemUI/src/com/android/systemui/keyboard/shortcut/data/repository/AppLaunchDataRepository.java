package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputManager;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import java.util.Comparator;
import java.util.List;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AppLaunchDataRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InputManager inputManager;
    public final ShortcutCategoriesUtils shortcutCategoriesUtils;
    public final ReadonlyStateFlow shortcutCommandToAppLaunchDataMap;

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

    public AppLaunchDataRepository(InputManager inputManager, CoroutineScope coroutineScope, ShortcutCategoriesUtils shortcutCategoriesUtils, ShortcutHelperInputDeviceRepository shortcutHelperInputDeviceRepository) {
        this.inputManager = inputManager;
        this.shortcutCategoriesUtils = shortcutCategoriesUtils;
        final ReadonlyStateFlow readonlyStateFlow = shortcutHelperInputDeviceRepository.activeInputDevice;
        Flow flow = new Flow() { // from class: com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r23, kotlin.coroutines.Continuation r24) {
                    /*
                        r22 = this;
                        r0 = r22
                        r1 = r24
                        boolean r2 = r1 instanceof com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r2 == 0) goto L17
                        r2 = r1
                        com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1$2$1 r2 = (com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r2
                        int r3 = r2.label
                        r4 = -2147483648(0xffffffff80000000, float:-0.0)
                        r5 = r3 & r4
                        if (r5 == 0) goto L17
                        int r3 = r3 - r4
                        r2.label = r3
                        goto L1c
                    L17:
                        com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1$2$1 r2 = new com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1$2$1
                        r2.<init>(r1)
                    L1c:
                        java.lang.Object r1 = r2.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r4 = r2.label
                        r5 = 1
                        if (r4 == 0) goto L34
                        if (r4 != r5) goto L2c
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto Ld5
                    L2c:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L34:
                        kotlin.ResultKt.throwOnFailure(r1)
                        r1 = r23
                        android.view.InputDevice r1 = (android.view.InputDevice) r1
                        if (r1 != 0) goto L43
                        java.util.Map r1 = kotlin.collections.MapsKt__MapsKt.emptyMap()
                        goto Lca
                    L43:
                        int r4 = com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository.$r8$clinit
                        com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository r4 = r0.this$0
                        r4.getClass()
                        java.util.LinkedHashMap r6 = new java.util.LinkedHashMap
                        r6.<init>()
                        android.hardware.input.InputManager r7 = r4.inputManager
                        java.util.List r7 = r7.getAppLaunchBookmarks()
                        r7.getClass()
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        java.util.Iterator r7 = r7.iterator()
                    L5e:
                        boolean r8 = r7.hasNext()
                        if (r8 == 0) goto Lc9
                        java.lang.Object r8 = r7.next()
                        android.hardware.input.InputGestureData r8 = (android.hardware.input.InputGestureData) r8
                        android.hardware.input.InputManager r9 = r4.inputManager
                        int r10 = r1.getId()
                        android.hardware.input.KeyGlyphMap r9 = r9.getKeyGlyphMap(r10)
                        android.view.KeyCharacterMap r10 = r1.getKeyCharacterMap()
                        android.hardware.input.InputGestureData$Trigger r11 = r8.getTrigger()
                        android.hardware.input.InputGestureData$KeyTrigger r11 = (android.hardware.input.InputGestureData.KeyTrigger) r11
                        com.android.systemui.keyboard.shortcut.data.repository.ShortcutCategoriesUtils r12 = r4.shortcutCategoriesUtils
                        r12.getClass()
                        com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo r13 = new com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo
                        int r15 = r11.getKeycode()
                        int r16 = r11.getModifierState()
                        r18 = 0
                        r19 = 0
                        r14 = 0
                        r17 = 0
                        r20 = 57
                        r21 = 0
                        r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21)
                        com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand r9 = r12.toShortcutCommand(r9, r10, r13)
                        if (r9 == 0) goto Lb5
                        com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$ShortcutCommandKey r10 = new com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$ShortcutCommandKey
                        r10.<init>(r9)
                        android.hardware.input.InputGestureData$Action r8 = r8.getAction()
                        android.hardware.input.AppLaunchData r8 = r8.appLaunchData()
                        r8.getClass()
                        r6.put(r10, r8)
                        goto L5e
                    Lb5:
                        java.lang.StringBuilder r9 = new java.lang.StringBuilder
                        java.lang.String r10 = "could not get Shortcut Command. inputGesture: "
                        r9.<init>(r10)
                        r9.append(r8)
                        java.lang.String r8 = r9.toString()
                        java.lang.String r9 = "AppLaunchDataRepository"
                        android.util.Log.w(r9, r8)
                        goto L5e
                    Lc9:
                        r1 = r6
                    Lca:
                        r2.label = r5
                        kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                        java.lang.Object r0 = r0.emit(r1, r2)
                        if (r0 != r3) goto Ld5
                        return r3
                    Ld5:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.repository.AppLaunchDataRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.shortcutCommandToAppLaunchDataMap = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, MapsKt__MapsKt.emptyMap());
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
