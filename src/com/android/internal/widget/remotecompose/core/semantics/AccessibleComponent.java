package com.android.internal.widget.remotecompose.core.semantics;

/* loaded from: classes6.dex */
public interface AccessibleComponent extends AccessibilitySemantics {

    public enum Mode {
        SET,
        CLEAR_AND_SET,
        MERGE
    }

    default Integer getContentDescriptionId() {
        return null;
    }

    default Role getRole() {
        return null;
    }

    default Integer getTextId() {
        return null;
    }

    default boolean isClickable() {
        return false;
    }

    default Mode getMode() {
        return Mode.SET;
    }

    public enum Role {
        BUTTON("Button"),
        CHECKBOX("Checkbox"),
        SWITCH("Switch"),
        RADIO_BUTTON("RadioButton"),
        TAB("Tab"),
        IMAGE("Image"),
        DROPDOWN_LIST("DropdownList"),
        PICKER("Picker"),
        CAROUSEL("Carousel"),
        UNKNOWN(null);

        private final String mDescription;

        Role(String str) {
            this.mDescription = str;
        }

        public String getDescription() {
            return this.mDescription;
        }

        public static Role fromInt(int i) {
            Role role = UNKNOWN;
            return i < role.ordinal() ? values()[i] : role;
        }
    }
}
