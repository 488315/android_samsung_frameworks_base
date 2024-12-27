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

    BooleanFlagBase(String namespace, String name) {
        this.mNamespace = namespace;
        this.mName = name;
        this.mLabel = name;
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
    public Flag<Boolean> defineMetaData(String label, String description, String categoryName) {
        this.mLabel = label;
        this.mDescription = description;
        this.mCategoryName = categoryName;
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
        return getNamespace()
                + MediaMetrics.SEPARATOR
                + getName()
                + NavigationBarInflaterView.SIZE_MOD_START
                + getDefault()
                + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
