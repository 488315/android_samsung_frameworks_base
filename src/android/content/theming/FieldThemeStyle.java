package android.content.theming;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class FieldThemeStyle extends ThemeSettingsField<Integer, String> {
    private static final List<Integer> sValidStyles = Arrays.asList(3, 0, 1, 5, 4, 2, 7);

    public FieldThemeStyle(String str, BiConsumer<ThemeSettingsUpdater, Integer> biConsumer, Function<ThemeSettings, Integer> function, ThemeSettings themeSettings) {
        super(str, biConsumer, function, themeSettings);
    }

    @Override // android.content.theming.ThemeSettingsField
    public String serialize(Integer num) {
        return ThemeStyle.toString(num);
    }

    @Override // android.content.theming.ThemeSettingsField
    public boolean validate(Integer num) {
        return sValidStyles.contains(num);
    }

    @Override // android.content.theming.ThemeSettingsField
    public Integer parse(String str) {
        try {
            return Integer.valueOf(ThemeStyle.valueOf(str));
        } catch (Exception unused) {
            return null;
        }
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
