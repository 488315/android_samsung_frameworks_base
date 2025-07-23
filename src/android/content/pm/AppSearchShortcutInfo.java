package android.content.pm;

import android.app.Person;
import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;
import android.content.ComponentName;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.AppSearchShortcutInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/* loaded from: classes.dex */
public class AppSearchShortcutInfo extends GenericDocument {
    public static final String IS_DISABLED = "Dis";
    public static final String IS_DYNAMIC = "Dyn";
    public static final String IS_IMMUTABLE = "Im";
    public static final String IS_MANIFEST = "Man";
    public static final String KEY_ACTIVITY = "activity";
    public static final String KEY_FLAGS = "flags";
    public static final String KEY_ICON_RES_ID = "iconResId";
    public static final String KEY_ICON_RES_NAME = "iconResName";
    public static final String KEY_PERSON = "person";
    public static final String NOT_DISABLED = "nDis";
    public static final String NOT_DYNAMIC = "nDyn";
    public static final String NOT_IMMUTABLE = "nIm";
    public static final String NOT_MANIFEST = "nMan";
    public static final int SCHEMA_VERSION = 3;
    public static final long SHORTCUT_TTL = TimeUnit.DAYS.toMillis(90);
    public static final String SCHEMA_TYPE = "Shortcut";
    public static final String KEY_SHORT_LABEL = "shortLabel";
    public static final String KEY_LONG_LABEL = "longLabel";
    public static final String KEY_DISABLED_MESSAGE = "disabledMessage";
    public static final String KEY_CATEGORIES = "categories";
    public static final String KEY_INTENTS = "intents";
    public static final String KEY_INTENT_PERSISTABLE_EXTRAS = "intentPersistableExtras";
    public static final String KEY_LOCUS_ID = "locusId";
    public static final String KEY_EXTRAS = "extras";
    public static final String KEY_ICON_URI = "iconUri";
    public static final String KEY_DISABLED_REASON = "disabledReason";
    public static final String KEY_CAPABILITY = "capability";
    public static final String KEY_CAPABILITY_BINDINGS = "capabilityBindings";
    public static final AppSearchSchema SCHEMA = new AppSearchSchema.Builder(SCHEMA_TYPE).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("activity").setCardinality(2).setTokenizerType(1).setIndexingType(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_SHORT_LABEL).setCardinality(2).setTokenizerType(1).setIndexingType(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_LONG_LABEL).setCardinality(2).setTokenizerType(1).setIndexingType(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_DISABLED_MESSAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_CATEGORIES).setCardinality(1).setTokenizerType(1).setIndexingType(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_INTENTS).setCardinality(1).setTokenizerType(0).setIndexingType(0).build()).addProperty(new AppSearchSchema.BytesPropertyConfig.Builder(KEY_INTENT_PERSISTABLE_EXTRAS).setCardinality(1).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("person", AppSearchShortcutPerson.SCHEMA_TYPE).setCardinality(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_LOCUS_ID).setCardinality(2).setTokenizerType(1).setIndexingType(1).build()).addProperty(new AppSearchSchema.BytesPropertyConfig.Builder(KEY_EXTRAS).setCardinality(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("flags").setCardinality(1).setTokenizerType(1).setIndexingType(1).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("iconResId").setCardinality(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("iconResName").setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_ICON_URI).setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_DISABLED_REASON).setCardinality(3).setTokenizerType(1).setIndexingType(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_CAPABILITY).setCardinality(1).setTokenizerType(1).setIndexingType(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(KEY_CAPABILITY_BINDINGS).setCardinality(1).setTokenizerType(1).setIndexingType(2).build()).build();

    public AppSearchShortcutInfo(GenericDocument genericDocument) {
        super(genericDocument);
    }

    public static AppSearchShortcutInfo instance(ShortcutInfo shortcutInfo) {
        Objects.requireNonNull(shortcutInfo);
        return new Builder(shortcutInfo.getPackage(), shortcutInfo.getId()).setActivity(shortcutInfo.getActivity()).setShortLabel(shortcutInfo.getShortLabel()).setLongLabel(shortcutInfo.getLongLabel()).setDisabledMessage(shortcutInfo.getDisabledMessage()).setCategories(shortcutInfo.getCategories()).setIntents(shortcutInfo.getIntents()).setExtras(shortcutInfo.getExtras()).setCreationTimestampMillis(shortcutInfo.getLastChangedTimestamp()).setFlags(shortcutInfo.getFlags()).setIconResId(shortcutInfo.getIconResourceId()).setIconResName(shortcutInfo.getIconResName()).setIconUri(shortcutInfo.getIconUri()).setDisabledReason(shortcutInfo.getDisabledReason()).setPersons(shortcutInfo.getPersons()).setLocusId(shortcutInfo.getLocusId()).setCapabilityBindings(shortcutInfo.getCapabilityBindingsInternal()).setTtlMillis(SHORTCUT_TTL).build();
    }

    public ShortcutInfo toShortcutInfo(int i) {
        Intent[] intentArr;
        Bundle bundle;
        String namespace = getNamespace();
        String propertyString = getPropertyString("activity");
        ComponentName unflattenFromString = propertyString == null ? null : ComponentName.unflattenFromString(propertyString);
        String propertyString2 = getPropertyString(KEY_SHORT_LABEL);
        String propertyString3 = getPropertyString(KEY_LONG_LABEL);
        String propertyString4 = getPropertyString(KEY_DISABLED_MESSAGE);
        String[] propertyStringArray = getPropertyStringArray(KEY_CATEGORIES);
        ArraySet arraySet = propertyStringArray == null ? null : new ArraySet(Arrays.asList(propertyStringArray));
        String[] propertyStringArray2 = getPropertyStringArray(KEY_INTENTS);
        if (propertyStringArray2 == null) {
            intentArr = new Intent[0];
        } else {
            intentArr = (Intent[]) Arrays.stream(propertyStringArray2).map(new Function() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AppSearchShortcutInfo.lambda$toShortcutInfo$0((String) obj);
                }
            }).toArray(new IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    return AppSearchShortcutInfo.lambda$toShortcutInfo$1(i2);
                }
            });
        }
        byte[][] propertyBytesArray = getPropertyBytesArray(KEY_INTENT_PERSISTABLE_EXTRAS);
        Bundle[] bundleArr = propertyBytesArray == null ? null : (Bundle[]) Arrays.stream(propertyBytesArray).map(new Function() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Bundle transformToBundle;
                transformToBundle = AppSearchShortcutInfo.this.transformToBundle((byte[]) obj);
                return transformToBundle;
            }
        }).toArray(new IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return AppSearchShortcutInfo.lambda$toShortcutInfo$2(i2);
            }
        });
        if (intentArr != null) {
            for (int i2 = 0; i2 < intentArr.length; i2++) {
                Intent intent = intentArr[i2];
                if (intent != null && bundleArr != null && bundleArr.length > i2 && (bundle = bundleArr[i2]) != null && bundle.size() != 0) {
                    intent.replaceExtras(bundleArr[i2]);
                }
            }
        }
        Person[] parsePerson = parsePerson(getPropertyDocumentArray("person"));
        String propertyString5 = getPropertyString(KEY_LOCUS_ID);
        return new ShortcutInfo(i, getId(), namespace, unflattenFromString, null, propertyString2, 0, null, propertyString3, 0, null, propertyString4, 0, null, arraySet, intentArr, Integer.MAX_VALUE, transformToPersistableBundle(getPropertyBytes(KEY_EXTRAS)), getCreationTimestampMillis(), parseFlags(getPropertyStringArray("flags")), (int) getPropertyLong("iconResId"), getPropertyString("iconResName"), null, getPropertyString(KEY_ICON_URI), TextUtils.isEmpty(getPropertyString(KEY_DISABLED_REASON)) ? 0 : Integer.parseInt(getPropertyString(KEY_DISABLED_REASON)), parsePerson, propertyString5 != null ? new LocusId(propertyString5) : null, null, parseCapabilityBindings(getPropertyStringArray(KEY_CAPABILITY_BINDINGS)));
    }

    static /* synthetic */ Intent lambda$toShortcutInfo$0(String str) {
        if (TextUtils.isEmpty(str)) {
            return new Intent("android.intent.action.VIEW");
        }
        try {
            return Intent.parseUri(str, 0);
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    static /* synthetic */ Intent[] lambda$toShortcutInfo$1(int i) {
        return new Intent[i];
    }

    static /* synthetic */ Bundle[] lambda$toShortcutInfo$2(int i) {
        return new Bundle[i];
    }

    public static List<GenericDocument> toGenericDocuments(Collection<ShortcutInfo> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<ShortcutInfo> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(instance(it.next()));
        }
        return arrayList;
    }

    public static class Builder extends GenericDocument.Builder<Builder> {
        private final List<String> mFlags;

        public Builder(String str, String str2) {
            super(str, str2, AppSearchShortcutInfo.SCHEMA_TYPE);
            this.mFlags = new ArrayList(1);
        }

        public Builder setLocusId(LocusId locusId) {
            if (locusId != null) {
                setPropertyString(AppSearchShortcutInfo.KEY_LOCUS_ID, locusId.getId());
            }
            return this;
        }

        public Builder setActivity(ComponentName componentName) {
            if (componentName != null) {
                setPropertyString("activity", componentName.flattenToShortString());
            }
            return this;
        }

        public Builder setShortLabel(CharSequence charSequence) {
            if (!TextUtils.isEmpty(charSequence)) {
                setPropertyString(AppSearchShortcutInfo.KEY_SHORT_LABEL, Preconditions.checkStringNotEmpty(charSequence, "shortLabel cannot be empty").toString());
            }
            return this;
        }

        public Builder setLongLabel(CharSequence charSequence) {
            if (!TextUtils.isEmpty(charSequence)) {
                setPropertyString(AppSearchShortcutInfo.KEY_LONG_LABEL, Preconditions.checkStringNotEmpty(charSequence, "longLabel cannot be empty").toString());
            }
            return this;
        }

        public Builder setDisabledMessage(CharSequence charSequence) {
            if (!TextUtils.isEmpty(charSequence)) {
                setPropertyString(AppSearchShortcutInfo.KEY_DISABLED_MESSAGE, Preconditions.checkStringNotEmpty(charSequence, "disabledMessage cannot be empty").toString());
            }
            return this;
        }

        public Builder setCategories(Set<String> set) {
            if (set != null && !set.isEmpty()) {
                setPropertyString(AppSearchShortcutInfo.KEY_CATEGORIES, (String[]) set.stream().toArray(new IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda2
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return AppSearchShortcutInfo.Builder.lambda$setCategories$0(i);
                    }
                }));
            }
            return this;
        }

        static /* synthetic */ String[] lambda$setCategories$0(int i) {
            return new String[i];
        }

        public Builder setIntent(Intent intent) {
            return intent == null ? this : setIntents(new Intent[]{intent});
        }

        public Builder setIntents(Intent[] intentArr) {
            if (intentArr != null && intentArr.length != 0) {
                for (Intent intent : intentArr) {
                    Objects.requireNonNull(intent, "intents cannot contain null");
                    Objects.requireNonNull(intent.getAction(), "intent's action must be set");
                }
                byte[][] bArr = new byte[intentArr.length][];
                for (int i = 0; i < intentArr.length; i++) {
                    Bundle extras = intentArr[i].getExtras();
                    bArr[i] = extras == null ? new byte[0] : AppSearchShortcutInfo.transformToByteArray(new PersistableBundle(extras));
                }
                setPropertyString(AppSearchShortcutInfo.KEY_INTENTS, (String[]) Arrays.stream(intentArr).map(new Function() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String uri;
                        uri = ((Intent) obj).toUri(0);
                        return uri;
                    }
                }).toArray(new IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i2) {
                        return AppSearchShortcutInfo.Builder.lambda$setIntents$2(i2);
                    }
                }));
                setPropertyBytes(AppSearchShortcutInfo.KEY_INTENT_PERSISTABLE_EXTRAS, bArr);
            }
            return this;
        }

        static /* synthetic */ String[] lambda$setIntents$2(int i) {
            return new String[i];
        }

        public Builder setPerson(Person person) {
            return person == null ? this : setPersons(new Person[]{person});
        }

        public Builder setPersons(Person[] personArr) {
            if (personArr != null && personArr.length != 0) {
                GenericDocument[] genericDocumentArr = new GenericDocument[personArr.length];
                for (int i = 0; i < personArr.length; i++) {
                    Person person = personArr[i];
                    if (person != null) {
                        genericDocumentArr[i] = AppSearchShortcutPerson.instance(person);
                    }
                }
                setPropertyDocument("person", genericDocumentArr);
            }
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            if (persistableBundle != null) {
                setPropertyBytes(AppSearchShortcutInfo.KEY_EXTRAS, AppSearchShortcutInfo.transformToByteArray(persistableBundle));
            }
            return this;
        }

        public Builder setFlags(int i) {
            String[] flattenFlags = AppSearchShortcutInfo.flattenFlags(i);
            if (flattenFlags != null && flattenFlags.length > 0) {
                this.mFlags.addAll(Arrays.asList(flattenFlags));
            }
            return this;
        }

        public Builder setIconResId(int i) {
            setPropertyLong("iconResId", i);
            return this;
        }

        public Builder setIconResName(String str) {
            if (!TextUtils.isEmpty(str)) {
                setPropertyString("iconResName", str);
            }
            return this;
        }

        public Builder setIconUri(String str) {
            if (!TextUtils.isEmpty(str)) {
                setPropertyString(AppSearchShortcutInfo.KEY_ICON_URI, str);
            }
            return this;
        }

        public Builder setDisabledReason(int i) {
            setPropertyString(AppSearchShortcutInfo.KEY_DISABLED_REASON, String.valueOf(i));
            return this;
        }

        public Builder setCapabilityBindings(Map<String, Map<String, List<String>>> map) {
            if (map != null && !map.isEmpty()) {
                Set<String> keySet = map.keySet();
                final ArraySet arraySet = new ArraySet(1);
                for (final String str : keySet) {
                    Map<String, List<String>> map2 = map.get(str);
                    for (final String str2 : map2.keySet()) {
                        map2.get(str2).stream().map(new Function() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda3
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return AppSearchShortcutInfo.Builder.lambda$setCapabilityBindings$3(str, str2, (String) obj);
                            }
                        }).forEach(new Consumer() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                arraySet.add((String) obj);
                            }
                        });
                    }
                }
                setPropertyString(AppSearchShortcutInfo.KEY_CAPABILITY, (String[]) keySet.toArray(new String[0]));
                setPropertyString(AppSearchShortcutInfo.KEY_CAPABILITY_BINDINGS, (String[]) arraySet.toArray(new String[0]));
            }
            return this;
        }

        static /* synthetic */ String lambda$setCapabilityBindings$3(String str, String str2, String str3) {
            return str + "/" + str2 + "/" + str3;
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public AppSearchShortcutInfo build() {
            setPropertyString("flags", (String[]) this.mFlags.toArray(new String[0]));
            return new AppSearchShortcutInfo(super.build());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] transformToByteArray(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                new PersistableBundle(persistableBundle).writeToStream(byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle transformToBundle(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        Objects.requireNonNull(bArr);
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                Bundle bundle = new Bundle();
                bundle.putAll(PersistableBundle.readFromStream(byteArrayInputStream));
                byteArrayInputStream.close();
                return bundle;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }

    private PersistableBundle transformToPersistableBundle(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                PersistableBundle readFromStream = PersistableBundle.readFromStream(byteArrayInputStream);
                byteArrayInputStream.close();
                return readFromStream;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] flattenFlags(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 31; i2++) {
            String flagToString = flagToString(i, 1 << i2);
            if (flagToString != null) {
                arrayList.add(flagToString);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private static String flagToString(int i, int i2) {
        if (i2 == 1) {
            return (i & i2) != 0 ? IS_DYNAMIC : NOT_DYNAMIC;
        }
        if (i2 == 32) {
            return (i & i2) != 0 ? IS_MANIFEST : NOT_MANIFEST;
        }
        if (i2 == 64) {
            return (i & i2) != 0 ? IS_DISABLED : NOT_DISABLED;
        }
        if (i2 != 256) {
            return null;
        }
        return (i & i2) != 0 ? IS_IMMUTABLE : NOT_IMMUTABLE;
    }

    private static int parseFlags(String[] strArr) {
        if (strArr == null) {
            return 0;
        }
        int i = 0;
        for (String str : strArr) {
            i |= parseFlag(str);
        }
        return i;
    }

    private static int parseFlag(String str) {
        str.hashCode();
        switch (str) {
            case "Im":
                return 256;
            case "Dis":
                return 64;
            case "Dyn":
                return 1;
            case "Man":
                return 32;
            default:
                return 0;
        }
    }

    private static Person[] parsePerson(GenericDocument[] genericDocumentArr) {
        if (genericDocumentArr == null) {
            return new Person[0];
        }
        Person[] personArr = new Person[genericDocumentArr.length];
        for (int i = 0; i < genericDocumentArr.length; i++) {
            GenericDocument genericDocument = genericDocumentArr[i];
            if (genericDocument != null) {
                personArr[i] = new AppSearchShortcutPerson(genericDocument).toPerson();
            }
        }
        return personArr;
    }

    private static Map<String, Map<String, List<String>>> parseCapabilityBindings(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        final ArrayMap arrayMap = new ArrayMap(1);
        Arrays.stream(strArr).forEach(new Consumer() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AppSearchShortcutInfo.lambda$parseCapabilityBindings$3(arrayMap, (String) obj);
            }
        });
        return arrayMap;
    }

    static /* synthetic */ void lambda$parseCapabilityBindings$3(Map map, String str) {
        int indexOf;
        if (TextUtils.isEmpty(str) || (indexOf = str.indexOf("/")) == -1 || indexOf == str.length() - 1) {
            return;
        }
        String substring = str.substring(0, indexOf);
        int i = indexOf + 1;
        int indexOf2 = str.indexOf("/", i);
        if (indexOf2 == -1 || indexOf2 == str.length() - 1) {
            return;
        }
        String substring2 = str.substring(i, indexOf2);
        String substring3 = str.substring(indexOf2 + 1);
        if (!map.containsKey(substring)) {
            map.put(substring, new ArrayMap(1));
        }
        Map map2 = (Map) map.get(substring);
        if (!map2.containsKey(substring2)) {
            map2.put(substring2, new ArrayList(1));
        }
        ((List) map2.get(substring2)).add(substring3);
    }
}
