package android.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
/* loaded from: classes.dex */
public abstract class LauncherActivity extends ListActivity {
    IconResizer mIconResizer;
    Intent mIntent;
    PackageManager mPackageManager;

    protected boolean onEvaluateShowIcons() {
        return true;
    }

    public static class ListItem {
        public String className;
        public Bundle extras;
        public Drawable icon;
        public CharSequence label;
        public String packageName;
        public ResolveInfo resolveInfo;

        ListItem(PackageManager packageManager, ResolveInfo resolveInfo, IconResizer iconResizer) {
            this.resolveInfo = resolveInfo;
            this.label = resolveInfo.loadLabel(packageManager);
            ComponentInfo componentInfo = resolveInfo.activityInfo;
            componentInfo = componentInfo == null ? resolveInfo.serviceInfo : componentInfo;
            if (this.label == null && componentInfo != null) {
                this.label = resolveInfo.activityInfo.name;
            }
            if (iconResizer != null) {
                this.icon = iconResizer.createIconThumbnail(resolveInfo.loadIcon(packageManager));
            }
            this.packageName = componentInfo.applicationInfo.packageName;
            this.className = componentInfo.name;
        }

        public ListItem() {
        }
    }

    private class ActivityAdapter extends BaseAdapter implements Filterable {
        private final Object lock = new Object();
        protected List<ListItem> mActivitiesList;
        private Filter mFilter;
        protected final IconResizer mIconResizer;
        protected final LayoutInflater mInflater;
        private ArrayList<ListItem> mOriginalValues;
        private final boolean mShowIcons;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public ActivityAdapter(IconResizer iconResizer) {
            this.mIconResizer = iconResizer;
            this.mInflater = (LayoutInflater) LauncherActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mShowIcons = LauncherActivity.this.onEvaluateShowIcons();
            this.mActivitiesList = LauncherActivity.this.makeListItems();
        }

        public Intent intentForPosition(int i) {
            if (this.mActivitiesList == null) {
                return null;
            }
            Intent intent = new Intent(LauncherActivity.this.mIntent);
            ListItem listItem = this.mActivitiesList.get(i);
            intent.setClassName(listItem.packageName, listItem.className);
            if (listItem.extras != null) {
                intent.putExtras(listItem.extras);
            }
            return intent;
        }

