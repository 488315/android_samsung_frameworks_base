package androidx.compose.ui.platform.actionmodecallback;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MenuItemOption {
    public static final /* synthetic */ MenuItemOption[] $VALUES;
    public static final MenuItemOption Autofill;
    public static final MenuItemOption Copy;
    public static final MenuItemOption Cut;
    public static final MenuItemOption Paste;
    public static final MenuItemOption SelectAll;
    private final int id;
    private final int order;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MenuItemOption.values().length];
            try {
                iArr[MenuItemOption.Copy.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MenuItemOption.Paste.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[MenuItemOption.Cut.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[MenuItemOption.SelectAll.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[MenuItemOption.Autofill.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        MenuItemOption menuItemOption = new MenuItemOption("Copy", 0, 0);
        Copy = menuItemOption;
        MenuItemOption menuItemOption2 = new MenuItemOption("Paste", 1, 1);
        Paste = menuItemOption2;
        MenuItemOption menuItemOption3 = new MenuItemOption("Cut", 2, 2);
        Cut = menuItemOption3;
        MenuItemOption menuItemOption4 = new MenuItemOption("SelectAll", 3, 3);
        SelectAll = menuItemOption4;
        MenuItemOption menuItemOption5 = new MenuItemOption("Autofill", 4, 4);
        Autofill = menuItemOption5;
        MenuItemOption[] menuItemOptionArr = {menuItemOption, menuItemOption2, menuItemOption3, menuItemOption4, menuItemOption5};
        $VALUES = menuItemOptionArr;
        EnumEntriesKt.enumEntries(menuItemOptionArr);
    }

    private MenuItemOption(String str, int i, int i2) {
        this.id = i2;
        this.order = i2;
    }

    public static MenuItemOption valueOf(String str) {
        return (MenuItemOption) Enum.valueOf(MenuItemOption.class, str);
    }

    public static MenuItemOption[] values() {
        return (MenuItemOption[]) $VALUES.clone();
    }

    public final int getId() {
        return this.id;
    }

    public final int getOrder() {
        return this.order;
    }
}
