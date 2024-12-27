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
import android.appwidget.flags.Flags;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
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
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.UserHandle;
import android.system.Os;
import android.telecom.Logging.Session;
import android.text.ParcelableSpan;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.IntArray;
import android.util.Log;
import android.util.LongArray;
import android.util.Pair;
import android.util.SizeF;
import android.util.SparseArray;
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
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.IRemoteViewsFactory;
import com.android.internal.widget.remotecompose.player.RemoteComposeDocument;
import com.android.internal.widget.remotecompose.player.RemoteComposePlayer;
import com.samsung.android.cocktailbar.CocktailHostView;
import com.samsung.android.rune.ViewRune;
import java.io.ByteArrayInputStream;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

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
    private static final int MAX_ADAPTER_CONVERSION_WAITING_TIME_MS = 20000;
    private static final int MAX_INIT_VIEW_COUNT = 16;
    private static final int MAX_NESTED_VIEWS = 10;
    private static final int MAX_SINGLE_PARCEL_SIZE = 800000;
    private static final int MODE_HAS_LANDSCAPE_AND_PORTRAIT = 1;
    private static final int MODE_HAS_SIZED_REMOTEVIEWS = 2;
    private static final int MODE_NORMAL = 0;
    private static final int NIGHT_MODE_REFLECTION_ACTION_TAG = 30;
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
    private static final int SEM_SET_STRING_TAG = 109;
    private static final int SEM_SET_TEXT_VIEW_SHADOW_ACTION_TAG = 108;
    private static final int SEM_TEXT_VIEW_TEXT_ACTION_TAG = 110;
    private static final int SEM_VIEW_OBJECT_ANIMATOR_ACTION_TAG = 106;
    private static final int SET_COMPOUND_BUTTON_CHECKED_TAG = 26;
    private static final int SET_DRAWABLE_TINT_TAG = 3;
    private static final int SET_DRAW_INSTRUCTION_TAG = 35;
    private static final int SET_EMPTY_VIEW_ACTION_TAG = 6;
    private static final int SET_INT_TAG_TAG = 22;
    private static final int SET_ON_CHECKED_CHANGE_RESPONSE_TAG = 29;
    private static final int SET_ON_CLICK_RESPONSE_TAG = 1;
    private static final int SET_ON_STYLUS_HANDWRITING_RESPONSE_TAG = 34;
    private static final int SET_PENDING_INTENT_TEMPLATE_TAG = 8;
    private static final int SET_RADIO_GROUP_CHECKED = 27;
    private static final int SET_REMOTE_ADAPTER_TAG = 33;
    private static final int SET_REMOTE_COLLECTION_ITEMS_ADAPTER_TAG = 31;
    private static final int SET_REMOTE_INPUTS_ACTION_TAG = 18;
    private static final int SET_REMOTE_VIEW_ADAPTER_INTENT_TAG = 10;
    private static final int SET_RIPPLE_DRAWABLE_COLOR_TAG = 21;
    private static final int SET_VIEW_OUTLINE_RADIUS_TAG = 28;
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
    private RemoteCollectionCache mCollectionCache;
    private SparseArray<Intent> mFillInIntent;
    private boolean mHasDrawInstructions;
    private SizeF mIdealSize;
    private boolean mIsForcedOrientation;
    private boolean mIsPortrait;
    private boolean mIsRoot;
    private RemoteViews mLandscape;
    private int mLayoutId;
    private LayoutInflater.Factory2 mLayoutInflaterFactory2;
    private int mLightBackgroundLayoutId;
    private SparseArray<PendingIntent> mPendingIntentTemplate;
    private RemoteViews mPortrait;
    private long mProviderInstanceId;
    private List<RemoteViews> mSizedRemoteViews;
    private int mViewId;
    private static final Parcel.ReadWriteHelper ALTERNATIVE_DEFAULT = new Parcel.ReadWriteHelper();
    private static final LayoutInflater.Filter INFLATER_FILTER = new LayoutInflater.Filter() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda1
        @Override // android.view.LayoutInflater.Filter
        public final boolean onLoadClass(Class cls) {
            boolean isAnnotationPresent;
            isAnnotationPresent = cls.isAnnotationPresent(RemoteViews.RemoteView.class);
            return isAnnotationPresent;
        }
    };
    private static final InteractionHandler DEFAULT_INTERACTION_HANDLER = new InteractionHandler() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda2
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
    private static final String[] PARCELABLE_SPAN_KEYS = {"TypefaceSpan", "TextAppearanceSpan", "UnderlineSpan", "StrikethroughSpan", "StyleSpan"};
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
    }

    public void setRemoteInputs(int viewId, RemoteInput[] remoteInputs) {
        this.mActions.add(new SetRemoteInputsAction(viewId, remoteInputs));
    }

    public void setLayoutInflaterFactory(LayoutInflater.Factory2 factory) {
        this.mLayoutInflaterFactory2 = factory;
    }

    public LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory2;
    }

    public void reduceImageSizes(int maxWidth, int maxHeight) {
        ArrayList<Bitmap> cache = this.mBitmapCache.mBitmaps;
        for (int i = 0; i < cache.size(); i++) {
            Bitmap bitmap = cache.get(i);
            cache.set(i, Icon.scaleDownIfNecessary(bitmap, maxWidth, maxHeight));
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Action {
        public static final int MERGE_APPEND = 1;
        public static final int MERGE_IGNORE = 2;
        public static final int MERGE_REPLACE = 0;
        int mViewId;

        public abstract void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException;

        public abstract int getActionTag();

        public abstract void writeToParcel(Parcel parcel, int i);

        private Action() {
        }

        public void setHierarchyRootData(HierarchyRootData root) {
        }

        public int mergeBehavior() {
            return 0;
        }

        public String getUniqueKey() {
            return getActionTag() + Session.SESSION_SEPARATION_CHAR_CHILD + this.mViewId;
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

        @Override // android.widget.RemoteViews.Action
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

    public boolean isLegacyListRemoteViews() {
        return this.mCollectionCache.mIdToUriMapping.size() > 0;
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
        if (this.mLandscape != null) {
            this.mLandscape.visitUris(visitor);
        }
        if (this.mPortrait != null) {
            this.mPortrait.visitUris(visitor);
        }
    }

    public boolean replaceRemoteCollections(int viewId) {
        boolean isActionReplaced = false;
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                Action action = this.mActions.get(i);
                if (action instanceof SetRemoteCollectionItemListAdapterAction) {
                    SetRemoteCollectionItemListAdapterAction itemsAction = (SetRemoteCollectionItemListAdapterAction) action;
                    if (itemsAction.mViewId == viewId && itemsAction.mServiceIntent != null) {
                        SetRemoteCollectionItemListAdapterAction newCollectionAction = new SetRemoteCollectionItemListAdapterAction(itemsAction.mViewId, itemsAction.mServiceIntent);
                        newCollectionAction.mIntentId = itemsAction.mIntentId;
                        newCollectionAction.mIsReplacedIntoAction = true;
                        this.mActions.set(i, newCollectionAction);
                        isActionReplaced = true;
                    }
                }
                if (action instanceof SetRemoteViewsAdapterIntent) {
                    SetRemoteViewsAdapterIntent intentAction = (SetRemoteViewsAdapterIntent) action;
                    if (intentAction.mViewId == viewId) {
                        this.mActions.set(i, new SetRemoteCollectionItemListAdapterAction(intentAction.mViewId, intentAction.mIntent));
                        isActionReplaced = true;
                    }
                }
                if (action instanceof ViewGroupActionAdd) {
                    ViewGroupActionAdd groupAction = (ViewGroupActionAdd) action;
                    if (groupAction.mNestedViews != null) {
                        isActionReplaced |= groupAction.mNestedViews.replaceRemoteCollections(viewId);
                    }
                }
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                isActionReplaced |= this.mSizedRemoteViews.get(i2).replaceRemoteCollections(viewId);
            }
        }
        if (this.mLandscape != null) {
            isActionReplaced |= this.mLandscape.replaceRemoteCollections(viewId);
        }
        if (this.mPortrait != null) {
            return isActionReplaced | this.mPortrait.replaceRemoteCollections(viewId);
        }
        return isActionReplaced;
    }

    public boolean hasLegacyLists() {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                Action action = this.mActions.get(i);
                if (action instanceof SetRemoteCollectionItemListAdapterAction) {
                    SetRemoteCollectionItemListAdapterAction itemsAction = (SetRemoteCollectionItemListAdapterAction) action;
                    if (itemsAction.mServiceIntent != null) {
                        return true;
                    }
                }
                if (action instanceof SetRemoteViewsAdapterIntent) {
                    SetRemoteViewsAdapterIntent intentAction = (SetRemoteViewsAdapterIntent) action;
                    if (intentAction.mIntent != null) {
                        return true;
                    }
                }
                if (action instanceof ViewGroupActionAdd) {
                    ViewGroupActionAdd groupAction = (ViewGroupActionAdd) action;
                    if (groupAction.mNestedViews != null && groupAction.mNestedViews.hasLegacyLists()) {
                        return true;
                    }
                }
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                if (this.mSizedRemoteViews.get(i2).hasLegacyLists()) {
                    return true;
                }
            }
        }
        if (this.mLandscape == null || !this.mLandscape.hasLegacyLists()) {
            return this.mPortrait != null && this.mPortrait.hasLegacyLists();
        }
        return true;
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
        int mEmptyViewId;

        SetEmptyView(int viewId, int emptyViewId) {
            super();
            this.mViewId = viewId;
            this.mEmptyViewId = emptyViewId;
        }

        SetEmptyView(Parcel in) {
            super();
            this.mViewId = in.readInt();
            this.mEmptyViewId = in.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.mViewId);
            out.writeInt(this.mEmptyViewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View view = root.findViewById(this.mViewId);
            if (view instanceof AdapterView) {
                AdapterView<?> adapterView = (AdapterView) view;
                View emptyView = root.findViewById(this.mEmptyViewId);
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
        PendingIntent mPendingIntentTemplate;

        public SetPendingIntentTemplate(int id, PendingIntent pendingIntentTemplate) {
            super();
            this.mViewId = id;
            this.mPendingIntentTemplate = pendingIntentTemplate;
        }

        public SetPendingIntentTemplate(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mPendingIntentTemplate = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntentTemplate, dest);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
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
                av.setTag(this.mPendingIntentTemplate);
                return;
            }
            Log.e(RemoteViews.LOG_TAG, "Cannot setPendingIntentTemplate on a view which is notan AdapterView (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
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
                Object tag = view.getTag(R.id.fillInIntent);
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
        int mIntentId;
        boolean mIsReplacedIntoAction;
        private RemoteCollectionItems mItems;
        final Intent mServiceIntent;

        SetRemoteCollectionItemListAdapterAction(int id, RemoteCollectionItems items) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = id;
            items.setHierarchyRootData(RemoteViews.this.getHierarchyRootData());
            this.mItems = items;
            this.mServiceIntent = null;
        }

        SetRemoteCollectionItemListAdapterAction(int id, Intent intent) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = id;
            this.mItems = null;
            this.mServiceIntent = intent;
        }

        SetRemoteCollectionItemListAdapterAction(Parcel parcel) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = parcel.readInt();
            this.mIntentId = parcel.readInt();
            this.mIsReplacedIntoAction = parcel.readBoolean();
            this.mServiceIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            this.mItems = this.mServiceIntent == null ? new RemoteCollectionItems(parcel, RemoteViews.this.getHierarchyRootData()) : null;
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData rootData) {
            if (this.mItems != null) {
                this.mItems.setHierarchyRootData(rootData);
            } else if (this.mIntentId != -1) {
                RemoteViews.this.mCollectionCache.setHierarchyDataForId(this.mIntentId, rootData);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mIntentId);
            dest.writeBoolean(this.mIsReplacedIntoAction);
            dest.writeTypedObject(this.mServiceIntent, flags);
            if (this.mItems != null) {
                this.mItems.writeToParcel(dest, flags, true);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            RemoteCollectionItems items;
            ActionException actionException;
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            if (this.mIntentId == -1) {
                if (this.mItems == null) {
                    items = new RemoteCollectionItems.Builder().build();
                } else {
                    items = this.mItems;
                }
            } else {
                items = RemoteViews.this.mCollectionCache.getItemsForId(this.mIntentId);
            }
            if (!(rootParent instanceof AppWidgetHostView) && !RemoteViews.this.mAllowOtherRootParent) {
                Log.e(RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (!(target instanceof AdapterView)) {
                Log.e(RemoteViews.LOG_TAG, "Cannot call setRemoteAdapter on a view which is not an AdapterView (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            AdapterView adapterView = (AdapterView) target;
            Adapter adapter = adapterView.getAdapter();
            if ((adapter instanceof RemoteCollectionItemsAdapter) && adapter.getViewTypeCount() >= items.getViewTypeCount()) {
                try {
                    ((RemoteCollectionItemsAdapter) adapter).setData(items, params.handler, params.colorResources);
                } finally {
                }
            } else {
                try {
                    adapterView.setAdapter(new RemoteCollectionItemsAdapter(items, params.handler, params.colorResources));
                } finally {
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 31;
        }

        @Override // android.widget.RemoteViews.Action
        public String getUniqueKey() {
            return "33_" + this.mViewId;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            if (this.mIntentId != -1 || this.mItems == null) {
                return;
            }
            this.mItems.visitUris(visitor);
        }
    }

    public CompletableFuture<Void> collectAllIntents() {
        return this.mCollectionCache.collectAllIntentsNoComplete(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class RemoteCollectionCache {
        private final SparseArray<String> mIdToUriMapping = new SparseArray<>();
        private final Map<String, RemoteCollectionItems> mUriToCollectionMapping = new HashMap();

        RemoteCollectionCache() {
        }

        RemoteCollectionCache(RemoteCollectionCache src) {
            for (int i = 0; i < src.mIdToUriMapping.size(); i++) {
                String uri = src.mIdToUriMapping.valueAt(i);
                this.mIdToUriMapping.put(src.mIdToUriMapping.keyAt(i), uri);
                this.mUriToCollectionMapping.put(uri, src.mUriToCollectionMapping.get(uri));
            }
        }

        RemoteCollectionCache(Parcel in) {
            int cacheSize = in.readInt();
            HierarchyRootData currentRootData = new HierarchyRootData(RemoteViews.this.mBitmapCache, this, RemoteViews.this.mApplicationInfoCache, RemoteViews.this.mClassCookies);
            for (int i = 0; i < cacheSize; i++) {
                int intentId = in.readInt();
                String intentUri = in.readString8();
                RemoteCollectionItems items = new RemoteCollectionItems(in, currentRootData);
                this.mIdToUriMapping.put(intentId, intentUri);
                this.mUriToCollectionMapping.put(intentUri, items);
            }
        }

        void setHierarchyDataForId(int intentId, HierarchyRootData data) {
            String uri = this.mIdToUriMapping.get(intentId);
            if (this.mUriToCollectionMapping.get(uri) == null) {
                Log.e(RemoteViews.LOG_TAG, "Error setting hierarchy data for id=" + intentId);
            } else {
                RemoteCollectionItems items = this.mUriToCollectionMapping.get(uri);
                items.setHierarchyRootData(data);
            }
        }

        RemoteCollectionItems getItemsForId(int intentId) {
            String uri = this.mIdToUriMapping.get(intentId);
            return this.mUriToCollectionMapping.get(uri);
        }

        public CompletableFuture<Void> collectAllIntentsNoComplete(RemoteViews inViews) {
            SparseArray<Intent> idToIntentMapping = new SparseArray<>();
            collectAllIntentsInternal(inViews, idToIntentMapping);
            int numOfIntents = idToIntentMapping.size();
            if (numOfIntents == 0) {
                Log.e(RemoteViews.LOG_TAG, "Possibly notifying updates for nonexistent view Id");
                return CompletableFuture.completedFuture(null);
            }
            Parcel sizeTestParcel = Parcel.obtain();
            RemoteViews.this.writeToParcel(sizeTestParcel, 0, idToIntentMapping);
            int remainingSize = RemoteViews.MAX_SINGLE_PARCEL_SIZE - sizeTestParcel.dataSize();
            sizeTestParcel.recycle();
            int individualSize = remainingSize >= 0 ? remainingSize / numOfIntents : 0;
            return connectAllUniqueIntents(individualSize, idToIntentMapping);
        }

        private void collectAllIntentsInternal(RemoteViews inViews, SparseArray<Intent> idToIntentMapping) {
            if (inViews.hasSizedRemoteViews()) {
                for (RemoteViews remoteViews : inViews.mSizedRemoteViews) {
                    collectAllIntentsInternal(remoteViews, idToIntentMapping);
                }
                return;
            }
            if (inViews.hasLandscapeAndPortraitLayouts()) {
                collectAllIntentsInternal(inViews.mLandscape, idToIntentMapping);
                collectAllIntentsInternal(inViews.mPortrait, idToIntentMapping);
                return;
            }
            if (inViews.mActions != null) {
                Iterator it = inViews.mActions.iterator();
                while (it.hasNext()) {
                    Action action = (Action) it.next();
                    if (action instanceof SetRemoteCollectionItemListAdapterAction) {
                        SetRemoteCollectionItemListAdapterAction rca = (SetRemoteCollectionItemListAdapterAction) action;
                        if (rca.mIntentId == -1 || rca.mIsReplacedIntoAction) {
                            if (rca.mIntentId != -1 && rca.mIsReplacedIntoAction) {
                                rca.mIsReplacedIntoAction = false;
                                if (!idToIntentMapping.contains(rca.mIntentId)) {
                                    idToIntentMapping.put(rca.mIntentId, rca.mServiceIntent);
                                    rca.mItems = null;
                                }
                            } else if (rca.mServiceIntent != null) {
                                String uri = rca.mServiceIntent.toUri(0);
                                int index = this.mIdToUriMapping.indexOfValueByValue(uri);
                                if (index == -1) {
                                    int newIntentId = this.mIdToUriMapping.size();
                                    rca.mIntentId = newIntentId;
                                    this.mIdToUriMapping.put(newIntentId, uri);
                                    idToIntentMapping.put(rca.mIntentId, rca.mServiceIntent);
                                    rca.mItems = null;
                                } else {
                                    rca.mIntentId = this.mIdToUriMapping.keyAt(index);
                                    rca.mItems = null;
                                }
                            } else {
                                for (RemoteViews views : rca.mItems.mViews) {
                                    collectAllIntentsInternal(views, idToIntentMapping);
                                }
                            }
                        }
                    } else if (action instanceof ViewGroupActionAdd) {
                        ViewGroupActionAdd vgaa = (ViewGroupActionAdd) action;
                        if (vgaa.mNestedViews != null) {
                            collectAllIntentsInternal(vgaa.mNestedViews, idToIntentMapping);
                        }
                    }
                }
            }
        }

        private CompletableFuture<Void> connectAllUniqueIntents(int individualSize, SparseArray<Intent> idToIntentMapping) {
            List<CompletableFuture<Void>> intentFutureList = new ArrayList<>();
            for (int i = 0; i < idToIntentMapping.size(); i++) {
                final String currentIntentUri = this.mIdToUriMapping.get(idToIntentMapping.keyAt(i));
                Intent currentIntent = idToIntentMapping.valueAt(i);
                intentFutureList.add(getItemsFutureFromIntentWithTimeout(currentIntent, individualSize).thenAccept(new Consumer() { // from class: android.widget.RemoteViews$RemoteCollectionCache$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RemoteViews.RemoteCollectionCache.this.lambda$connectAllUniqueIntents$0(currentIntentUri, (RemoteViews.RemoteCollectionItems) obj);
                    }
                }));
            }
            return CompletableFuture.allOf((CompletableFuture[]) intentFutureList.toArray(new IntFunction() { // from class: android.widget.RemoteViews$RemoteCollectionCache$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    return RemoteViews.RemoteCollectionCache.lambda$connectAllUniqueIntents$1(i2);
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$connectAllUniqueIntents$0(String currentIntentUri, RemoteCollectionItems items) {
            items.setHierarchyRootData(RemoteViews.this.getHierarchyRootData());
            this.mUriToCollectionMapping.put(currentIntentUri, items);
        }

        static /* synthetic */ CompletableFuture[] lambda$connectAllUniqueIntents$1(int x$0) {
            return new CompletableFuture[x$0];
        }

        private static CompletableFuture<RemoteCollectionItems> getItemsFutureFromIntentWithTimeout(Intent intent, final int individualSize) {
            if (intent == null) {
                Log.e(RemoteViews.LOG_TAG, "Null intent received when generating adapter future");
                return CompletableFuture.completedFuture(new RemoteCollectionItems.Builder().build());
            }
            final Context context = ActivityThread.currentApplication();
            final CompletableFuture<RemoteCollectionItems> result = new CompletableFuture<>();
            context.bindService(intent, Context.BindServiceFlags.of(1L), result.defaultExecutor(), new ServiceConnection() { // from class: android.widget.RemoteViews.RemoteCollectionCache.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    RemoteCollectionItems items;
                    try {
                        try {
                            items = IRemoteViewsFactory.Stub.asInterface(iBinder).getRemoteCollectionItems(individualSize);
                        } catch (RemoteException re) {
                            RemoteCollectionItems items2 = new RemoteCollectionItems.Builder().build();
                            Log.e(RemoteViews.LOG_TAG, "Error getting collection items from the factory", re);
                            context.unbindService(this);
                            items = items2;
                        }
                        if (items == null) {
                            items = new RemoteCollectionItems.Builder().build();
                        }
                        result.complete(items);
                    } finally {
                        context.unbindService(this);
                    }
                }

                @Override // android.content.ServiceConnection
                public void onNullBinding(ComponentName name) {
                    context.unbindService(this);
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                }
            });
            result.completeOnTimeout(new RemoteCollectionItems.Builder().build(), 20000L, TimeUnit.MILLISECONDS);
            return result;
        }

        public void writeToParcel(Parcel out, int flags, SparseArray<Intent> intentsToIgnore) {
            out.writeInt(this.mIdToUriMapping.size());
            for (int i = 0; i < this.mIdToUriMapping.size(); i++) {
                int currentIntentId = this.mIdToUriMapping.keyAt(i);
                if (intentsToIgnore == null || !intentsToIgnore.contains(currentIntentId)) {
                    out.writeInt(currentIntentId);
                    String intentUri = this.mIdToUriMapping.valueAt(i);
                    out.writeString8(intentUri);
                    this.mUriToCollectionMapping.get(intentUri).writeToParcel(out, flags, true);
                }
            }
        }
    }

    private class SetRemoteViewsAdapterIntent extends Action {
        Intent mIntent;
        boolean mIsAsync;

        public SetRemoteViewsAdapterIntent(int id, Intent intent) {
            super();
            this.mIsAsync = false;
            this.mViewId = id;
            this.mIntent = intent;
        }

        public SetRemoteViewsAdapterIntent(Parcel parcel) {
            super();
            this.mIsAsync = false;
            this.mViewId = parcel.readInt();
            this.mIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeTypedObject(this.mIntent, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            boolean isCocktail = false;
            if (rootParent instanceof CocktailHostView) {
                isCocktail = true;
            }
            if (!(rootParent instanceof AppWidgetHostView) && !RemoteViews.this.mAllowOtherRootParent && !isCocktail) {
                Log.e(RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (!(target instanceof AbsListView) && !(target instanceof AdapterViewAnimator)) {
                Log.e(RemoteViews.LOG_TAG, "Cannot setRemoteAdapter on a view which is not an AbsListView or AdapterViewAnimator (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (isCocktail) {
                this.mIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_COCKTAIL, 1);
                CocktailHostView host = (CocktailHostView) rootParent;
                this.mIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, host.getCocktailId());
            } else {
                if (!RemoteViews.this.mAllowOtherRootParent || (rootParent instanceof AppWidgetHostView)) {
                    AppWidgetHostView host2 = (AppWidgetHostView) rootParent;
                    RemoteViews.this.mAppWidgetId = host2.getAppWidgetId();
                }
                this.mIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, RemoteViews.this.mAppWidgetId).putExtra(RemoteViews.EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND, RemoteViews.this.hasFlags(4));
            }
            if (target instanceof AbsListView) {
                AbsListView v = (AbsListView) target;
                v.setRemoteViewsAdapter(this.mIntent, this.mIsAsync);
                v.setRemoteViewsInteractionHandler(params.handler);
                v.hidden_semSetAppWidgetId(RemoteViews.this.mAppWidgetId);
                return;
            }
            if (target instanceof AdapterViewAnimator) {
                AdapterViewAnimator v2 = (AdapterViewAnimator) target;
                v2.setRemoteViewsAdapter(this.mIntent, this.mIsAsync);
                v2.setRemoteViewsOnClickHandler(params.handler);
                v2.hidden_semSetAppWidgetId(RemoteViews.this.mAppWidgetId);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            SetRemoteViewsAdapterIntent copy = RemoteViews.this.new SetRemoteViewsAdapterIntent(this.mViewId, this.mIntent);
            copy.mIsAsync = true;
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
            this.mViewId = id;
            this.mResponse = response;
        }

        SetOnClickResponse(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mResponse = new RemoteResponse();
            this.mResponse.readFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            this.mResponse.writeToParcel(dest, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target;
            if ((RemoteViews.this.hasDrawInstructions() && (root instanceof RemoteComposePlayer)) || (target = root.findViewById(this.mViewId)) == null) {
                return;
            }
            if (this.mResponse.mPendingIntent != null) {
                if (RemoteViews.this.hasFlags(2) && !RemoteViews.this.mAllowOtherRootParent) {
                    Log.w(RemoteViews.LOG_TAG, "Cannot SetOnClickResponse for collection item (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                    ApplicationInfo appInfo = root.getContext().getApplicationInfo();
                    if (appInfo != null && appInfo.targetSdkVersion >= 16) {
                        return;
                    }
                }
                target.setTagInternal(R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!RemoteViews.this.hasFlags(2)) {
                    Log.e(RemoteViews.LOG_TAG, "The method setOnClickFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                } else if (target == root) {
                    target.setTagInternal(R.id.fillInIntent, this.mResponse);
                    return;
                }
            } else {
                target.setOnClickListener(null);
                target.setTagInternal(R.id.pending_intent_tag, null);
                target.setTagInternal(R.id.fillInIntent, null);
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
    class SetOnStylusHandwritingResponse extends Action {
        final PendingIntent mPendingIntent;

        SetOnStylusHandwritingResponse(int id, PendingIntent pendingIntent) {
            super();
            this.mViewId = id;
            this.mPendingIntent = pendingIntent;
        }

        SetOnStylusHandwritingResponse(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mPendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, dest);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            final View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            if (RemoteViews.this.hasFlags(2)) {
                Log.w(RemoteViews.LOG_TAG, "Cannot use setOnStylusHandwritingPendingIntent for collection item (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (this.mPendingIntent != null) {
                final RemoteResponse response = RemoteResponse.fromPendingIntent(this.mPendingIntent);
                target.setHandwritingDelegatorCallback(new Runnable() { // from class: android.widget.RemoteViews$SetOnStylusHandwritingResponse$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteViews.RemoteResponse.this.handleViewInteraction(target, params.handler);
                    }
                });
                target.setAllowedHandwritingDelegatePackage(this.mPendingIntent.getCreatorPackage());
            } else {
                target.setHandwritingDelegatorCallback(null);
                target.setAllowedHandwritingDelegatePackage(null);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 34;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnCheckedChangeResponse extends Action {
        private final RemoteResponse mResponse;

        SetOnCheckedChangeResponse(int id, RemoteResponse response) {
            super();
            this.mViewId = id;
            this.mResponse = response;
        }

        SetOnCheckedChangeResponse(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mResponse = new RemoteResponse();
            this.mResponse.readFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            this.mResponse.writeToParcel(dest, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            if (!(target instanceof CompoundButton)) {
                Log.w(RemoteViews.LOG_TAG, "setOnCheckedChange methods cannot be used on non-CompoundButton child (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            CompoundButton button = (CompoundButton) target;
            if (this.mResponse.mPendingIntent != null) {
                if (RemoteViews.this.hasFlags(2)) {
                    Log.w(RemoteViews.LOG_TAG, "Cannot setOnCheckedChangePendingIntent for collection item (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                    return;
                }
                target.setTagInternal(R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!RemoteViews.this.hasFlags(2)) {
                    Log.e(RemoteViews.LOG_TAG, "The method setOnCheckedChangeFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                }
            } else {
                button.setOnCheckedChangeListener(null);
                button.setTagInternal(R.id.remote_checked_change_listener_tag, null);
                return;
            }
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: android.widget.RemoteViews$SetOnCheckedChangeResponse$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    RemoteViews.SetOnCheckedChangeResponse.this.lambda$apply$0(params, compoundButton, z);
                }
            };
            button.setTagInternal(R.id.remote_checked_change_listener_tag, onCheckedChangeListener);
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
        synchronized (sMethods) {
            sLookupKey.set(cls, paramType, methodName);
            MethodArgs result = sMethods.get(sLookupKey);
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
                    sMethods.put(key, result);
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
        int mColorFilter;
        PorterDuff.Mode mFilterMode;
        boolean mTargetBackground;

        SetDrawableTint(int id, boolean targetBackground, int colorFilter, PorterDuff.Mode mode) {
            super();
            this.mViewId = id;
            this.mTargetBackground = targetBackground;
            this.mColorFilter = colorFilter;
            this.mFilterMode = mode;
        }

        SetDrawableTint(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mTargetBackground = parcel.readInt() != 0;
            this.mColorFilter = parcel.readInt();
            this.mFilterMode = PorterDuff.intToMode(parcel.readInt());
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mTargetBackground ? 1 : 0);
            parcel.writeInt(this.mColorFilter);
            parcel.writeInt(PorterDuff.modeToInt(this.mFilterMode));
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            Drawable targetDrawable = null;
            if (this.mTargetBackground) {
                targetDrawable = target.getBackground();
            } else if (target instanceof ImageView) {
                ImageView imageView = (ImageView) target;
                targetDrawable = imageView.getDrawable();
            }
            if (targetDrawable != null) {
                targetDrawable.mutate().setColorFilter(this.mColorFilter, this.mFilterMode);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 3;
        }
    }

    private class SetTextViewShadowAction extends Action {
        int color;
        float dx;
        float dy;
        float radius;
        int viewId;

        public SetTextViewShadowAction(int viewId, float radius, float dx, float dy, int color) {
            super();
            this.viewId = viewId;
            this.radius = radius;
            this.dx = dx;
            this.dy = dy;
            this.color = color;
        }

        public SetTextViewShadowAction(Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.radius = parcel.readFloat();
            this.dx = parcel.readFloat();
            this.dy = parcel.readFloat();
            this.color = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.viewId);
            dest.writeFloat(this.radius);
            dest.writeFloat(this.dx);
            dest.writeFloat(this.dy);
            dest.writeInt(this.color);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.viewId);
            if (target instanceof TextView) {
                ((TextView) target).setShadowLayer(this.radius, this.dx, this.dy, this.color);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 108;
        }
    }

    private static class SetStringTagAction extends Action {
        private final int mKey;
        private final String mTag;
        private final int mViewId;

        SetStringTagAction(int viewId, int key, String tag) {
            super();
            this.mViewId = viewId;
            this.mKey = key;
            this.mTag = tag;
        }

        SetStringTagAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mKey = parcel.readInt();
            this.mTag = parcel.readString();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mKey);
            dest.writeString(this.mTag);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            target.setTag(this.mKey, this.mTag);
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
            this.mViewId = id;
            this.mColorStateList = colorStateList;
        }

        SetRippleDrawableColor(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mColorStateList = (ColorStateList) parcel.readParcelable(null, ColorStateList.class);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeParcelable(this.mColorStateList, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
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
            this.mViewId = viewId;
            this.mNext = next;
        }

        ViewContentNavigation(Parcel in) {
            super();
            this.mViewId = in.readInt();
            this.mNext = in.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.mViewId);
            out.writeBoolean(this.mNext);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View view = root.findViewById(this.mViewId);
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
        Bitmap mBitmap;
        int mBitmapId;
        String mMethodName;

        BitmapReflectionAction(int viewId, String methodName, Bitmap bitmap) {
            super();
            this.mBitmap = bitmap;
            this.mViewId = viewId;
            this.mMethodName = methodName;
            this.mBitmapId = RemoteViews.this.mBitmapCache.getBitmapId(bitmap);
        }

        BitmapReflectionAction(Parcel in) {
            super();
            this.mViewId = in.readInt();
            this.mMethodName = in.readString8();
            this.mBitmapId = in.readInt();
            this.mBitmap = RemoteViews.this.mBitmapCache.getBitmapForId(this.mBitmapId);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeString8(this.mMethodName);
            dest.writeInt(this.mBitmapId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            ReflectionAction ra = new ReflectionAction(this.mViewId, this.mMethodName, 12, this.mBitmap);
            ra.apply(root, rootParent, params);
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData rootData) {
            this.mBitmapId = rootData.mBitmapCache.getBitmapId(this.mBitmap);
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
        String mMethodName;
        int mType;

        protected abstract Object getParameterValue(View view) throws ActionException;

        BaseReflectionAction(int viewId, String methodName, int type) {
            super();
            this.mViewId = viewId;
            this.mMethodName = methodName;
            this.mType = type;
        }

        BaseReflectionAction(Parcel in) {
            super();
            this.mViewId = in.readInt();
            this.mMethodName = in.readString8();
            this.mType = in.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.mViewId);
            out.writeString8(this.mMethodName);
            out.writeInt(this.mType);
        }

        @Override // android.widget.RemoteViews.Action
        public final void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View view = root.findViewById(this.mViewId);
            if (view == null) {
                return;
            }
            Class<?> param = RemoteViews.getParameterType(this.mType);
            if (param == null) {
                throw new ActionException("bad type: " + this.mType);
            }
            Object value = getParameterValue(view);
            try {
                (void) RemoteViews.getMethod(view, this.mMethodName, param, false).invoke(view, value);
            } catch (Throwable ex) {
                throw new ActionException(ex);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public final Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            Bitmap bitmap;
            View view = root.findViewById(this.mViewId);
            if (view == null) {
                return RemoteViews.ACTION_NOOP;
            }
            Class<?> param = RemoteViews.getParameterType(this.mType);
            if (param == null) {
                throw new ActionException("bad type: " + this.mType);
            }
            Object value = getParameterValue(view);
            try {
                MethodHandle method = RemoteViews.getMethod(view, this.mMethodName, param, true);
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
                        root.findViewTreeById(this.mViewId).replaceView(((ViewStub.ViewReplaceRunnable) endAction).view);
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
            if (this.mMethodName.equals("smoothScrollBy")) {
                return 1;
            }
            return 0;
        }

        @Override // android.widget.RemoteViews.Action
        public final String getUniqueKey() {
            return super.getUniqueKey() + this.mMethodName + this.mType;
        }

        @Override // android.widget.RemoteViews.Action
        public final boolean prefersAsyncApply() {
            return this.mType == 11 || this.mType == 16;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            switch (this.mType) {
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
        Object mValue;

        ReflectionAction(int viewId, String methodName, int type, Object value) {
            super(viewId, methodName, type);
            this.mValue = value;
        }

        ReflectionAction(Parcel in) {
            super(in);
            switch (this.mType) {
                case 1:
                    this.mValue = Boolean.valueOf(in.readBoolean());
                    break;
                case 2:
                    this.mValue = Byte.valueOf(in.readByte());
                    break;
                case 3:
                    this.mValue = Short.valueOf((short) in.readInt());
                    break;
                case 4:
                    this.mValue = Integer.valueOf(in.readInt());
                    break;
                case 5:
                    this.mValue = Long.valueOf(in.readLong());
                    break;
                case 6:
                    this.mValue = Float.valueOf(in.readFloat());
                    break;
                case 7:
                    this.mValue = Double.valueOf(in.readDouble());
                    break;
                case 8:
                    this.mValue = Character.valueOf((char) in.readInt());
                    break;
                case 9:
                    this.mValue = in.readString8();
                    break;
                case 10:
                    this.mValue = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
                    break;
                case 11:
                    this.mValue = in.readTypedObject(Uri.CREATOR);
                    break;
                case 12:
                    this.mValue = in.readTypedObject(Bitmap.CREATOR);
                    break;
                case 13:
                    if (in.hasReadWriteHelper()) {
                        this.mValue = in.readBundle();
                        break;
                    } else {
                        in.setReadWriteHelper(RemoteViews.ALTERNATIVE_DEFAULT);
                        this.mValue = in.readBundle();
                        in.setReadWriteHelper(null);
                        break;
                    }
                case 14:
                    this.mValue = in.readTypedObject(Intent.CREATOR);
                    break;
                case 15:
                    this.mValue = in.readTypedObject(ColorStateList.CREATOR);
                    break;
                case 16:
                    this.mValue = in.readTypedObject(Icon.CREATOR);
                    break;
                case 17:
                    this.mValue = BlendMode.fromValue(in.readInt());
                    break;
                case 30:
                    this.mValue = in.readTypedObject(SemBlurInfo.CREATOR);
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            switch (this.mType) {
                case 1:
                    out.writeBoolean(((Boolean) this.mValue).booleanValue());
                    break;
                case 2:
                    out.writeByte(((Byte) this.mValue).byteValue());
                    break;
                case 3:
                    out.writeInt(((Short) this.mValue).shortValue());
                    break;
                case 4:
                    out.writeInt(((Integer) this.mValue).intValue());
                    break;
                case 5:
                    out.writeLong(((Long) this.mValue).longValue());
                    break;
                case 6:
                    out.writeFloat(((Float) this.mValue).floatValue());
                    break;
                case 7:
                    out.writeDouble(((Double) this.mValue).doubleValue());
                    break;
                case 8:
                    out.writeInt(((Character) this.mValue).charValue());
                    break;
                case 9:
                    out.writeString8((String) this.mValue);
                    break;
                case 10:
                    TextUtils.writeToParcel((CharSequence) this.mValue, out, flags);
                    break;
                case 11:
                case 12:
                case 14:
                case 15:
                case 16:
                case 30:
                    out.writeTypedObject((Parcelable) this.mValue, flags);
                    break;
                case 13:
                    out.writeBundle((Bundle) this.mValue);
                    break;
                case 17:
                    out.writeInt(BlendMode.toValue((BlendMode) this.mValue));
                    break;
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            return this.mValue;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 2;
        }
    }

    private static final class ResourceReflectionAction extends BaseReflectionAction {
        static final int COLOR_RESOURCE = 2;
        static final int DIMEN_RESOURCE = 1;
        static final int INTEGER_RESOURCE = 4;
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

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
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
                        switch (this.mType) {
                            case 4:
                                return Integer.valueOf(this.mResId != 0 ? resources.getDimensionPixelSize(this.mResId) : 0);
                            case 5:
                            default:
                                throw new ActionException("dimen resources must be used as INT or FLOAT, not " + this.mType);
                            case 6:
                                return Float.valueOf(this.mResId == 0 ? 0.0f : resources.getDimension(this.mResId));
                        }
                    case 2:
                        switch (this.mType) {
                            case 4:
                                return Integer.valueOf(this.mResId != 0 ? view.getContext().getColor(this.mResId) : 0);
                            case 15:
                                return this.mResId != 0 ? view.getContext().getColorStateList(this.mResId) : null;
                            default:
                                throw new ActionException("color resources must be used as INT or COLOR_STATE_LIST, not " + this.mType);
                        }
                    case 3:
                        switch (this.mType) {
                            case 9:
                                return this.mResId != 0 ? resources.getString(this.mResId) : null;
                            case 10:
                                return this.mResId != 0 ? resources.getText(this.mResId) : null;
                            default:
                                throw new ActionException("string resources must be used as STRING or CHAR_SEQUENCE, not " + this.mType);
                        }
                    case 4:
                        switch (this.mType) {
                            case 4:
                                return Integer.valueOf(this.mResId != 0 ? resources.getInteger(this.mResId) : 0);
                            default:
                                throw new ActionException("integer resources must be used as INT, not " + this.mType);
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

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
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
                            switch (this.mType) {
                                case 4:
                                    return Integer.valueOf(typedArray.getDimensionPixelSize(0, 0));
                                case 5:
                                default:
                                    throw new ActionException("dimen attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as INT or FLOAT, not " + this.mType);
                                case 6:
                                    return Float.valueOf(typedArray.getDimension(0, 0.0f));
                            }
                        case 2:
                            switch (this.mType) {
                                case 4:
                                    return Integer.valueOf(typedArray.getColor(0, 0));
                                case 15:
                                    return typedArray.getColorStateList(0);
                                default:
                                    throw new ActionException("color attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as INT or COLOR_STATE_LIST, not " + this.mType);
                            }
                        case 3:
                            switch (this.mType) {
                                case 9:
                                    return typedArray.getString(0);
                                case 10:
                                    return typedArray.getText(0);
                                default:
                                    throw new ActionException("string attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as STRING or CHAR_SEQUENCE, not " + this.mType);
                            }
                        default:
                            throw new ActionException("Unknown resource type: " + this.mResourceType);
                    }
                } catch (ActionException ex) {
                    throw ex;
                } catch (Throwable t) {
                    throw new ActionException(t);
                }
            } finally {
                typedArray.recycle();
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

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
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
                switch (this.mType) {
                    case 4:
                        return Integer.valueOf(TypedValue.complexToDimensionPixelSize(data, dm));
                    case 5:
                    default:
                        throw new ActionException("parameter type must be INT or FLOAT, not " + this.mType);
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
            switch (this.mType) {
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
                    throw new ActionException("Unexpected night mode action type: " + this.mType);
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            switch (this.mType) {
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
            if (this.mType == 16) {
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
        Object tag = view.getTag(R.id.remote_views_stable_id);
        return tag != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getStableId(View view) {
        Integer id = (Integer) view.getTag(R.id.remote_views_stable_id);
        if (id == null) {
            return -1;
        }
        return id.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setStableId(View view, int stableId) {
        view.setTagInternal(R.id.remote_views_stable_id, Integer.valueOf(stableId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getNextRecyclableChild(ViewGroup vg) {
        Integer tag = (Integer) vg.getTag(R.id.remote_views_next_child);
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
            vg.setTagInternal(R.id.remote_views_next_child, -1);
        } else {
            vg.setTagInternal(R.id.remote_views_next_child, Integer.valueOf(nextChild));
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

        @Override // android.widget.RemoteViews.Action
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
            this.mViewId = viewId;
            this.mNestedViews = nestedViews;
            this.mIndex = index;
            this.mStableId = stableId;
            nestedViews.configureAsChild(RemoteViews.this.getHierarchyRootData());
        }

        ViewGroupActionAdd(Parcel parcel, ApplicationInfo info, int depth) {
            super();
            this.mViewId = parcel.readInt();
            this.mIndex = parcel.readInt();
            this.mStableId = parcel.readInt();
            this.mNestedViews = new RemoteViews(parcel, RemoteViews.this.getHierarchyRootData(), info, depth);
            this.mNestedViews.addFlags(RemoteViews.this.mApplyFlags);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
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
            Context context = root.getContext();
            ViewGroup target = (ViewGroup) root.findViewById(this.mViewId);
            if (target == null || this.mNestedViews == null) {
                return;
            }
            int nextChild = RemoteViews.getNextRecyclableChild(target);
            if (RemoteViews.this.mAllowOtherRootParent) {
                this.mNestedViews.hidden_semSetAllowOtherRootParent(true, RemoteViews.this.mAppWidgetId);
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
            if (this.mStableId != -1) {
                RemoteViews.setStableId(nestedView, this.mStableId);
            }
            target.addView(nestedView, this.mIndex >= 0 ? this.mIndex : nextChild);
            if (nextChild >= 0) {
                RemoteViews.setNextRecyclableChild(target, nextChild + 1, target.getChildCount());
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            root.createTree();
            ViewTree target = root.findViewTreeById(this.mViewId);
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
                                if (recycledViewIndex > nextChild) {
                                    targetVg.removeViews(nextChild, recycledViewIndex - nextChild);
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
            final int insertIndex = this.mIndex >= 0 ? this.mIndex : nextChild;
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
        public void visitUris(Consumer<Uri> visitor) {
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
            this.mViewId = viewId;
            this.mViewIdToKeep = viewIdToKeep;
        }

        ViewGroupActionRemove(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mViewIdToKeep = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mViewIdToKeep);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            ViewGroup target = (ViewGroup) root.findViewById(this.mViewId);
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
            ViewTree target = root.findViewTreeById(this.mViewId);
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
            this.mViewId = viewId;
        }

        RemoveFromParentAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
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
            final ViewTree target = root.findViewTreeById(this.mViewId);
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
        int mD1;
        int mD2;
        int mD3;
        int mD4;
        boolean mDrawablesLoaded;
        Icon mI1;
        Icon mI2;
        Icon mI3;
        Icon mI4;
        Drawable mId1;
        Drawable mId2;
        Drawable mId3;
        Drawable mId4;
        boolean mIsRelative;
        boolean mUseIcons;

        public TextViewDrawableAction(int viewId, boolean isRelative, int d1, int d2, int d3, int d4) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = viewId;
            this.mIsRelative = isRelative;
            this.mUseIcons = false;
            this.mD1 = d1;
            this.mD2 = d2;
            this.mD3 = d3;
            this.mD4 = d4;
        }

        public TextViewDrawableAction(int viewId, boolean isRelative, Icon i1, Icon i2, Icon i3, Icon i4) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = viewId;
            this.mIsRelative = isRelative;
            this.mUseIcons = true;
            this.mI1 = i1;
            this.mI2 = i2;
            this.mI3 = i3;
            this.mI4 = i4;
        }

        public TextViewDrawableAction(Parcel parcel) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = parcel.readInt();
            this.mIsRelative = parcel.readInt() != 0;
            this.mUseIcons = parcel.readInt() != 0;
            if (this.mUseIcons) {
                this.mI1 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                this.mI2 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                this.mI3 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                this.mI4 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                return;
            }
            this.mD1 = parcel.readInt();
            this.mD2 = parcel.readInt();
            this.mD3 = parcel.readInt();
            this.mD4 = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mIsRelative ? 1 : 0);
            parcel.writeInt(this.mUseIcons ? 1 : 0);
            if (this.mUseIcons) {
                parcel.writeTypedObject(this.mI1, 0);
                parcel.writeTypedObject(this.mI2, 0);
                parcel.writeTypedObject(this.mI3, 0);
                parcel.writeTypedObject(this.mI4, 0);
                return;
            }
            parcel.writeInt(this.mD1);
            parcel.writeInt(this.mD2);
            parcel.writeInt(this.mD3);
            parcel.writeInt(this.mD4);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            if (this.mDrawablesLoaded) {
                if (this.mIsRelative) {
                    target.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mId1, this.mId2, this.mId3, this.mId4);
                    return;
                } else {
                    target.setCompoundDrawablesWithIntrinsicBounds(this.mId1, this.mId2, this.mId3, this.mId4);
                    return;
                }
            }
            if (this.mUseIcons) {
                Context ctx = target.getContext();
                Drawable id1 = this.mI1 == null ? null : this.mI1.loadDrawable(ctx);
                Drawable id2 = this.mI2 == null ? null : this.mI2.loadDrawable(ctx);
                Drawable id3 = this.mI3 == null ? null : this.mI3.loadDrawable(ctx);
                Drawable id4 = this.mI4 != null ? this.mI4.loadDrawable(ctx) : null;
                if (this.mIsRelative) {
                    target.setCompoundDrawablesRelativeWithIntrinsicBounds(id1, id2, id3, id4);
                    return;
                } else {
                    target.setCompoundDrawablesWithIntrinsicBounds(id1, id2, id3, id4);
                    return;
                }
            }
            if (this.mIsRelative) {
                target.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mD1, this.mD2, this.mD3, this.mD4);
            } else {
                target.setCompoundDrawablesWithIntrinsicBounds(this.mD1, this.mD2, this.mD3, this.mD4);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            TextViewDrawableAction copy;
            TextView target = (TextView) root.findViewById(this.mViewId);
            if (target == null) {
                return RemoteViews.ACTION_NOOP;
            }
            if (this.mUseIcons) {
                copy = new TextViewDrawableAction(this.mViewId, this.mIsRelative, this.mI1, this.mI2, this.mI3, this.mI4);
            } else {
                copy = new TextViewDrawableAction(this.mViewId, this.mIsRelative, this.mD1, this.mD2, this.mD3, this.mD4);
            }
            copy.mDrawablesLoaded = true;
            Context ctx = target.getContext();
            if (this.mUseIcons) {
                copy.mId1 = this.mI1 == null ? null : this.mI1.loadDrawable(ctx);
                copy.mId2 = this.mI2 == null ? null : this.mI2.loadDrawable(ctx);
                copy.mId3 = this.mI3 == null ? null : this.mI3.loadDrawable(ctx);
                copy.mId4 = this.mI4 != null ? this.mI4.loadDrawable(ctx) : null;
            } else {
                copy.mId1 = this.mD1 == 0 ? null : ctx.getDrawable(this.mD1);
                copy.mId2 = this.mD2 == 0 ? null : ctx.getDrawable(this.mD2);
                copy.mId3 = this.mD3 == 0 ? null : ctx.getDrawable(this.mD3);
                copy.mId4 = this.mD4 != 0 ? ctx.getDrawable(this.mD4) : null;
            }
            return copy;
        }

        @Override // android.widget.RemoteViews.Action
        public boolean prefersAsyncApply() {
            return this.mUseIcons;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 11;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> visitor) {
            if (this.mUseIcons) {
                RemoteViews.visitIconUri(this.mI1, visitor);
                RemoteViews.visitIconUri(this.mI2, visitor);
                RemoteViews.visitIconUri(this.mI3, visitor);
                RemoteViews.visitIconUri(this.mI4, visitor);
            }
        }
    }

    private static class TextViewSizeAction extends Action {
        float mMaxFontScale;
        float mSize;
        int mSizeResId;
        int mUnits;

        TextViewSizeAction(int viewId, int units, float size) {
            super();
            this.mViewId = viewId;
            this.mUnits = units;
            this.mSize = size;
            this.mSizeResId = 0;
            this.mMaxFontScale = 0.0f;
        }

        TextViewSizeAction(int viewId, int units, float size, float maxFontScale) {
            super();
            this.mViewId = viewId;
            this.mUnits = units;
            this.mSize = size;
            this.mSizeResId = 0;
            this.mMaxFontScale = maxFontScale;
        }

        TextViewSizeAction(int viewId, int units, int sizeResId, float maxFontScale) {
            super();
            this.mViewId = viewId;
            this.mUnits = units;
            this.mSize = 0.0f;
            this.mSizeResId = sizeResId;
            this.mMaxFontScale = maxFontScale;
        }

        TextViewSizeAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mUnits = parcel.readInt();
            this.mSize = parcel.readFloat();
            this.mSizeResId = parcel.readInt();
            this.mMaxFontScale = parcel.readFloat();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mUnits);
            dest.writeFloat(this.mSize);
            dest.writeInt(this.mSizeResId);
            dest.writeFloat(this.mMaxFontScale);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            try {
                Resources res = target.getContext().getResources();
                if (this.mMaxFontScale != 0.0f && this.mUnits == 2 && res.getConfiguration().fontScale > this.mMaxFontScale) {
                    if (this.mSizeResId != 0) {
                        float size = res.getFloat(this.mSizeResId);
                        target.setTextSize(1, this.mMaxFontScale * size);
                    } else {
                        target.setTextSize(1, this.mSize * this.mMaxFontScale);
                    }
                } else if (this.mSizeResId != 0) {
                    target.setTextSize(this.mUnits, res.getFloat(this.mSizeResId));
                } else {
                    target.setTextSize(this.mUnits, this.mSize);
                }
            } catch (Exception ex) {
                Log.e(RemoteViews.LOG_TAG, "ex=" + ex);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 13;
        }
    }

    private static class SemSetTextViewTextResourceAction extends Action {
        int mResid;
        Bundle mSpans;

        SemSetTextViewTextResourceAction(int viewId, int resid, Bundle spans) {
            super();
            this.mViewId = viewId;
            this.mResid = resid;
            this.mSpans = spans;
        }

        SemSetTextViewTextResourceAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mResid = parcel.readInt();
            this.mSpans = parcel.readBundle();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mResid);
            dest.writeBundle(this.mSpans);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            String text = target.getContext().getResources().getString(this.mResid);
            RemoteViews.setTextWithSpannableString(target, text, this.mSpans);
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree root, ViewGroup rootParent, ActionApplyParams params) {
            TextView target = (TextView) root.findViewById(this.mViewId);
            return target == null ? RemoteViews.ACTION_NOOP : this;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 110;
        }
    }

    static void setTextWithSpannableString(TextView textView, String text, Bundle style) {
        int typeface;
        if (textView == null) {
            return;
        }
        if (style != null && text != null) {
            try {
                SpannableString content = new SpannableString(text);
                int length = content.length();
                for (String key : PARCELABLE_SPAN_KEYS) {
                    ParcelableSpan span = null;
                    if ("TypefaceSpan".equals(key)) {
                        String family = style.getString(key, null);
                        if (family != null) {
                            span = new TypefaceSpan(family);
                        }
                    } else if ("TextAppearanceSpan".equals(key)) {
                        int textAppearance = style.getInt(key, 0);
                        if (textAppearance != 0) {
                            span = new TextAppearanceSpan(textView.getContext(), textAppearance);
                        }
                    } else if ("UnderlineSpan".equals(key)) {
                        if (style.getBoolean(key, false)) {
                            span = new UnderlineSpan();
                        }
                    } else if ("StrikethroughSpan".equals(key)) {
                        if (style.getBoolean(key, false)) {
                            span = new StrikethroughSpan();
                        }
                    } else if ("StyleSpan".equals(key) && (typeface = style.getInt(key, -1)) != -1) {
                        span = new StyleSpan(typeface);
                    }
                    if (span != null) {
                        content.setSpan(span, 0, length, 17);
                    }
                }
                textView.lambda$setTextAsync$0(content);
                return;
            } catch (Exception ex) {
                Log.e(LOG_TAG, "ex=" + ex);
                return;
            }
        }
        textView.lambda$setTextAsync$0(text);
    }

    private static class ViewPaddingAction extends Action {
        int mBottom;
        int mLeft;
        int mRight;
        int mTop;

        public ViewPaddingAction(int viewId, int left, int top, int right, int bottom) {
            super();
            this.mViewId = viewId;
            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
        }

        public ViewPaddingAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mLeft = parcel.readInt();
            this.mTop = parcel.readInt();
            this.mRight = parcel.readInt();
            this.mBottom = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mLeft);
            dest.writeInt(this.mTop);
            dest.writeInt(this.mRight);
            dest.writeInt(this.mBottom);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            target.setPadding(this.mLeft, this.mTop, this.mRight, this.mBottom);
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
            this.mViewId = viewId;
            this.mProperty = property;
            this.mValueType = 2;
            this.mValue = TypedValue.createComplexDimension(value, units);
            this.mAnimatorId = -1;
            this.mIsAnimationEnd = false;
        }

        LayoutParamAction(int viewId, int property, int value, int valueType) {
            super();
            this.mViewId = viewId;
            this.mProperty = property;
            this.mValueType = valueType;
            this.mValue = value;
            this.mAnimatorId = -1;
            this.mIsAnimationEnd = false;
        }

        LayoutParamAction(int viewId, int property, int animatorId) {
            super();
            this.mViewId = viewId;
            this.mProperty = property;
            this.mValueType = 101;
            this.mValue = 0;
            this.mAnimatorId = animatorId;
            this.mIsAnimationEnd = false;
        }

        public LayoutParamAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mProperty = parcel.readInt();
            this.mValueType = parcel.readInt();
            this.mValue = parcel.readInt();
            this.mAnimatorId = parcel.readInt();
            this.mIsAnimationEnd = parcel.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mProperty);
            dest.writeInt(this.mValueType);
            dest.writeInt(this.mValue);
            dest.writeInt(this.mAnimatorId);
            dest.writeBoolean(this.mIsAnimationEnd);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            ViewGroup.LayoutParams layoutParams;
            View target = root.findViewById(this.mViewId);
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
        final Parcelable[] mRemoteInputs;

        public SetRemoteInputsAction(int viewId, RemoteInput[] remoteInputs) {
            super();
            this.mViewId = viewId;
            this.mRemoteInputs = remoteInputs;
        }

        public SetRemoteInputsAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mRemoteInputs = (Parcelable[]) parcel.createTypedArray(RemoteInput.CREATOR);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeTypedArray(this.mRemoteInputs, flags);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            target.setTagInternal(R.id.remote_input_tag, this.mRemoteInputs);
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 18;
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

        @Override // android.widget.RemoteViews.Action
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
            this.mViewId = viewId;
            this.mChecked = checked;
        }

        SetCompoundButtonCheckedAction(Parcel in) {
            super();
            this.mViewId = in.readInt();
            this.mChecked = in.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeBoolean(this.mChecked);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            if (!(target instanceof CompoundButton)) {
                Log.w(RemoteViews.LOG_TAG, "Cannot set checked to view " + this.mViewId + " because it is not a CompoundButton");
                return;
            }
            CompoundButton button = (CompoundButton) target;
            Object tag = button.getTag(R.id.remote_checked_change_listener_tag);
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
            this.mViewId = viewId;
            this.mCheckedId = checkedId;
        }

        SetRadioGroupCheckedAction(Parcel in) {
            super();
            this.mViewId = in.readInt();
            this.mCheckedId = in.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mCheckedId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            View target = root.findViewById(this.mViewId);
            if (target == null) {
                return;
            }
            if (!(target instanceof RadioGroup)) {
                Log.w(RemoteViews.LOG_TAG, "Cannot check " + this.mViewId + " because it's not a RadioGroup");
                return;
            }
            RadioGroup group = (RadioGroup) target;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                if ((child instanceof CompoundButton) && (child.getTag(R.id.remote_checked_change_listener_tag) instanceof CompoundButton.OnCheckedChangeListener)) {
                    ((CompoundButton) child).setOnCheckedChangeListener(null);
                }
            }
            int i2 = this.mCheckedId;
            group.check(i2);
            for (int i3 = 0; i3 < group.getChildCount(); i3++) {
                View child2 = group.getChildAt(i3);
                if (child2 instanceof CompoundButton) {
                    Object tag = child2.getTag(R.id.remote_checked_change_listener_tag);
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
            this.mViewId = viewId;
            this.mValueType = valueType;
            this.mValue = value;
        }

        SetViewOutlinePreferredRadiusAction(int viewId, float radius, int units) {
            super();
            this.mViewId = viewId;
            this.mValueType = 2;
            this.mValue = TypedValue.createComplexDimension(radius, units);
        }

        SetViewOutlinePreferredRadiusAction(Parcel in) {
            super();
            this.mViewId = in.readInt();
            this.mValueType = in.readInt();
            this.mValue = in.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            dest.writeInt(this.mValueType);
            dest.writeInt(this.mValue);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, ActionApplyParams params) throws ActionException {
            float radius;
            View target = root.findViewById(this.mViewId);
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

    /* JADX INFO: Access modifiers changed from: private */
    class SetDrawInstructionAction extends Action {
        private final DrawInstructions mInstructions;

        SetDrawInstructionAction(DrawInstructions instructions) {
            super();
            this.mInstructions = instructions;
        }

        SetDrawInstructionAction(Parcel in) {
            super();
            if (Flags.drawDataParcel()) {
                this.mInstructions = DrawInstructions.readFromParcel(in);
            } else {
                this.mInstructions = null;
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            if (Flags.drawDataParcel()) {
                DrawInstructions.writeToParcel(this.mInstructions, dest, flags);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) throws ActionException {
            if (Flags.drawDataParcel() && this.mInstructions != null && (root instanceof RemoteComposePlayer)) {
                final RemoteComposePlayer player = (RemoteComposePlayer) root;
                List<byte[]> bytes = this.mInstructions.mInstructions;
                if (bytes.isEmpty()) {
                    return;
                }
                try {
                    ByteArrayInputStream is = new ByteArrayInputStream(bytes.get(0));
                    try {
                        player.setDocument(new RemoteComposeDocument(is));
                        player.addClickListener(new RemoteComposePlayer.ClickCallbacks() { // from class: android.widget.RemoteViews$SetDrawInstructionAction$$ExternalSyntheticLambda0
                            @Override // com.android.internal.widget.remotecompose.player.RemoteComposePlayer.ClickCallbacks
                            public final void click(int i, String str) {
                                RemoteViews.SetDrawInstructionAction.this.lambda$apply$1(player, params, i, str);
                            }
                        });
                        is.close();
                    } finally {
                    }
                } catch (IOException e) {
                    Log.e(RemoteViews.LOG_TAG, "Failed to render draw instructions", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$1(final RemoteComposePlayer player, final ActionApplyParams params, final int viewId, String metadata) {
            RemoteViews.this.mActions.forEach(new Consumer() { // from class: android.widget.RemoteViews$SetDrawInstructionAction$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteViews.SetDrawInstructionAction.lambda$apply$0(viewId, player, params, (RemoteViews.Action) obj);
                }
            });
        }

        static /* synthetic */ void lambda$apply$0(int viewId, RemoteComposePlayer player, ActionApplyParams params, Action action) {
            if (viewId == action.mViewId && (action instanceof SetOnClickResponse)) {
                SetOnClickResponse setOnClickResponse = (SetOnClickResponse) action;
                setOnClickResponse.mResponse.handleViewInteraction(player, params.handler);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 35;
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
        this.mCollectionCache = new RemoteCollectionCache();
        this.mApplicationInfoCache = new ApplicationInfoCache();
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
        this.mApplicationInfoCache.put(application);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasMultipleLayouts() {
        return hasLandscapeAndPortraitLayouts() || hasSizedRemoteViews();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasLandscapeAndPortraitLayouts() {
        return (this.mLandscape == null || this.mPortrait == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasSizedRemoteViews() {
        return this.mSizedRemoteViews != null;
    }

    private SizeF getIdealSize() {
        return this.mIdealSize;
    }

    private void setIdealSize(SizeF size) {
        this.mIdealSize = size;
    }

    private RemoteViews findSmallestRemoteView() {
        return this.mSizedRemoteViews.get(this.mSizedRemoteViews.size() - 1);
    }

    public RemoteViews(RemoteViews landscape, RemoteViews portrait) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mCollectionCache = new RemoteCollectionCache();
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
        this.mClassCookies = portrait.mClassCookies != null ? portrait.mClassCookies : landscape.mClassCookies;
        configureDescendantsAsChildren();
    }

    public RemoteViews(Map<SizeF, RemoteViews> remoteViews) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mCollectionCache = new RemoteCollectionCache();
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
            this.mClassCookies = initializeSizedRemoteViews(remoteViews.entrySet().stream().map(new Function() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda0
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
        this.mCollectionCache = new RemoteCollectionCache();
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
        this.mCollectionCache = new RemoteCollectionCache();
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
            this.mCollectionCache = new RemoteCollectionCache(src.mCollectionCache);
            this.mApplicationInfoCache = src.mApplicationInfoCache;
        } else {
            this.mBitmapCache = hierarchyRoot.mBitmapCache;
            this.mCollectionCache = hierarchyRoot.mCollectionCache;
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
        this.mHasDrawInstructions = src.mHasDrawInstructions;
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
    }

    public RemoteViews(Parcel parcel) {
        this(parcel, null, null, 0);
    }

    public RemoteViews(DrawInstructions drawInstructions) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mCollectionCache = new RemoteCollectionCache();
        this.mApplicationInfoCache = new ApplicationInfoCache();
        this.mIsRoot = true;
        this.mLandscape = null;
        this.mPortrait = null;
        this.mSizedRemoteViews = null;
        this.mIdealSize = null;
        this.mApplyFlags = 0;
        this.mViewId = -1;
        this.mProviderInstanceId = -1L;
        Objects.requireNonNull(drawInstructions);
        this.mHasDrawInstructions = true;
        addAction(new SetDrawInstructionAction(drawInstructions));
    }

    private RemoteViews(Parcel parcel, HierarchyRootData rootData, ApplicationInfo info, int depth) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mCollectionCache = new RemoteCollectionCache();
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
            this.mCollectionCache = new RemoteCollectionCache(parcel);
        } else {
            configureAsChild(rootData);
        }
        if (mode == 0) {
            this.mApplication = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
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
            this.mPortrait = new RemoteViews(parcel, getHierarchyRootData(), this.mLandscape.mApplication, depth2);
            this.mApplication = this.mPortrait.mApplication;
            this.mLayoutId = this.mPortrait.mLayoutId;
            this.mViewId = this.mPortrait.mViewId;
            this.mLightBackgroundLayoutId = this.mPortrait.mLightBackgroundLayoutId;
        }
        this.mApplyFlags = parcel.readInt();
        this.mProviderInstanceId = parcel.readLong();
        this.mHasDrawInstructions = parcel.readBoolean();
        this.mAllowOtherRootParent = parcel.readBoolean();
        if (this.mAllowOtherRootParent) {
            this.mAppWidgetId = parcel.readInt();
        }
        if (this.mIsRoot) {
            configureDescendantsAsChildren();
        }
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
            case 18:
                return new SetRemoteInputsAction(parcel);
            case 19:
                return new LayoutParamAction(parcel);
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
            case 34:
                return new SetOnStylusHandwritingResponse(parcel);
            case 35:
                return new SetDrawInstructionAction(parcel);
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
                return new SetTextViewShadowAction(parcel);
            case 109:
                return new SetStringTagAction(parcel);
            case 110:
                return new SemSetTextViewTextResourceAction(parcel);
            default:
                throw new ActionException("Tag " + tag + " not found");
        }
    }

    @Override // 
    @Deprecated
    /* renamed from: clone */
    public RemoteViews mo447clone() {
        Preconditions.checkState(this.mIsRoot, "RemoteView has been attached to another RemoteView. May only clone the root of a RemoteView hierarchy.");
        return new RemoteViews(this);
    }

    public String getPackage() {
        if (this.mApplication != null) {
            return this.mApplication.packageName;
        }
        return null;
    }

    public int getLayoutId() {
        return (!hasFlags(4) || this.mLightBackgroundLayoutId == 0) ? this.mLayoutId : this.mLightBackgroundLayoutId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configureAsChild(HierarchyRootData rootData) {
        this.mIsRoot = false;
        this.mBitmapCache = rootData.mBitmapCache;
        this.mCollectionCache = rootData.mRemoteCollectionCache;
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
            if (this.mActions != null) {
                Iterator<Action> it = this.mActions.iterator();
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
            this.mApplicationInfoCache = new ApplicationInfoCache();
            this.mApplication = this.mApplicationInfoCache.getOrPut(this.mApplication);
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

    private void hidden_semSetTextViewTextResource(int viewId, int resid, Bundle spans) {
        addAction(new SemSetTextViewTextResourceAction(viewId, resid, spans));
    }

    public void setTextViewTextSize(int viewId, int units, float size) {
        addAction(new TextViewSizeAction(viewId, units, size));
    }

    private void hidden_semSetTextViewTextSize(int viewId, float size, float maxFontScale) {
        addAction(new TextViewSizeAction(viewId, 2, size, maxFontScale));
    }

    private void hidden_semSetTextViewTextSizeResource(int viewId, int units, int sizeResId, float maxFontScale) {
        addAction(new TextViewSizeAction(viewId, units, sizeResId, maxFontScale));
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
        if (hasDrawInstructions()) {
            getPendingIntentTemplate().set(viewId, pendingIntentTemplate);
            tryAddRemoteResponse(viewId);
        } else {
            addAction(new SetPendingIntentTemplate(viewId, pendingIntentTemplate));
        }
    }

    public void setOnClickFillInIntent(int viewId, Intent fillInIntent) {
        if (hasDrawInstructions()) {
            getFillInIntent().set(viewId, fillInIntent);
            tryAddRemoteResponse(viewId);
        } else {
            setOnClickResponse(viewId, RemoteResponse.fromFillInIntent(fillInIntent));
        }
    }

    public void setOnCheckedChangeResponse(int viewId, RemoteResponse response) {
        addAction(new SetOnCheckedChangeResponse(viewId, response.setInteractionType(1)));
    }

    public void setOnStylusHandwritingPendingIntent(int viewId, PendingIntent pendingIntent) {
        addAction(new SetOnStylusHandwritingResponse(viewId, pendingIntent));
    }

    public void setDrawableTint(int viewId, boolean targetBackground, int colorFilter, PorterDuff.Mode mode) {
        addAction(new SetDrawableTint(viewId, targetBackground, colorFilter, mode));
    }

    private void hidden_semSetTextViewShadow(int viewId, float radius, float dx, float dy, int color) {
        addAction(new SetTextViewShadowAction(viewId, radius, dx, dy, color));
    }

    private void hidden_semSetStringTag(int viewId, int key, String value) {
        addAction(new SetStringTagAction(viewId, key, value));
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

    @Deprecated
    public void setRemoteAdapter(int viewId, Intent intent) {
        if (Flags.remoteAdapterConversion()) {
            addAction(new SetRemoteCollectionItemListAdapterAction(viewId, intent));
        } else {
            addAction(new SetRemoteViewsAdapterIntent(viewId, intent));
        }
    }

    @Deprecated
    public void setRemoteAdapter(int viewId, ArrayList<RemoteViews> list, int viewTypeCount) {
        RemoteCollectionItems.Builder b = new RemoteCollectionItems.Builder();
        for (int i = 0; i < list.size(); i++) {
            b.addItem(i, list.get(i));
        }
        setRemoteAdapter(viewId, b.setViewTypeCount(viewTypeCount).build());
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

    private void hidden_semSetIntInteger(int viewId, String methodName, int integerResource) {
        addAction(new ResourceReflectionAction(viewId, methodName, 4, 4, integerResource));
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
    public boolean hasDrawInstructions() {
        return this.mHasDrawInstructions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RemoteViews getRemoteViewsToApply(Context context) {
        if (this.isProductDEV) {
            Log.d(LOG_TAG, "getRemoteViewsToApply() - mIsForcedOrientation=" + this.mIsForcedOrientation + "mIsPortrait=" + this.mIsPortrait + ", mLandscape=" + this.mLandscape + ", mPortrait=" + this.mPortrait);
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
                Log.d(LOG_TAG, "getRemoteViewsToApply apply remoteViews orientation = " + orientation);
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
            Log.w(LOG_TAG, "Could not find a RemoteViews fitting the current size: " + widgetSize);
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
        View v;
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
        if (rv.hasDrawInstructions()) {
            RemoteComposePlayer player = new RemoteComposePlayer(inflationContext);
            player.setDebug((Build.IS_USERDEBUG || Build.IS_ENG) ? 1 : 0);
            v = player;
        } else {
            LayoutInflater inflater = LayoutInflater.from(context).cloneInContext(inflationContext);
            inflater.setFilter(shouldUseStaticFilter() ? INFLATER_FILTER : this);
            if (this.mLayoutInflaterFactory2 != null) {
                inflater.setFactory2(this.mLayoutInflaterFactory2);
            }
            v = inflater.inflate(rv.getLayoutId(), parent, false);
        }
        try {
            if (this.isProductDEV && (parent instanceof AppWidgetHostView)) {
                Log.d(LOG_TAG, "inflateView, package = " + inflationContext.getPackageName() + ", layout = " + ((Object) inflationContext.getResources().getText(rv.getLayoutId())) + ", App Config = " + inflationContext.getResources().getConfiguration());
            }
            if (this.mViewId != -1) {
                v.setId(this.mViewId);
                v.setTagInternal(R.id.remote_views_override_id, Integer.valueOf(this.mViewId));
            }
            v.setTagInternal(16908312, Integer.valueOf(rv.getLayoutId()));
            return v;
        } catch (RuntimeException e) {
            Log.w(LOG_TAG, "inflate error, layoutId = " + rv.getLayoutId());
            int i = 0;
            for (ApkAssets apkAssets : inflationContext.getAssets().getApkAssets()) {
                Log.w(LOG_TAG, NavigationBarInflaterView.SIZE_MOD_START + i + "], " + inflationContext.getPackageName() + " : " + apkAssets);
                i++;
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
                if (this.mListener != null) {
                    this.mListener.onViewInflated(viewTree.mRoot);
                }
                try {
                    if (this.mActions != null) {
                        ActionApplyParams applyParams = this.mApplyParams.m6859clone();
                        if (applyParams.handler == null) {
                            applyParams.handler = RemoteViews.DEFAULT_INTERACTION_HANDLER;
                        }
                        for (Action a : this.mActions) {
                            a.apply(viewTree.mRoot, this.mParent, applyParams);
                        }
                    }
                    if (this.mTopLevel && (this.mResult instanceof ViewGroup)) {
                        RemoteViews.this.finalizeViewRecycling((ViewGroup) this.mResult);
                    }
                } catch (Exception e) {
                    this.mError = e;
                }
            }
            if (this.mListener != null) {
                if (this.mError != null) {
                    this.mListener.onError(this.mError);
                    return;
                } else {
                    this.mListener.onViewApplied(viewTree.mRoot);
                    return;
                }
            }
            if (this.mError != null) {
                if (this.mError instanceof ActionException) {
                    throw ((ActionException) this.mError);
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
        if (v == null || hasDrawInstructions() || (previousLayoutId = (Integer) v.getTag(16908312)) == null) {
            return false;
        }
        Integer overrideIdTag = (Integer) v.getTag(R.id.remote_views_override_id);
        int overrideId = overrideIdTag == null ? -1 : overrideIdTag.intValue();
        return previousLayoutId.intValue() == getLayoutId() && this.mViewId == overrideId;
    }

    private RemoteViews getRemoteViewsToReapply(Context context, View v, SizeF size) {
        RemoteViews rvToApply = getRemoteViewsToApply(context, size);
        if ((hasMultipleLayouts() || rvToApply.mViewId != -1 || v.getTag(R.id.remote_views_override_id) != null) && !rvToApply.canRecycleView(v)) {
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
        ActionApplyParams params2 = params.m6859clone();
        if (params2.handler == null) {
            params2.handler = DEFAULT_INTERACTION_HANDLER;
        }
        if (v instanceof RemoteComposePlayer) {
            RemoteComposePlayer player = (RemoteComposePlayer) v;
            player.setTheme(v.getResources().getConfiguration().isNightModeActive() ? -2 : -3);
        }
        if (this.mActions != null) {
            int count = this.mActions.size();
            for (int i = 0; i < count; i++) {
                this.mActions.get(i).apply(v, parent, params2);
            }
        }
    }

    public boolean prefersAsyncApply() {
        if (this.mActions != null) {
            int count = this.mActions.size();
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
                Log.e(LOG_TAG, "Package name " + this.mApplication.packageName + " not found");
            }
        }
        return context;
    }

    private SparseArray<PendingIntent> getPendingIntentTemplate() {
        if (this.mPendingIntentTemplate == null) {
            this.mPendingIntentTemplate = new SparseArray<>();
        }
        return this.mPendingIntentTemplate;
    }

    private SparseArray<Intent> getFillInIntent() {
        if (this.mFillInIntent == null) {
            this.mFillInIntent = new SparseArray<>();
        }
        return this.mFillInIntent;
    }

    private void tryAddRemoteResponse(int viewId) {
        PendingIntent pendingIntent = getPendingIntentTemplate().get(viewId);
        Intent intent = getFillInIntent().get(viewId);
        if (pendingIntent != null && intent != null) {
            addAction(new SetOnClickResponse(viewId, RemoteResponse.fromPendingIntentTemplateAndFillInIntent(pendingIntent, intent)));
        }
    }

    public class ActionApplyParams {
        public int applyThemeResId;
        public ColorResources colorResources;
        public Executor executor;
        public InteractionHandler handler;

        public ActionApplyParams() {
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public ActionApplyParams m6859clone() {
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
        private static final int LAST_RESOURCE_COLOR_ID = 17170641;
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
            InputStream input = context.getResources().openRawResource(R.raw.remote_views_color_resources);
            try {
                ByteArrayOutputStream rawContent = readFileContent(input);
                byte[] content = rawContent.toByteArray();
                if (input != null) {
                    input.close();
                }
                int valuesOffset = (content.length - 3344) - 4;
                if (valuesOffset < 0) {
                    Log.e(RemoteViews.LOG_TAG, "ARSC file for theme colors is invalid.");
                    return null;
                }
                for (int colorRes = 17170461; colorRes <= 17170641; colorRes++) {
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
                            ColorResources colorResources = new ColorResources(colorsLoader, colorMapping.m5237clone());
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
                Log.e(RemoteViews.LOG_TAG, "Failed to setup the context for theme colors", ex);
                return null;
            }
        }
    }

    public int getSequenceNumber() {
        if (this.mActions == null) {
            return 0;
        }
        return this.mActions.size();
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
        writeToParcel(dest, flags, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeToParcel(Parcel dest, int flags, SparseArray<Intent> intentsToIgnore) {
        boolean prevSquashingAllowed = dest.allowSquashing();
        if (!hasMultipleLayouts()) {
            dest.writeInt(0);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(dest, flags);
                this.mCollectionCache.writeToParcel(dest, flags, intentsToIgnore);
            }
            dest.writeTypedObject(this.mApplication, flags);
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
                this.mCollectionCache.writeToParcel(dest, flags, intentsToIgnore);
            }
            dest.writeInt(this.mSizedRemoteViews.size());
            for (RemoteViews view : this.mSizedRemoteViews) {
                view.writeToParcel(dest, flags);
            }
        } else {
            dest.writeInt(1);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(dest, flags);
                this.mCollectionCache.writeToParcel(dest, flags, intentsToIgnore);
            }
            this.mLandscape.writeToParcel(dest, flags);
            this.mPortrait.writeToParcel(dest, flags);
        }
        dest.writeInt(this.mApplyFlags);
        dest.writeLong(this.mProviderInstanceId);
        dest.writeBoolean(this.mHasDrawInstructions);
        dest.writeBoolean(this.mAllowOtherRootParent);
        if (this.mAllowOtherRootParent) {
            dest.writeInt(this.mAppWidgetId);
        }
        dest.restoreAllowSquashing(prevSquashingAllowed);
    }

    private void writeActionsToParcel(Parcel parcel, int flags) {
        int count;
        synchronized (this.mActionsLock) {
            if (this.mActions != null) {
                count = this.mActions.size();
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
        return this.mApplication == null || (this.mApplication.packageName.equals(info.packageName) && this.mApplication.uid == info.uid);
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
            if (this.mRoot instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) this.mRoot;
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
            if (this.mChildren == null) {
                return null;
            }
            Iterator<ViewTree> it = this.mChildren.iterator();
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
            if (this.mChildren == null) {
                return null;
            }
            Iterator<ViewTree> it = this.mChildren.iterator();
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

        /* JADX INFO: Access modifiers changed from: private */
        public static RemoteResponse fromPendingIntentTemplateAndFillInIntent(PendingIntent pendingIntent, Intent intent) {
            RemoteResponse response = new RemoteResponse();
            response.mPendingIntent = pendingIntent;
            response.mFillIntent = intent;
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
            dest.writeBoolean(this.mFillIntent != null);
            if (this.mFillIntent != null) {
                dest.writeTypedObject(this.mFillIntent, flags);
            }
            dest.writeInt(this.mInteractionType);
            dest.writeIntArray(this.mViewIds == null ? null : this.mViewIds.toArray());
            dest.writeStringList(this.mElementNames);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readFromParcel(Parcel parcel) {
            this.mPendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            this.mFillIntent = parcel.readBoolean() ? (Intent) parcel.readTypedObject(Intent.CREATOR) : null;
            this.mInteractionType = parcel.readInt();
            int[] viewIds = parcel.createIntArray();
            this.mViewIds = viewIds != null ? IntArray.wrap(viewIds) : null;
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
                    Log.e(RemoteViews.LOG_TAG, "Collection item doesn't have AdapterView parent");
                    return;
                } else {
                    if (!(ancestor.getTag() instanceof PendingIntent)) {
                        Log.e(RemoteViews.LOG_TAG, "Attempting setOnClickFillInIntent or setOnCheckedChangeFillInIntent without calling setPendingIntentTemplate on parent.");
                        return;
                    }
                    pi = (PendingIntent) ancestor.getTag();
                }
            } else {
                Log.e(RemoteViews.LOG_TAG, "Response has neither pendingIntent nor fillInIntent");
                return;
            }
            handler.onInteraction(v, pi, this);
        }

        private static AdapterView<?> getAdapterViewAncestor(View view) {
            if (view == null) {
                return null;
            }
            View parent = (View) view.getParent();
            while (parent != null && !(parent instanceof AdapterView) && (!(parent instanceof AppWidgetHostView) || (parent instanceof AppWidgetHostView.AdapterChildHostView))) {
                parent = (View) parent.getParent();
            }
            if (parent instanceof AdapterView) {
                return (AdapterView) parent;
            }
            return null;
        }

        public Pair<Intent, ActivityOptions> getLaunchOptions(View view) {
            Intent intent = this.mFillIntent == null ? new Intent() : new Intent(this.mFillIntent);
            intent.setSourceBounds(RemoteViews.getSourceBounds(view));
            if ((view instanceof CompoundButton) && this.mInteractionType == 1) {
                intent.putExtra(RemoteViews.EXTRA_CHECKED, ((CompoundButton) view).isChecked());
            }
            ActivityOptions opts = null;
            Context context = view.getContext();
            if (context.getResources().getBoolean(R.bool.config_overrideRemoteViewsActivityTransition)) {
                TypedArray windowStyle = context.getTheme().obtainStyledAttributes(R.styleable.Window);
                int windowAnimations = windowStyle.getResourceId(8, 0);
                TypedArray windowAnimationStyle = context.obtainStyledAttributes(windowAnimations, R.styleable.WindowAnimation);
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
                    opts = ((AppWidgetHostView) parent).createSharedElementActivityOptions(this.mViewIds.toArray(), (String[]) this.mElementNames.toArray(new String[this.mElementNames.size()]), intent);
                }
            }
            if (opts == null) {
                opts = ActivityOptions.makeBasic();
                opts.setPendingIntentLaunchFlags(268435456);
            }
            if (view.getDisplay() != null) {
                opts.setLaunchDisplayId(view.getDisplay().getDisplayId());
            } else {
                Log.w(RemoteViews.LOG_TAG, "getLaunchOptions: view.getDisplay() is null!", new Exception());
            }
            opts.setPendingIntentBackgroundActivityStartMode(1);
            opts.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
            return Pair.create(intent, opts);
        }
    }

    public static boolean startPendingIntent(View view, PendingIntent pendingIntent, Pair<Intent, ActivityOptions> options) {
        KeyguardManager keyguardManager;
        boolean z;
        try {
            Context context = view.getContext();
            Log.d(LOG_TAG, "startPendingIntent" + view.getClass().getName());
            if (pendingIntent.isActivity()) {
                boolean onLockscreen = false;
                ViewParent parent = view.getParent();
                while (true) {
                    if (!(parent instanceof View)) {
                        break;
                    }
                    if (parent instanceof AppWidgetHostView) {
                        int hostType = ((AppWidgetHostView) parent).getHostType();
                        if (hostType != 2 && hostType != 4) {
                            z = false;
                            onLockscreen = z;
                        }
                        z = true;
                        onLockscreen = z;
                    } else {
                        parent = ((View) parent).getParent();
                    }
                }
                Log.d(LOG_TAG, "startPendingIntent: onLockscreen = " + onLockscreen);
                if (onLockscreen && (keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)) != null && keyguardManager.isKeyguardLocked()) {
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
            Log.e(LOG_TAG, "Cannot send pending intent: ", e);
            return false;
        } catch (Exception e2) {
            Log.e(LOG_TAG, "Cannot send pending intent due to unknown exception: ", e2);
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
            this.mIds = new long[length];
            in.readLongArray(this.mIds);
            boolean attached = in.readBoolean();
            this.mViews = new RemoteViews[length];
            if (attached) {
                if (hierarchyRootData == null) {
                    throw new IllegalStateException("Cannot unparcel a RemoteCollectionItems that was parceled as attached without providing data for a root RemoteViews");
                }
                this.mHierarchyRootData = hierarchyRootData;
                firstChildIndex = 0;
            } else {
                this.mViews[0] = new RemoteViews(in);
                this.mHierarchyRootData = this.mViews[0].getHierarchyRootData();
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
            if (!attached && this.mViews.length > 0 && !this.mViews[0].mIsRoot) {
                restoreRoot = true;
                this.mViews[0].mIsRoot = true;
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

    public static final class DrawInstructions {
        private static final long VERSION = 1;
        final List<byte[]> mInstructions;

        private DrawInstructions() {
            throw new UnsupportedOperationException("DrawInstructions cannot be instantiate without instructions");
        }

        private DrawInstructions(List<byte[]> instructions) {
            this.mInstructions = new ArrayList(instructions.size());
            for (byte[] instruction : instructions) {
                int len = instruction.length;
                byte[] target = new byte[len];
                System.arraycopy(instruction, 0, target, 0, len);
                this.mInstructions.add(target);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static DrawInstructions readFromParcel(Parcel in) {
            int size = in.readInt();
            if (size == -1) {
                return null;
            }
            List<byte[]> instructions = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                byte[] instruction = in.readBlob();
                instructions.add(instruction);
            }
            return new DrawInstructions(instructions);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void writeToParcel(DrawInstructions drawInstructions, Parcel dest, int flags) {
            if (drawInstructions == null) {
                dest.writeInt(-1);
                return;
            }
            List<byte[]> instructions = drawInstructions.mInstructions;
            dest.writeInt(instructions.size());
            for (byte[] instruction : instructions) {
                dest.writeBlob(instruction);
            }
        }

        public static long getSupportedVersion() {
            return 1L;
        }

        public static final class Builder {
            private final List<byte[]> mInstructions;

            public Builder(List<byte[]> instructions) {
                this.mInstructions = new ArrayList(instructions);
            }

            public DrawInstructions build() {
                return new DrawInstructions(this.mInstructions);
            }
        }
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

        @Override // android.widget.RemoteViews.Action
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
            this.mViewId = id;
            this.pendingIntentTemplate = pendingIntentTemplate;
        }

        public SemSetLongClickPendingIntentTemplate(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.pendingIntentTemplate = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mViewId);
            this.pendingIntentTemplate.writeToParcel(dest, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View root, ViewGroup rootParent, final ActionApplyParams params) {
            View target = root.findViewById(this.mViewId);
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
                                Object tag = vg.getChildAt(i).getTag(R.id.fillInIntent);
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
            Log.e(RemoteViews.LOG_TAG, "Cannot setLongClickPendingIntentTemplate on a view which is notan AdapterView (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
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
            Log.e(RemoteViews.LOG_TAG, "SetOnLongClickDragable - read:" + parcel.toString());
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

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            Log.e(RemoteViews.LOG_TAG, "SetOnLongClickDragable - writeToParcel:" + parcel.toString());
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
                        Log.e(RemoteViews.LOG_TAG, "Drag info: " + SemSetOnLongClickDragable.this.clipData + SemSetOnLongClickDragable.this.dragStartIntent + SemSetOnLongClickDragable.this.isNeedToRemove);
                        v.startDrag(SemSetOnLongClickDragable.this.clipData, shadowBuilder, null, 0);
                        if (SemSetOnLongClickDragable.this.dragStartIntent != null) {
                            try {
                                v.getContext().startIntentSender(SemSetOnLongClickDragable.this.dragStartIntent.getIntentSender(), null, 268435456, 268435456, 0);
                            } catch (IntentSender.SendIntentException e) {
                                Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
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
                                            Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
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
                                            Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e2);
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
        return new HierarchyRootData(this.mBitmapCache, this.mCollectionCache, this.mApplicationInfoCache, this.mClassCookies);
    }

    private static final class HierarchyRootData {
        final ApplicationInfoCache mApplicationInfoCache;
        final BitmapCache mBitmapCache;
        final Map<Class, Object> mClassCookies;
        final RemoteCollectionCache mRemoteCollectionCache;

        HierarchyRootData(BitmapCache bitmapCache, RemoteCollectionCache remoteCollectionCache, ApplicationInfoCache applicationInfoCache, Map<Class, Object> classCookies) {
            this.mBitmapCache = bitmapCache;
            this.mRemoteCollectionCache = remoteCollectionCache;
            this.mApplicationInfoCache = applicationInfoCache;
            this.mClassCookies = classCookies;
        }
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

        @Override // android.widget.RemoteViews.Action
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

    public void semSetOnCheckedChangedPendingIntent(int viewId, PendingIntent pendingIntent) {
        Log.d(LOG_TAG, "semSetOnCheckedChangedPendingIntent() viewId = " + viewId + ", pendingIntent = " + pendingIntent);
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

        @Override // android.widget.RemoteViews.Action
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
                            Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
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

        @Override // android.widget.RemoteViews.Action
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
                                    Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
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
                                    Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e2);
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

    public void setOrientation(boolean isPortrait) {
        this.mIsForcedOrientation = true;
        this.mIsPortrait = isPortrait;
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

    private void hidden_semSetValueAnimation(int viewId, String animationType, String valueType, float valueFrom, float valueTo, int duration, Bundle options) {
        SemRemoteViewsValueAnimation animation = new SemRemoteViewsValueAnimation(viewId, animationType, valueType, valueFrom, valueTo, duration, options);
        addAction(new SemAnimationAction(4, animation));
    }

    private void hidden_semSetValueAnimation(int viewId, String animationType, String valueType, int valueFrom, int valueTo, int duration, Bundle options) {
        SemRemoteViewsValueAnimation animation = new SemRemoteViewsValueAnimation(viewId, animationType, valueType, valueFrom, valueTo, duration, options);
        addAction(new SemAnimationAction(4, animation));
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
            this.animationType = parcel.readInt();
            switch (this.animationType) {
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

        @Override // android.widget.RemoteViews.Action
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
            if (this.animation != null) {
                this.animation.play(root);
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
