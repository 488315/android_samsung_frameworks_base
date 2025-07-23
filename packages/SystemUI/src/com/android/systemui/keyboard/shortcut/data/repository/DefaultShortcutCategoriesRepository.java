package com.android.systemui.keyboard.shortcut.data.repository;

import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutGroup;
import com.android.systemui.keyboard.shortcut.data.source.KeyboardShortcutGroupsSource;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import java.util.Arrays;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultShortcutCategoriesRepository implements ShortcutCategoriesRepository {
    public final ReadonlyStateFlow categories;
    public final List sources;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            public final Object mo779invoke(Object obj) {
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
            public final Object mo779invoke(Object obj) {
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
            public final Object mo779invoke(Object obj) {
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
            public final Object mo779invoke(Object obj) {
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
            public final Object mo779invoke(Object obj) {
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
            public final Object mo779invoke(Object obj) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:36:0x01f7, code lost:
                
                    if (r4.emit(r0, r2) == r3) goto L60;
                 */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:21:0x01b6  */
                /* JADX WARN: Removed duplicated region for block: B:42:0x00ec  */
                /* JADX WARN: Removed duplicated region for block: B:58:0x00a5  */
                /* JADX WARN: Removed duplicated region for block: B:61:0x016e  */
                /* JADX WARN: Removed duplicated region for block: B:64:0x0076  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x00cb -> B:35:0x00d2). Please report as a decompilation issue!!! */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r28, kotlin.coroutines.Continuation r29) {
                    /*
                        Method dump skipped, instructions count: 509
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.repository.DefaultShortcutCategoriesRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, shortcutCategoriesUtils), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
