package androidx.compose.runtime;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Latch {
    public final Object lock = new Object();
    public List awaiters = new ArrayList();
    public List spareList = new ArrayList();
    public boolean _isOpen = true;
}
