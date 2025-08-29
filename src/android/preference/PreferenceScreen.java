package android.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public final class PreferenceScreen extends PreferenceGroup implements AdapterView.OnItemClickListener, DialogInterface.OnDismissListener {
    private Dialog mDialog;
    private boolean mDialogFitsSystemWindows;
    private Drawable mDividerDrawable;
    private boolean mDividerSpecified;
    private int mLayoutResId;
    private ListView mListView;
    private ListAdapter mRootAdapter;

    @Override // android.preference.PreferenceGroup
    protected boolean isOnSameScreenAsChildren() {
        return false;
    }

    public PreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842891);
        this.mLayoutResId = R.layout.preference_list_fragment;
        this.mDialogFitsSystemWindows = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.PreferenceScreen, 16842891, 0);
        this.mLayoutResId = typedArrayObtainStyledAttributes.getResourceId(1, this.mLayoutResId);
        if (typedArrayObtainStyledAttributes.hasValueOrEmpty(0)) {
            this.mDividerDrawable = typedArrayObtainStyledAttributes.getDrawable(0);
            this.mDividerSpecified = true;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setDialogFitsSystemWindows(boolean z) {
        this.mDialogFitsSystemWindows = z;
    }

    public ListAdapter getRootAdapter() {
        if (this.mRootAdapter == null) {
            this.mRootAdapter = onCreateRootAdapter();
        }
        return this.mRootAdapter;
    }

    protected ListAdapter onCreateRootAdapter() {
        return new PreferenceGroupAdapter(this);
    }

    public void bind(ListView listView) {
        listView.setOnItemClickListener(this);
        if (View.sIsSamsungBasicInteraction) {
            if (this.mIsChangedCategoryBG) {
                listView.semSetBottomColor(this.mCategoryBGColor);
                listView.semSetRoundedCornerColor(15, this.mCategoryBGColor);
            }
            listView.semSetRoundedCorners(3);
            listView.setDivider(null);
            listView.setDividerHeight(0);
        }
        listView.setAdapter(getRootAdapter());
        onAttachedToActivity();
    }

    @Override // android.preference.Preference
    protected void onClick() throws Resources.NotFoundException {
        if (getIntent() == null && getFragment() == null && getPreferenceCount() != 0) {
            showDialog(null);
        }
    }

    private void showDialog(Bundle bundle) throws Resources.NotFoundException {
        Context context = getContext();
        ListView listView = this.mListView;
        if (listView != null) {
            listView.setAdapter((ListAdapter) null);
        }
        View viewInflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.mLayoutResId, (ViewGroup) null);
        View viewFindViewById = viewInflate.findViewById(16908310);
        ListView listView2 = (ListView) viewInflate.findViewById(16908298);
        this.mListView = listView2;
        if (this.mDialogFitsSystemWindows) {
            listView2.setFitsSystemWindows(true);
        }
        if (this.mDividerSpecified) {
            this.mListView.setDivider(this.mDividerDrawable);
        }
        bind(this.mListView);
        CharSequence title = getTitle();
        Dialog dialog = new Dialog(context, context.getThemeResId());
        this.mDialog = dialog;
        if (TextUtils.isEmpty(title)) {
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(8);
            }
            dialog.getWindow().requestFeature(1);
        } else if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).lambda$setTextAsync$0(title);
            viewFindViewById.setVisibility(0);
        } else {
            dialog.setTitle(title);
        }
        dialog.setContentView(viewInflate);
        dialog.setOnDismissListener(this);
        if (bundle != null) {
            dialog.onRestoreInstanceState(bundle);
        }
        getPreferenceManager().addPreferencesScreen(dialog);
        dialog.show();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        this.mDialog = null;
        getPreferenceManager().removePreferencesScreen(dialogInterface);
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        if (adapterView instanceof ListView) {
            i -= ((ListView) adapterView).getHeaderViewsCount();
        }
        Object item = getRootAdapter().getItem(i);
        if (item instanceof Preference) {
            ((Preference) item).performClick(this);
        }
    }

    @Override // android.preference.Preference
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        Dialog dialog = this.mDialog;
        if (dialog == null || !dialog.isShowing()) {
            return parcelableOnSaveInstanceState;
        }
        SavedState savedState = new SavedState(parcelableOnSaveInstanceState);
        savedState.isDialogShowing = true;
        savedState.dialogBundle = dialog.onSaveInstanceState();
        return savedState;
    }

    @Override // android.preference.Preference
    protected void onRestoreInstanceState(Parcelable parcelable) throws Resources.NotFoundException {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.isDialogShowing) {
            showDialog(savedState.dialogBundle);
        }
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.preference.PreferenceScreen.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Bundle dialogBundle;
        boolean isDialogShowing;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isDialogShowing = parcel.readInt() == 1;
            this.dialogBundle = parcel.readBundle();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isDialogShowing ? 1 : 0);
            parcel.writeBundle(this.dialogBundle);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public void semSetCategoryBGColor(int i) {
        this.mIsChangedCategoryBG = true;
        this.mCategoryBGColor = i;
    }
}
