package com.android.internal.statusbar;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class StatusBarIcon implements Parcelable {
    public static final Parcelable.Creator<StatusBarIcon> CREATOR = new Parcelable.Creator<StatusBarIcon>() { // from class: com.android.internal.statusbar.StatusBarIcon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusBarIcon createFromParcel(Parcel parcel) {
            return new StatusBarIcon(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusBarIcon[] newArray(int i) {
            return new StatusBarIcon[i];
        }
    };
    public CharSequence contentDescription;
    public Icon icon;
    public int iconLevel;
    public int number;
    public String pkg;
    public Drawable preloadedIcon;
    public Shape shape;
    public Type type;
    public UserHandle user;
    public boolean visible;

    public enum Shape {
        WRAP_CONTENT,
        FIXED_SPACE
    }

    public enum Type {
        PeopleAvatar,
        NotifSmallIcon,
        SystemIcon,
        ResourceIcon
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StatusBarIcon(UserHandle userHandle, String str, Icon icon, int i, int i2, CharSequence charSequence, Type type, Shape shape) {
        this.visible = true;
        if (icon.getType() == 2 && TextUtils.isEmpty(icon.getResPackage())) {
            icon = Icon.createWithResource(str, icon.getResId());
        }
        this.pkg = str;
        this.user = userHandle;
        this.icon = icon;
        this.iconLevel = i;
        this.number = i2;
        this.contentDescription = charSequence;
        this.type = type;
        this.shape = shape;
    }

    public StatusBarIcon(UserHandle userHandle, String str, Icon icon, int i, int i2, CharSequence charSequence, Type type) {
        this(userHandle, str, icon, i, i2, charSequence, type, Shape.WRAP_CONTENT);
    }

    public StatusBarIcon(String str, UserHandle userHandle, int i, int i2, int i3, CharSequence charSequence, Type type) {
        this(userHandle, str, Icon.createWithResource(str, i), i2, i3, charSequence, type);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("StatusBarIcon(icon=");
        sb.append(this.icon);
        String str2 = "";
        if (this.iconLevel != 0) {
            str = " level=" + this.iconLevel;
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(this.visible ? " visible" : "");
        sb.append(" user=");
        sb.append(this.user.getIdentifier());
        if (this.number != 0) {
            str2 = " num=" + this.number;
        }
        sb.append(str2);
        sb.append(" )");
        return sb.toString();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public StatusBarIcon m8283clone() {
        StatusBarIcon statusBarIcon = new StatusBarIcon(this.user, this.pkg, this.icon, this.iconLevel, this.number, this.contentDescription, this.type, this.shape);
        statusBarIcon.visible = this.visible;
        statusBarIcon.preloadedIcon = this.preloadedIcon;
        return statusBarIcon;
    }

    public StatusBarIcon(Parcel parcel) {
        this.visible = true;
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.icon = (Icon) parcel.readParcelable(null, Icon.class);
        this.pkg = parcel.readString();
        this.user = (UserHandle) parcel.readParcelable(null, UserHandle.class);
        this.iconLevel = parcel.readInt();
        this.visible = parcel.readInt() != 0;
        this.number = parcel.readInt();
        this.contentDescription = parcel.readCharSequence();
        this.type = Type.valueOf(parcel.readString());
        this.shape = Shape.valueOf(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.icon, 0);
        parcel.writeString(this.pkg);
        parcel.writeParcelable(this.user, 0);
        parcel.writeInt(this.iconLevel);
        parcel.writeInt(this.visible ? 1 : 0);
        parcel.writeInt(this.number);
        parcel.writeCharSequence(this.contentDescription);
        parcel.writeString(this.type.name());
        parcel.writeString(this.shape.name());
    }
}
