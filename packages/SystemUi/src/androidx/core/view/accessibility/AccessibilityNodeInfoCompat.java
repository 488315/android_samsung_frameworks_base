package androidx.core.view.accessibility;

import android.R;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityNodeInfoCompat {
    public final AccessibilityNodeInfo mInfo;
    public int mParentVirtualDescendantId = -1;
    public int mVirtualDescendantId = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_CLICK;
        public static final AccessibilityActionCompat ACTION_COLLAPSE;
        public static final AccessibilityActionCompat ACTION_DISMISS;
        public static final AccessibilityActionCompat ACTION_EXPAND;
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN;
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD;
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT;
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT;
        public static final AccessibilityActionCompat ACTION_SCROLL_UP;
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
        public final Object mAction;
        public final AccessibilityViewCommand mCommand;
        public final int mId;
        public final Class mViewCommandArgumentClass;
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);

        static {
            new AccessibilityActionCompat(4, null);
            new AccessibilityActionCompat(8, null);
            ACTION_CLICK = new AccessibilityActionCompat(16, null);
            new AccessibilityActionCompat(32, null);
            new AccessibilityActionCompat(64, null);
            new AccessibilityActionCompat(128, null);
            new AccessibilityActionCompat(256, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveAtGranularityArguments.class);
            new AccessibilityActionCompat(512, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveAtGranularityArguments.class);
            new AccessibilityActionCompat(1024, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveHtmlArguments.class);
            new AccessibilityActionCompat(2048, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.MoveHtmlArguments.class);
            ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
            ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
            new AccessibilityActionCompat(16384, null);
            new AccessibilityActionCompat(32768, null);
            new AccessibilityActionCompat(65536, null);
            new AccessibilityActionCompat(131072, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.SetSelectionArguments.class);
            ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
            ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
            ACTION_DISMISS = new AccessibilityActionCompat(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, null);
            new AccessibilityActionCompat(QuickStepContract.SYSUI_STATE_DEVICE_DOZING, (CharSequence) null, (Class<? extends AccessibilityViewCommand.CommandArguments>) AccessibilityViewCommand.SetTextArguments.class);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN, R.id.accessibilityActionShowOnScreen, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION, R.id.accessibilityActionScrollToPosition, null, null, AccessibilityViewCommand.ScrollToPositionArguments.class);
            ACTION_SCROLL_UP = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP, R.id.accessibilityActionScrollUp, null, null, null);
            ACTION_SCROLL_LEFT = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT, R.id.accessibilityActionScrollLeft, null, null, null);
            ACTION_SCROLL_DOWN = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN, R.id.accessibilityActionScrollDown, null, null, null);
            ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT, R.id.accessibilityActionScrollRight, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP, R.id.accessibilityActionPageUp, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN, R.id.accessibilityActionPageDown, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT, R.id.accessibilityActionPageLeft, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT, R.id.accessibilityActionPageRight, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK, R.id.accessibilityActionContextClick, null, null, null);
            ACTION_SET_PROGRESS = new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS, R.id.accessibilityActionSetProgress, null, null, AccessibilityViewCommand.SetProgressArguments.class);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW, R.id.accessibilityActionMoveWindow, null, null, AccessibilityViewCommand.MoveWindowArguments.class);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP, R.id.accessibilityActionShowTooltip, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP, R.id.accessibilityActionHideTooltip, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_PRESS_AND_HOLD, R.id.accessibilityActionPressAndHold, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_IME_ENTER, R.id.accessibilityActionImeEnter, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_START, R.id.accessibilityActionDragStart, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_DROP, R.id.accessibilityActionDragDrop, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_CANCEL, R.id.accessibilityActionDragCancel, null, null, null);
            new AccessibilityActionCompat(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TEXT_SUGGESTIONS, R.id.accessibilityActionShowTextSuggestions, null, null, null);
        }

        public AccessibilityActionCompat(int i, CharSequence charSequence) {
            this(null, i, charSequence, null, null);
        }

        public final boolean equals(Object obj) {
            if (obj == null || !(obj instanceof AccessibilityActionCompat)) {
                return false;
            }
            Object obj2 = ((AccessibilityActionCompat) obj).mAction;
            Object obj3 = this.mAction;
            return obj3 == null ? obj2 == null : obj3.equals(obj2);
        }

        public final int getId() {
            return ((AccessibilityNodeInfo.AccessibilityAction) this.mAction).getId();
        }

        public final int hashCode() {
            Object obj = this.mAction;
            if (obj != null) {
                return obj.hashCode();
            }
            return 0;
        }

        public AccessibilityActionCompat(int i, CharSequence charSequence, AccessibilityViewCommand accessibilityViewCommand) {
            this(null, i, charSequence, accessibilityViewCommand, null);
        }

        public AccessibilityActionCompat(Object obj) {
            this(obj, 0, null, null, null);
        }

        private AccessibilityActionCompat(int i, CharSequence charSequence, Class<? extends AccessibilityViewCommand.CommandArguments> cls) {
            this(null, i, charSequence, null, cls);
        }

        public AccessibilityActionCompat(Object obj, int i, CharSequence charSequence, AccessibilityViewCommand accessibilityViewCommand, Class<? extends AccessibilityViewCommand.CommandArguments> cls) {
            this.mId = i;
            this.mCommand = accessibilityViewCommand;
            if (obj == null) {
                this.mAction = new AccessibilityNodeInfo.AccessibilityAction(i, charSequence);
            } else {
                this.mAction = obj;
            }
            this.mViewCommandArgumentClass = cls;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CollectionInfoCompat {
        public final Object mInfo;

        public CollectionInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public static CollectionInfoCompat obtain(int i, int i2, int i3) {
            return new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, false, i3));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CollectionItemInfoCompat {
        public final Object mInfo;

        public CollectionItemInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public static CollectionItemInfoCompat obtain(boolean z, int i, int i2, int i3, int i4, boolean z2) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RangeInfoCompat {
        public final Object mInfo;

        public RangeInfoCompat(Object obj) {
            this.mInfo = obj;
        }
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object obj) {
        this.mInfo = (AccessibilityNodeInfo) obj;
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        return new AccessibilityNodeInfoCompat(AccessibilityNodeInfo.obtain(view));
    }

    public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new AccessibilityNodeInfoCompat(accessibilityNodeInfo);
    }

    public final void addAction(int i) {
        this.mInfo.addAction(i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AccessibilityNodeInfoCompat)) {
            return false;
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj;
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
        AccessibilityNodeInfo accessibilityNodeInfo2 = this.mInfo;
        if (accessibilityNodeInfo2 == null) {
            if (accessibilityNodeInfo != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo2.equals(accessibilityNodeInfo)) {
            return false;
        }
        return this.mVirtualDescendantId == accessibilityNodeInfoCompat.mVirtualDescendantId && this.mParentVirtualDescendantId == accessibilityNodeInfoCompat.mParentVirtualDescendantId;
    }

    public final List extrasIntList(String str) {
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        ArrayList<Integer> integerArrayList = accessibilityNodeInfo.getExtras().getIntegerArrayList(str);
        if (integerArrayList != null) {
            return integerArrayList;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        accessibilityNodeInfo.getExtras().putIntegerArrayList(str, arrayList);
        return arrayList;
    }

    public final List getActionList() {
        List<AccessibilityNodeInfo.AccessibilityAction> actionList = this.mInfo.getActionList();
        if (actionList == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = actionList.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new AccessibilityActionCompat(actionList.get(i)));
        }
        return arrayList;
    }

    public final void getBoundsInParent(Rect rect) {
        this.mInfo.getBoundsInParent(rect);
    }

    public final CharSequence getText() {
        boolean z = !extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").isEmpty();
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (!z) {
            return accessibilityNodeInfo.getText();
        }
        List extrasIntList = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
        List extrasIntList2 = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
        List extrasIntList3 = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
        List extrasIntList4 = extrasIntList("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
        SpannableString spannableString = new SpannableString(TextUtils.substring(accessibilityNodeInfo.getText(), 0, accessibilityNodeInfo.getText().length()));
        for (int i = 0; i < extrasIntList.size(); i++) {
            spannableString.setSpan(new AccessibilityClickableSpanCompat(((Integer) extrasIntList4.get(i)).intValue(), this, accessibilityNodeInfo.getExtras().getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY")), ((Integer) extrasIntList.get(i)).intValue(), ((Integer) extrasIntList2.get(i)).intValue(), ((Integer) extrasIntList3.get(i)).intValue());
        }
        return spannableString;
    }

    public final int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public final void removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        this.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) accessibilityActionCompat.mAction);
    }

    public final void setBoundsInParent(Rect rect) {
        this.mInfo.setBoundsInParent(rect);
    }

    public final void setCheckable(boolean z) {
        this.mInfo.setCheckable(z);
    }

    public final void setClassName(CharSequence charSequence) {
        this.mInfo.setClassName(charSequence);
    }

    public final void setClickable(boolean z) {
        this.mInfo.setClickable(z);
    }

    public final void setCollectionInfo(CollectionInfoCompat collectionInfoCompat) {
        this.mInfo.setCollectionInfo(collectionInfoCompat == null ? null : (AccessibilityNodeInfo.CollectionInfo) collectionInfoCompat.mInfo);
    }

    public final void setCollectionItemInfo(CollectionItemInfoCompat collectionItemInfoCompat) {
        this.mInfo.setCollectionItemInfo(collectionItemInfoCompat == null ? null : (AccessibilityNodeInfo.CollectionItemInfo) collectionItemInfoCompat.mInfo);
    }

    public final void setContentDescription(CharSequence charSequence) {
        this.mInfo.setContentDescription(charSequence);
    }

    public final void setScrollable(boolean z) {
        this.mInfo.setScrollable(z);
    }

    public final void setSelected(boolean z) {
        this.mInfo.setSelected(z);
    }

    public final void setText(CharSequence charSequence) {
        this.mInfo.setText(charSequence);
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        getBoundsInParent(rect);
        sb.append("; boundsInParent: " + rect);
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        accessibilityNodeInfo.getBoundsInScreen(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ");
        sb.append(accessibilityNodeInfo.getPackageName());
        sb.append("; className: ");
        sb.append(accessibilityNodeInfo.getClassName());
        sb.append("; text: ");
        sb.append(getText());
        sb.append("; contentDescription: ");
        sb.append(accessibilityNodeInfo.getContentDescription());
        sb.append("; viewId: ");
        sb.append(accessibilityNodeInfo.getViewIdResourceName());
        sb.append("; uniqueId: ");
        sb.append(accessibilityNodeInfo.getUniqueId());
        sb.append("; checkable: ");
        sb.append(accessibilityNodeInfo.isCheckable());
        sb.append("; checked: ");
        sb.append(accessibilityNodeInfo.isChecked());
        sb.append("; focusable: ");
        sb.append(accessibilityNodeInfo.isFocusable());
        sb.append("; focused: ");
        sb.append(accessibilityNodeInfo.isFocused());
        sb.append("; selected: ");
        sb.append(accessibilityNodeInfo.isSelected());
        sb.append("; clickable: ");
        sb.append(accessibilityNodeInfo.isClickable());
        sb.append("; longClickable: ");
        sb.append(accessibilityNodeInfo.isLongClickable());
        sb.append("; enabled: ");
        sb.append(accessibilityNodeInfo.isEnabled());
        sb.append("; password: ");
        sb.append(accessibilityNodeInfo.isPassword());
        sb.append("; scrollable: " + accessibilityNodeInfo.isScrollable());
        sb.append("; [");
        List actionList = getActionList();
        for (int i = 0; i < actionList.size(); i++) {
            AccessibilityActionCompat accessibilityActionCompat = (AccessibilityActionCompat) actionList.get(i);
            int id = accessibilityActionCompat.getId();
            if (id == 1) {
                str = "ACTION_FOCUS";
            } else if (id != 2) {
                switch (id) {
                    case 4:
                        str = "ACTION_SELECT";
                        break;
                    case 8:
                        str = "ACTION_CLEAR_SELECTION";
                        break;
                    case 16:
                        str = "ACTION_CLICK";
                        break;
                    case 32:
                        str = "ACTION_LONG_CLICK";
                        break;
                    case 64:
                        str = "ACTION_ACCESSIBILITY_FOCUS";
                        break;
                    case 128:
                        str = "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
                        break;
                    case 256:
                        str = "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
                        break;
                    case 512:
                        str = "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
                        break;
                    case 1024:
                        str = "ACTION_NEXT_HTML_ELEMENT";
                        break;
                    case 2048:
                        str = "ACTION_PREVIOUS_HTML_ELEMENT";
                        break;
                    case 4096:
                        str = "ACTION_SCROLL_FORWARD";
                        break;
                    case 8192:
                        str = "ACTION_SCROLL_BACKWARD";
                        break;
                    case 16384:
                        str = "ACTION_COPY";
                        break;
                    case 32768:
                        str = "ACTION_PASTE";
                        break;
                    case 65536:
                        str = "ACTION_CUT";
                        break;
                    case 131072:
                        str = "ACTION_SET_SELECTION";
                        break;
                    case 262144:
                        str = "ACTION_EXPAND";
                        break;
                    case 524288:
                        str = "ACTION_COLLAPSE";
                        break;
                    case QuickStepContract.SYSUI_STATE_DEVICE_DOZING /* 2097152 */:
                        str = "ACTION_SET_TEXT";
                        break;
                    case R.id.accessibilityActionMoveWindow:
                        str = "ACTION_MOVE_WINDOW";
                        break;
                    default:
                        switch (id) {
                            case R.id.accessibilityActionShowOnScreen:
                                str = "ACTION_SHOW_ON_SCREEN";
                                break;
                            case R.id.accessibilityActionScrollToPosition:
                                str = "ACTION_SCROLL_TO_POSITION";
                                break;
                            case R.id.accessibilityActionScrollUp:
                                str = "ACTION_SCROLL_UP";
                                break;
                            case R.id.accessibilityActionScrollLeft:
                                str = "ACTION_SCROLL_LEFT";
                                break;
                            case R.id.accessibilityActionScrollDown:
                                str = "ACTION_SCROLL_DOWN";
                                break;
                            case R.id.accessibilityActionScrollRight:
                                str = "ACTION_SCROLL_RIGHT";
                                break;
                            case R.id.accessibilityActionContextClick:
                                str = "ACTION_CONTEXT_CLICK";
                                break;
                            case R.id.accessibilityActionSetProgress:
                                str = "ACTION_SET_PROGRESS";
                                break;
                            default:
                                switch (id) {
                                    case R.id.accessibilityActionShowTooltip:
                                        str = "ACTION_SHOW_TOOLTIP";
                                        break;
                                    case R.id.accessibilityActionHideTooltip:
                                        str = "ACTION_HIDE_TOOLTIP";
                                        break;
                                    case R.id.accessibilityActionPageUp:
                                        str = "ACTION_PAGE_UP";
                                        break;
                                    case R.id.accessibilityActionPageDown:
                                        str = "ACTION_PAGE_DOWN";
                                        break;
                                    case R.id.accessibilityActionPageLeft:
                                        str = "ACTION_PAGE_LEFT";
                                        break;
                                    case R.id.accessibilityActionPageRight:
                                        str = "ACTION_PAGE_RIGHT";
                                        break;
                                    case R.id.accessibilityActionPressAndHold:
                                        str = "ACTION_PRESS_AND_HOLD";
                                        break;
                                    default:
                                        switch (id) {
                                            case R.id.accessibilityActionImeEnter:
                                                str = "ACTION_IME_ENTER";
                                                break;
                                            case R.id.accessibilityActionDragStart:
                                                str = "ACTION_DRAG_START";
                                                break;
                                            case R.id.accessibilityActionDragDrop:
                                                str = "ACTION_DRAG_DROP";
                                                break;
                                            case R.id.accessibilityActionDragCancel:
                                                str = "ACTION_DRAG_CANCEL";
                                                break;
                                            default:
                                                str = "ACTION_UNKNOWN";
                                                break;
                                        }
                                }
                        }
                }
            } else {
                str = "ACTION_CLEAR_FOCUS";
            }
            if (str.equals("ACTION_UNKNOWN")) {
                Object obj = accessibilityActionCompat.mAction;
                if (((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel() != null) {
                    str = ((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel().toString();
                }
            }
            sb.append(str);
            if (i != actionList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public final void addAction(AccessibilityActionCompat accessibilityActionCompat) {
        this.mInfo.addAction((AccessibilityNodeInfo.AccessibilityAction) accessibilityActionCompat.mAction);
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return new AccessibilityNodeInfoCompat(AccessibilityNodeInfo.obtain());
    }

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mInfo = accessibilityNodeInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return new AccessibilityNodeInfoCompat(AccessibilityNodeInfo.obtain(accessibilityNodeInfoCompat.mInfo));
    }
}
