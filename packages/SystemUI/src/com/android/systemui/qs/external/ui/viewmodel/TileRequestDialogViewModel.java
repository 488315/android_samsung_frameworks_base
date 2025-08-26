package com.android.systemui.qs.external.ui.viewmodel;

import android.app.IUriGrantsManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.external.TileData;
import com.android.systemui.qs.panels.ui.viewmodel.TileUiStateKt;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class TileRequestDialogViewModel extends ExclusiveActivatable {
    public static final QSTile.Icon defaultIcon;
    public final MutableState _icon$delegate = SnapshotStateKt.mutableStateOf$default(defaultIcon);
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context dialogContext;
    public final IUriGrantsManager iUriGrantsManager;
    public final State iconProvider$delegate;
    public final TileData tileData;
    public final State uiState$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        TileRequestDialogViewModel create(Context context, TileData tileData);
    }

    /* renamed from: com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TileRequestDialogViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TileRequestDialogViewModel.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Drawable drawableLoadDrawableCheckingUriGrant;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            TileRequestDialogViewModel tileRequestDialogViewModel = TileRequestDialogViewModel.this;
            TileData tileData = tileRequestDialogViewModel.tileData;
            Icon icon = tileData.icon;
            if (icon == null || (drawableLoadDrawableCheckingUriGrant = icon.loadDrawableCheckingUriGrant(tileRequestDialogViewModel.dialogContext, tileRequestDialogViewModel.iUriGrantsManager, tileData.callingUid, tileData.packageName)) == null) {
                return null;
            }
            ((SnapshotMutableStateImpl) TileRequestDialogViewModel.this._icon$delegate).setValue(new QSTileImpl.DrawableIcon(drawableLoadDrawableCheckingUriGrant));
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
        defaultIcon = QSTileImpl.ResourceIcon.get(R.drawable.android);
    }

    public TileRequestDialogViewModel(IUriGrantsManager iUriGrantsManager, CoroutineDispatcher coroutineDispatcher, Context context, TileData tileData) {
        this.iUriGrantsManager = iUriGrantsManager;
        this.backgroundDispatcher = coroutineDispatcher;
        this.dialogContext = context;
        this.tileData = tileData;
        final int i = 0;
        this.uiState$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ TileRequestDialogViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TileRequestDialogViewModel tileRequestDialogViewModel = this.f$0;
                switch (i) {
                    case 0:
                        QSTile.Icon icon = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state = new QSTile.State();
                        state.label = tileRequestDialogViewModel.tileData.label;
                        state.handlesLongClick = false;
                        state.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toUiState(state, tileRequestDialogViewModel.dialogContext.getResources());
                    default:
                        QSTile.Icon icon2 = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state2 = new QSTile.State();
                        state2.label = tileRequestDialogViewModel.tileData.label;
                        state2.handlesLongClick = false;
                        state2.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toIconProvider(state2);
                }
            }
        });
        final int i2 = 1;
        this.iconProvider$delegate = SnapshotStateKt.derivedStateOf(new Function0(this) { // from class: com.android.systemui.qs.external.ui.viewmodel.TileRequestDialogViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ TileRequestDialogViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TileRequestDialogViewModel tileRequestDialogViewModel = this.f$0;
                switch (i2) {
                    case 0:
                        QSTile.Icon icon = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state = new QSTile.State();
                        state.label = tileRequestDialogViewModel.tileData.label;
                        state.handlesLongClick = false;
                        state.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toUiState(state, tileRequestDialogViewModel.dialogContext.getResources());
                    default:
                        QSTile.Icon icon2 = TileRequestDialogViewModel.defaultIcon;
                        QSTile.State state2 = new QSTile.State();
                        state2.label = tileRequestDialogViewModel.tileData.label;
                        state2.handlesLongClick = false;
                        state2.icon = (QSTile.Icon) ((SnapshotMutableStateImpl) tileRequestDialogViewModel._icon$delegate).getValue();
                        return TileUiStateKt.toIconProvider(state2);
                }
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0050, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (BuildersKt.withContext(this.backgroundDispatcher, anonymousClass2, anonymousClass1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        ResultKt.throwOnFailure(obj);
        anonymousClass1.label = 2;
    }
}
