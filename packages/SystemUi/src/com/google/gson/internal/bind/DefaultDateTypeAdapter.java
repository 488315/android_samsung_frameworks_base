package com.google.gson.internal.bind;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.JavaVersion;
import com.google.gson.internal.PreJava9DateFormatProvider;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DefaultDateTypeAdapter<T extends Date> extends TypeAdapter {
    public final List dateFormats;
    public final DateType dateType;

    @Override // com.google.gson.TypeAdapter
    /* renamed from: read */
    public final Object mo2768read(JsonReader jsonReader) {
        Date parse;
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        String nextString = jsonReader.nextString();
        synchronized (this.dateFormats) {
            Iterator it = ((ArrayList) this.dateFormats).iterator();
            while (true) {
                if (!it.hasNext()) {
                    try {
                        parse = ISO8601Utils.parse(nextString, new ParsePosition(0));
                        break;
                    } catch (ParseException e) {
                        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("Failed parsing '", nextString, "' as Date; at path ");
                        m4m.append(jsonReader.getPreviousPath());
                        throw new JsonSyntaxException(m4m.toString(), e);
                    }
                }
                try {
                    parse = ((DateFormat) it.next()).parse(nextString);
                    break;
                } catch (ParseException unused) {
                }
            }
        }
        return this.dateType.deserialize(parse);
    }

    public final String toString() {
        DateFormat dateFormat = (DateFormat) this.dateFormats.get(0);
        if (dateFormat instanceof SimpleDateFormat) {
            return "DefaultDateTypeAdapter(" + ((SimpleDateFormat) dateFormat).toPattern() + ')';
        }
        return "DefaultDateTypeAdapter(" + dateFormat.getClass().getSimpleName() + ')';
    }

    @Override // com.google.gson.TypeAdapter
    public final void write(JsonWriter jsonWriter, Object obj) {
        String format;
        Date date = (Date) obj;
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        DateFormat dateFormat = (DateFormat) ((ArrayList) this.dateFormats).get(0);
        synchronized (this.dateFormats) {
            format = dateFormat.format(date);
        }
        jsonWriter.value(format);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DateType {
        public static final C44711 DATE = new DateType(Date.class) { // from class: com.google.gson.internal.bind.DefaultDateTypeAdapter.DateType.1
            @Override // com.google.gson.internal.bind.DefaultDateTypeAdapter.DateType
            public final Date deserialize(Date date) {
                return date;
            }
        };
        public final Class dateClass;

        public DateType(Class<Date> cls) {
            this.dateClass = cls;
        }

        public final TypeAdapterFactory createAdapterFactory(String str) {
            DefaultDateTypeAdapter defaultDateTypeAdapter = new DefaultDateTypeAdapter(this, str);
            TypeAdapterFactory typeAdapterFactory = TypeAdapters.CLASS_FACTORY;
            return new TypeAdapters.C450431(this.dateClass, defaultDateTypeAdapter);
        }

        public abstract Date deserialize(Date date);

        public final TypeAdapterFactory createAdapterFactory(int i, int i2) {
            DefaultDateTypeAdapter defaultDateTypeAdapter = new DefaultDateTypeAdapter(this, i, i2);
            TypeAdapterFactory typeAdapterFactory = TypeAdapters.CLASS_FACTORY;
            return new TypeAdapters.C450431(this.dateClass, defaultDateTypeAdapter);
        }
    }

    private DefaultDateTypeAdapter(DateType dateType, String str) {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        dateType.getClass();
        this.dateType = dateType;
        Locale locale = Locale.US;
        arrayList.add(new SimpleDateFormat(str, locale));
        if (Locale.getDefault().equals(locale)) {
            return;
        }
        arrayList.add(new SimpleDateFormat(str));
    }

    private DefaultDateTypeAdapter(DateType dateType, int i) {
        String str;
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        dateType.getClass();
        this.dateType = dateType;
        Locale locale = Locale.US;
        arrayList.add(DateFormat.getDateInstance(i, locale));
        if (!Locale.getDefault().equals(locale)) {
            arrayList.add(DateFormat.getDateInstance(i));
        }
        if (JavaVersion.majorJavaVersion >= 9) {
            if (i == 0) {
                str = "EEEE, MMMM d, y";
            } else if (i == 1) {
                str = "MMMM d, y";
            } else if (i == 2) {
                str = "MMM d, y";
            } else {
                if (i != 3) {
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown DateFormat style: ", i));
                }
                str = "M/d/yy";
            }
            arrayList.add(new SimpleDateFormat(str, locale));
        }
    }

    private DefaultDateTypeAdapter(DateType dateType, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        this.dateFormats = arrayList;
        dateType.getClass();
        this.dateType = dateType;
        Locale locale = Locale.US;
        arrayList.add(DateFormat.getDateTimeInstance(i, i2, locale));
        if (!Locale.getDefault().equals(locale)) {
            arrayList.add(DateFormat.getDateTimeInstance(i, i2));
        }
        if (JavaVersion.majorJavaVersion >= 9) {
            arrayList.add(PreJava9DateFormatProvider.getUSDateTimeFormat(i, i2));
        }
    }
}
