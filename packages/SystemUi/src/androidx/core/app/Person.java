package androidx.core.app;

import android.app.Person;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Person {
    public final IconCompat mIcon;
    public final boolean mIsBot;
    public final boolean mIsImportant;
    public final String mKey;
    public final CharSequence mName;
    public final String mUri;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public IconCompat mIcon;
        public boolean mIsBot;
        public boolean mIsImportant;
        public String mKey;
        public CharSequence mName;
        public String mUri;

        public Builder(Person person) {
            this.mName = person.mName;
            this.mIcon = person.mIcon;
            this.mUri = person.mUri;
            this.mKey = person.mKey;
            this.mIsBot = person.mIsBot;
            this.mIsImportant = person.mIsImportant;
        }
    }

    public Person(Builder builder) {
        this.mName = builder.mName;
        this.mIcon = builder.mIcon;
        this.mUri = builder.mUri;
        this.mKey = builder.mKey;
        this.mIsBot = builder.mIsBot;
        this.mIsImportant = builder.mIsImportant;
    }

    public static Person fromAndroidPerson(android.app.Person person) {
        Builder builder = new Builder();
        builder.mName = person.getName();
        builder.mIcon = person.getIcon() != null ? IconCompat.createFromIcon(person.getIcon()) : null;
        builder.mUri = person.getUri();
        builder.mKey = person.getKey();
        builder.mIsBot = person.isBot();
        builder.mIsImportant = person.isImportant();
        return new Person(builder);
    }

    public static Person fromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("icon");
        Builder builder = new Builder();
        builder.mName = bundle.getCharSequence("name");
        builder.mIcon = bundle2 != null ? IconCompat.createFromBundle(bundle2) : null;
        builder.mUri = bundle.getString("uri");
        builder.mKey = bundle.getString("key");
        builder.mIsBot = bundle.getBoolean("isBot");
        builder.mIsImportant = bundle.getBoolean("isImportant");
        return new Person(builder);
    }

    public final android.app.Person toAndroidPerson() {
        Person.Builder name = new Person.Builder().setName(this.mName);
        IconCompat iconCompat = this.mIcon;
        return name.setIcon(iconCompat != null ? iconCompat.toIcon$1() : null).setUri(this.mUri).setKey(this.mKey).setBot(this.mIsBot).setImportant(this.mIsImportant).build();
    }
}
