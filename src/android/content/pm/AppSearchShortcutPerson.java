package android.content.pm;

import android.app.Person;
import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;
import android.graphics.drawable.Icon;
import android.net.UriCodec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes.dex */
public class AppSearchShortcutPerson extends GenericDocument {
    private static final String KEY_ICON = "icon";
    private static final String KEY_KEY = "key";
    private static final String KEY_NAME = "name";
    public static final String SCHEMA_TYPE = "ShortcutPerson";
    private static final String KEY_IS_BOT = "isBot";
    private static final String KEY_IS_IMPORTANT = "isImportant";
    public static final AppSearchSchema SCHEMA = new AppSearchSchema.Builder(SCHEMA_TYPE).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("key").setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder(KEY_IS_BOT).setCardinality(3).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder(KEY_IS_IMPORTANT).setCardinality(3).build()).addProperty(new AppSearchSchema.BytesPropertyConfig.Builder("icon").setCardinality(2).build()).build();

    public AppSearchShortcutPerson(GenericDocument genericDocument) {
        super(genericDocument);
    }

    public static AppSearchShortcutPerson instance(Person person) {
        String string;
        Objects.requireNonNull(person);
        if (person.getUri() != null) {
            string = person.getUri();
        } else {
            string = UUID.randomUUID().toString();
        }
        return new Builder(string).setName(person.getName()).setKey(person.getKey()).setIsBot(person.isBot()).setIsImportant(person.isImportant()).setIcon(transformToByteArray(person.getIcon())).build();
    }

    public Person toPerson() {
        String strDecode;
        try {
            strDecode = UriCodec.decode(getId(), false, StandardCharsets.UTF_8, true);
        } catch (IllegalArgumentException unused) {
            strDecode = null;
        }
        return new Person.Builder().setName(getPropertyString("name")).setUri(strDecode).setKey(getPropertyString("key")).setBot(getPropertyBoolean(KEY_IS_BOT)).setImportant(getPropertyBoolean(KEY_IS_IMPORTANT)).setIcon(transformToIcon(getPropertyBytes("icon"))).build();
    }

    public static class Builder extends GenericDocument.Builder<Builder> {
        public Builder(String str) {
            super("", str, AppSearchShortcutPerson.SCHEMA_TYPE);
        }

        public Builder setName(CharSequence charSequence) {
            if (charSequence != null) {
                setPropertyString("name", charSequence.toString());
            }
            return this;
        }

        public Builder setKey(String str) {
            if (str != null) {
                setPropertyString("key", str);
            }
            return this;
        }

        public Builder setIsBot(boolean z) {
            setPropertyBoolean(AppSearchShortcutPerson.KEY_IS_BOT, z);
            return this;
        }

        public Builder setIsImportant(boolean z) {
            setPropertyBoolean(AppSearchShortcutPerson.KEY_IS_IMPORTANT, z);
            return this;
        }

        public Builder setIcon(byte[] bArr) {
            if (bArr != null) {
                setPropertyBytes("icon", bArr);
            }
            return this;
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public AppSearchShortcutPerson build() {
            return new AppSearchShortcutPerson(super.build());
        }
    }

    private static byte[] transformToByteArray(Icon icon) throws IOException {
        if (icon == null) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                icon.writeToStream(byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }

    private Icon transformToIcon(byte[] bArr) throws IOException {
        if (bArr == null) {
            return null;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                Icon iconCreateFromStream = Icon.createFromStream(byteArrayInputStream);
                byteArrayInputStream.close();
                return iconCreateFromStream;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }
}
