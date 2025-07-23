package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CacheDrawModifierNodeImpl extends Modifier.Node implements CacheDrawModifierNode, ObserverModifierNode, BuildDrawCacheParams {
    public Function1 block;
    public final CacheDrawScope cacheDrawScope;
    public ScopedGraphicsContext cachedGraphicsContext;
    public boolean isCacheValid;

    public CacheDrawModifierNodeImpl(CacheDrawScope cacheDrawScope, Function1 function1) {
        this.cacheDrawScope = cacheDrawScope;
        this.block = function1;
        cacheDrawScope.cacheParams = this;
        cacheDrawScope.graphicsContextProvider = new Function0() { // from class: androidx.compose.ui.draw.CacheDrawModifierNodeImpl.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CacheDrawModifierNodeImpl cacheDrawModifierNodeImpl = CacheDrawModifierNodeImpl.this;
                ScopedGraphicsContext scopedGraphicsContext = cacheDrawModifierNodeImpl.cachedGraphicsContext;
                if (scopedGraphicsContext == null) {
                    scopedGraphicsContext = new ScopedGraphicsContext();
                    cacheDrawModifierNodeImpl.cachedGraphicsContext = scopedGraphicsContext;
                }
                if (scopedGraphicsContext.graphicsContext == null) {
                    GraphicsContext requireGraphicsContext = DelegatableNodeKt.requireGraphicsContext(cacheDrawModifierNodeImpl);
                    scopedGraphicsContext.releaseGraphicsLayers();
                    scopedGraphicsContext.graphicsContext = requireGraphicsContext;
                }
                return scopedGraphicsContext;
            }
        };
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        boolean z = this.isCacheValid;
        final CacheDrawScope cacheDrawScope = this.cacheDrawScope;
        if (!z) {
            cacheDrawScope.drawResult = null;
            cacheDrawScope.contentDrawScope = layoutNodeDrawScope;
            ObserverModifierNodeKt.observeReads(this, new Function0() { // from class: androidx.compose.ui.draw.CacheDrawModifierNodeImpl$getOrBuildCachedDrawBlock$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CacheDrawModifierNodeImpl.this.block.mo779invoke(cacheDrawScope);
                    return Unit.INSTANCE;
                }
            });
            if (cacheDrawScope.drawResult == null) {
                throw AndroidAutofill$$ExternalSyntheticOutline0.m("DrawResult not defined, did you forget to call onDraw?");
            }
            this.isCacheValid = true;
        }
        DrawResult drawResult = cacheDrawScope.drawResult;
        drawResult.getClass();
        drawResult.block.mo779invoke(layoutNodeDrawScope);
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    public final Density getDensity() {
        return DelegatableNodeKt.requireLayoutNode(this).density;
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    public final LayoutDirection getLayoutDirection() {
        return DelegatableNodeKt.requireLayoutNode(this).layoutDirection;
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    /* renamed from: getSize-NH-jbRc */
    public final long mo360getSizeNHjbRc() {
        return IntSizeKt.m864toSizeozmzZPI(DelegatableNodeKt.m632requireCoordinator64DMado(this, 128).measuredSize);
    }

    @Override // androidx.compose.ui.draw.CacheDrawModifierNode
    public final void invalidateDrawCache() {
        ScopedGraphicsContext scopedGraphicsContext = this.cachedGraphicsContext;
        if (scopedGraphicsContext != null) {
            scopedGraphicsContext.releaseGraphicsLayers();
        }
        this.isCacheValid = false;
        this.cacheDrawScope.drawResult = null;
        DrawModifierNodeKt.invalidateDraw(this);
    }

    @Override // androidx.compose.ui.node.DelegatableNode, androidx.compose.ui.node.PointerInputModifierNode
    public final void onDensityChange() {
        invalidateDrawCache();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        ScopedGraphicsContext scopedGraphicsContext = this.cachedGraphicsContext;
        if (scopedGraphicsContext != null) {
            scopedGraphicsContext.releaseGraphicsLayers();
        }
    }

    @Override // androidx.compose.ui.node.DelegatableNode
    public final void onLayoutDirectionChange() {
        invalidateDrawCache();
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void onMeasureResultChanged() {
        invalidateDrawCache();
    }

    @Override // androidx.compose.ui.node.ObserverModifierNode
    public final void onObservedReadsChanged() {
        invalidateDrawCache();
    }
}
