package com.android.systemui.keyboard.shortcut.data.repository;

import android.view.InputDevice;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutGroup;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo;
import com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository;
import com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class DefaultShortcutCategoriesRepository implements ShortcutCategoriesRepository {
    public final ReadonlyStateFlow categories;
    public final List sources;

    public final class InternalGroupsSource {
        public final KeyboardShortcutGroupsSource source;
        public final Function1 typeProvider;

        public InternalGroupsSource(KeyboardShortcutGroupsSource keyboardShortcutGroupsSource, Function1 function1) {
            this.source = keyboardShortcutGroupsSource;
            this.typeProvider = function1;
        }
    }

    public DefaultShortcutCategoriesRepository(CoroutineScope coroutineScope, KeyboardShortcutGroupsSource keyboardShortcutGroupsSource, KeyboardShortcutGroupsSource keyboardShortcutGroupsSource2, KeyboardShortcutGroupsSource keyboardShortcutGroupsSource3, KeyboardShortcutGroupsSource keyboardShortcutGroupsSource4, KeyboardShortcutGroupsSource keyboardShortcutGroupsSource5, KeyboardShortcutGroupsSource keyboardShortcutGroupsSource6, ShortcutHelperInputDeviceRepository shortcutHelperInputDeviceRepository, final ShortcutCategoriesUtils shortcutCategoriesUtils) {
        final int i = 0;
        InternalGroupsSource internalGroupsSource = new InternalGroupsSource(keyboardShortcutGroupsSource, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i) {
                    case 0:
                        return ShortcutCategoryType.System.INSTANCE;
                    case 1:
                        return ShortcutCategoryType.MultiTasking.INSTANCE;
                    case 2:
                        return ShortcutCategoryType.AppCategories.INSTANCE;
                    case 3:
                        return ShortcutCategoryType.InputMethodEditor.INSTANCE;
                    default:
                        return ShortcutCategoryType.Accessibility.INSTANCE;
                }
            }
        });
        final int i2 = 1;
        InternalGroupsSource internalGroupsSource2 = new InternalGroupsSource(keyboardShortcutGroupsSource2, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i2) {
                    case 0:
                        return ShortcutCategoryType.System.INSTANCE;
                    case 1:
                        return ShortcutCategoryType.MultiTasking.INSTANCE;
                    case 2:
                        return ShortcutCategoryType.AppCategories.INSTANCE;
                    case 3:
                        return ShortcutCategoryType.InputMethodEditor.INSTANCE;
                    default:
                        return ShortcutCategoryType.Accessibility.INSTANCE;
                }
            }
        });
        final int i3 = 2;
        InternalGroupsSource internalGroupsSource3 = new InternalGroupsSource(keyboardShortcutGroupsSource3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i3) {
                    case 0:
                        return ShortcutCategoryType.System.INSTANCE;
                    case 1:
                        return ShortcutCategoryType.MultiTasking.INSTANCE;
                    case 2:
                        return ShortcutCategoryType.AppCategories.INSTANCE;
                    case 3:
                        return ShortcutCategoryType.InputMethodEditor.INSTANCE;
                    default:
                        return ShortcutCategoryType.Accessibility.INSTANCE;
                }
            }
        });
        final int i4 = 3;
        InternalGroupsSource internalGroupsSource4 = new InternalGroupsSource(keyboardShortcutGroupsSource4, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i4) {
                    case 0:
                        return ShortcutCategoryType.System.INSTANCE;
                    case 1:
                        return ShortcutCategoryType.MultiTasking.INSTANCE;
                    case 2:
                        return ShortcutCategoryType.AppCategories.INSTANCE;
                    case 3:
                        return ShortcutCategoryType.InputMethodEditor.INSTANCE;
                    default:
                        return ShortcutCategoryType.Accessibility.INSTANCE;
                }
            }
        });
        final int i5 = 4;
        this.sources = Arrays.asList(internalGroupsSource, internalGroupsSource2, internalGroupsSource3, internalGroupsSource4, new InternalGroupsSource(keyboardShortcutGroupsSource6, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                switch (i5) {
                    case 0:
                        return ShortcutCategoryType.System.INSTANCE;
                    case 1:
                        return ShortcutCategoryType.MultiTasking.INSTANCE;
                    case 2:
                        return ShortcutCategoryType.AppCategories.INSTANCE;
                    case 3:
                        return ShortcutCategoryType.InputMethodEditor.INSTANCE;
                    default:
                        return ShortcutCategoryType.Accessibility.INSTANCE;
                }
            }
        }), new InternalGroupsSource(keyboardShortcutGroupsSource5, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                String str;
                InternalKeyboardShortcutGroup internalKeyboardShortcutGroup = (InternalKeyboardShortcutGroup) CollectionsKt___CollectionsKt.firstOrNull((List) obj);
                if (internalKeyboardShortcutGroup == null || (str = internalKeyboardShortcutGroup.packageName) == null) {
                    return null;
                }
                return new ShortcutCategoryType.CurrentApp(str);
            }
        }));
        final ReadonlyStateFlow readonlyStateFlow = shortcutHelperInputDeviceRepository.activeInputDevice;
        Flow flow = new Flow() { // from class: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ ShortcutCategoriesUtils $shortcutCategoriesUtils$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DefaultShortcutCategoriesRepository this$0;

                /* renamed from: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    Object L$3;
                    Object L$4;
                    Object L$5;
                    Object L$6;
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

                public AnonymousClass2(FlowCollector flowCollector, DefaultShortcutCategoriesRepository defaultShortcutCategoriesRepository, ShortcutCategoriesUtils shortcutCategoriesUtils) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = defaultShortcutCategoriesRepository;
                    this.$shortcutCategoriesUtils$inlined = shortcutCategoriesUtils;
                }

                /* JADX WARN: Code restructure failed: missing block: B:59:0x01f7, code lost:
                
                    if (r4.emit(r0, r2) == r3) goto L60;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00a5  */
                /* JADX WARN: Removed duplicated region for block: B:32:0x00ec  */
                /* JADX WARN: Removed duplicated region for block: B:42:0x016e  */
                /* JADX WARN: Removed duplicated region for block: B:49:0x01b6  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x00cb -> B:29:0x00d2). Please report as a decompilation issue!!! */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    InputDevice inputDevice;
                    FlowCollector flowCollector;
                    FlowCollector flowCollector2;
                    Collection collection;
                    Iterator it;
                    EmptyList emptyList;
                    AnonymousClass2 anonymousClass2;
                    List list;
                    InputDevice inputDevice2;
                    FlowCollector flowCollector3;
                    AnonymousClass2 anonymousClass22 = this;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = anonymousClass22.new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    int i3 = 1;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        inputDevice = (InputDevice) obj;
                        flowCollector = anonymousClass22.$this_unsafeFlow;
                        if (inputDevice != null) {
                            List list2 = anonymousClass22.this$0.sources;
                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                            Iterator it2 = list2.iterator();
                            flowCollector2 = flowCollector;
                            collection = arrayList;
                            it = it2;
                            if (it.hasNext()) {
                            }
                            return coroutineSingletons;
                        }
                        emptyList = EmptyList.INSTANCE;
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.L$3 = null;
                        anonymousClass1.label = 3;
                    } else {
                        if (i2 == 1) {
                            DefaultShortcutCategoriesRepository defaultShortcutCategoriesRepository = (DefaultShortcutCategoriesRepository) anonymousClass1.L$6;
                            collection = (Collection) anonymousClass1.L$5;
                            Iterator it3 = (Iterator) anonymousClass1.L$4;
                            Collection collection2 = (Collection) anonymousClass1.L$3;
                            InputDevice inputDevice3 = (InputDevice) anonymousClass1.L$2;
                            FlowCollector flowCollector4 = (FlowCollector) anonymousClass1.L$1;
                            AnonymousClass2 anonymousClass23 = (AnonymousClass2) anonymousClass1.L$0;
                            ResultKt.throwOnFailure(obj2);
                            Iterator it4 = it3;
                            flowCollector2 = flowCollector4;
                            defaultShortcutCategoriesRepository.getClass();
                            List<KeyboardShortcutGroup> list3 = (List) obj2;
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                            for (KeyboardShortcutGroup keyboardShortcutGroup : list3) {
                                String string = keyboardShortcutGroup.getLabel().toString();
                                List<KeyboardShortcutInfo> items = keyboardShortcutGroup.getItems();
                                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(items, 10));
                                for (KeyboardShortcutInfo keyboardShortcutInfo : items) {
                                    keyboardShortcutInfo.getClass();
                                    CharSequence label = keyboardShortcutInfo.getLabel();
                                    label.getClass();
                                    arrayList3.add(new InternalKeyboardShortcutInfo(label.toString(), keyboardShortcutInfo.getKeycode(), keyboardShortcutInfo.getModifiers(), keyboardShortcutInfo.getBaseCharacter(), keyboardShortcutInfo.getIcon(), false, 32, null));
                                }
                                CharSequence packageName = keyboardShortcutGroup.getPackageName();
                                arrayList2.add(new InternalKeyboardShortcutGroup(string, arrayList3, packageName != null ? packageName.toString() : null));
                            }
                            collection.add(arrayList2);
                            collection = collection2;
                            inputDevice = inputDevice3;
                            it = it4;
                            anonymousClass22 = anonymousClass23;
                            i3 = 1;
                            if (it.hasNext()) {
                                DefaultShortcutCategoriesRepository.InternalGroupsSource internalGroupsSource = (DefaultShortcutCategoriesRepository.InternalGroupsSource) it.next();
                                DefaultShortcutCategoriesRepository defaultShortcutCategoriesRepository2 = anonymousClass22.this$0;
                                KeyboardShortcutGroupsSource keyboardShortcutGroupsSource = internalGroupsSource.source;
                                int id = inputDevice.getId();
                                anonymousClass1.L$0 = anonymousClass22;
                                anonymousClass1.L$1 = flowCollector2;
                                anonymousClass1.L$2 = inputDevice;
                                anonymousClass1.L$3 = collection;
                                anonymousClass1.L$4 = it;
                                anonymousClass1.L$5 = collection;
                                anonymousClass1.L$6 = defaultShortcutCategoriesRepository2;
                                anonymousClass1.label = i3;
                                Object objShortcutGroups = keyboardShortcutGroupsSource.shortcutGroups(id, anonymousClass1);
                                if (objShortcutGroups != coroutineSingletons) {
                                    inputDevice3 = inputDevice;
                                    obj2 = objShortcutGroups;
                                    anonymousClass23 = anonymousClass22;
                                    defaultShortcutCategoriesRepository = defaultShortcutCategoriesRepository2;
                                    it4 = it;
                                    collection2 = collection;
                                    defaultShortcutCategoriesRepository.getClass();
                                    List<KeyboardShortcutGroup> list32 = (List) obj2;
                                    ArrayList arrayList22 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list32, 10));
                                    while (r1.hasNext()) {
                                    }
                                    collection.add(arrayList22);
                                    collection = collection2;
                                    inputDevice = inputDevice3;
                                    it = it4;
                                    anonymousClass22 = anonymousClass23;
                                    i3 = 1;
                                    if (it.hasNext()) {
                                        List list4 = (List) collection;
                                        ShortcutCategoriesUtils shortcutCategoriesUtils = anonymousClass22.$shortcutCategoriesUtils$inlined;
                                        int id2 = inputDevice.getId();
                                        anonymousClass1.L$0 = anonymousClass22;
                                        anonymousClass1.L$1 = flowCollector2;
                                        anonymousClass1.L$2 = inputDevice;
                                        anonymousClass1.L$3 = list4;
                                        anonymousClass1.L$4 = null;
                                        anonymousClass1.L$5 = null;
                                        anonymousClass1.L$6 = null;
                                        anonymousClass1.label = 2;
                                        shortcutCategoriesUtils.getClass();
                                        Object objWithContext = BuildersKt.withContext(shortcutCategoriesUtils.backgroundCoroutineContext, new ShortcutCategoriesUtils$fetchSupportedKeyCodes$2(list4, shortcutCategoriesUtils, id2, null), anonymousClass1);
                                        if (objWithContext != coroutineSingletons) {
                                            anonymousClass2 = anonymousClass22;
                                            list = list4;
                                            inputDevice2 = inputDevice;
                                            obj2 = objWithContext;
                                            flowCollector3 = flowCollector2;
                                            Set set = (Set) obj2;
                                            List list5 = anonymousClass2.this$0.sources;
                                            ArrayList arrayList4 = new ArrayList();
                                            int i4 = 0;
                                            while (r6.hasNext()) {
                                            }
                                            flowCollector = flowCollector3;
                                            emptyList = arrayList4;
                                            anonymousClass1.L$0 = null;
                                            anonymousClass1.L$1 = null;
                                            anonymousClass1.L$2 = null;
                                            anonymousClass1.L$3 = null;
                                            anonymousClass1.label = 3;
                                        }
                                    }
                                }
                            }
                            return coroutineSingletons;
                        }
                        if (i2 != 2) {
                            if (i2 != 3) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                            return Unit.INSTANCE;
                        }
                        list = (List) anonymousClass1.L$3;
                        inputDevice2 = (InputDevice) anonymousClass1.L$2;
                        flowCollector3 = (FlowCollector) anonymousClass1.L$1;
                        anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        Set set2 = (Set) obj2;
                        List list52 = anonymousClass2.this$0.sources;
                        ArrayList arrayList42 = new ArrayList();
                        int i42 = 0;
                        for (Object obj3 : list52) {
                            int i5 = i42 + 1;
                            if (i42 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            ShortcutCategory shortcutCategoryFetchShortcutCategory = anonymousClass2.$shortcutCategoriesUtils$inlined.fetchShortcutCategory((ShortcutCategoryType) ((DefaultShortcutCategoriesRepository.InternalGroupsSource) obj3).typeProvider.mo781invoke(list.get(i42)), (List) list.get(i42), inputDevice2, set2);
                            if (shortcutCategoryFetchShortcutCategory != null) {
                                arrayList42.add(shortcutCategoryFetchShortcutCategory);
                            }
                            i42 = i5;
                        }
                        flowCollector = flowCollector3;
                        emptyList = arrayList42;
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.L$3 = null;
                        anonymousClass1.label = 3;
                    }
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this, shortcutCategoriesUtils), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.categories = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Lazily, EmptyList.INSTANCE);
    }

    @Override // com.android.systemui.keyboard.shortcut.data.repository.ShortcutCategoriesRepository
    public final ReadonlyStateFlow getCategories() {
        return this.categories;
    }
}
