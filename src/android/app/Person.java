package android.app;

import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class Person implements Parcelable {
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() { // from class: android.app.Person.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Person[] newArray(int i) {
            return new Person[i];
        }
    };
    private Icon mIcon;
    private boolean mIsBot;
    private boolean mIsImportant;
    private String mKey;
    private CharSequence mName;
    private String mUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Person(Parcel parcel) {
        this.mName = parcel.readCharSequence();
        if (parcel.readInt() != 0) {
            this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        }
        this.mUri = parcel.readString();
        this.mKey = parcel.readString();
        this.mIsImportant = parcel.readBoolean();
        this.mIsBot = parcel.readBoolean();
    }

    private Person(Builder builder) {
        this.mName = builder.mName;
        this.mIcon = builder.mIcon;
        this.mUri = builder.mUri;
        this.mKey = builder.mKey;
        this.mIsBot = builder.mIsBot;
        this.mIsImportant = builder.mIsImportant;
    }

    public Builder toBuilder() {
        return new Builder();
    }

    public String getUri() {
        return this.mUri;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public String getKey() {
        return this.mKey;
    }

    public boolean isBot() {
        return this.mIsBot;
    }

    public boolean isImportant() {
        return this.mIsImportant;
    }

    public String resolveToLegacyUri() {
        String str = this.mUri;
        if (str != null) {
            return str;
        }
        if (this.mName != null) {
            return "name:" + ((Object) this.mName);
        }
        return "";
    }

    public Uri getIconUri() {
        Icon icon = this.mIcon;
        if (icon == null) {
            return null;
        }
        if (icon.getType() == 4 || this.mIcon.getType() == 6) {
            return this.mIcon.getUri();
        }
        return null;
    }

    public boolean equals(Object obj) {
        Icon icon;
        Icon icon2;
        if (obj instanceof Person) {
            Person person = (Person) obj;
            if (Objects.equals(this.mName, person.mName) && ((icon = this.mIcon) != null ? !((icon2 = person.mIcon) == null || !icon.sameAs(icon2)) : person.mIcon == null) && Objects.equals(this.mUri, person.mUri) && Objects.equals(this.mKey, person.mKey) && this.mIsBot == person.mIsBot && this.mIsImportant == person.mIsImportant) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mIcon, this.mUri, this.mKey, Boolean.valueOf(this.mIsBot), Boolean.valueOf(this.mIsImportant));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mName);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mUri);
        parcel.writeString(this.mKey);
        parcel.writeBoolean(this.mIsImportant);
        parcel.writeBoolean(this.mIsBot);
    }

    public void visitUris(Consumer<Uri> consumer) {
        consumer.accept(getIconUri());
        String str = this.mUri;
        if (str == null || str.isEmpty()) {
            return;
        }
        consumer.accept(Uri.parse(this.mUri));
    }

    public static class Builder {
        private Icon mIcon;
        private boolean mIsBot;
        private boolean mIsImportant;
        private String mKey;
        private CharSequence mName;
        private String mUri;

        public Builder() {
        }

        private Builder(Person person) {
            this.mName = person.mName;
            this.mIcon = person.mIcon;
            this.mUri = person.mUri;
            this.mKey = person.mKey;
            this.mIsBot = person.mIsBot;
            this.mIsImportant = person.mIsImportant;
        }

        public Builder setName(CharSequence charSequence) {
            this.mName = charSequence;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setUri(String str) {
            this.mUri = str;
            return this;
        }

        public Builder setKey(String str) {
            this.mKey = str;
            return this;
        }

        public Builder setImportant(boolean z) {
            this.mIsImportant = z;
            return this;
        }

        public Builder setBot(boolean z) {
            this.mIsBot = z;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
