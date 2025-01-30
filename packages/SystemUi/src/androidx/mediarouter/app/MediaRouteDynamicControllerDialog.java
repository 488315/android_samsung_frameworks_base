package androidx.mediarouter.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouteDynamicControllerDialog extends AppCompatDialog {
    public static final boolean DEBUG = Log.isLoggable("MediaRouteCtrlDialog", 3);
    public RecyclerAdapter mAdapter;
    public int mArtIconBackgroundColor;
    public Bitmap mArtIconBitmap;
    public boolean mArtIconIsLoaded;
    public Bitmap mArtIconLoadedBitmap;
    public Uri mArtIconUri;
    public ImageView mArtView;
    public boolean mAttachedToWindow;
    public final MediaRouterCallback mCallback;
    public ImageButton mCloseButton;
    public final Context mContext;
    public final MediaControllerCallback mControllerCallback;
    public boolean mCreated;
    public MediaDescriptionCompat mDescription;
    public final boolean mEnableGroupVolumeUX;
    public FetchArtTask mFetchArtTask;
    public final List mGroupableRoutes;
    public final HandlerC03281 mHandler;
    public boolean mIsAnimatingVolumeSliderLayout;
    public long mLastUpdateTime;
    public MediaControllerCompat mMediaController;
    public final List mMemberRoutes;
    public ImageView mMetadataBackground;
    public View mMetadataBlackScrim;
    public RecyclerView mRecyclerView;
    public MediaRouter.RouteInfo mRouteForVolumeUpdatingByUser;
    public final MediaRouter mRouter;
    public MediaRouter.RouteInfo mSelectedRoute;
    public MediaRouteSelector mSelector;
    public Button mStopCastingButton;
    public TextView mSubtitleView;
    public String mTitlePlaceholder;
    public TextView mTitleView;
    public final List mTransferableRoutes;
    public final List mUngroupableRoutes;
    public Map mUnmutedVolumeMap;
    public boolean mUpdateMetadataViewsDeferred;
    public boolean mUpdateRoutesViewDeferred;
    public VolumeChangeListener mVolumeChangeListener;
    public Map mVolumeSliderHolderMap;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FetchArtTask extends AsyncTask {
        public int mBackgroundColor;
        public final Bitmap mIconBitmap;
        public final Uri mIconUri;

        public FetchArtTask() {
            MediaDescriptionCompat mediaDescriptionCompat = MediaRouteDynamicControllerDialog.this.mDescription;
            Bitmap bitmap = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.mIcon;
            if (bitmap != null && bitmap.isRecycled()) {
                Log.w("MediaRouteCtrlDialog", "Can't fetch the given art bitmap because it's already recycled.");
                bitmap = null;
            }
            this.mIconBitmap = bitmap;
            MediaDescriptionCompat mediaDescriptionCompat2 = MediaRouteDynamicControllerDialog.this.mDescription;
            this.mIconUri = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.mIconUri : null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:69:0x00cc  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x00de  */
        /* JADX WARN: Type inference failed for: r4v0 */
        /* JADX WARN: Type inference failed for: r4v2 */
        /* JADX WARN: Type inference failed for: r4v3, types: [java.io.InputStream] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object doInBackground(Object[] objArr) {
            InputStream inputStream;
            Bitmap bitmap = this.mIconBitmap;
            ?? r4 = 0;
            if (bitmap == null) {
                Uri uri = this.mIconUri;
                try {
                    if (uri != null) {
                        try {
                            inputStream = openInputStreamByScheme(uri);
                            try {
                                if (inputStream == null) {
                                    Log.w("MediaRouteCtrlDialog", "Unable to open: " + this.mIconUri);
                                    if (inputStream == null) {
                                        return null;
                                    }
                                } else {
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;
                                    BitmapFactory.decodeStream(inputStream, null, options);
                                    if (options.outWidth != 0 && options.outHeight != 0) {
                                        try {
                                            inputStream.reset();
                                        } catch (IOException unused) {
                                            inputStream.close();
                                            inputStream = openInputStreamByScheme(this.mIconUri);
                                            if (inputStream == null) {
                                                Log.w("MediaRouteCtrlDialog", "Unable to open: " + this.mIconUri);
                                                if (inputStream == null) {
                                                    return null;
                                                }
                                            }
                                        }
                                        options.inJustDecodeBounds = false;
                                        options.inSampleSize = Math.max(1, Integer.highestOneBit(options.outHeight / MediaRouteDynamicControllerDialog.this.mContext.getResources().getDimensionPixelSize(R.dimen.mr_cast_meta_art_size)));
                                        if (!isCancelled()) {
                                            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
                                            try {
                                                inputStream.close();
                                            } catch (IOException unused2) {
                                            }
                                            bitmap = decodeStream;
                                        }
                                    }
                                }
                                try {
                                    inputStream.close();
                                    return null;
                                } catch (IOException unused3) {
                                    return null;
                                }
                            } catch (IOException e) {
                                e = e;
                                Log.w("MediaRouteCtrlDialog", "Unable to open: " + this.mIconUri, e);
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException unused4) {
                                    }
                                }
                                bitmap = null;
                                boolean z = MediaRouteDynamicControllerDialog.DEBUG;
                                if (!(bitmap == null && bitmap.isRecycled())) {
                                }
                            }
                        } catch (IOException e2) {
                            e = e2;
                            inputStream = null;
                        } catch (Throwable th) {
                            th = th;
                            if (r4 != 0) {
                                try {
                                    r4.close();
                                } catch (IOException unused5) {
                                }
                            }
                            throw th;
                        }
                    }
                    bitmap = null;
                } catch (Throwable th2) {
                    th = th2;
                    r4 = uri;
                }
            }
            boolean z2 = MediaRouteDynamicControllerDialog.DEBUG;
            if (!(bitmap == null && bitmap.isRecycled())) {
                Log.w("MediaRouteCtrlDialog", "Can't use recycled bitmap: " + bitmap);
                return null;
            }
            if (bitmap != null && bitmap.getWidth() < bitmap.getHeight()) {
                Palette.Builder builder = new Palette.Builder(bitmap);
                builder.mMaxColors = 1;
                List list = builder.generate().mSwatches;
                this.mBackgroundColor = Collections.unmodifiableList(list).isEmpty() ? 0 : ((Palette.Swatch) Collections.unmodifiableList(list).get(0)).mRgb;
            }
            return bitmap;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            mediaRouteDynamicControllerDialog.mFetchArtTask = null;
            if (Objects.equals(mediaRouteDynamicControllerDialog.mArtIconBitmap, this.mIconBitmap) && Objects.equals(MediaRouteDynamicControllerDialog.this.mArtIconUri, this.mIconUri)) {
                return;
            }
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog2 = MediaRouteDynamicControllerDialog.this;
            mediaRouteDynamicControllerDialog2.mArtIconBitmap = this.mIconBitmap;
            mediaRouteDynamicControllerDialog2.mArtIconLoadedBitmap = bitmap;
            mediaRouteDynamicControllerDialog2.mArtIconUri = this.mIconUri;
            mediaRouteDynamicControllerDialog2.mArtIconBackgroundColor = this.mBackgroundColor;
            mediaRouteDynamicControllerDialog2.mArtIconIsLoaded = true;
            mediaRouteDynamicControllerDialog2.updateMetadataViews();
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            mediaRouteDynamicControllerDialog.mArtIconIsLoaded = false;
            mediaRouteDynamicControllerDialog.mArtIconLoadedBitmap = null;
            mediaRouteDynamicControllerDialog.mArtIconBackgroundColor = 0;
        }

        public final InputStream openInputStreamByScheme(Uri uri) {
            InputStream openInputStream;
            String lowerCase = uri.getScheme().toLowerCase();
            if ("android.resource".equals(lowerCase) || "content".equals(lowerCase) || "file".equals(lowerCase)) {
                openInputStream = MediaRouteDynamicControllerDialog.this.mContext.getContentResolver().openInputStream(uri);
            } else {
                URLConnection openConnection = new URL(uri.toString()).openConnection();
                openConnection.setConnectTimeout(30000);
                openConnection.setReadTimeout(30000);
                openInputStream = openConnection.getInputStream();
            }
            if (openInputStream == null) {
                return null;
            }
            return new BufferedInputStream(openInputStream);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaControllerCallback extends MediaControllerCompat.Callback {
        public MediaControllerCallback() {
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public final void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaRouteDynamicControllerDialog.this.mDescription = mediaMetadataCompat == null ? null : mediaMetadataCompat.getDescription();
            MediaRouteDynamicControllerDialog.this.reloadIconIfNeeded();
            MediaRouteDynamicControllerDialog.this.updateMetadataViews();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.Callback
        public final void onSessionDestroyed() {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            MediaControllerCompat mediaControllerCompat = mediaRouteDynamicControllerDialog.mMediaController;
            if (mediaControllerCompat != null) {
                mediaControllerCompat.unregisterCallback(mediaRouteDynamicControllerDialog.mControllerCallback);
                MediaRouteDynamicControllerDialog.this.mMediaController = null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class MediaRouteVolumeSliderHolder extends RecyclerView.ViewHolder {
        public final ImageButton mMuteButton;
        public MediaRouter.RouteInfo mRoute;
        public final MediaRouteVolumeSlider mVolumeSlider;

        public MediaRouteVolumeSliderHolder(View view, ImageButton imageButton, MediaRouteVolumeSlider mediaRouteVolumeSlider) {
            super(view);
            int color;
            int color2;
            this.mMuteButton = imageButton;
            this.mVolumeSlider = mediaRouteVolumeSlider;
            Context context = MediaRouteDynamicControllerDialog.this.mContext;
            Drawable drawable = AppCompatResources.getDrawable(R.drawable.mr_cast_mute_button, context);
            if (MediaRouterThemeHelper.isLightTheme(context)) {
                Object obj = ContextCompat.sLock;
                drawable.setTint(context.getColor(R.color.mr_dynamic_dialog_icon_light));
            }
            imageButton.setImageDrawable(drawable);
            Context context2 = MediaRouteDynamicControllerDialog.this.mContext;
            if (MediaRouterThemeHelper.isLightTheme(context2)) {
                Object obj2 = ContextCompat.sLock;
                color = context2.getColor(R.color.mr_cast_progressbar_progress_and_thumb_light);
                color2 = context2.getColor(R.color.mr_cast_progressbar_background_light);
            } else {
                Object obj3 = ContextCompat.sLock;
                color = context2.getColor(R.color.mr_cast_progressbar_progress_and_thumb_dark);
                color2 = context2.getColor(R.color.mr_cast_progressbar_background_dark);
            }
            mediaRouteVolumeSlider.setColor(color, color2);
        }

        public final void bindRouteVolumeSliderHolder(MediaRouter.RouteInfo routeInfo) {
            this.mRoute = routeInfo;
            int i = routeInfo.mVolume;
            boolean z = i == 0;
            ImageButton imageButton = this.mMuteButton;
            imageButton.setActivated(z);
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.MediaRouteVolumeSliderHolder.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                    if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser != null) {
                        mediaRouteDynamicControllerDialog.mHandler.removeMessages(2);
                    }
                    MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder = MediaRouteVolumeSliderHolder.this;
                    MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser = mediaRouteVolumeSliderHolder.mRoute;
                    int i2 = 1;
                    boolean z2 = !view.isActivated();
                    if (z2) {
                        i2 = 0;
                    } else {
                        MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder2 = MediaRouteVolumeSliderHolder.this;
                        Integer num = (Integer) ((HashMap) MediaRouteDynamicControllerDialog.this.mUnmutedVolumeMap).get(mediaRouteVolumeSliderHolder2.mRoute.mUniqueId);
                        if (num != null) {
                            i2 = Math.max(1, num.intValue());
                        }
                    }
                    MediaRouteVolumeSliderHolder.this.setMute(z2);
                    MediaRouteVolumeSliderHolder.this.mVolumeSlider.setProgress(i2);
                    MediaRouteVolumeSliderHolder.this.mRoute.requestSetVolume(i2);
                    sendEmptyMessageDelayed(2, 500L);
                }
            });
            MediaRouter.RouteInfo routeInfo2 = this.mRoute;
            MediaRouteVolumeSlider mediaRouteVolumeSlider = this.mVolumeSlider;
            mediaRouteVolumeSlider.setTag(routeInfo2);
            mediaRouteVolumeSlider.setMax(routeInfo.mVolumeMax);
            mediaRouteVolumeSlider.setProgress(i);
            mediaRouteVolumeSlider.setOnSeekBarChangeListener(MediaRouteDynamicControllerDialog.this.mVolumeChangeListener);
        }

        public final void setMute(boolean z) {
            ImageButton imageButton = this.mMuteButton;
            if (imageButton.isActivated() == z) {
                return;
            }
            imageButton.setActivated(z);
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            if (z) {
                ((HashMap) mediaRouteDynamicControllerDialog.mUnmutedVolumeMap).put(this.mRoute.mUniqueId, Integer.valueOf(this.mVolumeSlider.getProgress()));
            } else {
                ((HashMap) mediaRouteDynamicControllerDialog.mUnmutedVolumeMap).remove(this.mRoute.mUniqueId);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteAdded(MediaRouter mediaRouter) {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState;
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            boolean z = false;
            if (routeInfo == mediaRouteDynamicControllerDialog.mSelectedRoute && MediaRouter.RouteInfo.getDynamicGroupController() != null) {
                MediaRouter.ProviderInfo providerInfo = routeInfo.mProvider;
                providerInfo.getClass();
                MediaRouter.checkCallingThread();
                Iterator it = Collections.unmodifiableList(providerInfo.mRoutes).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MediaRouter.RouteInfo routeInfo2 = (MediaRouter.RouteInfo) it.next();
                    if (!mediaRouteDynamicControllerDialog.mSelectedRoute.getMemberRoutes().contains(routeInfo2) && (dynamicGroupState = mediaRouteDynamicControllerDialog.mSelectedRoute.getDynamicGroupState(routeInfo2)) != null) {
                        MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                        if ((dynamicRouteDescriptor != null && dynamicRouteDescriptor.mIsGroupable) && !((ArrayList) mediaRouteDynamicControllerDialog.mGroupableRoutes).contains(routeInfo2)) {
                            z = true;
                            break;
                        }
                    }
                }
            }
            if (!z) {
                mediaRouteDynamicControllerDialog.updateRoutesView();
            } else {
                mediaRouteDynamicControllerDialog.updateViewsIfNeeded();
                mediaRouteDynamicControllerDialog.updateRoutes();
            }
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteRemoved(MediaRouter mediaRouter) {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteSelected(MediaRouter.RouteInfo routeInfo) {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            mediaRouteDynamicControllerDialog.mSelectedRoute = routeInfo;
            mediaRouteDynamicControllerDialog.updateViewsIfNeeded();
            mediaRouteDynamicControllerDialog.updateRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteUnselected() {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public final void onRouteVolumeChanged(MediaRouter.RouteInfo routeInfo) {
            MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder;
            int i = routeInfo.mVolume;
            if (MediaRouteDynamicControllerDialog.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onRouteVolumeChanged(), route.getVolume:", i, "MediaRouteCtrlDialog");
            }
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser == routeInfo || (mediaRouteVolumeSliderHolder = (MediaRouteVolumeSliderHolder) ((HashMap) mediaRouteDynamicControllerDialog.mVolumeSliderHolderMap).get(routeInfo.mUniqueId)) == null) {
                return;
            }
            int i2 = mediaRouteVolumeSliderHolder.mRoute.mVolume;
            mediaRouteVolumeSliderHolder.setMute(i2 == 0);
            mediaRouteVolumeSliderHolder.mVolumeSlider.setProgress(i2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecyclerAdapter extends RecyclerView.Adapter {
        public final Drawable mDefaultIcon;
        public Item mGroupVolumeItem;
        public final LayoutInflater mInflater;
        public final int mLayoutAnimationDurationMs;
        public final Drawable mSpeakerGroupIcon;
        public final Drawable mSpeakerIcon;
        public final Drawable mTvIcon;
        public final ArrayList mItems = new ArrayList();
        public final Interpolator mAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class GroupViewHolder extends RecyclerView.ViewHolder {
            public final float mDisabledAlpha;
            public final ImageView mImageView;
            public final View mItemView;
            public final ProgressBar mProgressBar;
            public MediaRouter.RouteInfo mRoute;
            public final TextView mTextView;

            public GroupViewHolder(View view) {
                super(view);
                this.mItemView = view;
                this.mImageView = (ImageView) view.findViewById(R.id.mr_cast_group_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.mr_cast_group_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) view.findViewById(R.id.mr_cast_group_name);
                this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(MediaRouteDynamicControllerDialog.this.mContext);
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(MediaRouteDynamicControllerDialog.this.mContext, progressBar);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class GroupVolumeViewHolder extends MediaRouteVolumeSliderHolder {
            public final int mExpandedHeight;
            public final TextView mTextView;

            public GroupVolumeViewHolder(View view) {
                super(view, (ImageButton) view.findViewById(R.id.mr_cast_mute_button), (MediaRouteVolumeSlider) view.findViewById(R.id.mr_cast_volume_slider));
                this.mTextView = (TextView) view.findViewById(R.id.mr_group_volume_route_name);
                Resources resources = MediaRouteDynamicControllerDialog.this.mContext.getResources();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                TypedValue typedValue = new TypedValue();
                resources.getValue(R.dimen.mr_dynamic_volume_group_list_item_height, typedValue, true);
                this.mExpandedHeight = (int) typedValue.getDimension(displayMetrics);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class HeaderViewHolder extends RecyclerView.ViewHolder {
            public final TextView mTextView;

            public HeaderViewHolder(RecyclerAdapter recyclerAdapter, View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(R.id.mr_cast_header_name);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Item {
            public final Object mData;
            public final int mType;

            public Item(RecyclerAdapter recyclerAdapter, Object obj, int i) {
                this.mData = obj;
                this.mType = i;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class RouteViewHolder extends MediaRouteVolumeSliderHolder {
            public final CheckBox mCheckBox;
            public final float mDisabledAlpha;
            public final int mExpandedLayoutHeight;
            public final ImageView mImageView;
            public final View mItemView;
            public final ProgressBar mProgressBar;
            public final TextView mTextView;
            public final ViewOnClickListenerC03351 mViewClickListener;
            public final RelativeLayout mVolumeSliderLayout;

            /* JADX WARN: Type inference failed for: r0v1, types: [androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$RouteViewHolder$1] */
            public RouteViewHolder(View view) {
                super(view, (ImageButton) view.findViewById(R.id.mr_cast_mute_button), (MediaRouteVolumeSlider) view.findViewById(R.id.mr_cast_volume_slider));
                this.mViewClickListener = new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.RouteViewHolder.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        RouteViewHolder routeViewHolder = RouteViewHolder.this;
                        boolean z = !routeViewHolder.isSelected(routeViewHolder.mRoute);
                        boolean isGroup = RouteViewHolder.this.mRoute.isGroup();
                        if (z) {
                            RouteViewHolder routeViewHolder2 = RouteViewHolder.this;
                            MediaRouter mediaRouter = MediaRouteDynamicControllerDialog.this.mRouter;
                            MediaRouter.RouteInfo routeInfo = routeViewHolder2.mRoute;
                            mediaRouter.getClass();
                            if (routeInfo == null) {
                                throw new NullPointerException("route must not be null");
                            }
                            MediaRouter.checkCallingThread();
                            MediaRouter.GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
                            if (!(globalRouter.mSelectedRouteController instanceof MediaRouteProvider.DynamicGroupRouteController)) {
                                throw new IllegalStateException("There is no currently selected dynamic group route.");
                            }
                            MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = globalRouter.mSelectedRoute.getDynamicGroupState(routeInfo);
                            if (!globalRouter.mSelectedRoute.getMemberRoutes().contains(routeInfo) && dynamicGroupState != null) {
                                MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                                if (dynamicRouteDescriptor != null && dynamicRouteDescriptor.mIsGroupable) {
                                    ((MediaRouteProvider.DynamicGroupRouteController) globalRouter.mSelectedRouteController).onAddMemberRoute(routeInfo.mDescriptorId);
                                }
                            }
                            Log.w("MediaRouter", "Ignoring attempt to add a non-groupable route to dynamic group : " + routeInfo);
                        } else {
                            RouteViewHolder routeViewHolder3 = RouteViewHolder.this;
                            MediaRouter mediaRouter2 = MediaRouteDynamicControllerDialog.this.mRouter;
                            MediaRouter.RouteInfo routeInfo2 = routeViewHolder3.mRoute;
                            mediaRouter2.getClass();
                            if (routeInfo2 == null) {
                                throw new NullPointerException("route must not be null");
                            }
                            MediaRouter.checkCallingThread();
                            MediaRouter.GlobalMediaRouter globalRouter2 = MediaRouter.getGlobalRouter();
                            if (!(globalRouter2.mSelectedRouteController instanceof MediaRouteProvider.DynamicGroupRouteController)) {
                                throw new IllegalStateException("There is no currently selected dynamic group route.");
                            }
                            MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState2 = globalRouter2.mSelectedRoute.getDynamicGroupState(routeInfo2);
                            if (globalRouter2.mSelectedRoute.getMemberRoutes().contains(routeInfo2) && dynamicGroupState2 != null) {
                                MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor2 = dynamicGroupState2.mDynamicDescriptor;
                                if (dynamicRouteDescriptor2 == null || dynamicRouteDescriptor2.mIsUnselectable) {
                                    if (globalRouter2.mSelectedRoute.getMemberRoutes().size() <= 1) {
                                        Log.w("MediaRouter", "Ignoring attempt to remove the last member route.");
                                    } else {
                                        ((MediaRouteProvider.DynamicGroupRouteController) globalRouter2.mSelectedRouteController).onRemoveMemberRoute(routeInfo2.mDescriptorId);
                                    }
                                }
                            }
                            Log.w("MediaRouter", "Ignoring attempt to remove a non-unselectable member route : " + routeInfo2);
                        }
                        RouteViewHolder.this.showSelectingProgress(z, !isGroup);
                        if (isGroup) {
                            List memberRoutes = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes();
                            for (MediaRouter.RouteInfo routeInfo3 : RouteViewHolder.this.mRoute.getMemberRoutes()) {
                                if (memberRoutes.contains(routeInfo3) != z) {
                                    MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder = (MediaRouteVolumeSliderHolder) ((HashMap) MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap).get(routeInfo3.mUniqueId);
                                    if (mediaRouteVolumeSliderHolder instanceof RouteViewHolder) {
                                        ((RouteViewHolder) mediaRouteVolumeSliderHolder).showSelectingProgress(z, true);
                                    }
                                }
                            }
                        }
                        RouteViewHolder routeViewHolder4 = RouteViewHolder.this;
                        RecyclerAdapter recyclerAdapter = RecyclerAdapter.this;
                        MediaRouter.RouteInfo routeInfo4 = routeViewHolder4.mRoute;
                        MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                        List memberRoutes2 = mediaRouteDynamicControllerDialog.mSelectedRoute.getMemberRoutes();
                        int max = Math.max(1, memberRoutes2.size());
                        if (routeInfo4.isGroup()) {
                            Iterator it = routeInfo4.getMemberRoutes().iterator();
                            while (it.hasNext()) {
                                if (memberRoutes2.contains((MediaRouter.RouteInfo) it.next()) != z) {
                                    max += z ? 1 : -1;
                                }
                            }
                        } else {
                            max += z ? 1 : -1;
                        }
                        MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog2 = MediaRouteDynamicControllerDialog.this;
                        boolean z2 = mediaRouteDynamicControllerDialog2.mEnableGroupVolumeUX && mediaRouteDynamicControllerDialog2.mSelectedRoute.getMemberRoutes().size() > 1;
                        boolean z3 = mediaRouteDynamicControllerDialog.mEnableGroupVolumeUX && max >= 2;
                        if (z2 != z3) {
                            RecyclerView.ViewHolder findViewHolderForAdapterPosition = mediaRouteDynamicControllerDialog.mRecyclerView.findViewHolderForAdapterPosition(0);
                            if (findViewHolderForAdapterPosition instanceof GroupVolumeViewHolder) {
                                GroupVolumeViewHolder groupVolumeViewHolder = (GroupVolumeViewHolder) findViewHolderForAdapterPosition;
                                recyclerAdapter.animateLayoutHeight(groupVolumeViewHolder.itemView, z3 ? groupVolumeViewHolder.mExpandedHeight : 0);
                            }
                        }
                    }
                };
                this.mItemView = view;
                this.mImageView = (ImageView) view.findViewById(R.id.mr_cast_route_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.mr_cast_route_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) view.findViewById(R.id.mr_cast_route_name);
                this.mVolumeSliderLayout = (RelativeLayout) view.findViewById(R.id.mr_cast_volume_layout);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.mr_cast_checkbox);
                this.mCheckBox = checkBox;
                MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                Context context = mediaRouteDynamicControllerDialog.mContext;
                Drawable drawable = AppCompatResources.getDrawable(R.drawable.mr_cast_checkbox, context);
                if (MediaRouterThemeHelper.isLightTheme(context)) {
                    Object obj = ContextCompat.sLock;
                    drawable.setTint(context.getColor(R.color.mr_dynamic_dialog_icon_light));
                }
                checkBox.setButtonDrawable(drawable);
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(mediaRouteDynamicControllerDialog.mContext, progressBar);
                this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(mediaRouteDynamicControllerDialog.mContext);
                Resources resources = mediaRouteDynamicControllerDialog.mContext.getResources();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                TypedValue typedValue = new TypedValue();
                resources.getValue(R.dimen.mr_dynamic_dialog_row_height, typedValue, true);
                this.mExpandedLayoutHeight = (int) typedValue.getDimension(displayMetrics);
            }

            public final boolean isSelected(MediaRouter.RouteInfo routeInfo) {
                if (routeInfo.isSelected()) {
                    return true;
                }
                MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getDynamicGroupState(routeInfo);
                if (dynamicGroupState != null) {
                    MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                    if ((dynamicRouteDescriptor != null ? dynamicRouteDescriptor.mSelectionState : 1) == 3) {
                        return true;
                    }
                }
                return false;
            }

            public final void showSelectingProgress(boolean z, boolean z2) {
                CheckBox checkBox = this.mCheckBox;
                checkBox.setEnabled(false);
                this.mItemView.setEnabled(false);
                checkBox.setChecked(z);
                if (z) {
                    this.mImageView.setVisibility(4);
                    this.mProgressBar.setVisibility(0);
                }
                if (z2) {
                    RecyclerAdapter.this.animateLayoutHeight(this.mVolumeSliderLayout, z ? this.mExpandedLayoutHeight : 0);
                }
            }
        }

        public RecyclerAdapter() {
            this.mInflater = LayoutInflater.from(MediaRouteDynamicControllerDialog.this.mContext);
            this.mDefaultIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteDefaultIconDrawable, MediaRouteDynamicControllerDialog.this.mContext);
            this.mTvIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteTvIconDrawable, MediaRouteDynamicControllerDialog.this.mContext);
            this.mSpeakerIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteSpeakerIconDrawable, MediaRouteDynamicControllerDialog.this.mContext);
            this.mSpeakerGroupIcon = MediaRouterThemeHelper.getIconByAttrId(R.attr.mediaRouteSpeakerGroupIconDrawable, MediaRouteDynamicControllerDialog.this.mContext);
            this.mLayoutAnimationDurationMs = MediaRouteDynamicControllerDialog.this.mContext.getResources().getInteger(R.integer.mr_cast_volume_slider_layout_animation_duration_ms);
            updateItems();
        }

        public final void animateLayoutHeight(final View view, final int i) {
            final int i2 = view.getLayoutParams().height;
            Animation animation = new Animation(this) { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.1
                @Override // android.view.animation.Animation
                public final void applyTransformation(float f, Transformation transformation) {
                    int i3 = i;
                    int i4 = i2;
                    View view2 = view;
                    int i5 = i4 + ((int) ((i3 - i4) * f));
                    boolean z = MediaRouteDynamicControllerDialog.DEBUG;
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    layoutParams.height = i5;
                    view2.setLayoutParams(layoutParams);
                }
            };
            animation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.2
                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation2) {
                    MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                    mediaRouteDynamicControllerDialog.mIsAnimatingVolumeSliderLayout = false;
                    mediaRouteDynamicControllerDialog.updateViewsIfNeeded();
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation2) {
                    MediaRouteDynamicControllerDialog.this.mIsAnimatingVolumeSliderLayout = true;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation2) {
                }
            });
            animation.setDuration(this.mLayoutAnimationDurationMs);
            animation.setInterpolator(this.mAccelerateDecelerateInterpolator);
            view.startAnimation(animation);
        }

        public final Drawable getIconDrawable(MediaRouter.RouteInfo routeInfo) {
            Uri uri = routeInfo.mIconUri;
            if (uri != null) {
                try {
                    Drawable createFromStream = Drawable.createFromStream(MediaRouteDynamicControllerDialog.this.mContext.getContentResolver().openInputStream(uri), null);
                    if (createFromStream != null) {
                        return createFromStream;
                    }
                } catch (IOException e) {
                    Log.w("MediaRouteCtrlDialog", "Failed to load " + uri, e);
                }
            }
            int i = routeInfo.mDeviceType;
            return i != 1 ? i != 2 ? routeInfo.isGroup() ? this.mSpeakerGroupIcon : this.mDefaultIcon : this.mSpeakerIcon : this.mTvIcon;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mItems.size() + 1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemViewType(int i) {
            Item item;
            if (i == 0) {
                item = this.mGroupVolumeItem;
            } else {
                item = (Item) this.mItems.get(i - 1);
            }
            return item.mType;
        }

        public final void notifyAdapterDataSetChanged() {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            ((ArrayList) mediaRouteDynamicControllerDialog.mUngroupableRoutes).clear();
            List list = mediaRouteDynamicControllerDialog.mUngroupableRoutes;
            List list2 = mediaRouteDynamicControllerDialog.mGroupableRoutes;
            ArrayList arrayList = new ArrayList();
            MediaRouter.ProviderInfo providerInfo = mediaRouteDynamicControllerDialog.mSelectedRoute.mProvider;
            providerInfo.getClass();
            MediaRouter.checkCallingThread();
            for (MediaRouter.RouteInfo routeInfo : Collections.unmodifiableList(providerInfo.mRoutes)) {
                MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = mediaRouteDynamicControllerDialog.mSelectedRoute.getDynamicGroupState(routeInfo);
                if (dynamicGroupState != null) {
                    MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                    if (dynamicRouteDescriptor != null && dynamicRouteDescriptor.mIsGroupable) {
                        arrayList.add(routeInfo);
                    }
                }
            }
            HashSet hashSet = new HashSet(list2);
            hashSet.removeAll(arrayList);
            ((ArrayList) list).addAll(hashSet);
            notifyDataSetChanged();
        }

        /* JADX WARN: Code restructure failed: missing block: B:51:0x0113, code lost:
        
            if ((r9 == null || r9.mIsUnselectable) != false) goto L55;
         */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            boolean z;
            float f;
            int itemViewType = getItemViewType(i);
            Item item = i == 0 ? this.mGroupVolumeItem : (Item) this.mItems.get(i - 1);
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            if (itemViewType == 1) {
                ((HashMap) mediaRouteDynamicControllerDialog.mVolumeSliderHolderMap).put(((MediaRouter.RouteInfo) item.mData).mUniqueId, (MediaRouteVolumeSliderHolder) viewHolder);
                GroupVolumeViewHolder groupVolumeViewHolder = (GroupVolumeViewHolder) viewHolder;
                MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog2 = MediaRouteDynamicControllerDialog.this;
                r2 = mediaRouteDynamicControllerDialog2.mEnableGroupVolumeUX && mediaRouteDynamicControllerDialog2.mSelectedRoute.getMemberRoutes().size() > 1 ? groupVolumeViewHolder.mExpandedHeight : 0;
                View view = groupVolumeViewHolder.itemView;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = r2;
                view.setLayoutParams(layoutParams);
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) item.mData;
                groupVolumeViewHolder.bindRouteVolumeSliderHolder(routeInfo);
                groupVolumeViewHolder.mTextView.setText(routeInfo.mName);
                return;
            }
            if (itemViewType == 2) {
                ((HeaderViewHolder) viewHolder).mTextView.setText(item.mData.toString());
                return;
            }
            if (itemViewType != 3) {
                if (itemViewType != 4) {
                    Log.w("MediaRouteCtrlDialog", "Cannot bind item to ViewHolder because of wrong view type");
                    return;
                }
                final GroupViewHolder groupViewHolder = (GroupViewHolder) viewHolder;
                MediaRouter.RouteInfo routeInfo2 = (MediaRouter.RouteInfo) item.mData;
                groupViewHolder.mRoute = routeInfo2;
                ImageView imageView = groupViewHolder.mImageView;
                imageView.setVisibility(0);
                groupViewHolder.mProgressBar.setVisibility(4);
                RecyclerAdapter recyclerAdapter = RecyclerAdapter.this;
                List memberRoutes = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes();
                if (memberRoutes.size() == 1 && memberRoutes.get(0) == routeInfo2) {
                    r1 = false;
                }
                r5 = r1 ? 1.0f : groupViewHolder.mDisabledAlpha;
                View view2 = groupViewHolder.mItemView;
                view2.setAlpha(r5);
                view2.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.GroupViewHolder.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        GroupViewHolder groupViewHolder2 = GroupViewHolder.this;
                        MediaRouter mediaRouter = MediaRouteDynamicControllerDialog.this.mRouter;
                        MediaRouter.RouteInfo routeInfo3 = groupViewHolder2.mRoute;
                        mediaRouter.getClass();
                        if (routeInfo3 == null) {
                            throw new NullPointerException("route must not be null");
                        }
                        MediaRouter.checkCallingThread();
                        MediaRouter.GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
                        if (!(globalRouter.mSelectedRouteController instanceof MediaRouteProvider.DynamicGroupRouteController)) {
                            throw new IllegalStateException("There is no currently selected dynamic group route.");
                        }
                        MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = globalRouter.mSelectedRoute.getDynamicGroupState(routeInfo3);
                        if (dynamicGroupState != null) {
                            MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                            if (dynamicRouteDescriptor != null && dynamicRouteDescriptor.mIsTransferable) {
                                ((MediaRouteProvider.DynamicGroupRouteController) globalRouter.mSelectedRouteController).onUpdateMemberRoutes(Collections.singletonList(routeInfo3.mDescriptorId));
                                GroupViewHolder.this.mImageView.setVisibility(4);
                                GroupViewHolder.this.mProgressBar.setVisibility(0);
                            }
                        }
                        Log.w("MediaRouter", "Ignoring attempt to transfer to a non-transferable route.");
                        GroupViewHolder.this.mImageView.setVisibility(4);
                        GroupViewHolder.this.mProgressBar.setVisibility(0);
                    }
                });
                imageView.setImageDrawable(recyclerAdapter.getIconDrawable(routeInfo2));
                groupViewHolder.mTextView.setText(routeInfo2.mName);
                return;
            }
            ((HashMap) mediaRouteDynamicControllerDialog.mVolumeSliderHolderMap).put(((MediaRouter.RouteInfo) item.mData).mUniqueId, (MediaRouteVolumeSliderHolder) viewHolder);
            RouteViewHolder routeViewHolder = (RouteViewHolder) viewHolder;
            MediaRouter.RouteInfo routeInfo3 = (MediaRouter.RouteInfo) item.mData;
            RecyclerAdapter recyclerAdapter2 = RecyclerAdapter.this;
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog3 = MediaRouteDynamicControllerDialog.this;
            if (routeInfo3 == mediaRouteDynamicControllerDialog3.mSelectedRoute && routeInfo3.getMemberRoutes().size() > 0) {
                Iterator it = routeInfo3.getMemberRoutes().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MediaRouter.RouteInfo routeInfo4 = (MediaRouter.RouteInfo) it.next();
                    if (!((ArrayList) mediaRouteDynamicControllerDialog3.mGroupableRoutes).contains(routeInfo4)) {
                        routeInfo3 = routeInfo4;
                        break;
                    }
                }
            }
            routeViewHolder.bindRouteVolumeSliderHolder(routeInfo3);
            Drawable iconDrawable = recyclerAdapter2.getIconDrawable(routeInfo3);
            ImageView imageView2 = routeViewHolder.mImageView;
            imageView2.setImageDrawable(iconDrawable);
            routeViewHolder.mTextView.setText(routeInfo3.mName);
            CheckBox checkBox = routeViewHolder.mCheckBox;
            checkBox.setVisibility(0);
            boolean isSelected = routeViewHolder.isSelected(routeInfo3);
            if (!((ArrayList) mediaRouteDynamicControllerDialog3.mUngroupableRoutes).contains(routeInfo3) && (!routeViewHolder.isSelected(routeInfo3) || mediaRouteDynamicControllerDialog3.mSelectedRoute.getMemberRoutes().size() >= 2)) {
                if (routeViewHolder.isSelected(routeInfo3)) {
                    MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = mediaRouteDynamicControllerDialog3.mSelectedRoute.getDynamicGroupState(routeInfo3);
                    if (dynamicGroupState != null) {
                        MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                    }
                }
                z = true;
                checkBox.setChecked(isSelected);
                routeViewHolder.mProgressBar.setVisibility(4);
                imageView2.setVisibility(0);
                View view3 = routeViewHolder.mItemView;
                view3.setEnabled(z);
                checkBox.setEnabled(z);
                routeViewHolder.mMuteButton.setEnabled(!z || isSelected);
                if (!z && !isSelected) {
                    r1 = false;
                }
                routeViewHolder.mVolumeSlider.setEnabled(r1);
                RouteViewHolder.ViewOnClickListenerC03351 viewOnClickListenerC03351 = routeViewHolder.mViewClickListener;
                view3.setOnClickListener(viewOnClickListenerC03351);
                checkBox.setOnClickListener(viewOnClickListenerC03351);
                if (isSelected && !routeViewHolder.mRoute.isGroup()) {
                    r2 = routeViewHolder.mExpandedLayoutHeight;
                }
                RelativeLayout relativeLayout = routeViewHolder.mVolumeSliderLayout;
                ViewGroup.LayoutParams layoutParams2 = relativeLayout.getLayoutParams();
                layoutParams2.height = r2;
                relativeLayout.setLayoutParams(layoutParams2);
                f = routeViewHolder.mDisabledAlpha;
                view3.setAlpha((!z || isSelected) ? 1.0f : f);
                if (!z && isSelected) {
                    r5 = f;
                }
                checkBox.setAlpha(r5);
            }
            z = false;
            checkBox.setChecked(isSelected);
            routeViewHolder.mProgressBar.setVisibility(4);
            imageView2.setVisibility(0);
            View view32 = routeViewHolder.mItemView;
            view32.setEnabled(z);
            checkBox.setEnabled(z);
            routeViewHolder.mMuteButton.setEnabled(!z || isSelected);
            if (!z) {
                r1 = false;
            }
            routeViewHolder.mVolumeSlider.setEnabled(r1);
            RouteViewHolder.ViewOnClickListenerC03351 viewOnClickListenerC033512 = routeViewHolder.mViewClickListener;
            view32.setOnClickListener(viewOnClickListenerC033512);
            checkBox.setOnClickListener(viewOnClickListenerC033512);
            if (isSelected) {
                r2 = routeViewHolder.mExpandedLayoutHeight;
            }
            RelativeLayout relativeLayout2 = routeViewHolder.mVolumeSliderLayout;
            ViewGroup.LayoutParams layoutParams22 = relativeLayout2.getLayoutParams();
            layoutParams22.height = r2;
            relativeLayout2.setLayoutParams(layoutParams22);
            f = routeViewHolder.mDisabledAlpha;
            view32.setAlpha((!z || isSelected) ? 1.0f : f);
            if (!z) {
                r5 = f;
            }
            checkBox.setAlpha(r5);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            LayoutInflater layoutInflater = this.mInflater;
            if (i == 1) {
                return new GroupVolumeViewHolder(layoutInflater.inflate(R.layout.mr_cast_group_volume_item, (ViewGroup) recyclerView, false));
            }
            if (i == 2) {
                return new HeaderViewHolder(this, layoutInflater.inflate(R.layout.mr_cast_header_item, (ViewGroup) recyclerView, false));
            }
            if (i == 3) {
                return new RouteViewHolder(layoutInflater.inflate(R.layout.mr_cast_route_item, (ViewGroup) recyclerView, false));
            }
            if (i == 4) {
                return new GroupViewHolder(layoutInflater.inflate(R.layout.mr_cast_group_item, (ViewGroup) recyclerView, false));
            }
            Log.w("MediaRouteCtrlDialog", "Cannot create ViewHolder because of wrong view type");
            return null;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.values().remove(viewHolder);
        }

        public final void updateItems() {
            ArrayList arrayList = this.mItems;
            arrayList.clear();
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            this.mGroupVolumeItem = new Item(this, mediaRouteDynamicControllerDialog.mSelectedRoute, 1);
            if (((ArrayList) mediaRouteDynamicControllerDialog.mMemberRoutes).isEmpty()) {
                arrayList.add(new Item(this, mediaRouteDynamicControllerDialog.mSelectedRoute, 3));
            } else {
                Iterator it = ((ArrayList) mediaRouteDynamicControllerDialog.mMemberRoutes).iterator();
                while (it.hasNext()) {
                    arrayList.add(new Item(this, (MediaRouter.RouteInfo) it.next(), 3));
                }
            }
            boolean z = false;
            if (!((ArrayList) mediaRouteDynamicControllerDialog.mGroupableRoutes).isEmpty()) {
                Iterator it2 = ((ArrayList) mediaRouteDynamicControllerDialog.mGroupableRoutes).iterator();
                boolean z2 = false;
                while (it2.hasNext()) {
                    MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) it2.next();
                    if (!((ArrayList) mediaRouteDynamicControllerDialog.mMemberRoutes).contains(routeInfo)) {
                        if (!z2) {
                            mediaRouteDynamicControllerDialog.mSelectedRoute.getClass();
                            MediaRouteProvider.DynamicGroupRouteController dynamicGroupController = MediaRouter.RouteInfo.getDynamicGroupController();
                            String groupableSelectionTitle = dynamicGroupController != null ? dynamicGroupController.getGroupableSelectionTitle() : null;
                            if (TextUtils.isEmpty(groupableSelectionTitle)) {
                                groupableSelectionTitle = mediaRouteDynamicControllerDialog.mContext.getString(R.string.mr_dialog_groupable_header);
                            }
                            arrayList.add(new Item(this, groupableSelectionTitle, 2));
                            z2 = true;
                        }
                        arrayList.add(new Item(this, routeInfo, 3));
                    }
                }
            }
            if (!((ArrayList) mediaRouteDynamicControllerDialog.mTransferableRoutes).isEmpty()) {
                Iterator it3 = ((ArrayList) mediaRouteDynamicControllerDialog.mTransferableRoutes).iterator();
                while (it3.hasNext()) {
                    MediaRouter.RouteInfo routeInfo2 = (MediaRouter.RouteInfo) it3.next();
                    MediaRouter.RouteInfo routeInfo3 = mediaRouteDynamicControllerDialog.mSelectedRoute;
                    if (routeInfo3 != routeInfo2) {
                        if (!z) {
                            routeInfo3.getClass();
                            MediaRouteProvider.DynamicGroupRouteController dynamicGroupController2 = MediaRouter.RouteInfo.getDynamicGroupController();
                            String transferableSectionTitle = dynamicGroupController2 != null ? dynamicGroupController2.getTransferableSectionTitle() : null;
                            if (TextUtils.isEmpty(transferableSectionTitle)) {
                                transferableSectionTitle = mediaRouteDynamicControllerDialog.mContext.getString(R.string.mr_dialog_transferable_header);
                            }
                            arrayList.add(new Item(this, transferableSectionTitle, 2));
                            z = true;
                        }
                        arrayList.add(new Item(this, routeInfo2, 4));
                    }
                }
            }
            notifyAdapterDataSetChanged();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VolumeChangeListener implements SeekBar.OnSeekBarChangeListener {
        public VolumeChangeListener() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) seekBar.getTag();
                MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder = (MediaRouteVolumeSliderHolder) ((HashMap) MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap).get(routeInfo.mUniqueId);
                if (mediaRouteVolumeSliderHolder != null) {
                    mediaRouteVolumeSliderHolder.setMute(i == 0);
                }
                routeInfo.requestSetVolume(i);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser != null) {
                mediaRouteDynamicControllerDialog.mHandler.removeMessages(2);
            }
            MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser = (MediaRouter.RouteInfo) seekBar.getTag();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            sendEmptyMessageDelayed(2, 500L);
        }
    }

    public MediaRouteDynamicControllerDialog(Context context) {
        this(context, 0);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        updateRoutes();
        this.mRouter.getClass();
        boolean z = MediaRouter.DEBUG;
        setMediaSession();
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.mr_cast_dialog);
        MediaRouterThemeHelper.setDialogBackgroundColor(this.mContext, this);
        ImageButton imageButton = (ImageButton) findViewById(R.id.mr_cast_close_button);
        this.mCloseButton = imageButton;
        imageButton.setColorFilter(-1);
        this.mCloseButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaRouteDynamicControllerDialog.this.dismiss();
            }
        });
        Button button = (Button) findViewById(R.id.mr_cast_stop_button);
        this.mStopCastingButton = button;
        button.setTextColor(-1);
        this.mStopCastingButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (MediaRouteDynamicControllerDialog.this.mSelectedRoute.isSelected()) {
                    MediaRouteDynamicControllerDialog.this.mRouter.getClass();
                    MediaRouter.unselect(2);
                }
                MediaRouteDynamicControllerDialog.this.dismiss();
            }
        });
        this.mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mr_cast_list);
        this.mRecyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mVolumeChangeListener = new VolumeChangeListener();
        this.mVolumeSliderHolderMap = new HashMap();
        this.mUnmutedVolumeMap = new HashMap();
        this.mMetadataBackground = (ImageView) findViewById(R.id.mr_cast_meta_background);
        this.mMetadataBlackScrim = findViewById(R.id.mr_cast_meta_black_scrim);
        this.mArtView = (ImageView) findViewById(R.id.mr_cast_meta_art);
        TextView textView = (TextView) findViewById(R.id.mr_cast_meta_title);
        this.mTitleView = textView;
        textView.setTextColor(-1);
        TextView textView2 = (TextView) findViewById(R.id.mr_cast_meta_subtitle);
        this.mSubtitleView = textView2;
        textView2.setTextColor(-1);
        this.mTitlePlaceholder = this.mContext.getResources().getString(R.string.mr_cast_dialog_title_view_placeholder);
        this.mCreated = true;
        updateLayout();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        removeCallbacksAndMessages(null);
        setMediaSession();
    }

    public final void onFilterRoutes(List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) list.get(size);
            if (!(!routeInfo.isDefaultOrBluetooth() && routeInfo.mEnabled && routeInfo.matchesSelector(this.mSelector) && this.mSelectedRoute != routeInfo)) {
                list.remove(size);
            }
        }
    }

    public final void reloadIconIfNeeded() {
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        Bitmap bitmap = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.mIcon;
        Uri uri = mediaDescriptionCompat != null ? mediaDescriptionCompat.mIconUri : null;
        FetchArtTask fetchArtTask = this.mFetchArtTask;
        Bitmap bitmap2 = fetchArtTask == null ? this.mArtIconBitmap : fetchArtTask.mIconBitmap;
        Uri uri2 = fetchArtTask == null ? this.mArtIconUri : fetchArtTask.mIconUri;
        if (bitmap2 != bitmap || (bitmap2 == null && !Objects.equals(uri2, uri))) {
            FetchArtTask fetchArtTask2 = this.mFetchArtTask;
            if (fetchArtTask2 != null) {
                fetchArtTask2.cancel(true);
            }
            FetchArtTask fetchArtTask3 = new FetchArtTask();
            this.mFetchArtTask = fetchArtTask3;
            fetchArtTask3.execute(new Void[0]);
        }
    }

    public final void setMediaSession() {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat != null) {
            mediaControllerCompat.unregisterCallback(this.mControllerCallback);
            this.mMediaController = null;
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
            updateRoutes();
        }
    }

    public final void updateLayout() {
        Context context = this.mContext;
        getWindow().setLayout(!context.getResources().getBoolean(R.bool.is_tablet) ? -1 : MediaRouteDialogHelper.getDialogWidth(context), this.mContext.getResources().getBoolean(R.bool.is_tablet) ? -2 : -1);
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        reloadIconIfNeeded();
        updateMetadataViews();
        updateRoutesView();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMetadataViews() {
        boolean z;
        boolean isEmpty;
        if ((this.mRouteForVolumeUpdatingByUser != null || this.mIsAnimatingVolumeSliderLayout) ? true : !this.mCreated) {
            this.mUpdateMetadataViewsDeferred = true;
            return;
        }
        this.mUpdateMetadataViewsDeferred = false;
        if (!this.mSelectedRoute.isSelected() || this.mSelectedRoute.isDefaultOrBluetooth()) {
            dismiss();
        }
        if (this.mArtIconIsLoaded) {
            Bitmap bitmap = this.mArtIconLoadedBitmap;
            if (!(bitmap != null && bitmap.isRecycled()) && this.mArtIconLoadedBitmap != null) {
                this.mArtView.setVisibility(0);
                this.mArtView.setImageBitmap(this.mArtIconLoadedBitmap);
                this.mArtView.setBackgroundColor(this.mArtIconBackgroundColor);
                this.mMetadataBlackScrim.setVisibility(0);
                Bitmap bitmap2 = this.mArtIconLoadedBitmap;
                RenderScript create = RenderScript.create(this.mContext);
                Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap2);
                Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
                ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                create2.setRadius(10.0f);
                create2.setInput(createFromBitmap);
                create2.forEach(createTyped);
                Bitmap copy = bitmap2.copy(bitmap2.getConfig(), true);
                createTyped.copyTo(copy);
                createFromBitmap.destroy();
                createTyped.destroy();
                create2.destroy();
                create.destroy();
                this.mMetadataBackground.setImageBitmap(copy);
                this.mArtIconIsLoaded = false;
                this.mArtIconLoadedBitmap = null;
                this.mArtIconBackgroundColor = 0;
                MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
                CharSequence charSequence = mediaDescriptionCompat != null ? null : mediaDescriptionCompat.mTitle;
                z = !TextUtils.isEmpty(charSequence);
                MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
                CharSequence charSequence2 = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.mSubtitle : null;
                isEmpty = true ^ TextUtils.isEmpty(charSequence2);
                if (z) {
                    this.mTitleView.setText(this.mTitlePlaceholder);
                } else {
                    this.mTitleView.setText(charSequence);
                }
                if (isEmpty) {
                    this.mSubtitleView.setVisibility(8);
                    return;
                } else {
                    this.mSubtitleView.setText(charSequence2);
                    this.mSubtitleView.setVisibility(0);
                    return;
                }
            }
        }
        Bitmap bitmap3 = this.mArtIconLoadedBitmap;
        if (bitmap3 != null && bitmap3.isRecycled()) {
            Log.w("MediaRouteCtrlDialog", "Can't set artwork image with recycled bitmap: " + this.mArtIconLoadedBitmap);
        }
        this.mArtView.setVisibility(8);
        this.mMetadataBlackScrim.setVisibility(8);
        this.mMetadataBackground.setImageBitmap(null);
        this.mArtIconIsLoaded = false;
        this.mArtIconLoadedBitmap = null;
        this.mArtIconBackgroundColor = 0;
        MediaDescriptionCompat mediaDescriptionCompat3 = this.mDescription;
        if (mediaDescriptionCompat3 != null) {
        }
        z = !TextUtils.isEmpty(charSequence);
        MediaDescriptionCompat mediaDescriptionCompat22 = this.mDescription;
        if (mediaDescriptionCompat22 != null) {
        }
        isEmpty = true ^ TextUtils.isEmpty(charSequence2);
        if (z) {
        }
        if (isEmpty) {
        }
    }

    public final void updateRoutes() {
        ((ArrayList) this.mMemberRoutes).clear();
        ((ArrayList) this.mGroupableRoutes).clear();
        ((ArrayList) this.mTransferableRoutes).clear();
        ((ArrayList) this.mMemberRoutes).addAll(this.mSelectedRoute.getMemberRoutes());
        MediaRouter.ProviderInfo providerInfo = this.mSelectedRoute.mProvider;
        providerInfo.getClass();
        MediaRouter.checkCallingThread();
        for (MediaRouter.RouteInfo routeInfo : Collections.unmodifiableList(providerInfo.mRoutes)) {
            MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = this.mSelectedRoute.getDynamicGroupState(routeInfo);
            if (dynamicGroupState != null) {
                MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                if (dynamicRouteDescriptor != null && dynamicRouteDescriptor.mIsGroupable) {
                    ((ArrayList) this.mGroupableRoutes).add(routeInfo);
                }
                if (dynamicRouteDescriptor != null && dynamicRouteDescriptor.mIsTransferable) {
                    ((ArrayList) this.mTransferableRoutes).add(routeInfo);
                }
            }
        }
        onFilterRoutes(this.mGroupableRoutes);
        onFilterRoutes(this.mTransferableRoutes);
        List list = this.mMemberRoutes;
        RouteComparator routeComparator = RouteComparator.sInstance;
        Collections.sort(list, routeComparator);
        Collections.sort(this.mGroupableRoutes, routeComparator);
        Collections.sort(this.mTransferableRoutes, routeComparator);
        this.mAdapter.updateItems();
    }

    public final void updateRoutesView() {
        if (this.mAttachedToWindow) {
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime < 300) {
                removeMessages(1);
                sendEmptyMessageAtTime(1, this.mLastUpdateTime + 300);
                return;
            }
            if ((this.mRouteForVolumeUpdatingByUser != null || this.mIsAnimatingVolumeSliderLayout) ? true : !this.mCreated) {
                this.mUpdateRoutesViewDeferred = true;
                return;
            }
            this.mUpdateRoutesViewDeferred = false;
            if (!this.mSelectedRoute.isSelected() || this.mSelectedRoute.isDefaultOrBluetooth()) {
                dismiss();
            }
            this.mLastUpdateTime = SystemClock.uptimeMillis();
            this.mAdapter.notifyAdapterDataSetChanged();
        }
    }

    public final void updateViewsIfNeeded() {
        if (this.mUpdateRoutesViewDeferred) {
            updateRoutesView();
        }
        if (this.mUpdateMetadataViewsDeferred) {
            updateMetadataViews();
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Type inference failed for: r2v7, types: [androidx.mediarouter.app.MediaRouteDynamicControllerDialog$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MediaRouteDynamicControllerDialog(Context context, int i) {
        super(r2, MediaRouterThemeHelper.createThemedDialogStyle(r2));
        boolean z = false;
        Context createThemedDialogContext = MediaRouterThemeHelper.createThemedDialogContext(context, i, false);
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mMemberRoutes = new ArrayList();
        this.mGroupableRoutes = new ArrayList();
        this.mTransferableRoutes = new ArrayList();
        this.mUngroupableRoutes = new ArrayList();
        this.mHandler = new Handler() { // from class: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 1) {
                    MediaRouteDynamicControllerDialog.this.updateRoutesView();
                    return;
                }
                if (i2 != 2) {
                    return;
                }
                MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser != null) {
                    mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser = null;
                    mediaRouteDynamicControllerDialog.updateViewsIfNeeded();
                }
            }
        };
        Context context2 = getContext();
        this.mContext = context2;
        this.mRouter = MediaRouter.getInstance(context2);
        if (MediaRouter.sGlobal != null) {
            MediaRouter.getGlobalRouter().getClass();
            z = true;
        }
        this.mEnableGroupVolumeUX = z;
        this.mCallback = new MediaRouterCallback();
        this.mSelectedRoute = MediaRouter.getSelectedRoute();
        this.mControllerCallback = new MediaControllerCallback();
        setMediaSession();
    }
}
