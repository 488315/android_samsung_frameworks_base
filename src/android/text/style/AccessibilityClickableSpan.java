package android.text.style;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.view.View;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.R;

/* loaded from: classes4.dex */
public class AccessibilityClickableSpan extends ClickableSpan implements ParcelableSpan {
    public static final Parcelable.Creator<AccessibilityClickableSpan> CREATOR = new Parcelable.Creator<AccessibilityClickableSpan>() { // from class: android.text.style.AccessibilityClickableSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityClickableSpan createFromParcel(Parcel parcel) {
            return new AccessibilityClickableSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityClickableSpan[] newArray(int i) {
            return new AccessibilityClickableSpan[i];
        }
    };
    private final int mOriginalClickableSpanId;
    private int mWindowId = -1;
    private long mSourceNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    private int mConnectionId = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 25;
    }

    public AccessibilityClickableSpan(int i) {
        this.mOriginalClickableSpanId = i;
    }

    public AccessibilityClickableSpan(Parcel parcel) {
        this.mOriginalClickableSpanId = parcel.readInt();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeInt(this.mOriginalClickableSpanId);
    }

    public ClickableSpan findClickableSpan(CharSequence charSequence) {
        if (!(charSequence instanceof Spanned)) {
            return null;
        }
        ClickableSpan[] clickableSpanArr = (ClickableSpan[]) ((Spanned) charSequence).getSpans(0, charSequence.length(), ClickableSpan.class);
        for (int i = 0; i < clickableSpanArr.length; i++) {
            if (clickableSpanArr[i].getId() == this.mOriginalClickableSpanId) {
                return clickableSpanArr[i];
            }
        }
        return null;
    }

    public void copyConnectionDataFrom(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mConnectionId = accessibilityNodeInfo.getConnectionId();
        this.mWindowId = accessibilityNodeInfo.getWindowId();
        this.mSourceNodeId = accessibilityNodeInfo.getSourceNodeId();
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccessibilityNodeInfo.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN, this);
        if (this.mWindowId == -1 || this.mSourceNodeId == AccessibilityNodeInfo.UNDEFINED_NODE_ID || this.mConnectionId == -1) {
            throw new RuntimeException("ClickableSpan for accessibility service not properly initialized");
        }
        AccessibilityInteractionClient.getInstance().performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, R.id.accessibilityActionClickOnClickableSpan, bundle);
    }

    public int getOriginalClickableSpanId() {
        return this.mOriginalClickableSpanId;
    }
}
