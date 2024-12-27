package com.android.systemui.searcle;

import android.R;
import android.content.Context;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SearcleTipLayoutHelper {
    public final int bubbleLayoutWidth;
    public final Context context;
    public final DirectionType direction;
    public final int gravity;
    public final boolean isFoldWithMainDisplay;
    public final boolean isLandscape;
    public final boolean isNaviBtnAndLandscape;
    public final boolean isTablet;
    public final TipsNaviAlign naviAlign;
    public final int naviBarHeight;
    public final int rotation;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DirectionType {
        public static final /* synthetic */ DirectionType[] $VALUES;
        public static final DirectionType XOnLTR;
        public static final DirectionType XOnRTL;
        public static final DirectionType XYOnLTR;
        public static final DirectionType XYOnRTL;
        public static final DirectionType Y;

        static {
            DirectionType directionType = new DirectionType("Y", 0);
            Y = directionType;
            DirectionType directionType2 = new DirectionType("XYOnLTR", 1);
            XYOnLTR = directionType2;
            DirectionType directionType3 = new DirectionType("XYOnRTL", 2);
            XYOnRTL = directionType3;
            DirectionType directionType4 = new DirectionType("XOnLTR", 3);
            XOnLTR = directionType4;
            DirectionType directionType5 = new DirectionType("XOnRTL", 4);
            XOnRTL = directionType5;
            DirectionType[] directionTypeArr = {directionType, directionType2, directionType3, directionType4, directionType5};
            $VALUES = directionTypeArr;
            EnumEntriesKt.enumEntries(directionTypeArr);
        }

        private DirectionType(String str, int i) {
        }

        public static DirectionType valueOf(String str) {
            return (DirectionType) Enum.valueOf(DirectionType.class, str);
        }

        public static DirectionType[] values() {
            return (DirectionType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TipsNaviAlign {
        public static final /* synthetic */ TipsNaviAlign[] $VALUES;
        public static final TipsNaviAlign Center;
        public static final TipsNaviAlign Default;
        public static final TipsNaviAlign Left;
        public static final TipsNaviAlign Right;

        static {
            TipsNaviAlign tipsNaviAlign = new TipsNaviAlign(SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT, 0);
            Default = tipsNaviAlign;
            TipsNaviAlign tipsNaviAlign2 = new TipsNaviAlign(SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT, 1);
            Left = tipsNaviAlign2;
            TipsNaviAlign tipsNaviAlign3 = new TipsNaviAlign(SystemUIAnalytics.DT_BOUNCER_POSITION_CENTER, 2);
            Center = tipsNaviAlign3;
            TipsNaviAlign tipsNaviAlign4 = new TipsNaviAlign(SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT, 3);
            Right = tipsNaviAlign4;
            TipsNaviAlign[] tipsNaviAlignArr = {tipsNaviAlign, tipsNaviAlign2, tipsNaviAlign3, tipsNaviAlign4};
            $VALUES = tipsNaviAlignArr;
            EnumEntriesKt.enumEntries(tipsNaviAlignArr);
        }

        private TipsNaviAlign(String str, int i) {
        }

        public static TipsNaviAlign valueOf(String str) {
            return (TipsNaviAlign) Enum.valueOf(TipsNaviAlign.class, str);
        }

        public static TipsNaviAlign[] values() {
            return (TipsNaviAlign[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TipsNaviAlign.values().length];
            try {
                iArr[TipsNaviAlign.Left.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TipsNaviAlign.Center.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TipsNaviAlign.Right.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[DirectionType.values().length];
            try {
                iArr2[DirectionType.Y.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[DirectionType.XOnRTL.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[DirectionType.XOnLTR.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[DirectionType.XYOnRTL.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[DirectionType.XYOnLTR.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    public SearcleTipLayoutHelper(Context context, int i, boolean z, boolean z2, boolean z3, int i2, boolean z4) {
        DirectionType directionType;
        float f;
        int i3;
        this.context = context;
        this.rotation = i;
        this.isTablet = z;
        this.isFoldWithMainDisplay = z2;
        this.naviBarHeight = context.getResources().getDimensionPixelSize(R.dimen.resolver_max_collapsed_height);
        boolean z5 = false;
        boolean z6 = context.getResources().getConfiguration().orientation == 2;
        this.isLandscape = z6;
        if (z6 && !z3) {
            z5 = true;
        }
        this.isNaviBtnAndLandscape = z5;
        TipsNaviAlign tipsNaviAlign = i2 != 0 ? i2 != 1 ? i2 != 2 ? TipsNaviAlign.Default : TipsNaviAlign.Right : TipsNaviAlign.Center : TipsNaviAlign.Left;
        this.naviAlign = tipsNaviAlign;
        int i4 = 81;
        if (z || z2) {
            if (!z3) {
                int i5 = WhenMappings.$EnumSwitchMapping$0[tipsNaviAlign.ordinal()];
                if (i5 == 1) {
                    i4 = 8388691;
                } else if (i5 == 2 ? z4 : i5 == 3) {
                    i4 = 8388693;
                }
            }
        } else if (z5) {
            i4 = i == 1 ? 8388629 : 8388627;
        }
        this.gravity = i4;
        if (!z && !z2) {
            directionType = z5 ? i == 1 ? DirectionType.XOnRTL : DirectionType.XOnLTR : DirectionType.Y;
        } else if (z3) {
            directionType = DirectionType.Y;
        } else {
            int i6 = WhenMappings.$EnumSwitchMapping$0[tipsNaviAlign.ordinal()];
            directionType = i6 != 1 ? i6 != 2 ? i6 != 3 ? DirectionType.Y : DirectionType.XYOnRTL : z4 ? DirectionType.XYOnRTL : DirectionType.Y : DirectionType.XYOnLTR;
        }
        this.direction = directionType;
        if (z) {
            f = 0.2552f;
            i3 = z6 ? context.getResources().getDisplayMetrics().widthPixels : context.getResources().getDisplayMetrics().heightPixels;
        } else if (z2) {
            f = 0.5f;
            i3 = z6 ? context.getResources().getDisplayMetrics().heightPixels : context.getResources().getDisplayMetrics().widthPixels;
        } else {
            f = 0.94f;
            i3 = z6 ? context.getResources().getDisplayMetrics().heightPixels : context.getResources().getDisplayMetrics().widthPixels;
        }
        this.bubbleLayoutWidth = (int) (i3 * f);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LayoutHelper{direction = ");
        sb.append(this.direction);
        sb.append(", gravity = ");
        sb.append(this.gravity);
        sb.append(", isTablet = ");
        sb.append(this.isTablet);
        sb.append(", isFoldWithMainDisplay = ");
        sb.append(this.isFoldWithMainDisplay);
        sb.append(", isLandscape = ");
        sb.append(this.isLandscape);
        sb.append(", isNaviBtnAndLandscape = ");
        sb.append(this.isNaviBtnAndLandscape);
        sb.append(", naviAlign = ");
        sb.append(this.naviAlign);
        sb.append(", bubbleWidth = ");
        return Anchor$$ExternalSyntheticOutline0.m(this.bubbleLayoutWidth, "}", sb);
    }
}
