package com.android.systemui.settings.multisim.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* loaded from: classes3.dex */
public interface Button {

    public interface ClickListener {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Layout {
        public static final /* synthetic */ Layout[] $VALUES;
        public static final Layout HIDEDETAILS;
        public static final Layout NORMAL;
        public static final Layout SIMINFO;
        public static final Layout TEXTONLY_1;
        public static final Layout TEXTONLY_2;

        static {
            Layout layout = new Layout("NORMAL", 0);
            NORMAL = layout;
            Layout layout2 = new Layout("SIMINFO", 1);
            SIMINFO = layout2;
            Layout layout3 = new Layout("TEXTONLY_1", 2);
            TEXTONLY_1 = layout3;
            Layout layout4 = new Layout("TEXTONLY_2", 3);
            TEXTONLY_2 = layout4;
            Layout layout5 = new Layout("HIDEDETAILS", 4);
            HIDEDETAILS = layout5;
            Layout[] layoutArr = {layout, layout2, layout3, layout4, layout5};
            $VALUES = layoutArr;
            EnumEntriesKt.enumEntries(layoutArr);
        }

        private Layout(String str, int i) {
        }

        public static Layout valueOf(String str) {
            return (Layout) Enum.valueOf(Layout.class, str);
        }

        public static Layout[] values() {
            return (Layout[]) $VALUES.clone();
        }
    }
}
