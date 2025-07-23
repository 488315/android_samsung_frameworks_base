package android.view;

import android.graphics.Rect;

/* loaded from: classes4.dex */
public abstract class ActionMode {
    public static final int DEFAULT_HIDE_DURATION = -1;
    public static final int SEM_TYPE_FLOATING = 99;
    public static final int TYPE_FLOATING = 1;
    public static final int TYPE_PRIMARY = 0;
    private Object mTag;
    private boolean mTitleOptionalHint;
    private int mType = 0;

    public interface Callback {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, Menu menu);

        void onDestroyActionMode(ActionMode actionMode);

        boolean onPrepareActionMode(ActionMode actionMode, Menu menu);
    }

    public abstract void finish();

    public abstract View getCustomView();

    public abstract Menu getMenu();

    public abstract MenuInflater getMenuInflater();

    public abstract CharSequence getSubtitle();

    public abstract CharSequence getTitle();

    public void hide(long j) {
    }

    public abstract void invalidate();

    public void invalidateContentRect() {
    }

    public boolean isTitleOptional() {
        return false;
    }

    public boolean isUiFocusable() {
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
    }

    public abstract void setCustomView(View view);

    public abstract void setSubtitle(int i);

    public abstract void setSubtitle(CharSequence charSequence);

    public abstract void setTitle(int i);

    public abstract void setTitle(CharSequence charSequence);

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public Object getTag() {
        return this.mTag;
    }

    public void setTitleOptionalHint(boolean z) {
        this.mTitleOptionalHint = z;
    }

    public boolean getTitleOptionalHint() {
        return this.mTitleOptionalHint;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }

    public static abstract class Callback2 implements Callback {
        public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            if (view != null) {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            } else {
                rect.set(0, 0, 0, 0);
            }
        }
    }
}
