package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class C2paManifestList {
    public static final Companion Companion = new Companion(null);
    public static final String EXIF_LABEL = "stds.exif";
    public static final String PARENT_RELATION = "parentOf";
    public static final String UNKNOWN_TIME = "1970-01-01T00:00:00+00:00";
    public static final String UNKNOWN_VALUE = "Unknown";
    private final String activeManifest;
    private final Map<String, C2paManifest> manifests;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public C2paManifestList(String str, Map<String, C2paManifest> map) {
        this.activeManifest = str;
        this.manifests = map;
    }

    private final boolean checkInvalid(List<ValidationStatus> list) {
        List<ValidationStatus> list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return false;
        }
        return C2paError.Companion.checkInvalid(CollectionsKt___CollectionsKt.joinToString$default(list, "::", null, null, new Function1() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList$checkInvalid$concatErrorCode$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
            public final CharSequence mo779invoke(ValidationStatus validationStatus) {
                return String.valueOf(validationStatus.getCode());
            }
        }, 30));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ C2paManifestList copy$default(C2paManifestList c2paManifestList, String str, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            str = c2paManifestList.activeManifest;
        }
        if ((i & 2) != 0) {
            map = c2paManifestList.manifests;
        }
        return c2paManifestList.copy(str, map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ List getFilteredActions$default(C2paManifestList c2paManifestList, List list, Boolean bool, Boolean bool2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = null;
        }
        if ((i & 2) != 0) {
            bool = null;
        }
        if ((i & 4) != 0) {
            bool2 = null;
        }
        return c2paManifestList.getFilteredActions(list, bool, bool2);
    }

    private final Action getLatestAction(List<Action> list) {
        return (Action) CollectionsKt___CollectionsKt.firstOrNull((List) sortedByManifest(list));
    }

    private final Action getOldestAction(List<Action> list) {
        return (Action) CollectionsKt___CollectionsKt.firstOrNull((List) sortedByManifest(list));
    }

    private final String getRootParentManifestKey() {
        List<Ingredients> list;
        Ingredients next;
        String str = this.activeManifest;
        while (true) {
            for (boolean z = true; z; z = false) {
                C2paManifest c2paManifest = this.manifests.get(str);
                if (c2paManifest == null || (list = c2paManifest.getIngredients()) == null) {
                    list = EmptyList.INSTANCE;
                }
                Iterator<Ingredients> it = list.iterator();
                while (it.hasNext()) {
                    next = it.next();
                    if (!Intrinsics.areEqual(next.getRelationship(), PARENT_RELATION) || next.getActiveManifest() == null) {
                    }
                }
            }
            return str;
            str = next.getActiveManifest();
        }
    }

    private final void setValidationStatus(String str) {
        C2paManifest c2paManifest = this.manifests.get(str);
        if (c2paManifest == null) {
            return;
        }
        c2paManifest.setInvalid(true);
        C2paManifest c2paManifest2 = this.manifests.get(str);
        List<Ingredients> ingredients = c2paManifest2 != null ? c2paManifest2.getIngredients() : null;
        if (ingredients != null) {
            Iterator<Ingredients> it = ingredients.iterator();
            while (it.hasNext()) {
                String activeManifest = it.next().getActiveManifest();
                if (activeManifest != null) {
                    setValidationStatus(activeManifest);
                }
            }
        }
    }

    private final List<Action> sortedByManifest(List<Action> list) {
        ArrayList arrayList = new ArrayList();
        sortedByManifest$preOrderSearch(arrayList, list, this, this.activeManifest);
        return arrayList;
    }

    private static final void sortedByManifest$preOrderSearch(List<Action> list, List<Action> list2, C2paManifestList c2paManifestList, String str) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (Intrinsics.areEqual(((Action) obj).getActiveManifest(), str)) {
                arrayList.add(obj);
            }
        }
        list.addAll(arrayList);
        Iterator<IngredientManifestInfo> it = c2paManifestList.getIngredientManifestInfo(str).iterator();
        while (it.hasNext()) {
            sortedByManifest$preOrderSearch(list, list2, c2paManifestList, it.next().getManifestKey());
        }
    }

    public final void calculateValidation() {
        String activeManifest;
        Iterator<String> it = getManifestKeys().iterator();
        while (it.hasNext()) {
            C2paManifest c2paManifest = this.manifests.get(it.next());
            List<Ingredients> ingredients = c2paManifest != null ? c2paManifest.getIngredients() : null;
            if (ingredients != null) {
                for (Ingredients ingredients2 : ingredients) {
                    if (checkInvalid(ingredients2.getValidationStatus()) && (activeManifest = ingredients2.getActiveManifest()) != null) {
                        setValidationStatus(activeManifest);
                    }
                }
            }
        }
    }

    public final String component1() {
        return this.activeManifest;
    }

    public final Map<String, C2paManifest> component2() {
        return this.manifests;
    }

    public final C2paManifestList copy(String str, Map<String, C2paManifest> map) {
        return new C2paManifestList(str, map);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2paManifestList)) {
            return false;
        }
        C2paManifestList c2paManifestList = (C2paManifestList) obj;
        return Intrinsics.areEqual(this.activeManifest, c2paManifestList.activeManifest) && Intrinsics.areEqual(this.manifests, c2paManifestList.manifests);
    }

    public final List<Action> getActionsFromManifestKey(String str) {
        List<Action> allActions = getAllActions();
        ArrayList arrayList = new ArrayList();
        for (Object obj : allActions) {
            if (Intrinsics.areEqual(((Action) obj).getActiveManifest(), str)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final String getActiveManifest() {
        return this.activeManifest;
    }

    public final List<Action> getAllActions() {
        List<Action> list;
        for (Map.Entry<String, C2paManifest> entry : this.manifests.entrySet()) {
            String key = entry.getKey();
            List<C2paAssertion> assertions = entry.getValue().getAssertions();
            if (assertions == null) {
                assertions = EmptyList.INSTANCE;
            }
            Iterator<C2paAssertion> it = assertions.iterator();
            while (it.hasNext()) {
                Data data = it.next().getData();
                if (data == null || (list = data.getActions()) == null) {
                    list = EmptyList.INSTANCE;
                }
                Iterator<Action> it2 = list.iterator();
                while (it2.hasNext()) {
                    it2.next().setActiveManifest(key);
                }
            }
        }
        Collection<C2paManifest> values = this.manifests.values();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it3 = values.iterator();
        while (it3.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll(((C2paManifest) it3.next()).getActions(), arrayList);
        }
        return sortedByManifest(CollectionsKt___CollectionsKt.toList(arrayList));
    }

    public final List<Action> getAllEditActions() {
        ArrayList arrayList = new ArrayList(getAllValidActions());
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (!Intrinsics.areEqual((Action) obj, getSourceAction())) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public final List<Action> getAllValidActions() {
        return getFilteredActions(null, null, Boolean.TRUE);
    }

    public final Map<Exif, String> getExif(String str) {
        JsonArray assertionsJsonArray;
        String str2;
        Object obj;
        JsonElement jsonElement;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        C2paManifest manifest = getManifest(str);
        if (manifest != null && (assertionsJsonArray = manifest.getAssertionsJsonArray()) != null) {
            Iterator<JsonElement> it = assertionsJsonArray.iterator();
            while (it.hasNext()) {
                JsonObject asJsonObject = it.next().getAsJsonObject();
                if (Intrinsics.areEqual(asJsonObject.get("label").getAsString(), EXIF_LABEL)) {
                    JsonObject asJsonObject2 = asJsonObject.get("data").getAsJsonObject();
                    for (Exif exif : Exif.values()) {
                        Set<String> keySet = asJsonObject2.keySet();
                        if (!(keySet instanceof Collection) || !keySet.isEmpty()) {
                            Iterator<T> it2 = keySet.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                if (StringsKt__StringsJVMKt.equals((String) it2.next(), exif.getStr(), true)) {
                                    Iterator<T> it3 = asJsonObject2.keySet().iterator();
                                    while (true) {
                                        str2 = null;
                                        if (!it3.hasNext()) {
                                            obj = null;
                                            break;
                                        }
                                        obj = it3.next();
                                        if (StringsKt__StringsJVMKt.equals((String) obj, exif.getStr(), true)) {
                                            break;
                                        }
                                    }
                                    String str3 = (String) obj;
                                    if (str3 != null && (jsonElement = asJsonObject2.get(str3)) != null) {
                                        str2 = jsonElement.getAsString();
                                    }
                                    if (str2 != null) {
                                        linkedHashMap.put(exif, str2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return linkedHashMap;
    }

    public final Map<Exif, String> getExifFromSource() {
        return getExif(getRootParentManifestKey());
    }

    public final List<Action> getFilteredActions(List<String> list, Boolean bool, Boolean bool2) {
        ArrayList arrayList;
        ArrayList arrayList2;
        List<Action> allActions = getAllActions();
        if (list != null) {
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : allActions) {
                if (CollectionsKt___CollectionsKt.contains(list, ((Action) obj).getAction())) {
                    arrayList3.add(obj);
                }
            }
            allActions = arrayList3;
        }
        if (bool != null) {
            if (bool.booleanValue()) {
                arrayList2 = new ArrayList();
                for (Object obj2 : allActions) {
                    if (((Action) obj2).isAiGenerated()) {
                        arrayList2.add(obj2);
                    }
                }
            } else {
                arrayList2 = new ArrayList();
                for (Object obj3 : allActions) {
                    if (!((Action) obj3).isAiGenerated()) {
                        arrayList2.add(obj3);
                    }
                }
            }
            allActions = arrayList2;
        }
        if (bool2 != null) {
            if (bool2.booleanValue()) {
                arrayList = new ArrayList();
                for (Object obj4 : allActions) {
                    if (Intrinsics.areEqual(((Action) obj4).isInvalid(), Boolean.FALSE)) {
                        arrayList.add(obj4);
                    }
                }
            } else {
                arrayList = new ArrayList();
                for (Object obj5 : allActions) {
                    if (Intrinsics.areEqual(((Action) obj5).isInvalid(), Boolean.TRUE)) {
                        arrayList.add(obj5);
                    }
                }
            }
            allActions = arrayList;
        }
        return sortedByManifest(allActions);
    }

    public final List<IngredientManifestInfo> getIngredientManifestInfo(String str) {
        List<Ingredients> list;
        ArrayList arrayList = new ArrayList();
        C2paManifest c2paManifest = this.manifests.get(str);
        if (c2paManifest == null || (list = c2paManifest.getIngredients()) == null) {
            list = EmptyList.INSTANCE;
        }
        for (Ingredients ingredients : list) {
            String activeManifest = ingredients.getActiveManifest();
            boolean areEqual = Intrinsics.areEqual(ingredients.getRelationship(), PARENT_RELATION);
            if (activeManifest != null) {
                if (areEqual) {
                    arrayList.add(0, new IngredientManifestInfo(activeManifest, areEqual));
                } else {
                    arrayList.add(new IngredientManifestInfo(activeManifest, areEqual));
                }
            }
        }
        return arrayList;
    }

    public final Action getLatestAiAction() {
        Boolean bool = Boolean.TRUE;
        return getLatestAction(getFilteredActions(null, bool, bool));
    }

    public final Action getLatestEditAction() {
        return (Action) CollectionsKt___CollectionsKt.lastOrNull(getAllEditActions());
    }

    public final C2paManifest getManifest(String str) {
        return this.manifests.get(str);
    }

    public final List<String> getManifestKeys() {
        return CollectionsKt___CollectionsKt.toList(this.manifests.keySet());
    }

    public final Map<String, C2paManifest> getManifests() {
        return this.manifests;
    }

    public final C2paManifestList getSingleTreeC2paManifestList(String str) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String str2 = str;
        while (true) {
            C2paManifest manifest = getManifest(str2);
            if (manifest == null) {
                break;
            }
            linkedHashMap.put(str2, manifest);
            List<IngredientManifestInfo> ingredientManifestInfo = getIngredientManifestInfo(str2);
            if (ingredientManifestInfo.size() != 1) {
                break;
            }
            str2 = ingredientManifestInfo.get(0).getManifestKey();
        }
        return new C2paManifestList(str, linkedHashMap);
    }

    public final C2paManifestList getSingleTreeC2paManifestListLatest() {
        return getSingleTreeC2paManifestList(this.activeManifest);
    }

    public final Action getSourceAction() {
        String rootParentManifestKey = getRootParentManifestKey();
        for (Action action : getFilteredActions(Collections.singletonList(C2paAction.C2PA_CREATED.getStr()), null, Boolean.TRUE)) {
            if (Intrinsics.areEqual(action.getActiveManifest(), rootParentManifestKey)) {
                return action;
            }
        }
        return null;
    }

    public int hashCode() {
        return this.manifests.hashCode() + (this.activeManifest.hashCode() * 31);
    }

    public final boolean isAiGenerated() {
        List<Action> allActions = getAllActions();
        if ((allActions instanceof Collection) && allActions.isEmpty()) {
            return false;
        }
        Iterator<T> it = allActions.iterator();
        while (it.hasNext()) {
            if (((Action) it.next()).isAiGenerated()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isEdited() {
        List<Action> allActions = getAllActions();
        if ((allActions instanceof Collection) && allActions.isEmpty()) {
            return false;
        }
        Iterator<T> it = allActions.iterator();
        while (it.hasNext()) {
            if (((Action) it.next()).isEdited()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isEnhanced() {
        List<Action> allActions = getAllActions();
        if ((allActions instanceof Collection) && allActions.isEmpty()) {
            return false;
        }
        Iterator<T> it = allActions.iterator();
        while (it.hasNext()) {
            if (((Action) it.next()).isEnhanced()) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "C2paManifestList(activeManifest=" + this.activeManifest + ", manifests=" + this.manifests + ')';
    }
}
