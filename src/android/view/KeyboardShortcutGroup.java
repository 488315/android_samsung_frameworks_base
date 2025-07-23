package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class KeyboardShortcutGroup implements Parcelable {
    public static final Parcelable.Creator<KeyboardShortcutGroup> CREATOR = new Parcelable.Creator<KeyboardShortcutGroup>() { // from class: android.view.KeyboardShortcutGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardShortcutGroup createFromParcel(Parcel parcel) {
            return new KeyboardShortcutGroup(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardShortcutGroup[] newArray(int i) {
            return new KeyboardShortcutGroup[i];
        }
    };
    private final List<KeyboardShortcutInfo> mItems;
    private final CharSequence mLabel;
    private CharSequence mPackageName;
    private boolean mSystemGroup;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeyboardShortcutGroup(CharSequence charSequence, List<KeyboardShortcutInfo> list) {
        this.mLabel = charSequence;
        this.mItems = new ArrayList((Collection) Preconditions.checkNotNull(list));
    }

    public KeyboardShortcutGroup(CharSequence charSequence) {
        this(charSequence, (List<KeyboardShortcutInfo>) Collections.EMPTY_LIST);
    }

    public KeyboardShortcutGroup(CharSequence charSequence, List<KeyboardShortcutInfo> list, boolean z) {
        this.mLabel = charSequence;
        this.mItems = new ArrayList((Collection) Preconditions.checkNotNull(list));
        this.mSystemGroup = z;
    }

    public KeyboardShortcutGroup(CharSequence charSequence, boolean z) {
        this(charSequence, Collections.EMPTY_LIST, z);
    }

    private KeyboardShortcutGroup(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mItems = arrayList;
        this.mLabel = parcel.readCharSequence();
        parcel.readTypedList(arrayList, KeyboardShortcutInfo.CREATOR);
        this.mSystemGroup = parcel.readInt() == 1;
        this.mPackageName = parcel.readCharSequence();
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public List<KeyboardShortcutInfo> getItems() {
        return this.mItems;
    }

    public boolean isSystemGroup() {
        return this.mSystemGroup;
    }

    public void setPackageName(CharSequence charSequence) {
        this.mPackageName = charSequence;
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public void addItem(KeyboardShortcutInfo keyboardShortcutInfo) {
        this.mItems.add(keyboardShortcutInfo);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeTypedList(this.mItems);
        parcel.writeInt(this.mSystemGroup ? 1 : 0);
        parcel.writeCharSequence(this.mPackageName);
    }
}
