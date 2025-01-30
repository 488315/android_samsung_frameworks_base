package androidx.appcompat.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import androidx.core.content.res.ResourcesCompat;
import java.io.InputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ResourcesWrapper extends Resources {
    public final Resources mResources;

    public ResourcesWrapper(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.mResources = resources;
    }

    @Override // android.content.res.Resources
    public final XmlResourceParser getAnimation(int i) {
        return this.mResources.getAnimation(i);
    }

    @Override // android.content.res.Resources
    public final boolean getBoolean(int i) {
        return this.mResources.getBoolean(i);
    }

    @Override // android.content.res.Resources
    public final int getColor(int i) {
        return this.mResources.getColor(i);
    }

    @Override // android.content.res.Resources
    public final ColorStateList getColorStateList(int i) {
        return this.mResources.getColorStateList(i);
    }

    @Override // android.content.res.Resources
    public final Configuration getConfiguration() {
        return this.mResources.getConfiguration();
    }

    @Override // android.content.res.Resources
    public final float getDimension(int i) {
        return this.mResources.getDimension(i);
    }

    @Override // android.content.res.Resources
    public final int getDimensionPixelOffset(int i) {
        return this.mResources.getDimensionPixelOffset(i);
    }

    @Override // android.content.res.Resources
    public final int getDimensionPixelSize(int i) {
        return this.mResources.getDimensionPixelSize(i);
    }

    @Override // android.content.res.Resources
    public final DisplayMetrics getDisplayMetrics() {
        return this.mResources.getDisplayMetrics();
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int i) {
        return this.mResources.getDrawable(i);
    }

    public final Drawable getDrawableCanonical(int i) {
        return super.getDrawable(i);
    }

    @Override // android.content.res.Resources
    public final Drawable getDrawableForDensity(int i, int i2) {
        Resources resources = this.mResources;
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        return resources.getDrawableForDensity(i, i2, null);
    }

    @Override // android.content.res.Resources
    public final float getFraction(int i, int i2, int i3) {
        return this.mResources.getFraction(i, i2, i3);
    }

    @Override // android.content.res.Resources
    public final int getIdentifier(String str, String str2, String str3) {
        return this.mResources.getIdentifier(str, str2, str3);
    }

    @Override // android.content.res.Resources
    public final int[] getIntArray(int i) {
        return this.mResources.getIntArray(i);
    }

    @Override // android.content.res.Resources
    public final int getInteger(int i) {
        return this.mResources.getInteger(i);
    }

    @Override // android.content.res.Resources
    public final XmlResourceParser getLayout(int i) {
        return this.mResources.getLayout(i);
    }

    @Override // android.content.res.Resources
    public final Movie getMovie(int i) {
        return this.mResources.getMovie(i);
    }

    @Override // android.content.res.Resources
    public final String getQuantityString(int i, int i2, Object... objArr) {
        return this.mResources.getQuantityString(i, i2, objArr);
    }

    @Override // android.content.res.Resources
    public final CharSequence getQuantityText(int i, int i2) {
        return this.mResources.getQuantityText(i, i2);
    }

    @Override // android.content.res.Resources
    public final String getResourceEntryName(int i) {
        return this.mResources.getResourceEntryName(i);
    }

    @Override // android.content.res.Resources
    public final String getResourceName(int i) {
        return this.mResources.getResourceName(i);
    }

    @Override // android.content.res.Resources
    public final String getResourcePackageName(int i) {
        return this.mResources.getResourcePackageName(i);
    }

    @Override // android.content.res.Resources
    public final String getResourceTypeName(int i) {
        return this.mResources.getResourceTypeName(i);
    }

    @Override // android.content.res.Resources
    public final String getString(int i) {
        return this.mResources.getString(i);
    }

    @Override // android.content.res.Resources
    public final String[] getStringArray(int i) {
        return this.mResources.getStringArray(i);
    }

    @Override // android.content.res.Resources
    public final CharSequence getText(int i) {
        return this.mResources.getText(i);
    }

    @Override // android.content.res.Resources
    public final CharSequence[] getTextArray(int i) {
        return this.mResources.getTextArray(i);
    }

    @Override // android.content.res.Resources
    public final void getValue(int i, TypedValue typedValue, boolean z) {
        this.mResources.getValue(i, typedValue, z);
    }

    @Override // android.content.res.Resources
    public final void getValueForDensity(int i, int i2, TypedValue typedValue, boolean z) {
        this.mResources.getValueForDensity(i, i2, typedValue, z);
    }

    @Override // android.content.res.Resources
    public final XmlResourceParser getXml(int i) {
        return this.mResources.getXml(i);
    }

    @Override // android.content.res.Resources
    public final TypedArray obtainAttributes(AttributeSet attributeSet, int[] iArr) {
        return this.mResources.obtainAttributes(attributeSet, iArr);
    }

    @Override // android.content.res.Resources
    public final TypedArray obtainTypedArray(int i) {
        return this.mResources.obtainTypedArray(i);
    }

    @Override // android.content.res.Resources
    public final InputStream openRawResource(int i) {
        return this.mResources.openRawResource(i);
    }

    @Override // android.content.res.Resources
    public final AssetFileDescriptor openRawResourceFd(int i) {
        return this.mResources.openRawResourceFd(i);
    }

    @Override // android.content.res.Resources
    public final void parseBundleExtra(String str, AttributeSet attributeSet, Bundle bundle) {
        this.mResources.parseBundleExtra(str, attributeSet, bundle);
    }

    @Override // android.content.res.Resources
    public final void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) {
        this.mResources.parseBundleExtras(xmlResourceParser, bundle);
    }

    @Override // android.content.res.Resources
    public final void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        super.updateConfiguration(configuration, displayMetrics);
        Resources resources = this.mResources;
        if (resources != null) {
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    @Override // android.content.res.Resources
    public final Drawable getDrawable(int i, Resources.Theme theme) {
        Resources resources = this.mResources;
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        return resources.getDrawable(i, theme);
    }

    @Override // android.content.res.Resources
    public final String getQuantityString(int i, int i2) {
        return this.mResources.getQuantityString(i, i2);
    }

    @Override // android.content.res.Resources
    public final String getString(int i, Object... objArr) {
        return this.mResources.getString(i, objArr);
    }

    @Override // android.content.res.Resources
    public final CharSequence getText(int i, CharSequence charSequence) {
        return this.mResources.getText(i, charSequence);
    }

    @Override // android.content.res.Resources
    public final void getValue(String str, TypedValue typedValue, boolean z) {
        this.mResources.getValue(str, typedValue, z);
    }

    @Override // android.content.res.Resources
    public final InputStream openRawResource(int i, TypedValue typedValue) {
        return this.mResources.openRawResource(i, typedValue);
    }

    @Override // android.content.res.Resources
    public final Drawable getDrawableForDensity(int i, int i2, Resources.Theme theme) {
        Resources resources = this.mResources;
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        return resources.getDrawableForDensity(i, i2, theme);
    }
}
