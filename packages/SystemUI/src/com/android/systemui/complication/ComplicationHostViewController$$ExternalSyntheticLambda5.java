package com.android.systemui.complication;

import android.graphics.Rect;
import android.util.Log;
import android.widget.TextClock;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.complication.DreamClockTimeComplication;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class ComplicationHostViewController$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComplicationHostViewController$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ComplicationHostViewController complicationHostViewController = (ComplicationHostViewController) obj2;
                ComplicationViewModel complicationViewModel = (ComplicationViewModel) obj;
                boolean z = ComplicationHostViewController.DEBUG;
                complicationHostViewController.getClass();
                ComplicationId complicationId = complicationViewModel.mId;
                Complication complication = complicationViewModel.mComplication;
                DreamClockTimeComplication.DreamClockTimeViewHolder dreamClockTimeViewHolderCreateView = complication.createView();
                TextClock textClock = dreamClockTimeViewHolderCreateView.mView;
                if (textClock == null) {
                    Log.e("ComplicationHostVwCtrl", "invalid complication view. null view supplied by ViewHolder");
                    break;
                } else {
                    if (!complicationHostViewController.mDreamOverlayStateController.containsState(4) && complicationHostViewController.mIsAnimationEnabled) {
                        textClock.setVisibility(4);
                    }
                    complicationHostViewController.mComplications.put(complicationId, dreamClockTimeViewHolderCreateView);
                    if (textClock.getParent() != null) {
                        Log.e("ComplicationHostVwCtrl", "View for complication " + complication.getClass() + " already has a parent. Make sure not to reuse complication views!");
                    }
                    ComplicationLayoutParams complicationLayoutParams = dreamClockTimeViewHolderCreateView.mLayoutParams;
                    StringBuilder sb = new StringBuilder("@");
                    ComplicationLayoutEngine complicationLayoutEngine = complicationHostViewController.mLayoutEngine;
                    sb.append(Integer.toHexString(complicationLayoutEngine.hashCode()));
                    sb.append(" addComplication: ");
                    sb.append(complicationId);
                    Log.d("ComplicationLayoutEng", sb.toString());
                    if (complicationLayoutEngine.mEntries.containsKey(complicationId)) {
                        complicationLayoutEngine.removeComplication(complicationId);
                    }
                    ComplicationLayoutEngine.ViewEntry.Builder builder = new ComplicationLayoutEngine.ViewEntry.Builder(textClock, complicationLayoutEngine.mSession, complicationLayoutParams, 1);
                    int i2 = complicationLayoutParams.mPosition;
                    if (!complicationLayoutEngine.mPositions.containsKey(Integer.valueOf(i2))) {
                        complicationLayoutEngine.mPositions.put(Integer.valueOf(i2), new ComplicationLayoutEngine.PositionGroup(complicationLayoutEngine.mDefaultDirectionalSpacing, (HashMap) complicationLayoutEngine.mPositionDirectionMarginMapping.get(Integer.valueOf(complicationLayoutParams.mPosition))));
                    }
                    ComplicationLayoutEngine.PositionGroup positionGroup = (ComplicationLayoutEngine.PositionGroup) complicationLayoutEngine.mPositions.get(Integer.valueOf(i2));
                    positionGroup.getClass();
                    int i3 = builder.mLayoutParams.mDirection;
                    if (!positionGroup.mDirectionGroups.containsKey(Integer.valueOf(i3))) {
                        positionGroup.mDirectionGroups.put(Integer.valueOf(i3), new ComplicationLayoutEngine.DirectionGroup(positionGroup));
                    }
                    ComplicationLayoutEngine.DirectionGroup directionGroup = (ComplicationLayoutEngine.DirectionGroup) positionGroup.mDirectionGroups.get(Integer.valueOf(i3));
                    directionGroup.getClass();
                    ComplicationLayoutEngine.ViewEntry viewEntry = new ComplicationLayoutEngine.ViewEntry(builder.mView, builder.mLayoutParams, builder.mTouchSession, builder.mCategory, directionGroup);
                    directionGroup.mViews.add(viewEntry);
                    Collections.sort(directionGroup.mViews);
                    Collections.reverse(directionGroup.mViews);
                    ((ComplicationLayoutEngine.PositionGroup) directionGroup.mParent).onEntriesChanged();
                    complicationLayoutEngine.mEntries.put(complicationId, viewEntry);
                    complicationLayoutEngine.mLayout.addView(viewEntry.mView);
                    break;
                }
            case 1:
                ComplicationHostViewController complicationHostViewController2 = (ComplicationHostViewController) obj2;
                ComplicationId complicationId2 = (ComplicationId) obj;
                complicationHostViewController2.mLayoutEngine.removeComplication(complicationId2);
                complicationHostViewController2.mComplications.remove(complicationId2);
                break;
            default:
                ComplicationLayoutEngine complicationLayoutEngine2 = (ComplicationLayoutEngine) obj2;
                Rect rect = (Rect) obj;
                if (rect.width() != complicationLayoutEngine2.mScreenBounds.width() || rect.height() != complicationLayoutEngine2.mScreenBounds.height()) {
                    complicationLayoutEngine2.mScreenBounds = rect;
                    ComplicationLayoutEngine.updatePositionDirectionalMarginsMapping(complicationLayoutEngine2.mPositionDirectionMarginMapping, (ComplicationLayoutEngine.Margins) complicationLayoutEngine2.mComplicationMarginsProvider.get());
                    for (Integer num : complicationLayoutEngine2.mPositions.keySet()) {
                        ComplicationLayoutEngine.PositionGroup positionGroup2 = (ComplicationLayoutEngine.PositionGroup) complicationLayoutEngine2.mPositions.get(num);
                        final Map map = (Map) complicationLayoutEngine2.mPositionDirectionMarginMapping.get(num);
                        positionGroup2.getClass();
                        if (map.keySet().containsAll(positionGroup2.mDirectionalMargins.keySet())) {
                            positionGroup2.mDirectionalMargins.replaceAll(new BiFunction() { // from class: com.android.systemui.complication.ComplicationLayoutEngine$PositionGroup$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiFunction
                                public final Object apply(Object obj3, Object obj4) {
                                    return (ComplicationLayoutEngine.Margins) map.get((Integer) obj3);
                                }
                            });
                        } else {
                            Log.e("ComplicationLayoutEng", "Directional margins map does not have the same keys");
                        }
                        positionGroup2.onEntriesChanged();
                    }
                    Log.d("ComplicationLayoutEng", "Updated margins for complications as screen size changed to width = " + rect.width() + "px, height = " + rect.height() + "px.");
                    break;
                }
                break;
        }
    }
}
