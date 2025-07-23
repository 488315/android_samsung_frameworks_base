package androidx.compose.ui.semantics;

import androidx.compose.ui.autofill.ContentDataType;
import androidx.compose.ui.autofill.ContentType;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsProperties {
    public static final SemanticsPropertyKey ContentDataType;
    public static final SemanticsPropertyKey ContentType = null;
    public static final SemanticsPropertyKey EditableText;
    public static final SemanticsPropertyKey Error;
    public static final SemanticsPropertyKey HorizontalScrollAxisRange;
    public static final SemanticsPropertyKey ImeAction;
    public static final SemanticsPropertyKey IndexForKey;
    public static final SemanticsPropertyKey IsDialog;
    public static final SemanticsPropertyKey IsEditable;
    public static final SemanticsPropertyKey IsPopup;
    public static final SemanticsPropertyKey IsShowingTextSubstitution;
    public static final SemanticsPropertyKey LinkTestMarker;
    public static final SemanticsPropertyKey MaxTextLength;
    public static final SemanticsPropertyKey Password;
    public static final SemanticsPropertyKey Role;
    public static final SemanticsPropertyKey Selected;
    public static final SemanticsPropertyKey TestTag;
    public static final SemanticsPropertyKey Text;
    public static final SemanticsPropertyKey TextSelectionRange;
    public static final SemanticsPropertyKey TextSubstitution;
    public static final SemanticsPropertyKey ToggleableState;
    public static final SemanticsPropertyKey TraversalIndex;
    public static final SemanticsPropertyKey VerticalScrollAxisRange;
    public static final SemanticsProperties INSTANCE = new SemanticsProperties();
    public static final SemanticsPropertyKey ContentDescription = new SemanticsPropertyKey("ContentDescription", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$ContentDescription$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            List list = (List) obj;
            List list2 = (List) obj2;
            if (list == null) {
                return list2;
            }
            ArrayList arrayList = new ArrayList(list);
            arrayList.addAll(list2);
            return arrayList;
        }
    });
    public static final SemanticsPropertyKey StateDescription = new SemanticsPropertyKey("StateDescription", true);
    public static final SemanticsPropertyKey ProgressBarRangeInfo = new SemanticsPropertyKey("ProgressBarRangeInfo", true);
    public static final SemanticsPropertyKey PaneTitle = new SemanticsPropertyKey("PaneTitle", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$PaneTitle$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            throw new IllegalStateException("merge function called on unmergeable property PaneTitle.");
        }
    });
    public static final SemanticsPropertyKey SelectableGroup = new SemanticsPropertyKey("SelectableGroup", true);
    public static final SemanticsPropertyKey CollectionInfo = new SemanticsPropertyKey("CollectionInfo", true);
    public static final SemanticsPropertyKey CollectionItemInfo = new SemanticsPropertyKey("CollectionItemInfo", true);
    public static final SemanticsPropertyKey Heading = new SemanticsPropertyKey("Heading", true);
    public static final SemanticsPropertyKey Disabled = new SemanticsPropertyKey(KnoxVpnPolicyConstants.VPN_CERT_TYPE_DISABLED, true);
    public static final SemanticsPropertyKey LiveRegion = new SemanticsPropertyKey("LiveRegion", true);
    public static final SemanticsPropertyKey Focused = new SemanticsPropertyKey("Focused", true);
    public static final SemanticsPropertyKey IsContainer = new SemanticsPropertyKey("IsContainer", true);
    public static final SemanticsPropertyKey IsTraversalGroup = new SemanticsPropertyKey("IsTraversalGroup", null, 2, null);
    public static final SemanticsPropertyKey InvisibleToUser = new SemanticsPropertyKey("InvisibleToUser", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$InvisibleToUser$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return (Unit) obj;
        }
    });
    public static final SemanticsPropertyKey HideFromAccessibility = new SemanticsPropertyKey("HideFromAccessibility", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$HideFromAccessibility$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return (Unit) obj;
        }
    });

    static {
        new SemanticsPropertyKey("ContentType", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$ContentType$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (ContentType) obj;
            }
        });
        ContentDataType = new SemanticsPropertyKey("ContentDataType", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$ContentDataType$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (ContentDataType) obj;
            }
        });
        TraversalIndex = new SemanticsPropertyKey("TraversalIndex", new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$TraversalIndex$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Float f = (Float) obj;
                ((Number) obj2).floatValue();
                return f;
            }
        });
        HorizontalScrollAxisRange = new SemanticsPropertyKey("HorizontalScrollAxisRange", true);
        VerticalScrollAxisRange = new SemanticsPropertyKey("VerticalScrollAxisRange", true);
        IsPopup = new SemanticsPropertyKey("IsPopup", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$IsPopup$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                throw new IllegalStateException("merge function called on unmergeable property IsPopup. A popup should not be a child of a clickable/focusable node.");
            }
        });
        IsDialog = new SemanticsPropertyKey("IsDialog", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$IsDialog$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                throw new IllegalStateException("merge function called on unmergeable property IsDialog. A dialog should not be a child of a clickable/focusable node.");
            }
        });
        Role = new SemanticsPropertyKey("Role", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$Role$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Role role = (Role) obj;
                int i = ((Role) obj2).value;
                return role;
            }
        });
        TestTag = new SemanticsPropertyKey("TestTag", false, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$TestTag$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (String) obj;
            }
        });
        LinkTestMarker = new SemanticsPropertyKey("LinkTestMarker", false, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$LinkTestMarker$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (Unit) obj;
            }
        });
        Text = new SemanticsPropertyKey("Text", true, new Function2() { // from class: androidx.compose.ui.semantics.SemanticsProperties$Text$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                List list = (List) obj;
                List list2 = (List) obj2;
                if (list == null) {
                    return list2;
                }
                ArrayList arrayList = new ArrayList(list);
                arrayList.addAll(list2);
                return arrayList;
            }
        });
        TextSubstitution = new SemanticsPropertyKey("TextSubstitution", null, 2, null);
        IsShowingTextSubstitution = new SemanticsPropertyKey("IsShowingTextSubstitution", null, 2, null);
        EditableText = new SemanticsPropertyKey("EditableText", true);
        TextSelectionRange = new SemanticsPropertyKey("TextSelectionRange", true);
        ImeAction = new SemanticsPropertyKey("ImeAction", true);
        Selected = new SemanticsPropertyKey("Selected", true);
        ToggleableState = new SemanticsPropertyKey("ToggleableState", true);
        Password = new SemanticsPropertyKey("Password", true);
        Error = new SemanticsPropertyKey("Error", true);
        IndexForKey = new SemanticsPropertyKey("IndexForKey", null, 2, null);
        IsEditable = new SemanticsPropertyKey("IsEditable", null, 2, null);
        MaxTextLength = new SemanticsPropertyKey("MaxTextLength", null, 2, null);
    }

    private SemanticsProperties() {
    }
}
