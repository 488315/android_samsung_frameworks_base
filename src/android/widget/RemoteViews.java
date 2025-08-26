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
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.flags.Flags;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.om.FabricatedOverlay;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManager;
import android.content.om.OverlayManagerTransaction;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.loader.ResourcesLoader;
import android.content.res.loader.ResourcesProvider;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.os.Trace;
import android.os.UserHandle;
import android.system.Os;
import android.telecom.Logging.Session;
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
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.SizeF;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoStream;
import android.util.proto.ProtoUtils;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RemoteViews;
import android.widget.RemoteViewsProto;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.IRemoteViewsFactory;
import com.android.internal.widget.remotecompose.core.CoreDocument;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
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
    private static final int SEM_MAX_REMOTE_COLLECTION_ITEM_SIZE = 100000;
    private static final int SEM_PERCENT_POLICY_ALL = 0;
    private static final int SEM_PERCENT_POLICY_HEIGHT = 2;
    private static final int SEM_PERCENT_POLICY_WIDTH = 1;
    private static final int SEM_SET_AUTO_SIZE_TEXT_UNIFORM_WITH_CONFIGURATION_TAG = 115;
    private static final int SEM_SET_BLUR_INFO_TAG = 105;
    private static final int SEM_SET_ON_CHECKED_CHANGED_PENDING_INTENT_TAG = 104;
    private static final int SEM_SET_ON_LONG_CLICK_DRAGABLE_TAG = 102;
    private static final int SEM_SET_ON_LONG_CLICK_PENDING_INTENT_TAG = 100;
    private static final int SEM_SET_ON_LONG_CLICK_PENDING_INTENT_TEMPLATE_TAG = 101;
    private static final int SEM_SET_ON_TOUCH_PENDING_INTENT_TAG = 103;
    private static final int SEM_SET_PERCENT_LAYOUT_SIZE_ACTION_TAG = 111;
    private static final int SEM_SET_PERCENT_PADDING_ACTION_TAG = 113;
    private static final int SEM_SET_PERCENT_TEXT_SIZE_ACTION_TAG = 112;
    private static final int SEM_SET_STRING_TAG = 109;
    private static final int SEM_SET_TEXT_APPEARANCE_ACTION_TAG = 114;
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
    private static final LayoutInflater.Filter INFLATER_FILTER = new LayoutInflater.Filter() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda7
        @Override // android.view.LayoutInflater.Filter
        public final boolean onLoadClass(Class cls) {
            return cls.isAnnotationPresent(RemoteViews.RemoteView.class);
        }
    };
    private static final InteractionHandler DEFAULT_INTERACTION_HANDLER = new InteractionHandler() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda8
        @Override // android.widget.RemoteViews.InteractionHandler
        public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
            return RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
        }
    };
    private static final ArrayMap<MethodKey, MethodArgs> sMethods = new ArrayMap<>();
    private static final MethodKey sLookupKey = new MethodKey();
    private static final Action ACTION_NOOP = new RuntimeAction() { // from class: android.widget.RemoteViews.1
        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
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
        public RemoteViews[] newArray(int i) {
            return new RemoteViews[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplyFlags {
    }

    public interface InteractionHandler {
        boolean onInteraction(View view, PendingIntent pendingIntent, RemoteResponse remoteResponse);

        default void onScroll(AbsListView absListView) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MarginType {
    }

    public interface OnViewAppliedListener {
        void onError(Exception exc);

        void onViewApplied(View view);

        default void onViewInflated(View view) {
        }
    }

    interface PendingResources<T> {
        T create(Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception;
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RemoteView {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface ValueType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void hidden_semSetAllowOtherRootParent(boolean z, int i) {
        this.mAllowOtherRootParent = z;
        this.mAppWidgetId = i;
    }

    public void setRemoteInputs(int i, RemoteInput[] remoteInputArr) {
        this.mActions.add(new SetRemoteInputsAction(i, remoteInputArr));
    }

    public void setLayoutInflaterFactory(LayoutInflater.Factory2 factory2) {
        this.mLayoutInflaterFactory2 = factory2;
    }

    public LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory2;
    }

    public void reduceImageSizes(int i, int i2) {
        ArrayList<Bitmap> arrayList = this.mBitmapCache.mBitmaps;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList.set(i3, Icon.scaleDownIfNecessary(arrayList.get(i3), i, i2));
        }
    }

    public void setIntTag(int i, int i2, int i3) {
        addAction(new SetIntTagAction(i, i2, i3));
    }

    public void setUsageEventTag(int i, int i2) {
        addAction(new SetIntTagAction(i, R.id.remoteViewsMetricsId, i2));
    }

    public void addFlags(int i) {
        this.mApplyFlags |= i;
        int i2 = i & 6;
        if (i2 != 0) {
            if (hasSizedRemoteViews()) {
                Iterator<RemoteViews> it = this.mSizedRemoteViews.iterator();
                while (it.hasNext()) {
                    it.next().addFlags(i2);
                }
            } else if (hasLandscapeAndPortraitLayouts()) {
                this.mLandscape.addFlags(i2);
                this.mPortrait.addFlags(i2);
            }
        }
    }

    public boolean hasFlags(int i) {
        return (this.mApplyFlags & i) == i;
    }

    static class MethodKey {
        public String methodName;
        public Class paramClass;
        public Class targetClass;

        MethodKey() {
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MethodKey)) {
                return false;
            }
            MethodKey methodKey = (MethodKey) obj;
            return Objects.equals(methodKey.targetClass, this.targetClass) && Objects.equals(methodKey.paramClass, this.paramClass) && Objects.equals(methodKey.methodName, this.methodName);
        }

        public int hashCode() {
            return Objects.hashCode(this.methodName) ^ (Objects.hashCode(this.targetClass) ^ Objects.hashCode(this.paramClass));
        }

        public void set(Class cls, Class cls2, String str) {
            this.targetClass = cls;
            this.paramClass = cls2;
            this.methodName = str;
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
        public ActionException(Exception exc) {
            super(exc);
        }

        public ActionException(String str) {
            super(str);
        }

        public ActionException(Throwable th) {
            super(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Action {
        public static final int MERGE_APPEND = 1;
        public static final int MERGE_IGNORE = 2;
        public static final int MERGE_REPLACE = 0;
        int mViewId;
        int viewId;

        public abstract void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException;

        public boolean canWriteToProto() {
            return false;
        }

        public void clear() {
        }

        public abstract int getActionTag();

        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            return this;
        }

        public int mergeBehavior() {
            return 0;
        }

        public boolean prefersAsyncApply() {
            return false;
        }

        public void setHierarchyRootData(HierarchyRootData hierarchyRootData) {
        }

        public void visitIcons(Consumer<Icon> consumer) {
        }

        public void visitUris(Consumer<Uri> consumer) {
        }

        public abstract void writeToParcel(Parcel parcel, int i);

        private Action() {
        }

        public String getUniqueKey() {
            return getActionTag() + Session.SESSION_SEPARATION_CHAR_CHILD + this.mViewId;
        }

        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            throw new UnsupportedOperationException();
        }
    }

    private static abstract class RuntimeAction extends Action {
        @Override // android.widget.RemoteViews.Action
        public final int getActionTag() {
            return 0;
        }

        private RuntimeAction() {
            super();
        }

        @Override // android.widget.RemoteViews.Action
        public final void writeToParcel(Parcel parcel, int i) {
            throw new UnsupportedOperationException();
        }
    }

    public void mergeRemoteViews(RemoteViews remoteViews) {
        if (remoteViews == null) {
            return;
        }
        RemoteViews remoteViews2 = new RemoteViews(remoteViews);
        HashMap map = new HashMap();
        if (this.mActions == null) {
            this.mActions = new ArrayList<>();
        }
        synchronized (this.mActionsLock) {
            int size = this.mActions.size();
            for (int i = 0; i < size; i++) {
                Action action = this.mActions.get(i);
                map.put(action.getUniqueKey(), action);
            }
            ArrayList<Action> arrayList = remoteViews2.mActions;
            if (arrayList == null) {
                return;
            }
            arrayList.size();
            HashMap map2 = new HashMap();
            ArrayList arrayList2 = new ArrayList();
            Iterator<Action> it = arrayList.iterator();
            while (it.hasNext()) {
                Action next = it.next();
                String uniqueKey = next.getUniqueKey();
                if (!map2.containsKey(uniqueKey)) {
                    map2.put(uniqueKey, next);
                } else if (next.mergeBehavior() == 0) {
                    arrayList2.add((Action) map2.get(uniqueKey));
                    map2.put(uniqueKey, next);
                }
            }
            arrayList.removeAll(arrayList2);
            arrayList2.clear();
            map2.clear();
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Action action2 = arrayList.get(i2);
                String uniqueKey2 = arrayList.get(i2).getUniqueKey();
                int iMergeBehavior = arrayList.get(i2).mergeBehavior();
                if (map.containsKey(uniqueKey2) && iMergeBehavior == 0) {
                    Action action3 = (Action) map.get(uniqueKey2);
                    this.mActions.remove(action3);
                    if (action3 != null) {
                        action3.clear();
                    }
                    map.remove(uniqueKey2);
                }
                if (iMergeBehavior == 0 || iMergeBehavior == 1) {
                    this.mActions.add(action2);
                }
            }
            reconstructCaches();
        }
    }

    public boolean isLegacyListRemoteViews() {
        return this.mCollectionCache.mIdToUriMapping.size() > 0;
    }

    public void visitUris(Consumer<Uri> consumer) {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                this.mActions.get(i).visitUris(consumer);
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                this.mSizedRemoteViews.get(i2).visitUris(consumer);
            }
        }
        RemoteViews remoteViews = this.mLandscape;
        if (remoteViews != null) {
            remoteViews.visitUris(consumer);
        }
        RemoteViews remoteViews2 = this.mPortrait;
        if (remoteViews2 != null) {
            remoteViews2.visitUris(consumer);
        }
    }

    public void visitIcons(Consumer<Icon> consumer) {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                this.mActions.get(i).visitIcons(consumer);
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                this.mSizedRemoteViews.get(i2).visitIcons(consumer);
            }
        }
        RemoteViews remoteViews = this.mLandscape;
        if (remoteViews != null) {
            remoteViews.visitIcons(consumer);
        }
        RemoteViews remoteViews2 = this.mPortrait;
        if (remoteViews2 != null) {
            remoteViews2.visitIcons(consumer);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean replaceRemoteCollections(int i) {
        boolean zReplaceRemoteCollections;
        if (this.mActions != null) {
            zReplaceRemoteCollections = false;
            for (int i2 = 0; i2 < this.mActions.size(); i2++) {
                Action action = this.mActions.get(i2);
                if (action instanceof SetRemoteCollectionItemListAdapterAction) {
                    SetRemoteCollectionItemListAdapterAction setRemoteCollectionItemListAdapterAction = (SetRemoteCollectionItemListAdapterAction) action;
                    if (setRemoteCollectionItemListAdapterAction.mViewId == i && setRemoteCollectionItemListAdapterAction.mServiceIntent != null) {
                        SetRemoteCollectionItemListAdapterAction setRemoteCollectionItemListAdapterAction2 = new SetRemoteCollectionItemListAdapterAction(setRemoteCollectionItemListAdapterAction.mViewId, setRemoteCollectionItemListAdapterAction.mServiceIntent);
                        setRemoteCollectionItemListAdapterAction2.mIntentId = setRemoteCollectionItemListAdapterAction.mIntentId;
                        setRemoteCollectionItemListAdapterAction2.mIsReplacedIntoAction = true;
                        this.mActions.set(i2, setRemoteCollectionItemListAdapterAction2);
                    } else {
                        if (action instanceof SetRemoteViewsAdapterIntent) {
                            SetRemoteViewsAdapterIntent setRemoteViewsAdapterIntent = (SetRemoteViewsAdapterIntent) action;
                            if (setRemoteViewsAdapterIntent.mViewId == i) {
                                this.mActions.set(i2, new SetRemoteCollectionItemListAdapterAction(setRemoteViewsAdapterIntent.mViewId, setRemoteViewsAdapterIntent.mIntent));
                            }
                        }
                        if (action instanceof ViewGroupActionAdd) {
                            ViewGroupActionAdd viewGroupActionAdd = (ViewGroupActionAdd) action;
                            if (viewGroupActionAdd.mNestedViews != null) {
                                zReplaceRemoteCollections |= viewGroupActionAdd.mNestedViews.replaceRemoteCollections(i);
                            }
                        }
                    }
                    zReplaceRemoteCollections = true;
                }
            }
        } else {
            zReplaceRemoteCollections = false;
        }
        if (this.mSizedRemoteViews != null) {
            for (int i3 = 0; i3 < this.mSizedRemoteViews.size(); i3++) {
                zReplaceRemoteCollections |= this.mSizedRemoteViews.get(i3).replaceRemoteCollections(i);
            }
        }
        RemoteViews remoteViews = this.mLandscape;
        if (remoteViews != null) {
            zReplaceRemoteCollections |= remoteViews.replaceRemoteCollections(i);
        }
        RemoteViews remoteViews2 = this.mPortrait;
        return remoteViews2 != null ? remoteViews2.replaceRemoteCollections(i) | zReplaceRemoteCollections : zReplaceRemoteCollections;
    }

    public boolean hasLegacyLists() {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                Action action = this.mActions.get(i);
                if ((!(action instanceof SetRemoteCollectionItemListAdapterAction) || ((SetRemoteCollectionItemListAdapterAction) action).mServiceIntent == null) && (!(action instanceof SetRemoteViewsAdapterIntent) || ((SetRemoteViewsAdapterIntent) action).mIntent == null)) {
                    if (action instanceof ViewGroupActionAdd) {
                        ViewGroupActionAdd viewGroupActionAdd = (ViewGroupActionAdd) action;
                        if (viewGroupActionAdd.mNestedViews == null || !viewGroupActionAdd.mNestedViews.hasLegacyLists()) {
                        }
                    }
                }
                return true;
            }
        }
        if (this.mSizedRemoteViews != null) {
            for (int i2 = 0; i2 < this.mSizedRemoteViews.size(); i2++) {
                if (this.mSizedRemoteViews.get(i2).hasLegacyLists()) {
                    return true;
                }
            }
        }
        RemoteViews remoteViews = this.mLandscape;
        if (remoteViews != null && remoteViews.hasLegacyLists()) {
            return true;
        }
        RemoteViews remoteViews2 = this.mPortrait;
        return remoteViews2 != null && remoteViews2.hasLegacyLists();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void visitIconUri(Icon icon, Consumer<Uri> consumer) {
        if (icon != null) {
            if (icon.getType() == 4 || icon.getType() == 6) {
                consumer.accept(icon.getUri());
            }
        }
    }

    private static class RemoteViewsContextWrapper extends ContextWrapper {
        private final Context mContextForResources;

        RemoteViewsContextWrapper(Context context, Context context2) {
            super(context);
            this.mContextForResources = context2;
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

    /* JADX INFO: Access modifiers changed from: private */
    static class SetEmptyView extends Action {
        int mEmptyViewId;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 6;
        }

        SetEmptyView(int i, int i2) {
            super();
            this.mViewId = i;
            this.mEmptyViewId = i2;
        }

        SetEmptyView(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mEmptyViewId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mEmptyViewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById instanceof AdapterView) {
                AdapterView adapterView = (AdapterView) viewFindViewById;
                View viewFindViewById2 = view.findViewById(this.mEmptyViewId);
                if (viewFindViewById2 == null) {
                    return;
                }
                adapterView.setEmptyView(viewFindViewById2);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268043L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, resources.getResourceName(this.mViewId));
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268043L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L});
            return new PendingResources() { // from class: android.widget.RemoteViews$SetEmptyView$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.SetEmptyView.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new SetEmptyView(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333442L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetPendingIntentTemplate extends Action {
        PendingIntent mPendingIntentTemplate;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 8;
        }

        public SetPendingIntentTemplate(int i, PendingIntent pendingIntent) {
            super();
            this.mViewId = i;
            this.mPendingIntentTemplate = pendingIntent;
        }

        public SetPendingIntentTemplate(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mPendingIntentTemplate = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntentTemplate, parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (viewFindViewById instanceof AdapterView) {
                AdapterView adapterView = (AdapterView) viewFindViewById;
                adapterView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: android.widget.RemoteViews$SetPendingIntentTemplate$$ExternalSyntheticLambda0
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(AdapterView adapterView2, View view2, int i, long j) {
                        this.f$0.lambda$apply$0(actionApplyParams, adapterView2, view2, i, j);
                    }
                });
                adapterView.setTag(this.mPendingIntentTemplate);
            } else {
                Log.e(RemoteViews.LOG_TAG, "Cannot setPendingIntentTemplate on a view which is notan AdapterView (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(ActionApplyParams actionApplyParams, AdapterView adapterView, View view, int i, long j) {
            RemoteResponse remoteResponseFindRemoteResponseTag = findRemoteResponseTag(view);
            if (remoteResponseFindRemoteResponseTag != null) {
                remoteResponseFindRemoteResponseTag.handleViewInteraction(view, actionApplyParams.handler);
            }
        }

        private RemoteResponse findRemoteResponseTag(View view) {
            if (view == null) {
                return null;
            }
            ArrayDeque arrayDeque = new ArrayDeque();
            arrayDeque.addLast(view);
            while (!arrayDeque.isEmpty()) {
                View view2 = (View) arrayDeque.removeFirst();
                Object tag = view2.getTag(R.id.fillInIntent);
                if (tag instanceof RemoteResponse) {
                    return (RemoteResponse) tag;
                }
                if (view2 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view2;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        arrayDeque.addLast(viewGroup.getChildAt(i));
                    }
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ApplicationInfoCache {
        private final Map<Pair<String, Integer>, ApplicationInfo> mPackageUserToApplicationInfo = new ArrayMap();

        static /* synthetic */ ApplicationInfo lambda$getOrPut$0(ApplicationInfo applicationInfo, Pair pair) {
            return applicationInfo;
        }

        ApplicationInfoCache() {
        }

        ApplicationInfo getOrPut(final ApplicationInfo applicationInfo) {
            Pair<String, Integer> packageUserKey = RemoteViews.getPackageUserKey(applicationInfo);
            if (packageUserKey == null) {
                return null;
            }
            return this.mPackageUserToApplicationInfo.computeIfAbsent(packageUserKey, new Function() { // from class: android.widget.RemoteViews$ApplicationInfoCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return RemoteViews.ApplicationInfoCache.lambda$getOrPut$0(applicationInfo, (Pair) obj);
                }
            });
        }

        void put(ApplicationInfo applicationInfo) {
            Pair<String, Integer> packageUserKey = RemoteViews.getPackageUserKey(applicationInfo);
            if (packageUserKey == null) {
                return;
            }
            this.mPackageUserToApplicationInfo.put(packageUserKey, applicationInfo);
        }

        ApplicationInfo get(ApplicationInfo applicationInfo) {
            Pair packageUserKey = RemoteViews.getPackageUserKey(applicationInfo);
            if (packageUserKey == null) {
                return null;
            }
            return this.mPackageUserToApplicationInfo.get(packageUserKey);
        }
    }

    private class SetRemoteCollectionItemListAdapterAction extends Action {
        int mIntentId;
        boolean mIsReplacedIntoAction;
        private RemoteCollectionItems mItems;
        final Intent mServiceIntent;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 31;
        }

        SetRemoteCollectionItemListAdapterAction(int i, RemoteCollectionItems remoteCollectionItems) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = i;
            remoteCollectionItems.setHierarchyRootData(RemoteViews.this.getHierarchyRootData());
            this.mItems = remoteCollectionItems;
            this.mServiceIntent = null;
        }

        SetRemoteCollectionItemListAdapterAction(int i, Intent intent) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = i;
            this.mItems = null;
            this.mServiceIntent = intent;
        }

        /* JADX WARN: Multi-variable type inference failed */
        SetRemoteCollectionItemListAdapterAction(Parcel parcel) {
            super();
            this.mIntentId = -1;
            this.mIsReplacedIntoAction = false;
            this.mViewId = parcel.readInt();
            this.mIntentId = parcel.readInt();
            this.mIsReplacedIntoAction = parcel.readBoolean();
            Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
            this.mServiceIntent = intent;
            this.mItems = intent == null ? new RemoteCollectionItems(parcel, RemoteViews.this.getHierarchyRootData()) : null;
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData hierarchyRootData) {
            RemoteCollectionItems remoteCollectionItems = this.mItems;
            if (remoteCollectionItems != null) {
                remoteCollectionItems.setHierarchyRootData(hierarchyRootData);
            } else if (this.mIntentId != -1) {
                RemoteViews.this.mCollectionCache.setHierarchyDataForId(this.mIntentId, hierarchyRootData);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mIntentId);
            parcel.writeBoolean(this.mIsReplacedIntoAction);
            parcel.writeTypedObject(this.mServiceIntent, i);
            RemoteCollectionItems remoteCollectionItems = this.mItems;
            if (remoteCollectionItems != null) {
                remoteCollectionItems.writeToParcel(parcel, i, true);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) throws ActionException {
            RemoteCollectionItems itemsForId;
            ActionException actionException;
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (this.mIntentId == -1) {
                itemsForId = this.mItems;
                if (itemsForId == null) {
                    itemsForId = new RemoteCollectionItems.Builder().build();
                }
            } else {
                itemsForId = RemoteViews.this.mCollectionCache.getItemsForId(this.mIntentId);
            }
            boolean z = viewGroup instanceof CocktailHostView;
            if (!(viewGroup instanceof AppWidgetHostView) && !RemoteViews.this.mAllowOtherRootParent && !z) {
                Log.e(RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (!(viewFindViewById instanceof AdapterView)) {
                Log.e(RemoteViews.LOG_TAG, "Cannot call setRemoteAdapter on a view which is not an AdapterView (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (z && this.mItems == null) {
                this.mServiceIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_COCKTAIL, 1);
                this.mServiceIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, ((CocktailHostView) viewGroup).getCocktailId());
                Log.d(RemoteViews.LOG_TAG, "mServiceIntent=" + this.mServiceIntent);
                if (viewFindViewById instanceof AbsListView) {
                    AbsListView absListView = (AbsListView) viewFindViewById;
                    absListView.setRemoteViewsAdapter(this.mServiceIntent, false);
                    absListView.setRemoteViewsInteractionHandler(actionApplyParams.handler);
                    absListView.hidden_semSetAppWidgetId(RemoteViews.this.mAppWidgetId);
                    return;
                }
                if (viewFindViewById instanceof AdapterViewAnimator) {
                    AdapterViewAnimator adapterViewAnimator = (AdapterViewAnimator) viewFindViewById;
                    adapterViewAnimator.setRemoteViewsAdapter(this.mServiceIntent, false);
                    adapterViewAnimator.setRemoteViewsOnClickHandler(actionApplyParams.handler);
                    adapterViewAnimator.hidden_semSetAppWidgetId(RemoteViews.this.mAppWidgetId);
                    return;
                }
                return;
            }
            AdapterView adapterView = (AdapterView) viewFindViewById;
            Adapter adapter = adapterView.getAdapter();
            boolean zHasFlags = RemoteViews.this.hasFlags(4);
            if ((adapter instanceof RemoteCollectionItemsAdapter) && adapter.getViewTypeCount() >= itemsForId.getViewTypeCount()) {
                try {
                    ((RemoteCollectionItemsAdapter) adapter).setData(itemsForId, actionApplyParams.handler, actionApplyParams.colorResources, zHasFlags);
                    return;
                } finally {
                }
            }
            try {
                adapterView.setAdapter(new RemoteCollectionItemsAdapter(itemsForId, actionApplyParams.handler, actionApplyParams.colorResources, zHasFlags));
                if (adapterView instanceof AbsListView) {
                    ((AbsListView) adapterView).setOnScrollListener(new AbsListView.OnScrollListener(this) { // from class: android.widget.RemoteViews.SetRemoteCollectionItemListAdapterAction.1
                        @Override // android.widget.AbsListView.OnScrollListener
                        public void onScroll(AbsListView absListView2, int i, int i2, int i3) {
                        }

                        @Override // android.widget.AbsListView.OnScrollListener
                        public void onScrollStateChanged(AbsListView absListView2, int i) {
                            if (i != 0) {
                                actionApplyParams.handler.onScroll(absListView2);
                            }
                        }
                    });
                }
            } finally {
            }
        }

        @Override // android.widget.RemoteViews.Action
        public String getUniqueKey() {
            return "33_" + this.mViewId;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> consumer) {
            RemoteCollectionItems remoteCollectionItems = this.mItems;
            if (remoteCollectionItems == null) {
                RemoteCollectionItems itemsForId = RemoteViews.this.mCollectionCache.getItemsForId(this.mIntentId);
                if (itemsForId != null) {
                    itemsForId.visitUris(consumer);
                    return;
                }
                return;
            }
            remoteCollectionItems.visitUris(consumer);
        }

        @Override // android.widget.RemoteViews.Action
        public void visitIcons(Consumer<Icon> consumer) {
            RemoteCollectionItems remoteCollectionItems = this.mItems;
            if (remoteCollectionItems == null) {
                RemoteCollectionItems itemsForId = RemoteViews.this.mCollectionCache.getItemsForId(this.mIntentId);
                if (itemsForId != null) {
                    itemsForId.visitIcons(consumer);
                    return;
                }
                return;
            }
            remoteCollectionItems.visitIcons(consumer);
        }

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return this.mItems != null;
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            if (this.mItems == null) {
                return;
            }
            long jStart = protoOutputStream.start(1146756268046L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            long jStart2 = protoOutputStream.start(1146756268034L);
            this.mItems.writeToProto(context, protoOutputStream, true);
            protoOutputStream.end(jStart2);
            protoOutputStream.end(jStart);
        }
    }

    private PendingResources<Action> createSetRemoteCollectionItemListAdapterActionFromProto(ProtoInputStream protoInputStream) throws Exception {
        final LongSparseArray longSparseArray = new LongSparseArray();
        long jStart = protoInputStream.start(1146756268046L);
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                long jStart2 = protoInputStream.start(1146756268034L);
                longSparseArray.put(1146756268034L, RemoteCollectionItems.createFromProto(protoInputStream));
                protoInputStream.end(jStart2);
            } else {
                Log.w(LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        protoInputStream.end(jStart);
        checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1146756268034L});
        return new PendingResources() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda6
            @Override // android.widget.RemoteViews.PendingResources
            public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                return this.f$0.lambda$createSetRemoteCollectionItemListAdapterActionFromProto$2(longSparseArray, context, resources, hierarchyRootData, i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Action lambda$createSetRemoteCollectionItemListAdapterActionFromProto$2(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
        return new SetRemoteCollectionItemListAdapterAction(getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), (RemoteCollectionItems) ((PendingResources) longSparseArray.get(1146756268034L)).create(context, resources, hierarchyRootData, i));
    }

    public CompletableFuture<Void> collectAllIntents(int i, AppWidgetManager.ServiceCollectionCache serviceCollectionCache) {
        return this.mCollectionCache.collectAllIntentsNoComplete(this, i, serviceCollectionCache);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class RemoteCollectionCache {
        private final SparseArray<String> mIdToUriMapping = new SparseArray<>();
        private final Map<String, RemoteCollectionItems> mUriToCollectionMapping = new HashMap();

        RemoteCollectionCache() {
        }

        RemoteCollectionCache(RemoteCollectionCache remoteCollectionCache) {
            for (int i = 0; i < remoteCollectionCache.mIdToUriMapping.size(); i++) {
                String strValueAt = remoteCollectionCache.mIdToUriMapping.valueAt(i);
                this.mIdToUriMapping.put(remoteCollectionCache.mIdToUriMapping.keyAt(i), strValueAt);
                this.mUriToCollectionMapping.put(strValueAt, remoteCollectionCache.mUriToCollectionMapping.get(strValueAt));
            }
        }

        RemoteCollectionCache(Parcel parcel) {
            int i = parcel.readInt();
            HierarchyRootData hierarchyRootData = new HierarchyRootData(RemoteViews.this.mBitmapCache, this, RemoteViews.this.mApplicationInfoCache, RemoteViews.this.mClassCookies);
            for (int i2 = 0; i2 < i; i2++) {
                addMapping(parcel.readInt(), parcel.readString8(), new RemoteCollectionItems(parcel, hierarchyRootData));
            }
        }

        void addMapping(int i, String str, RemoteCollectionItems remoteCollectionItems) {
            this.mIdToUriMapping.put(i, str);
            this.mUriToCollectionMapping.put(str, remoteCollectionItems);
        }

        void setHierarchyDataForId(int i, HierarchyRootData hierarchyRootData) {
            String str = this.mIdToUriMapping.get(i);
            if (this.mUriToCollectionMapping.get(str) == null) {
                Log.e(RemoteViews.LOG_TAG, "Error setting hierarchy data for id=" + i);
                return;
            }
            this.mUriToCollectionMapping.get(str).setHierarchyRootData(hierarchyRootData);
        }

        RemoteCollectionItems getItemsForId(int i) {
            return this.mUriToCollectionMapping.get(this.mIdToUriMapping.get(i));
        }

        public CompletableFuture<Void> collectAllIntentsNoComplete(RemoteViews remoteViews, int i, AppWidgetManager.ServiceCollectionCache serviceCollectionCache) {
            SparseArray<Intent> sparseArray = new SparseArray<>();
            collectAllIntentsInternal(remoteViews, sparseArray);
            int size = sparseArray.size();
            if (size == 0) {
                Log.e(RemoteViews.LOG_TAG, "Possibly notifying updates for nonexistent view Id");
                return CompletableFuture.completedFuture(null);
            }
            Parcel parcelObtain = Parcel.obtain();
            RemoteViews.this.writeToParcel(parcelObtain, 0, sparseArray);
            int iDataSize = 800000 - parcelObtain.dataSize();
            parcelObtain.recycle();
            return connectAllUniqueIntents(iDataSize >= 0 ? iDataSize / size : 0, (i - RemoteViews.this.getBitmapMemoryUsedByActions()) / size, sparseArray, serviceCollectionCache);
        }

        private void collectAllIntentsInternal(RemoteViews remoteViews, SparseArray<Intent> sparseArray) {
            if (remoteViews.hasSizedRemoteViews()) {
                Iterator it = remoteViews.mSizedRemoteViews.iterator();
                while (it.hasNext()) {
                    collectAllIntentsInternal((RemoteViews) it.next(), sparseArray);
                }
                return;
            }
            if (remoteViews.hasLandscapeAndPortraitLayouts()) {
                collectAllIntentsInternal(remoteViews.mLandscape, sparseArray);
                collectAllIntentsInternal(remoteViews.mPortrait, sparseArray);
                return;
            }
            if (remoteViews.mActions != null) {
                Iterator it2 = remoteViews.mActions.iterator();
                while (it2.hasNext()) {
                    Action action = (Action) it2.next();
                    if (action instanceof SetRemoteCollectionItemListAdapterAction) {
                        SetRemoteCollectionItemListAdapterAction setRemoteCollectionItemListAdapterAction = (SetRemoteCollectionItemListAdapterAction) action;
                        if (setRemoteCollectionItemListAdapterAction.mIntentId == -1 || setRemoteCollectionItemListAdapterAction.mIsReplacedIntoAction) {
                            if (setRemoteCollectionItemListAdapterAction.mIntentId != -1 && setRemoteCollectionItemListAdapterAction.mIsReplacedIntoAction) {
                                setRemoteCollectionItemListAdapterAction.mIsReplacedIntoAction = false;
                                if (!sparseArray.contains(setRemoteCollectionItemListAdapterAction.mIntentId)) {
                                    sparseArray.put(setRemoteCollectionItemListAdapterAction.mIntentId, setRemoteCollectionItemListAdapterAction.mServiceIntent);
                                    setRemoteCollectionItemListAdapterAction.mItems = null;
                                }
                            } else if (setRemoteCollectionItemListAdapterAction.mServiceIntent != null) {
                                String uri = setRemoteCollectionItemListAdapterAction.mServiceIntent.toUri(0);
                                int iIndexOfValueByValue = this.mIdToUriMapping.indexOfValueByValue(uri);
                                if (iIndexOfValueByValue == -1) {
                                    int size = this.mIdToUriMapping.size();
                                    setRemoteCollectionItemListAdapterAction.mIntentId = size;
                                    this.mIdToUriMapping.put(size, uri);
                                    sparseArray.put(setRemoteCollectionItemListAdapterAction.mIntentId, setRemoteCollectionItemListAdapterAction.mServiceIntent);
                                    setRemoteCollectionItemListAdapterAction.mItems = null;
                                } else {
                                    setRemoteCollectionItemListAdapterAction.mIntentId = this.mIdToUriMapping.keyAt(iIndexOfValueByValue);
                                    setRemoteCollectionItemListAdapterAction.mItems = null;
                                }
                            } else {
                                for (RemoteViews remoteViews2 : setRemoteCollectionItemListAdapterAction.mItems.mViews) {
                                    collectAllIntentsInternal(remoteViews2, sparseArray);
                                }
                            }
                        }
                    } else if (action instanceof ViewGroupActionAdd) {
                        ViewGroupActionAdd viewGroupActionAdd = (ViewGroupActionAdd) action;
                        if (viewGroupActionAdd.mNestedViews != null) {
                            collectAllIntentsInternal(viewGroupActionAdd.mNestedViews, sparseArray);
                        }
                    }
                }
            }
        }

        private CompletableFuture<Void> connectAllUniqueIntents(int i, int i2, SparseArray<Intent> sparseArray, AppWidgetManager.ServiceCollectionCache serviceCollectionCache) {
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                final String str = this.mIdToUriMapping.get(sparseArray.keyAt(i3));
                arrayList.add(getItemsFutureFromIntentWithTimeout(sparseArray.valueAt(i3), i, i2, serviceCollectionCache).thenAccept(new Consumer() { // from class: android.widget.RemoteViews$RemoteCollectionCache$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$connectAllUniqueIntents$0(str, (RemoteViews.RemoteCollectionItems) obj);
                    }
                }));
            }
            return CompletableFuture.allOf((CompletableFuture[]) arrayList.toArray(new IntFunction() { // from class: android.widget.RemoteViews$RemoteCollectionCache$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final Object apply(int i4) {
                    return RemoteViews.RemoteCollectionCache.lambda$connectAllUniqueIntents$1(i4);
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$connectAllUniqueIntents$0(String str, RemoteCollectionItems remoteCollectionItems) {
            remoteCollectionItems.setHierarchyRootData(RemoteViews.this.getHierarchyRootData());
            this.mUriToCollectionMapping.put(str, remoteCollectionItems);
        }

        static /* synthetic */ CompletableFuture[] lambda$connectAllUniqueIntents$1(int i) {
            return new CompletableFuture[i];
        }

        private static CompletableFuture<RemoteCollectionItems> getItemsFutureFromIntentWithTimeout(Intent intent, final int i, final int i2, AppWidgetManager.ServiceCollectionCache serviceCollectionCache) {
            if (intent == null) {
                Log.e(RemoteViews.LOG_TAG, "Null intent received when generating adapter future");
                return CompletableFuture.completedFuture(new RemoteCollectionItems.Builder().build());
            }
            Application applicationCurrentApplication = ActivityThread.currentApplication();
            final CompletableFuture<RemoteCollectionItems> completableFuture = new CompletableFuture<>();
            String packageName = applicationCurrentApplication.getPackageName();
            ComponentName component = intent.getComponent();
            if (packageName != null && component != null && !packageName.equals(component.getPackageName())) {
                completableFuture.complete(new RemoteCollectionItems.Builder().build());
                return completableFuture;
            }
            serviceCollectionCache.connectAndConsume(intent, new Consumer() { // from class: android.widget.RemoteViews$RemoteCollectionCache$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteViews.RemoteCollectionCache.lambda$getItemsFutureFromIntentWithTimeout$2(i, i2, completableFuture, (IBinder) obj);
                }
            }, completableFuture.defaultExecutor());
            completableFuture.completeOnTimeout(new RemoteCollectionItems.Builder().build(), 20000L, TimeUnit.MILLISECONDS);
            return completableFuture;
        }

        static /* synthetic */ void lambda$getItemsFutureFromIntentWithTimeout$2(int i, int i2, CompletableFuture completableFuture, IBinder iBinder) {
            RemoteCollectionItems remoteCollectionItemsBuild;
            try {
                remoteCollectionItemsBuild = IRemoteViewsFactory.Stub.asInterface(iBinder).getRemoteCollectionItems(i, i2);
            } catch (RemoteException e) {
                RemoteCollectionItems remoteCollectionItemsBuild2 = new RemoteCollectionItems.Builder().build();
                Log.e(RemoteViews.LOG_TAG, "Error getting collection items from the factory", e);
                remoteCollectionItemsBuild = remoteCollectionItemsBuild2;
            }
            if (remoteCollectionItemsBuild == null) {
                remoteCollectionItemsBuild = new RemoteCollectionItems.Builder().build();
            }
            completableFuture.complete(remoteCollectionItemsBuild);
        }

        public void writeToParcel(Parcel parcel, int i, SparseArray<Intent> sparseArray) {
            parcel.writeInt(this.mIdToUriMapping.size());
            for (int i2 = 0; i2 < this.mIdToUriMapping.size(); i2++) {
                int iKeyAt = this.mIdToUriMapping.keyAt(i2);
                if (sparseArray == null || !sparseArray.contains(iKeyAt)) {
                    parcel.writeInt(iKeyAt);
                    String strValueAt = this.mIdToUriMapping.valueAt(i2);
                    parcel.writeString8(strValueAt);
                    this.mUriToCollectionMapping.get(strValueAt).writeToParcel(parcel, i, true);
                }
            }
        }

        public void writeToProto(Context context, ProtoOutputStream protoOutputStream) {
            long jStart = protoOutputStream.start(1146756268047L);
            for (int i = 0; i < this.mIdToUriMapping.size(); i++) {
                long jStart2 = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1112396529665L, this.mIdToUriMapping.keyAt(i));
                String strValueAt = this.mIdToUriMapping.valueAt(i);
                protoOutputStream.write(1138166333442L, strValueAt);
                long jStart3 = protoOutputStream.start(1146756268035L);
                this.mUriToCollectionMapping.get(strValueAt).writeToProto(context, protoOutputStream, true);
                protoOutputStream.end(jStart3);
                protoOutputStream.end(jStart2);
            }
            protoOutputStream.end(jStart);
        }
    }

    private PendingResources<RemoteCollectionCache> populateRemoteCollectionCacheFromProto(ProtoInputStream protoInputStream) throws Exception {
        final ArrayList arrayList = new ArrayList();
        long jStart = protoInputStream.start(1146756268047L);
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                LongSparseArray longSparseArray = new LongSparseArray();
                long jStart2 = protoInputStream.start(2246267895809L);
                while (protoInputStream.nextField() != -1) {
                    int fieldNumber = protoInputStream.getFieldNumber();
                    if (fieldNumber == 1) {
                        longSparseArray.put(1112396529665L, Integer.valueOf(protoInputStream.readInt(1112396529665L)));
                    } else if (fieldNumber == 2) {
                        longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                    } else if (fieldNumber == 3) {
                        long jStart3 = protoInputStream.start(1146756268035L);
                        longSparseArray.put(1146756268035L, RemoteCollectionItems.createFromProto(protoInputStream));
                        protoInputStream.end(jStart3);
                    } else {
                        Log.w(LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                    }
                }
                protoInputStream.end(jStart2);
                checkContainsKeys(longSparseArray, new long[]{1112396529665L, 1138166333442L, 1146756268035L});
                arrayList.add(longSparseArray);
            } else {
                Log.w(LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        protoInputStream.end(jStart);
        return new PendingResources() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda1
            @Override // android.widget.RemoteViews.PendingResources
            public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                return RemoteViews.lambda$populateRemoteCollectionCacheFromProto$3(arrayList, context, resources, hierarchyRootData, i);
            }
        };
    }

    static /* synthetic */ RemoteCollectionCache lambda$populateRemoteCollectionCacheFromProto$3(ArrayList arrayList, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LongSparseArray longSparseArray = (LongSparseArray) it.next();
            hierarchyRootData.mRemoteCollectionCache.addMapping(((Integer) longSparseArray.get(1112396529665L)).intValue(), (String) longSparseArray.get(1138166333442L), (RemoteCollectionItems) ((PendingResources) longSparseArray.get(1146756268035L)).create(context, resources, hierarchyRootData, i));
        }
        return hierarchyRootData.mRemoteCollectionCache;
    }

    private class SetRemoteViewsAdapterIntent extends Action {
        Intent mIntent;
        boolean mIsAsync;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 10;
        }

        public SetRemoteViewsAdapterIntent(int i, Intent intent) {
            super();
            this.mIsAsync = false;
            this.mViewId = i;
            this.mIntent = intent;
        }

        public SetRemoteViewsAdapterIntent(Parcel parcel) {
            super();
            this.mIsAsync = false;
            this.mViewId = parcel.readInt();
            this.mIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeTypedObject(this.mIntent, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            boolean z = viewGroup instanceof CocktailHostView;
            boolean z2 = viewGroup instanceof AppWidgetHostView;
            if (!z2 && !RemoteViews.this.mAllowOtherRootParent && !z) {
                Log.e(RemoteViews.LOG_TAG, "setRemoteAdapter can only be used for AppWidgets (root id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            boolean z3 = viewFindViewById instanceof AbsListView;
            if (!z3 && !(viewFindViewById instanceof AdapterViewAnimator)) {
                Log.e(RemoteViews.LOG_TAG, "Cannot setRemoteAdapter on a view which is not an AbsListView or AdapterViewAnimator (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            if (z) {
                this.mIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_COCKTAIL, 1);
                this.mIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, ((CocktailHostView) viewGroup).getCocktailId());
            } else {
                if (!RemoteViews.this.mAllowOtherRootParent || z2) {
                    RemoteViews.this.mAppWidgetId = ((AppWidgetHostView) viewGroup).getAppWidgetId();
                }
                this.mIntent.putExtra(RemoteViews.EXTRA_REMOTEADAPTER_APPWIDGET_ID, RemoteViews.this.mAppWidgetId).putExtra(RemoteViews.EXTRA_REMOTEADAPTER_ON_LIGHT_BACKGROUND, RemoteViews.this.hasFlags(4));
            }
            if (z3) {
                AbsListView absListView = (AbsListView) viewFindViewById;
                absListView.setRemoteViewsAdapter(this.mIntent, this.mIsAsync);
                absListView.setRemoteViewsInteractionHandler(actionApplyParams.handler);
                absListView.setOnScrollListener(new AbsListView.OnScrollListener(this) { // from class: android.widget.RemoteViews.SetRemoteViewsAdapterIntent.1
                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScroll(AbsListView absListView2, int i, int i2, int i3) {
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScrollStateChanged(AbsListView absListView2, int i) {
                        if (i != 0) {
                            actionApplyParams.handler.onScroll(absListView2);
                        }
                    }
                });
                absListView.hidden_semSetAppWidgetId(RemoteViews.this.mAppWidgetId);
                return;
            }
            if (viewFindViewById instanceof AdapterViewAnimator) {
                AdapterViewAnimator adapterViewAnimator = (AdapterViewAnimator) viewFindViewById;
                adapterViewAnimator.setRemoteViewsAdapter(this.mIntent, this.mIsAsync);
                adapterViewAnimator.setRemoteViewsOnClickHandler(actionApplyParams.handler);
                adapterViewAnimator.hidden_semSetAppWidgetId(RemoteViews.this.mAppWidgetId);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            SetRemoteViewsAdapterIntent setRemoteViewsAdapterIntent = RemoteViews.this.new SetRemoteViewsAdapterIntent(this.mViewId, this.mIntent);
            setRemoteViewsAdapterIntent.mIsAsync = true;
            return setRemoteViewsAdapterIntent;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnClickResponse extends Action {
        final RemoteResponse mResponse;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 1;
        }

        SetOnClickResponse(int i, RemoteResponse remoteResponse) {
            super();
            this.mViewId = i;
            this.mResponse = remoteResponse;
        }

        SetOnClickResponse(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            RemoteResponse remoteResponse = new RemoteResponse();
            this.mResponse = remoteResponse;
            remoteResponse.readFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            this.mResponse.writeToParcel(parcel, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) {
            View viewFindViewById;
            if ((RemoteViews.this.hasDrawInstructions() && (view instanceof RemoteComposePlayer)) || (viewFindViewById = view.findViewById(this.mViewId)) == null) {
                return;
            }
            if (this.mResponse.mPendingIntent != null) {
                if (RemoteViews.this.hasFlags(2) && !RemoteViews.this.mAllowOtherRootParent) {
                    Log.w(RemoteViews.LOG_TAG, "Cannot SetOnClickResponse for collection item (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                    ApplicationInfo applicationInfo = view.getContext().getApplicationInfo();
                    if (applicationInfo != null && applicationInfo.targetSdkVersion >= 16) {
                        return;
                    }
                }
                viewFindViewById.setTagInternal(R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!RemoteViews.this.hasFlags(2)) {
                    Log.e(RemoteViews.LOG_TAG, "The method setOnClickFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                } else if (viewFindViewById == view) {
                    viewFindViewById.setTagInternal(R.id.fillInIntent, this.mResponse);
                    return;
                }
            } else {
                viewFindViewById.setOnClickListener(null);
                viewFindViewById.setTagInternal(R.id.pending_intent_tag, null);
                viewFindViewById.setTagInternal(R.id.fillInIntent, null);
                return;
            }
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: android.widget.RemoteViews$SetOnClickResponse$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$apply$0(actionApplyParams, view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(ActionApplyParams actionApplyParams, View view) {
            this.mResponse.handleViewInteraction(view, actionApplyParams.handler);
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

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 34;
        }

        SetOnStylusHandwritingResponse(int i, PendingIntent pendingIntent) {
            super();
            this.mViewId = i;
            this.mPendingIntent = pendingIntent;
        }

        SetOnStylusHandwritingResponse(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mPendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) {
            final View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (RemoteViews.this.hasFlags(2)) {
                Log.w(RemoteViews.LOG_TAG, "Cannot use setOnStylusHandwritingPendingIntent for collection item (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            PendingIntent pendingIntent = this.mPendingIntent;
            if (pendingIntent != null) {
                final RemoteResponse remoteResponseFromPendingIntent = RemoteResponse.fromPendingIntent(pendingIntent);
                viewFindViewById.setHandwritingDelegatorCallback(new Runnable() { // from class: android.widget.RemoteViews$SetOnStylusHandwritingResponse$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        remoteResponseFromPendingIntent.handleViewInteraction(viewFindViewById, actionApplyParams.handler);
                    }
                });
                viewFindViewById.setAllowedHandwritingDelegatePackage(this.mPendingIntent.getCreatorPackage());
            } else {
                viewFindViewById.setHandwritingDelegatorCallback(null);
                viewFindViewById.setAllowedHandwritingDelegatePackage(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SetOnCheckedChangeResponse extends Action {
        private final RemoteResponse mResponse;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 29;
        }

        SetOnCheckedChangeResponse(int i, RemoteResponse remoteResponse) {
            super();
            this.mViewId = i;
            this.mResponse = remoteResponse;
        }

        SetOnCheckedChangeResponse(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            RemoteResponse remoteResponse = new RemoteResponse();
            this.mResponse = remoteResponse;
            remoteResponse.readFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            this.mResponse.writeToParcel(parcel, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (!(viewFindViewById instanceof CompoundButton)) {
                Log.w(RemoteViews.LOG_TAG, "setOnCheckedChange methods cannot be used on non-CompoundButton child (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            CompoundButton compoundButton = (CompoundButton) viewFindViewById;
            if (this.mResponse.mPendingIntent != null) {
                if (RemoteViews.this.hasFlags(2)) {
                    Log.w(RemoteViews.LOG_TAG, "Cannot setOnCheckedChangePendingIntent for collection item (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
                    return;
                }
                viewFindViewById.setTagInternal(R.id.pending_intent_tag, this.mResponse.mPendingIntent);
            } else if (this.mResponse.mFillIntent != null) {
                if (!RemoteViews.this.hasFlags(2)) {
                    Log.e(RemoteViews.LOG_TAG, "The method setOnCheckedChangeFillInIntent is available only from RemoteViewsFactory (ie. on collection items).");
                    return;
                }
            } else {
                compoundButton.setOnCheckedChangeListener(null);
                compoundButton.setTagInternal(R.id.remote_checked_change_listener_tag, null);
                return;
            }
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: android.widget.RemoteViews$SetOnCheckedChangeResponse$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton2, boolean z) {
                    this.f$0.lambda$apply$0(actionApplyParams, compoundButton2, z);
                }
            };
            compoundButton.setTagInternal(R.id.remote_checked_change_listener_tag, onCheckedChangeListener);
            compoundButton.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(ActionApplyParams actionApplyParams, CompoundButton compoundButton, boolean z) {
            this.mResponse.handleViewInteraction(compoundButton, actionApplyParams.handler);
        }

        @Override // android.widget.RemoteViews.Action
        public void clear() {
            this.mResponse.mPendingIntent = null;
            this.mResponse.mFillIntent = null;
        }
    }

    public static Rect getSourceBounds(View view) {
        float f = view.getContext().getResources().getCompatibilityInfo().applicationScale;
        view.getLocationOnScreen(new int[2]);
        Rect rect = new Rect();
        rect.left = (int) ((r1[0] * f) + 0.5f);
        rect.top = (int) ((r1[1] * f) + 0.5f);
        rect.right = (int) (((r1[0] + view.getWidth()) * f) + 0.5f);
        rect.bottom = (int) (((r1[1] + view.getHeight()) * f) + 0.5f);
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Class<?> getParameterType(int i) {
        if (i != 30) {
            switch (i) {
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
                default:
                    return null;
            }
        }
        return SemBlurInfo.class;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ae A[Catch: all -> 0x011e, TryCatch #2 {, blocks: (B:4:0x000f, B:8:0x0021, B:10:0x0030, B:12:0x0038, B:13:0x0055, B:15:0x0062, B:16:0x0086, B:9:0x0028, B:17:0x0087, B:18:0x00ab, B:20:0x00ae, B:21:0x00b0, B:23:0x00b2, B:25:0x00ba, B:27:0x00bc, B:29:0x00c0, B:30:0x00d0, B:32:0x00dd, B:33:0x0119, B:34:0x011a, B:35:0x011c), top: B:43:0x000f, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b2 A[Catch: all -> 0x011e, TryCatch #2 {, blocks: (B:4:0x000f, B:8:0x0021, B:10:0x0030, B:12:0x0038, B:13:0x0055, B:15:0x0062, B:16:0x0086, B:9:0x0028, B:17:0x0087, B:18:0x00ab, B:20:0x00ae, B:21:0x00b0, B:23:0x00b2, B:25:0x00ba, B:27:0x00bc, B:29:0x00c0, B:30:0x00d0, B:32:0x00dd, B:33:0x0119, B:34:0x011a, B:35:0x011c), top: B:43:0x000f, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static MethodHandle getMethod(View view, String str, Class<?> cls, boolean z) {
        Method method;
        Class<?> cls2 = view.getClass();
        ArrayMap<MethodKey, MethodArgs> arrayMap = sMethods;
        synchronized (arrayMap) {
            MethodKey methodKey = sLookupKey;
            methodKey.set(cls2, cls, str);
            MethodArgs methodArgs = arrayMap.get(methodKey);
            if (methodArgs == null) {
                try {
                    if (cls == null) {
                        Class[] clsArr = new Class[0];
                        method = cls2.getMethod(str, null);
                    } else {
                        method = cls2.getMethod(str, cls);
                    }
                    if (!method.isAnnotationPresent(RemotableViewMethod.class)) {
                        throw new ActionException("view: " + cls2.getName() + " can't use method with RemoteViews: " + str + getParameters(cls));
                    }
                    MethodArgs methodArgs2 = new MethodArgs();
                    methodArgs2.syncMethod = MethodHandles.publicLookup().unreflect(method);
                    methodArgs2.asyncMethodName = ((RemotableViewMethod) method.getAnnotation(RemotableViewMethod.class)).asyncImpl();
                    MethodKey methodKey2 = new MethodKey();
                    methodKey2.set(cls2, cls, str);
                    arrayMap.put(methodKey2, methodArgs2);
                    methodArgs = methodArgs2;
                    if (z) {
                        return methodArgs.syncMethod;
                    }
                    if (methodArgs.asyncMethodName.isEmpty()) {
                        return null;
                    }
                    if (methodArgs.asyncMethod == null) {
                        MethodType methodTypeChangeReturnType = methodArgs.syncMethod.type().dropParameterTypes(0, 1).changeReturnType(Runnable.class);
                        try {
                            methodArgs.asyncMethod = MethodHandles.publicLookup().findVirtual(cls2, methodArgs.asyncMethodName, methodTypeChangeReturnType);
                        } catch (IllegalAccessException | NoSuchMethodException unused) {
                            throw new ActionException("Async implementation declared as " + methodArgs.asyncMethodName + " but not defined for " + str + ": public Runnable " + methodArgs.asyncMethodName + " (" + TextUtils.join(",", methodTypeChangeReturnType.parameterArray()) + NavigationBarInflaterView.KEY_CODE_END);
                        }
                    }
                    return methodArgs.asyncMethod;
                } catch (IllegalAccessException | NoSuchMethodException unused2) {
                    throw new ActionException("view: " + cls2.getName() + " doesn't have method: " + str + getParameters(cls));
                }
            }
            if (z) {
            }
        }
    }

    private static String getParameters(Class<?> cls) {
        if (cls == null) {
            return "()";
        }
        return NavigationBarInflaterView.KEY_CODE_START + cls + NavigationBarInflaterView.KEY_CODE_END;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetDrawableTint extends Action {
        int mColorFilter;
        PorterDuff.Mode mFilterMode;
        boolean mTargetBackground;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 3;
        }

        SetDrawableTint(int i, boolean z, int i2, PorterDuff.Mode mode) {
            super();
            this.mViewId = i;
            this.mTargetBackground = z;
            this.mColorFilter = i2;
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
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            Drawable drawable;
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (this.mTargetBackground) {
                drawable = viewFindViewById.getBackground();
            } else {
                drawable = viewFindViewById instanceof ImageView ? ((ImageView) viewFindViewById).getDrawable() : null;
            }
            if (drawable != null) {
                drawable.mutate().setColorFilter(this.mColorFilter, this.mFilterMode);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268042L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1120986464259L, this.mColorFilter);
            protoOutputStream.write(1120986464260L, PorterDuff.modeToInt(this.mFilterMode));
            protoOutputStream.write(1133871366146L, this.mTargetBackground);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268042L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1133871366146L, Boolean.valueOf(protoInputStream.readBoolean(1133871366146L)));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else if (fieldNumber == 4) {
                    longSparseArray.put(1120986464260L, PorterDuff.intToMode(protoInputStream.readInt(1120986464260L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$SetDrawableTint$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.SetDrawableTint.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new SetDrawableTint(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), ((Boolean) longSparseArray.get(1133871366146L, false)).booleanValue(), ((Integer) longSparseArray.get(1120986464259L, 0)).intValue(), (PorterDuff.Mode) longSparseArray.get(1120986464260L));
        }
    }

    private class SetTextViewShadowAction extends Action {
        int color;
        float dx;
        float dy;
        float radius;
        int viewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 108;
        }

        public SetTextViewShadowAction(RemoteViews remoteViews, int i, float f, float f2, float f3, int i2) {
            super();
            this.viewId = i;
            this.radius = f;
            this.dx = f2;
            this.dy = f3;
            this.color = i2;
        }

        public SetTextViewShadowAction(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.radius = parcel.readFloat();
            this.dx = parcel.readFloat();
            this.dy = parcel.readFloat();
            this.color = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.viewId);
            parcel.writeFloat(this.radius);
            parcel.writeFloat(this.dx);
            parcel.writeFloat(this.dy);
            parcel.writeInt(this.color);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.viewId);
            if (viewFindViewById instanceof TextView) {
                ((TextView) viewFindViewById).setShadowLayer(this.radius, this.dx, this.dy, this.color);
            }
        }
    }

    private static class SetStringTagAction extends Action {
        private final int mKey;
        private final String mTag;
        private final int mViewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 109;
        }

        SetStringTagAction(int i, int i2, String str) {
            super();
            this.mViewId = i;
            this.mKey = i2;
            this.mTag = str;
        }

        SetStringTagAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mKey = parcel.readInt();
            this.mTag = parcel.readString();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mKey);
            parcel.writeString(this.mTag);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            viewFindViewById.setTag(this.mKey, this.mTag);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetRippleDrawableColor extends Action {
        ColorStateList mColorStateList;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 21;
        }

        SetRippleDrawableColor(int i, ColorStateList colorStateList) {
            super();
            this.mViewId = i;
            this.mColorStateList = colorStateList;
        }

        SetRippleDrawableColor(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mColorStateList = (ColorStateList) parcel.readParcelable(null, ColorStateList.class);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeParcelable(this.mColorStateList, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            Drawable background = viewFindViewById.getBackground();
            if (background instanceof RippleDrawable) {
                ((RippleDrawable) background.mutate()).setColor(this.mColorStateList);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268047L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            RemoteViews.writeColorStateListToProto(protoOutputStream, this.mColorStateList, 1146756268034L);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268047L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1146756268034L, RemoteViews.createColorStateListFromProto(protoInputStream, 1146756268034L));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1146756268034L});
            return new PendingResources() { // from class: android.widget.RemoteViews$SetRippleDrawableColor$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.SetRippleDrawableColor.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new SetRippleDrawableColor(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), (ColorStateList) longSparseArray.get(1146756268034L));
        }
    }

    @Deprecated
    private final class ViewContentNavigation extends Action {
        final boolean mNext;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 5;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 2;
        }

        ViewContentNavigation(RemoteViews remoteViews, int i, boolean z) {
            super();
            this.mViewId = i;
            this.mNext = z;
        }

        ViewContentNavigation(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mNext = parcel.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeBoolean(this.mNext);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            try {
                (void) RemoteViews.getMethod(viewFindViewById, this.mNext ? "showNext" : "showPrevious", null, false).invoke(viewFindViewById);
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }
    }

    BitmapCache getBitmapCache() {
        return this.mBitmapCache;
    }

    static class BitmapCache {
        SparseIntArray mBitmapHashes;
        long mBitmapMemory;
        ArrayList<Bitmap> mBitmaps;

        public BitmapCache() {
            this.mBitmapMemory = -1L;
            this.mBitmaps = new ArrayList<>();
            this.mBitmapHashes = new SparseIntArray();
        }

        public BitmapCache(Parcel parcel) {
            this.mBitmapMemory = -1L;
            this.mBitmaps = parcel.createTypedArrayList(Bitmap.CREATOR);
            this.mBitmapHashes = new SparseIntArray();
            for (int i = 0; i < this.mBitmaps.size(); i++) {
                Bitmap bitmap = this.mBitmaps.get(i);
                if (bitmap != null) {
                    this.mBitmapHashes.put(bitmap.hashCode(), i);
                }
            }
        }

        BitmapCache(BitmapCache bitmapCache) {
            this.mBitmapMemory = -1L;
            this.mBitmaps = new ArrayList<>(bitmapCache.mBitmaps);
            this.mBitmapHashes = bitmapCache.mBitmapHashes.m5539clone();
        }

        public int getBitmapId(Bitmap bitmap) {
            if (bitmap == null) {
                return -1;
            }
            int iHashCode = bitmap.hashCode();
            int i = this.mBitmapHashes.get(iHashCode, -1);
            if (i != -1) {
                return i;
            }
            if (bitmap.isMutable()) {
                bitmap = bitmap.asShared();
            }
            this.mBitmaps.add(bitmap);
            this.mBitmapHashes.put(iHashCode, this.mBitmaps.size() - 1);
            this.mBitmapMemory = -1L;
            return this.mBitmaps.size() - 1;
        }

        public Bitmap getBitmapForId(int i) {
            if (i == -1 || i >= this.mBitmaps.size()) {
                return null;
            }
            return this.mBitmaps.get(i);
        }

        public void writeBitmapsToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(this.mBitmaps, i);
        }

        public void writeBitmapsToProto(ProtoOutputStream protoOutputStream) {
            for (int i = 0; i < this.mBitmaps.size(); i++) {
                Bitmap bitmap = this.mBitmaps.get(i);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, byteArrayOutputStream);
                protoOutputStream.write(RemoteViewsProto.BITMAP_CACHE, byteArrayOutputStream.toByteArray());
            }
        }

        public long getBitmapMemory() {
            if (this.mBitmapMemory < 0) {
                this.mBitmapMemory = 0L;
                int size = this.mBitmaps.size();
                for (int i = 0; i < size; i++) {
                    this.mBitmapMemory += this.mBitmaps.get(i).getAllocationByteCount();
                }
            }
            return this.mBitmapMemory;
        }

        public void mergeWithCache(BitmapCache bitmapCache) {
            for (int i = 0; i < bitmapCache.mBitmaps.size(); i++) {
                getBitmapId(bitmapCache.mBitmaps.get(i));
            }
        }
    }

    private class BitmapReflectionAction extends Action {
        Bitmap mBitmap;
        int mBitmapId;
        String mMethodName;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 12;
        }

        BitmapReflectionAction(RemoteViews remoteViews, int i, String str, Bitmap bitmap) {
            super();
            this.mBitmap = bitmap;
            this.mViewId = i;
            this.mMethodName = str;
            this.mBitmapId = remoteViews.mBitmapCache.getBitmapId(bitmap);
        }

        BitmapReflectionAction(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mMethodName = parcel.readString8();
            this.mBitmapId = parcel.readInt();
            this.mBitmap = remoteViews.mBitmapCache.getBitmapForId(this.mBitmapId);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeString8(this.mMethodName);
            parcel.writeInt(this.mBitmapId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException {
            new ReflectionAction(this.mViewId, this.mMethodName, 12, this.mBitmap).apply(view, viewGroup, actionApplyParams);
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData hierarchyRootData) {
            this.mBitmapId = hierarchyRootData.mBitmapCache.getBitmapId(this.mBitmap);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, this.mMethodName);
            protoOutputStream.write(1120986464259L, this.mBitmapId);
            protoOutputStream.end(jStart);
        }
    }

    private PendingResources<Action> createFromBitmapReflectionActionFromProto(ProtoInputStream protoInputStream) throws Exception {
        final LongSparseArray longSparseArray = new LongSparseArray();
        long jStart = protoInputStream.start(1146756268034L);
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
            } else if (fieldNumber == 3) {
                longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
            } else {
                Log.w(LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        protoInputStream.end(jStart);
        checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L});
        return new PendingResources() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda2
            @Override // android.widget.RemoteViews.PendingResources
            public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                return this.f$0.lambda$createFromBitmapReflectionActionFromProto$4(longSparseArray, context, resources, hierarchyRootData, i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Action lambda$createFromBitmapReflectionActionFromProto$4(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
        return new BitmapReflectionAction(this, getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), (String) longSparseArray.get(1138166333442L), hierarchyRootData.mBitmapCache.getBitmapForId(((Integer) longSparseArray.get(1120986464259L, 0)).intValue()));
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

        BaseReflectionAction(int i, String str, int i2) {
            super();
            this.mViewId = i;
            this.mMethodName = str;
            this.mType = i2;
        }

        BaseReflectionAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mMethodName = parcel.readString8();
            this.mType = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeString8(this.mMethodName);
            parcel.writeInt(this.mType);
        }

        @Override // android.widget.RemoteViews.Action
        public final void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            Class parameterType = RemoteViews.getParameterType(this.mType);
            if (parameterType == null) {
                throw new ActionException("bad type: " + this.mType);
            }
            try {
                (void) RemoteViews.getMethod(viewFindViewById, this.mMethodName, parameterType, false).invoke(viewFindViewById, getParameterValue(viewFindViewById));
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public final Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException {
            Bitmap bitmap;
            View viewFindViewById = viewTree.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return RemoteViews.ACTION_NOOP;
            }
            Class parameterType = RemoteViews.getParameterType(this.mType);
            if (parameterType == null) {
                throw new ActionException("bad type: " + this.mType);
            }
            Object parameterValue = getParameterValue(viewFindViewById);
            try {
                MethodHandle method = RemoteViews.getMethod(viewFindViewById, this.mMethodName, parameterType, true);
                if (parameterValue instanceof Bitmap) {
                    ((Bitmap) parameterValue).prepareToDraw();
                }
                if (parameterValue instanceof Icon) {
                    Icon icon = (Icon) parameterValue;
                    if ((icon.getType() == 1 || icon.getType() == 5) && (bitmap = icon.getBitmap()) != null) {
                        bitmap.prepareToDraw();
                    }
                }
                if (method == null) {
                    return this;
                }
                Runnable runnableInvoke = (Runnable) method.invoke(viewFindViewById, parameterValue);
                if (runnableInvoke == null) {
                    return RemoteViews.ACTION_NOOP;
                }
                if (runnableInvoke instanceof ViewStub.ViewReplaceRunnable) {
                    viewTree.createTree();
                    viewTree.findViewTreeById(this.mViewId).replaceView(((ViewStub.ViewReplaceRunnable) runnableInvoke).view);
                }
                return new RunnableAction(runnableInvoke);
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public final int mergeBehavior() {
            return this.mMethodName.equals("smoothScrollBy") ? 1 : 0;
        }

        @Override // android.widget.RemoteViews.Action
        public final String getUniqueKey() {
            return super.getUniqueKey() + this.mMethodName + this.mType;
        }

        @Override // android.widget.RemoteViews.Action
        public final boolean prefersAsyncApply() {
            int i = this.mType;
            return i == 11 || i == 16;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> consumer) {
            Icon icon;
            int i = this.mType;
            if (i != 11) {
                if (i == 16 && (icon = (Icon) getParameterValue(null)) != null) {
                    RemoteViews.visitIconUri(icon, consumer);
                    return;
                }
                return;
            }
            Uri uri = (Uri) getParameterValue(null);
            if (uri != null) {
                consumer.accept(uri);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void visitIcons(Consumer<Icon> consumer) throws ActionException {
            if (this.mType == 16) {
                Object parameterValue = getParameterValue(null);
                if (parameterValue instanceof Icon) {
                    consumer.accept((Icon) parameterValue);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ReflectionAction extends BaseReflectionAction {
        Object mValue;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 2;
        }

        ReflectionAction(int i, String str, int i2, Object obj) {
            super(i, str, i2);
            this.mValue = obj;
        }

        ReflectionAction(Parcel parcel) {
            super(parcel);
            int i = this.mType;
            if (i != 30) {
                switch (i) {
                    case 1:
                        this.mValue = Boolean.valueOf(parcel.readBoolean());
                        break;
                    case 2:
                        this.mValue = Byte.valueOf(parcel.readByte());
                        break;
                    case 3:
                        this.mValue = Short.valueOf((short) parcel.readInt());
                        break;
                    case 4:
                        this.mValue = Integer.valueOf(parcel.readInt());
                        break;
                    case 5:
                        this.mValue = Long.valueOf(parcel.readLong());
                        break;
                    case 6:
                        this.mValue = Float.valueOf(parcel.readFloat());
                        break;
                    case 7:
                        this.mValue = Double.valueOf(parcel.readDouble());
                        break;
                    case 8:
                        this.mValue = Character.valueOf((char) parcel.readInt());
                        break;
                    case 9:
                        this.mValue = parcel.readString8();
                        break;
                    case 10:
                        this.mValue = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
                        break;
                    case 11:
                        this.mValue = parcel.readTypedObject(Uri.CREATOR);
                        break;
                    case 12:
                        this.mValue = parcel.readTypedObject(Bitmap.CREATOR);
                        break;
                    case 13:
                        if (parcel.hasReadWriteHelper()) {
                            this.mValue = parcel.readBundle();
                            break;
                        } else {
                            parcel.setReadWriteHelper(RemoteViews.ALTERNATIVE_DEFAULT);
                            this.mValue = parcel.readBundle();
                            parcel.setReadWriteHelper(null);
                            break;
                        }
                    case 14:
                        this.mValue = parcel.readTypedObject(Intent.CREATOR);
                        break;
                    case 15:
                        this.mValue = parcel.readTypedObject(ColorStateList.CREATOR);
                        break;
                    case 16:
                        this.mValue = parcel.readTypedObject(Icon.CREATOR);
                        break;
                    case 17:
                        this.mValue = BlendMode.fromValue(parcel.readInt());
                        break;
                }
                return;
            }
            this.mValue = parcel.readTypedObject(SemBlurInfo.CREATOR);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            int i2 = this.mType;
            if (i2 != 30) {
                switch (i2) {
                    case 1:
                        parcel.writeBoolean(((Boolean) this.mValue).booleanValue());
                        break;
                    case 2:
                        parcel.writeByte(((Byte) this.mValue).byteValue());
                        break;
                    case 3:
                        parcel.writeInt(((Short) this.mValue).shortValue());
                        break;
                    case 4:
                        parcel.writeInt(((Integer) this.mValue).intValue());
                        break;
                    case 5:
                        parcel.writeLong(((Long) this.mValue).longValue());
                        break;
                    case 6:
                        parcel.writeFloat(((Float) this.mValue).floatValue());
                        break;
                    case 7:
                        parcel.writeDouble(((Double) this.mValue).doubleValue());
                        break;
                    case 8:
                        parcel.writeInt(((Character) this.mValue).charValue());
                        break;
                    case 9:
                        parcel.writeString8((String) this.mValue);
                        break;
                    case 10:
                        TextUtils.writeToParcel((CharSequence) this.mValue, parcel, i);
                        break;
                    case 13:
                        parcel.writeBundle((Bundle) this.mValue);
                        break;
                    case 17:
                        parcel.writeInt(BlendMode.toValue((BlendMode) this.mValue));
                        break;
                }
                return;
            }
            parcel.writeTypedObject((Parcelable) this.mValue, i);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            return this.mValue;
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268038L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, this.mMethodName);
            protoOutputStream.write(1120986464259L, this.mType);
            if (this.mValue != null) {
                switch (this.mType) {
                    case 1:
                        protoOutputStream.write(1133871366148L, ((Boolean) this.mValue).booleanValue());
                        break;
                    case 2:
                        protoOutputStream.write(1151051235333L, new byte[]{((Byte) this.mValue).byteValue()});
                        break;
                    case 3:
                        protoOutputStream.write(1120986464262L, (int) ((Short) this.mValue).shortValue());
                        break;
                    case 4:
                        protoOutputStream.write(1120986464263L, ((Integer) this.mValue).intValue());
                        break;
                    case 5:
                        protoOutputStream.write(1112396529672L, ((Long) this.mValue).longValue());
                        break;
                    case 6:
                        protoOutputStream.write(1108101562377L, ((Float) this.mValue).floatValue());
                        break;
                    case 7:
                        protoOutputStream.write(1103806595082L, ((Double) this.mValue).doubleValue());
                        break;
                    case 8:
                        protoOutputStream.write(1120986464267L, (int) ((Character) this.mValue).charValue());
                        break;
                    case 9:
                        protoOutputStream.write(1138166333452L, (String) this.mValue);
                        break;
                    case 10:
                        long jStart2 = protoOutputStream.start(1146756268045L);
                        RemoteViewsSerializers.writeCharSequenceToProto(protoOutputStream, (CharSequence) this.mValue);
                        protoOutputStream.end(jStart2);
                        break;
                    case 11:
                        protoOutputStream.write(1138166333454L, ((Uri) this.mValue).toString());
                        break;
                    case 12:
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ((Bitmap) this.mValue).compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, byteArrayOutputStream);
                        protoOutputStream.write(1151051235343L, byteArrayOutputStream.toByteArray());
                        break;
                    case 15:
                        RemoteViews.writeColorStateListToProto(protoOutputStream, (ColorStateList) this.mValue, 1146756268048L);
                        break;
                    case 16:
                        RemoteViews.writeIconToProto(protoOutputStream, resources, (Icon) this.mValue, 1146756268049L);
                        break;
                    case 17:
                        protoOutputStream.write(1120986464274L, BlendMode.toValue((BlendMode) this.mValue));
                        break;
                }
            }
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268038L);
            while (protoInputStream.nextField() != -1) {
                switch (protoInputStream.getFieldNumber()) {
                    case 1:
                        longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                        break;
                    case 2:
                        longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                        break;
                    case 3:
                        longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                        break;
                    case 4:
                        longSparseArray.put(1133871366148L, Boolean.valueOf(protoInputStream.readBoolean(1133871366148L)));
                        break;
                    case 5:
                        longSparseArray.put(1151051235333L, protoInputStream.readBytes(1151051235333L));
                        break;
                    case 6:
                        longSparseArray.put(1120986464262L, Short.valueOf((short) protoInputStream.readInt(1120986464262L)));
                        break;
                    case 7:
                        longSparseArray.put(1120986464263L, Integer.valueOf(protoInputStream.readInt(1120986464263L)));
                        break;
                    case 8:
                        longSparseArray.put(1112396529672L, Long.valueOf(protoInputStream.readLong(1112396529672L)));
                        break;
                    case 9:
                        longSparseArray.put(1108101562377L, Float.valueOf(protoInputStream.readFloat(1108101562377L)));
                        break;
                    case 10:
                        longSparseArray.put(1103806595082L, Double.valueOf(protoInputStream.readDouble(1103806595082L)));
                        break;
                    case 11:
                        longSparseArray.put(1120986464267L, Character.valueOf((char) protoInputStream.readInt(1120986464267L)));
                        break;
                    case 12:
                        longSparseArray.put(1138166333452L, protoInputStream.readString(1138166333452L));
                        break;
                    case 13:
                        longSparseArray.put(1146756268045L, RemoteViews.createCharSequenceFromProto(protoInputStream, 1146756268045L));
                        break;
                    case 14:
                        longSparseArray.put(1138166333454L, protoInputStream.readString(1138166333454L));
                        break;
                    case 15:
                        byte[] bytes = protoInputStream.readBytes(1151051235343L);
                        longSparseArray.put(1151051235343L, BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        break;
                    case 16:
                        longSparseArray.put(1146756268048L, RemoteViews.createColorStateListFromProto(protoInputStream, 1146756268048L));
                        break;
                    case 17:
                        longSparseArray.put(1146756268049L, RemoteViews.createIconFromProto(protoInputStream, 1146756268049L));
                        break;
                    case 18:
                        longSparseArray.put(1120986464274L, BlendMode.fromValue(protoInputStream.readInt(1120986464274L)));
                        break;
                    default:
                        Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                        break;
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L, 1120986464259L});
            return new PendingResources() { // from class: android.widget.RemoteViews$ReflectionAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.ReflectionAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            int asIdentifier = RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L);
            int iIntValue = ((Integer) longSparseArray.get(1120986464259L)).intValue();
            Object objValueOf = null;
            objValueOf = null;
            switch (iIntValue) {
                case 1:
                    Boolean bool = (Boolean) longSparseArray.get(1133871366148L, false);
                    bool.booleanValue();
                    objValueOf = bool;
                    break;
                case 2:
                    byte[] bArr = (byte[]) longSparseArray.get(1151051235333L);
                    if (bArr != null && bArr.length > 0) {
                        objValueOf = Byte.valueOf(bArr[0]);
                        break;
                    }
                    break;
                case 3:
                    Short sh = (Short) longSparseArray.get(1120986464262L, 0);
                    sh.shortValue();
                    objValueOf = sh;
                    break;
                case 4:
                    Integer num = (Integer) longSparseArray.get(1120986464263L, 0);
                    num.intValue();
                    objValueOf = num;
                    break;
                case 5:
                    Long l = (Long) longSparseArray.get(1112396529672L, 0);
                    l.longValue();
                    objValueOf = l;
                    break;
                case 6:
                    Float f = (Float) longSparseArray.get(1108101562377L, 0);
                    f.floatValue();
                    objValueOf = f;
                    break;
                case 7:
                    Double d = (Double) longSparseArray.get(1103806595082L, 0);
                    d.doubleValue();
                    objValueOf = d;
                    break;
                case 8:
                    Character ch = (Character) longSparseArray.get(1120986464267L, 0);
                    ch.charValue();
                    objValueOf = ch;
                    break;
                case 9:
                    objValueOf = (String) longSparseArray.get(1138166333452L);
                    break;
                case 10:
                    objValueOf = (CharSequence) longSparseArray.get(1146756268045L);
                    break;
                case 11:
                    objValueOf = Uri.parse((String) longSparseArray.get(1138166333454L));
                    break;
                case 12:
                    objValueOf = (Bitmap) longSparseArray.get(1151051235343L);
                    break;
                case 13:
                case 14:
                default:
                    return null;
                case 15:
                    objValueOf = (ColorStateList) longSparseArray.get(1146756268048L);
                    break;
                case 16:
                    objValueOf = ((PendingResources) longSparseArray.get(1146756268049L)).create(context, resources, hierarchyRootData, i);
                    break;
                case 17:
                    objValueOf = (BlendMode) longSparseArray.get(1120986464274L);
                    break;
            }
            return new ReflectionAction(asIdentifier, (String) longSparseArray.get(1138166333442L), iIntValue, objValueOf);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ResourceReflectionAction extends BaseReflectionAction {
        static final int COLOR_RESOURCE = 2;
        static final int DIMEN_RESOURCE = 1;
        static final int INTEGER_RESOURCE = 4;
        static final int STRING_RESOURCE = 3;
        private final int mResId;
        private final int mResourceType;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 24;
        }

        ResourceReflectionAction(int i, String str, int i2, int i3, int i4) {
            super(i, str, i2);
            this.mResourceType = i3;
            this.mResId = i4;
        }

        ResourceReflectionAction(Parcel parcel) {
            super(parcel);
            this.mResourceType = parcel.readInt();
            this.mResId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mResourceType);
            parcel.writeInt(this.mResId);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            if (view == null) {
                return null;
            }
            Resources resources = view.getContext().getResources();
            try {
                int i = this.mResourceType;
                int dimensionPixelSize = 0;
                if (i == 1) {
                    int i2 = this.mType;
                    if (i2 == 4) {
                        int i3 = this.mResId;
                        if (i3 != 0) {
                            dimensionPixelSize = resources.getDimensionPixelSize(i3);
                        }
                        return Integer.valueOf(dimensionPixelSize);
                    }
                    if (i2 == 6) {
                        int i4 = this.mResId;
                        return Float.valueOf(i4 == 0 ? 0.0f : resources.getDimension(i4));
                    }
                    throw new ActionException("dimen resources must be used as INT or FLOAT, not " + this.mType);
                }
                if (i == 2) {
                    int i5 = this.mType;
                    if (i5 == 4) {
                        if (this.mResId != 0) {
                            dimensionPixelSize = view.getContext().getColor(this.mResId);
                        }
                        return Integer.valueOf(dimensionPixelSize);
                    }
                    if (i5 == 15) {
                        if (this.mResId == 0) {
                            return null;
                        }
                        return view.getContext().getColorStateList(this.mResId);
                    }
                    throw new ActionException("color resources must be used as INT or COLOR_STATE_LIST, not " + this.mType);
                }
                if (i != 3) {
                    if (i == 4) {
                        if (this.mType == 4) {
                            int i6 = this.mResId;
                            if (i6 != 0) {
                                dimensionPixelSize = resources.getInteger(i6);
                            }
                            return Integer.valueOf(dimensionPixelSize);
                        }
                        throw new ActionException("integer resources must be used as INT, not " + this.mType);
                    }
                    throw new ActionException("unknown resource type: " + this.mResourceType);
                }
                int i7 = this.mType;
                if (i7 == 9) {
                    int i8 = this.mResId;
                    if (i8 == 0) {
                        return null;
                    }
                    return resources.getString(i8);
                }
                if (i7 == 10) {
                    int i9 = this.mResId;
                    if (i9 == 0) {
                        return null;
                    }
                    return resources.getText(i9);
                }
                throw new ActionException("string resources must be used as STRING or CHAR_SEQUENCE, not " + this.mType);
            } catch (ActionException e) {
                throw e;
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268040L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, this.mMethodName);
            protoOutputStream.write(1120986464261L, this.mType);
            protoOutputStream.write(1120986464259L, this.mResourceType);
            int i = this.mResId;
            if (i != 0) {
                protoOutputStream.write(1138166333444L, resources.getResourceName(i));
            }
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268040L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else if (fieldNumber == 4) {
                    longSparseArray.put(1138166333444L, protoInputStream.readString(1138166333444L));
                } else if (fieldNumber == 5) {
                    longSparseArray.put(1120986464261L, Integer.valueOf(protoInputStream.readInt(1120986464261L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L, 1120986464261L});
            return new PendingResources() { // from class: android.widget.RemoteViews$ResourceReflectionAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.ResourceReflectionAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            int i2;
            int asIdentifier;
            int asIdentifier2 = RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L);
            if (longSparseArray.indexOfKey(1138166333444L) >= 0) {
                asIdentifier = RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333444L);
                i2 = 0;
            } else {
                i2 = 0;
                asIdentifier = 0;
            }
            return new ResourceReflectionAction(asIdentifier2, (String) longSparseArray.get(1138166333442L), ((Integer) longSparseArray.get(1120986464261L)).intValue(), ((Integer) longSparseArray.get(1120986464259L, Integer.valueOf(i2))).intValue(), asIdentifier);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AttributeReflectionAction extends BaseReflectionAction {
        static final int COLOR_RESOURCE = 2;
        static final int DIMEN_RESOURCE = 1;
        static final int STRING_RESOURCE = 3;
        private final int mAttrId;
        private final int mResourceType;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 32;
        }

        AttributeReflectionAction(int i, String str, int i2, int i3, int i4) {
            super(i, str, i2);
            this.mResourceType = i3;
            this.mAttrId = i4;
        }

        AttributeReflectionAction(Parcel parcel) {
            super(parcel);
            this.mResourceType = parcel.readInt();
            this.mAttrId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mResourceType);
            parcel.writeInt(this.mAttrId);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(new int[]{this.mAttrId});
            try {
                try {
                    if (this.mAttrId != 0 && typedArrayObtainStyledAttributes.getType(0) == 0) {
                        throw new ActionException("Attribute 0x" + Integer.toHexString(this.mAttrId) + " is not defined");
                    }
                    int i = this.mResourceType;
                    if (i == 1) {
                        int i2 = this.mType;
                        if (i2 == 4) {
                            return Integer.valueOf(typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0));
                        }
                        if (i2 == 6) {
                            return Float.valueOf(typedArrayObtainStyledAttributes.getDimension(0, 0.0f));
                        }
                        throw new ActionException("dimen attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as INT or FLOAT, not " + this.mType);
                    }
                    if (i == 2) {
                        int i3 = this.mType;
                        if (i3 == 4) {
                            return Integer.valueOf(typedArrayObtainStyledAttributes.getColor(0, 0));
                        }
                        if (i3 == 15) {
                            return typedArrayObtainStyledAttributes.getColorStateList(0);
                        }
                        throw new ActionException("color attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as INT or COLOR_STATE_LIST, not " + this.mType);
                    }
                    if (i != 3) {
                        throw new ActionException("Unknown resource type: " + this.mResourceType);
                    }
                    int i4 = this.mType;
                    if (i4 == 9) {
                        return typedArrayObtainStyledAttributes.getString(0);
                    }
                    if (i4 == 10) {
                        return typedArrayObtainStyledAttributes.getText(0);
                    }
                    throw new ActionException("string attribute 0x" + Integer.toHexString(this.mAttrId) + " must be used as STRING or CHAR_SEQUENCE, not " + this.mType);
                } finally {
                    typedArrayObtainStyledAttributes.recycle();
                }
            } catch (ActionException e) {
                throw e;
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, this.mMethodName);
            protoOutputStream.write(1120986464259L, this.mType);
            protoOutputStream.write(1120986464260L, this.mResourceType);
            int i = this.mAttrId;
            if (i != 0) {
                protoOutputStream.write(1138166333445L, resources.getResourceName(i));
            }
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268033L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else if (fieldNumber == 4) {
                    longSparseArray.put(1120986464260L, Integer.valueOf(protoInputStream.readInt(1120986464260L)));
                } else if (fieldNumber == 5) {
                    longSparseArray.put(1138166333445L, protoInputStream.readString(1138166333445L));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L, 1120986464259L, 1120986464260L});
            return new PendingResources() { // from class: android.widget.RemoteViews$AttributeReflectionAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.AttributeReflectionAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new AttributeReflectionAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), (String) longSparseArray.get(1138166333442L), ((Integer) longSparseArray.get(1120986464259L)).intValue(), ((Integer) longSparseArray.get(1120986464260L)).intValue(), longSparseArray.indexOfKey(1138166333445L) >= 0 ? RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333445L) : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ComplexUnitDimensionReflectionAction extends BaseReflectionAction {
        private final int mUnit;
        private final float mValue;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 25;
        }

        ComplexUnitDimensionReflectionAction(int i, String str, int i2, float f, int i3) {
            super(i, str, i2);
            this.mValue = f;
            this.mUnit = i3;
        }

        ComplexUnitDimensionReflectionAction(Parcel parcel) {
            super(parcel);
            this.mValue = parcel.readFloat();
            this.mUnit = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.mValue);
            parcel.writeInt(this.mUnit);
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            if (view == null) {
                return null;
            }
            DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
            try {
                int iCreateComplexDimension = TypedValue.createComplexDimension(this.mValue, this.mUnit);
                int i = this.mType;
                if (i == 4) {
                    return Integer.valueOf(TypedValue.complexToDimensionPixelSize(iCreateComplexDimension, displayMetrics));
                }
                if (i == 6) {
                    return Float.valueOf(TypedValue.complexToDimension(iCreateComplexDimension, displayMetrics));
                }
                throw new ActionException("parameter type must be INT or FLOAT, not " + this.mType);
            } catch (ActionException e) {
                throw e;
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, this.mMethodName);
            protoOutputStream.write(1120986464259L, this.mType);
            protoOutputStream.write(1108101562372L, this.mValue);
            protoOutputStream.write(1120986464261L, this.mUnit);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268035L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else if (fieldNumber == 4) {
                    longSparseArray.put(1108101562372L, Float.valueOf(protoInputStream.readFloat(1108101562372L)));
                } else if (fieldNumber == 5) {
                    longSparseArray.put(1120986464261L, Integer.valueOf(protoInputStream.readInt(1120986464261L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L, 1120986464259L});
            return new PendingResources() { // from class: android.widget.RemoteViews$ComplexUnitDimensionReflectionAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.ComplexUnitDimensionReflectionAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new ComplexUnitDimensionReflectionAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), (String) longSparseArray.get(1138166333442L), ((Integer) longSparseArray.get(1120986464259L)).intValue(), ((Float) longSparseArray.get(1108101562372L, 0)).floatValue(), ((Integer) longSparseArray.get(1120986464261L, 0)).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class NightModeReflectionAction extends BaseReflectionAction {
        private final Object mDarkValue;
        private final Object mLightValue;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 30;
        }

        NightModeReflectionAction(int i, String str, int i2, Object obj, Object obj2) {
            super(i, str, i2);
            this.mLightValue = obj;
            this.mDarkValue = obj2;
        }

        NightModeReflectionAction(Parcel parcel) {
            super(parcel);
            int i = this.mType;
            if (i == 4) {
                this.mLightValue = Integer.valueOf(parcel.readInt());
                this.mDarkValue = Integer.valueOf(parcel.readInt());
            } else if (i == 15) {
                this.mLightValue = parcel.readTypedObject(ColorStateList.CREATOR);
                this.mDarkValue = parcel.readTypedObject(ColorStateList.CREATOR);
            } else if (i == 16) {
                this.mLightValue = parcel.readTypedObject(Icon.CREATOR);
                this.mDarkValue = parcel.readTypedObject(Icon.CREATOR);
            } else {
                throw new ActionException("Unexpected night mode action type: " + this.mType);
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            int i2 = this.mType;
            if (i2 == 4) {
                parcel.writeInt(((Integer) this.mLightValue).intValue());
                parcel.writeInt(((Integer) this.mDarkValue).intValue());
            } else if (i2 == 15 || i2 == 16) {
                parcel.writeTypedObject((Parcelable) this.mLightValue, i);
                parcel.writeTypedObject((Parcelable) this.mDarkValue, i);
            }
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction
        protected Object getParameterValue(View view) throws ActionException {
            if (view == null) {
                return null;
            }
            return view.getResources().getConfiguration().isNightModeActive() ? this.mDarkValue : this.mLightValue;
        }

        @Override // android.widget.RemoteViews.BaseReflectionAction, android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> consumer) {
            if (this.mType == 16) {
                RemoteViews.visitIconUri((Icon) this.mDarkValue, consumer);
                RemoteViews.visitIconUri((Icon) this.mLightValue, consumer);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268037L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, this.mMethodName);
            protoOutputStream.write(1120986464259L, this.mType);
            int i = this.mType;
            if (i == 4) {
                protoOutputStream.write(1120986464262L, ((Integer) this.mLightValue).intValue());
                protoOutputStream.write(1120986464265L, ((Integer) this.mDarkValue).intValue());
            } else if (i == 15) {
                RemoteViews.writeColorStateListToProto(protoOutputStream, (ColorStateList) this.mLightValue, 1146756268037L);
                RemoteViews.writeColorStateListToProto(protoOutputStream, (ColorStateList) this.mDarkValue, 1146756268040L);
            } else if (i == 16) {
                RemoteViews.writeIconToProto(protoOutputStream, resources, (Icon) this.mLightValue, 1146756268036L);
                RemoteViews.writeIconToProto(protoOutputStream, resources, (Icon) this.mDarkValue, 1146756268039L);
            }
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268037L);
            while (protoInputStream.nextField() != -1) {
                switch (protoInputStream.getFieldNumber()) {
                    case 1:
                        longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                        break;
                    case 2:
                        longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                        break;
                    case 3:
                        longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                        break;
                    case 4:
                        longSparseArray.put(1146756268036L, RemoteViews.createIconFromProto(protoInputStream, 1146756268036L));
                        break;
                    case 5:
                        longSparseArray.put(1146756268037L, RemoteViews.createColorStateListFromProto(protoInputStream, 1146756268037L));
                        break;
                    case 6:
                        longSparseArray.put(1120986464262L, Integer.valueOf(protoInputStream.readInt(1120986464262L)));
                        break;
                    case 7:
                        longSparseArray.put(1146756268039L, RemoteViews.createIconFromProto(protoInputStream, 1146756268039L));
                        break;
                    case 8:
                        longSparseArray.put(1146756268040L, RemoteViews.createColorStateListFromProto(protoInputStream, 1146756268040L));
                        break;
                    case 9:
                        longSparseArray.put(1120986464265L, Integer.valueOf(protoInputStream.readInt(1120986464265L)));
                        break;
                    default:
                        Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                        break;
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L, 1120986464259L});
            return new PendingResources() { // from class: android.widget.RemoteViews$NightModeReflectionAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.NightModeReflectionAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            int asIdentifier = RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L);
            String str = (String) longSparseArray.get(1138166333442L);
            int iIntValue = ((Integer) longSparseArray.get(1120986464259L)).intValue();
            if (iIntValue == 4) {
                Integer num = (Integer) longSparseArray.get(1120986464262L, 0);
                num.intValue();
                Integer num2 = (Integer) longSparseArray.get(1120986464265L, 0);
                num2.intValue();
                return new NightModeReflectionAction(asIdentifier, str, iIntValue, num, num2);
            }
            if (iIntValue == 15) {
                return new NightModeReflectionAction(asIdentifier, str, iIntValue, (ColorStateList) longSparseArray.get(1146756268037L), (ColorStateList) longSparseArray.get(1146756268040L));
            }
            if (iIntValue == 16) {
                PendingResources pendingResources = (PendingResources) longSparseArray.get(1146756268036L);
                PendingResources pendingResources2 = (PendingResources) longSparseArray.get(1146756268039L);
                return new NightModeReflectionAction(asIdentifier, str, iIntValue, pendingResources != null ? (Icon) pendingResources.create(context, resources, hierarchyRootData, i) : null, pendingResources2 != null ? (Icon) pendingResources2.create(context, resources, hierarchyRootData, i) : null);
            }
            throw new RuntimeException("Unknown parameterType: " + iIntValue);
        }
    }

    private static final class RunnableAction extends RuntimeAction {
        private final Runnable mRunnable;

        RunnableAction(Runnable runnable) {
            super();
            this.mRunnable = runnable;
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            this.mRunnable.run();
        }
    }

    void setNotRoot() {
        this.mIsRoot = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasStableId(View view) {
        return view.getTag(R.id.remote_views_stable_id) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getStableId(View view) {
        Integer num = (Integer) view.getTag(R.id.remote_views_stable_id);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setStableId(View view, int i) {
        view.setTagInternal(R.id.remote_views_stable_id, Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getNextRecyclableChild(ViewGroup viewGroup) {
        Integer num = (Integer) viewGroup.getTag(R.id.remote_views_next_child);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    private static int getViewLayoutId(View view) {
        return ((Integer) view.getTag(16908312)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setNextRecyclableChild(ViewGroup viewGroup, int i, int i2) {
        if (i < 0 || i >= i2) {
            viewGroup.setTagInternal(R.id.remote_views_next_child, -1);
        } else {
            viewGroup.setTagInternal(R.id.remote_views_next_child, Integer.valueOf(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finalizeViewRecycling(ViewGroup viewGroup) throws Resources.NotFoundException {
        int nextRecyclableChild = getNextRecyclableChild(viewGroup);
        if (nextRecyclableChild >= 0 && nextRecyclableChild < viewGroup.getChildCount()) {
            viewGroup.removeViews(nextRecyclableChild, viewGroup.getChildCount() - nextRecyclableChild);
        }
        setNextRecyclableChild(viewGroup, -1, 0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof ViewGroup) && !childAt.isRootNamespace()) {
                finalizeViewRecycling((ViewGroup) childAt);
            }
        }
    }

    private class ViewObjectAnimatorAction extends Action {
        private int mAnimatorId;
        private boolean mIsAnimationEnd;
        private final int mViewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 106;
        }

        public ViewObjectAnimatorAction(RemoteViews remoteViews, int i, int i2) {
            super();
            this.mViewId = i;
            this.mAnimatorId = i2;
            this.mIsAnimationEnd = false;
        }

        public ViewObjectAnimatorAction(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mAnimatorId = parcel.readInt();
            this.mIsAnimationEnd = parcel.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mAnimatorId);
            parcel.writeBoolean(this.mIsAnimationEnd);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            AnimatorSet animatorSet;
            if (view == null || this.mAnimatorId == -1) {
                return;
            }
            Context context = view.getContext();
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null || (animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, this.mAnimatorId)) == null) {
                return;
            }
            animatorSet.setTarget(viewFindViewById);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: android.widget.RemoteViews.ViewObjectAnimatorAction.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ViewObjectAnimatorAction.this.mIsAnimationEnd = true;
                }
            });
            if (this.mIsAnimationEnd) {
                animatorSet.setDuration(0L);
            }
            animatorSet.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ViewGroupActionAdd extends Action {
        static final int NO_ID = -1;
        private int mIndex;
        private RemoteViews mNestedViews;
        private int mStableId;

        static /* synthetic */ void lambda$initActionAsync$2() {
        }

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 4;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }

        ViewGroupActionAdd(RemoteViews remoteViews, int i, RemoteViews remoteViews2) {
            this(i, remoteViews2, -1, -1);
        }

        ViewGroupActionAdd(RemoteViews remoteViews, int i, RemoteViews remoteViews2, int i2) {
            this(i, remoteViews2, i2, -1);
        }

        ViewGroupActionAdd(int i, RemoteViews remoteViews, int i2, int i3) {
            super();
            this.mViewId = i;
            this.mNestedViews = remoteViews;
            this.mIndex = i2;
            this.mStableId = i3;
            remoteViews.configureAsChild(RemoteViews.this.getHierarchyRootData());
        }

        ViewGroupActionAdd(Parcel parcel, ApplicationInfo applicationInfo, int i) {
            super();
            this.mViewId = parcel.readInt();
            this.mIndex = parcel.readInt();
            this.mStableId = parcel.readInt();
            RemoteViews remoteViews = new RemoteViews(parcel, RemoteViews.this.getHierarchyRootData(), applicationInfo, i);
            this.mNestedViews = remoteViews;
            remoteViews.addFlags(RemoteViews.this.mApplyFlags);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mIndex);
            parcel.writeInt(this.mStableId);
            this.mNestedViews.writeToParcel(parcel, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void setHierarchyRootData(HierarchyRootData hierarchyRootData) {
            this.mNestedViews.configureAsChild(hierarchyRootData);
        }

        private int findViewIndexToRecycle(ViewGroup viewGroup, RemoteViews remoteViews) {
            for (int nextRecyclableChild = RemoteViews.getNextRecyclableChild(viewGroup); nextRecyclableChild < viewGroup.getChildCount(); nextRecyclableChild++) {
                if (RemoteViews.getStableId(viewGroup.getChildAt(nextRecyclableChild)) == this.mStableId) {
                    return nextRecyclableChild;
                }
            }
            return -1;
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws Resources.NotFoundException {
            int iFindViewIndexToRecycle;
            Context context = view.getContext();
            ViewGroup viewGroup2 = (ViewGroup) view.findViewById(this.mViewId);
            if (viewGroup2 == null || this.mNestedViews == null) {
                return;
            }
            int nextRecyclableChild = RemoteViews.getNextRecyclableChild(viewGroup2);
            if (RemoteViews.this.mAllowOtherRootParent) {
                this.mNestedViews.hidden_semSetAllowOtherRootParent(true, RemoteViews.this.mAppWidgetId);
            }
            RemoteViews remoteViewsToApply = this.mNestedViews.getRemoteViewsToApply(context);
            int i = RemoteViews.this.mApplyFlags & 6;
            if (i != 0) {
                remoteViewsToApply.addFlags(i);
            }
            if (nextRecyclableChild >= 0 && this.mStableId != -1 && (iFindViewIndexToRecycle = findViewIndexToRecycle(viewGroup2, remoteViewsToApply)) >= 0) {
                View childAt = viewGroup2.getChildAt(iFindViewIndexToRecycle);
                if (remoteViewsToApply.canRecycleView(childAt)) {
                    if (nextRecyclableChild < iFindViewIndexToRecycle) {
                        viewGroup2.removeViews(nextRecyclableChild, iFindViewIndexToRecycle - nextRecyclableChild);
                    }
                    RemoteViews.setNextRecyclableChild(viewGroup2, nextRecyclableChild + 1, viewGroup2.getChildCount());
                    remoteViewsToApply.reapplyNestedViews(context, childAt, viewGroup, actionApplyParams);
                    return;
                }
                viewGroup2.removeViews(nextRecyclableChild, (iFindViewIndexToRecycle - nextRecyclableChild) + 1);
            }
            View viewApply = remoteViewsToApply.apply(context, viewGroup2, viewGroup, (SizeF) null, actionApplyParams);
            int i2 = this.mStableId;
            if (i2 != -1) {
                RemoteViews.setStableId(viewApply, i2);
            }
            int i3 = this.mIndex;
            if (i3 < 0) {
                i3 = nextRecyclableChild;
            }
            viewGroup2.addView(viewApply, i3);
            if (nextRecyclableChild >= 0) {
                RemoteViews.setNextRecyclableChild(viewGroup2, nextRecyclableChild + 1, viewGroup2.getChildCount());
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            viewTree.createTree();
            ViewTree viewTreeFindViewTreeById = viewTree.findViewTreeById(this.mViewId);
            if (viewTreeFindViewTreeById == null || !(viewTreeFindViewTreeById.mRoot instanceof ViewGroup)) {
                return RemoteViews.ACTION_NOOP;
            }
            final ViewGroup viewGroup2 = (ViewGroup) viewTreeFindViewTreeById.mRoot;
            Context context = viewTree.mRoot.getContext();
            this.mNestedViews.addFlags(RemoteViews.this.mApplyFlags);
            final int nextRecyclableChild = RemoteViews.getNextRecyclableChild(viewGroup2);
            if (nextRecyclableChild >= 0 && this.mStableId != -1) {
                RemoteViews remoteViewsToApply = this.mNestedViews.getRemoteViewsToApply(context);
                final int iFindChildIndex = viewTreeFindViewTreeById.findChildIndex(nextRecyclableChild, new Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.lambda$initActionAsync$0((View) obj);
                    }
                });
                if (iFindChildIndex >= 0) {
                    ViewTree viewTree2 = (ViewTree) viewTreeFindViewTreeById.mChildren.get(iFindChildIndex);
                    if (remoteViewsToApply.canRecycleView(viewTree2.mRoot)) {
                        if (iFindChildIndex > nextRecyclableChild) {
                            viewTreeFindViewTreeById.removeChildren(nextRecyclableChild, iFindChildIndex - nextRecyclableChild);
                        }
                        RemoteViews.setNextRecyclableChild(viewGroup2, nextRecyclableChild + 1, viewTreeFindViewTreeById.mChildren.size());
                        final AsyncApplyTask internalAsyncApplyTask = remoteViewsToApply.getInternalAsyncApplyTask(context, viewGroup2, null, actionApplyParams, null, viewTree2.mRoot);
                        final ViewTree viewTreeDoInBackground = internalAsyncApplyTask.doInBackground(new Void[0]);
                        if (viewTreeDoInBackground == null) {
                            throw new ActionException(internalAsyncApplyTask.mError);
                        }
                        return new RuntimeAction(this) { // from class: android.widget.RemoteViews.ViewGroupActionAdd.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super();
                            }

                            @Override // android.widget.RemoteViews.Action
                            public void apply(View view, ViewGroup viewGroup3, ActionApplyParams actionApplyParams2) throws Resources.NotFoundException, ActionException {
                                internalAsyncApplyTask.onPostExecute(viewTreeDoInBackground);
                                int i = iFindChildIndex;
                                int i2 = nextRecyclableChild;
                                if (i > i2) {
                                    viewGroup2.removeViews(i2, i - i2);
                                }
                            }
                        };
                    }
                    viewTreeFindViewTreeById.removeChildren(nextRecyclableChild, (iFindChildIndex - nextRecyclableChild) + 1);
                    return insertNewView(context, viewTreeFindViewTreeById, actionApplyParams, new Runnable() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            ViewGroup viewGroup3 = viewGroup2;
                            int i = nextRecyclableChild;
                            viewGroup3.removeViews(i, (iFindChildIndex - i) + 1);
                        }
                    });
                }
            }
            return insertNewView(context, viewTreeFindViewTreeById, actionApplyParams, new Runnable() { // from class: android.widget.RemoteViews$ViewGroupActionAdd$$ExternalSyntheticLambda2
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

        private Action insertNewView(Context context, ViewTree viewTree, ActionApplyParams actionApplyParams, final Runnable runnable) {
            final ViewGroup viewGroup = (ViewGroup) viewTree.mRoot;
            int nextRecyclableChild = RemoteViews.getNextRecyclableChild(viewGroup);
            final AsyncApplyTask internalAsyncApplyTask = this.mNestedViews.getInternalAsyncApplyTask(context, viewGroup, null, actionApplyParams, null, null);
            final ViewTree viewTreeDoInBackground = internalAsyncApplyTask.doInBackground(new Void[0]);
            if (viewTreeDoInBackground == null) {
                throw new ActionException(internalAsyncApplyTask.mError);
            }
            if (this.mStableId != -1) {
                RemoteViews.setStableId(internalAsyncApplyTask.mResult, this.mStableId);
            }
            int i = this.mIndex;
            final int i2 = i >= 0 ? i : nextRecyclableChild;
            viewTree.addChild(viewTreeDoInBackground, i2);
            if (nextRecyclableChild >= 0) {
                RemoteViews.setNextRecyclableChild(viewGroup, nextRecyclableChild + 1, viewTree.mChildren.size());
            }
            return new RuntimeAction(this) { // from class: android.widget.RemoteViews.ViewGroupActionAdd.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(View view, ViewGroup viewGroup2, ActionApplyParams actionApplyParams2) {
                    internalAsyncApplyTask.onPostExecute(viewTreeDoInBackground);
                    runnable.run();
                    viewGroup.addView(internalAsyncApplyTask.mResult, i2);
                }
            };
        }

        @Override // android.widget.RemoteViews.Action
        public boolean prefersAsyncApply() {
            return this.mNestedViews.prefersAsyncApply();
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> consumer) {
            this.mNestedViews.visitUris(consumer);
        }

        @Override // android.widget.RemoteViews.Action
        public void visitIcons(Consumer<Icon> consumer) {
            this.mNestedViews.visitIcons(consumer);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            if (Flags.remoteViewsProto()) {
                long jStart = protoOutputStream.start(1146756268051L);
                protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
                protoOutputStream.write(1120986464259L, this.mIndex);
                protoOutputStream.write(1120986464260L, this.mStableId);
                long jStart2 = protoOutputStream.start(1146756268034L);
                this.mNestedViews.writePreviewToProto(context, protoOutputStream);
                protoOutputStream.end(jStart2);
                protoOutputStream.end(jStart);
            }
        }
    }

    private PendingResources<Action> createViewGroupActionAddFromProto(ProtoInputStream protoInputStream) throws Exception {
        final LongSparseArray longSparseArray = new LongSparseArray();
        long jStart = protoInputStream.start(1146756268051L);
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
            } else if (fieldNumber == 2) {
                long jStart2 = protoInputStream.start(1146756268034L);
                longSparseArray.put(1146756268034L, createFromProto(protoInputStream));
                protoInputStream.end(jStart2);
            } else if (fieldNumber == 3) {
                longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
            } else if (fieldNumber == 4) {
                longSparseArray.put(1120986464260L, Integer.valueOf(protoInputStream.readInt(1120986464260L)));
            } else {
                Log.w(LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        protoInputStream.end(jStart);
        checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1146756268034L});
        return new PendingResources() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda4
            @Override // android.widget.RemoteViews.PendingResources
            public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                return this.f$0.lambda$createViewGroupActionAddFromProto$5(longSparseArray, context, resources, hierarchyRootData, i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Action lambda$createViewGroupActionAddFromProto$5(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
        return new ViewGroupActionAdd(getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), (RemoteViews) ((PendingResources) longSparseArray.get(1146756268034L)).create(context, resources, hierarchyRootData, i), ((Integer) longSparseArray.get(1120986464259L, 0)).intValue(), ((Integer) longSparseArray.get(1120986464260L, 0)).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewGroupActionRemove extends Action {
        private static final int REMOVE_ALL_VIEWS_ID = -2;
        private int mViewIdToKeep;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 7;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }

        ViewGroupActionRemove(int i) {
            this(i, -2);
        }

        ViewGroupActionRemove(int i, int i2) {
            super();
            this.mViewId = i;
            this.mViewIdToKeep = i2;
        }

        ViewGroupActionRemove(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mViewIdToKeep = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mViewIdToKeep);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws Resources.NotFoundException {
            ViewGroup viewGroup2 = (ViewGroup) view.findViewById(this.mViewId);
            if (viewGroup2 == null) {
                return;
            }
            if (this.mViewIdToKeep == -2) {
                for (int childCount = viewGroup2.getChildCount() - 1; childCount >= 0; childCount--) {
                    if (!RemoteViews.hasStableId(viewGroup2.getChildAt(childCount))) {
                        viewGroup2.removeViewAt(childCount);
                    }
                }
                RemoteViews.setNextRecyclableChild(viewGroup2, 0, viewGroup2.getChildCount());
                return;
            }
            removeAllViewsExceptIdToKeep(viewGroup2);
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            viewTree.createTree();
            ViewTree viewTreeFindViewTreeById = viewTree.findViewTreeById(this.mViewId);
            if (viewTreeFindViewTreeById == null || !(viewTreeFindViewTreeById.mRoot instanceof ViewGroup)) {
                return RemoteViews.ACTION_NOOP;
            }
            final ViewGroup viewGroup2 = (ViewGroup) viewTreeFindViewTreeById.mRoot;
            if (this.mViewIdToKeep == -2) {
                viewTreeFindViewTreeById.mChildren.removeIf(new Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionRemove$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return RemoteViews.ViewGroupActionRemove.lambda$initActionAsync$0((RemoteViews.ViewTree) obj);
                    }
                });
                RemoteViews.setNextRecyclableChild(viewGroup2, 0, viewTreeFindViewTreeById.mChildren.size());
            } else {
                viewTreeFindViewTreeById.mChildren.removeIf(new Predicate() { // from class: android.widget.RemoteViews$ViewGroupActionRemove$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.lambda$initActionAsync$1((RemoteViews.ViewTree) obj);
                    }
                });
                if (viewTreeFindViewTreeById.mChildren.isEmpty()) {
                    viewTreeFindViewTreeById.mChildren = null;
                }
            }
            return new RuntimeAction() { // from class: android.widget.RemoteViews.ViewGroupActionRemove.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(View view, ViewGroup viewGroup3, ActionApplyParams actionApplyParams2) throws Resources.NotFoundException {
                    if (ViewGroupActionRemove.this.mViewIdToKeep == -2) {
                        for (int childCount = viewGroup2.getChildCount() - 1; childCount >= 0; childCount--) {
                            if (!RemoteViews.hasStableId(viewGroup2.getChildAt(childCount))) {
                                viewGroup2.removeViewAt(childCount);
                            }
                        }
                        return;
                    }
                    ViewGroupActionRemove.this.removeAllViewsExceptIdToKeep(viewGroup2);
                }
            };
        }

        static /* synthetic */ boolean lambda$initActionAsync$0(ViewTree viewTree) {
            return !RemoteViews.hasStableId(viewTree.mRoot);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$initActionAsync$1(ViewTree viewTree) {
            return viewTree.mRoot.getId() != this.mViewIdToKeep;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeAllViewsExceptIdToKeep(ViewGroup viewGroup) throws Resources.NotFoundException {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                if (viewGroup.getChildAt(childCount).getId() != this.mViewIdToKeep) {
                    viewGroup.removeViewAt(childCount);
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268052L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            int i = this.mViewIdToKeep;
            if (i != -2) {
                protoOutputStream.write(1138166333442L, resources.getResourceName(i));
            }
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268052L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$ViewGroupActionRemove$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.ViewGroupActionRemove.lambda$createFromProto$2(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$2(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new ViewGroupActionRemove(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), longSparseArray.indexOfKey(1138166333442L) >= 0 ? RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333442L) : -2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RemoveFromParentAction extends Action {
        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 23;
        }

        @Override // android.widget.RemoteViews.Action
        public int mergeBehavior() {
            return 1;
        }

        RemoveFromParentAction(int i) {
            super();
            this.mViewId = i;
        }

        RemoveFromParentAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null || viewFindViewById == view) {
                return;
            }
            ViewParent parent = viewFindViewById.getParent();
            if (parent instanceof ViewManager) {
                ((ViewManager) parent).removeView(viewFindViewById);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            viewTree.createTree();
            final ViewTree viewTreeFindViewTreeById = viewTree.findViewTreeById(this.mViewId);
            if (viewTreeFindViewTreeById == null || viewTreeFindViewTreeById == viewTree) {
                return RemoteViews.ACTION_NOOP;
            }
            ViewTree viewTreeFindViewTreeParentOf = viewTree.findViewTreeParentOf(viewTreeFindViewTreeById);
            if (viewTreeFindViewTreeParentOf == null || !(viewTreeFindViewTreeParentOf.mRoot instanceof ViewManager)) {
                return RemoteViews.ACTION_NOOP;
            }
            final ViewManager viewManager = (ViewManager) viewTreeFindViewTreeParentOf.mRoot;
            viewTreeFindViewTreeParentOf.mChildren.remove(viewTreeFindViewTreeById);
            return new RuntimeAction(this) { // from class: android.widget.RemoteViews.RemoveFromParentAction.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // android.widget.RemoteViews.Action
                public void apply(View view, ViewGroup viewGroup2, ActionApplyParams actionApplyParams2) {
                    viewManager.removeView(viewTreeFindViewTreeById.mRoot);
                }
            };
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268039L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268039L);
            while (protoInputStream.nextField() != -1) {
                if (protoInputStream.getFieldNumber() == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$RemoveFromParentAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.RemoveFromParentAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new RemoveFromParentAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TextViewDrawableAction extends Action {
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

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 11;
        }

        public TextViewDrawableAction(int i, boolean z, int i2, int i3, int i4, int i5) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = i;
            this.mIsRelative = z;
            this.mUseIcons = false;
            this.mD1 = i2;
            this.mD2 = i3;
            this.mD3 = i4;
            this.mD4 = i5;
        }

        public TextViewDrawableAction(int i, boolean z, Icon icon, Icon icon2, Icon icon3, Icon icon4) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = i;
            this.mIsRelative = z;
            this.mUseIcons = true;
            this.mI1 = icon;
            this.mI2 = icon2;
            this.mI3 = icon3;
            this.mI4 = icon4;
        }

        public TextViewDrawableAction(Parcel parcel) {
            super();
            this.mIsRelative = false;
            this.mUseIcons = false;
            this.mDrawablesLoaded = false;
            this.mViewId = parcel.readInt();
            this.mIsRelative = parcel.readInt() != 0;
            boolean z = parcel.readInt() != 0;
            this.mUseIcons = z;
            if (z) {
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
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            TextView textView = (TextView) view.findViewById(this.mViewId);
            if (textView == null) {
                return;
            }
            if (this.mDrawablesLoaded) {
                if (this.mIsRelative) {
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mId1, this.mId2, this.mId3, this.mId4);
                    return;
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(this.mId1, this.mId2, this.mId3, this.mId4);
                    return;
                }
            }
            if (this.mUseIcons) {
                Context context = textView.getContext();
                Icon icon = this.mI1;
                Drawable drawableLoadDrawable = icon == null ? null : icon.loadDrawable(context);
                Icon icon2 = this.mI2;
                Drawable drawableLoadDrawable2 = icon2 == null ? null : icon2.loadDrawable(context);
                Icon icon3 = this.mI3;
                Drawable drawableLoadDrawable3 = icon3 == null ? null : icon3.loadDrawable(context);
                Icon icon4 = this.mI4;
                Drawable drawableLoadDrawable4 = icon4 != null ? icon4.loadDrawable(context) : null;
                if (this.mIsRelative) {
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLoadDrawable, drawableLoadDrawable2, drawableLoadDrawable3, drawableLoadDrawable4);
                    return;
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(drawableLoadDrawable, drawableLoadDrawable2, drawableLoadDrawable3, drawableLoadDrawable4);
                    return;
                }
            }
            if (this.mIsRelative) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mD1, this.mD2, this.mD3, this.mD4);
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(this.mD1, this.mD2, this.mD3, this.mD4);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            TextViewDrawableAction textViewDrawableAction;
            TextView textView = (TextView) viewTree.findViewById(this.mViewId);
            if (textView == null) {
                return RemoteViews.ACTION_NOOP;
            }
            if (this.mUseIcons) {
                textViewDrawableAction = new TextViewDrawableAction(this.mViewId, this.mIsRelative, this.mI1, this.mI2, this.mI3, this.mI4);
            } else {
                textViewDrawableAction = new TextViewDrawableAction(this.mViewId, this.mIsRelative, this.mD1, this.mD2, this.mD3, this.mD4);
            }
            textViewDrawableAction.mDrawablesLoaded = true;
            Context context = textView.getContext();
            if (this.mUseIcons) {
                Icon icon = this.mI1;
                textViewDrawableAction.mId1 = icon == null ? null : icon.loadDrawable(context);
                Icon icon2 = this.mI2;
                textViewDrawableAction.mId2 = icon2 == null ? null : icon2.loadDrawable(context);
                Icon icon3 = this.mI3;
                textViewDrawableAction.mId3 = icon3 == null ? null : icon3.loadDrawable(context);
                Icon icon4 = this.mI4;
                textViewDrawableAction.mId4 = icon4 != null ? icon4.loadDrawable(context) : null;
                return textViewDrawableAction;
            }
            int i = this.mD1;
            textViewDrawableAction.mId1 = i == 0 ? null : context.getDrawable(i);
            int i2 = this.mD2;
            textViewDrawableAction.mId2 = i2 == 0 ? null : context.getDrawable(i2);
            int i3 = this.mD3;
            textViewDrawableAction.mId3 = i3 == 0 ? null : context.getDrawable(i3);
            int i4 = this.mD4;
            textViewDrawableAction.mId4 = i4 != 0 ? context.getDrawable(i4) : null;
            return textViewDrawableAction;
        }

        @Override // android.widget.RemoteViews.Action
        public boolean prefersAsyncApply() {
            return this.mUseIcons;
        }

        @Override // android.widget.RemoteViews.Action
        public void visitUris(Consumer<Uri> consumer) {
            if (this.mUseIcons) {
                RemoteViews.visitIconUri(this.mI1, consumer);
                RemoteViews.visitIconUri(this.mI2, consumer);
                RemoteViews.visitIconUri(this.mI3, consumer);
                RemoteViews.visitIconUri(this.mI4, consumer);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268049L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1133871366146L, this.mIsRelative);
            if (this.mUseIcons) {
                long jStart2 = protoOutputStream.start(1146756268036L);
                Icon icon = this.mI1;
                if (icon != null) {
                    RemoteViews.writeIconToProto(protoOutputStream, resources, icon, 1146756268033L);
                }
                Icon icon2 = this.mI2;
                if (icon2 != null) {
                    RemoteViews.writeIconToProto(protoOutputStream, resources, icon2, 1146756268034L);
                }
                Icon icon3 = this.mI3;
                if (icon3 != null) {
                    RemoteViews.writeIconToProto(protoOutputStream, resources, icon3, 1146756268035L);
                }
                Icon icon4 = this.mI4;
                if (icon4 != null) {
                    RemoteViews.writeIconToProto(protoOutputStream, resources, icon4, 1146756268036L);
                }
                protoOutputStream.end(jStart2);
            } else {
                long jStart3 = protoOutputStream.start(1146756268035L);
                int i = this.mD1;
                if (i != 0) {
                    protoOutputStream.write(1138166333441L, resources.getResourceName(i));
                }
                int i2 = this.mD2;
                if (i2 != 0) {
                    protoOutputStream.write(1138166333442L, resources.getResourceName(i2));
                }
                int i3 = this.mD3;
                if (i3 != 0) {
                    protoOutputStream.write(1138166333443L, resources.getResourceName(i3));
                }
                int i4 = this.mD4;
                if (i4 != 0) {
                    protoOutputStream.write(1138166333444L, resources.getResourceName(i4));
                }
                protoOutputStream.end(jStart3);
            }
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long j = 1146756268036L;
            longSparseArray.put(1146756268036L, new SparseArray());
            long j2 = 1146756268035L;
            longSparseArray.put(1146756268035L, new SparseArray());
            long jStart = protoInputStream.start(1146756268049L);
            while (true) {
                int i = -1;
                int i2 = 1;
                if (protoInputStream.nextField() != -1) {
                    int fieldNumber = protoInputStream.getFieldNumber();
                    if (fieldNumber != 1) {
                        int i3 = 2;
                        if (fieldNumber != 2) {
                            int i4 = 4;
                            if (fieldNumber == 3) {
                                long jStart2 = protoInputStream.start(j2);
                                while (protoInputStream.nextField() != -1) {
                                    int fieldNumber2 = protoInputStream.getFieldNumber();
                                    if (fieldNumber2 != i2) {
                                        if (fieldNumber2 == 2) {
                                            ((SparseArray) longSparseArray.get(1146756268035L)).put(2, protoInputStream.readString(1138166333442L));
                                        } else if (fieldNumber2 == 3) {
                                            ((SparseArray) longSparseArray.get(1146756268035L)).put(3, protoInputStream.readString(1138166333443L));
                                        } else if (fieldNumber2 == i4) {
                                            ((SparseArray) longSparseArray.get(1146756268035L)).put(i4, protoInputStream.readString(1138166333444L));
                                        } else {
                                            Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                                        }
                                        i2 = 1;
                                    } else {
                                        ((SparseArray) longSparseArray.get(1146756268035L)).put(1, protoInputStream.readString(1138166333441L));
                                        i2 = 1;
                                        i4 = 4;
                                    }
                                }
                                protoInputStream.end(jStart2);
                                j2 = 1146756268035L;
                            } else if (fieldNumber == 4) {
                                long jStart3 = protoInputStream.start(j);
                                while (protoInputStream.nextField() != i) {
                                    int fieldNumber3 = protoInputStream.getFieldNumber();
                                    if (fieldNumber3 == i2) {
                                        i2 = 1;
                                        ((SparseArray) longSparseArray.get(j)).put(1, RemoteViews.createIconFromProto(protoInputStream, 1146756268033L));
                                        i = -1;
                                    } else if (fieldNumber3 == i3) {
                                        ((SparseArray) longSparseArray.get(j)).put(2, RemoteViews.createIconFromProto(protoInputStream, 1146756268034L));
                                        i3 = 2;
                                        i = -1;
                                        i2 = 1;
                                    } else if (fieldNumber3 != 3) {
                                        if (fieldNumber3 == 4) {
                                            ((SparseArray) longSparseArray.get(j)).put(4, RemoteViews.createIconFromProto(protoInputStream, j));
                                        } else {
                                            Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                                        }
                                        i = -1;
                                    } else {
                                        ((SparseArray) longSparseArray.get(j)).put(3, RemoteViews.createIconFromProto(protoInputStream, 1146756268035L));
                                        i = -1;
                                        i2 = 1;
                                    }
                                    i3 = 2;
                                }
                                protoInputStream.end(jStart3);
                                j2 = 1146756268035L;
                            } else {
                                Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                            }
                        } else {
                            longSparseArray.put(1133871366146L, Boolean.valueOf(protoInputStream.readBoolean(1133871366146L)));
                        }
                    } else {
                        longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                    }
                    j = 1146756268036L;
                } else {
                    protoInputStream.end(jStart);
                    RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
                    return new PendingResources() { // from class: android.widget.RemoteViews$TextViewDrawableAction$$ExternalSyntheticLambda0
                        @Override // android.widget.RemoteViews.PendingResources
                        public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i5) {
                            return RemoteViews.TextViewDrawableAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i5);
                        }
                    };
                }
            }
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            int asIdentifier = RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L);
            SparseArray sparseArray = (SparseArray) longSparseArray.get(1146756268036L);
            SparseArray sparseArray2 = (SparseArray) longSparseArray.get(1146756268035L);
            boolean zBooleanValue = ((Boolean) longSparseArray.get(1133871366146L, false)).booleanValue();
            if (sparseArray.size() > 0) {
                return new TextViewDrawableAction(asIdentifier, zBooleanValue, (Icon) ((PendingResources) sparseArray.get(1)).create(context, resources, hierarchyRootData, i), (Icon) ((PendingResources) sparseArray.get(2)).create(context, resources, hierarchyRootData, i), (Icon) ((PendingResources) sparseArray.get(3)).create(context, resources, hierarchyRootData, i), (Icon) ((PendingResources) sparseArray.get(4)).create(context, resources, hierarchyRootData, i));
            }
            return new TextViewDrawableAction(asIdentifier, zBooleanValue, sparseArray2.contains(1) ? RemoteViews.getAsIdentifier(resources, (SparseArray<?>) sparseArray2, 1) : 0, sparseArray2.contains(2) ? RemoteViews.getAsIdentifier(resources, (SparseArray<?>) sparseArray2, 2) : 0, sparseArray2.contains(3) ? RemoteViews.getAsIdentifier(resources, (SparseArray<?>) sparseArray2, 3) : 0, sparseArray2.contains(4) ? RemoteViews.getAsIdentifier(resources, (SparseArray<?>) sparseArray2, 4) : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TextViewSizeAction extends Action {
        float mMaxFontScale;
        float mSize;
        int mSizeResId;
        int mUnits;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 13;
        }

        TextViewSizeAction(int i, int i2, float f) {
            super();
            this.mViewId = i;
            this.mUnits = i2;
            this.mSize = f;
            this.mSizeResId = 0;
            this.mMaxFontScale = 0.0f;
        }

        TextViewSizeAction(int i, int i2, float f, float f2) {
            super();
            this.mViewId = i;
            this.mUnits = i2;
            this.mSize = f;
            this.mSizeResId = 0;
            this.mMaxFontScale = f2;
        }

        TextViewSizeAction(int i, int i2, int i3, float f) {
            super();
            this.mViewId = i;
            this.mUnits = i2;
            this.mSize = 0.0f;
            this.mSizeResId = i3;
            this.mMaxFontScale = f;
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
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mUnits);
            parcel.writeFloat(this.mSize);
            parcel.writeInt(this.mSizeResId);
            parcel.writeFloat(this.mMaxFontScale);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            TextView textView = (TextView) view.findViewById(this.mViewId);
            if (textView == null) {
                return;
            }
            try {
                Resources resources = textView.getContext().getResources();
                if (this.mMaxFontScale != 0.0f && this.mUnits == 2) {
                    float f = resources.getConfiguration().fontScale;
                    float f2 = this.mMaxFontScale;
                    if (f > f2) {
                        int i = this.mSizeResId;
                        if (i != 0) {
                            textView.setTextSize(1, resources.getFloat(i) * this.mMaxFontScale);
                            return;
                        } else {
                            textView.setTextSize(1, this.mSize * f2);
                            return;
                        }
                    }
                }
                int i2 = this.mSizeResId;
                if (i2 != 0) {
                    textView.setTextSize(this.mUnits, resources.getFloat(i2));
                } else {
                    textView.setTextSize(this.mUnits, this.mSize);
                }
            } catch (Exception e) {
                Log.e(RemoteViews.LOG_TAG, "ex=" + e);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268050L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1120986464258L, this.mUnits);
            protoOutputStream.write(1108101562371L, this.mSize);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268050L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1120986464258L, Integer.valueOf(protoInputStream.readInt(1120986464258L)));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1108101562371L, Float.valueOf(protoInputStream.readFloat(1108101562371L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$TextViewSizeAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.TextViewSizeAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new TextViewSizeAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), ((Integer) longSparseArray.get(1120986464258L, 0)).intValue(), ((Float) longSparseArray.get(1108101562371L, 0)).floatValue());
        }
    }

    private static class SemSetTextViewTextResourceAction extends Action {
        int mResid;
        Bundle mSpans;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 110;
        }

        SemSetTextViewTextResourceAction(int i, int i2, Bundle bundle) {
            super();
            this.mViewId = i;
            this.mResid = i2;
            this.mSpans = bundle;
        }

        SemSetTextViewTextResourceAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mResid = parcel.readInt();
            this.mSpans = parcel.readBundle();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mResid);
            parcel.writeBundle(this.mSpans);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            TextView textView = (TextView) view.findViewById(this.mViewId);
            if (textView == null) {
                return;
            }
            RemoteViews.setTextWithSpannableString(textView, textView.getContext().getResources().getString(this.mResid), this.mSpans);
        }

        @Override // android.widget.RemoteViews.Action
        public Action initActionAsync(ViewTree viewTree, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            return ((TextView) viewTree.findViewById(this.mViewId)) == null ? RemoteViews.ACTION_NOOP : this;
        }
    }

    private static class SetTextAppearanceAction extends Action {
        int mResId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 114;
        }

        SetTextAppearanceAction(int i, int i2) {
            super();
            this.mViewId = i;
            this.mResId = i2;
        }

        SetTextAppearanceAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mResId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mResId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws Resources.NotFoundException {
            TextView textView = (TextView) view.findViewById(this.mViewId);
            if (textView == null) {
                return;
            }
            textView.setTextAppearance(this.mResId);
        }
    }

    private static class SetPercentPaddingAction extends Action {
        float mBottom;
        float mLeft;
        int mPercentPolicy;
        float mRight;
        float mTop;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 113;
        }

        public SetPercentPaddingAction(int i, float f, float f2, float f3, float f4, int i2) {
            super();
            this.mViewId = i;
            this.mLeft = f;
            this.mTop = f2;
            this.mRight = f3;
            this.mBottom = f4;
            this.mPercentPolicy = i2;
        }

        public SetPercentPaddingAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mLeft = parcel.readFloat();
            this.mTop = parcel.readFloat();
            this.mRight = parcel.readFloat();
            this.mBottom = parcel.readFloat();
            this.mPercentPolicy = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeFloat(this.mLeft);
            parcel.writeFloat(this.mTop);
            parcel.writeFloat(this.mRight);
            parcel.writeFloat(this.mBottom);
            parcel.writeInt(this.mPercentPolicy);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            float f;
            float f2;
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (!(viewGroup instanceof AppWidgetHostView)) {
                Log.e(RemoteViews.LOG_TAG, "The setPercentPadding API is only available in AppWidgetHostView");
                return;
            }
            int measuredWidth = viewGroup.getMeasuredWidth();
            int measuredHeight = viewGroup.getMeasuredHeight();
            if (measuredWidth == 0 || measuredHeight == 0) {
                Log.e(RemoteViews.LOG_TAG, "Container's size is not measured yet");
                return;
            }
            int i = this.mPercentPolicy;
            if (i == 0) {
                f = measuredWidth;
                f2 = measuredHeight;
            } else {
                f = i == 1 ? measuredWidth : measuredHeight;
                f2 = f;
            }
            viewFindViewById.setPadding((int) (this.mLeft * f), (int) (this.mTop * f2), (int) (f * this.mRight), (int) (f2 * this.mBottom));
        }
    }

    private static class SetPercentTextSizeAction extends Action {
        float mHeightPercent;
        float mMaxSize;
        float mMinSize;
        String mText;
        float mWidthPercent;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 112;
        }

        SetPercentTextSizeAction(int i, float f, float f2, float f3, float f4, String str) {
            super();
            this.mViewId = i;
            this.mHeightPercent = f;
            this.mWidthPercent = f2;
            this.mMinSize = f3;
            this.mMaxSize = f4;
            this.mText = str;
        }

        public SetPercentTextSizeAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mHeightPercent = parcel.readFloat();
            this.mWidthPercent = parcel.readFloat();
            this.mMinSize = parcel.readFloat();
            this.mMaxSize = parcel.readFloat();
            this.mText = parcel.readString();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeFloat(this.mHeightPercent);
            parcel.writeFloat(this.mWidthPercent);
            parcel.writeFloat(this.mMinSize);
            parcel.writeFloat(this.mMaxSize);
            parcel.writeString(this.mText);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (!(viewGroup instanceof AppWidgetHostView)) {
                Log.e(RemoteViews.LOG_TAG, "The setPercentSize API is only available in AppWidgetHostView");
                return;
            }
            int measuredWidth = viewGroup.getMeasuredWidth();
            int measuredHeight = viewGroup.getMeasuredHeight();
            if (measuredWidth == 0 || measuredHeight == 0) {
                Log.e(RemoteViews.LOG_TAG, "Container's size is not measured yet");
            } else {
                setTextPercentSize(measuredWidth, measuredHeight, viewFindViewById);
            }
        }

        private void setTextPercentSize(int i, int i2, View view) {
            if (view instanceof TextView) {
                try {
                    ((TextView) view).setTextSize(1, calculateTextSize(view.getContext(), (int) (i * this.mWidthPercent), (int) (i2 * this.mHeightPercent), (int) this.mMinSize, (int) this.mMaxSize));
                    return;
                } catch (Exception e) {
                    Log.e(RemoteViews.LOG_TAG, "Text auto size is stopped by " + e);
                    return;
                }
            }
            Log.e(RemoteViews.LOG_TAG, "Auto TextSize is only applied at TextView");
        }

        private float calculateTextSize(Context context, int i, int i2, int i3, int i4) {
            TextView textView = new TextView(context);
            textView.lambda$setTextAsync$0(this.mText);
            textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            textView.setAutoSizeTextTypeUniformWithConfiguration(i3, i4, 1, 1);
            textView.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
            textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
            return textView.getTextSize() / context.getResources().getDisplayMetrics().density;
        }
    }

    private static class SetPercentLayoutSizeAction extends Action {
        float mHeightPercent;
        float mMaxHeightSize;
        float mMaxWidthSize;
        int mPercentPolicy;
        int mPercentType;
        float mRatio;
        float mWidthPercent;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 111;
        }

        SetPercentLayoutSizeAction(int i, float f, int i2, int i3, float f2, float f3) {
            super();
            this.mViewId = i;
            if (i2 == 1) {
                this.mWidthPercent = f;
                this.mHeightPercent = 0.0f;
            } else if (i2 == 2) {
                this.mWidthPercent = 0.0f;
                this.mHeightPercent = f;
            } else {
                this.mWidthPercent = f;
                this.mHeightPercent = f;
            }
            this.mPercentPolicy = i2;
            this.mPercentType = i3;
            this.mMaxWidthSize = f2;
            this.mMaxHeightSize = f3;
            this.mRatio = 0.0f;
        }

        SetPercentLayoutSizeAction(int i, float f, float f2, float f3, float f4, float f5) {
            super();
            this.mViewId = i;
            this.mWidthPercent = f;
            this.mHeightPercent = f2;
            this.mPercentPolicy = 0;
            this.mPercentType = 0;
            this.mMaxWidthSize = f3;
            this.mMaxHeightSize = f4;
            this.mRatio = f5;
        }

        public SetPercentLayoutSizeAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mWidthPercent = parcel.readFloat();
            this.mHeightPercent = parcel.readFloat();
            this.mPercentPolicy = parcel.readInt();
            this.mPercentType = parcel.readInt();
            this.mMaxWidthSize = parcel.readFloat();
            this.mMaxHeightSize = parcel.readFloat();
            this.mRatio = parcel.readFloat();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeFloat(this.mWidthPercent);
            parcel.writeFloat(this.mHeightPercent);
            parcel.writeInt(this.mPercentPolicy);
            parcel.writeInt(this.mPercentType);
            parcel.writeFloat(this.mMaxWidthSize);
            parcel.writeFloat(this.mMaxHeightSize);
            parcel.writeFloat(this.mRatio);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (!(viewGroup instanceof AppWidgetHostView)) {
                Log.e(RemoteViews.LOG_TAG, "The setPercentSize API is only available in AppWidgetHostView");
                return;
            }
            int measuredWidth = viewGroup.getMeasuredWidth();
            int measuredHeight = viewGroup.getMeasuredHeight();
            if (measuredWidth == 0 || measuredHeight == 0) {
                Log.e(RemoteViews.LOG_TAG, "Container's size is not measured yet");
            } else {
                setLayoutPercentSize(measuredWidth, measuredHeight, viewFindViewById);
            }
        }

        private void setLayoutPercentSize(int i, int i2, View view) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            float f = view.getResources().getDisplayMetrics().density;
            float f2 = this.mMaxWidthSize * f;
            float f3 = this.mMaxHeightSize * f;
            if (this.mRatio == 0.0f) {
                int i3 = this.mPercentType;
                if (i3 == 8) {
                    layoutParams.width = (int) Math.clamp(i * this.mWidthPercent, 0.0f, f2);
                    view.setLayoutParams(layoutParams);
                    return;
                } else {
                    if (i3 != 9) {
                        return;
                    }
                    layoutParams.height = (int) Math.clamp(i2 * this.mHeightPercent, 0.0f, f3);
                    view.setLayoutParams(layoutParams);
                    return;
                }
            }
            int iClamp = (int) Math.clamp(i * this.mWidthPercent, 0.0f, f2);
            int i4 = (int) (iClamp / this.mRatio);
            int iClamp2 = (int) Math.clamp(i2 * this.mHeightPercent, 0.0f, f3);
            int i5 = (int) (iClamp2 * this.mRatio);
            if (iClamp >= i5 || i4 >= iClamp2) {
                iClamp = i5;
                i4 = iClamp2;
            }
            layoutParams.width = iClamp;
            layoutParams.height = i4;
            view.setLayoutParams(layoutParams);
        }
    }

    private static class SetAutoSizeTextTypeUniformWithConfigurationAction extends Action {
        int mAutoSizeMaxTextSize;
        int mAutoSizeMinTextSize;
        int mAutoSizeStepGranularity;
        int mUnit;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 115;
        }

        SetAutoSizeTextTypeUniformWithConfigurationAction(int i, int i2, int i3, int i4, int i5) {
            super();
            this.mViewId = i;
            this.mAutoSizeMinTextSize = i2;
            this.mAutoSizeMaxTextSize = i3;
            this.mAutoSizeStepGranularity = i4;
            this.mUnit = i5;
        }

        SetAutoSizeTextTypeUniformWithConfigurationAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mAutoSizeMinTextSize = parcel.readInt();
            this.mAutoSizeMaxTextSize = parcel.readInt();
            this.mAutoSizeStepGranularity = parcel.readInt();
            this.mUnit = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mAutoSizeMinTextSize);
            parcel.writeInt(this.mAutoSizeMaxTextSize);
            parcel.writeInt(this.mAutoSizeStepGranularity);
            parcel.writeInt(this.mUnit);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            TextView textView = (TextView) view.findViewById(this.mViewId);
            if (textView == null) {
                return;
            }
            textView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeMinTextSize, this.mAutoSizeMaxTextSize, this.mAutoSizeStepGranularity, this.mUnit);
        }
    }

    static void setTextWithSpannableString(TextView textView, String str, Bundle bundle) {
        int i;
        if (textView == null) {
            return;
        }
        if (bundle != null && str != null) {
            try {
                SpannableString spannableString = new SpannableString(str);
                int length = spannableString.length();
                for (String str2 : PARCELABLE_SPAN_KEYS) {
                    Object styleSpan = null;
                    if ("TypefaceSpan".equals(str2)) {
                        String string = bundle.getString(str2, null);
                        if (string != null) {
                            styleSpan = new TypefaceSpan(string);
                        }
                    } else if ("TextAppearanceSpan".equals(str2)) {
                        int i2 = bundle.getInt(str2, 0);
                        if (i2 != 0) {
                            styleSpan = new TextAppearanceSpan(textView.getContext(), i2);
                        }
                    } else if ("UnderlineSpan".equals(str2)) {
                        if (bundle.getBoolean(str2, false)) {
                            styleSpan = new UnderlineSpan();
                        }
                    } else if ("StrikethroughSpan".equals(str2)) {
                        if (bundle.getBoolean(str2, false)) {
                            styleSpan = new StrikethroughSpan();
                        }
                    } else if ("StyleSpan".equals(str2) && (i = bundle.getInt(str2, -1)) != -1) {
                        styleSpan = new StyleSpan(i);
                    }
                    if (styleSpan != null) {
                        spannableString.setSpan(styleSpan, 0, length, 17);
                    }
                }
                textView.lambda$setTextAsync$0(spannableString);
                return;
            } catch (Exception e) {
                Log.e(LOG_TAG, "ex=" + e);
                return;
            }
        }
        textView.lambda$setTextAsync$0(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewPaddingAction extends Action {
        int mBottom;
        int mLeft;
        int mRight;
        int mTop;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 14;
        }

        public ViewPaddingAction(int i, int i2, int i3, int i4, int i5) {
            super();
            this.mViewId = i;
            this.mLeft = i2;
            this.mTop = i3;
            this.mRight = i4;
            this.mBottom = i5;
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
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mLeft);
            parcel.writeInt(this.mTop);
            parcel.writeInt(this.mRight);
            parcel.writeInt(this.mBottom);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            viewFindViewById.setPadding(this.mLeft, this.mTop, this.mRight, this.mBottom);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268053L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1120986464258L, this.mLeft);
            protoOutputStream.write(1120986464259L, this.mRight);
            protoOutputStream.write(1120986464260L, this.mTop);
            protoOutputStream.write(1120986464261L, this.mBottom);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268053L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1120986464258L, Integer.valueOf(protoInputStream.readInt(1120986464258L)));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else if (fieldNumber == 4) {
                    longSparseArray.put(1120986464260L, Integer.valueOf(protoInputStream.readInt(1120986464260L)));
                } else if (fieldNumber == 5) {
                    longSparseArray.put(1120986464261L, Integer.valueOf(protoInputStream.readInt(1120986464261L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$ViewPaddingAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.ViewPaddingAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new ViewPaddingAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), ((Integer) longSparseArray.get(1120986464258L, 0)).intValue(), ((Integer) longSparseArray.get(1120986464260L, 0)).intValue(), ((Integer) longSparseArray.get(1120986464259L, 0)).intValue(), ((Integer) longSparseArray.get(1120986464261L, 0)).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LayoutParamAction extends Action {
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

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 19;
        }

        LayoutParamAction(int i, int i2, float f, int i3) {
            super();
            this.mViewId = i;
            this.mProperty = i2;
            this.mValueType = 2;
            this.mValue = TypedValue.createComplexDimension(f, i3);
            this.mAnimatorId = -1;
            this.mIsAnimationEnd = false;
        }

        LayoutParamAction(int i, int i2, int i3, int i4) {
            super();
            this.mViewId = i;
            this.mProperty = i2;
            this.mValueType = i4;
            this.mValue = i3;
            this.mAnimatorId = -1;
            this.mIsAnimationEnd = false;
        }

        LayoutParamAction(int i, int i2, int i3) {
            super();
            this.mViewId = i;
            this.mProperty = i2;
            this.mValueType = 101;
            this.mValue = 0;
            this.mAnimatorId = i3;
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
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mProperty);
            parcel.writeInt(this.mValueType);
            parcel.writeInt(this.mValue);
            parcel.writeInt(this.mAnimatorId);
            parcel.writeBoolean(this.mIsAnimationEnd);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            ViewGroup.LayoutParams layoutParams;
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null || (layoutParams = viewFindViewById.getLayoutParams()) == null) {
                return;
            }
            int i = this.mProperty;
            if (i == 0) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getPixelOffset(viewFindViewById);
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            if (i == 1) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getPixelOffset(viewFindViewById);
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            if (i == 2) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = getPixelOffset(viewFindViewById);
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            if (i == 3) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = getPixelOffset(viewFindViewById);
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            if (i == 4) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(getPixelOffset(viewFindViewById));
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            if (i == 5) {
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(getPixelOffset(viewFindViewById));
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                }
                return;
            }
            if (i == 8) {
                if (this.mAnimatorId == -1) {
                    layoutParams.width = getPixelSize(viewFindViewById);
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                } else {
                    startValueAnimator(viewFindViewById, layoutParams);
                    return;
                }
            }
            if (i == 9) {
                if (this.mAnimatorId == -1) {
                    layoutParams.height = getPixelSize(viewFindViewById);
                    viewFindViewById.setLayoutParams(layoutParams);
                    return;
                } else {
                    startValueAnimator(viewFindViewById, layoutParams);
                    return;
                }
            }
            throw new IllegalArgumentException("Unknown property " + this.mProperty);
        }

        private void startValueAnimator(final View view, final ViewGroup.LayoutParams layoutParams) {
            ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(view.getContext(), this.mAnimatorId);
            if (valueAnimator == null) {
                return;
            }
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: android.widget.RemoteViews.LayoutParamAction.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    layoutParams.width = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                    view.setLayoutParams(layoutParams);
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.RemoteViews.LayoutParamAction.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    LayoutParamAction.this.mIsAnimationEnd = true;
                    PropertyValuesHolder[] values = ((ValueAnimator) animator).getValues();
                    if (values != null) {
                        PropertyValuesHolder propertyValuesHolder = values[0];
                        PropertyValuesHolder.PropertyValues propertyValues = new PropertyValuesHolder.PropertyValues();
                        if (propertyValuesHolder != null) {
                            propertyValuesHolder.getPropertyValues(propertyValues);
                            layoutParams.width = ((Integer) propertyValues.endValue).intValue();
                            view.setLayoutParams(layoutParams);
                        }
                    }
                }
            });
            if (this.mIsAnimationEnd) {
                valueAnimator.setDuration(0L);
            }
            valueAnimator.start();
        }

        private int getPixelOffset(View view) {
            try {
                int i = this.mValueType;
                if (i == 2) {
                    return TypedValue.complexToDimensionPixelOffset(this.mValue, view.getResources().getDisplayMetrics());
                }
                if (i == 3) {
                    if (this.mValue == 0) {
                        return 0;
                    }
                    return view.getResources().getDimensionPixelOffset(this.mValue);
                }
                if (i == 4) {
                    TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(new int[]{this.mValue});
                    try {
                        return typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, 0);
                    } finally {
                        typedArrayObtainStyledAttributes.recycle();
                    }
                }
                return this.mValue;
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }

        private int getPixelSize(View view) {
            try {
                int i = this.mValueType;
                if (i == 2) {
                    return TypedValue.complexToDimensionPixelSize(this.mValue, view.getResources().getDisplayMetrics());
                }
                if (i == 3) {
                    if (this.mValue == 0) {
                        return 0;
                    }
                    return view.getResources().getDimensionPixelSize(this.mValue);
                }
                if (i == 4) {
                    TypedArray typedArrayObtainStyledAttributes = view.getContext().obtainStyledAttributes(new int[]{this.mValue});
                    try {
                        return typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
                    } finally {
                        typedArrayObtainStyledAttributes.recycle();
                    }
                }
                return this.mValue;
            } catch (Throwable th) {
                throw new ActionException(th);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public String getUniqueKey() {
            return super.getUniqueKey() + this.mProperty;
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268036L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1120986464258L, this.mProperty);
            protoOutputStream.write(1120986464259L, this.mValue);
            protoOutputStream.write(1120986464260L, this.mValueType);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268036L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1120986464258L, Integer.valueOf(protoInputStream.readInt(1120986464258L)));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else if (fieldNumber == 4) {
                    longSparseArray.put(1120986464260L, Integer.valueOf(protoInputStream.readInt(1120986464260L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$LayoutParamAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.LayoutParamAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new LayoutParamAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), ((Integer) longSparseArray.get(1120986464258L, 0)).intValue(), ((Integer) longSparseArray.get(1120986464259L, 0)).intValue(), ((Integer) longSparseArray.get(1120986464260L, 0)).intValue());
        }
    }

    private static class SetRemoteInputsAction extends Action {
        final Parcelable[] mRemoteInputs;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 18;
        }

        public SetRemoteInputsAction(int i, RemoteInput[] remoteInputArr) {
            super();
            this.mViewId = i;
            this.mRemoteInputs = remoteInputArr;
        }

        public SetRemoteInputsAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mRemoteInputs = (Parcelable[]) parcel.createTypedArray(RemoteInput.CREATOR);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeTypedArray(this.mRemoteInputs, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            viewFindViewById.setTagInternal(R.id.remote_input_tag, this.mRemoteInputs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetIntTagAction extends Action {
        private final int mKey;
        private final int mTag;
        private final int mViewId;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 22;
        }

        SetIntTagAction(int i, int i2, int i3) {
            super();
            this.mViewId = i;
            this.mKey = i2;
            this.mTag = i3;
        }

        SetIntTagAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mKey = parcel.readInt();
            this.mTag = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mKey);
            parcel.writeInt(this.mTag);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            viewFindViewById.setTagInternal(this.mKey, Integer.valueOf(this.mTag));
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268044L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1138166333442L, resources.getResourceName(this.mKey));
            protoOutputStream.write(1120986464259L, this.mTag);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268044L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1138166333442L});
            return new PendingResources() { // from class: android.widget.RemoteViews$SetIntTagAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.SetIntTagAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new SetIntTagAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333442L), ((Integer) longSparseArray.get(1120986464259L, 0)).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetCompoundButtonCheckedAction extends Action {
        private final boolean mChecked;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 26;
        }

        SetCompoundButtonCheckedAction(int i, boolean z) {
            super();
            this.mViewId = i;
            this.mChecked = z;
        }

        SetCompoundButtonCheckedAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mChecked = parcel.readBoolean();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeBoolean(this.mChecked);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (!(viewFindViewById instanceof CompoundButton)) {
                Log.w(RemoteViews.LOG_TAG, "Cannot set checked to view " + this.mViewId + " because it is not a CompoundButton");
                return;
            }
            CompoundButton compoundButton = (CompoundButton) viewFindViewById;
            Object tag = compoundButton.getTag(R.id.remote_checked_change_listener_tag);
            if (tag instanceof CompoundButton.OnCheckedChangeListener) {
                compoundButton.setOnCheckedChangeListener(null);
                compoundButton.setChecked(this.mChecked);
                compoundButton.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) tag);
                return;
            }
            compoundButton.setChecked(this.mChecked);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268041L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1133871366146L, this.mChecked);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268041L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1133871366146L, Boolean.valueOf(protoInputStream.readBoolean(1133871366146L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$SetCompoundButtonCheckedAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.SetCompoundButtonCheckedAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new SetCompoundButtonCheckedAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), ((Boolean) longSparseArray.get(1133871366146L, false)).booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetRadioGroupCheckedAction extends Action {
        private final int mCheckedId;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 27;
        }

        SetRadioGroupCheckedAction(int i, int i2) {
            super();
            this.mViewId = i;
            this.mCheckedId = i2;
        }

        SetRadioGroupCheckedAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mCheckedId = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mCheckedId);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (!(viewFindViewById instanceof RadioGroup)) {
                Log.w(RemoteViews.LOG_TAG, "Cannot check " + this.mViewId + " because it's not a RadioGroup");
                return;
            }
            RadioGroup radioGroup = (RadioGroup) viewFindViewById;
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                View childAt = radioGroup.getChildAt(i);
                if ((childAt instanceof CompoundButton) && (childAt.getTag(R.id.remote_checked_change_listener_tag) instanceof CompoundButton.OnCheckedChangeListener)) {
                    ((CompoundButton) childAt).setOnCheckedChangeListener(null);
                }
            }
            radioGroup.check(this.mCheckedId);
            for (int i2 = 0; i2 < radioGroup.getChildCount(); i2++) {
                View childAt2 = radioGroup.getChildAt(i2);
                if (childAt2 instanceof CompoundButton) {
                    Object tag = childAt2.getTag(R.id.remote_checked_change_listener_tag);
                    if (tag instanceof CompoundButton.OnCheckedChangeListener) {
                        ((CompoundButton) childAt2).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) tag);
                    }
                }
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268045L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            int i = this.mCheckedId;
            if (i != -1) {
                protoOutputStream.write(1138166333442L, resources.getResourceName(i));
            }
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268045L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1138166333442L, protoInputStream.readString(1138166333442L));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L});
            return new PendingResources() { // from class: android.widget.RemoteViews$SetRadioGroupCheckedAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.SetRadioGroupCheckedAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new SetRadioGroupCheckedAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), longSparseArray.indexOfKey(1138166333442L) >= 0 ? RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333442L) : -1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SetViewOutlinePreferredRadiusAction extends Action {
        private final int mValue;
        private final int mValueType;

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return true;
        }

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 28;
        }

        SetViewOutlinePreferredRadiusAction(int i, int i2, int i3) {
            super();
            this.mViewId = i;
            this.mValueType = i3;
            this.mValue = i2;
        }

        SetViewOutlinePreferredRadiusAction(int i, float f, int i2) {
            super();
            this.mViewId = i;
            this.mValueType = 2;
            this.mValue = TypedValue.createComplexDimension(f, i2);
        }

        SetViewOutlinePreferredRadiusAction(Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.mValueType = parcel.readInt();
            this.mValue = parcel.readInt();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mValueType);
            parcel.writeInt(this.mValue);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException {
            float fComplexToDimension;
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            try {
                int i = this.mValueType;
                if (i != 2) {
                    float dimension = 0.0f;
                    if (i == 3) {
                        if (this.mValue != 0) {
                            dimension = viewFindViewById.getResources().getDimension(this.mValue);
                        }
                        fComplexToDimension = dimension;
                    } else if (i == 4) {
                        TypedArray typedArrayObtainStyledAttributes = viewFindViewById.getContext().obtainStyledAttributes(new int[]{this.mValue});
                        try {
                            float dimension2 = typedArrayObtainStyledAttributes.getDimension(0, 0.0f);
                            typedArrayObtainStyledAttributes.recycle();
                            fComplexToDimension = dimension2;
                        } catch (Throwable th) {
                            typedArrayObtainStyledAttributes.recycle();
                            throw th;
                        }
                    } else {
                        fComplexToDimension = this.mValue;
                    }
                } else {
                    fComplexToDimension = TypedValue.complexToDimension(this.mValue, viewFindViewById.getResources().getDisplayMetrics());
                }
                viewFindViewById.setOutlineProvider(new RemoteViewOutlineProvider(fComplexToDimension));
            } catch (Throwable th2) {
                throw new ActionException(th2);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            long jStart = protoOutputStream.start(1146756268048L);
            protoOutputStream.write(1138166333441L, resources.getResourceName(this.mViewId));
            protoOutputStream.write(1120986464258L, this.mValueType);
            protoOutputStream.write(1120986464259L, this.mValue);
            protoOutputStream.end(jStart);
        }

        public static PendingResources<Action> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            long jStart = protoInputStream.start(1146756268048L);
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    longSparseArray.put(1138166333441L, protoInputStream.readString(1138166333441L));
                } else if (fieldNumber == 2) {
                    longSparseArray.put(1120986464258L, Integer.valueOf(protoInputStream.readInt(1120986464258L)));
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1120986464259L, Integer.valueOf(protoInputStream.readInt(1120986464259L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            protoInputStream.end(jStart);
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1138166333441L, 1120986464258L});
            return new PendingResources() { // from class: android.widget.RemoteViews$SetViewOutlinePreferredRadiusAction$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.SetViewOutlinePreferredRadiusAction.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ Action lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            return new SetViewOutlinePreferredRadiusAction(RemoteViews.getAsIdentifier(resources, (LongSparseArray<?>) longSparseArray, 1138166333441L), ((Integer) longSparseArray.get(1120986464259L, 0)).intValue(), ((Integer) longSparseArray.get(1120986464258L)).intValue());
        }
    }

    public static final class RemoteViewOutlineProvider extends ViewOutlineProvider {
        private final float mRadius;

        public RemoteViewOutlineProvider(float f) {
            this.mRadius = f;
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

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 35;
        }

        SetDrawInstructionAction(DrawInstructions drawInstructions) {
            super();
            this.mInstructions = drawInstructions;
        }

        SetDrawInstructionAction(Parcel parcel) {
            super();
            if (Flags.drawDataParcel()) {
                this.mInstructions = DrawInstructions.readFromParcel(parcel);
            } else {
                this.mInstructions = null;
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            if (Flags.drawDataParcel()) {
                DrawInstructions.writeToParcel(this.mInstructions, parcel, i);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) throws ActionException, IOException {
            DrawInstructions drawInstructions;
            if (Flags.drawDataParcel() && (drawInstructions = this.mInstructions) != null && (view instanceof RemoteComposePlayer)) {
                final RemoteComposePlayer remoteComposePlayer = (RemoteComposePlayer) view;
                List<byte[]> list = drawInstructions.mInstructions;
                if (list.isEmpty()) {
                    return;
                }
                try {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(list.get(0));
                    try {
                        remoteComposePlayer.setDocument(new RemoteComposeDocument(byteArrayInputStream));
                        remoteComposePlayer.addIdActionListener(new RemoteComposePlayer.IdActionCallbacks() { // from class: android.widget.RemoteViews$SetDrawInstructionAction$$ExternalSyntheticLambda0
                            @Override // com.android.internal.widget.remotecompose.player.RemoteComposePlayer.IdActionCallbacks
                            public final void onAction(int i, String str) {
                                this.f$0.lambda$apply$1(remoteComposePlayer, actionApplyParams, i, str);
                            }
                        });
                        byteArrayInputStream.close();
                    } finally {
                    }
                } catch (IOException e) {
                    Log.e(RemoteViews.LOG_TAG, "Failed to render draw instructions", e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$1(final RemoteComposePlayer remoteComposePlayer, final ActionApplyParams actionApplyParams, final int i, final String str) {
            RemoteViews.this.mActions.forEach(new Consumer() { // from class: android.widget.RemoteViews$SetDrawInstructionAction$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteViews.SetDrawInstructionAction.lambda$apply$0(i, str, remoteComposePlayer, actionApplyParams, (RemoteViews.Action) obj);
                }
            });
        }

        static /* synthetic */ void lambda$apply$0(int i, String str, RemoteComposePlayer remoteComposePlayer, ActionApplyParams actionApplyParams, Action action) {
            if (i == action.mViewId && (action instanceof SetOnClickResponse)) {
                RemoteResponse remoteResponse = ((SetOnClickResponse) action).mResponse;
                if (remoteResponse.mFillIntent == null) {
                    remoteResponse.mFillIntent = new Intent();
                }
                remoteResponse.mFillIntent.putExtra("remotecompose_metadata", str);
                remoteResponse.handleViewInteraction(remoteComposePlayer, actionApplyParams.handler);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public boolean canWriteToProto() {
            return Flags.drawDataParcel();
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToProto(ProtoOutputStream protoOutputStream, Context context, Resources resources) {
            if (Flags.drawDataParcel()) {
                long jStart = protoOutputStream.start(1146756268054L);
                DrawInstructions drawInstructions = this.mInstructions;
                if (drawInstructions != null) {
                    Iterator<byte[]> it = drawInstructions.mInstructions.iterator();
                    while (it.hasNext()) {
                        protoOutputStream.write(2250562863105L, it.next());
                    }
                }
                protoOutputStream.end(jStart);
            }
        }
    }

    private PendingResources<Action> createSetDrawInstructionActionFromProto(ProtoInputStream protoInputStream) throws Exception {
        final ArrayList arrayList = new ArrayList();
        long jStart = protoInputStream.start(1146756268054L);
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                arrayList.add(protoInputStream.readBytes(2250562863105L));
            } else {
                Log.w(LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        protoInputStream.end(jStart);
        return new PendingResources() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda5
            @Override // android.widget.RemoteViews.PendingResources
            public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                return this.f$0.lambda$createSetDrawInstructionActionFromProto$6(arrayList, context, resources, hierarchyRootData, i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Action lambda$createSetDrawInstructionActionFromProto$6(List list, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
        return new SetDrawInstructionAction(new DrawInstructions.Builder(list).build());
    }

    public RemoteViews(String str, int i) {
        this(getApplicationInfo(str, UserHandle.myUserId()), i);
    }

    public RemoteViews(String str, int i, int i2) {
        this(str, i);
        this.mViewId = i2;
    }

    protected RemoteViews(ApplicationInfo applicationInfo, int i) {
        this.mActionsLock = new Object();
        this.isProductDEV = ViewRune.COMMON_IS_PRODUCT_DEV;
        this.mLightBackgroundLayoutId = 0;
        this.mAllowOtherRootParent = false;
        this.mAppWidgetId = -1;
        this.mBitmapCache = new BitmapCache();
        this.mCollectionCache = new RemoteCollectionCache();
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
        this.mApplication = applicationInfo;
        this.mLayoutId = i;
        applicationInfoCache.put(applicationInfo);
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

    private void setIdealSize(SizeF sizeF) {
        this.mIdealSize = sizeF;
    }

    private RemoteViews findSmallestRemoteView() {
        return this.mSizedRemoteViews.get(r1.size() - 1);
    }

    public RemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) {
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
        if (remoteViews == null || remoteViews2 == null) {
            throw new IllegalArgumentException("Both RemoteViews must be non-null");
        }
        if (!remoteViews.hasSameAppInfo(remoteViews2.mApplication)) {
            throw new IllegalArgumentException("Both RemoteViews must share the same package and user");
        }
        this.mApplication = remoteViews2.mApplication;
        this.mLayoutId = remoteViews2.mLayoutId;
        this.mViewId = remoteViews2.mViewId;
        this.mLightBackgroundLayoutId = remoteViews2.mLightBackgroundLayoutId;
        this.mLandscape = remoteViews;
        this.mPortrait = remoteViews2;
        Map<Class, Object> map = remoteViews2.mClassCookies;
        this.mClassCookies = map == null ? remoteViews.mClassCookies : map;
        configureDescendantsAsChildren();
    }

    public RemoteViews(Map<SizeF, RemoteViews> map) {
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
        if (map.isEmpty()) {
            throw new IllegalArgumentException("The set of RemoteViews cannot be empty");
        }
        if (map.size() > 16) {
            throw new IllegalArgumentException("Too many RemoteViews in constructor");
        }
        if (map.size() == 1) {
            RemoteViews next = map.values().iterator().next();
            initializeFrom(next, next);
            return;
        }
        this.mClassCookies = initializeSizedRemoteViews(map.entrySet().stream().map(new Function() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return RemoteViews.lambda$new$7((Map.Entry) obj);
            }
        }).iterator());
        RemoteViews remoteViewsFindSmallestRemoteView = findSmallestRemoteView();
        this.mApplication = remoteViewsFindSmallestRemoteView.mApplication;
        this.mLayoutId = remoteViewsFindSmallestRemoteView.mLayoutId;
        this.mViewId = remoteViewsFindSmallestRemoteView.mViewId;
        this.mLightBackgroundLayoutId = remoteViewsFindSmallestRemoteView.mLightBackgroundLayoutId;
        configureDescendantsAsChildren();
    }

    static /* synthetic */ RemoteViews lambda$new$7(Map.Entry entry) {
        ((RemoteViews) entry.getValue()).setIdealSize((SizeF) entry.getKey());
        return (RemoteViews) entry.getValue();
    }

    private Map<Class, Object> initializeSizedRemoteViews(Iterator<RemoteViews> it) {
        ArrayList arrayList = new ArrayList();
        RemoteViews remoteViews = null;
        float f = Float.MAX_VALUE;
        Map<Class, Object> map = null;
        while (it.hasNext()) {
            RemoteViews next = it.next();
            SizeF idealSize = next.getIdealSize();
            if (idealSize == null) {
                throw new IllegalStateException("Expected RemoteViews to have ideal size");
            }
            float width = idealSize.getWidth() * idealSize.getHeight();
            if (remoteViews != null && !next.hasSameAppInfo(remoteViews.mApplication)) {
                throw new IllegalArgumentException("All RemoteViews must share the same package and user");
            }
            if (remoteViews == null || width < f) {
                if (remoteViews != null) {
                    arrayList.add(remoteViews);
                }
                remoteViews = next;
                f = width;
            } else {
                arrayList.add(next);
            }
            next.setIdealSize(idealSize);
            if (map == null) {
                map = next.mClassCookies;
            }
        }
        arrayList.add(remoteViews);
        this.mSizedRemoteViews = arrayList;
        return map;
    }

    public RemoteViews(RemoteViews remoteViews) {
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
        initializeFrom(remoteViews, null);
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

    public void semSetBlurInfo(int i, SemBlurInfo semBlurInfo) {
        addAction(new semSetBlurInfoAction(this, i, semBlurInfo));
    }

    private class semSetBlurInfoAction extends Action {
        SemBlurInfo blurInfo;
        int viewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 105;
        }

        public semSetBlurInfoAction(RemoteViews remoteViews, int i, SemBlurInfo semBlurInfo) {
            super();
            this.viewId = i;
            this.blurInfo = semBlurInfo;
        }

        public semSetBlurInfoAction(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.blurInfo = SemBlurInfo.CREATOR.createFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.viewId);
            this.blurInfo.writeToParcel(parcel, i);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            new ReflectionAction(this.viewId, "semSetBlurInfo", 30, this.blurInfo).apply(view, viewGroup, actionApplyParams);
        }
    }

    public void semSetOnCheckedChangedPendingIntent(int i, PendingIntent pendingIntent) {
        Log.d(LOG_TAG, "semSetOnCheckedChangedPendingIntent() viewId = " + i + ", pendingIntent = " + pendingIntent);
        addAction(new semSetOnCheckedChangedPendingIntent(this, i, pendingIntent));
    }

    private class semSetOnCheckedChangedPendingIntent extends Action {
        PendingIntent pendingIntent;
        int viewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 104;
        }

        public semSetOnCheckedChangedPendingIntent(RemoteViews remoteViews, int i, PendingIntent pendingIntent) {
            super();
            this.viewId = i;
            this.pendingIntent = pendingIntent;
        }

        public semSetOnCheckedChangedPendingIntent(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.pendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.viewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.pendingIntent, parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            CompoundButton compoundButton = (CompoundButton) view.findViewById(this.viewId);
            if (compoundButton == null || this.pendingIntent == null) {
                return;
            }
            compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: android.widget.RemoteViews.semSetOnCheckedChangedPendingIntent.1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton2, boolean z) {
                    try {
                        Intent intent = new Intent();
                        intent.putExtra(RemoteViews.SEM_EXTRA_IS_CHECKED, z);
                        compoundButton2.getContext().startIntentSender(semSetOnCheckedChangedPendingIntent.this.pendingIntent.getIntentSender(), intent, 268435456, 268435456, 0);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                    }
                }
            });
        }

        @Override // android.widget.RemoteViews.Action
        public void clear() {
            this.pendingIntent = null;
        }
    }

    public void semSetOnTouchPendingIntent(int i, PendingIntent pendingIntent) {
        addAction(new SemSetOnTouchPendingIntent(this, i, pendingIntent));
    }

    private class SemSetOnTouchPendingIntent extends Action {
        PendingIntent pendingIntent;
        int viewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 103;
        }

        public SemSetOnTouchPendingIntent(RemoteViews remoteViews, int i, PendingIntent pendingIntent) {
            super();
            this.viewId = i;
            this.pendingIntent = pendingIntent;
        }

        public SemSetOnTouchPendingIntent(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.pendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.viewId);
            PendingIntent.writePendingIntentOrNullToParcel(this.pendingIntent, parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.viewId);
            if (viewFindViewById == null || this.pendingIntent == null) {
                return;
            }
            viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: android.widget.RemoteViews.SemSetOnTouchPendingIntent.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        try {
                            Intent intent = new Intent();
                            intent.putExtra(RemoteViews.SEM_EXTRA_X_POSITION, x);
                            intent.putExtra(RemoteViews.SEM_EXTRA_Y_POSITION, y);
                            intent.putExtra(RemoteViews.SEM_EXTRA_IS_UP, false);
                            view2.getContext().startIntentSender(SemSetOnTouchPendingIntent.this.pendingIntent.getIntentSender(), intent, 268435456, 268435456, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                        }
                    } else if (action == 1) {
                        try {
                            Intent intent2 = new Intent();
                            intent2.putExtra(RemoteViews.SEM_EXTRA_X_POSITION, x);
                            intent2.putExtra(RemoteViews.SEM_EXTRA_Y_POSITION, y);
                            intent2.putExtra(RemoteViews.SEM_EXTRA_IS_UP, true);
                            view2.getContext().startIntentSender(SemSetOnTouchPendingIntent.this.pendingIntent.getIntentSender(), intent2, 268435456, 268435456, 0);
                        } catch (IntentSender.SendIntentException e2) {
                            Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e2);
                        }
                    }
                    return false;
                }
            });
        }

        @Override // android.widget.RemoteViews.Action
        public void clear() {
            this.pendingIntent = null;
        }
    }

    public void setOrientation(boolean z) {
        this.mIsForcedOrientation = true;
        this.mIsPortrait = z;
    }

    public void semSetViewObjectAnimator(int i, int i2) {
        addAction(new ViewObjectAnimatorAction(this, i, i2));
    }

    public void semSetAnimation(SemRemoteViewsAnimation semRemoteViewsAnimation) {
        if (semRemoteViewsAnimation instanceof SemRemoteViewsDrawableAnimation) {
            addAction(new SemAnimationAction(this, 1, semRemoteViewsAnimation));
            return;
        }
        if (semRemoteViewsAnimation instanceof SemRemoteViewsViewAnimation) {
            addAction(new SemAnimationAction(this, 2, semRemoteViewsAnimation));
            return;
        }
        if (semRemoteViewsAnimation instanceof SemRemoteViewsPropertyAnimation) {
            addAction(new SemAnimationAction(this, 3, semRemoteViewsAnimation));
        } else if (semRemoteViewsAnimation instanceof SemRemoteViewsValueAnimation) {
            addAction(new SemAnimationAction(this, 4, semRemoteViewsAnimation));
        } else if (semRemoteViewsAnimation instanceof SemRemoteViewsBasicAnimation) {
            addAction(new SemAnimationAction(this, 5, semRemoteViewsAnimation));
        }
    }

    private void hidden_semSetValueAnimation(int i, String str, String str2, float f, float f2, int i2, Bundle bundle) {
        addAction(new SemAnimationAction(this, 4, new SemRemoteViewsValueAnimation(i, str, str2, f, f2, i2, bundle)));
    }

    private void hidden_semSetValueAnimation(int i, String str, String str2, int i2, int i3, int i4, Bundle bundle) {
        addAction(new SemAnimationAction(this, 4, new SemRemoteViewsValueAnimation(i, str, str2, i2, i3, i4, bundle)));
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

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 107;
        }

        public SemAnimationAction(RemoteViews remoteViews, int i, SemRemoteViewsAnimation semRemoteViewsAnimation) {
            super();
            this.animationType = i;
            this.animation = semRemoteViewsAnimation;
        }

        public SemAnimationAction(RemoteViews remoteViews, Parcel parcel) {
            super();
            int i = parcel.readInt();
            this.animationType = i;
            if (i == 1) {
                this.animation = SemRemoteViewsDrawableAnimation.CREATOR.createFromParcel(parcel);
                return;
            }
            if (i == 2) {
                this.animation = SemRemoteViewsViewAnimation.CREATOR.createFromParcel(parcel);
                return;
            }
            if (i == 3) {
                this.animation = SemRemoteViewsPropertyAnimation.CREATOR.createFromParcel(parcel);
            } else if (i == 4) {
                this.animation = SemRemoteViewsValueAnimation.CREATOR.createFromParcel(parcel);
            } else {
                if (i != 5) {
                    return;
                }
                this.animation = SemRemoteViewsBasicAnimation.CREATOR.createFromParcel(parcel);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.animationType);
            int i2 = this.animationType;
            if (i2 == 1) {
                SemRemoteViewsDrawableAnimation.writeToParcel((SemRemoteViewsDrawableAnimation) this.animation, parcel);
                return;
            }
            if (i2 == 2) {
                SemRemoteViewsViewAnimation.writeToParcel((SemRemoteViewsViewAnimation) this.animation, parcel);
                return;
            }
            if (i2 == 3) {
                SemRemoteViewsPropertyAnimation.writeToParcel((SemRemoteViewsPropertyAnimation) this.animation, parcel);
            } else if (i2 == 4) {
                SemRemoteViewsValueAnimation.writeToParcel((SemRemoteViewsValueAnimation) this.animation, parcel);
            } else {
                if (i2 != 5) {
                    return;
                }
                SemRemoteViewsBasicAnimation.writeToParcel((SemRemoteViewsBasicAnimation) this.animation, parcel);
            }
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws ActionException {
            SemRemoteViewsAnimation semRemoteViewsAnimation = this.animation;
            if (semRemoteViewsAnimation != null) {
                semRemoteViewsAnimation.play(view);
            }
        }

        public String getActionName() {
            return "SemAnimationAction";
        }
    }

    private static RemoteViews createInitializedFrom(RemoteViews remoteViews, RemoteViews remoteViews2) {
        RemoteViews remoteViews3 = new RemoteViews();
        remoteViews3.initializeFrom(remoteViews, remoteViews2);
        return remoteViews3;
    }

    private void initializeFrom(RemoteViews remoteViews, RemoteViews remoteViews2) {
        if (remoteViews2 == null) {
            this.mBitmapCache = remoteViews.mBitmapCache;
            this.mCollectionCache = new RemoteCollectionCache(remoteViews.mCollectionCache);
            this.mApplicationInfoCache = remoteViews.mApplicationInfoCache;
        } else {
            this.mBitmapCache = remoteViews2.mBitmapCache;
            this.mCollectionCache = remoteViews2.mCollectionCache;
            this.mApplicationInfoCache = remoteViews2.mApplicationInfoCache;
        }
        if (remoteViews2 == null || remoteViews.mIsRoot) {
            this.mIsRoot = true;
            remoteViews2 = this;
        } else {
            this.mIsRoot = false;
        }
        this.mApplication = remoteViews.mApplication;
        this.mLayoutId = remoteViews.mLayoutId;
        this.mLightBackgroundLayoutId = remoteViews.mLightBackgroundLayoutId;
        this.mApplyFlags = remoteViews.mApplyFlags;
        this.mClassCookies = remoteViews.mClassCookies;
        this.mIdealSize = remoteViews.mIdealSize;
        this.mProviderInstanceId = remoteViews.mProviderInstanceId;
        this.mHasDrawInstructions = remoteViews.mHasDrawInstructions;
        this.mAllowOtherRootParent = remoteViews.mAllowOtherRootParent;
        this.mAppWidgetId = remoteViews.mAppWidgetId;
        if (remoteViews.hasLandscapeAndPortraitLayouts()) {
            this.mLandscape = createInitializedFrom(remoteViews.mLandscape, remoteViews2);
            this.mPortrait = createInitializedFrom(remoteViews.mPortrait, remoteViews2);
        }
        if (remoteViews.hasSizedRemoteViews()) {
            this.mSizedRemoteViews = new ArrayList(remoteViews.mSizedRemoteViews.size());
            Iterator<RemoteViews> it = remoteViews.mSizedRemoteViews.iterator();
            while (it.hasNext()) {
                this.mSizedRemoteViews.add(createInitializedFrom(it.next(), remoteViews2));
            }
        }
        if (remoteViews.mActions != null) {
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.putClassCookies(this.mClassCookies);
            remoteViews.writeActionsToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            readActionsFromParcel(parcelObtain, 0);
            parcelObtain.recycle();
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

    private RemoteViews(Parcel parcel, HierarchyRootData hierarchyRootData, ApplicationInfo applicationInfo, int i) {
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
        if (i > 10 && UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            throw new IllegalArgumentException("Too many nested views.");
        }
        int i2 = i + 1;
        int i3 = parcel.readInt();
        if (hierarchyRootData == null) {
            this.mBitmapCache = new BitmapCache(parcel);
            this.mClassCookies = parcel.copyClassCookies();
            this.mCollectionCache = new RemoteCollectionCache(parcel);
        } else {
            configureAsChild(hierarchyRootData);
        }
        if (i3 == 0) {
            this.mApplication = (ApplicationInfo) parcel.readTypedObject(ApplicationInfo.CREATOR);
            this.mIdealSize = parcel.readInt() != 0 ? SizeF.CREATOR.createFromParcel(parcel) : null;
            this.mLayoutId = parcel.readInt();
            this.mViewId = parcel.readInt();
            this.mLightBackgroundLayoutId = parcel.readInt();
            readActionsFromParcel(parcel, i2);
        } else if (i3 == 2) {
            int i4 = parcel.readInt();
            if (i4 > 16) {
                throw new IllegalArgumentException("Too many views in mapping from size to RemoteViews.");
            }
            ArrayList arrayList = new ArrayList(i4);
            for (int i5 = 0; i5 < i4; i5++) {
                RemoteViews remoteViews = new RemoteViews(parcel, getHierarchyRootData(), applicationInfo, i2);
                applicationInfo = remoteViews.mApplication;
                arrayList.add(remoteViews);
            }
            initializeSizedRemoteViews(arrayList.iterator());
            RemoteViews remoteViewsFindSmallestRemoteView = findSmallestRemoteView();
            this.mApplication = remoteViewsFindSmallestRemoteView.mApplication;
            this.mLayoutId = remoteViewsFindSmallestRemoteView.mLayoutId;
            this.mViewId = remoteViewsFindSmallestRemoteView.mViewId;
            this.mLightBackgroundLayoutId = remoteViewsFindSmallestRemoteView.mLightBackgroundLayoutId;
        } else {
            this.mLandscape = new RemoteViews(parcel, getHierarchyRootData(), applicationInfo, i2);
            RemoteViews remoteViews2 = new RemoteViews(parcel, getHierarchyRootData(), this.mLandscape.mApplication, i2);
            this.mPortrait = remoteViews2;
            this.mApplication = remoteViews2.mApplication;
            this.mLayoutId = remoteViews2.mLayoutId;
            this.mViewId = remoteViews2.mViewId;
            this.mLightBackgroundLayoutId = remoteViews2.mLightBackgroundLayoutId;
        }
        this.mApplyFlags = parcel.readInt();
        this.mProviderInstanceId = parcel.readLong();
        this.mHasDrawInstructions = parcel.readBoolean();
        boolean z = parcel.readBoolean();
        this.mAllowOtherRootParent = z;
        if (z) {
            this.mAppWidgetId = parcel.readInt();
        }
        if (this.mIsRoot) {
            configureDescendantsAsChildren();
        }
    }

    private void readActionsFromParcel(Parcel parcel, int i) {
        int i2 = parcel.readInt();
        if (i2 > 0) {
            this.mActions = new ArrayList<>(i2);
            synchronized (this.mActionsLock) {
                for (int i3 = 0; i3 < i2; i3++) {
                    this.mActions.add(getActionFromParcel(parcel, i));
                }
            }
        }
    }

    private Action getActionFromParcel(Parcel parcel, int i) {
        int i2 = parcel.readInt();
        if (i2 == 18) {
            return new SetRemoteInputsAction(parcel);
        }
        if (i2 == 19) {
            return new LayoutParamAction(parcel);
        }
        if (i2 == 34) {
            return new SetOnStylusHandwritingResponse(parcel);
        }
        if (i2 != 35) {
            switch (i2) {
                case 1:
                    return new SetOnClickResponse(parcel);
                case 2:
                    return new ReflectionAction(parcel);
                case 3:
                    return new SetDrawableTint(parcel);
                case 4:
                    return new ViewGroupActionAdd(parcel, this.mApplication, i);
                case 5:
                    return new ViewContentNavigation(this, parcel);
                case 6:
                    return new SetEmptyView(parcel);
                case 7:
                    return new ViewGroupActionRemove(parcel);
                case 8:
                    return new SetPendingIntentTemplate(parcel);
                default:
                    switch (i2) {
                        case 10:
                            return new SetRemoteViewsAdapterIntent(parcel);
                        case 11:
                            return new TextViewDrawableAction(parcel);
                        case 12:
                            return new BitmapReflectionAction(this, parcel);
                        case 13:
                            return new TextViewSizeAction(parcel);
                        case 14:
                            return new ViewPaddingAction(parcel);
                        default:
                            switch (i2) {
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
                                default:
                                    switch (i2) {
                                        case 100:
                                            return new SemSetOnLongClickPendingIntent(this, parcel);
                                        case 101:
                                            return new SemSetLongClickPendingIntentTemplate(this, parcel);
                                        case 102:
                                            return new SemSetOnLongClickDragable(this, parcel);
                                        case 103:
                                            return new SemSetOnTouchPendingIntent(this, parcel);
                                        case 104:
                                            return new semSetOnCheckedChangedPendingIntent(this, parcel);
                                        case 105:
                                            return new semSetBlurInfoAction(this, parcel);
                                        case 106:
                                            return new ViewObjectAnimatorAction(this, parcel);
                                        case 107:
                                            return new SemAnimationAction(this, parcel);
                                        case 108:
                                            return new SetTextViewShadowAction(this, parcel);
                                        case 109:
                                            return new SetStringTagAction(parcel);
                                        case 110:
                                            return new SemSetTextViewTextResourceAction(parcel);
                                        case 111:
                                            return new SetPercentLayoutSizeAction(parcel);
                                        case 112:
                                            return new SetPercentTextSizeAction(parcel);
                                        case 113:
                                            return new SetPercentPaddingAction(parcel);
                                        case 114:
                                            return new SetTextAppearanceAction(parcel);
                                        case 115:
                                            return new SetAutoSizeTextTypeUniformWithConfigurationAction(parcel);
                                        default:
                                            throw new ActionException("Tag " + i2 + " not found");
                                    }
                            }
                    }
            }
        }
        return new SetDrawInstructionAction(parcel);
    }

    @Override // 
    @Deprecated
    /* renamed from: clone */
    public RemoteViews mo469clone() {
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
    public void configureAsChild(HierarchyRootData hierarchyRootData) {
        this.mIsRoot = false;
        this.mBitmapCache = hierarchyRootData.mBitmapCache;
        this.mCollectionCache = hierarchyRootData.mRemoteCollectionCache;
        this.mApplicationInfoCache = hierarchyRootData.mApplicationInfoCache;
        this.mClassCookies = hierarchyRootData.mClassCookies;
        configureDescendantsAsChildren();
    }

    private void configureDescendantsAsChildren() {
        this.mApplication = this.mApplicationInfoCache.getOrPut(this.mApplication);
        HierarchyRootData hierarchyRootData = getHierarchyRootData();
        if (hasSizedRemoteViews()) {
            Iterator<RemoteViews> it = this.mSizedRemoteViews.iterator();
            while (it.hasNext()) {
                it.next().configureAsChild(hierarchyRootData);
            }
        } else {
            if (hasLandscapeAndPortraitLayouts()) {
                this.mLandscape.configureAsChild(hierarchyRootData);
                this.mPortrait.configureAsChild(hierarchyRootData);
                return;
            }
            synchronized (this.mActionsLock) {
                ArrayList<Action> arrayList = this.mActions;
                if (arrayList != null) {
                    Iterator<Action> it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        it2.next().setHierarchyRootData(hierarchyRootData);
                    }
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

    public long estimateMemoryUsage() {
        return this.mBitmapCache.getBitmapMemory();
    }

    public long estimateIconMemoryUsage() {
        final AtomicLong atomicLong = new AtomicLong(0L);
        visitIcons(new Consumer() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RemoteViews.lambda$estimateIconMemoryUsage$8(atomicLong, (Icon) obj);
            }
        });
        return atomicLong.get();
    }

    static /* synthetic */ void lambda$estimateIconMemoryUsage$8(AtomicLong atomicLong, Icon icon) {
        if (icon.getType() == 1 || icon.getType() == 5) {
            atomicLong.addAndGet(icon.getBitmap().getAllocationByteCount());
        }
    }

    public long estimateTotalBitmapMemoryUsage() {
        return estimateMemoryUsage() + estimateIconMemoryUsage();
    }

    private void addAction(Action action) {
        if (hasMultipleLayouts()) {
            throw new RuntimeException("RemoteViews specifying separate layouts for orientation or size cannot be modified. Instead, fully configure each layouts individually before constructing the combined layout.");
        }
        if (this.mActions == null) {
            this.mActions = new ArrayList<>();
        }
        synchronized (this.mActionsLock) {
            this.mActions.add(action);
        }
    }

    public void addView(int i, RemoteViews remoteViews) {
        Action viewGroupActionAdd;
        if (remoteViews == null) {
            viewGroupActionAdd = new ViewGroupActionRemove(i);
        } else {
            viewGroupActionAdd = new ViewGroupActionAdd(this, i, remoteViews);
        }
        addAction(viewGroupActionAdd);
    }

    public void addStableView(int i, RemoteViews remoteViews, int i2) {
        addAction(new ViewGroupActionAdd(i, remoteViews, -1, i2));
    }

    public void addView(int i, RemoteViews remoteViews, int i2) {
        addAction(new ViewGroupActionAdd(this, i, remoteViews, i2));
    }

    public void removeAllViews(int i) {
        addAction(new ViewGroupActionRemove(i));
    }

    public void removeAllViewsExceptId(int i, int i2) {
        addAction(new ViewGroupActionRemove(i, i2));
    }

    public void removeFromParent(int i) {
        addAction(new RemoveFromParentAction(i));
    }

    @Deprecated
    public void showNext(int i) {
        addAction(new ViewContentNavigation(this, i, true));
    }

    @Deprecated
    public void showPrevious(int i) {
        addAction(new ViewContentNavigation(this, i, false));
    }

    public void setDisplayedChild(int i, int i2) {
        setInt(i, "setDisplayedChild", i2);
    }

    public void setViewVisibility(int i, int i2) {
        setInt(i, "setVisibility", i2);
    }

    public void setTextViewText(int i, CharSequence charSequence) {
        setCharSequence(i, "setText", charSequence);
    }

    private void hidden_semSetTextViewTextResource(int i, int i2, Bundle bundle) {
        addAction(new SemSetTextViewTextResourceAction(i, i2, bundle));
    }

    public void setTextViewTextSize(int i, int i2, float f) {
        addAction(new TextViewSizeAction(i, i2, f));
    }

    private void hidden_semSetTextViewTextSize(int i, float f, float f2) {
        addAction(new TextViewSizeAction(i, 2, f, f2));
    }

    private void hidden_semSetTextViewTextSizeResource(int i, int i2, int i3, float f) {
        addAction(new TextViewSizeAction(i, i2, i3, f));
    }

    private void hidden_semSetLayoutPercentSize(int i, float f, int i2, int i3, float f2, float f3) {
        addAction(new SetPercentLayoutSizeAction(i, f, i2, i3, f2, f3));
    }

    private void hidden_semSetLayoutPercentSize(int i, float f, float f2, float f3, float f4, float f5) {
        addAction(new SetPercentLayoutSizeAction(i, f, f2, f3, f4, f5));
    }

    private void hidden_semSetTextPercentSize(int i, float f, float f2, float f3, float f4, String str) {
        addAction(new SetPercentTextSizeAction(i, f, f2, f3, f4, str));
    }

    private void hidden_semSetPercentViewPadding(int i, float f, float f2, float f3, float f4, int i2) {
        addAction(new SetPercentPaddingAction(i, f, f2, f3, f4, i2));
    }

    private void hidden_semSetTextAppearance(int i, int i2) {
        addAction(new SetTextAppearanceAction(i, i2));
    }

    private void hidden_semSetAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4, int i5) {
        addAction(new SetAutoSizeTextTypeUniformWithConfigurationAction(i, i2, i3, i4, i5));
    }

    public void setTextViewCompoundDrawables(int i, int i2, int i3, int i4, int i5) {
        addAction(new TextViewDrawableAction(i, false, i2, i3, i4, i5));
    }

    public void setTextViewCompoundDrawablesRelative(int i, int i2, int i3, int i4, int i5) {
        addAction(new TextViewDrawableAction(i, true, i2, i3, i4, i5));
    }

    public void setTextViewCompoundDrawables(int i, Icon icon, Icon icon2, Icon icon3, Icon icon4) {
        addAction(new TextViewDrawableAction(i, false, icon, icon2, icon3, icon4));
    }

    public void setTextViewCompoundDrawablesRelative(int i, Icon icon, Icon icon2, Icon icon3, Icon icon4) {
        addAction(new TextViewDrawableAction(i, true, icon, icon2, icon3, icon4));
    }

    public void setImageViewResource(int i, int i2) {
        setInt(i, "setImageResource", i2);
    }

    public void setImageViewUri(int i, Uri uri) throws IOException {
        setUri(i, "setImageURI", uri);
    }

    public void setImageViewBitmap(int i, Bitmap bitmap) {
        setBitmap(i, "setImageBitmap", bitmap);
    }

    public void setImageViewIcon(int i, Icon icon) {
        setIcon(i, "setImageIcon", icon);
    }

    public void setEmptyView(int i, int i2) {
        addAction(new SetEmptyView(i, i2));
    }

    public void setChronometer(int i, long j, String str, boolean z) {
        setLong(i, "setBase", j);
        setString(i, "setFormat", str);
        setBoolean(i, "setStarted", z);
    }

    public void setChronometerCountDown(int i, boolean z) {
        setBoolean(i, "setCountDown", z);
    }

    public void setProgressBar(int i, int i2, int i3, boolean z) {
        setBoolean(i, "setIndeterminate", z);
        if (z) {
            return;
        }
        setInt(i, "setMax", i2);
        setInt(i, "setProgress", i3);
    }

    public void setOnClickPendingIntent(int i, PendingIntent pendingIntent) {
        setOnClickResponse(i, RemoteResponse.fromPendingIntent(pendingIntent));
    }

    public void setOnClickResponse(int i, RemoteResponse remoteResponse) {
        addAction(new SetOnClickResponse(i, remoteResponse));
    }

    public void setPendingIntentTemplate(int i, PendingIntent pendingIntent) {
        if (hasDrawInstructions()) {
            getPendingIntentTemplate().set(i, pendingIntent);
            tryAddRemoteResponse(i);
        } else {
            addAction(new SetPendingIntentTemplate(i, pendingIntent));
        }
    }

    public void setOnClickFillInIntent(int i, Intent intent) {
        if (hasDrawInstructions()) {
            getFillInIntent().set(i, intent);
            tryAddRemoteResponse(i);
        } else {
            setOnClickResponse(i, RemoteResponse.fromFillInIntent(intent));
        }
    }

    public void setOnCheckedChangeResponse(int i, RemoteResponse remoteResponse) {
        addAction(new SetOnCheckedChangeResponse(i, remoteResponse.setInteractionType(1)));
    }

    public void setOnStylusHandwritingPendingIntent(int i, PendingIntent pendingIntent) {
        addAction(new SetOnStylusHandwritingResponse(i, pendingIntent));
    }

    public void setDrawableTint(int i, boolean z, int i2, PorterDuff.Mode mode) {
        addAction(new SetDrawableTint(i, z, i2, mode));
    }

    private void hidden_semSetTextViewShadow(int i, float f, float f2, float f3, int i2) {
        addAction(new SetTextViewShadowAction(this, i, f, f2, f3, i2));
    }

    private void hidden_semSetStringTag(int i, int i2, String str) {
        addAction(new SetStringTagAction(i, i2, str));
    }

    public void setRippleDrawableColor(int i, ColorStateList colorStateList) {
        addAction(new SetRippleDrawableColor(i, colorStateList));
    }

    public void setProgressTintList(int i, ColorStateList colorStateList) {
        addAction(new ReflectionAction(i, "setProgressTintList", 15, colorStateList));
    }

    public void setProgressBackgroundTintList(int i, ColorStateList colorStateList) {
        addAction(new ReflectionAction(i, "setProgressBackgroundTintList", 15, colorStateList));
    }

    public void setProgressIndeterminateTintList(int i, ColorStateList colorStateList) {
        addAction(new ReflectionAction(i, "setIndeterminateTintList", 15, colorStateList));
    }

    public void setTextColor(int i, int i2) {
        setInt(i, "setTextColor", i2);
    }

    public void setTextColor(int i, ColorStateList colorStateList) {
        addAction(new ReflectionAction(i, "setTextColor", 15, colorStateList));
    }

    @Deprecated
    public void setRemoteAdapter(int i, int i2, Intent intent) {
        setRemoteAdapter(i2, intent);
    }

    @Deprecated
    public void setRemoteAdapter(int i, Intent intent) {
        if (Flags.remoteAdapterConversion()) {
            addAction(new SetRemoteCollectionItemListAdapterAction(i, intent));
        } else {
            addAction(new SetRemoteViewsAdapterIntent(i, intent));
        }
    }

    @Deprecated
    public void setRemoteAdapter(int i, ArrayList<RemoteViews> arrayList, int i2) {
        RemoteCollectionItems.Builder builder = new RemoteCollectionItems.Builder();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            builder.addItem(i3, arrayList.get(i3));
        }
        setRemoteAdapter(i, builder.setViewTypeCount(i2).build());
    }

    public void setRemoteAdapter(int i, RemoteCollectionItems remoteCollectionItems) {
        addAction(new SetRemoteCollectionItemListAdapterAction(i, remoteCollectionItems));
    }

    public void setScrollPosition(int i, int i2) {
        setInt(i, "smoothScrollToPosition", i2);
    }

    public void setRelativeScrollPosition(int i, int i2) {
        setInt(i, "smoothScrollByOffset", i2);
    }

    public void setViewPadding(int i, int i2, int i3, int i4, int i5) {
        addAction(new ViewPaddingAction(i, i2, i3, i4, i5));
    }

    public void setViewLayoutMarginDimen(int i, int i2, int i3) {
        addAction(new LayoutParamAction(i, i2, i3, 3));
    }

    public void setViewLayoutMarginAttr(int i, int i2, int i3) {
        addAction(new LayoutParamAction(i, i2, i3, 4));
    }

    public void setViewLayoutMargin(int i, int i2, float f, int i3) {
        addAction(new LayoutParamAction(i, i2, f, i3));
    }

    public void setViewLayoutWidth(int i, float f, int i2) {
        addAction(new LayoutParamAction(i, 8, f, i2));
    }

    public void setViewLayoutWidthDimen(int i, int i2) {
        addAction(new LayoutParamAction(i, 8, i2, 3));
    }

    public void setViewLayoutWidthAttr(int i, int i2) {
        addAction(new LayoutParamAction(i, 8, i2, 4));
    }

    public void semSetViewLayoutWidthAnimator(int i, int i2) {
        addAction(new LayoutParamAction(i, 8, i2));
    }

    public void semSetViewLayoutHeightAnimator(int i, int i2) {
        addAction(new LayoutParamAction(i, 9, i2));
    }

    public void setViewLayoutHeight(int i, float f, int i2) {
        addAction(new LayoutParamAction(i, 9, f, i2));
    }

    public void setViewLayoutHeightDimen(int i, int i2) {
        addAction(new LayoutParamAction(i, 9, i2, 3));
    }

    public void setViewLayoutHeightAttr(int i, int i2) {
        addAction(new LayoutParamAction(i, 9, i2, 4));
    }

    public void setViewOutlinePreferredRadius(int i, float f, int i2) {
        addAction(new SetViewOutlinePreferredRadiusAction(i, f, i2));
    }

    public void setViewOutlinePreferredRadiusDimen(int i, int i2) {
        addAction(new SetViewOutlinePreferredRadiusAction(i, i2, 3));
    }

    public void setViewOutlinePreferredRadiusAttr(int i, int i2) {
        addAction(new SetViewOutlinePreferredRadiusAction(i, i2, 4));
    }

    public void setBoolean(int i, String str, boolean z) {
        addAction(new ReflectionAction(i, str, 1, Boolean.valueOf(z)));
    }

    public void setByte(int i, String str, byte b) {
        addAction(new ReflectionAction(i, str, 2, Byte.valueOf(b)));
    }

    public void setShort(int i, String str, short s) {
        addAction(new ReflectionAction(i, str, 3, Short.valueOf(s)));
    }

    public void setInt(int i, String str, int i2) {
        addAction(new ReflectionAction(i, str, 4, Integer.valueOf(i2)));
    }

    public void setIntDimen(int i, String str, int i2) {
        addAction(new ResourceReflectionAction(i, str, 4, 1, i2));
    }

    public void setIntDimen(int i, String str, float f, int i2) {
        addAction(new ComplexUnitDimensionReflectionAction(i, str, 4, f, i2));
    }

    public void setIntDimenAttr(int i, String str, int i2) {
        addAction(new AttributeReflectionAction(i, str, 4, 1, i2));
    }

    private void hidden_semSetIntInteger(int i, String str, int i2) {
        addAction(new ResourceReflectionAction(i, str, 4, 4, i2));
    }

    public void setColor(int i, String str, int i2) {
        addAction(new ResourceReflectionAction(i, str, 4, 2, i2));
    }

    public void setColorAttr(int i, String str, int i2) {
        addAction(new AttributeReflectionAction(i, str, 4, 2, i2));
    }

    public void setColorInt(int i, String str, int i2, int i3) {
        addAction(new NightModeReflectionAction(i, str, 4, Integer.valueOf(i2), Integer.valueOf(i3)));
    }

    public void setColorStateList(int i, String str, ColorStateList colorStateList) {
        addAction(new ReflectionAction(i, str, 15, colorStateList));
    }

    public void setColorStateList(int i, String str, ColorStateList colorStateList, ColorStateList colorStateList2) {
        addAction(new NightModeReflectionAction(i, str, 15, colorStateList, colorStateList2));
    }

    public void setColorStateList(int i, String str, int i2) {
        addAction(new ResourceReflectionAction(i, str, 15, 2, i2));
    }

    public void setColorStateListAttr(int i, String str, int i2) {
        addAction(new AttributeReflectionAction(i, str, 15, 2, i2));
    }

    public void setLong(int i, String str, long j) {
        addAction(new ReflectionAction(i, str, 5, Long.valueOf(j)));
    }

    public void setFloat(int i, String str, float f) {
        addAction(new ReflectionAction(i, str, 6, Float.valueOf(f)));
    }

    public void setFloatDimen(int i, String str, int i2) {
        addAction(new ResourceReflectionAction(i, str, 6, 1, i2));
    }

    public void setFloatDimen(int i, String str, float f, int i2) {
        addAction(new ComplexUnitDimensionReflectionAction(i, str, 6, f, i2));
    }

    public void setFloatDimenAttr(int i, String str, int i2) {
        addAction(new AttributeReflectionAction(i, str, 6, 1, i2));
    }

    public void setDouble(int i, String str, double d) {
        addAction(new ReflectionAction(i, str, 7, Double.valueOf(d)));
    }

    public void setChar(int i, String str, char c) {
        addAction(new ReflectionAction(i, str, 8, Character.valueOf(c)));
    }

    public void setString(int i, String str, String str2) {
        addAction(new ReflectionAction(i, str, 9, str2));
    }

    public void setCharSequence(int i, String str, CharSequence charSequence) {
        addAction(new ReflectionAction(i, str, 10, charSequence));
    }

    public void setCharSequence(int i, String str, int i2) {
        addAction(new ResourceReflectionAction(i, str, 10, 3, i2));
    }

    public void setCharSequenceAttr(int i, String str, int i2) {
        addAction(new AttributeReflectionAction(i, str, 10, 3, i2));
    }

    public void setUri(int i, String str, Uri uri) throws IOException {
        if (uri != null) {
            uri = uri.getCanonicalUri();
            if (StrictMode.vmFileUriExposureEnabled()) {
                uri.checkFileUriExposed("RemoteViews.setUri()");
            }
        }
        addAction(new ReflectionAction(i, str, 11, uri));
    }

    public void setBitmap(int i, String str, Bitmap bitmap) {
        addAction(new BitmapReflectionAction(this, i, str, bitmap));
    }

    public void setBlendMode(int i, String str, BlendMode blendMode) {
        addAction(new ReflectionAction(i, str, 17, blendMode));
    }

    public void setBundle(int i, String str, Bundle bundle) {
        addAction(new ReflectionAction(i, str, 13, bundle));
    }

    public void setIntent(int i, String str, Intent intent) {
        addAction(new ReflectionAction(i, str, 14, intent));
    }

    public void setIcon(int i, String str, Icon icon) {
        addAction(new ReflectionAction(i, str, 16, icon));
    }

    public void setIcon(int i, String str, Icon icon, Icon icon2) {
        addAction(new NightModeReflectionAction(i, str, 16, icon, icon2));
    }

    public void setContentDescription(int i, CharSequence charSequence) {
        setCharSequence(i, "setContentDescription", charSequence);
    }

    public void setAccessibilityTraversalBefore(int i, int i2) {
        setInt(i, "setAccessibilityTraversalBefore", i2);
    }

    public void setAccessibilityTraversalAfter(int i, int i2) {
        setInt(i, "setAccessibilityTraversalAfter", i2);
    }

    public void setLabelFor(int i, int i2) {
        setInt(i, "setLabelFor", i2);
    }

    public void setCompoundButtonChecked(int i, boolean z) {
        addAction(new SetCompoundButtonCheckedAction(i, z));
    }

    public void setRadioGroupChecked(int i, int i2) {
        addAction(new SetRadioGroupCheckedAction(i, i2));
    }

    public void setLightBackgroundLayoutId(int i) {
        this.mLightBackgroundLayoutId = i;
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
        if (!hasLandscapeAndPortraitLayouts()) {
            return hasSizedRemoteViews() ? findSmallestRemoteView() : this;
        }
        if (this.mIsForcedOrientation) {
            if (this.mIsPortrait) {
                return this.mPortrait;
            }
            return this.mLandscape;
        }
        int i = context.getResources().getConfiguration().orientation;
        if (this.isProductDEV) {
            Log.d(LOG_TAG, "getRemoteViewsToApply apply remoteViews orientation = " + i);
        }
        if (i == 2) {
            return this.mLandscape;
        }
        return this.mPortrait;
    }

    private static float squareDistance(SizeF sizeF, SizeF sizeF2) {
        float width = sizeF.getWidth() - sizeF2.getWidth();
        float height = sizeF.getHeight() - sizeF2.getHeight();
        return (width * width) + (height * height);
    }

    private static boolean fitsIn(SizeF sizeF, SizeF sizeF2) {
        return sizeF2 != null && Math.ceil((double) sizeF2.getWidth()) + 1.0d > ((double) sizeF.getWidth()) && Math.ceil((double) sizeF2.getHeight()) + 1.0d > ((double) sizeF.getHeight());
    }

    private RemoteViews findBestFitLayout(SizeF sizeF) {
        RemoteViews remoteViews = null;
        float fSquareDistance = Float.MAX_VALUE;
        for (RemoteViews remoteViews2 : this.mSizedRemoteViews) {
            SizeF idealSize = remoteViews2.getIdealSize();
            if (idealSize == null) {
                throw new IllegalStateException("Expected RemoteViews to have ideal size");
            }
            if (fitsIn(idealSize, sizeF)) {
                if (remoteViews == null) {
                    fSquareDistance = squareDistance(idealSize, sizeF);
                    remoteViews = remoteViews2;
                } else {
                    float fSquareDistance2 = squareDistance(idealSize, sizeF);
                    if (fSquareDistance2 < fSquareDistance) {
                        remoteViews = remoteViews2;
                        fSquareDistance = fSquareDistance2;
                    }
                }
            }
        }
        if (remoteViews != null) {
            return remoteViews;
        }
        Log.w(LOG_TAG, "Could not find a RemoteViews fitting the current size: " + sizeF);
        return findSmallestRemoteView();
    }

    public RemoteViews getRemoteViewsToApply(Context context, SizeF sizeF) {
        if (!hasSizedRemoteViews() || sizeF == null) {
            return getRemoteViewsToApply(context);
        }
        return findBestFitLayout(sizeF);
    }

    public RemoteViews getRemoteViewsToApplyIfDifferent(SizeF sizeF, SizeF sizeF2) {
        if (!hasSizedRemoteViews()) {
            return null;
        }
        RemoteViews remoteViewsFindSmallestRemoteView = sizeF == null ? findSmallestRemoteView() : findBestFitLayout(sizeF);
        RemoteViews remoteViewsFindBestFitLayout = findBestFitLayout(sizeF2);
        if (remoteViewsFindSmallestRemoteView != remoteViewsFindBestFitLayout) {
            return remoteViewsFindBestFitLayout;
        }
        return null;
    }

    public View apply(Context context, ViewGroup viewGroup) {
        return apply(context, viewGroup, null);
    }

    public View apply(Context context, ViewGroup viewGroup, InteractionHandler interactionHandler) {
        return apply(context, viewGroup, interactionHandler, (SizeF) null);
    }

    public View apply(Context context, ViewGroup viewGroup, InteractionHandler interactionHandler, SizeF sizeF) {
        return apply(context, viewGroup, sizeF, new ActionApplyParams().withInteractionHandler(interactionHandler));
    }

    public View applyWithTheme(Context context, ViewGroup viewGroup, InteractionHandler interactionHandler, int i) {
        return apply(context, viewGroup, (SizeF) null, new ActionApplyParams().withInteractionHandler(interactionHandler).withThemeResId(i));
    }

    public View apply(Context context, ViewGroup viewGroup, InteractionHandler interactionHandler, SizeF sizeF, ColorResources colorResources) {
        return apply(context, viewGroup, sizeF, new ActionApplyParams().withInteractionHandler(interactionHandler).withColorResources(colorResources));
    }

    public View apply(Context context, ViewGroup viewGroup, SizeF sizeF, ActionApplyParams actionApplyParams) {
        return apply(context, viewGroup, viewGroup, sizeF, actionApplyParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View apply(Context context, ViewGroup viewGroup, ViewGroup viewGroup2, SizeF sizeF, ActionApplyParams actionApplyParams) {
        RemoteViews remoteViewsToApply = getRemoteViewsToApply(context, sizeF);
        View viewInflateView = inflateView(context, remoteViewsToApply, viewGroup, actionApplyParams.applyThemeResId, actionApplyParams.colorResources);
        remoteViewsToApply.performApply(viewInflateView, viewGroup2, actionApplyParams);
        return viewInflateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View inflateView(Context context, RemoteViews remoteViews, ViewGroup viewGroup, int i, ColorResources colorResources) {
        String str;
        try {
            if (remoteViews.hasDrawInstructions()) {
                str = "RemoteViews#inflateViewWithDrawInstructions";
            } else {
                str = "RemoteViews#inflateView";
            }
            Trace.beginSection(str);
            return inflateViewInternal(context, remoteViews, viewGroup, i, colorResources);
        } finally {
            Trace.endSection();
        }
    }

    private View inflateViewInternal(Context context, RemoteViews remoteViews, ViewGroup viewGroup, int i, ColorResources colorResources) throws Resources.NotFoundException {
        View viewInflate;
        Context contextForResourcesEnsuringCorrectCachedApkPaths = getContextForResourcesEnsuringCorrectCachedApkPaths(context);
        if (colorResources != null) {
            colorResources.apply(contextForResourcesEnsuringCorrectCachedApkPaths);
        }
        Context remoteViewsContextWrapper = new RemoteViewsContextWrapper(context, contextForResourcesEnsuringCorrectCachedApkPaths);
        if (i != 0) {
            remoteViewsContextWrapper = new ContextThemeWrapper(remoteViewsContextWrapper, i);
        }
        if (remoteViews.hasDrawInstructions()) {
            RemoteComposePlayer remoteComposePlayer = new RemoteComposePlayer(remoteViewsContextWrapper);
            remoteComposePlayer.setDebug((Build.IS_USERDEBUG || Build.IS_ENG) ? 1 : 0);
            viewInflate = remoteComposePlayer;
        } else {
            LayoutInflater layoutInflaterCloneInContext = LayoutInflater.from(context).cloneInContext(remoteViewsContextWrapper);
            layoutInflaterCloneInContext.setFilter(shouldUseStaticFilter() ? INFLATER_FILTER : this);
            LayoutInflater.Factory2 factory2 = this.mLayoutInflaterFactory2;
            if (factory2 != null) {
                layoutInflaterCloneInContext.setFactory2(factory2);
            }
            viewInflate = layoutInflaterCloneInContext.inflate(remoteViews.getLayoutId(), viewGroup, false);
        }
        try {
            if (this.isProductDEV && (viewGroup instanceof AppWidgetHostView)) {
                Log.d(LOG_TAG, "inflateView, package = " + remoteViewsContextWrapper.getPackageName() + ", layout = " + ((Object) remoteViewsContextWrapper.getResources().getText(remoteViews.getLayoutId())) + ", App Config = " + remoteViewsContextWrapper.getResources().getConfiguration());
            }
            int i2 = this.mViewId;
            if (i2 != -1) {
                viewInflate.setId(i2);
                viewInflate.setTagInternal(R.id.remote_views_override_id, Integer.valueOf(this.mViewId));
            }
            viewInflate.setTagInternal(16908312, Integer.valueOf(remoteViews.getLayoutId()));
            return viewInflate;
        } catch (RuntimeException e) {
            Log.w(LOG_TAG, "inflate error, layoutId = " + remoteViews.getLayoutId());
            int i3 = 0;
            for (ApkAssets apkAssets : remoteViewsContextWrapper.getAssets().getApkAssets()) {
                Log.w(LOG_TAG, NavigationBarInflaterView.SIZE_MOD_START + i3 + "], " + remoteViewsContextWrapper.getPackageName() + " : " + apkAssets);
                i3++;
            }
            throw e;
        }
    }

    protected boolean shouldUseStaticFilter() {
        return getClass().equals(RemoteViews.class);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup viewGroup, Executor executor, OnViewAppliedListener onViewAppliedListener) {
        return applyAsync(context, viewGroup, executor, onViewAppliedListener, null);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup viewGroup, Executor executor, OnViewAppliedListener onViewAppliedListener, InteractionHandler interactionHandler) {
        return applyAsync(context, viewGroup, executor, onViewAppliedListener, interactionHandler, null);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup viewGroup, Executor executor, OnViewAppliedListener onViewAppliedListener, InteractionHandler interactionHandler, SizeF sizeF) {
        return applyAsync(context, viewGroup, executor, onViewAppliedListener, interactionHandler, sizeF, null);
    }

    public CancellationSignal applyAsync(Context context, ViewGroup viewGroup, Executor executor, OnViewAppliedListener onViewAppliedListener, InteractionHandler interactionHandler, SizeF sizeF, ColorResources colorResources) {
        return new AsyncApplyTask(getRemoteViewsToApply(context, sizeF), viewGroup, context, onViewAppliedListener, new ActionApplyParams().withInteractionHandler(interactionHandler).withColorResources(colorResources).withExecutor(executor), null, true).startTaskOnExecutor(executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AsyncApplyTask getInternalAsyncApplyTask(Context context, ViewGroup viewGroup, OnViewAppliedListener onViewAppliedListener, ActionApplyParams actionApplyParams, SizeF sizeF, View view) {
        return new AsyncApplyTask(getRemoteViewsToApply(context, sizeF), viewGroup, context, onViewAppliedListener, actionApplyParams, view, false);
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

        private AsyncApplyTask(RemoteViews remoteViews, ViewGroup viewGroup, Context context, OnViewAppliedListener onViewAppliedListener, ActionApplyParams actionApplyParams, View view, boolean z) {
            this.mCancelSignal = new CancellationSignal();
            this.mRV = remoteViews;
            this.mParent = viewGroup;
            this.mContext = context;
            this.mListener = onViewAppliedListener;
            this.mTopLevel = z;
            this.mApplyParams = actionApplyParams;
            this.mResult = view;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public ViewTree doInBackground(Void... voidArr) {
            String str;
            try {
                if (this.mResult == null) {
                    this.mResult = RemoteViews.this.inflateView(this.mContext, this.mRV, this.mParent, 0, this.mApplyParams.colorResources);
                }
                this.mTree = new ViewTree(this.mResult);
                if (this.mRV.mActions != null) {
                    int size = this.mRV.mActions.size();
                    this.mActions = new Action[size];
                    try {
                        if (RemoteViews.this.hasDrawInstructions()) {
                            str = "RemoteViews#initActionAsyncWithDrawInstructions";
                        } else {
                            str = "RemoteViews#initActionAsync";
                        }
                        Trace.beginSection(str);
                        for (int i = 0; i < size; i++) {
                            if (isCancelled()) {
                                break;
                            }
                            this.mActions[i] = ((Action) this.mRV.mActions.get(i)).initActionAsync(this.mTree, this.mParent, this.mApplyParams);
                        }
                        Trace.endSection();
                    } catch (Throwable th) {
                        Trace.endSection();
                        throw th;
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
            String str;
            this.mCancelSignal.setOnCancelListener(null);
            if (this.mError == null) {
                OnViewAppliedListener onViewAppliedListener = this.mListener;
                if (onViewAppliedListener != null) {
                    onViewAppliedListener.onViewInflated(viewTree.mRoot);
                }
                try {
                    if (this.mActions != null) {
                        ActionApplyParams actionApplyParamsM7196clone = this.mApplyParams.m7196clone();
                        if (actionApplyParamsM7196clone.handler == null) {
                            actionApplyParamsM7196clone.handler = RemoteViews.DEFAULT_INTERACTION_HANDLER;
                        }
                        try {
                            if (RemoteViews.this.hasDrawInstructions()) {
                                str = "RemoteViews#applyActionsAsyncWithDrawInstructions";
                            } else {
                                str = "RemoteViews#applyActionsAsync";
                            }
                            Trace.beginSection(str);
                            for (Action action : this.mActions) {
                                action.apply(viewTree.mRoot, this.mParent, actionApplyParamsM7196clone);
                            }
                            Trace.endSection();
                        } catch (Throwable th) {
                            Trace.endSection();
                            throw th;
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
            if (executor == null) {
                executor = AsyncTask.THREAD_POOL_EXECUTOR;
            }
            executeOnExecutor(executor, new Void[0]);
            return this.mCancelSignal;
        }
    }

    public void reapply(Context context, View view) throws Resources.NotFoundException {
        reapply(context, view, null, new ActionApplyParams());
    }

    public void reapply(Context context, View view, InteractionHandler interactionHandler) throws Resources.NotFoundException {
        reapply(context, view, null, new ActionApplyParams().withInteractionHandler(interactionHandler));
    }

    public void reapply(Context context, View view, InteractionHandler interactionHandler, SizeF sizeF, ColorResources colorResources) throws Resources.NotFoundException {
        reapply(context, view, sizeF, new ActionApplyParams().withInteractionHandler(interactionHandler).withColorResources(colorResources));
    }

    public void reapply(Context context, View view, SizeF sizeF, ActionApplyParams actionApplyParams) throws Resources.NotFoundException {
        reapply(context, view, (ViewGroup) view.getParent(), sizeF, actionApplyParams, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reapplyNestedViews(Context context, View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws Resources.NotFoundException {
        reapply(context, view, viewGroup, null, actionApplyParams, false);
    }

    private void reapply(Context context, View view, ViewGroup viewGroup, SizeF sizeF, ActionApplyParams actionApplyParams, boolean z) throws Resources.NotFoundException {
        getRemoteViewsToReapply(context, view, sizeF).performApply(view, viewGroup, actionApplyParams);
        if (z && (view instanceof ViewGroup)) {
            finalizeViewRecycling((ViewGroup) view);
        }
    }

    public boolean canRecycleView(View view) {
        Integer num;
        if (view == null || hasDrawInstructions() || (num = (Integer) view.getTag(16908312)) == null) {
            return false;
        }
        Integer num2 = (Integer) view.getTag(R.id.remote_views_override_id);
        return num.intValue() == getLayoutId() && this.mViewId == (num2 == null ? -1 : num2.intValue());
    }

    private RemoteViews getRemoteViewsToReapply(Context context, View view, SizeF sizeF) {
        RemoteViews remoteViewsToApply = getRemoteViewsToApply(context, sizeF);
        if ((!hasMultipleLayouts() && remoteViewsToApply.mViewId == -1 && view.getTag(R.id.remote_views_override_id) == null) || remoteViewsToApply.canRecycleView(view)) {
            return remoteViewsToApply;
        }
        throw new RuntimeException("Attempting to re-apply RemoteViews to a view that that does not share the same root layout id.");
    }

    public CancellationSignal reapplyAsync(Context context, View view, Executor executor, OnViewAppliedListener onViewAppliedListener) {
        return reapplyAsync(context, view, executor, onViewAppliedListener, null);
    }

    public CancellationSignal reapplyAsync(Context context, View view, Executor executor, OnViewAppliedListener onViewAppliedListener, InteractionHandler interactionHandler) {
        return reapplyAsync(context, view, executor, onViewAppliedListener, interactionHandler, null, null);
    }

    public CancellationSignal reapplyAsync(Context context, View view, Executor executor, OnViewAppliedListener onViewAppliedListener, InteractionHandler interactionHandler, SizeF sizeF, ColorResources colorResources) {
        return new AsyncApplyTask(getRemoteViewsToReapply(context, view, sizeF), (ViewGroup) view.getParent(), context, onViewAppliedListener, new ActionApplyParams().withColorResources(colorResources).withInteractionHandler(interactionHandler).withExecutor(executor), view, true).startTaskOnExecutor(executor);
    }

    private void performApply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) {
        String str;
        ActionApplyParams actionApplyParamsM7196clone = actionApplyParams.m7196clone();
        if (actionApplyParamsM7196clone.handler == null) {
            actionApplyParamsM7196clone.handler = DEFAULT_INTERACTION_HANDLER;
        }
        if (view instanceof RemoteComposePlayer) {
            ((RemoteComposePlayer) view).setTheme(view.getResources().getConfiguration().isNightModeActive() ? -2 : -3);
        }
        ArrayList<Action> arrayList = this.mActions;
        if (arrayList != null) {
            int size = arrayList.size();
            try {
                if (hasDrawInstructions()) {
                    str = "RemoteViews#applyActionsWithDrawInstructions";
                } else {
                    str = "RemoteViews#applyActions";
                }
                Trace.beginSection(str);
                for (int i = 0; i < size; i++) {
                    this.mActions.get(i).apply(view, viewGroup, actionApplyParamsM7196clone);
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    public boolean prefersAsyncApply() {
        ArrayList<Action> arrayList = this.mActions;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (this.mActions.get(i).prefersAsyncApply()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateAppInfo(ApplicationInfo applicationInfo) {
        ApplicationInfo applicationInfo2 = this.mApplicationInfoCache.get(applicationInfo);
        if (applicationInfo2 == null || applicationInfo2.sourceDir.equals(applicationInfo.sourceDir)) {
            this.mApplicationInfoCache.put(applicationInfo);
            configureDescendantsAsChildren();
        }
    }

    private Context getContextForResourcesEnsuringCorrectCachedApkPaths(Context context) {
        if (this.mApplication != null && (context.getUserId() != UserHandle.getUserId(this.mApplication.uid) || !context.getPackageName().equals(this.mApplication.packageName))) {
            try {
                ApplicationInfo applicationInfoAsUser = this.mApplication;
                try {
                    applicationInfoAsUser = context.getPackageManager().getApplicationInfoAsUser(this.mApplication.packageName, 0, UserHandle.getUserId(this.mApplication.uid));
                } catch (SecurityException unused) {
                    Log.d(LOG_TAG, "Unable to fetch appInfo for " + this.mApplication.packageName);
                }
                return context.createApplicationContext(applicationInfoAsUser, 4).createConfigurationContext(context.getResources().getConfiguration());
            } catch (PackageManager.NameNotFoundException unused2) {
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

    private void tryAddRemoteResponse(int i) {
        PendingIntent pendingIntent = getPendingIntentTemplate().get(i);
        Intent intent = getFillInIntent().get(i);
        if (pendingIntent == null || intent == null) {
            return;
        }
        addAction(new SetOnClickResponse(i, RemoteResponse.fromPendingIntentTemplateAndFillInIntent(pendingIntent, intent)));
    }

    public class ActionApplyParams {
        public int applyThemeResId;
        public ColorResources colorResources;
        public Executor executor;
        public InteractionHandler handler;

        public ActionApplyParams() {
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public ActionApplyParams m7196clone() {
            return RemoteViews.this.new ActionApplyParams().withInteractionHandler(this.handler).withColorResources(this.colorResources).withExecutor(this.executor).withThemeResId(this.applyThemeResId);
        }

        public ActionApplyParams withInteractionHandler(InteractionHandler interactionHandler) {
            this.handler = interactionHandler;
            return this;
        }

        public ActionApplyParams withColorResources(ColorResources colorResources) {
            this.colorResources = colorResources;
            return this;
        }

        public ActionApplyParams withThemeResId(int i) {
            this.applyThemeResId = i;
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
        private static final String OVERLAY_NAME = "remote_views_color_resources";
        private static final String OVERLAY_TARGET_PACKAGE_NAME = "android";
        private final SparseIntArray mColorMapping;
        private final ResourcesLoader mLoader;

        private ColorResources(ResourcesLoader resourcesLoader, SparseIntArray sparseIntArray) {
            this.mLoader = resourcesLoader;
            this.mColorMapping = sparseIntArray;
        }

        public void apply(Context context) {
            context.getResources().addLoaders(this.mLoader);
        }

        public SparseIntArray getColorMapping() {
            return this.mColorMapping;
        }

        private static ByteArrayOutputStream readFileContent(InputStream inputStream) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
            byte[] bArr = new byte[4096];
            while (inputStream.available() > 0) {
                byteArrayOutputStream.write(bArr, 0, inputStream.read(bArr));
            }
            return byteArrayOutputStream;
        }

        private static byte[] createCompiledResourcesContent(Context context, SparseIntArray sparseIntArray) throws Resources.NotFoundException, IOException {
            InputStream inputStreamOpenRawResource = context.getResources().openRawResource(R.raw.remote_views_color_resources);
            try {
                byte[] byteArray = readFileContent(inputStreamOpenRawResource).toByteArray();
                if (inputStreamOpenRawResource != null) {
                    inputStreamOpenRawResource.close();
                }
                int length = byteArray.length - 3348;
                if (length < 0) {
                    Log.e(RemoteViews.LOG_TAG, "ARSC file for theme colors is invalid.");
                    return null;
                }
                for (int i = 17170461; i <= 17170641; i++) {
                    int i2 = ((65535 & i) * 16) + length;
                    int i3 = sparseIntArray.get(i, context.getColor(i));
                    for (int i4 = 0; i4 < 4; i4++) {
                        byteArray[i2 + i4] = (byte) (i3 & 255);
                        i3 >>= 8;
                    }
                }
                return byteArray;
            } catch (Throwable th) {
                if (inputStreamOpenRawResource != null) {
                    try {
                        inputStreamOpenRawResource.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public static ColorResources create(Context context, SparseIntArray sparseIntArray) throws Throwable {
            FileDescriptor fileDescriptorMemfd_create;
            try {
                byte[] bArrCreateCompiledResourcesContent = createCompiledResourcesContent(context, sparseIntArray);
                if (bArrCreateCompiledResourcesContent == null) {
                    return null;
                }
                try {
                    fileDescriptorMemfd_create = Os.memfd_create("remote_views_theme_colors.arsc", 0);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptorMemfd_create);
                        try {
                            fileOutputStream.write(bArrCreateCompiledResourcesContent);
                            ParcelFileDescriptor parcelFileDescriptorDup = ParcelFileDescriptor.dup(fileDescriptorMemfd_create);
                            try {
                                ResourcesLoader resourcesLoader = new ResourcesLoader();
                                resourcesLoader.addProvider(ResourcesProvider.loadFromTable(parcelFileDescriptorDup, null));
                                ColorResources colorResources = new ColorResources(resourcesLoader, sparseIntArray.m5539clone());
                                if (parcelFileDescriptorDup != null) {
                                    parcelFileDescriptorDup.close();
                                }
                                fileOutputStream.close();
                                if (fileDescriptorMemfd_create != null) {
                                    Os.close(fileDescriptorMemfd_create);
                                }
                                return colorResources;
                            } finally {
                            }
                        } finally {
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (fileDescriptorMemfd_create != null) {
                            Os.close(fileDescriptorMemfd_create);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileDescriptorMemfd_create = null;
                }
            } catch (Exception e) {
                Log.e(RemoteViews.LOG_TAG, "Failed to setup the context for theme colors", e);
                return null;
            }
        }

        public static ColorResources createWithOverlay(Context context, SparseIntArray sparseIntArray) {
            try {
                final String packageName = context.getPackageName();
                FabricatedOverlay fabricatedOverlayBuild = new FabricatedOverlay.Builder(packageName, OVERLAY_NAME, "android").build();
                for (int i = 0; i < sparseIntArray.size(); i++) {
                    fabricatedOverlayBuild.setResourceValue(context.getResources().getResourceName(sparseIntArray.keyAt(i)), 28, sparseIntArray.valueAt(i), (String) null);
                }
                OverlayManager overlayManager = (OverlayManager) context.getSystemService(OverlayManager.class);
                overlayManager.commit(new OverlayManagerTransaction.Builder().registerFabricatedOverlay(fabricatedOverlayBuild).setSelfTargeting(true).build());
                OverlayInfo overlayInfoOrElse = overlayManager.getOverlayInfosForTarget("android").stream().filter(new Predicate() { // from class: android.widget.RemoteViews$ColorResources$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return RemoteViews.ColorResources.lambda$createWithOverlay$0(packageName, (OverlayInfo) obj);
                    }
                }).findFirst().orElse(null);
                if (overlayInfoOrElse == null) {
                    Log.e(RemoteViews.LOG_TAG, "Failed to get overlay info ", new Throwable());
                    return null;
                }
                ResourcesLoader resourcesLoader = new ResourcesLoader();
                resourcesLoader.addProvider(ResourcesProvider.loadOverlay(overlayInfoOrElse));
                return new ColorResources(resourcesLoader, sparseIntArray.m5539clone());
            } catch (Exception e) {
                Log.e(RemoteViews.LOG_TAG, "Failed to add theme color overlay into loader", e);
                return null;
            }
        }

        static /* synthetic */ boolean lambda$createWithOverlay$0(String str, OverlayInfo overlayInfo) {
            return TextUtils.equals(overlayInfo.overlayName, OVERLAY_NAME) && TextUtils.equals(overlayInfo.packageName, str);
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
    public boolean onLoadClass(Class cls) {
        return cls.isAnnotationPresent(RemoteView.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcel(parcel, i, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeToParcel(Parcel parcel, int i, SparseArray<Intent> sparseArray) {
        boolean zAllowSquashing = parcel.allowSquashing();
        if (!hasMultipleLayouts()) {
            parcel.writeInt(0);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, i);
                this.mCollectionCache.writeToParcel(parcel, i, sparseArray);
            }
            parcel.writeTypedObject(this.mApplication, i);
            if (this.mIsRoot || this.mIdealSize == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.mIdealSize.writeToParcel(parcel, i);
            }
            parcel.writeInt(this.mLayoutId);
            parcel.writeInt(this.mViewId);
            parcel.writeInt(this.mLightBackgroundLayoutId);
            writeActionsToParcel(parcel, i);
        } else if (hasSizedRemoteViews()) {
            parcel.writeInt(2);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, i);
                this.mCollectionCache.writeToParcel(parcel, i, sparseArray);
            }
            parcel.writeInt(this.mSizedRemoteViews.size());
            Iterator<RemoteViews> it = this.mSizedRemoteViews.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcel, i);
            }
        } else {
            parcel.writeInt(1);
            if (this.mIsRoot) {
                this.mBitmapCache.writeBitmapsToParcel(parcel, i);
                this.mCollectionCache.writeToParcel(parcel, i, sparseArray);
            }
            this.mLandscape.writeToParcel(parcel, i);
            this.mPortrait.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mApplyFlags);
        parcel.writeLong(this.mProviderInstanceId);
        parcel.writeBoolean(this.mHasDrawInstructions);
        parcel.writeBoolean(this.mAllowOtherRootParent);
        if (this.mAllowOtherRootParent) {
            parcel.writeInt(this.mAppWidgetId);
        }
        parcel.restoreAllowSquashing(zAllowSquashing);
    }

    private void writeActionsToParcel(Parcel parcel, int i) {
        synchronized (this.mActionsLock) {
            ArrayList<Action> arrayList = this.mActions;
            int size = arrayList != null ? arrayList.size() : 0;
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                Action action = this.mActions.get(i2);
                parcel.writeInt(action.getActionTag());
                action.writeToParcel(parcel, i);
            }
        }
    }

    private static ApplicationInfo getApplicationInfo(String str, int i) {
        if (str == null) {
            return null;
        }
        Application applicationCurrentApplication = ActivityThread.currentApplication();
        if (applicationCurrentApplication == null) {
            throw new IllegalStateException("Cannot create remote views out of an aplication.");
        }
        ApplicationInfo applicationInfo = applicationCurrentApplication.getApplicationInfo();
        if (UserHandle.getUserId(applicationInfo.uid) == i && applicationInfo.packageName.equals(str)) {
            return applicationInfo;
        }
        try {
            return applicationCurrentApplication.getBaseContext().createPackageContextAsUser(str, 0, new UserHandle(i)).getApplicationInfo();
        } catch (PackageManager.NameNotFoundException unused) {
            throw new IllegalArgumentException("No such package " + str);
        }
    }

    public boolean hasSameAppInfo(ApplicationInfo applicationInfo) {
        ApplicationInfo applicationInfo2 = this.mApplication;
        if (applicationInfo2 != null) {
            return applicationInfo2.packageName.equals(applicationInfo.packageName) && this.mApplication.uid == applicationInfo.uid;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ViewTree {
        private static final int INSERT_AT_END_INDEX = -1;
        private ArrayList<ViewTree> mChildren;
        private View mRoot;

        private ViewTree(View view) {
            this.mRoot = view;
        }

        public void createTree() {
            if (this.mChildren != null) {
                return;
            }
            this.mChildren = new ArrayList<>();
            View view = this.mRoot;
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addViewChild(viewGroup.getChildAt(i));
                }
            }
        }

        public ViewTree findViewTreeById(int i) {
            if (this.mRoot.getId() == i) {
                return this;
            }
            ArrayList<ViewTree> arrayList = this.mChildren;
            if (arrayList == null) {
                return null;
            }
            Iterator<ViewTree> it = arrayList.iterator();
            while (it.hasNext()) {
                ViewTree viewTreeFindViewTreeById = it.next().findViewTreeById(i);
                if (viewTreeFindViewTreeById != null) {
                    return viewTreeFindViewTreeById;
                }
            }
            return null;
        }

        public ViewTree findViewTreeParentOf(ViewTree viewTree) {
            ArrayList<ViewTree> arrayList = this.mChildren;
            if (arrayList == null) {
                return null;
            }
            Iterator<ViewTree> it = arrayList.iterator();
            while (it.hasNext()) {
                ViewTree next = it.next();
                if (next == viewTree) {
                    return this;
                }
                ViewTree viewTreeFindViewTreeParentOf = next.findViewTreeParentOf(viewTree);
                if (viewTreeFindViewTreeParentOf != null) {
                    return viewTreeFindViewTreeParentOf;
                }
            }
            return null;
        }

        public void replaceView(View view) {
            this.mRoot = view;
            this.mChildren = null;
            createTree();
        }

        public <T extends View> T findViewById(int i) {
            if (this.mChildren == null) {
                return (T) this.mRoot.findViewById(i);
            }
            ViewTree viewTreeFindViewTreeById = findViewTreeById(i);
            if (viewTreeFindViewTreeById == null) {
                return null;
            }
            return (T) viewTreeFindViewTreeById.mRoot;
        }

        public void addChild(ViewTree viewTree) {
            addChild(viewTree, -1);
        }

        public void addChild(ViewTree viewTree, int i) {
            if (this.mChildren == null) {
                this.mChildren = new ArrayList<>();
            }
            viewTree.createTree();
            if (i == -1) {
                this.mChildren.add(viewTree);
            } else {
                this.mChildren.add(i, viewTree);
            }
        }

        public void removeChildren(int i, int i2) {
            if (this.mChildren != null) {
                for (int i3 = 0; i3 < i2; i3++) {
                    this.mChildren.remove(i);
                }
            }
        }

        private void addViewChild(View view) {
            if (view.isRootNamespace()) {
                return;
            }
            if (view.getId() != 0) {
                ViewTree viewTree = new ViewTree(view);
                this.mChildren.add(viewTree);
                this = viewTree;
            }
            if ((view instanceof ViewGroup) && this.mChildren == null) {
                this.mChildren = new ArrayList<>();
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    this.addViewChild(viewGroup.getChildAt(i));
                }
            }
        }

        public int findChildIndex(Predicate<View> predicate) {
            return findChildIndex(0, predicate);
        }

        public int findChildIndex(int i, Predicate<View> predicate) {
            if (this.mChildren == null) {
                return -1;
            }
            while (i < this.mChildren.size()) {
                if (predicate.test(this.mChildren.get(i).mRoot)) {
                    return i;
                }
                i++;
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
            RemoteResponse remoteResponse = new RemoteResponse();
            remoteResponse.mPendingIntent = pendingIntent;
            return remoteResponse;
        }

        public static RemoteResponse fromFillInIntent(Intent intent) {
            RemoteResponse remoteResponse = new RemoteResponse();
            remoteResponse.mFillIntent = intent;
            if (intent != null) {
                intent.collectExtraIntentKeys();
            }
            return remoteResponse;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static RemoteResponse fromPendingIntentTemplateAndFillInIntent(PendingIntent pendingIntent, Intent intent) {
            RemoteResponse remoteResponse = new RemoteResponse();
            remoteResponse.mPendingIntent = pendingIntent;
            remoteResponse.mFillIntent = intent;
            intent.collectExtraIntentKeys();
            return remoteResponse;
        }

        public RemoteResponse addSharedElement(int i, String str) {
            if (this.mViewIds == null) {
                this.mViewIds = new IntArray();
                this.mElementNames = new ArrayList<>();
            }
            this.mViewIds.add(i);
            this.mElementNames.add(str);
            return this;
        }

        public RemoteResponse setInteractionType(int i) {
            this.mInteractionType = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel, int i) {
            PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, parcel);
            parcel.writeBoolean(this.mFillIntent != null);
            Intent intent = this.mFillIntent;
            if (intent != null) {
                parcel.writeTypedObject(intent, i);
            }
            parcel.writeInt(this.mInteractionType);
            IntArray intArray = this.mViewIds;
            parcel.writeIntArray(intArray == null ? null : intArray.toArray());
            parcel.writeStringList(this.mElementNames);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readFromParcel(Parcel parcel) {
            this.mPendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
            this.mFillIntent = parcel.readBoolean() ? (Intent) parcel.readTypedObject(Intent.CREATOR) : null;
            this.mInteractionType = parcel.readInt();
            int[] iArrCreateIntArray = parcel.createIntArray();
            this.mViewIds = iArrCreateIntArray != null ? IntArray.wrap(iArrCreateIntArray) : null;
            this.mElementNames = parcel.createStringArrayList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleViewInteraction(View view, InteractionHandler interactionHandler) {
            PendingIntent pendingIntent = this.mPendingIntent;
            if (pendingIntent == null) {
                if (this.mFillIntent != null) {
                    AdapterView<?> adapterViewAncestor = getAdapterViewAncestor(view);
                    if (adapterViewAncestor == null) {
                        Log.e(RemoteViews.LOG_TAG, "Collection item doesn't have AdapterView parent");
                        return;
                    } else {
                        if (!(adapterViewAncestor.getTag() instanceof PendingIntent)) {
                            Log.e(RemoteViews.LOG_TAG, "Attempting setOnClickFillInIntent or setOnCheckedChangeFillInIntent without calling setPendingIntentTemplate on parent.");
                            return;
                        }
                        pendingIntent = (PendingIntent) adapterViewAncestor.getTag();
                    }
                } else {
                    Log.e(RemoteViews.LOG_TAG, "Response has neither pendingIntent nor fillInIntent");
                    return;
                }
            }
            interactionHandler.onInteraction(view, pendingIntent, this);
        }

        private static AdapterView<?> getAdapterViewAncestor(View view) {
            if (view == null) {
                return null;
            }
            View view2 = (View) view.getParent();
            while (view2 != null && !(view2 instanceof AdapterView) && (!(view2 instanceof AppWidgetHostView) || (view2 instanceof AppWidgetHostView.AdapterChildHostView))) {
                view2 = (View) view2.getParent();
            }
            if (view2 instanceof AdapterView) {
                return (AdapterView) view2;
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x006d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Pair<Intent, ActivityOptions> getLaunchOptions(View view) throws Resources.NotFoundException {
            ActivityOptions activityOptionsMakeBasic;
            Intent intent = this.mFillIntent == null ? new Intent() : new Intent(this.mFillIntent);
            intent.setSourceBounds(RemoteViews.getSourceBounds(view));
            if ((view instanceof CompoundButton) && this.mInteractionType == 1) {
                intent.putExtra(RemoteViews.EXTRA_CHECKED, ((CompoundButton) view).isChecked());
            }
            Context context = view.getContext();
            if (context.getResources().getBoolean(R.bool.config_overrideRemoteViewsActivityTransition)) {
                TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(R.styleable.Window);
                TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(typedArrayObtainStyledAttributes.getResourceId(8, 0), R.styleable.WindowAnimation);
                int resourceId = typedArrayObtainStyledAttributes2.getResourceId(26, 0);
                typedArrayObtainStyledAttributes.recycle();
                typedArrayObtainStyledAttributes2.recycle();
                if (resourceId != 0) {
                    activityOptionsMakeBasic = ActivityOptions.makeCustomAnimation(context, resourceId, 0);
                    activityOptionsMakeBasic.setPendingIntentLaunchFlags(268435456);
                } else {
                    activityOptionsMakeBasic = null;
                }
            }
            if (activityOptionsMakeBasic == null && this.mViewIds != null && this.mElementNames != null) {
                View view2 = (View) view.getParent();
                while (view2 != null && !(view2 instanceof AppWidgetHostView)) {
                    view2 = (View) view2.getParent();
                }
                if (view2 instanceof AppWidgetHostView) {
                    int[] array = this.mViewIds.toArray();
                    ArrayList<String> arrayList = this.mElementNames;
                    activityOptionsMakeBasic = ((AppWidgetHostView) view2).createSharedElementActivityOptions(array, (String[]) arrayList.toArray(new String[arrayList.size()]), intent);
                }
            }
            if (activityOptionsMakeBasic == null) {
                activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setPendingIntentLaunchFlags(268435456);
            }
            if (view.getDisplay() != null) {
                activityOptionsMakeBasic.setLaunchDisplayId(view.getDisplay().getDisplayId());
            } else {
                Log.w(RemoteViews.LOG_TAG, "getLaunchOptions: view.getDisplay() is null!", new Exception());
            }
            activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(3);
            return Pair.create(intent, activityOptionsMakeBasic);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0049, code lost:
    
        r11 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean startPendingIntent(View view, PendingIntent pendingIntent, Pair<Intent, ActivityOptions> pair) {
        boolean z;
        KeyguardManager keyguardManager;
        try {
            Context context = view.getContext();
            Log.d(LOG_TAG, "startPendingIntent" + view.getClass().getName());
            if (pendingIntent.isActivity()) {
                Object parent = view.getParent();
                while (true) {
                    if (!(parent instanceof View)) {
                        break;
                    }
                    if (parent instanceof AppWidgetHostView) {
                        int hostType = ((AppWidgetHostView) parent).getHostType();
                        if (hostType == 2 || hostType == 4) {
                            z = true;
                        }
                    } else {
                        parent = ((View) parent).getParent();
                    }
                }
                Log.d(LOG_TAG, "startPendingIntent: onLockscreen = " + z);
                if (z && (keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)) != null && keyguardManager.isKeyguardLocked()) {
                    Intent intent = new Intent();
                    intent.putExtra("runOnCover", true);
                    intent.putExtra("ignoreKeyguardState", true);
                    keyguardManager.semSetPendingIntentAfterUnlock(pendingIntent, intent);
                    return true;
                }
            }
            context.startIntentSender(pendingIntent.getIntentSender(), pair.first, 0, 0, 0, pair.second.toBundle());
            return true;
        } catch (IntentSender.SendIntentException e) {
            Log.e(LOG_TAG, "Cannot send pending intent: ", e);
            return false;
        } catch (Exception e2) {
            Log.e(LOG_TAG, "Cannot send pending intent due to unknown exception: ", e2);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBitmapMemoryUsedByActions() {
        Iterator<Integer> it = getBitmapIdsUsedByActions(new HashSet()).iterator();
        int allocationByteCount = 0;
        while (it.hasNext()) {
            Bitmap bitmapForId = this.mBitmapCache.getBitmapForId(it.next().intValue());
            if (bitmapForId != null) {
                allocationByteCount += bitmapForId.getAllocationByteCount();
            }
        }
        return allocationByteCount;
    }

    private Set<Integer> getBitmapIdsUsedByActions(Set<Integer> set) {
        if (hasSizedRemoteViews()) {
            Iterator<RemoteViews> it = this.mSizedRemoteViews.iterator();
            while (it.hasNext()) {
                it.next().getBitmapIdsUsedByActions(set);
            }
        } else {
            if (hasLandscapeAndPortraitLayouts()) {
                this.mLandscape.getBitmapIdsUsedByActions(set);
                this.mPortrait.getBitmapIdsUsedByActions(set);
                return set;
            }
            ArrayList<Action> arrayList = this.mActions;
            if (arrayList != null) {
                Iterator<Action> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    Action next = it2.next();
                    if (next instanceof ViewGroupActionAdd) {
                        ViewGroupActionAdd viewGroupActionAdd = (ViewGroupActionAdd) next;
                        if (viewGroupActionAdd.mNestedViews != null) {
                            viewGroupActionAdd.mNestedViews.getBitmapIdsUsedByActions(set);
                        }
                    }
                    if (next instanceof BitmapReflectionAction) {
                        set.add(Integer.valueOf(((BitmapReflectionAction) next).mBitmapId));
                    }
                }
            }
        }
        return set;
    }

    public static final class RemoteCollectionItems implements Parcelable {
        public static final Parcelable.Creator<RemoteCollectionItems> CREATOR = new Parcelable.Creator<RemoteCollectionItems>() { // from class: android.widget.RemoteViews.RemoteCollectionItems.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RemoteCollectionItems createFromParcel(Parcel parcel) {
                return new RemoteCollectionItems(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RemoteCollectionItems[] newArray(int i) {
                return new RemoteCollectionItems[i];
            }
        };
        private final boolean mHasStableIds;
        private HierarchyRootData mHierarchyRootData;
        private final long[] mIds;
        private final int mViewTypeCount;
        private final RemoteViews[] mViews;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        RemoteCollectionItems(long[] jArr, RemoteViews[] remoteViewsArr, boolean z, int i) {
            this.mIds = jArr;
            this.mViews = remoteViewsArr;
            this.mHasStableIds = z;
            this.mViewTypeCount = i;
            if (jArr.length != remoteViewsArr.length) {
                throw new IllegalArgumentException("RemoteCollectionItems has different number of ids and views");
            }
            if (i < 1) {
                throw new IllegalArgumentException("View type count must be >= 1");
            }
            int iCount = (int) Arrays.stream(remoteViewsArr).mapToInt(new RemoteViews$RemoteCollectionItems$$ExternalSyntheticLambda1()).distinct().count();
            if (iCount > i) {
                throw new IllegalArgumentException("View type count is set to " + i + ", but the collection contains " + iCount + " different layout ids");
            }
            if (remoteViewsArr.length > 0) {
                setHierarchyRootData(remoteViewsArr[0].getHierarchyRootData());
                remoteViewsArr[0].mIsRoot = true;
            }
        }

        RemoteCollectionItems(Parcel parcel, HierarchyRootData hierarchyRootData) {
            this.mHasStableIds = parcel.readBoolean();
            this.mViewTypeCount = parcel.readInt();
            int i = parcel.readInt();
            if (i > 100000) {
                Log.e(RemoteViews.LOG_TAG, "Cannot allocate RemoteCollectionItems length=" + i + ", dataSize=" + parcel.dataSize());
                throw new IllegalArgumentException("Cannot allocate RemoteCollectionItems length=" + i + ", dataSize=" + parcel.dataSize());
            }
            long[] jArr = new long[i];
            this.mIds = jArr;
            parcel.readLongArray(jArr);
            boolean z = parcel.readBoolean();
            RemoteViews[] remoteViewsArr = new RemoteViews[i];
            this.mViews = remoteViewsArr;
            int i2 = 0;
            if (!z) {
                RemoteViews remoteViews = new RemoteViews(parcel);
                remoteViewsArr[0] = remoteViews;
                this.mHierarchyRootData = remoteViews.getHierarchyRootData();
                i2 = 1;
            } else {
                if (hierarchyRootData == null) {
                    throw new IllegalStateException("Cannot unparcel a RemoteCollectionItems that was parceled as attached without providing data for a root RemoteViews");
                }
                this.mHierarchyRootData = hierarchyRootData;
            }
            while (i2 < i) {
                this.mViews[i2] = new RemoteViews(parcel, this.mHierarchyRootData, null, 0);
                i2++;
            }
        }

        void setHierarchyRootData(HierarchyRootData hierarchyRootData) {
            this.mHierarchyRootData = hierarchyRootData;
            for (RemoteViews remoteViews : this.mViews) {
                remoteViews.configureAsChild(hierarchyRootData);
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            writeToParcel(parcel, i, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0044  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void writeToParcel(Parcel parcel, int i, boolean z) {
            boolean z2;
            boolean zAllowSquashing = parcel.allowSquashing();
            parcel.writeBoolean(this.mHasStableIds);
            parcel.writeInt(this.mViewTypeCount);
            parcel.writeInt(this.mIds.length);
            parcel.writeLongArray(this.mIds);
            if (z && this.mHierarchyRootData == null) {
                throw new IllegalStateException("Cannot call writeToParcelAttached for a RemoteCollectionItems without first calling setHierarchyRootData()");
            }
            parcel.writeBoolean(z);
            if (!z) {
                RemoteViews[] remoteViewsArr = this.mViews;
                if (remoteViewsArr.length <= 0 || remoteViewsArr[0].mIsRoot) {
                    z2 = false;
                } else {
                    z2 = true;
                    this.mViews[0].mIsRoot = true;
                }
            }
            for (RemoteViews remoteViews : this.mViews) {
                remoteViews.writeToParcel(parcel, i);
            }
            if (z2) {
                this.mViews[0].mIsRoot = false;
            }
            parcel.restoreAllowSquashing(zAllowSquashing);
        }

        public void writeToProto(Context context, ProtoOutputStream protoOutputStream) {
            writeToProto(context, protoOutputStream, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void writeToProto(Context context, ProtoOutputStream protoOutputStream, boolean z) {
            boolean z2;
            for (long j : this.mIds) {
                protoOutputStream.write(RemoteViewsProto.RemoteCollectionItems.IDS, j);
            }
            protoOutputStream.write(1133871366149L, z);
            if (!z) {
                RemoteViews[] remoteViewsArr = this.mViews;
                if (remoteViewsArr.length <= 0 || remoteViewsArr[0].mIsRoot) {
                    z2 = false;
                } else {
                    z2 = true;
                    this.mViews[0].mIsRoot = true;
                }
            }
            for (RemoteViews remoteViews : this.mViews) {
                long jStart = protoOutputStream.start(2246267895810L);
                remoteViews.writePreviewToProto(context, protoOutputStream);
                protoOutputStream.end(jStart);
            }
            if (z2) {
                this.mViews[0].mIsRoot = false;
            }
            protoOutputStream.write(1133871366147L, this.mHasStableIds);
            protoOutputStream.write(1120986464260L, this.mViewTypeCount);
        }

        public static RemoteCollectionItems createFromProto(Context context, ProtoInputStream protoInputStream) throws Exception {
            return createFromProto(protoInputStream).create(context, context.getResources(), null, 0);
        }

        public static PendingResources<RemoteCollectionItems> createFromProto(ProtoInputStream protoInputStream) throws Exception {
            final LongSparseArray longSparseArray = new LongSparseArray();
            longSparseArray.put(RemoteViewsProto.RemoteCollectionItems.IDS, new ArrayList());
            longSparseArray.put(2246267895810L, new ArrayList());
            while (protoInputStream.nextField() != -1) {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    ((ArrayList) longSparseArray.get(RemoteViewsProto.RemoteCollectionItems.IDS)).add(Long.valueOf(protoInputStream.readLong(RemoteViewsProto.RemoteCollectionItems.IDS)));
                } else if (fieldNumber == 2) {
                    long jStart = protoInputStream.start(2246267895810L);
                    ((ArrayList) longSparseArray.get(2246267895810L)).add(RemoteViews.createFromProto(protoInputStream));
                    protoInputStream.end(jStart);
                } else if (fieldNumber == 3) {
                    longSparseArray.put(1133871366147L, Boolean.valueOf(protoInputStream.readBoolean(1133871366147L)));
                } else if (fieldNumber == 4) {
                    longSparseArray.put(1120986464260L, Integer.valueOf(protoInputStream.readInt(1120986464260L)));
                } else if (fieldNumber == 5) {
                    longSparseArray.put(1133871366149L, Boolean.valueOf(protoInputStream.readBoolean(1133871366149L)));
                } else {
                    Log.w(RemoteViews.LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
            RemoteViews.checkContainsKeys(longSparseArray, new long[]{1120986464260L});
            return new PendingResources() { // from class: android.widget.RemoteViews$RemoteCollectionItems$$ExternalSyntheticLambda0
                @Override // android.widget.RemoteViews.PendingResources
                public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                    return RemoteViews.RemoteCollectionItems.lambda$createFromProto$0(longSparseArray, context, resources, hierarchyRootData, i);
                }
            };
        }

        static /* synthetic */ RemoteCollectionItems lambda$createFromProto$0(LongSparseArray longSparseArray, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
            int i2;
            List list = (List) longSparseArray.get(RemoteViewsProto.RemoteCollectionItems.IDS);
            long[] jArr = new long[list.size()];
            for (int i3 = 0; i3 < list.size(); i3++) {
                jArr[i3] = ((Long) list.get(i3)).longValue();
            }
            boolean zBooleanValue = ((Boolean) longSparseArray.get(1133871366149L, false)).booleanValue();
            List list2 = (List) longSparseArray.get(2246267895810L);
            int size = list2.size();
            RemoteViews[] remoteViewsArr = new RemoteViews[size];
            if (zBooleanValue && hierarchyRootData == null) {
                throw new IllegalStateException("Cannot create a RemoteCollectionItems from proto that was attached without providing HierarchyRootData");
            }
            if (zBooleanValue || list2.size() <= 0) {
                i2 = 0;
            } else {
                RemoteViews remoteViews = (RemoteViews) ((PendingResources) list2.get(0)).create(context, resources, null, 0);
                remoteViewsArr[0] = remoteViews;
                hierarchyRootData = remoteViews.getHierarchyRootData();
                i2 = 1;
            }
            while (i2 < size) {
                remoteViewsArr[i2] = (RemoteViews) ((PendingResources) list2.get(i2)).create(context, resources, hierarchyRootData, 0);
                i2++;
            }
            return new RemoteCollectionItems(jArr, remoteViewsArr, ((Boolean) longSparseArray.get(1133871366147L, false)).booleanValue(), ((Integer) longSparseArray.get(1120986464260L, 0)).intValue());
        }

        public long getItemId(int i) {
            return this.mIds[i];
        }

        public RemoteViews getItemView(int i) {
            return this.mViews[i];
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

            public Builder addItem(long j, RemoteViews remoteViews) {
                remoteViews.getClass();
                if (remoteViews.hasMultipleLayouts()) {
                    throw new IllegalArgumentException("RemoteViews used in a RemoteCollectionItems cannot specify separate layouts for orientations or sizes.");
                }
                this.mIds.add(j);
                this.mViews.add(remoteViews);
                return this;
            }

            public Builder setHasStableIds(boolean z) {
                this.mHasStableIds = z;
                return this;
            }

            public Builder setViewTypeCount(int i) {
                this.mViewTypeCount = i;
                return this;
            }

            public RemoteCollectionItems build() {
                if (this.mViewTypeCount < 1) {
                    this.mViewTypeCount = (int) this.mViews.stream().mapToInt(new RemoteViews$RemoteCollectionItems$$ExternalSyntheticLambda1()).distinct().count();
                }
                return new RemoteCollectionItems(this.mIds.toArray(), (RemoteViews[]) this.mViews.toArray(new RemoteViews[0]), this.mHasStableIds, Math.max(this.mViewTypeCount, 1));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(Consumer<Uri> consumer) {
            for (RemoteViews remoteViews : this.mViews) {
                remoteViews.visitUris(consumer);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitIcons(Consumer<Icon> consumer) {
            for (RemoteViews remoteViews : this.mViews) {
                remoteViews.visitIcons(consumer);
            }
        }
    }

    public static final class DrawInstructions {
        private static final long VERSION = 1;
        final List<byte[]> mInstructions;

        private DrawInstructions() {
            throw new UnsupportedOperationException("DrawInstructions cannot be instantiate without instructions");
        }

        private DrawInstructions(List<byte[]> list) {
            this.mInstructions = new ArrayList(list.size());
            for (byte[] bArr : list) {
                int length = bArr.length;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 0, bArr2, 0, length);
                this.mInstructions.add(bArr2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static DrawInstructions readFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == -1) {
                return null;
            }
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(parcel.readBlob());
            }
            return new DrawInstructions(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void writeToParcel(DrawInstructions drawInstructions, Parcel parcel, int i) {
            if (drawInstructions == null) {
                parcel.writeInt(-1);
                return;
            }
            List<byte[]> list = drawInstructions.mInstructions;
            parcel.writeInt(list.size());
            Iterator<byte[]> it = list.iterator();
            while (it.hasNext()) {
                parcel.writeBlob(it.next());
            }
        }

        public static long getSupportedVersion() {
            return CoreDocument.getDocumentApiLevel();
        }

        public static final class Builder {
            private final List<byte[]> mInstructions;

            public Builder(List<byte[]> list) {
                this.mInstructions = new ArrayList(list);
            }

            public DrawInstructions build() {
                return new DrawInstructions(this.mInstructions);
            }
        }
    }

    public void semSetOnLongClickPendingIntent(int i, PendingIntent pendingIntent) {
        addAction(new SemSetOnLongClickPendingIntent(this, i, pendingIntent));
    }

    private class SemSetOnLongClickPendingIntent extends Action {
        PendingIntent longClickPendingIntent;
        int viewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 100;
        }

        public SemSetOnLongClickPendingIntent(RemoteViews remoteViews, int i, PendingIntent pendingIntent) {
            super();
            this.viewId = i;
            this.longClickPendingIntent = pendingIntent;
        }

        public SemSetOnLongClickPendingIntent(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.viewId = parcel.readInt();
            this.longClickPendingIntent = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.viewId);
            this.longClickPendingIntent.writeToParcel(parcel, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) throws Resources.NotFoundException {
            View viewFindViewById = view.findViewById(this.viewId);
            if (viewFindViewById == null || this.longClickPendingIntent == null) {
                return;
            }
            viewFindViewById.setOnLongClickListener(new View.OnLongClickListener() { // from class: android.widget.RemoteViews.SemSetOnLongClickPendingIntent.1
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    Rect sourceBounds = RemoteViews.getSourceBounds(view2);
                    Intent intent = new Intent();
                    intent.setSourceBounds(sourceBounds);
                    actionApplyParams.handler.onInteraction(view2, SemSetOnLongClickPendingIntent.this.longClickPendingIntent, RemoteResponse.fromFillInIntent(intent));
                    return true;
                }
            });
        }
    }

    public void semSetOnLongClickPendingIntentTemplate(int i, PendingIntent pendingIntent) {
        addAction(new SemSetLongClickPendingIntentTemplate(this, i, pendingIntent));
    }

    private class SemSetLongClickPendingIntentTemplate extends Action {
        PendingIntent pendingIntentTemplate;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 101;
        }

        public SemSetLongClickPendingIntentTemplate(RemoteViews remoteViews, int i, PendingIntent pendingIntent) {
            super();
            this.mViewId = i;
            this.pendingIntentTemplate = pendingIntent;
        }

        public SemSetLongClickPendingIntentTemplate(RemoteViews remoteViews, Parcel parcel) {
            super();
            this.mViewId = parcel.readInt();
            this.pendingIntentTemplate = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        }

        @Override // android.widget.RemoteViews.Action
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mViewId);
            this.pendingIntentTemplate.writeToParcel(parcel, 0);
        }

        @Override // android.widget.RemoteViews.Action
        public void apply(View view, ViewGroup viewGroup, final ActionApplyParams actionApplyParams) {
            View viewFindViewById = view.findViewById(this.mViewId);
            if (viewFindViewById == null) {
                return;
            }
            if (viewFindViewById instanceof AdapterView) {
                ((AdapterView) viewFindViewById).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: android.widget.RemoteViews.SemSetLongClickPendingIntentTemplate.1
                    @Override // android.widget.AdapterView.OnItemLongClickListener
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view2, int i, long j) {
                        RemoteResponse remoteResponse;
                        if (view2 instanceof ViewGroup) {
                            ViewGroup viewGroup2 = (ViewGroup) view2;
                            int i2 = 0;
                            if (adapterView instanceof AdapterViewAnimator) {
                                viewGroup2 = (ViewGroup) viewGroup2.getChildAt(0);
                            }
                            if (viewGroup2 == null) {
                                return true;
                            }
                            int childCount = viewGroup2.getChildCount();
                            while (true) {
                                if (i2 >= childCount) {
                                    remoteResponse = null;
                                    break;
                                }
                                Object tag = viewGroup2.getChildAt(i2).getTag(R.id.fillInIntent);
                                if (tag instanceof RemoteResponse) {
                                    remoteResponse = (RemoteResponse) tag;
                                    break;
                                }
                                i2++;
                            }
                            if (remoteResponse == null) {
                                return true;
                            }
                            new Intent().setSourceBounds(RemoteViews.getSourceBounds(view2));
                            actionApplyParams.handler.onInteraction(view2, SemSetLongClickPendingIntentTemplate.this.pendingIntentTemplate, remoteResponse);
                        }
                        return true;
                    }
                });
                return;
            }
            Log.e(RemoteViews.LOG_TAG, "Cannot setLongClickPendingIntentTemplate on a view which is notan AdapterView (id: " + this.mViewId + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void semSetOnLongClickDragable(int i, ClipData clipData, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3, boolean z) {
        addAction(new SemSetOnLongClickDragable(this, i, clipData, pendingIntent, pendingIntent2, pendingIntent3, z));
    }

    private class SemSetOnLongClickDragable extends Action {
        ClipData clipData;
        PendingIntent dragEnterNotiIntent;
        PendingIntent dragExitNotiIntent;
        PendingIntent dragStartIntent;
        boolean isNeedToRemove;
        int viewId;

        @Override // android.widget.RemoteViews.Action
        public int getActionTag() {
            return 102;
        }

        public SemSetOnLongClickDragable(RemoteViews remoteViews, int i, ClipData clipData, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3, boolean z) {
            super();
            this.viewId = i;
            this.isNeedToRemove = z;
            this.clipData = clipData;
            this.dragStartIntent = pendingIntent;
            this.dragEnterNotiIntent = pendingIntent2;
            this.dragExitNotiIntent = pendingIntent3;
        }

        public SemSetOnLongClickDragable(RemoteViews remoteViews, Parcel parcel) {
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
        public void apply(View view, ViewGroup viewGroup, ActionApplyParams actionApplyParams) throws Resources.NotFoundException, ActionException {
            View viewFindViewById = view.findViewById(this.viewId);
            if (viewFindViewById != null) {
                viewFindViewById.setOnLongClickListener(new View.OnLongClickListener() { // from class: android.widget.RemoteViews.SemSetOnLongClickDragable.1
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view2) {
                        ViewGroup viewGroup2;
                        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view2);
                        Log.e(RemoteViews.LOG_TAG, "Drag info: " + SemSetOnLongClickDragable.this.clipData + SemSetOnLongClickDragable.this.dragStartIntent + SemSetOnLongClickDragable.this.isNeedToRemove);
                        view2.startDrag(SemSetOnLongClickDragable.this.clipData, dragShadowBuilder, null, 0);
                        if (SemSetOnLongClickDragable.this.dragStartIntent != null) {
                            try {
                                view2.getContext().startIntentSender(SemSetOnLongClickDragable.this.dragStartIntent.getIntentSender(), null, 268435456, 268435456, 0);
                            } catch (IntentSender.SendIntentException e) {
                                Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                            }
                        }
                        if (!SemSetOnLongClickDragable.this.isNeedToRemove || (viewGroup2 = (ViewGroup) view2.getParent()) == null) {
                            return true;
                        }
                        viewGroup2.removeView(view2);
                        return true;
                    }
                });
                if (this.dragEnterNotiIntent == null && this.dragExitNotiIntent == null) {
                    return;
                }
                viewFindViewById.setOnDragListener(new View.OnDragListener() { // from class: android.widget.RemoteViews.SemSetOnLongClickDragable.2
                    @Override // android.view.View.OnDragListener
                    public boolean onDrag(View view2, DragEvent dragEvent) {
                        int action = dragEvent.getAction();
                        if (action == 5) {
                            if (SemSetOnLongClickDragable.this.dragEnterNotiIntent == null) {
                                return true;
                            }
                            try {
                                view2.getContext().startIntentSender(SemSetOnLongClickDragable.this.dragEnterNotiIntent.getIntentSender(), null, 268435456, 268435456, 0);
                                return true;
                            } catch (IntentSender.SendIntentException e) {
                                Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e);
                                return true;
                            }
                        }
                        if (action != 6 || SemSetOnLongClickDragable.this.dragExitNotiIntent == null) {
                            return true;
                        }
                        try {
                            view2.getContext().startIntentSender(SemSetOnLongClickDragable.this.dragExitNotiIntent.getIntentSender(), null, 268435456, 268435456, 0);
                            return true;
                        } catch (IntentSender.SendIntentException e2) {
                            Log.e(RemoteViews.LOG_TAG, "Cannot send pending intent: ", e2);
                            return true;
                        }
                    }
                });
            }
        }
    }

    public int getViewId() {
        return this.mViewId;
    }

    public void setProviderInstanceId(long j) {
        this.mProviderInstanceId = j;
    }

    public long getProviderInstanceId() {
        return this.mProviderInstanceId;
    }

    private int getChildId(RemoteViews remoteViews) {
        if (remoteViews == this) {
            return 0;
        }
        if (hasSizedRemoteViews()) {
            for (int i = 0; i < this.mSizedRemoteViews.size(); i++) {
                if (this.mSizedRemoteViews.get(i) == remoteViews) {
                    return i + 1;
                }
            }
        }
        if (hasLandscapeAndPortraitLayouts()) {
            if (this.mLandscape == remoteViews) {
                return 1;
            }
            if (this.mPortrait == remoteViews) {
                return 2;
            }
        }
        return 0;
    }

    public long computeUniqueId(RemoteViews remoteViews) {
        int childId;
        if (this.mIsRoot) {
            long providerInstanceId = getProviderInstanceId();
            return providerInstanceId != -1 ? providerInstanceId << 8 : providerInstanceId;
        }
        if (remoteViews == null) {
            return -1L;
        }
        long providerInstanceId2 = remoteViews.getProviderInstanceId();
        if (providerInstanceId2 == -1 || (childId = remoteViews.getChildId(this)) == -1) {
            return -1L;
        }
        return childId | (providerInstanceId2 << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Pair<String, Integer> getPackageUserKey(ApplicationInfo applicationInfo) {
        if (applicationInfo == null || applicationInfo.packageName == null) {
            return null;
        }
        return Pair.create(applicationInfo.packageName, Integer.valueOf(applicationInfo.uid));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HierarchyRootData getHierarchyRootData() {
        return new HierarchyRootData(this.mBitmapCache, this.mCollectionCache, this.mApplicationInfoCache, this.mClassCookies);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class HierarchyRootData {
        final ApplicationInfoCache mApplicationInfoCache;
        final BitmapCache mBitmapCache;
        final Map<Class, Object> mClassCookies;
        final RemoteCollectionCache mRemoteCollectionCache;

        HierarchyRootData(BitmapCache bitmapCache, RemoteCollectionCache remoteCollectionCache, ApplicationInfoCache applicationInfoCache, Map<Class, Object> map) {
            this.mBitmapCache = bitmapCache;
            this.mRemoteCollectionCache = remoteCollectionCache;
            this.mApplicationInfoCache = applicationInfoCache;
            this.mClassCookies = map;
        }
    }

    public void writePreviewToProto(Context context, ProtoOutputStream protoOutputStream) {
        ApplicationInfo applicationInfo = this.mApplication;
        if (applicationInfo != null) {
            protoOutputStream.write(1138166333442L, applicationInfo.packageName);
            protoOutputStream.write(1120986464273L, this.mApplication.uid);
        }
        Resources resources = getContextForResourcesEnsuringCorrectCachedApkPaths(context).getResources();
        int i = this.mLayoutId;
        if (i != 0) {
            protoOutputStream.write(1138166333443L, resources.getResourceName(i));
        }
        int i2 = this.mLightBackgroundLayoutId;
        if (i2 != 0) {
            protoOutputStream.write(1138166333444L, resources.getResourceName(i2));
        }
        int i3 = this.mViewId;
        if (i3 != 0 && i3 != -1) {
            protoOutputStream.write(1138166333445L, resources.getResourceName(i3));
        }
        if (this.mIsRoot) {
            this.mBitmapCache.writeBitmapsToProto(protoOutputStream);
            this.mCollectionCache.writeToProto(context, protoOutputStream);
        }
        protoOutputStream.write(1133871366156L, this.mIsRoot);
        protoOutputStream.write(1120986464263L, this.mApplyFlags);
        protoOutputStream.write(1133871366157L, this.mHasDrawInstructions);
        long j = this.mProviderInstanceId;
        if (j != -1) {
            protoOutputStream.write(1112396529672L, j);
        }
        if (!hasMultipleLayouts()) {
            protoOutputStream.write(1120986464257L, 0);
            if (this.mIdealSize != null) {
                long jStart = protoOutputStream.start(1146756268038L);
                protoOutputStream.write(1108101562369L, this.mIdealSize.getWidth());
                protoOutputStream.write(1108101562370L, this.mIdealSize.getHeight());
                protoOutputStream.end(jStart);
            }
            ArrayList<Action> arrayList = this.mActions;
            if (arrayList != null) {
                Iterator<Action> it = arrayList.iterator();
                while (it.hasNext()) {
                    Action next = it.next();
                    if (next.canWriteToProto()) {
                        long jStart2 = protoOutputStream.start(2246267895824L);
                        next.writeToProto(protoOutputStream, context, resources);
                        protoOutputStream.end(jStart2);
                    }
                }
                return;
            }
            return;
        }
        if (hasSizedRemoteViews()) {
            protoOutputStream.write(1120986464257L, 2);
            for (RemoteViews remoteViews : this.mSizedRemoteViews) {
                long jStart3 = protoOutputStream.start(2246267895817L);
                remoteViews.writePreviewToProto(context, protoOutputStream);
                protoOutputStream.end(jStart3);
            }
            return;
        }
        protoOutputStream.write(1120986464257L, 1);
        long jStart4 = protoOutputStream.start(1146756268043L);
        this.mLandscape.writePreviewToProto(context, protoOutputStream);
        protoOutputStream.end(jStart4);
        long jStart5 = protoOutputStream.start(1146756268042L);
        this.mPortrait.writePreviewToProto(context, protoOutputStream);
        protoOutputStream.end(jStart5);
    }

    public static RemoteViews createPreviewFromProto(Context context, ProtoInputStream protoInputStream) throws Exception {
        return createFromProto(protoInputStream).create(context, context.getResources(), null, 0);
    }

    /* renamed from: android.widget.RemoteViews$3, reason: invalid class name */
    class AnonymousClass3 {
        final RemoteViews mRv = new RemoteViews();
        int mMode = 0;
        int mApplyFlags = 0;
        long mProviderInstanceId = -1;
        String mPackageName = null;
        Integer mUid = null;
        SizeF mIdealSize = null;
        String mLayoutResName = null;
        String mLightBackgroundResName = null;
        String mViewResName = null;
        final List<PendingResources<Action>> mActions = new ArrayList();
        final List<PendingResources<RemoteViews>> mSizedRemoteViews = new ArrayList();
        PendingResources<RemoteViews> mLandscapeViews = null;
        PendingResources<RemoteViews> mPortraitViews = null;
        PendingResources<RemoteCollectionCache> mPopulateRemoteCollectionCache = null;
        boolean mIsRoot = false;
        boolean mHasDrawInstructions = false;

        AnonymousClass3() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PendingResources<RemoteViews> createFromProto(ProtoInputStream protoInputStream) throws Exception {
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        while (protoInputStream.nextField() != -1) {
            try {
                switch (protoInputStream.getFieldNumber()) {
                    case 1:
                        anonymousClass3.mMode = protoInputStream.readInt(1120986464257L);
                        break;
                    case 2:
                        anonymousClass3.mPackageName = protoInputStream.readString(1138166333442L);
                        break;
                    case 3:
                        anonymousClass3.mLayoutResName = protoInputStream.readString(1138166333443L);
                        break;
                    case 4:
                        anonymousClass3.mLightBackgroundResName = protoInputStream.readString(1138166333444L);
                        break;
                    case 5:
                        anonymousClass3.mViewResName = protoInputStream.readString(1138166333445L);
                        break;
                    case 6:
                        long jStart = protoInputStream.start(1146756268038L);
                        anonymousClass3.mIdealSize = createSizeFFromProto(protoInputStream);
                        protoInputStream.end(jStart);
                        break;
                    case 7:
                        anonymousClass3.mApplyFlags = protoInputStream.readInt(1120986464263L);
                        break;
                    case 8:
                        anonymousClass3.mProviderInstanceId = protoInputStream.readInt(1112396529672L);
                        break;
                    case 9:
                        long jStart2 = protoInputStream.start(2246267895817L);
                        anonymousClass3.mSizedRemoteViews.add(createFromProto(protoInputStream));
                        protoInputStream.end(jStart2);
                        break;
                    case 10:
                        long jStart3 = protoInputStream.start(1146756268042L);
                        anonymousClass3.mPortraitViews = createFromProto(protoInputStream);
                        protoInputStream.end(jStart3);
                        break;
                    case 11:
                        long jStart4 = protoInputStream.start(1146756268043L);
                        anonymousClass3.mLandscapeViews = createFromProto(protoInputStream);
                        protoInputStream.end(jStart4);
                        break;
                    case 12:
                        anonymousClass3.mIsRoot = protoInputStream.readBoolean(1133871366156L);
                        break;
                    case 13:
                        anonymousClass3.mHasDrawInstructions = protoInputStream.readBoolean(1133871366157L);
                        break;
                    case 14:
                        byte[] bytes = protoInputStream.readBytes(RemoteViewsProto.BITMAP_CACHE);
                        anonymousClass3.mRv.mBitmapCache.getBitmapId(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        break;
                    case 15:
                        long jStart5 = protoInputStream.start(1146756268047L);
                        anonymousClass3.mPopulateRemoteCollectionCache = anonymousClass3.mRv.populateRemoteCollectionCacheFromProto(protoInputStream);
                        protoInputStream.end(jStart5);
                        break;
                    case 16:
                        long jStart6 = protoInputStream.start(2246267895824L);
                        PendingResources<Action> pendingResourcesCreateActionFromProto = createActionFromProto(anonymousClass3.mRv, protoInputStream);
                        if (pendingResourcesCreateActionFromProto != null) {
                            anonymousClass3.mActions.add(pendingResourcesCreateActionFromProto);
                        }
                        protoInputStream.end(jStart6);
                        break;
                    case 17:
                        anonymousClass3.mUid = Integer.valueOf(protoInputStream.readInt(1120986464273L));
                        break;
                    default:
                        Log.w(LOG_TAG, "Unhandled field while reading RemoteViews proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new PendingResources() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda9
            @Override // android.widget.RemoteViews.PendingResources
            public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                return RemoteViews.lambda$createFromProto$9(anonymousClass3, context, resources, hierarchyRootData, i);
            }
        };
    }

    static /* synthetic */ RemoteViews lambda$createFromProto$9(AnonymousClass3 anonymousClass3, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
        Resources resources2;
        if (i > 10 && UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
            throw new IllegalArgumentException("Too many nested views.");
        }
        int i2 = i + 1;
        RemoteViews remoteViews = anonymousClass3.mRv;
        remoteViews.mApplyFlags = anonymousClass3.mApplyFlags;
        remoteViews.mIsRoot = anonymousClass3.mIsRoot;
        remoteViews.mHasDrawInstructions = anonymousClass3.mHasDrawInstructions;
        if (hierarchyRootData == null) {
            if (!remoteViews.mIsRoot || i2 != 1) {
                throw new IllegalStateException("A nested view did not receive HierarchyRootData");
            }
            hierarchyRootData = remoteViews.getHierarchyRootData();
        } else {
            remoteViews.configureAsChild(hierarchyRootData);
        }
        boolean z = false;
        Context context2 = null;
        if (anonymousClass3.mHasDrawInstructions) {
            resources2 = null;
        } else {
            checkProtoResultNotNull(anonymousClass3.mPackageName, "No application info");
            checkProtoResultNotNull(anonymousClass3.mUid, "No uid");
            remoteViews.mApplication = context.getPackageManager().getApplicationInfoAsUser(anonymousClass3.mPackageName, 0, UserHandle.getUserId(anonymousClass3.mUid.intValue()));
            Context contextForResourcesEnsuringCorrectCachedApkPaths = remoteViews.getContextForResourcesEnsuringCorrectCachedApkPaths(context);
            resources2 = contextForResourcesEnsuringCorrectCachedApkPaths.getResources();
            checkProtoResultNotNull(anonymousClass3.mLayoutResName, "No layout id");
            int identifier = resources2.getIdentifier(anonymousClass3.mLayoutResName, null, null);
            remoteViews.mLayoutId = identifier;
            checkValidResource(identifier, "Invalid layout id", anonymousClass3.mLayoutResName);
            if (anonymousClass3.mViewResName != null) {
                int identifier2 = resources2.getIdentifier(anonymousClass3.mViewResName, null, null);
                remoteViews.mViewId = identifier2;
                checkValidResource(identifier2, "Invalid view id", anonymousClass3.mViewResName);
            }
            if (anonymousClass3.mLightBackgroundResName != null) {
                int identifier3 = resources2.getIdentifier(anonymousClass3.mLightBackgroundResName, null, null);
                checkValidResource(identifier3, "Invalid light background layout id", anonymousClass3.mLightBackgroundResName);
                remoteViews.setLightBackgroundLayoutId(identifier3);
            }
            context2 = contextForResourcesEnsuringCorrectCachedApkPaths;
        }
        if (anonymousClass3.mPopulateRemoteCollectionCache != null) {
            anonymousClass3.mPopulateRemoteCollectionCache.create(context2, resources2, hierarchyRootData, i2);
        }
        if (anonymousClass3.mProviderInstanceId != -1) {
            remoteViews.mProviderInstanceId = anonymousClass3.mProviderInstanceId;
        }
        if (anonymousClass3.mMode == 0) {
            remoteViews.setIdealSize(anonymousClass3.mIdealSize);
            Iterator<PendingResources<Action>> it = anonymousClass3.mActions.iterator();
            while (it.hasNext()) {
                Action actionCreate = it.next().create(context2, resources2, hierarchyRootData, i2);
                if (actionCreate != null) {
                    if (actionCreate instanceof SetDrawInstructionAction) {
                        z = true;
                    }
                    remoteViews.addAction(actionCreate);
                }
            }
            if (!remoteViews.mHasDrawInstructions || z) {
                return remoteViews;
            }
            throw new InvalidProtoException("RemoteViews proto is missing DrawInstructions");
        }
        if (anonymousClass3.mMode == 2) {
            ArrayList arrayList = new ArrayList();
            Iterator<PendingResources<RemoteViews>> it2 = anonymousClass3.mSizedRemoteViews.iterator();
            while (it2.hasNext()) {
                arrayList.add(it2.next().create(context2, resources2, hierarchyRootData, i2));
            }
            remoteViews.initializeSizedRemoteViews(arrayList.iterator());
            return remoteViews;
        }
        if (anonymousClass3.mMode == 1) {
            checkProtoResultNotNull(anonymousClass3.mLandscapeViews, "Missing landscape views");
            checkProtoResultNotNull(anonymousClass3.mPortraitViews, "Missing portrait views");
            RemoteViews remoteViews2 = new RemoteViews(anonymousClass3.mLandscapeViews.create(context2, resources2, hierarchyRootData, i2), anonymousClass3.mPortraitViews.create(context2, resources2, hierarchyRootData, i2));
            remoteViews2.initializeFrom(remoteViews, remoteViews);
            return remoteViews2;
        }
        throw new InvalidProtoException(anonymousClass3.mMode + " is not a valid mode.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InvalidProtoException extends Exception {
        InvalidProtoException(String str) {
            super(str);
        }
    }

    private static PendingResources<Action> createActionFromProto(RemoteViews remoteViews, ProtoInputStream protoInputStream) throws Exception {
        int iNextField = protoInputStream.nextField();
        if (iNextField == -1) {
            return null;
        }
        switch (iNextField) {
            case 1:
                return AttributeReflectionAction.createFromProto(protoInputStream);
            case 2:
                return remoteViews.createFromBitmapReflectionActionFromProto(protoInputStream);
            case 3:
                return ComplexUnitDimensionReflectionAction.createFromProto(protoInputStream);
            case 4:
                return LayoutParamAction.createFromProto(protoInputStream);
            case 5:
                return NightModeReflectionAction.createFromProto(protoInputStream);
            case 6:
                return ReflectionAction.createFromProto(protoInputStream);
            case 7:
                return RemoveFromParentAction.createFromProto(protoInputStream);
            case 8:
                return ResourceReflectionAction.createFromProto(protoInputStream);
            case 9:
                return SetCompoundButtonCheckedAction.createFromProto(protoInputStream);
            case 10:
                return SetDrawableTint.createFromProto(protoInputStream);
            case 11:
                return SetEmptyView.createFromProto(protoInputStream);
            case 12:
                return SetIntTagAction.createFromProto(protoInputStream);
            case 13:
                return SetRadioGroupCheckedAction.createFromProto(protoInputStream);
            case 14:
                return remoteViews.createSetRemoteCollectionItemListAdapterActionFromProto(protoInputStream);
            case 15:
                return SetRippleDrawableColor.createFromProto(protoInputStream);
            case 16:
                return SetViewOutlinePreferredRadiusAction.createFromProto(protoInputStream);
            case 17:
                return TextViewDrawableAction.createFromProto(protoInputStream);
            case 18:
                return TextViewSizeAction.createFromProto(protoInputStream);
            case 19:
                return remoteViews.createViewGroupActionAddFromProto(protoInputStream);
            case 20:
                return ViewGroupActionRemove.createFromProto(protoInputStream);
            case 21:
                return ViewPaddingAction.createFromProto(protoInputStream);
            case 22:
                if (Flags.drawDataParcel()) {
                    return remoteViews.createSetDrawInstructionActionFromProto(protoInputStream);
                }
                return null;
            default:
                throw new RuntimeException("Unhandled field while reading Action proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
        }
    }

    private static void checkValidResource(int i, String str, String str2) throws Exception {
        if (i != 0) {
            return;
        }
        throw new Exception(str + ": " + str2);
    }

    private static void checkProtoResultNotNull(Object obj, String str) throws InvalidProtoException {
        if (obj == null) {
            throw new InvalidProtoException(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkContainsKeys(LongSparseArray<?> longSparseArray, long[] jArr) {
        for (long j : jArr) {
            if (longSparseArray.indexOfKey(j) < 0) {
                throw new IllegalArgumentException("RemoteViews proto missing field: " + ProtoStream.getFieldIdString(j));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getAsIdentifier(Resources resources, LongSparseArray<?> longSparseArray, long j) throws Exception {
        String str = (String) longSparseArray.get(j);
        int identifier = resources.getIdentifier(str, null, null);
        checkValidResource(identifier, "Invalid id", str);
        return identifier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getAsIdentifier(Resources resources, SparseArray<?> sparseArray, int i) throws Exception {
        String str = (String) sparseArray.get(i);
        int identifier = resources.getIdentifier(str, null, null);
        checkValidResource(identifier, "Invalid id", str);
        return identifier;
    }

    private static SizeF createSizeFFromProto(ProtoInputStream protoInputStream) throws Exception {
        float f = 0.0f;
        float f2 = 0.0f;
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                f = protoInputStream.readFloat(1108101562369L);
            } else if (fieldNumber == 2) {
                f2 = protoInputStream.readFloat(1108101562370L);
            } else {
                Log.w(LOG_TAG, "Unhandled field while reading SizeF proto!\n" + ProtoUtils.currentFieldToString(protoInputStream));
            }
        }
        return new SizeF(f, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeIconToProto(ProtoOutputStream protoOutputStream, Resources resources, Icon icon, long j) {
        long jStart = protoOutputStream.start(j);
        RemoteViewsSerializers.writeIconToProto(protoOutputStream, resources, icon);
        protoOutputStream.end(jStart);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PendingResources<Icon> createIconFromProto(ProtoInputStream protoInputStream, long j) throws Exception {
        long jStart = protoInputStream.start(j);
        final Function<Resources, Icon> functionCreateIconFromProto = RemoteViewsSerializers.createIconFromProto(protoInputStream);
        protoInputStream.end(jStart);
        return new PendingResources() { // from class: android.widget.RemoteViews$$ExternalSyntheticLambda10
            @Override // android.widget.RemoteViews.PendingResources
            public final Object create(Context context, Resources resources, RemoteViews.HierarchyRootData hierarchyRootData, int i) {
                return RemoteViews.lambda$createIconFromProto$10(functionCreateIconFromProto, context, resources, hierarchyRootData, i);
            }
        };
    }

    static /* synthetic */ Icon lambda$createIconFromProto$10(Function function, Context context, Resources resources, HierarchyRootData hierarchyRootData, int i) throws Exception {
        return (Icon) function.apply(resources);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeColorStateListToProto(ProtoOutputStream protoOutputStream, ColorStateList colorStateList, long j) {
        long jStart = protoOutputStream.start(j);
        colorStateList.writeToProto(protoOutputStream);
        protoOutputStream.end(jStart);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ColorStateList createColorStateListFromProto(ProtoInputStream protoInputStream, long j) throws Exception {
        long jStart = protoInputStream.start(j);
        ColorStateList colorStateListCreateFromProto = ColorStateList.createFromProto(protoInputStream);
        protoInputStream.end(jStart);
        return colorStateListCreateFromProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CharSequence createCharSequenceFromProto(ProtoInputStream protoInputStream, long j) throws Exception {
        long jStart = protoInputStream.start(j);
        CharSequence charSequenceCreateCharSequenceFromProto = RemoteViewsSerializers.createCharSequenceFromProto(protoInputStream);
        protoInputStream.end(jStart);
        return charSequenceCreateCharSequenceFromProto;
    }

    public boolean hasSetTextSizeAction() {
        if (this.mActions != null) {
            for (int i = 0; i < this.mActions.size(); i++) {
                Action action = this.mActions.get(i);
                if ((action instanceof TextViewSizeAction) || (action instanceof SetPercentTextSizeAction) || (action instanceof SetAutoSizeTextTypeUniformWithConfigurationAction)) {
                    return true;
                }
            }
        }
        return false;
    }
}
