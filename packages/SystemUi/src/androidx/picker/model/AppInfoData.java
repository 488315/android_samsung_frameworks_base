package androidx.picker.model;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface AppInfoData extends AppData {
    Drawable getActionIcon();

    boolean getDimmed();

    String getExtraLabel();

    Drawable getIcon();

    int getItemType();

    String getLabel();

    boolean getSelected();

    Drawable getSubIcon();

    String getSubLabel();

    boolean isValueInSubLabel();

    void setDimmed(boolean z);

    void setIcon(Drawable drawable);

    void setLabel(String str);

    void setSelected(boolean z);
}
