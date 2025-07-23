package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.util.TypedValue;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.authentication.shared.model.AuthenticationPatternCoordinate;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PatternBouncerViewModel extends AuthMethodBouncerViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _currentDot;
    public final StateFlowImpl _dotColor;
    public final StateFlowImpl _dots;
    public final StateFlowImpl _isWhiteBg;
    public final Context applicationContext;
    public final AuthenticationMethodModel.Pattern authenticationMethod;
    public final int columnCount;
    public final ReadonlyStateFlow currentDot;
    public final StateFlowImpl dotColor;
    public final ReadonlyStateFlow dots;
    public final Lazy hitFactor$delegate;
    public final ReadonlyStateFlow isPatternVisible;
    public final ReadonlyStateFlow isWhiteBg;
    public final StateFlowImpl lineColor;
    public final Function0 onIntentionalUserInput;
    public final int rowCount;
    public final StateFlowImpl selectedDotList;
    public final StateFlowImpl selectedDotSet;
    public final ReadonlyStateFlow selectedDots;
    public final WallpaperManager wallpaperManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        PatternBouncerViewModel create(BouncerHapticPlayer bouncerHapticPlayer, StateFlow stateFlow, Function0 function0);
    }

    static {
        new Companion(null);
    }

    public PatternBouncerViewModel(Context context, BouncerInteractor bouncerInteractor, BouncerHapticPlayer bouncerHapticPlayer, StateFlow stateFlow, Function0 function0, UpdateInteractor updateInteractor, WallpaperManager wallpaperManager) {
        super(bouncerInteractor, stateFlow, "PatternBouncerViewModel", bouncerHapticPlayer, null);
        this.applicationContext = context;
        this.onIntentionalUserInput = function0;
        this.wallpaperManager = wallpaperManager;
        this.columnCount = 3;
        this.rowCount = 3;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new LinkedHashSet());
        this.selectedDotSet = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(CollectionsKt___CollectionsKt.toList((Iterable) MutableStateFlow.getValue()));
        this.selectedDotList = MutableStateFlow2;
        this.selectedDots = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._currentDot = MutableStateFlow3;
        this.currentDot = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(defaultDots());
        this._dots = MutableStateFlow4;
        this.dots = FlowKt.asStateFlow(MutableStateFlow4);
        this.isPatternVisible = bouncerInteractor.isPatternVisible;
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isWhiteBg = MutableStateFlow5;
        this.isWhiteBg = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(Color.m454boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pattern_dot_color))));
        this._dotColor = MutableStateFlow6;
        this.dotColor = MutableStateFlow6;
        this.lineColor = MutableStateFlow6;
        this.authenticationMethod = AuthenticationMethodModel.Pattern.INSTANCE;
        this.hitFactor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = PatternBouncerViewModel.$r8$clinit;
                TypedValue typedValue = new TypedValue();
                PatternBouncerViewModel.this.applicationContext.getResources().getValue(android.R.dimen.notification_top_pad_narrow, typedValue, true);
                return Float.valueOf(Math.max(Math.min(typedValue.getFloat(), 1.0f), 0.2f));
            }
        });
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void clearInput() {
        this._dots.setValue(defaultDots());
        this._currentDot.setValue(null);
        this.selectedDotSet.updateState(null, new LinkedHashSet());
    }

    public final ListBuilder defaultDots() {
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        IntProgressionIterator it = RangesKt___RangesKt.until(0, this.columnCount).iterator();
        while (it.hasNext) {
            int nextInt = it.nextInt();
            IntProgressionIterator it2 = RangesKt___RangesKt.until(0, this.rowCount).iterator();
            while (it2.hasNext) {
                createListBuilder.add(new PatternDotViewModel(nextInt, it2.nextInt()));
            }
        }
        return createListBuilder.build();
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final List getInput() {
        Iterable<PatternDotViewModel> iterable = (Iterable) this.selectedDotSet.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        for (PatternDotViewModel patternDotViewModel : iterable) {
            arrayList.add(new AuthenticationPatternCoordinate(patternDotViewModel.x, patternDotViewModel.y));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel, com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2 r5 = new com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
