package androidx.mediarouter.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouteDynamicChooserDialog extends AppCompatDialog {
    public RecyclerAdapter mAdapter;
    public boolean mAttachedToWindow;
    public final MediaRouterCallback mCallback;
    public final Context mContext;
    public final HandlerC03251 mHandler;
    public long mLastUpdateTime;
    public RecyclerView mRecyclerView;
    public final MediaRouter mRouter;
    public List mRoutes;
    public MediaRouter.RouteInfo mSelectingRoute;
    public MediaRouteSelector mSelector;
    public final long mUpdateRoutesDelayMs;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteAdded(MediaRouter mediaRouter) {
            MediaRouteDynamicChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteDynamicChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteRemoved(MediaRouter mediaRouter) {
            MediaRouteDynamicChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteSelected(MediaRouter.RouteInfo routeInfo) {
            MediaRouteDynamicChooserDialog.this.dismiss();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecyclerAdapter extends RecyclerView.Adapter {
        public final Drawable mDefaultIcon;
        public final LayoutInflater mInflater;
        public final ArrayList mItems = new ArrayList();
        public final Drawable mSpeakerGroupIcon;
        public final Drawable mSpeakerIcon;
        public final Drawable mTvIcon;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class HeaderViewHolder extends RecyclerView.ViewHolder {
            public final TextView mTextView;

            public HeaderViewHolder(RecyclerAdapter recyclerAdapter, View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(R.id.mr_picker_header_name);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Item {
            public final Object mData;
            public final int mType;

            public Item(RecyclerAdapter recyclerAdapter, Object obj) {
                this.mData = obj;
                if (obj instanceof String) {
                    this.mType = 1;
                } else if (obj instanceof MediaRouter.RouteInfo) {
                    this.mType = 2;
                } else {
                    this.mType = 0;
                    Log.w("RecyclerAdapter", "Wrong type of data passed to Item constructor");
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class RouteViewHolder extends RecyclerView.ViewHolder {
            public final ImageView mImageView;
            public final View mItemView;
            public final ProgressBar mProgressBar;
            public final TextView mTextView;

            public RouteViewHolder(View view) {
                super(view);
                this.mItemView = view;
                this.mImageView = (ImageView) view.findViewById(R.id.mr_picker_route_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.mr_picker_route_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) view.findViewById(R.id.mr_picker_route_name);
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(MediaRouteDynamicChooserDialog.this.mContext, progressBar);
            }
        }

        public RecyclerAdapter() {
            this.mInflater = LayoutInflater.from(MediaRouteDynamicChooserDialog.this.mContext);
            this.mDefaultIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteDefaultIconDrawable, MediaRouteDynamicChooserDialog.this.mContext);
            this.mTvIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteTvIconDrawable, MediaRouteDynamicChooserDialog.this.mContext);
            this.mSpeakerIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteSpeakerIconDrawable, MediaRouteDynamicChooserDialog.this.mContext);
            this.mSpeakerGroupIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteSpeakerGroupIconDrawable, MediaRouteDynamicChooserDialog.this.mContext);
            rebuildItems();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mItems.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemViewType(int i) {
            return ((Item) this.mItems.get(i)).mType;
        }

        /* JADX WARN: Code restructure failed: missing block: B:24:0x0056, code lost:
        
            if (r1 != null) goto L24;
         */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            Drawable createFromStream;
            int itemViewType = getItemViewType(i);
            Item item = (Item) this.mItems.get(i);
            if (itemViewType == 1) {
                ((HeaderViewHolder) viewHolder).mTextView.setText(item.mData.toString());
                return;
            }
            if (itemViewType != 2) {
                Log.w("RecyclerAdapter", "Cannot bind item to ViewHolder because of wrong view type");
                return;
            }
            final RouteViewHolder routeViewHolder = (RouteViewHolder) viewHolder;
            final MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) item.mData;
            View view = routeViewHolder.mItemView;
            view.setVisibility(0);
            routeViewHolder.mProgressBar.setVisibility(4);
            view.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicChooserDialog.RecyclerAdapter.RouteViewHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = MediaRouteDynamicChooserDialog.this;
                    MediaRouter.RouteInfo routeInfo2 = routeInfo;
                    mediaRouteDynamicChooserDialog.mSelectingRoute = routeInfo2;
                    routeInfo2.select();
                    RouteViewHolder.this.mImageView.setVisibility(4);
                    RouteViewHolder.this.mProgressBar.setVisibility(0);
                }
            });
            routeViewHolder.mTextView.setText(routeInfo.mName);
            RecyclerAdapter recyclerAdapter = RecyclerAdapter.this;
            recyclerAdapter.getClass();
            Uri uri = routeInfo.mIconUri;
            if (uri != null) {
                try {
                    createFromStream = Drawable.createFromStream(MediaRouteDynamicChooserDialog.this.mContext.getContentResolver().openInputStream(uri), null);
                } catch (IOException e) {
                    Log.w("RecyclerAdapter", "Failed to load " + uri, e);
                }
            }
            int i2 = routeInfo.mDeviceType;
            createFromStream = i2 != 1 ? i2 != 2 ? routeInfo.isGroup() ? recyclerAdapter.mSpeakerGroupIcon : recyclerAdapter.mDefaultIcon : recyclerAdapter.mSpeakerIcon : recyclerAdapter.mTvIcon;
            routeViewHolder.mImageView.setImageDrawable(createFromStream);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            LayoutInflater layoutInflater = this.mInflater;
            if (i == 1) {
                return new HeaderViewHolder(this, layoutInflater.inflate(R.layout.mr_picker_header_item, (ViewGroup) recyclerView, false));
            }
            if (i == 2) {
                return new RouteViewHolder(layoutInflater.inflate(R.layout.mr_picker_route_item, (ViewGroup) recyclerView, false));
            }
            Log.w("RecyclerAdapter", "Cannot create ViewHolder because of wrong view type");
            return null;
        }

        public final void rebuildItems() {
            ArrayList arrayList = this.mItems;
            arrayList.clear();
            MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = MediaRouteDynamicChooserDialog.this;
            arrayList.add(new Item(this, mediaRouteDynamicChooserDialog.mContext.getString(R.string.mr_chooser_title)));
            Iterator it = mediaRouteDynamicChooserDialog.mRoutes.iterator();
            while (it.hasNext()) {
                arrayList.add(new Item(this, (MediaRouter.RouteInfo) it.next()));
            }
            notifyDataSetChanged();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RouteComparator implements Comparator {
        public static final RouteComparator sInstance = new RouteComparator();

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((MediaRouter.RouteInfo) obj).mName.compareToIgnoreCase(((MediaRouter.RouteInfo) obj2).mName);
        }
    }

    public MediaRouteDynamicChooserDialog(Context context) {
        this(context, 0);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        refreshRoutes();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.mr_picker_dialog);
        MediaRouterThemeHelper.setDialogBackgroundColor(this.mContext, this);
        this.mRoutes = new ArrayList();
        ((ImageButton) findViewById(R.id.mr_picker_close_button)).setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicChooserDialog.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaRouteDynamicChooserDialog.this.dismiss();
            }
        });
        this.mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mr_picker_list);
        this.mRecyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        Context context = this.mContext;
        getWindow().setLayout(!context.getResources().getBoolean(R.bool.is_tablet) ? -1 : MediaRouteDialogHelper.getDialogWidth(context), this.mContext.getResources().getBoolean(R.bool.is_tablet) ? -2 : -1);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        removeMessages(1);
    }

    public final void refreshRoutes() {
        if (this.mSelectingRoute == null && this.mAttachedToWindow) {
            this.mRouter.getClass();
            MediaRouter.checkCallingThread();
            MediaRouter.GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
            ArrayList arrayList = new ArrayList(globalRouter == null ? Collections.emptyList() : globalRouter.mRoutes);
            int size = arrayList.size();
            while (true) {
                int i = size - 1;
                if (size <= 0) {
                    break;
                }
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) arrayList.get(i);
                if (!(!routeInfo.isDefaultOrBluetooth() && routeInfo.mEnabled && routeInfo.matchesSelector(this.mSelector))) {
                    arrayList.remove(i);
                }
                size = i;
            }
            Collections.sort(arrayList, RouteComparator.sInstance);
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime < this.mUpdateRoutesDelayMs) {
                removeMessages(1);
                HandlerC03251 handlerC03251 = this.mHandler;
                handlerC03251.sendMessageAtTime(handlerC03251.obtainMessage(1, arrayList), this.mLastUpdateTime + this.mUpdateRoutesDelayMs);
            } else {
                this.mLastUpdateTime = SystemClock.uptimeMillis();
                ((ArrayList) this.mRoutes).clear();
                ((ArrayList) this.mRoutes).addAll(arrayList);
                this.mAdapter.rebuildItems();
            }
        }
    }

    public final void setRouteSelector(MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        }
        if (this.mSelector.equals(mediaRouteSelector)) {
            return;
        }
        this.mSelector = mediaRouteSelector;
        if (this.mAttachedToWindow) {
            this.mRouter.removeCallback(this.mCallback);
            this.mRouter.addCallback(mediaRouteSelector, this.mCallback, 1);
        }
        refreshRoutes();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Type inference failed for: r2v3, types: [androidx.mediarouter.app.MediaRouteDynamicChooserDialog$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MediaRouteDynamicChooserDialog(Context context, int i) {
        super(r2, MediaRouterThemeHelper.createThemedDialogStyle(r2));
        Context createThemedDialogContext = MediaRouterThemeHelper.createThemedDialogContext(context, i, false);
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mHandler = new Handler() { // from class: androidx.mediarouter.app.MediaRouteDynamicChooserDialog.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = MediaRouteDynamicChooserDialog.this;
                List list = (List) message.obj;
                mediaRouteDynamicChooserDialog.getClass();
                mediaRouteDynamicChooserDialog.mLastUpdateTime = SystemClock.uptimeMillis();
                ((ArrayList) mediaRouteDynamicChooserDialog.mRoutes).clear();
                ((ArrayList) mediaRouteDynamicChooserDialog.mRoutes).addAll(list);
                mediaRouteDynamicChooserDialog.mAdapter.rebuildItems();
            }
        };
        Context context2 = getContext();
        this.mRouter = MediaRouter.getInstance(context2);
        this.mCallback = new MediaRouterCallback();
        this.mContext = context2;
        this.mUpdateRoutesDelayMs = context2.getResources().getInteger(R.integer.mr_update_routes_delay_ms);
    }
}
