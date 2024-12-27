package com.android.server.wm;

import android.util.Slog;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.wm.DisplayArea;
import com.android.server.wm.DisplayAreaPolicyBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class RootDisplayArea extends DisplayArea.Dimmable {
    private DisplayArea.Tokens[] mAreaForLayer;
    Map mFeatureToDisplayAreas;
    List mFeatures;
    private boolean mHasBuiltHierarchy;

    public RootDisplayArea(WindowManagerService windowManagerService, String str, int i) {
        super(windowManagerService, DisplayArea.Type.ANY, str, i);
    }

    @Override // com.android.server.wm.WindowContainer
    public RootDisplayArea asRootDisplayArea() {
        return this;
    }

    public DisplayArea.Tokens findAreaForTokenInLayer(WindowToken windowToken) {
        return findAreaForWindowTypeInLayer(windowToken.windowType, windowToken.mOwnerCanManageAppTokens, windowToken.mRoundedCornerOverlay);
    }

    public DisplayArea.Tokens findAreaForWindowTypeInLayer(int i, boolean z, boolean z2) {
        this.mWmService.mPolicy.getClass();
        int windowLayerFromTypeLw = WindowManagerPolicy.getWindowLayerFromTypeLw(i, z, z2);
        if (windowLayerFromTypeLw != 2) {
            return this.mAreaForLayer[windowLayerFromTypeLw];
        }
        throw new IllegalArgumentException("There shouldn't be WindowToken on APPLICATION_LAYER");
    }

    @Override // com.android.server.wm.WindowContainer
    public RootDisplayArea getRootDisplayArea() {
        return this;
    }

    public boolean isOrientationDifferentFromDisplay() {
        return false;
    }

    public void onHierarchyBuilt(ArrayList arrayList, DisplayArea.Tokens[] tokensArr, Map map) {
        if (this.mHasBuiltHierarchy) {
            throw new IllegalStateException("Root should only build the hierarchy once");
        }
        this.mHasBuiltHierarchy = true;
        this.mFeatures = Collections.unmodifiableList(arrayList);
        this.mAreaForLayer = tokensArr;
        this.mFeatureToDisplayAreas = map;
    }

    public boolean placeImeContainer(DisplayArea.Tokens tokens) {
        RootDisplayArea rootDisplayArea = tokens.getRootDisplayArea();
        List list = this.mFeatures;
        for (int i = 0; i < list.size(); i++) {
            DisplayAreaPolicyBuilder.Feature feature = (DisplayAreaPolicyBuilder.Feature) list.get(i);
            if (feature.mId == 7) {
                List list2 = (List) this.mFeatureToDisplayAreas.get(feature);
                if (list2.size() != 1) {
                    throw new IllegalStateException("There must be exactly one DisplayArea for the FEATURE_IME_PLACEHOLDER");
                }
                WindowManagerPolicy windowManagerPolicy = rootDisplayArea.mWmService.mPolicy;
                DisplayArea.Tokens[] tokensArr = rootDisplayArea.mAreaForLayer;
                windowManagerPolicy.getClass();
                tokensArr[WindowManagerPolicy.getWindowLayerFromTypeLw(2011)] = null;
                rootDisplayArea.mAreaForLayer[WindowManagerPolicy.getWindowLayerFromTypeLw(2012)] = null;
                tokens.reparent((WindowContainer) list2.get(0), Integer.MAX_VALUE);
                WindowManagerPolicy windowManagerPolicy2 = this.mWmService.mPolicy;
                DisplayArea.Tokens[] tokensArr2 = this.mAreaForLayer;
                windowManagerPolicy2.getClass();
                tokensArr2[WindowManagerPolicy.getWindowLayerFromTypeLw(2011)] = tokens;
                this.mAreaForLayer[WindowManagerPolicy.getWindowLayerFromTypeLw(2012)] = tokens;
                return true;
            }
        }
        if (!isDescendantOf(rootDisplayArea)) {
            Slog.w("WindowManager", "The IME target is not in the same root as the IME container, but there is no DisplayArea of FEATURE_IME_PLACEHOLDER in the target RootDisplayArea");
        }
        return false;
    }
}
