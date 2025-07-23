package android.content.theming;

import android.graphics.Color;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class FieldColor extends ThemeSettingsField<Integer, String> {
    private static final Pattern COLOR_PATTERN = Pattern.compile("[0-9a-fA-F]{6,8}");

    public FieldColor(String str, BiConsumer<ThemeSettingsUpdater, Integer> biConsumer, Function<ThemeSettings, Integer> function, ThemeSettings themeSettings) {
        super(str, biConsumer, function, themeSettings);
    }

    @Override // android.content.theming.ThemeSettingsField
    public Integer parse(String str) {
        if (str == null || !COLOR_PATTERN.matcher(str).matches()) {
            return null;
        }
        try {
            return Integer.valueOf(Color.valueOf(Color.parseColor("#" + str)).toArgb());
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    @Override // android.content.theming.ThemeSettingsField
    public String serialize(Integer num) {
        return Integer.toHexString(num.intValue());
    }

    @Override // android.content.theming.ThemeSettingsField
    public boolean validate(Integer num) {
        return !num.equals(0);
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
