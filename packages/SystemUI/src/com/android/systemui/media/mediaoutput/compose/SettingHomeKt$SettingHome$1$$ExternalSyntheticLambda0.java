package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.material3.DividerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.widget.ListsKt;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final /* synthetic */ class SettingHomeKt$SettingHome$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ViewModel f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ MutableState f$2;

    public /* synthetic */ SettingHomeKt$SettingHome$1$$ExternalSyntheticLambda0(LabsViewModel labsViewModel, MutableState mutableState, PointerInputScope pointerInputScope) {
        this.f$0 = labsViewModel;
        this.f$2 = mutableState;
        this.f$1 = pointerInputScope;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x009e  */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo781invoke(Object obj) {
        ViewModel viewModel = this.f$0;
        Object obj2 = this.f$1;
        MutableState mutableState = this.f$2;
        switch (this.$r8$classId) {
            case 0:
                LazyListScope lazyListScope = (LazyListScope) obj;
                final SettingViewModel settingViewModel = (SettingViewModel) viewModel;
                final Function1 function1 = (Function1) obj2;
                LazyListScope.item$default(lazyListScope, new ComposableLambdaImpl(1123544188, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                        Composer composer = (Composer) obj4;
                        if ((((Number) obj5).intValue() & 17) == 16) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SettingHome.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SettingHome.kt:87)");
                                }
                                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                                final Context context = (Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext);
                                composerImpl2.startReplaceGroup(1469335914);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                Composer.Companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(PackageManagerExtKt.isPackageInstalled(context.getPackageManager(), "com.spotify.music")));
                                    composerImpl2.updateRememberedValue(objRememberedValue);
                                }
                                final MutableState mutableState2 = (MutableState) objRememberedValue;
                                composerImpl2.end(false);
                                final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(settingViewModel.isSupportSpotifyMediaProvider, null, composerImpl2, 48);
                                final SettingViewModel settingViewModel2 = settingViewModel;
                                final Function1 function12 = function1;
                                ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(1445593329, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1.1
                                    /* JADX WARN: Removed duplicated region for block: B:17:0x007f  */
                                    /* JADX WARN: Removed duplicated region for block: B:30:0x0100  */
                                    /* JADX WARN: Removed duplicated region for block: B:47:0x0197  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                        Composer composer2 = (Composer) obj7;
                                        if ((((Number) obj8).intValue() & 17) == 16) {
                                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                            if (composerImpl3.getSkipping()) {
                                                composerImpl3.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SettingHome.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SettingHome.kt:92)");
                                                }
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                composerImpl4.startReplaceGroup(-216201666);
                                                DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
                                                Context context2 = context;
                                                deviceUtils.getClass();
                                                boolean supportCastSetting = DeviceUtils.getSupportCastSetting(context2);
                                                Composer.Companion companion = Composer.Companion;
                                                final SettingViewModel settingViewModel3 = settingViewModel2;
                                                ComposerImpl composerImpl5 = composerImpl4;
                                                if (supportCastSetting) {
                                                    DataStoreExt$special$$inlined$map$2 dataStoreExt$special$$inlined$map$2 = settingViewModel3.isCastingPriority;
                                                    Boolean bool = Boolean.FALSE;
                                                    MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(dataStoreExt$special$$inlined$map$2, bool, null, composerImpl4, 48, 2);
                                                    MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(settingViewModel3.isSpotifyCastingPriority, bool, null, composerImpl4, 48, 2);
                                                    composerImpl4.startReplaceGroup(-216192125);
                                                    Function1 function13 = function12;
                                                    boolean zChanged = composerImpl4.changed(function13);
                                                    Object objRememberedValue2 = composerImpl4.rememberedValue();
                                                    if (!zChanged) {
                                                        companion.getClass();
                                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                                            objRememberedValue2 = new SettingHomeKt$SettingHome$1$2$1$1$1$$ExternalSyntheticLambda0(function13, 0);
                                                            composerImpl4.updateRememberedValue(objRememberedValue2);
                                                        }
                                                        Function0 function0 = (Function0) objRememberedValue2;
                                                        composerImpl4.end(false);
                                                        ListsKt.SecListItem(function0, StringResources_androidKt.stringResource(R.string.cast_setting, composerImpl4), StringResources_androidKt.stringResource(((Boolean) mutableStateCollectAsState.getValue()).booleanValue() ? R.string.casting_priority : R.string.audio_mirroring_priority, composerImpl4), composerImpl4, 0, 0);
                                                        composerImpl5 = composerImpl4;
                                                        if (((Boolean) mutableState2.getValue()).booleanValue()) {
                                                            composerImpl5 = composerImpl4;
                                                            if (Intrinsics.areEqual((Boolean) mutableStateCollectAsStateWithLifecycle.getValue(), Boolean.TRUE)) {
                                                                Dp.Companion companion2 = Dp.Companion;
                                                                DividerKt.m263HorizontalDivider9IZ8Weo(PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 16, 0.0f, 2), 1, ColorKt.dividerColor(composerImpl4), composerImpl4, 54, 0);
                                                                ComposerImpl composerImpl6 = composerImpl4;
                                                                composerImpl6.startReplaceGroup(-216168938);
                                                                boolean zChanged2 = composerImpl6.changed(function13);
                                                                Object objRememberedValue3 = composerImpl6.rememberedValue();
                                                                if (!zChanged2) {
                                                                    companion.getClass();
                                                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                                                        objRememberedValue3 = new SettingHomeKt$SettingHome$1$2$1$1$1$$ExternalSyntheticLambda0(function13, 1);
                                                                        composerImpl6.updateRememberedValue(objRememberedValue3);
                                                                    }
                                                                    Function0 function02 = (Function0) objRememberedValue3;
                                                                    composerImpl6.end(false);
                                                                    ListsKt.SecListItem(function02, StringResources_androidKt.stringResource(R.string.spotify_cast_setting, composerImpl6), StringResources_androidKt.stringResource(((Boolean) mutableStateCollectAsState2.getValue()).booleanValue() ? R.string.casting_priority : R.string.audio_mirroring_priority, composerImpl6), composerImpl6, 0, 0);
                                                                    composerImpl5 = composerImpl6;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                composerImpl5.end(false);
                                                composerImpl5.startReplaceGroup(-216152083);
                                                ComposerImpl composerImpl7 = composerImpl5;
                                                if (DeviceUtils.getSupportCastSetting(context)) {
                                                    composerImpl7 = composerImpl5;
                                                    if (((Boolean) DeviceUtils.supportMusicShare$delegate.getValue()).booleanValue()) {
                                                        Dp.Companion companion3 = Dp.Companion;
                                                        ComposerImpl composerImpl8 = composerImpl5;
                                                        DividerKt.m263HorizontalDivider9IZ8Weo(PaddingKt.m127paddingVpY3zN4$default(Modifier.Companion, 16, 0.0f, 2), 1, ColorKt.dividerColor(composerImpl8), composerImpl8, 54, 0);
                                                        composerImpl7 = composerImpl8;
                                                    }
                                                }
                                                composerImpl7.end(false);
                                                if (((Boolean) DeviceUtils.supportMusicShare$delegate.getValue()).booleanValue()) {
                                                    MutableState mutableStateCollectAsState3 = SnapshotStateKt.collectAsState(settingViewModel3.isShowMusicShareEnabled, null, null, composerImpl7, 48, 2);
                                                    composerImpl7.startReplaceGroup(-216137658);
                                                    boolean zChangedInstance = composerImpl7.changedInstance(settingViewModel3);
                                                    Object objRememberedValue4 = composerImpl7.rememberedValue();
                                                    if (!zChangedInstance) {
                                                        companion.getClass();
                                                        if (objRememberedValue4 == Composer.Companion.Empty) {
                                                            objRememberedValue4 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$1$1$$ExternalSyntheticLambda2
                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj9) {
                                                                    settingViewModel3.setShowMusicShareEnabled(((Boolean) obj9).booleanValue());
                                                                    return Unit.INSTANCE;
                                                                }
                                                            };
                                                            composerImpl7.updateRememberedValue(objRememberedValue4);
                                                        }
                                                        composerImpl7.end(false);
                                                        ListsKt.SecSwitchListItem((Function1) objRememberedValue4, StringResources_androidKt.stringResource(R.string.show_music_share_setting, composerImpl7), StringResources_androidKt.stringResource(R.string.show_music_share_setting_description, composerImpl7), (Boolean) mutableStateCollectAsState3.getValue(), composerImpl7, 0, 0);
                                                    }
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl2), composerImpl2, 6);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }));
                if (((Boolean) mutableState.getValue()).booleanValue()) {
                    LazyListScope.item$default(lazyListScope, new ComposableLambdaImpl(-1057992233, true, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$2
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                            Composer composer = (Composer) obj4;
                            if ((((Number) obj5).intValue() & 17) == 16) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SettingHome.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SettingHome.kt:138)");
                                    }
                                    Dp.Companion companion = Dp.Companion;
                                    SpacerKt.Spacer(composer, SizeKt.m131height3ABfNKs(Modifier.Companion, 12));
                                    final Function1 function12 = function1;
                                    ListsKt.ListItemContainer(ComposableLambdaKt.rememberComposableLambda(-400615412, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.SettingHomeKt$SettingHome$1$2$1$2.1
                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0047  */
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                            Composer composer2 = (Composer) obj7;
                                            if ((((Number) obj8).intValue() & 17) == 16) {
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.SettingHome.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SettingHome.kt:141)");
                                                    }
                                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                    composerImpl3.startReplaceGroup(-216118850);
                                                    Function1 function13 = function12;
                                                    boolean zChanged = composerImpl3.changed(function13);
                                                    Object objRememberedValue = composerImpl3.rememberedValue();
                                                    if (!zChanged) {
                                                        Composer.Companion.getClass();
                                                        if (objRememberedValue == Composer.Companion.Empty) {
                                                            objRememberedValue = new SettingHomeKt$SettingHome$1$2$1$1$1$$ExternalSyntheticLambda0(function13, 2);
                                                            composerImpl3.updateRememberedValue(objRememberedValue);
                                                        }
                                                        composerImpl3.end(false);
                                                        ListsKt.SecListItem((Function0) objRememberedValue, "Labs", null, composerImpl3, 48, 4);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composer), composer, 6);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }));
                }
                ComposableSingletons$SettingHomeKt.INSTANCE.getClass();
                LazyListScope.item$default(lazyListScope, ComposableSingletons$SettingHomeKt.f79lambda1);
                break;
            default:
                Offset offset = (Offset) obj;
                Pair pair = (Pair) mutableState.getValue();
                PointerInputScope pointerInputScope = (PointerInputScope) obj2;
                Pair pair2 = null;
                if (Float.intBitsToFloat((int) (offset.packedValue & 4294967295L)) <= ((int) (4294967295L & pointerInputScope.mo51getSizeYbymL2g())) * 0.75d) {
                    pair = null;
                }
                if (pair != null) {
                    int iIntValue = ((Number) pair.component1()).intValue();
                    int iIntValue2 = ((Number) pair.component2()).intValue();
                    int i = (int) (offset.packedValue >> 32);
                    if (Float.intBitsToFloat(i) < ((int) (pointerInputScope.mo51getSizeYbymL2g() >> 32)) * 0.4d) {
                        pair2 = new Pair(Integer.valueOf(iIntValue + 1), Integer.valueOf(iIntValue2));
                    } else if (((int) (pointerInputScope.mo51getSizeYbymL2g() >> 32)) * 0.6d < Float.intBitsToFloat(i)) {
                        pair2 = new Pair(Integer.valueOf(iIntValue), Integer.valueOf(iIntValue2 + 1));
                    }
                    if (pair2 == null) {
                        pair2 = new Pair(0, 0);
                    }
                }
                mutableState.setValue(pair2);
                if (((Number) ((Pair) mutableState.getValue()).getFirst()).intValue() >= 10 && ((Number) ((Pair) mutableState.getValue()).getSecond()).intValue() >= 10) {
                    mutableState.setValue(new Pair(0, 0));
                    int i2 = LabsViewModel.$r8$clinit;
                    ((LabsViewModel) viewModel).setShowLabsMenu(true);
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ SettingHomeKt$SettingHome$1$$ExternalSyntheticLambda0(SettingViewModel settingViewModel, Function1 function1, MutableState mutableState) {
        this.f$0 = settingViewModel;
        this.f$1 = function1;
        this.f$2 = mutableState;
    }
}
