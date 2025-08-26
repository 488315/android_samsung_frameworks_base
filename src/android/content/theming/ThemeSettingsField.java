package android.content.theming;

import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class ThemeSettingsField<T, J> {
    private static final String KEY_PREFIX = "android.theme.customization.";
    public static final String OVERLAY_CATEGORY_ACCENT_COLOR = "android.theme.customization.accent_color";
    public static final String OVERLAY_CATEGORY_SYSTEM_PALETTE = "android.theme.customization.system_palette";
    public static final String OVERLAY_CATEGORY_THEME_STYLE = "android.theme.customization.theme_style";
    public static final String OVERLAY_COLOR_BOTH = "android.theme.customization.color_both";
    public static final String OVERLAY_COLOR_INDEX = "android.theme.customization.color_index";
    public static final String OVERLAY_COLOR_SOURCE = "android.theme.customization.color_source";
    private static final String TAG = "ThemeSettingsField";
    public final String key;
    private final ThemeSettings mDefaults;
    private final Function<ThemeSettings, T> mGetter;
    private final BiConsumer<ThemeSettingsUpdater, T> mSetter;

    public abstract Class<T> getFieldType();

    public abstract Class<J> getJsonType();

    public abstract T parse(J j);

    public abstract J serialize(T t);

    public abstract boolean validate(T t);

    public static ThemeSettingsField<?, ?>[] getFields(ThemeSettings themeSettings) {
        return new ThemeSettingsField[]{new FieldColorIndex("android.theme.customization.color_index", new BiConsumer() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ThemeSettingsUpdater) obj).colorIndex(((Integer) obj2).intValue());
            }
        }, new Function() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ThemeSettings) obj).colorIndex();
            }
        }, themeSettings), new FieldColor("android.theme.customization.system_palette", new BiConsumer() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ThemeSettingsUpdater) obj).systemPalette(((Integer) obj2).intValue());
            }
        }, new Function() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ThemeSettings) obj).systemPalette();
            }
        }, themeSettings), new FieldColor("android.theme.customization.accent_color", new BiConsumer() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ThemeSettingsUpdater) obj).accentColor(((Integer) obj2).intValue());
            }
        }, new Function() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ThemeSettings) obj).accentColor();
            }
        }, themeSettings), new FieldColorSource("android.theme.customization.color_source", new BiConsumer() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ThemeSettingsUpdater) obj).colorSource((String) obj2);
            }
        }, new Function() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ThemeSettings) obj).colorSource();
            }
        }, themeSettings), new FieldThemeStyle("android.theme.customization.theme_style", new BiConsumer() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ThemeSettingsUpdater) obj).themeStyle(((Integer) obj2).intValue());
            }
        }, new Function() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ThemeSettings) obj).themeStyle();
            }
        }, themeSettings), new FieldColorBoth(OVERLAY_COLOR_BOTH, new BiConsumer() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ThemeSettingsUpdater) obj).colorBoth(((Boolean) obj2).booleanValue());
            }
        }, new Function() { // from class: android.content.theming.ThemeSettingsField$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ThemeSettings) obj).colorBoth();
            }
        }, themeSettings)};
    }

    public ThemeSettingsField(String str, BiConsumer<ThemeSettingsUpdater, T> biConsumer, Function<ThemeSettings, T> function, ThemeSettings themeSettings) {
        this.key = str;
        this.mSetter = biConsumer;
        this.mGetter = function;
        this.mDefaults = themeSettings;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private T fallbackParse(Object obj, T t) {
        if (obj == 0) {
            Log.w(TAG, "Error, field `" + this.key + "` was not found, defaulting to " + t);
            return t;
        }
        if (!getJsonType().isInstance(obj)) {
            Log.w(TAG, "Error, field `" + this.key + "` expected to be of type `" + getJsonType().getSimpleName() + "`, got `" + obj.getClass().getSimpleName() + "`, defaulting to " + t);
            return t;
        }
        T t2 = obj;
        if (getFieldType() != getJsonType()) {
            t2 = parse(obj);
        }
        if (t2 == null) {
            Log.w(TAG, "Error parsing JSON field `" + this.key + "` , defaulting to " + t);
            return t;
        }
        if (!validate(t2)) {
            Log.w(TAG, "Error validating JSON field `" + this.key + "` , defaulting to " + t);
            return t;
        }
        if (t2.getClass() == getFieldType()) {
            return t2;
        }
        Log.w(TAG, "Error: JSON field `" + this.key + "` expected to be of type `" + getFieldType().getSimpleName() + "`, defaulting to " + t);
        return t;
    }

    public void fromJSON(JSONObject jSONObject, ThemeSettingsUpdater themeSettingsUpdater) {
        this.mSetter.accept(themeSettingsUpdater, fallbackParse(jSONObject.opt(this.key), getDefaultValue()));
    }

    public void toJSON(ThemeSettings themeSettings, JSONObject jSONObject) throws JSONException {
        J jSerialize;
        T tApply = this.mGetter.apply(themeSettings);
        Preconditions.checkState(tApply.getClass() == getFieldType());
        if (validate(tApply)) {
            jSerialize = serialize(tApply);
        } else {
            T defaultValue = getDefaultValue();
            J jSerialize2 = serialize(defaultValue);
            Log.w(TAG, "Invalid value `" + tApply + "` for key `" + this.key + "`, defaulting to '" + defaultValue);
            jSerialize = jSerialize2;
        }
        try {
            jSONObject.put(this.key, jSerialize);
        } catch (JSONException e) {
            Log.d(TAG, "Error writing JSON primitive, skipping field " + this.key + ", " + e.getMessage());
        }
    }

    public T getDefaultValue() {
        return this.mGetter.apply(this.mDefaults);
    }
}
