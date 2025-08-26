package com.android.systemui.shared.clocks.view;

import android.view.View;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public abstract class HorizontalAlignment {
    public static final /* synthetic */ HorizontalAlignment[] $VALUES;
    public static final HorizontalAlignment CENTER;
    public static final HorizontalAlignment START;

    static {
        HorizontalAlignment horizontalAlignment = new HorizontalAlignment("LEFT", 0) { // from class: com.android.systemui.shared.clocks.view.HorizontalAlignment.LEFT
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.shared.clocks.view.HorizontalAlignment
            public final XAlignment resolveXAlignment(View view) {
                return XAlignment.LEFT;
            }
        };
        HorizontalAlignment horizontalAlignment2 = new HorizontalAlignment("RIGHT", 1) { // from class: com.android.systemui.shared.clocks.view.HorizontalAlignment.RIGHT
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.shared.clocks.view.HorizontalAlignment
            public final XAlignment resolveXAlignment(View view) {
                return XAlignment.RIGHT;
            }
        };
        HorizontalAlignment horizontalAlignment3 = new HorizontalAlignment("START", 2) { // from class: com.android.systemui.shared.clocks.view.HorizontalAlignment.START
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.shared.clocks.view.HorizontalAlignment
            public final XAlignment resolveXAlignment(View view) {
                return view.isLayoutRtl() ? XAlignment.RIGHT : XAlignment.LEFT;
            }
        };
        START = horizontalAlignment3;
        HorizontalAlignment horizontalAlignment4 = new HorizontalAlignment("END", 3) { // from class: com.android.systemui.shared.clocks.view.HorizontalAlignment.END
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.shared.clocks.view.HorizontalAlignment
            public final XAlignment resolveXAlignment(View view) {
                return view.isLayoutRtl() ? XAlignment.LEFT : XAlignment.RIGHT;
            }
        };
        HorizontalAlignment horizontalAlignment5 = new HorizontalAlignment("CENTER", 4) { // from class: com.android.systemui.shared.clocks.view.HorizontalAlignment.CENTER
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.shared.clocks.view.HorizontalAlignment
            public final XAlignment resolveXAlignment(View view) {
                return XAlignment.CENTER;
            }
        };
        CENTER = horizontalAlignment5;
        HorizontalAlignment[] horizontalAlignmentArr = {horizontalAlignment, horizontalAlignment2, horizontalAlignment3, horizontalAlignment4, horizontalAlignment5};
        $VALUES = horizontalAlignmentArr;
        EnumEntriesKt.enumEntries(horizontalAlignmentArr);
    }

    public /* synthetic */ HorizontalAlignment(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i);
    }

    public static HorizontalAlignment valueOf(String str) {
        return (HorizontalAlignment) Enum.valueOf(HorizontalAlignment.class, str);
    }

    public static HorizontalAlignment[] values() {
        return (HorizontalAlignment[]) $VALUES.clone();
    }

    public abstract XAlignment resolveXAlignment(View view);

    private HorizontalAlignment(String str, int i) {
    }
}
