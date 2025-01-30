package android.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.LoadedApk;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.loader.ResourcesLoader;
import android.content.res.loader.ResourcesProvider;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.VectorDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.StrictMode;
import android.os.UserHandle;
import android.system.Os;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.IntArray;
import android.util.Log;
import android.util.LongArray;
import android.util.Pair;
import android.util.SizeF;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import com.android.internal.C4337R;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.util.Preconditions;
import com.samsung.android.cocktailbar.CocktailHostView;
import com.samsung.android.rune.ViewRune;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class RemoteViews implements Parcelable, LayoutInflater.Filter {
    private static final int ATTRIBUTE_REFLECTION_ACTION_TAG = 32;
    private static final int BITMAP_REFLECTION_ACTION_TAG = 12;
    private static final int COMPLEX_UNIT_DIMENSION_REFLECTION_ACTION_TAG = 25;
    public static final String EXTRA_CHECKED = "android.widget.extra.CHECKED";
    static final String EXTRA_REMOTEADAPTER_APPWIDGET_ID = "remoteAdapterAppWidgetId";
    static final String EXTRA_REMOTEADAPTER_COCKTAIL = "remoteAdapterCocktail";
    static final String EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND = "remoteAdapterOnLightBackground";
    public static final String EXTRA_SHARED_ELEMENT_BOUNDS = "android.widget.extra.SHARED_ELEMENT_BOUNDS";
    static final int FLAG_MASK_TO_PROPAGATE = 6;
    public static final int FLAG_REAPPLY_DISALLOWED = 1;
    public static final int FLAG_USE_LIGHT_BACKGROUND_LAYOUT = 4;
    public static final int FLAG_WIDGET_IS_COLLECTION_CHILD = 2;
    private static final int LAYOUT_PARAM_ACTION_TAG = 19;
    private static final String LOG_TAG = "RemoteViews";
    public static final int MARGIN_BOTTOM = 3;
    public static final int MARGIN_END = 5;
    public static final int MARGIN_LEFT = 0;
    public static final int MARGIN_RIGHT = 2;
    public static final int MARGIN_START = 4;
    public static final int MARGIN_TOP = 1;
    private static final int MAX_INIT_VIEW_COUNT = 16;
    private static final int MAX_NESTED_VIEWS = 10;
    private static final int MODE_HAS_LANDSCAPE_AND_PORTRAIT = 1;
    private static final int MODE_HAS_SIZED_REMOTEVIEWS = 2;
    private static final int MODE_NORMAL = 0;
    private static final int NIGHT_MODE_REFLECTION_ACTION_TAG = 30;
    private static final int OVERRIDE_TEXT_COLORS_TAG = 20;
    private static final int REFLECTION_ACTION_TAG = 2;
    private static final int REMOVE_FROM_PARENT_ACTION_TAG = 23;
    private static final int RESOURCE_REFLECTION_ACTION_TAG = 24;
    private static final int SEM_ANIMATION_ACTION_TAG = 107;
    public static final String SEM_EXTRA_IS_CHECKED = "isChecked";
    public static final String SEM_EXTRA_IS_UP = "isUp";
    public static final String SEM_EXTRA_X_POSITION = "x_position";
    public static final String SEM_EXTRA_Y_POSITION = "y_position";
    private static final int SEM_SET_BLUR_INFO_TAG = 105;
    private static final int SEM_SET_ON_CHECKED_CHANGED_PENDING_INTENT_TAG = 104;
    private static final int SEM_SET_ON_LONG_CLICK_DRAGABLE_TAG = 102;
    private static final int SEM_SET_ON_LONG_CLICK_PENDING_INTENT_TAG = 100;
    private static final int SEM_SET_ON_LONG_CLICK_PENDING_INTENT_TEMPLATE_TAG = 101;
    private static final int SEM_SET_ON_TOUCH_PENDING_INTENT_TAG = 103;
    private static final int SEM_SET_TEXT_VIEW_SHADOW_ACTION_TAG = 109;
    private static final int SEM_SET_VECTOR_DRAWABLE_PATH_COLOR_TAG = 108;
    private static final int SEM_VIEW_OBJECT_ANIMATOR_ACTION_TAG = 106;
    private static final int SET_COMPOUND_BUTTON_CHECKED_TAG = 26;
    private static final int SET_DRAWABLE_TINT_TAG = 3;
    private static final int SET_EMPTY_VIEW_ACTION_TAG = 6;
    private static final int SET_INT_TAG_TAG = 22;
    private static final int SET_ON_CHECKED_CHANGE_RESPONSE_TAG = 29;
    private static final int SET_ON_CLICK_RESPONSE_TAG = 1;
    private static final int SET_PENDING_INTENT_TEMPLATE_TAG = 8;
    private static final int SET_RADIO_GROUP_CHECKED = 27;
    private static final int SET_REMOTE_COLLECTION_ITEMS_ADAPTER_TAG = 31;
    private static final int SET_REMOTE_INPUTS_ACTION_TAG = 18;
    private static final int SET_REMOTE_VIEW_ADAPTER_INTENT_TAG = 10;
    private static final int SET_REMOTE_VIEW_ADAPTER_LIST_TAG = 15;
    private static final int SET_RIPPLE_DRAWABLE_COLOR_TAG = 21;
    private static final int SET_VIEW_OUTLINE_RADIUS_TAG = 28;
    private static final int SFE_STARTING_TAG = 41;
    private static final int TEXT_VIEW_DRAWABLE_ACTION_TAG = 11;
    private static final int TEXT_VIEW_SIZE_ACTION_TAG = 13;
    static final int VALUE_TYPE_ATTRIBUTE = 4;
    static final int VALUE_TYPE_COMPLEX_UNIT = 2;
    static final int VALUE_TYPE_RAW = 1;
    static final int VALUE_TYPE_RESOURCE = 3;
    static final int VALUE_TYPE_VALUE_ANIMATOR = 101;
    private static final int VIEW_CONTENT_NAVIGATION_TAG = 5;
    private static final int VIEW_GROUP_ACTION_ADD_TAG = 4;
    private static final int VIEW_GROUP_ACTION_REMOVE_TAG = 7;
    private static final int VIEW_PADDING_ACTION_TAG = 14;
    private boolean isProductDEV;
    private ArrayList<Action> mActions;
    private final Object mActionsLock;
    private boolean mAllowOtherRootParent;
    private int mAppWidgetId;
    public ApplicationInfo mApplication;
    private ApplicationInfoCache mApplicationInfoCache;
    private int mApplyFlags;
    private BitmapCache mBitmapCache;
    private Map<Class, Object> mClassCookies;
    private SizeF mIdealSize;
    private boolean mIsAllowPendintIntentInCollection;
    private boolean mIsForcedOrientation;
    private boolean mIsPortrait;
    private boolean mIsRoot;
    private RemoteViews mLandscape;
    private int mLayoutId;
    private int mLightBackgroundLayoutId;
    private RemoteViews mPortrait;
    private long mProviderInstanceId;
    private List<RemoteViews> mSizedRemoteViews;
    private int mViewId;
    private static final Parcel.ReadWriteHelper ALTERNATIVE_DEFAULT = new Parcel.ReadWriteHelper();
    private static final LayoutInflater.Filter INFLATER_FILTER = new LayoutInflater.Filter() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda0
        @Override // android.view.LayoutInflater.Filter
        public final boolean onLoadClass(Class cls) {
            boolean isAnnotationPresent;
            isAnnotationPresent = cls.isAnnotationPresent(RemoteViews.RemoteView.class);
            return isAnnotationPresent;
        }
    };
    private static final InteractionHandler DEFAULT_INTERACTION_HANDLER = new InteractionHandler() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda1
        @Override // android.widget.RemoteViews.InteractionHandler
        public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
            boolean startPendingIntent;
            startPendingIntent = RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
            return startPendingIntent;
        }
    };
    private static final ArrayMap<MethodKey, MethodArgs> sMethods = new ArrayMap<>();
    private static final MethodKey sLookupKey = new MethodKey();
    private static final Action ACTION_NOOP = new RuntimeAction() { // from class: android.widget.RemoteViews.1
        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
        }
    };
    public static final Parcelable.Creator<RemoteViews> CREATOR = new Parcelable.Creator<RemoteViews>() { // from class: android.widget.RemoteViews.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteViews createFromParcel(Parcel parcel) {
            return new RemoteViews(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RemoteViews[] newArray(int size) {
            return new RemoteViews[size];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplyFlags {
    }

    public interface InteractionHandler {
        boolean onInteraction(View view, PendingIntent pendingIntent, RemoteResponse remoteResponse);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MarginType {
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RemoteView {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface ValueType {
    }

    public void hidden_semSetAllowOtherRootParent(boolean enable, int appwidgetid) {
        this.mAllowOtherRootParent = enable;
        this.mAppWidgetId = appwidgetid;
        this.mIsAllowPendintIntentInCollection = enable;
    }

    public void setRemoteInputs(int viewId, RemoteInput[] remoteInputs) {
        this.mActions.add(new SetRemoteInputsAction(viewId, remoteInputs));
    }

    public void reduceImageSizes(int maxWidth, int maxHeight) {
        ArrayList<Bitmap> cache = this.mBitmapCache.mBitmaps;
        for (int i = 0; i < cache.size(); i++) {
            Bitmap bitmap = cache.get(i);
            cache.set(i, Icon.scaleDownIfNecessary(bitmap, maxWidth, maxHeight));
        }
    }

    public void overrideTextColors(int textColor) {
        addAction(new OverrideTextColorsAction(textColor));
    }

    public void setIntTag(int viewId, int key, int tag) {
        addAction(new SetIntTagAction(viewId, key, tag));
    }

    public void semSetViewObjectAnimator(int viewId, int animatorId) {
        addAction(new ViewObjectAnimatorAction(viewId, animatorId));
    }

    public void addFlags(int flags) {
        this.mApplyFlags |= flags;
        int flagsToPropagate = flags & 6;
        if (flagsToPropagate != 0) {
            if (hasSizedRemoteViews()) {
                for (RemoteViews remoteView : this.mSizedRemoteViews) {
                    remoteView.addFlags(flagsToPropagate);
                }
                return;
            }
            if (hasLandscapeAndPortraitLayouts()) {
                this.mLandscape.addFlags(flagsToPropagate);
                this.mPortrait.addFlags(flagsToPropagate);
            }
        }
    }

    public boolean hasFlags(int flag) {
        return (this.mApplyFlags & flag) == flag;
    }

    static class MethodKey {
        public String methodName;
        public Class paramClass;
        public Class targetClass;

        MethodKey() {
        }

        public boolean equals(Object o) {
            if (!(o instanceof MethodKey)) {
                return false;
            }
            MethodKey p = (MethodKey) o;
            return Objects.equals(p.targetClass, this.targetClass) && Objects.equals(p.paramClass, this.paramClass) && Objects.equals(p.methodName, this.methodName);
        }

        public int hashCode() {
            return (Objects.hashCode(this.targetClass) ^ Objects.hashCode(this.paramClass)) ^ Objects.hashCode(this.methodName);
        }

        public void set(Class targetClass, Class paramClass, String methodName) {
            this.targetClass = targetClass;
            this.paramClass = paramClass;
            this.methodName = methodName;
        }
    }

    static class MethodArgs {
        public MethodHandle asyncMethod;
        public String asyncMethodName;
        public MethodHandle syncMethod;

        MethodArgs() {
        }
    }

    public static class ActionException extends RuntimeException {
        public ActionException(Exception ex) {
            super(ex);
        }

        public ActionException(String message) {
            super(message);
        }

        public ActionException(Throwable t) {
            super(t);
        }
    }

    private static abstract class Action implements Parcelable {
        public static final int MERGE_APPEND = 1;
        public static final int MERGE_IGNORE = 2;
        public static final int MERGE_REPLACE = 0;
        int viewId;

        public abstract void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException;

        public abstract int getActionTag();

        private Action() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void setHierarchyRootData(HierarchyRootData root) {
        }

        public int mergeBehavior() {
            return 0;
        }

        public String getUniqueKey() {
            return getActionTag() + Session.SESSION_SEPARATION_CHAR_CHILD + this.viewId;
        }

        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            return this;
        }

        public boolean prefersAsyncApply() {
            return false;
        }

        public void visitUris(Consumer<Uri> visitor) {
        }

        public void clear() {
        }
    }

    private static abstract class RuntimeAction extends Action {
        private RuntimeAction() {
            super();
        }

        @Override // android.widget.RemoteViews.Action
        public final int getActionTag() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel dest, int flags) {
            throw new UnsupportedOperationException();
        }
    }

    public void mergeRemoteViews(RemoteViews newRv) {
        if (newRv == null) {
            return;
        }
        RemoteViews copy = new RemoteViews(newRv);
        HashMap<String, Action> map = new HashMap<>();
        if (this.mActions == null) {
            this.mActions = new ArrayList<>();
        }
        synchronized (this.mActionsLock) {
            int count = this.mActions.size();
            for (int i = 0; i < count; i++) {
                Action a = this.mActions.get(i);
                map.put(a.getUniqueKey(), a);
            }
            ArrayList<Action> newActions = copy.mActions;
            if (newActions == null) {
                return;
            }
            newActions.size();
            HashMap<String, Action> newMap = new HashMap<>();
            ArrayList<Action> duplicatedActions = new ArrayList<>();
            Iterator<Action> it = newActions.iterator();
            while (it.hasNext()) {
                Action a2 = it.next();
                String key = a2.getUniqueKey();
                if (!newMap.containsKey(key)) {
                    newMap.put(key, a2);
                } else if (a2.mergeBehavior() == 0) {
                    duplicatedActions.add(newMap.get(key));
                    newMap.put(key, a2);
                }
            }
            newActions.removeAll(duplicatedActions);
            duplicatedActions.clear();
            newMap.clear();
            int count2 = newActions.size();
            for (int i2 = 0; i2 < count2; i2++) {
                Action a3 = newActions.get(i2);
                String key2 = newActions.get(i2).getUniqueKey();
                int mergeBehavior = newActions.get(i2).mergeBehavior();
                if (map.containsKey(key2) && mergeBehavior == 0) {
                    Action old = map.get(key2);
                    this.mActions.remove(old);
                    if (old != null) {
                        old.clear();
                    }
                    map.remove(key2);
                }
                if (mergeBehavior == 0 || mergeBehavior == 1) {
                    this.mActions.add(a3);
                }
            }
            reconstructCaches();
        }
    }

    public void visitUris(Consumer<Uri> visitor) {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                this.mActions.get(i).visitUris(visitor);
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                this.mSizedRemoteViews.get(i2).visitUris(visitor);
            }
        }
        RemoteViews remoteViews = this.mLandscape;
        if (remoteViews != null) {
            remoteViews.visitUris(visitor);
        }
        RemoteViews remoteViews2 = this.mPortrait;
        if (remoteViews2 != null) {
            remoteViews2.visitUris(visitor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void visitIconUri(Icon icon, Consumer<Uri> visitor) {
        if (icon != null) {
            if (icon.getType() == 4 || icon.getType() == 6) {
                visitor.accept(icon.getUri());
            }
        }
    }

    private static class RemoteViewsContextWrapper extends ContextWrapper {
        private final Context mContextForResources;

        RemoteViewsContextWrapper(Context context, Context contextForResources) {
            super(context);
            this.mContextForResources = contextForResources;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Resources getResources() {
            return this.mContextForResources.getResources();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public Resources.Theme getTheme() {
            return this.mContextForResources.getTheme();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public String getPackageName() {
            return this.mContextForResources.getPackageName();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public UserHandle getUser() {
            return this.mContextForResources.getUser();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public int getUserId() {
            return this.mContextForResources.getUserId();
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public boolean isRestricted() {
            return this.mContextForResources.isRestricted();
        }
    }

    private static class SetEmptyView extends Action {
        int emptyViewId;

        SetEmptyView(int viewId, int emptyViewId) {
            super();
            this.viewId = viewId;
            this.emptyViewId = emptyViewId;
        }

        SetEmptyView(Parcel in) {
            super();
            this.viewId = in.readInt();
            this.emptyViewId = in.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.viewId);
            out.writeInt(this.emptyViewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View view = root.findViewById(this.viewId);
            if (view instanceof AdapterView) {
                AdapterView<?> adapterView = (AdapterView) view;
                View emptyView = root.findViewById(this.emptyViewId);
                if (emptyView == null) {
                    return;
                }
                adapterView.setEmptyView(emptyView);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 6;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetPendingIntentTemplate extends Action {
        PendingIntent pendingIntentTemplate;

        public SetPendingIntentTemplate(int id, PendingIntent pendingIntentTemplate) {
            super();
            this.viewId = id;
            this.pendingIntentTemplate = pendingIntentTemplate;
        }

        public SetPendingIntentTemplate(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.pendingIntentTemplate = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.pendingIntentTemplate, dest);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (target instanceof AdapterView) {
                AdapterView<?> av = (AdapterView) target;
                AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() { // from class: android.widget.RemoteViews$SetPendingIntentTemplate$$ExternalSyntheticLambda0
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                        RemoteViews.SetPendingIntentTemplate.this.lambda$apply$0(params, adapterView, view, i, j);
                    }
                };
                av.setOnItemClickListener(listener);
                av.setTag(this.pendingIntentTemplate);
                return;
            }
            Log.m96e(RemoteViews.LOG_TAG, "Cannot setPendingIntentTemplate on a view which is notan AdapterView (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(ActionApplyParams params, AdapterView parent, View view, int position, long id) {
            RemoteResponse response = findRemoteResponseTag(view);
            if (response != null) {
                response.handleViewInteraction(view, params.handler);
            }
        }

        private RemoteResponse findRemoteResponseTag(View rootView) {
            if (rootView == null) {
                return null;
            }
            ArrayDeque<View> viewsToCheck = new ArrayDeque<>();
            viewsToCheck.addLast(rootView);
            while (!viewsToCheck.isEmpty()) {
                View view = viewsToCheck.removeFirst();
                Object tag = view.getTag(C4337R.id.fillInIntent);
                if (tag instanceof RemoteResponse) {
                    return (RemoteResponse) tag;
                }
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        viewsToCheck.addLast(viewGroup.getChildAt(i));
                    }
                }
            }
            return null;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 8;
        }
    }

    private class SetRemoteViewsAdapterList extends Action {
        ArrayList<RemoteViews> list;
        int viewTypeCount;

        public SetRemoteViewsAdapterList(int id, ArrayList<RemoteViews> list, int viewTypeCount) {
            super();
            this.viewId = id;
            this.list = list;
            this.viewTypeCount = viewTypeCount;
        }

        public SetRemoteViewsAdapterList(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.viewTypeCount = parcel.readInt();
            this.list = parcel.createTypedArrayList(RemoteViews.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.viewTypeCount);
            dest.writeTypedList(this.list, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (!(rootParent instanceof AppWidgetHostView) && !RemoteViews.this.mAllowOtherRootParent) {
                Log.m96e(RemoteViews.LOG_TAG, "SetRemoteViewsAdapterIntent action can only be used for AppWidgets (root id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (!(target instanceof AbsListView) && !(target instanceof AdapterViewAnimator)) {
                Log.m96e(RemoteViews.LOG_TAG, "Cannot setRemoteViewsAdapter on a view which is not an AbsListView or AdapterViewAnimator (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (target instanceof AbsListView) {
                AbsListView v = (AbsListView) target;
                Adapter a = v.getAdapter();
                if ((a instanceof RemoteViewsListAdapter) && this.viewTypeCount <= a.getViewTypeCount()) {
                    ((RemoteViewsListAdapter) a).setViewsList(this.list);
                    return;
                } else {
                    v.setAdapter((ListAdapter) new RemoteViewsListAdapter(v.getContext(), this.list, this.viewTypeCount, params.colorResources));
                    return;
                }
            }
            if (target instanceof AdapterViewAnimator) {
                AdapterViewAnimator v2 = (AdapterViewAnimator) target;
                Adapter a2 = v2.getAdapter();
                if ((a2 instanceof RemoteViewsListAdapter) && this.viewTypeCount <= a2.getViewTypeCount()) {
                    ((RemoteViewsListAdapter) a2).setViewsList(this.list);
                } else {
                    v2.setAdapter(new RemoteViewsListAdapter(v2.getContext(), this.list, this.viewTypeCount, params.colorResources));
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 15;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            Iterator<RemoteViews> it = this.list.iterator();
            while (it.hasNext()) {
                RemoteViews remoteViews = it.next();
                remoteViews.visitUris(visitor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ApplicationInfoCache {
        private final Map<Pair<String, Integer>, ApplicationInfo> mPackageUserToApplicationInfo = new ArrayMap();

        ApplicationInfoCache() {
        }

        ApplicationInfo getOrPut(final ApplicationInfo applicationInfo) {
            Pair<String, Integer> key = RemoteViews.getPackageUserKey(applicationInfo);
            if (key == null) {
                return null;
            }
            return this.mPackageUserToApplicationInfo.computeIfAbsent(key, new Function() { // from class: android.widget.RemoteViews$ApplicationInfoCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return RemoteViews.ApplicationInfoCache.lambda$getOrPut$0(ApplicationInfo.this, (Pair) obj);
                }
            });
        }

        static /* synthetic */ ApplicationInfo lambda$getOrPut$0(ApplicationInfo applicationInfo, Pair ignored) {
            return applicationInfo;
        }

        void put(ApplicationInfo applicationInfo) {
            Pair<String, Integer> key = RemoteViews.getPackageUserKey(applicationInfo);
            if (key == null) {
                return;
            }
            this.mPackageUserToApplicationInfo.put(key, applicationInfo);
        }

        ApplicationInfo get(ApplicationInfo applicationInfo) {
            Pair<String, Integer> key = RemoteViews.getPackageUserKey(applicationInfo);
            if (key == null) {
                return null;
            }
            return this.mPackageUserToApplicationInfo.get(key);
        }
    }

    private class SetRemoteCollectionItemListAdapterAction extends Action {
        private final RemoteCollectionItems mItems;

        SetRemoteCollectionItemListAdapterAction(int id, RemoteCollectionItems items) {
            super();
            this.viewId = id;
            this.mItems = items;
            items.setHierarchyRootData(RemoteViews.this.getHierarchyRootData());
        }

        SetRemoteCollectionItemListAdapterAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.mItems = new RemoteCollectionItems(parcel, RemoteViews.this.getHierarchyRootData());
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData rootData) {
            this.mItems.setHierarchyRootData(rootData);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            this.mItems.writeToParcel(dest, flags, true);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            ActionException actionException;
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (!(rootParent instanceof AppWidgetHostView) && !RemoteViews.this.mAllowOtherRootParent) {
                Log.m96e(RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (!(target instanceof AdapterView)) {
                Log.m96e(RemoteViews.LOG_TAG, "Cannot call setRemoteAdapter on a view which is not an AdapterView (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            AdapterView adapterView = (AdapterView) target;
            Adapter adapter = adapterView.getAdapter();
            if ((adapter instanceof RemoteCollectionItemsAdapter) && adapter.getViewTypeCount() >= this.mItems.getViewTypeCount()) {
                try {
                    ((RemoteCollectionItemsAdapter) adapter).setData(this.mItems, params.handler, params.colorResources);
                } finally {
                }
            } else {
                try {
                    adapterView.setAdapter(new RemoteCollectionItemsAdapter(this.mItems, params.handler, params.colorResources));
                } finally {
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 31;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            RemoteCollectionItems remoteCollectionItems = this.mItems;
            if (remoteCollectionItems != null) {
                remoteCollectionItems.visitUris(visitor);
            }
        }
    }

    private class SetRemoteViewsAdapterIntent extends Action {
        Intent intent;
        boolean isAsync;

        public SetRemoteViewsAdapterIntent(int id, Intent intent) {
            super();
            this.isAsync = false;
            this.viewId = id;
            this.intent = intent;
        }

        public SetRemoteViewsAdapterIntent(Parcel parcel) {
            super();
            this.isAsync = false;
            this.viewId = parcel.readInt();
            this.intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeTypedObject(this.intent, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            boolean isCocktail = false;
            if (rootParent instanceof CocktailHostView) {
                isCocktail = true;
            }
            if (!(rootParent instanceof AppWidgetHostView) && !RemoteViews.this.mAllowOtherRootParent && !isCocktail) {
                Log.m96e(RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (!(target instanceof AbsListView) && !(target instanceof AdapterViewAnimator)) {
                Log.m96e(RemoteViews.LOG_TAG, "Cannot setRemoteAdapter on a view which is not an AbsListView or AdapterViewAnimator (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (isCocktail) {
                this.intent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_COCKTAIL, 1);
                CocktailHostView host = (CocktailHostView) rootParent;
                this.intent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, host.getCocktailId());
            } else {
                if (!RemoteViews.this.mAllowOtherRootParent) {
                    RemoteViews.this.mAppWidgetId = ((AppWidgetHostView) rootParent).getAppWidgetId();
                }
                this.intent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, RemoteViews.this.mAppWidgetId).putExtra(RemoteViews.EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND, RemoteViews.this.hasFlags(4));
            }
            if (target instanceof AbsListView) {
                AbsListView v = (AbsListView) target;
                v.setRemoteViewsAdapter(this.intent, this.isAsync);
                v.setRemoteViewsInteractionHandler(params.handler);
            } else if (target instanceof AdapterViewAnimator) {
                AdapterViewAnimator v2 = (AdapterViewAnimator) target;
                v2.setRemoteViewsAdapter(this.intent, this.isAsync);
                v2.setRemoteViewsOnClickHandler(params.handler);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            SetRemoteViewsAdapterIntent copy = RemoteViews.this.new SetRemoteViewsAdapterIntent(this.viewId, this.intent);
            copy.isAsync = true;
            return copy;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 10;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnClickResponse extends Action {
        final RemoteResponse mResponse;

        SetOnClickResponse(int id, RemoteResponse response) {
            super();
            this.viewId = id;
            this.mResponse = response;
        }

        SetOnClickResponse(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            RemoteResponse remoteResponse = new RemoteResponse();
            this.mResponse = remoteResponse;
            remoteResponse.readFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            this.mResponse.writeToParcel(dest, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (this.mResponse.mPendingIntent != null) {
                if (RemoteViews.this.hasFlags(2) && !RemoteViews.this.mIsAllowPendintIntentInCollection) {
                    Log.m102w(RemoteViews.LOG_TAG, "Cannot SetOnClickResponse for collection item (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                    ApplicationInfo appInfo = root.getContext().getApplicationInfo();
                    if (appInfo != null && appInfo.targetSdkVersion >= 16) {
                        return;
                    }
                }
                target.setTagInternal(C4337R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!RemoteViews.this.hasFlags(2)) {
                    Log.m96e(RemoteViews.LOG_TAG, "The method setOnClickFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                } else if (target == root) {
                    target.setTagInternal(C4337R.id.fillInIntent, this.mResponse);
                    return;
                }
            } else {
                target.setOnClickListener(null);
                target.setTagInternal(C4337R.id.pending_intent_tag, null);
                target.setTagInternal(C4337R.id.fillInIntent, null);
                return;
            }
            target.setOnClickListener(new View.OnClickListener() { // from class: android.widget.RemoteViews$SetOnClickResponse$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RemoteViews.SetOnClickResponse.this.lambda$apply$0(params, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(ActionApplyParams params, View v) {
            this.mResponse.handleViewInteraction(v, params.handler);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 1;
        }

        @Override // android.widget.RemoteViews.Action
        public void clear() {
            this.mResponse.mPendingIntent = null;
            this.mResponse.mFillIntent = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnCheckedChangeResponse extends Action {
        private final RemoteResponse mResponse;

        SetOnCheckedChangeResponse(int id, RemoteResponse response) {
            super();
            this.viewId = id;
            this.mResponse = response;
        }

        SetOnCheckedChangeResponse(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            RemoteResponse remoteResponse = new RemoteResponse();
            this.mResponse = remoteResponse;
            remoteResponse.readFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            this.mResponse.writeToParcel(dest, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (!(target instanceof CompoundButton)) {
                Log.m102w(RemoteViews.LOG_TAG, "setOnCheckedChange methods cannot be used on non-CompoundButton child (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            CompoundButton button = (CompoundButton) target;
            if (this.mResponse.mPendingIntent != null) {
                if (RemoteViews.this.hasFlags(2)) {
                    Log.m102w(RemoteViews.LOG_TAG, "Cannot setOnCheckedChangePendingIntent for collection item (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
                    return;
                }
                target.setTagInternal(C4337R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!RemoteViews.this.hasFlags(2)) {
                    Log.m96e(RemoteViews.LOG_TAG, "The method setOnCheckedChangeFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                }
            } else {
                button.setOnCheckedChangeListener(null);
                button.setTagInternal(C4337R.id.remote_checked_change_listener_tag, null);
                return;
            }
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: android.widget.RemoteViews$SetOnCheckedChangeResponse$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    RemoteViews.SetOnCheckedChangeResponse.this.lambda$apply$0(params, compoundButton, z);
                }
            };
            button.setTagInternal(C4337R.id.remote_checked_change_listener_tag, onCheckedChangeListener);
            button.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(ActionApplyParams params, CompoundButton v, boolean isChecked) {
            this.mResponse.handleViewInteraction(v, params.handler);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 29;
        }

        @Override // android.widget.RemoteViews.Action
        public void clear() {
            this.mResponse.mPendingIntent = null;
            this.mResponse.mFillIntent = null;
        }
    }

    public static Rect getSourceBounds(View v) {
        float appScale = v.getContext().getResources().getCompatibilityInfo().applicationScale;
        int[] pos = new int[2];
        v.getLocationOnScreen(pos);
        Rect rect = new Rect();
        rect.left = (int) ((pos[0] * appScale) + 0.5f);
        rect.top = (int) ((pos[1] * appScale) + 0.5f);
        rect.right = (int) (((pos[0] + v.getWidth()) * appScale) + 0.5f);
        rect.bottom = (int) (((pos[1] + v.getHeight()) * appScale) + 0.5f);
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Class<?> getParameterType(int type) {
        switch (type) {
            case 1:
                return Boolean.TYPE;
            case 2:
                return Byte.TYPE;
            case 3:
                return Short.TYPE;
            case 4:
                return Integer.TYPE;
            case 5:
                return Long.TYPE;
            case 6:
                return Float.TYPE;
            case 7:
                return Double.TYPE;
            case 8:
                return Character.TYPE;
            case 9:
                return String.class;
            case 10:
                return CharSequence.class;
            case 11:
                return Uri.class;
            case 12:
                return Bitmap.class;
            case 13:
                return Bundle.class;
            case 14:
                return Intent.class;
            case 15:
                return ColorStateList.class;
            case 16:
                return Icon.class;
            case 17:
                return BlendMode.class;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
                return null;
            case 30:
                return SemBlurInfo.class;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MethodHandle getMethod(View view, String methodName, Class<?> paramType, boolean async) {
        Method method;
        Class<?> cls = view.getClass();
        ArrayMap<MethodKey, MethodArgs> arrayMap = sMethods;
        synchronized (arrayMap) {
            MethodKey methodKey = sLookupKey;
            methodKey.set(cls, paramType, methodName);
            MethodArgs result = arrayMap.get(methodKey);
            if (result == null) {
                try {
                    if (paramType == null) {
                        method = cls.getMethod(methodName, new Class[0]);
                    } else {
                        method = cls.getMethod(methodName, paramType);
                    }
                    if (!method.isAnnotationPresent(RemotableViewMethod.class)) {
                        throw new ActionException("view: " + cls.getName() + " can't use method with RemoteViews: " + methodName + getParameters(paramType));
                    }
                    result = new MethodArgs();
                    result.syncMethod = MethodHandles.publicLookup().unreflect(method);
                    result.asyncMethodName = ((RemotableViewMethod) method.getAnnotation(RemotableViewMethod.class)).asyncImpl();
                    MethodKey key = new MethodKey();
                    key.set(cls, paramType, methodName);
                    arrayMap.put(key, result);
                } catch (IllegalAccessException | NoSuchMethodException e) {
                    throw new ActionException("view: " + cls.getName() + " doesn't have method: " + methodName + getParameters(paramType));
                }
            }
            if (!async) {
                return result.syncMethod;
            }
            if (result.asyncMethodName.isEmpty()) {
                return null;
            }
            if (result.asyncMethod == null) {
                MethodType asyncType = result.syncMethod.type().dropParameterTypes(0, 1).changeReturnType(Runnable.class);
                try {
                    result.asyncMethod = MethodHandles.publicLookup().findVirtual(cls, result.asyncMethodName, asyncType);
                } catch (IllegalAccessException | NoSuchMethodException e2) {
                    throw new ActionException("Async implementation declared as " + result.asyncMethodName + " but not defined for " + methodName + ": public Runnable " + result.asyncMethodName + " (" + TextUtils.join(",", asyncType.parameterArray()) + NavigationBarInflaterView.KEY_CODE_END);
                }
            }
            return result.asyncMethod;
        }
    }

    private static String getParameters(Class<?> paramType) {
        return paramType == null ? "()" : NavigationBarInflaterView.KEY_CODE_START + paramType + NavigationBarInflaterView.KEY_CODE_END;
    }

    private static class SetDrawableTint extends Action {
        int colorFilter;
        PorterDuff.Mode filterMode;
        boolean targetBackground;

        SetDrawableTint(int id, boolean targetBackground, int colorFilter, PorterDuff.Mode mode) {
            super();
            this.viewId = id;
            this.targetBackground = targetBackground;
            this.colorFilter = colorFilter;
            this.filterMode = mode;
        }

        SetDrawableTint(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.targetBackground = parcel.readInt() != 0;
            this.colorFilter = parcel.readInt();
            this.filterMode = PorterDuff.intToMode(parcel.readInt());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.targetBackground ? 1 : 0);
            parcel.writeInt(this.colorFilter);
            parcel.writeInt(PorterDuff.modeToInt(this.filterMode));
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            Drawable targetDrawable = null;
            if (this.targetBackground) {
                targetDrawable = target.getBackground();
            } else if (target instanceof ImageView) {
                ImageView imageView = (ImageView) target;
                targetDrawable = imageView.getDrawable();
            }
            if (targetDrawable != null) {
                targetDrawable.mutate().setColorFilter(this.colorFilter, this.filterMode);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 3;
        }
    }

    private class SetVectorDrawablePathColor extends Action {
        int mColorFilter;
        String mTargetPathName;

        SetVectorDrawablePathColor(int id, String targetPathName, int colorFilter) {
            super();
            this.viewId = id;
            this.mTargetPathName = targetPathName;
            this.mColorFilter = colorFilter;
        }

        SetVectorDrawablePathColor(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.mTargetPathName = parcel.readString();
            this.mColorFilter = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeString(this.mTargetPathName);
            dest.writeInt(this.mColorFilter);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            String str = this.mTargetPathName;
            if (str == null || str.isEmpty()) {
                return;
            }
            View target = root.findViewById(this.viewId);
            if (target instanceof ImageView) {
                ImageView imageView = (ImageView) target;
                Drawable targetDrawable = imageView.getDrawable();
                if (targetDrawable instanceof VectorDrawable) {
                    ((VectorDrawable) targetDrawable).setPathColor(this.mTargetPathName, this.mColorFilter);
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 108;
        }
    }

    private class SetTextViewShadowAction extends Action {
        int color;

        /* renamed from: dx */
        float f605dx;

        /* renamed from: dy */
        float f606dy;
        float radius;
        int viewId;

        public SetTextViewShadowAction(int viewId, float radius, float dx, float dy, int color) {
            super();
            this.viewId = viewId;
            this.radius = radius;
            this.f605dx = dx;
            this.f606dy = dy;
            this.color = color;
        }

        public SetTextViewShadowAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.radius = parcel.readFloat();
            this.f605dx = parcel.readFloat();
            this.f606dy = parcel.readFloat();
            this.color = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeFloat(this.radius);
            dest.writeFloat(this.f605dx);
            dest.writeFloat(this.f606dy);
            dest.writeInt(this.color);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target instanceof TextView) {
                ((TextView) target).setShadowLayer(this.radius, this.f605dx, this.f606dy, this.color);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 109;
        }
    }

    private class SetRippleDrawableColor extends Action {
        ColorStateList mColorStateList;

        SetRippleDrawableColor(int id, ColorStateList colorStateList) {
            super();
            this.viewId = id;
            this.mColorStateList = colorStateList;
        }

        SetRippleDrawableColor(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.mColorStateList = (ColorStateList) parcel.readParcelable(null, ColorStateList.class);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeParcelable(this.mColorStateList, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            Drawable targetDrawable = target.getBackground();
            if (targetDrawable instanceof RippleDrawable) {
                ((RippleDrawable) targetDrawable.mutate()).setColor(this.mColorStateList);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 21;
        }
    }

    @Deprecated
    private final class ViewContentNavigation extends Action {
        final boolean mNext;

        ViewContentNavigation(int viewId, boolean next) {
            super();
            this.viewId = viewId;
            this.mNext = next;
        }

        ViewContentNavigation(Parcel in) {
            super();
            this.viewId = in.readInt();
            this.mNext = in.readBoolean();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.viewId);
            out.writeBoolean(this.mNext);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View view = root.findViewById(this.viewId);
            if (view == null) {
                return;
            }
            try {
                (void) RemoteViews.getMethod(view, this.mNext ? "showNext" : "showPrevious", null, false).invoke(view);
            } catch (Throwable ex) {
                throw new ActionException(ex);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 2;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 5;
        }
    }

    private static class BitmapCache {
        SparseIntArray mBitmapHashes;
        int mBitmapMemory;
        ArrayList<Bitmap> mBitmaps;

        public BitmapCache() {
            this.mBitmapMemory = -1;
            this.mBitmaps = new ArrayList<>();
            this.mBitmapHashes = new SparseIntArray();
        }

        public BitmapCache(Parcel source) {
            this.mBitmapMemory = -1;
            this.mBitmaps = source.createTypedArrayList(Bitmap.CREATOR);
            this.mBitmapHashes = new SparseIntArray();
            for (int i = 0; i < this.mBitmaps.size(); i++) {
                Bitmap b = this.mBitmaps.get(i);
                if (b != null) {
                    this.mBitmapHashes.put(b.hashCode(), i);
                }
            }
        }

        public int getBitmapId(Bitmap b) {
            if (b == null) {
                return -1;
            }
            int hash = b.hashCode();
            int hashId = this.mBitmapHashes.get(hash, -1);
            if (hashId != -1) {
                return hashId;
            }
            if (b.isMutable()) {
                b = b.asShared();
            }
            this.mBitmaps.add(b);
            this.mBitmapHashes.put(hash, this.mBitmaps.size() - 1);
            this.mBitmapMemory = -1;
            return this.mBitmaps.size() - 1;
        }

        public Bitmap getBitmapForId(int id) {
            if (id == -1 || id >= this.mBitmaps.size()) {
                return null;
            }
            return this.mBitmaps.get(id);
        }

        public void writeBitmapsToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.mBitmaps, flags);
        }

        public int getBitmapMemory() {
            if (this.mBitmapMemory < 0) {
                this.mBitmapMemory = 0;
                int count = this.mBitmaps.size();
                for (int i = 0; i < count; i++) {
                    this.mBitmapMemory += this.mBitmaps.get(i).getAllocationByteCount();
                }
            }
            int count2 = this.mBitmapMemory;
            return count2;
        }
    }

    private class BitmapReflectionAction extends Action {
        Bitmap bitmap;
        int bitmapId;
        String methodName;

        BitmapReflectionAction(int viewId, String methodName, Bitmap bitmap) {
            super();
            this.bitmap = bitmap;
            this.viewId = viewId;
            this.methodName = methodName;
            this.bitmapId = RemoteViews.this.mBitmapCache.getBitmapId(bitmap);
        }

        BitmapReflectionAction(Parcel in) {
            super();
            this.viewId = in.readInt();
            this.methodName = in.readString8();
            this.bitmapId = in.readInt();
            this.bitmap = RemoteViews.this.mBitmapCache.getBitmapForId(this.bitmapId);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeString8(this.methodName);
            dest.writeInt(this.bitmapId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            ReflectionAction ra = new ReflectionAction(this.viewId, this.methodName, 12, this.bitmap);
            ra.apply(root, rootParent, params);
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData rootData) {
            this.bitmapId = rootData.mBitmapCache.getBitmapId(this.bitmap);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 12;
        }
    }

    private static abstract class BaseReflectionAction extends Action {
        static final int BITMAP = 12;
        static final int BLEND_MODE = 17;
        static final int BOOLEAN = 1;
        static final int BUNDLE = 13;
        static final int BYTE = 2;
        static final int CHAR = 8;
        static final int CHAR_SEQUENCE = 10;
        static final int COLOR_STATE_LIST = 15;
        static final int DOUBLE = 7;
        static final int FLOAT = 6;
        static final int ICON = 16;
        static final int INT = 4;
        static final int INTENT = 14;
        static final int LONG = 5;
        static final int SEM_BLUR_INFO = 30;
        static final int SHORT = 3;
        static final int STRING = 9;
        static final int URI = 11;
        String methodName;
        int type;

        protected abstract Object getParameterValue(View view) throws ActionException;

        BaseReflectionAction(int viewId, String methodName, int type) {
            super();
            this.viewId = viewId;
            this.methodName = methodName;
            this.type = type;
        }

        BaseReflectionAction(Parcel in) {
            super();
            this.viewId = in.readInt();
            this.methodName = in.readString8();
            this.type = in.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.viewId);
            out.writeString8(this.methodName);
            out.writeInt(this.type);
        }

        @Override // android.widget.RemoteViews.Action
        public final void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View view = root.findViewById(this.viewId);
            if (view == null) {
                return;
            }
            Class<?> param = RemoteViews.getParameterType(this.type);
            if (param == null) {
                throw new ActionException("bad type: " + this.type);
            }
            Object value = getParameterValue(view);
            try {
                (void) RemoteViews.getMethod(view, this.methodName, param, false).invoke(view, value);
            } catch (Throwable ex) {
                throw new ActionException(ex);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public final Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            Bitmap bitmap;
            View view = root.findViewById(this.viewId);
            if (view == null) {
                return RemoteViews.ACTION_NOOP;
            }
            Class<?> param = RemoteViews.getParameterType(this.type);
            if (param == null) {
                throw new ActionException("bad type: " + this.type);
            }
            Object value = getParameterValue(view);
            try {
                MethodHandle method = RemoteViews.getMethod(view, this.methodName, param, true);
                if (value instanceof Bitmap) {
                    ((Bitmap) value).prepareToDraw();
                }
                if (value instanceof Icon) {
                    Icon icon = (Icon) value;
                    if ((icon.getType() == 1 || icon.getType() == 5) && (bitmap = icon.getBitmap()) != null) {
                        bitmap.prepareToDraw();
                    }
                }
                if (method != null) {
                    Runnable endAction = (Runnable) method.invoke(view, value);
                    if (endAction == null) {
                        return RemoteViews.ACTION_NOOP;
                    }
                    if (endAction instanceof ViewStub.ViewReplaceRunnable) {
                        root.createTree();
                        root.findViewTreeById(this.viewId).replaceView(((ViewStub.ViewReplaceRunnable) endAction).view);
                    }
                    return new RunnableAction(endAction);
                }
                return this;
            } catch (Throwable ex) {
                throw new ActionException(ex);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public final int mergeBehavior() {
            if (this.methodName.equals("smoothScrollBy")) {
                return 1;
            }
            return 0;
        }

        @Override // android.widget.RemoteViews.Action
        public final String getUniqueKey() {
            return super.getUniqueKey() + this.methodName + this.type;
        }

        @Override // android.widget.RemoteViews.Action
        public final boolean prefersAsyncApply() {
            int i = this.type;
            return i == 11 || i == 16;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            switch (this.type) {
                case 11:
                    Uri uri = (Uri) getParameterValue(null);
                    if (uri != null) {
                        visitor.accept(uri);
                        break;
                    }
                    break;
                case 16:
                    Icon icon = (Icon) getParameterValue(null);
                    if (icon != null) {
                        RemoteViews.visitIconUri(icon, visitor);
                        break;
                    }
                    break;
            }
        }
    }

    private static final class ReflectionAction extends BaseReflectionAction {
        Object value;

        ReflectionAction(int viewId, String methodName, int type, Object value) {
            super(viewId, methodName, type);
            this.value = value;
        }

        ReflectionAction(Parcel in) {
            super(in);
            switch (this.type) {
                case 1:
                    this.value = Boolean.valueOf(in.readBoolean());
                    break;
                case 2:
                    this.value = Byte.valueOf(in.readByte());
                    break;
                case 3:
                    this.value = Short.valueOf((short) in.readInt());
                    break;
                case 4:
                    this.value = Integer.valueOf(in.readInt());
                    break;
                case 5:
                    this.value = Long.valueOf(in.readLong());
                    break;
                case 6:
                    this.value = Float.valueOf(in.readFloat());
                    break;
                case 7:
                    this.value = Double.valueOf(in.readDouble());
                    break;
                case 8:
                    this.value = Character.valueOf((char) in.readInt());
                    break;
                case 9:
                    this.value = in.readString8();
                    break;
                case 10:
                    this.value = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
                    break;
                case 11:
                    this.value = in.readTypedObject(Uri.CREATOR);
                    break;
                case 12:
                    this.value = in.readTypedObject(Bitmap.CREATOR);
                    break;
                case 13:
                    if (in.hasReadWriteHelper()) {
                        this.value = in.readBundle();
                        break;
                    } else {
                        in.setReadWriteHelper(RemoteViews.ALTERNATIVE_DEFAULT);
                        this.value = in.readBundle();
                        in.setReadWriteHelper(null);
                        break;
                    }
                case 14:
                    this.value = in.readTypedObject(Intent.CREATOR);
                    break;
                case 15:
                    this.value = in.readTypedObject(ColorStateList.CREATOR);
                    break;
                case 16:
                    this.value = in.readTypedObject(Icon.CREATOR);
                    break;
                case 17:
                    this.value = BlendMode.fromValue(in.readInt());
                    break;
                case 30:
                    this.value = in.readTypedObject(SemBlurInfo.CREATOR);
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            switch (this.type) {
                case 1:
                    out.writeBoolean(((Boolean) this.value).booleanValue());
                    break;
                case 2:
                    out.writeByte(((Byte) this.value).byteValue());
                    break;
                case 3:
                    out.writeInt(((Short) this.value).shortValue());
                    break;
                case 4:
                    out.writeInt(((Integer) this.value).intValue());
                    break;
                case 5:
                    out.writeLong(((Long) this.value).longValue());
                    break;
                case 6:
                    out.writeFloat(((Float) this.value).floatValue());
                    break;
                case 7:
                    out.writeDouble(((Double) this.value).doubleValue());
                    break;
                case 8:
                    out.writeInt(((Character) this.value).charValue());
                    break;
                case 9:
                    out.writeString8((String) this.value);
                    break;
                case 10:
                    TextUtils.writeToParcel((CharSequence) this.value, out, flags);
                    break;
                case 11:
                case 12:
                case 14:
                case 15:
                case 16:
                case 30:
                    out.writeTypedObject((Parcelable) this.value, flags);
                    break;
                case 13:
                    out.writeBundle((Bundle) this.value);
                    break;
                case 17:
                    out.writeInt(BlendMode.toValue((BlendMode) this.value));
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            return this.value;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 2;
        }
    }

    private static final class ResourceReflectionAction extends BaseReflectionAction {
        static final int COLOR_RESOURCE = 2;
        static final int DIMEN_RESOURCE = 1;
        static final int STRING_RESOURCE = 3;
        private final int mResId;
        private final int mResourceType;

        ResourceReflectionAction(int viewId, String methodName, int parameterType, int resourceType, int resId) {
            super(viewId, methodName, parameterType);
            this.mResourceType = resourceType;
            this.mResId = resId;
        }

        ResourceReflectionAction(Parcel in) {
            super(in);
            this.mResourceType = in.readInt();
            this.mResId = in.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.mResourceType);
            dest.writeInt(this.mResId);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            if (view == null) {
                return null;
            }
            Resources resources = view.getContext().getResources();
            try {
                switch (this.mResourceType) {
                    case 1:
                        switch (this.type) {
                            case 4:
                                int i = this.mResId;
                                return Integer.valueOf(i != 0 ? resources.getDimensionPixelSize(i) : 0);
                            case 5:
                            default:
                                throw new ActionException("dimen resources must be used as INT or FLOAT, not " + this.type);
                            case 6:
                                int i2 = this.mResId;
                                return Float.valueOf(i2 == 0 ? 0.0f : resources.getDimension(i2));
                        }
                    case 2:
                        switch (this.type) {
                            case 4:
                                return Integer.valueOf(this.mResId != 0 ? view.getContext().getColor(this.mResId) : 0);
                            case 15:
                                return this.mResId != 0 ? view.getContext().getColorStateList(this.mResId) : null;
                            default:
                                throw new ActionException("color resources must be used as INT or COLOR_STATE_LIST, not " + this.type);
                        }
                    case 3:
                        switch (this.type) {
                            case 9:
                                int i3 = this.mResId;
                                return i3 != 0 ? resources.getString(i3) : null;
                            case 10:
                                int i4 = this.mResId;
                                return i4 != 0 ? resources.getText(i4) : null;
                            default:
                                throw new ActionException("string resources must be used as STRING or CHAR_SEQUENCE, not " + this.type);
                        }
                    default:
                        throw new ActionException("unknown resource type: " + this.mResourceType);
                }
            } catch (ActionException ex) {
                throw ex;
            } catch (Throwable t) {
                throw new ActionException(t);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 24;
        }
    }

    private static final class AttributeReflectionAction extends BaseReflectionAction {
        static final int COLOR_RESOURCE = 2;
        static final int DIMEN_RESOURCE = 1;
        static final int STRING_RESOURCE = 3;
        private final int mAttrId;
        private final int mResourceType;

        AttributeReflectionAction(int viewId, String methodName, int parameterType, int resourceType, int attrId) {
            super(viewId, methodName, parameterType);
            this.mResourceType = resourceType;
            this.mAttrId = attrId;
        }

        AttributeReflectionAction(Parcel in) {
            super(in);
            this.mResourceType = in.readInt();
            this.mAttrId = in.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.mResourceType);
            dest.writeInt(this.mAttrId);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            TypedArray typedArray = view.getContext().obtainStyledAttributes(new int[]{this.mAttrId});
            try {
                try {
                    if (this.mAttrId != 0 && typedArray.getType(0) == 0) {
                        throw new ActionException("Attribute 0x" + Integer.toHexString(this.mAttrId) + " is not defined");
                    }
                    switch (this.mResourceType) {
                        case 1:
                            switch (this.type) {
                                case 4:
                                    return Integer.valueOf(typedArray.getDimensionPixelSize(0, 0));
                                case 5:
                                default:
                                    throw new ActionException("dimen attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as INT or FLOAT, not " + this.type);
                                case 6:
                                    return Float.valueOf(typedArray.getDimension(0, 0.0f));
                            }
                        case 2:
                            switch (this.type) {
                                case 4:
                                    return Integer.valueOf(typedArray.getColor(0, 0));
                                case 15:
                                    return typedArray.getColorStateList(0);
                                default:
                                    throw new ActionException("color attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as INT or COLOR_STATE_LIST, not " + this.type);
                            }
                        case 3:
                            switch (this.type) {
                                case 9:
                                    return typedArray.getString(0);
                                case 10:
                                    return typedArray.getText(0);
                                default:
                                    throw new ActionException("string attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as STRING or CHAR_SEQUENCE, not " + this.type);
                            }
                        default:
                            throw new ActionException("Unknown resource type: " + this.mResourceType);
                    }
                } finally {
                    typedArray.recycle();
                }
            } catch (ActionException ex) {
                throw ex;
            } catch (Throwable t) {
                throw new ActionException(t);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 32;
        }
    }

    private static final class ComplexUnitDimensionReflectionAction extends BaseReflectionAction {
        private final int mUnit;
        private final float mValue;

        ComplexUnitDimensionReflectionAction(int viewId, String methodName, int parameterType, float value, int unit) {
            super(viewId, methodName, parameterType);
            this.mValue = value;
            this.mUnit = unit;
        }

        ComplexUnitDimensionReflectionAction(Parcel in) {
            super(in);
            this.mValue = in.readFloat();
            this.mUnit = in.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(this.mValue);
            dest.writeInt(this.mUnit);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            if (view == null) {
                return null;
            }
            DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
            try {
                int data = TypedValue.createComplexDimension(this.mValue, this.mUnit);
                switch (this.type) {
                    case 4:
                        return Integer.valueOf(TypedValue.complexToDimensionPixelSize(data, dm));
                    case 5:
                    default:
                        throw new ActionException("parameter type must be INT or FLOAT, not " + this.type);
                    case 6:
                        return Float.valueOf(TypedValue.complexToDimension(data, dm));
                }
            } catch (ActionException ex) {
                throw ex;
            } catch (Throwable t) {
                throw new ActionException(t);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 25;
        }
    }

    private static final class NightModeReflectionAction extends BaseReflectionAction {
        private final Object mDarkValue;
        private final Object mLightValue;

        NightModeReflectionAction(int viewId, String methodName, int type, Object lightValue, Object darkValue) {
            super(viewId, methodName, type);
            this.mLightValue = lightValue;
            this.mDarkValue = darkValue;
        }

        NightModeReflectionAction(Parcel in) {
            super(in);
            switch (this.type) {
                case 4:
                    this.mLightValue = Integer.valueOf(in.readInt());
                    this.mDarkValue = Integer.valueOf(in.readInt());
                    return;
                case 15:
                    this.mLightValue = in.readTypedObject(ColorStateList.CREATOR);
                    this.mDarkValue = in.readTypedObject(ColorStateList.CREATOR);
                    return;
                case 16:
                    this.mLightValue = in.readTypedObject(Icon.CREATOR);
                    this.mDarkValue = in.readTypedObject(Icon.CREATOR);
                    return;
                default:
                    throw new ActionException("Unexpected night mode action type: " + this.type);
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            switch (this.type) {
                case 4:
                    out.writeInt(((Integer) this.mLightValue).intValue());
                    out.writeInt(((Integer) this.mDarkValue).intValue());
                    break;
                case 15:
                case 16:
                    out.writeTypedObject((Parcelable) this.mLightValue, flags);
                    out.writeTypedObject((Parcelable) this.mDarkValue, flags);
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            if (view == null) {
                return null;
            }
            Configuration configuration = view.getResources().getConfiguration();
            return configuration.isNightModeActive() ? this.mDarkValue : this.mLightValue;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 30;
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            if (this.type == 16) {
                RemoteViews.visitIconUri((Icon) this.mDarkValue, visitor);
                RemoteViews.visitIconUri((Icon) this.mLightValue, visitor);
            }
        }
    }

    private static final class RunnableAction extends RuntimeAction {
        private final Runnable mRunnable;

        RunnableAction(Runnable r) {
            super();
            this.mRunnable = r;
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            this.mRunnable.run();
        }
    }

    void setNotRoot() {
        this.mIsRoot = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasStableId(View view) {
        Object tag = view.getTag(C4337R.id.remote_views_stable_id);
        return tag != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getStableId(View view) {
        Integer id = (Integer) view.getTag(C4337R.id.remote_views_stable_id);
        if (id == null) {
            return -1;
        }
        return id.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setStableId(View view, int stableId) {
        view.setTagInternal(C4337R.id.remote_views_stable_id, Integer.valueOf(stableId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getNextRecyclableChild(ViewGroup vg) {
        Integer tag = (Integer) vg.getTag(C4337R.id.remote_views_next_child);
        if (tag == null) {
            return -1;
        }
        return tag.intValue();
    }

    private static int getViewLayoutId(View v) {
        return ((Integer) v.getTag(16908312)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setNextRecyclableChild(ViewGroup vg, int nextChild, int numChildren) {
        if (nextChild < 0 || nextChild >= numChildren) {
            vg.setTagInternal(C4337R.id.remote_views_next_child, -1);
        } else {
            vg.setTagInternal(C4337R.id.remote_views_next_child, Integer.valueOf(nextChild));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finalizeViewRecycling(ViewGroup root) {
        int nextChild = getNextRecyclableChild(root);
        if (nextChild >= 0 && nextChild < root.getChildCount()) {
            root.removeViews(nextChild, root.getChildCount() - nextChild);
        }
        setNextRecyclableChild(root, -1, 0);
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if ((child instanceof ViewGroup) && !child.isRootNamespace()) {
                finalizeViewRecycling((ViewGroup) child);
            }
        }
    }

    private class ViewObjectAnimatorAction extends Action {
        private int mAnimatorId;
        private boolean mIsAnimationEnd;
        private final int mViewId;

        public ViewObjectAnimatorAction(int viewId, int animatorId) {
            super();
            this.mViewId = viewId;
            this.mAnimatorId = animatorId;
            this.mIsAnimationEnd = false;
        }

        public ViewObjectAnimatorAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mAnimatorId = parcel.readInt();
            this.mIsAnimationEnd = parcel.readBoolean();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mAnimatorId);
            dest.writeBoolean(this.mIsAnimationEnd);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            AnimatorSet animatorSet;
            if (root == null || this.mAnimatorId == -1) {
                return;
            }
            Context context = root.getContext();
            View target = root.findViewById(this.mViewId);
            if (target == null || (animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, this.mAnimatorId)) == null) {
                return;
            }
            animatorSet.setTarget(target);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: android.widget.RemoteViews.ViewObjectAnimatorAction.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    ViewObjectAnimatorAction.this.mIsAnimationEnd = true;
                }
            });
            if (this.mIsAnimationEnd) {
                animatorSet.setDuration(0L);
            }
            animatorSet.start();
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 106;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ViewGroupActionAdd extends Action {
        static final int NO_ID = -1;
        private int mIndex;
        private RemoteViews mNestedViews;
        private int mStableId;

        ViewGroupActionAdd(RemoteViews remoteViews, int viewId, RemoteViews nestedViews) {
            this(viewId, nestedViews, -1, -1);
        }

        ViewGroupActionAdd(RemoteViews remoteViews, int viewId, RemoteViews nestedViews, int index) {
            this(viewId, nestedViews, index, -1);
        }

        ViewGroupActionAdd(int viewId, RemoteViews nestedViews, int index, int stableId) {
            super();
            this.viewId = viewId;
            this.mNestedViews = nestedViews;
            this.mIndex = index;
            this.mStableId = stableId;
            nestedViews.configureAsChild(RemoteViews.this.getHierarchyRootData());
        }

        ViewGroupActionAdd(Parcel parcel, ApplicationInfo info, int depth) {
            super();
            this.viewId = parcel.readInt();
            this.mIndex = parcel.readInt();
            this.mStableId = parcel.readInt();
            RemoteViews remoteViews = new RemoteViews(parcel, RemoteViews.this.getHierarchyRootData(), info, depth);
            this.mNestedViews = remoteViews;
            remoteViews.addFlags(RemoteViews.this.mApplyFlags);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.mIndex);
            dest.writeInt(this.mStableId);
            this.mNestedViews.writeToParcel(dest, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData root) {
            this.mNestedViews.configureAsChild(root);
        }

        private int findViewIndexToRecycle(ViewGroup target, RemoteViews newContent) {
            for (int nextChild = RemoteViews.getNextRecyclableChild(target); nextChild < target.getChildCount(); nextChild++) {
                View child = target.getChildAt(nextChild);
                if (RemoteViews.getStableId(child) == this.mStableId) {
                    return nextChild;
                }
            }
            return -1;
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            int recycledViewIndex;
            RemoteViews remoteViews;
            Context context = root.getContext();
            ViewGroup target = (ViewGroup) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            int nextChild = RemoteViews.getNextRecyclableChild(target);
            if (RemoteViews.this.mIsAllowPendintIntentInCollection && (remoteViews = this.mNestedViews) != null) {
                remoteViews.hidden_semSetAllowOtherRootParent(true, RemoteViews.this.mAppWidgetId);
            }
            RemoteViews rvToApply = this.mNestedViews.getRemoteViewsToApply(context);
            int flagsToPropagate = RemoteViews.this.mApplyFlags & 6;
            if (flagsToPropagate != 0) {
                rvToApply.addFlags(flagsToPropagate);
            }
            if (nextChild >= 0 && this.mStableId != -1 && (recycledViewIndex = findViewIndexToRecycle(target, rvToApply)) >= 0) {
                View child = target.getChildAt(recycledViewIndex);
                if (rvToApply.canRecycleView(child)) {
                    if (nextChild < recycledViewIndex) {
                        target.removeViews(nextChild, recycledViewIndex - nextChild);
                    }
                    RemoteViews.setNextRecyclableChild(target, nextChild + 1, target.getChildCount());
                    rvToApply.reapplyNestedViews(context, child, rootParent, params);
                    return;
                }
                target.removeViews(nextChild, (recycledViewIndex - nextChild) + 1);
            }
            View nestedView = rvToApply.apply(context, target, rootParent, (SizeF) null, params);
            int i = this.mStableId;
            if (i != -1) {
                RemoteViews.setStableId(nestedView, i);
            }
            int i2 = this.mIndex;
            if (i2 < 0) {
                i2 = nextChild;
            }
            target.addView(nestedView, i2);
            if (nextChild >= 0) {
                RemoteViews.setNextRecyclableChild(target, nextChild + 1, target.getChildCount());
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            root.createTree();
            ViewTree target = root.findViewTreeById(this.viewId);
            if (target == null || !(target.mRoot instanceof ViewGroup)) {
                return RemoteViews.ACTION_NOOP;
            }
            final ViewGroup targetVg = (ViewGroup) target.mRoot;
            Context context = root.mRoot.getContext();
            this.mNestedViews.addFlags(RemoteViews.this.mApplyFlags);
            final int nextChild = RemoteViews.getNextRecyclableChild(targetVg);
            if (nextChild >= 0 && this.mStableId != -1) {
                RemoteViews rvToApply = this.mNestedViews.getRemoteViewsToApply(context);
                final int recycledViewIndex = target.findChildIndex(nextChild, new Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$initActionAsync$0;
                        lambda$initActionAsync$0 = RemoteViews.ViewGroupActionAdd.this.lambda$initActionAsync$0((View) obj);
                        return lambda$initActionAsync$0;
                    }
                });
                if (recycledViewIndex >= 0) {
                    ViewTree recycled = (ViewTree) target.mChildren.get(recycledViewIndex);
                    if (rvToApply.canRecycleView(recycled.mRoot)) {
                        if (recycledViewIndex > nextChild) {
                            target.removeChildren(nextChild, recycledViewIndex - nextChild);
                        }
                        RemoteViews.setNextRecyclableChild(targetVg, nextChild + 1, target.mChildren.size());
                        final AsyncApplyTask reapplyTask = rvToApply.getInternalAsyncApplyTask(context, targetVg, null, params, null, recycled.mRoot);
                        final ViewTree tree = reapplyTask.doInBackground(new Void[0]);
                        if (tree == null) {
                            throw new ActionException(reapplyTask.mError);
                        }
                        return new RuntimeAction() { // from class: android.widget.RemoteViews.ViewGroupActionAdd.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super();
                            }

                            @Override // android.widget.RemoteViews.Action
                            public void apply(View root2, ViewGroup rootParent2, ActionApplyParams params2) throws ActionException {
                                reapplyTask.onPostExecute(tree);
                                int i = recycledViewIndex;
                                int i2 = nextChild;
                                if (i > i2) {
                                    targetVg.removeViews(i2, i - i2);
                                }
                            }
                        };
                    }
                    target.removeChildren(nextChild, (recycledViewIndex - nextChild) + 1);
                    return insertNewView(context, target, params, new Runnable() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewGroup.this.removeViews(r1, (recycledViewIndex - nextChild) + 1);
                        }
                    });
                }
            }
            return insertNewView(context, target, params, new Runnable() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteViews.ViewGroupActionAdd.lambda$initActionAsync$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$initActionAsync$0(View view) {
            return RemoteViews.getStableId(view) == this.mStableId;
        }

        static /* synthetic */ void lambda$initActionAsync$2() {
        }

        private Action insertNewView(Context context, ViewTree target, ActionApplyParams params, final Runnable finalizeAction) {
            final ViewGroup targetVg = (ViewGroup) target.mRoot;
            int nextChild = RemoteViews.getNextRecyclableChild(targetVg);
            final AsyncApplyTask task = this.mNestedViews.getInternalAsyncApplyTask(context, targetVg, null, params, null, null);
            final ViewTree tree = task.doInBackground(new Void[0]);
            if (tree == null) {
                throw new ActionException(task.mError);
            }
            if (this.mStableId != -1) {
                RemoteViews.setStableId(task.mResult, this.mStableId);
            }
            int i = this.mIndex;
            if (i < 0) {
                i = nextChild;
            }
            final int insertIndex = i;
            target.addChild(tree, insertIndex);
            if (nextChild >= 0) {
                RemoteViews.setNextRecyclableChild(targetVg, nextChild + 1, target.mChildren.size());
            }
            return new RuntimeAction() { // from class: android.widget.RemoteViews.ViewGroupActionAdd.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(View root, ViewGroup rootParent, ActionApplyParams params2) {
                    task.onPostExecute(tree);
                    finalizeAction.run();
                    targetVg.addView(task.mResult, insertIndex);
                }
            };
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }

        @Override // android.widget.RemoteViews.Action
        public boolean prefersAsyncApply() {
            return this.mNestedViews.prefersAsyncApply();
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 4;
        }

        @Override // android.widget.RemoteViews.Action
        public final void visitUris(Consumer<Uri> visitor) {
            this.mNestedViews.visitUris(visitor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewGroupActionRemove extends Action {
        private static final int REMOVE_ALL_VIEWS_ID = -2;
        private int mViewIdToKeep;

        ViewGroupActionRemove(int viewId) {
            this(viewId, -2);
        }

        ViewGroupActionRemove(int viewId, int viewIdToKeep) {
            super();
            this.viewId = viewId;
            this.mViewIdToKeep = viewIdToKeep;
        }

        ViewGroupActionRemove(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.mViewIdToKeep = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.mViewIdToKeep);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            ViewGroup target = (ViewGroup) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (this.mViewIdToKeep == -2) {
                for (int i = target.getChildCount() - 1; i >= 0; i--) {
                    if (!RemoteViews.hasStableId(target.getChildAt(i))) {
                        target.removeViewAt(i);
                    }
                }
                RemoteViews.setNextRecyclableChild(target, 0, target.getChildCount());
                return;
            }
            removeAllViewsExceptIdToKeep(target);
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            root.createTree();
            ViewTree target = root.findViewTreeById(this.viewId);
            if (target == null || !(target.mRoot instanceof ViewGroup)) {
                return RemoteViews.ACTION_NOOP;
            }
            final ViewGroup targetVg = (ViewGroup) target.mRoot;
            if (this.mViewIdToKeep == -2) {
                target.mChildren.removeIf(new Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionRemove$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return RemoteViews.ViewGroupActionRemove.lambda$initActionAsync$0((RemoteViews.ViewTree) obj);
                    }
                });
                RemoteViews.setNextRecyclableChild(targetVg, 0, target.mChildren.size());
            } else {
                target.mChildren.removeIf(new Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionRemove$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$initActionAsync$1;
                        lambda$initActionAsync$1 = RemoteViews.ViewGroupActionRemove.this.lambda$initActionAsync$1((RemoteViews.ViewTree) obj);
                        return lambda$initActionAsync$1;
                    }
                });
                if (target.mChildren.isEmpty()) {
                    target.mChildren = null;
                }
            }
            return new RuntimeAction() { // from class: android.widget.RemoteViews.ViewGroupActionRemove.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(View root2, ViewGroup rootParent2, ActionApplyParams params2) {
                    if (ViewGroupActionRemove.this.mViewIdToKeep == -2) {
                        for (int i = targetVg.getChildCount() - 1; i >= 0; i--) {
                            if (!RemoteViews.hasStableId(targetVg.getChildAt(i))) {
                                targetVg.removeViewAt(i);
                            }
                        }
                        return;
                    }
                    ViewGroupActionRemove.this.removeAllViewsExceptIdToKeep(targetVg);
                }
            };
        }

        static /* synthetic */ boolean lambda$initActionAsync$0(ViewTree childTree) {
            return !RemoteViews.hasStableId(childTree.mRoot);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$initActionAsync$1(ViewTree childTree) {
            return childTree.mRoot.getId() != this.mViewIdToKeep;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeAllViewsExceptIdToKeep(ViewGroup viewGroup) {
            for (int index = viewGroup.getChildCount() - 1; index >= 0; index--) {
                if (viewGroup.getChildAt(index).getId() != this.mViewIdToKeep) {
                    viewGroup.removeViewAt(index);
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 7;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }
    }

    private static class RemoveFromParentAction extends Action {
        RemoveFromParentAction(int viewId) {
            super();
            this.viewId = viewId;
        }

        RemoveFromParentAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null || target == root) {
                return;
            }
            ViewParent parent = target.getParent();
            if (parent instanceof ViewManager) {
                ((ViewManager) parent).removeView(target);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            root.createTree();
            final ViewTree target = root.findViewTreeById(this.viewId);
            if (target == null || target == root) {
                return RemoteViews.ACTION_NOOP;
            }
            ViewTree parent = root.findViewTreeParentOf(target);
            if (parent == null || !(parent.mRoot instanceof ViewManager)) {
                return RemoteViews.ACTION_NOOP;
            }
            final ViewManager parentVg = (ViewManager) parent.mRoot;
            parent.mChildren.remove(target);
            return new RuntimeAction() { // from class: android.widget.RemoteViews.RemoveFromParentAction.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(View root2, ViewGroup rootParent2, ActionApplyParams params2) {
                    parentVg.removeView(target.mRoot);
                }
            };
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 23;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }
    }

    private static class TextViewDrawableAction extends Action {

        /* renamed from: d1 */
        int f607d1;

        /* renamed from: d2 */
        int f608d2;

        /* renamed from: d3 */
        int f609d3;

        /* renamed from: d4 */
        int f610d4;
        boolean drawablesLoaded;

        /* renamed from: i1 */
        Icon f611i1;

        /* renamed from: i2 */
        Icon f612i2;

        /* renamed from: i3 */
        Icon f613i3;

        /* renamed from: i4 */
        Icon f614i4;
        Drawable id1;
        Drawable id2;
        Drawable id3;
        Drawable id4;
        boolean isRelative;
        boolean useIcons;

        public TextViewDrawableAction(int viewId, boolean isRelative, int d1, int d2, int d3, int d4) {
            super();
            this.isRelative = false;
            this.useIcons = false;
            this.drawablesLoaded = false;
            this.viewId = viewId;
            this.isRelative = isRelative;
            this.useIcons = false;
            this.f607d1 = d1;
            this.f608d2 = d2;
            this.f609d3 = d3;
            this.f610d4 = d4;
        }

        public TextViewDrawableAction(int viewId, boolean isRelative, Icon i1, Icon i2, Icon i3, Icon i4) {
            super();
            this.isRelative = false;
            this.useIcons = false;
            this.drawablesLoaded = false;
            this.viewId = viewId;
            this.isRelative = isRelative;
            this.useIcons = true;
            this.f611i1 = i1;
            this.f612i2 = i2;
            this.f613i3 = i3;
            this.f614i4 = i4;
        }

        public TextViewDrawableAction(Parcel parcel) {
            super();
            this.isRelative = false;
            this.useIcons = false;
            this.drawablesLoaded = false;
            this.viewId = parcel.readInt();
            this.isRelative = parcel.readInt() != 0;
            boolean z = parcel.readInt() != 0;
            this.useIcons = z;
            if (z) {
                this.f611i1 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                this.f612i2 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                this.f613i3 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                this.f614i4 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                return;
            }
            this.f607d1 = parcel.readInt();
            this.f608d2 = parcel.readInt();
            this.f609d3 = parcel.readInt();
            this.f610d4 = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.viewId);
            parcel.writeInt(this.isRelative ? 1 : 0);
            parcel.writeInt(this.useIcons ? 1 : 0);
            if (this.useIcons) {
                parcel.writeTypedObject(this.f611i1, 0);
                parcel.writeTypedObject(this.f612i2, 0);
                parcel.writeTypedObject(this.f613i3, 0);
                parcel.writeTypedObject(this.f614i4, 0);
                return;
            }
            parcel.writeInt(this.f607d1);
            parcel.writeInt(this.f608d2);
            parcel.writeInt(this.f609d3);
            parcel.writeInt(this.f610d4);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (this.drawablesLoaded) {
                if (this.isRelative) {
                    target.setCompoundDrawablesRelativeWithIntrinsicBounds(this.id1, this.id2, this.id3, this.id4);
                    return;
                } else {
                    target.setCompoundDrawablesWithIntrinsicBounds(this.id1, this.id2, this.id3, this.id4);
                    return;
                }
            }
            if (this.useIcons) {
                Context ctx = target.getContext();
                Icon icon = this.f611i1;
                Drawable id1 = icon == null ? null : icon.loadDrawable(ctx);
                Icon icon2 = this.f612i2;
                Drawable id2 = icon2 == null ? null : icon2.loadDrawable(ctx);
                Icon icon3 = this.f613i3;
                Drawable id3 = icon3 == null ? null : icon3.loadDrawable(ctx);
                Icon icon4 = this.f614i4;
                Drawable id4 = icon4 != null ? icon4.loadDrawable(ctx) : null;
                if (this.isRelative) {
                    target.setCompoundDrawablesRelativeWithIntrinsicBounds(id1, id2, id3, id4);
                    return;
                } else {
                    target.setCompoundDrawablesWithIntrinsicBounds(id1, id2, id3, id4);
                    return;
                }
            }
            if (this.isRelative) {
                target.setCompoundDrawablesRelativeWithIntrinsicBounds(this.f607d1, this.f608d2, this.f609d3, this.f610d4);
            } else {
                target.setCompoundDrawablesWithIntrinsicBounds(this.f607d1, this.f608d2, this.f609d3, this.f610d4);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            TextViewDrawableAction copy;
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return RemoteViews.ACTION_NOOP;
            }
            if (this.useIcons) {
                copy = new TextViewDrawableAction(this.viewId, this.isRelative, this.f611i1, this.f612i2, this.f613i3, this.f614i4);
            } else {
                copy = new TextViewDrawableAction(this.viewId, this.isRelative, this.f607d1, this.f608d2, this.f609d3, this.f610d4);
            }
            copy.drawablesLoaded = true;
            Context ctx = target.getContext();
            if (this.useIcons) {
                Icon icon = this.f611i1;
                copy.id1 = icon == null ? null : icon.loadDrawable(ctx);
                Icon icon2 = this.f612i2;
                copy.id2 = icon2 == null ? null : icon2.loadDrawable(ctx);
                Icon icon3 = this.f613i3;
                copy.id3 = icon3 == null ? null : icon3.loadDrawable(ctx);
                Icon icon4 = this.f614i4;
                copy.id4 = icon4 != null ? icon4.loadDrawable(ctx) : null;
            } else {
                int i = this.f607d1;
                copy.id1 = i == 0 ? null : ctx.getDrawable(i);
                int i2 = this.f608d2;
                copy.id2 = i2 == 0 ? null : ctx.getDrawable(i2);
                int i3 = this.f609d3;
                copy.id3 = i3 == 0 ? null : ctx.getDrawable(i3);
                int i4 = this.f610d4;
                copy.id4 = i4 != 0 ? ctx.getDrawable(i4) : null;
            }
            return copy;
        }

        @Override // android.widget.RemoteViews.Action
        public boolean prefersAsyncApply() {
            return this.useIcons;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 11;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            if (this.useIcons) {
                RemoteViews.visitIconUri(this.f611i1, visitor);
                RemoteViews.visitIconUri(this.f612i2, visitor);
                RemoteViews.visitIconUri(this.f613i3, visitor);
                RemoteViews.visitIconUri(this.f614i4, visitor);
            }
        }
    }

    private static class TextViewSizeAction extends Action {
        float size;
        int units;

        TextViewSizeAction(int viewId, int units, float size) {
            super();
            this.viewId = viewId;
            this.units = units;
            this.size = size;
        }

        TextViewSizeAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.units = parcel.readInt();
            this.size = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.units);
            dest.writeFloat(this.size);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.setTextSize(this.units, this.size);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 13;
        }
    }

    private static class ViewPaddingAction extends Action {
        int bottom;
        int left;
        int right;
        int top;

        public ViewPaddingAction(int viewId, int left, int top, int right, int bottom) {
            super();
            this.viewId = viewId;
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public ViewPaddingAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.left = parcel.readInt();
            this.top = parcel.readInt();
            this.right = parcel.readInt();
            this.bottom = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.left);
            dest.writeInt(this.top);
            dest.writeInt(this.right);
            dest.writeInt(this.bottom);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.setPadding(this.left, this.top, this.right, this.bottom);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 14;
        }
    }

    private static class LayoutParamAction extends Action {
        static final int LAYOUT_HEIGHT = 9;
        static final int LAYOUT_MARGIN_BOTTOM = 3;
        static final int LAYOUT_MARGIN_END = 5;
        static final int LAYOUT_MARGIN_LEFT = 0;
        static final int LAYOUT_MARGIN_RIGHT = 2;
        static final int LAYOUT_MARGIN_START = 4;
        static final int LAYOUT_MARGIN_TOP = 1;
        static final int LAYOUT_WIDTH = 8;
        final int mAnimatorId;
        boolean mIsAnimationEnd;
        final int mProperty;
        final int mValue;
        final int mValueType;

        LayoutParamAction(int viewId, int property, float value, int units) {
            super();
            this.viewId = viewId;
            this.mProperty = property;
            this.mValueType = 2;
            this.mValue = TypedValue.createComplexDimension(value, units);
            this.mAnimatorId = -1;
            this.mIsAnimationEnd = false;
        }

        LayoutParamAction(int viewId, int property, int value, int valueType) {
            super();
            this.viewId = viewId;
            this.mProperty = property;
            this.mValueType = valueType;
            this.mValue = value;
            this.mAnimatorId = -1;
            this.mIsAnimationEnd = false;
        }

        LayoutParamAction(int viewId, int property, int animatorId) {
            super();
            this.viewId = viewId;
            this.mProperty = property;
            this.mValueType = 101;
            this.mValue = 0;
            this.mAnimatorId = animatorId;
            this.mIsAnimationEnd = false;
        }

        public LayoutParamAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.mProperty = parcel.readInt();
            this.mValueType = parcel.readInt();
            this.mValue = parcel.readInt();
            this.mAnimatorId = parcel.readInt();
            this.mIsAnimationEnd = parcel.readBoolean();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.mProperty);
            dest.writeInt(this.mValueType);
            dest.writeInt(this.mValue);
            dest.writeInt(this.mAnimatorId);
            dest.writeBoolean(this.mIsAnimationEnd);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            ViewGroup.LayoutParams layoutParams;
            View target = root.findViewById(this.viewId);
            if (target == null || (layoutParams = target.getLayoutParams()) == null) {
                return;
            }
            switch (this.mProperty) {
                case 0:
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getPixelOffset(target);
                        target.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 1:
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getPixelOffset(target);
                        target.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 2:
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = getPixelOffset(target);
                        target.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 3:
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = getPixelOffset(target);
                        target.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 4:
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(getPixelOffset(target));
                        target.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 5:
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ((ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(getPixelOffset(target));
                        target.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                case 6:
                case 7:
                default:
                    throw new IllegalArgumentException("Unknown property " + this.mProperty);
                case 8:
                    if (this.mAnimatorId == -1) {
                        layoutParams.width = getPixelSize(target);
                        target.setLayoutParams(layoutParams);
                        return;
                    } else {
                        startValueAnimator(target, layoutParams);
                        return;
                    }
                case 9:
                    if (this.mAnimatorId == -1) {
                        layoutParams.height = getPixelSize(target);
                        target.setLayoutParams(layoutParams);
                        return;
                    } else {
                        startValueAnimator(target, layoutParams);
                        return;
                    }
            }
        }

        private void startValueAnimator(final View target, final ViewGroup.LayoutParams layoutParams) {
            ValueAnimator animator = (ValueAnimator) AnimatorInflater.loadAnimator(target.getContext(), this.mAnimatorId);
            if (animator == null) {
                return;
            }
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.RemoteViews.LayoutParamAction.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = ((Integer) animation.getAnimatedValue()).intValue();
                    layoutParams.width = animatedValue;
                    target.setLayoutParams(layoutParams);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.RemoteViews.LayoutParamAction.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    LayoutParamAction.this.mIsAnimationEnd = true;
                    PropertyValuesHolder[] values = ((ValueAnimator) animation).getValues();
                    if (values != null) {
                        PropertyValuesHolder pvh = values[0];
                        PropertyValuesHolder.PropertyValues value = new PropertyValuesHolder.PropertyValues();
                        if (pvh != null) {
                            pvh.getPropertyValues(value);
                            layoutParams.width = ((Integer) value.endValue).intValue();
                            target.setLayoutParams(layoutParams);
                        }
                    }
                }
            });
            if (this.mIsAnimationEnd) {
                animator.setDuration(0L);
            }
            animator.start();
        }

        private int getPixelOffset(View target) {
            try {
                switch (this.mValueType) {
                    case 2:
                        return TypedValue.complexToDimensionPixelOffset(this.mValue, target.getResources().getDisplayMetrics());
                    case 3:
                        if (this.mValue == 0) {
                            return 0;
                        }
                        return target.getResources().getDimensionPixelOffset(this.mValue);
                    case 4:
                        TypedArray typedArray = target.getContext().obtainStyledAttributes(new int[]{this.mValue});
                        try {
                            return typedArray.getDimensionPixelOffset(0, 0);
                        } finally {
                            typedArray.recycle();
                        }
                    default:
                        return this.mValue;
                }
            } catch (Throwable t) {
                throw new ActionException(t);
            }
            throw new ActionException(t);
        }

        private int getPixelSize(View target) {
            try {
                switch (this.mValueType) {
                    case 2:
                        return TypedValue.complexToDimensionPixelSize(this.mValue, target.getResources().getDisplayMetrics());
                    case 3:
                        if (this.mValue == 0) {
                            return 0;
                        }
                        return target.getResources().getDimensionPixelSize(this.mValue);
                    case 4:
                        TypedArray typedArray = target.getContext().obtainStyledAttributes(new int[]{this.mValue});
                        try {
                            return typedArray.getDimensionPixelSize(0, 0);
                        } finally {
                            typedArray.recycle();
                        }
                    default:
                        return this.mValue;
                }
            } catch (Throwable t) {
                throw new ActionException(t);
            }
            throw new ActionException(t);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 19;
        }

        @Override // android.widget.RemoteViews.Action
        public String getUniqueKey() {
            return super.getUniqueKey() + this.mProperty;
        }
    }

    private static class SetRemoteInputsAction extends Action {
        final Parcelable[] remoteInputs;

        public SetRemoteInputsAction(int viewId, RemoteInput[] remoteInputs) {
            super();
            this.viewId = viewId;
            this.remoteInputs = remoteInputs;
        }

        public SetRemoteInputsAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.remoteInputs = (Parcelable[]) parcel.createTypedArray(RemoteInput.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeTypedArray(this.remoteInputs, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.setTagInternal(C4337R.id.remote_input_tag, this.remoteInputs);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 18;
        }
    }

    private static class OverrideTextColorsAction extends Action {
        private final int textColor;

        public OverrideTextColorsAction(int textColor) {
            super();
            this.textColor = textColor;
        }

        public OverrideTextColorsAction(Parcel parcel) {
            super();
            this.textColor = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.textColor);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            Stack<View> viewsToProcess = new Stack<>();
            viewsToProcess.add(root);
            while (!viewsToProcess.isEmpty()) {
                View v = viewsToProcess.pop();
                if (v instanceof TextView) {
                    TextView textView = (TextView) v;
                    textView.setText(ContrastColorUtil.clearColorSpans(textView.getText()));
                    textView.setTextColor(this.textColor);
                }
                if (v instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) v;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        viewsToProcess.push(viewGroup.getChildAt(i));
                    }
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 20;
        }
    }

    private static class SetIntTagAction extends Action {
        private final int mKey;
        private final int mTag;
        private final int mViewId;

        SetIntTagAction(int viewId, int key, int tag) {
            super();
            this.mViewId = viewId;
            this.mKey = key;
            this.mTag = tag;
        }

        SetIntTagAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mKey = parcel.readInt();
            this.mTag = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mKey);
            dest.writeInt(this.mTag);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            target.setTagInternal(this.mKey, Integer.valueOf(this.mTag));
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 22;
        }
    }

    private static class SetCompoundButtonCheckedAction extends Action {
        private final boolean mChecked;

        SetCompoundButtonCheckedAction(int viewId, boolean checked) {
            super();
            this.viewId = viewId;
            this.mChecked = checked;
        }

        SetCompoundButtonCheckedAction(Parcel in) {
            super();
            this.viewId = in.readInt();
            this.mChecked = in.readBoolean();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeBoolean(this.mChecked);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (!(target instanceof CompoundButton)) {
                Log.m102w(RemoteViews.LOG_TAG, "Cannot set checked to view " + this.viewId + " because it is not a CompoundButton");
                return;
            }
            CompoundButton button = (CompoundButton) target;
            Object tag = button.getTag(C4337R.id.remote_checked_change_listener_tag);
            if (tag instanceof CompoundButton.OnCheckedChangeListener) {
                button.setOnCheckedChangeListener(null);
                button.setChecked(this.mChecked);
                button.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) tag);
                return;
            }
            button.setChecked(this.mChecked);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 26;
        }
    }

    private static class SetRadioGroupCheckedAction extends Action {
        private final int mCheckedId;

        SetRadioGroupCheckedAction(int viewId, int checkedId) {
            super();
            this.viewId = viewId;
            this.mCheckedId = checkedId;
        }

        SetRadioGroupCheckedAction(Parcel in) {
            super();
            this.viewId = in.readInt();
            this.mCheckedId = in.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.mCheckedId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (!(target instanceof RadioGroup)) {
                Log.m102w(RemoteViews.LOG_TAG, "Cannot check " + this.viewId + " because it's not a RadioGroup");
                return;
            }
            RadioGroup group = (RadioGroup) target;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                if ((child instanceof CompoundButton) && (child.getTag(C4337R.id.remote_checked_change_listener_tag) instanceof CompoundButton.OnCheckedChangeListener)) {
                    ((CompoundButton) child).setOnCheckedChangeListener(null);
                }
            }
            int i2 = this.mCheckedId;
            group.check(i2);
            for (int i3 = 0; i3 < group.getChildCount(); i3++) {
                View child2 = group.getChildAt(i3);
                if (child2 instanceof CompoundButton) {
                    Object tag = child2.getTag(C4337R.id.remote_checked_change_listener_tag);
                    if (tag instanceof CompoundButton.OnCheckedChangeListener) {
                        ((CompoundButton) child2).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) tag);
                    }
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 27;
        }
    }

    private static class SetViewOutlinePreferredRadiusAction extends Action {
        private final int mValue;
        private final int mValueType;

        SetViewOutlinePreferredRadiusAction(int viewId, int value, int valueType) {
            super();
            this.viewId = viewId;
            this.mValueType = valueType;
            this.mValue = value;
        }

        SetViewOutlinePreferredRadiusAction(int viewId, float radius, int units) {
            super();
            this.viewId = viewId;
            this.mValueType = 2;
            this.mValue = TypedValue.createComplexDimension(radius, units);
        }

        SetViewOutlinePreferredRadiusAction(Parcel in) {
            super();
            this.viewId = in.readInt();
            this.mValueType = in.readInt();
            this.mValue = in.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeInt(this.mValueType);
            dest.writeInt(this.mValue);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            float radius;
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            try {
                switch (this.mValueType) {
                    case 2:
                        radius = TypedValue.complexToDimension(this.mValue, target.getResources().getDisplayMetrics());
                        target.setOutlineProvider(new RemoteViewOutlineProvider(radius));
                        return;
                    case 3:
                        radius = this.mValue != 0 ? target.getResources().getDimension(this.mValue) : 0.0f;
                        target.setOutlineProvider(new RemoteViewOutlineProvider(radius));
                        return;
                    case 4:
                        TypedArray typedArray = target.getContext().obtainStyledAttributes(new int[]{this.mValue});
                        try {
                            radius = typedArray.getDimension(0, 0.0f);
                            typedArray.recycle();
                            target.setOutlineProvider(new RemoteViewOutlineProvider(radius));
                            return;
                        } catch (Throwable th) {
                            typedArray.recycle();
                            throw th;
                        }
                    default:
                        radius = this.mValue;
                        target.setOutlineProvider(new RemoteViewOutlineProvider(radius));
                        return;
                }
            } catch (Throwable t) {
                throw new ActionException(t);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 28;
        }
    }

    public static final class RemoteViewOutlineProvider extends ViewOutlineProvider {
        private final float mRadius;

        public RemoteViewOutlineProvider(float radius) {
            this.mRadius = radius;
        }

        public float getRadius() {
            return this.mRadius;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.mRadius);
        }
    }

    public RemoteViews(String packageName, int layoutId) {
        this(getApplicationInfo(packageName, UserHandle.myUserId()), layoutId);
    }

    public RemoteViews(String packageName, int layoutId, int viewId) {
        this(packageName, layoutId);
        this.mViewId = viewId;
    }

    protected RemoteViews(ApplicationInfo application, int layoutId) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        ApplicationInfoCache applicationInfoCache = new ApplicationInfoCache();
        this.mApplicationInfoCache = applicationInfoCache;
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        this.mApplication = application;
        this.mLayoutId = layoutId;
        applicationInfoCache.put(application);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasMultipleLayouts() {
        return hasLandscapeAndPortraitLayouts() || hasSizedRemoteViews();
    }

    private boolean hasLandscapeAndPortraitLayouts() {
        return (this.mLandscape == null || this.mPortrait == null) ? false : true;
    }

    private boolean hasSizedRemoteViews() {
        return this.mSizedRemoteViews != null;
    }

    private SizeF getIdealSize() {
        return this.mIdealSize;
    }

    private void setIdealSize(SizeF size) {
        this.mIdealSize = size;
    }

    private RemoteViews findSmallestRemoteView() {
        return this.mSizedRemoteViews.get(r0.size() - 1);
    }

    public RemoteViews(RemoteViews landscape, RemoteViews portrait) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mApplicationInfoCache = new ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        if (landscape == null || portrait == null) {
            throw new IllegalArgumentException("Both RemoteViews must be non-null");
        }
        if (!landscape.hasSameAppInfo(portrait.mApplication)) {
            throw new IllegalArgumentException("Both RemoteViews must share the same package and user");
        }
        this.mApplication = portrait.mApplication;
        this.mLayoutId = portrait.mLayoutId;
        this.mViewId = portrait.mViewId;
        this.mLightBackgroundLayoutId = portrait.mLightBackgroundLayoutId;
        this.mLandscape = landscape;
        this.mPortrait = portrait;
        Map<Class, Object> map = portrait.mClassCookies;
        this.mClassCookies = map == null ? landscape.mClassCookies : map;
        configureDescendantsAsChildren();
    }

    public RemoteViews(Map<SizeF, RemoteViews> remoteViews) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mApplicationInfoCache = new ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        if (remoteViews.isEmpty()) {
            throw new IllegalArgumentException("The set of RemoteViews cannot be empty");
        }
        if (remoteViews.size() <= 16) {
            if (remoteViews.size() == 1) {
                RemoteViews single = remoteViews.values().iterator().next();
                initializeFrom(single, single);
                return;
            }
            this.mClassCookies = initializeSizedRemoteViews(remoteViews.entrySet().stream().map(new Function() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return RemoteViews.lambda$new$2((Map.Entry) obj);
                }
            }).iterator());
            RemoteViews smallestView = findSmallestRemoteView();
            this.mApplication = smallestView.mApplication;
            this.mLayoutId = smallestView.mLayoutId;
            this.mViewId = smallestView.mViewId;
            this.mLightBackgroundLayoutId = smallestView.mLightBackgroundLayoutId;
            configureDescendantsAsChildren();
            return;
        }
        throw new IllegalArgumentException("Too many RemoteViews in constructor");
    }

    static /* synthetic */ RemoteViews lambda$new$2(Map.Entry entry) {
        ((RemoteViews) entry.getValue()).setIdealSize((SizeF) entry.getKey());
        return (RemoteViews) entry.getValue();
    }

    private Map<Class, Object> initializeSizedRemoteViews(Iterator<RemoteViews> remoteViews) {
        List<RemoteViews> sizedRemoteViews = new ArrayList<>();
        Map<Class, Object> classCookies = null;
        float viewArea = Float.MAX_VALUE;
        RemoteViews smallestView = null;
        while (remoteViews.hasNext()) {
            RemoteViews view = remoteViews.next();
            SizeF size = view.getIdealSize();
            if (size == null) {
                throw new IllegalStateException("Expected RemoteViews to have ideal size");
            }
            float newViewArea = size.getWidth() * size.getHeight();
            if (smallestView != null && !view.hasSameAppInfo(smallestView.mApplication)) {
                throw new IllegalArgumentException("All RemoteViews must share the same package and user");
            }
            if (smallestView == null || newViewArea < viewArea) {
                if (smallestView != null) {
                    sizedRemoteViews.add(smallestView);
                }
                viewArea = newViewArea;
                smallestView = view;
            } else {
                sizedRemoteViews.add(view);
            }
            view.setIdealSize(size);
            if (classCookies == null) {
                classCookies = view.mClassCookies;
            }
        }
        sizedRemoteViews.add(smallestView);
        this.mSizedRemoteViews = sizedRemoteViews;
        return classCookies;
    }

    public RemoteViews(RemoteViews src) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mApplicationInfoCache = new ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        initializeFrom(src, null);
    }

    private RemoteViews() {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mApplicationInfoCache = new ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
    }

    private static RemoteViews createInitializedFrom(RemoteViews src, RemoteViews hierarchyRoot) {
        RemoteViews child = new RemoteViews();
        child.initializeFrom(src, hierarchyRoot);
        return child;
    }

    private void initializeFrom(RemoteViews src, RemoteViews hierarchyRoot) {
        if (hierarchyRoot == null) {
            this.mBitmapCache = src.mBitmapCache;
            this.mApplicationInfoCache = src.mApplicationInfoCache;
        } else {
            this.mBitmapCache = hierarchyRoot.mBitmapCache;
            this.mApplicationInfoCache = hierarchyRoot.mApplicationInfoCache;
        }
        if (hierarchyRoot == null || src.mIsRoot) {
            this.mIsRoot = true;
            hierarchyRoot = this;
        } else {
            this.mIsRoot = false;
        }
        this.mApplication = src.mApplication;
        this.mLayoutId = src.mLayoutId;
        this.mLightBackgroundLayoutId = src.mLightBackgroundLayoutId;
        this.mApplyFlags = src.mApplyFlags;
        this.mClassCookies = src.mClassCookies;
        this.mIdealSize = src.mIdealSize;
        this.mProviderInstanceId = src.mProviderInstanceId;
        this.mAllowOtherRootParent = src.mAllowOtherRootParent;
        this.mAppWidgetId = src.mAppWidgetId;
        if (src.hasLandscapeAndPortraitLayouts()) {
            this.mLandscape = createInitializedFrom(src.mLandscape, hierarchyRoot);
            this.mPortrait = createInitializedFrom(src.mPortrait, hierarchyRoot);
        }
        if (src.hasSizedRemoteViews()) {
            this.mSizedRemoteViews = new ArrayList(src.mSizedRemoteViews.size());
            for (RemoteViews srcView : src.mSizedRemoteViews) {
                this.mSizedRemoteViews.add(createInitializedFrom(srcView, hierarchyRoot));
            }
        }
        if (src.mActions != null) {
            Parcel p = Parcel.obtain();
            p.putClassCookies(this.mClassCookies);
            src.writeActionsToParcel(p, 0);
            p.setDataPosition(0);
            readActionsFromParcel(p, 0);
            p.recycle();
        }
        if (this.mIsRoot) {
            reconstructCaches();
        }
        this.mIsAllowPendintIntentInCollection = src.mIsAllowPendintIntentInCollection;
    }

    public RemoteViews(Parcel parcel) {
        this(parcel, null, null, 0);
    }

    private RemoteViews(Parcel parcel, HierarchyRootData rootData, ApplicationInfo info, int depth) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mApplicationInfoCache = new ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        if (depth > 10 && UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            throw new IllegalArgumentException("Too many nested views.");
        }
        int depth2 = depth + 1;
        int mode = parcel.readInt();
        if (rootData == null) {
            this.mBitmapCache = new BitmapCache(parcel);
            this.mClassCookies = parcel.copyClassCookies();
        } else {
            configureAsChild(rootData);
        }
        if (mode == 0) {
            this.mApplication = ApplicationInfo.CREATOR.createFromParcel(parcel);
            this.mIdealSize = parcel.readInt() != 0 ? SizeF.CREATOR.createFromParcel(parcel) : null;
            this.mLayoutId = parcel.readInt();
            this.mViewId = parcel.readInt();
            this.mLightBackgroundLayoutId = parcel.readInt();
            readActionsFromParcel(parcel, depth2);
        } else if (mode == 2) {
            int numViews = parcel.readInt();
            if (numViews > 16) {
                throw new IllegalArgumentException("Too many views in mapping from size to RemoteViews.");
            }
            List<RemoteViews> remoteViews = new ArrayList<>(numViews);
            for (int i = 0; i < numViews; i++) {
                RemoteViews view = new RemoteViews(parcel, getHierarchyRootData(), info, depth2);
                info = view.mApplication;
                remoteViews.add(view);
            }
            initializeSizedRemoteViews(remoteViews.iterator());
            RemoteViews smallestView = findSmallestRemoteView();
            this.mApplication = smallestView.mApplication;
            this.mLayoutId = smallestView.mLayoutId;
            this.mViewId = smallestView.mViewId;
            this.mLightBackgroundLayoutId = smallestView.mLightBackgroundLayoutId;
        } else {
            this.mLandscape = new RemoteViews(parcel, getHierarchyRootData(), info, depth2);
            RemoteViews remoteViews2 = new RemoteViews(parcel, getHierarchyRootData(), this.mLandscape.mApplication, depth2);
            this.mPortrait = remoteViews2;
            this.mApplication = remoteViews2.mApplication;
            this.mLayoutId = remoteViews2.mLayoutId;
            this.mViewId = remoteViews2.mViewId;
            this.mLightBackgroundLayoutId = remoteViews2.mLightBackgroundLayoutId;
        }
        this.mApplyFlags = parcel.readInt();
        this.mProviderInstanceId = parcel.readLong();
        this.mAllowOtherRootParent = parcel.readBoolean();
        this.mAppWidgetId = parcel.readInt();
        if (this.mIsRoot) {
            configureDescendantsAsChildren();
        }
        this.mIsAllowPendintIntentInCollection = parcel.readBoolean();
    }

    private void readActionsFromParcel(Parcel parcel, int depth) {
        int count = parcel.readInt();
        if (count > 0) {
            this.mActions = new ArrayList<>(count);
            synchronized (this.mActionsLock) {
                for (int i = 0; i < count; i++) {
                    this.mActions.add(getActionFromParcel(parcel, depth));
                }
            }
        }
    }

    private Action getActionFromParcel(Parcel parcel, int depth) {
        int tag = parcel.readInt();
        switch (tag) {
            case 1:
                return new SetOnClickResponse(parcel);
            case 2:
                return new ReflectionAction(parcel);
            case 3:
                return new SetDrawableTint(parcel);
            case 4:
                return new ViewGroupActionAdd(parcel, this.mApplication, depth);
            case 5:
                return new ViewContentNavigation(parcel);
            case 6:
                return new SetEmptyView(parcel);
            case 7:
                return new ViewGroupActionRemove(parcel);
            case 8:
                return new SetPendingIntentTemplate(parcel);
            case 10:
                return new SetRemoteViewsAdapterIntent(parcel);
            case 11:
                return new TextViewDrawableAction(parcel);
            case 12:
                return new BitmapReflectionAction(parcel);
            case 13:
                return new TextViewSizeAction(parcel);
            case 14:
                return new ViewPaddingAction(parcel);
            case 15:
                return new SetRemoteViewsAdapterList(parcel);
            case 18:
                return new SetRemoteInputsAction(parcel);
            case 19:
                return new LayoutParamAction(parcel);
            case 20:
                return new OverrideTextColorsAction(parcel);
            case 21:
                return new SetRippleDrawableColor(parcel);
            case 22:
                return new SetIntTagAction(parcel);
            case 23:
                return new RemoveFromParentAction(parcel);
            case 24:
                return new ResourceReflectionAction(parcel);
            case 25:
                return new ComplexUnitDimensionReflectionAction(parcel);
            case 26:
                return new SetCompoundButtonCheckedAction(parcel);
            case 27:
                return new SetRadioGroupCheckedAction(parcel);
            case 28:
                return new SetViewOutlinePreferredRadiusAction(parcel);
            case 29:
                return new SetOnCheckedChangeResponse(parcel);
            case 30:
                return new NightModeReflectionAction(parcel);
            case 31:
                return new SetRemoteCollectionItemListAdapterAction(parcel);
            case 32:
                return new AttributeReflectionAction(parcel);
            case 41:
                return new ClearAllTextEffectAction(parcel);
            case 42:
                return new AddOuterShadowAction(parcel);
            case 43:
                return new AddInnerShadowAction(parcel);
            case 44:
                return new AddStrokeAction(parcel);
            case 45:
                return new AddLinearGradientAction(parcel);
            case 46:
                return new AddOuterGlowAction(parcel);
            case 100:
                return new SemSetOnLongClickPendingIntent(parcel);
            case 101:
                return new SemSetLongClickPendingIntentTemplate(parcel);
            case 102:
                return new SemSetOnLongClickDragable(parcel);
            case 103:
                return new SemSetOnTouchPendingIntent(parcel);
            case 104:
                return new semSetOnCheckedChangedPendingIntent(parcel);
            case 105:
                return new semSetBlurInfoAction(parcel);
            case 106:
                return new ViewObjectAnimatorAction(parcel);
            case 107:
                return new SemAnimationAction(parcel);
            case 108:
                return new SetVectorDrawablePathColor(parcel);
            case 109:
                return new SetTextViewShadowAction(parcel);
            default:
                throw new ActionException("Tag " + tag + " not found");
        }
    }

    @Override // 
    @Deprecated
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public RemoteViews mo897clone() {
        Preconditions.checkState(this.mIsRoot, "RemoteView has been attached to another RemoteView. May only clone the root of a RemoteView hierarchy.");
        return new RemoteViews(this);
    }

    public String getPackage() {
        ApplicationInfo applicationInfo = this.mApplication;
        if (applicationInfo != null) {
            return applicationInfo.packageName;
        }
        return null;
    }

    public int getLayoutId() {
        int i;
        return (!hasFlags(4) || (i = this.mLightBackgroundLayoutId) == 0) ? this.mLayoutId : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configureAsChild(HierarchyRootData rootData) {
        this.mIsRoot = false;
        this.mBitmapCache = rootData.mBitmapCache;
        this.mApplicationInfoCache = rootData.mApplicationInfoCache;
        this.mClassCookies = rootData.mClassCookies;
        configureDescendantsAsChildren();
    }

    private void configureDescendantsAsChildren() {
        this.mApplication = this.mApplicationInfoCache.getOrPut(this.mApplication);
        HierarchyRootData rootData = getHierarchyRootData();
        if (hasSizedRemoteViews()) {
            for (RemoteViews remoteView : this.mSizedRemoteViews) {
                remoteView.configureAsChild(rootData);
            }
            return;
        }
        if (hasLandscapeAndPortraitLayouts()) {
            this.mLandscape.configureAsChild(rootData);
            this.mPortrait.configureAsChild(rootData);
            return;
        }
        synchronized (this.mActionsLock) {
            ArrayList<Action> arrayList = this.mActions;
            if (arrayList != null) {
                Iterator<Action> it = arrayList.iterator();
                while (it.hasNext()) {
                    Action action = it.next();
                    action.setHierarchyRootData(rootData);
                }
            }
        }
    }

    private void reconstructCaches() {
        if (this.mIsRoot) {
            this.mBitmapCache = new BitmapCache();
            ApplicationInfoCache applicationInfoCache = new ApplicationInfoCache();
            this.mApplicationInfoCache = applicationInfoCache;
            this.mApplication = applicationInfoCache.getOrPut(this.mApplication);
            configureDescendantsAsChildren();
        }
    }

    public int estimateMemoryUsage() {
        return this.mBitmapCache.getBitmapMemory();
    }

    private void addAction(Action a) {
        if (hasMultipleLayouts()) {
            throw new RuntimeException("RemoteViews specifying separate layouts for orientation or size cannot be modified. Instead, fully configure each layouts individually before constructing the combined layout.");
        }
        if (this.mActions == null) {
            this.mActions = new ArrayList<>();
        }
        synchronized (this.mActionsLock) {
            this.mActions.add(a);
        }
    }

    public void addView(int viewId, RemoteViews nestedView) {
        Action viewGroupActionAdd;
        if (nestedView == null) {
            viewGroupActionAdd = new ViewGroupActionRemove(viewId);
        } else {
            viewGroupActionAdd = new ViewGroupActionAdd(this, viewId, nestedView);
        }
        addAction(viewGroupActionAdd);
    }

    public void addStableView(int viewId, RemoteViews nestedView, int stableId) {
        addAction(new ViewGroupActionAdd(viewId, nestedView, -1, stableId));
    }

    public void addView(int viewId, RemoteViews nestedView, int index) {
        addAction(new ViewGroupActionAdd(this, viewId, nestedView, index));
    }

    public void removeAllViews(int viewId) {
        addAction(new ViewGroupActionRemove(viewId));
    }

    public void removeAllViewsExceptId(int viewId, int viewIdToKeep) {
        addAction(new ViewGroupActionRemove(viewId, viewIdToKeep));
    }

    public void removeFromParent(int viewId) {
        addAction(new RemoveFromParentAction(viewId));
    }

    @Deprecated
    public void showNext(int viewId) {
        addAction(new ViewContentNavigation(viewId, true));
    }

    @Deprecated
    public void showPrevious(int viewId) {
        addAction(new ViewContentNavigation(viewId, false));
    }

    public void setDisplayedChild(int viewId, int childIndex) {
        setInt(viewId, "setDisplayedChild", childIndex);
    }

    public void setViewVisibility(int viewId, int visibility) {
        setInt(viewId, "setVisibility", visibility);
    }

    public void setTextViewText(int viewId, CharSequence text) {
        setCharSequence(viewId, "setText", text);
    }

    public void setTextViewTextSize(int viewId, int units, float size) {
        addAction(new TextViewSizeAction(viewId, units, size));
    }

    public void setTextViewCompoundDrawables(int viewId, int left, int top, int right, int bottom) {
        addAction(new TextViewDrawableAction(viewId, false, left, top, right, bottom));
    }

    public void setTextViewCompoundDrawablesRelative(int viewId, int start, int top, int end, int bottom) {
        addAction(new TextViewDrawableAction(viewId, true, start, top, end, bottom));
    }

    public void setTextViewCompoundDrawables(int viewId, Icon left, Icon top, Icon right, Icon bottom) {
        addAction(new TextViewDrawableAction(viewId, false, left, top, right, bottom));
    }

    public void setTextViewCompoundDrawablesRelative(int viewId, Icon start, Icon top, Icon end, Icon bottom) {
        addAction(new TextViewDrawableAction(viewId, true, start, top, end, bottom));
    }

    public void setImageViewResource(int viewId, int srcId) {
        setInt(viewId, "setImageResource", srcId);
    }

    public void setImageViewUri(int viewId, Uri uri) {
        setUri(viewId, "setImageURI", uri);
    }

    public void setImageViewBitmap(int viewId, Bitmap bitmap) {
        setBitmap(viewId, "setImageBitmap", bitmap);
    }

    public void setImageViewIcon(int viewId, Icon icon) {
        setIcon(viewId, "setImageIcon", icon);
    }

    public void setEmptyView(int viewId, int emptyViewId) {
        addAction(new SetEmptyView(viewId, emptyViewId));
    }

    public void setChronometer(int viewId, long base, String format, boolean started) {
        setLong(viewId, "setBase", base);
        setString(viewId, "setFormat", format);
        setBoolean(viewId, "setStarted", started);
    }

    public void setChronometerCountDown(int viewId, boolean isCountDown) {
        setBoolean(viewId, "setCountDown", isCountDown);
    }

    public void setProgressBar(int viewId, int max, int progress, boolean indeterminate) {
        setBoolean(viewId, "setIndeterminate", indeterminate);
        if (!indeterminate) {
            setInt(viewId, "setMax", max);
            setInt(viewId, "setProgress", progress);
        }
    }

    public void setOnClickPendingIntent(int viewId, PendingIntent pendingIntent) {
        setOnClickResponse(viewId, RemoteResponse.fromPendingIntent(pendingIntent));
    }

    public void setOnClickResponse(int viewId, RemoteResponse response) {
        addAction(new SetOnClickResponse(viewId, response));
    }

    public void setPendingIntentTemplate(int viewId, PendingIntent pendingIntentTemplate) {
        addAction(new SetPendingIntentTemplate(viewId, pendingIntentTemplate));
    }

    public void setOnClickFillInIntent(int viewId, Intent fillInIntent) {
        setOnClickResponse(viewId, RemoteResponse.fromFillInIntent(fillInIntent));
    }

    public void setOnCheckedChangeResponse(int viewId, RemoteResponse response) {
        addAction(new SetOnCheckedChangeResponse(viewId, response.setInteractionType(1)));
    }

    public void setDrawableTint(int viewId, boolean targetBackground, int colorFilter, PorterDuff.Mode mode) {
        addAction(new SetDrawableTint(viewId, targetBackground, colorFilter, mode));
    }

    public void hidden_semSetVectorDrawablePathColor(int viewId, String targetPathName, int colorFilter) {
        addAction(new SetVectorDrawablePathColor(viewId, targetPathName, colorFilter));
    }

    public void hidden_semSetTextViewShadow(int viewId, float radius, float dx, float dy, int color) {
        addAction(new SetTextViewShadowAction(viewId, radius, dx, dy, color));
    }

    public void setRippleDrawableColor(int viewId, ColorStateList colorStateList) {
        addAction(new SetRippleDrawableColor(viewId, colorStateList));
    }

    public void setProgressTintList(int viewId, ColorStateList tint) {
        addAction(new ReflectionAction(viewId, "setProgressTintList", 15, tint));
    }

    public void setProgressBackgroundTintList(int viewId, ColorStateList tint) {
        addAction(new ReflectionAction(viewId, "setProgressBackgroundTintList", 15, tint));
    }

    public void setProgressIndeterminateTintList(int viewId, ColorStateList tint) {
        addAction(new ReflectionAction(viewId, "setIndeterminateTintList", 15, tint));
    }

    public void setTextColor(int viewId, int color) {
        setInt(viewId, "setTextColor", color);
    }

    public void setTextColor(int viewId, ColorStateList colors) {
        addAction(new ReflectionAction(viewId, "setTextColor", 15, colors));
    }

    @Deprecated
    public void setRemoteAdapter(int appWidgetId, int viewId, Intent intent) {
        setRemoteAdapter(viewId, intent);
    }

    public void setRemoteAdapter(int viewId, Intent intent) {
        addAction(new SetRemoteViewsAdapterIntent(viewId, intent));
    }

    @Deprecated
    public void setRemoteAdapter(int viewId, ArrayList<RemoteViews> list, int viewTypeCount) {
        addAction(new SetRemoteViewsAdapterList(viewId, list, viewTypeCount));
    }

    public void setRemoteAdapter(int viewId, RemoteCollectionItems items) {
        addAction(new SetRemoteCollectionItemListAdapterAction(viewId, items));
    }

    public void setScrollPosition(int viewId, int position) {
        setInt(viewId, "smoothScrollToPosition", position);
    }

    public void setRelativeScrollPosition(int viewId, int offset) {
        setInt(viewId, "smoothScrollByOffset", offset);
    }

    public void setViewPadding(int viewId, int left, int top, int right, int bottom) {
        addAction(new ViewPaddingAction(viewId, left, top, right, bottom));
    }

    public void setViewLayoutMarginDimen(int viewId, int type, int dimen) {
        addAction(new LayoutParamAction(viewId, type, dimen, 3));
    }

    public void setViewLayoutMarginAttr(int viewId, int type, int attr) {
        addAction(new LayoutParamAction(viewId, type, attr, 4));
    }

    public void setViewLayoutMargin(int viewId, int type, float value, int units) {
        addAction(new LayoutParamAction(viewId, type, value, units));
    }

    public void setViewLayoutWidth(int viewId, float width, int units) {
        addAction(new LayoutParamAction(viewId, 8, width, units));
    }

    public void setViewLayoutWidthDimen(int viewId, int widthDimen) {
        addAction(new LayoutParamAction(viewId, 8, widthDimen, 3));
    }

    public void setViewLayoutWidthAttr(int viewId, int widthAttr) {
        addAction(new LayoutParamAction(viewId, 8, widthAttr, 4));
    }

    public void semSetViewLayoutWidthAnimator(int viewId, int animatorId) {
        addAction(new LayoutParamAction(viewId, 8, animatorId));
    }

    public void semSetViewLayoutHeightAnimator(int viewId, int animatorId) {
        addAction(new LayoutParamAction(viewId, 9, animatorId));
    }

    public void setViewLayoutHeight(int viewId, float height, int units) {
        addAction(new LayoutParamAction(viewId, 9, height, units));
    }

    public void setViewLayoutHeightDimen(int viewId, int heightDimen) {
        addAction(new LayoutParamAction(viewId, 9, heightDimen, 3));
    }

    public void setViewLayoutHeightAttr(int viewId, int heightAttr) {
        addAction(new LayoutParamAction(viewId, 9, heightAttr, 4));
    }

    public void setViewOutlinePreferredRadius(int viewId, float radius, int units) {
        addAction(new SetViewOutlinePreferredRadiusAction(viewId, radius, units));
    }

    public void setViewOutlinePreferredRadiusDimen(int viewId, int resId) {
        addAction(new SetViewOutlinePreferredRadiusAction(viewId, resId, 3));
    }

    public void setViewOutlinePreferredRadiusAttr(int viewId, int attrId) {
        addAction(new SetViewOutlinePreferredRadiusAction(viewId, attrId, 4));
    }

    public void setBoolean(int viewId, String methodName, boolean value) {
        addAction(new ReflectionAction(viewId, methodName, 1, Boolean.valueOf(value)));
    }

    public void setByte(int viewId, String methodName, byte value) {
        addAction(new ReflectionAction(viewId, methodName, 2, Byte.valueOf(value)));
    }

    public void setShort(int viewId, String methodName, short value) {
        addAction(new ReflectionAction(viewId, methodName, 3, Short.valueOf(value)));
    }

    public void setInt(int viewId, String methodName, int value) {
        addAction(new ReflectionAction(viewId, methodName, 4, Integer.valueOf(value)));
    }

    public void setIntDimen(int viewId, String methodName, int dimenResource) {
        addAction(new ResourceReflectionAction(viewId, methodName, 4, 1, dimenResource));
    }

    public void setIntDimen(int viewId, String methodName, float value, int unit) {
        addAction(new ComplexUnitDimensionReflectionAction(viewId, methodName, 4, value, unit));
    }

    public void setIntDimenAttr(int viewId, String methodName, int dimenAttr) {
        addAction(new AttributeReflectionAction(viewId, methodName, 4, 1, dimenAttr));
    }

    public void setColor(int viewId, String methodName, int colorResource) {
        addAction(new ResourceReflectionAction(viewId, methodName, 4, 2, colorResource));
    }

    public void setColorAttr(int viewId, String methodName, int colorAttribute) {
        addAction(new AttributeReflectionAction(viewId, methodName, 4, 2, colorAttribute));
    }

    public void setColorInt(int viewId, String methodName, int notNight, int night) {
        addAction(new NightModeReflectionAction(viewId, methodName, 4, Integer.valueOf(notNight), Integer.valueOf(night)));
    }

    public void setColorStateList(int viewId, String methodName, ColorStateList value) {
        addAction(new ReflectionAction(viewId, methodName, 15, value));
    }

    public void setColorStateList(int viewId, String methodName, ColorStateList notNight, ColorStateList night) {
        addAction(new NightModeReflectionAction(viewId, methodName, 15, notNight, night));
    }

    public void setColorStateList(int viewId, String methodName, int colorResource) {
        addAction(new ResourceReflectionAction(viewId, methodName, 15, 2, colorResource));
    }

    public void setColorStateListAttr(int viewId, String methodName, int colorAttr) {
        addAction(new AttributeReflectionAction(viewId, methodName, 15, 2, colorAttr));
    }

    public void setLong(int viewId, String methodName, long value) {
        addAction(new ReflectionAction(viewId, methodName, 5, Long.valueOf(value)));
    }

    public void setFloat(int viewId, String methodName, float value) {
        addAction(new ReflectionAction(viewId, methodName, 6, Float.valueOf(value)));
    }

    public void setFloatDimen(int viewId, String methodName, int dimenResource) {
        addAction(new ResourceReflectionAction(viewId, methodName, 6, 1, dimenResource));
    }

    public void setFloatDimen(int viewId, String methodName, float value, int unit) {
        addAction(new ComplexUnitDimensionReflectionAction(viewId, methodName, 6, value, unit));
    }

    public void setFloatDimenAttr(int viewId, String methodName, int dimenAttr) {
        addAction(new AttributeReflectionAction(viewId, methodName, 6, 1, dimenAttr));
    }

    public void setDouble(int viewId, String methodName, double value) {
        addAction(new ReflectionAction(viewId, methodName, 7, Double.valueOf(value)));
    }

    public void setChar(int viewId, String methodName, char value) {
        addAction(new ReflectionAction(viewId, methodName, 8, Character.valueOf(value)));
    }

    public void setString(int viewId, String methodName, String value) {
        addAction(new ReflectionAction(viewId, methodName, 9, value));
    }

    public void setCharSequence(int viewId, String methodName, CharSequence value) {
        addAction(new ReflectionAction(viewId, methodName, 10, value));
    }

    public void setCharSequence(int viewId, String methodName, int stringResource) {
        addAction(new ResourceReflectionAction(viewId, methodName, 10, 3, stringResource));
    }

    public void setCharSequenceAttr(int viewId, String methodName, int stringAttribute) {
        addAction(new AttributeReflectionAction(viewId, methodName, 10, 3, stringAttribute));
    }

    public void setUri(int viewId, String methodName, Uri value) {
        if (value != null) {
            value = value.getCanonicalUri();
            if (StrictMode.vmFileUriExposureEnabled()) {
                value.checkFileUriExposed("RemoteViews.setUri()");
            }
        }
        addAction(new ReflectionAction(viewId, methodName, 11, value));
    }

    public void setBitmap(int viewId, String methodName, Bitmap value) {
        addAction(new BitmapReflectionAction(viewId, methodName, value));
    }

    public void setBlendMode(int viewId, String methodName, BlendMode value) {
        addAction(new ReflectionAction(viewId, methodName, 17, value));
    }

    public void setBundle(int viewId, String methodName, Bundle value) {
        addAction(new ReflectionAction(viewId, methodName, 13, value));
    }

    public void setIntent(int viewId, String methodName, Intent value) {
        addAction(new ReflectionAction(viewId, methodName, 14, value));
    }

    public void setIcon(int viewId, String methodName, Icon value) {
        addAction(new ReflectionAction(viewId, methodName, 16, value));
    }

    public void setIcon(int viewId, String methodName, Icon notNight, Icon night) {
        addAction(new NightModeReflectionAction(viewId, methodName, 16, notNight, night));
    }

    public void setContentDescription(int viewId, CharSequence contentDescription) {
        setCharSequence(viewId, "setContentDescription", contentDescription);
    }

    public void setAccessibilityTraversalBefore(int viewId, int nextId) {
        setInt(viewId, "setAccessibilityTraversalBefore", nextId);
    }

    public void setAccessibilityTraversalAfter(int viewId, int nextId) {
        setInt(viewId, "setAccessibilityTraversalAfter", nextId);
    }

    public void setLabelFor(int viewId, int labeledId) {
        setInt(viewId, "setLabelFor", labeledId);
    }

    public void setCompoundButtonChecked(int viewId, boolean checked) {
        addAction(new SetCompoundButtonCheckedAction(viewId, checked));
    }

    public void setRadioGroupChecked(int viewId, int checkedId) {
        addAction(new SetRadioGroupCheckedAction(viewId, checkedId));
    }

    public void setLightBackgroundLayoutId(int layoutId) {
        this.mLightBackgroundLayoutId = layoutId;
    }

    public RemoteViews getDarkTextViews() {
        if (hasFlags(4)) {
            return this;
        }
        try {
            addFlags(4);
            return new RemoteViews(this);
        } finally {
            this.mApplyFlags &= -5;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RemoteViews getRemoteViewsToApply(Context context) {
        if (this.isProductDEV) {
            Log.m94d(LOG_TAG, "getRemoteViewsToApply() - mIsForcedOrientation=" + this.mIsForcedOrientation + "mIsPortrait=" + this.mIsPortrait + ", mLandscape=" + this.mLandscape + ", mPortrait=" + this.mPortrait);
        }
        if (hasLandscapeAndPortraitLayouts()) {
            if (this.mIsForcedOrientation) {
                if (this.mIsPortrait) {
                    return this.mPortrait;
                }
                return this.mLandscape;
            }
            int orientation = context.getResources().getConfiguration().orientation;
            if (this.isProductDEV) {
                Log.m94d(LOG_TAG, "getRemoteViewsToApply apply remoteViews orientation = " + orientation);
            }
            if (orientation == 2) {
                return this.mLandscape;
            }
            return this.mPortrait;
        }
        if (hasSizedRemoteViews()) {
            return findSmallestRemoteView();
        }
        return this;
    }

    private static float squareDistance(SizeF p1, SizeF p2) {
        float dx = p1.getWidth() - p2.getWidth();
        float dy = p1.getHeight() - p2.getHeight();
        return (dx * dx) + (dy * dy);
    }

    private static boolean fitsIn(SizeF sizeLayout, SizeF sizeWidget) {
        return sizeWidget != null && Math.ceil((double) sizeWidget.getWidth()) + 1.0d > ((double) sizeLayout.getWidth()) && Math.ceil((double) sizeWidget.getHeight()) + 1.0d > ((double) sizeLayout.getHeight());
    }

    private RemoteViews findBestFitLayout(SizeF widgetSize) {
        RemoteViews bestFit = null;
        float bestSqDist = Float.MAX_VALUE;
        for (RemoteViews layout : this.mSizedRemoteViews) {
            SizeF layoutSize = layout.getIdealSize();
            if (layoutSize == null) {
                throw new IllegalStateException("Expected RemoteViews to have ideal size");
            }
            if (fitsIn(layoutSize, widgetSize)) {
                if (bestFit == null) {
                    bestFit = layout;
                    bestSqDist = squareDistance(layoutSize, widgetSize);
                } else {
                    float newSqDist = squareDistance(layoutSize, widgetSize);
                    if (newSqDist < bestSqDist) {
                        bestFit = layout;
                        bestSqDist = newSqDist;
                    }
                }
            }
        }
        if (bestFit == null) {
            Log.m102w(LOG_TAG, "Could not find a RemoteViews fitting the current size: " + widgetSize);
            return findSmallestRemoteView();
        }
        return bestFit;
    }

    public RemoteViews getRemoteViewsToApply(Context context, SizeF widgetSize) {
        if (!hasSizedRemoteViews() || widgetSize == null) {
            return getRemoteViewsToApply(context);
        }
        return findBestFitLayout(widgetSize);
    }

    public RemoteViews getRemoteViewsToApplyIfDifferent(SizeF oldSize, SizeF newSize) {
        if (!hasSizedRemoteViews()) {
            return null;
        }
        RemoteViews oldBestFit = oldSize == null ? findSmallestRemoteView() : findBestFitLayout(oldSize);
        RemoteViews newBestFit = findBestFitLayout(newSize);
        if (oldBestFit != newBestFit) {
            return newBestFit;
        }
        return null;
    }

    public View apply(Context context, ViewGroup parent) {
        return apply(context, parent, null);
    }

    public View apply(Context context, ViewGroup parent, InteractionHandler handler) {
        return apply(context, parent, handler, (SizeF) null);
    }

    public View apply(Context context, ViewGroup parent, InteractionHandler handler, SizeF size) {
        return apply(context, parent, size, new ActionApplyParams().withInteractionHandler(handler));
    }

    public View applyWithTheme(Context context, ViewGroup parent, InteractionHandler handler, int applyThemeResId) {
        return apply(context, parent, (SizeF) null, new ActionApplyParams().withInteractionHandler(handler).withThemeResId(applyThemeResId));
    }

    public View apply(Context context, ViewGroup parent, InteractionHandler handler, SizeF size, ColorResources colorResources) {
        return apply(context, parent, size, new ActionApplyParams().withInteractionHandler(handler).withColorResources(colorResources));
    }

    public View apply(Context context, ViewGroup parent, SizeF size, ActionApplyParams params) {
        return apply(context, parent, parent, size, params);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View apply(Context context, ViewGroup directParent, ViewGroup rootParent, SizeF size, ActionApplyParams params) {
        RemoteViews rvToApply = getRemoteViewsToApply(context, size);
        View result = inflateView(context, rvToApply, directParent, params.applyThemeResId, params.colorResources);
        rvToApply.performApply(result, rootParent, params);
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View inflateView(Context context, RemoteViews rv, ViewGroup parent, int applyThemeResId, ColorResources colorResources) {
        Context inflationContext;
        Context contextForResources = getContextForResourcesEnsuringCorrectCachedApkPaths(context);
        if (colorResources != null) {
            colorResources.apply(contextForResources);
        }
        Context inflationContext2 = new RemoteViewsContextWrapper(context, contextForResources);
        if (applyThemeResId == 0) {
            inflationContext = inflationContext2;
        } else {
            inflationContext = new ContextThemeWrapper(inflationContext2, applyThemeResId);
        }
        LayoutInflater inflater = LayoutInflater.from(context).cloneInContext(inflationContext);
        inflater.setFilter(shouldUseStaticFilter() ? INFLATER_FILTER : this);
        try {
            if (this.isProductDEV && (parent instanceof AppWidgetHostView)) {
                Log.m94d(LOG_TAG, "inflateView, package = " + inflationContext.getPackageName() + ", layout = " + ((Object) inflationContext.getResources().getText(rv.getLayoutId())) + ", App Config = " + inflationContext.getResources().getConfiguration());
            }
            View v = inflater.inflate(rv.getLayoutId(), parent, false);
            int i = this.mViewId;
            if (i != -1) {
                v.setId(i);
                v.setTagInternal(C4337R.id.remote_views_override_id, Integer.valueOf(this.mViewId));
            }
            v.setTagInternal(16908312, Integer.valueOf(rv.getLayoutId()));
            return v;
        } catch (RuntimeException e) {
            Log.m102w(LOG_TAG, "inflate error, layoutId = " + rv.getLayoutId());
            int i2 = 0;
            for (ApkAssets apkAssets : inflationContext.getAssets().getApkAssets()) {
                Log.m102w(LOG_TAG, NavigationBarInflaterView.SIZE_MOD_START + i2 + "], " + inflationContext.getPackageName() + " : " + apkAssets);
                i2++;
            }
            throw e;
        }
    }

    protected boolean shouldUseStaticFilter() {
        return getClass().equals(RemoteViews.class);
    }

    public interface OnViewAppliedListener {
        void onError(Exception exc);

        void onViewApplied(View view);

        default void onViewInflated(View v) {
        }
    }

    public CancellationSignal applyAsync(Context context, ViewGroup parent, Executor executor, OnViewAppliedListener listener) {
        return applyAsync(context, parent, executor, listener, null);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup parent, Executor executor, OnViewAppliedListener listener, InteractionHandler handler) {
        return applyAsync(context, parent, executor, listener, handler, null);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup parent, Executor executor, OnViewAppliedListener listener, InteractionHandler handler, SizeF size) {
        return applyAsync(context, parent, executor, listener, handler, size, null);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup parent, Executor executor, OnViewAppliedListener listener, InteractionHandler handler, SizeF size, ColorResources colorResources) {
        ActionApplyParams params = new ActionApplyParams().withInteractionHandler(handler).withColorResources(colorResources).withExecutor(executor);
        return new AsyncApplyTask(getRemoteViewsToApply(context, size), parent, context, listener, params, null, true).startTaskOnExecutor(executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AsyncApplyTask getInternalAsyncApplyTask(Context context, ViewGroup parent, OnViewAppliedListener listener, ActionApplyParams params, SizeF size, View result) {
        return new AsyncApplyTask(getRemoteViewsToApply(context, size), parent, context, listener, params, result, false);
    }

    private class AsyncApplyTask extends AsyncTask<Void, Void, ViewTree> implements CancellationSignal.OnCancelListener {
        private Action[] mActions;
        final ActionApplyParams mApplyParams;
        final CancellationSignal mCancelSignal;
        final Context mContext;
        private Exception mError;
        final OnViewAppliedListener mListener;
        final ViewGroup mParent;
        final RemoteViews mRV;
        private View mResult;
        final boolean mTopLevel;
        private ViewTree mTree;

        private AsyncApplyTask(RemoteViews rv, ViewGroup parent, Context context, OnViewAppliedListener listener, ActionApplyParams applyParams, View result, boolean topLevel) {
            this.mCancelSignal = new CancellationSignal();
            this.mRV = rv;
            this.mParent = parent;
            this.mContext = context;
            this.mListener = listener;
            this.mTopLevel = topLevel;
            this.mApplyParams = applyParams;
            this.mResult = result;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public ViewTree doInBackground(Void... params) {
            try {
                if (this.mResult == null) {
                    this.mResult = RemoteViews.this.inflateView(this.mContext, this.mRV, this.mParent, 0, this.mApplyParams.colorResources);
                }
                this.mTree = new ViewTree(this.mResult);
                if (this.mRV.mActions != null) {
                    int count = this.mRV.mActions.size();
                    this.mActions = new Action[count];
                    for (int i = 0; i < count && !isCancelled(); i++) {
                        this.mActions[i] = ((Action) this.mRV.mActions.get(i)).initActionAsync(this.mTree, this.mParent, this.mApplyParams);
                    }
                } else {
                    this.mActions = null;
                }
                return this.mTree;
            } catch (Exception e) {
                this.mError = e;
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(ViewTree viewTree) {
            this.mCancelSignal.setOnCancelListener(null);
            if (this.mError == null) {
                OnViewAppliedListener onViewAppliedListener = this.mListener;
                if (onViewAppliedListener != null) {
                    onViewAppliedListener.onViewInflated(viewTree.mRoot);
                }
                try {
                    if (this.mActions != null) {
                        ActionApplyParams applyParams = this.mApplyParams.m6955clone();
                        if (applyParams.handler == null) {
                            applyParams.handler = RemoteViews.DEFAULT_INTERACTION_HANDLER;
                        }
                        for (Action a : this.mActions) {
                            a.apply(viewTree.mRoot, this.mParent, applyParams);
                        }
                    }
                    if (this.mTopLevel) {
                        View view = this.mResult;
                        if (view instanceof ViewGroup) {
                            RemoteViews.this.finalizeViewRecycling((ViewGroup) view);
                        }
                    }
                } catch (Exception e) {
                    this.mError = e;
                }
            }
            OnViewAppliedListener onViewAppliedListener2 = this.mListener;
            if (onViewAppliedListener2 != null) {
                Exception exc = this.mError;
                if (exc != null) {
                    onViewAppliedListener2.onError(exc);
                    return;
                } else {
                    onViewAppliedListener2.onViewApplied(viewTree.mRoot);
                    return;
                }
            }
            Exception exc2 = this.mError;
            if (exc2 != null) {
                if (exc2 instanceof ActionException) {
                    throw ((ActionException) exc2);
                }
                throw new ActionException(this.mError);
            }
        }

        @Override // android.os.CancellationSignal.OnCancelListener
        public void onCancel() {
            cancel(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CancellationSignal startTaskOnExecutor(Executor executor) {
            this.mCancelSignal.setOnCancelListener(this);
            executeOnExecutor(executor == null ? AsyncTask.THREAD_POOL_EXECUTOR : executor, new Void[0]);
            return this.mCancelSignal;
        }
    }

    public void reapply(Context context, View v) {
        reapply(context, v, null, new ActionApplyParams());
    }

    public void reapply(Context context, View v, InteractionHandler handler) {
        reapply(context, v, null, new ActionApplyParams().withInteractionHandler(handler));
    }

    public void reapply(Context context, View v, InteractionHandler handler, SizeF size, ColorResources colorResources) {
        reapply(context, v, size, new ActionApplyParams().withInteractionHandler(handler).withColorResources(colorResources));
    }

    public void reapply(Context context, View v, SizeF size, ActionApplyParams params) {
        reapply(context, v, (ViewGroup) v.getParent(), size, params, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reapplyNestedViews(Context context, View v, ViewGroup rootParent, ActionApplyParams params) {
        reapply(context, v, rootParent, null, params, false);
    }

    private void reapply(Context context, View v, ViewGroup rootParent, SizeF size, ActionApplyParams params, boolean topLevel) {
        RemoteViews rvToApply = getRemoteViewsToReapply(context, v, size);
        rvToApply.performApply(v, rootParent, params);
        if (topLevel && (v instanceof ViewGroup)) {
            finalizeViewRecycling((ViewGroup) v);
        }
    }

    public boolean canRecycleView(View v) {
        Integer previousLayoutId;
        if (v == null || (previousLayoutId = (Integer) v.getTag(16908312)) == null) {
            return false;
        }
        Integer overrideIdTag = (Integer) v.getTag(C4337R.id.remote_views_override_id);
        int overrideId = overrideIdTag == null ? -1 : overrideIdTag.intValue();
        return previousLayoutId.intValue() == getLayoutId() && this.mViewId == overrideId;
    }

    private RemoteViews getRemoteViewsToReapply(Context context, View v, SizeF size) {
        RemoteViews rvToApply = getRemoteViewsToApply(context, size);
        if ((hasMultipleLayouts() || rvToApply.mViewId != -1 || v.getTag(C4337R.id.remote_views_override_id) != null) && !rvToApply.canRecycleView(v)) {
            throw new RuntimeException("Attempting to re-apply RemoteViews to a view that that does not share the same root layout id.");
        }
        return rvToApply;
    }

    public CancellationSignal reapplyAsync(Context context, View v, Executor executor, OnViewAppliedListener listener) {
        return reapplyAsync(context, v, executor, listener, null);
    }

    public CancellationSignal reapplyAsync(Context context, View v, Executor executor, OnViewAppliedListener listener, InteractionHandler handler) {
        return reapplyAsync(context, v, executor, listener, handler, null, null);
    }

    public CancellationSignal reapplyAsync(Context context, View v, Executor executor, OnViewAppliedListener listener, InteractionHandler handler, SizeF size, ColorResources colorResources) {
        RemoteViews rvToApply = getRemoteViewsToReapply(context, v, size);
        ActionApplyParams params = new ActionApplyParams().withColorResources(colorResources).withInteractionHandler(handler).withExecutor(executor);
        return new AsyncApplyTask(rvToApply, (ViewGroup) v.getParent(), context, listener, params, v, true).startTaskOnExecutor(executor);
    }

    private void performApply(View v, ViewGroup parent, ActionApplyParams params) {
        ActionApplyParams params2 = params.m6955clone();
        if (params2.handler == null) {
            params2.handler = DEFAULT_INTERACTION_HANDLER;
        }
        ArrayList<Action> arrayList = this.mActions;
        if (arrayList != null) {
            int count = arrayList.size();
            for (int i = 0; i < count; i++) {
                this.mActions.get(i).apply(v, parent, params2);
            }
        }
    }

    public boolean prefersAsyncApply() {
        ArrayList<Action> arrayList = this.mActions;
        if (arrayList != null) {
            int count = arrayList.size();
            for (int i = 0; i < count; i++) {
                if (this.mActions.get(i).prefersAsyncApply()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void updateAppInfo(ApplicationInfo info) {
        ApplicationInfo existing = this.mApplicationInfoCache.get(info);
        if (existing != null && !existing.sourceDir.equals(info.sourceDir)) {
            return;
        }
        this.mApplicationInfoCache.put(info);
        configureDescendantsAsChildren();
    }

    private Context getContextForResourcesEnsuringCorrectCachedApkPaths(Context context) {
        if (this.mApplication != null) {
            if (context.getUserId() == UserHandle.getUserId(this.mApplication.uid) && context.getPackageName().equals(this.mApplication.packageName)) {
                return context;
            }
            try {
                LoadedApk.checkAndUpdateApkPaths(this.mApplication);
                return context.createApplicationContext(this.mApplication, 4);
            } catch (PackageManager.NameNotFoundException e) {
                Log.m96e(LOG_TAG, "Package name " + this.mApplication.packageName + " not found");
            }
        }
        return context;
    }

    public class ActionApplyParams {
        public int applyThemeResId;
        public ColorResources colorResources;
        public Executor executor;
        public InteractionHandler handler;

        public ActionApplyParams() {
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public ActionApplyParams m6955clone() {
            return RemoteViews.this.new ActionApplyParams().withInteractionHandler(this.handler).withColorResources(this.colorResources).withExecutor(this.executor).withThemeResId(this.applyThemeResId);
        }

        public ActionApplyParams withInteractionHandler(InteractionHandler handler) {
            this.handler = handler;
            return this;
        }

        public ActionApplyParams withColorResources(ColorResources colorResources) {
            this.colorResources = colorResources;
            return this;
        }

        public ActionApplyParams withThemeResId(int themeResId) {
            this.applyThemeResId = themeResId;
            return this;
        }

        public ActionApplyParams withExecutor(Executor executor) {
            this.executor = executor;
            return this;
        }
    }

    public static final class ColorResources {
        private static final int ARSC_ENTRY_SIZE = 16;
        private static final int FIRST_RESOURCE_COLOR_ID = 17170461;
        private static final int LAST_RESOURCE_COLOR_ID = 17170525;
        private final SparseIntArray mColorMapping;
        private final ResourcesLoader mLoader;

        private ColorResources(ResourcesLoader loader, SparseIntArray colorMapping) {
            this.mLoader = loader;
            this.mColorMapping = colorMapping;
        }

        public void apply(Context context) {
            context.getResources().addLoaders(this.mLoader);
        }

        public SparseIntArray getColorMapping() {
            return this.mColorMapping;
        }

        private static ByteArrayOutputStream readFileContent(InputStream input) throws IOException {
            ByteArrayOutputStream content = new ByteArrayOutputStream(2048);
            byte[] buffer = new byte[4096];
            while (input.available() > 0) {
                int read = input.read(buffer);
                content.write(buffer, 0, read);
            }
            return content;
        }

        private static byte[] createCompiledResourcesContent(Context context, SparseIntArray colorResources) throws IOException {
            InputStream input = context.getResources().openRawResource(C4337R.raw.remote_views_color_resources);
            try {
                ByteArrayOutputStream rawContent = readFileContent(input);
                byte[] content = rawContent.toByteArray();
                if (input != null) {
                    input.close();
                }
                int valuesOffset = (content.length - 1488) - 4;
                if (valuesOffset < 0) {
                    Log.m96e(RemoteViews.LOG_TAG, "ARSC file for theme colors is invalid.");
                    return null;
                }
                for (int colorRes = 17170461; colorRes <= 17170525; colorRes++) {
                    int index = 65535 & colorRes;
                    int offset = (index * 16) + valuesOffset;
                    int value = colorResources.get(colorRes, context.getColor(colorRes));
                    for (int b = 0; b < 4; b++) {
                        content[offset + b] = (byte) (value & 255);
                        value >>= 8;
                    }
                }
                return content;
            } catch (Throwable th) {
                if (input != null) {
                    try {
                        input.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public static ColorResources create(Context context, SparseIntArray colorMapping) {
            try {
                byte[] contentBytes = createCompiledResourcesContent(context, colorMapping);
                if (contentBytes == null) {
                    return null;
                }
                FileDescriptor arscFile = null;
                try {
                    arscFile = Os.memfd_create("remote_views_theme_colors.arsc", 0);
                    OutputStream pipeWriter = new FileOutputStream(arscFile);
                    try {
                        pipeWriter.write(contentBytes);
                        ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(arscFile);
                        try {
                            ResourcesLoader colorsLoader = new ResourcesLoader();
                            colorsLoader.addProvider(ResourcesProvider.loadFromTable(pfd, null));
                            ColorResources colorResources = new ColorResources(colorsLoader, colorMapping.m5443clone());
                            if (pfd != null) {
                                pfd.close();
                            }
                            pipeWriter.close();
                            return colorResources;
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                    if (arscFile != null) {
                        Os.close(arscFile);
                    }
                }
            } catch (Exception ex) {
                Log.m97e(RemoteViews.LOG_TAG, "Failed to setup the context for theme colors", ex);
                return null;
            }
        }
    }

    public int getSequenceNumber() {
        ArrayList<Action> arrayList = this.mActions;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override // android.view.LayoutInflater.Filter
    @Deprecated
    public boolean onLoadClass(Class clazz) {
        return clazz.isAnnotationPresent(RemoteView.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        boolean prevSquashingAllowed = dest.allowSquashing();
        if (!hasMultipleLayouts()) {
            dest.writeInt(0);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(dest, flags);
            }
            this.mApplication.writeToParcel(dest, flags);
            if (this.mIsRoot || this.mIdealSize == null) {
                dest.writeInt(0);
            } else {
                dest.writeInt(1);
                this.mIdealSize.writeToParcel(dest, flags);
            }
            dest.writeInt(this.mLayoutId);
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mLightBackgroundLayoutId);
            writeActionsToParcel(dest, flags);
        } else if (hasSizedRemoteViews()) {
            dest.writeInt(2);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(dest, flags);
            }
            dest.writeInt(this.mSizedRemoteViews.size());
            for (RemoteViews view : this.mSizedRemoteViews) {
                view.writeToParcel(dest, flags);
            }
        } else {
            dest.writeInt(1);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(dest, flags);
            }
            this.mLandscape.writeToParcel(dest, flags);
            this.mPortrait.writeToParcel(dest, flags);
        }
        dest.writeInt(this.mApplyFlags);
        dest.writeLong(this.mProviderInstanceId);
        dest.writeBoolean(this.mAllowOtherRootParent);
        dest.writeInt(this.mAppWidgetId);
        dest.restoreAllowSquashing(prevSquashingAllowed);
        dest.writeBoolean(this.mIsAllowPendintIntentInCollection);
    }

    private void writeActionsToParcel(Parcel parcel, int flags) {
        int count;
        synchronized (this.mActionsLock) {
            ArrayList<Action> arrayList = this.mActions;
            if (arrayList != null) {
                count = arrayList.size();
            } else {
                count = 0;
            }
            parcel.writeInt(count);
            for (int i = 0; i < count; i++) {
                Action a = this.mActions.get(i);
                parcel.writeInt(a.getActionTag());
                a.writeToParcel(parcel, flags);
            }
        }
    }

    private static ApplicationInfo getApplicationInfo(String packageName, int userId) {
        if (packageName == null) {
            return null;
        }
        Application application = ActivityThread.currentApplication();
        if (application == null) {
            throw new IllegalStateException("Cannot create remote views out of an aplication.");
        }
        ApplicationInfo applicationInfo = application.getApplicationInfo();
        if (UserHandle.getUserId(applicationInfo.uid) != userId || !applicationInfo.packageName.equals(packageName)) {
            try {
                Context context = application.getBaseContext().createPackageContextAsUser(packageName, 0, new UserHandle(userId));
                return context.getApplicationInfo();
            } catch (PackageManager.NameNotFoundException e) {
                throw new IllegalArgumentException("No such package " + packageName);
            }
        }
        return applicationInfo;
    }

    public boolean hasSameAppInfo(ApplicationInfo info) {
        return this.mApplication.packageName.equals(info.packageName) && this.mApplication.uid == info.uid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewTree {
        private static final int INSERT_AT_END_INDEX = -1;
        private ArrayList<ViewTree> mChildren;
        private View mRoot;

        private ViewTree(View root) {
            this.mRoot = root;
        }

        public void createTree() {
            if (this.mChildren != null) {
                return;
            }
            this.mChildren = new ArrayList<>();
            View view = this.mRoot;
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int count = vg.getChildCount();
                for (int i = 0; i < count; i++) {
                    addViewChild(vg.getChildAt(i));
                }
            }
        }

        public ViewTree findViewTreeById(int id) {
            if (this.mRoot.getId() == id) {
                return this;
            }
            ArrayList<ViewTree> arrayList = this.mChildren;
            if (arrayList == null) {
                return null;
            }
            Iterator<ViewTree> it = arrayList.iterator();
            while (it.hasNext()) {
                ViewTree tree = it.next();
                ViewTree result = tree.findViewTreeById(id);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }

        public ViewTree findViewTreeParentOf(ViewTree child) {
            ArrayList<ViewTree> arrayList = this.mChildren;
            if (arrayList == null) {
                return null;
            }
            Iterator<ViewTree> it = arrayList.iterator();
            while (it.hasNext()) {
                ViewTree tree = it.next();
                if (tree == child) {
                    return this;
                }
                ViewTree result = tree.findViewTreeParentOf(child);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }

        public void replaceView(View v) {
            this.mRoot = v;
            this.mChildren = null;
            createTree();
        }

        public <T extends View> T findViewById(int i) {
            if (this.mChildren == null) {
                return (T) this.mRoot.findViewById(i);
            }
            ViewTree findViewTreeById = findViewTreeById(i);
            if (findViewTreeById == null) {
                return null;
            }
            return (T) findViewTreeById.mRoot;
        }

        public void addChild(ViewTree child) {
            addChild(child, -1);
        }

        public void addChild(ViewTree child, int index) {
            if (this.mChildren == null) {
                this.mChildren = new ArrayList<>();
            }
            child.createTree();
            if (index == -1) {
                this.mChildren.add(child);
            } else {
                this.mChildren.add(index, child);
            }
        }

        public void removeChildren(int start, int count) {
            if (this.mChildren != null) {
                for (int i = 0; i < count; i++) {
                    this.mChildren.remove(start);
                }
            }
        }

        private void addViewChild(View v) {
            ViewTree tree;
            if (v.isRootNamespace()) {
                return;
            }
            if (v.getId() != 0) {
                tree = new ViewTree(v);
                this.mChildren.add(tree);
            } else {
                tree = this;
            }
            if ((v instanceof ViewGroup) && tree.mChildren == null) {
                tree.mChildren = new ArrayList<>();
                ViewGroup vg = (ViewGroup) v;
                int count = vg.getChildCount();
                for (int i = 0; i < count; i++) {
                    tree.addViewChild(vg.getChildAt(i));
                }
            }
        }

        public int findChildIndex(Predicate<View> condition) {
            return findChildIndex(0, condition);
        }

        public int findChildIndex(int startIndex, Predicate<View> condition) {
            if (this.mChildren == null) {
                return -1;
            }
            for (int i = startIndex; i < this.mChildren.size(); i++) {
                if (condition.test(this.mChildren.get(i).mRoot)) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static class RemoteResponse {
        public static final int INTERACTION_TYPE_CHECKED_CHANGE = 1;
        public static final int INTERACTION_TYPE_CLICK = 0;
        private ArrayList<String> mElementNames;
        private Intent mFillIntent;
        private int mInteractionType = 0;
        private PendingIntent mPendingIntent;
        private IntArray mViewIds;

        @Retention(RetentionPolicy.SOURCE)
        @interface InteractionType {
        }

        public static RemoteResponse fromPendingIntent(PendingIntent pendingIntent) {
            RemoteResponse response = new RemoteResponse();
            response.mPendingIntent = pendingIntent;
            return response;
        }

        public static RemoteResponse fromFillInIntent(Intent fillIntent) {
            RemoteResponse response = new RemoteResponse();
            response.mFillIntent = fillIntent;
            return response;
        }

        public RemoteResponse addSharedElement(int viewId, String sharedElementName) {
            if (this.mViewIds == null) {
                this.mViewIds = new IntArray();
                this.mElementNames = new ArrayList<>();
            }
            this.mViewIds.add(viewId);
            this.mElementNames.add(sharedElementName);
            return this;
        }

        public RemoteResponse setInteractionType(int type) {
            this.mInteractionType = type;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel dest, int flags) {
            PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, dest);
            if (this.mPendingIntent == null) {
                dest.writeTypedObject(this.mFillIntent, flags);
            }
            dest.writeInt(this.mInteractionType);
            IntArray intArray = this.mViewIds;
            dest.writeIntArray(intArray == null ? null : intArray.toArray());
            dest.writeStringList(this.mElementNames);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readFromParcel(Parcel parcel) {
            PendingIntent readPendingIntentOrNullFromParcel = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            this.mPendingIntent = readPendingIntentOrNullFromParcel;
            if (readPendingIntentOrNullFromParcel == null) {
                this.mFillIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            }
            this.mInteractionType = parcel.readInt();
            int[] viewIds = parcel.createIntArray();
            this.mViewIds = viewIds == null ? null : IntArray.wrap(viewIds);
            this.mElementNames = parcel.createStringArrayList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleViewInteraction(View v, InteractionHandler handler) {
            PendingIntent pi;
            if (this.mPendingIntent != null) {
                pi = this.mPendingIntent;
            } else if (this.mFillIntent != null) {
                AdapterView<?> ancestor = getAdapterViewAncestor(v);
                if (ancestor == null) {
                    Log.m96e(RemoteViews.LOG_TAG, "Collection item doesn't have AdapterView parent");
                    return;
                } else {
                    if (!(ancestor.getTag() instanceof PendingIntent)) {
                        Log.m96e(RemoteViews.LOG_TAG, "Attempting setOnClickFillInIntent or setOnCheckedChangeFillInIntent without calling setPendingIntentTemplate on parent.");
                        return;
                    }
                    pi = (PendingIntent) ancestor.getTag();
                }
            } else {
                Log.m96e(RemoteViews.LOG_TAG, "Response has neither pendingIntent nor fillInIntent");
                return;
            }
            handler.onInteraction(v, pi, this);
        }

        private static AdapterView<?> getAdapterViewAncestor(View view) {
            if (view == null) {
                return null;
            }
            View parent = (View) view.getParent();
            while (parent != null && !(parent instanceof AdapterView) && (!(parent instanceof AppWidgetHostView) || (parent instanceof RemoteViewsAdapter.RemoteViewsFrameLayout))) {
                parent = (View) parent.getParent();
            }
            if (parent instanceof AdapterView) {
                return (AdapterView) parent;
            }
            return null;
        }

        public Pair<Intent, ActivityOptions> getLaunchOptions(View view) {
            Intent intent = this.mPendingIntent != null ? new Intent() : new Intent(this.mFillIntent);
            intent.setSourceBounds(RemoteViews.getSourceBounds(view));
            if ((view instanceof CompoundButton) && this.mInteractionType == 1) {
                intent.putExtra(RemoteViews.EXTRA_CHECKED, ((CompoundButton) view).isChecked());
            }
            ActivityOptions opts = null;
            Context context = view.getContext();
            if (context.getResources().getBoolean(C4337R.bool.config_overrideRemoteViewsActivityTransition)) {
                TypedArray windowStyle = context.getTheme().obtainStyledAttributes(C4337R.styleable.Window);
                int windowAnimations = windowStyle.getResourceId(8, 0);
                TypedArray windowAnimationStyle = context.obtainStyledAttributes(windowAnimations, C4337R.styleable.WindowAnimation);
                int enterAnimationId = windowAnimationStyle.getResourceId(26, 0);
                windowStyle.recycle();
                windowAnimationStyle.recycle();
                if (enterAnimationId != 0) {
                    opts = ActivityOptions.makeCustomAnimation(context, enterAnimationId, 0);
                    opts.setPendingIntentLaunchFlags(268435456);
                }
            }
            if (opts == null && this.mViewIds != null && this.mElementNames != null) {
                View parent = (View) view.getParent();
                while (parent != null && !(parent instanceof AppWidgetHostView)) {
                    parent = (View) parent.getParent();
                }
                if (parent instanceof AppWidgetHostView) {
                    int[] array = this.mViewIds.toArray();
                    ArrayList<String> arrayList = this.mElementNames;
                    opts = ((AppWidgetHostView) parent).createSharedElementActivityOptions(array, (String[]) arrayList.toArray(new String[arrayList.size()]), intent);
                }
            }
            if (opts == null) {
                opts = ActivityOptions.makeBasic();
                opts.setPendingIntentLaunchFlags(268435456);
            }
            if (view.getDisplay() != null) {
                opts.setLaunchDisplayId(view.getDisplay().getDisplayId());
            } else {
                Log.m103w(RemoteViews.LOG_TAG, "getLaunchOptions: view.getDisplay() is null!", new Exception());
            }
            opts.setPendingIntentBackgroundActivityStartMode(1);
            return Pair.create(intent, opts);
        }
    }

    public static boolean startPendingIntent(View view, PendingIntent pendingIntent, Pair<Intent, ActivityOptions> options) {
        KeyguardManager keyguardManager;
        try {
            Context context = view.getContext();
            if (ViewRune.APPWIDGET_COMPLICATION && pendingIntent.isActivity()) {
                boolean isComplication = false;
                Object parent = view.getParent();
                if (parent instanceof View) {
                    View parent2 = (View) parent;
                    int i = 0;
                    while (true) {
                        if (parent2 == null || i >= 5) {
                            break;
                        }
                        if (parent2 instanceof AppWidgetHostView) {
                            AppWidgetProviderInfo info = ((AppWidgetHostView) parent2).getAppWidgetInfo();
                            if (info != null && info.widgetCategory == 8192) {
                                isComplication = true;
                            }
                        } else {
                            i++;
                            Object parent3 = parent2.getParent();
                            if (!(parent3 instanceof View)) {
                                break;
                            }
                            parent2 = (View) parent3;
                        }
                    }
                }
                if (isComplication && (keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)) != null && keyguardManager.isKeyguardLocked()) {
                    Intent fillInIntent = new Intent();
                    fillInIntent.putExtra("runOnCover", true);
                    fillInIntent.putExtra("ignoreKeyguardState", true);
                    keyguardManager.semSetPendingIntentAfterUnlock(pendingIntent, fillInIntent);
                    return true;
                }
            }
            context.startIntentSender(pendingIntent.getIntentSender(), options.first, 0, 0, 0, options.second.toBundle());
            return true;
        } catch (IntentSender.SendIntentException e) {
            Log.m97e(LOG_TAG, "Cannot send pending intent: ", e);
            return false;
        } catch (Exception e2) {
            Log.m97e(LOG_TAG, "Cannot send pending intent due to unknown exception: ", e2);
            return false;
        }
    }

    public static final class RemoteCollectionItems implements Parcelable {
        public static final Parcelable.Creator<RemoteCollectionItems> CREATOR = new Parcelable.Creator<RemoteCollectionItems>() { // from class: android.widget.RemoteViews.RemoteCollectionItems.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RemoteCollectionItems createFromParcel(Parcel source) {
                return new RemoteCollectionItems(source, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RemoteCollectionItems[] newArray(int size) {
                return new RemoteCollectionItems[size];
            }
        };
        private final boolean mHasStableIds;
        private HierarchyRootData mHierarchyRootData;
        private final long[] mIds;
        private final int mViewTypeCount;
        private final RemoteViews[] mViews;

        RemoteCollectionItems(long[] ids, RemoteViews[] views, boolean hasStableIds, int viewTypeCount) {
            this.mIds = ids;
            this.mViews = views;
            this.mHasStableIds = hasStableIds;
            this.mViewTypeCount = viewTypeCount;
            if (ids.length != views.length) {
                throw new IllegalArgumentException("RemoteCollectionItems has different number of ids and views");
            }
            if (viewTypeCount < 1) {
                throw new IllegalArgumentException("View type count must be >= 1");
            }
            int layoutIdCount = (int) Arrays.stream(views).mapToInt(new RemoteViews$RemoteCollectionItems$$ExternalSyntheticLambda0()).distinct().count();
            if (layoutIdCount > viewTypeCount) {
                throw new IllegalArgumentException("View type count is set to " + viewTypeCount + ", but the collection contains " + layoutIdCount + " different layout ids");
            }
            if (views.length > 0) {
                setHierarchyRootData(views[0].getHierarchyRootData());
                views[0].mIsRoot = true;
            }
        }

        RemoteCollectionItems(Parcel in, HierarchyRootData hierarchyRootData) {
            int firstChildIndex;
            this.mHasStableIds = in.readBoolean();
            this.mViewTypeCount = in.readInt();
            int length = in.readInt();
            long[] jArr = new long[length];
            this.mIds = jArr;
            in.readLongArray(jArr);
            boolean attached = in.readBoolean();
            RemoteViews[] remoteViewsArr = new RemoteViews[length];
            this.mViews = remoteViewsArr;
            if (attached) {
                if (hierarchyRootData == null) {
                    throw new IllegalStateException("Cannot unparcel a RemoteCollectionItems that was parceled as attached without providing data for a root RemoteViews");
                }
                this.mHierarchyRootData = hierarchyRootData;
                firstChildIndex = 0;
            } else {
                RemoteViews remoteViews = new RemoteViews(in);
                remoteViewsArr[0] = remoteViews;
                this.mHierarchyRootData = remoteViews.getHierarchyRootData();
                firstChildIndex = 1;
            }
            for (int i = firstChildIndex; i < length; i++) {
                this.mViews[i] = new RemoteViews(in, this.mHierarchyRootData, null, 0);
            }
        }

        void setHierarchyRootData(HierarchyRootData rootData) {
            this.mHierarchyRootData = rootData;
            for (RemoteViews view : this.mViews) {
                view.configureAsChild(rootData);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            writeToParcel(dest, flags, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel dest, int flags, boolean attached) {
            boolean prevAllowSquashing = dest.allowSquashing();
            dest.writeBoolean(this.mHasStableIds);
            dest.writeInt(this.mViewTypeCount);
            dest.writeInt(this.mIds.length);
            dest.writeLongArray(this.mIds);
            if (attached && this.mHierarchyRootData == null) {
                throw new IllegalStateException("Cannot call writeToParcelAttached for a RemoteCollectionItems without first calling setHierarchyRootData()");
            }
            dest.writeBoolean(attached);
            boolean restoreRoot = false;
            if (!attached) {
                RemoteViews[] remoteViewsArr = this.mViews;
                if (remoteViewsArr.length > 0 && !remoteViewsArr[0].mIsRoot) {
                    restoreRoot = true;
                    this.mViews[0].mIsRoot = true;
                }
            }
            for (RemoteViews view : this.mViews) {
                view.writeToParcel(dest, flags);
            }
            if (restoreRoot) {
                this.mViews[0].mIsRoot = false;
            }
            dest.restoreAllowSquashing(prevAllowSquashing);
        }

        public long getItemId(int position) {
            return this.mIds[position];
        }

        public RemoteViews getItemView(int position) {
            return this.mViews[position];
        }

        public int getItemCount() {
            return this.mIds.length;
        }

        public int getViewTypeCount() {
            return this.mViewTypeCount;
        }

        public boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public static final class Builder {
            private boolean mHasStableIds;
            private int mViewTypeCount;
            private final LongArray mIds = new LongArray();
            private final List<RemoteViews> mViews = new ArrayList();

            public Builder addItem(long id, RemoteViews view) {
                if (view == null) {
                    throw new NullPointerException();
                }
                if (view.hasMultipleLayouts()) {
                    throw new IllegalArgumentException("RemoteViews used in a RemoteCollectionItems cannot specify separate layouts for orientations or sizes.");
                }
                this.mIds.add(id);
                this.mViews.add(view);
                return this;
            }

            public Builder setHasStableIds(boolean hasStableIds) {
                this.mHasStableIds = hasStableIds;
                return this;
            }

            public Builder setViewTypeCount(int viewTypeCount) {
                this.mViewTypeCount = viewTypeCount;
                return this;
            }

            public RemoteCollectionItems build() {
                if (this.mViewTypeCount < 1) {
                    this.mViewTypeCount = (int) this.mViews.stream().mapToInt(new RemoteViews$RemoteCollectionItems$$ExternalSyntheticLambda0()).distinct().count();
                }
                return new RemoteCollectionItems(this.mIds.toArray(), (RemoteViews[]) this.mViews.toArray(new RemoteViews[0]), this.mHasStableIds, Math.max(this.mViewTypeCount, 1));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(Consumer<Uri> visitor) {
            for (RemoteViews view : this.mViews) {
                view.visitUris(visitor);
            }
        }
    }

    public int getViewId() {
        return this.mViewId;
    }

    public void setProviderInstanceId(long id) {
        this.mProviderInstanceId = id;
    }

    public long getProviderInstanceId() {
        return this.mProviderInstanceId;
    }

    private int getChildId(RemoteViews child) {
        if (child == this) {
            return 0;
        }
        if (hasSizedRemoteViews()) {
            for (int i = 0; i < this.mSizedRemoteViews.size(); i++) {
                if (this.mSizedRemoteViews.get(i) == child) {
                    return i + 1;
                }
            }
        }
        if (hasLandscapeAndPortraitLayouts()) {
            if (this.mLandscape == child) {
                return 1;
            }
            if (this.mPortrait == child) {
                return 2;
            }
        }
        return 0;
    }

    public long computeUniqueId(RemoteViews parent) {
        int childId;
        if (this.mIsRoot) {
            long viewId = getProviderInstanceId();
            if (viewId != -1) {
                return viewId << 8;
            }
            return viewId;
        }
        if (parent == null) {
            return -1L;
        }
        long viewId2 = parent.getProviderInstanceId();
        if (viewId2 == -1 || (childId = parent.getChildId(this)) == -1) {
            return -1L;
        }
        return (viewId2 << 8) | childId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Pair<String, Integer> getPackageUserKey(ApplicationInfo info) {
        if (info == null || info.packageName == null) {
            return null;
        }
        return Pair.create(info.packageName, Integer.valueOf(info.uid));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HierarchyRootData getHierarchyRootData() {
        return new HierarchyRootData(this.mBitmapCache, this.mApplicationInfoCache, this.mClassCookies);
    }

    private static final class HierarchyRootData {
        final ApplicationInfoCache mApplicationInfoCache;
        final BitmapCache mBitmapCache;
        final Map<Class, Object> mClassCookies;

        HierarchyRootData(BitmapCache bitmapCache, ApplicationInfoCache applicationInfoCache, Map<Class, Object> classCookies) {
            this.mBitmapCache = bitmapCache;
            this.mApplicationInfoCache = applicationInfoCache;
            this.mClassCookies = classCookies;
        }
    }

    private class ClearAllTextEffectAction extends Action {
        public static final int TAG = 41;
        final String methodName;

        public ClearAllTextEffectAction(int viewId) {
            super();
            this.methodName = "clearAllTextEffect";
            this.viewId = viewId;
        }

        public ClearAllTextEffectAction(Parcel parcel) {
            super();
            this.methodName = "clearAllTextEffect";
            this.viewId = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.semClearAllTextEffect();
        }

        public String getActionName() {
            return "ClearAllTextEffectAction";
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 41;
        }
    }

    private class AddOuterShadowAction extends Action {
        public static final int TAG = 42;
        float angle;
        float blendingOpacity;
        int color;
        final String methodName;
        float offset;
        float softness;

        public AddOuterShadowAction(int viewId, float angle, float offset, float softness, int color, float blendingOpacity) {
            super();
            this.methodName = "addOuterShadowTextEffect";
            this.viewId = viewId;
            this.angle = angle;
            this.offset = offset;
            this.softness = softness;
            this.color = color;
            this.blendingOpacity = blendingOpacity;
        }

        public AddOuterShadowAction(Parcel parcel) {
            super();
            this.methodName = "addOuterShadowTextEffect";
            this.viewId = parcel.readInt();
            this.angle = parcel.readFloat();
            this.offset = parcel.readFloat();
            this.softness = parcel.readFloat();
            this.color = parcel.readInt();
            this.blendingOpacity = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeFloat(this.angle);
            dest.writeFloat(this.offset);
            dest.writeFloat(this.softness);
            dest.writeInt(this.color);
            dest.writeFloat(this.blendingOpacity);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.semAddOuterShadowTextEffect(this.angle, this.offset, this.softness, this.color, this.blendingOpacity);
            target.getPaint().setFilterBitmap(true);
        }

        public String getActionName() {
            return "AddOuterShadowAction";
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 42;
        }
    }

    private class AddInnerShadowAction extends Action {
        public static final int TAG = 43;
        float angle;
        float blendingOpacity;
        int color;
        final String methodName;
        float offset;
        float softness;

        public AddInnerShadowAction(int viewId, float angle, float offset, float softness, int color, float blendingOpacity) {
            super();
            this.methodName = "addInnerShadowTextEffect";
            this.viewId = viewId;
            this.angle = angle;
            this.offset = offset;
            this.softness = softness;
            this.color = color;
            this.blendingOpacity = blendingOpacity;
        }

        public AddInnerShadowAction(Parcel parcel) {
            super();
            this.methodName = "addInnerShadowTextEffect";
            this.viewId = parcel.readInt();
            this.angle = parcel.readFloat();
            this.offset = parcel.readFloat();
            this.softness = parcel.readFloat();
            this.color = parcel.readInt();
            this.blendingOpacity = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeFloat(this.angle);
            dest.writeFloat(this.offset);
            dest.writeFloat(this.softness);
            dest.writeInt(this.color);
            dest.writeFloat(this.blendingOpacity);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.semAddInnerShadowTextEffect(this.angle, this.offset, this.softness, this.color, this.blendingOpacity);
            target.getPaint().setFilterBitmap(true);
        }

        public String getActionName() {
            return "AddInnerShadowAction";
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 43;
        }
    }

    private class AddStrokeAction extends Action {
        public static final int TAG = 44;
        float blendingOpacity;
        int color;
        final String methodName;
        float size;

        public AddStrokeAction(int viewId, float size, int color, float blendingOpacity) {
            super();
            this.methodName = "addStrokeTextEffect";
            this.viewId = viewId;
            this.size = size;
            this.color = color;
            this.blendingOpacity = blendingOpacity;
        }

        public AddStrokeAction(Parcel parcel) {
            super();
            this.methodName = "addStrokeTextEffect";
            this.viewId = parcel.readInt();
            this.size = parcel.readFloat();
            this.color = parcel.readInt();
            this.blendingOpacity = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeFloat(this.size);
            dest.writeInt(this.color);
            dest.writeFloat(this.blendingOpacity);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.semAddStrokeTextEffect(this.size, this.color, this.blendingOpacity);
            target.getPaint().setFilterBitmap(true);
        }

        public String getActionName() {
            return "AddStrokeAction";
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 44;
        }
    }

    private class AddLinearGradientAction extends Action {
        public static final int TAG = 45;
        float[] alphas;
        float angle;
        float blendingOpacity;
        int[] colors;
        final String methodName;
        float[] positions;
        float scale;

        public AddLinearGradientAction(int viewId, float angle, float scale, int[] colors, float[] alphas, float[] positions, float blendingOpacity) {
            super();
            this.methodName = "addLinearGradientTextEffect";
            this.viewId = viewId;
            this.angle = angle;
            this.scale = scale;
            this.colors = colors;
            this.alphas = alphas;
            this.positions = positions;
            this.blendingOpacity = blendingOpacity;
        }

        public AddLinearGradientAction(Parcel parcel) {
            super();
            this.methodName = "addLinearGradientTextEffect";
            this.viewId = parcel.readInt();
            this.angle = parcel.readFloat();
            this.scale = parcel.readFloat();
            this.colors = parcel.createIntArray();
            this.alphas = parcel.createFloatArray();
            this.positions = parcel.createFloatArray();
            this.blendingOpacity = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeFloat(this.angle);
            dest.writeFloat(this.scale);
            dest.writeIntArray(this.colors);
            dest.writeFloatArray(this.alphas);
            dest.writeFloatArray(this.positions);
            dest.writeFloat(this.blendingOpacity);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.semAddLinearGradientTextEffect(this.angle, this.scale, this.colors, this.alphas, this.positions, this.blendingOpacity);
        }

        public String getActionName() {
            return "AddLinearGradientAction";
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 45;
        }
    }

    private class AddOuterGlowAction extends Action {
        public static final int TAG = 46;
        float blendingOpacity;
        int color;
        final String methodName;
        float size;

        public AddOuterGlowAction(int viewId, float size, int color, float blendingOpacity) {
            super();
            this.methodName = "addOuterGlowTextEffect";
            this.viewId = viewId;
            this.size = size;
            this.color = color;
            this.blendingOpacity = blendingOpacity;
        }

        public AddOuterGlowAction(Parcel parcel) {
            super();
            this.methodName = "addOuterGlowTextEffect";
            this.viewId = parcel.readInt();
            this.size = parcel.readFloat();
            this.color = parcel.readInt();
            this.blendingOpacity = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeFloat(this.size);
            dest.writeInt(this.color);
            dest.writeFloat(this.blendingOpacity);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            target.semAddOuterGlowTextEffect(this.size, this.color, this.blendingOpacity);
        }

        public String getActionName() {
            return "AddOuterGlowAction";
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 46;
        }
    }

    public void semClearAllTextEffect(int viewId) {
        addAction(new ClearAllTextEffectAction(viewId));
    }

    public void semAddOuterShadowTextEffect(int viewId, float angle, float offset, float softness, int color, float blendingOpacity) {
        addAction(new AddOuterShadowAction(viewId, angle, offset, softness, color, blendingOpacity));
    }

    public void semAddInnerShadowTextEffect(int viewId, float angle, float offset, float softness, int color, float blendingOpacity) {
        addAction(new AddInnerShadowAction(viewId, angle, offset, softness, color, blendingOpacity));
    }

    public void semAddStrokeTextEffect(int viewId, float size, int color, float blendingOpacity) {
        addAction(new AddStrokeAction(viewId, size, color, blendingOpacity));
    }

    public void semAddOuterGlowTextEffect(int viewId, float size, int color, float blendingOpacity) {
        addAction(new AddOuterGlowAction(viewId, size, color, blendingOpacity));
    }

    public void semAddLinearGradientTextEffect(int viewId, float angle, float scale, int[] colors, float[] alphas, float[] positions, float blendingOpacity) {
        addAction(new AddLinearGradientAction(viewId, angle, scale, colors, alphas, positions, blendingOpacity));
    }

    public void semSetOnLongClickPendingIntent(int viewId, PendingIntent longClickPendingIntent) {
        addAction(new SemSetOnLongClickPendingIntent(viewId, longClickPendingIntent));
    }

    private class SemSetOnLongClickPendingIntent extends Action {
        PendingIntent longClickPendingIntent;
        int viewId;

        public SemSetOnLongClickPendingIntent(int id, PendingIntent longClickPendingIntent) {
            super();
            this.viewId = id;
            this.longClickPendingIntent = longClickPendingIntent;
        }

        public SemSetOnLongClickPendingIntent(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.longClickPendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            this.longClickPendingIntent.writeToParcel(dest, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target != null && this.longClickPendingIntent != null) {
                View.OnLongClickListener longClickListener = new View.OnLongClickListener() { // from class: android.widget.RemoteViews.SemSetOnLongClickPendingIntent.1
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View v) {
                        Rect rect = RemoteViews.getSourceBounds(v);
                        Intent intent = new Intent();
                        intent.setSourceBounds(rect);
                        params.handler.onInteraction(v, SemSetOnLongClickPendingIntent.this.longClickPendingIntent, RemoteResponse.fromFillInIntent(intent));
                        return true;
                    }
                };
                target.setOnLongClickListener(longClickListener);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 100;
        }
    }

    public void semSetOnLongClickPendingIntentTemplate(int viewId, PendingIntent pendingIntentTemplate) {
        addAction(new SemSetLongClickPendingIntentTemplate(viewId, pendingIntentTemplate));
    }

    private class SemSetLongClickPendingIntentTemplate extends Action {
        PendingIntent pendingIntentTemplate;

        public SemSetLongClickPendingIntentTemplate(int id, PendingIntent pendingIntentTemplate) {
            super();
            this.viewId = id;
            this.pendingIntentTemplate = pendingIntentTemplate;
        }

        public SemSetLongClickPendingIntentTemplate(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.pendingIntentTemplate = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            this.pendingIntentTemplate.writeToParcel(dest, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target == null) {
                return;
            }
            if (target instanceof AdapterView) {
                AdapterView<?> av = (AdapterView) target;
                AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() { // from class: android.widget.RemoteViews.SemSetLongClickPendingIntentTemplate.1
                    @Override // android.widget.AdapterView.OnItemLongClickListener
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (view instanceof ViewGroup) {
                            ViewGroup vg = (ViewGroup) view;
                            if (parent instanceof AdapterViewAnimator) {
                                vg = (ViewGroup) vg.getChildAt(0);
                            }
                            if (vg == null) {
                                return true;
                            }
                            RemoteResponse response = null;
                            int childCount = vg.getChildCount();
                            int i = 0;
                            while (true) {
                                if (i >= childCount) {
                                    break;
                                }
                                Object tag = vg.getChildAt(i).getTag(C4337R.id.fillInIntent);
                                if (!(tag instanceof RemoteResponse)) {
                                    i++;
                                } else {
                                    response = (RemoteResponse) tag;
                                    break;
                                }
                            }
                            if (response == null) {
                                return true;
                            }
                            Rect rect = RemoteViews.getSourceBounds(view);
                            Intent intent = new Intent();
                            intent.setSourceBounds(rect);
                            params.handler.onInteraction(view, SemSetLongClickPendingIntentTemplate.this.pendingIntentTemplate, response);
                        }
                        return true;
                    }
                };
                av.setOnItemLongClickListener(listener);
                return;
            }
            Log.m96e(RemoteViews.LOG_TAG, "Cannot setLongClickPendingIntentTemplate on a view which is notan AdapterView (id: " + this.viewId + NavigationBarInflaterView.KEY_CODE_END);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 101;
        }
    }

    public void semSetOnLongClickDragable(int viewId, ClipData clipData, PendingIntent dragStartNotiIntent, PendingIntent dragEnterNotiIntent, PendingIntent dragExitNotiIntent, boolean isNeedToRemove) {
        addAction(new SemSetOnLongClickDragable(viewId, clipData, dragStartNotiIntent, dragEnterNotiIntent, dragExitNotiIntent, isNeedToRemove));
    }

    private class SemSetOnLongClickDragable extends Action {
        ClipData clipData;
        PendingIntent dragEnterNotiIntent;
        PendingIntent dragExitNotiIntent;
        PendingIntent dragStartIntent;
        boolean isNeedToRemove;
        int viewId;

        public SemSetOnLongClickDragable(int id, ClipData clipData, PendingIntent dragStartNotiIntent, PendingIntent dragEnterNotiIntent, PendingIntent dragExitNotiIntent, boolean isNeedToRemove) {
            super();
            this.viewId = id;
            this.isNeedToRemove = isNeedToRemove;
            this.clipData = clipData;
            this.dragStartIntent = dragStartNotiIntent;
            this.dragEnterNotiIntent = dragEnterNotiIntent;
            this.dragExitNotiIntent = dragExitNotiIntent;
        }

        public SemSetOnLongClickDragable(Parcel parcel) {
            super();
            Log.m96e(RemoteViews.LOG_TAG, "SetOnLongClickDragable - read:" + parcel.toString());
            this.viewId = parcel.readInt();
            this.isNeedToRemove = parcel.readByte() != 0;
            this.clipData = (ClipData) parcel.readParcelable(ClipData.class.getClassLoader());
            if (parcel.readInt() != 0) {
                this.dragStartIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                this.dragEnterNotiIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                this.dragExitNotiIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            Log.m96e(RemoteViews.LOG_TAG, "SetOnLongClickDragable - writeToParcel:" + parcel.toString());
            parcel.writeInt(102);
            parcel.writeInt(this.viewId);
            parcel.writeByte(this.isNeedToRemove ? (byte) 1 : (byte) 0);
            parcel.writeParcelable(this.clipData, 0);
            if (this.dragStartIntent != null) {
                parcel.writeInt(1);
                this.dragStartIntent.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (this.dragEnterNotiIntent != null) {
                parcel.writeInt(1);
                this.dragEnterNotiIntent.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            if (this.dragExitNotiIntent != null) {
                parcel.writeInt(1);
                this.dragExitNotiIntent.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            View target = root.findViewById(this.viewId);
            if (target != null) {
                View.OnLongClickListener longClickListener = new View.OnLongClickListener() { // from class: android.widget.RemoteViews.SemSetOnLongClickDragable.1
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View v) {
                        ViewGroup parent;
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        Log.m96e(RemoteViews.LOG_TAG, "Drag info: " + SemSetOnLongClickDragable.this.clipData + SemSetOnLongClickDragable.this.dragStartIntent + SemSetOnLongClickDragable.this.isNeedToRemove);
                        v.startDrag(SemSetOnLongClickDragable.this.clipData, shadowBuilder, null, 0);
                        if (SemSetOnLongClickDragable.this.dragStartIntent != null) {
                            try {
                                v.getContext().startIntentSender(SemSetOnLongClickDragable.this.dragStartIntent.getIntentSender(), null, 268435456, 268435456, 0);
                            } catch (IntentSender.SendIntentException e) {
                                Log.m97e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                            }
                        }
                        if (SemSetOnLongClickDragable.this.isNeedToRemove && (parent = (ViewGroup) v.getParent()) != null) {
                            parent.removeView(v);
                            return true;
                        }
                        return true;
                    }
                };
                target.setOnLongClickListener(longClickListener);
                if (this.dragEnterNotiIntent != null || this.dragExitNotiIntent != null) {
                    View.OnDragListener dragLinstener = new View.OnDragListener() { // from class: android.widget.RemoteViews.SemSetOnLongClickDragable.2
                        @Override // android.view.View.OnDragListener
                        public boolean onDrag(View v, DragEvent dragEvent) {
                            switch (dragEvent.getAction()) {
                                case 5:
                                    if (SemSetOnLongClickDragable.this.dragEnterNotiIntent != null) {
                                        try {
                                            v.getContext().startIntentSender(SemSetOnLongClickDragable.this.dragEnterNotiIntent.getIntentSender(), null, 268435456, 268435456, 0);
                                            break;
                                        } catch (IntentSender.SendIntentException e) {
                                            Log.m97e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                                            return true;
                                        }
                                    }
                                    break;
                                case 6:
                                    if (SemSetOnLongClickDragable.this.dragExitNotiIntent != null) {
                                        try {
                                            v.getContext().startIntentSender(SemSetOnLongClickDragable.this.dragExitNotiIntent.getIntentSender(), null, 268435456, 268435456, 0);
                                            break;
                                        } catch (IntentSender.SendIntentException e2) {
                                            Log.m97e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e2);
                                            return true;
                                        }
                                    }
                                    break;
                            }
                            return true;
                        }
                    };
                    target.setOnDragListener(dragLinstener);
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 102;
        }
    }

    public void semSetOnTouchPendingIntent(int viewId, PendingIntent pendingIntent) {
        addAction(new SemSetOnTouchPendingIntent(viewId, pendingIntent));
    }

    private class SemSetOnTouchPendingIntent extends Action {
        PendingIntent pendingIntent;
        int viewId;

        public SemSetOnTouchPendingIntent(int id, PendingIntent pendingIntent) {
            super();
            this.viewId = id;
            this.pendingIntent = pendingIntent;
        }

        public SemSetOnTouchPendingIntent(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.pendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.pendingIntent, dest);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target != null && this.pendingIntent != null) {
                View.OnTouchListener touchListener = new View.OnTouchListener() { // from class: android.widget.RemoteViews.SemSetOnTouchPendingIntent.1
                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View v, MotionEvent event) {
                        float x = event.getX();
                        float y = event.getY();
                        switch (event.getAction()) {
                            case 0:
                                try {
                                    Intent intent = new Intent();
                                    intent.putExtra(RemoteViews.SEM_EXTRA_X_POSITION, x);
                                    intent.putExtra(RemoteViews.SEM_EXTRA_Y_POSITION, y);
                                    intent.putExtra(RemoteViews.SEM_EXTRA_IS_UP, false);
                                    v.getContext().startIntentSender(SemSetOnTouchPendingIntent.this.pendingIntent.getIntentSender(), intent, 268435456, 268435456, 0);
                                    break;
                                } catch (IntentSender.SendIntentException e) {
                                    Log.m97e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                                    break;
                                }
                            case 1:
                                try {
                                    Intent intent2 = new Intent();
                                    intent2.putExtra(RemoteViews.SEM_EXTRA_X_POSITION, x);
                                    intent2.putExtra(RemoteViews.SEM_EXTRA_Y_POSITION, y);
                                    intent2.putExtra(RemoteViews.SEM_EXTRA_IS_UP, true);
                                    v.getContext().startIntentSender(SemSetOnTouchPendingIntent.this.pendingIntent.getIntentSender(), intent2, 268435456, 268435456, 0);
                                    break;
                                } catch (IntentSender.SendIntentException e2) {
                                    Log.m97e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e2);
                                    break;
                                }
                        }
                        return false;
                    }
                };
                target.setOnTouchListener(touchListener);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 103;
        }

        @Override // android.widget.RemoteViews.Action
        public void clear() {
            this.pendingIntent = null;
        }
    }

    public void semSetOnCheckedChangedPendingIntent(int viewId, PendingIntent pendingIntent) {
        Log.m94d(LOG_TAG, "semSetOnCheckedChangedPendingIntent() viewId = " + viewId + ", pendingIntent = " + pendingIntent);
        addAction(new semSetOnCheckedChangedPendingIntent(viewId, pendingIntent));
    }

    private class semSetOnCheckedChangedPendingIntent extends Action {
        PendingIntent pendingIntent;
        int viewId;

        public semSetOnCheckedChangedPendingIntent(int id, PendingIntent pendingIntent) {
            super();
            this.viewId = id;
            this.pendingIntent = pendingIntent;
        }

        public semSetOnCheckedChangedPendingIntent(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.pendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.pendingIntent, dest);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            CompoundButton target = (CompoundButton) root.findViewById(this.viewId);
            if (target != null && this.pendingIntent != null) {
                CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() { // from class: android.widget.RemoteViews.semSetOnCheckedChangedPendingIntent.1
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        try {
                            Intent intent = new Intent();
                            intent.putExtra(RemoteViews.SEM_EXTRA_IS_CHECKED, isChecked);
                            buttonView.getContext().startIntentSender(semSetOnCheckedChangedPendingIntent.this.pendingIntent.getIntentSender(), intent, 268435456, 268435456, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.m97e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                        }
                    }
                };
                target.setOnCheckedChangeListener(checkListener);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 104;
        }

        @Override // android.widget.RemoteViews.Action
        public void clear() {
            this.pendingIntent = null;
        }
    }

    public void setOrientation(boolean isPortrait) {
        this.mIsForcedOrientation = true;
        this.mIsPortrait = isPortrait;
    }

    public void semSetBlurInfo(int viewId, SemBlurInfo blurInfo) {
        addAction(new semSetBlurInfoAction(viewId, blurInfo));
    }

    private class semSetBlurInfoAction extends Action {
        SemBlurInfo blurInfo;
        int viewId;

        public semSetBlurInfoAction(int id, SemBlurInfo blurInfo) {
            super();
            this.viewId = id;
            this.blurInfo = blurInfo;
        }

        public semSetBlurInfoAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.blurInfo = SemBlurInfo.CREATOR.createFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            this.blurInfo.writeToParcel(dest, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            ReflectionAction ra = new ReflectionAction(this.viewId, "semSetBlurInfo", 30, this.blurInfo);
            ra.apply(root, rootParent, params);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 105;
        }
    }

    public void semSetAnimation(SemRemoteViewsAnimation animation) {
        if (animation instanceof SemRemoteViewsDrawableAnimation) {
            addAction(new SemAnimationAction(1, animation));
            return;
        }
        if (animation instanceof SemRemoteViewsViewAnimation) {
            addAction(new SemAnimationAction(2, animation));
            return;
        }
        if (animation instanceof SemRemoteViewsPropertyAnimation) {
            addAction(new SemAnimationAction(3, animation));
        } else if (animation instanceof SemRemoteViewsValueAnimation) {
            addAction(new SemAnimationAction(4, animation));
        } else if (animation instanceof SemRemoteViewsBasicAnimation) {
            addAction(new SemAnimationAction(5, animation));
        }
    }

    private class SemAnimationAction extends Action {
        public static final int TAG = 107;
        static final int TYPE_BASIC_ANIMATION = 5;
        static final int TYPE_DRAWABLE_ANIMATION = 1;
        static final int TYPE_DYNAMIC_ANIMATION = 4;
        static final int TYPE_PROPERTY_ANIMATION = 3;
        static final int TYPE_VIEW_ANIMATION = 2;
        SemRemoteViewsAnimation animation;
        int animationType;

        public SemAnimationAction(int animationType, SemRemoteViewsAnimation animation) {
            super();
            this.animationType = animationType;
            this.animation = animation;
        }

        public SemAnimationAction(Parcel parcel) {
            super();
            int readInt = parcel.readInt();
            this.animationType = readInt;
            switch (readInt) {
                case 1:
                    this.animation = SemRemoteViewsDrawableAnimation.CREATOR.createFromParcel(parcel);
                    break;
                case 2:
                    this.animation = SemRemoteViewsViewAnimation.CREATOR.createFromParcel(parcel);
                    break;
                case 3:
                    this.animation = SemRemoteViewsPropertyAnimation.CREATOR.createFromParcel(parcel);
                    break;
                case 4:
                    this.animation = SemRemoteViewsValueAnimation.CREATOR.createFromParcel(parcel);
                    break;
                case 5:
                    this.animation = SemRemoteViewsBasicAnimation.CREATOR.createFromParcel(parcel);
                    break;
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.animationType);
            switch (this.animationType) {
                case 1:
                    SemRemoteViewsDrawableAnimation.writeToParcel((SemRemoteViewsDrawableAnimation) this.animation, dest);
                    break;
                case 2:
                    SemRemoteViewsViewAnimation.writeToParcel((SemRemoteViewsViewAnimation) this.animation, dest);
                    break;
                case 3:
                    SemRemoteViewsPropertyAnimation.writeToParcel((SemRemoteViewsPropertyAnimation) this.animation, dest);
                    break;
                case 4:
                    SemRemoteViewsValueAnimation.writeToParcel((SemRemoteViewsValueAnimation) this.animation, dest);
                    break;
                case 5:
                    SemRemoteViewsBasicAnimation.writeToParcel((SemRemoteViewsBasicAnimation) this.animation, dest);
                    break;
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            SemRemoteViewsAnimation semRemoteViewsAnimation = this.animation;
            if (semRemoteViewsAnimation != null) {
                semRemoteViewsAnimation.play(root);
            }
        }

        public String getActionName() {
            return "SemAnimationAction";
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 107;
        }
    }
}
