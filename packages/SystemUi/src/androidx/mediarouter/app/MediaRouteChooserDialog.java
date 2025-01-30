package androidx.mediarouter.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import com.android.systemui.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouteChooserDialog extends AppCompatDialog {
    public RouteAdapter mAdapter;
    public boolean mAttachedToWindow;
    public final MediaRouterCallback mCallback;
    public final HandlerC03111 mHandler;
    public long mLastUpdateTime;
    public ListView mListView;
    public final MediaRouter mRouter;
    public ArrayList mRoutes;
    public MediaRouteSelector mSelector;
    public TextView mTitleView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteAdded(MediaRouter mediaRouter) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteRemoved(MediaRouter mediaRouter) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteSelected(MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.dismiss();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RouteAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
        public final Drawable mDefaultIcon;
        public final LayoutInflater mInflater;
        public final Drawable mSpeakerGroupIcon;
        public final Drawable mSpeakerIcon;
        public final Drawable mTvIcon;

        public RouteAdapter(Context context, List<MediaRouter.RouteInfo> list) {
            super(context, 0, list);
            this.mInflater = LayoutInflater.from(context);
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.mediaRouteDefaultIconDrawable, R.attr.mediaRouteTvIconDrawable, R.attr.mediaRouteSpeakerIconDrawable, R.attr.mediaRouteSpeakerGroupIconDrawable});
            this.mDefaultIcon = AppCompatResources.getDrawable(obtainStyledAttributes.getResourceId(0, 0), context);
            this.mTvIcon = AppCompatResources.getDrawable(obtainStyledAttributes.getResourceId(1, 0), context);
            this.mSpeakerIcon = AppCompatResources.getDrawable(obtainStyledAttributes.getResourceId(2, 0), context);
            this.mSpeakerGroupIcon = AppCompatResources.getDrawable(obtainStyledAttributes.getResourceId(3, 0), context);
            obtainStyledAttributes.recycle();
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean areAllItemsEnabled() {
            return false;
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x007f, code lost:
        
            if (r0 != null) goto L35;
         */
        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Drawable createFromStream;
            if (view == null) {
                view = this.mInflater.inflate(R.layout.mr_chooser_list_item, viewGroup, false);
            }
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) getItem(i);
            TextView textView = (TextView) view.findViewById(R.id.mr_chooser_route_name);
            TextView textView2 = (TextView) view.findViewById(R.id.mr_chooser_route_desc);
            textView.setText(routeInfo.mName);
            String str = routeInfo.mDescription;
            int i2 = routeInfo.mConnectionState;
            if (!(i2 == 2 || i2 == 1) || TextUtils.isEmpty(str)) {
                textView.setGravity(16);
                textView2.setVisibility(8);
                textView2.setText("");
            } else {
                textView.setGravity(80);
                textView2.setVisibility(0);
                textView2.setText(str);
            }
            view.setEnabled(routeInfo.mEnabled);
            ImageView imageView = (ImageView) view.findViewById(R.id.mr_chooser_route_icon);
            if (imageView != null) {
                Uri uri = routeInfo.mIconUri;
                if (uri != null) {
                    try {
                        createFromStream = Drawable.createFromStream(getContext().getContentResolver().openInputStream(uri), null);
                    } catch (IOException e) {
                        Log.w("MediaRouteChooserDialog", "Failed to load " + uri, e);
                    }
                }
                int i3 = routeInfo.mDeviceType;
                createFromStream = i3 != 1 ? i3 != 2 ? routeInfo.isGroup() ? this.mSpeakerGroupIcon : this.mDefaultIcon : this.mSpeakerIcon : this.mTvIcon;
                imageView.setImageDrawable(createFromStream);
            }
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return ((MediaRouter.RouteInfo) getItem(i)).mEnabled;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) getItem(i);
            if (routeInfo.mEnabled) {
                ImageView imageView = (ImageView) view.findViewById(R.id.mr_chooser_route_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.mr_chooser_route_progress_bar);
                if (imageView != null && progressBar != null) {
                    imageView.setVisibility(8);
                    progressBar.setVisibility(0);
                }
                routeInfo.select();
            }
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

    public MediaRouteChooserDialog(Context context) {
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
        setContentView(R.layout.mr_chooser_dialog);
        this.mRoutes = new ArrayList();
        this.mAdapter = new RouteAdapter(getContext(), this.mRoutes);
        ListView listView = (ListView) findViewById(R.id.mr_chooser_list);
        this.mListView = listView;
        listView.setAdapter((ListAdapter) this.mAdapter);
        this.mListView.setOnItemClickListener(this.mAdapter);
        this.mListView.setEmptyView(findViewById(android.R.id.empty));
        this.mTitleView = (TextView) findViewById(R.id.mr_chooser_title);
        getWindow().setLayout(MediaRouteDialogHelper.getDialogWidth(getContext()), -2);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        removeMessages(1);
        super.onDetachedFromWindow();
    }

    public final void refreshRoutes() {
        if (this.mAttachedToWindow) {
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
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime < 300) {
                removeMessages(1);
                HandlerC03111 handlerC03111 = this.mHandler;
                handlerC03111.sendMessageAtTime(handlerC03111.obtainMessage(1, arrayList), this.mLastUpdateTime + 300);
            } else {
                this.mLastUpdateTime = SystemClock.uptimeMillis();
                this.mRoutes.clear();
                this.mRoutes.addAll(arrayList);
                this.mAdapter.notifyDataSetChanged();
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

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public final void setTitle(CharSequence charSequence) {
        this.mTitleView.setText(charSequence);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Type inference failed for: r2v3, types: [androidx.mediarouter.app.MediaRouteChooserDialog$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MediaRouteChooserDialog(Context context, int i) {
        super(r2, MediaRouterThemeHelper.createThemedDialogStyle(r2));
        Context createThemedDialogContext = MediaRouterThemeHelper.createThemedDialogContext(context, i, false);
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mHandler = new Handler() { // from class: androidx.mediarouter.app.MediaRouteChooserDialog.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                MediaRouteChooserDialog mediaRouteChooserDialog = MediaRouteChooserDialog.this;
                List list = (List) message.obj;
                mediaRouteChooserDialog.getClass();
                mediaRouteChooserDialog.mLastUpdateTime = SystemClock.uptimeMillis();
                mediaRouteChooserDialog.mRoutes.clear();
                mediaRouteChooserDialog.mRoutes.addAll(list);
                mediaRouteChooserDialog.mAdapter.notifyDataSetChanged();
            }
        };
        this.mRouter = MediaRouter.getInstance(getContext());
        this.mCallback = new MediaRouterCallback();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public final void setTitle(int i) {
        this.mTitleView.setText(i);
    }
}
