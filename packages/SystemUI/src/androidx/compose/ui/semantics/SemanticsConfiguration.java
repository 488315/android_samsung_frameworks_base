package androidx.compose.ui.semantics;

import androidx.collection.MapWrapper;
import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.JvmActuals_jvmKt;
import java.util.Iterator;
import java.util.Map;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsConfiguration implements SemanticsPropertyReceiver, Iterable<Map.Entry<? extends SemanticsPropertyKey<?>, ? extends Object>>, KMappedMarker {
    public boolean isClearingSemantics;
    public boolean isMergingSemanticsOfDescendants;
    public MapWrapper mapWrapper;
    public final MutableScatterMap props = ScatterMapKt.mutableScatterMapOf();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SemanticsConfiguration)) {
            return false;
        }
        SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) obj;
        return Intrinsics.areEqual(this.props, semanticsConfiguration.props) && this.isMergingSemanticsOfDescendants == semanticsConfiguration.isMergingSemanticsOfDescendants && this.isClearingSemantics == semanticsConfiguration.isClearingSemantics;
    }

    public final Object get(SemanticsPropertyKey semanticsPropertyKey) {
        Object obj = this.props.get(semanticsPropertyKey);
        if (obj != null) {
            return obj;
        }
        throw new IllegalStateException("Key not present: " + semanticsPropertyKey + " - consider getOrElse or getOrNull");
    }

    public final Object getOrElse(SemanticsPropertyKey semanticsPropertyKey, Function0 function0) {
        Object obj = this.props.get(semanticsPropertyKey);
        return obj == null ? function0.invoke() : obj;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isClearingSemantics) + TransitionData$$ExternalSyntheticOutline0.m(this.props.hashCode() * 31, 31, this.isMergingSemanticsOfDescendants);
    }

    @Override // java.lang.Iterable
    public final Iterator<Map.Entry<? extends SemanticsPropertyKey<?>, ? extends Object>> iterator() {
        MapWrapper mapWrapper = this.mapWrapper;
        if (mapWrapper == null) {
            MutableScatterMap mutableScatterMap = this.props;
            mutableScatterMap.getClass();
            MapWrapper mapWrapper2 = new MapWrapper(mutableScatterMap);
            this.mapWrapper = mapWrapper2;
            mapWrapper = mapWrapper2;
        }
        return mapWrapper.entrySet().iterator();
    }

    public final void set(SemanticsPropertyKey semanticsPropertyKey, Object obj) {
        if (!(obj instanceof AccessibilityAction) || !this.props.containsKey(semanticsPropertyKey)) {
            this.props.set(semanticsPropertyKey, obj);
            return;
        }
        AccessibilityAction accessibilityAction = (AccessibilityAction) this.props.get(semanticsPropertyKey);
        MutableScatterMap mutableScatterMap = this.props;
        AccessibilityAction accessibilityAction2 = (AccessibilityAction) obj;
        String str = accessibilityAction2.label;
        if (str == null) {
            str = accessibilityAction.label;
        }
        Function function = accessibilityAction2.action;
        if (function == null) {
            function = accessibilityAction.action;
        }
        mutableScatterMap.set(semanticsPropertyKey, new AccessibilityAction(str, function));
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.isMergingSemanticsOfDescendants) {
            sb.append("mergeDescendants=true");
            str = ", ";
        } else {
            str = "";
        }
        if (this.isClearingSemantics) {
            sb.append(str);
            sb.append("isClearingSemantics=true");
            str = ", ";
        }
        MutableScatterMap mutableScatterMap = this.props;
        Object[] objArr = mutableScatterMap.keys;
        Object[] objArr2 = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj = objArr[i4];
                            Object obj2 = objArr2[i4];
                            sb.append(str);
                            sb.append(((SemanticsPropertyKey) obj).name);
                            sb.append(" : ");
                            sb.append(obj2);
                            str = ", ";
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                }
                i++;
            }
        }
        return JvmActuals_jvmKt.simpleIdentityToString(this) + "{ " + ((Object) sb) + " }";
    }
}
