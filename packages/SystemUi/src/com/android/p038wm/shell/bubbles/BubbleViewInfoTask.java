package com.android.p038wm.shell.bubbles;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.util.Log;
import android.util.PathParser;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.p038wm.shell.bubbles.Bubble;
import com.android.p038wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleViewInfoTask extends AsyncTask {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BubbleBadgeIconFactory mBadgeIconFactory;
    public final Bubble mBubble;
    public final Callback mCallback;
    public final WeakReference mContext;
    public final WeakReference mController;
    public final BubbleIconFactory mIconFactory;
    public final Executor mMainExecutor;
    public final boolean mSkipInflation;
    public final WeakReference mStackView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class BubbleViewInfo {
        public String appName;
        public Bitmap badgeBitmap;
        public Bitmap bubbleBitmap;
        public int dotColor;
        public Path dotPath;
        public BubbleExpandedView expandedView;
        public Bubble.FlyoutMessage flyoutMessage;
        public BadgedImageView imageView;
        public ShortcutInfo shortcutInfo;

        public static BubbleViewInfo populate(Context context, BubbleController bubbleController, BubbleStackView bubbleStackView, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory, Bubble bubble, boolean z) {
            Drawable drawable;
            BubbleViewInfo bubbleViewInfo = new BubbleViewInfo();
            if (!z) {
                if (!(((bubble.mIconView == null || bubble.mExpandedView == null) && bubble.mBubbleBarExpandedView == null) ? false : true)) {
                    LayoutInflater from = LayoutInflater.from(context);
                    BadgedImageView badgedImageView = (BadgedImageView) from.inflate(R.layout.bubble_view, (ViewGroup) bubbleStackView, false);
                    bubbleViewInfo.imageView = badgedImageView;
                    badgedImageView.initialize(bubbleController.getPositioner());
                    BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) from.inflate(R.layout.bubble_expanded_view, (ViewGroup) bubbleStackView, false);
                    bubbleViewInfo.expandedView = bubbleExpandedView;
                    bubbleExpandedView.initialize(bubbleController, bubbleStackView, false);
                }
            }
            ShortcutInfo shortcutInfo = bubble.mShortcutInfo;
            if (shortcutInfo != null) {
                bubbleViewInfo.shortcutInfo = shortcutInfo;
            }
            PackageManager packageManagerForUser = BubbleController.getPackageManagerForUser(bubble.mUser.getIdentifier(), context);
            Drawable drawable2 = null;
            try {
                ApplicationInfo applicationInfo = packageManagerForUser.getApplicationInfo(bubble.mPackageName, 795136);
                if (applicationInfo != null) {
                    bubbleViewInfo.appName = String.valueOf(packageManagerForUser.getApplicationLabel(applicationInfo));
                }
                Drawable applicationIcon = packageManagerForUser.getApplicationIcon(bubble.mPackageName);
                Drawable userBadgedIcon = packageManagerForUser.getUserBadgedIcon(applicationIcon, bubble.mUser);
                ShortcutInfo shortcutInfo2 = bubbleViewInfo.shortcutInfo;
                Icon icon = bubble.mIcon;
                bubbleIconFactory.getClass();
                if (shortcutInfo2 != null) {
                    drawable = ((LauncherApps) context.getSystemService("launcherapps")).getShortcutIconDrawable(shortcutInfo2, context.getResources().getConfiguration().densityDpi);
                } else if (icon != null) {
                    if (icon.getType() == 4 || icon.getType() == 6) {
                        context.grantUriPermission(context.getPackageName(), icon.getUri(), 1);
                    }
                    drawable = icon.loadDrawable(context);
                } else {
                    drawable = null;
                }
                if (drawable != null) {
                    applicationIcon = drawable;
                }
                bubbleViewInfo.badgeBitmap = bubbleBadgeIconFactory.getBadgeBitmap(userBadgedIcon).icon;
                if (bubble.mIsImportantConversation) {
                    bubbleBadgeIconFactory.getBadgeBitmap(userBadgedIcon);
                }
                bubbleViewInfo.bubbleBitmap = bubbleIconFactory.createIconBitmap(bubbleIconFactory.getCircledBubble(applicationIcon, false)).icon;
                Path createPathFromPathData = PathParser.createPathFromPathData(context.getResources().getString(android.R.string.dump_heap_ready_text));
                Matrix matrix = new Matrix();
                float f = new float[1][0];
                matrix.setScale(f, f, 50.0f, 50.0f);
                createPathFromPathData.transform(matrix);
                bubbleViewInfo.dotPath = createPathFromPathData;
                bubbleViewInfo.dotColor = context.getResources().getColor(R.color.sec_bubble_badge_color, null);
                Bubble.FlyoutMessage flyoutMessage = bubble.mFlyoutMessage;
                bubbleViewInfo.flyoutMessage = flyoutMessage;
                if (flyoutMessage != null) {
                    Icon icon2 = flyoutMessage.senderIcon;
                    int i = BubbleViewInfoTask.$r8$clinit;
                    if (icon2 != null) {
                        try {
                            if (icon2.getType() == 4 || icon2.getType() == 6) {
                                context.grantUriPermission(context.getPackageName(), icon2.getUri(), 1);
                            }
                            drawable2 = icon2.loadDrawable(context);
                        } catch (Exception e) {
                            Log.w("Bubbles", "loadSenderAvatar failed: " + e.getMessage());
                        }
                    }
                    flyoutMessage.senderAvatar = drawable2;
                }
                return bubbleViewInfo;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("Bubbles", "Unable to find package: " + bubble.mPackageName);
                return null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onBubbleViewsReady(Bubble bubble);
    }

    public BubbleViewInfoTask(Bubble bubble, Context context, BubbleController bubbleController, BubbleStackView bubbleStackView, BubbleBarLayerView bubbleBarLayerView, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory, boolean z, Callback callback, Executor executor) {
        this.mBubble = bubble;
        this.mContext = new WeakReference(context);
        this.mController = new WeakReference(bubbleController);
        this.mStackView = new WeakReference(bubbleStackView);
        new WeakReference(bubbleBarLayerView);
        this.mIconFactory = bubbleIconFactory;
        this.mBadgeIconFactory = bubbleBadgeIconFactory;
        this.mSkipInflation = z;
        this.mCallback = callback;
        this.mMainExecutor = executor;
    }

    @Override // android.os.AsyncTask
    public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        return doInBackground();
    }

    @Override // android.os.AsyncTask
    public final void onPostExecute(Object obj) {
        BubbleViewInfo bubbleViewInfo = (BubbleViewInfo) obj;
        if (isCancelled() || bubbleViewInfo == null) {
            return;
        }
        this.mMainExecutor.execute(new BubbleViewInfoTask$$ExternalSyntheticLambda0(this, bubbleViewInfo));
    }

    public final BubbleViewInfo doInBackground() {
        ((BubbleController) this.mController.get()).isShowingAsBubbleBar();
        return BubbleViewInfo.populate((Context) this.mContext.get(), (BubbleController) this.mController.get(), (BubbleStackView) this.mStackView.get(), this.mIconFactory, this.mBadgeIconFactory, this.mBubble, this.mSkipInflation);
    }
}
