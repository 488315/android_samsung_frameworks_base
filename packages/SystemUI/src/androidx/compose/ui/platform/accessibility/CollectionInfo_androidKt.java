package androidx.compose.ui.platform.accessibility;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.semantics.CollectionInfo;
import androidx.compose.ui.semantics.CollectionItemInfo;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.util.ListUtilsKt;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CollectionInfo_androidKt {
    public static final boolean calculateIfHorizontallyStacked(List list) {
        List list2;
        long j;
        if (list.size() >= 2) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() <= 1) {
                list2 = EmptyList.INSTANCE;
            } else {
                ArrayList arrayList2 = new ArrayList();
                Object obj = arrayList.get(0);
                int size = arrayList.size() - 1;
                int i = 0;
                while (i < size) {
                    i++;
                    Object obj2 = arrayList.get(i);
                    SemanticsNode semanticsNode = (SemanticsNode) obj2;
                    SemanticsNode semanticsNode2 = (SemanticsNode) obj;
                    float abs = Math.abs(Float.intBitsToFloat((int) (semanticsNode2.getBoundsInRoot().m407getCenterF1C5BW0() >> 32)) - Float.intBitsToFloat((int) (semanticsNode.getBoundsInRoot().m407getCenterF1C5BW0() >> 32)));
                    float abs2 = Math.abs(Float.intBitsToFloat((int) (semanticsNode2.getBoundsInRoot().m407getCenterF1C5BW0() & 4294967295L)) - Float.intBitsToFloat((int) (semanticsNode.getBoundsInRoot().m407getCenterF1C5BW0() & 4294967295L)));
                    arrayList2.add(Offset.m393boximpl((Float.floatToRawIntBits(abs) << 32) | (Float.floatToRawIntBits(abs2) & 4294967295L)));
                    obj = obj2;
                }
                list2 = arrayList2;
            }
            if (list2.size() == 1) {
                j = ((Offset) CollectionsKt___CollectionsKt.first(list2)).packedValue;
            } else {
                if (list2.isEmpty()) {
                    ListUtilsKt.throwUnsupportedOperationException("Empty collection can't be reduced.");
                }
                Object first = CollectionsKt___CollectionsKt.first(list2);
                int size2 = list2.size() - 1;
                if (1 <= size2) {
                    int i2 = 1;
                    while (true) {
                        first = Offset.m393boximpl(Offset.m401plusMKHz9U(((Offset) first).packedValue, ((Offset) list2.get(i2)).packedValue));
                        if (i2 == size2) {
                            break;
                        }
                        i2++;
                    }
                }
                j = ((Offset) first).packedValue;
            }
            if (Float.intBitsToFloat((int) (4294967295L & j)) >= Float.intBitsToFloat((int) (j >> 32))) {
                return false;
            }
        }
        return true;
    }

    public static final boolean hasCollectionInfo(SemanticsNode semanticsNode) {
        SemanticsConfiguration config = semanticsNode.getConfig();
        SemanticsProperties.INSTANCE.getClass();
        return (SemanticsConfigurationKt.getOrNull(config, SemanticsProperties.CollectionInfo) == null && SemanticsConfigurationKt.getOrNull(semanticsNode.getConfig(), SemanticsProperties.SelectableGroup) == null) ? false : true;
    }

    public static final void setCollectionItemInfo(SemanticsNode semanticsNode, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        SemanticsConfiguration config = semanticsNode.getConfig();
        SemanticsProperties.INSTANCE.getClass();
        CollectionItemInfo collectionItemInfo = (CollectionItemInfo) SemanticsConfigurationKt.getOrNull(config, SemanticsProperties.CollectionItemInfo);
        if (collectionItemInfo != null) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(((Boolean) semanticsNode.getConfig().getOrElse(SemanticsProperties.Selected, new Function0() { // from class: androidx.compose.ui.platform.accessibility.CollectionInfo_androidKt$toAccessibilityCollectionItemInfo$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Boolean.FALSE;
                }
            })).booleanValue(), collectionItemInfo.rowIndex, collectionItemInfo.rowSpan, collectionItemInfo.columnIndex, collectionItemInfo.columnSpan));
        }
        SemanticsNode parent = semanticsNode.getParent();
        if (parent == null || SemanticsConfigurationKt.getOrNull(parent.getConfig(), SemanticsProperties.SelectableGroup) == null) {
            return;
        }
        CollectionInfo collectionInfo = (CollectionInfo) SemanticsConfigurationKt.getOrNull(parent.getConfig(), SemanticsProperties.CollectionInfo);
        if (collectionInfo == null || (collectionInfo.rowCount >= 0 && collectionInfo.columnCount >= 0)) {
            if (semanticsNode.getConfig().props.containsKey(SemanticsProperties.Selected)) {
                ArrayList arrayList = new ArrayList();
                List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, parent);
                int size = children$ui_release$default.size();
                int i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    SemanticsNode semanticsNode2 = (SemanticsNode) children$ui_release$default.get(i2);
                    SemanticsConfiguration config2 = semanticsNode2.getConfig();
                    SemanticsProperties.INSTANCE.getClass();
                    if (config2.props.containsKey(SemanticsProperties.Selected)) {
                        arrayList.add(semanticsNode2);
                        if (semanticsNode2.layoutNode.getPlaceOrder$ui_release() < semanticsNode.layoutNode.getPlaceOrder$ui_release()) {
                            i++;
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                boolean calculateIfHorizontallyStacked = calculateIfHorizontallyStacked(arrayList);
                int i3 = calculateIfHorizontallyStacked ? 0 : i;
                int i4 = calculateIfHorizontallyStacked ? i : 0;
                SemanticsConfiguration config3 = semanticsNode.getConfig();
                SemanticsProperties.INSTANCE.getClass();
                accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(((Boolean) config3.getOrElse(SemanticsProperties.Selected, new Function0() { // from class: androidx.compose.ui.platform.accessibility.CollectionInfo_androidKt$setCollectionItemInfo$itemInfo$1
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Boolean.FALSE;
                    }
                })).booleanValue(), i3, 1, i4, 1));
            }
        }
    }
}
