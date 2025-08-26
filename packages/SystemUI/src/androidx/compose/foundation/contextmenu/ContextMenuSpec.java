package androidx.compose.foundation.contextmenu;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* loaded from: classes.dex */
public final class ContextMenuSpec {
    public static final float ContainerWidthMax;
    public static final float ContainerWidthMin;
    public static final float CornerRadius;
    public static final long FontSize;
    public static final FontWeight FontWeight;
    public static final float HorizontalPadding;
    public static final ContextMenuSpec INSTANCE = new ContextMenuSpec();
    public static final float IconSize;
    public static final int LabelHorizontalTextAlignment;
    public static final BiasAlignment.Vertical LabelVerticalTextAlignment;
    public static final long LetterSpacing;
    public static final long LineHeight;
    public static final float ListItemHeight;
    public static final float MenuContainerElevation;
    public static final float VerticalPadding;

    static {
        Dp.Companion companion = Dp.Companion;
        ContainerWidthMin = 112;
        ContainerWidthMax = IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView;
        ListItemHeight = 48;
        MenuContainerElevation = 3;
        CornerRadius = 4;
        Alignment.Companion.getClass();
        LabelVerticalTextAlignment = Alignment.Companion.CenterVertically;
        TextAlign.Companion.getClass();
        LabelHorizontalTextAlignment = TextAlign.Start;
        HorizontalPadding = 12;
        VerticalPadding = 8;
        IconSize = 24;
        FontSize = TextUnitKt.getSp(14);
        FontWeight.Companion.getClass();
        FontWeight = FontWeight.Medium;
        LineHeight = TextUnitKt.getSp(20);
        LetterSpacing = TextUnitKt.pack(0.1f, 4294967296L);
    }

    private ContextMenuSpec() {
    }
}
