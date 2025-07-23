package android.content.theming;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class FieldColorSource extends ThemeSettingsField<String, String> {

    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    @Override // android.content.theming.ThemeSettingsField
    public String parse(String str) {
        return str;
    }

    @Override // android.content.theming.ThemeSettingsField
    public String serialize(String str) {
        return str;
    }

    public FieldColorSource(String str, BiConsumer<ThemeSettingsUpdater, String> biConsumer, Function<ThemeSettings, String> function, ThemeSettings themeSettings) {
        super(str, biConsumer, function, themeSettings);
    }

    @Override // android.content.theming.ThemeSettingsField
    public boolean validate(String str) {
        str.hashCode();
        switch (str) {
            case "preset":
            case "lock_wallpaper":
            case "home_wallpaper":
                return true;
            default:
                return false;
        }
    }

    @Override // android.content.theming.ThemeSettingsField
    public Class<String> getFieldType() {
        return String.class;
    }

    @Override // android.content.theming.ThemeSettingsField
    public Class<String> getJsonType() {
        return String.class;
    }
}