        public ListItem itemForPosition(int i) {
            List<ListItem> list = this.mActivitiesList;
            if (list == null) {
                return null;
            }
            return list.get(i);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            List<ListItem> list = this.mActivitiesList;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.activity_list_item_2, viewGroup, false);
            }
            bindView(view, this.mActivitiesList.get(i));
            return view;
        }

        private void bindView(View view, ListItem listItem) {
            TextView textView = (TextView) view;
            textView.lambda$setTextAsync$0(listItem.label);
            if (this.mShowIcons) {
                if (listItem.icon == null) {
                    listItem.icon = this.mIconResizer.createIconThumbnail(listItem.resolveInfo.loadIcon(LauncherActivity.this.getPackageManager()));
                }
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(listItem.icon, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }

        @Override // android.widget.Filterable
        public Filter getFilter() {
            if (this.mFilter == null) {
                this.mFilter = new ArrayFilter();
            }
            return this.mFilter;
        }

        private class ArrayFilter extends Filter {
            private ArrayFilter() {
            }

            @Override // android.widget.Filter
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                Filter.FilterResults filterResults = new Filter.FilterResults();
                if (ActivityAdapter.this.mOriginalValues == null) {
                    synchronized (ActivityAdapter.this.lock) {
                        ActivityAdapter.this.mOriginalValues = new ArrayList(ActivityAdapter.this.mActivitiesList);
                    }
                }
                if (charSequence == null || charSequence.length() == 0) {
                    synchronized (ActivityAdapter.this.lock) {
                        ArrayList arrayList = new ArrayList(ActivityAdapter.this.mOriginalValues);
                        filterResults.values = arrayList;
                        filterResults.count = arrayList.size();
                    }
                    return filterResults;
                }
                String lowerCase = charSequence.toString().toLowerCase();
                ArrayList arrayList2 = ActivityAdapter.this.mOriginalValues;
                int size = arrayList2.size();
                ArrayList arrayList3 = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    ListItem listItem = (ListItem) arrayList2.get(i);
                    String[] strArrSplit = listItem.label.toString().toLowerCase().split(" ");
                    int length = strArrSplit.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        if (strArrSplit[i2].startsWith(lowerCase)) {
                            arrayList3.add(listItem);
                            break;
                        }
                        i2++;
                    }
                }
                filterResults.values = arrayList3;
                filterResults.count = arrayList3.size();
                return filterResults;
            }

            @Override // android.widget.Filter
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                ActivityAdapter.this.mActivitiesList = (List) filterResults.values;
                if (filterResults.count > 0) {
                    ActivityAdapter.this.notifyDataSetChanged();
                } else {
                    ActivityAdapter.this.notifyDataSetInvalidated();
                }
            }
        }
    }

    public class IconResizer {
        private Canvas mCanvas;
        private int mIconHeight;
        private int mIconWidth;
        private final Rect mOldBounds = new Rect();

        public IconResizer() {
            this.mIconWidth = -1;
            this.mIconHeight = -1;
            Canvas canvas = new Canvas();
            this.mCanvas = canvas;
            canvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
            int dimension = (int) LauncherActivity.this.getResources().getDimension(17104896);
            this.mIconHeight = dimension;
            this.mIconWidth = dimension;
        }

        public Drawable createIconThumbnail(Drawable drawable) {
            int i = this.mIconWidth;
            int i2 = this.mIconHeight;
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (drawable instanceof PaintDrawable) {
                PaintDrawable paintDrawable = (PaintDrawable) drawable;
                paintDrawable.setIntrinsicWidth(i);
                paintDrawable.setIntrinsicHeight(i2);
            }
            if (i <= 0 || i2 <= 0) {
                return drawable;
            }
            if (i >= intrinsicWidth && i2 >= intrinsicHeight) {
                if (intrinsicWidth >= i || intrinsicHeight >= i2) {
                    return drawable;
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = this.mCanvas;
                canvas.setBitmap(bitmapCreateBitmap);
                this.mOldBounds.set(drawable.getBounds());
                int i3 = (i - intrinsicWidth) / 2;
                int i4 = (i2 - intrinsicHeight) / 2;
                drawable.setBounds(i3, i4, intrinsicWidth + i3, intrinsicHeight + i4);
                drawable.draw(canvas);
                drawable.setBounds(this.mOldBounds);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(LauncherActivity.this.getResources(), bitmapCreateBitmap);
                canvas.setBitmap(null);
                return bitmapDrawable;
            }
            float f = intrinsicWidth / intrinsicHeight;
            if (intrinsicWidth > intrinsicHeight) {
                i2 = (int) (i / f);
            } else if (intrinsicHeight > intrinsicWidth) {
                i = (int) (i2 * f);
            }
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas2 = this.mCanvas;
            canvas2.setBitmap(bitmapCreateBitmap2);
            this.mOldBounds.set(drawable.getBounds());
            int i5 = (this.mIconWidth - i) / 2;
            int i6 = (this.mIconHeight - i2) / 2;
            drawable.setBounds(i5, i6, i + i5, i2 + i6);
            drawable.draw(canvas2);
            drawable.setBounds(this.mOldBounds);
            BitmapDrawable bitmapDrawable2 = new BitmapDrawable(LauncherActivity.this.getResources(), bitmapCreateBitmap2);
            canvas2.setBitmap(null);
            return bitmapDrawable2;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PackageManager packageManager = getPackageManager();
        this.mPackageManager = packageManager;
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)) {
            requestWindowFeature(5);
            setProgressBarIndeterminateVisibility(true);
        }
        onSetContentView();
        this.mIconResizer = new IconResizer();
        Intent intent = new Intent(getTargetIntent());
        this.mIntent = intent;
        intent.setComponent(null);
        this.mAdapter = new ActivityAdapter(this.mIconResizer);
        setListAdapter(this.mAdapter);
        getListView().setTextFilterEnabled(true);
        updateAlertTitle();
        updateButtonText();
        if (this.mPackageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)) {
            return;
        }
        setProgressBarIndeterminateVisibility(false);
    }

    private void updateAlertTitle() {
        TextView textView = (TextView) findViewById(R.id.alertTitle);
        if (textView != null) {
            textView.lambda$setTextAsync$0(getTitle());
        }
    }

    private void updateButtonText() {
        Button button = (Button) findViewById(16908313);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: android.app.LauncherActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    LauncherActivity.this.finish();
                }
            });
        }
    }

    @Override // android.app.Activity
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        updateAlertTitle();
    }

    @Override // android.app.Activity
    public void setTitle(int i) {
        super.setTitle(i);
        updateAlertTitle();
    }

    protected void onSetContentView() {
        setContentView(R.layout.activity_list);
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(ListView listView, View view, int i, long j) {
        startActivity(intentForPosition(i));
    }

    protected Intent intentForPosition(int i) {
        return ((ActivityAdapter) this.mAdapter).intentForPosition(i);
    }

    protected ListItem itemForPosition(int i) {
        return ((ActivityAdapter) this.mAdapter).itemForPosition(i);
    }

    protected Intent getTargetIntent() {
        return new Intent();
    }

    protected List<ResolveInfo> onQueryPackageManager(Intent intent) {
        return this.mPackageManager.queryIntentActivities(intent, 0);
    }

    protected void onSortResultList(List<ResolveInfo> list) {
        Collections.sort(list, new ResolveInfo.DisplayNameComparator(this.mPackageManager));
    }

    public List<ListItem> makeListItems() {
        List<ResolveInfo> listOnQueryPackageManager = onQueryPackageManager(this.mIntent);
        onSortResultList(listOnQueryPackageManager);
        ArrayList arrayList = new ArrayList(listOnQueryPackageManager.size());
        int size = listOnQueryPackageManager.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new ListItem(this.mPackageManager, listOnQueryPackageManager.get(i), null));
        }
        return arrayList;
    }
}
