package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.util.PathParser;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.bubbles.bar.BubbleBarLayerView;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.bubbles.FlyoutDrawableLoader;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class BubbleViewInfoTask {
    public final BubbleBadgeIconFactory mBadgeIconFactory;
    public final Executor mBgExecutor;
    public final Bubble mBubble;
    public final Callback mCallback;
    public final WeakReference mContext;
    public final WeakReference mExpandedViewManager;
    public final BubbleIconFactory mIconFactory;
    public final WeakReference mLayerView;
    public final Executor mMainExecutor;
    public final WeakReference mPositioner;
    public final boolean mSkipInflation;
    public final WeakReference mStackView;
    public final WeakReference mTaskViewFactory;
    public final AtomicBoolean mStarted = new AtomicBoolean();
    public final AtomicBoolean mCancelled = new AtomicBoolean();
    public final AtomicBoolean mFinished = new AtomicBoolean();

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
        public BubbleTaskView taskView;

        public static BubbleViewInfo populate(Context context, BubbleTaskViewFactory bubbleTaskViewFactory, BubblePositioner bubblePositioner, BubbleStackView bubbleStackView, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory, Bubble bubble, boolean z) {
            BubbleViewInfo bubbleViewInfo = new BubbleViewInfo();
            if (!z && !bubble.isInflated()) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, 8082017669105793175L, 0, String.valueOf(bubble.mKey));
                }
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
                BadgedImageView badgedImageView = (BadgedImageView) layoutInflaterFrom.inflate(R.layout.bubble_view, (ViewGroup) bubbleStackView, false);
                bubbleViewInfo.imageView = badgedImageView;
                badgedImageView.initialize(bubblePositioner);
                bubbleViewInfo.taskView = bubble.getOrCreateBubbleTaskView(bubbleTaskViewFactory);
                bubbleViewInfo.expandedView = (BubbleExpandedView) layoutInflaterFrom.inflate(R.layout.bubble_expanded_view, (ViewGroup) bubbleStackView, false);
            }
            if (!BubbleViewInfoTask.m3233$$Nest$smpopulateCommonInfo(bubbleViewInfo, context, bubble, bubbleIconFactory, bubbleBadgeIconFactory)) {
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

    public interface Callback {
        void onBubbleViewsReady(Bubble bubble);
    }

    /* renamed from: -$$Nest$smpopulateCommonInfo, reason: not valid java name */
    public static boolean m3233$$Nest$smpopulateCommonInfo(BubbleViewInfo bubbleViewInfo, Context context, Bubble bubble, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory) throws PackageManager.NameNotFoundException {
        Drawable bubbleDrawable;
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
                bubbleDrawable = BubbleIconFactory.getBubbleDrawable(context, shortcutInfo2, icon);
            } catch (Exception unused) {
                Log.w("Bubbles", "Exception creating icon for the bubble: " + bubble.mKey);
                bubbleDrawable = null;
            }
            if (bubbleDrawable != null) {
                applicationIcon = bubbleDrawable;
            }
            Bitmap bitmap = bubbleBadgeIconFactory.getBadgeBitmap(userBadgedIcon).icon;
            bubbleViewInfo.badgeBitmap = bitmap;
            if (bubble.mIsImportantConversation) {
                bitmap = bubbleBadgeIconFactory.getBadgeBitmap(userBadgedIcon).icon;
            }
            bubbleViewInfo.rawBadgeBitmap = bitmap;
            bubbleViewInfo.bubbleBitmap = bubbleIconFactory.createIconBitmap(bubbleIconFactory.getCircledBubble(applicationIcon, false)).icon;
            Path pathCreatePathFromPathData = PathParser.createPathFromPathData(context.getResources().getString(android.R.string.eventTypeCustom));
            Matrix matrix = new Matrix();
            float f = new float[1][0];
            matrix.setScale(f, f, 50.0f, 50.0f);
            pathCreatePathFromPathData.transform(matrix);
            bubbleViewInfo.dotPath = pathCreatePathFromPathData;
            bubbleViewInfo.dotColor = context.getResources().getColor(R.color.sec_bubble_badge_color, null);
            return true;
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.w("Bubbles", "Unable to find package: " + bubble.mPackageName);
            return false;
        }
    }

    public BubbleViewInfoTask(Bubble bubble, Context context, BubbleExpandedViewManager bubbleExpandedViewManager, BubbleTaskViewFactory bubbleTaskViewFactory, BubblePositioner bubblePositioner, BubbleStackView bubbleStackView, BubbleBarLayerView bubbleBarLayerView, BubbleIconFactory bubbleIconFactory, BubbleBadgeIconFactory bubbleBadgeIconFactory, boolean z, Callback callback, Executor executor, Executor executor2) {
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
        this.mCallback = callback;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
    }

    public final BubbleViewInfo loadViewInfo() {
        ((BubbleExpandedViewManager$Companion$fromBubbleController$1) ((BubbleExpandedViewManager) this.mExpandedViewManager.get())).$controller.getClass();
        if (!(this.mStackView.get() != null)) {
            return null;
        }
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1];
        Bubble bubble = this.mBubble;
        if (z) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, 8956445472117109952L, 0, String.valueOf(bubble.mKey));
        }
        if (this.mLayerView.get() == null) {
            return BubbleViewInfo.populate((Context) this.mContext.get(), (BubbleTaskViewFactory) this.mTaskViewFactory.get(), (BubblePositioner) this.mPositioner.get(), (BubbleStackView) this.mStackView.get(), this.mIconFactory, this.mBadgeIconFactory, this.mBubble, this.mSkipInflation);
        }
        Context context = (Context) this.mContext.get();
        BubbleTaskViewFactory bubbleTaskViewFactory = (BubbleTaskViewFactory) this.mTaskViewFactory.get();
        BubbleBarLayerView bubbleBarLayerView = (BubbleBarLayerView) this.mLayerView.get();
        BubbleViewInfo bubbleViewInfo = new BubbleViewInfo();
        if (!this.mSkipInflation && !bubble.isInflated()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, -4859492011243720687L, 0, String.valueOf(bubble.mKey));
            }
            bubbleViewInfo.taskView = bubble.getOrCreateBubbleTaskView(bubbleTaskViewFactory);
            bubbleViewInfo.bubbleBarExpandedView = (BubbleBarExpandedView) LayoutInflater.from(context).inflate(R.layout.bubble_bar_expanded_view, (ViewGroup) bubbleBarLayerView, false);
        }
        if (!m3233$$Nest$smpopulateCommonInfo(bubbleViewInfo, context, bubble, this.mIconFactory, this.mBadgeIconFactory)) {
            return null;
        }
        bubbleViewInfo.flyoutMessage = bubble.mFlyoutMessage;
        return bubbleViewInfo;
    }

    public final void updateViewInfo(BubbleViewInfo bubbleViewInfo) {
        if (bubbleViewInfo != null) {
            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) ((BubbleExpandedViewManager) this.mExpandedViewManager.get())).$controller.getClass();
            if (this.mStackView.get() != null) {
                boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1];
                Bubble bubble = this.mBubble;
                if (z) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, -5831295297072777918L, 0, String.valueOf(bubble.mKey));
                }
                if (!bubble.isInflated()) {
                    BubbleExpandedView bubbleExpandedView = bubbleViewInfo.expandedView;
                    String str = bubble.mKey;
                    if (bubbleExpandedView != null) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, 6848726834197248998L, 0, String.valueOf(str));
                        }
                        bubbleViewInfo.expandedView.initialize((BubbleExpandedViewManager) this.mExpandedViewManager.get(), (BubbleStackView) this.mStackView.get(), (BubblePositioner) this.mPositioner.get(), false, bubbleViewInfo.taskView);
                    } else if (bubbleViewInfo.bubbleBarExpandedView != null) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_BUBBLES, -6935383456668109712L, 0, String.valueOf(str));
                        }
                        bubbleViewInfo.bubbleBarExpandedView.initialize((BubbleExpandedViewManager) this.mExpandedViewManager.get(), (BubblePositioner) this.mPositioner.get(), false, bubbleViewInfo.taskView, this.mMainExecutor, this.mBgExecutor, new RegionSamplingProvider(this) { // from class: com.android.wm.shell.bubbles.BubbleViewInfoTask.1
                            @Override // com.android.wm.shell.bubbles.RegionSamplingProvider
                            public final RegionSamplingHelper createHelper(BubbleBarExpandedView bubbleBarExpandedView, BubbleBarExpandedView.AnonymousClass5 anonymousClass5, Executor executor, Executor executor2) {
                                return new RegionSamplingHelper(bubbleBarExpandedView, anonymousClass5, executor, executor2);
                            }
                        });
                    }
                }
                if (!bubble.isInflated()) {
                    bubble.mIconView = bubbleViewInfo.imageView;
                    bubble.mExpandedView = bubbleViewInfo.expandedView;
                    bubble.mBubbleBarExpandedView = bubbleViewInfo.bubbleBarExpandedView;
                }
                bubble.mShortcutInfo = bubbleViewInfo.shortcutInfo;
                String str2 = bubbleViewInfo.appName;
                bubble.mAppName = str2;
                if (bubble.mTitle == null) {
                    bubble.mTitle = str2;
                }
                bubble.mFlyoutMessage = bubbleViewInfo.flyoutMessage;
                bubble.mBadgeBitmap = bubbleViewInfo.badgeBitmap;
                bubble.mRawBadgeBitmap = bubbleViewInfo.rawBadgeBitmap;
                bubble.mBubbleBitmap = bubbleViewInfo.bubbleBitmap;
                bubble.mDotColor = bubbleViewInfo.dotColor;
                bubble.mDotPath = bubbleViewInfo.dotPath;
                BubbleExpandedView bubbleExpandedView2 = bubble.mExpandedView;
                if (bubbleExpandedView2 != null) {
                    bubbleExpandedView2.update(bubble);
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
                Callback callback = this.mCallback;
                if (callback != null) {
                    callback.onBubbleViewsReady(bubble);
                }
            }
        }
    }
}
