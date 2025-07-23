package androidx.appcompat.view;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.MenuBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ActionMode {
    public Object mTag;
    public boolean mTitleOptionalHint;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, MenuBuilder menuBuilder);

        void onDestroyActionMode(ActionMode actionMode);

        boolean onPrepareActionMode(ActionMode actionMode, MenuBuilder menuBuilder);
    }

    public abstract void finish();

    public abstract View getCustomView();

    public abstract MenuBuilder getMenu();

    public abstract MenuInflater getMenuInflater();

    public abstract CharSequence getSubtitle();

    public abstract CharSequence getTitle();

    public abstract void invalidate();

    public boolean isTitleOptional() {
        return false;
    }

    public abstract void setCustomView(View view);

    public abstract void setSubtitle(int i);

    public abstract void setSubtitle(CharSequence charSequence);

    public abstract void setTitle(int i);

    public abstract void setTitle(CharSequence charSequence);

    public void setTitleOptionalHint(boolean z) {
        this.mTitleOptionalHint = z;
    }
}
