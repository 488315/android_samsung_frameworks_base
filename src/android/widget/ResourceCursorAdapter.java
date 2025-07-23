package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes5.dex */
public abstract class ResourceCursorAdapter extends CursorAdapter {
    private LayoutInflater mDropDownInflater;
    private int mDropDownLayout;
    private LayoutInflater mInflater;
    private int mLayout;

    @Deprecated
    public ResourceCursorAdapter(Context context, int i, Cursor cursor) {
        super(context, cursor);
        this.mDropDownLayout = i;
        this.mLayout = i;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mInflater = layoutInflater;
        this.mDropDownInflater = layoutInflater;
    }

    public ResourceCursorAdapter(Context context, int i, Cursor cursor, boolean z) {
        super(context, cursor, z);
        this.mDropDownLayout = i;
        this.mLayout = i;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mInflater = layoutInflater;
        this.mDropDownInflater = layoutInflater;
    }

    public ResourceCursorAdapter(Context context, int i, Cursor cursor, int i2) {
        super(context, cursor, i2);
        this.mDropDownLayout = i;
        this.mLayout = i;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mInflater = layoutInflater;
        this.mDropDownInflater = layoutInflater;
    }

    @Override // android.widget.CursorAdapter, android.widget.ThemedSpinnerAdapter
    public void setDropDownViewTheme(Resources.Theme theme) {
        super.setDropDownViewTheme(theme);
        if (theme == null) {
            this.mDropDownInflater = null;
        } else if (theme == this.mInflater.getContext().getTheme()) {
            this.mDropDownInflater = this.mInflater;
        } else {
            this.mDropDownInflater = LayoutInflater.from(new ContextThemeWrapper(this.mContext, theme));
        }
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.mInflater.inflate(this.mLayout, viewGroup, false);
    }

    @Override // android.widget.CursorAdapter
    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.mDropDownInflater.inflate(this.mDropDownLayout, viewGroup, false);
    }

    public void setViewResource(int i) {
        this.mLayout = i;
    }

    public void setDropDownViewResource(int i) {
        this.mDropDownLayout = i;
    }
}
