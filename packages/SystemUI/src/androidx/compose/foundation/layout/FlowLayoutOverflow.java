package androidx.compose.foundation.layout;

import com.android.systemui.bixby2.actionresult.ActionResults;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class FlowLayoutOverflow {
    public final Function1 collapseGetter;
    public final int minCrossAxisSizeToShowCollapse;
    public final int minLinesToShowCollapse;
    public final Function1 seeMoreGetter;
    public final OverflowType type;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class OverflowType {
        public static final /* synthetic */ OverflowType[] $VALUES;
        public static final OverflowType Clip;
        public static final OverflowType ExpandIndicator;
        public static final OverflowType ExpandOrCollapseIndicator;
        public static final OverflowType Visible;

        static {
            OverflowType overflowType = new OverflowType(ActionResults.RESULT_LAUNCHER_VISIBLE, 0);
            Visible = overflowType;
            OverflowType overflowType2 = new OverflowType("Clip", 1);
            Clip = overflowType2;
            OverflowType overflowType3 = new OverflowType("ExpandIndicator", 2);
            ExpandIndicator = overflowType3;
            OverflowType overflowType4 = new OverflowType("ExpandOrCollapseIndicator", 3);
            ExpandOrCollapseIndicator = overflowType4;
            OverflowType[] overflowTypeArr = {overflowType, overflowType2, overflowType3, overflowType4};
            $VALUES = overflowTypeArr;
            EnumEntriesKt.enumEntries(overflowTypeArr);
        }

        private OverflowType(String str, int i) {
        }

        public static OverflowType valueOf(String str) {
            return (OverflowType) Enum.valueOf(OverflowType.class, str);
        }

        public static OverflowType[] values() {
            return (OverflowType[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OverflowType.values().length];
            try {
                iArr[OverflowType.ExpandIndicator.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[OverflowType.ExpandOrCollapseIndicator.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ FlowLayoutOverflow(OverflowType overflowType, int i, int i2, Function1 function1, Function1 function12, DefaultConstructorMarker defaultConstructorMarker) {
        this(overflowType, i, i2, function1, function12);
    }

    private FlowLayoutOverflow(OverflowType overflowType, int i, int i2, Function1 function1, Function1 function12) {
        this.type = overflowType;
        this.minLinesToShowCollapse = i;
        this.minCrossAxisSizeToShowCollapse = i2;
        this.seeMoreGetter = function1;
        this.collapseGetter = function12;
    }

    public /* synthetic */ FlowLayoutOverflow(OverflowType overflowType, int i, int i2, Function1 function1, Function1 function12, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(overflowType, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? null : function1, (i3 & 16) != 0 ? null : function12, null);
    }
}
