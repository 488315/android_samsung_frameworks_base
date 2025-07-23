package android.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes5.dex */
public abstract class ResourceCursorTreeAdapter extends CursorTreeAdapter {
    private int mChildLayout;
    private int mCollapsedGroupLayout;
    private int mExpandedGroupLayout;
    private LayoutInflater mInflater;
    private int mLastChildLayout;

    public ResourceCursorTreeAdapter(Context context, Cursor cursor, int i, int i2, int i3, int i4) {
        super(cursor, context);
        this.mCollapsedGroupLayout = i;
        this.mExpandedGroupLayout = i2;
        this.mChildLayout = i3;
        this.mLastChildLayout = i4;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ResourceCursorTreeAdapter(Context context, Cursor cursor, int i, int i2, int i3) {
        this(context, cursor, i, i2, i3, i3);
    }

    public ResourceCursorTreeAdapter(Context context, Cursor cursor, int i, int i2) {
        this(context, cursor, i, i, i2, i2);
    }

    @Override // android.widget.CursorTreeAdapter
    public View newChildView(Context context, Cursor cursor, boolean z, ViewGroup viewGroup) {
        return this.mInflater.inflate(z ? this.mLastChildLayout : this.mChildLayout, viewGroup, false);
    }

    @Override // android.widget.CursorTreeAdapter
    public View newGroupView(Context context, Cursor cursor, boolean z, ViewGroup viewGroup) {
        return this.mInflater.inflate(z ? this.mExpandedGroupLayout : this.mCollapsedGroupLayout, viewGroup, false);
    }
}
