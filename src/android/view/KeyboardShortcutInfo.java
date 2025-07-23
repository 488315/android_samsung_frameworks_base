package android.view;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes4.dex */
public final class KeyboardShortcutInfo implements Parcelable {
    public static final Parcelable.Creator<KeyboardShortcutInfo> CREATOR = new Parcelable.Creator<KeyboardShortcutInfo>() { // from class: android.view.KeyboardShortcutInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardShortcutInfo createFromParcel(Parcel parcel) {
            return new KeyboardShortcutInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardShortcutInfo[] newArray(int i) {
            return new KeyboardShortcutInfo[i];
        }
    };
    private final char mBaseCharacter;
    private Icon mIcon;
    private final int mKeycode;
    private final CharSequence mLabel;
    private final int mModifiers;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeyboardShortcutInfo(CharSequence charSequence, Icon icon, int i, int i2) {
        this.mLabel = charSequence;
        this.mIcon = icon;
        boolean z = false;
        this.mBaseCharacter = (char) 0;
        if (i >= 0 && i <= KeyEvent.getMaxKeyCode()) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.mKeycode = i;
        this.mModifiers = i2;
    }

    public KeyboardShortcutInfo(CharSequence charSequence, int i, int i2) {
        this(charSequence, (Icon) null, i, i2);
    }

    public KeyboardShortcutInfo(CharSequence charSequence, char c, int i) {
        this(charSequence, (Icon) null, c, i);
    }

    public KeyboardShortcutInfo(CharSequence charSequence, Icon icon, char c, int i) {
        this.mLabel = charSequence;
        Preconditions.checkArgument(c != 0);
        this.mBaseCharacter = c;
        this.mKeycode = 0;
        this.mModifiers = i;
        this.mIcon = icon;
    }

    private KeyboardShortcutInfo(Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mIcon = (Icon) parcel.readParcelable(null, Icon.class);
        this.mBaseCharacter = (char) parcel.readInt();
        this.mKeycode = parcel.readInt();
        this.mModifiers = parcel.readInt();
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public void clearIcon() {
        this.mIcon = null;
    }

    public int getKeycode() {
        return this.mKeycode;
    }

    public char getBaseCharacter() {
        return this.mBaseCharacter;
    }

    public int getModifiers() {
        return this.mModifiers;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeParcelable(this.mIcon, 0);
        parcel.writeInt(this.mBaseCharacter);
        parcel.writeInt(this.mKeycode);
        parcel.writeInt(this.mModifiers);
    }
}
