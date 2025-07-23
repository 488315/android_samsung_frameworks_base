package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.util.Log;
import android.util.PathParser;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.internal.graphics.ColorUtils;
import com.android.launcher3.icons.BitmapInfo;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleViewInfoTaskLegacy;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.shared.bubbles.FlyoutDrawableLoader;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleViewInfoTaskLegacy extends AsyncTask {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor mBackgroundExecutor;
    public final BubbleBadgeIconFactory mBadgeIconFactory;
    public final Bubble mBubble;
    public final WeakReference mContext;
    public final WeakReference mExpandedViewManager;
    public final BubbleIconFactory mIconFactory;
    public final WeakReference mLayerView;
    public final Executor mMainExecutor;
    public final WeakReference mPositioner;
    public final boolean mSkipInflation;
    public final WeakReference mStackView;
    public final WeakReference mTaskViewFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BubbleViewInfo {
        public String appName;
        public Bitmap badgeBitmap;
        public BubbleBarExpandedView bubbleBarExpandedView;
        public Bitmap bubbleBitmap;
        public int dotColor;
        public Path dotPath;
        public BubbleExpandedView expandedView;
        public Bubble.FlyoutMessage flyoutMessage;
        public BadgedImageView imageView;
        public Bitmap rawBadgeBitmap;
        public ShortcutInfo shortcutInfo;

        public static BubbleViewInfo populate(Context context, BubbleExpandedViewManager bubbleExpandedViewManager, BubbleTaskViewFactory bubbleTaskViewFactory, BubblePositioner bubblePositioner, BubbleStackView bubbleStackView, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory, Bubble bubble, boolean z) {
            BubbleViewInfo bubbleViewInfo = new BubbleViewInfo();
            if (!z && !bubble.isInflated()) {
                LayoutInflater from = LayoutInflater.from(context);
                BadgedImageView badgedImageView = (BadgedImageView) from.inflate(R.layout.bubble_view, (ViewGroup) bubbleStackView, false);
                bubbleViewInfo.imageView = badgedImageView;
                badgedImageView.initialize(bubblePositioner);
                BubbleTaskView orCreateBubbleTaskView = bubble.getOrCreateBubbleTaskView(bubbleTaskViewFactory);
                BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) from.inflate(R.layout.bubble_expanded_view, (ViewGroup) bubbleStackView, false);
                bubbleViewInfo.expandedView = bubbleExpandedView;
                bubbleExpandedView.initialize(bubbleExpandedViewManager, bubbleStackView, bubblePositioner, false, orCreateBubbleTaskView);
            }
            if (!BubbleViewInfoTaskLegacy.m3218$$Nest$smpopulateCommonInfo(bubbleViewInfo, context, bubble, bubbleIconFactory, bubbleBadgeIconFactory)) {
                return null;
            }
            Bubble.FlyoutMessage flyoutMessage = bubble.mFlyoutMessage;
            bubbleViewInfo.flyoutMessage = flyoutMessage;
            if (flyoutMessage != null) {
                flyoutMessage.senderAvatar = FlyoutDrawableLoader.loadFlyoutDrawable(context, flyoutMessage.senderIcon);
            }
            return bubbleViewInfo;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
    }

    /* renamed from: -$$Nest$smpopulateCommonInfo, reason: not valid java name */
    public static boolean m3218$$Nest$smpopulateCommonInfo(BubbleViewInfo bubbleViewInfo, Context context, Bubble bubble, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory) {
        Drawable drawable;
        Bitmap createIconBitmap;
        ShortcutInfo shortcutInfo = bubble.mShortcutInfo;
        if (shortcutInfo != null) {
            bubbleViewInfo.shortcutInfo = shortcutInfo;
        }
        PackageManager packageManagerForUser = BubbleController.getPackageManagerForUser(bubble.mUser.getIdentifier(), context);
        try {
            ApplicationInfo applicationInfo = packageManagerForUser.getApplicationInfo(bubble.mPackageName, 795136);
            if (applicationInfo != null) {
                bubbleViewInfo.appName = String.valueOf(packageManagerForUser.getApplicationLabel(applicationInfo));
            }
            Drawable applicationIcon = packageManagerForUser.getApplicationIcon(bubble.mPackageName);
            Drawable userBadgedIcon = packageManagerForUser.getUserBadgedIcon(applicationIcon, bubble.mUser);
            try {
                ShortcutInfo shortcutInfo2 = bubbleViewInfo.shortcutInfo;
                Icon icon = bubble.mIcon;
                bubbleIconFactory.getClass();
                drawable = BubbleIconFactory.getBubbleDrawable(context, shortcutInfo2, icon);
            } catch (Exception unused) {
                Log.w("Bubbles", "Exception creating icon for the bubble: " + bubble.mKey);
                drawable = null;
            }
            if (drawable != null) {
                applicationIcon = drawable;
            }
            BitmapInfo badgeBitmap = bubbleBadgeIconFactory.getBadgeBitmap(userBadgedIcon);
            Bitmap bitmap = badgeBitmap.icon;
            bubbleViewInfo.badgeBitmap = bitmap;
            if (bubble.mIsImportantConversation) {
                bitmap = bubbleBadgeIconFactory.getBadgeBitmap(userBadgedIcon).icon;
            }
            bubbleViewInfo.rawBadgeBitmap = bitmap;
            float[] fArr = new float[1];
            if (applicationIcon instanceof AdaptiveIconDrawable) {
                createIconBitmap = Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createIconBitmap);
                applicationIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) applicationIcon;
                if (adaptiveIconDrawable.getBackground() != null) {
                    adaptiveIconDrawable.getBackground().draw(canvas);
                }
                if (adaptiveIconDrawable.getForeground() != null) {
                    adaptiveIconDrawable.getForeground().draw(canvas);
                }
            } else {
                createIconBitmap = bubbleBadgeIconFactory.createIconBitmap(applicationIcon, 1.0f, bubbleBadgeIconFactory.mContext.getResources().getDimensionPixelSize(R.dimen.bubble_size));
            }
            int dimensionPixelSize = bubbleBadgeIconFactory.mContext.getResources().getDimensionPixelSize(R.dimen.bubble_size);
            float dimensionPixelSize2 = bubbleBadgeIconFactory.mContext.getResources().getDimensionPixelSize(R.dimen.sec_noti_bubble_icon_outline_border);
            Bitmap createBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setColor(-12303292);
            Rect rect = new Rect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            paint.setAntiAlias(true);
            canvas2.drawARGB(0, 0, 0, 0);
            canvas2.drawCircle(canvas2.getWidth() / 2, canvas2.getHeight() / 2, (canvas2.getWidth() / 2) - dimensionPixelSize2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas2.drawBitmap(createIconBitmap, (Rect) null, rect, paint);
            paint.setColor(bubbleBadgeIconFactory.mContext.getResources().getColor(R.color.sec_bubble_noti_icon_outline_border_color));
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            canvas2.drawCircle(canvas2.getWidth() / 2, canvas2.getHeight() / 2, canvas2.getWidth() / 2, paint);
            bubbleViewInfo.bubbleBitmap = bubbleBadgeIconFactory.createIconBitmap(createBitmap).icon;
            Path createPathFromPathData = PathParser.createPathFromPathData(context.getResources().getString(android.R.string.eventTypeAnniversary));
            Matrix matrix = new Matrix();
            float f = fArr[0];
            matrix.setScale(f, f, 50.0f, 50.0f);
            createPathFromPathData.transform(matrix);
            bubbleViewInfo.dotPath = createPathFromPathData;
            bubbleViewInfo.dotColor = ColorUtils.blendARGB(badgeBitmap.color, -1, 0.54f);
            return true;
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.w("Bubbles", "Unable to find package: " + bubble.mPackageName);
            return false;
        }
    }

    public BubbleViewInfoTaskLegacy(Bubble bubble, Context context, BubbleExpandedViewManager bubbleExpandedViewManager, BubbleTaskViewFactory bubbleTaskViewFactory, BubblePositioner bubblePositioner, BubbleStackView bubbleStackView, BubbleBarLayerView bubbleBarLayerView, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory, boolean z, Callback callback, Executor executor, Executor executor2) {
        this.mBubble = bubble;
        this.mContext = new WeakReference(context);
        this.mExpandedViewManager = new WeakReference(bubbleExpandedViewManager);
        this.mTaskViewFactory = new WeakReference(bubbleTaskViewFactory);
        this.mPositioner = new WeakReference(bubblePositioner);
        this.mStackView = new WeakReference(bubbleStackView);
        this.mLayerView = new WeakReference(bubbleBarLayerView);
        this.mIconFactory = bubbleIconFactory;
        this.mBadgeIconFactory = bubbleBadgeIconFactory;
        this.mSkipInflation = z;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
    }

    @Override // android.os.AsyncTask
    public final Object doInBackground(Object[] objArr) {
        ((BubbleExpandedViewManager$Companion$fromBubbleController$1) ((BubbleExpandedViewManager) this.mExpandedViewManager.get())).$controller.getClass();
        if (this.mStackView.get() == null) {
            return null;
        }
        if (this.mLayerView.get() == null) {
            return BubbleViewInfo.populate((Context) this.mContext.get(), (BubbleExpandedViewManager) this.mExpandedViewManager.get(), (BubbleTaskViewFactory) this.mTaskViewFactory.get(), (BubblePositioner) this.mPositioner.get(), (BubbleStackView) this.mStackView.get(), this.mIconFactory, this.mBadgeIconFactory, this.mBubble, this.mSkipInflation);
        }
        Context context = (Context) this.mContext.get();
        BubbleExpandedViewManager bubbleExpandedViewManager = (BubbleExpandedViewManager) this.mExpandedViewManager.get();
        BubbleTaskViewFactory bubbleTaskViewFactory = (BubbleTaskViewFactory) this.mTaskViewFactory.get();
        BubblePositioner bubblePositioner = (BubblePositioner) this.mPositioner.get();
        BubbleBarLayerView bubbleBarLayerView = (BubbleBarLayerView) this.mLayerView.get();
        BubbleIconFactory bubbleIconFactory = this.mIconFactory;
        BubbleBadgeIconFactory bubbleBadgeIconFactory = this.mBadgeIconFactory;
        Bubble bubble = this.mBubble;
        boolean z = this.mSkipInflation;
        Executor executor = this.mMainExecutor;
        Executor executor2 = this.mBackgroundExecutor;
        BubbleViewInfo bubbleViewInfo = new BubbleViewInfo();
        if (!z && !bubble.isInflated()) {
            BubbleTaskView orCreateBubbleTaskView = bubble.getOrCreateBubbleTaskView(bubbleTaskViewFactory);
            BubbleBarExpandedView bubbleBarExpandedView = (BubbleBarExpandedView) LayoutInflater.from(context).inflate(R.layout.bubble_bar_expanded_view, (ViewGroup) bubbleBarLayerView, false);
            bubbleViewInfo.bubbleBarExpandedView = bubbleBarExpandedView;
            bubbleBarExpandedView.initialize(bubbleExpandedViewManager, bubblePositioner, false, orCreateBubbleTaskView, executor, executor2, new RegionSamplingProvider() { // from class: com.android.wm.shell.bubbles.BubbleViewInfoTaskLegacy.BubbleViewInfo.1
                @Override // com.android.wm.shell.bubbles.RegionSamplingProvider
                public final RegionSamplingHelper createHelper(BubbleBarExpandedView bubbleBarExpandedView2, BubbleBarExpandedView.AnonymousClass5 anonymousClass5, Executor executor3, Executor executor4) {
                    return new RegionSamplingHelper(bubbleBarExpandedView2, anonymousClass5, executor3, executor4);
                }
            });
        }
        if (!m3218$$Nest$smpopulateCommonInfo(bubbleViewInfo, context, bubble, bubbleIconFactory, bubbleBadgeIconFactory)) {
            return null;
        }
        bubbleViewInfo.flyoutMessage = bubble.mFlyoutMessage;
        return bubbleViewInfo;
    }

    @Override // android.os.AsyncTask
    public final void onPostExecute(Object obj) {
        final BubbleViewInfo bubbleViewInfo = (BubbleViewInfo) obj;
        if (isCancelled() || bubbleViewInfo == null) {
            return;
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleViewInfoTaskLegacy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BubbleViewInfoTaskLegacy bubbleViewInfoTaskLegacy = BubbleViewInfoTaskLegacy.this;
                BubbleViewInfoTaskLegacy.BubbleViewInfo bubbleViewInfo2 = bubbleViewInfo;
                int i = BubbleViewInfoTaskLegacy.$r8$clinit;
                ((BubbleExpandedViewManager$Companion$fromBubbleController$1) ((BubbleExpandedViewManager) bubbleViewInfoTaskLegacy.mExpandedViewManager.get())).$controller.getClass();
                if (bubbleViewInfoTaskLegacy.mStackView.get() != null) {
                    Bubble bubble = bubbleViewInfoTaskLegacy.mBubble;
                    if (!bubble.isInflated()) {
                        bubble.mIconView = bubbleViewInfo2.imageView;
                        bubble.mExpandedView = bubbleViewInfo2.expandedView;
                        bubble.mBubbleBarExpandedView = bubbleViewInfo2.bubbleBarExpandedView;
                    }
                    bubble.mShortcutInfo = bubbleViewInfo2.shortcutInfo;
                    String str = bubbleViewInfo2.appName;
                    bubble.mAppName = str;
                    if (bubble.mTitle == null) {
                        bubble.mTitle = str;
                    }
                    bubble.mFlyoutMessage = bubbleViewInfo2.flyoutMessage;
                    bubble.mBadgeBitmap = bubbleViewInfo2.badgeBitmap;
                    bubble.mRawBadgeBitmap = bubbleViewInfo2.rawBadgeBitmap;
                    bubble.mBubbleBitmap = bubbleViewInfo2.bubbleBitmap;
                    bubble.mDotColor = bubbleViewInfo2.dotColor;
                    bubble.mDotPath = bubbleViewInfo2.dotPath;
                    BubbleExpandedView bubbleExpandedView = bubble.mExpandedView;
                    if (bubbleExpandedView != null) {
                        bubbleExpandedView.update(bubble);
                    }
                    BubbleBarExpandedView bubbleBarExpandedView = bubble.mBubbleBarExpandedView;
                    if (bubbleBarExpandedView != null) {
                        bubbleBarExpandedView.mBubble = bubble;
                        bubbleBarExpandedView.mBubbleTaskViewListener.setBubble(bubble);
                        bubbleBarExpandedView.mMenuViewController.mBubble = bubble;
                    }
                    BadgedImageView badgedImageView = bubble.mIconView;
                    if (badgedImageView != null) {
                        badgedImageView.setRenderedBubble(bubble);
                    }
                }
            }
        });
    }
}
