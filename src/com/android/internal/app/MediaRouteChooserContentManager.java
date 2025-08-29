package com.android.internal.app;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaRouter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import java.util.Comparator;

/* loaded from: classes5.dex */
public class MediaRouteChooserContentManager {
    private RouteAdapter mAdapter;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback = new MediaRouterCallback();
    Context mContext;
    Delegate mDelegate;
    private int mRouteTypes;
    private final MediaRouter mRouter;

    public interface Delegate {
        void dismissView();

        boolean showProgressBarWhenEmpty();
    }

    public MediaRouteChooserContentManager(Context context, Delegate delegate) {
        this.mContext = context;
        this.mDelegate = delegate;
        this.mRouter = (MediaRouter) context.getSystemService(MediaRouter.class);
        this.mAdapter = new RouteAdapter(this.mContext);
    }

    public void bindViews(View view) throws Resources.NotFoundException {
        View viewFindViewById = view.findViewById(16908292);
        ListView listView = (ListView) view.findViewById(R.id.media_route_list);
        listView.setAdapter((ListAdapter) this.mAdapter);
        listView.setOnItemClickListener(this.mAdapter);
        listView.setEmptyView(viewFindViewById);
        if (this.mDelegate.showProgressBarWhenEmpty()) {
            return;
        }
        view.findViewById(R.id.media_route_progress_bar).setVisibility(8);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById.getLayoutParams();
        layoutParams.gravity = 17;
        viewFindViewById.setLayoutParams(layoutParams);
    }

    public void onAttachedToWindow() {
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mRouteTypes, this.mCallback, 1);
        refreshRoutes();
    }

    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
    }

    public int getRouteTypes() {
        return this.mRouteTypes;
    }

    public void setRouteTypes(int i) {
        if (this.mRouteTypes != i) {
            this.mRouteTypes = i;
            if (this.mAttachedToWindow) {
                this.mRouter.removeCallback(this.mCallback);
                this.mRouter.addCallback(i, this.mCallback, 1);
            }
            refreshRoutes();
        }
    }

    public void refreshRoutes() {
        if (this.mAttachedToWindow) {
            this.mAdapter.update();
        }
    }

    public boolean onFilterRoute(MediaRouter.RouteInfo routeInfo) {
        return !routeInfo.isDefault() && routeInfo.isEnabled() && routeInfo.matchesTypes(this.mRouteTypes);
    }

    private final class RouteAdapter extends ArrayAdapter<MediaRouter.RouteInfo> implements AdapterView.OnItemClickListener {
        private final LayoutInflater mInflater;

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return false;
        }

        RouteAdapter(Context context) {
            super(context, 0);
            this.mInflater = LayoutInflater.from(context);
        }

        public void update() {
            clear();
            int routeCount = MediaRouteChooserContentManager.this.mRouter.getRouteCount();
            for (int i = 0; i < routeCount; i++) {
                MediaRouter.RouteInfo routeAt = MediaRouteChooserContentManager.this.mRouter.getRouteAt(i);
                if (MediaRouteChooserContentManager.this.onFilterRoute(routeAt)) {
                    add(routeAt);
                }
            }
            sort(RouteComparator.sInstance);
            notifyDataSetChanged();
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            return getItem(i).isEnabled();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.media_route_list_item, viewGroup, false);
            }
            MediaRouter.RouteInfo item = getItem(i);
            TextView textView = (TextView) view.findViewById(16908308);
            TextView textView2 = (TextView) view.findViewById(16908309);
            textView.lambda$setTextAsync$0(item.getName());
            CharSequence description = item.getDescription();
            if (TextUtils.isEmpty(description)) {
                textView2.setVisibility(8);
                textView2.lambda$setTextAsync$0("");
            } else {
                textView2.setVisibility(0);
                textView2.lambda$setTextAsync$0(description);
            }
            view.setEnabled(item.isEnabled());
            return view;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            MediaRouter.RouteInfo item = getItem(i);
            if (item.isEnabled()) {
                item.select();
                MediaRouteChooserContentManager.this.mDelegate.dismissView();
            }
        }
    }

    private static final class RouteComparator implements Comparator<MediaRouter.RouteInfo> {
        public static final RouteComparator sInstance = new RouteComparator();

        private RouteComparator() {
        }

        @Override // java.util.Comparator
        public int compare(MediaRouter.RouteInfo routeInfo, MediaRouter.RouteInfo routeInfo2) {
            return routeInfo.getName().toString().compareTo(routeInfo2.getName().toString());
        }
    }

    private final class MediaRouterCallback extends MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserContentManager.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserContentManager.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserContentManager.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter.SimpleCallback, android.media.MediaRouter.Callback
        public void onRouteSelected(MediaRouter mediaRouter, int i, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserContentManager.this.mDelegate.dismissView();
        }
    }
}
