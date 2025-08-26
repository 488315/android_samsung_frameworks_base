package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceRepository extends FlowDumperImpl {
    public final Context appContext;
    public final Map configsByAffordanceId;
    public final MutableStateFlow launchingAffordance;
    public final KeyguardQuickAffordanceLocalUserSelectionManager localUserSelectionManager;
    public final KeyguardQuickAffordanceRemoteUserSelectionManager remoteUserSelectionManager;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow selectionManager;
    public final ReadonlyStateFlow selections;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
                String str = keyguardSlotPickerRepresentation.id;
                List list = (List) currentSelections.get(str);
                List list2 = list;
                printWriter.println("    " + str + ((list2 == null || list2.isEmpty()) ? " is empty" : AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(": ", CollectionsKt___CollectionsKt.joinToString$default(list, ", ", null, null, null, 62))) + " (capacity = " + keyguardSlotPickerRepresentation.maxSelectedAffordances + ")");
            }
            printWriter.println("Available affordances on device:");
            for (KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig : ((LinkedHashMap) keyguardQuickAffordanceRepository.configsByAffordanceId).values()) {
                printWriter.println(MotionLayout$$ExternalSyntheticOutline0.m("    ", keyguardQuickAffordanceConfig.getKey(), " (\"", keyguardQuickAffordanceConfig.pickerName(), "\")"));
            }
        }
    }

    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceRepository.this.getAffordancePickerRepresentations(this);
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardQuickAffordanceRepository(Context context, CoroutineScope coroutineScope, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager, KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager, UserTracker userTracker, KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, Set<KeyguardQuickAffordanceConfig> set, DumpManager dumpManager, final UserHandle userHandle) {
        super(dumpManager, null, 2, null);
        this.appContext = context;
        this.scope = coroutineScope;
        this.localUserSelectionManager = keyguardQuickAffordanceLocalUserSelectionManager;
        this.remoteUserSelectionManager = keyguardQuickAffordanceRemoteUserSelectionManager;
        this.userTracker = userTracker;
        this.launchingAffordance = (MutableStateFlow) dumpValue(StateFlowKt.MutableStateFlow(Boolean.FALSE), "launchingAffordance");
        Set<KeyguardQuickAffordanceConfig> set2 = set;
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity < 16 ? 16 : iMapCapacity);
        for (Object obj : set2) {
            linkedHashMap.put(((KeyguardQuickAffordanceConfig) obj).getKey(), obj);
        }
        this.configsByAffordanceId = linkedHashMap;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardQuickAffordanceRepository$userId$1 keyguardQuickAffordanceRepository$userId$1 = new KeyguardQuickAffordanceRepository$userId$1(this, null);
        conflatedCallbackFlow.getClass();
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(keyguardQuickAffordanceRepository$userId$1));
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserHandle $userHandle$inlined;
                public final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, UserHandle userHandle, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$userHandle$inlined = userHandle;
                    this.this$0 = keyguardQuickAffordanceRepository;
                }

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
                        int iIntValue = ((Number) obj).intValue();
                        int identifier = this.$userHandle$inlined.getIdentifier();
                        KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = this.this$0;
                        Object obj3 = identifier == iIntValue ? keyguardQuickAffordanceRepository.localUserSelectionManager : keyguardQuickAffordanceRepository.remoteUserSelectionManager;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, userHandle, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        CoroutineScope coroutineScope2 = this.scope;
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope2, startedEagerly, this.localUserSelectionManager);
        this.selectionManager = readonlyStateFlowStateIn;
        this.selections = FlowKt.stateIn(FlowKt.transformLatest(readonlyStateFlowStateIn, new KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1(null, set)), this.scope, startedEagerly, MapsKt__MapsKt.emptyMap());
        KeyguardQuickAffordanceLegacySettingSyncer.startSyncing$default(keyguardQuickAffordanceLegacySettingSyncer);
        dumpManager.registerNormalDumpable("KeyguardQuickAffordances", new Dumpster());
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0082 -> B:24:0x0083). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getAffordancePickerRepresentations(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Iterator it;
        Map map;
        Intent intent;
        Intent intent2;
        Intent intent3;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object pickerScreenState = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(pickerScreenState);
            Collection collectionValues = ((LinkedHashMap) this.configsByAffordanceId).values();
            int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(collectionValues, 10));
            if (iMapCapacity < 16) {
                iMapCapacity = 16;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
            it = collectionValues.iterator();
            map = linkedHashMap;
            if (it.hasNext()) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Object next = anonymousClass1.L$3;
            map = (Map) anonymousClass1.L$2;
            it = (Iterator) anonymousClass1.L$1;
            Map map2 = (LinkedHashMap) anonymousClass1.L$0;
            ResultKt.throwOnFailure(pickerScreenState);
            map.put(next, (KeyguardQuickAffordanceConfig.PickerScreenState) pickerScreenState);
            map = map2;
            if (it.hasNext()) {
                next = it.next();
                anonymousClass1.L$0 = map;
                anonymousClass1.L$1 = it;
                anonymousClass1.L$2 = map;
                anonymousClass1.L$3 = next;
                anonymousClass1.label = 1;
                pickerScreenState = ((KeyguardQuickAffordanceConfig) next).getPickerScreenState(anonymousClass1);
                if (pickerScreenState == coroutineSingletons) {
                    return coroutineSingletons;
                }
                map2 = map;
                map.put(next, (KeyguardQuickAffordanceConfig.PickerScreenState) pickerScreenState);
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
                        KeyguardQuickAffordanceConfig.PickerScreenState pickerScreenState2 = (KeyguardQuickAffordanceConfig.PickerScreenState) entry2.getValue();
                        boolean z = pickerScreenState2 instanceof KeyguardQuickAffordanceConfig.PickerScreenState.Default;
                        KeyguardQuickAffordanceConfig.PickerScreenState.Default r3 = z ? (KeyguardQuickAffordanceConfig.PickerScreenState.Default) pickerScreenState2 : null;
                        KeyguardQuickAffordanceConfig.PickerScreenState.Disabled disabled = pickerScreenState2 instanceof KeyguardQuickAffordanceConfig.PickerScreenState.Disabled ? (KeyguardQuickAffordanceConfig.PickerScreenState.Disabled) pickerScreenState2 : null;
                        KeyguardQuickAffordanceConfig.PickerScreenState.Default r4 = r3;
                        String key = keyguardQuickAffordanceConfig.getKey();
                        String strPickerName = keyguardQuickAffordanceConfig.pickerName();
                        int pickerIconResourceId = keyguardQuickAffordanceConfig.getPickerIconResourceId();
                        String str = disabled != null ? disabled.explanation : null;
                        String str2 = disabled != null ? disabled.actionText : null;
                        if (disabled == null || (intent = disabled.actionIntent) == null) {
                            intent = null;
                        } else {
                            intent.addFlags(335544320);
                            Unit unit = Unit.INSTANCE;
                        }
                        if (r4 == null || (intent3 = r4.configureIntent) == null) {
                            intent2 = null;
                        } else {
                            intent3.addFlags(335544320);
                            Unit unit2 = Unit.INSTANCE;
                            intent2 = intent3;
                        }
                        arrayList.add(new KeyguardQuickAffordancePickerRepresentation(key, strPickerName, pickerIconResourceId, z, str, str2, intent, intent2));
                    }
                    return CollectionsKt___CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$$inlined$sortedBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return ComparisonsKt__ComparisonsKt.compareValues(((KeyguardQuickAffordancePickerRepresentation) obj).name, ((KeyguardQuickAffordancePickerRepresentation) obj2).name);
                        }
                    });
                }
            }
        }
    }

    public final Map getCurrentSelections() {
        return ((KeyguardQuickAffordanceSelectionManager) this.selectionManager.$$delegate_0.getValue()).getSelections();
    }

    public final List getSlotPickerRepresentations() {
        int length;
        String[] stringArray = this.appContext.getResources().getStringArray(R.array.config_keyguardQuickAffordanceSlots);
        if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.appContext) == 1 && (length = (stringArray.length / 2) - 1) >= 0) {
            int length2 = stringArray.length - 1;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    String str = stringArray[i];
                    stringArray[i] = stringArray[length2];
                    stringArray[length2] = str;
                    length2--;
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList = new ArrayList();
        for (String str2 : stringArray) {
            str2.getClass();
            List listSplit$default = StringsKt__StringsKt.split$default(str2, new String[]{":"}, 0, 6);
            if (listSplit$default.size() != 2) {
                throw new IllegalStateException("Check failed.");
            }
            Pair pair = new Pair((String) listSplit$default.get(0), Integer.valueOf(Integer.parseInt((String) listSplit$default.get(1))));
            String str3 = (String) pair.component1();
            int iIntValue = ((Number) pair.component2()).intValue();
            if (linkedHashSet.contains(str3)) {
                throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Duplicate slot \"", str3, "\"!").toString());
            }
            linkedHashSet.add(str3);
            arrayList.add(new KeyguardSlotPickerRepresentation(str3, iIntValue));
        }
        return arrayList;
    }
}
