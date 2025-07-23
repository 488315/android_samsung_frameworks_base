package android.service.chooser;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes3.dex */
public final class ChooserTarget implements Parcelable {
    public static final Parcelable.Creator<ChooserTarget> CREATOR = new Parcelable.Creator<ChooserTarget>() { // from class: android.service.chooser.ChooserTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChooserTarget createFromParcel(Parcel parcel) {
            return new ChooserTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChooserTarget[] newArray(int i) {
            return new ChooserTarget[i];
        }
    };
    private static final String TAG = "ChooserTarget";
    private ComponentName mComponentName;
    private Icon mIcon;
    private Bundle mIntentExtras;
    private float mScore;
    private CharSequence mTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ChooserTarget(CharSequence charSequence, Icon icon, float f, ComponentName componentName, Bundle bundle) {
        this.mTitle = charSequence;
        this.mIcon = icon;
        if (f > 1.0f || f < 0.0f) {
            throw new IllegalArgumentException("Score " + f + " out of range; must be between 0.0f and 1.0f");
        }
        this.mScore = f;
        this.mComponentName = componentName;
        this.mIntentExtras = bundle;
    }

    ChooserTarget(Parcel parcel) {
        this.mTitle = parcel.readCharSequence();
        if (parcel.readInt() != 0) {
            this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        this.mScore = parcel.readFloat();
        this.mComponentName = ComponentName.readFromParcel(parcel);
        this.mIntentExtras = parcel.readBundle();
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public float getScore() {
        return this.mScore;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public Bundle getIntentExtras() {
        return this.mIntentExtras;
    }

    public String toString() {
        return "ChooserTarget{" + this.mComponentName + ", " + this.mIntentExtras + ", '" + ((Object) this.mTitle) + "', " + this.mScore + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mTitle);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeFloat(this.mScore);
        ComponentName.writeToParcel(this.mComponentName, parcel);
        parcel.writeBundle(this.mIntentExtras);
    }
}
