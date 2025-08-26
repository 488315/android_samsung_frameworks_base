package androidx.slice.widget;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.slice.widget.SliceView;

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
        String strM;
        StringBuilder sb = new StringBuilder("mode=");
        SliceView.AnonymousClass3 anonymousClass3 = SliceView.SLICE_ACTION_PRIORITY_COMPARATOR;
        int i = this.sliceMode;
        sb.append(i != 1 ? i != 2 ? i != 3 ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unknown mode: ") : "MODE SHORTCUT" : "MODE LARGE" : "MODE SMALL");
        sb.append(", actionType=");
        String strM2 = "TOGGLE";
        int i2 = this.actionType;
        switch (i2) {
            case 0:
                strM = "TOGGLE";
                break;
            case 1:
                strM = "BUTTON";
                break;
            case 2:
                strM = "SLIDER";
                break;
            case 3:
                strM = "CONTENT";
                break;
            case 4:
                strM = "SEE MORE";
                break;
            case 5:
                strM = "SELECTION";
                break;
            case 6:
                strM = "DATE_PICK";
                break;
            case 7:
                strM = "TIME_PICK";
                break;
            default:
                strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "unknown action: ");
                break;
        }
        sb.append(strM);
        sb.append(", rowTemplateType=");
        int i3 = this.rowTemplateType;
        switch (i3) {
            case -1:
                strM2 = "SHORTCUT";
                break;
            case 0:
                strM2 = "LIST";
                break;
            case 1:
                strM2 = "GRID";
                break;
            case 2:
                strM2 = "MESSAGING";
                break;
            case 3:
                break;
            case 4:
                strM2 = "SLIDER";
                break;
            case 5:
                strM2 = "PROGRESS";
                break;
            case 6:
                strM2 = "SELECTION";
                break;
            case 7:
                strM2 = "DATE_PICK";
                break;
            case 8:
                strM2 = "TIME_PICK";
                break;
            default:
                strM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "unknown row type: ");
                break;
        }
        sb.append(strM2);
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
