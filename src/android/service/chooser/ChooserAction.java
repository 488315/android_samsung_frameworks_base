package android.service.chooser;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ChooserAction implements Parcelable {
    public static final Parcelable.Creator<ChooserAction> CREATOR = new Parcelable.Creator<ChooserAction>() { // from class: android.service.chooser.ChooserAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChooserAction createFromParcel(Parcel parcel) {
            return new ChooserAction(Icon.CREATOR.createFromParcel(parcel), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), PendingIntent.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChooserAction[] newArray(int i) {
            return new ChooserAction[i];
        }
    };
    private final PendingIntent mAction;
    private final Icon mIcon;
    private final CharSequence mLabel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ChooserAction(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
        this.mIcon = icon;
        this.mLabel = charSequence;
        this.mAction = pendingIntent;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public PendingIntent getAction() {
        return this.mAction;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mIcon.writeToParcel(parcel, i);
        TextUtils.writeToParcel(this.mLabel, parcel, i);
        this.mAction.writeToParcel(parcel, i);
    }

    public String toString() {
        return "ChooserAction {label=" + ((Object) this.mLabel) + ", intent=" + this.mAction + "}";
    }

    public static final class Builder {
        private final PendingIntent mAction;
        private final Icon mIcon;
        private final CharSequence mLabel;

        public Builder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
            Objects.requireNonNull(icon, "icon can not be null");
            Objects.requireNonNull(charSequence, "label can not be null");
            Objects.requireNonNull(pendingIntent, "pending intent can not be null");
            this.mIcon = icon;
            this.mLabel = charSequence;
            this.mAction = pendingIntent;
        }

        public ChooserAction build() {
            return new ChooserAction(this.mIcon, this.mLabel, this.mAction);
        }
    }
}
