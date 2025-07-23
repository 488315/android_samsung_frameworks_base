package android.app.jank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes.dex */
public final class AppJankStats {
    public static final String WIDGET_CATEGORY_ANIMATION = "animation";
    public static final String WIDGET_CATEGORY_KEYBOARD = "keyboard";
    public static final String WIDGET_CATEGORY_MEDIA = "media";
    public static final String WIDGET_CATEGORY_NAVIGATION = "navigation";
    public static final String WIDGET_CATEGORY_OTHER = "other";
    public static final String WIDGET_CATEGORY_SCROLL = "scroll";
    public static final String WIDGET_CATEGORY_UNSPECIFIED = "unspecified";
    public static final String WIDGET_STATE_ANIMATING = "animating";
    public static final String WIDGET_STATE_DRAGGING = "dragging";
    public static final String WIDGET_STATE_FLINGING = "flinging";
    public static final String WIDGET_STATE_NONE = "none";
    public static final String WIDGET_STATE_PLAYBACK = "playback";
    public static final String WIDGET_STATE_PREDICTIVE_BACK = "predictive_back";
    public static final String WIDGET_STATE_SCROLLING = "scrolling";
    public static final String WIDGET_STATE_SWIPING = "swiping";
    public static final String WIDGET_STATE_TAPPING = "tapping";
    public static final String WIDGET_STATE_UNSPECIFIED = "unspecified";
    public static final String WIDGET_STATE_ZOOMING = "zooming";
    private long mJankyFrames;
    private String mNavigationComponent;
    private RelativeFrameTimeHistogram mRelativeFrameTimeHistogram;
    private long mTotalFrames;
    private int mUid;
    private String mWidgetCategory;
    private String mWidgetId;
    private String mWidgetState;

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WidgetCategory {
    }

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WidgetState {
    }

    public AppJankStats(int i, String str, String str2, String str3, String str4, long j, long j2, RelativeFrameTimeHistogram relativeFrameTimeHistogram) {
        this.mUid = i;
        this.mWidgetId = str;
        this.mNavigationComponent = str2;
        this.mWidgetCategory = str3 == null ? "unspecified" : str3;
        this.mWidgetState = str4 == null ? "unspecified" : str4;
        this.mTotalFrames = j;
        this.mJankyFrames = j2;
        this.mRelativeFrameTimeHistogram = relativeFrameTimeHistogram;
    }

    public int getUid() {
        return this.mUid;
    }

    public String getWidgetId() {
        return this.mWidgetId;
    }

    public String getWidgetCategory() {
        return this.mWidgetCategory;
    }

    public String getWidgetState() {
        return this.mWidgetState;
    }

    public long getJankyFrameCount() {
        return this.mJankyFrames;
    }

    public long getTotalFrameCount() {
        return this.mTotalFrames;
    }

    public RelativeFrameTimeHistogram getRelativeFrameTimeHistogram() {
        return this.mRelativeFrameTimeHistogram;
    }

    public String getNavigationComponent() {
        return this.mNavigationComponent;
    }
}
