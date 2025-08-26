package androidx.compose.runtime;

import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableScatterMap;
import androidx.compose.runtime.collection.MultiValueMap;
import com.android.systemui.compose.EnableCommand$enableCompositionTracing$1;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
final class Pending {
    public int groupIndex;
    public final MutableIntObjectMap groupInfos;
    public final List keyInfos;
    public final Lazy keyMap$delegate;
    public final int startIndex;
    public final List usedKeys;

    public Pending(List<KeyInfo> list, int i) {
        this.keyInfos = list;
        this.startIndex = i;
        if (!(i >= 0)) {
            PreconditionsKt.throwIllegalArgumentException("Invalid start index");
        }
        this.usedKeys = new ArrayList();
        MutableIntObjectMap mutableIntObjectMap = new MutableIntObjectMap(0, 1, null);
        int size = list.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            KeyInfo keyInfo = (KeyInfo) this.keyInfos.get(i3);
            int i4 = keyInfo.location;
            int i5 = keyInfo.nodes;
            mutableIntObjectMap.set(i4, new GroupInfo(i3, i2, i5));
            i2 += i5;
        }
        this.groupInfos = mutableIntObjectMap;
        this.keyMap$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.compose.runtime.Pending$keyMap$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int size2 = this.this$0.keyInfos.size();
                EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
                MutableScatterMap mutableScatterMap = new MutableScatterMap(size2);
                Pending pending = this.this$0;
                int size3 = pending.keyInfos.size();
                for (int i6 = 0; i6 < size3; i6++) {
                    KeyInfo keyInfo2 = (KeyInfo) pending.keyInfos.get(i6);
                    Object obj = keyInfo2.objectKey;
                    int i7 = keyInfo2.key;
                    MultiValueMap.m342addimpl(mutableScatterMap, obj != null ? new JoinedKey(Integer.valueOf(i7), keyInfo2.objectKey) : Integer.valueOf(i7), keyInfo2);
                }
                return MultiValueMap.m343boximpl(mutableScatterMap);
            }
        });
    }

    public final boolean updateNodeCount(int i, int i2) {
        int i3;
        MutableIntObjectMap mutableIntObjectMap = this.groupInfos;
        GroupInfo groupInfo = (GroupInfo) mutableIntObjectMap.get(i);
        if (groupInfo == null) {
            return false;
        }
        int i4 = groupInfo.nodeIndex;
        int i5 = i2 - groupInfo.nodeCount;
        groupInfo.nodeCount = i2;
        if (i5 == 0) {
            return true;
        }
        Object[] objArr = mutableIntObjectMap.values;
        long[] jArr = mutableIntObjectMap.metadata;
        int length = jArr.length - 2;
        if (length < 0) {
            return true;
        }
        int i6 = 0;
        while (true) {
            long j = jArr[i6];
            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                int i7 = 8 - ((~(i6 - length)) >>> 31);
                for (int i8 = 0; i8 < i7; i8++) {
                    if ((255 & j) < 128) {
                        GroupInfo groupInfo2 = (GroupInfo) objArr[(i6 << 3) + i8];
                        if (groupInfo2.nodeIndex >= i4 && !groupInfo2.equals(groupInfo) && (i3 = groupInfo2.nodeIndex + i5) >= 0) {
                            groupInfo2.nodeIndex = i3;
                        }
                    }
                    j >>= 8;
                }
                if (i7 != 8) {
                    return true;
                }
            }
            if (i6 == length) {
                return true;
            }
            i6++;
        }
    }
}
