package androidx.appcompat.widget;

import android.R;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.ResourceCursorAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
final class SuggestionsAdapter extends ResourceCursorAdapter implements View.OnClickListener {
    private final int mCommitIconResId;
    private int mFlagsCol;
    private int mIconName1Col;
    private int mIconName2Col;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private int mQueryRefinement;
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private int mText1Col;
    private int mText2Col;
    private int mText2UrlCol;
    private ColorStateList mUrlColor;

    private static final class ChildViewCache {
        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View view) {
            this.mText1 = (TextView) view.findViewById(R.id.text1);
            this.mText2 = (TextView) view.findViewById(R.id.text2);
            this.mIcon1 = (ImageView) view.findViewById(R.id.icon1);
            this.mIcon2 = (ImageView) view.findViewById(R.id.icon2);
            this.mIconRefine = (ImageView) view.findViewById(com.samsung.android.biometrics.app.setting.R.id.edit_query);
        }
    }

    public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout());
        this.mQueryRefinement = 1;
        this.mText1Col = -1;
        this.mText2Col = -1;
        this.mText2UrlCol = -1;
        this.mIconName1Col = -1;
        this.mIconName2Col = -1;
        this.mFlagsCol = -1;
        this.mSearchView = searchView;
        this.mSearchable = searchableInfo;
        this.mCommitIconResId = searchView.getSuggestionCommitIconResId();
        this.mProviderContext = context;
        this.mOutsideDrawablesCache = weakHashMap;
    }

    public static String getColumnString(Cursor cursor, String str) {
        return getStringOrNull(cursor, cursor.getColumnIndex(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0116  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Drawable getDrawableFromResourceValue(String str) {
        Drawable drawable = null;
        if (str != null && !str.isEmpty() && !"0".equals(str)) {
            try {
                int parseInt = Integer.parseInt(str);
                String str2 = "android.resource://" + this.mProviderContext.getPackageName() + "/" + parseInt;
                Drawable.ConstantState constantState = this.mOutsideDrawablesCache.get(str2);
                Drawable newDrawable = constantState == null ? null : constantState.newDrawable();
                if (newDrawable != null) {
                    return newDrawable;
                }
                Context context = this.mProviderContext;
                int i = ContextCompat.$r8$clinit;
                Drawable drawable2 = context.getDrawable(parseInt);
                if (drawable2 != null) {
                    this.mOutsideDrawablesCache.put(str2, drawable2.getConstantState());
                }
                return drawable2;
            } catch (Resources.NotFoundException unused) {
                Log.w("SuggestionsAdapter", "Icon resource not found: ".concat(str));
                return null;
            } catch (NumberFormatException unused2) {
                Drawable.ConstantState constantState2 = this.mOutsideDrawablesCache.get(str);
                Drawable newDrawable2 = constantState2 == null ? null : constantState2.newDrawable();
                if (newDrawable2 != null) {
                    return newDrawable2;
                }
                Uri parse = Uri.parse(str);
                try {
                } catch (FileNotFoundException e) {
                    Log.w("SuggestionsAdapter", "Icon not found: " + parse + ", " + e.getMessage());
                }
                if ("android.resource".equals(parse.getScheme())) {
                    try {
                        drawable = getDrawableFromResourceUri(parse);
                        if (drawable != null) {
                            this.mOutsideDrawablesCache.put(str, drawable.getConstantState());
                        }
                    } catch (Resources.NotFoundException unused3) {
                        throw new FileNotFoundException("Resource does not exist: " + parse);
                    }
                } else {
                    InputStream openInputStream = this.mProviderContext.getContentResolver().openInputStream(parse);
                    if (openInputStream == null) {
                        throw new FileNotFoundException("Failed to open " + parse);
                    }
                    try {
                        Drawable createFromStream = Drawable.createFromStream(openInputStream, null);
                        try {
                            openInputStream.close();
                        } catch (IOException e2) {
                            Log.e("SuggestionsAdapter", "Error closing icon stream for " + parse, e2);
                        }
                        drawable = createFromStream;
                        if (drawable != null) {
                        }
                    } finally {
                    }
                }
            }
        }
        return drawable;
    }

    private static String getStringOrNull(Cursor cursor, int i) {
        if (i == -1) {
            return null;
        }
        try {
            return cursor.getString(i);
        } catch (Exception e) {
            Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", e);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0147  */
    @Override // androidx.cursoradapter.widget.CursorAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bindView(View view, Cursor cursor) {
        Drawable drawableFromResourceValue;
        Drawable drawable;
        ActivityInfo activityInfo;
        int iconResource;
        String str;
        ChildViewCache childViewCache = (ChildViewCache) view.getTag();
        int i = this.mFlagsCol;
        int i2 = i != -1 ? cursor.getInt(i) : 0;
        TextView textView = childViewCache.mText1;
        if (textView != null) {
            String stringOrNull = getStringOrNull(cursor, this.mText1Col);
            textView.setText(stringOrNull);
            if (TextUtils.isEmpty(stringOrNull)) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
        }
        TextView textView2 = childViewCache.mText2;
        if (textView2 != null) {
            String stringOrNull2 = getStringOrNull(cursor, this.mText2UrlCol);
            if (stringOrNull2 != null) {
                if (this.mUrlColor == null) {
                    TypedValue typedValue = new TypedValue();
                    this.mProviderContext.getTheme().resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.textColorSearchUrl, typedValue, true);
                    this.mUrlColor = this.mProviderContext.getResources().getColorStateList(typedValue.resourceId);
                }
                SpannableString spannableString = new SpannableString(stringOrNull2);
                spannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, stringOrNull2.length(), 33);
                str = spannableString;
            } else {
                str = getStringOrNull(cursor, this.mText2Col);
            }
            if (TextUtils.isEmpty(str)) {
                if (textView != null) {
                    textView.setSingleLine(false);
                    textView.setMaxLines(2);
                }
            } else if (textView != null) {
                textView.setSingleLine(true);
                textView.setMaxLines(1);
            }
            textView2.setText(str);
            if (TextUtils.isEmpty(str)) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
            }
        }
        ImageView imageView = childViewCache.mIcon1;
        if (imageView != null) {
            int i3 = this.mIconName1Col;
            if (i3 == -1) {
                drawableFromResourceValue = null;
            } else {
                drawableFromResourceValue = getDrawableFromResourceValue(cursor.getString(i3));
                if (drawableFromResourceValue == null) {
                    ComponentName searchActivity = this.mSearchable.getSearchActivity();
                    String flattenToShortString = searchActivity.flattenToShortString();
                    if (this.mOutsideDrawablesCache.containsKey(flattenToShortString)) {
                        Drawable.ConstantState constantState = this.mOutsideDrawablesCache.get(flattenToShortString);
                        drawableFromResourceValue = constantState == null ? null : constantState.newDrawable(this.mProviderContext.getResources());
                    } else {
                        PackageManager packageManager = this.mProviderContext.getPackageManager();
                        try {
                            activityInfo = packageManager.getActivityInfo(searchActivity, 128);
                            iconResource = activityInfo.getIconResource();
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.w("SuggestionsAdapter", e.toString());
                        }
                        if (iconResource != 0) {
                            drawable = packageManager.getDrawable(searchActivity.getPackageName(), iconResource, activityInfo.applicationInfo);
                            if (drawable == null) {
                                Log.w("SuggestionsAdapter", "Invalid icon resource " + iconResource + " for " + searchActivity.flattenToShortString());
                            }
                            this.mOutsideDrawablesCache.put(flattenToShortString, drawable != null ? null : drawable.getConstantState());
                            drawableFromResourceValue = drawable;
                        }
                        drawable = null;
                        this.mOutsideDrawablesCache.put(flattenToShortString, drawable != null ? null : drawable.getConstantState());
                        drawableFromResourceValue = drawable;
                    }
                    if (drawableFromResourceValue == null) {
                        drawableFromResourceValue = this.mProviderContext.getPackageManager().getDefaultActivityIcon();
                    }
                }
            }
            imageView.setImageDrawable(drawableFromResourceValue);
            if (drawableFromResourceValue == null) {
                imageView.setVisibility(4);
            } else {
                imageView.setVisibility(0);
                drawableFromResourceValue.setVisible(false, false);
                drawableFromResourceValue.setVisible(true, false);
            }
        }
        ImageView imageView2 = childViewCache.mIcon2;
        if (imageView2 != null) {
            int i4 = this.mIconName2Col;
            Drawable drawableFromResourceValue2 = i4 == -1 ? null : getDrawableFromResourceValue(cursor.getString(i4));
            imageView2.setImageDrawable(drawableFromResourceValue2);
            if (drawableFromResourceValue2 == null) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
                drawableFromResourceValue2.setVisible(false, false);
                drawableFromResourceValue2.setVisible(true, false);
            }
        }
        int i5 = this.mQueryRefinement;
        ImageView imageView3 = childViewCache.mIconRefine;
        if (i5 != 2 && (i5 != 1 || (i2 & 1) == 0)) {
            imageView3.setVisibility(8);
            return;
        }
        imageView3.setVisibility(0);
        imageView3.setTag(textView.getText());
        imageView3.setOnClickListener(this);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.CursorFilter.CursorFilterClient
    public final void changeCursor(Cursor cursor) {
        try {
            super.changeCursor(cursor);
            if (cursor != null) {
                this.mText1Col = cursor.getColumnIndex("suggest_text_1");
                this.mText2Col = cursor.getColumnIndex("suggest_text_2");
                this.mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url");
                this.mIconName1Col = cursor.getColumnIndex("suggest_icon_1");
                this.mIconName2Col = cursor.getColumnIndex("suggest_icon_2");
                this.mFlagsCol = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Exception e) {
            Log.e("SuggestionsAdapter", "error changing cursor and caching columns", e);
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.CursorFilter.CursorFilterClient
    public final CharSequence convertToString(Cursor cursor) {
        String stringOrNull;
        String stringOrNull2;
        if (cursor == null) {
            return null;
        }
        String stringOrNull3 = getStringOrNull(cursor, cursor.getColumnIndex("suggest_intent_query"));
        if (stringOrNull3 != null) {
            return stringOrNull3;
        }
        if (this.mSearchable.shouldRewriteQueryFromData() && (stringOrNull2 = getStringOrNull(cursor, cursor.getColumnIndex("suggest_intent_data"))) != null) {
            return stringOrNull2;
        }
        if (!this.mSearchable.shouldRewriteQueryFromText() || (stringOrNull = getStringOrNull(cursor, cursor.getColumnIndex("suggest_text_1"))) == null) {
            return null;
        }
        return stringOrNull;
    }

    final Drawable getDrawableFromResourceUri(Uri uri) throws FileNotFoundException {
        int parseInt;
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException("No authority: " + uri);
        }
        try {
            Resources resourcesForApplication = this.mProviderContext.getPackageManager().getResourcesForApplication(authority);
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new FileNotFoundException("No path: " + uri);
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    parseInt = Integer.parseInt(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                }
            } else {
                if (size != 2) {
                    throw new FileNotFoundException("More than two path segments: " + uri);
                }
                parseInt = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            }
            if (parseInt != 0) {
                return resourcesForApplication.getDrawable(parseInt);
            }
            throw new FileNotFoundException("No resource found for: " + uri);
        } catch (PackageManager.NameNotFoundException unused2) {
            throw new FileNotFoundException("No package found for authority: " + uri);
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i, view, viewGroup);
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e);
            View newDropDownView = this.newDropDownView(viewGroup);
            if (newDropDownView != null) {
                ((ChildViewCache) newDropDownView.getTag()).mText1.setText(e.toString());
            }
            return newDropDownView;
        }
    }

    final Cursor getSearchManagerSuggestions(SearchableInfo searchableInfo, String str) {
        String suggestAuthority;
        String[] strArr = null;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder fragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            fragment.appendEncodedPath(suggestPath);
        }
        fragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            fragment.appendPath(str);
        }
        fragment.appendQueryParameter("limit", String.valueOf(50));
        return this.mProviderContext.getContentResolver().query(fragment.build(), null, suggestSelection, strArr, null);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup);
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e);
            View newView = this.newView(this.mProviderContext, this.getCursor(), viewGroup);
            ((ChildViewCache) newView.getTag()).mText1.setText(e.toString());
            return newView;
        }
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public final boolean hasStableIds() {
        return false;
    }

    @Override // androidx.cursoradapter.widget.ResourceCursorAdapter, androidx.cursoradapter.widget.CursorAdapter
    public final View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View newView = super.newView(context, cursor, viewGroup);
        newView.setTag(new ChildViewCache(newView));
        ((ImageView) newView.findViewById(com.samsung.android.biometrics.app.setting.R.id.edit_query)).setImageResource(this.mCommitIconResId);
        return newView;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Cursor cursor = getCursor();
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        Cursor cursor = getCursor();
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.mSearchView.onQueryRefine((CharSequence) tag);
        }
    }

    @Override // androidx.cursoradapter.widget.CursorFilter.CursorFilterClient
    public final Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        String charSequence2 = charSequence == null ? "" : charSequence.toString();
        if (this.mSearchView.getVisibility() != 0 || this.mSearchView.getWindowVisibility() != 0) {
            return null;
        }
        try {
            Cursor searchManagerSuggestions = getSearchManagerSuggestions(this.mSearchable, charSequence2);
            if (searchManagerSuggestions == null) {
                return null;
            }
            searchManagerSuggestions.getCount();
            return searchManagerSuggestions;
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", e);
            return null;
        }
    }

    public final void setQueryRefinement(int i) {
        this.mQueryRefinement = i;
    }
}
