package androidx.compose.ui.platform;

import android.content.res.Resources;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.LayoutNode$$ExternalSyntheticLambda0;
import androidx.compose.ui.semantics.ProgressBarRangeInfo;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsConfigurationKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.state.ToggleableState;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidComposeViewAccessibilityDelegateCompat_androidKt {
    public static final Function2 UnmergedConfigComparator;
    public static final Comparator[] semanticComparators;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ToggleableState.values().length];
            try {
                iArr[ToggleableState.On.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ToggleableState.Off.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ToggleableState.Indeterminate.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Comparator[] comparatorArr = new Comparator[2];
        int i = 0;
        while (i < 2) {
            final Comparator comparator = i == 0 ? RtlBoundsComparator.INSTANCE : LtrBoundsComparator.INSTANCE;
            LayoutNode.Companion.getClass();
            final LayoutNode$$ExternalSyntheticLambda0 layoutNode$$ExternalSyntheticLambda0 = LayoutNode.ZComparator;
            final Comparator comparator2 = new Comparator() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$special$$inlined$thenBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compare = comparator.compare(obj, obj2);
                    return compare != 0 ? compare : layoutNode$$ExternalSyntheticLambda0.compare(((SemanticsNode) obj).layoutNode, ((SemanticsNode) obj2).layoutNode);
                }
            };
            comparatorArr[i] = new Comparator() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$special$$inlined$thenBy$2
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int compare = comparator2.compare(obj, obj2);
                    return compare != 0 ? compare : ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((SemanticsNode) obj).id), Integer.valueOf(((SemanticsNode) obj2).id));
                }
            };
            i++;
        }
        semanticComparators = comparatorArr;
        UnmergedConfigComparator = new Function2() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$UnmergedConfigComparator$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                SemanticsConfiguration semanticsConfiguration = ((SemanticsNode) obj).unmergedConfig;
                SemanticsProperties.INSTANCE.getClass();
                SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.TraversalIndex;
                return Integer.valueOf(Float.compare(((Number) semanticsConfiguration.getOrElse(semanticsPropertyKey, new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$UnmergedConfigComparator$1.1
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Float.valueOf(0.0f);
                    }
                })).floatValue(), ((Number) ((SemanticsNode) obj2).unmergedConfig.getOrElse(semanticsPropertyKey, new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$UnmergedConfigComparator$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Float.valueOf(0.0f);
                    }
                })).floatValue()));
            }
        };
    }

    public static final boolean access$enabled(SemanticsNode semanticsNode) {
        SemanticsConfiguration config = semanticsNode.getConfig();
        SemanticsProperties.INSTANCE.getClass();
        return !config.props.containsKey(SemanticsProperties.Disabled);
    }

    public static final LayoutNode findClosestParentNode(LayoutNode layoutNode, Function1 function1) {
        for (LayoutNode parent$ui_release = layoutNode.getParent$ui_release(); parent$ui_release != null; parent$ui_release = parent$ui_release.getParent$ui_release()) {
            if (((Boolean) function1.mo779invoke(parent$ui_release)).booleanValue()) {
                return parent$ui_release;
            }
        }
        return null;
    }

    public static final void geometryDepthFirstSearch(SemanticsNode semanticsNode, ArrayList arrayList, MutableIntObjectMap mutableIntObjectMap, MutableIntObjectMap mutableIntObjectMap2, Resources resources) {
        boolean isRtl = isRtl(semanticsNode);
        SemanticsProperties.INSTANCE.getClass();
        boolean booleanValue = ((Boolean) semanticsNode.unmergedConfig.getOrElse(SemanticsProperties.IsTraversalGroup, new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$geometryDepthFirstSearch$isTraversalGroup$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.FALSE;
            }
        })).booleanValue();
        int i = semanticsNode.id;
        if ((booleanValue || isScreenReaderFocusable(semanticsNode, resources)) && mutableIntObjectMap2.containsKey(i)) {
            arrayList.add(semanticsNode);
        }
        if (booleanValue) {
            mutableIntObjectMap.set(i, subtreeSortedByGeometryGrouping(isRtl, SemanticsNode.getChildren$ui_release$default(7, semanticsNode), mutableIntObjectMap2, resources));
            return;
        }
        List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(7, semanticsNode);
        int size = children$ui_release$default.size();
        for (int i2 = 0; i2 < size; i2++) {
            geometryDepthFirstSearch((SemanticsNode) children$ui_release$default.get(i2), arrayList, mutableIntObjectMap, mutableIntObjectMap2, resources);
        }
    }

    public static final boolean getInfoIsCheckable(SemanticsNode semanticsNode) {
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        semanticsProperties.getClass();
        ToggleableState toggleableState = (ToggleableState) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.ToggleableState);
        semanticsProperties.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.Role;
        SemanticsConfiguration semanticsConfiguration2 = semanticsNode.unmergedConfig;
        Role role = (Role) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, semanticsPropertyKey);
        boolean z = toggleableState != null;
        semanticsProperties.getClass();
        if (((Boolean) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Selected)) == null) {
            return z;
        }
        Role.Companion.getClass();
        int i = Role.Tab;
        if (role != null && role.value == i) {
            return z;
        }
        return true;
    }

    public static final String getInfoStateDescriptionOrNull(SemanticsNode semanticsNode, Resources resources) {
        Collection collection;
        CharSequence charSequence;
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        SemanticsProperties.INSTANCE.getClass();
        Object orNull = SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.StateDescription);
        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.ToggleableState;
        SemanticsConfiguration semanticsConfiguration2 = semanticsNode.unmergedConfig;
        ToggleableState toggleableState = (ToggleableState) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, semanticsPropertyKey);
        Role role = (Role) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Role);
        if (toggleableState != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[toggleableState.ordinal()];
            if (i == 1) {
                Role.Companion.getClass();
                int i2 = Role.Switch;
                if (role != null && role.value == i2 && orNull == null) {
                    orNull = resources.getString(R.string.state_on);
                }
            } else if (i == 2) {
                Role.Companion.getClass();
                int i3 = Role.Switch;
                if (role != null && role.value == i3 && orNull == null) {
                    orNull = resources.getString(R.string.state_off);
                }
            } else if (i == 3 && orNull == null) {
                orNull = resources.getString(R.string.indeterminate);
            }
        }
        Boolean bool = (Boolean) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.Selected);
        if (bool != null) {
            boolean booleanValue = bool.booleanValue();
            Role.Companion.getClass();
            int i4 = Role.Tab;
            if ((role == null || role.value != i4) && orNull == null) {
                orNull = booleanValue ? resources.getString(R.string.selected) : resources.getString(R.string.not_selected);
            }
        }
        ProgressBarRangeInfo progressBarRangeInfo = (ProgressBarRangeInfo) SemanticsConfigurationKt.getOrNull(semanticsConfiguration2, SemanticsProperties.ProgressBarRangeInfo);
        if (progressBarRangeInfo != null) {
            ProgressBarRangeInfo.Companion.getClass();
            if (progressBarRangeInfo != ProgressBarRangeInfo.Indeterminate) {
                if (orNull == null) {
                    ClosedFloatRange closedFloatRange = (ClosedFloatRange) progressBarRangeInfo.range;
                    float f = closedFloatRange._endInclusive;
                    float f2 = closedFloatRange._start;
                    float f3 = f - f2 == 0.0f ? 0.0f : (progressBarRangeInfo.current - f2) / (closedFloatRange._endInclusive - f2);
                    if (f3 < 0.0f) {
                        f3 = 0.0f;
                    }
                    if (f3 > 1.0f) {
                        f3 = 1.0f;
                    }
                    orNull = resources.getString(R.string.template_percent, Integer.valueOf(f3 == 0.0f ? 0 : f3 == 1.0f ? 100 : RangesKt___RangesKt.coerceIn(Math.round(f3 * 100), 1, 99)));
                }
            } else if (orNull == null) {
                orNull = resources.getString(R.string.in_progress);
            }
        }
        SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.EditableText;
        if (semanticsConfiguration2.props.containsKey(semanticsPropertyKey2)) {
            SemanticsConfiguration config = new SemanticsNode(semanticsNode.outerSemanticsNode, true, semanticsNode.layoutNode, semanticsConfiguration2).getConfig();
            Collection collection2 = (Collection) SemanticsConfigurationKt.getOrNull(config, SemanticsProperties.ContentDescription);
            orNull = ((collection2 == null || collection2.isEmpty()) && ((collection = (Collection) SemanticsConfigurationKt.getOrNull(config, SemanticsProperties.Text)) == null || collection.isEmpty()) && ((charSequence = (CharSequence) SemanticsConfigurationKt.getOrNull(config, semanticsPropertyKey2)) == null || charSequence.length() == 0)) ? resources.getString(R.string.state_empty) : null;
        }
        return (String) orNull;
    }

    public static final AnnotatedString getInfoText(SemanticsNode semanticsNode) {
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        SemanticsProperties semanticsProperties = SemanticsProperties.INSTANCE;
        semanticsProperties.getClass();
        AnnotatedString annotatedString = (AnnotatedString) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.EditableText);
        semanticsProperties.getClass();
        List list = (List) SemanticsConfigurationKt.getOrNull(semanticsNode.unmergedConfig, SemanticsProperties.Text);
        return annotatedString == null ? list != null ? (AnnotatedString) CollectionsKt___CollectionsKt.firstOrNull(list) : null : annotatedString;
    }

    public static final boolean isRtl(SemanticsNode semanticsNode) {
        return semanticsNode.layoutNode.layoutDirection == LayoutDirection.Rtl;
    }

    public static final boolean isScreenReaderFocusable(SemanticsNode semanticsNode, Resources resources) {
        SemanticsConfiguration semanticsConfiguration = semanticsNode.unmergedConfig;
        SemanticsProperties.INSTANCE.getClass();
        List list = (List) SemanticsConfigurationKt.getOrNull(semanticsConfiguration, SemanticsProperties.ContentDescription);
        return !SemanticsUtils_androidKt.isHidden(semanticsNode) && (semanticsNode.unmergedConfig.isMergingSemanticsOfDescendants || (semanticsNode.isUnmergedLeafNode$ui_release() && ((list != null ? (String) CollectionsKt___CollectionsKt.firstOrNull(list) : null) != null || getInfoText(semanticsNode) != null || getInfoStateDescriptionOrNull(semanticsNode, resources) != null || getInfoIsCheckable(semanticsNode))));
    }

    public static final List subtreeSortedByGeometryGrouping(boolean z, List list, MutableIntObjectMap mutableIntObjectMap, Resources resources) {
        MutableIntObjectMap mutableIntObjectMapOf = IntObjectMapKt.mutableIntObjectMapOf();
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            geometryDepthFirstSearch((SemanticsNode) list.get(i), arrayList, mutableIntObjectMapOf, mutableIntObjectMap, resources);
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size() / 2);
        int size2 = arrayList.size() - 1;
        if (size2 >= 0) {
            int i2 = 0;
            while (true) {
                SemanticsNode semanticsNode = (SemanticsNode) arrayList.get(i2);
                if (i2 != 0) {
                    float f = semanticsNode.getBoundsInWindow().top;
                    float f2 = semanticsNode.getBoundsInWindow().bottom;
                    boolean z2 = f >= f2;
                    int size3 = arrayList2.size() - 1;
                    if (size3 >= 0) {
                        int i3 = 0;
                        while (true) {
                            Rect rect = (Rect) ((Pair) arrayList2.get(i3)).getFirst();
                            float f3 = rect.top;
                            float f4 = rect.bottom;
                            boolean z3 = f3 >= f4;
                            if (!z2 && !z3 && Math.max(f, f3) < Math.min(f2, f4)) {
                                arrayList2.set(i3, new Pair(new Rect(Math.max(rect.left, 0.0f), Math.max(rect.top, f), Math.min(rect.right, Float.POSITIVE_INFINITY), Math.min(f4, f2)), ((Pair) arrayList2.get(i3)).getSecond()));
                                ((List) ((Pair) arrayList2.get(i3)).getSecond()).add(semanticsNode);
                                break;
                            }
                            if (i3 == size3) {
                                break;
                            }
                            i3++;
                        }
                    }
                }
                arrayList2.add(new Pair(semanticsNode.getBoundsInWindow(), CollectionsKt__CollectionsKt.mutableListOf(semanticsNode)));
                if (i2 == size2) {
                    break;
                }
                i2++;
            }
        }
        CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList2, TopBottomBoundsComparator.INSTANCE);
        ArrayList arrayList3 = new ArrayList();
        Comparator comparator = semanticComparators[!z ? 1 : 0];
        int size4 = arrayList2.size();
        for (int i4 = 0; i4 < size4; i4++) {
            Pair pair = (Pair) arrayList2.get(i4);
            CollectionsKt__MutableCollectionsJVMKt.sortWith((List) pair.getSecond(), comparator);
            arrayList3.addAll((Collection) pair.getSecond());
        }
        final Function2 function2 = UnmergedConfigComparator;
        CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList3, new Comparator() { // from class: androidx.compose.ui.platform.AndroidComposeViewAccessibilityDelegateCompat_androidKt$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                Function2 function22 = Function2.this;
                Comparator[] comparatorArr = AndroidComposeViewAccessibilityDelegateCompat_androidKt.semanticComparators;
                return ((Number) function22.invoke(obj, obj2)).intValue();
            }
        });
        int i5 = 0;
        while (i5 <= arrayList3.size() - 1) {
            List list2 = (List) mutableIntObjectMapOf.get(((SemanticsNode) arrayList3.get(i5)).id);
            if (list2 != null) {
                if (isScreenReaderFocusable((SemanticsNode) arrayList3.get(i5), resources)) {
                    i5++;
                } else {
                    arrayList3.remove(i5);
                }
                arrayList3.addAll(i5, list2);
                i5 += list2.size();
            } else {
                i5++;
            }
        }
        return arrayList3;
    }
}
