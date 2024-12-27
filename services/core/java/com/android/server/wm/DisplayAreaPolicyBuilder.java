package com.android.server.wm;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.window.WindowContainerToken;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.policy.WindowManagerPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
class DisplayAreaPolicyBuilder {
    private final ArrayList mDisplayAreaGroupHierarchyBuilders = new ArrayList();
    private HierarchyBuilder mRootHierarchyBuilder;
    private BiFunction mSelectRootForWindowFunc;
    private Function mSelectTaskDisplayAreaFunc;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultSelectRootForWindowFunction implements BiFunction {
        public final List mDisplayAreaGroupRoots;
        public final RootDisplayArea mDisplayRoot;

        public DefaultSelectRootForWindowFunction(RootDisplayArea rootDisplayArea, List list) {
            this.mDisplayRoot = rootDisplayArea;
            this.mDisplayAreaGroupRoots = Collections.unmodifiableList(list);
        }

        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            Bundle bundle = (Bundle) obj2;
            if (this.mDisplayAreaGroupRoots.isEmpty()) {
                return this.mDisplayRoot;
            }
            if (bundle != null && bundle.containsKey("root_display_area_id")) {
                int i = bundle.getInt("root_display_area_id");
                RootDisplayArea rootDisplayArea = this.mDisplayRoot;
                if (rootDisplayArea.mFeatureId == i) {
                    return rootDisplayArea;
                }
                for (int size = this.mDisplayAreaGroupRoots.size() - 1; size >= 0; size--) {
                    if (((RootDisplayArea) this.mDisplayAreaGroupRoots.get(size)).mFeatureId == i) {
                        return (RootDisplayArea) this.mDisplayAreaGroupRoots.get(size);
                    }
                }
            }
            return this.mDisplayRoot;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultSelectTaskDisplayAreaFunction implements Function {
        public final TaskDisplayArea mDefaultTaskDisplayArea;
        public final int mDisplayId;

        public DefaultSelectTaskDisplayAreaFunction(TaskDisplayArea taskDisplayArea) {
            this.mDefaultTaskDisplayArea = taskDisplayArea;
            this.mDisplayId = taskDisplayArea.mDisplayContent.mDisplayId;
        }

        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            WindowContainerToken launchTaskDisplayArea;
            Bundle bundle = (Bundle) obj;
            if (bundle != null
                    && (launchTaskDisplayArea =
                                    new ActivityOptions(bundle).getLaunchTaskDisplayArea())
                            != null) {
                TaskDisplayArea asTaskDisplayArea =
                        WindowContainer.fromBinder(launchTaskDisplayArea.asBinder())
                                .asTaskDisplayArea();
                if (asTaskDisplayArea == null) {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_ORGANIZER_enabled[3]) {
                        ProtoLogImpl_54989576.w(
                                ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER,
                                4917824058925068521L,
                                0,
                                null,
                                String.valueOf(launchTaskDisplayArea));
                    }
                    return this.mDefaultTaskDisplayArea;
                }
                if (asTaskDisplayArea.mDisplayContent.mDisplayId == this.mDisplayId) {
                    return asTaskDisplayArea;
                }
                throw new IllegalArgumentException(
                        "The specified TaskDisplayArea must attach to Display#"
                                + this.mDisplayId
                                + ", but it is in Display#"
                                + asTaskDisplayArea.mDisplayContent.mDisplayId);
            }
            return this.mDefaultTaskDisplayArea;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Feature {
        public final int mId;
        public final String mName;
        public final NewDisplayAreaSupplier mNewDisplayAreaSupplier;
        public final boolean[] mWindowLayers;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Builder {
            public final int mId;
            public final boolean[] mLayers;
            public final String mName;
            public final WindowManagerPolicy mPolicy;
            public NewDisplayAreaSupplier mNewDisplayAreaSupplier =
                    new DisplayAreaPolicyBuilder$Feature$Builder$$ExternalSyntheticLambda0();
            public final boolean mExcludeRoundedCorner = true;

            public Builder(WindowManagerPolicy windowManagerPolicy, String str, int i) {
                this.mPolicy = windowManagerPolicy;
                this.mName = str;
                this.mId = i;
                windowManagerPolicy.getClass();
                this.mLayers = new boolean[37];
            }

            public final Feature build() {
                boolean z = this.mExcludeRoundedCorner;
                boolean[] zArr = this.mLayers;
                if (z) {
                    this.mPolicy.getClass();
                    zArr[36] = false;
                }
                return new Feature(
                        this.mName,
                        this.mId,
                        (boolean[]) zArr.clone(),
                        this.mNewDisplayAreaSupplier);
            }

            public final void except(int... iArr) {
                for (int i : iArr) {
                    set(i, false);
                }
            }

            public final int layerFromType(int i, boolean z) {
                this.mPolicy.getClass();
                return WindowManagerPolicy.getWindowLayerFromTypeLw(i, z, false);
            }

            public final void set(int i, boolean z) {
                int layerFromType = layerFromType(i, true);
                boolean[] zArr = this.mLayers;
                zArr[layerFromType] = z;
                if (i == 2038) {
                    zArr[layerFromType(i, true)] = z;
                    zArr[layerFromType(2003, false)] = z;
                    zArr[layerFromType(2006, false)] = z;
                    zArr[layerFromType(2010, false)] = z;
                }
            }
        }

        public Feature(
                String str, int i, boolean[] zArr, NewDisplayAreaSupplier newDisplayAreaSupplier) {
            this.mName = str;
            this.mId = i;
            this.mWindowLayers = zArr;
            this.mNewDisplayAreaSupplier = newDisplayAreaSupplier;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Feature(\"");
            sb.append(this.mName);
            sb.append("\", ");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.mId, '}');
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HierarchyBuilder {
        public DisplayArea.Tokens mImeContainer;
        public final RootDisplayArea mRoot;
        public final ArrayList mFeatures = new ArrayList();
        public final ArrayList mTaskDisplayAreas = new ArrayList();

        public HierarchyBuilder(RootDisplayArea rootDisplayArea) {
            this.mRoot = rootDisplayArea;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void build(List list) {
            RootDisplayArea rootDisplayArea = this.mRoot;
            rootDisplayArea.mWmService.mPolicy.getClass();
            int i = 37;
            DisplayArea.Tokens[] tokensArr = new DisplayArea.Tokens[37];
            ArrayMap arrayMap = new ArrayMap(this.mFeatures.size());
            int i2 = 0;
            for (int i3 = 0; i3 < this.mFeatures.size(); i3++) {
                arrayMap.put((Feature) this.mFeatures.get(i3), new ArrayList());
            }
            PendingArea[] pendingAreaArr = new PendingArea[37];
            Feature feature = null;
            PendingArea pendingArea = new PendingArea(null, 0, null);
            Arrays.fill(pendingAreaArr, pendingArea);
            int size = this.mFeatures.size();
            int i4 = 0;
            while (i4 < size) {
                Feature feature2 = (Feature) this.mFeatures.get(i4);
                PendingArea pendingArea2 = null;
                for (int i5 = i2; i5 < 37; i5++) {
                    if (feature2.mWindowLayers[i5]) {
                        if (pendingArea2 == null || pendingArea2.mParent != pendingAreaArr[i5]) {
                            pendingArea2 = new PendingArea(feature2, i5, pendingAreaArr[i5]);
                            pendingAreaArr[i5].mChildren.add(pendingArea2);
                        }
                        pendingAreaArr[i5] = pendingArea2;
                    } else {
                        pendingArea2 = null;
                    }
                }
                i4++;
                i2 = 0;
            }
            PendingArea pendingArea3 = null;
            int i6 = 0;
            boolean z = false;
            while (i6 < i) {
                boolean z2 =
                        i6 == 2
                                ? 1
                                : (i6 == WindowManagerPolicy.getWindowLayerFromTypeLw(2011)
                                                || i6
                                                        == WindowManagerPolicy
                                                                .getWindowLayerFromTypeLw(2012))
                                        ? 2
                                        : 0;
                if (pendingArea3 == null || pendingArea3.mParent != pendingAreaArr[i6] || z2 != z) {
                    pendingArea3 = new PendingArea(feature, i6, pendingAreaArr[i6]);
                    pendingAreaArr[i6].mChildren.add(pendingArea3);
                    if (z2 == 1) {
                        PendingArea pendingArea4 = pendingAreaArr[i6];
                        int size2 = this.mTaskDisplayAreas.size();
                        int i7 = 0;
                        while (i7 < size2) {
                            PendingArea pendingArea5 = new PendingArea(feature, 2, pendingArea4);
                            pendingArea5.mExisting = (DisplayArea) this.mTaskDisplayAreas.get(i7);
                            pendingArea5.mMaxLayer = 2;
                            pendingArea4.mChildren.add(pendingArea5);
                            i7++;
                            feature = null;
                        }
                        PendingArea pendingArea6 = pendingAreaArr[i6];
                        if (list != null) {
                            int size3 = list.size();
                            for (int i8 = 0; i8 < size3; i8++) {
                                PendingArea pendingArea7 = new PendingArea(null, 2, pendingArea6);
                                pendingArea7.mExisting = ((HierarchyBuilder) list.get(i8)).mRoot;
                                pendingArea7.mMaxLayer = 2;
                                pendingArea6.mChildren.add(pendingArea7);
                            }
                        }
                        pendingArea3.mSkipTokens = true;
                    } else if (z2 == 2) {
                        pendingArea3.mExisting = this.mImeContainer;
                        pendingArea3.mSkipTokens = true;
                    }
                    z = z2;
                }
                pendingArea3.mMaxLayer = i6;
                i6++;
                i = 37;
                feature = null;
            }
            pendingArea.computeMaxLayer();
            pendingArea.instantiateChildren(rootDisplayArea, tokensArr, 0, arrayMap);
            rootDisplayArea.onHierarchyBuilt(this.mFeatures, tokensArr, arrayMap);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface NewDisplayAreaSupplier {
        DisplayArea create(
                WindowManagerService windowManagerService,
                DisplayArea.Type type,
                String str,
                int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingArea {
        public DisplayArea mExisting;
        public final Feature mFeature;
        public int mMaxLayer;
        public final int mMinLayer;
        public final PendingArea mParent;
        public final ArrayList mChildren = new ArrayList();
        public boolean mSkipTokens = false;

        public PendingArea(Feature feature, int i, PendingArea pendingArea) {
            this.mMinLayer = i;
            this.mFeature = feature;
            this.mParent = pendingArea;
        }

        public final int computeMaxLayer() {
            for (int i = 0; i < this.mChildren.size(); i++) {
                this.mMaxLayer =
                        Math.max(
                                this.mMaxLayer,
                                ((PendingArea) this.mChildren.get(i)).computeMaxLayer());
            }
            return this.mMaxLayer;
        }

        public final void instantiateChildren(
                DisplayArea displayArea, DisplayArea.Tokens[] tokensArr, int i, Map map) {
            DisplayArea create;
            this.mChildren.sort(
                    Comparator.comparingInt(
                            new DisplayAreaPolicyBuilder$PendingArea$$ExternalSyntheticLambda0()));
            for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
                PendingArea pendingArea = (PendingArea) this.mChildren.get(i2);
                DisplayArea displayArea2 = pendingArea.mExisting;
                int i3 = pendingArea.mMinLayer;
                Feature feature = pendingArea.mFeature;
                if (displayArea2 != null) {
                    if (displayArea2.asTokens() != null) {
                        DisplayArea.Tokens asTokens = pendingArea.mExisting.asTokens();
                        while (i3 <= pendingArea.mMaxLayer) {
                            tokensArr[i3] = asTokens;
                            i3++;
                        }
                    }
                    create = pendingArea.mExisting;
                } else if (pendingArea.mSkipTokens) {
                    create = null;
                } else {
                    DisplayArea.Type type =
                            i3 > 2
                                    ? DisplayArea.Type.ABOVE_TASKS
                                    : pendingArea.mMaxLayer < 2
                                            ? DisplayArea.Type.BELOW_TASKS
                                            : DisplayArea.Type.ANY;
                    if (feature == null) {
                        WindowManagerService windowManagerService = displayArea.mWmService;
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(i3, "Leaf:", ":");
                        m.append(pendingArea.mMaxLayer);
                        DisplayArea.Tokens tokens =
                                new DisplayArea.Tokens(windowManagerService, type, m.toString(), 2);
                        while (i3 <= pendingArea.mMaxLayer) {
                            tokensArr[i3] = tokens;
                            i3++;
                        }
                        create = tokens;
                    } else {
                        WindowManagerService windowManagerService2 = displayArea.mWmService;
                        StringBuilder sb = new StringBuilder();
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                i3, feature.mName, ":", ":", sb);
                        sb.append(pendingArea.mMaxLayer);
                        create =
                                feature.mNewDisplayAreaSupplier.create(
                                        windowManagerService2, type, sb.toString(), feature.mId);
                    }
                }
                if (create != null) {
                    displayArea.addChild(create, Integer.MAX_VALUE);
                    if (feature != null) {
                        ((List) ((ArrayMap) map).get(feature)).add(create);
                    }
                    pendingArea.instantiateChildren(create, tokensArr, i + 1, map);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Result extends DisplayAreaPolicy {
        public final TaskDisplayArea mDefaultTaskDisplayArea;
        public final List mDisplayAreaGroupRoots;
        public final BiFunction mSelectRootForWindowFunc;
        public final Function mSelectTaskDisplayAreaFunc;

        public Result(
                RootDisplayArea rootDisplayArea,
                List list,
                BiFunction biFunction,
                Function function) {
            super(rootDisplayArea);
            this.mDisplayAreaGroupRoots = Collections.unmodifiableList(list);
            this.mSelectRootForWindowFunc = biFunction;
            TaskDisplayArea taskDisplayArea =
                    (TaskDisplayArea)
                            rootDisplayArea.getItemFromTaskDisplayAreas(
                                    new DisplayAreaPolicyBuilder$Result$$ExternalSyntheticLambda0());
            this.mDefaultTaskDisplayArea = taskDisplayArea;
            if (taskDisplayArea == null) {
                throw new IllegalStateException(
                        "No display area with FEATURE_DEFAULT_TASK_CONTAINER");
            }
            this.mSelectTaskDisplayAreaFunc =
                    function == null
                            ? new DefaultSelectTaskDisplayAreaFunction(taskDisplayArea)
                            : function;
        }

        public static void getDisplayAreas(RootDisplayArea rootDisplayArea, List list) {
            List list2 = rootDisplayArea.mFeatures;
            for (int i = 0; i < list2.size(); i++) {
                Feature feature = (Feature) list2.get(i);
                if (feature.mId == 10002) {
                    ((ArrayList) list)
                            .addAll(
                                    (Collection)
                                            rootDisplayArea.mFeatureToDisplayAreas.get(feature));
                }
            }
        }

        public DisplayArea.Tokens findAreaForToken(WindowToken windowToken) {
            return ((RootDisplayArea)
                            this.mSelectRootForWindowFunc.apply(
                                    Integer.valueOf(windowToken.windowType), windowToken.mOptions))
                    .findAreaForTokenInLayer(windowToken);
        }

        public List getFeatures() {
            ArraySet arraySet = new ArraySet();
            arraySet.addAll(this.mRoot.mFeatures);
            for (int i = 0; i < this.mDisplayAreaGroupRoots.size(); i++) {
                arraySet.addAll(((RootDisplayArea) this.mDisplayAreaGroupRoots.get(i)).mFeatures);
            }
            return new ArrayList(arraySet);
        }
    }

    public static boolean canBeWindowingLayer(int i) {
        return i == 4 || i == 9;
    }

    private static boolean containsDefaultTaskDisplayArea(HierarchyBuilder hierarchyBuilder) {
        for (int i = 0; i < hierarchyBuilder.mTaskDisplayAreas.size(); i++) {
            if (((TaskDisplayArea) hierarchyBuilder.mTaskDisplayAreas.get(i)).mFeatureId == 1) {
                return true;
            }
        }
        return false;
    }

    private void validate() {
        if (this.mRootHierarchyBuilder == null) {
            throw new IllegalStateException("Root must be set for the display area policy.");
        }
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        validateIds(this.mRootHierarchyBuilder, arraySet, arraySet2);
        HierarchyBuilder hierarchyBuilder = this.mRootHierarchyBuilder;
        boolean z = hierarchyBuilder.mImeContainer != null;
        boolean containsDefaultTaskDisplayArea = containsDefaultTaskDisplayArea(hierarchyBuilder);
        for (int i = 0; i < this.mDisplayAreaGroupHierarchyBuilders.size(); i++) {
            HierarchyBuilder hierarchyBuilder2 =
                    (HierarchyBuilder) this.mDisplayAreaGroupHierarchyBuilders.get(i);
            validateIds(hierarchyBuilder2, arraySet, arraySet2);
            if (hierarchyBuilder2.mTaskDisplayAreas.isEmpty()) {
                throw new IllegalStateException(
                        "DisplayAreaGroup must contain at least one TaskDisplayArea.");
            }
            if (!z) {
                z = hierarchyBuilder2.mImeContainer != null;
            } else if (hierarchyBuilder2.mImeContainer != null) {
                throw new IllegalStateException(
                        "Only one DisplayArea hierarchy can contain the IME container");
            }
            if (!containsDefaultTaskDisplayArea) {
                containsDefaultTaskDisplayArea = containsDefaultTaskDisplayArea(hierarchyBuilder2);
            } else if (containsDefaultTaskDisplayArea(hierarchyBuilder2)) {
                throw new IllegalStateException(
                        "Only one TaskDisplayArea can have the feature id of"
                            + " FEATURE_DEFAULT_TASK_CONTAINER");
            }
        }
        if (!z) {
            throw new IllegalStateException("IME container must be set.");
        }
        if (!containsDefaultTaskDisplayArea) {
            throw new IllegalStateException(
                    "There must be a default TaskDisplayArea with id of"
                        + " FEATURE_DEFAULT_TASK_CONTAINER.");
        }
        HierarchyBuilder hierarchyBuilder3 = this.mRootHierarchyBuilder;
        if (hierarchyBuilder3.mFeatures.isEmpty()
                || !canBeWindowingLayer(((Feature) hierarchyBuilder3.mFeatures.get(0)).mId)) {
            throw new IllegalStateException("WindowingLayer must exist at the top level index");
        }
    }

    private static void validateIds(HierarchyBuilder hierarchyBuilder, Set set, Set set2) {
        int i = hierarchyBuilder.mRoot.mFeatureId;
        if (!set2.add(Integer.valueOf(i)) || !set.add(Integer.valueOf(i))) {
            throw new IllegalStateException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "RootDisplayArea must have unique id, but id=", " is not unique."));
        }
        if (i > 20001) {
            throw new IllegalStateException(
                    "RootDisplayArea should not have an id greater than FEATURE_VENDOR_LAST.");
        }
        for (int i2 = 0; i2 < hierarchyBuilder.mTaskDisplayAreas.size(); i2++) {
            int i3 = ((TaskDisplayArea) hierarchyBuilder.mTaskDisplayAreas.get(i2)).mFeatureId;
            if (!set2.add(Integer.valueOf(i3)) || !set.add(Integer.valueOf(i3))) {
                throw new IllegalStateException(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i3,
                                "TaskDisplayArea must have unique id, but id=",
                                " is not unique."));
            }
            if (i3 > 20001) {
                throw new IllegalStateException(
                        "TaskDisplayArea declared in the policy should nothave an id greater than"
                            + " FEATURE_VENDOR_LAST.");
            }
        }
        ArraySet arraySet = new ArraySet();
        for (int i4 = 0; i4 < hierarchyBuilder.mFeatures.size(); i4++) {
            int i5 = ((Feature) hierarchyBuilder.mFeatures.get(i4)).mId;
            if (set.contains(Integer.valueOf(i5))) {
                throw new IllegalStateException(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i5,
                                "Feature must not have same id with any RootDisplayArea or"
                                    + " TaskDisplayArea, but id=",
                                " is used"));
            }
            if (!arraySet.add(Integer.valueOf(i5))) {
                throw new IllegalStateException(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i5,
                                "Feature below the same root must have unique id, but id=",
                                " is not unique."));
            }
            if (i5 > 20001) {
                throw new IllegalStateException(
                        "Feature should not have an id greater than FEATURE_VENDOR_LAST.");
            }
        }
        set2.addAll(arraySet);
    }

    public DisplayAreaPolicyBuilder addDisplayAreaGroupHierarchy(
            HierarchyBuilder hierarchyBuilder) {
        this.mDisplayAreaGroupHierarchyBuilders.add(hierarchyBuilder);
        return this;
    }

    public Result build(WindowManagerService windowManagerService) {
        HierarchyBuilder hierarchyBuilder = this.mRootHierarchyBuilder;
        if (hierarchyBuilder != null
                && (hierarchyBuilder.mFeatures.isEmpty()
                        || !canBeWindowingLayer(
                                ((Feature) hierarchyBuilder.mFeatures.get(0)).mId))) {
            ArrayList arrayList = this.mRootHierarchyBuilder.mFeatures;
            WindowManagerPolicy windowManagerPolicy = windowManagerService.mPolicy;
            DisplayAreaPolicyBuilder$Feature$Builder$$ExternalSyntheticLambda0
                    displayAreaPolicyBuilder$Feature$Builder$$ExternalSyntheticLambda0 =
                            new DisplayAreaPolicyBuilder$Feature$Builder$$ExternalSyntheticLambda0();
            windowManagerPolicy.getClass();
            boolean[] zArr = new boolean[37];
            Arrays.fill(zArr, true);
            arrayList.add(
                    0,
                    new Feature(
                            "WindowingLayer",
                            9,
                            (boolean[]) zArr.clone(),
                            displayAreaPolicyBuilder$Feature$Builder$$ExternalSyntheticLambda0));
        }
        validate();
        this.mRootHierarchyBuilder.build(this.mDisplayAreaGroupHierarchyBuilders);
        ArrayList arrayList2 = new ArrayList(this.mDisplayAreaGroupHierarchyBuilders.size());
        for (int i = 0; i < this.mDisplayAreaGroupHierarchyBuilders.size(); i++) {
            HierarchyBuilder hierarchyBuilder2 =
                    (HierarchyBuilder) this.mDisplayAreaGroupHierarchyBuilders.get(i);
            hierarchyBuilder2.build(null);
            arrayList2.add(hierarchyBuilder2.mRoot);
        }
        if (this.mSelectRootForWindowFunc == null) {
            this.mSelectRootForWindowFunc =
                    new DefaultSelectRootForWindowFunction(
                            this.mRootHierarchyBuilder.mRoot, arrayList2);
        }
        return new Result(
                this.mRootHierarchyBuilder.mRoot,
                arrayList2,
                this.mSelectRootForWindowFunc,
                this.mSelectTaskDisplayAreaFunc);
    }

    public DisplayAreaPolicyBuilder setRootHierarchy(HierarchyBuilder hierarchyBuilder) {
        this.mRootHierarchyBuilder = hierarchyBuilder;
        return this;
    }

    public DisplayAreaPolicyBuilder setSelectRootForWindowFunc(BiFunction biFunction) {
        this.mSelectRootForWindowFunc = biFunction;
        return this;
    }

    public DisplayAreaPolicyBuilder setSelectTaskDisplayAreaFunc(Function function) {
        this.mSelectTaskDisplayAreaFunc = function;
        return this;
    }
}
