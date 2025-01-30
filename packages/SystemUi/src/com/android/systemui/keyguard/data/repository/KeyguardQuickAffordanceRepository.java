package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.settings.UserTracker;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceRepository {
    public final Context appContext;
    public final Set configs;
    public final KeyguardQuickAffordanceLocalUserSelectionManager localUserSelectionManager;
    public final KeyguardQuickAffordanceRemoteUserSelectionManager remoteUserSelectionManager;
    public final ReadonlyStateFlow selectionManager;
    public final ReadonlyStateFlow selections;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Dumpster implements Dumpable {
        public Dumpster() {
        }

        @Override // com.android.systemui.Dumpable
        public final void dump(PrintWriter printWriter, String[] strArr) {
            KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = KeyguardQuickAffordanceRepository.this;
            List slotPickerRepresentations = keyguardQuickAffordanceRepository.getSlotPickerRepresentations();
            Map currentSelections = keyguardQuickAffordanceRepository.getCurrentSelections();
            printWriter.println("Slots & selections:");
            Iterator it = ((ArrayList) slotPickerRepresentations).iterator();
            while (it.hasNext()) {
                KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation = (KeyguardSlotPickerRepresentation) it.next();
                String str = keyguardSlotPickerRepresentation.f302id;
                List list = (List) currentSelections.get(str);
                printWriter.println("    " + str + (!(list == null || list.isEmpty()) ? ": ".concat(CollectionsKt___CollectionsKt.joinToString$default(list, ", ", null, null, null, 62)) : " is empty") + " (capacity = " + keyguardSlotPickerRepresentation.maxSelectedAffordances + ")");
            }
            printWriter.println("Available affordances on device:");
            for (KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig : keyguardQuickAffordanceRepository.configs) {
                printWriter.println(MotionLayout$$ExternalSyntheticOutline0.m22m("    ", keyguardQuickAffordanceConfig.getKey(), " (\"", keyguardQuickAffordanceConfig.pickerName(), "\")"));
            }
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardQuickAffordanceRepository(Context context, CoroutineScope coroutineScope, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager, KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager, UserTracker userTracker, KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, Set<KeyguardQuickAffordanceConfig> set, DumpManager dumpManager, final UserHandle userHandle) {
        this.appContext = context;
        this.localUserSelectionManager = keyguardQuickAffordanceLocalUserSelectionManager;
        this.remoteUserSelectionManager = keyguardQuickAffordanceRemoteUserSelectionManager;
        this.userTracker = userTracker;
        this.configs = set;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardQuickAffordanceRepository$userId$1 keyguardQuickAffordanceRepository$userId$1 = new KeyguardQuickAffordanceRepository$userId$1(this, null);
        conflatedCallbackFlow.getClass();
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(keyguardQuickAffordanceRepository$userId$1));
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1$2 */
            public final class C16142 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserHandle $userHandle$inlined;
                public final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1$2", m277f = "KeyguardQuickAffordanceRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C16142.this.emit(null, this);
                    }
                }

                public C16142(FlowCollector flowCollector, UserHandle userHandle, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$userHandle$inlined = userHandle;
                    this.this$0 = keyguardQuickAffordanceRepository;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                int intValue = ((Number) obj).intValue();
                                int identifier = this.$userHandle$inlined.getIdentifier();
                                KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = this.this$0;
                                Object obj3 = identifier == intValue ? keyguardQuickAffordanceRepository.localUserSelectionManager : keyguardQuickAffordanceRepository.remoteUserSelectionManager;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C16142(flowCollector, userHandle, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, keyguardQuickAffordanceLocalUserSelectionManager);
        this.selectionManager = stateIn;
        this.selections = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new C1613x58c2c529(null, this)), coroutineScope, startedEagerly, MapsKt__MapsKt.emptyMap());
        BuildersKt.launch$default(keyguardQuickAffordanceLegacySettingSyncer.scope, null, null, new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1(KeyguardQuickAffordanceLegacySettingSyncer.BINDINGS, keyguardQuickAffordanceLegacySettingSyncer, null), 3);
        DumpManager.registerDumpable$default(dumpManager, "KeyguardQuickAffordances", new Dumpster());
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x007a -> B:10:0x007b). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getAffordancePickerRepresentations(Continuation continuation) {
        C1615x5ebf5164 c1615x5ebf5164;
        int i;
        Iterator it;
        Map map;
        Intent intent;
        Intent intent2;
        Intent intent3;
        if (continuation instanceof C1615x5ebf5164) {
            c1615x5ebf5164 = (C1615x5ebf5164) continuation;
            int i2 = c1615x5ebf5164.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                c1615x5ebf5164.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = c1615x5ebf5164.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = c1615x5ebf5164.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    Set set = this.configs;
                    int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
                    if (mapCapacity < 16) {
                        mapCapacity = 16;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                    it = set.iterator();
                    map = linkedHashMap;
                    if (it.hasNext()) {
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    Object next = c1615x5ebf5164.L$3;
                    map = (Map) c1615x5ebf5164.L$2;
                    it = (Iterator) c1615x5ebf5164.L$1;
                    Map map2 = (LinkedHashMap) c1615x5ebf5164.L$0;
                    ResultKt.throwOnFailure(obj);
                    map.put(next, (KeyguardQuickAffordanceConfig.PickerScreenState) obj);
                    map = map2;
                    if (it.hasNext()) {
                        next = it.next();
                        c1615x5ebf5164.L$0 = map;
                        c1615x5ebf5164.L$1 = it;
                        c1615x5ebf5164.L$2 = map;
                        c1615x5ebf5164.L$3 = next;
                        c1615x5ebf5164.label = 1;
                        obj = ((KeyguardQuickAffordanceConfig) next).getPickerScreenState(c1615x5ebf5164);
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        map2 = map;
                        map.put(next, (KeyguardQuickAffordanceConfig.PickerScreenState) obj);
                        map = map2;
                        if (it.hasNext()) {
                            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                            for (Map.Entry entry : map.entrySet()) {
                                if (!(((KeyguardQuickAffordanceConfig.PickerScreenState) entry.getValue()) instanceof KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice)) {
                                    linkedHashMap2.put(entry.getKey(), entry.getValue());
                                }
                            }
                            ArrayList arrayList = new ArrayList(linkedHashMap2.size());
                            for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
                                KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) entry2.getKey();
                                KeyguardQuickAffordanceConfig.PickerScreenState pickerScreenState = (KeyguardQuickAffordanceConfig.PickerScreenState) entry2.getValue();
                                boolean z = pickerScreenState instanceof KeyguardQuickAffordanceConfig.PickerScreenState.Default;
                                KeyguardQuickAffordanceConfig.PickerScreenState.Default r3 = z ? (KeyguardQuickAffordanceConfig.PickerScreenState.Default) pickerScreenState : null;
                                KeyguardQuickAffordanceConfig.PickerScreenState.Disabled disabled = pickerScreenState instanceof KeyguardQuickAffordanceConfig.PickerScreenState.Disabled ? (KeyguardQuickAffordanceConfig.PickerScreenState.Disabled) pickerScreenState : null;
                                String key = keyguardQuickAffordanceConfig.getKey();
                                String pickerName = keyguardQuickAffordanceConfig.pickerName();
                                int pickerIconResourceId = keyguardQuickAffordanceConfig.getPickerIconResourceId();
                                String str = disabled != null ? disabled.explanation : null;
                                String str2 = disabled != null ? disabled.actionText : null;
                                if (disabled == null || (intent = disabled.actionIntent) == null) {
                                    intent = null;
                                } else {
                                    intent.addFlags(335544320);
                                    Unit unit = Unit.INSTANCE;
                                }
                                if (r3 == null || (intent3 = r3.configureIntent) == null) {
                                    intent2 = null;
                                } else {
                                    intent3.addFlags(335544320);
                                    Unit unit2 = Unit.INSTANCE;
                                    intent2 = intent3;
                                }
                                arrayList.add(new KeyguardQuickAffordancePickerRepresentation(key, pickerName, pickerIconResourceId, z, str, str2, intent, intent2));
                            }
                            return CollectionsKt___CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$$inlined$sortedBy$1
                                @Override // java.util.Comparator
                                public final int compare(Object obj2, Object obj3) {
                                    return ComparisonsKt__ComparisonsKt.compareValues(((KeyguardQuickAffordancePickerRepresentation) obj2).name, ((KeyguardQuickAffordancePickerRepresentation) obj3).name);
                                }
                            });
                        }
                    }
                }
            }
        }
        c1615x5ebf5164 = new C1615x5ebf5164(this, continuation);
        Object obj2 = c1615x5ebf5164.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c1615x5ebf5164.label;
        if (i != 0) {
        }
    }

    public final Map getCurrentSelections() {
        return ((KeyguardQuickAffordanceSelectionManager) this.selectionManager.getValue()).getSelections();
    }

    public final List getSlotPickerRepresentations() {
        int length;
        Context context = this.appContext;
        String[] stringArray = context.getResources().getStringArray(R.array.config_keyguardQuickAffordanceSlots);
        if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(context) == 1 && (stringArray.length / 2) - 1 >= 0) {
            int length2 = stringArray.length - 1;
            IntProgressionIterator it = new IntRange(0, length).iterator();
            while (it.hasNext) {
                int nextInt = it.nextInt();
                String str = stringArray[nextInt];
                stringArray[nextInt] = stringArray[length2];
                stringArray[length2] = str;
                length2--;
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList = new ArrayList();
        for (String str2 : stringArray) {
            List split$default = StringsKt__StringsKt.split$default(str2, new String[]{":"}, 0, 6);
            if (!(split$default.size() == 2)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            Pair pair = new Pair((String) split$default.get(0), Integer.valueOf(Integer.parseInt((String) split$default.get(1))));
            String str3 = (String) pair.component1();
            int intValue = ((Number) pair.component2()).intValue();
            if (!(!linkedHashSet.contains(str3))) {
                throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("Duplicate slot \"", str3, "\"!").toString());
            }
            linkedHashSet.add(str3);
            arrayList.add(new KeyguardSlotPickerRepresentation(str3, intValue));
        }
        return arrayList;
    }
}
