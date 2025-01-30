package com.sec.ims;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class DialogEvent implements Parcelable {
    public static final Parcelable.Creator<DialogEvent> CREATOR = new Parcelable.Creator<DialogEvent>() { // from class: com.sec.ims.DialogEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DialogEvent createFromParcel(Parcel parcel) {
            return new DialogEvent(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DialogEvent[] newArray(int i) {
            return new DialogEvent[i];
        }
    };
    private List<Dialog> mDialogList;
    private String mMSISDN;
    private int mPhoneId;
    private int mRegId;

    public /* synthetic */ DialogEvent(Parcel parcel, int i) {
        this(parcel);
    }

    public void clearDialogList() {
        this.mDialogList.clear();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<Dialog> getDialogList() {
        return Collections.unmodifiableList(this.mDialogList);
    }

    public String getMsisdn() {
        return this.mMSISDN;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public int getRegId() {
        return this.mRegId;
    }

    public void setPhoneId(int i) {
        this.mPhoneId = i;
    }

    public void setRegId(int i) {
        this.mRegId = i;
    }

    public String toString() {
        return "DialogEvent#" + this.mPhoneId + " [mMSISDN=" + this.mMSISDN + ", mDialogList=" + this.mDialogList + "]";
    }

    public String toXmlString() {
        String m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("<?xml version=\"1.0\"?>\n\t<dialog-info xmlns=\"urn:ietf:params:xml:ns:dialog-info\"\n\t\tversion=\"0\" state=\"full\" entity=\""), this.mMSISDN, "\">\n");
        for (Dialog dialog : this.mDialogList) {
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(m16m);
            m18m.append(dialog.toXmlString());
            m16m = m18m.toString();
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m16m, "</dialog-info>");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeTypedList(this.mDialogList);
        parcel.writeString(this.mMSISDN);
        parcel.writeInt(this.mRegId);
        parcel.writeInt(this.mPhoneId);
    }

    public DialogEvent(String str, List<Dialog> list) {
        this.mMSISDN = str;
        this.mDialogList = list;
    }

    private DialogEvent(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mDialogList = arrayList;
        parcel.readTypedList(arrayList, Dialog.CREATOR);
        this.mMSISDN = parcel.readString();
        this.mRegId = parcel.readInt();
        this.mPhoneId = parcel.readInt();
    }
}
