package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.TypefaceCompat;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class TintTypedArray {
    private final Context mContext;
    private TypedValue mTypedValue;
    private final TypedArray mWrapped;

    private TintTypedArray(Context context, TypedArray typedArray) {
        this.mContext = context;
        this.mWrapped = typedArray;
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    public final boolean getBoolean(int i, boolean z) {
        return this.mWrapped.getBoolean(i, z);
    }

    public final int getColor() {
        return this.mWrapped.getColor(0, 0);
    }

    public final ColorStateList getColorStateList(int i) {
        int resourceId;
        ColorStateList colorStateList;
        TypedArray typedArray = this.mWrapped;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0 || (colorStateList = AppCompatResources.getColorStateList(this.mContext, resourceId)) == null) ? typedArray.getColorStateList(i) : colorStateList;
    }

    public final int getDimensionPixelOffset(int i, int i2) {
        return this.mWrapped.getDimensionPixelOffset(i, i2);
    }

    public final int getDimensionPixelSize(int i, int i2) {
        return this.mWrapped.getDimensionPixelSize(i, i2);
    }

    public final Drawable getDrawable(int i) {
        int resourceId;
        TypedArray typedArray = this.mWrapped;
        return (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) ? typedArray.getDrawable(i) : AppCompatResources.getDrawable(this.mContext, resourceId);
    }

    public final Drawable getDrawableIfKnown(int i) {
        int resourceId;
        TypedArray typedArray = this.mWrapped;
        if (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) {
            return null;
        }
        return AppCompatDrawableManager.get().getDrawable(resourceId, this.mContext);
    }

    public final float getFloat() {
        return this.mWrapped.getFloat(4, -1.0f);
    }

    public final Typeface getFont(int i, int i2, ResourcesCompat.FontCallback fontCallback) {
        int resourceId = this.mWrapped.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        int i3 = ResourcesCompat.$r8$clinit;
        Context context = this.mContext;
        if (context.isRestricted()) {
            return null;
        }
        Resources resources = context.getResources();
        resources.getValue(resourceId, typedValue, true);
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(resourceId) + "\" (" + Integer.toHexString(resourceId) + ") is not a Font: " + typedValue);
        }
        String charSequence2 = charSequence.toString();
        if (!charSequence2.startsWith("res/")) {
            fontCallback.callbackFailAsync();
            return null;
        }
        Typeface findFromCache = TypefaceCompat.findFromCache(resources, resourceId, charSequence2, typedValue.assetCookie, i2);
        if (findFromCache != null) {
            fontCallback.callbackSuccessAsync(findFromCache);
        } else {
            try {
                if (charSequence2.toLowerCase().endsWith(".xml")) {
                    FontResourcesParserCompat.FamilyResourceEntry parse = FontResourcesParserCompat.parse(resources.getXml(resourceId), resources);
                    if (parse != null) {
                        return TypefaceCompat.createFromResourcesFamilyXml(context, parse, resources, resourceId, charSequence2, typedValue.assetCookie, i2, fontCallback);
                    }
                    Log.e("ResourcesCompat", "Failed to find font-family tag");
                    fontCallback.callbackFailAsync();
                    return null;
                }
                findFromCache = TypefaceCompat.createFromResourcesFontFile(resources, resourceId, charSequence2, typedValue.assetCookie, i2);
                if (findFromCache != null) {
                    fontCallback.callbackSuccessAsync(findFromCache);
                } else {
                    fontCallback.callbackFailAsync();
                }
            } catch (IOException e) {
                Log.e("ResourcesCompat", "Failed to read xml resource ".concat(charSequence2), e);
                fontCallback.callbackFailAsync();
                return null;
            } catch (XmlPullParserException e2) {
                Log.e("ResourcesCompat", "Failed to parse xml resource ".concat(charSequence2), e2);
                fontCallback.callbackFailAsync();
                return null;
            }
        }
        return findFromCache;
    }

    public final int getInt(int i, int i2) {
        return this.mWrapped.getInt(i, i2);
    }

    public final int getInteger(int i, int i2) {
        return this.mWrapped.getInteger(i, i2);
    }

    public final int getLayoutDimension(int i, int i2) {
        return this.mWrapped.getLayoutDimension(i, i2);
    }

    public final int getResourceId(int i, int i2) {
        return this.mWrapped.getResourceId(i, i2);
    }

    public final String getString(int i) {
        return this.mWrapped.getString(i);
    }

    public final CharSequence getText(int i) {
        return this.mWrapped.getText(i);
    }

    public final CharSequence[] getTextArray() {
        return this.mWrapped.getTextArray(0);
    }

    public final TypedArray getWrappedTypeArray() {
        return this.mWrapped;
    }

    public final boolean hasValue(int i) {
        return this.mWrapped.hasValue(i);
    }

    public final void recycle() {
        this.mWrapped.recycle();
    }

    public static TintTypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2) {
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }

    public static TintTypedArray obtainStyledAttributes(Context context, int i, int[] iArr) {
        return new TintTypedArray(context, context.obtainStyledAttributes(i, iArr));
    }
}
