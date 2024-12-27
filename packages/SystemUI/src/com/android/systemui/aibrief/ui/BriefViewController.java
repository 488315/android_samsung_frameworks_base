package com.android.systemui.aibrief.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.blob.BlobHandle;
import android.app.blob.BlobStoreManager;
import android.content.Context;
import android.graphics.ImageDecoder;
import android.graphics.Point;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.view.OneShotPreDrawListener;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.aibrief.data.NowBarData;
import com.android.systemui.aibrief.log.BriefLogger;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class BriefViewController {
    private static final long FOUR_STAR_ANIMATION_DURATION = 150;
    private static final String PARAM_KEY_EXPIRY_TIME_MILLIS = "expiryTimeMillis";
    private static final String PARAM_KEY_PUBLISHER_LABEL = "publisherLabel";
    private static final String PARAM_KEY_RESOURCE_DIGEST = "resourceDigest";
    private static final String PARAM_KEY_TAG = "tag";
    public static final String SUGGESTION_DATA_KEY = "data";
    public static final String SUGGESTION_EXPAND_REMOTE_VIEWS_KEY = "expandRemoteViews";
    public static final String SUGGESTION_NORMAL_REMOTE_VIEWS_KEY = "normalRemoteViews";
    public static final String TAG = "BriefViewController";
    private static final String URI_AUTHORITY = "blobstore";
    public static final String URI_PATH_ANIMATED_IMAGE = "animatedimage";
    public static final String URI_PATH_DRAWABLE = "drawable";
    private static final String URI_PATH_HANDLE = "handle";
    private static final String URI_SCHEME = "contextualinsight";
    private final Context context;
    private FrameLayout iconContainer;
    private final BriefLogger logger;
    private TextView mainText;
    private GradientDrawable nowBarBg;
    private NowBarData nowBarData;
    private View nowBarView;
    private TextView subText;
    private FrameLayout suggestionContainerWidget;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final PathInterpolator TRANSLATION_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BriefViewController(Context context, BriefLogger briefLogger) {
        this.context = context;
        this.logger = briefLogger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dismissAnimation(final LottieAnimationView lottieAnimationView) {
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(FOUR_STAR_ANIMATION_DURATION);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefViewController$dismissAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FrameLayout frameLayout;
                TextView textView;
                LottieAnimationView.this.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
                frameLayout = this.iconContainer;
                if (frameLayout != null) {
                    frameLayout.setAlpha(1.0f - ((Float) ofFloat.getAnimatedValue()).floatValue());
                }
                textView = this.subText;
                if (textView == null) {
                    return;
                }
                textView.setAlpha(1.0f - ((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.aibrief.ui.BriefViewController$dismissAnimation$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LottieAnimationView.this.setAlpha(0.0f);
                LottieAnimationView.this.setVisibility(4);
            }
        });
        ofFloat.start();
    }

    private final Drawable getDrawableFromUri(String str) {
        try {
            Intrinsics.checkNotNull(str);
            Uri uriFromString = getUriFromString(str);
            return Drawable.createFromStream(uriFromString != null ? this.context.getContentResolver().openInputStream(uriFromString) : null, str);
        } catch (Exception e) {
            BriefLogger briefLogger = this.logger;
            e.printStackTrace();
            briefLogger.e("BriefViewController", "Exception :  " + Unit.INSTANCE);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ GradientDrawable getNowBarBackground$default(BriefViewController briefViewController, NowBarData nowBarData, boolean z, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            list = null;
        }
        return briefViewController.getNowBarBackground(nowBarData, z, list);
    }

    private static final boolean getNowBarBackground$isDark(BriefViewController briefViewController) {
        return (briefViewController.context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    private final Uri getUriFromString(String str) {
        try {
            return Uri.parse(str);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initAnimatedViews(LottieAnimationView lottieAnimationView) {
        TextView textView = this.subText;
        if (textView != null) {
            textView.setAlpha(0.0f);
            TextView textView2 = this.mainText;
            if (textView2 != null) {
                textView2.setTranslationY((textView2.getHeight() / 2) - 10);
            }
        }
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null) {
            frameLayout.setAlpha(0.0f);
        }
        FrameLayout frameLayout2 = this.iconContainer;
        if (frameLayout2 != null) {
            frameLayout2.setVisibility(0);
        }
        if (lottieAnimationView != null) {
            lottieAnimationView.setAlpha(0.0f);
        }
        if (lottieAnimationView == null) {
            return;
        }
        lottieAnimationView.setVisibility(0);
    }

    private final boolean isDarkMode() {
        return (this.context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    private final void setBriefImageURI(ImageView imageView, String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        BlobHandle blobHandle = toBlobHandle(str);
        if (blobHandle == null) {
            imageView.setImageURI(getUriFromString(str));
            return;
        }
        try {
            imageView.setVisibility(8);
            BlobStoreManager blobStoreManager = (BlobStoreManager) this.context.getSystemService(BlobStoreManager.class);
            ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(blobStoreManager != null ? blobStoreManager.openBlob(blobHandle) : null);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(8192, autoCloseInputStream.available()));
                byte[] bArr = new byte[8192];
                for (int read = autoCloseInputStream.read(bArr); read >= 0; read = autoCloseInputStream.read(bArr)) {
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                ImageDecoder.Source createSource = ImageDecoder.createSource(byteArrayOutputStream.toByteArray());
                String lastPathSegment = Uri.parse(str).getLastPathSegment();
                if (lastPathSegment != null) {
                    int hashCode = lastPathSegment.hashCode();
                    if (hashCode == -1837625960) {
                        if (lastPathSegment.equals(URI_PATH_ANIMATED_IMAGE)) {
                            Drawable decodeDrawable = ImageDecoder.decodeDrawable(createSource);
                            AnimatedImageDrawable animatedImageDrawable = decodeDrawable instanceof AnimatedImageDrawable ? (AnimatedImageDrawable) decodeDrawable : null;
                            if (animatedImageDrawable != null) {
                                animatedImageDrawable.setRepeatCount(-1);
                                imageView.setImageDrawable(animatedImageDrawable);
                                animatedImageDrawable.start();
                            }
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(autoCloseInputStream, null);
                        }
                    } else if (hashCode == -826507106 && lastPathSegment.equals(URI_PATH_DRAWABLE)) {
                        Drawable decodeDrawable2 = ImageDecoder.decodeDrawable(createSource);
                        if (!(decodeDrawable2 instanceof Drawable)) {
                            decodeDrawable2 = null;
                        }
                        if (decodeDrawable2 != null) {
                            imageView.setImageDrawable(decodeDrawable2);
                        }
                        Unit unit2 = Unit.INSTANCE;
                        CloseableKt.closeFinally(autoCloseInputStream, null);
                    }
                }
                imageView.setImageURI(getUriFromString(str));
                Unit unit22 = Unit.INSTANCE;
                CloseableKt.closeFinally(autoCloseInputStream, null);
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void setIconBackground(FrameLayout frameLayout, String str) {
        this.logger.d("BriefViewController", "icon bg uri :" + str);
        if (str == null || str.length() == 0) {
            return;
        }
        Drawable drawableFromUri = getDrawableFromUri(str);
        if (drawableFromUri != null) {
            frameLayout.setBackground(drawableFromUri);
        } else {
            this.logger.d("BriefViewController", "icon bg is null");
        }
    }

    private final void showAnimation(final LottieAnimationView lottieAnimationView) {
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(FOUR_STAR_ANIMATION_DURATION);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefViewController$showAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LottieAnimationView.this.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startFourStarAnimation() {
        final LottieAnimationView lottieAnimationView;
        View view = this.nowBarView;
        if (view == null || (lottieAnimationView = (LottieAnimationView) view.findViewById(R.id.ai_brief_now_bar_four_star_image)) == null) {
            return;
        }
        lottieAnimationView.playAnimation();
        showAnimation(lottieAnimationView);
        lottieAnimationView.addAnimatorListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.aibrief.ui.BriefViewController$startFourStarAnimation$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                BriefViewController.this.dismissAnimation(lottieAnimationView);
                BriefViewController.this.translationAnimation();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final android.app.blob.BlobHandle toBlobHandle(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r5 = "contextualinsight://blobstore/handle"
            boolean r5 = r6.startsWith(r5)
            r0 = 0
            if (r5 != 0) goto La
            return r0
        La:
            int r5 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L43
            android.net.Uri r5 = android.net.Uri.parse(r6)     // Catch: java.lang.Throwable -> L43
            java.lang.String r6 = "resourceDigest"
            java.lang.String r6 = r5.getQueryParameter(r6)     // Catch: java.lang.Throwable -> L43
            if (r6 != 0) goto L1a
        L18:
            r5 = r0
            goto L4c
        L1a:
            java.lang.String r1 = "publisherLabel"
            java.lang.String r1 = r5.getQueryParameter(r1)     // Catch: java.lang.Throwable -> L43
            if (r1 != 0) goto L23
            goto L18
        L23:
            java.lang.String r2 = "expiryTimeMillis"
            java.lang.String r2 = r5.getQueryParameter(r2)     // Catch: java.lang.Throwable -> L43
            if (r2 == 0) goto L18
            long r2 = java.lang.Long.parseLong(r2)     // Catch: java.lang.Throwable -> L43
            java.lang.String r4 = "tag"
            java.lang.String r5 = r5.getQueryParameter(r4)     // Catch: java.lang.Throwable -> L43
            if (r5 != 0) goto L39
            goto L18
        L39:
            r4 = 2
            byte[] r6 = android.util.Base64.decode(r6, r4)     // Catch: java.lang.Throwable -> L43
            android.app.blob.BlobHandle r5 = android.app.blob.BlobHandle.createWithSha256(r6, r1, r2, r5)     // Catch: java.lang.Throwable -> L43
            goto L4c
        L43:
            r5 = move-exception
            int r6 = kotlin.Result.$r8$clinit
            kotlin.Result$Failure r6 = new kotlin.Result$Failure
            r6.<init>(r5)
            r5 = r6
        L4c:
            boolean r6 = r5 instanceof kotlin.Result.Failure
            if (r6 == 0) goto L51
            goto L52
        L51:
            r0 = r5
        L52:
            android.app.blob.BlobHandle r0 = (android.app.blob.BlobHandle) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.aibrief.ui.BriefViewController.toBlobHandle(java.lang.String):android.app.blob.BlobHandle");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void translationAnimation() {
        final TextView textView = this.mainText;
        if (textView != null) {
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(textView.getTranslationY(), 0.0f);
            ofFloat.setDuration(FOUR_STAR_ANIMATION_DURATION);
            ofFloat.setInterpolator(TRANSLATION_INTERPOLATOR);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefViewController$translationAnimation$1$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    textView.setTranslationY(((Float) ofFloat.getAnimatedValue()).floatValue());
                }
            });
            ofFloat.start();
        }
    }

    public final View createBriefNowBarView(NowBarData nowBarData) {
        LinearLayout linearLayout;
        Unit unit;
        Unit unit2;
        FrameLayout frameLayout = null;
        final View inflate = View.inflate(this.context, R.layout.ai_brief_nowbar_layout, null);
        this.nowBarView = inflate;
        this.nowBarData = nowBarData;
        if (nowBarData != null && (linearLayout = (LinearLayout) inflate.findViewById(R.id.ai_brief_now_bar_container)) != null) {
            if (LsRune.LOCKUI_NOW_BAR_SUPPORT_GUIDING_EFFECT) {
                this.logger.d("BriefViewController", "bg should be empty by LOCKUI_NOW_BAR_SUPPORT_GUIDING_EFFECT");
            } else {
                GradientDrawable nowBarBackground$default = getNowBarBackground$default(this, nowBarData, false, null, 6, null);
                if (nowBarBackground$default != null) {
                    linearLayout.setBackground(nowBarBackground$default);
                } else {
                    nowBarBackground$default = null;
                }
                this.nowBarBg = nowBarBackground$default;
            }
            TextView textView = (TextView) linearLayout.requireViewById(R.id.ai_brief_main_text);
            textView.setText(nowBarData.getTitle());
            this.mainText = textView;
            TextView textView2 = (TextView) linearLayout.requireViewById(R.id.ai_brief_sub_text);
            if (nowBarData.getSubTitle() != null) {
                this.subText = textView2;
                textView2.setText(nowBarData.getSubTitle());
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                textView2.setVisibility(8);
            }
            LottieAnimationView lottieAnimationView = (LottieAnimationView) linearLayout.findViewById(R.id.ai_brief_now_bar_image);
            if (lottieAnimationView != null) {
                String icon = nowBarData.getIcon();
                if (icon != null) {
                    setBriefImageURI(lottieAnimationView, icon);
                    unit2 = Unit.INSTANCE;
                } else {
                    unit2 = null;
                }
                if (unit2 == null) {
                    lottieAnimationView.setVisibility(8);
                }
            }
            FrameLayout frameLayout2 = (FrameLayout) linearLayout.findViewById(R.id.ai_brief_now_bar_icon_container);
            if (frameLayout2 != null) {
                if (isDarkMode()) {
                    String iconBackgroundForDark = nowBarData.getIconBackgroundForDark();
                    if (iconBackgroundForDark != null) {
                        setIconBackground(frameLayout2, iconBackgroundForDark);
                    }
                } else {
                    String iconBackground = nowBarData.getIconBackground();
                    if (iconBackground != null) {
                        setIconBackground(frameLayout2, iconBackground);
                    }
                }
                frameLayout = frameLayout2;
            }
            this.iconContainer = frameLayout;
        }
        Intrinsics.checkNotNull(inflate);
        OneShotPreDrawListener.add(inflate, new Runnable() { // from class: com.android.systemui.aibrief.ui.BriefViewController$createBriefNowBarView$$inlined$doOnPreDraw$1
            @Override // java.lang.Runnable
            public final void run() {
                this.initAnimatedViews((LottieAnimationView) inflate.findViewById(R.id.ai_brief_now_bar_four_star_image));
                this.startFourStarAnimation();
            }
        });
        return inflate;
    }

    public final RemoteViews createExpandRemoteView(Bundle bundle) {
        return (RemoteViews) bundle.getParcelable(SUGGESTION_EXPAND_REMOTE_VIEWS_KEY, RemoteViews.class);
    }

    public final View createFullView() {
        Unit unit = null;
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.context).inflate(R.layout.suggestion_container_widget, (ViewGroup) null, false);
        this.suggestionContainerWidget = frameLayout;
        frameLayout.setVisibility(8);
        GradientDrawable gradientDrawable = this.nowBarBg;
        if (gradientDrawable != null) {
            frameLayout.setBackground(gradientDrawable);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            frameLayout.setBackgroundResource(R.drawable.brief_morning_bg);
        }
        FrameLayout frameLayout2 = this.suggestionContainerWidget;
        Intrinsics.checkNotNull(frameLayout2);
        return frameLayout2;
    }

    public final RemoteViews createNormalRemoteView(Bundle bundle) {
        return (RemoteViews) bundle.getParcelable(SUGGESTION_NORMAL_REMOTE_VIEWS_KEY, RemoteViews.class);
    }

    public final GradientDrawable getNowBarBackground(NowBarData nowBarData, boolean z, List<Integer> list) {
        ArrayList<Integer> background;
        if (z) {
            if (getNowBarBackground$isDark(this)) {
                if (nowBarData != null) {
                    background = nowBarData.getBackgroundForDark();
                    list = background;
                }
                list = null;
            } else {
                if (nowBarData != null) {
                    background = nowBarData.getBackground();
                    list = background;
                }
                list = null;
            }
        }
        ArrayList arrayList = new ArrayList();
        if (LsRune.LOCKUI_NOW_BAR_SUPPORT_GUIDING_EFFECT) {
            if (list != null) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((Number) it.next()).intValue()));
                }
            }
        } else if (list != null) {
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                arrayList.add(Integer.valueOf(((Number) it2.next()).intValue() | (-16777216)));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, CollectionsKt___CollectionsKt.toIntArray(arrayList));
    }

    public final void hideContainer() {
        FrameLayout frameLayout = this.suggestionContainerWidget;
        if (frameLayout == null) {
            return;
        }
        frameLayout.setAlpha(0.0f);
    }

    public final void setNowBarBackground(GradientDrawable gradientDrawable) {
        Unit unit;
        View view = this.nowBarView;
        if (view != null) {
            if (gradientDrawable != null) {
                view.setBackground(gradientDrawable);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                view.setBackgroundResource(R.drawable.brief_morning_bg);
            }
        }
    }

    public final void setNowBarIconBackgroundOnUpdate(NowBarData nowBarData) {
        View view;
        FrameLayout frameLayout;
        this.logger.d("BriefViewController", "set icon bg on now bar update");
        if (nowBarData == null || (view = this.nowBarView) == null || (frameLayout = (FrameLayout) view.findViewById(R.id.ai_brief_now_bar_icon_container)) == null) {
            return;
        }
        if (isDarkMode()) {
            String iconBackgroundForDark = nowBarData.getIconBackgroundForDark();
            if (iconBackgroundForDark != null) {
                setIconBackground(frameLayout, iconBackgroundForDark);
                return;
            }
            return;
        }
        String iconBackground = nowBarData.getIconBackground();
        if (iconBackground != null) {
            setIconBackground(frameLayout, iconBackground);
        }
    }

    public final void showCircleAnimation(final Runnable runnable) {
        Unit unit;
        FrameLayout frameLayout = this.suggestionContainerWidget;
        if (frameLayout == null) {
            unit = null;
        } else {
            if (!frameLayout.isAttachedToWindow()) {
                this.logger.w("BriefViewController", "R.layout.suggestion_container_widget is not attached");
                runnable.run();
                return;
            }
            View findViewById = frameLayout.findViewById(R.id.suggestion_container);
            if (findViewById == null || !findViewById.isAttachedToWindow()) {
                this.logger.w("BriefViewController", "R.id.suggestion_container is not attached");
                runnable.run();
                return;
            }
            frameLayout.setVisibility(0);
            Object systemService = this.context.getSystemService((Class<Object>) WindowManager.class);
            Intrinsics.checkNotNull(systemService);
            Display defaultDisplay = ((WindowManager) systemService).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            int i = point.x;
            int i2 = i / 2;
            int i3 = point.y;
            int i4 = i3 - 150;
            float hypot = (float) Math.hypot(i, i3);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(findViewById, "alpha", 0.0f, 1.0f);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(new LinearInterpolator());
            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(findViewById, i2, i4, 0.0f, hypot);
            createCircularReveal.setDuration(400L);
            createCircularReveal.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.1f, 1.0f));
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(createCircularReveal, ofFloat);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.aibrief.ui.BriefViewController$showCircleAnimation$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BriefLogger briefLogger;
                    briefLogger = BriefViewController.this.logger;
                    briefLogger.d("BriefViewController", "showCircleAnimation onAnimationEnd");
                    runnable.run();
                }
            });
            animatorSet.start();
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            runnable.run();
        }
    }
}
