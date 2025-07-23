package android.flags;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;

/* loaded from: classes.dex */
abstract class BooleanFlagBase implements Flag<Boolean> {
    private String mCategoryName;
    private String mDescription;
    private String mLabel;
    private final String mName;
    private final String mNamespace;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.Flag
    public abstract Boolean getDefault();

    BooleanFlagBase(String str, String str2) {
        this.mNamespace = str;
        this.mName = str2;
        this.mLabel = str2;
    }

    @Override // android.flags.Flag
    public String getNamespace() {
        return this.mNamespace;
    }

    @Override // android.flags.Flag
    public String getName() {
        return this.mName;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.Flag
    public Flag<Boolean> defineMetaData(String str, String str2, String str3) {
        this.mLabel = str;
        this.mDescription = str2;
        this.mCategoryName = str3;
        return this;
    }

    @Override // android.flags.Flag
    public String getLabel() {
        return this.mLabel;
    }

    @Override // android.flags.Flag
    public String getDescription() {
        return this.mDescription;
    }

    @Override // android.flags.Flag
    public String getCategoryName() {
        return this.mCategoryName;
    }

    public String toString() {
        return getNamespace() + MediaMetrics.SEPARATOR + getName() + NavigationBarInflaterView.SIZE_MOD_START + getDefault() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
