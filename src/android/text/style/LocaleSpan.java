package android.text.style;

import android.graphics.Paint;
import android.os.LocaleList;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import com.android.internal.util.Preconditions;
import java.util.Locale;

/* loaded from: classes4.dex */
public class LocaleSpan extends MetricAffectingSpan implements ParcelableSpan {
    private final LocaleList mLocales;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 23;
    }

    public LocaleSpan(Locale locale) {
        this.mLocales = locale == null ? LocaleList.getEmptyLocaleList() : new LocaleList(locale);
    }

    public LocaleSpan(LocaleList localeList) {
        Preconditions.checkNotNull(localeList, "locales cannot be null");
        this.mLocales = localeList;
    }

    public LocaleSpan(Parcel parcel) {
        this.mLocales = LocaleList.CREATOR.createFromParcel(parcel);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        this.mLocales.writeToParcel(parcel, i);
    }

    public Locale getLocale() {
        return this.mLocales.get(0);
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        apply(textPaint, this.mLocales);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        apply(textPaint, this.mLocales);
    }

    private static void apply(Paint paint, LocaleList localeList) {
        paint.setTextLocales(localeList);
    }

    public String toString() {
        return "LocaleSpan{locales=" + getLocales() + '}';
    }
}
