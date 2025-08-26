package androidx.core.app;

import android.app.Person;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;
import java.util.Objects;

/* loaded from: classes.dex */
public class Person {
    public final IconCompat mIcon;
    public final boolean mIsBot;
    public final boolean mIsImportant;
    public final String mKey;
    public final CharSequence mName;
    public final String mUri;

    public class Builder {
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
        IconCompat iconCompatCreateFromIconInner;
        Builder builder = new Builder();
        builder.mName = person.getName();
        if (person.getIcon() != null) {
            Icon icon = person.getIcon();
            PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
            iconCompatCreateFromIconInner = IconCompat.Api23Impl.createFromIconInner(icon);
        } else {
            iconCompatCreateFromIconInner = null;
        }
        builder.mIcon = iconCompatCreateFromIconInner;
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

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Person)) {
            return false;
        }
        Person person = (Person) obj;
        String str = this.mKey;
        String str2 = person.mKey;
        return (str == null && str2 == null) ? Objects.equals(Objects.toString(this.mName), Objects.toString(person.mName)) && Objects.equals(this.mUri, person.mUri) && Boolean.valueOf(this.mIsBot).equals(Boolean.valueOf(person.mIsBot)) && Boolean.valueOf(this.mIsImportant).equals(Boolean.valueOf(person.mIsImportant)) : Objects.equals(str, str2);
    }

    public final int hashCode() {
        String str = this.mKey;
        if (str != null) {
            return str.hashCode();
        }
        return Objects.hash(this.mName, this.mUri, Boolean.valueOf(this.mIsBot), Boolean.valueOf(this.mIsImportant));
    }

    public final android.app.Person toAndroidPerson() {
        Person.Builder name = new Person.Builder().setName(this.mName);
        IconCompat iconCompat = this.mIcon;
        return name.setIcon(iconCompat != null ? iconCompat.toIcon$1() : null).setUri(this.mUri).setKey(this.mKey).setBot(this.mIsBot).setImportant(this.mIsImportant).build();
    }
}
