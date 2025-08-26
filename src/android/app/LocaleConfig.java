package android.app;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class LocaleConfig implements Parcelable {
    public static final Parcelable.Creator<LocaleConfig> CREATOR = new Parcelable.Creator<LocaleConfig>() { // from class: android.app.LocaleConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocaleConfig createFromParcel(Parcel parcel) {
            return new LocaleConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocaleConfig[] newArray(int i) {
            return new LocaleConfig[i];
        }
    };
    public static final int STATUS_NOT_SPECIFIED = 1;
    public static final int STATUS_PARSING_FAILED = 2;
    public static final int STATUS_SUCCESS = 0;
    private static final String TAG = "LocaleConfig";
    public static final String TAG_LOCALE = "locale";
    public static final String TAG_LOCALE_CONFIG = "locale-config";
    private Locale mDefaultLocale;
    private LocaleList mLocales;
    private int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocaleConfig(Context context) {
        this(context, true);
    }

    public static LocaleConfig fromContextIgnoringOverride(Context context) {
        return new LocaleConfig(context, false);
    }

    private LocaleConfig(Context context, boolean z) {
        this.mStatus = 1;
        if (z) {
            LocaleManager localeManager = (LocaleManager) context.getSystemService(LocaleManager.class);
            if (localeManager == null) {
                Slog.w(TAG, "LocaleManager is null, cannot get the override LocaleConfig");
                this.mStatus = 1;
                return;
            }
            LocaleConfig overrideLocaleConfig = localeManager.getOverrideLocaleConfig();
            if (overrideLocaleConfig != null) {
                Slog.d(TAG, "Has the override LocaleConfig");
                this.mStatus = overrideLocaleConfig.getStatus();
                this.mLocales = overrideLocaleConfig.getSupportedLocales();
                return;
            }
        }
        Resources resources = context.getResources();
        int localeConfigRes = context.getApplicationInfo().getLocaleConfigRes();
        if (localeConfigRes == 0) {
            this.mStatus = 1;
            return;
        }
        try {
            try {
                XmlResourceParser xml = resources.getXml(localeConfigRes);
                try {
                    parseLocaleConfig(xml, resources);
                    if (xml != null) {
                        xml.close();
                    }
                } catch (Throwable th) {
                    if (xml != null) {
                        try {
                            xml.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Resources.NotFoundException unused) {
                Slog.w(TAG, "The resource file pointed to by the given resource ID isn't found.");
                this.mStatus = 1;
            }
        } catch (IOException | XmlPullParserException e) {
            Slog.w(TAG, "Failed to parse XML configuration from " + resources.getResourceEntryName(localeConfigRes), e);
            this.mStatus = 2;
        }
    }

    public LocaleConfig(LocaleList localeList) {
        this.mStatus = 0;
        this.mLocales = localeList;
    }

    private LocaleConfig(Parcel parcel) {
        this.mStatus = 1;
        this.mStatus = parcel.readInt();
        this.mLocales = (LocaleList) parcel.readTypedObject(LocaleList.CREATOR);
    }

    private void parseLocaleConfig(XmlResourceParser xmlResourceParser, Resources resources) throws XmlPullParserException, IOException {
        String string;
        TypedArray typedArrayObtainAttributes;
        XmlUtils.beginDocument(xmlResourceParser, TAG_LOCALE_CONFIG);
        int depth = xmlResourceParser.getDepth();
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParser);
        if (android.content.res.Flags.defaultLocale()) {
            typedArrayObtainAttributes = resources.obtainAttributes(attributeSetAsAttributeSet, R.styleable.LocaleConfig);
            try {
                string = typedArrayObtainAttributes.getString(0);
                if (typedArrayObtainAttributes != null) {
                    typedArrayObtainAttributes.close();
                }
            } finally {
            }
        } else {
            string = null;
        }
        HashSet hashSet = new HashSet();
        while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if ("locale".equals(xmlResourceParser.getName())) {
                typedArrayObtainAttributes = resources.obtainAttributes(attributeSetAsAttributeSet, R.styleable.LocaleConfig_Locale);
                try {
                    hashSet.add(typedArrayObtainAttributes.getString(0));
                    if (typedArrayObtainAttributes != null) {
                        typedArrayObtainAttributes.close();
                    }
                } finally {
                }
            } else {
                XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        this.mStatus = 0;
        this.mLocales = LocaleList.forLanguageTags(String.join(",", hashSet));
        if (string != null) {
            if (hashSet.contains(string)) {
                this.mDefaultLocale = Locale.forLanguageTag(string);
                return;
            }
            Slog.w(TAG, "Default locale specified that is not contained in the list: " + string);
            this.mStatus = 2;
        }
    }

    public LocaleList getSupportedLocales() {
        return this.mLocales;
    }

    public Locale getDefaultLocale() {
        return this.mDefaultLocale;
    }

    public int getStatus() {
        return this.mStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeTypedObject(this.mLocales, i);
    }

    public boolean isSameLocaleConfig(LocaleConfig localeConfig) {
        if (localeConfig == this) {
            return true;
        }
        if (localeConfig == null || this.mStatus != localeConfig.mStatus) {
            return false;
        }
        LocaleList localeList = localeConfig.mLocales;
        LocaleList localeList2 = this.mLocales;
        if (localeList2 == null && localeList == null) {
            return true;
        }
        if (localeList2 != null && localeList != null) {
            List listAsList = Arrays.asList(localeList2.toLanguageTags().split(","));
            List listAsList2 = Arrays.asList(localeList.toLanguageTags().split(","));
            Collections.sort(listAsList);
            Collections.sort(listAsList2);
            return listAsList.equals(listAsList2);
        }
        return false;
    }

    public boolean containsLocale(Locale locale) {
        if (this.mLocales == null) {
            return false;
        }
        for (int i = 0; i < this.mLocales.size(); i++) {
            if (LocaleList.matchesLanguageAndScript(this.mLocales.get(i), locale)) {
                return true;
            }
        }
        return false;
    }
}
