package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.statusbar.chips.sharetoapp.ui.view.EndGenericShareToAppDialogDelegate;
import com.android.systemui.statusbar.chips.sharetoapp.ui.view.EndShareScreenToAppDialogDelegate;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShareToAppChipViewModel$stopDialogDueToCallEndedState$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ShareToAppChipViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ProjectionChipModel.ContentType.values().length];
            try {
                iArr[ProjectionChipModel.ContentType.Screen.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionChipModel.ContentType.Audio.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProjectionChipModel.Receiver.values().length];
            try {
                iArr2[ProjectionChipModel.Receiver.ShareToApp.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ProjectionChipModel.Receiver.CastToOtherDevice.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShareToAppChipViewModel$stopDialogDueToCallEndedState$1(ShareToAppChipViewModel shareToAppChipViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = shareToAppChipViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ShareToAppChipViewModel$stopDialogDueToCallEndedState$1 shareToAppChipViewModel$stopDialogDueToCallEndedState$1 = new ShareToAppChipViewModel$stopDialogDueToCallEndedState$1(this.this$0, (Continuation) obj3);
        shareToAppChipViewModel$stopDialogDueToCallEndedState$1.L$0 = (ProjectionChipModel) obj2;
        return shareToAppChipViewModel$stopDialogDueToCallEndedState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ProjectionChipModel projectionChipModel = (ProjectionChipModel) this.L$0;
        if (projectionChipModel instanceof ProjectionChipModel.NotProjecting) {
            return MediaProjectionStopDialogModel.Hidden.INSTANCE;
        }
        if (!(projectionChipModel instanceof ProjectionChipModel.Projecting)) {
            throw new NoWhenBranchMatchedException();
        }
        ProjectionChipModel.Projecting projecting = (ProjectionChipModel.Projecting) projectionChipModel;
        int i = WhenMappings.$EnumSwitchMapping$1[projecting.receiver.ordinal()];
        if (i != 1) {
            if (i == 2) {
                return MediaProjectionStopDialogModel.Hidden.INSTANCE;
            }
            throw new NoWhenBranchMatchedException();
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[projecting.contentType.ordinal()];
        if (i2 == 1) {
            ShareToAppChipViewModel shareToAppChipViewModel = this.this$0;
            ShareToAppChipViewModel.Companion companion = ShareToAppChipViewModel.Companion;
            return new MediaProjectionStopDialogModel.Shown(new EndShareScreenToAppDialogDelegate(shareToAppChipViewModel.endMediaProjectionDialogHelper, shareToAppChipViewModel.context, new ShareToAppChipViewModel$createShareScreenToAppDialogDelegate$1(shareToAppChipViewModel), projecting), new ShareToAppChipViewModel$createShareScreenToAppStopDialog$1(shareToAppChipViewModel));
        }
        if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        ShareToAppChipViewModel shareToAppChipViewModel2 = this.this$0;
        ShareToAppChipViewModel.Companion companion2 = ShareToAppChipViewModel.Companion;
        return new MediaProjectionStopDialogModel.Shown(new EndGenericShareToAppDialogDelegate(shareToAppChipViewModel2.endMediaProjectionDialogHelper, shareToAppChipViewModel2.context, new ShareToAppChipViewModel$createGenericShareToAppDialogDelegate$1(shareToAppChipViewModel2)), new ShareToAppChipViewModel$createGenericShareScreenToAppStopDialog$1(shareToAppChipViewModel2));
    }
}
