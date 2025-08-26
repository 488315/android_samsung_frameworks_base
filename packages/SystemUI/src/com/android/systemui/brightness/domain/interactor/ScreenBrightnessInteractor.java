package com.android.systemui.brightness.domain.interactor;

import com.android.settingslib.display.BrightnessUtils;
import com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository;
import com.android.systemui.brightness.data.repository.ScreenBrightnessRepository;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.shared.model.GammaBrightnessKt;
import com.android.systemui.brightness.shared.model.LinearBrightness;
import com.android.systemui.log.table.TableLogBuffer;
import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class ScreenBrightnessInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow brightnessOverriddenByWindow;
    public final ReadonlyStateFlow gammaBrightness;
    public final ScreenBrightnessRepository screenBrightnessRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ScreenBrightnessInteractor(ScreenBrightnessRepository screenBrightnessRepository, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        this.screenBrightnessRepository = screenBrightnessRepository;
        ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository = (ScreenBrightnessDisplayManagerRepository) screenBrightnessRepository;
        this.gammaBrightness = FlowKt.stateIn(GammaBrightnessKt.m1068logDiffForTableGAU2kQA(FlowKt.combine(screenBrightnessDisplayManagerRepository.linearBrightness, screenBrightnessDisplayManagerRepository.minLinearBrightness, screenBrightnessDisplayManagerRepository.maxLinearBrightness, new ScreenBrightnessInteractor$gammaBrightness$1$1(this, null)), tableLogBuffer), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), GammaBrightness.m1066boximpl(0));
        this.brightnessOverriddenByWindow = screenBrightnessDisplayManagerRepository.isBrightnessOverriddenByWindow;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: setBrightness-saDbZGg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m1063setBrightnesssaDbZGg(int i, ContinuationImpl continuationImpl) throws Throwable {
        ScreenBrightnessInteractor$setBrightness$1 screenBrightnessInteractor$setBrightness$1;
        ScreenBrightnessRepository screenBrightnessRepository;
        if (continuationImpl instanceof ScreenBrightnessInteractor$setBrightness$1) {
            screenBrightnessInteractor$setBrightness$1 = (ScreenBrightnessInteractor$setBrightness$1) continuationImpl;
            int i2 = screenBrightnessInteractor$setBrightness$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                screenBrightnessInteractor$setBrightness$1.label = i2 - Integer.MIN_VALUE;
            } else {
                screenBrightnessInteractor$setBrightness$1 = new ScreenBrightnessInteractor$setBrightness$1(this, continuationImpl);
            }
        }
        Object obj = screenBrightnessInteractor$setBrightness$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = screenBrightnessInteractor$setBrightness$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            int iCoerceIn = RangesKt___RangesKt.coerceIn(i, 0, CustomDeviceManager.QUICK_PANEL_ALL);
            ScreenBrightnessRepository screenBrightnessRepository2 = this.screenBrightnessRepository;
            screenBrightnessInteractor$setBrightness$1.L$0 = screenBrightnessRepository2;
            screenBrightnessInteractor$setBrightness$1.label = 1;
            Object objM1065toLinearBrightnesskRMD4pI = m1065toLinearBrightnesskRMD4pI(iCoerceIn, screenBrightnessInteractor$setBrightness$1);
            if (objM1065toLinearBrightnesskRMD4pI == obj2) {
                return obj2;
            }
            obj = objM1065toLinearBrightnesskRMD4pI;
            screenBrightnessRepository = screenBrightnessRepository2;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            screenBrightnessRepository = (ScreenBrightnessRepository) screenBrightnessInteractor$setBrightness$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ((ScreenBrightnessDisplayManagerRepository) screenBrightnessRepository).apiQueue.mo3476trySendJP2dKIU(ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod.Permanent.m1061boximpl(((LinearBrightness) obj).floatValue));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: setTemporaryBrightness-saDbZGg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m1064setTemporaryBrightnesssaDbZGg(int i, ContinuationImpl continuationImpl) throws Throwable {
        ScreenBrightnessInteractor$setTemporaryBrightness$1 screenBrightnessInteractor$setTemporaryBrightness$1;
        ScreenBrightnessRepository screenBrightnessRepository;
        if (continuationImpl instanceof ScreenBrightnessInteractor$setTemporaryBrightness$1) {
            screenBrightnessInteractor$setTemporaryBrightness$1 = (ScreenBrightnessInteractor$setTemporaryBrightness$1) continuationImpl;
            int i2 = screenBrightnessInteractor$setTemporaryBrightness$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                screenBrightnessInteractor$setTemporaryBrightness$1.label = i2 - Integer.MIN_VALUE;
            } else {
                screenBrightnessInteractor$setTemporaryBrightness$1 = new ScreenBrightnessInteractor$setTemporaryBrightness$1(this, continuationImpl);
            }
        }
        Object obj = screenBrightnessInteractor$setTemporaryBrightness$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = screenBrightnessInteractor$setTemporaryBrightness$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            int iCoerceIn = RangesKt___RangesKt.coerceIn(i, 0, CustomDeviceManager.QUICK_PANEL_ALL);
            ScreenBrightnessRepository screenBrightnessRepository2 = this.screenBrightnessRepository;
            screenBrightnessInteractor$setTemporaryBrightness$1.L$0 = screenBrightnessRepository2;
            screenBrightnessInteractor$setTemporaryBrightness$1.label = 1;
            Object objM1065toLinearBrightnesskRMD4pI = m1065toLinearBrightnesskRMD4pI(iCoerceIn, screenBrightnessInteractor$setTemporaryBrightness$1);
            if (objM1065toLinearBrightnesskRMD4pI == obj2) {
                return obj2;
            }
            obj = objM1065toLinearBrightnesskRMD4pI;
            screenBrightnessRepository = screenBrightnessRepository2;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            screenBrightnessRepository = (ScreenBrightnessRepository) screenBrightnessInteractor$setTemporaryBrightness$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ((ScreenBrightnessDisplayManagerRepository) screenBrightnessRepository).apiQueue.mo3476trySendJP2dKIU(ScreenBrightnessDisplayManagerRepository.SetBrightnessMethod.Temporary.m1062boximpl(((LinearBrightness) obj).floatValue));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: toLinearBrightness-kRMD4pI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m1065toLinearBrightnesskRMD4pI(int i, ContinuationImpl continuationImpl) throws Throwable {
        ScreenBrightnessInteractor$toLinearBrightness$1 screenBrightnessInteractor$toLinearBrightness$1;
        if (continuationImpl instanceof ScreenBrightnessInteractor$toLinearBrightness$1) {
            screenBrightnessInteractor$toLinearBrightness$1 = (ScreenBrightnessInteractor$toLinearBrightness$1) continuationImpl;
            int i2 = screenBrightnessInteractor$toLinearBrightness$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                screenBrightnessInteractor$toLinearBrightness$1.label = i2 - Integer.MIN_VALUE;
            } else {
                screenBrightnessInteractor$toLinearBrightness$1 = new ScreenBrightnessInteractor$toLinearBrightness$1(this, continuationImpl);
            }
        }
        Object minMaxLinearBrightness = screenBrightnessInteractor$toLinearBrightness$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = screenBrightnessInteractor$toLinearBrightness$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(minMaxLinearBrightness);
            screenBrightnessInteractor$toLinearBrightness$1.I$0 = i;
            screenBrightnessInteractor$toLinearBrightness$1.label = 1;
            minMaxLinearBrightness = ((ScreenBrightnessDisplayManagerRepository) this.screenBrightnessRepository).getMinMaxLinearBrightness(screenBrightnessInteractor$toLinearBrightness$1);
            if (minMaxLinearBrightness == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = screenBrightnessInteractor$toLinearBrightness$1.I$0;
            ResultKt.throwOnFailure(minMaxLinearBrightness);
        }
        Pair pair = (Pair) minMaxLinearBrightness;
        return LinearBrightness.m1069boximpl(BrightnessUtils.convertGammaToLinearFloat(((LinearBrightness) pair.getFirst()).floatValue, ((LinearBrightness) pair.getSecond()).floatValue, i));
    }
}
