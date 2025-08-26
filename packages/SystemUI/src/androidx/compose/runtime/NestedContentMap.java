package androidx.compose.runtime;

import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.ObjectList;
import androidx.compose.runtime.collection.MultiValueMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class NestedContentMap {
    public final MutableScatterMap contentMap = MultiValueMap.m344constructorimpl$default();
    public final MutableScatterMap containerMap = MultiValueMap.m344constructorimpl$default();

    public final void usedContainer(final MovableContentStateReference movableContentStateReference) {
        Object obj = this.containerMap.get(movableContentStateReference);
        if (obj != null) {
            boolean z = obj instanceof MutableObjectList;
            MutableScatterMap mutableScatterMap = this.contentMap;
            if (!z) {
                MultiValueMap.m346removeValueIfimpl(mutableScatterMap, (MovableContent) obj, new Function1() { // from class: androidx.compose.runtime.NestedContentMap$usedContainer$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        return Boolean.valueOf(Intrinsics.areEqual(((NestedMovableContent) obj2).container, movableContentStateReference));
                    }
                });
                return;
            }
            ObjectList objectList = (ObjectList) obj;
            Object[] objArr = objectList.content;
            int i = objectList._size;
            for (int i2 = 0; i2 < i; i2++) {
                MultiValueMap.m346removeValueIfimpl(mutableScatterMap, (MovableContent) objArr[i2], new Function1() { // from class: androidx.compose.runtime.NestedContentMap$usedContainer$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        return Boolean.valueOf(Intrinsics.areEqual(((NestedMovableContent) obj2).container, movableContentStateReference));
                    }
                });
            }
        }
    }
}
