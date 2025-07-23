package android.content.theming;

import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class FieldColorIndex extends ThemeSettingsField<Integer, String> {
    public FieldColorIndex(String str, BiConsumer<ThemeSettingsUpdater, Integer> biConsumer, Function<ThemeSettings, Integer> function, ThemeSettings themeSettings) {
        super(str, biConsumer, function, themeSettings);
    }

    @Override // android.content.theming.ThemeSettingsField
    public Integer parse(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    @Override // android.content.theming.ThemeSettingsField
    public String serialize(Integer num) {
        return num.toString();
    }

    @Override // android.content.theming.ThemeSettingsField
    public boolean validate(Integer num) {
        return num.intValue() >= -1;
    }

    @Override // android.content.theming.ThemeSettingsField
    public Class<Integer> getFieldType() {
        return Integer.class;
    }

    @Override // android.content.theming.ThemeSettingsField
    public Class<String> getJsonType() {
        return String.class;
    }
}
