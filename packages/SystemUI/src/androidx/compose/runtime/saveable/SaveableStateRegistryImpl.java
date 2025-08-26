package androidx.compose.runtime.saveable;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.text.CharsKt__CharJVMKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class SaveableStateRegistryImpl implements SaveableStateRegistry {
    public final Function1 canBeSaved;
    public final MutableScatterMap restored;
    public MutableScatterMap valueProviders;

    /* renamed from: androidx.compose.runtime.saveable.SaveableStateRegistryImpl$registerProvider$3, reason: invalid class name */
    public final class AnonymousClass3 implements SaveableStateRegistry.Entry {
        public final /* synthetic */ String $key;
        public final /* synthetic */ Function0 $valueProvider;
        public final /* synthetic */ MutableScatterMap $valueProviders;

        public AnonymousClass3(MutableScatterMap mutableScatterMap, String str, Function0 function0) {
            this.$valueProviders = mutableScatterMap;
            this.$key = str;
            this.$valueProvider = function0;
        }

        public final void unregister() {
            MutableScatterMap mutableScatterMap = this.$valueProviders;
            String str = this.$key;
            List list = (List) mutableScatterMap.remove(str);
            if (list != null) {
                list.remove(this.$valueProvider);
            }
            List list2 = list;
            if (list2 == null || list2.isEmpty()) {
                return;
            }
            mutableScatterMap.set(str, list);
        }
    }

    public SaveableStateRegistryImpl(Map<String, ? extends List<? extends Object>> map, Function1 function1) {
        MutableScatterMap mutableScatterMap;
        this.canBeSaved = function1;
        if (map == null || map.isEmpty()) {
            mutableScatterMap = null;
        } else {
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = SaveableStateRegistryKt.LocalSaveableStateRegistry;
            mutableScatterMap = new MutableScatterMap(map.size());
            for (Map.Entry<String, ? extends List<? extends Object>> entry : map.entrySet()) {
                mutableScatterMap.set(entry.getKey(), entry.getValue());
            }
        }
        this.restored = mutableScatterMap;
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final boolean canBeSaved(Object obj) {
        return ((Boolean) this.canBeSaved.mo781invoke(obj)).booleanValue();
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final Object consumeRestored(String str) {
        MutableScatterMap mutableScatterMap = this.restored;
        List list = mutableScatterMap != null ? (List) mutableScatterMap.remove(str) : null;
        List list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return null;
        }
        if (list.size() > 1 && mutableScatterMap != null) {
            List listSubList = list.subList(1, list.size());
            int iFindInsertIndex = mutableScatterMap.findInsertIndex(str);
            if (iFindInsertIndex < 0) {
                iFindInsertIndex = ~iFindInsertIndex;
            }
            Object[] objArr = mutableScatterMap.values;
            Object obj = objArr[iFindInsertIndex];
            mutableScatterMap.keys[iFindInsertIndex] = str;
            objArr[iFindInsertIndex] = listSubList;
        }
        return list.get(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0090  */
    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Map performSave() {
        char c;
        long j;
        long j2;
        long j3;
        long[] jArr;
        int i;
        long[] jArr2;
        int i2;
        char c2;
        long j4;
        MutableScatterMap mutableScatterMap = this.restored;
        if (mutableScatterMap == null && this.valueProviders == null) {
            return MapsKt__MapsKt.emptyMap();
        }
        int i3 = 0;
        int i4 = mutableScatterMap != null ? mutableScatterMap._size : 0;
        MutableScatterMap mutableScatterMap2 = this.valueProviders;
        HashMap map = new HashMap(i4 + (mutableScatterMap2 != null ? mutableScatterMap2._size : 0));
        char c3 = 7;
        long j5 = -9187201950435737472L;
        int i5 = 8;
        if (mutableScatterMap != null) {
            Object[] objArr = mutableScatterMap.keys;
            Object[] objArr2 = mutableScatterMap.values;
            long[] jArr3 = mutableScatterMap.metadata;
            int length = jArr3.length - 2;
            if (length >= 0) {
                int i6 = 0;
                j2 = 128;
                while (true) {
                    long j6 = jArr3[i6];
                    j3 = 255;
                    if ((((~j6) << c3) & j6 & j5) != j5) {
                        int i7 = 8 - ((~(i6 - length)) >>> 31);
                        int i8 = 0;
                        while (i8 < i7) {
                            if ((j6 & 255) < 128) {
                                int i9 = (i6 << 3) + i8;
                                c2 = c3;
                                j4 = j5;
                                map.put((String) objArr[i9], (List) objArr2[i9]);
                            } else {
                                c2 = c3;
                                j4 = j5;
                            }
                            j6 >>= 8;
                            i8++;
                            c3 = c2;
                            j5 = j4;
                        }
                        c = c3;
                        j = j5;
                        if (i7 != 8) {
                            break;
                        }
                    } else {
                        c = c3;
                        j = j5;
                    }
                    if (i6 == length) {
                        break;
                    }
                    i6++;
                    c3 = c;
                    j5 = j;
                }
            } else {
                c = 7;
                j = -9187201950435737472L;
                j2 = 128;
                j3 = 255;
            }
        }
        MutableScatterMap mutableScatterMap3 = this.valueProviders;
        if (mutableScatterMap3 != null) {
            Object[] objArr3 = mutableScatterMap3.keys;
            Object[] objArr4 = mutableScatterMap3.values;
            long[] jArr4 = mutableScatterMap3.metadata;
            int length2 = jArr4.length - 2;
            if (length2 >= 0) {
                int i10 = 0;
                while (true) {
                    long j7 = jArr4[i10];
                    if ((((~j7) << c) & j7 & j) != j) {
                        int i11 = 8 - ((~(i10 - length2)) >>> 31);
                        int i12 = i3;
                        while (i12 < i11) {
                            if ((j7 & j3) < j2) {
                                int i13 = (i10 << 3) + i12;
                                Object obj = objArr3[i13];
                                List list = (List) objArr4[i13];
                                String str = (String) obj;
                                i2 = i5;
                                if (list.size() == 1) {
                                    Object objInvoke = ((Function0) list.get(i3)).invoke();
                                    if (objInvoke != null) {
                                        if (!canBeSaved(objInvoke)) {
                                            throw new IllegalStateException(RememberSaveableKt.generateCannotBeSavedErrorMessage(objInvoke).toString());
                                        }
                                        map.put(str, CollectionsKt__CollectionsKt.arrayListOf(objInvoke));
                                    }
                                    jArr2 = jArr4;
                                } else {
                                    int size = list.size();
                                    ArrayList arrayList = new ArrayList(size);
                                    while (i3 < size) {
                                        long[] jArr5 = jArr4;
                                        Object objInvoke2 = ((Function0) list.get(i3)).invoke();
                                        if (objInvoke2 != null && !canBeSaved(objInvoke2)) {
                                            throw new IllegalStateException(RememberSaveableKt.generateCannotBeSavedErrorMessage(objInvoke2).toString());
                                        }
                                        arrayList.add(objInvoke2);
                                        i3++;
                                        jArr4 = jArr5;
                                    }
                                    jArr2 = jArr4;
                                    map.put(str, arrayList);
                                }
                            } else {
                                jArr2 = jArr4;
                                i2 = i5;
                            }
                            j7 >>= i2;
                            i12++;
                            i5 = i2;
                            jArr4 = jArr2;
                            i3 = 0;
                        }
                        jArr = jArr4;
                        i = i5;
                        if (i11 != i) {
                            break;
                        }
                    } else {
                        jArr = jArr4;
                        i = i5;
                    }
                    if (i10 == length2) {
                        break;
                    }
                    i10++;
                    i5 = i;
                    jArr4 = jArr;
                    i3 = 0;
                }
            }
        }
        return map;
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final SaveableStateRegistry.Entry registerProvider(String str, Function0 function0) {
        boolean z = true;
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = SaveableStateRegistryKt.LocalSaveableStateRegistry;
        int length = str.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (!CharsKt__CharJVMKt.isWhitespace(str.charAt(i))) {
                z = false;
                break;
            }
            i++;
        }
        if (z) {
            throw new IllegalArgumentException("Registered key is empty or blank");
        }
        MutableScatterMap mutableScatterMapMutableScatterMapOf = this.valueProviders;
        if (mutableScatterMapMutableScatterMapOf == null) {
            mutableScatterMapMutableScatterMapOf = ScatterMapKt.mutableScatterMapOf();
            this.valueProviders = mutableScatterMapMutableScatterMapOf;
        }
        Object arrayList = mutableScatterMapMutableScatterMapOf.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            mutableScatterMapMutableScatterMapOf.set(str, arrayList);
        }
        ((List) arrayList).add(function0);
        return new AnonymousClass3(mutableScatterMapMutableScatterMapOf, str, function0);
    }
}
