package android.view.contentcapture;

import android.annotation.SystemApi;
import android.graphics.Insets;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.inputmethod.BaseInputConnection;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class ContentCaptureEvent implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureEvent> CREATOR = new Parcelable.Creator<ContentCaptureEvent>() { // from class: android.view.contentcapture.ContentCaptureEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureEvent createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            ContentCaptureEvent contentCaptureEvent = new ContentCaptureEvent(readInt, readInt2, parcel.readLong());
            AutofillId autofillId = (AutofillId) parcel.readParcelable(null, AutofillId.class);
            if (autofillId != null) {
                contentCaptureEvent.setAutofillId(autofillId);
            }
            ArrayList<AutofillId> createTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
            if (createTypedArrayList != null) {
                contentCaptureEvent.setAutofillIds(createTypedArrayList);
            }
            ViewNode readFromParcel = ViewNode.readFromParcel(parcel);
            if (readFromParcel != null) {
                contentCaptureEvent.setViewNode(readFromParcel);
            }
            contentCaptureEvent.setText(parcel.readCharSequence());
            if (readInt2 == -1 || readInt2 == -2) {
                contentCaptureEvent.setParentSessionId(parcel.readInt());
            }
            if (readInt2 == -1 || readInt2 == 6) {
                contentCaptureEvent.setClientContext((ContentCaptureContext) parcel.readParcelable(null, ContentCaptureContext.class));
            }
            if (readInt2 == 9) {
                contentCaptureEvent.setInsets((Insets) parcel.readParcelable(null, Insets.class));
            }
            if (readInt2 == 10) {
                contentCaptureEvent.setBounds((Rect) parcel.readParcelable(null, Rect.class));
            }
            if (readInt2 == 3) {
                contentCaptureEvent.setComposingIndex(parcel.readInt(), parcel.readInt());
                contentCaptureEvent.restoreComposingSpan();
                contentCaptureEvent.setSelectionIndex(parcel.readInt(), parcel.readInt());
                contentCaptureEvent.restoreSelectionSpans();
            }
            return contentCaptureEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureEvent[] newArray(int i) {
            return new ContentCaptureEvent[i];
        }
    };
    public static final int MAX_INVALID_VALUE = -1;
    private static final String TAG = "ContentCaptureEvent";
    public static final int TYPE_CONTEXT_UPDATED = 6;
    public static final int TYPE_SESSION_FINISHED = -2;
    public static final int TYPE_SESSION_FLUSH = 11;
    public static final int TYPE_SESSION_PAUSED = 8;
    public static final int TYPE_SESSION_RESUMED = 7;
    public static final int TYPE_SESSION_STARTED = -1;
    public static final int TYPE_VIEW_APPEARED = 1;
    public static final int TYPE_VIEW_DISAPPEARED = 2;
    public static final int TYPE_VIEW_INSETS_CHANGED = 9;
    public static final int TYPE_VIEW_TEXT_CHANGED = 3;
    public static final int TYPE_VIEW_TREE_APPEARED = 5;
    public static final int TYPE_VIEW_TREE_APPEARING = 4;
    public static final int TYPE_WINDOW_BOUNDS_CHANGED = 10;
    private Rect mBounds;
    private ContentCaptureContext mClientContext;
    private int mComposingEnd;
    private int mComposingStart;
    private final long mEventTime;
    private AutofillId mId;
    private ArrayList<AutofillId> mIds;
    private Insets mInsets;
    private ViewNode mNode;
    private int mParentSessionId;
    private int mSelectionEndIndex;
    private int mSelectionStartIndex;
    private final int mSessionId;
    private CharSequence mText;
    private boolean mTextHasComposingSpan;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentCaptureEvent(int i, int i2, long j) {
        this.mParentSessionId = 0;
        this.mComposingStart = -1;
        this.mComposingEnd = -1;
        this.mSelectionStartIndex = -1;
        this.mSelectionEndIndex = -1;
        this.mSessionId = i;
        this.mType = i2;
        this.mEventTime = j;
    }

    public ContentCaptureEvent(int i, int i2) {
        this(i, i2, System.currentTimeMillis());
    }

    public ContentCaptureEvent setAutofillId(AutofillId autofillId) {
        this.mId = (AutofillId) Objects.requireNonNull(autofillId);
        return this;
    }

    public ContentCaptureEvent setAutofillIds(ArrayList<AutofillId> arrayList) {
        this.mIds = (ArrayList) Objects.requireNonNull(arrayList);
        return this;
    }

    public ContentCaptureEvent addAutofillId(AutofillId autofillId) {
        Objects.requireNonNull(autofillId);
        if (this.mIds == null) {
            ArrayList<AutofillId> arrayList = new ArrayList<>();
            this.mIds = arrayList;
            AutofillId autofillId2 = this.mId;
            if (autofillId2 == null) {
                Log.w(TAG, "addAutofillId(" + autofillId + ") called without an initial id");
            } else {
                arrayList.add(autofillId2);
                this.mId = null;
            }
        }
        this.mIds.add(autofillId);
        return this;
    }

    public ContentCaptureEvent setParentSessionId(int i) {
        this.mParentSessionId = i;
        return this;
    }

    public ContentCaptureEvent setClientContext(ContentCaptureContext contentCaptureContext) {
        this.mClientContext = contentCaptureContext;
        return this;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getParentSessionId() {
        return this.mParentSessionId;
    }

    public ContentCaptureContext getContentCaptureContext() {
        return this.mClientContext;
    }

    public ContentCaptureEvent setViewNode(ViewNode viewNode) {
        this.mNode = (ViewNode) Objects.requireNonNull(viewNode);
        return this;
    }

    public ContentCaptureEvent setText(CharSequence charSequence) {
        this.mText = charSequence;
        return this;
    }

    public ContentCaptureEvent setComposingIndex(int i, int i2) {
        this.mComposingStart = i;
        this.mComposingEnd = i2;
        return this;
    }

    public boolean hasComposingSpan() {
        return this.mComposingStart > -1;
    }

    public ContentCaptureEvent setSelectionIndex(int i, int i2) {
        this.mSelectionStartIndex = i;
        this.mSelectionEndIndex = i2;
        return this;
    }

    boolean hasSameComposingSpan(ContentCaptureEvent contentCaptureEvent) {
        return this.mComposingStart == contentCaptureEvent.mComposingStart && this.mComposingEnd == contentCaptureEvent.mComposingEnd;
    }

    boolean hasSameSelectionSpan(ContentCaptureEvent contentCaptureEvent) {
        return this.mSelectionStartIndex == contentCaptureEvent.mSelectionStartIndex && this.mSelectionEndIndex == contentCaptureEvent.mSelectionEndIndex;
    }

    private int getComposingStart() {
        return this.mComposingStart;
    }

    private int getComposingEnd() {
        return this.mComposingEnd;
    }

    private int getSelectionStart() {
        return this.mSelectionStartIndex;
    }

    private int getSelectionEnd() {
        return this.mSelectionEndIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreComposingSpan() {
        int i;
        int i2 = this.mComposingStart;
        if (i2 <= -1 || (i = this.mComposingEnd) <= -1) {
            return;
        }
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Spannable) {
            BaseInputConnection.setComposingSpans((Spannable) charSequence, i2, i);
        } else {
            Log.w(TAG, "Text is not a Spannable.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreSelectionSpans() {
        if (this.mSelectionStartIndex <= -1 || this.mSelectionEndIndex <= -1) {
            return;
        }
        CharSequence charSequence = this.mText;
        if (charSequence instanceof SpannableString) {
            SpannableString spannableString = (SpannableString) charSequence;
            Object obj = Selection.SELECTION_START;
            int i = this.mSelectionStartIndex;
            spannableString.setSpan(obj, i, i, 0);
            Object obj2 = Selection.SELECTION_END;
            int i2 = this.mSelectionEndIndex;
            spannableString.setSpan(obj2, i2, i2, 0);
            return;
        }
        Log.w(TAG, "Text is not a SpannableString.");
    }

    public ContentCaptureEvent setInsets(Insets insets) {
        this.mInsets = insets;
        return this;
    }

    public ContentCaptureEvent setBounds(Rect rect) {
        this.mBounds = rect;
        return this;
    }

    public int getType() {
        return this.mType;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public ViewNode getViewNode() {
        return this.mNode;
    }

    public AutofillId getId() {
        return this.mId;
    }

    public List<AutofillId> getIds() {
        return this.mIds;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public Insets getInsets() {
        return this.mInsets;
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    public void mergeEvent(ContentCaptureEvent contentCaptureEvent) {
        Objects.requireNonNull(contentCaptureEvent);
        int type = contentCaptureEvent.getType();
        if (this.mType != type) {
            Log.e(TAG, "mergeEvent(" + getTypeAsString(type) + ") cannot be merged with different eventType=" + getTypeAsString(this.mType));
            return;
        }
        if (type != 2) {
            if (type == 3) {
                setText(contentCaptureEvent.getText());
                setComposingIndex(contentCaptureEvent.getComposingStart(), contentCaptureEvent.getComposingEnd());
                setSelectionIndex(contentCaptureEvent.getSelectionStart(), contentCaptureEvent.getSelectionEnd());
                return;
            }
            Log.e(TAG, "mergeEvent(" + getTypeAsString(type) + ") does not support this event type.");
            return;
        }
        List<AutofillId> ids = contentCaptureEvent.getIds();
        AutofillId id = contentCaptureEvent.getId();
        if (ids == null) {
            if (id != null) {
                addAutofillId(id);
                return;
            }
            throw new IllegalArgumentException("mergeEvent(): got TYPE_VIEW_DISAPPEARED event with neither id or ids: " + contentCaptureEvent);
        }
        if (id != null) {
            Log.w(TAG, "got TYPE_VIEW_DISAPPEARED event with both id and ids: " + contentCaptureEvent);
        }
        for (int i = 0; i < ids.size(); i++) {
            addAutofillId(ids.get(i));
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print("type=");
        printWriter.print(getTypeAsString(this.mType));
        printWriter.print(", time=");
        printWriter.print(this.mEventTime);
        if (this.mId != null) {
            printWriter.print(", id=");
            printWriter.print(this.mId);
        }
        if (this.mIds != null) {
            printWriter.print(", ids=");
            printWriter.print(this.mIds);
        }
        if (this.mNode != null) {
            printWriter.print(", mNode.id=");
            printWriter.print(this.mNode.getAutofillId());
        }
        if (this.mSessionId != 0) {
            printWriter.print(", sessionId=");
            printWriter.print(this.mSessionId);
        }
        if (this.mParentSessionId != 0) {
            printWriter.print(", parentSessionId=");
            printWriter.print(this.mParentSessionId);
        }
        if (this.mText != null) {
            printWriter.print(", text=");
            printWriter.println(ContentCaptureHelper.getSanitizedString(this.mText));
        }
        if (this.mClientContext != null) {
            printWriter.print(", context=");
            this.mClientContext.dump(printWriter);
            printWriter.println();
        }
        if (this.mInsets != null) {
            printWriter.print(", insets=");
            printWriter.println(this.mInsets);
        }
        if (this.mBounds != null) {
            printWriter.print(", bounds=");
            printWriter.println(this.mBounds);
        }
        if (this.mComposingStart > -1) {
            printWriter.print(", composing(");
            printWriter.print(this.mComposingStart);
            printWriter.print(", ");
            printWriter.print(this.mComposingEnd);
            printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mSelectionStartIndex > -1) {
            printWriter.print(", selection(");
            printWriter.print(this.mSelectionStartIndex);
            printWriter.print(", ");
            printWriter.print(this.mSelectionEndIndex);
            printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ContentCaptureEvent[type=");
        sb.append(getTypeAsString(this.mType));
        sb.append(", session=");
        sb.append(this.mSessionId);
        if (this.mType == -1 && this.mParentSessionId != 0) {
            sb.append(", parent=");
            sb.append(this.mParentSessionId);
        }
        if (this.mId != null) {
            sb.append(", id=");
            sb.append(this.mId);
        }
        if (this.mIds != null) {
            sb.append(", ids=");
            sb.append(this.mIds);
        }
        ViewNode viewNode = this.mNode;
        if (viewNode != null) {
            String className = viewNode.getClassName();
            sb.append(", class=");
            sb.append(className);
            sb.append(", id=");
            sb.append(this.mNode.getAutofillId());
            if (this.mNode.getText() != null) {
                sb.append(", text=");
                sb.append((CharSequence) ContentCaptureHelper.getSanitizedString(this.mNode.getText()));
            }
        }
        if (this.mText != null) {
            sb.append(", text=");
            sb.append((CharSequence) ContentCaptureHelper.getSanitizedString(this.mText));
        }
        if (this.mClientContext != null) {
            sb.append(", context=");
            sb.append(this.mClientContext);
        }
        if (this.mInsets != null) {
            sb.append(", insets=");
            sb.append(this.mInsets);
        }
        if (this.mBounds != null) {
            sb.append(", bounds=");
            sb.append(this.mBounds);
        }
        if (this.mComposingStart > -1) {
            sb.append(", composing=[");
            sb.append(this.mComposingStart);
            sb.append(",");
            sb.append(this.mComposingEnd);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (this.mSelectionStartIndex > -1) {
            sb.append(", selection=[");
            sb.append(this.mSelectionStartIndex);
            sb.append(",");
            sb.append(this.mSelectionEndIndex);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSessionId);
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mEventTime);
        parcel.writeParcelable(this.mId, i);
        parcel.writeTypedList(this.mIds);
        ViewNode.writeToParcel(parcel, this.mNode, i);
        parcel.writeCharSequence(this.mText);
        int i2 = this.mType;
        if (i2 == -1 || i2 == -2) {
            parcel.writeInt(this.mParentSessionId);
        }
        int i3 = this.mType;
        if (i3 == -1 || i3 == 6) {
            parcel.writeParcelable(this.mClientContext, i);
        }
        if (this.mType == 9) {
            parcel.writeParcelable(this.mInsets, i);
        }
        if (this.mType == 10) {
            parcel.writeParcelable(this.mBounds, i);
        }
        if (this.mType == 3) {
            parcel.writeInt(this.mComposingStart);
            parcel.writeInt(this.mComposingEnd);
            parcel.writeInt(this.mSelectionStartIndex);
            parcel.writeInt(this.mSelectionEndIndex);
        }
    }

    public static String getTypeAsString(int i) {
        switch (i) {
            case -2:
                return "SESSION_FINISHED";
            case -1:
                return "SESSION_STARTED";
            case 0:
            default:
                return "UKNOWN_TYPE: " + i;
            case 1:
                return "VIEW_APPEARED";
            case 2:
                return "VIEW_DISAPPEARED";
            case 3:
                return "VIEW_TEXT_CHANGED";
            case 4:
                return "VIEW_TREE_APPEARING";
            case 5:
                return "VIEW_TREE_APPEARED";
            case 6:
                return "CONTEXT_UPDATED";
            case 7:
                return "SESSION_RESUMED";
            case 8:
                return "SESSION_PAUSED";
            case 9:
                return "VIEW_INSETS_CHANGED";
            case 10:
                return "TYPE_WINDOW_BOUNDS_CHANGED";
            case 11:
                return "TYPE_SESSION_FLUSH";
        }
    }
}
