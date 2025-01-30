package android.view;

import android.p009os.Parcel;
import android.p009os.Parcelable;
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
        public KeyboardShortcutGroup createFromParcel(Parcel source) {
            return new KeyboardShortcutGroup(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardShortcutGroup[] newArray(int size) {
            return new KeyboardShortcutGroup[size];
        }
    };
    private final List<KeyboardShortcutInfo> mItems;
    private final CharSequence mLabel;
    private boolean mSystemGroup;

    public KeyboardShortcutGroup(CharSequence label, List<KeyboardShortcutInfo> items) {
        this.mLabel = label;
        this.mItems = new ArrayList((Collection) Preconditions.checkNotNull(items));
    }

    public KeyboardShortcutGroup(CharSequence label) {
        this(label, (List<KeyboardShortcutInfo>) Collections.emptyList());
    }

    public KeyboardShortcutGroup(CharSequence label, List<KeyboardShortcutInfo> items, boolean isSystemGroup) {
        this.mLabel = label;
        this.mItems = new ArrayList((Collection) Preconditions.checkNotNull(items));
        this.mSystemGroup = isSystemGroup;
    }

    public KeyboardShortcutGroup(CharSequence label, boolean isSystemGroup) {
        this(label, Collections.emptyList(), isSystemGroup);
    }

    private KeyboardShortcutGroup(Parcel source) {
        ArrayList arrayList = new ArrayList();
        this.mItems = arrayList;
        this.mLabel = source.readCharSequence();
        source.readTypedList(arrayList, KeyboardShortcutInfo.CREATOR);
        this.mSystemGroup = source.readInt() == 1;
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

    public void addItem(KeyboardShortcutInfo item) {
        this.mItems.add(item);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeTypedList(this.mItems);
        parcel.writeInt(this.mSystemGroup ? 1 : 0);
    }
}
