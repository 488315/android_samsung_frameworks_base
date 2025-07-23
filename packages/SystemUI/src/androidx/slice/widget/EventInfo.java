package androidx.slice.widget;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.slice.widget.SliceView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class EventInfo {
    public final int actionType;
    public final int rowIndex;
    public final int rowTemplateType;
    public final int sliceMode;
    public int actionPosition = -1;
    public int actionIndex = -1;
    public int actionCount = -1;
    public int state = -1;

    public EventInfo(int i, int i2, int i3, int i4) {
        this.sliceMode = i;
        this.actionType = i2;
        this.rowTemplateType = i3;
        this.rowIndex = i4;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("mode=");
        SliceView.AnonymousClass3 anonymousClass3 = SliceView.SLICE_ACTION_PRIORITY_COMPARATOR;
        int i = this.sliceMode;
        sb.append(i != 1 ? i != 2 ? i != 3 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unknown mode: ") : "MODE SHORTCUT" : "MODE LARGE" : "MODE SMALL");
        sb.append(", actionType=");
        String str2 = "TOGGLE";
        int i2 = this.actionType;
        switch (i2) {
            case 0:
                str = "TOGGLE";
                break;
            case 1:
                str = "BUTTON";
                break;
            case 2:
                str = "SLIDER";
                break;
            case 3:
                str = "CONTENT";
                break;
            case 4:
                str = "SEE MORE";
                break;
            case 5:
                str = "SELECTION";
                break;
            case 6:
                str = "DATE_PICK";
                break;
            case 7:
                str = "TIME_PICK";
                break;
            default:
                str = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "unknown action: ");
                break;
        }
        sb.append(str);
        sb.append(", rowTemplateType=");
        int i3 = this.rowTemplateType;
        switch (i3) {
            case -1:
                str2 = "SHORTCUT";
                break;
            case 0:
                str2 = "LIST";
                break;
            case 1:
                str2 = "GRID";
                break;
            case 2:
                str2 = "MESSAGING";
                break;
            case 3:
                break;
            case 4:
                str2 = "SLIDER";
                break;
            case 5:
                str2 = "PROGRESS";
                break;
            case 6:
                str2 = "SELECTION";
                break;
            case 7:
                str2 = "DATE_PICK";
                break;
            case 8:
                str2 = "TIME_PICK";
                break;
            default:
                str2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "unknown row type: ");
                break;
        }
        sb.append(str2);
        sb.append(", rowIndex=");
        sb.append(this.rowIndex);
        sb.append(", actionPosition=");
        int i4 = this.actionPosition;
        sb.append(i4 != 0 ? i4 != 1 ? i4 != 2 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "unknown position: ") : "CELL" : "END" : "START");
        sb.append(", actionIndex=");
        sb.append(this.actionIndex);
        sb.append(", actionCount=");
        sb.append(this.actionCount);
        sb.append(", state=");
        sb.append(this.state);
        return sb.toString();
    }
}
