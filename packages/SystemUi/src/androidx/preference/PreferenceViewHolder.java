package androidx.preference;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PreferenceViewHolder extends RecyclerView.ViewHolder {
    public final Drawable mBackground;
    public final SparseArray mCachedViews;
    public boolean mDividerAllowedAbove;
    public boolean mDividerAllowedBelow;
    public int mDividerStartOffset;
    public boolean mDrawBackground;
    public int mDrawCorners;
    public boolean mSubheaderRound;
    public final ColorStateList mTitleTextColors;

    public PreferenceViewHolder(View view) {
        super(view);
        SparseArray sparseArray = new SparseArray(4);
        this.mCachedViews = sparseArray;
        this.mDividerStartOffset = 0;
        this.mDrawBackground = false;
        this.mSubheaderRound = false;
        TextView textView = (TextView) view.findViewById(R.id.title);
        sparseArray.put(R.id.title, textView);
        sparseArray.put(R.id.summary, view.findViewById(R.id.summary));
        sparseArray.put(R.id.icon, view.findViewById(R.id.icon));
        sparseArray.put(com.android.systemui.R.id.icon_frame, view.findViewById(com.android.systemui.R.id.icon_frame));
        sparseArray.put(R.id.icon_frame, view.findViewById(R.id.icon_frame));
        this.mBackground = view.getBackground();
        if (textView != null) {
            this.mTitleTextColors = textView.getTextColors();
        }
    }

    public final View findViewById(int i) {
        SparseArray sparseArray = this.mCachedViews;
        View view = (View) sparseArray.get(i);
        if (view != null) {
            return view;
        }
        View findViewById = this.itemView.findViewById(i);
        if (findViewById != null) {
            sparseArray.put(i, findViewById);
        }
        return findViewById;
    }
}
