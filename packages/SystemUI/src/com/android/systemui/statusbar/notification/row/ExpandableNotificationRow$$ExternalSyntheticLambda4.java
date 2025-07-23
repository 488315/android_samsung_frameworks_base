package com.android.systemui.statusbar.notification.row;

import android.util.IndentingPrintWriter;
import android.view.View;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationUi;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ IndentingPrintWriter f$1;
    public final /* synthetic */ String[] f$2;

    public /* synthetic */ ExpandableNotificationRow$$ExternalSyntheticLambda4(Object obj, IndentingPrintWriter indentingPrintWriter, String[] strArr, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = indentingPrintWriter;
        this.f$2 = strArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.f$0;
                final IndentingPrintWriter indentingPrintWriter = this.f$1;
                final String[] strArr = this.f$2;
                SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
                expandableNotificationRow.getClass();
                indentingPrintWriter.println(expandableNotificationRow);
                indentingPrintWriter.print("visibility: " + expandableNotificationRow.getVisibility());
                indentingPrintWriter.print(", alpha: " + expandableNotificationRow.getAlpha());
                indentingPrintWriter.print(", translation: " + expandableNotificationRow.getTranslation());
                indentingPrintWriter.print(", entry dismissable: " + (((NotificationDismissibilityProviderImpl) expandableNotificationRow.mDismissibilityProvider).nonDismissableEntryKeys.contains(expandableNotificationRow.getKey()) ^ true));
                StringBuilder sb = new StringBuilder(", mOnUserInteractionCallback==null: ");
                sb.append(expandableNotificationRow.mOnUserInteractionCallback == null);
                indentingPrintWriter.print(sb.toString());
                indentingPrintWriter.print(", removed: false");
                indentingPrintWriter.print(", expandAnimationRunning: " + expandableNotificationRow.mExpandAnimationRunning);
                indentingPrintWriter.print(", mShowingPublic: " + expandableNotificationRow.mShowingPublic);
                indentingPrintWriter.print(", mShowingPublicInitialized: " + expandableNotificationRow.mShowingPublicInitialized);
                NotificationContentView showingLayout = expandableNotificationRow.getShowingLayout();
                StringBuilder sb2 = new StringBuilder(", privateShowing: ");
                sb2.append(showingLayout == expandableNotificationRow.mPrivateLayout);
                indentingPrintWriter.print(sb2.toString());
                StringBuilder sb3 = new StringBuilder(", childrenContainerShowing: ");
                sb3.append(!expandableNotificationRow.shouldShowPublic() && expandableNotificationRow.mIsSummaryWithChildren);
                indentingPrintWriter.print(sb3.toString());
                indentingPrintWriter.print(", mShowNoBackground: " + expandableNotificationRow.mShowNoBackground);
                indentingPrintWriter.print(", clipBounds: " + expandableNotificationRow.getClipBounds());
                int i2 = PromotedNotificationUi.$r8$clinit;
                indentingPrintWriter.print(", isPromotedOngoing: false");
                indentingPrintWriter.print(", isShowingExpanded: " + expandableNotificationRow.isShowingExpanded());
                StringBuilder sb4 = new StringBuilder(", isAccessibilityExpandable: ");
                sb4.append((expandableNotificationRow.mIsSnoozed || !expandableNotificationRow.isContentExpandable() || expandableNotificationRow.mEntry.isPromotedState()) ? false : true);
                indentingPrintWriter.print(sb4.toString());
                indentingPrintWriter.print(", isExpandable: " + expandableNotificationRow.isExpandable());
                indentingPrintWriter.print(", mExpandable: " + expandableNotificationRow.mExpandable);
                indentingPrintWriter.print(", isUserExpanded: " + expandableNotificationRow.mUserExpanded);
                indentingPrintWriter.print(", hasUserChangedExpansion: " + expandableNotificationRow.mHasUserChangedExpansion);
                indentingPrintWriter.print(", isOnKeyguard: " + expandableNotificationRow.mOnKeyguard);
                indentingPrintWriter.print(", isSummaryWithChildren: " + expandableNotificationRow.mIsSummaryWithChildren);
                indentingPrintWriter.print(", enableNonGroupedExpand: " + expandableNotificationRow.mEnableNonGroupedNotificationExpand);
                indentingPrintWriter.print(", isPinned: " + expandableNotificationRow.mPinnedStatus.isPinned());
                indentingPrintWriter.print(", expandedWhenPinned: " + expandableNotificationRow.mExpandedWhenPinned);
                indentingPrintWriter.print(", isMinimized: " + expandableNotificationRow.mIsMinimized);
                indentingPrintWriter.print(", isAboveShelf: " + expandableNotificationRow.isAboveShelf());
                indentingPrintWriter.print(", redactionType: " + expandableNotificationRow.mRedactionType);
                if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
                    indentingPrintWriter.print(", inflationWakelock: " + expandableNotificationRow.mEntry.mInflationWakeLock);
                }
                indentingPrintWriter.println();
                indentingPrintWriter.print("Heights: ");
                indentingPrintWriter.print("intrinsic", Integer.valueOf(expandableNotificationRow.getIntrinsicHeight()));
                indentingPrintWriter.print("actual", Integer.valueOf(expandableNotificationRow.mActualHeight));
                indentingPrintWriter.print("maxContent", Integer.valueOf(expandableNotificationRow.getMaxContentHeight()));
                indentingPrintWriter.print("maxExpanded", Integer.valueOf(expandableNotificationRow.getMaxExpandHeight()));
                indentingPrintWriter.print("collapsed", Integer.valueOf(expandableNotificationRow.getCollapsedHeight()));
                indentingPrintWriter.print("headsup", Integer.valueOf(expandableNotificationRow.getHeadsUpHeight()));
                indentingPrintWriter.print("headsup  without header", Integer.valueOf(expandableNotificationRow.getHeadsUpHeightWithoutHeader()));
                indentingPrintWriter.print("minHeight", Integer.valueOf(expandableNotificationRow.getMinHeight(false)));
                indentingPrintWriter.print("pinned headsup", Integer.valueOf(expandableNotificationRow.getPinnedHeadsUpHeight(true)));
                indentingPrintWriter.println();
                indentingPrintWriter.print("Intrinsic Height Factors: ");
                indentingPrintWriter.print("isUserLocked()", Boolean.valueOf(expandableNotificationRow.mUserLocked));
                indentingPrintWriter.print("isChildInGroup()", Boolean.valueOf(expandableNotificationRow.isChildInGroup()));
                indentingPrintWriter.print("isGroupExpanded()", Boolean.valueOf(expandableNotificationRow.isGroupExpanded$1()));
                indentingPrintWriter.print("sensitive", Boolean.valueOf(expandableNotificationRow.mSensitive));
                indentingPrintWriter.print("hideSensitiveForIntrinsicHeight", Boolean.valueOf(expandableNotificationRow.mHideSensitiveForIntrinsicHeight));
                indentingPrintWriter.print("isSummaryWithChildren", Boolean.valueOf(expandableNotificationRow.mIsSummaryWithChildren));
                indentingPrintWriter.print("canShowHeadsUp()", Boolean.valueOf(expandableNotificationRow.canShowHeadsUp$1()));
                indentingPrintWriter.print("isHeadsUpState()", Boolean.valueOf(expandableNotificationRow.isHeadsUpState()));
                indentingPrintWriter.print("isPinned()", Boolean.valueOf(expandableNotificationRow.mPinnedStatus.isPinned()));
                indentingPrintWriter.print("headsupDisappearRunning", Boolean.valueOf(expandableNotificationRow.mHeadsupDisappearRunning));
                indentingPrintWriter.print("isExpanded()", Boolean.valueOf(expandableNotificationRow.isExpanded(false)));
                indentingPrintWriter.println();
                indentingPrintWriter.print("contentView visibility: " + showingLayout.getVisibility());
                indentingPrintWriter.print(", alpha: " + showingLayout.getAlpha());
                indentingPrintWriter.print(", clipBounds: " + showingLayout.getClipBounds());
                indentingPrintWriter.print(", contentHeight: " + showingLayout.mContentHeight);
                indentingPrintWriter.print(", visibleType: " + showingLayout.mVisibleType);
                View viewForVisibleType = showingLayout.getViewForVisibleType(showingLayout.mVisibleType);
                indentingPrintWriter.print(", visibleView ");
                if (viewForVisibleType != null) {
                    indentingPrintWriter.print(" visibility: " + viewForVisibleType.getVisibility());
                    indentingPrintWriter.print(", alpha: " + viewForVisibleType.getAlpha());
                    indentingPrintWriter.print(", clipBounds: " + viewForVisibleType.getClipBounds());
                } else {
                    indentingPrintWriter.print("null");
                }
                indentingPrintWriter.println();
                IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(indentingPrintWriter);
                asIndenting.print("ContentDimensions: ");
                int i3 = showingLayout.mVisibleType;
                asIndenting.print("visibleType(String)", i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE : "SINGLELINE" : "HEADSUP" : "EXPANDED" : "CONTRACTED");
                asIndenting.print("measured width", Integer.valueOf(showingLayout.getMeasuredWidth()));
                asIndenting.print("measured height", Integer.valueOf(showingLayout.getMeasuredHeight()));
                asIndenting.print("maxHeight", Integer.valueOf(showingLayout.getMaxHeight()));
                asIndenting.print("minHeight", Integer.valueOf(showingLayout.getMinHeight(true)));
                asIndenting.println();
                asIndenting.println("ChildViews:");
                DumpUtilsKt.withIncreasedIndent(asIndenting, new NotificationContentView$$ExternalSyntheticLambda3(showingLayout, asIndenting));
                asIndenting.print("expandedRemoteInputHeight", Integer.valueOf(showingLayout.getExtraRemoteInputHeight(showingLayout.mExpandedRemoteInput)));
                asIndenting.print("headsUpRemoteInputHeight", 0);
                asIndenting.println();
                indentingPrintWriter.println("mBubblesEnabledForUser: " + showingLayout.mBubblesEnabledForUser);
                indentingPrintWriter.print("RemoteInputViews { ");
                indentingPrintWriter.print(" visibleType: " + showingLayout.mVisibleType);
                indentingPrintWriter.print(", headsUpRemoteInputController: null");
                if (showingLayout.mExpandedRemoteInputController != null) {
                    indentingPrintWriter.print(", expandedRemoteInputController.isActive: " + ((RemoteInputViewControllerImpl) showingLayout.mExpandedRemoteInputController).view.isActive());
                } else {
                    indentingPrintWriter.print(", expandedRemoteInputController: null");
                }
                indentingPrintWriter.println(" }");
                indentingPrintWriter.print("AppearAnimation: ");
                indentingPrintWriter.print("mDrawingAppearAnimation", Boolean.valueOf(expandableNotificationRow.mDrawingAppearAnimation));
                indentingPrintWriter.print("mAppearAnimationFraction", Float.valueOf(expandableNotificationRow.mAppearAnimationFraction));
                indentingPrintWriter.print("mIsHeadsUpAnimation", Boolean.valueOf(expandableNotificationRow.mIsHeadsUpAnimation));
                indentingPrintWriter.print("mIsHeadsUpCycling", Boolean.valueOf(expandableNotificationRow.mIsHeadsUpCycling));
                indentingPrintWriter.print("mTargetPoint", expandableNotificationRow.mTargetPoint);
                indentingPrintWriter.println();
                indentingPrintWriter.print("CustomOutline: ");
                indentingPrintWriter.print("mCustomOutline", Boolean.valueOf(expandableNotificationRow.mCustomOutline));
                indentingPrintWriter.print("mOutlineRect", expandableNotificationRow.mOutlineRect);
                indentingPrintWriter.print("mOutlineAlpha", Float.valueOf(expandableNotificationRow.mOutlineAlpha));
                indentingPrintWriter.print("mAlwaysRoundBothCorners", Boolean.valueOf(expandableNotificationRow.mAlwaysRoundBothCorners));
                indentingPrintWriter.println();
                indentingPrintWriter.print("Clipping: ");
                indentingPrintWriter.print("mInRemovalAnimation", Boolean.valueOf(expandableNotificationRow.mInRemovalAnimation));
                indentingPrintWriter.print("mClipTopAmount", Integer.valueOf(expandableNotificationRow.mClipTopAmount));
                indentingPrintWriter.print("mClipBottomAmount", Integer.valueOf(expandableNotificationRow.mClipBottomAmount));
                indentingPrintWriter.print("mClipToActualHeight", Boolean.valueOf(expandableNotificationRow.mClipToActualHeight));
                indentingPrintWriter.print("mExtraWidthForClipping", Float.valueOf(expandableNotificationRow.mExtraWidthForClipping));
                indentingPrintWriter.print("mMinimumHeightForClipping", Integer.valueOf(expandableNotificationRow.mMinimumHeightForClipping));
                indentingPrintWriter.print("getClipBounds()", expandableNotificationRow.getClipBounds());
                indentingPrintWriter.println();
                ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
                if (expandableViewState != null) {
                    expandableViewState.dump(indentingPrintWriter, strArr);
                    indentingPrintWriter.println();
                } else {
                    indentingPrintWriter.println("no viewState!!!");
                }
                indentingPrintWriter.println(((ExpandableOutlineView) expandableNotificationRow).mRoundableState.debugString());
                indentingPrintWriter.println("Background View: " + expandableNotificationRow.mBackgroundNormal);
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                final int transientViewCount = notificationChildrenContainer != null ? notificationChildrenContainer.getTransientViewCount() : 0;
                if (expandableNotificationRow.mIsSummaryWithChildren || transientViewCount > 0) {
                    NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
                    indentingPrintWriter.println("NotificationChildrenContainer { visibility: " + notificationChildrenContainer2.getVisibility() + ", alpha: " + notificationChildrenContainer2.getAlpha() + ", translationY: " + notificationChildrenContainer2.getTranslationY() + ", clipBounds: " + notificationChildrenContainer2.getClipBounds() + ", roundableState: " + notificationChildrenContainer2.mRoundableState.debugString() + "}");
                    StringBuilder sb5 = new StringBuilder("Children Container Intrinsic Height: ");
                    sb5.append(expandableNotificationRow.mChildrenContainer.getIntrinsicHeight());
                    indentingPrintWriter.println(sb5.toString());
                    indentingPrintWriter.println();
                    List attachedChildren = expandableNotificationRow.getAttachedChildren();
                    StringBuilder sb6 = new StringBuilder("Children: ");
                    ArrayList arrayList = (ArrayList) attachedChildren;
                    sb6.append(arrayList.size());
                    sb6.append(" {");
                    indentingPrintWriter.print(sb6.toString());
                    DumpUtilsKt.withIncreasedIndent(indentingPrintWriter, new ExpandableNotificationRow$$ExternalSyntheticLambda4(arrayList, indentingPrintWriter, strArr, i));
                    indentingPrintWriter.println("}");
                    indentingPrintWriter.print("Transient Views: " + transientViewCount + " {");
                    DumpUtilsKt.withIncreasedIndent(indentingPrintWriter, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExpandableNotificationRow expandableNotificationRow2 = ExpandableNotificationRow.this;
                            int i4 = transientViewCount;
                            PrintWriter printWriter = indentingPrintWriter;
                            String[] strArr2 = strArr;
                            SourceType$Companion$from$1 sourceType$Companion$from$12 = ExpandableNotificationRow.BASE_VALUE;
                            expandableNotificationRow2.getClass();
                            for (int i5 = 0; i5 < i4; i5++) {
                                printWriter.println();
                                ((ExpandableView) expandableNotificationRow2.mChildrenContainer.getTransientView(i5)).dump(printWriter, strArr2);
                            }
                        }
                    });
                    indentingPrintWriter.println("}");
                    break;
                } else {
                    NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                    if (notificationContentView != null) {
                        if (notificationContentView.mHeadsUpSmartReplyView != null) {
                            indentingPrintWriter.println("HeadsUp SmartReplyView:");
                            indentingPrintWriter.increaseIndent();
                            notificationContentView.mHeadsUpSmartReplyView.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                        }
                        if (notificationContentView.mExpandedSmartReplyView != null) {
                            indentingPrintWriter.println("Expanded SmartReplyView:");
                            indentingPrintWriter.increaseIndent();
                            notificationContentView.mExpandedSmartReplyView.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                            break;
                        }
                    }
                }
                break;
            default:
                List<ExpandableNotificationRow> list = (List) this.f$0;
                PrintWriter printWriter = this.f$1;
                String[] strArr2 = this.f$2;
                SourceType$Companion$from$1 sourceType$Companion$from$12 = ExpandableNotificationRow.BASE_VALUE;
                for (ExpandableNotificationRow expandableNotificationRow2 : list) {
                    printWriter.println();
                    expandableNotificationRow2.dump(printWriter, strArr2);
                }
                break;
        }
    }
}
