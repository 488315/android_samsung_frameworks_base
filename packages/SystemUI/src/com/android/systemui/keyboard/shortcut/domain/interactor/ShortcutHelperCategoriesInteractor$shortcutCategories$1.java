package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.extensions.ShortcutKeyExtensionsKt;
import com.android.systemui.keyboard.shortcut.shared.model.Shortcut;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutSubCategory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShortcutHelperCategoriesInteractor$shortcutCategories$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ ShortcutHelperCategoriesInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperCategoriesInteractor$shortcutCategories$1(ShortcutHelperCategoriesInteractor shortcutHelperCategoriesInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = shortcutHelperCategoriesInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ShortcutHelperCategoriesInteractor$shortcutCategories$1 shortcutHelperCategoriesInteractor$shortcutCategories$1 = new ShortcutHelperCategoriesInteractor$shortcutCategories$1(this.this$0, (Continuation) obj3);
        shortcutHelperCategoriesInteractor$shortcutCategories$1.L$0 = (List) obj;
        shortcutHelperCategoriesInteractor$shortcutCategories$1.L$1 = (List) obj2;
        return shortcutHelperCategoriesInteractor$shortcutCategories$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        ShortcutHelperCategoriesInteractor shortcutHelperCategoriesInteractor = this.this$0;
        List plus = CollectionsKt___CollectionsKt.plus((Iterable) list2, (Collection) list);
        shortcutHelperCategoriesInteractor.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList arrayList = (ArrayList) plus;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            ShortcutCategoryType shortcutCategoryType = ((ShortcutCategory) obj2).type;
            Object obj3 = linkedHashMap.get(shortcutCategoryType);
            if (obj3 == null) {
                obj3 = new ArrayList();
                linkedHashMap.put(shortcutCategoryType, obj3);
            }
            ((List) obj3).add(obj2);
        }
        Set entrySet = linkedHashMap.entrySet();
        int i3 = 10;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ShortcutCategoryType shortcutCategoryType2 = (ShortcutCategoryType) entry.getKey();
            List list3 = (List) entry.getValue();
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                CollectionsKt__MutableCollectionsKt.addAll(((ShortcutCategory) it2.next()).subCategories, arrayList3);
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            int size2 = arrayList3.size();
            int i4 = i;
            while (i4 < size2) {
                Object obj4 = arrayList3.get(i4);
                i4++;
                String str = ((ShortcutSubCategory) obj4).label;
                Object obj5 = linkedHashMap2.get(str);
                if (obj5 == null) {
                    obj5 = new ArrayList();
                    linkedHashMap2.put(str, obj5);
                }
                ((List) obj5).add(obj4);
            }
            Set entrySet2 = linkedHashMap2.entrySet();
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet2, i3));
            Iterator it3 = entrySet2.iterator();
            while (it3.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it3.next();
                String str2 = (String) entry2.getKey();
                List list4 = (List) entry2.getValue();
                ArrayList arrayList5 = new ArrayList();
                Iterator it4 = list4.iterator();
                while (it4.hasNext()) {
                    CollectionsKt__MutableCollectionsKt.addAll(((ShortcutSubCategory) it4.next()).shortcuts, arrayList5);
                }
                LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                int size3 = arrayList5.size();
                int i5 = i;
                while (i5 < size3) {
                    Object obj6 = arrayList5.get(i5);
                    i5++;
                    String str3 = ((Shortcut) obj6).label;
                    Object obj7 = linkedHashMap3.get(str3);
                    if (obj7 == null) {
                        obj7 = new ArrayList();
                        linkedHashMap3.put(str3, obj7);
                    }
                    ((List) obj7).add(obj6);
                }
                Set<Map.Entry> entrySet3 = linkedHashMap3.entrySet();
                ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet3, i3));
                for (Map.Entry entry3 : entrySet3) {
                    String str4 = (String) entry3.getKey();
                    List list5 = (List) entry3.getValue();
                    Shortcut shortcut = (Shortcut) list5.get(i);
                    List list6 = list5;
                    ArrayList arrayList7 = new ArrayList();
                    Iterator it5 = list6.iterator();
                    while (it5.hasNext()) {
                        CollectionsKt__MutableCollectionsKt.addAll(((Shortcut) it5.next()).commands, arrayList7);
                    }
                    List sortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList7, new Comparator() { // from class: com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperCategoriesInteractor$groupShortcutsInSubcategory$lambda$10$$inlined$sortedBy$1
                        @Override // java.util.Comparator
                        public final int compare(Object obj8, Object obj9) {
                            return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((ShortcutCommand) obj8).keys.size()), Integer.valueOf(((ShortcutCommand) obj9).keys.size()));
                        }
                    });
                    ArrayList arrayList8 = new ArrayList();
                    Iterator it6 = list6.iterator();
                    while (it6.hasNext()) {
                        CollectionsKt__MutableCollectionsKt.addAll(((Shortcut) it6.next()).commands, arrayList8);
                    }
                    String string = shortcutHelperCategoriesInteractor.context.getString(R.string.shortcut_helper_add_shortcut_dialog_placeholder);
                    String string2 = shortcutHelperCategoriesInteractor.context.getString(R.string.shortcut_helper_key_combinations_and_conjunction);
                    String string3 = shortcutHelperCategoriesInteractor.context.getString(R.string.shortcut_helper_key_combinations_or_separator);
                    StringBuilder sb = new StringBuilder();
                    Iterator it7 = it;
                    sb.append(str4 + ", " + string);
                    int size4 = arrayList8.size();
                    Iterator it8 = it3;
                    int i6 = 0;
                    int i7 = 0;
                    while (i6 < size4) {
                        Object obj8 = arrayList8.get(i6);
                        i6++;
                        int i8 = i7 + 1;
                        if (i7 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                            throw null;
                        }
                        int i9 = size4;
                        ShortcutCommand shortcutCommand = (ShortcutCommand) obj8;
                        if (i7 > 0) {
                            sb.append(", " + string3);
                        }
                        Iterator it9 = shortcutCommand.keys.iterator();
                        int i10 = 0;
                        while (it9.hasNext()) {
                            Object next = it9.next();
                            int i11 = i10 + 1;
                            if (i10 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            Iterator it10 = it9;
                            ShortcutKey shortcutKey = (ShortcutKey) next;
                            ArrayList arrayList9 = arrayList8;
                            if (i10 > 0) {
                                sb.append(" " + string2);
                            }
                            String contentDescription = ShortcutKeyExtensionsKt.toContentDescription(shortcutKey, shortcutHelperCategoriesInteractor.context);
                            if (contentDescription != null) {
                                sb.append(" ".concat(contentDescription));
                            }
                            arrayList8 = arrayList9;
                            i10 = i11;
                            it9 = it10;
                        }
                        i7 = i8;
                        size4 = i9;
                    }
                    arrayList6.add(new Shortcut(shortcut.label, sortedWith, shortcut.icon, sb.toString(), shortcut.isCustomizable));
                    it = it7;
                    it3 = it8;
                    i = 0;
                }
                arrayList4.add(new ShortcutSubCategory(str2, arrayList6));
                it = it;
                i3 = 10;
                i = 0;
            }
            arrayList2.add(new ShortcutCategory(shortcutCategoryType2, arrayList4));
            it = it;
            i3 = 10;
            i = 0;
        }
        return arrayList2;
    }
}
