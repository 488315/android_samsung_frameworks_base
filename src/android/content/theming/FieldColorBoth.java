package android.content.theming;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class FieldColorBoth extends ThemeSettingsField<Boolean, String> {
    public FieldColorBoth(String str, BiConsumer<ThemeSettingsUpdater, Boolean> biConsumer, Function<ThemeSettings, Boolean> function, ThemeSettings themeSettings) {
        super(str, biConsumer, function, themeSettings);
    }

    @Override // android.content.theming.ThemeSettingsField
    public Boolean parse(String str) {
        str.hashCode();
        if (str.equals("0")) {
            return false;
        }
        return !str.equals("1") ? null : true;
    }

    @Override // android.content.theming.ThemeSettingsField
    public String serialize(Boolean bool) {
        return bool.booleanValue() ? "1" : "0";
    }

    @Override // android.content.theming.ThemeSettingsField
    public boolean validate(Boolean bool) {
        Objects.requireNonNull(bool);
        return true;
    }

    @Override // android.content.theming.ThemeSettingsField
    public Class<Boolean> getFieldType() {
        return Boolean.class;
    }

    @Override // android.content.theming.ThemeSettingsField
    public Class<String> getJsonType() {
        return String.class;
    }
}
