package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import android.view.WindowInsets;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemBarAttributesListener implements Dumpable {
    public final CentralSurfaces centralSurfaces;
    public LetterboxAppearance lastLetterboxAppearance;
    public SystemBarAttributesParams lastSystemBarAttributesParams;
    public final LetterboxAppearanceCalculator letterboxAppearanceCalculator;
    public final LightBarController lightBarController;
    public final SysuiStatusBarStateController statusBarStateController;
    public final SystemStatusAnimationScheduler systemStatusAnimationScheduler;

    public SystemBarAttributesListener(CentralSurfaces centralSurfaces, LetterboxAppearanceCalculator letterboxAppearanceCalculator, SysuiStatusBarStateController sysuiStatusBarStateController, LightBarController lightBarController, DumpManager dumpManager, SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.centralSurfaces = centralSurfaces;
        this.letterboxAppearanceCalculator = letterboxAppearanceCalculator;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.lightBarController = lightBarController;
        this.systemStatusAnimationScheduler = systemStatusAnimationScheduler;
        dumpManager.getClass();
        dumpManager.registerCriticalDumpable("SystemBarAttributesListener", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("lastSystemBarAttributesParams: " + this.lastSystemBarAttributesParams);
        printWriter.println("lastLetterboxAppearance: " + this.lastLetterboxAppearance);
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b0 A[LOOP:0: B:9:0x0042->B:30:0x00b0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ae A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        Pair pair;
        boolean z2;
        boolean z3;
        boolean z4;
        LetterboxAppearance letterboxAppearance;
        LetterboxDetails letterboxDetails;
        boolean z5;
        LetterboxDetails[] letterboxDetailsArr2 = letterboxDetailsArr;
        this.lastSystemBarAttributesParams = new SystemBarAttributesParams(i, i2, ArraysKt___ArraysKt.toList(appearanceRegionArr), z, i3, i4, str, ArraysKt___ArraysKt.toList(letterboxDetailsArr));
        boolean z6 = true;
        if (!(letterboxDetailsArr2.length == 0)) {
            Integer valueOf = Integer.valueOf(i2);
            LetterboxAppearanceCalculator letterboxAppearanceCalculator = this.letterboxAppearanceCalculator;
            letterboxAppearanceCalculator.lastAppearance = valueOf;
            letterboxAppearanceCalculator.lastAppearanceRegions = appearanceRegionArr;
            letterboxAppearanceCalculator.lastLetterboxes = letterboxDetailsArr2;
            LetterboxBackgroundProvider letterboxBackgroundProvider = letterboxAppearanceCalculator.letterboxBackgroundProvider;
            if (!letterboxBackgroundProvider.isLetterboxBackgroundMultiColored) {
                for (LetterboxDetails letterboxDetails2 : letterboxDetailsArr2) {
                    Rect letterboxInnerBounds = letterboxDetails2.getLetterboxInnerBounds();
                    StatusBarBoundsProvider statusBarBoundsProvider = letterboxAppearanceCalculator.statusBarBoundsProvider;
                    Rect boundsOnScreen = statusBarBoundsProvider != null ? ConvenienceExtensionsKt.getBoundsOnScreen(statusBarBoundsProvider.startSideContent) : new Rect();
                    if (!((letterboxInnerBounds.contains(boundsOnScreen) || boundsOnScreen.contains(letterboxInnerBounds)) ? false : letterboxInnerBounds.intersects(boundsOnScreen.left, boundsOnScreen.top, boundsOnScreen.right, boundsOnScreen.bottom))) {
                        Rect letterboxInnerBounds2 = letterboxDetails2.getLetterboxInnerBounds();
                        StatusBarBoundsProvider statusBarBoundsProvider2 = letterboxAppearanceCalculator.statusBarBoundsProvider;
                        Rect boundsOnScreen2 = statusBarBoundsProvider2 != null ? ConvenienceExtensionsKt.getBoundsOnScreen(statusBarBoundsProvider2.endSideContent) : new Rect();
                        if (!((letterboxInnerBounds2.contains(boundsOnScreen2) || boundsOnScreen2.contains(letterboxInnerBounds2)) ? false : letterboxInnerBounds2.intersects(boundsOnScreen2.left, boundsOnScreen2.top, boundsOnScreen2.right, boundsOnScreen2.bottom))) {
                            z5 = false;
                            if (z5) {
                            }
                        }
                    }
                    z5 = true;
                    if (z5) {
                    }
                }
                z4 = false;
                if (z4) {
                    int i5 = i2 & (-33);
                    ArrayList arrayList = new ArrayList(appearanceRegionArr.length);
                    for (AppearanceRegion appearanceRegion : appearanceRegionArr) {
                        int length = letterboxDetailsArr2.length;
                        int i6 = 0;
                        while (true) {
                            if (i6 >= length) {
                                letterboxDetails = null;
                                break;
                            }
                            letterboxDetails = letterboxDetailsArr2[i6];
                            if (Intrinsics.areEqual(letterboxDetails.getLetterboxFullBounds(), appearanceRegion.getBounds())) {
                                break;
                            } else {
                                i6++;
                            }
                        }
                        if (letterboxDetails != null) {
                            appearanceRegion = new AppearanceRegion(appearanceRegion.getAppearance(), letterboxDetails.getLetterboxInnerBounds());
                        }
                        arrayList.add(appearanceRegion);
                    }
                    ArrayList arrayList2 = new ArrayList(letterboxDetailsArr2.length);
                    int length2 = letterboxDetailsArr2.length;
                    int i7 = 0;
                    while (i7 < length2) {
                        LetterboxDetails letterboxDetails3 = letterboxDetailsArr2[i7];
                        int i8 = letterboxBackgroundProvider.letterboxBackgroundColor;
                        LightBarController lightBarController = letterboxAppearanceCalculator.lightBarController;
                        int i9 = ContrastColorUtil.calculateContrast(lightBarController.mDarkIconColor, i8) > ContrastColorUtil.calculateContrast(lightBarController.mLightIconColor, i8) ? 8 : 0;
                        Rect letterboxInnerBounds3 = letterboxDetails3.getLetterboxInnerBounds();
                        Rect letterboxFullBounds = letterboxDetails3.getLetterboxFullBounds();
                        LetterboxBackgroundProvider letterboxBackgroundProvider2 = letterboxBackgroundProvider;
                        int i10 = length2;
                        List listOf = CollectionsKt__CollectionsKt.listOf(new Rect(letterboxFullBounds.left, letterboxFullBounds.top, letterboxInnerBounds3.left, letterboxFullBounds.bottom), new Rect(letterboxFullBounds.left, letterboxFullBounds.top, letterboxFullBounds.right, letterboxInnerBounds3.top), new Rect(letterboxInnerBounds3.right, letterboxFullBounds.top, letterboxFullBounds.right, letterboxFullBounds.bottom), new Rect(letterboxFullBounds.left, letterboxInnerBounds3.bottom, letterboxFullBounds.right, letterboxFullBounds.bottom));
                        ArrayList arrayList3 = new ArrayList();
                        for (Object obj : listOf) {
                            if (!((Rect) obj).isEmpty()) {
                                arrayList3.add(obj);
                            }
                        }
                        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            arrayList4.add(new AppearanceRegion(i9, (Rect) it.next()));
                        }
                        arrayList2.add(arrayList4);
                        i7++;
                        length2 = i10;
                        letterboxDetailsArr2 = letterboxDetailsArr;
                        z6 = true;
                        letterboxBackgroundProvider = letterboxBackgroundProvider2;
                    }
                    letterboxAppearance = new LetterboxAppearance(i5, (AppearanceRegion[]) ((ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__IterablesKt.flatten(arrayList2), (Collection) arrayList)).toArray(new AppearanceRegion[0]));
                } else {
                    letterboxAppearance = new LetterboxAppearance(i2 | 32, appearanceRegionArr);
                }
                letterboxAppearanceCalculator.lastLetterboxAppearance = letterboxAppearance;
                this.lastLetterboxAppearance = letterboxAppearance;
                pair = new Pair(Integer.valueOf(letterboxAppearance.appearance), letterboxAppearance.appearanceRegions);
            }
            z4 = true;
            if (z4) {
            }
            letterboxAppearanceCalculator.lastLetterboxAppearance = letterboxAppearance;
            this.lastLetterboxAppearance = letterboxAppearance;
            pair = new Pair(Integer.valueOf(letterboxAppearance.appearance), letterboxAppearance.appearanceRegions);
        } else {
            this.lastLetterboxAppearance = null;
            pair = new Pair(Integer.valueOf(i2), appearanceRegionArr);
        }
        int intValue = ((Number) pair.component1()).intValue();
        AppearanceRegion[] appearanceRegionArr2 = (AppearanceRegion[]) pair.component2();
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.centralSurfaces;
        if (centralSurfacesImpl.mAppearance != intValue) {
            centralSurfacesImpl.mAppearance = intValue;
            int barMode = CentralSurfacesImpl.barMode(intValue, centralSurfacesImpl.mTransientShown);
            if (centralSurfacesImpl.mStatusBarMode != barMode) {
                centralSurfacesImpl.mStatusBarMode = barMode;
                centralSurfacesImpl.checkBarModes();
                centralSurfacesImpl.mAutoHideController.touchAutoHide();
                z3 = z6;
            } else {
                z3 = false;
            }
            int i11 = centralSurfacesImpl.mAppearance;
            int i12 = centralSurfacesImpl.mStatusBarMode;
            SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = centralSurfacesImpl.mSamsungStatusBarGrayIconHelper;
            boolean z7 = samsungStatusBarGrayIconHelper.isGrayAppearance;
            boolean z8 = (!((i12 == 0 || i12 == 6) ? z6 : false) || (i11 & 32768) == 0) ? false : z6;
            samsungStatusBarGrayIconHelper.isGrayAppearance = z8;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isGrayAppearance=", z8, "SamsungStatusBarGrayIconHelper");
            samsungStatusBarGrayIconHelper.grayAppearanceChanged = z7 != samsungStatusBarGrayIconHelper.isGrayAppearance ? z6 : false;
            z2 = z3;
        } else {
            z2 = false;
        }
        this.lightBarController.onStatusBarAppearanceChanged(appearanceRegionArr2, z2, ((CentralSurfacesImpl) this.centralSurfaces).mStatusBarMode, z, str);
        CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) this.centralSurfaces;
        centralSurfacesImpl2.getClass();
        centralSurfacesImpl2.mBubblesOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda13(centralSurfacesImpl2, 0));
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.statusBarStateController;
        statusBarStateControllerImpl.getClass();
        boolean z9 = ((WindowInsets.Type.statusBars() & i4) == 0 || (WindowInsets.Type.navigationBars() & i4) == 0) ? z6 : false;
        if (statusBarStateControllerImpl.mIsFullscreen != z9) {
            statusBarStateControllerImpl.mIsFullscreen = z9;
            synchronized (statusBarStateControllerImpl.mListeners) {
                Iterator it2 = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                while (it2.hasNext()) {
                    ((SysuiStatusBarStateController.RankedListener) it2.next()).mListener.onFullscreenStateChanged(z9);
                }
            }
        }
        if (StatusBarStateControllerImpl.DEBUG_IMMERSIVE_APPS) {
            boolean z10 = (intValue & 4) != 0 ? z6 : false;
            String flagsToString = ViewDebug.flagsToString(InsetsFlags.class, "behavior", i3);
            String type = WindowInsets.Type.toString(i4);
            if (type.isEmpty()) {
                type = "none";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" dim=");
            sb.append(z10);
            sb.append(" behavior=");
            sb.append(flagsToString);
            ExifInterface$$ExternalSyntheticOutline0.m36m(sb, " requested visible types: ", type, "SbStateController");
        }
        SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = (SystemStatusAnimationSchedulerImpl) this.systemStatusAnimationScheduler;
        systemStatusAnimationSchedulerImpl.getClass();
        if ((i4 & WindowInsets.Type.statusBars()) != 0) {
            if (systemStatusAnimationSchedulerImpl.statusBarWindowStateController.windowState == 0 ? z6 : false) {
                z6 = false;
            }
        }
        systemStatusAnimationSchedulerImpl.statusBarHidden = z6;
    }
}
